package com.iss.itreasury.loan.aftercredit.bizlogic;


import com.iss.itreasury.codingrule.dao.Sys_CodingRuleDao;
import com.iss.itreasury.codingrule.dao.Sys_CodingRuleDetailDao;
import com.iss.itreasury.codingrule.dataentity.CodingRuleInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.aftercredit.dao.AfterCreditDao;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditReportInfo;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditFiveSortInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.system.util.SYSConstant;


import com.iss.itreasury.util.Log;

public class AfterCreditBiz {
	
	
	private AfterCreditDao detailDao=null;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	private long m_luserID = -1;

	public AfterCreditBiz() {

		detailDao = new AfterCreditDao();
	}
	  public long insert(AfterCreditReportInfo info) throws Exception
	  {
		  long resultid = -1;
			try {
				 
				resultid = detailDao.insert(info);
				 
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception();
			}
			return resultid;
	  }
	  
	  
	    /**
	     * 
	     * @return
	     * @throws SettlementException
	     */
		public String buildcode()throws SettlementException
		{
			StringBuffer sb = new StringBuffer();
			String sDate = "";
			String assureCode = "J";
			try{
				sDate = Env.getSystemDateString();
			    //sDate = sDate.replaceAll("-", "");
				System.out.println("sDate_______________________sDate="+sDate);
				sDate = sDate.substring(2, 4);
            // 类型编号
			    sb.append(assureCode);
			    //日期
			    sb.append(sDate);
			    sb.append("12");
			    long serial = detailDao.getMaxSerial(sb.toString()) + 1;
			    String serialCode = DataFormat.transCode(serial, 4);
			    sb.append(serialCode);
		      }
			catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}

		     return sb.toString();
		}
		
		 /* @return
		 * 
		 * 贷后检查报告
		 * 
		 * @throws RemoteException
		 * @throws IException
		 */
		public long examinePass(AfterCreditReportInfo lepInfo) throws RemoteException, IException
		{
			//对历史意见不修改，只新增审批纪录，同时将申请状态改成已提交。
			AfterCreditDao  ldao=new AfterCreditDao ();
			ApprovalTracingInfo info = new ApprovalTracingInfo();//审核意见信息 //审批记录表 （每一个人审批过的记录表存在ApprovalTracingInfo类中）
			ApprovalSettingInfo asInfo = new ApprovalSettingInfo();//查询审批设置信息
			ApprovalDelegation appbiz = new ApprovalDelegation();
			AfterCreditReportInfo appInfo=null;
			
			/*传过来的lepInfo*/
			long lOfficeID=lepInfo.getOfficeid();//办事处
			long lCurrencyID=lepInfo.getCurrencyid();//币种
			long lLoanID=lepInfo.getId();//获得 表单ID
			long lUserID=lepInfo.getUserID();//sessionMng.m_lUserID（那个用户登录的）
			
			long lNextUserID=lepInfo.getNextcheckuserid();//下一个审核人 ID
			String strOption=lepInfo.getHeckOpinion();//审批意见
			/*传过来的lepInfo完成*/
			
			long lModuleID = Constant.ModuleType.LOAN;//贷款
			long lLoanTypeID = -1;
			long lActionID = Constant.ApprovalAction.DH_1;//综合授信-授信额度调整
			long lApprovalID = -1;
			long lStatusID = Constant.RecordStatus.VALID;//有效(用)
			long lResultID = Constant.ApprovalDecision.PASS;//审核通过(用)
			long ret=-1;
			long checkLevelID=lepInfo.getNextchecklevel() ;//下一个审批级别nextchecklevel
			long lLevel = -1;						
			try
			{
				appInfo=ldao.selectReportInfoById(lLoanID);//得到表单信息
				lLoanTypeID=9;
				long status=appInfo.getStatus() ;//获得当前状态
				long nNextCheckUserID=appInfo.getNextcheckuserid();//下一个审核人
				if(!(status==LOANConstant.reportState.CHECKING||status==LOANConstant.reportState.SUBMIT))
					throw new IException("Gen_E001");
				ldao.examineUpdate(lepInfo);//在表单中 设置下一个审批人
				
				//获得ApprovalID	返回审批设置信息 返回审批流ID值；失败，返回值=-1
				lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
				if ( lApprovalID<=0 )
					throw new IException("Gen_E105");
				//下一级审核人级别  返回用户审核级别
				lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
				lLevel = lLevel-1;
				//审批设置  返回审批设置信息 返回审批流程表信息
				asInfo = appbiz.findApprovalSetting(lApprovalID);
				
				
				
				if (lLevel==asInfo.getTotalLevel())	//最后一级审核   asInfo.getTotalLevel();//审批级别总数	
				{
					//将申请状态改成已提交  //修改审批状态
					ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECK);
					lResultID = Constant.ApprovalDecision.FINISH;		
				}
				else
				{
						//判断是否可越级审批
				    if (asInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
					{
				    	//lLoanID 表但ID   lLevel 审核级别
				        ldao.updateLoanNextCheckLevel(lLoanID,lLevel);
				        //设置状态 审批中
				        ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
				        Log.print("更新下一个审核级别（可越级）：" + lLevel);
					}
					else
					{
					    //下一个审核级别自动加一
						ldao.updateLoanNextCheckLevel(lLoanID,-1);
//						设置状态 审批中
				        ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
						Log.print("更新下一个审核级别（不可越级）：" + lLevel);
					}			    
				}
										
				//保存审批意见
				info.setModuleID(lModuleID);
				info.setLoanTypeID(lLoanTypeID);
				info.setActionID(lActionID);
				info.setApprovalContentID(lLoanID);
				info.setUserID(lUserID);
				info.setNextUserID(lNextUserID);
				info.setOpinion(strOption);
				info.setResultID(lResultID);//保存审批状态用  审批结果标示
				info.setStatusID(lStatusID);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				ret=appbiz.saveApprovalTracing(info);
				System.out.println("SaveApprovalTracing:"+ret);
			}
			catch (IException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException(e.getMessage());
			}
			
			return ret;    	
		}
		/* @return
		 * 
		 * 贷款五级分类
		 * 
		 * @throws RemoteException
		 * @throws IException
		 */
		public long fiveSortPass(AfterCreditFiveSortInfo lepInfo) throws RemoteException, IException
		{
			//对历史意见不修改，只新增审批纪录，同时将申请状态改成已提交。
			AfterCreditDao  ldao=new AfterCreditDao ();
			ApprovalTracingInfo info = new ApprovalTracingInfo();//审核意见信息 //审批记录表 （每一个人审批过的记录表存在ApprovalTracingInfo类中）
			ApprovalSettingInfo asInfo = new ApprovalSettingInfo();//查询审批设置信息
			ApprovalDelegation appbiz = new ApprovalDelegation();
			AfterCreditFiveSortInfo appInfo=null;
			
			/*传过来的lepInfo*/
			long lOfficeID=lepInfo.getOfficeid();//办事处
			long lCurrencyID=lepInfo.getCurrencyid();//币种
			long lLoanID=lepInfo.getId();//获得 表单ID
			long lUserID=lepInfo.getUserID();//sessionMng.m_lUserID（那个用户登录的）
			
			long lNextUserID=lepInfo.getNextcheckuserid();//下一个审核人 ID
			String strOption=lepInfo.getHeckOpinion();//审批意见
			/*传过来的lepInfo完成*/
			
			long lModuleID = Constant.ModuleType.LOAN;//贷款
			long lLoanTypeID = -1;
			long lActionID = Constant.ApprovalAction.DH_1;
			long lApprovalID = -1;
			long lStatusID = Constant.RecordStatus.VALID;//有效(用)
			long lResultID = Constant.ApprovalDecision.PASS;//审核通过(用)
			long ret=-1;
			long checkLevelID=lepInfo.getNextchecklevel() ;//下一个审批级别nextchecklevel
			long lLevel = -1;
//			System.out.println("nextcheckuser:"+lNextUserID);
//			System.out.println("interestRate:"+lepInfo.getInterestRate() );
							
			try
			{
				appInfo=ldao.selectFiveSortInfoById(lLoanID);//得到表单信息
//				lLoanTypeID=appInfo.getTypeID();
				lLoanTypeID=14;//原始值 13
				long status=appInfo.getStatus() ;//获得当前状态
				long nNextCheckUserID=appInfo.getNextcheckuserid();//下一个审核人
				
				System.out.println("  状态 status: " + status);
				/*
				if ( nNextCheckUserID!=lUserID )
					throw new IException("Gen_E001");
				*/
				//判断当前状态 是否是 提交状态 或 审批中状态 
				//if ( status!=LOANConstant.CreditState.SUBMIT_ADJUST )
					//throw new IException("Gen_E001");
				if(!(status==LOANConstant.reportState.CHECKING||status==LOANConstant.reportState.SUBMIT))
					throw new IException("Gen_E001");
				
				System.out.println("下级审批人：" + lepInfo.getNextcheckuserid());
				
				ldao.fiveSortUpdate(lepInfo);//在表单中 设置下一个审批人
				
				//获得ApprovalID	返回审批设置信息 返回审批流ID值；失败，返回值=-1
				lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
				if ( lApprovalID<=0 )
					throw new IException("Gen_E105");
				//下一级审核人级别  返回用户审核级别
				lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
				
				System.out.println(" 下一级审批人级别：" + lLevel);
				lLevel = lLevel-1;
				Log.print("下一级审核人级别：" + lLevel);
				//审批设置  返回审批设置信息 返回审批流程表信息
				asInfo = appbiz.findApprovalSetting(lApprovalID);
				
				System.out.println("最后一级审核 : " + asInfo.getTotalLevel());
				//if (checkLevelID==asInfo.getTotalLevel())原
				System.out.println(" 最后一级审批 lLevel : " + lLevel);
				System.out.println(" 最后一级审批         : " + asInfo.getTotalLevel());
				if (lLevel==asInfo.getTotalLevel())	//最后一级审核   asInfo.getTotalLevel();//审批级别总数	
				{
					//将申请状态改成已提交  //修改审批状态
					ldao.updateLoanStatuss(lLoanID,lUserID,LOANConstant.reportState.CHECK);
					
					//将贷款申请信息复制到合同表
					//ContractDao conDao=new ContractDao();
					//conDao.insert(lLoanID,appInfo.getTypeID());
					lResultID = Constant.ApprovalDecision.FINISH;		
				}
				else
				{
						//判断是否可越级审批
				    if (asInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
					{
				    	//lLoanID 表但ID   lLevel 审核级别
				        ldao.updateLoanNextCheckLevels(lLoanID,lLevel);
				        //设置状态 审批中
				        ldao.updateLoanStatuss(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
				        Log.print("更新下一个审核级别（可越级）：" + lLevel);
					}
					else
					{
					    //下一个审核级别自动加一
						ldao.updateLoanNextCheckLevels(lLoanID,-1);
//						设置状态 审批中
				        ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
						Log.print("更新下一个审核级别（不可越级）：" + lLevel);
					}			    
				}
										
				//保存审批意见
				info.setModuleID(lModuleID);
				info.setLoanTypeID(lLoanTypeID);
				info.setActionID(lActionID);
				info.setApprovalContentID(lLoanID);
				info.setUserID(lUserID);
				info.setNextUserID(lNextUserID);
				info.setOpinion(strOption);
				info.setResultID(lResultID);//保存审批状态用  审批结果标示
				info.setStatusID(lStatusID);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				ret=appbiz.saveApprovalTracing(info);
				System.out.println("SaveApprovalTracing:"+ret);
			}
			catch (IException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException(e.getMessage());
			}
			
			return ret;    	
		}
		//added by mk 2010-01-11 删除贷后管理记录
	    public long deleteAfterCredit(long[] lDeleteID) 
	    {
	    	long id=-1;
	    	AfterCreditReportInfo info = new AfterCreditReportInfo();
	    	AfterCreditDao dao = new AfterCreditDao(info);
			
	        try {
		        	for(int i=0;i<lDeleteID.length;i++)
		        	{
		        		AfterCreditReportInfo afterCreditReportInfo =new AfterCreditReportInfo();
		        		afterCreditReportInfo.setId(lDeleteID[i]);
		        		afterCreditReportInfo.setStatus(SYSConstant.CodingRuleStatus.DELETED);
		        		dao.update(afterCreditReportInfo);
		        	}
		        	id=1;
				} catch (ITreasuryDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return id;
	    }
		//added by mk 2010-01-12 删除贷后管理五级分类记录 
	    public long deleteAfterCreditFiveSort(long[] lDeleteID) 
	    {
	    	long id=-1;
	    	AfterCreditFiveSortInfo info =new AfterCreditFiveSortInfo();
	    	AfterCreditDao dao = new AfterCreditDao(info);
			
	        try {
		        	for(int i=0;i<lDeleteID.length;i++)
		        	{
		        		AfterCreditFiveSortInfo afterCreditReportInfo =new AfterCreditFiveSortInfo();
		        		afterCreditReportInfo.setId(lDeleteID[i]);
		        		afterCreditReportInfo.setStatus(SYSConstant.CodingRuleStatus.DELETED);
		        		dao.update(afterCreditReportInfo);
		        	}
		        	id=1;
				} catch (ITreasuryDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return id;
	    }
		
}
