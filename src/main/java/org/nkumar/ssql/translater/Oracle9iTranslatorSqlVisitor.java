package org.nkumar.ssql.translater;

import java.sql.Types;

public class Oracle9iTranslatorSqlVisitor extends GenericTranslatorSqlVisitor
{
    public Oracle9iTranslatorSqlVisitor()
    {
        super("Oracle9i");
        registerCharacterTypeMappings();
        registerNumericTypeMappings();
        registerDateTimeTypeMappings();
        registerLargeObjectTypeMappings();

        functions.put("current_time", "current_timestamp");
    }

    protected void registerCharacterTypeMappings()
    {
        typeNames.put(Types.CHAR, "char($l char)");
        typeNames.put(Types.VARCHAR, 4000, "varchar2($l char)");
        typeNames.put(Types.VARCHAR, "long");
    }

    protected void registerNumericTypeMappings()
    {
        typeNames.put(Types.BIT, "number(1,0)");
        typeNames.put(Types.BIGINT, "number(19,0)");
        typeNames.put(Types.SMALLINT, "number(5,0)");
        typeNames.put(Types.TINYINT, "number(3,0)");
        typeNames.put(Types.INTEGER, "number(10,0)");

        typeNames.put(Types.FLOAT, "float");
        typeNames.put(Types.DOUBLE, "double precision");
        typeNames.put(Types.NUMERIC, "number($p,$s)");
        typeNames.put(Types.DECIMAL, "number($p,$s)");

        typeNames.put(Types.BOOLEAN, "number(1,0)");
    }

    protected void registerDateTimeTypeMappings()
    {
        typeNames.put(Types.DATE, "date");
        typeNames.put(Types.TIME, "date");
        typeNames.put(Types.TIMESTAMP, "timestamp");
    }

    protected void registerLargeObjectTypeMappings()
    {
        typeNames.put(Types.BINARY, 2000, "raw($l)");
        typeNames.put(Types.BINARY, "long raw");

        typeNames.put(Types.VARBINARY, 2000, "raw($l)");
        typeNames.put(Types.VARBINARY, "long raw");

        typeNames.put(Types.BLOB, "blob");
        typeNames.put(Types.CLOB, "clob");

        typeNames.put(Types.LONGVARCHAR, "long");
        typeNames.put(Types.LONGVARBINARY, "long raw");
    }

    @Override
    public String getAddColumnString()
    {
        return "add";
    }

    @Override
    public String getCascadeConstraintsString()
    {
        return " cascade constraints";
    }

    @Override
    public boolean supportsSequences()
    {
        return true;
    }
}
