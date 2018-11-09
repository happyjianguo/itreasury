<%--
 ҳ������ ��d010-v.jsp
 ҳ�湦�� : ������������-����Ʊ����ϸ���� ��ʾҳ��
 ��    �� ��gqzhang
 ��    �� ��2004��1��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* ����̶����� */
	String strTitle = "[��������]";
%>					
<%
  try
  {
	   Log.print("*******����ҳ��--ebank/loan/discountapply/d010-v.jsp*******");
	
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		 //�������
		 String strTmp = "";
		 String strAction = (String)request.getAttribute("txtAction");
		  String frompage = (String)request.getAttribute("frompage");
		 int nArrayNumber = 10;
		 long lDiscountBillID = -1; //����Ʊ�ݱ�ʶ,
		 long lDiscountApplyID = -1; //���ֱ�ʶ,
		 long lDiscountCredenceID = -1; //����ƾ֤��ʶ,
		 String strUser = ""; //ԭʼ��Ʊ��,
		 String strBank = ""; //�ж�����,
		 long lSerialNo = -1; //���к�,
		 long lIsLocal = -1; //�Ƿ��ڱ���,
		 Timestamp tsCreate = null; //��Ʊ��,
		 Timestamp tsEnd = null; // ������,
		 String strCode = ""; //��Ʊ����,
		 double dAmount = 0.0; //��Ʊ���,
		 long lAddDay = -1; //�ڼ�����������,
		 long lAcceptPOTypeID = -1; //Ʊ������,
		 String strFormerOwner = ""; //���ֵ�λֱ��ǰ��
		 
		 //��������
		 Timestamp tsDiscountStartDate = null;
		 String strDiscountCode = ""; 
	  
	     strTmp = (String)request.getAttribute("lID");
		 if( strTmp != null && strTmp.length() > 0 )
		 {
			 lDiscountApplyID = Long.parseLong(strTmp.trim());
		 }
    	long loanType=OBConstant.LoanInstrType.DISCOUNT;
		String subtypeid="";
		long[] loanTypeid={loanType};
		 //��������
	    strTmp = (String)request.getAttribute("tsDiscountStartDate");
		if (strTmp != null && strTmp.trim().length() > 0)
		{
			tsDiscountStartDate =  DataFormat.getDateTime(strTmp);
		}
		strTmp = (String)request.getAttribute("subtypeid");
		if (strTmp != null && strTmp.trim().length() > 0)
		{
			subtypeid =  strTmp;
		}
		strTmp = (String)request.getAttribute("strDiscountCode");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strDiscountCode = DataFormat.toChinese(strTmp.trim());
		}
		//Log.print("\n\n====@@@@@@@@@@@@@@@strDiscountCode:"+strDiscountCode);
		 
	   //��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	  
%>		
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post">
<input type=hidden name="lID" value="<%=lDiscountApplyID%>">
<input type="Hidden" name="tsDiscountStartDate" value="<%=tsDiscountStartDate%>"><!--��������-->
<input type="Hidden" name="strDiscountCode" value="<%=strDiscountCode%>">
<!--���ڷ��صĲ���!-->
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<TABLE border=0 class=top height=127 width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>�������롪������Ʊ����ϸ</B></TD></TR>
  <TR>
    <TD height=227>
      <TABLE align=left border=0 height=81 width=100%>
        <TBODY>
        <TR borderColor=#ffffff>
          <TD height=30 width=1>&nbsp;</TD>
          <TD height=30 width=125><U>����Ʊ����ϸ��</U></TD>
          <TD colSpan=4 height=30>&nbsp;</TD>
          <TD height=30 width=3>&nbsp;</TD></TR>
		   <TR borderColor=#ffffff>
          <TD height=30 width=1>&nbsp;</TD>
          <TD colSpan=2 height=17>�����������ƣ�
		  <select class='box'><option>
<%=LOANConstant.SubLoanType.getName(Long.valueOf(subtypeid).longValue())%>
			</option></select>
			</TD>
          <TD colSpan=4 height=30>&nbsp;</TD>
          <TD height=30 width=3>&nbsp;</TD></TR>
 
	<% for (int i=1; i<=nArrayNumber; i++) { %>
		
		<TR borderColor=#ffffff>
          <TD height=17 width=1>&nbsp;</TD>
          <TD colSpan=6 height=17>&nbsp;</TD>
		</TR>
        <TR borderColor=#ffffff>
          <TD height=17 width=1>&nbsp;</TD>
          <TD colSpan=6 height=17>Ʊ�� <%=DataFormat.formatInt(i,2,true)%></TD>
		</TR>
		<TR borderColor=#ffffff>
          <TD height=17 width=1>&nbsp;</TD>
          <TD colSpan=6 height=17>
            <HR align=center SIZE=2 width="100%">
          </TD>
		</TR>
		<TR borderColor=#ffffff>
          <TD height=17 width=1>&nbsp;</TD>
          <TD height=17 width=125><font color='#FF0000'>* </FONT>ԭʼ��Ʊ�ˣ�</TD>
          <TD height=17 width=168>
			<INPUT name="strUser<%=i%>" class=box onfocus="nextfield='lAddDay<%=i%>'"  value="<%=strUser%>" size=25 maxlength="100"></TD>
          <TD colSpan=2 height=17>�ڼ������Ӽ�Ϣ������</TD>
          <TD colSpan=2 height=17><INPUT class=box onfocus="this.select();nextfield='strBank<%=i%>'"
            name="lAddDay<%=i%>" size=8 value=""></TD></TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=7 width=125><font color='#FF0000'>* </font>�жҷ���</TD>
          <TD height=7 width=168>
			<INPUT name="strBank<%=i%>" class=box onfocus="nextfield='lIsLocal<%=i%>'" value="" size=25 maxlength="100">
		  </TD>
          <TD colSpan=2 height=31><font color='#FF0000'>* </font>�жҷ����ڵأ�</TD>
          <TD colSpan=2 height=31>
			<SELECT name="lIsLocal<%=i%>" onfocus="nextfield='strFormerOwner<%=i%>'">
				<OPTION value="<%=Constant.YesOrNo.NO%>" selected>�Ǳ���</OPTION>
				<OPTION value="<%=Constant.YesOrNo.YES%>">����</OPTION></SELECT>
		  </TD>
		</TR>
		<TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=7 width=125><font color='#FF0000'>* </font>���ֵ�λֱ��ǰ�֣�</TD>
          <TD height=7 width=168>
			<INPUT name="strFormerOwner<%=i%>" class=box onfocus="nextfield='lAcceptPOTypeID<%=i%>'" value="" size=30 maxlength="100">
		  </TD>
          <TD colSpan=2 height=31><font color='#FF0000'>* </font>Ʊ�����ͣ�</TD>
          <TD colSpan=2 height=31>
		    <SELECT name="lAcceptPOTypeID<%=i%>" onfocus="nextfield='tsCreate<%=i%>'">
				<OPTION value="<%=OBConstant.DraftType.BANK%>" selected><%=OBConstant.DraftType.getName(OBConstant.DraftType.BANK)%></OPTION>
				<OPTION value="<%=OBConstant.DraftType.BIZ%>"><%=OBConstant.DraftType.getName(OBConstant.DraftType.BIZ)%></OPTION>
			</SELECT>
		  </TD>
		</TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=31 width=125><font color='#FF0000'>* </font>��Ʊ�գ�</TD>
          <TD height=31 width=168>
          <fs_c:calendar 
          	    name="tsCreate<%=i%>"
	          	value="" 
	          	properties="nextfield ='tsEnd<%=i%>'" 
	          	size="18"/>
          </TD>
          <TD colSpan=2 height=31><font color='#FF0000'>* </font>�����գ�</TD>
          <TD colSpan=2 height=31>
           <fs_c:calendar 
						          	    name="tsEnd<%=i%>"
							          	value="" 
							          	properties="nextfield ='strCode<%=i%>'" 
							          	size="18"/>
		</TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=31 width=125><font color='#FF0000'>* </font>��Ʊ���룺</TD>
          <TD height=31 width=168><INPUT            name="strCode<%=i%>" class=box onfocus="nextfield='dAmount<%=i%>'" value="" size=18 maxlength="100"></TD>
          <TD height=31 width=103><font color='#FF0000'>* </font>��Ʊ��</TD>
          <TD height=31 width=36>
            <DIV align=right>��</DIV></TD>
          <TD colSpan=2 height=31>
		  <% if (i == nArrayNumber) { %>
			<script language="javascript">
			createAmountCtrl("frm", "dAmount<%=i%>", "", "insertbill", null);
			</script>		
		  <% } else { %>
			<script language="javascript">
			createAmountCtrl("frm", "dAmount<%=i%>", "", "strUser<%=i+1%>", null);
			</script>
		  <% } %>
		  </TD>
		</TR>
	<% } %>
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD colSpan=6 height=31>
            <HR align=center SIZE=2 width="100%">
          </TD></TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=3>&nbsp;</TD>
		  <TD colSpan=6 height=31>
            <DIV align=right>
				<INPUT class=button type=button name="insertbill" onFocus="nextfield='submitfunction';" onclick="confirmSave(frm);" value=" �� �� "> 
				
				<%if (lDiscountBillID > 0) { %>
				<INPUT class=button type=button name="deletebill" onclick="confirmDel(frm)" value=" ɾ ��">
				<% } %>
				<INPUT class=button type=button name="comeback" onclick="goBack();" value=" �� ��"> 
            </DIV></TD>
          </TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>
</form>
<script language="javascript">
//����
function goBack()
{
   if (confirm("�Ƿ񷵻أ�"))
   {
       frm.action="d008-c.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
		return true;
   }	 
}

//��űȽ�
function CodeCompare(d_input1,d_input2,d_str)
{
	if (d_input1.value.length>0 && d_input2.value.length>0)
	{
		if (d_input1.value>d_input2.value)
		{
			alert(d_str);
			d_input1.focus();
			return (false);
		}
	}
	return true;
}
function checkInput(i)
{
  	if (eval("frm.strUser"+i+".value.length") > 0) 
	{
		return true;
	}

	if (eval("frm.lAddDay"+i+".value.length") > 0) 
	{
		return true;
	}

	if (eval("frm.strBank"+i+".value.length") > 0)
	{
		return true;
	}

	if (eval("frm.strFormerOwner"+i+".value.length") > 0)
	{
		return true;
	}

	if (eval("frm.tsCreate"+i+".value.length") > 0)
	{
		return true;
	}

	if (eval("frm.tsEnd"+i+".value.length") > 0)
	{	
		return true;
	}
						
	if (eval("frm.strCode"+i+".value.length") > 0)
	{
		return true;
	}

	if (eval("frm.dAmount"+i+".value.length") > 0)
	{
		return true;
	}
	return false;
}
function confirmSave(frm)
{
  	var sTmp = "";
	var nNum = 0;
	for (var i = 1; i <= <%=nArrayNumber%>; i++)
	{
		if (checkInput(i))
		{
		sTmp = "Ʊ�� " + (i < 10 ? "0" : "") + i + " ��";
		if (!InputValid(eval("frm.strUser"+i), 1, "string", 0, 0, 0,sTmp+"ԭʼ��Ʊ��")) 
		{
			return false;
		}
		
		if (!InputValid(eval("frm.lAddDay"+i), 0, "int", 0, 0, 0,sTmp+"�ڼ������Ӽ�Ϣ����")) 
		{
			return false;
		}

		if (eval("frm.lAddDay"+i+".value.length") > 0 && eval("frm.lAddDay"+i+".value") < 0)
		{
			alert("�ڼ������Ӽ�Ϣ����Ӧ���ڵ���0��");
			return false;
		}

		if (!InputValid(eval("frm.strBank"+i), 1, "string", 0, 0, 0,sTmp+"�жҷ�")) 
		{
			return false;
		}

		if (!InputValid(eval("frm.strFormerOwner"+i), 1, "string", 0, 0, 0,sTmp+"���ֵ�λֱ��ǰ��")) 
		{
			return false;
		}

		if (!checkDate(eval("frm.tsCreate"+i),1,sTmp+"��Ʊ��"))
		{
			return(false);
		}

		if (!checkDate(eval("frm.tsEnd"+i),1,sTmp+"������"))
		{	
			return(false);
		}

		if (!CodeCompare(eval("frm.tsCreate"+i), eval("frm.tsEnd"+i), sTmp+"��Ʊ��Ӧ��С�ڵ����գ�"))
		{
			return(false);
		}
		
		if (!CodeCompare(eval("frm.tsCreate"+i), eval("frm.tsDiscountStartDate"), sTmp+"��Ʊ��Ӧ��С�������գ�"))
		{
			return(false);
		}
		
		if (!CodeCompare( eval("frm.tsDiscountStartDate"),eval("frm.tsEnd"+i), sTmp+"������Ӧ�ô��������գ�"))
		{
			return(false);
		}
			
		if (!InputValid(eval("frm.strCode"+i), 1, "string", 0, 0, 0,sTmp+"��Ʊ����")) 
		{
			return false;
		}

		if (!checkAmount(eval("frm.dAmount"+i),1,sTmp+"��Ʊ���")) 
		{
			return false;
		}
		nNum++;
		}
	}
	if (nNum == 0)
	{
		alert("������һ��Ʊ�ݣ�");
		return false;
	}
	
	if(confirm("�Ƿ��ύ��"))
	{
		frm.action="d011-c.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
		return true;
	}	
	
}
firstFocus(frm.strUser1);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 
</script>			


<%	
        //��ʾ�ļ�β
		OBHtml.showOBHomeEnd(out);	   
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>

