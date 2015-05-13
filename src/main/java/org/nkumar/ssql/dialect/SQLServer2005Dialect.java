package org.nkumar.ssql.dialect;

import java.sql.Types;

public class SQLServer2005Dialect extends Dialect
{
    public SQLServer2005Dialect()
    {
        this("SQLServer2005");
    }

    @SuppressWarnings("MagicNumber")
    protected SQLServer2005Dialect(String dbName)
    {
        super(dbName);
        typeNames.put(Types.BINARY, "binary($l)");
        typeNames.put(Types.BOOLEAN, "bit");
//        typeNames.put(Types.TINYINT, "smallint");
        typeNames.put(Types.DATE, "datetime");
        typeNames.put(Types.TIME, "datetime");
        typeNames.put(Types.TIMESTAMP, "datetime");
        typeNames.put(Types.BLOB, "varbinary(MAX)");
        typeNames.put(Types.CLOB, "varchar(MAX)");

        typeNames.put(Types.VARBINARY, "varbinary(MAX)");
        typeNames.put(Types.VARBINARY, 8000, "varbinary($l)");

        functions.put("current_date", "getdate()");
        functions.put("current_time", "getdate()");
        functions.put("current_timestamp", "getdate()");

        addKeywords("top");
    }

    @Override
    public char closeQuote()
    {
        return ']';
    }

    @Override
    public char openQuote()
    {
        return '[';
    }
}
