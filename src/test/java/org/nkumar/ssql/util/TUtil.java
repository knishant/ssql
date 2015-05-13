package org.nkumar.ssql.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;

public final class TUtil
{
    private TUtil()
    {
    }

    public static String loadFile(File file)
    {
        try
        {
            try (Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8"))
            {
                StringBuilder builder = new StringBuilder(1024);
                char[] buffer = new char[1024];
                int length;
                while ((length = reader.read(buffer)) != -1)
                {
                    builder.append(buffer, 0, length);
                }
                return builder.toString();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void assertSameContent(String expectedFilePath, String actualFilePath)
    {
        assertSameContent(new File(expectedFilePath), new File(actualFilePath));
    }

    public static void assertSameContent(File expectedFile, File actualFile)
    {
        if (expectedFile == null)
        {
            throw new IllegalArgumentException("expected file cannot be null");
        }
        if (actualFile == null)
        {
            throw new AssertionError("actual file was null");
        }
        if (!actualFile.exists())
        {
            throw new AssertionError("actual file[" + actualFile.getAbsolutePath() + "] does not exist");
        }
        if (!expectedFile.exists())
        {
            try
            {
                Files.move(actualFile.getAbsoluteFile().toPath(), expectedFile.getAbsoluteFile().toPath());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            System.err.println("Expected File [" + expectedFile.getAbsolutePath()
                    + "] does not exist. Renaming actual file to expected file. Review Content");
            return;
        }
        if (expectedFile.length() != actualFile.length())
        {
            throw new AssertionError(
                    "expected file size[" + expectedFile.getAbsolutePath() + ":" + expectedFile.length()
                            + "] is different from actual file size[" + actualFile.getAbsolutePath() + ":"
                            + actualFile.length() + "]");
        }
        String expectedString = loadFile(expectedFile);
        String actualString = loadFile(actualFile);
        if (!expectedString.equals(actualString))
        {
            throw new AssertionError(
                    "Content of expected file [" + expectedFile.getAbsolutePath()
                            + "] is different from content of actual file [" + actualFile.getAbsolutePath() + "]");
        }
    }


    public static void deleteFile(File file)
    {
        if (file.exists())
        {
            if (file.isDirectory())
            {
                File[] files = file.listFiles();
                assert files != null;
                for (File child : files)
                {
                    deleteFile(child);
                }
            }
            file.delete();
        }
    }
}
