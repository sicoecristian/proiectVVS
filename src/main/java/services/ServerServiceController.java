package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ServerServiceController {

    private ServerService serverService;

    public ServerServiceController(ServerService serverService) {
        this.serverService = serverService;
    }

    public static ServerSocket newServerSocket(int socketPort) throws BindException {

        try {
            ServerSocket newSocket = new ServerSocket(socketPort);
            System.out.println("Server created successfully on port : " + socketPort);
            return newSocket;
        } catch (IllegalArgumentException e) {
            System.out.println("The port must be between 0 and 65535.");
            e.printStackTrace();
            throw e;
        } catch (BindException b) {
           b.printStackTrace();
            throw b;
        } catch (Exception ex) {
            System.out.println("creating server socket failed on port: " + socketPort);
            ex.printStackTrace();
            return null;
        }

    }

    public static void closeServerSocket(ServerSocket serverSocket) throws NullPointerException {
        try {

            serverSocket.close();
            System.out.println("Closed server socket successfully on port: " + serverSocket.getLocalPort());

        } catch (NullPointerException e) {

            System.out.println("This socket is null.");
            throw e;

        } catch (Exception ex) {

            System.out.println("closing server socket failed on port: " + serverSocket.getLocalPort());
            ex.printStackTrace();

        }
    }

    public static Socket newClientSocket(ServerSocket serverSocket) throws Exception {

        try {
            Socket newClientSocket = acceptSocket(serverSocket);
            System.out.println("client created successfully");
            return newClientSocket;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static void closeClientSocket(Socket clientSocket) throws NullPointerException {
        try {

            clientSocket.close();

        } catch (NullPointerException e) {

            throw e;

        } catch (Exception b) {

            System.out.println("Exception: " + b);

        }
    }

    public static Socket acceptSocket(ServerSocket serverSocket) throws Exception {
        try {
            return serverSocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestHandler() {
        try (ServerSocket serverSock = this.newServerSocket(serverService.getPort())) {

            Socket clientSock = newClientSocket(serverSock);
            clientStuff(clientSock);
            this.closeClientSocket(clientSock);
            this.closeServerSocket(serverSock);

        } catch (IOException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        out.write(("HTTP/1.1 \r\n" + status).getBytes()); //http response
        out.write(("ContentType: " + contentType + "\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(content);
        out.write("\r\n\r\n".getBytes());
    }

    public void clientStuff(Socket clientSock) throws IOException {

        ArrayList<String> inputs = new ArrayList<String>();
        Path filePath= Paths.get("/src/main/java/website");
        String contentType;
        String rawPath;
        try {
            OutputStream out = clientSock.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            String inputString;
            while ((inputString = in.readLine()) != null) {
                inputs.add(inputString);
                if (inputString.isEmpty()) {
                    break;
                }
            }
            if (!inputs.isEmpty())  {
                rawPath = inputs.get(0).split(" ")[1];
                if ("/".equals(rawPath) || "/home.html".equals(rawPath)) {
                    filePath = Paths.get(serverService.getWebsiteFilesPath(), "/home.html");
                } else if ("/index.css".equals(rawPath)) {
                    filePath = Paths.get(serverService.getWebsiteFilesPath(), "/home.css");
                } else {
                    filePath = Paths.get(rawPath);
                }

                System.out.println("DEBUG : file path : "+filePath); //debug

                if (!filePath.toString().contains("home")) {
                    if (serverService.pagesList.get(0).contains(rawPath.substring(1))) {
                        String aux = serverService.getWebsiteFilesPath() ;//+ "/website"
                        filePath = Paths.get(aux, rawPath);
                    } else {
                        filePath = Paths.get(rawPath);
                    }

                }
                contentType = Files.probeContentType(filePath);
                String tmpReq = serverService.getRequest() + " " + filePath.toString();
                serverService.setRequest(tmpReq);
                if (serverService.getServerStatus().equals("Running")) {
                    if (Files.exists(filePath)) {
                        sendResponse(out, "200 OK", contentType, Files.readAllBytes(filePath));
                    } else {
                        sendResponse(out, "404 Not Found", contentType, Files.readAllBytes(Paths.get(serverService.getWebsiteFilesPath(), "/notfound.html")));
                    }
                }

            }
            in.close();
            out.close();

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

