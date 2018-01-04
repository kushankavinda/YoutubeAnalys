package Reader;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String[] trend_videos() throws BiffException, IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w = Workbook.getWorkbook(inputWorkbook); // Get the first sheet
		Sheet sheet = w.getSheet(0);

		/*
		 * //get current date and time DateTimeFormatter dtf =
		 * DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); LocalDateTime now =
		 * LocalDateTime.now(); System.out.println("local date"+dtf.format(now));
		 * //2016/11/16 12:08:43
		 */

		String number_of_views_string_value, nu_likes_string_value, nu_dislikes_string_value, video_id;
		int number_of_views, nu_likes, nu_dislikes;
        
		
		for (int i = 2; i < sheet.getRows(); i++) {
			Cell cell1 = sheet.getCell(5, i);
			
			try {
				CellType type = cell1.getType(); 
				
				if (type == CellType.NUMBER) { 
						
				number_of_views_string_value = cell1.getContents();
				number_of_views = Integer.parseInt(number_of_views_string_value);
				// videos view <100
					if (number_of_views<100) {
						Cell cell2 = sheet.getCell(6, i);
						nu_likes_string_value = cell2.getContents();
						nu_likes = Integer.parseInt(nu_likes_string_value);
	
						Cell cell3 = sheet.getCell(7, i);
						nu_dislikes_string_value = cell3.getContents();
						nu_dislikes = Integer.parseInt(nu_dislikes_string_value);
						
						//analys likes and dislikes
						if (nu_likes >  nu_dislikes || nu_likes >1) {
							Cell cell4 = sheet.getCell(0, i);
							video_id = cell4.getContents();
							System.out.println("this is a trending video : " + video_id);
						}
					}
				
				}
			} catch (Exception e) {
				System.out.println("exception :"+e);
			}
		}
		return null;
	}

	public static void main(String[] args) throws IOException, BiffException {
		ReadExcel test = new ReadExcel();
		test.setInputFile("C:\\eclips for java SE\\GBvideos.xls");
		test.trend_videos();
		// test.read();
	}
}