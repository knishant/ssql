package org.nkumar.ssql.translater;

import java.sql.Types;

public class MySQL5InnoDBTranslatorSqlVisitor extends GenericTranslatorSqlVisitor
{
    @SuppressWarnings("MagicNumber")
    public MySQL5InnoDBTranslatorSqlVisitor()
    {
        super("MySQL5InnoDB");
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
        typeNames.put(Types.VARCHAR, 65535, "varchar($l)");
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
    protected boolean needsSpaceAfterDoubleDashComment()
    {
        return true;
    }

    @Override
    protected String getTableTypeString()
    {
        return " ENGINE=InnoDB";
    }
}
