/*
 * Created on 2006-12-6
 * 
 */
package com.iss.itreasury.craftbrother.util;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.craftbrother.setting.dao.*;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Constant.ModuleType;
public class CRAconstant  extends com.iss.itreasury.util.Constant
{
	/*ҵ������
	 * ҵ�����ʱȽ����⣬��ҵ�����ʱ���ֻ���������ƣ�û�ж���ID�����ͨ�����Ʒ��س����ж����ID
	 * */
	public static class SameBusinessAttribute
	{
		public static final long SAME_BUSINESS = 1; //1,�ʲ�ת��ҵ��
		public static final long FUND_ATTORN = 2; //2���ʽ���ҵ��
		public static final long DISCOUNT = 6; //6��ת����ҵ��
		public static final long OTHER = 3; //8����������
		
		public static final long getIDByName(String name)
		{
			if (name.compareToIgnoreCase("�ʲ�ת��ҵ��") == 0)
				return SAME_BUSINESS;
			else if (name.compareToIgnoreCase("�ʽ���ҵ��") == 0)
				return FUND_ATTORN;
			else if (name.compareToIgnoreCase("ת����ҵ��") == 0)
				return DISCOUNT;
			else if (name.compareToIgnoreCase("��������") == 0)
				return OTHER;
			return -1;
		}
		public static final long[] getCodeForApproval(){
			return new long[]{SAME_BUSINESS,FUND_ATTORN,DISCOUNT,OTHER};
		}
		
		public static final long[] getCodeForCodeRule(){
			return new long[]{DISCOUNT,FUND_ATTORN,SAME_BUSINESS};
		}
		
        public static final long[] getCraAllCode(long lOfficeID,long lCurrencyID)
		{
        	LoanTypeRelationDao dao = new LoanTypeRelationDao();
			return dao.getAllSetLoanTypeID(lOfficeID,lCurrencyID);
		}
        
		public static final String getName(long ID){
			if (ID==SAME_BUSINESS)
				return "�Ŵ��ʲ�ת��";
			else if (ID==FUND_ATTORN)
				return "�ʽ���";
			else if (ID==DISCOUNT) 
				return "ת����";		
			else if (ID==OTHER) 
				return "��������";		
			return "-1";
		}
		public static final void showList(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long[] lArrayID){
			
			String[] strArrayName = null;
			try
			{
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
	
	/**
	 * ����״̬
	 * @author cqzhu
	 *
	 */
	public static class TransactionStatus
	{ 
	    public static final long DELETED = 0; //��ɾ��
        public static final long SAVE = 1; //�ѱ��棨δ������
        public static final long APPROVALING = 20; //������
        public static final long APPROVALED = 3;//������
        public static final long REFUSED = 4; //�Ѿܾ�
        public static final long USED = 5; //��ʹ��
        
        public static final String getName(long ID)
		{   
			String strReturn = ""; //��ʼ������ֵ
			switch ((int)ID)
			{
				case (int)DELETED:
					strReturn = "��ɾ��"; 
					break;
				case (int)SAVE:
					strReturn = "�ѱ���";
					break;
				case (int)APPROVALING:
					strReturn = "������"; 
					break;
				case (int)APPROVALED:
					strReturn = "������";
					break;	
				case (int)REFUSED:
					strReturn = "�Ѿܾ�";
					break;	
				case (int)USED:
					strReturn = "��ʹ��";
					break;
			}
			return strReturn;
		}
        public static final long[] getAllCode()
        {
            long[] lTemp =
            { DELETED, SAVE,APPROVALING,APPROVALED,USED};
            return lTemp;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.craftbrother.util.CRAconstant$TransactionStatus", officeID,
					currencyID);
		}
        /**
         * ��ʾ�����б�
         * 
         * @param out
         * @param strControlName,
         *            �ؼ�����
         * @param nType���ؼ�����
         *            ��0����ʾȫ����
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
                    case 0:
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

        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
                        break;
                    case 1:
                        lArrayID = new long[]{SAVE,APPROVALING,APPROVALED};
                        break;
                    case 2:
                        lArrayID = new long[]{SAVE,APPROVALING,APPROVALED,USED};
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
	
	//�������� added by xwhe 2007/09/10
	public static class WorkType {
		
		public static final long WAITDEALWITHWORK = 1;//������ҵ��
		
		public static final long CURRENTWORK = 2; //��������

		public static final long HISTORYWORK = 3;//�Ѱ�����

		public static final long FINISHEDWORK = 4;//�������
		
		public static final long REFUSEWORK = 5;//�ܾ�ҵ��
		
		public static final long CANCELAPPROVAL = 6;//ȡ������		
	}
	
	
	/**
	 * �������ͣ����������õ�ʱ��ʹ�ã�����Ϊ��������������������
	 * @author cqzhu
	 *
	 */
	public static class TransactionType {
		public static final long LOAN_APPLY = 1;  //��������
        public static final long LOAN_CONTRACT = 2; //�����ͬ
        public static final long CAPITAL_IN_APPLY = 2001; //�ʽ��������
        public static final long CAPITAL_OUT_APPLY = 2002; //�ʽ�������
        public static final long CAPITAL_APPLY = 2000; //�ʽ�������
        public static final long CAPITAL_LANDING_NOTICE = 2003; //�ʽ���ҵ��֪ͨ��
        public static final long CAPITAL_EXCHANGE_LISTS = 2004; //�ʽ��轻�
        public static final long TRANSDISCOUNT_CREDENCE = 17; //ת����ƾ֤
        public static final long TRANSDISCOUNT_REPURCHASECREDENCE = 18; //ת���ֻع�ƾ֤
		public static final long ZTX_INVEST_REPURCHASE = 6; //ת�������루�ع���
		public static final long ZTX_INVEST_BREAK = 7; //ת�������루��ϣ�
		public static final long ZTX_AVERAGE_REPURCHASE = 8; //ת�����������ع���
		public static final long ZTX_AVERAGE_BREAK = 9; //ת������������ϣ�
		public static final long CAPITAL_IN = SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN ; //�ʽ����
		public static final long CAPITAL_OUT = SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT; //�ʽ���
		public static final long AVERAGE_NOTIFY = SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY; //���루�ع���
		public static final long BREAK_INVEST = SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK; //���루��ϣ�
		public static final long REPURCHASE_NOTIFY = SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY ; //�������ع���
		public static final long BREAK_NOTIFY = SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY; //��������ϣ�
        public static final long SAME_BUSINESS_APPLY = 3501; //�ʲ�ת������
        public static final long SAME_BUSINESS_CONTRACT = 3502; //�ʲ�ת�ú�ͬ
        public static final long SAME_BUSINESS_NOTICE = 3503; //�ʲ�ת��ҵ��֪ͨ��
        public static final long SAME_BUSINESS_LOANCONTRACT = 3504; //�ʲ�ת�ô����ͬ
        public static final long CAPITAL_IN_REPAY = 1302; //�ʽ���뷵��
        public static final long CAPITAL_OUT_REPAY = 1304; //�ʽ�������
        public static final long PAYBACK_NOTIFY = 7103; //��ͬ�������أ��ع���
        public static final long ACCEPT_NOTIFY = 7107; //��ͬ���빺�أ��ع���
        public static final long INTEREST_PAYMENT = 7104; //��Ϣ֧��֪ͨ��
        
        public static final long CAPITAL_PAYMENT = 7110; //֧��ת�ÿ���
        public static final long INTEREST_PAYBACK = 7111; //��Ϣ�ջ�֪ͨ��
        public static final long REPURCHASE_CAPITAL = 7112; //���루�ع�������֪ͨ��
        public static final long ACCEPT_CAPITAL = 7113; //�������ع�������֪ͨ��
        public static final long CAPITAL_PAYBACK = 7114; //�յ�ת�ÿ���
        
        public static final long INVEST_REPURCHASE = 12; //ת�������루�ع���
		public static final long INVEST_BREAK = 11; //ת�������루��ϣ�
		public static final long AVERAGE_REPURCHASE = 22; //ת�����������ع���
		public static final long AVERAGE_BREAK = 21; //ת������������ϣ�
        
		public static final String getName(long ID)
		{   
			String strReturn = ""; //��ʼ������ֵ
			switch ((int)ID)
			{
				case (int)LOAN_APPLY:
					strReturn = "ת��������"; 
					break;
				case (int)LOAN_CONTRACT:
					strReturn = "ת���ֺ�ͬ"; 
					break;
				case (int)CAPITAL_IN_APPLY:
					strReturn = "�ʽ��������"; 
					break;
				case (int)CAPITAL_OUT_APPLY:
					strReturn = "�ʽ�������";
					break;
				case (int)CAPITAL_APPLY:
					strReturn = "�ʽ�������"; 
				    break;
				case (int)CAPITAL_EXCHANGE_LISTS:
					strReturn = "�ʽ��轻�"; 
				    break;				
				case (int)CAPITAL_LANDING_NOTICE:
					strReturn = "�ʽ���֪ͨ��"; 
					break;
				case (int)TRANSDISCOUNT_CREDENCE:
					strReturn = "ת����ƾ֤"; 
					break;
				case (int)TRANSDISCOUNT_REPURCHASECREDENCE:
					strReturn = "ת���ֻع�ƾ֤";
					break;					
				case (int)ZTX_INVEST_REPURCHASE:
					strReturn = "ת�������루�ع���";
					break;
				case (int)ZTX_INVEST_BREAK:
					strReturn = "ת�������루��ϣ�";
					break;
				case (int)ZTX_AVERAGE_REPURCHASE:
					strReturn = "ת�����������ع���";
					break;
				case (int)ZTX_AVERAGE_BREAK:
					strReturn = "ת������������ϣ�";
					break;
				case (int)CAPITAL_IN:
					strReturn = "�ʽ����"; 
					break;
				case (int)CAPITAL_OUT:
					strReturn = "�ʽ���";
					break;
				case (int)REPURCHASE_NOTIFY:
					strReturn = "�������ع���"; 
					break;					
				case (int)AVERAGE_NOTIFY:
					strReturn = "���루�ع���"; 
					break;
				case (int)BREAK_NOTIFY:
					strReturn = "��������ϣ�";
					break;
				case (int)BREAK_INVEST:
					strReturn = "���루��ϣ�";
					break;
				case (int)SAME_BUSINESS_APPLY:
					strReturn = "�ʲ�ת������"; 
					break;
				case (int)SAME_BUSINESS_CONTRACT:
					strReturn = "�ʲ�ת�ú�ͬ";
					break;
				case (int)SAME_BUSINESS_NOTICE:
					strReturn = "�ʲ�ת��֪ͨ��";
					break;
				case (int)SAME_BUSINESS_LOANCONTRACT:
					strReturn = "�ʲ�ת�ô����ͬ";
				    break;
				case (int)CAPITAL_IN_REPAY:
					strReturn = "�ʽ���뷵��";
					break;
				case (int)CAPITAL_OUT_REPAY:
					strReturn = "�ʽ�������";
					break;
				case (int)PAYBACK_NOTIFY:
					strReturn = "��ͬ�������أ��ع���";
					break;
				case (int)ACCEPT_NOTIFY:
					strReturn = "��ͬ���빺�أ��ع���";
					break;
				case (int)INTEREST_PAYMENT:
					strReturn = "��Ϣ֧��֪ͨ��";
					break;
				case (int)INVEST_REPURCHASE:
					strReturn ="���루�ع���";
					break;
				case (int)INVEST_BREAK:
					strReturn ="���루��ϣ�";
					break;
				case (int)AVERAGE_REPURCHASE:
					strReturn ="�������ع���";
					break;
				case (int)AVERAGE_BREAK:
					strReturn ="��������ϣ�";
					break;
				case (int)CAPITAL_PAYMENT:
					strReturn = "֧��ת�ÿ���";
					break;
				case (int)INTEREST_PAYBACK:
					strReturn ="��Ϣ�ջ�֪ͨ��";
					break;
				case (int)REPURCHASE_CAPITAL:
					strReturn ="���루�ع�������֪ͨ��";
					break;
				case (int)ACCEPT_CAPITAL:
					strReturn ="�������ع�������֪ͨ��";
					break;
				case (int)CAPITAL_PAYBACK:
					strReturn ="�յ�ת�ÿ���";
					break;
			}
			return strReturn;
		}
		
		//����ҵ�����ͻ�ȡ���轻������
		public static final long[] getTransactType(long type)
		{
			long[] lArray = null;
			switch ((int)type)
			{
				case (int)CRAconstant.SameBusinessAttribute.DISCOUNT:  //ת����
					lArray = new long[]{LOAN_APPLY,LOAN_CONTRACT,TRANSDISCOUNT_REPURCHASECREDENCE};
					break;
				case (int)CRAconstant.SameBusinessAttribute.FUND_ATTORN: //�ʽ���
					lArray = new long[]{CAPITAL_APPLY,CAPITAL_EXCHANGE_LISTS,CAPITAL_LANDING_NOTICE};
					break;
				case (int)CRAconstant.SameBusinessAttribute.SAME_BUSINESS: //�ʲ�ת��
					lArray = new long[]{SAME_BUSINESS_APPLY,SAME_BUSINESS_CONTRACT,SAME_BUSINESS_NOTICE,SAME_BUSINESS_LOANCONTRACT};
					break;
				default:
					lArray = new long[]{};
					break;
			}
				
			return lArray;
		}
		//��ȡ������Ҫ�Ľ�������
        public static final long[] getTransCode()
        {
            long[] lTemp =
            { SAME_BUSINESS_APPLY,ZTX_INVEST_REPURCHASE,ZTX_INVEST_BREAK,ZTX_AVERAGE_REPURCHASE, ZTX_AVERAGE_BREAK, 
              CAPITAL_IN, CAPITAL_OUT,AVERAGE_NOTIFY,REPURCHASE_NOTIFY,BREAK_NOTIFY,CAPITAL_APPLY,SAME_BUSINESS_CONTRACT,SAME_BUSINESS_NOTICE,LOAN_APPLY,
              LOAN_CONTRACT,TRANSDISCOUNT_CREDENCE,TRANSDISCOUNT_REPURCHASECREDENCE,CAPITAL_LANDING_NOTICE,SAME_BUSINESS_NOTICE,SAME_BUSINESS_LOANCONTRACT};
            return lTemp;
        }
        //����ͬҵ����ģ��ҵ��������������Code       lijunli  2010.8.23
        public static final long[] getAlertCode(){
        	long[] lTemp=
        	{ ZTX_INVEST_REPURCHASE,ZTX_AVERAGE_REPURCHASE,AVERAGE_NOTIFY ,REPURCHASE_NOTIFY,CAPITAL_OUT_REPAY,CAPITAL_IN_REPAY };
        	return lTemp;
        	
        }
        //����ͬҵ����ģ��ҵ������������������              lijunli  2010.8.30
        public static final String getAlterName(long ID)
		{   
			String strReturn = ""; //��ʼ������ֵ
			switch ((int)ID)
			{							
			case (int)ZTX_INVEST_REPURCHASE:
				strReturn = "ת�������루�ع���";
				break;
			
			case (int)ZTX_AVERAGE_REPURCHASE:
				strReturn = "ת�����������ع���";
				break;		
		
			case (int)REPURCHASE_NOTIFY:
				strReturn = "��ͬ�������ع���"; 
				break;		
			case (int)AVERAGE_NOTIFY:
			    strReturn = "��ͬ���루�ع���";
			    break;
					
			case (int)CAPITAL_IN_REPAY:
				strReturn = "�ʽ���뷵��";
				break;
			case (int)CAPITAL_OUT_REPAY:
				strReturn = "�ʽ�������";
				break;					
			}
			return strReturn;
		}
        
        //��ȡת���ֲ�ѯʱ���õĽ�������
        public static final long[] getTransDiscountCode()
        {
            long[] lTemp =
            {INVEST_REPURCHASE, INVEST_BREAK, AVERAGE_REPURCHASE, AVERAGE_BREAK};
            return lTemp;
        }
        
      //  ��ȡ������Ҫ���ŵĽ�������
        public static final long[] getCreditCode()
        {
            long[] lTemp =
            { ZTX_INVEST_REPURCHASE,ZTX_INVEST_BREAK,ZTX_AVERAGE_REPURCHASE, ZTX_AVERAGE_BREAK, 
              CAPITAL_IN, CAPITAL_OUT,AVERAGE_NOTIFY,REPURCHASE_NOTIFY,BREAK_NOTIFY,BREAK_INVEST};
            return lTemp;
        }
        
        //����˾�Խ��׶������ſ��ܷ����Ľ�������
        public static final long[] getPositiveCreditCode()
        {
            long[] lTemp =
            {ZTX_INVEST_REPURCHASE, ZTX_INVEST_BREAK, CAPITAL_OUT, AVERAGE_NOTIFY, BREAK_INVEST};
            return lTemp;
        }
        
        //ͬ�жԲ���˾���ſ��ܷ����Ľ�������
        public static final long[] getOppositeCreditCode()
        {
            long[] lTemp =
            {ZTX_AVERAGE_REPURCHASE, ZTX_AVERAGE_BREAK, CAPITAL_IN, REPURCHASE_NOTIFY, BREAK_NOTIFY};
            return lTemp;
        }
        
        //ͬ�ж��ڲ���λ���ſ��ܷ����Ľ�������
        public static final long[] getAttormTransCode()
        {
            long[] lTemp =
            {REPURCHASE_NOTIFY,BREAK_NOTIFY};
            return lTemp;
        }

        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.settlement.util.CRAconstant$TransactionType", officeID,
					currencyID);
		}
        
        public static final long[] getCodeByTransactonType(long transactionType)
        {
        	long[] lTemp = null;
        	switch ((int) transactionType)
        	{
        		//�ʽ���ҵ��֪ͨ��
    		  	case(int)CAPITAL_LANDING_NOTICE :
    		  		lTemp = new long[]{
    		  			CAPITAL_IN,
    		  			CAPITAL_OUT,
    		  			CAPITAL_IN_REPAY,
    		  			CAPITAL_OUT_REPAY
    		  		};
    		  		break;
    		  	//�ʽ��轻�
    		  	case (int) CAPITAL_EXCHANGE_LISTS:
    		  		lTemp = new long[]{
    		  			CAPITAL_IN,
    		  			CAPITAL_OUT,
    		  			CAPITAL_IN_REPAY,
    		  			CAPITAL_OUT_REPAY   		  			
    		  		};
    		  		break;
    		  	//�ʽ�������
    		  	case(int)CAPITAL_APPLY :
    		  		lTemp = new long[]{
    		  			CAPITAL_IN,
    		  			CAPITAL_OUT   		  			
    		  		};
    		  		break;
    		  	//ת����
    		  	case(int)CRAconstant.SameBusinessAttribute.DISCOUNT :
    		  		lTemp = new long[]{
    		  			LOAN_APPLY,
    		  			LOAN_CONTRACT,
    		  			//TRANSDISCOUNT_CREDENCE,
    		  	        TRANSDISCOUNT_REPURCHASECREDENCE
    		  		};
    		  		break;
    		  	//�ʲ�ת������
    		  	case(int)SAME_BUSINESS_APPLY :
    		  		lTemp = new long[]{
    		  			REPURCHASE_NOTIFY,
    		  			AVERAGE_NOTIFY,
    		  			BREAK_NOTIFY,
    		  			BREAK_INVEST
    		  		};
    		  		break;
//        		//�ʲ�ת�ú�ͬ  
//    		  	case(int)SAME_BUSINESS_CONTRACT :
//    		  		lTemp = new long[]{
//    		  			REPURCHASE_NOTIFY,
//    		  			AVERAGE_NOTIFY,
//    		  			BREAK_NOTIFY
//    		  		};
//    		  		break; 
    		  		
    		  	//�ʲ�ת�ú�ͬ  
    		  	case(int)SAME_BUSINESS_CONTRACT :
    		  		lTemp = new long[]{
    		  			REPURCHASE_NOTIFY,
    		  			AVERAGE_NOTIFY,
    		  			BREAK_NOTIFY,
    		  			BREAK_INVEST
    		  		};
    		  		break; 
    		  	/**
        		//�ʲ�ת��ҵ��֪ͨ��
    		  	case(int)SAME_BUSINESS_NOTICE :
    		  		lTemp = new long[]{
    		  			REPURCHASE_NOTIFY,
    		  			AVERAGE_NOTIFY,
    		  			BREAK_NOTIFY,
    		  			PAYBACK_NOTIFY,
    		  			ACCEPT_NOTIFY,
    		  			INTEREST_PAYMENT
    		  		};
    		  		break;
    		  		*/
    		  	//�ʲ�ת��ҵ��֪ͨ��
    		  	case(int)SAME_BUSINESS_NOTICE :
    		  		lTemp = new long[]{
    		  			CAPITAL_PAYMENT,
    		  			INTEREST_PAYBACK,
    		  			REPURCHASE_CAPITAL,
    		  			ACCEPT_CAPITAL,
    		  			CAPITAL_PAYBACK,
    		  			INTEREST_PAYMENT
    		  		};
    		  		break;
    		  	case(int)SAME_BUSINESS_LOANCONTRACT:
    		  		lTemp = new long[]{
    		  			AVERAGE_REPURCHASE,
    		  			AVERAGE_BREAK
    		  		};
    		  		break;
        		//����
    		  	case(int)CRAconstant.SameBusinessAttribute.OTHER :
    		  		lTemp = new long[]{
    					REPURCHASE_NOTIFY,
    					AVERAGE_NOTIFY,
    					BREAK_NOTIFY,
    					BREAK_INVEST,
    					ZTX_INVEST_REPURCHASE,
    					ZTX_INVEST_BREAK,
    					ZTX_AVERAGE_REPURCHASE,
    					ZTX_AVERAGE_BREAK,
    					CAPITAL_IN,
    					CAPITAL_OUT
    		  		};
    		  		break;
    		  	//ת����ƾ֤
    		  	case(int)TRANSDISCOUNT_CREDENCE:
    		  		lTemp = new long[]{
    		  			ZTX_INVEST_REPURCHASE, 	//ת�������루�ع���
    		  			ZTX_INVEST_BREAK, 		//ת�������루��ϣ�
    		  			ZTX_AVERAGE_REPURCHASE,	//ת�����������ع���
    		  			ZTX_AVERAGE_BREAK 		//ת������������ϣ�
    		  		};
    		  		break;
    		  	//ת���ֻع�ƾ֤
    		  	case(int)TRANSDISCOUNT_REPURCHASECREDENCE:
    		  		lTemp = new long[]{
    		  			ZTX_INVEST_REPURCHASE, 	//ת�������루�ع���
    		  			ZTX_INVEST_BREAK, 		//ת�������루��ϣ�
    		  			ZTX_AVERAGE_REPURCHASE,	//ת�����������ع���
    		  			ZTX_AVERAGE_BREAK 		//ת������������ϣ�
    		  		};
    		  		break;
    		  	default:
    		  		lTemp = new long[]{};
        	}            
            return lTemp;
        }     
        
        public static final long[] getAllCode(long lModuleType)
        {
        	long[] lTemp = null;
        	switch ((int) lModuleType)
        	{
        		  case(int)ModuleType.CRAFTBROTHER:
        		
        			  lTemp = new long[]{
        				  LOAN_APPLY,
        				  LOAN_CONTRACT,
        				// CAPITAL_IN_APPLY,
        				// CAPITAL_OUT_APPLY,
        				  CAPITAL_LANDING_NOTICE,
        				  TRANSDISCOUNT_CREDENCE,
        				  TRANSDISCOUNT_REPURCHASECREDENCE,
        				  ZTX_INVEST_REPURCHASE,
        				  ZTX_INVEST_BREAK,
        				  ZTX_AVERAGE_REPURCHASE,
        				  ZTX_AVERAGE_BREAK,
        				  CAPITAL_IN,
        				  CAPITAL_OUT,
        				  REPURCHASE_NOTIFY,
        				  AVERAGE_NOTIFY,
        				  BREAK_NOTIFY,
        				 //SAME_BUSINESS_APPLY,
        				 //SAME_BUSINESS_CONTRACT,
        				 //SAME_BUSINESS_NOTICE,
    			    };
        		  
     			break;       			
        	}            
            return lTemp;
        } 
        
        //ͬҵ������ͬ��ѯʱʹ��
        public static final long[] getContractQueryCode()
        {
            long[] lTemp =
            {AVERAGE_NOTIFY, REPURCHASE_NOTIFY, BREAK_NOTIFY};
            return lTemp;
        }
        
        //�ʲ�ת����Ϣ����
        public static final long[] getCapitalTransferInterestQueryCode()
        {
            long[] lTemp =
            {REPURCHASE_NOTIFY, BREAK_NOTIFY};
            return lTemp;
        }  
    
        /**
         * ��ʾ�����б�
         * 
         * @param out
         * @param strControlName,
         *            �ؼ�����
         * @param nType���ؼ�����
         *            ��0����ʾȫ����
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
                    case 0:
                        //lArrayID = getCreditCode();
                    	lArrayID = getContractQueryCode();
                        break;
                    case 1:
                        lArrayID = getPositiveCreditCode();
                        break;  
                    case 2:
                        lArrayID = getOppositeCreditCode();
                        break;   
                    case 3:
                        lArrayID = getAttormTransCode();
                        break; 
                    case 4:
                        lArrayID = getTransCode();
                        break;
                    case 5:
                        lArrayID = getContractQueryCode();
                        break;
                    case 6:
                    	lArrayID = getCapitalTransferInterestQueryCode();
                    	break;
                    case 7:
                    	lArrayID = getTransDiscountCode();
                    	break;
                    default:
                    	lArrayID= getCreditCode();
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
         * ��ʾ�����б�
         * 
         * @param out
         * @param strControlName,
         *            �ؼ�����
         * @param nTransType����������
         * @param lSelectValue
         * @param isNeedAll���Ƿ���Ҫ��ȫ���
         * @param isNeedBlank
         *            �Ƿ���Ҫ�հ���
         * @param strProperty
         */
        public static final void showTransList(JspWriter out, String strControlName, int nTransType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
            	lArrayID = getCodeByTransactonType(nTransType);
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
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
                        break;
                    case 1:
                        lArrayID = getPositiveCreditCode();
                        break;  
                    case 2:
                        lArrayID = getOppositeCreditCode();
                        break;   
                    case 3:
                        lArrayID = getAttormTransCode();
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
    	public static class counterpartBank
    	{
    		public static final long VALID = 1;//��Ч
    		public static final long INVALID = 0;//��Ч
    		
    	}
	}
 /*
  * add by xwhe 2009-06-30
  * �Ŵ��ʲ�ת�ý�������
  */       
   public static class CraTransactionType 
   {
	   public static final long REPURCHASE_NOTIFY = 1001;//�����ع�
	   
	   public static final long BREAK_NOTIFY = 1002;//��������
	   
	   public static final long AVERAGE_NOTIFY =1003;//����ع�
	   
	   public static final long BREAK_AVERAGE = 1004;//�������
	   
	   public static final String getName(long transactionTypeID )
	   {
		   String strReturn = ""; //��ʼ������ֵ
			switch ((int)transactionTypeID)
			{
				case (int)REPURCHASE_NOTIFY:
					strReturn = "�����ع�"; 
					break;
				case (int)BREAK_NOTIFY:
					strReturn = "��������"; 
					break;
				case (int)AVERAGE_NOTIFY:
					strReturn = "����ع�"; 
					break;
				case (int)BREAK_AVERAGE:
					strReturn = "�������";
					break;
			}
			return strReturn;
				
	   }
	   public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
       {
           long[] lArrayID = null;
           String[] strArrayName = null;
           try
           {
               switch (nType)
               {
                   case 0:
                       lArrayID = getAllCode(lOfficeID,lCurrencyID);
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
       public static final long[] getAllCode(long officeID, long currencyID) 
       {
			return Constant.getAllCode(
					"com.iss.itreasury.craftbrother.util.CRAconstant$CraTransactionType", officeID,
					currencyID);
		}
	   
       }
   /*
    * add by xwhe 2009-06-30
    * �Ŵ��ʲ�ת��ҵ������
    */       
     public static class CraActionType 
     {
         public static final long SAME_BUSINESS_APPLY = 3501; //�Ŵ��ʲ�ת������
         
         public static final long SAME_BUSINESS_CONTRACT = 3502; //�Ŵ��ʲ�ת�ú�ͬ        
         
         public static final long SAME_BUSINESS_NOTICE = 3503; //�Ŵ��ʲ�ת��ҵ��֪ͨ��
         
         public static final long CRA_LOANCONTRACT_APPLY = 3504; //�����ͬ����
  	  
  	   
  	   public static final String getName(long transactionTypeID )
  	   {
  		   String strReturn = ""; //��ʼ������ֵ
  			switch ((int)transactionTypeID)
  			{
  				case (int)SAME_BUSINESS_APPLY:
  					strReturn = "�Ŵ��ʲ�ת������"; 
  					break;
  				case (int)SAME_BUSINESS_CONTRACT:
  					strReturn = "�Ŵ��ʲ�ת�ú�ͬ"; 
  					break;
  				case (int)SAME_BUSINESS_NOTICE:
  					strReturn = "�Ŵ��ʲ�ת��ҵ��֪ͨ��"; 
  					break;
  				case (int)CRA_LOANCONTRACT_APPLY:
  					strReturn = "�����ͬ����"; 
  					break;
  			}
  			return strReturn;
  				
  	   }
  	   public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
         {
             long[] lArrayID = null;
             String[] strArrayName = null;
             try
             {
                 switch (nType)
                 {
                     case 0:
                         lArrayID = getAllCode(lOfficeID,lCurrencyID);
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
         public static final long[] getAllCode(long officeID, long currencyID) 
         {
  			return Constant.getAllCode(
  					"com.iss.itreasury.craftbrother.util.CRAconstant$CraActionType", officeID,
  					currencyID);
  		 }
         
         public static final long[] getCodeByTransactonType(long transactionType)
         {
         	long[] lTemp = null;
         	switch ((int) transactionType)
         	{
         		
     		  	//�Ŵ��ʲ�ת������
     		  	case(int)SAME_BUSINESS_APPLY :
     		  		lTemp = new long[]{
     		  			CRAconstant.CraTransactionType.REPURCHASE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_NOTIFY,
     		  			CRAconstant.CraTransactionType.AVERAGE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_AVERAGE
     		  		};
     		  		break;
         		//�Ŵ��ʲ�ת�ú�ͬ  
     		  	case(int)SAME_BUSINESS_CONTRACT :
     		  		lTemp = new long[]{
     		  			CRAconstant.CraTransactionType.REPURCHASE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_NOTIFY,
     		  			CRAconstant.CraTransactionType.AVERAGE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_AVERAGE
     		  		};
     		  		break; 
         		//�Ŵ��ʲ�ת��ҵ��֪ͨ��
     		  	case(int)SAME_BUSINESS_NOTICE :
     		  		lTemp = new long[]{
     		  			CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT,
     		  			CRAconstant.CRANoticeActionType.TRANSFERPAYAMOUNT,    	
     		  		};
     		  		break;
                 //�����ͬ���봦��
     		  	case(int)CRA_LOANCONTRACT_APPLY :
     		  		lTemp = new long[]{
     		  			CRAconstant.CraTransactionType.REPURCHASE_NOTIFY,
     		  			CRAconstant.CraTransactionType.BREAK_NOTIFY,    	
     		  		};
     		  		break;
         	}            
             return lTemp;
         }     
  	   
         }
 /*
  * �Ŵ��ʲ�ת��֪ͨ����������
  */
     public static class CRANoticeActionType 
     {
    	 
         public static final long TRANSFERREPAYAMOUNT = 11; //�Ŵ��ʲ�ת���տ�
         
         public static final long TRANSFERPAYAMOUNT = 22; //�Ŵ��ʲ�ת�ø���
         
         public static final String getName(long transactionTypeID )
    	   {
    		   String strReturn = ""; //��ʼ������ֵ
    			switch ((int)transactionTypeID)
    			{
    				case (int)TRANSFERREPAYAMOUNT:
    					strReturn = "�Ŵ��ʲ�ת���տ�"; 
    					break;
    				case (int)TRANSFERPAYAMOUNT:
    					strReturn = "�Ŵ��ʲ�ת�ø���"; 
    					break;
    			}
    			return strReturn;
    				
    	   }
         public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
         {
             long[] lArrayID = null;
             String[] strArrayName = null;
             try
             {
                 switch (nType)
                 {
                     case 0:
                         lArrayID = getAllCode(lOfficeID,lCurrencyID);
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
         public static final long[] getAllCode(long officeID, long currencyID) 
         {
  			return Constant.getAllCode(
  					"com.iss.itreasury.craftbrother.util.CRAconstant$CRANoticeActionType", officeID,
  					currencyID);
  		 }
  	  
      }
      /*
       * �Ŵ��ʲ�ת��֪ͨ���Ƿ����
       */
     public static class ISURROGATEPAY
     {
    	 public static final long ISTRUE = 1; //����
    	 
    	 public static final long ISNOT = 2; //�Ǵ���
    	 
    	 public static final String getName(long ID )
  	   {
  		   String strReturn = ""; //��ʼ������ֵ
  			switch ((int)ID)
  			{
  				case (int)ISTRUE:
  					strReturn = "��"; 
  					break;
  				case (int)ISNOT:
  					strReturn = "��"; 
  					break;
  			}
  			return strReturn;
  				
  	   }
    	 public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
         {
             long[] lArrayID = null;
             String[] strArrayName = null;
             try
             {
                 switch (nType)
                 {
                     case 0:
                         lArrayID = getAllCode(lOfficeID,lCurrencyID);
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
         public static final long[] getAllCode(long officeID, long currencyID) 
         {
  			return Constant.getAllCode(
  					"com.iss.itreasury.craftbrother.util.CRAconstant$ISURROGATEPAY", officeID,
  					currencyID);
  		 } 
    	 
     }
     
     /*
      * �Ŵ��ʲ�ת��֪ͨ���Ƿ���㻧ֱ������
      */
    public static class ISDIRECTSETT
    {
   	 public static final long ISTRUE = 1; //��
   	 
   	 public static final long ISNOT = 2; //��
   	 
   	 public static final String getName(long ID )
 	   {
 		   String strReturn = ""; //��ʼ������ֵ
 			switch ((int)ID)
 			{
 				case (int)ISTRUE:
 					strReturn = "��"; 
 					break;
 				case (int)ISNOT:
 					strReturn = "��"; 
 					break;
 			}
 			return strReturn;
 				
 	   }
   	 public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
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
        public static final long[] getAllCode(long officeID, long currencyID) 
        {
 			return Constant.getAllCode(
 					"com.iss.itreasury.craftbrother.util.CRAconstant$ISDIRECTSETT", officeID,
 					currencyID);
 		 } 
   	 
    }
     
     public static class Counterparttype
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
     	public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
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
     	
     	
     	public static final long[] getAllCode(long officeID, long currencyID) 
        {
 			return Constant.getAllCode(
 					"com.iss.itreasury.craftbrother.util.CRAconstant$Counterparttype", officeID,
 					currencyID);
 		 } 
     	
     }
     
     /* �����Ѽ��㷽ʽ */
 	public static class ChargeCommisonPayType {
 		
 		public static final long  CHARGENONE  = 0;
 		
 		public static final long CHARGEAMOUNT = 1;

 		public static final long CHARGEINTEREST = 2;

 		public static final long CHARGEOTHER = 3;

 		public static final String getName(long lCode) {
 			String strReturn = ""; // ��ʼ������ֵ
 			switch ((int) lCode) {
 			case (int) CHARGENONE:
 				strReturn = "����ȡ������";
 				break;
 			case (int) CHARGEAMOUNT:
 				strReturn = "�����Ĺ̶��ٷֱ�";
 				break;
 			case (int) CHARGEINTEREST:
 				strReturn = "��������Ϣ���Ĺ̶��ٷֱ�";
 				break;
 			case (int) CHARGEOTHER:
 				strReturn = "���׶�����ȡ�̶�������Ϣ��ʣ��Ϊ����˾������";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { CHARGENONE, CHARGEAMOUNT, CHARGEINTEREST, CHARGEOTHER };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$ChargeCommisonPayType",
 							officeID, currencyID);
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
 		public static final void showList(JspWriter out, String strControlName,
 				int nType, long lSelectValue, boolean isNeedAll,
 				boolean isNeedBlank, String strProperty) {
 			long[] lArrayID = null;
 			String[] strArrayName = null;
 			try {
 				switch (nType) {
 				case 0:
 					lArrayID = getAllCode();
 					break;
 				}
 				strArrayName = new String[lArrayID.length];
 				for (int i = 0; i < lArrayID.length; i++) {
 					strArrayName[i] = getName(lArrayID[i]);
 				}
 				showCommonList(out, strControlName, lArrayID, strArrayName,
 						lSelectValue, isNeedAll, strProperty, isNeedBlank);
 			} catch (Exception ex) {
 				System.out.println(ex.toString());
 			}
 		}

     }
    /* ��Ϣ��ʽ */
 	public static class ClearInterestType {
 		
 		public static final long  CLEARMONTH  = 1;
 		
 		public static final long CLEARQUARTER = 2;

 		public static final long CLEARENDDATE = 3;

 		public static final long CLEAROTHER = 4;

 		public static final String getName(long lCode) {
 			String strReturn = ""; // ��ʼ������ֵ
 			switch ((int) lCode) {
 			case (int) CLEARMONTH:
 				strReturn = "����֧��";
 				break;
 			case (int) CLEARQUARTER:
 				strReturn = "������֧��";
 				break;
 			case (int) CLEARENDDATE:
 				strReturn = "����֧��";
 				break;
 			case (int) CLEAROTHER:
 				strReturn = "����";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { CLEARMONTH, CLEARQUARTER, CLEARENDDATE, CLEAROTHER };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$ClearInterestType",
 							officeID, currencyID);
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
 		public static final void showList(JspWriter out, String strControlName,
 				int nType, long lSelectValue, boolean isNeedAll,
 				boolean isNeedBlank, String strProperty) {
 			long[] lArrayID = null;
 			String[] strArrayName = null;
 			try {
 				switch (nType) {
 				case 0:
 					lArrayID = getAllCode();
 					break;
 				}
 				strArrayName = new String[lArrayID.length];
 				for (int i = 0; i < lArrayID.length; i++) {
 					strArrayName[i] = getName(lArrayID[i]);
 				}
 				showCommonList(out, strControlName, lArrayID, strArrayName,
 						lSelectValue, isNeedAll, strProperty, isNeedBlank);
 			} catch (Exception ex) {
 				System.out.println(ex.toString());
 			}
 		}

     }
 	 /* ��������ȡ��ʽ */
 	public static class CommisonPayType {
 		
 		public static final long  DEDUCTION  = 1;
 		
 		public static final long FANHUAN = 2;
 		
 		public static final long NOCOMMISSION = 3;

 		public static final String getName(long lCode) {
 			String strReturn = ""; // ��ʼ������ֵ
 			switch ((int) lCode) {
 			case (int) DEDUCTION:
 				strReturn = "�ȿ۳�";
 				break;
 			case (int) FANHUAN:
 				strReturn = "�󷵻�";
 				break;
 			case (int) NOCOMMISSION:
 				strReturn = "��";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { DEDUCTION, FANHUAN ,NOCOMMISSION};
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$CommisonPayType",
 							officeID, currencyID);
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
 		public static final void showList(JspWriter out, String strControlName,
 				int nType, long lSelectValue, boolean isNeedAll,
 				boolean isNeedBlank, String strProperty) {
 			long[] lArrayID = null;
 			String[] strArrayName = null;
 			try {
 				switch (nType) {
 				case 0:
 					lArrayID = getAllCode();
 					break;
 				case 1:
 					lArrayID = new long[]{NOCOMMISSION};
 					break;
 				case 2:
 					lArrayID = new long[]{DEDUCTION,FANHUAN};
 					break;
 				}
 				strArrayName = new String[lArrayID.length];
 				for (int i = 0; i < lArrayID.length; i++) {
 					strArrayName[i] = getName(lArrayID[i]);
 				}
 				showCommonList(out, strControlName, lArrayID, strArrayName,
 						lSelectValue, isNeedAll, strProperty, isNeedBlank);
 			} catch (Exception ex) {
 				System.out.println(ex.toString());
 			}
 		}

     }
 	 /* ���˻��������� */
 	public static class operationType {
 		
 		public static final long  CLIENT  = 1; //�Գ�Ա��λ
 		
 		public static final long CONTERPART= 2; //�Խ��׶���

 		public static final String getName(long lCode) {
 			String strReturn = ""; // ��ʼ������ֵ
 			switch ((int) lCode) {
 			case (int) CLIENT:
 				strReturn = "��Ա��λ";
 				break;
 			case (int) CONTERPART:
 				strReturn = "���׶���";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { CLIENT, CONTERPART };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$operationType",
 							officeID, currencyID);
 		}
 	}
 	
	 /* ���շ�ʽ */
 	public static class AgentType {
 		
 		public static final long  AMOUNT  = 1; //���ձ���
 		
 		public static final long AMOUNTANDINTEREST= 2; //���ձ�Ϣ

 		public static final String getName(long lCode) {
 			String strReturn = ""; // ��ʼ������ֵ
 			switch ((int) lCode) {
 			case (int) AMOUNT:
 				strReturn = "���ձ���";
 				break;
 			case (int) AMOUNTANDINTEREST:
 				strReturn = "���ձ�Ϣ";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { AMOUNT, AMOUNTANDINTEREST };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$AgentType",
 							officeID, currencyID);
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
 		public static final void showList(JspWriter out, String strControlName,
 				int nType, long lSelectValue, boolean isNeedAll,
 				boolean isNeedBlank, String strProperty) {
 			long[] lArrayID = null;
 			String[] strArrayName = null;
 			try {
 				switch (nType) {
 				case 0:
 					lArrayID = getAllCode();
 					break;
 				}
 				strArrayName = new String[lArrayID.length];
 				for (int i = 0; i < lArrayID.length; i++) {
 					strArrayName[i] = getName(lArrayID[i]);
 				}
 				showCommonList(out, strControlName, lArrayID, strArrayName,
 						lSelectValue, isNeedAll, strProperty, isNeedBlank);
 			} catch (Exception ex) {
 				System.out.println(ex.toString());
 			}
 		}
 	}
 	
	 /* �����ع����ʽ */
 	public static class PayType {
 		
 		public static final long  INTEREST  = 1; //��Ϣ
 		
 		public static final long AMOUNTANDINTEREST= 2; //����Ϣ

 		public static final String getName(long lCode) {
 			String strReturn = ""; // ��ʼ������ֵ
 			switch ((int) lCode) {
 			case (int) INTEREST:
 				strReturn = "��Ϣ";
 				break;
 			case (int) AMOUNTANDINTEREST:
 				strReturn = "������Ϣ";
 				break;
 			}
 			return strReturn;
 		}

 		public static final long[] getAllCode() {
 			long[] lTemp = { INTEREST, AMOUNTANDINTEREST };
 			return lTemp;
 		}

 		public static final long[] getAllCode(long officeID, long currencyID) {
 			return Constant
 					.getAllCode(
 							"com.iss.itreasury.craftbrother.util.CRAconstant$PayType",
 							officeID, currencyID);
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
 		public static final void showList(JspWriter out, String strControlName,
 				int nType, long lSelectValue, boolean isNeedAll,
 				boolean isNeedBlank, String strProperty) {
 			long[] lArrayID = null;
 			String[] strArrayName = null;
 			try {
 				switch (nType) {
 				case 0:
 					lArrayID = getAllCode();
 					break;
 				}
 				strArrayName = new String[lArrayID.length];
 				for (int i = 0; i < lArrayID.length; i++) {
 					strArrayName[i] = getName(lArrayID[i]);
 				}
 				showCommonList(out, strControlName, lArrayID, strArrayName,
 						lSelectValue, isNeedAll, strProperty, isNeedBlank);
 			} catch (Exception ex) {
 				System.out.println(ex.toString());
 			}
 		}
 	}
 	//�ʲ�ת��֪ͨ������
	public static class NoticeFormType
	{
        public static final long CAPITAL_PAYMENT = SECConstant.NoticeFormType.CAPITAL_PAYMENT; //֧��ת�ÿ���
        public static final long INTEREST_PAYBACK = SECConstant.NoticeFormType.INTEREST_PAYBACK; //��Ϣ�ջ�֪ͨ��
        public static final long CAPITAL_REPURCHASE = SECConstant.NoticeFormType.CAPITAL_REPURCHASE; //����֪ͨ��
        public static final long INTEREST_PAYMENT = SECConstant.NoticeFormType.INTEREST_PAYMENT; //��Ϣ֧��֪ͨ��
        public static final long CAPITAL_PAYBACK = SECConstant.NoticeFormType.CAPITAL_PAYBACK; //�յ�ת�ÿ���
        
        
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
                         lArrayID[1] = INTEREST_PAYBACK;
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

		
	
