package org.nkumar.ssql.translater;

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

}
