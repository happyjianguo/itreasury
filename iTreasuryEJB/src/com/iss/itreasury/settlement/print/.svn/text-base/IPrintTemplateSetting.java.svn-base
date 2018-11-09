/*
 * Created on 2004-10-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.print.templateinfo.PrintOptionInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class IPrintTemplateSetting
{
	//查询所属办事处的所有模版信息
	public Collection findPrintOption(long lOfficeID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws RemoteException
	{
		Vector returnVector = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			Log.print("程序内1111111111111111111111111111");
			con = Database.getConnection();
			Log.print("程序内2222222222222222222222222222");
			StringBuffer sbRecord = new StringBuffer();
			StringBuffer sbCondition = new StringBuffer();
			StringBuffer sbEnd = new StringBuffer();
			StringBuffer sbCount = new StringBuffer();
			sbCondition.append("  WHERE  a.nStatusID != 0  and a.nOfficeID=?");
			Log.print("1111111111111111111111111111");
			sbEnd.append(" ORDER BY  ");
			switch ((int) lOrderParam)
			{
				case 1 :
					sbEnd.append(" a.ID ");
					break;
				case 2 :
					sbEnd.append(" a.sName ");
					break;
				case 3 :
					sbEnd.append(" a.sDesc ");
					break;
				case 4 :
					sbEnd.append(" a.sPrinterName ");
					break;
				case 5 :
					sbEnd.append(" a.fTop ");
					break;
				case 6 :
					sbEnd.append(" a.fLeft ");
					break;
				case 7 :
					sbEnd.append(" a.nStatusID ");
					break;
				default :
					sbEnd.append("  a.ID ");
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_ASC)
				sbEnd.append(" ASC ");
			else
				sbEnd.append(" DESC ");
			sbRecord.append(" SELECT * FROM ( SELECT a.ID aID,a.fLeft  afLeft,a.fTop  afTop,a.nOfficeID  anOfficeID,a.sName  asName,a.sPrinterName  asPrinterName,a.sDesc  asDesc,a.NStatusID aNStatusID  FROM Sett_PrintTemplate a  ");
			sbRecord.append(sbCondition.toString());
			sbRecord.append(sbEnd.toString());
			sbRecord.append(" ) aa ");
			Log.print("2*********" + sbRecord.toString());
			ps = con.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			rs = ps.executeQuery();
			Log.print("3*********" + sbRecord.toString());
			while (rs != null && rs.next())
			{
				PrintOptionInfo printOptionInfo = new PrintOptionInfo();
				printOptionInfo.m_lTemplateID = rs.getLong("aID");
				printOptionInfo.m_strName = rs.getString("asName");
				printOptionInfo.m_strDesc = rs.getString("asDesc");
				printOptionInfo.m_strPrintName = rs.getString("asPrinterName");
				printOptionInfo.m_dTemplateTop = rs.getDouble("afTop");
				printOptionInfo.m_dTemplateLeft = rs.getDouble("afLeft");
				printOptionInfo.m_lStatusID = rs.getLong("aNStatusID");
				printOptionInfo.m_lAllPage = lPageCount;
				returnVector.addElement(printOptionInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		Log.print("22222*****************sbRecord**********");
		return (returnVector.size() > 0 ? returnVector : null);
	}
	//查询模一显示条目
	public PrintOptionInfo findPrintOptionDetailsByTemplateIDOrderID(long lTemplateID, long lTemplateDetailsID, long lOrderID) throws RemoteException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PrintOptionInfo printOptionInfo = null;
		try
		{
			Log.print("程序内1111111111111111111111111111");
			con = Database.getConnection();
			Log.print("程序内2222222222222222222222222222");
			StringBuffer sbRecord = new StringBuffer();
			if (lOrderID == 0)
				sbRecord.append(" SELECT a.ID aID,a.fLeft  afLeft,a.fTop  afTop,a.nOfficeID  anOfficeID,a.sName  asName,a.sPrinterName  asPrinterName,a.sDesc  asDesc,a.NStatusID aNStatusID  FROM Sett_PrintTemplate a  WHERE  a.ID  = ? ");
			else
				sbRecord.append(
					" select a.ID  aID,a.SCODE  aScode ,a.sDesc  asDesc,a.sdata  asdata,a.sname  asname,a.ftop  aFtop  ,a.fleft  aFleft ,a.sfont  asFont,a.ntypeid  anTypeID , a.nisbold  anIsBold  ,a.nisitalic  anIsItalic ,a.fsize  afSize ,a.NFIELDWIDTH  anFielDwidth,b.sname  bsName,b.sdesc  bsDesc ,b.sPrinterName   bsPrinterName ,b.ftop  bfTop  ,b.fleft  bfLeft      from  Sett_PRINTTEMPLATEDETAILS  a,Sett_PRINTTEMPLATE  b  WHERE   a.NPRINTTEMPLATEID = b.id  and a.id = ?  ");
			ps = con.prepareStatement(sbRecord.toString());
			if (lOrderID == 0)
				ps.setLong(1, lTemplateID);
			else
			{
				ps.setLong(1, lTemplateDetailsID);
			}
			Log.print("3*********" + sbRecord.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				printOptionInfo = new PrintOptionInfo();
				if (lOrderID != 0)
				{
					printOptionInfo.m_lTemplateDetailsID = rs.getLong("aID");
					printOptionInfo.m_dDetailsTop = rs.getDouble("aFtop");
					printOptionInfo.m_dDetailsLeft = rs.getDouble("aFleft");
					printOptionInfo.m_strPrintName = rs.getString("bsPrinterName");
					printOptionInfo.m_strFont = rs.getString("asFont");
					printOptionInfo.m_lTypeID = rs.getLong("anTypeID");
					printOptionInfo.m_lIsBold = rs.getLong("anIsBold");
					printOptionInfo.m_lIsItalic = rs.getLong("anIsItalic");
					printOptionInfo.m_lSize = rs.getDouble("afSize");
					printOptionInfo.m_lFiledWidth = rs.getLong("anFielDwidth");
					printOptionInfo.m_strName = rs.getString("bsName");
					printOptionInfo.m_strDesc = rs.getString("asDesc");
					printOptionInfo.m_strPrintName = rs.getString("bsPrinterName");
					printOptionInfo.m_dTemplateTop = rs.getDouble("bfTop");
					printOptionInfo.m_dTemplateLeft = rs.getDouble("bfLeft");
					printOptionInfo.m_strDetailsName = rs.getString("asName");
					printOptionInfo.m_strDetailsData = rs.getString("asData");
				}
				if (lOrderID == 0)
				{
					printOptionInfo.m_lTemplateID = rs.getLong("aID");
					printOptionInfo.m_strName = rs.getString("asName");
					printOptionInfo.m_strDesc = rs.getString("asDesc");
					printOptionInfo.m_strPrintName = rs.getString("asPrinterName");
					printOptionInfo.m_dTemplateTop = rs.getDouble("afTop");
					printOptionInfo.m_dTemplateLeft = rs.getDouble("afLeft");
					printOptionInfo.m_lStatusID = rs.getLong("aNStatusID");
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		Log.print("22222*****************sbRecord**********");
		return (printOptionInfo == null ? null : printOptionInfo);
	}
	//保存某一显示条目的设置信息
	// lTypeID  当前显示条目的字符间距，
	//0:没有间距；
	//1: 间距为0.1em;
	//-------
	//9: 间距为0.9em;
	//--------
	//15: 间距为 1.5em
	//15为最大值
	public long saveTemplateDetails(long lTemplateDetailsID, String strDesc, double dTop, double dLeft, double dSize, long lIsBold, long lIsItalic, String strFont, long lFileDwidth, String strData, long lTypeID) throws RemoteException
	{
		long result = 0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lStatusID = -1;
		long lTransactionNoID = -1;
		long lCheckStatusID = 0;
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement("UPDATE Sett_PRINTTEMPLATEDETAILS   SET SDESC = ?   , FTOP = ?, FLEFT = ?, FSIZE = ?, NISBOLD = ?, NISITALIC = ?, SFONT = ?, NFIELDWIDTH = ?, SDATA = ? ,NTYPEID = ?   where ID = ?");
			ps.setString(1, strDesc);
			ps.setDouble(2, dTop);
			ps.setDouble(3, dLeft);
			ps.setDouble(4, dSize);
			ps.setLong(5, lIsBold);
			ps.setLong(6, lIsItalic);
			ps.setString(7, strFont);
			ps.setLong(8, lFileDwidth);
			ps.setString(9, strData);
			ps.setLong(10, lTypeID);
			ps.setLong(11, lTemplateDetailsID);
			result = ps.executeUpdate();
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return (result);
	}
	//保存某一套打模版的设置信息
	public long saveTemplate(long lTemplateID, String strDesc, String strPrintName, double dTop, double dLeft) throws RemoteException
	{
		long result = 0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lStatusID = -1;
		long lTransactionNoID = -1;
		long lCheckStatusID = 0;
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement("UPDATE Sett_PRINTTEMPLATE SET SDESC = ? , SPRINTERNAME = ?, FTOP = ?, FLEFT = ? where ID = ?");
			ps.setString(1, strDesc);
			ps.setString(2, strPrintName);
			ps.setDouble(3, dTop);
			ps.setDouble(4, dLeft);
			ps.setLong(5, lTemplateID);
			result = ps.executeUpdate();
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return (result);
	}
	//查询给出模版条件的所有显示条目
	public Collection findPrintOptionDetailsByTemplateID(long lTemplateID) throws RemoteException
	{
		Vector returnVector = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			Log.print("程序内1111111111111111111111111111");
			con = Database.getConnection();
			Log.print("程序内2222222222222222222222222222");
			StringBuffer sbRecord = new StringBuffer();
			sbRecord.append(
				" select a.ID  aID,a.SCODE  aScode ,a.Sname  asName ,a.sdata  asData,a.ftop  aFtop  ,a.fleft  aFleft ,a.sfont  asFont,a.ntypeid  anTypeID , a.nisbold  anIsBold  ,a.nisitalic  anIsItalic ,a.fsize  afSize ,a.NFIELDWIDTH  anFielDwidth,b.sname  bsName,b.sdesc  bsDesc ,b.sPrinterName   bsPrinterName ,b.ftop  bfTop  ,b.fleft  bfLeft      from  Sett_PRINTTEMPLATEDETAILS  a,Sett_PRINTTEMPLATE  b  WHERE  a.scode like '__'  and  a.NPRINTTEMPLATEID = b.id  and b.id = ?");
			Log.print(
				" select a.ID  aID,a.SCODE  aScode ,a.Sname  asName ,a.sdata  asData,a.ftop  aFtop  ,a.fleft  aFleft ,a.sfont  asFont,a.ntypeid  anTypeID , a.nisbold  anIsBold  ,a.nisitalic  anIsItalic ,a.fsize  afSize ,a.NFIELDWIDTH  anFielDwidth,b.sname  bsName,b.sdesc  bsDesc ,b.sPrinterName   bsPrinterName ,b.ftop  bfTop  ,b.fleft  bfLeft      from  Sett_PRINTTEMPLATEDETAILS  a,Sett_PRINTTEMPLATE  b  WHERE  a.scode like '__'  and  a.NPRINTTEMPLATEID = b.id  and b.id = ?");
			ps = con.prepareStatement(sbRecord.toString());
			ps.setLong(1, lTemplateID);
			Log.print("3*********" + sbRecord.toString());
			rs = ps.executeQuery();
			Log.print("3*********" + sbRecord.toString());
			while (rs != null && rs.next())
			{
				PrintOptionInfo printOptionInfo = new PrintOptionInfo();
				System.out.println("asdfasdfasdfasdfasdfasd");
				printOptionInfo.m_lTemplateDetailsID = rs.getLong("aID");
				printOptionInfo.m_dDetailsTop = rs.getDouble("aFtop");
				System.out.println("asdfasdfasdfasdfasd111fasd");
				printOptionInfo.m_dDetailsLeft = rs.getDouble("aFleft");
				printOptionInfo.m_strDetailsName = rs.getString("asName");
				printOptionInfo.m_strDetailsData = rs.getString("asData");
				printOptionInfo.m_strPrintName = rs.getString("bsPrinterName");
				printOptionInfo.m_strCode = rs.getString("aScode");
				printOptionInfo.m_strFont = rs.getString("asFont");
				printOptionInfo.m_lTypeID = rs.getLong("anTypeID");
				printOptionInfo.m_lIsBold = rs.getLong("anIsBold");
				printOptionInfo.m_lIsItalic = rs.getLong("anIsItalic");
				printOptionInfo.m_lSize = rs.getDouble("afSize");
				printOptionInfo.m_lFiledWidth = rs.getLong("anFielDwidth");
				printOptionInfo.m_strName = rs.getString("bsName");
				printOptionInfo.m_strDesc = rs.getString("bsDesc");
				printOptionInfo.m_lIsBold = rs.getLong("anIsBold");
				printOptionInfo.m_dTemplateTop = rs.getDouble("bfTop");
				printOptionInfo.m_dTemplateLeft = rs.getDouble("bfLeft");
				returnVector.addElement(printOptionInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();throw new RemoteException("remote exception : " + e.toString());
			}
		}
		Log.print("22222*****************sbRecord**********");
		return (returnVector.size() > 0 ? returnVector : null);
	}

}
