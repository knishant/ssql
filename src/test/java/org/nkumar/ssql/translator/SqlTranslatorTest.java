package org.nkumar.ssql.translator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nkumar.ssql.translater.IdentityTranslatorSqlVisitor;
import org.nkumar.ssql.translater.Oracle9iTranslatorSqlVisitor;
import org.nkumar.ssql.translater.PostgreSQL9TranslatorSqlVisitor;
import org.nkumar.ssql.translater.SqlTranslator;
import org.nkumar.ssql.util.TUtil;

import java.io.File;

public final class SqlTranslatorTest
{
    private static final File BASE_RSRC_DIR = new File("src/test/resources");
    private static final File BASE_TRANS_DIR = new File("build/translated/sqltranslatortest");
    private static final File BASE_EXP_DIR = new File(BASE_RSRC_DIR, "translated/sqltranslatortest");

    @BeforeClass
    public static void initClass()
    {
        TUtil.deleteFile(BASE_TRANS_DIR);
        BASE_TRANS_DIR.mkdirs();
    }

    @Test
    public void translateDDL1() throws Exception
    {
        assertCorrectTranslation("ddl_1.sql", "Identity", "Oracle9i", "PostgreSQL9");
    }

    private static void assertCorrectTranslation(String sqlFileName, String... dbnames) throws Exception
    {
        File srcFile = new File(BASE_RSRC_DIR, "sqltranslatortest/" + sqlFileName);
        String[] translatorNames = getTranslatorNames(dbnames);
        SqlTranslator.translateSqlFileToMultipleTranslators(srcFile, BASE_TRANS_DIR, translatorNames);
        for (String dbname : dbnames)
        {
            File expectedDir = new File(BASE_EXP_DIR, dbname);
            expectedDir.mkdirs();
            File actualDir = new File(BASE_TRANS_DIR, dbname);
            TUtil.assertSameContent(new File(expectedDir, sqlFileName), new File(actualDir, sqlFileName));
        }
    }

    private static String[] getTranslatorNames(String... dbNames)
    {
        String[] names = new String[dbNames.length];
        for (int i = 0; i < dbNames.length; i++)
        {
            names[i] = getTranslatorName(dbNames[i]);
        }
        return names;
    }

    private static String getTranslatorName(String dbName)
    {
        switch (dbName)
        {
            case "Identity":
                return IdentityTranslatorSqlVisitor.class.getName();
            case "Oracle9i":
                return Oracle9iTranslatorSqlVisitor.class.getName();
            case "PostgreSQL9":
                return PostgreSQL9TranslatorSqlVisitor.class.getName();
            default:
                throw new AssertionError(dbName + " not supported");
        }
    }
}
