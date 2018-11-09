package com.iss.itreasury.creditrating.query.dao;

import com.iss.itreasury.creditrating.query.dataentity.Crert_CreditRatingQueryInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;
import java.sql.Timestamp;

public class Crert_CreditRatingQueryDAO extends ITreasuryDAO{

	
	private StringBuffer m_sbSelect  = null;
	
	private StringBuffer m_sbFrom    = null;
	
	private StringBuffer m_sbWhere   = null;
	
	private StringBuffer m_sbOrderBy = null;
	
	
	public Crert_CreditRatingQueryDAO()
	{
		
	}
	
	public PageLoader queryCreditRatingList(Crert_CreditRatingQueryInfo crert_CreditRatingQueryInfo)throws Exception
	{
		    getQueryCreditRatingListSQL(crert_CreditRatingQueryInfo);
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	        pageLoader.initPageLoader(new AppContext(),
					                  m_sbFrom.toString(),
					                  m_sbSelect.toString(),
					                  m_sbWhere.toString(),
					                  (int) Constant.PageControl.CODE_PAGELINECOUNT,
					                  "com.iss.itreasury.creditrating.query.dataentity.Crert_CreditRatingQueryResultInfo",
					                  null);
	        pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
        return pageLoader;
	}
	
	private void getQueryCreditRatingListSQL(Crert_CreditRatingQueryInfo crert_CreditRatingQueryInfo)
	{
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append(" C.ID AS CLIENTID,C.SCODE AS CLIENTCODE,C.SNAME AS CLIENTNAME,D.DETALLID AS ID, ");
		m_sbSelect.append(" D.CREDITRATINGID AS CREDITRATINGID,D.DCLIENTID AS DCLIENTID,D.RATINGCODE AS RATINGCODE, ");
		m_sbSelect.append(" D.RATINGTYPE AS RATINGTYPE,D.RATINGPROJECTID AS RATINGPROJECTID,D.RATINGPROJECTNAME AS RATINGPROJECTNAME, ");
		m_sbSelect.append(" D.RATINGRESULT AS RATINGRESULT,D.STATEDATE AS STATEDATE,D.ENDDATE AS ENDDATE,D.RATINGDATE AS RATINGDATE, ");
		m_sbSelect.append(" D.STATE AS STATE, (select max(cce.revalueddate) from crert_creditratingrevalued cce  where  cce.state = "+CreRtConstant.CreRtStatus.APPROVALED+" ");
		if(crert_CreditRatingQueryInfo.getOfficeID()>0)
		{
			m_sbSelect.append(" and cce.officeid = "+crert_CreditRatingQueryInfo.getOfficeID());
		}
		if(crert_CreditRatingQueryInfo.getCurrencyID()>0)
		{
			m_sbSelect.append(" and cce.currencyid = "+crert_CreditRatingQueryInfo.getCurrencyID());
		}
		m_sbSelect.append(" and cce.creditratingid = d.DETALLID) revalueddate ");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" CLIENT C,client_clientinfo,(SELECT DETALL.ID AS DETALLID,DETALL.CREDITRATINGID AS CREDITRATINGID,DETALL.RATINGCODE AS RATINGCODE, ");
		m_sbFrom.append(" DETALL.CLIENTID AS DCLIENTID,DETALL.RATINGTYPE AS RATINGTYPE,DETALL.RATINGPROJECTID AS RATINGPROJECTID, ");
		m_sbFrom.append(" DETALL.RATINGPROJECTNAME AS RATINGPROJECTNAME,DETALL.RATINGRESULT AS RATINGRESULT,DETALL.STATEDATE AS STATEDATE, ");
		m_sbFrom.append(" DETALL.ENDDATE AS ENDDATE,DETALL.RATINGDATE AS RATINGDATE,DETALL.STATE AS STATE ");
		m_sbFrom.append(" FROM CRERT_CREDITRATINGDETAIL DETALL WHERE DETALL.STATE = "+CreRtConstant.CreRtStatus.APPROVALED);
		if(crert_CreditRatingQueryInfo.getOfficeID()>0)
		{
			m_sbFrom.append(" and DETALL.officeid = "+crert_CreditRatingQueryInfo.getOfficeID());
		}
		if(crert_CreditRatingQueryInfo.getCurrencyID()>0)
		{
			m_sbFrom.append(" and DETALL.currencyid = "+crert_CreditRatingQueryInfo.getCurrencyID());
		}
		if(!crert_CreditRatingQueryInfo.getQueryDate().equals(""))
		{
			m_sbFrom.append(" AND DETALL.STATEDATE <= TO_DATE('" + crert_CreditRatingQueryInfo.getQueryDate() + "','yyyy-mm-dd')");
			
			m_sbFrom.append(" AND DETALL.ENDDATE >= TO_DATE('" + crert_CreditRatingQueryInfo.getQueryDate() + "','yyyy-mm-dd')");
		}
		m_sbFrom.append("  )D ");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  C.ID = D.DCLIENTID(+)and c.id=client_clientinfo.id and client_clientinfo.statusid=1 ");
		
		if(crert_CreditRatingQueryInfo.getClientID() > 0)
		{
		   m_sbWhere.append(" AND C.ID = " + crert_CreditRatingQueryInfo.getClientID());
		}
		System.out.print(m_sbSelect);
		System.out.print(m_sbFrom);
		System.out.print(m_sbWhere);
		
		/*
		if(!crert_CreditRatingQueryInfo.getQueryDate().equals(""))
		{
			m_sbWhere.append(" AND D.STATEDATE <= TO_DATE('" + crert_CreditRatingQueryInfo.getQueryDate() + "','yyyy-mm-dd')");
			
			m_sbWhere.append(" AND D.ENDDATE >= TO_DATE('" + crert_CreditRatingQueryInfo.getQueryDate() + "','yyyy-mm-dd')");
		}
		*/
		m_sbOrderBy = new StringBuffer();
		
		m_sbOrderBy.append(getQueryCreditRatingListOrderParam(crert_CreditRatingQueryInfo.getDesc(),crert_CreditRatingQueryInfo.getOrderParam()));
	}
	public  String getQueryCreditRatingListOrderParam(long lDesc,long lOrderParam)
	{
		StringBuffer sbOrderBy = new StringBuffer();
        String strDesc = lDesc == 2 ? " DESC " : " ASC ";
		
		if(lOrderParam > 0) 
		{
			switch ((int) lOrderParam)
			{
				case 1:sbOrderBy.append(" \n ORDER BY CLIENTCODE " + strDesc);             break;
				case 2:sbOrderBy.append(" \n ORDER BY CLIENTNAME " + strDesc);   break;
				case 3:sbOrderBy.append(" \n ORDER BY RATINGCODE " + strDesc);         break;
				case 4:sbOrderBy.append(" \n ORDER BY RATINGPROJECTNAME " + strDesc);break;
				case 5:sbOrderBy.append(" \n ORDER BY RATINGRESULT " + strDesc);        break;
				case 6:sbOrderBy.append(" \n ORDER BY STATEDATE " + strDesc);          break;
				case 7:sbOrderBy.append(" \n ORDER BY ENDDATE " + strDesc);     break;
				case 8:sbOrderBy.append(" \n ORDER BY RATINGDATE " + strDesc);      break;
				case 9:sbOrderBy.append(" \n ORDER BY REVALUEDDATE " + strDesc);      break;
				default:sbOrderBy.append(" \n ORDER BY ID DESC" );break;
		    }	
		}
		else
		{
			sbOrderBy.append(" \n ORDER BY CLIENTCODE ASC" );
		}
		return sbOrderBy.toString();
	}
	
	
	
	
	
	
}
