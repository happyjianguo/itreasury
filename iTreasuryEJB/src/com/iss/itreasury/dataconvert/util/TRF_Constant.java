/*
 * Created on 2006-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TRF_Constant {

    //�ϴ��ļ���·��
    public static final String UPLOADPATH="D:/upload/dataconvert/user_data/";
    //��ʼ�����ݿ�sql�ļ���url
    public static final String INITDATABASESQLFILEURL="d:/sql.txt";
    
    /**
     * ���񼶱�
     */
    public static class ServiceLevel {
        public static final long IMPORTANTGUEST = 1;

        public static final long HONOUREDGUEST = 2;

        public static final long COMMONGUEST = 3;

        public static final long RISKGUEST = 4;

        public static final String getName(long lCode) {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode) {
            case (int) IMPORTANTGUEST:
                strReturn = "�ص�ͻ�";
                break;
            case (int) HONOUREDGUEST:
                strReturn = "����ͻ�";
                break;
            case (int) COMMONGUEST:
                strReturn = "��ͨ�ͻ�";
                break;
            case (int) RISKGUEST:
                strReturn = "���տͻ�";
                break;
            }
            return strReturn;
        }

        public static final long getValue(String name) {
            if ("�ص�ͻ�".equalsIgnoreCase(name)) {
                return 1;
            }
            if ("����ͻ�".equalsIgnoreCase(name)) {
                return 2;
            }
            if ("��ͨ�ͻ�".equalsIgnoreCase(name)) {
                return 3;
            }
            if ("���տͻ�".equalsIgnoreCase(name)) {
                return 4;
            }
            return -1;
        }
    }
    
    /**
     * ���м�¼��nstatus
     */
    public static class RecordStatus
    {
        public static final long VALID = 1; //��Ч

        public static final long INVALID = 0; //��Ч(ɾ��)

        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) VALID:
                    strReturn = "��Ч";
                    break;
                case (int) INVALID:
                    strReturn = "��Ч";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("��Ч".equalsIgnoreCase(name)){
                return 1;
            }
            if("��Ч".equalsIgnoreCase(name)){
                return 0;
            }
            return -1;
        }
    }
    
    /**
     * �˻�״̬
     */
    public static class AccountStatus
    {
        public static final long NORMAL = 1; //����

        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) NORMAL:
                    strReturn = "����";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("����".equalsIgnoreCase(name)){
                return 1;
            }
            return -1;
        }
    }
    
    /**
     * ���˻�״̬
     */
    public static class SubAccountStatus
    {
        public static final long NORMAL = 1; //δ����

        public static final long FINISH = 2; //�ѽ���

        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) NORMAL:
                    strReturn = "δ����";
                    break;
                case (int) FINISH:
                    strReturn = "�ѽ���";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("δ����".equalsIgnoreCase(name)){
                return 1;
            }
            if("�ѽ���".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    }    
        
    /**
     * ��,��
     */
    public static class YesOrNo
    {
        public static final long YES = 1; //��

        public static final long NO = 2; //��

        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) YES:
                    strReturn = "��";
                    break;
                case (int) NO:
                    strReturn = "��";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("��".equalsIgnoreCase(name)){
                return 1;
            }
            if("��".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    }
    
    /**
     * �˻�����״̬ 
     */
    public static class AccountCheckStatus
    {
        public static final long NEWSAVE = 1; //����

        public static final long OLDSAVE = 2; //���޸�

        public static final long BATCHSAVE = 3; //�����޸�

        public static final long CHECK = 4; //����

        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) NEWSAVE:
                    strReturn = "δ����";
                    break;
                case (int) OLDSAVE:
                    strReturn = "���޸�δ����";
                    break;
                case (int) BATCHSAVE:
                    strReturn = "�����޸�δ����";
                    break;
                case (int) CHECK:
                    strReturn = "�Ѹ���";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("δ����".equalsIgnoreCase(name)){
                return 1;
            }
            if("���޸�δ����".equalsIgnoreCase(name)){
                return 2;
            }
            if("�����޸�δ����".equalsIgnoreCase(name)){
                return 3;
            }
            if("�Ѹ���".equalsIgnoreCase(name)){
                return 4;
            }
            return -1;
        }
    }  
    
    /**
     * ����
     */
    public static class CurrencyType
    {
        //���֣�����������һ��----//haier ���±���
        public static final long RMB = 1; //�����---------------/��ͳһ/ CNY
        public static final long USD = 2; //��Ԫ
        
        public static final String getName(long lCode){
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) RMB:
                    strReturn = "�����";
                    break;
                case (int) USD:
                    strReturn = "��Ԫ";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("�����".equalsIgnoreCase(name)){
                return 1;
            }
            if("��Ԫ".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    } 
    
    /**
     * ͸֧����
     */
    public static class AccountOverDraftType
    {
        public static final long ALL = 1; //����͸֧(������)

        public static final long CONSIGN = 2; //����ί���տ�͸֧(��ί���տ�)

        public static final long INTEREST = 3; //����Ϣ͸֧(��Ϣ)

        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) ALL:
                    strReturn = "����͸֧";
                    break;
                case (int) CONSIGN:
                    strReturn = "����ί���տ�͸֧";
                    break;
                case (int) INTEREST:
                    strReturn = "����Ϣ͸֧";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("����͸֧".equalsIgnoreCase(name)){
                return 1;
            }
            if("����ί���տ�͸֧".equalsIgnoreCase(name)){
                return 2;
            }
            if("����Ϣ͸֧".equalsIgnoreCase(name)){
                return 3;
            }
            return -1;
        }
        
    }   
    
    /**
     * �ʽ���������
     * @author yqwu
     */
    public static class CapitalType
    {
        public static final int IRRESPECTIVE = 0; //�޹�

        public static final int INTERNAL = 1; //�ڲ�ת��

        public static final int BANK = 2; //����
        
        public static final int GENERALLEDGER = 3; //������

        public static final String getName(long lCode)
        {
            String strReturn = "";
            switch ((int) lCode)
            {
                case IRRESPECTIVE:
                    strReturn = "�޹�";
                    break;
                case INTERNAL:
                    strReturn = "�ڲ�ת��";
                    break;
                case BANK:
                    strReturn = "����";
                    break;
                case GENERALLEDGER:
                    strReturn = "������";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("�޹�".equalsIgnoreCase(name)){
                return 0;
            }
            if("�ڲ�ת��".equalsIgnoreCase(name)){
                return 1;
            }
            if("����".equalsIgnoreCase(name)){
                return 2;
            }
            if("������".equalsIgnoreCase(name)){
                return 3;
            }
            return -1;
        }
    } 
    
    /**
     * �Ż���
     */
    public static class PayOrReturn
    {
        public static final long PAY = 1; //�ſ�

        public static final long RETURN = 2; //����

        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) PAY:
                    strReturn = "�ſ�";
                    break;
                case (int) RETURN:
                    strReturn = "����";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("�ſ�".equalsIgnoreCase(name)){
                return 1;
            }
            if("����".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    }
    
    /**
     * ������������
     */
	public static class ChargeRatePayType
	{
		public static final long ONETIME = 1;
		public static final long YEAR = 2;
		public static final long QUARTER = 3;

		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) ONETIME :
					strReturn = "һ����";
					break;
				case (int) YEAR :
					strReturn = "����";
					break;
				case (int) QUARTER :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("һ����".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return 3;
		    }
		    return -1;
		}
	}
	
	/**
	 * ��������
	 */
	public static class VentureLevel
	{
		//��������
		public static final long A = 1; //"����";
		public static final long B = 2; //"��ע";
		public static final long C = 3; //"�μ�";
		public static final long D = 4; //"����";
		public static final long E = 5; //"��ʧ";
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) A :
					strReturn = "����";
					break;
				case (int) B :
					strReturn = "��ע";
					break;
				case (int) C :
					strReturn = "�μ�";
					break;
				case (int) D :
					strReturn = "����";
					break;
				case (int) E :
					strReturn = "��ʧ";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("����".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("��ע".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("�μ�".equalsIgnoreCase(name)){
		        return 3;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return 4;
		    }
		    if("��ʧ".equalsIgnoreCase(name)){
		        return 5;
		    }
		    return -1;
		}
	}
	
	/**
	 * ��������1
	 */
	public static class AssureType1
	{
	    //��������1
	    public static final long FINANCING = 1; //���ʵ���
		public static final long NONFINANCING = 2; //�����ʵ���
		public static final long OTHER = 3; //����		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) FINANCING :
					strReturn = "���ʵ���";
					break;
				case (int) NONFINANCING :
					strReturn = "�����ʵ���";
					break;
				case (int) OTHER :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("���ʵ���".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("�����ʵ���".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return 3;
		    }
		    return -1;
		}
	}	
	
	/**
	 * ��������2
	 */
	public static class AssureType2
	{
	    //��������2
	    public static final long LOAN = 1; //�����
		public static final long HOMELAND = 2; //ó�����µĹ��ڵ���
		public static final long OVERSEAS = 3; //ó�����µĹ��ⵣ��
		public static final long TENDER = 4; //��Ͷ�굣��
		public static final long PERFORM = 5; //��Լ����
		public static final long IMPAWN = 6; //�ʱ�
		public static final long PREPAYMENT = 7; //Ԥ�����
		public static final long OTHER = 8; //����
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) LOAN :
					strReturn = "�����";
					break;
				case (int) HOMELAND :
					strReturn = "ó�����µĹ��ڵ���";
					break;
				case (int) OVERSEAS :
					strReturn = "ó�����µĹ��ⵣ��";
					break;
				case (int) TENDER :
					strReturn = "��Ͷ�굣��";
					break;
				case (int) PERFORM :
					strReturn = "��Լ����";
					break;
				case (int) IMPAWN :
					strReturn = "�ʱ�";
					break;
				case (int) PREPAYMENT :
					strReturn = "Ԥ�����";
					break;
				case (int) OTHER :
					strReturn = "����";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("�����".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("ó�����µĹ��ڵ���".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("ó�����µĹ��ⵣ��".equalsIgnoreCase(name)){
		        return 3;
		    }
		    if("��Ͷ�굣��".equalsIgnoreCase(name)){
		        return 4;
		    }
		    if("��Լ����".equalsIgnoreCase(name)){
		        return 5;
		    }
		    if("�ʱ�".equalsIgnoreCase(name)){
		        return 6;
		    }
		    if("Ԥ�����".equalsIgnoreCase(name)){
		        return 7;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return 8;
		    }
		    return -1;
		}
	}
	
	/**
	 * ��������
	 */
	public static class LoanType   
	{
 		//��������
		public static final long ZY = 1; //��Ӫ����
		public static final long WT = 2; //ί�д���
		public static final long TX = 3; //����
		public static final long ZGXE = 4; //����޶�
		public static final long YT = 5; //���Ŵ���
		public static final long ZTX = 6; //ת����
		public static final long MFXD = 7; //���Ŵ�
		public static final long DB = 8; //����
		public static final long OTHER = 9; //����
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) ZY :
					strReturn = "��Ӫ����";
					break;
				case (int) WT :
					strReturn = "ί�д���";
					break;
				case (int) TX :
					strReturn = "����";
					break;
				case (int) ZGXE :
					strReturn = "����޶����";
					break;
				case (int) YT :
					strReturn = "���Ŵ���";
					break;
				case (int) ZTX :
					strReturn = "ת����";
					break;
				case (int) MFXD :
					strReturn = "���Ŵ�";
					break;
				case (int) DB :
					strReturn = "����";
					break;
				case (int) OTHER :
					strReturn = "����";
					break;
			}
			return strReturn;
		}

		public static final long getValue(String name){
		    if("��Ӫ����".equalsIgnoreCase(name)){
		        return ZY;
		    }
		    if("ί�д���".equalsIgnoreCase(name)){
		        return WT;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return TX;
		    }
		    if("����޶����".equalsIgnoreCase(name)){
		        return ZGXE;
		    }
		    if("���Ŵ���".equalsIgnoreCase(name)){
		        return YT;
		    }
		    if("ת����".equalsIgnoreCase(name)){
		        return ZTX;
		    }
		    if("���Ŵ�".equalsIgnoreCase(name)){
		        return MFXD;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return DB;
		    }
		    if("����".equalsIgnoreCase(name)){
		        return OTHER;
		    }
		    return -1;
		}
	}
	
	/**
	 * ��Ʊ����
	 */
	public static class DraftType
	{
		//��Ʊ����
		public static final long BANK = 1; //���гжһ�Ʊ
		public static final long BIZ = 2; //��ҵ�жһ�Ʊ
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) BANK :
					strReturn = "���гжһ�Ʊ";
					break;
				case (int) BIZ :
					strReturn = "��ҵ�жһ�Ʊ";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("���гжһ�Ʊ".equals(name)){
		        return 1;
		    }
		    if("��ҵ�жһ�Ʊ".equals(name)){
		        return 2;
		    }
		    return -1;
		}
	}	
	
	/**
	 * ������ֲ��������
	 */
	public static class DataConvertErrorType
	{
		public static final long STRINGTRANSERR = 1; //�ַ���ת������
		public static final long NUMBERTRANSERR = 2; //��ֵת������
		public static final long DATETRANSERR=3;//����ת������
		public static final long NULLERR=4;//�ǿմ���
		public static final long NULLLINKERR=5;//�ǿ������ж�����
		public static final long IMPORTERR=11;//���ݵ��뵽��ʽ�����
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //��ʼ������ֵ
			switch ((int) lCode)
			{
				case (int) STRINGTRANSERR :
					strReturn = "�ַ���ת������";
					break;
				case (int) NUMBERTRANSERR :
					strReturn = "��ֵת������";
					break;
				case (int) DATETRANSERR :
					strReturn = "����ת������";
					break;
				case (int) NULLERR :
					strReturn = "�ǿմ���";
					break;			
				case (int) NULLLINKERR :
					strReturn = "�ǿ������ж�����";
					break;		
				case (int) IMPORTERR :
					strReturn = "���ݵ��뵽��ʽ�����";
					break;	
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("�ַ���ת������".equals(name)){
		        return 1;
		    }
		    if("��ֵת������".equals(name)){
		        return 2;
		    }
		    if("����ת������".equals(name)){
		        return 3;
		    }
		    if("�ǿմ���".equals(name)){
		        return 4;
		    }
		    if("�ǿ������ж�����".equals(name)){
		        return 5;
		    }
		    if("���ݵ��뵽��ʽ�����".equals(name)){
		        return 11;
		    }
		    return -1;
		}
	}
	
}