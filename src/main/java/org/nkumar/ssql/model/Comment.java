package org.nkumar.ssql.model;

public final class Comment
{
    private String comment;
    private int kind;

    public Comment(String comment, int kind)
    {
        this.comment = comment;
        this.kind = kind;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public int getKind()
    {
        return kind;
    }

    public void setKind(int kind)
    {
        this.kind = kind;
    }
}
