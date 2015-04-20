package org.nkumar.ssql.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

public final class Util
{
    private Util()
    {
    }

    public static String padToSize(String str, int size)
    {
        if (str.length() >= size)
        {
            return str;
        }
        StringBuilder builder = new StringBuilder(size);
        builder.append(str);
        for (int i = str.length(); i < size; i++)
        {
            builder.append(" ");
        }
        return builder.toString();
    }

    public static String trimToNull(String str)
    {
        if (str == null)
        {
            return null;
        }
        str = str.trim();
        return (str.isEmpty()) ? null : str;
    }

    public static String toTrimLowerCase(String str)
    {
        str = trimToNull(str);
        if (str == null)
        {
            return null;
        }
        return str.toLowerCase();
    }

    public static boolean isEmptyCollection(Collection col)
    {
        return col == null || col.isEmpty();
    }

    public static void commafy(Iterable col, StringBuilder builder)
    {
        for (Iterator itr = col.iterator(); itr.hasNext(); )
        {
            Object obj = itr.next();
            builder.append(obj.toString());
            if (itr.hasNext())
            {
                builder.append(", ");
            }
        }
    }

    public static String readFileAsString(File file) throws IOException
    {
        Reader in = new InputStreamReader(new FileInputStream(file), "UTF-8");
        char[] buffer = new char[1024];
        int read;
        StringBuilder builder = new StringBuilder(8 * 1024);
        try
        {
            while ((read = in.read(buffer)) != -1)
            {
                builder.append(buffer, 0, read);
            }
        }
        finally
        {
            closeStreamQuietly(in);
        }
        return builder.toString();
    }

    public static void writeStringToFile(File file, String str) throws IOException
    {
        Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        try
        {
            out.write(str);
        }
        finally
        {
            closeStreamQuietly(out);
        }
    }

    public static void closeStreamQuietly(Closeable closeable)
    {
        try
        {
            if (closeable != null)
            {
                closeable.close();
            }
        }
        catch (IOException ignore)
        {
        }
    }

    public static String getDialectShortName(String dialectClass)
    {
        int index = dialectClass.lastIndexOf('.');
        if (index == -1)
        {
            return dialectClass;
        }
        return dialectClass.substring(index + 1);
    }
}
