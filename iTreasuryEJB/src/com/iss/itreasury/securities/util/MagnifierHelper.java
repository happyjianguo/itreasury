/*
 * Created on 2004-4-1
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.util;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MagnifierHelper
{
	
	/**���׶�������,��ʱֻ�õ�����**/
	public static final long COUNTERPART_TYPE_BANK 						= 0;	//������
	public static final long COUNTERPART_TYPE_BANKOFDEPOSIT 			= 1;	//����Ӫҵ��
	public static final long COUNTERPART_TYPE_FUNDMANAGEMENTCO 			= 2;	//�����������˾
	public static final long COUNTERPART_TYPE_ISINTERBANKCOUNTERPART 	= 3;	//���м佻�׶���
	public static final long COUNTERPART_TYPE_ISSECURITIESUNDERWRITER 	= 4;	//ծȯ������
	
	/**��������**/
	public static final long COUNTERPART_TYPE_ISSECURITYCO 				= 5;	//ί�����ȯ��
	public static final long COUNTERPART_TYPE_ISINVESTEDCORPORATION 	= 6;	//��Ͷ����ҵ
	public static final long COUNTERPART_TYPE_ISOWNERSHIPTRANSFER 		= 7;	//��Ȩ���÷�
	public static final long COUNTERPART_TYPE_ISFLOATERS 				= 8;	//ծȯ������
	public static final long COUNTERPART_TYPE_ISCONSIGNER 				= 9;	//�������ί�з�
	public static final long COUNTERPART_TYPE_ISPOLICYHOLDER 			= 10;	//Ͷ����  */
	
	
	private static String[] counterpartTypeFields = new String[11];		//�����ֶ�
	
	static{
		counterpartTypeFields[0] 	= "IsBank";							//������
		counterpartTypeFields[1] 	= "IsBankOfDeposit";				//����Ӫҵ��
		counterpartTypeFields[2] 	= "IsFundManagementCo";				//�����������˾
		counterpartTypeFields[3] 	= "IsInterBankCounterpart";			//���м佻�׶���
		counterpartTypeFields[4] 	= "IsSecuritiesUnderwriter";		//ծȯ������
		counterpartTypeFields[5] 	= "IsSecurityCo";					//ί�����ȯ��
		counterpartTypeFields[6] 	= "IsInvestedCorporation";			//��Ͷ����ҵ
		counterpartTypeFields[7] 	= "IsOwnershipTransfer";			//��Ȩ���÷�
		counterpartTypeFields[8]	= "IsFloaters";						//ծȯ������
		counterpartTypeFields[9] 	= "IsConsigner";					//�������ί�з�
		counterpartTypeFields[10] 	= "IsPolicyHolder";					//Ͷ����
	}

	
	/**
	 * �ý������ͻ����Ӧ�Ľ��׶������͵��ֶ���
	 * ����,ĳ�������͵����еĽ��׶��ֵ�����Ϊ"������"��"���м佻�׶���"
	 * �򷵻��ַ�������{"IsBank","IsInterBankCounterpart"}
	 * ����������ʹ���������Ͳ��߱����׶�������,�򷵻� ""
	 * @param transactionTypeId
	 * @return
	 */
	public static String[] getCounterpartTypeFields(long transactionTypeId)throws SecuritiesException{
		String[] strFields = null;
		long[] counterpartTypeIds = getCounterpartTypeIdsByTransactionTypeId(transactionTypeId);
		
		int counter = 0;
		for (int n=0;n<counterpartTypeIds.length;n++){					//ƴsql
			if (counterpartTypeIds[n] == SECConstant.TRUE){
				counter++;
			}
		}
		strFields = new String[counter];
		counter = 0;
		for (int n=0;n<counterpartTypeIds.length;n++){
			if (counterpartTypeIds[n] == SECConstant.TRUE){
				strFields[counter] = getCounterpartTypeField(n);
				counter++;
			}
		}
		return strFields;
	}

	/**
	 * ��ý��׶�����Ӧ���ֶ���
	 * @param counterpartTypeId
	 * @return
	 */
	public static String getCounterpartTypeField(long counterpartTypeId){
		return counterpartTypeFields[(int)counterpartTypeId];
	}
	
	/**
	 * ͨ���������ͻ�øý������͵Ľ��׶������ʹ���
	 * @param transactionTypeId		�������ʹ���
	 * @return
	 */
	public static long[] getCounterpartTypeIdsByTransactionTypeId(long transactionTypeId)throws SecuritiesException{
		long[] returnVal = new long[12];
		
		if (!(transactionTypeId>=0 && transactionTypeId<=11) && String.valueOf(transactionTypeId).length()<2){
			throw new SecuritiesException("���׶��ִ���Ŵ󾵴��뽻�����ʹ������",null);
		}
		
		for(int n=0;n<returnVal.length;n++){
			returnVal[n] = SECConstant.FALSE;
		}
		
		//��ý�������ǰ��λ����
		int intTransTypeId = -1;
		if (!(transactionTypeId>=0 && transactionTypeId<=11)){				//���������ǽ��׺�,���մ����ֵ���ؽ��׶�������
			intTransTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		}
		else{
			intTransTypeId = (int)transactionTypeId;
		}
		
		switch (intTransTypeId){
			case 0:{																//0.ȫ��
				for (int n=0;n<11;n++){
					returnVal[n] = SECConstant.TRUE;
				}
				break;
			}
			case (int)(COUNTERPART_TYPE_BANK+1):{
				returnVal[(int)COUNTERPART_TYPE_BANK] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_BANKOFDEPOSIT+1):{
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_FUNDMANAGEMENTCO+1):{
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISINTERBANKCOUNTERPART+1):{
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISSECURITIESUNDERWRITER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISSECURITYCO+1):{
				returnVal[(int)COUNTERPART_TYPE_ISSECURITYCO] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISINVESTEDCORPORATION+1):{
				returnVal[(int)COUNTERPART_TYPE_ISINVESTEDCORPORATION] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISOWNERSHIPTRANSFER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISOWNERSHIPTRANSFER] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISFLOATERS+1):{
				returnVal[(int)COUNTERPART_TYPE_ISFLOATERS] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISCONSIGNER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISCONSIGNER] = SECConstant.TRUE;
				break;
			}
			case (int)(COUNTERPART_TYPE_ISPOLICYHOLDER+1):{
				returnVal[(int)COUNTERPART_TYPE_ISPOLICYHOLDER] = SECConstant.TRUE;
				break;
			}
			
			case 12:{
				for (int n=0;n<11;n++){
					returnVal[n] = SECConstant.TRUE;
				}
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.FALSE;
				break;
			}
			
			//---------------------------------------��ҵ�����ͷ�
			case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{					//1.�ʽ���
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{				//2.��Ʊһ�������깺
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{						//3.��Ʊһ�������깺
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{				//4.��Ʊ����
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{			//5.����Ʊ��һ��
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{	//6.����Ʊ�ݶ���
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{			//7.���м�ծȯ�ع�
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{	//8.������ծȯ�ع�
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{			//9.���м��ծһ��
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{	//10.���м��ծ����
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}	
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{//11.��������ծһ��
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{//12.��������ծ����
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{				//13.����ծһ��
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{		//14.����ծ����
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{//15.�����Խ���ծһ��
				returnVal[(int)COUNTERPART_TYPE_ISSECURITIESUNDERWRITER] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{//16.�����Խ���ծ����
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{				//17.��ҵծһ��
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{		//18.��ҵծ����
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{	//19.��תծһ�������깺
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{			//20.��תծһ�������깺
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{	//21.��תծ����
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{					//22.ծת��
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{		//23.���ʽ����һ�������깺
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{				//24.���ʽ����һ�������깺
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{		//25.���ʽ�������
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{			//26.����ʽ����һ���Ϲ�
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{					//27.����ʽ��������깺
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{				//28.����ʽ����������
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{		//29.����ʽ����ֺ�
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			//---------------------------����ӵ�
			case (int)SECConstant.BusinessType.CAPITAL_TRANSFER.ID:{				//30.�ʽ𻮲�
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.INTEREST_SETTLEMENT.ID:{				//31.�ʽ���Ϣ����
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{				//32.֤ȯ��ת
				returnVal[(int)COUNTERPART_TYPE_BANKOFDEPOSIT] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.CAPITAL_REPURCHASE.ID:{				//33.�ʲ��ع�
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID:{		//34.�ṹ��Ͷ��
				returnVal[(int)COUNTERPART_TYPE_ISINTERBANKCOUNTERPART] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{				//35.ί�����
				returnVal[(int)COUNTERPART_TYPE_ISSECURITYCO] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{				//36.ծȯ����
				returnVal[(int)COUNTERPART_TYPE_ISFLOATERS] = SECConstant.TRUE;
				break;
			}
			case (int)SECConstant.BusinessType.FUND_TRANSFER.ID:{					//37.����ת
				returnVal[(int)COUNTERPART_TYPE_FUNDMANAGEMENTCO] = SECConstant.TRUE;
				break;
			}
			
		}
		
		return returnVal;
	}
	
	//---------------------------------------------------------------------------
	
	/**
	 * ֤ȯ�����Ӧ�ֶ�,�׷��Ͷ���������
	 */
	private static String[] securitiesCodes = new String[4];
	static{
		securitiesCodes[0] = "SECURITIESCODE1";							//�׷�����
		securitiesCodes[1] = "SECURITIESCODE2";							//��������
		securitiesCodes[2] = "SECURITIESCODE3";							//��������
		securitiesCodes[3] = "SECURITIESCODE4";							//��������
	}
	
	
	public MagnifierHelper(){
		super();
	}
	
	/**
	 * ����ҵ������ID���֤ȯ������Ҫ���ֶ�
	 * @param transactionTypeId
	 * @return
	 */
	public static String getSecuritiesCodeField(long transactionTypeId) throws SecuritiesException{
		String returnVal = "";
		if (!(transactionTypeId>=0 && transactionTypeId<5) && String.valueOf(transactionTypeId).length()<2){
			throw new SecuritiesException("֤ȯ����Ŵ󾵴��뽻�����ʹ������",null);
		}
		
		int intTransactionTypeId = -1;
		if (transactionTypeId > 11){
			intTransactionTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		}
		else{
			intTransactionTypeId = (int)transactionTypeId;
		}
		
		switch (intTransactionTypeId){
			/*case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{					//1.�ʽ���
				returnVal = securitiesCodes[0];
				break;
			}*/
			case 0:{
				returnVal = securitiesCodes[0];
				break;
			}
			case 1:{
				returnVal = securitiesCodes[0];
				break;
			}
			case 2:{
				returnVal = securitiesCodes[1];
				break;
			}
			case 3:{
				returnVal = securitiesCodes[2];
				break;
			}
			case 4:{
				returnVal = securitiesCodes[3];
				break;
			}
				
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{				//2.��Ʊһ�������깺
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{						//3.��Ʊһ�������깺
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{				//4.��Ʊ����
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{			//5.����Ʊ��һ��
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{	//6.����Ʊ�ݶ���
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{			//7.���м�ծȯ�ع�
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{	//8.������ծȯ�ع�
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{			//9.���м��ծһ��
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{	//10.���м��ծ����
				returnVal = securitiesCodes[1];
				break;
			}	
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{//11.��������ծһ��
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{//12.��������ծ����
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{				//13.����ծһ��
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{		//14.����ծ����
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{//15.�����Խ���ծһ��
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{//16.�����Խ���ծ����
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{				//17.��ҵծһ��
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{		//18.��ҵծ����
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{	//19.��תծһ�������깺
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{			//20.��תծһ�������깺
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{	//21.��תծ����
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{					//22.ծת��
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{		//23.���ʽ����һ�������깺
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{				//24.���ʽ����һ�������깺
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{		//25.���ʽ�������
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{			//26.����ʽ����һ���Ϲ�
				returnVal = securitiesCodes[0];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{					//27.����ʽ��������깺
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{				//28.����ʽ����������
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{		//29.����ʽ����ֺ�
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{				//30.֤ȯ��תXXXX
				returnVal = securitiesCodes[1];
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{				//31.ί�����
				returnVal = securitiesCodes[1]; 
				break;
			}
			case (int)SECConstant.BusinessType.FUND_TRANSFER.ID:{					//32.����ת
				returnVal = securitiesCodes[1]; 
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{				//33.ծȯ����
				returnVal = securitiesCodes[0]; 
				break;
			}
		}
		return returnVal;
	}
	
	//-----------------------------------------------------------------------------
	
	
	/**
	 * ��������Id�Ķ��ŷָ��ƴ��
	 */
	public static String getSecuritiesTypeIds(long transactionTypeId , long currencyId,long typeId)throws SecuritiesException{
		String ids = "";
		
		long[] securitiesIds = getSecuritiesTypeId(transactionTypeId , currencyId,typeId);
		
		if (securitiesIds!=null && securitiesIds.length>0){
			for (int n=0;n<securitiesIds.length;n++){
				Log.print("����"+n+":"+securitiesIds[n]);
				if (n==0){
					ids = String.valueOf(securitiesIds[n]);
				}
				else{
					ids += "," + securitiesIds[n];
				}
			}
		}
		
		return ids;
	}
	
	/**
	 * ͨ���������ͺͱ���ȷ����ǰ��֤ȯ����
	 */
	public static long[] getSecuritiesTypeId(long transactionTypeId , long currencyId,long typeId)throws SecuritiesException{
		long[] securitiesTypeId = null;
		if (!(transactionTypeId>=0 && transactionTypeId<5) && String.valueOf(transactionTypeId).length()<2){
			throw new SecuritiesException("֤ȯ����Ŵ󾵴��뽻�����ʹ������",null);
		}
		int intTransactionTypeId = -1;
		
		if (transactionTypeId > 10){
			intTransactionTypeId = Integer.parseInt(String.valueOf(transactionTypeId).substring(0,2));
		}
		else{
			intTransactionTypeId = (int)transactionTypeId;
		}
		
		switch (intTransactionTypeId){
			/*case (int)SECConstant.BusinessType.CAPITAL_LANDING.ID:{					//1.�ʽ���
				returnVal = securitiesCodes[0];
				break;
			}*/
			case 0:{																//����֤ȯ����
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 1:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 2:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 3:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case 4:{
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID_ONLINE.ID:{				//2.��Ʊһ�������깺
				if (currencyId == Constant.CurrencyType.RMB){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				else if (currencyId == Constant.CurrencyType.USD){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_B;
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_BID.ID:{						//3.��Ʊһ�������깺
				if (currencyId == Constant.CurrencyType.RMB){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				else if (currencyId == Constant.CurrencyType.USD){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_B;
				}
				break;
			}
			case (int)SECConstant.BusinessType.STOCK_TRANSACTION.ID:{				//4.��Ʊ����
				if (currencyId == Constant.CurrencyType.RMB){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				else if (currencyId == Constant.CurrencyType.USD){
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_B;
				}
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.ID:{			//5.����Ʊ��һ��
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.CENTRAL_BANK_NOTE;
				break;
			}
			case (int)SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.ID:{	//6.����Ʊ�ݶ���
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.CENTRAL_BANK_NOTE;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_BOND_REPURCHASE.ID:{			//7.���м�ծȯ�ع�**
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.BANK_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.ID:{	//8.������ծȯ�ع�
				securitiesTypeId = new long[2];
				securitiesTypeId[0] = SECConstant.SecuritiesType.EXCHANGECENTER_BOND_REPURCHASE;
				securitiesTypeId[1] = SECConstant.SecuritiesType.EXCHANGECENTER_ENTERPRISE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.ID:{			//9.���м��ծһ��
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.BANK_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.ID:{	//10.���м��ծ����
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.BANK_NATIONAL_BOND;
				break;
			}	
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.ID:{//11.��������ծһ��
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.EXCHANGECENTER_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID:{//12.��������ծ����
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.EXCHANGECENTER_NATIONAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_BID.ID:{				//13.����ծһ��
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.ID:{		//14.����ծ����
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_BID.ID:{//15.�����Խ���ծһ��
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.POLICY_RELATED_FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID:{//16.�����Խ���ծ����
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.POLICY_RELATED_FINANCIAL_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_BID.ID:{				//17.��ҵծһ��
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENTERPRISE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.ID:{		//18.��ҵծ����
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENTERPRISE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.ID:{	//19.��תծһ�������깺
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.ID:{			//20.��תծһ�������깺
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.ID:{	//21.��תծ����
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				break;
			}
			case (int)SECConstant.BusinessType.DEBT_TO_EQUITY.ID:{					//22.ծת��********
				if (typeId == 0){					//����һ��תծ��ȯ
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.TRANSFORMABLE_BOND;
				}
				else if (typeId == 1){				//���Ͷ�A��
					securitiesTypeId = new long[1];
					securitiesTypeId[0] = SECConstant.SecuritiesType.STOCK_A;
				}
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.ID:{		//23.���ʽ����һ�������깺
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENCLOSED_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_BID.ID:{				//24.���ʽ����һ�������깺
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENCLOSED_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.ID:{		//25.���ʽ�������
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENCLOSED_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.ID:{			//26.����ʽ����һ���Ϲ�
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_BID.ID:{					//27.����ʽ��������깺
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_REDEEM.ID:{				//28.����ʽ����������
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.ID:{		//29.����ʽ����ֺ�
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.SECURITIES_TRANSFER.ID:{				//30.֤ȯ��תXXXX
				//securitiesTypeId = SECConstant.SecuritiesType.getAllCode();
                /**
                 * ��ҵ��ĽǶȿ���֤ȯ��ת�Ķ����ǽ�������ȯ������������ʽ�����ծȯ�ع�����������ȫ�������͵�֤ȯ��
                 * ��˲��ܵ���SECConstant.SecuritiesType.getAllCode()������
                 * �¼�һ������ȡ����Ӧ��֤ȯ���ͣ�SECConstant.SecuritiesType.getExchangeCode()��
                 */
                
                securitiesTypeId = SECConstant.SecuritiesType.getExchangeCode();
                 
				Log.print("��ǰҵ����֤ȯ��ת,֤ȯ���ʹ�СΪ:"+securitiesTypeId.length);
				break;
			}
			case (int)SECConstant.BusinessType.ENTRUST_FINANCING.ID:{				//31.ί�����
				securitiesTypeId = SECConstant.SecuritiesType.getAllCode(); 
				break;
			}
			case (int)SECConstant.BusinessType.FUND_TRANSFER.ID:{					//32.����ת
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.MUTUAL_FUND;
				break;
			}
			case (int)SECConstant.BusinessType.BOND_UNDERWRITING.ID:{				//33.ծȯ����
				securitiesTypeId = new long[1];
				securitiesTypeId[0] = SECConstant.SecuritiesType.ENTERPRISE_BOND;
				break;
		}
			
		}
		return securitiesTypeId;
	}
	
	public static void main(String[] args)throws Exception{
		/*String[] str = MagnifierHelper.getCounterpartTypeFields(3101);
		if (str!=null){
			for (int n=0;n<str.length;n++){
				Log.print(str[n]);
			}
		}*/
		Log.print(MagnifierHelper.getCounterpartTypeField(4));
	}
}
