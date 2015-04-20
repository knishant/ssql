package org.nkumar.ssql.model;


public final class DropColumnStatement extends AlterTableStatement
{
    private final String columnName;

    public DropColumnStatement(String tableName, String columnName)
    {
        super(tableName);
        this.columnName = columnName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
