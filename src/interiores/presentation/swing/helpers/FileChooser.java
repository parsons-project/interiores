package interiores.presentation.swing.helpers;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author hector
 */
public class FileChooser
    extends JFileChooser
{
    @Override
    public void approveSelection() {
        File f = getSelectedFile();
        
        if(f.exists() && getDialogType() == SAVE_DIALOG) {
            int result = JOptionPane.showConfirmDialog(this, "The selected file already exists. Do you want to "
                    + "overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result) {
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                    
                case JOptionPane.NO_OPTION:
                    return;
                    
                case JOptionPane.CLOSED_OPTION:
                    return;
                    
                default:
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        
        super.approveSelection();
    }
}
