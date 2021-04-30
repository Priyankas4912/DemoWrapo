package businessLogic;


import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import reportData.exportReport;
import dao.ExportBank;
import Application.App;
import Utility.DriverUtility;

public class MobilitycollectionUpdate 
{

	public static int genericFlag=0;
	public static boolean appError=false;
	public static String pendingCount="1";
	public static String genericError="1";

	public static ArrayList<exportReport> reportList=new ArrayList<exportReport>();

	public WebDriver collectionScroll(WebDriver driver,List<ExportBank> dataListBank,PrintStream out)
			throws InterruptedException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm-ss");
		Date date = new Date();			
		String curDate=dateFormat.format(date);
		System.out.println("Current Date: "+curDate);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		exportReport er=new exportReport();

		String BankCode,amount,depAmt,rDate,deptxt,challanNO,pndCnt,scrolltxt,errOr = " ";

		/*Configuration cfg=new Configuration();  				   //creating configuration object  
		cfg.configure("hibernate.cfg.xml");						  //populates the data of the configuration file   	      
		SessionFactory factory=cfg.buildSessionFactory(); 		 //creating seession factory object   
		Session session=factory.openSession();   				//creating session object
		 */
		try 
		{
			boolean errorFlag=true, match=false,challanFlag=false;	
			String pageTitle="Robotics Approval_Pending";
			
			driver = DriverUtility.search(driver,pageTitle);
			Thread.sleep(1000);
			driver.switchTo().activeElement();			
			Thread.sleep(1000);	

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,-250)", "");//ScrollUP

			/*if(genericFlag>2)
			{
				driver.findElement(By.xpath("html/body/div[1]/ul/li/a/span[1]")).click();
				System.out.println("click Admin");
				Thread.sleep(2000);
			}*/
			WebElement pendingCnt=driver.findElement(By.xpath("html/body/center[2]/h3"));	
			genericFlag=0;
			pndCnt=pendingCnt.getText().trim();				
			pendingCount=pndCnt.replaceAll("[^0-9]", "");			
			System.out.println("Pending Count: "+pendingCount);
			if(pendingCount.length() == 0)
			{
				pendingCount="1";			
			}

			WebElement bankCode=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='bank']")));
			BankCode=bankCode.getAttribute("value").trim();						

			WebElement amt=driver.findElement(By.xpath("html/body/center[2]/form/table/tbody/tr[17]/td[2]/big/b")); 
			amount=amt.getText().trim();	

			WebElement depositeAmount=driver.findElement(By.xpath(".//*[@id='depositamount']")); 
			depAmt=depositeAmount.getAttribute("value").trim();

			WebElement remiDate=driver.findElement(By.xpath("html/body/center[2]/form/table/tbody/tr[6]/td[2]")); 

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();				
			c.setTime(sdf1.parse(remiDate.getText().substring(0, 10).trim()));					
			rDate = sdf.format(c.getTime());

			WebElement deDateM=driver.findElement(By.xpath("html/body/center[2]/form/table/tbody/tr[20]/td[2]/input"));
			deptxt=deDateM.getAttribute("value").trim();

			WebElement scrollNo=driver.findElement(By.xpath("html/body/center[2]/form/table/tbody/tr[4]/td[2]"));
			scrolltxt=scrollNo.getText().trim();  

			WebElement challan=driver.findElement(By.xpath("html/body/center[2]/form/table/tbody/tr[21]/td[2]"));
			challanNO=challan.getText().trim(); 

			WebElement bankURN= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='bankurn']")));
			WebElement acceptBtn=driver.findElement(By.xpath("html/body/center[2]/form/table/tbody/tr[22]/td[2]/input"));

			System.out.println("Scroll BankType: "+BankCode+" RemiDate: "+rDate+" Deposite txt: "+deptxt+" Scroll Amount: "+amount+" Deposite Amount: "+depAmt+" Challan: "+challanNO+" Scroll No: "+scrolltxt);																																	

			er.setChallanNumber(challanNO);
			er.setScrollNumber(scrolltxt);
			er.setScrollAmount(amount);
			er.setRemitanceDate(rDate);
			er.setDepostieDate(deptxt);


			/*try 
			{
				Transaction  tx = session.beginTransaction();      //creating transaction object 
				//String sql = "SELECT * FROM exportbank where mobstatus='Pending'";
				String sql = "SELECT * FROM ExportBank where ( URN LIKE '"+BankCode+"%' OR URN LIKE '"+BankCode.substring(0, 3)+"%' ) and (date1='"+rDate+"' or date1='"+deptxt+"') and amount='"+amount+"' and mobstatus='Pending'";
				@SuppressWarnings("rawtypes")
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity(ExportBank.class);
				dataListBank= query.list();
				tx.commit();
				//System.out.println(bankdata);

				//dataListBank=bankdata;
				System.out.println(dataListBank.size());
			} 
			catch (HibernateException e) 
			{	         
				e.printStackTrace(); 
			} */


			{				//////////////////////////////////////////////////////////////////////////////////
				if(BankCode.equals("IBTL"))
				{
					BankCode="IBG";
				}
				for(ExportBank eb:dataListBank)
				{	
					try
					{
						if( ( eb.getURN().substring(0, 3).equals(BankCode.substring(0, 3))||eb.getURN().substring(0, 4).equals(BankCode) || eb.getURN().substring(0, 3).equals(BankCode)|| eb.getURN().substring(0, 5).equals(BankCode))
								&& !eb.getMobStatus().equals("Approved") && ( eb.getAmount().equals(amount) || eb.getAmount().equals(depAmt)) && (eb.getDate1().equals(deptxt)||eb.getDate1().equals(rDate)) )
						{							 
							WebElement challanNEW=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/center[2]/form/table/tbody/tr[21]/td[2]")));
							//challanNEW=wait.until(ExpectedConditions.stalenessOf(challanNEW));
							if(challanNO.equals(challanNEW.getText().trim())) 
							{								
								//Thread.sleep(200);
								bankURN.clear();
								Thread.sleep(200);
								bankURN.sendKeys(eb.getURN().trim());	
								System.out.println("Checking..."+eb.getURN().trim());
								Thread.sleep(200);

								acceptBtn.click();
								Thread.sleep(3000);

								try
								{							
									driver = DriverUtility.search(driver,pageTitle);
									driver.switchTo().activeElement();
									WebElement err=driver.findElement(By.xpath("html/body/p[4]/a"));
									System.out.println("Approval_Pending Errorrrrr....!     "+err.getText());
									WebElement s1=driver.findElement(By.xpath("html/body"));
									errOr=s1.getText();
									//System.out.println(errOr);
									err.click();									
									er.setErrorReason(errOr);
									Thread.sleep(1000);
									errorFlag=true;
									System.out.println("Error Found ");
									//continue;
								}
								catch(Exception e)
								{
									errorFlag=false;
									System.out.println("Error Not found");
								}
								if(errorFlag==false)
								{	
									match=true;	
									eb.setMobStatus("Approved");
									er.setBankUrn(eb.getURN().trim());
									er.setMobilityStatus("Approved");
									er.setErrorReason(" ");	

									/*Transaction tran = session.beginTransaction();
									try 
									{	
										session.saveOrUpdate(eb);						
										tran.commit();					
									}
									catch (Exception e)
									{
										System.out.println("trans: "+e);
										tran.rollback();
									}*/

									System.out.println("Record Matched @..........................."+eb.getURN());
									break;
								}	
							}
							else
							{ 
								System.out.println("Challan Number Changed..!! "+challanFlag);
								challanFlag=true;
								match=false;
								er.setBankUrn("Not Found");
								er.setMobilityStatus("Hold");
								er.setErrorReason("Challan Number changed during processing record");	

								break;
							}
						}
					} 
					catch (Exception e) 
					{
						System.out.println("For Loop Error:  "+e);
						break;
					}					
				}   //for loop end
			}	

			if(match==false)
			{
				//write file hold
				//Thread.sleep(500);
				WebElement holdBtn= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/center[2]/form/table/tbody/tr[22]/td[1]/input")));
				holdBtn.click();

				System.out.println("Match not found in list & Hold");
				er.setBankUrn("Not Found");
				er.setMobilityStatus("Hold");				
			}					
		} 
		catch(StaleElementReferenceException se)
		{
			System.out.println("StaleElement:  "+se);
			try
			{
				driver.navigate().refresh();
				er.setMobilityStatus("Pending");				
				er.setErrorReason("Page Loading Error");
			}
			catch(Exception ez)
			{			}
		}
		catch (Exception e)
		{	
			Thread.sleep(500);
			System.out.println("Main try mobility:  "+e);	
			genericFlag++;

			WebElement body=driver.findElement(By.xpath("html/body"));
			genericError=body.getText();
			System.out.println("Gen Error: "+genericError);
			er.setErrorReason(genericError);

			
			System.out.println("Generic Error Flag: "+genericFlag);
			if(genericFlag<=4)
			{
				try
				{	driver.navigate().refresh();	}
				catch(Exception ez)
				{				}
			}
			if(genericFlag==5)
			{
				try
				{
					pendingCount="1";					
					appError=true;
				}
				catch(Exception ez)
				{	appError=true;		}
			}
			System.setOut(out);
		}

		reportList.add(er);
		System.out.println("End");	
		System.setOut(out);

		/*try{}
		finally 
		{
			session.clear();
			session.close(); 
			factory.close();	        
		}*/
		return driver;
	}
}
