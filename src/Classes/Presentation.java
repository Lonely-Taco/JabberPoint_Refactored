package Classes;

import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String showTitle; //The title of the presentation
	private ArrayList<Slide> showList = null; //An ArrayList with slides

	public Presentation() {
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {

	}

	public int getPresentationSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}


	//Remove the presentation
	public void clear() {
		showList = new ArrayList<Slide>();
	}

	//Add a slide to the presentation
	public void append(Slide slide) {
		showList.add(slide);
	}

	//Return a slide with a specific number
	public Slide getSlide(int number) {
		if (number < 0 || number >= getPresentationSize()){
			return null;
	    }
			return (Slide)showList.get(number);
	}


}
