/*
 * Created on 2004-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.dao;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.conversion.dataentity.DestinationBalanceInfo;
import com.iss.itreasury.settlement.conversion.dataentity.DestinationReceivePayInfo;
import com.iss.itreasury.settlement.conversion.db.DestinationConn;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConversionDebitCredit {

	/**
	 * 
	 */
	public ConversionDebitCredit() {
	}
	
	/**
	 * 写入收支余额表 A_A02
	 * 余额=上次余额+收入(贷)-支出（借）
	 * @param receive   收入  ->Credit 贷 
	 * @param pay       支出  ->Debit  借
	 * @param conn
	 * @throws SQLException
	 */
	public long insertBalance(Hashtable dBalance, Connection conn) throws SQLException {
		long rnt= 0;
		PreparedStatement pstat = null ;
		DestinationBalanceInfo dBalanceInfo = null;
		String insertSql = "insert into a_a02 (BANK_CODE," +"\n"+           //银行分支机构代码
										        "ACCOUNT,"+"\n"+            //外汇账户账号
												"DEAL_DATE,"+"\n"+          //发生日期
												"CURRENCE_CODE,"+ "\n"+     //账户币种
										        "CREDIT," +"\n"+            //当日贷方发生额
										        "DEBT," +"\n"+              //当日借方发生额
												"BALANCE," + "\n"+          //金额
										        "TLRNO) values(" +"\n"+     //录入柜员
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?)" +"\n"+
										        "";
		String strBankCode = "00000"; 
		   if(dBalance!=null &&!dBalance.isEmpty()){
			   	Enumeration e = dBalance.keys();
			   	while(e.hasMoreElements()){
			   		dBalanceInfo = (DestinationBalanceInfo)dBalance.get(e.nextElement());
			   		if(dBalanceInfo!=null){
			   			pstat = conn.prepareStatement(insertSql);
						pstat.setString(1,dBalanceInfo.getBank_code());        //机构部门号
						pstat.setString(2,dBalanceInfo.getAccount());          //外汇账户账号
						//pstat.setTimestamp(3,dBalanceInfo.getDeal_date());     //发生日期
						pstat.setDate(3,DataFormat.getDate(DataFormat.getDateString(dBalanceInfo.getDeal_date())));
						pstat.setString(4,dBalanceInfo.getCurrence_code());    //账户币种
						pstat.setDouble(5,dBalanceInfo.getCredit());           //当日贷方发生额
						pstat.setDouble(6,dBalanceInfo.getDebt());             //当日借方发生额
						pstat.setDouble(7,dBalanceInfo.getBalance());          //金额
						pstat.setString(8,dBalanceInfo.getTlrno());            //录入柜员
						pstat.executeUpdate();
						rnt++;
			   		}
			   	}
			}
		   return rnt;
	}
	

	/**
	 * 
	 * 取出对应银行分支机构代码、外汇账户账号、币种和当前日期的上一次日期的余额
	 * @param bank_code 银行分支机构代码
	 * @param account  外汇账户账号
	 * @param deal_date  当前日期
	 * @param currence_code 币种
	 * @return 当前日期的上一次日期的余额
	 * @throws SQLException 
	 */
	private double getLasttimeBalance(String bank_code , 
			                          String account ,
								      Timestamp deal_date , 
									  String currence_code)
	                                   {
		double rnt = 0.0;
		
		DestinationConn dc = new DestinationConn();
		Connection conn = dc.getConn();
		
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select balance "+"\n");
		sqlBuffer.append(" from a_a02 "+"\n");
		sqlBuffer.append(" where bank_code='"+bank_code+"' \n");
		sqlBuffer.append(" and account ='"+account+"' \n");
		sqlBuffer.append(" and currence_code ='"+currence_code+"' \n");
		sqlBuffer.append(" and deal_date = to_date('" +DataFormat.formatDate(deal_date)+"','yyyy-mm-dd') \n");
		sqlBuffer.append(" order by deal_date desc ");
		System.out.println(sqlBuffer.toString());
		
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			rs = pstat.executeQuery();
			if(rs.next()){
				rnt = rs.getDouble("balance");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			
			try{
				if(rs!=null)
					rs.close();
				if(pstat!=null)
					pstat.close();
				if(conn!=null)
				  conn.close();
			}catch(Exception e){
				
			}
		}
		return rnt;
	}
	
	/**
	 * 
	 * @param receive 贷方(收入)信息
	 * @param pay 借方(支出)信息
	 * @return
	 */
	
	public Hashtable getAccountBalance(Vector receive , Vector pay ){
		Hashtable rnt = new Hashtable();
		DestinationBalanceInfo dBalanceInfo = null;
		DestinationReceivePayInfo drpInfo = null;
		
		//处理Credit贷方(收入)信息
		if(receive!=null){
			for(int i=0 ;i<receive.size();i++){
				drpInfo = (DestinationReceivePayInfo)receive.get(i);
				if(drpInfo!=null){
					dBalanceInfo = (DestinationBalanceInfo)rnt.get(drpInfo.getAccount()+drpInfo.getCurrence_code());
					if(dBalanceInfo==null){
						dBalanceInfo = new DestinationBalanceInfo();
						dBalanceInfo.setBank_code(drpInfo.getBank_code());
						dBalanceInfo.setAccount(drpInfo.getAccount());
						dBalanceInfo.setDeal_date(drpInfo.getDeal_date());
						dBalanceInfo.setCurrence_code(drpInfo.getCurrence_code());
						dBalanceInfo.setLasttimeBalance(
								                  this.getLasttimeBalance(drpInfo.getBank_code(),
								                  		                  drpInfo.getAccount(),
																		  drpInfo.getDeal_date(),
																		  drpInfo.getCurrence_code()
																		  )
														);
						dBalanceInfo.setCredit(drpInfo.getAmount());
						rnt.put(drpInfo.getAccount()+drpInfo.getCurrence_code(),dBalanceInfo);
					}else{
						rnt.remove(drpInfo.getAccount()+drpInfo.getCurrence_code());
						dBalanceInfo.setCredit(drpInfo.getAmount());
						rnt.put(drpInfo.getAccount()+drpInfo.getCurrence_code(),dBalanceInfo);
					}
				}
			}
		}
		
        //处理Debit借方(支出)信息
		if(pay!=null){
			for(int i=0 ;i<pay.size();i++){
				drpInfo = (DestinationReceivePayInfo)pay.get(i);
				if(drpInfo!=null){
					dBalanceInfo = (DestinationBalanceInfo)rnt.get(drpInfo.getAccount()+drpInfo.getCurrence_code());
					if(dBalanceInfo==null){
						dBalanceInfo = new DestinationBalanceInfo();
						dBalanceInfo.setBank_code(drpInfo.getBank_code());
						dBalanceInfo.setAccount(drpInfo.getAccount());
						dBalanceInfo.setDeal_date(drpInfo.getDeal_date());
						dBalanceInfo.setCurrence_code(drpInfo.getCurrence_code());
						dBalanceInfo.setLasttimeBalance(
								                  this.getLasttimeBalance(drpInfo.getBank_code(),
								                  		                  drpInfo.getAccount(),
																		  drpInfo.getDeal_date(),
																		  drpInfo.getCurrence_code()
																		  )
														);
						dBalanceInfo.setDebt(drpInfo.getAmount());
						rnt.put(drpInfo.getAccount()+drpInfo.getCurrence_code(),dBalanceInfo);
					}else{
						rnt.remove(drpInfo.getAccount()+drpInfo.getCurrence_code());
						dBalanceInfo.setDebt(drpInfo.getAmount());
						rnt.put(drpInfo.getAccount()+drpInfo.getCurrence_code(),dBalanceInfo);
					}
				}
			}
		}
		return rnt;
	}

	public static void main(String[] args) {
		ConversionAccount ca = new ConversionAccount();
		Vector sAccount = ca.getSourceAccount();
		Vector dAccount = ca.getDestinationAccout();
		Vector newAccount = ca.getNewAccount(sAccount,dAccount);
		
		ConversionPay cp = new ConversionPay();
		Vector newPay = cp.getNewPay();
		
		ConversionReceive cr = new ConversionReceive();
		Vector newReceive = cr.getNewReceive();
	    
	    ConversionDebitCredit cdc = new ConversionDebitCredit();
	    DestinationConn dc = new DestinationConn();
	    Connection conn = dc.getConn();
	    try {
			cdc.insertBalance(cdc.getAccountBalance(newReceive,newPay),conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	}
}
