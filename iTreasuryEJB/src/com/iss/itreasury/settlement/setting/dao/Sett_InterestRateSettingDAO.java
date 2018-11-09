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
     * findInterestByID ����������Ϣ ������������������Ϣ ���� BANKLOANINTERESTRATE ���ݱ� ��ѯ��Ӧ��¼
     * 
     * @param long
     *            lID ���ʱ�ʾ
     * @return InterestRateInfo
     * @throws SettlementDAOException
     * @throws RemoteException
     */
    public SettInterestRateSettingInfo findInterestRateByID(long lID)
            throws SettlementDAOException
    {
        //    	���ܻ�����Ҫ�����Ϣ
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

            Log.print("SQL  ####################���                     " + sb);
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
                // //���ʵ�ʾ����ϱߵ�BankInterestTypeID

            }

            transRS.close();
            transRS = null;
            transPS.close();
            transPS = null;

            sb.setLength(0);
            sb.append(" SELECT *  ");
            sb.append(" FROM userinfo ");
            sb.append(" WHERE id=? ");
            Log.print("SQL  ####################���                     " + sb);
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
            Log.print("SQL  ####################���                     " + sb);
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
     * findInterestRate �����������ò��� ���������������Ͳ�ѯ������������ ����InterestRateHistory���ݱ� ����
     * ������Ч���ڵ�ǰ����֮�� �ļ�¼ ��ѯ��Ӧ��¼
     * 
     * @param lTypeID
     *            �������ʱ��
     * @param lCurrencyID
     *            ����
     * @return Collection ����������Ϣ
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
            //�����¼����
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
            //������ҳ��
            lPageCount = lRecordCount / lPageLineCount;
            if ((lRecordCount % lPageLineCount) != 0)
            {
                lPageCount++;
            }
            //��������Ľ����
            //��ҳ��ʾ����ʾ��һҳ
            lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
            lRowNumEnd = lRowNumStart + lPageLineCount - 1;
            ////////////////////////////////////////////
            sb.setLength(0);
            //���в���
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
            System.out.println("SQL           Ϊ-----------------"
                    + strSQL.toString());
            System.out
                    .println("------>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            strTmpSQL = "";
            int nIndex = 0;
            System.out.println("����������������Ϊ:    " + sOrderParam);
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
            System.out.println("SQL           Ϊ-----------------"
                    + strTmpSQL.toString());
            System.out
                    .println("------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //strSQL +=strTmpSQL;
            //�ж��������ǽ���������ϵͳĬ�ϵģ�������desc
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
            System.out.println("SQL           Ϊ-----------------"
                    + strSQL.toString());
            System.out
                    .println("->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            transRS = transPS.executeQuery();
            while (transRS != null && transRS.next())
            { //����SettInterestRateSettingInfo
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
                log4j.info("�� EJB ��" + ii.getId());
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
            //������ҳ��
            lPageCount = lRecordCount / lPageLineCount;
            if ((lRecordCount % lPageLineCount) != 0)
            {
                lPageCount++;
            }

            //��������Ľ����

            //��ҳ��ʾ����ʾ��һҳ
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

            //���������

            int nIndex = 0;
            System.out.println("����������������Ϊ:    " + sOrderParam);
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

            //�ж��������ǽ���������ϵͳĬ�ϵģ�������desc
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
            { //����InterestRateInfo
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
                //log4j.info("�� EJB ��" + ii.getId());
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
			//������ҳ��
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}

			//��������Ľ����

			//��ҳ��ʾ����ʾ��һҳ
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
			
		       //���������

            int nIndex = 0;
            System.out.println("����������������Ϊ:    " + sOrderParam);
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


			//�ж��������ǽ���������ϵͳĬ�ϵģ�������desc
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
			{   //����InterestRateInfo
			    
			    
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
                //log4j.info("�� EJB ��" + ii.getId());
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
     * addBankInterestReteSetting �������޸��������� �����������������������޸���������
     * ����InterestRateTypeInfo��InterestRateHistory���ݱ� �������޸���Ӧ��¼
     * ����BankLoanInterestRateHistory�����lID��0���޸� lID=0,���� �������裺 1
     * �������ʱ�Ŵ�InterestRateTypeInfo�в鴦�������ͱ�ʾ 2
     * ���������ͱ�ʾ����������ֶβ���InterestRateHistory ������1 ��Ч�� �� ��ǰ�� 2
     * ��ͬһ�������ͱ�ʾ,ͬһ���֣���Ч�ղ����غ� 3 ��ɹ����룬�޸�InterestRateTypeInfo
     * ��������͵ļ�¼�Ѵ��ڣ���sInterestRateName�滻ԭ�ȵ��������� �޸�ʱ��������������������ʵ�����Ҫ�޸����ʵ������е�������Ч����
     * ������1 ��Ч�� �� ��ǰ�� 2 ��ͬһ�������ͱ�ʾ,ͬһ���֣���Ч�ղ����غ�
     * 
     * @param lID
     *            ��ʶ
     * @param strInterestRateCode
     *            ���ʱ��
     * @param strInterestRateName
     *            ��������
     * @param dInterestRate
     *            ����
     * @param tsValibleDate
     *            ������Ч��
     * @param lInputUserID
     *            ¼����
     * @param tsInputDate
     *            ¼������
     * @param lCurrencyID
     *            ����
     * @return long �ɹ�����ID��ʶ��ʧ�ܷ���0
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

     //���������
     if (lID == 0)
     {

     StringBuffer sb = new StringBuffer();
     StringBuffer sbCondition = new StringBuffer();
     StringBuffer sbNumber = new StringBuffer();
     /////////////////////////////////////////////////
     ////��֤SINTERESTRATENO��Ψһ

     ////////////////////////////////////////////////////

     ////��֤sInterestRateName��Ψһ

     ////��֤loan_interestrete��mRate,dtvalidate��Ψһ

     //������������
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
     //������������ʼ�¼������
     //�������ݿ�������
     //SINTERESTRATENAME�ں�������¸���һ��
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
     //���ڲ��� loan_InterestRate
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

     //������id��1
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

     //����loan_InterestRate
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
     //��Ч�յ�������ҳ��ȥ����
     /////////////////////////////
     }
     ////////////////////////////////////////////////////////////
     //�����Ǹ���////////////////////////////////////////////////
     ////////////////////////////////////////////////////////////
     else
     {

     StringBuffer sb = new StringBuffer();
     StringBuffer sbCondition = new StringBuffer();
     StringBuffer sbNumber = new StringBuffer();
     //���LOAN_InterestRateTypeInfo����Ӧ��id
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
     ////��֤������¼�ĵ�Ψһ���Ƿ������м�¼�������ͻ(�������������¼��¼��ļ�¼����Ч���غϣ��򷵻�)
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
     ////��֤������¼�ĵ�Ψһ���Ƿ������м�¼�������ͻ(���ֻ�޸�������ֵ��û���޸���Ч�ջ���Ч���ڵ�ǰ��Ч��֮ǰ����ֻ��updateԭ�ȵļ�¼��������������¼)
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
     lInsertOrUpdate = 1; //�޸�
     }
     transRS.close();
     transRS = null;
     transPS.close();
     transPS = null;
     ////////////////////////////////////////////////////
     if (lInsertOrUpdate == 1) //�޸�
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
     //����޸ĳɹ������޸ĸ�������adjustedcontract�е�����ֵ����Ч��
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
     //����
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
     * addBankInterestReteSetting  ������������
     * �����������������������޸���������
     * ����InterestRateTypeInfo��InterestRateHistory���ݱ�
     * �������޸���Ӧ��¼
     * ����BankLoanInterestRateHistory�����lID��0���޸�  lID=0,����
     * �������裺
     *    1  �������ʱ�Ŵ�InterestRateTypeInfo�в鴦�������ͱ�ʾ
     *    2  ���������ͱ�ʾ����������ֶβ���InterestRateHistory
     *       ������1 ��Ч�� �� ��ǰ��
     *             2 ��ͬһ�������ͱ�ʾ,ͬһ���֣���Ч�ղ����غ�
     *    3 ��ɹ����룬�޸�InterestRateTypeInfo
     *      ��������͵ļ�¼�Ѵ��ڣ���sInterestRateName�滻ԭ�ȵ���������
     * �޸�ʱ��������������������ʵ�����Ҫ�޸����ʵ������е�������Ч����
     *       ������1 ��Ч�� �� ��ǰ��
     *             2 ��ͬһ�������ͱ�ʾ,ͬһ���֣���Ч�ղ����غ�
     * @param lID ��ʶ
     * @param strInterestRateCode ���ʱ��
     * @param strInterestRateName ��������
     * @param dInterestRate ����
     * @param tsValibleDate ������Ч��
     * @param lInputUserID ¼����
     * @param tsInputDate ¼������
     * @param lCurrencyID ����
     * @return long �ɹ�����ID��ʶ��ʧ�ܷ���0
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
            System.out.println("sql���Ϊ--->>>>>>>     " + sbSQL.toString());

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

            System.out.println("��ѯ��sqlΪ  ---->>>>     " + sbSQL);

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

            System.out.println("��ѯ��sqlΪ  ---->>>>     " + sbSQL);

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
        Log.print("����Ϊ###########       "+lBankLoanInterestRateID+"         "+  tsValibleDate );
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" select ID from loan_interestrate  ");
            sbSQL.append(" where NBANKINTERESTTYPEID= ?  ");
            sbSQL
                    .append(" and to_char(DTVALIDATE,'yyyymmdd')>=to_char(?,'yyyymmdd') ");

            System.out.println("��ѯ��sqlΪ  ---->>>>     " + sbSQL);
            
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
     * ��ѯ�������ID
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

            System.out.println("sql���Ϊ--->>>>>>>     " + sbSQL.toString());

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

            System.out.println("sql���Ϊ--->>>>>>>     " + sbSQL.toString());

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

    //�������
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
            System.out.println("����IDΪ" + testInfo.getId() + "��������"
                    + testInfo.getNBankInterestTypeId() + "������ֵ>>>"
                    + testInfo.getMRate());
        }
    }

   
}