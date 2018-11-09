<%--
/**
 ҳ������ ��queryControl.jsp
 ҳ�湦�� : ������ϸ��Ϣ��ѯ�Ŀ�����ҳ��
 ��    �� ��kewen hu
 ��    �� ��2004-02-24
 ����˵�� ��
			�ڲ�ѯҳ��ֻ���ʹ�������url
			    /settlement/query/control/querycontrol.jsp?TransactionTypeID=&TransNo=&strFailPageURL=
			һ�����գ�
				/settlement/query/control/querycontrol.jsp?TransactionTypeID=&TransNo=&SerialNo=&strFailPageURL=
 �޸���ʷ ��
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//ҳ����Ʊ���
    String strNextPageURL = null;
	String strSuccessPageURL = null;
	String strFailPageURL = null;
	String strAction = "Query";
	String strContext = request.getContextPath();

    //�������
    String strTitle = "[�˻���ʷ��Ϣ]";
    try {
         /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        //�������
		long lTransactionTypeID = -1;
		String strTransactionTypeID = "";
		String strTransNo = "";
		String strSerialNo = "";
		String strID = "";
		long lAccountID = 1;
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String strTemp = null;
		strTemp = (String)request.getAttribute("TransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransactionTypeID = strTemp;
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("TransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		strTemp = (String)request.getAttribute("SerialNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strSerialNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strID = strTemp;
		}
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lAccountID = Long.parseLong(strTemp);
		}
		request.setAttribute("lAccountID",String.valueOf(lAccountID));

		Log.print("**********����ַ�ҳ�����*************");
		Log.print("lTransactionTypeID="+lTransactionTypeID);
		Log.print("strTransNo="+strTransNo);
		Log.print("strSerialNo="+strSerialNo);
		Log.print("strID="+strID);
		Log.print("lAccountID="+lAccountID);

		switch ((int)lTransactionTypeID)
		{
			//���ڽ���
			case (int)SETTConstant.TransactionType.BANKPAY: //���и���
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a515-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY: //��˾����
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a516-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT: //�����˻�
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a517-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.SUBCLIENT_BANKPAY: //������λ���и���
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a515-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID+"&child=1";
				break;
			case (int)SETTConstant.TransactionType.DRAFTPAY: //Ʊ�㸶��
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a305-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.INTERNALVIREMENT: //�ڲ�ת��
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a315-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE: //�����տ�
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE_GATHERING: //�����տ���տ���
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT: //�����տ��Ա��λ�տ�
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT: //�����տת��Ա��λ�տ�
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE: //������λ�����տ�
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a325-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID+"&child=1";
				break;
			case (int)SETTConstant.TransactionType.CASHPAY: //�ֽ𸶿�
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a335-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CHECKPAY: //֧Ʊ����
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a345-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CASHRECEIVE: //�ֽ��տ�
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a355-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.OTHERPAY: //��������
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a365-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CONSIGNSAVE: //ί�д��
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a375-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.CAUTIONMONEYSAVE: //��֤����
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a385-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
			case (int)SETTConstant.TransactionType.ONETOMULTI: //һ������
				strNextPageURL = strContext+ "/accountinfo/a313-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&lSerialNo="+strSerialNo+"&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;

			//������
			case (int)SETTConstant.TransactionType.GENERALLEDGER: //������
				strNextPageURL = strContext+ "/accountinfo/a302-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a306-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//������
			case (int)SETTConstant.TransactionType.TRANSFEE: //���׷��ô���
				strNextPageURL = strContext+ "/accountinfo/a303-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a307-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//���ڽ���
			case (int)SETTConstant.TransactionType.OPENFIXEDDEPOSIT: //���ڿ���
				strNextPageURL = strContext+ "/accountinfo/a304-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a304-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.OPENNOTIFYACCOUNT: //֪ͨ����
				strNextPageURL = strContext+ "/accountinfo/a304-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a334-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER: //����֧ȡ
				strNextPageURL = strContext+ "/accountinfo/a305-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a316-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.NOTIFYDEPOSITDRAW: //֪֧ͨȡ
				strNextPageURL = strContext+ "/accountinfo/a305-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a346-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.FIXEDCONTINUETRANSFER: //��������
				strNextPageURL = strContext+ "/accountinfo/a306-c.jsp?strTransNo="+strTransNo+"&strAction=FixedQuery&strSuccessPageURL=../accountinfo/a326-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//����/ί�д���
			case (int)SETTConstant.TransactionType.TRUSTLOANGRANT: //���з���
				strNextPageURL = strContext+ "/accountinfo/a913-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a914-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;		
				break;
			case (int)SETTConstant.TransactionType.TRUSTLOANRECEIVE: //�����ջ�
				strNextPageURL = strContext+ "/accountinfo/a917-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a925-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;//v016
				break;
			case (int)SETTConstant.TransactionType.CONSIGNLOANGRANT: //ί�з���
				strNextPageURL = strContext+ "/accountinfo/a913-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a926-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;//v026
				break;
			case (int)SETTConstant.TransactionType.CONSIGNLOANRECEIVE: //ί���ջ�
				strNextPageURL = strContext+ "/accountinfo/a917-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a918-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//��Ϣ����֧��
			case (int)SETTConstant.TransactionType.INTERESTFEEPAYMENT: //��Ϣ����֧��
				strNextPageURL = strContext+ "/accountinfo/a901-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a902-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//��ʴ����ջ�
			case (int)SETTConstant.TransactionType.MULTILOANRECEIVE: //��ʴ����ջ�
				strNextPageURL = strContext+ "/accountinfo/a920-c.jsp?strTransNo="+strTransNo+"&lSerialNo="+strSerialNo+"&strAction="+strAction+"&strSuccessPageURL=&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;

			//���ִ���
			case (int)SETTConstant.TransactionType.DISCOUNTGRANT: //���ַ���
				strNextPageURL = strContext+ "/accountinfo/a905-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a906-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.DISCOUNTRECEIVE: //�����ջ�
				strNextPageURL = strContext+ "/accountinfo/a909-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a910-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
				
			//���Ŵ���
			case (int)SETTConstant.TransactionType.BANKGROUPLOANGRANT: //���Ŵ����
				strNextPageURL = strContext+ "/accountinfo/a601-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a602-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.BANKGROUPLOANRECEIVE: //���Ŵ����ջ�
				strNextPageURL = strContext+ "/accountinfo/a603-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a604-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			

			//���⽻��
			case (int)SETTConstant.TransactionType.SPECIALOPERATION: //���⽻��
				strNextPageURL = strContext+ "/accountinfo/a307-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a308-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID;
				break;
				
			//��֤��ҵ��
			case (int)SETTConstant.TransactionType.OPENMARGIN: //��֤����
				strNextPageURL = strContext+ "/accountinfo/a1011-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a1012-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.WITHDRAWMARGIN: //��֤��֧ȡ
				strNextPageURL = strContext+ "/accountinfo/a1013-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a1014-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			//case (int)SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN: //��Ϣ����֧����֤���Ϣ
				//strNextPageURL = strContext+ "/accountinfo/a1011-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a1012-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				//break;
			//��������ҵ��
			/*case (int)SETTConstant.TransactionType.RECEIVEFINANCE: //���������տ�
				strNextPageURL = strContext+ "/accountinfo/a601-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a602-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
			case (int)SETTConstant.TransactionType.RETURNFINANCE: //�������޻���
				strNextPageURL = strContext+ "/accountinfo/a603-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a604-v.jsp&strFailPageURL="+strFailPageURL+"&lID="+strID;
				break;
				*/
			case (int)SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER: //���и���»���Ա��λ
				strNextPageURL = strContext+ "/accountinfo/a301-c.jsp?strTransNo="+strTransNo+"&strAction="+strAction+"&strSuccessPageURL=../accountinfo/a515-v.jsp&strFailPageURL="+strFailPageURL+"&lId="+strID+"&child=1";
				break;
			default:
				strNextPageURL = null;
				out.print("����ҵ�����Ͳ��ԣ�");
		}

		if(strNextPageURL != null)
		{
			response.sendRedirect(strNextPageURL);
		}
    } catch( IException ie ) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>