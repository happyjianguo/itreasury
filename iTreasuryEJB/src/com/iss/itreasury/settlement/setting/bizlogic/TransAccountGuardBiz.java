package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dao.Sett_AccountGuardSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_TransAccountGuardDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountGuardSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.TransAccountGuardInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

public class TransAccountGuardBiz {

	// ��Ա����
	private Connection conn = null; // ���ݿ�����

	// ���캯��
	public TransAccountGuardBiz() {
		try {
			conn = Database.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// add();
	//	
	// update(info); info.setStatsusid(0);
	//	
	// findByCondition(info);
	// findById();

	// ����һ����¼
	public boolean add(TransAccountGuardInfo form) {

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();
			// ����һ����¼
			dao.add(form);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// ���غ���ֵ
		return true;
	}

	// ��ȡ�˻��޶����ü�¼�б�
	public Collection findByCondition() {

		// �������
		Collection colList = null;

		// ��ʼ����
		try {

			// �½���ѯ�����ı�
			TransAccountGuardInfo form = new TransAccountGuardInfo();
			form.setStatusId((int) Constant.RecordStatus.VALID);

			// �½��������ݿ��DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();

			// ��ȡ˵�м�¼
			colList = dao.findByCondition(form, " order by id ");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return colList;
	}

	// ��ȡҪ�޸ĵļ�¼
	public TransAccountGuardInfo findById(long id) {

		// �������
		TransAccountGuardInfo adli = null;

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();

			// ��ȡҪ�༭������
			adli = (TransAccountGuardInfo) dao.findByID(id, (new TransAccountGuardInfo())
					.getClass());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ���غ���ֵ
		return adli;
	}

	// �޸�ָ���ļ�¼
	public boolean update(TransAccountGuardInfo form) {

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();

			// ��������
			dao.update(form);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// ���غ���ֵ
		return true;
	}

	/*
	 * У�鸶���
	 * 
	 * ����ֵ�� 1 У��ͨ����0 �������д��� ��-1 �Ǽ���˻��� -2 ����˻�����������֧������� -3 ����˻������������ڸ����ܶ
	 * 
	 */
	public long checkTransAccountGuardInfo(TransAccountGuardInfo form) {

		// �������
		long bResult = 0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		try {
			// ��ѯ���˻��ļ��������Ϣ
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // �½���ѯDAO
			Collection col = AccountGuardDAO.findByAccountId(form.getPayAccountId()); // ��ѯ���ݱ�

			// �Ƿ��Ǽ���˻�
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// ������˻����ڼ�������У���ȡ���������Ϣ
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();

				// ��ȡ���˻����θ��������ڸ����ܶ�
				double lPayCurrent = form.getAmount(); // ���θ����
				double dbTransPayLimited = accountGuardSettingInfo.getTransPayLimited();	//���ʽ����޶�

				// У�鱾�θ����
				if (dbTransPayLimited > 0 && lPayCurrent >= dbTransPayLimited) {
					bResult = -2; // ���θ������޶�
				}
				Log.print("�˻�(" + form.getPayAccountId() + ")�������:" + lPayCurrent);
				Log.print("�˻�(" + form.getPayAccountId() + ")���ʽ����޶���:"
						+ accountGuardSettingInfo.getTransPayLimited());

				// У�������ڸ����ܶ�
				double dbSumLimited = accountGuardSettingInfo.getSumLimited();	//�ۼƸ����޶�
				if (bResult != -2 && dbSumLimited > 0) {

					// ͳ�Ƹ��˻������ڵĸ����ܶ�
					double lSumPay = getSumPayInPeriods(form, accountGuardSettingInfo
							.getValidDate(), accountGuardSettingInfo.getStatDays());
					Log.print("�˻�(" + form.getPayAccountId() + ")�����ڸ������(�������θ����):" + lSumPay);
					Log.print("�˻�(" + form.getPayAccountId() + ")�����ڸ����޶���:"
							+ accountGuardSettingInfo.getSumLimited());
					if (lSumPay >= dbSumLimited) {
						bResult = -3; // �����ڸ������޶�
					} else {
						bResult = 1; // У��ͨ��
					}

				}else if(bResult != -2){
					bResult = 1; // У��ͨ��
				}

			} else {
				bResult = -1; // �Ǽ���˻�
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return bResult;
		}

		// ���غ���ֵ
		return bResult;

	}

	// ͳ���˻������ڸ����ܶ�������Σ��������ݣ��༭���ݣ�
	private double getSumPayInPeriods(TransAccountGuardInfo form, Timestamp tmValidDate,
			long lStatDays) throws SQLException {

		// �������
		double dbResult = -1; // �����ܶ�
		Timestamp dtExecuteDate = form.getExecuteDate();
		Timestamp dtStart = null; // ��ʼ����
		Timestamp dtEnd = null; // ��������

		// ���ͳ����Ч������ҵ������֮������Ҫ����������¼
		if (tmValidDate.after(dtExecuteDate)) {
			dbResult = 0.0;
		} else {

			// ����ҵ������ʱ��������еĸ������ܺ�(������ǰ���)

			// ����ҵ�����������ĸ�ʱ���
			Hashtable hsbDateSection = DataFormat.getDateSection(tmValidDate, lStatDays,
					dtExecuteDate);
			dtStart = (Timestamp) hsbDateSection.get("Start");
			dtEnd = (Timestamp) hsbDateSection.get("End");

			// ������ѯ���ݿ��DAO����
			Sett_TransAccountGuardDAO transAccountGuardDAO = new Sett_TransAccountGuardDAO();

			// ͳ�Ʒ����������˺Ÿ�����֮��
			dbResult = transAccountGuardDAO.sumPayInPeriods(form.getId(),form.getPayAccountId(), dtStart, dtEnd);

			// ���ϱ���֧�����
			dbResult += form.getAmount();

		}
		Log.print("�˻�(" + form.getPayAccountId() + ")�����ڸ����޶����Ч��ʼ������:"
				+ DataFormat.getDateString(tmValidDate));
		Log.print("�˻�(" + form.getPayAccountId() + ")�����������:"
				+ DataFormat.getDateString(dtExecuteDate));
		Log.print("�˻�(" + form.getPayAccountId() + ")������������ڵ�����������:("
				+ DataFormat.getDateString(dtStart) + "��" + DataFormat.getDateString(dtEnd) + ")");

		// ���غ���ֵ
		return dbResult;
	}

	// ͳ���˻������ڸ����ܶ�(����������)
	public double getSumPayInPeriods(TransAccountGuardInfo form) {

		// �������
		double dbResult = 0.0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		try {

			// ͨ���˻�ID��ѯ�˻������Ϣ
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // �½���ѯDAO
			Collection col = null;
			col = AccountGuardDAO.findByAccountId(form.getPayAccountId());// ��ѯ���ݱ�

			// �Ƿ��Ǽ���˻�
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// ������˻����ڼ�������У���ȡ���������Ϣ
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();
				Timestamp tmValidDate = accountGuardSettingInfo.getValidDate();
				long lStatDays = accountGuardSettingInfo.getStatDays();

				// ȷ������ʱ���
				Timestamp dtExecuteDate = form.getExecuteDate();
				Hashtable hsbDateSection = DataFormat.getDateSection(tmValidDate, lStatDays,
						dtExecuteDate);
				Timestamp dtStart = (Timestamp) hsbDateSection.get("Start");// ��ʼ����
				Timestamp dtEnd = (Timestamp) hsbDateSection.get("End");// ��������

				// ������ѯ���ݿ��DAO����
				Sett_TransAccountGuardDAO transAccountGuardDAO = new Sett_TransAccountGuardDAO();

				// ͳ�Ʒ����������˺Ÿ�����֮��
				dbResult = transAccountGuardDAO.sumPayInPeriods(form.getId(),form.getPayAccountId(), dtStart,
						dtEnd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// ���غ���ֵ
		return dbResult;

	}

	// ͨ���˻�ID��ѯ�˻����ʽ��׽������
	public static double getTransPayLimitedByAccountId(long lAccountId) {

		// �������
		double dbResult = 0.0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		// ��ʼ����
		try {

			// ��ѯ���˻��ļ��������Ϣ
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // �½���ѯDAO
			Collection col = AccountGuardDAO.findByAccountId(lAccountId); // ��ѯ���ݱ�

			// �Ƿ��Ǽ���˻�
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// ������˻����ڼ�������У���ȡ���������Ϣ
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();
				dbResult = accountGuardSettingInfo.getTransPayLimited();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// ���غ���ֵ
		return dbResult;

	}

	// ͨ���˻�ID��ѯ�˻������ڸ����ܶ�����
	public static double getSumLimitedByAccountId(long lAccountId) {

		// �������
		double dbResult = 0.0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		// ��ʼ����
		try {

			// ��ѯ���˻��ļ��������Ϣ
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // �½���ѯDAO
			Collection col = AccountGuardDAO.findByAccountId(lAccountId); // ��ѯ���ݱ�

			// �Ƿ��Ǽ���˻�
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// ������˻����ڼ�������У���ȡ���������Ϣ
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();
				dbResult = accountGuardSettingInfo.getSumLimited();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// ���غ���ֵ
		return dbResult;
	}

	// ���Ժ���
	public static void main(String[] args) {

		System.out.println("�����Ե�ֵ��:");
		Double dbTmp = null;
		getDouble(dbTmp);
		System.out.println(dbTmp.doubleValue());

	}

	//
	public static void getDouble(Double dbTmp) {
		dbTmp = new Double(1.2);
		System.out.println(dbTmp.doubleValue());
	}

}
