package Utility;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;

public class DriverUtility 
{
	public static WebDriver driver;

	public static WebDriver getDriverIE(PrintStream out)
	{
		File file = new File("D:\\VyomLabs\\DevelopmentKit\\Drivers\\IEDriverServer.exe");   //Pune
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		driver = new InternetExplorerDriver();
		System.setOut(out);
		
		return driver;
	}
	public static WebDriver getDriverChrome(PrintStream out)
	{
		File file = new File("D:\\VyomLabs\\DevelopmentKit\\Drivers\\chromedriver.exe");   //Pune
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver=new ChromeDriver();
		System.setOut(out);
		//driver.manage().window().maximize();
		
		return driver;
	}
	public static WebDriver getDriverMozilla(PrintStream out)
	{
		File file = new File("D:\\VyomLabs\\DevelopmentKit\\Drivers\\geckodriver.exe");   //Pune
		System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
		driver=new FirefoxDriver();
		System.setOut(out);
				
		return driver;
	}

	public static AutoItX getAutoIT()
	{
		AutoItX x;
		String jacobDllVersionToUse;
		String path="D:\\VyomLabs\\DevelopmentKit\\AutoIT\\lib\\"; //Mumbai
		//String path="E:\\VyomLabs\\DevlopmentKit\\AutoIT\\lib\\"; //Channai

		// String jacobDllVersionToUse;

		if (jvmBitVersion().contains("32")) 
		{
			jacobDllVersionToUse = path+"jacob-1.18-x86.dll";
		}
		else 
		{
			jacobDllVersionToUse = path+"jacob-1.18-x64.dll";
		}

		//File file = new File("D:\\VyomLabs\\DevlopmentKit\\AutoIT\\lib", jacobDllVersionToUse);
		File file = new File(jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		return x= new AutoItX() ;
	}
	private static String jvmBitVersion()
	{
		return System.getProperty("sun.arch.data.model");
	}

	public static String createDir(String path, String curDate)
	{
		String downloadPath=null;
		/*Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd_hh-mm-ss-a");
		String formattedDate = sdf.format(date);*/
		//	System.out.println(formattedDate); // 12/01/2011 4:48:16 PM

		File file = new File(path+curDate);
		if (!file.exists()) 
		{
			if (file.mkdir()) 
			{
				//System.out.println("Directory is created! ");
				downloadPath=file.getAbsolutePath();
				//System.out.println(downloadPath);                
			} 
			else 
			{
				//System.out.println("Failed to create directory!");
			}
		}
		return downloadPath;		
	}
	
	public static String createDirLog(String createNew, String curDate)
	{
		String downloadPath=null;
	/*	Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd_hh-mm-a");
		String formattedDate = sdf.format(date);*/
		//	System.out.println(formattedDate); // 12/01/2011 4:48:16 PM

		File file = new File(createNew+"\\Log");
		if (!file.exists()) 
		{
			if (file.mkdir()) 
			{
				System.out.println("Directory is created! ");
				downloadPath=file.getAbsolutePath();
				System.out.println(downloadPath);                
			} 
			else 
			{
				System.out.println("Failed to create directory!");
			}
		}
		return downloadPath;		
	}

	public static void waitForAlert(WebDriver driver)throws InterruptedException
	{
		int i = 0;
		while (i++ < 30) 
		{
			try 
			{
				Alert alert = driver.switchTo().alert();
				alert.accept();				
				break;
			} 
			catch (NoAlertPresentException e)
			{
				Thread.sleep(1000);
				continue;
			}
		}
	}

	public static String waitForAlertMsg(WebDriver driver)throws InterruptedException
	{
		int i = 0;
		while (i++ < 30) 
		{
			try 
			{
				Alert alert = driver.switchTo().alert();				
				return alert.getText();
			} 
			catch (NoAlertPresentException e)
			{
				Thread.sleep(1000);
				continue;
			}
		}
		return null;
	}

	public static boolean waitForAlertMsgAccept(WebDriver driver,String str)throws InterruptedException
	{
		boolean present=false;
		int i = 0;		
		while (i++ < 30) 
		{
			try 
			{				
				Alert alert = driver.switchTo().alert();
				if(alert.getText().trim().contains(str))
				{
					alert.accept();
					present=true;
					break;
				}
			} 
			catch (NoAlertPresentException e)
			{
				Thread.sleep(1000);				
				continue;
			}
		}
		return present;
	}

	public static WebDriver search(WebDriver driver, String window) 
	{
		try 
		{
			int i = 0;
			while (i++ < 90) 
			{
				//System.out.println("search window");
				Set<String> allWindowHandles = driver.getWindowHandles();
				for (String currentWindowHandle : allWindowHandles) 
				{
					driver.switchTo().window(currentWindowHandle);
					if (driver.getTitle().toString().equalsIgnoreCase(window))
					{
						break;
					} 
				}
				if (driver.getTitle().toString().equalsIgnoreCase(window)) 
				{
					//System.out.println("found");
					break;
				}
				Thread.sleep(1000);
				continue;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(e);
		}
		return driver;
	}

	public static void ClearBrowserCacheIE() throws Exception
	{
		Thread.sleep(1000);		
		System.out.println("In clearBrowser");		
		Runtime.getRuntime().exec("taskkill /IM iedriverserver.exe /F");

	}
	
	public static void ClearBrowserCacheChrome() throws Exception
	{
		Thread.sleep(1000);		
		System.out.println("In clearBrowser");		
		Runtime.getRuntime().exec("taskkill /IM chromedriver.exe /F");

	}
	
	public static void ClearBrowserCacheMozilla() throws Exception
	{
		Thread.sleep(1000);		
		System.out.println("In clearBrowser");		
		Runtime.getRuntime().exec("taskkill /IM firefox.exe /F");

	}
	public static String IEStop(AutoItX x)
	{
		try
		{
			int i=0;
			while(i++<5)
			{
				try
				{					
					if(x.winExists("Internet Explorer"))
					{
						x.winActivate("Internet Explorer");
						String txtIE=x.winGetText("Internet Explorer");
						if(txtIE.contains("Check online for a solution and close the program Close the program"))
						{
							x.controlFocus("Internet Explorer", "", "[CLASS:Button; INSTANCE:2]");
							x.controlClick("Internet Explorer", "", "[CLASS:Button; INSTANCE:2]");
						}	
					}
					break;
				}
				catch(Exception e)
				{
					//System.out.println("AutoIt catch");
					Thread.sleep(1000);
					continue;
				}
			}		
		}
		catch(Exception e)
		{
			System.out.println("in side try catch Main auto it");
			System.out.println(e);
		}				
		return null;		
	}
	
	public static String Inputfile(String excelFileWithPath )
	{		
		File dir = new File(excelFileWithPath);
		File [] files = dir.listFiles();
		for (File excelFile : files) 
		{
			if(excelFile.toString().endsWith(".xlsx")||excelFile.toString().endsWith(".xls"))
			{
				return excelFile.toString();
				
			}			
		}	
	//	System.setOut(out);
		return null;		
	}
	
	
	public static void copyFile(File source, File dest) throws IOException 
	{
		Files.copy(source.toPath(), dest.toPath());
		System.out.println("file copied");
	}
	public static void moveFile(File source, File dest) throws IOException 
	{
		Files.move(source.toPath(), dest.toPath());
		System.out.println("file moved");
	}
	
	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {          
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) 
		{
			if(file.toString().endsWith(".xlsx")||file.toString().endsWith(".xls"))
			{
				if (file.lastModified() > lastMod) 
				{
					choice = file;
					lastMod = file.lastModified();
				}
			}

		}
		return choice;
	}

	public static AutoItX dClose(AutoItX x)
	{
		try
		{
			System.out.println("auit win txt");
			int i=0;
			while(i++<5)
			{				
				try
				{	
					x.winWaitActive("Windows Internet Explorer", "", 2);
					if(x.winExists("Windows Internet Explorer"))
					{
						x.winActivate("Windows Internet Explorer");
						String IEtxt=x.winGetText("Windows Internet Explorer");
						//System.out.println("win txt: "+IEtxt.trim());
						String newLine = System.getProperty("line.separator");
						String maintxt="&Yes"+newLine+" &No"+newLine+newLine+" The webpage you are viewing is trying to close the window."+newLine+newLine+" Do you want to close this window?";

						//if(IEtxt.trim().contains(main))
						{
						System.out.println("handled autoIT");
						x.controlFocus("Windows Internet Explorer", "", "[CLASS:Button; INSTANCE:1]");
						x.controlClick("Windows Internet Explorer", "", "[CLASS:Button; INSTANCE:1]");							
						break;
						}
					}
					else
					{
						throw new MyException("This is My error Message");
					}
				}
				catch(Exception e)
				{
					//System.out.println("AutoIt catch");
					Thread.sleep(1000);
					continue;
				}
			}		
		}
		catch(Exception e)
		{
			System.out.println("in side try catch Main auto it");
			System.out.println(e);
		}				
		return x;	
	}


}
