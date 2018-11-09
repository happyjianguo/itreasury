package com.iss.itreasury.ebank.fundplan.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.fundplan.dao.CapitalPlanConfigModelDao;
import com.iss.itreasury.ebank.fundplan.dao.CapitalPlanDao;
import com.iss.itreasury.ebank.fundplan.dao.SubCapitalPlanDao;
import com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanCondition;
import com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanConfigModelInfo;
import com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanGatherInfo;
import com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo;
import com.iss.itreasury.ebank.fundplan.dataentity.SubCapitalPlanInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

/**
 * 处理资金计划申报，复核，审批等相关业务
 * 
 *
 */
public class AllCapitalPlanBiz{
	

	/**
	 * 新增一个资金申报
	 * 
	 * @param info
	 * @param subPlanVector
	 * @return
	 * @throws IException
	 */
	public long addCapitalPlan(CapitalPlanInfo info, Vector subPlanVector) throws IException 
	{
		long returnId = -1;
		Connection conn = null ;
		SubCapitalPlanInfo subCapitalPlanInfo = null;
		
		if(isExistCapitalPlan(info.getOfficeId(),
				info.getCurrencyId(),
				info.getClientId(), 
				info.getStartdate(),
				info.getEnddate()))
		{
			throw new IException(DataFormat.formatDate(info.getStartdate())
					+ " 至 " + DataFormat.formatDate(info.getEnddate()) + " 已存在资金计划");
		}
		
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			CapitalPlanDao capitalPlan = new CapitalPlanDao(conn);
			SubCapitalPlanDao subCapitalPlanDAO = new SubCapitalPlanDao(conn);
			returnId = capitalPlan.add(info);
			
			if(returnId <= 0)
			{
				throw new IException("新增ID无效");
				
			}
			
			for(int i=0; i<subPlanVector.size(); i++)
			{
				subCapitalPlanInfo = new SubCapitalPlanInfo();
				subCapitalPlanInfo = (SubCapitalPlanInfo)subPlanVector.get(i);
				subCapitalPlanInfo.setCapitalplanId(returnId);
				
				subCapitalPlanDAO.add(subCapitalPlanInfo);
			}
			conn.commit();
		}
		catch (Exception e) {
			try {
				if(conn!=null) conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			throw new IException("资金计划申报保存失败，原因：" + e.getMessage(), e);
		}finally{
			try {
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return returnId;
	}
   
	/**
	 * added by ylguo
	 * 资金申报的查找（向主表中查找）
	 * @param
	 *       CapitalPlanInfo info2
	 *       long clientId
	 * @exception Exception
	 */
	public Collection findCapitalPlan(CapitalPlanInfo conditionInfo) throws Exception {	
		try{
			CapitalPlanDao dao = new CapitalPlanDao();
			return dao.findCapitalPlan(conditionInfo);
			
		}catch (Exception e) {
     		throw new IException("查询资金计划申报失败，原因：" + e.getMessage());
		}
	}
	
	
	/**
	 * added by ylguo
	 * 资金申报子表的修改
	 * @param
	 *       SubCapitalPlanInfo info<br>
	 *       CapitalPlanInfo info2
	 * @exception Exception
	 */
	public void updateSubCapitalPlan(CapitalPlanInfo info,Vector subPlanVector) throws IException 
	{
		Connection conn = null ;
		try
		{
			
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			CapitalPlanDao capitalPlanDao = new CapitalPlanDao(conn);
			if(!capitalPlanDao.checkCapitalplanStatus(info.getId(), OBConstant.SettInstrStatus.SAVE))
			{
				throw new IException("数据已经被修改，不能保存");
			}
			SubCapitalPlanDao subCapitalPlan = new SubCapitalPlanDao(conn);
			SubCapitalPlanInfo subCapitalPlanInfo = null;	
			
			for(int i=0; i<subPlanVector.size(); i++)
			{
				subCapitalPlanInfo = (SubCapitalPlanInfo)subPlanVector.get(i);
				subCapitalPlan.updateSubCapital(subCapitalPlanInfo);
			}
			
			conn.commit();
		}
		catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new IException("资金计划申报保存失败，原因：" + e.getMessage(), e);
			
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
   
	/**
	 * 删除资金计划申报
	 * 
	 * @param capitalplanId 资金计划主表ID
	 * @throws IException
	 */
	public void delAllCapitalPlan(long capitalplanId) throws IException 
	{
		Connection conn = null ;
		try
		{
			
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			SubCapitalPlanDao subCapitalPlan = new SubCapitalPlanDao(conn);
			CapitalPlanDao capitalPlanDao = new CapitalPlanDao(conn);
			if(!capitalPlanDao.checkCapitalplanStatus(capitalplanId, OBConstant.SettInstrStatus.SAVE))
			{
				throw new IException("数据已经被修改，不能保存");
			}
			//将主表中id为capitalplanId的记录状态置为0，表示删除；
			capitalPlanDao.delCapitalplan(capitalplanId);
			//将子表中capitalplanId字段为参数capitalplanId的所有的记录的状态状态置为0，表示删除；
			subCapitalPlan.delSubCapitalplan(capitalplanId);
			
			conn.commit();				
		}
		catch (Exception e) {
			try{
				if(conn!=null) conn.rollback();
			}catch(SQLException e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new IException("资金计划申报删除失败，原因：" + e.getMessage());
		}finally{
			try{
				if(conn!=null) conn.close();
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
	}
 
	/**
	 * added by ylguo
	 * 资金计划的复核
	 * @param long capitalplanId,long modifyuserid,String datetime
	 * @return long
	 * @throws Exception
	 */
	public long checkAllCapitalPlan(long capitalplanId,long modifyuserid,String datetime) throws Exception{
		long succ = 0;
		Connection conn = null ;
		try
		{
			
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			SubCapitalPlanDao subCapitalPlan = new SubCapitalPlanDao(conn);
			CapitalPlanDao capitalPlanDao = new CapitalPlanDao(conn);
			if(!capitalPlanDao.checkCapitalplanStatus(capitalplanId, OBConstant.SettInstrStatus.SAVE))
			{
				throw new IException("数据已经被修改，不能保存");
			}
			try{
				//将主表中id为capitalplanId的记录状态置为2，表示删除；
				capitalPlanDao.checkCapitalplan(capitalplanId,modifyuserid,datetime);
				//将子表中capitalplanId字段为参数capitalplanId的所有的记录的状态状态置为2，表示删除；
				subCapitalPlan.checkSubCapitalplan(capitalplanId);
				conn.commit();
				succ = 1;
			}
			catch (SQLException e) {
				conn.rollback();
			}
			conn.setAutoCommit(true);
		}
		catch (Exception e) {
			//System.out.println("test");
			e.printStackTrace();
			//log4j.error(e.toString());
			throw new IException("资金计划复核失败"+ e.getMessage(), e);
		}finally{
			conn.close();
		}
		return succ;
	}
	
	/**
	 * added by ylguo
	 * 资金计划的取消复核
	 * @param long capitalplanId,long modifyuserid,String datetime
	 * @return long
	 * @throws Exception
	 */
	public long disCheckAllCapitalPlan(long capitalplanId) throws Exception{
		long succ = 0;
		Connection conn = null ;
		try
		{
			
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			SubCapitalPlanDao subCapitalPlan = new SubCapitalPlanDao(conn);
			CapitalPlanDao capitalPlanDao = new CapitalPlanDao(conn);
			try{
				if(!capitalPlanDao.checkCapitalplanStatus(capitalplanId, OBConstant.SettInstrStatus.CHECK))
				{
					throw new IException("数据已经被修改，不能保存");
				}
				//将主表中id为capitalplanId的记录状态置为1，表示删除；
				capitalPlanDao.discheckCapitalplan(capitalplanId);
				//将子表中capitalplanId字段为参数capitalplanId的所有的记录的状态状态置为1，表示删除；
				subCapitalPlan.discheckSubCapitalplan(capitalplanId);
				conn.commit();
				succ = 1;
			}
			catch (SQLException e) {
				conn.rollback();
			}
			conn.setAutoCommit(true);
		}
		catch (Exception e) {
			//System.out.println("test");
			e.printStackTrace();
			//log4j.error(e.toString());
			throw new IException("资金计划取消复核失败"+ e.getMessage(), e);
		}finally{
			conn.close();
		}
		return succ;
	}
	
	/**
	 * added by ylguo 结算模块中的资金计划汇总查询
	 * @param CapitalPlanInfo conditionInfo
	 * @return PageLoader
	 * @throws Exception
	 */
	public PageLoader queryByConditionGather(CapitalPlanGatherInfo conditionInfo) throws Exception {	
		try{
			CapitalPlanDao dao = new CapitalPlanDao();
			PageLoader pageloader = dao.queryByConditionGather(conditionInfo);
			return pageloader;
			
		}catch (Exception e) {
			//log4j.error(e.toString());
     		throw new IException("资金计划汇总查询失败");
		}
	}
	
	/**
	 * added by ylguo 按照开始时间来关联主表，对资金计划子表数据按周进行汇总
	 * @param String startdate
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByStartdate(String startdate)throws Exception{
		try{
			SubCapitalPlanDao dao = new SubCapitalPlanDao();
			return dao.findByStartdate(startdate);
		}catch (Exception e) {
			throw new IException("查询资金计划汇总详情单失败");
		}
	}
	
	
	public PageLoader queryCapitalPlans(CapitalPlanCondition conditionInfo) throws Exception {	
		try{
			CapitalPlanDao dao = new CapitalPlanDao();
			PageLoader pageloader = dao.queryCapitalPlans(conditionInfo);
			return pageloader;
		}catch (Exception e) {
			//log4j.error(e.toString());
     		throw new IException("查询资金计划申报失败");
		}
	}
	
	public long auditingAllCapitalPlans(String strCpID[],String strAction,long auditingUserid) throws Exception{
		long succ = -1;
		Connection conn = null ;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			SubCapitalPlanDao subCapitalPlan = new SubCapitalPlanDao(conn);
			CapitalPlanDao capitalPlanDao = new CapitalPlanDao(conn);
			try{
				if(capitalPlanDao.checkAuditingAllCapitalplansStatus(strCpID,strAction) || subCapitalPlan.checkAuditingAllSubCapitalplansStatus(strCpID,strAction))
				{
					throw new IException("数据已经被修改，不能保存");
				}
				//将主表中id为capitalplanId的记录状态置为20，表示删除；
				capitalPlanDao.auditingAllCapitalplans(strCpID,strAction,auditingUserid);
				//将子表中capitalplanId字段为参数capitalplanId的所有的记录的状态状态置为20，表示删除；
				subCapitalPlan.auditingAllSubCapitalplans(strCpID,strAction);
				conn.commit();
				succ = 1;
			}
			catch (SQLException e) {
				conn.rollback();
			}
			conn.setAutoCommit(true);
		}
		catch (IException e) {
			//System.out.println("test");
			e.printStackTrace();
			//log4j.error(e.toString());
			throw new IException(e.getMessage());
		}finally{
			conn.close();
		}
		return succ;
	}
	
	/**
	 * 判断某客户在指定的时间段内是否已存在资金计划
	 * 
	 * @param office
	 * @param currency
	 * @param clientId
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws Exception
	 */
	public boolean isExistCapitalPlan(long office, long currency, long clientId, Timestamp dateFrom, Timestamp dateTo) throws IException
	{
		try {
			CapitalPlanDao planDao = new CapitalPlanDao();
			CapitalPlanInfo conditionInfo = new CapitalPlanInfo();
			conditionInfo.setOfficeId(office);
			conditionInfo.setCurrencyId(currency);
			conditionInfo.setClientId(clientId);
			conditionInfo.setStartdate(dateFrom);
			conditionInfo.setEnddate(dateTo);
			
			Collection colResult = planDao.findCapitalPlan(conditionInfo);
			if(colResult!=null && colResult.size()>0)
			{
				return true;
			}
			return false;
		}
		catch (Exception e) {
			throw new ITreasuryDAOException("判断时间段内是否存在资金申报时出错，原因：" + e.getMessage() ,e);
		}
	}	
	
	/**
	 * 
	 * 返回指定模板类型当前正在使用的模板ID
	 * 
	 * @param modelType	模板类型
	 * @param officeId
	 * @param currencyId
	 * @return
	 * @throws IException 
	 */
	public long getPlanModelIdInUse(long modelType,long officeId, long currencyId) throws IException
	{
		long resultId = -1;
		
		CapitalPlanConfigModelInfo conditionInfo = new CapitalPlanConfigModelInfo();
		conditionInfo.setModelType(modelType);
		conditionInfo.setOfficeId(officeId);
		conditionInfo.setCurrencyId(currencyId);
		conditionInfo.setStatusId(1);
		
		CapitalPlanConfigModelDao modelDAO = new CapitalPlanConfigModelDao();
		try {
			Collection colResult = modelDAO.findByCondition(conditionInfo);
			if(colResult != null && colResult.size()>0)
			{
				CapitalPlanConfigModelInfo modelInfo 
					= (CapitalPlanConfigModelInfo) colResult.toArray(new CapitalPlanConfigModelInfo[0])[0];
				resultId = modelInfo.getId();
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new IException("查找有效的模板时出错，" + e.getMessage(), e);
		}
		
		return resultId;
	}
	
	/**
	 * 根据申报的客户编号和开始时间产生申报的资金计划编号。
	 * 
	 * @param clientCode 客户编号
	 * @param startDateTime 计划的开始时间隔
	 * @return
	 */
	public String createCapitalPlanCode(String clientCode, Timestamp startDateTime) 
	{
	    String[] temp = clientCode.split("-");						
		return DataFormat.formatDate(startDateTime, DataFormat.FMT_DATE_SPECIAL) + temp[1];
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	}
	
}