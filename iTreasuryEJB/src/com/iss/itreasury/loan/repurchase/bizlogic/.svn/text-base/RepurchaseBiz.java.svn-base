package com.iss.itreasury.loan.repurchase.bizlogic;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.loan.repurchase.dao.RepurchaseDAO;
import com.iss.itreasury.loan.repurchase.dataentity.RepurchaseFormInfo;
import com.iss.itreasury.loan.repurchase.dataentity.RepurchaseItemInfo;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 * @author shantao
 * 
 * @version 1.0
 * 2005-12-27
 */
public class RepurchaseBiz
{
    RepurchaseDAO rd = new RepurchaseDAO();

    /**
     * 
     * @param info
     * @param contractID
     * @throws Exception
     */
    public void add(RepurchaseFormInfo info,String[] contractID)throws Exception
    {
        info.setStatusID(LOANConstant.RepurchaseStatus.SUBMIT);
        rd.setUseMaxID();
        long id = rd.add(info);
        rd.addRelContract(id,contractID);
    }
    /**
     * 
     * @param id 
     * @throws Exception
     */
    public void delete(String[] id)throws Exception
    {
        rd.delete(id);
    }
    /**
     * 
     * @param info
     * @throws Exception
     */
    public void update(RepurchaseFormInfo info)throws Exception
    {
        rd.update(info);
    }
    
    public void repurchase(String[] id)throws Exception
    {
        rd.repurchase(id);
    }
    /**
     * 
     * @return
     * @throws Exception
     */
    public RepurchaseFormInfo[] findRepByCon(Map condition)throws Exception
    {
        return rd.findRepByCon(condition);
    }
    /**
     * search repurchase contract all details.
     * @param id
     * @return
     * @throws Exception
     */
    public List findRepurchaseByID(long id)throws Exception
    {
        return rd.findRelByID(id);
    }
    /**
     * 
     * @param condition
     * @return
     * @throws Exception
     */
    public RepurchaseItemInfo[] searchContractByCondition(Map condition) throws Exception
    {
        return rd.searchContractByCondition(condition);
    }
    public boolean isExist(String id)throws Exception
    {
        boolean flag = false;
        flag = rd.isExist(id);
        return flag;
    }
}
