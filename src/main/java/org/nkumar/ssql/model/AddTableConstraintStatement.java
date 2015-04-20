package org.nkumar.ssql.model;

public final class AddTableConstraintStatement extends AlterTableStatement
{
    private final TableConstraint tableConstraint;

    public AddTableConstraintStatement(String tableName, TableConstraint tc)
    {
        super(tableName);
        this.tableConstraint = tc;
    }

    public TableConstraint getTableConstraint()
    {
        return tableConstraint;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
