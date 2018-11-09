<%--
 ҳ������ ��d009-v.jsp
 ҳ�湦�� : ������������-����Ʊ���б� ��ʾҳ��
 ��    �� ��gqzhang
 ��    �� ��2004��1��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 java.util.Iterator,
				 java.util.Collection,
				 java.util.*,
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
	   Log.print("*******����ҳ��--ebank/loan/discountapply/d009-v.jsp*******");
	
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
		String strAction = (String)request.getAttribute("txtAction");
		String frompage = (String)request.getAttribute("frompage");
		 int tmpInt = 0;
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
	     long lAddDay = 0; //�ڼ�����������,
	     long lAcceptPOTypeID = -1; //Ʊ������,
	     String strFormerOwner = ""; //���ֵ�λֱ��ǰ��
		
	     long lID = -1; //��������id
         long lPageNo = 1;
         long lOrderParam = -1;
         long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //�����Ƿ���;
		 
		 String strDiscountCode = "";
         
		 long lPageCount = 0;
		 long lCount = 0;
		
		 double dTotalAmount = 0.0;//����ͳ�ƽ��
		 long lBankCount = -1;//���гжһ�Ʊͳ������
		 long lBizCount = -1;//��ҵ�жһ�Ʊͳ������
		 
		 //��������ͳ����Ϣ
		 double dApplyDiscountAmount = 0.0;//���ֻ�Ʊ�ܽ��(����)
		 long lBankAccepPO = -1;//���гжһ�Ʊ����(����)
		 long lBizAcceptPO = -1;//��ҵ�жһ�Ʊ����(����)
		 long lApplyDiscountPO = -1;//��Ʊ������(����)
		 String subtypeid="";
		 //��������
		 Timestamp tsDiscountStartDate = null;	 
		 DiscountInfo discountInfo = null;
		 String type="3";
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};
		 discountInfo = (DiscountInfo)request.getAttribute("resultDiscountInfo");
		 if (discountInfo!=null){
			tsDiscountStartDate =  discountInfo.getDiscountStartDate();
			subtypeid=String.valueOf(discountInfo.getSubTypeId());
		 }
		 		 
		 if(discountInfo != null)
		 {
		    dTotalAmount = discountInfo.getBillAmount();
			lBankCount = discountInfo.getBankCount();
			lBizCount = discountInfo.getBizCount();
			dApplyDiscountAmount = discountInfo.getApplyDiscountAmount();
			lBankAccepPO = discountInfo.getBankAccepPO();
			lBizAcceptPO = discountInfo.getBizAcceptPO();
			lApplyDiscountPO = discountInfo.getApplyDiscountPO();
			
			lPageCount = discountInfo.getBillCount() / Constant.PageControl.CODE_PAGELINECOUNT;
			
			if ((discountInfo.getBillCount() %  Constant.PageControl.CODE_PAGELINECOUNT) != 0)
			{
				lPageCount ++;
			}
			Log.print("\n������������ҳ��:"+lPageCount);
			
			lCount = discountInfo.getBillCount();
			
			Log.print("\n�����������ܱ���:"+lCount);
			
		 }
		 
		 
		 
		//��������id  
		   String strTemp = "";
		   strTemp = (String)request.getAttribute("lID");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lID = Long.valueOf(strTemp).longValue();
		   }
		
		   //�ڼ�ҳ
		   strTemp = (String)request.getAttribute("lPageNo");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lPageNo = Long.valueOf(strTemp).longValue();
		   }
		   
		   //�������
		   strTemp = (String)request.getAttribute("lOrderParam");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lOrderParam = Long.valueOf(strTemp).longValue();
		   }
		   
		   //����/����
		   strTemp = (String)request.getAttribute("lDesc");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			  lDesc = Long.valueOf(strTemp).longValue();
		   }
		   
		   //���ֱ��
		   strTemp = (String)request.getAttribute("strDiscountCode");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			  strDiscountCode = strTemp.trim();
		   }
	     
		  Vector vctResult = (Vector)request.getAttribute("resultInfo");
		  
		  //��������
		    strTemp = (String)request.getAttribute("tsDiscountStartDate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsDiscountStartDate =  DataFormat.getDateTime(strTemp);
			}

		
	  //��ʾ�ļ�ͷ
	  long lTemp = OBConstant.ShowMenu.YES;
	  
	   String modifyFlag = (String)request.getAttribute("modifyFlag");
	   
	  	if (modifyFlag != null && modifyFlag.equals("1"))
		{
			lTemp = OBConstant.ShowMenu.NO;
		}
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, lTemp);
	  System.out.println(subtypeid+"^^^^^^^^^^^^^^^^^^^"+loanType);
	
%>		
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post">
<input type="hidden" name="lID" value="<%=lID%>"><!--��������id-->
<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
<input type="hidden" name="lPageNo" value="<%=lPageNo%>">
<input type="hidden" name="lOrderParam" value="<%=lOrderParam%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="Hidden" name="strDiscountCode" value="<%=strDiscountCode%>">
<input type="Hidden" name="tsDiscountStartDate" value="<%=tsDiscountStartDate%>"><!--��������-->
<input type="Hidden" name="subtypeid" value="<%=subtypeid%>"><!--������-->

<!--���ڷ��صĲ���!-->
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<TABLE border=0 class=top height=133 width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>�������롪������</B></TD></TR>
  <TR>
    <TD height=195>
      <TABLE cellPadding=0 height=112 width=730>
        <TBODY>
        <TR>
          <TD colSpan=2 height=17>���������ţ�<%=strDiscountCode%></TD>
		   <TD colSpan=2 height=17>�����������ƣ�
		   <select class='box'><option>
<%=LOANConstant.SubLoanType.getName(discountInfo.getSubTypeId())%>
			</option></select></TD>
		  </TR>
        <TR>
          <TD colSpan=4 height=20 vAlign=top>
            <HR align=center SIZE=2 width="100%">
          </TD></TR>
        <TR>
          <TD height=20 width="50%"><U>����Ʊ����ϸ��</U></TD>
          <TD height=20 align=right width="50%" colSpan=3>
            <DIV align=right>
			<%
		if (modifyFlag == null )
		{
	%>
			  <INPUT class=button type=button name="LastStep" onClick="doGoLast();" value=" ��һ�� "> 
			  <INPUT class=button type=button name="addBill"  value=" �� �� "  onClick="DoAddBill();"> 
			  <INPUT class=button type=button name="import" onclick="openBrWindow('d016-v.jsp?lID=<%=lID%>');" value=" �� �� ">
			  <%}%>
            </DIV>
		  </TD>
        </TR>
        <TR>
          <TD colSpan=4 height=170 vAlign=top>
            <TABLE border=0 borderColor=#999999 class=ItemList height=73 
            width="730">
              <TBODY>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
                <TD class=ItemTitle colSpan=11 height=27>
                  <DIV align=left>�������뵥λ��<%=sessionMng.m_strClientName%></DIV></TD></TR>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
                <TD class=ItemTitle height=20 >&nbsp;</TD>
                <TD class=ItemTitle height=20 nowrap><A href="javascript:go('1');">���к�</A></TD>
                <TD class=ItemTitle height=20 width="50"><A href="javascript:go('2');">ԭʼ��Ʊ��</A></TD>
                <TD class=ItemTitle height=20 width="50"><A href="javascript:go('3');">�жҷ�</A></TD>
                <TD class=ItemTitle height=20><A href="javascript:go('4');">�жҷ����ڵ�</A></TD>
                <TD class=ItemTitle height=20><A href="javascript:go('5');">��Ʊ��</A></TD>
                <TD class=ItemTitle height=20><A href="javascript:go('6');">������</A></TD>
                <TD class=ItemTitle height=20 width="80"><A href="javascript:go('7');">��Ʊ����</A></TD>
                <TD class=ItemTitle height=20 width="80"><A href="javascript:go('8');">��Ʊ���</A></TD>
                <TD class=ItemTitle height=20 width="80"><A href="javascript:go('9');">�ڼ�����������</A></TD>
				<TD class=ItemTitle height=20><A href="javascript:go('10');">��Ʊ����</A></TD>
              </TR>

	<%	
			    if( (vctResult != null) && (vctResult.size() > 0) )
                {
					Log.print("\n\n============��ѯ�����С��"+vctResult.size()+"\n\n");
                    for(int i=0; i<vctResult.size();i++)
					{
						DiscountBillInfo discountBillInfo = ( DiscountBillInfo ) vctResult.elementAt(i);                     
						
						
						
						if(discountBillInfo != null)
						{
						  lDiscountBillID = discountBillInfo.getDiscountBillID(); 
	     				  lDiscountApplyID = discountBillInfo.getDiscountApplyID();
						  Log.print("===========��������id:"+lDiscountApplyID);
                 	      lDiscountCredenceID = discountBillInfo.getDiscountCredenceID(); 
	                      strUser = discountBillInfo.getUser(); 
	                      strBank = discountBillInfo.getBank(); 
	                      lSerialNo = discountBillInfo.getSerialNo(); 
	                      lIsLocal = discountBillInfo.getIsLocal(); 
	                      tsCreate = discountBillInfo.getCreate(); 
	                      tsEnd = discountBillInfo.getEnd(); 
	                      strCode = discountBillInfo.getCode(); 
	                      dAmount = discountBillInfo.getAmount(); 
	                      lAddDay = discountBillInfo.getAddDay(); 
	                      lAcceptPOTypeID = discountBillInfo.getAcceptPOTypeID(); 
	                      strFormerOwner = discountBillInfo.getFormerOwner(); 
						  
						}
	%>

        <TR align="center" borderColor=#999999>
		  <TD class=ItemBody height=24>
		  	<INPUT name="lIDs" type=checkbox value="<%=lDiscountBillID%>" onClick="check(this);">
		  </TD>
          <TD class=ItemBody>
          <a href="d013-c.jsp?lDiscountBillID=<%=lDiscountBillID%>&lID=<%=lDiscountApplyID%>&tsDiscountStartDate=<%=tsDiscountStartDate%>&strDiscountCode=<%=strDiscountCode%>&modifyFlag=<%=modifyFlag%>&subtypeid=<%=subtypeid%>">
		  <%=lSerialNo%>
		  </a>
		  </TD>
          <TD align="center" width="50" class=ItemBody>
		  <%=strUser%>&nbsp;
		  </TD>
          <TD align="center" width="50" class=ItemBody>
		  <%=strBank%>
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%
				if (lIsLocal ==Constant.YesOrNo.NO) 
				{
					out.println("�Ǳ���");
				} 
				else if (lIsLocal == Constant.YesOrNo.YES) 
				{
					out.println("����");
				}
		  %>
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%=DataFormat.getDateString(tsCreate)%> 
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%=DataFormat.getDateString(tsEnd)%>
		  </TD>
		  <TD align="center" class=ItemBody width="80">
		  <%=strCode%>
		  </TD>
		  <TD align="center" class=ItemBody width="80">
		  <%=DataFormat.formatNumber(dAmount,2)%>		 
		  </TD>
		  <TD align="center" class=ItemBody width="80">
		  <%if (lAddDay>0) {out.println(lAddDay);} else {out.println("0");}%> 
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%if (lAcceptPOTypeID>0) {out.println(LOANConstant.DraftType.getName(lAcceptPOTypeID));} else {out.println("&nbsp;");}%>
		  </TD>
		</TR>
        <%
		           }
	            }
			   else{
			   %>
         <TR align=center borderColor=#999999>
          <TD class=ItemBody height=24>
			<INPUT name=checkbox2 type=checkbox value=checkbox onClick="doTag(this);">
		  </TD>
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody width="50">&nbsp;</TD>
          <TD class=ItemBody width="50">&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD></TR>			   
			   <%			   
			   }
        %>
				  
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=11 height=25>
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar height=50 width="100%">
              <TBODY>
              <TR>                
				<td width="500" height="2">�����ܼƣ�<%=lCount%>&nbsp;&nbsp;����ܼƣ���<% if (dTotalAmount > 0) out.println(DataFormat.formatListAmount(dTotalAmount)); else out.println("0.00"); %>
				</td>
				<TD height=2 width="500" align=right>
                 P. 
<INPUT class=SearchBar_Page maxLength=3 name="cz" size=3 onkeydown="key_down()" value="<%=lPageNo%>"> of <%=lPageCount%> 
<INPUT class=SearchBar_Btn name="aaaaa" type=button onclick="goto()" value=go> 

<%if(lPageNo > 1) {%>
<input type="button" name="Submit4222" value="|&lt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value='1';gohere();">
<%}%>
<%if(lPageNo > 1) {%>
<input type="button" name="Submit5222" value="&lt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageNo-1%>;gohere();">
<%}%>
<%if(lPageNo < lPageCount) {%>
<input type="button" name="Submit6222" value="&gt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageNo+1%>;gohere();">
<%}%>
<%if(lPageNo < lPageCount) {%>
<input type="button" name="Submit7222" value="&gt;|" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageCount%>;gohere();">
<%}%>

	</TD></TR>
   </TBODY></TABLE></TD></TR></TBODY></TABLE>
	<BR>
	<%
		if (modifyFlag == null )
		{
	%>
	<INPUT class=button name="submitDel" onclick="confirmDel(frm);" type=button value=" ɾ �� ">
	<%}%>
	<BR>
    <HR>
    <DIV align=right>
	<%
		if (modifyFlag != null && modifyFlag.equals("1"))
		{
	%>
		<INPUT class=button name="submitApply" onclick="window.close();" type=button value=" �� �� ">
	<%
	}
	else
	{
	%>
		<INPUT class=button name="submitApply" onclick="return confirmSave(frm);" type=button value=" �� �� ">
    <%}%>
	</DIV>
</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>
</form>

<script language="javascript">
var num =0;
var tag = 0;
function check(box)
{
  if(box.checked == true)
  {
   num++;
  }
  else
  {
    num --;
  }
 // alert(num);

}

function doTag(box)
{
  tag++;
}

function doGoLast()//��һ��
{
  if(confirm("�Ƿ񷵻�?"))
  {
	   <%if(frompage != null && frompage.equals("query")){%>
		document.location.href="<%=Env.EBANK_URL%>loan/query/q001-c.jsp?instrTypeID=3&parentID=<%=lID%>";
	 <%}else{%>
		 document.location.href ="d005-c.jsp?lID=<%=lID%>&tsDiscountStartDate=<%=tsDiscountStartDate%>";
	  <%}%>
  }
}

function DoAddBill()//����
{
   if(confirm("�Ƿ�������"))
   {
	    frm.action="d010-v.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
   }	 
}


function openBrWindow(theURL)
{
	window.open(theURL,"popup", "width=800,height=400,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0,screenX=0,screenY=0,left=150,top=150");
}

function key_down()
{
	if (window.event.keyCode == 13)
	{
		if (InputValid(frm.cz,1, "int", 1, 1, frm.lPageCount.value,"ҳ��")) 
		{
			frm.lPageNo.value=frm.cz.value;
		}
		else	
		{
			return (false);
		}
		confirmSave(frm);
	}
}

function go(para)
{  
	if (frm.lOrderParam.value==para)
	{
		if (frm.lDesc.value=="<%=Constant.PageControl.CODE_ASCORDESC_ASC%>")
		{
			frm.lDesc.value="<%=Constant.PageControl.CODE_ASCORDESC_DESC%>";
		}
		else
		{
			frm.lDesc.value="<%=Constant.PageControl.CODE_ASCORDESC_ASC%>"; 
		}
	}
	frm.lOrderParam.value=para;
	 frm.action="d008-c.jsp";
		showSending();
		frm.submit();
		return true;
}

function goto()
{
	var lMax=frm.lPageCount.value;
	if (InputValid(frm.cz,1, "int", 1, 1, lMax,"ҳ��"))
	{
		frm.lPageNo.value=frm.cz.value;
	}
	else 
	{
		return (false);
	}
	
	   frm.action="d008-c.jsp";
		showSending();
		frm.submit();
		return true;
}

function gohere()
{
	    frm.action="d008-c.jsp";
		showSending();
		frm.submit();
		return true;
}

function confirmDel(frm)
{  			
	if(tag > 0)
	{
	  alert("û�п�ɾ����Ʊ�ݼ�¼��")
	  return;
	}
	
	if(num <= 0)
	{
	  alert("��ѡ����Ҫɾ����Ʊ�ݼ�¼��")
	  return;
	
	}
	if(confirm("�Ƿ�Ҫɾ��ѡ�е�Ʊ����Ϣ��"))
	{
		frm.action="d012-c.jsp";
		showSending();
		frm.submit();
		return true;
		
	}			  
}

function confirmSave(frm)
{  	
   if(<%=dApplyDiscountAmount%> != <%=dTotalAmount%>)
	{
		alert("���ֻ�Ʊ�ܽ��Ӧ��������������ȣ�");
		return false;
	}
	if(<%=lBankAccepPO%> != <%=lBankCount%>)
	{
		alert("���гжһ�Ʊ����Ӧ������������ȣ�");
		return false;
	}
	if(<%=lBizAcceptPO%> != <%=lBizCount%>)
	{
		alert("��ҵ�жһ�Ʊ����Ӧ������������ȣ�");
		return false;
	}
	if(<%=lApplyDiscountPO%> != <%=lBankCount+lBizCount%>)
	{
		alert("��Ʊ����Ӧ������������ȣ�");
		return false;
	}

	if(confirm("�Ƿ񱣴棿"))
	{
		frm.action="d017-c.jsp";
		showSending();
		frm.submit();
	}
	
}

/*
function confirmApply(frm)
{
  confirmSave(frm);
  if(confirm("�Ƿ񱣴棿"))
  {
	frm.action="d020-c.jsp?lID=<%=lID%>&lStatusID=<%=OBConstant.LoanInstrStatus.SUBMIT%>";
	showSending();
	frm.submit();
	return true;
  }
}
*/
firstFocus(frm.submitApply);
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

