<%--
/*
 * �������ƣ�s004-c.jsp
 * ����˵��������ָ������ǩ�Ͽ���ҳ��
 * �������ߣ�xfma
 * ������ڣ�2011-04-19
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //�������
    String strTitle = "[ҵ��ǩ��]";
    /* ��ʼ����Ϣ�� */
	OBBankPayInfo financeInfo = new OBBankPayInfo();
    TransInfo transinfo = new TransInfo();
    try {
        long lShowMenu = OBConstant.ShowMenu.YES;
        String strMenu = (String)request.getParameter("menu");
        if (strMenu != null && strMenu.equals("hidden")) {
            lShowMenu = OBConstant.ShowMenu.NO;
        }
        Log.print("=================strMenu="+strMenu);
        //��ѯ������Ϣ������s005-v.jsp,����ʱ����formдֵ
        QueryCapForm qcf = new QueryCapForm();
        //��ʱ��
        String sTemp = null;
        // �������н�������,�ʽ𻮲���ר�ŵ�����
        sTemp = (String) request.getParameter("SelectType");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setTransType(Long.parseLong(sTemp));
            Log.print("SelectType:"+sTemp);
        }

		Log.print("**********�ڴ�ѭ��*******qcf.getTransType()**********"+qcf.getTransType());
        // �ύ����-��
        sTemp = (String) request.getParameter("txtConfirmA");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStartSubmit(sTemp);
            Log.print("txtConfirmA:"+sTemp);
        }
        // �ύ����-��
        sTemp = (String) request.getParameter("txtConfirmB");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setEndSubmit(sTemp);
            Log.print("txtConfirmB:"+sTemp);
        }
        // ����ָ��״̬
        sTemp = (String) request.getParameter("SelectStatus");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStatus(Long.parseLong(sTemp));
            Log.print("SelectStatus:"+sTemp);
        }
        // ���׽��-��Сֵ
        if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase(""))) {
            qcf.setMinAmount(Double.parseDouble(DataFormat.reverseFormatAmount(
                request.getParameter("txtMinAmount").trim())));
            Log.print("s004-c.jsp:qcf.dMinAmount="+qcf.getMinAmount());
        }
        // ���׽��-���ֵ
        if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase(""))) {//�Ƿ������˻�
            qcf.setMaxAmount(Double.parseDouble(DataFormat.reverseFormatAmount(
                request.getParameter("txtMaxAmount").trim())));
            Log.print("s004-c.jsp:qcf.dMaxAmount="+qcf.getMaxAmount());
        }
        // ִ������-��
        sTemp = (String) request.getParameter("txtExecuteA");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStartExe(sTemp);
            Log.print("txtExecuteA:"+sTemp);
        }
        // ִ������-��
        sTemp = (String) request.getParameter("txtExecuteB");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setEndExe(sTemp);
            Log.print("txtExecuteB:"+sTemp);
        }

          //�������Form��Ϣ�ı���
        long lIsCheck = -1;//�Ƿ���ǩ��
        sTemp = (String) request.getParameter("txtisCheck");
        if (sTemp != null && sTemp.trim().length() > 0) {
            lIsCheck = Long.parseLong(sTemp);
            Log.print("txtisCheck:"+sTemp);
        }

        String strCheckbox[] = request.getParameterValues("txtCheckbox");
        String strID[] = null;
		String strTypeID[] = null;
        if (request.getParameter("txtID") != null && request.getParameter("txtID").trim().length() > 0) {
            strID = request.getParameterValues("txtID");
			strTypeID=request.getParameterValues("txtTransType");
			if(strTypeID == null)
				strTypeID[0] = request.getParameter("txtTransType");
        }
        Log.print("strTypeID��ֵ�ǣ�"+request.getParameter("txtTransType"));
        Log.print("�Ƿ���ǩ��ȡ����"+(lIsCheck==0?"��":"��"));

        //��ʼ��EJB
        OBFinanceInstrHome financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
        OBFinanceInstr financeInstr = financeInstrHome.create();

        //����ejb����
        int iCount = -1;//�������ݵĸ���
        if (strCheckbox != null) {
            iCount = strCheckbox.length;
        } else {
            iCount = 1;
        }
        Log.print("iCount:"+iCount);
        String strTemp = new String();//������Ϣ
        Vector vcResult = new Vector(1);//������Ϣ
        //��ʼ״̬��δǩ�ϣ�����ǩ�ϲ���
        if (lIsCheck == 1) {
            //����
            for (int i=0; i<iCount; i++) {
                int iTag;//�����±�
                long lID;//ָ�����
                long lRet;//����sign()�ķ���ֵ
                if (strCheckbox != null) {
                    iTag = Integer.parseInt(strCheckbox[i]);
                    lID = Long.parseLong(strID[iTag-1]);
                } else {
                    lID = Long.parseLong(strID[0]);
                }
                //����Ejb����
                //������־�����
		        try{
	            	financeInfo = financeInstr.findByID( lID);
	            	financeInfo.setNsignuserid(sessionMng.m_lUserID);//����ǩ����
	            	
	                lRet = financeInstr.signBankPay(lID,sessionMng.m_lUserID);//��������Ϊʵ���������������ࡣ 
	                transinfo.setStatus(Constant.SUCCESSFUL);
	             }catch(Exception ex)
		         {
		              transinfo.setStatus(Constant.FAIL);
			          ex.printStackTrace();
			          throw new IException(ex.getMessage());
		         }finally
	             {	
	              	if(transinfo.getStatus()!=-1)
		            	{
			          	TranslogBiz translofbiz= new TranslogBiz();
			          	transinfo.setHostip(request.getRemoteAddr());
			          	transinfo.setHostname(request.getRemoteHost());
			          	transinfo.setTransType(financeInfo.getNtranstype());
			         	transinfo.setActionType(Constant.TransLogActionType.sign);				
			          	translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
	         	 	}
	        	 }
                //����	
				if(lRet<0){
					strTemp = "����ǩ��ʧ�ܣ�<br>";
					vcResult.add(strTemp);
					strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
					vcResult.add(strTemp);
				}else{
					strTemp ="�������ύ��"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+
					"��<br>";
					vcResult.add(strTemp);
					strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
					vcResult.add(strTemp);
				}
            }
        } else {//��ʼ״̬Ϊ��ǩ�ϣ�ȡ��ǩ��
            for(int i=0; i<iCount; i++) {
                int iTag;//�����±�
                long lID;//ָ�����
                long lRet;//����cancelsign()�ķ���ֵ
                if (strCheckbox != null) {
                    iTag = Integer.parseInt(strCheckbox[i]);
                    lID = Long.parseLong(strID[iTag-1]);
                } else {
                    lID = Long.parseLong(strID[0]);
                }
                //����Ejb����
                //������־�����
		        try{
	                financeInfo = financeInstr.findByID(lID);
	                lRet = financeInstr.cancelSignBankPay(lID, sessionMng.m_lUserID);                     
	                transinfo.setStatus(Constant.SUCCESSFUL);
                }
		        catch(Exception ex)
		        {
		              transinfo.setStatus(Constant.FAIL);
			          ex.printStackTrace();
			          throw new IException(ex.getMessage());
		        }
			    finally
	            {	
		            if(transinfo.getStatus()!=-1)
		            {
			          	TranslogBiz translofbiz= new TranslogBiz();
			          	transinfo.setHostip(request.getRemoteAddr());
			          	transinfo.setHostname(request.getRemoteHost());
			          	transinfo.setTransType(financeInfo.getNtranstype());
			          	transinfo.setActionType(Constant.TransLogActionType.cancelSign);				
			          	translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
		         	}
				}
				if(lRet<0){
					strTemp = "����ȡ��ǩ��ʧ�ܣ�<br>";
					vcResult.add(strTemp);
					strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
					vcResult.add(strTemp);
				}else{
					 //������Ϣ����
					strTemp = "���ҵ����Ϊ�Ѹ���״̬����Ҫǩ�Ϻ�����ύ��"+
					NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"��<br>";
					vcResult.add(strTemp);
					strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
					vcResult.add(strTemp);
				}
            }
        }

        //�������б���������
        request.setAttribute("menu", strMenu);
        request.setAttribute("return",vcResult);
        request.setAttribute("FormValue",qcf);
        request.setAttribute("isCheck",request.getParameter("txtisCheck"));
        //���÷��ص�ַ
        RequestDispatcher rd = null;
        
        //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
        if (strCheckbox != null) {
        	pageControllerInfo.setUrl("/bankpay/view/s005-v.jsp");
			//�ַ�
	 		rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        } else {
          	pageControllerInfo.setUrl("/bankpay/view/s006-v.jsp");
			//�ַ�
	 		rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        }
        //forward�����ҳ��
        rd.forward(request, response);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>
