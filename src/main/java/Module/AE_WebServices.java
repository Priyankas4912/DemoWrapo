package Module;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class AE_WebServices 
{




	/*public static void main(String[] args)throws Exception 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm");
		Date date = new Date();			
		String curDate=dateFormat.format(date);

		PrintStream out = new PrintStream(new FileOutputStream("D:\\VyomLabs\\Log.txt"));
		System.setOut(out);


		mobilityScrollPhase2(curDate,out);
	}*/
	public static void mobilityScrollPhase2(String curDate, PrintStream out, String iPAddressAE2, String orgCodeAE, String workFlowNameBOT1, String tenantAdmin, String workFlowNameBOT2, String botName)
	{
		try
		{
			String urlBase="http://"+iPAddressAE2+":8080/aeengine/rest/";
			String sent=getToken(out,urlBase);
			System.out.println("Sent Message:  "+sent);
			System.out.println(" ");

			String s = StringUtils.substringBetween(getToken(out,urlBase), "sessionToken", "roles");
			String token=s.substring(3, s.length()-3);
			System.out.println("Token:   "+token);
			System.out.println(" ");
			Thread.sleep(2000);
			
			if(botName.equals("MOBILITY-BOT-ID-1"))
			{
				String receivedBOT1=executeBOT1(token,curDate,out,urlBase,orgCodeAE,workFlowNameBOT1,tenantAdmin);
				System.out.println("Recived Message BOT1 :  "+receivedBOT1);
				System.out.println(" ");
				Thread.sleep(2000);
			}
			

			if(botName.equals("MOBILITY-BOT-ID-2"))
			{
				String receivedBOT2=executeBOT2(token,curDate,out,urlBase,orgCodeAE,workFlowNameBOT2,tenantAdmin);
				System.out.println("Recived Message BOT2 :  "+receivedBOT2);
				System.out.println(" ");
				Thread.sleep(2000);
			}

			
			String logoutResponse= logoutAERestfulWS(token,urlBase,out);
			System.out.println("Recived Message Logout-AE :  "+logoutResponse);
			System.out.println(" ");
		} 
		catch (Exception e) 
		{			
			System.out.println(e);
		}
		System.setOut(out);
	}

	public static String getToken(PrintStream out, String urlBase) throws Exception
	{
		String inputLine;
		String sessionToken = null;
		try 
		{
			URL obj = new URL(urlBase + "authenticate");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");

			String postDataBody = "username=tvsAdmin&password=Password0#";
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());

			wr.writeBytes(postDataBody);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String aeResponse = null;
			while ((inputLine = in.readLine()) != null) 
			{
				aeResponse = inputLine;
			}

			sessionToken = aeResponse;

			in.close();
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		System.setOut(out);
		return sessionToken;
	}

	public static String executeBOT1(String token, String curDate, PrintStream out, String urlBase, String orgCodeAE, String workFlowNameBOT1, String tenantAdmin) throws Exception 
	{
		//String requestJSON = "{  \"orgCode\": \"TENANT1\",  \"workflowName\": \"Add Two Numbers - Without Scripts\",  \"userId\": \"Test Tenant 1\",  \"source\": \"AutomationEdge \",  \"sourceId\": null,  \"responseMailSubject\": \"null\",  \"params\": [    {      \"name\": \"param1\",      \"value\": \"10\"    },    {      \"name\": \"param2\",      \"value\": \"20\"    }  ]}";

		String aeRequestNo = null;
		try
		{
			String requestJSON= " {"
					//+ "	\"orgCode\": \"TVSCS_DEV\","
					//+ "\"workflowName\": \"TVS-CollectionUpdate-Phase2\","
					//+ "	\"userId\": \"tvsAdmin\","
					+ "	\"orgCode\": \""+orgCodeAE+"\","
					+ "\"workflowName\": \""+workFlowNameBOT1+"\","					
					+ "	\"userId\": \""+tenantAdmin+"\","
					+ "	\"sourceId\": \""+curDate+"_BOT1_AE_Rest\","
					+ "	\"source\": \"Postman\","
					+ "\"responseMailSubject\": \"null\","
					+ "\"params\": []} ";


			URL url = new URL(urlBase + "execute");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("X-session-token", token);
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(requestJSON);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine, str = null;
			while ((inputLine = in.readLine()) != null)
				str = inputLine;
			in.close();
			//System.out.println("Received Message:  "+str);
			//String aeRequestNo = new JSONObject(str).getBigInteger("automationRequestId").toString();
			aeRequestNo=str;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.setOut(out);
		return aeRequestNo;
	}

	public static String executeBOT2(String token, String curDate, PrintStream out, String urlBase, String orgCodeAE, String workFlowNameBOT2, String tenantAdmin) throws Exception 
	{
		//String requestJSON = "{  \"orgCode\": \"TENANT1\",  \"workflowName\": \"Add Two Numbers - Without Scripts\",  \"userId\": \"Test Tenant 1\",  \"source\": \"AutomationEdge \",  \"sourceId\": null,  \"responseMailSubject\": \"null\",  \"params\": [    {      \"name\": \"param1\",      \"value\": \"10\"    },    {      \"name\": \"param2\",      \"value\": \"20\"    }  ]}";

		String aeRequestNo = null;
		try
		{
			String requestJSON= " {"
					//+ "	\"orgCode\": \"TVSCS_DEV\","
					//+ "\"workflowName\": \"TVS-CollectionUpdate-Phase2\","
					//+ "	\"userId\": \"tvsAdmin\","
					+ "	\"orgCode\": \""+orgCodeAE+"\","
					+ "\"workflowName\": \""+workFlowNameBOT2+"\","					
					+ "	\"userId\": \""+tenantAdmin+"\","
					+ "	\"sourceId\": \""+curDate+"_BOT2_AE_Rest\","
					+ "	\"source\": \"Postman\","
					+ "\"responseMailSubject\": \"null\","
					+ "\"params\": []} ";


			URL url = new URL(urlBase + "execute");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("X-session-token", token);
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());

			wr.writeBytes(requestJSON);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine, str = null;
			while ((inputLine = in.readLine()) != null)
				str = inputLine;
			in.close();
			//System.out.println("Received Message:  "+str);
			//String aeRequestNo = new JSONObject(str).getBigInteger("automationRequestId").toString();
			aeRequestNo=str;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.setOut(out);
		return aeRequestNo;
	}

	public static String logoutAERestfulWS(String token, String urlBase,PrintStream out) throws IOException
	{
		URL url = new URL(urlBase + "logout");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("X-session-token", token);
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine, str = null;
		while ((inputLine = in.readLine()) != null)
			str = inputLine;
		in.close();

		//System.out.println("Received Message:  "+str);
		//String aeRequestNo = new JSONObject(str).getBigInteger("automationRequestId").toString();
		String aeRequestNo=str;

		System.setOut(out);
		return aeRequestNo;		
	}

}
