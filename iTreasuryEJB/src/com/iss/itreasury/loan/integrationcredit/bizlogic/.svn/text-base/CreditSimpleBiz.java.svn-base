package com.iss.itreasury.loan.integrationcredit.bizlogic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.clientmanage.client.dao.CorporationDAO;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitDetailSimpleDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditLimitSimpleDAO;
import com.iss.itreasury.loan.integrationcredit.dao.CreditProductDAO;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditProductInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.query.dao.QueryDao;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class CreditSimpleBiz extends AbstractCreditBiz{
	
	/**
	 * 根据贷款类型查询授信类型
	 * 
	 * @param loanTypeID
	 * @return
	 */
	public long getCreditTypeByLoanType(long loanTypeID) {

		long ret = -1;
		switch ((int) loanTypeID) {
		case (int) LOANConstant.LoanType.ZY:
			ret = LOANConstant.CreditProduct.ZY;
			break;
		case (int) LOANConstant.LoanType.TX:
			ret = LOANConstant.CreditProduct.SP;
			break;
		case (int) LOANConstant.LoanType.DB:
			ret = LOANConstant.CreditProduct.BH;
			break;
		}
		return ret;
	}

	/**
	 * 根据授信品种类型查询对应的贷款类型
	 * 
	 * @param creditProductType
	 * @return
	 * @throws Exception
	 */
	public long typeTrans(long creditProductType) throws IException {
		long loanType = -1;
		try {
			switch ((int) creditProductType) {
			case (int) LOANConstant.CreditProduct.ZY:
				loanType = LOANConstant.LoanType.ZY;
				break;
			case (int) LOANConstant.CreditProduct.SP:
				loanType = LOANConstant.LoanType.TX;
				break;
			case (int) LOANConstant.CreditProduct.BH:
				loanType = LOANConstant.LoanType.DB;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("转换错误",e);
		}
		return loanType;
	}

	
	/***************************************************************************/
	
	
	/**
	 * 根据授信类型ID 查询授信分类设置
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	public CreditProductInfo findCreditProductInfo(CreditProductInfo qInfo)
			throws IException {
		CreditProductInfo info = null;
		CreditProductDAO dao = new CreditProductDAO();

		try {
			info = dao.findByCreditTypeID(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return info;
	}
	

	/**
	 * 保存授信产品分类设置信息
	 * 
	 * @param CreditProductInfo
	 * @return
	 * @throws Exception
	 */
	public long saveProductInfo(CreditProductInfo info) throws IException {
		long ret = -1;
		CreditProductDAO dao = new CreditProductDAO();
		CreditProductInfo qInfo = null;

		try {
			qInfo = dao.findByCreditTypeID(info);
			
			if(qInfo == null){
				info.setLoanTypeID(this.switchCreditTypeId(info.getCreditTypeID()));
				ret = dao.add(info);
			}
			else {
				qInfo.setOfficeID(info.getOfficeID());    
				qInfo.setCurrencyID(info.getCurrencyID());
				qInfo.setUpdateTime(info.getUpdateTime());
				qInfo.setUpdateUserID(info.getUpdateUserID());
				qInfo.setCreditTypeID(info.getCreditTypeID());
				qInfo.setLoanTypeID(this.switchCreditTypeId(info.getCreditTypeID()));
				qInfo.setIsControl(info.getIsControl());
				qInfo.setControlTypeID(info.getControlTypeID());
				qInfo.setEngrossRate(info.getEngrossRate()); 
				qInfo.setStatusID(info.getStatusID()); 
				dao.update(qInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信产品分类设置错误",e);
		}
		return ret;
	}
	
	
	/***************************************************************************/
	
	/**
	 * 
	 * 检查客户的所有上级客户是否已作过集团授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean isParentCredit(CorporationInfo corporationInfo, CreditLimitDetailInfo detailInfo) throws IException
	{    
		 boolean isCredit = false;
		 try {
			 CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
			 CreditLimitDetailInfo  queryinfo = new CreditLimitDetailInfo();
			 queryinfo.setCreditLimitID(detailInfo.getCreditLimitID());
			 queryinfo.setCreditTypeID(detailInfo.getCreditTypeID());
			 queryinfo.setOfficeID(detailInfo.getOfficeID());
			 queryinfo.setCurrencyID(detailInfo.getCurrencyID());
			 queryinfo.setStartDate(detailInfo.getStartDate());
			 queryinfo.setEndDate(detailInfo.getEndDate());
			 queryinfo.setClientID(corporationInfo.getParentCorpID1());
			 queryinfo.setCreditModeID(LOANConstant.CreditMode.GROUP);
			 
			 Collection c = dao.findByDateOption(queryinfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX, true);
			 
//			 if(corporationInfo.getParentCorpID1() == -1){
//				 if(c == null){
//					 isCredit = true;
//				 }
//				 else {
//					 isCredit = false;
//				 }
//			 }
//			 else {
//				if(c == null){
////					CorporationDAO corporationDAO = new CorporationDAO();
////					CorporationInfo parentCInfo = corporationDAO.findByclietID(corporationInfo.getParentCorpID1());
////					isCredit = isParentCredit(parentCInfo, detailInfo);
//					isCredit = true;
//				}
//				 else {
//					isCredit = false;
//				 }
//			 }
			 if(c == null){
				 isCredit = true;
			 }
			 else{
				 isCredit = false;
			 }
		 }
		 catch (Exception e) {
			 throw new IException("查询上级客户授信出错");
		 }
		 return isCredit;
    }
	
	/**
	 * 
	 * 检查客户在做集团授信时客户的所有下级客户是否已作过授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean isChildCredit(CreditLimitDetailInfo detailInfo) throws IException{
		 boolean isCredit = false;
		 try {
			 CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
			 
			 Collection c = dao.findByDateOptionAndChildClient(detailInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			 
			 if(c == null){
				 isCredit = true;
			 }
			 else{
				 isCredit = false;
			 }
		 }
		 catch (Exception e) {
			 throw new IException("查询下级客户授信出错");
		 }
		 return isCredit;
	}
	
	/***************************************************************************/
	
	/**
	 * 新增授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long saveCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();

		try {
			// 判断是否做过授信额度设置
			Collection c = dao.findByDateOption(info, LOANConstant.queryCreditProduct.SELF_AND_ZHSX, false);
			if (c != null) {
				throw new IException("Loan_E121");
			}
			
			//检查客户的所有上级客户是否已作过集团授信
			CorporationDAO corporationDAO = new CorporationDAO();
			CorporationInfo corporationInfo = corporationDAO.findByclietID(info.getClientID());
			boolean isCredit = isParentCredit(corporationInfo, info);
			if(isCredit == false){
				throw new IException("客户上级已设置过集团授信");
			}
			
			//检查客户在做集团授信时客户的所有下级客户是否已作过授信
			if(info.getCreditModeID() == LOANConstant.CreditMode.GROUP){
				isCredit = isChildCredit(info);
				if(isCredit == false){
					throw new IException("客户下级已设置过授信");
				}
			}

			lID = dao.add(info);
			
			if(info.getInutParameterInfo()!=null){
				
				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(lID));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());

				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// 更新状态到"审批中"
				info.setStatusID(LOANConstant.LoanStatus.APPROVALING);
				info.setId(lID);
				dao.update(info);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信额度设置失败 ：" + e.getMessage(),e);
		}
		return lID;
	}
	
	/**
	 * 修改授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long updateCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();

		try {
			//检查客户的所有上级客户是否已作过集团授信
			CorporationDAO corporationDAO = new CorporationDAO();
			CorporationInfo corporationInfo = corporationDAO.findByclietID(info.getClientID());
			boolean isCredit = isParentCredit(corporationInfo, info);
			if(isCredit == false){
				throw new IException("客户上级已设置过集团授信");
			}
			
			//检查客户在做集团授信时客户的所有下级客户是否已作过授信
			if(info.getCreditModeID() == LOANConstant.CreditMode.GROUP){
				isCredit = isChildCredit(info);
				if(isCredit == false){
					throw new IException("客户下级已设置过授信");
				}
			}
			
			if(info.getInutParameterInfo() != null){
				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
				inutParameterInfo.setTransID(String.valueOf(info.getId()));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl() + inutParameterInfo.getTransID());

				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
				// 更新状态到"审批中"
				info.setStatusID(LOANConstant.LoanStatus.APPROVALING);
			}
			dao.update(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("保存授信额度信息错误",e);
		}
		return lID;
	}
	
	/**
	 * 删除授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public long deleteCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException {

		long lID = -1;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();

		try {
			dao.update(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("删除授信额度信息错误",e);
		}
		return lID;
	}
	
	/**
	 * 根据授信额度ID查询额度设置信息（包括额度的使用情况）
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CreditLimitInfo findCreditLimitInfoByID(long id) throws IException {
		
		CreditLimitSimpleDAO dao = new CreditLimitSimpleDAO();
		CreditLimitInfo info = new CreditLimitInfo();
		try{
			
			info = (CreditLimitInfo) dao.findByID(id, CreditLimitInfo.class);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("查询额度设置信息错误",e);
		}
		return info;
	}
	
	/**
	 * 通过ID获得授信信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CreditLimitDetailInfo findCreditLimitDetailInfoByID(long id) throws IException {
		
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		CreditLimitDetailInfo cinfo = new CreditLimitDetailInfo();

		try{
			cinfo = (CreditLimitDetailInfo) dao.findByID(id, CreditLimitDetailInfo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("获得授信信息错误",e);
		}
		return cinfo;
	}
	
	public void doApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException {
		InutParameterInfo returnInfo = new InutParameterInfo();
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		
		try {
			returnInfo = FSWorkflowManager.doApproval(info.getInutParameterInfo());

			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) 
			{
				info.setStatusID(LOANConstant.LoanStatus.CHECK);
				dao.updateStatusID(info);
				
			}
			
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (returnInfo.isRefuse()) 
			{
				info.setStatusID(LOANConstant.LoanStatus.SAVE);
				dao.updateStatusID(info);
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IException("授信设置审批失败",ex);
		}
	}
	
	public void cancelApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException {
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		
		try {
			if(info.getActiveStatusID() == LOANConstant.CreditStatus.ACTIVE){
				throw new IException("授信记录已被激活不能取消审批");
			}
			
			info.setStatusID(LOANConstant.LoanStatus.SAVE);
			dao.updateStatusID(info);
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
			else {
				throw new Exception();
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IException(ex.getMessage(),ex);
		}
	}
	
	
	
	/*******************************************************************/
	/*************        贷款申请检查所用       *****************************/
	
	//转换一个贷款ID为一个授信ID
	public long switchLoanTypeId(long loanTypeID) {

		long ret = -1;
		switch ((int) loanTypeID) {
		case (int) LOANConstant.LoanType.ZY:
			ret = LOANConstant.CreditProduct.ZY;
			break;
		case (int) LOANConstant.LoanType.TX:
			ret = LOANConstant.CreditProduct.SP;
			break;
		case (int) LOANConstant.LoanType.DB:
			ret = LOANConstant.CreditProduct.BH;
			break;
		}
		return ret;
	}
	
	//转换一个授信ID为一个贷款ID
	public long switchCreditTypeId(long creditTypeID) {

		long ret = -1;
		switch ((int) creditTypeID) {
		case (int) LOANConstant.CreditProduct.ZY:
			ret = LOANConstant.LoanType.ZY;
			break;
		case (int) LOANConstant.CreditProduct.SP:
			ret = LOANConstant.LoanType.TX;
			break;
		case (int) LOANConstant.CreditProduct.BH:
			ret = LOANConstant.LoanType.DB;
			break;
		}
		return ret;
	}
	
	/**
	 * 综合授信时，检查授信产品的分类设置
	 */
	public boolean checkCreditProduct(long officeId, long currencyId, long creditTypeId) throws IException {
		boolean isCheck = false;
		CreditProductInfo creditProductInfo = null; 
		try {
			if(creditTypeId == LOANConstant.CreditProduct.ZHSX){
				long lArrayID[] = new long[3];
				lArrayID[0] = LOANConstant.CreditProduct.ZY;
				lArrayID[1] = LOANConstant.CreditProduct.SP;
				lArrayID[2] = LOANConstant.CreditProduct.BH;
				
				for(int i=0; i<lArrayID.length; i++){
					CreditProductInfo qInfo = new CreditProductInfo();
					qInfo.setOfficeID(officeId);
					qInfo.setCurrencyID(currencyId);
					qInfo.setCreditTypeID(lArrayID[i]);
					
					creditProductInfo = this.findCreditProductInfo(qInfo);
					
					if (creditProductInfo != null && creditProductInfo.getId() > 0) {
						if (creditProductInfo.getIsControl() == LOANConstant.ISRatingControl.NO) {
							isCheck = false;
							throw new IException("所有授信产品分类必须进行额度控制");
						}
					} else {
						isCheck = false;
						throw new IException("请设置所有授信产品分类");
					}
				}
				
				isCheck = true;
			}
			else {
				isCheck = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	
	/**
	 * 授信金额及日期控制主方法
	 * 
	 */
	public boolean checkCreditLimit(LoanApplyInfo applyInfo) throws IException {
		boolean isCheck = false; 
		try {
			// 读取配置文件判断是否有贷款授信业务
			boolean integrationcredit = Config.getBoolean(ConfigConstant.LOAN_CREDIT_INTEGRATIONCREDIT, false);
			
			if(integrationcredit){
				long creditTypeId = this.switchLoanTypeId(applyInfo.getTypeID());
				
				//然后把贷款申请的开始日期放入到CreditLimitInfo对象中
				CreditLimitInfo queryCreditInfo = new CreditLimitInfo();
				queryCreditInfo.setOfficeID(applyInfo.getOfficeID());
				queryCreditInfo.setCurrencyID(applyInfo.getCurrencyID());
				queryCreditInfo.setStartDate(applyInfo.getStartDate());
				queryCreditInfo.setCreditTypeID(creditTypeId);
				queryCreditInfo.setClientID(applyInfo.getBorrowClientID());
				
				// 是否进行过产品的分类设置
				// 先找对应的贷款类型
				CreditProductInfo qInfo = new CreditProductInfo();
				qInfo.setOfficeID(applyInfo.getOfficeID());
				qInfo.setCurrencyID(applyInfo.getCurrencyID());
				qInfo.setCreditTypeID(creditTypeId);
				
				CreditProductInfo creditProductInfo = this.findCreditProductInfo(qInfo);
				
				if (creditProductInfo != null && creditProductInfo.getId() > 0) {
					if (creditProductInfo.getIsControl() == LOANConstant.ISRatingControl.YES) {
						return this.checkCreditAmountAndDate(queryCreditInfo, creditProductInfo,applyInfo);
					}
					else {
						isCheck = true;
					}
				} else {
					isCheck = false;
					throw new IException("请设置授信产品分类");
				}
			}

			isCheck = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	/**
	 * 判断贷款申请的日期是否在授信的期限之内
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public boolean checkCreditAmountAndDate(CreditLimitInfo queryCreditInfo, CreditProductInfo creditProductInfo, LoanApplyInfo applyInfo)
			throws IException {
		boolean isCheck = false;
		CreditLimitSimpleDAO creditLimitSimpleDAO = null;
		CreditLimitInfo creditInfo = null;
		
		//申请批准金额
		double examineAmount = 0;
		//授信金额
	  	double creditAmount = 0;
	  	//客户将占用的总金额
	  	double countAmount = 0;
	  	//客户历史占用的总金额
	  	double historyCountAmount = 0;
	  	//占用金额总合
	  	double sumAmount = 0;
		
		try{
			creditLimitSimpleDAO = new CreditLimitSimpleDAO();
			
			// 判断贷款申请的开始日期是否在授信的期限之内
			creditInfo = creditLimitSimpleDAO.findDateByCreditLimit(queryCreditInfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			
			if (creditInfo != null) {
				  //检查出客户自己的授信记录
				  //申请批准金额
				  examineAmount = applyInfo.getExamineAmount();
				  //授信金额
		    	  creditAmount = creditInfo.getAmount();
		    	  //客户将占用的总金额
		    	  countAmount = this.getClientCountAmount(creditInfo);
		    	  //客户历史占用的总金额
		    	  historyCountAmount = this.getHistoryClientCountAmount(creditInfo);
		    	  
		    	  sumAmount = UtilOperation.Arith.add(countAmount, UtilOperation.Arith.mul(examineAmount, creditProductInfo.getEngrossRate()));
		    	  sumAmount = UtilOperation.Arith.add(sumAmount, historyCountAmount);
		    	  
		    	  System.out.println("查询类型：" + LOANConstant.CreditProduct.getName(creditInfo.getCreditTypeID()));
		    	  System.out.println("申请批准金额 examineAmount：" + examineAmount);
		    	  System.out.println("占用额度比率 engrossRate：" + creditProductInfo.getEngrossRate());
		    	  System.out.println("客户将占用的总金额 countAmount：" + countAmount);
		    	  System.out.println("客户历史占用的总金额 historyCountAmount：" + historyCountAmount);
		    	  System.out.println("占用金额总合 sumAmount：" + sumAmount);
		    	  System.out.println("授信金额 creditAmount：" + creditAmount);
	
		    	  if(Double.compare(sumAmount, creditAmount) > 0){
		    		  // 如果是刚性控制,返回一个标识位为true,如果是柔性控制,返回一个标识位为false
		    		  if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
		    			  isCheck = false;
		    			  throw new IException("您申请的金额超过授信余额");
		    		  } else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
		    			  isCheck = false;
		    			  throw new IException("CREDIT_1");
		    		  }
		    	  }
		    	  else {
		    		  isCheck = true;
		    	  }
			}
			else {
				CorporationDAO corporationDAO = new CorporationDAO();
				CorporationInfo corporationInfo = corporationDAO.findByclietID(queryCreditInfo.getClientID());
				creditInfo = this.getParentCreditLimitInfo(corporationInfo, queryCreditInfo);

				if(creditInfo != null){
					  //检查出客户自己的授信记录
					  //申请批准金额
					  examineAmount = applyInfo.getExamineAmount();
					  //授信金额
			    	  creditAmount = creditInfo.getAmount();
			    	  //客户将占用的总金额
			    	  countAmount = this.getClientCountAmount(creditInfo);
			    	  //客户历史占用的总金额
			    	  historyCountAmount = this.getHistoryClientCountAmount(creditInfo);
			    	  
			    	  sumAmount = UtilOperation.Arith.add(countAmount, UtilOperation.Arith.mul(examineAmount, creditProductInfo.getEngrossRate()));
			    	  sumAmount = UtilOperation.Arith.add(sumAmount, historyCountAmount);
			    	  
			    	  System.out.println("查询类型：" + LOANConstant.CreditProduct.getName(creditInfo.getCreditTypeID()));
			    	  System.out.println("申请批准金额 examineAmount：" + examineAmount);
			    	  System.out.println("占用额度比率 engrossRate：" + creditProductInfo.getEngrossRate());
			    	  System.out.println("客户将占用的总金额 countAmount：" + countAmount);
			    	  System.out.println("客户历史占用的总金额 historyCountAmount：" + historyCountAmount);
			    	  System.out.println("占用金额总合 sumAmount：" + sumAmount);
			    	  System.out.println("授信金额 creditAmount：" + creditAmount);
		
			    	  if(Double.compare(sumAmount, creditAmount) > 0){
			    		  // 如果是刚性控制,返回一个标识位为true,如果是柔性控制,返回一个标识位为false
			    		  if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.RIGIDITY) {
			    			  isCheck = false;
			    			  throw new IException("您申请的金额超过授信余额");
			    		  } else if (creditProductInfo.getControlTypeID() == LOANConstant.ControlMode.FLEXIBLE) {
			    			  isCheck = false;
			    			  throw new IException("CREDIT_1");
			    		  }
			    	  }
			    	  else {
			    		  isCheck = true;
			    	  }
				}
				else {
					isCheck = false;
					//throw new IException("申请开始日期与结束日期内没有授信记录");
					throw new IException("CREDIT_2");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return isCheck;
	}
	
	/**
	 * 取出客户已占用的金额
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public double getClientCountAmount(CreditLimitInfo creditInfo)
		throws IException {

	  	//客户已占用的申请金额
	  	double applyAmount = 0;
	  	//客户已占用的合同金额
	  	double contractAmount = 0;
	  	//非循环贷款时还款单金额
	  	double circleAmount = 0;
	  	//客户将占用的总金额
	  	double countAmount = 0;
	  	
	  	CreditLimitSimpleDAO creditLimitSimpleDAO = null;
		
		try{
			creditLimitSimpleDAO = new CreditLimitSimpleDAO();
			
			if (creditInfo != null) {
				  // 判断是否是综合授信，并检查所有类型是否进行过产品的分类设置
//				  if(this.checkCreditProduct(creditInfo.getOfficeID(), creditInfo.getCurrencyID(), creditInfo.getCreditTypeID()) == false){
//	    			  throw new IException("授信产品分类设置不完整");
//				  }
	
		    	  //根据客户信息查询贷款申请金额
		    	  applyAmount = creditLimitSimpleDAO.getClientLoanApplyAmount(creditInfo, false);
	
		    	  //根据客户信息查询贷款合同金额
		    	  contractAmount = creditLimitSimpleDAO.getClientLoanContractAmount(creditInfo, false);
		    	  
		    	  //自营贷款非循环贷款时还款单金额
		    	  circleAmount = creditLimitSimpleDAO.getClientCircleLoanAmount(creditInfo, false);
		    	  
		    	  //总计金额
		    	  countAmount = UtilOperation.Arith.sub(UtilOperation.Arith.add(applyAmount, contractAmount), circleAmount);
		    	  
		    	  System.out.println("客户已占用的申请金额 applyAmount：" + applyAmount);
		    	  System.out.println("客户已占用的合同金额 contractAmount：" + contractAmount);
		    	  System.out.println("非循环贷款时还款单金额 circleAmount：" + circleAmount);
		    	  System.out.println("客户占用总金额 countAmount：" + countAmount);
			}
			else {
				throw new IException("授信记录有误");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		
		return countAmount;
	}
	
	/**
	 * 取出历史客户已占用的金额
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public double getHistoryClientCountAmount(CreditLimitInfo creditInfo)
		throws IException {

	  	//客户历史已占用的申请金额
	  	double applyAmount = 0;
	  	//客户历史已占用的合同金额
	  	double contractAmount = 0;
	  	//历史非循环贷款时还款单金额
	  	double circleAmount = 0;
	  	//客户将占用的总金额
	  	double countAmount = 0;
	  	
	  	CreditLimitSimpleDAO creditLimitSimpleDAO = null;
		
		try{
			creditLimitSimpleDAO = new CreditLimitSimpleDAO();
			
			if (creditInfo != null) {
	
		    	  //根据客户信息查询历史贷款申请金额
		    	  applyAmount = creditLimitSimpleDAO.getClientLoanApplyAmount(creditInfo, true);
	
		    	  //根据客户信息查询历史贷款合同金额
		    	  contractAmount = creditLimitSimpleDAO.getClientLoanContractAmount(creditInfo, true);
		    	  
		    	  //历史自营贷款非循环贷款时还款单金额
		    	  circleAmount = creditLimitSimpleDAO.getClientCircleLoanAmount(creditInfo, true);
		    	  
		    	  //总计金额
		    	  countAmount = UtilOperation.Arith.sub(UtilOperation.Arith.add(applyAmount, contractAmount), circleAmount);
		    	  
		    	  System.out.println("客户历史已占用的申请金额 applyAmount：" + applyAmount);
		    	  System.out.println("客户历史已占用的合同金额 contractAmount：" + contractAmount);
		    	  System.out.println("历史非循环贷款时还款单金额 circleAmount：" + circleAmount);
		    	  System.out.println("客户占用总金额 countAmount：" + countAmount);
			}
			else {
				throw new IException("授信记录有误");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		
		return countAmount;
	}
	
	/**
	 * 
	 * 查找客户的上级客户是否已作过集团授信，并取得上级客户的集团授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public CreditLimitInfo getParentCreditLimitInfo(CorporationInfo corporationInfo, CreditLimitInfo limitInfo) throws IException
	{    
		CreditLimitInfo returnCInfo = null;
		 try {
			 CreditLimitSimpleDAO CreditLimitSimpleDAO = new CreditLimitSimpleDAO();
			 CreditLimitInfo  queryinfo = new CreditLimitInfo();
			 queryinfo.setOfficeID(limitInfo.getOfficeID());
			 queryinfo.setCurrencyID(limitInfo.getCurrencyID());
			 queryinfo.setCreditTypeID(limitInfo.getCreditTypeID());
			 queryinfo.setStartDate(limitInfo.getStartDate());
			 queryinfo.setClientID(corporationInfo.getParentCorpID1());
			 queryinfo.setCreditModeID(LOANConstant.CreditMode.GROUP);
			 
			 returnCInfo = CreditLimitSimpleDAO.findDateByCreditLimit(queryinfo, LOANConstant.queryCreditProduct.SELF_AND_ZHSX);
			 
//			 if(corporationInfo.getParentCorpID1() == -1){
//				 return returnCInfo;
//			 }
//			 else {
//				if(returnCInfo == null){
//					CorporationDAO corporationDAO = new CorporationDAO();
//					CorporationInfo parentCInfo = corporationDAO.findByclietID(corporationInfo.getParentCorpID1());
//					returnCInfo = getParentCreditLimitInfo(parentCInfo, limitInfo);
//				}
//				 else {
//					return returnCInfo;
//				 }
//			 }
		 }
		 catch (Exception e) {
			 throw new IException("查找上级客户授信出错");
		 }
		 return returnCInfo;
    }
	
	
	/**
	 * 查询授信额度信息(激活/取消激活)
	 * 
	 */
	public Collection findLimitDetailInfoByCondition(CreditLimitDetailInfo qInfo)
			throws IException {
		Collection c = null;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		try {
			c = dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("查询授信信息错误",e);
		}
		return c;
	}
	
	/**
	 * 查询授信记录信息
	 * 
	 */
	public PageLoader findLimitDetailHistoryInfoByCondition(CreditLimitDetailInfo qInfo)
			throws IException {
		PageLoader pageLoader = null;
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		try {
			 pageLoader = dao.findByHistoryMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("查询授信信息错误",e);
		}
		return pageLoader;
	}
	
	
	/**
	 * 查询授信额度信息
	 * 
	 */
	public Collection findLimitInfoByCondition(CreditLimitInfo qInfo)
			throws Exception {
		Collection c = null;
		CreditLimitSimpleDAO dao = new CreditLimitSimpleDAO();
		try {
			c = dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("查询授信额度信息错误",e);
		}
		return c;
	}
	
	/**
	 * 激活/取消授信额度变更信息
	 * 
	 * @param info
	 * @throws IException
	 * @throws Exception
	 */
	public void activeCreditLimitDetail(long creditId, long activeUserId)
		throws IException {
		
		CreditLimitSimpleDAO creditDao = null;
		CreditLimitDetailSimpleDAO creditDetailDao = null;
		CreditLimitInfo creditInfo = null;
		CreditLimitDetailInfo creditDetailInfo = null;
		Timestamp tsTime = Env.getSystemDateTime();
		long lReturn = -1;
		
		try {
			creditDao = new CreditLimitSimpleDAO();
			creditDetailDao = new CreditLimitDetailSimpleDAO();

			creditDetailInfo = (CreditLimitDetailInfo)creditDetailDao.findByID(creditId, CreditLimitDetailInfo.class);
			if(creditDetailInfo == null) {
				throw new IException("不能找到授信详细信息，激活失败");
			}
			
			if(creditDetailInfo.getChangeTypeID() == LOANConstant.changeType.XINZENG){
				//添加主表授信信息
				creditInfo = new CreditLimitInfo();
				creditInfo.setOfficeID(creditDetailInfo.getOfficeID());
				creditInfo.setCurrencyID(creditDetailInfo.getCurrencyID());
				creditInfo.setClientID(creditDetailInfo.getClientID());
				creditInfo.setAmount(creditDetailInfo.getChangeAmount());
				creditInfo.setCreditTypeID(creditDetailInfo.getCreditTypeID());
				creditInfo.setCreditModeID(creditDetailInfo.getCreditModeID());
				creditInfo.setStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditInfo.setStartDate(creditDetailInfo.getStartDate());
				creditInfo.setEndDate(creditDetailInfo.getEndDate());
				creditInfo.setInputUserID(creditDetailInfo.getInputUserID());
				creditInfo.setInputDate(tsTime);
				creditInfo.setActiveUserID(activeUserId);
				creditInfo.setActiveDate(tsTime);
				lReturn = creditDao.add(creditInfo);
				
				//修改授信详细信息
				creditDetailInfo.setCreditLimitID(lReturn);
				creditDetailInfo.setActiveUserID(activeUserId);
				creditDetailInfo.setActiveDate(tsTime);
				creditDetailInfo.setActiveStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditDetailDao.update(creditDetailInfo);
			}
			else {
				creditInfo = (CreditLimitInfo)creditDao.findByID(creditDetailInfo.getCreditLimitID(), CreditLimitInfo.class);
				if(creditInfo == null) {
					throw new IException("不能找到授信信息，激活失败");
				}
				
				double initAmount = creditInfo.getAmount();
				
				if(creditDetailInfo.getChangeTypeID() == LOANConstant.changeType.JIA){
					initAmount = initAmount + creditDetailInfo.getChangeAmount();
				}
				else {
					initAmount = initAmount - creditDetailInfo.getChangeAmount();
				}
				
				//修改主表授信信息
				creditInfo.setAmount(initAmount);
				creditInfo.setEndDate(creditDetailInfo.getEndDate());
				creditInfo.setActiveUserID(activeUserId);
				creditInfo.setActiveDate(tsTime);
				creditDao.update(creditInfo);
				
				//修改授信详细信息
				creditDetailInfo.setActiveUserID(activeUserId);
				creditDetailInfo.setActiveDate(tsTime);
				creditDetailInfo.setActiveStatusID(LOANConstant.CreditStatus.ACTIVE);
				creditDetailDao.update(creditDetailInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
	}
	
	public long getCreditSubLoanTypeId(long loanTypeId,long lOfficeID,long lCurrencyID)
		throws IException {
		long subLoanTypeId = -1;
		try {
			LoanTypeSettingBiz biz = new LoanTypeSettingBiz();
			Collection coll = biz.findByLoanTypeID(loanTypeId,lOfficeID,lCurrencyID);
			if(coll != null){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					LoanTypeSettingInfo info = (LoanTypeSettingInfo)it.next();
					subLoanTypeId = info.getId();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage(),e);
		}
		return subLoanTypeId;
	}
	//授信记录信息排序
	public String getCreditLimitDetailOrderSQL(long lOrder, long lDesc)
	{
		CreditLimitDetailSimpleDAO dao = new CreditLimitDetailSimpleDAO();
		return dao.getCreditLimitDetailOrderSQL(lOrder, lDesc);
	}
}	
	
