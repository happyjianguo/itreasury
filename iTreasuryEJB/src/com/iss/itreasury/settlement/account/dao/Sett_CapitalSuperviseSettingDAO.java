/*
 * Created on 2004-4-9
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.CapitalSuperviseSettingInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author ruixie
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_CapitalSuperviseSettingDAO extends ITreasuryDAO {

	protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * ����Ǹ���ķ�������ά������Ӧ�ô�����ȡ�����ݿ����ӻ���ֱ��ͨ��JDBC���ʣ�ȱʡΪ������ȡ�á�
	 */
	private boolean bConnectionFromContainer = true;

	public Sett_CapitalSuperviseSettingDAO() {
		super("sett_CapitalSuperviseSetting");
	}

	/**
	 * �����ϼ���λ�ͻ�ID �����������õ� ��Ա��λ����Ϣ
	 * 
	 * @param ID
	 * @return @throws
	 *         Exception
	 */
	public Collection findBySuperClientID(long lSuperClientID, long lOfficeID, long lCurrencyID)
			throws Exception {
		try {
			initDAO();

			String strSQL = "select * from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ lOfficeID + " and CurrencyID = " + lCurrencyID
					+ " and SuperClientID = " + lSuperClientID + " and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE
					+ " order by SuperClientID,SuperAccountID,ChildAccountID";
			CapitalSuperviseSettingInfo capitalSuperviseSettingInfo = new CapitalSuperviseSettingInfo();
			log4j.print("find by SuperClientID��" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(capitalSuperviseSettingInfo
					.getClass());

			finalizeDAO();

			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ϼ���λ�ͻ�ID �����������õ��ϼ��˻�����Ϣ
	 * 
	 * @param ID
	 * @return @throws
	 *         Exception
	 */
	public Collection findSuperBySuperClientID(long lSuperClientID, long lOfficeID,
			long lCurrencyID) throws Exception {
		try {
			initDAO();

			String strSQL = "select distinct SUPERCLIENTID,SUPERACCOUNTID,DEBITINTERESTACCOUNTID,StatusID,OfficeID,CurrencyID from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ lOfficeID
					+ " and CurrencyID = "
					+ lCurrencyID
					+ " and SuperClientID = "
					+ lSuperClientID
					+ " and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE
					+ " order by SuperAccountID,DEBITINTERESTACCOUNTID";
			CapitalSuperviseSettingInfo capitalSuperviseSettingInfo = new CapitalSuperviseSettingInfo();
			log4j.print("findSuperBySuperClientID��" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(capitalSuperviseSettingInfo
					.getClass());

			finalizeDAO();

			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ϼ���λ�ͻ�ID �����������õ��¼��˻�����Ϣ
	 * 
	 * @param ID
	 * @return @throws
	 *         Exception
	 */
	public Collection findChildBySuperClientID(long lSuperClientID, long lOfficeID,
			long lCurrencyID) throws Exception {
		try {
			initDAO();

			String strSQL = "select distinct SUPERCLIENTID,CHILDACCOUNTID,StatusID,OfficeID,CurrencyID from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ lOfficeID
					+ " and CurrencyID = "
					+ lCurrencyID
					+ " and SuperClientID = "
					+ lSuperClientID
					+ " and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE + " order by ChildAccountID";
			CapitalSuperviseSettingInfo capitalSuperviseSettingInfo = new CapitalSuperviseSettingInfo();
			log4j.print("findChildBySuperClientID��" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(capitalSuperviseSettingInfo
					.getClass());

			finalizeDAO();

			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����������õ���Ϣ
	 * 
	 * @param
	 * @return @throws
	 *         Exception
	 */
	public Collection findAll(long lOfficeID, long lCurrencyID) throws Exception {
		try {
			initDAO();

			String strSQL = "select * from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ lOfficeID + " and CurrencyID = " + lCurrencyID + " and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE
					+ " order by SuperClientID,SuperAccountID,ChildAccountID";
			CapitalSuperviseSettingInfo capitalSuperviseSettingInfo = new CapitalSuperviseSettingInfo();
			log4j.print("find all��" + strSQL);
			prepareStatement(strSQL);
			executeQuery();
			Collection c = getDataEntitiesFromResultSet(capitalSuperviseSettingInfo
					.getClass());
			c = transCollection(c);
			finalizeDAO();

			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * У�����ݵ���ȷ��
	 * 
	 * @param
	 * @return
	 */
	public String checkSuper(CapitalSuperviseSettingInfo info, Collection superColl,
			Collection childColl) throws Exception {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String errMsg = "";

		Iterator itChildResult = null;
		Iterator itSuperResult = null;

		if (superColl != null) {
			itSuperResult = superColl.iterator();
		}
		if (childColl != null) {
			itChildResult = childColl.iterator();
		}
		if (itSuperResult != null && itSuperResult.hasNext()) {
			while (itSuperResult.hasNext()) {
				CapitalSuperviseSettingInfo superInfo = (CapitalSuperviseSettingInfo) itSuperResult
						.next();
				if (superInfo.getSuperAccountID() == info.getSuperAccountID()) {
					errMsg = "�ϼ���λ�˻�����ظ������������룡";
					return errMsg;
				}
				if (superInfo.getDebitInterestAccountID() == info.getSuperAccountID()) {
					errMsg = "�ϼ���λ�˻�����븺Ϣ�˻��ظ������������룡";
					return errMsg;
				}
			}
		}
		if (itChildResult != null && itChildResult.hasNext()) {
			while (itChildResult.hasNext()) {
				CapitalSuperviseSettingInfo childInfo = (CapitalSuperviseSettingInfo) itChildResult
						.next();
				if (childInfo.getChildAccountID() == info.getSuperAccountID()) {
					errMsg = "�ϼ���λ�˻�����������˻��ظ������������룡";
					return errMsg;
				}
			}
		}
		try {
			String strSQL = "select distinct SuperClientID from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ info.getOfficeID()
					+ " and CurrencyID = "
					+ info.getCurrencyID()
					+ " and SuperClientID <> "
					+ info.getSuperClientID()
					+ " and(SUPERACCOUNTID = "
					+ info.getSuperAccountID()
					+ " or DEBITINTERESTACCOUNTID ="
					+ info.getSuperAccountID()
					+ " or CHILDACCOUNTID = "
					+ info.getSuperAccountID()
					+ " )  and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE
					+ " order by SuperClientID";
			log4j.info("*****Sql**" + strSQL);
			ps = con.prepareStatement(strSQL);

			rs = ps.executeQuery();
			if (rs.next()) {
				errMsg = "�ϼ���λ�˻�����ظ���";
				return errMsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return errMsg;

	}
	/**
	 * ���
	 * @param superColl
	 * @return
	 * @throws Exception
	 */
	public String chekcSuperBanlance(Collection superColl) throws Exception 
	{
		String errRtn = "";
		Iterator itSuperResult = null;
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		if (superColl != null) 
		{
			itSuperResult = superColl.iterator();
		}
		if(superColl != null)
		{
				itSuperResult = superColl.iterator();
		}
		if(itSuperResult != null && itSuperResult.hasNext())
		{
			while(itSuperResult.hasNext())
			{
				CapitalSuperviseSettingInfo superInfo = (CapitalSuperviseSettingInfo)itSuperResult.next();				
				if(dao.findByAccountID(superInfo.getDebitInterestAccountID()).getSubAccountCurrenctInfo().getBalance()<0 || dao.findByAccountID(superInfo.getSuperAccountID()).getSubAccountCurrenctInfo().getBalance()<0 || dao.findByAccountID(superInfo.getDebitInterestAccountID()).getSubAccountCurrenctInfo().getInterest()<0)
				{
					errRtn="�ϼ���λ�Ѿ�ռ���¼���λ�ʽ𣬲����˳��ʽ��й������飡";
					return errRtn;
				}
			
			}
		}
		return errRtn;
	}

	/**
	 * У�����ݵ���ȷ��
	 * 
	 * @param
	 * @return
	 */
	public String checkDebit(CapitalSuperviseSettingInfo info, Collection superColl,
			Collection childColl) throws Exception {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String errMsg = "";

		Iterator itChildResult = null;
		Iterator itSuperResult = null;

		if (superColl != null) {
			itSuperResult = superColl.iterator();
		}
		if (childColl != null) {
			itChildResult = childColl.iterator();
		}
		if (itSuperResult != null && itSuperResult.hasNext()) {
			while (itSuperResult.hasNext()) {
				CapitalSuperviseSettingInfo superInfo = (CapitalSuperviseSettingInfo) itSuperResult
						.next();
				if (superInfo.getSuperAccountID() == info.getDebitInterestAccountID()) {

					errMsg = "��Ϣ�˻����ϼ���λ�˻�����ظ������������룡";
					return errMsg;
				}
				if (superInfo.getDebitInterestAccountID() == info
						.getDebitInterestAccountID()) {
					errMsg = "��Ϣ�˻�����ظ������������룡";
					return errMsg;
				}
			}
		}
		if (itChildResult != null && itChildResult.hasNext()) {
			while (itChildResult.hasNext()) {
				CapitalSuperviseSettingInfo childInfo = (CapitalSuperviseSettingInfo) itChildResult
						.next();
				if (childInfo.getChildAccountID() == info.getDebitInterestAccountID()) {
					errMsg = "�ϼ���λ�˻�����������˻��ظ������������룡";
					return errMsg;
				}
			}
		}
		try {
			String strSQL = "select distinct SuperClientID from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ info.getOfficeID()
					+ " and CurrencyID = "
					+ info.getCurrencyID()
					+ " and SuperClientID <> "
					+ info.getSuperClientID()
					+ " and(SUPERACCOUNTID = "
					+ info.getDebitInterestAccountID()
					+ " or DEBITINTERESTACCOUNTID ="
					+ info.getDebitInterestAccountID()
					+ " or CHILDACCOUNTID = "
					+ info.getDebitInterestAccountID()
					+ " )  and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE
					+ " order by SuperClientID";
			log4j.info("*****Sql**" + strSQL);
			ps = con.prepareStatement(strSQL);

			rs = ps.executeQuery();
			if (rs.next()) {
				errMsg = "��Ϣ�˻�����ظ���";
				return errMsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return errMsg;

	}

	/**
	 * У�����ݵ���ȷ��
	 * 
	 * @param
	 * @return
	 */
	public String checkChild(CapitalSuperviseSettingInfo info, Collection superColl,
			Collection childColl) throws Exception {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String errMsg = "";

		Iterator itChildResult = null;
		Iterator itSuperResult = null;

		if (superColl != null) {
			itSuperResult = superColl.iterator();
		}
		if (childColl != null) {
			itChildResult = childColl.iterator();
		}
		if (itSuperResult != null && itSuperResult.hasNext()) {
			while (itSuperResult.hasNext()) {
				CapitalSuperviseSettingInfo superInfo = (CapitalSuperviseSettingInfo) itSuperResult
						.next();	
				if (superInfo.getSuperAccountID() == info.getChildAccountID()) {
					errMsg = "������λ�˻�������ϼ���λ�˻��ظ������������룡";
					return errMsg;
				}
				if (superInfo.getDebitInterestAccountID() == info.getChildAccountID()) {
					errMsg = "������λ�˻�����븺Ϣ�˻��ظ������������룡";
					return errMsg;
				}
			}
		}
		if (itChildResult != null && itChildResult.hasNext()) {
			while (itChildResult.hasNext()) {
				CapitalSuperviseSettingInfo childInfo = (CapitalSuperviseSettingInfo) itChildResult
						.next();
				if (childInfo.getChildAccountID() == info.getChildAccountID()) {
					errMsg = "������λ�˻����ظ������������룡";
					return errMsg;
				}
			}
		}
		try {
			String strSQL = "select distinct SuperClientID from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ info.getOfficeID()
					+ " and CurrencyID = "
					+ info.getCurrencyID()
					+ " and SuperClientID <> "
					+ info.getSuperClientID()
					+ " and(SUPERACCOUNTID = "
					+ info.getChildAccountID()
					+ " or DEBITINTERESTACCOUNTID ="
					+ info.getChildAccountID()
					+ " or CHILDACCOUNTID = "
					+ info.getChildAccountID()
					+ " )  and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE
					+ " order by SuperClientID";
			log4j.info("*****Sql**" + strSQL);
			ps = con.prepareStatement(strSQL);

			rs = ps.executeQuery();
			if (rs.next()) {
				errMsg = "������λ�˻�����ظ���";
				return errMsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return errMsg;

	}

	/**
	 * У�����ݵ���ȷ��
	 * 
	 * @param
	 * @return
	 */
	public long checkSuperClient(long lSuperClientID, long lOfficeID, long lCurrencyID)
			throws Exception {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long rtnFlag = -1;
		try {
			String strSQL = "select distinct SuperClientID from SETT_CAPITALSUPERVISESETTING where OfficeID = "
					+ lOfficeID
					+ " and CurrencyID = "
					+ lCurrencyID
					+ " and SuperClientID = "
					+ lSuperClientID
					+ " and StatusID = "
					+ SETTConstant.BooleanValue.ISTRUE + " order by SuperClientID";
			log4j.info("*****Sql**" + strSQL);
			ps = con.prepareStatement(strSQL);

			rs = ps.executeQuery();
			if (rs.next()) {
				rtnFlag = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return rtnFlag;

	}

	/**
	 * ����ѯ���תΪҳ����ʾ���
	 * 
	 * @return @throws
	 *         Exception
	 */
	private Collection transCollection(Collection c) {
		ArrayList rtnList = null;
		Iterator itResult = null;
		long SuperClientID = -1;//�ϼ���λ�ͻ�ID
		long SuperAccountID = -1;//�ϼ���λ�˻�ID
		long ChildAccountID = -1;//������λ�˻�ID
		String strSuperAccountNo = ""; //�ϼ���λ�˻���
		String strChildAccountNo = ""; //������λ�˻���

		if (c != null) {
			itResult = c.iterator();
		}
		if (itResult != null && itResult.hasNext()) {
			rtnList = new ArrayList();
			while (itResult.hasNext()) {
				CapitalSuperviseSettingInfo info = (CapitalSuperviseSettingInfo) itResult
						.next();

				if (info.getSuperClientID() != SuperClientID) {
					if (SuperClientID != -1) {
						CapitalSuperviseSettingInfo infoRtn = new CapitalSuperviseSettingInfo();
						infoRtn.setSuperClientID(SuperClientID);
						infoRtn.setSuperAccountNo(strSuperAccountNo);
						infoRtn.setChildAccountNo(strChildAccountNo);
						rtnList.add(infoRtn);
					}
					SuperClientID = info.getSuperClientID();
					strSuperAccountNo = "";
					if (SuperAccountID != info.getSuperAccountID()) {
						SuperAccountID = info.getSuperAccountID();
						if (strSuperAccountNo != null && !strSuperAccountNo.equals("")) {
							strSuperAccountNo = strSuperAccountNo
									+ ","
									+ NameRef.getAccountNoByID(info
											.getSuperAccountID());
						} else {
							strSuperAccountNo = NameRef.getAccountNoByID(info
									.getSuperAccountID());
						}
						strChildAccountNo = "";
						if (ChildAccountID != info.getChildAccountID()) {
							ChildAccountID = info.getChildAccountID();
							if (strChildAccountNo != null
									&& !strChildAccountNo.equals("")) {
								strChildAccountNo = strChildAccountNo
										+ ","
										+ NameRef.getAccountNoByID(info
												.getChildAccountID());
							} else {
								strChildAccountNo = NameRef.getAccountNoByID(info
										.getChildAccountID());
							}
						}
					} else {
						if (ChildAccountID != info.getChildAccountID()) {
							ChildAccountID = info.getChildAccountID();
							if (strChildAccountNo != null
									&& !strChildAccountNo.equals("")) {
								strChildAccountNo = strChildAccountNo
										+ ","
										+ NameRef.getAccountNoByID(info
												.getChildAccountID());
							} else {
								strChildAccountNo = NameRef.getAccountNoByID(info
										.getChildAccountID());
							}
						}
					}
				} else {
					if (SuperAccountID != info.getSuperAccountID()) {
						SuperAccountID = info.getSuperAccountID();
						strSuperAccountNo = strSuperAccountNo
								+ ","
								+ NameRef
										.getAccountNoByID(info
												.getSuperAccountID());
						strChildAccountNo = "";
						if (ChildAccountID != info.getChildAccountID()) {
							ChildAccountID = info.getChildAccountID();
							if (strChildAccountNo != null
									&& !strChildAccountNo.equals("")) {
								strChildAccountNo = strChildAccountNo
										+ ","
										+ NameRef.getAccountNoByID(info
												.getChildAccountID());
							} else {
								strChildAccountNo = NameRef.getAccountNoByID(info
										.getChildAccountID());
							}
						}
					} else {
						if (ChildAccountID != info.getChildAccountID()) {
							ChildAccountID = info.getChildAccountID();
							if (strChildAccountNo != null
									&& !strChildAccountNo.equals("")) {
								strChildAccountNo = strChildAccountNo
										+ ","
										+ NameRef.getAccountNoByID(info
												.getChildAccountID());
							} else {
								strChildAccountNo = NameRef.getAccountNoByID(info
										.getChildAccountID());
							}
						}
					}
				}
			}
			CapitalSuperviseSettingInfo infoRtn = new CapitalSuperviseSettingInfo();
			infoRtn.setSuperClientID(SuperClientID);
			infoRtn.setSuperAccountNo(strSuperAccountNo);
			infoRtn.setChildAccountNo(strChildAccountNo);
			rtnList.add(infoRtn);
		}
		return rtnList;
	}

	/**
	 * �����ϼ���λ�ͻ�ID �޸��������õ� ��Ա��λ��״̬��ȫ��ɾ����
	 * 
	 * @param ID
	 * @return @throws
	 *         Exception
	 */
	public long updateBySuperClientID(long lSuperClientID, long lOfficeID, long lCurrencyID,
			long lStatusID) throws Exception {
		try {
			initDAO();
			long c = -1;

			String strSQL = "update SETT_CAPITALSUPERVISESETTING set StatusID=" + lStatusID
					+ "  where OfficeID = " + lOfficeID + " and CurrencyID = "
					+ lCurrencyID + " and StatusID=" + SETTConstant.BooleanValue.ISTRUE
					+ "and SuperClientID = " + lSuperClientID;
			CapitalSuperviseSettingInfo capitalSuperviseSettingInfo = new CapitalSuperviseSettingInfo();
			log4j.print("update by SuperClientID��" + strSQL);
			prepareStatement(strSQL);
			c = executeUpdate();
			finalizeDAO();

			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * ���浱ǰ������Ϣ
	 * 
	 * @param capitalSuperviseSettingInfo
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long save(CapitalSuperviseSettingInfo info, Collection superColl, Collection childColl)
			throws Exception {
		long rtnFlag = -1;
		Iterator itChildResult = null;
		Iterator itSuperResult = null;
		Sett_SubAccountDAO subDao = new Sett_SubAccountDAO();
		if (superColl != null) {
			itSuperResult = superColl.iterator();
		} else {
			throw new IException("��������������λ�˻���ţ�");
		}
		if (childColl != null) {
			itChildResult = childColl.iterator();
		} else {
			throw new IException("������λΪ�գ����ʽ��й�����ϵδ��Ч���Ƿ���д�¼���λ�˻�����");
		}
		if (itSuperResult != null && itSuperResult.hasNext()) {
			log4j.info("--------ɾ��ԭ�м�¼-------");
			rtnFlag = updateBySuperClientID(info.getSuperClientID(), info.getOfficeID(), info
					.getCurrencyID(), SETTConstant.BooleanValue.ISFALSE);

			while (itSuperResult.hasNext()) {
				CapitalSuperviseSettingInfo superInfo = (CapitalSuperviseSettingInfo) itSuperResult
						.next();
				log4j.info("--------����������¼��ʼ-------");
				if (itChildResult != null && itChildResult.hasNext()) {
					while (itChildResult.hasNext()) {
						CapitalSuperviseSettingInfo childInfo = (CapitalSuperviseSettingInfo) itChildResult
								.next();
						superInfo.setChildAccountID(childInfo.getChildAccountID());
						setUseMaxID();
						rtnFlag = super.add(superInfo);						

					}
					itChildResult = childColl.iterator();
				}
				log4j.info("--------����������¼����-------");
				
				//���Ϣ�˻�
				
				SubAccountCurrentInfo currentInfo = null;
				currentInfo = subDao.findByAccountID(
						superInfo.getDebitInterestAccountID())
						.getSubAccountCurrenctInfo();
				if (currentInfo != null) {
					currentInfo.setInterestAccountID(superInfo
							.getSuperAccountID());
					subDao.updateSubAccountCurrent(currentInfo);
				}
			}
		}
		return rtnFlag;
	}
	/**
	 * ���浱ǰ������Ϣ
	 * 
	 * @param capitalSuperviseSettingInfo
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public String delete(CapitalSuperviseSettingInfo info, Collection superColl)
			throws Exception {
		String errMsg="";
		Iterator itChildResult = null;
		Iterator itSuperResult = null;
		
		if(superColl==null || superColl.size()==0)
		{
			return errMsg;
		}
		else
		{
			itSuperResult = superColl.iterator();
		}
		errMsg = chekcSuperBanlance(superColl);	
		if(errMsg!=null && !errMsg.equals(""))
		{
			return errMsg;
		}
		else
		{		
		
			if(itSuperResult != null && itSuperResult.hasNext()) 
			{
				log4j.info("--------ɾ��ԭ�м�¼-------");
				long rtnFlag = updateBySuperClientID(info.getSuperClientID(), info.getOfficeID(), info
						.getCurrencyID(), SETTConstant.BooleanValue.ISFALSE);
			}
			
		}
		return errMsg;
	}
	/**
	 * ��������ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
	 * 
	 * @return ���ݿ����ӡ�
	 */
	private Connection getConnection() throws Exception {

		Connection con = null;
		if (bConnectionFromContainer) {
			con = Database.getConnection();
		} else {
			con = Database.getConnection();
		}
		return con;
	}

	/**
	 * �������ݿ����ӵ���Դ��
	 * 
	 * @param bConnectionFromContainer
	 *            true - ������ȡ�����ݿ����ӡ� false - ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
	 */
	public void setConnectionFromContainer(boolean bConnectionFromContainer) {
		this.bConnectionFromContainer = bConnectionFromContainer;
	}

	public static final int ACCOUNT_TYPE_PARENT = 1;

	public static final int ACCOUNT_TYPE_SUB = 2;

	public long countRecordByAccountID(long accountID, int accountType)
			throws ITreasuryDAOException {
		initDAO();
		long number = 0;
		String strSQL = "select count(*) from " + strTableName + " where ";
		if (accountType == ACCOUNT_TYPE_PARENT) {
			strSQL += " superaccountid = " + accountID;
		} else {
			strSQL += " childaccountid = " + accountID;
		}
		strSQL += " and statusid > 0";
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		try {
			if (localRS.next())
				number = localRS.getLong(1);
		} catch (SQLException e) {
			throw new ITreasuryDAOException("", e);
		}
		finalizeDAO();
		return number;
	}
	/**
	 * ��Ӧ������ϵ�����и�Ϣ�ʽ��˻����ϼ�
	 */
	public double[] sumDebitInterestBlanceOfOneClient(long clientID)
			throws ITreasuryDAOException {
		initDAO();
		double balance[] = new double[2];
		String strSQL = "select sum(a.mbalance),sum(a.MUNCHECKPAYMENTAMOUNT) from sett_subaccount a,(select distinct b.DEBITINTERESTACCOUNTID as accountid from sett_account c,sett_CapitalSuperviseSetting b where b.superclientid = "
				+ clientID
				+ " and c.id = b.DEBITINTERESTACCOUNTID and c.nstatusid = "
				+ SETTConstant.AccountStatus.NORMAL
				+ " and b.statusid = 1) where a.naccountid = accountid and a.nstatusid = "
				+ SETTConstant.SubAccountStatus.NORMAL;
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		try {
			if (localRS.next())
				balance[0] = localRS.getDouble(1);
				balance[1] = localRS.getDouble(2);
		} catch (SQLException e) {
			throw new ITreasuryDAOException("", e);
		}
		finalizeDAO();
		return balance;
	}
	/**
	 * ����������λ�����ʽ��й�����������ڴ���˻����ϼ�
	 */
	public double[] sumCurrentBlanceOfOneClient(long clientID)
			throws ITreasuryDAOException {
		initDAO();
		double balance[] = new double[2];
		String strSQL = "select sum(a.mbalance),sum(a.MUNCHECKPAYMENTAMOUNT) from sett_subaccount a,(select distinct b.CHILDACCOUNTID as accountid from sett_account c,sett_CapitalSuperviseSetting b where b.superclientid = "
				+ clientID
				+ " and c.id = b.CHILDACCOUNTID and c.nstatusid = "
				+ SETTConstant.AccountStatus.NORMAL
				+ " and b.statusid = 1) where a.naccountid = accountid and a.nstatusid = "
				+ SETTConstant.SubAccountStatus.NORMAL;
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		try {
			if (localRS.next())
				balance[0] = localRS.getDouble(1);
				balance[1] = localRS.getDouble(2);
		} catch (SQLException e) {
			throw new ITreasuryDAOException("", e);
		}
		finalizeDAO();
		return balance;
	}
	/**
	 * �����ӹ�˾�˻�IDͳ���ϼ���λ����ʵ���ʽ��й���Ļ��ڴ���˻���� clientID �ϼ���λ�ͻ�ID superAccountID
	 * �ϼ���λ�˻�ID
	 */
	public double[] sumSuperAccountBalanceByChildAccountID(long childAccountID)
			throws ITreasuryDAOException {
		initDAO();
		double balance[] = new double[2];
		String strSQL = "select sum(suba.mbalance),sum(suba.MUNCHECKPAYMENTAMOUNT) from sett_subaccount suba,sett_account a,(select distinct superaccountid from sett_CapitalSuperviseSetting where childaccountid = "
				+ childAccountID
				+ " and statusid = 1) c "
				+ "where suba.naccountid = a.id and a.id=c.superaccountid and a.nstatusid = "
				+ SETTConstant.AccountStatus.NORMAL
				+ " and suba.nstatusid = "
				+ SETTConstant.SubAccountStatus.NORMAL;
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		try {
			if (localRS.next())
				balance[0] = localRS.getDouble(1);
				balance[1] = localRS.getDouble(2);
		} catch (SQLException e) {
			throw new ITreasuryDAOException("", e);
		}
		finalizeDAO();
		return balance;
	}
	
	/**
	 * �����ӹ�˾�˻�IDͳ���ϼ���λ����ʵ���ʽ��й���Ļ��ڴ���˻���� clientID �ϼ���λ�ͻ�ID superAccountID
	 * �ϼ���λ�˻�ID
	 */
	public double[] sumSuperAccountBalanceBySuperAccountID(long superAccountID)
			throws ITreasuryDAOException {
		initDAO();
		double balance[] = new double[2];
		String strSQL = "select sum(suba.mbalance),sum(suba.MUNCHECKPAYMENTAMOUNT) from sett_subaccount suba,sett_account a,(select distinct superaccountid from sett_CapitalSuperviseSetting where superclientid in (select superclientid from sett_CapitalSuperviseSetting where superaccountid = "
				+ superAccountID
				+ " and statusid = 1) and statusid = 1) c "
				+ "where suba.naccountid = a.id and a.id=c.superaccountid and a.nstatusid = "
				+ SETTConstant.AccountStatus.NORMAL
				+ " and suba.nstatusid = "
				+ SETTConstant.SubAccountStatus.NORMAL;
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		try {
			if (localRS.next())
				balance[0] = localRS.getDouble(1);
				balance[1] = localRS.getDouble(2);
		} catch (SQLException e) {
			throw new ITreasuryDAOException("", e);
		}
		finalizeDAO();
		return balance;
	}	
	/**
	 * ͨ������� ���˻�ID �õ� ��Ϣ�ʽ��˻�ID ���� �� �� ��Ϣ�ʽ��˻�ID �õ� ���˻� ID
	 */
	public long getSuperOrDebitInterestAccountID(long lAccountID) throws ITreasuryDAOException {
		initDAO();

		long lReturn = -1;
		long lAccountTypeID = -1;
		String strSQL = "";
		try {
			if (lAccountID > 0) {
				Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
				AccountInfo accountInfo = sett_AccountDAO.findByID(lAccountID);
				if (accountInfo != null) {
					lAccountTypeID = accountInfo.getAccountTypeID();
				}
				strSQL = "select DebitInterestAccountID from SETT_CAPITALSUPERVISESETTING where SuperAccountID = " + lAccountID + " and StatusID >0 ";
			}
			log.print("��ѯ��Ϣ�˻����߻����˻�SQL = " + strSQL);
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if (localRS.next()) lReturn = localRS.getLong(1);
			finalizeDAO();
			return lReturn;
		}
		catch (SQLException e) {
		throw new ITreasuryDAOException("", e);
		}
	}
	
	public long getSuperClientIDByChildAccountID(long childAccountID) throws ITreasuryDAOException{
		CapitalSuperviseSettingInfo info = new CapitalSuperviseSettingInfo();
		info.setChildAccountID(childAccountID);
		Collection c = super.findByCondition(info);
		long superClientID = -1;
		Iterator it = c.iterator();
		if(it.hasNext()){
			CapitalSuperviseSettingInfo tmp = (CapitalSuperviseSettingInfo) it.next();
			superClientID = tmp.getSuperClientID();
		}
		return superClientID;
	}

}
