package com.iss.itreasury.clientmanage.client.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;
import com.iss.itreasury.clientmanage.client.dataentity.ExcelCellInfo;
import com.iss.itreasury.util.DataFormat;

public class ExcelAnalysis {

	//poi 解析报表方法  2003 
	
	
	/**
	 * 获取当前行最大列数
	 * @param sheet
	 * @param row
	 * @return
	 * @throws Exception
	 */


	
	public long getLastCell(HSSFSheet sheet,HSSFRow row) throws Exception
	{
		long end = 0;
		try{
			if(row != null)
			{
				int merged = sheet.getNumMergedRegions();
				for(int x = row.getLastCellNum()-1;x>=0;x--)
				{
					HSSFCell cell = row.getCell((short)x);
					
					if(cell==null)
						continue;
					if(cell.getCellType()==HSSFCell.CELL_TYPE_BLANK)
					{
						int rowNumber = row.getRowNum();
						int cellNumber = cell.getCellNum();
						boolean flag = false;
						for(int i=0;i<merged;i++)
						{	
							Region region=sheet.getMergedRegionAt(i);
							if(rowNumber>=region.getRowFrom()&&rowNumber<=region.getRowTo())
							{
								if(cellNumber>=region.getColumnFrom()&&cellNumber<=region.getColumnTo())
								{
									flag = true;
									break;
								}
								
							}
							
						}
						if(flag)
						{
							end = x;
							break;
						}
					}else
					{
						end = x;
						break;
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return end;
	}
	
	/**
	 * 判断当前行是否为空
	 * @param row
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	public boolean checkRowIsNull(HSSFRow row,HSSFSheet sheet)throws Exception
	{
		boolean isNull = true;
		int length = 0;

		int merged = sheet.getNumMergedRegions();
		int rowNumber = -1;
		try{
			if(row == null)
			{
				return isNull;
			}
			
			length = row.getLastCellNum();

			
			for(int j = 0;j<length;j++)
			{
				HSSFCell cell = row.getCell((short)j);
				if(cell!=null)
				{
					if(cell.getCellType()!=HSSFCell.CELL_TYPE_BLANK)
					{
						if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING)
						{
							if(!cell.getRichStringCellValue().toString().trim().equals(""))
							{
							
								isNull = false;
								break;
							}
						}else
						{
							isNull = false;
							break;
						}
					}
				}
				
			}
			//判断该行中是否含有合并单元格
			rowNumber = row.getRowNum();
			
			for(int x=0;x<merged;x++)
			{
				Region region=sheet.getMergedRegionAt(x);
				if(rowNumber>=region.getRowFrom()&&rowNumber<=region.getRowTo())
				{
					
						isNull = false;
						break;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		return isNull;
	}
	
	/**
	 * 得到合并单元格位置信息
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	
	public List sysRange(HSSFSheet sheet) throws Exception
	{
		ExcelCellInfo excelCellInfo =null;
		List list = new ArrayList();
		int count = sheet.getNumMergedRegions();
		//String cellValue = "";
		int colDistance = -1;
		int rowDistance = -1;
	//	HSSFCell cell = null;
		try{
			if(count>0)
			{
				for(int i=0;i<count;i++)
				{
					Region region=sheet.getMergedRegionAt(i);
					excelCellInfo = new ExcelCellInfo();
					excelCellInfo.setColumnFrom(region.getColumnFrom());
					excelCellInfo.setColumnTo(region.getColumnTo());
					excelCellInfo.setRowFrom(region.getRowFrom());
					excelCellInfo.setRowTo(region.getRowTo());
					
					colDistance = excelCellInfo.getColumnTo()-excelCellInfo.getColumnFrom()+1;
					excelCellInfo.setColumnDistance(colDistance);
					
					rowDistance = excelCellInfo.getRowTo()-excelCellInfo.getRowFrom()+1;
					excelCellInfo.setRowDistance(rowDistance);
				
					
					
					if(excelCellInfo.getRowFrom()==excelCellInfo.getRowTo())
					{
						excelCellInfo.setIsDirect(1);  //横向 1行
					}
					else if(excelCellInfo.getColumnFrom()==excelCellInfo.getColumnTo())
					{
						excelCellInfo.setIsDirect(2);  //纵向 1列
					}
					else
					{
						excelCellInfo.setIsDirect(3);  //全部
					}
					
					//cellValue = sheet.getRow(excelCellInfo.getRowFrom()).getCell(excelCellInfo.getColumnFrom()).getRichStringCellValue().toString();
		/*			cell = sheet.getRow(excelCellInfo.getRowFrom()).getCell(excelCellInfo.getColumnFrom());
					if(cell!=null)
					{
						//在Excel中的类型是文本、常规
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
						{
							cellValue=cell.getStringCellValue();
						}
						// 在Excel中的类型是数值
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{
							double d = cell.getNumericCellValue();
							cellValue=DataFormat.format(d,2);
		
						}
						//在Excel中的类型是公式
						  	else if( cell.getCellType() ==HSSFCell.CELL_TYPE_FORMULA)
						 	{						    
								double d = cell.getNumericCellValue();
								cellValue=DataFormat.format(d,0);                  	
					 	}  
					}
					excelCellInfo.setValue(cellValue);*/
					list.add(excelCellInfo);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return list.size()>0?list:null;
	}
	
	/**
	 * 判断该单元格是否在合并单元格中，不包含每个合并单元格左上角含有值的那个单元格
	 * @param sheet
	 * @param row
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public boolean isInCombineCell(HSSFSheet sheet,HSSFRow row,HSSFCell cell)throws Exception
	{
		boolean isInCombineCell = false;
		int merged = -1;
		int cellNumber = -1;
		int rowNumber = -1;
		try{
			if(cell==null)
			{
				isInCombineCell = false;
			}
			else
			{
				merged = sheet.getNumMergedRegions();
				rowNumber = row.getRowNum();
				cellNumber = cell.getCellNum();
				
				for(int i=0;i<merged;i++)
				{
					Region region = sheet.getMergedRegionAt(i);
					if(rowNumber>=region.getRowFrom()&&rowNumber<=region.getRowTo())
					{
						if(cellNumber>=region.getColumnFrom()&&cellNumber<=region.getColumnTo())
						{
							isInCombineCell = true;
							if(rowNumber == region.getRowFrom()&&cellNumber == region.getColumnFrom())
							{
								isInCombineCell = false;
							}
							break;
						}
					}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return isInCombineCell;
	}
	
	/**
	 * 从单元格中取出单元格中的值
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public  String getCellValue(HSSFCell cell) throws Exception
	{
		String cellValue = "";
		if(cell!=null)
		{
			if(cell.getCellNum()==0)
			{
				//在Excel中的类型是文本、常规
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
				{
					cellValue=cell.getRichStringCellValue().toString();
				
				}
				// 在Excel中的类型是数值
				else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					double d = cell.getNumericCellValue();
					cellValue=DataFormat.format(d,0);
	
				}
				//在Excel中的类型是公式
				else if( cell.getCellType() ==HSSFCell.CELL_TYPE_FORMULA)
				{						    
					double d = cell.getNumericCellValue();
					cellValue=DataFormat.format(d,0);                  	
			 	}  
			}
			else
			{
				//在Excel中的类型是文本、常规
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
				{
					cellValue=cell.toString();
				
				}
				// 在Excel中的类型是数值
				else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					double d = cell.getNumericCellValue();
					cellValue=DataFormat.format(d,2);
	
				}
				//在Excel中的类型是公式
				else if( cell.getCellType() ==HSSFCell.CELL_TYPE_FORMULA)
				{						    
					double d = cell.getNumericCellValue();
					cellValue=DataFormat.format(d,2);                  	
			 	}  
			}
		}
		return cellValue;
	}


}
