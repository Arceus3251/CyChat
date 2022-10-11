import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginPage {
    String[] information = new String[2];
    public LoginPage() {
        JFrame frame = new JFrame();
        frame.setSize(500, 200);
        GridLayout labelLayout = new GridLayout(2, 1);
        //Creating Text for side
        JLabel userLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(labelLayout);
        labelPanel.add(userLabel);
        labelPanel.add(passwordLabel);
        //Creating Fields for side
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(labelLayout);
        fieldPanel.add(userField);
        fieldPanel.add(passField);
        //Creating the submit button
        JButton submitButton = new JButton("Login");
        submitButton.addActionListener(e -> {
            information[0] = userField.getText();
            information[1] = Arrays.toString(passField.getPassword());
            frame.dispose();
        });
        //Begin adding objects to the frame
        frame.add(fieldPanel);
        frame.add(BorderLayout.WEST, labelPanel);
        frame.add(BorderLayout.SOUTH, submitButton);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public String[] getInfo(){
        return information;
    }
}
