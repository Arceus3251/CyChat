import javax.swing.*;
import java.awt.*;
import java.net.

public class ChatWindow extends JFrame {
    //public ChatWindow(){
        //initComponents();
    private void intiComponents(){
        JLabel heading=new JLabel("Chat");
        JTextArea messageScreen= new JTextArea();
        JTextField messageInput=new JTextField();
        Font font=new Font("Roboto",Font.PLAIN,20);

    }
    public ChatWindow(){
        try{
            System.out.println("sending request");
            socket =new Socket("127.0.0.1",7777 );
            System.out.println("successful connection");
        }

    }
}
