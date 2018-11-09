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
 * �������ӿ�ʵ����
 * @author xiangzhou 2013-01-08
 *
 */
public class GLJoinCheerBean extends GLExtendBaseBean{
	
	GLDataUtil glDataUtil = new GLDataUtil();
	String GLSubjectView = "v_accountview";
	String GLSubjectBalanceView = "v_accountViewBalance";
	
	/**
     * ��ȡ��Ŀ
     */
    public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
    {
        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            /////���¿�Ŀ��
        	System.out.println("==================���¿�Ŀ����ʼ==================");
            conn = glDataUtil.get_jdbc_connection(lOfficeID,lCurrencyID,0);
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            
            /**
             * ��Ŀ����ͼ��
             * v_accountview
             * 
             * FAccountCode			��Ŀ����	
			 * FAccountName			��Ŀ����	
			 * FAaccountTypeCode	��Ŀ������	
			 * FAaccountTypeName	��Ŀ�������	
			 * Fisleaf				�Ƿ�Ҷ�ӽڵ�	0��1��
			 * Fjdfx				�������	1�裻2��
			 * Fiscustomer			�Ƿ���Ҫ�ͻ�������Ŀ	0��1��
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
                info.setSegmentCode2(rs.getString("FAccountCode"));      //��Ŀ������           
                info.setSegmentName2(rs.getString("FAccountName"));      //��Ŀ����
                info.setSubjectType(rs.getLong("FaccountTypeCode"));     //��Ŀ���Ա���
                if(rs.getInt("Fisleaf")==Constant.RecordStatus.VALID)	 //�Ƿ��ӽڵ�
                {
                	info.setLeaf(true);
                }
                else 
                {
                	info.setLeaf(false);
                }
                if(rs.getLong("Fjdfx")==SETTConstant.DebitOrCredit.DEBIT)		//�������	1�� 2��
                {
                	info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);
                }
                else 
                {
                	info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);
                }
                if(rs.getInt("Fiscustomer")==Constant.RecordStatus.VALID)		//�Ƿ���Ҫ�ͻ�������Ŀ
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
            System.out.println("==================���¿�Ŀ������,����Ϊ��"+list.size()+"==================");
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
     * ��ȡ��Ŀ���
     */
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
       Connection conn = null;
       ArrayList list = new ArrayList();
       PreparedStatement ps = null;
       ResultSet rs = null;
       try
       {
	       //���¿�Ŀ���
	       System.out.println("==================��ȡ��Ŀ����ʼ==================");
	       conn = glDataUtil.get_jdbc_connection(lOfficeID,lCurrencyID,0);
       	
           StringBuffer strSQL = new StringBuffer();
           strSQL.setLength(0);
           
           String strDate=DataFormat.getDateString(tsDate);
           System.out.println(" �õ���ʽ����Ĵ������������ǣ�" +strDate);
           
           String strDate2="";//������� YYYYMM
           strDate2=strDate.replaceAll("-","");
           strDate2=strDate2.substring(0,strDate2.length()-2); 
           System.out.println(" ������䣺" +strDate2);
           
           /**
			 * 
			 * ��Ŀ�����ͼ 
			 * v_accountViewBalance
			 * 
			 * FDATE         				�ڼ�  ( YYYYMM )
			 * FAccountCode  				��Ŀ���� 
			 * FAccountName	 				��Ŀ����
			 * FCurrencyID	 				����
			 * Fdebit        				���ڽ跽������   
			 * Fcredit		            	���ڴ���������
			 * Fbalance      				�������        
			 * cfdate						��������	
			 * fcd							�������	1�裬2��,�����ǿ�Ŀ����
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
               info.setGLDate(tsDate);			//����
               info.setOfficeID(lOfficeID);		//���´�
               info.setCurrencyID(lCurrencyID);	//����
               info.setGLSubjectCode(rs.getString("FAccountCode"));			//��Ŀ��  
               info.setDebitAmount(rs.getDouble("Fdebit"));//�跽������
               info.setCreditAmount(rs.getDouble("Fcredit"));//����������
               if((rs.getLong("fcd")==SETTConstant.DebitOrCredit.DEBIT)){
            	   info.setDebitBalance(rs.getDouble("FBalance"));				//�������
            	   info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);	//���򣺽�
               }else{
            	   info.setCreditBalance(rs.getDouble("FBalance"));				//�������
            	   info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);	//���򣺴�
               }
               list.add(info);
           }
		   System.out.println("==================��ȡ��Ŀ������,����Ϊ��"+list.size()+"==================");
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
	 * �������ƾ֤
	 * @throws Exception 
	 */
	public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
	{
		ArrayList result = new ArrayList();
		String requestXML = "";
		String responseXML = "";
		try {
			System.out.println("==================�������ƾ֤����ʼ==================");
			requestXML = glDataUtil.getVoucherRequestXML(collGlVoucher,lOfficeID,lCurrencyID,date);
			
//			IVoucherGenerateSvr port = new VoucherGenerateSvr().getVoucherGenerateSvrPort();
//			responseXML = port.generateVoucher(requestXML);
			
			result = glDataUtil.getVoucherResult(responseXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		System.out.println("==================�������ƾ֤������==================");
		return result;
	}

}
