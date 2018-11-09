package com.iss.itreasury.evoucher.endorse.dao;

import java.sql.Connection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherDAO;
import com.iss.itreasury.evoucher.base.VoucherDAOException;
import com.iss.itreasury.evoucher.util.VOUConstant;

public class EndorseDao extends VoucherDAO
{

	public EndorseDao()
    {
        super("print_printapply");
    }
    
    public EndorseDao(Connection con)
    {
        super("print_printapply",con);
    }
    
    /**
     * 单据签章（针对于网银的再打印申请）
     * @param lTransID
     * @return
     * @throws VoucherDAOException
     */
	public long updateStatus (long lTransID) throws VoucherDAOException
	{
		long lResult = -1;
		String strSQL = "";
		
		try 
		{
			try
            {
                initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	e.printStackTrace();
                throw new VoucherDAOException(e);
            }
            
            strSQL = " update print_printapply set nstatusid=" + VOUConstant.VoucherStatus.SIGN;
            strSQL += " where nprintcontentid=" + lTransID;
            strSQL += " and nstatusid=" + VOUConstant.VoucherStatus.APPROVED;
            strSQL += " and ndeptid=" + VOUConstant.IsEbankApply.YES;
            
            transPS = transConn.prepareStatement(strSQL);
            lResult = transPS.executeUpdate();
            
            finalizeDAO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
		return lResult;
	}
}
