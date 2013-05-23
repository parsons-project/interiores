package interiores.presentation.swing.views;

import interiores.core.presentation.SwingController;
import javax.swing.JPanel;
/**
 *
 * @author hector
 */
public class WelcomePanel extends JPanel
{
    private SwingController swing;

    /**
     * Creates new form WelcomePanel
     */
    public WelcomePanel(SwingController swing)
    {
        this.swing = swing;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        newDesignButton = new javax.swing.JButton();
        openDesignButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Welcome to Interior design!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(jLabel1, gridBagConstraints);

        newDesignButton.setText("Create a new room design...");
        newDesignButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                newDesignButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(newDesignButton, gridBagConstraints);

        openDesignButton.setText("Open a previous design...");
        openDesignButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                openDesignButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(openDesignButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void newDesignButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_newDesignButtonActionPerformed
    {//GEN-HEADEREND:event_newDesignButtonActionPerformed
        swing.showNew(NewDesignDialog.class);
    }//GEN-LAST:event_newDesignButtonActionPerformed

    private void openDesignButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_openDesignButtonActionPerformed
    {//GEN-HEADEREND:event_openDesignButtonActionPerformed
        swing.get(MainAppFrame.class).openDesign();
    }//GEN-LAST:event_openDesignButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton newDesignButton;
    private javax.swing.JButton openDesignButton;
    // End of variables declaration//GEN-END:variables
}
