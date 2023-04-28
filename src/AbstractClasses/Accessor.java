package AbstractClasses;

import Classes.BitmapItem;
import Classes.Presentation;
import Classes.Slide;
import Classes.TextItem;
import Enumerations.Attribute;
import Enumerations.Style;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * <p>An AbstractClasses.Accessor makes it possible to read and write data
 * for a presentation.</p>
 * <p>Non-abstract subclasses should implement the load and save methods.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class Accessor {

    /**
     * Default API to use.
     */
    protected static final String DEFAULT_API_TO_USE = "dom";
    public static final String DEMO_NAME = "Demo presentation";
    public static final String DEFAULT_EXTENSION = ".xml";


    /**
     * Text of messages
     */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";

    public Accessor() {
    }

    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    public void loadFile(Presentation presentation, String filename) throws IOException {
        int slideNumber, itemNumber, max = 0, maxItems = 0;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new InputSource(filename)); //Create a JDOM document
            Element doc = document.getDocumentElement();
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
                    loadSlideItem(slide, item);
                }
            }
        } catch (IOException iox) {
            System.err.println(iox.toString());
        } catch (SAXException sax) {
            System.err.println(sax.getMessage());
        } catch (ParserConfigurationException pcx) {
            System.err.println(PCE);
        }
    }

    protected void loadSlideItem(Slide slide, Element item) {
       Style level = Style.level0; // default
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(Attribute.level.name()).getTextContent();
        if (leveltext != null) {
            try {
                level = Style.valueOf(leveltext);
            } catch(NumberFormatException x) {
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

    public void saveFile(Presentation presentation, String filename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");
        for (int slideNumber=0; slideNumber<presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);
            out.println("<slide>");
            out.println("<title>" + slide.getTitle() + "</title>");
            Vector<SlideItem> slideItems = slide.getSlideItems();
            for (int itemNumber = 0; itemNumber<slideItems.size(); itemNumber++) {
                SlideItem slideItem = (SlideItem) slideItems.elementAt(itemNumber);
                out.print("<item kind=");
                if (slideItem instanceof TextItem) {
                    out.print("\"text\" level=\"" + slideItem.getStyle().name() + "\">");
                    out.print( ( (TextItem) slideItem).getText());
                }
                else {
                    if (slideItem instanceof BitmapItem) {
                        out.print("\"image\" level=\"" + slideItem.getStyle().name() + "\">");
                        out.print( ( (BitmapItem) slideItem).getName());
                    }
                    else {
                        System.out.println("Ignoring " + slideItem);
                    }
                }
                out.println("</item>");
            }
            out.println("</slide>");
        }
        out.println("</presentation>");
        out.close();
    }
}
