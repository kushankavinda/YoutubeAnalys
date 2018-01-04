package Reader;

import java.io.File;
import java.io.IOException;
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

	public void read() throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook); // Get the first sheet
			Sheet sheet = w.getSheet(0); // loop over first 10 column and lines
			//System.out.println("sheet : "+sheet);
		/*	for (int j = 0; j < sheet.getColumns(); j++) {
				//System.out.print(j);
				for (int i = 0; i < sheet.getRows(); i++) {
					//System.out.print(i);
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
					if (type == CellType.LABEL) {
					//	System.out.println("I got a label " + cell.getContents());
					}
					if (type == CellType.NUMBER) {
						//System.out.println("I got a number " + cell.getContents());
					}
				}
			}*/
			String value;
			int x,y=0;
			for(int i=0;i<7995;i++) {
				Cell cell = sheet.getCell(5,i);
				CellType type = cell.getType();

				if (type == CellType.NUMBER) {
					value=cell.getContents();
					x=Integer.parseInt(value);
					
					if(x>y && x < 100) {
						//System.out.println("I got a number " + cell.getContents());
						Cell cell1 = sheet.getCell(6,i);
						CellType type1 = cell1.getType();						
					}
					y=x;
				}
			}
			System.out.println(y);
		} catch (BiffException e) {
			System.out.println("exception came"+e);
			e.printStackTrace();
		}
	}



	public static void main(String[] args) throws IOException {
	ReadExcel test= new ReadExcel();
	test.setInputFile("C:\\eclips for java SE\\GBvideos.xls");
	test.read();
	}
}