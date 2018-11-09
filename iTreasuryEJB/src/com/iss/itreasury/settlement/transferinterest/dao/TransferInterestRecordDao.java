package com.iss.itreasury.settlement.transferinterest.dao;

import java.sql.Connection;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.transferinterest.dataentity.TransferInterestRecordInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.settlement.util.SETTConstant;

public class TransferInterestRecordDao extends ITreasuryDAO {
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	public TransferInterestRecordDao()
	{
		super("SETT_TRANSFERINTERSETRECORD");
	}
	
	public TransferInterestRecordDao(Connection con)
	{
		super("SETT_TRANSFERINTERSETRECORD");
	}

	//翻页查询
	public PageLoader queryTransferInterestRecordInfo(TransferInterestRecordInfo transferInterestRecordInfo) throws Exception
	{
		getTransferInterestRecordSQL(transferInterestRecordInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.transferinterest.dataentity.TransferInterestRecordInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +" "+m_sbOrderBy.toString());
		return pageLoader;
	}
	
	/**
	 * 产生查询SQL  
	 * @param info
	 *
	 */
	public void getTransferInterestRecordSQL(TransferInterestRecordInfo transferInterestRecordInfo)
	{
		try{
			
			m_sbSelect = new StringBuffer();
			
			// select 
			m_sbSelect.append(" a.id,b.contractcode cracontractcode,a.dtstart,a.dtend,a.days,a.minterest,a.drate,a.ninteresttype, "); 
			m_sbSelect.append(" a.amount,c.sname inputusername,a.inputdate,a.stransno \n");
 
			// from 
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" SETT_TRANSFERINTERSETRECORD a, CRA_TRANSFERCONTRACTFORM b, USERINFO c \n");
			
			// where 
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and a.officeid= "+transferInterestRecordInfo.getOfficeid()+" \n");
			m_sbWhere.append(" and a.currencyid= "+transferInterestRecordInfo.getCurrencyid()+" \n");
			m_sbWhere.append(" and a.cracontractid = b.id \n");
			m_sbWhere.append(" and a.inputuserid = c.id \n");
			m_sbWhere.append(" and a.inputuserid = "+transferInterestRecordInfo.getInputuserid()+"\n");
			m_sbWhere.append(" and a.statusid = "+Constant.RecordStatus.VALID+" \n");
			
			String strTemp = "";
			//结息日
			strTemp = transferInterestRecordInfo.getStrinterestsettlement();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and a.dtinterestsettlement = to_date('"+strTemp+"','yyyy-mm-dd')");
			}
			
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by a.inputdate desc");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * 
	 */
	public long deleteRecord(long id) throws Exception
	{
		long lReturn = -1;
		StringBuffer sql = null;
		try
		{
			initDAO();
			sql = new StringBuffer();
			sql.append("update SETT_TRANSFERINTERSETRECORD A ");
		    sql.append("set A.statusid = "+SETTConstant.TransactionStatus.DELETED+" ");
		    sql.append("where A.id = "+id);
		    transPS = transConn.prepareStatement(sql.toString());
		    transPS.executeUpdate(sql.toString());
		    lReturn = id;
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		finally
		{
            finalizeDAO();
        }		
		return lReturn;
	}

}
