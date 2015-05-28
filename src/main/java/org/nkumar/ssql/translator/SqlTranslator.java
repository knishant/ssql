package org.nkumar.ssql.translator;

import org.nkumar.ssql.dialect.Dialect;
import org.nkumar.ssql.model.SqlVisitable;
import org.nkumar.ssql.translator.SqlParser;
import org.nkumar.ssql.util.Util;

import java.io.File;
import java.util.List;

public final class SqlTranslator
{
    private SqlTranslator()
    {
    }

    @SuppressWarnings("unchecked")
    public static List<SqlVisitable> parseSql(String sql) throws Exception
    {
        return (List<SqlVisitable>) SqlParser.parseDdls(sql);
    }

    public static TranslatorSqlVisitor createVisitor(String dialectClassName) throws Exception
    {
        if (dialectClassName == null)
        {
            throw new IllegalArgumentException("Dialect class name was null");
        }
        dialectClassName = dialectClassName.trim();
        Class<?> clazz;
        try
        {
            clazz = Class.forName(dialectClassName);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Could not load the Dialect class - " + dialectClassName, e);
        }
        Dialect dialect;
        try
        {
            dialect = (Dialect) clazz.newInstance();
        }
        catch (ClassCastException e)
        {
            throw new RuntimeException(dialectClassName + " not of type Dialect", e);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not create an instance of - " + dialectClassName, e);
        }
        return new GenericTranslatorSqlVisitor(dialect);
    }

    public static void translateSqlFile(File srcFile, File destFile, String dialectClassName) throws Exception
    {
        List<SqlVisitable> list = parseSql(Util.readFileAsString(srcFile));
        TranslatorSqlVisitor visitor = createVisitor(dialectClassName);
        String generatedSql = translate(list, visitor);
        Util.writeStringToFile(destFile, generatedSql);
    }

    private static String translate(Iterable<SqlVisitable> list, TranslatorSqlVisitor visitor)
    {
        for (SqlVisitable sql : list)
        {
            sql.accept(visitor);
        }
        return visitor.getGeneratedSql();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void translateSqlFileToMultipleTranslators(File srcFile, File destDir, String... dialectClassNames)
            throws Exception
    {
        List<SqlVisitable> list = parseSql(Util.readFileAsString(srcFile));
        for (String dialectClass : dialectClassNames)
        {
            TranslatorSqlVisitor visitor = createVisitor(dialectClass);
            File dialectDir = new File(destDir, visitor.getDbName());
            dialectDir.mkdirs();
            File destFile = new File(dialectDir, srcFile.getName());
            String generatedSql = translate(list, visitor);
            Util.writeStringToFile(destFile, generatedSql);
        }
    }

    public static void translateSqlDirToMultipleTranslators(File srcDir, File destDir, String... dialectClassNames)
            throws Exception
    {
        if (!srcDir.isDirectory())
        {
            throw new IllegalArgumentException(srcDir.getAbsolutePath() + " is not a directory");
        }
        File[] files = srcDir.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.getName().toLowerCase().endsWith(".sql"))
                {
                    translateSqlFileToMultipleTranslators(file, destDir, dialectClassNames);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        String[] dialectClassNames = new String[args.length - 2];
        System.arraycopy(args, 2, dialectClassNames, 0, dialectClassNames.length);
        translateSqlDirToMultipleTranslators(new File(args[0]), new File(args[1]), dialectClassNames);
    }
}
