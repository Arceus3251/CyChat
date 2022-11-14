package Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class PeerThread extends Thread
{
    // User Socket
    private Socket socket;
    // Main Server
    private ServerThread server;
    private PrintWriter writer;
    private String peerName;
    private String identity;

    //constructors for following
    // peer: Socket
    // server: Main Server, in example I use localhost
    public PeerThread(Socket socket, ServerThread server)
    {
        this.socket = socket;
        this.server = server;
        this.peerName = "Peer One";
    }

    /**
     * Overriding run() method of Thread Class
     */
    @Override
    public void run()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) //input stream of new peer
        {
            String peerMessage;// peer message string

            this.writer = new PrintWriter(socket.getOutputStream(), true);

            printPeers(); //displays current peers
            this.identity = reader.readLine();// first line contains the unique identify
            this.peerName = reader.readLine();// Second line given by host/server is peer name
            server.addPeerName(peerName);

            String hostMessage = "New peer connected: " + peerName;// message sent by server to all peers, except current one
            server.sendMessage(hostMessage, this);


            // message send by current peer while socket is open&connected
            while (socket.isConnected() && !socket.isClosed())
            {
                peerMessage = reader.readLine();
                hostMessage = "+" + this.identity + "(" + peerName + "):" + peerMessage;
                server.sendMessage(hostMessage, this);
            }

        } catch (IOException ex) //error handling if something messes up
            {
                System.out.println("Error in PeerThread: " + ex.getMessage());
                ex.printStackTrace();

            } finally
            {
                server.removePeer(peerName, this);

                try
                {
                socket.close();

                } catch (IOException e)
                    {
                        System.out.println(e);
                    }

                String serverMessage = peerName + " has quit.";
                server.sendMessage(serverMessage, this);
            }
    }

    // Sends a list of online peers to the newly connected peer.
    void printPeers()
    {
        if (server.hasPeers())
        {
            writer.println("Connected Peers: " + server.getPeerNames());
        } else
            {
                writer.println("No other peers connected");
            }
    }

    //Sends a message to the host in order to broadcast.
    void sendMessage(String message)
    {
        writer.println(message);
    }
}
