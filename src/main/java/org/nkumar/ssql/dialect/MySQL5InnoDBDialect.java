package org.nkumar.ssql.dialect;

import java.sql.Types;

public class MySQL5InnoDBDialect extends Dialect
{
    public MySQL5InnoDBDialect()
    {
        this("MySQL5InnoDB");
    }

    @SuppressWarnings("MagicNumber")
    protected MySQL5InnoDBDialect(String dbName)
    {
        super(dbName);
        typeNames.put(Types.BOOLEAN, "bit");
        typeNames.put(Types.TIMESTAMP, "datetime");
        typeNames.put(Types.VARBINARY, "longblob");
        typeNames.put(Types.VARBINARY, 16777215, "mediumblob");
        typeNames.put(Types.VARBINARY, 65535, "blob");
        typeNames.put(Types.VARBINARY, 255, "tinyblob");
        typeNames.put(Types.BINARY, "binary($l)");
        typeNames.put(Types.NUMERIC, "decimal($p,$s)");
        typeNames.put(Types.BLOB, "longblob");
        typeNames.put(Types.CLOB, "longtext");
        typeNames.put(Types.VARCHAR, "longtext");
        //following are different from hibernate
        //this is to take care of the row limit
        typeNames.put(Types.VARCHAR, 8000, "varchar($l)");
        typeNames.put(Types.NVARCHAR, "longtext");
        typeNames.put(Types.NVARCHAR, 2000, "nvarchar($l)");
    }

    @Override
    public char closeQuote()
    {
        return '`';
    }

    @Override
    public char openQuote()
    {
        return '`';
    }

    @Override
    public boolean supportsIfExistsBeforeTableName()
    {
        return true;
    }

    @Override
    public boolean needsSpaceAfterDoubleDashComment()
    {
        return true;
    }

    @Override
    public String getTableTypeString()
    {
        return " ENGINE=InnoDB";
    }

    @Override
    public boolean supportsDefaultCurrentDate()
    {
        return false;
    }

    @Override
    public boolean supportsDefaultCurrentTime()
    {
        return false;
    }
}
