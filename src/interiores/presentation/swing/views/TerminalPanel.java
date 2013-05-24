/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing.views;

import interiores.core.presentation.SwingController;
import interiores.core.presentation.terminal.ConsolePrintStream;
import javax.swing.JPanel;

/**
 *
 * @author hector0193
 */
public class TerminalPanel
        extends JPanel
{
    /**
     * Creates new form TerminalPanel
     */
    public TerminalPanel(SwingController swing) {
        initComponents();
    }
    
    @Override
    public void setVisible(boolean visible) {
        if(visible) {
            ConsolePrintStream printStream = (ConsolePrintStream) System.out;
            printStream.setTextComponent(jTextPane1);
        }
        
        super.setVisible(visible);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(23, 200));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(0, 200));

        jTextPane1.setMinimumSize(new java.awt.Dimension(0, 200));
        jScrollPane2.setViewportView(jTextPane1);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
