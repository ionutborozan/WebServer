import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.TemporaryFolder;
import server.RunThreadUtil;
import server.StartWebServerUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;


public class Test {

    private static String rootPath,maintenancePath;


    public static TemporaryFolder temporaryFolder;

    @Before
    public static void setup() {

        temporaryFolder = new TemporaryFolder();
        rootPath = "C:\\ws\\work\\proiectVVS\\src\\webapp\\root";
        maintenancePath = "C:\\ws\\work\\proiectVVS\\src\\webapp\\maintenance";
    }


    @org.junit.Test
    public void testFileFolderLocation() {

        boolean isRootFilePresent = RunThreadUtil.existsFileFolder(rootPath);
        boolean isMaintenanceFilePresent = RunThreadUtil.existsFileFolder(maintenancePath);

        assertTrue(isRootFilePresent);
        assertTrue(isMaintenanceFilePresent);
    }


    @org.junit.Test
    public void testGetContentType() {
         String result = RunThreadUtil.getContentType( rootPath + "index.html");

         assertTrue(result.equals("html"));
    }


    @org.junit.Test
    public void testReadFileData() throws IOException {
        File tempFolderForTest = temporaryFolder.newFile("tIndex.html");
        FileOutputStream writeInFile = new FileOutputStream(tempFolderForTest);
        String content = "<html>\r\n" + "  <head>\r\n" +"    <title>VVS SERVER</title>\r\n" +
                "  </head>\r\n" +"  <body>\r\n" + "    <h1>My Header</h1>\r\n" + "  </body>\r\n" +
                "</html>";

        byte[] htmlBytesToWrite = content.getBytes();
        int fileLength = (int) tempFolderForTest.length();

        writeInFile.write(htmlBytesToWrite);
        writeInFile.flush();
        writeInFile.close();

        FileInputStream readFromFile = new FileInputStream(tempFolderForTest);
        byte[] htmlBytes = new byte[fileLength];
        readFromFile.read(htmlBytes);
        readFromFile.close();

        Assert.assertArrayEquals(htmlBytes, RunThreadUtil.readFileData(tempFolderForTest, fileLength));
    }
}
