package org.nkumar.ssql.dialect;

public final class IdentityDialect extends Dialect
{
    public IdentityDialect()
    {
        super("Identity");
    }

    @Override
    public String getCascadeConstraintsString()
    {
        return "cascade constraints";
    }

    @Override
    public boolean isIdentity()
    {
        return true;
    }

    @Override
    public boolean supportsSequences()
    {
        return true;
    }
}
