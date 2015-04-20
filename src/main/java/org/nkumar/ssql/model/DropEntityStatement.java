package org.nkumar.ssql.model;

public abstract class DropEntityStatement implements DdlStatement
{
    protected final String name;
    private Comment[] comments;

    protected DropEntityStatement(String name)
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
}
