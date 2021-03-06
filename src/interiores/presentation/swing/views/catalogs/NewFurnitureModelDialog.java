/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing.views.catalogs;

import interiores.business.controllers.FurnitureModelController;
import interiores.utils.CoolColor;
import interiores.utils.Material;

/**
 *
 * @author larribas
 */
public class NewFurnitureModelDialog extends javax.swing.JDialog {

    private FurnitureModelController fmController;
    private String current_ftype;
    
    /**
     * Creates new form newRoomTypeDialog
     */
    public NewFurnitureModelDialog(FurnitureModelController fmc, String ftype) {
        initComponents();
        
        fmController = fmc;
        current_ftype = ftype;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        widthField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        depthField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        materialSelector = new javax.swing.JComboBox();
        materialLabel1 = new javax.swing.JLabel();
        colorSelector = new javax.swing.JComboBox();
        colorLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        passiveSpaceLabel = new javax.swing.JLabel();
        passiveSpaceField = new javax.swing.JTextField();
        priceField = new javax.swing.JTextField();
        priceLabel2 = new javax.swing.JLabel();
        priceLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New room type");
        setAlwaysOnTop(true);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel1.setText("Select the properties the new model type will have:");

        jLabel2.setText("Name:");

        jLabel3.setText("Dimensions:");

        widthField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                widthFieldKeyTyped(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        createButton.setText("Create");
        createButton.setEnabled(false);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("x");

        depthField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                depthFieldKeyTyped(evt);
            }
        });

        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameFieldKeyTyped(evt);
            }
        });

        Object[] m= Material.getNames().toArray();
        materialSelector.setModel(new javax.swing.DefaultComboBoxModel(m));

        materialLabel1.setText("Material:");

        Object[] c = CoolColor.getNames().toArray();
        colorSelector.setModel(new javax.swing.DefaultComboBoxModel(c));

        colorLabel.setText("Color:");

        priceLabel.setText("Price:");

        passiveSpaceLabel.setText("Passive Space:");

        passiveSpaceField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passiveSpaceFieldKeyTyped(evt);
            }
        });

        priceField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                priceFieldKeyTyped(evt);
            }
        });

        priceLabel2.setText("€");

        priceLabel3.setText("cm");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(cancelButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(createButton))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(layout.createSequentialGroup()
                                .add(materialLabel1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(materialSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(colorLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(colorSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(12, 12, 12))
                            .add(jLabel1))
                        .add(0, 27, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(passiveSpaceLabel)
                                .add(18, 18, 18)
                                .add(passiveSpaceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(priceLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(priceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(priceLabel2))
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(nameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(jLabel3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(widthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(1, 1, 1)
                                .add(jLabel6)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(depthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(priceLabel3)))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(nameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 16, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(depthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(priceLabel3))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(widthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel3)
                        .add(jLabel6)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(materialSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(materialLabel1)
                    .add(colorSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colorLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(passiveSpaceLabel)
                    .add(passiveSpaceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(priceLabel)
                    .add(priceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(priceLabel2))
                .add(10, 10, 10)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(createButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
 
        String name = nameField.getText();
        int width = Integer.parseInt(widthField.getText());
        int depth = Integer.parseInt(depthField.getText());
        String material = materialSelector.getSelectedItem().toString();
        String color = colorSelector.getSelectedItem().toString();
        float price = Float.parseFloat(priceField.getText());
        String str = passiveSpaceField.getText();
        str = str.replace(" ","");
        String[] s_ps = str.split(",");
        int[] i_ps = new int[4];
        for(int i = 0; i < Math.min(4, s_ps.length); i++) i_ps[i] = Integer.parseInt(s_ps[i]);
        fmController.add(current_ftype, name, width, depth, price, color, material, i_ps);
        
        dispose();
    }//GEN-LAST:event_createButtonActionPerformed

    private void nameFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameFieldKeyTyped
        verifyFields();
    }//GEN-LAST:event_nameFieldKeyTyped

    private void widthFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_widthFieldKeyTyped
        verifyFields();
    }//GEN-LAST:event_widthFieldKeyTyped

    private void depthFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_depthFieldKeyTyped
        verifyFields();
    }//GEN-LAST:event_depthFieldKeyTyped

    private void passiveSpaceFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passiveSpaceFieldKeyTyped
        verifyFields();
    }//GEN-LAST:event_passiveSpaceFieldKeyTyped

    private void priceFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceFieldKeyTyped
        verifyFields();
    }//GEN-LAST:event_priceFieldKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JComboBox colorSelector;
    private javax.swing.JButton createButton;
    private javax.swing.JTextField depthField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel materialLabel1;
    private javax.swing.JComboBox materialSelector;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField passiveSpaceField;
    private javax.swing.JLabel passiveSpaceLabel;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JLabel priceLabel2;
    private javax.swing.JLabel priceLabel3;
    private javax.swing.JTextField widthField;
    // End of variables declaration//GEN-END:variables

    /**
     * Checks whether all the fields contain valid information, and if so, enables the create button
     */
    private void verifyFields() {
        boolean nf, wf, df, psf, pf;
        nf = !"".equals(nameField.getText());
        wf = widthField.getText().matches("[0-9]+");
        df = depthField.getText().matches("[0-9]+");
        psf = passiveSpaceField.getText().matches("[0-9,]+");
        pf = priceField.getText().matches("[0-9.]+");
        
        createButton.setEnabled(nf && wf && df && psf && pf);
    }
}
