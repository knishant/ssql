package org.nkumar.ssql.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CreateTableStatement extends CreateEntityStatement
{
    private final Map<String, Column> columns = new LinkedHashMap<>();
    private final List<TableConstraint> tableConstraints = new ArrayList<>();

    public CreateTableStatement(String name)
    {
        super(name);
    }

    public List<Column> getColumns()
    {
        return new ArrayList<>(columns.values());
    }

    public Column getColumnByName(String name)
    {
        return columns.get(name);
    }

    public void addColumn(Column col)
    {
        this.columns.put(col.getName(), col);
    }

    public List<TableConstraint> getTableConstraints()
    {
        return tableConstraints;
    }

    public void addTableConstraint(TableConstraint constraint)
    {
        this.tableConstraints.add(constraint);
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
