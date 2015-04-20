package org.nkumar.ssql.model;

import org.nkumar.ssql.util.Util;

import java.util.List;

public final class ForeignKeyTableConstraint extends TableConstraint
{
    private final List<String> columnList;
    private final References references;

    public ForeignKeyTableConstraint(List<String> columnList, References references)
    {
        super(FOREIGN_KEY);
        if (Util.isEmptyCollection(columnList))
        {
            throw new AssertionError("column list specified was empty");
        }
        this.columnList = columnList;
        if (references == null)
        {
            throw new AssertionError("references specified was empty");
        }
        this.references = references;
    }

    public List<String> getColumnList()
    {
        return columnList;
    }

    public References getReferences()
    {
        return references;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
