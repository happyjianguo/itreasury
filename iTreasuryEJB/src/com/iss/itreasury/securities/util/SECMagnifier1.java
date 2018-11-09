/*
 * CLASS   : SECMagnifier
 * FUNCTION: ֤ȯϵͳ�Ŵ���
 * VERSION : 1.0.0
 * DATE    : 2004/02/23
 * AUTHOR  : lgwang
 * HISTORY : 2004/02/23 �½�
 *         :
 */

/**
 * Methods:��Ҫ���ҷŴ��������Ŵ�ǰ��ı��
 * 
 * //--------------------- ͨ�÷Ŵ�-----------------------
 * Magnifier0010: createClientCtrl()					//�ͻ�(ҵ��λ)�Ŵ�,����NAME
 * Magnifier0020: createClientCtrl() 					//�ͻ���ҵ��λ���Ŵ�,����CODE,����NAME
 * 
 * Magnifier0030: createCounterPartCtrl()				//���׶��ַŴ�,��ҵ��λIDԪ��Ӱ��,����NAME,��transactionTypeId���ֲ�ͬ���,ʹ����ͼSEC_VCounterClientMagnifier
 * Magnifier0040: createCounterPartCtrl()				//���׶��ַŴ�,��ҵ��λIDԪ��Ӱ��,����CODE,����NAME,��transactionTypeId���ֲ�ͬ���,ʹ����ͼSEC_VCounterClientMagnifier
 *
 * Magnifier0120: createCounterpartBankCtrl()			//���׶��ֿ����зŴ�,�ܰ��´��ͱ��ֿ���,�ܽ��׶���ID����
 * Magnifier0121: createCounterpartBankCtrl()			//���׶��ֿ����зŴ�,�ܰ��´��ͱ��ֿ���,����ʾĳһ���ཻ�׶��ֵĿ�����,�ܽ��׶���ID����
 * 
 * Magnifier0130: createClientBankCtrl()				//��˾�����зŴ�,����NAME,�����ʻ�CODE��NAME,ʹ����ͼSEC_VClientBankMagnifier
 * 
 * Magnifier0140: createUserCtrl()						//�û��Ŵ�,�հ��´���������,���Թ��˵���ǰ�û�
 * 
 * Magnifier0150: createStockHolderAccountCtrl()		//�ɶ��ʻ��Ŵ�,��ʾ�ʻ�Code,��������
 * Magnifier0151: createStockHolderAccountCtrl()		//�ɶ��ʻ��Ŵ�,��ʾ����,��ҵ��λӰ��
 * Magnifier0160: createStockHolderAccountCtrl()		//�ɶ��ʻ��Ŵ�,��ʾ�ʻ�����
 * 
 * 
 * 
 * Magnifier0170: createExchangeCenterCodeCtrl()		//֤�����Ŵ�,���ش���,��������
 * 
 * Magnifier0180: createSecuritiesCtrl()				//֤ȯ�Ŵ�,�ܱ��ֿ���,����֤ȯ����,�ܽ�������Ӱ��
 * Magnifier0190: createSecuritiesCtrl()				//֤ȯ�Ŵ�,�ܱ��ֿ���,����֤ȯ����,����֤ȯ����,�ܽ�������Ӱ��
 * Magnifier0181: createSecuritiesCtrl()				//֤ȯ�Ŵ�,�ܱ��ֿ���,����֤ȯ����,��typeIdȷ����ʾ��֤ȯ����,������
 * Magnifier0182: createSecuritiesCtrl()				//֤ȯ�Ŵ�,����֤ȯ����,��֤ȯ����IDԪ�ؿ���,��ѯ��
 * Magnifier0183: createSecuritiesCtrl()				//֤ȯ�Ŵ�,�ܱ��ֿ���,����֤ȯ����,����֤ȯ���ƺ�����,�ܽ�������Ӱ��
 * Magnifier0184: createSecuritiesCtrl()				//֤ȯ�Ŵ�,����֤ȯ����,��������,��֤ȯ����IDԪ�ؿ���
 * 
 * Magnifier0185: createSecuritiesCtrl()				//ծȯ�Ŵ�,����֤ȯ����,��������,���Ҹ�ծȯ����������ȡծȯ
 * 
 * Magnifier0200: createAccountCtrl()					//�ʽ��ʻ�(typeId=0) / �����ʻ�(typeId=1)�Ŵ�,��ҵ��λIDԪ��Ӱ��,����Account Code,
 * 										����ʾ�ʽ��ʻ�ʱ,�����ɶ��ʻ�����͹ɶ��ʻ�����,��ʾ�����ʻ�ʱ���������ʻ����������,������ҵ��λ�ͽ��׶��ֵ����ƺ�ID
 * Magnifier0201: createAccountCtrl()					//�ʽ��ʻ�(typeId=0) / �����ʻ�(typeId=1)�Ŵ�,��ҵ��λIDԪ��Ӱ��,����Account Code,
 * 										����ʾ�ʽ��ʻ�ʱ,�����ɶ��ʻ�����͹ɶ��ʻ�����,��ʾ�����ʻ�ʱ���������ʻ����������,������ҵ��λ�ͽ��׶��ֵ����ƺ�ID,�ͽ��׶��ֵĴ���
 *
 * Magnifier0080: createRemarkCtrl()					//��ע�Ŵ�,����DESCRIPTION,��typeId��������,0Ϊȫ��,ʹ����ͼSEC_VRemarkMagnifier
 *
 * Magnifier0081: createSubjectCtrl()					//��Ŀ�Ŵ�,����CODE,����NAME
 *
 * Magnifier0082: createApplyContractCtrl()				//��ͬ�Ŵ�,���ش���,��ҵ������,¼����,��һ��������,״̬����
 * Magnifier0083: createApplyContractCtrl()				//��ͬ�Ŵ�,���ش���,��ҵ������,¼����,��һ��������,״̬����,�������ʲ�����
 * Magnifier0084: createApplyContractCtrl2()			//��ͬ�Ŵ�,���ش���,��ҵ������,¼����,��һ��������,״̬����,���˺�ͬ���ڵ�����
 * Magnifier0085: createApplyContractCtrl3()			//��ͬ�Ŵ�,���ش���,��ҵ������,¼����,��һ��������,״̬���ƣ���ͬ��״̬Ϊ��δִ�С�ִ���С���չ��
 *
 * //------------------------------��ѯ�÷Ŵ�
 * Magnifier0011: createSecuritiesTypeCtrl()			//֤ȯ���ͷŴ�,��������
 * Magnifier0018: createSecuritiesTypeCtrl()			//֤ȯ���ͷŴ�,���ش���,��������
 *
 * Magnifier0012: createBusinessTypeCtrl()				//ҵ�����ͷŴ�,��������,����ҵ������
 * Magnifier0013: createBusinessTypeCtrl()				//ҵ�����ͷŴ�,���ش���,��������,ҵ������
 *  
 * Magnifier0014: createTransactionTypeCtrl()			//�������ͷŴ�,��������,��ҵ��������Լ
 * Magnifier0017: createTransactionTypeCtrl()			//�������ͷŴ�,���ش���,��������,��ҵ��������Լ
 * 
 * Magnifier0015: createBusinessAttributeCtrl()			//�������ʷŴ�
 * Magnifier0016: createBusinessTypeCtrlOne()			//ҵ�����ͷŴ�,��������,��ҵ������IDԪ��Ӱ��								
 *
 *
 * //------------------------------����Ŵ�
 * Magnifier0049: createCounterPartCtrl()				//���׶��ַŴ�,��ѯ,����ר��,����NAME,��ʾ����״̬Ϊ�����Ľ��׶���
 * Magnifier0050: createCounterPartCtrl()				//���׶��ַŴ�,��ѯ,����ר��,����CODE,����NAME,��ʾ����״̬Ϊ�����Ľ��׶���
 * 
 * Magnifier0060: createCounterPartCtrl()				//����Ŵ�1,����ר��,���׶��ַŴ󾵴�����������,����CODE,����NAME,ʹ����ͼSEC_VCounterpartMagnifier_1
 * Magnifier0070: createCounterPartCtrl()				//����Ŵ�2,����ר��,���׶��ַŴ�,����CODE,����NAME,ʹ����ͼSEC_VCounterpartMagnifier_2
 * 
 * Magnifier0100: createApplyFormCtrl()					//������Ŵ�***,�Դ���ѯ�����Ŵ�
 * 
 * Magnifier0110: createDeliveryOrderCtrl()				//����Ŵ�***,�Դ���ѯ�����Ŵ�
 * 
 * Magnifier0090: createRemarkCodeCtrl()				//��ע��ŷŴ�,����ר��,����CODE,����DESCRIPTION,��typeId��������,0Ϊȫ��,ʹ����ͼSEC_VRemarkMagnifier
 * 
 * Magnifier0091: createSettAccountCtrl()				//�����ʻ��Ŵ�,���ı���,��ʾ�����ʻ����еĻ����ʻ�
 * 									�˷Ŵ���ʾ�ĸ��ı���,�ֱ���ʾ�����ʻ����ĸ�����,������Ϊ%accountName%+Ctrl,���ҳ���ý�����Ҫ���������
 */

package com.iss.itreasury.securities.util;


import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.bill.util.BILLMagnifier;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;


public class SECMagnifier1
{
	
	/**Magnifier0010
	 * ҵ��λ�Ŵ�Magnifier001
	 * @param out								jspWriter
	 * @param mainElementName					ҳ��ͻ�������ʾ�ı�������,����ҵ��λ����
	 * @param hiddenElementName					�ͻ�ID�����������,ҵ��λID
	 * @param hiddenElementValue				�ͻ�ID�ĳ�ʼֵ
	 * 
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createClientCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};				//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
	
		String[] strOperators = {"like","like","="};
		String[] strLogicOperators = {"or","and"};
	
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getClientNameByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"�ͻ����","�ͻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-�ͻ��Ŵ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_CLIENT";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0020
		 * ҵ��λ�Ŵ�
		 * @param out								jspWriter
		 * @param mainElementName					ҳ��ͻ�������ʾ�ı�������,ҵ��λ����Ԫ������
		 * @param hiddenElementName					�ͻ�ID�����������,ҵ��λIDԪ������
		 * @param hiddenElementValue				�ͻ�ID�ĳ�ʼֵ
		 * 
		 * @param clientNameElementName				����ҵ��λ����Ԫ������
		 * 
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createClientCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String clientNameElementName,
				
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","="};
			String[] strLogicOperators = {"or","and"};
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getClientCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={clientNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"NAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={NameRef.getClientNameByID(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"�ͻ����","�ͻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-�ͻ��Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "sec_client";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}


	/**Magnifier0030
	 * ���׶��ַŴ�,ͨ�÷Ŵ�
	 * @param out								jspWriter
	 * @param displayElementName				ҳ��ͻ�������ʾ�ı�������,���׶�������
	 * @param idElementName						�ͻ�ID�����������,���׶���ID
	 * @param idElementIniValue					�ͻ�ID�ĳ�ʼֵ,���׶���ID��ʼֵ
	 * 
	 * @param clientIdElementName				ҵ��λID��Ԫ������,Ӱ��Ŵ�
	 * @param transactionTypeId					��������ID,��Ӱ��Ŵ���ʾ�Ľ��׶������͵�ֵ
	 * 											�����Ҫ�ض����͵Ľ��׶���,��ô����ý��׶��ֵĴ���,����:
	 *						 							0:  ��������
	 *													1:	������
	 *						 							2:	����Ӫҵ��
	 *													3:	�����������˾
	 *													4:	���м佻�׶���
	 *													5:	ծȯ������
	 *  
	 * @param typeId	
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String clientIdElementName,
			
			long transactionTypeId,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName,clientIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"COUNTERPARTCODE","CLIENTID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={"",NameRef.getClientIdsByCounterpartId(idElementIniValue)};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
		String[] counterpartTypeFields = MagnifierHelper.getCounterpartTypeFields(transactionTypeId);   //��ý��׶��������ֶ�����
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		/**
		 * ������иý������͵Ľ��׶�������
		 */
		if (counterpartTypeFields != null && counterpartTypeFields.length>0){	//������׶������Ͳ�Ϊ����ʾ�ý��׶���
			strCtrlValues = new String[counterpartTypeFields.length];
			strCtrlFields = new String[counterpartTypeFields.length];

			for(int n=0;n<counterpartTypeFields.length;n++){					//����Լ��ֵ
				strCtrlValues[n] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//���Ͷ�Ӧ�ֶ�
				strCtrlFields[n] = counterpartTypeFields[n];
			}

			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];

			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�������
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�߼��������
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
		}
		else{							//������׶�������Ϊ����ʾȫ������
			throw new SecuritiesException("���׶��ַŴ�,����transactionTypeId����",null);
		}
	
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="COUNTERPARTNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getCounterpartNameByID(idElementIniValue);							//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"COUNTERPARTID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames = null;
		String[] strListTitleDisplayFields = null;
		
		if (counterpartTypeFields.length == 1 && counterpartTypeFields[0].equals("IsBankOfDeposit")){
			strListTitleDisplayNames=new String[]{"����Ӫҵ������","ҵ��λ"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			strListTitleDisplayFields=new String[]{"COUNTERPARTNAME","CLIENTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
		}
		else{
			strListTitleDisplayNames=new String[]{"���׶��ִ���","���׶�������","ҵ��λ"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			strListTitleDisplayFields=new String[]{"COUNTERPARTCODE","COUNTERPARTNAME","CLIENTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
		}
		String strTableName = "SEC_VCounterClientMagnifier";

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-���׶��ַŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
		
		/**
		 * �����ǰ�Ľ��׶�����ծȯ�����̻������м佻�׶���,��ô:
		 */
		if (counterpartTypeFields.length == 1 && 
				(counterpartTypeFields[0].equals("IsInterBankCounterpart") 
						|| counterpartTypeFields[0].equals("IsSecuritiesUnderwriter")
						|| counterpartTypeFields[0].equals("IsFloaters")
						|| counterpartTypeFields[0].equals("IsSecurityCo"))){
			
			strCtrlElementNames=new String[]{displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			strCtrlElementFields=new String[]{"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			strCtrlElementValues=new String[]{""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			strCtrlValues = new String[counterpartTypeFields.length+1];
			strCtrlFields = new String[counterpartTypeFields.length+1];
			
			strCtrlValues[0] = String.valueOf(String.valueOf(SECConstant.SettingStatus.CHECKED));
			strCtrlFields[0] = "STATUSID";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//����Լ��ֵ
				strCtrlValues[n+1] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//���Ͷ�Ӧ�ֶ�
				strCtrlFields[n+1] = counterpartTypeFields[n];
			}

			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];
			
			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�������
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�߼��������
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
			
			strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			strVisibleElementValues=NameRef.getCounterpartNameByID(idElementIniValue);							//��ʾԪ�صĳ�ʼֵ?????
			
			strHiddenElementNames=new String[]{idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			strHiddenElementFields=new String[]{"ID"}; 					//id��Ӧ���ֶ���
			strHiddenElementValues=new String[]{String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
			strListTitleDisplayNames=new String[]{"���׶��ִ���","���׶�������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			strListTitleDisplayFields=new String[]{"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
			strTableName = "SEC_counterPart";
		}
		
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0040
	 * ���׶��ַŴ�,ͨ�÷Ŵ󾵶�
	 * @param out								jspWriter
	 * @param displayElementName				ҳ�潻�׶��ִ�����ʾ�ı�������
	 * @param idElementName						���׶���ID�����������,���׶���ID
	 * @param idElementIniValue					���׶���ID�ĳ�ʼֵ
	 * 
	 * @param clientIdElementName				���׶���IDԪ������,Ӱ��Ŵ󾵴���ֵ
	 * @param transactionTypeId					��������Id,����ȷ��Ҫ�����Ľ��׶�������
	 * 											�����Ҫ�ض����͵Ľ��׶���,��ô����ý��׶��ֵĴ���,����:
	 *						 							0:  ��������
	 *													1:	������
	 *						 							2:	����Ӫҵ��
	 *													3:	�����������˾
	 *													4:	���м佻�׶���
	 *													5:	ծȯ������
	 *  
	 * @param counterpartNameElementName		�����Ľ��׶������� 
	 * 
	 * @param typeId							
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//������ֵ
			String clientIdElementName, 
			
			long transactionTypeId,								//��������Id,����ȷ��Ҫ�����Ľ��׶�������
			
			String counterpartNameElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName,clientIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"COUNTERPARTCODE","CLIENTID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={"",NameRef.getClientIdsByCounterpartId(idElementIniValue)};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
		String[] counterpartTypeFields = MagnifierHelper.getCounterpartTypeFields(transactionTypeId);   //��ý��׶��������ֶ�����
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		
		String[] strOperators = null;
		String[] strLogicOperators = null;
		Log.print("��������ID:"+transactionTypeId);
		
		if (counterpartTypeFields!=null){
			for (int n=0;n<counterpartTypeFields.length;n++){
				Log.print("���׶�������:"+counterpartTypeFields[n]);
			}
		}
		
		
		/**
		 * ������иý������͵Ľ��׶�������
		 */
		if (counterpartTypeFields != null && counterpartTypeFields.length>0){	//������׶������Ͳ�Ϊ����ʾ�ý��׶���
			strCtrlValues = new String[counterpartTypeFields.length];
			strCtrlFields = new String[counterpartTypeFields.length];
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//����Լ��ֵ
				strCtrlValues[n] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//���Ͷ�Ӧ�ֶ�
				strCtrlFields[n] = counterpartTypeFields[n];
			}

			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];

			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�������
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�߼��������
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
		}
		else{							//������׶�������Ϊ����ʾȫ������
			throw new SecuritiesException("���׶��ַŴ�,����transactionTypeId����",null);
		}
		
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="COUNTERPARTCODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"COUNTERPARTID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={counterpartNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"COUNTERPARTNAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue)};
		
		String[] strListTitleDisplayNames = null;
		String[] strListTitleDisplayFields = null;
		
		if (transactionTypeId == 2 || (counterpartTypeFields.length == 1 && counterpartTypeFields[0].equals("IsBankOfDeposit"))){
			strListTitleDisplayNames=new String[]{"����Ӫҵ������","ҵ��λ"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			strListTitleDisplayFields=new String[]{"COUNTERPARTNAME","CLIENTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
		}
		else{
			strListTitleDisplayNames=new String[]{"���׶��ִ���","���׶�������","ҵ��λ"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			strListTitleDisplayFields=new String[]{"COUNTERPARTCODE","COUNTERPARTNAME","CLIENTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
		}
		
		
		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
		
		String strWindowTitle="֤ȯ-���׶��ַŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
		
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VCounterClientMagnifier";
		
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
		
		/**
		 * �����ǰ�Ľ��׶�����ծȯ�����̻������м佻�׶���,��ô:
		 */
		if (counterpartTypeFields.length == 1 && 
				(counterpartTypeFields[0].equals("IsInterBankCounterpart") 
						|| counterpartTypeFields[0].equals("IsSecuritiesUnderwriter")
						|| counterpartTypeFields[0].equals("IsFloaters")
						|| counterpartTypeFields[0].equals("IsSecurityCo"))){
			
			strCtrlElementNames=new String[]{displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			strCtrlElementFields=new String[]{"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			strCtrlElementValues=new String[]{""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			
			strCtrlValues = new String[counterpartTypeFields.length+1];
			strCtrlFields = new String[counterpartTypeFields.length+1];
			
			strCtrlValues[0] = String.valueOf(String.valueOf(SECConstant.SettingStatus.CHECKED));
			strCtrlFields[0] = "STATUSID";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//����Լ��ֵ
				strCtrlValues[n+1] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//���Ͷ�Ӧ�ֶ�
				strCtrlFields[n+1] = counterpartTypeFields[n];
			}
			
			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];
			
			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�������
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//�߼��������
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
			
			strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//��ʾԪ�صĳ�ʼֵ?????
			
			strHiddenElementNames=new String[]{idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			strHiddenElementFields=new String[]{"ID"}; 					//id��Ӧ���ֶ���
			strHiddenElementValues=new String[]{String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
			strOtherElementNames=new String[]{counterpartNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			strOtherElementFields=new String[]{"NAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			strOtherElementValues=new String[]{NameRef.getCounterpartNameByID(idElementIniValue)};
		
			
			strListTitleDisplayNames=new String[]{"���׶��ִ���","���׶�������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			strListTitleDisplayFields=new String[]{"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
			strTableName = "SEC_counterPart";
		}
		
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	
	/**Magnifier0049
		 * ���׶��ַŴ�,���÷Ŵ󾵶�,
		 * @param out								jspWriter
		 * @param displayElementName				ҳ��ͻ�������ʾ�ı�������,���׶�������
		 * @param idElementName						�ͻ�ID�����������,���׶���ID
		 * @param idElementIniValue					�ͻ�ID�ĳ�ʼֵ
		 * 
		 * @param typeId							������
		 * 									0:���н��׶���
		 * 									1:ֻ��ʾȥ������Ӫҵ���Ľ��׶��� 
		 * 									2:ֻ��ʾ����Ӫҵ��
		 * 									3:ֻ��ʾ���м佻�׶���
		 * 									4:ֻ��ʾ�������˾
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createCounterPartCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
				
			if (typeId >4 || typeId <0){
				throw new SecuritiesException("���׶��ַŴ�,����typeId����",null);
			}
			
			String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		

			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			
			String[] strOperators = null;
			String[] strLogicOperators = null;
			
			if (typeId == 0){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
				strCtrlFields = new String[]{"STATUSID"};
				
				strOperators = new String[]{"like","like","="};
				strLogicOperators = new String[]{"or","and"};
			}
			else if (typeId == 1){		//ֻ��ʾȥ������Ӫҵ���Ľ��׶���
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"0","null"};
				strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT","ISBANKOFDEPOSIT"};
				
				strOperators = new String[]{"like","like","=","=","is"};
				strLogicOperators = new String[]{"or","and","and","or"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
				strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT"};
				
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};
			}
			else if (typeId == 3){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
				strCtrlFields = new String[]{"STATUSID","ISINTERBANKCOUNTERPART"};
				
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};
			}
			else if (typeId == 4){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
				strCtrlFields = new String[]{"STATUSID","ISFUNDMANAGEMENTCO"};
				
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};
			}
		
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getCounterpartNameByID(idElementIniValue);							//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"���׶��ִ���","���׶�������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

					String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
					String strWindowTitle="";
					switch((int)typeId){
					case 0:						
					case 1:
						strWindowTitle="֤ȯ-���׶��ַŴ�";
						break;
					case 2:
						strWindowTitle="֤ȯ-����Ӫҵ���Ŵ�";
						strListTitleDisplayNames[0]="����Ӫҵ������";
						strListTitleDisplayNames[1]="����Ӫҵ������";
						break;
					case 3:
						strWindowTitle="֤ȯ-���м佻�׶��ַŴ�";
						strListTitleDisplayNames[0]="���м佻�׶��ִ���";
						strListTitleDisplayNames[1]="���м佻�׶�������";
						break;
					case 4:
						strWindowTitle="֤ȯ-�������˾�Ŵ�";
						strListTitleDisplayNames[0]="�������˾����";
						strListTitleDisplayNames[1]="�������˾����";
						break;
					}	
					//String strWindowTitle="֤ȯ-���׶��ַŴ�";					//�Ŵ󾵵������ڱ���
					int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
					int intSQL=4;												//���ݲ�ѯSql 
					String strTableName = "sec_counterpart";
	
					boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}


/**Magnifier0050
	 * ���׶��ַŴ�,���÷Ŵ�һ
	 * @param out								jspWriter
	 * @param displayElementName				ҳ��ͻ�������ʾ�ı�������,���׶��ִ���
	 * @param idElementName						�ͻ�ID�����������,���׶���ID
	 * @param idElementIniValue					�ͻ�ID�ĳ�ʼֵ
	 * 
	 * @param counterpartNameElementName					���׶�������Ԫ������
	 *
	 * @param typeId							������
	 * 									0:���н��׶���
	 * 									1:ֻ��ʾȥ������Ӫҵ���Ľ��׶��� 
	 * 									2:ֻ��ʾ����Ӫҵ��
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
		
			String counterpartNameElementName,			

			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId >2 || typeId <0){
			throw new SecuritiesException("���׶��ַŴ�,����typeId����",null);
		}
		String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		

		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		if (typeId == 0){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
			
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else if (typeId == 1){		//ֻ��ʾȥ������Ӫҵ���Ľ��׶���
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1","null"};
			strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT","ISBANKOFDEPOSIT"};
			
			strOperators = new String[]{"like","like","=","<>","is"};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
		else if (typeId == 2){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
			strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT"};
			
			strOperators = new String[]{"like","like","=","="};
			strLogicOperators = new String[]{"or","and","and"};
		}

		
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={counterpartNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"NAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue)};
		
		String[] strListTitleDisplayNames={"���׶��ִ���","���׶�������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

				String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
				String strWindowTitle="֤ȯ-���׶��ַŴ�";					//�Ŵ󾵵������ڱ���
				int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
				int intSQL=4;												//���ݲ�ѯSql 
				String strTableName = "sec_counterpart";
	
				boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}



	/**Magnifier0060
	 * ���׶��ַŴ�	ҵ������,�ʽ���ҵ��ר��
	 * @param out								jspWriter
	 * @param displayElementName				ҳ��ͻ�������ʾ�ı�������
	 * @param idElementName						�ͻ�ID�����������
	 * @param idElementIniValue					�ͻ�ID�ĳ�ʼֵ
	 * 
	 * @param String transactionTypeElementName Ӱ��Ŵ󾵵Ľ�������Ԫ��
	 * 
	 * @param counterPartNameElementName		�����Ľ��׶������� 
	 * 
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//Ӱ��Ŵ󾵵Ľ�������Ԫ��
			long transactionType,
			
			//������ֵ
			String counterPartNameElementName, 
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE"};					//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};										//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

		String[] strCtrlValues = {String.valueOf(SECConstant.CounterpartStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};

		String[] strOperators = {"like","like","="};
		String[] strLogicOperators = {"or","and"};
	
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"id"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={counterPartNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"NAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue)};
			
		String[] strListTitleDisplayNames={"���׶��ִ���","���׶�������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������

		String strWindowTitle="֤ȯ-���׶��ַŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "";
		if (transactionType == SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION){
			strTableName = "SEC_VCounterpartMagnifier_2";
		}
		else if (transactionType == SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION){
			strTableName = "SEC_VCounterpartMagnifier_3";
		}
		 
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ

		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	
	/**Magnifier0070
	 * ���׶��ַŴ�		ҵ������,�ʽ���ҵ��ר��
	 * @param out								jspWriter
	 * @param displayElementName				ҳ��ͻ�������ʾ�ı�������
	 * @param idElementName						�ͻ�ID�����������
	 * @param idElementIniValue					�ͻ�ID�ĳ�ʼֵ
	 * 
	 * @param String transactionTypeElementName Ӱ��Ŵ󾵵Ľ�������Ԫ��
	 * 
	 * @param counterPartNameElementName		�����Ľ��׶������� 
	 * @param String transactionStartDateElementName,
	 * @param String transactionEndDateElementName,
	 * @param String pledgeSecuritiesAmountElementName,
	 * @param String termElementName,
	 * 
	 * 
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//Ӱ��Ŵ󾵵Ľ�������Ԫ��
			String transactionTypeElementName,
			
			//������ֵ
			String counterPartNameElementName,
			String transactionStartDateElementName,
			String transactionEndDateElementName,
			String pledgeSecuritiesAmountElementName,
			String termElementName,
			String sumpledgesecuritiesamountElementName,
			String sumpledgesecuritiesamountElementName1,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	displayElementName,
										transactionTypeElementName
										};//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE","transactionTypeId"};					//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={"",""};										//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

		String[] strCtrlValues = {String.valueOf(SECConstant.CounterpartStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};

		String[] strOperators = {"like","like","=","="};
		String[] strLogicOperators = {"or","and","and"};
	
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};			//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"id"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={	counterPartNameElementName,
										transactionStartDateElementName,
										transactionEndDateElementName,
										pledgeSecuritiesAmountElementName,
										termElementName,
										sumpledgesecuritiesamountElementName,
										sumpledgesecuritiesamountElementName1};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"NAME",
										"transactionStartDate",
										"transactionEndDate",
										"pledgeSecuritiesAmount",
										"term",
										"sumpledgesecuritiesamount1",
										"sumpledgesecuritiesamount2"};									//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue),
										NameRef.getTransactionStartDateByCounterpartID(idElementIniValue),
										NameRef.getTransactionEndDateByCounterpartID(idElementIniValue),
										NameRef.getPledgeSecuritiesAmountByCounterpartID(idElementIniValue),
										NameRef.getTermByCounterpartID(idElementIniValue),
										"",
										""};
			
		String[] strListTitleDisplayNames={"���׶��ִ���","���׶�������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","NAME"}; 				//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������

		String strWindowTitle="֤ȯ-���׶��ַŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VCounterpartMagnifier_1";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0080
	 * ժҪ�Ŵ�
	 * @param out								jspWriter
	 * @param displayElementName				��ʾԪ������,����ժҪ����
	 * @param displayElementIniValue			��ʾԪ�س�ʼֵ
	 * 
	 * @param typeId
	 * ժҪ����,�Խ���������Ϊ����,���뽻��������,��ù���ժҪ�ͱ�����ժҪ,"0"Ϊ���ȫ��
	 * 
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createRemarkCtrl(
			JspWriter out,
			String displayElementName,
			String displayElementIniValue,
			
			long typeId,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
	
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		if (typeId == 0){				//��ʾȫ��
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
	
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else{							//ֻ��ʾȫ��ժҪ��������͵�ժҪ
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(typeId),"0"};
			strCtrlFields = new String[]{"STATUSID","BUSINESSTYPEID","BUSINESSTYPEID"};
	
			strOperators = new String[]{"like","like","=","=","="};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
		
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="DESCRIPTION";				//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=displayElementIniValue;			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="textarea";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={"remarkID"};							//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 						//id��Ӧ���ֶ���
		String[] strHiddenElementValues={""};							//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};	
		
		String[] strListTitleDisplayNames={"��������","����������","ҵ������"};	 //�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","DESCRIPTION","BUSINESSTYPENAME"};//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-������Ŵ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VRemarkMagnifier";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0090
	 * ��ע��ŷŴ�
	 * @param out								jspWriter
	 * @param displayElementName				ҳ�汸ע�����ʾ�ı�������
	 * @param displayElementName				��ע���ID�����������
	 * @param idElementIniValue					��ע���ID�ĳ�ʼֵ
	 * 
	 * @param remarkDescElementName				�������ı�ע����Ԫ������
	 * 
	 * @param typeId
	 * ժҪ����,�Խ���������Ϊ����,���뽻��������,��ù���ժҪ�ͱ�����ժҪ,"0"Ϊ���ȫ��
	 * 
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createRemarkCodeCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String remarkDescElementName,
			
			long typeId,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};				//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
	
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		if (typeId == 0){				//��ʾȫ��
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
	
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else{							//ֻ��ʾȫ��ժҪ��������͵�ժҪ
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(typeId),"0"};
			strCtrlFields = new String[]{"STATUSID","BUSINESSTYPEID","BUSINESSTYPEID"};
	
			strOperators = new String[]{"like","like","=","=","="};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
		

		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getRemarkCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????

		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����

		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ

		String[] strOtherElementNames={remarkDescElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"DESCRIPTION"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={NameRef.getRemarkDescByID(idElementIniValue)};

		String[] strListTitleDisplayNames={"��������","����������"};	 //�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","DESCRIPTION"};//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������

		String strWindowTitle="֤ȯ-������Ŵ�";				//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��

		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VRemarkMagnifier";

		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ

		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,

						strCtrlValues,
						strCtrlFields,

						strOperators,
						strLogicOperators,

						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,

						strVisibleElementType,
						strVisibleElementProperty,

						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,

						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,

						strListTitleDisplayNames,
						strListTitleDisplayFields,

						strnextFocusElementNames,

						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}


	
	/**Magnifier0100
	 * ������Ŵ�
	 * @param out
	 * @param displayElementName						//��ʾCODEԪ�ص�����
	 * @param idElementName						//idԪ������
	 * @param idElementIniValue						//id�ĳ�ʼֵ
	 * -----------------------------����Ԫ��
	 * @param ��װ��Ŵ�
	 * -----------------------------
	 * long currencyId,								//����
	 * long officeId,								//���´�
	 * -----------------------------
	 * @param visibleElementProperty				//��ʾCODEԪ�ص�����
	 * @param nextFocusElementNames					//��һ������λ��
	 * @param showOnly								//�Ƿ�ֻ����ʾ
	 * @throws Exception
	 */
	public static void createApplyFormCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			long currencyId,
			long officeId,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	"secTransactionTypeId",
										"secApplyFormInputDateFrom",
										"secApplyFormInputDateTo",
										"secTransactionDateFrom",
										"secTransactionDateTo",
										"secSystemTransactionCode",
										"secClientId",
										"secCounterpartId",
										"secPledgeSecuritiesAmountFrom",
										"secPledgeSecuritiesAmountTo",
										"secAmountFrom",
										"secAmountTo",
										"secPriceFrom",
										"secPriceTo",
										"secQuantityFrom",
										"secQuantityTo",
										"secSecuritiesId",
										"secApplyFormStatusId",
										"secStockId"
										};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={	"TRANSACTIONTYPEID",
										"_INPUTDATE",
										"_INPUTDATE",
										"_TRANSACTIONDATE",
										"_TRANSACTIONDATE",
										"SYSTEMTRANSACTIONCODE",
										"CLIENTID",
										"COUNTERPARTID",			//10
										"PLEDGESECURITIESAMOUNT",
										"PLEDGESECURITIESAMOUNT",
										"AMOUNT",
										"AMOUNT",
										"PRICE",					//15
										"PRICE",
										"QUANTITY",
										"QUANTITY",
										"SECURITIESID",				//20
										"STATUSID",
										"STOCKID"
										};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={	"",
										"",
										"",
										"",
										"",							//5
										"",
										"",
										"",
										"",
										"",							//10
										"",
										"",
										"",
										"",
										"",							//15
										"",
										"",
										"",
										""
										};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

		String[] strCtrlValues = {String.valueOf(currencyId),String.valueOf(officeId)};
		String[] strCtrlFields = {"CURRENCYID","OFFICEID"};
		
		/**
		 * ������
		 */
		String[] strOperators = {		"like",
										"=",
										">=",
										"<=",						//5
										">=",
										"<=",
										"=",
										"=",						//10
										"=",
										">=",
										"<=",
										">=",
										"<=",						//15
										">=",
										"<=",
										">=",
										"<=",
										"=",
										"in",
										"=",
										"=",
										"="};						//24
		
		/**
		 * ������
		 */
		String[] strLogicOperators = {};
	
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getApplyFormCodeByID(idElementIniValue);			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"id"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"������ID","���������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"id","CODE"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-������Ŵ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "Sec_ApplyForm";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	
	/**Magnifier0110
	 * ����Ŵ�
	 * @param out
	 * 
	 * @param displayElementName			//��ʾԪ������,������
	 * @param idElementName					//idԪ������,���ID
	 * @param idElementIniValue				//�Ŵ󾵳�ʼֵ
	 * 
	 * @param currencyId					//����ID
	 * @param officeId						//���´�ID
	 * 
	 * @param typeId						//
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createDeliveryOrderCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			long currencyId,
			long officeId,
			
			long typeId,			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	"transactionTypeId",
										"transactionDateFrom",
										"transactionDateTo",
										"systemTransactionCode",
										"clientId",							//5
										"counterpartId",
										"amountFrom",
										"amountTo",
										"statusId",
										"accountId",
										"securitiesId",
										"priceFrom",
										"priceTo",
										"transactionQuantityFrom",			//15
										"transactionQuantityTo",
										"netPriceFrom",
										"netPriceTo",						//18
										"quantityFrom",
										"quantityTo"
										};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={	"TRANSACTIONTYPEID",
										"_TRANSACTIONDATE",
										"_TRANSACTIONDATE",
										"SYSTEMTRANSACTIONCODE",
										"CLIENTID",							//5
										"COUNTERPARTID",
										"AMOUNT",
										"AMOUNT",
										"STATUSID",
										"ACCOUNTID",
										"SECURITIESID",
										"PRICE",
										"PRICE",
										"TRANSACTIONQUANTITY",				//15
										"TRANSACTIONQUANTITY",
										"NETPRICE",
										"NETPRICE",							//18
										"QUANTITY",
										"QUANTITY"
										};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={	"",
										"",
										"",
										"",
										"",									//5
										"",
										"",
										"",
										"",									//10
										"",
										"",
										"",
										"",
										"",									//15
										"",
										"",
										"",									//18
										"",
										""
										};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

		String[] strCtrlValues = {String.valueOf(currencyId),String.valueOf(officeId)};
		String[] strCtrlFields = {"CURRENCYID","OFFICEID"};
		
		/**
		 * ������
		 */
		String[] strOperators = {		"like",
										"=",
										">=",
										"<=",
										"=",								//5
										"=",
										"=",
										">=",
										"<=",
										"=",
										"=",
										"=",
										">=",
										"<=",								//15
										">=",
										"<=",
										">=",
										"<=",									//19
										">=",
										"<=",
										"=",
										"="
									};
		
		/**
		 * ������
		 */
		String[] strLogicOperators = {};
	
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getDeliveryOrderCodeByID(idElementIniValue);			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"�������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE"}; 	//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-����Ŵ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_DeliveryOrder";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	/**Magnifier0120
	 * ���׶��ֿ����зŴ�
	 * @param out
	 * 
	 * @param displayElementName					//��ʾԪ������,���׶��ֿ���������
	 * @param idElementName							//IDԪ������,���׶��ֿ�����ID
	 * @param idElementIniValue						//���׶��ֿ����г�ʼֵ
	 * ---------����ֵ
	 * @param officeId								//���´�Id
	 * @param currencyId							//����Id
	 * 
	 * ---------����Ԫ��
	 * @param counterpartIdElementName				//���׶���Ԫ������
	 * 
	 * ---------Ӱ��Ԫ��
	 * @param accountCode							//�����ʻ�����
	 * @param accountName							//�����ʻ�����
	 * 
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createCounterpartBankCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//���Ʋ���
			long officeId,
			long currencyId,
			String counterpartIdElementName,
			//���Ʋ���
			
			//���ò���
			String accountCode,
			String accountName,
			//���ò���
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	displayElementName,
										counterpartIdElementName,
										};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"id",
										"CounterpartID",
										};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={	"",
										String.valueOf(idElementIniValue)};						//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��

		String[] strCtrlValues = {String.valueOf(officeId),String.valueOf(currencyId),String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"OFFICEID","CURRENCYID","STATUSID"};
		
		/**
		 * ������
		 */
		String[] strOperators = {		"like",
										"like",
										"=",
										"=",
										"=",
										"="};
		/**
		 * ������
		 */
		String[] strLogicOperators = {	"or",
										"and",
										"and",
										"and",
										"and"};
	
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="BANKNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getCounterpartBankNameByBankID(idElementIniValue);			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
		String[] strOtherElementNames={accountCode,accountName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"BANKACCOUNTCODE","BANKACCOUNTNAME"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={
										NameRef.getCounterpartAccountCodeByBankID(idElementIniValue),
										NameRef.getCounterpartAccountNameByBankID(idElementIniValue)};
		
		String[] strListTitleDisplayNames={"���׶��ֿ���������","�����ʻ�����"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"BANKNAME","BANKACCOUNTNAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
		
		String strWindowTitle="֤ȯ-���׶��ֿ����зŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_CounterpartBankAccount";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	
	
	/**Magnifier0121
	 * ���׶��ֿ����зŴ�,������ʾָ�����׶������͵Ŀ�����
	 * @param out
	 * 
	 * @param displayElementName					//��ʾԪ������,���׶��ֿ���������
	 * @param idElementName							//IDԪ������,���׶��ֿ�����ID
	 * @param idElementIniValue						//���׶��ֿ����г�ʼֵ
	 * ---------����ֵ
	 * @param officeId								//���´�Id
	 * @param currencyId							//����Id
	 * @param counterpartTypeId						//ָ���Ľ��׶�������ID
	 * ---------����Ԫ��
	 * @param counterpartIdElementName				//���׶���Ԫ������
	 * 
	 * ---------Ӱ��Ԫ��
	 * @param accountCode							//�����ʻ�����
	 * @param accountName							//�����ʻ�����
	 * 
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createCounterpartBankCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//���Ʋ���
			long officeId,
			long currencyId,
			
			long counterpartTypeId,
			
			String counterpartIdElementName,
			//���Ʋ���
			
			//���ò���
			String accountCode,
			String accountName,
			//���ò���
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	displayElementName,
										counterpartIdElementName,
										};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"id",
										"CounterpartID",
										};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={	"",
										String.valueOf(idElementIniValue)};						//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
		
		/**
		 * ������
		 */
		String[] strOperators = new String[]{	"like",
										"like",
										"=",
										"=",
										"=",
										"="};
		/**
		 * ������
		 */
		String[] strLogicOperators = new String[]{"or",
										"and",
										"and",
										"and",
										"and"};
		String[] strCtrlValues = null; 
		String[] strCtrlFields = null;
		
		boolean isNotAll = false;
		if (counterpartTypeId == 0){
			strCtrlValues = new String[]{String.valueOf(officeId),String.valueOf(currencyId),String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID"};
		}
		else if (counterpartTypeId == SECConstant.CounterpartObjectType.INTER_BANK_COUNTERPART){//���м佻�׶���
			strCtrlValues = new String[]{	String.valueOf(officeId),
										String.valueOf(currencyId),
										String.valueOf(SECConstant.SettingStatus.CHECKED),
										String.valueOf(SECConstant.TRUE)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID","ISINTERBANKCOUNTERPART"};
			isNotAll = true;
		}
		else if (counterpartTypeId == SECConstant.CounterpartObjectType.FUND_MANAGEMENT_CO){	//�����������˾
			strCtrlValues = new String[]{	String.valueOf(officeId),
										String.valueOf(currencyId),
										String.valueOf(SECConstant.SettingStatus.CHECKED),
										String.valueOf(SECConstant.TRUE)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID","ISFUNDMANAGEMENTCO"};
			isNotAll = true;
		}
		else if (counterpartTypeId == SECConstant.CounterpartObjectType.SECURITIES_UNDERWRITER){//ծȯ������
			strCtrlValues = new String[]{	String.valueOf(officeId),
										String.valueOf(currencyId),
										String.valueOf(SECConstant.SettingStatus.CHECKED),
										String.valueOf(SECConstant.TRUE)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID","ISSECURITIESUNDERWRITER"};
			isNotAll = true;
		}
		else{
			throw new SecuritiesException("�˽��׶������ͽ��׶��ֿ����зŴ󾵻�δ֧��",null);
		}
		
		if (isNotAll){
			strOperators = new String[]{	"like",
					"like",
					"=",
					"=",
					"=",
					"=",
					"="};

			strLogicOperators = new String[]{"or",
					"and",
					"and",
					"and",
					"and",
					"and"};
		}
		
		
		
	
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="BANKNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getCounterpartBankNameByBankID(idElementIniValue);			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
		String[] strOtherElementNames={accountCode,accountName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"BANKACCOUNTCODE","BANKACCOUNTNAME"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={
										NameRef.getCounterpartAccountCodeByBankID(idElementIniValue),
										NameRef.getCounterpartAccountNameByBankID(idElementIniValue)};
		
		String[] strListTitleDisplayNames={"���׶��ֿ���������","�����ʻ�����"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"BANKNAME","BANKACCOUNTNAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
		
		String strWindowTitle="֤ȯ-���׶��ֿ����зŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VCounterAccountMagnifier";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	
	

	/**Magnifier0130
	 * ��˾�����зŴ�
	 * @param out
	 * 
	 * @param displayElementName					//��ʾԪ������,��˾����������
	 * @param idElementName							//IDԪ������,��˾������ID
	 * @param idElementIniValue						//��˾�����г�ʼֵ
	 * 
	 * ---------����ֵ
	 * @param officeId								//���´�
	 * @param currencyId							//����
	 * 
	 * 
	 * ---------Ӱ��Ԫ��
	 * @param accountCode							//�����ʻ�����
	 * @param accountName							//�����ʻ�����
	 * 
	 * @param visibleElementProperty				//��ʾԪ�صĸ�������
	 * @param nextFocusElementNames					//��һ������Ԫ������
	 * @param showOnly								//�Ƿ�ֻ����ʾ
	 * @throws Exception
	 */
	public static void createClientBankCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//���Ʋ���
			long officeId,
			long currencyId,
			//���Ʋ���
			
			//���ò���
			String accountCode,						//�����д���
			String accountName,						//����������
			//���ò���
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};		//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"SCODE"};		//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={	""};					//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��

		String[] strCtrlValues = {String.valueOf(officeId),String.valueOf(currencyId)};
		String[] strCtrlFields = {"NOFFICEID","NCURRENCYID"};
		
		/**
		 * ������
		 */
		String[] strOperators = {		"like",
										"like",
										"=",
										"="};
		/**
		 * ������
		 */
		String[] strLogicOperators = {	"or",
										"and",
										"and"};
	
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="SNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getClientBankNameByBankID(idElementIniValue);			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
		String[] strOtherElementNames={accountCode,accountName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"SBANKACCOUNTCODE","SENTERPRISENAME"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={
										NameRef.getClientAccountCodeByBankID(idElementIniValue),
										NameRef.getClientAccountNameByBankID(idElementIniValue)
										};
		
		String[] strListTitleDisplayNames={"ҵ��λ�����д���","ҵ��λ����������","�ʻ�����"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"SCODE","SNAME","SBANKACCOUNTCODE"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
		
		String strWindowTitle="֤ȯ-ҵ��λ�����зŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "sett_branch";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	
	
	/**Magnifier0140
	 * �û��Ŵ�
	 * @param out
	 * 
	 * @param displayElementName
	 * @param idElementName
	 * @param idElementIniValue
	 * 
	 * ---------����ֵ
	 * @param officeId								//���´�
	 * @param currencyId							//����
	 * @param exceptUserId							//ȥ�����û�����
	 * 
	 * 
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createUserCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//����ֵ
			long officeId,						//���´�ID
			long currencyId,					//����
			String exceptUserId,				//�ų��û�ID
			//����ֵ
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��

		String[] strCtrlValues = {String.valueOf(officeId),String.valueOf(currencyId),exceptUserId};
		String[] strCtrlFields = {"nOfficeId","nCurrencyId","id"};
		
		/**
		 * ������
		 */
		String[] strOperators = {"like","=","=","<>"};
		/**
		 * �߼�������
		 */
		String[] strLogicOperators = {"and","and","and",};
	
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="SNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getUserNameCodeByID(idElementIniValue);			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"id"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
		String[] strOtherElementNames={};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"�û�����"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"SNAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
		
		String strWindowTitle="֤ȯ-�û��Ŵ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "userInfo";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	


	/**Magnifier0150
		 * �ɶ��ʻ��Ŵ�
		 * @param out								jspWriter
		 * @param mainElementName					ҳ��ͻ�������ʾ�ı�������
		 * @param hiddenElementName					�ͻ�ID�����������
		 * @param hiddenElementValue				�ͻ�ID�ĳ�ʼֵ
		 * 
		 * @param accountNameElementName			�ɶ��ʻ�����Ԫ������
		 * 
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createStockHolderAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String clientIdElementName,
				String clientNameElementName,
				
				String accountNameElementName,
				
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName,clientIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE","CLIENTID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={"",""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="code";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getStockHolderAccountCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		
			String[] strOtherElementNames={accountNameElementName,clientIdElementName,clientNameElementName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"NAME","CLIENTID","CLIENTNAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={NameRef.getStockHolderAccountNameByID(idElementIniValue),"",""};
		
			String[] strListTitleDisplayNames={"�ɶ��ʻ����","�ɶ��ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-�ɶ��ʻ���Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_VStockHolderMagnifier";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}

	/**Magnifier0151
		 * �ɶ��ʻ��Ŵ�,��ҵ��λIDԪ������Ӱ��
		 * @param out								jspWriter
		 * @param mainElementName					ҳ��ͻ�������ʾ�ı�������
		 * @param hiddenElementName					�ͻ�ID�����������
		 * @param hiddenElementValue				�ͻ�ID�ĳ�ʼֵ
		 * 
		 * @param clientIdElementName				Ӱ��Ŵ󾵵�ҵ��λIDԪ������
		 * @param clientNameElementName				�����ص�ҵ��λ����
		 * 
		 * @param typeId							������
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createStockHolderAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String clientIdElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName,clientIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE","CLIENTID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={"",""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getStockHolderAccountNameByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"�ɶ��ʻ����","�ɶ��ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-�ɶ��ʻ���Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_VStockHolderMagnifier";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}

		
	/**Magnifier0160
		 * �ɶ��ʻ���Ϣ
		 * @param out								jspWriter
		 * @param mainElementName					ҳ��ͻ�������ʾ�ı�������,���عɶ��ʻ�����
		 * @param hiddenElementName					�ͻ�ID�����������
		 * @param hiddenElementValue				�ͻ�ID�ĳ�ʼֵ
		 * 
		 * 
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createStockHolderAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
			
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","="};
			String[] strLogicOperators = {"or","and"};
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getStockHolderAccountNameByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"�ɶ��ʻ����","�ɶ��ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-�ɶ��ʻ���Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_StockHolderAccount";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}
		
		
	/**Magnifier0170
		 * ֤������Ϣ
		 * @param out								jspWriter
		 * @param mainElementName					ҳ��ͻ�������ʾ�ı�������
		 * @param hiddenElementName					�ͻ�ID�����������
		 * @param hiddenElementValue				�ͻ�ID�ĳ�ʼֵ
		 * 
		 * @param exchangeCenterElementName			�ɶ��ʻ�����Ԫ������
		 * 
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createExchangeCenterCodeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String exchangeCenterElementName,
				
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","="};
			String[] strLogicOperators = {"or","and"};
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getExchangeCenterCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={exchangeCenterElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"NAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={NameRef.getExchangeCenterNameByID(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"֤�������","֤��������"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-֤������Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_ExchangeCenter";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}		

		
	/**Magnifier0180
		 * ֤ȯ��Ϣ
		 * @param out								jspWriter
		 * @param displayElementName				��ʾ�ı������� , ֤ȯ����
		 * @param idElementName						����������� , ֤ȯID
		 * @param idElementIniValue					ID�ĳ�ʼֵ , ֤ȯID
		 * 
		 * @param currencyId						����
		 * @param transactionTypeId					�������ʹ���
		 * 
		 * @param typeId							������,ֻ����ծת��ҳ�� 0:��תծ���� 1:ת�ɹ�Ʊ����
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,					//����
				long transactionTypeId,				//��������
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (!(typeId == 0 || typeId == 1)){
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
			}
			
			String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={MagnifierHelper.getSecuritiesCodeField(transactionTypeId)};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ


			String securitiesTypeIds = MagnifierHelper.getSecuritiesTypeIds(transactionTypeId,currencyId,typeId);										//ƴ�����е����ʹ�
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null; 
			String[] strLogicOperators = null;

			if (securitiesTypeIds!=null && securitiesTypeIds.length()>0){						//��ȷ��������
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),securitiesTypeIds};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","like","=","=","in"};
				strLogicOperators = new String[]{"or","and","and","and"};
			}
			else{												//�������
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
				/*strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};*/
			}
			
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="SHORTNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getSecuritiesNameByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"һ������","��������","֤ȯ���"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-֤ȯ��Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=2;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}

		/**Magnifier0183  ֤ȯ�Ŵ�,�ܱ��ֿ���,����֤ȯ����,����֤ȯ���ƺ�����,�ܽ�������Ӱ��
		 * ֤ȯ��Ϣ
		 * @param out								jspWriter
		 * @param displayElementName				��ʾ�ı������� , ֤ȯ����
		 * @param idElementName						����������� , ֤ȯID
		 * @param idElementIniValue					ID�ĳ�ʼֵ , ֤ȯID
		 * 
		 * @param currencyId						����
		 * @param transactionTypeId					�������ʹ���
		 * 
		 * @param securitiesNameElementName			����֤ȯ����Ԫ������
		 * @param termElementName					��������Ԫ������
		 * 
		 * @param typeId							������,ֻ����ծת��ҳ�� 0:��תծ���� 1:ת�ɹ�Ʊ����
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,					//����
				long transactionTypeId,				//��������
				
				String securitiesNameElementName,
				String termElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (!(typeId == 0 || typeId == 1)){
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
			}
			
			String[] strCtrlElementNames={};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ


			String securitiesTypeIds = MagnifierHelper.getSecuritiesTypeIds(transactionTypeId,currencyId,typeId);										//ƴ�����е����ʹ�
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null; 
			String[] strLogicOperators = null;

			if (securitiesTypeIds!=null && securitiesTypeIds.length()>0){						//��ȷ��������
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),securitiesTypeIds};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","=","=","in"};
				strLogicOperators = new String[]{"and","and","and"};
			}
			else{												//�������
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
				/*strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};*/
			}
			
			String field = MagnifierHelper.getSecuritiesCodeField(transactionTypeId);
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields= field;						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String iniCode = "";
			if (field.equalsIgnoreCase("securitiesCode1")){
				iniCode = NameRef.getSecuritiesCodeByID(idElementIniValue);
			}
			else{
				iniCode = NameRef.getSecuritiesCode2ByID(idElementIniValue);
			}
			String strVisibleElementValues=iniCode;//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={securitiesNameElementName,termElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"SHORTNAME","TERM"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue),NameRef.getPledgeTermBySecuritiesId(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"һ������","��������","֤ȯ���"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-֤ȯ��Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=2;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}	
		
		
	/**Magnifier0181
		 * ֤ȯ��Ϣ,����֤ȯ����,������
		 * @param out								jspWriter
		 * @param displayElementName				��ʾ�ı������� , ֤ȯ����
		 * @param idElementName						����������� , ֤ȯID
		 * @param idElementIniValue					ID�ĳ�ʼֵ , ֤ȯID
		 * 
		 * @param currencyId						����
		 * 
		 * @param typeId							������,���볣���е�����֤ȯ���͵�ID�������֤ȯ
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,					//����
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			
			String[] strCtrlElementNames={};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null;
			String[] strLogicOperators = null;
			
			if (typeId == 0){				//��ȫ��
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","=","="};
				strLogicOperators = new String[]{"and","and"};
			}
			else{							//�鵥��
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(typeId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","=","=","="};
				strLogicOperators = new String[]{"and","and","and"};
			}
			
			
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="SHORTNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getSecuritiesNameByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"һ������","��������","֤ȯ���"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-֤ȯ��Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=2;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}		

		/**Magnifier0182
		 * ֤ȯ��Ϣ,��֤ȯ����IDԪ��Ӱ��
		 * @param out								jspWriter
		 * @param displayElementName				��ʾ�ı������� , ֤ȯ����
		 * @param idElementName						����������� , ֤ȯID
		 * @param idElementIniValue					ID�ĳ�ʼֵ , ֤ȯID
		 * 
		 * @param securitiesTypeIdElementName		֤ȯ����Ԫ������
		 * 
		 * @param typeId							������
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String securitiesTypeIdElementName,	//֤ȯ����Ԫ������
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (!(typeId == 0)){
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
			}
			
			String[] strCtrlElementNames={securitiesTypeIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"TYPEID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={NameRef.getSecuritiesTypeIDBySecuritiesID(idElementIniValue)};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};

			String[] strOperators = {"like","in","="}; 
			String[] strLogicOperators = {"and","and"};
		
			
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="SHORTNAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getSecuritiesNameByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
			
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"һ������","��������","֤ȯ���"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-֤ȯ��Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=2;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_Securities";
			
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}		
		
		/**Magnifier0184
		 * ֤ȯ��Ϣ,��֤ȯ����IDԪ��Ӱ��
		 * @param out								jspWriter
		 * @param displayElementName				��ʾ�ı������� , ֤ȯ����
		 * @param idElementName						����������� , ֤ȯID
		 * @param idElementIniValue					ID�ĳ�ʼֵ , ֤ȯID
		 * 
		 * @param securitiesTypeIdElementName		֤ȯ����Ԫ������
		 * @param securitiesNameElementName			֤ȯ����Ԫ������
		 * 
		 * @param typeId							������,���� 1,����һ������,2,���ض�������,3,������������
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String securitiesTypeIdElementName,	//֤ȯ����Ԫ������
				
				String securitiesNameElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (typeId<0 || typeId>3){
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
			}
			
			String[] strCtrlElementNames={securitiesTypeIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"TYPEID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={NameRef.getSecuritiesTypeIDBySecuritiesID(idElementIniValue)};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
			
			String[] strOperators = {"like","=","="}; 
			String[] strLogicOperators = {"and","and"};
			
			String securitieCode = "SECURITIESCODE" + typeId;
			String securitieIniCode = NameRef.getSecuritiesCodeByID(idElementIniValue,securitieCode);
			
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields=securitieCode;					//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=securitieIniCode;//��ʾԪ�صĳ�ʼֵ?????
			
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
			String[] strOtherElementNames={securitiesNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"SHORTNAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue)};
			
			String[] strListTitleDisplayNames={"һ������","��������","֤ȯ���"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-֤ȯ��Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=2;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_Securities";
			
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}			
		
		
	/**Magnifier0185
	 * ֤ȯ��Ϣ,��֤ȯ����IDԪ��Ӱ��
	 * @param out								jspWriter
	 * @param displayElementName				��ʾ�ı������� , ֤ȯ����
	 * @param idElementName						����������� , ֤ȯID
	 * @param idElementIniValue					ID�ĳ�ʼֵ , ֤ȯID
	 * 
	 * @param contractIdElementName				��ͬ��Ԫ������
	 * 
	 * @param securitiesNameElementName			֤ȯ����Ԫ������
	 * @param noticeWithSecuritiesElementName	������noticeWithSecuritiesId
	 * 
	 * @param typeId							������,���� 1,����һ������,2,���ض�������,3,������������
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createSecuritiesCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
				
			String contractIdElementName,
				
			String securitiesNameElementName,
			String noticeWithSecuritiesElementName,
				
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId<0 || typeId>4){
			throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
		}
			
		String[] strCtrlElementNames={contractIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CONTRACTID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			
		String[] strCtrlValues = {String.valueOf(Constant.RecordStatus.VALID),
									String.valueOf(SECConstant.NoticeFormStatus.CHECKED),
									String.valueOf(SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY)};
		String[] strCtrlFields = {"noticeWithStatusId","noticeFormStatusId","transactionTypeId"};
			
		String[] strOperators = {"like","=","=","=","="}; 
		String[] strLogicOperators = {"and","and","and","and"};
			
		String securitieCode = "SECURITIESCODE" + typeId;
		String securitieIniCode = NameRef.getSecuritiesCodeByID(idElementIniValue,securitieCode);
			
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields=securitieCode;					//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=securitieIniCode;//��ʾԪ�صĳ�ʼֵ?????
			
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
		String[] strOtherElementNames={securitiesNameElementName,noticeWithSecuritiesElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"SHORTNAME","noticeWithSecId"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue),
										NameRef.getNoticeWithSecuritiesId(idElementIniValue)};
			
		String[] strListTitleDisplayNames={"һ������","��������","֤ȯ���"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-֤ȯ��Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=2;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_NoticeSecMagnifier";
			
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}			
		
		
		
		
	/**Magnifier0190
		 * ֤ȯ��Ϣ
		 * @param out								jspWriter
		 * @param displayElementName				��ʾ�ı������� , ֤ȯ����
		 * @param idElementName						����������� , ֤ȯID
		 * @param idElementIniValue					ID�ĳ�ʼֵ , ֤ȯID
		 * 
		 * @param currencyId						����
		 * @param transactionTypeId					�������ʹ���,���ղ�ͬ�Ľ��׷��ز�ͬ���͵�֤ȯ����
		 * 										���û�н������ʹ���,ֻ֪��Ҫ����ָ����֤ȯ����������:
		 * 											1:SecuritiesCode1 һ������
		 * 											2:SecuritiesCode2 ��������
		 * 											3:SecuritiesCode3 ��������
		 * 											4:SecuritiesCode4 ��������
		 * @param securitiesNameElementName			֤ȯ����
		 * 
		 * @param typeId							������,ֻ����ծת��ҳ�� 0:��תծ���� 1:ת�ɹ�Ʊ����
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,
				long transactionTypeId,				//��������
				
				String securitiesNameElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{

			if (!(typeId == 0 || typeId == 1)){
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
			}
			String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={MagnifierHelper.getSecuritiesCodeField(transactionTypeId)};	//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ

			String securitiesTypeIds = MagnifierHelper.getSecuritiesTypeIds(transactionTypeId,currencyId,typeId);
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null; 
			String[] strLogicOperators = null;
			
			if (securitiesTypeIds!=null && securitiesTypeIds.length()>0){						//��ȷ��������
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),securitiesTypeIds};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","like","=","=","in"};
				strLogicOperators = new String[]{"or","and","and","and"};
			}
			else{												//û����������
				throw new SecuritiesException("֤ȯ�Ŵ�,�����typeId����",null);
				/*strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};*/
			}
			
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields=MagnifierHelper.getSecuritiesCodeField(transactionTypeId);						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getSecuritiesCodeByID(idElementIniValue,MagnifierHelper.getSecuritiesCodeField(transactionTypeId));//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={securitiesNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"SHORTNAME"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"һ������","��������","֤ȯ���"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-֤ȯ��Ϣ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=2;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}		

	/**
	 * �ʽ��ʻ�/�����ʻ��Ŵ�Magnifier0200
	 * @param out
	 * @param displayElementName							//�ʽ��ʻ�NameԪ������
	 * @param idElementName									//�ʽ��ʻ�IdԪ������
	 * @param idElementIniValue								//�ʽ��ʻ�Id��ʼֵ
	 * ����Ԫ��
	 * @param clientIdElementName							//ҵ��λ�Ŵ�,��Ӱ�쵽�ʽ��ʻ��Ŵ�����
	 * ����ֵ
	 * @param currencyId									//����
	 * 
	 * ����ֵ
	 * @param holderAccountCodeElementName					//�Ŵ󾵴������ʻ�CodeԪ������,�ʽ��ʻ������ɶ��ʺ�,�����ʻ����������ʺ�
	 * @param holderAccountNameElementName					//�Ŵ󾵴������ʻ�NameԪ������,�ʽ��ʻ������ɶ��ʻ�,�����ʻ����������ʻ�
	 * @param clientNameElementName							//ҵ��λ����Ԫ������
	 * @param counterpartIdElementName						//����Ӫҵ��IdԪ������ 		//����ֵ
	 * @param counterpartNameElementName					//����Ӫҵ��nameԪ������
	 * ����ֵ
	 * 
	 * @param typeId										//������ 0:�����ʽ��ʻ� 1:�û������ʻ�
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createAccountCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String clientIdElementName,
			
			long currencyId,
			
			String holderAccountCodeElementName,
			String holderAccountNameElementName,
			String clientNameElementName,
			String counterpartIdElementName,
			String counterpartNameElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		
			//У��typeId
		if (!(typeId == 0 || typeId == 1)){
			throw new SecuritiesException("�ʽ��ʻ��Ŵ󾵴����typeId����",null);
		}
		
		String[] strCtrlElementNames={displayElementName,clientIdElementName,counterpartIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"ACCOUNTCODE","CLIENTID","COUNTERPARTID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={"",NameRef.getClientIDByAccountID(idElementIniValue),NameRef.getCounterpartIDByAccountID(idElementIniValue)};											//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
		String[] strCtrlFields = {"STATUSID","CURRENCYID"};
		
		if (typeId == 0){					//�ʽ��ʻ�
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.SECURITIES_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		else if (typeId == 1){				//�����ʻ�
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.OPENFUND_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		
		String[] strOperators = {"like","like","=","=","=","=","="};
		String[] strLogicOperators = {"or","and","and","and","and","and"};
		
		
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields = "ACCOUNTCODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getAccountCodeById(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={	holderAccountCodeElementName,
										holderAccountNameElementName,
										clientIdElementName,
										clientNameElementName,
										counterpartIdElementName,
										counterpartNameElementName,
										clientIdElementName+counterpartNameElementName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		
		String[] strOtherElementFields = null;
		String[] strOtherElementValues = null;
		if (typeId == 0){									//�ʽ��ʻ�
			strOtherElementFields=new String[]{"HOLDERACCOUNTCODE",
												"HOLDERACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"CLIENTID"};		//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			strOtherElementValues=new String[]{NameRef.getStockHolderAccountCodeByAccountId(idElementIniValue),
												NameRef.getStockHolderAccountNameByAccountId(idElementIniValue),
												"",
												"",
												"",
												"",
												""};
		}
		else if (typeId == 1){								//�����ʻ�
			strOtherElementFields=new String[]{"CODE",
												"ACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"CLIENTID"};		//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			strOtherElementValues=new String[]{NameRef.getCodeByAccountID(idElementIniValue),
												NameRef.getAccountNameById(idElementIniValue),
												"",
												"",
												"",
												"",
												""};
		}
		
		
		
		String[] strListTitleDisplayNames = null;
		
		if (typeId == 0){
			strListTitleDisplayNames = new String[]{"�ʽ��ʻ�����","�ʽ��ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		}
		else if (typeId == 1){
			strListTitleDisplayNames = new String[]{"�����ʻ�����","�����ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		}
		
		String[] strListTitleDisplayFields={"ACCOUNTCODE","ACCOUNTNAME"}; 	//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-�ʽ��ʻ��Ŵ�";					//�Ŵ󾵵������ڱ���
		
		if (typeId == 1){
			strWindowTitle="֤ȯ-�����ʻ��Ŵ�";					//�Ŵ󾵵������ڱ���
		}
		
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��

		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VAccountMagnifier";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	/**
	 * �ʽ��ʻ�/�����ʻ��Ŵ�Magnifier0201
	 * @param out
	 * @param displayElementName							//�ʽ��ʻ�NameԪ������
	 * @param idElementName									//�ʽ��ʻ�IdԪ������
	 * @param idElementIniValue								//�ʽ��ʻ�Id��ʼֵ
	 * ����Ԫ��
	 * @param clientIdElementName							//ҵ��λ�Ŵ�,��Ӱ�쵽�ʽ��ʻ��Ŵ�����
	 * ����ֵ
	 * @param currencyId									//����
	 * 
	 * ����ֵ
	 * @param holderAccountCodeElementName					//�Ŵ󾵴������ʻ�CodeԪ������,�ʽ��ʻ������ɶ��ʺ�,�����ʻ����������ʺ�
	 * @param holderAccountNameElementName					//�Ŵ󾵴������ʻ�NameԪ������,�ʽ��ʻ������ɶ��ʻ�,�����ʻ����������ʻ�
	 * @param clientNameElementName							//ҵ��λ����Ԫ������
	 * @param counterpartIdElementName						//����Ӫҵ��IdԪ������ 		//����ֵ
	 * @param counterpartNameElementName					//����Ӫҵ��nameԪ������
	 * @param counterpartCodeElementName					//����Ӫҵ��CodeԪ������
	 * ����ֵ
	 * 
	 * @param typeId										//������ 0:�����ʽ��ʻ� 1:�û������ʻ�
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createAccountCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String clientIdElementName,
			
			long currencyId,
			
			String holderAccountCodeElementName,
			String holderAccountNameElementName,
			String clientNameElementName,
			String counterpartIdElementName,
			String counterpartNameElementName,
			String counterpartCodeElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		
			//У��typeId
		if (!(typeId == 0 || typeId == 1)){
			throw new SecuritiesException("�ʽ��ʻ��Ŵ󾵴����typeId����",null);
		}
		
		String[] strCtrlElementNames={displayElementName,clientIdElementName,counterpartIdElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"ACCOUNTCODE","CLIENTID","COUNTERPARTID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={"",NameRef.getClientIDByAccountID(idElementIniValue),NameRef.getCounterpartIDByAccountID(idElementIniValue)};											//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
		String[] strCtrlFields = {"STATUSID","CURRENCYID"};
		
		if (typeId == 0){					//�ʽ��ʻ�
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.SECURITIES_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		else if (typeId == 1){				//�����ʻ�
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.OPENFUND_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		
		String[] strOperators = {"like","like","=","=","=","=","="};
		String[] strLogicOperators = {"or","and","and","and","and","and"};
		
		
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields = "ACCOUNTCODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getAccountCodeById(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
		String[] strOtherElementNames={	holderAccountCodeElementName,
										holderAccountNameElementName,
										clientIdElementName,
										clientNameElementName,
										counterpartIdElementName,
										counterpartNameElementName,
										counterpartCodeElementName,
										clientIdElementName+counterpartCodeElementName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		
		String[] strOtherElementFields = null;
		String[] strOtherElementValues = null;
		if (typeId == 0){									//�ʽ��ʻ�
			strOtherElementFields=new String[]{"HOLDERACCOUNTCODE",
												"HOLDERACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"COUNTERPARTCODE",
												"CLIENTID"};		//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			strOtherElementValues=new String[]{NameRef.getStockHolderAccountCodeByAccountId(idElementIniValue),
												NameRef.getStockHolderAccountNameByAccountId(idElementIniValue),
												"",
												"",
												"",
												"",
												"",
												""};
		}
		else if (typeId == 1){								//�����ʻ�
			strOtherElementFields=new String[]{"CODE",
												"ACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"COUNTERPARTCODE",
												"CLIENTID"};		//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			strOtherElementValues=new String[]{NameRef.getCodeByAccountID(idElementIniValue),
												NameRef.getAccountNameById(idElementIniValue),
												"",
												"",
												"",
												"",
												"",
												""};
		}
		
		
		
		String[] strListTitleDisplayNames = null;
		
		if (typeId == 0){
			strListTitleDisplayNames = new String[]{"�ʽ��ʻ�����","�ʽ��ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		}
		else if (typeId == 1){
			strListTitleDisplayNames = new String[]{"�����ʻ�����","�����ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		}
		
		String[] strListTitleDisplayFields={"ACCOUNTCODE","ACCOUNTNAME"}; 	//��ʾ���ƵĶ�Ӧ�ֶ�

		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-�ʽ��ʻ��Ŵ�";					//�Ŵ󾵵������ڱ���
		
		if (typeId == 1){
			strWindowTitle="֤ȯ-�����ʻ��Ŵ�";					//�Ŵ󾵵������ڱ���
		}
		
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��

		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VAccountMagnifier";
	
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	
	/**Magnifier0011:֤ȯ���ͷŴ�
	 * @param out
	 * @param displayElementName							//֤ȯ��������Ԫ������
	 * @param idElementName									//֤ȯ����IDԪ������
	 * @param idElementIniValue								//֤ȯ����Id��ʼֵ
	 * 
	 * @param typeId										
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createSecuritiesTypeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
			
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE"};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
		
		/**
		 * ������
		 */
		String[] strOperators = {"like","like","="};
		/**
		 * �߼�������
		 */
		String[] strLogicOperators = {"or","and"};
		
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues="";			//��ʾԪ�صĳ�ʼֵ?????
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
		String[] strOtherElementNames={};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"֤ȯ���ʹ���","֤ȯ��������"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","NAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�
		
		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
		
		String strWindowTitle="֤ȯ-֤ȯ���ͷŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=1;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
		
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_SecuritiesType";
		
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
		
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
				
				strCtrlValues,
				strCtrlFields,
				
				strOperators,
				strLogicOperators,
				
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
				
				strVisibleElementType,
				strVisibleElementProperty,
				
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
				
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
				
				strListTitleDisplayNames,
				strListTitleDisplayFields,
				
				strnextFocusElementNames,
				
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
		
	/**Magnifier0018:֤ȯ���ͷŴ�,���ش���,��������
	 * @param out
	 * @param displayElementName							//֤ȯ���ʹ���Ԫ������
	 * @param idElementName									//֤ȯ����IDԪ������
	 * @param idElementIniValue								//֤ȯ����Id��ʼֵ
	 * 
	 * @param securitiesTypeName							//֤ȯ��������Ԫ������
	 * 
	 * @param typeId										//������
	 * 											0:���ش���,��������
	 * 											1:��������,��������		
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createSecuritiesTypeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String securitiesTypeName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
			)throws Exception{
		
		if (typeId>1 || typeId<0){
			throw new SecuritiesException("֤ȯ���ͷŴ�,����typeId����",null);
		}
		
		String[] strCtrlElementNames = null;							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields = null;							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues = null;								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
		
		if (typeId == 0){
			strCtrlElementNames = new String[]{displayElementName};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			strCtrlElementFields = new String[]{"NAME"};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			strCtrlElementValues = new String[]{""};								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
		}
		else if (typeId ==1){
			strCtrlElementNames = new String[]{displayElementName};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			strCtrlElementFields = new String[]{"CODE"};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			strCtrlElementValues = new String[]{""};								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
		}
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
		
		/**
		 * ������
		 */
		String[] strOperators = {"like","like","="};
		/**
		 * �߼�������
		 */
		String[] strLogicOperators = {"or","and"};
		
		String strVisibleElementNames=null;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields=null;						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=null;							//��ʾԪ�صĳ�ʼֵ?????
		
		if (typeId == 0){
			strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			strVisibleElementValues="";							//��ʾԪ�صĳ�ʼֵ?????
		}
		else if (typeId == 1){
			strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			strVisibleElementValues="";							//��ʾԪ�صĳ�ʼֵ?????
		}
		
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
		String[] strOtherElementNames=null;	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields=null;//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues=null;
			
			if (typeId == 0){
				strOtherElementNames=new String[]{securitiesTypeName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
				strOtherElementFields=new String[]{"NAME"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
				strOtherElementValues=new String[]{""};
			}
			else if (typeId == 1){
				strOtherElementNames=new String[]{securitiesTypeName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
				strOtherElementFields=new String[]{"CODE"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
				strOtherElementValues=new String[]{""};
			}
		
		String[] strListTitleDisplayNames={"֤ȯ���ʹ���","֤ȯ��������"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE","NAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�
		
		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
		
		String strWindowTitle="֤ȯ-֤ȯ���ͷŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=1;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
		
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_SecuritiesType";
		
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
		
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
				
				strCtrlValues,
				strCtrlFields,
				
				strOperators,
				strLogicOperators,
				
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
				
				strVisibleElementType,
				strVisibleElementProperty,
				
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
				
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
				
				strListTitleDisplayNames,
				strListTitleDisplayFields,
				
				strnextFocusElementNames,
				
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
	
	
	/**Magnifier0012:ҵ�����ͷŴ�,��������,����ҵ������ID
		 * @param out
		 * @param displayElementName							//ҵ����������Ԫ������
		 * @param idElementName									//ҵ������IDԪ������
		 * @param idElementIniValue								//ҵ������Id��ʼֵ
		 * 
		 * @param bussinessAttributeElementName					//������ҵ������Ԫ������
		 * 
		 * @param typeId										//������
		 * 												0:ȫ��
		 * 												1:ȥ������������ҵ��
		 * 												2:ֻ��ʾ����������ҵ��
		 * @param visibleElementProperty
		 * @param nextFocusElementNames
		 * @param showOnly
		 * @throws Exception
		 */
		public static void createBusinessTypeCtrl(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
					
					String bussinessAttributeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE"};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
		
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			/**
			 * ������
			 */
			String[] strOperators = null;
			/**
			 * �߼�������
			 */
			String[] strLogicOperators = null;
			if (typeId == 0){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
				strCtrlFields = new String[]{"STATUSID"};
				
				strOperators = new String[]{"like","like","="};
				strLogicOperators = new String[]{"or","and"};
			}
			else if (typeId == 1){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"11","12"};
				strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
				strOperators = new String[]{"like","like","=","<>","<>"};
				strLogicOperators = new String[]{"or","and","and","and"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"11","12"};
				strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
				strOperators = new String[]{"like","like","=","=","="};
				strLogicOperators = new String[]{"and","and","and","or"};
			}
		
			String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues="";			//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
			String[] strOtherElementNames={bussinessAttributeElementName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"BUSINESSATTRIBUTEID"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={""};
		
			String[] strListTitleDisplayNames={"ҵ�����ʹ���","ҵ����������"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�
		
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-ҵ�����ͷŴ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=1;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
		
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "sec_businessType";
		
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
		
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
				
					strCtrlValues,
					strCtrlFields,
				
					strOperators,
					strLogicOperators,
				
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
				
					strVisibleElementType,
					strVisibleElementProperty,
				
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
				
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
				
					strListTitleDisplayNames,
					strListTitleDisplayFields,
				
					strnextFocusElementNames,
				
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
		
		
	/**Magnifier0013:ҵ�����ͷŴ� ,���ش���,�������ƺ�ҵ������
		 * @param out
		 * @param displayElementName							//ҵ�����ʹ���Ԫ������
		 * @param idElementName									//ҵ������IDԪ������
		 * @param idElementIniValue								//ҵ������Id��ʼֵ
		 * 
		 * @param businessTypeNameElementName					//������ҵ����������Ԫ������
		 * @param bussinessAttributeElementName					//������ҵ������Ԫ������
		 * 
		 * @param typeId										//������
		 * 												0:���ش���,��������
		 * 												1:��������,��������
		 * @param visibleElementProperty
		 * @param nextFocusElementNames
		 * @param showOnly
		 * @throws Exception
		 */
		public static void createBusinessTypeCtrl(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
					
					String businessTypeNameElementName,
					String businessAttributeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
				)throws Exception{
			
			if (typeId<0 || typeId>1){
				throw new SecuritiesException("ҵ�����ͷŴ󾵴���typeId����",null);
			}
			
			String[] strCtrlElementNames = null;							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields = null;							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues = null;								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
			
			if (typeId == 0){
				strCtrlElementNames = new String[]{displayElementName};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
				strCtrlElementFields = new String[]{"NAME"};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
				strCtrlElementValues = new String[]{""};								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
			}
			else if (typeId ==1){
				strCtrlElementNames = new String[]{displayElementName};							//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
				strCtrlElementFields = new String[]{"CODE"};							//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
				strCtrlElementValues = new String[]{""};								//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
			}
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
		
			/**
			 * ������
			 */
			String[] strOperators = {"like","like","="};
			/**
			 * �߼�������
			 */
			String[] strLogicOperators = {"or","and"};
		
			String strVisibleElementNames=null;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields=null;						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=null;							//��ʾԪ�صĳ�ʼֵ?????
			
			if (typeId == 0){
				strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
				strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
				strVisibleElementValues="";							//��ʾԪ�صĳ�ʼֵ?????
			}
			else if (typeId == 1){
				strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
				strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
				strVisibleElementValues="";							//��ʾԪ�صĳ�ʼֵ?????
			}
			
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
		
		
		
			String[] strOtherElementNames=null;	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields=null;//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues=null;
			
			if (typeId == 0){
				strOtherElementNames=new String[]{businessTypeNameElementName,businessAttributeElementName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
				strOtherElementFields=new String[]{"NAME","BUSINESSATTRIBUTEID"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
				strOtherElementValues=new String[]{"",""};
			}
			else if (typeId == 1){
				strOtherElementNames=new String[]{businessTypeNameElementName,businessAttributeElementName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
				strOtherElementFields=new String[]{"CODE","BUSINESSATTRIBUTEID"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
				strOtherElementValues=new String[]{"",""};
			}
		
			String[] strListTitleDisplayNames={"ҵ�����ʹ���","ҵ����������"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�
		
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-ҵ�����ͷŴ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=1;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
		
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "sec_businessType";
		
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
		
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
				
					strCtrlValues,
					strCtrlFields,
				
					strOperators,
					strLogicOperators,
				
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
				
					strVisibleElementType,
					strVisibleElementProperty,
				
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
				
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
				
					strListTitleDisplayNames,
					strListTitleDisplayFields,
				
					strnextFocusElementNames,
				
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
	
		
	
		/**Magnifier0014:�������ͷŴ� ,��������,��ҵ������Ԫ��Լ��
		 * @param out
		 * @param displayElementName							//������������Ԫ������
		 * @param idElementName									//��������IDԪ������
		 * @param idElementIniValue								//��������Id��ʼֵ
		 * 
		 * @param businessTypeIdElementName						//ҵ������IdԪ������,Լ�����Ŵ���ʾֵ
		 * 
		 * @param typeId										// ������: 
		 * 										0:ȫ����������
		 * 										1:��ʾȥ���ʽ�������ź��ʽ������ŵ����н�������
		 * 										2:ֻ��ʾ�ʽ�������ź��ʽ�������
		 * @param visibleElementProperty
		 * @param nextFocusElementNames
		 * @param showOnly
		 * @throws Exception
		 */
		public static void createTransactionTypeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String businessTypeIdElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (typeId>2 || typeId<0){
				throw new SecuritiesException("�������ͷŴ�,����typeId����",null);
			}
			
			String[] strCtrlElementNames={businessTypeIdElementName};	//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"BUSINESSTYPEID"};			//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			/**
			 * ������
			 */
			String[] strOperators = null;
			/**
			 * �߼�������
			 */
			String[] strLogicOperators = null;
			if (typeId == 0){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
				strCtrlFields = new String[]{"STATUSID"};
				
				strOperators = new String[]{"like","like","="};
				strLogicOperators = new String[]{"or","and"};
			}
			else if (typeId == 1){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101,1201,8501,8502"};
				strCtrlFields = new String[]{"STATUSID","ID"};
				
				strOperators = new String[]{"like","like","=","not in"};
				strLogicOperators = new String[]{"or","and","and"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101","1201"};
				strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
				strOperators = new String[]{"like","like","=","=","="};
				strLogicOperators = new String[]{"and","and","and","or"};
			}
			
			
			String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues="";			//��ʾԪ�صĳ�ʼֵ?????
			
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
			
			
			
			String[] strOtherElementNames={};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"������������"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"NAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-�������ͷŴ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "sec_transactionType";
			
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
		
	/**Magnifier0017:�������ͷŴ� ,��������,��ҵ������Ԫ��Լ��,�ᷴ����ҵ������ID��ҵ����������
	 * @param out
	 * @param displayElementName							//������������Ԫ������
	 * @param idElementName									//��������IDԪ������
	 * @param idElementIniValue								//��������Id��ʼֵ
	 * 
	 * @param businessTypeIdElementName						//ҵ������IdԪ������,Լ�����Ŵ���ʾֵ,ѡ�˽������ͻᷴ������
	 * @param businessTypeNameElementName					//ҵ����������Ԫ������,Լ�����Ŵ���ʾֵ,ѡ�˽������ͻᷴ������
	 * 
	 * @param typeId										// ������: 
	 * 										0:ȫ����������
	 * 										1:��ʾȥ���ʽ�������ź��ʽ������ŵ����н�������
	 * 										2:ֻ��ʾ�ʽ�������ź��ʽ�������
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createTransactionTypeCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
				
			String businessTypeIdElementName,
			String businessTypeNameElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId>2 || typeId<0){
			throw new SecuritiesException("�������ͷŴ�,����typeId����",null);
		}
			
		String[] strCtrlElementNames={businessTypeIdElementName};	//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"BUSINESSTYPEID"};			//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ,��һ��Ϊģ����ѯ����,��ʼֵһ��Ϊ��
			
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		/**
		 * ������
		 */
		String[] strOperators = null;
		/**
		 * �߼�������
		 */
		String[] strLogicOperators = null;
		if (typeId == 0){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
				
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else if (typeId == 1){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101","1201"};
			strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
			strOperators = new String[]{"like","like","=","<>","<>"};
			strLogicOperators = new String[]{"or","and","and","and"};
		}
		else if (typeId == 2){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101","1201"};
			strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
			strOperators = new String[]{"like","like","=","=","="};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
			
			
		String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues="";			//��ʾԪ�صĳ�ʼֵ?????
			
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//���ص�Ĭ��ֵ
			
			
			
		String[] strOtherElementNames={businessTypeIdElementName,businessTypeNameElementName};	//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={"BUSINESSTYPEID","BUSINESSTYPENAME"};//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={"",""};
			
		String[] strListTitleDisplayNames={"������������"};//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"NAME"}; 		//��ʾ���ƵĶ�Ӧ�ֶ�
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-�������ͷŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_VBusiTransTypeMagnifier";
			
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
		
		
		/**Magnifier0081
		 * ��Ŀ�Ŵ�
		 * @param out								jspWriter
		 * @param mainElementName					ҳ����ʾ�ı�������,��Ŀ����Ԫ������
		 * @param hiddenElementName					�����������,��ĿIDԪ������
		 * @param hiddenElementValue				��ĿID�ĳ�ʼֵ,String����,��Ŀ�Ĵ���
		 * 
		 * @param subjectNameElementName				������Ŀ����Ԫ������
		 * 
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSubjectCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				String codeElementIniValue,				//��Ŀ����
				
				String subjectNameElementName,
				
				long currencyId,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"sSubjectCode"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			String[] strCtrlValues = {"1",String.valueOf(currencyId)};
			String[] strCtrlFields = {"NSTATUS","NCURRENCYID"};
			
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
			
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="sSubjectCode";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=codeElementIniValue;//��ʾԪ�صĳ�ʼֵ?????
			
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
			String subjectIdTmp = NameRef.getSubjectIDByCode(codeElementIniValue);
			long subjectId = -1;
			if (subjectIdTmp!=null && subjectIdTmp.trim().length()>0){
				subjectId = Long.parseLong(subjectIdTmp);
			}
			
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(subjectId)};	//���ص�Ĭ��ֵ
			
			String[] strOtherElementNames={subjectNameElementName};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={"sSubjectName"};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={NameRef.getSubjectNameByID(subjectId)};
			
			String[] strListTitleDisplayNames={"��Ŀ���","��Ŀ����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"sSubjectCode","sSubjectName"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-��Ŀ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SETT_VGLSUBJECTDEFINITION";
			
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
	/**Magnifier0015
	 * ҵ�����ʷŴ�
	 * @param out								jspWriter
	 * @param mainElementName					ҳ����ʾ�ı�������,ҵ�����ʵ�����
	 * @param idElementName						�����������,ҵ�����ʵ�ID
	 * @param idElementIniValue					ID�ĳ�ʼֵ
	 * 
	 * @param typeId							������
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createBusinessAttributeCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"CODE"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
			
		String[] strOperators = {"like","like","="};
		String[] strLogicOperators = {"or","and"};
			
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getBusinessAttributeNameById(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
			
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			

		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
		String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
			
		String[] strListTitleDisplayNames={"ҵ����������"}; 		//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"NAME"}; 				//��ʾ���ƵĶ�Ӧ�ֶ�
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-ҵ�����ʷŴ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "sec_businessAttribute";
			
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}		
	
	/**Magnifier0016
		 * ҵ�����ͷŴ�,��ҵ������Ԫ����Լ
		 * @param out								jspWriter
		 * @param mainElementName					ҳ����ʾ�ı�������,ҵ�����͵�����
		 * @param idElementName						�����������,ҵ�����͵�ID
		 * @param idElementIniValue					ID�ĳ�ʼֵ
		 * 
		 * @param businessAttributeElementName		ҵ������Ԫ������,��Լ�Ŵ�����
		 * 
		 * @param typeId							������
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createBusinessTypeCtrl_One(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String businessAttributeElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName,businessAttributeElementName};			//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"CODE","BUSINESSATTRIBUTEID"};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={"",""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
			
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
			
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="NAME";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getBusinessTypeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
			
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			

			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"ҵ�����ʹ���","ҵ����������"}; 		//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE","NAME"}; 				//��ʾ���ƵĶ�Ӧ�ֶ�
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-ҵ�����ʷŴ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "sec_businesstype";
			
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}		
	
		/**Magnifier0082
		 * ��ͬ�Ŵ�	���غ�ͬ����
		 * @param out								jspWriter
		 * @param mainElementName					ҳ��������ʾ�ı�������,���غ�ͬ����
		 * @param hiddenElementName					�ͻ�ID�����������,��ͬID
		 * @param hiddenElementValue				��ͬID�ĳ�ʼֵ
		 * 
		 * ---���Ʊ���
		 * @param transactionTypeId					��������ID
		 * @param inputUserId						¼����
		 * @param nextCheckUserId					��һ��������
		 * @param statusIdElementName				��ͬ״̬Ԫ������
		 * @param counterpartIdElementName			���׶���IdԪ������
		 * 
		 * @param typeId							������		
		 * 													0,ȫ�� 1,ֻ��ʾ�Ѽ���״̬�ĺ�ͬ 2,��ʾ������ҵ��Ѽ���״̬�ĺ�ͬ(currencyId != 1)
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createApplyContractCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String transactionTypeId,
				long inputUserId,
				long nextCheckUserId,
				String statusIdElementName,
				String couterpartIdElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (typeId<0 || typeId >2){
				throw new SecuritiesException("�����ͬ�Ŵ�,����typeId����",null);
			}
			
			String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName};				//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={"STATUSID","COUNTERPARTID"};	//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue)};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators  = null;
			String[] strLogicOperators =null;
			
			if (typeId ==0){
				strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId)};
				strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID"};
				
				strOperators = new String[]{"like","in","=","in","=","="};
				strLogicOperators = new String[]{"and","and","and","and","and"};
			}
			else if (typeId == 1){
				strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId),String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE)};
				strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID","STATUSID"};
				
				strOperators = new String[]{"like","in","in","in","=","=","in"};
				strLogicOperators = new String[]{"and","and","and","and","and","and"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(transactionTypeId),
												String.valueOf(inputUserId),
												String.valueOf(nextCheckUserId),
												String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE),
												String.valueOf(Constant.CurrencyType.RMB)};
				strCtrlFields = new String[]{"TRANSACTIONTYPEID",
												"INPUTUSERID",
												"NEXTCHECKUSERID",
												"STATUSID",
												"CURRENCYID"};
				
				strOperators = new String[]{"like","in","=","=","=","=","in","<>"};
				strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
			}
			
			
			
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
			
			String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"��ͬ����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"CODE"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-��ͬ�Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SEC_APPLYCONTRACT";
			
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
		

	/**Magnifier0083
			 * ��ͬ�Ŵ�	���غ�ͬ����
			 * @param out								jspWriter
			 * @param mainElementName					ҳ��������ʾ�ı�������,���غ�ͬ����
			 * @param hiddenElementName					�ͻ�ID�����������,��ͬID
			 * @param hiddenElementValue				��ͬID�ĳ�ʼֵ
			 * 
			 * ---���Ʊ���
			 * @param transactionTypeId					��������ID
			 * @param inputUserId						¼����
			 * @param nextCheckUserId					��һ��������
			 * @param statusIdElementName				��ͬ״̬Ԫ������
			 * @param counterpartIdElementName			���׶���IdԪ������
			 * @param capitalTypeElementName			�ʲ���ʽԪ������,ȷ����ʾ���ʲ�����
			 * 
			 * @param typeId							������		
			 * 													0,ȫ�� 1,ֻ��ʾ�Ѽ���״̬�ĺ�ͬ
			 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
			 * @param nextFocusElementNames				��һ������λ��
			 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
			 * @throws Exception
			 */
			public static void createApplyContractCtrl(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
				
					long transactionTypeId,
					long inputUserId,
					long nextCheckUserId,
					String statusIdElementName,
					String couterpartIdElementName,
					String capitalTypeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
					)throws Exception{
			
				if (typeId<0 || typeId >1){
					throw new SecuritiesException("�����ͬ�Ŵ�,����typeId����",null);
				}
				
				String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName,capitalTypeElementName};				//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
				String[] strCtrlElementFields={"STATUSID","COUNTERPARTID","INTERESTTYPEID"};	//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
				String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue),""};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
				String[] strCtrlValues = null;
				String[] strCtrlFields = null;
				String[] strOperators  = null;
				String[] strLogicOperators =null;
			
				if (typeId ==0){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","="};
					strLogicOperators = new String[]{"and","and","and","and","and","and"};
				}
				else if (typeId == 1){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId),String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID","STATUSID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","=","in"};
					strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
				}
			
			
			
				String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
				String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
				String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
			
				String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
				String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
				String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
				String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
				String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
				String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
				String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
				String[] strOtherElementValues={};
			
				String[] strListTitleDisplayNames={"��ͬ����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
				String[] strListTitleDisplayFields={"CODE"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
				String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
				String strWindowTitle="֤ȯ-��ͬ�Ŵ�";					//�Ŵ󾵵������ڱ���
				int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
				int intSQL=4;												//���ݲ�ѯSql 
				String strTableName = "SEC_APPLYCONTRACT";
			
				boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
				showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
					
						strCtrlValues,
						strCtrlFields,
					
						strOperators,
						strLogicOperators,
					
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
					
						strVisibleElementType,
						strVisibleElementProperty,
					
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
					
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
					
						strListTitleDisplayNames,
						strListTitleDisplayFields,
					
						strnextFocusElementNames,
					
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
			}
			
			/**Magnifier0085
			 * ��ͬ�Ŵ�	���غ�ͬ����
			 * @param out								jspWriter
			 * @param mainElementName					ҳ��������ʾ�ı�������,���غ�ͬ����
			 * @param hiddenElementName					�ͻ�ID�����������,��ͬID
			 * @param hiddenElementValue				��ͬID�ĳ�ʼֵ
			 * 
			 * ---���Ʊ���
			 * @param transactionTypeId					��������ID
			 * @param inputUserId						¼����
			 * @param nextCheckUserId					��һ��������
			 * @param statusIdElementName				��ͬ״̬Ԫ������
			 * @param counterpartIdElementName			���׶���IdԪ������
			 * @param capitalTypeElementName			�ʲ���ʽԪ������,ȷ����ʾ���ʲ�����
			 * 
			 * @param typeId							������		
			 * 													0,ȫ�� 1,ֻ��ʾ�Ѽ���״̬�ĺ�ͬ
			 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
			 * @param nextFocusElementNames				��һ������λ��
			 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
			 * @throws Exception
			 */
			public static void createApplyContractCtrl3(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
				
					long transactionTypeId,
					long inputUserId,
					long nextCheckUserId,
					String statusIdElementName,
					String couterpartIdElementName,
					String capitalTypeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
					)throws Exception{
			
				if (typeId<0 || typeId >1){
					throw new SecuritiesException("��ͬҵ��Ŵ�,����typeId����",null);
				}
				
				String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName,capitalTypeElementName};				//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
				String[] strCtrlElementFields={"STATUSID","COUNTERPARTID","INTERESTTYPEID"};	                                //Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
				String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue),""};					//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
				String[] strCtrlValues = null;
				String[] strCtrlFields = null;
				String[] strOperators  = null;
				String[] strLogicOperators =null;
			
				if (typeId ==0){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","="};
					strLogicOperators = new String[]{"and","and","and","and","and","and"};
				}
				else if (typeId == 1){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId),String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE+","+SECConstant.ContractStatus.EXTEND)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID","STATUSID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","=","in"};
					strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
				}
			
			
			
				String strVisibleElementNames=displayElementName;			//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
				String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
				String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
			
				String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
				String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
				String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
				String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
				String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
				String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
				String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
				String[] strOtherElementValues={};
			
				String[] strListTitleDisplayNames={"��ͬ����"}; 	        //�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
				String[] strListTitleDisplayFields={"CODE"}; 		    	//��ʾ���ƵĶ�Ӧ�ֶ�
			
				String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
				String strWindowTitle="֤ȯ-��ͬ�Ŵ�";					//�Ŵ󾵵������ڱ���
				int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
				int intSQL=4;												//���ݲ�ѯSql 
				String strTableName = "SEC_APPLYCONTRACT";
			
				boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
				showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
					
						strCtrlValues,
						strCtrlFields,
					
						strOperators,
						strLogicOperators,
					
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
					
						strVisibleElementType,
						strVisibleElementProperty,
					
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
					
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
					
						strListTitleDisplayNames,
						strListTitleDisplayFields,
					
						strnextFocusElementNames,
					
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
			}
			

	/**Magnifier0084
	 * ��ͬ�Ŵ�	���غ�ͬ����
	 * @param out								jspWriter
	 * @param mainElementName					ҳ��������ʾ�ı�������,���غ�ͬ����
	 * @param hiddenElementName					�ͻ�ID�����������,��ͬID
	 * @param hiddenElementValue				��ͬID�ĳ�ʼֵ
	 * 
	 * ---���Ʊ���
	 * @param transactionTypeId					��������ID
	 * @param inputUserId						¼����
	 * @param nextCheckUserId					��һ��������
	 * @param statusIdElementName				��ͬ״̬Ԫ������
	 * @param counterpartIdElementName			���׶���IdԪ������
	 * 
	 * @param contractInterestDate				��ͬ��Ϣ����
	 * 
	 * @param typeId							������		
	 * 													0,ȫ�� 1,ֻ��ʾִ����״̬�ĺ�ͬ 2,��ʾ������ҵ��Ѽ���״̬�ĺ�ͬ(currencyId != 1)
	 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
	 * @param nextFocusElementNames				��һ������λ��
	 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
	 * @throws Exception
	 */
	public static void createApplyContractCtrl2(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
				
			String transactionTypeId,
			long inputUserId,
			long nextCheckUserId,
			String statusIdElementName,
			String couterpartIdElementName,
			
			String contractInterestDate,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId<0 || typeId >2){
			throw new SecuritiesException("�����ͬ�Ŵ�,����typeId����",null);
		}
			
		String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName};				//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
		String[] strCtrlElementFields={"STATUSID","COUNTERPARTID"};	//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
		String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue)};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
			
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		String[] strOperators  = null;
		String[] strLogicOperators =null;
		transactionTypeId="'"+transactionTypeId+"'";	
		if (typeId ==0){
			strCtrlValues = new String[]{String.valueOf(transactionTypeId),
										String.valueOf(inputUserId),
										String.valueOf(nextCheckUserId),
										contractInterestDate};
			strCtrlFields = new String[]{"TRANSACTIONTYPEID",
										"INPUTUSERID",
										"NEXTCHECKUSERID",
										"_CONTRACTINTERESTDATE"};
				
			strOperators = new String[]{"like","in","=","in","=","=","<"};
			strLogicOperators = new String[]{"and","and","and","and","and","and"};
		}
		else if (typeId == 1){
			strCtrlValues = new String[]{String.valueOf(transactionTypeId),
								String.valueOf(inputUserId),
					String.valueOf(nextCheckUserId),
					String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE),
					contractInterestDate};
			strCtrlFields = new String[]{"TRANSACTIONTYPEID",
										"INPUTUSERID",
										"NEXTCHECKUSERID",
										"STATUSID",
										"_CONTRACTINTERESTDATE"};
				
			strOperators = new String[]{"like","in","=","in","=","=","in","<"};
			strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
		}
		else if (typeId == 2){
			strCtrlValues = new String[]{String.valueOf(transactionTypeId),
											String.valueOf(inputUserId),
											String.valueOf(nextCheckUserId),
											String.valueOf(SECConstant.ContractStatus.ACTIVE),
											String.valueOf(Constant.CurrencyType.RMB),
											contractInterestDate};
			strCtrlFields = new String[]{"TRANSACTIONTYPEID",
											"INPUTUSERID",
											"NEXTCHECKUSERID",
											"STATUSID",
											"CURRENCYID",
											"_CONTRACTINTERESTDATE"};
				
			strOperators = new String[]{"like","in","=","in","=","=","=","=","<"};
			strLogicOperators = new String[]{"and","and","and","and","and","and","and","and"};
		}
			
			
			
		String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
		String strVisibleElementFields="CODE";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
		String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
			
		String strVisibleElementType="text";						//��ʾԪ�ص�����=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
			
		String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
		String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
			
		String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
		String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
		String[] strOtherElementValues={};
			
		String[] strListTitleDisplayNames={"��ͬ����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
		String[] strListTitleDisplayFields={"CODE"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
		String strWindowTitle="֤ȯ-��ͬ�Ŵ�";					//�Ŵ󾵵������ڱ���
		int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
		int intSQL=4;												//���ݲ�ѯSql 
		String strTableName = "SEC_APPLYCONTRACT";
			
		boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
			
			
	//�����ʻ��Ŵ�	
		
	/**Magnifier0010
		 * �ʻ��Ŵ󾵷Ŵ�Magnifier001
		 * @param out								jspWriter
		 * @param mainElementName					ҳ��ͻ�������ʾ�ı�������,����ҵ��λ����
		 * @param hiddenElementName					�ͻ�ID�����������,ҵ��λID
		 * @param hiddenElementValue				�ͻ�ID�ĳ�ʼֵ
		 * 
		 * @param typeId							������,��Ϊ����,�˴��������   **********
		 * @param visibleElementProperty			����ʾԪ�ص��Զ�������
		 * @param nextFocusElementNames				��һ������λ��
		 * @param showOnly							�Ƿ����Ϊ��ʾ����(���ڸ���ҳ��)
		 * @throws Exception
		 */
		public static void createSettAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={};				//��Ӱ�쵽�Ŵ���ʾ��ֵ�Ĳ���
			String[] strCtrlElementFields={};						//Ӱ�쵽�Ŵ���ʾֵ�ò�����Ӧ�����ݿ��ֶ�
			String[] strCtrlElementValues={};							//��Ӱ�쵽�Ŵ󾵵ĳ�ʼֵ
		
			
			//zpli modify 2005-09-14 
			//TODO: ֤ȯ ��ȷ��
			String[] strCtrlValues = {	String.valueOf(SETTConstant.AccountGroupType.CURRENT),
					String.valueOf(SETTConstant.AccountCheckStatus.CHECK),
					String.valueOf(SETTConstant.AccountStatus.NORMAL+","+SETTConstant.AccountStatus.SEALUP),
					String.valueOf(typeId)};

			/*String[] strCtrlValues = {	String.valueOf(SETTConstant.AccountType.CURRENTDEPOSIT),
										String.valueOf(SETTConstant.AccountCheckStatus.CHECK),
										String.valueOf(SETTConstant.AccountStatus.NORMAL+","+SETTConstant.AccountStatus.SEALUP),
										String.valueOf(typeId)};*/
			String[] strCtrlFields = {"NACCOUNTTYPEID","NCHECKSTATUSID","NSTATUSID","nCurrencyID"};
	
			String[] strOperators = {"like","=","=","in","="};
			String[] strLogicOperators = {"and","and","and","and"};
	
			String strVisibleElementNames=displayElementName;				//ҳ����ʾԪ�ص����� =;Ҫ����ҳ��Ԫ��
			String strVisibleElementFields="SACCOUNTNO";						//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
			String strVisibleElementValues=NameRef.getSettAccountNoById(idElementIniValue);//��ʾԪ�صĳ�ʼֵ?????
		
			String strVisibleElementProperty=visibleElementProperty + "size='4' maxlength='4' class='box'";//����=;д�뵽�Ŵ���ʾ�ı�����
		
			String[] strHiddenElementNames={idElementName};				//��ҳ�����������id=;Ҫ����ҳ��Ԫ��
			String[] strHiddenElementFields={"ID"}; 					//id��Ӧ���ֶ���
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//���ص�Ĭ��ֵ
		
			String[] strOtherElementNames={};							//�����ͱ��Ŵ󾵹�����Ԫ��=;��������ʱҲҪ����
			String[] strOtherElementFields={};							//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"�ʻ����","�ʻ�����"}; 	//�Ŵ󾵵���ҳ�����ʾ����=;�����ж���
			String[] strListTitleDisplayFields={"SACCOUNTNO","SNAME"}; 			//��ʾ���ƵĶ�Ӧ�ֶ�

			String[] strnextFocusElementNames=nextFocusElementNames;	//��һ������
			
			String strWindowTitle="֤ȯ-�ʻ��Ŵ�";					//�Ŵ󾵵������ڱ���
			int intListAnchorPosition=0;								//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
			
			int intSQL=4;												//���ݲ�ѯSql 
			String strTableName = "SETT_ACCOUNT";
	
			boolean blnShowOnly = showOnly;								//ֻ����ʾ��־λ
				
			showMultiZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							4,
							"-",
							strTableName,
							blnShowOnly
							);
		}
		
	//-----------------------------------------------------------
	
		
	/**
	 * ��ͨ�Ŵ�����html������
	 * @param out
	 * @param strCtrlElementNames						//��Ӱ�쵽�Ŵ󾵵�ҳ��Ԫ������
	 * @param strCtrlElementFields						//��Ӱ�쵽�Ŵ󾵵�ҳ��Ԫ�ض�Ӧ�����ݿ��ֶ�
	 * @param strCtrlElementValues						//Ӱ�쵽�Ŵ�ҳ��Ԫ�صĺ�̨У���ʼֵ
	 * 
	 * @param strCtrlValues								//��Ӱ�쵽�Ŵ󾵵�ֵ
	 * @param strCtrlFields								//��Ӱ�쵽�Ŵ󾵵�ֵ��Ӧ�����ݿ��ֶ�
	 * 
	 * String[] strOperators,							//ÿһ�����Ʋ����Ĳ�����,��">","<","like","in"
	 * String[] strLogicOperators,						//����where�Ӿ�֮����߼������,ֻ������"and"��"or"
	 * 
	 * @param strVisibleElementNames					//ҳ����ʾԪ�ص����� ,Ҫ����ҳ��Ԫ��
	 * @param strVisibleElementFields					//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
	 * @param strVisibleElementValues					//��ʾԪ�صĳ�ʼֵ
	 * 
	 * @param strVisibleElementType						//��ʾԪ�ص�����,"text","textarea"
	 * @param strVisibleElementProperty 				//����,д�뵽�Ŵ���ʾ�ı�����
	 *  
	 * @param strHiddenElementNames						//��ҳ��������������,Ҫ����������
	 * @param strHiddenElementFields 					//id��Ӧ���ֶ���
	 * @param strHiddenElementValues					//��ʼֵ
	 * 
	 * @param strOtherElementNames						//�����ͱ��Ŵ󾵹�����Ԫ��,��������ʱҲҪ����
	 * @param strOtherElementFields						//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
	 * 
	 * @param strListTitleDisplayNames 					//�Ŵ󾵵���ҳ�����ʾ����,�����ж���
	 * @param strListTitleDisplayFields 				//��ʾ���ƵĶ�Ӧ�ֶ�
	 * 
	 * @param strNextFocusElementNames					//��һ������
	 * 
	 * @param strWindowTitle							//�Ŵ󾵵������ڱ���
	 * @param intListAnchorPosition						//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
	 * @param intSQL									//���ݲ�ѯSql����,ȷ��ʹ�õڼ���sql
	 * @param strName									//���� 
	 * @param blnShowOnly								//��־λ,ȷ����ǰ�Ŵ��Ƿ�ֻ��������ʾ
	 * 											(���縴��ҳ��,ȫ���û�,��������󴫵�),�����,�򲻻�д�뵯�����ڴ���
	 * @throws Exception
	 */
	public static void showZoomCtrl(
			JspWriter out,
		
			String[] strCtrlElementNames,
			String[] strCtrlElementFields,
			String[] strCtrlElementValues,
			
			String[] strCtrlValues,
			String[] strCtrlFields,
			
			String[] strOperators,
			String[] strLogicOperators,
			
			String strVisibleElementNames,
			String strVisibleElementFields,
			String strVisibleElementValues,

			String strVisibleElementType,
			String strVisibleElementProperty,
			
			String[] strHiddenElementNames,
			String[] strHiddenElementFields,
			String[] strHiddenElementValues,

			String[] strOtherElementNames,
			String[] strOtherElementFields,
			String[] strOtherElementValues,
			
			String[] strListTitleDisplayNames,
			String[] strListTitleDisplayFields,

			String[] strNextFocusElementNames,
			
			String strWindowTitle,
			int intListAnchorPosition,
			
			int intSQL,
			String strName,
			boolean blnShowOnly
			)throws Exception{
        
			if (strVisibleElementNames==null || strVisibleElementNames.trim().length()==0){
		
			}
			
			/**����ת��*/
			strCtrlElementNames			= convertNullToSpace(strCtrlElementNames);
			strCtrlElementFields		= convertNullToSpace(strCtrlElementFields);
			strCtrlElementValues		= convertNullToSpace(strCtrlElementValues);
			strCtrlValues				= convertNullToSpace(strCtrlValues);
			strCtrlFields				= convertNullToSpace(strCtrlFields);
			
			strVisibleElementValues 	= convertNullToSpace(strVisibleElementValues);
			
			strVisibleElementType 		= convertNullToSpace(strVisibleElementType);
			
			strHiddenElementNames		= convertNullToSpace(strHiddenElementNames);
			strHiddenElementFields		= convertNullToSpace(strHiddenElementFields);
			strHiddenElementValues		= convertNullToSpace(strHiddenElementValues);
			
			strOtherElementNames		= convertNullToSpace(strOtherElementNames);
			strOtherElementFields		= convertNullToSpace(strOtherElementFields);
			strOtherElementValues		= convertNullToSpace(strOtherElementValues);
			
			strListTitleDisplayNames	= convertNullToSpace(strListTitleDisplayNames);
			strListTitleDisplayFields	= convertNullToSpace(strListTitleDisplayFields);
						
			strWindowTitle 				= convertNullToSpace(strWindowTitle);
			strVisibleElementProperty 	= convertNullToSpace(strVisibleElementProperty);
			
			strNextFocusElementNames	= convertNullToSpace(strNextFocusElementNames);
			/**ת������*/
			
			/**У����Чֵ*/
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementFields,true)){
				throw new SecuritiesException("����Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementValues,true)){
				throw new SecuritiesException("����Ԫ��'����'��'��ʼֵ'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strCtrlValues,strCtrlFields,true)){
				throw new SecuritiesException("����ֵ'ֵ'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementFields,false)){		//������Ϊ��
				throw new SecuritiesException("����Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementValues,true)){
				throw new SecuritiesException("����Ԫ��'����'��'��ʼֵ'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strOtherElementNames,strOtherElementFields,true)){
				throw new SecuritiesException("����Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strOtherElementFields,strOtherElementValues,true)){
				throw new SecuritiesException("����Ԫ��'�ֶ�'��'��ʼֵ'��ƥ��",null);
			}
			if (!isElementsMatchFields(strListTitleDisplayNames,strListTitleDisplayFields,false)){	//������Ϊ��
				throw new SecuritiesException("����'����'��'�ֶ�'��ƥ��!",null);
			}
			
			if (strVisibleElementNames.equals("") || strVisibleElementFields.equals("")){			//������Ϊ��
				throw new SecuritiesException("��Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			/**У�����*/
			
			StringBuffer sbOutputContent = new StringBuffer(1024);				//�������
			StringBuffer sbJsOnkeyDown = new StringBuffer(256);					//?���洫�ݵĲ���
			StringBuffer sbJsOnFocus = new StringBuffer(128);					//���Ŵ󾵻�ý���ʱ����,���У����
        	
        	
        	
				  /**        ��ӿɼ�Ԫ������     	 **/
			sbJsOnkeyDown.append("openWindow(new Array('" + strVisibleElementNames + "'");

			for (int n=0;n<strHiddenElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementNames[n] + "'");
			}
			for (int n=0;n<strOtherElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**        ��ӿɼ�Ԫ�ض�Ӧ���ݿ��ֶ�����     	 **/
			sbJsOnkeyDown.append("new Array('" + strVisibleElementFields + "'");
			for (int n=0;n<strHiddenElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementFields[n] + "'");
			}
			for (int n=0;n<strOtherElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");

			/**			��ӿ���Ԫ�ص�ֵ		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
			/**			��ӿ���Ԫ�ض�Ӧ���ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
		
				/**			��ӿ���Ԫ�ض�Ӧ���ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlValues.length;n++){
				if (n==0) 
					sbJsOnkeyDown.append(strCtrlValues[n]);
				else sbJsOnkeyDown.append(",'" + strCtrlValues[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        
				/**			��ӿ���Ԫ�ض�Ӧ���ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			��ӿ���Ԫ�ض�Ӧ�������		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			��ӿ���Ԫ��֮����߼������		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strLogicOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strLogicOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strLogicOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**			��ӵ���������ʾ�ֶε�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + URLEncoder.encode(strListTitleDisplayNames[n]) + "'");
				else sbJsOnkeyDown.append(",'" + URLEncoder.encode(strListTitleDisplayNames[n]) + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
				  /**			��ӵ���������ʾ�ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
			sbJsOnkeyDown.append("'"+URLEncoder.encode(strWindowTitle)+"', \n");
			sbJsOnkeyDown.append("'"+intListAnchorPosition+"', \n");
			sbJsOnkeyDown.append("'"+intSQL+"', \n");
			sbJsOnkeyDown.append("'"+strName+"', \n");
		
			/**			�����һ������λ��		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strNextFocusElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strNextFocusElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strNextFocusElementNames[n] + "'");
			}
			sbJsOnkeyDown.append(")) \n");

			
			if (blnShowOnly) strVisibleElementProperty +=" disabled";			//���ֻ����ʾ,��ô��Ϊdisabled
			if (strVisibleElementType.toLowerCase().equals("text") || strVisibleElementType.equals("")){
				sbOutputContent.append("<input type='" + strVisibleElementType + 
				"' name='" + strVisibleElementNames + 
				"' value='" + strVisibleElementValues + 
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					sbOutputContent.append(" onKeyDown=\"if(event.keyCode==13)" + sbJsOnkeyDown.toString() + " \"" );
				
					if (strHiddenElementNames!=null){
						sbOutputContent.append(" onChange=\"");
						for (int n=0;n<strHiddenElementNames.length;n++){
							sbOutputContent.append("document.all.");
							sbOutputContent.append(strHiddenElementNames[n]+".value=''; \n");
							if (n<(strHiddenElementNames.length-1)){sbOutputContent.append(",");}
						}
						for (int n=0;n<strCtrlElementNames.length;n++){				//Ӱ��Ŵ󾵵�Ԫ�ص�У����ͳ�ʼֵ
							if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
								sbOutputContent.append("document.all.");
								sbOutputContent.append(strCtrlElementNames[n] + strVisibleElementNames);
								sbOutputContent.append(".value=''; \n");
							}
						}
						sbOutputContent.append("\"");
					}
				}
			
				sbOutputContent.append("> \n");
			}
			else if(strVisibleElementType.toLowerCase().equals("textarea")){
				sbOutputContent.append("<textArea name='" + strVisibleElementNames +
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					sbOutputContent.append(" onKeyDown=\"if(event.keyCode==13)" + sbJsOnkeyDown.toString()+ "\"" );
				}
			
				sbOutputContent.append(">");
				sbOutputContent.append(strVisibleElementValues + "</textArea> \n");
			}
			if (!blnShowOnly){
				sbOutputContent.append("<a href='#1'><img name='imgButton'" +
				" src='/websec/image/icon.gif' border=0 " +
				" onclick=\"" + sbJsOnkeyDown.toString() +
				"\"></a> \n");
			}
			
			
			for (int n=0;n<strHiddenElementNames.length;n++){
				sbOutputContent.append("<input type='hidden' name='" + strHiddenElementNames[n] + 
				"' value='" + strHiddenElementValues[n] + "'> \n");
			}
		
			if (!blnShowOnly){
				for (int n=0;n<strCtrlElementNames.length;n++){				//Ӱ��Ŵ󾵵�Ԫ�ص�У����ͳ�ʼֵ
					if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
						sbOutputContent.append("<input type='hidden' name='" + 
								(strCtrlElementNames[n] + strVisibleElementNames) + 
								"' value='" + strCtrlElementValues[n] + "'> \n");
					}
				}
			}
			if (strOtherElementNames != null && strOtherElementNames.length > 0){//Ӱ�����ĳ�ʼֵ��ֵ
				sbOutputContent.append("<script language='javascript'> \n");
				sbOutputContent.append("	setTimeout(\"");
				for (int n=0;n<strOtherElementNames.length;n++){
					if (strOtherElementNames[n]!=null && strOtherElementNames[n].trim().length()>0 
							&& strOtherElementValues[n]!=null && strOtherElementValues[n].length()>0){
						sbOutputContent.append("document.all."+strOtherElementNames[n].trim()+".value='"+strOtherElementValues[n]+"';");
					}
				}
				sbOutputContent.append("	\",100); \n");
				sbOutputContent.append("</script> \n");
			}
				
			out.println(sbOutputContent.toString());
			System.out.println("ѽѽѽ"+sbOutputContent.toString());
		}
	
	
	/**
	 * ���ı���Ŵ�����html������
	 * @param out
	 * @param strCtrlElementNames						//��Ӱ�쵽�Ŵ󾵵�ҳ��Ԫ������
	 * @param strCtrlElementFields						//��Ӱ�쵽�Ŵ󾵵�ҳ��Ԫ�ض�Ӧ�����ݿ��ֶ�
	 * @param strCtrlElementValues						//Ӱ�쵽�Ŵ�ҳ��Ԫ�صĺ�̨У���ʼֵ
	 * 
	 * @param strCtrlValues								//��Ӱ�쵽�Ŵ󾵵�ֵ
	 * @param strCtrlFields								//��Ӱ�쵽�Ŵ󾵵�ֵ��Ӧ�����ݿ��ֶ�
	 * 
	 * String[] strOperators,							//ÿһ�����Ʋ����Ĳ�����,��">","<","like","in"
	 * String[] strLogicOperators,						//����where�Ӿ�֮����߼������,ֻ������"and"��"or"
	 * 
	 * @param strVisibleElementNames					//ҳ����ʾԪ�ص����� ,Ҫ����ҳ��Ԫ��
	 * @param strVisibleElementFields					//��ʾԪ�ض�Ӧ���ݿ���ֶ���.
	 * @param strVisibleElementValues					//��ʾԪ�صĳ�ʼֵ
	 * 
	 * @param strVisibleElementProperty 				//����,д�뵽�Ŵ���ʾ�ı�����
	 *  
	 * @param strHiddenElementNames						//��ҳ��������������,Ҫ����������
	 * @param strHiddenElementFields 					//id��Ӧ���ֶ���
	 * @param strHiddenElementValues					//��ʼֵ
	 * 
	 * @param strOtherElementNames						//�����ͱ��Ŵ󾵹�����Ԫ��,��������ʱҲҪ����
	 * @param strOtherElementFields						//������Ҫ����ֵ��Ԫ�ض�Ӧ�����ݿ��ֶ�
	 * 
	 * @param strListTitleDisplayNames 					//�Ŵ󾵵���ҳ�����ʾ����,�����ж���
	 * @param strListTitleDisplayFields 				//��ʾ���ƵĶ�Ӧ�ֶ�
	 * 
	 * @param strNextFocusElementNames					//��һ������
	 * 
	 * @param strWindowTitle							//�Ŵ󾵵������ڱ���
	 * @param intListAnchorPosition						//�Ŵ�ҳ�浯��ʱĬ��ͣ����λ��
	 * @param intSQL									//���ݲ�ѯSql����,ȷ��ʹ�õڼ���sql
	 * @param textNum									//�ı�������
	 * @param delims									//�ָ���
	 * @param strName									//���� 
	 * @param blnShowOnly								//��־λ,ȷ����ǰ�Ŵ��Ƿ�ֻ��������ʾ
	 * 											(���縴��ҳ��,ȫ���û�,��������󴫵�),�����,�򲻻�д�뵯�����ڴ���
	 * @throws Exception
	 */
	public static void showMultiZoomCtrl(
			JspWriter out,
		
			String[] strCtrlElementNames,
			String[] strCtrlElementFields,
			String[] strCtrlElementValues,
			
			String[] strCtrlValues,
			String[] strCtrlFields,
			
			String[] strOperators,
			String[] strLogicOperators,
			
			String strVisibleElementNames,
			String strVisibleElementFields,
			String strVisibleElementValues,

			String strVisibleElementProperty,
			
			String[] strHiddenElementNames,
			String[] strHiddenElementFields,
			String[] strHiddenElementValues,

			String[] strOtherElementNames,
			String[] strOtherElementFields,
			String[] strOtherElementValues,
			
			String[] strListTitleDisplayNames,
			String[] strListTitleDisplayFields,

			String[] strNextFocusElementNames,
			
			String strWindowTitle,
			int intListAnchorPosition,
			
			int intSQL,
			int textNum,
			String delims,
			String strName,
			boolean blnShowOnly
			)throws Exception{
        
			if (strVisibleElementNames==null || strVisibleElementNames.trim().length()==0){
		
			}
			
			/**����ת��*/
			strCtrlElementNames			= convertNullToSpace(strCtrlElementNames);
			strCtrlElementFields		= convertNullToSpace(strCtrlElementFields);
			strCtrlElementValues		= convertNullToSpace(strCtrlElementValues);
			strCtrlValues				= convertNullToSpace(strCtrlValues);
			strCtrlFields				= convertNullToSpace(strCtrlFields);
			
			strVisibleElementValues 	= convertNullToSpace(strVisibleElementValues);
			
			strHiddenElementNames		= convertNullToSpace(strHiddenElementNames);
			strHiddenElementFields		= convertNullToSpace(strHiddenElementFields);
			strHiddenElementValues		= convertNullToSpace(strHiddenElementValues);
			
			strOtherElementNames		= convertNullToSpace(strOtherElementNames);
			strOtherElementFields		= convertNullToSpace(strOtherElementFields);
			strOtherElementValues		= convertNullToSpace(strOtherElementValues);
			
			strListTitleDisplayNames	= convertNullToSpace(strListTitleDisplayNames);
			strListTitleDisplayFields	= convertNullToSpace(strListTitleDisplayFields);
						
			strWindowTitle 				= convertNullToSpace(strWindowTitle);
			strVisibleElementProperty 	= convertNullToSpace(strVisibleElementProperty);
			
			strNextFocusElementNames	= convertNullToSpace(strNextFocusElementNames);
			/**ת������*/
			
			/**У����Чֵ*/
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementFields,true)){
				throw new SecuritiesException("����Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementValues,true)){
				throw new SecuritiesException("����Ԫ��'����'��'��ʼֵ'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strCtrlValues,strCtrlFields,true)){
				throw new SecuritiesException("����ֵ'ֵ'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementFields,false)){		//������Ϊ��
				throw new SecuritiesException("����Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementValues,true)){
				throw new SecuritiesException("����Ԫ��'����'��'��ʼֵ'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strOtherElementNames,strOtherElementFields,true)){
				throw new SecuritiesException("����Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			if (!isElementsMatchFields(strOtherElementFields,strOtherElementValues,true)){
				throw new SecuritiesException("����Ԫ��'�ֶ�'��'��ʼֵ'��ƥ��",null);
			}
			if (!isElementsMatchFields(strListTitleDisplayNames,strListTitleDisplayFields,false)){	//������Ϊ��
				throw new SecuritiesException("����'����'��'�ֶ�'��ƥ��!",null);
			}
			
			if (strVisibleElementNames.equals("") || strVisibleElementFields.equals("")){			//������Ϊ��
				throw new SecuritiesException("��Ԫ��'����'��'�ֶ�'��ƥ��!",null);
			}
			
			if (delims == null || delims.equals("")){
				throw new SecuritiesException ("��ָ���ָ���!",null);
			}
			/**У�����*/
			
			StringBuffer sbOutputContent = new StringBuffer(1024);				//�������
			StringBuffer sbJsOnkeyDown = new StringBuffer(256);					//?���洫�ݵĲ���
			StringBuffer sbJsOnFocus = new StringBuffer(128);					//���Ŵ󾵻�ý���ʱ����,���У����
        	
        	
        	
				  /**        ��ӿɼ�Ԫ������     	 **/
			sbJsOnkeyDown.append("openWindow(new Array('" + strVisibleElementNames + "'");

			for (int n=0;n<strHiddenElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementNames[n] + "'");
			}
			for (int n=0;n<strOtherElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**        ��ӿɼ�Ԫ�ض�Ӧ���ݿ��ֶ�����     	 **/
			sbJsOnkeyDown.append("new Array('" + strVisibleElementFields + "'");
			for (int n=0;n<strHiddenElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementFields[n] + "'");
			}
			for (int n=0;n<strOtherElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");

			/**			��ӿ���Ԫ�ص�ֵ		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
			/**			��ӿ���Ԫ�ض�Ӧ���ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
		
				/**			��ӿ���Ԫ�ض�Ӧ���ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlValues.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlValues[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlValues[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        
				/**			��ӿ���Ԫ�ض�Ӧ���ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			��ӿ���Ԫ�ض�Ӧ�������		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			��ӿ���Ԫ��֮����߼������		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strLogicOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strLogicOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strLogicOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**			��ӵ���������ʾ�ֶε�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
				  /**			��ӵ���������ʾ�ֶ�����		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
			sbJsOnkeyDown.append("'"+strWindowTitle+"', \n");
			sbJsOnkeyDown.append("'"+intListAnchorPosition+"', \n");
			sbJsOnkeyDown.append("'"+intSQL+"', \n");
			sbJsOnkeyDown.append("'"+strName+"', \n");
		
			/**			�����һ������λ��		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strNextFocusElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strNextFocusElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strNextFocusElementNames[n] + "'");
			}
			sbJsOnkeyDown.append(")) \n");

			
			if (blnShowOnly) strVisibleElementProperty +=" disabled";			//���ֻ����ʾ,��ô��Ϊdisabled
		/**
		 * ��ʾ����ı���
		 */
			if (blnShowOnly) strVisibleElementProperty +=" disabled";			//���ֻ����ʾ,��ô��Ϊdisabled
			for (int n=0;n<textNum;n++){
				sbOutputContent.append("<input type='text"+ 
				"' name='" + strVisibleElementNames + "Ctrl" + 					//���Ʒֳɶ���ı���,���Ƽ�Ctrl��׺
				"' value='" + 
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					if (n < (textNum-1)){
						sbOutputContent.append(" onFocus=\"nextfield='" + strVisibleElementNames + "Ctrl[" + (n+1) + "]'\" \n");
						sbOutputContent.append(" onKeyDown=\"handleKeyEvent('" + strVisibleElementNames + "Ctrl'," + n + ") \" \n" );
					}
					else{
						sbOutputContent.append(" onKeyDown=\"changeAccountNo('" + delims + "',document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl');if(event.keyCode==13)" + sbJsOnkeyDown.toString() + "; \n" );
						sbOutputContent.append("handleKeyEvent('" + strVisibleElementNames + "Ctrl'," + n + ") \" \n");
					}
					
				
					if (strHiddenElementNames!=null){
						sbOutputContent.append(" onKeyUp=\"");
						for (int i=0;i<strHiddenElementNames.length;i++){
							sbOutputContent.append("document.all.");
							sbOutputContent.append(strHiddenElementNames[i]+".value=''; \n");
							if (i<(strHiddenElementNames.length-1)){sbOutputContent.append(",");}
						}
						for (int i=0;i<strCtrlElementNames.length;i++){				//Ӱ��Ŵ󾵵�Ԫ�ص�У����ͳ�ʼֵ
							if (strCtrlElementNames[i]!=null && strCtrlElementNames[i].trim().length()>0 && !strCtrlElementNames[i].equals(strVisibleElementNames)){
								sbOutputContent.append("document.all.");
								sbOutputContent.append(strCtrlElementNames[i] + strVisibleElementNames);
								sbOutputContent.append(".value=''; \n");
							}
						}
					}
				}
				sbOutputContent.append("\">");
				if (n<(textNum-1)) sbOutputContent.append(delims);
			}
			sbOutputContent.append("<input type='hidden' name='" + strVisibleElementNames + "' value='" + strVisibleElementValues + "' \n" +
					"onpropertychange='splitAccount(\"" + delims + "\",document.all." + strVisibleElementNames + ",\"" + strVisibleElementNames + "Ctrl\")'> \n");
			sbOutputContent.append("<script language='javascript'> \n");			//д���ʻ���ʼֵ
			sbOutputContent.append("	splitAccount(document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl'); \n");
			sbOutputContent.append("</script> \n");
		/**
		 * ���ı�����ʾ����
		 */
			if (!blnShowOnly){
				sbOutputContent.append("<a href='#1'><img name='imgButton'" +
				" src='/websec/image/icon.gif' border=0 " +
				" onclick=\"changeAccountNo('" + delims + "',document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl');" + sbJsOnkeyDown.toString() + "\"></a> \n");
			}
			
			
			for (int n=0;n<strHiddenElementNames.length;n++){
				sbOutputContent.append("<input type='hidden' name='" + strHiddenElementNames[n] + 
				"' value='" + strHiddenElementValues[n] + "'> \n");
			}
		
			if (!blnShowOnly){
				for (int n=0;n<strCtrlElementNames.length;n++){				//Ӱ��Ŵ󾵵�Ԫ�ص�У����ͳ�ʼֵ
					if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
						sbOutputContent.append("<input type='hidden' name='" + 
								(strCtrlElementNames[n] + strVisibleElementNames) + 
								"' value='" + strCtrlElementValues[n] + "'> \n");
					}
				}
			}
			
			sbOutputContent.append("<script language='javascript'> \n");
			sbOutputContent.append("		splitAccount('" + delims + "',document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl') \n");
			sbOutputContent.append("</script> \n");
			
			if (strOtherElementNames != null && strOtherElementNames.length > 0){//Ӱ�����ĳ�ʼֵ��ֵ
				sbOutputContent.append("<script language='javascript'> \n");
				sbOutputContent.append("	setTimeout(\"");
				for (int n=0;n<strOtherElementNames.length;n++){
					if (strOtherElementNames[n]!=null && strOtherElementNames[n].trim().length()>0 
							&& strOtherElementValues[n]!=null && strOtherElementValues[n].length()>0){
						sbOutputContent.append("document.all."+strOtherElementNames[n].trim()+".value='"+strOtherElementValues[n]+"';");
					}
				}
				sbOutputContent.append("	\",100); \n");
				sbOutputContent.append("</script> \n");
			}
				
			out.println(sbOutputContent.toString());
		}
	
	
	
	
	/**
	 * ת��null��""
	 * @param str
	 * @return
	 */
	private static String convertNullToSpace(String str){
		return str == null?"":str;
	}
	/**
	 * ת���ַ������������nullΪ""
	 * @param str
	 * @return
	 */
	private static String[] convertNullToSpace(String[] str){
		String[] strArray = null;
		if (str!=null){
			for (int n=0;n<str.length;n++){
				str[n] = (str[n]==null?"":str[n]);
			}
			strArray = str;
		}
		else {strArray = new String[]{};}
		return strArray;
	}
    
	/**
	 * �ж�Ԫ�����ƺ����ݿ��ֶ��Ƿ�Գ�
	 * @param strElements
	 * @param strFields
	 * @return
	 */
	private static boolean isElementsMatchFields(String[] strElements,String[] strFields,boolean blnPermitNoMember){
		boolean blnMatch = false;
		if (!blnPermitNoMember){			//������Ϊnull
			if (strElements.length == strFields.length && strElements.length>0) blnMatch = true;
		}
		else{								//����Ϊnull
			if (strElements.length == strFields.length) blnMatch = true;
		}
	    
		return blnMatch;
	}
    
	/**
	 * build magnifier SQL
	 * @param strFields
	 * @param strCtrlFields
	 * @param strOperators
	 * @param strCtrlValues
	 * @param strLogics
	 * @param strTableName
	 * @return
	 */
	public String buildSql(String[] strFields,String[] strCtrlFields,String[] strOperators,String[] strCtrlValues,String[] strLogics,String strTableName){
		final String[] STR_SPECIAL_OPERATOR = {"like","between","in","not in","is","is not"};	//special operators
		final String STR_LOGIC_OPERATOR_OR  = "or";					//
		
		StringBuffer sbWhere = new StringBuffer(128);
		StringBuffer sbSql = new StringBuffer(256);
		
		Stack sckExpression = new Stack();
		Stack sckLogic  = new Stack();
		
		//����߼������û�д���,����Ĭ���߼�����,����ȫ��"and"
		if (strLogics == null || strLogics.length == 0){
			strLogics = new String[strOperators.length-1];
			for (int n=0;n<strLogics.length;n++){
				strLogics[n] = "and";
			}
		}
		
		
		String[] strCtrlExpression = new String[strCtrlFields.length];
		
		strFields = this.filterSameString(strFields);
		sbSql.append("select distinct \n");
		for (int n=0;n<strFields.length;n++){
			if (n==0) {sbSql.append(strFields[n]);}
			else{sbSql.append("," + strFields[n]);}
			}
		
		sbSql.append("\n from " + strTableName);
		sbSql.append(" where 1=1 \n");
		
		if (strCtrlFields!=null && strCtrlFields.length>0){
						//Integrate each expression , build "where" expression
			for (int n=0;n<strCtrlFields.length;n++){
				String strExpressionTemp = "";
				
				int intSpecialOperatorType = -1;		//special operator sign
				
				if (strCtrlValues[n]!=null && strCtrlValues[n].length()>0 && !strCtrlValues[n].equals("-1")){
					for (int i=0;i<STR_SPECIAL_OPERATOR.length;i++){
						if (strOperators[n].trim().toLowerCase().equals(STR_SPECIAL_OPERATOR[i])){
							intSpecialOperatorType = i;
							break;
							}//end if
						}//end for i
		
					if (intSpecialOperatorType >= 0){		//current operator is special operator
						switch (intSpecialOperatorType){
							case 0:{						//current operator is "like"
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " '%" +strCtrlValues[n] + "%'";
								break;
								}//end case
							case 1:{						//current operator is "between"
								strExpressionTemp = "(" + strCtrlFields[n] + " " + strOperators[n] + " " + strCtrlValues[n] + ")";
								break;
								}//end case
							case 2:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " (" + strCtrlValues[n] + ")";
								break;
								}//end case
							case 3:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " (" + strCtrlValues[n] + ")";
								break;
								}//end case
							case 4:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " " + strCtrlValues[n];
								break;
								}
							case 5:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " " + strCtrlValues[n];
								break;
								}
							}//end switch
						}//end if
					else{									//current operator is normal operator
						if (strCtrlFields[n].startsWith("_")){
							strExpressionTemp = "to_char(" + strCtrlFields[n].substring(1) + ",'yyyy-mm-dd') " + strOperators[n] + "'" + strCtrlValues[n] +"'";
							}
						else{
							strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " '" + DataFormat.reverseFormatAmount(strCtrlValues[n]) + "'";
							}
						}
					strCtrlExpression[n] = strExpressionTemp;
					}
				}//end for n
	
			sckExpression.push(strCtrlExpression[0]);		//push first expression
			for (int n=0;n<strLogics.length;n++){
				String strTemp = "";
				if (strLogics[n].toLowerCase().equals(STR_LOGIC_OPERATOR_OR)){

					String strFirstExp = (String)sckExpression.pop();
					String strSecondExp = strCtrlExpression[n+1];

					if (strFirstExp != null && strSecondExp != null){
						strTemp = "(" + (String)strFirstExp + " " + strLogics[n] + " " + strCtrlExpression[n+1] +")";
						sckExpression.push(strTemp);
						}
					else if (strFirstExp != null){
						strTemp = strFirstExp;
						sckExpression.push(strTemp);
						}
					else if (strSecondExp !=null){
						strTemp = strSecondExp;
						sckExpression.push(strTemp);
						}
					}//end if
				else{
					if (strCtrlExpression[n+1]!=null){
						sckExpression.push(strCtrlExpression[n+1]);
						}
					}
				}//end for n
			
			int intSize = sckExpression.size();
			for (int n=0;n<intSize;n++){
				String strExp = (String)sckExpression.pop();
				if (strExp !=null){
					if (sbWhere.toString() == null){
						sbWhere.append(strExp + " \n");
						}
					else{
						sbWhere.append(" and " + strExp + " \n");
						}
					}
				}
			}
		if (sbWhere.toString()!=null){
			sbSql.append(sbWhere.toString());
			}
			
		return sbSql.toString();
		}//end method buildSql
	
	/**
	 * ������ͬ���ַ���,���Դ�Сд
	 * @param str
	 * @return
	 */
	public static String[] filterSameString(String[] str){
		String[] strRtn = null;
		Stack s = new Stack();
		
		for (int n=0;n<str.length;n++){
			boolean hasSameStr = false;
			for (int i=n+1;i<str.length;i++){
				if (str[n].equalsIgnoreCase(str[i])){
					hasSameStr = true;
					break;
					}
				}//end for i
			if (!hasSameStr){
				s.push(str[n]);
				}
			}//end for n
		strRtn = new String[s.size()];
		for (int n=0;n<strRtn.length;n++){
			strRtn[n] = (String)s.pop();
			}//end for n
		return strRtn;
		}//end method filterSameString
	 /**
	 *
	 * @param sMainFields
	 * @param sReturnFields
	 * @param sDisplayFields
	 * @param sSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getCommonSelectList(String[] sMainFields,
		String[] sDisplayFields, String sSQL)
		throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(sSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				info = new CommonSelectInfo();
				//��ȡ�Ŵ󾵲�ѯ�����ֶ�
				Object[] oMainCols = new Object[sMainFields.length];
				for (int i = 0; i < sMainFields.length; i++) {
					oMainCols[i] = rs.getObject(sMainFields[i]);
				}

				//��Ҫ�ڷŴ�����ʾ���ֶ�
				Object[] oDisplayCols = new Object[sDisplayFields.length];
				for (int i = 0; i < sDisplayFields.length; i++) {
					oDisplayCols[i] = rs.getObject(sDisplayFields[i]); //<PK>
				}

				info.setMainCols(oMainCols);

				info.setDisplayCols(oDisplayCols);

				vResult.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("�������ݿ����");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception _ex) {
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}

		return vResult.size() > 0 ? vResult : null;
	}

	public static void showZoomCtrl(JspWriter out, String strMagnifierName,
			String strFormName, String strPrefix, String[] strMainNames,
			String[] strMainFields, String[] strReturnNames,
			String[] strReturnFields, String strReturnInitValues,
			String[] strReturnValues, String[] strDisplayNames,
			String[] strDisplayFields, int nIndex, String strMainProperty,
			String strSQL, String[] strMatchValue, String strNextControls,
			String strTitle, String strFirstTD, String strSecondTD,
			boolean blnIsOptional, boolean blnIsRateCtrl) throws Exception {
		String strButtonName = "button";
		try {
			//���Ŵ󾵲���
			//checkValue(strMainNames, strMainFields, true);
			//checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			//checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null
					|| strFormName.equals("") || strSQL == null
					|| strSQL.equals("")) {
				throw new Exception();
			}
			if (strNextControls == null) {
				throw new Exception();
			}
			if (strMatchValue == null)//|| strMatchValue.equals(""))
			{
				strMatchValue = new String[1];
				strMatchValue[0] = strMainFields[0];
			} else {
				if (strMatchValue[0] == null || strMatchValue[0].equals("")) {
					strMatchValue[0] = strMainFields[0];
				}
			}

			if (strFirstTD == null) {
				strFirstTD = "";
			}
			if (strSecondTD == null) {
				strSecondTD = "";
			}

			if (strReturnInitValues == null) {
				strReturnInitValues = "";
			}

			//������
			//����ǰ׺
			if (strPrefix != null && !strPrefix.trim().equals("")) {
				for (int i = 0; i < strMainNames.length; i++) {
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//�������ڵ�����
			String sFeatures = null;
			if (strDisplayNames.length < 3) {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			} else {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//���ɴ��ݸ��������ڵĲ����ַ���
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName="
					+ URLEncoder.encode(strMagnifierName);
			strParam += "&nIndex=" + nIndex;

			if (!isSQL(strSQL)) {
				strParam += "&strSQL= select * from ( '+" + strSQL
						+ "+' ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			} else {
				strParam += "&strSQL= select * from ( " + strSQL
						+ " ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			}

			if (strNextControls != null && !strNextControls.equals("")) {
				strParam += "&strNextControls=" + strNextControls;
			}

			for (int i = 0; i < strMainNames.length; i++) {
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}

			if (strReturnNames != null) {
				boolean bValue = false;
				if (strReturnValues != null
						&& strReturnValues.length == strReturnNames.length) {
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					//�����������
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue) {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\" value=\""
								+ strReturnValues[i] + "\">");
					} else {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\">");
					}
				}
			}

			for (int i = 0; i < strDisplayNames.length; i++) {
				//�����������
				strParam += "&strDisplayNames="
						+ URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//���ɲ�ѯ��ť���¼��ַ���
			String strTmp = "";

			/*  TOCONFIG��TODELETE  */
			/*
			 * ��Ʒ������������Ŀ 
			 * ninh 
			 * 2005-03-24
			 */

			//	            if(Env.getProjectName().equals("cpf"))//���⴦������
			//	            {
			//	                strTmp = "cpfLoan";
			//	            }
			//	            else
			//	            {
			//	                strTmp = "iTreasury-loan";
			//	            }
			strTmp = "iTreasury-loan";

			/*  TOCONFIG��END  */

			String sOnKeydown = "if(" + strFormName + "." + strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX + "/" + strTmp
					+ "/magnifier/ShowMagnifierZoom.jsp?" + strParam
					+ "', 'SelectAnything', '" + sFeatures + "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null) {
				for (int i = 0; i < strReturnNames.length; i++) {
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}

			int iPos = -1;
			//��ʾ�ؼ�
			if (iPos == -1) {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ "��&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
								+ sOnKeydown + "\"></td>");
			} else {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ ":&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
			}

			//blnIsOptional,�Ƿ��ѡ�����ժҪ���ֽ�����Ŵ���Ч��
			if (blnIsOptional == true) {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty + ">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty + "></td>");
				}
			} else {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\"></td>");
				}
			}

			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue == null) {
				out.println(" sql += \"\"; ");
			} else {
				if (strMatchValue.length == 1) {
					out.println(" sql +=  \" and " + strMatchValue[0]
							+ " like '" + URLEncoder.encode("%") + "\"+str+\""
							+ URLEncoder.encode("%") + "'\"; ");
				} else {
					out.println(" sql +=  \" and  ( \";");
					for (int i = 0; i < strMatchValue.length; i++) {
						if (i == 0) {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \"  " + strMatchValue[i]
										+ " like '" + URLEncoder.encode("%")
										+ "\"+str+\"" + URLEncoder.encode("%")
										+ "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						} else {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \" or  "
										+ strMatchValue[i] + " like '"
										+ URLEncoder.encode("%") + "\"+str+\""
										+ URLEncoder.encode("%") + "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						}
					}
					out.println(" sql +=  \" ) \";");
				}
				//					out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</SCRIPT> ");
		} catch (Exception exp) {
			throw exp;
		}
	}

	private static boolean isSQL(String strSQL) {
		String strTemp = strSQL.toLowerCase();
		int nIndex = strTemp.indexOf("select ");
		if (nIndex == -1) {
			return false;
		}
		nIndex = strTemp.indexOf(" from ");
		if (nIndex == -1) {
			return false;
		}
		return true;
	}
	/**
	 * ��ʾ֤ȯ�����ͷŴ�
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����
	 * @param out
	 * @param strLoanTypeID ֤ȯ����
	 * @param strFormName ��ҳ�������
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl(long lOfficeID, long lCurrencyID,
			JspWriter out, String strLoanTypeID, String strFormName,
			String strPrefix, String strMainProperty, String strNextControls)
			throws Exception {
		try {
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*===================="
					+ URLEncoder.encode("֤ȯ�����ͷŴ�") + "=================*/");
			out.println("function " + strPrefix
					+ "getSubLoanTypeSQL(nOfficeID,lLoanTypeID)");
			out.println("{");
			out
					.println("	var sql = \"select a.ID, a.LoanTypeID, a.Code, a.Name \";");
			out
					.println("	sql += \" from sec_secTypeSetting a, sec_secTypeRelation b \";");
			out
					.println("	sql += \" where a.ID = b.subLoanTypeID and a.statusID = 1 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and b.officeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("	if (lLoanTypeID > 0) ");
			out.println("	{");
			out.println("		sql += \" and a.loanTypeID = \" + lLoanTypeID; ");
			out.println("	}");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			String strMagnifierName = "ҵ��������";
			String[] strMainNames = { "txtSubLoanTypeName",
					"txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID", "hidLoanTypeID" };
			String[] strReturnFields = { "ID", "LoanTypeID" };
			String[] strReturnValues = { "-1", "-1" };
			String[] strDisplayNames = { "ҵ������������", "ҵ�������ͱ���" };
			String[] strDisplayFields = { "Name", "Code" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","
					+ strFormName + "." + strPrefix + strLoanTypeID + ".value)";

			showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
					strMainNames, strMainFields, strReturnNames,
					strReturnFields, "", strReturnValues, strDisplayNames,
					strDisplayFields, nIndexOffice, strMainProperty, strSQL,
					"", strNextControls, strMagnifierName, "", "");
		} catch (Exception exp) {
			throw exp;
		}
	}

	/**
	 * ��ʾ��ͨ�Ŵ�
	 * @param JspWriter out
	 * @param String strMagnifierName �Ŵ󾵵�����
	 * @param String strFormName ��ҳ�������
	 * @param strPrefix strPrefix �ؼ�����ǰ׺
	 * @param String[] strMainNames �Ŵ󾵻�����λֵ�б�
	 * @param String[] strMainFields �Ŵ󾵻�����λ��Ӧ�ı���ֶ�
	 * @param String[] strReturnNames �Ŵ󾵷���ֵ�б�(����ֵ)
	 * @param String[] strReturnFields �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ı���ֶ��б�
	 * @param String   strReturnInitValues �Ŵ󾵻�����λ��Ӧ�ĳ�ʼֵ
	 * @param String[] strReturnValues �Ŵ󾵷���ֵ(����ֵ)��Ӧ�ĳ�ʼֵ
	 * @param String[] strDisplayNames �Ŵ�С������ʾ����λ����
	 * @param String[] strDisplayFields �Ŵ�С������ʾ��λ��Ӧ�ı���ֶ�
	 * @param nIndex ȷ��ѡ����,��0��ʼ,���С��0,��Ĭ��������λ����ѡ��,������ڻ����strDisplayNames.length,��û��ѡ����
	 * @param strMainProperty �Ŵ���λ����
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strMatchValue �Ŵ�Ҫģ��ƥ����ֶ�
	 * @param strNextControls ������һ������
	 * @param strTitle ��λ����
	 * @param strFirstTD ��һ��TD������
	 * @param strSecondTD �ڶ���TD������ 
	 * @throws Exception
	 */
	public static void showZoomCtrl(JspWriter out, String strMagnifierName,
			String strFormName, String strPrefix, String[] strMainNames,
			String[] strMainFields, String[] strReturnNames,
			String[] strReturnFields, String strReturnInitValues,
			String[] strReturnValues, String[] strDisplayNames,
			String[] strDisplayFields, int nIndex, String strMainProperty,
			String strSQL, String strMatchValue, String strNextControls,
			String strTitle, String strFirstTD, String strSecondTD)
			throws Exception {
		//2004-11-18 ģ��ƥ��֧�ֶ��ֶ�ƥ��
		String[] strMatchValues = new String[1];
		strMatchValues[0] = strMatchValue;

		showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
				strMainNames, strMainFields, strReturnNames, strReturnFields,
				strReturnInitValues, strReturnValues, strDisplayNames,
				strDisplayFields, nIndex, strMainProperty, strSQL,
				strMatchValues, strNextControls, strTitle, strFirstTD,
				strSecondTD, false, false);
	}
	/**
	 * ��ʾ�������Ŵ�
	 * @param lOfficeID ���´�ID
	 * @param lCurrencyID ����
	 * @param out
	 * @param lApprovalID ������ID
	 * @param strFormName ��ҳ�������
	 * @param strControlName ��ҳ��ؼ����� 
	 * @param strPrefix �ؼ�����ǰ׺
	 * @param strMainProperty �Ŵ���λ����
	 * @param strReturnName �Ŵ���������ֵ
	 * @param strSQL �Ŵ󾵲�ѯSQL���
	 * @param strNextControls ������һ������
	 * @throws Exception
	 */
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String strNextControls) throws Exception
	{
		try
		{
			//���SQL��ҳ��
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("��ʾ�������Ŵ�") + "=================*/");
			out.println("function " + strPrefix + "getApprovalSettingSQL(nOfficeID,lApprovalID,sname)");
			out.println("{");
			out.println("	var sql = \"select ID, sName \";");
			out.println("	sql += \" from loan_approvalSetting \";");
			out.println("	sql += \" where nStatusID = 2 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and nOfficeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("   if (lApprovalID > 0)");
			out.println("	{");
			out.println("		sql += \" and ID = \" + lApprovalID; ");
			out.println("	}");
			out.println("   if (sname != null && sname != \"\")");
			out.println("   {");
			out.println("       sql += \" and sname like '%\" + sname + \"%'\"");
			out.println("   }");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			String strMagnifierName = "������";
			String[] strMainNames = { strControlName };
			String[] strMainFields = { "sName" };
			if(strReturnName.equals(""))
			{
				strReturnName = "hidApprovalID";
			}
			String[] strReturnNames = { strReturnName };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { "-1" };
			String strReturnInitValues = "";
			String[] strDisplayNames = { URLEncoder.encode("���������"), URLEncoder.encode("����������") };
			String[] strDisplayFields = { "ID", "sName" };
			String strMatchValue = "";
			int nIndexOffice = 0;
			String name = DataFormat.toChinese(strFormName+"."+strControlName+".value");
			String strSQL = strPrefix + "getApprovalSettingSQL(" + lOfficeID + ","  + lApprovalID + "," + name + ")";
						
			SECMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnInitValues,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndexOffice,
				strMainProperty,
				strSQL,
				strMatchValue,
				strNextControls,
				strMagnifierName,
				"",
				"");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
}