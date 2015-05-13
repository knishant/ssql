package org.nkumar.ssql.model;

public final class PrecisionScaleBean
{
    @SuppressWarnings("MagicNumber")
    private int precision = 19;
    private boolean scaleSet;
    private int scale = 2;

    public int getPrecision()
    {
        return precision;
    }

    public void setPrecision(int precision)
    {
        this.precision = precision;
    }

    public boolean isScaleSet()
    {
        return scaleSet;
    }

    public int getScale()
    {
        return scale;
    }

    public void setScale(int scale)
    {
        this.scaleSet = true;
        this.scale = scale;
    }

}
