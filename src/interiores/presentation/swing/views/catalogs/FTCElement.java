/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing.views.catalogs;

import interiores.business.controllers.FurnitureTypeController;
import interiores.core.presentation.SwingController;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author larribas
 */
public final class FTCElement extends javax.swing.JPanel {

    // The actual name of the element contains its internal name within the application
    private String shortName;
    private String fullName;
    
    private boolean hasWidthChanged = false;
    private boolean hasDepthChanged = false;
    private boolean hasPassiveSpaceChanged = false;
    
    private FurnitureTypeCatalogFrame catFrame;
    private FurnitureTypeController ftController;
    
    /**
     * Creates new form FTCElement
     */
    public FTCElement(SwingController sw, String shortName, String fullName, FurnitureTypeCatalogFrame frame) {
        initComponents();
        
        this.shortName = shortName;
        this.fullName = fullName;
        
        catFrame = frame;
        ftController = sw.getBusinessController(FurnitureTypeController.class);
        
        initValues();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        removeButton = new javax.swing.JButton();
        innerPanel = new javax.swing.JPanel();
        ftname = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        minWidthField = new javax.swing.JTextField();
        widthLabel2 = new javax.swing.JLabel();
        maxWidthField = new javax.swing.JTextField();
        depthLabel3 = new javax.swing.JLabel();
        depthLabel = new javax.swing.JLabel();
        minDepthField = new javax.swing.JTextField();
        depthLabel2 = new javax.swing.JLabel();
        maxDepthField = new javax.swing.JTextField();
        widthLabel3 = new javax.swing.JLabel();
        passiveSpaceLabel = new javax.swing.JLabel();
        passiveSpaceField = new javax.swing.JTextField();
        passiveSpaceHint = new javax.swing.JLabel();
        wallCheckbox = new javax.swing.JCheckBox();
        placeButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        ImageIcon im = new javax.swing.ImageIcon("src/resources/remove_element.png");
        im.setImage( im.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH) );
        removeButton.setIcon(im);
        removeButton.setBorder(BorderFactory.createEmptyBorder());
        removeButton.setContentAreaFilled(false);

        innerPanel.setBackground(new java.awt.Color(255, 255, 255));
        innerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        ftname.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        ftname.setText("Name:");

        widthLabel.setText("This furniture should have a width between");

        widthLabel2.setText("and");

        depthLabel3.setText("cm");

        depthLabel.setText("And a depth between");

        depthLabel2.setText("and");

        widthLabel3.setText("cm");

        passiveSpaceLabel.setText("It should always have the following free space to its sides:");

        passiveSpaceHint.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        passiveSpaceHint.setForeground(new java.awt.Color(153, 153, 153));
        passiveSpaceHint.setText("Format: N,E,S,W. e.g. 10,0,0,20");

        wallCheckbox.setText("Cling to a wall");

        im = new javax.swing.ImageIcon("src/resources/place.png");
        im.setImage( im.getImage().getScaledInstance(50,40,java.awt.Image.SCALE_SMOOTH) );
        placeButton.setIcon(im);
        placeButton.setBorder(BorderFactory.createEmptyBorder());
        placeButton.setContentAreaFilled(false);

        jLabel2.setText("Modify position with");

        jLabel3.setText("respect to other furniture");

        org.jdesktop.layout.GroupLayout innerPanelLayout = new org.jdesktop.layout.GroupLayout(innerPanel);
        innerPanel.setLayout(innerPanelLayout);
        innerPanelLayout.setHorizontalGroup(
            innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(innerPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(passiveSpaceLabel)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, innerPanelLayout.createSequentialGroup()
                        .add(199, 199, 199)
                        .add(passiveSpaceHint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 207, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(passiveSpaceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, innerPanelLayout.createSequentialGroup()
                        .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, depthLabel)
                            .add(innerPanelLayout.createSequentialGroup()
                                .add(ftname)
                                .add(64, 64, 64)
                                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(widthLabel)
                                    .add(innerPanelLayout.createSequentialGroup()
                                        .add(placeButton)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel2)
                                            .add(jLabel3))))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(innerPanelLayout.createSequentialGroup()
                                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(minWidthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(minDepthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(innerPanelLayout.createSequentialGroup()
                                        .add(widthLabel2)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(maxWidthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(widthLabel3))
                                    .add(innerPanelLayout.createSequentialGroup()
                                        .add(depthLabel2)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(maxDepthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(depthLabel3))))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, wallCheckbox))))
                .add(15, 15, 15))
        );
        innerPanelLayout.setVerticalGroup(
            innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(innerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ftname)
                    .add(widthLabel)
                    .add(minWidthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(widthLabel2)
                    .add(maxWidthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(widthLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(depthLabel)
                    .add(minDepthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(depthLabel2)
                    .add(maxDepthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(depthLabel3))
                .add(18, 18, Short.MAX_VALUE)
                .add(passiveSpaceLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(innerPanelLayout.createSequentialGroup()
                        .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(passiveSpaceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(passiveSpaceHint))
                        .add(18, 18, 18)
                        .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel3)
                            .add(wallCheckbox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(innerPanelLayout.createSequentialGroup()
                        .add(jLabel2)
                        .add(20, 20, 20))
                    .add(placeButton))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(100, 100, 100)
                .add(removeButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(22, 22, 22)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(removeButton))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel depthLabel;
    private javax.swing.JLabel depthLabel2;
    private javax.swing.JLabel depthLabel3;
    private javax.swing.JLabel ftname;
    private javax.swing.JPanel innerPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField maxDepthField;
    private javax.swing.JTextField maxWidthField;
    private javax.swing.JTextField minDepthField;
    private javax.swing.JTextField minWidthField;
    private javax.swing.JTextField passiveSpaceField;
    private javax.swing.JLabel passiveSpaceHint;
    private javax.swing.JLabel passiveSpaceLabel;
    private javax.swing.JButton placeButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JCheckBox wallCheckbox;
    private javax.swing.JLabel widthLabel;
    private javax.swing.JLabel widthLabel2;
    private javax.swing.JLabel widthLabel3;
    // End of variables declaration//GEN-END:variables

    /**
    * Builds a visual representation of the item in the currently active catalog
    * whose name is 'actname'
    */
    public void initValues() {
        // These lines substitute the default aspect of a button with a custom icon
        
        removeButton.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                catFrame.performModification();
                ftController.rm(shortName);
            }
        });
        
        
        placeButton.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(FTCElement.this, "Dialog still unimplemented");
            }
        });

        // Name of the element
        ftname.setText(fullName + ":");

        // Width setup and events
        minWidthField.setText(Integer.toString(ftController.getWidthRange(shortName).min));
        maxWidthField.setText(Integer.toString(ftController.getWidthRange(shortName).max));
        minWidthField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasWidthChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) {  hasWidthChanged = true;  }
            @Override
            public void removeUpdate(DocumentEvent e) {  hasWidthChanged = true;  }
         });
        maxWidthField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {  hasWidthChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) {  hasWidthChanged = true;  }
            @Override
            public void removeUpdate(DocumentEvent e) {  hasWidthChanged = true;  }
         });
        minWidthField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if (hasWidthChanged) widthRangeUpdate(); }
        });
        maxWidthField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if (hasWidthChanged) widthRangeUpdate(); }
        });

        // Depth setup and events
        minDepthField.setText(Integer.toString(ftController.getDepthRange(shortName).min));
        maxDepthField.setText(Integer.toString(ftController.getDepthRange(shortName).max));
        minDepthField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasDepthChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) { hasDepthChanged = true; }
            @Override
            public void removeUpdate(DocumentEvent e) { hasDepthChanged = true; }
         });
        maxDepthField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasDepthChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) { hasDepthChanged = true; }
            @Override
            public void removeUpdate(DocumentEvent e) { hasDepthChanged = true; }
         });
        minDepthField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if (hasDepthChanged) depthRangeUpdate(); }
        });
        maxDepthField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if (hasDepthChanged) depthRangeUpdate(); }
        });

        // Passive space fields
        passiveSpaceField.setText(getFormattedPassiveSpace());
        passiveSpaceField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasPassiveSpaceChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) { hasPassiveSpaceChanged = true; }
            @Override
            public void removeUpdate(DocumentEvent e) { hasPassiveSpaceChanged = true; }
         });
        passiveSpaceField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if (hasPassiveSpaceChanged) passiveSpaceUpdate(); }
        });
        
        wallCheckbox.setSelected(ftController.getWallClinging(shortName));
    }

    public void updateChanges() {
        minWidthField.setText(Integer.toString(ftController.getWidthRange(shortName).min));
        maxWidthField.setText(Integer.toString(ftController.getWidthRange(shortName).max));
        minDepthField.setText(Integer.toString(ftController.getDepthRange(shortName).min));
        maxDepthField.setText(Integer.toString(ftController.getDepthRange(shortName).max));
        passiveSpaceField.setText(getFormattedPassiveSpace());
    }     

    private void widthRangeUpdate() {
        hasWidthChanged = false;
        String min = minWidthField.getText();
        String max = maxWidthField.getText();
        if (min.matches("[0-9]+") && max.matches("[0-9]+")) {
            catFrame.performModification();
            ftController.setWidthRange(shortName, Integer.parseInt(min), Integer.parseInt(max));
        }
        else {
            String msg = "Width should be a positive number";
            JOptionPane.showMessageDialog(this,msg,"Invalid value",JOptionPane.ERROR_MESSAGE);
            minWidthField.setText(Integer.toString(ftController.getWidthRange(shortName).min));
            maxWidthField.setText(Integer.toString(ftController.getWidthRange(shortName).max));
        }
    }

    private void depthRangeUpdate() {
        hasDepthChanged = false;
        String min = minDepthField.getText();
        String max = maxDepthField.getText();
        if (min.matches("[0-9]+") && max.matches("[0-9]+")) {
            catFrame.performModification();
            ftController.setDepthRange(shortName, Integer.parseInt(min), Integer.parseInt(max));
        }
        else {
            String msg = "Depth should be a positive number";
            JOptionPane.showMessageDialog(this,msg,"Invalid value",JOptionPane.ERROR_MESSAGE);
            minDepthField.setText(Integer.toString(ftController.getDepthRange(shortName).min));
            maxDepthField.setText(Integer.toString(ftController.getDepthRange(shortName).max));
        }
    }

    private void passiveSpaceUpdate() {
        hasPassiveSpaceChanged = false;
        String str = passiveSpaceField.getText();
        str = str.replace(" ","");
        String[] s_ps = str.split(",");
        String passive = getFormattedPassiveSpace();
        try {
            catFrame.performModification();
            int[] i_ps = new int[4];
            for(int i = 0; i < Math.min(4, s_ps.length); i++) i_ps[i] = Integer.parseInt(s_ps[i]);
            ftController.setPassiveSpace(shortName, i_ps);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Invalid value",JOptionPane.ERROR_MESSAGE);
            passiveSpaceField.setText(passive);
        }
    }

    private String getFormattedPassiveSpace() {
        int[] ps = ftController.getPassiveSpace(shortName);
        return ps[0] + ", " + ps[1] + ", " + ps[2] + ", " + ps[3];
    }



}
