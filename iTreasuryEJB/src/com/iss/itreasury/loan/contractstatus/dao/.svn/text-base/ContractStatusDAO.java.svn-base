package com.iss.itreasury.loan.contractstatus.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class ContractStatusDAO extends ITreasuryDAO {

	/**
	 * added by mzh_fu 2007/07/10 更改状态方法
	 * 
	 * @param lID
	 * @param lStatus
	 * @return
	 * @throws IException
	 */
	public long updateStatus(long lID, long lStatus) throws IException {

		String strSQL = "update loan_contractstatus set nStatusID = ? where id =? ";
		long lReturn = -1;

		try {
			initDAO();
			prepareStatement(strSQL);
			transPS.setLong(1, lStatus);
			transPS.setLong(2, lID);

			lReturn = executeUpdate();

		} catch (Exception e) {
			throw new IException("更新状态失败", e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lID
	 * @param lStatus
	 * @return
	 * @throws IException
	 */
	public long updateStatusAndCheckStatus(long lID, long lStatus) throws IException {

		String strSQL = "update loan_contractstatus set nStatusID = ? where id =? and nStatusID = ?";
		long lReturn = -1;

		try {
			initDAO();
			prepareStatement(strSQL);
			transPS.setLong(1, lStatus);
			transPS.setLong(2, lID);
			transPS.setLong(3, LOANConstant.RiskModifyStatus.CHECK);

			lReturn = executeUpdate();

		} catch (Exception e) {
			throw new IException("更新状态失败", e);
		} finally {
			finalizeDAO();
		}
		return lReturn;
	}
	

    
    /**
     * 更新合同状态，审批完成和取消审批
     * @param lContractStatusID
     * @param action
     * @return
     * @throws Exception
     */
    public long updateContractStatus( long lContractStatusID,String action ) throws
          Exception
    {
        PreparedStatement ps = null ;
        PreparedStatement ps1 = null ;
        ResultSet rs = null ;
        ResultSet rs1 = null ;
        Connection conn = null ;
        long lContractID = -1 ;
        long lStatusID = -1 ;
        long lReturn = -1 ;

        //long lOBStatusID = 0;
        String strSQL = "" ;
        try
        {

            conn = Database.getConnection() ;

            strSQL =
                  "select * from loan_contractstatus where ID = " +
                  lContractStatusID ;
            System.out.println( strSQL ) ;
            ps = conn.prepareStatement( strSQL ) ;
            rs = ps.executeQuery() ;
            if ( rs != null && rs.next() )
            {
                lContractID = rs.getLong( "NCONTRACTID" ) ;
                if(action.equals("doApproval"))
                {
                	lStatusID = rs.getLong( "NCHANGESTATUS" ) ;
                }
                else if(action.equals("cancelApproval"))
                {
                	 lStatusID = rs.getLong( "NOLDSTATUS" ) ;
                }
            }
            rs.close() ;
            rs = null ;
            ps.close() ;
            ps = null ;

            if ( lContractID < 0 )
            {
                lReturn = -1 ;
                throw new RemoteException( "合同ID不正确！" ) ;
            }
            else
            {
                strSQL = "update LOAN_CONTRACTFORM set NSTATUSID = "
                      + lStatusID + " where ID = " + lContractID ;
                System.out.println( strSQL ) ;
                ps = conn.prepareStatement( strSQL ) ;
                if ( ( lReturn = ps.executeUpdate() ) < 1 )
                {
                    System.out.println( "error.update.LOAN_CONTRACTFORM.status" ) ;
                }
                ps.close() ;
                ps = null ;

            }

            if ( conn != null )
            {
                conn.close() ;
            }
            conn = null ;

        }
        catch ( Exception ex )
        {
            ex.printStackTrace() ;
            throw ex ;
        }
        finally
        {
            try
            {
                if ( rs1 != null )
                {
                    rs1.close() ;
                    rs1 = null ;
                }
                if ( ps1 != null )
                {
                    ps1.close() ;
                    ps1 = null ;
                }
                if ( rs != null )
                {
                    rs.close() ;
                    rs = null ;
                }
                if ( ps != null )
                {
                    ps.close() ;
                    ps = null ;
                }
                if ( conn != null )
                {
                    conn.close() ;
                    conn = null ;
                }
            }
            catch ( Exception ex )
            {
                throw ex ;
            }
        }
		return lReturn;
    }
}
