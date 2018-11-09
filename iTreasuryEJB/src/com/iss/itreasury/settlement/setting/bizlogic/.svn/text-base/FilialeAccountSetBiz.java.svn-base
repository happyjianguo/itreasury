package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;
import java.sql.Connection;
import java.util.Vector;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.setting.dataentity.QueryFilialeAccountInfo;

/**
 * @author rongyang
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class FilialeAccountSetBiz
{
	private Connection conn = null;

	/**
	 * Constructor for FilialeBiz.
	 */
	public FilialeAccountSetBiz()
	{
		try
		{
			conn = Database.getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public FilialeAccountInfo findbyID(long lID) throws IException
	{
		FilialeAccountInfo fi = null;
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(
					conn);
			fi = sett_FilialeAccountSetDAO.findByID(lID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return fi != null ? fi : null;
	}

	public Collection findByCondition(QueryFilialeAccountInfo qfi)
			throws IException
	{
		Vector v = new Vector();
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(
					conn);
			v = sett_FilialeAccountSetDAO.findByCondition(qfi);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return v != null ? v : null;
	}

	public long save(FilialeAccountInfo info) throws IException
	{
		long lID = -1;
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(
					conn);
			if (sett_FilialeAccountSetDAO.verifyByInfo(info) > 0)
			{
				lID = -2;
			}
			else
			{
				if (info.getID() < 0)
				{
					lID = sett_FilialeAccountSetDAO.add(info);
				}
				else
				{
					sett_FilialeAccountSetDAO.update(info);
					lID = info.getID();
				}
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lID;
	}

	public long delete(long lID) throws IException
	{
		long lResult = -1;
		try
		{
			Sett_FilialeAccountSetDAO sett_FilialeAccountSetDAO = new Sett_FilialeAccountSetDAO(
					conn);
			lResult = sett_FilialeAccountSetDAO.delete(lID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/***************************************************************************
	 * 查询根据输入的银行类型，查询所有符合条件的银行账户
	 * 
	 * @param long
	 *            nBankType 银行类型
	 * @return FilialeAccountInfo[] 银行账户的类型
	 * @throws IException
	 */
	public FilialeAccountInfo[] findAll(long nBankType) throws IException
	{
		FilialeAccountInfo[] infos = null;
		try
		{
			Sett_FilialeAccountSetDAO dao = new Sett_FilialeAccountSetDAO(conn);

			infos = dao.findAll(nBankType);
		}
		catch (Exception ie)
		{
			ie.printStackTrace();
			throw new IException("Gen_E001");
		}
		return infos;
	}

	public static void main(String args[]) throws IException
	{
		FilialeAccountSetBiz biz = new FilialeAccountSetBiz();
		//String accountNo = "test";
//		long lTemp = 1;
//		System.out.println("fincAccountId=" + biz.findAccountId(accountNo));
//		System.out.println("findUpBankAccountNo="
//				+ biz.findUpBankAccountID(accountNo));
//		//FilialeAccountInfo[] findBankAccountNo
//		FilialeAccountInfo[] infos = biz.findBankAccountNo(lTemp);
//		for (int i = 0; i < infos.length; i++)
//		{
//			System.out.println("id=" + infos[i].getID());
//		}
		FilialeAccountInfo[] infos = null;
		infos = biz.findAll(2);
		if( infos == null)
		{
			System.out.println("null");
		}
		else {
			System.out.println(infos[0]);
		}
	}

}