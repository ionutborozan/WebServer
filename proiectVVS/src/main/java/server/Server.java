package server;

import java.net.Socket;
import java.util.*;

public class Server extends Thread {

    static PageAndDirectoryModel model = new PageAndDirectoryModel();

    private Socket client;
    private String curentDir;
    static public StartWebServerUtil startWebServerUtil = new StartWebServerUtil();
    static public RunThreadUtil runThreadUtil = new RunThreadUtil();

    public Server() {

    }

    public Server(Socket client, String directory) {
        this.client = client;
        this.curentDir = directory;
        start();
    }


    public static void main(String[] args) {

        List<String> defaultDir = new ArrayList<>();
        defaultDir.add("root");

        List<String> defaultPage = new ArrayList<>();
        defaultPage.add("index.html");

        model.setDefaultDir(defaultDir);
        model.setDefaultPage(defaultPage);

        startWebServerUtil.startServer();
    }

    public static StartWebServerUtil getStartWebServerUtil() {
        return startWebServerUtil;
    }

    public static RunThreadUtil getRunThreadUtil() {
        return runThreadUtil;
    }

    public void run() {
        runThreadUtil.run(model.getDefaultPage(), model.defaultDir, client, curentDir);
    }
}

