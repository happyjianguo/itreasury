/*
 * Created on 2004-11-24
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalanceregister.dao;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo;
import com.iss.itreasury.settlement.offbalanceregister.exception.OffBalanceRegisterException;
import com.iss.itreasury.settlement.offbalanceregister.exception.OffBalanceRegisterDAOException;

/**
 * Title: iTreasury Description: OffBalanceDAO后台操作的方法 Copyright (c) 2004
 * Company: iSoftStone
 * 
 * @author kewen hu
 * @version Date of Creation 2004-11-23
 */
public class OffBalanceRegisterDAO extends ITreasuryDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    public OffBalanceRegisterDAO()
    {
        super("sett_offBalanceRegister");
    }

    public OffBalanceRegisterDAO(Connection conn)
    {
        super("sett_offBalanceRegister", conn);
    }

    /**
     * 通过ID查找结果集
     * 
     * @param long
     *            id
     * @return OffBalanceRegisterInfo
     * @exception throws
     *                OffBalanceRegisterException
     */
    public OffBalanceRegisterInfo findByID(long id) throws OffBalanceRegisterException
    {
        OffBalanceRegisterInfo offBalanceRegisterInfo = null;
        try
        {
            offBalanceRegisterInfo = (OffBalanceRegisterInfo) super.findByID(id, offBalanceRegisterInfo.getClass());
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceRegisterDAOException(ie);
        }
        return offBalanceRegisterInfo;
    }

    /**
     * 通过条件查找结果集
     * 
     * @param OffBalanceRegisterInfo
     *            offBalanceRegisterInfo
     * @return Collection
     * @exception throws
     *                OffBalanceRegisterException
     */
    public Collection findByCondition(OffBalanceRegisterInfo offBalanceRegisterInfo) throws OffBalanceRegisterException
    {
        Collection collection = null;
        try
        {
            collection = super.findByCondition(offBalanceRegisterInfo);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceRegisterDAOException(ie);
        }
        return collection;
    }

    /**
     * 保存
     * 
     * @param OffBalanceRegisterInfo
     *            offBalanceRegisterInfo
     * @return void
     * @exception throws
     *                OffBalanceRegisterException
     */
    public void add(OffBalanceRegisterInfo offBalanceRegisterInfo) throws OffBalanceRegisterException
    {
        try
        {
            super.add(offBalanceRegisterInfo);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceRegisterDAOException(ie);
        }
    }

    /**
     * 修改
     * 
     * @param OffBalanceRegisterInfo
     *            offBalanceRegisterInfo
     * @return void
     * @exception throws
     *                OffBalanceRegisterException
     */
    public void update(OffBalanceRegisterInfo offBalanceRegisterInfo) throws OffBalanceRegisterException
    {
        try
        {
            super.update(offBalanceRegisterInfo);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceRegisterDAOException(ie);
        }
    }

    /**
     * 修改状态
     * 
     * @param long
     *            statusID, String sTransNo
     * @return void
     * @exception throws
     *                OffBalanceRegisterException
     */
    public void updateByTransNo(long statusID, String sTransNo) throws OffBalanceRegisterException
    {
        StringBuffer strSQL = new StringBuffer();
        strSQL.append(" UPDATE sett_OffBalanceRegister SET statusID = " + statusID + " WHERE transNo = '" + sTransNo + "' \n");

        log4j.debug(strSQL.toString());
        try
        {
            this.initDAO();
            this.prepareStatement(strSQL.toString());
            this.executeUpdate();
            this.finalizeDAO();
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceRegisterDAOException(ie);
        }
        finally
        {
            try
            {
                this.finalizeDAO();
            }
            catch (ITreasuryDAOException es)
            {
            }
        }
    }

    /**
     * 修改业务方向
     * 
     * @param String
     *            sTransNo, long direction
     * @return void
     * @exception throws
     *                OffBalanceRegisterException
     */
    public void updateByTransNo(String sTransNo, long direction, long transactionType) throws OffBalanceRegisterException
    {
        StringBuffer strSQL = new StringBuffer();
        strSQL.append(" UPDATE sett_OffBalanceRegister SET direction = " + direction + ", transactionType = " + transactionType + " WHERE transNo = '" + sTransNo + "' \n");

        log4j.debug(strSQL.toString());
        try
        {
            this.initDAO();
            this.prepareStatement(strSQL.toString());
            this.executeUpdate();
            this.finalizeDAO();
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceRegisterDAOException(ie);
        }
        finally
        {
            try
            {
                this.finalizeDAO();
            }
            catch (ITreasuryDAOException es)
            {
            }
        }
    }

    /**
     * 删除
     * 
     * @param long
     *            id
     * @return void
     * @exception throws
     *                OffBalanceRegisterException
     */
    public void delete(long id) throws OffBalanceRegisterException
    {
        try
        {
            OffBalanceRegisterInfo offBalanceRegisterInfo = new OffBalanceRegisterInfo();
            offBalanceRegisterInfo.setId(id);
            offBalanceRegisterInfo.setStatusID(SETTConstant.TransactionStatus.DELETED);
            super.update(offBalanceRegisterInfo);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceRegisterDAOException(ie);
        }
    }

    //test
    public static void main(String[] args)
    {
        OffBalanceRegisterDAO offBalanceDAO = new OffBalanceRegisterDAO();
        try
        {
            OffBalanceRegisterInfo info = new OffBalanceRegisterInfo();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}