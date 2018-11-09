/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * 总体功能说明：账户查询业务层，主要用于账户的各种查询
 *
 * 使用注意事项：
 * 1
 * 2
 *
 * 作者：Soften
 *
 * 更改人员：
 *
 */
package com.iss.itreasury.ebank.obaccountinfo.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
 
import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Log;
 


public class OBAccountQueryAmountBiz {
 
	
	public OBAccountQueryAmountBiz(){
		
	}
	/**
	 *  取得账户组
	 * @param  long[] lTypeID
	 * @return Collection
	 * @exception nothing
	 */
	public Collection getCodeInfo(long[] lTypeID) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < lTypeID.length; i++) {
			OBAccountQueryAmountInfo obaqai = new OBAccountQueryAmountInfo();
			obaqai.setAccountGroupID(lTypeID[i]);
			obaqai.setAccountGroupName(SETTConstant.AccountGroupType.getName(lTypeID[i]));
			list.add(obaqai);
		}
		return list.size() > 0?list:null;
	}
	/**
	 *  获得子公司
	 * @param  OBAccountQueryWhere obaqw, long lParentCorpID, 
	 * @param  long lAccountGroupTypeID
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getChildClientID(OBAccountQueryWhere obaqw, long lParentCorpID, long lAccountGroupTypeID) throws SQLException {
		Connection conn = null;
		OBAccountQueryAmountDao dao = null;
		Collection coll = null;
		try {
			dao = new OBAccountQueryAmountDao(conn);
			coll = dao.getChildClientID(obaqw,lParentCorpID,lAccountGroupTypeID);
		}
		catch(Exception e) {
			System.out.println("OBAccountQueryAmountBiz.getChildClientID() failed.  Exception is " +	e.toString());
		}
		finally {
			dao.closeConn();
		}
		return coll;
	}
	
	/**
	 *  下拉框控件(选择下属单位账号)
	 * @param  JspWriter out, String strFileName, OBAccountQueryWhere obaqw,
	 * @param  long lAccountGroupID,
	 * @param  Collection list, long lClientID, long lAccountID
	 * @return Collection
	 * @exception throws Exception
	 */
	public void showAccountListControl(
		JspWriter out, 
		String strFileName, 
		OBAccountQueryWhere obaqw,
		long lAccountGroupTypeID, 
		long lParentClentID,
		long lClientID, 
		long lAccountID,
		String strJS) 
		throws Exception {
		Connection conn = null;
		OBAccountQueryAmountDao dao = null;
		 try {
			dao = new OBAccountQueryAmountDao(conn);
			dao.showAccountListControl(out,strFileName,obaqw,lAccountGroupTypeID,lParentClentID,lClientID,lAccountID,strJS);
		}
		catch(Exception e) {
			System.out.println("OBAccountQueryAmountBiz.showAccountListControl() failed.  Exception is " +	e.toString());
		}
		finally {
			dao.closeConn();
		} 
	}
	public void showClientListControl(
			JspWriter out, 
			String strFileName, 
			OBAccountQueryWhere obaqw, 
			long lParentCorpID, 
			long lAccountGroupTypeID, 
			long lClientID,
			long lOfficeid,
			boolean child) 
			throws Exception {
		Connection conn = null;
		OBAccountQueryAmountDao dao = null;
		 try {
			dao = new OBAccountQueryAmountDao(conn);
			dao.showClientListControl(out,strFileName,obaqw,lParentCorpID,lAccountGroupTypeID,lClientID,lOfficeid,true);
		}
		catch(Exception e) {
			System.out.println("OBAccountQueryAmountBiz.showAccountListControl() failed.  Exception is " +	e.toString());
		}
		finally {
			dao.closeConn();
		} 
	}
	/**
	 * 按客户编号排序
	 * @param out
	 * @param strFileName
	 * @param obaqw
	 * @param lParentCorpID
	 * @param lAccountGroupTypeID
	 * @param lClientID
	 * @throws Exception
	 */
	public Collection doSortClientCode(Collection coll) {
		if (coll == null)
		{
			return null;
		}
		ArrayList list = new ArrayList();
		ArrayList arrayList = (ArrayList) coll;		
		Iterator iterList = null;
		List listTemp = arrayList.subList(0, arrayList.size());		
		if (listTemp != null) {
			iterList = listTemp.iterator();
		}
		while (iterList != null && iterList.hasNext()) {
			int iIndex = 0;
			int indexTemp=0;
			OBAccountQueryAmountInfo info1 = (OBAccountQueryAmountInfo)iterList.next();			
			String strClientNo1 = info1.getClientCode();
			while (iterList != null && iterList.hasNext()) {
				iIndex=iIndex+1;
				OBAccountQueryAmountInfo info2 = (OBAccountQueryAmountInfo)iterList.next();
				String strClientNo2 = info2.getClientCode();
				if (strClientNo1.compareTo(strClientNo2) > 0) {
					strClientNo1 = strClientNo2;
					indexTemp = iIndex;
				}
			}
			list.add(listTemp.get(indexTemp));
			listTemp.remove(indexTemp);
			if (iIndex >= 0) {
				iterList = listTemp.iterator();
			}
		}
		return list.size() > 0?list:null;
	}
	public Collection getBankAcctByClientID(long clientID) throws SQLException {
		Connection conn = null;
		OBAccountQueryAmountDao dao = null;
		Collection coll = null;
		try {
			dao = new OBAccountQueryAmountDao(conn);
			coll = dao.getBankAcctByClientID(clientID);
		}
		catch(Exception e) {
			System.out.println("OBAccountQueryAmountBiz.getChildClientID() failed.  Exception is " +	e.toString());
		}
		finally {
			dao.closeConn();
		}
		return coll;
	}
}
