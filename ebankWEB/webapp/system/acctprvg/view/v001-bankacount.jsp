<%--
 ҳ������ ��v001-gd.jsp
 ҳ�湦�� : �����˻���Ȩ��ʾҳ��
 ��    �� ��zcwang
 ��    �� ��2008-4-29
 ����˵�� ��
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.AcctPrvgByClientInfo" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<head>
</head>
<%
try
{
	String strTitle = "";
	 //�û���¼��� 
	if (sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
		out.flush();
		return;
	}

	// �ж��û��Ƿ���Ȩ�� 
	if (sessionMng.hasRight(request) == false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
		out.flush();
		return;
	}
	/* ��ʾ�ļ�ͷ */
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, Constant.YesOrNo.YES);
 %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<form name="form1" method="post" action="../control/c002-bankacount.jsp">
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td>
	<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
	    <br>
		    <table cellspacing="0" cellpadding="0" class=title_Top1 align="center" width="98%">
				<TR>
			       <td class=title><span class="txt_til2">�����˻���Ȩ</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
           <br/>    
          
              <table  align="center" width="98%" border=0 cellspacing="0" class=normal>
                <tr height="2" class="itemtitle"> 
                  <td width="33%" height="20" nowrap class="MsoNormal" style="border-bottom: 1px solid #ccc;border-right: 1px solid #ccc;" height="2"> 
                    <div align="center">�ϼ���λ</div>
                  </td>
                  <td width="67%"  height="2" class="MsoNormal" style="border-bottom: 1px solid #ccc"> 
                    <div align="center">�˺�</div>
                  </td>
                </tr>
                <%
				 	long clientId=-1;
				 	Collection con=null;
				 	String check="checkbox";
				 	int temp=0;
				 	int temp1=0;
				 	int temp2=0;
				 	String checkBox=check+temp;
				 	if(request.getAttribute("clientInfo")!=null){
				 		con=(Collection)request.getAttribute("clientInfo");
				 		Log.print("�ɹ�����"+con);
				 	}
				 	if(con!=null&&!con.isEmpty()){
				 		Iterator itr=con.iterator();
				 		while(itr.hasNext()){
				 			temp2++;
				 			AcctPrvgByClientInfo info=(AcctPrvgByClientInfo)itr.next();
				 			if(clientId!=info.getClientID()){
				 				if(temp1!=0){
				 					out.println("</td></tr>");
				 				}
 				%>
 				
 				 <tr> 
                  <td valign="top" style="border-bottom: 1px solid #ccc;border-right: 1px solid #ccc;" width="33%"> 
                    <div align="center"> <%=info.getClientName()%></div>
                  </td>
                  <td valign="top" style="border-bottom: 1px solid #ccc" width="67%"> 
                    <p> 
                      <input type="checkbox" name="<%=checkBox%>" value="" onClick="selectAll(form1.<%=checkBox%>)">
                      ȫѡ </p>
                    <p> 
                      <input type="checkbox" name="<%=checkBox%>" value="<%=info.getClientID()+","+info.getAccountID()%>" <%if(info.getHavePrvg()==1){out.print("checked");} %>>
                      <%=info.getAccountNo() %></p> 
 				<% 
			 				temp++;
 							temp1++;
			            	checkBox=check+temp;
			            	clientId=info.getClientID();
 				
				 			}
				 			
				 			else{
				 				%>
				 					<p>
				 					<input type="checkbox" name="<%=check+(temp-1) %>" value="<%=info.getClientID()+","+info.getAccountID()%>" <%if(info.getHavePrvg()==1){out.print("checked");} %>>
				                      <%=info.getAccountNo() %></p>
				 				<% 
				 			}
				 		}
				 		%>
				 			</td></tr>
				 			<tr> 
                  <td colspan='6' height="13"> 
                    <div align=right> 
                      <input class="button1" name="Submit001" type="button" value=" �� �� " onClick="save()">&nbsp;&nbsp;
                    </div>
                  </td>
                </tr>
                <tr>
                <td height="5"></td></tr>
				 			 <table align=center height="43" width="95%">
                 
                
               
              </table>
				 		<%
				 	}
				 	else{
				 		%>
				 		<tr>
				 			<td align="center" colspan="2">�����趨�ϼ���λ�͹����˺�</td>
				 		</tr>
				 		<%
				 	}
				 %>      
				 <br>        
              </table>
        </table>
        <input type="hidden" name="number" value="<%=temp%>"/>
         </table>
        </td>
    </tr>
</table>
</form>
<script type="text/javascript">
function selectAll(checkBoxObj){
	var myCheck=checkBoxObj;
	for(i=0;i<myCheck.length;i++){
		myCheck[i].checked=myCheck[0].checked;
	}
}
function save(){
	if (confirm("�Ƿ񱣴�"))
	{
		showSending();
		form1.submit();
	}	
}
</script>
                
 
 <%	
	/**
	* ��ʵ�ļ�β
	*/
	OBHtml.showOBHomeEnd(out);	
}
//�쳣����
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>

<%@ include file="/common/SignValidate.inc" %>