package org.nkumar.ssql.translater;

public final class IdentityTranslatorSqlVisitor extends GenericTranslatorSqlVisitor
{
    public IdentityTranslatorSqlVisitor()
    {
        super("Identity");
    }

    @Override
    protected String getCascadeConstraintsString()
    {
        return "cascade constraints";
    }

}
