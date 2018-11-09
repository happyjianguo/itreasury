/*
 * Created on 2005-5-8
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.craftbrother.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeRelationInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanTypeRelationDao extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);

    public LoanTypeRelationDao()
    {
        super("Cra_CraTypeRelation");
    }
    
  
    /*
     * 取贷款类型关系设置中选择的大类
     */
	public long[] getAllSetLoanTypeID(long officeID,long currencyID)
	{
		long[] allLoanType = null;
		
		int count = 0;
		
		String strSelect = "";
		String strSQL = "";


		try
		{
			try
			{
				initDAO();
			} 
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
            

				strSQL = " from Cra_CraTypeRelation a " + " where 1 = 1 ";

//			  //////////////////////查询条件////////////////////////////////////////////////////
			if (officeID > 0)
			{
				strSQL += " and a.OfficeID = " + officeID;
			}
			if (currencyID > 0)
			{
				strSQL += " and a.CurrencyID = " + currencyID;
			}

            
			strSQL += " order by a.LoanTypeID ";
			
			//log4j.debug(strSQL);
			try
			{
				strSelect = " select distinct(a.LoanTypeID) ";  
			
				strSQL =  strSelect + strSQL;
				  
				transPS = transConn.prepareStatement(" select count(*) from ( "+strSQL + " ) ");
				ResultSet rs = executeQuery(); 
				while (rs != null && rs.next())
				{
					count = rs.getInt(1);
				}
				if(rs != null)
				{ 
					rs.close();
					rs = null;
				}
				/*----2005-08-04----*/
				/*--神龙骑士修改漏洞--*/				
				if (transPS!=null)
				{
					transPS.close();
					transPS=null;
				}
				/*--神龙骑士修改漏洞--*/
				long[] allTmp = new long[count];
				int n = 0;
				
				transPS=transConn.prepareStatement(strSQL);
				rs = executeQuery();
				while (rs != null && rs.next() && n < count)
				{
					allTmp[n] = rs.getLong(1);
					n++;
				}
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
				
				allLoanType = allTmp;
				
				
			} catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
			} catch (SQLException e)
			{
				throw new LoanDAOException("批量查询贷款类型关联设置产生错误", e);
			}
			try
			{
				finalizeDAO();
			} catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try
			{
				finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return allLoanType;
    
	}
	

    public static void main(String[] args)
    {
        //
  /*     LoanTypeRelationDao dao = new LoanTypeRelationDao();
        long strCode = -1;
        try 
        {
            strCode = dao.getLoanTypeBySubLoan(25);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("################strCode = "+strCode);*/
          
    }

}
