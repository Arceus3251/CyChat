package Backend;

import Frontend.ChatWindow;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.net.Socket;

/**
 *  This is the main class for running a localized server, can be localhost or use service ngrok o expose a local development server to the Internet
 */
public class ServerThread extends Thread
{
    private Set<String> peerNames = new HashSet<>();
    private Set<PeerThread> peerThread = new HashSet<PeerThread>();
    private int portNumber;
    public boolean terminate;
    private ChatWindow window;

    //specific port to run the server on
    public ServerThread(int port, ChatWindow window)
    {
        this.portNumber = port;
        this.terminate = false;
        this.window = window;
    }

    //This is a default constructor
    public ServerThread()
    {
        this.portNumber = 3000;
        this.terminate = false;
    }

    public void init()
    {
        try (ServerSocket server = new ServerSocket(portNumber))
        {
            while (!terminate)
            {//server thread for each one of the peers
                Socket clientSocket = server.accept();
                window.writeToServer("Host Connected"); //add it to left window (Server Info)
                window.writeToServer("Host Info: "+ clientSocket.getInetAddress().toString() );
                window.writeToServer("-----------------------");

                PeerThread newPeer = new PeerThread(clientSocket, this); //Separate thread for each peer
                peerThread.add(newPeer); //adds peer to the list of online peers
                newPeer.start(); //Starts the thread

            }
        } catch (Exception e) //exception if the port number enter when staring server is displays message
            {
                window.writeToServer("Port already in use.");
                window.writeToServer("Enter new port number.");
                window.setServerButton(false);
                System.out.println(e);
            }
    }
    // sends message from peer 1 to other peers, basically broadcast message, can exclude certain peers
    void sendMessage(String message, PeerThread excludePeer)
    {
        for (PeerThread aPeer : peerThread)
        {
            if (aPeer != excludePeer)
            {
                aPeer.sendMessage(message);
            }
        }
    }
    //this is used for removing a peer, when they disconnect, removes peer name aka display name and their peerThread.
    void removePeer(String peerName, PeerThread aUser)
    {
        boolean removed = peerNames.remove(peerName);
        if (removed)
        {
            peerThread.remove(aUser);
            window.writeToServer("The peer " + peerName + " quit the session");
        }
    }
    //returns port number
    public int getPort()
    {
        return this.portNumber;
    }
    //stores peer name of the host client
    void addPeerName(String peerName)
    {
        peerNames.add(peerName);
    }
    //returns peer name
    Set<String> getPeerNames()
    {
        return this.peerNames;
    }
    //returns true if there are other peers connected, does not count host peer.
    boolean hasPeers()
    {
        return !this.peerNames.isEmpty();

    }

    @Override
    public void run()
    {
        init();

    }
}