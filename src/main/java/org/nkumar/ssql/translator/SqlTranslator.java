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

    /**
     * Create GenericTranslatorSqlVisitor with an instance of the dialect class passed in.
     * @param dialectClassName name of the dialect
     * @return instance of GenericTranslatorSqlVisitor
     * @throws Exception mostly when there is any issue loading or instantiating the dialect class
     */
    static TranslatorSqlVisitor createVisitor(String dialectClassName) throws Exception
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

    /**
     * Translates the sql in the srcFile based on the dialect passed and write the output to the destFile.
     * @param srcFile file containing the input sql
     * @param destFile file where the translated sql is wriiten
     * @param overwrite when false translation will happen only when destFile is non existent
     * or is older than the srcFile
     * @param dialectClassName name of the dialect class
     * @throws Exception
     */
    public static void translateSqlFile(File srcFile, File destFile, boolean overwrite, String dialectClassName)
            throws Exception
    {
        translateSqlFileInternal(srcFile, destFile, overwrite, null, createVisitor(dialectClassName));
    }

    private static List<SqlVisitable> translateSqlFileInternal(File srcFile, File destFile,
            boolean overwrite, List<SqlVisitable> list, TranslatorSqlVisitor visitor)
            throws Exception
    {
        //just a small check to enable incremental builds
        if (!overwrite && isAlreadyTranslated(srcFile, destFile))
        {
            return list;
        }
        //both srcFile and list of SqlVisitable is accepted in the method
        //so that the same list may be used in consecutive calls for translation using different dialect
        if (list == null)
        {
            list = parseSql(Util.readFileAsString(srcFile));
        }
        String generatedSql = translate(list, visitor);
        Util.writeStringToFile(destFile, generatedSql);
        return list;
    }

    //just a small check to enable incremental builds
    private static boolean isAlreadyTranslated(File srcFile, File destFile)
    {
        return destFile.exists() && destFile.isFile() && destFile.lastModified() > srcFile.lastModified();
    }

    private static String translate(Iterable<SqlVisitable> list, TranslatorSqlVisitor visitor)
    {
        for (SqlVisitable sql : list)
        {
            sql.accept(visitor);
        }
        return visitor.getGeneratedSql();
    }

    /**
     * Translates the sql in the file srcDir/srcFileRelativePath
     * and write the output to the file destDir/dialectName/srcFileRelativePath.
     * This is done once for each dialect passed.
     * @param srcDir the root directory containing the input sql file
     * @param destDir the root directory where all the dialect outputs would be written
     * @param srcFileRelativePath path of the input file relative to the srcDir
     * @param overwrite when false translation will happen only when destFile is non existent
     * or is older than the srcFile
     * @param dialectClassNames names of the dialect classes
     * @throws Exception
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void translateSqlFileToMultipleTranslators(File srcDir, File destDir, String srcFileRelativePath,
            boolean overwrite, String... dialectClassNames)
            throws Exception
    {
        List<SqlVisitable> list = null;
        for (String dialectClass : dialectClassNames)
        {
            TranslatorSqlVisitor visitor = createVisitor(dialectClass);
            File dialectDir = new File(destDir, visitor.getDbName());
            if (!dialectDir.exists())
            {
                dialectDir.mkdirs();
            }
            File destFile = new File(dialectDir, srcFileRelativePath);
            File srcFile = new File(srcDir, srcFileRelativePath);
            list = translateSqlFileInternal(srcFile, destFile, overwrite, list, visitor);
        }
    }
}
