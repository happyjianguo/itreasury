
package com.iss.itreasury.audit.util;

import com.iss.itreasury.util.Constant;

public class AUDITConstant extends com.iss.itreasury.util.Constant
{
	/**
	 * �������ר��
	 * @author zcwang
	 *
	 */
	public static class AmountTypeForAudit {
		public static final int AmountType_1 = 1; // ����

		public static final int AmountType_2 = 2; // ��Ϣ֧�� add by xfma ���Ӧ���ǡ���Ϣ֧����

		public static final int AmountType_3 = 3; // Ӧ����Ϣ add by xfma ���Ӧ���ǡ�Ӧ����Ϣ��
		
		public static final int AmountType_4 = 4; // ������ ����ʹ��
		
		public static final int AmountType_5 = 5; // ��Ϣ˰�� ����ʹ��
		
		public static final int AmountType_6 = 6; // ��Ϣ���� add by xfma ����Ӧ���ǡ���Ϣ���롱

		public static final int AmountType_7 = 7; // Ӧ����Ϣ add by xfma ����Ӧ���ǡ�Ӧ����Ϣ��

		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case AmountType_1:
				strReturn = "����";
				break;
			case AmountType_2:
				strReturn = "��Ϣ֧��";
				break;
			case AmountType_3:
				strReturn = "Ӧ����Ϣ";
				break;
			case AmountType_4:
				strReturn = "������";
				break;
			case AmountType_5:
				strReturn = "��Ϣ˰��";
				break;
			case AmountType_6://add by xfma ����Ӧ���ǡ���Ϣ���롱
				strReturn = "��Ϣ����";
				break;
			case AmountType_7://add by xfma ����Ӧ���ǡ�Ӧ����Ϣ��
				strReturn = "Ӧ����Ϣ";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AmountType_1, AmountType_2, AmountType_3, AmountType_4, AmountType_5
					, AmountType_6, AmountType_7};
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.audit.util.AUDITConstant$AmountTypeForAudit",
							officeID, currencyID);
		}
		public static final long[] getDepositCode() {
			long[] lTemp = { AmountType_1, AmountType_2, AmountType_3
					 };
			return lTemp;
		}
		public static final long[] getLoanCode() {
			long[] lTemp = { AmountType_1, AmountType_6, AmountType_7, AmountType_4, AmountType_5
					 };
			return lTemp;
		}

	}
	/**
	 * �������ר��
	 * @author zcwang
	 *
	 */
	public static class AmountShowTypeForAudit {
		public static final int AmountShowType_1 = 1; // �ڳ����

		public static final int AmountShowType_2 = 2; // �跽������

		public static final int AmountShowType_3 = 3; // ����������

		public static final int AmountShowType_4 = 4; // ��ĩ���
		
		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case AmountShowType_1:
				strReturn = "�ڳ����";
				break;
			case AmountShowType_2:
				strReturn = "�跽������";
				break;
			case AmountShowType_3:
				strReturn = "����������";
				break;
			case AmountShowType_4:
				strReturn = "��ĩ���";
				break;
			
			}
			return strReturn;
		}

		public static final long[] getAllCode() {
			long[] lTemp = { AmountShowType_1, AmountShowType_2, AmountShowType_3,AmountShowType_4
					 };
			return lTemp;
		}

		public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.audit.util.AUDITConstant$AmountShowTypeForAudit",
							officeID, currencyID);
		}

	}
}