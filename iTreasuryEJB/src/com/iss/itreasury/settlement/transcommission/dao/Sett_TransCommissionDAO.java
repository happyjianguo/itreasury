package com.iss.itreasury.settlement.transcommission.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;

/**
 *
 * @name: Sett_TransCommissionDAO
 * @description:
 * @author: gqfang
 * @create: 2005-8-25
 *
 */
public class Sett_TransCommissionDAO extends SettlementDAO
{
    /**
     * Constructor for TransCommissionDAO
     */
    public Sett_TransCommissionDAO()
    {
        super("Sett_TransCommission",false);
        super.setUseMaxID();
    }
    
    public void clearDAO() throws ITreasuryDAOException
    {
        super.finalizeDAO();
    }   
}