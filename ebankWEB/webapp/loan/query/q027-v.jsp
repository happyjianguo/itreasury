<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
                com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.obsystem.bizlogic.*,
                com.iss.itreasury.ebank.obquery.bizlogic.*,
                com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
    			com.iss.itreasury.loan.discount.bizlogic.*,
                com.iss.itreasury.loan.discount.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
                com.iss.itreasury.loan.contract.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
//////////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
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

		//�����������ȡ�������

		String strTmp = "";
		String strControl = "";
		String backurl = "";
		String backpage = "";

		int tmpInt = 0;
		int lastPageNum = 0;
		int delNum = 0;
		
		long lBillID = -1;
		long[] lBillIDArray = new long[1000]; 
		String strBillID = "";
		Vector v = null;

		long lContractID = -1;
		long lCredenceID = -1;
		long lDiscountID = -1;
		
		long txtContract = -1;			//���ֱ�ʾ
		String txtContractCode = "";	//����������

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
		double dTotalAmount = 0;

		String strApplyClient = "";

		Collection temp = null;
		DiscountLoanInfo		dli = new DiscountLoanInfo ();
		DiscountCredenceInfo	dci = new DiscountCredenceInfo ();
		DiscountBillInfo		dbi = new DiscountBillInfo ();
			
		OBLoanQuery Discount = new OBLoanQuery();

		ClientInfo clientinfo = new ClientInfo();
		
		ContractInfo cinfo=new ContractInfo();
		OBContractQuery contract=new OBContractQuery();

		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem loanCommonSetting = home.create();
				
		// ��ҳ����
		long lPageCount = 1;                   //��ҳ
		long lPageNo = 1;                      //�ڼ�ҳ
		long lOrderParam = 1;                  //����ʲô����
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //�����Ƿ���

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

///////control////////////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}
		
		strTmp = (String)request.getAttribute("backurl");
        if(strTmp != null && strTmp.length() > 0)
        {
             backurl = strTmp.trim();
        }

		strTmp = (String)request.getAttribute("backpage");
        if(strTmp != null && strTmp.length() > 0)
        {
             backpage = strTmp.trim();
        }

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

		strTmp = (String)request.getAttribute("lDiscountID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lDiscountID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("strBillID");
        if(strTmp != null && strTmp.length() > 0)
        {
             strBillID = strTmp.trim();
			 /**
			  * ��һ����","�ֿ��Ĵ��ֽ�Ϊһ��Vector������
			  * @param strParam ��Ҫ��ֵĲ���
			  * @return ����һ��Vector��������Long��
			  */
			 
			 v = DataFormat.changeStringGroup(strBillID);
			 /*
			 if( (v != null) && (v.size() > 0) )
			 {
				Iterator it = v.iterator();
                while (it.hasNext() )
                {
					lBillID = ( long ) it.next();
				}
			 }
			 */
		}

////////view//////////////////////////////////////////////////////////////////////////
		if (strControl.equals("view"))
		{
			if (lContractID > 0)		
            {
				temp = Discount.findBillInterestByID(lContractID,-1,1000,1,lOrderParam,lDesc);
				cinfo = contract.findByID(lContractID);
				clientinfo = loanCommonSetting.findClientByID(cinfo.getBorrowClientID());
				strApplyClient = DataFormat.formatString(clientinfo.getName());
			}
			else if (lDiscountID > 0)		
            {
				temp = Discount.findBillInterestByID(lDiscountID,1000,1,lOrderParam,lDesc);
				dli = Discount.findDiscountByID(lDiscountID);
				clientinfo = loanCommonSetting.findClientByID(dli.getApplyClientID());
				strApplyClient = DataFormat.formatString(clientinfo.getName());
			}
		}


		//����ǵ�������
		long lShowMenu = Constant.YesOrNo.NO;
  		strTmp = (String)request.getAttribute("isSM");
		if(strTmp != null && strTmp.length() > 0)
		{
			lShowMenu = Long.parseLong(strTmp);
		}

		OBHtml.showOBHomeHead(out,sessionMng,"����Ʊ�ݼ�Ϣ��ϸ��",lShowMenu);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">

<TABLE border=0 class=top height=133 width="90%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>����Ʊ�ݼ�Ϣ��ϸ��</B></TD></TR>
  <TR>
    <TD height=29>��λ��<%=strApplyClient%></TD></TR>
  <TR>
    <TD height=195>
      <TABLE cellPadding=0 height=112 width="99%">
        <TBODY>    
        <TR>          
          <TD colSpan=3 height=170 vAlign=top>
            <TABLE border=0 borderColor=#999999 class=ItemList height=73 
            width=730>
              <TBODY>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
				<TD class=ItemTitle height=20 width="5%"><A href="javascript:go('1');">���к�</A></TD>
                <TD class=ItemTitle height=20 width="8%"><A href="javascript:go('7');">��Ʊ����</A></TD>
                <TD class=ItemTitle height=20 width="15%"><A href="javascript:go('8');">��Ʊ���</A></TD>
                <TD class=ItemTitle height=20 width="10%">������</TD>
                <TD class=ItemTitle height=20 width="10%"><A href="javascript:go('6');">������</A></TD>
				<TD class=ItemTitle height=20 width="8%"><A href="javascript:go('7');">�ڼ�����������</A></TD>
                <TD class=ItemTitle height=20 width="8%">ʵ����������</TD>
                <TD class=ItemTitle height=20 width="8%">��������</TD>
			    <TD class=ItemTitle height=20 width="13%">������Ϣ</TD>
                <TD class=ItemTitle height=20 width="15%">ʵ�����ֽ��</TD>
			  </TR>

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
/*	
//����Ʊ�ݵļ�Ϣ��EJB�д���
					   strEnd = info.tsEnd.toString();
					   tsEnd = new java.sql.Timestamp(new Integer(strEnd.substring(0,4)).intValue()-1900,new Integer(strEnd.substring(5,7)).intValue()-1,new Integer(strEnd.substring(8,10)).intValue(),0,0,0,0);

					   nDays = (int)java.lang.Math.ceil((tsEnd.getTime() - rq.getTime()) / 86400000) + info.nAddDays;
					   if (nDays >= 0)
					   {
							if (info.nIsBeijing == 0) nDays = nDays + 3;
							dAccrual = info.dAmount * (llv / Common.getDiscountDays(-1)) * 0.01 * nDays;
							dAccrual = DataFormat.formatDouble(dAccrual,2);
					        dRealAmount = info.dAmount - dAccrual;
					   } 
					   else
					   {
							nDays = 0;
							dAccrual = 0;
							dRealAmount = 0;
					   }
*/
						lCount = info.getCount();
						dTotalAccrual = info.getTotalAccrual();
						dTotalRealAmount = info.getTotalRealAmount();
						dTotalAmount = info.getTotalAmount();

						tmpInt++;
	%>
        <TR align="center"  borderColor=#999999>		  
		  <TD class=ItemBody>
		  <%=DataFormat.formatInt(tmpInt,3,true)%>
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
		  <%=info.getAddDays()%> ��
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=info.getDays()%> ��
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=DataFormat.formatRate(info.getDiscountRate())%> %
		  </TD>
		  <TD align="center" class=ItemBody>��
		  <%if (info.getAccrual()>0) {out.println(DataFormat.formatDisabledAmount(info.getAccrual()));} else {out.println("0.00");}%>
		  </TD>
		  <TD align="center" class=ItemBody>��
		  <%if (info.getRealAmount()>0) {out.println(DataFormat.formatDisabledAmount(info.getRealAmount()));} else {out.println("0.00");}%>	  
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
          <TD class=ItemBody>&nbsp;</TD></TR>			   
			   <%			   
			   }
        %>
				  
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=10 height=25>
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
			<DIV align=right>
				<INPUT class=button name=Submit423 onclick="printPage();" type="button" value=" �� ӡ ">
				<%if ((backurl != null) && (backurl.length() > 0)) {%>
				<INPUT class=button name="backstep" onClick="MM_goToURL('self','<%=backurl%>.jsp?lDiscountID=<%=lDiscountID%>&lCredenceID=<%=lCredenceID%>&lContractID=<%=lContractID%>&backpage=<%=backpage%>&control=view');" type="button" value=" �� �� ">
				<%} else {%>
				<INPUT class=button name="close" onClick="window.close();" type="button" value=" �� �� ">
				<%}%>
            </DIV></TD></TR></TBODY></TABLE></TD></TR>

</TBODY></TABLE></TD></TR></TBODY></TABLE>

<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
<input type="hidden" name="lPageNo" value="<%=lPageNo%>">
<input type="hidden" name="lOrderParam" value="<%=lOrderParam%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="hidden" name="control" value="view">
<input type="hidden" name="backurl" value="<%=backurl%>">
<input type="hidden" name="backpage" value="<%=backpage%>">
<input type="hidden" name="lCredenceID" value="<%=lCredenceID%>">
<input type="hidden" name="lContractID" value="<%=lContractID%>">
<input type="hidden" name="lDiscountID" value="<%=lDiscountID%>">

<P><BR></P>
</form>

<script language="javascript">

function printPage()
{	
	window.open("q029-p.jsp?lDiscountID=<%=lDiscountID%>&lContractID=<%=lContractID%>&lCredenceID=<%=lCredenceID%>&control=view&isSM=<%=Constant.YesOrNo.NO%>","popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");	
}
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
	frm.action = "q027-v.jsp";
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

function selectAll(frm)
{
	var i;
	//ֻѡ����һ��checkBox,��checkBox����Ϊ����,checkBox[i]������
	if( isNaN(frm.checkbox) == true )
	{
		return false;
	}
	else if( isNaN(frm.checkbox.length) == true )	
	{		
		frm.checkbox.checked = true; 
	}
	else
	{
		for(i = 0; i < <%=lCount%>; i++)
		{
			if (frm.checkbox[i] != null)
			{
				frm.checkbox[i].checked = true;
			}
		}
	}
}

function disabledAll(frm)
{
	var i;
	//ֻѡ����һ��checkBox,��checkBox����Ϊ����,checkBox[i]������
	if( isNaN(frm.checkbox.length) == true )	
	{		
		frm.checkbox.disabled = true;
	}
	else
	{
		for(i = 0; i < <%=lCount%>; i++)
		{
			if (frm.checkbox[i] != null)
			{
				frm.checkbox[i].disabled = true;
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
	//ֻѡ����һ��checkBox,��checkBox����Ϊ����,checkBox[i]������
	if( isNaN(frm.checkbox.length) == true )
	{
		if( frm.checkbox.checked == true )
		{
			bSelect = true;
		}			
	}
	else
	{
		var i;
		for(i=0; i<frm.checkbox.length; i++ )
		{
			if( frm.checkbox[i].checked == true )
			{
				bSelect = true;
				break;
			}
		}
	}
	if(!bSelect)
	{
		alert("������ѡ��һ������Ʊ�ݣ�");
		return false;
	}

	if(confirm("�Ƿ��ύ��"))
	{
		frm.control.value="save";
		frm.action="S127.jsp";
		showSending();
		frm.submit();
		return true;
	}
}

<% if( (strBillID == null) || (strBillID.length() == 0) ) { %>
	//disabledAll(frm);
<% } %>
//firstFocus(frm.txtContractCtrl);
////setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 

</script>
<%
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"��������", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>