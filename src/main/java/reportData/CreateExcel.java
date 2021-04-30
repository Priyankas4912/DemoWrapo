package reportData;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateExcel 
{

	public void CreateExcelFile(List<exportReport> reportDatalist,String createfile,String CreateFileName,PrintStream out) throws Exception 
	{		
		try 
		{
			String excelFileWithPath=createfile+"\\"+CreateFileName;
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			{
				System.out.println("Create File");

				XSSFSheet sheet = workbook.createSheet("Process_Report");

				int rowCount = 0;
				int columnCount = -1;
				XSSFRow headerRow = sheet.createRow(0);

				Cell headerCell1 = headerRow.createCell(++columnCount);
				headerCell1.setCellValue(("Challan Number"));

				Cell headerCell2 = headerRow.createCell(++columnCount);
				headerCell2.setCellValue(("Scroll Number"));

				Cell headerCell3 = headerRow.createCell(++columnCount);
				headerCell3.setCellValue(("Scroll Amount"));

				Cell headerCell4 = headerRow.createCell(++columnCount);
				headerCell4.setCellValue(("Remitance Date"));

				Cell headerCell5 = headerRow.createCell(++columnCount);
				headerCell5.setCellValue(("Deposite Date"));

				Cell headerCell6 = headerRow.createCell(++columnCount);
				headerCell6.setCellValue(("Bank URN"));

				Cell headerCell7 = headerRow.createCell(++columnCount);
				headerCell7.setCellValue(("Mobility Status"));

				Cell headerCell8 = headerRow.createCell(++columnCount);
				headerCell8.setCellValue(("Error Reason"));

				for (exportReport eb : reportDatalist)
				{
					try
					{							
						XSSFRow row = sheet.createRow(++rowCount);

						columnCount = -1;
						Cell cell1 = row.createCell(++columnCount);
						cell1.setCellValue((String) eb.getChallanNumber());

						Cell cell2 = row.createCell(++columnCount);
						cell2.setCellValue((String) eb.getScrollNumber());

						Cell cell3 = row.createCell(++columnCount);
						cell3.setCellValue((String) eb.getScrollAmount());

						Cell cell4 = row.createCell(++columnCount);
						cell4.setCellValue((String) eb.getRemitanceDate());

						Cell cell5 = row.createCell(++columnCount);
						cell5.setCellValue((String) eb.getDepostieDate());

						Cell cell6 = row.createCell(++columnCount);
						cell6.setCellValue((String) eb.getBankUrn());

						Cell cell7 = row.createCell(++columnCount);
						cell7.setCellValue((String) eb.getMobilityStatus());

						Cell cell8 = row.createCell(++columnCount);
						cell8.setCellValue((String) eb.getErrorReason());
					} 
					catch (Exception e)
					{

					}
				}				

				try (FileOutputStream outputStream = new FileOutputStream(excelFileWithPath)) 
				{
					workbook.write(outputStream);
				}
			}
			workbook.close();
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
		System.setOut(out);
	}

}
