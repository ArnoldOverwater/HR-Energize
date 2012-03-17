package nl.hr.energize;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



public class App {
	private double KWhvalue;
	
	public App(){
		KWhvalue = 0.0;
	}
	
	public Double getValue(){
		try{
			
			//Download file
			URL url = new URL("http://www.eview.nl/DownloadData.ashx?FromDate=17-3-2012%200:00:00&TillDate=17-3-2012%2023:59:00&Datachannel=DSEDS937732");
			URLConnection urlConn = url.openConnection();
			

			//cookie value
			String cookie = "031D91E4004EDBCB01380046003600410045004300340044002D0043004400420";
				  cookie += "045002D0034003900460039002D0038004100410042002D004100460037004400";
				  cookie += "4500410036003000460032004100450000001845ABEA61FDCC010118057263331";
				  cookie += "CCE0142004C0041004100540000002F000000455F6B2DA06B678FFE00188DD805";
				  cookie += "DB4FE1361C4D";
			
		    //Het bijvoegen van een request property
				  							
		                               //type   //cookienaam  //value
			urlConn.setRequestProperty("Cookie", ".ASPXAUTH="+cookie);
			
			
			// De inputstream word direct verwerkt in een workbook.
			HSSFWorkbook workbook = new HSSFWorkbook(urlConn.getInputStream());
			HSSFSheet worksheet = workbook.getSheetAt(0);

			
			int RowIndex = 2;
			KWhvalue = 0.0;
			
			for(int i = 1; i < 97; i++){
				HSSFRow row = worksheet.getRow(i);
				HSSFCell cell = row.getCell(RowIndex);
				KWhvalue += cell.getNumericCellValue();
			}
			
			System.out.println(KWhvalue);
			
			/**
			OutputStream out = new BufferedOutputStream(new FileOutputStream("meting.xls"));
			byte[] buf = new byte[1024];		
			
			int byteRead = 0;
			while((byteRead = is.read(buf)) != -1){
				out.write(buf, 0, byteRead);
			}

			
			is.close();
			out.close();
		*/
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return KWhvalue;
}
}
