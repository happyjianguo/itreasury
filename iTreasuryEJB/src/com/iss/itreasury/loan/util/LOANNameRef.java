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
 * <li>getClientNameByID(long lID)  						�ͻ�(ҵ��λ),ͨ��ID�������
 * <li>getCounterpartNameByID(long lID)  					���׶���,ͨ��ID�������
 * <li>getCounterpartCodeByID(long lID)						���׶���,ͨ��ID���CODE
 * <li>getTransTypeNameByID(long lID)						��������,ͨ��ID�������
 * <li>getTransAttributeByTransTypeID(long lID)				ͨ����������ID��ý�������ID
 * <li>getApplyFormCodeByID(long lID)						������,ͨ��IDȡ��CODE
 * <li>getAccountIDFromDeliverOrder(DeliveryOrderInfo doInfo)�ӽ���и��ݽ�������ȷ���ý��ʹ�õ��ʽ��˻�ID 
 * <li>getTransTypeIDByApplyFormID(long lID)				��������ID��ý������� ID
 * <li>getDeliveryOrderCodeByID(long lID)					ͨ�����ID��ý��CODE
 * <li>getUserNameCodeByID(long lID)						ͨ���û�ID�����û�����
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
    *                   NameRefˢ�º�����
    *************************************************/
    
    /**
	 * ���ܣ�ͨ���ͻ�����ID��ѯ�ͻ���������
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
    * ��ID��code������Ʒ����Ĺ��÷���1
    * @param strNameField           //�����ֶ���
    * @param strTableName           //����
    * @param strIdField         //id����code�ֶ���
    * @param strValue               //id����code��ֵ
    * @return
    */
   public static String getNameByID(String strNameField,String strTableName,String strIdField,String strValue,HashMap map){
       String strReturn = null;
       try
       {
           String strSQL = "select " + strNameField + " from " + strTableName + " where " + strIdField + "='" + strValue+"'";
           Log.print("NameRef SQL:"+strSQL);
           if (map != null){                                //���ȴ�HashMap�л�ȡ����
               strReturn = (String)map.get(strValue);
           }
           if (strReturn == null){                      //���HashMap��û��,������ݿ��л��
               Collection c = LOANHTML.getCommonSelectList(strSQL, strNameField);
               if (c != null)
               {
                   Object o = c.iterator().next();
                   Log.print("NameRef return value:"+o.toString());
                   strReturn = String.valueOf(o);
               }
               if (strReturn == null) strReturn = "";
               if (map != null)map.put(strValue,strReturn);             //����HashMap
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
    * ��ID��code������Ʒ����Ĺ��÷���2
    * @param strSQL                 sql
    * @param strField                   Ҫ�õ����ֶ�
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
    * ��һ��ID������е�Name,�м���","�ָ�
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
    * ��һ��ID������е�Name,�м���","�ָ�
    * @param strSql                         SQL���
    * @param strField                           Ҫ��õ��ֶ���
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
     * ͨ���ͻ�ID��ÿͻ�����
     * @param lID
     * @return
     */
    public static String getClientNameById(long lID){
        return getNameByID("sname","client","id",String.valueOf(lID),null);
    }
    
	/**
	 * ͨ���ͻ�ID��ÿͻ����
	 * @param lID
	 * @return
	 */
	public static String getClientCodeById(long lID){
		return getNameByID("sCode","client","id",String.valueOf(lID),null);
	}

	/**
	 * ͨ���˻����
	 * ���ұ�֤�����
	 * @author yunchang
	 * @date 2010-08-04
	 */
	public static double getDepositMountCount(String constractid)
	{
		String sql = "select to_char(nvl(sum(mbalance), 0)) as mbalance from sett_subaccount where id in (select collectionrentaccountid from loan_assurechargeform where statusid = "+LOANConstant.LoanPayNoticeStatus.USED+" and contractid = "+constractid+")";
		return Double.parseDouble((getNameByID(sql,"mbalance")));
	}

    /**
     * ͨ���û�ID����û�����
     * @param lID
     * @return
     */
    public static String getUserNameById(long lID){
        return getNameByID("sname","userinfo","id",String.valueOf(lID),null);
    }
    /**
     * ͨ����Libor����ID���Libor��������
     * @param lID
     * @return
     */
    public static String getLiborNameById(long lID){
        return getNameByID("liborname","loan_liborinterestrate","id",String.valueOf(lID),null);
    }
    


    /**
     * ͨ�����Ų�����ID��ò���������
     * @param lID
     * @return
     */
    public static String getAttendBankNameById(long lID){
        return getNameByID("name","loan_yt_attendbank","id",String.valueOf(lID),null);
    }
    /**
     * ͨ�����Ų�����ID��ò����п�����
     * @param lID
     * @return
     */
    public static String getAttendBankById(long lID){
        return getNameByID("bank","loan_yt_attendbank","id",String.valueOf(lID),null);
    }
    /**
     * ͨ�����Ų�����ID��ò����п������˻�����
     * @param lID
     * @return
     */
    public static String getAttendBankAccountNoById(long lID){
        return getNameByID("bankAccountNo","loan_yt_attendbank","id",String.valueOf(lID),null);
    }


	/**
	 * ͨ��ID��ô����������ƣ����ࣩ
	 * @param lID
	 * @return
	 */
	public static String getSubLoanTypeNameByID(long lID){
		return getNameByID("name","Loan_LoanTypeSetting","id",String.valueOf(lID),null);
	}


	/**
	 * ͨ��ID��ô�������Code�����ࣩ
	 * @param lID
	 * @return
	 */
	public static String getSubLoanTypeCodeByID(long lID){
		return getNameByID("code","Loan_LoanTypeSetting","id",String.valueOf(lID),null);
	}
	

	/**
	 * ͨ��ID������������ƣ����ࣩ
	 * @param lID
	 * @return
	 */
	public static String getApprovalSettingNameByID(long lID){
		return getNameByID("sName","loan_approvalSetting","id",String.valueOf(lID),null);
	}
	
	/**
	 * ͨ����ͬID����Ӵ�������
	 * @param lID
	 * @return
	 */
	public static String getSubTypeByContractID(long lID){
		return getNameByID("nsubtypeid","loan_contractform","id",String.valueOf(lID),null);
	}
	
   /**
    * Modify by leiyang 2007/09/05 ��Ʊ�ݹ������跽��
    * ͨ���ͻ�ID��ÿͻ�����
    * @param lID
    * @return
    */
    public static String getClientNameById1(long lID){
       return getNameByID("name","sec_counterpart","id",String.valueOf(lID),null);
    }
    
    //add by zwxiao 2010-06-02 ���ͨ����ͬID����ѯ�ۼƳٸ�����
    public static String getContractLateSum(long contractID) throws IException{
		String strReturn = "0";
		Connection conn = null;//����ʵ������
		PreparedStatement pstmt = null;
		ResultSet res=null;//����ʵ���ѯ�����
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
    
    //add by afzhu 2010-06-04 ���ͨ����ͬID����ѯ�ۼƳٸ�����----����
    public static List getContractLateSum(long contractID[]) throws IException{
		String paramContractId="";
		Connection conn = null;//����ʵ������
		PreparedStatement pstmt = null;
		ResultSet res=null;//����ʵ���ѯ�����
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
    
    
  //add by zwxiao 2010-06-02 ���ͨ����ͬID����ѯ����δ������
  //����˵����contractIDΪ��ͬ���룬queryDateһ��Ϊ�������ڣ������Դ���������ʱ��������ͳ�Ʋ�ѯ 
    public static String getContractOutstandingSum(long contractID,Timestamp queryDate) throws IException{
		String strReturn = "0";
		Connection conn = null;//����ʵ������
		PreparedStatement pstmt = null;
		ResultSet res=null;//����ʵ���ѯ�����
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
			//��Ҫ����������
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
    
  //add by afzhu 2010-06-04 ���ͨ����ͬID����ѯ����δ������
    //����˵����contractIDΪ��ͬ���룬queryDateһ��Ϊ�������ڣ������Դ���������ʱ��������ͳ�Ʋ�ѯ -----����
      public static List getContractOutstandingSum(long contractID[],String queryDate) throws IException{
  		String paramContractId="";
  		Connection conn = null;//����ʵ������
		PreparedStatement pstmt = null;
		ResultSet res=null;//����ʵ���ѯ�����
		List returnList=new ArrayList();
  		try
  		{
  		//��Ҫ����������
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