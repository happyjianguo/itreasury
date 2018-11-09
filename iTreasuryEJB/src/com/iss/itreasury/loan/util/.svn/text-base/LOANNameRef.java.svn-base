/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */


package com.iss.itreasury.loan.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTHTML;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
/**
 * Methods:
 * <ul>
 * <li>getClientNameByID(long lID)  						客户(业务单位),通过ID获得名称
 * <li>getCounterpartNameByID(long lID)  					交易对手,通过ID获得名称
 * <li>getCounterpartCodeByID(long lID)						交易对手,通过ID获得CODE
 * <li>getTransTypeNameByID(long lID)						交易类型,通过ID获得名称
 * <li>getTransAttributeByTransTypeID(long lID)				通过交易类型ID获得交易属性ID
 * <li>getApplyFormCodeByID(long lID)						申请书,通过ID取得CODE
 * <li>getAccountIDFromDeliverOrder(DeliveryOrderInfo doInfo)从交割单中根据交易类型确定该交割单使用的资金账户ID 
 * <li>getTransTypeIDByApplyFormID(long lID)				从申请书ID获得交易类型 ID
 * <li>getDeliveryOrderCodeByID(long lID)					通过交割单ID获得交割单CODE
 * <li>getUserNameCodeByID(long lID)						通过用户ID查找用户名称
 * 
 * </ul>
 */


/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LOANNameRef
{   /*************************************************
    *                   NameRef刷新函数区
    *************************************************/
    
    /**
	 * 功能：通过客户类型ID查询客户类型名称
	 * @param lClientTypeID
	 * @return
	 * @
	 */
	public static String getClientTypeNameByID(long lClientTypeID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sName from sett_ClientType where ID =" + lClientTypeID;
			Collection c = LOANHTML.getCommonSelectList(strSQL, "sName");
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
    * 用ID或code获得名称方法的公用方法1
    * @param strNameField           //名称字段名
    * @param strTableName           //表名
    * @param strIdField         //id或者code字段名
    * @param strValue               //id或者code的值
    * @return
    */
   public static String getNameByID(String strNameField,String strTableName,String strIdField,String strValue,HashMap map){
       String strReturn = null;
       try
       {
           String strSQL = "select " + strNameField + " from " + strTableName + " where " + strIdField + "='" + strValue+"'";
           Log.print("NameRef SQL:"+strSQL);
           if (map != null){                                //首先从HashMap中获取数据
               strReturn = (String)map.get(strValue);
           }
           if (strReturn == null){                      //如果HashMap中没有,则从数据库中获得
               Collection c = LOANHTML.getCommonSelectList(strSQL, strNameField);
               if (c != null)
               {
                   Object o = c.iterator().next();
                   Log.print("NameRef return value:"+o.toString());
                   strReturn = String.valueOf(o);
               }
               if (strReturn == null) strReturn = "";
               if (map != null)map.put(strValue,strReturn);             //置入HashMap
           }
           else{
               Log.print("from HashMap:" + strReturn + "  Cache size:" + map.size());
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       if (strReturn == null) strReturn = "";
       return strReturn;
   }

   /**
    * 用ID或code获得名称方法的公用方法2
    * @param strSQL                 sql
    * @param strField                   要得到的字段
    * @return
    */
   public static String getNameByID(String strSQL,String strField){
       String strReturn = "";
       try
       {
           Collection c = LOANHTML.getCommonSelectList(strSQL, strField);
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
    * 用一个ID获得所有的Name,中间用","分割
    * @param strNameField
    * @param strTableName
    * @param strIdField
    * @param strValue
    * @return
    */
   public static String getNamesByID(String strNameField,String strTableName,String strIdField,String strValue){
       String strReturn = "";
       try
       {
           String strSQL = "select " + strNameField + " from " + strTableName + " where " + strIdField + "='" + strValue+"'";
           Log.print("NameRef SQL:"+strSQL);
           Collection c = LOANHTML.getCommonSelectList(strSQL, strNameField);
           if (c != null)
           {
               Iterator ite = c.iterator();
                
               while (ite.hasNext()){
                   if (strReturn.equals("")){
                       strReturn +=ite.next().toString();
                   }
                   else{
                       strReturn += ","+ite.next().toString();
                   }
               }
           }
       }
       catch (Exception e)
       {
           System.out.println(e.toString());
       }
        
       return strReturn;
   }
    

   /**
    * 用一个ID获得所有的Name,中间用","分割
    * @param strSql                         SQL语句
    * @param strField                           要获得的字段名
    * @return
    */
   public static String getNamesByID(String strSql,String strField){
       String strReturn = "";
       try
       {
            
           Log.print("NameRef SQL:"+strSql);
           Collection c = LOANHTML.getCommonSelectList(strSql, strField);
           if (c != null)
           {
               Iterator ite = c.iterator();
                
               while (ite.hasNext()){
                   if (strReturn.equals("")){
                       strReturn +=ite.next().toString();
                   }
                   else{
                       strReturn += ","+ite.next().toString();
                   }
               }
           }
       }
       catch (Exception e)
       {
           System.out.println(e.toString());
       }
        
       return strReturn;
   }
    


    /**
     * 通过客户ID获得客户名称
     * @param lID
     * @return
     */
    public static String getClientNameById(long lID){
        return getNameByID("sname","client","id",String.valueOf(lID),null);
    }
    
	/**
	 * 通过客户ID获得客户编号
	 * @param lID
	 * @return
	 */
	public static String getClientCodeById(long lID){
		return getNameByID("sCode","client","id",String.valueOf(lID),null);
	}

	/**
	 * 通过账户编号
	 * 查找保证金余额
	 * @author yunchang
	 * @date 2010-08-04
	 */
	public static double getDepositMountCount(String constractid)
	{
		String sql = "select to_char(nvl(sum(mbalance), 0)) as mbalance from sett_subaccount where id in (select collectionrentaccountid from loan_assurechargeform where statusid = "+LOANConstant.LoanPayNoticeStatus.USED+" and contractid = "+constractid+")";
		return Double.parseDouble((getNameByID(sql,"mbalance")));
	}

    /**
     * 通过用户ID获得用户名称
     * @param lID
     * @return
     */
    public static String getUserNameById(long lID){
        return getNameByID("sname","userinfo","id",String.valueOf(lID),null);
    }
    /**
     * 通过用Libor利率ID获得Libor利率名称
     * @param lID
     * @return
     */
    public static String getLiborNameById(long lID){
        return getNameByID("liborname","loan_liborinterestrate","id",String.valueOf(lID),null);
    }
    


    /**
     * 通过银团参与行ID获得参与行名称
     * @param lID
     * @return
     */
    public static String getAttendBankNameById(long lID){
        return getNameByID("name","loan_yt_attendbank","id",String.valueOf(lID),null);
    }
    /**
     * 通过银团参与行ID获得参与行开户行
     * @param lID
     * @return
     */
    public static String getAttendBankById(long lID){
        return getNameByID("bank","loan_yt_attendbank","id",String.valueOf(lID),null);
    }
    /**
     * 通过银团参与行ID获得参与行开户行账户代码
     * @param lID
     * @return
     */
    public static String getAttendBankAccountNoById(long lID){
        return getNameByID("bankAccountNo","loan_yt_attendbank","id",String.valueOf(lID),null);
    }


	/**
	 * 通过ID获得贷款类型名称（子类）
	 * @param lID
	 * @return
	 */
	public static String getSubLoanTypeNameByID(long lID){
		return getNameByID("name","Loan_LoanTypeSetting","id",String.valueOf(lID),null);
	}


	/**
	 * 通过ID获得贷款类型Code（子类）
	 * @param lID
	 * @return
	 */
	public static String getSubLoanTypeCodeByID(long lID){
		return getNameByID("code","Loan_LoanTypeSetting","id",String.valueOf(lID),null);
	}
	

	/**
	 * 通过ID获得审批流名称（子类）
	 * @param lID
	 * @return
	 */
	public static String getApprovalSettingNameByID(long lID){
		return getNameByID("sName","loan_approvalSetting","id",String.valueOf(lID),null);
	}
	
	/**
	 * 通过合同ID获得子贷款类型
	 * @param lID
	 * @return
	 */
	public static String getSubTypeByContractID(long lID){
		return getNameByID("nsubtypeid","loan_contractform","id",String.valueOf(lID),null);
	}
	
   /**
    * Modify by leiyang 2007/09/05 因票据管理所需方法
    * 通过客户ID获得客户名称
    * @param lID
    * @return
    */
    public static String getClientNameById1(long lID){
       return getNameByID("name","sec_counterpart","id",String.valueOf(lID),null);
    }
    
    //add by zwxiao 2010-06-02 添加通过合同ID来查询累计迟付次数
    public static String getContractLateSum(long contractID) throws IException{
		String strReturn = "0";
		Connection conn = null;//匡算实体连接
		PreparedStatement pstmt = null;
		ResultSet res=null;//匡算实体查询结果集
		try
		{
			String strSQL = 
				"select count(b.id) as contractTimes, a.id"+
				  " from loan_contractform a, "+
				  "     (select * from sett_transreturnfinance where isDelayPayment = "+Constant.TRUE+" and NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+") b "+
				  " where a.id = b.ncontractid(+) "+
				  " and a.id = "+contractID+
				  " group by a.id ";
			conn=Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			res=pstmt.executeQuery();
			while (res.next())
			{
				strReturn = String.valueOf(res.getInt("contractTimes")) ;
			}
			res.close();
			pstmt.close();
			conn.close();
		}catch (Exception e)
		{
			try {
				res.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new IException();
			}
		}finally
		{
			try {
				res.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException();
			}
			
		}
		return strReturn;
    }
    
    //add by afzhu 2010-06-04 添加通过合同ID来查询累计迟付次数----重载
    public static List getContractLateSum(long contractID[]) throws IException{
		String paramContractId="";
		Connection conn = null;//匡算实体连接
		PreparedStatement pstmt = null;
		ResultSet res=null;//匡算实体查询结果集
		List returnList=new ArrayList();
		try
		{
			String strSQL = 
				"select count(b.id) as contractTimes, a.id"+
				  " from loan_contractform a, "+
				  "     (select * from sett_transreturnfinance where isDelayPayment = "+Constant.TRUE+" and NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+") b "+
				  " where a.id = b.ncontractid(+) "+
				  " and a.id in (";
			for(int i=0;i<contractID.length;i++)
			{
				if(i!=contractID.length-1)
				{
					paramContractId=paramContractId+contractID[i]+",";
				}
				else
				{
					paramContractId=paramContractId+contractID[i]+")";
					
				}
				
			}
			strSQL=strSQL+paramContractId+" group by a.id";
			conn=Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			res=pstmt.executeQuery();
			TransReturnFinanceNewInfo trfni=null;
			while(res.next())
			{
				trfni=new TransReturnFinanceNewInfo();
				trfni.setLjcf(res.getInt(1));
				trfni.setContractID(res.getInt(2));
				returnList.add(trfni);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			throw new IException();
		}
		finally
		{
			try {
				res.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return returnList;
    }
    
    
  //add by zwxiao 2010-06-02 添加通过合同ID来查询连续未付次数
  //参数说明：contractID为合同号码，queryDate一般为开机日期，这块可以传入其他的时间来进行统计查询 
    public static String getContractOutstandingSum(long contractID,Timestamp queryDate) throws IException{
		String strReturn = "0";
		Connection conn = null;//匡算实体连接
		PreparedStatement pstmt = null;
		ResultSet res=null;//匡算实体查询结果集
		try
		{
//			String strSQL = " select aa.contractID, (aa.issue - bb.issue) as issue "+
//			  " from (select a.ncontractid as contractID, count(*) as issue "+
//			         " from loan_loancontractplan a, loan_loancontractplandetail b "+
//			         " where a.id = b.ncontractplanid "+
//			         "  and b.dtplandate <= to_Date('"+DataFormat.formatDate(queryDate) +"','yyyy-MM-dd')"+
//			         " group by a.ncontractid) aa, "+
//			         " (select a.id as contractID, count(b.issue) as issue "+
//			         " from loan_contractform a, Sett_TransReturnFinance b "+
//			         " where a.id = b.ncontractid "+
//			         " and b.NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+
//			         " group by a.id) bb "+
//			 " where aa.contractID = bb.contractID "+
//			 " and aa.contractID = "+contractID;
			//需要考虑外连接
			String strSQL = " select bbb.id as contractID, decode(sign(nvl(issue, 0)-0),1,nvl(issue, 0),-1,0) as issue "+
			                " from (select aa.contractID, (nvl(aa.issue, 0) - nvl(bb.issue, 0)) as issue "+
				   			" from (select a.ncontractid as contractID, count(*) as issue "+
			                "  from (select max(id) as id,ncontractid from loan_loancontractplan where nisused = 2 group by ncontractid) a, loan_loancontractplandetail b "+
			                " where a.id = b.ncontractplanid "+
			                "   and b.dtplandate <= to_Date('"+DataFormat.formatDate(queryDate) +"','yyyy-MM-dd')"+
			                " group by a.ncontractid) aa, "+
			                " (select a.id as contractID, count(b.issue) as issue "+
			                "  from loan_contractform a, Sett_TransReturnFinance b "+
			                " where a.id = b.ncontractid "+
			                "   and b.NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+
			                " group by a.id) bb where aa.contractID = bb.contractID(+)) aaa "+
							" right join loan_contractform bbb on aaa.contractID = bbb.id "+
							" where bbb.id = "+contractID+
							" and bbb.nstatusid = "+LOANConstant.ContractStatus.ACTIVE;
			conn=Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			res=pstmt.executeQuery();
			while (res.next())
			{
				strReturn = String.valueOf(res.getInt("issue")) ;
			}
			res.close();
			pstmt.close();
			conn.close();
		}
		catch (Exception e)
		{
			try {
				res.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new IException();
			}
		}finally
		{
			try {
				res.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException();
			}
			
		}
		return strReturn;
    }
    
  //add by afzhu 2010-06-04 添加通过合同ID来查询连续未付次数
    //参数说明：contractID为合同号码，queryDate一般为开机日期，这块可以传入其他的时间来进行统计查询 -----重载
      public static List getContractOutstandingSum(long contractID[],String queryDate) throws IException{
  		String paramContractId="";
  		Connection conn = null;//匡算实体连接
		PreparedStatement pstmt = null;
		ResultSet res=null;//匡算实体查询结果集
		List returnList=new ArrayList();
  		try
  		{
  		//需要考虑外连接
			String strSQL = " select bbb.id as contractID,decode(sign(nvl(issue, 0)-0),1,nvl(issue, 0),-1,0) as issue, aaa.allnopaymount "+
			                " from (select aa.contractID, (nvl(aa.issue, 0) - nvl(bb.issue, 0)) as issue, aa.allnopaymount - bb.allnopaymount as allnopaymount "+
				   			" from (select a.ncontractid as contractID, count(*) as issue, sum(b.mamount) + sum(b.minterestamount) as allnopaymount  "+
			                "  from (select max(id) as id,ncontractid from loan_loancontractplan where nisused = 2 group by ncontractid) a, loan_loancontractplandetail b "+
			                " where a.id = b.ncontractplanid "+
			                "   and b.dtplandate <= to_Date('"+queryDate +"','yyyy-MM-dd')"+
			                " group by a.ncontractid) aa, "+
			                " (select a.id as contractID, count(b.issue) as issue,sum(b.mcorpusamount) + sum(b.minterestamount) as allnopaymount "+
			                "  from loan_contractform a, Sett_TransReturnFinance b "+
			                " where a.id = b.ncontractid "+
			                "   and b.NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+
			                " group by a.id) bb where aa.contractID = bb.contractID(+)) aaa "+
							" right join loan_contractform bbb on aaa.contractID = bbb.id "+
							" where bbb.id in (";
  			for(int i=0;i<contractID.length;i++)
			{
				if(i!=contractID.length-1)
				{
					paramContractId=paramContractId+contractID[i]+",";
				}
				else
				{
					paramContractId=paramContractId+contractID[i]+")";
					
				}
				
			}
			strSQL=strSQL+paramContractId;
			conn=Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			res=pstmt.executeQuery();
			TransReturnFinanceNewInfo trfni=null;
			while(res.next())
			{
				trfni=new TransReturnFinanceNewInfo();
				trfni.setContractID(res.getInt(1));
				trfni.setLxwf(res.getInt(2));
				trfni.setAllNoPayMount(res.getDouble(3));
				returnList.add(trfni);
			}
  		}
  		catch (Exception e)
  		{
  			System.out.println(e.toString());
  			throw new IException();
  		}
  		finally
		{
			try {
				res.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException();
			}
			
		}
  		return returnList;
      }
}