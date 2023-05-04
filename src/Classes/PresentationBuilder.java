package Classes;

import Classes.SlideItems.BitmapItem;
import Classes.SlideItems.TextItem;
import Enumerations.Attribute;
import Enumerations.Style;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class PresentationBuilder {

    /**
     * Text of messages
     */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";

    public static Presentation buildPresentation(Element doc) {
        int slideNumber, itemNumber, max = 0, maxItems = 0;
        Presentation presentation = new Presentation();
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

        return presentation;
    }

    protected static void parseSlideItem(Slide slide, Element item) {
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

    protected static String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    public static Presentation buildDemoPresentation() {
        Presentation presentation = new Presentation();
        presentation.setTitle("Demo Presentation");
        Slide slide;
        slide = new Slide();
        slide.setTitle("JabberPoint");
        slide.append(Style.level1, "The Java prestentation tool");
        slide.append(Style.level2, "Copyright (c) 1996-2000: Ian Darwin");
        slide.append(Style.level2, "Copyright (c) 2000-now:");
        slide.append(Style.level2, "Gert Florijn and Sylvia Stuurman");
        slide.append(Style.level4, "Calling Jabberpoint without a filename");
        slide.append(Style.level4, "will show this presentation");
        slide.append(Style.level1, "Navigate:");
        slide.append(Style.level3, "Next slide: PgDn or Enter");
        slide.append(Style.level3, "Previous slide: PgUp or up-arrow");
        slide.append(Style.level3, "Quit: q or Q");
        presentation.append(slide);

        slide = new Slide();
        slide.setTitle("Demonstration of levels and styles");
        slide.append(Style.level1, "Level 1");
        slide.append(Style.level2, "Level 2");
        slide.append(Style.level1, "Again level 1");
        slide.append(Style.level1, "Level 1 has style number 1");
        slide.append(Style.level2, "Level 2 has style number 2");
        slide.append(Style.level3, "This is how level 3 looks like");
        slide.append(Style.level4, "And this is level 4");
        presentation.append(slide);

        slide = new Slide();
        slide.setTitle("The third slide");
        slide.append(Style.level1, "To open a new presentation,");
        slide.append(Style.level2, "use File->Open from the menu.");
        slide.append(Style.level1, " ");
        slide.append(Style.level1, "This is the end of the presentation.");
        slide.append(new BitmapItem(Style.level1, "JabberPoint.jpg"));
        presentation.append(slide);

        return presentation;
    }
}
