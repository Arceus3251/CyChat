import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class LoginPage {
    String[] information = new String[2];
    public LoginPage() {
        JFrame frame = new JFrame();
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setTitle("CyChat Login");
        GridLayout labelLayout = new GridLayout(3, 1);
        //Creating Text for side
        JLabel userLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel retypeLabel = new JLabel("Retype Password:");
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(labelLayout);
        labelPanel.add(userLabel);
        labelPanel.add(passwordLabel);
        JPasswordField retypePass = new JPasswordField();
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
            submitAction(userField, passField, frame);
        });
        passField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    submitAction(userField, passField, frame);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        //Creating the SignUp Button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e ->{
            if(!((Arrays.toString(passField.getPassword())).equals(Arrays.toString(retypePass.getPassword())))){
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please Try again.");
            }
            else {
                registerAction(userField.getText(), passField.getPassword());
                JOptionPane.showMessageDialog(null, "User Created! Please log in.");
                userField.setText("");
                labelPanel.remove(2);
                fieldPanel.remove(2);
                frame.remove(signUpButton);
                frame.add(BorderLayout.SOUTH, submitButton);
                passField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){
                            submitAction(userField, passField, frame);
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                labelPanel.updateUI();
            }
        });
        //Adding New User functionality
        JCheckBox newUserBox = new JCheckBox();
        newUserBox.setText("New User?");
        newUserBox.addActionListener(e->{
            labelPanel.remove(2);
            labelPanel.add(retypeLabel);
            fieldPanel.add(retypePass);
            labelPanel.updateUI();
            frame.remove(submitButton);
            frame.add(BorderLayout.SOUTH, signUpButton);
            passField.removeKeyListener(passField.getKeyListeners()[0]);
            retypePass.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (!((Arrays.toString(passField.getPassword())).equals(Arrays.toString(retypePass.getPassword())))) {
                            JOptionPane.showMessageDialog(null, "Passwords do not match. Please Try again.");
                        } else {
                            registerAction(userField.getText(), passField.getPassword());
                            JOptionPane.showMessageDialog(null, "User Created! Please log in.");
                            userField.setText("");
                            labelPanel.remove(2);
                            fieldPanel.remove(2);
                            passField.addKeyListener(new KeyListener() {
                                @Override
                                public void keyTyped(KeyEvent e) {

                                }

                                @Override
                                public void keyPressed(KeyEvent e) {
                                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                                        submitAction(userField, passField, frame);
                                    }
                                }

                                @Override
                                public void keyReleased(KeyEvent e) {

                                }
                            });
                            frame.remove(signUpButton);
                            frame.add(BorderLayout.SOUTH, submitButton);
                            labelPanel.updateUI();
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        });
        labelPanel.add(newUserBox);
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
    public void submitAction(JTextField userField, JPasswordField passField, JFrame frame){
        information[0] = userField.getText();
        information[1] = Arrays.toString(passField.getPassword());
        frame.dispose();
    }
    public void registerAction(String userField, char[] password){
        //TODO:Sign the user up for CyChat
    }
}
