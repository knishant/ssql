package org.nkumar.ssql.model;

public final class Value implements SqlVisitable
{
    private Number numberValue;
    private String stringValue;
    private boolean nullValue;
    private String dateValueFunction;

    public Number getNumberValue()
    {
        return numberValue;
    }

    public void setNumberValue(int numberValue)
    {
        this.numberValue = numberValue;
    }

    public void setNumberValue(String numberValue)
    {
        try
        {
            this.numberValue = new Integer(numberValue);
        }
        catch (NumberFormatException ignore)
        {
            this.numberValue = new Double(numberValue);
        }
    }

    public String getStringValue()
    {
        return stringValue;
    }

    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public boolean isNullValue()
    {
        return nullValue;
    }

    public void setNullValue(boolean nullValue)
    {
        this.nullValue = nullValue;
    }

    public String getDateValueFunction()
    {
        return dateValueFunction;
    }

    public void setDateValueFunction(String dateValueFunction)
    {
        this.dateValueFunction = dateValueFunction.toLowerCase();
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
