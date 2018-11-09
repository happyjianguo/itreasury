/*
 * Created on 2004-10-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import com.iss.itreasury.settlement.setting.dao.Sett_SpecialOperationDAO;
import java.util.*;
import com.iss.itreasury.settlement.setting.dataentity.*;
/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SpecialOperationBiz {
	
	private Sett_SpecialOperationDAO specialoperationdao=new Sett_SpecialOperationDAO();
	
	/**
	 * 删除特殊业务类型设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>删除特殊业务类型设置</b>
	 * <ul>
	 * <li>操作数据库表SpecialOperation
	 * <li>将状态置为删除
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return lResult
	 * @exception Exception
	 */
	public long deleteSpecialOperation(long lID){
		return specialoperationdao.deleteSpecialOperation(lID);
	}
	
	/**
	 * 查询所有特殊业务类型设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询所有特殊业务类型设置</b>
	 * <ul>
	 * <li>操作数据库表SpecialOperation
	 * <li>返回Collection，包含类SpecialOperationInfo
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
	public Collection findAllSpecialOperation(long lOfficeID, long lStartID, long lEndID, String strContext, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc){
		return specialoperationdao.findAllSpecialOperation(lOfficeID,lStartID,lEndID,strContext,lPageLineCount,lPageNo,lOrderParam,lDesc);
	}
	
	/**
	 * 查询所有特殊业务类型设置
	 * @param lcurrencyID   为了防止改变原方法对其他模块有影响，在此 添加了一个新的方法   将币种id传入
	 * @param lOfficeID
	 * @param lStartID
	 * @param lEndID
	 * @param strContext
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 * @return
	 */
	public Collection findAllSpecialOperation(long lcurrencyID,long lOfficeID, long lStartID, long lEndID, String strContext, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc){
		return specialoperationdao.findAllSpecialOperation(lcurrencyID,lOfficeID,lStartID,lEndID,strContext,lPageLineCount,lPageNo,lOrderParam,lDesc);
	}
	
	
	/**
	 * 根据标识查询特殊业务类型设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询特殊业务类型设置</b>
	 * <ul>
	 * <li>操作数据库表SpecialOperation
	 * <li>返回类SpecialOperationInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return SpecialOperationInfo
	 * @exception Exception
	 */
	public SpecialOperationInfo findSpecialOperationByID(long lID) {
		return specialoperationdao.findSpecialOperationByID(lID);
	}
	
	
	/**
	 * 保存特殊业务类型设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存特殊业务类型设置</b>
	 * <ul>
	 * <li>操作数据库表SpecialOperation
	 * <li>如果lID<0，则在SpecialOperation表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID 办事处标识
	 * @param strName
	 * @param strContent
	 * @return void
	 * @exception Exception
	 */
	public long saveSpecialOperation(long lID, long lOfficeID, String strName, String strContent) {
		return specialoperationdao.saveSpecialOperation(lID,lOfficeID,strName,strContent);
	}
    //保存的时候，要保存“收款方”和“付款方”的借贷关系，1为借，-1为贷 ，重载了方法“saveSpecialOperation” 全哨 2010-5-26
	public long saveSpecialOperation(long lID, long lOfficeID, String strName, String strContent, long payRelation, long gatheringRelation){
		return specialoperationdao.saveSpecialOperation(lID,lOfficeID,strName,strContent, payRelation, gatheringRelation);
	}
	
    //保存的时候，要保存币种信息  重载了上面的方法   张雷  2010-07-22
	public long saveSpecialOperation(long lID,long lcurrencyID, long lOfficeID, String strName, String strContent, long payRelation, long gatheringRelation){
		return specialoperationdao.saveSpecialOperation(lID,lcurrencyID,lOfficeID,strName,strContent, payRelation, gatheringRelation);
	}
	
    //  重载了上面的方法   江琪 2011-07-01   特殊业务设置改造
	public long saveSpecialOperation(SpecialOperationInfo info){
		return specialoperationdao.saveSpecialOperation(info);
	}
}
