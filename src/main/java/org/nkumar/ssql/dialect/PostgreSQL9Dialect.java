package org.nkumar.ssql.dialect;

import java.sql.Types;

public class PostgreSQL9Dialect extends Dialect
{
    public PostgreSQL9Dialect()
    {
        this("PostgreSQL9");
    }

    protected PostgreSQL9Dialect(String dbName)
    {
        super(dbName);
        typeNames.put(Types.BIT, "bool");
//        typeNames.put(Types.TINYINT, "smallint");
        typeNames.put(Types.INTEGER, "integer");
        typeNames.put(Types.FLOAT, "real");
        typeNames.put(Types.VARBINARY, "bytea");
        typeNames.put(Types.BINARY, "bytea");
        typeNames.put(Types.CLOB, "text");
        typeNames.put(Types.BLOB, "oid");

        typeNames.put(Types.NCHAR, "char($l)");
        typeNames.put(Types.NVARCHAR, "varchar($l)");
        typeNames.put(Types.NCLOB, "clob");
    }

    @Override
    public String getCascadeConstraintsString()
    {
        return "cascade";
    }

    @Override
    public boolean supportsSequences()
    {
        return true;
    }

    @Override
    public boolean supportsIfExistsBeforeTableName()
    {
        return true;
    }

    @Override
    public String getNoCacheSequenceString()
    {
        return "";
    }

    @Override
    public boolean supportsIdentityColumns()
    {
        return true;
    }

    @Override
    public boolean hasDataTypeInIdentityColumn()
    {
        return false;
    }

    @Override
    public String getIdentityColumnString(int type) {
        return type==Types.BIGINT ?
                "bigserial not null" :
                "serial not null";
    }

}
