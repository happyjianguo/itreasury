<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
 //response.setHeader("Cache-Control","no-stored");
 //response.setHeader("Pragma","no-cache");
 //response.setDateHeader("Expires",0);
%>
<%
	/* ����̶����� */
	String strTitle = "[����ƾ֤]";
%>	
<%!
public int IsInVector (Vector v,long lID)
{
    int i = 0;
    long lNum = -1;
	boolean flag = false;
	
	try{  	
		if (v != null && v.size() > 0)
		{
	 		while (i < v.size()) 
 	 		{
	      		Long lTmp = (Long) v.get(i);
				lNum = lTmp.longValue();
				if (lNum == lID)
				{					
					flag = true;
					break;
				}
				i++;
			}
		}
	}
	catch(Exception e) 
	{
    	System.out.println(e.toString());
    }
    
    return ((flag == true) ? i : -1);    
}
%>
<%
//////////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
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

		//�����������ȡ�������

		String strTmp = "";
		String strControl = "";
		
		int tmpInt = 0;
		int lastPageNum = 0;
		int delNum = 0;
		
		long lBillID = -1;
		String strBillID = "";
		Vector v = null;

		long lContractID = -1;			//���ֺ�ͬ
		long lLoanID = -1;				//��������
		long lCredenceID = -1;

		double llv = 0;					//��������
		Timestamp rq = null;			//��������
		Timestamp tsEnd = null;			//��������
		String strEnd = "";				//��������
		int nDays = 0;					//ʵ����������
		double dAccrual = 0;			//������Ϣ
		double dRealAmount = 0;			//ʵ�����ֽ��
		long lCount = 0;				//Ʊ���ܱ���
		double dTotalAccrual = 0;		//����������Ϣ
		double dTotalRealAmount = 0;	//����ʵ�����ֽ��

		Collection temp = null;

		// ��ҳ����
		long lPageCount = 1;                   //��ҳ
		long lPageNo = 1;                      //�ڼ�ҳ
		long lOrderParam = 1;                  //����ʲô����
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //�����Ƿ���
		//Ϊ��ѯ����Ʊ����ϸ�����
		String BillInfo = "";
	    strTmp = (String)request.getAttribute("BillInfo");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     BillInfo = strTmp;
		}

       	if (request.getAttribute("temp") != null)
       	{
           	temp = (Collection)request.getAttribute("temp");
       	}


///////control////////////////////////////////////////////////////////////////////////
		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
//����
		//�ж�����ͷ���
		strTmp = (String)request.getAttribute("lDesc");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lDesc = Long.parseLong(strTmp.trim());
		}
		
		//�ж���������
		strTmp = (String)request.getAttribute("lOrderParam");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lOrderParam = Long.parseLong(strTmp.trim());
		}

		//��ʾҳ��
		strTmp = (String)request.getAttribute("lPageNo");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lPageNo = Long.parseLong(strTmp.trim());
		}

		OBHtml.showOBHomeHead(out,sessionMng,"����Ʊ�ݼ�Ϣ��ϸ��",Constant.ShowMenu.YES);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm" method="post">

<TABLE border=0 class=top height=133 width=740>
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>����Ʊ�ݼ�Ϣ��ϸ��</B></TD></TR>  
  <TR>
    <TD height=195>
      <TABLE cellPadding=0 height=112 width="99%">
        <TBODY>    
        <TR>          
          <TD colSpan=3 height=170 vAlign=top>
            <TABLE border=0 borderColor=#999999 class=ItemList height=73 
            width=740>
              <TBODY>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">                
				<TD class=ItemTitle height=20 width=43>&nbsp;</TD>
				<TD class=ItemTitle height=20 width=50><A href="javascript:go('1');">���к�</A></TD>
                <TD class=ItemTitle height=20 width=96><A href="javascript:go('8');">��Ʊ����</A></TD>
                <TD class=ItemTitle height=20 width=116><A href="javascript:go('9');">��Ʊ���</A></TD>
                <TD class=ItemTitle height=20 width=87>������</TD>
                <TD class=ItemTitle height=20 width=87><A href="javascript:go('6');">������</A></TD>
			    <TD class=ItemTitle height=20 width=87><A href="javascript:go('7');">�ڼ�����������</A></TD>
                <TD class=ItemTitle height=20 width=92>ʵ����������</TD>
                <TD class=ItemTitle height=20 width=101>��������</TD>
			    <TD class=ItemTitle height=20 width=111>������Ϣ</TD>
                <TD class=ItemTitle height=20 width=111>ʵ�����ֽ��</TD></TR>

<% /************************* Information Display********************************/ %>	
	<%	
                if( (temp != null) && (temp.size() > 0) )
                {
					Iterator it = temp.iterator();
                    while (it.hasNext() )
					{
						DiscountBillInfo info = ( DiscountBillInfo ) it.next();                     
						lPageCount = info.getCount() / Constant.PageControl.CODE_PAGELINECOUNT;
						//lPageCount = info.lCount / 1;
            
						//if ((info.lCount % 1) != 0)
						if ((info.getCount() % Constant.PageControl.CODE_PAGELINECOUNT) != 0)
						{
							lPageCount ++;
						}

						lCount = info.getCount();
						dTotalAccrual = info.getTotalInterest();
						dTotalRealAmount = info.getTotalAmount();
	%>
        <TR align="center"  borderColor=#999999>
		  <TD class=ItemBody height=24 width=43>
<%if(BillInfo.equals("yes"))
{%>
		  	<INPUT name="checkbox" type="checkbox" value="<%=info.getID()%>" 
	<%	//������Ʊ�ݱ�����ƾ֤ѡ��
		if (info.getOBDiscountCredenceID() > 0 && info.getOBDiscountCredenceID() != lCredenceID){
			out.println("disabled");
		}
		//������Ʊ�ݱ���ǰƾ֤ѡ��
		else if (info.getOBDiscountCredenceID() > 0 && info.getOBDiscountCredenceID() == lCredenceID)
		{
			out.println("checked"); 
		}
		//������Ʊ��δ��ѡ��
		else
		{
			out.println("");
		}
	%>>
<%}else{%>
		  	<INPUT name="checkbox" type="checkbox" value="<%=info.getID()%>" <% if (IsInVector(v,info.getID()) > -1) 
		out.println("checked"); else if (info.getDiscountCredenceID() > 0||info.getOBDiscountCredenceID()>0) out.println("disabled"); %>>
<%}%>		 
		 </TD>
		  <TD class=ItemBody>
		  <%=DataFormat.formatInt(info.getSerialNo(),3,true)%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.formatString(info.getCode())%>
		  </TD>
          <TD align="center" class=ItemBody>��
		  <%if ((info!=null)&&(info.getAmount()>0)) {out.println(DataFormat.formatDisabledAmount(info.getAmount()));} else {out.println("0.00");}%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getDiscountDate())%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getEnd())%> 
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=info.getAddDay()%> ��
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=info.getDays()%> ��
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=DataFormat.formatRate(info.getDiscountRate())%> %
		  </TD>
		  <TD align="center" class=ItemBody>��
		  <%if (info.getInterest()>0) {out.println(DataFormat.formatDisabledAmount(info.getInterest()));} else {out.println("0.00");}%>
		  </TD>
		  <TD align="center" class=ItemBody>��
		  <%if (info.getCheckAmount()>0) {out.println(DataFormat.formatDisabledAmount(info.getCheckAmount()));} else {out.println("0.00");}%>	  
		  </TD>
		</TR>
<% /*************************The If and while 's End ********************************/ %>
        <%
		           }
	            }
			   else{
			   %>
         <TR align=center borderColor=#999999>
          <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
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
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar 
            height=50 width="100%">
              <TBODY>
              <TR>
                <td width="500" height="2">�������ֱ���������<%=lCount%><BR>
����������Ϣ�������� <%if (dTotalAccrual>0) {out.println(DataFormat.formatDisabledAmount(dTotalAccrual));} else {out.println("0.00");}%><BR>����ʵ�����ֽ��� <%if (dTotalRealAmount>0) {out.println(DataFormat.formatDisabledAmount(dTotalRealAmount));} else {out.println("0.00");}%>
</td>
<TD height=2 width="428" valign=bottom></TD>
     </TR>
   </TBODY></TABLE></TD></TR></TBODY></TABLE>            
</TD></TR>
			
  <TR>
    <TD height=40 vAlign=bottom>
      <TABLE align=center border=0 height=40 width="99%">
        <TBODY>
        <TR borderColor=#ffffff>
          <TD colSpan=4 height=1>
		  <input type="button" name="chkall" value=" ȫ ѡ " onclick="selectAll(this.form,1)">
            
			<DIV align=right>
				<!--INPUT class=button name="backstep" onClick="MM_goToURL('self','d030-v.jsp?lContractID=<%=lContractID%>&control=view');" onfocus="nextfield='submitfunction';" type="button" value=" ��һ�� "-->
				<INPUT class=button name="backstep" onClick="backto(frm);" onfocus="nextfield='submitfunction';" type="button" value=" ��һ�� ">
				<input type="button" name="nextstep" value=" ��һ�� " class="button" onclick="confirmSave(frm);">
            </DIV></TD></TR></TBODY></TABLE></TD></TR>

</TBODY></TABLE></TD></TR></TBODY></TABLE>

<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
<input type="hidden" name="lPageNo" value="<%=lPageNo%>">
<input type="hidden" name="lOrderParam" value="<%=lOrderParam%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="hidden" name="control" value="view">
<input type="hidden" name="lContractID" value="<%=lContractID%>">
<input type="hidden" name="lCredenceID" value="<%=lCredenceID%>">
<input type="hidden" name="BillInfo" value="<%=BillInfo%>">
<input type="hidden" name="nextPage" value="">

<P><BR></P>
</form>

<script language="javascript">

/*function printPage()
{	
	window.open("S117-p.jsp?lContractID=<%=lContractID%>&lPageNo=<%=lPageNo%>&lOrderParam=<%=lOrderParam%>&lDesc=<%=lDesc%>&llv=<%=llv%>&rq=<%=DataFormat.getDateString(rq)%>&control=view&isSM=<%=Constant.YesOrNo.NO%>","popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");	
}*/
/*
function key_down()
{
	if (window.event.keyCode == 13)
	{
		if (InputValid(frm.cz,1, "int", 1, 1, frm.lPageCount.value,"ҳ��")) 
		{
			frm.lPageNo.value=frm.cz.value;
		}
		else	return (false);
		confirmSave(frm);
	}
}
*/
function go(para)
{  
	if (frm.lOrderParam.value == para)
	{
		if (frm.lDesc.value == "<%=Constant.PageControl.CODE_ASCORDESC_ASC%>")
		{
			frm.lDesc.value = "<%=Constant.PageControl.CODE_ASCORDESC_DESC%>";
		}
		else
		{
	   		frm.lDesc.value = "<%=Constant.PageControl.CODE_ASCORDESC_ASC%>"; 
		}
	}
	frm.lOrderParam.value = para;
	frm.control.value = "view";
	frm.action = "../discountapply/d031-c.jsp";
	showSending();
	frm.submit();
}
/*
function goto()
{
	var lMax=frm.lPageCount.value;
	if (InputValid(frm.cz,1, "int", 1, 1, lMax,"ҳ��"))
	{
		frm.lPageNo.value=frm.cz.value;
	}
	else return (false);
	confirmSave(frm);
}

function gohere()
{
	confirmSave(frm);
}
*/
function selectAll(frm,str){
		for (var i=0;i<frm.elements.length;i++){
			if(document.frm.elements[i].disabled==false){
			if (frm.elements[i].type == "checkbox" && str=="1"){
				frm.elements[i].checked = true;
			}
			}
		}
}

function countAll()
{
	frm.lPageNo.value="";
	confirmSave(frm);
}

function confirmSave(frm)
{		
	var bSelect;
	bSelect = false;
	var isCheckAll = 0;
	for (var i = 0; i < document.frm.elements.length; i++) {
			if (document.frm.elements[i].checked == true){
				isCheckAll++;
		}
	}
	//ֻѡ����һ��checkBox,��checkBox����Ϊ����,checkBox[i]������
	if(isCheckAll=="0"){
		alert("��ѡ��");
	}else{
		
			frm.control.value="save";
			frm.action="../discountapply/d033-c.jsp";
			showSending();
			frm.submit();
			return true;
	}
}

function backto(frm)
{
	frm.control.value="view";
	//������޸�ҳ����
	if(frm.BillInfo.value=="yes")
	{
		frm.BillInfo.value="";
		frm.nextPage.value="d037-v.jsp";
		frm.action="../discountapply/d033-c.jsp";
	}else
	{
		frm.action="d030-v.jsp";
	}
	showSending();
	frm.submit();
	return true;
}

<% if( (strBillID == null) || (strBillID.length() == 0) ) { %>
	selectAll(frm,"0");
<% } %>
//firstFocus(frm.txtContractCtrl);
////setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 
selectAll(frm,"0");
</script>			
<%
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"����ƾ֤", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"����ƾ֤",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>