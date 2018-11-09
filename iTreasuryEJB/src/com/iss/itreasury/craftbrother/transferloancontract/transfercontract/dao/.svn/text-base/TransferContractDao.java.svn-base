package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.settlement.util.SETTConstant;

public class TransferContractDao extends ITreasuryDAO {
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	public TransferContractDao()
	{
		super("CRA_TRANSFERCONTRACTFORM");
	}
	
	public TransferContractDao(Connection conn)
	{
		super("CRA_TRANSFERCONTRACTFORM","SEQ_CRA_TRANSFERCONTRACTFORM",conn);
	}
	public void save(TransferContractInfo info) throws Exception 
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
	public PageLoader queryTransferContractInfo(TransferContractInfo transferContractInfo) throws Exception
	{
		getTransferContractSQL(transferContractInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo",
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
	public void getTransferContractSQL(TransferContractInfo transferContractInfo)
	{
		try{
			
			m_sbSelect = new StringBuffer();
			// select 
			m_sbSelect.append("b.counterpartname,b.counterpartcode, a.inputdate,a.inputuserid,A.id,A.contractcode,A.TRANSTYPEID,A.COUNTERPARTID,A.amount,A.statusid \n");
			// from 
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" CRA_TRANSFERCONTRACTFORM A,cra_counterpart B \n");
			// where 
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and A.officeid= "+transferContractInfo.getOfficeId()+" \n");
			m_sbWhere.append(" and A.currencyid= "+transferContractInfo.getCurrencyId()+" \n");
			m_sbWhere.append(" and A.COUNTERPARTID = b.id\n");
			
			String strTemp = "";
			//业务类型
			strTemp = String.valueOf(transferContractInfo.getTranstypeId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.transtypeid = '" + strTemp + "'");
			}
			//合同状态
			strTemp = String.valueOf(transferContractInfo.getStatusId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.statusid= "+strTemp+" \n");
			}
			//合同编号
			strTemp = String.valueOf(transferContractInfo.getContractIdFrom());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append("and A.id >= '"+strTemp+"'");
			}
			strTemp = String.valueOf(transferContractInfo.getContractIdTo());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append("and A.id <= '"+strTemp+"'");
			}
			//交易对手编号
			strTemp = String.valueOf(transferContractInfo.getCounterPartId());
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.counterpartid = '" + strTemp + "'");
			}
			//金额
			double dbTemp = 0.0;
			dbTemp = transferContractInfo.getApplyAmountFrom();
			if (dbTemp > 0.0)
			{	
				m_sbWhere.append(" and A.amount >= '" + dbTemp + "'");
			}
			dbTemp = transferContractInfo.getApplyAmountTo();
			if (dbTemp > 0.0)
			{	
				m_sbWhere.append(" and A.amount <= '" + dbTemp + "'");
			}
			//录入日期	
			strTemp = transferContractInfo.getInputDateFrom();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.inputdate >= to_date('"+strTemp+"','yyyy-mm-dd')");
			}
			strTemp = transferContractInfo.getInputDateTo();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.inputdate <= to_date('"+strTemp+"','yyyy-mm-dd')+1 ");
			}
			strTemp = String.valueOf(transferContractInfo.getInputUserId());
			if (strTemp!=null && !strTemp.equals("-1"))
			{	
				m_sbWhere.append(" and A.inputuserid = "+strTemp+" ");
			}
			
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by A.inputdate desc");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 激活合同
	 * 
	 */
	public long activeContract(String ids)throws ITreasuryDAOException
	{
		long activeResult = -1;
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update CRA_TRANSFERCONTRACTFORM A set A.Statusid = '"+CRAconstant.TransactionStatus.USED+"'" +
					"where 1 = 1  and ( ");
			
			String idTemp[] = ids.split(",");//截取id字符串存放到数组中
			
			for(int i = 0 ; i<idTemp.length;i++)
			{
				buffer.append(" A.id = "+idTemp[i]);
				if(i<idTemp.length-1)
				{
					buffer.append(" or ");
				}
			}
			buffer.append(") ");
			
			String sql = buffer.toString();
			
			log.debug(sql);
			System.out.println(sql);
			prepareStatement(sql);
			executeQuery();
			activeResult = 1;
		}
		catch(ITreasuryDAOException e)
		{
			e.printStackTrace();
			return activeResult;
		}
		finally 
		{
			finalizeDAO();
		}
		
		return activeResult;
	}
	
	public Collection findInfosByIds(String ids) throws ITreasuryDAOException
	{
		Collection colResult = null;

		try {
			initDAO();
			ResultSet rs = null;
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM CRA_TRANSFERCONTRACTFORM t  WHERE t.id in ( " );
			//将传过来的id所拼成字符串拆分存入数组id[]
			String id[] = ids.split(",");
			for( int i = 0 ; i<id.length ; i++ )
			{
				buffer.append(id[i]);
				if(i<id.length-1)
				{
					buffer.append(",");
				}
			}
			buffer.append(")");
			String strSQL = buffer.toString();
			log.debug(strSQL);
			System.out.println(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			
			colResult = getDataEntitiesFromResultSet(TransferContractInfo.class);
			
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
		
		return colResult;
		
	}
	
	public Collection findLoanNoteIdsByInfo(long transferContractformId) throws ITreasuryDAOException
	{
		Collection colResult = null;

		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select t.loannoteid loannoteid from CRA_CONTRACTDETAIL t where t.transfercontractformid = " + transferContractformId);

			String strSQL = buffer.toString();
			
			log.debug(strSQL);
			System.out.println(strSQL);
			
			prepareStatement(strSQL);
			executeQuery();
			
			colResult = getDataEntitiesFromResultSet(long.class);
			
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			finalizeDAO();
		}
		
		return colResult;
		
	}
	
	public long checkcontractcode(TransferContractInfo info) throws  Exception
	{
		long returnvalue=-1;
		ResultSet rs = null;
		try {
			initDAO();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT count(*) num FROM CRA_TRANSFERCONTRACTFORM t  WHERE 1=1 " );

			buffer.append(" and t.statusid <>"+CRAconstant.TransactionStatus.DELETED);
			buffer.append(" and t.contractcode = '"+info.getContractCode()+"'");
			if(info.getId()>0)
			{
				buffer.append(" and t.id <> "+info.getId());
			}
			String strSQL = buffer.toString();
			log.debug(strSQL);
			System.out.println(strSQL);
			prepareStatement(strSQL);
			rs=executeQuery();
			while(rs.next())
			{
				returnvalue=rs.getLong("num");
			}
			
			
			
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			rs.close();
			rs=null;
			finalizeDAO();
		}
		
		return returnvalue;
		
	}
	
	/**
	 * 根据ID查询信贷转让合同(非代收收款通知单)
	 * @param tracontractId
	 * @return
	 * @throws IException
	 * @author liangli
	 */
	public ITreasuryBaseDataEntity findContractInfoById(long tracontractId) throws  ITreasuryDAOException
	{
		ITreasuryBaseDataEntity res = null;
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select b.id," +
								"b.commissionpaymenttype," +
								"b.drate," +
								"b.contractcode," +
								"b.startdate," +
								"b.enddate," +
								"b.interestcounttypeid," +
								"b.chargerate," +
								"b.chargetypeid," +
								"b.counterpartid," +
								"b.commissionpaymenttype," +
								"b.transtypeid, " +
								" (b.amount-nvl(d.sellamount,0)) amount " +
							" from CRA_TRANSFERCONTRACTFORM b ," +
							" (select c.cracontractid, sum(c.amount) sellamount " +
							" from CRA_TRANSFERNOTICEFORM c " +
							" where c.statusid in( " +CRAconstant.TransactionStatus.APPROVALED +","+CRAconstant.TransactionStatus.USED+")"+
							" and c.noticetypeid = "+CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT+
							"  group by c.cracontractid ) d " +
							" where b.id =d.cracontractid(+)" +
							" and b.id = "+tracontractId+"");

			String strSQL = buffer.toString();
			
			log.debug(strSQL);
			System.out.println(" 非代收收款单查询合同信息SQL "+strSQL);
			
			prepareStatement(strSQL);
			executeQuery();
			
			res = getDataEntityFromResultSet(TransferContractInfo.class);
			
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			finalizeDAO();
		}
		
		return res;
		
	}
	
	/**
	 * 根据ID查询信贷转让合同(只查询转让合同相关信息)
	 * @param contractID
	 * @return
	 * @throws ITreasuryDAOException
	 * @author xintan
	 */
	public TransferContractInfo findTransferContractInfoByID(long contractID) throws  ITreasuryDAOException
	{
		TransferContractInfo resultInfo = null;
		try {
			initDAO();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select contract.*, cp.counterpartcode, cp.counterpartname \n");
			sbSQL.append("   from cra_transfercontractform contract, cra_counterpart cp \n");
			sbSQL.append("  where contract.counterpartid = cp.id \n");
			sbSQL.append("    and contract.id = " + contractID + " \n");
			
			prepareStatement(sbSQL.toString());
			executeQuery();
			
			resultInfo = (TransferContractInfo)getDataEntityFromResultSet(TransferContractInfo.class);
			
		} catch (ITreasuryDAOException ide) {
			throw ide;
			
		} catch (Exception exp){
			throw new ITreasuryDAOException(exp.getMessage(), exp);
		}
		finally {
			finalizeDAO();
		}
		
		return resultInfo;
		
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
			sb.append("select count(*) num from CRA_TRANSFERCONTRACTFORM t where t.COUNTERPARTBANKID = "+id+" and t.statusid <> "+CRAconstant.TransactionStatus.DELETED);
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
	
	public long checkLoanapplyUsed(TransferContractInfo info) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) num from CRA_TRANSFERCONTRACTFORM t where t.loanapplyid = "+info.getLoanApplyId()+" and t.statusid <> "+CRAconstant.TransactionStatus.DELETED+" ");
			if(info.getId()>0)
			{
				
				sb.append(" and t.id<>"+info.getId());
			}
			
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
