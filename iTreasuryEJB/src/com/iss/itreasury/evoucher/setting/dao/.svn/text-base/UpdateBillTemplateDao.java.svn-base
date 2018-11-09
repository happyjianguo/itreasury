package com.iss.itreasury.evoucher.setting.dao;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherDAO;
import com.iss.itreasury.evoucher.base.VoucherDAOException;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillTemplateInfo;

public class UpdateBillTemplateDao extends VoucherDAO {

	public UpdateBillTemplateDao() {
		super("");
	}

	public Collection getTemplateInfos () throws VoucherDAOException
	{
		Vector vector = new Vector();
		String strSQL = "";
		
		try
		{
			try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            PrintBillTemplateInfo info = null;
            
            strSQL = "select a.id tempID,a.sdescription tempName,a.nurl,a.nisneedseal,a.ncoupletno,b.id billID,b.sname billName";
            strSQL += " from print_billtemplate a,print_billsetting b";
            strSQL += " where a.nbillid=b.id";
            strSQL += " order by a.id";
            
            System.out.println("查询单据模版sql："+strSQL);
            
            prepareStatement(strSQL);
            transRS = transPS.executeQuery();
            
            while(transRS.next())
            {
            	info = new PrintBillTemplateInfo();
            	
            	info.setId(transRS.getLong("tempID"));
            	info.setSDescription(transRS.getString("tempName"));
            	info.setNUrl(transRS.getString("nurl"));
            	info.setNIsNeedSeal(transRS.getLong("nisneedseal"));
            	info.setLCoupletNo(transRS.getLong("ncoupletno"));
            	info.setNBillID(transRS.getLong("billID"));
            	info.setStrBillName(transRS.getString("billName"));
            	
            	vector.add(info);
            }
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
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
		return vector;
	}
}
