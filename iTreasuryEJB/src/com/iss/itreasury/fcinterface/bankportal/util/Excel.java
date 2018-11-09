/*
 * Created on 2004-10-12 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * @author mxzhou
 * Excel对象，与具体的一个Excel文件相对应，包含相应的Workbook、Sheet、Row、Cell对象
 * 封装了poi中的具体读取文件的过程，对外仅提供下叙接口：
 * 
 * ◎ Excel(String Name, File excelFile) 构造函数，提供本Excel对象的别名和对应的Excel文件
 * ◎ getWorkbook() 获取Excel文件中的Workbook，包含其中所有的对象
 * ◎ setName(String Name)、getName() 设置或获取Excel对象的别名
 * ◎ main(String[] args) 测试函数
 */
public class Excel
{
	/**
	 * 日志记录对象
	 */
	private static Logger logger = new Logger(Excel.class);
	
	/**
	 * Excel对象别名
	 */
	private String name = null;
	/**
	 * 本对象对应的Excel文件
	 */
	private File file = null;
	/**
	 * 本Excel对象中的Workbook对象，包含着具体的数据，
	 * 所有数据的类型均为Object，获取后根据需要作相应转换
	 */
	private Workbook workbook = null;
	/**
	 * 构造函数，需传入对象名称和对应的文件
	 * @param name
	 * @param file
	 */
	public Excel(String name, File file)
	{
		this.name = name;
		this.file = file;
	}
	/**
	 * 内部方法，作导入数据用
	 *
	 */
	public void importData()
	{
		logger.info("enter into Excel.importData()");
		try
		{	
			POIFSFileSystem fs = null;
			HSSFWorkbook wb = null;

			fs = new POIFSFileSystem(new FileInputStream(file));
			wb = new HSSFWorkbook(fs);
	
			ArrayList sheets = new ArrayList(0);
			Workbook workbookTemp = new Workbook();
			Sheet sheetTemp = null;
			Row rowTemp = null;
			Cell cellTemp = null;
	
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;


			//循环获得每个sheet
			for (int s = 0;; s++)
			{
				try
				{
					sheet = wb.getSheetAt(s);
				}
				catch ( IndexOutOfBoundsException e)
				{
					break;
				}
				if (sheet != null)
				{
					//当获得一个不为null的sheet时，创建一个临时sheet对象。
					sheetTemp = new Sheet();
					sheetTemp.setSheetName(wb.getSheetName(s));
					if (!(sheet.getLastRowNum() == 0 && sheet.getRow(0) == null))
					{
						sheetTemp.rows = new Row[sheet.getLastRowNum() + 1];
					}

					logger.debug("sheet[" + s + "]:");
					for (int r = 0; r <= sheet.getLastRowNum(); r++)
					{
						row = sheet.getRow(r);
						if (row != null)
						{
							sheetTemp.rows[r] = new Row();
							sheetTemp.rows[r].cells = new Cell[row.getLastCellNum()+1];

							logger.debug("    row[" + r + "]:");
							for (int c = 0; c <= row.getLastCellNum(); c++)
							{
								cell = row.getCell((short) c);
								logger.debug("        cell[" + c + "]:");
								if (cell != null)
								{
									sheetTemp.rows[r].cells[c] = new Cell();
									sheetTemp.rows[r].cells[c].cellValue = Excel
											.getCellValue(cell, cell
													.getCellType());

									logger.debug(" type:"
											+ cell.getCellType()
											+ " value:"
											+ Excel.getCellValue(cell, cell
													.getCellType()));
								}
								else
								{
									logger.debug(" null");
								}
							}
						}
					}

					sheets.add(sheetTemp);
				}
			}
			if (sheets.size() > 0)
			{
				workbookTemp.sheets = (Sheet[]) sheets.toArray(new Sheet[0]);
			}
			this.workbook = workbookTemp;

			logger.debug("Import Data Success!");
		}
		catch ( Exception e)
		{
			logger.error("Import data Failed! Cause by:" + e.getMessage(),e);
//			e.printStackTrace();
		}
	}
	
	public void exportData()
	{
		logger.info("enter into Excel.exportData()");
		if(workbook == null || workbook.getSheets() == null || workbook.getSheets().length <= 0)
		{
			logger.debug("workbook or sheet is null!");
		}
		
		Sheet excelSheet = null;
		Row excelRow = null;
		Cell excelCell = null;
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = null;
		HSSFRow row = null;
		
		//按照sheet的个数，循环创建HSSFSheet
		for(int s = 0; s < workbook.getSheets().length; s++)
		{
			excelSheet = workbook.getSheets()[s];
			if(excelSheet.getSheetName() != null)
			{
				sheet = wb.createSheet(excelSheet.getSheetName());
			}
			else
			{
				sheet = wb.createSheet();
			}
			//按照row的个数，循环创建HSSFSheet
			if(excelSheet != null && excelSheet.getRows() != null && excelSheet.getRows().length > 0)
			{
				for(int r = 0; r < excelSheet.getRows().length; r++)
				{
					excelRow = excelSheet.getRows()[r];
					row = sheet.createRow((short)r);
					//按照cell的个数，循环创建HSSFCell
					if(excelRow != null && excelRow.getCells() != null && excelRow.getCells().length > 0)
					{
						for(int c = 0; c < excelRow.getCells().length; c++)
						{
							createCell(wb, row, (short)c, excelRow.getCells()[c]);
						}
					}
				}
			}
		}
		
		//将结果输出到文件中
        FileOutputStream fileOut = null;
		try
		{
			fileOut = new FileOutputStream(file);
			wb.write(fileOut);
	        fileOut.close();
		}
		catch (Exception e)
		{
			logger.error("export data Failed! Cause by:" + e.getMessage(),e);
		}


	}
	
	/**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb        the workbook
     * @param row       the row to create the cell in
     * @param column    the column number to create the cell in
     * @param align     the alignment for the cell.
     */
    private void createCell(HSSFWorkbook wb, HSSFRow row, short column, Cell excelCell)
    {
        HSSFCell cell = row.createCell(column);
       // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        
        short backgroundColor = HSSFColor.WHITE.index;
        HSSFCellStyle cellStyle = null;
       
        if(excelCell != null)
        {
        	//按值类型设置Cell值
        	if(excelCell.getCellValue() != null)
        	{
        		//如果没有设置，则按String设置
        		if(excelCell.getCellType() == null || excelCell.getCellType().trim().length() <= 0)
        		{
        			if(excelCell.getCellValue() instanceof String)
        			{
        				cell.setCellValue((String)excelCell.getCellValue());
        			}
        			else
        			{
        				cell.setCellValue("no type");
        			}
        			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		}
        		//否则，按相应的类型设置
        		else if(excelCell.getCellType().equals("String"))
        		{
        			cell.setCellValue((String)excelCell.getCellValue());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		}
        		else if(excelCell.getCellType().equals("Double"))
        		{
        			cell.setCellValue(((Double)excelCell.getCellValue()).doubleValue());
        		}
        		else if(excelCell.getCellType().equals("Boolean"))
        		{
        			cell.setCellValue(((Boolean)excelCell.getCellValue()).booleanValue());
        		}
        		else if(excelCell.getCellType().equals("money"))
        		{
					cell.setCellValue(Double.valueOf((String)excelCell.getCellValue()).doubleValue());
					
					cellStyle = wb.createCellStyle();
					HSSFDataFormat format = wb.createDataFormat();
					cellStyle.setDataFormat(format.getFormat("#,##0.00"));
					//居右
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					cell.setCellStyle(cellStyle);
        		}
        		else if(excelCell.getCellType().equals("textNumber"))
        		{
        			cell.setCellValue((String)excelCell.getCellValue());
        			cellStyle = wb.createCellStyle();
        			
        			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					
        			HSSFDataFormat format = wb.createDataFormat();
    				cellStyle.setDataFormat(format.getFormat("@"));
    				cell.setCellStyle(cellStyle);
        		}
        		else if(excelCell.getCellType().equals("textDate"))
        		{
        			cell.setCellValue((String)excelCell.getCellValue());
        			cellStyle = wb.createCellStyle();
        			
        			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					
        			HSSFDataFormat format = wb.createDataFormat();
    				cellStyle.setDataFormat(format.getFormat("@"));
    				cell.setCellStyle(cellStyle);
        		}
        		
        		//默认的值类型为String
        		else
        		{
        			if(excelCell.getCellValue() instanceof String)
        			{
        				cell.setCellValue((String)excelCell.getCellValue());
        			}
        			else
        			{
        				cell.setCellValue("unknowed type");
        			}
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        		}
        	}
        	
        	//根据给定的颜色设置背景色
        	if(excelCell.getCellColor() != null && excelCell.getCellColor().trim().length() > 0)
        	{
        		if(excelCell.getCellColor().equals("green"))
        		{
        			backgroundColor = HSSFColor.GREEN.index;
        		}
        		else if(excelCell.getCellColor().equals("yellow"))
        		{
        			backgroundColor = HSSFColor.YELLOW.index;
        		}
        		else if(excelCell.getCellColor().equals("middle"))
        		{
        			backgroundColor = HSSFColor.ROSE.index;
        		}
        		else if(excelCell.getCellColor().equals("red"))
        		{
        			backgroundColor = HSSFColor.RED.index;
        		}
        		//缺省按白色处理
        		else
        		{
        			backgroundColor = HSSFColor.WHITE.index;
        		}
        		
        		cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(backgroundColor);
                //块填充
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                //点填充
//        	    cellStyle.setFillPattern(HSSFCellStyle.BIG_SPOTS);
                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//              cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM_DASHED);//虚线
                cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                

				cell.setCellValue("★");
				cell.setCellStyle(cellStyle);
        	}
        }
  
    }

	public class Workbook
	{
		private Sheet[] sheets = null;
		/**
		 * @return Returns the sheets.
		 */
		public Sheet[] getSheets()
		{
			return sheets;
		}
		/**
		 * @param sheets The sheets to set.
		 */
		public void setSheets(Sheet[] sheets)
		{
			this.sheets = sheets;
		}
	}

	public class Sheet
	{
		private String sheetName = null;
		private Row[] rows = null;
		/**
		 * @return Returns the rows.
		 */
		public Row[] getRows()
		{
			return rows;
		}
		/**
		 * @return Returns the sheetName.
		 */
		public String getSheetName()
		{
			return sheetName;
		}
		/**
		 * @param rows The rows to set.
		 */
		public void setRows(Row[] rows)
		{
			this.rows = rows;
		}
		/**
		 * @param sheetName The sheetName to set.
		 */
		public void setSheetName(String sheetName)
		{
			this.sheetName = sheetName;
		}
	}

	public class Row
	{
		private Cell[] cells = null;
		/**
		 * @return Returns the cells.
		 */
		public Cell[] getCells()
		{
			return cells;
		}
		/**
		 * @param cells The cells to set.
		 */
		public void setCells(Cell[] cells)
		{
			this.cells = cells;
		}

	}

	public class Cell
	{
		private Object cellValue = null;
		private String cellType = null;
		private String cellColor = null;
		/**
		 * @return Returns the cellValue.
		 */
		public Object getCellValue()
		{
			return cellValue;
		}
		/**
		 * @param cellValue The cellValue to set.
		 */
		public void setCellValue(Object cellValue)
		{
			this.cellValue = cellValue;
		}
		/**
		 * @return Returns the cellColor.
		 */
		public String getCellColor()
		{
			return cellColor;
		}
		/**
		 * @param cellColor The cellColor to set.
		 */
		public void setCellColor(String cellColor)
		{
			this.cellColor = cellColor;
		}
		/**
		 * @return Returns the cellType.
		 */
		public String getCellType()
		{
			return cellType;
		}
		/**
		 * @param cellType The cellType to set.
		 */
		public void setCellType(String cellType)
		{
			this.cellType = cellType;
		}
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the workbook.
	 */
	public Workbook getWorkbook()
	{
		if (workbook == null)
		{
			importData();
		}
		return workbook;
	}
	
	/**
	 * @param workbook The workbook to set.
	 */
	public void setWorkbook(Workbook workbook)
	{
		this.workbook = workbook;
	}

	private static Object getCellValue(HSSFCell cell, int cellType)
	{
		if (cell == null)
		{
			return null;
		}

		Object result = null;
		switch (cellType)
		{
			case HSSFCell.CELL_TYPE_BLANK :
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN :
				result = new Boolean(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR :
				result = new Byte(cell.getErrorCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA :
				result = new String(cell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_NUMERIC :
				result = new Double(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING :
				result = new String(cell.getStringCellValue());
				break;
		}

		return result;
	}

	public static Workbook main_testImport(String[] args) throws Exception
	{
		Excel excel = new Excel("test", new File("E:\\Projects\\BankService\\ChinaTobacco\\Release\\workspace\\中间数据\\1.xls"));
		Workbook wk = excel.getWorkbook();
		if(wk != null)
		{
			Sheet[] sheets = wk.getSheets();
			if(sheets != null && sheets.length > 0)
			{
				for(int i = 0; i < sheets.length; i++)
				{
					Row[] rows = sheets[i].getRows();
					if(rows != null && rows.length > 0)
					{
						for(int j = 0; j < rows.length; j++)
						{
							if(rows[j]!=null)
							{
								Cell[] cells = rows[j].getCells();
								if(cells != null && cells.length > 0)
								{
									for(int k = 0; k < cells.length; k++)
									{
										if(cells[k]!=null)
										{
											Object cellValue = cells[k].getCellValue();
											System.out.println("sheet["+i+"]row["+j+"]cell["+k+"]:"+cellValue);
										}
										else
										{
											System.out.println("sheet["+i+"]row["+j+"]cell["+k+"]:"+null);
										}
									}
								}
							}
							else
							{
								System.out.println("sheet["+i+"]row["+j+"]:"+null);
							}
						}
					}
				}
			}
		}
		return wk;
	}
	
	public static void main(String[] args)
	{
		Excel.Workbook wk = null;
		try
		{
			wk = Excel.main_testImport(null);
		}
		catch ( Exception e)
		{
			e.printStackTrace();
		}
		
//		Excel excel = new Excel("test", new File("e:\\2.xls"));
//		excel.setWorkbook(wk);
//		excel.exportData();
//		
		System.out.println("export test Succeed!");
	}

}
