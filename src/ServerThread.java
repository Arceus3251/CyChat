import java.net.ServerSocket;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author lozan
 */
public class ServerThread extends Thread{

    private ServerSocket serverSocket;
    private Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>();
    public ServerThread(String portNumb) throws IOException // constructor takes in port number
    {
        serverSocket = new ServerSocket(Integer.valueOf(portNumb)); //use this to initialize socket
    }
    public void run()
    {
        try{
            while(true)
            {//server thread for each one of the peers
                ServerThreadThread serverThreadThread = new ServerThreadThread(serverSocket.accept(), this);
                serverThreadThreads.add(serverThreadThread); // put threads in the private set<>
                serverThreadThread.start(); //call start methods on each one of those threads, triggers call to run method in serverThreadThread.java
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void sendMessage(String message){
        try{
            serverThreadThreads.forEach(t-> t.getPrintWriter().println(message));//
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Set<ServerThreadThread> getServerThreadThreads(){
        return serverThreadThreads;
    }
}
