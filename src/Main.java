/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 3/3/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main (String[] args){
        System.out.println("I'm going to run thew web server...");
        TinyWebServer webServer = new TinyWebServer(7890);

        System.out.println("Server running...");


    }
}
