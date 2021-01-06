package server;

import java.net.Socket;
import java.util.*;

public class Server extends Thread {

    static PageAndDirectoryModel model = new PageAndDirectoryModel();

    private Socket client;
    private String curentDir;

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

        StartWebServerUtil.startServer();
    }


    public void run() {
        RunThreadUtil.run(model.getDefaultPage(), model.defaultDir, client, curentDir);
    }
}

