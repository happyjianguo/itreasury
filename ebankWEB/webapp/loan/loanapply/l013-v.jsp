<%
/**
 * ҳ������ ��l013-v.jsp
 * ҳ�湦�� : ѡ��֤��λ
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
    	
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
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
		   		
        //��ʾ�ļ�ͷ
        if (action.equals("modify"))
        	OBHtml.showOBHomeHead(out,sessionMng,"["+loanTypeName+"��������֤����]",Constant.YesOrNo.NO);
        else
        	OBHtml.showOBHomeHead(out,sessionMng,"["+loanTypeName+"��������֤����]",Constant.YesOrNo.YES);	
        	
        //��ǩ����
        String strLableCName="���";
        if ( assureType==LOANConstant.AssureType.ASSURE ) {
        	strLableCName = "��֤";
        }else if (assureType==LOANConstant.AssureType.IMPAWN) {
        	strLableCName = "��Ѻ";
        }else if (assureType==LOANConstant.AssureType.PLEDGE) {
        	strLableCName = "��Ѻ";
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
      <TD class=FormTitle height=29><B><%=loanTypeName%>������</B></TD>
  </TR>
  <TR>
    <TD>

      <TABLE cellPadding=0 height=90 width=100%>
	  
        <TR height=15>
        	<td>
            <% if ( assureType==LOANConstant.AssureType.ASSURE ) {%>   
               <P class=MsoNormal><U>��֤��λ����</U></P>
            <% }else if (assureType==LOANConstant.AssureType.IMPAWN) {%>
               <P class=MsoNormal><U>��Ѻ��λ����</U></P>
            <% }else if (assureType==LOANConstant.AssureType.PLEDGE) {%>
               <P class=MsoNormal><U>��Ѻ��λ����</U></P> 
			<% }else if (assureType==LOANConstant.AssureType.RECOGNIZANCE) {%>
               <P class=MsoNormal><U>��֤��������</U></P>
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
                        <TD borderColor=#999999 colSpan=3 height=33 vAlign=middle><B>���пͻ�</B></TD>
                        <TD borderColor=#999999 height=33 vAlign=middle width="12%">&nbsp;</TD>
                        <TD height=33 vAlign=middle width="14%">&nbsp;</TD>
                        <TD height=33 vAlign=middle width="30%">&nbsp;</TD>
                      </TR>
                      <TR> 
                        <TD borderColor=#999999 height=32 vAlign=middle width="3%">&nbsp;</TD>
                        <%
		String strMagnifierName = URLEncoder.encode("�ͻ����");							//�Ŵ󾵵�����
		String strFormName = "frm";										//��ҳ�������
		String strPrefix ="";											////�ؼ�����ǰ׺
		String[] strMainNames = {"oclientcode","oclientname","olicencecode"};	//�Ŵ󾵻�����λֵ�б�
		String[] strMainFields = { "sCode","sName","sLicenceCode"};		//�Ŵ󾵻�����λ��Ӧ�ı���ֶ�
		String[] strReturnNames = {"oclientid"};							//�Ŵ󾵷���ֵ�б�(����ֵ)
		String[] strReturnFields = {"ID"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
		String   strReturnInitValues="" ;		                        ////�Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
		String[] strReturnValues = {"-1"};								//�Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
		String[] strDisplayNames = {URLEncoder.encode("�ͻ����"),URLEncoder.encode("�ͻ�����")};				//�Ŵ�С������ʾ����λ����
		String[] strDisplayFields = {"sCode","sName"};					//�Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
		int intIndex = 0; 												//ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,
		String strMainProperty = " ";									//�Ŵ󾵵Ķ�Ӧ�ؼ���λ����
		String strMatchValue="sCode";									////�Ŵ�Ҫģ��ƥ����ֶ�
		String strNextControls = "xyb";								//������һ������
		String strTitle="<font color=#FF0000>*</font> �ͻ����";
		String strFirstTD=" height=32 vAlign=center width=17% colspan=2";
		String strSecondTD=" height=32 vAlign=center width=20% ";
			
		//���ò����Ŵ󾵵ķ���
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
                        <TD borderColor=#999999 height=32 vAlign=middle  width="17%">��֤����:</TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="3%">&nbsp; 
                        </TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="20%">
						<SCRIPT language=javascript>
				document.writeln(" <input class=tar   type='text' name='dbje' value='<%=DataFormat.formatAmount(amount)%>' size='18' maxlength='15' onchange='calculateScale(\"frm\",\"bzjbl\")' onblur='adjustAmount(\"frm\",\"dbje\",1,\"\",1)'  onfocus='adjustAmount(\"frm\",\"dbje\",2,\"\",1)'> <input type='hidden' name='dbjeNextCtrlName' value='xyb'>");
	          </SCRIPT></TD>
                        <TD borderColor=#999999 height=32 vAlign=middle width="19%"> ��֤�����: 
                        </TD>
                        <TD height=32 vAlign=middle width="15%" nowrap>
                          <INPUT name=bzjbl readonly onfocus="nextfield='xyb'" class=tar maxlength="50" value="<%=DataFormat.formatNumber(amount,2)%>"> %</TD>
                        <TD height=32 vAlign=middle width="24%">&nbsp; </TD>
                      </TR>
			<%}else{%>
				 <TR> 
                        <TD borderColor=#999999 height=32 vAlign=middle  width="3%">&nbsp;</TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="17%"><%=strLableCName %>��λ��</TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="3%">&nbsp; 
                        </TD>
                        <TD borderColor=#999999 height=32 vAlign=middle  width="20%"><FONT size=2> 
                          <INPUT class=box maxLength=7 name="oclientname" size="24" value="" readonly >
                          </FONT> </TD>
                        <TD borderColor=#999999 height=32 vAlign=middle width="19%">Ӫҵִ�պ��룺 
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
            
			<INPUT class=button name="syb" onclick="backto()" type="button" value=" ��һ�� "> 
			<INPUT class=button name="xyb" onclick="javascript:return confirmSave(frm);" type="button" value=" ��һ�� " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 
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
	if(!checkAmount(frm.dbje,1,"��֤����"))
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

			//��С����ǰ�ͺ�����ݷֱ�ȡ����
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			//С����ǰ�������λ����0
			if (strFront.length==0)
			{
				strFront = "0";
			}
			//�����߽�С��������ֵ��������λ
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
		if(!checkAmount(frm.dbje,1,"��֤����")){
			return false;
		}
		frm.isnew.value="old";
		frm.action="l017-c.jsp"
	<%}else{%>
			if (!checkMagnifier("frm","oclientid","oclientcode","���пͻ����"))
				return false;
			<%if ( assureType==LOANConstant.AssureType.ASSURE ){%>		
				if (frm.oclientid.value=='<%=clientID%>'){
					alert("��֤��λ�����ǽ�λ!");
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
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"�ͻ�����", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
 
<%@ include file="/common/SignValidate.inc" %>