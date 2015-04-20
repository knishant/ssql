package org.nkumar.ssql.model;

public final class DropTableConstraintStatement extends AlterTableStatement
{
    private final String constraintName;

    public DropTableConstraintStatement(String tableName, String constraintName)
    {
        super(tableName);
        this.constraintName = constraintName;
    }

    public String getConstraintName()
    {
        return constraintName;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
