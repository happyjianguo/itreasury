/* Generated by Together */

package com.iss.itreasury.budget.constitute.dao;

import java.sql.Connection;

import com.iss.itreasury.budget.dao.BudgetDAO;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
public class Budget_planDetailDAO  extends BudgetDAO {
	public Budget_planDetailDAO(){
		super("Budget_planDetail");
		super.setUseMaxID(); 
	}
	public Budget_planDetailDAO(String tableName){
		super(tableName);
		super.setUseMaxID(); 
	}
	
	public Budget_planDetailDAO(Connection conn){
		super(conn);
		super.setUseMaxID(); 
	}
	
	public Budget_planDetailDAO(String tableName,Connection conn){
		super(tableName,conn);
		super.setUseMaxID(); 
	}
	
	public long deleteByPlanID(long planID) throws BudgetException
	{
	    long lReturn = -1;
	    try {
            this.initDAO();       
		    String strSQL = "update budget_planDetail set statusID = ? where planID=?";
		    transPS = transConn.prepareStatement(strSQL);
		    transPS.setLong(1,Constant.RecordStatus.INVALID);
		    transPS.setLong(2,planID);
		    lReturn = transPS.executeUpdate();
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