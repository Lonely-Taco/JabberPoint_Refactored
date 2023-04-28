package Enumerations;

import java.awt.*;

public enum Style {
    level0(0, Color.red, 48, 20),
    level1(20, Color.blue, 40, 10),
    level2(50, Color.black, 36, 10),
    level3(70, Color.black, 30, 10),
    level4(90, Color.black, 24, 10);

    private static final String FONTNAME = "Helvetica";
    public int indent;
    public Color color;
    public Font font;
    public int fontSize;
    public int leading;

    Style(int indent, Color color, int points, int leading) {
        this.indent = indent;
        this.color = color;
        font = new Font(FONTNAME, Font.BOLD, fontSize = points);
        this.leading = leading;
    }

    public String toString() {
        return "["+ indent + "," + color + "; " + fontSize + " on " + leading +"]";
    }

    public Font getFont(float scale) {
        return font.deriveFont(fontSize * scale);
    }
}
