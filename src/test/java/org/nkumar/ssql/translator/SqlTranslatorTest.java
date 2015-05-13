package org.nkumar.ssql.translator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nkumar.ssql.translater.GenericTranslatorSqlVisitor;
import org.nkumar.ssql.translater.HSQL2TranslatorSqlVisitor;
import org.nkumar.ssql.translater.IdentityTranslatorSqlVisitor;
import org.nkumar.ssql.translater.MySQL5TranslatorSqlVisitor;
import org.nkumar.ssql.translater.Oracle9iTranslatorSqlVisitor;
import org.nkumar.ssql.translater.PostgreSQL9TranslatorSqlVisitor;
import org.nkumar.ssql.translater.SQLServer2005TranslatorSqlVisitor;
import org.nkumar.ssql.translater.SQLServer2008TranslatorSqlVisitor;
import org.nkumar.ssql.translater.SqlTranslator;
import org.nkumar.ssql.util.TUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

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
    public void testTypeMappings() throws Exception
    {
        String[] dbNames = new String[]{"MySQL5", "Oracle9i", "PostgreSQL9", "SQLServer2005", "SQLServer2008"};
        Arrays.sort(dbNames);
        StringBuilder builder = new StringBuilder(10 * 1024);
        builder.append("<mappings>\n");
        for (String dbName : dbNames)
        {
            String translatorName = getTranslatorName(dbName);
            GenericTranslatorSqlVisitor translator =
                    (GenericTranslatorSqlVisitor) Class.forName(translatorName).newInstance();
            builder.append(translator.toXml());
        }
        builder.append("</mappings>\n");
        File actualFile = new File(BASE_TRANS_DIR, "../typemapping.xml");
        FileWriter out = new FileWriter(actualFile);
        out.write(builder.toString());
        out.close();
        TUtil.assertSameContent(new File("src/main/resources/typemapping.xml"), actualFile);
    }

    @Test
    public void translateDDL1() throws Exception
    {
        assertCorrectTranslation("ddl_1.sql", "Identity", "MySQL5","Oracle9i", "PostgreSQL9", "SQLServer2005");
    }

    @Test
    public void translateSpringBatchDDL() throws Exception
    {
        assertCorrectTranslation("spring_batch.sql", "Identity", "MySQL5","Oracle9i", "PostgreSQL9", "SQLServer2005");
        assertCorrectTranslation("spring_batch_drop.sql", "Identity", "MySQL5","Oracle9i", "PostgreSQL9", "SQLServer2005");
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
            case "MySQL5":
                return MySQL5TranslatorSqlVisitor.class.getName();
            case "HSQL2":
                return HSQL2TranslatorSqlVisitor.class.getName();
            case "SQLServer2005":
                return SQLServer2005TranslatorSqlVisitor.class.getName();
            case "SQLServer2008":
                return SQLServer2008TranslatorSqlVisitor.class.getName();
            default:
                throw new AssertionError(dbName + " not supported");
        }
    }

}
