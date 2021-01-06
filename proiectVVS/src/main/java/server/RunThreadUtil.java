package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

public class RunThreadUtil {

    public static void run(List<String> defaultPage, List<String> rootDir, Socket client,String curentDir) {

        try {
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedOutputStream data = new BufferedOutputStream(client.getOutputStream());

            String requestedFile = null;

            String inputLine = in.readLine();
            StringTokenizer parse = new StringTokenizer(inputLine);
            String method = parse.nextToken().toUpperCase();
            requestedFile = parse.nextToken().toLowerCase();


            if (method.equals("GET") || method.equals("HEAD")) {

                if (requestedFile.endsWith("/")) {

                    if (existsFileFolder(curentDir + "/" + defaultPage.get(0))) {
                        requestedFile += defaultPage.get(0);
                    } else if (existsFileFolder(curentDir + "/" + defaultPage.get(1))) {
                        requestedFile += defaultPage.get(1);
                    } else if (existsFileFolder(curentDir + "/" + defaultPage.get(0))) {
                        requestedFile += defaultPage.get(2);
                    }
                }


                File root = new File(curentDir);

                File file = new File(root, requestedFile);
                int fileLength = (int) file.length();
                String content = getContentType(requestedFile);

                if (method.equals("GET")) { // GET method so we return content

                    byte[] fileData = readFileData(file, fileLength);

                    // send HTTP Headers
                    out.println("HTTP/1.1 200 OK");
                    out.println("Java HTTP server");

                    out.println("Content-type: " + content);
                    out.println("Content-length: " + fileLength);
                    out.println("");

                    data.write(fileData, 0, fileLength);
                    data.flush();
                }

            }

            out.close();
            in.close();
            data.close();
            client.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public static boolean existsFileFolder(String path) { return Files.exists(Paths.get(path), LinkOption.NOFOLLOW_LINKS); }

    public static String getContentType(String fileRequested) {
        String contentType = fileRequested.endsWith(".html") ? "html" :  "text";
        return contentType;
    }

    public static byte[] readFileData(File file, int length) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[length];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    public static void closeServer(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
