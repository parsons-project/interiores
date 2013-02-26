/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewLoader is a view injector.
 * It uses Inversion of Control (IoC) to inject subviews in views and delegate
 * control to each view controller.
 * @author hector
 */
public class ViewLoader
{
   private Map<Class, Class> controllers;
   private Map<Class, Class[]> dependencies;
   
   public ViewLoader()
   {
       controllers = new HashMap<Class, Class>();
       dependencies = new HashMap<Class, Class[]>();
   }
   
   public void addViews(Class controller, Class[] views)
   {
       for(Class view : views)
           controllers.put(view, controller);
   }
   
   public void setDependencies(Class view, Class[] deps)
   {
       dependencies.put(view, deps);
   }
   
   public View getView(Class view) throws Exception
   {
       if(! controllers.containsKey(view))
           throw new Exception("The view " + view.getName() + " is not defined"
                   + "in the ViewLoader.");
       
       Object instance;
       View[] vdeps = getDependencies(view);
       
       switch(vdeps.length)
       {
           case 1:
               instance = view.getConstructor(View.class).newInstance(vdeps[0]);
           
           case 2:
               instance = view.getConstructor(View.class, View.class).newInstance(
                       vdeps[0],
                       vdeps[1]);
               
           case 3:
               instance = view.getConstructor(View.class, View.class,
                       View.class).newInstance(
                        vdeps[0],
                        vdeps[1],
                        vdeps[2]);
                       
           default:
               instance = view.newInstance();
       }
       
       return (View)instance;
   }
   
   private View[] getDependencies(Class view) throws Exception
   {
       Class[] deps = dependencies.get(view);
       View[] vdeps = new View[deps.length];
       
       for(int i = 0; i < vdeps.length; ++i)
           vdeps[i] = getView(deps[i]);
       
       return vdeps;
   }
}
