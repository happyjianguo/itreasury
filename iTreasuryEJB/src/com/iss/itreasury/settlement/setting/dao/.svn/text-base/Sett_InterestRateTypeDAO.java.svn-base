/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.setting.dataentity.SettInterestRateTypeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author jinchen
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_InterestRateTypeDAO extends SettlementDAO
{
    /**
     * @param tableName
     * @param isNeedPrefix
     */
    private Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    public Sett_InterestRateTypeDAO()
    {
        super("loan_interestratetypeinfo");
        // TODO Auto-generated constructor stub
    }

    public Sett_InterestRateTypeDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param tableName
     */
    public Sett_InterestRateTypeDAO(String tableName)
    {
        super(tableName);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param tableName
     * @param conn
     */
    public Sett_InterestRateTypeDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
        // TODO Auto-generated constructor stub
    }

    /**
     * findMaxInterestRateCode 查找最大银行利率编号 操作InterestRateType数据表 查询相应记录
     * 
     * @param lCurrencyID
     *            币种标示
     * 
     * @return String 最大银行利率编号
     * @throws
     */

    public String findMaxInterestRateCode(long lCurrencyID)
            throws SettlementDAOException
    {
        long ltemp = 1;
        String MaxCode = "";
        StringBuffer sb = new StringBuffer();
        String strSql = "";
        try
        {
            this.initDAO();

            strSql = " select id from ( select id from LOAN_INTERESTRATETYPEINFO where NCURRENCYID="
                    + lCurrencyID
                    + " minus  select to_number(SINTERESTRATENO) scode from LOAN_INTERESTRATETYPEINFO   where NCURRENCYID="
                    + lCurrencyID + ")";

            transPS = transConn.prepareStatement(strSql);
            transRS = transPS.executeQuery();
            if (transRS.next())
            { // 如果有跳过的scode，通过下面方sql获取被跳过的scode；如果没有跳过的scode，数据库查询结果返回0。
                ltemp = transRS.getLong(1);
            } else
            //取最大值加一
            {
                sb.append(" SELECT max(nvl(SINTERESTRATENO,'001'))  ");
                sb.append(" FROM LOAN_INTERESTRATETYPEINFO where NCURRENCYID="
                        + lCurrencyID);
                transPS = transConn.prepareStatement(sb.toString());
                System.out.print("sql is: " + sb.toString());
                transRS = transPS.executeQuery();
                if (transRS.next())
                {
                    MaxCode = transRS.getString(1);
                } else
                {
                    MaxCode = "0";
                }
                MaxCode = MaxCode != null ? MaxCode : "0";
                //Common.log(" MaxCode : " + MaxCode );
                ltemp = Long.parseLong(MaxCode);
                ltemp = ltemp + 1;
                log4j.info("ltemp is " + ltemp);
            }//end else

            MaxCode = String.valueOf(ltemp);
            for (int j = MaxCode.length(); j < 3; j++)
            {
                MaxCode = "0" + MaxCode;
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
        return MaxCode;
    }

    /**
     * 根据货币ID,利率编号,查询利率
     * 
     * @param sInterestRateCode
     * @param lCurrencyID
     * @return @throws
     *  
     */
    public boolean findInterestRateCodeByCode(String sInterestRateCode,
            long lCurrencyID) throws SettlementDAOException
    {
        boolean boolTemp = false;
        Vector vectTemp = new Vector();
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL
                    .append(" select SINTERESTRATENO from LOAN_INTERESTRATETYPEINFO ");
            sbSQL.append(" where SINTERESTRATENO= ? and NCURRENCYID = ? ");
           

            System.out.println("查询的sql为  ---->>>>     " + sbSQL);

            transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setString(1,sInterestRateCode);
            transPS.setLong(2,lCurrencyID);
            transRS = transPS.executeQuery();

            String strTemp = null;

            while (transRS.next())
            {
                vectTemp.add(transRS.getString("SINTERESTRATENO"));
            }

            this.finalizeDAO();
            if (null != vectTemp)
            {
                if (vectTemp.size() > 0)
                    boolTemp = true;
            }

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

        return boolTemp;
    }

    public boolean findInterestRateCodeByName(String sInterestRateName,
            long lCurrencyID) throws SettlementDAOException
    {
        boolean boolTemp = false;
        Vector vectTemp = new Vector();

        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL
                    .append(" select SINTERESTRATENO from LOAN_INTERESTRATETYPEINFO ");
            sbSQL.append(" where SINTERESTRATEName= ? and NCURRENCYID= ? ");
            

            System.out.println("查询的sql为  ---->>>>     " + sbSQL);

            transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setString(1,sInterestRateName);
            transPS.setLong(2,lCurrencyID);
            transRS = transPS.executeQuery();

            String strTemp = null;

            while (transRS.next())
            {
                vectTemp.add(transRS.getString("SINTERESTRATENO"));
            }

            this.finalizeDAO();
            if (null != vectTemp)
            {
                if (vectTemp.size() > 0)
                    boolTemp = true;
            }

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new SettlementDAOException(e1);
        }

        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return boolTemp;
    }

    /**
     * 根据货币ID利率编号 查询利率类型ID
     * 
     * @param sInterestRateCode
     * @param lCurrencyID
     * @return @throws
     *  
     */
    public long findInterestRateTypeIdbyCondition(String sInterestRateCode,
            long lCurrencyID) throws SettlementDAOException
    {
        long lTemp = -1;
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" select ID from  LOAN_InterestRateTypeInfo ");
            sbSQL.append(" where sInterestRateNo=? and  NCURRENCYID=? ");

            System.out.println("sql语句为--->>>>>>>     " + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setString(1, sInterestRateCode);
            transPS.setLong(2, lCurrencyID);
            transRS = transPS.executeQuery();
            if (transRS != null && transRS.next())
            {
                lTemp = transRS.getLong("ID");
            }

            this.finalizeDAO();

        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return lTemp;
    }

    /**
     * 根据货币ID, 查询最大利率类型ID
     * 
     * @param sInterestRateCode
     * @param lCurrencyID
     * @return @throws
     *  
     */
    public long findMaxInterestRateTypeIdbyCondition(String sInterestRateCode,
            long lCurrencyID) throws SettlementDAOException
    {
        long lTemp = -1;
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL
                    .append(" SELECT nvl(max(id),0) + 1 FROM LOAN_INTERESTRATETYPEINFO ");
            sbSQL.append(" where NCURRENCYID=? ");

            System.out.println("sql语句为--->>>>>>>     " + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());
            transPS.setLong(1, lCurrencyID);
            transRS = transPS.executeQuery();
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

    public long addSett_InterestRateTypeInfo(long lID,
            String sInterestRateCode, String sInterestRateName, long lCurrencyID)
            throws SettlementDAOException
    {
        long lTemp = -1;
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" insert into LOAN_InterestRateTypeInfo ");
            sbSQL
                    .append(" (ID, SINTERESTRATENO, SINTERESTRATENAME, NCURRENCYID ) ");
            sbSQL.append(" values (?,?,?,?) ");
            System.out.println("sql语句为--->>>>>>>     " + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());
            int index = 1;
            transPS.setLong(index++, lID);
            transPS.setString(index++, sInterestRateCode);
            transPS.setString(index++, sInterestRateName);
            transPS.setLong(index++, lCurrencyID);
            transPS.executeUpdate();
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

    public long updateSett_InterestRateTypeInfoName(String sInterestRateName,
            long lCurrencyID) throws SettlementDAOException
    {
        long lTemp = -1;
        try
        {
            this.initDAO();

            StringBuffer sbSQL = new StringBuffer();
            sbSQL
                    .append(" UPDATE   LOAN_InterestRateTypeInfo SET SINTERESTRATENAME=?  ");
            sbSQL.append(" where sInterestRateNo=? and NCURRENCYID=? ");
            System.out.println("sql语句为--->>>>>>>     " + sbSQL.toString());

            transPS = transConn.prepareStatement(sbSQL.toString());
            int index = 1;

            transPS.setString(index++, sInterestRateName);
            transPS.setLong(index++, lCurrencyID);
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

    //测试
    public static void main(String[] args)
    {
        Sett_InterestRateTypeDAO testDao = new Sett_InterestRateTypeDAO(
                "loan_interestratetypeinfo");
        testDao.setUseMaxID();
        SettInterestRateTypeInfo testInfo = new SettInterestRateTypeInfo();
        testInfo.setNCurrencyId(1);
        testInfo.setSInterestRateName("34343");
        testInfo.setSInterestRateNo("094");
        long ltemp = -1;
        try
        {
           ltemp = testDao.add(testInfo);
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
       System.out.println("dddddddddddddddddddddddd" + ltemp);
    }
}