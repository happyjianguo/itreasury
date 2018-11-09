package com.iss.itreasury.credit.check;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.iss.itreasury.credit.exp.CreditFlexException;
import com.iss.itreasury.credit.exp.CreditRigidityException;
import com.iss.itreasury.credit.setting.dao.AmountFormDao;
import com.iss.itreasury.credit.setting.dao.AmountSetupDao;
import com.iss.itreasury.credit.setting.dao.SubAmountFormDao;
import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.QueryLoanUsedAmountInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountFormInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class CreditCheckBiz {
	
	private Log4j logger = new Log4j(Constant.ModuleType.LOAN, this);
	
	private long[] creditTypeID = null; //当前事务中有效的授信品种	
	
	/**
	 * 在贷款申请提交时，校验授信额度是否足够
	 * 
	 * @param applyInfo 贷款申请信息
	 * @return
	 * @throws IException
	 */
	public void checkLoanApply(LoanApplyInfo applyInfo)
		throws IException {
		
		Connection conn = null;

		try{
			// 读取配置文件判断是否有贷款授信业务
			if(!Config.getBoolean(ConfigConstant.LOAN_CREDIT_INTEGRATIONCREDIT, false))
			{
				return;
			}
			
			//判断该贷款业务是否应受授信控制
			if(!isInCreditControl(applyInfo))
			{
				return;
			}
			
			conn = Database.getConnection();
			
			AmountFormDao afDao = new AmountFormDao(conn);
			SubAmountFormDao safDao = new SubAmountFormDao(conn);
			
			AmountFormViewInfo afvInfo = null;
			SubAmountFormInfo safInfo = null;
			/**
			 * 判断该笔贷款是否存在授信控制，条件如下：
			 * 1.该客户存在授信；
			 * 2.该贷款的起始日期在授信有效期内
			 */
			AmountFormInfo queryInfo = new AmountFormInfo();
			
			queryInfo.setOfficeId(applyInfo.getOfficeID());
			queryInfo.setCurrencyId(applyInfo.getCurrencyID());
			queryInfo.setClientId(applyInfo.getBorrowClientID());
			queryInfo.setStartDate(applyInfo.getStartDate());
			
			afvInfo = afDao.findAmountFormViewInfo(queryInfo);			
			if(afvInfo == null || afvInfo.getId()<=0)
			{
				if(Config.getBoolean(ConfigConstant.CREDIT_ISCREDITPROMPT, false))
				{
					//客户没有授信记录
					throw new CreditFlexException("该客户在贷款日期内不存在有效的授信记录");
				}
				else
				{
					return;
				}
			}
			
			safInfo = safDao.getSubAmountFormInfo(afvInfo.getAmountFormInfo(), applyInfo.getTypeID());

			/**
			 *　校验客户本身的授信额度是否足够 
			 */
			validateSimpleCreditAmount(applyInfo, afvInfo, safInfo, afDao);
			
			//如果在集团授信的控制之下
			if(isInGroupCreditControl(afvInfo))
			{
				long groupAmountFormID = -1; //集团授信的授信ID
				
				if(afvInfo.getCreditModel()==LOANConstant.CreditModel.GROUP)
				{
					groupAmountFormID = afvInfo.getId();
				}
				else{
					groupAmountFormID = afvInfo.getGroupCreditId();
				}
				
				if(groupAmountFormID<=0) throw new IException("集团授信无效");
				
				AmountFormInfo groupAmountFormInfo = 
						(AmountFormInfo) afDao.findByID(groupAmountFormID, AmountFormInfo.class);
				
				if(groupAmountFormInfo == null)
				{
					throw new IException("集团授信不存在，[ID：" + groupAmountFormID + "]");
				}
				
				SubAmountFormInfo groupSubAmountFormInfo = 
					safDao.getSubAmountFormInfo(groupAmountFormInfo, applyInfo.getTypeID());		

				/**
				 *　校验整个集团的授信额度是否足够
				 */
				validateGroupCreditAmount(applyInfo, groupAmountFormInfo, groupSubAmountFormInfo, afDao);
			}
		}
		catch(IException exp1){
			throw exp1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
			
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
		}
	}


	/**
	 * 校验贷款申请金额是否超时整个集团的可用额度
	 * 
	 * 
	 * @param applyInfo 申请信息
	 * @param groupAmountFormInfo 集团授信信息
	 * @param groupSubAmountFormInfo　集团授信的品种授信信息
	 * @param afDao
	 * @throws CreditRigidityException 柔性异常
	 * @throws CreditFlexException 刚性异常
	 * @throws IException 其他异常
	 */
	private void validateGroupCreditAmount(LoanApplyInfo applyInfo,
			AmountFormInfo groupAmountFormInfo, 
			SubAmountFormInfo groupSubAmountFormInfo, 
			AmountFormDao afDao) throws IException 
	{			
		QueryLoanUsedAmountInfo qUsedAmountInfo = new QueryLoanUsedAmountInfo();
		qUsedAmountInfo.setId(groupAmountFormInfo.getId());
		qUsedAmountInfo.setClientId(groupAmountFormInfo.getClientId());
		qUsedAmountInfo.setOfficeId(applyInfo.getOfficeID());
		qUsedAmountInfo.setCurrencyId(applyInfo.getCurrencyID());
		qUsedAmountInfo.setCheckDate(groupAmountFormInfo.getEndDate());
				
		double dCurLoanTypeUsedAmount = 0.0;
		double dRemainAmount = 0.0;
		double examineAmount = applyInfo.getExamineAmount();
		
		boolean isCreditNotShare = false;
		if(groupSubAmountFormInfo!=null && groupSubAmountFormInfo.getId()>0)
		{
			isCreditNotShare = true;
			logger.debug("开始校验申请金额是否超过本集团的品种授信");
			
			qUsedAmountInfo.setLoanType(applyInfo.getTypeID());
			dCurLoanTypeUsedAmount = afDao.getUsedGroupCreditAmount(qUsedAmountInfo);

			//计算品种授信的可用余额
			dRemainAmount = UtilOperation.Arith.sub(
					UtilOperation.Arith.div(UtilOperation.Arith.mul(
							groupSubAmountFormInfo.getCreditAmount(), groupSubAmountFormInfo.getExcessPercent()), 100), 
							dCurLoanTypeUsedAmount);
			
			logger.debug("集团品种授信的可用余额为：" + dRemainAmount);
			
			if(Double.compare(examineAmount, dRemainAmount) > 0)
			{
				if(groupAmountFormInfo.getControlType() == LOANConstant.ControlType.RIGIDITY)
				{
					 throw new CreditRigidityException("申请金额超过集团该授信品种的可用余额");
				}
				else 
				{
					//申请的贷款金额超出授信余额
					throw new CreditFlexException("申请金额超过集团该授信品种的可用余额");
				}
			}	
		}
		
		double dUsedAmount = 0.0; 
		dRemainAmount = 0.0;
		
		//计算综合授信额度
		for(int i=0; i<this.creditTypeID.length; i++)
		{
			if(this.creditTypeID[i]==applyInfo.getTypeID() && isCreditNotShare)
			{
				dUsedAmount+=dCurLoanTypeUsedAmount;
			}else{
				qUsedAmountInfo.setLoanType(this.creditTypeID[i]);
				dUsedAmount = UtilOperation.Arith.add(
						dUsedAmount, 
						afDao.getUsedGroupCreditAmount(qUsedAmountInfo));				
			}
		}
		
		dRemainAmount = UtilOperation.Arith.sub(groupAmountFormInfo.getCreditAmount(), dUsedAmount);
		
		logger.debug("集团综合授信的可用余额为：" + dRemainAmount);

		if(Double.compare(examineAmount, dRemainAmount) > 0)
		{
			if(groupAmountFormInfo.getControlType() == LOANConstant.ControlType.RIGIDITY)
			{
				 throw new CreditRigidityException("申请金额超出集团综合授信的可用余额");
			}
			else 
			{
				//申请的贷款金额超出授信余额
				throw new CreditFlexException("申请金额超出集团综合授信的可用余额");
			}
		}
	}


	/**
	 * 
	 * 检查该客户本身的授信可用余额是否足够
	 * 
	 * @param applyInfo 贷款申请信息
	 * @param afvInfo 授信额度信息
	 * @param afDao 授信dao
	 * @throws CreditRigidityException 柔性异常
	 * @throws CreditFlexException 刚性异常
	 * @throws IException 其他异常
	 */
	private void validateSimpleCreditAmount(
			LoanApplyInfo applyInfo,
			AmountFormViewInfo afvInfo,
			SubAmountFormInfo safInfo,
			AmountFormDao afDao)
			throws IException{
		
		double dCurLoanTypeUsedAmount = 0.0; //当前贷款类型已占用额度
		double dRemainAmount = 0.0;
		double examineAmount = applyInfo.getExamineAmount();
		
		QueryLoanUsedAmountInfo qUsedAmountInfo = new QueryLoanUsedAmountInfo();
		
		qUsedAmountInfo.setId(afvInfo.getId());
		qUsedAmountInfo.setClientId(afvInfo.getClientId());
		qUsedAmountInfo.setCheckDateStart(afvInfo.getStartDate());			
		qUsedAmountInfo.setCheckDate(afvInfo.getEndDate());			
		qUsedAmountInfo.setOfficeId(applyInfo.getOfficeID());
		qUsedAmountInfo.setCurrencyId(applyInfo.getCurrencyID());
				
		boolean isCreditNotShare = false; //是否存在该贷款类型的品种授信,false,不存在
		if(safInfo != null && safInfo.getId()>0)
		{				
			isCreditNotShare = true;			
			logger.debug("开始校验申请金额是否超过本客户的品种授信");
			
			qUsedAmountInfo.setLoanType(applyInfo.getTypeID());
			dCurLoanTypeUsedAmount = afDao.getUsedLoanAmount(qUsedAmountInfo);			
			//计算品种授信的可用余额
			dRemainAmount = UtilOperation.Arith.sub(
					UtilOperation.Arith.div(UtilOperation.Arith.mul(
							safInfo.getCreditAmount(), safInfo.getExcessPercent()), 100), 
							dCurLoanTypeUsedAmount);
			
			logger.debug("品种授信的可用余额为：" + dRemainAmount);
			
			if(Double.compare(examineAmount, dRemainAmount) > 0)
			{
				if(afvInfo.getControlType() == LOANConstant.ControlType.RIGIDITY)
				{
					 throw new CreditRigidityException("申请金额超出本客户该授信品种的授信金额");
				}
				else 
				{
					//申请的贷款金额超出授信余额
					throw new CreditFlexException("申请金额超出本客户该授信品种的授信金额");
				}
			}				
		}
		
		logger.debug("开始校验申请金额是否超过本客户的综合授信");
		
		double dUsedAmount = 0.0; //综合授信已占用额度
		
		for(int i=0; i<this.creditTypeID.length; i++)
		{
			if(this.creditTypeID[i]==applyInfo.getTypeID() && isCreditNotShare)
			{
				dUsedAmount+=dCurLoanTypeUsedAmount;
			}else{
				qUsedAmountInfo.setLoanType(this.creditTypeID[i]);
				dUsedAmount = UtilOperation.Arith.add(
						dUsedAmount, 
						afDao.getUsedLoanAmount(qUsedAmountInfo));				
			}
		}
		
		dRemainAmount = UtilOperation.Arith.sub(afvInfo.getCreditAmount(), dUsedAmount);
		
		logger.debug("综合授信的可用余额为：" + dRemainAmount);

		if(Double.compare(examineAmount, dRemainAmount) > 0)
		{
			if(afvInfo.getControlType() == LOANConstant.ControlType.RIGIDITY)
			{
				 throw new CreditRigidityException("该客户的申请金额超出其综合授信的可用余额");
			}
			else 
			{
				throw new CreditFlexException("该客户的申请金额超出其综合授信的可用余额");
			}
		}
	}
	
	
	/**
	 * 判断该授信是否在集团授信的控制之下
	 * 
	 * @param afvInfo
	 * @return
	 */
	private boolean isInGroupCreditControl(AmountFormViewInfo afvInfo)
	{
		if(afvInfo.getGroupCreditId()>0 
				|| afvInfo.getCreditModel()==LOANConstant.CreditModel.GROUP)
		{
			return true;
		}
		return false;
	}
	/**
	 * 判断当前的贷款业务是否应受授信的控制
	 * 
	 * @param applyInfo
	 * @return
	 */
	private boolean isInCreditControl(LoanApplyInfo applyInfo) {
		//将当前可用的授信品种值在当前biz对象里共享，减少数据库查询次数
		initCreditTypeID(applyInfo.getOfficeID(), applyInfo.getCurrencyID());
		
		for(int i=0; i<creditTypeID.length; i++)
		{
			if(creditTypeID[i]==applyInfo.getTypeID())
				return true;
		}
		
		return false;
	}
	
	/**
	 * 初始化creditType
	 * 
	 * @param officeID
	 * @param currencyID
	 */
	private void initCreditTypeID(long officeID, long currencyID){
		if(this.creditTypeID==null){
			this.creditTypeID = LOANConstant.CreditType
				.getAllCode(officeID, currencyID);
		}
		
		if(creditTypeID ==null){
			creditTypeID = new long[0];
		}		
	}

	/**
	 * 统计集团成员(不包括集团本身)所分配的授信金额（授信记录保存时使用）
	 * @param afInfo
	 * @return
	 * @throws IException
	 */
	public double getGroupLeaguerCreditAmount(AmountFormViewInfo queryInfo)
		throws IException {

	  	double dCreditFormAmount = 0.0;
	  	double dCreditSetupAmount = 0.0;
	  	//已被占用的授信额度
	  	double dUsedAmount = 0.0;
	  	
	  	AmountFormDao afDao = null;
	  	AmountSetupDao asDao = null;
		
		try{
			afDao = new AmountFormDao();
			asDao = new AmountSetupDao();

			dCreditFormAmount = afDao.getGroupLeaguerCreditAmount(queryInfo);
			dCreditSetupAmount = asDao.getGroupLeaguerCreditAmount(queryInfo);
			
			dUsedAmount = UtilOperation.Arith.add(dCreditFormAmount, dCreditSetupAmount);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		return dUsedAmount;
	}
	
					
	/**
	 * 
	 * 统计单一法人客户贷款占用授信总金额(=申请金额+合同金额-非循环贷款时还款单金额)
	 * 
	 * @author xintan
	 * @param afInfo
	 * @param lCreditType
	 * @return
	 * @throws IException
	 */
	public double getUsedCreditAmount(AmountFormViewInfo queryInfo)
		throws IException {

		double dUsedAmount = 0.0; //综合授信已占用额度
		
		try{
		  	AmountFormDao afDao = new AmountFormDao();
						
			initCreditTypeID(queryInfo.getOfficeId(), queryInfo.getCurrencyId());
			
			QueryLoanUsedAmountInfo qUsedAmountInfo = new QueryLoanUsedAmountInfo();
			
			qUsedAmountInfo.setId(queryInfo.getId());
			qUsedAmountInfo.setClientId(queryInfo.getClientId());
			qUsedAmountInfo.setCheckDate(queryInfo.getEndDate());		
			qUsedAmountInfo.setCheckDateStart(queryInfo.getStartDate());
			qUsedAmountInfo.setOfficeId(queryInfo.getOfficeId());
			qUsedAmountInfo.setCurrencyId(queryInfo.getCurrencyId());
			
			List infos = queryInfo.getSubAmountFormList();
			for(int i=0; i<this.creditTypeID.length; i++)
			{
					qUsedAmountInfo.setLoanType(this.creditTypeID[i]);
					double amount = afDao.getUsedLoanAmount(qUsedAmountInfo);
					dUsedAmount = UtilOperation.Arith.add(
							dUsedAmount, 
							amount);	
					for(int j=0;j<infos.size();j++){
						SubAmountFormInfo subAmountFormInfo = (SubAmountFormInfo) infos.get(j);
						if(subAmountFormInfo.getCreditType()==this.creditTypeID[i]){
							subAmountFormInfo.setUsedAmount(amount);
							j=0;
							break;
						}
					}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		
		return dUsedAmount;
	}
	

	/**
	 * 计算某一集团授信客户的已占用额度
	 * 
	 * @param queryInfo
	 * @return
	 * @throws IException
	 */
	public double getGroupUsedCreditAmount(AmountFormViewInfo queryInfo) throws IException {
		
		double dUsedAmount = 0.0; //综合授信已占用额度
		
		try{
		  	AmountFormDao afDao = new AmountFormDao();
						
			initCreditTypeID(queryInfo.getOfficeId(), queryInfo.getCurrencyId());
			
			QueryLoanUsedAmountInfo qUsedAmountInfo = new QueryLoanUsedAmountInfo();
			
			qUsedAmountInfo.setId(queryInfo.getId());
			qUsedAmountInfo.setClientId(queryInfo.getClientId());
			qUsedAmountInfo.setCheckDateStart(queryInfo.getStartDate());			
			qUsedAmountInfo.setCheckDate(queryInfo.getEndDate());			
			qUsedAmountInfo.setOfficeId(queryInfo.getOfficeId());
			qUsedAmountInfo.setCurrencyId(queryInfo.getCurrencyId());
			
			for(int i=0; i<this.creditTypeID.length; i++)
			{
					qUsedAmountInfo.setLoanType(this.creditTypeID[i]);
					dUsedAmount = UtilOperation.Arith.add(
							dUsedAmount, 
							afDao.getUsedGroupCreditAmount(qUsedAmountInfo));				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		
		return dUsedAmount;
	}	
	
}
