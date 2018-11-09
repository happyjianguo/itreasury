/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataentity.CurrencyInfo;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Env
{
	/**当前系统环境对象的静态实例*/
	private static Env INSTANCE = new Env(); //静态实例
	private Properties properties = null; //属性文件所对应的属性对象变量
	private static Properties DEFAULT_PROPERTIES = null; //记录配置项的默认配置值
	public static String ROOT_URL = "/NASApp/";
	public static String SETTLEMENT_URL = "/NASApp/iTreasury-settlement/";
	public static String LOAN_URL = "/NASApp/iTreasury-loan/";
	public static String SECURITIES_URL = "/NASApp/iTreasury-securities/";
	public static String EBANK_URL = "/NASApp/iTreasury-ebank/";
	public static String CLIENTCENTER_URL = "/NASApp/iTreasury-treasuryplan/";
	public static String SYSTEM_URL = "/NASApp/iTreasury-system/";
	public static String PLAN_URL = "/NASApp/iTreasury-treasuryplan/";
	public static String FUNDPLAN_URL = "/NASApp/iTreasury-settlement/";//增加新的“资金计划”菜单 2008-11-25No.196增加 kaishao
	public static String BILL_URL = "/NASApp/iTreasury-bill/";
	public static String FOREIGN_URL = "/NASApp/iTreasury-loan/";
	public static String BUDGET_URL = "/NASApp/iTreasury-budget/";
	public static String CLIENTMANAGE_URL="/NASApp/iTreasury-clientmanage/";
	public static String GENERALLEDGER_URL="/NASApp/iTreasury-system/";
	public static String REPORT_URL="/NASApp/iTreasury-report/"; //add by xfma 2008-10-6
	public static String MANAGERQUERY_URL="/NASApp/iTreasury-managerQuery/"; //add by xfma 2008-10-6
	public static String CLIENTMANAGE_SIMPLE_URL="/NASApp/";
	public static String EVOUCHER_URL="/NASApp/iTreasury-evoucher/";
	public static String BANKPORTAL_URL="/NASApp/iTreasury-bankportal/";
	public static String CRAFTBROTHER_URL="/NASApp/iTreasury-craftbrother/";
	public static String ARCHIVESMANAGEMENT_URL="/NASApp/iTreasury-archivesmanagement/";
	public static String AUDIT_URL="/NASApp/iTreasury-audit/"; 
	public static String CRERT_URL="/NASApp/iTreasury-creditrating/";
	public static String CPF_OS = "windows";
	public static String RESOURCE_JNDI_NAME="";
	public static String UPLOAD_PATH = "/upload/";
	private static String CLIENT_NAME = "南方电网财务有限公司";
	public static boolean CHINESE_ENCODE = false;
	public static String URL_PREFIX = "/NASApp";
	private static String WEBSERVER_URL = "http://10.91.19.16";
	private static final String PFILE = "itreasury.properties";
	/**
	 * SAP 连接参数 ADD BY XIANGZHOU 2011-03-30
	 */
	private static String SAP_CLIENT = "";
	private static String SAP_USERID = "";
	private static String SAP_PASSWORD = "";
	private static String SAP_LANGUAGE = "";
	private static String SAP_HOSTNAME = "";
	private static String SAP_SYSTEMNUMBER = "";
	private static String SAP_SYSTEMCODE = "";
	private static String SAP_PROGRAMID = "";
	private static String SAP_UNICODE = "";
	
	private static String SAP_CLIENT_1 = "";
	private static String SAP_USERID_1 = "";
	private static String SAP_PASSWORD_1 = "";
	private static String SAP_LANGUAGE_1 = "";
	private static String SAP_HOSTNAME_1 = "";
	private static String SAP_SYSTEMNUMBER_1 = "";
	private static String SAP_SYSTEMCODE_1 = "";
	private static String SAP_PROGRAMID_1 = "";
	private static String SAP_UNICODE_1 = "";
	
	/**
     * 总部办事处ID
     * 如果是-1，则都是总部
     * **/
    //public static final String HQOFFICEID = "HQOfficeID";
	public static long HQOFFICEID = -1;
	// 对应于属性文件的文件对象变量
	private static File m_file = null;
	// 属性文件的最后修改日期
	private static long m_lastModifiedTime = 0;
	// 属性文件所对应的属性对象变量
	private static Properties m_props = null;
	//	 属性文件所对应的属性对象变量
	private static Hashtable CurrencyHash = null;
	private static Hashtable AccountTypeHash = null;
	private static Env m_instance = new Env();
	public  static ArrayList connectionList = new ArrayList();

	private Env()
	{
		m_file = new File(PFILE);
		m_lastModifiedTime = m_file.lastModified();
		if (m_lastModifiedTime == 0)
		{
			System.err.println(PFILE + " file does exist !");
		}
		m_props = new Properties();
		try
		{
			FileInputStream is = new FileInputStream(PFILE);
			m_props.load(is);
			is.close();
			getAllEnvItem();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
     * 判断某一办事处是否是总部
     * 是，返回true；否则，返回false
     * 
	 * @param officeID
	 * @return
	 */
//	public static boolean isHQ(long officeID)
//	{
//		long lOfficeID = Long.parseLong(getEnvConfigItem(HQOFFICEID));
//		if(lOfficeID < 0 || lOfficeID == officeID)
//		{
//			return true;
//		}		
//		return false;
//	}
	
	/**
	 * 构造所有配置项的显示信息
	 * @return String
	 */
	public static String showAllEnvConfigItem()
	{
		StringBuffer sbTemp = new StringBuffer(128);

		Enumeration enu = INSTANCE.properties.keys();

		if (enu != null)
		{
			String key = null;

			while (enu.hasMoreElements())
			{
				key = (String) enu.nextElement();
				sbTemp.append(
					"EnvItem  "
						+ key
						+ " : "
						+ INSTANCE.properties.getProperty(key) + "\n");
			}
		}

		return sbTemp.toString();
	}
	/**
	 * 获取指定名称的配置信息
	 * @param name
	 * @return
	 */
	public static String getEnvConfigItem(String name)
	{
		String val = null;
		val = INSTANCE.properties.getProperty(name);
		//对属性文件的内容进行转码
		//之所以不在load入文件的时候统一做转码，
		//是因为在往Properties里set之后，之前做的转码工作无效，
		//可能在Hashtable的put方法里再对值进行了转码
		//这是测试后得出的结论
        if(val != null)
        {
        	try
			{
				val = new String(val.getBytes("ISO8859_1"),"GBK");
			} catch (UnsupportedEncodingException e)
			{				
			}
        }
		//如果未配置，则读取默认值
		if (val == null)
		{
			val = DEFAULT_PROPERTIES.getProperty(name);
		}		
        
		return val;
	}
	
	public void getAllEnvItem()
	{
		String EnvItem = null;
		getSETTLEMENT_URL();
		getLOAN_URL();
		getSECURITIES_URL();
		getEBANK_URL();
		getPLAN_URL();
		getCPF_OS();
		getUPLOAD_PATH();
		getClientName();		
		isCHINESE_ENCODE();
		getWEBSERVER_URL();
		getSAP_CLIENT();
		getSAP_USERID();
		getSAP_PASSWORD();
		getSAP_LANGUAGE();
		getSAP_HOSTNAME();
		getSAP_SYSTEMNUMBER();
		getSAP_SYSTEMCODE();
		getSAP_PROGRAMID();
		getSAP_UNICODE();
		getSAP_CLIENT_1();
		getSAP_USERID_1();
		getSAP_PASSWORD_1();
		getSAP_LANGUAGE_1();
		getSAP_HOSTNAME_1();
		getSAP_SYSTEMNUMBER_1();
		getSAP_SYSTEMCODE_1();
		getSAP_PROGRAMID_1();
		getSAP_UNICODE_1();
	}
	public static String getEnvItem(String name)
	{
		String val = null;
		// 检查属性文件是否被修改过
		long newTime = m_file.lastModified();
		// 如果是，读取属性文件
		if (newTime == 0)
		{
			val = null;
		}
		else
			if (newTime > m_lastModifiedTime)
			{
				m_props.clear();
				try
				{
					FileInputStream is = new FileInputStream(PFILE);
					m_props.load(is);
					is.close();
					m_lastModifiedTime = newTime;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		try
		{
			val = m_props.getProperty(name);
			//System.out.println("Env Item '" + name + "' ----------- " + val);
		}
		catch (Exception e)
		{
			System.out.println("读取 env item '" + name + "' 错误.");
		}
		return val;
	}
	
	public static String getEnvItem(String name,String path) throws Exception 
	{
		String val = "";
		try
		{
			m_props.clear();
			FileInputStream fs = new FileInputStream(path);
			m_props.load(fs);
			fs.close();
			val = m_props.getProperty(name);
			val = new String(val.getBytes("ISO-8859-1"),"GBK");
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("读取配置文件出错!");
		}
		return val;
	}
	
	public static Env getInstance()
	{
		return m_instance;
	}
	/**
	 * @return
	 */
	public static String getClientName()
	{
		String str = "";
		try
		{
			String EnvItem = getEnvItem("client.name");
			CLIENT_NAME = EnvItem != null ? EnvItem : CLIENT_NAME;
			str = new String(Env.CLIENT_NAME.getBytes("ISO-8859-1"), "GBK");
			//str = Env.CLIENT_NAME ;
		}
		catch (Exception e)
		{
			System.out.println("Can not getClient_Name " + Env.CLIENT_NAME);
		}
		return str;
	}
	public static String getClientName(long clientID)
	{
		String clientName="";
		String SQL="select sname from client where id="+clientID;
		System.out.println(SQL);
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection con = null;
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				clientName=rs.getString("sname");
				System.out.println("getOfficeName"+clientName);
			}
			else{
				System.out.println("rs结果为空");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				e.printStackTrace();
			}
			exp.printStackTrace();
		}
		
		return clientName;
	}
	/**
	 * @param lModule
	 * @return
	 */
	public String getURL(long lModule)
	{
		if (lModule == Constant.ModuleType.SETTLEMENT)
		{
			return SETTLEMENT_URL;
		}
		else if (lModule == Constant.ModuleType.SYSTEM)
		{
			return SYSTEM_URL;
		}
		else if (lModule == Constant.ModuleType.LOAN)
		{
			return LOAN_URL;
		}
		else if (lModule == Constant.ModuleType.EBANK)
		{
			return EBANK_URL;
		}
		else if (lModule == Constant.ModuleType.CLIENTCENTER)
		{
			return CLIENTCENTER_URL;
		}
		else if (lModule == Constant.ModuleType.PLAN)
		{
			return PLAN_URL;
		}
		//增加新的“资金计划”菜单 2008-11-25No.196增加 kaishao
		else if (lModule == Constant.ModuleType.FUNDPLAN)
		{
			return FUNDPLAN_URL;
		}
		//No.196结束
		else if (lModule == Constant.ModuleType.SECURITIES)
		{
			return SECURITIES_URL;
		}
		else if (lModule == Constant.ModuleType.BILL)
		{
			return BILL_URL;
		}
		else if (lModule == Constant.ModuleType.FOREIGN)
		{
			return FOREIGN_URL;
		}
		else if (lModule == Constant.ModuleType.BUDGET)
		{
			return BUDGET_URL;
		}
		else if (lModule == Constant.ModuleType.CLIENTMANAGE)
		{
			return CLIENTMANAGE_URL;
		}
		else if (lModule == Constant.ModuleType.GENERALLEDGER)
		{
			return GENERALLEDGER_URL;
		}
		else if (lModule == Constant.ModuleType.REPORT)//add by xfma 2008-10-6
		{
			return REPORT_URL;
		}
		else if (lModule == Constant.ModuleType.MANAGER)
		{
			return SETTLEMENT_URL;
		}
		else if (lModule == Constant.ModuleType.MANAGERQUERY)//add by xfma 2008-10-8
		{
			return MANAGERQUERY_URL;
		}
		else if (lModule == Constant.ModuleType.EVOUCHER)
		{
			return EVOUCHER_URL;
		}
		else if (lModule == Constant.ModuleType.BANKPORTAL){
			return BANKPORTAL_URL;
		}
        else if (lModule == Constant.ModuleType.TREASURYMONITOR)
        {
            return BANKPORTAL_URL;
        }
        else if (lModule == Constant.ModuleType.CRAFTBROTHER){
			return CRAFTBROTHER_URL;
		}
        else if (lModule == Constant.ModuleType.ARCHIVESMANAGEMENT){
			return ARCHIVESMANAGEMENT_URL;
		}
        else if (lModule == Constant.ModuleType.AUDIT){
			return AUDIT_URL;
		}
        else if (lModule == Constant.ModuleType.CREDITRATING){
			return CRERT_URL;
		}
		else {
		    return SETTLEMENT_URL;
		}
	}
	/**
	 * 获得Timestamp类型的系统时间（开机时间）
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 */
	public static Timestamp getSystemDate(long lOfficeID, long lCurrencyID)
	{
		Timestamp tsDate = null;
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		Connection con = null;
		try
		{
			con = Database.getConnection();
			strSQL =
				"select to_char(dtOpenDate,'yyyy') as syear,to_char(dtOpenDate,'mm') as smonth, to_char(dtOpenDate,'dd') as sDay from sett_officetime where ncurrencyid="
					+ lCurrencyID
					+ " and nOfficeID="
					+ lOfficeID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int nYear = Integer.parseInt(rs.getString("sYear"));
				int nMonth = Integer.parseInt(rs.getString("sMonth"));
				int nDay = Integer.parseInt(rs.getString("sDay"));
				tsDate = DataFormat.getDateTime(nYear, nMonth, nDay, 0, 0, 0);
				//精度问题
				tsDate = DataFormat.getDateTime(DataFormat.getDateString(tsDate));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				;
			}
		}
		return tsDate;
	}
	/**
	 * 获得Timestamp类型的系统时间（开机时间）
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 */
	public static Timestamp getSystemDate(Connection con, long lOfficeID, long lCurrencyID)
	{
		Timestamp tsDate = null;
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		try
		{
			strSQL =
				"select to_char(dtOpenDate,'yyyy') as syear,to_char(dtOpenDate,'mm') as smonth, to_char(dtOpenDate,'dd') as sDay from sett_officetime where ncurrencyid="
					+ lCurrencyID
					+ " and nOfficeID="
					+ lOfficeID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int nYear = Integer.parseInt(rs.getString("sYear"));
				int nMonth = Integer.parseInt(rs.getString("sMonth"));
				int nDay = Integer.parseInt(rs.getString("sDay"));
				tsDate = DataFormat.getDateTime(nYear, nMonth, nDay, 0, 0, 0);
				//精度问题
				tsDate = DataFormat.getDateTime(DataFormat.getDateString(tsDate));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return tsDate;
	}
	/**
	 * 获得String类型的系统开机时间
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 */
	public static String getSystemDateString(long lOfficeID, long lCurrencyID)
	{
		String str = "";
		try
		{
			Timestamp ts = getSystemDate(lOfficeID, lCurrencyID);
			str = DataFormat.getDateString(ts);
		}
		catch (Exception exp)
		{
			Log.print(exp.toString());
		}
		return str;
	}
	/**
	 * 获得Timestamp类型的系统时间（数据库时间） 结算系统都用开机时间，请不要用此时间
	 * 
	 * @return
	 */
	public static Timestamp getSystemDate()
	{
		Timestamp tsDate = null;
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		Connection con = null;
		try
		{
			con = Database.getConnection();
			strSQL = "select to_char(sysDate,'yyyy') as syear,to_char(sysDate,'mm') as smonth, to_char(sysDate,'dd') as sDay from dual";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int nYear = Integer.parseInt(rs.getString("sYear"));
				int nMonth = Integer.parseInt(rs.getString("sMonth"));
				int nDay = Integer.parseInt(rs.getString("sDay"));
				tsDate = DataFormat.getDateTime(nYear, nMonth, nDay, 0, 0, 0);
				//精度问题
				tsDate = DataFormat.getDateTime(DataFormat.getDateString(tsDate));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				;
			}
		}
		return tsDate;
	}
	/**
	 * 获取当前客户所在结算中心的名称 
	 */
	public static String getOfficeName(long officeID){
		String officeName="";
		String SQL="select sname from office where id="+officeID;
		System.out.println(SQL);
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection con = null;
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				officeName=rs.getString("sname");
				System.out.println("getOfficeName"+officeName);
			}
			else{
				System.out.println("rs结果为空");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				e.printStackTrace();
			}
			exp.printStackTrace();
		}
		
		return officeName;
	}
   /**
    * 通过office的id得到办事处的币种
    * @author haoliang
    * @param office
    * @return String
    */
   public static String getOfficeCurrencyId(long office)
   {
	   String currencyid="";
		String SQL="select currencyid from office where id="+office;
		System.out.println(SQL);
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection con = null;
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				currencyid=rs.getString("currencyid");
				if(currencyid == null){
					currencyid = "-1";
				}
				System.out.println("getOfficeCurrencyId=="+currencyid);
			}
			else{
				System.out.println("rs结果为空");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				e.printStackTrace();
			}
			exp.printStackTrace();
		}
		
		return currencyid;
   }
	
   /**
    * 通过得到的办事处已有的币种id得到币种信息
    * @author haoliang
    * @param currencyid
    * @return Collection
    */
   public static Collection getCurrencyInfo(String currencyid)
   {
	    String SQL = "";
	    if(currencyid.equals("-1")){
	    	SQL = "select ID,Name,Code,Symbol,Status from CurrencyInfo";
	    }else{
	    	SQL = "select ID,Name,Code,Symbol,Status from CurrencyInfo where ID not in("+currencyid+")";
	    }
	    Collection coll = new ArrayList();
		System.out.println(SQL);
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		Connection con = null;
		try
		{
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			while(rs.next()){
				CurrencyInfo info = new CurrencyInfo(); 
				info.setID(rs.getLong("ID"));
				info.setName(rs.getString("Name"));
				info.setCode(rs.getString("Code"));
				info.setSymbol(rs.getString("Symbol"));
				info.setStatus(rs.getLong("Status"));
				coll.add(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				e.printStackTrace();
			}
			exp.printStackTrace();
		}
		
		return coll;
   }
	
	/**
	 * 获得String类型的系统时间（数据库时间） 结算系统都用开机时间，请不要用此时间
	 * 
	 * @return
	 */
	public static String getSystemDateString()
	{
		String str = "";
		try
		{
			Timestamp ts = getSystemDate();
			str = DataFormat.getDateString(ts);
		}
		catch (Exception exp)
		{
			Log.print(exp.toString());
		}
		return str;
	}
	/**
	 * Method getSystemDateTime.
	 * 获得Timestamp类型的系统时间（数据库时间，精确到秒） 结算系统都用开机时间，请不要用此时间
	 * @return Timestamp
	 */
	public static Timestamp getSystemDateTime()
	{
		Timestamp tsDate = null;
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		Connection con = null;
		try
		{
			con = Database.getConnection();
			strSQL = "select sysDate from dual";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//精度问题
				tsDate = rs.getTimestamp("sysDate");
			}
		}
		catch (Exception exp)
		{
			//do nothing
		}finally{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				;
			}
		}
		return tsDate;
	}
	//TODO 代码调整 bingliu
	/**
	* 取得系统状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static long getSystemStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		long lStatusID = -1;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select sSystemStatusDesc from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				lStatusID = Constant.SystemStatus.getID(rs.getString(1));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		};
		return lStatusID;
	}
	
	/**
	* 取得开/关机处理状态
	* @param lOfficeID 办事处标识
	* @param lCurrencyID 币种标识
	* @param lModelID 模块标识 
	* return 处理状态
	*/
	public static long getDealStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		long lStatusID = -1;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sbRecord.setLength(0);
			sbRecord.append(" select nCloseSystemStatusID from  sett_OfficeTime where nOfficeID = ? and nCurrencyID = ?  ");
			ps = conn.prepareStatement(sbRecord.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();
			//Log.print(sbRecord.toString());
			if (rs.next())
			{
				lStatusID = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();

			throw e;
		}
		finally
		{
			try
			{
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		};
		return lStatusID;
	}
	
	/**
	 * Method getSystemDateTimeString.
	 * 获得String类型的系统时间（数据库时间，精确到秒） 结算系统都用开机时间，请不要用此时间
	 * @return String
	 */
	public static String getSystemDateTimeString()
	{
		String strReturn = null;
		try
		{
			Timestamp ts = getSystemDateTime();
			strReturn = DataFormat.getDateString(ts);
		}
		catch (Exception exp)
		{
			Log.print(exp.toString());
		}
		return strReturn;
	}
	/**
	 * 获取证券模块当前系统时间 由于目前证券模块对于开关机的机制尚未确定，因此暂时取服务器系统时间作为当前时间．
	 */
	public static Timestamp getSecuritiesSystemDate(long officeID, long currencyID)
	{
		return getCurrentSystemDate();
	}
	
	/**
		 * 获取票据管理模块当前系统时间 
		 */
	public static Timestamp getBillSystemDate(long officeID, long currencyID)
	{
		return getSystemDate(officeID,currencyID);
	}
	
	/**
	 *获取当前应用服务器日期
	 * */
	public static Timestamp getCurrentSystemDate()
	{
		Calendar cal = Calendar.getInstance();
		Timestamp res = new Timestamp(cal.getTime().getTime());
		return DataFormat.getDateTime(DataFormat.getDateString(res));
	}

	/**
	 * @return Returns the uPLOAD_PATH.
	 */
	public String getUPLOAD_PATH()
	{
		String EnvItem = getEnvItem("upload.path");
		UPLOAD_PATH = EnvItem != null ? EnvItem : UPLOAD_PATH;
		return UPLOAD_PATH;
	}
	public String getWEBSERVER_URL()
	{
		String EnvItem = getEnvItem("webserver.url");
		WEBSERVER_URL = EnvItem != null ? EnvItem : WEBSERVER_URL;
		return WEBSERVER_URL;
	}
	/**
	 * @return Returns the cHINESE_ENCODE.
	 */
	public  boolean isCHINESE_ENCODE()
	{
		String EnvItem = getEnvItem("chinese.encode");
		if (EnvItem != null)
		{
			if (EnvItem.equals("true"))
				CHINESE_ENCODE = true;
			else
				CHINESE_ENCODE = false;
		}
		else
			CHINESE_ENCODE = false;
		return CHINESE_ENCODE;
	}

	public String getSETTLEMENT_URL()
	{
		String EnvItem = getEnvItem("settlement.url");
		SETTLEMENT_URL = EnvItem != null ? EnvItem : SETTLEMENT_URL;
		return SETTLEMENT_URL;
	}
	public String getLOAN_URL()
	{
		String EnvItem = getEnvItem("loan.url");
		LOAN_URL = EnvItem != null ? EnvItem : LOAN_URL;
		return LOAN_URL;
	}
	public String getPLAN_URL()
	{
		String EnvItem = getEnvItem("plan.url");
		PLAN_URL = EnvItem != null ? EnvItem : PLAN_URL;
		return PLAN_URL;
	}
	public String getSECURITIES_URL()
	{
		String EnvItem = getEnvItem("securities.url");
		SECURITIES_URL = EnvItem != null ? EnvItem : SECURITIES_URL;
		return SECURITIES_URL;
	}
	public String getEBANK_URL()
	{
		String EnvItem = getEnvItem("ebank.url");
		EBANK_URL = EnvItem != null ? EnvItem : EBANK_URL;
		return EBANK_URL;
	}

	public static String getCPF_OS()
	{
		String EnvItem = getEnvItem("cpf.os");
		CPF_OS = EnvItem != null ? EnvItem : CPF_OS;
		return CPF_OS;
	}
	
	/**
	 * 针对联通提供，获得总公司的银行存款账户号
	 * @return String
	 */
/*	
	public String getBANK_DEPOSIT_ACCOUNTNO_OF_HQ()
	{
		String EnvItem = getEnvItem("hq.bank.deposit.accountno");
		BANK_DEPOSIT_ACCOUNTNO_OF_HQ = EnvItem != null ? EnvItem : GL_NAME;
		return BANK_DEPOSIT_ACCOUNTNO_OF_HQ;
	}
*/
    /**
     * @return Returns the currencyHash.
     */
    public static Hashtable getCurrencyHash()
    {
    	//System.out.println(" getCurrencyHash():"+CurrencyHash);
    	if(CurrencyHash == null || CurrencyHash.size() == 0 )
			setCurrencyHash();
    	
        return CurrencyHash;
    }
    /**
     * @param currencyHash The currencyHash to set.
     */
    public static void setCurrencyHash()
    {
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		Connection con = null;
		try
		{
		    CurrencyHash = new Hashtable();
			con = Database.getConnection();
			strSQL = " select ID,Name,Code,Symbol,Status from CurrencyInfo where Status = "+Constant.RecordStatus.VALID+" order by ID asc ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{ 
			    CurrencyInfo info = new CurrencyInfo();
			    String key = rs.getString("ID");
			    info.setID(rs.getLong("ID"));
			    info.setName(rs.getString("Name"));
			    info.setCode(rs.getString("Code"));
			    info.setSymbol(rs.getString("Symbol"));
			    info.setStatus(rs.getLong("Status"));
			    CurrencyHash.put(key,info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				;
			}
		} 
	}
    /**
     * 
     * @return
     */
    public static Hashtable getAccountTypeHash()
    {
    	if(AccountTypeHash == null || AccountTypeHash.size() == 0 )
    		setAccountTypeHash();
    	
        return AccountTypeHash;
    }
    /**
     * add by zcwang 2007-7-7
     * @return
     */
    public static Hashtable getAccountTypeHash(long lAccountTypeID)
    {
    	if(AccountTypeHash == null || AccountTypeHash.size() == 0 || AccountTypeHash.get(String.valueOf(lAccountTypeID))==null)
    		setAccountTypeHash();
    	
        return AccountTypeHash;
    }
    
    /**
     * 2007.6.14
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     */
    public static Hashtable getAccountTypeHashByOfficeAndCurrency(long lOfficeID,long lCurrencyID)
    {
    	//if(AccountTypeHash == null || AccountTypeHash.size() == 0 )
    		setAccountTypeHashByOfficeAndCurrency(lOfficeID,lCurrencyID);
    	
        return AccountTypeHash;
    }
    public static Hashtable getAccountTypeHashByAccountGroupIds(long lOfficeID,long lCurrencyID,long[] AccountGroupIds)
    {
    	setAccountTypeHashByAccountGroupIds(lOfficeID,lCurrencyID,AccountGroupIds);    	
        return AccountTypeHash;
    }    
    
    /**
     * 2008-04-10 by zcwang
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     */
    public static Hashtable getAccountTypeHashByOfficeCurrencyAndCode(long lOfficeID,long lCurrencyID)
    {
    	//if(AccountTypeHash == null || AccountTypeHash.size() == 0 )
    	setAccountTypeHashByOfficeCurrencyAndCode(lOfficeID,lCurrencyID);
    	
        return AccountTypeHash;
    }
    /**
     * 
     *
     */
    public static void setAccountTypeHash()
    {
    	AccountTypeHash = new Hashtable();
    	Sett_AccountTypeDAO dao = new Sett_AccountTypeDAO();
    	ArrayList list = (ArrayList)dao.findAllAccountType();
    	if (list != null && list.size() > 0)
    	{
    		for (int i=0;i<list.size();i++)
    		{
    			AccountTypeInfo info = (AccountTypeInfo)list.get(i);
    			AccountTypeHash.put(String.valueOf(info.getId()),info);
    		}
    	}
    }
    /**
     * 2007.6.14
     * @param lOfficeID
     * @param lCurrencyID
     */
    public static void setAccountTypeHashByOfficeAndCurrency(long lOfficeID,long lCurrencyID)
    {
    	AccountTypeHash = new Hashtable();
    	Sett_AccountTypeDAO dao = new Sett_AccountTypeDAO();
    	ArrayList list = (ArrayList)dao.findAllAccountTypeByOfficeAndCurrency(lOfficeID, lCurrencyID);
    	if (list != null && list.size() > 0)
    	{
    		for (int i=0;i<list.size();i++)
    		{
    			AccountTypeInfo info = (AccountTypeInfo)list.get(i);
    			AccountTypeHash.put(String.valueOf(info.getId()),info);
    		}
    	}
    }
    public static void setAccountTypeHashByAccountGroupIds(long lOfficeID,long lCurrencyID,long[] AccountGroupIds)
    {
    	AccountTypeHash = new Hashtable();
    	Sett_AccountTypeDAO dao = new Sett_AccountTypeDAO();
    	ArrayList list = (ArrayList)dao.findAccountTypeByAccountGroupIds(lOfficeID, lCurrencyID,AccountGroupIds);
    	if (list != null && list.size() > 0)
    	{
    		for (int i=0;i<list.size();i++)
    		{
    			AccountTypeInfo info = (AccountTypeInfo)list.get(i);
    			AccountTypeHash.put(String.valueOf(info.getId()),info);
    		}
    	}
    }
    /**
     * 2008-04-10 by  zcwang 
     * @param lOfficeID
     * @param lCurrencyID
     */
    public static void setAccountTypeHashByOfficeCurrencyAndCode(long lOfficeID,long lCurrencyID)
    {
    	AccountTypeHash = new Hashtable();
    	Sett_AccountTypeDAO dao = new Sett_AccountTypeDAO();
    	ArrayList list = (ArrayList)dao.findAllAccountTypeByOfficeAndCurrency(lOfficeID, lCurrencyID);
    	if (list != null && list.size() > 0)
    	{
    		for (int i=0;i<list.size();i++)
    		{
    			AccountTypeInfo info = (AccountTypeInfo)list.get(i);
    			AccountTypeHash.put(String.valueOf(Long.valueOf(info.getAccountTypeCode())),info);
    		}
    	}
    }
    public static void main(String[] args){
    	//System.out.print("1111");
    	System.out.print("officeName:"+getOfficeName(2));
    }

	/**
	 * 获得Timestamp类型的系统时间（开机时间,精确到秒）
	 * 备注：此时间取的是开机时间的年月日+数据库时间的时分秒
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 * @throws Exception 
	 */
	public static Timestamp getSystemDateTime(long lOfficeID, long lCurrencyID) throws Exception
	{
		//数据库时间
		Timestamp tsSystemDateTime = Env.getSystemDateTime();
		
		Timestamp tsDate = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null; //查询串
		Connection con = null;
        
		try
		{
	       /*-----------------init DAO --------------------*/
	       try {
	    	   con = Database.getConnection();
	       }
	       catch (ITreasuryDAOException e) {
	          throw new ITreasuryDAOException("创建连接时异常",e);
	       }
			
			strSQL =
				"select to_char(dtOpenDate,'yyyy') as syear,to_char(dtOpenDate,'mm') as smonth, to_char(dtOpenDate,'dd') as sDay from sett_officetime where ncurrencyid="
					+ lCurrencyID
					+ " and nOfficeID="
					+ lOfficeID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int nYear = Integer.parseInt(rs.getString("sYear"));
				int nMonth = Integer.parseInt(rs.getString("sMonth"));
				int nDay = Integer.parseInt(rs.getString("sDay"));
				int nHour = tsSystemDateTime.getHours();
				int nMinute = tsSystemDateTime.getMinutes();
				int nSecond = tsSystemDateTime.getSeconds();
				
				tsDate = DataFormat.getDateTime(nYear, nMonth, nDay, nHour, nMinute, nSecond);
				
				//精度问题
				tsDate = DataFormat.getDateTime(DataFormat.getDateTimeString(tsDate));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
		       // TODO Auto-generated catch block
		       e.printStackTrace();
		       throw new ITreasuryDAOException("查询异常",e);
			}
		}
		return tsDate;
	}
	
	/**
	 * 获得总部办事处ID
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 */
	public static long getHQOFFICEID()
	{
		long officeid = -1;
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		Connection con = null;
		try
		{
			if(Env.HQOFFICEID>0)
			{//如果内存中有则直接取内存中的值
				return Env.HQOFFICEID;
			}
			else
			{//如果内存中没有则从数据库中查，并且放到内存中
				con = Database.getConnection();
				strSQL =
					"select id from office where nstatusid > 0 and orglevel = 1";
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					officeid = rs.getLong(1);
					if(officeid > 0)
					{
						Env.HQOFFICEID=officeid;
					}
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
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
				;
			}
		}
		return officeid;
	}
	
	/**
	 * SAP财企接口 ADD BY XIANGZHOU 2011-03-30
	 */
	
	public static String getSAP_CLIENT()
    {
        String EnvItem = getEnvItem("sap.sapclient");
        SAP_CLIENT = EnvItem == null ? SAP_CLIENT : EnvItem;
        return SAP_CLIENT;
    }

    public static String getSAP_USERID()
    {
        String EnvItem = getEnvItem("sap.userid");
        SAP_USERID = EnvItem == null ? SAP_USERID : EnvItem;
        return SAP_USERID;
    }

    public static String getSAP_PASSWORD()
    {
        String EnvItem = getEnvItem("sap.password");
        SAP_PASSWORD = EnvItem == null ? SAP_PASSWORD : EnvItem;
        return SAP_PASSWORD;
    }

    public static String getSAP_LANGUAGE()
    {
        String EnvItem = getEnvItem("sap.language");
        SAP_LANGUAGE = EnvItem == null ? SAP_LANGUAGE : EnvItem;
        return SAP_LANGUAGE;
    }

    public static String getSAP_HOSTNAME()
    {
        String EnvItem = getEnvItem("sap.hostname");
        SAP_HOSTNAME = EnvItem == null ? SAP_HOSTNAME : EnvItem;
        return SAP_HOSTNAME;
    }

    public static String getSAP_SYSTEMNUMBER()
    {
        String EnvItem = getEnvItem("sap.systemnumber");
        SAP_SYSTEMNUMBER = EnvItem == null ? SAP_SYSTEMNUMBER : EnvItem;
        return SAP_SYSTEMNUMBER;
    }
	
    public static String getSAP_SYSTEMCODE()
    {
    	String EnvItem = getEnvItem("sap.systemcode");
    	SAP_SYSTEMCODE = EnvItem == null ? SAP_SYSTEMCODE : EnvItem;
    	return SAP_SYSTEMCODE;
    }

    public static String getSAP_PROGRAMID()
    {
    	String EnvItem = getEnvItem("sap.programid");
    	SAP_PROGRAMID = EnvItem == null ? SAP_PROGRAMID : EnvItem;
    	return SAP_PROGRAMID;
    }

    public static String getSAP_UNICODE()
    {
    	String EnvItem = getEnvItem("sap.unicode");
    	SAP_UNICODE = EnvItem == null ? SAP_UNICODE : EnvItem;
    	return SAP_UNICODE;
    }
    
    /**
     * SAP600机
     * @return
     */
    public static String getSAP_CLIENT_1()
    {
        String EnvItem = getEnvItem("sap.sapclient_1");
        SAP_CLIENT_1 = EnvItem == null ? SAP_CLIENT_1 : EnvItem;
        return SAP_CLIENT_1;
    }

    public static String getSAP_USERID_1()
    {
        String EnvItem = getEnvItem("sap.userid_1");
        SAP_USERID_1 = EnvItem == null ? SAP_USERID_1 : EnvItem;
        return SAP_USERID_1;
    }

    public static String getSAP_PASSWORD_1()
    {
        String EnvItem = getEnvItem("sap.password_1");
        SAP_PASSWORD_1 = EnvItem == null ? SAP_PASSWORD_1 : EnvItem;
        return SAP_PASSWORD_1;
    }

    public static String getSAP_LANGUAGE_1()
    {
        String EnvItem = getEnvItem("sap.language_1");
        SAP_LANGUAGE_1 = EnvItem == null ? SAP_LANGUAGE_1 : EnvItem;
        return SAP_LANGUAGE_1;
    }

    public static String getSAP_HOSTNAME_1()
    {
        String EnvItem = getEnvItem("sap.hostname_1");
        SAP_HOSTNAME_1 = EnvItem == null ? SAP_HOSTNAME_1 : EnvItem;
        return SAP_HOSTNAME_1;
    }

    public static String getSAP_SYSTEMNUMBER_1()
    {
        String EnvItem = getEnvItem("sap.systemnumber_1");
        SAP_SYSTEMNUMBER_1 = EnvItem == null ? SAP_SYSTEMNUMBER_1 : EnvItem;
        return SAP_SYSTEMNUMBER_1;
    }
	
    public static String getSAP_SYSTEMCODE_1()
    {
    	String EnvItem = getEnvItem("sap.systemcode_1");
    	SAP_SYSTEMCODE_1 = EnvItem == null ? SAP_SYSTEMCODE_1 : EnvItem;
    	return SAP_SYSTEMCODE_1;
    }

    public static String getSAP_PROGRAMID_1()
    {
    	String EnvItem = getEnvItem("sap.programid_1");
    	SAP_PROGRAMID_1 = EnvItem == null ? SAP_PROGRAMID_1 : EnvItem;
    	return SAP_PROGRAMID_1;
    }

    public static String getSAP_UNICODE_1()
    {
    	String EnvItem = getEnvItem("sap.unicode_1");
    	SAP_UNICODE_1 = EnvItem == null ? SAP_UNICODE_1 : EnvItem;
    	return SAP_UNICODE_1;
    }
	
}