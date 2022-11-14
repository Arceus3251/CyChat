package Backend;

import Frontend.ChatWindow; //connects to other package, java file MainWindow

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.UUID;

// The main class for the User/Peer
public class Peer extends Thread
{
    private int port;
    private Socket socket;
    private HashMap<String, String> tableHash;
    private String hostName;
    private String displayName;
    private String id;
    private String uniqueIdentifier;
    private ChatWindow window;


    //default constructor
    public Peer(String uniqueIdentifier)
    {
        this.hostName = "localhost";
        this.port = 3000;
        this.id = UUID.randomUUID().toString();
        this.uniqueIdentifier = uniqueIdentifier;
    }

    //Constructor
    // hostname: hostname of the server
    // port:     port server connected to
    public Peer(String hostname, int port, String uniqueIdentifier, ChatWindow window)
    {
        this.hostName = hostname;
        this.port = port;
        this.id = UUID.randomUUID().toString();
        this.uniqueIdentifier = uniqueIdentifier;
        this.window = window;
        this.tableHash = new HashMap<String, String>();
    }

    public void init()
    {

        window.setUniqueIdField(this.uniqueIdentifier);
        window.setIdField(this.id);

        try
        {
            InetAddress address = InetAddress.getByName(hostName);
            socket = new Socket(address, port);

            window.writePeer("Connected to the chat server");
            window.writePeer("Your public key:" + this.id);
            window.writePeer("Your unique identifier:" + this.uniqueIdentifier);

            // seperate threads for reading and writing msgs
            new DisplayThread(socket, this, window).start();
            new CommunicateThread(socket, this, window).start();

        } catch (UnknownHostException ex)
            {
                window.writePeer("Server not found");
                window.setPeerToggle(false);
                System.out.println("Server not found: " + ex.getMessage());
            } catch (IOException ex)
                {
                    window.writePeer(ex.getMessage());
                    window.setPeerToggle(false);
                    System.out.println("I/O Error: " + ex.getMessage());
                }

    }


    @Override
    public void run()
    {
        init();
    }

    public void close()
    {
        try
        {
            socket.close();
        }
        catch (Exception e)
            {
                System.out.println(e.toString());
            }
    }

    //method to set display name
    void setDisplayName(String userName)
    {
        this.displayName = userName;

    }

    //returns display name
    String getDisplayName()
    {
        return this.displayName;

    }

    String getUniqueId()
    {
        return this.id;

    }

    String getCurrentSecret()
    {
        return this.uniqueIdentifier;

    }

    String getSecretOfId(String id)
    {
        return this.tableHash.get(id);

    }

    public void addSecretOfId(String id, String secret)
    {
        this.tableHash.put(id, secret);

    }


}
