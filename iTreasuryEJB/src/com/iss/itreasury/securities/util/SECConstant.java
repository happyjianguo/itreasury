/*
 * Created on 2003-8-7
 *  
 */
package com.iss.itreasury.securities.util;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.dao.SEC_TransactionTypeDAO;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.util.Log;
/** 
 * ����������ϵͳ���еĳ���
 * 
 * @author wlming
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class SECConstant extends com.iss.itreasury.util.Constant
{

	/*ҵ������
	 * ҵ�����ʱȽ����⣬��ҵ�����ʱ���ֻ���������ƣ�û�ж���ID�����ͨ�����Ʒ��س����ж����ID
	 * */
	public static class BusinessAttribute
	{
		public static final long INTER_BANK = 1; //1,���м�ҵ��
		public static final long INTER_EXCHANGECENTER = 2; //2��������ҵ��
		public static final long OPENFUND = 3; //3������ʽ����ҵ��
		public static final long CONTRACTS = 4; //4����ͬҵ��
		public static final long OTHERS = 5; //5������ҵ��
		
		public static final long getIDByName(String name)
		{
			if (name.compareToIgnoreCase("���м�ҵ��") == 0)
				return INTER_BANK;
			else if (name.compareToIgnoreCase("������ҵ��") == 0)
				return INTER_EXCHANGECENTER;
			else if (name.compareToIgnoreCase("����ʽ����ҵ��") == 0)
				return OPENFUND;
			else if (name.compareToIgnoreCase("��ͬҵ��") == 0)
				return CONTRACTS;
			else if (name.compareToIgnoreCase("����ҵ��") == 0)
				return OTHERS;
			return -1;
		}
		public static final long[] getCodeForApprovalSetting(){
			return new long[]{INTER_BANK,INTER_EXCHANGECENTER};
		}
		public static final long[] getAllCode(){
			return new long[]{INTER_BANK,INTER_EXCHANGECENTER,OPENFUND,CONTRACTS,OTHERS};
		}
		public static final long[] getCodeForApproval(){
			return new long[]{INTER_BANK,INTER_EXCHANGECENTER,CONTRACTS};
		}
		
		public static final String getName(long ID){
			if (ID==INTER_BANK)
				return "���м�ҵ��";
			else if (ID==INTER_EXCHANGECENTER)
				return "������ҵ��";
			else if (ID==OPENFUND)
				return "����ʽ����ҵ��";
			else if (ID==CONTRACTS)
				return "��ͬҵ��";
			else if (ID==OTHERS)
				return "����ҵ��";
			
			return "-1";
		}
		public static final void showList(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long[] lArrayID){
			
			String[] strArrayName = null;
			try
			{
				//lArrayID = getAllCode(lOfficeID,lCurrencyID);
				//lArrayID = getAllSetLoanTypeID(lOfficeID,lCurrencyID);
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	/*ҵ������״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class BusinessAttributeStatus
	{
		public static final long DELETED = 0; //0,��ɾ����
		public static final long SAVED = 2; //2���ѱ���
		public static final long CHECKED = 3; //3���Ѹ���
	}
	/*��ע״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class RemarkStatus extends BusinessAttributeStatus
	{
	}
	/*��Ŀ����״̬
	 0=��ɾ��
	 3=�ѱ��� */
	public static class EntrySubjectStatus extends BusinessAttributeStatus
	{
	}
	/*�����Ŀ����״̬
	 0=��ɾ��
	 3=�ѱ��� */
	public static class AccountTypeStatus extends BusinessAttributeStatus
	{
	}
	/*֤����״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class ExchangeCenterStatus extends BusinessAttributeStatus
	{
	}
	/*ҵ������״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class BusinessTypeStatus extends BusinessAttributeStatus
	{
	}
	/*����״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class SettingStatus extends BusinessAttributeStatus
	{
	}
	/*֤ȯ״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class SecuritiesStatus extends BusinessAttributeStatus
	{
	}
	/*��������״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class TransactionTypeStatus extends BusinessAttributeStatus
	{
	}
	/*֤ȯ���״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class SecuritiesTypeStatus extends BusinessAttributeStatus
	{
	}
	/*���׶���״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class CounterpartStatus extends BusinessAttributeStatus
	{
	}
	/*�ɶ��ʻ�״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class StockHolderAccountStatus extends BusinessAttributeStatus
	{
	}
	/*�ʽ��ʻ�״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class AccountStatus extends BusinessAttributeStatus
	{
	}
	/*֤ȯ����״̬
	  0=��ɾ��
	  2=�ѱ���
	  3=�Ѹ��� */
	public static class SecuritiesMarketStatus extends BusinessAttributeStatus
	{
	}
	/*�ո�����
	  1=��
	  2=�� */
	public static class Direction
	{
		public static final long RECEIVE = 1; //�ʽ��ʻ���
		public static final long PAY = 2; //�ʽ��ʻ���
		public static final long PAY_AND_RECEIVE = 3; //һ��һ��
		public static final long FINANCE_RECEIVE = 4; //����˾�տ�
		public static final long FINANCE_PAY = 5; //����˾����
		public static final long RECEIVE_AND_FINANCE_PAY = 6; //�ʽ��ʻ��գ����и�
		public static final long PAY_AND_FINANCE_RECEIVE = 7; //�ʽ��ʻ�����������
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case (int) RECEIVE :
					strReturn = "��";
					break;
				case (int) PAY :
					strReturn = "��";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { RECEIVE, PAY };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ�����
		 * ��0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	public static class StockDirection
	{
		public static final long IN = 1; //��
		public static final long OUT = 2; //��		
	}
	/*�������
	  1=ʵ��
	  2=����*/
	public static class DeliveryOrderAttribute
	{
		public static final long REAL = 1; //ʵ��
		public static final long DUMMY = 2; //����
	}
	/*�۸�ָ��
	  0=������
	  1=������/С�ڵ���
	  2=��С��/���ڵ���
	  3=�̶�ֵ
	  4=�̶���Χ*/
	public static class PriceTarget
	{
		public static final long INVALID = 0; //������
		public static final long LESSTHAN = 1; //������/С�ڵ���
		public static final long MORETHAN = 2; //��С��/���ڵ���
		public static final long EQUAL = 3; //�̶�ֵ
		public static final long RANGE = 4; //�̶���Χ
	}
	/**
	 * 
	 * ҳ���������
	 * 
	 * @author rongyang
	 */
	public static class Actions
	{
		public static final int CREATETEMPSAVE = 1; //������ʱ����
		public static final int MODIFYTEMPSAVE = 2; //�޸���ʱ����
		public static final int CREATESAVE = 3; //��������
		public static final int MODIFYSAVE = 4; //�޸ı���
		public static final int DELETE = 5; //ɾ��
		public static final int LINKSEARCH = 6; //���Ӳ���
		public static final int MATCHSEARCH = 7; //ƥ�����
		public static final int CHECK = 8; //����/���
		public static final int CANCELCHECK = 9; //ȡ������
		public static final int NEXTSTEP = 10; //��һ��
		public static final int TODETAIL = 11; //�㽻�׺ŵ���ϸҳ��
		public static final int MODIFYNEXTSTEP = 12; //������һ��
		public static final int REJECT = 13; //�ܾ�
		public static final int RETURN = 14; //�����޸�
		public static final int CHECKOVER = 15; //������
		public static final int MASSCHECK = 16; //��������
		public static final int MASSCANCELCHECK = 17; //����ȡ������
		public static final int UPDATESEARCH = 18; //�޸Ĳ���
		public static final int CHECKSEARCH = 19; //��˲���
		public static final int COMMIT = 20; //�ύ
		public static final int INITAPPROVAL = 25;// �ύ����
		public static final int SAVEANDINITAPPROVAL = 26; // ���沢�ύ����
		public static final int DOAPPRVOAL = 27; // ����
		public static final int APPMATCHSEARCH = 28; // ��������
		public static final int CANCELAPPROVAL = 29; //ȡ������	
		public static final int MODIFYANDINITAPPROVAL = 30; // �޸Ĳ��ύ����

		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case CREATETEMPSAVE :
					strReturn = "��ʱ����";
					break;
				case MODIFYTEMPSAVE :
					strReturn = "��ʱ����";
					break;
				case CREATESAVE :
					strReturn = "��������";
					break;
				case MODIFYSAVE :
					strReturn = "�޸ı���";
					break;
				case DELETE :
					strReturn = "ɾ��";
					break;
				case LINKSEARCH :
					strReturn = "���Ӳ���";
					break;
				case MATCHSEARCH :
					strReturn = "ƥ�����";
					break;
				case CHECK :
					strReturn = "����";
					break;
				case CANCELCHECK :
					strReturn = "ȡ������";
					break;
				case NEXTSTEP :
					strReturn = "��һ��";
					break;
				case TODETAIL :
					strReturn = "�쿴��ϸ";
					break;
				case REJECT :
					strReturn = "�ܾ�";
					break;
				case RETURN :
					strReturn = "�����޸�";
					break;
				case CHECKOVER :
					strReturn = "������";
					break;
				case MASSCHECK :
					strReturn = "��������";
					break;
				case MASSCANCELCHECK :
					strReturn = "����ȡ������";
					break;
				case UPDATESEARCH :
					strReturn = "�޸Ĳ���";
					break;
				case CHECKSEARCH :
					strReturn = "��˲���";
					break;
				case COMMIT :
					strReturn = "�ύ";
				case INITAPPROVAL:
					strReturn = "�ύ����";
					break;
				case SAVEANDINITAPPROVAL:
					strReturn = "���沢�ύ����";
					break;				
				case DOAPPRVOAL:
					strReturn = "����";
					break;
				case APPMATCHSEARCH:
					strReturn = "��������";
					break;
				case CANCELAPPROVAL:
					strReturn = "ȡ������";
					break;

			}
			return strReturn;
		}
	}
	/**
	 * ����״̬
	 * @author lgwang
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class TransactionStatus
	{
		public static final long DELETED = 0; //��ɾ��
		public static final long TEMPSAVE = 1; //�ݴ�
		public static final long SAVE = 2; //�ѱ��棨δ���ˣ�
		public static final long CHECK = 3; //�Ѹ���
		public static final long NOTSIGN = 4; //δǩ��
		public static final long SIGN = 5; //��ǩ��
		public static final long CONFIRM = 6; //��ȷ��
		public static final long CIRCLE = 7; //�ѹ���
		public static final long APPROVALING = 10; //������		
		public static final long APPROVALED = 20;  //������		
		public static final long REFUSE = 30;  //�Ѿܾ�
		public static final long WAITAPPROVAL=40;//������		
		
		public static final long[] getAllCode()
		{
			long[] lTemp = { DELETED, TEMPSAVE, SAVE, CHECK, NOTSIGN, SIGN, CONFIRM, CIRCLE, APPROVALING, APPROVALED, REFUSE, WAITAPPROVAL };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) DELETED :
					strReturn = "ɾ��";
					break;
				case (int) TEMPSAVE :
					strReturn = "�ݴ�";
					break;
				case (int) SAVE :
					strReturn = "�ѱ���";
					break;
				case (int) CHECK :
					strReturn = "�Ѹ���";
					break;
				case (int) NOTSIGN :
					strReturn = "δǩ��";
					break;
				case (int) SIGN :
					strReturn = "��ǩ��";
					break;
				case (int) CONFIRM :
					strReturn = "��ȷ��";
					break;
				case (int) CIRCLE :
					strReturn = "�ѹ���";
					break;
				case (int) APPROVALING:
					strReturn = "������";
					break;
				case (int) APPROVALED:
					strReturn = "������";
					break;
				case (int) REFUSE:
					strReturn = "�Ѿܾ�";
					break;					
				case (int) WAITAPPROVAL:
					strReturn = "������";
					break;					
				default :
					strReturn = "����״̬";
			}
			return strReturn;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ�����
		 * ��0����ʾȫ����1��ҵ����2��ҵ�񸴺�;3,��ʾ3��ݴ桢δ���ˡ��Ѹ��ˣ���4,����(����,�Ѹ���)
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[2];
						lArrayID[0] = TEMPSAVE;
						lArrayID[1] = SAVE;
						break;
					case 2 :
						lArrayID = new long[1];
						lArrayID[0] = CHECK;
						break;
					case 3 :
						lArrayID = new long[] { TEMPSAVE, SAVE, CHECK };
						break;
					case 4 :
						lArrayID = new long[] { SAVE, CHECK,NOTSIGN };
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	/*����ָ��
	  0=������
	  1=������/С�ڵ���
	  2=��С��/���ڵ���
	  3=�̶�ֵ
	  4=�̶���Χ*/
	public static class AmountTarget extends PriceTarget
	{
	}
	/*����ָ��
	  0=������
	  1=������/С�ڵ���
	  2=��С��/���ڵ���
	  3=�̶�ֵ
	  4=�̶���Χ*/
	public static class DateTarget extends PriceTarget
	{
	}
	/*����ָ��
	  0=������
	  1=������/С�ڵ���
	  2=��С��/���ڵ���
	  3=�̶�ֵ
	  4=�̶���Χ*/
	public static class InterestRateTarget extends PriceTarget
	{
	}
	
	
	//֤ȯ���
	//Modify by leiyang 2007/09/06
	public static class SecuritiesType {
		public static final long STOCK_A = 1; //"��Ʊ"
		public static final long STOCK_B = 2; //"B��"
		public static final long ENTERPRISE_BOND = 3; //"��ҵծ��ȯ"
		public static final long TRANSFORMABLE_BOND = 4; //"��תծ��ȯ"
		public static final long ENCLOSED_FUND = 5; //"���ʽ����"
		public static final long MUTUAL_FUND = 6; //"����ʽ����"
		public static final long EXCHANGECENTER_NATIONAL_BOND = 7; //"��������ծ��ȯ"
		public static final long EXCHANGECENTER_BOND_REPURCHASE = 8; //"��������ծ�ع�"
		public static final long EXCHANGECENTER_ENTERPRISE_BOND = 9; //"��������ҵծ�ع�"
		public static final long BANK_NATIONAL_BOND = 10; //"���м��ծ��ȯ"
		public static final long BANK_BOND_REPURCHASE = 11; //"���м��ծ�ع�"
		public static final long CENTRAL_BANK_NOTE = 12; //"����Ʊ����ȯ"
		public static final long POLICY_RELATED_FINANCIAL_BOND = 13; //"�����Խ���ծ��ȯ"
		public static final long FINANCIAL_BOND = 14; //"����ծ��ȯ"
		public static final long OTHERS = 15; //"����"
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) STOCK_A :
					strReturn = "��Ʊ";
					break;
				case (int) CENTRAL_BANK_NOTE :
					strReturn = "����Ʊ��";
					break;
				case (int) BANK_BOND_REPURCHASE :
					strReturn = "���м��ծ�ع�";
					break;
				case (int) EXCHANGECENTER_ENTERPRISE_BOND :
					strReturn = "��������ҵծ�ع�";
					break;
				case (int) BANK_NATIONAL_BOND :
					strReturn = "���м��ծ";
					break;
				case (int) EXCHANGECENTER_NATIONAL_BOND :
					strReturn = "��������ծ";
					break;
				case (int) FINANCIAL_BOND :
					strReturn = "����ծ";
					break;
				case (int) ENTERPRISE_BOND :
					strReturn = "��ҵծ";
					break;
				case (int) TRANSFORMABLE_BOND :
					strReturn = "��תծ";
					break;
				case (int) ENCLOSED_FUND :
					strReturn = "���ʽ����";
					break;
				case (int) MUTUAL_FUND :
					strReturn = "����ʽ����";
					break;
				case (int) OTHERS :
					strReturn = "����";
					break;
					
				case (int) STOCK_B :
					strReturn = "B��";
					break;
				case (int) EXCHANGECENTER_BOND_REPURCHASE :
					strReturn = "��������ծ�ع�";
					break;
				case (int) POLICY_RELATED_FINANCIAL_BOND :
					strReturn = "�����Խ���ծ";
					break;
			}
			return strReturn;
		}
		
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = {
					STOCK_A,
					STOCK_B,
					ENTERPRISE_BOND,
					TRANSFORMABLE_BOND,
					ENCLOSED_FUND,
					MUTUAL_FUND,
					EXCHANGECENTER_NATIONAL_BOND,
					EXCHANGECENTER_BOND_REPURCHASE,
					EXCHANGECENTER_ENTERPRISE_BOND,
					BANK_NATIONAL_BOND,
					BANK_BOND_REPURCHASE,
					CENTRAL_BANK_NOTE,
					POLICY_RELATED_FINANCIAL_BOND,
					FINANCIAL_BOND,
					OTHERS
			};
			return lTemp;
		}
        
        //��������ȯ
        public static final long[] getExchangeCode()
        {
            long[] lTemp =
                {
                    STOCK_A,
                    ENTERPRISE_BOND,
                    TRANSFORMABLE_BOND,
                    ENCLOSED_FUND,
                    //MUTUAL_FUND,
                    EXCHANGECENTER_NATIONAL_BOND,
                    //EXCHANGECENTER_BOND_REPURCHASE,
                    //EXCHANGECENTER_ENTERPRISE_BOND,
                    BANK_NATIONAL_BOND,
                    //BANK_BOND_REPURCHASE,
                    CENTRAL_BANK_NOTE,
                    POLICY_RELATED_FINANCIAL_BOND,
                    FINANCIAL_BOND,
                    OTHERS };
            return lTemp;
        }
        
		/**
			* ��ʾ�����б�
			* 
			* @param out
			* @param strControlName,
			*            �ؼ�����
			* @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			* @param lSelectValue
			* @param isNeedAll���Ƿ���Ҫ��ȫ���
			* @param isNeedBlank
			*            �Ƿ���Ҫ�հ���
			* @param strProperty
			*/
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[11];
						lArrayID[0] = STOCK_A;
						lArrayID[1] = ENTERPRISE_BOND;
						lArrayID[2] = TRANSFORMABLE_BOND;
						lArrayID[3] = ENCLOSED_FUND;
						lArrayID[4] = MUTUAL_FUND;
						lArrayID[5] = EXCHANGECENTER_NATIONAL_BOND;
						lArrayID[6] = EXCHANGECENTER_ENTERPRISE_BOND;
						lArrayID[7] = BANK_NATIONAL_BOND;
						//Ŀǰû�����м��ծ�ع�ҵ����ʱ����  add 2007-11-07
						//lArrayID[8] = BANK_BOND_REPURCHASE;
						lArrayID[8] = CENTRAL_BANK_NOTE;
						lArrayID[9] = FINANCIAL_BOND;
                        //Ŀǰû������ҵ����ʱ����  add 2007-11-07
						//lArrayID[11] = OTHERS;
						lArrayID[10] = EXCHANGECENTER_BOND_REPURCHASE;
						break;
					case 5 :
						lArrayID = new long[10];
						lArrayID[0] = STOCK_A;
						lArrayID[1] = ENTERPRISE_BOND;
						lArrayID[2] = TRANSFORMABLE_BOND;
						lArrayID[3] = ENCLOSED_FUND;
						lArrayID[4] = MUTUAL_FUND;
						lArrayID[5] = EXCHANGECENTER_NATIONAL_BOND;
						lArrayID[6] = EXCHANGECENTER_BOND_REPURCHASE;
						lArrayID[7] = EXCHANGECENTER_ENTERPRISE_BOND;
						lArrayID[8] = BANK_NATIONAL_BOND;

						lArrayID[9] = FINANCIAL_BOND;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//֤ȯ�����
	public static class SecuritiesSubType
	{
		//public static final long TOSTOCK = 1; //"��Թ�Ʊ";
		public static final long PREFERREDSTOCK = 2; //"���ȹ�";
		public static final long ORDINARYSTOCK = 3; //"��ͨ��";
		public static final long OTHERS = 4; //"����";
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				//case (int) TOSTOCK :
				//strReturn = "��Թ�Ʊ";
				//break;
				case (int) PREFERREDSTOCK :
					strReturn = "���ȹ�";
					break;
				case (int) ORDINARYSTOCK :
					strReturn = "��ͨ��";
					break;
				case (int) OTHERS :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { PREFERREDSTOCK, ORDINARYSTOCK, OTHERS };
			return lTemp;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[3];
						//lArrayID[0] = TOSTOCK;
						lArrayID[0] = PREFERREDSTOCK;
						lArrayID[1] = ORDINARYSTOCK;
						lArrayID[2] = OTHERS;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//��������
	public static class TermType
	{
		public static final String YEAR = "Y";
		public static final String MONTH = "M";
		public static final String DAY = "D";
	}
	//���׶�������
	public static class CounterpartType
	{
		public static final long STOCK_JOBBER = 1; //֤ȯ��˾
		public static final long FUND_MGMT_COMPANY = 2; //�������˾
		public static final long CENTRAL_BANK = 3; //��������
		public static final long POLICY_RELATED_BANK = 4; //����������
		public static final long STATE_BANK = 5; //������ҵ����
		public static final long COMMERCIAL_BANK = 6; //������ҵ����
		public static final long INSURER = 7; //���չ�˾
		public static final long NONBANK_INSTITUTION = 8; //���������н��ڻ���
		public static final long OTHERS = 9; //������λ
		public static final long[] getAllCode()
		{
			long[] lTemp = { STOCK_JOBBER, FUND_MGMT_COMPANY, CENTRAL_BANK, POLICY_RELATED_BANK, STATE_BANK, COMMERCIAL_BANK, INSURER, NONBANK_INSTITUTION, OTHERS };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) STOCK_JOBBER :
					strReturn = "֤ȯ��˾";
					break;
				case (int) FUND_MGMT_COMPANY :
					strReturn = "�������˾";
					break;
				case (int) CENTRAL_BANK :
					strReturn = "��������";
					break;
				case (int) POLICY_RELATED_BANK :
					strReturn = "����������";
					break;
				case (int) STATE_BANK :
					strReturn = "������ҵ����";
					break;
				case (int) COMMERCIAL_BANK :
					strReturn = "������ҵ����";
					break;
				case (int) INSURER :
					strReturn = "���չ�˾";
					break;
				case (int) NONBANK_INSTITUTION :
					strReturn = "���������н��ڻ���";
					break;
				case (int) OTHERS :
					strReturn = "������λ";
					break;
			}
			return strReturn;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//���׶�������
	public static class CounterpartObjectType
	{
		public static final long FUND_MANAGEMENT_CO = 1; //�����������˾
		public static final long INTER_BANK_COUNTERPART = 2; //���м佻�׶���
		public static final long SECURITIES_UNDERWRITER = 3; //ծȯ������
		public static final long SECURITIES_CO = 4; //ί�����ȯ��
		public static final long INVESTED_CORPORATION = 5; //��Ͷ����ҵ
		public static final long OWNERSHIP_TRANSFER = 6; //��Ȩ���÷�
		public static final long FLOATERS = 7; //ծȯ������
		public static final long CONSIGNER = 8; //�������ί�з�
		public static final long POLICY_HOLDER = 9; //Ͷ����
	}
	//��ҵ��𣺶�Ӧ�ڽ��׶��������Ǳ�Ͷ����ҵ
	public static class IndustrySort
	{
		public static final long MANUFACTURING = 1; //����ҵ
		public static final long FINANCE = 2; //����ҵ
		public static final long REALTY = 3; //���ز�ҵ
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { MANUFACTURING, FINANCE, REALTY };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) MANUFACTURING :
					strReturn = "����ҵ";
					break;
				case (int) FINANCE :
					strReturn = "����ҵ";
					break;
				case (int) REALTY :
					strReturn = "���ز�ҵ";
					break;
			}
			return strReturn;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[3];
						lArrayID[0] = MANUFACTURING;
						lArrayID[1] = FINANCE;
						lArrayID[2] = REALTY;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//��ҵ���ͣ���Ӧ�ڽ��׶���������Ͷ����
	public static class IndustryType
	{
		public static final long INDUSTRYTYPE_ENTERPRISE = 1; //��ҵ
		
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { INDUSTRYTYPE_ENTERPRISE };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) INDUSTRYTYPE_ENTERPRISE :
					strReturn = "��ҵ";
					break;				
			}
			return strReturn;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ�����
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//�ʽ��ʻ�����
	public static class AccountType
	{
		public static final long SECURITIES_ACCOUNT = 1; //֤ȯ�ʻ�
		public static final long OPENFUND_ACCOUNT = 2; //����ʽ�����ʻ�
		public static final long BANK_ACCOUNT = 3; //�����ʻ�
	}
	/*�ʻ�״̬
	  1=����
	  2=����
	  3=���
	  4=�廧*/
	public static class AccountStatusID
	{
		public static final long NORMAL = 1; //����
		public static final long FREEZE = 2; //����
		public static final long SEALUP = 3; //���
		public static final long CLOSE = 4; //�廧
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) NORMAL :
					strReturn = "����";
					break;
				case (int) FREEZE :
					strReturn = "����";
					break;
				case (int) SEALUP :
					strReturn = "���";
					break;
				case (int) CLOSE :
					strReturn = "�廧";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { NORMAL, FREEZE, SEALUP, CLOSE };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[3];
						lArrayID[0] = NORMAL;
						lArrayID[1] = FREEZE;
						lArrayID[2] = SEALUP;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	
	//ҵ������
	public static class BusinessType
	{
		//Modify by leiyang date 2007/09/07
		//����ȡ��ҵ��Ľ������ͱ��
		public static final long[] getBusinessTransType(long bussinessType){
        	long[] lTemp = null;
        	switch ((int) bussinessType)
        	{
        		//16.��Ʊһ�������깺
        		case (int) STOCK_BID_ONLINE.ID :
        			lTemp = STOCK_BID_ONLINE.getAllCode();
        			break;
        		//17.��Ʊһ�������깺
        		case (int) STOCK_BID.ID :
        			lTemp = STOCK_BID.getAllCode();
        			break;
        		//18.��Ʊ����
        		case (int) STOCK_TRANSACTION.ID :
        			lTemp = STOCK_TRANSACTION.getAllCode();
        			break;
        		//21.����Ʊ��һ�� 
        		case (int) CENTRAL_BANK_NOTE_BID.ID :
        			lTemp = CENTRAL_BANK_NOTE_BID.getAllCode();
        			break;
        		//22.����Ʊ�ݶ��� 
        		case (int) CENTRAL_BANK_NOTE_TRANSACTION.ID :
        			lTemp = CENTRAL_BANK_NOTE_TRANSACTION.getAllCode();
        			break;
        		//26.���м�ծȯ�ع�
        		case (int) BANK_BOND_REPURCHASE.ID :
        			lTemp = BANK_BOND_REPURCHASE.getAllCode();
        			break;
        		//27.������ծȯ�ع� 
        		case (int) EXCHANGECENTER_BOND_REPURCHASE.ID :
        			lTemp = EXCHANGECENTER_BOND_REPURCHASE.getAllCode();
        			break;
        		//31.���м��ծһ�� 
        		case (int) BANK_NATIONAL_BOND_BID.ID :
        			lTemp = BANK_NATIONAL_BOND_BID.getAllCode();
        			break;
        		//32.���м��ծ���� 
        		case (int) BANK_NATIONAL_BOND_TRANSACTION.ID :
        			lTemp = BANK_NATIONAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//33.��������ծһ�� 
        		case (int) EXCHANGECENTER_NATIONAL_BOND_BID.ID :
        			lTemp = EXCHANGECENTER_NATIONAL_BOND_BID.getAllCode();
        			break;
        		//34.��������ծ����
        		case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID :
        			lTemp = EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//36.����ծһ��
        		case (int) FINANCIAL_BOND_BID.ID :
        			lTemp = FINANCIAL_BOND_BID.getAllCode();
        			break;
        		//37.����ծ����
        		case (int) FINANCIAL_BOND_TRANSACTION.ID :
        			lTemp = FINANCIAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//41.�����Խ���ծһ�� 
        		case (int) POLICY_RELATED_FINANCIAL_BOND_BID.ID :
        			lTemp = POLICY_RELATED_FINANCIAL_BOND_BID.getAllCode();
        			break;
        		//42.�����Խ���ծ����
        		case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID :
        			lTemp = POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.getAllCode();
        			break;
        		//46.��ҵծһ��
        		case (int) ENTERPRISE_BOND_BID.ID :
        			lTemp = ENTERPRISE_BOND_BID.getAllCode();
        			break;
        		//47.��ҵծ����
        		case (int) ENTERPRISE_BOND_TRANSACTION.ID :
        			lTemp = ENTERPRISE_BOND_TRANSACTION.getAllCode();
        			break;
        		//51.��תծһ�������깺 
        		case (int) TRANSFORMABLE_BOND_BID_ONLINE.ID :
        			lTemp = TRANSFORMABLE_BOND_BID_ONLINE.getAllCode();
        			break;
        		//52.��תծһ�������깺
        		case (int) TRANSFORMABLE_BOND_BID.ID :
        			lTemp = TRANSFORMABLE_BOND_BID.getAllCode();
        			break;
        		//53.��תծ����
        		case (int) TRANSFORMABLE_BOND_TRANSACTION.ID :
        			lTemp = TRANSFORMABLE_BOND_TRANSACTION.getAllCode();
        			break;
        		//54.ծת�� 
        		case (int) DEBT_TO_EQUITY.ID :
        			lTemp = DEBT_TO_EQUITY.getAllCode();
        			break;
        		//56.���ʽ����һ�������깺
        		case (int) ENCLOSED_FUND_BID_ONLINE.ID :
        			lTemp = ENCLOSED_FUND_BID_ONLINE.getAllCode();
        			break;
        		//57.���ʽ����һ�������깺
        		case (int) ENCLOSED_FUND_BID.ID :
        			lTemp = ENCLOSED_FUND_BID.getAllCode();
        			break;
        		//58.���ʽ�������
        		case (int) ENCLOSED_FUND_TRANSACTION.ID :
        			lTemp = ENCLOSED_FUND_TRANSACTION.getAllCode();
        			break;
        		//61.����ʽ����һ���Ϲ� 
        		case (int) MUTUAL_FUND_SUBSCRIBE.ID :
        			lTemp = MUTUAL_FUND_SUBSCRIBE.getAllCode();
        			break;
        		//62.����ʽ��������깺
        		case (int) MUTUAL_FUND_BID.ID :
        			lTemp = MUTUAL_FUND_BID.getAllCode();
        			break;
        		//63.����ʽ����������
        		case (int) MUTUAL_FUND_REDEEM.ID :
        			lTemp = MUTUAL_FUND_REDEEM.getAllCode();
        			break;
        		//64.����ʽ����ֺ�
        		case (int) MUTUAL_FUND_MELON_CUTTING.ID :
        			lTemp = MUTUAL_FUND_MELON_CUTTING.getAllCode();
        			break;
        		//73.ί�����
        		case (int) ENTRUST_FINANCING.ID :
        			lTemp = ENTRUST_FINANCING.getAllCode();
        			break;
        		//77.�ṹ��Ͷ�� 
        		case (int) FOREIGN_CURRENCY_INVESTMENT.ID :
        			lTemp = FOREIGN_CURRENCY_INVESTMENT.getAllCode();
        			break;
        		//81.ծȯ����
        		case (int) BOND_UNDERWRITING.ID :
        			lTemp = BOND_UNDERWRITING.getAllCode();
        			break;
        		//85.�ʽ𻮲�
           		case (int) CAPITAL_TRANSFER.ID :
        			lTemp = CAPITAL_TRANSFER.getAllCode();
           			break;
           		default:
           			lTemp = null;
        	}
        	return lTemp;
		}
		
		//Modify by leiyang date 2007/09/07
		//����ȡ��ҵ��Ľ�����������
		public static final String getBusinessTransName(long bussinessType, long transId){
        	String strTemp = null;
        	switch ((int) bussinessType)
        	{
        		//16.��Ʊһ�������깺
        		case (int) STOCK_BID_ONLINE.ID :
        			strTemp = STOCK_BID_ONLINE.getName(transId);
        			break;
        		//17.��Ʊһ�������깺
        		case (int) STOCK_BID.ID :
        			strTemp = STOCK_BID.getName(transId);
        			break;
        		//18.��Ʊ����
        		case (int) STOCK_TRANSACTION.ID :
        			strTemp = STOCK_TRANSACTION.getName(transId);
        			break;
        		//21.����Ʊ��һ�� 
        		case (int) CENTRAL_BANK_NOTE_BID.ID :
        			strTemp = CENTRAL_BANK_NOTE_BID.getName(transId);
        			break;
        		//22.����Ʊ�ݶ��� 
        		case (int) CENTRAL_BANK_NOTE_TRANSACTION.ID :
        			strTemp = CENTRAL_BANK_NOTE_TRANSACTION.getName(transId);
        			break;
        		//26.���м�ծȯ�ع�
        		case (int) BANK_BOND_REPURCHASE.ID :
        			strTemp = BANK_BOND_REPURCHASE.getName(transId);
        			break;
        		//27.������ծȯ�ع� 
        		case (int) EXCHANGECENTER_BOND_REPURCHASE.ID :
        			strTemp = EXCHANGECENTER_BOND_REPURCHASE.getName(transId);
        			break;
        		//31.���м��ծһ�� 
        		case (int) BANK_NATIONAL_BOND_BID.ID :
        			strTemp = BANK_NATIONAL_BOND_BID.getName(transId);
        			break;
        		//32.���м��ծ���� 
        		case (int) BANK_NATIONAL_BOND_TRANSACTION.ID :
        			strTemp = BANK_NATIONAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//33.��������ծһ�� 
        		case (int) EXCHANGECENTER_NATIONAL_BOND_BID.ID :
        			strTemp = EXCHANGECENTER_NATIONAL_BOND_BID.getName(transId);
        			break;
        		//34.��������ծ����
        		case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID :
        			strTemp = EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//36.����ծһ��
        		case (int) FINANCIAL_BOND_BID.ID :
        			strTemp = FINANCIAL_BOND_BID.getName(transId);
        			break;
        		//37.����ծ����
        		case (int) FINANCIAL_BOND_TRANSACTION.ID :
        			strTemp = FINANCIAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//41.�����Խ���ծһ�� 
        		case (int) POLICY_RELATED_FINANCIAL_BOND_BID.ID :
        			strTemp = POLICY_RELATED_FINANCIAL_BOND_BID.getName(transId);
        			break;
        		//42.�����Խ���ծ����
        		case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID :
        			strTemp = POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.getName(transId);
        			break;
        		//46.��ҵծһ��
        		case (int) ENTERPRISE_BOND_BID.ID :
        			strTemp = ENTERPRISE_BOND_BID.getName(transId);
        			break;
        		//47.��ҵծ����
        		case (int) ENTERPRISE_BOND_TRANSACTION.ID :
        			strTemp = ENTERPRISE_BOND_TRANSACTION.getName(transId);
        			break;
        		//51.��תծһ�������깺 
        		case (int) TRANSFORMABLE_BOND_BID_ONLINE.ID :
        			strTemp = TRANSFORMABLE_BOND_BID_ONLINE.getName(transId);
        			break;
        		//52.��תծһ�������깺
        		case (int) TRANSFORMABLE_BOND_BID.ID :
        			strTemp = TRANSFORMABLE_BOND_BID.getName(transId);
        			break;
        		//53.��תծ����
        		case (int) TRANSFORMABLE_BOND_TRANSACTION.ID :
        			strTemp = TRANSFORMABLE_BOND_TRANSACTION.getName(transId);
        			break;
        		//54.ծת�� 
        		case (int) DEBT_TO_EQUITY.ID :
        			strTemp = DEBT_TO_EQUITY.getName(transId);
        			break;
        		//56.���ʽ����һ�������깺
        		case (int) ENCLOSED_FUND_BID_ONLINE.ID :
        			strTemp = ENCLOSED_FUND_BID_ONLINE.getName(transId);
        			break;
        		//57.���ʽ����һ�������깺
        		case (int) ENCLOSED_FUND_BID.ID :
        			strTemp = ENCLOSED_FUND_BID.getName(transId);
        			break;
        		//58.���ʽ�������
        		case (int) ENCLOSED_FUND_TRANSACTION.ID :
        			strTemp = ENCLOSED_FUND_TRANSACTION.getName(transId);
        			break;
        		//61.����ʽ����һ���Ϲ� 
        		case (int) MUTUAL_FUND_SUBSCRIBE.ID :
        			strTemp = MUTUAL_FUND_SUBSCRIBE.getName(transId);
        			break;
        		//62.����ʽ��������깺
        		case (int) MUTUAL_FUND_BID.ID :
        			strTemp = MUTUAL_FUND_BID.getName(transId);
        			break;
        		//63.����ʽ����������
        		case (int) MUTUAL_FUND_REDEEM.ID :
        			strTemp = MUTUAL_FUND_REDEEM.getName(transId);
        			break;
        		//64.����ʽ����ֺ�
        		case (int) MUTUAL_FUND_MELON_CUTTING.ID :
        			strTemp = MUTUAL_FUND_MELON_CUTTING.getName(transId);
        			break;
        		//73.ί�����
        		case (int) ENTRUST_FINANCING.ID :
        			strTemp = ENTRUST_FINANCING.getName(transId);
        			break;
        		//77.�ṹ��Ͷ�� 
        		case (int) FOREIGN_CURRENCY_INVESTMENT.ID :
        			strTemp = FOREIGN_CURRENCY_INVESTMENT.getName(transId);
        			break;
        		//81.ծȯ����
        		case (int) BOND_UNDERWRITING.ID :
        			strTemp = BOND_UNDERWRITING.getName(transId);
        			break;
            	//85.�ʽ𻮲�
        		case (int) CAPITAL_TRANSFER.ID :
        			strTemp = CAPITAL_TRANSFER.getName(transId);
        			break;
        		default:
        			strTemp = "";
        	}
        	return strTemp;
		}
		
		/*�Ƿ���Ҫ�Ǽǻع��ǼǱ�*/
		static public boolean isNeedRegiesterRepurchaseRegister(long businessTypeID)
		{
			//TBD
			return true;
		}
		/*�Ƿ���Ҫ�Ǽ��깺�ǼǱ�*/
		static public boolean isNeedRegiesterPurchaseRegister(long businessTypeID)
		{
			//TBD
			return true;
		}
		/*�Ƿ���Ҫ�Ǽǳ���Ͷ�ʵǼǱ�*/
		static public boolean isNeedRegiesterInvestmentRegister(long businessTypeID)
		{
			//TBD
			return true;
		}
		//��ȡҵ�����͵���ϸ��Ϣ
		public static TransactionTypeInfo getTransactionTypeInfoByID(long id) throws ITreasuryDAOException
		{
			SEC_TransactionTypeDAO dao = new SEC_TransactionTypeDAO();
			TransactionTypeInfo resInfo = (TransactionTypeInfo) dao.findByID(id, TransactionTypeInfo.class);
			return resInfo;
		}
		//11.�ʽ��������
		public static class CAPITAL_IN_CREDIT_EXTENSION
		{
			public static final long ID = 11;
			public static final long CAPITAL_IN_CREDIT_EXTENSION = 1101; //�ʽ��������
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN_CREDIT_EXTENSION :
						strReturn = "�ʽ��������";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN_CREDIT_EXTENSION };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//12.�ʽ�������
		public static class CAPITAL_OUT_CREDIT_EXTENSION
		{
			public static final long ID = 12;
			public static final long CAPITAL_OUT_CREDIT_EXTENSION = 1201; //�ʽ�������
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) CAPITAL_OUT_CREDIT_EXTENSION :
						strReturn = "�ʽ�������";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_OUT_CREDIT_EXTENSION };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_OUT_CREDIT_EXTENSION;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_OUT_CREDIT_EXTENSION;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = CAPITAL_OUT_CREDIT_EXTENSION;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//13.�ʽ���ҵ��
		public static class CAPITAL_LANDING
		{
			public static final long ID = 13;
			public static final long CAPITAL_IN = 1301; //�ʽ����
			public static final long CAPITAL_IN_REPAY = 1302; //�ʽ���뷵��
			public static final long CAPITAL_OUT = 1303; //�ʽ��� 
			public static final long CAPITAL_OUT_REPAY = 1304; //�ʽ�������
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN :
						strReturn = "�ʽ����";
						break;
					case (int) CAPITAL_IN_REPAY :
						strReturn = "�ʽ���뷵��";
						break;
					case (int) CAPITAL_OUT :
						strReturn = "�ʽ���";
						break;
					case (int) CAPITAL_OUT_REPAY :
						strReturn = "�ʽ�������";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN, CAPITAL_IN_REPAY, CAPITAL_OUT, CAPITAL_OUT_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,ֻ��ʾ�����ף�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
							//������������
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
							break;
							//���ܽ����
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
							break;
							//����֪ͨ����
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
							break;
							//���������
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_IN_REPAY;
							lArrayID[2] = CAPITAL_OUT;
							lArrayID[3] = CAPITAL_OUT_REPAY;
							break;

					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//16.��Ʊһ�������깺
		public static class STOCK_BID_ONLINE
		{
			public static final long ID = 16;
			public static final long INITIAL_OFFERING_BID_ONLINE = 1601; //��Ʊ�׷������깺
			public static final long INITIAL_OFFERING_BID_WIN = 1602; //��Ʊ�׷��깺��ǩ
			public static final long INITIAL_OFFERING_BID_REPAY = 1603; //��Ʊ�׷��깺����
			public static final long PROMOTION_BID_ONLINE = 1604; //��Ʊ���������깺
			public static final long PROMOTION_BID_WIN = 1605; //��Ʊ�����깺��ǩ
			public static final long PROMOTION_BID_REPAY = 1606; //��Ʊ�����깺����
			//���¹���ʹ��
			public static final long INITIAL_OFFERING_SELL = 1607; //��Ʊ�׷�����
			public static final long INITIAL_OFFERING_QUOTA_CONFIRM = 1608; //��Ʊ�׷�������ǩ
			public static final long PROMOTION_SELL = 1609; //��Ʊ��������
			public static final long PROMOTION_QUOTA_CONFIRM = 1610; //��Ʊ����������ǩ

			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) INITIAL_OFFERING_BID_ONLINE :
						strReturn = "��Ʊ�׷������깺";
						break;
					case (int) INITIAL_OFFERING_BID_WIN :
						strReturn = "��Ʊ�׷��깺��ǩ";
						break;
					case (int) INITIAL_OFFERING_BID_REPAY :
						strReturn = "��Ʊ�׷��깺����";
						break;
					case (int) PROMOTION_BID_ONLINE :
						strReturn = "��Ʊ���������깺";
						break;
					case (int) PROMOTION_BID_WIN :
						strReturn = "��Ʊ�����깺��ǩ";
						break;
					case (int) PROMOTION_BID_REPAY :
						strReturn = "��Ʊ�����깺����";
						break;
						//					���¹���ʹ��
					case (int) INITIAL_OFFERING_SELL :
						strReturn = "��Ʊ�׷�����";
						break;
					case (int) INITIAL_OFFERING_QUOTA_CONFIRM :
						strReturn = "��Ʊ�׷�������ǩ";
						break;
					case (int) PROMOTION_SELL :
						strReturn = "��Ʊ��������";
						break;
					case (int) PROMOTION_QUOTA_CONFIRM :
						strReturn = "��Ʊ����������ǩ";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp =
					{
						INITIAL_OFFERING_BID_ONLINE,
						INITIAL_OFFERING_BID_WIN,
						INITIAL_OFFERING_BID_REPAY,
						PROMOTION_BID_ONLINE,
						PROMOTION_BID_WIN,
						PROMOTION_BID_REPAY,
						INITIAL_OFFERING_SELL,
						INITIAL_OFFERING_QUOTA_CONFIRM,
						PROMOTION_SELL,
						PROMOTION_QUOTA_CONFIRM };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[3] = PROMOTION_BID_ONLINE;
							lArrayID[4] = PROMOTION_BID_WIN;
							lArrayID[5] = PROMOTION_BID_REPAY;
							break;
						case 2 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[3] = PROMOTION_BID_ONLINE;
							lArrayID[4] = PROMOTION_BID_WIN;
							lArrayID[5] = PROMOTION_BID_REPAY;
							break;
						case 3 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[3] = PROMOTION_BID_ONLINE;
							lArrayID[4] = PROMOTION_BID_WIN;
							lArrayID[5] = PROMOTION_BID_REPAY;
							break;
						case 5 :
							lArrayID = new long[10];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_WIN;
							lArrayID[2] = INITIAL_OFFERING_BID_REPAY;
							//						���¹���ʹ��
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							lArrayID[4] = INITIAL_OFFERING_SELL;
							lArrayID[5] = PROMOTION_BID_ONLINE;
							lArrayID[6] = PROMOTION_BID_WIN;
							lArrayID[7] = PROMOTION_BID_REPAY;
							//						���¹���ʹ��
							lArrayID[8] = PROMOTION_QUOTA_CONFIRM;
							lArrayID[9] = PROMOTION_SELL;

							break;
							/**�������ڹ���--ҵ��֪ͨ��**/
						case 6 :
							lArrayID = new long[4];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[2] = PROMOTION_BID_ONLINE;
							lArrayID[3] = PROMOTION_BID_REPAY;
							break;

					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//17.��Ʊһ�������깺
		public static class STOCK_BID
		{
			public static final long ID = 17;
			public static final long INITIAL_OFFERING_BID = 1701; //��Ʊ�׷������깺
			public static final long INITIAL_OFFERING_MARGIN_SUPPLY = 1702; //��Ʊ�׷�����
			public static final long INITIAL_OFFERING_MARGIN_REPAY = 1703; //��Ʊ�׷�����
			public static final long INITIAL_OFFERING_QUOTA_CONFIRM = 1704; //��Ʊ�׷�����ȷ��
			public static final long PROMOTION_BID = 1705; //��Ʊ���������깺
			public static final long PROMOTION_BID_MARGIN_SUPPLY = 1706; //��Ʊ��������
			public static final long PROMOTION_BID_MARGIN_REPAY = 1707; //��Ʊ��������
			public static final long PROMOTION_BID_QUOTA_CONFIRM = 1708; //��Ʊ��������ȷ��
			//			���¹���ʹ��
			public static final long INITIAL_OFFERING_SELL = 1709; //��Ʊ�׷�����
			public static final long PROMOTION_SELL = 1710; //��Ʊ��������

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) INITIAL_OFFERING_BID :
						strReturn = "��Ʊ�׷������깺";
						break;
					case (int) INITIAL_OFFERING_MARGIN_SUPPLY :
						strReturn = "��Ʊ�׷�����";
						break;
					case (int) INITIAL_OFFERING_MARGIN_REPAY :
						strReturn = "��Ʊ�׷�����";
						break;
					case (int) INITIAL_OFFERING_QUOTA_CONFIRM :
						strReturn = "��Ʊ�׷�����ȷ��";
						break;
					case (int) PROMOTION_BID :
						strReturn = "��Ʊ���������깺";
						break;
					case (int) PROMOTION_BID_MARGIN_SUPPLY :
						strReturn = "��Ʊ��������";
						break;
					case (int) PROMOTION_BID_MARGIN_REPAY :
						strReturn = "��Ʊ��������";
						break;
					case (int) PROMOTION_BID_QUOTA_CONFIRM :
						strReturn = "��Ʊ��������ȷ��";
						break;
						//					���¹���ʹ��
					case (int) INITIAL_OFFERING_SELL :
						strReturn = "��Ʊ�׷�����";
						break;
					case (int) PROMOTION_SELL :
						strReturn = "��Ʊ��������";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp =
					{
						INITIAL_OFFERING_BID,
						INITIAL_OFFERING_MARGIN_SUPPLY,
						INITIAL_OFFERING_MARGIN_REPAY,
						INITIAL_OFFERING_QUOTA_CONFIRM,
						PROMOTION_BID,
						PROMOTION_BID_MARGIN_SUPPLY,
						PROMOTION_BID_MARGIN_REPAY,
						PROMOTION_BID_QUOTA_CONFIRM,
						INITIAL_OFFERING_SELL,
						PROMOTION_SELL };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[8];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							lArrayID[4] = PROMOTION_BID;
							lArrayID[5] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[6] = PROMOTION_BID_MARGIN_REPAY;
							lArrayID[7] = PROMOTION_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[8];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							lArrayID[4] = PROMOTION_BID;
							lArrayID[5] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[6] = PROMOTION_BID_MARGIN_REPAY;
							lArrayID[7] = PROMOTION_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = PROMOTION_BID;
							lArrayID[4] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[5] = PROMOTION_BID_MARGIN_REPAY;
							break;
						case 5 :
							lArrayID = new long[10];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = INITIAL_OFFERING_QUOTA_CONFIRM;
							//						���¹���ʹ��
							lArrayID[4] = INITIAL_OFFERING_SELL;

							lArrayID[5] = PROMOTION_BID;
							lArrayID[6] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[7] = PROMOTION_BID_MARGIN_REPAY;
							lArrayID[8] = PROMOTION_BID_QUOTA_CONFIRM;
							//						���¹���ʹ��
							lArrayID[9] = PROMOTION_SELL;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//18.��Ʊ����
		public static class STOCK_TRANSACTION
		{
			public static final long ID = 18;
			public static final long STOCK_BUYIN = 1801; //��Ʊ����
			public static final long STOCK_SELL = 1802; //��Ʊ����
			public static final long STOCK_RATION_ONLINE = 1803; //�������
			public static final long STOCK_RATION = 1804; //�������
			public static final long STOCK_TAKEBACK = 1805; //�͹�
			public static final long STOCK_MELON_CUTTING = 1806; //�ֺ�
			public static final long STOCK_MELON_CUTTING_CNMEF = 1807; //��Ϣ

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) STOCK_BUYIN :
						strReturn = "��Ʊ����";
						break;
					case (int) STOCK_SELL :
						strReturn = "��Ʊ����";
						break;
					case (int) STOCK_RATION_ONLINE :
						strReturn = "�������";
						break;
					case (int) STOCK_RATION :
						strReturn = "�������";
						break;
					case (int) STOCK_TAKEBACK :
						strReturn = "�͹�";
						break;
					case (int) STOCK_MELON_CUTTING :
						strReturn = "�ֺ�";
						break;
					case (int) STOCK_MELON_CUTTING_CNMEF :
						strReturn = "��Ϣ";
						break;

				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { STOCK_BUYIN, STOCK_SELL, STOCK_RATION_ONLINE, STOCK_RATION, STOCK_TAKEBACK, STOCK_MELON_CUTTING, STOCK_MELON_CUTTING_CNMEF };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[6];
							lArrayID[0] = STOCK_BUYIN;
							lArrayID[1] = STOCK_SELL;
							lArrayID[2] = STOCK_RATION_ONLINE;
							lArrayID[3] = STOCK_RATION;
							lArrayID[4] = STOCK_TAKEBACK;
							lArrayID[5] = STOCK_MELON_CUTTING;
							break;
						case 2 :
							lArrayID = new long[6];
							lArrayID[0] = STOCK_BUYIN;
							lArrayID[1] = STOCK_SELL;
							lArrayID[2] = STOCK_RATION_ONLINE;
							lArrayID[3] = STOCK_RATION;
							lArrayID[4] = STOCK_TAKEBACK;
							lArrayID[5] = STOCK_MELON_CUTTING;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = STOCK_RATION;
							break;
							//����
						case 5 :
							lArrayID = new long[6];
							lArrayID[0] = STOCK_BUYIN;
							lArrayID[1] = STOCK_SELL;
							lArrayID[2] = STOCK_RATION_ONLINE;
							lArrayID[3] = STOCK_RATION;
							lArrayID[4] = STOCK_TAKEBACK;
							lArrayID[5] = STOCK_MELON_CUTTING_CNMEF;
							break;
							/**�������ڹ���---ҵ��֪ͨ��**/
						case 6 :
							lArrayID = new long[1];
							lArrayID[0] = STOCK_BUYIN;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//21.����Ʊ��һ�� 
		public static class CENTRAL_BANK_NOTE_BID
		{
			public static final long ID = 21;
			public static final long NOTE_BID = 2101; //����Ʊ���깺
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) NOTE_BID :
						strReturn = "����Ʊ���깺";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { NOTE_BID };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = NOTE_BID;
							break;

					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//22.����Ʊ�ݶ��� 
		public static class CENTRAL_BANK_NOTE_TRANSACTION
		{
			public static final long ID = 22;
			public static final long NOTE_BUYIN = 2201; //����Ʊ������
			public static final long NOTE_SELL = 2202; //����Ʊ������
			public static final long NOTE_ACCRUAL_IN = 2203; //��Ϣ
			public static final long NOTE_MATURITY_REPAY = 2204; //���ڻ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) NOTE_BUYIN :
						strReturn = "����Ʊ������";
						break;
					case (int) NOTE_SELL :
						strReturn = "����Ʊ������";
						break;
					case (int) NOTE_ACCRUAL_IN :
						strReturn = "��Ϣ";
						break;
					case (int) NOTE_MATURITY_REPAY :
						strReturn = "���ڻ���";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { NOTE_BUYIN, NOTE_SELL, NOTE_ACCRUAL_IN, NOTE_MATURITY_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = NOTE_BUYIN;
							lArrayID[1] = NOTE_SELL;
							lArrayID[2] = NOTE_ACCRUAL_IN;
							lArrayID[3] = NOTE_MATURITY_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//26.���м�ծȯ�ع�
		public static class BANK_BOND_REPURCHASE
		{
			public static final long ID = 26;
			public static final long FUND_REPURCHASE = 2601; //���ع�
			public static final long FUND_REPAY = 2602; //���ع�����
			public static final long BOND_REPURCHASE = 2603; //��ع�
			public static final long BOND_REPAY = 2604; //��ع�����
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_REPURCHASE :
						strReturn = "���ع�";
						break;
					case (int) FUND_REPAY :
						strReturn = "���ع�����";
						break;
					case (int) BOND_REPURCHASE :
						strReturn = "��ع�";
						break;
					case (int) BOND_REPAY :
						strReturn = "��ع�����";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_REPURCHASE, FUND_REPAY, BOND_REPURCHASE, BOND_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//27.������ծȯ�ع� 
		public static class EXCHANGECENTER_BOND_REPURCHASE
		{
			public static final long ID = 27;
			public static final long FUND_REPURCHASE = 2701; //���ع�
			public static final long FUND_REPAY = 2702; //������
			public static final long BOND_REPURCHASE = 2703; //��ع�
			public static final long BOND_REPAY = 2704; //�湺��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_REPURCHASE :
						strReturn = "���ع�";
						break;
					case (int) FUND_REPAY :
						strReturn = "������";
						break;
					case (int) BOND_REPURCHASE :
						strReturn = "��ع�";
						break;
					case (int) BOND_REPAY :
						strReturn = "�湺��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_REPURCHASE, FUND_REPAY, BOND_REPURCHASE, BOND_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_REPURCHASE;
							lArrayID[1] = FUND_REPAY;
							lArrayID[2] = BOND_REPURCHASE;
							lArrayID[3] = BOND_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//31.���м��ծһ�� 
		public static class BANK_NATIONAL_BOND_BID
		{
			public static final long ID = 31;
			public static final long BOND_BID = 3101; //���м��ծ�깺
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "���м��ծ�깺";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//32.���м��ծ���� 
		public static class BANK_NATIONAL_BOND_TRANSACTION
		{
			public static final long ID = 32;
			public static final long BOND_BUYIN = 3201; //���м��ծ����
			public static final long BOND_SELL = 3202; //���м��ծ����
			public static final long BOND_ACCRUAL_IN = 3203; //��Ϣ
			public static final long BOND_MATURITY_REPAY = 3204; //���ڻ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "���м��ծ����";
						break;
					case (int) BOND_SELL :
						strReturn = "���м��ծ����";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "��Ϣ";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "���ڻ���";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//33.��������ծһ�� 
		public static class EXCHANGECENTER_NATIONAL_BOND_BID
		{
			public static final long ID = 33;
			public static final long BOND_BID = 3301; //��������ծ�깺
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "��������ծ�깺";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//34.��������ծ����
		public static class EXCHANGECENTER_NATIONAL_BOND_TRANSACTION
		{
			public static final long ID = 34;
			public static final long BOND_BUYIN = 3401; //��������ծ����
			public static final long BOND_SELL = 3402; //��������ծ����
			public static final long BOND_ACCRUAL_IN = 3403; //��Ϣ
			public static final long BOND_MATURITY_REPAY = 3404; //���ڻ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "��������ծ����";
						break;
					case (int) BOND_SELL :
						strReturn = "��������ծ����";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "��Ϣ";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "���ڻ���";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//36.����ծһ��
		public static class FINANCIAL_BOND_BID
		{
			public static final long ID = 36;
			public static final long BOND_BID = 3601; //����ծ�깺
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "����ծ�깺";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//37.����ծ����
		public static class FINANCIAL_BOND_TRANSACTION
		{
			public static final long ID = 37;
			public static final long BOND_BUYIN = 3701; //����ծ����
			public static final long BOND_SELL = 3702; //����ծ����
			public static final long BOND_ACCRUAL_IN = 3703; //��Ϣ
			public static final long BOND_MATURITY_REPAY = 3704; //���ڻ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "����ծ����";
						break;
					case (int) BOND_SELL :
						strReturn = "����ծ����";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "��Ϣ";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "���ڻ���";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//41.�����Խ���ծһ�� 
		public static class POLICY_RELATED_FINANCIAL_BOND_BID
		{
			public static final long ID = 41;
			public static final long BOND_BID = 4101; //�����Խ���ծ�깺
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "�����Խ���ծ�깺";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BID;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//42.�����Խ���ծ����
		public static class POLICY_RELATED_FINANCIAL_BOND_TRANSACTION
		{
			public static final long ID = 42;
			public static final long BOND_BUYIN = 4201; //�����Խ���ծ����
			public static final long BOND_SELL = 4202; //�����Խ���ծ����
			public static final long BOND_ACCRUAL_IN = 4203; //��Ϣ
			public static final long BOND_MATURITY_REPAY = 4204; //���ڻ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "�����Խ���ծ����";
						break;
					case (int) BOND_SELL :
						strReturn = "�����Խ���ծ����";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "��Ϣ";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "���ڻ���";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//46.��ҵծһ��
		public static class ENTERPRISE_BOND_BID
		{
			public static final long ID = 46;
			public static final long BOND_BID = 4601; //��ҵծ�깺
			public static final long BOND_BID_MARGIN_SUPPLY = 4602; //��ҵծ�깺����
			public static final long BOND_BID_MARGIN_REPAY = 4603; //��ҵծ�깺����
			public static final long BOND_BID_QUOTA_CONFIRM = 4604; //��ҵծ�깺����ȷ��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "��ҵծ�깺";
						break;
					case (int) BOND_BID_MARGIN_SUPPLY :
						strReturn = "��ҵծ�깺����";
						break;
					case (int) BOND_BID_MARGIN_REPAY :
						strReturn = "��ҵծ�깺����";
						break;
					case (int) BOND_BID_QUOTA_CONFIRM :
						strReturn = "��ҵծ�깺����ȷ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID, BOND_BID_MARGIN_SUPPLY, BOND_BID_MARGIN_REPAY, BOND_BID_QUOTA_CONFIRM };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPLY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//47.��ҵծ����
		public static class ENTERPRISE_BOND_TRANSACTION
		{
			public static final long ID = 47;
			public static final long BOND_BUYIN = 4701; //��ҵծ����
			public static final long BOND_SELL = 4702; //��ҵծ����
			public static final long BOND_ACCRUAL_IN = 4703; //��Ϣ
			public static final long BOND_MATURITY_REPAY = 4704; //���ڻ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "��ҵծ����";
						break;
					case (int) BOND_SELL :
						strReturn = "��ҵծ����";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "��Ϣ";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "���ڻ���";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//51.��תծһ�������깺 
		public static class TRANSFORMABLE_BOND_BID_ONLINE
		{
			public static final long ID = 51;
			public static final long BOND_BID_ONLINE = 5101; //��תծ�����깺
			public static final long BOND_BID_WIN = 5102; //��תծ�깺��ǩ
			public static final long BOND_BID_REPAY = 5103; //��תծ�깺����
			//����ʹ��
			public static final long BOND_SELL = 5104; //��תծ�����깺����
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BID_ONLINE :
						strReturn = "��תծ�����깺";
						break;
					case (int) BOND_BID_WIN :
						strReturn = "��תծ�깺��ǩ";
						break;
					case (int) BOND_BID_REPAY :
						strReturn = "��תծ�깺����";
						break;
						//					����ʹ��
					case (int) BOND_SELL :
						strReturn = "��תծ�����깺����";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID_ONLINE, BOND_BID_WIN, BOND_BID_REPAY, BOND_SELL };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							break;
						case 2 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_WIN;
							lArrayID[2] = BOND_BID_REPAY;
							//							����ʹ��
							lArrayID[3] = BOND_SELL;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//52.��תծһ�������깺
		public static class TRANSFORMABLE_BOND_BID
		{
			public static final long ID = 52;
			public static final long BOND_BID = 5201; //��תծ�����깺
			public static final long BOND_BID_MARGIN_SUPPAY = 5202; //��תծ�깺����
			public static final long BOND_BID_MARGIN_REPAY = 5203; //��תծ�깺����
			public static final long BOND_BID_QUOTA_CONFIRM = 5204; //��תծ�깺����ȷ��
			//			����ʹ��
			public static final long BOND_SELL = 5205; //��תծ�����깺����
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BID :
						strReturn = "��תծ�����깺";
						break;
					case (int) BOND_BID_MARGIN_SUPPAY :
						strReturn = "��תծ�깺����";
						break;
					case (int) BOND_BID_MARGIN_REPAY :
						strReturn = "��תծ�깺����";
						break;
					case (int) BOND_BID_QUOTA_CONFIRM :
						strReturn = "��תծ�깺����ȷ��";
						break;
						//					����ʹ��
					case (int) BOND_SELL :
						strReturn = "��תծ�����깺����";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BID, BOND_BID_MARGIN_SUPPAY, BOND_BID_MARGIN_REPAY, BOND_BID_QUOTA_CONFIRM, BOND_SELL };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							break;
						case 5 :
							lArrayID = new long[5];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							lArrayID[3] = BOND_BID_QUOTA_CONFIRM;
							//							����ʹ��
							lArrayID[4] = BOND_SELL;
							break;

					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//53.��תծ����
		public static class TRANSFORMABLE_BOND_TRANSACTION
		{
			public static final long ID = 53;
			public static final long BOND_BUYIN = 5301; //��תծ����
			public static final long BOND_SELL = 5302; //��תծ����
			public static final long BOND_ACCRUAL_IN = 5303; //��Ϣ
			public static final long BOND_MATURITY_REPAY = 5304; //���ڻ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_BUYIN :
						strReturn = "��תծ����";
						break;
					case (int) BOND_SELL :
						strReturn = "��תծ����";
						break;
					case (int) BOND_ACCRUAL_IN :
						strReturn = "��Ϣ";
						break;
					case (int) BOND_MATURITY_REPAY :
						strReturn = "���ڻ���";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_BUYIN, BOND_SELL, BOND_ACCRUAL_IN, BOND_MATURITY_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							lArrayID[3] = BOND_MATURITY_REPAY;
							break;
						case 5 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BUYIN;
							lArrayID[1] = BOND_SELL;
							lArrayID[2] = BOND_ACCRUAL_IN;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//54.ծת�� 
		public static class DEBT_TO_EQUITY
		{
			public static final long ID = 54;
			public static final long DEBT_TO_EQUITY = 5401; //ծת��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) DEBT_TO_EQUITY :
						strReturn = "ծת��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { DEBT_TO_EQUITY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//56.���ʽ����һ�������깺
		public static class ENCLOSED_FUND_BID_ONLINE
		{
			public static final long ID = 56;
			public static final long FUND_BID_ONLINE = 5601; //���ʽ���������깺
			public static final long FUND_BID_WIN = 5602; //���ʽ�����깺��ǩ
			public static final long FUND_BID_REPAY = 5603; //���ʽ�����깺����
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_BID_ONLINE :
						strReturn = "���ʽ���������깺";
						break;
					case (int) FUND_BID_WIN :
						strReturn = "���ʽ�����깺��ǩ";
						break;
					case (int) FUND_BID_REPAY :
						strReturn = "���ʽ�����깺����";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BID_ONLINE, FUND_BID_WIN, FUND_BID_REPAY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
							break;
						case 2 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
							break;
						case 5 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID_ONLINE;
							lArrayID[1] = FUND_BID_WIN;
							lArrayID[2] = FUND_BID_REPAY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//57.���ʽ����һ�������깺
		public static class ENCLOSED_FUND_BID
		{
			public static final long ID = 57;
			public static final long FUND_BID = 5701; //���ʽ���������깺
			public static final long FUND_BID_MARGIN_SUPPLY = 5702; //���ʽ�����깺����
			public static final long FUND_BID_MARGIN_REPAY = 5703; //���ʽ�����깺����
			public static final long FUND_BID_QUOTA_CONFIRM = 5704; //���ʽ�����깺����ȷ��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_BID :
						strReturn = "���ʽ���������깺";
						break;
					case (int) FUND_BID_MARGIN_SUPPLY :
						strReturn = "���ʽ�����깺����";
						break;
					case (int) FUND_BID_MARGIN_REPAY :
						strReturn = "���ʽ�����깺����";
						break;
					case (int) FUND_BID_QUOTA_CONFIRM :
						strReturn = "���ʽ�����깺����ȷ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BID, FUND_BID_MARGIN_SUPPLY, FUND_BID_MARGIN_REPAY, FUND_BID_QUOTA_CONFIRM };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;
							lArrayID[3] = FUND_BID_QUOTA_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;
							lArrayID[3] = FUND_BID_QUOTA_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;

							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_MARGIN_SUPPLY;
							lArrayID[2] = FUND_BID_MARGIN_REPAY;
							lArrayID[3] = FUND_BID_QUOTA_CONFIRM;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//58.���ʽ�������
		public static class ENCLOSED_FUND_TRANSACTION
		{
			public static final long ID = 58;
			public static final long FUND_BUYIN = 5801; //���ʽ��������
			public static final long FUND_SELL = 5802; //���ʽ��������
			public static final long FUND_MELON_CUTTING = 5803; //���ʽ����ֺ�
			public static final long FUND_MATURITY_SETTLE = 5804; //���ʽ����������
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_BUYIN :
						strReturn = "���ʽ��������";
						break;
					case (int) FUND_SELL :
						strReturn = "���ʽ��������";
						break;
					case (int) FUND_MELON_CUTTING :
						strReturn = "���ʽ����ֺ�";
						break;
					case (int) FUND_MATURITY_SETTLE :
						strReturn = "���ʽ����������";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BUYIN, FUND_SELL, FUND_MELON_CUTTING, FUND_MATURITY_SETTLE };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
							break;
						case 2 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
							break;
						case 3 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
							break;
						case 5 :
							lArrayID = new long[4];
							lArrayID[0] = FUND_BUYIN;
							lArrayID[1] = FUND_SELL;
							lArrayID[2] = FUND_MELON_CUTTING;
							lArrayID[3] = FUND_MATURITY_SETTLE;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//61.����ʽ����һ���Ϲ� 
		public static class MUTUAL_FUND_SUBSCRIBE
		{
			public static final long ID = 61;
			public static final long FUND_SUBSCRIBE = 6101; //����ʽ����һ���Ϲ�
			public static final long FUND_SUBSCRIBE_CONFIRM = 6102; //����ʽ����һ���Ϲ�ȷ��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_SUBSCRIBE :
						strReturn = "����ʽ����һ���Ϲ�";
						break;
					case (int) FUND_SUBSCRIBE_CONFIRM :
						strReturn = "����ʽ����һ���Ϲ�ȷ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_SUBSCRIBE, FUND_SUBSCRIBE_CONFIRM };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_SUBSCRIBE_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_SUBSCRIBE_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_SUBSCRIBE;
							break;
						case 5 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_SUBSCRIBE_CONFIRM;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//62.����ʽ��������깺
		public static class MUTUAL_FUND_BID
		{
			public static final long ID = 62;
			public static final long FUND_BID = 6201; //����ʽ��������깺
			public static final long FUND_BID_CONFIRM = 6202; //����ʽ��������깺ȷ��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_BID :
						strReturn = "����ʽ��������깺";
						break;
					case (int) FUND_BID_CONFIRM :
						strReturn = "����ʽ��������깺ȷ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_BID, FUND_BID_CONFIRM };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_CONFIRM;
							break;
						case 2 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_CONFIRM;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_BID;
							break;
						case 5 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_BID;
							lArrayID[1] = FUND_BID_CONFIRM;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//63.����ʽ����������
		public static class MUTUAL_FUND_REDEEM
		{
			public static final long ID = 63;
			public static final long FUND_REDEEM = 6301; //����ʽ����������
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_REDEEM :
						strReturn = "����ʽ����������";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_REDEEM };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
							break;
						case 2 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_REDEEM;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//64.����ʽ����ֺ�
		public static class MUTUAL_FUND_MELON_CUTTING
		{
			public static final long ID = 64;
			public static final long FUND_CASH_MELON_CUTTING = 6401; //����ʽ�����ֽ�ֺ�
			public static final long FUND_SHARE_MELON_CUTTING = 6402; //����ʽ����ݶ�ֺ�
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_CASH_MELON_CUTTING :
						strReturn = "����ʽ�����ֽ�ֺ�";
						break;
					case (int) FUND_SHARE_MELON_CUTTING :
						strReturn = "����ʽ����ݶ�ֺ�";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_CASH_MELON_CUTTING, FUND_SHARE_MELON_CUTTING };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							lArrayID[1] = FUND_SHARE_MELON_CUTTING;
							break;
						case 2 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							lArrayID[1] = FUND_SHARE_MELON_CUTTING;
							break;
						case 3 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							break;
						case 5 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_CASH_MELON_CUTTING;
							lArrayID[1] = FUND_SHARE_MELON_CUTTING;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
			public static final void showNoticeFormList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				showList(out, strControlName, 3, lSelectValue, isNeedAll, isNeedBlank, strProperty);
			}
		}
		//71.�ʲ��ع�
		public static class CAPITAL_REPURCHASE
		{
            public static final long ID = 71;
            public static final long CAPITAL_REPURCHASE = 7101; //�ʲ��ع�
            public static final long REPURCHASE_NOTIFY = 7102; //��ͬ�������ع���
            public static final long PAYBACK_NOTIFY = 7103; //��ͬ�������أ��ع���
            public static final long INTEREST_PAYMENT = 7104; //��Ϣ֧��֪ͨ��
            public static final long INTEREST_PLAN = 7105; //������Ϣ
            public static final long AVERAGE_NOTIFY = 7106; //��ͬ���루�ع���
            public static final long ACCEPT_NOTIFY = 7107; //��ͬ���빺�أ��ع���
            public static final long BREAK_NOTIFY = 7108; //��ͬ��������ϣ�
            public static final long INVEST_BREAK = 7109; //��ͬ���루��ϣ�
            
            
            public static final long CAPITAL_PAYMENT = 7110; //֧��ת�ÿ���
            public static final long INTEREST_PAYBACK = 7111; //��Ϣ�ջ�֪ͨ��
            public static final long REPURCHASE_CAPITAL = 7112; //���루�ع�������֪ͨ��
            public static final long ACCEPT_CAPITAL = 7113; //�������ع�������֪ͨ��
            public static final long CAPITAL_PAYBACK = 7114; //�յ�ת�ÿ���
           
            
            //
            public static final String getName(long lCode)
            {
                String strReturn = ""; //��ʼ������ֵ
                switch ((int) lCode)
                {
                    case (int) CAPITAL_REPURCHASE :
                        strReturn = "�ʲ��ع�";
                        break;
                    case (int) REPURCHASE_NOTIFY :
                        strReturn = "�������ع���";
                        break;
                    case (int) PAYBACK_NOTIFY :
                        strReturn = "�������أ��ع���";
                        break;
                    case (int) INTEREST_PAYMENT :
                        strReturn = "��Ϣ֧��֪ͨ��";
                        break;
                    case (int) INTEREST_PLAN :
                        strReturn = "������Ϣ";
                        break;
                    case (int) AVERAGE_NOTIFY :
                        strReturn = "���루�ع���";
                        break;
                    case (int) ACCEPT_NOTIFY :
                        strReturn = "���빺�أ��ع���";
                        break;
                    case (int) BREAK_NOTIFY :
                        strReturn = "��������ϣ�";
                        break;
                    case (int) INVEST_BREAK :
                        strReturn = "���루��ϣ�";
                        break;
                    case (int) CAPITAL_PAYMENT :
                        strReturn = "֧��ת�ÿ���";
                        break;
                    case (int) INTEREST_PAYBACK :
                        strReturn = "��Ϣ�ջ�֪ͨ��";
                        break;
                    case (int) REPURCHASE_CAPITAL :
                        strReturn = "���루�ع�������֪ͨ��";
                        break;
                    case (int) ACCEPT_CAPITAL :
                        strReturn = "�������ع�������֪ͨ��";
                        break;
                    case (int) CAPITAL_PAYBACK :
                        strReturn = "�յ�ת�ÿ���";
                        break;
                }
                return strReturn;
            }
            //
            public static final long[] getAllCode()
            {
                long[] lTemp = { CAPITAL_REPURCHASE, REPURCHASE_NOTIFY, PAYBACK_NOTIFY, INTEREST_PAYMENT };
                return lTemp;
            }
            /**
              * ��ʾ�����б�
              * 
              * @param out
              * @param strControlName,
              *            �ؼ�����
              * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
              * @param lSelectValue
              * @param isNeedAll���Ƿ���Ҫ��ȫ���
              * @param isNeedBlank
              *            �Ƿ���Ҫ�հ���
              * @param strProperty
              */
            public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
            {
                long[] lArrayID = null;
                String[] strArrayName = null;
                try
                {
                    switch (nType)
                    {
                        case 0 :
                            lArrayID = getAllCode();
                            break;
                        case 1 :
                            lArrayID = new long[3];
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = PAYBACK_NOTIFY;
                            lArrayID[2] = INTEREST_PAYMENT;
                            break;
                        case 2 :
                            lArrayID = new long[6];
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = PAYBACK_NOTIFY;
                            lArrayID[2] = INTEREST_PAYMENT;
                            lArrayID[3] = AVERAGE_NOTIFY;
                            lArrayID[4] = ACCEPT_NOTIFY;
                            lArrayID[5] = BREAK_NOTIFY;
                            break; 
                        case 3 :
                            lArrayID = new long[4];
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = AVERAGE_NOTIFY;
                            lArrayID[2] = BREAK_NOTIFY;
                            lArrayID[3] = INVEST_BREAK;
                            break;
                        case 4 :
                            lArrayID = new long[2];
                            lArrayID[0] = REPURCHASE_NOTIFY;
                            lArrayID[1] = BREAK_NOTIFY;
                            break;
                        case 5 :
                            lArrayID = new long[3];
                            lArrayID[0] = CAPITAL_PAYMENT;
                            lArrayID[1] = INTEREST_PAYBACK;
                            lArrayID[2] = REPURCHASE_CAPITAL;
                            break;
                        case 6 :
                            lArrayID = new long[1];
                            lArrayID[0] = CAPITAL_PAYMENT;
                            break; 
                        case 7 :
                        	 lArrayID = new long[3];
                             lArrayID[0] = CAPITAL_PAYBACK;
                             lArrayID[1] = INTEREST_PAYMENT;
                             lArrayID[2] = ACCEPT_CAPITAL;
                            break;
                        case 8 :
                            lArrayID = new long[1];
                            lArrayID[0] = CAPITAL_PAYBACK;
                            break;
                    }
                    strArrayName = new String[lArrayID.length];
                    for (int i = 0; i < lArrayID.length; i++)
                    {
                        strArrayName[i] = getName(lArrayID[i]);
                    }
                    showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
                }
                catch (Exception ex)
                {
                    Log.print(ex.toString());
                }
            }
        }
		//73.ί�����
		public static class ENTRUST_FINANCING
		{
			public static final long ID = 73;
			public static final long ENTRUST_FINANCING = 7301; //ί�����
			public static final long CORPORACITY_PAYMENT_NOTIFY = 7302; //����֧��֪ͨ��
			public static final long CORPORACITY_DRAWBACK_NOTIFY = 7303; //�����ջ�֪ͨ��
			public static final long INCOME_DRAWBACK_NOTIFY = 7304; //�����ջ�֪ͨ��
			//�¼�����
			public static final long SECURITIES_PAYMENT_NOTIFY = 7305; //֤ȯת��ҵ��֪ͨ��
			public static final long SECURITIES_DRAWBACK_NOTIFY = 7306; //֤ȯ�ջ�ҵ��֪ͨ��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) ENTRUST_FINANCING :
						strReturn = "ί�����";
						break;
					case (int) CORPORACITY_PAYMENT_NOTIFY :
						strReturn = "����֧��֪ͨ��";
						break;
					case (int) CORPORACITY_DRAWBACK_NOTIFY :
						strReturn = "�����ջ�֪ͨ��";
						break;
					case (int) INCOME_DRAWBACK_NOTIFY :
						strReturn = "�����ջ�֪ͨ��";
						break;
					case (int) SECURITIES_PAYMENT_NOTIFY :
						strReturn = "֤ȯת��֪ͨ��";
						break;
					case (int) SECURITIES_DRAWBACK_NOTIFY :
						strReturn = "֤ȯ�ջ�֪ͨ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { ENTRUST_FINANCING, CORPORACITY_PAYMENT_NOTIFY, CORPORACITY_DRAWBACK_NOTIFY, INCOME_DRAWBACK_NOTIFY, SECURITIES_PAYMENT_NOTIFY, SECURITIES_DRAWBACK_NOTIFY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[5];
							lArrayID[0] = CORPORACITY_PAYMENT_NOTIFY;
							lArrayID[1] = CORPORACITY_DRAWBACK_NOTIFY;
							lArrayID[2] = INCOME_DRAWBACK_NOTIFY;
							lArrayID[3] = SECURITIES_PAYMENT_NOTIFY;
							lArrayID[4] = SECURITIES_DRAWBACK_NOTIFY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//75.������� 
		public static class ENTRUSTED_FINANCING
		{
			public static final long ID = 75;
			public static final long ENTRUSTED_FINANCING = 7501; //�������
			public static final long CORPORACITY_DRAWBACK_NOTIFY = 7502; //�����ջ�֪ͨ��
			public static final long CORPORACITY_PAYMENT_NOTIFY = 7503; //����֧��֪ͨ��
			public static final long INCOME_PAYMENT_NOTIFY = 7504; //����֧��֪ͨ��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) ENTRUSTED_FINANCING :
						strReturn = "ί�����";
						break;
					case (int) CORPORACITY_PAYMENT_NOTIFY :
						strReturn = "����֧��֪ͨ��";
						break;
					case (int) CORPORACITY_DRAWBACK_NOTIFY :
						strReturn = "�����ջ�֪ͨ��";
						break;
					case (int) INCOME_PAYMENT_NOTIFY :
						strReturn = "����֧��֪ͨ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { ENTRUSTED_FINANCING, CORPORACITY_PAYMENT_NOTIFY, CORPORACITY_DRAWBACK_NOTIFY, INCOME_PAYMENT_NOTIFY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = ENTRUSTED_FINANCING;
							lArrayID[1] = CORPORACITY_DRAWBACK_NOTIFY;
							lArrayID[2] = CORPORACITY_PAYMENT_NOTIFY;
							lArrayID[3] = INCOME_PAYMENT_NOTIFY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//77.�ṹ��Ͷ�� 
		public static class FOREIGN_CURRENCY_INVESTMENT
		{
			public static final long ID = 77;
			public static final long FOREIGN_CURRENCY_INVESTMENT = 7701; //�ṹ��Ͷ��
			public static final long INVESTMENT_PAYMENT_NOTIFY = 7702; //֧��Ͷ�ʿ�֪ͨ��
			public static final long INVESTMENT_DRAWBACK_NOTIFY = 7703; //Ͷ�ʿ��ջ�֪ͨ��
			public static final long INCOME_DRAWBACK_NOTIFY = 7704; //Ͷ�������ջ�֪ͨ��
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FOREIGN_CURRENCY_INVESTMENT :
						strReturn = "�ṹ��Ͷ��";
						break;
					case (int) INVESTMENT_PAYMENT_NOTIFY :
						strReturn = "֧��Ͷ�ʿ�֪ͨ��";
						break;
					case (int) INVESTMENT_DRAWBACK_NOTIFY :
						strReturn = "Ͷ�ʿ��ջ�֪ͨ��";
						break;
					case (int) INCOME_DRAWBACK_NOTIFY :
						strReturn = "Ͷ�������ջ�֪ͨ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FOREIGN_CURRENCY_INVESTMENT, INVESTMENT_PAYMENT_NOTIFY, INVESTMENT_DRAWBACK_NOTIFY, INCOME_DRAWBACK_NOTIFY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 : //�ṹ��Ͷ��ҵ��֪ͨ��ʹ��
							lArrayID = new long[3];
							lArrayID[0] = INVESTMENT_PAYMENT_NOTIFY;
							lArrayID[1] = INVESTMENT_DRAWBACK_NOTIFY;
							lArrayID[2] = INCOME_DRAWBACK_NOTIFY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//79.��ȨͶ�� 
		public static class STOCK_INVESTMENT
		{
			public static final long ID = 79;
		}
		//81.ծȯ����
		public static class BOND_UNDERWRITING
		{
			public static final long ID = 81;
			public static final long BOND_UNDERWRITING = 8101; //ծȯ����
			public static final long UNDERWRITING_DRAWBACK_NOTIFY = 8102; //ծȯ�����տ�֪ͨ��
			public static final long UNDERWRITING_PAYMENT_NOTIFY = 8103; //֧��ծȯ������֪ͨ��
			public static final long INCOME_DRAWBACK_NOTIFY = 8104; //�������տ�֪ͨ��
			public static final long BOND_DRAWBACK_NOTIFY = 8105; //ծȯ��ȡ֪ͨ��
			public static final long CONTRACT_END = 8106; //��ͬ����
			
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) BOND_UNDERWRITING :
						strReturn = "ծȯ����";
						break;
					case (int) UNDERWRITING_DRAWBACK_NOTIFY :
						strReturn = "ծȯ�����տ�֪ͨ��";
						break;
					case (int) UNDERWRITING_PAYMENT_NOTIFY :
						strReturn = "֧��ծȯ������֪ͨ��";
						break;
					case (int) INCOME_DRAWBACK_NOTIFY :
						strReturn = "�������տ�֪ͨ��";
						break;
					case (int) BOND_DRAWBACK_NOTIFY :
						strReturn = "ծȯ��ȡ֪ͨ��";
						break;
					case (int) CONTRACT_END :
						strReturn = "��ͬ����";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { BOND_UNDERWRITING, BOND_UNDERWRITING, UNDERWRITING_PAYMENT_NOTIFY, INCOME_DRAWBACK_NOTIFY, UNDERWRITING_DRAWBACK_NOTIFY };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[4];
							lArrayID[0] = BOND_DRAWBACK_NOTIFY;
							lArrayID[1] = UNDERWRITING_PAYMENT_NOTIFY;
							lArrayID[2] = INCOME_DRAWBACK_NOTIFY;
							lArrayID[3] = UNDERWRITING_DRAWBACK_NOTIFY;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//83.���մ���
		public static class INSURANCE
		{
			public static final long ID = 83;
		}
		//85.�ʽ𻮲�  
		public static class CAPITAL_TRANSFER
		{
			public static final long ID = 85;
			public static final long CAPITAL_IN = 8501; //�ʽ����
			public static final long CAPITAL_OUT = 8502; //�ʽ�ȡ��
			//��Ʊ����
			public static final long INITIAL_OFFERING_BID_ONLINE = 8503; //��Ʊ�׷������깺�ʽ𻮲�
			public static final long INITIAL_OFFERING_BID_REPAY = 8504; //��Ʊ�׷��깺�����ʽ𻮲�
			public static final long PROMOTION_BID_ONLINE = 8505; //��Ʊ���������깺�ʽ𻮲�
			public static final long PROMOTION_BID_REPAY = 8506; //��Ʊ�����깺�����ʽ𻮲�
			//��Ʊ����
			public static final long STOCK_BUYIN = 8507; //��Ʊ�����ʽ𻮲�
			//��תծ����
			public static final long BOND_BID_ONLINE = 8508; //��תծ�����깺�ʽ𻮲�
			public static final long BOND_BID_REPAY = 8509; //��תծ�깺�����ʽ𻮲�
			//			��תծ����
			public static final long BOND_BUYIN = 8510; //��תծ�����ʽ𻮲�
			//			ծת��
			public static final long DEBT_TO_EQUITY = 8511; //ծת���ʽ𻮲�
			//			����ʽ����
			public static final long FUND_SUBSCRIBE = 8512; //����ʽ����һ���Ϲ��ʽ𻮲�
			public static final long FUND_BID = 8513; //����ʽ��������깺�ʽ𻮲�
			public static final long FUND_TRANSFER = 8514; //����ת���ʽ𻮲�

			//��Ʊ����
			public static final long INITIAL_OFFERING_BID = 8515; //��Ʊ�׷������깺�ʽ𻮲�
			public static final long INITIAL_OFFERING_MARGIN_SUPPLY = 8516; //��Ʊ�׷������ʽ𻮲�
			public static final long INITIAL_OFFERING_MARGIN_REPAY = 8517; //��Ʊ�׷������ʽ𻮲�
			public static final long PROMOTION_BID = 8518; //��Ʊ���������깺�ʽ𻮲�
			public static final long PROMOTION_BID_MARGIN_SUPPLY = 8519; //��Ʊ���������ʽ𻮲�
			public static final long PROMOTION_BID_MARGIN_REPAY = 8520; //��Ʊ���������ʽ𻮲�

			//			��תծ����
			public static final long BOND_BID = 8521; //��תծ�����깺�ʽ𻮲�
			public static final long BOND_BID_MARGIN_SUPPAY = 8522; //��תծ�깺�����ʽ𻮲�
			public static final long BOND_BID_MARGIN_REPAY = 8523; //��תծ�깺�����ʽ𻮲�

			//			����ʽ����
			public static final long FUND_REDEEM = 8524; //����ʽ�����������ʽ𻮲�
			public static final long FUND_CASH_MELON_CUTTING = 8525; //����ʽ����ֺ��ʽ𻮲�

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN :
						strReturn = "�ʽ����";
						break;
					case (int) CAPITAL_OUT :
						strReturn = "�ʽ�ȡ��";
						break;

						//��Ʊһ������	
					case (int) INITIAL_OFFERING_BID_ONLINE :
						strReturn = "��Ʊ�׷������깺�ʽ𻮲�";
						break;
					case (int) INITIAL_OFFERING_BID_REPAY :
						strReturn = "��Ʊ�׷��깺�����ʽ𻮲�";
						break;
					case (int) PROMOTION_BID_ONLINE :
						strReturn = "��Ʊ���������깺�ʽ𻮲�";
						break;
					case (int) PROMOTION_BID_REPAY :
						strReturn = "��Ʊ�����깺�����ʽ𻮲�";
						break;

						//��Ʊһ������	
					case (int) INITIAL_OFFERING_BID :
						strReturn = "��Ʊ�׷������깺�ʽ𻮲�";
						break;
					case (int) INITIAL_OFFERING_MARGIN_SUPPLY :
						strReturn = "��Ʊ�׷������ʽ𻮲�";
						break;
					case (int) INITIAL_OFFERING_MARGIN_REPAY :
						strReturn = "��Ʊ�׷������ʽ𻮲�";
						break;
					case (int) PROMOTION_BID :
						strReturn = "��Ʊ���������깺�ʽ𻮲�";
						break;
					case (int) PROMOTION_BID_MARGIN_SUPPLY :
						strReturn = "��Ʊ���������ʽ𻮲�";
						break;
					case (int) PROMOTION_BID_MARGIN_REPAY :
						strReturn = "��Ʊ���������ʽ𻮲�";
						break;

						//��Ʊ����
					case (int) STOCK_BUYIN :
						strReturn = "��Ʊ�����ʽ𻮲�";
						break;

						//��תծһ������
					case (int) BOND_BID_ONLINE :
						strReturn = "��תծ�����깺�ʽ𻮲�";
						break;
					case (int) BOND_BID_REPAY :
						strReturn = "��תծ�깺�����ʽ𻮲�";
						break;
						//��תծһ������
					case (int) BOND_BID :
						strReturn = "��תծ�����깺�ʽ𻮲�";
						break;
					case (int) BOND_BID_MARGIN_SUPPAY :
						strReturn = "��תծ�깺�����ʽ𻮲�";
						break;
					case (int) BOND_BID_MARGIN_REPAY :
						strReturn = "��תծ�깺�����ʽ𻮲�";
						break;

						//��תծ����
					case (int) BOND_BUYIN :
						strReturn = "��תծ�����ʽ𻮲�";
						break;
						//ծת��
					case (int) DEBT_TO_EQUITY :
						strReturn = "ծת���ʽ𻮲�";
						break;

						//����ʽ����
					case (int) FUND_SUBSCRIBE :
						strReturn = "����ʽ����һ���Ϲ��ʽ𻮲�";
						break;
					case (int) FUND_BID :
						strReturn = "����ʽ��������깺�ʽ𻮲�";
						break;
					case (int) FUND_REDEEM :
						strReturn = "����ʽ�����������ʽ𻮲�";
						break;
					case (int) FUND_CASH_MELON_CUTTING :
						strReturn = "����ʽ����ֺ��ʽ𻮲�";
						break;

					case (int) FUND_TRANSFER :
						strReturn = "����ת���ʽ𻮲�";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN, CAPITAL_OUT };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 3 :
							lArrayID = new long[2];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[1] = CAPITAL_OUT;
							break;
							//						��Ʊһ������	
						case 7 :
							lArrayID = new long[4];
							lArrayID[0] = INITIAL_OFFERING_BID_ONLINE;
							lArrayID[1] = INITIAL_OFFERING_BID_REPAY;
							lArrayID[2] = PROMOTION_BID_ONLINE;
							lArrayID[3] = PROMOTION_BID_REPAY;
							break;
							//						��Ʊ����
						case 8 :
							lArrayID = new long[1];
							lArrayID[0] = STOCK_BUYIN;
							break;
							//						��תծһ������
						case 9 :
							lArrayID = new long[2];
							lArrayID[0] = BOND_BID_ONLINE;
							lArrayID[1] = BOND_BID_REPAY;
							break;
							//						ծת��
						case 10 :
							lArrayID = new long[1];
							lArrayID[0] = DEBT_TO_EQUITY;
							break;
							//						����ʽ����   
						case 11 :
							lArrayID = new long[3];
							lArrayID[0] = FUND_SUBSCRIBE;
							lArrayID[1] = FUND_BID;
							lArrayID[2] = FUND_TRANSFER;
							break;
							//						��תծ����
						case 12 :
							lArrayID = new long[1];
							lArrayID[0] = BOND_BUYIN;
							break;
							//						��Ʊ����
						case 13 :
							lArrayID = new long[6];
							lArrayID[0] = INITIAL_OFFERING_BID;
							lArrayID[1] = INITIAL_OFFERING_MARGIN_SUPPLY;
							lArrayID[2] = INITIAL_OFFERING_MARGIN_REPAY;
							lArrayID[3] = PROMOTION_BID;
							lArrayID[4] = PROMOTION_BID_MARGIN_SUPPLY;
							lArrayID[5] = PROMOTION_BID_MARGIN_REPAY;
							break;
							//						��תծ����
						case 14 :
							lArrayID = new long[3];
							lArrayID[0] = BOND_BID;
							lArrayID[1] = BOND_BID_MARGIN_SUPPAY;
							lArrayID[2] = BOND_BID_MARGIN_REPAY;
							break;
							//						����ʽ����   
						case 15 :
							lArrayID = new long[2];
							lArrayID[0] = FUND_REDEEM;
							lArrayID[1] = FUND_CASH_MELON_CUTTING;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//87.�ʽ���Ϣ���� 
		public static class INTEREST_SETTLEMENT
		{
			public static final long ID = 87;
			public static final long INTEREST_SETTLEMENT = 8701; //�ʽ���Ϣ����
			public static final long OTHER_CAPITAL_IN = 8702; //�����ʽ���
			public static final long OTHER_CAPITAL_OUT = 8703; //�����ʽ𻮳�

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) INTEREST_SETTLEMENT :
						strReturn = "�ʽ���Ϣ����";
						break;
					case (int) OTHER_CAPITAL_IN :
						strReturn = "�����ʽ���";
						break;
					case (int) OTHER_CAPITAL_OUT :
						strReturn = "�����ʽ𻮳�";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { INTEREST_SETTLEMENT, OTHER_CAPITAL_IN, OTHER_CAPITAL_OUT };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[3];
							lArrayID[0] = INTEREST_SETTLEMENT;
							lArrayID[1] = OTHER_CAPITAL_IN;
							lArrayID[2] = OTHER_CAPITAL_OUT;
							break;
						case 5 :
							lArrayID = new long[1];
							lArrayID[0] = INTEREST_SETTLEMENT;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//89.�����ʽ�����  
		public static class OTHER_CAPITAL_SETTLEMENT
		{
			public static final long ID = 89;
			public static final long CAPITAL_IN = 8901; //�ʽ���������
			public static final long CAPITAL_OUT = 8902; //�ʽ�����֧��
			public static final long VENTURE_FUND_SETTLE = 8903; //���ɽ�����ջ���
			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) CAPITAL_IN :
						strReturn = "�ʽ����";
						break;
					case (int) CAPITAL_OUT :
						strReturn = "�ʽ�ȡ��";
						break;
					case (int) VENTURE_FUND_SETTLE :
						strReturn = "�ʽ�ȡ��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { CAPITAL_IN, CAPITAL_OUT, VENTURE_FUND_SETTLE };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[3];
							lArrayID[0] = CAPITAL_IN;
							lArrayID[0] = CAPITAL_OUT;
							lArrayID[0] = VENTURE_FUND_SETTLE;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//91.֤ȯ��ת
		public static class SECURITIES_TRANSFER
		{
			public static final long ID = 91;
			public static final long STOCK_IN = 9101; //֤ȯ��ת

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) STOCK_IN :
						strReturn = "֤ȯ��ת";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { STOCK_IN };
				return lTemp;
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = STOCK_IN;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//92.����ת��
		public static class FUND_TRANSFER
		{
			public static final long ID = 92;
			public static final long FUND_TRANSFER = 9201; //����ת��

			//
			public static final String getName(long lCode)
			{
				String strReturn = ""; //��ʼ������ֵ
				switch ((int) lCode)
				{
					case (int) FUND_TRANSFER :
						strReturn = "����ת��";
						break;
				}
				return strReturn;
			}
			//
			public static final long[] getAllCode()
			{
				long[] lTemp = { FUND_TRANSFER };
				return lTemp; 
			}
			/**
			  * ��ʾ�����б�
			  * 
			  * @param out
			  * @param strControlName,
			  *            �ؼ�����
			  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
			  * @param lSelectValue
			  * @param isNeedAll���Ƿ���Ҫ��ȫ���
			  * @param isNeedBlank
			  *            �Ƿ���Ҫ�հ���
			  * @param strProperty
			  */
			public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
			{
				long[] lArrayID = null;
				String[] strArrayName = null;
				try
				{
					switch (nType)
					{
						case 0 :
							lArrayID = getAllCode();
							break;
						case 1 :
							lArrayID = new long[1];
							lArrayID[0] = FUND_TRANSFER;
							break;
					}
					strArrayName = new String[lArrayID.length];
					for (int i = 0; i < lArrayID.length; i++)
					{
						strArrayName[i] = getName(lArrayID[i]);
					}
					showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
				}
				catch (Exception ex)
				{
					Log.print(ex.toString());
				}
			}
		}
		//93.��ת�ɱ� 
		public static class CARRY_COST
		{
			public static final long ID = 93;
			public static final long CARRY_COST = 9301; //��ת�ɱ� 
		}

		//801.������ע  
		public static class REMARK_APPROVAL
		{
			public static final long ID = 801;
		}
		//802.�����ע  
		public static class REMARK_TRANSACTION
		{
			public static final long ID = 802;
		}
		//�õ�ҵ�����͵�����
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				//�ʽ���
				case (int) CAPITAL_IN_CREDIT_EXTENSION.ID :
					strReturn = "�ʽ��������";
					break;
				case (int) CAPITAL_OUT_CREDIT_EXTENSION.ID :
					strReturn = "�ʽ�������";
					break;
				case (int) CAPITAL_LANDING.ID :
					strReturn = "�ʽ���ҵ��";
					break;
					//��Ʊ	
				case (int) STOCK_BID_ONLINE.ID :
					strReturn = "��Ʊһ�������깺";
					break;
				case (int) STOCK_BID.ID :
					strReturn = "��Ʊһ�������깺";
					break;
				case (int) STOCK_TRANSACTION.ID :
					strReturn = "��Ʊ����";
					break;
					//����Ʊ��	
				case (int) CENTRAL_BANK_NOTE_BID.ID :
					strReturn = "����Ʊ��һ��";
					break;
				case (int) CENTRAL_BANK_NOTE_TRANSACTION.ID :
					strReturn = "����Ʊ�ݶ���";
					break;
					//ծȯ�ع�	
				case (int) BANK_BOND_REPURCHASE.ID :
					strReturn = "���м�ծȯ�ع�";
					break;
				case (int) EXCHANGECENTER_BOND_REPURCHASE.ID :
					strReturn = "������ծȯ�ع�";
					break;
					//��ծ	
				case (int) BANK_NATIONAL_BOND_BID.ID :
					strReturn = "���м��ծһ��";
					break;
				case (int) BANK_NATIONAL_BOND_TRANSACTION.ID :
					strReturn = "���м��ծ����";
					break;
				case (int) EXCHANGECENTER_NATIONAL_BOND_BID.ID :
					strReturn = "��������ծһ��";
					break;
				case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID :
					strReturn = "��������ծ����";
					break;
					//����ծ	
				case (int) FINANCIAL_BOND_BID.ID :
					strReturn = "����ծһ��";
					break;
				case (int) FINANCIAL_BOND_TRANSACTION.ID :
					strReturn = "����ծ����";
					break;
					//�����Խ���ծ	
				case (int) POLICY_RELATED_FINANCIAL_BOND_BID.ID :
					strReturn = "�����Խ���ծһ��";
					break;
				case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID :
					strReturn = "�����Խ���ծ����";
					break;
					//��ҵծ	
				case (int) ENTERPRISE_BOND_BID.ID :
					strReturn = "��ҵծһ��";
					break;
				case (int) ENTERPRISE_BOND_TRANSACTION.ID :
					strReturn = "��ҵծ����";
					break;
					//��תծ	
				case (int) TRANSFORMABLE_BOND_BID_ONLINE.ID :
					strReturn = "��תծһ�������깺";
					break;
				case (int) TRANSFORMABLE_BOND_BID.ID :
					strReturn = "��תծһ�������깺";
					break;
				case (int) TRANSFORMABLE_BOND_TRANSACTION.ID :
					strReturn = "��תծ����";
					break;
				case (int) DEBT_TO_EQUITY.ID :
					strReturn = "ծת��";
					break;
					//���ʽ����	
				case (int) ENCLOSED_FUND_BID_ONLINE.ID :
					strReturn = "���ʽ����һ�������깺";
					break;
				case (int) ENCLOSED_FUND_BID.ID :
					strReturn = "���ʽ����һ�������깺";
					break;
				case (int) ENCLOSED_FUND_TRANSACTION.ID :
					strReturn = "���ʽ�������";
					break;
					//����ʽ����	
				case (int) MUTUAL_FUND_SUBSCRIBE.ID :
					strReturn = "����ʽ����һ���Ϲ�";
					break;
				case (int) MUTUAL_FUND_BID.ID :
					strReturn = "����ʽ��������깺";
					break;
				case (int) MUTUAL_FUND_REDEEM.ID :
					strReturn = "����ʽ����������";
					break;
				case (int) MUTUAL_FUND_MELON_CUTTING.ID :
					strReturn = "����ʽ����ֺ�";
					break;
				case (int) CAPITAL_REPURCHASE.ID :
					strReturn = "�ʲ��ع�";
					break;
				case (int) ENTRUST_FINANCING.ID :
					strReturn = "ί�����";
					break;
				case (int) ENTRUSTED_FINANCING.ID :
					strReturn = "�������";
					break;
				case (int) FOREIGN_CURRENCY_INVESTMENT.ID :
					strReturn = "�ṹ��Ͷ��";
					break;
				case (int) STOCK_INVESTMENT.ID :
					strReturn = "��ȨͶ��";
					break;
				case (int) BOND_UNDERWRITING.ID :
					strReturn = "ծȯ����";
					break;
				case (int) INSURANCE.ID :
					strReturn = "���մ���";
					break;
				case (int) CAPITAL_TRANSFER.ID :
					strReturn = "�ʽ𻮲�";
					break;
				case (int) INTEREST_SETTLEMENT.ID :
					strReturn = "�ʽ���Ϣ����";
					break;
				case (int) OTHER_CAPITAL_SETTLEMENT.ID :
					strReturn = "�����ʽ�����";
					break;
				case (int) SECURITIES_TRANSFER.ID :
					strReturn = "֤ȯ��ת";
					break;
				case (int) FUND_TRANSFER.ID :
					strReturn = "����ת";
					break;
				case (int) CARRY_COST.ID :
					strReturn = "��ת�ɱ�";
					break;
				case (int) REMARK_APPROVAL.ID :
					strReturn = "������ע";
					break;
				case (int) REMARK_TRANSACTION.ID :
					strReturn = "�����ע";
					break;
			}
			return strReturn;
		}

		//����
		public final static int TRANS_GROUP_REPAY = 0;
		//����
		public final static int TRANSTYPE_GROUP_SUPPLY = 1;
		/**
		  * ���ط���ǲ���
		  * -1 ��������Ҳ���������...
		  * 0 ������
		  * 1 ������
		*/
		public static final long isRepayOrSupply(long transactionTypeId)
		{
			long lReturn = -1; //��ʼ������ֵ

			switch ((int) transactionTypeId)
			{
				//�ʽ���ҵ��	
				case (int) CAPITAL_LANDING.CAPITAL_IN_REPAY :
				case (int) CAPITAL_LANDING.CAPITAL_OUT_REPAY :
					lReturn = TRANS_GROUP_REPAY; //����
					break;

					//��Ʊһ�������깺	
				case (int) STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY :
				case (int) STOCK_BID_ONLINE.PROMOTION_BID_REPAY :
					//lReturn = TRANS_GROUP_REPAY;//����
					lReturn = -1; //����Ҫ�����ж�
					break;

					//��Ʊһ�������깺
				case (int) STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY :
				case (int) STOCK_BID.PROMOTION_BID_MARGIN_REPAY :
					lReturn = TRANS_GROUP_REPAY; //����
					break;

				case (int) STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY :
				case (int) STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY :
					lReturn = TRANSTYPE_GROUP_SUPPLY; //����
					break;

					//��ҵծ	
				case (int) ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY :
					//lReturn = TRANS_GROUP_REPAY;//����
					lReturn = -1; //����Ҫ�����ж�
					break;
				case (int) ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY :
					//lReturn = TRANSTYPE_GROUP_SUPPLY;//����
					lReturn = -1; //����Ҫ�����ж�
					break;

					//��תծ	
				case (int) TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY :
				case (int) TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY :
					//lReturn = TRANS_GROUP_REPAY;//����
					lReturn = -1; //����Ҫ�����ж�
					break;
				case (int) TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY :
					lReturn = TRANSTYPE_GROUP_SUPPLY; //����
					break;

					//���ʽ����	
				case (int) ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY :
					lReturn = -1; //����Ҫ�����ж�
				case (int) ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY :
					lReturn = TRANS_GROUP_REPAY; //����
					break;
				case (int) ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY :
					lReturn = TRANSTYPE_GROUP_SUPPLY; //����
					break;
				default :
					lReturn = -1; //������Ҳ������

			}
			return lReturn;

		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					CAPITAL_IN_CREDIT_EXTENSION.ID,
					CAPITAL_OUT_CREDIT_EXTENSION.ID,
					CAPITAL_LANDING.ID,
					STOCK_BID_ONLINE.ID,
					STOCK_BID.ID,
					STOCK_TRANSACTION.ID,
					CENTRAL_BANK_NOTE_BID.ID,
					CENTRAL_BANK_NOTE_TRANSACTION.ID,
					BANK_BOND_REPURCHASE.ID,
					EXCHANGECENTER_BOND_REPURCHASE.ID,
					BANK_NATIONAL_BOND_BID.ID,
					BANK_NATIONAL_BOND_TRANSACTION.ID,
					EXCHANGECENTER_NATIONAL_BOND_BID.ID,
					EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID,
					FINANCIAL_BOND_BID.ID,
					FINANCIAL_BOND_TRANSACTION.ID,
					POLICY_RELATED_FINANCIAL_BOND_BID.ID,
					POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID,
					ENTERPRISE_BOND_BID.ID,
					ENTERPRISE_BOND_TRANSACTION.ID,
					TRANSFORMABLE_BOND_BID_ONLINE.ID,
					TRANSFORMABLE_BOND_BID.ID,
					TRANSFORMABLE_BOND_TRANSACTION.ID,
					DEBT_TO_EQUITY.ID,
					ENCLOSED_FUND_BID_ONLINE.ID,
					ENCLOSED_FUND_BID.ID,
					ENCLOSED_FUND_TRANSACTION.ID,
					MUTUAL_FUND_SUBSCRIBE.ID,
					MUTUAL_FUND_BID.ID,
					MUTUAL_FUND_REDEEM.ID,
					MUTUAL_FUND_MELON_CUTTING.ID,
					CAPITAL_REPURCHASE.ID,
					ENTRUST_FINANCING.ID,
					ENTRUSTED_FINANCING.ID,
					FOREIGN_CURRENCY_INVESTMENT.ID,
					STOCK_INVESTMENT.ID,
					BOND_UNDERWRITING.ID,
					INSURANCE.ID,
					CAPITAL_TRANSFER.ID,
					INTEREST_SETTLEMENT.ID,
					OTHER_CAPITAL_SETTLEMENT.ID,
					SECURITIES_TRANSFER.ID,
					FUND_TRANSFER.ID,
					CARRY_COST.ID,
					REMARK_APPROVAL.ID,
					REMARK_TRANSACTION.ID };
			return lTemp;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[42];
						lArrayID[0] = CAPITAL_IN_CREDIT_EXTENSION.ID;
						lArrayID[1] = CAPITAL_OUT_CREDIT_EXTENSION.ID;
						lArrayID[2] = CAPITAL_LANDING.ID;
						lArrayID[3] = STOCK_BID_ONLINE.ID;
						lArrayID[4] = STOCK_BID.ID;
						lArrayID[5] = STOCK_TRANSACTION.ID;
						lArrayID[6] = CENTRAL_BANK_NOTE_BID.ID;
						lArrayID[7] = CENTRAL_BANK_NOTE_TRANSACTION.ID;
						lArrayID[8] = BANK_BOND_REPURCHASE.ID;
						lArrayID[9] = EXCHANGECENTER_BOND_REPURCHASE.ID;
						lArrayID[10] = BANK_NATIONAL_BOND_BID.ID;
						lArrayID[11] = BANK_NATIONAL_BOND_TRANSACTION.ID;
						lArrayID[12] = EXCHANGECENTER_NATIONAL_BOND_BID.ID;
						lArrayID[13] = EXCHANGECENTER_NATIONAL_BOND_TRANSACTION.ID;
						lArrayID[14] = FINANCIAL_BOND_BID.ID;
						lArrayID[15] = FINANCIAL_BOND_TRANSACTION.ID;
						lArrayID[16] = POLICY_RELATED_FINANCIAL_BOND_BID.ID;
						lArrayID[17] = POLICY_RELATED_FINANCIAL_BOND_TRANSACTION.ID;
						lArrayID[18] = ENTERPRISE_BOND_BID.ID;
						lArrayID[19] = ENTERPRISE_BOND_TRANSACTION.ID;
						lArrayID[20] = TRANSFORMABLE_BOND_BID_ONLINE.ID;
						lArrayID[21] = TRANSFORMABLE_BOND_BID.ID;
						lArrayID[22] = TRANSFORMABLE_BOND_TRANSACTION.ID;
						lArrayID[23] = DEBT_TO_EQUITY.ID;
						lArrayID[24] = ENCLOSED_FUND_BID_ONLINE.ID;
						lArrayID[25] = ENCLOSED_FUND_BID.ID;
						lArrayID[26] = ENCLOSED_FUND_TRANSACTION.ID;
						lArrayID[27] = MUTUAL_FUND_SUBSCRIBE.ID;
						lArrayID[28] = MUTUAL_FUND_BID.ID;
						lArrayID[29] = MUTUAL_FUND_REDEEM.ID;
						lArrayID[30] = MUTUAL_FUND_MELON_CUTTING.ID;
						lArrayID[31] = CAPITAL_REPURCHASE.ID;
						lArrayID[32] = ENTRUST_FINANCING.ID;
						lArrayID[33] = ENTRUSTED_FINANCING.ID;
						lArrayID[34] = FOREIGN_CURRENCY_INVESTMENT.ID;
						lArrayID[35] = STOCK_INVESTMENT.ID;
						lArrayID[36] = BOND_UNDERWRITING.ID;
						lArrayID[37] = INSURANCE.ID;
						lArrayID[38] = CAPITAL_TRANSFER.ID;
						lArrayID[39] = INTEREST_SETTLEMENT.ID;
						lArrayID[40] = OTHER_CAPITAL_SETTLEMENT.ID;
						lArrayID[41] = SECURITIES_TRANSFER.ID;
						lArrayID[42] = CARRY_COST.ID;
						lArrayID[43] = REMARK_APPROVAL.ID;
						lArrayID[44] = REMARK_TRANSACTION.ID;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//������״̬
	public static class ApplyFormStatus
	{
		public static final long CANCELED = 0; //��ȡ��
		public static final long SUBMITED = 1; //�ѱ���
		public static final long CHECKED = 2; //������
		public static final long USED = 3; //��ʹ��
		public static final long COMPLETED = 4; //�ѽ���
		public static final long REJECTED = 5; //�Ѿܾ�
		public static final long APPROVALING = 6;//�����
		/**
		 * ��������ͳһ�������
		 * @Deprecated
		 */
		public static final long APPROVALED = 7;  		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SUBMITED :
					strReturn = "�ѱ���";
					break;
				case (int) CHECKED :
					strReturn = "������";
					break;
				case (int) USED :
					strReturn = "��ʹ��";
					break;
				case (int) COMPLETED :
					strReturn = "�ѽ���";
					break;
				case (int) CANCELED :
					strReturn = "��ɾ��";
					break;
				case (int) REJECTED :
					strReturn = "�Ѿܾ�";
					break;
				case (int) APPROVALING :	
					strReturn = "������";
				    break;
				case (int) APPROVALED :	
					strReturn = "������";
				    break;				
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { SUBMITED, CHECKED, USED, COMPLETED, CANCELED, REJECTED,APPROVALING };
			return lTemp;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ��,2,��ʾ�ѷ��Ϻ���ʹ�����
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[7];
						lArrayID[0] = SUBMITED;
						lArrayID[1] = CHECKED;
						lArrayID[2] = USED;
						lArrayID[3] = COMPLETED;
						lArrayID[4] = CANCELED;
						lArrayID[5] = REJECTED;
						lArrayID[6] = APPROVALING;
						break;
					case 2 :
						lArrayID = new long[2];
						lArrayID[0] = CHECKED;
						lArrayID[1] = USED;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
		/**
		 * ���¼�����������ר��
		 * @param out
		 * @param strControlName
		 * @param nType
		 * @param strSelectValue
		 * @param isNeedAll
		 * @param isNeedBlank
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, String strSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			String[] strArrayID = new String[3];
			String[] strArrayName = new String[3];
			strArrayID = new String[3];
			strArrayName = new String[3];
			strArrayID[0] = CHECKED + "," + USED;
			strArrayID[1] = "" + CHECKED;
			strArrayID[2] = "" + USED;
			strArrayName[0] = "";
			strArrayName[1] = getName(CHECKED);
			strArrayName[2] = getName(USED);
			showCommonList(out, strControlName, strArrayID, strArrayName, strSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
	}
	//���״̬
	public static class DeliveryOrderStatus
	{
		public static final long DELETED = 0; //��ɾ��
		public static final long TEMPSAVED = 1; //���ݴ�
		public static final long SAVED = 2; //�ѱ���
		public static final long CHECKED = 3; //�Ѹ���
		public static final long USED = 4; //��ʹ��
		public static final long POSTED = 5; //�ѹ���
		public static final long APPROVALING = 10; //������
		public static final long APPROVALED = 20;  //������
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) TEMPSAVED :
					strReturn = "���ݴ�";
					break;
				case (int) SAVED :
					strReturn = "�ѱ���";
					break;
				case (int) CHECKED :
					strReturn = "�Ѹ���";
					break;
				case (int) USED :
					strReturn = "��ʹ��";
					break;
				case (int) DELETED :
					strReturn = "��ɾ��";
					break;
				case (int) POSTED :
					strReturn = "�ѹ���";
					break;
				case (int) APPROVALING :
					strReturn = "������";
					break;
				case (int) APPROVALED :
					strReturn = "������";
					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { TEMPSAVED, SAVED, CHECKED, USED, DELETED, POSTED, APPROVALING, APPROVALED };
			return lTemp;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ�����
		  * 	��0����ʾȫ����1,û���廧ѡ�� 2,��ʾ�ݴ�ͱ�������,3,��ʾ�ѱ�����Ѹ�������,4����ʾ�ѷ��Ϻ���ʹ�����
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[8];
						lArrayID[0] = TEMPSAVED;
						lArrayID[1] = SAVED;
						lArrayID[2] = CHECKED;
						lArrayID[3] = USED;
						lArrayID[4] = DELETED;
						lArrayID[5] = POSTED;
						lArrayID[6] = APPROVALING;
						lArrayID[7] = APPROVALED;
						break;
					case 2 :
						lArrayID = new long[2];
						lArrayID[0] = TEMPSAVED;
						lArrayID[1] = SAVED;
						break;
					case 3 :
						lArrayID = new long[2];
						lArrayID[0] = SAVED;
						lArrayID[1] = CHECKED;
						break;
					case 4 :
						lArrayID = new long[2];
						lArrayID[0] = CHECKED;
						lArrayID[1] = USED;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}

	/**
	 * �˻�����
	 * 
	 * @author yqwu
	 * 
	 *  
	 */
	public static class SubjectAttribute
	{
		public static final int ASSET = 1; //�ʲ����Ŀ
		public static final int DEBT = 2; //��ծ���Ŀ
		public static final int RIGHT = 3; //Ȩ�����Ŀ
		public static final int INCOME = 4; //�������Ŀ
		public static final int PAYOUT = 5; //֧�����Ŀ
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case ASSET :
					strReturn = "�ʲ����Ŀ";
					break;
				case DEBT :
					strReturn = "��ծ���Ŀ";
					break;
				case RIGHT :
					strReturn = "Ȩ�����Ŀ";
					break;
				case INCOME :
					strReturn = "�������Ŀ";
					break;
				case PAYOUT :
					strReturn = "֧�����Ŀ";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { ASSET, DEBT, RIGHT, INCOME, PAYOUT };
			return lTemp;
		}
		public static final long getDirection(long lTypeID)
		{
			long lDiretionID = DebitOrCredit.CREDIT;
			if (lTypeID == ASSET || lTypeID == PAYOUT)
			{
				lDiretionID = DebitOrCredit.DEBIT;
			}
			return lDiretionID;
		}
	}
	/**
	 * �������
	 * 
	 * @author wlming
	 * 
	 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
	 * Comments
	 */
	public static class DebitOrCredit
	{
		public static final long DEBIT = 1; //��
		public static final long CREDIT = 2; //��
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) DEBIT :
					strReturn = "��";
					break;
				case (int) CREDIT :
					strReturn = "��";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { DEBIT, CREDIT };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//֪ͨ��״̬
	public static class NoticeFormStatus
	{
		public static final long CANCELED = 0; //��ɾ��
		public static final long SUBMITED = 1; //�ѱ���
		public static final long CHECKED = 2; //������,�����
		public static final long USED = 3; //��ʹ��
		public static final long COMPLETED = 4; //�����
		public static final long POSTED = 5; //�ѹ���
		public static final long REJECTED = 6; //�Ѿܾ�
		public static final long APPROVALING = 7;//������
		public static final long APPROVED = 8; //������
		
		//public static final long GRANTED = 9;//�ѷ���
		//public static final long RECEIVED = 10;//���ջ�
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SUBMITED :
					strReturn = "�ѱ���";
					break;
				case (int) CHECKED :
					strReturn = "������";
					break;
				case (int) USED :
					strReturn = "��ʹ��";
					break;
				case (int) COMPLETED :
					strReturn = "�����";
					break;
				case (int) POSTED :
					strReturn = "�ѹ���";
					break;
				case (int) CANCELED :
					strReturn = "��ɾ��";
					break;
				case (int) REJECTED :
					strReturn = "�Ѿܾ�";
					break;
				case (int) APPROVALING :
					strReturn = "������";
					break;
				case (int)	APPROVED	:
					strReturn = "������";
					break;
//				case (int) GRANTED:
//					strReturn = "�ѷ���";
//					break;
//				case (int) RECEIVED:
//					strReturn = "���ջ�";
//					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { SUBMITED, CHECKED, USED, COMPLETED, POSTED, CANCELED, REJECTED, APPROVALING };
			return lTemp;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ��,2,���ݵ��ʽ�����ѡ�
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[8];
						lArrayID[0] = SUBMITED;
						lArrayID[1] = CHECKED;
						lArrayID[2] = USED;
						lArrayID[3] = COMPLETED;
						lArrayID[4] = POSTED;
						lArrayID[5] = CANCELED;
						lArrayID[6] = REJECTED;
						lArrayID[7] = APPROVALING;
						break;
					case 2 :
						lArrayID = new long[4];
						lArrayID[0] = CHECKED;
						lArrayID[1] = USED;
						lArrayID[2] = COMPLETED;
						lArrayID[3] = POSTED;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * �ǼǱ�����
	 * */
	public static class RegisterType
	{
		public static final long REPURCHASE = 1; //�ع��ǼǱ�
		public static final long PURCHASE = 2; //�깺�ǼǱ�
		public static final long INVESTMENT = 3; //����Ͷ�ʵǼǱ�		
	}
	/**
	 * ����
	 *
	 */
	public static class AmountDirection
	{
		public static final int BLUEFONT = 1; //����  
		public static final int REDFONT = 2; //����
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) BLUEFONT :
					strReturn = "����";
					break;
				case (int) REDFONT :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { BLUEFONT, REDFONT };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * �ʽ�����
	 *
	 */
	public static class CapitalType
	{
		public static final int IRRESPECTIVE = 0; //�޹�
		public static final int INTERNAL = 1; //�ڲ�ת��
		public static final int BANK = 2; //����
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case IRRESPECTIVE :
					strReturn = "�޹�";
					break;
				case INTERNAL :
					strReturn = "�ڲ�ת��";
					break;
				case BANK :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { IRRESPECTIVE, INTERNAL, BANK };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * �������
	 * */
	public static class AmountType
	{
		public static final int AmountType_1 = 1; //ʵ���ո�
		public static final int AmountType_2 = 2; //����/�ɱ�/��ֵ(ȫ��)
		public static final int AmountType_3 = 3; //��Ϣ/����/֧��
		public static final int AmountType_4 = 4; //֤ȯ����/�������/��ʧ
		public static final int AmountType_5 = 5; //Ӧ����Ϣ/�������
		public static final int AmountType_6 = 6; //δʵ������
		public static final int AmountType_7 = 7; //������/Ӷ��
		public static final int AmountType_8 = 8; //�ۼ�
		public static final int AmountType_9 = 9; //���
		public static final int AmountType_10 = 10; // ӡ��˰
		public static final int AmountType_11 = 11; //Ӫҵ˰�𼰸���
		public static final int AmountType_12 = 12; //��Ϣ
		public static final int AmountType_13 = 13; //ȯ��ʵ�ո�
		public static final int AmountType_14 = 14; //֤����ʵ�ո�
		public static final int AmountType_15 = 15; //������ջ���
		public static final int AmountType_16 = 16; //����/�ɱ�/��ֵ(����)
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case AmountType_1 :
					strReturn = "ʵ���ո�";
					break;
				case AmountType_2 :
					strReturn = "����/�ɱ�/��ֵ(ȫ��)";
					break;
				case AmountType_3 :
					strReturn = "��Ϣ/����/֧��";
					break;
				case AmountType_4 :
					strReturn = "֤ȯ����/�������/��ʧ";
					break;
				case AmountType_5 :
					strReturn = "Ӧ����Ϣ/�������";
					break;
				case AmountType_6 :
					strReturn = "δʵ������";
					break;
				case AmountType_7 :
					strReturn = "������/Ӷ��";
					break;
				case AmountType_8 :
					strReturn = "�ۼ�";
					break;
				case AmountType_9 :
					strReturn = "���";
					break;
				case AmountType_10 :
					strReturn = "ӡ��˰";
					break;
				case AmountType_11 :
					strReturn = "Ӫҵ˰�𼰸���";
					break;
				case AmountType_12 :
					strReturn = "��Ϣ";
					break;
				case AmountType_13 :
					strReturn = "ȯ��ʵ�ո�";
					break;
				case AmountType_14 :
					strReturn = "֤����ʵ�ո�";
					break;
				case AmountType_15 :
					strReturn = "������ջ���";
					break;
				case AmountType_16 :
					strReturn = "����/�ɱ�/��ֵ(����)";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					AmountType_1,
					AmountType_2,
					AmountType_3,
					AmountType_4,
					AmountType_5,
					AmountType_6,
					AmountType_7,
					AmountType_8,
					AmountType_9,
					AmountType_10,
					AmountType_11,
					AmountType_12,
					AmountType_13,
					AmountType_14,
					AmountType_15,
					AmountType_16 };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * ��Ŀ����
	 *   
	 */
	public static class EntrySubjectType
	{
		public static final int SUBJECT_TYPE_1 = 1; //�����ʽ��ʻ�
		public static final int SUBJECT_TYPE_2 = 2; //�շ��ʽ��ʻ�
		public static final int SUBJECT_TYPE_3 = 3; //��������/�ɱ�/��ֵ��Ŀ
		public static final int SUBJECT_TYPE_4 = 4; //�շ�����/�ɱ�/��ֵ��Ŀ
		public static final int SUBJECT_TYPE_5 = 5; //������Ϣ/����/֧����Ŀ
		public static final int SUBJECT_TYPE_6 = 6; //�շ���Ϣ/����/֧����Ŀ
		public static final int SUBJECT_TYPE_7 = 7; //����֤ȯ����/�������/��ʧ��Ŀ
		public static final int SUBJECT_TYPE_8 = 8; //�շ�֤ȯ����/�������/��ʧ��Ŀ
		public static final int SUBJECT_TYPE_9 = 9; //����Ӧ����Ϣ/���������Ŀ
		public static final int SUBJECT_TYPE_10 = 10; //�շ�Ӧ����Ϣ/���������Ŀ
		public static final int SUBJECT_TYPE_11 = 11; //����δʵ�����ÿ�Ŀ
		public static final int SUBJECT_TYPE_12 = 12; //�շ�δʵ�����ÿ�Ŀ
		public static final int SUBJECT_TYPE_13 = 13; //����������/Ӷ���Ŀ
		public static final int SUBJECT_TYPE_14 = 14; //�շ�������/Ӷ���Ŀ
		public static final int SUBJECT_TYPE_15 = 15; //�����ۼۿ�Ŀ
		public static final int SUBJECT_TYPE_16 = 16; //�շ��ۼۿ�Ŀ
		public static final int SUBJECT_TYPE_17 = 17; //������ۿ�Ŀ
		public static final int SUBJECT_TYPE_18 = 18; //�շ���ۿ�Ŀ		
		public static final int SUBJECT_TYPE_19 = 19; //��������/��ֵ׼����Ŀ
		public static final int SUBJECT_TYPE_20 = 20; //�շ�����/��ֵ׼����Ŀ
		public static final int SUBJECT_TYPE_21 = 21; //����ӡ��˰��Ŀ
		public static final int SUBJECT_TYPE_22 = 22; //�շ�ӡ��˰��Ŀ
		public static final int SUBJECT_TYPE_23 = 23; //����Ӫҵ˰��Ŀ		
		public static final int SUBJECT_TYPE_24 = 24; //�շ�Ӫҵ˰��Ŀ
		public static final int SUBJECT_TYPE_25 = 25; //������Ϣ��Ŀ
		public static final int SUBJECT_TYPE_26 = 26; //�շ���Ϣ��Ŀ
		public static final int SUBJECT_TYPE_27 = 27; //�����ʱ�������Ŀ
		public static final int SUBJECT_TYPE_28 = 28; //�շ��ʱ�������Ŀ
		public static final int SUBJECT_TYPE_29 = 29; //�����깺/���ʿ�Ŀ
		public static final int SUBJECT_TYPE_30 = 30; //�շ��깺/���ʿ�Ŀ
		public static final int SUBJECT_TYPE_31 = 31; //���������ʻ�
		public static final int SUBJECT_TYPE_32 = 32; //�շ������ʻ�
		public static final int SUBJECT_TYPE_33 = 33; //��������Ӧ�տ��Ŀ
		public static final int SUBJECT_TYPE_34 = 34; //�շ�����Ӧ�տ��Ŀ
		public static final int SUBJECT_TYPE_35 = 35; //��������Ӧ�����Ŀ
		public static final int SUBJECT_TYPE_36 = 36; //�շ�����Ӧ�����Ŀ
		public static final int SUBJECT_TYPE_99 = 99; //����
		public static long getSubjectTypeDirection(int subjectType)
		{
			if (subjectType == SUBJECT_TYPE_1
				|| subjectType == SUBJECT_TYPE_3
				|| subjectType == SUBJECT_TYPE_5
				|| subjectType == SUBJECT_TYPE_7
				|| subjectType == SUBJECT_TYPE_9
				|| subjectType == SUBJECT_TYPE_11
				|| subjectType == SUBJECT_TYPE_13
				|| subjectType == SUBJECT_TYPE_15
				|| subjectType == SUBJECT_TYPE_17
				|| subjectType == SUBJECT_TYPE_19
				|| subjectType == SUBJECT_TYPE_21
				|| subjectType == SUBJECT_TYPE_23
				|| subjectType == SUBJECT_TYPE_25
				|| subjectType == SUBJECT_TYPE_27
				|| subjectType == SUBJECT_TYPE_1
				|| subjectType == SUBJECT_TYPE_29)
			{
				return Direction.PAY;
			}
			else if (subjectType == SUBJECT_TYPE_99)
			{
				return -1;
			}
			else
			{
				return Direction.RECEIVE;
			}
		}
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					SUBJECT_TYPE_1,
					SUBJECT_TYPE_2,
					SUBJECT_TYPE_3,
					SUBJECT_TYPE_4,
					SUBJECT_TYPE_5,
					SUBJECT_TYPE_6,
					SUBJECT_TYPE_7,
					SUBJECT_TYPE_8,
					SUBJECT_TYPE_9,
					SUBJECT_TYPE_10,
					SUBJECT_TYPE_11,
					SUBJECT_TYPE_12,
					SUBJECT_TYPE_13,
					SUBJECT_TYPE_14,
					SUBJECT_TYPE_15,
					SUBJECT_TYPE_16,
					SUBJECT_TYPE_17,
					SUBJECT_TYPE_18,
					SUBJECT_TYPE_19,
					SUBJECT_TYPE_20,
					SUBJECT_TYPE_21,
					SUBJECT_TYPE_22,
					SUBJECT_TYPE_23,
					SUBJECT_TYPE_24,
					SUBJECT_TYPE_25,
					SUBJECT_TYPE_26,
					SUBJECT_TYPE_27,
					SUBJECT_TYPE_28,
					SUBJECT_TYPE_29,
					SUBJECT_TYPE_30,
					SUBJECT_TYPE_31,
					SUBJECT_TYPE_32,
					SUBJECT_TYPE_33,
					SUBJECT_TYPE_34,
					SUBJECT_TYPE_35,
					SUBJECT_TYPE_36,
					SUBJECT_TYPE_99 };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *        �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *        �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
		//TBD....
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case SUBJECT_TYPE_1 :
					strReturn = "�����ʽ��ʻ�";
					break;
				case SUBJECT_TYPE_2 :
					strReturn = "�շ��ʽ��ʻ�";
					break;
				case SUBJECT_TYPE_3 :
					strReturn = "��������/�ɱ�/��ֵ��Ŀ";
					break;
				case SUBJECT_TYPE_4 :
					strReturn = "�շ�����/�ɱ�/��ֵ��Ŀ";
					break;
				case SUBJECT_TYPE_5 :
					strReturn = "������Ϣ/����/֧����Ŀ";
					break;
				case SUBJECT_TYPE_6 :
					strReturn = "�շ���Ϣ/����/֧����Ŀ";
					break;
				case SUBJECT_TYPE_7 :
					strReturn = "����֤ȯ����/�������/��ʧ��Ŀ";
					break;
				case SUBJECT_TYPE_8 :
					strReturn = "�շ�֤ȯ����/�������/��ʧ��Ŀ";
					break;
				case SUBJECT_TYPE_9 :
					strReturn = "����Ӧ����Ϣ/���������Ŀ";
					break;
				case SUBJECT_TYPE_10 :
					strReturn = "�շ�Ӧ����Ϣ/���������Ŀ";
					break;
				case SUBJECT_TYPE_11 :
					strReturn = "����δʵ�����ÿ�Ŀ";
					break;
				case SUBJECT_TYPE_12 :
					strReturn = "�շ�δʵ�����ÿ�Ŀ";
					break;
				case SUBJECT_TYPE_13 :
					strReturn = "����������/Ӷ���Ŀ";
					break;
				case SUBJECT_TYPE_14 :
					strReturn = "�շ�������/Ӷ���Ŀ";
					break;
				case SUBJECT_TYPE_15 :
					strReturn = "�����ۼۿ�Ŀ";
					break;
				case SUBJECT_TYPE_16 :
					strReturn = "�շ��ۼۿ�Ŀ";
					break;
				case SUBJECT_TYPE_17 :
					strReturn = "������ۿ�Ŀ";
					break;
				case SUBJECT_TYPE_18 :
					strReturn = "�շ���ۿ�Ŀ";
					break;
				case SUBJECT_TYPE_19 :
					strReturn = "��������/��ֵ׼����Ŀ";
					break;
				case SUBJECT_TYPE_20 :
					strReturn = "�շ�����/��ֵ׼����Ŀ";
					break;
				case SUBJECT_TYPE_21 :
					strReturn = "����ӡ��˰��Ŀ";
					break;
				case SUBJECT_TYPE_22 :
					strReturn = "�շ�ӡ��˰��Ŀ";
					break;
				case SUBJECT_TYPE_23 :
					strReturn = "����Ӫҵ˰��Ŀ";
					break;
				case SUBJECT_TYPE_24 :
					strReturn = "�շ�Ӫҵ˰��Ŀ";
					break;
				case SUBJECT_TYPE_25 :
					strReturn = "������Ϣ��Ŀ";
					break;
				case SUBJECT_TYPE_26 :
					strReturn = "�շ���Ϣ��Ŀ";
					break;
				case SUBJECT_TYPE_27 :
					strReturn = "�����ʱ�������Ŀ";
					break;
				case SUBJECT_TYPE_28 :
					strReturn = "�շ��ʱ�������Ŀ";
					break;
				case SUBJECT_TYPE_29 :
					strReturn = "�����깺/���ʿ�Ŀ";
					break;
				case SUBJECT_TYPE_30 :
					strReturn = "�շ��깺/���ʿ�Ŀ";
					break;
				case SUBJECT_TYPE_31 :
					strReturn = "���������ʻ�";
					break;
				case SUBJECT_TYPE_32 :
					strReturn = "�շ������ʻ�";
					break;
				case SUBJECT_TYPE_33 :
					strReturn = "��������Ӧ�տ��Ŀ";
					break;
				case SUBJECT_TYPE_34 :
					strReturn = "�շ�����Ӧ�տ��Ŀ";
					break;
				case SUBJECT_TYPE_35 :
					strReturn = "��������Ӧ�����Ŀ";
					break;
				case SUBJECT_TYPE_36 :
					strReturn = "�շ�����Ӧ�����Ŀ";
					break;
				case SUBJECT_TYPE_99 :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
	}
	/**
	 * ƾ֤����״̬
	 *   
	 */
	public static class PostEntryStatus
	{
		public static final long NotPost = 0; //δ����		
		public static final long Posted = 1; //�ѹ���		
	}
	/**
	 * ��������
	 * */
	public static class EntryAccountType
	{
		public static final int AccountType_0 = 0; //�޹�
		public static final int AccountType_1 = 1; //�����ʽ�
		public static final int AccountType_2 = 2; //����ʽ�
		public static final int AccountType_3 = 3; //���ع�
		public static final int AccountType_4 = 4; //��ع�
		public static final int AccountType_5 = 5; //���м�֤ȯ��ȯ
		public static final int AccountType_6 = 6; //������֤ȯ��ȯ
		public static final int AccountType_7 = 7; //��ع�
		public static final int AccountType_8 = 8; //���ع�
		public static final int AccountType_9 = 9; //����ʽ����
		public static final int AccountType_10 = 10; //�ʽ𻮲�
		public static final int AccountType_11 = 11; //�ʲ��ع�
		public static final int AccountType_12 = 12; //�ṹ��Ͷ��
		public static final int AccountType_13 = 13; //ծȯ����
		public static final int AccountType_14 = 14; //ί�����
		public static final int AccountType_15 = 15; //�������
		public static final int AccountType_16 = 16; //��ȨͶ�ʣ���Ӫ/ί�У�
		public static final int AccountType_17 = 17; //������
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case (int) AccountType_0 :
					strReturn = "�޹�";
					break;
				case (int) AccountType_1 :
					strReturn = "�����ʽ�";
					break;
				case (int) AccountType_2 :
					strReturn = "����ʽ�";
					break;
				case (int) AccountType_3 :
					strReturn = "���ع�";
					break;
				case (int) AccountType_4 :
					strReturn = "��ع�";
					break;
				case (int) AccountType_5 :
					strReturn = "���м�֤ȯ��ȯ";
					break;
				case (int) AccountType_6 :
					strReturn = "������֤ȯ��ȯ";
					break;
				case (int) AccountType_7 :
					strReturn = "��ع�";
					break;
				case (int) AccountType_8 :
					strReturn = "���ع�";
					break;
				case (int) AccountType_9 :
					strReturn = "����ʽ����";
					break;
				case (int) AccountType_10 :
					strReturn = "�ʽ𻮲�";
					break;					
				case (int) AccountType_11 :
					strReturn = "�ʲ��ع�";
					break;
				case (int) AccountType_12 :
					strReturn = "�ṹ��Ͷ��";
					break;
				case (int) AccountType_13 :
					strReturn = "ծȯ����";
					break;
				case (int) AccountType_14 :
					strReturn = "ί�����";
					break;
				case (int) AccountType_15 :
					strReturn = "�������";
					break;
				case (int) AccountType_16 :
					strReturn = "��ȨͶ�ʣ���Ӫ/ί�У�";
					break;
				case (int) AccountType_17 :
					strReturn = "������";
					break;
			}
			return strReturn;
		}
		/**
		 * ҵ������Ϊ���м����������
		 */
		public static final long[] getAllCode_BANK()
		{
			long[] lTemp = { AccountType_0, AccountType_1, AccountType_2, AccountType_3, AccountType_4, AccountType_5, AccountType_10};
			return lTemp;
		}
		/**
		 * ҵ������Ϊ����������������
		 */
		public static final long[] getAllCode_EXCHANGECENTER()
		{
			long[] lTemp = { AccountType_0, AccountType_6, AccountType_7, AccountType_8 };
			return lTemp;
		}
		/**
		* ҵ������Ϊ����ʽ�������������
		*/
		public static final long[] getAllCode_OPENFUND()
		{
			long[] lTemp = { AccountType_0, AccountType_9 };
			return lTemp;
		}
		/**
		* ҵ������Ϊ��ͬҵ�����������
		*/
		public static final long[] getAllCode_CONTRACT()
		{
			long[] lTemp = { AccountType_0, AccountType_11, AccountType_12, AccountType_13, AccountType_14, AccountType_15, AccountType_16, AccountType_17 };
			return lTemp;
		}
        /**
        * ҵ������Ϊ����ҵ�����������
        */
        public static final long[] getAllCode_OTHERS()
        {
            long[] lTemp = { AccountType_0 };
            return lTemp;
        }
		public static final long[] getAllCode()
		{
			long[] lTemp =
				{
					AccountType_0,
					AccountType_1,
					AccountType_2,
					AccountType_3,
					AccountType_4,
					AccountType_5,
					AccountType_6,
					AccountType_7,
					AccountType_8,
					AccountType_9,
					AccountType_10,
					AccountType_11,
					AccountType_12,
					AccountType_13,
					AccountType_14,
					AccountType_15,
					AccountType_16,
					AccountType_17 };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 * 
		 * @param out
		 * @param strControlName,
		 *        �ؼ�����
		 * @param nType���ؼ�����
		 * ��0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *        �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = getAllCode_BANK();
						break;
					case 2 :
						lArrayID = getAllCode_EXCHANGECENTER();
						break;
					case 3 :
						lArrayID = getAllCode_OPENFUND();
						break;
					case 4 :
						lArrayID = getAllCode_CONTRACT();
						break;
                    case 5 :
                        lArrayID = getAllCode_OTHERS();
                        break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//�ļ�����
	public static class FileType
	{
		//�ļ�����
		public static final long TXT = 1; //�ı��ļ�
		public static final long DOC = 2; //WORD�ĵ�
		public static final long XLS = 3; //EXCEL�ĵ�
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) TXT :
					strReturn = "�ı��ļ�";
					break;
				case (int) DOC :
					strReturn = "WORD�ĵ�";
					break;
				case (int) XLS :
					strReturn = "EXCEL�ĵ�";
					break;
			}
			return strReturn;
		}
	}
	
	//Modify by leiyang date 2007/09/14
	//�ϴ���������������������
	//�����������õĲ�������
	public static class AttachParentType
	{
		public static final long APPLY = 1; //����
		public static final long CONTRACT = 2; //��ͬ
		public static final long NOTICE = 3; //ҵ��֪ͨ��
		public static final long CLIENT = 4; //�ͻ�
		public static final long DELIVERY = 5; //���
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) APPLY :
					strReturn = "����";
					break;
				case (int) CONTRACT :
					strReturn = "��ͬ";
					break;
				case (int) NOTICE :
					strReturn = "ҵ��֪ͨ��";
					break;
				case (int) CLIENT :
					strReturn = "�ͻ�";
					break;
				case (int) DELIVERY :
					strReturn = "���";
					break;	
			}
			return strReturn;
		}
		
		public static final long[] getAllCode()
		{
			long[] lTemp = { APPLY, CONTRACT, NOTICE};
			return lTemp;
		}
	}
	
	//�ʽ���Դ
	public static class CapitalSource
	{
		public static final long SELFSUPPORT = 1; //��Ӫ
		public static final long AGENT = 2; //����
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SELFSUPPORT :
					strReturn = "��Ӫ";
					break;
				case (int) AGENT :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		//
		public static final long[] getAllCode()
		{
			long[] lTemp = { SELFSUPPORT, AGENT };
			return lTemp;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[2];
						lArrayID[0] = SELFSUPPORT;
						lArrayID[1] = AGENT;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	//���ᴦ��ʽ
	public static class FrozenProcess
	{
		public final static long NOT_DEAL = 0; //������
		public final static long FREEZE = 1; //��涳��
		public final static long CANCEL_FREEZE = 2; //���ⶳ
	}
	public static class RegisterProcess
	{
		public final static long CAPITAL = 11; //���/�ع�
		public final static long CAPITAL_MATURE = 12; //���/�ع�����
		public final static long APPLICATION_APPLY = 21; //�깺����
		public final static long APPLICATION_CONFIRM = 22; //�깺ȷ��
		public final static long APPLICATION_MATURE = 23; //�깺����
		public final static long APPLICATION_SELLOUT = 24; //�깺����
		public final static long LONGTERM_BUYIN = 31; //����Ͷ������
		public final static long LONGTERM_BUYOUT = 32; //����Ͷ�����
		public final static long REPURCHASE_REGISTER = 1; //�ع��ǼǱ�
		public final static long PURCHASE_REGISTER = 2; //�����ǼǱ���
		public final static long LONGTERMINVESTMENT_REGISTER = 3; //����Ͷ�ʵǼǱ�
		public final static long getBelongRegister(long registerProcess)
		{
			if (registerProcess == CAPITAL || registerProcess == CAPITAL_MATURE)
			{
				return REPURCHASE_REGISTER;
			}
			else if (registerProcess == APPLICATION_APPLY || registerProcess == APPLICATION_CONFIRM || registerProcess == APPLICATION_MATURE || registerProcess == APPLICATION_SELLOUT)
			{
				return PURCHASE_REGISTER;
			}
			else if (registerProcess == LONGTERM_BUYIN || registerProcess == LONGTERM_BUYOUT)
			{
				return LONGTERMINVESTMENT_REGISTER;
			}
			return -1;
		}
	}
	/************************************************************/
	/**
	 * Ͷ�귽ʽ
	 * @author lgwang
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class BidType
	{
		public static final long US = 1; //����ʽ
		public static final long HOLLAND = 2; //����ʽ
		public static final long OTHERS = 3; //����
		public static final long[] getAllCode()
		{
			long[] lTemp = { US, HOLLAND, OTHERS };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) US :
					strReturn = "����ʽ";
					break;
				case (int) HOLLAND :
					strReturn = "����ʽ";
					break;
				case (int) OTHERS :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[3];
						lArrayID[0] = US;
						lArrayID[1] = HOLLAND;
						lArrayID[2] = OTHERS;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}
	/**
	 * Ͷ�귽ʽ
	 * @author lgwang
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class TaxType
	{
		public static final long BUSINESS_TAX = 1; //Ӫҵ˰
		public static final long CONSTRUCTION_TAX = 2; //����ά������˰
		public static final long EDUCATION_TAX = 3; //�����Ѹ���˰
		public static final long[] getAllCode()
		{
			long[] lTemp = { BUSINESS_TAX, CONSTRUCTION_TAX, EDUCATION_TAX };
			return lTemp;
		}
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) BUSINESS_TAX :
					strReturn = "Ӫҵ˰";
					break;
				case (int) CONSTRUCTION_TAX :
					strReturn = "����ά������˰";
					break;
				case (int) EDUCATION_TAX :
					strReturn = "�����Ѹ���˰";
					break;
			}
			return strReturn;
		}
		/**
		  * ��ʾ�����б�
		  * 
		  * @param out
		  * @param strControlName,
		  *            �ؼ�����
		  * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
		  * @param lSelectValue
		  * @param isNeedAll���Ƿ���Ҫ��ȫ���
		  * @param isNeedBlank
		  *            �Ƿ���Ҫ�հ���
		  * @param strProperty
		  */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = new long[3];
						lArrayID[0] = BUSINESS_TAX;
						lArrayID[1] = CONSTRUCTION_TAX;
						lArrayID[2] = EDUCATION_TAX;
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}

	//��ͬ״̬
	public static class ContractStatus
	{
		//��ͬ״̬
		public static final long SAVE = 1; //׫д
		public static final long SUBMIT = 2; //���ύ
		public static final long CHECK = 3; //�����
		public static final long NOTACTIVE = 4; //δִ��
		public static final long ACTIVE = 5; //ִ����
		public static final long EXTEND = 6; //��չ��
		public static final long FINISH = 10; //�ѽ���
		public static final long CANCEL = 11; //��ȡ��
		public static final long REFUSE = 12; //�Ѿܾ�
		public static final long APPROVALING = 13;//�����

		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SAVE :
					strReturn = "�ѱ���";
					break;
				case (int) SUBMIT :
					strReturn = "���ύ";
					break;
				case (int) CHECK :
					strReturn = "�����";
					break;
				case (int) NOTACTIVE :
					strReturn = "δִ��";
					break;
				case (int) ACTIVE :
					strReturn = "ִ����";
					break;
				case (int) EXTEND :
					strReturn = "��չ��";
					break;
				case (int) FINISH :
					strReturn = "�ѽ���";
					break;
				case (int) CANCEL :
					strReturn = "��ȡ��";
					break;
				case (int) REFUSE :
					strReturn = "�Ѿܾ�";
					break;
				case (int) APPROVALING :
					strReturn = "�����";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[9];
			lTemp[0] = SAVE;
			lTemp[1] = SUBMIT;
			lTemp[2] = CHECK;
			lTemp[3] = NOTACTIVE;
			lTemp[4] = ACTIVE;
			lTemp[5] = EXTEND;
			lTemp[6] = FINISH;
			lTemp[7] = CANCEL;
			lTemp[8] = REFUSE;
			lTemp[8] = APPROVALING;

			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
		public static final void showList1(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{

				lArrayID = new long[] { SAVE, SUBMIT, CHECK, NOTACTIVE, ACTIVE, FINISH };
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}

	/*
	 * ί���ʲ���ʽ
	 * @author gdzhao
	 *
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class ConsignAssetType
	{
		public static final long CASH = 1; //�ֽ�
		public static final long STOCK = 2; //��Ʊ
		public static final long DEBT = 3; //��ծ
		public static final long ALL = 4;  //ȫ��
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) CASH :
					strReturn = "�ֽ�";
					break;
				case (int) STOCK :
					strReturn = "��Ʊ";
					break;
				case (int) DEBT :
					strReturn = "��ծ";
					break;
				case (int) ALL :
					strReturn = "ȫ��";					
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[0] = CASH;
			lTemp[1] = STOCK;
			lTemp[2] = DEBT;
			lTemp[3] = ALL;
			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	/**
	 * ��Ϣ��ʽ
	 * @author gdzhao
	 *
	 * To change the template for this generated type comment go to
	 * Window>Preferences>Java>Code Generation>Code and Comments
	 */
	public static class SettlementType
	{
		public static final long ONEMONTH = 1; //1 month
		public static final long THREEMONTH = 2; //3 month
		public static final long SIXMONTH = 3; //5 month
		public static final long ONEYEAR = 4; //1 year
		public static final long ONTIME = 5; //on time
		public static final long PRETIME = 6; //PRE TIME

		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) ONEMONTH :
					strReturn = "1����";
					break;
				case (int) THREEMONTH :
					strReturn = "3����";
					break;
				case (int) SIXMONTH :
					strReturn = "6����";
					break;
				case (int) ONEYEAR :
					strReturn = "1��";
					break;
				case (int) ONTIME :
					strReturn = "����֧��";
					break;
				case (int) PRETIME :
					strReturn = "��ǰ֧��";
					break;

			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[6];
			lTemp[0] = ONEMONTH;
			lTemp[1] = THREEMONTH;
			lTemp[2] = SIXMONTH;
			lTemp[3] = ONEYEAR;
			lTemp[4] = ONTIME;
			lTemp[5] = PRETIME;
			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	public static class BoldSaleType
	{
		public static final long ALLSALE = 1;
		public static final long BALANCESALE = 2;
		public static final long CONSIGNSALE = 3;
		public static final long OTHER = 4;
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) ALLSALE :
					strReturn = "ȫ�����";
					break;
				case (int) BALANCESALE :
					strReturn = "������";
					break;
				case (int) CONSIGNSALE :
					strReturn = "����";
					break;
				case (int) OTHER :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[4];
			lTemp[0] = ALLSALE;
			lTemp[1] = BALANCESALE;
			lTemp[2] = CONSIGNSALE;
			lTemp[3] = OTHER;
			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	/*�ʻ����ͣ��������ͣ���ӣ�
	  1=�����ʻ�
	  2=���ܲ����ʻ�
	  3=�����ʻ� */
	public static class NoticeAccountType
	{
		public static final long COUNTERPARTACCOUNT = 1; //1,�����ʻ���
		public static final long COMPANYACCOUNT = 2; //2�����ܲ����ʻ�
		public static final long CURRENTACCOUNT = 3; //3�������ʻ�
	}

	/*�ո�������ӣ�
	 * 8=�տ�
	 * 9=����
	 * @author fanyang
	 */
	/*public static class ReceiveOrPay
	{
		public static final long RECEIVE = 8; //��
		public static final long PAY = 9; //��
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case (int) RECEIVE :
					strReturn = "��";
					break;
				case (int) PAY :
					strReturn = "��";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { RECEIVE, PAY };
			return lTemp;
		}
	
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
	}*/
	/*	��������
	 * 1=һ�㷨��
	 * 2=ս�Է���
	 * 3=һ��Ͷ����
	 * @author xuyu
	 */

	public static class OwnerType
	{
		public static final long NORMAL_OWNER = 1; //һ�㷨��
		public static final long STRATERGY_OWNER = 2; //ս�Է���

		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) NORMAL_OWNER :
					strReturn = "һ�㷨��";
					break;
				case (int) STRATERGY_OWNER :
					strReturn = "ս�Է���";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = null;
			lTemp = new long[2];
			lTemp[0] = NORMAL_OWNER;
			lTemp[1] = STRATERGY_OWNER;

			return lTemp;
		}
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}

	}

	//��ִͬ�мƻ����״̬
	public static class PlanModifyStatus
	{
		//�⻹����״̬
		public static final long SUBMIT = 1; //���ύ
		public static final long CHECK = 2; //�����
		public static final long REFUSE = -2; //�Ѿܾ�
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) SUBMIT :
					strReturn = "���ύ";
					break;
				case (int) CHECK :
					strReturn = "�����";
					break;
				case (int) REFUSE :
					strReturn = "�Ѿܾ�";
					break;
			}
			return strReturn;
		}
	}
	//����ſʽ
	public static class PayType
	{
		public static final long ONEMONTH = 1; //1����
		public static final long THREEMONTH = 2; //3����
		public static final long SIXMONTH = 3; //6����
		public static final long ONEYEAR = 4; //����ſ�
		public static final long ONETIME = 5; //һ�ηſ�
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) ONEMONTH :
					strReturn = "1����";
					break;
				case (int) THREEMONTH :
					strReturn = "3����";
					break;
				case (int) SIXMONTH :
					strReturn = "6����";
					break;
				case (int) ONEYEAR :
					strReturn = "һ��";
					break;
				case (int) ONETIME :
					strReturn = "һ����";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { ONEMONTH, THREEMONTH, SIXMONTH, ONEYEAR, ONETIME };
			return lTemp;
		}
		public static final long getCount()
		{
			return 5;
		}
		/**
		 * ��ʾ�����б�
		 *
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	//����ʽ
	public static class RepayType
	{
		public static final long ONEMONTH = 1; //1����
		public static final long THREEMONTH = 2; //3����
		public static final long SIXMONTH = 3; //6����
		public static final long ONEYEAR = 4; //����ſ�
		public static final long ONETIME = 5; //һ�ηſ�
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) ONEMONTH :
					strReturn = "1����";
					break;
				case (int) THREEMONTH :
					strReturn = "3����";
					break;
				case (int) SIXMONTH :
					strReturn = "6����";
					break;
				case (int) ONEYEAR :
					strReturn = "һ��";
					break;
				case (int) ONETIME :
					strReturn = "һ����";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { ONEMONTH, THREEMONTH, SIXMONTH, ONEYEAR, ONETIME };
			return lTemp;
		}
		public static final long getCount()
		{
			return 5;
		}
		/**
		 * ��ʾ�����б�
		 *
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	//�ƻ�����
	public static class PlanType
	{
		//������������
		public static final long PAY = 1; //�ſ�
		public static final long REPAY = 2; //����
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) PAY :
					strReturn = "�ſ�";
					break;
				case (int) REPAY :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { PAY, REPAY };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 *
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	//��������
	public static class SiteType
	{
		public static final long STATE_OWNED_BANK	 = 1; //������ҵ����
		public static final long COMMERCIAL_BANK		 = 2; //������ҵ����
		public static final long NONBANK_ORGANIZATION = 3; //�����н��ڻ��� 
		public static final long OTHER_ORGANIZATION	 = 4; //������λ
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) STATE_OWNED_BANK :
					strReturn = "������ҵ����";
					break;
				case (int) COMMERCIAL_BANK :
					strReturn = "������ҵ����";
					break;
				case (int) NONBANK_ORGANIZATION :
					strReturn = "�����н��ڻ���";
					break;
				case (int) OTHER_ORGANIZATION :
					strReturn = "������λ";
					break;
			}
			return strReturn;
		}
		public static final long[] getAllCode()
		{
			long[] lTemp = { STATE_OWNED_BANK, COMMERCIAL_BANK,NONBANK_ORGANIZATION,OTHER_ORGANIZATION };
			return lTemp;
		}
		/**
		 * ��ʾ�����б�
		 *
		 * @param out
		 * @param strControlName,
		 *            �ؼ�����
		 * @param nType���ؼ����ͣ�0����ʾȫ������
		 * @param lSelectValue
		 * @param isNeedAll���Ƿ���Ҫ��ȫ���
		 * @param isNeedBlank
		 *            �Ƿ���Ҫ�հ���
		 * @param strProperty
		 */
		public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
	}
	
	//��Ŀ�ţ�����
	public static class SubjectCode
	{
		public static final long SUBJECTTYPE_ZYZQ = 1;//��Ӫ֤ȯ
		public static final long SUBJECTTYPE_DQTZ = 2;//����Ͷ��
		public static final long SUBJECTTYPE_MRHGZC = 3;//����ع��ʲ�
		public static final long SUBJECTTYPE_MCHGZCK = 4;//�����ع��ʲ���
		public static final long SUBJECTTYPE_JGXTZ = 5; //�ṹ��Ͷ��
		
		
		/**
		 * һ����Ŀ
		 * code1:subjectTypeID
		**/
		public static final String getName( long code1 )
		{
			String strReturn = ""; //��ʼ������ֵ
			
			switch ( (int) code1 )
			{
				case (int) SUBJECTTYPE_ZYZQ:
					strReturn = "121";
					break;
				case (int) SUBJECTTYPE_DQTZ:
					strReturn = "118";
					break;
				case (int) SUBJECTTYPE_MRHGZC:
					strReturn = "1260";
					break;
				case (int) SUBJECTTYPE_MCHGZCK:
					strReturn = "2250";
					break;
				case (int) SUBJECTTYPE_JGXTZ:
					strReturn = "1020";
					break;
			}
			return strReturn;
		}
		
		/**
		 * ������Ŀ
		 * code1:subjectTypeID
		 * code2:businessTypeID
		**/
		public static final String getName( long code1,long code2 )
		{
			String strReturn = ""; //��ʼ������ֵ
			
			strReturn = getName( code1 );
			
			switch ( (int) code2 )
			{
				case (int) 16://��Ʊ
					strReturn += "001";
					break;
				case (int) 31://��ծ
					strReturn += "-100";
					break;
				case (int) 73://ί�����
					strReturn += "-200";
					break;
				case (int) 46://ծȯ
					strReturn += "002";
					break;
				case (int) 56://����
					strReturn += "003";
					break;
				case (int) 26://ծȯ�ع��������ع�֤ȯ��
					strReturn += "01";
					break;
				case (int) 71://�Ŵ��ʲ��ع��������ع��Ŵ��ʲ���
					strReturn += "02";
					break;
			}
			
			return strReturn;
		}
		
		/**
		 * ������Ŀ
		 * code1:subjectTypeID
		 * code2:businessTypeID
		 * code3:siteTypeID
		**/
		public static final String getName( long code1,long code2,long code3 )
		{
			String strReturn = ""; //��ʼ������ֵ
			
			strReturn = getName( code1,code2 );
			
			switch ( (int) code3 )
			{
				case (int) SiteType.COMMERCIAL_BANK://������ҵ����
					strReturn += "0200";
					break;
				case (int) SiteType.NONBANK_ORGANIZATION://�����н��ڻ���
					strReturn += "0300";
					break;
				case (int) SiteType.OTHER_ORGANIZATION://������λ
					strReturn += "0400";
					break;
				case (int) SiteType.STATE_OWNED_BANK://������ҵ����
					strReturn += "0100";
					break;
			}
			
			return strReturn;
		}
	}
	
	// �������� add by leiyang,kevin 2007/09/05
	public static class WorkType {
		
		public static final long WAITDEALWITHWORK = 1;//������ҵ��
		
		public static final long CURRENTWORK = 2; //��������

		public static final long HISTORYWORK = 3;//�Ѱ�����

		public static final long FINISHEDWORK = 4;//�������
		
		public static final long REFUSEWORK = 5;//�ܾ�ҵ��
		
		public static final long CANCELAPPROVAL = 6;//ȡ������		
		
		//Added by zwsun, 2007/09/06 �õ��ҵĹ��������ӵ�ַ
		public final static String getWorkUrl(String strContext, long workType){
			String workUrl = "";
			switch((int)workType){
				case (int)WAITDEALWITHWORK:
					workUrl = strContext + "/securities/mywork/waitDealWithWorkList-main.jsp";
					break;
				case (int)CURRENTWORK:
					workUrl = strContext + "/securities/mywork/currentWorkList-main.jsp";
					break;
				case (int)HISTORYWORK:
					workUrl = strContext + "/securities/mywork/historyWorkList-main.jsp";
					break;
				case (int)FINISHEDWORK:
					workUrl = strContext + "";
					break;
				case (int)REFUSEWORK:
					workUrl = strContext + "/securities/mywork/refuseWorkList-main.jsp";
					break;
				case (int)CANCELAPPROVAL:
					workUrl = strContext + "/securities/mywork/cancelApprovalList-main.jsp";
					break;					
			}
			return workUrl;
		}
		public final static String getWorkUrl(long workType){
			String strContext="/NASApp/iTreasury-securities";
			return getWorkUrl(strContext, workType);
		}
	}
	
	//�ʲ�ת��֪ͨ������
	public static class NoticeFormType
	{
        public static final long CAPITAL_PAYMENT = 7111; //֧��ת�ÿ���
        public static final long INTEREST_PAYBACK = 7112; //��Ϣ�ջ�֪ͨ��
        public static final long CAPITAL_REPURCHASE = 7113; //����֪ͨ��
        public static final long INTEREST_PAYMENT = 7114; //��Ϣ֧��֪ͨ��
        public static final long CAPITAL_PAYBACK = 7115; //�յ�ת�ÿ���
        
        
        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) CAPITAL_PAYMENT :
                    strReturn = "֧��ת�ÿ���";
                    break;
                case (int) INTEREST_PAYBACK :
                    strReturn = "��Ϣ�ջ�֪ͨ��";
                    break;
                case (int) CAPITAL_REPURCHASE :
                    strReturn = "����֪ͨ��";
                    break;
                case (int) INTEREST_PAYMENT :
                    strReturn = "��Ϣ֧��֪ͨ��";
                    break;
                case (int) CAPITAL_PAYBACK :
                    strReturn = "�յ�ת�ÿ���";
                    break;
            }
            return strReturn;
        }
        
        public static final long[] getAllCode()
        {
            long[] lTemp = { CAPITAL_PAYMENT, INTEREST_PAYBACK, CAPITAL_REPURCHASE, INTEREST_PAYMENT, CAPITAL_PAYBACK };
            return lTemp;
        }
        /**
          * ��ʾ�����б�
          * 
          * @param out
          * @param strControlName,
          *            �ؼ�����
          * @param nType���ؼ����ͣ�0����ʾȫ����1,û���廧ѡ�
          * @param lSelectValue
          * @param isNeedAll���Ƿ���Ҫ��ȫ���
          * @param isNeedBlank
          *            �Ƿ���Ҫ�հ���
          * @param strProperty
          */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0 :
                        lArrayID = getAllCode();
                        break;
                    case 1 :
                        lArrayID = new long[3];
                        lArrayID[0] = CAPITAL_PAYMENT;
                        lArrayID[1] = INTEREST_PAYBACK;
                        lArrayID[2] = CAPITAL_REPURCHASE;
                        break;
                    case 2 :
                        lArrayID = new long[1];
                        lArrayID[0] = CAPITAL_PAYMENT;
                        break; 
                    case 3 :
                    	 lArrayID = new long[3];
                         lArrayID[0] = CAPITAL_PAYBACK;
                         lArrayID[1] = INTEREST_PAYMENT;
                         lArrayID[2] = CAPITAL_REPURCHASE;
                        break;
                    case 4 :
                        lArrayID = new long[1];
                        lArrayID[0] = CAPITAL_PAYBACK;
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                Log.print(ex.toString());
            }
        }
    }
}