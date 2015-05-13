package org.nkumar.ssql.translater;

import java.sql.Types;

public final class PostgreSQL9TranslatorSqlVisitor extends GenericTranslatorSqlVisitor
{
    public PostgreSQL9TranslatorSqlVisitor()
    {
        super("PostgreSQL9");
        registerPostgreSQLTypes();
    }

    private void registerPostgreSQLTypes()
    {
        typeNames.put(Types.BIT, "bool");
        typeNames.put(Types.TINYINT, "smallint");
        typeNames.put(Types.FLOAT, "real");
        typeNames.put(Types.DOUBLE, "double precision");
        typeNames.put(Types.VARBINARY, "bytea");
        typeNames.put(Types.BINARY, "bytea");
        typeNames.put(Types.LONGVARCHAR, "text");
        typeNames.put(Types.LONGVARBINARY, "bytea");
        typeNames.put(Types.CLOB, "text");
        typeNames.put(Types.BLOB, "oid");
        typeNames.put(Types.OTHER, "uuid");
    }

    @Override
    public String getCascadeConstraintsString()
    {
        return " cascade";
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

}
