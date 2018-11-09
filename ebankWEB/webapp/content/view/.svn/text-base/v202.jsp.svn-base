<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contractcontent.dao.ContractContentDao"
%>

<%

	//log4j //log4j = new //log4j(Constant.ModuleType.LOAN);

	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	String strTableTitle = "现金流量表－查询";
	try
	{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
//定义变量
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lUserID = sessionMng.m_lUserID;
		long lIsSM = Constant.YesOrNo.NO;
		long lIsReadOnly = Constant.YesOrNo.YES;
		long lTypeId= -1;
		String sTemp="";
		/////////////////////////////////////
		boolean isRead=false;
		//int index++=0;
		
		String[] arrContent=null;
		arrContent=new String[200];
		String strContent=(String)request.getAttribute("sContent");
   	   //String[] arrContent=new String[64];
   	   if (strContent!=null&&strContent.length() > 0)
	   {
			int nIndex; //","的索引位置
			int nTmp = 0;
			nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
			while (nIndex >= 0)
			{
				arrContent[nTmp] = strContent.substring(0, nIndex);
				strContent = strContent.substring(nIndex + 4);
				nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[nTmp] = strContent;
		}
		

		////////////////////////////////////
		//log4j.info("c211-v----------5-------------------");
		//显示文件头
		 OBHtml.showOBHomeHead(out,sessionMng,"",Constant.YesOrNo.NO);
		int index = 1;
%>


<%
		long lContentID = -1;
		long lClientID = -1;
		String strTemp="";
		String strYear="";
		String strSeason="";
		String strClientName="";

		strTemp = (String)request.getAttribute("strYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//年份
			strYear = strTemp.trim();
			
		}
	
		 strTemp = (String)request.getAttribute("strSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//季度
			strSeason = strTemp.trim();
			
		}
			strTemp = (String)request.getAttribute("hdnstrYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//年份
			strYear = strTemp.trim();
			
		}
	
		 strTemp = (String)request.getAttribute("hdnstrSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//季度
			strSeason = strTemp.trim();
			
		}

		
		strTemp = (String)request.getAttribute("lTypeId");
		if (strTemp != null && strTemp.trim().length() > 0){				//利率类型编号
			lTypeId = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && strTemp.trim().length()>0){				//操作结果
			lClientID = Long.parseLong(strTemp.trim());
		}

		strContent = (String) request.getAttribute("sContent");
		strTemp = (String) request.getAttribute("lContentID");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lContentID = Long.parseLong(strTemp.trim());
		}
		strTemp = (String) request.getAttribute("lClientID");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lClientID = Long.parseLong(strTemp.trim());
		}
		strTemp = (String) request.getAttribute("hdnClientName");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strClientName = strTemp.trim();
		}

		String strControl = "";
		strTemp = (String) request.getAttribute("hdnControl");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strControl = strTemp.trim();
		}
		if("view".equals(strControl))
		{
			isRead=true;
		}

%>


<SCRIPT language="javascript" src="/webloan/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webloan/js/Check.js"></SCRIPT>

<form name="frm" method="post">
	<!--页面控制变量-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	
	<!--重复请求控制-->
	<!--页面控制变量-->
	
	<!--隐含业务数据-->
	<input type=hidden name="hdnTypeId" value="<%=lTypeId%>">
	<input type=hidden name="hdnlClientID" value="<%=lClientID%>">
	<input type=hidden name="hdnstrYear" value="<%=strYear%>">
	<input type=hidden name="hdnstrSeason" value="<%=strSeason%>">
	<input type=hidden name="hdnClientName" value="<%=strClientName%>">
	<input type="hidden" name="hdnControl" value="<%=strControl%>">
	
	<!--隐含业务数据-->
  <TABLE border=0 class=top width="99%"><tr><td>编制单位:</td><td><%=strClientName%> </td><td>年份:</td><td><%=strYear%> </td>
  </tr></TABLE>



<TABLE border=0 class=top width="99%">

  <tr> 
		<td class="FormTitle" height=2  width="100%"><b>现金流量表</b></td>
  </tr>


  <tr>
    <td width="100%">
		<table width="100%" border="0"  class="ItemList" height="102">
      
        <tr>
          <td class=ItemTitle width="60%" height="47" align="center">
            项目</td>
          <td class=ItemTitle width="8%" height="47" align="center">
            行次</td>
          <td class=ItemTitle width="32%" height="47" align="center">
            金额</td>
        </tr>
        <tr>
          <td class=ItemBody  >　一.&nbsp;经营活动产生的现金流量&nbsp;</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>　
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;销售商品,提供劳务收到的现金</td>
          <td class=ItemBody  align="center">1</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;收到的税费返还</td>
          <td class=ItemBody  align="center">3</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;收到的其他与经营活动有关的现金</td>
          <td class=ItemBody   align="center">8</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  align="center">现金流入小计</td>
          <td class=ItemBody   align="center">9</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;购买商品,接受劳务支付的现金</td>
          <td class=ItemBody   align="center">10</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;支付给职工以及为职工支付的现金</td>
          <td class=ItemBody   align="center">12</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;支付的各项税费</td>
          <td class=ItemBody   align="center">13</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;支付的其他与经营活动有关的现金</td>
          <td class=ItemBody   align="center">18</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">现金流入小计</td>
          <td class=ItemBody align="center">20</td>
          <td class=ItemBody align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;经营活动产生的现金流量净额</td>
          <td class=ItemBody   align="center">21</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　二.&nbsp;投资活动产生的现金流量:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>　
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;收回投资所收到的现金</td>
          <td class=ItemBody   align="center">22</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;取得投资收益所收到的现金</td>
          <td class=ItemBody   align="center">23</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;处置固定资产,无形资产和其它长期资产所收回的现金净额</td>
          <td class=ItemBody   align="center">25</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;收到的其他与投资活动有关的现金</td>
          <td class=ItemBody   align="center">28</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">现金流入小计</td>
          <td class=ItemBody   align="center">29</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;购建固定资产,无形资产和其他长期资产所支付的现金</td>
          <td class=ItemBody   align="center">30</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;投资所支付的现金</td>
          <td class=ItemBody   align="center">31</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;支付的其他于投资活动有关的现金</td>
          <td class=ItemBody   align="center">25</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">现金流出小计</td>
          <td class=ItemBody   align="center">36</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;投资活动产生的现金流量净额</td>
          <td class=ItemBody   align="center">37</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　三.&nbsp;筹资活动产生的现金流量:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;吸收投资所收到的现金</td>
          <td class=ItemBody   align="center">38</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;借款所收到的现金</td>
          <td class=ItemBody   align="center">40</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;收到的其他与筹资活动有关的现金</td>
          <td class=ItemBody   align="center">43</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">现金流入小计</td>
          <td class=ItemBody   align="center">44</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;偿还债务所支付的现金</td>
          <td class=ItemBody  align="center">45</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;分配股利,利润或偿付利息所支付的现金</td>
          <td class=ItemBody   align="center">46</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;支付的其他与筹资活动有关的现金</td>
          <td class=ItemBody  align="center">52</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">现金流出小计</td>
          <td class=ItemBody  align="center">53</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;筹资活动产生的现金流量净额</td>
          <td class=ItemBody   align="center">54</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　四.&nbsp;汇率变动对现金的影响</td>
          <td class=ItemBody   align="center">55</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　五.&nbsp;现金及现金等价物净增加额</td>
          <td class=ItemBody   align="center">56</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
  <tr>
    <td width="100%">
		<table width="100%" border="0"  class="ItemList" height="102">
      
        <tr>
          <td class=ItemTitle width="60%" height="47" align="center">
            补充资料</td>
          <td class=ItemTitle width="8%" height="47" align="center">
            行次</td>
          <td class=ItemTitle width="32%" height="47" align="center">
            金额</td>
        </tr>
        <tr>
          <td class=ItemBody  >　1.&nbsp;将净利润调节为经营活动现金流量:</td>
          <td class=ItemBody  align="center"></td>
          <td class=ItemBody  align="center" height=20>
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;净利润</td>
          <td class=ItemBody   align="center">57</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;如:&nbsp;计提的资产减值准备</td>
          <td class=ItemBody  align="center">58</td>
          <td class=ItemBody  align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;固定资产折旧</td>
          <td class=ItemBody   align="center">59</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无形资产摊消</td>
          <td class=ItemBody   align="center">60</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;长期待摊费用摊销</td>
          <td class=ItemBody   align="center">61</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;待摊费用减少(减:增加)</td>
          <td class=ItemBody   align="center">64</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预提费用增加(减:减少)</td>
          <td class=ItemBody   align="center">65</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;处置固定资产,无形资产和其他长期资产的损失(减:收益)</td>
          <td class=ItemBody   align="center">66</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;固定资产报废损失</td>
          <td class=ItemBody   align="center">67</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;财务费用</td>
          <td class=ItemBody   align="center">68</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投资损失(减:收益)</td>
          <td class=ItemBody   align="center">69</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;递延税款贷项(减:借项)</td>
          <td class=ItemBody   align="center">70</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存货的减少(减:增加)</td>
          <td class=ItemBody   align="center">71</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经营性应收项目的减少(减:增加)</td>
          <td class=ItemBody   align="center">72</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经营性应付项目的增加(减:减少)</td>
          <td class=ItemBody   align="center">73</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他</td>
          <td class=ItemBody   align="center">74</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;经营活动产生的现金流量净额</td>
          <td class=ItemBody   align="center">75</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　2.&nbsp;不涉及现金收支的投资和筹资活动:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;债务转为资本</td>
          <td class=ItemBody   align="center">76</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;一年内到期的可转换公司债券</td>
          <td class=ItemBody   align="center">77</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;融资租人固定资产</td>
          <td class=ItemBody   align="center">78</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　3.&nbsp;现金及现金等价物净增加情况:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>　
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;现金的期末余额</td>
          <td class=ItemBody   align="center">79</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;减:&nbsp;现金的期初余额</td>
          <td class=ItemBody   align="center">80</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;加:&nbsp;现金等价物的期末余额</td>
          <td class=ItemBody   align="center">81</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;减:&nbsp;现金等价物的期初余额</td>
          <td class=ItemBody   align="center">82</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >　&nbsp;&nbsp;&nbsp;&nbsp;现金及现金等价物净增加额</td>
          <td class=ItemBody   align="center">83</td>
          <td class=ItemBody   align="center">　<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='AddSubmit';" >
          </td>
        </tr>
      </table>
    </td>
  </tr>
 
 
		<TR bordercolor="#999999"> 
			<TD align=right>

<% 
	if(!isRead)
	{
%>
				<INPUT onfocus="nextfield=''" class=button name=AddSubmit onclick="FrmSubmit(frm,1)" type=button value=" 保存 "> 

<%
	}
%>
				<INPUT onfocus="nextfield='AddSubmit'" class=button name=backSubmit onclick="FrmSubmit(frm,2)" onKeydown="if(event.keyCode==13) FrmSubmit(frm,2);" type=button value=" 返回 "> 
			</TD>
		</TR>

	    <input type="hidden" name="control" value="">
		<input type="hidden" name="SUBMIT" >
		<INPUT type="hidden" name="lClientID" value="<%=lClientID%>">
		<input type="hidden" name="lContentID" value="<%=lContentID%>">
		<input type="hidden" name="PageName"  value="c211-v.jsp">
		<input type="hidden" name="PageNo"  value="1">
		<input type="hidden" name="lIsReadOnly"  value="<%=Constant.YesOrNo.NO%>">

</table>



</form>


<script language="JavaScript">
		firstFocus(frm.textfield[0]);
		//setSubmitFunction("FrmSubmit(frm,1)");
		setFormName("frm");
</script>


<script language="JavaScript">

function FrmSubmit(frm,nType)
{
	var bSubmit = false;

	if (nType == "1")
	{
		if (confirm("是否保存？"))
		{
			frm.action = "../control/c002.jsp";
			frm.strSuccessPageURL.value = "../view/v004.jsp";
			frm.strFailPageURL.value = "../view/v004.jsp";		
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "save";
			
			frm.submit();
			return true;
		}
		else
		{
			return false;
		}
	}
	else if (nType == "2")
	{
		if (confirm("放弃保存文本信息？"))
		{

			frm.action = "../control/c002.jsp";
			frm.strSuccessPageURL.value = "../view/v004.jsp";
			frm.strFailPageURL.value = "../view/v004.jsp";	
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "";
			
			frm.submit();
			return true;
		}
		else
		{
			return false;
		}
	}

}
</script>








<%
		OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,strTableTitle, Constant.RecordStatus.VALID);
		out.flush();
		return;
	}
%>
