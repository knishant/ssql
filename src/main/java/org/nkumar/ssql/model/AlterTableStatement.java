package org.nkumar.ssql.model;

public abstract class AlterTableStatement implements DdlStatement
{
    protected final String tableName;
    private Comment[] comments;

    protected AlterTableStatement(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableName()
    {
        return tableName;
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
