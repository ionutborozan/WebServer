import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import server.Server;
import server.StartWebServerUtil;

import static org.junit.Assert.*;

public class WebTest {
    WebDriver myWebDriver;
    String url = "http://localhost:10003";

    @Before
    public void before() {

        new Thread(() -> {
            System.out.println("Started Test!!");
            StartWebServerUtil.getUserInterface().setMaintenanceDir("C:\\ws\\work\\proiectVVS\\src\\webapp\\maintenance");
            StartWebServerUtil.getUserInterface().setPageRootDir("C:\\ws\\work\\proiectVVS\\src\\webapp\\root");
            StartWebServerUtil.getUserInterface().clickStartButton();
            Server.main(new String[]{"start"});
        }).start();

        myWebDriver = new ChromeDriver();
        String chromeDriverPath = "\\Chrome Driver\\chromedriver.exe";
    }

    @Test
    public void testTitle() {
        myWebDriver.navigate().to(this.url + "/proiectVVS/src/webapp/root/firstPage.html");

        String actualTitle = myWebDriver.getTitle();
        String expectedTitle = "VVS SERVER";

        assertEquals(expectedTitle, actualTitle);
        myWebDriver.quit();
    }

    @Test
    public void testNavigateToFirstPage() {
        String expectedUrl = this.url + "/proiectVVS/src/webapp/root/firstPage.html";

        myWebDriver.findElement(By.id("firstPage"));
        assertTrue(expectedUrl.equals(myWebDriver.getCurrentUrl()));
        myWebDriver.quit();
    }

    @Test
    public void testNavigateToAboutPage() {
        String expectedUrl = this.url + "/proiectVVS/src/webapp/root/about.html";

        myWebDriver.findElement(By.id("aboutPage"));
        assertTrue(expectedUrl.equals(myWebDriver.getCurrentUrl()));
        myWebDriver.quit();
    }
    @Test
    public void testNavigateToIndexFromFirstDIr() {
        String expectedUrl = this.url + "/proiectVVS/src/webapp/root/firstDir/index.html";

        myWebDriver.findElement(By.id("aboutPage"));
        assertTrue(expectedUrl.equals(myWebDriver.getCurrentUrl()));
        myWebDriver.quit();
    }

    @Test
    public void testCheckMaintenancePage() {
        String expectedUrl = this.url + "/proiectVVS/src/webapp/maintenance/index.html";
        String expectedH1Content = "This site is under maintainance";

        WebElement myElement = myWebDriver.findElement(By.id("h1"));
        String receivedTestFromMaintenanceH1 = myElement.getText();

        assertTrue(expectedH1Content.equals(receivedTestFromMaintenanceH1));
        assertTrue(expectedUrl.equals(myWebDriver.getCurrentUrl()));
    }
}
