package com.iss.itreasury.settlement.craftbrother.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.craftbrother.dataentity.CalcNoticeInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.CraInterestCalcInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.SumCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;

public class TransCraInterestPreDrawDAO extends ITreasuryDAO implements
		Serializable {
	private StringBuffer m_sbSelect = null;
	private StringBuffer m_sbFrom = null;
	private StringBuffer m_sbWhere = null;
	private StringBuffer m_sbOrderBy = null;
	
	public TransCraInterestPreDrawDAO() {		
		super("SETT_CRAINTERESTPREDRAW");
		this.setUseMaxID();
	}

	public TransCraInterestPreDrawDAO(String strTableName) {
		super(strTableName);
		this.setUseMaxID();
	}

	public TransCraInterestPreDrawDAO(Connection conn) {
		super(conn);
		this.setUseMaxID();
	}

	public TransCraInterestPreDrawDAO(String strTableName, Connection conn) {
		super(strTableName, conn);
		this.setUseMaxID();
	}
	
	/**
	 * @param calInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCredencesByCondition(CraInterestCalcInfo calcInfo)throws ITreasuryDAOException{
		Collection coll  = null;
		try{
			initDAO();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" select  c.id              as Id,"+" \n ");
			sqlBuffer.append("         c.sCode           as Code,"+" \n ");
			sqlBuffer.append("	       b.ID              as contractID,"+" \n ");
			sqlBuffer.append("	       b.sContractCode   as contractCode,"+" \n ");
			sqlBuffer.append("	       b.NBORROWCLIENTID as counterpartId,"+" \n ");
			sqlBuffer.append("	       c.sapplyclient    as counterpartName,"+" \n ");
			sqlBuffer.append("	       b.ninorout        as inorout,"+" \n ");
			sqlBuffer.append("	       b.ndiscounttypeid as discountType,"+" \n ");
			sqlBuffer.append("	       b.dtdiscountdate  as transDate,"+" \n ");
			sqlBuffer.append("	       a.dtrepurchasedate as repurchaseDate,"+" \n ");
			sqlBuffer.append("	       decode((select max(t.dtinterestend) from sett_crainterestpredraw t where t.nnoticeid = c.id and t.nstatusid = " + SETTConstant.TransactionStatus.CHECK + "),null,b.dtdiscountdate,b.dtdiscountdate,(b.dtdiscountdate+1),(select max(t.dtinterestend+1) from sett_crainterestpredraw t where t.nnoticeid = c.id and t.nstatusid = " + SETTConstant.TransactionStatus.CHECK + "))  as interestStartDate,"+" \n ");
			sqlBuffer.append("	       to_date('" + DataFormat.formatDate(calcInfo.getEndInterestDate()) + "','YYYY-MM-DD') as interestEndDate,"+" \n ");
			sqlBuffer.append("	       c.mamount         as amount,"+" \n ");
			sqlBuffer.append("	       b.mdiscountrate   as rate,"+" \n ");
			sqlBuffer.append("	       (select sum(t.mpredrawinterest ) from sett_crainterestpredraw t where t.nnoticeid = c.id and t.nstatusid = " + SETTConstant.TransactionStatus.CHECK + ") as summpredrawinterest,"+" \n ");
			sqlBuffer.append("          c.minterest credenceInterest \n ");
			sqlBuffer.append("   from cra_loanform a, loan_contractform b, loan_discountcredence c,sec_counterpart d"+" \n ");
			sqlBuffer.append("  where b.nloanid = a.id"+" \n ");
			sqlBuffer.append("    and b.NBORROWCLIENTID = d.ID(+)"+" \n ");
			sqlBuffer.append("    and b.id = c.ncontractid"+" \n ");
			sqlBuffer.append("    and b.nTypeID = " + LOANConstant.LoanType.ZTX +" \n ");
			sqlBuffer.append("    and c.ntypeid = " + LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE);
			sqlBuffer.append("    and b.nstatusid in (" + LOANConstant.ContractStatus.FINISH + "," + LOANConstant.ContractStatus.ACTIVE +")"+" \n ");
			
			

			if(calcInfo.getSubTransactionTypeId() > 0){
				switch((int)calcInfo.getSubTransactionTypeId()){
					case (int) SETTConstant.SubTransactionType.BREAK_INVEST:
						sqlBuffer.append("    and b.ninorout = " + LOANConstant.TransDiscountInOrOut.IN +" \n ");
						sqlBuffer.append("    and b.ndiscounttypeid = " + LOANConstant.TransDiscountType.BUYBREAK +" \n ");
						break;
					case (int) SETTConstant.SubTransactionType.REPURCHASE_INVEST:
						sqlBuffer.append("    and b.ninorout = " + LOANConstant.TransDiscountInOrOut.IN +" \n ");
						sqlBuffer.append("    and b.ndiscounttypeid = " + LOANConstant.TransDiscountType.REPURCHASE +" \n ");
						break;
					case (int) SETTConstant.SubTransactionType.BREAK_NOTIFY:
						sqlBuffer.append("    and b.ninorout = " + LOANConstant.TransDiscountInOrOut.OUT +" \n ");
						sqlBuffer.append("    and b.ndiscounttypeid = " + LOANConstant.TransDiscountType.BUYBREAK +" \n ");
						break;
					case (int) SETTConstant.SubTransactionType.REPURCHASE_NOTIFY:
						sqlBuffer.append("    and b.ninorout = " + LOANConstant.TransDiscountInOrOut.OUT +" \n ");
						sqlBuffer.append("    and b.ndiscounttypeid = " + LOANConstant.TransDiscountType.REPURCHASE +" \n ");
						break;
					default:
						break;
				}
				
			}
			if(calcInfo.getCounterpartId() > 0){
				sqlBuffer.append("    and b.NBORROWCLIENTID = " + calcInfo.getCounterpartId());
			}
			if(calcInfo.getContractCodeFrom() != null && !calcInfo.getContractCodeFrom().equals("")){
				sqlBuffer.append("    and b.scontractcode >= '" + calcInfo.getContractCodeFrom() + "'"+" \n ");
			}
			if(calcInfo.getContractCodeTo() != null && !calcInfo.getContractCodeTo().equals("")){
				sqlBuffer.append("    and b.scontractcode <= '" + calcInfo.getContractCodeTo() + "'"+" \n ");
			}
			if(calcInfo.getCredenceCodeFrom() != null && !calcInfo.getCredenceCodeFrom().equals("")){
				sqlBuffer.append("    and c.scode >= '" + calcInfo.getCredenceCodeFrom() + "'"+" \n ");
			}
			if(calcInfo.getCredenceCodeTo() != null && !calcInfo.getCredenceCodeTo().equals("")){
				sqlBuffer.append("    and c.scode <= '" + calcInfo.getCredenceCodeTo() + "'"+" \n ");
			}
			sqlBuffer.append("    and c.nstatusid in (" + LOANConstant.DiscountCredenceStatus.CHECK + "," + LOANConstant.DiscountCredenceStatus.USED + ")"+" \n ");
			sqlBuffer.append("    and b.nOfficeID = " + calcInfo.getOfficeId() + " \n ");
			sqlBuffer.append("    and b.nCurrencyID = " + calcInfo.getCurrencyId() + " \n ");
			sqlBuffer.append("  order by b.sContractCode, c.scode");
			log.info("转贴现计提查找转贴现凭证：SQL={"+sqlBuffer.toString()+"}");
			transPS = prepareStatement(sqlBuffer.toString());
			transRS = transPS.executeQuery();
			coll = this.getDataEntitiesFromResultSet(CalcNoticeInfo.class);
		}catch(Exception e){
			throw new ITreasuryDAOException("Gen_E001", e);
		}finally{
			finalizeDAO();
		}
		return coll;
	}
	private void getTransDiscountPerDrawInterestSQL(TransCraInterestPreDrawInfo info){
		//select 
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct t.*,c.id ncontractid,c.scontractcode scontractcode,d.scode snoticecode,d.mamount,e.name scounterpartname ");
		//from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_crainterestpredraw t, sett_glentry g, loan_contractform c, loan_discountcredence d, sec_counterpart e ");
		//where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" t.stransno = g.stransno ");
		m_sbWhere.append(" and t.nnoticeid = d.id ");
		m_sbWhere.append(" and d.ncontractid = c.id ");
		m_sbWhere.append(" and t.ncounterpartid = e.id(+) ");
		m_sbWhere.append(" and (g.npoststatusid <> 1 or g.npoststatusid is null) ");
		//m_sbWhere.append(" and (t.dtinterestend + 1) = to_date('" + com.iss.itreasury.util.DataFormat.formatDate(info.getDtmake()) + "','YYYY-MM-DD') ");
		m_sbWhere.append(" and t.nstatusid = " + SETTConstant.TransactionStatus.CHECK);
		if(!info.getQueryDate().equals(""))
		{
			m_sbWhere.append(" and to_char(t.dtmake,'yyyy-mm-dd')='"+info.getQueryDate()+"'");
		}
		//Order by 
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		switch ((int) info.getOrderIndex())
		{
			default :
				m_sbOrderBy.append(" order by t.dtmake desc ,t.stransno" + strDesc + " \n");
				break;
		}

		log.info("查找计提记录【"+DataFormat.formatDate(info.getDtinterestend())+"】：SQL=select"+m_sbSelect.toString()
			+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	/**
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public PageLoader searchTransInterestPerDraw(TransCraInterestPreDrawInfo info)throws Exception {
		Collection coll = null;
		getTransDiscountPerDrawInterestSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.craftbrother.dataentity.TransCraInterestPreDrawInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	/**
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public SumCraInterestPreDrawInfo getSumCraPreDrawInterest(TransCraInterestPreDrawInfo info) throws ITreasuryDAOException{
		SumCraInterestPreDrawInfo sumInfo = new SumCraInterestPreDrawInfo();
		//
		String strSelect = "";
		String sql = "";
		try
		{
			initDAO();
			getTransDiscountPerDrawInterestSQL(info);
			// select 
			strSelect = " select sum(round(mamount,2)) balanceSum,sum(round(mpredrawinterest ,2)) interestSum from ( \n";
			sql = strSelect + " select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString()+")";
			log.info("转贴现计提查找转贴利息计提交易汇总信息：SQL={"+sql+"}");
			transPS = prepareStatement(sql);
			transRS = transPS.executeQuery();
			if (transRS.next())
			{
				sumInfo.setSumBalance(transRS.getDouble("balanceSum"));
				sumInfo.setSumInterest(transRS.getDouble("interestSum"));
			}
		}
		catch (Exception exp)
		{
			throw new ITreasuryDAOException("Gen_E001", exp);
		}
		finally
		{
			finalizeDAO();
		}
		return sumInfo;
	}
	
	public String validateTransaction(TransCraInterestPreDrawInfo info) throws ITreasuryDAOException{
		String transNo = "";
		try
		{
			StringBuffer sql = new StringBuffer();
			initDAO();
			sql.append(" select max(s.stransno) transNo from SETT_CRAINTERESTPREDRAW s, ");
			sql.append(" (select draw.dtinterestend, draw.nnoticeid from SETT_CRAINTERESTPREDRAW draw ");
			sql.append(" where draw.id ="+info.getId());
			sql.append(" and draw.nstatusid!="+SETTConstant.TransactionStatus.DELETED+") draw");
			sql.append(" where s.id !="+info.getId());
			sql.append(" and s.nnoticeid=draw.nnoticeid ");
			sql.append(" and s.nstatusid !="+SETTConstant.TransactionStatus.DELETED);
			sql.append(" and s.dtintereststart>draw.dtinterestend ");
			sql.append(" and s.dtmake = to_date('"+Env.getSystemDateString(info.getNofficeid(), info.getNcurrencyid())+"','yyyy-mm-dd')");
			log.info("校验sql="+sql);
			transPS = prepareStatement(sql.toString());
			transRS = transPS.executeQuery();		
			while(transRS.next())
			{
				System.out.println(transRS);
				transNo = transRS.getString("transNo");
			}

		}catch(Exception exp)
		{
			throw new ITreasuryDAOException("Gen_E001", exp);
		}
		finally
		{
			finalizeDAO();
		}		
		return transNo==null?"":transNo;
	}
	
	public void updateDraftAmortization(long AmortizationID) throws Exception 
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			initDAO();
			sql.append(" update DraftAmortization d ");
			sql.append(" set d.nstatus ="+SETTConstant.TransactionStatus.DELETED);
			sql.append(" where d.amortizationid ="+AmortizationID);
			log.info("删除票据sql="+sql);
			transPS = prepareStatement(sql.toString());
			transPS.execute();
		}catch(Exception e)
		{
			throw new Exception("删除票据摊销记录失败!", e);
		}
		finally
		{
			finalizeDAO();
		}		
	}
	
}
