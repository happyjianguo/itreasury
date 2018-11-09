/*
 * Created on 2003-12-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import com.iss.itreasury.settlement.setting.dao.Sett_ClientTypeDAO;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClientTypeBiz
{
    private Sett_ClientTypeDAO cliDao = null;
    
    public ClientTypeBiz()
    {
        cliDao = new Sett_ClientTypeDAO();
    }
    
    /**
     * @return
     */
    public Sett_ClientTypeDAO getCliTypDao()
    {
        return cliDao;
    }

    /**
     * @param clientDAO
     */
    public void setCliTypDao(Sett_ClientTypeDAO typeDAO)
    {
        cliDao = typeDAO;
    }

}