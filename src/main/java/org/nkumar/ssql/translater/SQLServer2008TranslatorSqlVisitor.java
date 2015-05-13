package org.nkumar.ssql.translater;

import java.sql.Types;

public class SQLServer2008TranslatorSqlVisitor extends SQLServer2005TranslatorSqlVisitor
{
    public SQLServer2008TranslatorSqlVisitor()
    {
        super("SQLServer2008");
        typeNames.put(Types.DATE, "date");
        typeNames.put(Types.TIME, "time");
        typeNames.put(Types.TIMESTAMP, "datetime2");

        functions.put("current_timestamp", "current_timestamp");

    }
}
