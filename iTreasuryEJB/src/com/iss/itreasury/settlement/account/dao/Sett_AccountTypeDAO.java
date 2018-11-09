package com.iss.itreasury.settlement.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.util.*;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-17
 */
public class Sett_AccountTypeDAO extends SettlementDAO
{

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public Sett_AccountTypeDAO()
	{
		strTableName = "Sett_AccountType";
	}
	
	public Sett_AccountTypeDAO(Connection conn)
	{
		super(conn);
		strTableName = "Sett_AccountType";
	}	
	

	public AccountTypeInfo findByID(long id) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		AccountTypeInfo info = null;
		try
		{ 
			String strSQL = "SELECT * FROM " + strTableName + " WHERE ID = " + id + " AND NSTATUSID = " + 1;
			log.info(strSQL);
			conn = this.getConnection();
			pstmt = conn.prepareStatement(strSQL);
		
			rset = pstmt.executeQuery();

			ArrayList list = (ArrayList) getInfoFromResultSet(rset);
			
			if (list.size() >= 1)
				info = (AccountTypeInfo) list.get(0);
			
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			cleanup(rset);
			cleanup(pstmt);
			cleanup(conn);
		}
		return info;
	}

	private Collection getInfoFromResultSet(ResultSet rs) throws SQLException
	{

		ArrayList list = new ArrayList();
		while (rs.next())
		{
			AccountTypeInfo info = new AccountTypeInfo();
			info.setAccountType(rs.getString("SACCOUNTTYPE"));
			info.setAccountTypeCode(rs.getString("SACCOUNTTYPECODE"));
			info.setAccountGroupID(rs.getLong("NACCOUNTGROUPID"));
			info.setAutoClearAccount(rs.getLong("NAUTOCLEARACCOUNT"));
			info.setDefaultDocCode(rs.getString("SDEFAULTDOCCODE"));
			info.setId(rs.getLong("ID"));
			info.setIsCosign(rs.getLong("NISCONSIGN"));
			info.setIsDraftType(rs.getLong("NISDRAFTTYPE"));
			info.setIsExistSubClass(rs.getLong("NISEXISTSUBCLASS"));
			info.setIsLoanMonth(rs.getLong("NISLOANMONTH"));
			info.setIsLoanType(rs.getLong("NISLOANTYPE"));
			info.setIsLoanYear(rs.getLong("NISLOANYEAR"));
			info.setSubjectCode(rs.getString("SSUBJECTCODE"));
			info.setBalanceDirection(rs.getLong("nBalanceDirection"));
			info.setIsClientID(rs.getLong("NISCLIENT"));
			info.setIsReDiscountType(rs.getLong("NISTRANSDISCOUNTTYPE"));
			info.setIsAssure(rs.getLong("NISASSURE"));
			
			//added by mzh_fu 2008/04/29 国电需求活期增加账户下级分类；
		    // 定期、通知增加账户和存单号下级分类；贷款类增加合同号和放款通知单下级分类
			info.setIsAccount(rs.getLong("NISACCOUNT"));
			info.setIsDeposit(rs.getLong("NISDEPOSIT"));
			info.setIsContract(rs.getLong("NISCONTRACT"));
			info.setIsLoanNote(rs.getLong("NISLOANNOTE"));
			
			list.add(info);
		}
		return list;

	}
	/**
	 * 取出所有账户类型
	 * @return
	 */
	public Collection findAllAccountType()
	{
		ArrayList list = new ArrayList();
		String strSQL = "SELECT * FROM " + strTableName + " WHERE NSTATUSID = " + Constant.RecordStatus.VALID + " order by id";
		
		try {
			this.initDAO();
			transPS  = this.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			list = (ArrayList) getInfoFromResultSet(transRS);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 取出当前办事处，币种下所有账户类型
	 * @return
	 */
	public Collection findAllAccountTypeByOfficeAndCurrency(long lOfficeID,long lCurrencyID)
	{
		ArrayList list = new ArrayList();
		String strSQL = "SELECT * FROM " + strTableName + " WHERE NSTATUSID = " + Constant.RecordStatus.VALID +" and OFFICEID="+lOfficeID+" and CURRENCYID="+lCurrencyID+" order by id";
		//String strSQL = "SELECT * FROM " + strTableName + " WHERE NSTATUSID = " + Constant.RecordStatus.VALID +" and CURRENCYID= "+lCurrencyID+" order by id";
		try {
			this.initDAO();
			transPS  = this.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			list = (ArrayList) getInfoFromResultSet(transRS);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * add by kevin(刘连凯)2011-07-13
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param AccountGroupIds
	 * @return
	 */
	public Collection findAccountTypeByAccountGroupIds(long lOfficeID,long lCurrencyID,long[] AccountGroupIds)
	{
		String query="";
		
		for(int i=0;i<AccountGroupIds.length;i++)
		{									
			if(i<AccountGroupIds.length -1)
			{	
								
				query= query+ AccountGroupIds[i] + " , ";
			}
			else
			{
				query= query+ AccountGroupIds[i];
			}
		}	
		ArrayList list = new ArrayList();		
		String strSQL = " SELECT * FROM " + strTableName + " WHERE NSTATUSID = " + Constant.RecordStatus.VALID;
		if(query!=null&&query.trim().length()>0) strSQL+=" and NACCOUNTGROUPID in ("+query+")";
		//if(lOfficeID>0)      strSQL+=" and OFFICEID= "+lOfficeID+"";
		         strSQL+=" and CURRENCYID= "+lCurrencyID+" order by id";
		try {
			this.initDAO();
			transPS  = this.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			list = (ArrayList) getInfoFromResultSet(transRS);
		} catch (ITreasuryDAOException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		finally
		{
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {				
				e1.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * add by kevin(刘连凯)2011-07-26
	 * 查询办事处为lOfficeID，币种为lCurrencyID的委托贷款类型的id
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 */
	public long getEntrustmentAccountTypeId(long lOfficeID,long lCurrencyID){	
		String accountTypeCode = Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"4");		
		return this.getAccountTypeId(lOfficeID,lCurrencyID,accountTypeCode);
	}
	/**
	 * add by kevin(刘连凯)2011-07-26
	 * 查询满足办事处id为lOfficeID,币种id为lCurrencyID,账户编号为strAccountTypeCode的账户类型id
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param strAccountTypeCode
	 * @return
	 */
	public long getAccountTypeId(long lOfficeID,long lCurrencyID,String strAccountTypeCode)
	{		
		
		long lReturnID=-1;		
		String strSQL = " SELECT distinct ID FROM " + strTableName + " WHERE NSTATUSID = " + Constant.RecordStatus.VALID;		
		strSQL+=" and OFFICEID= "+lOfficeID+"";
		strSQL+=" and SACCOUNTTYPECODE='"+strAccountTypeCode+"'";
		strSQL+=" and CURRENCYID= "+lCurrencyID+" order by id";
		try {
			this.initDAO();
			transPS  = this.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				lReturnID=transRS.getLong("ID");
			}
		} catch (ITreasuryDAOException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		finally
		{
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {				
				e1.printStackTrace();
			}
		}
		return lReturnID;
	}
}
