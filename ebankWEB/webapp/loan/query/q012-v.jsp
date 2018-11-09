<%
/**
 * 页面名：q012-v.jsp
 * 页面功能 : 保证资料维护
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.settlement.util.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    try{
    	
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
  		
	    String type=(String)request.getAttribute("lLoanType");
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=LOANConstant.LoanType.getName(loanType);
		boolean	backward=false;
		boolean isCheck=false;
		String action="";
		String tmp="";
		String tempStr="";
    	long tempLong=-1;
    	
    	long[] QTClientID={-1,-1,-1};
	    String[] QTClientName=new String[3];
    	float[] QTScale =new float[3];
    	String[] QTCardNo=new String[3];
    	String[] QTPwd=new String[3];
	
		tmp=(String)request.getAttribute("txtAction");
		if ( (tmp!=null) && ( tmp.length()>0 ) )
			action=tmp;

		long assureTypeID=Long.valueOf((String)request.getAttribute("lAssureTypeID")).longValue();	
		
        ClientInfo cInfo= (ClientInfo) request.getAttribute("ClientInfo");
        AssureInfo assInfo=(AssureInfo)request.getAttribute("AssureInfo");
        
        double  amount=0;
        long assureID=-1;
    	String  impawName="";
    	String  impawQuality="";
    	String  impawStatus="";
    	double  pledgeAmount=0;
    	double  pledgeRate=0;
    	long    statusID=-1;
    	String  assureCode="";
    	double impawAmount=0;
    	
    	if ( assInfo!=null )
    	{
    		assureID=assInfo.getID();
    		amount=assInfo.getAmount();
    		impawName=assInfo.getImpawName();
    		impawQuality=assInfo.getImpawQuality();
    		pledgeAmount=assInfo.getPledgeAmount();
    		pledgeRate=assInfo.getPledgeRate();
    		impawStatus=assInfo.getImpawStatus();
    	}	
    	
    	//显示文件头
   		OBHtml.showOBHomeHead(out,sessionMng,"[保证单位资料]",Constant.YesOrNo.NO);
   		isCheck=true;   
       		
		if ( cInfo!=null )
        {
    		QTClientName=cInfo.getQTClientName();
			QTScale=cInfo.getFQTScale();
			QTCardNo=cInfo.getQTCardNo();
			QTPwd=cInfo.getQTPwd();
		}	
		boolean isdq=false;
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=330 >
  <TR class="tableHeader">
  
      <TD class=FormTitle height=29><B><%=loanTypeName%>——新增</B></TD>
    </TR>
  <TR>
    <TD>
	    <TABLE cellPadding=0 border=0 width=730 >
        <tr style="HEIGHT: 3.5pt">
        	<td width=10>
        	<td width=110>
        	<td width=230>
        	<td width=10>
        	<td width=110>
        	<td width=200>
        	<td width=30>
        </tr>
          <TR> 
            <TD colSpan=7 > <% if ( assureTypeID==LOANConstant.AssureType.ASSURE ) {%> 
              <P class=MsoNormal><U>保证单位资料</U></P>
              <% }else if (assureTypeID==LOANConstant.AssureType.IMPAWN) {%> 
              <P class=MsoNormal><U>质押单位资料</U></P>
              <% }else if (assureTypeID==LOANConstant.AssureType.PLEDGE) {%> 
              <P class=MsoNormal><U>抵押单位资料</U></P>
              <% } %>
			  <br> </TD>
          </TR>
          <TR> 
            <TD colspan=2> 客户编号:</TD>
            <TD  > 
              <INPUT class=box maxlength="50" disabled name="ccode" value="<%=cInfo.getCode()%>">
              <input type="hidden" name="cclientid" value="<%=cInfo.getClientID()%>">
            </TD>
            <TD colspan=2>财务公司:</TD>
            <TD > 
              <INPUT class=box maxlength="50" disabled name="cofficename" value="<%=(cInfo.getOfficeName()==null?"":cInfo.getOfficeName())%>">
              <INPUT class=box maxlength="50" Type="hidden" name="cofficeid" value="<%=cInfo.getOfficeID()%>">
            </TD>
            <TD >&nbsp;</TD>
          </TR>
          <TR style="HEIGHT: 7.5pt"> 
            <TD colspan=2> 
              <P class=MsoNormal >借款单位:</P>
            </TD>
            <TD  colspan="5"> 
              <P class=MsoNormal > 
                <INPUT class=box size="80" disabled name="cname" value="<%=cInfo.getName()%>">
              </P>
            </TD>
          </TR>
          <TR style="HEIGHT: 7.5pt"> 
            <TD colspan=2>营业执照号码:</TD>
            <TD > 
              <INPUT class=box maxlength="50" disabled name="clicencecode" value="<%=((cInfo.getLicenceCode()==null) ? "" : cInfo.getLicenceCode())%>">
            </TD>
            <TD colspan=4> 

            </TD>
          </TR>
          <TR> 
            <TD colSpan=6 height=25 > 
              <HR align=center SIZE=2 width="100%">
            </TD>
            <TD height=25 width=30>&nbsp;</TD>
          </TR>
        </TABLE>
        <table cellpadding=0 width=99% align="center">
          <tr> <%
 		//财务公司放大镜，来自sett_account
 		
 		tempStr=cInfo.getAccount();
 		if (tempStr==null)
 			tempStr="";
 		
		String strMagnifierName2 = "财务公司账号";						//放大镜的名称
		String strFormName2 = "frm";										//主页面表单名称
		String strPrefix2 ="";												////控件名称前缀
		String[] strMainNames2 = {"caccount"};								//放大镜回显栏位值列表
		String[] strMainFields2 = { "sAccountNo"};							//放大镜回显栏位对应的表格字段
		String[] strReturnNames2 = {"caccountid"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields2 = {"ID"};									//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues2=tempStr;								////放大镜回显栏位对应的初始值
		String[] strReturnValues2 = {"-1"};									//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames2 = {"编号","账号"};						//放大镜小窗口显示的栏位名称
		String[] strDisplayFields2 = {"ID","sAccountNO"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex2 = 0; 													//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty2 = "";										//放大镜的对应控件栏位属性
		if ( isCheck ) strMainProperty2+=" disabled ";
		String strMatchValue2="sAccountNO";										////放大镜要模糊匹配的字段
		String strNextControls2 = "cbank1";								//设置下一个焦点
		String strTitle2=" 财务公司账号";
		String strFirstTD2="colspan=2";
		String strSecondTD2="";
	
		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName2,strFormName2,strPrefix2,strMainNames2,strMainFields2,
			strReturnNames2,strReturnFields2, strReturnInitValues2, strReturnValues2,strDisplayNames2,strDisplayFields2,
			intIndex2,strMainProperty2,"getAccount("+strMainNames2[0]+".value)", strMatchValue2,strNextControls2 ,strTitle2, strFirstTD2, strSecondTD2 );	
		%> 
            <td colspan=2><font color="#CC0000">*</font>结算客户分类:</td>
            
            <td>
            <%
				OBHtmlCom.ShowList(out,"lSettClientTypeID","cbank1",cInfo.getSettClientTypeID(),LOANConstant.ListType.SETTCLIENTTYPE,isCheck,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			%></td>
          </tr>
          <tr style="HEIGHT: 23.25pt"> 
            <td colspan=2>开户银行:</td>
            <td> 
              <input name="cbank1" class=box maxlength="50" value="<%=((cInfo.getBank1()==null) ? "" : cInfo.getBank1())%>" onfocus="nextfield='cbankaccount1'" <%if (isCheck) {%>disabled<%}%> >
            </td>
            <td colspan=2>开户银行账号:</td>
            <td> 
              <input name="cbankaccount1" class=box maxlength="50" value="<%=((cInfo.getBankAccount1()==null) ? "" : cInfo.getBankAccount1())%>" onfocus="nextfield='loancardno'" <%if (isCheck) {%>disabled<%}%> >
            </td>
          </tr>
          <tr style="HEIGHT: 23.25pt"> 
            <td colspan=2><font color="#CC0000">*</font>贷款卡号:</td>
            <td> 
              <input name="loancardno" class=box maxlength="50" value="<%=((cInfo.getLoanCardNo()==null) ? "" : cInfo.getLoanCardNo())%>" onfocus="nextfield='loancardpwd'" <%if (isCheck) {%>disabled<%}%> >
            </td>
            <td colspan=2><font color="#CC0000">*</font>贷款卡密码:</td>
            <td> 
              <input name="loancardpwd" class=box class=box maxlength="50" value="<%=((cInfo.getLoanCardPwd()==null) ? "" : cInfo.getLoanCardPwd())%>" onfocus="nextfield='cprovince'" <%if (isCheck) {%>disabled<%}%>>
            </td>
          </tr>
          <tr style='height:56.25pt'> 
            <td colspan=2 > 
              <p class=MsoNormal><font color="#CC0000">*</font> 地址:</p>
            </td>
            <td > 
              <p> 
                <input class=box size="8" name="cprovince" value="<%=((cInfo.getProvince()==null) ? "" : cInfo.getProvince())%>" onfocus="nextfield='ccity'" <%if (isCheck) {%>disabled<%}%>>
                省 
                <input class=box size="8" name="ccity" value="<%=((cInfo.getCity()==null) ? "" : cInfo.getCity())%>" onfocus="nextfield='caddress'" <%if (isCheck) {%>disabled<%}%> >
                市 
                <input class=box size="25" name="caddress" value="<%=((cInfo.getAddress()==null) ? "" : cInfo.getAddress())%>" onfocus="nextfield='cphone'" <%if (isCheck) {%>disabled<%}%>>
              </p>
            </td>
            <td colspan=2> 
              <p class=MsoNormal>电话: <br>
                <br>
                传真:</p>
            </td>
            <td > 
              <p class=MsoNormal> 
                <input class=box size="17" name="cphone" value="<%=((cInfo.getPhone()==null) ? "" : cInfo.getPhone())%>" onfocus="nextfield='cfax'" <%if (isCheck) {%>disabled<%}%>>
                <br>
                <input class=box size="17" name="cfax" value="<%=((cInfo.getFax()==null) ? "" : cInfo.getFax())%>" onfocus="nextfield='clegalperson'" <%if (isCheck) {%>disabled<%}%>>
              </p>
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2>法人代表:</td>
            <td > 
              <input class=box size="20" name="clegalperson" value="<%=((cInfo.getLegalPerson()==null)? "" : cInfo.getLegalPerson())%>" onfocus="nextfield='ccontacter'" <%if (isCheck) {%>disabled<%}%>>
            </td>
            <td colspan=2 >联系人:</td>
            <td > 
              <input class=box size="20" name="ccontacter" value="<%=((cInfo.getContacter()==null) ? "" : cInfo.getContacter())%>"  onfocus="nextfield='czipcode'" <%if (isCheck) {%>disabled<%}%> >
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 > 
              <p class=MsoNormal>邮编:</p>
            </td>
            <td  > 
              <p class=MsoNormal> 
                <input class=box size="6" name="czipcode" value="<%=((cInfo.getZipCode()==null)?"":cInfo.getZipCode())%>" maxlength="6" onfocus="nextfield='cmail'" <%if (isCheck) {%>disabled<%}%> >
              </p>
            </td>
            <td colspan=2 > 
              <p class=MsoNormal>电邮地址:</p>
            </td>
            <td  > 
              <p class=MsoNormal> 
                <input class=box size="20" name="cmail" value="<%=((cInfo.getEmail()==null)?"":cInfo.getEmail())%>" onfocus="nextfield='ccorpnatureid'" <%if (isCheck) {%>disabled<%}%> >
              </p>
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 > 
              <p class=MsoNormal>企业类型:</p>
            </td>
            <td  >
       		 <%
            	long lCorpNatureID=cInfo.getCorpNatureID();
            	OBHtmlCom.showEnterpriseTypeListCtrl(out,"ccorpnatureid",lCorpNatureID,true," onfocus=nextfield='ParentCorpName' ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
            %>           
		   </td>
            <%
 		//上级主管部门放大镜
 		tempLong=cInfo.getParentCorpID();
 		tempStr=cInfo.getParentCorpName();
 		if (tempStr==null)
 			tempStr="";
 		
		String strMagnifierName = "上级主管单位";							//放大镜的名称
		String strFormName = "frm";											//主页面表单名称
		String strPrefix ="";												////控件名称前缀
		String[] strMainNames = {"ParentCorpName"};							//放大镜回显栏位值列表
		String[] strMainFields = { "sName"};								//放大镜回显栏位对应的表格字段
		String[] strReturnNames = {"cparentcorpid"};						//放大镜返回值列表(隐含值)
		String[] strReturnFields = {"ID"};									//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues=tempStr;						////放大镜回显栏位对应的初始值
		String[] strReturnValues = {""+tempLong};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames = {"客户编号","客户名称"};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields = {"sCode","sName"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty = "";									//放大镜的对应控件栏位属性
		if ( isCheck ) strMainProperty+=" disabled ";
		String strMatchValue="sCode";									////放大镜要模糊匹配的字段
		String strNextControls = "isShareHolder";								//设置下一个焦点
		String strTitle="上级主管部门";
		String strFirstTD=" colspan=2 ";
		String strSecondTD="  ";

		OBMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,strPrefix,strMainNames,strMainFields,
			strReturnNames,strReturnFields, strReturnInitValues, strReturnValues,strDisplayNames,strDisplayFields,
			intIndex,strMainProperty,"getClient("+strMainNames[0]+".value)", strMatchValue,strNextControls ,strTitle, strFirstTD, strSecondTD );			

		%> </tr>
          <tr> 
            <td colspan=2 height="32"> 
              <p class=MsoNormal>股东: </p>
            </td>
            <td  height="32"  > 
              <p class=MsoNormal> 
                <select class='box' name="isShareHolder" onfocus="nextfield='lClientTypeID'" <%if (isCheck) {%>disabled<%}%> >
                  <option value="1" <%if (cInfo.getIsPartner()==1) {%> selected <% } %> >是 
                  </option>
                  <option value="2" <%if (cInfo.getIsPartner()==2) {%> selected <% } %>> 
                  否</option>
                </select>
              </p>
            </td>
            <td colspan=2 height="32"><font color="#CC0000">*</font>自营贷款客户分类:</td>
            <td  height="32"> 
            <%
				OBHtmlCom.ShowList(out,"lClientTypeID","lCreditLevel",cInfo.getLoanClientTypeID(),LOANConstant.ListType.LOANCLIENTTYPE,isCheck,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			%>
             
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 height="32">信用等级:</td>
            <td  height="32"> 
              <select name="lCreditLevel" onFocus="nextfield='crisklevel'" <%if (isCheck) {%>disabled<%}%>>
                <option value="-1"></option>
                <%
              	long credVal[]=LOANConstant.CreditLevel.getAllCode();
              	String credName="";
              	for ( int i=0;i<credVal.length;i++ )
              	{
              		credName=LOANConstant.CreditLevel.getName(credVal[i]);
              		if ( credVal[i]<=6 )
              		{
              %> 
                <option value="<%=credVal[i]%>" <% if (credVal[i]==cInfo.getCreditLevelID() ) {%> selected <% } %>><%=credName%></option>
                <%	
              		}	
              	}
              %> 
              </select>
            </td>
            <td colspan=2 height="32">风险评级:</td>
            <td  height="32"> 
              <input type="text" name="crisklevel" size="18" class="box" value="<%=((cInfo.getRiskLevel()==null)?"":cInfo.getRiskLevel())%>" onFocus="nextfield='cjudgelevel'" <%if (isCheck) {%>disabled<%}%>>
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 height="32">评级单位:</td>
            <td  height="32"> 
              <input type="text" name="cjudgelevel" size="18" class="box" value="<%=((cInfo.getJudGelevelClient()==null)?Env.getClientName():cInfo.getJudGelevelClient())%>" onFocus="nextfield='cgenerator'" <%if (isCheck) {%>disabled<%}%> >
            </td>
			   <td colspan=2 height="32">&nbsp;</td>
               <td height="32">&nbsp;</td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 height="46"><%if (!isdq) {%><font color="#CC0000">*</font><%}%>注册资本金:</td>
            <td  height="46">￥ 
            <% if ( isCheck ){ %>
            <input type="text" name="ccaptial" size="15" class="box" value="<%=((cInfo.getCaptial()==null)?"":cInfo.getCaptial())%>" onFocus="nextfield='cdealscope'" disabled >
            <%}else{%>
            <input type="text" name="ccaptial" size="15" class="box" value="<%=((cInfo.getCaptial()==null)?"":cInfo.getCaptial())%>" onFocus="nextfield='cdealscope'"  >
		    <% } %>    
            </td>
            <td colspan=2 height="46"><%if (!isdq) {%><font color="#CC0000">*</font><%}%>经营范围:</td>
            <td  height="46"> 
              <input type="text" name="cdealscope" size="15" class="box" value="<%=((cInfo.getDealScope()==null)?"":cInfo.getDealScope())%>" onFocus="nextfield='ckgclientname'" <%if (isCheck) {%>disabled<%}%> >
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=6  height="32"> 
              <hr>
            </td>
          </tr>
          <tr style='height:23.25pt'> <%
        tempLong=cInfo.getKGClientID();
        tempStr=cInfo.getKGClientName();
        if ( tempStr==null )
        	tempStr="";
		String strMagnifierName3 = "控股单位";							//放大镜的名称
		String strFormName3 = "frm";										//主页面表单名称
		String strPrefix3 ="";											////控件名称前缀
		String[] strMainNames3 = {"ckgclientname","ckgcardno","ckgpwd"};	//放大镜回显栏位值列表
		String[] strMainFields3 = { "sName","sLoanCardNo","sLoanCardPwd"};		//放大镜回显栏位对应的表格字段
		String[] strReturnNames3 = {"ckgclientid"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields3 = {"ID"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues3=tempStr;						////放大镜回显栏位对应的初始值
		String[] strReturnValues3 = {""+tempLong};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames3 = {"客户编号","客户名称"};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields3 = {"sCode","sName"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex3 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty3 = "value='"+tempStr+"' ";									//放大镜的对应控件栏位属性
		if (isCheck) strMainProperty3+=" disabled ";
		String strMatchValue3="sCode";									////放大镜要模糊匹配的字段
		String strNextControls3 = "ckgscale";								//设置下一个焦点
		String strTitle3="";
		if (!isdq) 
		{
			strTitle3="<font color=\"#CC0000\">*</font>控股单位";
		}
		else
		{
			strTitle3="控股单位";
		}	
		String strFirstTD3=" colspan=2 ";
		String strSecondTD3="  ";
		
		OBMagnifier.showZoomCtrl(out,strMagnifierName3,strFormName3,strPrefix3,strMainNames3,strMainFields3,
			strReturnNames3,strReturnFields3, strReturnInitValues3, strReturnValues3,strDisplayNames3,strDisplayFields3,
			intIndex3,strMainProperty3,"getClient("+strMainNames3[0]+".value)", strMatchValue3,strNextControls3 ,strTitle3, strFirstTD3, strSecondTD3 );	
		%> 
            <td colspan=2 height="32"><%if (!isdq) {%><font color="#CC0000">*</font><%}%>持股比例:</td>
            <td  height="32"> 
             <% if (isCheck ) {%>
                <input type="text" name="ckgscale" size="15" class="box" value="<%=cInfo.getFKGScale()%>" onFocus="nextfield='ckgcardno'" disabled >
             <% }else{ %>
                <script language="javascript">
        			createAmountCtrl("frm","ckgscale","<%=(cInfo.getFKGScale()==0)?"":DataFormat.formatRate(cInfo.getFKGScale())%>","ckgcardno","");
		        </script>
            <% } %>  
				%
              </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 height="32"><%if (!isdq) {%><font color="#CC0000">*</font><%}%>贷款卡号:</td>
            <td  height="32"> 
              <input type="text" name="ckgcardno" size="15" class="box" value="<%=((cInfo.getKGCardNo()==null)?"":cInfo.getKGCardNo())%>" onFocus="nextfield='ckgpwd'" <%if (isCheck) {%>disabled<%}%> >
            </td>
            <td colspan=2 height="32"><%if (!isdq) {%><font color="#CC0000">*</font><%}%>贷款卡密码:</td>
            <td  height="32"> 
              <input class=box name="ckgpwd" size="15" class="box" value="<%=((cInfo.getKGPwd()==null)?"":cInfo.getKGPwd())%>" onFocus="nextfield='cqtclientname0'" <%if (isCheck) {%>disabled<%}%> >
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=6 height="32"> 
              <hr>
            </td>
          </tr>
          <tr style='height:23.25pt'>
          <td colspan=2> 其他股东单位1</td>
          <td ><input class=box name=cqtclientname0 value="<%=(QTClientName[0]==null?"":QTClientName[0])%>" onFocus="nextfield='cqtscale0'" <%if (isCheck) {%>disabled<%}%>>
          <td colspan=2 height="32">持股比例:</td>
          <td  height="32"> 
             <% if ( isCheck ) { %>
             <input type="text" name="cqtscale0" size="15" class="box" value="<%=QTScale[0]%>" onFocus="nextfield='cqtcardno0'" disabled >
             <% }else{ %>
              <script language="javascript">
        			createAmountCtrl("frm","cqtscale0","<%=(QTScale[0]==0)?"":DataFormat.formatRate(QTScale[0])%>","cqtcardno0","");
		        </script>
             <% } %> 
              %
              </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 height="32">贷款卡号:</td>
            <td  height="32"> 
              <input type="text" name="cqtcardno0" size="15" class="box" value="<%=((QTCardNo[0]==null)?"":QTCardNo[0])%>" onFocus="nextfield='cqtpwd0'" <%if (isCheck) {%>disabled<%}%>>
            </td>
            <td colspan=2 height="32">贷款卡密码:</td>
            <td  height="32"> 
              <input class=box name="cqtpwd0" size="15" class="box" value="<%=((QTPwd[0]==null)?"":QTPwd[0])%>" onFocus="nextfield='cqtclientname1'" <%if (isCheck) {%>disabled<%}%> >
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=6 height="32"> 
              <hr>
            </td>
          </tr>
          <tr style='height:23.25pt'> <td colspan=2> 其他股东单位2</td>
          <td ><input class=box name=cqtclientname1 value="<%=(QTClientName[1]==null?"":QTClientName[1])%>" onFocus="nextfield='cqtscale1'" <%if (isCheck) {%>disabled<%}%> >
            <td colspan=2 height="32">持股比例:</td>
            <td  height="32"> 
             <% if ( isCheck ) { %>
             <input type="text" name="cqtscale1" size="15" class="box" value="<%=QTScale[1]%>" onFocus="nextfield='cqtcardno1'" disabled >
             <% }else{ %>
              <script language="javascript">
        			createAmountCtrl("frm","cqtscale1","<%=(QTScale[1]==0)?"":DataFormat.formatRate(QTScale[1])%>","cqtcardno1","");
		        </script>
             <% } %>
             %</td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 height="32">贷款卡号:</td>
            <td  height="32"> 
              <input type="text" name="cqtcardno1" size="15" class="box" value="<%=((QTCardNo[1]==null)?"":QTCardNo[1])%>" onFocus="nextfield='cqtpwd1'" <%if (isCheck) {%>disabled<%}%> >
            </td>
            <td colspan=2 height="32">贷款卡密码:</td>
            <td  height="32"> 
              <input class=box name="cqtpwd1" size="15" class="box" value="<%=((QTPwd[1]==null)?"":QTPwd[1])%>" onFocus="nextfield='cqtclientname2'" <%if (isCheck) {%>disabled<%}%> >
            </td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=6 height="32"> 
              <hr>
            </td>
          </tr>
          <tr style='height:23.25pt'><td colspan=2> 其他股东单位3</td>
          <td ><input class=box name=cqtclientname2 value="<%=(QTClientName[2]==null?"":QTClientName[2])%>" onFocus="nextfield='cqtscale2'" <%if (isCheck) {%>disabled<%}%> >
            <td colspan=2 height="32">持股比例:</td>
            <td  height="32"> 
             <% if ( isCheck ) { %>
             <input type="text" name="cqtscale2" size="15" class="box" value="<%=QTScale[2]%>" onFocus="nextfield='cqtcardno2'" disabled >
             <% }else{ %>
              <script language="javascript">
        			createAmountCtrl("frm","cqtscale2","<%=(QTScale[2]==0)?"":DataFormat.formatRate(QTScale[2])%>","cqtcardno2","");
		        </script>
             <% } %>
              %</td>
          </tr>
          <tr style='height:23.25pt'> 
            <td colspan=2 height="32">贷款卡号:</td>
            <td  height="32"> 
              <input type="text" name="cqtcardno2" size="15" class="box" value="<%=((QTCardNo[2]==null)?"":QTCardNo[2])%>" onFocus="nextfield='cqtpwd2'" <%if (isCheck) {%>disabled<%}%> >
            </td>
            <td colspan=2 height="32">贷款卡密码:</td>
            <td  height="32"> 
              <p class=MsoNormal> <% if ( assureTypeID==LOANConstant.AssureType.ASSURE ) {%> 
                 <input class=box name="cqtpwd2" size="15" class="box" value="<%=((QTPwd[2]==null)?"":QTPwd[2])%>" onFocus="nextfield='dbje'" <%if (isCheck) {%>disabled<%}%> >
                <% }else if (assureTypeID==LOANConstant.AssureType.IMPAWN) {%> 
                 <input class=box name="cqtpwd2" size="15" class="box" value="<%=((QTPwd[2]==null)?"":QTPwd[2])%>" onFocus="nextfield='zydcmc'" <%if (isCheck) {%>disabled<%}%> >
                <% }else if (assureTypeID==LOANConstant.AssureType.PLEDGE) {%> 
                 <input class=box name="cqtpwd2" size="15" class="box" value="<%=((QTPwd[2]==null)?"":QTPwd[2])%>" onFocus="nextfield='dycczj'" <%if (isCheck) {%>disabled<%}%> >
                <% } %> </p>
            </td>
          </tr>
          <tr>
			<td colspan=5 height="32"> 
				<% if (!isCheck) {%>
				<input class=button name="financialDCB" type=button value="资产负债表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../contractcontent/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
				<% }else{ %>
				<input class=button name="financialDCB" type=button value="资产负债表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../contractcontent/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">
				<%}%>
			</td>
			<td>&nbsp; </td>
		</tr>            
          <TR> 
            <TD colSpan=6 height=25 > 
              <HR align=center SIZE=2 width="100%">
            </TD>
          </TR>
          <% if ( assureTypeID==LOANConstant.AssureType.ASSURE ) {%> 
          <TR> 
            <TD colSpan=6 height=2 align=left><U>保证贷款资料</U></TD>
          </TR>
          <TR> 
            <TD colSpan=2> <FONT color=#ff0000>* </FONT>保证金额:</TD>
            <TD width=270>￥ 
            <% if (isCheck){ %>
               <INPUT class=box maxlength="50" name="dbje" onfocus="nextfield='xyb'" value="<%=DataFormat.formatNumber(amount,2)%>" disabled >
            <% }else{ %>
              <SCRIPT language=javascript>
        		createAmountCtrl("frm","dbje","<%=DataFormat.formatAmount(amount)%>","xyb","");
	          </SCRIPT>
	        <% } %>  
            </TD>
            <TD colspan=2> 保证单位调查表: </TD>
            <TD> 
              <INPUT name=dcb onfocus="nextfield='xyb'" type=hidden value=保证调查表>
              <!--
              <IFRAME frameBorder=0 height=100 name=iFrame scrolling=no src="S1-IFRAM.htm" width="100%"></IFRAME> 
              -->
            </TD>
          </TR>
          <% }else if (assureTypeID==LOANConstant.AssureType.IMPAWN) {%> 
          <TR> 
            <TD colSpan=6 height=2 align=left><U>质押贷款资料</U></TD>
          </TR>
          <TR> 
            <TD colSpan=2> <FONT color=#ff0000>* </FONT>质押动产名称:</TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zydcmc" onfocus="nextfield='sl'" value="<%=impawName%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
            <TD colspan=2> <FONT color=#ff0000>* </FONT>数量（数值）:</TD>
            <TD> 
            <% if (isCheck) { %>
               <INPUT class=box maxlength="50" name="sl" onfocus="nextfield='zl'" value="<%=DataFormat.formatNumber(amount,2)%>" disabled >
            <% }else{ %>
               <script language="javascript">
        			createAmountCtrl("frm","sl","<%=DataFormat.formatAmount(amount)%>","zl","");
		       </script>
		    <% } %>   
            </TD>
          </TR>
          <TR> 
            <TD colSpan=2> <FONT color=#ff0000>* </FONT>质量: </TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zl" onfocus="nextfield='zk'" value="<%=impawQuality%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
            <TD colspan=2> <FONT color=#ff0000>* </FONT>状况: </TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zk" onfocus="nextfield='xyb'" value="<%=impawStatus%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
          </TR>
          <% }else if (assureTypeID==LOANConstant.AssureType.PLEDGE) {%> 
          <TR> 
            <TD colSpan=6 height=2 align=left><U>抵押贷款资料</U></TD>
          </TR>
          <TR> 
            <TD colSpan=2> <FONT color=#ff0000>* </FONT>抵押财产总价:</TD>
            <TD>￥ 
            <% if (isCheck) { %>
                <INPUT class=box maxlength="50" name="dycczj" onfocus="nextfield='dyl'" value="<%=DataFormat.formatNumber(pledgeAmount,2)%>" disabled >
            <% }else{ %>
                <script language="javascript">
					createAmountCtrl("frm", "dycczj", "<%=DataFormat.formatAmount(pledgeAmount)%>", "dyl", null);
				</script>
			<% } %>	
            </TD>
            <TD colspan=2> <FONT color=#ff0000>* </FONT>抵押率:</TD>
            <TD> 
              <INPUT class=tar name="dyl" onfocus="nextfield='xyb'"  size="13" value="<%=DataFormat.formatRate(pledgeRate)%>" onblur="writed()" <%if (isCheck) {%>disabled<%}%>>
              %</TD>
          </TR>
          <TR> 
            <TD colSpan=2> 实际抵押额:</TD>
            <TD> ￥
            <% if (isCheck) { %>
            <INPUT disabled class=tar name="sjdye" size="18" onfocus="nextfield='xyb'" value="<%=DataFormat.formatNumber(amount,2)%>">
            <% }else{ %>
            <INPUT readonly class=tar name="sjdye" size="18" onfocus="nextfield='xyb'" >
            <script>
              frm.sjdye.value=eval(frm.dyl.value/100*reverseFormatAmount(frm.dycczj.value));
			</script>
			<% } %>
            </TD>
            <TD colspan=2> 状况: </TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zk" onfocus="nextfield='xyb'" value="<%=impawStatus%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
          </TR>
          <% } %> 
        </table>
        
        <TABLE cellPadding=0 height=50 width=720>
        <TR>
          <TD height=19 width=10>&nbsp;</TD>
          <TD colSpan=3 height=19>  <HR align=center SIZE=2 width="100%">
          </TD> 
        </TR>
        <TR>
          <TD height=9 width=10>
            <P class=MsoNormal>&nbsp;</P></TD>
          <TD height=9 width=529>
            <P align=right class=MsoNormal></P></TD>
          <TD colSpan=-3 height=9 width=258>
            <P align=right class=MsoNormal>
            <INPUT class=button name="syb" onclick="backto()" type="button" value=" 返 回 "> 
          </TD>
          <TD height=9 width=12>
            <P align=right class=MsoNormal >&nbsp;</P>
          </TD>
        </TR>
        </TABLE>
      </TD>
      </TR>
      </TABLE>
		  <input type=hidden name="lLoanType" value="<%=type%>">
		  <input type=hidden name="lClientID" value="<%=(String)request.getAttribute("lClientID")%>">
		  <input type=hidden name="lAssureTypeID" value="<%=assureTypeID%>">
		  <input type=hidden name="lLoanID" value="<%=(String)request.getAttribute("lLoanID")%>">
		  <INPUT name=assClientID type=hidden value="<%=cInfo.getClientID()%>" > 
		  <input type=hidden name="lAssureID" value="<%=assureID%>" >
		  <input type="hidden" name="txtAction" value="<%=action%>">
		  <input type="hidden" name="mType" value="assure">
	</form>	  
	
<script language="javascript">
function backto()
{
	frm.action="q006-c.jsp?mType=assure";
	showSending();
	frm.submit();
	return true;
}

	function  getAccount(account)
	{
		var sql="select ID,SACCOUNTNO from sett_account where nStatusID=<%=Constant.RecordStatus.VALID%> and nClientID=<%=cInfo.getClientID()%> order by id";
		
		return sql;
	}
	function  getClient(cname)
	{
		var sql = "SELECT id,sCode,sName,SLOANCARDNO,SLOANCARDPWD FROM client where nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";
		
		return sql ;
		
	}

firstFocus(frm.caccount);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");		
</script>			  
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//OBHtmlCom.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>

