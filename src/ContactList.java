import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ContactList {
    JPanel framePanel = new JPanel();
    JMenuBar mb = new JMenuBar();
    public ContactList(String userName){
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        mb.add(fileMenu);
        JLabel userLabel = new JLabel(userName+": ");
        JComboBox<String> availableBox = new JComboBox<>();
        availableBox.addItem("Available");
        availableBox.addItem("Away");
        availableBox.addItem("Do Not Disturb");
        availableBox.addItem("Invisible");
        availableBox.addItem("Offline");
        availableBox.addActionListener(e->{
            //TODO: Send availability to the server
            //availableBox.getItemAt(availableBox.getSelectedIndex());
            //Send availability to the server
        });
        JPanel statusPanel = new JPanel();
        JScrollPane contactPanel = new JScrollPane();
        //TODO: Define structure to retrieve list of contacts
        //for contact: contacts{
            //TODO: Build panel for each contact
            //contactPanel.add(contactThing);
        statusPanel.add(BorderLayout.WEST, userLabel);
        statusPanel.add(BorderLayout.AFTER_LINE_ENDS, availableBox);
        framePanel.add(contactPanel);
        framePanel.add(BorderLayout.NORTH, statusPanel);
    }
    public JPanel getContent(){
        return framePanel;
    }
    public JMenuBar getMb(){
        return mb;
    }
}
