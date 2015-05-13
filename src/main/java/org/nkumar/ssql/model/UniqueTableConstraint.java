package org.nkumar.ssql.model;

import org.nkumar.ssql.util.Util;

import java.util.List;

public final class UniqueTableConstraint extends TableConstraint
{
    private final List<String> columnList;

    public UniqueTableConstraint(String keytype, List<String> columnList)
    {
        super(keytype);
        assert PRIMARY_KEY.equals(type) || UNIQUE.equals(type);
        assert !Util.isEmptyCollection(columnList);
        this.columnList = columnList;
    }

    public List<String> getColumnList()
    {
        return columnList;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
