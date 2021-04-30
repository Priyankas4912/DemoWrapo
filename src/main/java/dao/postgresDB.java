package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class postgresDB 
{
	public List<ExportBank> listDataFetching(PrintStream out)
	{
		SessionFactory factory=null;
		Session session=null;
		List<ExportBank> bankdata=null;

		try 
		{
			Configuration cfg=new Configuration();  				   //creating configuration object  
			cfg.configure("hibernate.cfg.xml");						  //populates the data of the configuration file   	      
			factory=cfg.buildSessionFactory(); 		 //creating seession factory object   
			session=factory.openSession();   				//creating session object

			Transaction  tx = session.beginTransaction();      //creating transaction object 
			String sql = "SELECT * FROM exportbank where mobstatus='Pending'";
			@SuppressWarnings("rawtypes")
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(ExportBank.class);
			bankdata= query.list();
			tx.commit();
		} 
		catch (HibernateException e) 
		{	         
			System.out.println(e);
			System.setOut(out);
		} 
		finally 
		{
			session.close(); 
			factory.close();	        
		}
		//System.out.println(bankdata.size());
		System.setOut(out);	
		return bankdata;
	}

	public List<ExportBank> mobilityStatusUpdation(List<ExportBank> dataListBank,PrintStream out)
	{

		SessionFactory factory=null;
		Session session=null;
		try 
		{
			Configuration cfg=new Configuration();  				   //creating configuration object  
			cfg.configure("hibernate.cfg.xml");						  //populates the data of the configuration file   	      
			factory=cfg.buildSessionFactory(); 		 //creating seession factory object   
			session=factory.openSession();   				//creating session object
			//session=factory.openSession();

			for(ExportBank eb:dataListBank)
			{			
				try
				{
					if(eb.getMobStatus().trim().equals("Approved"))
					{						
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
					}					
				} 
				catch (Exception e) 
				{						
					//e.printStackTrace();  // Null Data				
				}
			}	
		} 
		catch (HibernateException e) 
		{	         
			System.out.println(e); 
		} 
		finally 
		{
			session.close(); 
			factory.close();	        
		}
		System.setOut(out);	
		return dataListBank;
	}
	/*	public static void main(String args[]) throws InterruptedException, FileNotFoundException
	{
		postgresDB pg=new postgresDB();
		List<ExportBank> dataListBank = new ArrayList<ExportBank>();
		PrintStream out = new PrintStream(new FileOutputStream("D:\\VyomLabs\\Log.txt"));
		System.setOut(out);
		dataListBank=pg.listDataFetching(out);
		System.out.println(dataListBank.size());

		boolean errorFlag=true, match=false;

			Map<String, Integer> countsmain = new HashMap<String, Integer>();
		List<String> DuplicateBank = new ArrayList<String>();
		for (ExportBank ed : dataListBank) 
		{
			String strmain=ed.getDate1()+" "+ed.getAmount()+" "+ed.getURN().subSequence(0, 3);		        	
			if (countsmain.containsKey(strmain))
			{	countsmain.put(strmain, countsmain.get(strmain) + 1);	}
			else 
			{	countsmain.put(strmain, 1);	             				}					
		}											

		for (Map.Entry<String, Integer> entry1 : countsmain.entrySet()) 
		{
			if(entry1.getValue()>1)
			{
				System.out.println(entry1.getKey() + " = " + entry1.getValue());
				DuplicateBank.add(entry1.getKey());
			}														
		}
		System.out.println(DuplicateBank.size()); //2156  3694
		//String BankCode="AXB",amount="10",rDate="27-11-2018",deptxt="27-11-2018";
//CBI28081800010/2196	28-08-2018	2196

		//System.out.println("Scroll DataType- "+BankCode+" Date- "+rDate+" Amt- "+amount+" Agent-"+agentCode+" BRHCN- "+mainBranch+" Deposite txt: "+deptxt+" Challan: "+challanNO+" URN- "+URN+" Scroll No "+scrolltxt);																																	

		//dataListBank.r
		String currData=rDate+" "+amount+" "+BankCode;
		String currData1=deptxt+" "+amount+" "+BankCode;
		if(DuplicateBank.contains(currData1)||DuplicateBank.contains(currData))
		{
			System.out.println("Duplicate");
		}
		//else
		 		String BankCode="AXBN",amount="10",rDate="27-11-2018",deptxt="27-11-2018";
		 {
			 for(ExportBank eb:dataListBank)
			 {
				 if( (eb.getURN().substring(0, 3).equals(BankCode.substring(0, 3))||eb.getURN().substring(0, 3).equals(BankCode)||eb.getURN().substring(0, 4).equals(BankCode)||eb.getURN().substring(0, 5).equals(BankCode)) && (eb.getDate1().equals(deptxt)||eb.getDate1().equals(rDate)) && eb.getAmount().equals(amount))
				 {


					 // System.out.println("Matched");
					 System.out.println(eb.getURN());
										 //bankURN.clear();
					 //bankURN.sendKeys(eb.getURN().trim());	
					 // Thread.sleep(200);
					 //acceptBtn.click();
					 // Thread.sleep(3000);

					 try
					 {
						 if(eb.getURN().equals("AXB26111800103/10"))
						 {

							 System.out.println("Perfect Match  ");
							 int z=29,y=0,x=z/y;
							 System.out.println(x);

						 }
						 //driver = DriverUtility.search(driver,pageTitle);
						 driver.switchTo().activeElement();
							WebElement err=driver.findElement(By.xpath("html/body/p[4]/a"));
							System.out.println("Approval_Pending Errorrrrr....!     "+err.getText());
							WebElement s1=driver.findElement(By.xpath("html/body"));
							errOr=s1.getText();
							System.out.println(errOr);
						 //err.click();
						// if(errorFlag==true)

							 errorFlag=true;
							 System.out.println("Error true");



					 }
					 catch(Exception e)
					 { 
						 errorFlag=false;
						 System.out.println("error not found");
					 }
					 if(errorFlag==false)
					 {
						 System.out.println("break");
						 match=true;	
						 break;
					 }
				 }				
			 }


		 }
		 System.out.println("Exit IfElse For lopp Break");
		 WebElement scrollNo1=driver.findElement(By.xpath("html/body/center[2]/form/table/tbody/tr[4]/td[2]"));
		scrolltxt1=scrollNo1.getText().trim();
		if(scrolltxt1.equals(scrolltxt))
		{
			holdBtn.click();
			System.out.println("Match not found in list");
		}

		 if(match==false)
		 {
			 //write file hold
			 System.out.println("Match not found in list & Hold");
			 //er.setBankUrn("Not Found");
			er.setMobilityStatus("Hold");
			holdBtn.click();
		 }	
	}*/

}
