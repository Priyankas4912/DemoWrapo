package Utility;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutomationUtil
{
	

	/*public String createOUTPUTtxt(String excelPath,String msg,String fileName)
	{
		String attach=null;
		
		File f1= new File(excelPath+"\\"+fileName);
		String fType;
		String fileExtensionName = f1.getPath().substring(f1.getPath().indexOf("."));
		if(fileExtensionName.equals(".xlsx"))
		{	 		
			fType=".xlsx";
			System.out.println("File type="+fType);
		}
		else if(fileExtensionName.equals(".xls"))
		{
			fType=".xls";
			System.out.println("File type="+fType);
		}
		else 
		{
			fType=fileExtensionName;
		}
		attach=f1.getPath();

		try
		{
			String mainPath=excelPath+"\\"+fileName;
			PrintWriter writer = new PrintWriter(excelPath + "OUTPUT.txt","UTF-8");			

			writer.write("{" + "\"success\"" + ":true," + "\n" + "\"message\""
					+ ":\""+msg+"\"," + "\n"
					+ "\"operationsTotal\"" + ":0," + "\n"
					+ "\"operationsPerformed\"" + ":0," + "\n"
					+ "\"outputParameters\"" +":[\n{" +

					"\n" + "\"name\"" + ":\"" + mainPath + "\","
					+ "\n" + "\"value\"" + ":\"" + mainPath + "\"" + ","
					+ "\n" + "\"type\""	+ ":" + "\"File\"" + ","
					+ "\n" + "\"displayName\"" + ":\""+ fileName + "\","
					+ "\n" + "\"extension\""+ ":" + "\""+fType+"\"" + "}" + "\n]" + "\n}");
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return attach;
	}*/

	public String createFile(String excelPath,String fileName,String msg, PrintStream out) 
	{
		String attach=null;
		try
		{

			/*System.out.println("Workflow Folder: " + excelPath);
			System.out.println("File Name :" +fileName);
*/
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm-ss_");
			Date date = new Date();			
			String curDate=dateFormat.format(date);
			//System.out.println(curDate);

			File f1= new File(excelPath+"\\"+fileName);
			String newName=curDate+fileName;

			File f2= new File(excelPath+"\\"+newName);
			boolean success = f1.renameTo(f2);
			//System.out.println(success);
			//System.out.println("Old File Name: "+f1.getPath());
			System.out.println("New File Name: "+f2.getPath());

			String fileExtensionName = f2.getPath().substring(f2.getPath().indexOf("."));
			String fType=fileExtensionName;
			
		

			attach=f2.getPath();
			String mainPath=excelPath+"\\"+newName;
			PrintWriter writer = new PrintWriter(excelPath + "OUTPUT.txt","UTF-8");

			writer.write("{" + "\"success\"" + ":true," + "\n" + "\"message\""
					+ ":\""+msg+"\"," + "\n"
					+ "\"operationsTotal\"" + ":0," + "\n"
					+ "\"operationsPerformed\"" + ":0," + "\n"
					+ "\"outputParameters\"" + ":[\n{" +

					"\n" + "\"name\"" + ":\"" + newName + "\","
					+ "\n" + "\"value\"" + ":\"" + attach + "\"" + ","
					+ "\n" + "\"type\""	+ ":" + "\"File\"" + ","
					+ "\n" + "\"displayName\"" + ":\""+ newName + "\","
					+ "\n" + "\"extension\""+ ":" + "\""+fType+"\"" + "}" + "\n]" + "\n}");
			System.out.println();
			writer.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.setOut(out);
		return attach;
	}

}


