package com.iss.itreasury.settlement.generalledger.dao;

import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;

/**
 * �˻���ѯ���ݲ�
 * 
 * @author xiang
 * 
 */
public class QGLSubjectDefinitionDao {

	public String queryLoanSQL(GLSubjectDefinitionInfo condition) {
		StringBuffer buffer = new StringBuffer(64);
		buffer.append("select \n");
		buffer.append("ID ID,\n");
		buffer.append("nOfficeID,\n");
		buffer.append("nvl(sSegmentCode1,'')||''|| nvl(sSegmentCode2,'')||''||nvl(sSegmentCode3,'')||''||nvl(sSegmentCode4,'')||''||nvl(sSegmentCode5,'')||''||nvl(sSegmentCode6,'')||''||nvl(sSegmentCode7,'')  ID1,\n");
		buffer.append("nCurrencyID,\n");
		buffer.append("sSegmentCode1,\n");
		buffer.append("sSegmentCode2,\n");
		buffer.append("sSegmentCode3,\n");
		buffer.append("sSegmentCode4,\n");
		buffer.append("sSegmentCode5,\n");
		buffer.append("sSegmentCode6,\n");
		buffer.append("sSegmentCode7,\n");
		buffer.append("sSegmentName1,\n");
		buffer.append("sSegmentName2,\n");
		buffer.append("sSegmentName3,\n");
		buffer.append("sSegmentName4,\n");
		buffer.append("nSubjectType,\n");
		buffer.append("case nIsleaf when 1 then '��' else '��' end as nIsleaf,\n");
		buffer.append("nIsRoot,\n");
		buffer.append("nParentSubjectID,\n");
		buffer.append("nBalanceDirection,\n");
		buffer.append("nAmountDirection,\n");
		buffer.append("nStatus,\n");
		buffer.append("nLederType,\n");
		buffer.append("nSecurityLevel,\n");
		buffer.append("nUseScope,\n");
		buffer.append("nFlag,\n");
		buffer.append("dtValidDate \n");
		buffer.append("from SETT_VGLSUBJECTDEFINITION where 1=1");

		
		
		
		// ���´�
		if (condition.getOfficeID() > 0) {
			buffer.append(" and nOfficeID =").append(condition.getOfficeID());
		}
		// ����
		if (condition.getCurrencyID() > 0) {
			buffer.append(" and nCurrencyID =").append(condition.getCurrencyID());
		}

//		// ��Ŀ����
//		if (condition.getSegmentCode2() != null
//				&& !condition.getSegmentCode2().equals("")) {
//			buffer.append(" and SSUBJECTCODE like ?");
//		}
		
		//��Ŀ���� ��һ��
		if (condition.getSegmentCode1() != null
				&& !condition.getSegmentCode1().equals("")) {
			buffer.append(" and SSEGMENTCODE1 like ").append("'%" + condition.getSegmentCode1()
						+ "%'");
		}
		
		//��Ŀ���� �ڶ���
		if (condition.getSegmentCode2() != null
				&& !condition.getSegmentCode2().equals("")) {
			buffer.append(" and SSEGMENTCODE2 like ").append("'%" + condition.getSegmentCode2()
					+ "%'");
		}

		//��Ŀ���� ������
		if (condition.getSegmentCode3() != null
				&& !condition.getSegmentCode3().equals("")) {
			buffer.append(" and SSEGMENTCODE3 like ").append("'%" + condition.getSegmentCode3()
					+ "%'");
		}

		//��Ŀ���� ���Ķ�
		if (condition.getSegmentCode4() != null
				&& !condition.getSegmentCode4().equals("")) {
			buffer.append(" and SSEGMENTCODE4 like ").append("'%" + condition.getSegmentCode4()
					+ "%'");
		}

		//��Ŀ���� �����
		if (condition.getSegmentCode5() != null
				&& !condition.getSegmentCode5().equals("")) {
			buffer.append(" and SSEGMENTCODE5 like ").append("'%" + condition.getSegmentCode5()
					+ "%'");
		}

		//��Ŀ���� ������
		if (condition.getSegmentCode6() != null
				&& !condition.getSegmentCode6().equals("")) {
			buffer.append(" and SSEGMENTCODE6 like ").append("'%" + condition.getSegmentCode6()
					+ "%'");
		}

		//��Ŀ���� ���߶�
		if (condition.getSegmentCode7() != null
				&& !condition.getSegmentCode7().equals("")) {
			buffer.append(" and SSEGMENTCODE7 like ").append("'%" + condition.getSegmentCode7()
					+ "%'");
		}

		// ��Ŀ����
		if (condition.getSegmentName2() != null
				&& !condition.getSegmentName2().equals("")) {
			buffer.append(" and sSubjectName like ").append("'%" + condition.getSegmentName2()
					+ "%'");
		}

		// ��Ŀ����ID
		if (condition.getSubjectType() > 0) {
			buffer.append(" and nSubjectType=").append(condition.getSubjectType());
		}

		// ����
		if (condition.getBalanceDirection() > 0) {
			buffer.append(" and NBALANCEDIRECTION=").append(condition.getBalanceDirection());
		}

		// �������
		if (condition.getAmountDirection() > 0) {
			buffer.append(" and NAMOUNTDIRECTION=").append(condition.getAmountDirection());
		}
		
		return buffer.toString();
	}

}
