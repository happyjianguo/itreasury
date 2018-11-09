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
	
    //���ݵĶ��뷽ʽ
	public static final short CENTER=HSSFCellStyle.ALIGN_CENTER;
	public static final short LEFT=HSSFCellStyle.ALIGN_LEFT;
	public static final short RIGHT=HSSFCellStyle.ALIGN_RIGHT;
	
	private String [] head;//������������	
	private short [] align;//ÿ�ж��뷽ʽ 
	private Object[] values; //���ݼ���
	private String[] notes; //ע����Ϣ

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
	 * ����һ��Excel�ļ�
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	public void createExcel(HttpServletResponse response,String fileName) throws IOException {
		HSSFWorkbook workbook = this.writeExcel();
		OutputStream os = response.getOutputStream();//ȡ�������
        response.reset();//��������
        String att="attachment; filename="+this.gbkToIso8859(fileName)+".xls";
        response.setHeader("Content-disposition",att );//�趨����ļ�ͷ
        response.setContentType("application/msexcel");//�����������
        workbook.write(os);//д���ڴ��е�excel
        os.close();
	}
	/**
	 * �����Ϣ��EXCEL
	 * @return  HSSFWorkbook
	 * @throws IOException
	 */
	private HSSFWorkbook writeExcel() throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet Sheet = workbook.createSheet(); //���һ��sheet
		int startRow = 0;
		if(head !=null && head.length>0){		
			for(int i=0;i<this.head.length ;i++)
			{
				 HSSFRow row0 = Sheet.createRow(startRow);//ȡ�õ�һ�У���ǩ�Ǵ�0��ʼ�ģ�
				 HSSFCell cell  = row0.createCell((short)i);
				 cell.setCellStyle(this.getCellStyle(0,workbook));
			     cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			     HSSFRichTextString htr = new HSSFRichTextString(this.head[i]);
			     cell.setCellValue(htr);	
			}
		    startRow = 1;
		}
		//�������ע����Ϣ�����ڵڶ�����ʾ
		if(notes !=null && notes.length > 0 ){
			for(int i=0;i<this.notes.length;i++){
			   HSSFRow row = Sheet.createRow(startRow+i);  //���û��ͷ��Ϣ��ӵ�һ�п�ʼ����ǩΪ0��������ӵڶ��п�ʼ����ǩΪ1��
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
			HSSFRow row = Sheet.createRow(startRow+i);//���û��ע����Ϣ����ȡ�õڶ��У���ǩ�Ǵ�0��ʼ�ģ�������ȡע����Ϣ���ȣ�1��
			Object [] rowValue=(Object[])values[i];
			for(int j=0;j<rowValue.length ;j++)
			{
				HSSFCell cell  = row.createCell((short)j);
				 cell.setCellStyle(this.getCellStyle(j,workbook));
			     //����ӷ�������
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
     * �ж�ÿһ�еĶ��뷽ʽ��Ĭ��Ϊ���У�
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
	 * ���õ���GBK������ַ�ת����ISO8859-1������ַ�
	 * 
	 * @param s Ŀ���ַ���.
	 * @return ����ʾ����.
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
