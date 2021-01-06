package ui;

import server.RunThreadUtil;
import server.StartWebServerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartServerUtil {

    public void startServer(JButton startServer,JTextField tfRootDir,JLabel serverState) {
        startServer.setPreferredSize(new Dimension(1, 1));


        startServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(StartWebServerUtil.getState() == StartWebServerUtil.getState().Stopped) {
                    if(StartWebServerUtil.getPort() == 0) {
                        JOptionPane.showMessageDialog(null, "Error", "Alert", JOptionPane.ERROR_MESSAGE);
                    } else if(tfRootDir.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error", "Alert", JOptionPane.ERROR_MESSAGE);
                    } else {
                        StartWebServerUtil.setState(StartWebServerUtil.getState().nextState("Run",StartWebServerUtil.getState()));
                        startServer.setText("Stop server");
                    }
                }
                else {
                    StartWebServerUtil.setState(StartWebServerUtil.getState().nextState("Stop",StartWebServerUtil.getState()));
                    startServer.setText("Start server");
                    RunThreadUtil.closeServer(StartWebServerUtil.getServerSocket());
                    serverState.setText(UiConstants.NOT_RUNNING);
                }
                StartWebServerUtil.getUserInterface().setTitle("SERVER VVS" + "is on state: " + StartWebServerUtil.getState());
            }
        });
    }
}
