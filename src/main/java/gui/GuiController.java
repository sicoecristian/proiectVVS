package gui;

import com.sun.security.ntlm.Server;
import services.ServerService;
import services.ServerServiceController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GuiController extends JFrame {
    private JPanel mainPanel;
    private JLabel currentStatusTextView;
    private JLabel currentStatusLable;
    private JLabel Title;
    private JButton startButton;
    private JButton stopButton;
    private JButton maintenanceButton;
    private JLabel portTextView;
    private JLabel portLabel;
    private JLabel hostnameTextView;
    private JLabel hostnameLabel;
    private JTextField newPortField;
    private JButton changePortButton;
    private JLabel filepathTextView;
    private JLabel rootPathLabel;
    private JTextField newRootPathLabel;
    private JButton changeRootPathButton;

    private static ServerService serverService;
    private static ServerServiceController serverServiceController;

    public GuiController(String title) throws UnknownHostException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(850  ,750);
        this.setResizable(false);

        this.portLabel.setText(Integer.toString(serverService.getPort()));
        this.rootPathLabel.setText(serverService.getWebsiteFilesPath());
        this.currentStatusLable.setText(serverService.getServerStatus());
        this.hostnameLabel.setText(String.valueOf(InetAddress.getLocalHost()));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverService.setServerStatus("Running");
                currentStatusLable.setText(serverService.getServerStatus());
                JOptionPane.showMessageDialog(null, "The server is Running !");
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverService.setServerStatus("Stopped");
                currentStatusLable.setText(serverService.getServerStatus());
                JOptionPane.showMessageDialog(null, "The server was Stopped !");
            }
        });


    }
    public static void main(String[] args) throws UnknownHostException {
        serverMainAction();
        JFrame frame = new GuiController("Gui controller");
        frame.setVisible(true);
        while(true) {
            serverServiceController.requestHandler();
        }
    }
    public static void serverMainAction() {
        int port = 8888; // port
        String websiteFilesPath = "src/main/java/website";
        String serverStatus = "Stopped";
        ServerService serverService = new ServerService(port,websiteFilesPath,serverStatus);
        ArrayList<String> level1 = new ArrayList<String>();
        ArrayList<String> level2 = new ArrayList<String>();
        ArrayList<String> level3 = new ArrayList<String>();
        serverService.addPageLevel(level1);
        serverService.addPageLevel(level2);
        serverService.addPageLevel(level3);
        serverService.addPageAtLevel("soup.html",0);
        serverService.addPageAtLevel("turkey.html",0);
        serverService.addPageAtLevel("cake.html",0);
        serverServiceController = new ServerServiceController(serverService);
    }
}
