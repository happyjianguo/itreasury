package com.iss.itreasury.glinterface.joinCheer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.iss.itreasury.closesystem.settlement.SettGLWithinDao;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.kingdee.dao.GLVoucherDao;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Constant.CurrencyType;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * 久其财务接口工具类
 * @author xiangzhou 2013-01-09
 *
 */
public class GLDataUtil {
	
	String SystemDate = "";
	public static String GlResponseHTTP = "";
	
	/**
	 * 组织发送凭证报文
	 * @param collGlVoucher
	 * @return
	 * @throws Exception 
	 */
	public String getVoucherRequestXML(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception{
		
		String requestXML = "";
		
		GLVoucherInfo voucherTemp = null;
		Timestamp glPostDate = null;
		GLEntryInfo gLEntryInfo = null;
		
		String debit = "";
		String credit = "";
		String rate = "1.00";
		
		String sourcesystemid = "SLCWGSZJXT";
		String sourcesystemname = "首旅财务公司资金管理系统";
		String memo = "首旅财务公司资金管理系统测试凭证";
		
		SystemDate = DataFormat.getDateString(date);
		
		try
		{
			if(collGlVoucher != null)
			{
				GlSettingInfo glSettingInfo = this.getGlSettingInfo(lOfficeID, lCurrencyID, 1);
				GlResponseHTTP = glSettingInfo.getGlResponseHTTP();
				//得到辅助核算协定项
				GLVoucherDao dao = new GLVoucherDao();
				Map<String, String> map = dao.getGLKingdeeclient();
				
				Document document = DocumentHelper.createDocument();
		    	Element gl_voucher = document.addElement("gl_voucher");
		    	
		    	Element voucher = null;
		    	Element master = null;
		    	Element items = null;
		    	Element item = null;
				
				//组装报文头
		    	Element msgheader = gl_voucher.addElement("msgheader");
				msgheader.addElement("sourcesystemid").addText(sourcesystemid);
				msgheader.addElement("sourcesystemname").addText(sourcesystemname);
				msgheader.addElement("userid").addText("1");
				msgheader.addElement("username").addText(glSettingInfo.getGlUserName());
				//msgheader.addElement("password").addText(glSettingInfo.getGlPassWord());
				msgheader.addElement("password").addText("");
				msgheader.addElement("submitdate").addText(this.getDateTime());
				msgheader.addElement("reserve1").addText("");
				msgheader.addElement("reserve2").addText("");
				msgheader.addElement("reserve3").addText("");
				
				Iterator itTemp = collGlVoucher.iterator();
				while (itTemp.hasNext())
				{
					voucherTemp = (GLVoucherInfo) itTemp.next();
					glPostDate = voucherTemp.getPostDate();
					
					//组装凭证
					voucher = gl_voucher.addElement("voucher");
					
					//组装凭证头
					master = voucher.addElement("master");
					master.addElement("id").addText(voucherTemp.getTransNo());
					master.addElement("vchrtypecode").addText(glSettingInfo.getGlVoucherType());
					master.addElement("VCHRNUM").addText(this.getYear());
					master.addElement("acctyear").addText(this.getYear());
					master.addElement("acctperiod").addText(this.getMonth());
					master.addElement("createdate").addText(DataFormat.getDateString(glPostDate));
					master.addElement("unitcode").addText(glSettingInfo.getPk_corp());
					master.addElement("acctbookcode").addText(glSettingInfo.getBranChcode());
					master.addElement("createuser").addText(glSettingInfo.getGlUserName());
					master.addElement("attachment").addText("0");
					master.addElement("memo").addText(memo);
					
					items = voucher.addElement("items");
					for (int i = 0; voucherTemp != null && i < voucherTemp.getList().size(); i++)
	                {
						debit = "0.00";
						credit = "0.00";
						
						gLEntryInfo = (GLEntryInfo)voucherTemp.getList().get(i);
						item = items.addElement("item");
				    	item.addElement("id").addText(gLEntryInfo.getID()+"");
				    	item.addElement("vchrid").addText(gLEntryInfo.getTransNo());
				    	item.addElement("itemorder").addText(i+1+"");
				    	item.addElement("subjectcode").addText(gLEntryInfo.getSubject());
				    	if (gLEntryInfo.getDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
						{
				    		debit = DataFormat.formatAmount(gLEntryInfo.getAmount());
						}
						else if (gLEntryInfo.getDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
						{
				    		credit = DataFormat.formatAmount(gLEntryInfo.getAmount());
						}
				    	item.addElement("debit").addText(debit);
				    	item.addElement("credit").addText(credit);
				    	item.addElement("qty").addText("0");
				    	item.addElement("price").addText("0");
				    	item.addElement("currencycode").addText(CurrencyType.getCode(gLEntryInfo.getCurrencyID()));
				    	item.addElement("exchrate").addText(rate);
				    	item.addElement("orgnd").addText(debit);
				    	item.addElement("orgnc").addText(credit);
				    	item.addElement("settlementcode").addText("");
				    	item.addElement("settlementno").addText("");
				    	item.addElement("bizdate").addText(DataFormat.getDateString(glPostDate));
				    	item.addElement("digest").addText(this.getAbstract(gLEntryInfo, voucherTemp));
				    	item.addElement("bizno").addText("");
				    	item.addElement("suppliercode").addText("");
				    	item.addElement("departmentcode").addText("");
				    	item.addElement("customercode").addText(map.get(gLEntryInfo.getAssitantValue())==null?gLEntryInfo.getAssitantValue():map.get(gLEntryInfo.getAssitantValue()));
				    	item.addElement("staffcode").addText("");
	                }
				}
				System.out.println("发送开始>>>");
				System.out.println(formatXml(document));
				System.out.println("发送结束>>>");
				
				requestXML = document.asXML();
				
				//this.writeFile(formatXml(document), "request_Voucher");
			}
		}catch(Exception e){
			e.printStackTrace();
			new Exception("组装凭证报文失败："+e.getMessage());
		}
		return requestXML;
	}
	
	/**
	 * 根据响应报文返回信息
	 * @param responseXML
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	public ArrayList<GLVoucherInfo> getVoucherResult(String responseXML) throws Exception{
		
		ArrayList<GLVoucherInfo> result = new ArrayList<GLVoucherInfo>();
		String sTransNo = null;
		String sPostStatusID = null;
		String responsemessage = null;
		GLVoucherInfo info = null;
		GLEntryInfo gLEntryInfo = new GLEntryInfo();
		gLEntryInfo.setOfficeID(1);
		gLEntryInfo.setCurrencyID(1);
		StringBuffer strBuffer = new StringBuffer();
		
		System.out.println("接受开始>>>");
		System.out.println(responseXML);
		System.out.println("接受结束>>>");
		
		//this.writeFile(responseXML, "response_Voucher");
		
		Document doc;
		Element e = null;
		Iterator iter = null;
		try {
			doc = (Document) DocumentHelper.parseText(responseXML);
			List msg = doc.selectNodes("/gl_voucher/response");
			if(msg.isEmpty()){
				msg = doc.selectNodes("/response");
				iter = msg.iterator();
				while(iter.hasNext()){
					e = (Element)iter.next();
					sTransNo = e.elementText("id").trim();
					sPostStatusID = e.elementText("responseflag").trim();
					responsemessage = e.elementText("responsemessage").trim();
					if(sPostStatusID.equals("0")){
						strBuffer.append("交易号："+sTransNo+"倒帐失败，原因："+responsemessage+";");
					}
					info = new GLVoucherInfo();
					info.setTransNo(sTransNo);
					info.setPostStatusID((Long.valueOf(sPostStatusID).longValue() == 1) ? Constant.GLPostStatus.SUCCESS : Constant.GLPostStatus.FAILED);
					info.addEntryInfo(gLEntryInfo);
					result.add(info);
				}
			}else{
				iter = msg.iterator();
				while(iter.hasNext()){
					e = (Element)iter.next();
					sTransNo = e.elementText("id").trim();
					sPostStatusID = e.elementText("responseflag").trim();
					responsemessage = e.elementText("responsemessage").trim();
					if(sPostStatusID.equals("0")){
						strBuffer.append("交易号："+sTransNo+"倒帐失败，原因："+responsemessage+";");
					}
					info = new GLVoucherInfo();
					info.setTransNo(sTransNo);
					info.setPostStatusID((Long.valueOf(sPostStatusID).longValue() == 1) ? Constant.GLPostStatus.SUCCESS : Constant.GLPostStatus.FAILED);
					info.addEntryInfo(gLEntryInfo);
					result.add(info);
				}
			}
			new SettGLWithinDao().updatePostStatus(result);		//更新总账
			if(strBuffer.toString().length()>0){
				throw new IException(strBuffer.toString());	//统一抛出失败信息
			}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new Exception("解析报文出错："+e1.getMessage());
		}catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			throw new IException(e2.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取总账接口设置参数
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param lType
	 * @return
	 * @throws Exception
	 */
	public GlSettingInfo getGlSettingInfo(long lOfficeID,long lCurrencyID,long lType) throws Exception
	{
		GlSettingInfo info = new GlSettingInfo();
		Connection con = null;
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try
		{		
			con=Database.getConnection();		
			String strSQL = "select * from SETT_GLSETTING where OfficeID=? and CurrencyID=? and nStatusID !=0 ";
			if(lType==0){		//导入
				strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
			    strSQL +="," + Constant.GLOperationType.ImportOnly + ")";
			}else if(lType==1){		//导出
				strSQL +=" and glOperationType in (" + Constant.GLOperationType.ImportAndExport;
			    strSQL +="," + Constant.GLOperationType.ExportOnly + ")";
			 }
			  
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
     * 得到ORACLE数据库连接
     * @param lOfficeID
     * @param lCurrencyID
     * @param lType
     * @return
     * @throws Exception
     */
    public Connection get_jdbc_connection(long lOfficeID,long lCurrencyID,long lType) throws Exception
    {
        Connection conn = null;
        try
        {
        	//得到配置信息
			GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = this.getGlSettingInfo(lOfficeID, lCurrencyID,lType);
		
			String DB_IP = glSettingInfo.getGlDBIP(); // IP
			String DB_SID = glSettingInfo.getGlDBDatabaseName(); // 库名称
			String DB_USERNAME = glSettingInfo.getGlDBUserName(); // 用户名
			String DB_PASSWORD = DataFormat.formatNullString(glSettingInfo.getGlDBPassWord()); // 密码
			String DB_PORT = glSettingInfo.getGlDBPort(); // 端口

			String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
			
			Log.print("dbURL = " + dbURL);
            Log.print("DB_USERNAME = " + DB_USERNAME);
            Log.print("DB_PASSWORD = " + DB_PASSWORD);
            
			Class.forName(jdbcDriver).newInstance();
			conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
        }
        catch (Exception e)
        {
            Log.print("connect db failed by oracle jdbc driver. " + e.toString());
            throw new Exception(e.getMessage());
        }
        return conn;
    }
    
    /**
	 * 写入文件
	 * @param data
	 * @param fileName
	 * @throws Exception 
	 */
	public void writeFile(String data,String fileName) throws Exception{
		try {
			FileWriter output;
			output = new FileWriter("/"+fileName+"_"+SystemDate+".xml");
			output.write(data);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("写入文件失败："+e.getMessage());
		}
	}
	
	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public String getDateTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(new Date()).toString();
	}
	
	/**
	 * 根据日期得到会计期间 MM
	 * @return
	 */
	public String getMonth(){
		String month = "";
		if(SystemDate.length()>0){
			month = String.valueOf(Long.parseLong(SystemDate.split("-")[1]));
		}
		return month;
	}
	
	/**
	 * 根据日期得到年份 YYYY
	 * @return
	 */
	public String getYear(){
		String year = "";
		if(SystemDate.length()>0){
			year = SystemDate.split("-")[0];
		}
		return year;
	}
	
	/**
	 * 格式化Document
	 * @param document
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String formatXml(Document document) throws DocumentException, IOException{               
        
        //创建输出格式   
        OutputFormat format = OutputFormat.createPrettyPrint();        
        //制定输出xml的编码类型   
        format.setEncoding("gb2312");   
           
        StringWriter writer = new StringWriter();   
        //创建一个文件输出流   
        XMLWriter xmlwriter = new XMLWriter( writer, format );   
        //将格式化后的xml串写入到文件   
        xmlwriter.write(document);    
        String returnValue = writer.toString();   
        writer.close();     
            
        //返回编译后的字符串格式   
        return returnValue;   
    }
	
	/**
	 * 获取摘要
	 * @param gLEntryInfo
	 * @param voucherTemp
	 * @return
	 */
	public String getAbstract(GLEntryInfo gLEntryInfo,GLVoucherInfo voucherTemp){
		String Abstract = "";
		Abstract = "交易类型："+SETTConstant.TransactionType.getName(gLEntryInfo.getTransactionTypeID())+"；";
		Abstract += "交易编号："+voucherTemp.getTransNo()+"；";
		if(gLEntryInfo.getAbstract() == null || gLEntryInfo.getAbstract().equals("")){
			gLEntryInfo.setAbstract("空");
		}
		Abstract += "备注："+gLEntryInfo.getAbstract();
		return Abstract;
	}
	
}
