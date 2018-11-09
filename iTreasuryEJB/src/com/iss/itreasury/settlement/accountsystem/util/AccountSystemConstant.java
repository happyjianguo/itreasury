package com.iss.itreasury.settlement.accountsystem.util;

public class AccountSystemConstant {

	public static class SettingStatus {
		public static final long DELETED = 0; //��ɾ��
		public static final long SAVE = 1; //�ѱ���
		public static final long CHECKED = 2 ; //�����
		public static final long DELETING = 3; //ɾ�������
		public static final long MODIFING = 4; //�޸Ĵ����
		
        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) DELETED:
                    strReturn = "��ɾ��";
                    break;
                case (int) SAVE:
                    strReturn = "�ѱ���";
                    break;
                case (int) CHECKED:
                    strReturn = "�����";
                    break;
                case (int) DELETING:
                    strReturn = "ɾ�������";
                    break;
                case (int) MODIFING:
                    strReturn = "�޸Ĵ����";
                    break;
            }
            return strReturn;
        }
	}
	
	public static class RelationStatus {
		public static final long DELETED = 0; //��ɾ��
		public static final long SAVE = 1; //�ѱ���
		public static final long CHECKED = 2 ; //�����
		public static final long DELETING = 3; //ɾ�������
		
        public static final String getName(long lCode)
        {
            String strReturn = ""; //��ʼ������ֵ
            switch ((int) lCode)
            {
                case (int) DELETED:
                    strReturn = "��ɾ��";
                    break;
                case (int) SAVE:
                    strReturn = "�ѱ���";
                    break;
                case (int) CHECKED:
                    strReturn = "�����";
                    break;
                case (int) DELETING:
                    strReturn = "ɾ�������";
                    break;
            }
            return strReturn;
        }
	}
	
	
	public static class Actions {
		public static final long SAVE = 1; //����
		public static final long MODIFY = 2; //�޸�
		public static final long DELETE = 3; //ɾ��
		public static final long CHECK = 4; //���
		public static final long QUERY = 5; //��ѯ
		public static final long DETAILS = 6; //��ϸ����
		public static final long NEXT = 7; //��һ��
		public static final long BATCHCHECK = 8; //�������
		public static final long CAPITALCHANGE = 9; //�ʽ�䶯
		public static final long UNCAPITALCHANGE = 10; //�ʽ�䶯
		
	}
}
