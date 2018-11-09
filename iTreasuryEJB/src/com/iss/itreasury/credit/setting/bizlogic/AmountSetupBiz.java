package com.iss.itreasury.credit.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import com.iss.itreasury.credit.check.CreditCheckBiz;
import com.iss.itreasury.credit.setting.dao.AmountFormDao;
import com.iss.itreasury.credit.setting.dao.AmountSetupDao;
import com.iss.itreasury.credit.setting.dao.SubAmountFormDao;
import com.iss.itreasury.credit.setting.dao.SubAmountSetupDao;
import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupViewInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountSetupInfo;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;

public class AmountSetupBiz {
	
	public long getSubLoanTypeId(long loanTypeId, long lOfficeID, long lCurrencyID)
		throws IException
	{
		long lSubLoanTypeId = -1;
		try {
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			Collection coll = biz.findByLoanTypeID(loanTypeId,lOfficeID,lCurrencyID);
			if(coll != null){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					LoanTypeSettingInfo info = (LoanTypeSettingInfo)it.next();
					lSubLoanTypeId = info.getId();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		return lSubLoanTypeId;
	}
	
	/**
	 * 授信额度设置，保存、保存并提交审批
	 * 
	 * @param asInfo  授信额度信息
	 * @param sasColl 品种授信额度信息
	 * @throws IException
	 */
	public void saveAmountSetup(AmountSetupInfo asInfo, Collection sasColl)
		throws IException
	{
		Connection conn = null;
		long lAmountSetupId = -1;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			//保存时的的验证
			this.saveValidate(asInfo, sasColl);

			AmountSetupDao asDao = new AmountSetupDao(conn);
			lAmountSetupId = asDao.add(asInfo);
			
			if(asInfo.getInutParameterInfo() != null){
				InutParameterInfo inutParameterInfo = asInfo.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(lAmountSetupId));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());

				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// 更新状态到"审批中"
				asInfo.setState(LOANConstant.CreditFlowStatus.APPROVALING);
				asInfo.setId(lAmountSetupId);
				asDao.updateAmountSetup(asInfo);
			}
			
			SubAmountSetupDao sasDao = new SubAmountSetupDao(conn);
			Iterator it = sasColl.iterator();
			while (it.hasNext()) {
				SubAmountSetupInfo sasInfo = (SubAmountSetupInfo)it.next();
				sasInfo.setAmountSetupId(lAmountSetupId);
				sasDao.add(sasInfo);
			}
			
			conn.commit();

		} catch (Exception e) {
			if(conn!=null)
			{
				try { 
					conn.rollback(); 
				} catch (SQLException e1) { 
					throw new IException("数据库操作异常" ,e); 
				}
			}
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new IException("数据库操作异常" ,e);
			}
		}
	}
	
	public void updateAmountSetup(AmountSetupInfo asInfo, Collection sasColl)
		throws IException
	{
		Connection conn = null;
		Iterator sasIt = null;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			//保存时的的验证
			this.saveValidate(asInfo, sasColl);
			
			AmountSetupDao asDao = new AmountSetupDao(conn);
			asDao.updateAmountSetup(asInfo);
			
			if(asInfo.getInutParameterInfo() != null){
				InutParameterInfo inutParameterInfo = asInfo.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(asInfo.getId()));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());
	
				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// 更新状态到"审批中"
				asInfo.setState(LOANConstant.CreditFlowStatus.APPROVALING);
				asDao.updateAmountSetup(asInfo);
			}
			
			SubAmountSetupDao sasDao = new SubAmountSetupDao(conn);
            //修改操作前删除掉以前的数据 modify by xwhe 20090608
			sasDao.deleteBatch(asInfo.getId());
			sasIt = sasColl.iterator();
			while (sasIt.hasNext()) {
				SubAmountSetupInfo sasInfo = (SubAmountSetupInfo)sasIt.next();
				sasInfo.setAmountSetupId(asInfo.getId());
				//sasDao.updateSubAmountSetup(sasInfo);
				sasDao.add(sasInfo);
			}
			
			conn.commit();
	
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new IException("数据库操作异常" ,e);
			}
		}
	}
	
	/**
	 * 授信额度设置保存前的校验，如果满足以下条件，则可以做授信：
	 * 1.该客户在该时间段内没有做过授信（保存，审批中的都算）；
	 * 2.如果是集团授信下挂的单一法人授信，集团的综合授信额度和单一法人授信额度是否足够；
	 * 
	 * @param asInfo
	 * @param sasColl
	 * @throws IException
	 */
	public void saveValidate(AmountSetupInfo asInfo, Collection sasColl)
		throws IException
	{
		AmountSetupDao asDao = null;
		AmountFormDao afDao = null;
		SubAmountFormDao safDao = null;
		CreditCheckBiz checkBiz = null;
		
		try {
			asDao = new AmountSetupDao();
			afDao = new AmountFormDao();
			safDao = new SubAmountFormDao();
			checkBiz = new CreditCheckBiz();
			
			//判断该客户是否进行过授信
			Collection coll = asDao.checkByCondition(asInfo);
			if(coll != null)
			{
				throw new IException("提交的时间范围中存在授信记录");
			}
			
			//已被占用的授信额度
			double dUsedAmount = 0.0;
			//剩余的授信额度
			double dRemainAmount = 0.0;
			//授信类型名称
			String strCreditTypeName = "";
			
			//检查关联集团授信的单一法人授信的授信金额占用情况
			if(asInfo.getCreditModel() == LOANConstant.CreditModel.SIMPLE && asInfo.getGroupCreditId() > 0)
			{
				AmountFormInfo queryInfo = new AmountFormInfo();
				queryInfo.setId(asInfo.getGroupCreditId());
				queryInfo.setOfficeId(asInfo.getOfficeId());
				queryInfo.setCurrencyId(asInfo.getCurrencyId());
				//获取集团授信实体
				AmountFormViewInfo groupAfvInfo = afDao.getAmountFormViewInfo(queryInfo);
				
				//已分给集团成员的授信额度
				dUsedAmount = checkBiz.getGroupLeaguerCreditAmount(groupAfvInfo);
				//集团剩余的授信额度
				dRemainAmount = UtilOperation.Arith.sub(groupAfvInfo.getCreditAmount(), dUsedAmount);
	
				if(Double.compare(asInfo.getCreditAmount(), dRemainAmount) > 0)
				{
					System.out.println("已被集团成员占用的授信额度：dUsedAmount = " + dUsedAmount);
					System.out.println("客户授信额度：CreditAmount = " + asInfo.getCreditAmount());
					System.out.println("集团剩余的授信额度：dRemainAmount = " + dRemainAmount);
					throw new IException("提交的授信额度大于集团的剩余授信额度");
				}
				
				Iterator it = sasColl.iterator();
				while (it.hasNext())
				{
					SubAmountSetupInfo sasInfo = (SubAmountSetupInfo)it.next();
					
					if(sasInfo.getCreditShare() == LOANConstant.CreditShare.NO)
					{
						//查询集团贷款类型子项
						SubAmountFormInfo groupSafInfo = safDao.getSubAmountFormInfo(groupAfvInfo.getAmountFormInfo(), sasInfo.getCreditType());
						groupAfvInfo.setSubAmountFormInfo(groupSafInfo);
						
						if(groupSafInfo.getCreditShare() == LOANConstant.CreditShare.NO)
						{
							//集团已被占用的授信额度（单一授信类型）
							dUsedAmount = checkBiz.getGroupLeaguerCreditAmount(groupAfvInfo);
							//集团剩余的授信额度（单一授信类型）
							dRemainAmount = UtilOperation.Arith.sub(groupSafInfo.getCreditAmount(), dUsedAmount);
							
							if(Double.compare(sasInfo.getCreditAmount(), dRemainAmount) > 0)
							{
								strCreditTypeName = LOANConstant.CreditType.getName(sasInfo.getCreditType());
								System.out.println("集团" + strCreditTypeName + "已被占用的授信额度：dUsedAmount = " + dUsedAmount);
								System.out.println("客户"+ strCreditTypeName +"授信额度：CreditAmount = " + sasInfo.getCreditAmount());
								System.out.println("集团" + strCreditTypeName + "剩余的授信额度：dRemainAmount = " + dRemainAmount);
								throw new IException("提交的"+ strCreditTypeName +"授信额度大于集团"+ strCreditTypeName +"授信的剩余额度");
							}
						}
					}
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
	}
	
	public void deleteAmountSetup(long id)
		throws IException
	{
		Connection conn = null;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			AmountSetupDao asDao = new AmountSetupDao(conn);
			asDao.delete(id);
			
			SubAmountSetupDao sasDao = new SubAmountSetupDao(conn);
			sasDao.deleteBatch(id);
			
			conn.commit();
	
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new IException("数据库操作异常" ,e);
			}
		}
	}
	
	public void doApprovalAmountSetup(AmountSetupInfo asInfo)
		throws IException
	{
		try {
			AmountSetupDao asDao = new AmountSetupDao();
			InutParameterInfo returnInfo = FSWorkflowManager.doApproval(asInfo.getInutParameterInfo());
			
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) 
			{
				asInfo.setState(LOANConstant.CreditFlowStatus.CHECK);
				asDao.updateAmountSetup(asInfo);
				
			}
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (returnInfo.isRefuse()) 
			{
				asInfo.setState(LOANConstant.CreditFlowStatus.SAVE);
				asDao.updateAmountSetup(asInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
	}
	
	public void cancelApprovalAmountSetup(AmountSetupInfo asInfo)
		throws IException
	{
		try {
			AmountSetupDao asDao = new AmountSetupDao();
			InutParameterInfo inutInfo = asInfo.getInutParameterInfo();
			
			if(asInfo.getActiveState() == LOANConstant.CreditFlowStatus.ACTIVE){
				throw new IException("授信已被激活不能取消审批");
			}
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutInfo);
				
				asInfo.setState(LOANConstant.CreditFlowStatus.SAVE);
				asDao.updateAmountSetup(asInfo);
			}
			else {
				throw new IException("取消审批异常");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
	}
	
	/**
	 * 查询某一授信记录明细
	 * 
	 * @param asInfo
	 * @return
	 * @throws IException
	 */
	public AmountSetupViewInfo getCreditAmountSetupView(AmountSetupInfo asInfo)
		throws IException
	{
		AmountSetupViewInfo asvInfo = null;
		try {
			AmountSetupDao asDao = new AmountSetupDao();
			asvInfo =  asDao.getCreditAmountSetupView(asInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		return asvInfo;
	}
	
	public Collection getSubCreditAmountSetupColl(AmountSetupInfo asInfo)
		throws IException
	{
		Collection coll = null;
		try {
			SubAmountSetupDao sasDao = new SubAmountSetupDao();
			coll =  sasDao.getSubCreditAmountSetupColl(asInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		return coll;
	}
	/**
	 * 返回授信设置子信息，只能用于页面显示
	 * 
	 * @param asInfo
	 * @return
	 * @throws IException
	 */
	public Collection getSubInfoCreditAmountSetupColl(AmountSetupInfo asInfo)
		throws IException
	{
		Collection colResult = new ArrayList();
		try {
			SubAmountSetupDao sasDao = new SubAmountSetupDao();
			Collection colSubAmountSetup =  sasDao.getSubCreditAmountSetupColl(asInfo);
			HashMap subAmountSetupMap = new HashMap();
			SubAmountSetupInfo curSubSetupInfo = null;
			
			if(colSubAmountSetup!=null && colSubAmountSetup.size()>0)
			{
				Iterator it = colSubAmountSetup.iterator();
				while(it.hasNext()){
					curSubSetupInfo = (SubAmountSetupInfo)it.next();
					subAmountSetupMap.put("" + curSubSetupInfo.getCreditType(), curSubSetupInfo);
				}
			}
			
			curSubSetupInfo = null;
			
			long[] lCreditTypes = LOANConstant.CreditType.getAllCode(asInfo.getOfficeId(), asInfo.getCurrencyId());
			for(int i=0; i<lCreditTypes.length; i++)
			{
				if((curSubSetupInfo=(SubAmountSetupInfo)subAmountSetupMap.get("" + lCreditTypes[i]))==null)
				{
					curSubSetupInfo = new SubAmountSetupInfo();
					curSubSetupInfo.setCreditType(lCreditTypes[i]);
					curSubSetupInfo.setCreditShare(LOANConstant.CreditShare.YES);
					curSubSetupInfo.setHistoryCreditAmount(0.0);
					curSubSetupInfo.setOperationSign(LOANConstant.OperationSign.ADDITION);
					curSubSetupInfo.setExcessPercent(100.0);
					curSubSetupInfo.setCreditAmount(0.0);
				}
				
				colResult.add(curSubSetupInfo);
			}
			
		} catch (Exception e) {
			throw new IException(e.getMessage() ,e);
		}
		return colResult;
	}
}
