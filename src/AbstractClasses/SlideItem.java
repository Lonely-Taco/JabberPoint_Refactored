package AbstractClasses;

import Enumerations.Style;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * <p>The abstract class for items on a slide.<p>
 * <p>All SlideItems have drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class SlideItem {
    private Style style;

    public SlideItem(Style style) {
        this.style = style;
    }

    public SlideItem() {
        this(Style.level0);
    }


    //Returns the level
    public Style getStyle() {
        return style;
    }

    //Returns the bounding box
    public abstract Rectangle getBoundingBox(
            Graphics g,
            ImageObserver observer, float scale, Style style
    );

    //Draws the item
    public abstract void draw(
            int x, int y, float scale,
            Graphics g, Style style, ImageObserver observer
    );
}
