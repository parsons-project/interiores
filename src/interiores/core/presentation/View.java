/*
 */
package interiores.core.presentation;

/**
 *
 * @author hector
 */
public interface View
{
    public void showView();
    public void setPresentation(PresentationController presentation);
    public void onLoad() throws Exception;
}
