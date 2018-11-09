<%
/**
 * ҳ������l015-v.jsp
 * ҳ�湦�� : ��֤����ά��
 * ��    �� ��gump
 * ��    �� ��2003-09-27
 * ����˵�� ��
 *			  
 * �޸���ʷ ��
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
	com.iss.itreasury.settlement.util.*,
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
        OBAssureInfo assInfo=(OBAssureInfo)request.getAttribute("AssureInfo");
        double loanAmount=0.0;
		tmp=(String)request.getAttribute("loanAmount");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			loanAmount=Double.valueOf(tmp).doubleValue();
		}	
        double  amount=0;
        long assureID=-1;
    	String  impawName="";
    	String  impawQuality="";
    	String  impawStatus="";
    	double  pledgeAmount=0;
    	double  pledgeRate=0;
    	long    statusID=-1;
    	String  assureCode="";
		long clientid=-1;
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
    	 if(impawStatus==null){impawStatus="";}
    	//��ʾ�ļ�ͷ
        if ( (action.equals("modify"))||(action.equals("check")) )
    		OBHtml.showOBHomeHead(out,sessionMng,"[��֤��λ����]",Constant.YesOrNo.NO);
    	else
    		OBHtml.showOBHomeHead(out,sessionMng,"[��֤��λ����]",Constant.YesOrNo.YES);	
        if ( action.equals("check") )
       		isCheck=true;   
       		
		if ( cInfo!=null )
        {
    		QTClientName=cInfo.getQTClientName();
			QTScale=cInfo.getFQTScale();
			QTCardNo=cInfo.getQTCardNo();
			QTPwd=cInfo.getQTPwd();
		}	
		boolean isdq=false;
		if(assureTypeID==LOANConstant.AssureType.RECOGNIZANCE){
			clientid=sessionMng.m_lClientID;
		}else{
			clientid=cInfo.getClientID();
		}
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top >

  <TR class="tableHeader">
  
      <TD class=FormTitle height=29><B><%=loanTypeName%>��������</B></TD>
    </TR>
  <TR>
    <TD>
	    <TABLE cellPadding=0 border=0 width=730 >
         <% if (assureTypeID==LOANConstant.AssureType.RECOGNIZANCE) {%>
          <TR> 
            <TD colSpan=6 height=2 align=left><U>��֤��������</U></TD>
          </TR>
          <TR> 
            <TD colSpan=1> <FONT color=#ff0000>* </FONT>��֤��</TD>
            <TD width=270>�� 
			 <% if (isCheck){ %>
               <INPUT class=box maxlength="50" name="dbje" onfocus="nextfield='xyb'" value="<%=DataFormat.formatNumber(amount,2)%>" disabled >
            <% }else{ %>
				<SCRIPT language=javascript>
				document.writeln(" <input class=tar   type='text' name='dbje' value='<%=DataFormat.formatAmount(amount)%>' size='18' maxlength='15' onchange='calculateScale(\"frm\",\"bzjbl\")' onblur='adjustAmount(\"frm\",\"dbje\",1,\"\",1)'  onfocus='adjustAmount(\"frm\",\"dbje\",2,\"\",1)'> <input type='hidden' name='dbjeNextCtrlName' value='xyb'>");
	          </SCRIPT>
			  <%}%></TD>
			<TD> ��֤������� </TD>
			<TD nowrap> 
              <INPUT name=bzjbl readonly onfocus="nextfield='xyb'" class=tar maxlength="50" value="<%=DataFormat.formatNumber(amount,2)%>" <%if (isCheck) {%>disabled<%}%>> %
            </TD>
            <TD height=32 vAlign=center width="24%">&nbsp; </TD>
            </TR>
		  <% }else if ( assureTypeID==LOANConstant.AssureType.ASSURE ) {%> 
          <TR> 
            <TD colSpan=6 height=2 align=left><U>��֤��������</U></TD>
          </TR>
          <TR> 
            <TD colSpan=1> <FONT color=#ff0000>* </FONT>��֤��</TD>
            <TD width=270>�� 
            <% if (isCheck){ %>
               <INPUT class=box maxlength="50" name="dbje" onfocus="nextfield='xyb'" value="<%=DataFormat.formatNumber(amount,2)%>" disabled >
            <% }else{ %>
              <SCRIPT language=javascript>
        		createAmountCtrl("frm","dbje","<%=DataFormat.formatAmount(amount)%>","xyb","");
	          </SCRIPT>
	        <% } %>  
            </TD>
            <!--TD colspan=2> ��֤��λ����� </TD!-->
			<TD colspan=4> &nbsp;</TD>
            <TD> 
              <INPUT name=dcb onfocus="nextfield='xyb'" type=hidden value=��֤�����>
              <!--
              <IFRAME frameBorder=0 height=100 name=iFrame scrolling=no src="S1-IFRAM.htm" width="100%"></IFRAME> 
              -->
            </TD>
          </TR>
          <% }else if (assureTypeID==LOANConstant.AssureType.IMPAWN) {%> 
          <TR> 
            <TD colSpan=6 height=2 align=left><U>��Ѻ��������</U></TD>
          </TR>
          <TR> 
            <TD colSpan=2> <FONT color=#ff0000>* </FONT>��Ѻ�������ƣ�</TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zydcmc" onfocus="nextfield='sl'" value="<%=impawName%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
            <TD colspan=2> <FONT color=#ff0000>* </FONT>��������ֵ����</TD>
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
            <TD colSpan=2> <FONT color=#ff0000>* </FONT>������ </TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zl" onfocus="nextfield='zk'" value="<%=impawQuality%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
            <TD colspan=2> <FONT color=#ff0000>* </FONT>״���� </TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zk" onfocus="nextfield='xyb'" value="<%=impawStatus%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
          </TR>
          <% }else if (assureTypeID==LOANConstant.AssureType.PLEDGE) {%> 
          <TR> 
            <TD colSpan=6 height=2 align=left><U>��Ѻ��������</U></TD>
          </TR>
          <TR> 
            <TD colSpan=2> <FONT color=#ff0000>* </FONT>��Ѻ�Ʋ��ܼۣ�</TD>
            <TD>�� 
            <% if (isCheck) { %>
                <INPUT class=box maxlength="50" name="dycczj" onfocus="nextfield='dyl'" value="<%=DataFormat.formatNumber(pledgeAmount,2)%>" disabled >
            <% }else{ %>
                <input type="text" name="dycczj" onblur="adjustAmount('frm','dycczj',1,'',1);convertRMB()" class="tar" value="<%=DataFormat.formatNumber(pledgeAmount,2)%>" onfocus="adjustAmount('frm','dycczj',2,'',1);" maxlength='15'>
		<input type='hidden' name="dycczjNextCtrlName" value="dyl">
			<% } %>	
            </TD>
            <TD colspan=2> <FONT color=#ff0000>* </FONT>��Ѻ�ʣ�</TD>
            <TD> 
              <INPUT class=tar name="dyl" onfocus="nextfield='zk'"  size="13" value="<%=DataFormat.formatRate(pledgeRate)%>" onblur="convertRMB()" <%if (isCheck) {%>disabled<%}%>>
              %</TD>
          </TR>
          <TR> 
            <TD colSpan=2> ʵ�ʵ�Ѻ�</TD>
            <TD> ��
            <% if (isCheck) { %>
            <INPUT disabled class=tar name="sjdye" size="18" onfocus="nextfield='zk'" value="<%=DataFormat.formatNumber(amount,2)%>">
            <% }else{ %>
            <INPUT readonly class=tar name="sjdye" size="18" onfocus="nextfield='zk'" >
            <script>
              frm.sjdye.value=eval((frm.dyl.value/100*reverseFormatAmount(frm.dycczj.value)).toFixed(2));
			</script>
			<% } %>
            </TD>
            <TD colspan=2> ״���� </TD>
            <TD> 
              <INPUT class=box maxlength="50" name="zk" onfocus="nextfield='xyb'" value="<%=impawStatus%>" <%if (isCheck) {%>disabled<%}%>>
            </TD>
          </TR>
          <% } %> 
        </table>
        
        <TABLE cellPadding=0 width=720>
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
            <% if (isCheck) {%>
            <INPUT class=button name="syb" onclick="backto()" type="button" value=" �� �� "> 
            <% }else{ %>
			<INPUT class=button name="syb" onclick="backto()" type="button" value=" ��һ�� "> 
            <INPUT class=button name="xyb"  onclick="confirmSave()" type="button" value=" ��һ�� "> 
            <% } %>
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
		  <input type=hidden name="lAssureID" value="<%=assureID%>" >
		  <input type=hidden name="txtAction" value="<%=action%>">
		  <input type=hidden name="commandKey" value="<%=sessionMng.nextInterval()%>">
		  <input type=hidden name="loanAmount" value="<%=loanAmount%>" >
		  <input type=hidden name="assClientID" value="<%=clientid%>" >
  </form>	  
	
<script language="javascript">
function getAllScale()
{
	var f1=0,f2=0,f3=0,f4=0 ;
	if(frm.ckgscale.value > 0) f1= frm.ckgscale.value;
	if(frm.cqtscale0.value > 0) f2 = frm.cqtscale0.value; 
	if(frm.cqtscale1.value > 0) f3 = frm.cqtscale1.value; 
	if(frm.cqtscale2.value > 0) f4 = frm.cqtscale2.value; 
	
	if((eval(f1) + eval(f2) + eval(f3) + eval(f4))>100)
	{
		alert("�عɱ���֮�Ͳ��ܳ���100%");
		return false;
	}
	return true;
}

function backto()
{
	<% if ( assureID<=0 ) {%>
	frm.action="l013-v.jsp"
	<% }else{ %>
	frm.action="l016-c.jsp"
	<% } %>
	showSending();
	frm.submit();
	return true;
	
}

function convertRMB(){
	//added by mzh_fu 2007/03/27
	frm.dycczj.value=reverseFormatAmount1(frm.dycczj.value);
	
	if (!InputValid(frm.dycczj, 1, "float", 0, 0, 0,"��Ѻ�Ʋ��ܼ�")) 
  	{
		return false;
  	}	
  	if (!InputValid(frm.dyl, 1, "float", 1, 0.001, 100,"��Ѻ��")) 
  	{
		return false;
  	}	
	var tempvar;
   	var tempAmount=document.frm.dycczj.value; 
   	var tempRate=document.frm.dyl.value; 
   	tempvar=reverseFormatAmount1(tempAmount); 
   	
   	//modified by mzh_fu 2007-03-13   	
   	tempvar=(tempvar * tempRate/100).toFixed(2);   	
 	
	document.frm.sjdye.value=formatAmount1(tempvar);
	adjustAmount("frm","sjdye",1,"",1); 
	
	//added by mzh_fu 2007/03/27
	adjustAmount("frm","dycczj",1,"",1); 		
}

function writed()
{
  if (!InputValid(frm.dycczj, 1, "float", 0, 0, 0,"��Ѻ�Ʋ��ܼ�")) 
  {
	return false;
  }	
  if (!InputValid(frm.dyl, 1, "float", 1, 0.001, 100,"��Ѻ��")) 
  {
	return false;
  }	

  //modified by mzh_fu 2007-03-13 
  frm.sjdye.value=formatAmount((frm.dyl.value*reverseFormatAmount(frm.dycczj.value)/100).toFixed(2));
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
function  confirmSave()
{
<%if(assureTypeID==LOANConstant.AssureType.RECOGNIZANCE){%>
		if(!checkAmount(frm.dbje,1,"��֤����")){
			return false;
		}
<% }else if ( assureTypeID==LOANConstant.AssureType.ASSURE ) {%>  
	//��֤��� dbje
	if(!checkAmount(frm.dbje,1,"��֤���"))
	{
	    return false;
	}
<% }else if (assureTypeID==LOANConstant.AssureType.IMPAWN) {%>
		//��Ѻ�������� zydcmc
		if (!InputValid(frm.zydcmc, 1, "string", 0, 0, 0,"��Ѻ��������")) 
		{
			return false;
		}
		//����		sl
		if(!checkAmount(frm.sl,1,"����"))
		{
	    	return false;
		}

		//���� zl
		if (!InputValid(frm.zl, 1, "string", 0, 0, 0,"����")) 
		{
			return false;
		}
		//״�� zk
		if (!InputValid(frm.zk, 1, "string", 0, 0, 0,"״��")) 
		{
			return false;
		}
<% }else if (assureTypeID==LOANConstant.AssureType.PLEDGE) {%>
		//��Ѻ�Ʋ��ܼ� dycczj
	if(!checkAmount(frm.dycczj,1,"��Ѻ�Ʋ��ܼ�"))
	{
	    return false;
	}

		//��Ѻ��	dyl

	if (!InputValid(frm.dyl, 1, "float", 1, 0.001, 100,"��Ѻ��")) 
  	{
		return false;
  	}
  	
 	//added by mzh_fu 2007/03/23
	if(parseFloat(frm.sjdye.value)<0.01){
		alert("ʵ�ʵ�Ѻ���С��0.01");
		return false;
	}		
<% } %> 

		showSending();
		frm.action="l017-c.jsp";
		frm.submit();

	}

<%   if ( assureTypeID==LOANConstant.AssureType.ASSURE ) { %>
   		firstFocus(frm.dbje);
 <%  }else if (assureTypeID==LOANConstant.AssureType.IMPAWN) { %>
 		firstFocus(frm.zydcmc);
<%   }else if (assureTypeID==LOANConstant.AssureType.PLEDGE) { %>
		firstFocus(frm.dycczj);
 <%  }%>

//setSubmitFunction("confirmSave(frm)");
setFormName("frm");		
</script>			  
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//OBHtmlCom.showExceptionMessage(out,sessionMng,ie,request,response,"�ͻ�����", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>


