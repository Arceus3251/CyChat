/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.json.Json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
/**
 *
 * @author lozan
 */
public class Peer {

    public static void main(String args[]) throws Exception
    {  //main method run this java file
       //prompt user for input, username and their port number
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("> enter username & port # for this peer:");
        String[] setupValues = bufferedReader.readLine().split(" ");
        ServerThread serverThread = new ServerThread(setupValues[1]); //instantiate server thread, passing in port number
        serverThread.start(); //call which will trigger run method in serverThread.java
        new Peer().updateListenToPeers(bufferedReader, setupValues[0], serverThread);
    }
    
    public void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception //takes in server thread,buffered reader(used for user input), and username
    { //update peers that its picking up messages
        System.out.println("> enter (space separated) hostname:port#");
        System.out.println(" peers to receive messages from (s to skip):");
        String input = bufferedReader.readLine();
        
        String[] inputValues = input.split(" ");
        if(!input.equals("s")) for (int i = 0; i < inputValues.length; i++)
        { //if user enters s, skip
            String[] adress = inputValues[i].split(":"); //otherwise we split, pick up host name and port number
            Socket socket =null;
            try
            {//instanciate socket, passing in host name and port number, do this for all peers
                socket = new Socket(adress[0], Integer.valueOf(adress[1]));
                new PeerThread(socket).start(); //pass in socket, call start method(run) of peerthread.java,
            }
            catch(Exception e)
            {
                if(socket != null) socket.close();
                else System.out.println("invalid input. skipping to next step.");
            }
        }
        communicate(bufferedReader, username, serverThread);
    }
    public void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread)
    {//used to send message
        try{//user cna e to exit, or c to add peers to pick up messages from
            System.out.println("> you can now communicate (e to exit, c to change)");
            boolean flag = true;
            while(flag)
            {
                String message = bufferedReader.readLine();
                if(message.equals("e"))
                {
                    flag = false;
                    break;
                } else if(message.equals("c")) //triggers call to updateListenToPeers method
                {
                    updateListenToPeers(bufferedReader, username, serverThread);
                }else { //if user doesn't enter e or c , then we pack the message.
                    StringWriter stringWriter = new StringWriter();
                    Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                                                                .add("username", username)
                                                                .add("message", message)
                                                                .build());
                    serverThread.sendMessage(stringWriter.toString()); //send that message to serverThread.java
                }
            }
            System.exit(0);
        }catch(Exception e){}
    }
}
