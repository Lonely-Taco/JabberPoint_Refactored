package Classes;

import java.awt.*;

/**
 * Classes.JabberPoint Main Program
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2023/04/28 Sylvia Stuurman
 */

public class JabberPoint {
    protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

    /**
     * The main program
     */
    public static void main(String[] argv) {
        Accessor xmlAccessor = new Accessor();

        PresentationController presentationController = new PresentationController(xmlAccessor.buildDemoPresentation());

        SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentationController);

        if (argv.length != 0) {
            presentationController.setPresentation(xmlAccessor.accessFile(argv[0]), frame);
        }
    }

}

