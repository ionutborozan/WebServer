package server;

import ui.UiConstants;
import ui.UserInterface;

import java.io.IOException;
import java.net.ServerSocket;

public class StartWebServerUtil {

    private static UserInterface userInterface = new UserInterface();
    private static ServerState state;
    private static Server myWebServer;
    private static ServerSocket serverSocket;
    private static int port = 10003;

    public static ServerState getState() {
        return state;
    }

    public static UserInterface getUserInterface() {
        return userInterface;
    }

    public static Server getMyWebServer() {
        return myWebServer;
    }

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static int getPort() {
        return port;
    }

    public static void setUserInterface(UserInterface userInterface) {
        StartWebServerUtil.userInterface = userInterface;
    }

    public static void setState(ServerState state) {
        StartWebServerUtil.state = state;
    }

    public static void setMyWebServer(Server myWebServer) {
        StartWebServerUtil.myWebServer = myWebServer;
    }

    public static void setServerSocket(ServerSocket serverSocket) {
        StartWebServerUtil.serverSocket = serverSocket;
    }

    public static void setPort(int port) {
        StartWebServerUtil.port = port;
    }

    public static void startServer() {

        userInterface = new UserInterface();
        state = state.Stopped;

        try {
            while (true) {
                switch (state) {
                    case Running:
                        serverSocket = new ServerSocket(port);
                        userInterface.setServerState(UiConstants.RUNNING);

                        try {
                            while (true) {
                                userInterface.setFieldStateForMaintenance(true);
                                userInterface.enableMaintenance(true);
                                userInterface.setFieldStateForRoot(false);
                                myWebServer = new Server(serverSocket.accept(), userInterface.getRootDir().replace('\\', '/'));
                            }
                        } catch (IOException e) {
                            if (state != state.Stopped && state != state.Maintenance) {
                                System.exit(1);
                            }
                        }
                        break;
                    case Stopped:
                        userInterface.setFieldStateForRoot(true);
                        userInterface.setFieldStateForMaintenance(true);
                        userInterface.enableMaintenance(false);
                        userInterface.disableMaintenance();
                        break;
                    case Maintenance:
                        serverSocket = new ServerSocket(port);
                        userInterface.setServerState(UiConstants.MAINTENANCE);
                        userInterface.addInformationAboutServer(RunThreadUtil.getAddress(), String.valueOf(port));
                        try {
                            while (true) {
                                userInterface.enableMaintenance(true);
                                userInterface.setFieldStateForMaintenance(false);
                                userInterface.setFieldStateForRoot(true);
                                myWebServer = new Server(serverSocket.accept(), userInterface.getMaintenanceDir().replace('\\', '/'));
                            }
                        } catch (IOException e) {
                            if (state != state.Maintenance && state != state.Running) {
                                System.exit(1);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
