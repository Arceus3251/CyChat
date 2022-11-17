package Frontend;

import Backend.Peer;
import Backend.ServerThread;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatWindow
{
    // all these correspond to the .form file, they are a part of the javax swing GUI library
    public JPanel chatWindowPanel;
    private JPanel serverInfoPanel;
    private JPanel peerInfoPanel;
    private JPanel chatPanel;
    private JPanel keyIdPanel;
    private JPanel sendPanel;

    private ChatWindow window;

    private JRadioButton serverButton;
    private JRadioButton peerToggle; //used this variable name to not be confused with other button in panel

    private JTextArea serverText;
    private JTextArea peerText;

    private JTextPane chatPane;
    private JTextField uniqueIdField;
    private JTextField messageField;
    private JTextField idField;

    private JButton addPeerKey;
    public JButton sendButton;
    private JButton saveButton;
    private JButton clearButton;

    private JLabel image;


    private ServerThread server;
    private Peer peer;


    private JSONObject message;
    private JSONArray currentPeerMessage;
    private JSONArray otherPeerMessage;

    public ChatWindow()
    {
        window = this;
        message = new JSONObject();
        currentPeerMessage = new JSONArray();
        otherPeerMessage = new JSONArray();
        serverButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {

                if (serverButton.isSelected()) // waits for server button inorder to turn on the server
                {
                    int port = -1;
                    String text = readUser("Enter Port Number:");

                    if (text != null)
                        port = Integer.parseInt(text);
                    else
                        setServerButton(false);

                    if (port != -1)
                    {
                        server = new ServerThread(port, window);
                        writeToServer("Connected to port: " + server.getPort());

                        server.start();//starts server thread
                    }

                }

                if (!serverButton.isSelected() && server != null)
                {
                    server.terminate = true;
                    try (Socket temp = new Socket("localhost", server.getPort());)
                    {

                        writeToServer("Server Disconnected!");

                    } catch (Exception e)
                        {
                            System.out.println(e.toString());
                        }
                }

            }
        });

        peerToggle.addActionListener(new ActionListener() //waits for peer button inorder to add yourself as new peer
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (peerToggle.isSelected())
                {
                    PeerWindow peerWindow = new PeerWindow();
                    peerWindow.setSize(300, 200);
                    peerWindow.setLocationRelativeTo(null);
                    peerWindow.setResizable(false);
                    peerWindow.setVisible(true);

                    if (peerWindow.getCancelState() == false)
                    {
                        sendButton.setEnabled(true);
                        addPeerKey.setEnabled(true);
                        clearButton.setEnabled(true);
                        saveButton.setEnabled(true);
                        String hostName = peerWindow.getHostName();
                        int portNumber = peerWindow.getPortNumber();
                        String secret = peerWindow.getUniqueID();
                        peer = new Peer(hostName, portNumber, secret, window);
                        peer.start();
                    } else
                        {
                            setPeerToggle(false);
                        }
                }

                if (!peerToggle.isSelected() && peer != null)
                {
                    sendButton.setEnabled(false);
                    addPeerKey.setEnabled(false);
                    clearButton.setEnabled(false);
                    saveButton.setEnabled(false);
                    setUniqueIdField("");
                    setIdField("");

                }
            }
        });

        addPeerKey.addActionListener(new ActionListener() //adds a peer's key and Id in order to decrypt messages
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                AddPeerWindow addPeerWindow = new AddPeerWindow();
                addPeerWindow.setSize(300, 180);
                addPeerWindow.setLocationRelativeTo(null);
                addPeerWindow.setResizable(false);
                addPeerWindow.setVisible(true);

                if (addPeerWindow.getCancelStatus() == false)
                {
                    String id = addPeerWindow.getKeyField();
                    String secret = addPeerWindow.getIdField();
                    peer.addSecretOfId(id, secret);
                }
            }
        });


        clearButton.addActionListener(new ActionListener() // clear chat panel
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                clearChat();
                clearMessageVars();
            }
        });


        saveButton.addActionListener(new ActionListener() // save current session chat log json file
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                try (FileWriter output = new FileWriter(getDate() + ".json");)
                {
                    message.put("Java App Name", "CyChat");
                    message.put("Your Message", currentPeerMessage);
                    message.put("Other's Message", otherPeerMessage);
                    output.write(message.toJSONString());
                    clearMessageVars();
                } catch (IOException e)
                    {
                        System.out.println(e.toString());
                    }

            }
        });
    }
    //used when saving chat log
    private String getDate()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); //year-month-day hour-minute-second
        return dateFormat.format(date);
    }
    // clears the arrays
    private void clearMessageVars()
    {
        message = new JSONObject();
        otherPeerMessage = new JSONArray();
        currentPeerMessage = new JSONArray();
    }

    public void setServerButton(boolean set)
    {
        serverButton.setSelected(set);

    }

    public void setPeerToggle(boolean set)
    {
        peerToggle.setSelected(set);

    }

    public void writeToServer(String text)
    {
        serverText.append(text + "\n");

    }

    public void displayChatYou(String text) //how it displays your text/message on your screen
    {
        currentPeerMessage.add("YOU: " + text);
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        StyleConstants.setFontSize(attributeSet, 15);
        StyleConstants.setForeground(attributeSet, Color.decode("#3cb371")); //light green color hex color
        Document doc = chatPane.getStyledDocument();

        try
        {
            doc.insertString(doc.getLength(), text + "\n", attributeSet);

        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    public void displayChatOther(String username, String text) //how it displays text/message on your screen when you get chat from others
    {
        otherPeerMessage.add(username + ": " + text);
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        StyleConstants.setFontSize(attributeSet, 15);
        StyleConstants.setForeground(attributeSet, Color.BLUE);
        StyleConstants.setBackground(attributeSet, Color.YELLOW);
        Document doc = chatPane.getStyledDocument();

        try
        {
            doc.insertString(doc.getLength(), "=(" + username + "):", attributeSet);
            StyleConstants.setBackground(attributeSet, Color.WHITE);
            doc.insertString(doc.getLength(), text + "\n", attributeSet);
        } catch (Exception e)
            {
                System.out.println(e.toString());
            }

    }



    public void writePeer(String text)
    {
        peerText.append(text + "\n");

    }

    public String readUser(String text)
    {
        String res = JOptionPane.showInputDialog(text);
        return res;

    }

    public String getMsg()
    {
        return messageField.getText();

    }

    public void setIdField(String id)
    {
        idField.setText(id);

    }

    public void clearChat()
    {
        chatPane.setText("");

    }

    public void cleanMsgField()
    {
        messageField.setText("");

    }

    public void setUniqueIdField(String code)
    {
        uniqueIdField.setText(code);

    }


}




