<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contractcontent.dao.ContractContentDao"
%>

<%

	//log4j //log4j = new //log4j(Constant.ModuleType.LOAN);

	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	String strTableTitle = "�ֽ���������ѯ";
	try
	{
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
//�������
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lUserID = sessionMng.m_lUserID;
		long lIsSM = Constant.YesOrNo.NO;
		long lIsReadOnly = Constant.YesOrNo.YES;
		long lTypeId= -1;
		String sTemp="";
		/////////////////////////////////////
		boolean isRead=false;
		//int index++=0;
		
		String[] arrContent=null;
		arrContent=new String[200];
		String strContent=(String)request.getAttribute("sContent");
   	   //String[] arrContent=new String[64];
   	   if (strContent!=null&&strContent.length() > 0)
	   {
			int nIndex; //","������λ��
			int nTmp = 0;
			nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
			while (nIndex >= 0)
			{
				arrContent[nTmp] = strContent.substring(0, nIndex);
				strContent = strContent.substring(nIndex + 4);
				nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[nTmp] = strContent;
		}
		

		////////////////////////////////////
		//log4j.info("c211-v----------5-------------------");
		//��ʾ�ļ�ͷ
		 OBHtml.showOBHomeHead(out,sessionMng,"",Constant.YesOrNo.NO);
		int index = 1;
%>


<%
		long lContentID = -1;
		long lClientID = -1;
		String strTemp="";
		String strYear="";
		String strSeason="";
		String strClientName="";

		strTemp = (String)request.getAttribute("strYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//���
			strYear = strTemp.trim();
			
		}
	
		 strTemp = (String)request.getAttribute("strSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//����
			strSeason = strTemp.trim();
			
		}
			strTemp = (String)request.getAttribute("hdnstrYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//���
			strYear = strTemp.trim();
			
		}
	
		 strTemp = (String)request.getAttribute("hdnstrSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//����
			strSeason = strTemp.trim();
			
		}

		
		strTemp = (String)request.getAttribute("lTypeId");
		if (strTemp != null && strTemp.trim().length() > 0){				//�������ͱ��
			lTypeId = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && strTemp.trim().length()>0){				//�������
			lClientID = Long.parseLong(strTemp.trim());
		}

		strContent = (String) request.getAttribute("sContent");
		strTemp = (String) request.getAttribute("lContentID");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lContentID = Long.parseLong(strTemp.trim());
		}
		strTemp = (String) request.getAttribute("lClientID");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lClientID = Long.parseLong(strTemp.trim());
		}
		strTemp = (String) request.getAttribute("hdnClientName");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strClientName = strTemp.trim();
		}

		String strControl = "";
		strTemp = (String) request.getAttribute("hdnControl");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strControl = strTemp.trim();
		}
		if("view".equals(strControl))
		{
			isRead=true;
		}

%>


<SCRIPT language="javascript" src="/webloan/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webloan/js/Check.js"></SCRIPT>

<form name="frm" method="post">
	<!--ҳ����Ʊ���-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	
	<!--�ظ��������-->
	<!--ҳ����Ʊ���-->
	
	<!--����ҵ������-->
	<input type=hidden name="hdnTypeId" value="<%=lTypeId%>">
	<input type=hidden name="hdnlClientID" value="<%=lClientID%>">
	<input type=hidden name="hdnstrYear" value="<%=strYear%>">
	<input type=hidden name="hdnstrSeason" value="<%=strSeason%>">
	<input type=hidden name="hdnClientName" value="<%=strClientName%>">
	<input type="hidden" name="hdnControl" value="<%=strControl%>">
	
	<!--����ҵ������-->
  <TABLE border=0 class=top width="99%"><tr><td>���Ƶ�λ:</td><td><%=strClientName%> </td><td>���:</td><td><%=strYear%> </td>
  </tr></TABLE>



<TABLE border=0 class=top width="99%">

  <tr> 
		<td class="FormTitle" height=2  width="100%"><b>�ֽ�������</b></td>
  </tr>


  <tr>
    <td width="100%">
		<table width="100%" border="0"  class="ItemList" height="102">
      
        <tr>
          <td class=ItemTitle width="60%" height="47" align="center">
            ��Ŀ</td>
          <td class=ItemTitle width="8%" height="47" align="center">
            �д�</td>
          <td class=ItemTitle width="32%" height="47" align="center">
            ���</td>
        </tr>
        <tr>
          <td class=ItemBody  >��һ.&nbsp;��Ӫ��������ֽ�����&nbsp;</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>��
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;������Ʒ,�ṩ�����յ����ֽ�</td>
          <td class=ItemBody  align="center">1</td>
          <td class=ItemBody  align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�յ���˰�ѷ���</td>
          <td class=ItemBody  align="center">3</td>
          <td class=ItemBody  align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�յ��������뾭Ӫ��йص��ֽ�</td>
          <td class=ItemBody   align="center">8</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  align="center">�ֽ�����С��</td>
          <td class=ItemBody   align="center">9</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;������Ʒ,��������֧�����ֽ�</td>
          <td class=ItemBody   align="center">10</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;֧����ְ���Լ�Ϊְ��֧�����ֽ�</td>
          <td class=ItemBody   align="center">12</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;֧���ĸ���˰��</td>
          <td class=ItemBody   align="center">13</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;֧���������뾭Ӫ��йص��ֽ�</td>
          <td class=ItemBody   align="center">18</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">�ֽ�����С��</td>
          <td class=ItemBody align="center">20</td>
          <td class=ItemBody align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;��Ӫ��������ֽ���������</td>
          <td class=ItemBody   align="center">21</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >����.&nbsp;Ͷ�ʻ�������ֽ�����:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>��
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�ջ�Ͷ�����յ����ֽ�</td>
          <td class=ItemBody   align="center">22</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;ȡ��Ͷ���������յ����ֽ�</td>
          <td class=ItemBody   align="center">23</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;���ù̶��ʲ�,�����ʲ������������ʲ����ջص��ֽ𾻶�</td>
          <td class=ItemBody   align="center">25</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�յ���������Ͷ�ʻ�йص��ֽ�</td>
          <td class=ItemBody   align="center">28</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">�ֽ�����С��</td>
          <td class=ItemBody   align="center">29</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�����̶��ʲ�,�����ʲ������������ʲ���֧�����ֽ�</td>
          <td class=ItemBody   align="center">30</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;Ͷ����֧�����ֽ�</td>
          <td class=ItemBody   align="center">31</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;֧����������Ͷ�ʻ�йص��ֽ�</td>
          <td class=ItemBody   align="center">25</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">�ֽ�����С��</td>
          <td class=ItemBody   align="center">36</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;Ͷ�ʻ�������ֽ���������</td>
          <td class=ItemBody   align="center">37</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >����.&nbsp;���ʻ�������ֽ�����:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;����Ͷ�����յ����ֽ�</td>
          <td class=ItemBody   align="center">38</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;������յ����ֽ�</td>
          <td class=ItemBody   align="center">40</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�յ�����������ʻ�йص��ֽ�</td>
          <td class=ItemBody   align="center">43</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">�ֽ�����С��</td>
          <td class=ItemBody   align="center">44</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;����ծ����֧�����ֽ�</td>
          <td class=ItemBody  align="center">45</td>
          <td class=ItemBody  align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�������,����򳥸���Ϣ��֧�����ֽ�</td>
          <td class=ItemBody   align="center">46</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;֧������������ʻ�йص��ֽ�</td>
          <td class=ItemBody  align="center">52</td>
          <td class=ItemBody  align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody align="center">�ֽ�����С��</td>
          <td class=ItemBody  align="center">53</td>
          <td class=ItemBody  align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;���ʻ�������ֽ���������</td>
          <td class=ItemBody   align="center">54</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >����.&nbsp;���ʱ䶯���ֽ��Ӱ��</td>
          <td class=ItemBody   align="center">55</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >����.&nbsp;�ֽ��ֽ�ȼ��ﾻ���Ӷ�</td>
          <td class=ItemBody   align="center">56</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
  <tr>
    <td width="100%">
		<table width="100%" border="0"  class="ItemList" height="102">
      
        <tr>
          <td class=ItemTitle width="60%" height="47" align="center">
            ��������</td>
          <td class=ItemTitle width="8%" height="47" align="center">
            �д�</td>
          <td class=ItemTitle width="32%" height="47" align="center">
            ���</td>
        </tr>
        <tr>
          <td class=ItemBody  >��1.&nbsp;�����������Ϊ��Ӫ��ֽ�����:</td>
          <td class=ItemBody  align="center"></td>
          <td class=ItemBody  align="center" height=20>
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;������</td>
          <td class=ItemBody   align="center">57</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;��:&nbsp;������ʲ���ֵ׼��</td>
          <td class=ItemBody  align="center">58</td>
          <td class=ItemBody  align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�̶��ʲ��۾�</td>
          <td class=ItemBody   align="center">59</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�����ʲ�̯��</td>
          <td class=ItemBody   align="center">60</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ڴ�̯����̯��</td>
          <td class=ItemBody   align="center">61</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��̯���ü���(��:����)</td>
          <td class=ItemBody   align="center">64</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ԥ���������(��:����)</td>
          <td class=ItemBody   align="center">65</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ù̶��ʲ�,�����ʲ������������ʲ�����ʧ(��:����)</td>
          <td class=ItemBody   align="center">66</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�̶��ʲ�������ʧ</td>
          <td class=ItemBody   align="center">67</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������</td>
          <td class=ItemBody   align="center">68</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ͷ����ʧ(��:����)</td>
          <td class=ItemBody   align="center">69</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����˰�����(��:����)</td>
          <td class=ItemBody   align="center">70</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ļ���(��:����)</td>
          <td class=ItemBody   align="center">71</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ӫ��Ӧ����Ŀ�ļ���(��:����)</td>
          <td class=ItemBody   align="center">72</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ӫ��Ӧ����Ŀ������(��:����)</td>
          <td class=ItemBody   align="center">73</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����</td>
          <td class=ItemBody   align="center">74</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;��Ӫ��������ֽ���������</td>
          <td class=ItemBody   align="center">75</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��2.&nbsp;���漰�ֽ���֧��Ͷ�ʺͳ��ʻ:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;ծ��תΪ�ʱ�</td>
          <td class=ItemBody   align="center">76</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;һ���ڵ��ڵĿ�ת����˾ծȯ</td>
          <td class=ItemBody   align="center">77</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�������˹̶��ʲ�</td>
          <td class=ItemBody   align="center">78</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��3.&nbsp;�ֽ��ֽ�ȼ��ﾻ�������:</td>
          <td class=ItemBody   align="center"></td>
          <td class=ItemBody   align="center" height=20>��
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�ֽ����ĩ���</td>
          <td class=ItemBody   align="center">79</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;��:&nbsp;�ֽ���ڳ����</td>
          <td class=ItemBody   align="center">80</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;��:&nbsp;�ֽ�ȼ������ĩ���</td>
          <td class=ItemBody   align="center">81</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;��:&nbsp;�ֽ�ȼ�����ڳ����</td>
          <td class=ItemBody   align="center">82</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= index++ %>]';" >
          </td>
        </tr>
        <tr>
          <td class=ItemBody  >��&nbsp;&nbsp;&nbsp;&nbsp;�ֽ��ֽ�ȼ��ﾻ���Ӷ�</td>
          <td class=ItemBody   align="center">83</td>
          <td class=ItemBody   align="center">��<input class=tar type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[index])%>" maxlength="50"  onFocus="nextfield ='AddSubmit';" >
          </td>
        </tr>
      </table>
    </td>
  </tr>
 
 
		<TR bordercolor="#999999"> 
			<TD align=right>

<% 
	if(!isRead)
	{
%>
				<INPUT onfocus="nextfield=''" class=button name=AddSubmit onclick="FrmSubmit(frm,1)" type=button value=" ���� "> 

<%
	}
%>
				<INPUT onfocus="nextfield='AddSubmit'" class=button name=backSubmit onclick="FrmSubmit(frm,2)" onKeydown="if(event.keyCode==13) FrmSubmit(frm,2);" type=button value=" ���� "> 
			</TD>
		</TR>

	    <input type="hidden" name="control" value="">
		<input type="hidden" name="SUBMIT" >
		<INPUT type="hidden" name="lClientID" value="<%=lClientID%>">
		<input type="hidden" name="lContentID" value="<%=lContentID%>">
		<input type="hidden" name="PageName"  value="c211-v.jsp">
		<input type="hidden" name="PageNo"  value="1">
		<input type="hidden" name="lIsReadOnly"  value="<%=Constant.YesOrNo.NO%>">

</table>



</form>


<script language="JavaScript">
		firstFocus(frm.textfield[0]);
		//setSubmitFunction("FrmSubmit(frm,1)");
		setFormName("frm");
</script>


<script language="JavaScript">

function FrmSubmit(frm,nType)
{
	var bSubmit = false;

	if (nType == "1")
	{
		if (confirm("�Ƿ񱣴棿"))
		{
			frm.action = "../control/c002.jsp";
			frm.strSuccessPageURL.value = "../view/v004.jsp";
			frm.strFailPageURL.value = "../view/v004.jsp";		
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "save";
			
			frm.submit();
			return true;
		}
		else
		{
			return false;
		}
	}
	else if (nType == "2")
	{
		if (confirm("���������ı���Ϣ��"))
		{

			frm.action = "../control/c002.jsp";
			frm.strSuccessPageURL.value = "../view/v004.jsp";
			frm.strFailPageURL.value = "../view/v004.jsp";	
			frm.PageName.value = "c211-v.jsp";
			frm.SUBMIT.value = "";
			
			frm.submit();
			return true;
		}
		else
		{
			return false;
		}
	}

}
</script>








<%
		OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,strTableTitle, Constant.RecordStatus.VALID);
		out.flush();
		return;
	}
%>
