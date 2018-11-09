/*
 * Created on 2004-11-16
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.clientmanage.setting.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_ExtendAttributeDAO extends SettlementDAO
{
	public Sett_ExtendAttributeDAO()
	{
		super("Sett_ExtendAttribute",true);
		super.setUseMaxID();
	}
	public void clearDAO() throws ITreasuryDAOException
	{
		super.finalizeDAO();
	}
	public static void main(String[] args)
	{
		Sett_ExtendAttributeDAO dao = new Sett_ExtendAttributeDAO();
	}
}
