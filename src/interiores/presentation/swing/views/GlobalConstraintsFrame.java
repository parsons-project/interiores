package interiores.presentation.swing.views;

import interiores.business.controllers.GlobalConstraintController;
import interiores.business.events.constraints.GlobalConstraintAdded;
import interiores.business.events.constraints.GlobalConstraintRemoved;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.annotation.Listen;
import interiores.utils.CoolColor;
import interiores.utils.Material;
import java.awt.event.KeyEvent;
import java.util.Collection;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 *
 * @author alvaro
 */
public class GlobalConstraintsFrame extends javax.swing.JFrame {

    private GlobalConstraintController globalConstraintController;
    
    /** Creates new form GlobalConstraintsFrame */
    public GlobalConstraintsFrame(SwingController presentation) {
        initComponents();
        
        globalConstraintController = presentation.getBusinessController(GlobalConstraintController.class);
        
        showMaxPriceConstraint();
        updateActiveConstraintsList();
        updateComboBox(typeCombo, globalConstraintController.getAvailableConstraints());
        
    }
    
    /**
     * Set the values of the collection to the jlist
     * @param jlist The jlist to be updated
     * @param objects The array of objects to be putted in the jlist
     */
    private void setListValues(JList jlist, Collection<? extends Object> objects) {
            jlist.setListData(objects.toArray(new Object[objects.size()]));
    }

    
    private void showMaxPriceConstraint() {
        subtypeCombo.setVisible(false);
        priceSpinner.setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        globalConstraintsList = new javax.swing.JList();
        addConstraintButton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        typeCombo = new javax.swing.JComboBox();
        subtypeCombo = new javax.swing.JComboBox();
        priceSpinner = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Global constraints editor");
        setAlwaysOnTop(true);
        setResizable(false);

        globalConstraintsList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                globalConstraintsListKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(globalConstraintsList);

        addConstraintButton.setText("Add Constraint");
        addConstraintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addConstraintButtonActionPerformed(evt);
            }
        });

        closebutton.setText("Close");
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });

        typeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboActionPerformed(evt);
            }
        });

        priceSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addConstraintButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(typeCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 140, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(priceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addComponent(subtypeCombo, 0, 140, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(closebutton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(subtypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addConstraintButton)
                    .addComponent(closebutton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void addConstraintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addConstraintButtonActionPerformed
    String type = (String) typeCombo.getSelectedItem();
    String subtype = (String) subtypeCombo.getSelectedItem();
    float price = (Float) priceSpinner.getValue();
        
    addGlobalConstraint(type, subtype, price);         

    updateActiveConstraintsList();
}//GEN-LAST:event_addConstraintButtonActionPerformed

private void typeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboActionPerformed
    String consName = (String) typeCombo.getSelectedItem();
    if (consName != null) {
        if (consName.equals("budget")) showMaxPriceConstraint();
        else if (consName.startsWith("same")) showSameConstraint(consName);
        else if (consName.startsWith("specific")) showSpecificConstraint(consName);
    }
}//GEN-LAST:event_typeComboActionPerformed

private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
    dispose();
}//GEN-LAST:event_closebuttonActionPerformed

private void globalConstraintsListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_globalConstraintsListKeyReleased
    int index = globalConstraintsList.getSelectedIndex();
    if (index >= 0) {
        GlobalConstraint constraint = (GlobalConstraint) globalConstraintsList.getModel().getElementAt(index);
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:  // press delete
                globalConstraintController.removeGlobalConstraint(constraint.getClass());
                break;            
        }
        updateActiveConstraintsList();
   }   
}//GEN-LAST:event_globalConstraintsListKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addConstraintButton;
    private javax.swing.JButton closebutton;
    private javax.swing.JList globalConstraintsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner priceSpinner;
    private javax.swing.JComboBox subtypeCombo;
    private javax.swing.JComboBox typeCombo;
    // End of variables declaration//GEN-END:variables

    @Listen({GlobalConstraintAdded.class, GlobalConstraintRemoved.class})
    private void updateActiveConstraintsList() {
        setListValues(globalConstraintsList, globalConstraintController.getConstraints());
    }
    
    /**
     * Updates a combobox with new data
     * @param combo The combobox to be updated
     * @param objects The collection of elements that the combobox should contain
     */
    private void updateComboBox(JComboBox combo, Collection<? extends Object> objects) {
        combo.removeAllItems();
        for (Object item : objects)
            combo.addItem(item.toString());
    }

    private void showSameConstraint(String consName) {
        subtypeCombo.setVisible(false);
        priceSpinner.setVisible(false);
    }

    private void showSpecificConstraint(String consName) {
        if (consName.endsWith("color")) updateComboBox(subtypeCombo, CoolColor.getNames());
        else if (consName.endsWith("material")) updateComboBox(subtypeCombo, Material.getNames());
        priceSpinner.setVisible(false);
        subtypeCombo.setVisible(true);
    }

    private void addGlobalConstraint(String type, String subtype, float price) {
        if (type.equals("budget")) globalConstraintController.addBudgetConstraint(price);
        else if (type.equals("same-color")) globalConstraintController.addSameColorConstraint();
        else if (type.equals("same-material")) globalConstraintController.addSameMaterialConstraint();
        else if (type.equals("specific-color")) globalConstraintController.addSpecificColorConstraint(subtype);
        else if (type.equals("specific-material")) globalConstraintController.addSpecificMaterialConstraint(Material.valueOf(subtype));
    }
}
