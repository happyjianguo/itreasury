/*
 * Created on 2004-11-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.conversion.dataentity.DestinationReceivePayInfo;
import com.iss.itreasury.settlement.conversion.db.DestinationConn;
import com.iss.itreasury.settlement.conversion.db.SourceConn;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConversionUtil {
	//private static String  path = ConversionUtil.class.getResource("").getPath();
	private static String configFile = "haier_wb_config.properties";
	
	//private  String strPayTransactionType;
	//private  String strReceiveTransactionType;
	/**
	 * @return Returns the strPayTransactionType.
	 */
	public static String getStrPayTransactionType() {
		String strPayTransactionType="";
		InputStream input = null;
		try {
			input = new FileInputStream(configFile);
			Properties p = new Properties();
			p.load(input);
			strPayTransactionType = (String)p.get("payTransactionType");
			if(strPayTransactionType==null){
				strPayTransactionType ="";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
				try {
					if(input!=null)
					   input.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		return strPayTransactionType;
	}
	/**
	 * @return Returns the strReceiveTransactionType.
	 */
	public static String getStrReceiveTransactionType() {
		String strReceiveTransactionType="";

		InputStream input = null;

		try {
			input = new FileInputStream(configFile);
			Properties p = new Properties();
			p.load(input);
			strReceiveTransactionType = (String)p.get("receiveTransactionType");

			if(strReceiveTransactionType==null){
				strReceiveTransactionType = "";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
				try {
					if(input!=null)
					   input.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		return strReceiveTransactionType;
	}


	/**
	 * 
	 */
	public ConversionUtil() {

	}

	public static final String getTransactCode(long transId){
		String rnt = "";
		/*
		 *  public static final long CHECK_RECEIVE = 110; //支票收款

        public static final long REMIT_RECEIVE = 111; //汇款收款
        
        public static final long CHECK_PAY = 116; //支票付款

        public static final long REMIT_PAY = 117; //汇款付款
		 */
		if(transId==SETTConstant.TransactionType.CHECK_RECEIVE ||
				transId==SETTConstant.TransactionType.CHECK_PAY
				){
			rnt = "6";
		}else if(transId==SETTConstant.TransactionType.REMIT_RECEIVE ||
				transId==SETTConstant.TransactionType.REMIT_PAY
				){
			rnt = "3";
		}
		return rnt;
	}
	
	/**
	 * 信用证―――1
	 * 托收――――2
	 * 电汇――――3
	 * 信汇――――4
	 * 票汇――――5
	 * 其他――――6
	 * @param code
	 * @return
	 */
	public static final String getTransactNameByCode(String code){
	
		String rnt  = "";
		if(code==null){
			
		}else if(code.equals("1")){
			rnt = "信用证";
		}else if(code.equals("2")){
			rnt = "托收";
		}else if(code.equals("3")){
			rnt = "电汇";
		}else if(code.equals("4")){
			rnt = "信汇";
		}else if(code.equals("5")){
			rnt = "票汇";
		}else if(code.equals("6")){
			rnt = "其他";
		}
			
		return rnt;
	}
	
	public static final String[] getUseCurrency(){
		String[] strSplit = null;
		InputStream input = null;
		String strUserCurrency ="";
		try {
			input = new FileInputStream(configFile);
			Properties p = new Properties();
			p.load(input);
			strUserCurrency = (String)p.get("userCurrency");
			//System.out.println(strUserCurrency);
			if(strUserCurrency!=null){
				strSplit = strUserCurrency.split(",");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
				try {
					if(input!=null)
					   input.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		return strSplit;
	}
	
	/**
	 * 
	 * @return
	 */
	public static final long[] getReceiveTransactionType(){
		String[] strSplit = null;
		long[] rnt = null;

		try {

			String strReceiveTransactionType = getStrReceiveTransactionType();
			if(strReceiveTransactionType!=null &&!strReceiveTransactionType.equals("")){
				strSplit = strReceiveTransactionType.split(",");
				rnt = new long[strSplit.length];
				for(int i=0 ;i <strSplit.length;i++){
					rnt[i] = Long.parseLong(strSplit[i].trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{

		}
		return rnt;
	}
	
	/**
	 * 
	 * @return
	 */
	public static final long[] getPayTransactionType(){
		String[] strSplit = null;
		long[] rnt = null;
		try {
			
			String strPayTransactionType = getStrPayTransactionType();
			if(strPayTransactionType!=null){
				strSplit = strPayTransactionType.split(",");
				rnt = new long[strSplit.length];
				for(int i=0 ;i <strSplit.length;i++){
					rnt[i] = Long.parseLong(strSplit[i].trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{

		}
		return rnt;
	}
	
	public static final String getLastDate(){
		String[] strSplit = null;
		FileInputStream input = null;
		String strLastDate ="";
		try {
			input = new FileInputStream(configFile);
			Properties p = new Properties();
			p.load(input);
			strLastDate = (String)p.getProperty("lastDate");
			if(strLastDate!=null){
				strLastDate = strLastDate.trim();
			}
			System.out.println(strLastDate);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
				try {
					if(input!=null)
					   input.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		return strLastDate;
	}
	

	
	public static final void setLastDate(String str){
		String[] strSplit = null;
		//System.out.println(path);
		File file = new File(configFile);
		FileInputStream input = null;
		FileOutputStream output = null;

		try {
			input = new FileInputStream(file);
			
			Properties p = new Properties();
			p.load(input);

			//System.out.println(p.getProperty("lastDate"));
			output = new FileOutputStream(file);
			if(str!=null){
				p.setProperty("lastDate",str);
				StringBuffer strHeader = new StringBuffer();
				strHeader.append("");
				strHeader.append("116=CHECK_PAY,");//支票付款
				strHeader.append("117=REMIT_PAY,");//汇款付款
				strHeader.append("110=CHECK_RECEIVE,");//支票收款
				strHeader.append("111=REMIT_RECEIVE");//汇款收款	
				p.store(output,strHeader.toString());
				output.flush();
				output.close();
			System.out.println(p.getProperty("lastDate"));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
				try {
					if(input!=null)
					   input.close();
					if(output!=null)
						output.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
	}
	
	
	
	/**
	 * 根据配置文件中为上一次转换日期在sett_transaccountdetail中
	 * 查询在此日期之后的最近的一天，如果返回为空则没有需要处理的数据。
	 * @return
	 */

	public static final Timestamp getCurrentDate(){
		SourceConn sc = new SourceConn();
		Connection conn = sc.getConn();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select a.dtexecute "+"\n");
		sqlBuffer.append(" from sett_transaccountdetail a,"+"\n");
		sqlBuffer.append(" sett_account b,"+"\n");
		sqlBuffer.append(" sett_subaccount c ,"+"\n");
		sqlBuffer.append("  client d "+"\n");
		sqlBuffer.append(" where  a.nstatusid>0 "+"\n");
		sqlBuffer.append(" and a.ntransaccountid=b.id "+"\n");
		sqlBuffer.append(" and a.nsubaccountid=c.id " +"\n");
		sqlBuffer.append(" and c.naccountid = b.id "+"\n");
		sqlBuffer.append(" and  b.nclientid = d.id "+"\n");
		//sqlBuffer.append(" and and a.ntransactiontypeid in ("+ConversionUtil.getStrPayTransactionType()+") \n");
		sqlBuffer.append(" and a.ncurrencyid >1"+"\n");
		sqlBuffer.append(" and a.dtexecute > to_date('"+ConversionUtil.getLastDate()
							           +"','yyyy-mm-dd')"+"\n");	
		sqlBuffer.append(" order by a.dtexecute");

		DestinationReceivePayInfo drpInfo = null;
		System.out.println(sqlBuffer.toString());
		Timestamp rnt = null;
		//AccountInfo  accountInfo =  null;
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			rs = pstat.executeQuery();
			if(rs.next()){
				rnt = rs.getTimestamp("dtexecute");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}
		
		return rnt;
	}
	
	public static String getREF(Timestamp ts){
		String rnt ="";
		  if(ts!=null){
			java.util.Calendar calendar = Calendar.getInstance () ;
			calendar.setTime ( ts ) ;
			int y = calendar.get ( Calendar.YEAR );
			int m = calendar.get ( Calendar.MONTH ) + 1;
			int d = calendar.get ( Calendar.DATE );
			String mStr = "";
			String dStr = "";
			if(m<10) mStr = "0"; 
			if(d<10) dStr = "0";
			rnt =  String.valueOf(y)
					+ mStr+String.valueOf(m) 
					+ dStr+String.valueOf(d) ;
		  }
		return rnt;
	}
	public static void main(String[] args) {
		//ConversionUtil.getLastDate();
		System.out.println("===========");
		
		//ConversionUtil.setLastDate("2004-12-13");
		System.out.println("===========");
		//Timestamp t = ConversionUtil.getCurrentDate();
		//if(t!=null){
			//System.out.println(t);
		//}
		//Timestamp dd1 = Timestamp.valueOf("2004-10-10 00:00:00");
		Timestamp dd = Timestamp.valueOf("2004-13-9 00:00:00");
		System.out.println(getREF(dd));
		//System.out.println(ConversionUtil.getStrPayTransactionType());
		//System.out.println(ConversionUtil.getStrReceiveTransactionType());
		//System.out.println(get(dd));
		System.out.println("===========");
	}
	
	
}
