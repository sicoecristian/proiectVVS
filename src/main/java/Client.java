import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        // need host and port, we want to connect to the ServerSocket at port 7777



        Socket socket = new Socket("localhost", 8888);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        System.out.println("Sending string to the ServerSocket");

        // write the message we want to send

        dataOutputStream.writeUTF("1mail7@test.test password");
        dataOutputStream.flush(); // send the message
        dataOutputStream.close(); // close the output stream when we're done.


           /* InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // read the message from the socket
            String message = dataInputStream.readUTF();
            System.out.println("The message sent from the socket was: " + message);

            System.out.println("Closing socket and terminating program.");

            //socket.close();  */

    }


}