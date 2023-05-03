package Classes;

import java.util.ArrayList;

public class PresentationController {

    private Presentation presentation;
    //The view component of the slides
    private SlideViewerComponent slideViewComponent = null;

    private int currentSlideNumber = 0; //The number of the current slide

    public PresentationController(Presentation presentation) {
        this.presentation = presentation;
        clearPresentation();
    }

    //Returns the number of the current slide
    public int getSlideNumber() {
        return currentSlideNumber;
    }

    //Change the current slide number and report it the the window
    public void setSlideNumber(int number) {
        currentSlideNumber = number;
        if (slideViewComponent != null) {
            slideViewComponent.update(presentation);
        }
    }

    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide() {
        if (currentSlideNumber > 0) {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide() {
        if (currentSlideNumber < (presentation.getPresentationSize() - 1)) {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    public void setShowView(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
    }

    //Return the current slide
    public Slide getCurrentSlide() {
        return presentation.getSlide(currentSlideNumber);
    }

    public void clearPresentation() {
        presentation.clear();
        setSlideNumber(-1);
    }

    public void exit(int n) {
        System.exit(n);
    }

    public Presentation getPresentation() {
        return this.presentation;
    }
}
