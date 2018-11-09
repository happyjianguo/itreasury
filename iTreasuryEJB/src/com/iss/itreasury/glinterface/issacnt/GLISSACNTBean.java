/*
 * Created on 2006-07-05
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.issacnt;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.dataentity.GLISSAcntVchInfo;
import com.iss.itreasury.glinterface.oracle_cpf.GLOracleFinBean_cpf;
import com.iss.itreasury.glinterface.u850.ConnectionSQLServer;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.glinterface.u850.GLU850Bean;

/**
 * @author liuqing
 *
 * �޸���ʷ  1��yyhe��2006.11.01�޸ģ����Ӹ����˺������ױ�������ֶ���´�
 * 
 */
public class GLISSACNTBean extends GLExtendBaseBean
{

    public static void main(String[] args)
    {
    	GLISSACNTBean bean = new GLISSACNTBean();
    	
    	try {
    		//System.out.println(DataFormat.getDateString(DataFormat.getDateTime("2005-01-31")));
    		
    		//bean.getGLSubject(1,1);
    		//bean.getGLSubjectBalance(1,1,DataFormat.getDateTime("2005-01-31"));
    		//bean.getGLSubjectAmount(1,1,1,DataFormat.getDateTime("2005-01-31"));
    		
    		//System.out.println("��Ŀ���룺" + bean.getAccountId(1,1,"191.02"));
		} catch (Exception e) {
	
			e.printStackTrace();
		}
    }

    /*
     * ������ƾ֤ ������Collection
     * ���ص�ƾ֤���ϣ�
     */
    public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
    {
        try
        {
        	postGLVoucherToISSACNT(collGlVoucher,lOfficeID,lCurrencyID,date);                        
  
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    /*
     * �����������Ӳ���ģ���ȡ��Ŀ��
     */
    public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
    {
        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        PreparedStatement psBatch = null;
        ResultSet rs = null;
        try
        {
            /*
				select BranchCode, ItemCode, ItemName, ItemSortID, DetailFlag, case when Side = 1 then 1 else 2 end as Side
				from v02ItemPurview
				where LedgerID = 1 and Annual = (select year(getdate()))
				and BranchCode = '000'
				order by ItemCode 
            */
            System.out.println("===================================׼���Ӳ���ģ��ȡ��Ŀ������");
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
            System.out.println("===================================ȡ������ϣ�����");
            
            //�õ�������Ϣ
			GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,0);
			
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  select BranchCode,ltrim(rtrim(ItemCode)) ItemCode,ItemName,ItemSortID,DetailFlag,case when Side = 1 then 1 else 2 end as Side  \n ");            
            sbSQL.append("  from v02ItemPurview \n ");            
            sbSQL.append("  where LedgerID = 1 and Annual = (select year(getdate())) and BranchCode = '"+glSettingInfo.getBranChcode()+"' \n ");
            sbSQL.append("  order by ItemCode \n ");
            System.out.print("*********��Ŀ��ϢSql:**********\n"+sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                GLSubjectDefinitionInfo info = new GLSubjectDefinitionInfo();
                info.setOfficeID(lOfficeID);
                info.setCurrencyID(lCurrencyID);
                
                info.setSegmentCode2(rs.getString("ItemCode"));                
                info.setSegmentName2(rs.getString("ItemName"));
                info.setSubjectType(rs.getLong("ItemSortID"));  
                if(rs.getInt("DetailFlag")==1)
                {
                	info.setLeaf(true);
                }
                else 
                {
                	info.setLeaf(false);
                }
                info.setBalanceDirection(rs.getLong("Side"));
                info.setStatusID(Constant.RecordStatus.VALID);
                //�����ֶο��Ժ���
                list.add(info);
                
                //System.out.println(rs.getString("kmdm"));
            }
            System.out.print(list.size()+"\n");
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            conn.close();
            conn = null;
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
                if (psBatch != null)
                {
                    psBatch.close();
                    psBatch = null;
                }
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
    
    /* 
     * �õ�����ģ�����˵��տ�Ŀ���
     * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectBalance(long, long, java.sql.Timestamp)
     */
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		//for(int i=0;i<5;i++)
			System.out.println("==========ISSACNT=============�õ�����ģ��Ŀ�Ŀ���");
		
		
		Log.print("���뵽�õ���Ŀ���ķ������棡--------��ʼ��!!");
   	 //ԭ���ķ���
       Connection conn = null;
       ArrayList list = new ArrayList();
       PreparedStatement ps = null;
       ResultSet rs = null;
       long lImportCurrencyID = -1;
       try
       {
            // �õ�������Ϣ
			GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,0);
			
       	lImportCurrencyID = lCurrencyID;
           /////���¿�Ŀ���
       	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
       	
           StringBuffer strSQL = new StringBuffer();
           strSQL.setLength(0);
           
           //�õ�����ģ����ָ�����ڵ������Ϣ��
           /*
            * 
				sp_02_ItemBalance 1,2006,'R','','','2006-01-01','2006-07-31',1,1,'02',0,'000',''
			*	
            */
           
           String strDate=DataFormat.getDateString(tsDate);
           System.out.println(" �õ���ʽ����Ĵ������������ǣ�" +strDate);
           
           strSQL.append(" sp_02_ItemBalance 1," + strDate.substring(0,4) + ",'R','','','" + strDate + "','" + strDate + "',1,1,'02',0,'"+glSettingInfo.getBranChcode()+"','' \n ");          
			System.out.print(strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			Log.print("***********��ѯ����ģ���Ŀ���sp��" + strSQL.toString());
			rs = ps.executeQuery();
			
			
			int i=1;
			//GLBalanceInfo����Ŀ��Ϣ��������ݣ���ʱ�п��ܻ����ΪǰSQL�����͵ģ���3��0��Ϣ�е㲻ƥ��
			while (rs != null && rs.next())			
           {
				
                Log.print("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("ItemCode"));
				Log.print("  �õ����  :  " + rs.getDouble("Amount"));
							
               GLBalanceInfo info = new GLBalanceInfo();
               info.setGLDate(tsDate);			//����
               info.setOfficeID(lOfficeID);		//���´�
               info.setCurrencyID(lCurrencyID);	//����
               info.setGLSubjectCode(rs.getString("ItemCode"));	//��Ŀ��
               if (rs.getLong("Side") == 1){
               	 info.setBalanceDirection(1);	//����
               }else{ 
               	 info.setBalanceDirection(2); 
               }
               if (rs.getLong("Side") == 1){ 	//�跽���
	             info.setDebitBalance(rs.getDouble("Amount"));	
	             info.setCreditBalance(0);	
               }else {	//�������
                 info.setDebitBalance(0);	
                 info.setCreditBalance(rs.getDouble("Amount"));	
               }
               Log.print("������ĸ���Ϊ:"+i++);
               
               list.add(info);
           }
			
			if(list==null && list.size()==0){
				System.out.println("=============�õ�������Ϊ��!");
			}else{
				System.out.println("=============�õ������ݲ�Ϊ��!�����ݣ�������");
				//System.out.println("============="+	((GLBalanceInfo)list.get(0)).getGLSubjectCode() );
			}
			
		   Log.print("���뵽�õ���Ŀ���ķ������棡--------����!!��");
           rs.close();
           rs = null;
           ps.close();
           ps = null;
           conn.close();
           conn = null;
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
		//return null;
    }
	
	
	/* 
	 * �õ���Ŀ(����)���������  (����ģ��)
	 * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectAmount(long, long, long, java.sql.Timestamp)
	 */     
    public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsDate) throws Exception
    {
    
    	//for(int i=0;i<5;i++)
			System.out.println("==========ISSACNT=============�õ�����ģ��Ŀ�Ŀ������");

        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        long lImportCurrencyID = -1;
        try
        {
            // �õ�������Ϣ
			GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,0);
			
        	lImportCurrencyID = lCurrencyID;
            
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
        	
            StringBuffer strSQL = new StringBuffer();
            strSQL.setLength(0);
            
            //�õ�����ģ����ָ�����ڵĿ�Ŀ����������
            /*
				sp_02_ItemBalance 1,2006,'R','','','2006-01-01','2006-07-31',1,1,'02',0,'000',''
            */
            
            String strDate=DataFormat.getDateString(tsDate);
            System.out.println(" �õ���ʽ����Ĵ������������ǣ�" +strDate);
                        
            strSQL.append(" sp_02_ItemBalance 1," + strDate.substring(0,4) + ",'R','','','" + strDate + "','" + strDate + "',1,1,'02',0,'"+glSettingInfo.getBranChcode()+"','' \n ");          
           
            System.out.println(strSQL); 			
 			ps = conn.prepareStatement(strSQL.toString());
 			Log.print("***********��ѯ����ģ���Ŀ������sp��" + strSQL.toString());
 			rs = ps.executeQuery();
 			
 			
 			int i=1;
 			while (rs != null && rs.next())			
            {
 				
 				Log.print("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("ItemCode"));
 				Log.print("  �õ�������   " + strDate + "   �跽��" + rs.getDouble("dAmount") + "  ������" + rs.getDouble("cAmount"));
 				///////////Log.print("  �õ���������   " + strDate + "   �跽��" + rs.getLong("dCount") + "  ������" + rs.getLong("cCount"));
 							
                GLBalanceInfo info = new GLBalanceInfo();
                info.setGLDate(tsDate);				//����
                info.setOfficeID(lOfficeID);		//���´�
                info.setCurrencyID(lCurrencyID);	//����
                info.setGLSubjectCode(rs.getString("ItemCode"));			//��Ŀ��
                info.setDebitAmount(rs.getDouble("dAmount"));		//�跽������
                info.setCreditAmount(rs.getDouble("cAmount"));	//����������
                ////////////���ڳ������⣬����ʱ��ʱȥ��
                ///////////info.setDebitNumber(rs.getLong("dCount"));		//�跽����
                ///////////info.setCreditNumber(rs.getLong("cCount"));		//��������
               
                
                //System.out.println("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("ItemCode"));
                //System.out.println("  �õ�������   " + strDate + "   �跽��" + rs.getDouble("dAmount") + "  ������" + rs.getDouble("cAmount"));
                //System.out.println("  �õ���������   " + strDate + "   �跽��" + rs.getDouble("dCount") + "  ������" + rs.getDouble("cCount"));
                
                Log.print("������ĸ���Ϊ:"+i++);
                
                list.add(info);
            }
 			
 			if(list==null && list.size()==0){
 				System.out.println("=============�õ�������Ϊ��!");
 			}else{
 				System.out.println("=============�õ������ݲ�Ϊ��!�����ݣ�������");
 				//System.out.println("============="+	((GLBalanceInfo)list.get(0)).getGLSubjectCode() );
 			}
 			
 		   Log.print("���뵽�õ���Ŀ������ķ������棡--------����!!��");
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            conn.close();
            conn = null;
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
	

    /*
     * postGLVoucherToISSACNT()���ж϶Է�������Ϣ�Ƿ����
     */
    private static long postGLVoucherToISSACNT(Collection collVoucher, long lOfficeID, long lCurrencyID, Timestamp date) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        //PreparedStatement ps1 = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        try
        {
        	//�õ�����������Ϣ
        	System.out.println("---�õ�����������Ϣ---");
        	GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1);

            //conn = ConnectionSQLServer.getConnection(lOfficeID,lCurrencyID);
			conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
            
            conn.setAutoCommit(false);
            if (collVoucher != null)
            {
            	System.out.println("ȡƾ֤��Ϣ����������������������");
                Iterator it = collVoucher.iterator();
                
                //��¼����
                int EntryCount = 0;
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+collVoucher.size());
                while (it.hasNext())
                {
                	EntryCount = 0;

                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    voucher.setPostStatusID(Constant.GLPostStatus.DOING);	//��������

                    GLISSAcntVchInfo VchInfo = new GLISSAcntVchInfo();
				
	                VchInfo.setLedgerID(1); 
	                VchInfo.setAnnaul(voucher.getPostDate().getYear()+1900);
                    VchInfo.setCurrCode(getCurrCode(conn,voucher.getEntryInfo(0).getCurrencyID()));
                    //VchInfo.setBranchCode(getBranchCode(conn,voucher.getEntryInfo(0).getOfficeID()));
                    VchInfo.setBranchCode(glSettingInfo.getBranChcode());
                    
                    VchInfo.setUserCode(glSettingInfo.getGlUserName());
                    
                    VchInfo.setVchDate(voucher.getPostDate());  
                    if (voucher.getList().size() > 0)
                    {
                    	VchInfo.setVchGrp(getVchGrp(voucher.getEntryInfo(0).getTransactionTypeID()));                        
                    }
                    else
                    {
                    	VchInfo.setVchGrp(Integer.parseInt(glSettingInfo.getGlVoucherType()));//�ӽӿ���Ϣȡƾ֤����                    
                    }
                    VchInfo.setTransId(0);
                    VchInfo.setVchNo(getVchNo(conn,VchInfo.getLedgerID(),VchInfo.getAnnual(),VchInfo.getCurrCode(),VchInfo.getVchDate(),VchInfo.getVchGrp(),VchInfo.getBranchCode(),VchInfo.getUserCode()));
                    VchInfo.setAffix(0);
                    VchInfo.setRows(voucher.getList().size());
                    System.out.println("###############################="+voucher.getList().size());
                    
                    for (EntryCount = 0; voucher.getList() != null && EntryCount < voucher.getList().size(); EntryCount++)
                    {
                        
                        GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(EntryCount);
                        
                        VchInfo.setVchMemo(DataFormat.formatNullString(entryinfo.getAbstract()));
                        VchInfo.setItemCode(entryinfo.getSubject());
                        VchInfo.setQuantity(0);
                       	if(entryinfo.getDirectionID()==1)
                        {
                        	VchInfo.setDAmount(entryinfo.getAmount());
                        	VchInfo.setCAmount(0);
                        }
                        else if (entryinfo.getDirectionID()==2)
                        {
                        	VchInfo.setDAmount(0);
                        	VchInfo.setCAmount(entryinfo.getAmount());
                        }
                       	VchInfo.setIntrDate(entryinfo.getInterestStart());
                       	VchInfo.setNoteType(0);
                       	VchInfo.setNote("");
                       	
                       	VchInfo.setChecker(glSettingInfo.getGlUserName()); //yyhe��Ӹ����� 2006.11.02 �����н���Ŀ
                       	
                        //Log.print("************** before Save Voucher **************");

                        System.out.println("before Save Voucher");
                        
                        sbRecord.setLength(0);
//                        sbRecord.append(" sp_02_SaveVoucher ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ");
                        sbRecord.append(" sp_02_SaveVoucher ");
                        sbRecord.append(VchInfo.getLedgerID() + "," + VchInfo.getAnnual() + ",'" + VchInfo.getCurrCode() + "'," + VchInfo.getTransId() + ",'" + DataFormat.formatDate(VchInfo.getVchDate(),1) + "',");
                        sbRecord.append(VchInfo.getVchNo() + ",'" + VchInfo.getVchMemo() + "'," + VchInfo.getVchGrp() + ",'" + VchInfo.getItemCode() + "',");
                        sbRecord.append(VchInfo.getQuantity() + ","  + VchInfo.getDAmount() + "," + VchInfo.getCAmount() + ",'");                        
                        sbRecord.append(DataFormat.formatDate(VchInfo.getIntrDate(),1) + "'," + VchInfo.getNoteType() + ",'" + VchInfo.getNote() + "',");
                        sbRecord.append(VchInfo.getAffix() + ",null,null,null,0," + VchInfo.getRows() + ",null,'" + VchInfo.getBranchCode() + "','" + VchInfo.getUserCode() + "'" );                        
                        //Log.print(sbRecord.toString());
                        System.out.println(sbRecord.toString());
                        ps = conn.prepareStatement(sbRecord.toString());
                        //ps.addBatch(sbRecord.toString());
                        
//                        sbRecord.append("{ call sp_02_SaveVoucher (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
//                        
//                        cs = conn.prepareCall(sbRecord.toString());
//
//                        int nIndex = 1;
//	
//                        cs.setInt(nIndex++, VchInfo.getLedgerID());
//                        System.out.println("1********"+ VchInfo.getLedgerID());
//                        cs.setInt(nIndex++, VchInfo.getAnnual());
//                        System.out.println("2********"+ VchInfo.getAnnual());
//                        cs.setString(nIndex++, VchInfo.getCurrCode());
//                        System.out.println("3********"+ VchInfo.getCurrCode());
//                        cs.setInt(nIndex++, VchInfo.getTransId());
//                        System.out.println("4********"+ VchInfo.getTransId());
//                        cs.setTimestamp(nIndex++, VchInfo.getVchDate());
//                        System.out.println("5********"+ VchInfo.getVchDate());
//                        cs.setInt(nIndex++, VchInfo.getVchNo());
//                        System.out.println("6********"+ VchInfo.getVchNo());
//                        cs.setString(nIndex++, VchInfo.getVchMemo());
//                        System.out.println("7********"+ VchInfo.getVchMemo());
//                        cs.setInt(nIndex++, VchInfo.getVchGrp());
//                        System.out.println("8********"+ VchInfo.getVchGrp());
//                        cs.setString(nIndex++, VchInfo.getItemCode());
//                        System.out.println("9********"+ VchInfo.getItemCode());
//                        cs.setDouble(nIndex++, VchInfo.getQuantity());
//                        System.out.println("10*******"+ VchInfo.getQuantity());
//                        cs.setDouble(nIndex++, VchInfo.getDAmount());
//                        System.out.println("11*******"+ VchInfo.getDAmount());
//                        cs.setDouble(nIndex++, VchInfo.getCAmount());
//                        System.out.println("12*******"+ VchInfo.getCAmount());
//                        cs.setTimestamp(nIndex++, VchInfo.getIntrDate());
//                        System.out.println("13*******"+ VchInfo.getIntrDate());
//                        cs.setInt(nIndex++, VchInfo.getNoteType());
//                        System.out.println("14*******"+ VchInfo.getNoteType());
//                        if( VchInfo.getNote()=="" || VchInfo.getNote().equals("") ) {
//                        	cs.setString(nIndex++, "0");
//                        	//System.out.println("15*******---------1111"+ VchInfo.getNote());
//                        }else{
//                        	cs.setString(nIndex++, VchInfo.getNote());
//                        }
//                        System.out.println("15*******"+ VchInfo.getNote());
//                        cs.setInt(nIndex++, VchInfo.getAffix());	
//                        System.out.println("16*******"+ VchInfo.getAffix());
//                        cs.setString(nIndex++, null);			 
//                        System.out.println("17*******----------" );
//                        cs.setString(nIndex++, null);  	
//                        System.out.println("18*******----------" );
//                        cs.setString(nIndex++, null); 
//                        System.out.println("19*******----------" );
//                        cs.setInt(nIndex++, 0);	
//                        System.out.println("20*******----------" );
//                        cs.setInt(nIndex++, VchInfo.getRows());			
//                        System.out.println("21*******"+ VchInfo.getRows());
//                        cs.setString(nIndex++, null);		 
//                        System.out.println("22*******----------" );
//                        cs.setString(nIndex++, VchInfo.getBranchCode());	
//                        System.out.println("23*******"+ VchInfo.getBranchCode());
//                        cs.setString(nIndex++, VchInfo.getUserCode());	
//                        System.out.println("24*******"+ VchInfo.getUserCode());
//		                
//                        cs.executeUpdate();
//						
//                        //Log.print("************** after Save Voucher **************");
//                        System.out.println("after Save Voucher");
//                                                
//                        cs.close();
//                        cs = null;
                        ps.executeUpdate();
                        
                        System.out.println("after Save Voucher");
                        
                        ps.clearParameters();
                        //ps.clearBatch();
                        ps.close();
                        //yyhe�����Ƶ��ˡ������ˡ�״̬���� 2006.11.02 �����н���Ŀ
                        
                        String sql = "update t02Vch set Creator='"+VchInfo.getChecker()+"',Checker='"+VchInfo.getChecker()+"',State=2 where LedgerID="+VchInfo.getLedgerID() + " and Annual=" + VchInfo.getAnnual() + " and CurrCode='" + VchInfo.getCurrCode() + "' and BranchCode='" + VchInfo.getBranchCode() + "' and VchDate='" + DataFormat.formatDate(VchInfo.getVchDate(),1) + "'";
                        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$="+sql);
                        ps = conn.prepareStatement(sql);
                        ps.executeUpdate();
                        ps.close();
                        ps = null;
                        
                        
                        
                    }
                                        
                    //������ɣ����ô���״̬
                    voucher.setPostStatusID(Constant.GLPostStatus.SUCCESS);	//���
                    System.out.println("setPostStatusID Over");                    
                    
                }
            }
           
            conn.commit();
            conn.close();
            conn = null;
            
        }
        catch (Exception e)
        {
            lDealStatusID = Constant.ShutDownStatus.FAIL;
            e.printStackTrace();
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (cs != null)
            {
                cs.close();
                cs = null;
            }
            if (conn != null)
            {
                conn.rollback();
                conn.close();
                conn = null;
            }
            throw new RemoteException(e.getMessage());
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
                if (cs != null)
                {
                    cs.close();
                    cs = null;
                }
                if (conn != null)
                {
                    conn.close();
                    conn = null;
                }
            }
            catch (Exception e)
            {
                throw new Exception("remote exception : " + e.toString());
            }
        }
        return lDealStatusID;
    }
    
    
    /**
     * �õ�����ģ���Ӧ�ı��ִ���
     * @param lOfficeID,lCurrencyID
     * @return
     */
    public static String getCurrCode(Connection conn, long lCurrencyID) throws Exception
	{
          String strCurrCode = "R";
          PreparedStatement ps = null;        
          ResultSet rs = null;        

          if (lCurrencyID == 1)
          	strCurrCode = "R";
          else if (lCurrencyID == 2)
          	strCurrCode = "$";
          else if (lCurrencyID == 3)
            	strCurrCode = "E";
          else if (lCurrencyID == 18)
            	strCurrCode = "H";
          else if (lCurrencyID == 25)
            	strCurrCode = "J";
          
          
    	return strCurrCode;
    	
    }

    /**
     * �õ�����ģ���Ӧ�����״���
     * @param lOfficeID,lCurrencyID
     * @return
     */
    public static String getBranchCode(Connection conn, long lOfficeID) throws Exception
	{
          String strBranchCode = "000";
          PreparedStatement ps = null;        
          ResultSet rs = null; 
          
          if (lOfficeID == 1) 
          	strBranchCode = "000";
          else 
          	strBranchCode = "000";
          
    	return strBranchCode;
    	
    }
    
    /**
     * ת��ƾ֤����
     * @param lTransTypeId
     * @return
     */
    public static int getVchGrp(long lTransTypeId) throws Exception
	{
          int intVchGrp = 0;  //2:���� 3���տ� 4��ת��

          if (lTransTypeId == 46) { intVchGrp = 3; }			//�ֽ��տ�
          else if (lTransTypeId == 4) { intVchGrp = 2; }		//�ֽ𸶿�
          else if (lTransTypeId == 300) { intVchGrp = 2; }  	//���ñ�����Ӧƾ֤����--�ֽ𸶿�
          else if (lTransTypeId == 1) { intVchGrp = 3; }		//�����տ�
          else if (lTransTypeId == 2) { intVchGrp = 2; }		//���и���
          else if (lTransTypeId == 6) { intVchGrp = 4; }		//�ڲ�ת��
          else { intVchGrp = 4; }
          
    	return intVchGrp;
    	
    }
    
    /**
     * ȡƾ֤��,ֱ�ӵ��ô洢���� sp_02_getVchId
     * @param   @LedgerID int=3,
				@Annual int=2005,
				@CurrCode varchar(10)='R',
				@VchDate datetime='2005-02-02',
				@VchGrp int=4,	-- ƾ֤��
				@sVchId int=0 output,	-- ��ʾƾ֤��
				@VchId int=0 output,	-- ���ݱ�ƾ֤��
				@BranchCode Varchar(50)='000',
				@UserCode varchar(50)='' 
     * @return
     */
    public static int getVchNo(Connection conn,int iLedgerID, int iAnnual, String sCurrCode, Timestamp tVchDate, int iVchGrp, String sBranchCode, String sUserCode) throws Exception
	{
          int iVchNo = 0;
          PreparedStatement ps = null;        
          ResultSet rs = null;        
          try
          {
              StringBuffer sbSQL = new StringBuffer();
              sbSQL.setLength(0);
			  sbSQL.append(" declare @SVchID int, @VchNo int \n ");            
              sbSQL.append(" exec sp_02_getVchId " + iLedgerID + "," + iAnnual + ",'" + sCurrCode + "','" + DataFormat.formatDate(tVchDate,1) + "'," + iVchGrp + ", 0, 0, @SVchId output, @VchNo output, '" + sBranchCode + "','" + sUserCode + "' \n ");             
              sbSQL.append(" select @SVchID as SVchID, @VchNO as VchNo \n "); 
              //System.out.print(sbSQL.toString());
              ps = conn.prepareStatement(sbSQL.toString());
              rs = ps.executeQuery();
              if(rs != null && rs.next())
              {
              	iVchNo = rs.getInt("VchNo");
              	System.out.println("VchNo:"+iVchNo);
              }
              rs.close();
              rs = null;
              ps.close();
              ps = null;
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
                               
              }
              catch (Exception e)
              {
                  Log.print(e.toString());
              }
          }
          
    	return iVchNo;
    	
    }    
    
}
