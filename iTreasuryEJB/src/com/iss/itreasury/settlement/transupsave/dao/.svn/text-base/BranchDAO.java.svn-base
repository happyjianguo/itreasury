/*
 * Created on 2006-2-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transupsave.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;

import java.util.Collection;
import java.util.Vector;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BranchDAO extends SettlementDAO{
	//取得结算中心银行账号
	public Collection getSettBankNo(long officeid,long currencyid) throws SettlementDAOException
	{
		Vector v = null;
		v = new Vector();
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT sbankaccountcode FROM sett_branch \n");
			buffer.append(" WHERE nofficeid = "+officeid);
			buffer.append(" AND ncurrencyid = "+currencyid);
			
			log.debug(buffer.toString());			
			transPS = prepareStatement(buffer.toString());		
			transRS = executeQuery();
			while (transRS.next())
			{
				
				
					String acctcode = "";
					acctcode = transRS.getString("sbankaccountcode");
					if(acctcode!=null){
						v.add(acctcode);
						System.out.println("acctcode:"+acctcode);
					}
					
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return v;
	}
	
	public static void main(String [] args){
		BranchDAO b=new BranchDAO();
		try {
			b.getSettBankNo(1,1);
		} catch (SettlementDAOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}

