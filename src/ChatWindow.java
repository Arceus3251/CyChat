import javax.swing.*;
import java.awt.*;

public class ChatWindow {
    public ChatWindow(String recipName){
        JFrame frame = new JFrame(recipName);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JMenuBar actionBar = new JMenuBar();
        //TODO: Add Top Bar Actions
        JPanel sendingPanel = new JPanel();
        //TODO: Add elements required to send chat message textField and send button, add action listener for enter.
        JScrollPane messagesPanel = new JScrollPane();
        //TODO: Take message packet and throw it on here.
        frame.add(BorderLayout.NORTH, actionBar);
        frame.add(BorderLayout.SOUTH, sendingPanel);
        frame.add(messagesPanel);
        frame.setVisible(true);
    }
}
