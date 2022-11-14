package Backend;

import Frontend.ChatWindow;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Thread for writing/communicating the message to other peers/clients
//jl
public class CommunicateThread extends Thread
{
    private Socket socket;
    private Peer peer;
    private PrintWriter communicate;
    private ChatWindow window;

    public CommunicateThread(Socket socket, Peer peer, ChatWindow window)
    {
        this.socket = socket;
        this.peer = peer;
        this.window = window;

        try
        {
            OutputStream output = socket.getOutputStream();
            communicate = new PrintWriter(output, true);
        } catch (IOException ex)
            {
                System.out.println("Error getting output stream: ");
                ex.printStackTrace();
            }
    }

    @Override
    public void run()
    {

        communicate.println(peer.getUniqueId()); // give Unique identifier to the peerThread
        String displayName = window.readUser("Enter your Display Name: ");// First Input is Display Name
        peer.setDisplayName(displayName);
        communicate.println(displayName);

        window.sendButton.addActionListener(new ActionListener()   //listens for input and button click, when peer enters Display Name
        {

            public void actionPerformed(ActionEvent actionEvent)
            {
                String text = window.getMsg();
                if (!text.equals(""))
                {
                    String encMsg = Encryption.encrypt(text, peer.getCurrentSecret());

                    communicate.println(encMsg);
                    window.cleanMsgField();
                    window.displayChatYou(text);
                }
            }
        });

    }
}

