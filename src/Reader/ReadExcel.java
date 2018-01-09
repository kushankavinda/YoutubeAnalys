package Reader;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	private static String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public void same_video_id() throws BiffException, IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w1 = Workbook.getWorkbook(inputWorkbook); // Get the first sheet
		Sheet sheet = w1.getSheet(0);	
		String video_id,video_id_check;
		int count_of_video_id;
		List<String> mylist = new ArrayList<String>();
		int simmilar_id_checker;
		for(int i=1;i<sheet.getRows(); i++) {
			count_of_video_id=0;
			Cell cell = sheet.getCell(0, i);
			video_id_check = cell.getContents();
			simmilar_id_checker=0;		
					for(int j=2;j<sheet.getRows();j++) {
						Cell cell1 = sheet.getCell(0, j);
						video_id = cell1.getContents();	
						for(int k=0;k<mylist.size();k++) {
							if(video_id.equals(mylist.get(k))){
								++simmilar_id_checker;
							}
						}
						if(simmilar_id_checker==0) {					
							if(video_id_check.equals(video_id)) { 
								mylist.add(video_id);							
								++count_of_video_id;
								System.out.println("this id has equal ids : "+ (j+1));
							}
						}
					}
		System.out.println("video_id"+ " " +video_id_check+ "has nu of times : "+count_of_video_id);
		}
	}

	public String[] trend_videos(int quartile_1_of_view,int quartile_1_of_likes,int quartile_1_of_dislikes,int quartile_1_of_comments) throws BiffException, IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w = Workbook.getWorkbook(inputWorkbook); // Get the first sheet
		Sheet sheet = w.getSheet(0);

		String number_of_views_string_value, nu_likes_string_value, nu_dislikes_string_value, video_id,commnet_string_value;
		int number_of_views, nu_likes, nu_dislikes,nu_of_comment;
        //create thread videos array
		ArrayList<String> videos_distinct=new ArrayList<String>();
		
		int videos_distinct_checker;
		for (int i = 1; i < sheet.getRows(); i++) {
			
			videos_distinct_checker=0;
			try {			
				Cell cell1 = sheet.getCell(5, i);
				CellType type = cell1.getType(); 
						
				number_of_views_string_value = cell1.getContents();
				number_of_views = Integer.parseInt(number_of_views_string_value);
				
				// videos view > quartile_1_of_view
					if (number_of_views>quartile_1_of_view) {
						Cell cell2 = sheet.getCell(6, i);
						nu_likes_string_value = cell2.getContents();
						nu_likes = Integer.parseInt(nu_likes_string_value);
						
						Cell cell3 = sheet.getCell(7, i);
						nu_dislikes_string_value = cell3.getContents();
						nu_dislikes = Integer.parseInt(nu_dislikes_string_value);
						
				//analys likes and dislikes with Quartile1 values
						if (nu_likes > quartile_1_of_likes  && quartile_1_of_dislikes > nu_dislikes) {
							Cell cell4 = sheet.getCell(8, i);
							commnet_string_value = cell4.getContents();
							nu_of_comment = Integer.parseInt(commnet_string_value);
				//analys comment with Quartile1 values			
							if(nu_of_comment >quartile_1_of_comments) {
								Cell cell5 = sheet.getCell(0, i);
								video_id = cell5.getContents(); 
								
								for(int k=0;k<videos_distinct.size();k++) {
									if(video_id.equals(videos_distinct.get(k).toString())){
										++videos_distinct_checker;
									}
								}
								if(videos_distinct_checker==0) {
									System.out.println("There are "+videos_distinct.size()+" trending videos");
									videos_distinct.add(video_id);
									System.out.println("this is a trending video_id : " + video_id);
								}
							}
						}		
					}
			} catch (Exception e) {
				System.out.println("exception :"+e);
			}
		}
		return null;
	}
	public static int quartile_1(int j) throws BiffException, IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w = Workbook.getWorkbook(inputWorkbook); // Get the first sheet
		Sheet sheet = w.getSheet(0);
		
		String number_of_views_string_value ;
		int number_of_views,quartile_1;
		int array[]=new int[sheet.getRows()];
		for(int i = 1; i < sheet.getRows(); i++) {
			Cell cell1 = sheet.getCell(j,i);
			CellType type = cell1.getType(); 
						
			number_of_views_string_value = cell1.getContents();
			number_of_views = Integer.parseInt(number_of_views_string_value);
			array[i-1]=number_of_views;
		}
		Arrays.sort(array);
		quartile_1=array[sheet.getRows()/4];
		
		//System.out.println("quartile_1 : "+quartile_1);
		return quartile_1;
	}
	public static void main(String[] args) throws IOException, BiffException {
		ReadExcel test = new ReadExcel();
		test.setInputFile("C:\\eclips for java SE\\GBvideos.XLS");
		
		int quartile_1_of_view=quartile_1(5);
		int quartile_1_of_likes=quartile_1(6);
		int quartile_1_of_dislikes=quartile_1(7);
		int quartile_1_of_comments=quartile_1(8);
		
		test.trend_videos(quartile_1_of_view,quartile_1_of_likes,quartile_1_of_dislikes,quartile_1_of_comments);
		
		//test.same_video_id();
		// test.read();
	}
}