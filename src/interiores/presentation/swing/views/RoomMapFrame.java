package interiores.presentation.swing.views;

import interiores.business.events.room.RoomCreatedEvent;
import interiores.business.events.room.RoomLoadedEvent;
import interiores.core.presentation.annotation.Listen;
import interiores.presentation.swing.SwingFrame;
import java.awt.BorderLayout;

/**
 *
 * @author hector
 */
public class RoomMapFrame extends SwingFrame
{
    /**
     * Creates new form RoomMap
     */
    public RoomMapFrame()
    {
        initComponents();
        setLayout(new BorderLayout());
    }
    
    @Override
    public void onLoad() throws Exception
    {
        presentation.load(RoomMapPanel.class); 
    }
    
    @Override
    @Listen({RoomCreatedEvent.class, RoomLoadedEvent.class})
    public void showView()
    {
        //this.removeAll();
        
        RoomMapPanel map = (RoomMapPanel) presentation.get(RoomMapPanel.class);
        add(map, BorderLayout.CENTER);
        pack();
        
        super.showView();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Room map");
        setAlwaysOnTop(true);
        setResizable(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(RoomMapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(RoomMapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(RoomMapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(RoomMapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new RoomMapFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
