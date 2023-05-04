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

    protected Presentation accessFile(String filename) {
        Element doc = FileManager.loadFile(filename);

        if (doc == null) {
            return new Presentation();
        }

       return PresentationBuilder.buildPresentation(doc);
    }

    public Presentation buildDemoPresentation() {
        return PresentationBuilder.buildDemoPresentation();
    }
}
