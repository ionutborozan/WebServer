package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static ui.UiConstants.NOT_RUNNING;


public class InformationPanelUtil {

    public  void setUpInformationPanel(JPanel informationPanel, JPanel controlPanel, JPanel configurationPanel, JLabel serverState, JLabel curentServerAddress, JTextField port) {

        JLabel status = new JLabel("server.Server status: ");
        serverState = new JLabel(NOT_RUNNING);

        informationPanel.setBorder(BorderFactory.createTitledBorder(null, "SERVER INFORMATION", TitledBorder.LEFT, TitledBorder.TOP));
        informationPanel.setLayout(new GridLayout(3, 2));

        serverState.setHorizontalAlignment(SwingConstants.RIGHT);
        serverState.setFont(serverState.getFont().deriveFont(serverState.getFont().getStyle() & ~Font.PLAIN));


        port.setHorizontalAlignment(SwingConstants.RIGHT);
        port.setFont(port.getFont().deriveFont(port.getFont().getStyle() & ~Font.PLAIN));

        status.setFont(status.getFont().deriveFont(status.getFont().getStyle() & ~Font.PLAIN));

        informationPanel.add(status);
        informationPanel.add(serverState);
        informationPanel.add(curentServerAddress);
    }

    public void checkIfRootAndHandle(JButton searchForRoot,JTextField pageRootDir) {
        searchForRoot.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File home = FileSystemView.getFileSystemView().getHomeDirectory();
                JFileChooser fileChooser = new JFileChooser(home);

                String absolutePath = fileChooser.getSelectedFile().getAbsolutePath();

                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int dialogID = fileChooser.showSaveDialog(null);

                if(dialogID == JFileChooser.APPROVE_OPTION) {
                    pageRootDir.setText(absolutePath);
                }
            }
        });
    }
}
