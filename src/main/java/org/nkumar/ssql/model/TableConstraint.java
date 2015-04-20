package org.nkumar.ssql.model;

import org.nkumar.ssql.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked"})
public abstract class TableConstraint implements SqlVisitable
{
    public static final String UNIQUE = "unique";
    public static final String PRIMARY_KEY = "primary";
    public static final String FOREIGN_KEY = "foreign";

    public static final List TYPES;

    static
    {
        List list = new ArrayList();
        list.add(PRIMARY_KEY);
        list.add(UNIQUE);
        list.add(FOREIGN_KEY);
        TYPES = Collections.unmodifiableList(list);
    }

    protected String constraintName;
    protected final String type;

    protected TableConstraint(String type)
    {
        type = Util.toTrimLowerCase(type);
        assert TYPES.contains(type);
        this.type = type;
    }

    public String getConstraintName()
    {
        return constraintName;
    }

    public String getType()
    {
        return type;
    }

    public void setConstraintName(String constraintName)
    {
        this.constraintName = Util.trimToNull(constraintName);
    }
}