package org.nkumar.ssql.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class InsertStatement implements DmlStatement
{
    private String tableName;
    private final Map<String, Value> columns = new LinkedHashMap<>();

    public InsertStatement(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName.toUpperCase();
    }

    public void setColumnValues(List columnNames, List columnValues)
    {
        for (int i = 0; i < columnNames.size(); i++)
        {
            columns.put(((String) columnNames.get(i)).toUpperCase(),
                    (Value) columnValues.get(i));
        }
    }

    public Map<String, Value> getColumnValues()
    {
        return columns;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
