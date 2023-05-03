package Classes;

import Classes.SlideItems.BitmapItem;
import Enumerations.Style;

public class PresentationBuilder {

    public void buildDemoPresentation(Presentation presentation) {
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
    }
}
