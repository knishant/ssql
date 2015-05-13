package org.nkumar.ssql.translater;

import java.util.HashMap;
import java.util.Map;

final class Mode
{
    private final Map<String, Object> modes = new HashMap<>();

    public Object getMode(String key)
    {
        return modes.get(key);
    }

    public void setMode(String key, Object value)
    {
        modes.put(key, value);
    }

    public void unsetMode(String key)
    {
        modes.remove(key);
    }

    public boolean isModeSet(String key)
    {
        return Boolean.TRUE.equals(modes.get(key));
    }
}
