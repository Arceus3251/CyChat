/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.json.Json;
import javax.json.JsonObject;

public class PeerThread extends Thread{

    private BufferedReader bufferedReader;
    public PeerThread(Socket socket) throws IOException
    { //constructor takes in socket
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //will use socket to get input stream and input stream reader
        
    }
    public void run()
    {
        boolean flag = true;
        while (flag)
        {
            try
            { //this is where we pick up json messages from other peers, print out username and message
                JsonObject jsonObject = Json.createReader(bufferedReader).readObject();
                if(jsonObject.containsKey("username"))
                    System.out.println("["+jsonObject.getString("username")+"]: "+jsonObject.getString("message"));
            }catch(Exception e){
                flag = false;
                interrupt();
            }
        }
    }
}
