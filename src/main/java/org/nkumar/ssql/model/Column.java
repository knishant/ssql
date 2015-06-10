package org.nkumar.ssql.model;

public final class Column implements SqlVisitable
{
    public static final int DEFAULT_LENGTH = 255;
    public static final int DEFAULT_PRECISION = 19;
    public static final int DEFAULT_SCALE = 2;

    private final String name;
    private PredefinedType type;
    private Value defaultValue;
    private boolean notNull;

    private boolean identity;

    private Comment[] comments;

    private PlaceHolder typePlaceHolder;

    public Column(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public PredefinedType getType()
    {
        return type;
    }

    public void setType(PredefinedType type)
    {
        this.type = type;
    }

    public boolean isNotNull()
    {
        return notNull;
    }

    public void setNotNull(boolean notNull)
    {
        this.notNull = notNull;
    }

    public boolean isIdentity()
    {
        return identity;
    }

    public void setIdentity(boolean identity)
    {
        //TODO does identity implies not null
        this.identity = identity;
    }

    public Value getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(Value defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public PlaceHolder getTypePlaceHolder()
    {
        return typePlaceHolder;
    }

    public void setTypePlaceHolder(PlaceHolder typePlaceHolder)
    {
        this.typePlaceHolder = typePlaceHolder;
    }

    public Comment[] getComments()
    {
        return comments;
    }

    public void setComments(Comment[] comments)
    {
        this.comments = comments;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}
