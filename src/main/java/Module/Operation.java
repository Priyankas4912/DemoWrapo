package Module;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.FindActiveElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utility.DriverUtility;
import autoitx4java.AutoItX;

public class Operation 
{	
	
	public static boolean loginFlag=false;
	//public static boolean loginFlagLMS=false;
	public static boolean adminFlag=false;

	public WebDriver Load_url(WebDriver driver, String url, PrintStream out)
	{
		driver.get(url);		
		return driver;
	}

	public WebDriver loginMobility(WebDriver driver, String UId, String Pass, PrintStream out) 
	{
		try
		{
			WebDriverWait wait=new WebDriverWait(driver, 30);

			Set<String> allWindowHandles = driver.getWindowHandles();		

			for (String currentWindowHandle : allWindowHandles)
				driver.switchTo().window(currentWindowHandle);

			System.out.println("Login");

			Thread.sleep(1000);
			WebElement userName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='Loginlogin1']")));			
			userName.sendKeys(UId);			
			//System.out.println("UserID");

			WebElement password =wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='Loginpassword']")));			
			password.clear();
			password.sendKeys(Pass);

			Thread.sleep(1000);
			password.sendKeys(Keys.TAB);
			//System.out.println("Password");
			Thread.sleep(500);
			WebElement btnLogin = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='LoginButton_DoLogin']")));			
			btnLogin.click();

			//System.out.println("Login btn");

			Thread.sleep(3000);
			try
			{
				WebElement loginErr= driver.findElement(By.xpath(".//*[@id='Login']/table/tbody/tr/td/table[2]/tbody/tr[1]/td"));
				if(loginErr.getText().equals("Login or Password is incorrect."))
				{
					System.out.println("Error in Login Mobility");
					Operation.loginFlag=true;
				}
			}
			catch(Exception e)
			{

			}
		} 
		catch (Exception e) 
		{
			Operation.loginFlag=true;
			System.out.println("Error In Loading Mobility Application : " + e);
		}
		System.setOut(out);
		return driver;
	}

	public WebDriver TraveseToMobility(WebDriver driver, PrintStream out) throws InterruptedException 
	{
		WebDriverWait wait = new WebDriverWait(driver,30);
		try 
		{	
			Thread.sleep(3000);
			//driver.switchTo().activeElement();
			Actions action = new Actions(driver);
			WebElement act = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='HeaderMenu1Container']/ul/li[5]/a/span[1]")));
			action.moveToElement(act).perform();
			//act.click();
			//	System.out.println("Action Link");			

			Thread.sleep(1000);
			WebElement App_Remittance=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='HeaderMenu1Container']/ul/li[5]/ul/li[1]/a/span[1]")));
			action.moveToElement(App_Remittance).perform();
			//System.out.println("Approve Remittance Link");

			/*Thread.sleep(1000);
			WebElement pending=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='HeaderMenu1Container']/ul/li[5]/ul/li[1]/ul/li[1]/a")));
			action.moveToElement(pending).click(pending).build().perform();
			System.out.println("Travelling for Pending Link Cases");*/

			Thread.sleep(1000);
			WebElement pending=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='HeaderMenu1Container']/ul/li[5]/ul/li[1]/ul/li[1]/a")));
			action.moveToElement(pending).build().perform();
			Thread.sleep(1000);
			WebElement holding=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='HeaderMenu1Container']/ul/li[5]/ul/li[1]/ul/li[3]/a/span[1]")));					
			action.moveToElement(holding).perform();					//.//*[@id='HeaderMenu1Container']/ul/li[5]/ul/li[1]/ul/li[3]/a/span[1]
			Thread.sleep(1000);
			action.click(holding).build().perform();
			System.out.println("Holding Link");

			Thread.sleep(3000);
		} 
		catch (Exception e) 
		{
			System.out.println("Error IN Traversal");
			System.err.println(e.getMessage());	
		}
		System.setOut(out);

		return driver;
	}

	public void logOutMobility(WebDriver driver,PrintStream out) 
	{
		WebDriverWait wait = new WebDriverWait(driver, 45);
		try 
		{
			Thread.sleep(1000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,-250)", "");//ScrollUP
			Actions action = new Actions(driver);

			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();		    
			String os = cap.getPlatform().toString();
			String v = cap.getVersion().toString();

			//System.out.println("Browser Name: "+browserName+" OS: "+os+" Version: "+v);


			if(browserName=="internet explorer")
			{					
				WebElement AdminHeader = driver.findElement((By.xpath("html/body/div[1]/ul/li/a/span[1]")));
				action.moveToElement(AdminHeader).click().build().perform();                                        //html/body/div[1]/ul/li/a/span[1]  *robo
				//System.out.println("Mobility Admin Exit");									//.//*[@id='HeaderMenu1Container']/ul/li[6]/a/span[1]  *old


				//html/body/div[1]/ul/li/ul/li[2]/a/span[1]
				/*WebElement LogoutBtn =driver.findElement(By.xpath("html/body/div[1]/ul/li/ul/li/a/span[1]")) ;
				LogoutBtn.click();																//html/body/div[1]/ul/li/ul/li/a/span[1]  *robo
				System.out.println("Mobility Logout Exit");												//.//*[@id='HeaderMenu1Container']/ul/li[6]/ul/li[3]/a/span[1] *old
				Thread.sleep(2000);*/
			}
			else
			{				
				//WebElement AdminHeader = driver.findElement((By.xpath(".//*[@id='HeaderMenu1Container']/ul/li[6]/a/span[1]"))); //*old
				WebElement AdminHeader = driver.findElement((By.xpath("html/body/div[1]/ul/li/a/span[1]")));//*robo
				action.moveToElement(AdminHeader).perform();                                         
				//System.out.println("Mobility Admin Exit");									  

				//WebElement LogoutBtn =driver.findElement(By.xpath(".//*[@id='HeaderMenu1Container']/ul/li[6]/ul/li[3]/a/span[1]")); //*new
				WebElement LogoutBtn =driver.findElement(By.xpath("html/body/div[1]/ul/li/ul/li[2]/a/span[1]")); // *robo
				LogoutBtn.click();								//html/body/div[1]/ul/li/ul/li[2]/a/span[1]							 
				System.out.println("Mobility Logout Exit");											
			}
			Thread.sleep(2000);
			driver.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.setOut(out);
	}

}
