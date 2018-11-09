package com.iss.itreasury.settlement.mergedVoucher.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.matureremind.dataentity.MatureRemindInfo;
import com.iss.itreasury.settlement.mergedVoucher.dataentity.MergedVoucherInfo;
import com.iss.itreasury.settlement.setting.dataentity.AccountDeadLineInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class Sett_mergedVouchersDAO extends SettlementDAO{
	private StringBuffer strSelectSQLBuffer = null;
	public Sett_mergedVouchersDAO()
	{
		super.strTableName = "sett_mergedVouchers";
	}
	public Sett_mergedVouchersDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_mergedVouchers";
	}
	
	/**
	 * 根据批次号查询信息
	 */
	public Collection findBySbatchno(String sbatchno) throws Exception
	{
		Collection findBySbatchnoList = new ArrayList();
		//hy
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;		
		
		try
		{
			//链接数据库
			conn = getConnection();						
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append("SELECT * FROM sett_mergedVouchers WHERE sbatchno = ? ");
			ps = conn.prepareStatement(sbSQL.toString());		
			int nIndex = 1;
			ps.setString( nIndex++ ,sbatchno);
			rs = ps.executeQuery();	
			
			while (rs.next())
			{	
				
				MergedVoucherInfo voucherInfo = new MergedVoucherInfo();
				voucherInfo.setId(rs.getLong("id"));
				voucherInfo.setNOfficeId(rs.getLong("NOFFICEID"));
				voucherInfo.setNCurrencyId(rs.getLong("NCURRENCYID"));
				voucherInfo.setSSubjectCode(rs.getString("SSUBJECTCODE"));
				voucherInfo.setSBatchNo(rs.getString("SBATCHNO"));
				voucherInfo.setNTransactionTypeId(rs.getLong("NTRANSACTIONTYPEID"));
				voucherInfo.setNTransDirection(rs.getLong("NTRANSDIRECTION"));
				voucherInfo.setMAmount(rs.getDouble("MAMOUNT"));
				voucherInfo.setDtExecute(rs.getTimestamp("DTEXECUTE"));
				voucherInfo.setDtIntereststStart(rs.getTimestamp("DTINTERESTSTART"));
				voucherInfo.setSAbstract(rs.getString("SABSTRACT"));
				voucherInfo.setNPostStatusId(rs.getLong("NPOSTSTATUSID"));
				findBySbatchnoList.add (voucherInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return findBySbatchnoList.size() > 0 ? findBySbatchnoList : null ;
	}
	
	/**
	 * 新增结息提醒设置信息，将info里的信息insert到表sett_mergedVouchers里
	 */
	public long add(MergedVoucherInfo info) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		try
		{
			conn = getConnection();
			strSQL = new StringBuffer();
			strSQL.append("INSERT INTO  \n");
			strSQL.append(strTableName + " \n");
			strSQL.append("\n (ID, \n");
			strSQL.append("nOfficeID,\n");
			strSQL.append("nCurrencyID,\n");
			strSQL.append("sSubjectCode,\n");
			strSQL.append("sBatchNo,\n");
			strSQL.append("nTransactionTypeID,\n");
			strSQL.append("nTransDirection,\n ");
			strSQL.append("mAmount,\n");
			strSQL.append("dtExecute,\n ");
			strSQL.append("dtInterestStart,\n");
			strSQL.append("sAbstract,NSTATUSID)\n");	
			strSQL.append(" VALUES \n");
			strSQL.append(" ((SELECT nvl(MAX(id)+1,1) id FROM sett_mergedVouchers),?,?,?,?,?,?,?,?,?,?,?) ");
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			//ps.setLong( nIndex++ ,info.getId());
			ps.setLong( nIndex++ , info.getNOfficeId());
			ps.setLong( nIndex++ ,info.getNCurrencyId());
			ps.setString( nIndex++ ,info.getSSubjectCode());
			ps.setString( nIndex++ , info.getSBatchNo());
			ps.setLong( nIndex++ , info.getNTransactionTypeId());
			ps.setLong( nIndex++ , info.getNTransDirection());
			ps.setDouble( nIndex++ , info.getMAmount());
			ps.setTimestamp( nIndex++ , info.getDtExecute());
			ps.setTimestamp( nIndex++ , info.getDtIntereststStart());
			ps.setString( nIndex++ , info.getSAbstract());
			ps.setLong( nIndex++ , info.getNStatusId());
			System.out.print("strSQL===:"+strSQL);
			lret = ps.executeUpdate();

			cleanup(ps);
			cleanup(conn);
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lret;
	}

	
	/**
	 * 根据批次号来删除记录（物理删除）
	 */
	public long delBySbatchno(String sbatchno) throws Exception
	{
		long lret = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		try
		{
			conn = getConnection();
		
			strSQL = new StringBuffer();
			strSQL.append("DELETE FROM sett_mergedVouchers \n");
			strSQL.append(" WHERE SBATCHNO = ? \n");
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			ps.setString( nIndex++ , sbatchno);
			lret = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lret;
	}
	
	/**
	 * 根据条件查询所有已经合并的凭证，返回的是pageloader
	 */
	public PageLoader findByAll(MergedVoucherInfo form) throws Exception  {
		StringBuffer m_sbSelect = new StringBuffer();
		StringBuffer m_sbFrom = new StringBuffer();
		StringBuffer m_sbWhere = new StringBuffer();
		StringBuffer m_sbOrderBy = new StringBuffer();
		m_sbWhere.append(" 1=1 ");
		if (form.getNOfficeId()> 0) {
			m_sbWhere.append(" and a.NOFFICEID=" + form.getNOfficeId());
		}
		if (form.getNCurrencyId()> 0) {
			m_sbWhere.append(" and a.NCURRENCYID=" + form.getNCurrencyId());
		}
		if (form.getSBatchNoStart() != null && form.getSBatchNoStart().trim().length() > 0){
			m_sbWhere.append(" and a.SBATCHNO>='" + form.getSBatchNoStart() + "'");
		}
		if (form.getSBatchNoEnd() != null && form.getSBatchNoEnd().trim().length() > 0){
			m_sbWhere.append(" and a.SBATCHNO<='" + form.getSBatchNoEnd() + "'");
		}
		if (form.getNTransactionTypeId() > 0){
			m_sbWhere.append(" and a.NTRANSACTIONTYPEID=" + form.getNTransactionTypeId());
		}
		if (form.getDtExecute() != null){
			m_sbWhere.append(" and a.DTEXECUTE=to_date('"+ DataFormat.getDateString(form.getDtExecute()) + "','yyyy-mm-dd')");
		}else{
			if(form.getTsExecuteStartDate() != null){
				m_sbWhere.append(" and a.DTEXECUTE>=to_date('"+ DataFormat.getDateString(form.getTsExecuteStartDate()) + "','yyyy-mm-dd')");
			}
			if(form.getTsExecuteEndDate() !=null){
				m_sbWhere.append(" and a.DTEXECUTE<=to_date('"+ DataFormat.getDateString(form.getTsExecuteEndDate()) + "','yyyy-mm-dd')");
			}
		}
		
		m_sbSelect.append(" ID,NOFFICEID,NCURRENCYID,SSUBJECTCODE,SBATCHNO,NTRANSACTIONTYPEID,NTRANSDIRECTION,MAMOUNT,DTEXECUTE,DTINTERESTSTART,SABSTRACT,NPOSTSTATUSID ");
		m_sbFrom.append(" sett_mergedvouchers a ");
		m_sbOrderBy.append(" order by SBATCHNO \n");
		System.out.println("select "+m_sbSelect+" from "+m_sbFrom +" where "+m_sbWhere);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.mergedVoucher.dataentity.MergedVoucherInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 根据条件查询所有已经合并的凭证，返回的是集合
	 */
	public Collection findAllByMergedInfo(MergedVoucherInfo form) throws Exception  {
		StringBuffer m_sbSelect = new StringBuffer();
		StringBuffer m_sbFrom = new StringBuffer();
		StringBuffer m_sbWhere = new StringBuffer();
		StringBuffer m_sbOrderBy = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		Collection findAllByConditionList = new ArrayList();
		try{
//			链接数据库
			conn = getConnection();
			m_sbSelect.append(" ID,NOFFICEID,NCURRENCYID,SSUBJECTCODE,SBATCHNO,NTRANSACTIONTYPEID,NTRANSDIRECTION,MAMOUNT,DTEXECUTE,DTINTERESTSTART,SABSTRACT,NPOSTSTATUSID ");
			m_sbFrom.append(" sett_mergedvouchers a ");
			m_sbWhere.append(" 1=1 ");
			if (form.getNOfficeId()> 0) {
				m_sbWhere.append(" and a.NOFFICEID=" + form.getNOfficeId());
			}
			if (form.getNCurrencyId()> 0) {
				m_sbWhere.append(" and a.NCURRENCYID=" + form.getNCurrencyId());
			}
			if (form.getSBatchNoStart() != null && form.getSBatchNoStart().trim().length() > 0){
				m_sbWhere.append(" and a.SBATCHNO>='" + form.getSBatchNoStart() + "'");
			}
			if (form.getSBatchNoEnd() != null && form.getSBatchNoEnd().trim().length() > 0){
				m_sbWhere.append(" and a.SBATCHNO<='" + form.getSBatchNoEnd() + "'");
			}
			if (form.getNTransactionTypeId() > 0){
				m_sbWhere.append(" and a.NTRANSACTIONTYPEID=" + form.getNTransactionTypeId());
			}
			if (form.getDtExecute() != null){
				m_sbWhere.append(" and a.DTEXECUTE=to_date('"+ DataFormat.getDateString(form.getDtExecute()) + "','yyyy-mm-dd')");
			}else{
				if(form.getTsExecuteStartDate() != null){
					m_sbWhere.append(" and a.DTEXECUTE>=to_date('"+ DataFormat.getDateString(form.getTsExecuteStartDate()) + "','yyyy-mm-dd')");
				}
				if(form.getTsExecuteEndDate() !=null){
					m_sbWhere.append(" and a.DTEXECUTE<=to_date('"+ DataFormat.getDateString(form.getTsExecuteEndDate()) + "','yyyy-mm-dd')");
				}
			}
			m_sbOrderBy.append(" order by SBATCHNO \n");
			sql = " select "+m_sbSelect+" from "+m_sbFrom +" where "+m_sbWhere+m_sbOrderBy;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				MergedVoucherInfo voucherInfo = new MergedVoucherInfo();
				voucherInfo.setId(rs.getLong("id"));
				voucherInfo.setNOfficeId(rs.getLong("NOFFICEID"));
				voucherInfo.setNCurrencyId(rs.getLong("NCURRENCYID"));
				voucherInfo.setSSubjectCode(rs.getString("SSUBJECTCODE"));
				voucherInfo.setSBatchNo(rs.getString("SBATCHNO"));
				voucherInfo.setNTransactionTypeId(rs.getLong("NTRANSACTIONTYPEID"));
				voucherInfo.setNTransDirection(rs.getLong("NTRANSDIRECTION"));
				voucherInfo.setMAmount(rs.getDouble("MAMOUNT"));
				voucherInfo.setDtExecute(rs.getTimestamp("DTEXECUTE"));
				voucherInfo.setDtIntereststStart(rs.getTimestamp("DTINTERESTSTART"));
				voucherInfo.setSAbstract(rs.getString("SABSTRACT"));
				voucherInfo.setNPostStatusId(rs.getLong("NPOSTSTATUSID"));
				findAllByConditionList.add(voucherInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch(Exception exp){
			exp.printStackTrace();
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return findAllByConditionList.size() > 0 ? findAllByConditionList : null ;
	}
	
	/**
	 * 判断合并的凭证是否正常，返回合并凭证后的记录数
	 */
	public long isMergedSuccess(String sbatchno) throws Exception{
		long  lMergedCount = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			strSQL = new StringBuffer();
			strSQL.append("select count(*) mergedCount\n");
			strSQL.append("from  \n");
			strSQL.append("(select sum(a.mamount) mamount,a.ssubjectcode ssubjectcode,a.ntransdirection ntransdirection \n");
			strSQL.append("from sett_glentry a \n");
			strSQL.append("where a.sbatchno=? \n");
			strSQL.append("and a.nstatusid = "+SETTConstant.EntryStatus.MERGED+" \n");
			strSQL.append("group by a.ssubjectcode,a.ntransdirection) bb, \n");
			strSQL.append("(select b.mamount mamount,b.ssubjectcode ssubjectcode,b.ntransdirection ntransdirection \n");
			strSQL.append("from sett_mergedvouchers b where b.sbatchno=?) aa \n");
			strSQL.append("where aa.mamount = bb.mamount \n");
			strSQL.append("and aa.ssubjectcode = bb.ssubjectcode \n");
			strSQL.append("and aa.ntransdirection = bb.ntransdirection \n");
			ps = conn.prepareStatement(strSQL.toString());
			int nIndex = 1;
			ps.setString( nIndex++ , sbatchno);
			ps.setString( nIndex++ , sbatchno);
			rs = ps.executeQuery();
			while (rs.next())
			{
				lMergedCount = rs.getLong("mergedCount");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lMergedCount;
	}
}
