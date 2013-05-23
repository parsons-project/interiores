/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing.views;

import interiores.business.controllers.RoomTypeController;
import interiores.business.controllers.RoomTypesCatalogController;
import interiores.business.events.catalogs.CatalogChangedEvent;
import interiores.business.events.catalogs.RTCatalogChangedEvent;
import interiores.business.models.RoomType;
import interiores.core.Debug;
import interiores.core.Event;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.annotation.Listen;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 *
 * @author larribas
 */
public class RoomTypeCatalogPanel extends javax.swing.JPanel {

     private SwingController swing;
     private RoomTypeController rtController;
     private RoomTypesCatalogController rtcController;
    
     private List<RTC_Element> catElements;
    
    /**
     * Creates new form RoomTypeCatalogPanel
     */
    public RoomTypeCatalogPanel(SwingController presentation) {
        initComponents();
        
        this.swing = presentation;
        rtController = swing.getBusinessController(RoomTypeController.class);
        rtcController = swing.getBusinessController(RoomTypesCatalogController.class);
        
        catElements = new ArrayList();
        
        // Initially, we load the current catalog
        loadCatalog();
        initCatalogList();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        currentCatalogSelect = new javax.swing.JComboBox();
        currentCatalogLabel = new javax.swing.JLabel();
        newCatalogButton = new javax.swing.JButton();
        newCatalogButton.setBorder(BorderFactory.createEmptyBorder());
        newCatalogButton.setContentAreaFilled(false);

        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 900, 600));
        setMaximumSize(new java.awt.Dimension(900, 600));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setPreferredSize(new java.awt.Dimension(900, 600));

        title1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        title1.setText("Room types catalog");
        title1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(900, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanel1);

        currentCatalogSelect.setBackground(new java.awt.Color(255, 255, 255));
        currentCatalogSelect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        currentCatalogLabel.setText("Current catalog:");

        newCatalogButton.setBackground(new java.awt.Color(234, 234, 234));
        newCatalogButton.setText("Create new catalog");
        newCatalogButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .add(68, 68, 68)
                .add(title1)
                .add(97, 97, 97)
                .add(currentCatalogLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(currentCatalogSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(newCatalogButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(56, 56, 56)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(title1)
                    .add(currentCatalogSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(currentCatalogLabel)
                    .add(newCatalogButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentCatalogLabel;
    private javax.swing.JComboBox currentCatalogSelect;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newCatalogButton;
    private javax.swing.JLabel title1;
    // End of variables declaration//GEN-END:variables

    

    public void loadCatalog() {
        
        // Retrieve all the elements in the catalog
        Map<String,String> rtypes = rtController.getFullNamesMap();
        
        // Each 'key' has the full name of the furniture, which in turn is accessed by its short name
        for (String key : rtypes.keySet()) {
            
            String rtn = rtypes.get(key); // 'rtn' is the short name (its actual name within the program)
            
            int width = rtController.getWidthRange(rtn).min;
            int depth = rtController.getDepthRange(rtn).min;
            String mandatory = rtController.getMandatory(rtn).toString();
            mandatory = (String) mandatory.subSequence(1, mandatory.length()-1);
            
            String forbidden = rtController.getForbidden(rtn).toString();
            forbidden = (String) forbidden.subSequence(1, forbidden.length()-1);
            
            RTC_Element rtInstance = new RTC_Element(key,width,depth,mandatory,forbidden);
            rtInstance.addToPanel();
            
//            Debug.println("Creating a panel with the following specifications:\n"
//                    + "name: " + key + ", width: " + width + ", depth: " + depth
//                    + "\nMandatory: " + mandatory + "\nForbidden: " + forbidden);
            
        }       
    }
    
    public void initCatalogList() {
        Collection<String> catalogs = rtcController.getNamesLoadedCatalogs();
        Object[] s = catalogs.toArray();
        currentCatalogSelect.setModel(new javax.swing.DefaultComboBoxModel(s) );
    }

    @Listen({RTCatalogChangedEvent.class})
    public void updateCatalogList(CatalogChangedEvent evt) {
        if (evt.isAdded()) currentCatalogSelect.addItem(evt.getName());
        else currentCatalogSelect.removeItem(evt.getName());
    }
    

    // This class represents a catalog element
    class RTC_Element {
        private javax.swing.JPanel outerPanel = new javax.swing.JPanel();
        private javax.swing.JButton removeButton = new javax.swing.JButton();
        private javax.swing.JPanel innerPanel = new javax.swing.JPanel();;
        private javax.swing.JLabel rtname = new javax.swing.JLabel();
        private javax.swing.JTextField mandatoryField = new javax.swing.JTextField();
        private javax.swing.JLabel mandatoryLabel = new javax.swing.JLabel();
        private javax.swing.JTextField forbiddenField = new javax.swing.JTextField();
        private javax.swing.JLabel forbiddenLabel = new javax.swing.JLabel();
        private javax.swing.JTextField measureField1 = new javax.swing.JTextField();
        private javax.swing.JTextField measureField2 = new javax.swing.JTextField();
        private javax.swing.JLabel measureLabel1 = new javax.swing.JLabel();
        private javax.swing.JLabel measureLabel2 = new javax.swing.JLabel();
        private javax.swing.JLabel measureLabel3 = new javax.swing.JLabel();
        
        public RTC_Element(String rname, Integer width, Integer depth, String mandatory, String forbidden) {
            
            ImageIcon im = new javax.swing.ImageIcon(getClass().getResource("/resources/remove_element.png"));
            im.setImage( im.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH) );
            removeButton.setIcon(im); // NOI18N
            removeButton.setBorder(BorderFactory.createEmptyBorder());
            removeButton.setContentAreaFilled(false);
            
            
            innerPanel.setBackground(new java.awt.Color(255, 255, 255));
            innerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
            
            outerPanel.setBackground(new java.awt.Color(255, 255, 255));
            outerPanel.setSize(new Dimension(800, 200));
            outerPanel.setMinimumSize(new Dimension(800, 200));
            outerPanel.setMaximumSize(new Dimension(800, 200));

            rtname.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
            rtname.setText(rname + ":");

            measureLabel1.setText("This room should measure more than");
            measureField1.setText(width.toString());
//            measureField1.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    measureField1ActionPerformed(evt);
//                }
//            });

            measureLabel2.setText("cm wide and");
            measureField2.setText(depth.toString());
            measureLabel3.setText("cm deep");
            mandatoryLabel.setText("Mandatory furniture:");
            mandatoryField.setText(mandatory);
            mandatoryField.setColumns(30);
            forbiddenLabel.setText("Forbidden furniture:");
            forbiddenField.setText(forbidden);
            forbiddenField.setColumns(30);
            

            org.jdesktop.layout.GroupLayout innerPanelLayout = new org.jdesktop.layout.GroupLayout(innerPanel);
            innerPanel.setLayout(innerPanelLayout);
            innerPanelLayout.setHorizontalGroup(
                innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(innerPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(rtname)
                    .add(58, 58, 58)
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(innerPanelLayout.createSequentialGroup()
                            .add(measureLabel1)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(measureField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(measureLabel2)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(measureField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(measureLabel3))
                        .add(innerPanelLayout.createSequentialGroup()
                            .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(mandatoryLabel)
                                .add(forbiddenLabel))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(forbiddenField)
                                .add(mandatoryField))))
                    .addContainerGap(15, Short.MAX_VALUE))
            );
            innerPanelLayout.setVerticalGroup(
                innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(innerPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(rtname)
                        .add(measureLabel1)
                        .add(measureField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(measureLabel2)
                        .add(measureField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(measureLabel3))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(mandatoryLabel)
                        .add(mandatoryField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(forbiddenField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(forbiddenLabel))
                    .addContainerGap(15, Short.MAX_VALUE))
            );
            
            org.jdesktop.layout.GroupLayout outerPanelLayout = new org.jdesktop.layout.GroupLayout(outerPanel);
            outerPanel.setLayout(outerPanelLayout);
            outerPanelLayout.setHorizontalGroup(
                outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(outerPanelLayout.createSequentialGroup()
                    .add(removeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(768, Short.MAX_VALUE))
                .add(outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(outerPanelLayout.createSequentialGroup()
                        .add(0, 93, Short.MAX_VALUE)
                        .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 93, Short.MAX_VALUE)))
            );
            outerPanelLayout.setVerticalGroup(
                outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(0, 0, Short.MAX_VALUE)
                .add(outerPanelLayout.createSequentialGroup()
                    .add(26, 26, 26)
                    .add(removeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE))
                .add(outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(outerPanelLayout.createSequentialGroup()
                        .add(0, 24, Short.MAX_VALUE)
                        .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 25, Short.MAX_VALUE)))
            );
        }

        
        public void addToPanel() {
            jPanel1.add(outerPanel);
        }
        
    }








}
