<%@ page contentType="text/html; charset=GBK" %>
<%String strContext = request.getContextPath();%>

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="150" align="left" valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="navbar">
        
        <tr>
          <td valign="middle" ><a href="<%=strContext%>/currentStep.do?operate=selectCurrentStepList" >��������</a></td>
          
        </tr>
        <tr>
          <td valign="middle" ><a href="<%=strContext%>/historyStep.do?operate=selectHistoryStepList">�Ѱ�����</a></td>
        </tr>
        <tr>
          <td valign="middle" ><a href="<%=strContext%>/TranOverCtrl.do?operate=showStepList">����ѯ</a></td>
        </tr>
        <tr>
          <td valign="middle" ><a href="<%=strContext%>/transRight.do?operate=selectTransRightList">Ȩ��ת��</a></td>
        </tr>        
        <tr>
          <td valign="middle" ><a href="<%=strContext%>/wfDefine.do?operate=selectWfDefineList" >����ά��</a></td>
        </tr>
        <tr>
          <td valign="middle"><a href="<%=strContext%>/WFCtrl.do?operate=showDefineList">���̿���</a></td>
        </tr>
        <tr>
          <td valign="middle"><a href="<%=strContext%>/FormCtrl.do?operate=showFormsList">������ע��</a></td>
        </tr>
	<!-- 
        <tr>
          <td valign="middle"><table width="100%" border="0" cellpadding="0" cellspacing="0"  class="navbar_1">
              <tr>
                <td height="35" class="navbar_pad folder" onMouseUp="with(findObj('td1').style){display=display=='none'?'':'none';this.style.backgroundImage=display!='none'?'url(<%=strContext%>/workflow/images/minusTop.gif)':'url(<%=strContext%>/workflow/images/plusTop.gif)'}">���̸���</td>
              </tr>
              <tr>
                <td id="td1" style="display:none;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="iefile" onClick="main.location.href='005.htm'"><a href="#" style="border:none;">�б����</a></td>
                  </tr>
                  <tr>
                    <td class="iefile1" onClick="main.location.href='005.htm'"><a href="#" style="border:none;">ͼ�����</a></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
        </tr>
   -->     
        <tr>
          <td valign="middle"><a href="<%=strContext%>/workflow/jsp/test/test.jsp">ҵ�����</a></td>
        </tr>
      </table></td>
  </tr>
</table></td><td width="5" bgcolor="#ADC3EF"></td>
    <td valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>