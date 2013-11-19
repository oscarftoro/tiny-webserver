import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 3/3/13
 * Time: 1:13 AM
 *
 */
public class TinyWebServer {

    ServerSocket serverSocket = null;
    Socket connectionSocket = null;
    private final int port = 7890;
    String[] requestMessageLine;
    String fileRequested;
    String secondToken="unnitialized";
    PrintWriter messageToClient;
    File file;
    DataOutputStream outToClient;
    public final int BUFFER_SIZE =1024;

    //CONSTRUCTOR
    public TinyWebServer(int port){
        port = this.port;
        start();

    }

    public void start(){

        try {
            System.out.println("getting a socket");
            serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
            //close the socket in case of error
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        while(true){
            System.out.println("waiting for connection...");

            try {
                connectionSocket = serverSocket.accept();
                new ClientManager(connectionSocket).start();
            } catch (IOException e) {
                e.printStackTrace(); 
            }



        }
    }
}
