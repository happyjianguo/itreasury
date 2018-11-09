/*
 * Created on 2004-11-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transinvestment.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.transinvestment.dao.Sett_TransInvestmentDAO;
import com.iss.itreasury.settlement.transinvestment.dataentity.QueryTransInvestmentInfo;
import com.iss.itreasury.settlement.transinvestment.dataentity.TransInvestmentInfo;

/**
 * @author ygzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransInvestmentBiz
{
    Sett_TransInvestmentDAO dao = new Sett_TransInvestmentDAO();
    
    public long saveTransInvestment(TransInvestmentInfo info) throws Exception
    {
        try
        {
            return dao.add(info);
        }
        catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Gen_E001");
		}
    }
    
    public TransInvestmentInfo findTransInvestmentByID(long lID) throws Exception
	{
        try
        {
            return (TransInvestmentInfo)dao.findByID(lID);
        }
        catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Gen_E001");
		}
	}
    public void updateTransInvestment(TransInvestmentInfo info)throws Exception
    {
        try
        {
            dao.update(info); 
        }
        catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Gen_E001");
		}
    }
    public long deleteTransInvestment(long lID)throws Exception
    {
        try
        {
            return dao.delete(lID); 
        }
        catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Gen_E001");
		}
    }
    public Collection findTransInvestmentByCondition(QueryTransInvestmentInfo condition)throws Exception
    {
        try
        {
            return dao.findByCondition(condition); 
        }
        catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Gen_E001");
		}        
    }
    
}
