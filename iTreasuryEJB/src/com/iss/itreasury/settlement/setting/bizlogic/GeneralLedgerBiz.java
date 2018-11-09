/*
 * Created on 2004-10-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import com.iss.itreasury.settlement.setting.dao.Sett_GeneralLedgerDAO;
import java.util.*;
import com.iss.itreasury.settlement.setting.dataentity.*;

/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GeneralLedgerBiz {
	private Sett_GeneralLedgerDAO generalledgerdao=new Sett_GeneralLedgerDAO();
	/**
	 * 删除总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>删除总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedger
	 * <li>将状态置为删除
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void  sss
	 * @exception Exception
	 */
	public long deleteGeneralLedger(long lID){
		return generalledgerdao.deleteGeneralLedger(lID);
	}
	
	
	/**
	 * 查询所有总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询所有总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedgerType
	 * <li>返回Collection，包含类GeneralLedgerInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllGeneralLedger(long lOfficeID, long lCurrencyID, String strStartCode, String strEndCode, String strName, String strSubject, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) 
	{
		return generalledgerdao.findAllGeneralLedger(lOfficeID,lCurrencyID,strStartCode,strEndCode,strName,strSubject,lPageLineCount,lPageNo,lOrderParam,lDesc);
	}
	
	/**
	 * 根据标识查询总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedger
	 * <li>返回类GeneralLedgerInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return GeneralLedgerInfo
	 * @exception Exception
	 */
	public GeneralLedgerInfo findGeneralLedgerByID(long lID, long lCurrencyID)
	{
		return generalledgerdao.findGeneralLedgerByID(lID,lCurrencyID);
	}
	
	
	/**
	 * 得到最新的总账代码
	 */
	public String getNewGeneralLedgerCode(long lOfficeID,long lCurrencyID) 
	{
		return generalledgerdao.getNewGeneralLedgerCode(lOfficeID,lCurrencyID);
	}
	
	
	/**
	 * 保存总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedger
	 * <li>如果lID<0，则在GeneralLedger表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID
	 * @param strGeneralLedgerCode
	 * @param strName
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveGeneralLedger(long lID, long lOfficeID, String strGeneralLedgerCode, String strName, String strSubjectCode, long lCurrencyID) 
	{
		return generalledgerdao.saveGeneralLedger(lID, lOfficeID, strGeneralLedgerCode, strName, strSubjectCode, lCurrencyID);
	}
}
