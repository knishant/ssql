package org.nkumar.ssql.model;

public abstract class CreateEntityStatement implements DdlStatement
{
    protected final String name;
    protected Comment[] comments;
    protected PlaceHolder openingPlaceHolder;
    protected PlaceHolder closingPlaceHolder;

    protected CreateEntityStatement(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Comment[] getComments()
    {
        return comments;
    }

    public void setComments(Comment[] comments)
    {
        this.comments = comments;
    }

    public PlaceHolder getOpeningPlaceHolder()
    {
        return openingPlaceHolder;
    }

    public void setOpeningPlaceHolder(PlaceHolder openingPlaceHolder)
    {
        this.openingPlaceHolder = openingPlaceHolder;
    }

    public PlaceHolder getClosingPlaceHolder()
    {
        return closingPlaceHolder;
    }

    public void setClosingPlaceHolder(PlaceHolder closingPlaceHolder)
    {
        this.closingPlaceHolder = closingPlaceHolder;
    }
}
