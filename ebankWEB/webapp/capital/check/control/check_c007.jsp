<%--
/*
 * �������ƣ�check_c006.jsp
 * ����˵��������ָ����ϸ��Ϣ�쿴����ҳ��
 * �������ߣ�
 * ������ڣ�2010-10-12
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
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
    String strSuccessPageURL = "";           //�ɹ�ҳ
    sTemp = (String) request.getParameter("strSuccessPageURL");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strSuccessPageURL = sTemp;
    }

    
    request.setAttribute("search",request.getParameter("search"));
    request.setAttribute("strAction", "query");
    String strFrom = null;  //forwardҳ��
    switch ((int)iTransType) {
    case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF://1;�ʽ𻮲�-��ת
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY://2;�ʽ𻮲�-���и���
//    case (int)OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER://�ʽ𻮲����»�
        strFrom = "/capital/check/view/check_v012.jsp?src=1&checktype=1";
        break;
	case (int)OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY://2;�ʽ𻮲�-���и���-��˾����
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT://2;�ʽ𻮲�-���и���-�����˻�
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
    case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT://3;�ʽ𻮲�-�ڲ�ת��
        strFrom = "/capital/check/view/check_v012.jsp?src=1&checktype=1";
        break;        
	case (int)OBConstant.SettInstrType.CAPTRANSFER_OTHER://3;�ʽ𻮲�-����
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
        break;
	case (int)OBConstant.SettInstrType.CHILDCAPTRANSFER://3;������λ�ʽ𻮲�
        strFrom = "/capital/captransfer/c004-v.jsp?src=1";
		request.setAttribute("child", "1");
        break;
    case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT://4;���ڿ���
        strFrom = "/capital/check/view/check_v007.jsp?src=1&checktype=1";
        break;
    case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER://5;����֧ȡ
        strFrom = "/capital/check/view/check_v008.jsp?src=1&checktype=1";
        break;
    case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT://6;֪ͨ����
        strFrom = "/capital/check/view/check_v009.jsp?src=1&checktype=1";
        break;
    case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW://7;֪֧ͨȡ
        strFrom = "/capital/check/view/check_v010.jsp?src=1&checktype=1";
        break;
     case (int)OBConstant.SettInstrType.DRIVEFIXDEPOSIT://19;��������
        strFrom = "/capital/check/view/check_v011.jsp?src=1&checktype=1";
        break;
     case (int)OBConstant.SettInstrType.CHANGEFIXDEPOSIT://20;����ת��	
        strFrom = "/capital/fixed/f011-v.jsp?src=1";
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
    
    //��������תҳ��
    if(strSuccessPageURL != null && !"".equals(strSuccessPageURL))
    {
    	strFrom = strSuccessPageURL;  
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

        //�������б���������
        request.setAttribute("financeInfo",info);
        request.setAttribute("resultInfo",info);
        //��ȡ�����Ļ���
        //ServletContext sc = getServletContext();
        //���÷��ص�ַ
        
       
       
       //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		Log.print(""+strContext + strFrom);
		pageControllerInfo.setUrl(strContext + strFrom);
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	       
	    //forward�����ҳ��
	    rd.forward(request, response);
    } catch(IException ie) {
        //����ҳ��
        OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>