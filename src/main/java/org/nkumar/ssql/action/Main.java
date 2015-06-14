package org.nkumar.ssql.action;

import org.nkumar.ssql.util.Util;

import java.io.File;
import java.io.StringReader;
import java.util.Properties;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class Main
{
    public static void main(String[] args) throws Exception
    {
        if (args.length == 0)
        {
            System.err.println("No properties file was specified");
            System.exit(1);
        }
        Properties properties = new Properties();
        File propsFile = new File(args[0]);
        properties.load(new StringReader(Util.readFileAsString(propsFile)));
        String action = properties.getProperty("action");
        if (action == null || action.isEmpty())
        {
            System.err.println("action property not set in the properties file");
            System.exit(1);
        }
        switch (action)
        {
            case "translate":
                handleTranslate(propsFile, properties);
                return;
            default:
                System.err.println("action '" + action + "' is not supported");
                System.exit(1);
        }
    }

    private static void handleTranslate(File propFile, Properties props) throws Exception
    {
        TranslateCommand command = new TranslateCommand();
        String srcDirPath = props.getProperty("srcDir");
        String destDirPath = props.getProperty("destDir");
        command.setSrcDir(new File(propFile.getParentFile(), srcDirPath))
                .setDestDir(new File(propFile.getParentFile(), destDirPath));
        String dialects = props.getProperty("dialects");
        command.addDialects(dialects.split(","));
        command.execute();
    }
}
