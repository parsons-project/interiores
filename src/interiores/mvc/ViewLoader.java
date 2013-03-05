package interiores.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewLoader is a view injector. It uses Inversion of Control (IoC) to inject
 * subviews in views and delegate control to each view controller.
 *
 * @author hector
 */
public class ViewLoader
{
    private static final String APP_PACKAGE = "interiores";
    
    private String gui;
    private Map<String, Class> view_controller;
    private Map<Class, Controller> controllers;
    private Map<String, String[]> dependencies;

    public ViewLoader(String gui)
    {
        this.gui = gui;
        view_controller = new HashMap<String, Class>();
        controllers = new HashMap<Class, Controller>();
        dependencies = new HashMap<String, String[]>();
    }

    public void addViews(Class controller, String[] views)
    {
        for (String view : views)
            view_controller.put(view, controller);
    }

    public void setDependencies(String view, String[] deps)
    {
        dependencies.put(view, deps);
    }
    
    public void loadView(String viewName, Model model) throws ClassNotFoundException, Exception
    {        
        View view = getView(viewName, model);
        view.showView();
    }
    
    public View getView(String viewName, Model model) throws Exception
    {
        String className = APP_PACKAGE + ".views." + gui + "." + viewName + "View";
        Class viewClass = Class.forName(className);
        
        if (!view_controller.containsKey(viewName))
            throw new Exception("The view " + viewClass.getName() + " is not defined "
                    + "in the ViewLoader.");

        View instance;
        View[] vdeps = getDependencies(viewName, model);

        switch (vdeps.length)
        {
            case 1:
                instance = (View)viewClass.getConstructor(View.class).newInstance(vdeps[0]);
                break;

            case 2:
                instance = (View)viewClass.getConstructor(View.class, View.class).newInstance(
                        vdeps[0],
                        vdeps[1]);
                break;

            case 3:
                instance = (View)viewClass.getConstructor(View.class, View.class, View.class).newInstance(
                        vdeps[0],
                        vdeps[1],
                        vdeps[2]);
                break;

            default:
                instance = (View)viewClass.newInstance();
        }
        
        Controller viewController = getController(viewName);
        viewController.addView(instance);
        
        if(model != null)
            viewController.setModel(model);
        
        return instance;
    }

    private View[] getDependencies(String viewName, Model model) throws Exception
    {
        if(! dependencies.containsKey(viewName))
            return new View[0];
        
        String[] deps = dependencies.get(viewName);
        View[] vdeps = new View[deps.length];

        for (int i = 0; i < vdeps.length; ++i)
            vdeps[i] = getView(deps[i], model);

        return vdeps;
    }
    
    private Controller getController(String viewName) throws Exception
    {
        Class controllerClass = view_controller.get(viewName);
        
        if(controllers.containsKey(controllerClass))
            return controllers.get(controllerClass);
        
        Controller controller = (Controller)controllerClass.newInstance();
        controller.setViewLoader(this);
        
        controllers.put(controllerClass, controller);
        
        return controller;
    }
}
