package com.iss.itreasury.glinterface.nc;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpRecoverableException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.closesystem.settlement.SettGLWithinDao;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherLogInfo;
import com.iss.itreasury.glinterface.u850.ConnectionSQLServer;
import com.iss.itreasury.glinterface.u850.XMLHelper;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.setting.bizlogic.SettUserDistributeBiz;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dao.Sett_GlSettingDAO;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;


public class NCBean extends GLExtendBaseBean 
{	
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	Hashtable hash = new Hashtable();///保存交易号与凭证号之间的关系
	Hashtable hashentrylist = new Hashtable();///保存VoucherInfo与凭证号之间的关系
	GLVoucherLogInfo logInfo = null;
	GlSettingInfo GLInfo = null;
	boolean bIsPassed = false;
	String sLog = null;
	
	private long message_Model = 1;//报文模式
	private long database_Model = 2;//中间表模式
	
	public static void main(String[] args)
	{
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "trace");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "trace");
		try
		{
			new NCBean().getGLSubject(1, 1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}

/**
 *  组装request信息
 * @param lOfficeID
 * @param lCurrencyID
 * @return
 */
public NCXMLDataMarshalUtil.VoucherListInfo getRequestInfo(GlSettingInfo glInfo){
	
	NCXMLDataMarshalUtil utilTemp = new NCXMLDataMarshalUtil();
	//请求XML数据对象
	NCXMLDataMarshalUtil.VoucherListInfo requestInfo = utilTemp.new VoucherListInfo();
	
	requestInfo.rootInfo.sender = glInfo.getGlSender();
	requestInfo.rootInfo.receiver = glInfo.getBranChcode()+"@"+glInfo.getReceiver();//Q8801
	requestInfo.rootInfo.roottag = "voucher";
	requestInfo.rootInfo.billtype = "gl";
	requestInfo.rootInfo.proc = "add";
	requestInfo.rootInfo.subtype = "run";
	requestInfo.rootInfo.replace = "Y";
	requestInfo.rootInfo.isexchange = "Y";
	requestInfo.rootInfo.filename = "pz.xml";
	requestInfo.rootInfo.operation = "req";
	requestInfo._company = glInfo.getBranChcode();
	requestInfo._voucher_type = glInfo.getGlVoucherType();
	
	return requestInfo;
}
/**
 * 得到Response信息
 * @param collGlVoucher
 * @param GLInfo
 * @return
 * @throws Exception
 * @throws IException
 */
public NCXMLDataMarshalUtil.ResponseXMLInfo getResponseInfo(Collection collGlVoucher,Timestamp date) throws Exception,IException{
	
	//接收XML数据对象
	NCXMLDataMarshalUtil.ResponseXMLInfo responseInfo = new NCXMLDataMarshalUtil().new ResponseXMLInfo();
	
	NCXMLDataMarshalUtil.VoucherListInfo requestInfo = this.getRequestInfo(GLInfo);
	
	Session session = NCBean.createSession(GLInfo.getGlResponseHTTP());
	
	GLVoucherInfo voucherTemp = null;
	Iterator itTemp = collGlVoucher.iterator();
		
	///为了不断号，先得到所需凭证的值；
	long[] lVoucherNO = null;
	int iVoucherCounter = 0;
	
	long _sendNum = 0;		
	long otherNum = 0;
	long sendNum = GLInfo.getSendnum();	//每次提交条数
	boolean control = true;
	
	if(sendNum < 1){
		control = false;
	}else{
		otherNum = collGlVoucher.size()%sendNum;
	}
	
	while (itTemp.hasNext())
	{
		voucherTemp = (GLVoucherInfo) itTemp.next();
		System.out.println(voucherTemp.getTransNo() + "voucherTemp.getPostDate()11:" + voucherTemp.getPostDate());
		////如果没有取得最新的凭证号在，则取数据；
		if (lVoucherNO == null || lVoucherNO.length <= 0)
		{
			System.out.println("********开始从NC系统中取得所需的凭证号");					
			System.out.println("********手工调试凭证个数为"+collGlVoucher.size());
			lVoucherNO = new long[collGlVoucher.size()];
		
			for(int ii=3;ii<=(collGlVoucher.size()+2);ii++){
				lVoucherNO[ii-3] = ii;
				System.out.println("********手工调试凭证个数为 end "+ii);
			}
			
			System.out.println("********结束从NC系统中取得所需的凭证号"+lVoucherNO[0]);
		}
		if (lVoucherNO != null && lVoucherNO.length > iVoucherCounter)
		{
			//20080827 凭证号取0，由用友生成凭证号
			long lVoucherID = 0;
			voucherTemp.setVoucherNo(DataFormat.formatInt(lVoucherID, 5));
			requestInfo.addVoucher(voucherTemp,GLInfo.getOfficeID(),GLInfo.getCurrencyID(),GLInfo);
			hash.put(DataFormat.formatInt(lVoucherID, 5), voucherTemp.getTransNo());
			hashentrylist.put(voucherTemp.getTransNo(), voucherTemp);
			System.out.println(lVoucherID + " post Voucher NO:" + voucherTemp.getVoucherNo());
			System.out.println("post Trans NO:" + voucherTemp.getTransNo());
			_sendNum = _sendNum + 1;
		}
		else
		{
			logInfo.setLog("没有从NC系统中取得所需的凭证号");
			logInfo.setNStatusID(Constant.YesOrNo.NO);
			log();
			throw new IException(logInfo.getLog());
		}
		
		if(control && _sendNum%sendNum == 0){
			
			try
			{
				SettGLWithinDao dao = new SettGLWithinDao();
				NCBean.transportBySession(session, requestInfo, responseInfo);
				if(GLInfo.getNImportType()==Constant.GLImportType.hz){
					if(this.returnGLVoucherHZ(responseInfo, date)){
						dao.updatePostStatusHZ(GLInfo,date);
					}
				}else{
					dao.updatePostStatus(this.returnGLVoucher(responseInfo, date));
				}
			}
			catch (Exception ie)
			{
				logInfo.setLog("NC系统连接失败:"+ie.getMessage());
				logInfo.setNStatusID(Constant.YesOrNo.NO);
				log();
				throw new IException("NC系统连接失败");
			}
			requestInfo = this.getRequestInfo(GLInfo);
			responseInfo = new NCXMLDataMarshalUtil().new ResponseXMLInfo();
			_sendNum = 0;
			
		}
		
	}
	
	try
	{
		if(!control){
			NCBean.transportBySession(session, requestInfo, responseInfo);
		}else if(control && _sendNum > 0 && _sendNum == otherNum ) {
			NCBean.transportBySession(session, requestInfo, responseInfo);
		}
		SettGLWithinDao dao = new SettGLWithinDao();
		if(GLInfo.getNImportType()==Constant.GLImportType.hz){
			if(this.returnGLVoucherHZ(responseInfo, date)){
				dao.updatePostStatusHZ(GLInfo,date);
			}
		}else{
			dao.updatePostStatus(this.returnGLVoucher(responseInfo, date));
		}
	}
	catch (Exception ie)
	{
		logInfo.setLog("NC系统连接失败:"+ie.getMessage());
		logInfo.setNStatusID(Constant.YesOrNo.NO);
		log();
		throw new IException("NC系统连接失败");
	}
	
	return responseInfo;
	
}
/**
 * 返回凭证处理信息
 * @param responseInfo
 * @return
 * @throws Exception 
 */
public ArrayList returnGLVoucher(NCXMLDataMarshalUtil.ResponseXMLInfo responseInfo,Timestamp date) throws Exception
{
	ArrayList result = new ArrayList();
	GLVoucherInfo infoTemp = null;
	GLVoucherInfo tempvoucher = null;
	String sTempVoucher = "";
	logInfo.setNStatusID(Constant.YesOrNo.YES);
	for (int i = 0; i < responseInfo.getResultSetSize(); i++)
	{
		infoTemp = new GLVoucherInfo();
		infoTemp = responseInfo.getResult(i);
		System.out.println("return Voucher :" + infoTemp.getVoucherNo());
	 	System.out.println("return message :" + infoTemp.getContent());
	 	System.out.println("return getPostDate :" + infoTemp.getPostDate());
	 	System.out.println("return getPostStatusID :" + infoTemp.getPostStatusID());
		//转换为结算系统的交易号
		sTempVoucher = infoTemp.getVoucherNo() != null && infoTemp.getVoucherNo().length() > 0 ? infoTemp.getVoucherNo() : "0";
		infoTemp.setTransNo(sTempVoucher);
		
		////将具体每一个凭证内的分录还原
		tempvoucher = (GLVoucherInfo)hashentrylist.get(infoTemp.getTransNo());

		if(tempvoucher==null){
			System.out.println("为空！");
		}else{
			System.out.println("不为空");
		}
		
		tempvoucher.setPostDate(date);
		
		if(tempvoucher!=null)
		{
			infoTemp.setList(tempvoucher.getList());
			infoTemp.setPostDate(tempvoucher.getPostDate());
		}
		result.add(infoTemp);
		System.out.println("return TransNo:" + infoTemp.getTransNo() + " post status:" + infoTemp.getPostStatusID());
		returnLog(infoTemp);
	}
	
	return result;
}

public boolean returnGLVoucherHZ(NCXMLDataMarshalUtil.ResponseXMLInfo responseInfo,Timestamp date) throws Exception
{
	boolean bool = true;
	GLVoucherInfo infoTemp = null;
	logInfo.setNStatusID(Constant.YesOrNo.YES);
	for (int i = 0; i < responseInfo.getResultSetSize(); i++)
	{
		infoTemp = new GLVoucherInfo();
		infoTemp = responseInfo.getResult(i);
		System.out.println("return Voucher :" + infoTemp.getVoucherNo());
		System.out.println("return message :" + infoTemp.getContent());
		System.out.println("return getPostDate :" + infoTemp.getPostDate());
		System.out.println("return getPostStatusID :" + infoTemp.getPostStatusID());
		if(infoTemp.getPostStatusID()!=1) bool = false;
		returnLog(infoTemp);
	}
	return bool;
}

/**
 * 组装日志
 * @param log
 * @param infoTemp
 */
public void returnLog(GLVoucherInfo infoTemp){
	
	sLog += "单据【"+infoTemp.getVoucherNo()+"】";
	if(infoTemp.getPostStatusID() == Constant.YesOrNo.NO)
	{
		logInfo.setNStatusID(Constant.YesOrNo.NO);
		if(infoTemp.getContent().indexOf("异常信息:")>0){
			sLog += infoTemp.getContent().substring(infoTemp.getContent().indexOf("异常信息:"));
		}else if(infoTemp.getContent().indexOf("处理错误:")>0){
			sLog += infoTemp.getContent().substring(infoTemp.getContent().indexOf("处理错误:"));
		}else{
			sLog += infoTemp.getContent().trim();
		}
		sLog += ";";
	}else{
		sLog += "处理成功;";
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
public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
{
	ArrayList result = new ArrayList();
	try
	{
		GLInfo = NCBean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
		GLVoucherLogInfo.bulidGLLog(lOfficeID, lCurrencyID, -1);
		logInfo = GLVoucherLogInfo.getGLLog(lOfficeID,lCurrencyID);
		
		System.out.println("***************导出会计凭证开始:***************");
		
		//结果XML数据对象		
		this.returnGLVoucher(this.getResponseInfo(collGlVoucher,date), date);
		if(sLog != null){
			log();
			if(sLog.indexOf("单据翻译转换错误")>0){
				throw new IException("NC系统接口设置有误");
			}else if(sLog.indexOf("正在修改的凭证已被别人审核")>0){
				throw new IException("导入凭证在NC系统中已存在");
			}else if(sLog.indexOf("处理成功")<0){
				throw new IException("处理错误，请查看导致日志");
			}
		}
		
		System.out.println("***************导出会计凭证结束:***************");
	}
	catch (IException ie)
	{
		ie.printStackTrace();
		throw new IException(ie.getMessage());
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new Exception(e.getMessage());
	}
	return result;
}

/**
 * 得到数据库GLSetting中针对所有接口的配置信息(做用于判断总帐接口的类型用)
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
		String strSQL = "select * from SETT_GLSETTING where OfficeID=? and CurrencyID=? and nStatusID !=0  and glname='NC' ";
		
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
			
			//总帐的操作方式（导入导出组合方式)
			info.setGlOperationType(rs.getLong("glOperationType"));
			
			//浪潮制单人		此时可以不要GLGernerSoftBean处理Bean中没有调用此方法,把以针对浪潮的此值可以不要
			//info.setGLZDRForGener(rs.getString("glZDRForGener"));
			info.setBranChcode(rs.getString("branchcode"));
			//接收方
			info.setReceiver(rs.getString("receiver"));
			//公司主键
			info.setPk_corp(rs.getString("pk_corp"));
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
public  static GlSettingInfo  getGlSettingInfo(long lOfficeID,long lCurrencyID,long lType) throws Exception
{
	GlSettingInfo info = new GlSettingInfo();
	
	Connection con = null;
	PreparedStatement ps = null;		
	ResultSet rs = null;	
	try
	{		
		//	
		con=Database.getConnection();		
		String strSQL = "select * from SETT_GLSETTING where OfficeID=? and CurrencyID=? and nStatusID !=0 and glname='NC' ";
		if(lType==0){		//导入
			strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
		    strSQL +="," + Constant.GLOperationType.ImportOnly + ")";
		}else if(lType==1){		//导出
			strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
		    strSQL +="," + Constant.GLOperationType.ExportOnly + ")";
		 }
		  
		
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
			
			//总帐的操作方式（导入导出组合方式)
			info.setGlOperationType(rs.getLong("glOperationType"));
			
			//浪潮制单人
			info.setGLZDRForGener(rs.getString("glZDRForGener"));
			
			//接收方
			info.setReceiver(rs.getString("receiver"));
			
			//公司主键
			info.setPk_corp(rs.getString("pk_corp"));
			
			//公司编码
			info.setBranChcode(rs.getString("branchcode"));
			
			//发送条数
			info.setSendnum(rs.getLong("sendnum"));
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



	/*?	U850GLBean.getGLSubject() :获取科目组合信息
	 * 返回值：科目组合信息集合；
	功能描述：从总账核算系统获取科目组合；
	流程描述：
	l	调用U850GLBean.buildGLSubjectXML()，将查询科目组合的指令生成EAI参数所需XML文件(具体见附件EAI中标准数据文件描述)；
	l	调用JavaBean.triggerQueryGLSubject()将XML发送到EAI，启动EAI进行科目组合查询；
	* */
	public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
	{
		//add by duwj and feiye 20080831 start
		for(int i=0;i<5;i++) System.out.println("NC得到科目：开始");
		
		ArrayList collGlSubject = new ArrayList(128);
		
		long SETT_NC_IMPORT_MODEL = Config.getLong(Config.SETT_NC_IMPORT_MODEL, this.message_Model);//获取同步模式
		
		if (SETT_NC_IMPORT_MODEL == this.message_Model)
		{
			collGlSubject=(ArrayList)this.getGLSubjectForNC(lOfficeID,lCurrencyID);
		}
		else if (SETT_NC_IMPORT_MODEL == this.database_Model)
		{
			Connection conn = null;		
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{	
				Sett_GlSettingDAO dao = new Sett_GlSettingDAO();
				GlSettingInfo info = new GlSettingInfo();
				info.setGlName("NC");
				Collection col = dao.findByCondition(info);
				Iterator itResult = null;
				if(col != null)
				{
					itResult = col.iterator();
				}
				GlSettingInfo ginfo = (GlSettingInfo)itResult.next();
				String code = ginfo.getReceiver();
				conn = getOracleConnection(lOfficeID,lCurrencyID);
				String sql = "select  b.subjcode,b.dispname,b.dr,b.beginperiod,a.BalanOrient from bd_accsubj b,BD_SUBJTYPE a, bd_glorgbook c where a.PK_SUBJTYPE=b.PK_SUBJTYPE and   b.pk_glorgbook = c.pk_glorgbook  and c.GLORGBOOKCODE= '"
							+code
							+"'";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next())
				{
					GLSubjectDefinitionInfo infoTemp = new GLSubjectDefinitionInfo();
					infoTemp.setSegmentName2(rs.getString("dispname"));
					infoTemp.setSegmentCode2(rs.getString("subjcode"));
					infoTemp.setStatusID(rs.getLong("dr")==0?(long)1:0);
					infoTemp.setAmountDirection(rs.getLong("BalanOrient"));
					infoTemp.setBalanceDirection(rs.getLong("BalanOrient"));
					infoTemp.setOfficeID(lOfficeID);
					infoTemp.setRoot(true);
					infoTemp.setLeaf(true);
					infoTemp.setCurrencyID(lCurrencyID);
					if(rs.getLong("BalanOrient")==SETTConstant.DebitOrCredit.DEBIT)
					{
						infoTemp.setSubjectType(SETTConstant.SubjectAttribute.ASSET);
					}
					else
					{
						infoTemp.setSubjectType(SETTConstant.SubjectAttribute.DEBT);
						
					}
					collGlSubject.add(infoTemp);
				}   
			}
			catch (Exception e)
			{
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
		}
		for(int i=0;i<5;i++) System.out.println("NC得到科目：结束"+collGlSubject.size());
		//add by duwj and feiye 20080831 end
		return (collGlSubject != null && collGlSubject.size() > 0 ? collGlSubject : null);
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
			/*
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
			*/
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList collGlSubject=null;
		try
		{
		
		collGlSubject = new ArrayList(128);
		
		long SETT_NC_IMPORT_MODEL = Config.getLong(Config.SETT_NC_IMPORT_MODEL, this.database_Model);//获取同步模式 add by xiangzhou 2012-11-16
		if (SETT_NC_IMPORT_MODEL == this.message_Model)
		{
			collGlSubject = (ArrayList)this.getGLSubjectBalanceForNC(lOfficeID,lCurrencyID,tsDate);
		}
		else if (SETT_NC_IMPORT_MODEL == this.database_Model)
		{
				Sett_GlSettingDAO dao = new Sett_GlSettingDAO();
				GlSettingInfo info1 = new GlSettingInfo();
				info1.setGlName("NC");
				Collection col = dao.findByCondition(info1);
				Iterator itResult = null;
				if(col != null)
				{
					itResult = col.iterator();
				}
				GlSettingInfo ginfo = (GlSettingInfo)itResult.next();
				String code = ginfo.getReceiver();
				/////更新科目号
				String strYearPeriod_YYYY_MM = DataFormat.getYearString(tsDate)+DataFormat.formatInt(tsDate.getMonth()+1,2);
				System.out.println("strYearPeriod_YYYY_MM:"+strYearPeriod_YYYY_MM);
				String strYearPeriod_YYYY = DataFormat.getYearString(tsDate)+"00";
				System.out.println("strYearPeriod_YYYY:"+strYearPeriod_YYYY);
				String strDate_YYYY_MM_DD = DataFormat.getDateString(tsDate);
				System.out.println("strDate_YYYY_MM_DD:"+strDate_YYYY_MM_DD);
				conn = getOracleConnection(lOfficeID,lCurrencyID);
				StringBuffer sbSQL = new StringBuffer(); 
				sbSQL.append(" select c.SUBJCODE,dispname,BalanOrient,nvl(Balnace,0) PreBalnace,nvl(Amount,0) Amount,nvl(Balnace,0)+nvl(Amount,0)  SUBJBalance \n");
				sbSQL.append(" from \n");
				sbSQL.append(" ( \n");
				sbSQL.append(" select PK_ACCSUBJ,sum(nvl(DebitAmount,0)-nvl(CreditAmount,0)) Balnace \n");
				sbSQL.append(" from GL_BALANCE \n");
				sbSQL.append(" where YEAR||PERIOD='"+strYearPeriod_YYYY+"' \n");
				sbSQL.append(" group by PK_ACCSUBJ \n");
				sbSQL.append(" ) a, \n");
				sbSQL.append(" ( \n");
				sbSQL.append(" select b.PK_ACCSUBJ,sum(nvl(DebitAmount,0)-nvl(CreditAmount,0)) Amount \n");
				sbSQL.append(" from GL_Voucher a,GL_Detail b \n");
				sbSQL.append(" where a.PK_VOUCHER = b.PK_VOUCHER \n");
				sbSQL.append(" and YEAR||PERIOD >'"+strYearPeriod_YYYY+"' \n");
				sbSQL.append(" and PreParedDate <= '"+strDate_YYYY_MM_DD+"'  AND a.dr=0 AND a.discardflag='N' \n");
				sbSQL.append(" group by b.PK_ACCSUBJ \n");
				sbSQL.append(" ) b, \n"); 
				sbSQL.append(" ( \n"); 
				sbSQL.append(" select  PK_ACCSUBJ,b.subjcode,b.dispname,b.dr,b.beginperiod,a.BalanOrient \n");
				sbSQL.append(" from bd_accsubj b,BD_SUBJTYPE a,bd_glorgbook c1  \n");
				sbSQL.append(" where a.PK_SUBJTYPE=b.PK_SUBJTYPE and b.pk_glorgbook = c1.pk_glorgbook  and c1.GLORGBOOKCODE= '").append(code).append("'\n");
				sbSQL.append(" )  c \n");
				sbSQL.append(" where c.PK_ACCSUBJ=a.PK_ACCSUBJ(+) and c.PK_ACCSUBJ=b.PK_ACCSUBJ(+) \n");
				sbSQL.append(" order by subjcode"); 			 
				System.out.println(" NC数据查询:"+sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();			
				int i=0;
				while (rs != null && rs.next())
				{				
					 
					GLBalanceInfo info = new GLBalanceInfo();
					info.setGLDate(tsDate);
					info.setOfficeID(lOfficeID);
					info.setCurrencyID(lCurrencyID);
					info.setGLSubjectCode(rs.getString("SUBJCODE"));
					double dBalance = rs.getDouble("SUBJBalance");
					if ( rs.getLong("BalanOrient") == SETTConstant.DebitOrCredit.CREDIT )
						{
						info.setBalanceDirection(SETTConstant.DebitOrCredit.CREDIT);
						info.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
						info.setDebitBalance(0);
						info.setCreditBalance(dBalance);
					}
					else
					{
						info.setBalanceDirection(SETTConstant.DebitOrCredit.DEBIT);
						info.setTransDirection(SETTConstant.DebitOrCredit.DEBIT);
						info.setDebitBalance(dBalance);
						info.setCreditBalance(0);
					}			 
					collGlSubject.add(info);
				}			
			}}
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
			
		return (collGlSubject != null && collGlSubject.size() > 0 ? collGlSubject : null);
		//	add by dwj end
	
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
		NCBean temp = new NCBean();
		return temp.new Session(url);
	}
	private static void transportBySession(Session session, NCXMLDataMarshalUtil.XMLInfo requestInfo, NCXMLDataMarshalUtil.XMLInfo responseInfo) throws Exception
	{
		
		NCBean temp = new NCBean();
		NCXMLDataMarshalUtil util = new NCXMLDataMarshalUtil();
		//准备请求Package数据
		RequestDataPackage requestData = temp.new RequestDataPackage();
		//响应数据对象
		ResponseDataPackage responseData = null;
		//设置请求XML数据
		try
		{
			Node xmlDom = util.unmarshal(requestInfo);			
			requestData.setDataSegment(xmlDom);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Occured in generate request xml data.");
		}
		
		// execute the method.
		responseData = session.transport(requestData);
		//处理返回数据
		try
		{
			util.marshal(responseInfo, responseData.getDataSegment());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("parse response xml is failed.");
		}
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
		try
		{			
//			Connection conn = ConnectionSQLServer.getU8Connection(lOfficeID,lCurrencyID,1);		
			Connection conn = getOracleConnection(lOfficeID,lCurrencyID);
			StringBuffer sbSQL = new StringBuffer();
			//得到最小的凭证号
			sbSQL.setLength(0);
			sbSQL.append(" SELECT MIN(VoucherId) as VoucherId");
			sbSQL.append(" FROM (SELECT (NO + 1) as VoucherId ");
			sbSQL.append(" FROM GL_voucher ");
			sbSQL.append(" WHERE (NO + 1) NOT IN ");
			sbSQL.append("      (");
			sbSQL.append("  		SELECT NO ");
			sbSQL.append("     		 FROM GL_voucher ");
			sbSQL.append("    		 WHERE year =  " + (info.getPostDate().getYear()+1900) + " and period =  " + (info.getPostDate().getMonth() + 1));
			sbSQL.append(" 		 )");
			sbSQL.append(" and year =  " + (info.getPostDate().getYear()+1900) + "  AND period = " + (info.getPostDate().getMonth() + 1) + " ) DERIVEDTBL   ");
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
			sbSQL.append("  SELECT NO ");
			sbSQL.append("  FROM GL_voucher ");
			sbSQL.append("  WHERE year =  " + (info.getPostDate().getYear()+1900) + "  AND period =  " + (info.getPostDate().getMonth() + 1));
			sbSQL.append("  AND NO > " + lMinVoucherID);
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				hash.put(rs.getString("no"), "1");
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
			strURL = url;
		}
		public ResponseDataPackage transport(RequestDataPackage requestData) throws Exception
		{
			
			ResponseDataPackage response = null;
			PostMethod post = new PostMethod(this.strURL);
			log.debug("try to connect to the URL[" + this.strURL + "]");
			try
			{	
				//设置请求参数	
				String strRequestData = XMLHelper.getXMLString(requestData.getDataSegment(), Session.encoding);
				
				//change by feiye 20080526
				//recordRequestInfo(strRequestData);
				
				//add by dwj 20091202
				System.out.println("发送开始>>>");
				System.out.println(strRequestData);
				System.out.println("发送结束>>>");
				//end 
				
				//log.debug(strRequestData);
				post.addRequestHeader(new Header("Content-Type", "xml;charset=" + Session.encoding));
				post.setRequestBody(strRequestData);
				this.connect(post);
				log.debug("had connected to the offer URL[" + this.strURL + "]");
				checkHttpStatusCode(post.getStatusCode(), post.getStatusText());
				log.debug("start to parser response XML data");
				InputStream inputTemp = post.getResponseBodyAsStream();
				
				Document domTemp = XMLHelper.parse(inputTemp, Session.encoding);
				//log.debug(XMLHelper.getXMLString(domTemp, Session.encoding));
				System.out.println("接收开始<<<");
				System.out.println(XMLHelper.getXMLString(domTemp, Session.encoding));
				System.out.println("接收结束<<<");
				//recordResponseInfo(XMLHelper.getXMLString(domTemp, Session.encoding));
				response = new ResponseDataPackage();
				response.setDataSegment(domTemp);
			}
			finally
			{
				this.releaseConnection(post);
				post = null;
			}
			return response;
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
		private void recordResponseInfo(String source) throws FileNotFoundException
		{
			try
			{
				FileWriter output;
				output = new FileWriter("d:\\respone\\response_voucher.xml");
				output.write(source);
				output.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		private void recordRequestInfo(String source) throws FileNotFoundException
		{
			try
			{
				FileWriter output;
				output = new FileWriter("d:\\request\\request_voucher.xml");
				output.write(source);
				output.close();
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

	
	
	/**
	 * 此方法根据数据库中保存的参数，直接连接Oracle数据库
	 * 
	 * @return Connection
	 */

	private static Connection getOracleConnection(long lOfficeID, long lCurrencyID) throws Exception
	{
		Connection conn = null;
		System.out.println(" Enter method --getOracleConnection() ");
		try
		{
			// 得到配置信息
			System.out.println(" Enter method --getOracleConnection() ");
			GlSettingInfo gldbinfo = new GlSettingInfo();
			System.out.println(" Enter method --getOracleConnection() ");
			//gldbinfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID);
			
			//change by feiye 20080526
			System.out.println(" 叶飞更改NC取得连接---start ");
			gldbinfo = NCBean.getGlSettingInfo(lOfficeID, lCurrencyID);
			System.out.println("  叶飞更改NC取得连接---end ");
			
			
			System.out.println(" End method --getGlSettingInfo() ");
			String DB_IP = gldbinfo.getGlDBIP(); // IP
			String DB_SID = gldbinfo.getGlDBDatabaseName(); // 库名称
			String DB_USERNAME = gldbinfo.getGlDBUserName(); // 用户名
			String DB_PASSWORD = DataFormat.formatNullString(gldbinfo.getGlDBPassWord()); // 密码
			String DB_PORT = gldbinfo.getGlDBPort(); // 端口

			String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;

			System.out.println("dbURL = " + dbURL);
			System.out.println("DB_USERNAME = " + DB_USERNAME);
			System.out.println("DB_PASSWORD = " + DB_PASSWORD);

			Class.forName(jdbcDriver).newInstance();
			conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException sqe)
		{
			Log.print("connect db failed by oracle jdbc driver. " + sqe.toString());
			throw sqe;
		}

		return conn;
	}
	
	/*?	U850GLBean.getGLSubjectForNC() :获取科目组合信息
	 * //add by dwj and feiye 20080831 
	 * 返回值：科目组合信息集合；
	功能描述：从总账核算系统获取科目组合；
	流程描述：
	l	调用U850GLBean.buildGLSubjectXML()，将查询科目组合的指令生成EAI参数所需XML文件(具体见附件EAI中标准数据文件描述)；
	l	调用JavaBean.triggerQueryGLSubject()将XML发送到EAI，启动EAI进行科目组合查询；
	* */
	public Collection getGLSubjectForNC(long lOfficeID, long lCurrencyID) throws Exception
	{
		ArrayList collGlSubject = new ArrayList(128);
		try
		{
			GlSettingInfo glInfo = NCBean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
			
			//创建连NC服务器的session
			Session session = createSession(glInfo.getGlResponseHTTP());
				
			//创建XML解析工具类
			XMLDataMarshalUtilForNC utilTemp = new XMLDataMarshalUtilForNC();
			//请求XML数据对象
			XMLDataMarshalUtilForNC.RequestQuerySubjectInfo requestInfo = utilTemp.new RequestQuerySubjectInfo();
			//结果XML数据对象		
			XMLDataMarshalUtilForNC.ResponseQuerySubjectInfo responseInfo = utilTemp.new ResponseQuerySubjectInfo();

			//设置请求参数
			requestInfo.rootInfo.billtype = "bdaccsubjplugin";
			requestInfo.rootInfo.filename = "示例.xml";
			requestInfo.rootInfo.isexchange = "Y";
			requestInfo.rootInfo.proc = "query";
			requestInfo.rootInfo.receiver = glInfo.getBranChcode()+"@"+glInfo.getReceiver();
			requestInfo.rootInfo.replace = "Y";
			requestInfo.rootInfo.roottag = "voucher";
			requestInfo.rootInfo.sender = glInfo.getGlSender();	
			
			requestInfo.addBill(null,lOfficeID,lCurrencyID);
			
			System.out.println("==============开始1===============");
			
			//调用工具类查询科目信息并返回结果
			transportBySessionForNC(session, requestInfo, responseInfo);
			
			System.out.println("==============一共有多少个"+responseInfo.getResultSetSize());
			
			//将返回科目信息进行封装并退出方法
			for (int i = 0; i < responseInfo.getResultSetSize(); i++)
			{
				System.out.println("==============第"+i+"个:");
				GLSubjectDefinitionInfo infoTemp = responseInfo.getResult(i);
				infoTemp.setCurrencyID(lCurrencyID);
				infoTemp.setOfficeID(lOfficeID);
				infoTemp.setRoot(true);
				infoTemp.setStatusID(Constant.RecordStatus.VALID);
				collGlSubject.add(infoTemp);
			}
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return (collGlSubject.size() > 0) ? collGlSubject : null;
	}
	
	//
	/*?	U850GLBean.getGLSubjectBalanceForNC() :获取科目余额信息
	 * //add by dwj and feiye 20080831 
	 * 返回值：科目组合信息集合
	* */
	public Collection getGLSubjectBalanceForNC(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		ArrayList collGlSubjectBalance = new ArrayList(128);
		try
		{
			GlSettingInfo glInfo = NCBean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
			//创建连NC服务器的session
			Session session = NCBean.createSession(glInfo.getGlResponseHTTP());
				
			//创建XML解析工具类
			XMLDataMarshalUtilForNC utilTemp = new XMLDataMarshalUtilForNC();
			ArrayList arrayList = new ArrayList();
			XMLDataMarshalUtilForNC.RequestQuerySubjectBalanceInfo requestInfo = utilTemp.new RequestQuerySubjectBalanceInfo();
			XMLDataMarshalUtilForNC.RequestQuerySubjectBalanceInfo.BillInfo billInfo = requestInfo.new BillInfo();
			XMLDataMarshalUtilForNC.ResponseQuerySubjectBalanceInfo responseInfo = utilTemp.new ResponseQuerySubjectBalanceInfo();
			
			requestInfo.rootInfo.billtype = "accsubjban";
			requestInfo.rootInfo.filename = "55.xml";
			requestInfo.rootInfo.isexchange = "Y";
			requestInfo.rootInfo.proc = "query";
			requestInfo.rootInfo.receiver = glInfo.getBranChcode()+"@"+glInfo.getReceiver();//01
			requestInfo.rootInfo.replace = "Y";
			requestInfo.rootInfo.roottag = "bill";
			requestInfo.rootInfo.sender = glInfo.getGlSender();//ISS
			
			XMLDataMarshalUtilForNC.RequestQuerySubjectBalanceInfo.BillInfo.BillEntryInfo entryInfo = null;
			
			String sDate="";	//字符串型日期
			String sYear="";	//字符串型年
			String sMonth = "";	//字符串型月
			sDate=DataFormat.formatDate(tsDate,1);
			if(sDate!=null&&sDate.length()>4){
				sYear=sDate.substring(0,4);
			}
			if(sDate!=null&&sDate.length()>7){
				sMonth=sDate.substring(5,7);
				System.out.println("sMonth" + sMonth);
			}
			//update dwj 20080911 注释掉
			//for(int ii=0;ii<10;ii++){
			System.out.println("sDate:"+sDate);
			System.out.println("sYear:"+sYear);
			//}
			//end update dwj 20080911 注释掉
			
			//add by dwj 20090927
			billInfo.bill_head.date = sDate;//会计日期, 日期必须是 YYYY-MM-DD格式，如果periodordate=D不能空
			billInfo.bill_head.ifallaccsubj = "N";//有科目，Y代表是，N代表否，如果是Y，条件体内不需要再指定科目编码
			billInfo.bill_head.ifincludeuntally = "Y";//是否包含未记账凭证，Y代表是，N代表否，如果是Y
			billInfo.bill_head.ifneedbegin = "Y";//是否需要期初数据, Y代表需要，N代表不需要，不能空
			billInfo.bill_head.period = sMonth;//会计期间，不能空
			billInfo.bill_head.periodordate = "D";//按会计期间或会计日期查询, P代表按会计期间，D代表按会计日期查询，不能空
			billInfo.bill_head.pk_corp = glInfo.getBranChcode();//公司编码，可以是外部系统的编码，但是需要和NC系统的公司编码对照，不能空
			billInfo.bill_head.pk_glorgbook = glInfo.getReceiver();//主体账簿（会计账簿）编码，此数据NC系统提供
			billInfo.bill_head.year = sYear;//会计年份，不能空
			
			Vector vAll = new Vector();	//定义科目集合的信息
			
			//取数据库中所有的科目
			vAll=(Vector)this.findGLSubjectCodeForNC(lOfficeID, lCurrencyID);
			
			if(vAll!=null&&vAll.size()>0){
				for(int i=0;i<vAll.size();i++){
					System.out.println("组织数据: 第"+i+"条： 科目号为:"+(String)vAll.get(i));
					entryInfo = billInfo.new BillEntryInfo();
					//update by dwj 20090928
					//entryInfo.pk_corp = "1341";
					//entryInfo.period = sDate;//会计期间
					//entryInfo.year = sYear;//会计年度
					//entryInfo.pk_accsubj = (String)vAll.get(i);//科目编码
					//entryInfo.pk_glorgbook = "Q88-0001";//主体账簿（会计账簿）
					entryInfo.pk_accsubj = (String)vAll.get(i);//科目号
					entryInfo.pk_currtype = "CNY";//币种
					//end update by dwj 20090928
					arrayList.add(entryInfo);
				}
				//add by dwj 20090929 测试用
				//entryInfo = billInfo.new BillEntryInfo();
				//entryInfo.pk_accsubj = "100101";
				//entryInfo.pk_currtype = "CNY";//币种
				//arrayList.add(entryInfo);
				//end add by dwj 20090929
			}else{
				System.out.println("组织数据: 没有得到科目号数据!");
			}
			
			try {
				//update by dwj 20090928，在头中添加了节点值
				//requestInfo.addBill(arrayList, 1, 1);
				requestInfo.addBill(arrayList, 1, 1,billInfo);
				//end update by dwj 20090928
			} catch (Exception e) {
				e.printStackTrace();
			}
			//add dwj 20080901
			
			//调用工具类查询科目信息并返回结果
			transportBySessionForNC(session, requestInfo, responseInfo);
			
			String tmp = responseInfo.toString();
			if(tmp.indexOf("<resultcode>-32000</resultcode>")>1)
			{
				throw new Exception("返回科目余额信息报文失败："
						+tmp.substring(tmp.indexOf("<resultdescription>")+19,tmp.indexOf("</resultdescription>")));
			}
			
			//将返回科目信息进行封装并退出方法
			for (int i = 0; i < responseInfo.getResultSetSize(); i++)
			{
				//update dwj 20080910
				//GLSubjectDefinitionInfo infoTemp = responseInfo.getResult(i);
				GLBalanceInfo infoTemp = responseInfo.getResult(i);
				//end update dwj 20080910
				infoTemp.setGLDate(tsDate);
				infoTemp.setCurrencyID(lCurrencyID);	//办事处
				infoTemp.setOfficeID(lOfficeID);		//币种
				collGlSubjectBalance.add(infoTemp);
			}
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return (collGlSubjectBalance.size() > 0) ? collGlSubjectBalance : null;
	}
	
	private static void transportBySessionForNC(Session session, XMLDataMarshalUtilForNC.XMLInfo requestInfo, XMLDataMarshalUtilForNC.XMLInfo responseInfo) throws Exception
	{
		System.out.println("==============开始2===============");
		NCBean temp = new NCBean();
		XMLDataMarshalUtilForNC util = new XMLDataMarshalUtilForNC();
		//准备请求Package数据
		RequestDataPackage requestData = temp.new RequestDataPackage();
		//响应数据对象
		ResponseDataPackage responseData = null;
		//设置请求XML数据
		try
		{
			System.out.println("==============开始3===============");
			Node xmlDom = util.unmarshal(requestInfo);
			requestData.setDataSegment(xmlDom);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Occured in generate request xml data.");
		}
		
		System.out.println("==============开始4===============");
		// execute the method.
		responseData = session.transport(requestData);
		//处理返回数据
		try
		{
			System.out.println("==============开始8===============");			
			util.marshal(responseInfo, responseData.getDataSegment());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("parse response xml is failed.");
		}
	}
	
	/**
	 * 得到科目名称	add by feiye 20080911
	 * @param lOfficeID  办事处标识
	 * @param lCurrencyID 币种
	 */
	private Collection findGLSubjectCodeForNC(long lOfficeID, long lCurrencyID) throws RemoteException
	{
		Log.print("**********************in findGLTransType ForAll*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vAll = new Vector();	//得到所有的科目集合的信息
		String strAccount="";
		try
		{
			conn = Database.getConnection();
	
			sbSQL.setLength(0);
			sbSQL.append(" select sSegmentCode2 subjectNO \n");
			sbSQL.append(" from sett_glsubjectdefinition a \n");
			//update by dwj 20090929  nIsleaf=1 表示只取末级科目
			//sbSQL.append(" where a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatus,0) >0 " + " \n");
			sbSQL.append(" where a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatus,0) >0  and nIsleaf=1" + " \n");
			//end update by dwj 20090929
			sbSQL.append(" order by a.ssegmentcode2  \n");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				strAccount=rs.getString("subjectNO");  //得到科目号
				if(strAccount!=null && !strAccount.equals("")){
					vAll.add(strAccount);
				}
			}
	
			//关闭资源
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
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return (vAll.size() > 0 ? vAll : null);
	}
	
	/**
	 * 添加日志
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param log
	 */
	public void log(){
		
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = Database.getConnection();
			sbSQL.append("insert into SETT_GLVoucher_LOG(");
			sbSQL.append("id,OFFICEID,CURRENCYID,inputUserID,log,inputDate,NStatusID)");
			sbSQL.append("values(");
			sbSQL.append("(select nvl(max(id)+1,1) from SETT_GLVoucher_LOG),?,?,?,?,sysdate,?)");
			ps = conn.prepareStatement(sbSQL.toString());
			int index = 1;
			ps.setLong(index++, logInfo.OFFICEID);
			ps.setLong(index++, logInfo.CURRENCYID);
			ps.setLong(index++, logInfo.inputUserID);
			ps.setString(index++, logInfo.getLog()==null?sLog:logInfo.getLog());
			ps.setLong(index++, logInfo.getNStatusID());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
}
