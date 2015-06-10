package org.nkumar.ssql.dialect;

public class Oracle12cDialect extends Oracle9iDialect
{
    public Oracle12cDialect()
    {
        this("Oracle12c");
    }

    protected Oracle12cDialect(String dbName)
    {
        super(dbName);
    }

    @Override
    public boolean supportsIdentityColumns()
    {
        return true;
    }

    @Override
    public String getIdentityColumnString(int type)
    {
        return "generated as identity";
    }

    @Override
    public boolean hasDataTypeInIdentityColumn()
    {
        return true;
    }
}
