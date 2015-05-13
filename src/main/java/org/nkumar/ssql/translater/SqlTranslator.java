package org.nkumar.ssql.translater;

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

    public static TranslatorSqlVisitor createVisitor(String tsvClass) throws Exception
    {
        if (tsvClass == null)
        {
            throw new IllegalArgumentException("TranslatorSqlVisitor class name was null");
        }
        tsvClass = tsvClass.trim();
        Class<?> clazz;
        try
        {
            clazz = Class.forName(tsvClass);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Could not load the TranslatorSqlVisitor class - " + tsvClass, e);
        }
        TranslatorSqlVisitor tsv;
        try
        {
            tsv = (TranslatorSqlVisitor) clazz.newInstance();
        }
        catch (ClassCastException e)
        {
            throw new RuntimeException(tsvClass + " not of type TranslatorSqlVisitor", e);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not create an instance of - " + tsvClass, e);
        }
        return tsv;
    }

    public static void translateSqlFile(File srcFile, File destFile, String tsvClassName) throws Exception
    {
        List<SqlVisitable> list = parseSql(Util.readFileAsString(srcFile));
        TranslatorSqlVisitor visitor = createVisitor(tsvClassName);
        String generatedSql = translate(list, visitor);
        Util.writeStringToFile(destFile, generatedSql);
    }

    private static String translate(List<SqlVisitable> list, TranslatorSqlVisitor visitor)
    {
        for (SqlVisitable sql : list)
        {
            sql.accept(visitor);
        }
        return visitor.getGeneratedSql();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void translateSqlFileToMultipleTranslators(File srcFile, File destDir, String... tsvClassNames)
            throws Exception
    {
        List<SqlVisitable> list = parseSql(Util.readFileAsString(srcFile));
        for (String dialectClass : tsvClassNames)
        {
            TranslatorSqlVisitor visitor = createVisitor(dialectClass);
            File dialectDir = new File(destDir, visitor.getDbName());
            dialectDir.mkdirs();
            File destFile = new File(dialectDir, srcFile.getName());
            String generatedSql = translate(list, visitor);
            Util.writeStringToFile(destFile, generatedSql);
        }
    }

    public static void translateSqlDirToMultipleTranslators(File srcDir, File destDir, String... tsvClassNames)
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
                    translateSqlFileToMultipleTranslators(file, destDir, tsvClassNames);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        String[] tsvClassNames = new String[args.length - 2];
        System.arraycopy(args, 2, tsvClassNames, 0, tsvClassNames.length);
        translateSqlDirToMultipleTranslators(new File(args[0]), new File(args[1]), tsvClassNames);
    }
}
