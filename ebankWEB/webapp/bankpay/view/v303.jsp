<%--
/*
 * �������ƣ�
 * ����˵����ҵ���ѯ��ӡ
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
       	//OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
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
		
		eBankPrint.showPrintReport(out,sessionMng,"A4",2,true);
       
		

        
%>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>




<form name="formcancel">
		
		<table width="920" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td align="center" ><b><font style="font-size:22px">���л��ҵ���굥</font></b></td>
      </tr>
    </table>
      <table width="900" border="0" class="table1">
        <tr class="tableHeader">
          <td width="25"  height="18" rowspan="2" class="td-rightbottom" nowrap>
            <p align="center"></p>
          </td>
          
          <td width="58"  valign="middle" height="18" rowspan="2" class="td-rightbottom" nowrap>
            <p align="center"><font size="2" class="td-rightbottom" nowrap> ָ�����</font></p>
          </td>
          
          <td width="48"  valign="middle" height="18" rowspan="2" class="td-rightbottom" nowrap>
            <p align="center"><font size="2" class="td-rightbottom" nowrap> �����;</font></p>
          </td>
          
          <td  height="48" nowrap valign="middle" rowspan="2" class="td-rightbottom" nowrap width="75">
            <div align="center">�˺�</div>
          </td>
          
          <td  height="28" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="30">��/��</td>
          
          <td  height="28" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="72">
            <div align="center">���</div>
          </td>
          <td  height="38" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="30">
            <div align="center">�Ƿ��ύ</div>
          </td>
          <td  height="38" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="40">
            <div align="center">״̬</div>
          </td>
          <td  height="28" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="40">
            <div align="center">����ָ��״̬</div>
          </td>
          
          <td  height="0" valign="middle" colspan="2" class="td-rightbottom" nowrap>
            <div align="center">�Է�����</div>
          </td>
          
          
          <td  height="18" valign="middle" rowspan="2" class="td-rightbottom" nowrap width="51">
            <div align="center">ִ������</div>
          </td>
        </tr>
       
        <tr class="tableHeader">
          <td  height="18" valign="middle" class="td-rightbottom" nowrap width="132">
            <div align="center">����</div>
          </td>
       
          <td  height="18" valign="middle" class="td-rightbottom" nowrap width="85">
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
        <tr  valign="middle">
          <td width="25" align="left" class="ItemBody" height="20"></td>
          
          <td colspan="20" align="left"  class="ItemBody" height="20">
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
          
          <td colspan="20" align="left"  class="ItemBody" height="20">
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
          <td width="25" valign="top" align="left"  class="ItemBody">
            &nbsp;
            
          </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody" nowrap="nowrap">          
   			
            <div align="center"><a href="../view/v302.jsp?id=<%= info.getId()%>" target="_blank"><%= info.getId()%></a></div>
           
          
		  </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody">
            <div align="center"><%=DataFormat.formatString(info.getSnote()) %></div>
          </td>
          
          <td  width="75" class="ItemBody" valign="top" nowrap><%= NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %></td>
          
          <td  width="30" class="ItemBody" valign="top"><%out.print("��"); %></td>
          
          <td  width="72" class="ItemBody" valign="top">
            <div align="center"><%= sessionMng.m_strCurrencySymbol%><%= DataFormat.formatEAmount(info.getMamount()) %></div>
          </td>
          <td  class="ItemBody" width="30" valign="top">
          	<%if(info.getNiscanaccept()==1) out.print("��");
          	 	else out.print("");
          	 %>
          </td>
          <td  class="ItemBody" width="40" valign="top">
          	<%if(info.getNstatus()==OBConstant.OBBankPayStatus.SAVE) out.print("δ����");
          	 	if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK) out.print("�Ѹ���");
          	 	if(info.getNstatus()==OBConstant.OBBankPayStatus.SIGN) out.print("��ǩ��");
          	 %>
          	 
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
                if (info.getNtranstype() == -1000) {
                    dLoanAmount += info.getMamount(); //���д����
                } else {
                    dDebitAmount += info.getMamount();//���н���
                }
                dTotalAmount += info.getMamount();//�����ڷ��������ĵ��ܽ��׽��
                lTotalCount++;//���б���
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
          <td  class="ItemBody" width="40" valign="top"><%=InstructionStatus.getName(info.getBankPortalStatus())%></td>
          <td  class="ItemBody" width="132" valign="top" nowrap><%=info.getSpayeeacctname()%></td>
          
          <td  class="ItemBody" width="75" valign="top"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
         
          <td  class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"><%= info.getDtexecute().toString().substring(0,10) %></span></div>
          </td>
        </tr>
<%
		 }
		if (iCount == 0)//��ʾû��¼����ʾһ����
		{
%>
		
        <tr>
          <td width="25" height="14" valign="top" align="left"  class="ItemBody">
          </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody">
		  </td>
          
          <td width="48" valign="top" align="left"  class="ItemBody">
          </td>
          
          <td  width="75" class="ItemBody" valign="top"></td>
          
          <td  width="30" class="ItemBody" valign="top">
      </td>
          
          <td  width="72" class="ItemBody" valign="top">
          </td>
          <td  width="40" class="ItemBody" valign="top">
          </td>
           <td  width="30" class="ItemBody" valign="top">
          </td>
          
          <td  width="40" class="ItemBody" valign="top">
          </td>
          
          <td  class="ItemBody" width="132" valign="top"></td>
          
          <td  class="ItemBody" width="75" valign="top"></td>
        
          
          <td  class="ItemBody" width="51" valign="top">
            <div align="center"><span class="ItemBody"></span></div>
          </td>
        </tr>

<%	
		}
%>
       
      </table>

      <br>
	
    <br>
    <table width="810" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">��ѯʱ�䣺<%=DataFormat.getDateString().substring(0,10)%></span></div>
        </td>
      </tr>
    </table>
   
</form>



<%
   }
   catch(IException ie)
   {
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
   
%>
-------------------------------------------------------------------------------------%>