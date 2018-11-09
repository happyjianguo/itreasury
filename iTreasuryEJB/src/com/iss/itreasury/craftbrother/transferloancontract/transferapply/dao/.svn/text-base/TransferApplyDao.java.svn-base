package com.iss.itreasury.craftbrother.transferloancontract.transferapply.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransferApplyDao extends ITreasuryDAO {
	
	private static Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER);
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	public TransferApplyDao()
	{
		super ( "cra_transferapplyform" ) ;
	}
	
	public TransferApplyDao(String tablename ,String seqfortable)
	{
		super(tablename,seqfortable);
	}
	
	public TransferApplyDao(String tablename ,String seqfortable,Connection  conn)
	{
		super(tablename,seqfortable,conn);
	}
	public void save(TransferApplyInfo info) throws Exception 
	{
		try{
		     add(info);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					finalizeDAO();
				}
	}
	//翻页查询
	public PageLoader queryTransferApplyInfo(TransferApplyInfo transferApplyInfo) throws Exception
	{
		getTransferApplySQL(transferApplyInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo",
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
	public void getTransferApplySQL(TransferApplyInfo transferApplyInfo)
	{
		try{
			
			m_sbSelect = new StringBuffer();
			// select 
			m_sbSelect.append(" A.id,A.sapplycode,A.transtypeid,A.counterpartid,A.counterpartname, A.sapplyamount," +
					"A.zstartdate ,A.zenddate,A.inputuserid,A.inputdate,A.statusid,B.counterpartcode,C.sname as inputusername \n");
			// from 
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" cra_transferapplyform A , CRA_COUNTERPART B , USERINFO C \n");
			// where 
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and A.officeid = "+transferApplyInfo.getOfficeId()+" \n");
			m_sbWhere.append(" and A.currencyid = "+transferApplyInfo.getCurrencyId()+" \n");
			m_sbWhere.append(" and A.inputuserid = "+transferApplyInfo.getInputUserId()+" \n");
			m_sbWhere.append(" and A.counterpartid = B.id \n");
			m_sbWhere.append(" and A.inputuserid = C.id \n");
			 
			String strTemp = "";
			//状态
			strTemp = String.valueOf(transferApplyInfo.getStatusId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.statusid = "+strTemp+" \n");
			}
			//业务类型
			strTemp = String.valueOf(transferApplyInfo.getTransTypeId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.transtypeid = '" + strTemp + "' \n");
			}
			//申请编号
			strTemp = transferApplyInfo.getApplyCodeFrom();
			if(strTemp!=null && !strTemp.equals(""))
			{
				m_sbWhere.append("and A.sapplycode >= '"+strTemp+"' \n");
			}
			strTemp = transferApplyInfo.getApplyCodeTo();
			if(strTemp!=null && !strTemp.equals(""))
			{
				m_sbWhere.append("and A.sapplycode <= '"+strTemp+"' \n");
			}
			//交易对手编号
			strTemp = String.valueOf(transferApplyInfo.getCounterPartId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.counterpartid = '" + strTemp + "' \n");
			}
			//金额
			if (transferApplyInfo.getApplyAmountFrom()>0.0)
			{	
				m_sbWhere.append(" and A.sapplyamount >= " 
						+ DataFormat.formatAmount(transferApplyInfo.getApplyAmountFrom()) + " \n");
			}

			if (transferApplyInfo.getApplyAmountTo()>0.0)
			{	
				m_sbWhere.append(" and A.sapplyamount <=" 
						+ DataFormat.formatAmount(transferApplyInfo.getApplyAmountTo()) + " \n");
			}
			//录入日期	
			strTemp = transferApplyInfo.getInputDateFrom();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.inputdate >= to_date('"+strTemp+"','yyyy-mm-dd') \n");
			}
			strTemp = transferApplyInfo.getInputDateTo();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.inputdate <= to_date('"+strTemp+"','yyyy-mm-dd')+1 \n");
			}
			
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by A.inputdate desc");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public String applyCode(TransferApplyInfo transferApplyInfo,Connection conn) throws Exception 
	{
		String strReturn ="";
		try {		
				HashMap map = new HashMap();
				map.put("officeID",String.valueOf(transferApplyInfo.getOfficeId()));
				map.put("currencyID",String.valueOf(transferApplyInfo.getCurrencyId()));
				map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
				map.put("transTypeID",String.valueOf(Constant.ApprovalAction.CRA_TRANSLOANAPPLY));
				map.put("connection", conn);
				strReturn=CreateCodeManager.createCode(map);	
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return strReturn;
	}
	
	public long checkApplyForm(long id) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) countrecord from CRA_LOANAPPLYFORM t where t.SAPPLYFORMID = "+id+" and t.statusid <> "+CRAconstant.TransactionStatus.DELETED);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getLong("countrecord");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
	
	public long checkContractForm(long id) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) countrecord from CRA_TRANSFERCONTRACTFORM t where t.APPLYID = "+id+" and t.STATUSID <> "+CRAconstant.TransactionStatus.DELETED);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getLong("countrecord");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
	
	public long updateApplyStatus(TransferApplyInfo transferApplyInfo) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		try
		{
			long id = transferApplyInfo.getId();
			long statusId = transferApplyInfo.getStatusId();
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("update CRA_TRANSFERAPPLYFORM t set t.statusid = "+statusId+" where t.id = "+id);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			lReturn = 1;
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
	
	public long checkCounterPart(long id) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) num from CRA_TRANSFERAPPLYFORM t where t.COUNTERPARTID  = "+id+" and t.statusid <> "+CRAconstant.TransactionStatus.DELETED);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getLong("num");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
	
}
