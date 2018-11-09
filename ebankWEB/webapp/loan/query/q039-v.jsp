<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@page import="java.util.*,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,			
com.iss.itreasury.ebank.obdataentity.*,	
com.iss.itreasury.ebank.obquery.bizlogic.*,
com.iss.itreasury.ebank.obquery.dataentity.*,
com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo,
com.iss.itreasury.system.approval.bizlogic.ApprovalBiz,
com.iss.itreasury.loan.contract.dao.*,
com.iss.itreasury.loan.contract.dataentity.*,
com.iss.itreasury.loan.contractcontent.dataentity.*
" 
%>
<%
		long lModuleID = Constant.ModuleType.LOAN; //ģ������
		long lLoanTypeID = -1; //��������
		long lActionID = Constant.ApprovalAction.LOAN_APPLY; //�������
				
		long lLoanID = -1;
		long lLoanType = -1;
		
		//ȡ��������
		String strTemp = "";
		strTemp = request.getParameter("loanID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lLoanID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lLoanID = -1;
			}
		}
		
		//ȡ��������
		strTemp = request.getParameter("loanType");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lLoanType = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lLoanType = -1;
			}
		}
		
		ApprovalBiz appbiz = new ApprovalBiz();
		ContractDao cDao = new ContractDao();
		
		//ȡ����˵Ĵ�������
		lLoanTypeID = cDao.getApprovalLoanType(lLoanType); 

		//ȡ���������
		Collection cApproval = appbiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lLoanID,-1);
				
		//��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out,sessionMng,"��ʷ������",Constant.YesOrNo.NO);
%>
<div align="right"> 
        <table width="99%" border="1" bordercolor="#999999" cellpadding="0" cellspacing="0">
          <tr> 
            <td> 
              <table width="100%" border="0">
                <tr> 
                  <td colspan="3" height="28"><u>����˾�������</u></td>
                  <td height="28">&nbsp;</td>
                  <td height="28">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="20%">��ʷ������:</td>
                  <td colspan="4"> <br>
                    <table width="550" border="0" align="left" height="50%" class="ItemList">
                            <tr class="tableHeader"> 
                              <td class="ItemTitle" width="12%" height="20"> <div align="center">���к�</div></td>
                              <td class="ItemTitle" width="21%" height="20"> <div align="center">�������</div></td>
                              <td class="ItemTitle" width="21%" height="20"> <div align="center">�����</div></td>
                              <td class="ItemTitle" width="20%" height="20"> <div align="center">��˾���</div></td>
                              <td class="ItemTitle" width="26%" height="20"> <div align="center">���ں�ʱ��</div></td>
                            </tr>
<%
							
								if (cApproval != null)
								{
									Iterator it =cApproval.iterator();
									ApprovalTracingInfo ATInfo = new ApprovalTracingInfo();
									
									if ( it != null )
									{
										for (;it.hasNext();)
										{
											ATInfo = (ApprovalTracingInfo)it.next();
%>
                            <tr> 
                              <td class="ItemBody" width="12%"   height="20" align="center"><%=ATInfo.getSerialID()%></td>
                              <td class="ItemBody" width="21%"  align="center"><%=ATInfo.getOpinion()==null?"":ATInfo.getOpinion()%></td>
                              <td class="ItemBody" width="21%"  align="center"><%=ATInfo.getUserName()%></td>
                              <td class="ItemBody" width="20%"  align="center"><%=Constant.ApprovalDecision.getName(ATInfo.getResultID())%></td>
                              <td class="ItemBody" width="26%"  align="center"><%=DataFormat.getDateString(ATInfo.getApprovalDate())%></td>
                            </tr>
<%
									}
								}
							}
							else
							{
%>
							<tr> 
                              <td class="ItemBody" width="12%"  height="20"  align="center"></td>
                              <td class="ItemBody" width="21%"  align="center"></td>
                              <td class="ItemBody" width="21%"  align="center"></td>
                              <td class="ItemBody" width="20%"  align="center"></td>
                              <td class="ItemBody" width="26%"  align="center"></td>
                            </tr>
<%}%>
                          </table>
                  </td>
                </tr>
             </table>
            </td>
          </tr>
        </table>
        <p>
		<br>
          <input type="button" name="return"  class="button" onClick="window.close();" value=" �� �� ">
        </p>
      </div>