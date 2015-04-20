package org.nkumar.ssql.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class PlaceHolder implements SqlVisitable
{
    public static final String LITERAL_TYPE = "L";
    private static final String PREFIX = "/*{L{";
    private static final String SUFFIX = "}}*/";
    private static final String ANY_DIALECT = "any";

    private final String type;

    private final Map<String, String> map;

    public PlaceHolder(String type, Map<String, String> map)
    {
        this.type = type;
        this.map = Collections.unmodifiableMap(map);
    }

    public static PlaceHolder createPlaceHolder(String token)
    {
        if (!token.startsWith(PREFIX) || !token.endsWith(SUFFIX))
        {
            throw new AssertionError("illegal token passed " + token);
        }
        String content = token.substring(PREFIX.length(), token.length() - SUFFIX.length()).trim();
        String[] dbDetails = content.split("[|]");
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String dbDetail : dbDetails)
        {
            dbDetail = dbDetail.trim();
            if (dbDetail.isEmpty())
            {
                continue;
            }
            String[] split = dbDetail.split("[:]");
            if (split.length != 2)
            {
                throw new IllegalArgumentException("placeholder is not formatted correctly - " + token);
            }
            String dialectName = split[0].trim().toLowerCase();
            String literal = split[1];
            if (dialectName.isEmpty())
            {
                throw new IllegalArgumentException("placeholder is not formatted correctly - " + token);
            }
            map.put(dialectName, literal);
        }
        return new PlaceHolder(LITERAL_TYPE, map);
    }

    public String getType()
    {
        return type;
    }

    public Map<String, String> getMap()
    {
        return map;
    }

    public String getLiteral(String dialectName)
    {
        String literal = map.get(dialectName.toLowerCase());
        if (literal != null)
        {
            return literal;
        }
        literal = map.get(ANY_DIALECT);
        return literal;
    }

    @Override
    public void accept(SqlVisitor visitor)
    {
        visitor.visit(this);
    }
}