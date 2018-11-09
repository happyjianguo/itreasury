/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.anyi;

import java.io.Serializable;
import java.rmi.RemoteException;
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
import com.iss.itreasury.glinterface.dataentity.GLAnyiPzInfo;
import com.iss.itreasury.glinterface.dataentity.GLAnyiPzflInfo;
import com.iss.itreasury.glinterface.dataentity.GLAnyiPznrInfo;
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
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLAnyiR9Bean extends GLExtendBaseBean
{

    public static void main(String[] args)
    {
    	GLAnyiR9Bean bean = new GLAnyiR9Bean();
    	
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
     * 
     */
    public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
    {
        try
        {
        	postGLVoucherToAnyiR9(collGlVoucher,lOfficeID,lCurrencyID,date);                        
  
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    /*
     * �����������Ӱ���R9��ȡ��Ŀ��
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
             	select gsdm,kmdm,kmmc,kmxz,kmmx,case when yefx = 'J' then 1 else 2 end as yefx,syzt from gl_kmxx
             	order by kmdm
            */
            System.out.println("===================================׼���Ӱ���ȡ��Ŀ������");
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
            System.out.println("===================================ȡ������ϣ�����");
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  select gsdm,ltrim(rtrim(kmdm)) kmdm,kmmc,kmxz,kmmx,case when yefx = 'J' then 1 else 2 end as yefx,syzt  \n ");            
            sbSQL.append("  from gl_kmxx \n ");            
            //sbSQL.append("  where syzt = 1 \n ");
            sbSQL.append("  order by kmdm \n ");
            System.out.print("*********��Ŀ��ϢSql:**********\n"+sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                GLSubjectDefinitionInfo info = new GLSubjectDefinitionInfo();
                info.setOfficeID(lOfficeID);
                info.setCurrencyID(lCurrencyID);
                
                info.setSegmentCode2(rs.getString("kmdm"));                
                info.setSegmentName2(rs.getString("kmmc"));
                info.setSubjectType(rs.getLong("kmxz"));  
                if(rs.getInt("kmmx")==1)
                {
                	info.setLeaf(true);
                }
                else 
                {
                	info.setLeaf(false);
                }
                info.setBalanceDirection(rs.getLong("yefx"));
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
     * �õ��������˵��տ�Ŀ���
     * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectBalance(long, long, java.sql.Timestamp)
     */
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		//for(int i=0;i<5;i++)
			System.out.println("==========Anyi=============�õ����׽ӿڵĿ�Ŀ���");
		
		
		Log.print("���뵽�õ���Ŀ���ķ������棡--------��ʼ��!!");
   	 //ԭ���ķ���
       Connection conn = null;
       ArrayList list = new ArrayList();
       PreparedStatement ps = null;
       ResultSet rs = null;
       long lImportCurrencyID = -1;
       try
       {
       	lImportCurrencyID = lCurrencyID;
           /////���¿�Ŀ���
       	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
       	
           StringBuffer strSQL = new StringBuffer();
           strSQL.setLength(0);
           
           //�õ�����ϵͳ��ָ�����ڵ������Ϣ��
           //��Ϊÿ���������µ����룬����Ϊ�㣬��Ŀǰ��ȫ���ۼӣ���ֻȡ�����£��ڴ�ƴ��
           /*
            * 
				select ltrim(rtrim(a.kmdm)) kmdm, case when a.yefx = 'J' then 1 else 2 end as yefx,
						case when a.yefx = 'J' then (isnull(b.kmncj + b.kmjf1 + b.kmjf2 + b.kmjf3 + b.kmjf4 + b.kmjf5 + b.kmjf6 + b.kmjf7 + b.kmjf8 + b.kmjf9 + b.kmjf10 + b.kmjf11 + b.kmjf12 + b.kmjf13 - (b.kmncd + b.kmdf1 + b.kmdf2 + b.kmdf3 + b.kmdf4 + b.kmdf5 + b.kmdf6 + b.kmdf7 + b.kmdf8 + b.kmdf9 + b.kmdf10 + b.kmdf11 + b.kmdf12 + b.kmdf13),0) + sum(isnull(c.je,0) * case when c.jdbz = 1 then 1 else -1 end)) else 0 end as jfye,
						case when a.yefx = 'D' then (isnull(b.kmncd + b.kmdf1 + b.kmdf2 + b.kmdf3 + b.kmdf4 + b.kmdf5 + b.kmdf6 + b.kmdf7 + b.kmdf8 + b.kmdf9 + b.kmdf10 + b.kmdf11 + b.kmdf12 + b.kmdf13 - (b.kmncj + b.kmjf1 + b.kmjf2 + b.kmjf3 + b.kmjf4 + b.kmjf5 + b.kmjf6 + b.kmjf7 + b.kmjf8 + b.kmjf9 + b.kmjf10 + b.kmjf11 + b.kmjf12 + b.kmjf13),0) + sum(isnull(c.je,0) * case when c.jdbz = 2 then 1 else -1 end)) else 0 end as dfye  
				from gl_kmxx a left outer join gl_kmye b 
					on a.kmdm = b.kmdm and b.kjnd = '2004'
					left outer join (
									select q.kmdm, case when q.jdbz = '��' then 1 else 2 end jdbz, q.je, p.pzrq, p.srrq as zdrq
									from gl_pzml p left outer join gl_pznr q on p.gsdm = q.gsdm and p.kjqj = q.kjqj and p.pzly = q.pzly and p.pzh = q.pzh and isnull(p.shID,0) > 0 and substring(p.pzrq,1,4) = '2004' and substring(p.pzrq,5,2) = '11' and substring(p.pzrq,7,2) <= '17'
													left outer join gl_pzflmx r on p.gsdm = r.gsdm and p.kjqj = r.kjqj and p.pzly = r.pzly and p.pzh = r.pzh and q.flh = r.flh 
					) c on a.kmdm = c.kmdm 
				group by a.kmdm, a.yefx, b.kmncj, b.kmjf1, b.kmjf2, b.kmjf3, b.kmjf4, b.kmjf5, b.kmjf6, b.kmjf7, b.kmjf8, b.kmjf9, b.kmjf10, b.kmjf11, b.kmjf12, b.kmjf13, b.kmncd, b.kmdf1, b.kmdf2, b.kmdf3, b.kmdf4, b.kmdf5, b.kmdf6, b.kmdf7, b.kmdf8, b.kmdf9, b.kmdf10, b.kmdf11, b.kmdf12, b.kmdf13
				order by a.kmdm
			*	
            */
           
           String strDate=DataFormat.getDateString(tsDate);
           System.out.println(" �õ���ʽ����Ĵ������������ǣ�" +strDate);
           
           
           strSQL.append(" select ltrim(rtrim(a.kmdm)) kmdm, case when a.yefx = 'J' then 1 else 2 end as yefx, \n ");
           strSQL.append(" case when a.yefx = 'J' then (isnull(b.kmncj + b.kmjf1 + b.kmjf2 + b.kmjf3 + b.kmjf4 + b.kmjf5 + b.kmjf6 + b.kmjf7 + b.kmjf8 + b.kmjf9 + b.kmjf10 + b.kmjf11 + b.kmjf12 + b.kmjf13 - (b.kmncd + b.kmdf1 + b.kmdf2 + b.kmdf3 + b.kmdf4 + b.kmdf5 + b.kmdf6 + b.kmdf7 + b.kmdf8 + b.kmdf9 + b.kmdf10 + b.kmdf11 + b.kmdf12 + b.kmdf13),0) + sum(isnull(c.je,0) * case when c.jdbz = 1 then 1 else -1 end)) else 0 end as jfye, \n");
           strSQL.append(" case when a.yefx = 'D' then (isnull(b.kmncd + b.kmdf1 + b.kmdf2 + b.kmdf3 + b.kmdf4 + b.kmdf5 + b.kmdf6 + b.kmdf7 + b.kmdf8 + b.kmdf9 + b.kmdf10 + b.kmdf11 + b.kmdf12 + b.kmdf13 - (b.kmncj + b.kmjf1 + b.kmjf2 + b.kmjf3 + b.kmjf4 + b.kmjf5 + b.kmjf6 + b.kmjf7 + b.kmjf8 + b.kmjf9 + b.kmjf10 + b.kmjf11 + b.kmjf12 + b.kmjf13),0) + sum(isnull(c.je,0) * case when c.jdbz = 2 then 1 else -1 end)) else 0 end as dfye \n");
           strSQL.append(" from gl_kmxx a left outer join gl_kmye b \n");
           strSQL.append(" on a.kmdm = b.kmdm and b.kjnd = year('" + strDate + "') \n");
           strSQL.append(" left outer join ( \n");
           strSQL.append(" select q.kmdm, case when q.jdbz = '��' then 1 else 2 end jdbz, q.je, p.pzrq, p.srrq as zdrq \n");       
           strSQL.append(" from gl_pzml p left outer join gl_pznr q on p.gsdm = q.gsdm and p.kjqj = q.kjqj and p.pzly = q.pzly and p.pzh = q.pzh and isnull(p.shID,0) > 0 and substring(p.pzrq,1,4) = year('" + strDate + "') and substring(p.pzrq,5,2) = Month('" + strDate + "') and substring(p.pzrq,7,2) <= day('" + strDate + "') \n");
           strSQL.append(" left outer join gl_pzflmx r on p.gsdm = r.gsdm and p.kjqj = r.kjqj and p.pzly = r.pzly and p.pzh = r.pzh and q.flh = r.flh \n");
           strSQL.append(" ) c on a.kmdm = c.kmdm \n");
           strSQL.append(" group by a.kmdm, a.yefx, b.kmncj, b.kmjf1, b.kmjf2, b.kmjf3, b.kmjf4, b.kmjf5, b.kmjf6, b.kmjf7, b.kmjf8, b.kmjf9, b.kmjf10, b.kmjf11, b.kmjf12, b.kmjf13, b.kmncd, b.kmdf1, b.kmdf2, b.kmdf3, b.kmdf4, b.kmdf5, b.kmdf6, b.kmdf7, b.kmdf8, b.kmdf9, b.kmdf10, b.kmdf11, b.kmdf12, b.kmdf13 \n");
           strSQL.append(" order by a.kmdm \n");
           
			
			ps = conn.prepareStatement(strSQL.toString());
			Log.print("***********��ѯ���׿�Ŀ���sql��" + strSQL.toString());
			rs = ps.executeQuery();
			
			
			int i=1;
			//GLBalanceInfo����Ŀ��Ϣ��������ݣ���ʱ�п��ܻ����ΪǰSQL�����͵ģ���3��0��Ϣ�е㲻ƥ��
			while (rs != null && rs.next())			
           {
				
                Log.print("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("kmdm"));
				Log.print("  �õ����  :  " + (rs.getDouble("jfye") + rs.getDouble("dfye")));
							
               GLBalanceInfo info = new GLBalanceInfo();
               info.setGLDate(tsDate);			//����
               info.setOfficeID(lOfficeID);		//���´�
               info.setCurrencyID(lCurrencyID);	//����
               info.setGLSubjectCode(rs.getString("kmdm"));	//��Ŀ��
               info.setBalanceDirection(rs.getLong("yefx"));	//����
               info.setDebitBalance(rs.getDouble("jfye"));	//�跽���
               info.setCreditBalance(rs.getDouble("dfye"));	//�������
               
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
	 * �õ���Ŀ(����)���������  (����)
	 * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectAmount(long, long, long, java.sql.Timestamp)
	 */     
    public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsDate) throws Exception
    {
    
    	//for(int i=0;i<5;i++)
			System.out.println("==========AnyiR9=============�õ����׽ӿڵĿ�Ŀ������");

        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        long lImportCurrencyID = -1;
        try
        {
        	lImportCurrencyID = lCurrencyID;
            
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
        	
            StringBuffer strSQL = new StringBuffer();
            strSQL.setLength(0);
            
            //�õ�����ϵͳ��ָ�����ڵĿ�Ŀ����������
            /*
				select p.pzrq, ltrim(rtrim(q.kmdm)) kmdm,
					sum(case when q.jdbz = '��' then q.je else 0 end) as jfje, sum(case when q.jdbz = '��' then q.je else 0 end) as dfje, 
					sum(case when q.jdbz = '��' then 1 else 0 end) as jfbs, sum(case when q.jdbz = '��' then 1 else 0 end) as dfbs
				from gl_pzml p left outer join gl_pznr q on p.gsdm = q.gsdm and p.kjqj = q.kjqj and p.pzly = q.pzly and p.pzh = q.pzh 
								left outer join gl_pzflmx r on p.gsdm = r.gsdm and p.kjqj = r.kjqj and p.pzly = r.pzly and p.pzh = r.pzh and q.flh = r.flh 
				where isnull(p.shID,0) > 0 
				and substring(p.pzrq,1,4) = '2004' and substring(p.pzrq,5,2) = '11' and substring(p.pzrq,7,2) = '17'
				group by p.pzrq, q.kmdm
            */
            
            String strDate=DataFormat.getDateString(tsDate);
            System.out.println(" �õ���ʽ����Ĵ������������ǣ�" +strDate);
            
            
            strSQL.append(" select p.pzrq, ltrim(rtrim(q.kmdm)) kmdm, \n");
            strSQL.append(" sum(case when q.jdbz = '��' then q.je else 0 end) as jfje, sum(case when q.jdbz = '��' then q.je else 0 end) as dfje, \n");
            strSQL.append(" sum(case when q.jdbz = '��' then 1 else 0 end) as jfbs, sum(case when q.jdbz = '��' then 1 else 0 end) as dfbs \n");
            strSQL.append(" from gl_pzml p left outer join gl_pznr q on p.gsdm = q.gsdm and p.kjqj = q.kjqj and p.pzly = q.pzly and p.pzh = q.pzh \n");
            strSQL.append(" left outer join gl_pzflmx r on p.gsdm = r.gsdm and p.kjqj = r.kjqj and p.pzly = r.pzly and p.pzh = r.pzh and q.flh = r.flh \n");
            strSQL.append(" where isnull(p.shID,0) > 0 \n");
            strSQL.append(" and substring(p.pzrq,1,4) = year('" +strDate + "') and substring(p.pzrq,5,2) = month('" +strDate + "') and substring(p.pzrq,7,2) = day('" +strDate + "') \n");
            strSQL.append(" group by p.pzrq, q.kmdm \n");
           
            System.out.println(strSQL); 			
 			ps = conn.prepareStatement(strSQL.toString());
 			Log.print("***********��ѯ���׿�Ŀ������sql��" + strSQL.toString());
 			rs = ps.executeQuery();
 			
 			
 			int i=1;
 			while (rs != null && rs.next())			
            {
 				
 				//System.out.println("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("kmdm"));
 				//System.out.println("  �õ�������   " + strDate + "   �跽��" + rs.getDouble("jfje") + "  ������" + rs.getDouble("dfje"));
 				Log.print("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("kmdm"));
 				Log.print("  �õ�������   " + strDate + "   �跽��" + rs.getDouble("jfje") + "  ������" + rs.getDouble("dfje"));
 				Log.print("  �õ���������   " + strDate + "   �跽��" + rs.getDouble("jfbs") + "  ������" + rs.getDouble("dfbs"));
 							
                GLBalanceInfo info = new GLBalanceInfo();
                info.setGLDate(tsDate);				//����
                info.setOfficeID(lOfficeID);		//���´�
                info.setCurrencyID(lCurrencyID);	//����
                info.setGLSubjectCode(rs.getString("kmdm"));			//��Ŀ��
                info.setDebitAmount(rs.getDouble("jfje"));		//�跽������
                info.setCreditAmount(rs.getDouble("dfje"));	//����������
                info.setDebitNumber(rs.getLong("jfbs"));		//�跽����
                info.setCreditNumber(rs.getLong("dfbs"));		//��������

                System.out.println("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("kmdm"));
                System.out.println("  �õ�������   " + strDate + "   �跽��" + rs.getDouble("jfje") + "  ������" + rs.getDouble("dfje"));
                System.out.println("  �õ���������   " + strDate + "   �跽��" + rs.getDouble("jfbs") + "  ������" + rs.getDouble("dfbs"));
                
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
     * postGLVoucherToAnyiR9()���ж϶Է�������Ϣ�Ƿ����
     */
    private static long postGLVoucherToAnyiR9(Collection collVoucher, long lOfficeID, long lCurrencyID, Timestamp date) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        String strTransNo = "";
        String strBatchName = null;
        Hashtable hash = new Hashtable();
        long lje_line_num = 1;
        try
        {
        	//�õ�����������Ϣ
        	System.out.println("---�õ�����������Ϣ---");
        	GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1);

            //conn = ConnectionSQLServer.getConnection(lOfficeID,lCurrencyID);
        	conn=ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
            
            conn.setAutoCommit(false);
            if (collVoucher != null)
            {
            	System.out.println("ȡƾ֤��Ϣ����������������������");
                Iterator it = collVoucher.iterator();
                //ƾ֤�ţ���������
                String pzlx = "����";
                int pzh = getMaxPzh(lOfficeID,lCurrencyID,pzlx);
                System.out.println("pzh:"+pzh);
                
                //��¼����
                int EntryCount = 0;
                //������ϼ�
                double DebitTotal = 0;  
				double CreditTotal = 0;
                
                while (it.hasNext())
                {
                	pzh++;
                	EntryCount = 0;
                	DebitTotal = 0;
                	CreditTotal = 0;

                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    voucher.setPostStatusID(Constant.GLPostStatus.DOING);	//��������

                    GLAnyiPzInfo pzInfo = new GLAnyiPzInfo();
                    pzInfo.setFlag("0");                  //0��ʾ����ƾ֤
                    pzInfo.setKjqj(DataFormat.formatDate(voucher.getPostDate(),5).substring(0,6));
                    pzInfo.setPzh(pzlx + formatInt(pzh,6));
                    pzInfo.setPzrq(DataFormat.formatDate(voucher.getPostDate(),5));  //voucher.getEntryInfo(0).getInterestStart()
                    pzInfo.setSrID(getCzyID(lOfficeID,lCurrencyID,glSettingInfo.getGlUserName()));
                    pzInfo.setSr(glSettingInfo.getGlUserName());
                    pzInfo.setSrrq(DataFormat.formatDate(voucher.getPostDate(),5));
                    pzInfo.setShrq("");
                    pzInfo.setJzrq("");
                    pzInfo.setZt(1);
                    pzInfo.setPzzy(voucher.getEntryInfo(0).getAbstract());
                    pzInfo.setPzje(0);
                    
                   
                    for (EntryCount = 0; voucher.getList() != null && EntryCount < voucher.getList().size(); EntryCount++)
                    {
                        
                        GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(EntryCount);
                        GLAnyiPznrInfo pznrinfo = new GLAnyiPznrInfo();
                        
                        //System.out.println("����ƾ֤������Ϣ");
                        pznrinfo.setKjqj(DataFormat.formatDate(entryinfo.getExecute(),5).substring(0,6));
                        pznrinfo.setPzh(pzlx + formatInt(pzh,6));
                        pznrinfo.setFlh(EntryCount);
                        pznrinfo.setZy(entryinfo.getAbstract());
                        pznrinfo.setKmdm(entryinfo.getSubject());
                       	pznrinfo.setWbdm(DataFormat.formatInt(entryinfo.getCurrencyID(),2));
                       	pznrinfo.setHl(1);
                       	if(entryinfo.getDirectionID()==1)
                        {
                        	pznrinfo.setJdbz("��");
                        }
                        else if (entryinfo.getDirectionID()==2)
                        {
                        	pznrinfo.setJdbz("��");
                        }
                        pznrinfo.setJe(entryinfo.getAmount());
                       
                        Log.print("************** before insert gl_pznr **************");

                        //��Ĭ��ֵ��gsdm='',pzly='',wbdm='',hl=1,wbje=0                        
                        System.out.println("before insert gl_pznr");
                        sbRecord.setLength(0);
                        sbRecord.append(" Insert into gl_pznr( \n");                        
                        sbRecord.append(" gsdm,kjqj,pzly,pzh,flh,zy,kmdm,wbdm,hl,jdbz,wbje,je \n ");
                        sbRecord.append(" ) \n");
                        sbRecord.append(" values  \n");
                        sbRecord.append(" ( \n");
                        sbRecord.append(" '','" + pznrinfo.getKjqj() + "','','" + pznrinfo.getPzh() + "'," + pznrinfo.getFlh() + ",'" + DataFormat.formatNullString(pznrinfo.getZy()) + "',");
                        sbRecord.append(" '" + pznrinfo.getKmdm() + "','',1,'" + pznrinfo.getJdbz() + "',0," + pznrinfo.getJe() + ") \n");                        
                        Log.print(sbRecord.toString());
                        System.out.println(sbRecord.toString());
                        ps = conn.prepareStatement(sbRecord.toString());
                        
                        ps.executeUpdate();
                        Log.print("************** after insert **************");
                        System.out.println("after insert gl_pznr");
                        
                        if (pznrinfo.getJdbz() == "��")
						{
                        	DebitTotal = DebitTotal + pznrinfo.getJe();
						}
                        else 
                        {
                        	CreditTotal = CreditTotal + pznrinfo.getJe();
                        }
                        
                        ps.close();
                        ps = null;
                    }
                    
                    
                    Log.print("************** befin insert gl_pzml **************");
                    sbRecord.setLength(0);
                    
                    //��Ĭ��ֵ��gsdm='',pzly='',wbdm='',hl=1,wbje=0,pzhzkmdy=0,pzhzbz=0                        
                    System.out.println("before insert gl_pzml");
                    
                    sbRecord.append(" insert into gl_pzml( \n");                        
                    sbRecord.append(" gsdm,kjqj,pzly,pzh,pzrq,fjzs,srID,sr,srrq,shrq,jzrq,zt,pzzy,pzje,pzhzkmdy,pzhzbz \n ");
                    sbRecord.append(" ) \n");
                    sbRecord.append(" values  \n");
                    sbRecord.append(" ( \n");
                    sbRecord.append(" '','" + pzInfo.getKjqj() + "','','" + pzInfo.getPzh() + "','" + pzInfo.getPzrq() + "',0," + pzInfo.getSrID() + ",'" + pzInfo.getSr() + "', \n");                                           
                    sbRecord.append(" '" + pzInfo.getSrrq() + "','" + pzInfo.getShrq() + "','" + pzInfo.getJzrq() + "'," + pzInfo.getZt() + ",'" + DataFormat.formatNullString(pzInfo.getPzzy()) + "'," + DataFormat.formatDouble(DebitTotal) + ",0,0) \n");
                    Log.print(sbRecord.toString());
                    System.out.println(sbRecord.toString());
                    ps = conn.prepareStatement(sbRecord.toString());
                    
                    //System.out.println("SQL:"+sbRecord.toString());
                    ps.executeUpdate();
                    System.out.println("after insert gl_pzml");
                    Log.print("************** after insert gl_pzml **************");

                    //������ɣ����ô���״̬
                    voucher.setPostStatusID(Constant.GLPostStatus.SUCCESS);	//���
                    System.out.println("setPostStatusID Over");

                    ps.close();
                    ps = null;
                    
                    
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
            if (ps != null)
            {
                ps.close();
                ps = null;
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
                throw new Exception("remote exception : " + e.toString());
            }
        }
        return lDealStatusID;
    }

 
    
    /**
     * ���ݲ����õ�ƾ֤���ܱ����ƾ֤��--��ƾ֤�ְ�������
     * @param lOfficeID
     * @param lCurrencyID
     * @param lGroupId
     * @return 
     * @throws Exception
     */
    public static int getMaxPzh(long lOfficeID, long lCurrencyID, String lGroupID) throws Exception
	{
    	int iMaxPzNo = 0;
    	Connection conn = null;        
        PreparedStatement ps = null;        
        ResultSet rs = null;        
        
       
        try
        {
            //
        	  conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  select isnull(max(convert(int,substring(pzh,len(pzh)-5,6))),0) as maxpzh from gl_pzml \n ");            
            sbSQL.append("  where pzh like '" + lGroupID + "%' \n ");                
            //System.out.println(sbSQL.toString());
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();

            if(rs != null && rs.next())
            {
            	iMaxPzNo = rs.getInt("maxpzh");
            }
            
            if(rs!=null)
            {
            	rs.close();
            	rs = null;
            }
            if(ps!=null)
            {
            ps.close();
            ps = null;
            }
            if(conn!=null)
            {
            conn.close();
            conn = null;
            }
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
        
  	return iMaxPzNo;
  	
  }
    
    
    /**
     * �õ����׶�Ӧ�Ĳ���ԱID
     * @param lOfficeID,lCurrencyID
     * @return
     */
    public static int getCzyID(long lOfficeID, long lCurrencyID, String sCzyName) throws Exception
	{
          int iCzyID = 0;
    	  Connection conn = null;        
          PreparedStatement ps = null;        
          ResultSet rs = null;        

          try
          {
              //
          	  conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
              StringBuffer sbSQL = new StringBuffer();
              sbSQL.setLength(0);
              sbSQL.append(" select ID from gl_czy where rtrim(ltrim(name)) = '" + sCzyName + "' \n ");            
              //System.out.println(sbSQL.toString());
              Log.print(sbSQL.toString());
              ps = conn.prepareStatement(sbSQL.toString());
              rs = ps.executeQuery();
              if(rs != null && rs.next())
              {
              	iCzyID = rs.getInt("ID");
              }
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
          
    	return iCzyID;
    	
    }
     
	/**
	 * ��ʽ�����֣����磺��5ת��Ϊ4λ�ַ�����õ�"   5"
	 * 
	 * @param dValue
	 *            ����ʽ������ֵ
	 * @param nWidth
	 *            ��Ҫת����λ��
	 * @return
	 */
	public static String formatInt ( long lValue , int nWidth )
	{
		String strReturn = "" + lValue ;
		int initLength = strReturn.length ( ) ;
		for (int i = nWidth; i > initLength; i--)
		{
			strReturn = " " + strReturn ;
		}
		return strReturn ;
	}
            
}
