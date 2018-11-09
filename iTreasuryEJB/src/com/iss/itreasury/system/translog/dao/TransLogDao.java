package com.iss.itreasury.system.translog.dao;


import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo;
import com.iss.itreasury.system.translog.dataentity.TranslogInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

public class TransLogDao extends ITreasuryDAO
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	public TransLogDao()
	{
		super("sys_translogger","SEQ_SYS_TRANSLOGGER");
	}
	public void save(TranslogInfo info) throws Exception 
	{
		try{
		     add(info);
		}catch(Exception exp){
			System.out.println("保存业务日志失败，" + exp.getMessage());
			exp.printStackTrace();
		}
	}
	
	//	翻页查询
	public PageLoader queryTransLogInfo(QueryTransLogInfo queryConditionTransLogInfo) throws Exception
	{
		getTransLogSQL(queryConditionTransLogInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 产生查询SQL  
	 * @param info
	 *
	 */
	public void getTransLogSQL(QueryTransLogInfo queryConditionTransLogInfo)
	{
		try{
			
			m_sbSelect = new StringBuffer();
			// select 
			m_sbSelect.append("A.departmentname, A.USERNAME,A.LOGTYPE,A.FUNCTIONPOINTDESCRIPTION,A.MAININFO,to_char(A.ACCESSTIME,'hh24:mi:ss') time, \n");
			m_sbSelect.append(" A.STATUS,A.ACCESSTIME,A.CLIENTIP,A.CURRENCYID \n");
			// from 
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" sys_translog_view A \n");
			// where 
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and A.officeid= "+queryConditionTransLogInfo.getOfficeid()+" \n");
			
			
			//币种
			String strTemp = String.valueOf(queryConditionTransLogInfo.getCurrencyid());
			if(strTemp!=null && !strTemp.equals("-1"))
			{
			m_sbWhere.append(" and A.currencyid= "+queryConditionTransLogInfo.getCurrencyid()+" \n");
			}
			//操作人
			strTemp = queryConditionTransLogInfo.getQueryuserid();
			if (strTemp!=null && !strTemp.equals("-1")&& !strTemp.equals(""))
			{	
				m_sbWhere.append(" and A.userid = '" + strTemp + "'");
			}
			//操作日期
			strTemp = queryConditionTransLogInfo.getStartdate();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append("and A.ACCESSTIME>= to_date('"+strTemp+"','yyyy-mm-dd')");
				
			}
			strTemp = queryConditionTransLogInfo.getEnddate();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append("and A.ACCESSTIME<= to_date('"+strTemp+"','yyyy-mm-dd')+1");
				
			}
			//操作时间
			strTemp = queryConditionTransLogInfo.getStarttime();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and substr(to_char(A.ACCESSTIME,'yyyy-mm-dd hh24:mi:ss'),INSTR(to_char(A.ACCESSTIME,'yyyy-mm-dd hh24:mi:ss'),' ')+1,8) >= '" + strTemp + "'");
				
			}
			strTemp = queryConditionTransLogInfo.getEndtime();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				m_sbWhere.append(" and substr(to_char(A.ACCESSTIME,'yyyy-mm-dd hh24:mi:ss'),INSTR(to_char(A.ACCESSTIME,'yyyy-mm-dd hh24:mi:ss'),' ')+1,8) <= '" + strTemp + "'");
				
			}
			//日志类型	
			strTemp = queryConditionTransLogInfo.getQuerylogtype();
			if (strTemp!=null && !strTemp.equals("0"))
			{	
				m_sbWhere.append(" and A.LOGTYPE = '" + strTemp + "' ");
				
			}
			//操作结果
			strTemp = queryConditionTransLogInfo.getQuerystatus();
			if(strTemp!=null&&!strTemp.equals("2")){
				m_sbWhere.append(" and A.STATUS = '" + strTemp + "'  \n");
			}
			//部门编号
			strTemp = String.valueOf(queryConditionTransLogInfo.getDepartmentid());
			if(strTemp!=null&&!strTemp.equals("-1")){
				m_sbWhere.append(" and A.departmentid = '" + strTemp + "'  \n");
			}
			//客户编号
			strTemp = String.valueOf(queryConditionTransLogInfo.getClientid());
			if(strTemp!=null&&!strTemp.equals("-1")){
				m_sbWhere.append(" and A.clientid = '" + strTemp + "'  \n");
			}
			
			//用户类型
			strTemp =  String.valueOf(queryConditionTransLogInfo.getUsertype());
			if(strTemp!=null&&!strTemp.equals("-1")){
				m_sbWhere.append(" and A.usertype = '" + strTemp + "'  \n");
			}
			
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by A.ACCESSTIME desc");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
