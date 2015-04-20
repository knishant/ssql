package org.nkumar.ssql.model;

public final class DropSequenceStatement extends DropEntityStatement
{
    public DropSequenceStatement(String name)
    {
        super(name);
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
