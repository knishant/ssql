package org.nkumar.ssql.translater;

import java.sql.Types;

public class MySQL5TranslatorSqlVisitor extends GenericTranslatorSqlVisitor
{
    public MySQL5TranslatorSqlVisitor()
    {
        this("MySQL5");
    }

    protected MySQL5TranslatorSqlVisitor(String dbName)
    {
        super(dbName);
        typeNames.put(Types.TIMESTAMP, "datetime");
        typeNames.put(Types.VARBINARY, "longblob");
        typeNames.put(Types.VARBINARY, 16777215, "mediumblob");
        typeNames.put(Types.VARBINARY, 65535, "blob");
        typeNames.put(Types.VARBINARY, 255, "tinyblob");
        typeNames.put(Types.BINARY, "binary($l)");
        typeNames.put(Types.LONGVARBINARY, "longblob");
        typeNames.put(Types.LONGVARBINARY, 16777215, "mediumblob");
        typeNames.put(Types.NUMERIC, "decimal($p,$s)");
        typeNames.put(Types.BLOB, "longblob");
        typeNames.put(Types.CLOB, "longtext");
        registerVarcharTypes();
    }

    protected void registerVarcharTypes()
    {
        typeNames.put(Types.VARCHAR, "longtext");
        typeNames.put(Types.VARCHAR, 65535, "varchar($l)");
        typeNames.put(Types.LONGVARCHAR, "longtext");
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
    public boolean supportsCascadeDelete()
    {
        return false;
    }
}
