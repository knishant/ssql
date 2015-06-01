package org.nkumar.ssql.action;

import org.nkumar.ssql.translator.SqlTranslator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class TranslateCommand
{
    private File srcDir;
    private File destDir;
    private final List<String> dialects = new ArrayList<>();
    private final Map<String, String> customDialectMappings = new HashMap<>();

    public void setSrcDir(File srcDir)
    {
        try
        {
            this.srcDir = srcDir.getCanonicalFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setDestDir(File destDir)
    {
        try
        {
            this.destDir = destDir.getCanonicalFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addDialect(String dialect)
    {
        dialects.add(dialect);
    }

    public void addCustomDialectMapping(String dialectName, String dialectClassName)
    {
        customDialectMappings.put(dialectName, dialectClassName);
    }

    public void execute() throws Exception
    {
        System.out.println("srcDir = " + srcDir.getAbsolutePath());
        System.out.println("destDir = " + destDir.getAbsolutePath());
        String[] dialectClassNames = new String[dialects.size()];
        for (int i = 0; i < dialects.size(); i++)
        {
            dialectClassNames[i] = getDialectClassName(dialects.get(i));
        }
        SqlTranslator.translateSqlDirToMultipleTranslators(srcDir, destDir,dialectClassNames);
    }

    private String getDialectClassName(String dialectName)
    {
        String className = "org.nkumar.ssql.dialect." + dialectName + "Dialect";
        if (checkClassExists(className))
        {
            return className;
        }
        className = customDialectMappings.get(dialectName);
        if (checkClassExists(className))
        {
            return className;
        }
        throw new IllegalArgumentException("No dialect classname defined for " + dialectName);
    }

    private static boolean checkClassExists(String className)
    {
        if (className == null)
        {
            return false;
        }
        try
        {
            Class.forName(className);
            return true;
        }
        catch (ClassNotFoundException ignore)
        {
            return false;
        }
    }
}
