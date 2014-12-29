package net.sourceforge.gator.ui;

import java.awt.Font;

import java.util.HashMap;
import java.util.Map;

public class FontRegistry
{
    private Map fonts;

    public FontRegistry()
    {
        fonts = new HashMap();
    }

    public void add(String name, Font font)
    {
        fonts.put(name, font);
    }

    public void add(String name, String fontName, int style, int size)
    {
        Font font = new Font(fontName, style, size);

        fonts.put(name, font);
    }

    public Font get(String name)
        throws UnknownFontException
    {
        Font font = (Font) fonts.get(name);

        if (font == null) {
            throw new UnknownFontException(name);
        }

        return font;
    }
}
