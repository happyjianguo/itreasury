package com.iss.itreasury.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.closesystem.BeanFactory;
import com.iss.itreasury.dataentity.GLVouchInfo;
import com.iss.itreasury.settlement.consignvoucher.dao.sett_AccountTrustVoucherDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class ExportVouchesServlet extends HttpServlet {
	
	private ServletConfig config;

	public void init(ServletConfig config) throws ServletException
	{
		
		super.init(config);
		this.config = config;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		exportVouches(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		exportVouches(request, response);
	}

	/*public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("ExportVouchesServlet load success!");
	}*/
	
	public void exportVouches(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");
		//定义变量
		String strExecuteDate = "";
		long lCurrencyID = 0;
		long lOfficeID = 0;
		String strFileName = "";
		String strSysDate = "";
		long lModuleID = 0;
		String strTemp = null;	
		Collection collVouches = null;
		
		
		// 定义相对表字段变量
		//	 对应表 sett_glentry
		String strSubjectCode = ""; // 科目编号
		String strTransNO = ""; // 交易编号
		long lTransactionTypeID = -1; // 交易类型
		long lTransDirection = -1; // 交易方向
		double dbMamount = 0.0; // 交易发生额
		Timestamp dtExecute = null; // 执行日期
		Timestamp dtInterestStart = null; // 起息日期
		String strAbstract = ""; // 摘要
		String strMulticode = ""; // 多维码
		long lInputUserID = -1; // 录入人
		long lCheckUserID = -1; // 复核人
		long lStatusID = -1;
		long lGroup = 0;
		long lType = 0;
		long lPostStatusID = -1;

		// 对应表 sett_glsubjectdefinition
		String strSegCode1 = "";
		String strSegCode2 = "";
		String strSegCode3 = "";
		String strSegCode4 = "";
		String strSegCode5 = "";
		String strSegCode6 = "";
		String strSegCode7 = "";
		String strSegName1 = "";
		String strSegName2 = "";
		String strSegName3 = "";
		String strSegName4 = "";
		String strSegName5 = "";
		String strSegName6 = "";
		String strSegName7 = "";
		long lSubjectType = -1; // 科目属性
		long lIsLeaf = -1; // 是否末级科目
		long lIsRoot = -1; // 是否总帐科目
		long lParentSubjectID = -1; // 上级科目代码ID
		long lBalanceDirection = -1; // 控制余额方向
		long lAmountDirection = 9; // 控制发生额方向
		long lStatus = -1; // 状态
		long lLedertype = -1; // 帐类
		long lSecurityLevel = -1; // 安全级别
		long lUseScope = -1; // 使用范围
		long lFlag = -1; // 保留
		Timestamp dtValidDate = null; // 生效日期

		double entered_dr = 0.00; // 原币借方
		double entered_cr = 0.00; // 原币贷方
		double accounted_dr = 0.00; // 本位币借方
		double accounted_cr = 0.00; // 本位币贷方
		String strSeg3 = "";		// 一二级科目
		String strSeg4 = "";		// 三四级科目
		String strSeg5 = "";		//参考段
		
		String space = ",";
		String newLine = "\r\n";

		//获取参数
		strTemp = request.getParameter("exportDate");
		if(strTemp != null || strTemp != "") {
			strExecuteDate = strTemp;
		}
		System.out.println("ExportVouchesServlet strExecuteDate = " + strExecuteDate);
		
		strTemp = request.getParameter("currencyID");
		if(strTemp != null || strTemp != "") {
			lCurrencyID = Long.parseLong(strTemp);
		}
		System.out.println("ExportVouchesServlet lCurrencyID = " + lCurrencyID);
		
		strTemp = request.getParameter("officeID");
		if(strTemp != null || strTemp != "") {
			lOfficeID = Long.parseLong(strTemp);
		}
		System.out.println("ExportVouchesServlet lOfficeID = " + lOfficeID);
		
		strTemp = request.getParameter("fileName");
		if(strTemp != null || strTemp != "") {
			strFileName = strTemp;
		}
		System.out.println("ExportVouchesServlet strFileName = " + strFileName);
		
		strTemp = request.getParameter("moduleID");
		if(strTemp != null || strTemp != "") {
			lModuleID = Long.parseLong(strTemp);
		}
		System.out.println("ExportVouchesServlet lModuleID = " + lModuleID);
		
		strSysDate = DataFormat.formatDate(Env.getSystemDate(lOfficeID,lCurrencyID));
		System.out.println("ExportVouchesServlet strSysDate = " + strSysDate);
		
		//获取系统时间年月日
		String strSysYear	= strSysDate.substring(0,4);
		String strSysMonth	= strSysDate.substring(5,7);
		String strSysDay	= strSysDate.substring(8,10);
		System.out.println(" 系统日期是："+strSysYear+"年"+strSysMonth+"月"+strSysDay+"日");
		
		//获取查询时间年月日
		String strYear	= strExecuteDate.substring(0,4);
		String strMonth	= strExecuteDate.substring(5,7);
		String strDay	= strExecuteDate.substring(8,10);
		System.out.println(" 查询日期是："+strYear+"年"+strMonth+"月"+strDay+"日");
		
		String[] strTitles = new String[]{	"attribute10",	//单位编号  固定值（南航）70021
											"group_id",		//组ID 在南航客户实施时添加 2007-9-25
											"ref1",			// 批名 / 总序   财务公司资金结算系统数据导入[年月日]
											"ref4",			//凭证类型：固定值 转帐
											"ref10",		//分录行说明 即摘要 
											"Rref1",		//批名 和 ref1一样  财务公司资金结算系统数据导入[年月日]
											"ref21",		//	为空
											"Rref4",		//和ref10一样 
											"ref22",		//批描述 取生成批时间	财务公司资金结算系统数据导入[年月日]
											"batch_acct_date",	//批的会计区间		如是当前日期则是关机日期 如是历史则取执行日期
											"header_acct_date",	//凭证的会计区间	如是当前日期则是关机日期 如是历史则取执行日期
											"seg1",			//公司段	固定值70021
											"seg2",			//成本中心段	固定值 210000
											"seg3",			//一二级科目 取一二级	
											"seg4",			//三四级科目 取三四级 如无补为000000
											"seg5",			//参考段 取参考段 如无补为000000000
											"seg6",			//项目段 固定值 0
											"seg7",			//备用段 固定值 0
											"currency_code",	//币种	固定值 RMB
											"currency_convesion_rate",	// 汇率 固定值 1
											"entered_dr",	//原方借方
											"entered_cr",	//原方贷方
											"accounted_dr",	//本位币借方
											"accounted_cr",	//本位币贷方
											"lines_description",	//日记帐行描述 即摘要
											"set_of_books_id",	//帐簿 固定值 75
											"source_name",		//来源 固定值 财务公司资金结算系统
											"category_name"		};	//类别 固定值  转帐
		
		try {
			ServletContext sc = getServletContext();
			String mimeType = sc.getMimeType(strFileName);
			if (mimeType == null) {
	          	   mimeType="application/any";
	        }
			response.setHeader("Content-Disposition","attachment; filename=\"" + toUtf8String(strFileName)+"\"");
			
			response.setContentType(mimeType); 		
			PrintWriter out = response.getWriter();
			String strPrint = "";
			
			for(int nI = 0; nI < strTitles.length;nI++) {
				if(nI == (strTitles.length - 1) ) {
					strPrint+=strTitles[nI];
				} else {
					strPrint+=strTitles[nI] + space;
				}
			}
			//out.print(strPrint+newLine);		// 打印标题
			
			collVouches = BeanFactory.getGLWithinBean(lModuleID).getVouches(strExecuteDate,lOfficeID,lCurrencyID);
			System.out.println("ExportVouchesServlet vouches is null = " + (collVouches == null));
			Iterator it = null;
			if(collVouches != null)
				it = collVouches.iterator();
			if(it != null) {
				while(it.hasNext()) 
				{		
					strPrint = "";
					entered_dr = 0.00; // 原币借方		
					entered_cr = 0.00; // 原币贷方
					accounted_dr = 0.00; // 本位币借方
					accounted_cr = 0.00; // 本位币贷方
					
					GLVouchInfo vouch = (GLVouchInfo)it.next();
					System.out.println(" vouch = " + vouch);
					lOfficeID = vouch.getLOfficeID();
					lCurrencyID = vouch.getLCurrencyID();
					strSubjectCode = vouch.getStrSubjectCode();	// 科目编号
					strTransNO = vouch.getStrTransNO();			// 交易编号
					lTransactionTypeID = vouch.getLTransactionTypeID();
					lTransDirection = vouch.getLTransDirection();
					dbMamount = vouch.getDbMamount();
					if(lTransDirection == SETTConstant.DebitOrCredit.DEBIT)
					{
						entered_dr = dbMamount;
						accounted_dr = dbMamount;
					}
					else
					{
						entered_cr = dbMamount;
						accounted_cr = dbMamount;
					}
					dtExecute = vouch.getDtExecute();
					dtInterestStart = vouch.getDtInterestStart();
					strAbstract = vouch.getStrAbstract();
					strMulticode = vouch.getStrMulticode();
					lInputUserID = vouch.getLInputUserID();
					lCheckUserID = vouch.getLCheckUserID();
					lStatusID = vouch.getLStatusID();
					lGroup = vouch.getLGroup();
					lType = vouch.getLType();
					lPostStatusID = vouch.getLPostStatusID();
					strSegCode1 = vouch.getStrSegCode1();
					strSegCode2 = vouch.getStrSegCode2();		//	科目编号
					//strSegCode2 = "1001001222333";
					/*if(strSegCode2.length() == 7)		// 无三四级科目 和 参考段
					{
						strSeg3 = strSegCode2;
						strSeg4 = "";
						strSeg5 = "";
					}
					else if(strSegCode2.length() == 13)	// 无参考段 有一二三四级科目
					{
						strSeg3 = strSegCode2.substring(0,7);
						strSeg4 = strSegCode2.substring(7,13);
						strSeg5 = "";
					}
					else if(strSegCode2.length() > 13)	// 有参考段 一二三四级科目
					{
						strSeg3 = strSegCode2.substring(0,7);
						strSeg4 = strSegCode2.substring(7,13);
						strSeg5 = strSegCode2.substring(13,strSegCode2.length());
					}*/
					strSegCode3 = vouch.getStrSegCode3();
					strSegCode4 = vouch.getStrSegCode4();
					strSegCode5 = vouch.getStrSegCode5();
					strSegCode6 = vouch.getStrSegCode6();
					strSegCode7 = vouch.getStrSegCode7();
					strSegName1 = vouch.getStrSegName1();
					strSegName2 = vouch.getStrSegName2();		//	科目名称
					strSegName3 = vouch.getStrSegName3();
					strSegName4 = vouch.getStrSegName4();
					strSegName5 = vouch.getStrSegName5();
					strSegName6 = vouch.getStrSegName6();
					strSegName7 = vouch.getStrSegName7();
					lSubjectType = vouch.getLSubjectType();
					lIsLeaf = vouch.getLIsLeaf();
					lIsRoot = vouch.getLIsRoot();
					lParentSubjectID = vouch.getLParentSubjectID();
					lBalanceDirection = vouch.getLBalanceDirection();
					lAmountDirection = vouch.getLAmountDirection();
					lStatus = vouch.getLStatus();
					lLedertype = vouch.getLLedertype();
					lSecurityLevel = vouch.getLSecurityLevel();
					lUseScope = vouch.getLUseScope();
					lFlag = vouch.getLFlag();
					dtValidDate = vouch.getDtValidDate();
					
					//strValues[]  必须和上面定义的 strTitles[] 相对应
					String[] strValues = new String[]{	"70021",							//单位编号  固定值（南航）70021
														strYear+strMonth+strDay,	//组ID 在南航客户实施时添加  2007年9月25日
														"财务公司结算数据"+strYear+strMonth+strDay,					
																							// 批名 / 总序
																							//格式 财务公司资金结算系统数据导入[年月日]
														"转帐",								//凭证类型：固定值 转帐
														strAbstract!=null?strAbstract:"为空",	//分录行说明 即摘要
														"财务公司结算数据"+strYear+strMonth+strDay,				
																							//批名 和 ref1一样
														"",									//	为空
														strAbstract!=null?strAbstract:"",	//和ref10一样 
														"财务公司结算数据"+strYear+strMonth+strDay,	
																							//批描述格式 财务公司资金结算系统数据导入[年月日]
														strMonth+"-"+strYear,		//批的会计区间 如是当前日期则是关机日期 如是历史则取执行日期
														strMonth+"-"+strYear,				//凭证的会计区间 与批的会计区间一样
														strSegCode1!=null&&strSegCode1!=""?strSegCode1:"70021",		//公司段	固定值 70021
														strSegCode2!=null&&strSegCode2!=""?strSegCode2:"210000",		//成本中心段	固定值 210000
														strSegCode3!=null&&strSegCode3!=""?strSegCode3:"0000000",	//一二级科目 取二级
														strSegCode4!=null&&strSegCode4!=""?strSegCode4:"000000",		//三四级科目 取四级
														strSegCode5!=null&&strSegCode5!=""?strSegCode5:"000000000",	//参考段	
														strSegCode6!=null&&strSegCode6!=""?strSegCode6:"000000000",	//项目段 固定值 000000000
														strSegCode7!=null&&strSegCode7!=""?strSegCode7:"0000",			//备用段 固定值 0000
														"RMB",								//币种	固定值 RMB
														"1",								// 汇率 固定值 1
														entered_dr!=0.0?new String().valueOf(entered_dr):"", //原方借方
														entered_cr!=0.0?new String().valueOf(entered_cr):"", //原方贷方
														accounted_dr != 0.0?new String().valueOf(accounted_dr):"", //本位币借方
														accounted_cr!=0.0?new String().valueOf(accounted_cr):"", //本位币贷方
														strAbstract!=null?strAbstract:"为空",				//日记帐行描述 即摘要
														"75",								//帐簿 固定值  75
														"资金结算系统",			//来源 固定值  财务公司资金结算系统
														"转帐"		};			//类别 固定值  转帐 
					for(int nJ = 0; nJ < strValues.length;nJ++){
						if(nJ == (strValues.length - 1) ) {
							strPrint+=strValues[nJ];
						} else {
							strPrint+=strValues[nJ] + space;
						}
					}
					System.out.println("strPrint  = " + strPrint);
					//out.print(new String(strPrint.getBytes("utf-8"),"gb2312")+"\n");		//打印内容
					out.print(strPrint+newLine);		//打印内容
				}
			}
			if(out != null)
				out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     * @param s 原文件名
     * @return 重新编码后的文件名
     */
    protected String toUtf8String(String s) {
	StringBuffer sb = new StringBuffer();
	for (int i=0;i<s.length();i++) {
	    char c = s.charAt(i);
	    if (c >= 0 && c <= 255) {
		sb.append(c);
	    } else {
		byte[] b;
		try {
			Character character = new Character(c);
		    b = character.toString().getBytes("utf-8");
		} catch (Exception ex) {
		    System.out.println(ex);
		    b = new byte[0];
		}
		for (int j = 0; j < b.length; j++) {
		    int k = b[j];
		    if (k < 0) k += 256;
		    sb.append("%" + Integer.toHexString(k).toUpperCase());
		}
	   }
	}
	return sb.toString();
   }
    

    
    
}
