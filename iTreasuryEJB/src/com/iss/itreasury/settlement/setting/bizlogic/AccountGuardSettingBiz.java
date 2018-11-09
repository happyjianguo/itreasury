/**
 * 
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import com.iss.itreasury.settlement.setting.dao.Sett_AccountGuardSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountGuardSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

/**
 * @author zyzhu
 * 
 */
public class AccountGuardSettingBiz {

	// ��Ա����
	private Connection conn = null; // ���ݿ�����

	// ���캯��
	public AccountGuardSettingBiz() {
		try {
			conn = Database.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ͨ���˺�Id��ѯ��¼
	public Collection findByAccountId(long lAccountId) throws SQLException {

		// �������
		Collection col = null;

		// �½��������ݿ��DAO
		Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();
		col = dao.findByAccountId(lAccountId);

		// ���غ���ֵ
		return col;

	}

	// ����һ����¼
	public boolean add(AccountGuardSettingInfo form) {

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();
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
	public Collection findByCondition(AccountGuardSettingInfo form) {

		// �������
		Collection colList = null;

		// ��ʼ����
		try {

			// �½���ѯ�����ı�
			
			form.setStatusId(Constant.RecordStatus.VALID);
			// �½��������ݿ��DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();

			// ��ȡ˵�м�¼
			colList = dao.findByAll(form);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return colList;
	}

	// ��ȡҪ�޸ĵļ�¼
	public AccountGuardSettingInfo findById(long id) {

		// �������
		AccountGuardSettingInfo adli = null;

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();

			// ��ȡҪ�༭������
			adli = (AccountGuardSettingInfo) dao.findByID(id, (new AccountGuardSettingInfo())
					.getClass());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ���غ���ֵ
		return adli;
	}

	// �޸�ָ���ļ�¼
	public boolean update(AccountGuardSettingInfo form) {

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();

			// ��������
			dao.update(form);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// ���غ���ֵ
		return true;
	}

}
