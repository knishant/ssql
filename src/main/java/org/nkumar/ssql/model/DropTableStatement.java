package org.nkumar.ssql.model;

public final class DropTableStatement extends DropEntityStatement
{
    private boolean cascadeConstraint;

    public boolean isCascadeConstraint()
    {
        return cascadeConstraint;
    }

    public void setCascadeConstraint(boolean cascadeConstraint)
    {
        this.cascadeConstraint = cascadeConstraint;
    }

    public DropTableStatement(String name)
    {
        super(name);
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
