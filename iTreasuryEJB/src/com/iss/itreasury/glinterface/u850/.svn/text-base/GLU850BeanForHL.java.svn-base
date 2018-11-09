/*
 * Created on 2004-2-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.u850;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpRecoverableException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.w3c.dom.Node;

import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.u850.GLU850Bean.ResponseDataPackage;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.jspsmart.upload.SmartUpload;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLU850BeanForHL extends GLExtendBaseBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	SmartUpload mySmartUpLoad = new SmartUpload();
	public static void main(String[] args)
	{
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "trace");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "trace");
		try
		{
			new GLU850Bean().getGLSubject(1, 1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * @author yychen
	GLSystemBaseBean.postGLVoucher()：导出会计凭证
	参数：Collection collGlVoucher: FindGLVoucherBaseBean. findGLVoucherByCondition ()返回的凭证集合；
	返回值：boolean bIsSuccess:是否成功；
	功能描述：将业务系统的凭证信息生成XML文件，然后传出至EAI。
	流程描述：
	l	调用U850GLBean.buildGlVoucherXML(),将凭证信息集合转化成XML文件；
	l	调用U850GLBean.triggerPostGlVoucher()，将XML文件传至EAI系统；
	l	返回是否成功! 
	*/
public String postGLVoucher(HttpServletResponse response,Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp startdate,Timestamp enddate) throws Exception
{
	ArrayList result = null;
	Hashtable hash = new Hashtable();///保存交易号与凭证号之间的关系
	Hashtable hashentrylist = new Hashtable();///保存VoucherInfo与凭证号之间的关系
	String strRequestData = null;
	try
	{
		//Session session = GLU850Bean.createSession(Env.GLRESPONSE_HTTP);
		
		Session session = null;
		session = GLU850BeanForHL.createSession(GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1).getGlResponseHTTP());
		
		XMLDataMarshalUtil utilTemp = new XMLDataMarshalUtil();
		//请求XML数据对象
		XMLDataMarshalUtil.VoucherListInfo requestInfo = utilTemp.new VoucherListInfo();
		//结果XML数据对象		
		XMLDataMarshalUtil.ResponseXMLInfo responseInfo = utilTemp.new ResponseXMLInfo();
		//requestInfo.rootInfo.sender = Env.GL_SENDER;
		
		requestInfo.rootInfo.sender = GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1).getGlSender();
	
		requestInfo.rootInfo.receiver = "u8";
		requestInfo.rootInfo.roottag = "voucher";
		requestInfo.rootInfo.docid = ".533424";
		requestInfo.rootInfo.proc = "Add";
		requestInfo.rootInfo.codeexchanged = "n";
		requestInfo.rootInfo.exportneedexch = "n";
		requestInfo.rootInfo.display = "凭证";
		requestInfo.rootInfo.family = "凭证";
		GLVoucherInfo voucherTemp = null;
		Iterator itTemp = collGlVoucher.iterator();
		System.out.println("collGlVoucher.size()" + collGlVoucher.size());
		///为了不断号，先得到所需凭证的值；
		long[] lVoucherNO = null;
		int iVoucherArrayLength = collGlVoucher.size(); ////凭证数组长度，即凭证的多少
		int iVoucherCounter = 0;
		
		while (itTemp.hasNext())
		{
			voucherTemp = (GLVoucherInfo) itTemp.next();
			System.out.println(voucherTemp.getTransNo() + "voucherTemp.getPostDate()11:" + voucherTemp.getPostDate());
			////如果没有取得最新的凭证号在，则取数据；
			if (lVoucherNO == null || lVoucherNO.length <= 0)
			{
				System.out.println("********开始从U850系统中取得所需的凭证号");
					
				lVoucherNO = getVoucherNo(voucherTemp, iVoucherArrayLength,lOfficeID,lCurrencyID);
				
				System.out.println("********结束从U850系统中取得所需的凭证号");
			}
			if (lVoucherNO != null && lVoucherNO.length > iVoucherCounter)
			{
				long lVoucherID = lVoucherNO[iVoucherCounter++];
				voucherTemp.setVoucherNo(DataFormat.formatInt(lVoucherID, 5));
				requestInfo.addVoucher(voucherTemp,lOfficeID,lCurrencyID);
				hash.put(DataFormat.formatInt(lVoucherID, 5), voucherTemp.getTransNo());
				hashentrylist.put(DataFormat.formatInt(lVoucherID, 5), voucherTemp);
				System.out.println(lVoucherID + " post Voucher NO:" + voucherTemp.getVoucherNo());
				System.out.println("post Trans NO:" + voucherTemp.getTransNo());
			}
			else
			{
				throw new Exception("********没有从U850系统中取得所需的凭证号");
			}
		}

		//获取当前处理凭证的交易号，必须保证都是同一交易日、同一币种、办事处的凭证
		strRequestData = GLU850BeanForHL.transportBySession(response,session, requestInfo, responseInfo,startdate,enddate);
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new Exception(e.getMessage());
	}
	return strRequestData;
}

 

/**
 * 得到数据库GLSetting中针对所有接口的配置信息(做用于判断总账接口的类型用)
 * 
 * lOfficeID :办事处
 * lCurrencyID :币种
 * lType:	操作类型 0:导入 1:导出
 * 
 * @return Connection
 */
public static GlSettingInfo getGlSettingInfo(long lOfficeID,long lCurrencyID) throws Exception
{
	GlSettingInfo info = new GlSettingInfo();
	
	Connection con = null;
	PreparedStatement ps = null;		
	ResultSet rs = null;	
	try
	{		
		//	
		con=Database.getConnection();		
		String strSQL = "select * from SETT_GLSETTING where OfficeID=? and CurrencyID=? and nStatusID !=0 ";
		
		System.out.println("**********************"+strSQL);
		ps = con.prepareStatement(strSQL);
		ps.setLong(1,lOfficeID);	
		ps.setLong(2,lCurrencyID);			
		rs=ps.executeQuery();
		if(rs.next())		//如果有查询的办事处和币种相同的记录则取第一条即可
		{
			info.setId(rs.getLong("id"));
			info.setOfficeID(rs.getLong("OfficeID"));
			info.setCurrencyID(rs.getLong("CurrencyID"));
			info.setGlDBDatabaseName(rs.getString("GLDBDATABASENAME"));
			info.setGlDBIP(rs.getString("GLDBIP"));			
			info.setGlDBPort(rs.getString("GLDBPORT"));
			info.setGlDBUserName(rs.getString("GLDBUSERNAME"));
			info.setGlDBPassWord(rs.getString("GLDBPASSWORD"));
			
			info.setGlName(rs.getString("GLNAME"));
			info.setGlUserName(rs.getString("GLUSERNAME"));
			info.setGlPassWord(rs.getString("GLPASSWORD"));
			info.setGlSender(rs.getString("GLSENDER"));
			info.setGlVoucherType(rs.getString("GLVOUCHERTYPE"));
			info.setGlResponseHTTP(rs.getString("GLRESPONSEHTTP"));			
			info.setNStatusID(rs.getLong("nStatusID"));
			
			info.setNImportType(rs.getLong("nImportType"));
			
			//总账的操作方式（导入导出组合方式)
			info.setGlOperationType(rs.getLong("glOperationType"));
			
			//浪潮制单人		此时可以不要GLGernerSoftBean处理Bean中没有调用此方法,把以针对浪潮的此值可以不要
			//info.setGLZDRForGener(rs.getString("glZDRForGener"));
		}			
		if(rs!=null)
		{
		  rs.close();
		  rs=null;
		}
		if(ps!=null)
		{
			ps.close();
		  	ps=null;
		}
		if(con!=null)
		{
			con.close();
			con=null;
		}
		
	}		
	catch(Exception e)
	{
		throw e;
	}
	finally
	{
		if(rs!=null)
		{
		  rs.close();
		  rs=null;
		}
		if(ps!=null)
		{
			ps.close();
		  	ps=null;
		}
		if(con!=null)
		{
			con.close();
			con=null;
		}
	}	
	return info;
}

/**
 * 得到数据库GLSetting中针对所有接口的配置信息(做实际的导入导出处理时,取具体的连接信息时用)
 * 
 * lOfficeID :办事处
 * lCurrencyID :币种
 * lType:	操作类型 0:导入 1:导出
 * 
 * @return Connection
 */
public static GlSettingInfo getGlSettingInfo(long lOfficeID,long lCurrencyID,long lType) throws Exception
{
	GlSettingInfo info = new GlSettingInfo();
	
	Connection con = null;
	PreparedStatement ps = null;		
	ResultSet rs = null;	
	try
	{		
		//	
		con=Database.getConnection();		
		String strSQL = "select * from SETT_GLSETTING where OfficeID=? and CurrencyID=? and nStatusID !=0 ";
//		if(lType==0){		//导入
//			strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
//		    strSQL +="," + Constant.GLOperationType.ImportOnly + ")";
//		}else if(lType==1){		//导出
//			strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
//		    strSQL +="," + Constant.GLOperationType.ExportOnly + ")";
//		}
		  
		
		System.out.println("**********************"+strSQL);
		ps = con.prepareStatement(strSQL);
		ps.setLong(1,lOfficeID);	
		ps.setLong(2,lCurrencyID);			
		rs=ps.executeQuery();
		if(rs.next())		//如果有查询的办事处和币种相同的记录则取第一条即可
		{
			info.setId(rs.getLong("id"));
			info.setOfficeID(rs.getLong("OfficeID"));
			info.setCurrencyID(rs.getLong("CurrencyID"));
			info.setGlDBDatabaseName(rs.getString("GLDBDATABASENAME"));
			info.setGlDBIP(rs.getString("GLDBIP"));			
			info.setGlDBPort(rs.getString("GLDBPORT"));
			info.setGlDBUserName(rs.getString("GLDBUSERNAME"));
			info.setGlDBPassWord(rs.getString("GLDBPASSWORD"));
			
			info.setGlName(rs.getString("GLNAME"));
			info.setGlUserName(rs.getString("GLUSERNAME"));
			info.setGlPassWord(rs.getString("GLPASSWORD"));
			info.setGlSender(rs.getString("GLSENDER"));
			info.setGlVoucherType(rs.getString("GLVOUCHERTYPE"));
			info.setGlResponseHTTP(rs.getString("GLRESPONSEHTTP"));			
			info.setNStatusID(rs.getLong("nStatusID"));
			
			info.setNImportType(rs.getLong("nImportType"));
			System.out.println("*************************账套编号*********************"+rs.getString("BRANCHCODE"));
			
			info.setBranChcode(rs.getString("BRANCHCODE"));
			System.out.println("***********************************账套编号设置完成**********************************");
			
			//总账的操作方式（导入导出组合方式)
			info.setGlOperationType(rs.getLong("glOperationType"));
			
			//浪潮制单人
			info.setGLZDRForGener(rs.getString("glZDRForGener"));
		}			
		if(rs!=null)
		{
		  rs.close();
		  rs=null;
		}
		if(ps!=null)
		{
			ps.close();
		  	ps=null;
		}
		if(con!=null)
		{
			con.close();
			con=null;
		}
		
	}		
	catch(Exception e)
	{
		throw e;
	}
	finally
	{
		if(rs!=null)
		{
		  rs.close();
		  rs=null;
		}
		if(ps!=null)
		{
			ps.close();
		  	ps=null;
		}
		if(con!=null)
		{
			con.close();
			con=null;
		}
	}	
	return info;
}


	public static boolean isAccounting(long lOfficeID, long lCurrencyID) throws Exception
	{
    	boolean nFalse = false;
    	//String strMaxPzNo = "";
    	Connection conn = null;        
    	PreparedStatement ps = null;
		ResultSet rs = null;      
        long bflag = -1;
        
       
        try
        {
            //获得接口设置信息
        	GlSettingInfo info = getGlSettingInfo(lOfficeID,lCurrencyID,1);  
        	//
        	conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
        	StringBuffer sbSQL = new StringBuffer();
  			//得到帐套信息   
        	//Modify by chuanliu for u870
  			sbSQL.setLength(0);
  			sbSQL.append(" select mend.bflag bflag from ufsystem.dbo.ua_period as period left join");
  			sbSQL.append(" gl_mend mend on (period.iid=mend.iperiod) ");
  			//sbSQL.append(" FROM GL_accvouch ");
  			sbSQL.append(" where cAcc_id='" + info.getBranChcode() + "'");
  			sbSQL.append(" and iYear='" + Env.getSystemDateString(lOfficeID,lCurrencyID).substring(0,4) + "'");
  			sbSQL.append(" and dbegin<='" + Env.getSystemDate(lOfficeID,lCurrencyID) + "'");
  			sbSQL.append(" and dend>='" + Env.getSystemDate(lOfficeID,lCurrencyID) + "'");
  			Log.print(sbSQL.toString());
  			ps = conn.prepareStatement(sbSQL.toString());
  			rs = ps.executeQuery();
  			if (rs != null && rs.next())
  			{
  				/////从数据库中取ID
  				bflag = rs.getLong("bflag");
  				if (bflag == 1)
  				{
  					nFalse = true;
  				}
  			}
  			rs.close();
  			rs = null;
  			ps.close();
  			ps = null;
  		
  			conn.close();
  			conn = null;
  		}
  		catch (Exception e)
  		{
  			e.printStackTrace();
  		}
  		return nFalse;
  	
  }
	
	/**
	 * 得到外币与用友账套本位币的汇率
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static double getExchangeRate (long lOfficeID, long lCurrencyID,Timestamp dtDate) throws Exception
	{

		Connection conn = null;
		double dRtn = 1.00;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			if(getNaturalCurrency(lOfficeID,lCurrencyID).equals(Constant.CurrencyType.getName(lCurrencyID)))
			{
				return 1;
			}
			else
			{			
				/////
				conn = Database.getConnection();
				StringBuffer sbSQL = new StringBuffer();			
				sbSQL.append("select * from sett_exchangeRateSetting");			
				sbSQL.append("where ");
				sbSQL.append("nOfficeID = "+ lOfficeID + " and nCurrencyID = " + lCurrencyID + " and dtDate= to_date('"+ DataFormat.getDateString(dtDate) +"','yyyy-mm-dd') " );
				
				Log.print(sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{				
					dRtn = rs.getDouble("mRate");			
					
				}			
			}
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
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
				Log.print(e.toString());
			}
		}
		return dRtn;
	
	}
	/**
	 * 得到账套本位币
	 * @return
	 * @throws Exception
	 */	
	public static String getNaturalCurrency (long lOfficeID, long lCurrencyID) throws Exception
	{

		Connection conn = null;
		String strRtn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			/////						值得考虑一下  2005/9/14
			conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);
			StringBuffer sbSQL = new StringBuffer();			
			sbSQL.append("select * from foreigncurrency");			
			sbSQL.append("where ");
			sbSQL.append("iotherused = -1 ");
			
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{				
				strRtn = rs.getString("cexch_name");			
				
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
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
				Log.print(e.toString());
			}
		}
		return strRtn;
	
	}
	/**
	 * 得到本月发生额
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param tsDate
	 * @return
	 * @throws Exception
	 */
	public ArrayList getGLSubjectBanlanceCurrent(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{

		Connection conn = null;
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			
			conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
			
			
			StringBuffer sbSQL = new StringBuffer();			
			sbSQL.append("select g.ccode,c.cclass,g.cexch_name,sum(g.md) md,sum(g.mc) mc,sum(g.md_f) wbmd,sum(g.mc_f) wbmc ");
			sbSQL.append("from gl_accvouch g,code c ");
			sbSQL.append("where ");
			sbSQL.append("g.ccode=c.ccode ");
			sbSQL.append("and g.iperiod = " + (tsDate.getMonth() + 1));
			sbSQL.append(" and convert(char(10),g.dbill_date,101)<= '" + DataFormat.getDateString1(tsDate) + "' ");
			
			
			sbSQL.append(" group by g.ccode,c.cclass,g.cexch_name order by g.ccode ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				GLBalanceInfo info = new GLBalanceInfo();
				info.setGLDate(tsDate);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				info.setGLSubjectCode(rs.getString("ccode"));				
				info.setDebitAmount(rs.getDouble("md"));
				info.setCreditAmount(rs.getDouble("mc"));
				
				list.add(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
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
				Log.print(e.toString());
			}
		}
		return (list != null && list.size() > 0 ? list : null);
	
	}
	/**
	 * 当月发生+当月余额=科目余额
	 */
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		
		

		Connection conn = null;
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			/////更新科目号
			conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
			StringBuffer sbSQL = new StringBuffer();
			//sbSQL.append(" select * from GL_accsum where iperiod =    " + (tsDate.getMonth() + 1));
			/*sbSQL.append("select g.iperiod,g.ccode ccode,g.cexch_name,g.cbegind_c_engl cbegind_c_engl,sum(g.mb) ye,sum(g.mb_f) wbye ");
			sbSQL.append("from gl_accsum g,code c ");
			sbSQL.append("where ");
			sbSQL.append("c.ccode = + g.ccode ");
			sbSQL.append("and g.iperiod = " + (tsDate.getMonth() + 1));
			//原来是为了取得人民币账套的美元科目余额，现在不用了
			/*if(lCurrencyID!=Constant.CurrencyType.RMB && Env.getProjectName().equals(Constant.ProjectName.HAIER))
			{
				sbSQL.append(" and g.cexch_name = "+ Constant.CurrencyType.getName(lCurrencyID));
			}*/
			//sbSQL.append(" group by g.iperiod,g.ccode,g.cexch_name,g.cbegind_c_engl order by g.iperiod,g.ccode");
			
			sbSQL.append("select g.iperiod,c.ccode ccode,g.cexch_name,c.bproperty direction, sum(g.mb) ye,sum(g.mb_f) wbye ");
			sbSQL.append("from gl_accsum g,code c ");
			sbSQL.append("where ");
			sbSQL.append("c.ccode *=  g.ccode ");
			sbSQL.append("and g.iperiod = " + (tsDate.getMonth() + 1));			
			sbSQL.append(" group by g.iperiod,c.ccode,g.cexch_name,c.bproperty order by c.ccode,g.iperiod");
			System.out.println(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();			
			ArrayList col = null;
			col = this.getGLSubjectBanlanceCurrent(lOfficeID,lCurrencyID,tsDate);
			while (rs != null && rs.next())
			{				
				
				GLBalanceInfo info = new GLBalanceInfo();
				info.setGLDate(tsDate);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				info.setGLSubjectCode(rs.getString("ccode"));
				System.out.println("********lxr显示科目*******"+info.getGLSubjectCode());
				if (rs.getByte("direction")==0)
					{
					info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);
					info.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
					info.setDebitBalance(0);
					info.setCreditBalance(-1*rs.getDouble("ye"));
				}
				else
				{
					info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);
					info.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
					info.setDebitBalance(rs.getDouble("ye") );
					info.setCreditBalance(0);
				}
								
			
				if(col!=null && col.size()!=0)
				{					
					for(int i = 0 ;i<col.size();i++)
					{
						GLBalanceInfo tmp = new GLBalanceInfo();
						tmp = (GLBalanceInfo)col.get(i);
						
						
						if(tmp.getGLSubjectCode().equals(info.getGLSubjectCode()))
						{
						
							System.out.println("********科目*******"+info.getGLSubjectCode());
							
							if (rs.getByte("direction")==1)
							{
									System.out.println("********发生前科目借方余额*******"+info.getDebitBalance());
									info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);
									info.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									info.setDebitBalance(info.getDebitBalance() + tmp.getDebitAmount()-tmp.getCreditAmount());
									info.setCreditBalance(0);
									System.out.println("********发生后科目借方余额*******"+info.getDebitBalance());
									System.out.println("********借方发生*******"+tmp.getDebitAmount());
									System.out.println("********贷方发生*******"+tmp.getCreditAmount());
							}
							else
							{
									System.out.println("********发生前贷方余额*******"+info.getCreditBalance());
									info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);
									info.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									info.setDebitBalance(0);
									info.setCreditBalance(info.getCreditBalance()+tmp.getDebitAmount()-tmp.getCreditAmount());
									System.out.println("********发生后科目借方余额*******"+info.getCreditBalance());
									System.out.println("********借方发生*******"+tmp.getDebitAmount());
									System.out.println("********贷方发生*******"+tmp.getCreditAmount());
							}
							
						}
						
						if(tmp.getGLSubjectCode().startsWith(info.getGLSubjectCode()) && !tmp.getGLSubjectCode().equals(info.getGLSubjectCode()) )    //增加上级科目余额
						{
							
							System.out.println("********上级科目*******"+info.getGLSubjectCode());
							System.out.println("********下级科目*******"+tmp.getGLSubjectCode());
							if (rs.getByte("direction")==1)
							{
								System.out.println("********计算前上级科目借方余额*******"+info.getDebitBalance());
									info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);
									info.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
									info.setDebitBalance(info.getDebitBalance() + tmp.getDebitAmount()-tmp.getCreditAmount());
									info.setCreditBalance(0);
								System.out.println("********计算后上级科目借方余额*******"+info.getDebitBalance());
								System.out.println("********下级科目借方发生*******"+tmp.getDebitAmount());
								System.out.println("********下级科目贷方发生*******"+tmp.getCreditAmount());
							}
							else
							{
								System.out.println("********计算前上级科目贷方余额*******"+info.getCreditBalance());
									info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);
									info.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
									info.setDebitBalance(0);
									info.setCreditBalance(info.getCreditBalance()+tmp.getDebitAmount()-tmp.getCreditAmount());
									System.out.println("********计算后上级科目贷方余额*******"+info.getCreditBalance());
									System.out.println("********下级科目借方发生*******"+tmp.getDebitAmount());
									System.out.println("********下级科目贷方发生*******"+tmp.getCreditAmount());
							}
						}
						
					}
				}
				list.add(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;				
			
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
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
				Log.print(e.toString());
			}
		}
		return (list != null && list.size() > 0 ? list : null);
	
	}
	public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID, long lMouduleID, Timestamp tsDate) throws Exception
	{	    
		Connection conn = null;
		ArrayList list = new ArrayList();
		ArrayList returnlist = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			
			conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,0);
			
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("select g.dbill_date,g.ccode ccode,c.cclass,g.ibook,g.cexch_name,sum(g.md) md,sum(g.mc) mc,sum(g.md_f) wbmd,sum(g.mc_f) wbmc ");
			sbSQL.append("from gl_accvouch g,code c ");
			sbSQL.append("where ");
			sbSQL.append("g.ccode=c.ccode ");
			sbSQL.append(" and convert(char(10),g.dbill_date,101) = '" + DataFormat.getDateString1(tsDate) + "'  ");
		
			sbSQL.append(" group by g.dbill_date,g.ccode,c.cclass,g.ibook,g.cexch_name order by g.dbill_date,g.ccode");
			System.out.println(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();						
			while (rs != null && rs.next())
			{				
				
				GLBalanceInfo info = new GLBalanceInfo();
				info.setGLDate(tsDate);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				info.setGLSubjectCode(rs.getString("ccode"));
				info.setDebitAmount(rs.getDouble("md"));
				info.setCreditAmount(rs.getDouble("mc"));			
				
				list.add(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sbSQL.setLength(0);
			sbSQL.append("select * ");
			sbSQL.append("from code  ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();						
			while (rs != null && rs.next())
			{				
				
				GLBalanceInfo info = new GLBalanceInfo();
				info.setGLDate(tsDate);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				info.setGLSubjectCode(rs.getString("ccode"));
				for(int i=0;i<list.size();i++)
				{
				    GLBalanceInfo tempinfo = (GLBalanceInfo)list.get(i);
				    if(tempinfo.getGLSubjectCode()!=null && info.getGLSubjectCode()!=null && tempinfo.getGLSubjectCode().startsWith(info.getGLSubjectCode()))
				    {
				        info.setDebitAmount(info.getDebitAmount()+tempinfo.getDebitAmount());
				        info.setCreditAmount(info.getCreditAmount()+tempinfo.getCreditAmount()); 
				    }
				}
				returnlist.add(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
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
				Log.print(e.toString());
			}
		}
		return (returnlist != null && returnlist.size() > 0 ? returnlist : null);
	}
	/*
	 * U850GLBean.triggerPostGlVoucher ():
	参数：XML文件对象；
	返回值：boolean bIsSuccess:是否成功；
	功能描述：将XML文件对象传送至EAI；
	流程描述：
	l	将XML文件对象传送至EAI； 	
	 * */
	private static long triggerPostGLVoucher() throws Exception
	{
		return -1;
	}
	/*
	 U850GLBean. buildGLVoucherXML ():
	 参数：Collection collGlVoucher: 凭证集合（U850GLBean.postGlVoucher（）参数）；
	返回值：XML文件对象；
	功能描述：将业务系统的凭证信息集合转化成XML文件；
	流程描述：
	l	将业务系统的凭证信息集合转化成XML文件；	
	 *  */
	private static long buildGLVoucherXML(Collection collGlVoucher) throws Exception
	{
		return -1;
	}
	/*
	 U850GLBean. resolveGLVoucherXML ():
	参数：；
	返回值：解析 XML文件所得的信息集合；
	功能描述：解析 XML文件；
	流程描述：
	解析 XML文件；	
	 * */
	private static Collection resolveGLVoucherXML(Collection collGlVoucher) throws Exception
	{
		return null;
	}
	/*
		 * U850GLBean.triggerPostGlVoucher ():
		参数：XML文件对象；
		返回值：boolean bIsSuccess:是否成功；
		功能描述：将XML文件对象传送至EAI；
		流程描述：
		l	将XML文件对象传送至EAI； 	
		 * */
	private static long triggerQueryGLSubject() throws Exception
	{
		return -1;
	}
	/*
		 U850GLBean. resolveGLSubjectXML ():
		参数：；
		返回值：解析 XML文件所得的信息集合；
		功能描述：解析 XML文件；
		流程描述：
		解析 XML文件；	
		 * */
	private static Collection resolveGLSubjectXML() throws Exception
	{
		return null;
	}
	private static Session createSession(String url)
	{
		GLU850BeanForHL temp = new GLU850BeanForHL();
		return temp.new Session(url);
	}
	private static String transportBySession(HttpServletResponse response,Session session, XMLDataMarshalUtil.XMLInfo requestInfo, XMLDataMarshalUtil.XMLInfo responseInfo,Timestamp startdate,Timestamp enddate) throws Exception
	{
		GLU850BeanForHL temp = new GLU850BeanForHL();
		XMLDataMarshalUtil util = new XMLDataMarshalUtil();
		//准备请求Package数据
		RequestDataPackage requestData = temp.new RequestDataPackage();
		//响应数据对象
		ResponseDataPackage responseData = null;
		//设置请求XML数据
		String strRequestData = null;
		try
		{
			Node xmlDom = util.unmarshal(requestInfo);
			requestData.setDataSegment(xmlDom);
			strRequestData = session.transport(response,requestData,startdate,enddate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Occured in generate request xml data.");
		}
		return strRequestData;
	}
	

	/**
	 * 得到不同账套的凭证号
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static long[] getVoucherNo(GLVoucherInfo info, int iLength,long lOfficeID,long lCurrencyID) throws Exception
	{
		long[] lVoucherNO = new long[iLength];
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iVoucherCouter = 0;
		long lVoucherID = 0;
		long lMinVoucherID = 0;
		Hashtable hash = new Hashtable();
		String glVoucherType ="";     //凭证类型
		try
		{			
            //取系统管理总账接口设置的凭证类型
			glVoucherType  =  GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,1).getGlVoucherType();
			Connection conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);			
			StringBuffer sbSQL = new StringBuffer();
			//得到最小的凭证号
			sbSQL.setLength(0);
			sbSQL.append(" SELECT MIN(VoucherId) as VoucherId");
			sbSQL.append(" FROM (SELECT (ino_id + 1) as VoucherId ");
			sbSQL.append(" FROM GL_accvouch ");
			sbSQL.append(" WHERE (ino_id + 1) NOT IN ");
			sbSQL.append("      (");
			sbSQL.append("  		SELECT ino_id ");
			sbSQL.append("     		 FROM GL_accvouch ");
			sbSQL.append("           and csign = '"+glVoucherType+"'");
			sbSQL.append(" 		 )");
			sbSQL.append("   AND iperiod = " + (info.getPostDate().getMonth() + 1)+"  ) DERIVEDTBL");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				/////从数据库中取ID
				lMinVoucherID = rs.getLong("VoucherID");
				if (lMinVoucherID <= 0)
				{
					lMinVoucherID = 1;
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//得到大于最小凭证号的所有凭证
			sbSQL.setLength(0);
			sbSQL.append("  SELECT ino_id ");
			sbSQL.append("  FROM GL_accvouch ");
			sbSQL.append("  WHERE iperiod =  " + (info.getPostDate().getMonth() + 1));
			sbSQL.append("  AND ino_id > " + lMinVoucherID);
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				hash.put(rs.getString("ino_id"), "1");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			////得到所需的凭证号
			lVoucherID = lMinVoucherID;
			while (iVoucherCouter < iLength)
			{
				if (hash.get(String.valueOf(lVoucherID)) == null)
				{
					lVoucherNO[iVoucherCouter++] = lVoucherID;
					Log.print("***************" + lVoucherID);
				}
				lVoucherID++;
			}
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lVoucherNO;
	}
	class Session
	{
		public final static int HTTP_POST_METHOD = 1;
		public final static int HTTP_GET_METHOD = 2;
		public final static String encoding = "gb2312";
		private HttpClient m_client = new HttpClient();
		private String strURL = null;
		/**
		 * Constructor for Session.
		 */
		private Session(String url)
		{
			super();
			strURL = url;
		}
		public ResponseDataPackage transport(RequestDataPackage requestData) {
			// TODO Auto-generated method stub
			return null;
		}
		public String transport(HttpServletResponse response,RequestDataPackage requestData,Timestamp startdate,Timestamp enddate) throws Exception
		{
			InputStream in =null;
			OutputStream output = null;
			String strRequestData = null;
			PostMethod post = new PostMethod(this.strURL);
			System.out.println("try to connect to the URL[" + this.strURL + "]");
			try
			{
				//设置请求参数	
				strRequestData = XMLHelper.getXMLString(requestData.getDataSegment(), Session.encoding);
				//recordRequestInfo("123",startdate,enddate);
				String fileName = DataFormat.getTransDateString(startdate)+"-"+DataFormat.getTransDateString(enddate)+".xml";
				//String fileName = "071123-071123.xml";
				//in = stringtoInputStream(strRequestData);
				output = response.getOutputStream();
				response.reset(); 
				response.setContentType("application/octet-stream"); 
				response.addHeader("Content-Disposition","attachment;filename=" + fileName);                
				output.write(strRequestData.getBytes());
//				//循环取出流中的数据 
//				byte[] b = new byte[100]; 
//				int len; 
//				while((len=in.read(b)) >0){ 
//				 output.write(b,0,len); 
//				}
//				in.close(); 
     		    System.out.println("-------------------------凭证导出结束－－－－－－－－－－－－－－－－－－－－－－－－");
			}
			catch(Exception e)
			{
				output.close();
				e.printStackTrace();
				log.debug("=====exception is "+e.toString());
			}
			finally
			{
				output.flush();
				output.close();
				output = null;
			}
			return strRequestData;
		}
		
		
		public InputStream stringtoInputStream(String str) throws UnsupportedEncodingException{
	        ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
	        return bais;
	    }

		/**
		 * Method checkHttpStatusCode.
		 * @param i
		 * @param string
		 */
		private void checkHttpStatusCode(int i, String string)
		{
		}
		/**
		 * 按照指定的方法执行链接请求
		 * 注：同一个HttpMethod不能链接两次
		 * @param method
		 * @throws Exception
		 */
		private void connect(HttpMethod method) throws Exception
		{
			if (method == null)
			{
				throw new IllegalArgumentException("null HttpMethod.");
			}
			// Execute the method.
			int statusCode = -1;
			int attempt = 0;
			// We will retry up to 3 times.
			while (statusCode == -1 && attempt < 3)
			{
				attempt++;
				try
				{
					log.info("Connect loop try " + attempt);
					// execute the method.
					statusCode = this.m_client.executeMethod(method);
				}
				catch (HttpRecoverableException e)
				{
					log.error("A recoverable exception occurred, retrying." + e.getMessage());
				}
				catch (IOException e)
				{
					log.error("Failed to connect.");
					throw e;
				}
			}
			// Check that we didn't run out of retries.
			if (statusCode == -1)
			{
				throw new Exception("Failed to connect.");
			}
		}
		private void releaseConnection(HttpMethod method) throws Exception
		{
			if (method == null)
			{
				throw new IllegalArgumentException("null HttpMethod.");
			}
			method.releaseConnection();
		}
		public void recordResponseInfo(String source) throws FileNotFoundException
		{
			try
			{
				byte[] responseBody = new byte[1024];
				FileWriter output;
				output = new FileWriter("C:\\Documents and Settings\\rongyang\\桌面\\distribute\\Xml\\Samples\\response_voucher.xml");
				output.write(source);
				output.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		private void recordRequestInfo(String source,Timestamp startdate,Timestamp enddate) throws FileNotFoundException
		{
			try
			{
				
//				byte[] responseBody = new byte[1024];
//				FileWriter output;
//    			String fileName = "d:\\"+DataFormat.getTransDateString(startdate)+"-"+DataFormat.getTransDateString(enddate)+".xml";
//				output = new FileWriter(fileName);
//				output.write(source);
//				output.close();
				String fileName = "d:\\071123-071123.xml";
    			//mySmartUpLoad.initialize(pageContext);
    			mySmartUpLoad.setContentDisposition(null);
				mySmartUpLoad.downloadFile(fileName);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	} //end Class Session
	class RequestDataPackage
	{
		private int m_MethodType = -1;
		private Node m_DataSegment = null;
		/**
		 * Returns the dataSegment.
		 * @return Node
		 */
		public Node getDataSegment()
		{
			return m_DataSegment;
		}
		/**
		 * Returns the methodType.
		 * @return int
		 */
		public int getMethodType()
		{
			return m_MethodType;
		}
		/**
		 * Sets the dataSegment.
		 * @param dataSegment The dataSegment to set
		 */
		public void setDataSegment(Node dataSegment)
		{
			m_DataSegment = dataSegment;
		}
		/**
		 * Sets the methodType.
		 * @param methodType The methodType to set
		 */
		public void setMethodType(int methodType)
		{
			m_MethodType = methodType;
		}
	}
	class ResponseDataPackage
	{
		private Node m_DataSegment = null;
		/**
		 * Returns the dataSegment.
		 * @return Node
		 */
		public Node getDataSegment()
		{
			return m_DataSegment;
		}
		/**
		 * Sets the dataSegment.
		 * @param dataSegment The dataSegment to set
		 */
		public void setDataSegment(Node dataSegment)
		{
			m_DataSegment = dataSegment;
		}
	}
}
