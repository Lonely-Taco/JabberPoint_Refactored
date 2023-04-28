package Classes;

import Enumerations.Action;
import Exceptions.SaveFileException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

    private Frame parent; //The frame, only used as parent for the Dialogs
    private Presentation presentation; //Commands are given to the presentation

    private File filePath;

    private static final long serialVersionUID = 227L;

    protected static final String PAGENR = "Page number?";

    protected String TESTFILE = "testPresentation.xml";
    protected String SAVEFILE = "savedPresentation.xml";

    protected static final String IOEX = "IO Exception: ";

    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;
        MenuItem menuItem = new MenuItem();
        Menu fileMenu = new Menu(Action.File.name());
        addOpenFileMenuItem(fileMenu, menuItem);
        addNewFile(fileMenu, menuItem);
        addSaveFileMenuItem(fileMenu, menuItem);
        addQuitMenuItem(fileMenu, menuItem);
        addViewMenu(fileMenu);
        addHelpMenu();
    }

    private void fileChooser() {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());
        jfc.showDialog(null, "Apply");
        jfc.setVisible(true);

        if (jfc.getSelectedFile() != null) {
            filePath = jfc.getSelectedFile();
        }
    }


    private void addOpenFileMenuItem(Menu fileMenu, MenuItem menuItem) {
        fileMenu.add(menuItem = makeMenuItem(Action.Open.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.clear();
                Accessor xmlAccessor = new Accessor();

                fileChooser();

                if (filePath != null) {
                    xmlAccessor.accessFile(presentation, filePath.getPath());
                    presentation.setSlideNumber(0);
                }

                parent.repaint();
            }
        });

    }

    private void addNewFile(Menu fileMenu, MenuItem menuItem) {
        fileMenu.add(menuItem = makeMenuItem(Action.New.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.clear();
                parent.repaint();
            }
        });
    }

    private void addSaveFileMenuItem(Menu fileMenu, MenuItem menuItem) {
        fileMenu.add(menuItem = makeMenuItem(Action.Save.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Accessor xmlAccessor = new Accessor();
                try {
                    fileChooser();
                    if (filePath != null) {
                        FileManager.saveFile(presentation, filePath.getPath());
                    }
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc,
                                                  new SaveFileException().getMessage(), JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    private void addQuitMenuItem(Menu fileMenu, MenuItem menuItem) {
        fileMenu.addSeparator();

        fileMenu.add(menuItem = makeMenuItem(Action.Exit.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.exit(0);
            }
        });

        add(fileMenu);
    }

    private void addViewMenu(MenuItem menuItem) {
        Menu viewMenu = new Menu(Action.View.name());

        viewMenu.add(menuItem = makeMenuItem(Action.Next.name()));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.nextSlide();
            }
        });

        viewMenu.add(menuItem = makeMenuItem(Action.Prev.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.prevSlide();
            }
        });

        viewMenu.add(menuItem = makeMenuItem(Action.Goto.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
            }
        });

        add(viewMenu);

    }

    private void addHelpMenu() {
        Menu helpMenu = new Menu(Action.Help.name());
        MenuItem menuItem;

        helpMenu.add(menuItem = makeMenuItem(Action.About.name()));

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AboutBox.show(parent);
            }
        });

        setHelpMenu(helpMenu);        //Needed for portability (Motif, etc.).
    }


    public MenuItem makeMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
