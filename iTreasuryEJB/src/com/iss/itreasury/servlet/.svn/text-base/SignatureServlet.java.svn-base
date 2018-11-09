package com.iss.itreasury.servlet;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.util.SessionMng;
import com.iss.itreasury.util.DataFormat;

public class SignatureServlet extends HttpServlet {
	
	Connection conn = null;
	
	private static HashMap keyMap = new HashMap();

	private String command;

	private String transNo;

	private String signatureID;

	private String signature;

	private String signatures;

	private String strSql;

	private String userName;

	private String extParam;

	private boolean result;

	private java.lang.String keyName; // 文件名

	private java.io.File objFile; // 文件对象

	private java.io.FileReader objFileReader; // 读文件对象

	private char[] chrBuffer; // 缓冲

	private int intLength; // 实际读出的字符数

	private String signatureName; // 印章名称

	private String signatureUnit; // 签章单位

	private String signatureUser; // 持章人

	private String signatureSN; // 签章SN

	private String signatureGUID; // 全球唯一标识符

	private String macIP; // 机器IP

	private String opType; // 操作标志

	private String keySn; // KEY序列号

	private byte[] signatureBody;

	private int signatureSize;
	
	private int printType;

	public SignatureServlet() {
		super();
	}

	public void init() throws ServletException {
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
			try {
				command = request.getParameter("COMMAND");
				System.out.println("=========command==========:"+command);
				userName = DataFormat.toChinese(request.getParameter("USERNAME"));
				System.out.println("=========userName==========:"+userName);
//					extParam = new String(request.getParameter("EXTPARAM").getBytes("8859_1"));
//					System.out.println("=========extParam==========:"+extParam);
//					transNo = extParam.substring(0, extParam.indexOf("|"));
//					String temp = extParam.substring(extParam.indexOf("|")+1);
//					if(temp.indexOf("P")>0){
//						printType = Integer.parseInt(temp.substring(0,temp.indexOf("P")));
//					}else{
//						printType = Integer.parseInt(temp);
//					}
				
				if (command.equalsIgnoreCase("SAVESIGNATURE")) {
					//transNo = new String(request.getParameter("EXTPARAM").getBytes("8859_1"));
					//System.out.println("=========SAVESIGNATURE==========");
					//System.out.println("=========transNo==========:"+transNo);
					//signatureID = new String(request.getParameter("SIGNATUREID").getBytes("8859_1"));
					//System.out.println(signatureID);
					//signature = new String(request.getParameter("SIGNATURE").getBytes("8859_1"));
					//signatureBody = signature.getBytes();
					//signatureSize = signatureBody.length;
					//info.setSignatureID(signatureID);
					//info.setTransNo(transNo);
					//info.setSignatureSize(signatureSize);
					//info.setSignature(signature);
					//info.setCommand(command);
					try{
						//signatureBiz.signature(info);
						System.out.println("======PRO===SAVESIGNATURE==========");
//							SysPrintLoggerInfo logInfo = new SysPrintLoggerInfo();
//							logInfo.setClientName(session.m_strClientName);
//							logInfo.setClientId(session.m_lClientID);
//							logInfo.setCurrencyId(session.m_lCurrencyID);
//							logInfo.setOfficeId(session.m_lOfficeID);
//							logInfo.setPrintDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
//							logInfo.setPrintType(printType);
//							logInfo.setPrintUserId(session.m_lUserID);
//							logInfo.setPrintUserName(session.m_strUserName);
//							logInfo.setRemoteIP(request.getRemoteAddr());
//							logInfo.setTransNo(transNo);
//							
//							SysPrintLogger biz = new SysPrintLogger();
//							biz.addPrintLogger(logInfo);
						System.out.println("======PRO===SAVESIGNATURE=====ADDLOG=====");
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
					
					signatureBody = null;
					out.flush();
//						out.print("SIGNATUREID=" + info.getSignatureID() + "\r\n");
					out.print("RESULT=OK");
				}
				if (command.equalsIgnoreCase("GETNOWTIME")) { // 获取服务器时间
					String mDateTime = new String(request.getParameter("EXTPARAM1").getBytes("8859_1"));
					if(mDateTime.equals("nowtime")){
						java.sql.Date mDate;
						Calendar cal = Calendar.getInstance();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						mDateTime = formatter.format(cal.getTime());
					}
					out.print("NOWTIME=" + mDateTime + "\r\n");
					out.print("RESULT=OK");
				}
				if (command.equalsIgnoreCase("DELESIGNATURE")) { //删除签章数据信息
					transNo = request.getParameter("TRANSNO");
//						signatureID = request.getParameter("SIGNATUREID");
//						info.setTransNo(transNo);
//						info.setSignatureID(signatureID);
//						try{
//							signatureBiz.delSignature(info);
//						}catch(Exception ex){
//							ex.printStackTrace();
//						}
				}

				if (command.equalsIgnoreCase("LOADSIGNATURE")) { //调入签章数据信息
					transNo = request.getParameter("DOCUMENTID");
					signatureID = request.getParameter("SIGNATUREID");
//						SignatureInfo signatureInfo = new SignatureInfo();
//						info.setTransNo(transNo);
//						info.setSignatureID(signatureID);
					try{
//							signatureInfo = signatureBiz.getSignature(info);
						out.flush();
//							out.print(signatureInfo.getSignature() + "\r\n");
						out.print("RESULT=OK");
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}

				if (command.equalsIgnoreCase("SHOWSIGNATURE")) { //获取当前签章SignatureID，调出SignatureID，再自动调LOADSIGNATURE数据
					transNo = request.getParameter("DOCUMENTID");
//						info.setTransNo(transNo);
					signatures = "";
//						Collection signColl = signatureBiz.querySignatureInfo(info);
//						if(signColl!=null&&signColl.size() > 0)
//						{
//							Iterator iterator = signColl.iterator();
//							while(iterator.hasNext()){
//								SignatureInfo signatureInfo = (SignatureInfo)iterator.next();
//								signatures = signatures+signatureInfo.getSignatureID();
//							}
//						}
						out.flush();
						out.print("SIGNATURES=" + signatures + "\r\n");
						out.print("RESULT=OK");
				}

				// ---------------------------------------------------------------------------------------

				if (command.equalsIgnoreCase("PUTSIGNATUREDATA")) { // 批量签章时，写入签章数据
					transNo = new String(request.getParameter("TRANSNO").getBytes("8859_1"));
					signatureID = new String(request.getParameter("SIGNATUREID").getBytes("8859_1"));
					System.out.println(signatureID);
					signature = new String(request.getParameter("SIGNATURE").getBytes("8859_1"));
					signatureBody = signature.getBytes();
					signatureSize = signatureBody.length;
//						info.setSignatureID(signatureID);
//						info.setTransNo(transNo);
//						info.setSignatureSize(signatureSize);
//						info.setSignature(signature);
//						info.setCommand(command);
//						try{
//							signatureBiz.signature(info);
//						}catch(Exception ex){
//							ex.printStackTrace();
//						}
					signatureBody = null;
				}

				// ---------------------------------------------------------------------------------------

				if (command.equalsIgnoreCase("SIGNATUREKEY")) {
					System.out.println("签章文件的文件名称是=======:"+userName);
					try {
						char[] keyChars = null;
						
						if(keyMap.get(userName) != null){
							keyChars = (char[])keyMap.get(userName);
							out.write(keyChars, 0, keyChars.length);
							out.write("\r\n");
							out.write("RESULT=OK");
						}else{
							String RealPath = userName+"//"+userName+".key";
							
							
							keyName = request.getRealPath(RealPath);
							
							System.out.println("签章文件的路径是==========："+keyName);
							
							CharArrayWriter content = new CharArrayWriter();
		
							objFile = new java.io.File(keyName); // 创建文件对象
							chrBuffer = new char[10];
							
							if (objFile.exists()) {// 文件存在
								InputStreamReader isr = new InputStreamReader(
										new FileInputStream(keyName));
								while ((intLength = isr.read(chrBuffer)) != -1) { // 读文件内容
									content.write(chrBuffer, 0, intLength);
								}
								keyChars = content.toCharArray();
								keyMap.put(userName, keyChars);	
								out.write(keyChars, 0, keyChars.length);
								out.write("\r\n");
								out.write("RESULT=OK");
								isr.close(); // 关闭读文件对象
							}else {
								RealPath = "signatureName//signatureName.key";
								keyName = request.getRealPath(RealPath);
								
								System.out.println("签章文件的路径是==========："+keyName);				
								objFile = new java.io.File(keyName); // 创建文件对象
								chrBuffer = new char[10];
								if (objFile.exists()) {// 默认文件存在
									InputStreamReader isr = new InputStreamReader(
											new FileInputStream(keyName));
									while ((intLength = isr.read(chrBuffer)) != -1) { // 读文件内容
										content.write(chrBuffer, 0, intLength);
									}
									keyChars = content.toCharArray();
									keyMap.put(userName, keyChars);	
									out.write(keyChars, 0, keyChars.length);
									out.write("\r\n");
									out.write("RESULT=OK");
									isr.close(); // 关闭读文件对象
								}else
									out.println("key File Not Found" + keyName); // 文件和默认文件都不存在
							}
									
						}
					}
					catch (Exception e) {

						System.out.println(e.toString());
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
//		out.flush();
//		out.print("SIGNATUREID= 111\r\n");
//		out.print("RESULT=OK");
//	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
