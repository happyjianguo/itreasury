package com.iss.itreasury.evoucher.setting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz;
import com.iss.itreasury.util.Constant;

//add by dwj 20110815 将电子签章添加到电子单据柜模块下 
//代码来源com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterparBankDao
public class ElectronSignatureDao extends ITreasuryDAO{
	
	public static long userId;
	public static String sTransNo;
	public static String nPrintCount;
	
	public static void initPrintLog(long userId,String sTransNo,String nPrintCount){
		ElectronSignatureDao.userId = userId;
		ElectronSignatureDao.sTransNo = sTransNo;
		ElectronSignatureDao.nPrintCount = nPrintCount;
		
		System.out.println("ElectronSignatureDao.userId==>"+ElectronSignatureDao.userId);
		System.out.println("ElectronSignatureDao.sTransNo==>"+ElectronSignatureDao.sTransNo);
		System.out.println("ElectronSignatureDao.nPrintCount==>"+ElectronSignatureDao.nPrintCount);
	}
	
	/**
     * jzw 2010-05-24
     * 判断是否要加签章
     * @param clientID  客户id
     * @param transTypeID 交易类型编号
     * @param nOfficeID 办事处ID
     * @param nCurrencyID 币种ID
     * @return String
	 * @throws ITreasuryDAOException
	 */
	public String findIsSignature(long clientID,long transTypeID,long nOfficeID,long nCurrencyID,String billName,int isNetBank) throws ITreasuryDAOException {
		ResultSet res = null;
		String isSignature = "0";
		String transSignature = "0";
		StringBuffer buffer = null;
		try {
			
			initDAO();
			if(isNetBank==Constant.NETBANK)
			{
				buffer = new StringBuffer(); 
				buffer.append("SELECT NISSIGNATURE FROM \n");
				buffer.append("sett_signature");
				buffer.append("\n WHERE NOFFICEID="+nOfficeID+" and NCLIENTID="+clientID);
				System.out.println("客户是否授权允许签章语句---"+buffer.toString());  //jzw 2010-05-26
				prepareStatement(buffer.toString());
				res = executeQuery();
				try {
					while(res!=null&&res.next()){
						isSignature = res.getString("NISSIGNATURE");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				isSignature = "1";
			}
			buffer = new StringBuffer();
			buffer.append("SELECT NISSIGNATURE FROM \n");
			buffer.append("print_billrelation");
			buffer.append("\n WHERE NOFFICEID="+nOfficeID+" and ncurrency="+nCurrencyID);
			buffer.append("\n and ntransactiontypeid="+transTypeID+" and upper(stempname)='"+billName.toUpperCase()+"' and ndeptid="+isNetBank);
			System.out.println("单据类型是否授权允许签章语句---"+buffer.toString());  //jzw 2010-05-26
			prepareStatement(buffer.toString());
			res = executeQuery();
			try {
				while(res!=null&&res.next()){
						transSignature = res.getString("NISSIGNATURE");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(isSignature!=null&&isSignature.equals("1")&&transSignature!=null&&transSignature.equals("1")){
				new OBAccountSignatureBiz().recordPrintInfo(clientID, nOfficeID, nCurrencyID, 
						transTypeID+"",userId,sTransNo,nPrintCount);
				return isSignature;
			}
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
		return "0";
	}

}
