package Backend;

import Frontend.ChatWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

//Specific thread for reading and displaying the message
//jl
public class DisplayThread extends Thread
{
    private Socket socket;
    private Peer client;
    private BufferedReader reader;
    private ChatWindow window;

    public DisplayThread(Socket socket, Peer client, ChatWindow window)
    {
        this.socket = socket;
        this.client = client;
        this.window = window;

        try
        {
            InputStream input = this.socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) //error handling
            {
                System.out.println("Error getting input stream: " + e.getMessage());
                e.printStackTrace();
            }
    }

    @Override
    public void run()
    {
        while (!socket.isInputShutdown())
        {
            try
            {
                String response = reader.readLine();

                if (response.contains("+"))
                {
                    String encodedMessage = response.substring(response.indexOf(':') + 1);
                    String id = response.substring(1, response.indexOf('('));
                    String peerName = response.substring(response.indexOf('(') + 1, response.indexOf(')'));

                    if (encodedMessage != null && !encodedMessage.isEmpty() && !encodedMessage.equals("null"))
                    { //DECODES HERE decode message with secretKey hash of the peer class
                        String secret = client.getSecretOfId(id);
                        String decodedMessage = null;
                        if (secret != null)
                            decodedMessage = Encryption.decrypt(encodedMessage, secret); //decrypts
                        else
                            decodedMessage = encodedMessage;// does not decrypt

                        window.displayChatOther(peerName, decodedMessage);
                    }
                } else
                    {
                        // server/host messages
                        window.writePeer("-------------------------------");
                        window.writePeer("-" + response);
                        window.writePeer("-------------------------------");
                    }

            } catch (IOException ex)
                {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    ex.printStackTrace();
                }
        }

        try
        {
            socket.close();
            reader.close();
        } catch (IOException ex)
            {

                System.out.println("Error writing to server: " + ex.getMessage());
            }
    }
}

