/*
 * Created on 2004-2-11
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem.settlement;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.closesystem.basebean.GLWithinBaseBean;
import com.iss.itreasury.dataentity.GLVouchInfo;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLKingDeeExtAcctInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.oracle_cpf.GLOracleFinBean_cpf;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SettGLWithinDao extends GLWithinBaseBean
{
    /**
     *  
     */
    public SettGLWithinDao()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args)
    {
        try
        {
            //findGLVoucherByCondition(1, 1, 1,
            // DataFormat.getDateTime("2004-02-02"),
            // DataFormat.getDateTime("2004-02-02"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * @author yychen ? FindGLVoucherBaseBean.findGLVoucherByCondition ():查询会计凭证
     * 参数：long lOfficeID:办事处； long lCurrencyID:币种； Timestamp tsStartDate:执行日；
     * Timestamp tsEndDate:执行日； 返回值：Collection collVoucher:凭证集合；
     * 功能描述：根据条件从业务系统中查询所需的会计凭证； 流程描述： l 建立连接，开始事务； l 根据条件查询出所需的交易编号； l
     * 分别根据交易编号查询出该交易的会计分录； l 关闭连接，提交事务；
     */
    public Collection findGLVoucherByConditionForISS(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
    {
        long lReturn = -1;
        ArrayList list = new ArrayList();
        try
        {
            sett_GLEntryDAO dao = new sett_GLEntryDAO();
            boolean HaveData = false;
            for (Timestamp tsTempDate = tsStart; DataFormat.getDateString(tsTempDate).compareTo(DataFormat.getDateString(tsEnd)) <= 0; tsTempDate = DataFormat.getNextDate(tsTempDate))
            {
                Collection coll = null;
                coll = dao.findByExecuteDate(tsTempDate, lOfficeID, lCurrencyID);
                System.out.println("**findGLVoucherByCondition:coll=" + coll);
                if (coll != null)
                {
                    Iterator it = coll.iterator();
                    while (it.hasNext())
                    {
                        GLVoucherInfo voucher = null;
                        com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo sett_gl = new com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo();
                        GLEntryInfo info = new GLEntryInfo();
                        sett_gl = (com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo) it.next();
                        //////
                        System.out.println("**findGLVoucherByCondition:sett_gl.getPostStatusID()=" + sett_gl.getPostStatusID());

                        info.setID(sett_gl.getID());
                        info.setAbstract(sett_gl.getAbstract());
                        info.setAmount(sett_gl.getAmount());
                        info.setCurrencyID(sett_gl.getCurrencyID());
                        info.setOfficeID(sett_gl.getOfficeID());
                        info.setDirectionID(sett_gl.getTransDirection());
                        info.setExecute(sett_gl.getExecute());
                        info.setInterestStart(sett_gl.getInterestStart());
                        info.setSubject(sett_gl.getSubjectCode());
                        info.setTransNo(sett_gl.getTransNo());
                        info.setBankChequeNo(getBankChequeNoByTransNo(info.getTransNo()));
                        //////
                        boolean bHaveData = false;
                        for (int i = 0; list != null && i < list.size(); i++)
                        {
                            voucher = (GLVoucherInfo) list.get(i);
                            if (voucher.getTransNo().equals(info.getTransNo()))
                            {
                                bHaveData = true;
                                break;
                            }
                        }
                        if (bHaveData == false)
                        {
                            System.out.println("**findGLVoucherByCondition:info.getTransNo()=" + info.getTransNo());
                            voucher = new GLVoucherInfo();
                            voucher.setTransNo(info.getTransNo());
                            voucher.setPostDate(info.getExecute());
                            voucher.setModelID(Constant.ModuleType.SETTLEMENT);
                            list.add(voucher);
                        }
                        voucher.addEntryInfo(info);
                    }
                }
            }
        }
        finally
        {
        }
        return (list != null && list.size() > 0 ? list : null);
    }

    //查找内部需要导出的会计数据信息(分笔和汇总两种方式)
    public Collection findGLVoucherByCondition(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
    {
        long lReturn = -1;
        ArrayList list = new ArrayList();
        try
        {
        	System.out.println("-------开始--获得内部财务数据（制成凭证）");
            sett_GLEntryDAO dao = new sett_GLEntryDAO();
            boolean HaveData = false;
            GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
            for (Timestamp tsTempDate = tsStart; DataFormat.getDateString(tsTempDate).compareTo(DataFormat.getDateString(tsEnd)) <= 0; tsTempDate = DataFormat.getNextDate(tsTempDate))
            {
            	//如果目前配置的导账接口是中油的话，得要更新内部表sett_GlSetting中SMULTICODE多维码的值，以供中油现金流量用
            	if(glSettingInfo.getGlName().equalsIgnoreCase("ORACLE_CPF")){ //Oracle中油接口
            		System.out.println("========是中油总账接口,批量更新多维码字段=======开始!");
            		lReturn=updateMultiCodeForCPF(lOfficeID,lCurrencyID,tsTempDate);
            		System.out.println("========是中油总账接口,批量更新多维码字段=======结束!");
            	}
         	   
            	//以下是以前的流程
            	System.out.println("======开始--按照（分笔）或（汇总）方式得到分计分录（GLEntryInfo）的集合,值："+glSettingInfo.getNImportType());
                Collection coll = null;
                if(glSettingInfo.getNImportType()==Constant.GLImportType.hz)  //汇总
                {
                	coll = dao.findByExecuteDateHz(tsTempDate, lOfficeID, lCurrencyID);
                	System.out.println("======结束--得到分计分录（GLEntryInfo）的集合==========总账导入方式:汇总,得到的（GLEntryInfo）个数为:"+coll.size());
                }
                else	//其它的方式(分笔)	
                {
                	if(glSettingInfo.getGlName().equalsIgnoreCase("KINGDEE")||glSettingInfo.getGlName().equalsIgnoreCase("JOINCHEER")) //金蝶接口&久其（分笔方式） 
                	{
                    	coll = dao.findByExecuteDateAndPostStatusID(tsTempDate, lOfficeID, lCurrencyID);                		
                	}
                	else if(glSettingInfo.getGlName().equalsIgnoreCase("NC"))
                    {
                    	System.out.println("    ======开始---按日期取未传输的分录信息(大唐“NC”接口)--=== 日期："+ tsTempDate);
                        coll = dao.findByExecuteDateForNC(tsTempDate, lOfficeID, lCurrencyID);
                        System.out.println("    ======结束---按日期取未传输的分录信息(大唐“NC”)--===");
                    }
                	//专门针对于国电开发
                	else if(glSettingInfo.getGlName().equalsIgnoreCase("U850"))
        			{
                    	System.out.println("    ======开始---按日期取未传输的分录信息(国电“U850”接口)--=== 日期："+ tsTempDate);
                        coll = dao.findByExecuteDateForU850(tsTempDate, lOfficeID, lCurrencyID);
                        System.out.println("    ======结束---按日期取未传输的分录信息(国电“U850”)--===");
        			}
                	else
                	{
                		coll = dao.findByExecuteDate(tsTempDate, lOfficeID, lCurrencyID);
                	}

                	if(glSettingInfo.getNImportType()==Constant.GLImportType.fb)
                		System.out.println("======结束--得到分计分录（GLEntryInfo）的集合=====分笔,得到的（GLEntryInfo）个数为:"+coll.size());
                	else
                		System.out.println("======结束--得到分计分录（GLEntryInfo）的集合=====分笔(数据库没有配置信息，按照默认的分笔方式进行),得到的（GLEntryInfo）个数为:"+coll.size());
                }
                
                if (coll != null)
                {
                	System.out.println("=======开始--按照接口类型把会计分录制成凭证");	
        			if(glSettingInfo.getGlName().equalsIgnoreCase("GENERSOFT")) //浪潮接口
        			{
        				if(glSettingInfo.getNImportType()==Constant.GLImportType.hz)   //汇总
	        			{
	        				System.out.println("======开始--浪潮接口，制成一大张凭证信息~");	
	        				Iterator it = coll.iterator();
	                        GLVoucherInfo voucher = new GLVoucherInfo();
	                        while (it.hasNext())
	                        {
	                            com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo sett_gl = new com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo();
	                            GLEntryInfo info = new GLEntryInfo();
	                            sett_gl = (com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo) it.next();
	                            //////
	                            //System.out.println("**findGLVoucherByCondition:sett_gl.getPostStatusID()=" + sett_gl.getPostStatusID());
	                            if (sett_gl.getPostStatusID() != Constant.GLPostStatus.SUCCESS && sett_gl.getPostStatusID() != Constant.GLPostStatus.DOING && NameRef.getSubjectTypeByCode(sett_gl.getSubjectCode()) != SETTConstant.SubjectAttribute.TABLEOUT)
	                            {
	                                info.setID(sett_gl.getID());
	                                info.setAbstract(sett_gl.getAbstract());
	                                info.setAmount(sett_gl.getAmount());
	                                info.setCurrencyID(sett_gl.getCurrencyID());
	                                info.setOfficeID(sett_gl.getOfficeID());
	                                info.setDirectionID(sett_gl.getTransDirection());
	                                info.setExecute(sett_gl.getExecute());
	                                info.setInterestStart(sett_gl.getInterestStart());
	                                info.setSubject(sett_gl.getSubjectCode());
	                                info.setTransNo(sett_gl.getTransNo());
	                                info.setBankChequeNo(getBankChequeNoByTransNo(info.getTransNo()));
	                                //////
	                                //System.out.println("---------------得到分录的交易号为:"+info.getTransNo());
	                                voucher.addEntryInfo(info);
	                                voucher.setPostDate(Env.getSystemDate(lOfficeID, lCurrencyID));
	                                voucher.setModelID(Constant.ModuleType.SETTLEMENT);
	                                //后来加上以前是屏闭了的
	                                voucher.setTransNo(info.getTransNo());
	                                HaveData = true;
	                            }
	                        }
	                        if (HaveData)
	                        {
	                            list.add(voucher);
	                            HaveData = false;
	                        }
	                        System.out.println("======结束--浪潮接口，制成一大张凭证信息~");
	        			}
        				else    //分笔
        				{
        					 System.out.println("======开始--浪潮接口，制成分笔凭证信息~");
        					 Iterator it = coll.iterator();
                             while (it.hasNext())
                             {
                                 GLVoucherInfo voucher = null;
                                 com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo sett_gl = new com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo();
                                 GLEntryInfo info = new GLEntryInfo();
                                 sett_gl = (com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo) it.next();
                                 //////
                                 //System.out.println("**findGLVoucherByCondition:sett_gl.getPostStatusID()=" + sett_gl.getPostStatusID());
                                 //System.out.println("**findGLVoucherByCondition:sett_gl.getTransNo()=" + sett_gl.getTransNo());
                                 if (sett_gl.getPostStatusID() != Constant.GLPostStatus.SUCCESS && sett_gl.getPostStatusID() != Constant.GLPostStatus.DOING && NameRef.getSubjectTypeByCode(sett_gl.getSubjectCode()) != SETTConstant.SubjectAttribute.TABLEOUT)
                                 {
                                     System.out.println("＝＝＝＝＝＝＝＝需要导入＝＝＝＝＝＝＝＝＝＝");
                                     info.setID(sett_gl.getID());
                                     info.setAbstract(sett_gl.getAbstract());
                                     info.setAmount(sett_gl.getAmount());
                                     info.setCurrencyID(sett_gl.getCurrencyID());
                                     info.setOfficeID(sett_gl.getOfficeID());
                                     info.setDirectionID(sett_gl.getTransDirection());
                                     info.setExecute(sett_gl.getExecute());
                                     info.setInterestStart(sett_gl.getInterestStart());
                                     info.setSubject(sett_gl.getSubjectCode());
                                     info.setTransNo(sett_gl.getTransNo());
                                     info.setBankChequeNo(getBankChequeNoByTransNo(info.getTransNo()));
                                     //////
                                     boolean bHaveData = false;
                                     for (int i = 0; list != null && i < list.size(); i++)
                                     {
                                         voucher = (GLVoucherInfo) list.get(i);
                                         if (voucher.getTransNo().equals(info.getTransNo()))
                                         {
                                             bHaveData = true;
                                             break;
                                         }
                                     }
                                     if (bHaveData == false)
                                     {
                                         System.out.println("**getTransNo from info : info.getTransNo()=" + info.getTransNo());
                                         voucher = new GLVoucherInfo();
                                         voucher.setTransNo(info.getTransNo());
                                         voucher.setPostDate(info.getExecute());
                                         voucher.setModelID(Constant.ModuleType.SETTLEMENT);
                                         list.add(voucher);
                                     }
                                     voucher.addEntryInfo(info);
                                 }
                                 else
                                 {
                                 	System.out.println("＝＝＝＝＝＝＝＝不需要导入＝＝＝＝＝＝＝＝＝＝");
                                 }
                             }
                             System.out.println("======结束--浪潮接口，制成分笔凭证信息~");
        				}
                    }
                    else	
                    {
                    	System.out.println("======开始--其它接口，制成多张会计凭证,根据会计分录的交易号生成会计凭证~");
                        Iterator it = coll.iterator();
                        while (it.hasNext())
                        {
                            GLVoucherInfo voucher = null;
                            com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo sett_gl = new com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo();
                            GLEntryInfo info = new GLEntryInfo();
                            sett_gl = (com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo) it.next();
                            //////
                            //System.out.println("**findGLVoucherByCondition:sett_gl.getPostStatusID()=" + sett_gl.getPostStatusID());
                            if (sett_gl.getPostStatusID() != Constant.GLPostStatus.SUCCESS)	//判断还未传输
                            {
                                info.setID(sett_gl.getID());
                                info.setAbstract(sett_gl.getAbstract());
                                info.setAmount(sett_gl.getAmount());
                                info.setCurrencyID(sett_gl.getCurrencyID());
                                info.setOfficeID(sett_gl.getOfficeID());
                                info.setDirectionID(sett_gl.getTransDirection());
                                info.setExecute(sett_gl.getExecute());
                                info.setInterestStart(sett_gl.getInterestStart());
                                info.setSubject(sett_gl.getSubjectCode());
                                info.setTransNo(sett_gl.getTransNo());
                                
                                /**
                                 * added by shuangniu  2011-01-19  新奥财务公司
                                 */
                                info.setTransactionTypeID(sett_gl.getTransactionTypeID());
                                info.setClientName(sett_gl.getClientName());
                                info.setClientCode(sett_gl.getClientCode());
                                info.setInputUserName(sett_gl.getInputUserName());
                                info.setCheckUserName(sett_gl.getCheckUserName());
                                
                                info.setMultiCode(sett_gl.getMultiCode());	//叶飞添加多维码 2005.12.17
                                
                                info.setCheckUserName(sett_gl.getCheckUserName()); //yyhe添加复核人 2006.11.02 用于中交项目
                                
                                //YYHE 添加辅助核算信息 [2006-12-21] 用于大唐项目
                                info.setAssitantName(sett_gl.getAssitantName());
                                info.setAssitantValue(sett_gl.getAssitantValue());
                                
                                info.setBankChequeNo(getBankChequeNoByTransNo(info.getTransNo()));
                                //////
                                boolean bHaveData = false;
                                for (int i = 0; list != null && i < list.size(); i++)
                                {
                                    voucher = (GLVoucherInfo) list.get(i);
                                    if (voucher.getTransNo().equals(info.getTransNo()))
                                    {
                                        bHaveData = true;
                                        break;
                                    }
                                }
                                if (bHaveData == false)
                                {
                                    //System.out.println("**findGLVoucherByCondition:info.getTransNo()=" + info.getTransNo());
                                    voucher = new GLVoucherInfo();
                                    voucher.setTransNo(info.getTransNo());
                                    voucher.setPostDate(info.getExecute());
                                    voucher.setModelID(Constant.ModuleType.SETTLEMENT);
                                    list.add(voucher);
                                }
                                voucher.addEntryInfo(info);
                            }
                        }
                        System.out.println("======结束--其它接口，制成多张会计凭证,根据会计分录的交易号生成会计凭证~");
                    }
        			System.out.println("======结束--按照接口类型把会计分录制成凭证");	
                }
            }
            System.out.println("--结束--获得内部财务数据（制成凭证）");
        }
        finally
        {
        }
        return (list != null && list.size() > 0 ? list : null);
    }

    /**
     * lixr
     * 
     * @param
     * @return @throws
     *         Exception
     */
    public boolean checkPostVoucher(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
    {
        boolean lReturn = true;
        ArrayList list = new ArrayList();
        try
        {
            sett_GLEntryDAO dao = new sett_GLEntryDAO();
            boolean HaveData = false;
            for (Timestamp tsTempDate = tsStart; DataFormat.getDateString(tsTempDate).compareTo(DataFormat.getDateString(tsEnd)) <= 0; tsTempDate = DataFormat.getNextDate(tsTempDate))
            {
                lReturn = dao.checkPostVoucher(tsTempDate, lOfficeID, lCurrencyID);
                if (!lReturn)
                {
                    return lReturn;
                }
            }
        }
        finally
        {
        }
        return lReturn;
    }

    private static String getBankChequeNoByTransNo(String sTransNo) throws Exception
    {
        String strBankChequeNo = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        StringBuffer buffer = new StringBuffer();
        try
        {
            conn = Database.getConnection();
            buffer.append("select sBankChequeNo from Sett_TransCurrentDeposit where sTransNo = '" + sTransNo + "' and sBankChequeNo is not null ");
            buffer.append(" union all ");
            buffer.append("select sBankChequeNo from sett_TransSpecialOperation where sTransNo = '" + sTransNo + "' and sBankChequeNo is not null ");
            pstmt = conn.prepareStatement(buffer.toString());
            rset = pstmt.executeQuery();
            if (rset != null & rset.next())
            {
                strBankChequeNo = DataFormat.formatString(rset.getString("sBankChequeNo"));
            }
            rset.close();
            rset = null;
            pstmt.close();
            pstmt = null;
            conn.close();
            conn = null;
        }
        finally
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
        return strBankChequeNo;
    }
    
    //批量更新现金流量（多维码字段）为中油
    private static long updateMultiCodeForCPF(long lOfficeID, long lCurrencyID,Timestamp tsStart) throws Exception
    {
        String sMultiCodeForIncome="";	//收入多维码配置
        String sMultiCodeForPayout="";	//支出多维码配置
        Connection conn = null;
        PreparedStatement pstmt = null;
        long  lResult = -1;
        StringBuffer buffer = new StringBuffer();
        try
        {
        	//得到配置项多维码SMULTICODE配置的值
        	sMultiCodeForIncome=Config.getProperty(Config.SETT_ORACLEFORCPF_MULTICODEFORINCOME);
        	sMultiCodeForPayout=Config.getProperty(Config.SETT_ORACLEFORCPF_MULTICODEFORPAYOUT);
        	System.out.println("-----得到配置项：收入多维码配置:"+sMultiCodeForIncome);
        	System.out.println("-----得到配置项：支出多维码配置:"+sMultiCodeForPayout);
        	
        	//执行SQL进行更新
        	conn = Database.getConnection();
            //业务类型方向收入更新SQL
            buffer.append(" update sett_glEntry set SMULTICODE = '" + sMultiCodeForIncome + "'");		//读取配置项里的信息
			buffer.append(" where  NTRANSDIRECTION = '" + SETTConstant.TransactionDirection.INCOME + "' ");	//收入方向
			buffer.append(" AND nOfficeID = '" + lOfficeID + "'");      //办事处
			buffer.append(" AND nCurrencyID = '" + lCurrencyID + "'");    //币种
			buffer.append(" AND nStatusID = '" + SETTConstant.TransactionStatus.CHECK + "'");      //状态(己复核)
			buffer.append(" AND dtExecute= ? ");  //执行日
			//只有102开头的科目才update
			buffer.append(" And ( sSubjectCode like '"+GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID).trim()+".102%' ");
			buffer.append("  or sSubjectCode like '"+GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID).trim()+".101%' ");
			buffer.append("  or sSubjectCode like '"+GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID).trim()+".132%' )");  
			buffer.append(" And ( nPostStatusID <> "+Constant.GLPostStatus.SUCCESS+" or nPostStatusID is null ) ");  //状态为1的为已导入的或者为字段的值为空

			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, tsStart);
			Log.print(buffer.toString());
			lResult = pstmt.executeUpdate();
			
			buffer.setLength(0);
			//业务类型方向支出更新SQL
            buffer.append(" update sett_glEntry set SMULTICODE = '" + sMultiCodeForPayout + "'");		//读取配置项里的信息
			buffer.append(" where  NTRANSDIRECTION = '" + SETTConstant.TransactionDirection.PAYOUT + "' ");	//支出方向
			buffer.append(" AND nOfficeID = '" + lOfficeID + "'");      //办事处
			buffer.append(" AND nCurrencyID = '" + lCurrencyID + "'");    //币种
			buffer.append(" AND nStatusID = '" + SETTConstant.TransactionStatus.CHECK + "'");      //状态(己复核)
			buffer.append(" AND dtExecute= ? ");  //执行日
			//只有102开头的科目才update
			buffer.append(" And ( sSubjectCode like '"+GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID).trim()+".102%' ");
			buffer.append("  or sSubjectCode like '"+GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID).trim()+".101%' ");
			buffer.append("  or sSubjectCode like '"+GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID).trim()+".132%' )");  
			buffer.append(" And ( nPostStatusID <> "+Constant.GLPostStatus.SUCCESS+" or nPostStatusID is null ) ");  //状态为1的为已导入的或者为字段的值为空

			pstmt = conn.prepareStatement(buffer.toString());
			pstmt.setTimestamp(1, tsStart);
			Log.print(buffer.toString());
			lResult = pstmt.executeUpdate();

			//释放资源
			pstmt.close();
            pstmt = null;
            conn.close();
            conn = null;
        }
        finally
        {
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
        return lResult;
    }

    /*
     * @author yychen 参数：Collection collVoucher:解析从EAI返回信息所得到的信息集合； 返回值：boolean
     * bIsSuccess:是否成功； 功能描述：修改业务系统中会计分录的状态； 流程描述： l 建立连接，开始修改会计分录状态事务； l
     * 根据参数，修改每笔会计分录的状态（数据库会计分录表Sett_GLEntry中nPostGLStatusID字段）； l
     * 提交修改会计分录状态事务，返回是否成功；
     */
    //更新内部财务数据的传输状态。（所有的接口都必须执行)
    public boolean updatePostStatus(Collection collVoucher) throws Exception
    {
        boolean bIsPassed = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        try
        {
        	//为了得到从凭证中分录中得到办事处和币种信息
        	long lOfficeID_tmp=-1;
        	long lCurrencyID_tmp=-1;
        	Iterator it_tmp = collVoucher.iterator();
        	if(it_tmp.hasNext()){
        		GLVoucherInfo voucher_tmp = (GLVoucherInfo) it_tmp.next();
        		GLEntryInfo glentryinfo_tmp = (GLEntryInfo) voucher_tmp.getList().get(0);
        		lOfficeID_tmp=glentryinfo_tmp.getOfficeID();
        		lCurrencyID_tmp=glentryinfo_tmp.getCurrencyID();
        	}
        	
        	//为了得到凭证是分笔还是汇总的信息
            GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID_tmp,lCurrencyID_tmp);
            if(glSettingInfo.getNImportType()==Constant.GLImportType.hz)  //如果是汇总的话
            {
            	System.out.println("-----------执行的是(---汇总(所有接口)---)方式更新内部财务数据的传输状态");
                conn = Database.getConnection();
                sbRecord.append("update sett_glentry set npoststatusid = ? where nStatusId = "+ SETTConstant.TransactionStatus.CHECK +" and ncurrencyId=? and nOfficeID=? \n ");
                sbRecord.append(" and sSubjectCode =? and nTransDirection = ? and dtExecute = ? ");
                System.out.println("-----------SQL:"+sbRecord.toString());
                ps = conn.prepareStatement(sbRecord.toString());
                Iterator it = collVoucher.iterator();
                while (it.hasNext())
                {
                	//给更新SQL赋值，然后批量执行SQL语句
                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    for (int i = 0; i < voucher.getList().size(); i++)
                    {
                        GLEntryInfo glentryinfo = (GLEntryInfo) voucher.getList().get(i);
                        System.out.println("---voucher.getPostStatusID()=" + voucher.getPostStatusID() + ";glentryinfo.getID()=" + glentryinfo.getID());
                        	
                        ps.setLong(1, voucher.getPostStatusID());		//凭证传输状态
                        ps.setLong(2, glentryinfo.getCurrencyID());		//分录中币种ID
                        ps.setLong(3, glentryinfo.getOfficeID());		//分录中办事处ID
                        ps.setString(4, glentryinfo.getSubject());		//分录中科目
                        ps.setLong(5, glentryinfo.getDirectionID());	//分录中的交易方向
                        ps.setTimestamp(6, glentryinfo.getExecute());	//分录中的执行日

                        ps.addBatch();
                        //如果有一个传输状态为失败，则这次更新操作则为失败
                        if (voucher.getPostStatusID() == Constant.GLPostStatus.FAILED)
                        {
                            bIsPassed = false;
                        }
                    }
                }
                ps.executeBatch();
                ps.close();
                ps = null;
                conn.close();
                conn = null;
            }
            else			//分笔
            {
                if (collVoucher != null)
                {
                	System.out.println("-----------执行的是(---分笔(所有接口)---)方式更新内部财务数据的传输状态");
                    conn = Database.getConnection();
                    sbRecord.append("update sett_glentry set npoststatusid = ? where stransno = ? ");
                    ps = conn.prepareStatement(sbRecord.toString());
                    Iterator it = collVoucher.iterator();
                    while (it.hasNext())
                    {
                        GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                        //凭证传输状态,金蝶接口直接置成功--liuqing
                        if (glSettingInfo.getGlName()=="KINGDEE")
                        {
                            ps.setLong(1, Constant.GLPostStatus.SUCCESS);	
                        }
                        else
                        {
                            ps.setLong(1, voucher.getPostStatusID());	                        	
                        }
                        ps.setString(2, voucher.getTransNo());		//凭证交易号
                        System.out.println("修改 TransNo = " + voucher.getTransNo() + " 的状态为：" + voucher.getPostStatusID());
                        ps.addBatch();
                        //如果有一个传输状态为失败，则这次更新操作则为失败
                        if (voucher.getPostStatusID() == Constant.GLPostStatus.FAILED)
                        {
                            bIsPassed = false;
                        }
                    }
                    ps.executeBatch();
                    ps.close();
                    ps = null;
                    conn.close();
                    conn = null;
                }
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
            e.printStackTrace();
            throw e;
        }
        return bIsPassed;
    }
    public boolean updatePostStatusHZ(GlSettingInfo info,Timestamp date) throws Exception{
    	
    	boolean bIsPassed = false;
    	Connection conn = null;
        PreparedStatement ps = null;
        StringBuffer sbRecord = new StringBuffer();
        int i = 1;
        try{
        	conn = Database.getConnection();
        	sbRecord.append(" update sett_glentry set npoststatusid = ? where nStatusId = ? \n ");
        	sbRecord.append(" and ncurrencyId=? and nOfficeID=? and dtExecute = ? ");
        	System.out.println("-----HZ------SQL:"+sbRecord.toString());
        	ps = conn.prepareStatement(sbRecord.toString());
        	ps.setLong(i++,Constant.SUCCESSFUL);
        	ps.setLong(i++,SETTConstant.TransactionStatus.CHECK);
        	ps.setLong(i++,info.getCurrencyID());
        	ps.setLong(i++,info.getOfficeID());
        	ps.setTimestamp(i++,date);
        	ps.executeUpdate();
        	ps.close();
            ps = null;
            conn.close();
            conn = null;
            bIsPassed = true;
        }
        catch (Exception e)
        {
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
            throw new RemoteException(e.getMessage());
        }
        finally
        {
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
        return bIsPassed;
    	
    }
    
    /*
     * @author lixr 浪潮财务软件接口 参数： 返回值：boolean bIsSuccess:是否成功；
     * 功能描述：修改业务系统中会计分录的状态； 流程描述： l 建立连接，开始修改会计分录状态事务； l
     * 根据参数，修改每笔会计分录的状态（数据库会计分录表Sett_GLEntry中nPostGLStatusID字段）； l
     * 提交修改会计分录状态事务，返回是否成功；
     */
    //更新内部财务数据的状态，浪潮接口专用
    public boolean updatePostStatus(long lOfficeID, long lCurrencyID) throws Exception
    {
        boolean bIsPassed = true;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        try
        {
        	System.out.println("--开始---浪潮接口------更新内部财务数据传输状态！");
            conn = Database.getConnection();
            sbRecord.append(" update sett_glentry set npoststatusid = "+Constant.GLPostStatus.SUCCESS+" where npoststatusid = "+Constant.GLPostStatus.DOING+" \n");
            sbRecord.append(" and nOfficeID="+lOfficeID+" and nCurrencyID= "+lCurrencyID);
            System.out.println("--------SQL:"+sbRecord.toString());
            ps = conn.prepareStatement(sbRecord.toString());
            
            ps.executeUpdate();

            ps.close();
            ps = null;
            conn.close();
            conn = null;
            System.out.println("--结束---浪潮接口------更新内部财务数据传输状态！");
        }
        catch (Exception e)
        {
            bIsPassed = false;
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
            throw new RemoteException(e.getMessage());
        }
        finally
        {
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
        return bIsPassed;
    }
    /*
     * 导入科目数据
     */
    //往自己的内部财务系统添加外部接口中得到的科目信息
    public boolean addSubject(long lOfficeID, long lCurrencyID, Collection collSubject) throws Exception
    {
        boolean bIsPassed = true;
        Connection conn = null;
        PreparedStatement psAddBatch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        long lID = -1;
        try
        {
        	//add by xfma 2009-02-12 以前导科目的方式是：先删除全部的科目，然后再insert
        	//后来由于考虑到效率的问题，改成了增量更新，但是增量更新是有弊端的，当财务的科目名称修改了以后
        	//在结算的系统中不能更新，因此今天又改回到最初的导科目方式：即先删除全部的科目，然后再insert
            if (collSubject != null)
            {
                conn = Database.getConnection();
                sbRecord.setLength(0);
                //根据办事处和币种删除相应的科目定义信息
                sbRecord.append(" delete from Sett_GlSubjectDefinition where nOfficeID=" + lOfficeID + " and nCurrencyID =" + lCurrencyID);

                ps = conn.prepareStatement(sbRecord.toString());
                ps.executeUpdate();
                ps.close();
                ps = null;
                sbRecord.setLength(0);
                
                //得到目前的最大号ID值
                sbRecord.append(" select nvl(max(ID)+1,1) ID from Sett_GlSubjectDefinition  ");
                Log.print(sbRecord.toString());
                ps = conn.prepareStatement(sbRecord.toString());
                rs = ps.executeQuery();
                while (rs != null && rs.next())
                {
                    lID = rs.getLong("ID");
                }
                rs.close();
                rs = null;
                ps.close();
                ps = null;
                
                sbRecord.setLength(0);
                //往科目定义表中插入由前一个大步骤得到的外部接口中的科目定义信息
                sbRecord.append(" insert into Sett_GlSubjectDefinition(");
                sbRecord.append(" ID,nOfficeID,nCurrencyID,sSegmentCode1,sSegmentCode2,sSegmentCode3,sSegmentCode4,sSegmentCode5,sSegmentCode6,sSegmentCode7,");
                sbRecord.append(" sSegmentName1,sSegmentName2,sSegmentName3,sSegmentName4,sSegmentName5,sSegmentName6,sSegmentName7,nSubjectType,nStatus,nIsleaf,nBalanceDirection,NISCustomer ");
                sbRecord.append(" ) values(");
                sbRecord.append(" ?,?,?,?,?,?,?,?,?,?,");
                sbRecord.append(" ?,?,?,?,?,?,?,?,?,?,?,? ");
                sbRecord.append(" ) ");
                psAddBatch = conn.prepareStatement(sbRecord.toString());
                
                Iterator it = collSubject.iterator();
                while (it.hasNext())
                {
                    GLSubjectDefinitionInfo subjectinfo = (GLSubjectDefinitionInfo) it.next();
                    System.out.println("getSegmentCode2:"+subjectinfo.getSegmentCode2());
                    System.out.println("getSegmentName2:"+subjectinfo.getSegmentName2());
                    int iIndex = 1;
                    psAddBatch.setLong(iIndex++, lID++);
                    psAddBatch.setLong(iIndex++, subjectinfo.getOfficeID());
                    psAddBatch.setLong(iIndex++, subjectinfo.getCurrencyID());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentCode1());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentCode2());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentCode3());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentCode4());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentCode5());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentCode6());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentCode7());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentName1());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentName2());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentName3());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentName4());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentName5());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentName6());
                    psAddBatch.setString(iIndex++, subjectinfo.getSegmentName7());
                    psAddBatch.setLong(iIndex++, subjectinfo.getSubjectType());
                    psAddBatch.setLong(iIndex++, subjectinfo.getStatusID());
                    if (subjectinfo.isLeaf())
                    {
                        psAddBatch.setLong(iIndex++, Constant.RecordStatus.VALID);
                    }
                    else
                    {
                        psAddBatch.setLong(iIndex++, Constant.RecordStatus.INVALID);
                    }
                    psAddBatch.setLong(iIndex++, subjectinfo.getBalanceDirection());
                    if (subjectinfo.isCustomer())
                    {
                        psAddBatch.setLong(iIndex++, Constant.RecordStatus.VALID);
                    }
                    else
                    {
                        psAddBatch.setLong(iIndex++, Constant.RecordStatus.INVALID);
                    }
                    
                    psAddBatch.addBatch();
                }
                System.out.println("开始导入");
                psAddBatch.executeBatch();
                System.out.println("导入完毕");
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        finally
        {
            try
            {
                if (psAddBatch != null)
                {
                	psAddBatch.close();
                	psAddBatch = null;
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
        return bIsPassed;
    }
    /** 
     * 导入科目余额
     * 修改记录：mzh_fu 2008/04/29 将Sett_GlBalance取ID的逻辑改为取序列，不再采用Max(id)方式
     */
    public boolean addSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate, Collection collBalance) throws Exception
    {
        boolean bIsPassed = true;
        Connection conn = null;
        PreparedStatement psAddBatch = null;
        PreparedStatement ps = null;
        
        StringBuffer sbRecord = new StringBuffer();
        
        try
        {
        	Log.print("进入到本系统里面添加科目余额的方法------开始!");
            if (collBalance != null)
            {
            	System.out.println("******导入科目余额开始******");
                conn = Database.getConnection();
                sbRecord.setLength(0);
                sbRecord.append(" delete from Sett_GlBalance where dtGlDate=to_date('" + DataFormat.getDateString(tsDate) + "','yyyy-mm-dd') and nOfficeID= " + lOfficeID + " and nCurrencyID=" + lCurrencyID);
                Log.print(sbRecord.toString());
                ps = conn.prepareStatement(sbRecord.toString());
                ps.executeUpdate();
                ps.close();
                ps = null;
                sbRecord.setLength(0);
                
                sbRecord.append(" insert into Sett_GlBalance( \n");
                sbRecord.append(" ID,dtGlDate,nOfficeID,nCurrencyID,sGlSubjectCode,nBalanceDirection,mDebitBalance,mCreditBalance,mDebitAmount,mCreditAmount \n");
                sbRecord.append(" ) values( SEQ_Sett_GlBalance.nextval ,?,?,?,?,?,?,?,?,?) \n");
                
                Log.print(sbRecord.toString());
                psAddBatch = conn.prepareStatement(sbRecord.toString());
                Iterator it = collBalance.iterator();
                int i=1;
                while (it.hasNext())
                {
                    GLBalanceInfo balanceinfo = (GLBalanceInfo) it.next();
                    int iIndex = 1;
                    psAddBatch.setTimestamp(iIndex++, balanceinfo.getGLDate());	//执行日
                    psAddBatch.setLong(iIndex++, balanceinfo.getOfficeID());	//办事处
                    psAddBatch.setLong(iIndex++, balanceinfo.getCurrencyID());	//币种
                    psAddBatch.setString(iIndex++, balanceinfo.getGLSubjectCode());		//科目代号
                    psAddBatch.setLong(iIndex++, balanceinfo.getBalanceDirection());	//余额方向
                    psAddBatch.setDouble(iIndex++, balanceinfo.getDebitBalance());		//借方余额
                    psAddBatch.setDouble(iIndex++, balanceinfo.getCreditBalance());		//贷方余额
                    psAddBatch.setDouble(iIndex++, balanceinfo.getDebitAmount());		//借方发生额
                    psAddBatch.setDouble(iIndex++, balanceinfo.getCreditAmount());		//贷方发生额
                    psAddBatch.addBatch();
                    
                    Log.print("==================================");
                    Log.print("---------内部添加的科目余额的个数"+i++);
                    Log.print("插入iTreasury中addSubjectAmount：" + balanceinfo.getGLSubjectCode());
                    
                    if(balanceinfo.getBalanceDirection()==1)
                    	Log.print(" 内部余额方向:借方"+balanceinfo.getBalanceDirection()+" 余额为："+balanceinfo.getDebitBalance());
                    else
                    	Log.print(" 内部余额方向:贷方"+balanceinfo.getBalanceDirection()+" 余额为："+balanceinfo.getCreditBalance());
                
                }
                psAddBatch.executeBatch();
                System.out.println("******导入科目余额结束******");
            }
            Log.print("进入到本系统里面添加科目余额的方法------结束!");
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        finally
        {
            try
            {
                if (psAddBatch != null)
                {
                	psAddBatch.close();
                	psAddBatch = null;
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
        return bIsPassed;
    }

    /*
     * 导入科目发生额及笔数
     */
    public boolean addSubjectAmount(long lOfficeID, long lCurrencyID, Timestamp tsDate, Collection collBalance) throws Exception
    {
        boolean bIsPassed = true;
        Connection conn = null;
        PreparedStatement psAddBatch = null;
        StringBuffer sbRecord = new StringBuffer();

        PreparedStatement ps = null;        
        ResultSet rs = null;
        PreparedStatement pssum = null;
        ResultSet rssum = null;
        long lID = -1;
        boolean bHaveTSubject = false;

        GlSettingInfo glSettingInfo=new GlSettingInfo();
		glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        try
        {
            if (collBalance != null)
            {
        		if(glSettingInfo.getGlName().equalsIgnoreCase("KINGDEE")) //金蝶接口
        		{
                    System.out.println("======开始--金蝶接口=======");
        			conn = Database.getConnection();
        			Log.print("***********************************************start");
        			sbRecord.setLength(0);
	                sbRecord.append(" update Sett_GlBalance \n");
	                sbRecord.append(" set mDebitAmount=?,mCreditAmount=?,nDebitNumber=?,nCreditNumber=? where to_char(dtGlDate,'yyyy-mm-dd') = ? and sGlSubjectCode=? \n");
	                Log.print("开始SettGLWithinDao====" + sbRecord.toString());
	                psAddBatch = conn.prepareStatement(sbRecord.toString());
	                Iterator it = collBalance.iterator();
	                while (it.hasNext())
	                {
	                    GLBalanceInfo balanceinfo = (GLBalanceInfo) it.next();
	                    int iIndex = 1;
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getDebitAmount());
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getCreditAmount());
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getDebitNumber());
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getCreditNumber());
	                    psAddBatch.setString(iIndex++, DataFormat.getDateString(balanceinfo.getGLDate()));
	                    psAddBatch.setString(iIndex++, balanceinfo.getGLSubjectCode());
	                    psAddBatch.addBatch();
	                    Log.print("插入iTreasury中addSubjectAmount：" + balanceinfo.getGLSubjectCode() + "==getGLDate=" + balanceinfo.getGLDate() + "==getDebitAmount=" + balanceinfo.getDebitAmount() + "====getCreditAmount=" + balanceinfo.getCreditAmount());
	                    //System.out.println(balanceinfo.getGLSubjectCode() + ": getGLDate=" + balanceinfo.getGLDate() + ";  getDebitAmount=" + balanceinfo.getDebitAmount() + "; getCreditNumber=" + balanceinfo.getCreditNumber());
	                    //System.out.println(balanceinfo.getGLSubjectCode() + ": getGLDate=" + balanceinfo.getGLDate() + ";  getDebitNumber=" + balanceinfo.getDebitNumber() + "; getCreditNumber=" + balanceinfo.getCreditNumber());
	                }
	                psAddBatch.executeBatch();
	                psAddBatch.close();
	                psAddBatch = null;
	
	                Log.print("结束SettGLWithinDao");
	                Log.print("***********************************************end");
	                conn.close();
	                conn = null;
                    System.out.println("======结束--金蝶接口=======");
        		}
        		else //其它接口
        		{
                    System.out.println("======开始--其它接口=======");
	                conn = Database.getConnection();
	                Log.print("***********************************************start");
	                sbRecord.setLength(0);
	                sbRecord.append(" update Sett_GlBalance \n");
	                sbRecord.append(" set mDebitAmount=?,mCreditAmount=?,nDebitNumber=?,nCreditNumber=? where  to_char(dtGlDate,'yyyy-mm-dd') = ? and sGlSubjectCode=? \n");
	                Log.print("开始SettGLWithinDao====" + sbRecord.toString());
	                psAddBatch = conn.prepareStatement(sbRecord.toString());
	                Iterator it = collBalance.iterator();
	                while (it.hasNext())
	                {
	                    GLBalanceInfo balanceinfo = (GLBalanceInfo) it.next();
	                    int iIndex = 1;
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getDebitAmount());
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getCreditAmount());
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getDebitNumber());
	                    psAddBatch.setDouble(iIndex++, balanceinfo.getCreditNumber());
	                    psAddBatch.setString(iIndex++, DataFormat.getDateString(balanceinfo.getGLDate()));
	                    psAddBatch.setString(iIndex++, balanceinfo.getGLSubjectCode());
	                    psAddBatch.addBatch();
	                    Log.print("插入iTreasury中addSubjectAmount：" + balanceinfo.getGLSubjectCode() + "==getGLDate=" + balanceinfo.getGLDate() + "==getDebitAmount=" + balanceinfo.getDebitAmount() + "====getCreditAmount=" + balanceinfo.getCreditAmount());
	                }
	                psAddBatch.executeBatch();
	                psAddBatch.close();
	                psAddBatch = null;
	                sbRecord.setLength(0);
	                sbRecord.append(" update Sett_GlBalance \n");
	                sbRecord.append(" set mDebitAmount=?,mCreditAmount=? where  to_char(dtGlDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(tsDate) + "' and sGlSubjectCode = ? \n");
	                psAddBatch = conn.prepareStatement(sbRecord.toString());
	                Log.print("开始TTTTTTTTTTTTTT====" + sbRecord.toString());
	                sbRecord.setLength(0);
	                sbRecord.append(" select *   \n");
	                sbRecord.append(" from Sett_GlBalance \n");
	                sbRecord.append(" where to_char(dtGlDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(tsDate) + "' and sglsubjectcode like '001.T.5%.T.T.T.T' \n");
	                ps = conn.prepareStatement(sbRecord.toString());
	                Log.print("====" + sbRecord.toString());
	                rs = ps.executeQuery();
	                while (rs != null && rs.next())
	                {
	                    String sGlSubjectCode = rs.getString("sGlSubjectCode");
	                    Log.print("====" + sGlSubjectCode);
	                    sbRecord.setLength(0);
	                    sbRecord.append(" select sum(mDebitAmount) mDebitAmount,sum(mCreditAmount) mCreditAmount   \n");
	                    sbRecord.append(" from Sett_GlBalance \n");
	                    sbRecord.append(" where to_char(dtGlDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(tsDate) + "' and sglsubjectcode like '" + (sGlSubjectCode.substring(0, sGlSubjectCode.indexOf(".T.T.T.T")) + "%").replace('T', '%') + "' \n");
	                    pssum = conn.prepareStatement(sbRecord.toString());
	                    rssum = pssum.executeQuery();
	                    if (rssum != null && rssum.next())
	                    {
	                        bHaveTSubject = true;
	                        int iIndex = 1;
	                        psAddBatch.setDouble(iIndex++, rssum.getDouble("mDebitAmount"));
	                        psAddBatch.setDouble(iIndex++, rssum.getDouble("mCreditAmount"));
	                        psAddBatch.setString(iIndex++, sGlSubjectCode);
	                        psAddBatch.addBatch();
	                        Log.print(sbRecord.toString());
	                        Log.print("TTTTTTTTTTTTTT====" + sGlSubjectCode + "==" + rssum.getDouble("mDebitAmount") + "===" + rssum.getDouble("mCreditAmount"));
	                    }
	                    rssum.close();
	                    rssum = null;
	                    pssum.close();
	                    pssum = null;
	                }
	                rs.close();
	                rs = null;
	                ps.close();
	                ps = null;
	                if (bHaveTSubject)
	                {
	                    psAddBatch.executeBatch();
	                }
	                psAddBatch.close();
	                psAddBatch = null;
	                Log.print("结束SettGLWithinDao");
	                Log.print("***********************************************end");
	                conn.close();
	                conn = null;
	
	                System.out.println("======结束--其它接口=======");    
        		}
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            bIsPassed = false;
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }
    
    /**
     * 说明：导入外部客户信息（金蝶专用--t_organization客户表）
     * 		增量导入，根据客户编号SExtAcctNo判断
     * @author liuqing
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     * @throws Exception
     */
    public boolean addExternalAccount(long lOfficeID, long lCurrencyID, Collection collExternalAccount) throws Exception
    {
        boolean bIsPassed = true;
        Connection conn = null;
        PreparedStatement psAddBatch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        long lID = -1;

        try
        {
            System.out.println("开始检验结果集！");
            
            if (collExternalAccount != null)
            {
                conn = Database.getConnection();

                sbRecord.setLength(0);
                //得到目前的最大号ID值
                sbRecord.append(" select nvl(max(ID)+1,1) ID from Sett_ExternalAccount ");
                Log.print(sbRecord.toString());
                ps = conn.prepareStatement(sbRecord.toString());
                rs = ps.executeQuery();
                while (rs != null && rs.next())
                {
                    lID = rs.getLong("ID");
                    System.out.println("客户表MaxID:"+lID);
                }
//                rs.close();
//                rs = null;
//                ps.close();
//                ps = null;
                sbRecord.setLength(0);
                sbRecord.append(" Insert into Sett_ExternalAccount(");
                sbRecord.append(" ID,nOfficeID,SExtAcctNo,SExtAcctName,SBankName,SProvince,SCity ");
                sbRecord.append(" ) values(");
                sbRecord.append(" ?,?,?,?,?,?,? ");
                sbRecord.append(" ) ");
                psAddBatch = conn.prepareStatement(sbRecord.toString());
                Iterator it = collExternalAccount.iterator();
                while (it.hasNext())
                {            
                	GLKingDeeExtAcctInfo extacctinfo = (GLKingDeeExtAcctInfo) it.next();
                	//判断，按增量导入
                    sbRecord.setLength(0);
                    sbRecord.append(" select SExtAcctNo from Sett_ExternalAccount where SExtAcctNo = '" + extacctinfo.getFNumber() + "'");
                    //System.out.println(sbRecord.toString());
                    ps = conn.prepareStatement(sbRecord.toString());
                    rs = ps.executeQuery();
                    if (rs != null && rs.next())
                    {
                    	System.out.println(extacctinfo.getFName()+"已存在");
                        continue;
                    }
                    System.out.println("SExtAcctNo:"+extacctinfo.getFNumber()+"===SExtAcctName:"+extacctinfo.getFName());
                    int iIndex = 1;
                    psAddBatch.setLong(iIndex++, lID++);
                    psAddBatch.setLong(iIndex++, lOfficeID);
                    psAddBatch.setString(iIndex++, extacctinfo.getFNumber());
                    psAddBatch.setString(iIndex++, extacctinfo.getFName());
                    psAddBatch.setString(iIndex++, extacctinfo.getFBank());
                    psAddBatch.setString(iIndex++, extacctinfo.getFProvince());
                    psAddBatch.setString(iIndex++, extacctinfo.getFCity());
                    
                    psAddBatch.addBatch();
                	
                }
                System.out.println("开始导入");
                psAddBatch.executeBatch();     
                System.out.println("=====导入数量："+psAddBatch.getUpdateCount());
                System.out.println("导入完毕");
                psAddBatch.close();
                psAddBatch = null;

                rs.close();
                rs = null;
                ps.close();
                ps = null;

                conn.close();
                conn = null;
                
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
            throw new RemoteException(e.getMessage());
        }
        finally
        {
            try
            {
                if (psAddBatch != null)
                {
                	psAddBatch.close();
                	psAddBatch = null;
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
        return bIsPassed;
    }
    
    /*
     * 
     * 
     */
    public boolean convertGlSubject(long lOfficeID, long lCurrencyID, String strConvert) throws Exception
    {
        boolean bIsPassed = true;
        Connection conn = null;
        PreparedStatement ps = null;
        StringBuffer sbRecord = new StringBuffer();
        try
        {
            conn = Database.getConnection();
            sbRecord.append("call Sett_ConvertGlSubject("+ lOfficeID +","+ lCurrencyID +",'"+ strConvert +"')");
            System.out.println("--------SQL:"+sbRecord.toString());
            ps = conn.prepareStatement(sbRecord.toString());
            ps.execute();

            ps.close();
            ps = null;
            conn.close();
            conn = null;
        }
        catch (Exception e)
        {
            bIsPassed = false;
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
            throw new RemoteException(e.getMessage());
        }
        finally
        {
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
        return bIsPassed;
    }
    /**	
     * 查询会计凭证信息
     * @author Kloud
     * @create date 2007-08-15
     * @param strExecuteDate
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     */
    public Collection getVouches(String strExecuteDate,long lOfficeID, long lCurrencyID)
    {
    	System.out.println("[ Kloud ] 進入SettGLWithinDao().getVouches()方法成功");
    	String strSql = "";
    	ResultSet rs = null;
    	PreparedStatement ps = null;
    	Collection coll = null;
    	Connection conn = null;
    
    	try {
			conn = Database.getConnection();	    
			//select 
	    	strSql = "select ";
	    	strSql += "e.nofficeid officeid,e.ncurrencyid currencyid, e.ssubjectcode subjectcode,";
	    	strSql += "e.stransno transno,e.ntransactiontypeid transactiontype,e.ntransdirection transdirection,";
	    	strSql += "e.mamount amount,e.dtexecute executedate,e.dtintereststart intereststartdate,";
	    	strSql += "e.sabstract abstract,e.smulticode multicode,e.ninputuserid inputuserid,e.ncheckuserid checkuserid,";
	    	strSql += "e.nstatusid statusid,e.ngroup ngroup,e.ntype ntype,e.npoststatusid poststatusid,";
	    	
	    	strSql += "s.ssegmentcode1 segcode1,s.ssegmentcode2 segcode2,s.ssegmentcode3 segcode3,";
	    	strSql += "s.ssegmentcode4 segcode4,s.ssegmentcode5 segcode5,s.ssegmentcode6 segcode6,";
	    	strSql += "s.ssegmentcode7 segcode7,s.ssegmentname1 segname1,s.ssegmentname2 segname2,";
	    	strSql += "s.ssegmentname3 segname3,s.ssegmentname4 segname4,s.ssegmentname5 segname5,";
	    	strSql += "s.ssegmentname6 segname6,s.ssegmentname7 segname7,s.nsubjecttype subjecttype,";
	    	strSql += "s.nisleaf isleaf,s.nisroot isroot,s.nparentsubjectid parentsubjectid,";
	    	strSql += "s.nbalancedirection balancedire,s.namountdirection amountdire,";
	    	strSql += "s.nstatus status,s.nledertype ledertype,s.nsecuritylevel securitylevel,";
	    	strSql += "s.nusescope usescope,s.nflag flag,s.dtvaliddate validdate";
	    	// from 
	    	strSql += " from sett_glentry e,sett_glsubjectdefinition s ";
	    	//where 
	    	strSql += " where e.ssubjectcode = s.ssegmentcode1||'.'||s.ssegmentcode2||'.'||s.ssegmentcode3||'.'||s.ssegmentcode4||'.'||s.ssegmentcode5||'.'||s.ssegmentcode6||'.'||s.ssegmentcode7 ";
	    	strSql += " and e.nofficeid = " + lOfficeID + " and e.ncurrencyid = " + lCurrencyID + " ";
	    	strSql += " and e.dtexecute = to_date('"+strExecuteDate+"','yyyy-MM-dd') and e.nstatusid <>0 and s.nstatus = 1";
	    	Log.print("[ Kloud ] getVouchByFields.strSql = " + strSql);
	    	
	    	ps = conn.prepareStatement(strSql);
	    	rs = ps.executeQuery();
	    	if(rs != null) {
	    		coll = new ArrayList();
		    	while(rs.next()) {
		    		// sett_glentry 表
		    		GLVouchInfo vouch = new GLVouchInfo();
		    		vouch.setLOfficeID(rs.getLong("officeid"));
		    		vouch.setLCurrencyID(rs.getLong("currencyid"));
		    		vouch.setStrSubjectCode(rs.getString("subjectcode"));
		    		vouch.setStrTransNO(rs.getString("transno"));
		    		vouch.setLTransactionTypeID(rs.getLong("transactiontype"));
		    		vouch.setLTransDirection(rs.getLong("transdirection"));
		    		vouch.setDbMamount(rs.getDouble("amount"));
		    		vouch.setDtExecute(rs.getTimestamp("executedate"));
		    		vouch.setDtInterestStart(rs.getTimestamp("intereststartdate"));
		    		//vouch.setStrAbstract(rs.getString("abstract"));
		    		//如果摘要里有逗号(全角?半角?),oracle导入这个文件的时候是不是会出错?
					//hyluo 2008-12-19
		    		/////////////////////////////
		    		if(rs.getString("abstract") != null && rs.getString("abstract").length() > 0){
		    			vouch.setStrAbstract(rs.getString("abstract").replaceAll(",", ";"));//半角逗号转为半角分号
		    		}else{
		    			vouch.setStrAbstract(rs.getString("abstract"));
		    		}
		    		/////////////////////////////
		    		vouch.setStrMulticode(rs.getString("multicode"));
		    		vouch.setLInputUserID(rs.getLong("inputuserid"));
		    		vouch.setLCheckUserID(rs.getLong("checkuserid"));
		    		vouch.setLStatusID(rs.getLong("statusid"));
		    		vouch.setLGroup(rs.getLong("ngroup"));
		    		vouch.setLType(rs.getLong("ntype"));
		    		vouch.setLPostStatusID(rs.getLong("poststatusid"));
		    		
		    		//sett_glsubjectdefinition
		    		vouch.setStrSegCode1(rs.getString("segcode1"));
		    		vouch.setStrSegCode2(rs.getString("segcode2"));
		    		vouch.setStrSegCode3(rs.getString("segcode3"));
		    		vouch.setStrSegCode4(rs.getString("segcode4"));
		    		vouch.setStrSegCode5(rs.getString("segcode5"));
		    		vouch.setStrSegCode6(rs.getString("segcode6"));
		    		vouch.setStrSegCode7(rs.getString("segcode7"));
		    		vouch.setStrSegName1(rs.getString("segname1"));
		    		vouch.setStrSegName2(rs.getString("segname2"));
		    		vouch.setStrSegName3(rs.getString("segname3"));
		    		vouch.setStrSegName4(rs.getString("segname4"));
		    		vouch.setStrSegName5(rs.getString("segname5"));
		    		vouch.setStrSegName6(rs.getString("segname6"));
		    		vouch.setStrSegName7(rs.getString("segname7"));
		    		vouch.setLSubjectType(rs.getLong("subjecttype"));
		    		vouch.setLIsLeaf(rs.getLong("isleaf"));
		    		vouch.setLIsRoot(rs.getLong("isroot"));
		    		vouch.setLParentSubjectID(rs.getLong("parentsubjectid"));
		    		vouch.setLBalanceDirection(rs.getLong("balancedire"));
		    		vouch.setLAmountDirection(rs.getLong("amountdire"));
		    		vouch.setLStatus(rs.getLong("status"));
		    		vouch.setLLedertype(rs.getLong("ledertype"));
		    		vouch.setLSecurityLevel(rs.getLong("securitylevel"));
		    		vouch.setLUseScope(rs.getLong("usescope"));
		    		vouch.setLFlag(rs.getLong("flag"));
		    		vouch.setDtValidDate(rs.getTimestamp("validdate"));
		    		
		    		coll.add(vouch);
		    	}
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("[ Kloud ] 退出SettGLWithinDao().getVouches()方法成功");	
    	return coll;
    }     
}