/*
 * Created on 2004-2-11
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.oracle;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Hashtable;


import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.oracle_cpf.GLOracleFinBean_cpf;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

;
/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLOracleFinBean extends GLExtendBaseBean
{
    public static void main(String[] args)
    {
    }

    /*
     * @author yychen GLSystemBaseBean.postGLVoucher()：导出会计凭证 参数：Collection
     * collGlVoucher: FindGLVoucherBaseBean. findGLVoucherByCondition ()返回的凭证集合；
     * 返回值：boolean bIsSuccess:是否成功； 功能描述：将业务系统的凭证信息生成XML文件，然后传出至EAI。 流程描述： l
     * 调用U850GLBean.buildGlVoucherXML(),将凭证信息集合转化成XML文件； l
     * 调用U850GLBean.triggerPostGlVoucher()，将XML文件传至EAI系统； l 返回是否成功!
     */
    public Collection postGLVoucher(Collection collGlVoucher) throws Exception
    {
        try
        {
        									//将办事处和币种设置为-1以上它各不到
            postGLVoucherToOracleFin(collGlVoucher,-1,-1);
            triggerPostGLVoucher(-1,-1);
            collGlVoucher = getPostGlVoucherBackInfo(collGlVoucher,-1,-1);
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }
    
    /*
     * @author yychen GLSystemBaseBean.postGLVoucher()：导出会计凭证 参数：Collection
     * collGlVoucher: FindGLVoucherBaseBean. findGLVoucherByCondition ()返回的凭证集合；
     * 返回值：boolean bIsSuccess:是否成功； 功能描述：将业务系统的凭证信息生成XML文件，然后传出至EAI。 流程描述： l
     * 调用U850GLBean.buildGlVoucherXML(),将凭证信息集合转化成XML文件； l
     * 调用U850GLBean.triggerPostGlVoucher()，将XML文件传至EAI系统； l 返回是否成功!
     */
    public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
    {
        try
        {
            postGLVoucherToOracleFin(collGlVoucher,lOfficeID,lCurrencyID);	//判断对方返回信息是否完毕
            triggerPostGLVoucher(lOfficeID,lCurrencyID);	//判断对方返回信息是否完毕
            collGlVoucher = getPostGlVoucherBackInfo(collGlVoucher,lOfficeID,lCurrencyID);
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    /*
     * U850GLBean. getPostGlVoucherBackInfo () 参数：； 返回值：Collection
     * collVoucher:解析EAI返回信息所得到的信息集合； 功能描述：循环等待并解析EAI返回信息； 流程描述： l 循环开始； l
     * 调用U850GLBean .isCompleted()判断EAI是否处理完毕并返回信息； l 如果处理完毕，则调用U850GLBean
     * .resolveGLSubjectXML()，解析返回的信息，循环结束； l 如果没有处理完毕，则继续循环等待； l 返回解析所得的信息集合；
     */
    private Collection getPostGlVoucherBackInfo(Collection collGlVoucher,long lOfficeID,long lCurrencyID) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.DOING;
        try
        {
            long lPostCount = 0;
            while (lDealStatusID == Constant.ShutDownStatus.DOING)
            {
                lPostCount++;
                if (lPostCount > CloseSystemMain.getExportMaxSecond())
                {
                    lDealStatusID = Constant.ShutDownStatus.FAIL;
                    break;
                }
                //等待两秒钟然后再判断数据是否更成功，主要是怕触发器程序执行的功能没有执行完毕
                Thread.currentThread().sleep(2000);
                lDealStatusID = isCompleted(lOfficeID,lCurrencyID);
            }
            if (collGlVoucher != null)
            {
                Iterator it = collGlVoucher.iterator();
                while (it.hasNext())
                {
                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    if (lDealStatusID == Constant.ShutDownStatus.SUCCESS)
                    {
                        voucher.setPostStatusID(Constant.GLPostStatus.SUCCESS);
                    }
                    else
                    {
                        voucher.setPostStatusID(Constant.GLPostStatus.FAILED);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    /*
     * ? U850GLBean.getGLSubject() :获取科目组合信息 返回值：科目组合信息集合； 功能描述：从总账核算系统获取科目组合；
     * 流程描述： l
     * 调用U850GLBean.buildGLSubjectXML()，将查询科目组合的指令生成EAI参数所需XML文件(具体见附件EAI中标准数据文件描述)；
     * l 调用JavaBean.triggerQueryGLSubject()将XML发送到EAI，启动EAI进行科目组合查询；
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
            conn = Database.getConnection();
            //conn=GLOracleFinBean.getOracleConnection(lOfficeID,lCurrencyID);
            
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  SELECT GCC.CODE_COMBINATION_ID, \n ");
            sbSQL.append("  	   GCC.SEGMENT1,GCC.SEGMENT2,GCC.SEGMENT3,GCC.SEGMENT4, \n ");
            sbSQL.append("  GCC.SEGMENT5,GCC.SEGMENT6,GCC.SEGMENT7,ACCOUNT_TYPE,  \n ");
            sbSQL.append("    S1.DESCRIPTION DESCRIPTION1,S2.DESCRIPTION DESCRIPTION2,S3.DESCRIPTION DESCRIPTION3,  \n ");
            sbSQL.append("     S4.DESCRIPTION DESCRIPTION4,S5.DESCRIPTION DESCRIPTION5,S6.DESCRIPTION DESCRIPTION6  \n ");
            sbSQL.append("     ,S7.DESCRIPTION DESCRIPTION7 \n ");
            sbSQL.append("  FROM GL_CODE_COMBINATIONS@ITREASURY_T GCC,GL_SETS_OF_BOOKS@ITREASURY_T GSOB, \n ");
            sbSQL.append("  (SELECT FVS.FLEX_VALUE_SET_ID,FVS.FLEX_VALUE_SET_NAME,FV.FLEX_VALUE,VT.DESCRIPTION \n ");
            sbSQL.append("  FROM FND_FLEX_VALUE_SETS@ITREASURY_T FVS,FND_FLEX_VALUES@ITREASURY_T FV,FND_FLEX_VALUES_TL@ITREASURY_T VT \n ");
            sbSQL.append("  WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID \n ");
            sbSQL.append("  AND	  FV.FLEX_VALUE_ID = VT.FLEX_VALUE_ID \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = VT.SOURCE_LANG \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = 'ZHS' \n ");
            sbSQL.append("  AND	  FV.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  ) S1, \n ");
            sbSQL.append("  (SELECT FVS.FLEX_VALUE_SET_ID,FVS.FLEX_VALUE_SET_NAME,FV.FLEX_VALUE,VT.DESCRIPTION \n ");
            sbSQL.append("  FROM FND_FLEX_VALUE_SETS@ITREASURY_T FVS,FND_FLEX_VALUES@ITREASURY_T FV,FND_FLEX_VALUES_TL@ITREASURY_T VT \n ");
            sbSQL.append("  WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID \n ");
            sbSQL.append("  AND	  FV.FLEX_VALUE_ID = VT.FLEX_VALUE_ID \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = VT.SOURCE_LANG \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = 'ZHS' \n ");
            sbSQL.append("  AND	  FV.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  ) S2, \n ");
            sbSQL.append("  (SELECT FVS.FLEX_VALUE_SET_ID,FVS.FLEX_VALUE_SET_NAME,FV.FLEX_VALUE,VT.DESCRIPTION \n ");
            sbSQL.append("  FROM FND_FLEX_VALUE_SETS@ITREASURY_T FVS,FND_FLEX_VALUES@ITREASURY_T FV,FND_FLEX_VALUES_TL@ITREASURY_T VT \n ");
            sbSQL.append("  WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID \n ");
            sbSQL.append("  AND	  FV.FLEX_VALUE_ID = VT.FLEX_VALUE_ID \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = VT.SOURCE_LANG \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = 'ZHS' \n ");
            sbSQL.append("  AND	  FV.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  ) S3, \n ");
            sbSQL.append("  (SELECT FVS.FLEX_VALUE_SET_ID,FVS.FLEX_VALUE_SET_NAME,FV.FLEX_VALUE,VT.DESCRIPTION \n ");
            sbSQL.append("  FROM FND_FLEX_VALUE_SETS@ITREASURY_T FVS,FND_FLEX_VALUES@ITREASURY_T FV,FND_FLEX_VALUES_TL@ITREASURY_T VT \n ");
            sbSQL.append("  WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID \n ");
            sbSQL.append("  AND	  FV.FLEX_VALUE_ID = VT.FLEX_VALUE_ID \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = VT.SOURCE_LANG \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = 'ZHS' \n ");
            sbSQL.append("  AND	  FV.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  ) S4, \n ");
            sbSQL.append("  (SELECT FVS.FLEX_VALUE_SET_ID,FVS.FLEX_VALUE_SET_NAME,FV.FLEX_VALUE,VT.DESCRIPTION \n ");
            sbSQL.append("  FROM FND_FLEX_VALUE_SETS@ITREASURY_T FVS,FND_FLEX_VALUES@ITREASURY_T FV,FND_FLEX_VALUES_TL@ITREASURY_T VT \n ");
            sbSQL.append("  WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID \n ");
            sbSQL.append("  AND	  FV.FLEX_VALUE_ID = VT.FLEX_VALUE_ID \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = VT.SOURCE_LANG \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = 'ZHS' \n ");
            sbSQL.append("  AND	  FV.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  ) S5, \n ");
            sbSQL.append("  (SELECT FVS.FLEX_VALUE_SET_ID,FVS.FLEX_VALUE_SET_NAME,FV.FLEX_VALUE,VT.DESCRIPTION \n ");
            sbSQL.append("  FROM FND_FLEX_VALUE_SETS@ITREASURY_T FVS,FND_FLEX_VALUES@ITREASURY_T FV,FND_FLEX_VALUES_TL@ITREASURY_T VT \n ");
            sbSQL.append("  WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID \n ");
            sbSQL.append("  AND	  FV.FLEX_VALUE_ID = VT.FLEX_VALUE_ID \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = VT.SOURCE_LANG \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = 'ZHS' \n ");
            sbSQL.append("  AND	  FV.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  ) S6, \n ");
            sbSQL.append("  (SELECT FVS.FLEX_VALUE_SET_ID,FVS.FLEX_VALUE_SET_NAME,FV.FLEX_VALUE,VT.DESCRIPTION \n ");
            sbSQL.append("  FROM FND_FLEX_VALUE_SETS@ITREASURY_T FVS,FND_FLEX_VALUES@ITREASURY_T FV,FND_FLEX_VALUES_TL@ITREASURY_T VT \n ");
            sbSQL.append("  WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID \n ");
            sbSQL.append("  AND	  FV.FLEX_VALUE_ID = VT.FLEX_VALUE_ID \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = VT.SOURCE_LANG \n ");
            sbSQL.append("  AND	  VT.LANGUAGE = 'ZHS' \n ");
            sbSQL.append("  AND	  FV.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  ) S7 \n ");
            sbSQL.append("  WHERE GCC.CHART_OF_ACCOUNTS_ID = GSOB.CHART_OF_ACCOUNTS_ID \n ");
            sbSQL.append("  AND	  GCC.ENABLED_FLAG = 'Y' \n ");
            sbSQL.append("  AND	  GCC.SUMMARY_FLAG = 'N' \n ");
            sbSQL.append("  AND	  GSOB.SET_OF_BOOKS_ID =  " + getSetOfBooksID(lOfficeID) + " \n ");
            sbSQL.append("  AND	  GCC.SEGMENT1 = DECODE('" + getCurrencyCode(lCurrencyID) + "','CNY','001','USD','002') \n ");
            sbSQL.append("  AND	  S1.FLEX_VALUE = GCC.SEGMENT1 \n ");
            sbSQL.append("  AND	  S2.FLEX_VALUE = GCC.SEGMENT2 \n ");
            sbSQL.append("  AND	  S3.FLEX_VALUE = GCC.SEGMENT3 \n ");
            sbSQL.append("  AND	  S4.FLEX_VALUE = GCC.SEGMENT4 \n ");
            sbSQL.append("  AND	  S5.FLEX_VALUE = GCC.SEGMENT5 \n ");
            sbSQL.append("  AND	  S6.FLEX_VALUE = GCC.SEGMENT6 \n ");
            sbSQL.append("  AND	  S7.FLEX_VALUE = GCC.SEGMENT7 \n ");
            sbSQL.append("  AND	  S1.FLEX_VALUE_SET_NAME = '公司段值集' \n ");
            sbSQL.append("  AND	  S2.FLEX_VALUE_SET_NAME = '成本中心段值集' \n ");
            sbSQL.append("  AND	  S3.FLEX_VALUE_SET_NAME = '科目段值集' \n ");
            sbSQL.append("  AND	  S4.FLEX_VALUE_SET_NAME = '产品段值集' \n ");
            sbSQL.append("  AND	  S5.FLEX_VALUE_SET_NAME = '参考段值集' \n ");
            sbSQL.append("  AND	  S6.FLEX_VALUE_SET_NAME = '备用段1值集' \n ");
            sbSQL.append("  AND	  S7.FLEX_VALUE_SET_NAME = '备用段2值集' \n ");
            sbSQL.append("  ORDER BY GCC.CODE_COMBINATION_ID \n ");
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                GLSubjectDefinitionInfo info = new GLSubjectDefinitionInfo();
                info.setOfficeID(lOfficeID);
                info.setCurrencyID(lCurrencyID);
                info.setSegmentCode1(rs.getString("Segment1"));
                info.setSegmentCode2(rs.getString("Segment2"));
                info.setSegmentCode3(rs.getString("Segment3"));
                info.setSegmentCode4(rs.getString("Segment4"));
                info.setSegmentCode5(rs.getString("Segment5"));
                info.setSegmentCode6(rs.getString("Segment6"));
                info.setSegmentCode7(rs.getString("Segment7"));
                info.setSegmentName1(rs.getString("DESCRIPTION1"));
                info.setSegmentName2(rs.getString("DESCRIPTION2"));
                info.setSegmentName3(rs.getString("DESCRIPTION3"));
                info.setSegmentName4(rs.getString("DESCRIPTION4"));
                info.setSegmentName5(rs.getString("DESCRIPTION5"));
                info.setSegmentName6(rs.getString("DESCRIPTION6"));
                info.setSegmentName7(rs.getString("DESCRIPTION7"));
                info.setSubjectType(getSubjectType(rs.getString("Account_Type")));
                info.setLeaf(true);
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

    /*
     * ? getGLSubjectBalance() :获取科目余额 返回值：科目余额信息集合； 功能描述：从总账核算系统获取科目余额； 流程描述： l
     * 调用U850GLBean.buildGLSubjectXML()，将查询科目组合的指令生成EAI参数所需XML文件(具体见附件EAI中标准数据文件描述)；
     * l 调用JavaBean.triggerQueryGLSubject()将XML发送到EAI，启动EAI进行科目组合查询；
     */
    public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
    {
        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            /////更新科目号
            conn = Database.getConnection();
            //conn=GLOracleFinBean.getOracleConnection(lOfficeID,lCurrencyID);
            
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  SELECT NVL(NVL(DAY.CURR_BLA,0)+NVL(PERIOD.END_BALANCE,0),0)  BALANCE,LOOKUP.DESCRIPTION  ACCOUNT_TYPE, \n ");
            sbSQL.append("  CODE.SEGMENT1||'.'||CODE.SEGMENT2||'.'||CODE.SEGMENT3||'.'||CODE.SEGMENT4||'.'||CODE.SEGMENT5||'.'||CODE.SEGMENT6||'.'||CODE.SEGMENT7 sSubject \n ");
            sbSQL.append("  FROM 	 \n ");
            sbSQL.append("  		  (  \n ");
            sbSQL.append("  		   SELECT CODE.CODE_COMBINATION_ID,NVL(A.CURR_BLA,0) CURR_BLA  \n ");
            sbSQL.append("  		   FROM \n ");
            sbSQL.append("  				   (SELECT GJL.CODE_COMBINATION_ID, SUM(NVL(GJL.ENTERED_DR,0)-NVL(GJL.ENTERED_CR,0))  CURR_BLA  \n ");
            sbSQL.append("  							FROM GL_JE_LINES@ITREASURY_T    GJL  \n ");
            sbSQL.append("  									 ,GL_JE_HEADERS@ITREASURY_T   GJH \n ");
            sbSQL.append("  							WHERE TO_CHAR(GJH.POSTED_DATE,'yyyymmdd') >=  TO_CHAR(TRUNC(TO_DATE('" + DataFormat.getDateString(tsDate) + "','YYYY-MM-DD'),'month'),'yyyymmdd') \n ");
            sbSQL.append("  									AND   TO_CHAR(GJH.POSTED_DATE,'yyyymmdd') <=TO_CHAR(TO_DATE('" + DataFormat.getDateString(tsDate) + "','YYYY-MM-DD'),'YYYYMMDD') \n ");
            sbSQL.append("  									AND		 GJL.JE_HEADER_ID = GJH.JE_HEADER_ID  \n ");
            sbSQL.append("  									AND   GJL.SET_OF_BOOKS_ID=   " + getSetOfBooksID(lOfficeID) + "  \n ");
            sbSQL.append("  									AND   GJH.CURRENCY_CODE= '" + getCurrencyCode(lCurrencyID) + "'  \n ");
            sbSQL.append("  							GROUP BY GJL.CODE_COMBINATION_ID	 \n ");
            sbSQL.append("  					)    A, \n ");
            sbSQL.append("  					GL_CODE_COMBINATIONS@ITREASURY_T CODE \n ");
            sbSQL.append("  					WHERE CODE.CODE_COMBINATION_ID = A.CODE_COMBINATION_ID(+)   \n ");
            sbSQL.append("  			) DAY,		  \n ");
            sbSQL.append("  			( \n ");
            sbSQL.append("  				  SELECT CODE.CODE_COMBINATION_ID,NVL(B.END_BALANCE,0) END_BALANCE  \n ");
            sbSQL.append("  				  FROM \n ");
            sbSQL.append("  				   (	   SELECT GB.CODE_COMBINATION_ID, SUM(GB.BEGIN_BALANCE_DR-GB.BEGIN_BALANCE_CR+ GB.PERIOD_NET_DR-   GB.PERIOD_NET_CR) END_BALANCE  \n ");
            sbSQL.append("  							FROM 	GL_BALANCES@ITREASURY_T   GB \n ");
            sbSQL.append("  							WHERE GB.PERIOD_YEAR = TO_CHAR(ADD_MONTHS(TO_DATE('" + DataFormat.getDateString(tsDate) + "','YYYY-MM-DD'),-1),'YYYY') \n ");
            sbSQL.append("  									AND GB.PERIOD_NUM = TO_CHAR(ADD_MONTHS(TO_DATE('" + DataFormat.getDateString(tsDate) + "','YYYY-MM-DD'),-1),'MM') \n ");
            sbSQL.append("  									AND   GB.SET_OF_BOOKS_ID=  " + getSetOfBooksID(lOfficeID) + " \n ");
            sbSQL.append("  									AND   GB.CURRENCY_CODE= '" + getCurrencyCode(lCurrencyID) + "'  \n ");
            sbSQL.append("  									AND   GB.ACTUAL_FLAG='A'  \n ");
            sbSQL.append("  									GROUP BY GB.CODE_COMBINATION_ID \n ");
            sbSQL.append("  				  )   B, \n ");
            sbSQL.append("  				  GL_CODE_COMBINATIONS@ITREASURY_T CODE \n ");
            sbSQL.append("  				  WHERE CODE.CODE_COMBINATION_ID = B.CODE_COMBINATION_ID(+) \n ");
            sbSQL.append("  			) PERIOD, \n ");
            sbSQL.append("  			GL_CODE_COMBINATIONS@ITREASURY_T CODE, \n ");
            sbSQL.append("  			GL_LOOKUPS@ITREASURY_T LOOKUP \n ");
            sbSQL.append("  WHERE DAY.CODE_COMBINATION_ID = PERIOD.CODE_COMBINATION_ID \n ");
            sbSQL.append("  				  AND DAY.CODE_COMBINATION_ID = CODE.CODE_COMBINATION_ID	 \n ");
            sbSQL.append("  				  AND    CODE.ACCOUNT_TYPE= LOOKUP.LOOKUP_CODE  \n ");
            sbSQL.append("  				  AND    LOOKUP.LOOKUP_TYPE='ACCOUNT TYPE' \n ");
            sbSQL.append("  				  AND    CODE.SEGMENT1 = '" + getFirstSegment(lCurrencyID) + "' \n ");
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                GLBalanceInfo info = new GLBalanceInfo();
                long lDirectionID = SETTConstant.SubjectAttribute.getDirection(getSubjectType(rs.getString("ACCOUNT_TYPE")));
                double dBalance = rs.getDouble("BALANCE");
                info.setGLDate(tsDate);
                info.setOfficeID(lOfficeID);
                info.setCurrencyID(lCurrencyID);
                info.setGLSubjectCode(rs.getString("sSubject"));
                info.setBalanceDirection(lDirectionID);
                if (lDirectionID == SETTConstant.DebitOrCredit.DEBIT)
                {
                    info.setDebitBalance(dBalance);
                }
                else
                {
                    info.setCreditBalance(dBalance);
                }
                Log.print("从GL中得到getGLSubjectBalance："+info.getGLSubjectCode());
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

    public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID,long lModelID, Timestamp tsDate) throws Exception
    {
        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            /////更新科目号
            conn = Database.getConnection();
            //conn=GLOracleFinBean.getOracleConnection(lOfficeID,lCurrencyID);
            
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.setLength(0);
            sbSQL.append("  SELECT  \n ");
            sbSQL.append("  GJL.CODE_COMBINATION_ID \n ");
            sbSQL.append("  ,CODE.SEGMENT1||'.'||CODE.SEGMENT2||'.'||CODE.SEGMENT3||'.'||CODE.SEGMENT4||'.'||CODE.SEGMENT5||'.'||CODE.SEGMENT6||'.'||CODE.SEGMENT7 sSubject \n ");
            sbSQL.append("  ,SUM(NVL(GJL.ENTERED_DR,0)) DR,SUM(NVL(GJL.ENTERED_CR,0))  CR, GJH.CURRENCY_CODE CURRENCY_CODE \n ");
            sbSQL.append("  FROM GL_JE_LINES@iTreasury_t     GJL,GL_JE_HEADERS@iTreasury_t    GJH,GL_CODE_COMBINATIONS@iTreasury_t CODE  \n ");
            sbSQL.append("  WHERE TO_CHAR(GJH.DATE_CREATED,'yyyymmdd') =  TO_CHAR(TO_DATE('" + DataFormat.getDateString(tsDate) + "','YYYY-MM-DD'),'yyyymmdd')   \n ");
            sbSQL.append("  AND   GJL.JE_HEADER_ID = GJH.JE_HEADER_ID    \n ");
            sbSQL.append("  AND   GJL.CODE_COMBINATION_ID = CODE.CODE_COMBINATION_ID \n ");
            sbSQL.append("  AND   GJL.SET_OF_BOOKS_ID=   " + getSetOfBooksID(lOfficeID) + "      \n ");
            sbSQL.append("  AND   GJH.CURRENCY_CODE='" + getCurrencyCode(lCurrencyID) + "' \n ");
            sbSQL.append("  AND   CODE.SEGMENT1 = '" + getFirstSegment(lCurrencyID) + "' \n ");
            sbSQL.append("  AND   GJH.JE_SOURCE <> '"+getSourceName(Constant.ModuleType.SETTLEMENT,lOfficeID,lCurrencyID)+"'  \n ");             
            sbSQL.append("  AND   GJH.JE_SOURCE <> '"+getSourceName(Constant.ModuleType.SECURITIES,lOfficeID,lCurrencyID)+"'  \n ");             
            sbSQL.append("  GROUP BY GJL.CODE_COMBINATION_ID \n ");
            sbSQL.append("  ,GJH.CURRENCY_CODE,CODE.SEGMENT1||'.'||CODE.SEGMENT2||'.'||CODE.SEGMENT3||'.'||CODE.SEGMENT4||'.'||CODE.SEGMENT5||'.'||CODE.SEGMENT6||'.'||CODE.SEGMENT7  \n ");
            sbSQL.append("  \n ");
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            while (rs != null && rs.next())
            {
                GLBalanceInfo info = new GLBalanceInfo();
                info.setGLDate(tsDate);
                info.setGLSubjectCode(rs.getString("sSubject"));
                info.setDebitAmount(rs.getDouble("DR"));
                info.setCreditAmount(rs.getDouble("CR"));
                list.add(info);
                Log.print("从GL中得到getGLSubjectAmount："+info.getGLSubjectCode());
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
        return (list != null && list.size() > 0 ? list : null);
    }

    /*
     * U850GLBean.isCompleted()：判断对方返回信息是否完毕
     */
    private static long isCompleted(long lOfficeID,long lCurrencyID) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long lDealStatusID = Constant.ShutDownStatus.FAIL;
        try
        {
            conn = Database.getConnection();
            //conn=GLOracleFinBean.getOracleConnection(lOfficeID,lCurrencyID,1);
            
            ps = conn.prepareStatement(" select * from CUX_GL_IMPORT@ITREASURY_T ");
            rs = ps.executeQuery();
            if (rs != null & rs.next())
            {
                String strStatusID = rs.getString("STATUS");
                if (strStatusID.equals("NEW")) ////正在处理
                {
                    lDealStatusID = Constant.ShutDownStatus.DOING;
                }
                else /////失败    (可能开头是E,等其它的各种情况)
                {
                    lDealStatusID = Constant.ShutDownStatus.FAIL;
                }
            }
            else
            {
            	/////成功
                lDealStatusID = Constant.ShutDownStatus.SUCCESS;
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
            lDealStatusID = Constant.ShutDownStatus.FAIL;
            e.printStackTrace();
            if (rs != null)
            {
                rs.close();
                rs = null;
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
                throw new RemoteException("remote exception : " + e.toString());
            }
        }
        return lDealStatusID;
    }

    /*
     * postGLVoucherToOracleFin()：判断对方返回信息是否完毕
     */
    private static long postGLVoucherToOracleFin(Collection collVoucher,long lOfficeID,long lCurrencyID) throws Exception
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
            //conn = Database.get_jdbc_connection();	//原来是database库里面的jdbc中的Oracle连接
        	conn=GLOracleFinBean.getOracleConnection(lOfficeID,lCurrencyID,1);	//运用自己的私有方法根据币种办事处得到数据库的连接
            
            conn.setAutoCommit(false);
            if (collVoucher != null)
            {
                Iterator it = collVoucher.iterator();
                while (it.hasNext())
                {
                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    for (int i = 0; voucher.getList() != null && i < voucher.getList().size(); i++)
                    {
                        GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(i);
                        OracleGLInfo oracleinfo = new OracleGLInfo();
                        oracleinfo.modelname = getModelName(voucher.getModelID());
                        oracleinfo.status = "NEW";
                        oracleinfo.set_of_books_id = getSetOfBooksID(entryinfo.getOfficeID());
                        oracleinfo.accounting_date = entryinfo.getExecute();
                        if (oracleinfo.accounting_date == null)
                            oracleinfo.accounting_date = voucher.getPostDate();
                        ///////////// ;
                        ////////得到会计日期并处理
                        boolean bHavaAccountDate = false;
                        Timestamp tsStartAccountDate = voucher.getPostDate();
                        sbRecord.setLength(0);
                        sbRecord.append("	SELECT 	SET_OF_BOOKS_ID,PERIOD_NAME,START_DATE,END_DATE \n");
                        sbRecord.append("	FROM 	GL_PERIOD_STATUSES@ITREASURY_T      \n");
                        sbRecord.append("	WHERE 	CLOSING_STATUS = 'N' and START_DATE <= ? and END_DATE >= ? and  SET_OF_BOOKS_ID = " + oracleinfo.set_of_books_id);
                        ps = conn.prepareStatement(sbRecord.toString());
                        ps.setTimestamp(1, oracleinfo.accounting_date);
                        ps.setTimestamp(2, oracleinfo.accounting_date);
                        rs = ps.executeQuery();
                        if (rs != null & rs.next())
                        {
                            bHavaAccountDate = true;
                        }
                        rs.close();
                        rs = null;
                        ps.close();
                        ps = null;
                        if (bHavaAccountDate == false)
                        {
                            //////////////如果没有取得会计日期，则取最近的会计日期
                            sbRecord.setLength(0);
                            sbRecord.append("SELECT min(START_DATE) START_DATE \n");
                            sbRecord.append("FROM 	GL_PERIOD_STATUSES@ITREASURY_T      \n");
                            sbRecord.append("WHERE 	CLOSING_STATUS = 'N' and START_DATE > ?  and  SET_OF_BOOKS_ID = " + oracleinfo.set_of_books_id);
                            ps = conn.prepareStatement(sbRecord.toString());
                            ps.setTimestamp(1, oracleinfo.accounting_date);
                            rs = ps.executeQuery();
                            while (rs != null & rs.next())
                            {
                                tsStartAccountDate = rs.getTimestamp(1);
                            }
                            rs.close();
                            rs = null;
                            ps.close();
                            ps = null;
                            oracleinfo.accounting_date = tsStartAccountDate;
                        }
                        oracleinfo.currency_code = getCurrencyCode(entryinfo.getCurrencyID());
                        oracleinfo.user_je_category_name = getUserJeCategoryName(entryinfo.getCurrencyID());
                        oracleinfo.date_created = voucher.getPostDate();
                        long created_by = -1;
                        /*
                         * pstemp = conn.prepareStatement("select user_id from
                         * fnd_user@ITREASURY_T where user_name = 'INTERFACE'
                         * "); rstemp = pstemp.executeQuery(); if (rstemp !=
                         * null & rstemp.next()) { created_by =
                         * rstemp.getLong(1); } rstemp.close(); rstemp = null;
                         * pstemp.close(); pstemp = null;
                         */
                        oracleinfo.Actual_flag = "A";
                        oracleinfo.user_je_source_name = oracleinfo.modelname;
                        oracleinfo.currency_conversion_date = entryinfo.getExecute();
                        oracleinfo.user_currency_conversion_type = "User";
                        if (entryinfo.getCurrencyID() != Constant.CurrencyType.RMB)
                        {
                            oracleinfo.user_currency_conversion_type = "Corporate";
                        }
                        String sAccountTemp = entryinfo.getSubject();
                        int index = -1;
                        index = sAccountTemp.indexOf(".");
                        if (index > 0)
                        {
                            oracleinfo.segment1 = sAccountTemp.substring(0, index);
                            sAccountTemp = sAccountTemp.substring(index + 1);
                            index = sAccountTemp.indexOf(".");
                        }
                        if (index > 0)
                        {
                            oracleinfo.segment2 = sAccountTemp.substring(0, index);
                            sAccountTemp = sAccountTemp.substring(index + 1);
                            index = sAccountTemp.indexOf(".");
                        }
                        if (index > 0)
                        {
                            oracleinfo.segment3 = sAccountTemp.substring(0, index);
                            sAccountTemp = sAccountTemp.substring(index + 1);
                            index = sAccountTemp.indexOf(".");
                        }
                        if (index > 0)
                        {
                            oracleinfo.segment4 = sAccountTemp.substring(0, index);
                            sAccountTemp = sAccountTemp.substring(index + 1);
                            index = sAccountTemp.indexOf(".");
                        }
                        if (index > 0)
                        {
                            oracleinfo.segment5 = sAccountTemp.substring(0, index);
                            sAccountTemp = sAccountTemp.substring(index + 1);
                            index = sAccountTemp.indexOf(".");
                        }
                        if (index > 0)
                        {
                            oracleinfo.segment6 = sAccountTemp.substring(0, index);
                            oracleinfo.segment7 = sAccountTemp.substring(index + 1);
                        }
                        Log.print("segment1=" + oracleinfo.segment1 + ",segment2=" + oracleinfo.segment2 + ",segment3=" + oracleinfo.segment3 + ",segment4=" + oracleinfo.segment4 + ",segment5=" + oracleinfo.segment5 + ",segment6=" + oracleinfo.segment6 + ",segment7=" + oracleinfo.segment7);
                        //Log.print("**************after
                        // segment6**************");
                        if (entryinfo.getDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
                        {
                            oracleinfo.entered_dr = entryinfo.getAmount();
                        }
                        else
                        {
                            oracleinfo.entered_cr = entryinfo.getAmount();
                        }
                        //oracleinfo.reference1 = oracleinfo.modelname +
                        // DataFormat.getDateString(voucher.getPostDate()) +
                        // getUserJeCategoryName(entryinfo.getCurrencyID());
                        if (strBatchName == null || strBatchName.length() <= 0)
                        {
                            strBatchName = getBatchName(conn, voucher.getModelID(), entryinfo.getOfficeID(), entryinfo.getCurrencyID(), voucher.getPostDate());
                        }
                        oracleinfo.reference1 = strBatchName;
                        oracleinfo.reference4 = oracleinfo.reference1 + "-" + entryinfo.getTransNo();
                        oracleinfo.reference10 = DataFormat.formatString(entryinfo.getAbstract());
                        oracleinfo.Period_Name = getPeriodName(oracleinfo.accounting_date);
                        if (hash.get(oracleinfo.reference4) != null && hash.get(oracleinfo.reference4).toString().length() > 0)
                        {
                            lje_line_num = Long.parseLong(hash.get(oracleinfo.reference4).toString());
                            lje_line_num++;
                            hash.put(oracleinfo.reference4, String.valueOf(lje_line_num));
                        }
                        else
                        {
                            lje_line_num = 1;
                            hash.put(oracleinfo.reference4, String.valueOf(lje_line_num));
                        }
                        oracleinfo.je_line_num = lje_line_num;
                        strTransNo = DataFormat.formatString(entryinfo.getTransNo());
                        oracleinfo.attribute5 = entryinfo.getTransNo();
                        oracleinfo.attribute6 = "";
                        oracleinfo.attribute6 = entryinfo.getBankChequeNo();
                        Log.print("**************before insert **************");
                        sbRecord.setLength(0);
                        sbRecord.append(" insert into GL_INTERFACE@ITREASURY_T\n");
                        sbRecord.append(" ( \n");
                        sbRecord.append(" status,set_of_books_id,accounting_date,currency_code,date_created, \n ");
                        sbRecord.append(" created_by,Actual_flag,user_je_category_name,user_je_source_name,currency_conversion_date,user_currency_conversion_type, \n");
                        sbRecord.append(" segment1,segment2,segment3,segment4,segment5,segment6,segment7, \n");
                        sbRecord.append(" entered_dr,entered_cr, \n");
                        sbRecord.append(" reference1,reference4,reference10,Period_Name,je_line_num,attribute5,attribute6 \n");
                        sbRecord.append(" ) \n");
                        sbRecord.append(" values  \n");
                        sbRecord.append(" ( \n");
                        sbRecord.append(" '" + oracleinfo.status + "'," + oracleinfo.set_of_books_id + ",?,'" + oracleinfo.currency_code + "',?, \n");
                        sbRecord.append(" " + oracleinfo.created_by + ", '" + oracleinfo.Actual_flag + "', '" + oracleinfo.user_je_category_name + "','" + oracleinfo.user_je_source_name + "',?,'" + oracleinfo.user_currency_conversion_type + "', \n");
                        sbRecord.append(" '" + oracleinfo.segment1 + "','" + oracleinfo.segment2 + "','" + oracleinfo.segment3 + "','" + oracleinfo.segment4 + "','" + oracleinfo.segment5 + "','" + oracleinfo.segment6 + "','" + oracleinfo.segment7 + "', \n");
                        sbRecord.append(" " + oracleinfo.entered_dr + "," + oracleinfo.entered_cr + ", \n");
                        sbRecord.append(" '" + oracleinfo.reference1 + "','" + oracleinfo.reference4 + "','" + oracleinfo.reference10 + "','" + oracleinfo.Period_Name + "'," + oracleinfo.je_line_num + ", '" + oracleinfo.attribute5 + "', '" + oracleinfo.attribute6 + "')");
                        Log.print(sbRecord.toString());
                        ps = conn.prepareStatement(sbRecord.toString());
                        ps.setTimestamp(1, oracleinfo.accounting_date);
                        ps.setTimestamp(2, oracleinfo.date_created);
                        ps.setTimestamp(3, oracleinfo.currency_conversion_date);
                        ps.executeUpdate();
                        Log.print("**************after insert **************");
                        ps.close();
                        ps = null;
                    }
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

    /*
     * U850GLBean.isCompleted()：判断对方返回信息是否完毕
     */
    private static long triggerPostGLVoucher(long lOfficeID,long lCurrencyID) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
        StringBuffer sbRecord = new StringBuffer();
        PreparedStatement ps = null;
        Connection conn = null;
        try
        {
            //		////启动Oracle程序，开始导出！
            Log.print("+++++++++++++++启动Oracle程序开始++++++");

            //conn = Database.get_jdbc_connection();	//原来是database库里面的jdbc中的Oracle连接
        	conn=GLOracleFinBean.getOracleConnection(lOfficeID,lCurrencyID,1);	//运用自己的私有方法根据币种办事处得到数据库的连接

            sbRecord.setLength(0);
            sbRecord.append(" delete from  CUX_GL_IMPORT@ITREASURY_T  ");
            ps = conn.prepareStatement(sbRecord.toString());
            ps.executeUpdate();
            ps.close();
            ps = null;
            sbRecord.setLength(0);
            sbRecord.append(" INSERT INTO CUX_GL_IMPORT@ITREASURY_T(BATCH_NUMBER,STATUS,CREATION_DATE)\n");
            sbRecord.append(" VALUES('1', '1', SYSDATE) ");
            ps = conn.prepareStatement(sbRecord.toString());
            ps.executeUpdate();
            ps.close();
            ps = null;
            conn.close();
            conn = null;
            Log.print("+++++++++++++++启动Oracle程序成功++++++");
        }
        catch (Exception e)
        {
            lDealStatusID = Constant.ShutDownStatus.FAIL;
            e.printStackTrace();
            throw e;
        }
        return lDealStatusID;
    }

    private static long findPostGLVoucherInfo(Collection collVoucher) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        try
        {
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
            }
            catch (Exception e)
            {
                throw new Exception("remote exception : " + e.toString());
            }
        }
        return lDealStatusID;
    }

    /**
     * 得到与Oralce系统相对应的账套
     * 
     * @param lOfficeID
     * @return
     */
    public static long getSetOfBooksID(long lOfficeID)
    {
        long lSet_Of_Books_ID = 1;
        switch ((int) lOfficeID)
        {
            case 1:
                lSet_Of_Books_ID = 1;
                break;
            default:
                lSet_Of_Books_ID = 1;
                break;
        }
        return lSet_Of_Books_ID;
    }

    /**
     * 得到与Oralce系统相对应的币种编码
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

    public static String getFirstSegment(long lCurrencyID)
    {
        String strFirstSegment = "001";
        switch ((int) lCurrencyID)
        {
            case (int) Constant.CurrencyType.USD:
                strFirstSegment = "002";
                break;
            default:
                strFirstSegment = "001";
                break;
        }
        return strFirstSegment;
    }

    /**
     * 得到与Oralce系统相对应的币种归类名称
     * 
     * @param lCurrencyID
     * @return
     */
    public static String getUserJeCategoryName(long lCurrencyID)
    {
        String user_je_category_name = "人民币业务";
        switch ((int) lCurrencyID)
        {
            case (int) Constant.CurrencyType.RMB:
                user_je_category_name = "人民币业务";
                break;
            case (int) Constant.CurrencyType.USD:
                user_je_category_name = "美元业务";
                break;
            default:
                user_je_category_name = "人民币业务";
                break;
        }
        return user_je_category_name;
    }

    /**
     * 得到与Oralce系统相对应的模块名称
     * 
     * @param lCurrencyID
     * @return
     */
    public static String getModelName(long lModelID)
    {
        String strModelName = "存贷款结算";
        switch ((int) lModelID)
        {
            case (int) Constant.ModuleType.SETTLEMENT:
                strModelName = "存贷款结算";
                break;
            case (int) Constant.ModuleType.SECURITIES:
                strModelName = "证券业务";
                break;
            default:
                strModelName = "存贷款结算";
                break;
        }
        return strModelName;
    }

    /**
     * 得到与Oralce系统相对应的模块名称
     * 
     * @param lCurrencyID
     * @return
     */
    public static String getBatchName(Connection conn, long lModelID, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
    {
        String strBatchName = getModelName(lModelID) + DataFormat.getDateString(tsDate) + getUserJeCategoryName(lCurrencyID);
        try
        {
            switch ((int) lModelID)
            {
                case (int) Constant.ModuleType.SETTLEMENT:
                    strBatchName = getModelName(lModelID) + DataFormat.getDateString(tsDate) + getUserJeCategoryName(lCurrencyID);
                    break;
                case (int) Constant.ModuleType.SECURITIES:
                    strBatchName = getModelName(lModelID) + DataFormat.getDateTimeString(conn) + getUserJeCategoryName(lCurrencyID);
                    break;
                default:
                    strBatchName = getModelName(lModelID) + DataFormat.getDateString(tsDate) + getUserJeCategoryName(lCurrencyID);
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return strBatchName;
    }

    /**
     * 得到与Oralce系统相对应的科目类型
     * 
     * @param lOfficeID
     * @return
     */
    public static long getSubjectType(String strType)
    {
        long lTypeID = -1;
        if (strType != null)
        {
            if (strType.equals("A"))
            {
                lTypeID = SETTConstant.SubjectAttribute.ASSET;
            }
            if (strType.equals("L"))
            {
                lTypeID = SETTConstant.SubjectAttribute.DEBT;
            }
            if (strType.equals("O"))
            {
                lTypeID = SETTConstant.SubjectAttribute.RIGHT;
            }
            if (strType.equals("R"))
            {
                lTypeID = SETTConstant.SubjectAttribute.INCOME;
            }
            if (strType.equals("E"))
            {
                lTypeID = SETTConstant.SubjectAttribute.PAYOUT;
            }
        }
        return lTypeID;
    }

    /**
     * 得到与Oralce系统相对应的科目区间名称
     * 
     * @param lOfficeID
     * @return
     */
    public static String getPeriodName(Timestamp tsDate)
    {
        String strPeriodName = "";
        if (tsDate != null)
        {
            int lMonth = tsDate.getMonth() + 1;
            int lYear = tsDate.getYear();
            String strMonth = "";
            String strYear = "";
            switch (lMonth)
            {
                case 1:
                    strMonth = "JAN";
                    break;
                case 2:
                    strMonth = "FEB";
                    break;
                case 3:
                    strMonth = "MAR";
                    break;
                case 4:
                    strMonth = "APR";
                    break;
                case 5:
                    strMonth = "MAY";
                    break;
                case 6:
                    strMonth = "JUN";
                    break;
                case 7:
                    strMonth = "JUL";
                    break;
                case 8:
                    strMonth = "AUG";
                    break;
                case 9:
                    strMonth = "SEP";
                    break;
                case 10:
                    strMonth = "OCT";
                    break;
                case 11:
                    strMonth = "NOV";
                    break;
                case 12:
                    strMonth = "DEC";
                    break;
            }
            strYear = DataFormat.formatInt((int) lYear % 100, 2);
            strPeriodName = strMonth + "-" + strYear;
            Log.print("*****lMonth=" + lMonth + ",lYear=" + lYear);
            Log.print("*****strMonth=" + strMonth + ",strYear=" + strYear);
            Log.print("*****strPeriodName=" + strPeriodName);
        }
        return strPeriodName;
    }
    public static String getSourceName(long lModelID,long lOfficeID, long lCurrencyID)
    { 
        String strSourceName = "";
        switch ((int)lModelID)
        {
            case (int)Constant.ModuleType.SETTLEMENT:
                strSourceName = "1";
                break;
            case (int)Constant.ModuleType.SECURITIES:
                strSourceName = "3";
                break;
            
        }
        return strSourceName;
    } 
    
    /**
	 * 此方法根据数据库中保存的参数，直接连接Oracle数据库
	 * @return Connection
	 */
	
	private static Connection getOracleConnection(long lOfficeID,long lCurrencyID,long lType) throws Exception
	{
		Connection conn = null;

		try
		{		
				//得到配置信息
				GlSettingInfo glSettingInfo=new GlSettingInfo();
				glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,lType);
				
	            String DB_IP = glSettingInfo.getGlDBIP();			//IP
	            String DB_SID = glSettingInfo.getGlDBDatabaseName();	//库名称
	            String DB_USERNAME = glSettingInfo.getGlDBUserName();		//用户名
	            String DB_PASSWORD = DataFormat.formatNullString(glSettingInfo.getGlDBPassWord());	//密码
	            String DB_PORT = glSettingInfo.getGlDBPort();		//端口
				
				Log.print(" Enter method --getOracleConnection() ");

				String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
	            
	            Log.print("dbURL = " + dbURL);
	            Log.print("DB_USERNAME = " + DB_USERNAME);
	            Log.print("DB_PASSWORD = " + DB_PASSWORD);

	            Class.forName(jdbcDriver).newInstance();
	            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
		}		
		catch (SQLException sqe)
        {
            Log.print("connect db failed by oracle jdbc driver. " + sqe.toString());
            throw sqe;
        }		
		
		return conn;
	}
    
    private static class OracleGLInfo implements Serializable
    {
        public String modelname = "";

        public String status = "";

        public long set_of_books_id = -1;

        public Timestamp accounting_date = null;

        public String currency_code = "";

        public Timestamp date_created = null;

        public long created_by = -1;

        public String Actual_flag = "";

        public String user_je_category_name = "";

        public String user_je_source_name = "";

        public Timestamp currency_conversion_date = null;

        public String user_currency_conversion_type = "";

        public String segment1 = "";

        public String segment2 = "";

        public String segment3 = "";

        public String segment4 = "";

        public String segment5 = "";

        public String segment6 = "";

        public String segment7 = "";

        public double entered_dr = 0;

        public double entered_cr = 0;

        public String reference1 = "";

        public String reference4 = "";

        public String reference10 = "";

        public String Period_Name = "";

        public long je_line_num = 1;

        public String attribute5 = "";

        public String attribute6 = "";
    }
}