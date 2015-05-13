package org.nkumar.ssql.translater;

import java.sql.Types;

public class SQLServer2000Dialect extends Dialect
{
    public SQLServer2000Dialect()
    {
        this("SQLServer2000");
    }

    @SuppressWarnings("MagicNumber")
    protected SQLServer2000Dialect(String dbName)
    {
        super(dbName);
        typeNames.put(Types.BINARY, "binary($l)");
        typeNames.put(Types.BIT, "tinyint");
        typeNames.put(Types.BOOLEAN, "bit");
        typeNames.put(Types.BIGINT, "numeric(19,0)");
        typeNames.put(Types.TINYINT, "smallint");
        typeNames.put(Types.DATE, "datetime");
        typeNames.put(Types.TIME, "datetime");
        typeNames.put(Types.TIMESTAMP, "datetime");
        typeNames.put(Types.BLOB, "image");
        typeNames.put(Types.CLOB, "text");

        typeNames.put(Types.VARBINARY, "image");
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
