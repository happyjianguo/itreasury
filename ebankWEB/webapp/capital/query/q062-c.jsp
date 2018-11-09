<%--
/*
 * �������ƣ�q002-c.jsp
 * ����˵�������������ѯ����ҳ��
 * �������ߣ�kewen hu
 * ������ڣ�2004-02-11
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    String strTitle = "�۽��������ѯ��";
    try {
        long lTransType = -1;       // �������н�������
        long lContractID = -1;      // ��ͬID
        String strContractNo = "";  // ��ͬ��
		long lChildClientID = -1;//������λ
		String sChildClientNo = "";//������λ
        long lDepositID = -1;       // ����ID
        String strDepositNo = "";   //���ݺ�
        String strStartSubmit = ""; // �ύ����-��
        String strEndSubmit = "";   // �ύ����-��
        long lStatus = -1;          // ����ָ��״̬
        double dMinAmount = 0.00;   // ���׽��-��Сֵ
        double dMaxAmount = 0.00;   // ���׽��-���ֵ
        String strStartExe = "";    // ִ������-��
        String strEndExe = "";      // ִ������-��
        String sNextSuccessPage = "";

        String sTemp = null;    //��ʱ��
        sTemp = (String)request.getParameter("sNextSuccessPage");
        if(sTemp != null && sTemp.trim().length() > 0) {
            sNextSuccessPage = sTemp;
            Log.print("sNextSuccessPage=" + sNextSuccessPage);
        }
        sTemp = (String) request.getParameter("lTransType");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lTransType = Long.parseLong(sTemp); // �������н�������
            if (lTransType == 0) {
                lTransType = -1;
            }
            Log.print("��������=" + lTransType);
        }
        sTemp = (String) request.getParameter("lStatus");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lStatus = Long.parseLong(sTemp); // ����ָ��״̬
            Log.print("����ָ��״̬=" + lStatus);
        }
        sTemp = (String) request.getParameter("sStartSubmit");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strStartSubmit = sTemp; // �ύ����-��
            Log.print("�ύ����-��=" + strStartSubmit);
        }
        sTemp = (String) request.getParameter("sEndSubmit");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strEndSubmit = sTemp; // �ύ����-��
            Log.print("�ύ����-��=" + strEndSubmit);
        }
        sTemp = (String) request.getParameter("sStartExe");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strStartExe = sTemp; // ִ������-��
            Log.print("ִ������-��=" + strStartExe);
        }
        sTemp = (String) request.getParameter("sEndExe");
        if(sTemp != null && sTemp.trim().length() > 0) {
            strEndExe = sTemp; // ִ������-��
            Log.print("ִ������-��=" + strEndExe);
        }
        sTemp = (String) request.getParameter("dMinAmount");
        if(sTemp != null && sTemp.trim().length() > 0) {
            dMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(sTemp)); // �����Сֵ
            Log.print("�����Сֵ=" + dMinAmount);
        }
        sTemp = (String) request.getParameter("dMaxAmount");
        if(sTemp != null && sTemp.trim().length() > 0) {
            dMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(sTemp)); // ������ֵ
            Log.print("������ֵ=" + dMaxAmount);
        }
        switch ((int)lTransType) {
            case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
                sTemp = (String) request.getParameter("lFixedDepositID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lDepositID = Long.parseLong(sTemp); // ���ڴ���ID
                    Log.print("���ڴ���ID=" + lDepositID);
                }
                sTemp = (String) request.getParameter("lFixedDepositIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strDepositNo = sTemp; // ���ڴ��ݺ�
                    Log.print("���ڴ��ݺ�=" + strDepositNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
                sTemp = (String) request.getParameter("lNotifyDepositID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lDepositID = Long.parseLong(sTemp); // ֪ͨ����ID
                    Log.print("֪ͨ����ID=" + lDepositID);
                }
                sTemp = (String) request.getParameter("lNotifyDepositIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strDepositNo = sTemp; // ֪ͨ���ݺ�
                    Log.print("֪ͨ���ݺ�=" + strDepositNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
                sTemp = (String) request.getParameter("lLoanContractID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lContractID = Long.parseLong(sTemp); // ��ͬID(��Ӫ�����廹)
                    Log.print("��ͬID(��Ӫ�����廹)=" + lContractID);
                }
                sTemp = (String) request.getParameter("lLoanContractIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strContractNo = sTemp; // ��ͬ��(��Ӫ�����廹)
                    Log.print("��ͬ��(��Ӫ�����廹)=" + strContractNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
                sTemp = (String) request.getParameter("lSettContractID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lContractID = Long.parseLong(sTemp); // ��ͬID(ί�д����廹)
                    Log.print("��ͬID(ί�д����廹)=" + lContractID);
                }
                sTemp = (String) request.getParameter("lSettContractIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strContractNo = sTemp; // ��ͬ��(ί�д����廹)
                    Log.print("��ͬ��(ί�д����廹)=" + strContractNo);
                }
                break;
            case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
                sTemp = (String) request.getParameter("lRateContractID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lContractID = Long.parseLong(sTemp); // ��ͬID(��Ϣ�����廹)
                    Log.print("��ͬID(��Ϣ�����廹)=" + lContractID);
                }
                sTemp = (String) request.getParameter("lRateContractIDCtrl");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    strContractNo = sTemp; // ��ͬ��(��Ϣ�����廹)
                    Log.print("��ͬ��(��Ϣ�����廹)=" + strContractNo);
                }
                break;
			case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
                sTemp = (String) request.getParameter("lChildClientID");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    lChildClientID = Long.parseLong(sTemp); 
                    Log.print("������λID=" + lChildClientID);
                }
				if (lChildClientID<=0)
				{
					lChildClientID = -2;
				}
                sTemp = (String) request.getParameter("txtClientCode");
                if(sTemp != null && sTemp.trim().length() > 0) {
                    sChildClientNo = sTemp; 
                    Log.print("������λ���=" + sChildClientNo);
                }
                break;
            default :
                break;
        }

        /* ��ʼ����ѯ��Ϣ�� */		
        QueryCapForm queryCapForm = new QueryCapForm();
		if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
		{
			queryCapForm.setChildClientID(lChildClientID);
			queryCapForm.setChildClientNo(sChildClientNo);
			request.setAttribute("child", "1");
		}
        queryCapForm.setTransType(lTransType);          // �������н�������
        queryCapForm.setStatus(lStatus);                // ����ָ��״̬
        queryCapForm.setStartSubmit(strStartSubmit);    // �ύ����-��
        queryCapForm.setEndSubmit(strEndSubmit);        // �ύ����-��
        queryCapForm.setStartExe(strStartExe);          // ִ������-��
        queryCapForm.setEndExe(strEndExe);              // ִ������-��
        queryCapForm.setMinAmount(dMinAmount);          // �����Сֵ
        queryCapForm.setMaxAmount(dMaxAmount);          // ������ֵ
        queryCapForm.setContractID(lContractID);        // ��ͬID
        queryCapForm.setDepositID(lDepositID);          // ����ID
        queryCapForm.setContractNo(strContractNo);      // ��ͬ��
        queryCapForm.setDepositNo(strDepositNo);        // ���ݺ�

        /* ��session�л�ȡ��Ӧ���� */
        queryCapForm.setClientID(sessionMng.m_lClientID);
        queryCapForm.setCurrencyID(sessionMng.m_lCurrencyID);
        queryCapForm.setUserID(sessionMng.m_lUserID);
        /* ����ҳ��˵�ȷ����ѯ���� */
        queryCapForm.setOperationTypeID(OBConstant.QueryOperationType.QUERY);
Log.print("===========OperationTypeID="+queryCapForm.getOperationTypeID());
        queryCapForm.setOrderBy(true);
        /* ��ʼ��EJB */
        OBFinanceInstrDao financeInstrDao = new OBFinanceInstrDao();
        /* ����EJB������ò�ѯ��� */
        Collection cltQcf = financeInstrDao.query(queryCapForm);

        /* �������б��������� */
        request.setAttribute("cltQcf", cltQcf);
        request.setAttribute("queryCapForm", queryCapForm);
        /* ��ȡ�����Ļ��� */
        //ServletContext sc = getServletContext();
        /* ���÷��ص�ַ */
        RequestDispatcher rd = null;
        
        //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	
        
        if (sNextSuccessPage == null || "".equals(sNextSuccessPage)) {
           
            
            pageControllerInfo.setUrl("/capital/query/q061-v.jsp");
 rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        } else {
            
       
       pageControllerInfo.setUrl("/capital/query/" + sNextSuccessPage);
 rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
       
        }

        /* forward�����ҳ�� */
        rd.forward(request, response);
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>