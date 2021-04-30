package zTestCode;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utility.DriverUtility;
import dao.ExportBank;

public class tryf 
{

	public static void main(String[] args)
	{

		boolean errorFlag=true, match=false,challanFlag=false;	

		Configuration cfg=new Configuration();  				   //creating configuration object  
		cfg.configure("hibernate.cfg.xml");						  //populates the data of the configuration file   	      
		SessionFactory factory=cfg.buildSessionFactory(); 		 //creating seession factory object   
		Session session=factory.openSession();   				//creating session object

		//List<ExportBank> bankdata=null;
		List<ExportBank> dataListBank = new ArrayList<ExportBank>();

		String BankCode="AXB",amount="10000",rDate="28-08-2018",deptxt="29-08-2018";

		try 
		{
			Transaction  tx = session.beginTransaction();      //creating transaction object 
			//String sql = "SELECT * FROM exportbank where mobstatus='Pending'";
			String sql = "SELECT * FROM ExportBank where ( URN LIKE '"+BankCode+"%' OR URN LIKE '"+BankCode.substring(0, 3)+"%' ) and (date1='"+rDate+"' or date1='"+deptxt+"') and amount='"+amount+"' and mobstatus='Pending'";
			@SuppressWarnings("rawtypes")
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(ExportBank.class);
			//bankdata= query.list();
			dataListBank= query.list();
			tx.commit();
			//System.out.println(bankdata);

			//dataListBank=bankdata;
			System.out.println(dataListBank.size());
		} 
		catch (HibernateException e) 
		{	         
			e.printStackTrace(); 
		} 
		/*finally 
		{
			session.close(); 
			factory.close();	        
		}
		 */
		//////////////////////////////////////////////////////////////////////////////////
		for(ExportBank eb:dataListBank)
		{	
			try
			{
				if( ( eb.getURN().substring(0, 3).equals(BankCode.substring(0, 3))||eb.getURN().substring(0, 4).equals(BankCode) || eb.getURN().substring(0, 3).equals(BankCode)|| eb.getURN().substring(0, 5).equals(BankCode))
						&& !eb.getMobStatus().equals("Approved") && eb.getAmount().equals(amount) && (eb.getDate1().equals(deptxt)||eb.getDate1().equals(rDate)) )
				{							 
					String challanNEW = "aa",challanNO = "aa";//=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/center[2]/form/table/tbody/tr[21]/td[2]")));
					//challanNEW=wait.until(ExpectedConditions.stalenessOf(challanNEW));
					if(challanNO.equals(challanNEW.trim())) 
					{								
						/*//Thread.sleep(200);
						//bankURN.clear();
						Thread.sleep(200);
						//bankURN.sendKeys(eb.getURN().trim());	  "
						 */						
						System.out.println("Checking..."+eb.getURN().trim());
						 Thread.sleep(200);

						 if(eb.getURN().trim().equals("AXB28081800047/10000"))
						 {
							 errorFlag=false;
							 System.out.println("Match zalla");
						 }
						 /*	//acceptBtn.click();
						Thread.sleep(3000);*/

						/* try
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
						 }*/
						 if(errorFlag==false)
						 {	
							 match=true;	
							 eb.setMobStatus("Approved");
							 /*er.setBankUrn(eb.getURN().trim());
							er.setMobilityStatus("Approved");
							er.setErrorReason(" ");*/

							 Transaction tran = session.beginTransaction();
							 try 
							 {	
								 session.saveOrUpdate(eb);						
								 tran.commit();					
							 }
							 catch (Exception e)
							 {
								 System.out.println("trans: "+e);
								 tran.rollback();
							 }

							 System.out.println("Record Matched @..........................."+eb.getURN());
							 break;
						 }	
					}
					else
					{ 
						System.out.println("Challan Number Changed..!!");
						challanFlag=true;
						match=false;
						/*er.setBankUrn("Not Found");
						er.setMobilityStatus("Hold");
						er.setErrorReason("Challan Number changed during processing record");	*/

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

		if(match==false)
		{
			//write file hold
			//Thread.sleep(500);
			/*WebElement holdBtn= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/center[2]/form/table/tbody/tr[22]/td[1]/input")));
			holdBtn.click();*/

			System.out.println("Match not found in list & Hold");
			/*er.setBankUrn("Not Found");
			er.setMobilityStatus("Hold");*/				
		}	
		try{}
		finally 
		{
			//session.flush();
			session.clear();
			session.close(); 
			factory.close();	        
		}



	}

}
