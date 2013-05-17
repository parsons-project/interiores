package interiores.core.presentation;

/**
 * View interface tells what a graphical user interface view should implement.
 * @author hector
 */
public interface View
{
    /**
     * Shows the view to the user.
     */
    public void showView();
    
    /**
     * Sets the presentation controller of the view.
     * @param presentation The presentation controller
     */
    public void setPresentation(PresentationController presentation);
    
    /**
     * Overridable method that is called when the view is loaded.
     * @throws Exception 
     */
    public void onLoad() throws Exception;
}
