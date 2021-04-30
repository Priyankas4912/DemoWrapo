package Application;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import reportData.CreateExcel;
import reportData.exportReport;
import businessLogic.MobilitycollectionUpdate;
import Module.AE_WebServices;
import Module.Operation;
import Utility.AutomationUtil;
import Utility.DriverUtility;
import Utility.EmailBO;
import autoitx4java.AutoItX;
import dao.ExportBank;
import dao.postgresDB;


public class App
{
	public static void main(String[] args) throws Exception 
	{
		//BOT-1 Config || BOT-2 Config || HIBERNATE CONFIG IPAddressAE_PROD=172.16.80.104

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm-ss");
		Date date = new Date();			
		String curDate=dateFormat.format(date);
		
		//AutoItX Aitx=DriverUtility.getAutoIT();
		Operation op = new Operation();	
		AutomationUtil su=new AutomationUtil();
		EmailBO emailBO=new EmailBO();
		postgresDB pg=new postgresDB();
		CreateExcel ce=new CreateExcel();
		MobilitycollectionUpdate cu=new MobilitycollectionUpdate();

		businessLogic.MobilitycollectionUpdate mcu = new businessLogic.MobilitycollectionUpdate();

		boolean logFlagMob=false,porpFlag=false;


		//DEV Machine Config                                      //  Check Hibernate LocalHost
		//String IPAddressAE="172.20.3.173",OrgCodeAE="TVSCS_DEV",workFlowNameBOT1="TVS-CollectionUpdate-Phase2",workFlowNameBOT2="COLLECTION UPDATE PHASE 2 BOT2",tenantAdmin="tvsAdmin",ramSize="6144";

		//UAT Machine Config                                    //  Check Hibernate LocalHost
		//String IPAddressAE="172.16.80.105",OrgCodeAE="TVSCS_UAT",workFlowNameBOT1="Collection Update BOT1",workFlowNameBOT2="Collection Update BOT2",tenantAdmin="tvsAdmin",ramSize="12000";


		//LIVE Machine Config                                   //  Check Hibernate LocalHost
		String IPAddressAE="172.16.80.104",OrgCodeAE="TVS_CREDIT_SERVICE",workFlowNameBOT1="Collection-Update-BOT-1",workFlowNameBOT2="Collection-Update-BOT-2",tenantAdmin="tvsAdmin",ramSize="20000";

		String host = "172.16.80.138",port = "25",UserMobility = null,PasswordMobility= null,URL_Mobility= null,CreateFileName = null,CreatedfilePath = null,start=curDate,
				maxC = null,password = null,mailFrom = null,mailTo = null,mailCC = null,subject = null,path=null,createfile=null,createfileLog=null,botName=null;

		int max = 0,runCnt=1,techError=1;	
		Properties p = null;

		try 
		{
			p = new Properties(); 
			FileInputStream f = new FileInputStream("D:\\VyomLabs\\jarsBats\\collectionUpdatePhaseII\\util\\CollectionUpdateProcess2.properties");
			p.load(f);		

			/////////////    BOT 1    ///////////////////////
			/*botName="MOBILITY-BOT-ID-1";
			UserMobility=p.getProperty("UserMobility_1").trim();				
			PasswordMobility=p.getProperty("PasswordMobility_1").trim();
			CreateFileName="CollectionUpdateBOT1.xlsx";
			path="D:\\VyomLabs\\CollectionUpdatePhaseII\\ColletionUpdateOutput_Report\\BOT1\\";*/

			/*______________________##_______ CHECK HIBERNATE localhost & MACHINE CONFIG _______###______________________*/

			/////////////    BOT 2    ///////////////////////
			botName="MOBILITY-BOT-ID-2";
			UserMobility=p.getProperty("UserMobility_2").trim();				
			PasswordMobility=p.getProperty("PasswordMobility_2").trim();
			CreateFileName="CollectionUpdateBOT2.xlsx";
			path="D:\\VyomLabs\\CollectionUpdatePhaseII\\ColletionUpdateOutput_Report\\BOT2\\";

			URL_Mobility=p.getProperty("URL_Mobility").trim();
			maxC=p.getProperty("MaxProcess").trim();
			max=Integer.parseInt(maxC);

			password = p.getProperty("mailPassword");
			mailFrom = p.getProperty("mailFrom");
			mailTo = p.getProperty("mailT0");
			mailCC = p.getProperty("mailCC");
			subject= p.getProperty("mailSubjectScroll")+" "+start.substring(0, start.length()-9)+" "+botName;

			porpFlag=true;
		} 
		catch (Exception e1) 
		{
			porpFlag=false;
			System.out.println("Properties file:  "+e1);
		}

		createfile=DriverUtility.createDir(path,curDate);
		createfileLog=DriverUtility.createDirLog(createfile,curDate);

		PrintStream out = new PrintStream(new FileOutputStream(createfileLog+"\\Log.txt"));
		System.setOut(out);

		System.out.println("Log file: "+createfileLog+"\\Log.txt");

		try
		{
			CreatedfilePath = createfile+"\\"+CreateFileName;//Excel Path
			if(porpFlag==true)
			{
				System.out.println("Properties File Found");
				List<ExportBank> dataListBankBOT1 = new ArrayList<ExportBank>();

				try
				{
					System.out.println("Mobility Application Start");
					WebDriver driver = null;

					driver = DriverUtility.getDriverChrome(out);
					driver = op.Load_url(driver, URL_Mobility,out);	
					driver = op.loginMobility(driver, UserMobility, PasswordMobility,out);

					if(Operation.loginFlag==false)
					{
						logFlagMob=true;

						//Reading DB
						System.out.println("Reading Records from  DataBase");

						dataListBankBOT1=pg.listDataFetching(out);						
						System.out.println("Data Size: "+dataListBankBOT1.size());

						/*if(UserMobility.equals("roboticsuser") || UserMobility.equals("robotics00") || UserMobility.equals("roboticuser") || UserMobility.equals("ROBOTICSUSER01") || UserMobility.equals("ROBOTICSUSER02"))
						{												}
						else
						{	driver = op.TraveseToMobility(driver,out);	}*/

						for (runCnt=1;runCnt<=max;runCnt++)
						{
							System.out.println("__________________________________  Automation Edge ________________________________________");
							System.out.println("Record No. :" + runCnt + " of " + max);

							driver= mcu.collectionScroll(driver,dataListBankBOT1,out);

							if(runCnt==1)
							{																		
								try
								{
									StringBuilder message =emailBO.emailStartMsg();											
									emailBO.sendEmailWithMessage(host, port, mailFrom, password, mailTo,mailCC, subject, message.toString(), out);
									System.out.println("Process Started E-Mail Notification sent to User.");
								} 
								catch (Exception ex) 
								{
									System.out.println("Could not send email.");											
									System.out.println(ex);
								}
							}

							if(MobilitycollectionUpdate.pendingCount.equals("1"))
							{
								System.out.println("Pending Count is 0");
								break;
							}
						}
						op.logOutMobility(driver,out);
					}
					driver.close();
					driver.quit();
					DriverUtility.ClearBrowserCacheChrome();								

					System.out.println("Mobility Application End");						
				}
				catch(Exception e)
				{
					DriverUtility.ClearBrowserCacheChrome();
					System.out.println(e);
				}	
				finally
				{
					System.out.println("Finally DB Entry");
					//Update DB					
					pg.mobilityStatusUpdation(dataListBankBOT1, out);
					//Write file 
					ce.CreateExcelFile(MobilitycollectionUpdate.reportList, createfile, CreateFileName, out);
				}
			}//Property file
		}
		catch (Exception e)
		{
			//System.out.println("File error DriverUtility Executed");

			System.out.println(e);
			System.setOut(out);
		}
		finally
		{
			Thread.sleep(1000);
			System.out.println("____________________________EXECUTION COMPLETED____________________________");	

			
			/*if(MobilitycollectionUpdate.genericError.contains("You have been successfully logged out. Click HERE to login again") ||
				     MobilitycollectionUpdate.genericError.contains("Admin Robotics Remittance Approval Pending Queue No Remittances Pending for Approval"))
				{
					 MobilitycollectionUpdate.appError=false;
				}*/
			
			// attachments
			String[] attachFiles = new String[1];
			String attachFile = CreatedfilePath;

			System.out.println("Generic Error: "+MobilitycollectionUpdate.genericError);
			

			if(porpFlag==false)
			{
				String msg="CollectionUpdate.properties File Not Found";
				String note=" [Please note E-mail will not be able to send as file not found]";
				System.out.println(msg+note);

				attachFile=su.createFile(createfile+"\\",CreateFileName,msg+note,out);	

				try
				{
					StringBuilder message =emailBO.emailBodyProp();					
					emailBO.sendEmailWithMessage(host, port, mailFrom, password, mailTo, mailCC, subject, message.toString(), out);
					System.out.println("Email sent.");
				} 
				catch (Exception ex) 
				{
					System.out.println("Could not send email.");
					System.out.println(ex);
				}
			}
			else if(logFlagMob==false)
			{
				String msg="Invalid Login Credentials for Moblility Application";
				String note=" [Please note:File will not be available for Download.]";
				System.out.println(msg+note);
				attachFile=su.createFile(createfile+"\\",CreateFileName,msg+note,out);
				try
				{
					StringBuilder message =emailBO.emailBodyInvMob();
					attachFiles[0] = attachFile;
					//emailBO.sendEmailWithAttachments(host, port, mailFrom, password, mailTo,mailCC, subject, message.toString(),attachFiles);
					emailBO.sendEmailWithMessage(host, port, mailFrom, password, mailTo, mailCC, subject, message.toString(), out);
					System.out.println("Email sent.");
				} 
				catch (Exception ex) 
				{
					System.out.println("Could not send email.");
					System.out.println(ex);
				}
			}	
			else if(!MobilitycollectionUpdate.genericError.equals("1") && MobilitycollectionUpdate.appError==true  )
			{
				/*
				 Admin Robotics Remittance Approval Pending Queue No Remittances Pending for Approval
				 You have been successfully logged out. Click HERE to login again
				 
				 This page isn?t working 172.16.80.141 is currently unable to handle this request. HTTP ERROR 500 Reload				 
				 This site can?t be reached The connection was reset. Try: Checking the connection Checking the proxy and the firewall Running Windows Network Diagnostics ERR_CONNECTION_RESET DETAILS
				 This site can?t be reached 172.16.80.141 refused to connect. Try: Checking the connection Checking the proxy and the firewall ERR_CONNECTION_REFUSED DETAILS
				 Admin Robotics Remittance Approval Pending Queue DB Connection Error: 1
				 			 
				 */
				String msg="Generic Error: "+MobilitycollectionUpdate.genericError.trim();

				//System.out.println(msg);
				attachFile=su.createFile(createfile+"\\",CreateFileName,msg,out);
				try
				{
					StringBuilder message =emailBO.emailBodyGen(MobilitycollectionUpdate.genericError);
					attachFiles[0] = attachFile;
					emailBO.sendEmailWithAttachments(host, port, mailFrom, password, mailTo,mailCC, "Technical Issue "+subject, message.toString(),attachFiles, out);
					System.out.println("Email sent.");
				} 
				catch (Exception ex) 
				{
					System.out.println("Could not send email.");
					System.out.println(ex);
				}
				/*if(techError==1)
				{
					techError++;
					try
					{
						AE_WebServices.mobilityScrollPhase2(curDate,out,IPAddressAE,OrgCodeAE,workFlowNameBOT1,tenantAdmin,workFlowNameBOT2,botName);

						System.out.println("Collection Update Phase 2 Mobility WorkFlow Triggered");
					}
					catch(Exception e)
					{
						System.out.println("AE Web Services could not be Triggered.");
						System.out.println(e);
					}
				}*/

			}
			else//(logFlagMob==true && porpFlag==true && MobilitycollectionUpdate.appError==false)
			{						
				String msg="Operation Performed Successfully";
				System.out.println(msg);
				attachFile=su.createFile(createfile+"\\",CreateFileName,msg,out);	

				try
				{
					StringBuilder message =emailBO.emailBody();
					attachFiles[0] = attachFile;
					emailBO.sendEmailWithAttachments(host, port, mailFrom, password, mailTo,mailCC, subject, message.toString(),attachFiles, out);
					System.out.println("Email sent.");
				} 
				catch (Exception ex) 
				{
					System.out.println("Could not send email.");
					System.out.println(ex);
				}
			}

		}		 
		System.setOut(out);		
	}
}