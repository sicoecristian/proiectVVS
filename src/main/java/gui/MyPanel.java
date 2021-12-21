package gui;

import java.awt.*;
import java.awt.event.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.event.*;

public class MyPanel extends JPanel {
    private static JLabel title;
    private static  JLabel jcomp2;
    private static JButton startServerButton;
    private static JButton stoServerButton;
    private static JButton maintenanceButton;
    private static  JLabel ipAddress;
    private static JLabel jcomp7;
    private static  JLabel port;
    private static JLabel status;

    public MyPanel() {
        //construct components
        title = new JLabel ("                              Server");
        jcomp2 = new JLabel (" Ip v4 address");
        startServerButton = new JButton ("Start Server");
        stoServerButton = new JButton ("Stop Server");
        maintenanceButton = new JButton ("Maintenance");
        ipAddress = new JLabel ("");
        jcomp7 = new JLabel (" Port");
        port = new JLabel ("8888");
        status = new JLabel ("");

        //adjust size and set layout
        setPreferredSize (new Dimension (944, 574));
        setLayout (null);

        //add components
        add (title);
        add (jcomp2);
        add (startServerButton);
        add (stoServerButton);
        add (maintenanceButton);
        add (ipAddress);
        add (jcomp7);
        add (port);
        add (status);

        //set component bounds (only needed by Absolute Positioning)
        title.setBounds (330, 15, 240, 70);
        jcomp2.setBounds (40, 110, 100, 25);
        startServerButton.setBounds (25, 335, 880, 45);
        stoServerButton.setBounds (25, 395, 885, 45);
        maintenanceButton.setBounds (25, 455, 885, 45);
        ipAddress.setBounds (145, 110, 185, 25);
        jcomp7.setBounds (40, 165, 100, 25);
        port.setBounds (150, 165, 200, 25);
        status.setBounds (215, 230, 375, 75);
    }


    public static void main (String[] args) throws UnknownHostException {
        final JFrame frame = new JFrame ("MyPanel");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new MyPanel());
        frame.pack();
        frame.setVisible (true);
        ipAddress.setText(Inet4Address.getLocalHost().getHostAddress());
        startServerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                status.setForeground(Color.GREEN);
                status.setText("Server is running!");
                JOptionPane.showMessageDialog(null, "The server is Running !");

            }
        });

        stoServerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                status.setForeground(Color.RED);
                status.setText("Server is stopped!");
                JOptionPane.showMessageDialog(null, "The server is stopped !");

            }
        });

        maintenanceButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                status.setForeground(Color.ORANGE);
                status.setText("Server is under maintenance!");
                JOptionPane.showMessageDialog(null, "The server is unedr maintenance !");

            }
        });
    }
}
