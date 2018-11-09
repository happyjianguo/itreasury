package com.iss.sysframe.excel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

public class ExcelUtil
{	
	static short borderBottom = 0;//下边框
	
	static short borderLeft = 0;//左边框
	
	static short borderRight = 0;//右边框
	
	static short borderTop = 0;//上边框
	
	public static long sheetMaxNum = 65530; //单个sheet显示记录数
	public static boolean isHaveTitle = false; //是否有标题
	public static boolean isTitle = false; //是否是标题
	public static boolean isHaveTotal = false;  //是否有合计信息
	public static Collection cTitle = null;  //标题
	public static String sTotal = "";        //合计信息
	public static float rowWidth[] = null;   //列宽
	
	public ExcelUtil()
	{
		
	}

	/**
	 * 配置Excel的极少部分基本功能
	 * 边框类型 BORDER_NONE 0, BORDER_THIN 1, BORDER_MEDIUM 2, BORDER_DASHED 3, BORDER_DOTTED 4, BORDER_THICK 5, BORDER_DOUBLE 6, BORDER_HAIR 7, BORDER_MEDIUM_DASHED 8 BORDER_DASH_DOT 9, BORDER_MEDIUM_DASH_DOT 10, BORDER_DASH_DOT_DOT 11, BORDER_MEDIUM_DASH_DOT_DOT 12, BORDER_SLANTED_DASH_DOT 13
	 * @param isBorderBottom 下边框
	 * @param isBorderLeft 左边框
	 * @param isBorderRight 右边框
	 * @param isBorderTop 上边框
	 */
	public static void excelConfig(long borderBottom,long borderLeft,long borderRight,long borderTop)
	{
		ExcelUtil.borderBottom = (short) borderBottom;
		ExcelUtil.borderLeft = (short) borderLeft;
		ExcelUtil.borderRight = (short) borderRight;
		ExcelUtil.borderTop = (short) borderTop;
	}
	
	/**
	 * 用第三方包生成excel文件
	 * 生成单sheet的excel文件
	 *
	 */
	public static void createExcel(String fliePath,String fileName,String sheetName,Collection fileList)
	{
		FileOutputStream fileOut = null;//文件输出流
		HSSFWorkbook wb;//excel文件对象
		HSSFSheet sheet;//sheet对象
		HSSFRow row;//行对象
		try 
		{  
			wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
			long sheetNum = 0;
			sheet = wb.createSheet(sheetName+sheetNum);//建立新的sheet对象

			for(int i = 0 ; i < fileList.size() ; i++)
			{
				//数据量大分为多个sheet
				if((i%ExcelUtil.sheetMaxNum)==0)
				{
					sheetNum ++;
					sheet = wb.createSheet(sheetName+sheetNum);//建立新的sheet对象
					//是否有标题
					if(ExcelUtil.isHaveTitle)
					{
						//Create a row and put some cells in it. Rows are 0 based.
						row = sheet.createRow((short)i);//建立新行
						Collection titleList = ExcelUtil.cTitle;
						for(int rows = 0; rows < titleList.size(); rows++){
							sheet.setColumnWidth(rows, (int) ExcelUtil.rowWidth[rows]*50);//设置列宽
						}
						ExcelUtil.isTitle = true;
						writeFile(wb,row,titleList);
					}
				}
				
				/**文件内容部分**/
				ExcelUtil.isTitle = false;
				//是否有标题
				if(ExcelUtil.isHaveTitle)
				{
					//Create a row and put some cells in it. Rows are 0 based.
					row = sheet.createRow((short)i+1);//建立新行
					Collection innerFileList = ExcelUtil.parseList(fileList,i);//得到第i个对象
					writeFile(wb,row,innerFileList);
				}
				else
				{
					row = sheet.createRow((short)i);//建立新行
					Collection innerFileList = ExcelUtil.parseList(fileList,i);//得到第i个对象
					writeFile(wb,row,innerFileList);
				}
				
				
				
			}
			
			//是否有合计信息
			if(ExcelUtil.isHaveTotal)
			{
				//起始行、起始列、结束行、结束列
				short sBeginRow = 0;
				short sEndCell = 0;
				
				if(ExcelUtil.isHaveTitle)
					sBeginRow = (short)(fileList.size() + 1);
				else
					sBeginRow = (short)(fileList.size());
				if(ExcelUtil.cTitle!=null && !ExcelUtil.cTitle.isEmpty())
					sEndCell = (short)ExcelUtil.cTitle.size();
				
				//写合计信息
				writeTotalInfo(wb,sheet,sBeginRow,sEndCell);
			}
			
			 //删除第一个sheet
			  HSSFSheet hs = wb.getSheetAt(0);
			  if(hs != null)
			  {
				  wb.removeSheetAt(0);
			  }
			
			
			File file = new File(fliePath);
			if(!file.isHidden())
			{
				if(!file.exists())
				{
					if(file.mkdirs())
					{
						//Write the output to a file
						fileOut = new FileOutputStream(fliePath + fileName);
						wb.write(fileOut);
						fileOut.close();
					}
				}
				else
				{
					//Write the output to a file
					fileOut = new FileOutputStream(fliePath + fileName);
					wb.write(fileOut);
					fileOut.close();
				}
			}
		} catch (FileNotFoundException e) 
		{
			System.out.println("文件未找到");
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置动态数据的样式
	 *
	 */
	private static void addSyteml(HSSFWorkbook workbook,HSSFSheet aSheet,int rowFrom,int rowTo,int columnFrom,int columnTo){
		HSSFCellStyle cloneStyle = workbook.createCellStyle();
		cloneStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cloneStyle.setBorderLeft((short)0);
		cloneStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cloneStyle.setBorderBottom((short)0);
		cloneStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        for(int i=rowFrom;i<=rowTo;i++){
	            HSSFRow row_temp = aSheet.getRow(i);
	            for(int j=columnFrom;j<=columnTo;j++){
	                HSSFCell cell_temp = row_temp.getCell((short)j);
	                if(cell_temp ==null ){
	                    cell_temp = row_temp.createCell((short)j);
	                }
	                cell_temp.setCellStyle(cloneStyle);
	            }
	        }
	}
	
	private static void writeTotalInfo(HSSFWorkbook wb,HSSFSheet sheet,short sBeginRow,short sEndCell)
	{
		//合并单元格
	    sheet.addMergedRegion(new Region(sBeginRow,(short)0,sBeginRow,(short)(sEndCell-1)));
	    setRegionStyle(sheet,new Region(sBeginRow,(short)0,sBeginRow,(short)(sEndCell-1)),getTotalStyle(wb));
	    HSSFRow rowT = sheet.createRow(sBeginRow);
	    HSSFCell cellT = rowT.createCell((short)0);
	    //cellT.setEncoding(HSSFCell.ENCODING_UTF_16);
	    cellT.setCellValue(ExcelUtil.sTotal);
	}
	
	private static HSSFCellStyle getTotalStyle(HSSFWorkbook wb)
	{
		  HSSFFont fontStyle = wb.createFont();
		  fontStyle.setFontName("黑体");
		  fontStyle.setFontHeightInPoints((short)10);
		  fontStyle.setColor(HSSFColor.BLACK.index);
		  fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		  
		  HSSFCellStyle totalStyle = wb.createCellStyle();
		  totalStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		  totalStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		  totalStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		  totalStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		  totalStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		  totalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		  totalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER );
		  totalStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		  totalStyle.setFont(fontStyle); 
		  totalStyle.setWrapText(true);
		  
		  return totalStyle;
	}
	
	private static void setRegionStyle(HSSFSheet sheet, Region region , HSSFCellStyle cs) {
	        int toprowNum = region.getRowFrom();
	        for (int i = region.getRowFrom(); i <= region.getRowTo(); i ++) {
	            HSSFRow row = sheet.getRow(i);
	            if(row == null)
	            	row = sheet.createRow(i);
	            for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
	                HSSFCell cell = row.getCell((short)j);
	                if(cell == null)
	                	cell = row.createCell((short)j);
	                
	                cell.setCellStyle(cs);
	            }
	        }
	 }
	
	private static void writeFile(HSSFWorkbook wb,HSSFRow row,Collection innerFileList )
	{
		HSSFCell cell;//单元格对象
		HSSFCellStyle cellStyle;//cell样式
		for(int j = 0 ; j < innerFileList.size() ; j++)
		{//Create a cell and put a value in it.
			if(((List)innerFileList).get(j) instanceof Long)//如果是Long
			{
				cell = row.createCell((short)j);
				cell.setCellValue(Long.parseLong(((List)innerFileList).get(j).toString()));//设置cell为Long型的值
				
				if(ExcelUtil.isTitle )
					cell.setCellStyle(ExcelUtil.getTitleCellStyle(wb));
				else
					cell.setCellStyle(ExcelUtil.getCellStyle(wb)); 
			}
			else if(((List)innerFileList).get(j) instanceof Double)//如果是Double
			{
				cell = row.createCell((short)j);
				cell.setCellValue(Double.parseDouble(((List)innerFileList).get(j).toString()));//设置cell浮点类型的值
				if(ExcelUtil.isTitle )
					cell.setCellStyle(ExcelUtil.getTitleCellStyle(wb));
				else
					cell.setCellStyle(ExcelUtil.getCellStyle(wb));  
			}
			else if(((List)innerFileList).get(j) instanceof String)//如果是字符串
			{
				cell = row.createCell((short)j);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
				cell.setCellValue(((List)innerFileList).get(j).toString());//设置cell字符串类型的值
				if(ExcelUtil.isTitle )
					cell.setCellStyle(ExcelUtil.getTitleCellStyle(wb));
				else
					cell.setCellStyle(ExcelUtil.getCellStyle(wb));  
			}
			else if(((List)innerFileList).get(j) instanceof Boolean)//如果是布尔对象
			{
				cell = row.createCell((short)j);
				cell.setCellValue(Boolean.getBoolean(((List)innerFileList).get(j).toString()));//设置cell布尔类型的值
				if(ExcelUtil.isTitle )
					cell.setCellStyle(ExcelUtil.getTitleCellStyle(wb));
				else
					cell.setCellStyle(ExcelUtil.getCellStyle(wb)); 
			}
			else if(((List)innerFileList).get(j) instanceof Date)//如果是时间(java.util.Date)对象
			{
				cellStyle = ExcelUtil.getCellStyle(wb);//建立新的cell样式
				//cellStyle.setDataFormat(HSSFDataFormat.getFormat("m/d/yy h:mm"));//设置cell样式为定制的日期格式 // POI所有
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));//设置cell样式为定制的日期格式
				//DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINESE);;
				//try 
				//{
					cell = row.createCell((short)j);
					cell.setCellValue((Date) ((List)innerFileList).get(j));
					cell.setCellStyle(cellStyle); //设置该cell日期的显示格式
				//} 
				//catch (ParseException e) 
				//{
				//	System.out.println("无法分析指定字符串的开始处");
				//	e.printStackTrace();
				//}
			}
		}
	}
	
	private static HSSFCellStyle getCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();//建立新的cell样式
		cellStyle.setBorderLeft(ExcelUtil.borderLeft);
		cellStyle.setBorderTop(ExcelUtil.borderTop);
		cellStyle.setBorderRight(ExcelUtil.borderRight);
		cellStyle.setBorderBottom(ExcelUtil.borderBottom);
		return cellStyle;
	}

	private static HSSFCellStyle getTitleCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();//建立新的cell样式
		
		HSSFFont fontStyle = wb.createFont();
		fontStyle.setFontName("黑体");
		fontStyle.setFontHeightInPoints((short)10);
		fontStyle.setColor(HSSFColor.BLACK.index);
		fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER );
		cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(fontStyle); 
	    cellStyle.setWrapText(true);
		
		return cellStyle;
	}
	
	/**
	 * 得到Collection:fileList的第indexOf的对象(从0开始)
	 * @param fileList
	 * @param indexOf
	 * @return
	 */
	private static Collection parseList(Collection fileList,int indexOf) 
	{
		return (List)((List)fileList).get(indexOf);
	}

	/**
	 * 用第三方包解析导入文件，返回信息集合
	 * 
	 * @param imporFilepath
	 *            InputStream格式的xls文件
	 * @param Sheet
	 *            要选取的工作表,为空串或小于0时,默认第一个,如果工作表有名字,必须传入名字
	 * @param FirstRow
	 *            从第几行开始,0为第一行,如果为-1则从第一行开始
	 * @param LastRow
	 *            到第几行结束,0为第一行,如果为-1则为最后一行
	 * @return Collection 为一个ArrayList,内部的每一行又为一个ArrayList
	 * @throws IException
	 */
	public static Collection parseExcel(InputStream xls) 
	{
		return parseExcel(xls, "", -1,-1) ;
	}
	
	/**
	 * 用第三方包解析导入文件，返回信息集合
	 * 
	 * @param imporFilepath
	 *            InputStream格式的xls文件
	 * @param Sheet
	 *            要选取的工作表,为空串或小于0时,默认第一个,如果工作表有名字,必须传入名字
	 * @param FirstRow
	 *            从第几行开始,0为第一行,如果为-1则从第一行开始
	 * @param LastRow
	 *            到第几行结束,0为第一行,如果为-1则为最后一行
	 * @return Collection 为一个ArrayList,内部的每一行又为一个ArrayList
	 * @throws IException
	 */
	public static Collection parseExcel(InputStream xls, long Sheet) 
	{
		return parseExcel(xls, new Long(Sheet).toString(), -1,-1) ;
	}
	/**
	 * 用第三方包解析导入文件，返回信息集合
	 * 
	 * @param imporFilepath
	 *            InputStream格式的xls文件
	 * @param Sheet
	 *            要选取的工作表,为空串或小于0时,默认第一个,如果工作表有名字,必须传入名字
	 * @param FirstRow
	 *            从第几行开始,0为第一行,如果为-1则从第一行开始
	 * @param LastRow
	 *            到第几行结束,0为第一行,如果为-1则为最后一行
	 * @return Collection 为一个ArrayList,内部的每一行又为一个ArrayList
	 * @throws IException
	 */
	public static Collection parseExcel(InputStream xls, String Sheet) 
	{
		return parseExcel(xls, Sheet, -1,-1) ;
	}
	
	/**
	 * 用第三方包解析导入文件，返回信息集合
	 * 
	 * @param imporFilepath
	 *            InputStream格式的xls文件
	 * @param Sheet
	 *            要选取的工作表,为空串或小于0时,默认第一个,如果工作表有名字,必须传入名字
	 * @param FirstRow
	 *            从第几行开始,0为第一行,如果为-1则从第一行开始
	 * @param LastRow
	 *            到第几行结束,0为第一行,如果为-1则为最后一行
	 * @return Collection 为一个ArrayList,内部的每一行又为一个ArrayList
	 * @throws IException
	 */
	public static Collection parseExcel(InputStream xls, long Sheet, int FirstRow,
			int LastRow) 
	{
		
		return parseExcel(xls, new Long(Sheet).toString(), FirstRow,LastRow) ;
	}
	
	/**
	 * 用第三方包解析导入文件，返回信息集合
	 * 
	 * @param imporFilepath
	 *            InputStream格式的xls文件
	 * @param Sheet
	 *            要选取的工作表,为空串或小于0时,默认第一个,如果工作表有名字,必须传入名字
	 * @param FirstRow
	 *            从第几行开始,0为第一行,如果为-1则从第一行开始
	 * @param LastRow
	 *            到第几行结束,0为第一行,如果为-1则为最后一行
	 * @return Collection 为一个ArrayList,内部的每一行又为一个ArrayList
	 * @throws IException
	 */
	public static Collection parseExcel(InputStream xls, String Sheet, int FirstRow, int LastRow) 
	{
		ArrayList RateList = new ArrayList();
		ArrayList _tamp = new ArrayList();
		int intSheet = -1;

		HSSFWorkbook wb;
		HSSFSheet sheet = null;// 定义工作表
		HSSFRow row = null;// 定义行
		HSSFCell cell = null;// 定义单元格
		/* 控制格式的变量 */
		int headNum = 0; // 控制从第headNum行开始读取
		int footNum = 0; // 控制到第footNum行结束读取
		try
		{
			wb = new HSSFWorkbook(xls);// 得到当前Excel的句柄
			try
			{
				intSheet = new Integer(Sheet).intValue();
				if (intSheet > -1)
				{
					sheet = wb.getSheetAt(intSheet);// 选取第N个工作表
				} else
				{
					sheet = wb.getSheetAt(0);// 选取第1个工作表
				}
			} catch (NumberFormatException e)
			{
				if (Sheet.equals(""))
				{
					sheet = wb.getSheetAt(0);// 选取第1个工作表
				} else
				{
					sheet = wb.getSheet(Sheet);// 选取名字为${Sheet}的工作表
				}
			} catch (IndexOutOfBoundsException e)
			{
				System.out.println("传入的工作表sheet不存在");
				e.printStackTrace();
			}
			try
			{
				if (sheet.getLastRowNum() - sheet.getFirstRowNum() == 0)
				{// 判断是否导入的是一张空表
					System.out.println("导入的是一张空表");
				} else
				{
					System.out.println((sheet.getLastRowNum()
							- sheet.getFirstRowNum() + 1)
							- headNum);
					if (LastRow < 0)// 如果传入的是-1,则用最值
					{
						footNum = sheet.getLastRowNum() + 1;
					} else if (LastRow >= 0)// //如果传入的值大于-1,则用传入值
					{
						footNum = LastRow;
					}
					if (FirstRow < 0)// 如果传入的是-1,则用最值
					{
						headNum = sheet.getFirstRowNum();
					} else if (FirstRow >= 0)// //如果传入的值大于-1,则用传入值
					{
						headNum = FirstRow;
					}

					for (int i = headNum; i < footNum; i++)
					{// 获取表的行信息
						if (sheet.getRow(i) == null)
							i++;
						row = sheet.getRow(i);
						_tamp = new ArrayList();
						for (short j = row.getFirstCellNum(); j < row
								.getLastCellNum(); j++)
						{// 获取表的列信息
							cell = row.getCell((short) j);
							if (cell == null
									|| cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
							{// 为空则改为空字符串
								_tamp.add(" ");
							} else if (cell.getCellType() != HSSFCell.CELL_TYPE_BLANK
									|| cell != null)
							{// 如果不为空
								if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
								{// 为字符串则加入ArrayList
									_tamp.add(cell.getStringCellValue());
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
								{// 如果为数字,设为Double
									_tamp.add(new Double(cell
											.getNumericCellValue()));
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
								{// 如果为boolean,设为Boolean
									_tamp.add(new Boolean(cell
											.getBooleanCellValue()));
								} else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
								{// 如果为公式,加入ArrayList
									_tamp.add(cell.getStringCellValue());
								}
							}// else if
						}// for
						RateList.add(_tamp);
					}// for
				}// else
			} catch (NullPointerException e1)
			{
				System.out.println("传入的工作表sheet名字不存在");
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("文件不存在");
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				xls.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println(RateList);
		return RateList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		List outerList = new ArrayList();
		List innerList = new ArrayList();
		innerList.add(new Long(1));
		innerList.add("jinni");
		innerList.add("中华人民共和国");
		innerList.add("2005-11-30");
		outerList.add(innerList);
		innerList = new ArrayList();
		innerList.add(new Long(2));
		innerList.add("daisy");
		innerList.add("中华人民共和国");
		innerList.add("2005-12-30");
		outerList.add(innerList);
		innerList = new ArrayList();
		innerList.add(new Long(3));
		innerList.add("bsss");
		innerList.add("中华人民共和国");
		innerList.add("2005-12-30");
		outerList.add(innerList);
		
		/*System.out.println("0%5 ===" + 0%5);
		System.out.println("1%5 ===" + 1%5);
		System.out.println("2%5 ===" + 2%5);
		System.out.println("3%5 ===" + 3%5);
		System.out.println("4%5 ===" + 4%5);
		System.out.println("5%5 ===" + 5%5);
		System.out.println("6%5 ===" + 6%5);*/
		List titleList = new ArrayList();
		titleList.add("aaa");
		titleList.add("bbb");
		titleList.add("ccc");
		titleList.add("ddd");
		ExcelUtil.cTitle=titleList;
		ExcelUtil.isHaveTitle = true;
		ExcelUtil.excelConfig(1, 1, 1, 1);
		ExcelUtil.createExcel("D:\\_temp\\","myword.xls","测试",outerList);
	}
}
