package org.nkumar.ssql.dialect;

import java.sql.Types;

public class SQLServer2008Dialect extends SQLServer2005Dialect
{
    public SQLServer2008Dialect()
    {
        this("SQLServer2008");
    }

    protected SQLServer2008Dialect(String dbName)
    {
        super(dbName);
        typeNames.put(Types.DATE, "date");
        typeNames.put(Types.TIME, "time");
        typeNames.put(Types.TIMESTAMP, "datetime2");

        functions.put("current_timestamp", "current_timestamp");
    }
}
