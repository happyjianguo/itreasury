package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_CommissionSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

/**
 * @name: CommissionSettingBiz
 * @description:
 * @author: gqfang
 * @create: 2005-8-18
 */
public class CommissionSettingBiz
{
    Sett_CommissionSettingDAO dao = null;

    /**
     * Constructor for CommissionSettingBiz
     */
    public CommissionSettingBiz()
    {
        dao = new Sett_CommissionSettingDAO();
    }

    /**
     * @description: find out all the valid commissionSettings
     * @return Collection
     * @throws IException
     */
    public Collection findAll(CommissionSettingInfo info) throws SettlementException
    {
        Collection coll = null;
   
       try 
        {
            coll = dao.findByCondition(info, " order by commissionType, isurgent, amountFrom ");
        }
        catch ( ITreasuryDAOException e ) 
        {
            e.printStackTrace();
            throw new SettlementException();
        }
        finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }

        return coll;
    }

    /**
     * @description: find a commissionSetting details
     * @param id
     * @return CommissionSettingInfo
     * @throws IException
     */
    public CommissionSettingInfo findById(long id) throws SettlementException
    {
        CommissionSettingInfo resultInfo = null;
        try 
        {
            resultInfo = new CommissionSettingInfo();

            resultInfo = (CommissionSettingInfo) dao.findByID(id, (new CommissionSettingInfo()).getClass());

        }
        catch ( ITreasuryDAOException e ) 
        {
            e.printStackTrace();
            throw new SettlementException();
        }
        finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }

        }
        return resultInfo;
    }

    /**
     * @description: long
     * @param CommissionSettingInfo
     * @return
     * @throws Exception
     */
    public long save(CommissionSettingInfo info) throws SettlementException, Exception
    {
        long lReturn = -1;

        try 
        {
            if ( dao.isAllow(info) == true ) 
            {
                System.out.println("======================= Allow!!!! =======================");
                if ( info.getId() < 0 ) 
                {
                    // new
                    System.out.println("======================= new =======================");
                    lReturn = dao.add(info);
                }
                else 
                {
                    // update
                    System.out.println("=======================update =======================");
                    dao.update(info);
                    lReturn = info.getId();
                }
            }
            else 
            {
                // throws Exception :cannot update or add
                lReturn = 0;
            }

        }
        catch ( ITreasuryDAOException e ) 
        {
            e.printStackTrace();
            throw new SettlementException();
        }
        finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }

        return lReturn;
    }

    /**
     * @description: delete a commisssionSettting record
     * @param id
     * @return long
     * @throws IException
     */
    public void delete(long id) throws SettlementException
    {
        CommissionSettingInfo info = new CommissionSettingInfo();
        info.setId(id);
        info.setStatusId(Constant.RecordStatus.INVALID);
        try 
        {
            dao.update(info);
        }
        catch ( Exception e ) 
        {
            e.printStackTrace();
            throw new SettlementException();
        }
        finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }

    }
    
    /**
     * 查询所有的凭证类型
     * @return
     * @throws Exception
     */
    public Collection getAllVouher_Type()throws Exception{
    	
    	Sett_CommissionSettingDAO dao = new Sett_CommissionSettingDAO();
    	Collection c = null; 
    	
    	try{
    		c = dao.getAllVouher_Type();
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e ;
    	}
    	
    	return c ;
    	
    }
    
}
