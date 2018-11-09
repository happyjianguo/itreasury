/**
 * 
 */
package com.iss.itreasury.settlement.mergedVoucher.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.settlement.mergedVoucher.dao.Sett_mergedVouchersDAO;
import com.iss.itreasury.settlement.mergedVoucher.dataentity.MergedVoucherInfo;
import com.iss.itreasury.settlement.setting.dao.AccountDeadLineDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountDeadLineInfo;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;

/** 
 * @author zyzhu
 * 
 */
public class Sett_mergedVouchersBiz {

	// ��Ա����
	//private Connection conn = null; // ���ݿ�����

	// ���캯��
	public Sett_mergedVouchersBiz() {
//		try {
//			conn = Database.getConnection();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// add();
	//	
	// update(info); info.setStatsusid(0);
	//	
	// findByCondition(info);
	// findById();

	// ����һ����¼
	public long add(MergedVoucherInfo form) throws RemoteException, IException {
		long result = -1;
		// ��ʼ����
		try {
			
			// �½��������ݿ��DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
			//����һ����¼
			result = dao.add(form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}

		// ���غ���ֵ
		return result;
	}

	//ͨ�����κŵõ���������
	public Collection findBySbatchno(String sbatchno) throws RemoteException, IException {

		//�������
		Collection col = null;
		
		// �½��������ݿ��DAO
		Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
		try {
			col = dao.findBySbatchno(sbatchno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		
		//���غ���ֵ
		return col;

	}

	// �������κ�ɾ����Ӧ�ļ�¼������ɾ����
	public void delBySbatchno(String sbatchno) throws RemoteException, IException {
		// ��ʼ����
		try {

//			 �½��������ݿ��DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
			dao.delBySbatchno(sbatchno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
	}

	
//	 ��ѯ�Ѿ��ϲ������κ�ͨ����ѯ����������,����PageLoader
	public PageLoader findByCondition(MergedVoucherInfo form) throws RemoteException, IException {

		// �������
		PageLoader loader = null;

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();

			// ��ȡ˵�м�¼
			loader = dao.findByAll(form);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}

		return loader;
	}
	
//	 ��ѯ�Ѿ��ϲ������κ�ͨ����ѯ���������ң����ؼ��� 
	public Collection findAllByMergedInfo(MergedVoucherInfo form) throws RemoteException, IException{
		
		// �������
		Collection findAllByInfoColl = null;
		
		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
			
			// ��ȡ˵�м�¼
			findAllByInfoColl = dao.findAllByMergedInfo(form);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		return findAllByInfoColl;
	}
	
/**	// ��ȡҪ�޸ĵļ�¼
	public AccountDeadLineInfo findById(long id)  throws RemoteException, IException {

		// �������
		AccountDeadLineInfo adli = null;

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			AccountDeadLineDAO dao = new AccountDeadLineDAO();

			// ��ȡҪ�༭������
			adli = (AccountDeadLineInfo) dao.findByID(id, (new AccountDeadLineInfo()).getClass());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ���غ���ֵ
		return adli;
	}

	// �޸�ָ���ļ�¼
	public boolean update(AccountDeadLineInfo form) {

		// ��ʼ����
		try {
			// �½��������ݿ��DAO
			AccountDeadLineDAO dao = new AccountDeadLineDAO();

			// ��������
			dao.update(form);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// ���غ���ֵ
		return true;
	}**/

}
