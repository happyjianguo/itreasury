/*
 * Created on 2005-4-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.print.templateinfo.PrintOptionInfo;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_PrintTemplateDAO extends ITreasuryDAO
{
	/**
	 * 
	 */
	public Sett_PrintTemplateDAO()
	{
		super();
		this.strTableName = "Sett_PrintTemplate";
		// TODO Auto-generated constructor stub
	}
	public Sett_PrintTemplateDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "Sett_PrintTemplate";
		// TODO Auto-generated constructor stub
	}
	public Collection findPrintOption(long lOfficeID,long lCurrecnyID) throws SettlementException
	{

		Vector returnVector = new Vector();

		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{

			this.initDAO();

			StringBuffer sbRecord = new StringBuffer();
			StringBuffer sbCondition = new StringBuffer();
			StringBuffer sbEnd = new StringBuffer();
			StringBuffer sbCount = new StringBuffer();
			sbCondition.append("  WHERE  a.nStatusID != 0  and a.nOfficeID=? and a.NCURRENCYID=? ");
			sbEnd.append(" ORDER BY  a.ID desc");

			sbRecord.append(" SELECT * FROM ( SELECT a.ID aID,a.fLeft  afLeft,a.fTop  afTop,a.nOfficeID  anOfficeID,a.sName  asName,a.sPrinterName  asPrinterName,a.sDesc  asDesc,a.NStatusID aNStatusID , a.nCurrencyID anCurrencyID, a.nPrintTemplateType anPrintTemplateType  FROM Sett_PrintTemplate a  ");
			sbRecord.append(sbCondition.toString());
			sbRecord.append(sbEnd.toString());
			sbRecord.append(" ) aa ");

			transPS = transConn.prepareStatement(sbRecord.toString());
			transPS.setLong(1, lOfficeID);
			transPS.setLong(2, lCurrecnyID);
			transRS = transPS.executeQuery();
			
			while (transRS != null && transRS.next())
			{
				PrintOptionInfo printOptionInfo = new PrintOptionInfo();
				printOptionInfo.m_lTemplateID = transRS.getLong("aID");
				printOptionInfo.m_strName = transRS.getString("asName");
				printOptionInfo.m_strDesc = transRS.getString("asDesc");
				printOptionInfo.m_strPrintName = transRS.getString("asPrinterName");
				printOptionInfo.m_dTemplateTop = transRS.getDouble("afTop");
				printOptionInfo.m_dTemplateLeft = transRS.getDouble("afLeft");
				printOptionInfo.m_lStatusID = transRS.getLong("aNStatusID");
				
				printOptionInfo.m_lCurrencyID = transRS.getLong("anCurrencyID");		// 币种
				printOptionInfo.m_nPrintTemplateType = transRS.getLong("anPrintTemplateType");	//模版的类型 1：存款  2:贷款
				
				printOptionInfo.m_lAllPage = lPageCount;
				returnVector.addElement(printOptionInfo);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				throw new SettlementException();
			}
		}

		return (returnVector.size() > 0 ? returnVector : null);
	
	}
	public PrintOptionInfo findPrintOptionDetailsByTemplateIDOrderID(long lTemplateID, long lTemplateDetailsID, long lOrderID) throws SettlementException
	{
		PrintOptionInfo printOptionInfo = null;
		try
		{
			this.initDAO();

			StringBuffer sbRecord = new StringBuffer();
			if (lOrderID == 0)
				sbRecord.append(" SELECT a.ID aID,a.fLeft  afLeft,a.fTop  afTop,a.nOfficeID  anOfficeID,a.sName  asName,a.sPrinterName  asPrinterName,a.sDesc  asDesc,a.NStatusID aNStatusID ,a.nCurrencyID  anCurrencyID ,a.nPrintTemplateType anPrintTemplateType FROM Sett_PrintTemplate a  WHERE  a.ID  = ? ");
			else
				sbRecord.append(
					" select a.ID  aID,a.SCODE  aScode ,a.sDesc  asDesc,a.sdata  asdata,a.sname  asname,a.ftop  aFtop  ,a.fleft  aFleft ,a.sfont  asFont,a.ntypeid  anTypeID , a.nisbold  anIsBold  ,a.nisitalic  anIsItalic ,a.fsize  afSize ,a.NFIELDWIDTH  anFielDwidth,b.sname  bsName,b.sdesc  bsDesc ,b.sPrinterName   bsPrinterName ,b.ftop  bfTop  ,b.fleft  bfLeft      from  Sett_PRINTTEMPLATEDETAILS  a,Sett_PRINTTEMPLATE  b  WHERE   a.NPRINTTEMPLATEID = b.id  and a.id = ?  ");
			transPS = transConn.prepareStatement(sbRecord.toString());
			if (lOrderID == 0)
				transPS.setLong(1, lTemplateID);
			else
			{
				transPS.setLong(1, lTemplateDetailsID);
			}

			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				printOptionInfo = new PrintOptionInfo();
				if (lOrderID != 0)
				{
					printOptionInfo.m_lTemplateDetailsID = transRS.getLong("aID");
					printOptionInfo.m_dDetailsTop = transRS.getDouble("aFtop");
					printOptionInfo.m_dDetailsLeft = transRS.getDouble("aFleft");
					printOptionInfo.m_strPrintName = transRS.getString("bsPrinterName");
					printOptionInfo.m_strFont = transRS.getString("asFont");
					printOptionInfo.m_lTypeID = transRS.getLong("anTypeID");
					printOptionInfo.m_lIsBold = transRS.getLong("anIsBold");
					printOptionInfo.m_lIsItalic = transRS.getLong("anIsItalic");
					printOptionInfo.m_lSize = transRS.getDouble("afSize");
					printOptionInfo.m_lFiledWidth = transRS.getLong("anFielDwidth");
					printOptionInfo.m_strName = transRS.getString("bsName");
					printOptionInfo.m_strDesc = transRS.getString("asDesc");
					printOptionInfo.m_strPrintName = transRS.getString("bsPrinterName");
					printOptionInfo.m_dTemplateTop = transRS.getDouble("bfTop");
					printOptionInfo.m_dTemplateLeft = transRS.getDouble("bfLeft");
					printOptionInfo.m_strDetailsName = transRS.getString("asName");
					printOptionInfo.m_strDetailsData = transRS.getString("asData");
				}
				if (lOrderID == 0)
				{
					printOptionInfo.m_lTemplateID = transRS.getLong("aID");
					printOptionInfo.m_strName = transRS.getString("asName");
					printOptionInfo.m_strDesc = transRS.getString("asDesc");
					printOptionInfo.m_strPrintName = transRS.getString("asPrinterName");
					printOptionInfo.m_dTemplateTop = transRS.getDouble("afTop");
					printOptionInfo.m_dTemplateLeft = transRS.getDouble("afLeft");
					printOptionInfo.m_lStatusID = transRS.getLong("aNStatusID");
					
					printOptionInfo.m_lCurrencyID = transRS.getLong("anCurrencyID");		// 币种
					printOptionInfo.m_nPrintTemplateType = transRS.getLong("anPrintTemplateType");	//模版的类型 1：存款  2:贷款
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return (printOptionInfo == null ? null : printOptionInfo);
	
	}
	public long saveTemplateDetails(long lTemplateDetailsID, String strDesc, double dTop, double dLeft, double dSize, long lIsBold, long lIsItalic, String strFont, long lFileDwidth, String strData, long lTypeID) throws SettlementException
	{

		long result = 0;
		long lStatusID = -1;
		long lTransactionNoID = -1;
		long lCheckStatusID = 0;
		try
		{
			this.initDAO();
			transPS = transConn.prepareStatement("UPDATE Sett_PRINTTEMPLATEDETAILS   SET SDESC = ?   , FTOP = ?, FLEFT = ?, FSIZE = ?, NISBOLD = ?, NISITALIC = ?, SFONT = ?, NFIELDWIDTH = ?, SDATA = ? ,NTYPEID = ?   where ID = ?");
			transPS.setString(1, strDesc);
			transPS.setDouble(2, dTop);
			transPS.setDouble(3, dLeft);
			transPS.setDouble(4, dSize);
			transPS.setLong(5, lIsBold);
			transPS.setLong(6, lIsItalic);
			transPS.setString(7, strFont);
			transPS.setLong(8, lFileDwidth);
			transPS.setString(9, strData);
			transPS.setLong(10, lTypeID);
			transPS.setLong(11, lTemplateDetailsID);
			result = transPS.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return (result);
	
	}
//	保存某一套打模版的设置信息
	public long saveTemplate(long lTemplateID, String strDesc, String strPrintName, double dTop, double dLeft , long nPrintTemplateType,long lCurrencyID) throws SettlementException
	{
		long result = 0;
		long lStatusID = -1;
		long lTransactionNoID = -1;
		long lCheckStatusID = 0;
		try
		{
			this.initDAO();
			transPS = transConn.prepareStatement("UPDATE Sett_PRINTTEMPLATE SET SDESC = ? , SPRINTERNAME = ?, FTOP = ?, FLEFT = ? , nPrintTemplateType = ? ,nCurrencyID = ? where ID = ?");
			transPS.setString(1, strDesc);
			transPS.setString(2, strPrintName);
			transPS.setDouble(3, dTop);
			transPS.setDouble(4, dLeft);
			transPS.setLong(5,	nPrintTemplateType);
			transPS.setLong(6,	lCurrencyID);
			transPS.setLong(7, lTemplateID);
			result = transPS.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return (result);
	
	}
//	查询给出模版条件的所有显示条目
	public Collection findPrintOptionDetailsByTemplateID(long lTemplateID) throws SettlementException
	{

		Vector returnVector = new Vector();
		try
		{
			this.initDAO();
			StringBuffer sbRecord = new StringBuffer();
			sbRecord.append(
				" select a.ID  aID,a.SCODE  aScode ,a.Sname  asName ,a.sdata  asData,a.ftop  aFtop  ,a.fleft  aFleft ,a.sfont  asFont,a.ntypeid  anTypeID , a.nisbold  anIsBold  ,a.nisitalic  anIsItalic ,a.fsize  afSize ,a.NFIELDWIDTH  anFielDwidth,b.sname  bsName,b.sdesc  bsDesc ,b.sPrinterName   bsPrinterName ,b.ftop  bfTop  ,b.fleft  bfLeft ,b.nCurrencyID bnCurrencyID , b.nPrintTemplateType bnPrintTemplateType   from  Sett_PRINTTEMPLATEDETAILS  a,Sett_PRINTTEMPLATE  b  WHERE  a.scode like '__'  and  a.NPRINTTEMPLATEID = b.id  and b.id = ?");
			
			transPS = transConn.prepareStatement(sbRecord.toString());
			transPS.setLong(1, lTemplateID);
			transRS = transPS.executeQuery();
			while (transRS != null && transRS.next())
			{
				PrintOptionInfo printOptionInfo = new PrintOptionInfo();
				System.out.println("asdfasdfasdfasdfasdfasd");
				printOptionInfo.m_lTemplateDetailsID = transRS.getLong("aID");
				printOptionInfo.m_dDetailsTop = transRS.getDouble("aFtop");
				System.out.println("asdfasdfasdfasdfasd111fasd");
				printOptionInfo.m_dDetailsLeft = transRS.getDouble("aFleft");
				printOptionInfo.m_strDetailsName = transRS.getString("asName");
				printOptionInfo.m_strDetailsData = transRS.getString("asData");
				printOptionInfo.m_strPrintName = transRS.getString("bsPrinterName");
				printOptionInfo.m_strCode = transRS.getString("aScode");
				printOptionInfo.m_strFont = transRS.getString("asFont");
				printOptionInfo.m_lTypeID = transRS.getLong("anTypeID");
				printOptionInfo.m_lIsBold = transRS.getLong("anIsBold");
				printOptionInfo.m_lIsItalic = transRS.getLong("anIsItalic");
				printOptionInfo.m_lSize = transRS.getDouble("afSize");
				printOptionInfo.m_lFiledWidth = transRS.getLong("anFielDwidth");
				printOptionInfo.m_strName = transRS.getString("bsName");
				printOptionInfo.m_strDesc = transRS.getString("bsDesc");
				printOptionInfo.m_lIsBold = transRS.getLong("anIsBold");
				printOptionInfo.m_dTemplateTop = transRS.getDouble("bfTop");
				printOptionInfo.m_dTemplateLeft = transRS.getDouble("bfLeft");
				
				printOptionInfo.m_lCurrencyID = transRS.getLong("bnCurrencyID");		// 币种
				printOptionInfo.m_nPrintTemplateType = transRS.getLong("bnPrintTemplateType");	//模版的类型 1：存款  2:贷款
				
				returnVector.addElement(printOptionInfo);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return (returnVector.size() > 0 ? returnVector : null);
	
	}
	
	//添加某一套打模版的设置信息
	public long addTemplate(long lOfficeID,long lCurrencyID,long nPrintTemplateType, String strName , String strDesc, String strPrintName, double dTop, double dLeft,long nStatusID) throws SettlementException
	{

		long result = 0;
		long lMaxID=-1;
		StringBuffer sbSQL=new StringBuffer();
		sbSQL.setLength(0);
		try
		{
			this.initDAO();
			//得到当前表中最大的ID号
			sbSQL.append(" select nvl(max(ID),0) maxID from Sett_PRINTTEMPLATE ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=======SQL:"+sbSQL.toString());
			transRS=transPS.executeQuery();
			if(transRS!=null && transRS.next()){
				lMaxID=transRS.getLong("maxID");
			}
			System.out.println("============得到数据库中当前最大的ID号为："+(lMaxID++));
			
			//进行SQL插值处理
			sbSQL.setLength(0);
			sbSQL.append(" insert into Sett_PRINTTEMPLATE ( ID,NOFFICEID,SNAME,SDESC,SPRINTERNAME,FTOP,FLEFT,NSTATUSID,nCurrencyID,nPrintTemplateType) values (?,?,?,?,?,?,?,?,?,?)");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=======SQL:"+sbSQL.toString());
			transPS.setLong(1, (lMaxID++));
			transPS.setLong(2, lOfficeID);
			transPS.setString(3, strName);
			transPS.setString(4, strDesc);
			transPS.setString(5, strPrintName);
			transPS.setDouble(6, dTop);
			transPS.setDouble(7, dLeft);
			transPS.setLong(8, nStatusID);
			transPS.setLong(9, lCurrencyID);
			transPS.setLong(10, nPrintTemplateType);
			result = transPS.executeUpdate();
		}
		catch (Exception e)
		{
			result=-1;
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				result=-1;
				e.printStackTrace();
			}
		}
		return (result);
	
	}
	
	//删除某一套打模版的设置信息
	public long delTemplate(long lTemplateID) throws SettlementException
	{
		long result = 0;
		StringBuffer sbSQL=new StringBuffer();
		sbSQL.setLength(0);
		try
		{
			this.initDAO();
			//删除执行指定ID主表模版信息		(逻辑删除)
			sbSQL.append(" update Sett_PRINTTEMPLATE  set nStatusID = ? where id = ? ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=======SQL:"+sbSQL.toString());
			transPS.setLong(1, Constant.RecordStatus.INVALID);	//无效状态
			transPS.setLong(2, lTemplateID);					//指定的模版ID
			result = transPS.executeUpdate();
			
			//删除执行指定ID从表模版信息		(物理删除)
			sbSQL.setLength(0);
			sbSQL.append(" delete Sett_PRINTTEMPLATEDETAILS  where NPRINTTEMPLATEID = ? ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=======SQL:"+sbSQL.toString());
			transPS.setLong(1, lTemplateID);					//指定的模版ID
			result = transPS.executeUpdate();
		}
		catch (Exception e)
		{
			result=-1;
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				result=-1;
				e.printStackTrace();
			}
		}
		return (result);
	}
	
	
	
	//添加某一套打模版元素的设置信息
	public long addTemplateDetails(long lPrintTemplateID,String strCode,String strName,String strDesc,	String strData,double dTop,double dLeft,String strFont,long lTypeID,long lIsBold,long lIsItalic,double lSize,	long lFiledWidth) throws SettlementException
	{

		long result = 0;
		long lMaxID=-1;
		StringBuffer sbSQL=new StringBuffer();
		sbSQL.setLength(0);
		try
		{
			this.initDAO();
			//得到当前表中最大的ID号
			sbSQL.append(" select nvl(max(ID),0) maxID from Sett_PRINTTEMPLATEDETAILS ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=======SQL:"+sbSQL.toString());
			transRS=transPS.executeQuery();
			if(transRS!=null && transRS.next()){
				lMaxID=transRS.getLong("maxID");
			}
			System.out.println("============得到数据库中当前最大的ID号为："+(lMaxID++));
			
			//得到sCode号的值(和谢商量估计要加上一些常量)
			//在前台地的jsp提供一个下拉框供选择
			
			//进行SQL插值处理
			sbSQL.setLength(0);
			
			sbSQL.append(" insert into Sett_PRINTTEMPLATEDETAILS ( ID,NPRINTTEMPLATEID,SCODE,SNAME,SDESC,SDATA,FTOP,FLEFT,SFONT,NTYPEID,NISBOLD,NISITALIC,FSIZE,NFIELDWIDTH) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("=======SQL:"+sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transPS.setLong(1, (lMaxID++));
			transPS.setLong(2, lPrintTemplateID);
			transPS.setString(3, strCode);
			transPS.setString(4, strName);
			transPS.setString(5, strDesc);
			transPS.setString(6, strData);
			transPS.setDouble(7, dTop);
			transPS.setDouble(8, dLeft);
			transPS.setString(9, strFont);
			transPS.setLong(10, lTypeID);
			transPS.setLong(11, lIsBold);
			transPS.setLong(12, lIsItalic);
			transPS.setDouble(13, lSize);
			transPS.setLong(14, lFiledWidth);
			
			//transPS.setLong(15, nStatusID);(作物理操作没有此状态设置)
			result = transPS.executeUpdate();
		}
		catch (Exception e)
		{
			result=-1;
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				result=-1;
				e.printStackTrace();
			}
		}
		return (result);
	
	}
	
	//删除某一套打模版元素的设置信息
	public long delTemplateDetails(long lTemplateDetailsID) throws SettlementException
	{
		long result = 0;
		StringBuffer sbSQL=new StringBuffer();
		sbSQL.setLength(0);
		try
		{
			this.initDAO();
			//删除执行指定ID从表模版信息	(物理删除)
			sbSQL.setLength(0);
			sbSQL.append(" delete Sett_PRINTTEMPLATEDETAILS  where ID = ? ");
			transPS = transConn.prepareStatement(sbSQL.toString());
			System.out.println("=======SQL:"+sbSQL.toString());
			transPS.setLong(1, lTemplateDetailsID);					//指定的模版ID
			result = transPS.executeUpdate();
		}
		catch (Exception e)
		{
			result=-1;
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				this.finalizeDAO();
			}
			catch (Exception e)
			{
				result=-1;
				e.printStackTrace();
			}
		}
		return (result);
	}
}
