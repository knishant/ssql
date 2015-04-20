package org.nkumar.ssql.model;

import org.nkumar.ssql.util.Util;

import java.util.List;

public final class References implements SqlVisitable
{
    private final String tableName;
    private final List<String> columnNames;
    private final boolean onDeleteCascade;

    public References(String tableName, List<String> columnNames, boolean onDeleteCascade)
    {
        
        assert tableName != null;
        this.tableName = tableName;
        assert !Util.isEmptyCollection(columnNames);
        this.columnNames = columnNames;
        this.onDeleteCascade = onDeleteCascade;
    }

    public String getTableName()
    {
        return tableName;
    }

    public List<String> getColumnNames()
    {
        return columnNames;
    }

    public boolean isOnDeleteCascade()
    {
        return onDeleteCascade;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
