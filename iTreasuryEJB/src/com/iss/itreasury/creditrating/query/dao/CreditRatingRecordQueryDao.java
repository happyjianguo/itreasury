package com.iss.itreasury.creditrating.query.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.creditrating.query.dataentity.CreditRatingRecordQueryInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.creditrating.util.NameRef;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

public class CreditRatingRecordQueryDao extends ITreasuryDAO{
	private StringBuffer m_sbSelect  = null;
	
	private StringBuffer m_sbFrom    = null;
	
	private StringBuffer m_sbWhere   = null;
	
	private StringBuffer m_sbOrderBy = null;
	

	public PageLoader queryCreditRating(CreditRatingRecordQueryInfo queryInfo)throws Exception
	{
		    queryCreditRatingSQL(queryInfo);
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	        pageLoader.initPageLoader(new AppContext(),
					                  m_sbFrom.toString(),
					                  m_sbSelect.toString(),
					                  m_sbWhere.toString(),
					                  (int) Constant.PageControl.CODE_PAGELINECOUNT,
					                  "com.iss.itreasury.creditrating.query.dataentity.CreditRatingRecordQueryResultInfo",
					                  null);
	        pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
        return pageLoader;
	}
	
	public void queryCreditRatingSQL(CreditRatingRecordQueryInfo creditRatingRecordQueryInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" t.ID AS ID,t.code AS RATINGCODE,t.crertType crertType,t.clientId AS CLIENTID,t1.SCODE AS CLIENTNO,t1.sName AS CLIENTNAME,\n"); 
		m_sbSelect.append(" t.ratingtype AS RATINGTYPE,t.ratingprojectid AS RATINGPROJECTID,t.ratingprojectname AS RATINGPROJECTNAME,\n");
		m_sbSelect.append(" t.startdate AS STATEDATE,t.enddate AS ENDDATE,t.ratingnumeric AS RATINGNUMERIC,\n");
		m_sbSelect.append(" t.ratingresult  AS RATINGRESULT,\n");
		m_sbSelect.append(" t.ratingDate AS RATINGDATE,t.statusId AS STATE,\n");
		m_sbSelect.append(" t.officeid AS OFFICEID,t.currencyid AS CURRENCYID,t.inputuserId AS INPUTUSERID,t.inputdate AS INPUTDATE \n");

		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( \n");
		m_sbFrom.append(" select cc.id ID,cc.ratingcode code,cc.currencyid currencyid,cc.officeid officeid,cc.clientid clientId,cc.ratingtype ,cc.ratingprojectid,cc.ratingprojectname,cc.ratingnumeric ,cc.ratingresult,cc.statedate startdate,cc.enddate enddate, cc.inputuserid inputuserId,cc.inputdate inputdate,cc.state statusId,"+Constant.ApprovalAction.CRERT_CREDITRATING+" crertType,cc1.ratingdate  \n");
		m_sbFrom.append(" from crert_creditrating cc left join crert_creditratingdetail cc1  on cc.id=cc1.creditratingid and cc1.state!="+CreRtConstant.CreRtStatus.DELETE+" \n");
		m_sbFrom.append("  union \n");
		m_sbFrom.append(" select ccr.id ID,ccr.ratingcode code,ccr.currencyid currencyid,ccr.officeid officeid,ccd.clientid clientId,ccd.ratingtype ,ccd.ratingprojectid,ccd.ratingprojectname,ccd.ratingnumeric ,ccr.revaluedresult ratingresult,ccd.statedate startdate,ccd.enddate enddate,ccr.inputuserid inputuserId,ccr.inputdate inputdate,ccr.state statusId,"+Constant.ApprovalAction.CRERT_CREDITEVALUTION+" crertType, ccr.revalueddate ratingdate \n");
		m_sbFrom.append("  from crert_creditratingdetail ccd , crert_creditratingrevalued ccr \n");
		m_sbFrom.append(" where ccd.id= ccr.CREDITRATINGID \n");
		m_sbFrom.append(" union \n");
		m_sbFrom.append(" select         ccc.id ID,ccc.ratingcode code,ccc.currencyid currencyid,ccc.officeid officeid,ccd.clientid clientId, ccd.ratingtype ,ccd.ratingprojectid,ccd.ratingprojectname, ccd.ratingnumeric ,ccd. ratingresult,ccd.statedate startdate,ccd.enddate enddate,ccc.inputuserid inputuserId,ccc.inputdate inputdate,ccc.state statusId,"+Constant.ApprovalAction.CRERT_BECOMINGINVALID+" crertType,ccc.canceldate ratingdate \n");
		m_sbFrom.append(" from crert_creditratingdetail ccd , crert_creditratingcancel ccc \n");
		m_sbFrom.append("  where ccd.id= ccc.RATINGPROJECTID \n");
		m_sbFrom.append(" ) t ,CLIENT t1 \n");
		
		
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" t.clientId = t1.id ");
		
		if(creditRatingRecordQueryInfo.getClientID() > 0)
		{
		   m_sbWhere.append(" AND t1.ID = " + creditRatingRecordQueryInfo.getClientID());
		}
		if(creditRatingRecordQueryInfo.getRatingProjectID() > 0)
		{
		   m_sbWhere.append(" AND t.ratingprojectid = " + creditRatingRecordQueryInfo.getRatingProjectID());
		}
		if(creditRatingRecordQueryInfo.getCrertType() > 0)
		{
		   m_sbWhere.append(" AND t.crertType  = " + creditRatingRecordQueryInfo.getCrertType());
		}
		if(creditRatingRecordQueryInfo.getRatingType() > 0)
		{
		   m_sbWhere.append(" AND t.ratingtype  = " + creditRatingRecordQueryInfo.getRatingType());
		}
		if(!creditRatingRecordQueryInfo.getQueryDate().equals(""))
		{
			m_sbWhere.append(" AND t.startdate <= TO_DATE('" + creditRatingRecordQueryInfo.getQueryDate() + "','yyyy-mm-dd')");
			m_sbWhere.append(" AND t.enddate   >= TO_DATE('" + creditRatingRecordQueryInfo.getQueryDate() + "','yyyy-mm-dd')");
		}
		if(creditRatingRecordQueryInfo.getState() > 0)
		{

			m_sbWhere.append(" AND  t.statusId = "+creditRatingRecordQueryInfo.getState()+" ");
		}
		else
		{
			m_sbWhere.append(" AND  t.statusId <>"+CreRtConstant.CreRtStatus.DELETE);
		}
		if(creditRatingRecordQueryInfo.getOfficeID() > 0)
		{
		   m_sbWhere.append(" AND t.OFFICEID = " + creditRatingRecordQueryInfo.getOfficeID());
		}
		if(creditRatingRecordQueryInfo.getCurrencyID() > 0)
		{
		   m_sbWhere.append(" AND t.CURRENCYID = " + creditRatingRecordQueryInfo.getCurrencyID());
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" ORDER BY t1.SCODE,t.startdate,t.ratingDate \n");
		
	}
	
	/**
	 * 通过信用评级ID,得到其有效的信用重估记录
	 * @param ratingID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCreditratingreValuedByRatingID(long ratingID)throws ITreasuryDAOException
	{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select t2.id ,t2.revalueddate ,t2.revaluedresult,t2.revaluedmark,t2.INPUTUSERID,t2.INPUTDATE \n");
			buffer.append("from crert_creditratingdetail t1, crert_creditratingrevalued t2 \n");
			buffer.append(" where t2.creditratingid = t1.id \n");
			buffer.append("  and t1.state != "+CreRtConstant.CreRtStatus.DELETE+" \n");
			buffer.append("  and t2.state = "+CreRtConstant.CreRtStatus.APPROVALED+" \n");
			buffer.append(" and t1.creditratingid=? \n");
			buffer.append(" order by t2.revalueddate asc \n");
			log.print("根据评级ID,得到重估记录明细SQL:"+buffer.toString());
			prepareStatement(buffer.toString());	
			transPS.setLong(1, ratingID);
			executeQuery();
			Collection coll = new ArrayList();
			while(transRS.next()){
				String strtemp = "";
				String revalueddate = transRS.getTimestamp("revalueddate")!=null?DataFormat.getDateString(transRS.getTimestamp("revalueddate")):"&nbsp;";
				String revaluedresult = transRS.getString("revaluedresult")==null?"&nbsp;":transRS.getString("revaluedresult");
				String revaluedmark = transRS.getString("revaluedmark")==null?"&nbsp;":transRS.getString("revaluedmark");
				String inputUserName = transRS.getLong("INPUTUSERID")==0?"&nbsp;":NameRef.getUserNameByID(transRS.getLong("INPUTUSERID"));
				String inputDate = transRS.getTimestamp("INPUTDATE")==null?"&nbsp;":DataFormat.getDateString(transRS.getTimestamp("INPUTDATE"));
				strtemp = transRS.getLong("id")+"::"+revalueddate+"::"+revaluedresult+"::"+revaluedmark+"::"+inputUserName+"::"+inputDate;
				coll.add(strtemp);
			}
				return coll;
			}catch(SQLException sqle){
				sqle.printStackTrace();
				throw new ITreasuryDAOException("查询重估记录明细报错",sqle);
			}finally{
				finalizeDAO();
			}
			
		
	}
	
	/**
	 * 通过信用评级ID,得到有效的作废记录
	 * @param ratingID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCreditratingCancelByRatingID(long ratingID)throws ITreasuryDAOException
	{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select t2.id ,t2.CANCELDATE ,t2.CANCELMARK,t2.INPUTUSERID,t2.INPUTDATE \n");
			buffer.append("from crert_creditratingdetail t1, crert_creditratingcancel t2 \n");
			buffer.append(" where t2.ratingprojectid = t1.id \n");
			buffer.append("  and t1.state != "+CreRtConstant.CreRtStatus.DELETE+" \n");
			buffer.append("  and t2.state = "+CreRtConstant.CreRtStatus.APPROVALED+" \n");
			buffer.append(" and t1.creditratingid=? \n");
			buffer.append(" order by t2.CANCELDATE asc \n");
			log.print("根据评级ID,得到作废记录明细SQL:"+buffer.toString());
			prepareStatement(buffer.toString());	
			transPS.setLong(1, ratingID);
			executeQuery();
			Collection coll = new ArrayList();
			while(transRS.next()){
				String strtemp = "";
				String CANCELDATE = transRS.getTimestamp("CANCELDATE")!=null?DataFormat.getDateString(transRS.getTimestamp("CANCELDATE")):"&nbsp;";
				String CANCELMARK = transRS.getString("CANCELMARK")==null?"&nbsp;":transRS.getString("CANCELMARK");
				String inputUserName = transRS.getLong("INPUTUSERID")==0?"&nbsp;":NameRef.getUserNameByID(transRS.getLong("INPUTUSERID"));
				String inputDate = transRS.getTimestamp("INPUTDATE")==null?"&nbsp;":DataFormat.getDateString(transRS.getTimestamp("INPUTDATE"));
				strtemp = transRS.getLong("id")+"::"+CANCELDATE+"::"+CANCELMARK+"::"+inputUserName+"::"+inputDate;
				coll.add(strtemp);
			}
				return coll;
			}catch(SQLException sqle){
				sqle.printStackTrace();
				throw new ITreasuryDAOException("查询作废记录明细报错",sqle);
			}finally{
				finalizeDAO();
			}
			
		
	}
}
