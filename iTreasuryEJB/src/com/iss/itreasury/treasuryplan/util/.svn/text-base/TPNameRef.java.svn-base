/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.util;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.util.Constant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TPNameRef
{


	/**
	 * 功能：通过部门ID查询部门名称
	 * @param lClientID
	 * @return
	 * @
	 */
	public static String getDepartmentNameByID(long lDepartmentID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select DepartmentName from department where ID=" + lDepartmentID;
            System.out.println(" 通过部门ID查询部门名称 :   \n"+strSQL);
			Collection c = TPHTML.getCommonSelectList(strSQL, "DepartmentName");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	
	
	/**
	 * 功能：通过部门ID查询部门名称
	 * @param lClientID
	 * @return
	 * @
	 * 模板调用此方法，不再重复取数据库联接
	 */
	public static String getDepartmentNameByID(Connection conn , long lDepartmentID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select DepartmentName from department where ID=" + lDepartmentID;
            System.out.println(" 通过部门ID查询部门名称 :   \n"+strSQL);
			Collection c = TPHTML.getCommonSelectList(conn,strSQL, "DepartmentName");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
    
    /**
     * 功能：通过客户编号查询客户Id
     * @param clientNo
     * @return lClientId
     * @
     */
    public static long getClientIdByNo( String clientNo )
    {
        long lClientId = -1;
        try
        {
            String strSQL = " select id from client where sCode = '" + clientNo+"'";
            strSQL       += " and nStatusId = "+Constant.RecordStatus.VALID;
            System.out.println(" 通过客户ID查询客户名称 :   \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(strSQL, "id");
            if (c != null)
            {
                lClientId = ((java.math.BigDecimal)c.iterator().next()).longValue();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return lClientId;
    }
    
    /**
     * 功能：通过交易对手编号查询交易对手Id
     * @param counterpartNo
     * @return lCounterpartId
     * @
     */
    public static long getCounterpartIdByNo( String counterpartNo )
    {
        long lCounterpartId = -1;
        try
        {
            String strSQL = " select id from sec_counterpart where Code = '" + counterpartNo+"'";
            strSQL       += " and StatusId > 0 ";
            System.out.println(" 通过交易对手编号查询交易对手Id :   \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(strSQL, "id");
            if (c != null)
            {
                lCounterpartId = ((java.math.BigDecimal)c.iterator().next()).longValue();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return lCounterpartId;
    }
    
    
    /**
     * 功能：通过科目编号查询科目Id
     * @param subjectCode
     * @return lSubjectId
     * @
     */
    public static long getSubjectIdByCode( String subjectCode )
    {
        long lSubjectId = -1;
        try
        {
//        	ssegmentcode2替换为sSubjectCode
//            String strSQL = " select id from sett_vglsubjectdefinition where sSegmentCode2 = '" + subjectCode+"'";
            String strSQL = " select id from sett_vglsubjectdefinition where sSubjectCode = '" + subjectCode+"'";
            strSQL       += " and nstatus = "+Constant.RecordStatus.VALID;
            System.out.println("  通过科目编号查询科目Id :   \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(strSQL, "id");
            if (c != null)
            {
                lSubjectId = ((java.math.BigDecimal)c.iterator().next()).longValue();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return lSubjectId;
    }
    
    
    /**
     * 功能：通过科目编号查询科目Id
     * @param subjectCode
     * @return lSubjectId
     * @
     * 模板调用此方法，不再重复取数据库联接
     */
    public static long getSubjectIdByCode( Connection conn , String subjectCode )
    {
        long lSubjectId = -1;
        try
        {
//        	ssegmentcode2替换为sSubjectCode
//            String strSQL = " select id from sett_vglsubjectdefinition where sSegmentCode2 = '" + subjectCode+"'";
            String strSQL = " select id from sett_vglsubjectdefinition where sSubjectCode = '" + subjectCode+"'";
            strSQL       += " and nstatus = "+Constant.RecordStatus.VALID;
            System.out.println("  通过科目编号查询科目Id :   \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(conn,strSQL, "id");
            if (c != null)
            {
                lSubjectId = ((java.math.BigDecimal)c.iterator().next()).longValue();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return lSubjectId;
    }
    
    
    /**
     *@功能: 通过行项目编号得到行项目的Id
     *@param lineNo
     *@return lineId
     */
    public static long getLineIdByNo( String lineNo )
    {
        long lLineId = -1;
        try
        {
            String strSQL = " select id from trea_tptemplate where lineNo = '" + lineNo+"'";
            strSQL       += " and statusId = "+Constant.RecordStatus.VALID;
            System.out.println("  通过行项目编号得到行项目的Id :   \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(strSQL, "id");
            if (c != null)
            {
                lLineId = ((java.math.BigDecimal)c.iterator().next()).longValue();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return lLineId;
    }
    
    /**
     *@功能: 通过行项目编号得到行项目的Id
     *@param lineNo
     *@return lineId
     *
     *模板调用此方法，不再重复取数据库联接
     */
    public static long getLineIdByNo( Connection conn,String lineNo )
    {
        long lLineId = -1;
        try
        {
            String strSQL = " select id from trea_tptemplate where lineNo = '" + lineNo+"'";
            strSQL       += " and statusId = "+Constant.RecordStatus.VALID;
            System.out.println("  通过行项目编号得到行项目的Id :   \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(conn,strSQL, "id");
            if (c != null)
            {
                lLineId = ((java.math.BigDecimal)c.iterator().next()).longValue();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return lLineId;
    }
    
    
    /**
     *@Description:功能 ：通过行项目的Id 得到上级项目的编号
     *@param levelId
     *@return String
     *@throws Exception
     */
    public static String getParentLevelNoById( long levelId ) throws Exception
    {
        String strReturn = "";
        
        try
        {
            String strSQL = " Select distinct pare.lineNo  lineNo  from Trea_TPTemplate son ,Trea_TPTemplate pare   \n";
            strSQL       += "   Where son.parentlineid = pare.id  and son.id = " + levelId ;
            System.out.println(" 通过行项目的Id 得到上级项目的编号 :  \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(strSQL, "lineNo");
            if (c != null)
            {
                strReturn = (String) c.iterator().next();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        return strReturn;
    }
    
    
    /**
     *@Description:功能 ：通过行项目的Id 得到上级项目的编号
     *@param levelId
     *@return String
     *@throws Exception
     * 模板调用此方法，不再重复取数据库联接
     */
    public static String getParentLevelNoById( Connection conn,long levelId ) throws Exception
    {
        String strReturn = "";
        
        try
        {
            String strSQL = " Select distinct pare.lineNo  lineNo  from Trea_TPTemplate son ,Trea_TPTemplate pare   \n";
            strSQL       += "   Where son.parentlineid = pare.id  and son.id = " + levelId ;
            System.out.println(" 通过行项目的Id 得到上级项目的编号 :  \n"+strSQL);
            Collection c = TPHTML.getCommonSelectList(conn,strSQL, "lineNo");
            if (c != null)
            {
                strReturn = (String) c.iterator().next();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        return strReturn;
    }
    
    /**
     *@Description:功能 ：通过行项目的Id 得到上级项目的名称
     *@param levelId
     *@return String
     *@throws Exception
     */
    public static String getParentLevelNameById( long levelId ) throws Exception
    {
        String strReturn = "";
        
        try
        {
            String strSQL = " Select distinct pare.linename  linename  from Trea_TPTemplate son ,Trea_TPTemplate pare   \n";
            strSQL       += "   Where son.parentlineid = pare.id  and son.id = " + levelId ;
            Collection c = TPHTML.getCommonSelectList(strSQL, "linename");
            if (c != null)
            {
                strReturn = (String) c.iterator().next();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        return strReturn;
    }
	
	
    /**
     *@Description:功能 ：通过行项目的Id 得到上级项目的名称
     *@param levelId
     *@return String
     *@throws Exception
     *模板调用此方法，不再重复取数据库联接
     */
    public static String getParentLevelNameById(Connection conn , long levelId ) throws Exception
    {
        String strReturn = "";
        
        try
        {
            String strSQL = " Select distinct pare.linename  linename  from Trea_TPTemplate son ,Trea_TPTemplate pare   \n";
            strSQL       += "   Where son.parentlineid = pare.id  and son.id = " + levelId ;
            Collection c = TPHTML.getCommonSelectList(conn,strSQL, "linename");
            if (c != null)
            {
                strReturn = (String) c.iterator().next();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        return strReturn;
    }
    
    
	/**
	 * 功能：通过客户ID查询该部门是否是公司级部门
	 * @param lClientID
	 * @return
	 * @
	 */
	public static long getDepartmentLevelByID(long lDepartmentID)
	{
		long res = -1;
		try
		{
			String strSQL = "select ISCOMPANYLEVEL from department where ID=" + lDepartmentID;
			Collection c = TPHTML.getCommonSelectList(strSQL, "ISCOMPANYLEVEL");
			if (c != null)
			{
				res = ((java.math.BigDecimal)c.iterator().next()).longValue();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}	

	/**
	 * 功能：通过部门ID来获取部门的编号
	 * @param lDepartmentID
	 * @return
	 */
	public static String getDepartmentCodeByID(long lDepartmentID)
	{
		String res = "";
		try
		{
			String strSQL = "select departmentCode from department where ID=" + lDepartmentID;
	
			Collection c = TPHTML.getCommonSelectList(strSQL, "departmentCode");
			if (c != null && c.size()==1 )
			{
				res = ( c.iterator().next()).toString();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return res;
	}	

	/**
	 * 功能：通过资金计划ID查询资金计划名称
	 * @param  lLineID
	 * @return String
	 */
	public static String getLineNameByID(long lLineID) {
		String strReturn = "";
		try {
			String strSQL = "select lineName from Trea_TPTemplate where ID="+lLineID;
			Collection collection = TPHTML.getCommonSelectList(strSQL, "lineName");
			if (collection != null) {
				strReturn = (String) collection.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}
    
    /**
     * 功能：通过资金计划ID查询资金计划编号
     * @param  lLineID
     * @return String
     */
    public static String getLineNoByID(long lLineID) {
        String strReturn = "";
        try {
            String strSQL = "select lineNo from Trea_TPTemplate where ID="+lLineID;
            Collection collection = TPHTML.getCommonSelectList(strSQL, "lineNo");
            if (collection != null) {
                strReturn = (String) collection.iterator().next();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return strReturn;
    }
    

	/**
	 * 功能：通过用户ID查询用户名称
	 * @param  lLineID
	 * @return String
	 */
	public static String getUserNameByID(long lUserID) {
		String strReturn = "";
		try {
			String strSQL = "select sName from userInfo where ID="+lUserID;
			Collection collection = TPHTML.getCommonSelectList(strSQL, "sName");
			if (collection != null) {
				strReturn = (String) collection.iterator().next();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strReturn;
	}

	/**
	 * 功能：通过用户ID查询所属部门ID
	 * @param  lLineID
	 * @return long
	 */
	public static long getDepartmentIDByUserID(long lUserID) {
		long res = -1;
		try {
			String strSQL = "select nDepartmentID from userInfo where ID="+lUserID;
			Collection collection = TPHTML.getCommonSelectList(strSQL, "nDepartmentID");
			if (collection != null) {
				res = ((java.math.BigDecimal)collection.iterator().next()).longValue();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return res;
	}
}
