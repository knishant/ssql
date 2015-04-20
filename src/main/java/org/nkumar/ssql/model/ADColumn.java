package org.nkumar.ssql.model;

//Ascendening Descending Column
public final class ADColumn implements SqlVisitable
{
    private String columnName;
    private String ascDesc;

    public ADColumn(String columnName)
    {
        this.columnName = columnName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getAscDesc()
    {
        return ascDesc;
    }

    public void setAscDesc(String ascDesc)
    {
        this.ascDesc = ascDesc.toLowerCase();
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
