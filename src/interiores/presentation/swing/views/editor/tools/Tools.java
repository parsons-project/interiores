package interiores.presentation.swing.views.editor.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 *
 * @author hector
 */
public class Tools {
    private List<EditorTool> tools;
    private ToolManageable manageable;
    private JMenu toolsMenu;
    private JToolBar toolBar;
    private ButtonGroup buttonGroup;
    private Map<EditorTool, JToggleButton> buttons;
    
    
    public Tools(ToolManageable manageable) {
        tools = new ArrayList();
        this.manageable = manageable;
        
        toolsMenu = new JMenu();
        toolsMenu.setText("Tools");
        toolsMenu.setName("Tools");
        
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setOrientation(JToolBar.VERTICAL);
        
        buttonGroup = new ButtonGroup();
        buttons = new HashMap();
    }
    
    public void add(EditorTool tool) {
        tools.add(tool);
        
        addMenuItem(tool);
        addButton(tool);
        
        if(tools.size() == 1)
            select(tool);
    }
    
    public void select(EditorTool tool) {
        if(!tools.contains(tool))
            return;
        
        manageable.setActiveTool(tool);
        buttons.get(tool).setSelected(true);
    }
    
    public JMenu getMenu() {
        return toolsMenu;
    }
    
    public JToolBar getToolBar() {
        return toolBar;
    }
    
    private void addMenuItem(final EditorTool tool) {
        JMenuItem toolMenuItem = new JMenuItem();
        
        toolMenuItem.setAccelerator(KeyStroke.getKeyStroke(tool.getKeyShortcut(), 0));
        toolMenuItem.setIcon(new ImageIcon(tool.getIconURL())); // NOI18N
        toolMenuItem.setText(tool.getName());
        
        toolMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                select(tool);
            }
        });
        
        toolsMenu.add(toolMenuItem);
    }
    
    private void addButton(final EditorTool tool) {
        JToggleButton selectionButton = new JToggleButton();
        buttons.put(tool, selectionButton);
        
        selectionButton.setIcon(new ImageIcon(tool.getIconURL())); // NOI18N
        selectionButton.setToolTipText(tool.getToolTipText());
        selectionButton.setFocusable(false);
        selectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectionButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                select(tool);
            }
        });
        
        buttonGroup.add(selectionButton);
        toolBar.add(selectionButton);
    }
}
