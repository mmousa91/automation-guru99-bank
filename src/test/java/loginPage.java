import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class loginPage {

    WebDriver driver = null;
    @BeforeTest
    public void openBrowser() {

        String chromepath = System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromepath);

        driver = new ChromeDriver();

        //maximize the page browser
        driver.manage().window().maximize();


    }
//The @DataProvider annotation returns Java objects which are values to the test method
    //logInData has the test data. The logInData method will supply data to the logIn test method.
    @DataProvider
    public Object [] [] loginData (){

        Object [][] data  = new Object[4][3];
        data [0][0] = "mngr473669";  data [0][1]="gEsumub";  data [0][2] =true;
        data [1][0] = "mngr473669";  data [1][1]= "invalid"; data [1][2] =false;
        data [2][0] = "invalid";     data [2][1]= "gEsumub"; data [2][2] =false;
        data [3][0] = "invalid";     data [3][1]= "invalid"; data [3][2] =false;

        return data;
    }

    @Test (dataProvider = "loginData")
    public void login (String username ,String password,boolean success){
        //enter the url
        driver.navigate().to("https://www.demo.guru99.com/V4/");
        // clear username field
        driver.findElement(By.xpath("//input[@type=\"text\"]")).clear();
        //enter username
        driver.findElement(By.xpath("//input[@type=\"text\"]")).sendKeys(username);
        //clear password field
        driver.findElement(By.xpath("//input[@type=\"password\"]")).clear();
        //enter password
        driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys(password);
        //click on login button
        driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();


        System.out.println("Sign In Credentials: " + "\n" +
                "username = " + username + "\n" +
                "password = " + password + "\n" +
                "successful signin " + success + "\n" );

        // welcome msg accepted result
        String expectedResult = "Welcome To Manager's Page of Guru99 Bank";
        // actual msg from pom page
        String actualResult = driver.findElement(By.cssSelector("marquee[class=\"heading3\"]")).getText();
        //first assertion to login successful
        Assert.assertTrue(actualResult.contains(expectedResult),"The Actual & Expected Results Do Not Match");
    }


    @Test (priority = 1)
    public void validUsername_validPassword() throws InterruptedException, IOException {
        //enter the url
        driver.navigate().to("https://www.demo.guru99.com/V4/");
        // clear username field
        driver.findElement(By.xpath("//input[@type=\"text\"]")).clear();
        //enter username
        driver.findElement(By.xpath("//input[@type=\"text\"]")).sendKeys("mngr473669");
        //clear password field
        driver.findElement(By.xpath("//input[@type=\"password\"]")).clear();
        //enter password
        driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys("gEsumub");
        //click on login button
        driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();

        // welcome msg accepted result
        String acceptedResult = "Welcome To Manager's Page of Guru99 Bank";
        // actual msg from pom page
        String actualResult = driver.findElement(By.cssSelector("marquee[class=\"heading3\"]")).getText();
        //first assertion to login successful
        Assert.assertTrue(actualResult.contains(acceptedResult));


        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript(
                "arguments[0].style.border = '3px solid red'",
                driver.findElement(By.xpath("//table[@align=\"center\"]/tbody/tr[3]/td")));


        //take screenShot
        File scrfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //save screenShot to desired location
        FileUtils.copyFile(scrfile,new File("C:\\Users\\MMousa\\Downloads\\screenshot.png"));


        // second assertion to verify title of home page
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.demo.guru99.com/V4/manager/Managerhomepage.php");


        Thread.sleep(3000);

    }


    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}
