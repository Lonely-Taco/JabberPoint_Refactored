package Classes;

import AbstractClasses.Accessor;
import Enumerations.Action;
import Exceptions.LoadFileException;
import Exceptions.SaveFileException;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

    private Frame parent; //The frame, only used as parent for the Dialogs
    private Presentation presentation; //Commands are given to the presentation
    private static final long serialVersionUID = 227L;

    protected static final String PAGENR = "Page number?";

    protected String TESTFILE = "testPresentation.xml";
    protected String SAVEFILE = "savedPresentation.xml";

    protected static final String IOEX = "IO Exception: ";

    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;

        MenuItem menuItem;

        Menu fileMenu = new Menu(Action.File.name());

        fileMenu.add(menuItem = mkMenuItem(Action.Open.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.clear();
                Accessor xmlAccessor = new XMLAccessor();
                try {
                    xmlAccessor.loadFile(presentation, TESTFILE);
                    presentation.setSlideNumber(0);
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc,
                                                  new LoadFileException().getMessage(), JOptionPane.ERROR_MESSAGE
                    );
                }
                parent.repaint();
            }
        });

        fileMenu.add(menuItem = mkMenuItem(Action.New.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.clear();
                parent.repaint();
            }
        });

        fileMenu.add(menuItem = mkMenuItem(Action.Save.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Accessor xmlAccessor = new XMLAccessor();
                try {
                    xmlAccessor.saveFile(presentation, SAVEFILE);
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc,
                                                  new SaveFileException().getMessage(), JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        fileMenu.addSeparator();

        fileMenu.add(menuItem = mkMenuItem(Action.Exit.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.exit(0);
            }
        });

        add(fileMenu);

        Menu viewMenu = new Menu(Action.View.name());

        viewMenu.add(menuItem = mkMenuItem(Action.Next.name()));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.nextSlide();
            }
        });

        viewMenu.add(menuItem = mkMenuItem(Action.Prev.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.prevSlide();
            }
        });

        viewMenu.add(menuItem = mkMenuItem(Action.Goto.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
            }
        });

        add(viewMenu);

        Menu helpMenu = new Menu(Action.Help.name());

        helpMenu.add(menuItem = mkMenuItem(Action.About.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AboutBox.show(parent);
            }
        });

        setHelpMenu(helpMenu);        //Needed for portability (Motif, etc.).
    }

    //Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
