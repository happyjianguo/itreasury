package com.iss.itreasury.settlement.transfinancialmargin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class TransfinancialMarginDao extends SettlementDAO{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public TransfinancialMarginDao()
	{
		super("SETT_TRANSFINANCIALMARGIN");
	}
	/**
	 * 根据 合同ID 账户ID 保证金保后处理通知单ID 查找表中是否已经存在该记录
	 * @param NContractID
	 * @param NAccountID
	 * @param NLoanNoticeID
	 * @return
	 * @throws Exception
	 */
	public TransFinancialMarginInfo findIDByCondition(long NContractID,long NAccountID,long NLoanNoticeID)throws Exception{
		TransFinancialMarginInfo rInfo = new TransFinancialMarginInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select id ,nStatusID from SETT_TRANSFINANCIALMARGIN where NContractID = ?  and NAccountID = ? and NLoanNoticeID = ? and NSTATUSID != "+SETTConstant.TransactionStatus.DELETED);
			ps = con.prepareStatement(buffer.toString());
			ps.setLong(1, NContractID);
			ps.setLong(2, NAccountID);
			ps.setLong(3, NLoanNoticeID);
			rs = ps.executeQuery();
			if(rs.next()){
				rInfo.setId(rs.getLong("id"));
				rInfo.setNStatusID(rs.getLong("nStatusID"));
			}
		}catch(Exception e){
			 e.printStackTrace();
			 throw e;
		}
		finally{
			try{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		} 
		return rInfo;
	}
	
	/**
	 * 根据交易号查询
	 * @param strTransNO
	 * @return
	 * @throws Exception
	 */
	public TransFinancialMarginInfo findByTransNO(String strTransNO)throws Exception{
		TransFinancialMarginInfo rInfo = new TransFinancialMarginInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from SETT_TRANSFINANCIALMARGIN where STRANSNO = ? and NSTATUSID != "+SETTConstant.TransactionStatus.DELETED);
			ps = con.prepareStatement(buffer.toString());
			ps.setString(1, strTransNO);
			rs = ps.executeQuery();
			if(rs.next()){
				rInfo.setId(rs.getLong("ID"));
				rInfo.setNOfficeID(rs.getLong("nOfficeID"));
				rInfo.setNCurrencyID(rs.getLong("nCurrencyID"));
				rInfo.setSTransNo(rs.getString("sTransNo"));
				rInfo.setTypeID(rs.getLong("typeid"));
				rInfo.setNClientID(rs.getLong("nClientID"));
				rInfo.setNAccountID(rs.getLong("nAccountID"));
				rInfo.setNContractID(rs.getLong("ncontractid"));
				rInfo.setNLoanNoticeID(rs.getLong("nloannoticeid"));
				rInfo.setCode(rs.getString("code"));
				rInfo.setAmount(rs.getDouble("amount"));
				rInfo.setNStatusID(rs.getLong("nstatusid"));
				rInfo.setTranstypeID(rs.getLong("transtypeid"));
				rInfo.setDtExecute(rs.getTimestamp("dtExecute"));
				rInfo.setSAbstract(rs.getString("sAbstract"));
				rInfo.setSCheckAbstract(rs.getString("SCheckAbstract"));
				rInfo.setDtInput(rs.getTimestamp("dtInput"));
				rInfo.setNInputUserID(rs.getLong("NInputUserID"));
				rInfo.setDtCheck(rs.getTimestamp("dtCheck"));
				rInfo.setNCheckUserID(rs.getLong("NCheckUserID"));
				rInfo.setNStatusID(rs.getLong("NStatusID"));
				rInfo.setNCurrentAccountID(rs.getLong("ncurrentaccountid"));
				rInfo.setSExtAcctNo(rs.getString("SExtAcctNo"));
				rInfo.setSExtClientName(rs.getString("SExtClientName"));
				rInfo.setSRemitInBank(rs.getString("SRemitInBank"));
				rInfo.setSRemitInProvince(rs.getString("SRemitInProvince"));
				rInfo.setSRemitInCity(rs.getString("SRemitInCity"));
				rInfo.setNExtBankID(rs.getLong("NExtBankID"));
				}
		}catch(Exception e){
			 e.printStackTrace();
			 throw e;
		}
		finally{
			try{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		} 
		return rInfo;
	}
	
	/**
	 * 根据条件判断保证金交易是否重复的方法： 逻辑说明：
	 * 
	 * @param FixedContinueInfo
	 *            searchInfo , 保证金交易实体类
	 * @return boolean , false 重复
	 * @throws Exception
	 */
	public boolean checkIsUsed(long lID,long statusid) throws Exception
	{ 
		boolean rtnFlg = false; 
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try { 

			String strSQL = "select * from loan_recognizancenoticeform where id=? and  statusid = ? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1,lID);
			ps.setLong(2,statusid);
			rs = ps.executeQuery();
			if (rs.next()) {
				rtnFlg = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}
	
	/**
	 * 将融资租赁保证金保护处理的表中，对应的记录的状态修改。
	 * @param id
	 * @param statusID
	 * @throws SQLException
	 */
	
	public void updaterecognizancenoticeformStatus(long id, long statusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update loan_recognizancenoticeform set statusid=? where id=?");
			pstmt.setLong(1, statusID);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	public Collection match(TransFinancialMarginInfo info,long typeFlag) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = null;

			/**
			 * 
			 * 
			 */
			if (info.getTranstypeID() == SETTConstant.TransactionType.FINANCIALMARGIN) 
			{
				buffer = new StringBuffer();
				
				buffer.append("SELECT * FROM SETT_TRANSFINANCIALMARGIN  \n");
				buffer.append("WHERE 1 = 1 \n");
				buffer.append("AND nofficeid = ? \n"); 
				buffer.append("AND ncurrencyid = ? \n");
				buffer.append("AND typeid = ? \n");
				buffer.append("AND nclientid = ? \n");
				buffer.append("AND naccountid = ? \n");
				buffer.append("AND ncontractid = ? \n");
				buffer.append("AND nloannoticeid = ? \n");
				buffer.append("AND code = ? \n");
				buffer.append("AND amount = ? \n");
				buffer.append("AND nstatusid = ? \n");
				buffer.append("AND NINPUTUSERID != ? \n");    //录入人不能等于复核人。
				
				if(typeFlag == 1 && info.getTypeID() == LOANConstant.DecognizanceDeal.MATURITY){
					buffer.append("AND ncurrentaccountid = ? \n");
				}
				
				if(typeFlag == 2 && info.getTypeID() == LOANConstant.DecognizanceDeal.MATURITY){
					buffer.append("AND sextacctno = ? \n");
					buffer.append("AND sextclientname = ? \n");
					buffer.append("AND sremitinbank = ? \n");
					buffer.append("AND sremitinprovince = ? \n");
					buffer.append("AND sremitincity = ? \n"); 
					buffer.append("AND nextbankid = ? \n");
				}
				
				buffer.append("ORDER BY id \n");
				
				ps = con.prepareStatement(buffer.toString());
				
				log.info("  匹配SQL \n"+buffer.toString());

				int index = 1;
				ps.setLong(index++, info.getNOfficeID());
				System.out.println("info.getNOfficeID() = "+info.getNOfficeID());
				
				ps.setLong(index++, info.getNCurrencyID());
				System.out.println("info.getNCurrencyID() = "+info.getNCurrencyID());
				
				ps.setLong(index++, info.getTypeID());
				System.out.println("info.getTypeID() = "+info.getTypeID());
				
				ps.setLong(index++, info.getNClientID());
				System.out.println("info.getNClientID() = "+info.getNClientID());
				
				ps.setLong(index++, info.getNAccountID());
				System.out.println("info.getNAccountID() = "+info.getNAccountID());
				
				ps.setLong(index++, info.getNContractID());
				System.out.println("info.getNContractID() = "+info.getNContractID());
				
				ps.setDouble(index++, info.getNLoanNoticeID());
				System.out.println("info.getNLoanNoticeID() = "+info.getNLoanNoticeID());
				
				ps.setString(index++, info.getCode());
				System.out.println("info.getCode() = "+info.getCode());
				
				ps.setDouble(index++, info.getAmount());
				System.out.println("info.getAmount() = "+info.getAmount());
				
				ps.setLong(index++, info.getNStatusID());
				System.out.println("info.getNStatusID() = "+info.getNStatusID());
				
				ps.setLong(index++, info.getNCheckUserID());
				System.out.println("info.getNCheckUserID() = "+info.getNCheckUserID());
				
				if(typeFlag == 1 && info.getTypeID() == LOANConstant.DecognizanceDeal.MATURITY){
					ps.setLong(index++, info.getNCurrentAccountID());
					System.out.println("info.getNCurrentAccountID() = "+info.getNCurrentAccountID());
				}
				if(typeFlag == 2 && info.getTypeID() == LOANConstant.DecognizanceDeal.MATURITY){
					ps.setString(index++, info.getSExtAcctNo());
					System.out.println("info.getSExtAcctNo() = "+info.getSExtAcctNo());
					
					ps.setString(index++, info.getSExtClientName());
					System.out.println("info.getSExtClientName() = "+info.getSExtClientName());
					
					ps.setString(index++, info.getSRemitInBank());
					System.out.println("info.getSRemitInBank() = "+info.getSRemitInBank());
					
					ps.setString(index++, info.getSRemitInProvince());
					System.out.println("info.getSRemitInProvince() = "+info.getSRemitInProvince());
					
					ps.setString(index++, info.getSRemitInCity());
					System.out.println("info.getSRemitInCity() = "+info.getSRemitInCity());
					
					ps.setLong(index++, info.getNExtBankID());
					System.out.println("info.getNExtBankID() = "+info.getNExtBankID());
					
				}
				
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransFinancialMarginInfo depositInfo = new TransFinancialMarginInfo();
					
					depositInfo.setId(rs.getLong("ID"));
					depositInfo.setNOfficeID(rs.getLong("nOfficeID"));
					depositInfo.setNCurrencyID(rs.getLong("nCurrencyID"));
					depositInfo.setSTransNo(rs.getString("sTransNo"));
					depositInfo.setTypeID(rs.getLong("typeid"));
					depositInfo.setNClientID(rs.getLong("nClientID"));
					depositInfo.setNAccountID(rs.getLong("nAccountID"));
					depositInfo.setNContractID(rs.getLong("ncontractid"));
					depositInfo.setNLoanNoticeID(rs.getLong("nloannoticeid"));
					depositInfo.setCode(rs.getString("code"));
					depositInfo.setAmount(rs.getDouble("amount"));
					depositInfo.setNStatusID(rs.getLong("nstatusid"));
					depositInfo.setTranstypeID(rs.getLong("transtypeid"));
					depositInfo.setDtExecute(rs.getTimestamp("dtExecute"));
					depositInfo.setSAbstract(rs.getString("sAbstract"));
					
					depositInfo.setSCheckAbstract(rs.getString("SCheckAbstract"));
					depositInfo.setDtInput(rs.getTimestamp("dtInput"));
					depositInfo.setNInputUserID(rs.getLong("NInputUserID"));
					depositInfo.setDtCheck(rs.getTimestamp("dtCheck"));
					depositInfo.setNCheckUserID(rs.getLong("NCheckUserID"));
					depositInfo.setNStatusID(rs.getLong("NStatusID"));
					
					if(typeFlag == 1 && info.getTypeID() == LOANConstant.DecognizanceDeal.MATURITY){
						depositInfo.setNCurrentAccountID(rs.getLong("ncurrentaccountid"));
					}
					if(typeFlag == 2 && info.getTypeID() == LOANConstant.DecognizanceDeal.MATURITY){
						depositInfo.setSExtAcctNo(rs.getString("SExtAcctNo"));
						depositInfo.setSExtClientName(rs.getString("SExtClientName"));
						depositInfo.setSRemitInBank(rs.getString("SRemitInBank"));
						depositInfo.setSRemitInProvince(rs.getString("SRemitInProvince"));
						depositInfo.setSRemitInCity(rs.getString("SRemitInCity"));
						depositInfo.setNExtBankID(rs.getLong("NExtBankID"));
					}
					arrResult.add(depositInfo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;
	}
	/**
	 * 业务复核 按状态查询
	 * @param info
	 * @param lStatusIDs
	 * @return
	 * @throws Exception
	 */
	public Collection findByStatus4Check(TransFinancialMarginInfo info,long[] lStatusIDs) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String strStatus = "";
			StringBuffer buffer = new StringBuffer();
			if(lStatusIDs != null && lStatusIDs.length != 0){
				for(int i = 0 ;i<lStatusIDs.length;i++){
					strStatus += lStatusIDs[i]+",";
				}
				strStatus = strStatus.substring(0, strStatus.length()-1);
			}
			buffer.append(" select * \n");
			buffer.append(" from sett_transfinancialmargin \n");
			buffer.append(" where  \n");
			buffer.append(" nOfficeID=? and \n");
			buffer.append(" nCurrencyID=? and transtypeID=? and \n");
			buffer.append(" NSTATUSID in ( "+strStatus+" ) \n");
			buffer.append(" and NCHECKUSERID=? \n");
			buffer.append(" order by sTransNo \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getNOfficeID());
			ps.setLong(index++, info.getNCurrencyID());
			ps.setLong(index++, info.getTranstypeID());
			ps.setLong(index++, info.getNCheckUserID());


			rs = ps.executeQuery();
			while (rs.next()) {
				TransFinancialMarginInfo depositInfo = new TransFinancialMarginInfo();
				
				depositInfo.setId(rs.getLong("ID"));
				depositInfo.setNOfficeID(rs.getLong("nOfficeID"));
				depositInfo.setNCurrencyID(rs.getLong("nCurrencyID"));
				depositInfo.setSTransNo(rs.getString("sTransNo"));
				depositInfo.setTypeID(rs.getLong("typeid"));
				depositInfo.setNClientID(rs.getLong("nClientID"));
				depositInfo.setNAccountID(rs.getLong("nAccountID"));
				depositInfo.setNContractID(rs.getLong("ncontractid"));
				depositInfo.setNLoanNoticeID(rs.getLong("nloannoticeid"));
				depositInfo.setCode(rs.getString("code"));
				depositInfo.setAmount(rs.getDouble("amount"));
				depositInfo.setNStatusID(rs.getLong("nstatusid"));
				depositInfo.setTranstypeID(rs.getLong("transtypeid"));
				depositInfo.setNCurrentAccountID(rs.getLong("ncurrentaccountid"));
				depositInfo.setSExtAcctNo(rs.getString("SExtAcctNo"));
				depositInfo.setSExtClientName(rs.getString("SExtClientName"));
				depositInfo.setSRemitInBank(rs.getString("SRemitInBank"));
				depositInfo.setSRemitInProvince(rs.getString("SRemitInProvince"));
				depositInfo.setSRemitInCity(rs.getString("SRemitInCity"));
				depositInfo.setNExtBankID(rs.getLong("NExtBankID"));
				depositInfo.setNBankID(rs.getLong("NBankID"));
				depositInfo.setDtExecute(rs.getTimestamp("dtExecute"));
				depositInfo.setSAbstract(rs.getString("sAbstract"));
				 
				arrResult.add(depositInfo);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}
	/**
	 * 业务处理，按状态查询
	 * @param info
	 * @param lStatusIDs
	 * @return
	 * @throws Exception
	 */
	public Collection findByStatus4Deal(TransFinancialMarginInfo info,long[] lStatusIDs) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String strStatus = "";
			StringBuffer buffer = new StringBuffer();
			if(lStatusIDs != null && lStatusIDs.length != 0){
				for(int i = 0 ;i<lStatusIDs.length;i++){
					strStatus += lStatusIDs[i]+",";
				}
				strStatus = strStatus.substring(0, strStatus.length()-1);
			}
			buffer.append(" select * \n");
			buffer.append(" from sett_transfinancialmargin \n");
			buffer.append(" where  \n");
			buffer.append(" nOfficeID=? and \n");
			buffer.append(" nCurrencyID=? and transtypeID=? and \n");
			buffer.append(" NSTATUSID in ( "+strStatus+" ) \n");
			buffer.append(" and NINPUTUSERID=? \n");
			buffer.append(" order by sTransNo \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getNOfficeID());
			ps.setLong(index++, info.getNCurrencyID());
			ps.setLong(index++, info.getTranstypeID());
			ps.setLong(index++, info.getNInputUserID());


			rs = ps.executeQuery();
			while (rs.next()) {
				TransFinancialMarginInfo depositInfo = new TransFinancialMarginInfo();
				
				depositInfo.setId(rs.getLong("ID"));
				depositInfo.setNOfficeID(rs.getLong("nOfficeID"));
				depositInfo.setNCurrencyID(rs.getLong("nCurrencyID"));
				depositInfo.setSTransNo(rs.getString("sTransNo"));
				depositInfo.setTypeID(rs.getLong("typeid"));
				depositInfo.setNClientID(rs.getLong("nClientID"));
				depositInfo.setNAccountID(rs.getLong("nAccountID"));
				depositInfo.setNContractID(rs.getLong("ncontractid"));
				depositInfo.setNLoanNoticeID(rs.getLong("nloannoticeid"));
				depositInfo.setCode(rs.getString("code"));
				depositInfo.setAmount(rs.getDouble("amount"));
				depositInfo.setNStatusID(rs.getLong("nstatusid"));
				depositInfo.setTranstypeID(rs.getLong("transtypeid"));
				depositInfo.setNCurrentAccountID(rs.getLong("ncurrentaccountid"));
				depositInfo.setSExtAcctNo(rs.getString("SExtAcctNo"));
				depositInfo.setSExtClientName(rs.getString("SExtClientName"));
				depositInfo.setSRemitInBank(rs.getString("SRemitInBank"));
				depositInfo.setSRemitInProvince(rs.getString("SRemitInProvince"));
				depositInfo.setSRemitInCity(rs.getString("SRemitInCity"));
				depositInfo.setNExtBankID(rs.getLong("NExtBankID"));
				depositInfo.setNBankID(rs.getLong("NBankID"));
				depositInfo.setDtExecute(rs.getTimestamp("dtExecute"));
				 
				arrResult.add(depositInfo);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;

	}
	
}
