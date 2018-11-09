/* Generated by Together */

package com.iss.itreasury.budget.setting.dao;

import java.sql.Connection;

import com.iss.itreasury.budget.dao.BudgetDAO;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.executecontrol.dataentity.ControlInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;

/* Generated by Together */

public class Budget_ItemPrivilegeDAO extends BudgetDAO {

    public Budget_ItemPrivilegeDAO() {
        super("Budget_ItemPrivilege");
        super.setUseMaxID(); 
        // TODO Auto-generated constructor stub
    }
    public Budget_ItemPrivilegeDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
        super.setUseMaxID(); 
    }
    public Budget_ItemPrivilegeDAO(Connection conn) {
        super(conn);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param tableName
     */
    public Budget_ItemPrivilegeDAO(String tableName) {
        super(tableName);
        // TODO Auto-generated constructor stub
    }
    /**
     * ������������ĳ��ϵ��ĳ��λ����ĿȨ��
     * @param info
     * @return
     * @throws BudgetException
     */
    public long findItemPivilege(ControlInfo info) throws BudgetException
    {
    	long lRtn =-1;
		   		   
		    try {
	            this.initDAO();
	            String strSQL = "";
	            strSQL += "select id from \n";	            
	            strSQL += "BUDGET_ITEMPRIVILEGE \n";
	           
	            //�����SQL�ǲ�ѯ����λ�Լ��¼���λ
	           
	            //����
	            //����SQL�ǰ����δ���������
	            strSQL += " where BUDGETCLIENTID = " + info.getClientID() + " \n";
	            strSQL += " and BUDGETITEMID = " + info.getItemID() + " \n";
	            strSQL += " and (BUDGETACCOUNTID = " + info.getAccountID() + " \n";
	            strSQL += "  or BUDGETACCOUNTID = -100) \n";
	            strSQL += " and STATUSID = " + Constant.RecordStatus.VALID + " \n";
	            
	                
	            log.print(strSQL);
	            
	            transPS = transConn.prepareStatement(strSQL);
	            transRS = transPS.executeQuery();
	            while (transRS.next())
	            {
	                
	            	lRtn = transRS.getLong("ID");
	                
	            }
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
		    
		    return lRtn;
		}
}

