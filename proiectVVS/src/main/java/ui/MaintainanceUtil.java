package ui;

import server.RunThreadUtil;
import server.StartWebServerUtil;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MaintainanceUtil{

    public void checkIfServerIsOnMaintainanceMode(JButton bMaintainance,JButton bOk,JButton bCancel,JTextField maintenanceDir) {
        bMaintainance = new JButton("...");
        bMaintainance.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File home = FileSystemView.getFileSystemView().getHomeDirectory();
                JFileChooser fileChooser = new JFileChooser(home);

                String absolutePathOfSelectedFile = fileChooser.getSelectedFile().getAbsolutePath();

                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int saveDialogId = fileChooser.showSaveDialog(null);

                if(saveDialogId == JFileChooser.APPROVE_OPTION) {
                    maintenanceDir.setText(absolutePathOfSelectedFile);
                }
            }
        });

        bOk = new JButton("Ok");
        bCancel = new JButton("Cancel");
    }

    public void setUpMaintenancePanel(JPanel maintenancePanel,JButton bMaintenance,JTextField maintenanceDir,JButton bCancel) {
        maintenancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maintenancePanel.add(maintenanceDir);
        maintenancePanel.add(bMaintenance);
        maintenancePanel.add(bCancel);
    }

    public void switchToMainainanceModeAndHandleServerState(JCheckBox chkSwithMaintainanceMode,JTextField maintenanceDir) {

        chkSwithMaintainanceMode = new JCheckBox("Switch to maintenance mode");
        chkSwithMaintainanceMode.setEnabled(false);
        JCheckBox finalMaintenanceMode = chkSwithMaintainanceMode;
        chkSwithMaintainanceMode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if(finalMaintenanceMode.isSelected()) {
                    if(maintenanceDir.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error", "Alert", JOptionPane.ERROR_MESSAGE);
                    } else {
                        StartWebServerUtil.setState(StartWebServerUtil.getState().nextState("Maintenance", StartWebServerUtil.getState()));
                    }
                } else {
                    StartWebServerUtil.setState(StartWebServerUtil.getState().nextState("Run", StartWebServerUtil.getState()));
                }
                RunThreadUtil.closeServer(StartWebServerUtil.getServerSocket());
                StartWebServerUtil.getUserInterface().setTitle("SERVER VVS" + "is on state: " + StartWebServerUtil.getState());
            }

        });
    }

}
