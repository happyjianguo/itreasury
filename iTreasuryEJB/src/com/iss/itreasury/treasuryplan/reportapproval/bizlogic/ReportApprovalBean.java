/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.treasuryplan.reportapproval.bizlogic;
import com.iss.itreasury.util.*;
import com.iss.itreasury.treasuryplan.util.*;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.dataentity.*;
import com.iss.itreasury.treasuryplan.reportapproval.dataentity.*;
import com.iss.itreasury.treasuryplan.reportapproval.dao.*;
import com.iss.itreasury.treasuryplan.report.dao.AbstractDestinationDataDAO;
import com.iss.itreasury.treasuryplan.report.dataentity.*;
import java.util.*;
import java.sql.Connection;
import java.sql.Timestamp;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 请在代码中区分部门是一般部门还是整个公司
 */
public class ReportApprovalBean
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
    
	/**
	 * 从预测数据读取编制区间内的本部门人员录入的计划明细
	 * @param depID
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Collection findTPForcastData(long depID,Timestamp startDate,Timestamp endDate) throws Exception
	{
		Collection c = null;
		
		return c;
	}
	
	/**
	 * 新建资金计划版本
	 * 1.检查在编制区间有没有未完成得资金计划版本
	 * 2.生成资金计划版本
	 * 3.拷贝资金计划明细
	 * @param verInfo
	 * @return
	 * @throws Exception
	 */
	public long createReportVersion(TreasuryPlanInfo verInfo) throws Exception
	{
		Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO();
		long versionID = dao.createReportVersion( verInfo );
		return versionID;
	}
	
	/**
	 * 在修改过程中保存资金计划版本信息
	 * @param verInfo
	 * @throws Exception
	 */
	public void saveReportVersion(TreasuryPlanInfo verInfo) throws Exception
	{
		Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO();
		if (verInfo.getId()<0)
		{
			Log.print( "ReportVersion: is null\n");
			return;
		}
		dao.update( verInfo );
	}
	
	/**
	 * 在修改完成之后，修改期末额逻辑
	 * @param c
	 * @throws Exception
	 */
	public void reComputeFinal(long versionID) throws Exception
	{
		Connection con = null;
		Trea_TreasuryPlanDAO dao = null;
		TreasuryPlanInfo info=null;
		try
		{
			long firmDepID = TPUtil.getCompanyID();
			con =  Database.getConnection();
			dao = new Trea_TreasuryPlanDAO();
			info=(TreasuryPlanInfo)dao.findByID( versionID,TreasuryPlanInfo.class );
			if (info.getId()>0 && info.getDepartmentId()!=firmDepID)
			{	
				dao.reComputeFinal( con,info.getId(),info.getStartDate(),info.getEndDate(),info.getOfficeId(),info.getCurrencyId() );
			}
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			throw ex;
		}	
		finally
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}		
		
	}
	/**
	 * 在修改过程中保存资金计划明细
	 * @param c
	 * @throws Exception
	 */
	public void saveReportDetail(Collection c) throws Exception
	{
		Connection con = null;
		Trea_TreasuryPlanDetailDAO dao = null;
		try
		{
			con =  Database.getConnection();
			con.setAutoCommit(false);
			dao = new Trea_TreasuryPlanDetailDAO(con);
			if (c != null)
			{
				Iterator it = (Iterator)c.iterator();
				while(it.hasNext())
				{
					TreasuryPlanDetailInfo info = (TreasuryPlanDetailInfo)it.next();
					dao.update(info);
				}
			}
			con.commit();
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			throw ex;
		}	
		finally
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}		
	}
	
	/**
	 * 用于修改查找和审核查找
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection findByMultiOption(TreasuryPlanQueryInfo qInfo) throws Exception
	{
		
		Collection c = null;
		Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO();		
		ApprovalBiz appBiz = new ApprovalBiz();
		String strUser = "";
		long lModuleID = Constant.ModuleType.PLAN;
		long lLoanTypeID = qInfo.getLoanTypeId();
		long lActionID = qInfo.getActionId();
		long lApprovalID = -1;		
		try
        {
        	lApprovalID = qInfo.getApprovalId();
        	//获得ApprovalID
        	if (lApprovalID <= 0)
        	{
        		//此处需要根据现有系统审批流进行修改
        		lApprovalID = appBiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,qInfo.getOfficeId(),qInfo.getCurrencyId());
        	}
        }
        catch (Exception e1)
        {
        	log4j.error("getApprovalID fail");
        	e1.printStackTrace();
        }
        
        try
        {
        	//获得真正来审批这个记录的人（真实或传给的虚拟的人！）        	
        	//此处需要根据现有系统审批流进行修改
        	strUser = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID, qInfo.getOfficeId(),qInfo.getCurrencyId(),qInfo.getUserId());
        }
        catch (Exception e2)
        {
        	log4j.error("findTheVeryUser fail");
        	e2.printStackTrace();
        }
        qInfo.setStrUser(strUser);
        qInfo.setApprovalId(lApprovalID);        
        c = dao.findByMultiOption(qInfo);
		return c;
	}

	public long getLevelCount(long planVersionID) throws Exception
	{
		long level=-1;
		Trea_TreasuryPlanDetailDAO dao = new Trea_TreasuryPlanDetailDAO();
		level=dao.getLevelCount( planVersionID );
		return level;
	}
	/**
	 * 根据版本号获得版本明细
	 * @return
	 * @throws Exception
	 */
	public Collection findReportDetailByVersionID(long versionID) throws Exception
	{
		Collection c = null;
		Trea_TreasuryPlanDetailDAO dao = new Trea_TreasuryPlanDetailDAO();
		
		c=dao.findPlanDetailByVersionID( versionID ); 
		return c;
	}
	
	/**
	 * 根据版本号，查找该版本里面的日期
	 * @author yuanxue
	 * @throws Exception 
	 */
	public ArrayList getDetailDateByversionID(long versionID) throws Exception{
		ArrayList list = new ArrayList(); 
		Trea_TreasuryPlanDetailDAO dao = new Trea_TreasuryPlanDetailDAO();
		return dao.findDetailDateByversionID(versionID);
	}

	/**
	 * 获得版本信息
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public TreasuryPlanInfo findReportVersionByID(long ID) throws Exception
	{
		TreasuryPlanInfo info = new TreasuryPlanInfo();
		Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO();
		
		info = (TreasuryPlanInfo)dao.findByID( ID,info.getClass() );
		
		return info;
			
	}
	/**
	 * 审批资金计划
	 * @param ATInfo
	 * @throws Exception
	 */
	public void check(ApprovalTracingInfo ATInfo) throws Exception
	{
	    Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO();

		long lCount = 0;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		long[] lApprovalContentIDList;
		String strSQL = "";

		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();

		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		String sOpinion = ATInfo.getOpinion();

		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalBiz appbiz = new ApprovalBiz();

		lApprovalContentIDList = ATInfo.getApprovalContentIDList();

		if (lApprovalContentIDList.length > 0)
		{
			try
			{
				//获得ApprovalID
				if (lApprovalID <= 0)
				{
					//此处需要根据现有系统审批流进行修改
					lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,ATInfo.getOfficeID(),ATInfo.getCurrencyID());
				}
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}

			//处理审批意见
			if (ATInfo.getCheckActionID() == TPConstant.Actions.REJECT) //拒绝
			{
				//审批意见状态
				lStatusID = Constant.RecordStatus.VALID;
				//审批操作类型
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (ATInfo.getCheckActionID() == TPConstant.Actions.CHECK) //审批
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (ATInfo.getCheckActionID() == TPConstant.Actions.CHECKOVER) //审批&&最后
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//审批完成后需要做的操作
			}
			if (ATInfo.getCheckActionID() == TPConstant.Actions.RETURN) //修改
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.RETURN;
			}
			ATInfo.setApprovalID(lApprovalID);
			ATInfo.setResultID(lResultID);
			ATInfo.setStatusID(lStatusID);

			lCount = lApprovalContentIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lApprovalContentIDList[i] > 0)
				{
					ATInfo.setApprovalContentID(lApprovalContentIDList[i]);
					Log.print("ATInfo.getApprovalContentID()=" + ATInfo.getApprovalContentID());
				}
				else
				{
					break;
				}
				//审核资金计划
				dao.check(ATInfo);

				log4j.debug("saveApprovalTracing begin");
				try
				{
					appbiz.saveApprovalTracing(ATInfo);
				}
				catch (Exception e)
				{
					log4j.error("saveApprovalTracing fail");
					e.printStackTrace();
				}
				log4j.debug("saveApprovalTracing end");
			}
		}
	}

	/**
	 * 取消资金计划
	 * @param lID
	 * @throws Exception
	 */	
	public void cancel(long lID) throws Exception
	{
		Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO();
		
		dao.updateStatus(lID,TPConstant.PlanVersionStatus.CANCEL);
	}
	
	public void delete(long lID) throws Exception
	{
		Connection conn = null;
		
		conn=Database.getConnection() ;
		Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO(conn);
		Trea_TreasuryPlanDetailDAO detailDAO = new Trea_TreasuryPlanDetailDAO(conn);
		
		detailDAO.deleteByVersion( lID );
		dao.deletePhysical( lID );
		
		if (conn!=null)
		{
			conn.close();
			conn=null;
		}
	}
	/**
	 * 从计划明细数据中组装模板详细信息
	 * @param forecastInfo
	 * @return
	 * @throws Exception
	 */
	private TemplateDetailInfo getTemplateDetailInfoFromPlanDetailInfo(TreasuryPlanDetailInfo dInfo) throws Exception
	{
		TemplateDetailInfo detailInfo = new TemplateDetailInfo();
		detailInfo.setTransactionDate(dInfo.getTransactionDate());
		detailInfo.setForecastAmount(dInfo.getForecastAmount());
		detailInfo.setPlanAmount(dInfo.getPlanAmount());
		detailInfo.setDifferenceAmount(dInfo.getPlanAmount()-dInfo.getForecastAmount());
		return detailInfo;
	}
	/**
	 * 从计划明细数据中组装模板主信息
	 * @param forecastInfo
	 * @return
	 * @throws Exception
	 */
	private TemplateInfo getTemplateInfoFromPlanDetailInfo(TreasuryPlanDetailInfo dInfo) throws Exception
	{
		TemplateInfo info = new TemplateInfo();
		info.setLineID(dInfo.getLineID());
		info.setLineNo(dInfo.getLineNo());
		info.setLineName(dInfo.getLineName());
		info.setLineLevel(dInfo.getLineLevel());
		info.setIsLeaf(dInfo.getIsLeaf());
		info.setParentLineID(dInfo.getParentLineID());
		return info;
	}
	/**
	 * 从计划明细数据类中得到模板数据类
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Vector getTemplateInfoFromPlanDetailData(Vector fV) throws Exception
	{
		Vector tV = new Vector();
		try
		{
			if (fV != null)
			{
				Iterator it = (Iterator)fV.iterator();

				String strLastLineNo = "";
				TemplateInfo templateInfo = null;
				TemplateDetailInfo detailInfo = null;
				ArrayList aryList = null;
				while(it.hasNext())
				{
					TreasuryPlanDetailInfo dInfo = (TreasuryPlanDetailInfo)it.next();
					
					detailInfo = this.getTemplateDetailInfoFromPlanDetailInfo(dInfo);
					
					if (!(strLastLineNo != null && dInfo.getLineNo() != null && strLastLineNo.equals(dInfo.getLineNo())))
					{
						//
						if (templateInfo != null)
						{
							//将ArrayList转换成array,并加入主信息类
							templateInfo.setDetailInfos(aryList);
							tV.add(templateInfo);
						}
						aryList = new ArrayList();
						templateInfo = this.getTemplateInfoFromPlanDetailInfo(dInfo);
						aryList.add(detailInfo);
					}
					else
					{
						aryList.add(detailInfo);
					}
					//得到上次行项目编号
					strLastLineNo = templateInfo.getLineNo();
					
					//最后一个
					if (it.hasNext() == false)
					{
						//将ArrayList转换成array,并加入主信息类
						templateInfo.setDetailInfos(aryList);
						tV.add(templateInfo);
					}					
				}
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return ((tV != null && tV.size() > 0 )? tV : null);
	}
	/**
	 * 从模板数据类中得到预测数据类
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Vector getPlanDataFromTemplateInfo(long planVersionID,Vector tV) throws Exception
	{
		Vector fV = new Vector();
		try
		{
			if (tV != null)
			{
				Iterator it = (Iterator)tV.iterator();

				TemplateInfo templateInfo = null;
				ArrayList detailInfos = null;
				TreasuryPlanDetailInfo dInfo = null;
				
				while(it.hasNext())
				{
					templateInfo = (TemplateInfo)it.next();
					detailInfos = templateInfo.getDetailInfos();
					for (int i=0; i<detailInfos.size();i++)
					{
						dInfo = new TreasuryPlanDetailInfo();

						dInfo.setLineID(templateInfo.getLineID());
						dInfo.setTreasuryPlanID(planVersionID);
						dInfo.setLineNo(templateInfo.getLineNo());
						dInfo.setLineName(templateInfo.getLineName());
						dInfo.setLineLevel(templateInfo.getLineLevel());
						dInfo.setParentLineID(templateInfo.getParentLineID());
						dInfo.setIsLeaf(templateInfo.getIsLeaf());
						dInfo.setTransactionDate(((TemplateDetailInfo)detailInfos.get(i)).getTransactionDate());
						dInfo.setForecastAmount(((TemplateDetailInfo)detailInfos.get(i)).getForecastAmount());
						dInfo.setPlanAmount(((TemplateDetailInfo)detailInfos.get(i)).getPlanAmount());

						fV.add(dInfo);
					}
				}
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return ((fV != null && fV.size() > 0 )? fV : null);
	}
	
	public static void main(String args[])
	{
		ReportApprovalBean bean= new ReportApprovalBean()	;
		
		try
		{
			
			bean.reComputeFinal(6);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
