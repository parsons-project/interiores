/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing.views;

import interiores.business.controllers.RoomTypeController;
import interiores.business.controllers.RoomTypesCatalogController;
import interiores.business.events.catalogs.RTCatalogChangedEvent;
import interiores.business.events.catalogs.RTCatalogCheckoutEvent;
import interiores.business.events.catalogs.RTChangedEvent;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.annotation.Listen;
import interiores.presentation.swing.helpers.FileChooser;
import java.awt.Dimension;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

/**
 *
 * @author larribas
 */
public class RoomTypeCatalogFrame extends javax.swing.JFrame {

     private SwingController swing;
     private RoomTypeController rtController;
     private RoomTypesCatalogController rtcController;
     private JFileChooser fileChooser;
     
    
     private Map<String,RTC_Element> catElements;
    
    /**
     * Creates new form RoomTypeCatalogPanel
     */
    public RoomTypeCatalogFrame(SwingController presentation) {
        initComponents();
        
        this.swing = presentation;
        rtController = swing.getBusinessController(RoomTypeController.class);
        rtcController = swing.getBusinessController(RoomTypesCatalogController.class);
        fileChooser = new FileChooser();
        
        catElements = new HashMap();
        
        // Initially, we load the current catalog
        refreshCatalog();
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
        loadCatalogButton = new javax.swing.JButton();
        removeCatalogButton = new javax.swing.JButton();
        saveCatalogButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Room Types Catalog Editor");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 900, 600));
        setMaximumSize(new java.awt.Dimension(900, 600));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setPreferredSize(new java.awt.Dimension(900, 600));

        title1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        title1.setText("Room types catalog editor");
        title1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(900, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanel1);

        currentCatalogSelect.setBackground(new java.awt.Color(255, 255, 255));
        currentCatalogSelect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        currentCatalogSelect.setMaximumSize(new java.awt.Dimension(130, 32767));
        currentCatalogSelect.setMinimumSize(new java.awt.Dimension(90, 27));
        currentCatalogSelect.setPreferredSize(new java.awt.Dimension(130, 27));
        currentCatalogSelect.setSize(new java.awt.Dimension(130, 0));
        currentCatalogSelect.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                currentCatalogSelectItemStateChanged(evt);
            }
        });

        currentCatalogLabel.setText("Current catalog:");

        newCatalogButton.setText("New Catalog");
        newCatalogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCatalogButtonActionPerformed(evt);
            }
        });

        loadCatalogButton.setText("Load Catalog");
        loadCatalogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCatalogButtonActionPerformed(evt);
            }
        });

        removeCatalogButton.setText("Remove Catalog");
        removeCatalogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCatalogButtonActionPerformed(evt);
            }
        });

        saveCatalogButton.setText("Save Catalog");
        saveCatalogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCatalogButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(newCatalogButton)
                        .add(18, 18, 18)
                        .add(loadCatalogButton))
                    .add(title1))
                .add(301, 301, 301)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(saveCatalogButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(removeCatalogButton))
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(currentCatalogLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(currentCatalogSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(title1)
                    .add(currentCatalogLabel)
                    .add(currentCatalogSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(newCatalogButton)
                    .add(loadCatalogButton)
                    .add(removeCatalogButton)
                    .add(saveCatalogButton))
                .add(31, 31, 31)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void currentCatalogSelectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_currentCatalogSelectItemStateChanged
        rtcController.checkout(currentCatalogSelect.getSelectedItem().toString());
    }//GEN-LAST:event_currentCatalogSelectItemStateChanged

    private void loadCatalogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCatalogButtonActionPerformed
        loadCatalog();
    }//GEN-LAST:event_loadCatalogButtonActionPerformed

    private void saveCatalogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCatalogButtonActionPerformed
        saveCatalog();
    }//GEN-LAST:event_saveCatalogButtonActionPerformed

    private void removeCatalogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCatalogButtonActionPerformed
        rtcController.checkout("default");
        rtcController.remove(currentCatalogSelect.getSelectedItem().toString());
    }//GEN-LAST:event_removeCatalogButtonActionPerformed

    private void newCatalogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCatalogButtonActionPerformed
        NewCatalogDialog newCat = new NewCatalogDialog(rtcController);
        newCat.setVisible(true);
    }//GEN-LAST:event_newCatalogButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentCatalogLabel;
    private javax.swing.JComboBox currentCatalogSelect;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadCatalogButton;
    private javax.swing.JButton newCatalogButton;
    private javax.swing.JButton removeCatalogButton;
    private javax.swing.JButton saveCatalogButton;
    private javax.swing.JLabel title1;
    // End of variables declaration//GEN-END:variables

    
    
    @Listen({RTChangedEvent.class})
    public void updateCatalogElement(RTChangedEvent evt) {
        if (evt.isAdded()) addElement(evt.getFullName(),evt.getName());
        else removeElement(evt.getFullName());
    }

    @Listen({RTCatalogChangedEvent.class})
    public void updateCatalogList(RTCatalogChangedEvent evt) {
        if (evt.isAdded()) currentCatalogSelect.addItem(evt.getName());
        else currentCatalogSelect.removeItem(evt.getName());
    }
    
    @Listen({RTCatalogCheckoutEvent.class})
    public void updateSelectedCatalog(RTCatalogCheckoutEvent evt) {
        currentCatalogSelect.setSelectedItem(evt.getName());
        refreshCatalog();
    }
    
    private void initCatalogList() {
        Collection<String> catalogs = rtcController.getNamesLoadedCatalogs();
        Object[] s = catalogs.toArray();
        currentCatalogSelect.setModel(new javax.swing.DefaultComboBoxModel(s) );
    }
    
    private void refreshCatalog() {
        clearElements();
        
        // Retrieve all the elements in the catalog
        Map<String,String> rtypes = rtController.getFullNamesMap();
        
        // Each 'key' has the full name of the furniture, which in turn is accessed by its short name
        for (String key : rtypes.keySet()) {
            String rtn = rtypes.get(key); // 'rtn' is the short name (its actual name within the program)
            addElement(key, rtn);
        }
    }

    // This class represents an existent catalog element
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
            
            ImageIcon im = new javax.swing.ImageIcon("src/resources/remove_element.png");
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
        
        public void removeFromPanel() {
            jPanel1.remove(outerPanel);
        }
        
    }


    private void addElement(String key, String rtn) {
        int width = rtController.getWidthRange(rtn).min;
        int depth = rtController.getDepthRange(rtn).min;
        String mandatory = rtController.getMandatory(rtn).toString();
        mandatory = (String) mandatory.subSequence(1, mandatory.length()-1);

        String forbidden = rtController.getForbidden(rtn).toString();
        forbidden = (String) forbidden.subSequence(1, forbidden.length()-1);

        RTC_Element rtInstance = new RTC_Element(key,width,depth,mandatory,forbidden);
        rtInstance.addToPanel();
        catElements.put(key, rtInstance);
    }
    
    private void removeElement(String key) {
        if (catElements.containsKey(key)) {
            catElements.get(key).removeFromPanel();
            catElements.remove(key);
        }
    }
    
    private void clearElements() {
        jPanel1.removeAll();
        catElements.clear();
    }
    
    private void loadCatalog() {
        int status = fileChooser.showOpenDialog(this);
        
        if(status == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            try {
                rtcController.load(file.getAbsolutePath());
            }
            catch(JAXBException e) {
            }
        }
    }
    
    private void saveCatalog() {
        int status = fileChooser.showSaveDialog(this);
        
        if(status == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            try {
                rtcController.save(file.getAbsolutePath());
            }
            catch(JAXBException e) {
                
            }
        }
    }





}
