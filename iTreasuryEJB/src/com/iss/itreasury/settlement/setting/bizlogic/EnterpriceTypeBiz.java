/*
 * Created on 2003-12-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import com.iss.itreasury.settlement.setting.dao.Sett_EnterpriceTypeDAO;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EnterpriceTypeBiz
{
    private Sett_EnterpriceTypeDAO entDao=null;
    
    public EnterpriceTypeBiz()
    {
        entDao = new Sett_EnterpriceTypeDAO();
    }
    
    /**
     * @return
     */
    public Sett_EnterpriceTypeDAO getEntPriDao()
    {
        return entDao;
    }

    /**
     * @param clientDAO
     */
    public void setEntPriDao(Sett_EnterpriceTypeDAO priceDAO)
    {
        entDao = priceDAO;
    }

}