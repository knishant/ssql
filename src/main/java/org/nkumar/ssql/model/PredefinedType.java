package org.nkumar.ssql.model;

public final class PredefinedType implements SqlVisitable
{
    private final int type;
    private boolean lengthSet;
    @SuppressWarnings("MagicNumber")
    private long length = 255;
    private boolean precisionScaleSet;
    private PrecisionScaleBean precisionScale = new PrecisionScaleBean();

    public PredefinedType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public boolean isLengthSet()
    {
        return lengthSet;
    }

    public long getLength()
    {
        return length;
    }

    public void setLength(long length)
    {
        this.lengthSet = true;
        this.length = length;
    }

    public boolean isPrecisionScaleSet()
    {
        return precisionScaleSet;
    }

    public PrecisionScaleBean getPrecisionScale()
    {
        return precisionScale;
    }

    public void setPrecisionScale(PrecisionScaleBean precisionScale)
    {
        this.precisionScaleSet = true;
        this.precisionScale = precisionScale;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
