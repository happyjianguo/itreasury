/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import com.iss.itreasury.settlement.setting.dao.Sett_TransactionFeeTypeDAO;
import java.util.Collection;
import com.iss.itreasury.settlement.setting.dataentity.TransFeeTypeSetInfo;
/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TransactionFeeTypeBiz {
	private Sett_TransactionFeeTypeDAO TransactionCostDAO=new Sett_TransactionFeeTypeDAO();
	
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-7-2 12:31:14)
	 * @return long
	 * @param lID long
	 * @param strTableName java.lang.String
	 * @param lRecordID long
	 * @param lCurrencyID long
	 * @param strSubject java.lang.String
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public long deleteTransactionFeeType(long lID){
		return TransactionCostDAO.deleteTransactionFeeType(lID);
	}
	
	/**
	 * 查询所有交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询所有交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>返回Collection，包含类TransactionFeeTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lOfficeID 办事处标识
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllTransactionFeeType(long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc){
		return TransactionCostDAO.findAllTransactionFeeType(lOfficeID,  lCurrencyID, lPageLineCount,  lPageNo,  lOrderParam,  lDesc);
	}
	
	
	/**
	 * 根据标识查询交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>返回类TransactionFeeTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return TransactionFeeTypeInfo
	 * @exception Exception
	 */
	public TransFeeTypeSetInfo findTransactionFeeTypeByID(long lID){
		return TransactionCostDAO.findTransactionFeeTypeByID(lID);
	}
	
	
	/**
	 * 根据标识查询交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>返回类TransactionFeeTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return TransactionFeeTypeInfo
	 * @exception Exception
	 */
	public TransFeeTypeSetInfo findTransactionFeeTypeByID(long lID, long lCurrencyID) {
		return TransactionCostDAO.findTransactionFeeTypeByID(lID, lCurrencyID);
	}
	
	
	/**
	 * 得到最新的交易费用类型代码
	 * @param lOfficeID 办事处标识
	 * @return
	 */
	public String getNewTransactionFeeTypeCode(long lOfficeID,long lCurrencyID){
		return TransactionCostDAO.getNewTransactionFeeTypeCode(lOfficeID,lCurrencyID);
	}
	
	
	/**
	 * 保存交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>如果lID<0，则在TransactionFeeType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID
	 * @param strName
	 * @param strCode
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveTransactionFeeType(long lID, long lOfficeID, long lCurrencyID, String strName, String strCode, String strSubjectCode, long lIsHaveBank){
		return TransactionCostDAO.saveTransactionFeeType( lID,  lOfficeID,  lCurrencyID,  strName,  strCode,  strSubjectCode,  lIsHaveBank);
	}
	

	
}
