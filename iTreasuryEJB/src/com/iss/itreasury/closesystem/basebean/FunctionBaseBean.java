/*
 * Created on 2004-2-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem.basebean;
import java.sql.Connection;
import java.sql.Timestamp;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class FunctionBaseBean
{ 
	/**
				* ������Ϣ  
				* @param lOfficeID ���´���ʶ
				* @param lCurrencyID ���ֱ�ʶ  
				* @param tsDate ��/�ػ�ʱ�� 
				* Description: 
				* ----��ȷ��
				*/
	public boolean calculateInterest(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
				* ҵ��У��  
				* @param lOfficeID ���´���ʶ
				* @param lCurrencyID ���ֱ�ʶ  
				* @param tsDate ��/�ػ�ʱ�� 
				*/
	public boolean checkTransaction(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
				* ���մ��� 
				* @param lOfficeID ���´���ʶ
				* @param lCurrencyID ���ֱ�ʶ  
				* @param tsDate ��/�ػ�ʱ�� 
				* Description: 
				* ------��ȷ��
				*/
	public boolean endEveryDay(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}	
	/**
				* ��ȡ���� 
				* @param lOfficeID ���´���ʶ
				* @param lCurrencyID ���ֱ�ʶ  
				* @param tsDate ��/�ػ�ʱ�� 
				* Description: 
				* ------��ȷ��
				*/
	public boolean extractPlanData(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
					* ��ϴ���������� 
					* @param lOfficeID ���´���ʶ
					* @param lCurrencyID ���ֱ�ʶ  
					* @param tsDate ��/�ػ�ʱ�� 
					* Description: 
					* ------��ȷ��
					*/
	public boolean launderPlanData(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* ���ÿ�/�ػ�����״̬
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lStatusID ״̬��ʶ
	*/
	public  boolean setDealStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		return true;
	}
	/**
	* ȡ�ÿ�/�ػ�����״̬
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* return ����״̬
	*/
	public  long getDealStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		return -1;
	}
	/**
	* ����ϵͳ״̬
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ
	* @param strMessage �ػ���Ϣ 
	*/
	public  boolean setSystemStatusID(long lOfficeID, long lCurrencyID, long lStatusID) throws Exception
	{
		return true;
	
	}
	/**
	* ȡ��ϵͳ״̬
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* return ����״̬
	*/
	public  long getSystemStatusID(long lOfficeID, long lCurrencyID) throws Exception
	{
		return -1;
	}
	/**
			* ����ϵͳʱ��
			* @param lOfficeID ���´���ʶ
			* @param lCurrencyID ���ֱ�ʶ
			* @param lModelID ģ���ʶ
			* @param strMessage �ػ���Ϣ 
			*/
	public  boolean setSystemDate(long lOfficeID, long lCurrencyID, Timestamp tsSystemDate) throws Exception
	{
		return true;
	}
	/**
	* ȡ��ϵͳʱ��
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ
	* @param lModelID ģ���ʶ 
	* return ����״̬
	*/
	public  Timestamp getSystemDate(long lOfficeID, long lCurrencyID) throws Exception
	{
		return null;
	}
	/**
			* �����޸�ϵͳʱ�䡢״̬
			* @param lOfficeID ���´���ʶ
			* @param lCurrencyID ���ֱ�ʶ  
			* @param lSystemStatusID ��/�ػ�ʱ�� 
			* @param tsDate ��/�ػ�ʱ�� 
			* Description:  
			*/
	public boolean setSystemStatusAndDate(Connection conn, long lOfficeID, long lCurrencyID, long lSystemStatusID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
				* ������������
				* @param lOfficeID ���´���ʶ
				* @param lCurrencyID ���ֱ�ʶ
				* @param lModelID ģ���ʶ
				* @param strMessage �ػ���Ϣ 
				*/
	public boolean openOtherProcess(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
					* �ػ���������
					* @param lOfficeID ���´���ʶ
					* @param lCurrencyID ���ֱ�ʶ
					* @param lModelID ģ���ʶ
					* @param strMessage �ػ���Ϣ 
					*/
	public boolean closeOtherProcess(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	
	/**
	* Э�����У��  
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ  
	* @param tsDate ��/�ػ�ʱ�� 
	*/
	public boolean checkNegotiate(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* �����˻�У��  
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ  
	* @param tsDate ��/�ػ�ʱ�� 
	*/
	public boolean checkCurrentAcount(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* ��ͬ��ʼ����  
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ  
	* @param tsDate ��/�ػ�ʱ�� 
	*/
	public boolean setSystemContractDate(Connection conn, long lOfficeID, long lCurrencyID,long lSystemStatusID, Timestamp tsDate) throws Exception
	{
		return true;
	}
	/**
	* �����Զ�����ҵ����
	* @param lOfficeID ���´���ʶ
	* @param lCurrencyID ���ֱ�ʶ  
	* @param tsDate ��/�ػ�ʱ�� 
	*/
	public boolean fixedDepositAutoContinue(Connection conn, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return true;
	}
}
