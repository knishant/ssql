package org.nkumar.ssql.model;

public final class AddColumnStatement extends AlterTableStatement
{
    private final Column column;

    public AddColumnStatement(String tableName, Column column)
    {
        super(tableName);
        this.column = column;
    }

    public Column getColumn()
    {
        return column;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
