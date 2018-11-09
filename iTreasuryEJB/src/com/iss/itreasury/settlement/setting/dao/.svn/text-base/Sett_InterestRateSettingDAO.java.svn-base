/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.setting.dataentity.SettInterestRateSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettSettingRateQueryInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author jinchen
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_InterestRateSettingDAO extends SettlementDAO
{
    /**
     * @param tableName
     * @param isNeedPrefix
     */
    private Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    public Sett_InterestRateSettingDAO()
    {
        super("loan_InterestRate");
        // TODO Auto-generated constructor stub
    }

    public Sett_InterestRateSettingDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param tableName
     */
    public Sett_InterestRateSettingDAO(String tableName)
    {
        super(tableName);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param tableName
     * @param conn
     */
    public Sett_InterestRateSettingDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
        // TODO Auto-generated constructor stub
    }

    /**
     * findInterestByID 查找利率信息 根据条件查找利率信息 操作 BANKLOANINTERESTRATE 数据表 查询相应记录
     * 
     * @param long
     *            lID 利率标示
     * @return InterestRateInfo
     * @throws SettlementDAOException
     * @throws RemoteException
     */
    public SettInterestRateSettingInfo findInterestRateByID(long lID)
            throws SettlementDAOException
    {
        //    	可能还会需要别的信息
        long lOfficeID = 0;
        long lInputUserID = 0;
        long lUpdateUserID = 0;
        SettInterestRateSettingInfo ii = new SettInterestRateSettingInfo();
        try
        {
            this.initDAO();
            StringBuffer sb = new StringBuffer();
            sb
                    .append(" SELECT a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,b.id as parentid ");
            sb.append(" FROM loan_InterestRate a,loan_INTERESTRATETYPEINFO b ");
            sb.append(" WHERE a.id=? and b.ID=a.NBANKINTERESTTYPEID ");

            Log.print("SQL  ####################语句                     " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transPS.setLong(1, lID);
            transRS = transPS.executeQuery();
            if (transRS != null && transRS.next())
            {
                ii.setId(transRS.getLong("ID"));
                ii.setNBankInterestTypeId(transRS
                        .getLong("nBankInterestTypeID"));
                ii.setMRate(transRS.getDouble("mRate"));
                ii.setDtValiDate(transRS.getTimestamp("dtValidate"));
                ii.setNInputUserId(transRS.getLong("nInputUserID"));
                ii.setDtInput(transRS.getTimestamp("dtInput"));
                ii.setSInterestRateNo(transRS.getString("SINTERESTRATENO"));
                ii.setSInterestRateName(transRS.getString("SINTERESTRATENAME"));
                ii.setNModifyUserId(transRS.getLong("nModifyUserID"));
                ii.setDtModify(transRS.getTimestamp("dtModify"));
                //ii.m_lLoanType = transRS.getLong("nCurrencyID");
                //ii.m_lBankInterestID = transRS.getLong("ParentID");
                // //这个实际就是上边的BankInterestTypeID

            }

            transRS.close();
            transRS = null;
            transPS.close();
            transPS = null;

            sb.setLength(0);
            sb.append(" SELECT *  ");
            sb.append(" FROM userinfo ");
            sb.append(" WHERE id=? ");
            Log.print("SQL  ####################语句                     " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transPS.setLong(1, ii.getNModifyUserId());
            transRS = transPS.executeQuery();
            if (transRS != null && transRS.next())
            {
                ii.setSModifyUserName(transRS.getString("sName"));
            }

            transRS.close();
            transRS = null;
            transPS.close();
            transPS = null;

            sb.setLength(0);
            sb.append(" SELECT sName ");
            sb.append(" FROM userinfo ");
            sb.append(" WHERE id=? ");
            Log.print("SQL  ####################语句                     " + sb);
            transPS = transConn.prepareStatement(sb.toString());
            transPS.setLong(1, ii.getNInputUserId());
            transRS = transPS.executeQuery();
            if (transRS != null && transRS.next())
            {
                ii.setSInputUserName(transRS.getString("sName"));
            }
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ii;

    }

    /**
     * findInterestRate 银行利率设置查找 根据银行利率类型查询银行利率设置 操作InterestRateHistory数据表 查找
     * 利率生效日在当前日起之后 的纪录 查询相应记录
     * 
     * @param lTypeID
     *            银行利率编号
     * @param lCurrencyID
     *            币种
     * @return Collection 银行利率信息
     * @throws RemoteException
     */
    public Collection findInterestRateByTypeID(
            SettSettingRateQueryInfo queryInfo) throws SettlementDAOException
    {
        Vector v = new Vector();

        long lTypeID = queryInfo.getLTypeID();
        long lCurrencyID = queryInfo.getLCurrencyID();
        long lPageLineCount = queryInfo.getLPageLineCount();
        long lPageNo = queryInfo.getLPageNo();
        String sOrderParam = queryInfo.getSOrderParam();
        long lDesc = queryInfo.getLDesc();

        long lAccountID = 0;
        long lBankID = 0;
        long lInputUserID = 0;
        long lCheckUserID = 0;
        long lPayerClientID = 0;
        long lID = 0;
        long lRecordCount = 1;
        long lPageCount = 10;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        String strTmpSQL = "";
        Timestamp tsSystemDate = null;
        int iIndex = 0;

        try
        {

            this.initDAO();
            StringBuffer sb = new StringBuffer();
            String strSQL = "";
            //tsSystemDate = Common.getSystemDate();
            //////////////////////////////////////////
            //计算记录总数
            strSQL = " select count(*) from loan_InterestRate a, loan_INTERESTRATETYPEINFO b,userinfo c  "
                    + " where   b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID "
                    + " and to_char(a.DTVALIDATE,'yyyymmdd')>to_char(sysdate,'yyyymmdd') ";
            if (lTypeID > 0)
            {
                log4j.info("lTypeID" + lTypeID);
                strSQL = strSQL + " and a.id=? ";
            }
            log4j.info(strSQL);

            transPS = transConn.prepareStatement(strSQL);
            if (lTypeID > 0)
            {
                transPS.setLong(1, lTypeID);
            }
            transRS = transPS.executeQuery();
            if (transRS != null && transRS.next())
            {
                lRecordCount = transRS.getLong(1);
            }
            log4j.info("the lRecordCount is " + lRecordCount);
            strSQL = " select count(distinct b.id) from loan_InterestRate a, loan_INTERESTRATETYPEINFO b,userinfo c  "
                    + " where b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID  "
                    + " and to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') ";
            if (lTypeID > 0)
            {
                log4j.info("lTypeID" + lTypeID);
                strSQL = strSQL + " and a.id=? ";
            }
            log4j.info(strSQL);
            transPS = transConn.prepareStatement(strSQL);
            if (lTypeID > 0)
            {
                transPS.setLong(1, lTypeID);
            }
            transRS = transPS.executeQuery();
            if (transRS != null && transRS.next())
            {
                lRecordCount = lRecordCount + transRS.getLong(1);
            }
            log4j.info("--------------the lRecordCount is------------- "
                    + lRecordCount);
            //计算总页数
            lPageCount = lRecordCount / lPageLineCount;
            if ((lRecordCount % lPageLineCount) != 0)
            {
                lPageCount++;
            }
            //返回需求的结果集
            //分页显示，显示下一页
            lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
            lRowNumEnd = lRowNumStart + lPageLineCount - 1;
            ////////////////////////////////////////////
            sb.setLength(0);
            //进行查找
            strSQL = " select a.dtInput, a.id,a.NBANKINTERESTTYPEID,a.mrate,a.dtvalidate ,b.SINTERESTRATENAME ,b.SINTERESTRATENO, c.SNAME as name1,a.nInputUserID nInputUserID from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c  "
                    + " where b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID  "
                    + " and to_char(a.DTVALIDATE,'yyyymmdd')>to_char(sysdate,'yyyymmdd')";
            if (lTypeID > 0)
            {
                log4j.info("lTypeID" + lTypeID);
                strSQL = strSQL + " and a.id=? ";
            }
            strSQL = strSQL
                    + " union "
                    + " select a.dtInput,a.id,a.NBANKINTERESTTYPEID,a.mrate,a.dtvalidate ,b.SINTERESTRATENAME ,b.SINTERESTRATENO, c.SNAME as name1,a.nInputUserID nInputUserID from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c  "
                    + " where b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID  "
                    +
                    //" and b.NCURRENCYID=a.NCURRENCYID " +
                    " and (b.id,a.dtValidate) in "
                    + " (select b.id,max(a.dtvalidate) from loan_InterestRate a,loan_INTERESTRATETYPEINFO b "
                    + " where  b.ID=a.NBANKINTERESTTYPEID "
                    + " and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') "
                    + " group by b.id )  ";
            if (lTypeID > 0)
            {
                log4j.info("lTypeID" + lTypeID);
                strSQL = strSQL + " and a.id=? ";
            }
            System.out
                    .println("--------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("SQL           为-----------------"
                    + strSQL.toString());
            System.out
                    .println("------>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            strTmpSQL = "";
            int nIndex = 0;
            System.out.println("传进来的排序条件为:    " + sOrderParam);
            nIndex = sOrderParam.indexOf(".");
            if (nIndex > 0)
            {
                if (sOrderParam.substring(0, nIndex).toLowerCase()
                        .equalsIgnoreCase("loan_InterestRate"))
                {
                    strTmpSQL += " \n order by a."
                            + sOrderParam.substring(nIndex + 1);
                }

            } else
            {
                strTmpSQL += " \n order by a.id ";
            }
            System.out
                    .println("------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>@");
            //strSQL +=strTmpSQL;
            System.out.println("SQL           为-----------------"
                    + strTmpSQL.toString());
            System.out
                    .println("------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //strSQL +=strTmpSQL;
            //判断是升序还是降序，升序是系统默认的，降序是desc
            if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
                strTmpSQL += " desc";
            }
            strSQL = " select ff.*, rownum r from ( select a.* from ( "
                    + strSQL + " ) a " + strTmpSQL + " ) ff";
            strSQL = " select * from ( " + strSQL + " ) b  where b.r between "
                    + lRowNumStart + " and " + lRowNumEnd;
            transPS = transConn.prepareStatement(strSQL);
            log4j.info("strSQL is  " + strSQL);
            System.out.println("strSQL is  " + strSQL);
            //ps.setTimestamp(2,tsSystemDate);
            iIndex = 0;
            if (lTypeID > 0)
            {
                iIndex++;
                transPS.setLong(iIndex, lTypeID);
            }
            if (lTypeID > 0)
            {
                iIndex++;
                transPS.setLong(iIndex, lTypeID);
            }
            System.out
                    .println("-->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("SQL           为-----------------"
                    + strSQL.toString());
            System.out
                    .println("->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            transRS = transPS.executeQuery();
            while (transRS != null && transRS.next())
            { //返回SettInterestRateSettingInfo
                SettInterestRateSettingInfo ii = new SettInterestRateSettingInfo();
                ii.setId(transRS.getLong("ID"));
                ii.setSInterestRateNo(transRS.getString("SINTERESTRATENO"));
                ii.setNBankInterestTypeId(transRS
                        .getLong("NBANKINTERESTTYPEID"));
                ii.setSInterestRateName(transRS.getString("SINTERESTRATENAME"));
                ii.setMRate(transRS.getDouble("mRATE"));
                ii.setDtValiDate(transRS.getTimestamp("DTVALIDATE"));
                ii.setSInputUserName(transRS.getString("name1"));
                ii.setDtInput(transRS.getTimestamp("DTINPUT"));
                log4j.info("在 EJB 中" + ii.getId());
                ii.setPageCount(lPageCount);
                v.addElement(ii);
            }
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return (v.size() > 0 ? v : null);
    }

    /**
     * @param queryInfo
     * @return
     * @throws SettlementDAOException
     */
    public Collection findInterestRateHistoryByTypeID(
            SettSettingRateQueryInfo queryInfo) throws SettlementDAOException
    {
        Vector v = new Vector();

        long lTypeID = queryInfo.getLTypeID();
        long lCurrencyID = queryInfo.getLCurrencyID();
        long lPageLineCount = queryInfo.getLPageLineCount();
        long lPageNo = queryInfo.getLPageNo();
        String sOrderParam = queryInfo.getSOrderParam();
        long lDesc = queryInfo.getLDesc();

        long lAccountID = 0;
        long lBankID = 0;
        long lInputUserID = 0;
        long lCheckUserID = 0;
        long lPayerClientID = 0;
        long lID = 0;
        long lRecordCount = 1;
        long lPageCount = 10;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        String strTmpSQL = "";
        Timestamp tsSystemDate = null;
        int iIndex = 0;
        String strSQL = "";

        try
        {
            this.initDAO();

            StringBuffer sb = new StringBuffer();

            strSQL = "SELECT count(*) FROM (select b.id,a.dtValidate from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c WHERE a.NBANKINTERESTTYPEID=? "
                    + " and b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') "
                    + " minus "
                    + " select b.id,max(a.dtValidate) from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c WHERE a.NBANKINTERESTTYPEID=? "
                    + " and b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') "
                    + " group by b.id )";
            log4j.info("strSQL = " + strSQL);
            transPS = transConn.prepareStatement(strSQL);
            transPS.setLong(1, lTypeID);
            transPS.setLong(2, lTypeID);
            //transPS.setTimestamp(3,tsSystemDate);
            System.out.println(strSQL);
            log4j.info(strSQL);
            transRS = transPS.executeQuery();
            while (transRS != null && transRS.next())
            {
                lRecordCount = transRS.getLong(1);
                System.out.println("1=" + lTypeID + "2=" + lRecordCount);
            }

            log4j.info("the lRecordCount is " + lRecordCount);
            transRS.close();
            transRS = null;
            transPS.close();
            transPS = null;

            log4j.info(sb.toString());
            //计算总页数
            lPageCount = lRecordCount / lPageLineCount;
            if ((lRecordCount % lPageLineCount) != 0)
            {
                lPageCount++;
            }

            //返回需求的结果集

            //分页显示，显示下一页
            lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
            lRowNumEnd = lRowNumStart + lPageLineCount - 1;
            ////////////////////////////////////////////
            sb.setLength(0);

            strSQL = "SELECT * FROM "
                    + " ( "
                    + " select a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,c.SNAME as name1  from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c "
                    + " WHERE a.NBANKINTERESTTYPEID=? "
                    + " and b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID "
                    + " and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') "
                    +
                    //" and b.NCURRENCYID=a.NCURRENCYID " +
                    " minus "
                    + " select a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,c.SNAME as name1 from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c "
                    + " WHERE a.NBANKINTERESTTYPEID=? "
                    + " and b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID "
                    + " and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') "
                    +
                    //" and b.NCURRENCYID=a.NCURRENCYID " +
                    " and (b.id,a.dtvalidate) in "
                    + " (   select b.id,max(a.dtvalidate) from loan_INTERESTRATETYPEINFO b,loan_InterestRate a,userinfo c "
                    + " WHERE a.NBANKINTERESTTYPEID=? "
                    + " and b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID "
                    + " and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') "
                    +
                    //" and b.NCURRENCYID=a.NCURRENCYID " +
                    " group by b.id " + " ) " + " ) ";

            strTmpSQL = "";

            //排序项控制

            int nIndex = 0;
            System.out.println("传进来的排序条件为:    " + sOrderParam);
            nIndex = sOrderParam.indexOf(".");
            if (nIndex > 0)
            {
                if (sOrderParam.substring(0, nIndex).toLowerCase()
                        .equalsIgnoreCase("loan_InterestRate"))
                {
                    strTmpSQL += " \n order by "
                            + sOrderParam.substring(nIndex + 1);
                }

            } else
            {
                strTmpSQL += " \n order by NBANKINTERESTTYPEID ";
            }

            strSQL += strTmpSQL;

            //判断是升序还是降序，升序是系统默认的，降序是desc
            if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
                strSQL += " desc";

            }

            strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
            strSQL = " select * from ( " + strSQL + " ) b  where b.r between "
                    + lRowNumStart + " and " + lRowNumEnd;
            System.out.println("the strSQL is " + strSQL);
            System.out.println("the lTypeID is " + lTypeID);
            transPS = transConn.prepareStatement(strSQL);
            transPS.setLong(1, lTypeID);

            transPS.setLong(2, lTypeID);

            transPS.setLong(3, lTypeID);

            //ps.setTimestamp(3,tsSystemDate);
            transRS = transPS.executeQuery();
            while (transRS != null && transRS.next())
            { //返回InterestRateInfo
                SettInterestRateSettingInfo ii = new SettInterestRateSettingInfo();

                //ii.setId(transRS.getLong("ID"));
                ii.setSInterestRateNo(transRS.getString("SINTERESTRATENO"));
                ii.setNBankInterestTypeId(transRS
                        .getLong("NBANKINTERESTTYPEID"));
                ii.setSInterestRateName(transRS.getString("SINTERESTRATENAME"));
                ii.setMRate(transRS.getDouble("mRATE"));
                ii.setDtValiDate(transRS.getTimestamp("DTVALIDATE"));
                ii.setSInputUserName(transRS.getString("name1"));
                ii.setDtInput(transRS.getTimestamp("DTINPUT"));
                //log4j.info("在 EJB 中" + ii.getId());
                ii.setPageCount(lPageCount);
                v.addElement(ii);

            }

            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return (v.size() > 0 ? v : null);
    }

    /**
     * @param queryInfo
     * @return
     * @throws SettlementDAOException
     */
    public Collection findInterestRateHistory(SettSettingRateQueryInfo queryInfo) throws SettlementDAOException
    {
        
        Vector v = new Vector();

        long lTypeID = queryInfo.getLTypeID();
        long lCurrencyID = queryInfo.getLCurrencyID();
        long lPageLineCount = queryInfo.getLPageLineCount();
        long lPageNo = queryInfo.getLPageNo();
        String sOrderParam = queryInfo.getSOrderParam();
        long lDesc = queryInfo.getLDesc();

        long lAccountID = 0;
        long lBankID = 0;
        long lInputUserID = 0;
        long lCheckUserID = 0;
        long lPayerClientID = 0;
        long lID = 0;
        long lRecordCount = 1;
        long lPageCount = 10;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        String strTmpSQL = "";
        Timestamp tsSystemDate = null;
        int iIndex = 0;
        String strSQL = "";

        try
        {
            this.initDAO();
        
        
        
            StringBuffer sb = new StringBuffer();
			//tsSystemDate = Common.getSystemDate();



			strSQL="SELECT count(*) FROM (select b.id,a.dtValidate from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c WHERE  "+
			" b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') " +
			" minus " +
			" select b.id,max(a.dtValidate) from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c WHERE "+
			" b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') " +
			" group by b.id )";

			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			while( transRS != null && transRS.next() )
			{
				lRecordCount = transRS.getLong(1);
			}

			log4j.info("the lRecordCount is "+lRecordCount);
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;


			log4j.info(sb.toString());
			//计算总页数
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}

			//返回需求的结果集

			//分页显示，显示下一页
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;
			////////////////////////////////////////////
			sb.setLength(0);

			strSQL="SELECT * FROM " +
			" ( " +
			" select a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,c.SNAME as name1  from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c " +
			" WHERE   " +
			"  b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID " +
			" and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') " +
			//" and b.NCURRENCYID=a.NCURRENCYID " +
			" minus " +
			" select a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,c.SNAME as name1 from loan_InterestRate a,loan_INTERESTRATETYPEINFO b,userinfo c " +
			" WHERE  " +
			"  b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID " +
			" and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') " +
			//" and b.NCURRENCYID=a.NCURRENCYID " +
			" and (b.id,a.dtvalidate) in " +
			" (   select b.id,max(a.dtvalidate) from loan_INTERESTRATETYPEINFO b,loan_InterestRate a,userinfo c " +
			" WHERE " +
			" b.ID=a.NBANKINTERESTTYPEID and c.ID=a.NINPUTUSERID " +
			" and  to_char(a.DTVALIDATE,'yyyymmdd')<=to_char(sysdate,'yyyymmdd') " +
			//" and b.NCURRENCYID=a.NCURRENCYID " +
			" group by b.id " +
			" ) " +
			" ) ";

			strTmpSQL = "";
			
		       //排序项控制

            int nIndex = 0;
            System.out.println("传进来的排序条件为:    " + sOrderParam);
            nIndex = sOrderParam.indexOf(".");
            if (nIndex > 0)
            {
                if (sOrderParam.substring(0, nIndex).toLowerCase()
                        .equalsIgnoreCase("loan_InterestRate"))
                {
                    strTmpSQL += " \n order by "
                            + sOrderParam.substring(nIndex + 1);
                }

            } else
            {
                strTmpSQL += " \n order by mRate ";
            }
			
			
			strSQL +=strTmpSQL;


			//判断是升序还是降序，升序是系统默认的，降序是desc
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";

			}

			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

			transPS = transConn.prepareStatement(strSQL);
			log4j.info("the strSQL is "+strSQL);
			System.out.println("the strSQL is "+strSQL);
			transRS = transPS.executeQuery();
			while( transRS != null && transRS.next() )
			{   //返回InterestRateInfo
			    
			    
			    SettInterestRateSettingInfo ii = new SettInterestRateSettingInfo();

                //ii.setId(transRS.getLong("ID"));
                ii.setSInterestRateNo(transRS.getString("SINTERESTRATENO"));
                ii.setNBankInterestTypeId(transRS
                        .getLong("NBANKINTERESTTYPEID"));
                ii.setSInterestRateName(transRS.getString("SINTERESTRATENAME"));
                ii.setMRate(transRS.getDouble("mRATE"));
                ii.setDtValiDate(transRS.getTimestamp("DTVALIDATE"));
                ii.setSInputUserName(transRS.getString("name1"));
                ii.setDtInput(transRS.getTimestamp("DTINPUT"));
                //log4j.info("在 EJB 中" + ii.getId());
                ii.setPageCount(lPageCount);
                v.addElement(ii);
			    
			    
			    
			    
				
			}
        
        
        
        
        
        
        
        
        
        this.finalizeDAO();
    } catch (ITreasuryDAOException e1)
    {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        throw new SettlementDAOException(e1);
    } catch (SQLException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    return (v.size() > 0 ? v : null);
    }
    
    
    
    
    
    
    /**
     * addBankInterestReteSetting 新增或修改银行利率 根据所给定的条件新增或修改银行利率
     * 操作InterestRateTypeInfo、InterestRateHistory数据表 新增或修改相应记录
     * 对于BankLoanInterestRateHistory，如果lID〉0，修改 lID=0,新增 新增步骤： 1
     * 根据利率编号从InterestRateTypeInfo中查处利率类型标示 2
     * 将利率类型标示和其他相关字段插入InterestRateHistory 条件：1 生效日 〉 当前日 2
     * 对同一利率类型标示,同一币种，生效日不能重合 3 如成功插入，修改InterestRateTypeInfo
     * 如果该类型的纪录已存在，用sInterestRateName替换原先的利率名称 修改时，如果该利率已做过利率调整，要修改利率调整表中的利率生效日期
     * 条件：1 生效日 〉 当前日 2 对同一利率类型标示,同一币种，生效日不能重合
     * 
     * @param lID
     *            标识
     * @param strInterestRateCode
     *            利率编号
     * @param strInterestRateName
     *            利率名称
     * @param dInterestRate
     *            利率
     * @param tsValibleDate
     *            利率生效日
     * @param lInputUserID
     *            录入人
     * @param tsInputDate
     *            录入日期
     * @param lCurrencyID
     *            币种
     * @return long 成功返回ID标识，失败返回0
     * @throws RemoteException
     */
    /* public long saveBankInterestRateSetting(long lID, String sInterestRateCode,
     String sInterestRateName, double dInterestRate,
     Timestamp tsValibleDate, long lInputUserID, Timestamp tsInputDate,
     long lCurrencyID)
     {

     String strSQL = null;
     String strSQL1 = null;
     long lResult = -5;
     long lBankLoanInterestRateID = -1;
     long lMaxID = 0;
     int iIndex = 0;
     int iNumber = 0;
     int i = 0;
     long lInsertOrUpdate = 0;
     ResultSet rss1 = null;

     //如果是新增
     if (lID == 0)
     {

     StringBuffer sb = new StringBuffer();
     StringBuffer sbCondition = new StringBuffer();
     StringBuffer sbNumber = new StringBuffer();
     /////////////////////////////////////////////////
     ////验证SINTERESTRATENO的唯一

     ////////////////////////////////////////////////////

     ////验证sInterestRateName的唯一

     ////验证loan_interestrete中mRate,dtvalidate的唯一

     //进行新增操作
     strSQL = "select ID from  LOAN_InterestRateTypeInfo where sInterestRateNo=? and  NCURRENCYID="
     + lCurrencyID;
     log4j.info("sql1 is :" + strSQL);
     transPS = transConn.prepareStatement(strSQL);
     transPS.setString(1, sInterestRateCode);
     transRS = transPS.executeQuery();
     if (transRS != null && transRS.next())
     {
     lBankLoanInterestRateID = transRS.getLong("ID");
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;

     } else
     {
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;

     StringBuffer sbMaxID = new StringBuffer();
     sbMaxID
     .append(" SELECT nvl(max(id),0) + 1 FROM LOAN_INTERESTRATETYPEINFO where NCURRENCYID="
     + lCurrencyID);
     transPS = transConn.prepareStatement(sbMaxID.toString());
     transRS = transPS.executeQuery();
     if (transRS.next())
     {
     lMaxID = transRS.getLong(1);
     } else
     {
     lResult = 1;
     }
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;

     /////////////////////////////////////
     //如果该银行利率记录不存在
     //则在数据库中新增
     //SINTERESTRATENAME在后面会重新更新一下
     ///////////////////////////////////////

     strSQL1 = "insert into LOAN_InterestRateTypeInfo (ID, SINTERESTRATENO, SINTERESTRATENAME, NCURRENCYID )"
     + "values (?,?,?,?)  ";

     transPS = transConn.prepareStatement(strSQL1);
     transPS.setLong(1, lMaxID);
     transPS.setString(2, sInterestRateCode);
     transPS.setString(3, sInterestRateName);
     transPS.setLong(4, lCurrencyID);

     transPS.executeUpdate();
     lResult = lMaxID;
     //用于插入 loan_InterestRate
     lBankLoanInterestRateID = lMaxID;
     transPS.close();
     transPS = null;

     }//else

     sInterestRateCode = sInterestRateCode.trim();

     log4j.info(" sInterestRateCode is" + sInterestRateCode);

     strSQL = "UPDATE   LOAN_InterestRateTypeInfo SET SINTERESTRATENAME=? where sInterestRateNo=? and NCURRENCYID="
     + lCurrencyID;

     log4j.info("sql is :" + strSQL);
     transPS = transConn.prepareStatement(strSQL);

     log4j.info("sInterestRateName is :" + sInterestRateName);
     transPS.setString(1, sInterestRateName);
     transPS.setString(2, sInterestRateCode);
     lResult = transPS.executeUpdate();

     log4j.info("lResult is :" + lResult);
     transPS.close();
     transPS = null;

     //获得最大id＋1
     StringBuffer sbMaxID = new StringBuffer();
     sbMaxID
     .append(" SELECT nvl(max(id),0) + 1 FROM loan_interestrate ");
     transPS = transConn.prepareStatement(sbMaxID.toString());
     transRS = transPS.executeQuery();
     if (transRS.next())
     {
     lMaxID = transRS.getLong(1);
     } else
     {
     lResult = 1;
     }
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;

     //插入loan_InterestRate
     strSQL1 = "insert into loan_interestrate (ID,NBANKINTERESTTYPEID,mRate,dtValidate,nInputUserID,dtInput)"
     + "values (?,?,?,?,?,?) ";

     transPS = transConn.prepareStatement(strSQL1);
     transPS.setLong(1, lMaxID);
     transPS.setLong(2, lBankLoanInterestRateID);
     transPS.setDouble(3, dInterestRate);
     transPS.setTimestamp(4, tsValibleDate);
     transPS.setLong(5, lInputUserID);
     transPS.setTimestamp(6, tsInputDate);
     //transPS.setLong(7,lCurrencyID);

     transPS.executeUpdate();
     lResult = lMaxID;
     transPS.close();
     transPS = null;
     ////////////////////////////
     //生效日的问题由页面去控制
     /////////////////////////////
     }
     ////////////////////////////////////////////////////////////
     //以下是更新////////////////////////////////////////////////
     ////////////////////////////////////////////////////////////
     else
     {

     StringBuffer sb = new StringBuffer();
     StringBuffer sbCondition = new StringBuffer();
     StringBuffer sbNumber = new StringBuffer();
     //获得LOAN_InterestRateTypeInfo中相应的id
     strSQL = "select ID from  LOAN_InterestRateTypeInfo where sInterestRateNo=? and  NCURRENCYID="
     + lCurrencyID;
     log4j.info("sql2 is :" + strSQL);
     transPS = transConn.prepareStatement(strSQL);

     transPS.setString(1, sInterestRateCode);
     transRS = transPS.executeQuery();
     if (transRS != null && transRS.next())
     {
     lBankLoanInterestRateID = transRS.getLong("ID");
     }
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;

     //should not effective in this field /////////////
     ////////////////////////////////////////////////////
     ////验证新增纪录的的唯一，是否与已有记录内容相冲突(如果存在其他纪录与录入的纪录的生效日重合，则返回)
     //strSQL = "select ID from loan_interestrate where
     // NBANKINTERESTTYPEID="+lBankLoanInterestRateID+" and
     // to_char(DTVALIDATE,'yyyymmdd')=to_char(?,'yyyymmdd') and id != ?
     // and mRate = ?";
     strSQL = "select ID from loan_interestrate where to_char(DTVALIDATE,'yyyymmdd')=to_char(?,'yyyymmdd') and id != ? and mRate = ?";
     transPS = transConn.prepareStatement(strSQL);
     transPS.setTimestamp(1, tsValibleDate);
     transPS.setLong(2, lID);
     transPS.setDouble(3, dInterestRate);
     rss1 = transPS.executeQuery();
     if (rss1 != null && rss1.next())
     {
     rss1.close();
     rss1 = null;
     transPS.close();
     transPS = null;

     transConn = null;
     return -2;
     }
     rss1.close();
     rss1 = null;
     transPS.close();
     transPS = null;
     ////////////////////////////////////////////////////

     ////////////////////////////////////////////////////
     ////验证新增纪录的的唯一，是否与已有记录内容相冲突(如果只修改了利率值，没有修改生效日或生效日在当前生效日之前，则只是update原先的纪录，而不是新增纪录)
     //strSQL = "select ID from loan_interestrate where
     // NBANKINTERESTTYPEID="+lBankLoanInterestRateID+" and
     // to_char(DTVALIDATE,'yyyymmdd')>=to_char(?,'yyyymmdd') ";//11.21
     // change by fanyang
     strSQL = "select ID from loan_interestrate where NBANKINTERESTTYPEID="
     + lBankLoanInterestRateID
     + " and to_char(DTVALIDATE,'yyyymmdd')>=to_char(?,'yyyymmdd') ";
     transPS = transConn.prepareStatement(strSQL);
     transPS.setTimestamp(1, tsValibleDate);
     //transPS.setDouble(2,dInterestRate);
     transRS = transPS.executeQuery();
     if (transRS != null && transRS.next())
     {
     log4j.info("1-------------------------1");
     lInsertOrUpdate = 1; //修改
     }
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;
     ////////////////////////////////////////////////////
     if (lInsertOrUpdate == 1) //修改
     {
     strSQL = "update loan_interestrate set mRate = ?,dtValidate=? where  id = ?";
     log4j.info("strSQL = " + strSQL);
     transPS = transConn.prepareStatement(strSQL);
     transPS.setDouble(1, dInterestRate);
     transPS.setTimestamp(2, tsValibleDate);
     transPS.setLong(3, lID);
     transPS.executeUpdate();
     lResult = lID;
     transPS.close();
     transPS = null;
     //如果修改成功，则修改该利率在adjustedcontract中的利率值和生效日
     strSQL = "update LOAN_RATEADJUSTCONTRACTDETAIL set mRate = ?,DTSTARTDATE=? where  NBANKINTERESTID = ?";
     log4j.info("strSQL = " + strSQL);
     transPS = transConn.prepareStatement(strSQL);
     transPS.setDouble(1, dInterestRate);
     transPS.setTimestamp(2, tsValibleDate);
     transPS.setLong(3, lID);
     transPS.executeUpdate();
     lResult = lID;
     transPS.close();
     transPS = null;
     } else
     //新增
     {
     StringBuffer sbMaxID = new StringBuffer();
     sbMaxID
     .append(" SELECT nvl(max(id),0) + 1 FROM loan_interestrate ");
     transPS = transConn.prepareStatement(sbMaxID.toString());
     transRS = transPS.executeQuery();
     if (transRS.next())
     {
     lMaxID = transRS.getLong(1);
     } else
     {
     lResult = 1;
     }
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;

     strSQL1 = "insert into loan_interestrate (ID,NBANKINTERESTTYPEID,mRate,dtValidate,nInputUserID,dtInput)"
     + "values (?,?,?,?,?,?) ";

     transPS = transConn.prepareStatement(strSQL1);
     transPS.setLong(1, lMaxID);
     transPS.setLong(2, lBankLoanInterestRateID);
     transPS.setDouble(3, dInterestRate);
     //need to modify
     //transPS.setDouble(3,0.3);
     transPS.setTimestamp(4, tsValibleDate);
     transPS.setLong(5, lInputUserID);
     transPS.setTimestamp(6, tsInputDate);
     //transPS.setLong(7,lCurrencyID);
     transPS.executeUpdate();
     lResult = lMaxID;
     transPS.close();
     transPS = null;
     }

     transConn = null;
     return lResult;
     }//else

     return lResult;

     }*/

    /**
     * addBankInterestReteSetting  新增银行利率
     * 根据所给定的条件新增或修改银行利率
     * 操作InterestRateTypeInfo、InterestRateHistory数据表
     * 新增或修改相应记录
     * 对于BankLoanInterestRateHistory，如果lID〉0，修改  lID=0,新增
     * 新增步骤：
     *    1  根据利率编号从InterestRateTypeInfo中查处利率类型标示
     *    2  将利率类型标示和其他相关字段插入InterestRateHistory
     *       条件：1 生效日 〉 当前日
     *             2 对同一利率类型标示,同一币种，生效日不能重合
     *    3 如成功插入，修改InterestRateTypeInfo
     *      如果该类型的纪录已存在，用sInterestRateName替换原先的利率名称
     * 修改时，如果该利率已做过利率调整，要修改利率调整表中的利率生效日期
     *       条件：1 生效日 〉 当前日
     *             2 对同一利率类型标示,同一币种，生效日不能重合
     * @param lID 标识
     * @param strInterestRateCode 利率编号
     * @param strInterestRateName 利率名称
     * @param dInterestRate 利率
     * @param tsValibleDate 利率生效日
     * @param lInputUserID 录入人
     * @param tsInputDate 录入日期
     * @param lCurrencyID 币种
     * @return long 成功返回ID标识，失败返回0
     * @throws SettlementDAOException
     
     */
    public long addSett_InterestRateSettingInfo(long lID,
            long lBankLoanInterestRateID, double dInterestRate,
            Timestamp tsValibleDate, long lInputUserID, Timestamp tsInputDate,
            long lCurrencyID) throws SettlementDAOException
    {
        long lTemp = -1;

        StringBuffer sbSQL = new StringBuffer();
        try
        {
            this.initDAO();
            sbSQL.append(" insert into LOAN_InterestRateTypeInfo ");
            sbSQL
                    .append(" (ID,NBANKINTERESTTYPEID,mRate,dtValidate,nInputUserID,dtInput) ");
            sbSQL.append(" values (?,?,?,?,?) ");
            System.out.println("sql语句为--->>>>>>>     " + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());
            int index = 1;
            transPS.setLong(index++, lID);
            transPS.setLong(index++, lBankLoanInterestRateID);
            transPS.setDouble(index++, dInterestRate);
            transPS.setTimestamp(index++, tsValibleDate);
            transPS.setLong(index++, lInputUserID);
            transPS.setTimestamp(index++, tsInputDate);
            transPS.executeUpdate();
            //transPS.setLong(index++, lCurrencyID);
            lTemp = lID;
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lTemp;
    }

    /**
     * 
     * @param dInterestRate
     * @param tsValibleDate
     * @return
     * @throws SettlementDAOException
     */
    public boolean findInterestRateCodeByDate(double dInterestRate,
            Timestamp tsValibleDate) throws SettlementDAOException
    {
        boolean boolTemp = false;
        Vector vectTemp = new Vector();
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" select id from LOAN_interestrate  ");
            sbSQL.append(" where mrate=  ");
            sbSQL.append(dInterestRate);
            sbSQL
                    .append(" and to_char(DTVALIDATE,'yyyymmdd')=to_char(?,'yyyymmdd') ");

            System.out.println("查询的sql为  ---->>>>     " + sbSQL);

            transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setTimestamp(1, tsValibleDate);
            transRS = transPS.executeQuery();

            String strTemp = null;
            long ltemp = -1;
            while (transRS.next())
            {
                ltemp = transRS.getLong("id");
                vectTemp.add(String.valueOf(ltemp));
                //vectTemp.add(transRS.getString("id"));
            }
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (null != vectTemp)
        {
            if (vectTemp.size() > 0)
                boolTemp = true;
        }
        return boolTemp;
    }

    /**
     * 
     * @param dInterestRate
     * @param tsValibleDate
     * @return
     * @throws SettlementDAOException
     */
    public boolean findInterestRateIDByDate(Timestamp tsValibleDate, long lID,
            double dInterestRate) throws SettlementDAOException
    {
        boolean boolTemp = false;
        Vector vectTemp = new Vector();
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" select id from LOAN_interestrate  ");
            sbSQL
                    .append(" where to_char(DTVALIDATE,'yyyymmdd')=to_char(?,'yyyymmdd')  ");
            sbSQL.append(" and id != ? and mRate = ? ");

            System.out.println("查询的sql为  ---->>>>     " + sbSQL);

            transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setTimestamp(1, tsValibleDate);
            transPS.setLong(2, lID);
            transPS.setDouble(3, dInterestRate);
            transRS = transPS.executeQuery();

            String strTemp = null;
            long ltemp = -1;
            while (transRS.next())
            {
                ltemp = transRS.getLong("id");
                vectTemp.add(String.valueOf(ltemp));
                //vectTemp.add(transRS.getString("id"));
            }
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (null != vectTemp)
        {
            if (vectTemp.size() > 0)
                boolTemp = true;
        }
        return boolTemp;
    }

    public boolean findInterestRateIDCondition(long lBankLoanInterestRateID,
            Timestamp tsValibleDate) throws SettlementDAOException
    {
        boolean boolTemp = false;
        Vector vectTemp = new Vector();
        Log.print("参数为###########       "+lBankLoanInterestRateID+"         "+  tsValibleDate );
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" select ID from loan_interestrate  ");
            sbSQL.append(" where NBANKINTERESTTYPEID= ?  ");
            sbSQL
                    .append(" and to_char(DTVALIDATE,'yyyymmdd')>=to_char(?,'yyyymmdd') ");

            System.out.println("查询的sql为  ---->>>>     " + sbSQL);
            
            transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setLong(1, lBankLoanInterestRateID);
            transPS.setTimestamp(2, tsValibleDate);

            transRS = transPS.executeQuery();

            String strTemp = null;
            long ltemp = -1;
            while (transRS.next())
            {
                ltemp = transRS.getLong("id");
                vectTemp.add(String.valueOf(ltemp));
                //vectTemp.add(transRS.getString("id"));
            }
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (null != vectTemp)
        {
            if (vectTemp.size() > 0)
                boolTemp = true;
        }
        return boolTemp;
    }

    /**
     * 查询最大利率ID
     * @return
     * @throws SettlementDAOException
     */
    public long findMaxInterestRateSettingId() throws SettlementDAOException
    {
        long lTemp = -1;
        StringBuffer sbSQL = new StringBuffer();
        try
        {
            this.initDAO();

            sbSQL.append(" SELECT nvl(max(id),0) + 1 FROM loan_interestrate ");

            System.out.println("sql语句为--->>>>>>>     " + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());

            if (transRS != null && transRS.next())
            {
                lTemp = transRS.getLong(1);
            }
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lTemp;
    }

    public long updateADJUSTCONTRACTDETAIL(double dInterestRate,
            Timestamp tsValibleDate, long lID) throws SettlementDAOException
    {
        long lTemp = -1;
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" update LOAN_RATEADJUSTCONTRACTDETAIL  ");
            sbSQL
                    .append(" set mRate = ?,DTSTARTDATE=? where  NBANKINTERESTID = ? ");

            System.out.println("sql语句为--->>>>>>>     " + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());
            int index = 1;
            transPS.setDouble(index++, dInterestRate);
            transPS.setTimestamp(index++, tsValibleDate);
            transPS.setLong(index++, lID);
            lTemp = transPS.executeUpdate();
            this.finalizeDAO();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lTemp;

    }

    //测试入口
    public static void main(String[] args) throws SettlementDAOException
    {
        Sett_InterestRateSettingDAO testDao = new Sett_InterestRateSettingDAO(
                "loan_InterestRate");
        SettInterestRateSettingInfo testInfo = new SettInterestRateSettingInfo();
        try
        {
            testInfo = (SettInterestRateSettingInfo) testDao.findByID(2,
                    SettInterestRateSettingInfo.class);
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null != testInfo)
        {
            System.out.println("利率ID为" + testInfo.getId() + "利率类型"
                    + testInfo.getNBankInterestTypeId() + "利率数值>>>"
                    + testInfo.getMRate());
        }
    }

   
}