<%--
 页面名称 ：d004-c.jsp
 页面功能 : 新增贴现申请-保存贴现申请 保存借款单位资料 控制页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>		
<%@ include file="/common/common.jsp" %>		
<%
  try
  {
		Log.print("*******进入页面--ebank/loan/discountapply/d004-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//定义变量
        String strTemp = "";
		
		 //ClientID标识
		 long lClientID = -1;
		//办事处标识 也是财务公司标识
		 long lOfficeID = -1;
		//办事处名称 也是财务公司名称
		 String strOfficeName = "";
		//客户编号
		 String strCode = "";
		//客户名称
		 String strName = "";
		//法人代码或营业执照
		 String strLicenceCode = "";
		//企业类型
		 long lCorpNatureID = -1;
		//父公司标识
		 long lParentCorpID = -1;
		//父公司名称
		 String strParentCorpName = "";
		//父公司2标识
		 long lParentCorpID2 = -1;
		//父公司2名称
		 String strParentCorpName2 = "";
		//电子邮件
		 String strEmail = "";
		//地址
		 String strAddress = "";
		//邮编
		 String strZipCode = "";
		//电话
		 String strPhone = "";
		//传真
		 String strFax = "";
		//录入人ID
		 long lInputUserID = -1;
		//录入人姓名
		 String strInputUserName = "";
		//录入时间
		 Timestamp dtInput = null;
		//更新用户ID
		 long lModifyUserID = -1;
		//更新用户
		 String strModifyUserName = "";
		//更新时间
		 Timestamp dtModify  =  null;
		//是否是股东
		 long lIsPartner = -1;
		//财务公司账号
		 String strAccount = "";
		 long lAccountID = -1;
		//开户银行1
		 String strBank1 = "";
		//开户银行2
		 String strBank2 = "";
		//开户银行3
		 String strBank3 = "";
		//省
		 String strProvince = "";
		//市
		 String strCity = "";
		//银行账户1
		 String strBankAccount1 = "";
		//银行账户2
		 String strBankAccount2 = "";
		//银行账户3
		 String strBankAccount3 = "";
		//经济性质
		 String strEconomyType = "";
		//信用等级
		 long lCreditLevelID = -1;
		//联系人
		 String strContacter = "";
		//自营贷款客户分类
		 long lLoanClientTypeID = -1;
		//自营贷款客户分类
		 String strLoanClientType = "";
		//结算客户分类
		 long lSettClientTypeID = -1;
		//结算客户分类
		 String strSettClientType = "";
		//风险级别
		 long lRiskLevelID = -1;
		//手工输入 风险评级
		 String strRiskLevel = ""; //注：华能专用 ***-大桥也是
	     //状态  是否有效
		 long lStatusID = -1;
		//法人代表
		 String strLegalPerson = "";
		//注册资本
		 String strCaptial = "";
		//机组容量
		 String strGeneratorCapacity = "";
		//评级单位
		 String strJudGelevelClient = "";
		//经营范围
		 String strDealScope = "";
		//贷款卡号
		 String strLoanCardNo = "";
		//贷款卡密码
		 String strLoanCardPwd = "";
		//法人代码证号
		 String strLegalPersonCode = "";
		//资质等级
		 String strIntelligenceLevel = "";
		//控股单位
		 long lKGClientID = -1;
		 String strKGClientName = "";
		 float fKGScale  = 0;
		 String strKGCardNo = "";
		 String strKGPwd = "";
		//其他股东1-3
		 long[] lQTClientID = {-1,-1,-1};
		 String[] strQTClientName = new String[3];
		 float[] fQTScale  = new float[3];
		 String[] strQTCardNo = new String[3];
		 String[] strQTPwd = new String[3];
		//分页显示总页数记录
		 long lPageCount = 0;
		//财务情况统计表ID  最近三年
		 long[] lFINANCETJBID = {-1,-1,-1};
		//财务情况统计表名称
		 String[] strFINANCETJBName = {"","",""};
		//财务情况统计表——年份
		 String[] strFINANCETJBYear = {"","",""};		
          
		//获取clientinfo需要的信息进行保存
		
	    strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientID = Long.valueOf(strTemp).longValue();
		}
		Log.print("=================lClientID:"+lClientID);
		
		strTemp = (String)request.getAttribute("lOfficeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lOfficeID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strOfficeName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strOfficeName = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strCode");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCode = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strName = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strLicenceCode");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strLicenceCode = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lCorpNatureID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCorpNatureID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("lParentCorpID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lParentCorpID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strParentCorpName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strParentCorpName = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lParentCorpID2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lParentCorpID2 = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strParentCorpName2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strParentCorpName2 = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strEmail");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strEmail = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strAddress");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strAddress = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strZipCode");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strZipCode = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strPhone");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPhone = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strFax");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strFax = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lInputUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lInputUserID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strInputUserName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strInputUserName = strTemp.trim();
		}
	
		strTemp = (String)request.getAttribute("dtInput");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dtInput =  DataFormat.getDateTime(strTemp);
		}
		
		strTemp = (String)request.getAttribute("lModifyUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lModifyUserID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strModifyUserName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strModifyUserName = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("dtModify");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dtModify =  DataFormat.getDateTime(strTemp);
		}
		
		strTemp = (String)request.getAttribute("lIsPartner");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lIsPartner = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strAccountCtrl1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strAccount = strTemp.trim();
		}
	/*	strTemp = (String)request.getAttribute("strAccount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strAccount = strTemp.trim();
		}*/
		strTemp = (String)request.getAttribute("strBank1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBank1 = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strBank2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBank2 = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strBank3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBank3 = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strProvince");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strProvince = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strCity");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCity = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strBankAccount1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBankAccount1 = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strBankAccount2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBankAccount2 = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strBankAccount3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBankAccount3 = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strEconomyType");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strEconomyType = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lCreditLevelID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCreditLevelID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strContacter");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strContacter = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lLoanClientTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanClientTypeID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strLoanClientType");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strLoanClientType = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lSettClientTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSettClientTypeID = Long.valueOf(strTemp).longValue();
		}
	
	    strTemp = (String)request.getAttribute("strSettClientType");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strSettClientType = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lRiskLevelID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRiskLevelID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strRiskLevel");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strRiskLevel = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lStatusID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strLegalPerson");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strLegalPerson = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strCaptial");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCaptial = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strGeneratorCapacity");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strGeneratorCapacity = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strJudGelevelClient");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strJudGelevelClient = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strDealScope");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strDealScope = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strLoanCardNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strLoanCardNo = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strLoanCardPwd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strLoanCardPwd = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strLegalPersonCode");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strLegalPersonCode = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strIntelligenceLevel");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strIntelligenceLevel = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lKGClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lKGClientID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strKGClientName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strKGClientName = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("fKGScale");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    fKGScale = Float.parseFloat(strTemp.trim());
		}
		
		strTemp = (String)request.getAttribute("strKGCardNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strKGCardNo = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strKGPwd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strKGPwd = strTemp.trim();
		}
		
		//其他股东1-3
		strTemp = (String)request.getAttribute("lQTClientID1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lQTClientID[0] = Long.valueOf(strTemp).longValue();
		}
		 
		strTemp = (String)request.getAttribute("lQTClientID2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lQTClientID[1] = Long.valueOf(strTemp).longValue();
		} 
		
		strTemp = (String)request.getAttribute("lQTClientID3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lQTClientID[2] = Long.valueOf(strTemp).longValue();
		} 
		
		strTemp = (String)request.getAttribute("strQTClientName1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTClientName[0] = strTemp.trim();
		}
		 
		strTemp = (String)request.getAttribute("strQTClientName2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTClientName[1] = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strQTClientName3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTClientName[2] = strTemp.trim();
		}
		 
		strTemp = (String)request.getAttribute("fQTScale1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    fQTScale[0] = Float.parseFloat(strTemp.trim());
		}
		
		strTemp = (String)request.getAttribute("fQTScale2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    fQTScale[1] = Float.parseFloat(strTemp.trim());
		}
		
		strTemp = (String)request.getAttribute("fQTScale3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    fQTScale[2] = Float.parseFloat(strTemp.trim());
		}
		
		strTemp = (String)request.getAttribute("strQTCardNo1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTCardNo[0] = strTemp.trim();
		} 
		
		strTemp = (String)request.getAttribute("strQTCardNo2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTCardNo[1] = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strQTCardNo3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTCardNo[2] = strTemp.trim();
		}
		 
		strTemp = (String)request.getAttribute("strQTPwd1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTPwd[0] = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strQTPwd2");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTPwd[1] = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strQTPwd3");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strQTPwd[2] = strTemp.trim();
		}
		
		ClientInfo clientInfo = new ClientInfo();
		
		 clientInfo.setClientID(lClientID);
		 clientInfo.setCode(strCode);
		 clientInfo.setName(strName);
		 clientInfo.setLicenceCode(strLicenceCode);
		 clientInfo.setCorpNatureID(lCorpNatureID);
		 clientInfo.setParentCorpID(lParentCorpID);
		 clientInfo.setParentCorpName(strParentCorpName);
		 clientInfo.setParentCorpID2(lParentCorpID2);
		 clientInfo.setParentCorpName2(strParentCorpName2);
		 clientInfo.setEmail(strEmail);
		 clientInfo.setAddress(strAddress);
		 clientInfo.setZipCode(strZipCode);
		 clientInfo.setPhone(strPhone);
		 clientInfo.setFax(strFax);
		 clientInfo.setInputUserID(lInputUserID);
		 clientInfo.setInputUserName(strInputUserName);
		 clientInfo.setInput(dtInput);
		 clientInfo.setModifyUserID(lModifyUserID);
		 clientInfo.setModifyUserName(strModifyUserName);
		 clientInfo.setModify(dtModify);
		 clientInfo.setIsPartner(lIsPartner);
		 clientInfo.setAccount(strAccount);
		 //clientInfo.setAccountID(lAccountID);
		 clientInfo.setBank1(strBank1);
		 clientInfo.setBank2(strBank2);
		 clientInfo.setBank3(strBank3);
		 clientInfo.setProvince(strProvince);
		 clientInfo.setCity(strCity);
		 clientInfo.setBankAccount1(strBankAccount1);
		 clientInfo.setBankAccount2(strBankAccount2);
		 clientInfo.setBankAccount3(strBankAccount3);
		 clientInfo.setEconomyType(strEconomyType);
		 clientInfo.setCreditLevelID(lCreditLevelID);
		 clientInfo.setContacter(strContacter);
		 clientInfo.setLoanClientTypeID(lLoanClientTypeID);
		 clientInfo.setLoanClientType(strLoanClientType);
		 clientInfo.setSettClientTypeID(lSettClientTypeID);
		 clientInfo.setSettClientType(strSettClientType);
		 clientInfo.setRiskLevelID(lRiskLevelID);
		 clientInfo.setRiskLevel(strRiskLevel); //注：华能专用 ***-大桥也是
		 clientInfo.setStatusID(lStatusID);
		 clientInfo.setLegalPerson(strLegalPerson);
		 clientInfo.setCaptial(strCaptial);
		 clientInfo.setGeneratorCapacity(strGeneratorCapacity);
		 clientInfo.setJudGelevelClient(strJudGelevelClient);
		 clientInfo.setDealScope(strDealScope);
		 clientInfo.setLoanCardNo(strLoanCardNo);
		 clientInfo.setLoanCardPwd(strLoanCardPwd);
		 clientInfo.setLegalPersonCode(strLegalPersonCode);
		 clientInfo.setIntelligenceLevel(strIntelligenceLevel);
		 clientInfo.setKGClientID(lKGClientID);
		 clientInfo.setKGClientName(strKGClientName);
		 clientInfo.setKGScale(fKGScale);
		 clientInfo.setKGCardNo(strKGCardNo);
		 clientInfo.setKGPwd(strKGPwd);
		 clientInfo.setQTClientID(lQTClientID);
		 clientInfo.setQTClientName(strQTClientName);
		 clientInfo.setQTScale(fQTScale);
		 clientInfo.setQTCardNo(strQTCardNo);
		 clientInfo.setQTPwd(strQTPwd);
		 
		 OBSystemHome  obSystemHome = null;
         OBSystem      obSystem = null;
         obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
         obSystem = obSystemHome.create();

		 
		 //用于从提交贴现申请页面点击"单位信息修改"之后的返回
		 String strBackPage = null;
		long lID = -1;//贴现申请id
		
		strTemp = (String)request.getAttribute("strBackPage");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strBackPage = DataFormat.toChinese(strTemp.trim());
			request.setAttribute("strBackPage",strBackPage);
		}
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
			request.setAttribute("lID",lID+"");
		}
			
       // if(obSystem.saveClientInfo(clientInfo) > 0)
	 	//{
		   /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
		   if(lID > 0 && strBackPage != null)
		   {
		     RequestDispatcher rd1 = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d017-c.jsp")));
			 /* forward到结果页面 */
			 rd1.forward(request, response);
		   }
		   else
		   {
	         RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d001-c.jsp")));
			/* forward到结果页面 */
			 rd.forward(request, response);
		   }	 
	       
		//}
		//else
		//{
		 // OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		//  return;
		//}
		
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>



