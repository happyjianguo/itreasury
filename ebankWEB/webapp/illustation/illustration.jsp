<%--
 ҳ������ ��illustation.jsp
 ҳ�湦�� : �������տ������Ϣ��ϸ˵�� 
 ��    �� : niweinan
 ��    �� ��2011-4-02
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
String strTitle = "�������տ������Ϣ��ϸ˵��";


try{
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	//��¼���
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	//���Ȩ��
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
%>



	<tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title nowrap><span class="txt_til2"><%=strTitle%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		</td>
	</tr>
	<tr>
		<td height="20">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="normal" style="word-break:break-all">
				<thead>
			        <tr>
			        	<td  height="18" align="center" rowspan="2" width="20%">��������</td>
						<td  height="18" align="center" rowspan="2" width="50%">�տ�ʻ���������</td>
						<td  height="18" align="center" rowspan="2" width="30%">��ע</td>
						
			        </tr>
			          
			    </thead>
				<tbody>
					<tr>
			        	<td height="25" align="center" width="20%">�й�����</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-70���ַ����������д35������</td>
						<td height="25" align="center" width="30%"></td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">�й�ũҵ����</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-70���ַ����������д34������</td>
						<td height="25" align="center" width="30%"></td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">�й���������</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-60���ַ����������д30������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">�й���������</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-60���ַ����������д30������</td>
						<td height="25" align="center" width="30%">�������к�������ܱ���40���ַ����ϼ�20������</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">�й���ͨ����</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-60���ַ����������д30������</td>
						<td height="25" align="center" width="30%"></td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">�ַ�����</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-62���ַ����������д31������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">��������</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-52���ַ����������д26������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">��������</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-124���ַ����������д62������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">��������</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-60���ַ����������д30������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">���ڷ�չ����</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-120���ַ����������д60������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">�������</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-62���ַ����������д31������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
					<tr>
			        	<td height="25" align="center" width="20%">��������</td>
						<td height="25" align="center" width="50%">����Ϊ�գ�����Ϊ1-60���ַ����������д30������</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
	
			    </tbody>
			</table>
		</td>
	</tr>


	<tr>
		<td height="10">&nbsp;</td>
	</tr>



<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}

%>
