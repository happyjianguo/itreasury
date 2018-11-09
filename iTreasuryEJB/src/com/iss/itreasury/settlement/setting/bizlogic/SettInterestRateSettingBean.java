/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

//import java.sql.Connection;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_InterestRateSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_InterestRateTypeDAO;
import com.iss.itreasury.settlement.setting.dataentity.SettInterestRateSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettInterestRateTypeInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettSettingRateQueryInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author jinchen
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SettInterestRateSettingBean
{
    /**
     *  
     */
    public SettInterestRateSettingBean()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * 创建数据库连接java.sql.Connection
     */
    private Connection initBean() throws SettlementDAOException
    {
        Connection transConn = null;
        try
        {

            transConn = Database.getConnection();
            transConn.setAutoCommit(false);

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new SettlementDAOException("数据库初使化异常发生", e);
        }
        return transConn;
    }

    /*
     * 释放数据库连接
     */
    private void finalizeBean(Connection transConn)
            throws SettlementDAOException
    {
        try
        {
            if (transConn != null)
            {
                transConn.close();
                transConn = null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new SettlementDAOException("数据库关闭异常发生", e);
        }
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
        //Connection conn = null;

        String strTemp = null;
        Sett_InterestRateTypeDAO ratetypeDao = new Sett_InterestRateTypeDAO();
        strTemp = ratetypeDao.findMaxInterestRateCode(lCurrencyID);
        return strTemp;
    }

    /**
     * findInterestByID 查找利率信息 根据条件查找利率信息 操作 BANKLOANINTERESTRATE 数据表 查询相应记录
     * 
     * @param long
     *            lID 利率标示
     * @return InterestRateInfo
     * @throws ITreasuryDAOException
     * @throws RemoteException
     */
    public SettInterestRateSettingInfo findInterestRateByID(long lID)
            throws SettlementException
    {
        SettInterestRateSettingInfo tempInfo = null;
        Sett_InterestRateSettingDAO rateDao = new Sett_InterestRateSettingDAO();
        tempInfo = rateDao.findInterestRateByID(lID);

        return tempInfo;

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
     * @throws ITreasuryDAOException
     * @throws RemoteException
     */
    public Collection findInterestRateByTypeID(
            SettSettingRateQueryInfo queryInfo) throws SettlementException
    {
        Collection c = null;
        Sett_InterestRateSettingDAO rateDao = new Sett_InterestRateSettingDAO();
        c = rateDao.findInterestRateByTypeID(queryInfo);
        return c;

    }

    /**
     * addBankInterestReteSetting 新增银行利率 根据所给定的条件新增或修改银行利率
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
     * @throws SettlementDAOException
     */
    public long addSett_InterestRateSetting(
            SettInterestRateSettingInfo rateInfo,
            SettInterestRateTypeInfo ratetypeInfo)
            throws SettlementException
    {

        Connection transConn = null;

        //long lID = rateInfo.getID();
        

        double dInterestRate = rateInfo.getMRate();
        Timestamp tsValibleDate = rateInfo.getDtValiDate();
        long lInputUserID = rateInfo.getNInputUserId();
        Timestamp tsInputDate = rateInfo.getDtInput();
        
        long lCurrencyID = ratetypeInfo.getNCurrencyId();
        String sInterestRateName = ratetypeInfo.getSInterestRateName();
        String sInterestRateCode = ratetypeInfo.getSInterestRateNo();
        
        long lTemp = -1;

        long lResult = -5;
        long lBankLoanInterestRateID = -1;
        long lMaxID = 0;
        int iIndex = 0;
        int iNumber = 0;
        int i = 0;
        long lInsertOrUpdate = 0;
        
        //错误信息
        String strError = "";
           
       /* Log.print("@@@@@@@@@#################              "+sInterestRateCode+
                "@@@@@@@@@#################              "+dInterestRate+
                "@@@@@@@@@#################              "+lInputUserID+
                "@@@@@@@@@#################              "+tsInputDate
                		);*/
        transConn = this.initBean();
        try
        {
            Sett_InterestRateSettingDAO rateDao = new Sett_InterestRateSettingDAO(
                    "loan_InterestRate", transConn);
            rateDao.setUseMaxID();
            Sett_InterestRateTypeDAO ratetypeDao = new Sett_InterestRateTypeDAO(
                    "loan_interestratetypeinfo", transConn);
            ratetypeDao.setUseMaxID();
            //////验证SINTERESTRATENO的唯一
            if (ratetypeDao.findInterestRateCodeByCode(sInterestRateCode,
                    lCurrencyID))
            {
                strError =  " 此贷款利率编号已经存在，请重新输入！";
            }

            ////验证sInterestRateName的唯一
            if (ratetypeDao.findInterestRateCodeByName(sInterestRateName,
                    lCurrencyID))
            {
                lResult = -2;
                strError =  " 此贷款利率名称已经存在，请重新输入！";
            }

            ////验证loan_interestrete中mRate,dtvalidate的唯一
            if (rateDao
                    .findInterestRateCodeByDate(dInterestRate, tsValibleDate))
            {
                lResult = -3;
                strError =  " 此贷款利率值和生效日已经存在，请重新输入！";
            }
            
            //抛出自定义异常
            if (strError != null && strError.length() > 0)
			{
				throw new SettlementException("Sett_E180",strError, null);
			}
            Log.print("通过验证..........................................................................");
            
            lTemp = ratetypeDao.findInterestRateTypeIdbyCondition(
                    sInterestRateCode, lCurrencyID);
            if (lTemp > 0)
            {
                lBankLoanInterestRateID = lTemp;
            } else
            {
                /*
                 * lTemp=
                 * ratetypeDao.findMaxInterestRateTypeIdbyCondition(sInterestRateCode,lCurrencyID);
                 * if(lTemp>0) { lMaxID =lTemp; } else { lResult = 1; }
                 */
                lTemp = ratetypeDao.add(ratetypeInfo);
                Log.print("更新type成功---------------------------------."   + lTemp);
                if (lTemp > 0)
                {
                    lMaxID = lTemp;
                    lBankLoanInterestRateID = lMaxID;
                } else
                {
                    lResult = 1;
                }

            }//end else

            ratetypeDao.updateSett_InterestRateTypeInfoName(sInterestRateName,
                    lCurrencyID);

            rateInfo.setNBankInterestTypeId(lBankLoanInterestRateID);

            rateDao.add(rateInfo);
            transConn.commit();

        } catch (ITreasuryDAOException e)
        {
            try
            {
                transConn.rollback();
                Log.print("系统错误，已经回滚到上一次成功提交状态。");
            } catch (SQLException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new SettlementDAOException("系统错误，已经回滚到上一次成功提交状态。",e);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        this.finalizeBean(transConn);
        return lResult;

    }

    /**
     * addBankInterestReteSetting 修改银行利率 根据所给定的条件新增或修改银行利率
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
     * @throws SettlementDAOException
     */
    public long updateSett_InterestRateSetting(
            SettInterestRateSettingInfo rateInfo,
            SettInterestRateTypeInfo ratetypeInfo)
            throws SettlementException
    {
        Connection transConn = null;

        long lID = rateInfo.getId();
        
        
        double dInterestRate = rateInfo.getMRate();
        Timestamp tsValibleDate = rateInfo.getDtValiDate();
        long lInputUserID = rateInfo.getNInputUserId();
        Timestamp tsInputDate = rateInfo.getDtInput();
        
        
        long lCurrencyID = ratetypeInfo.getNCurrencyId();
        String sInterestRateName = ratetypeInfo.getSInterestRateName();
        String sInterestRateCode = ratetypeInfo.getSInterestRateNo();
        Log.print("@@@@@@@@@#################              "+sInterestRateCode+
                "@@@@@@@@@#################              "+dInterestRate+
                "@@@@@@@@@#################              "+lInputUserID+
                "@@@@@@@@@#################              "+tsInputDate
                		);
        long lTemp = -1;

        long lResult = -5;
        long lBankLoanInterestRateID = -1;
        long lMaxID = 0;
        int iIndex = 0;
        int iNumber = 0;
        int i = 0;
        long lInsertOrUpdate = 0;

        //错误信息
        String strError = "";
        
        transConn = this.initBean();
        try
        {
            Sett_InterestRateSettingDAO rateDao = new Sett_InterestRateSettingDAO(
                    "loan_InterestRate", transConn);
            rateDao.setUseMaxID();
            Sett_InterestRateTypeDAO ratetypeDao = new Sett_InterestRateTypeDAO(
                    "loan_interestratetypeinfo", transConn);
            ratetypeDao.setUseMaxID();

            lTemp = ratetypeDao.findInterestRateTypeIdbyCondition(
                    sInterestRateCode, lCurrencyID);

            if (lTemp > 0)
            {
                lBankLoanInterestRateID = lTemp;
            }

            if (rateDao.findInterestRateIDByDate(tsValibleDate, lID,
                    dInterestRate))
            {
                lResult = -2;
                
                strError =  " 该贷款利率相同生效日的纪录已经存在，请重新输入！";

            }
            
            //抛出自定义异常
            if (strError != null && strError.length() > 0)
			{
                throw new SettlementException("Sett_E180",strError, null);
			}
            Log.print("通过验证..........................................................................");
            
            
            
            if (rateDao.findInterestRateIDCondition(lBankLoanInterestRateID,
                    tsValibleDate))
            {
                lInsertOrUpdate = 1; //修改
            }

            if (lInsertOrUpdate == 1) //修改
            {
                Log.print("修改 ＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
                SettInterestRateSettingInfo info = new SettInterestRateSettingInfo();
                info.setId(lID);
                info.setDtValiDate(tsValibleDate);
                info.setMRate(dInterestRate);
                rateDao.update(info);

                rateDao.updateADJUSTCONTRACTDETAIL(dInterestRate,
                        tsValibleDate, lID);
                
                lResult = lID;
                

            }

            else
            //新增
            {
                Log.print("新增 ＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
                rateInfo.setNBankInterestTypeId(lBankLoanInterestRateID);
                lTemp = rateDao.add(rateInfo);
                if (lTemp > 0)
                {
                    lResult = lTemp;
                }
                
              /*  transConn.commit();
                this.finalizeBean(transConn);
                return lResult;*/
            }
            //成功提交
            transConn.commit();

        } catch (ITreasuryDAOException e)

        {
            try
            {
                transConn.rollback();
                Log.print("系统错误，已经回滚到上一次成功提交状态。");
            } catch (SQLException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new SettlementDAOException("系统错误，已经回滚到上一次成功提交状态。",e);
        } catch (SQLException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        this.finalizeBean(transConn);
        return lResult;

    }

    public static void main(String[] args)
    {
    }

    /**
     * @param queryInfo
     * @return
     * @throws SettlementDAOException
     */
    public Collection findInterestRateHistoryByTypeID(SettSettingRateQueryInfo queryInfo) throws SettlementDAOException
    {
        Collection c = null;
        Sett_InterestRateSettingDAO rateDao = new Sett_InterestRateSettingDAO();
        c = rateDao.findInterestRateHistoryByTypeID(queryInfo);
        return c;
    }

    /**
     * @param queryInfo
     * @return
     * @throws SettlementDAOException
     */
    public Collection findInterestRateHistory(SettSettingRateQueryInfo queryInfo) throws SettlementDAOException
    {
        Collection c = null;
        Sett_InterestRateSettingDAO rateDao = new Sett_InterestRateSettingDAO();
        c = rateDao.findInterestRateHistory(queryInfo);
        return c;
    }
}