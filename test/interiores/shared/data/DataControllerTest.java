package interiores.shared.data;

import interiores.shared.data.objects.Container;
import interiores.shared.data.objects.Simple;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hector
 */
public class DataControllerTest
{
    private static final String SIMPLE_PATH = "data/test/simple.xml";
    private static final String CONTAINER_PATH = "data/test/container.xml";
    
    private DataController data;
    private Simple simple;
    private Container container;
    
    public DataControllerTest() {
        
    }
    
    @Before
    public void setUp() {
        data = new DataController();
        
        setUpSimple();
        setUpContainer();
    }
    
    private void setUpSimple() {
        simple = new Simple(1, 2, "Hi there!");
    }

    private void setUpContainer() {      
        List<Simple> list = new ArrayList();
        
        for(int i = 0; i < 100; i++)
            list.add(new Simple(i, i*4, "I'm " + i));
        
        container = new Container(new Simple(42, 42, "I'm the answer to everything!"), list);
    }
    
    /**
     * Test save a simple object.
     */
    @Test
    public void testSaveSimple() throws Exception {
        testSave(simple, "data/test/simple.test.xml", SIMPLE_PATH);
    }
    
    /**
     * Test save a container object.
     */
    @Test
    public void testSaveContainer() throws Exception {
        testSave(container, "data/test/container.test.xml", CONTAINER_PATH);
    }
    
    /**
     * Test load a simple object.
     */
    @Test
    public void testLoadSimple() throws Exception {     
        testLoad(Simple.class, SIMPLE_PATH, simple);
    }
    
    /**
     * Test load a container object.
     */
    @Test
    public void testLoadContainer() throws Exception {
        testLoad(Container.class, CONTAINER_PATH, container);
    }
    
    private void testSave(Object o, String savePath, String correctionPath) throws Exception {
        data.save(o, savePath);
        
        File f = new File(savePath);
        
        assertTrue("The files differ!", areFilesIdentical(new File(correctionPath), f));
        
        f.delete();
    }
    
    private void testLoad(Class c, String path, Object original) throws Exception {
        Object loaded = data.load(c, path);
        
        Method m = c.getMethod("equals", c); // Some simple reflection!
        assertTrue("The objects differ!", (Boolean) m.invoke(loaded, original));
    }
    
    
    private boolean areFilesIdentical(File file1, File file2) {
        return (file1.length() == file2.length()); // Very primitive! But pretty useful :D
    }
}
