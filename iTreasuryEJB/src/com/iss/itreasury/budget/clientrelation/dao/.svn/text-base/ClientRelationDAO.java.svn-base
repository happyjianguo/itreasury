/*
 * Created on 2005-8-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.clientrelation.dao;

import java.sql.Connection;

import com.iss.itreasury.budget.clientrelation.dataentity.ClientRelation;
import com.iss.itreasury.budget.dao.BudgetDAO;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.dao.ITreasuryDAOException;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClientRelationDAO extends BudgetDAO{

    /**
     * 
     */
    public ClientRelationDAO() {
        super("clientRelation");
        // TODO Auto-generated constructor stub
    }
    public ClientRelationDAO(Connection conn) {
        super("clientRelation",conn);
        // TODO Auto-generated constructor stub
    }
    
    public long findParentClientID(long clientID) throws BudgetException
    {
        long lReturn = -1;
        try {
            this.initDAO();
            String strSQL = "select parentClientID from " + this.strTableName +" where clientID = ?";
            transPS = prepareStatement(strSQL);
            transPS.setLong(1,clientID);
            transRS = executeQuery();
            if (transRS.next())
                lReturn = transRS.getLong("parentClientID");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new BudgetException();
        }
        finally
        {
            try {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return lReturn;
    }
    
}
