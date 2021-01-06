package ui;

import server.StartWebServerUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class UserInterface extends JFrame {


    private static final long serialVersionUID = 1L;

    private JPanel controlPanel, informationPanel, configPanel;
    private JPanel section, maintenancePanel;

    private JLabel serverState, curentServerAdress;
    private JButton startServer;
    private JCheckBox chkSwithMaintainanceMode;
    private JTextField maintenanceDir, pageRootDir, port;
    private JButton bMaintenance;

    private JButton searchForRoot, bOk, bCancel;

    InformationPanelUtil informationPanelUtil;
    MaintainanceUtil maintainanceUtil;
    StartServerUtil startServerUtil;

    public UserInterface() {
        super("SERVER VVS" + "is on state: " + StartWebServerUtil.getState());

        setupPanels();

        this.setSize(800, 500);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        section = new JPanel(new GridLayout(1, 2, 10, 0));
        section.add(informationPanel);
        section.add(controlPanel);

        this.setLayout(new GridLayout(2, 1, 0, 10));
        this.add(section);
        this.add(configPanel);
    }

    public InformationPanelUtil getInformationPanelUtil() {
        return informationPanelUtil;
    }

    public MaintainanceUtil getMaintainanceUtil() {
        return maintainanceUtil;
    }

    public StartServerUtil getStartServerUtil() {
        return startServerUtil;
    }

    public void addInformationAboutServer(String serverAddress, String serverPort) {
        this.curentServerAdress.setText(serverAddress);
        this.port.setText(serverPort);
    }

    public void setServerState(String state) {
        this.serverState.setText(state);
    }

    public void setMaintenanceDir(String path) { this.maintenanceDir.setText(path); }

    public void setPageRootDir(String path) { this.pageRootDir.setText(path); }

    public void setFieldStateForMaintenance(boolean state) {
        this.bMaintenance.setEnabled(state);
        this.maintenanceDir.setEnabled(state);
    }

    public void clickStartButton() {
        this.startServer.doClick();
    }


    public void setFieldStateForRoot(boolean state) {
        this.pageRootDir.setEnabled(state);
        this.searchForRoot.setEnabled(state);
    }

    public void setupPanels() {

        startServer = new JButton("Start server");
        searchForRoot = new JButton("...");
        pageRootDir = new JTextField(15);
        maintenanceDir = new JTextField(15);

        informationPanelUtil = new InformationPanelUtil();
        maintainanceUtil = new MaintainanceUtil();
        startServerUtil = new StartServerUtil();

        JLabel rootDir = new JLabel("Web root directory");
        JLabel maintenanceDir = new JLabel("Maintanance directory");
        JPanel listenPanel = new JPanel(new BorderLayout(12, 5));
        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel webPanel = new JPanel(new BorderLayout(38, 15));
        JPanel webRootPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 0));

        controlPanel.setBorder(BorderFactory.createTitledBorder(null, "CONTROL PANEL", TitledBorder.LEFT, TitledBorder.TOP));
        controlPanel.setLayout(new GridLayout(2, 1));

        startServerUtil.startServer(startServer, pageRootDir, serverState);
        informationPanelUtil.setUpInformationPanel(informationPanel, controlPanel, configPanel, serverState, curentServerAdress, port);

        this.chkSwithMaintainanceMode.setSelected(false);
        maintainanceUtil.switchToMainainanceModeAndHandleServerState(chkSwithMaintainanceMode, this.maintenanceDir);

        controlPanel.add(startServer);
        controlPanel.add(chkSwithMaintainanceMode);

        webRootPanel.add(pageRootDir);
        webRootPanel.add(searchForRoot);
        webRootPanel.add(bOk);

        webPanel.add(rootDir, BorderLayout.WEST);
        webPanel.add(webRootPanel, BorderLayout.CENTER);

        informationPanelUtil.checkIfRootAndHandle(searchForRoot, pageRootDir);
        maintainanceUtil.checkIfServerIsOnMaintainanceMode(bMaintenance, bOk, bCancel, this.maintenanceDir);

        listenPanel.add(port, BorderLayout.WEST);
        listenPanel.add(portPanel, BorderLayout.CENTER);

        maintainanceUtil.setUpMaintenancePanel(maintenancePanel, bMaintenance, this.maintenanceDir, bCancel);

        mainPanel.add(maintenanceDir, BorderLayout.WEST);
        mainPanel.add(maintenancePanel, BorderLayout.CENTER);

        configPanel.add(listenPanel);
        configPanel.add(webPanel);
        configPanel.add(mainPanel);

    }

    public void enableMaintenance(boolean state) {
        this.chkSwithMaintainanceMode.setEnabled(state);
    }

    public void disableMaintenance() {
        this.bMaintenance.setSelected(false);
    }

    public String getRootDir() {
        return pageRootDir.getText().trim();
    }

    public String getMaintenanceDir() {
        return maintenanceDir.getText().trim();
    }
}
