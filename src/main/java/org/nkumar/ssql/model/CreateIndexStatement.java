package org.nkumar.ssql.model;

import java.util.List;

public final class CreateIndexStatement extends CreateEntityStatement
{
    private String tableName;
    private final boolean unique;
    private List<ADColumn> columns;

    public CreateIndexStatement(String name, boolean unique)
    {
        super(name);
        this.unique = unique;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public boolean isUnique()
    {
        return unique;
    }

    public List<ADColumn> getColumns()
    {
        return columns;
    }

    public void setColumns(List<ADColumn> columns)
    {
        this.columns = columns;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
