/**
 * Created on 2011-7-18
 * Author gdlian
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */

package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransLiquidationInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransLiquidationDetailInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Env;

public class QTransLiquidation extends BaseQueryObject {
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QTransLiquidation()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	
	/**
	 * 获得交易金额合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getAmountSumForQuery(QueryTransLiquidationInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{
			getTransLiquidationSQLForQuery(info);
			// select 
			strSelect = " select sum(round(mAmount,2)) mAmountSum \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();

			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("mAmountSum");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return dReturn;
	}
	
	/**
	 * 获得总记录数
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getCountForQuery(QueryTransLiquidationInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		long lReturn = -1; //
		//
		try
		{
			getTransLiquidationSQLForQuery(info);
			// select 
			strSelect = " select count(*) count \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("count");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lReturn;
	}

	/**
	 * 产生清算查询SQL 
	 * @param info
	 */
	public void getTransLiquidationSQLForQuery(QueryTransLiquidationInfo info)
	{
		m_sbSelect = new StringBuffer();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String queryDate = dateFormat.format(info.getDTexecute());
		// select 
		m_sbSelect.append(" ID,NOFFICEID,NCURRENCYID,STRANSNO,NTRANSACTIONTYPEID, \n");
		m_sbSelect.append(" NPAYACCOUNTID,NPAYOFFICEID,NRECEIVEACCOUNTID,NRECEIVEOFFICEID,NPARENTOFFICEID, \n");
		m_sbSelect.append(" MAMOUNT,DTINTERESTSTART,DTEXECUTE,SABSTRACT,STATUSID  \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_liquidation \n");
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" statusid>0 ");
		m_sbWhere.append(" and dtexecute= to_date('" + queryDate + "','yyyy-mm-dd')");
		m_sbWhere.append(" and ncurrencyid= "+info.getCurrencyID());
		if( info.getOfficeID() != Env.getHQOFFICEID() )
		{
			m_sbWhere.append(" and (npayofficeid= " + info.getOfficeID() + " or nreceiveofficeid= "+ info.getOfficeID() + ")");
			
		}
		m_sbOrderBy=new  StringBuffer();
		m_sbOrderBy.append("order by STRANSNO");
		log.print("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString()
				+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	
	/**
	 * 产生付金清算明细信息查询SQL 
	 * @param info
	 */
	public void getTransLiquidationSQLForQueryAll(QueryTransLiquidationDetailInfo info)
	{
		m_sbSelect = new StringBuffer();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String queryDate = dateFormat.format(info.getDTexecute());
		// select 
		m_sbSelect.append(" c.stransno,c.dtexecute,b.sdebit,b.scredit,c.mamount  \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_liquidationdetail b,sett_liquidation c \n");
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" b.nstatusid > 0  ");
		m_sbWhere.append(" and c.statusid > 0 ");
		m_sbWhere.append(" and b.nliquidationid = c.id ");
		m_sbWhere.append(" and c.dtexecute =to_date('"+ queryDate+ "','yyyy-mm-dd')" );
		m_sbWhere.append(" and c.ncurrencyid= "+info.getCurrencyID());
		m_sbWhere.append(" and b.nofficeid= "+info.getOfficeID());
		//orderby
		m_sbOrderBy=new  StringBuffer();
		m_sbOrderBy.append("order by c.STRANSNO");	
		log.print("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString()
				+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	
	
	/**
	 * 
	 * 机构间清算信息查询使用 add by gdlian 2011-07-19
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryTransLiquidationInfoForQuery(QueryTransLiquidationInfo info) throws Exception
	{
		getTransLiquidationSQLForQuery(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.paraminfo.QueryTransLiquidationInfo",
			null);
		System.out.println("pageLoader:"+pageLoader);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		System.out.println("pageLoader:"+pageLoader);
		return pageLoader;
	}
	/**
	 * 
	 * 机构间备付金清算明细信息查询使用 add by gdlian 2011-07-19
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryTransLiquidationInfoForQueryAll(QueryTransLiquidationDetailInfo info) throws Exception
	{
		getTransLiquidationSQLForQueryAll(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.paraminfo.QueryTransLiquidationDetailInfo",
			null);
		System.out.println("pageLoader:"+pageLoader);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		System.out.println("pageLoader:"+pageLoader);
		return pageLoader;
	}
}
