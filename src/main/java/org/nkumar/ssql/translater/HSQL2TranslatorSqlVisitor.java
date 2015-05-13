package org.nkumar.ssql.translater;

import java.sql.Types;

public class HSQL2TranslatorSqlVisitor extends GenericTranslatorSqlVisitor
{
    public HSQL2TranslatorSqlVisitor()
    {
        this("HSQL2");
    }

    protected HSQL2TranslatorSqlVisitor(String dbName)
    {
        super(dbName);
        typeNames.put(Types.BINARY, "binary($l)");

        typeNames.put(Types.DECIMAL, "decimal($p,$s)");
        typeNames.put(Types.VARBINARY, "varbinary($l)");
        typeNames.put(Types.DOUBLE, "double");

    }

    @Override
    public boolean supportsSequences()
    {
        return true;
    }
}
