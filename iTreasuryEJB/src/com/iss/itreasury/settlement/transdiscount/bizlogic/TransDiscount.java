/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transdiscount.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountSubjectInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public interface TransDiscount extends javax.ejb.EJBObject {

	/**
	 * 审批流：审批通过， Added by zwsun ,2007-06-20
	 */
	public long doApproval(TransGrantDiscountInfo info)throws RemoteException, IRollbackException;
	/**
	 * 审批拒绝
	 */
	public long cancelApproval(TransGrantDiscountInfo info)throws RemoteException, IRollbackException;
	/**
	
	/** and by qulaian
	 * Method updateDiscountBillSave.
	 * 
	 * @param info
	 * @return int 票据修改接口 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateDiscountBillSave(long discount) throws RemoteException,
			IRollbackException;

	/**
	 * Method preSave.
	 * 
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantPreSave(TransGrantDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——暂存 逻辑操作： （1）如果lID不是-1，调用方法this.isTouched(),判断要暂存的记录是否被修改过。
	 * 调用方法Sett_TransGrantDiscountDAO.update()保存交易记录信息。 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransGrantDiscountDAO.add()保存交易记录信息。
	 * 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 暂存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantTempSave(TransGrantDiscountInfo info)
			throws RemoteException, IRollbackException;
	public long grantModifyTempSave(TransGrantDiscountInfo info)
			throws RemoteException, IRollbackException;
	/**
	 * 转贴现发放交易——暂存 逻辑操作： （1）如果lID不是-1，调用方法this.isTouched(),判断要暂存的记录是否被修改过。
	 * 调用方法Sett_TransGrantDiscountDAO.update()保存交易记录信息。 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransGrantDiscountDAO.add()保存交易记录信息。
	 * 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 暂存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantTempSave(TransDiscountDetailInfo info)
	throws RemoteException, IRollbackException;
	public long grantSave(TransDiscountDetailInfo info) throws RemoteException,
	IRollbackException;
	public long grantSubjectSave(TransDiscountSubjectInfo info) throws RemoteException,
	IRollbackException;
	/**
	 * 贴现发放交易——保存 逻辑操作： （1）判断参数TransGrantDiscountInfo交易实体类中的交易编号是否为空。 如果是空，说明是新增保存：
	 * 调用方法：UtilOperation.getNewTransactionNo()得到一个交易号，并将其写入TransGrantDiscountInfo 。 如果非空，说明是修改保存:
	 * 调用方法：this.isTouch,判断要暂存的记录是否被修改过。 调用方法：this.FindDetailByID(),得到原来的贴现发放交易实体类TransGrantDiscountInfo。
	 * 调用方法：AccountBookOperation.deleteGrantDiscount()。回滚原来的财务处理。注意参数是原来 的实体TransGrantDiscountInfo。
	 * （2）判断ID是否为－1，若是，则调用方法为：Sett_TransGrantDiscountDAO.add() 。 不是，则调用方法为：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 
	 * （3）调用方法：AccountBookOperation.saveGrantDiscount()。进行财务处理。 （4）调用方法：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 修改交易的状态为保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现贷款发放交易实体类
	 * @return long 保存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantSave(TransGrantDiscountInfo info) throws RemoteException,
			IRollbackException;

	/**
	 * 贴现发放交易——删除 逻辑说明： （1）调用方法this.isTouched,判断要删除的记录是否被修改过。 （2）判断参数TransGrantDiscountInfo 中的交易实体类的状态， 如果是暂存：
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。 如果是保存：
	 * 调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来 的实体TransGrantDiscountInfo.
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return boolean；true，删除成功；false,失败。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantDelete(TransGrantDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常"您要复核的单据已被修改，请检查！”。"
	 * （2）调用方法：AccountBookOperation.checkGrantDiscount()。进行复核的财务处理。 （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantCheck(TransGrantDiscountInfo info) throws RemoteException,
			IRollbackException;

	/**
	 * 贴现发放交易——取消复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常" 您要取消复核的单据已被修改，请检查！"。
	 * （2）调用方法：AccountBookOperation.cancelCheckGrantDiscount()。进行取消复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 取消复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantCancelCheck(TransGrantDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 链接查找(按状态查询) 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.findByStatus()方法。
	 * 
	 * @param info
	 *            QueryByStatusConditionInfo 按状态查询条件实体类
	 * @return Vector 包含特殊交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	///查询贴现合同票据
	public Collection findDiscountBillInfo(long lPageLineCount, long lPageNo,
			long lOrderParam, long lDesc, long nContractId, //票据ID 
			long nContractIdFrom, // 合同From
			long nContractIdTo, // 合同to
			String sCode, //汇票号码
			double mAmountFrom, //汇票金额from
			double mAmountTo, //汇票金额to
			Timestamp dtCheckDateFrom, //审查日期From
			Timestamp dtCheckDateTo, //审查日期To
			long nCheckStatus //'审查状态';
	) throws RemoteException, IException;

//	 /贴现票据状态修改
	public Collection saveDiscountBillInfo(long recordId, // 记录编号
			long lPageLineCount, long lPageNo, Timestamp nCheckDate, // 审查日期
			String nCheckId, // '查复编号';
			long nBillStatus // 票据状态';
	) throws RemoteException, IException, IRollbackException;

	//	/贴现票据查询  
	public Collection findDiscountBillStatusInfo(long lPageLineCount,
			long lPageNo, long lOrderParam, long lDesc, long nContractId, //票据id 
			long nContractIdFrom, // 合同From
			long nContractIdTo, // 合同to
			String sCode, //汇票号码
			double mAmountFrom, //汇票金额from
			double mAmountTo, //汇票金额to
			Timestamp dtCheckDateFrom, //审查日期From
			Timestamp dtCheckDateTo, //审查日期To
			long nCheckStatus, //'审查状态';

			long nBillStatusId, //票据状态-
			Timestamp dCancelDateFrom, //销账日期From-
			Timestamp dCancelDateTo, //销账日期To-

			String checkcodestr, //复查编号
			Timestamp dtendFrom, //票据到期日期 from
			Timestamp dtendTo //票据到期日期 to

	) throws RemoteException, IException;

	public Collection grantFindByConditions(QueryConditionInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——复核匹配 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.match()方法。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类（条件）
	 * @return Collection 包含贴现发放交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantDiscountInfo grantMatch(TransGrantDiscountInfo info)
			throws RemoteException, IRollbackException;
	/**
	 * 转贴现发放交易——复核匹配 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.match()方法。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类（条件）
	 * @return Collection 包含贴现发放交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransDiscountDetailInfo grantDiscountMatch(TransDiscountDetailInfo info)
			throws RemoteException, IRollbackException;
	
	public Collection findSubjectInfo(TransDiscountSubjectInfo info)
	       throws RemoteException, IRollbackException;
	/**
	 * 根据贴现发放交易ID，得到贴现发放交易详细信息 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.findByID()方法。
	 * 
	 * @param lTransID
	 *            贴现发放交易ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantDiscountInfo grantFindDetailByID(long lTransID)
			throws RemoteException, IRollbackException;

	/**
	 * 根据贴现凭证ID，得到放款的信息 逻辑操作： 调用方法：(信贷的方法，目前用SQL先实现。)
	 * 
	 * @param lDiscountNoteID
	 *            贴现凭证ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantDiscountInfo grantFindGrantDetailByNoteID(
			long lDiscountNoteID) throws RemoteException, IRollbackException;

	/**
	 * 根据转贴现凭证ID，得到放款的信息 逻辑操作： 调用方法：(信贷的方法，目前用SQL先实现。)
	 * 
	 * @param lDiscountNoteID
	 *            转贴现凭证ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransDiscountDetailInfo findTransDiscountByNoteID(
			long transDiscountNoteID) throws RemoteException, IRollbackException;
	/**
	 * 根据转贴现发放交易ID，得到转贴现发放交易详细信息 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.findByID()方法。
	 * 
	 * @param lTransID
	 *            转贴现发放交易ID
	 * @return TransGrantDiscountInfo 转贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findByConditions(QueryConditionInfo info)
	       throws RemoteException, IRollbackException;
	
	public TransDiscountDetailInfo findTransDetailByID(long lTransID)
			throws RemoteException, IRollbackException;
	/**
	 * 转贴现发放交易——复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常"您要复核的单据已被修改，请检查！”。"
	 *  （2）调用方法：Sett_TransGrantDiscountDAO.updateStatus。
	 * 
	 * @param info
	 *            TransDiscountDetailInfo 贴现发放交易实体类
	 * @return long 复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantCheck(TransDiscountDetailInfo info) throws RemoteException,
			IRollbackException;
	/**
	 * 转贴现发放交易——删除 逻辑说明： （1）调用方法this.isTouched,判断要删除的记录是否被修改过。 （2）判断参数TransGrantDiscountInfo 中的交易实体类的状态， 如果是暂存：
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。 如果是保存：
	 * 调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来 的实体TransGrantDiscountInfo.
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 转贴现发放交易实体类
	 * @return boolean；true，删除成功；false,失败。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantDelete(TransDiscountDetailInfo info)
			throws RemoteException, IRollbackException;
	
	/**
	 * 转贴现发放交易——取消复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常" 您要取消复核的单据已被修改，请检查！"。
	 * （2）调用方法：AccountBookOperation.cancelCheckGrantDiscount()。进行取消复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 转贴现发放交易实体类
	 * @return long 取消复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantCancelCheck(TransDiscountDetailInfo info)
			throws RemoteException, IRollbackException;

	/**************************************************************/
	/**
	 * Method preSave.
	 * 
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentPreSave(TransRepaymentDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——暂存 逻辑操作： （1）如果lID不是-1，调用方法this.isTouched(),判断要暂存的记录是否被修改过。
	 * 调用方法Sett_TransGrantDiscountDAO.update()保存交易记录信息。 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransGrantDiscountDAO.add()保存交易记录信息。
	 * 调用方法Sett_TransGrantDiscountDAO.updateStatus()更改记录的状态为未保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 暂存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentTempSave(TransRepaymentDiscountInfo info)
			throws RemoteException, IRollbackException;

	public long grantGetIDByTransNo(String strTransNo) throws RemoteException,
			IRollbackException;

	public long repaymentGetIDByTransNo(String strTransNo)
			throws RemoteException, IRollbackException;

	public long repaymentModifyTempSave(TransRepaymentDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——保存 逻辑操作： （1）判断参数TransGrantDiscountInfo交易实体类中的交易编号是否为空。 如果是空，说明是新增保存：
	 * 调用方法：UtilOperation.getNewTransactionNo()得到一个交易号，并将其写入TransGrantDiscountInfo 。 如果非空，说明是修改保存:
	 * 调用方法：this.isTouch,判断要暂存的记录是否被修改过。 调用方法：this.FindDetailByID(),得到原来的贴现发放交易实体类TransGrantDiscountInfo。
	 * 调用方法：AccountBookOperation.deleteGrantDiscount()。回滚原来的财务处理。注意参数是原来 的实体TransGrantDiscountInfo。
	 * （2）判断ID是否为－1，若是，则调用方法为：Sett_TransGrantDiscountDAO.add() 。 不是，则调用方法为：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 
	 * （3）调用方法：AccountBookOperation.saveGrantDiscount()。进行财务处理。 （4）调用方法：Sett_TransGrantDiscountDAO.updateStatus()。
	 * 修改交易的状态为保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现贷款发放交易实体类
	 * @return long 保存成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentSave(TransRepaymentDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——删除 逻辑说明： （1）调用方法this.isTouched,判断要删除的记录是否被修改过。 （2）判断参数TransGrantDiscountInfo 中的交易实体类的状态， 如果是暂存：
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。 如果是保存：
	 * 调用方法：AccountBookOperation.deleteSpecialOperation()。回滚原来的财务处理。注意参数是原来 的实体TransGrantDiscountInfo.
	 * 调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为删除（无效）。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return boolean；true，删除成功；false,失败。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentDelete(TransRepaymentDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常"您要复核的单据已被修改，请检查！”。"
	 * （2）调用方法：AccountBookOperation.checkGrantDiscount()。进行复核的财务处理。 （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCheck(TransRepaymentDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——取消复核 逻辑操作： （1）调用方法：this.isTouched,判断要复核的记录是否被修改过，否则抛出异常" 您要取消复核的单据已被修改，请检查！"。
	 * （2）调用方法：AccountBookOperation.cancelCheckGrantDiscount()。进行取消复核的财务处理。
	 * （3）调用方法：Sett_TransGrantDiscountDAO.updateStatus。修改交易的状态为保存。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类
	 * @return long 取消复核成功，返回交易ID；否则返回-1。
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCancelCheck(TransRepaymentDiscountInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 链接查找(按状态查询) 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.findByStatus()方法。
	 * 
	 * @param info
	 *            QueryByStatusConditionInfo 按状态查询条件实体类
	 * @return Vector 包含特殊交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection repaymentFindByConditions(QueryConditionInfo info)
			throws RemoteException, IRollbackException;

	/**
	 * 贴现发放交易——复核匹配 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.match()方法。
	 * 
	 * @param info
	 *            TransGrantDiscountInfo 贴现发放交易实体类（条件）
	 * @return Collection 包含贴现发放交易实体类的集合
	 * @throws RemoteException
	 * @throws IRollbackException	 */

	public TransRepaymentDiscountInfo repaymentMatch(
			TransRepaymentDiscountInfo info) throws RemoteException,
			IRollbackException;

	/**
	 * 根据贴现发放交易ID，得到贴现发放交易详细信息 逻辑操作： 调用方法：Sett_TransGrantDiscountDAO.findByID()方法。
	 * 
	 * @param lTransID
	 *            贴现发放交易ID
	 * @return TransGrantDiscountInfo 贴现发放交易详细信息
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentDiscountInfo repaymentFindDetailByID(long lTransID)
			throws RemoteException, IRollbackException;

	//由于打印方面的需要,增加一个方法
	public DiscountCredenceInfo findDiscountCredenceByID(
			long lDiscountCredenceID) throws RemoteException,
			IRollbackException;
	/** add by lkliu
	 * Method updateDiscountBillOfCancelCheck.
	 * 
	 * @param info
	 * @return int 票据修改接口 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateDiscountBillOfCancelCheck(long lID) throws RemoteException,
			IRollbackException;

}