package com.iss.itreasury.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.closesystem.BeanFactory;
import com.iss.itreasury.dataentity.GLVouchInfo;
import com.iss.itreasury.settlement.consignvoucher.dao.sett_AccountTrustVoucherDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class ExportVouchesServlet extends HttpServlet {
	
	private ServletConfig config;

	public void init(ServletConfig config) throws ServletException
	{
		
		super.init(config);
		this.config = config;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		exportVouches(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		exportVouches(request, response);
	}

	/*public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("ExportVouchesServlet load success!");
	}*/
	
	public void exportVouches(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");
		//�������
		String strExecuteDate = "";
		long lCurrencyID = 0;
		long lOfficeID = 0;
		String strFileName = "";
		String strSysDate = "";
		long lModuleID = 0;
		String strTemp = null;	
		Collection collVouches = null;
		
		
		// ������Ա��ֶα���
		//	 ��Ӧ�� sett_glentry
		String strSubjectCode = ""; // ��Ŀ���
		String strTransNO = ""; // ���ױ��
		long lTransactionTypeID = -1; // ��������
		long lTransDirection = -1; // ���׷���
		double dbMamount = 0.0; // ���׷�����
		Timestamp dtExecute = null; // ִ������
		Timestamp dtInterestStart = null; // ��Ϣ����
		String strAbstract = ""; // ժҪ
		String strMulticode = ""; // ��ά��
		long lInputUserID = -1; // ¼����
		long lCheckUserID = -1; // ������
		long lStatusID = -1;
		long lGroup = 0;
		long lType = 0;
		long lPostStatusID = -1;

		// ��Ӧ�� sett_glsubjectdefinition
		String strSegCode1 = "";
		String strSegCode2 = "";
		String strSegCode3 = "";
		String strSegCode4 = "";
		String strSegCode5 = "";
		String strSegCode6 = "";
		String strSegCode7 = "";
		String strSegName1 = "";
		String strSegName2 = "";
		String strSegName3 = "";
		String strSegName4 = "";
		String strSegName5 = "";
		String strSegName6 = "";
		String strSegName7 = "";
		long lSubjectType = -1; // ��Ŀ����
		long lIsLeaf = -1; // �Ƿ�ĩ����Ŀ
		long lIsRoot = -1; // �Ƿ����ʿ�Ŀ
		long lParentSubjectID = -1; // �ϼ���Ŀ����ID
		long lBalanceDirection = -1; // ��������
		long lAmountDirection = 9; // ���Ʒ������
		long lStatus = -1; // ״̬
		long lLedertype = -1; // ����
		long lSecurityLevel = -1; // ��ȫ����
		long lUseScope = -1; // ʹ�÷�Χ
		long lFlag = -1; // ����
		Timestamp dtValidDate = null; // ��Ч����

		double entered_dr = 0.00; // ԭ�ҽ跽
		double entered_cr = 0.00; // ԭ�Ҵ���
		double accounted_dr = 0.00; // ��λ�ҽ跽
		double accounted_cr = 0.00; // ��λ�Ҵ���
		String strSeg3 = "";		// һ������Ŀ
		String strSeg4 = "";		// ���ļ���Ŀ
		String strSeg5 = "";		//�ο���
		
		String space = ",";
		String newLine = "\r\n";

		//��ȡ����
		strTemp = request.getParameter("exportDate");
		if(strTemp != null || strTemp != "") {
			strExecuteDate = strTemp;
		}
		System.out.println("ExportVouchesServlet strExecuteDate = " + strExecuteDate);
		
		strTemp = request.getParameter("currencyID");
		if(strTemp != null || strTemp != "") {
			lCurrencyID = Long.parseLong(strTemp);
		}
		System.out.println("ExportVouchesServlet lCurrencyID = " + lCurrencyID);
		
		strTemp = request.getParameter("officeID");
		if(strTemp != null || strTemp != "") {
			lOfficeID = Long.parseLong(strTemp);
		}
		System.out.println("ExportVouchesServlet lOfficeID = " + lOfficeID);
		
		strTemp = request.getParameter("fileName");
		if(strTemp != null || strTemp != "") {
			strFileName = strTemp;
		}
		System.out.println("ExportVouchesServlet strFileName = " + strFileName);
		
		strTemp = request.getParameter("moduleID");
		if(strTemp != null || strTemp != "") {
			lModuleID = Long.parseLong(strTemp);
		}
		System.out.println("ExportVouchesServlet lModuleID = " + lModuleID);
		
		strSysDate = DataFormat.formatDate(Env.getSystemDate(lOfficeID,lCurrencyID));
		System.out.println("ExportVouchesServlet strSysDate = " + strSysDate);
		
		//��ȡϵͳʱ��������
		String strSysYear	= strSysDate.substring(0,4);
		String strSysMonth	= strSysDate.substring(5,7);
		String strSysDay	= strSysDate.substring(8,10);
		System.out.println(" ϵͳ�����ǣ�"+strSysYear+"��"+strSysMonth+"��"+strSysDay+"��");
		
		//��ȡ��ѯʱ��������
		String strYear	= strExecuteDate.substring(0,4);
		String strMonth	= strExecuteDate.substring(5,7);
		String strDay	= strExecuteDate.substring(8,10);
		System.out.println(" ��ѯ�����ǣ�"+strYear+"��"+strMonth+"��"+strDay+"��");
		
		String[] strTitles = new String[]{	"attribute10",	//��λ���  �̶�ֵ���Ϻ���70021
											"group_id",		//��ID ���Ϻ��ͻ�ʵʩʱ��� 2007-9-25
											"ref1",			// ���� / ����   ����˾�ʽ����ϵͳ���ݵ���[������]
											"ref4",			//ƾ֤���ͣ��̶�ֵ ת��
											"ref10",		//��¼��˵�� ��ժҪ 
											"Rref1",		//���� �� ref1һ��  ����˾�ʽ����ϵͳ���ݵ���[������]
											"ref21",		//	Ϊ��
											"Rref4",		//��ref10һ�� 
											"ref22",		//������ ȡ������ʱ��	����˾�ʽ����ϵͳ���ݵ���[������]
											"batch_acct_date",	//���Ļ������		���ǵ�ǰ�������ǹػ����� ������ʷ��ȡִ������
											"header_acct_date",	//ƾ֤�Ļ������	���ǵ�ǰ�������ǹػ����� ������ʷ��ȡִ������
											"seg1",			//��˾��	�̶�ֵ70021
											"seg2",			//�ɱ����Ķ�	�̶�ֵ 210000
											"seg3",			//һ������Ŀ ȡһ����	
											"seg4",			//���ļ���Ŀ ȡ���ļ� ���޲�Ϊ000000
											"seg5",			//�ο��� ȡ�ο��� ���޲�Ϊ000000000
											"seg6",			//��Ŀ�� �̶�ֵ 0
											"seg7",			//���ö� �̶�ֵ 0
											"currency_code",	//����	�̶�ֵ RMB
											"currency_convesion_rate",	// ���� �̶�ֵ 1
											"entered_dr",	//ԭ���跽
											"entered_cr",	//ԭ������
											"accounted_dr",	//��λ�ҽ跽
											"accounted_cr",	//��λ�Ҵ���
											"lines_description",	//�ռ��������� ��ժҪ
											"set_of_books_id",	//�ʲ� �̶�ֵ 75
											"source_name",		//��Դ �̶�ֵ ����˾�ʽ����ϵͳ
											"category_name"		};	//��� �̶�ֵ  ת��
		
		try {
			ServletContext sc = getServletContext();
			String mimeType = sc.getMimeType(strFileName);
			if (mimeType == null) {
	          	   mimeType="application/any";
	        }
			response.setHeader("Content-Disposition","attachment; filename=\"" + toUtf8String(strFileName)+"\"");
			
			response.setContentType(mimeType); 		
			PrintWriter out = response.getWriter();
			String strPrint = "";
			
			for(int nI = 0; nI < strTitles.length;nI++) {
				if(nI == (strTitles.length - 1) ) {
					strPrint+=strTitles[nI];
				} else {
					strPrint+=strTitles[nI] + space;
				}
			}
			//out.print(strPrint+newLine);		// ��ӡ����
			
			collVouches = BeanFactory.getGLWithinBean(lModuleID).getVouches(strExecuteDate,lOfficeID,lCurrencyID);
			System.out.println("ExportVouchesServlet vouches is null = " + (collVouches == null));
			Iterator it = null;
			if(collVouches != null)
				it = collVouches.iterator();
			if(it != null) {
				while(it.hasNext()) 
				{		
					strPrint = "";
					entered_dr = 0.00; // ԭ�ҽ跽		
					entered_cr = 0.00; // ԭ�Ҵ���
					accounted_dr = 0.00; // ��λ�ҽ跽
					accounted_cr = 0.00; // ��λ�Ҵ���
					
					GLVouchInfo vouch = (GLVouchInfo)it.next();
					System.out.println(" vouch = " + vouch);
					lOfficeID = vouch.getLOfficeID();
					lCurrencyID = vouch.getLCurrencyID();
					strSubjectCode = vouch.getStrSubjectCode();	// ��Ŀ���
					strTransNO = vouch.getStrTransNO();			// ���ױ��
					lTransactionTypeID = vouch.getLTransactionTypeID();
					lTransDirection = vouch.getLTransDirection();
					dbMamount = vouch.getDbMamount();
					if(lTransDirection == SETTConstant.DebitOrCredit.DEBIT)
					{
						entered_dr = dbMamount;
						accounted_dr = dbMamount;
					}
					else
					{
						entered_cr = dbMamount;
						accounted_cr = dbMamount;
					}
					dtExecute = vouch.getDtExecute();
					dtInterestStart = vouch.getDtInterestStart();
					strAbstract = vouch.getStrAbstract();
					strMulticode = vouch.getStrMulticode();
					lInputUserID = vouch.getLInputUserID();
					lCheckUserID = vouch.getLCheckUserID();
					lStatusID = vouch.getLStatusID();
					lGroup = vouch.getLGroup();
					lType = vouch.getLType();
					lPostStatusID = vouch.getLPostStatusID();
					strSegCode1 = vouch.getStrSegCode1();
					strSegCode2 = vouch.getStrSegCode2();		//	��Ŀ���
					//strSegCode2 = "1001001222333";
					/*if(strSegCode2.length() == 7)		// �����ļ���Ŀ �� �ο���
					{
						strSeg3 = strSegCode2;
						strSeg4 = "";
						strSeg5 = "";
					}
					else if(strSegCode2.length() == 13)	// �޲ο��� ��һ�����ļ���Ŀ
					{
						strSeg3 = strSegCode2.substring(0,7);
						strSeg4 = strSegCode2.substring(7,13);
						strSeg5 = "";
					}
					else if(strSegCode2.length() > 13)	// �вο��� һ�����ļ���Ŀ
					{
						strSeg3 = strSegCode2.substring(0,7);
						strSeg4 = strSegCode2.substring(7,13);
						strSeg5 = strSegCode2.substring(13,strSegCode2.length());
					}*/
					strSegCode3 = vouch.getStrSegCode3();
					strSegCode4 = vouch.getStrSegCode4();
					strSegCode5 = vouch.getStrSegCode5();
					strSegCode6 = vouch.getStrSegCode6();
					strSegCode7 = vouch.getStrSegCode7();
					strSegName1 = vouch.getStrSegName1();
					strSegName2 = vouch.getStrSegName2();		//	��Ŀ����
					strSegName3 = vouch.getStrSegName3();
					strSegName4 = vouch.getStrSegName4();
					strSegName5 = vouch.getStrSegName5();
					strSegName6 = vouch.getStrSegName6();
					strSegName7 = vouch.getStrSegName7();
					lSubjectType = vouch.getLSubjectType();
					lIsLeaf = vouch.getLIsLeaf();
					lIsRoot = vouch.getLIsRoot();
					lParentSubjectID = vouch.getLParentSubjectID();
					lBalanceDirection = vouch.getLBalanceDirection();
					lAmountDirection = vouch.getLAmountDirection();
					lStatus = vouch.getLStatus();
					lLedertype = vouch.getLLedertype();
					lSecurityLevel = vouch.getLSecurityLevel();
					lUseScope = vouch.getLUseScope();
					lFlag = vouch.getLFlag();
					dtValidDate = vouch.getDtValidDate();
					
					//strValues[]  ��������涨��� strTitles[] ���Ӧ
					String[] strValues = new String[]{	"70021",							//��λ���  �̶�ֵ���Ϻ���70021
														strYear+strMonth+strDay,	//��ID ���Ϻ��ͻ�ʵʩʱ���  2007��9��25��
														"����˾��������"+strYear+strMonth+strDay,					
																							// ���� / ����
																							//��ʽ ����˾�ʽ����ϵͳ���ݵ���[������]
														"ת��",								//ƾ֤���ͣ��̶�ֵ ת��
														strAbstract!=null?strAbstract:"Ϊ��",	//��¼��˵�� ��ժҪ
														"����˾��������"+strYear+strMonth+strDay,				
																							//���� �� ref1һ��
														"",									//	Ϊ��
														strAbstract!=null?strAbstract:"",	//��ref10һ�� 
														"����˾��������"+strYear+strMonth+strDay,	
																							//��������ʽ ����˾�ʽ����ϵͳ���ݵ���[������]
														strMonth+"-"+strYear,		//���Ļ������ ���ǵ�ǰ�������ǹػ����� ������ʷ��ȡִ������
														strMonth+"-"+strYear,				//ƾ֤�Ļ������ �����Ļ������һ��
														strSegCode1!=null&&strSegCode1!=""?strSegCode1:"70021",		//��˾��	�̶�ֵ 70021
														strSegCode2!=null&&strSegCode2!=""?strSegCode2:"210000",		//�ɱ����Ķ�	�̶�ֵ 210000
														strSegCode3!=null&&strSegCode3!=""?strSegCode3:"0000000",	//һ������Ŀ ȡ����
														strSegCode4!=null&&strSegCode4!=""?strSegCode4:"000000",		//���ļ���Ŀ ȡ�ļ�
														strSegCode5!=null&&strSegCode5!=""?strSegCode5:"000000000",	//�ο���	
														strSegCode6!=null&&strSegCode6!=""?strSegCode6:"000000000",	//��Ŀ�� �̶�ֵ 000000000
														strSegCode7!=null&&strSegCode7!=""?strSegCode7:"0000",			//���ö� �̶�ֵ 0000
														"RMB",								//����	�̶�ֵ RMB
														"1",								// ���� �̶�ֵ 1
														entered_dr!=0.0?new String().valueOf(entered_dr):"", //ԭ���跽
														entered_cr!=0.0?new String().valueOf(entered_cr):"", //ԭ������
														accounted_dr != 0.0?new String().valueOf(accounted_dr):"", //��λ�ҽ跽
														accounted_cr!=0.0?new String().valueOf(accounted_cr):"", //��λ�Ҵ���
														strAbstract!=null?strAbstract:"Ϊ��",				//�ռ��������� ��ժҪ
														"75",								//�ʲ� �̶�ֵ  75
														"�ʽ����ϵͳ",			//��Դ �̶�ֵ  ����˾�ʽ����ϵͳ
														"ת��"		};			//��� �̶�ֵ  ת�� 
					for(int nJ = 0; nJ < strValues.length;nJ++){
						if(nJ == (strValues.length - 1) ) {
							strPrint+=strValues[nJ];
						} else {
							strPrint+=strValues[nJ] + space;
						}
					}
					System.out.println("strPrint  = " + strPrint);
					//out.print(new String(strPrint.getBytes("utf-8"),"gb2312")+"\n");		//��ӡ����
					out.print(strPrint+newLine);		//��ӡ����
				}
			}
			if(out != null)
				out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
     * ���ļ����еĺ���תΪUTF8����Ĵ�,�Ա�����ʱ����ȷ��ʾ�����ļ���.
     * @param s ԭ�ļ���
     * @return ���±������ļ���
     */
    protected String toUtf8String(String s) {
	StringBuffer sb = new StringBuffer();
	for (int i=0;i<s.length();i++) {
	    char c = s.charAt(i);
	    if (c >= 0 && c <= 255) {
		sb.append(c);
	    } else {
		byte[] b;
		try {
			Character character = new Character(c);
		    b = character.toString().getBytes("utf-8");
		} catch (Exception ex) {
		    System.out.println(ex);
		    b = new byte[0];
		}
		for (int j = 0; j < b.length; j++) {
		    int k = b[j];
		    if (k < 0) k += 256;
		    sb.append("%" + Integer.toHexString(k).toUpperCase());
		}
	   }
	}
	return sb.toString();
   }
    

    
    
}
