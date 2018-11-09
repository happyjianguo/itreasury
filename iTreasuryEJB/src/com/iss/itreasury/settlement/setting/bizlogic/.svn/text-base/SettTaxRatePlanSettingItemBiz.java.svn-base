package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_TaxRatePlanSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_TaxRatePlanSettingItemDAO;
import com.iss.itreasury.settlement.setting.dataentity.TaxRatePlanSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.TaxRatePlanSettingItemInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
public class SettTaxRatePlanSettingItemBiz
{
	Sett_TaxRatePlanSettingItemDAO dao2 = null;
	public SettTaxRatePlanSettingItemBiz(){
		dao2 = new Sett_TaxRatePlanSettingItemDAO();
	}
 public Collection findAll() throws SettlementException
    {
        Collection coll = null;

        TaxRatePlanSettingItemInfo info = new TaxRatePlanSettingItemInfo();
		info.setStatusID(Constant.RecordStatus.VALID);
        try 
        {
            coll = dao2.findByCondition(info, " order by ID ");
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
                dao2.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }

        return coll;
    }

	//根据计划查找其相应明细
	 public Collection findAll(TaxRatePlanSettingInfo planInfo) throws SettlementException
    {
        Collection coll = null;
        TaxRatePlanSettingItemInfo info = new TaxRatePlanSettingItemInfo();
		
		info.setTaxRatePlanID(planInfo.getId());
		info.setStatusID(Constant.RecordStatus.VALID);
	   System.out.println("iteminfo..........info.getTaxRatePlanID="+info.getTaxRatePlanID()+"...info.getStatusID..."+info.getStatusID());
		try 
        {
            coll = dao2.findByCondition(info, " order by ID ");
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
                dao2.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		

        return coll;
    }


    public TaxRatePlanSettingItemInfo findById(long id) throws SettlementException,Exception{
    	TaxRatePlanSettingItemInfo resultInfo = null;
        try 
            {
                resultInfo = new TaxRatePlanSettingItemInfo();

                resultInfo = (TaxRatePlanSettingItemInfo) dao2.findByID(id, (new TaxRatePlanSettingItemInfo()).getClass());

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
                    dao2.clearDAO();
                }
                catch ( ITreasuryDAOException e1 ) 
                {
                    e1.printStackTrace();
                    throw new SettlementException();
                }

            }
            return resultInfo;
        }

    public long save(TaxRatePlanSettingItemInfo info) throws SettlementException, Exception
    {
        long lReturn = -1;
		 if ( info.getId() < 0 )
                {
                    lReturn = dao2.add(info);
                }
                else 
                {
                    dao2.update(info);
                    lReturn = info.getId();
                }
				return lReturn;
    }
	public void delete(long id) throws SettlementException
    {
        TaxRatePlanSettingItemInfo info = new TaxRatePlanSettingItemInfo();
        info.setId(id);
        info.setStatusID(Constant.RecordStatus.INVALID);
        try 
        {
            dao2.update(info);
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
                dao2.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }

    }

	public void deleteByPlan(TaxRatePlanSettingInfo info) throws Exception
    {
		dao2.deleteByPlan(info);
	}

	public long getMaxId()throws Exception{
	return dao2.getMaxId();
	}
}
