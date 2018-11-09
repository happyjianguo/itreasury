<%--
/*
 * �������ƣ�q003-c.jsp
 * ����˵��������ָ����ϸ��Ϣ�쿴����ҳ��
 * �������ߣ�kewen hu
 * ������ڣ�2004-02-12
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
    //�������
    String strTitle = "�۽��������ѯ��";
    //��ʱ��
    String sTemp = null;
    long lShowMenu = OBConstant.ShowMenu.YES;
    sTemp = (String)request.getParameter("menu");
    if ((sTemp != null) && (sTemp.equals("hidden"))) {
        lShowMenu = OBConstant.ShowMenu.NO;
    }
    request.setAttribute("menu", sTemp);
    Log.print("menu:"+lShowMenu);
    long iTransType = 0;     //��������
    sTemp = (String) request.getParameter("txtTransType");
    if (sTemp != null && sTemp.trim().length() > 0) {
        iTransType = Long.parseLong(sTemp);
    }
    Log.print("txtTransType:"+iTransType);
    long lID = 0;           //ָ�����
    sTemp = (String) request.getParameter("txtID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lID = Long.parseLong(sTemp);
    }
    Log.print("lID:"+lID);
    String strReturn = "";           //��һҳ
    sTemp = (String) request.getParameter("strReturn");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strReturn = sTemp;
    }
    Log.print("strReturn:"+strReturn);
    request.setAttribute("strReturn", strReturn);
    request.setAttribute("strAction", "query");
    String strFrom = null;  //forwardҳ��
    switch ((int)iTransType) {
	case (int)OBConstant.SettInstrType.APPLYCAPITAL://1;�ʽ�����
        strFrom = "/capital/captransfer/c014-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF://1;�ʽ𻮲�-��ת
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY://2;�ʽ𻮲�-���и���
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT://3;�ʽ𻮲�-�ڲ�ת��
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.CAPTRANSFER_OTHER://3;�ʽ𻮲�-����
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.CHILDCAPTRANSFER://3;������λ�ʽ𻮲�
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
		request.setAttribute("child", "1");
        break;
    case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT://4;���ڿ���
        strFrom = "/capital/fixed/f004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER://5;����֧ȡ
        strFrom = "/capital/fixed/f014-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT://6;֪ͨ����
        strFrom = "/capital/notify/n004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW://7;֪֧ͨȡ
        strFrom = "/capital/notify/n014-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.TRUSTLOANRECEIVE://8;��Ӫ�����廹
        strFrom = "/capital/loanrepayment/l006-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CONSIGNLOANRECEIVE://9;ί�д����廹
        strFrom = "/capital/loanrepayment/l016-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.INTERESTFEEPAYMENT://10;��Ϣ�����廹
        strFrom = "/capital/loanrepayment/l026-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.YTLOANRECEIVE://11;���Ŵ����廹
        strFrom = "/capital/loanrepayment/l006-v.jsp?src=1&isYT=1";
        break;
    default :
        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
        return;
    }

    try {
        /* ��ʼ����Ϣ��ѯ�� */
        OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        FinanceInfo info = new FinanceInfo();
        /*�������з�����ѯ��Ϣ����*/
        info = obFinanceInstrDao.findByID(
            lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);//���û�����lID��ˮ����info ��null
        if (info == null || strFrom == null) {//Ϊ����ʾ����ҳ��
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
            return;
        }
		
		ClientAccountInfo accountInfo=null;
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		//if (financeInfo!=null)	accountID=financeInfo.getPayerAcctID();
		accountInfo=dao.findAccountInfoByClient(sessionMng.m_lUserID, sessionMng.m_lClientID, -1, 1);
		
		request.setAttribute("accountInfo",accountInfo);


        //�������б���������
        request.setAttribute("financeInfo",info);
        request.setAttribute("resultInfo",info);
        //��ȡ�����Ļ���
        //ServletContext sc = getServletContext();
        //���÷��ص�ַ
       
        
        //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strFrom);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        
        
        //forward�����ҳ��
        rd.forward(request, response);
    } catch(IException ie) {
        //����ҳ��
        OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>