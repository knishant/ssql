package org.nkumar.ssql.model;

import java.util.regex.Pattern;

public final class CreateSequenceStatement extends CreateEntityStatement
{
    private static final Pattern NUMBER = Pattern.compile("[1-9][0-9]*");

    private long startWith;
    private boolean startWithSet;

    private long minValue;
    private boolean minValueSet;

    private String maxValue;
    private boolean maxValueSet;

    private boolean noOrder;

    private boolean noCache;

    private boolean noCycle;

    public CreateSequenceStatement(String name)
    {
        super(name);
    }

    public long getStartWith()
    {
        return startWith;
    }

    public void setStartWith(long startWith)
    {
        this.startWith = startWith;
        this.startWithSet = true;
    }

    public boolean isStartWithSet()
    {
        return startWithSet;
    }

    public long getMinValue()
    {
        return minValue;
    }

    public void setMinValue(long minValue)
    {
        this.minValue = minValue;
        this.minValueSet = true;
    }

    public boolean isMinValueSet()
    {
        return minValueSet;
    }

    public String getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue(String maxValue)
    {
        if (!NUMBER.matcher(maxValue).matches())
        {
            throw new IllegalArgumentException(maxValue + " should contain digits only");
        }
        this.maxValue = maxValue;
        this.maxValueSet = true;
    }

    public boolean isMaxValueSet()
    {
        return maxValueSet;
    }

    public boolean isNoOrder()
    {
        return noOrder;
    }

    public void setNoOrder(boolean noOrder)
    {
        this.noOrder = noOrder;
    }

    public boolean isNoCache()
    {
        return noCache;
    }

    public void setNoCache(boolean noCache)
    {
        this.noCache = noCache;
    }

    public boolean isNoCycle()
    {
        return noCycle;
    }

    public void setNoCycle(boolean noCycle)
    {
        this.noCycle = noCycle;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
