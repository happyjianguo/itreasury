package com.iss.itreasury.loan.integrationcredit.bizlogic;

import java.sql.SQLException;
import java.util.Collection;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditProductInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public abstract class AbstractCreditBiz {

	/**
	 * 根据贷款类型查询授信类型
	 * 
	 * @param loanTypeID
	 * @return
	 */
	public abstract long getCreditTypeByLoanType(long loanTypeID) ;
	
	/**
	 * 根据授信品种类型查询对应的贷款类型
	 * 
	 * @param creditProductType
	 * @return
	 * @throws Exception
	 */
	public abstract long typeTrans(long creditProductType) throws IException ;
	
	/**
	 * 根据授信类型ID 查询授信分类设置
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	public abstract CreditProductInfo findCreditProductInfo(CreditProductInfo qInfo) throws IException;
	

	/**
	 * 保存授信产品分类设置信息
	 * 
	 * @param CreditProductInfo
	 * @return
	 * @throws Exception
	 */
	public abstract long saveProductInfo(CreditProductInfo info) throws IException;
	
	/**
	 * 
	 * 检查客户的所有上级客户是否已作过集团授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public abstract boolean isParentCredit(CorporationInfo corporationInfo, CreditLimitDetailInfo detailInfo) throws IException;
	
	/**
	 * 
	 * 检查客户在做集团授信时客户的所有下级客户是否已作过授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public abstract boolean isChildCredit(CreditLimitDetailInfo detailInfo) throws IException;
	
	/***************************************************************************/
	
	/**
	 * 新增授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public abstract long saveCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException;
	
	/**
	 * 修改授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public abstract long updateCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException;
	
	/**
	 * 删除授信信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @throws IException
	 */
	public abstract long deleteCreditLimitDetailInfo(CreditLimitDetailInfo info) throws IException;
	
	/**
	 * 根据授信额度ID查询额度设置信息（包括额度的使用情况）
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract CreditLimitInfo findCreditLimitInfoByID(long id) throws IException;
	
	/**
	 * 通过ID获得授信信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract CreditLimitDetailInfo findCreditLimitDetailInfoByID(long id) throws IException;
	
	/**
	 * 授信设置审批
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public abstract void doApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException;
	
	/**
	 * 授信设置取消审批
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public abstract void cancelApprovalCreditLimitDetail(CreditLimitDetailInfo info) throws IException;

	/**
	 * 转换一个贷款ID为一个授信ID
	 * 
	 * @param loanTypeID
	 * @return
	 * @throws Exception
	 */
	public abstract long switchLoanTypeId(long loanTypeID);

	/**
	 * 转换一个授信ID为一个贷款ID
	 * 
	 * @param creditTypeID
	 * @return
	 * @throws Exception
	 */
	public abstract long switchCreditTypeId(long creditTypeID);
	
	/**
	 * 综合授信时，检查授信产品的分类设置
	 */
	public abstract boolean checkCreditProduct(long officeId, long currencyId, long creditTypeId) throws IException;
	
	
	/**
	 * 授信金额及日期控制主方法
	 * 
	 */
	public abstract boolean checkCreditLimit(LoanApplyInfo applyInfo) throws IException;
	
	/**
	 * 判断贷款申请的日期是否在授信的期限之内
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public abstract boolean checkCreditAmountAndDate(CreditLimitInfo queryCreditInfo, CreditProductInfo creditProductInfo, LoanApplyInfo applyInfo) throws IException;
	
	/**
	 * 取出客户已占用的金额
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public abstract double getClientCountAmount(CreditLimitInfo creditInfo) throws IException;
	
	/**
	 * 取出历史客户已占用的金额
	 * 
	 * @param CreditLimitInfo
	 * @return boolean
	 * @throws Exception
	 */
	public abstract double getHistoryClientCountAmount(CreditLimitInfo creditInfo) throws IException;
	
	/**
	 * 
	 * 查找客户的所有上级客户是否已作过集团授信，并取得上级客户的集团授信
	 * 
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public abstract CreditLimitInfo getParentCreditLimitInfo(CorporationInfo corporationInfo, CreditLimitInfo limitInfo) throws IException;
	
	/**
	 * 查询授信额度信息(激活/取消激活)
	 * 
	 */
	public abstract Collection findLimitDetailInfoByCondition(CreditLimitDetailInfo qInfo)throws IException;
	
	/**
	 * 查询授信记录信息
	 * 
	 */
	public abstract PageLoader findLimitDetailHistoryInfoByCondition(CreditLimitDetailInfo qInfo)throws IException;
	/**
	 * 授信记录信息排序
	 * 
	 */
	public abstract String getCreditLimitDetailOrderSQL(long lOrder, long lDesc) throws IException;;
	/**
	 * 查询授信额度信息
	 * 
	 */
	public abstract Collection findLimitInfoByCondition(CreditLimitInfo qInfo)throws Exception;
	
	/**
	 * 激活/取消授信额度变更信息
	 * 
	 * @param info
	 * @throws IException
	 * @throws Exception
	 */
	public abstract void activeCreditLimitDetail(long creditId, long activeUserId)throws IException;
	
	/**
	 * 获得授信设置子类型
	 * 
	 * @param loanTypeId
     * @param lOfficeID
     * @param lCurrencyID
	 * @throws IException
	 * @throws Exception
	 */
	public abstract long getCreditSubLoanTypeId(long loanTypeId,long lOfficeID,long lCurrencyID)throws IException;
}
