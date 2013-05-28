/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing.views.catalogs;

import interiores.business.controllers.FurnitureTypeController;
import interiores.business.controllers.RoomTypeController;
import interiores.core.Debug;
import interiores.core.presentation.SwingController;
import interiores.utils.Range;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author larribas
 */
public final class RTCElement extends javax.swing.JPanel {

    
    // The actual name of the element contains its internal name within the application
    private String shortName;
    private String fullName;
    
    private boolean hasWidthChanged = false;
    private boolean hasDepthChanged = false;
    private boolean hasMandatoryListChanged = false;
    private boolean hasForbiddenListChanged = false;
    
    private RoomTypeCatalogFrame catFrame;
    private RoomTypeController rtController;
    private FurnitureTypeController ftController;
    
    
    /**
     * Creates new form RTCElement
     */
    public RTCElement(SwingController sw, String shortName, String fullName, RoomTypeCatalogFrame frame) {
        initComponents();
        
        this.shortName = shortName;
        this.fullName = fullName;
        
        catFrame = frame;
        rtController = sw.getBusinessController(RoomTypeController.class);
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
        rtname = new javax.swing.JLabel();
        measureLabel1 = new javax.swing.JLabel();
        measureField1 = new javax.swing.JTextField();
        measureLabel2 = new javax.swing.JLabel();
        measureField2 = new javax.swing.JTextField();
        measureLabel3 = new javax.swing.JLabel();
        mandatoryLabel = new javax.swing.JLabel();
        mandatoryField = new javax.swing.JTextField();
        forbiddenLabel = new javax.swing.JLabel();
        forbiddenField = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        innerPanel.setBackground(new java.awt.Color(255, 255, 255));
        innerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        rtname.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        rtname.setText("Name:");

        measureLabel1.setText("This room should measure more than");

        measureLabel2.setText("cm wide and");

        measureLabel3.setText("cm deep");

        mandatoryLabel.setText("Mandatory furniture:");

        forbiddenLabel.setText("Forbidden furniture:");

        org.jdesktop.layout.GroupLayout innerPanelLayout = new org.jdesktop.layout.GroupLayout(innerPanel);
        innerPanel.setLayout(innerPanelLayout);
        innerPanelLayout.setHorizontalGroup(
            innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(innerPanelLayout.createSequentialGroup()
                .add(16, 16, 16)
                .add(rtname)
                .add(83, 83, 83)
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(innerPanelLayout.createSequentialGroup()
                        .add(forbiddenLabel)
                        .add(18, 18, 18)
                        .add(forbiddenField))
                    .add(innerPanelLayout.createSequentialGroup()
                        .add(measureLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(measureField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(measureLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(measureField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(measureLabel3))
                    .add(innerPanelLayout.createSequentialGroup()
                        .add(mandatoryLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(mandatoryField)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        innerPanelLayout.setVerticalGroup(
            innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(innerPanelLayout.createSequentialGroup()
                .add(13, 13, 13)
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(rtname)
                    .add(measureLabel1)
                    .add(measureField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(measureLabel2)
                    .add(measureField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(measureLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(mandatoryLabel)
                    .add(mandatoryField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(forbiddenLabel)
                    .add(forbiddenField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(removeButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(17, 17, 17)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(removeButton))
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField forbiddenField;
    private javax.swing.JLabel forbiddenLabel;
    private javax.swing.JPanel innerPanel;
    private javax.swing.JTextField mandatoryField;
    private javax.swing.JLabel mandatoryLabel;
    private javax.swing.JTextField measureField1;
    private javax.swing.JTextField measureField2;
    private javax.swing.JLabel measureLabel1;
    private javax.swing.JLabel measureLabel2;
    private javax.swing.JLabel measureLabel3;
    private javax.swing.JButton removeButton;
    private javax.swing.JLabel rtname;
    // End of variables declaration//GEN-END:variables

    /**
    * Builds a visual representation of the item in the currently active catalog
    * whose name is 'actname'
    */
    public void initValues() {

        // These lines substitute the default aspect of a button with a custom icon
        ImageIcon im = new javax.swing.ImageIcon("src/resources/remove_element.png");
        im.setImage( im.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH) );
        removeButton.setIcon(im);
        removeButton.setBorder(BorderFactory.createEmptyBorder());
        removeButton.setContentAreaFilled(false);
        removeButton.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                Debug.println("A");
                catFrame.performModification();
                Debug.println("B");
                rtController.rm(shortName);
                Debug.println("C");
            }
        });

        // Properties of the inner panel
        innerPanel.setBackground(new java.awt.Color(255, 255, 255));
        innerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        // Name of the element
        rtname.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        rtname.setText(fullName + ":");

        // Measure fields (1: width, 2:depth)
        measureLabel1.setText("This room should measure more than");
        measureField1.setText(Integer.toString(rtController.getWidthRange(shortName).min) );
        measureField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasWidthChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) {  hasWidthChanged = true;  }
            @Override
            public void removeUpdate(DocumentEvent e) {  hasWidthChanged = true;  }
         });
        measureField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if(hasWidthChanged) widthUpdate(); }
        });
        measureLabel2.setText("cm wide and");
        measureField2.setText(Integer.toString(rtController.getDepthRange(shortName).min) );
        measureField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasDepthChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) {  hasDepthChanged = true;  }
            @Override
            public void removeUpdate(DocumentEvent e) {  hasDepthChanged = true;  }
         });
        measureField2.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if(hasDepthChanged) depthUpdate(); }
        });
        measureLabel3.setText("cm deep");

        // Mandatory and Forbidden furniture lists and actions
        mandatoryLabel.setText("Mandatory furniture:");
        String mandatory = rtController.getMandatory(shortName).toString();
        mandatoryField.setText(mandatory.substring(1, mandatory.length()-1));
        mandatoryField.setColumns(30);
        mandatoryField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasMandatoryListChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) {  hasMandatoryListChanged = true;  }
            @Override
            public void removeUpdate(DocumentEvent e) {  hasMandatoryListChanged = true;  }
         });
        mandatoryField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if (hasMandatoryListChanged) mandatoryUpdate(); }
        });
        forbiddenLabel.setText("Forbidden furniture:");
        String forbidden = rtController.getForbidden(shortName).toString();
        forbiddenField.setText(forbidden.substring(1, forbidden.length()-1));
        forbiddenField.setColumns(30);
        forbiddenField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { hasForbiddenListChanged = true; }
            @Override
            public void insertUpdate(DocumentEvent e) {  hasForbiddenListChanged = true;  }
            @Override
            public void removeUpdate(DocumentEvent e) {  hasForbiddenListChanged = true;  }
         });
        forbiddenField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { if (hasForbiddenListChanged) forbiddenUpdate(); }
        });


        // We update the tooltips at construction time, in order to initialize them
        updateTooltips();

    }
    
    
    /**
     * Consults all over again the information of the item and updates its state
     */
    public void updateChanges() {
        measureField1.setText(Integer.toString(rtController.getWidthRange(shortName).min) );
        measureField2.setText(Integer.toString(rtController.getDepthRange(shortName).min) );
        String mandatory = rtController.getMandatory(shortName).toString();
        mandatoryField.setText(mandatory.substring(1, mandatory.length()-1));
        String forbidden = rtController.getForbidden(shortName).toString();
        forbiddenField.setText(forbidden.substring(1, forbidden.length()-1));
    }               

    private void widthUpdate() {
        hasWidthChanged = false;
        String str = measureField1.getText();
        Range r = rtController.getWidthRange(shortName);
        if (str.matches("[0-9]+") && Integer.parseInt(str) <= r.max) {
            catFrame.performModification();
            rtController.setMinWidth(shortName, Integer.parseInt(str));
        }
        else {
            String msg = "Width should be a positive number between 0 and " + r.max;
            JOptionPane.showMessageDialog(this,msg,"Invalid value",JOptionPane.ERROR_MESSAGE);
            measureField1.setText(String.valueOf(rtController.getWidthRange(shortName).min));
        }
    }

    private void depthUpdate() {
        hasDepthChanged = false;
        String str = measureField2.getText();
        Range r = rtController.getDepthRange(shortName);
        if (str.matches("[0-9]+") && Integer.parseInt(str) <= r.max) {
            catFrame.performModification();
            rtController.setMinDepth(shortName, Integer.parseInt(str));
        }
        else {
            String msg = "Depth should be a positive number between 0 and " + r.max;
            JOptionPane.showMessageDialog(this,msg,"Invalid value",JOptionPane.ERROR_MESSAGE);
            measureField2.setText(String.valueOf(rtController.getDepthRange(shortName).min));
        }
    }

    private void mandatoryUpdate() {
        hasMandatoryListChanged = false;
        String str = mandatoryField.getText();
        str = str.replace(" ","");
        String[] mand = str.split(",");
        String mandatory = rtController.getMandatory(shortName).toString();
        try {
            catFrame.performModification();
            rtController.setMandatory(shortName, mand);
            updateTooltips();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Invalid value",JOptionPane.ERROR_MESSAGE);
            mandatory = mandatory.substring(1, mandatory.length()-1);
            mandatoryField.setText(mandatory);
        }
    }

    private void forbiddenUpdate() {
        hasForbiddenListChanged = false;
        String str = forbiddenField.getText();
        str = str.replace(" ","");
        String[] forb = str.split(",");
        String forbidden = rtController.getForbidden(shortName).toString();
        try {
            catFrame.performModification();
            rtController.setForbidden(shortName, forb);
            updateTooltips();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Invalid value",JOptionPane.ERROR_MESSAGE);
            forbidden = forbidden.substring(1, forbidden.length()-1);
            forbiddenField.setText(forbidden);
        }
    }

    /**
     * Updates the tooltips of the current item with all the necessary information.
     */
    private void updateTooltips() {
        Collection<String> cs = ftController.getUncategorizedFurniture(shortName);
        String s = "<html><strong>Available furniture:</strong><br>";
        for(String f : cs) s += f + "<br>"; s += "</html>";
        mandatoryField.setToolTipText(s);
        forbiddenField.setToolTipText(s);
    }


}
