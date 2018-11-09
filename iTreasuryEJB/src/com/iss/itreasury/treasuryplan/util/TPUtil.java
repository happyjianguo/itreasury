/*
 * Created on 2005-2-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * @author yehuang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TPUtil {
	
	public void updateSubLinesAuthorityToParentLine(long parentLineLevel) throws Exception{
		Connection tpConn = null;
		tpConn = Database.getConnection();
		tpConn.setAutoCommit(false);
		Trea_TPTemplateDAO tpTemplateDAO = new Trea_TPTemplateDAO(tpConn);		
		TPTemplateInfo conditionTemplate = new TPTemplateInfo();
		conditionTemplate.setLineLevel(parentLineLevel);
		conditionTemplate.setIsLeaf(0);
		Collection c = tpTemplateDAO.findByCondition(conditionTemplate);
		Iterator it = c.iterator();
		while (it.hasNext()) {
			TPTemplateInfo tpTemplateInfo = (TPTemplateInfo) it.next();
			System.out.println("--------parent TemplateInfo:"+tpTemplateInfo);
			TPTemplateInfo conditionTemplate1 = new TPTemplateInfo();
			conditionTemplate1.setParentLineID(tpTemplateInfo.getId());
			Collection c1 = tpTemplateDAO.findByCondition(conditionTemplate1);
			Iterator it1 = c1.iterator();
			String parentUserAuthority = "";
			String parentDepartmentAuthority = "";
			while (it1.hasNext()) {
				TPTemplateInfo subTemplateInfo = (TPTemplateInfo) it1.next();
				System.out.println("--------sub TemplateInfo:"+subTemplateInfo);
				if(subTemplateInfo.getId()==13)
					System.out.println("--------debug");
				String userAuthority = subTemplateInfo.getAuthorizedUser();
				String departmentAuthority = subTemplateInfo.getAuthorizedDepartment();
				System.out.println("--------before parentUserAuthority:"+parentUserAuthority);
				if(userAuthority != null && userAuthority.trim().length() != 0){
					parentUserAuthority = addSubAuthorityToParentLine(userAuthority,parentUserAuthority);
				}
				if(departmentAuthority != null && departmentAuthority.trim().length() != 0){
					parentDepartmentAuthority = addSubAuthorityToParentLine(departmentAuthority,parentDepartmentAuthority);
				}				

				
				System.out.println("--------after parentUserAuthority:"+parentUserAuthority);				
			}			
			TPTemplateInfo updateTemplate = new TPTemplateInfo();	
			updateTemplate.setId(tpTemplateInfo.getId());
			updateTemplate.setAuthorizedUser(parentUserAuthority);
			updateTemplate.setAuthorizedDepartment(parentDepartmentAuthority);
			tpTemplateDAO.update(updateTemplate);
		}
		tpConn.commit();
		tpConn.close();	
		
	}
	
	private String addSubAuthorityToParentLine(String subAuthority,String parentAuthority){
		subAuthority = subAuthority.replaceAll("><",">,<");
		String users[] = subAuthority.split(",");
		//strAuthority = strAuthority.substring(1,strAuthority.length()-1);
		for(int i=0;i<users.length;i++){
			//strAuthority
			String user = users[i];
			if(parentAuthority.indexOf(user) >= 0)
				continue;
			parentAuthority += user;
			
		}
		
		return parentAuthority;
	}
	
	private String addToAuthority(String currentAuth,String subLineAuth){
		
		return currentAuth;
	}
	
	public static long getCompanyID()
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long firmDepID = -1;
		try
		{
			conn = Database.getConnection();
			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL.append("select * from department \n");
			sbSQL.append(" where ISCOMPANYLEVEL=1 and statusid="+Constant.RecordStatus.VALID);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				firmDepID = rs.getLong("ID");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return firmDepID;
	}
	
	/**
	* 取得以前没有运行资金计划的日期天数
	* @param Timestamp transDate 执行日
	* return 以前没有运行资金计划的日期天数
	*/
	public static long getInterval(Timestamp transDate) throws Exception
	{
		long lRet = 1;
		
		lRet= TPConstant.DayCount.getInstance().getRunDayCount();
		
		if (lRet > 0)
		{
			return lRet;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		Timestamp tsSystemDate = null;
		Connection conn = null;
		
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" SELECT DISTINCT t.transactiondate ");
			sbRecord.append(" FROM trea_actualbalance t ");
			sbRecord.append(" ORDER BY t.transactiondate DESC");
			
			ps = conn.prepareStatement(sbRecord.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				tsSystemDate = rs.getTimestamp(1);
			}
			
			if (tsSystemDate != null && transDate != null)
			{
				sbRecord.setLength(0);
				sbRecord.append(" SELECT TRUNC((TO_DATE(?,'yyyy-mm-dd')-TO_DATE(?,'yyyy-mm-dd')),0) as cnt ");
				sbRecord.append(" FROM dual");
				ps = conn.prepareStatement(sbRecord.toString());
				ps.setString(1, transDate.toString().substring(0,10));
				ps.setString(2, tsSystemDate.toString().substring(0,10));

				rs = ps.executeQuery();
				if (rs.next()) 
				{
					lRet = rs.getLong("cnt");
					TPConstant.DayCount.setRunDayCount(String.valueOf(lRet));
					System.out.println("lRet="+lRet); 
				}				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		
		return lRet;
	}
	
	static public void main(String[] args){
		TPUtil tpUtil = new TPUtil();
		try {
			for(int i = 5;i>0;i--){
				tpUtil.updateSubLinesAuthorityToParentLine(i);
			}
//			tpUtil.updateSubLinesAuthorityToParentLine(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
