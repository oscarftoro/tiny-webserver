import java.io.*;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 3/12/13
 * Time: 7:38 PM
 * Multithreaded client
 */
public class ClientManager extends Thread {
    private Socket clientSocket;
    private DataOutputStream dataOutputStreamToClient;
    String[] requestMessageLine;
    String fileRequested;
    String secondToken="unnitialized";
    File file;
    public final int BUFFER_SIZE =1024;
    ClientManager(Socket socket){
        clientSocket = socket;

    }

    public void run(){

        BufferedReader inFromClient = null;
        try {
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataOutputStreamToClient = new DataOutputStream(clientSocket.getOutputStream());
            //"\\s" regular expression to split the string from client by white space
            requestMessageLine = inFromClient.readLine().split("\\s");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestMessageLine[0].equals("GET")){
            secondToken = requestMessageLine[1];
            fileRequested = requestMessageLine[1];
            fileRequested = "http/" + fileRequested.substring(1);
            file = new File(fileRequested);
            //System.out.println(fileRequested);   //Muting the printing...
            if(secondToken.startsWith("/") == true){

                if(fileRequested.equals("index.html") || fileRequested.equals("http/"))  {
                   //send the index


                    try {
                        file = new File("http/index.html");
                        byte[] bytes = new byte[BUFFER_SIZE];
                        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                        //send the index page to the client
                        //calculate the si
                        int size = inputStream.read(bytes,0,BUFFER_SIZE);
                        //HEADER
                        dataOutputStreamToClient.writeBytes("HTTP 1.0 200 OK \r\n\n");

                        dataOutputStreamToClient.write(bytes, 0, size);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(file.exists())  {
                    System.out.println("the file exist : "+ file.exists());
                    System.out.println(fileRequested);
                    //send ẃhatever
                    // the client request

                    file = new File(fileRequested);
                    try {

                        byte[] bytes = new byte[BUFFER_SIZE];
                        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                        //send the file
                        //calculate the si
                        int size = inputStream.read(bytes,0,BUFFER_SIZE);
                        //header
                        dataOutputStreamToClient.writeBytes("HTTP 1.0 200 OK\r\n\n");

                        while(size !=-1){
                            dataOutputStreamToClient.write(bytes, 0, size);
                            size = inputStream.read(bytes,0,BUFFER_SIZE);
                        }
                        if (fileRequested.endsWith(".jpg")){
                            System.out.println("File Type: jpeg image\r\n");
                        }
                        else if (fileRequested.endsWith(".png")){
                            System.out.println("File Type: png image\r\n");
                        }
                        else if (fileRequested.endsWith(".txt")){

                            System.out.println("File Type: txt plain text file\r\n");
                        }
                        else if (fileRequested.endsWith(".html")){

                            System.out.println("File Type: html file\r\n");
                        }
                        System.out.println("File Size: " + file.length() + " bytes\r\n");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(!file.exists()){// the file does not exist
                    //send a response to the client when the file is not present
                    try {
                        file = new File("http/404.html");
                        int size = (int) file.length();
                        dataOutputStreamToClient.writeBytes("HTTP 1.0 400 Bad Requestr\r\n\r\n");
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[size];
                        fileInputStream.read(bytes);
                        dataOutputStreamToClient.write(bytes, 0, size);
                        System.out.println("HTTP 1.0 404 Not Found");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //and close the door after you
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
           }

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
