package org.nkumar.ssql.model;

public final class CreateSequenceStatement extends CreateEntityStatement
{
    private long startWith;
    private boolean startWithSet;

    private long incrementBy;
    private boolean incrementBySet;

    private long minValue;
    private boolean minValueSet;

    private long maxValue;
    private boolean maxValueSet;

    private boolean cycle;
    private boolean cycleSet;

    private boolean noCacheSet;

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

    public long getIncrementBy()
    {
        return incrementBy;
    }

    public void setIncrementBy(long incrementBy)
    {
        this.incrementBySet = true;
        this.incrementBy = incrementBy;
    }

    public boolean isIncrementBySet()
    {
        return incrementBySet;
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

    public long getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue(long maxValue)
    {
        this.maxValue = maxValue;
        this.maxValueSet = true;
    }

    public boolean isMaxValueSet()
    {
        return maxValueSet;
    }

    public boolean isCycleSet()
    {
        return cycleSet;
    }

    public boolean isCycle()
    {
        return cycle;
    }

    public void setCycle(boolean cycle)
    {
        this.cycleSet = true;
        this.cycle = cycle;
    }

    public boolean isNoCacheSet()
    {
        return noCacheSet;
    }

    public void setNoCacheSet(boolean noCacheSet)
    {
        this.noCacheSet = noCacheSet;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
