package services;

import java.util.ArrayList;

public class ServerService {
    private int port; // port
    private String websiteFilesPath;
    private String serverStatus;
    private String request;
    public ArrayList<ArrayList<String>> pagesList;

    public ServerService(){

    }

    public ServerService(int port, String websiteFilesPath, String serverStatus)  {

        this.port = port;
        this.websiteFilesPath = websiteFilesPath;
        this.serverStatus = serverStatus;
        this.request = "";
        pagesList = new ArrayList<ArrayList<String>>();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public String getWebsiteFilesPath() { return websiteFilesPath; }

    public void setWebsiteFilesPath(String websiteFilesPath) { this.websiteFilesPath = websiteFilesPath; }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void addPageLevel(ArrayList<String> newLevel){
        pagesList.add(newLevel);
    }
    public void addPageAtLevel(String newPage,int level){
        pagesList.get(level).add(newPage);
    }
}
