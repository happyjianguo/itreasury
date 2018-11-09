package com.iss.itreasury.settlement.exporttrans.bizlogic;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel {
	
    //内容的对齐方式
	public static final short CENTER=HSSFCellStyle.ALIGN_CENTER;
	public static final short LEFT=HSSFCellStyle.ALIGN_LEFT;
	public static final short RIGHT=HSSFCellStyle.ALIGN_RIGHT;
	
	private String [] head;//标题文字数组	
	private short [] align;//每列对齐方式 
	private Object[] values; //数据集合
	private String[] notes; //注释信息

    public ExportExcel(String[] head,short[] align,String[] notes,Object[] values){
	   this.head = head;
	   this.align = align;
       this.values = values;
       this.notes = notes;
    }
   
    public ExportExcel(String[] head,String[] notes,Object[] values){
 	   this.head = head;
       this.notes = notes;
       this.values = values;
    }
 
    /**
	 * 创建一个Excel文件
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	public void createExcel(HttpServletResponse response,String fileName) throws IOException {
		HSSFWorkbook workbook = this.writeExcel();
		OutputStream os = response.getOutputStream();//取得输出流
        response.reset();//清空输出流
        String att="attachment; filename="+this.gbkToIso8859(fileName)+".xls";
        response.setHeader("Content-disposition",att );//设定输出文件头
        response.setContentType("application/msexcel");//定义输出类型
        workbook.write(os);//写出内存中的excel
        os.close();
	}
	/**
	 * 输出信息到EXCEL
	 * @return  HSSFWorkbook
	 * @throws IOException
	 */
	private HSSFWorkbook writeExcel() throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet Sheet = workbook.createSheet(); //获得一个sheet
		int startRow = 0;
		if(head !=null && head.length>0){		
			for(int i=0;i<this.head.length ;i++)
			{
				 HSSFRow row0 = Sheet.createRow(startRow);//取得第一行（标签是从0开始的）
				 HSSFCell cell  = row0.createCell((short)i);
				 cell.setCellStyle(this.getCellStyle(0,workbook));
			     cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			     HSSFRichTextString htr = new HSSFRichTextString(this.head[i]);
			     cell.setCellValue(htr);	
			}
		    startRow = 1;
		}
		//如果存在注释信息，则在第二行显示
		if(notes !=null && notes.length > 0 ){
			for(int i=0;i<this.notes.length;i++){
			   HSSFRow row = Sheet.createRow(startRow+i);  //如果没有头信息则从第一行开始（标签为0），否则从第二行开始（标签为1）
			   HSSFCell cell  = row.createCell((short)0);
			   cell.setCellType(this.LEFT);
			   cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			   HSSFRichTextString htr = new HSSFRichTextString(notes[i]);
			   cell.setCellValue(htr);
			}
			startRow = this.notes.length+1;
		}
		for(int i=0;i<this.values.length ;i++)
		{
			HSSFRow row = Sheet.createRow(startRow+i);//如果没有注释信息，则取得第二行（标签是从0开始的），否则取注释信息长度＋1行
			Object [] rowValue=(Object[])values[i];
			for(int j=0;j<rowValue.length ;j++)
			{
				HSSFCell cell  = row.createCell((short)j);
				 cell.setCellStyle(this.getCellStyle(j,workbook));
			     //待添加返回类型
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				 HSSFRichTextString htr = new HSSFRichTextString((String)rowValue[j]);
				 cell.setCellValue(htr);
			}
		}
        return workbook;
	}
	
	
	
	public short[] getAlign() {
		return align;
	}

	public void setAlign(short[] align) {
		this.align = align;
	}

	public String[] getHead() {
		return head;
	}

	public void setHead(String[] head) {
		this.head = head;
	}

    /**
     * 判断每一列的对齐方式（默认为居中）
     * @param index
     * @param workbook
     * @return
     */
	public HSSFCellStyle getCellStyle(int index,HSSFWorkbook workbook){
    	HSSFCellStyle cellStyle = workbook.createCellStyle();
    	cellStyle.setAlignment(this.CENTER);
    	
    	if(align!=null && this.align[index]!=0){
    		cellStyle.setAlignment(this.align[index]);
    	}
    	return cellStyle;
    }
	
	  
	/**
	 * 将得到的GBK编码的字符转换成ISO8859-1编码的字符
	 * 
	 * @param s 目标字符串.
	 * @return 可显示符串.
	 */

	public static String gbkToIso8859(String s) {
		try {
			String s2 = new String(s.getBytes("GBK"), "iso8859-1");
			return s2;
		} catch (Exception e) {
			return null;
		}
	}

}
