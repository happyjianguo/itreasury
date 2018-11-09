/*
 * Created on 2004-2-11
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.genersoft;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Hashtable;

import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLGenerSoftPzInfo;
import com.iss.itreasury.glinterface.dataentity.GLGenerSoftPzflInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
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
 * @author lixr
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLGenerSoftBean extends GLExtendBaseBean
{
    public static void main(String[] args)
    {
    }

    /**
     * 检查导入的会计分录是否已经全部生成凭证
     * 全部生成  true 否则false 
     */
    public boolean checkPostVoucher(long lOfficeID,long lCurrencyID) throws Exception
	{
    	boolean flag = true;
    	

        Connection conn = null;        
        PreparedStatement ps = null;        
        ResultSet rs = null;        
        try
        {
            /////更新科目号
  
			
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  SELECT F_BZ \n ");            
            sbSQL.append("  FROM GQPZK \n ");       
            sbSQL.append("  where  F_BZ != '2' or F_pznm is null \n ");         
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            if(rs != null && rs.next())
            {
            	return false;
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
    	
    	return flag;
    }
	
    /*
     * 导出会计凭证 参数：Collection
     * 返回的凭证集合；
     * 
     */
    public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
    {
        try
        {
        	postGLVoucherToGenerSoft(collGlVoucher,lOfficeID,lCurrencyID,date);            
            
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    /*
     * 功能描述：从总账核算系统获取科目组合；
     *  
     * 
     */
    public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
    {
        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        PreparedStatement psBatch = null;
        ResultSet rs = null;
        long lresult = -1;
        long lID = 1;
        boolean bHaveData = false;
        try
        {
            /////更新科目号
            System.out.println("===================================准备从浪潮取科目！！！");
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
             System.out.println("===================================取连接完毕！！！");
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  SELECT ZWKMZD_KMBH, \n ");
            sbSQL.append("  	   ZWKMZD_KMMC,ZWKMZD_MX,ZWKMZD_SX \n ");            
            sbSQL.append("  FROM ZWKMZD \n ");            
            sbSQL.append("  ORDER BY ZWKMZD_KMBH \n ");
            System.out.print("*********kmSql--**"+sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                GLSubjectDefinitionInfo info = new GLSubjectDefinitionInfo();
                info.setOfficeID(lOfficeID);
                info.setCurrencyID(lCurrencyID);
                
                info.setSegmentCode2(rs.getString("ZWKMZD_KMBH"));                
                info.setSegmentName2(rs.getString("ZWKMZD_KMMC"));
                if(rs.getInt("ZWKMZD_MX")==1)
                {
                	info.setLeaf(true);
                }
                info.setSubjectType(getSubjectType(rs.getString("ZWKMZD_SX")));                
                info.setStatusID(Constant.RecordStatus.VALID);
                list.add(info);
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
    
    //  得到总账科目余额		(浪潮)
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		Log.print("进入到得到科目余额的方法里面！--------开始！!!");
		//原来的方法
       Connection conn = null;
       ArrayList list = new ArrayList();
       PreparedStatement ps = null;
       ResultSet rs = null;
       long lImportCurrencyID = -1;
       try
       {
       		lImportCurrencyID = lCurrencyID;
       		/////更新科目号余额
       		conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
       	
           StringBuffer strSQL = new StringBuffer();
           strSQL.setLength(0);
           
           String strDate=DataFormat.getDateString(tsDate);
           System.out.println("========得到格式化后的传进来的日期是：" +strDate);
           
           //得到当月第一天
           String strFirstDate=DataFormat.getDateString(DataFormat.getFirstDateOfMonth(tsDate));
           System.out.println("========得到格式化后的当月第一天的日期是：" +strFirstDate);
           
           //得到gl_setting配置的账套配置
           String sGlDbUserName="";	//账套名
    	   GlSettingInfo glSettingInfo=new GlSettingInfo();
           glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,0);//0：代表导入
		   sGlDbUserName=glSettingInfo.getGlDBUserName();
		   System.out.println("========得到数据库配置的账套号为：" +sGlDbUserName);
           
           //执行实际的SQL语句
           strSQL.append(" select Tb.ZWKMJE_KMBH GLcode , isnull(Tb.ZWKMJE_DQYE,0) + ");
           strSQL.append(" (select isnull(sum (Td.ZWPZFL_JE),0) ");
           strSQL.append(" from "+sGlDbUserName+".ZWPZK Tc ,"+sGlDbUserName+".ZWPZFL Td,"+sGlDbUserName+".ZWKMZD Ta ");
           strSQL.append(" where Td.ZWPZFL_KMBH=Tb.ZWKMJE_KmBH and Td.ZWPZFL_KMBH=Ta.ZWKMZD_KmBH and Td.ZWPZFL_YWRQ >='" + strFirstDate + "' and Td.ZWPZFL_YWRQ <='" + strDate + "' and Td.ZWPZFL_PZNM=Tc.ZWPZK_PZNM and Tc.ZWPZK_JZF=1 ) ");
           strSQL.append(" GLbalance ,Tt.ZWKMZD_YEFX GLfx ");
           strSQL.append(" from "+sGlDbUserName+".ZWKMJE Tb ,"+sGlDbUserName+".ZWKMZD Tt ");
           strSQL.append(" where Tb.ZWKMJE_KJND =year('" + strDate + "') and Tb.ZWKMJE_KJQJ =month('" + strDate + "')-1 ");
           strSQL.append(" and Tb.ZWKMJE_KMBH = Tt.ZWKMZD_KMBH ");
			
			ps = conn.prepareStatement(strSQL.toString());
			Log.print("***********SQL：查询浪潮科目余额：" + strSQL.toString());
			rs = ps.executeQuery();

			//GLBalanceInfo往科目信息里添加数据，此时有可能会错，因为前SQL是中油的，与3。0信息有点不匹配
			int i=1;
			while (rs != null && rs.next())			
            {
    		   Log.print("*************得到余额   "+rs.getDouble("GLbalance"));
			
               GLBalanceInfo info = new GLBalanceInfo();
               //根据账户类型得到账户类型ID从而得到借贷方向
               //long lDirectionID = SETTConstant.SubjectAttribute.getDirection(getSubjectType(rs.getString("account_type").trim().substring(0,1)));	
               long lDirectionID =rs.getLong("GLfx");	//得到临时的借贷方向
               double dBalance = rs.getDouble("GLbalance");	//余额
               info.setGLDate(tsDate);			//日期--
               info.setOfficeID(lOfficeID);		//办事处--
               info.setCurrencyID(lCurrencyID);	//币种--
               info.setGLSubjectCode(rs.getString("GLcode"));	//科目代号--
               info.setBalanceDirection(lDirectionID);	//借贷方向
               if (lDirectionID == SETTConstant.DebitOrCredit.DEBIT)
               {
               		Log.print("    当前科目的借贷方向:"+lDirectionID+"  在借方(DEBIT)添加余额为setDebitBalance:"+dBalance+"  科目类型:"+rs.getString("GLcode"));
                   info.setDebitBalance(dBalance);
               }
               else
               {
               	    //(值得考虑)中油财务的贷余额在读过来后，贷方的数据要＊(-1)来进入到我们内部的科目余额系统表中()
               	   Log.print("    当前科目的借贷方向:"+lDirectionID+"  在贷方(CREDIT)添加余额为setDebitBalance:"+dBalance+"  科目类型:"+rs.getString("GLcode"));
                   info.setCreditBalance(dBalance);
               }
               Log.print("添加余额的个数为:"+i++);
               Log.print("--从GL中得到科目号为:getGLSubjectCode："+info.getGLSubjectCode());
               
               list.add(info);
           }
			
		   Log.print("进入到得到科目余额的方法里面！--------结束!!！");
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
	
	
	 //得到科目金额  (浪潮)     
    public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID,long lModelID, Timestamp tsDate) throws Exception
    {
    	//和谢瑞确认，浪潮无得到科目的发生额的功能，此时只打印一条信息而己
		System.out.println("==========GenerSoft=============得到浪潮接口的科目发生额,开始!");
		System.out.println("==========GenerSoft=============中间实际作处理：没有！");
		System.out.println("==========GenerSoft=============得到浪潮接口的科目发生额,结束!");
		return null;
    }
	
	

    /*
     * postGLVoucherToGenerSoft()：判断对方返回信息是否完毕
     */
    private static long postGLVoucherToGenerSoft(Collection collVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
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
        	//得到总账设置信息
        	System.out.println("---得到总账设置信息---");
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
        	
            //conn = ConnectionSQLServer.getConnection(lOfficeID,lCurrencyID);
        	conn=ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
            
            conn.setAutoCommit(false);
            if (collVoucher != null)
            {
                Iterator it = collVoucher.iterator();
                int pzh = getMaxPzh(lOfficeID,lCurrencyID);
                while (it.hasNext())
                {
                	pzh++;
                	GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    voucher.setPostStatusID(Constant.GLPostStatus.DOING);	//正在生成
                    GLGenerSoftPzInfo pzInfo = new GLGenerSoftPzInfo();
                    pzInfo.setF_BZ("0");                  //0表示新增凭证
                    //pzInfo.setF_PZBH(voucher.getTransNo().substring(9,17));
                    pzInfo.setF_PZBH(DataFormat.formatInt(pzh,8));
                    pzInfo.setF_PZLXBH(glSettingInfo.getGlVoucherType());//暂时选择记账凭证 add by rxie  
                    //pzInfo.setF_PZNM()                    
                    pzInfo.setF_PZRQ(voucher.getPostDate());   //?????
                    pzInfo.setF_FJZS(0);
                    
                   pzInfo.setF_ZDR(glSettingInfo.getGLZDRForGener());	//2005.9.0为了针对浪潮9.0增加的字段F_ZDR
                   System.out.println("-------得到的浪潮的制单人是:"+pzInfo.getF_ZDR());
                    
                    for (int i = 0; voucher.getList() != null && i < voucher.getList().size(); i++)
                    {
                        GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(i);
                        GLGenerSoftPzflInfo pzflinfo = new GLGenerSoftPzflInfo();
                        
                        pzflinfo.setF_PZBH(DataFormat.formatInt(pzh,8));
                        pzflinfo.setF_FLBH(DataFormat.formatInt(i+1,4));
                        pzflinfo.setF_KMBH(entryinfo.getSubject());
                        //pzflinfo.setF_ZY(entryinfo.getTransNo());
                        pzflinfo.setF_ZY("外部引入");
                        pzflinfo.setF_JE(entryinfo.getAmount());
                        pzflinfo.setF_JZFX(String.valueOf(entryinfo.getDirectionID()));
                        pzflinfo.setF_YWRQ(entryinfo.getExecute());
                        if(entryinfo.getCurrencyID()!=Constant.CurrencyType.RMB)
                        {
                        	pzflinfo.setF_WB(entryinfo.getCurrencyID());
                        	pzflinfo.setF_WBBH(getCurrencyCode(entryinfo.getCurrencyID()));
                        	pzflinfo.setF_HL(0);   //暂时先不处理
                        }						
                       
                        Log.print("**************before insert **************");
                        
                        String ywrq = DataFormat.getDateString(pzflinfo.getF_YWRQ()).substring(0,4)+DataFormat.getDateString(pzflinfo.getF_YWRQ()).substring(5,7)+DataFormat.getDateString(pzflinfo.getF_YWRQ()).substring(8,10);
                        sbRecord.setLength(0);
                        sbRecord.append(" insert into GQPZFL( \n");                        
                        sbRecord.append(" F_PZBH,F_FLBH,F_KMBH,F_ZY, \n ");
                        sbRecord.append(" F_JE,F_SL,F_DJ,F_WB,F_HL,F_WBBH, \n");
                        sbRecord.append(" F_JZFX,F_JSFS,F_JSH,F_YWRQ,F_TZXM,F_PZFL \n");                        
                        sbRecord.append(" ) \n");
                        sbRecord.append(" values  \n");
                        sbRecord.append(" ( \n");
                        sbRecord.append(" '" + pzflinfo.getF_PZBH() + "','" + pzflinfo.getF_FLBH() + "','" + pzflinfo.getF_KMBH() + "','" + pzflinfo.getF_ZY() + "', \n");
                        sbRecord.append(" " + pzflinfo.getF_JE() + ", " + pzflinfo.getF_SL() + ", " + pzflinfo.getF_DJ() + "," + pzflinfo.getF_WB() + "," + pzflinfo.getF_HL() + ",'" + pzflinfo.getF_WBBH() + "', \n");                        
                        sbRecord.append(" '" + pzflinfo.getF_JZFX() + "','" + pzflinfo.getF_JSFS() + "', \n");
                        sbRecord.append(" '" + pzflinfo.getF_JSH() + "','" + ywrq + "','" + pzflinfo.getF_TZXMBH() + "','" + pzflinfo.getF_PZFL() + "')");
                        Log.print(sbRecord.toString());
                        ps = conn.prepareStatement(sbRecord.toString());
                        
                        ps.executeUpdate();
                        Log.print("**************after insert **************");
                        ps.close();
                        ps = null;
                    }
                    
                    
                    Log.print("**************befin insert pz **************");
                    sbRecord.setLength(0);
                    
                    String pzrq =  DataFormat.getDateString(pzInfo.getF_PZRQ()).substring(0,4)+DataFormat.getDateString(pzInfo.getF_PZRQ()).substring(5,7)+DataFormat.getDateString(pzInfo.getF_PZRQ()).substring(8,10);
                    //String pzrq = getAccountDate(lOfficeID,lCurrencyID);
                    sbRecord.append(" insert into GQPZK( \n");                        
                    sbRecord.append(" F_PZRQ,F_PZBH,F_LXBH,F_FJZS,F_BZ, \n ");
                    sbRecord.append(" F_PZNM,F_ZDR,F_YSBH \n");                                            
                    sbRecord.append(" ) \n");
                    sbRecord.append(" values  \n");
                    sbRecord.append(" ( \n");
                    sbRecord.append(" " + pzrq + ",'" + pzInfo.getF_PZBH() + "', \n");                                           
                    sbRecord.append(" '" + pzInfo.getF_PZLXBH() + "'," + pzInfo.getF_FJZS() + ", \n");
                    sbRecord.append(" '" + pzInfo.getF_BZ() + "','" + pzInfo.getF_PZNM() + "','" + pzInfo.getF_ZDR() + "','"+pzInfo.getF_YSBH() + "')");
                    Log.print(sbRecord.toString());
                    ps = conn.prepareStatement(sbRecord.toString());
                    
                    ps.executeUpdate();
                    Log.print("**************after insert pz **************");
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
     * 得到中间表（GQPZK）中最大凭证号
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     * @throws Exception
     */
    public static int getMaxPzh(long lOfficeID,long lCurrencyID) throws Exception
	{
    	int iMaxPzh = 0;
    	String strMaxPzh = "";
    	Connection conn = null;        
        PreparedStatement ps = null;        
        ResultSet rs = null;        
        try
        {
            //
        	  conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  SELECT max(f_pzbh) pzh \n ");            
            sbSQL.append("  FROM GQPZK \n ");                
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            if(rs != null && rs.next())
            {
            	strMaxPzh = rs.getString("pzh");
            	if(strMaxPzh!=null && !strMaxPzh.equals(""))
            	{
            		iMaxPzh  = Integer.valueOf(strMaxPzh).intValue()+1;
            	}
            	else
            	{
            		iMaxPzh =1;
            	}
            }
            else
            {
            	iMaxPzh =1;
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
        
  	return iMaxPzh;
  	
  }
    
    /**
     * 得到浪潮财务软件目前的会计日期
     * @param lOfficeID,lCurrencyID
     * @return
     */
    public static String getAccountDate(long lOfficeID,long lCurrencyID) throws Exception
	{
          String strAccountDate = "";
    	  Connection conn = null;        
          PreparedStatement ps = null;        
          ResultSet rs = null;        
          try
          {
              //
          	  conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
              StringBuffer sbSQL = new StringBuffer();
              sbSQL.setLength(0);
              sbSQL.append("  SELECT F_VAL \n ");            
              sbSQL.append("  FROM LSCONF \n ");       
              sbSQL.append("  where  F_VKEY = 'ZW_KJRQ' \n ");         
              Log.print(sbSQL.toString());
              ps = conn.prepareStatement(sbSQL.toString());
              rs = ps.executeQuery();
              if(rs != null && rs.next())
              {
              	strAccountDate = rs.getString("F_VAL");
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
          
    	return strAccountDate;
    	
    }
    /**
     * 得到与浪潮财务系统相对应的币种编码
     * 
     * @param lCurrencyID
     * @return
     */
    public static String getCurrencyCode(long lCurrencyID)
    {
        String strCurrencyCode = "CNY";
        switch ((int) lCurrencyID)
        {
            case (int) Constant.CurrencyType.USD:
                strCurrencyCode = "USD";
                break;
            default:
                strCurrencyCode = "CNY";
                break;
        }
        return strCurrencyCode;
    }

  

    /**
     * 得到与浪潮财务系统相对应的科目类型
     * 
     * @param lOfficeID
     * @return
     */
    public static long getSubjectType(String strType)
    {
        long lTypeID = -1;
        if (strType != null)
        {
            if (strType.equals("01"))
            {
                lTypeID = SETTConstant.SubjectAttribute.ASSET;
            }
            if (strType.equals("02"))
            {
                lTypeID = SETTConstant.SubjectAttribute.DEBT;
            }
            if (strType.equals("03"))
            {
                lTypeID = SETTConstant.SubjectAttribute.RIGHT;
            }
            if (strType.equals("05"))
            {
                lTypeID = SETTConstant.SubjectAttribute.INCOME;
            }
            /*if (strType.equals("05"))
            {
                lTypeID = SETTConstant.SubjectAttribute.PAYOUT;
            }*/
        }
        return lTypeID;
    }

   
  
}