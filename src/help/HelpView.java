package help;

import interiores.core.business.BusinessException;
import interiores.core.presentation.SwingController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author alvaro
 */
public class HelpView extends JScrollPane implements HyperlinkListener {

    private JEditorPane viewer = new JEditorPane();    
    private JFrame frame = null;
    private URL currentURL = null;
    private Stack<URL> urlStack = new Stack<URL>();
    private JButton back = null;

    public HelpView(SwingController swing) {
    }

    public void load(String file, String title) {
        setViewportView(viewer);
        viewer.addHyperlinkListener(this);
        readHTML(file);

        frame = new JFrame();

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        loadButtons(panel);

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
            }
        });

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);

        frame.add(this);

        frame.setSize(400, 300);
        frame.setTitle(title);
        frame.setVisible(true);
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent hyperLinkEvent) {
        try {
            if (hyperLinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                urlStack.push(currentURL);
                currentURL = hyperLinkEvent.getURL();
                viewer.setPage(currentURL);
                back.setEnabled(true);
            }
        } catch (IOException ioe) {
            throw new BusinessException("Unable to load file " + currentURL);
        }
    }


    public void backCallback() {
        try {
            currentURL = urlStack.pop();
            viewer.setPage(currentURL);
            if (urlStack.empty()) {
                back.setEnabled(false);
            }
        } 
        catch (IOException ioe) {
            throw new BusinessException("Unable to load previous file");
        }
    }

    private void readHTML(String file) {
        try {
            viewer.setEditable(false);
            currentURL = HelpView.class.getResource(file);
            viewer.setPage(currentURL);
        } 
        catch (Exception exc) {
            throw new BusinessException("Unable to load file " + currentURL);
        }
    }

    private void loadButtons(JPanel panel) {
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                backCallback();
            }
        });
        panel.add(back);
        back.setEnabled(false);

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
            }
        });
        panel.add(close);
    }
}
