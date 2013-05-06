/*
 */
package interiores.presentation.swing.views;

import interiores.business.controllers.DesignController;
import interiores.business.events.backtracking.ActualVariableSetEvent;
import interiores.business.events.backtracking.NextValueEvent;
import interiores.business.events.room.DebugRoomDesignStartedEvent;
import interiores.business.models.Orientation;
import interiores.core.Debug;
import interiores.core.presentation.annotation.Business;
import interiores.core.presentation.annotation.Listen;
import interiores.presentation.swing.SwingFrame;
import interiores.presentation.swing.views.map.RoomMapDebugger;
import java.awt.Point;

/**
 *
 * @author hector
 */
@Business(DesignController.class)
public class RoomMapDebuggerFrame extends SwingFrame
{
    private DesignController designController;
    private RoomMapPanel debuggee;
    private RoomMapDebugger map;
    private int currentIteration;
    private int skipIterations;
    
    /**
     * Creates new form RoomMapDebugger
     */
    public RoomMapDebuggerFrame(DesignController designController)
    {
        this.designController = designController;
        initComponents();
    }
    
    public void setDebuggee(RoomMapPanel debuggee)
    {
        this.debuggee = debuggee;
        this.map = (RoomMapDebugger) debuggee.getRoomMap();
    }

    @Override
    public void showView()
    {
        skipIterations = 1;
        reset();
        
        super.showView();
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        actualVariableNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        currentIterationLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        positionLabel = new javax.swing.JLabel();
        modelLabel = new javax.swing.JLabel();
        orientationLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        variablesList = new javax.swing.JList();
        iterateButton = new javax.swing.JButton();
        iterationsTextField = new javax.swing.JTextField();
        gridCheckBox = new javax.swing.JCheckBox();
        furnitureCheckBox = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();

        setTitle("Room map debugger");
        setAlwaysOnTop(true);

        jLabel1.setText("Actual variable:");

        actualVariableNameLabel.setText("Unknown");

        jLabel3.setText("Current iteration:");

        currentIterationLabel.setText("0");

        jLabel2.setText("Value information:");

        jLabel4.setText("Positon:");

        jLabel6.setText("Model:");

        jLabel7.setText("Orientation:");

        positionLabel.setText("Unknown");

        modelLabel.setText("Unknown");

        orientationLabel.setText("Unknown");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(currentIterationLabel))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actualVariableNameLabel))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(positionLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(orientationLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modelLabel)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(actualVariableNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(currentIterationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(positionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(modelLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(orientationLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        variablesList.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Not implemented yet" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(variablesList);

        iterateButton.setText("Iterate");
        iterateButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                iterateButtonActionPerformed(evt);
            }
        });

        iterationsTextField.setText("1");

        gridCheckBox.setText("Show grid");
        gridCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                gridCheckBoxActionPerformed(evt);
            }
        });

        furnitureCheckBox.setText("Show furniture");

        jLabel5.setText("Variable list:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(iterationsTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(iterateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gridCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(furnitureCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gridCheckBox)
                    .addComponent(furnitureCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iterateButton)
                    .addComponent(iterationsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iterateButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_iterateButtonActionPerformed
    {//GEN-HEADEREND:event_iterateButtonActionPerformed
        skipIterations = Integer.parseInt(iterationsTextField.getText());
        
        if(skipIterations < 1) skipIterations = 1;
        
        synchronized (this)
        {
            notify();
        }
    }//GEN-LAST:event_iterateButtonActionPerformed

    private void gridCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_gridCheckBoxActionPerformed
    {//GEN-HEADEREND:event_gridCheckBoxActionPerformed
       if(gridCheckBox.isSelected())
           map.enableGrid();
       else
           map.disableGrid();
       
       debuggee.repaint();
    }//GEN-LAST:event_gridCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel actualVariableNameLabel;
    private javax.swing.JLabel currentIterationLabel;
    private javax.swing.JCheckBox furnitureCheckBox;
    private javax.swing.JCheckBox gridCheckBox;
    private javax.swing.JButton iterateButton;
    private javax.swing.JTextField iterationsTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel modelLabel;
    private javax.swing.JLabel orientationLabel;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JList variablesList;
    // End of variables declaration//GEN-END:variables

    @Listen(DebugRoomDesignStartedEvent.class)
    public void debugDesignStarted() {
        showView();
    }
    
    @Listen(ActualVariableSetEvent.class)
    public void actualVariableSet(ActualVariableSetEvent event)
    {
        setActualVariableName(event.getVariableName());
    }
    
    @Listen(NextValueEvent.class)
    synchronized public void nextValue(NextValueEvent event)
            throws InterruptedException
    {
        updateCurrentIteration();
        updateValueInfo(event.getPosition(), event.getModelName(), event.getOrientation());
        
        map.addPoint(event.getPosition());
        
        if(skipIterations > 1)
        {
            --skipIterations;
            Debug.println("Iterations left: " + skipIterations);
        }
        else
        {
            debuggee.repaint();
            
            Debug.println("Solve paused");
            wait();
        }
    }
    
    private void reset()
    {
        map.clear();
        gridCheckBox.setSelected(map.isGridEnabled());
        
        setActualVariableName("Unknown");
        resetCurrentIteration();
        setValueInfo("Unknown", "Unknown", "Unknown");
    }
    
    private void setActualVariableName(String variableName)
    {
        actualVariableNameLabel.setText(variableName);
    }
        
    private void updateCurrentIteration()
    {
        setCurrentIteration(currentIteration + 1);
    }
    
    private void resetCurrentIteration()
    {
        setCurrentIteration(0);
    }
    
    private void setCurrentIteration(int iteration)
    {
        currentIteration = iteration;
        currentIterationLabel.setText(String.valueOf(iteration));
    }
    
    private void updateValueInfo(Point position, String modelName, Orientation orientation)
    {
        setValueInfo("(" + (int)position.getX() + ", " + (int)position.getY() + ")", modelName,
                orientation.toString(true));
    }
    
    private void setValueInfo(String position, String modelName, String orientation)
    {
        positionLabel.setText(position);
        modelLabel.setText(modelName);
        orientationLabel.setText(orientation);
    }
}
