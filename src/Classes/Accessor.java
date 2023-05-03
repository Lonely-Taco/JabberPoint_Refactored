package Classes;

import Classes.SlideItems.BitmapItem;
import Classes.SlideItems.TextItem;
import Enumerations.Attribute;
import Enumerations.Style;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * <p>An Classes.Accessor makes it possible to read and write data
 * for a presentation.</p>
 * <p>Non-abstract subclasses should implement the load and save methods.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Accessor {

    /**
     * Text of messages
     */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";


    protected void accessFile(Presentation presentation, String filename) {
        int slideNumber, itemNumber, max = 0, maxItems = 0;
        Element doc = FileManager.loadFile(filename);
        if (doc == null) {
            return;
        }

        presentation.setTitle(getTitle(doc, Attribute.showtitle.name()));
        NodeList slides = doc.getElementsByTagName(Attribute.slide.name());
        max = slides.getLength();
        for (slideNumber = 0; slideNumber < max; slideNumber++) {
            Element xmlSlide = (Element) slides.item(slideNumber);
            Slide slide = new Slide();
            slide.setTitle(getTitle(xmlSlide, Attribute.title.name()));
            presentation.append(slide);

            NodeList slideItems = xmlSlide.getElementsByTagName(Attribute.item.name());
            maxItems = slideItems.getLength();
            for (itemNumber = 0; itemNumber < maxItems; itemNumber++) {
                Element item = (Element) slideItems.item(itemNumber);
                parseSlideItem(slide, item);
            }
        }
    }

    protected String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    protected void parseSlideItem(Slide slide, Element item) {
        Style level = Style.level0; // default
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(Attribute.level.name()).getTextContent();
        if (leveltext != null) {
            try {
                level = Style.valueOf(leveltext);
            } catch (NumberFormatException x) {
                System.err.println(NFE);
            }
        }
        String type = attributes.getNamedItem(Attribute.kind.name()).getTextContent();
        if (Attribute.text.name().equals(type)) {
            slide.append(new TextItem(level, item.getTextContent()));
        }
        else {
            if (Attribute.image.name().equals(type)) {
                slide.append(new BitmapItem(level, item.getTextContent()));
            }
            else {
                System.err.println(UNKNOWNTYPE);
            }
        }
    }
}
