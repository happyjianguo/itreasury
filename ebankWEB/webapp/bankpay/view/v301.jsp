<%--
/*
 * �������ƣ�
 * ����˵����ҵ��ǩ�ϲ�ѯ��ʾҳ��
 * �������ߣ�baihuili
 * ���ڣ�2006��09��15��
 */
--%>
<%-- ע�ʹ�ҳ����:2008/03/12  ע���ˣ�leiyang3 --%>

<%-------------------------------------------------------------------------------------
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*,
                 com.iss.itreasury.budget.util.BUDGETNameRef,
                 com.iss.itreasury.bankportal.integration.constant.InstructionStatus"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->


<%
	//�������
	String strTitle = "[���л��]";
	String strdisable = "";
%>

<%	  
	/* �û���¼�����Ȩ��У�� */
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
           
        /* ��ʾ�ļ�ͷ */
       	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
   		Collection coll = null;
		if(request.getAttribute("rcoll")!=null)
		{
			coll = (Collection)request.getAttribute("rcoll");
		}
		Iterator rs = null;
	 	if(coll != null)
		{
			rs = coll.iterator();
		}
		//Iterator rs = (Iterator)request.getAttribute("return");
		
		//��ѯ��Ϣ����
		QueryCapForm rsForm = new QueryCapForm();
		if(request.getAttribute("info") != null)
		{
       		rsForm = (QueryCapForm)request.getAttribute("info");
       		
       		
        }
       
		

        
%>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>



<form name="form1" method="get">

	<table width="810" border="0" cellspacing="0" cellpadding="0"  class = top>
        <tr > 
          <td colspan="4" height="1" class=FormTitle >���л��--ҵ���ѯ</td>
        </tr>
      </table>
      <table width="810" border="0" cellspacing="0" cellpadding="0" class = top>
        
      
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;��</span></p>
          </td>
          <td width="60" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">��Сֵ<%= sessionMng.m_strCurrencySymbol%></div>
          </td>
          <td width="440" height="25" class="MsoNormal">
            
	        <script  language="JavaScript">
				createAmountCtrl('form1','txtMinAmount','<%=rsForm.getMinAmount()%>','txtMaxAmount','',1);
				form1.txtMinAmount.focus();
				//alert(form1.txtMinAmount.value);
				if(form1.txtMinAmount.value==0.00)
				{
					form1.txtMinAmount.value="";
				}
			</script>
        <span class="MsoNormal"> ���ֵ<%= sessionMng.m_strCurrencySymbol%></span>
        <script  language="JavaScript">
				createAmountCtrl('form1','txtMaxAmount','<%=rsForm.getMaxAmount()%>','txtExecuteA','',1);
				if(form1.txtMaxAmount.value==0.00)
				{
					form1.txtMaxAmount.value="";
				}
			</script>
          </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
     
        <tr class="MsoNormal">
          <td colspan="5" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;ִ�����ڣ�</span></p>
          </td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">��</div>
          </td>
          <td width="430" height="25" class="MsoNormal">
            <input type="text" name="txtExecuteA" size="12" value="<%= rsForm .getStartExe() %>" onfocus="nextfield ='txtExecuteB';">
            <a href="javascript:show_calendar('form1.txtExecuteA');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
			<img src="/webob/graphics/calendar.gif"  width="15" height="18" border=0></a>
             <span class="graytext">��
            </span>
            <input type="text" name="txtExecuteB" size="12" value="<%= rsForm .getEndExe() %>" onfocus="nextfield ='';" >
            <a href="javascript:show_calendar('form1.txtExecuteB');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
			<img src="/webob/graphics/calendar.gif"  width="15" height="18" border=0></a>
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;״̬��</span></p>
          </td>
          <td colspan="2" align="left">
          	<select name="lStatus">
          		<option value="-1" selected>ȫ��          		
          		<option value="<%=OBConstant.OBBankPayStatus.SAVE%>" >δ����</option>
          		<option value="<%=OBConstant.OBBankPayStatus.CHECK%>">�Ѹ���</option>
          		<option value="<%=OBConstant.OBBankPayStatus.AUDITING%>">�����</option>
          		<option value="<%=OBConstant.OBBankPayStatus.SIGN%>">��ǩ��</option>
          	</select>
          	<script language="javascript">
             	form1.lStatus.value="<%=rsForm.getStatus()%>";
            </script>
          </td>
          <td width="3" height="25"  class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      <table width="810" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="376">
            <div align="right"></div>
          </td>
          <td width="134">
            <div align="right"></div>
          </td>
          <td width="60">
            <div align="right">
            <input type="hidden" name="doact" >
			<!--img name="Query" src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doQuery();"-->
			<input type="button" name="Submitv00204" value=" ��  �� " class="button" onClick="javascript:doQuery();">
			</div>
          </td>
        </tr>
      </table>
	  <br>
</form>
<form name="formcancel">
      <table width="810" border="0" class="ItemList">
        <tr class="tableHeader">
          <td width="25" bgcolor="#456795" height="18" rowspan="2" class="ItemTitle">
            <p align="center"></p>
          </td>
          
          <td width="48" bgcolor="#456795" valign="middle" height="18" rowspan="2" class="ItemTitle">
            <p align="center"><font size="2" class="ItemTitle"> ָ�����</font></p>
          </td>
          
          <td width="48" bgcolor="#456795" valign="middle" height="18" rowspan="2" class="ItemTitle">
            <p align="center"><font size="2" class="ItemTitle"> �����;</font></p>
          </td>
          
          <td bgcolor="#456795" height="28" nowrap valign="middle" rowspan="2" class="ItemTitle" width="75">
            <div align="center">�˺�</div>
          </td>
          
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="30">��/��</td>
          
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="72">
            <div align="center">���</div>
          </td>
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="30">
            <div align="center">�Ƿ��ύ</div>
          </td>
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="40">
            <div align="center">״̬</div>
          </td>
          <td bgcolor="#456795" height="28" valign="middle" rowspan="2" class="ItemTitle" width="40">
            <div align="center">����ָ��״̬</div>
          </td>
          
          <td bgcolor="#456795" height="0" valign="middle" colspan="2" class="ItemTitle">
            <div align="center">�Է�����</div>
          </td>
          
          <td bgcolor="#456795" height="18" valign="middle" rowspan="2" class="ItemTitle" width="51">
            <div align="center">Ԥ����ϵ</div>
          </td>
          <td bgcolor="#456795" height="18" valign="middle" rowspan="2" class="ItemTitle" width="51">
            <div align="center">Ԥ������</div>
          </td>
          <td bgcolor="#456795" height="18" valign="middle" rowspan="2" class="ItemTitle" width="51">
            <div align="center">ִ������</div>
          </td>
        </tr>
       
        <tr class="tableHeader">
          <td bgcolor="#456795" height="18" valign="middle" class="ItemTitle" width="132">
            <div align="center">����</div>
          </td>
       
          <td bgcolor="#456795" height="18" valign="middle" class="ItemTitle" width="75">
            <div align="center">�˺�</div>
          </td>
        </tr>

<%
      	int iCount = 0;//������
      	String strDataLast = "";//ǰһ��ָ��
      	String strData = "";//��ǰָ��
      	
      	Vector vctCapSummarize = new Vector(); //���OBCapSummarizeInfo����ļ���
   	    OBCapSummarizeInfo obCSI=null; //��Ž����ܽ���Ϣ
   	    Timestamp tsConfirmDate = null; //ȷ��ʱ��
	    long lTotalCount = 0;   //���б���
	    long lDeleteCount = 0;  //��ɾ������
	    long lUnCheckCount = 0; //δ���˱���
	    long lCheckCount = 0;   //�Ѹ��˱���
	    long lSignCount = 0;    //��ǩ�ϱ���
	    double dTotalAmount = 0;//�ܽ��׽��
	    double dLoanAmount = 0; //���д����
	    double dDebitAmount = 0;//���н���
    
	  	//ѭ����������ʾ����
      	while ((rs != null) && rs.hasNext())
      	{
            OBBankPayInfo info=(OBBankPayInfo)rs.next();//�ʽ������Ϣ����
            iCount++;
            strData = info.getDtconfirm().toString();
  		  	//long billstatusid = info.getNDepositBillStatusId();
		  	//System.out.println("billstatusid��ֵ�ǣ�"+billstatusid);
            if (iCount == 1 && strData!=null)
            {
                  strDataLast = strData;
%>
        <tr bgcolor="#FDF5DF" valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="22" align="left" bgcolor="#FDF5DF" class="ItemBody" height="20">
            <p>�ύ����<span class="graytext">��<%= strData==null?"":strData.toString().substring(0,10) %></span></p>
          </td>
        </tr>
<%
            	
            	
            	
            }
            if ( strDataLast!=null && !strDataLast.equalsIgnoreCase(strData) )
            {
%>
        <tr  valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="22" align="left" bgcolor="#FDF5DF" class="ItemBody" height="20">
            <p>�ύ����<span class="graytext">��<%= strData.toString().substring(0,10) %></span></p>
          </td>
        </tr>
<%
						//��¼��һ��ʱ��ε���Ϣ
                 
        				obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //ȷ��ʱ��
                        obCSI.setTotalCount(lTotalCount);       //���б���
                        //obCSI.setDeleteCount(lDeleteCount);   //��ɾ������
                        obCSI.setUnCheckCount(lUnCheckCount);   //δ���˱���
                        obCSI.setCheckCount(lCheckCount);       //�Ѹ��˱���
                        obCSI.setSignCount(lSignCount);         //��ǩ�ϱ���
                        obCSI.setTotalAmount(dTotalAmount);     //�ܽ��׽��
                        obCSI.setLoanAmount(dLoanAmount);       //���д����
                        obCSI.setDebitAmount(dDebitAmount);     //���н���
                        lTotalCount = 0;    //���б���
                        lDeleteCount = 0;   //��ɾ������
                        lUnCheckCount = 0;  //δ���˱���
                        lCheckCount = 0;    //�Ѹ��˱���
                        lSignCount = 0;     //��ǩ�ϱ���
                        dTotalAmount = 0;   //�ܽ��׽��
                        dLoanAmount = 0;    //���д����
                        dDebitAmount = 0;   //���н���
                        vctCapSummarize.addElement(obCSI);
                 
                 
                  strDataLast = strData;
            }
        
                  	long pStatus=info.getBankPortalStatus();
                  	if(pStatus!=-1 && pStatus!=0 && pStatus!=10)
                  	{
                  		strdisable="disabled";
                  	}
                  	else
                  	{
                  		strdisable="";
                  	} 
                  	
%>
		
        <tr>
          <td width="25" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
            &nbsp;
            
          </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody" nowrap="nowrap">          
   			
            <div align="center"><a href="../view/v302.jsp?id=<%= info.getId()%>" target="_blank"><%= info.getId()%></a></div>
           
          
		  </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
            <div align="center"><%=DataFormat.formatString(info.getSnote()) %></div>
          </td>
          
          <td bgcolor="#C8D7EC" width="75" class="ItemBody" valign="top" nowrap><%= NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %></td>
          
          <td bgcolor="#C8D7EC" width="30" class="ItemBody" valign="top"><%out.print("��"); %></td>
          
          <td bgcolor="#C8D7EC" width="72" class="ItemBody" valign="top">
            <div align="center"><%= sessionMng.m_strCurrencySymbol%><%= DataFormat.formatEAmount(info.getMamount()) %></div>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="30" valign="top">
          	<%if(info.getNiscanaccept()==1) out.print("��");
          	 	else out.print("");
          	 %>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="40" valign="top">
          	<%=OBConstant.OBBankPayStatus.getName(info.getNstatus())%>
          	
          	 
          	 <%
          	 	//����ÿ����¼��״̬��������
          	 	switch ((int) info.getNstatus()) {
                    case (int) OBConstant.OBBankPayStatus.DELETE:
                        lDeleteCount++;//��ɾ��
                    break;
                    case (int) OBConstant.OBBankPayStatus.SAVE:
                        lUnCheckCount++;//δ���˱���
                    break;
                    case (int) OBConstant.OBBankPayStatus.CHECK:
                        lCheckCount++;//�Ѹ��˱���
                    break;
                    case (int) OBConstant.OBBankPayStatus.SIGN:
                        lSignCount++;//��ǩ�ϱ���
                    break;
                    default :
                    break;
                }
                if(info.getNstatus() != OBConstant.OBBankPayStatus.DELETE){
	                if (info.getNtranstype() == -1000) {
	                    dLoanAmount += info.getMamount(); //���д����
	                } else {
	                    dDebitAmount += info.getMamount();//���н���
	                }
	                dTotalAmount += info.getMamount();//�����ڷ��������ĵ��ܽ��׽��
	                lTotalCount++;//���б���
                }
                tsConfirmDate = info.getDtconfirm();
                
                if (rs != null && !rs.hasNext()) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //ȷ��ʱ��
                        obCSI.setTotalCount(lTotalCount);       //���б���
                        //obCSI.setDeleteCount(lDeleteCount);     //��ɾ������
                        obCSI.setUnCheckCount(lUnCheckCount);   //δ���˱���
                        obCSI.setCheckCount(lCheckCount);       //�Ѹ��˱���
                        obCSI.setSignCount(lSignCount);         //��ǩ�ϱ���
                        obCSI.setTotalAmount(dTotalAmount);     //�ܽ��׽��
                        obCSI.setLoanAmount(dLoanAmount);       //���д����
                        obCSI.setDebitAmount(dDebitAmount);     //���н���
                        vctCapSummarize.addElement(obCSI);
                }
            
          	 %>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="40" valign="top"><%=InstructionStatus.getName(info.getBankPortalStatus())%></td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="132" valign="top" nowrap><%=info.getSpayeeacctname()==null?"":info.getSpayeeacctname()%></td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="75" valign="top"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= BUDGETNameRef.getSystemNoByID(info.getBudgetSystemID()) %></span></div>
          </td>	
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= BUDGETNameRef.getItemNameByID(info.getBudgetItemID()) %></span></div>
          </td>	
         		
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= info.getDtexecute().toString().substring(0,10) %></span></div>
          </td>
        </tr>
<%
		 }
		if (iCount == 0)//��ʾû��¼����ʾһ����
		{
%>
		
        <tr>
          <td width="25" height="14" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
          </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
		  </td>
          
          <td width="48" valign="top" align="left" bgcolor="#C8D7EC" class="ItemBody">
          </td>
          
          <td bgcolor="#C8D7EC" width="75" class="ItemBody" valign="top"></td>
          
          <td bgcolor="#C8D7EC" width="30" class="ItemBody" valign="top">
      </td>
          
          <td bgcolor="#C8D7EC" width="72" class="ItemBody" valign="top">
          </td>
          <td bgcolor="#C8D7EC" width="40" class="ItemBody" valign="top">
          </td>
           <td bgcolor="#C8D7EC" width="30" class="ItemBody" valign="top">
          </td>
          
          <td bgcolor="#C8D7EC" width="40" class="ItemBody" valign="top">
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="132" valign="top"></td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="75" valign="top"></td>
        
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"></span></div>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"></span></div>
          </td>
          <td bgcolor="#C8D7EC" class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"></span></div>
          </td>
        </tr>

<%	
		}
%>
       
      </table>

      <br>
	<%  if (rs != null)
	    {
	    
	     session.setAttribute("vctCap", vctCapSummarize);
		 System.out.println("******************************"+vctCapSummarize.size()+"---------------");
		
		%>
    <br>
    <table width="810" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">��ѯʱ�䣺<%=DataFormat.getDateString().substring(0,10)%></span></div>
        </td>
      </tr>
    </table>
    <table width="810" border="0" cellspacing="0" cellpadding="0">
      <tr>
        
      <td width="750"> 
        <div align="right">
		<!--img src="\webob\graphics\button_jiaoyizongjie.gif" width="83" height="18" border="0" onclick="javascript:summarize();"-->
		<input type="Button" class="button" value="�����ܽ�" width="46" height="18"   onclick="javascript:summarize()">
		</div>
        </td>
        
        
      <td width="58"> 
        <div align="right">
		<!--img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="javascript:printMe();"-->
		<input type="Button" class="button" value="��  ӡ" width="46" height="18"   onclick="javascript:printMe()">
		
		</div>
        </td>
      </tr>
    </table>
	  <%}%>
</form>

 <script language="javascript">
 function doCheckForm()
 {
       var fTop,fLov;

	   //add by sun start 2003-02-19
		
		/* ִ������У�� */
		var startExe = form1.txtExecuteA.value;
		var endExe = form1.txtExecuteB.value;
		if (startExe != "")
		{
			if(chkdate(startExe) == 0)
			{
				alert("��������ȷ��ִ�п�ʼ����");
				form1.txtExecuteA.focus();
				return false;
			}
		}
		if (endExe != "")
		{
			if(chkdate(endExe) == 0)
			{
				alert("��������ȷ��ִ�н�������");
				form1.txtExecuteB.focus();
				return false;
			}
		}
		if ((startExe != "") && (endExe != ""))
		{	if (!CompareDate(form1.txtExecuteA, form1.txtExecuteB, "ִ�����ڣ���ʼ���ڲ��ܴ��ڽ�������"))
			{
				return false;
			}
		}
		//add by sun end 2003-02-19

       /*У����*/
       if (!checkAmount(form1.txtMinAmount,0,"��� ��Сֵ"))
             return false;
       if (!checkAmount(form1.txtMaxAmount,0,"��� ���ֵ"))
             return false;

       fLov =  parseFloat(reverseFormatAmount1(form1.txtMinAmount.value));
       fTop = parseFloat(reverseFormatAmount1(form1.txtMaxAmount.value));
       if (fLov > fTop)
       {
             alert("��� ��Сֵ���ܴ������ֵ");
             return false;
       }
       return true;
 }
 
 function doQuery()
 {
       if (doCheckForm())
       {
             form1.doact.value="";
             form1.target = "";
             form1.action="../control/c301.jsp";
             showSending(); form1.submit();
       }
 }
  /* �����ܽᴦ���� */
    function  summarize() {
   	    form1.doact.value="";
        form1.target = "";
        form1.action = "../view/v305.jsp";
        showSending();
        form1.submit();
        
    }
    //��ӡ������
    function printMe() {
        form1.action = "../control/c301.jsp";
        form1.doact.value="allprint";
        form1.target = "NewWindow_S";
        form1.submit();
    } 
 firstFocus(form1.txtMinAmount);
 setFormName("form1");
 </script>


<%
   }
   catch(IException ie)
   {
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
    OBHtml.showOBHomeEnd(out);
%>
-------------------------------------------------------------------------------------%>