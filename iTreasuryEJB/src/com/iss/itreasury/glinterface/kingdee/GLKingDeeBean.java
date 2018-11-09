/*
 * Created on 2005-11-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.kingdee;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLKingDeeExtAcctInfo;
import com.iss.itreasury.glinterface.dataentity.GLKingDeePzflInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.kingdee.dao.GLVoucherDao;
import com.iss.itreasury.glinterface.u850.ConnectionSQLServer;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author liuqing
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLKingDeeBean extends GLExtendBaseBean
{

    public static void main(String[] args)
    {
    	//GLKingDeeBean bean = new GLKingDeeBean();
    	
    	try {
    		//System.out.println(DataFormat.getDateString(DataFormat.getDateTime("2005-01-31")));
    		
    		//bean.getGLSubject(1,1);
    		//bean.getGLSubjectBalance(1,1,DataFormat.getDateTime("2005-01-31"));
    		//bean.getGLSubjectAmount(1,1,1,DataFormat.getDateTime("2005-01-31"));
    		
    		//System.out.println("科目内码：" + bean.getAccountId(1,1,"191.02"));
		} catch (Exception e) {
	
			e.printStackTrace();
		}
    }

    /*
     * 导入会计凭证 参数：Collection
     * 返回的凭证集合；
     * 
     */
    public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
    {
        try
        {
        	//postClinetInfoToKingDee(lOfficeID,lCurrencyID);
        	postGLVoucherToKingDee(collGlVoucher,lOfficeID,lCurrencyID,date);            
            
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    /*
     * 功能描述：从金蝶系统获取科目；
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
            System.out.println("===================================准备从金蝶取科目！！！");
        	//conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
            conn = get_jdbc_connection(lOfficeID,lCurrencyID,0);
            System.out.println("===================================取连接完毕！！！");
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            
            /**
             * Modified by shuangniu 2011-01-18 查询kingdee科目表（视图）
             * Modified by xiangzhou 河北建投 2012-11-22
             * 
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
            
            sbSQL.append("  select FAccountCode,FAccountName,FaccountTypeCode,FAccountTypeName,Fisleaf,Fjdfx,Fiscustomer \n ");            
            sbSQL.append("  from  v_accountview \n ");            
            sbSQL.append("  order by FAccountCode \n ");
            
            System.out.print("*********科目信息Sql:**********\n"+sbSQL.toString());
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
            System.out.println("===================================从金蝶取科目结束,个数为："+list.size());
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
     * 得到金蝶总账当日科目余额
     * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectBalance(long, long, java.sql.Timestamp)
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
	       System.out.println("===================================准备从金蝶取科目余额！！！");
	       //conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
	       conn = get_jdbc_connection(lOfficeID,lCurrencyID,0);
	       System.out.println("===================================取连接完毕！！！");
       	
           StringBuffer strSQL = new StringBuffer();
           strSQL.setLength(0);
           
           String strDate=DataFormat.getDateString(tsDate);
           System.out.println(" 得到格式化后的传进来的日期是：" +strDate);
           
           /**
			 * 修改记录：
			 * Modified by shuangniu 新奥导科目余额
			 * Modified by xiangzhou 河北建投 2012-11-22
			 * 
			 * KingDee 提供科目余额视图 v_accountViewBalance
			 * 
			 * FDATE         				期间  ( YYYYMMDD )
			 * FAccountCode  				科目编码 
			 * FAccountName	 				科目名称
			 * FCurrencyID	 				币种
			 * Fdebit        				本期借方发生额   
			 * Fcredit		            	本期贷方发生额
			 * Fbalance      				本期余额        
			 * cfdate						更新日期	
			 * fcd							借贷方向	1借，2贷,余额方向不是科目方向
			 */
           
           /**
            * added by shuangniu 2011-01-19 查询科目余额视图-------------------------------------------------
            */
           String strDate2="";//余额区间 YYYYMM
           strDate2=strDate.replaceAll("-","");
           strDate2=strDate2.substring(0,strDate2.length()-2); 
           System.out.println(" KingDee 余额区间：" +strDate2);
           
           strSQL.append(" select FDATE, FAccountCode, FAccountName,FCurrencyID,Fdebit,Fcredit,FBalance,cfdate,fcd  \n ");
           strSQL.append(" from v_accountViewBalance \n");
			
		   ps = conn.prepareStatement(strSQL.toString());
		   Log.print("***********查询金蝶科目余额sql：" + strSQL.toString());
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
            	   info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);	//余额方向：借
            	   info.setDebitBalance(rs.getDouble("FBalance"));				//本期余额
               }else if((rs.getLong("fcd")==SETTConstant.DebitOrCredit.CREDIT)){
            	   info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);	//余额方向：贷
            	   info.setCreditBalance(rs.getDouble("FBalance"));				//本期余额
               }
               list.add(info);
           }
		   System.out.println("===================================从金蝶取科目余额结束，个数为："+list.size());
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
	 * 得到科目(当日)发生额及笔数  (金蝶)
	 * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectAmount(long, long, long, java.sql.Timestamp)
	 */     
    public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID,long lModelID, Timestamp tsDate) throws Exception
    {
    
    	for(int i=0;i<5;i++)
			System.out.println("==========KingDee=============得到金蝶接口的科目发生额");

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
            
            //得到金蝶系统中指定日期的科目发生额数据
            /*
             * 
             	select a.FDate, c.FNumber, 
             		sum(case when b.FDC = 1 then b.FAmount else 0 end) as MDebitAmount, sum(case when b.FDC = 0 then b.FAmount else 0 end) as MCreditAmount, 
             		sum(case when b.FDC = 1 then 1 else 0 end) as NDebitNumber, sum(case when b.FDC = 0 then 1 else 0 end) as NCreditNumber
             	from t_Voucher a left outer join t_VoucherEntry b on a.FVoucherId = b.FVoucherID 
             		left outer join t_Account c on b.FAccountID = c.FAccountID
             	where b.FCurrencyID = @CurrencyID
             		and a.FDate = @DT
             		--and (@Checked =0 or @Checked =1 and a.FChecked = 1)
             	group by a.FDate, c.FNumber
            */
            
            String strDate=DataFormat.getDateString(tsDate);
            System.out.println(" 得到格式化后的传进来的日期是：" +strDate);
            
            //得到当月第一天
            String strFirstDate=DataFormat.getDateString(DataFormat.getFirstDateOfMonth(tsDate));
            System.out.println(" 得到格式化后的当月第一天的日期是：" +strFirstDate);
            	
            
            strSQL.append(" select a.FDate, c.FNumber, \n");
            strSQL.append(" sum(case when b.FDC = 1 then b.FAmount else 0 end) as MDebitAmount, sum(case when b.FDC = 0 then b.FAmount else 0 end) as MCreditAmount, \n");
            strSQL.append(" sum(case when b.FDC = 1 then 1 else 0 end) as NDebitNumber, sum(case when b.FDC = 0 then 1 else 0 end) as NCreditNumber \n");
            strSQL.append(" from t_Voucher a left outer join t_VoucherEntry b on a.FVoucherId = b.FVoucherID \n");
            strSQL.append(" left outer join t_Account c on b.FAccountID = c.FAccountID \n");
            strSQL.append(" where b.FCurrencyID = "+ lCurrencyID + "\n");
            strSQL.append(" and a.FDate = '" + strDate + "' \n");
            strSQL.append(" and a.FChecked = 1 \n");
            strSQL.append(" group by a.FDate, c.FNumber \n");
           
            System.out.println(strSQL); 			
 			ps = conn.prepareStatement(strSQL.toString());
 			Log.print("***********查询金蝶科目发生额sql：" + strSQL.toString());
 			rs = ps.executeQuery();
 			
 			
 			int i=1;
 			while (rs != null && rs.next())			
            {
 				
 				//System.out.println("--从GL中得到科目号为:getGLSubjectCode：" + rs.getString("FNumber"));
 				//System.out.println("  得到发生额   " + strFirstDate + "   借方：" + rs.getDouble("MDebitAmount") + "  贷方：" + rs.getDouble("MCreditAmount"));
 				Log.print("--从GL中得到科目号为:getGLSubjectCode：" + rs.getString("FNumber"));
 				Log.print("  得到发生额   " + strFirstDate + "   借方：" + rs.getDouble("MDebitAmount") + "  贷方：" + rs.getDouble("MCreditAmount"));
 							
                GLBalanceInfo info = new GLBalanceInfo();
                info.setGLDate(tsDate);				//日期
                info.setOfficeID(lOfficeID);		//办事处
                info.setCurrencyID(lCurrencyID);	//币种
                info.setGLSubjectCode(rs.getString("FNumber"));			//科目号
                info.setDebitAmount(rs.getDouble("MDebitAmount"));		//借方发生额
                info.setCreditAmount(rs.getDouble("MCreditAmount"));	//贷方发生额
                info.setDebitNumber(rs.getLong("NDebitNumber"));		//借方笔数
                info.setCreditNumber(rs.getLong("NCreditNumber"));		//贷方笔数
                
                Log.print("添加余额的个数为:"+i++);
                
                list.add(info);
            }
 			
 			if(list==null && list.size()==0){
 				System.out.println("=============得到的数据为空!");
 			}else{
 				System.out.println("=============得到的数据不为空!有数据！！！！");
 				//System.out.println("============="+	((GLBalanceInfo)list.get(0)).getGLSubjectCode() );
 			}
 			
 		   Log.print("进入到得到科目发生额的方法里面！--------结束!!！");
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
     * postGLVoucherToKingDee()：判断对方返回信息是否完毕
     */
    private static long postGLVoucherToKingDee(Collection collVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        PreparedStatement psAddBatch = null;
        
        String note = null;
        try
        {
        	/**
        	 * modify by shuangniu	新奥导凭证到金蝶系统中。
        	 */
        	//得到总账设置信息
        	System.out.println("===================================准备导凭证到金蝶系统！！！");
// 	        conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
        	conn = get_jdbc_connection(lOfficeID,lCurrencyID,0);
 	        System.out.println("===================================取连接完毕！！！");
            conn.setAutoCommit(false);
            
            GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
			
//			String drrq = DataFormat.getDateString( com.iss.itreasury.util.Env.getSystemDateTime()); //导入日期
			Timestamp drrq = com.iss.itreasury.util.Env.getSystemDateTime(); //导入日期
			
			//得到金蝶辅助核算协定项
			GLVoucherDao dao = new GLVoucherDao();
			Map<String, String> map = dao.getGLKingdeeclient();
            
            if (collVoucher != null)
            {
            	System.out.println("取凭证信息！！！！！！！！！！！");
                Iterator it = collVoucher.iterator();
                //分录笔数
                int EntryCount = 0;
                
                sbRecord.setLength(0);
                sbRecord.append(" insert into CT_CUS_Fmbusiness( \n");                       								 
                sbRecord.append(" FNumber,FBookDate,FBizDate,FDInputDate,FCompanyNumber,Fcurrency,FCreator,FAuditor,FAbstract,FAttachments,FEntryDC,FAmount,FAccountCode,FState,FCustomerCode \n ");
                sbRecord.append(" ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
                psAddBatch = conn.prepareStatement(sbRecord.toString());
                
                while (it.hasNext())
                {
                	EntryCount = 0;

                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    voucher.setPostStatusID(Constant.GLPostStatus.DOING);	//正在生成

                    for (EntryCount = 0; voucher.getList() != null && EntryCount < voucher.getList().size(); EntryCount++)
                    {
                        GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(EntryCount);
                        GLKingDeePzflInfo pzflinfo = new GLKingDeePzflInfo();
                        
                        pzflinfo.setFNumber(entryinfo.getTransNo());
                        pzflinfo.setFBookDate(entryinfo.getExecute());
                        pzflinfo.setFBizDate(entryinfo.getInterestStart());                       
                        pzflinfo.setFDInputDate(com.iss.itreasury.util.Env.getSystemDateTime());//导入时间系统时间
                        pzflinfo.setFCompanyNumber(glSettingInfo.getPk_corp());//财务公司单位编码
                        
                        pzflinfo.setFCurrencyID(entryinfo.getCurrencyID());
                        pzflinfo.setFCurrenyCode(Constant.CurrencyType.getCode(pzflinfo.getFCurrencyID()));//币种编码
                        
                        pzflinfo.setFCreator(entryinfo.getInputUserName().equals("-1")?"机制":entryinfo.getInputUserName());
                        pzflinfo.setFAuditor(entryinfo.getCheckUserName().equals("-1")?"稽核":entryinfo.getCheckUserName());
                        
                        pzflinfo.setFExplanation(entryinfo.getAbstract());
                        pzflinfo.setFAttachments(0);//方正技术项目，导入金蝶K3默认0个附件
                        //借贷方向 1借；2贷
                        if(entryinfo.getDirectionID()==SETTConstant.DebitOrCredit.DEBIT){
                        	pzflinfo.setFDC("1"); //传给金蝶的借方
                        } else if (entryinfo.getDirectionID()==SETTConstant.DebitOrCredit.CREDIT){
                        	pzflinfo.setFDC("2"); //传给金蝶的贷
                        }
                        pzflinfo.setFAmount(entryinfo.getAmount());
                        pzflinfo.setFAccountID(entryinfo.getSubject());
                        pzflinfo.setFState("0");//软通写入初始为0
                        pzflinfo.setFCustomerCode(map.get(entryinfo.getAssitantValue())== null?entryinfo.getAssitantValue():map.get(entryinfo.getAssitantValue()));
                        pzflinfo.setFCustomerName(entryinfo.getClientName());
                        pzflinfo.setFAmount(entryinfo.getAmount());
                       
                        note = "交易号："+entryinfo.getTransNo()+",交易类型："+SETTConstant.TransactionType.getName(entryinfo.getTransactionTypeID())+",原始摘要："+entryinfo.getAbstract();
                        
                        /**
                         * 新奥财务 金蝶凭证中间表  CT_CUS_Fmbusiness 字段信息如下：
                         * |----------------------------------------------------|
                         * | 字段名				   	| 字段描述					|
                         * |------------------------|---------------------------|
                         * | FNumber				| 交易编号					|
                         * | FBookDate				| 执行日期					|
                         * | FBizDate				| 起息日期					|
                         * | FDInputDate 			| 导入日期					|
                         * | FCompanyNumber			| 单位编码					|
                         * | Fcurrency				| 币种       				|
                         * | FCreator				| 制单人    					|
                         * | FAuditor				| 审核人   					|
                         * | FAbstract				| 摘要				     	|
                         * | FAttachments			| 附件数   					|
                         * | FEntryDC				| 借贷方向					|
                         * | FAmount				| 金额      					|
                         * | FAccountCode			| 科目编码					|
                         * | FState					| 状态      					|
                         * | FCustomerCode			| 客户编号     				|
                         * | FCustomerName			| 客户名称（河北建投无）      	|
                         * |------------------------|---------------------------|
                         */
                        
//                        String zxrq = DataFormat.getDateString( pzflinfo.getFBookDate());  //执行日期
//                        String qxrq = DataFormat.getDateString( pzflinfo.getFBizDate());   // 起息日期
                        Timestamp zxrq = pzflinfo.getFBookDate();  //执行日期
                        Timestamp qxrq = pzflinfo.getFBizDate();   // 起息日期
                       
                        int iIndex = 1;
                        psAddBatch.setString(iIndex++, pzflinfo.getFNumber());
                        psAddBatch.setTimestamp(iIndex++, zxrq);
                        psAddBatch.setTimestamp(iIndex++, qxrq);
                        psAddBatch.setTimestamp(iIndex++, drrq);
                        psAddBatch.setString(iIndex++, pzflinfo.getFCompanyNumber());
                        psAddBatch.setString(iIndex++, pzflinfo.getFCurrenyCode());
                        psAddBatch.setString(iIndex++, pzflinfo.getFCreator());
                        psAddBatch.setString(iIndex++, pzflinfo.getFAuditor());
                        psAddBatch.setString(iIndex++, note);
                        psAddBatch.setInt(iIndex++, pzflinfo.getFAttachments());
                        psAddBatch.setString(iIndex++, pzflinfo.getFDC());
                        psAddBatch.setDouble(iIndex++, pzflinfo.getFAmount());
                        psAddBatch.setString(iIndex++, pzflinfo.getFAccountID());
                        psAddBatch.setString(iIndex++, pzflinfo.getFState());
                        psAddBatch.setString(iIndex++, pzflinfo.getFCustomerCode());
                        psAddBatch.addBatch();
                        
                    }
                    
                    //传输完成，设置传输状态
                    voucher.setPostStatusID(Constant.GLPostStatus.SUCCESS);	//完成
                }
                psAddBatch.executeBatch();
            }
            conn.commit();
            System.out.println("===================================导凭证到金蝶系统结束！！！");
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
     * 根据参数得到凭证汇总表三个最大凭证号-- lType：1 凭证内码FVoucherID；2 分字类凭证号FNumber；3 凭证序号FSerialNum；
     * @param lOfficeID
     * @param lCurrencyID
     * @param lGroupId
     * @return 
     * @throws Exception
     */
    public static int getMaxPzNo(long lOfficeID, long lCurrencyID, int lType, String lGroupID) throws Exception
	{
    	int iMaxPzNo = 0;
    	//String strMaxPzNo = "";
    	Connection conn = null;        
        PreparedStatement ps = null;        
        ResultSet rs = null;        
        
        String lName = ""; 
        switch (lType)
		{
			case 	1	: lName = "FVoucherID"; break;
			case 	2	: lName = "FNumber"; break;
			case 	3	: lName = "FSerialNum"; break;			
		}
       
        try
        {
            //
        	  conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  SELECT isnull(max(" + lName + "),0) as MaxNo \n ");            
            sbSQL.append("  FROM t_voucher where convert(varchar(10),FGroupID) like '" + lGroupID + "%' \n ");                
            //System.out.println(sbSQL.toString());
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();

            if(rs != null && rs.next())
            {
            	iMaxPzNo = rs.getInt("MaxNo");
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
     * 得到金蝶目前的会计区间--  lType：1 会计年度CurrentYear；2 当前会计期间CurrentPeriod；
     * @param lOfficeID,lCurrencyID
     * @return
     */
    public static String getPeriod(long lOfficeID,long lCurrencyID,int lType) throws Exception
	{
          String strPeriod = "";
    	  Connection conn = null;        
          PreparedStatement ps = null;        
          ResultSet rs = null;        

          String lName = ""; 
          switch (lType)
  		  {
  				case 	1	: lName = "CurrentYear"; break;
  				case 	2	: lName = "CurrentPeriod"; break;
  		  }

          try
          {
              //
          	  conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
              StringBuffer sbSQL = new StringBuffer();
              sbSQL.setLength(0);
              sbSQL.append("  select FValue from T_SYSTEMPROFILE where fcategory='gl' and Fkey='" + lName + "' \n ");            
              //System.out.println(sbSQL.toString());
              Log.print(sbSQL.toString());
              ps = conn.prepareStatement(sbSQL.toString());
              rs = ps.executeQuery();
              if(rs != null && rs.next())
              {
              	strPeriod = rs.getString("FValue");
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
          
    	return strPeriod;
    	
    }
     
    /**
     * 得到科目在金蝶对应的科目内码 FAccountID
     * @param lOfficeID,lCurrencyID,lFNumber
     * @return
     */
    public static String getAccountId(long lOfficeID,long lCurrencyID,String lFNumber) throws Exception
	{
          String strAccountID = "";
    	  Connection conn = null;        
          PreparedStatement ps = null;        
          ResultSet rs = null;        
          try
          {
              //
          	  conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
              StringBuffer sbSQL = new StringBuffer();
              sbSQL.setLength(0);
              sbSQL.append("  select FAccountID from T_Account where FNumber='"+ lFNumber + "' \n ");            
              Log.print(sbSQL.toString());
              ps = conn.prepareStatement(sbSQL.toString());
              rs = ps.executeQuery();
              if(rs != null && rs.next())
              {
              	strAccountID = rs.getString("FAccountID");
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
          
    	return strAccountID;
    	
    }

    /**
     * 说明：取外部客户信息（金蝶专用--t_organization客户表）
     * @author liuqing
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     * @throws Exception
     */
    public Collection getGLExternalAccount(long lOfficeID, long lCurrencyID) throws Exception
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
            /////取客户信息(确认是否只按增量取数据？？？--全部取出，导入时判断是否存在)
            System.out.println("===================================准备从金蝶取客户信息！！！");
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
            System.out.println("===================================取连接完毕！！！");
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append(" select FItemID, FNumber, FName, FBank, FAccount, FCountry, FProvince, FCity, FRegionID, FAddress, FPostalCode, FPhone, FFax, FHomePage, FEMail, FShortName, FShortNumber, FTrade, FBrNo, FContact, FCorPerate, FCreditLevel, FStatus \n ");            
            sbSQL.append(" from t_organization \n ");            
            System.out.print("*********客户信息Sql:**********\n"+sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
            	GLKingDeeExtAcctInfo info = new GLKingDeeExtAcctInfo();
                info.setFItemID(rs.getString("FItemID"));                
                info.setFNumber(rs.getString("FNumber"));                
                info.setFName(rs.getString("FName"));
                info.setFBank(rs.getString("FBank"));  
                info.setFAccount(rs.getString("FAccount"));
                info.setFCountry(rs.getString("FCountry"));
                info.setFProvince(rs.getString("FProvince"));
                info.setFCity(rs.getString("FCity"));
                info.setFRegionID(rs.getString("FRegionID"));
                info.setFAddress(rs.getString("FAddress"));
                info.setFPostalCode(rs.getString("FPostalCode"));
                info.setFPhone(rs.getString("FPhone"));
                info.setFFax(rs.getString("FFax"));
                info.setFHomePage(rs.getString("FHomePage"));
                info.setFEMail(rs.getString("FEMail"));
                info.setFShortName(rs.getString("FShortName"));
                info.setFShortNumber(rs.getString("FShortNumber"));
                info.setFTrade(rs.getString("FTrade"));
                info.setFBrNo(rs.getString("FBrNo"));
                info.setFContact(rs.getString("FContact"));
                info.setFCorperate(rs.getString("FCorperate"));
                info.setFCreditLevel(rs.getString("FCreditLevel"));
                info.setFStatus(rs.getString("FStatus"));
                //其他字段暂时忽略
                list.add(info);
                
                Log.print("=====取得的客户："+rs.getString("FName"));
            }
            System.out.println("=====取得的客户数量："+list.size());
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
    
    /**
     * 将客户信息导入到金蝶系统中。
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     * @throws Exception
     */
    private static long postClinetInfoToKingDee(long lOfficeID,long lCurrencyID) throws Exception
    {
        long lDealStatusID = Constant.SUCCESSFUL;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        long lResult = 0;
        Collection clientColl = null;
        
        try
        {
        	clientColl = getClientColl(lOfficeID,lCurrencyID);
        	//得到总账设置信息
        	System.out.println("---得到总账设置信息---");
        	GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
        	conn=ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
            
            conn.setAutoCommit(false);
            
        	System.out.println("向金蝶插入一条客户信息！！！！！！！！！！！");
            System.out.println("before insert FZ_MID_CUS");

            /**
             * 新奥财务 金蝶凭证中间表  FZ_MID_CUS 字段信息如下：
             * |----------------------------------------------------|
             * | 字段名				   	| 字段描述					|
             * |------------------------|---------------------------|
             * | FNumber				| 客户编号					|
             * | FName				    | 客户名称					|
             * | fDate 		 	  	    | 导入时间     				|
             * | fk3Date 		 	  	| k3时间      				|
             * |------------------------|---------------------------|
             */
            
            if (clientColl != null)
            {
            	System.out.println("---------删除客户信息---------");
            	sbRecord.append("delete from XA_MID_CUS");
            	ps = conn.prepareStatement(sbRecord.toString());
                ps.executeUpdate();
                ps.close();
                ps = null;
            	
            	System.out.println("---------取客户信息-----------");
                Iterator it = clientColl.iterator();
                //分录笔数
                int EntryCount = 0;
                
                while (it.hasNext())
                {
                	ClientInfo client = (ClientInfo) it.next();
                	System.out.println("====客户编号===="+client.getCode().substring(3));
                	
                	sbRecord.setLength(0);
                	sbRecord.append(" insert into XA_MID_CUS( \n");                       								 
    	            sbRecord.append("FNumber ,FName \n ");
    	            sbRecord.append(" ) \n");
    	            sbRecord.append(" values  \n");
    	            sbRecord.append(" ( \n");                        
    	            sbRecord.append(" '" + client.getCode().substring(3) + "','" +client.getName() + "' \n");
    	            sbRecord.append(" ) \n");
                	
    	            ps = conn.prepareStatement(sbRecord.toString());
                    
    	            lResult = ps.executeUpdate();
    	            ps.close();
    	            ps = null;
                }
            }
               
//            if(lResult > 0) {   
//            	lDealStatusID = Constant.SUCCESSFUL;	//完成
//            }else{
//            	lDealStatusID = Constant.FAIL;	//完成
//            }
            conn.commit();
            conn.close();
            conn = null;
        }
        catch (Exception e)
        {
            lDealStatusID = Constant.FAIL;
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
    
    private static Collection getClientColl(long lOfficeID, long lCurrencyID) throws Exception
    {
    	long lReturn = -1;

 	   	Connection conn = null;
 	   	PreparedStatement pstmt = null;
 	   	ResultSet rset = null;
 	   	ArrayList list = new ArrayList();

 	   	StringBuffer buffer = new StringBuffer();
 	   	
 	   	buffer.append("select code,name from client_clientInfo where statusID="+Constant.RecordStatus.VALID);
 	   	
 	   
 	   	try
 	   	{
 	   		conn = Database.getConnection();
 			pstmt = conn.prepareStatement(buffer.toString());
 			rset = pstmt.executeQuery();

 			while(rset.next())
 			{
 				ClientInfo tmp = new ClientInfo();
 				tmp.setCode(rset.getString("code"));
 				tmp.setName(rset.getString("name"));
 				list.add(tmp);			  
 			}

 		}
 	   	finally
 	   	{
 	   		try
 	   		{
 	   			if (rset != null)
 	   			{
 	   				rset.close();
 	   				rset = null;
 	   			}
 	   			if (pstmt != null)
 	   			{
 	   				pstmt.close();
 	   				pstmt = null;
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
 	  	
 	   return list;
    }
    
    /**
     * 得到ORACLE数据库连接
     * @param lOfficeID
     * @param lCurrencyID
     * @param lType
     * @return
     * @throws Exception
     */
    public static Connection get_jdbc_connection(long lOfficeID,long lCurrencyID,long lType) throws Exception
    {
        Connection conn = null;
        try
        {
        	//得到配置信息
			GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,lType);
		
			String DB_IP = glSettingInfo.getGlDBIP(); // IP
			String DB_SID = glSettingInfo.getGlDBDatabaseName(); // 库名称
			String DB_USERNAME = glSettingInfo.getGlDBUserName(); // 用户名
			String DB_PASSWORD = DataFormat.formatNullString(glSettingInfo.getGlDBPassWord()); // 密码
			String DB_PORT = glSettingInfo.getGlDBPort(); // 端口

			String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
			
			Log.print("dbURL = " + dbURL);
            Log.print("DB_USERNAME = " + DB_USERNAME);
            Log.print("DB_PASSWORD = " + DB_PASSWORD);
            
			Class.forName(jdbcDriver).newInstance();
			conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
        }
        catch (Exception e)
        {
            Log.print("connect db failed by oracle jdbc driver. " + e.toString());
            throw new Exception(e.getMessage());
        }
        return conn;
    }
    
    
        
}
