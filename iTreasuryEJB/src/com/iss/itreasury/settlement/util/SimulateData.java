/*
 * Created on 2003-11-28
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SimulateData
{
	public static void main(String[] args)
	{
		try
		{
			//System.out.println(""+DataFormat.formatDouble(Math.random()*100000000));
			for (int i = 1; i <= 2000; i++)
			{
				//GlData();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：模拟压力测试数据
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return @throws
	 *         SQLException
	 */
	public long GlData() throws SQLException
	{
		sett_GLEntryDAO dao = new sett_GLEntryDAO();
		UtilOperation operation = new UtilOperation();
		GLEntryInfo info = new GLEntryInfo();
		long lOfficeID = 1;
		long lCurrencyID = 1;
	    long TransactionTypeID = 1;
		try
		{
			////对info进行付值
			info.setID(-1);
			info.setOfficeID(lOfficeID);
			info.setCurrencyID(lCurrencyID);
			info.setTransNo(operation.getNewTransactionNo(lOfficeID, lCurrencyID,TransactionTypeID));
			info.setTransactionTypeID(getRandomTypeID());
			info.setAmount(DataFormat.formatDouble(Math.random() * 100000000));
			info.setExecute(EndDayProcess.getSystemDate(lOfficeID, lCurrencyID));
			info.setInterestStart(EndDayProcess.getSystemDate(lOfficeID, lCurrencyID));
			info.setAbstract("");
			info.setInputUserID(48);
			info.setCheckUserID(49);
			info.setStatusID(3);
			/////增加数据
			info.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
			info.setSubjectCode(getRandomSubject());
			dao.add(info);
			info.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
			info.setSubjectCode(getRandomSubject());
			dao.add(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lOfficeID;
	}
	/**
	 * 方法说明：得到交易科目号
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return @throws
	 *         SQLException
	 */
	public String getRandomSubject() throws SQLException
	{
		String strSubject = "001.000.2120010000.000.000000.0000.0000";
		try
		{
			int length = strGlSubject.length;
			int iIndex = (int) DataFormat.formatDouble(Math.random() * length);
			if (iIndex >= length)
			{
				iIndex = length - 1;
			}
			else if (iIndex < 0)
			{
				iIndex = 0;
			}
			strSubject = strGlSubject[iIndex];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strSubject;
	}
	/**
		 * 方法说明：得到交易类型
		 * 
		 * @param lOfficeID
		 * @param lCurrencyID
		 * @return @throws
		 *         SQLException
		 */
	public long getRandomTypeID() throws SQLException
	{
		long lTypeID = 1;
		try
		{
			long lID[] = SETTConstant.TransactionType.getAllCode(1,1);
			int length = lID.length;
			int iIndex = (int) DataFormat.formatDouble(Math.random() * length);
			if (iIndex >= length)
			{
				iIndex = length - 1;
			}
			else if (iIndex < 0)
			{
				iIndex = 0;
			}
			lTypeID = lID[iIndex];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lTypeID;
	}
	//初始化关机信息
	private static boolean m_bIsInit = false;
	private static String[] strGlSubject = null;
	static 
	{
		if (!m_bIsInit)
		{
			try
			{
				Connection conn = Database.getConnection();
				PreparedStatement ps = conn.prepareStatement("select count(*) nCount from SETT_VGLSUBJECTDEFINITION where nStatus=" + Constant.RecordStatus.VALID + " and nOfficeID=1 and nCurrencyID=1 ");
				ResultSet rs = ps.executeQuery();
				int iCount = 0; //办事处的数量
				if (rs.next())
				{
					iCount = rs.getInt("nCount");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strGlSubject = new String[iCount];
				ps = conn.prepareStatement("select * from SETT_VGLSUBJECTDEFINITION where nStatus=" + Constant.RecordStatus.VALID + " and nOfficeID=1 and nCurrencyID=1 ");
				rs = ps.executeQuery();
				int nSize = 0;
				while (rs.next())
				{
					strGlSubject[nSize] = rs.getString("sSubjectCode");
					nSize++;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
			m_bIsInit = true;
		}
	}
}
