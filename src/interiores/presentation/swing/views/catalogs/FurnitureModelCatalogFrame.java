
package interiores.presentation.swing.views.catalogs;

import interiores.business.controllers.FurnitureModelController;
import interiores.business.controllers.FurnitureTypeController;
import interiores.business.events.catalogs.FMModifiedEvent;
import interiores.business.events.catalogs.FMSetModifiedEvent;
import interiores.business.events.catalogs.FTSetModifiedEvent;
import interiores.core.Debug;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.annotation.Listen;
import interiores.utils.CoolColor;
import interiores.utils.Material;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class represents the furniture model catalog editor, where the user can edit
 * the catalog of furniture models for a type.
 * N.B. Here, we will consider a catalog to be the type of furniture, and thus name it accordingly
 * @author larribas
 */
public class FurnitureModelCatalogFrame extends javax.swing.JFrame {

    // The frame has access to these controllers, and its own fileChooser
    private SwingController swing;
    private FurnitureTypeController ftController;
    private FurnitureModelController fmController;

    // The frame stores a map containing  all the elements in the currently
    // activer catalog. This map is modified everytime the catalog changes
    // or a particular element does. Changes from any other presentation layer
    // are automatically reflected
    private Map<String,FMC_Element> catElements;
    
    /**
     * Creates the very FMC editor frame
     * @param presentation 
     */
    public FurnitureModelCatalogFrame(SwingController presentation) {
        // We initialize all the fundamental components of the editor
        initComponents();
        
        // We reference all the approriate controllers internally
        this.swing = presentation;
        ftController = swing.getBusinessController(FurnitureTypeController.class);
        fmController = swing.getBusinessController(FurnitureModelController.class);
        
        // We initialize the data structures the frame will use
        catElements = new HashMap();
        
        // We load the list with all catalogs, set the required visual settings,
        // and load all the elements in the catalog
        initVisualSettings();
        boolean thereAreElements = initCatalogList();
        if (thereAreElements) refreshCatalog();
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
        removeCatalogButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        newButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Furniture Models Catalog Editor");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 900, 600));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(900, 700));

        title1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        title1.setText("Furniture types catalog editor");
        title1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(900, 0));
        jScrollPane1.setViewportView(null);

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

        currentCatalogLabel.setText("Current furniture type:");

        newCatalogButton.setText("New furniture type");
        newCatalogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCatalogButtonActionPerformed(evt);
            }
        });

        removeCatalogButton.setText("Remove furniture type");
        removeCatalogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCatalogButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Add a new furniture model");

        ImageIcon im = new javax.swing.ImageIcon("src/resources/add_element.png");
        im.setImage( im.getImage().getScaledInstance(35,35,java.awt.Image.SCALE_SMOOTH) );
        newButton.setIcon(im); // NOI18N
        newButton.setBorder(BorderFactory.createEmptyBorder());
        newButton.setContentAreaFilled(false);
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(newCatalogButton)
                            .add(title1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(removeCatalogButton)
                            .add(layout.createSequentialGroup()
                                .add(currentCatalogLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(currentCatalogSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(layout.createSequentialGroup()
                        .add(333, 333, 333)
                        .add(newButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1)
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 900, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
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
                    .add(removeCatalogButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 515, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, newButton))
                .addContainerGap(7, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Triggers when a different catalog is selected. It basically performs a checkout to the selected catalog
     */
    private void currentCatalogSelectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_currentCatalogSelectItemStateChanged
        refreshCatalog();
    }//GEN-LAST:event_currentCatalogSelectItemStateChanged

    /**
     * Triggers when the "Remove catalog" button is pressed. It removes the current catalog
     * and checks out the default one (which cannot be modified or removed)
     */
    private void removeCatalogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCatalogButtonActionPerformed
        ftController.rm( currentCatalogSelect.getSelectedItem().toString() );
    }//GEN-LAST:event_removeCatalogButtonActionPerformed

    /**
     * Triggers when the "New catalog" button is pressed. It shows a dialog asking the
     * properties of the new catalog, and creates it.
     */
    private void newCatalogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCatalogButtonActionPerformed
        NewFurnitureTypeDialog ftd = new NewFurnitureTypeDialog(ftController);
        ftd.setVisible(true);
    }//GEN-LAST:event_newCatalogButtonActionPerformed

    /**
     * Triggers when the "New element" (+) button is pressed. It shows a dialog asking the
     * properties of the new element, and creates it.
     */
    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        NewFurnitureModelDialog fmd = new NewFurnitureModelDialog(fmController,currentCatalogSelect.getSelectedItem().toString());
        fmd.setVisible(true);
    }//GEN-LAST:event_newButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentCatalogLabel;
    private javax.swing.JComboBox currentCatalogSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newButton;
    private javax.swing.JButton newCatalogButton;
    private javax.swing.JButton removeCatalogButton;
    private javax.swing.JLabel title1;
    // End of variables declaration//GEN-END:variables

    
    /**
     * This function is invoked whenever a particular element of the catalog
     * is added or removed, so that those changes reflect upon this frame
     * @param evt The tell-tale event
     */
    @Listen({FMSetModifiedEvent.class})
    public void updateCatalogElementSet(FMSetModifiedEvent evt) {
        if ( evt.getFurniture().equals(currentCatalogSelect.getSelectedItem().toString()) ) {
            if (evt.isAdded()) addElement(evt.getName());
            else {
                removeElement(evt.getName());
                if (catElements.isEmpty()) showEmptyCatalogMessage();
            }
            refresh();
        }
    }
    
    @Listen({FMModifiedEvent.class})
    public void updateElement(FMModifiedEvent evt) {
        if ( evt.getFurniture().equals(currentCatalogSelect.getSelectedItem().toString()) ) {
            if (catElements.containsKey(evt.getName()))
                catElements.get(evt.getName()).refreshElementValues();
            refresh();
        }
    }
    
    
    /**
     * This function is invoked whenever the list of available catalogs changes,
     * so that those changes reflect upon this frame
     * @param evt The tell-tale event
     */
    @Listen({FTSetModifiedEvent.class})
    public void updateCatalogList(FTSetModifiedEvent evt) {
        if (evt.isAdded()) {
            currentCatalogSelect.addItem(evt.getName());
            currentCatalogSelect.setSelectedItem(evt.getName());
        }
        else currentCatalogSelect.removeItem(evt.getName());
    }
    
    /**
     * This method is called when a full recognition of the current catalog's
     * elements is needed. It clears the element list and loads the appropriate one
     */
    private void refreshCatalog() {
        clearElements();
        
        // Retrieve all the elements in the catalog
        Collection<String> fmodels = fmController.getFurnitureModelNames(currentCatalogSelect.getSelectedItem().toString());
        
        if (fmodels.isEmpty()) showEmptyCatalogMessage();
        else for (String fm : fmodels) addElement(fm);
        
        refresh();
    }

    /**
     * Loads a new element to the current catalog display
     * @param fm The name of the element
     */
    private void addElement(String fm) {
        FMC_Element fmcInstance = new FMC_Element(currentCatalogSelect.getSelectedItem().toString(),fm);
        fmcInstance.addToPanel();
        catElements.put(fm, fmcInstance);
    }
    
    /**
     * Removes a specific element from the current catalog display
     * @param fm The name of the element
     */
    private void removeElement(String fm) {
        if (catElements.containsKey(fm)) {
            catElements.get(fm).removeFromPanel();
            catElements.remove(fm);
        }
    }
    
    /**
     * Clears all the catalog elements currently loaded.
     * This work is needed not only visually but also structurally
     */
    private void clearElements() {
        jPanel1.removeAll();
        catElements.clear();
    }
    
    /**
     * It loads the available catalogs into the frame
     */
    private boolean initCatalogList() {
        Collection<String> catalogs = ftController.getFullNamesMap().values();
        if (catalogs.isEmpty()) return false;
        else {
            Object[] s = catalogs.toArray();
            currentCatalogSelect.setModel(new javax.swing.DefaultComboBoxModel(s) );
            currentCatalogSelect.setSelectedIndex(0);
            return true;
        }
    }
    
    /**
     * Groups all the custom visual setting this frame has to initialize when loading
     */
    private void initVisualSettings() {
        
    }

    /**
     * Groups the appropriate swing routine we have to perform each time we need to
     * refresh (or redraw) the contents of the main panel
     */
    private void refresh() {
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
    private boolean showRemovalWarning() {
        String msg = "Are you sure you want to remove this model from the catalog?";
        int choice = JOptionPane.showConfirmDialog(this,msg,"Confirmation warning", JOptionPane.YES_NO_OPTION);
        return (choice == JOptionPane.YES_OPTION);
    }
    
    private boolean showSaveWarning() {
        String msg = "Are you sure you want to save changes to this model?";
        int choice = JOptionPane.showConfirmDialog(this,msg,"Confirmation warning", JOptionPane.YES_NO_OPTION);
        return (choice == JOptionPane.YES_OPTION);
    }

    private void showEmptyCatalogMessage() {
        javax.swing.JLabel emptyLabel = new javax.swing.JLabel();
        emptyLabel.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        emptyLabel.setText("There are no particular models of " + currentCatalogSelect.getSelectedItem().toString() + "s.");
        jPanel1.add(emptyLabel);
    }
       

    /**
     * This class is a visual representation of a catalog item.
     * It serves as an auxiliary data structure to hold a whole information pane
     * with its own features, events, and properties.
     */
    class FMC_Element {
        // The name of the element
        private String actualName;
        
        // ftname, on the other hand, contains a visually clearer name
        private javax.swing.JLabel fmname = new javax.swing.JLabel();
        
        // The outer panel holds the remove button, the save button, and the inner panel
        private javax.swing.JPanel outerPanel = new javax.swing.JPanel();
            private javax.swing.JButton removeButton = new javax.swing.JButton();
            private javax.swing.JButton saveButton = new javax.swing.JButton();
            private javax.swing.JPanel innerPanel = new javax.swing.JPanel();
        
        // The inner panel holds all the labels and text fields related to the properties of an element
        private javax.swing.JLabel measureLabel1 = new javax.swing.JLabel();
        private javax.swing.JLabel measureLabel2 = new javax.swing.JLabel();
        private javax.swing.JLabel measureLabel3 = new javax.swing.JLabel();
        private javax.swing.JTextField widthField = new javax.swing.JTextField();
        private javax.swing.JTextField depthField = new javax.swing.JTextField();
        
        private javax.swing.JLabel materialLabel = new javax.swing.JLabel();
        private javax.swing.JComboBox materialSelector = new javax.swing.JComboBox();
        
        private javax.swing.JLabel colorLabel = new javax.swing.JLabel();
        private javax.swing.JComboBox colorSelector = new javax.swing.JComboBox();
        
        private javax.swing.JLabel passiveSpaceLabel = new javax.swing.JLabel();
        private javax.swing.JLabel passiveSpaceHint = new javax.swing.JLabel();
        private javax.swing.JTextField passiveSpaceField = new javax.swing.JTextField();
        
        private javax.swing.JLabel priceLabel = new javax.swing.JLabel();
        private javax.swing.JLabel priceLabel2 = new javax.swing.JLabel();
        private javax.swing.JTextField priceField = new javax.swing.JTextField();
        
        private boolean hasWidthChanged = false;
        private boolean hasDepthChanged = false;
        private boolean hasColorChanged = false;
        private boolean hasMaterialChanged = false;
        private boolean hasPriceChanged = false;
        private boolean hasPassiveSpaceChanged = false;
                
        
        private final Color fieldBackground = new java.awt.Color(255, 255, 204);
        private final javax.swing.border.LineBorder selectorForeground = 
                new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 204), 2, true);
        /**
         * Builds a visual representation of the item in the currently active catalog
         * whose name is 'actname'
         * @param actname The actual (internal) id of the element
         * @param fullname A visually clearer name for such an element
         */
        public FMC_Element(String ft, String fm) {
            // First of all, we give the element a name
            actualName = fm;
                       
            // We set the properties of the outer panel
            outerPanel.setBackground(new java.awt.Color(255, 255, 255));
            outerPanel.setPreferredSize(new Dimension(750,200));
            outerPanel.setSize(new Dimension(750, 200));
            outerPanel.setMinimumSize(new Dimension(750, 200));
            outerPanel.setMaximumSize(new Dimension(750, 200));
            
            // These lines substitute the default aspect of a button with a custom icon
            ImageIcon im = new javax.swing.ImageIcon("src/resources/remove_element.png");
            im.setImage( im.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH) );
            removeButton.setIcon(im);
            removeButton.setBorder(BorderFactory.createEmptyBorder());
            removeButton.setContentAreaFilled(false);
            removeButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (showRemovalWarning()) fmController.rm(currentCatalogSelect.getSelectedItem().toString(), actualName);
                }
            });
            
            im = new javax.swing.ImageIcon("src/resources/save_element.png");
            im.setImage( im.getImage().getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH) );
            saveButton.setIcon(im);
            saveButton.setBorder(BorderFactory.createEmptyBorder());
            saveButton.setContentAreaFilled(false);
            saveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (isElementModified() && showSaveWarning()) saveElement();
                }
            });
            
            // Properties of the inner panel
            innerPanel.setBackground(new java.awt.Color(255, 255, 255));
            innerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
            
            // Name of the element
            fmname.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
            fmname.setText(actualName + ":");
                    
            refreshElementValues();
            
            measureLabel1.setText("This model is");
            measureLabel2.setText("cm wide, and ");
            measureLabel3.setText("cm deep");
            widthField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) { widthChange(); }
                @Override
                public void insertUpdate(DocumentEvent e) { widthChange(); }
                @Override
                public void removeUpdate(DocumentEvent e) { widthChange(); }
             });
            depthField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) { depthChange(); }
                @Override
                public void insertUpdate(DocumentEvent e) { depthChange(); }
                @Override
                public void removeUpdate(DocumentEvent e) { depthChange(); }
             });
            
            materialLabel.setText("It is made from");
            materialSelector.addItemListener(new java.awt.event.ItemListener() {
                @Override
                public void itemStateChanged(java.awt.event.ItemEvent evt) { materialChange(); }
            });
            
            
            colorLabel.setText("and painted in");
            colorSelector.addItemListener(new java.awt.event.ItemListener() {
                @Override
                public void itemStateChanged(java.awt.event.ItemEvent evt) { colorChange(); }
            });

            passiveSpaceLabel.setText("It should always have the following free space to its sides:");
            passiveSpaceHint.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
            passiveSpaceHint.setForeground(new java.awt.Color(153, 153, 153));
            passiveSpaceHint.setText("Format: N,E,S,W. e.g. 10,0,0,20");
            passiveSpaceField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) { passiveSpaceChange(); }
                @Override
                public void insertUpdate(DocumentEvent e) { passiveSpaceChange(); }
                @Override
                public void removeUpdate(DocumentEvent e) { passiveSpaceChange(); }
             });

            priceLabel.setText("Price");
            priceLabel2.setText("€");
            priceField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) { priceChange(); }
                @Override
                public void insertUpdate(DocumentEvent e) { priceChange(); }
                @Override
                public void removeUpdate(DocumentEvent e) { priceChange(); }
             });

            org.jdesktop.layout.GroupLayout innerPanelLayout = new org.jdesktop.layout.GroupLayout(innerPanel);
            innerPanel.setLayout(innerPanelLayout);
            innerPanelLayout.setHorizontalGroup(
                innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(innerPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(fmname)
                        .add(priceLabel)
                        .add(priceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(priceLabel2)
                    .add(18, 42, Short.MAX_VALUE)
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(innerPanelLayout.createSequentialGroup()
                            .add(passiveSpaceHint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 207, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(18, 18, 18)
                            .add(passiveSpaceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(passiveSpaceLabel)
                        .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(innerPanelLayout.createSequentialGroup()
                                .add(measureLabel1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(widthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(measureLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(depthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(measureLabel3)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(innerPanelLayout.createSequentialGroup()
                                .add(materialLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(materialSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(colorLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 11, Short.MAX_VALUE)
                                .add(colorSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(0, 16, Short.MAX_VALUE)))))
            );
            innerPanelLayout.setVerticalGroup(
                innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(innerPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(fmname)
                        .add(measureLabel1)
                        .add(widthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(measureLabel2)
                        .add(depthField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(measureLabel3))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(materialLabel)
                        .add(colorLabel)
                        .add(materialSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(colorSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(passiveSpaceLabel)
                        .add(priceLabel))
                    .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(innerPanelLayout.createSequentialGroup()
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(passiveSpaceHint)
                                .add(passiveSpaceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(innerPanelLayout.createSequentialGroup()
                            .add(4, 4, 4)
                            .add(innerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(priceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(priceLabel2))))
                    .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            org.jdesktop.layout.GroupLayout outerPanelLayout = new org.jdesktop.layout.GroupLayout(outerPanel);
            outerPanel.setLayout(outerPanelLayout);
            outerPanelLayout.setHorizontalGroup(
                outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, outerPanelLayout.createSequentialGroup()
                    .addContainerGap(58, Short.MAX_VALUE)
                    .add(outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, removeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(53, 53, 53))
            );
            outerPanelLayout.setVerticalGroup(
                outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, outerPanelLayout.createSequentialGroup()
                    .addContainerGap(27, Short.MAX_VALUE)
                    .add(outerPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(outerPanelLayout.createSequentialGroup()
                            .add(removeButton)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(saveButton))
                        .add(innerPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
            );
            
        }

        /**
         * Everything built, this operation adds the outer panel to the frame display 
         */
        public void addToPanel() {
            jPanel1.add(outerPanel);
        }
        
        /**
         * Everything built, this operation removes the outer panel from the frame display
         */
        public void removeFromPanel() {
            jPanel1.remove(outerPanel);
        }
        
        private boolean isElementModified() {
            return hasColorChanged || hasDepthChanged || hasMaterialChanged
                || hasPassiveSpaceChanged || hasPriceChanged || hasWidthChanged;
        }
        
        private void saveElement() {
            
            try {
                String ft = currentCatalogSelect.getSelectedItem().toString();
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
                fmController.replace(ft, actualName, width, depth, price, color, material, i_ps);
                
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(FurnitureModelCatalogFrame.this,e.getMessage(),"Invalid value",JOptionPane.ERROR_MESSAGE);
                refreshElementValues();
            }
            
        }
        
        private void refreshElementValues() {
            // First, we load all the appropriate values
            String ft = currentCatalogSelect.getSelectedItem().toString();
            String fm = actualName;
            widthField.setText(Integer.toString(fmController.getSize(ft,fm).width) );
            depthField.setText(Integer.toString(fmController.getSize(ft,fm).depth) );
            Object[] m= Material.getNames().toArray();
            materialSelector.setModel(new javax.swing.DefaultComboBoxModel(m) );
            materialSelector.setSelectedItem(fmController.getMaterial(ft, fm).toLowerCase());
            Object[] c = CoolColor.getNames().toArray();
            colorSelector.setModel(new javax.swing.DefaultComboBoxModel(c) );
            colorSelector.setSelectedItem(fmController.getColor(ft, fm).toLowerCase());
            passiveSpaceField.setText(getFormattedPassiveSpace());
            priceField.setText(Float.toString(fmController.getPrice(ft, fm)) );
            
            // Then, we reset the state of the UI
            hasColorChanged = hasDepthChanged = hasMaterialChanged = false;
            hasPassiveSpaceChanged = hasPriceChanged = hasWidthChanged = false;

            widthField.setBackground(Color.white);
            depthField.setBackground(Color.white);
            priceField.setBackground(Color.white);
            passiveSpaceField.setBackground(Color.white);
            colorSelector.setBorder(BorderFactory.createEmptyBorder());
            materialSelector.setBorder(BorderFactory.createEmptyBorder());

            saveButton.setVisible(false);
        }
        
        private void widthChange() {
            if (!hasWidthChanged) {
                hasWidthChanged = true;
                widthField.setBackground(fieldBackground);
                saveButton.setVisible(true);
            }
        }
        
        private void depthChange() {
            if (!hasDepthChanged) {
                hasDepthChanged = true;
                depthField.setBackground(fieldBackground);
                saveButton.setVisible(true);
            }
        }
        
        private void materialChange() {
            if (!hasMaterialChanged) {
                hasMaterialChanged = true;
                materialSelector.setBorder(selectorForeground);
                saveButton.setVisible(true);
            }
        }
        
        private void colorChange() {
            if (!hasColorChanged) {
                hasColorChanged = true;
                colorSelector.setBorder(selectorForeground);
                saveButton.setVisible(true);
            }
        }
        
        private void priceChange() {
            if (!hasPriceChanged) {
                hasPriceChanged = true;
                priceField.setBackground(fieldBackground);
                saveButton.setVisible(true);
            }
        }
        
        private void passiveSpaceChange() {
            if (!hasPassiveSpaceChanged) {
                hasPassiveSpaceChanged = true;
                passiveSpaceField.setBackground(fieldBackground);
                saveButton.setVisible(true);
            }
        }
        
        
        private String getFormattedPassiveSpace() {
            int[] ps = fmController.getPassiveSpace(currentCatalogSelect.getSelectedItem().toString(), actualName);
            return ps[0] + ", " + ps[1] + ", " + ps[2] + ", " + ps[3];
        }

    }
    /*
     * End of RTC_Element Class
     */

}
/*
 * End of the main class
 */
