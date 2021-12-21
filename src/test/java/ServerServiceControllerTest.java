import org.asynchttpclient.util.Assertions;
import org.junit.Test;
import services.ServerService;
import services.ServerServiceController;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ServerServiceControllerTest {

    private int port8080 = 8888;
    private String websiteFilePath = "src/main/java/website";

    @Test
    public void testNewServerSocketOk() {

        ServerService serverService = new ServerService(port8080, websiteFilePath, "Stopped");
        serverService.setServerStatus("Running");

        try {
            ServerSocket socket = ServerServiceController.newServerSocket(8888);

            assertTrue(socket.isBound());

            socket.close();
        } catch (BindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testNewClientSocketOk() throws BindException {

        ServerSocket serverSocket = ServerServiceController.newServerSocket(port8080);

        try{

            Socket clientSocket = ServerServiceController.newClientSocket(serverSocket);

        }catch(Exception e) {
            e.printStackTrace();
        }

    }


    @Test(expected = NullPointerException.class)
    public void testCloseClientSocketNotOk(){ // when try to close null client socket

        ServerServiceController.closeClientSocket(null);

    }

    @Test
    public void testCloseClientSocketOk() throws BindException {

        try{

            ServerSocket serverSocket = ServerServiceController.newServerSocket(port8080);
            Socket clientSocket = ServerServiceController.newClientSocket(serverSocket);

            ServerServiceController.closeClientSocket(clientSocket);
            assertTrue(clientSocket.isClosed());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    @Test
//    public void testAcceptSocketOk() {
//        try {
//            ServerSocket serverSocket = ServerServiceController.newServerSocket(port8080);
//            Socket clientSocket = ServerServiceController.acceptSocket(serverSocket);
//
//            assertTrue(clientSocket.isBound());
//            assertTrue(serverSocket.isBound());
//
//            serverSocket.close();
//            clientSocket.close();
//        } catch (BindException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test(expected = SocketException.class)
    public void testAcceptSocketNotOk() throws Exception { // when accept from a closed server socket
        ServerSocket serverSocket = ServerServiceController.newServerSocket(port8080);
        ServerServiceController.closeServerSocket(serverSocket);
        Socket clientSocket = ServerServiceController.acceptSocket(serverSocket);
    }
    @Test
    public void clientHandlerOk() throws  IOException {
       ServerService serverService = new ServerService(port8080,websiteFilePath,"Stopped");
        ServerServiceController serverController = new ServerServiceController(serverService);

        ServerSocket serverSocket = new ServerSocket(port8080);

        Socket clientSocket = serverSocket.accept();

        serverController.clientStuff(clientSocket);

    }

    @Test
    public void clientHandlerNotOk() throws IOException { // when try to handle a null client
        ServerService serverService = new ServerService(port8080,websiteFilePath,"Stopped");
        ServerServiceController serverController = new ServerServiceController(serverService);

        serverController.clientStuff(null);
        // exception thrown -> Null client object was given
    }

}
