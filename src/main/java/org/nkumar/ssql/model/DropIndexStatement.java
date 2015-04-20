package org.nkumar.ssql.model;

public final class DropIndexStatement extends DropEntityStatement
{
    public DropIndexStatement(String name)
    {
        super(name);
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
