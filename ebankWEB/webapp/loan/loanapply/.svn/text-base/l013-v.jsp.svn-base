<%
/**
 * 页面名称 ：l013-v.jsp
 * 页面功能 : 选择保证单位
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.settlement.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
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
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		boolean	backward=false;
		String loanID=(String)request.getAttribute("lLoanID");
		String clientID=(String)request.getAttribute("lClientID");
		String assureTypeID=(String)request.getAttribute("lAssureTypeID");
		String sApplyCode=(String)request.getAttribute("sApplyCode");

		String action="";
		String tmp="";
		double  amount=0.0;
		double loanAmount=0.0;
		tmp=(String)request.getAttribute("loanAmount");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			loanAmount=Double.valueOf(tmp).doubleValue();
		}	
		long assureType=Long.valueOf(assureTypeID).longValue();
	    tmp=(String)request.getAttribute("txtAction");
		if ( (tmp!=null) && ( tmp.length()>0 ) )
			action=tmp;	
		   		
        //显示文件头
        if (action.equals("modify"))
        	OBHtml.showOBHomeHead(out,sessionMng,"["+loanTypeName+"－新增保证资料]",Constant.YesOrNo.NO);
        else
        	OBHtml.showOBHomeHead(out,sessionMng,"["+loanTypeName+"－新增保证资料]",Constant.YesOrNo.YES);	
        	
        //标签名称
        String strLableCName="借款";
        if ( assureType==LOANConstant.AssureType.ASSURE ) {
        	strLableCName = "保证";
        }else if (assureType==LOANConstant.AssureType.IMPAWN) {
        	strLableCName = "质押";
        }else if (assureType==LOANConstant.AssureType.PLEDGE) {
        	strLableCName = "抵押";
        }
       // else if (assureType==LOANConstant.AssureType.RECOGNIZANCE) {
       // 	strLableCName = "";
       // }        

%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=100 width=730>
  <TR class="tableHeader">
      <TD class=FormTitle height=29><B><%=loanTypeName%>－新增</B></TD>
  </TR>
  <TR>
    <TD>

      <TABLE cellPadding=0 height=90 width=100%>
	  
        <TR height=15>
        	<td>
            <% if ( assureType==LOANConstant.AssureType.ASSURE ) {%>   
               <P class=MsoNormal><U>保证单位资料</U></P>
            <% }else if (assureType==LOANConstant.AssureType.IMPAWN) {%>
               <P class=MsoNormal><U>质押单位资料</U></P>
            <% }else if (assureType==LOANConstant.AssureType.PLEDGE) {%>
               <P class=MsoNormal><U>抵押单位资料</U></P> 
			<% }else if (assureType==LOANConstant.AssureType.RECOGNIZANCE) {%>
               <P class=MsoNormal><U>保证贷款资料</U></P>
            <% } %> 
           </td>
        </TR>
        <TR style="HEIGHT: 1.5pt">
          <TD height=35 vAlign=top>
            <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 width="100%">
              <TR>
                <TD>
                    <TABLE align=center height=87 width="100%">
					<% if (assureType!=LOANConstant.AssureType.RECOGNIZANCE) {%>
                      <TR> 
                        <TD borderColor=#999999 height=33 vAlign=middle width="3%"> 
                          <INPUT name="rb" type="radio" value="0" checked>
                        </TD>
                        <TD borderColor=#999999 colSpan=3 height=33 vAlign=middle><B>现有客户</B></TD>
                        <TD borderColor=#999999 height=33 vAlign=middle width="12%">&nbsp;</TD>
                        <TD height=33 vAlign=middle width="14%">&nbsp;</TD>
                        <TD height=33 vAlign=middle width="30%">&nbsp;</TD>
                      </TR>
                      <TR> 
                        <TD borderColor=#999999 height=32 vAlign=middle width="3%">&nbsp;</TD>
                        <%
		String strMagnifierName = URLEncoder.encode("客户编号");							//放大镜的名称
		String strFormName = "frm";										//主页面表单名称
		String strPrefix ="";											////控件名称前缀
		String[] strMainNames = {"oclientcode","oclientname","olicencecode"};	//放大镜回显栏位值列表
		String[] strMainFields = { "sCode","sName","sLicenceCode"};		//放大镜回显栏位对应的表格字段
		String[] strReturnNames = {"oclientid"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields = {"ID"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues="" ;		                        ////放大镜回显栏位对应的初始值
		String[] strReturnValues = {"-1"};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames = {URLEncoder.encode("客户编号"),URLEncoder.encode("客户名称")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields = {"sCode","sName"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty = " ";									//放大镜的对应控件栏位属性
		String strMatchValue="sCode";									////放大镜要模糊匹配的字段
		String strNextControls = "xyb";								//设置下一个焦点
		String strTitle="<font color=#FF0000>*</font> 客户编号";
		String strFirstTD=" height=32 vAlign=center width=17% colspan=2";
		String strSecondTD=" height=32 vAlign=center width=20% ";
			
		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,strPrefix,strMainNames,strMainFields,
			strReturnNames,strReturnFields, strReturnInitValues, strReturnValues,strDisplayNames,strDisplayFields,
			intIndex,strMainProperty,"getClient("+strMainNames[0]+".value)", strMatchValue,strNextControls ,strTitle, strFirstTD, strSecondTD );			
          
		%> 
                        <TD borderColor=#999999 height=32 vAlign=middle width="19%"></TD>
                        <TD height=32 vAlign=middle width="14%"></TD>
                        <TD height=32 vAlign=middle width="24%">&nbsp; </TD>
                      </TR>
			<%}%>
			<% if (assureType==LOANConstant.AssureType.RECOGNIZANCE) {%>
                      <TR> 
                        <TD borderColor=#999999 height=32 vAlign=middle  width="3%">&nbsp;</TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="17%">保证金金额:</TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="3%">&nbsp; 
                        </TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="20%">
						<SCRIPT language=javascript>
				document.writeln(" <input class=tar   type='text' name='dbje' value='<%=DataFormat.formatAmount(amount)%>' size='18' maxlength='15' onchange='calculateScale(\"frm\",\"bzjbl\")' onblur='adjustAmount(\"frm\",\"dbje\",1,\"\",1)'  onfocus='adjustAmount(\"frm\",\"dbje\",2,\"\",1)'> <input type='hidden' name='dbjeNextCtrlName' value='xyb'>");
	          </SCRIPT></TD>
                        <TD borderColor=#999999 height=32 vAlign=middle width="19%"> 保证金比例: 
                        </TD>
                        <TD height=32 vAlign=middle width="15%" nowrap>
                          <INPUT name=bzjbl readonly onfocus="nextfield='xyb'" class=tar maxlength="50" value="<%=DataFormat.formatNumber(amount,2)%>"> %</TD>
                        <TD height=32 vAlign=middle width="24%">&nbsp; </TD>
                      </TR>
			<%}else{%>
				 <TR> 
                        <TD borderColor=#999999 height=32 vAlign=middle  width="3%">&nbsp;</TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="17%"><%=strLableCName %>单位：</TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="3%">&nbsp; 
                        </TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="20%"><FONT size=2> 
                          <INPUT class=box maxLength=7 name="oclientname" size="24" value="" readonly >
                          </FONT> </TD>
                        <TD borderColor=#999999 height=32 vAlign=middle width="19%">营业执照号码： 
                        </TD>
                        <TD height=32 vAlign=middle width="14%"><FONT size=2> 
                          <INPUT class=box maxLength="50" name="olicencecode" size="16" readonly >
                          </FONT></TD>
                        <TD height=32 vAlign=middle width="24%">&nbsp; </TD>
                      </TR>
			<%}%>
                    </TABLE>
                  </TD></TR></TABLE></TD></TR>
        <TR >
          <TD vAlign=top align=right>
            
			<INPUT class=button name="syb" onclick="backto()" type="button" value=" 上一步 "> 
			<INPUT class=button name="xyb" onclick="javascript:return confirmSave(frm);" type="button" value=" 下一步 " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 
          </TD>
        </TR>
      </TABLE>
    </TD>
    </TR>
    </TABLE>

<input type=hidden name="lLoanType" value="<%=type%>">
<INPUT name=lLoanID type=hidden value="<%=loanID%>"> 
<INPUT name=assClientID type=hidden value=-1> 
<INPUT name=lClientID type=hidden value="<%=clientID%>"> 
<INPUT name=lAssureTypeID type=hidden value="<%=assureTypeID%>"> 
<INPUT name=isnew type=hidden value="old">
<input type="hidden" name="txtAction" value="<%=action%>">
<INPUT type=hidden name="loanAmount" value="<%=loanAmount%>" >
</form>

<script language="javascript">

function backto()
{

	frm.action="l016-c.jsp"
	showSending();
	frm.submit();
	return true;
}
function calculateScale(fromName,rateCntlName)
{
	if(!checkAmount(frm.dbje,1,"保证金金额"))
	{
	    return false;
	}
	else 
	{
		 frm.bzjbl.value=reverseFormatAmount(frm.dbje.value)/frm.loanAmount.value*100;
	}
	formatChargeRate1(fromName,rateCntlName);
}
function formatChargeRate1(fromName,rateCntlName)
 {
  var strData=eval(fromName + '.' + rateCntlName + '.value');
	if(isInnerFloat(strData))
 	{
 		
		if(strData!="")
 		{
			var i,strTemp;

			//将小数点前和后的数据分别取出来
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			//小数点前如果是零位，则付0
			if (strFront.length==0)
			{
				strFront = "0";
			}
			//补或者截小数点后面的值，保持四位
	 		if(strEnd.length>6)
	 		{
	 			strEnd=strEnd.substring(0,6);
	 		}
	 		else
	 		{
	 			if(strEnd.length==1)
	 			{
	 				strEnd=strEnd+ "00000";
	 			}
	 			else
				if (strEnd.length==2)
				{
					strEnd=strEnd+ "0000";
				}
				else
				if (strEnd.length==3)
				{
					strEnd=strEnd+ "000";
				}
				else
				if (strEnd.length==4)
				{
					strEnd=strEnd+ "00";
				}
				else
				if (strEnd.length==5)
				{
					strEnd=strEnd+ "0";
				}
				else
	 				if(strEnd.length==0)
	 				{
	 					strEnd="000000";
	 				}
	 		}
	 		strData=strFront+"." + strEnd;
 		}
		else
		{
			strData = "0.000000";
		}
	}
	eval(fromName + "." + rateCntlName + ".value='"+strData+"'");
 }
function  confirmSave(frm)
{
	<%if(assureType==LOANConstant.AssureType.RECOGNIZANCE){%>
		if(!checkAmount(frm.dbje,1,"保证金金额")){
			return false;
		}
		frm.isnew.value="old";
		frm.action="l017-c.jsp"
	<%}else{%>
			if (!checkMagnifier("frm","oclientid","oclientcode","现有客户编号"))
				return false;
			<%if ( assureType==LOANConstant.AssureType.ASSURE ){%>		
				if (frm.oclientid.value=='<%=clientID%>'){
					alert("保证单位不能是借款单位!");
					return false;
				}
			<%}%>		
				frm.isnew.value="old";
				frm.action="l014-c.jsp"
	<%}%>
	
		showSending();
		frm.submit();
		return true;
	
}

function getClient(cname)
{
 	var sql = "SELECT id,sCode,sName,sLicenceCode FROM client where nStatusID=<%=Constant.RecordStatus.VALID%> and nofficeid=<%=sessionMng.m_lOfficeID%> order by id";
	
	return sql ;
}
<%if(assureType==LOANConstant.AssureType.RECOGNIZANCE){%>
	firstFocus(frm.dbje);
<%}else{%>
	firstFocus(frm.oclientname);
<%}%>
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	
</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
 
<%@ include file="/common/SignValidate.inc" %>