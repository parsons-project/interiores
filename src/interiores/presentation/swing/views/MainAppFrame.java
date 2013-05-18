package interiores.presentation.swing.views;

import interiores.business.events.room.RoomCreatedEvent;
import interiores.business.events.room.RoomLoadedEvent;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.annotation.Listen;
import interiores.core.presentation.swing.SwingException;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author hector
 */
public class MainAppFrame extends JFrame
{
    private WelcomePanel welcome;
    private RoomMapPanel map;
    /**
     * Creates new form MainView
     */
    public MainAppFrame(SwingController presentation) throws SwingException
    {
        initComponents();
        setLayout(new BorderLayout());
        
        welcome = presentation.get(WelcomePanel.class);
        map = presentation.get(RoomMapPanel.class);
        
        add(welcome, BorderLayout.CENTER);
        welcome.setVisible(true);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Interiors design");
        setAlwaysOnTop(true);

        jMenu1.setText("File");

        jMenuItem1.setText("New room design...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables

    @Listen({RoomCreatedEvent.class, RoomLoadedEvent.class})
    public void showMap() {
        remove(welcome);
        add(map, BorderLayout.CENTER);
        map.setVisible(true);
        validate();
        pack();
    }
}
