/*
 */
package interiores.presentation.swing.views;

import interiores.core.presentation.SwingController;
import interiores.presentation.swing.views.editor.EditorTool;
import interiores.presentation.swing.views.editor.RoomMapPanel;
import interiores.presentation.swing.views.editor.WishListPanel;
import interiores.presentation.swing.views.editor.tools.DoorTool;
import interiores.presentation.swing.views.editor.tools.MoveTool;
import interiores.presentation.swing.views.editor.tools.PillarTool;
import interiores.presentation.swing.views.editor.tools.SelectionTool;
import interiores.presentation.swing.views.editor.tools.WindowTool;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author hector
 */
public class MapEditorPanel extends JPanel
{
    private RoomMapPanel mapPanel;
    private Map<JToggleButton, EditorTool> tools;
    private Map<String, JToggleButton> names;
    private JToggleButton activeButton;
    
    /**
     * Creates new form MapEditorPanel
     */
    public MapEditorPanel(SwingController swing)
    {
        initComponents();
        
        mapPanel = swing.getNew(RoomMapPanel.class);
        WishListPanel wishListPanel = swing.getNew(WishListPanel.class);
        
        add(mapPanel, BorderLayout.CENTER);
        add(wishListPanel, BorderLayout.LINE_END);
        
        mapPanel.setVisible(true);
        wishListPanel.setVisible(true);
        
        tools = new HashMap();
        names = new HashMap();
        addTools(swing);
        setActiveButton(selectionButton);  
    }
    
    private void addTools(SwingController swing) {
        addTool(selectionButton, new SelectionTool(swing), "selection");
        addTool(moveButton, new MoveTool(), "move");
        addTool(doorButton, new DoorTool(swing), "door");
        addTool(windowButton, new WindowTool(swing), "window");
        addTool(pillarButton, new PillarTool(swing), "pillar");
    }
    
    private void addTool(JToggleButton button, EditorTool tool, String name) {
        tools.put(button, tool);
        names.put(name, button);
    }
    
    private void setActiveButton(JToggleButton button) {
        activeButton = button;
        activeButton.setSelected(true);
        mapPanel.setActiveTool(tools.get(activeButton));
    } 
    
    public void setActiveButton(String name) {
        if (names.containsKey(name))
            setActiveButton(names.get(name));
    }
   
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        toolBar = new javax.swing.JToolBar();
        selectionButton = new javax.swing.JToggleButton();
        moveButton = new javax.swing.JToggleButton();
        doorButton = new javax.swing.JToggleButton();
        windowButton = new javax.swing.JToggleButton();
        pillarButton = new javax.swing.JToggleButton();

        setLayout(new java.awt.BorderLayout());

        toolBar.setFloatable(false);
        toolBar.setOrientation(javax.swing.SwingConstants.VERTICAL);
        toolBar.setRollover(true);

        buttonGroup1.add(selectionButton);
        selectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cursor.png"))); // NOI18N
        selectionButton.setMnemonic('s');
        selectionButton.setToolTipText("Selection tool");
        selectionButton.setFocusable(false);
        selectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionButtonActionPerformed(evt);
            }
        });
        toolBar.add(selectionButton);

        buttonGroup1.add(moveButton);
        moveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/move.png"))); // NOI18N
        moveButton.setMnemonic('m');
        moveButton.setToolTipText("Move tool");
        moveButton.setFocusable(false);
        moveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        moveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        moveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveButtonActionPerformed(evt);
            }
        });
        toolBar.add(moveButton);

        buttonGroup1.add(doorButton);
        doorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/door.png"))); // NOI18N
        doorButton.setMnemonic('d');
        doorButton.setToolTipText("Door tool");
        doorButton.setFocusable(false);
        doorButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        doorButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        doorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doorButtonActionPerformed(evt);
            }
        });
        toolBar.add(doorButton);

        buttonGroup1.add(windowButton);
        windowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/window.png"))); // NOI18N
        windowButton.setMnemonic('w');
        windowButton.setToolTipText("Windows tool");
        windowButton.setFocusable(false);
        windowButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        windowButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        windowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windowButtonActionPerformed(evt);
            }
        });
        toolBar.add(windowButton);

        buttonGroup1.add(pillarButton);
        pillarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cube.png"))); // NOI18N
        pillarButton.setMnemonic('p');
        pillarButton.setToolTipText("Pillar tool");
        pillarButton.setFocusable(false);
        pillarButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pillarButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pillarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pillarButtonActionPerformed(evt);
            }
        });
        toolBar.add(pillarButton);

        add(toolBar, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void moveButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_moveButtonActionPerformed
    {//GEN-HEADEREND:event_moveButtonActionPerformed
        setActiveButton((JToggleButton) evt.getSource());
    }//GEN-LAST:event_moveButtonActionPerformed

    private void selectionButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_selectionButtonActionPerformed
    {//GEN-HEADEREND:event_selectionButtonActionPerformed
        setActiveButton((JToggleButton) evt.getSource());
    }//GEN-LAST:event_selectionButtonActionPerformed

private void doorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doorButtonActionPerformed
    setActiveButton((JToggleButton) evt.getSource());
}//GEN-LAST:event_doorButtonActionPerformed

private void windowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windowButtonActionPerformed
    setActiveButton((JToggleButton) evt.getSource());
}//GEN-LAST:event_windowButtonActionPerformed

private void pillarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pillarButtonActionPerformed
    setActiveButton((JToggleButton) evt.getSource());
}//GEN-LAST:event_pillarButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToggleButton doorButton;
    private javax.swing.JToggleButton moveButton;
    private javax.swing.JToggleButton pillarButton;
    private javax.swing.JToggleButton selectionButton;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JToggleButton windowButton;
    // End of variables declaration//GEN-END:variables
}
