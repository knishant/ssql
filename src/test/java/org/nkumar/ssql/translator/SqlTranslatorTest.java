package org.nkumar.ssql.translator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nkumar.ssql.dialect.Dialect;
import org.nkumar.ssql.dialect.IdentityDialect;
import org.nkumar.ssql.dialect.MySQL5InnoDBDialect;
import org.nkumar.ssql.dialect.Oracle9iDialect;
import org.nkumar.ssql.dialect.PostgreSQL9Dialect;
import org.nkumar.ssql.dialect.SQLServer2005Dialect;
import org.nkumar.ssql.dialect.SQLServer2008Dialect;
import org.nkumar.ssql.util.TUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        List<String> dbNames = new ArrayList<>();
        dbNames.addAll(Arrays.asList("MySQL5InnoDB", "Oracle9i", "PostgreSQL9", "SQLServer2005", "SQLServer2008"));
        Collections.sort(dbNames);
        dbNames.add(0, "Identity");
        StringBuilder builder = new StringBuilder(10 * 1024);
        builder.append("<mappings>\n");
        for (String dbName : dbNames)
        {
            String dialectName = getDialectClassName(dbName);
            Dialect dialect = (Dialect) Class.forName(dialectName).newInstance();
            builder.append(dialect.toXml());
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
        assertCorrectTranslation("ddl_1.sql", "Identity", "MySQL5InnoDB", "Oracle9i", "PostgreSQL9", "SQLServer2005",
                "SQLServer2008");
    }

    @Test
    public void translateSpringBatchDDL() throws Exception
    {
        assertCorrectTranslation("spring_batch.sql", "Identity", "MySQL5InnoDB", "Oracle9i", "PostgreSQL9",
                "SQLServer2005", "SQLServer2008");
        assertCorrectTranslation("spring_batch_drop.sql", "Identity", "MySQL5InnoDB", "Oracle9i", "PostgreSQL9",
                "SQLServer2005", "SQLServer2008");
    }

    @Test
    public void translateQuartzDDL() throws Exception
    {
        assertCorrectTranslation("quartz.sql", "Identity", "MySQL5InnoDB", "Oracle9i", "PostgreSQL9", "SQLServer2005",
                "SQLServer2008");
    }

    private static void assertCorrectTranslation(String sqlFileName, String... dbnames) throws Exception
    {
        File srcFile = new File(BASE_RSRC_DIR, "sqltranslatortest/" + sqlFileName);
        String[] translatorNames = getDialectClassNames(dbnames);
        SqlTranslator.translateSqlFileToMultipleTranslators(srcFile, BASE_TRANS_DIR, translatorNames);
        for (String dbname : dbnames)
        {
            File expectedDir = new File(BASE_EXP_DIR, dbname);
            expectedDir.mkdirs();
            File actualDir = new File(BASE_TRANS_DIR, dbname);
            TUtil.assertSameContent(new File(expectedDir, sqlFileName), new File(actualDir, sqlFileName));
        }
    }

    private static String[] getDialectClassNames(String... dbNames)
    {
        String[] names = new String[dbNames.length];
        for (int i = 0; i < dbNames.length; i++)
        {
            names[i] = getDialectClassName(dbNames[i]);
        }
        return names;
    }

    private static String getDialectClassName(String dbName)
    {
        switch (dbName)
        {
            case "Identity":
                return IdentityDialect.class.getName();
            case "Oracle9i":
                return Oracle9iDialect.class.getName();
            case "PostgreSQL9":
                return PostgreSQL9Dialect.class.getName();
            case "MySQL5InnoDB":
                return MySQL5InnoDBDialect.class.getName();
            case "SQLServer2005":
                return SQLServer2005Dialect.class.getName();
            case "SQLServer2008":
                return SQLServer2008Dialect.class.getName();
            default:
                throw new AssertionError(dbName + " not supported");
        }
    }

}
