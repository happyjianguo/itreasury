package com.iss.itreasury.glinterface.joinCheer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.joinCheer.GLDataUtil;
//import com.iss.itreasury.glinterface.joinCheer.webservice.IVoucherGenerateSvr;
//import com.iss.itreasury.glinterface.joinCheer.webservice.VoucherGenerateSvr;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * 久其财务接口实现类
 * @author xiangzhou 2013-01-08
 *
 */
public class GLJoinCheerBean extends GLExtendBaseBean{
	
	GLDataUtil glDataUtil = new GLDataUtil();
	String GLSubjectView = "v_accountview";
	String GLSubjectBalanceView = "v_accountViewBalance";
	
	/**
     * 获取科目
     */
    public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
    {
        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            /////更新科目号
        	System.out.println("==================更新科目：开始==================");
            conn = glDataUtil.get_jdbc_connection(lOfficeID,lCurrencyID,0);
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            
            /**
             * 科目表（视图）
             * v_accountview
             * 
             * FAccountCode			科目编码	
			 * FAccountName			科目名称	
			 * FAaccountTypeCode	科目类别编码	
			 * FAaccountTypeName	科目类别名称	
			 * Fisleaf				是否叶子节点	0否；1是
			 * Fjdfx				借贷方向	1借；2贷
			 * Fiscustomer			是否需要客户辅助项目	0否；1是
             */
            
            sbSQL.append(" select FAccountCode,FAccountName,FaccountTypeCode,Fisleaf,Fjdfx,Fiscustomer \n ");            
            sbSQL.append(" from \n " + GLSubjectView);            
            sbSQL.append(" order by FAccountCode \n ");
            
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                GLSubjectDefinitionInfo info = new GLSubjectDefinitionInfo();
                info.setOfficeID(lOfficeID);
                info.setCurrencyID(lCurrencyID);
                info.setSegmentCode2(rs.getString("FAccountCode"));      //科目类别编码           
                info.setSegmentName2(rs.getString("FAccountName"));      //科目名称
                info.setSubjectType(rs.getLong("FaccountTypeCode"));     //科目属性编码
                if(rs.getInt("Fisleaf")==Constant.RecordStatus.VALID)	 //是否子节点
                {
                	info.setLeaf(true);
                }
                else 
                {
                	info.setLeaf(false);
                }
                if(rs.getLong("Fjdfx")==SETTConstant.DebitOrCredit.DEBIT)		//借贷方向	1借 2贷
                {
                	info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);
                }
                else 
                {
                	info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);
                }
                if(rs.getInt("Fiscustomer")==Constant.RecordStatus.VALID)		//是否需要客户辅助项目
                {
                	info.setCustomer(true);
                }
                else 
                {
                	info.setCustomer(false);
                }
                info.setStatusID(Constant.RecordStatus.VALID);
                
                list.add(info);
                
            }
            System.out.println("==================更新科目：结束,个数为："+list.size()+"==================");
        }
        catch (Exception e)
        {
            Log.print(e.toString());
            throw new Exception(e.getMessage());
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
                Log.print(e.toString());
            }
        }
        return (list != null && list.size() > 0 ? list : null);
    }
    
    /**
     * 获取科目余额
     */
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
       Connection conn = null;
       ArrayList list = new ArrayList();
       PreparedStatement ps = null;
       ResultSet rs = null;
       try
       {
	       //更新科目余额
	       System.out.println("==================获取科目余额：开始==================");
	       conn = glDataUtil.get_jdbc_connection(lOfficeID,lCurrencyID,0);
       	
           StringBuffer strSQL = new StringBuffer();
           strSQL.setLength(0);
           
           String strDate=DataFormat.getDateString(tsDate);
           System.out.println(" 得到格式化后的传进来的日期是：" +strDate);
           
           String strDate2="";//余额区间 YYYYMM
           strDate2=strDate.replaceAll("-","");
           strDate2=strDate2.substring(0,strDate2.length()-2); 
           System.out.println(" 余额区间：" +strDate2);
           
           /**
			 * 
			 * 科目余额视图 
			 * v_accountViewBalance
			 * 
			 * FDATE         				期间  ( YYYYMM )
			 * FAccountCode  				科目编码 
			 * FAccountName	 				科目名称
			 * FCurrencyID	 				币种
			 * Fdebit        				本期借方发生额   
			 * Fcredit		            	本期贷方发生额
			 * Fbalance      				本期余额        
			 * cfdate						更新日期	
			 * fcd							借贷方向	1借，2贷,余额方向不是科目方向
			 */
           
           strSQL.append(" select FDATE, FAccountCode, FAccountName,FCurrencyID,Fdebit,Fcredit,FBalance,fcd \n ");
           strSQL.append(" from \n" + GLSubjectBalanceView);
           strSQL.append(" where FDATE = ? \n");
			
		   ps = conn.prepareStatement(strSQL.toString());
		   ps.setString(1, strDate2);
		   rs = ps.executeQuery();
			
		   while (rs != null && rs.next())			
           {
               GLBalanceInfo info = new GLBalanceInfo();
               info.setGLDate(tsDate);			//日期
               info.setOfficeID(lOfficeID);		//办事处
               info.setCurrencyID(lCurrencyID);	//币种
               info.setGLSubjectCode(rs.getString("FAccountCode"));			//科目号  
               info.setDebitAmount(rs.getDouble("Fdebit"));//借方发生额
               info.setCreditAmount(rs.getDouble("Fcredit"));//贷方发生额
               if((rs.getLong("fcd")==SETTConstant.DebitOrCredit.DEBIT)){
            	   info.setDebitBalance(rs.getDouble("FBalance"));				//本期余额
            	   info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);	//余额方向：借
               }else{
            	   info.setCreditBalance(rs.getDouble("FBalance"));				//本期余额
            	   info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);	//余额方向：贷
               }
               list.add(info);
           }
		   System.out.println("==================获取科目余额：结束,个数为："+list.size()+"==================");
       }
       catch (Exception e)
       {
           Log.print(e.toString());
           throw new Exception(e.getMessage());
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
               Log.print(e.toString());
           }
       }

       return (list != null && list.size() > 0 ? list : null);
    }
	
	/**
	 * 导出会计凭证
	 * @throws Exception 
	 */
	public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
	{
		ArrayList result = new ArrayList();
		String requestXML = "";
		String responseXML = "";
		try {
			System.out.println("==================导出会计凭证：开始==================");
			requestXML = glDataUtil.getVoucherRequestXML(collGlVoucher,lOfficeID,lCurrencyID,date);
			
//			IVoucherGenerateSvr port = new VoucherGenerateSvr().getVoucherGenerateSvrPort();
//			responseXML = port.generateVoucher(requestXML);
			
			result = glDataUtil.getVoucherResult(responseXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		System.out.println("==================导出会计凭证：结束==================");
		return result;
	}

}
