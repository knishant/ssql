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

    private void registerCharacterTypeMappings()
    {
        typeNames.put(Types.CHAR, "char($l char)");
        typeNames.put(Types.VARCHAR, 4000, "varchar2($l char)");
        typeNames.put(Types.VARCHAR, "long");
    }

    private void registerNumericTypeMappings()
    {
        typeNames.put(Types.BIT, "number(1,0)");
        typeNames.put(Types.BIGINT, "number(19,0)");
        typeNames.put(Types.SMALLINT, "number(5,0)");
        typeNames.put(Types.TINYINT, "number(3,0)");
        typeNames.put(Types.INTEGER, "number(10,0)");

        typeNames.put(Types.NUMERIC, "number($p,$s)");
//        typeNames.put(Types.DECIMAL, "number($p,$s)");

        typeNames.put(Types.BOOLEAN, "number(1,0)");
    }

    private void registerDateTimeTypeMappings()
    {
        typeNames.put(Types.TIME, "date");
    }

    private void registerLargeObjectTypeMappings()
    {
        typeNames.put(Types.BINARY, 2000, "raw($l)");
        typeNames.put(Types.BINARY, "long raw");

        typeNames.put(Types.VARBINARY, 2000, "raw($l)");
        typeNames.put(Types.VARBINARY, "long raw");

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