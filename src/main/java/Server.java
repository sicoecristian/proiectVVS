
import com.sun.org.apache.xerces.internal.impl.XMLEntityScanner;
import exceptions.UsernameAlreadyExistsException;
import models.User;
import services.FileSystemService;
import services.UserService;
import sun.security.krb5.internal.crypto.Aes128;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Server {
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");
    static List<User> users=new ArrayList();

    public static void main(String[] args) throws IOException, UsernameAlreadyExistsException {

        // don't need to specify a hostname, it will be the current machine
        UserService.loadUsersFromFile(USERS_PATH,users);

        final ServerSocket ss = new ServerSocket(8888);
        System.out.println("ServerSocket awaiting connections...");


         // blocking call, this will wait until a connection is attempted on this port.

        Thread receive=new Thread(new Runnable() {
            String messageFromClient,response;
            @Override
            public void run() {
                try {
                    Socket socket = ss.accept();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader((socket.getInputStream())));
                    messageFromClient=bufferedReader.readLine();
                    while(messageFromClient!=null){
                        System.out.println("Got message: "+messageFromClient);
                        char req=messageFromClient.charAt(0);
                        StringBuilder clientMAil=new StringBuilder();
                        StringBuilder cllientPassword=new StringBuilder();
                        int i=1;
                        while(messageFromClient.charAt(i) != ' ') {
                            clientMAil.append(messageFromClient.charAt(i));
                            i++;
                        }
                        i++;
                        for(int j=i;j<messageFromClient.length();j++){
                            cllientPassword.append(messageFromClient.charAt(i));
                        }
                        if((int)req == 1){
                            try {
                                response = UserService.addUser(clientMAil.toString(),cllientPassword.toString(),users,USERS_PATH);
                                users.add(new User(clientMAil.toString(),UserService.encodePassword(clientMAil.toString(),cllientPassword.toString())));
                            } catch (UsernameAlreadyExistsException e) {
                                e.printStackTrace();
                            }
                        }
                        else if((int)req == 2){
                            if(Objects.equals(UserService.logIn(clientMAil.toString(),cllientPassword.toString(),users),"true")){
                                response="Log In successfully";
                            }
                            else{
                                response="Log In failed";
                            }
                            UserService.persistUsers(USERS_PATH,users);
                            try {
                                messageFromClient=bufferedReader.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        receive.start();

       // while(true) {
//            UserService.loadUsersFromFile(USERS_PATH,users);
//            OutputStream outputStream = socket.getOutputStream();
//            // create a data output stream from the output stream so we can send data through it
//            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//            System.out.println("Connection from " + socket + "!\n\n");
//
//            String responseForClient=new String();
//
//            // get the input stream from the connected socket
//            InputStream inputStream = socket.getInputStream();
//            // create a DataInputStream so we can read data from it.
//            DataInputStream dataInputStream = new DataInputStream(inputStream);
//
//            // read the message from the socket
//            String message = dataInputStream.readUTF();
//            char req=message.charAt(0);
//
//            if((int)req == 1){
//               responseForClient = UserService.addUser(clientMAil.toString(),cllientPassword.toString(),users,USERS_PATH);
//            }
//            else if((int)req == 2){
//                if(Objects.equals(UserService.logIn(clientMAil.toString(),cllientPassword.toString(),users),"true")){
//                    responseForClient="Log In successfully";
//                }
//                else{
//                    responseForClient="Log In failed";
//                }
//            }
//
//            dataOutputStream.writeUTF(responseForClient);
//            dataOutputStream.flush(); // send the message
//            dataOutputStream.close(); // close the output stream when we're done.
//
//            System.out.println("The message sent from the socket was: " + message);
//            //socket.close();

        //}
        // System.out.println("Closing sockets.");
        //ss.close();

    }
}