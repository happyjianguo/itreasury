/**
 * 
 */
package com.iss.itreasury.settlement.util;

import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

//import jxl.*;
//import com.iss.itreasury.bs.spi.Logger;
import com.iss.itreasury.util.Database;

/**
 * @author qijiang
 *
 */
public class BankDataUtil {
//
//	private static Logger logger =
//		Logger.getLogger(BankDataUtil.class);
//	
//	public static String bankDataFileName = "bankData.xls";	
//	public static File bankDataFile = new File(bankDataFileName);
//	
//	public static String BANKDATA_TABLE_NAME = "Sett_BOCBranchCode";
//	
//	private static long lastModifiedTime = -1;
//	
//	private StringBuffer sbBranch = null;  //存储所有分行数据 
//	private StringBuffer sbSupBranch = null; //存储上级行数据
//
//	/**
//	 * 
//	 */
//	public BankDataUtil() {
//		super();
//		sbBranch = new StringBuffer();
//		sbSupBranch = new StringBuffer();
//		// TODO Auto-generated constructor stub
//	}
//	
//	/**
//	 * 判断保存的数据是否发生过变化。
//	 * 异常：
//	 * 如果文件不存在，返回false
//	 * @return boolean
//	 */
//	public static boolean isBankDataModified()
//	{
//		boolean result = false;
//
//		if (bankDataFile.exists())
//		{
//			if (lastModifiedTime != bankDataFile.lastModified())
//				result = true;
//		}
//		else
//		{
//			//判文件的上次修改时间有没有有效值，有则是运行过程中文件被删除，返回true
//			if (lastModifiedTime != -1)
//				result = true;
//		}
//
//		return result;
//	}
//	
//	
//	/**
//	 * 将银行提供的Excel文件中的分行、支行数据导入到数据库中
//	 * 异常：
//	 * 如果文件不存在，返回false
//	 * @return
//	 */
//	/*
//	public void importExcelToDatabase() throws Exception {
//		
//		synchronized (bankDataFile)
//		{
//			if (bankDataFile.exists())
//			{
//				InputStream is = null;	
//				Workbook rwb = null;
//				Connection conn = null;				
//				Statement stmt = null;
//				
//				try
//				{
//					is = new FileInputStream(bankDataFile);
//					rwb = Workbook.getWorkbook(is);
//					
//					conn = Database.getConnection();
//					stmt = conn.createStatement();
//					
//					stmt.addBatch("delete from " + BANKDATA_TABLE_NAME);
//                    //获取第一张Sheet表
//					Sheet rs = rwb.getSheet(0);
//					String temp = "";					
//					for(int i=2;i<rs.getRows();i++) {
//						String sqlvalue = "";
//						for(int j=0;j<rs.getColumns();j++) {
//							Cell cell = rs.getCell(j,i);
//							if(cell.getType() == CellType.LABEL) {
//								LabelCell label = (LabelCell)cell;
//								temp = label.getString();								
//							}
//							sqlvalue += temp+"','";
//						}
//						sqlvalue = sqlvalue.substring(0,sqlvalue.length()-2);
//						stmt.addBatch("insert into "+BANKDATA_TABLE_NAME+" values('"+sqlvalue+")");
//						System.out.println("insert into "+BANKDATA_TABLE_NAME+" values('"+sqlvalue+")");
//					}
//					int[] result = stmt.executeBatch();
//					stmt.clearBatch();
//					System.out.println(result);
//				}
//				catch(Exception e) {
//					logger.error("读取银行数据文件出错",e);
//				}
//				finally
//				{
//					stmt.close();
//					conn.close();
//					rwb.close();
//					is.close();				
//					
//					lastModifiedTime = bankDataFile.lastModified();
//				}
//			}
//			else {
//				throw new Exception("读取银行数据文件出错，银行数据Excel文件不存在");
//			}
//		}
//	}*/
//	
//	
//	/**
//	 * 根据用户提供的地名，从数据库中读取满足条件的分行数据，并将数据保存在StringBuffer
//	 * 变量sbBranch和sbSupBranch中。StringBuffer中保存的字符串格式分别为： 
//	 * 例如：sbSupBranch: supBranchGroup[1]=new Option("中国银行北京朝阳区支行","1")，
//	 *      sbBranch: branchGroup[1][0]=new Option("中国银行北京朝阳区西坝河东里支行","40017-8026")，
//	 * 注意 supBranchGroup的option的value必须与branchGroup数组的一维下标对应。
//	 *     sbBranch的option的value为该支行的联行号和机构号，格式为:联行号+"-"+机构号
//	 * @param sState 用户输入的地名
//	 * 异常：
//	 * @return
//	 */
//	public void setBranchData(String sState) throws Exception {		
//		
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			int count = 0;
//			conn = Database.getConnection();
//			
//			//查询满足条件的上级行的记录数
//			int supBranchCount = 0; 
//			ps = conn.prepareStatement("select count(distinct SUPBANKEXCHAGECODE) from "+BANKDATA_TABLE_NAME
//					+" where SUPBRANCHNAME like '中国银行"+sState+"%' or SUPBRANCHNAME like '%总行'");
//			rs = ps.executeQuery();
//			if(rs.next()) {
//				supBranchCount = rs.getInt(1);
//			}
//			//生成对上级行选择下拉框的javascript定义部分
//			sbSupBranch.append("var supBranchSelect;"+"\n");
//			sbSupBranch.append("var supBranchGroup = new Array("+supBranchCount+");"+"\n");
//			
//			//生成对分行选择下拉框的javascript定义部分
//			sbBranch.append("var branchSelect;"+"\n");
//			sbBranch.append("var branchGroup=new Array("+supBranchCount+");"+"\n");
//			sbBranch.append("for (i = 0; i <= "+supBranchCount+"; i++)"+"\n");
//			sbBranch.append("{"+"\n");
//			sbBranch.append("branchGroup[i]=new Array();"+"\n");
//			sbBranch.append("}"+"\n");
//			
//			//查询结果集，并对StringBuffer赋值
//			String sql = "select * from "+BANKDATA_TABLE_NAME+" where SUPBRANCHNAME like '中国银行"+sState
//			+"%' or SUPBRANCHNAME like '%总行' order by SUPBANKEXCHAGECODE";
//			ps = conn.prepareStatement(sql);
//			rs = ps.executeQuery();
//			String temp = "";			
//			int m=1;
//			int n=1;
//			int k=0;
//			while(rs.next()) {
//				if(!temp.equals(rs.getString(5).trim())) {
//					sbSupBranch.append("supBranchGroup["+n+"] = new Option(\""
//							+rs.getString(4)+"\","+"\""+n+"\");"+"\n");
//					
//					sbBranch.append("branchGroup["+n+"][0] = new Option(\""
//							+rs.getString(4)+"\","+"\""
//							+rs.getString(5)+"-"+rs.getString(6)+"\");"+"\n");
//					n++;
//					m = 1;
//				}
//				k = n-1;				
//				
//				sbBranch.append("branchGroup["+k+"]["+m+"] = new Option(\""
//						+rs.getString(1)+"\","+"\""
//						+rs.getString(2)+"-"+rs.getString(3)+"\");"+"\n");
//				
//				temp = rs.getString(5).trim();  //保存这次取到的银行联行号，与下次取到的值作比较
//				
//				
//				m++;
//			}
//		} 
//		catch(SQLException e) {
//			logger.error("从数据库读取分行数据发生错误",e);
//		}
//		finally {
//			rs.close();
//			ps.close();
//			conn.close();
//		}		
//		
//	}
//	
//	
//	/**
//	 * 根据用户提供的地名，生成的javascript下拉框所需要的分行option数据
//	 * 异常：
//	 * @return String
//	 */
//	public String getBranchData() throws Exception {
//		return sbBranch.toString();
//	}
//	
//	/**
//	 * 根据用户提供的地名，生成的javascript下拉框所需要的上级行option数据
//	 * 异常：
//	 * @return String
//	 */
//	public String getSupBranchData() throws Exception {
//		return sbSupBranch.toString();
//	}
//	
//	public static void main(String args[]) {
//		BankDataUtil bd = new BankDataUtil();
//		//try {
//			//bd.importExcelToDatabase();
//			//bd.setBranchData("北京");
//			//System.out.println(bd.getSupBranchData());
//			//System.out.println(bd.getBranchData());
//			
//		//} catch(Exception e) {
//			
//		//}
//		
//	}

}
