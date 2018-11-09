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
	Hashtable hash = new Hashtable();///���潻�׺���ƾ֤��֮��Ĺ�ϵ
	Hashtable hashentrylist = new Hashtable();///����VoucherInfo��ƾ֤��֮��Ĺ�ϵ
	GLVoucherLogInfo logInfo = null;
	GlSettingInfo GLInfo = null;
	boolean bIsPassed = false;
	String sLog = null;
	
	private long message_Model = 1;//����ģʽ
	private long database_Model = 2;//�м��ģʽ
	
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
 *  ��װrequest��Ϣ
 * @param lOfficeID
 * @param lCurrencyID
 * @return
 */
public NCXMLDataMarshalUtil.VoucherListInfo getRequestInfo(GlSettingInfo glInfo){
	
	NCXMLDataMarshalUtil utilTemp = new NCXMLDataMarshalUtil();
	//����XML���ݶ���
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
 * �õ�Response��Ϣ
 * @param collGlVoucher
 * @param GLInfo
 * @return
 * @throws Exception
 * @throws IException
 */
public NCXMLDataMarshalUtil.ResponseXMLInfo getResponseInfo(Collection collGlVoucher,Timestamp date) throws Exception,IException{
	
	//����XML���ݶ���
	NCXMLDataMarshalUtil.ResponseXMLInfo responseInfo = new NCXMLDataMarshalUtil().new ResponseXMLInfo();
	
	NCXMLDataMarshalUtil.VoucherListInfo requestInfo = this.getRequestInfo(GLInfo);
	
	Session session = NCBean.createSession(GLInfo.getGlResponseHTTP());
	
	GLVoucherInfo voucherTemp = null;
	Iterator itTemp = collGlVoucher.iterator();
		
	///Ϊ�˲��Ϻţ��ȵõ�����ƾ֤��ֵ��
	long[] lVoucherNO = null;
	int iVoucherCounter = 0;
	
	long _sendNum = 0;		
	long otherNum = 0;
	long sendNum = GLInfo.getSendnum();	//ÿ���ύ����
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
		////���û��ȡ�����µ�ƾ֤���ڣ���ȡ���ݣ�
		if (lVoucherNO == null || lVoucherNO.length <= 0)
		{
			System.out.println("********��ʼ��NCϵͳ��ȡ�������ƾ֤��");					
			System.out.println("********�ֹ�����ƾ֤����Ϊ"+collGlVoucher.size());
			lVoucherNO = new long[collGlVoucher.size()];
		
			for(int ii=3;ii<=(collGlVoucher.size()+2);ii++){
				lVoucherNO[ii-3] = ii;
				System.out.println("********�ֹ�����ƾ֤����Ϊ end "+ii);
			}
			
			System.out.println("********������NCϵͳ��ȡ�������ƾ֤��"+lVoucherNO[0]);
		}
		if (lVoucherNO != null && lVoucherNO.length > iVoucherCounter)
		{
			//20080827 ƾ֤��ȡ0������������ƾ֤��
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
			logInfo.setLog("û�д�NCϵͳ��ȡ�������ƾ֤��");
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
				logInfo.setLog("NCϵͳ����ʧ��:"+ie.getMessage());
				logInfo.setNStatusID(Constant.YesOrNo.NO);
				log();
				throw new IException("NCϵͳ����ʧ��");
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
		logInfo.setLog("NCϵͳ����ʧ��:"+ie.getMessage());
		logInfo.setNStatusID(Constant.YesOrNo.NO);
		log();
		throw new IException("NCϵͳ����ʧ��");
	}
	
	return responseInfo;
	
}
/**
 * ����ƾ֤������Ϣ
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
		//ת��Ϊ����ϵͳ�Ľ��׺�
		sTempVoucher = infoTemp.getVoucherNo() != null && infoTemp.getVoucherNo().length() > 0 ? infoTemp.getVoucherNo() : "0";
		infoTemp.setTransNo(sTempVoucher);
		
		////������ÿһ��ƾ֤�ڵķ�¼��ԭ
		tempvoucher = (GLVoucherInfo)hashentrylist.get(infoTemp.getTransNo());

		if(tempvoucher==null){
			System.out.println("Ϊ�գ�");
		}else{
			System.out.println("��Ϊ��");
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
 * ��װ��־
 * @param log
 * @param infoTemp
 */
public void returnLog(GLVoucherInfo infoTemp){
	
	sLog += "���ݡ�"+infoTemp.getVoucherNo()+"��";
	if(infoTemp.getPostStatusID() == Constant.YesOrNo.NO)
	{
		logInfo.setNStatusID(Constant.YesOrNo.NO);
		if(infoTemp.getContent().indexOf("�쳣��Ϣ:")>0){
			sLog += infoTemp.getContent().substring(infoTemp.getContent().indexOf("�쳣��Ϣ:"));
		}else if(infoTemp.getContent().indexOf("�������:")>0){
			sLog += infoTemp.getContent().substring(infoTemp.getContent().indexOf("�������:"));
		}else{
			sLog += infoTemp.getContent().trim();
		}
		sLog += ";";
	}else{
		sLog += "����ɹ�;";
	}
	
}
	
	/*
	 * @author yychen
	GLSystemBaseBean.postGLVoucher()���������ƾ֤
	������Collection collGlVoucher: FindGLVoucherBaseBean. findGLVoucherByCondition ()���ص�ƾ֤���ϣ�
	����ֵ��boolean bIsSuccess:�Ƿ�ɹ���
	������������ҵ��ϵͳ��ƾ֤��Ϣ����XML�ļ���Ȼ�󴫳���EAI��
	����������
	l	����U850GLBean.buildGlVoucherXML(),��ƾ֤��Ϣ����ת����XML�ļ���
	l	����U850GLBean.triggerPostGlVoucher()����XML�ļ�����EAIϵͳ��
	l	�����Ƿ�ɹ�! 
	*/
public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
{
	ArrayList result = new ArrayList();
	try
	{
		GLInfo = NCBean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
		GLVoucherLogInfo.bulidGLLog(lOfficeID, lCurrencyID, -1);
		logInfo = GLVoucherLogInfo.getGLLog(lOfficeID,lCurrencyID);
		
		System.out.println("***************�������ƾ֤��ʼ:***************");
		
		//���XML���ݶ���		
		this.returnGLVoucher(this.getResponseInfo(collGlVoucher,date), date);
		if(sLog != null){
			log();
			if(sLog.indexOf("���ݷ���ת������")>0){
				throw new IException("NCϵͳ�ӿ���������");
			}else if(sLog.indexOf("�����޸ĵ�ƾ֤�ѱ��������")>0){
				throw new IException("����ƾ֤��NCϵͳ���Ѵ���");
			}else if(sLog.indexOf("����ɹ�")<0){
				throw new IException("���������鿴������־");
			}
		}
		
		System.out.println("***************�������ƾ֤����:***************");
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
 * �õ����ݿ�GLSetting��������нӿڵ�������Ϣ(�������ж����ʽӿڵ�������)
 * 
 * lOfficeID :���´�
 * lCurrencyID :����
 * lType:	�������� 0:���� 1:����
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
		if(rs.next())		//����в�ѯ�İ��´��ͱ�����ͬ�ļ�¼��ȡ��һ������
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
			
			//���ʵĲ�����ʽ�����뵼����Ϸ�ʽ)
			info.setGlOperationType(rs.getLong("glOperationType"));
			
			//�˳��Ƶ���		��ʱ���Բ�ҪGLGernerSoftBean����Bean��û�е��ô˷���,��������˳��Ĵ�ֵ���Բ�Ҫ
			//info.setGLZDRForGener(rs.getString("glZDRForGener"));
			info.setBranChcode(rs.getString("branchcode"));
			//���շ�
			info.setReceiver(rs.getString("receiver"));
			//��˾����
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
 * �õ����ݿ�GLSetting��������нӿڵ�������Ϣ(��ʵ�ʵĵ��뵼������ʱ,ȡ�����������Ϣʱ��)
 * 
 * lOfficeID :���´�
 * lCurrencyID :����
 * lType:	�������� 0:���� 1:����
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
		if(lType==0){		//����
			strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
		    strSQL +="," + Constant.GLOperationType.ImportOnly + ")";
		}else if(lType==1){		//����
			strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
		    strSQL +="," + Constant.GLOperationType.ExportOnly + ")";
		 }
		  
		
		System.out.println("**********************"+strSQL);
		ps = con.prepareStatement(strSQL);
		ps.setLong(1,lOfficeID);	
		ps.setLong(2,lCurrencyID);			
		rs=ps.executeQuery();
		if(rs.next())		//����в�ѯ�İ��´��ͱ�����ͬ�ļ�¼��ȡ��һ������
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
			
			//���ʵĲ�����ʽ�����뵼����Ϸ�ʽ)
			info.setGlOperationType(rs.getLong("glOperationType"));
			
			//�˳��Ƶ���
			info.setGLZDRForGener(rs.getString("glZDRForGener"));
			
			//���շ�
			info.setReceiver(rs.getString("receiver"));
			
			//��˾����
			info.setPk_corp(rs.getString("pk_corp"));
			
			//��˾����
			info.setBranChcode(rs.getString("branchcode"));
			
			//��������
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



	/*?	U850GLBean.getGLSubject() :��ȡ��Ŀ�����Ϣ
	 * ����ֵ����Ŀ�����Ϣ���ϣ�
	���������������˺���ϵͳ��ȡ��Ŀ��ϣ�
	����������
	l	����U850GLBean.buildGLSubjectXML()������ѯ��Ŀ��ϵ�ָ������EAI��������XML�ļ�(���������EAI�б�׼�����ļ�����)��
	l	����JavaBean.triggerQueryGLSubject()��XML���͵�EAI������EAI���п�Ŀ��ϲ�ѯ��
	* */
	public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
	{
		//add by duwj and feiye 20080831 start
		for(int i=0;i<5;i++) System.out.println("NC�õ���Ŀ����ʼ");
		
		ArrayList collGlSubject = new ArrayList(128);
		
		long SETT_NC_IMPORT_MODEL = Config.getLong(Config.SETT_NC_IMPORT_MODEL, this.message_Model);//��ȡͬ��ģʽ
		
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
		for(int i=0;i<5;i++) System.out.println("NC�õ���Ŀ������"+collGlSubject.size());
		//add by duwj and feiye 20080831 end
		return (collGlSubject != null && collGlSubject.size() > 0 ? collGlSubject : null);
	}
	/**
	 * �õ�������������ױ�λ�ҵĻ���
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
	 * �õ����ױ�λ��
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
			/////						ֵ�ÿ���һ��  2005/9/14
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
	 * �õ����·�����
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
	 * ���·���+�������=��Ŀ���
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
		
		long SETT_NC_IMPORT_MODEL = Config.getLong(Config.SETT_NC_IMPORT_MODEL, this.database_Model);//��ȡͬ��ģʽ add by xiangzhou 2012-11-16
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
				/////���¿�Ŀ��
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
				System.out.println(" NC���ݲ�ѯ:"+sbSQL.toString());
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
	������XML�ļ�����
	����ֵ��boolean bIsSuccess:�Ƿ�ɹ���
	������������XML�ļ���������EAI��
	����������
	l	��XML�ļ���������EAI�� 	
	 * */
	private static long triggerPostGLVoucher() throws Exception
	{
		return -1;
	}
	/*
	 U850GLBean. buildGLVoucherXML ():
	 ������Collection collGlVoucher: ƾ֤���ϣ�U850GLBean.postGlVoucher������������
	����ֵ��XML�ļ�����
	������������ҵ��ϵͳ��ƾ֤��Ϣ����ת����XML�ļ���
	����������
	l	��ҵ��ϵͳ��ƾ֤��Ϣ����ת����XML�ļ���	
	 *  */
	private static long buildGLVoucherXML(Collection collGlVoucher) throws Exception
	{
		return -1;
	}
	/*
	 U850GLBean. resolveGLVoucherXML ():
	��������
	����ֵ������ XML�ļ����õ���Ϣ���ϣ�
	�������������� XML�ļ���
	����������
	���� XML�ļ���	
	 * */
	private static Collection resolveGLVoucherXML(Collection collGlVoucher) throws Exception
	{
		return null;
	}
	/*
		 * U850GLBean.triggerPostGlVoucher ():
		������XML�ļ�����
		����ֵ��boolean bIsSuccess:�Ƿ�ɹ���
		������������XML�ļ���������EAI��
		����������
		l	��XML�ļ���������EAI�� 	
		 * */
	private static long triggerQueryGLSubject() throws Exception
	{
		return -1;
	}
	/*
		 U850GLBean. resolveGLSubjectXML ():
		��������
		����ֵ������ XML�ļ����õ���Ϣ���ϣ�
		�������������� XML�ļ���
		����������
		���� XML�ļ���	
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
		//׼������Package����
		RequestDataPackage requestData = temp.new RequestDataPackage();
		//��Ӧ���ݶ���
		ResponseDataPackage responseData = null;
		//��������XML����
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
		//����������
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
	 * �õ���ͬ���׵�ƾ֤��
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
			//�õ���С��ƾ֤��
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
				/////�����ݿ���ȡID
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
			//�õ�������Сƾ֤�ŵ�����ƾ֤
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
			////�õ������ƾ֤��
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
				//�����������	
				String strRequestData = XMLHelper.getXMLString(requestData.getDataSegment(), Session.encoding);
				
				//change by feiye 20080526
				//recordRequestInfo(strRequestData);
				
				//add by dwj 20091202
				System.out.println("���Ϳ�ʼ>>>");
				System.out.println(strRequestData);
				System.out.println("���ͽ���>>>");
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
				System.out.println("���տ�ʼ<<<");
				System.out.println(XMLHelper.getXMLString(domTemp, Session.encoding));
				System.out.println("���ս���<<<");
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
		 * ����ָ���ķ���ִ����������
		 * ע��ͬһ��HttpMethod������������
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
	 * �˷����������ݿ��б���Ĳ�����ֱ������Oracle���ݿ�
	 * 
	 * @return Connection
	 */

	private static Connection getOracleConnection(long lOfficeID, long lCurrencyID) throws Exception
	{
		Connection conn = null;
		System.out.println(" Enter method --getOracleConnection() ");
		try
		{
			// �õ�������Ϣ
			System.out.println(" Enter method --getOracleConnection() ");
			GlSettingInfo gldbinfo = new GlSettingInfo();
			System.out.println(" Enter method --getOracleConnection() ");
			//gldbinfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID);
			
			//change by feiye 20080526
			System.out.println(" Ҷ�ɸ���NCȡ������---start ");
			gldbinfo = NCBean.getGlSettingInfo(lOfficeID, lCurrencyID);
			System.out.println("  Ҷ�ɸ���NCȡ������---end ");
			
			
			System.out.println(" End method --getGlSettingInfo() ");
			String DB_IP = gldbinfo.getGlDBIP(); // IP
			String DB_SID = gldbinfo.getGlDBDatabaseName(); // ������
			String DB_USERNAME = gldbinfo.getGlDBUserName(); // �û���
			String DB_PASSWORD = DataFormat.formatNullString(gldbinfo.getGlDBPassWord()); // ����
			String DB_PORT = gldbinfo.getGlDBPort(); // �˿�

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
	
	/*?	U850GLBean.getGLSubjectForNC() :��ȡ��Ŀ�����Ϣ
	 * //add by dwj and feiye 20080831 
	 * ����ֵ����Ŀ�����Ϣ���ϣ�
	���������������˺���ϵͳ��ȡ��Ŀ��ϣ�
	����������
	l	����U850GLBean.buildGLSubjectXML()������ѯ��Ŀ��ϵ�ָ������EAI��������XML�ļ�(���������EAI�б�׼�����ļ�����)��
	l	����JavaBean.triggerQueryGLSubject()��XML���͵�EAI������EAI���п�Ŀ��ϲ�ѯ��
	* */
	public Collection getGLSubjectForNC(long lOfficeID, long lCurrencyID) throws Exception
	{
		ArrayList collGlSubject = new ArrayList(128);
		try
		{
			GlSettingInfo glInfo = NCBean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
			
			//������NC��������session
			Session session = createSession(glInfo.getGlResponseHTTP());
				
			//����XML����������
			XMLDataMarshalUtilForNC utilTemp = new XMLDataMarshalUtilForNC();
			//����XML���ݶ���
			XMLDataMarshalUtilForNC.RequestQuerySubjectInfo requestInfo = utilTemp.new RequestQuerySubjectInfo();
			//���XML���ݶ���		
			XMLDataMarshalUtilForNC.ResponseQuerySubjectInfo responseInfo = utilTemp.new ResponseQuerySubjectInfo();

			//�����������
			requestInfo.rootInfo.billtype = "bdaccsubjplugin";
			requestInfo.rootInfo.filename = "ʾ��.xml";
			requestInfo.rootInfo.isexchange = "Y";
			requestInfo.rootInfo.proc = "query";
			requestInfo.rootInfo.receiver = glInfo.getBranChcode()+"@"+glInfo.getReceiver();
			requestInfo.rootInfo.replace = "Y";
			requestInfo.rootInfo.roottag = "voucher";
			requestInfo.rootInfo.sender = glInfo.getGlSender();	
			
			requestInfo.addBill(null,lOfficeID,lCurrencyID);
			
			System.out.println("==============��ʼ1===============");
			
			//���ù������ѯ��Ŀ��Ϣ�����ؽ��
			transportBySessionForNC(session, requestInfo, responseInfo);
			
			System.out.println("==============һ���ж��ٸ�"+responseInfo.getResultSetSize());
			
			//�����ؿ�Ŀ��Ϣ���з�װ���˳�����
			for (int i = 0; i < responseInfo.getResultSetSize(); i++)
			{
				System.out.println("==============��"+i+"��:");
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
	/*?	U850GLBean.getGLSubjectBalanceForNC() :��ȡ��Ŀ�����Ϣ
	 * //add by dwj and feiye 20080831 
	 * ����ֵ����Ŀ�����Ϣ����
	* */
	public Collection getGLSubjectBalanceForNC(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		ArrayList collGlSubjectBalance = new ArrayList(128);
		try
		{
			GlSettingInfo glInfo = NCBean.getGlSettingInfo(lOfficeID,lCurrencyID,1);
			//������NC��������session
			Session session = NCBean.createSession(glInfo.getGlResponseHTTP());
				
			//����XML����������
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
			
			String sDate="";	//�ַ���������
			String sYear="";	//�ַ�������
			String sMonth = "";	//�ַ�������
			sDate=DataFormat.formatDate(tsDate,1);
			if(sDate!=null&&sDate.length()>4){
				sYear=sDate.substring(0,4);
			}
			if(sDate!=null&&sDate.length()>7){
				sMonth=sDate.substring(5,7);
				System.out.println("sMonth" + sMonth);
			}
			//update dwj 20080911 ע�͵�
			//for(int ii=0;ii<10;ii++){
			System.out.println("sDate:"+sDate);
			System.out.println("sYear:"+sYear);
			//}
			//end update dwj 20080911 ע�͵�
			
			//add by dwj 20090927
			billInfo.bill_head.date = sDate;//�������, ���ڱ����� YYYY-MM-DD��ʽ�����periodordate=D���ܿ�
			billInfo.bill_head.ifallaccsubj = "N";//�п�Ŀ��Y�����ǣ�N����������Y���������ڲ���Ҫ��ָ����Ŀ����
			billInfo.bill_head.ifincludeuntally = "Y";//�Ƿ����δ����ƾ֤��Y�����ǣ�N����������Y
			billInfo.bill_head.ifneedbegin = "Y";//�Ƿ���Ҫ�ڳ�����, Y������Ҫ��N������Ҫ�����ܿ�
			billInfo.bill_head.period = sMonth;//����ڼ䣬���ܿ�
			billInfo.bill_head.periodordate = "D";//������ڼ�������ڲ�ѯ, P��������ڼ䣬D����������ڲ�ѯ�����ܿ�
			billInfo.bill_head.pk_corp = glInfo.getBranChcode();//��˾���룬�������ⲿϵͳ�ı��룬������Ҫ��NCϵͳ�Ĺ�˾������գ����ܿ�
			billInfo.bill_head.pk_glorgbook = glInfo.getReceiver();//�����˲�������˲������룬������NCϵͳ�ṩ
			billInfo.bill_head.year = sYear;//�����ݣ����ܿ�
			
			Vector vAll = new Vector();	//�����Ŀ���ϵ���Ϣ
			
			//ȡ���ݿ������еĿ�Ŀ
			vAll=(Vector)this.findGLSubjectCodeForNC(lOfficeID, lCurrencyID);
			
			if(vAll!=null&&vAll.size()>0){
				for(int i=0;i<vAll.size();i++){
					System.out.println("��֯����: ��"+i+"���� ��Ŀ��Ϊ:"+(String)vAll.get(i));
					entryInfo = billInfo.new BillEntryInfo();
					//update by dwj 20090928
					//entryInfo.pk_corp = "1341";
					//entryInfo.period = sDate;//����ڼ�
					//entryInfo.year = sYear;//������
					//entryInfo.pk_accsubj = (String)vAll.get(i);//��Ŀ����
					//entryInfo.pk_glorgbook = "Q88-0001";//�����˲�������˲���
					entryInfo.pk_accsubj = (String)vAll.get(i);//��Ŀ��
					entryInfo.pk_currtype = "CNY";//����
					//end update by dwj 20090928
					arrayList.add(entryInfo);
				}
				//add by dwj 20090929 ������
				//entryInfo = billInfo.new BillEntryInfo();
				//entryInfo.pk_accsubj = "100101";
				//entryInfo.pk_currtype = "CNY";//����
				//arrayList.add(entryInfo);
				//end add by dwj 20090929
			}else{
				System.out.println("��֯����: û�еõ���Ŀ������!");
			}
			
			try {
				//update by dwj 20090928����ͷ������˽ڵ�ֵ
				//requestInfo.addBill(arrayList, 1, 1);
				requestInfo.addBill(arrayList, 1, 1,billInfo);
				//end update by dwj 20090928
			} catch (Exception e) {
				e.printStackTrace();
			}
			//add dwj 20080901
			
			//���ù������ѯ��Ŀ��Ϣ�����ؽ��
			transportBySessionForNC(session, requestInfo, responseInfo);
			
			String tmp = responseInfo.toString();
			if(tmp.indexOf("<resultcode>-32000</resultcode>")>1)
			{
				throw new Exception("���ؿ�Ŀ�����Ϣ����ʧ�ܣ�"
						+tmp.substring(tmp.indexOf("<resultdescription>")+19,tmp.indexOf("</resultdescription>")));
			}
			
			//�����ؿ�Ŀ��Ϣ���з�װ���˳�����
			for (int i = 0; i < responseInfo.getResultSetSize(); i++)
			{
				//update dwj 20080910
				//GLSubjectDefinitionInfo infoTemp = responseInfo.getResult(i);
				GLBalanceInfo infoTemp = responseInfo.getResult(i);
				//end update dwj 20080910
				infoTemp.setGLDate(tsDate);
				infoTemp.setCurrencyID(lCurrencyID);	//���´�
				infoTemp.setOfficeID(lOfficeID);		//����
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
		System.out.println("==============��ʼ2===============");
		NCBean temp = new NCBean();
		XMLDataMarshalUtilForNC util = new XMLDataMarshalUtilForNC();
		//׼������Package����
		RequestDataPackage requestData = temp.new RequestDataPackage();
		//��Ӧ���ݶ���
		ResponseDataPackage responseData = null;
		//��������XML����
		try
		{
			System.out.println("==============��ʼ3===============");
			Node xmlDom = util.unmarshal(requestInfo);
			requestData.setDataSegment(xmlDom);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Occured in generate request xml data.");
		}
		
		System.out.println("==============��ʼ4===============");
		// execute the method.
		responseData = session.transport(requestData);
		//����������
		try
		{
			System.out.println("==============��ʼ8===============");			
			util.marshal(responseInfo, responseData.getDataSegment());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("parse response xml is failed.");
		}
	}
	
	/**
	 * �õ���Ŀ����	add by feiye 20080911
	 * @param lOfficeID  ���´���ʶ
	 * @param lCurrencyID ����
	 */
	private Collection findGLSubjectCodeForNC(long lOfficeID, long lCurrencyID) throws RemoteException
	{
		Log.print("**********************in findGLTransType ForAll*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vAll = new Vector();	//�õ����еĿ�Ŀ���ϵ���Ϣ
		String strAccount="";
		try
		{
			conn = Database.getConnection();
	
			sbSQL.setLength(0);
			sbSQL.append(" select sSegmentCode2 subjectNO \n");
			sbSQL.append(" from sett_glsubjectdefinition a \n");
			//update by dwj 20090929  nIsleaf=1 ��ʾֻȡĩ����Ŀ
			//sbSQL.append(" where a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatus,0) >0 " + " \n");
			sbSQL.append(" where a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatus,0) >0  and nIsleaf=1" + " \n");
			//end update by dwj 20090929
			sbSQL.append(" order by a.ssegmentcode2  \n");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				strAccount=rs.getString("subjectNO");  //�õ���Ŀ��
				if(strAccount!=null && !strAccount.equals("")){
					vAll.add(strAccount);
				}
			}
	
			//�ر���Դ
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
	 * �����־
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
