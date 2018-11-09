package com.iss.itreasury.system.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataentity.InitUserInfo;

import com.iss.itreasury.ebank.privilege.bizlogic.GroupBean;
import com.iss.itreasury.ebank.privilege.bizlogic.UserBean;
import com.iss.itreasury.ebank.privilege.dataentity.AccountInfo;
import com.iss.itreasury.ebank.privilege.dataentity.ClientInfo;

import com.iss.itreasury.ebank.privilege.dao.OB_GroupDAO;
import com.iss.itreasury.ebank.privilege.dao.OB_Group_Of_UserDAO;
import com.iss.itreasury.ebank.privilege.dao.OB_PrivilegeDAO;
import com.iss.itreasury.ebank.privilege.dao.OB_Privilege_Of_GroupDAO;
import com.iss.itreasury.ebank.privilege.dao.OB_UserDAO;
import com.iss.itreasury.ebank.privilege.dataentity.OB_GroupInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_Group_Of_UserInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_PrivilegeInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_Privilege_Of_GroupInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo;
import com.iss.itreasury.encrypt.EncryptManager;

import com.iss.itreasury.util.*;
import com.iss.itreasury.system.dao.AdminOfUserDao;
import com.iss.itreasury.system.dataentity.*;
import com.iss.itreasury.system.privilege.dao.Sys_UserDAO;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.system.dao.PageLoader;

/**
 * @author jinchen
 * @version 1.0 �������пͻ��������ó�ʼ�����˻�ҵ���� (ʹ��javabean) ��������
 */
public class EBankbean {
	

	/**
	 * ���ҿͻ����õ��˻�
	 * 
	 * @throws IException
	 */

	/*
	 * �������ݿ�����
	 */
	
	private Connection initBean() throws ITreasuryDAOException {
		Connection transConn = null;
		try {

			transConn = Database.getConnection();
			transConn.setAutoCommit(false);

		} catch (Exception e) {
			throw new ITreasuryDAOException("���ݿ��ʹ���쳣����", e);
		}
		return transConn;
	}

	/*
	 * �ͷ����ݿ�����
	 */
	private void finalizeBean(Connection transConn)
			throws ITreasuryDAOException {
		try {
			if (transConn != null) {
				transConn.close();
				transConn = null;
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("���ݿ�ر��쳣����", e);
		}
	}

	public Collection findAccountByClient(long lClientID) throws IException {
		/*
		 * UserAdmin oUser = new UserAdmin(); Collection c = null; try { c =
		 * (Collection)oUser.findAccountByClient(lClientID); } catch (Exception
		 * e) { Log.print("bizlogic-EbankBiz-findAccountByClient failed because " +
		 * e.toString()); } return c;
		 */
		/*
		 * 2004-10-20 �޸� by jinchen
		 */

		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();

		try {
			c = (Collection) dao.findAccountByClient(lClientID);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("���ҿͻ����õ��˻�ʱ�����ڲ������������Ա��ϵ");
		}

		return c;

	}
	public Collection findEbankAccountBySelect(long lClientID,long lCurrencyID) throws IException
	{
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();
		try{
			c=dao.findEbankAccountBySelect(lClientID, lCurrencyID);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException("����ϵͳ����Ա���õ��˻����������������Ա��ϵ");
		}
		return c;
	}
	public Collection findEbankAccountBySA(long lClientID,long lCurrencyID) throws IException
	{
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();
		try{
			c=dao.findEbankAccountBySA(lClientID, lCurrencyID);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException("����ϵͳ����Ա���õ��˻����������������Ա��ϵ");
		}
		return c;
	}
	/**
	 * ����ϵͳ����Ա���õ��˻�
	 */
	public Collection findAccountBySA(long lClientID, long lCurrencyID,
			long lUserID) throws IException {
		/*
		 * UserAdmin oUser = new UserAdmin(); Collection c = null; try { c =
		 * (Collection)oUser.findAccountBySA(lClientID,lCurrencyID,lUserID); }
		 * catch (Exception e) { Log.print("bizlogic-EbankBiz-findAccountBySA
		 * failed because " + e.toString()); } return c;
		 */

		/*
		 * 2004-10-20 �޸� by jinchen
		 */

		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();
		try {
			c = (Collection) dao.findAccountBySA(lClientID, lCurrencyID,
					lUserID);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("����ϵͳ����Ա���õ��˻����������������Ա��ϵ");
		}

		return c;

	}

	/**
	 * ��������ϵͳ����Ա�û���ͬʱ���ӿ��õ��û��顣(��������)
	 * 
	 * added by mzh_fu 2008/02/02
	 * @throws IException
	 * @throws Exception
	 */
	public OB_UserInfo createClientSA(OB_UserInfo userInfo) throws Exception
	{
		long clientId = userInfo.getNClientId();
		String strEbankLoginNo = userInfo.getSLoginNo();
		String strCertNo = userInfo.getSCertNo();
		String strAccountNo[]=userInfo.getSAccountNo();
		long inputUserId = userInfo.getNInputUserID();
		String strCertCn = userInfo.getSCertCn();
		long currencyID = -1;
		currencyID = userInfo.getNCurrencyId();  //���ӱ��ֵĴ���
		System.out.println("===============lcurrencyID============"+currencyID);
		
		Connection transConn = null;
		OB_UserInfo info = null;
		long userId = -1;
		
		try {
			transConn = Database.getConnection();
			transConn.setAutoCommit(false);

			OB_UserDAO ob_userDao = new OB_UserDAO(transConn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO(
					"OB_Group_Of_User", transConn);
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", transConn);
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO(
					"ob_privilege", transConn);
			OB_Privilege_Of_GroupDAO ob_pribilege_of_groupDao = new OB_Privilege_Of_GroupDAO(
					"ob_privilege_of_group", transConn);
			
			if(ob_userDao.isExistOBInfo(clientId))
			{
				throw new IException("�ÿͻ��Ѵ��������û�");
			}
			
			if (ob_userDao.isDuplicateOBLoginNo(strEbankLoginNo)) 
			{
				throw new IException("�û���¼���ظ�");
			}
	
			ClientInfo clientInfo = ob_userDao.findClientById(clientId);

			if (clientInfo == null) 
			{
				throw new IException("�ͻ���Ч");
			}
			
			long groupIdSA = 0L; // ϵͳ������ ID
			InitUserXml initUserXml = new InitUserXml();
			Collection collInitUser = initUserXml.getIniUserInfo();
			Iterator itInitUser = collInitUser.iterator();
			
			ArrayList alUserGroup = new ArrayList();
			InitUserInfo initUserInfo = null;
			String strUserName = "Ĭ���û�";

			while (itInitUser.hasNext()) {
				initUserInfo = (InitUserInfo) itInitUser.next();
				
				String strGroupName = initUserInfo.getGroupName();
				strUserName = initUserInfo.getUserName();
				int intModuleId = initUserInfo.getModuleId();
				boolean blStatus = initUserInfo.isStatus();

				if (blStatus) {
					OB_GroupInfo groupCondition = new OB_GroupInfo();
					/*
					 * �����Ƿ���ϵͳ����Ա��
					 */
					if(strGroupName.indexOf("ϵͳ������")>-1)
					{
						groupCondition.setIsAdminGroup(1);
					}
					else
					{
						groupCondition.setIsAdminGroup(0);
					}		
					/*
					 * ����Ȩ������
					 */
					groupCondition.setName(strGroupName);
					/*
					 * ����Ȩ��ModuleId
					 */
					groupCondition.setModuleID(intModuleId);
					/*
					 * ���ÿͻ����
					 */
					groupCondition.setClientId(clientInfo.m_lID);
					groupCondition.setInputUserID(-1);
					
					Collection collPvg = initUserInfo.getPrivileges();
					Iterator itPvg = collPvg.iterator();
					ArrayList pgCondition = new ArrayList();
					
					while (itPvg.hasNext()) {
						String strPvgNo = (String) itPvg.next();
						OB_Privilege_Of_GroupInfo ob_Privilege_Of_GroupInfo = new OB_Privilege_Of_GroupInfo();
						ob_Privilege_Of_GroupInfo.setPrivilegeID(ob_privilegeDao.findPrivilegeIdByNo(strPvgNo));
						pgCondition.add(ob_Privilege_Of_GroupInfo);
					}
					
					Collection cSA = ob_groupDao.findGroupByName(groupCondition.getName(), clientInfo.m_lID);
					
					if (cSA != null && cSA.size() > 0) {
						throw new IException("�Ѿ����ڴ�����[" + groupCondition.getName()+ "]��������¼��");
					}
					groupIdSA = ob_groupDao.add(groupCondition);

					int lenSA = pgCondition.size();
					for (int i = 0; i < lenSA; i++) {
						OB_Privilege_Of_GroupInfo pgCon = (OB_Privilege_Of_GroupInfo) pgCondition
								.get(i);
						pgCon.setGroupID(groupIdSA);
						ob_pribilege_of_groupDao.add(pgCon);
					}
					OB_Group_Of_UserInfo group_userCondition = new OB_Group_Of_UserInfo();
					group_userCondition.setGroupID(groupIdSA);
					alUserGroup.add(group_userCondition);
				}
			}
			/*
			 * �����û��� ͬʱ����û����û���
			 */
			OB_UserInfo userCondition = new OB_UserInfo();
			/*
			 * ���ÿͻ����
			 */
			userCondition.setNClientId(clientInfo.m_lID);
			userCondition.setSName(strUserName);
			userCondition.setSCertNo(strCertNo);
			userCondition.setSCertCn(strCertCn);
			userCondition.setNOfficeID(clientInfo.m_lOfficeID);
			userCondition.setNCurrencyId(currencyID);    //�������-����ң�������ֻ������ң�
			userCondition.setSLoginNo(strEbankLoginNo);
			String strPassword = this.createRandomPassword();
			userCondition.setSPassword(strPassword);

			Collection c = ob_userDao.findByLoginNo(userCondition.getSLoginNo(), clientInfo.m_lID);
			/*
			 * c ��Ϊnull ˵����¼���Ѿ����ڣ�throw new IException
			 */
			if (c != null && c.size() > 0) {
				throw new IException("Sys_E001");
			}

			/*
			 * ����û���Ϣ
			 */
			userCondition.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			userCondition.setNInputUserID(inputUserId);
			userCondition.setNSaid(-1);
			// userCondition.setPassword("123456");
			userCondition.setInput(Env.getSystemDateTime());
			Log.print("���������û�Begain");
			userId = ob_userDao.add(userCondition);
			int len = alUserGroup.size();
			/*
			 * ����û��û����ϵ��Ϣ
			 */
			for (int i = 0; i < len; i++) {
				OB_Group_Of_UserInfo groupUser = (OB_Group_Of_UserInfo) alUserGroup
						.get(i);
				groupUser.setUserID(userId);
				ob_group_of_userDao.add(groupUser);
			}
			/*
			 * ����½�ϵͳ����Ա���ܲ������˺�
			 */
			if (strAccountNo != null) {
				for (int j = 0; j < strAccountNo.length; j++) {
					ob_userDao.addAccountOwnedBySA(clientInfo.m_lID,
							userId, strAccountNo[j]);
					ob_userDao
							.addAccountPrvg(ob_userDao
									.getInitAccountPrvgByAccountNo(strAccountNo[j]));
				}
			}
			
			info = (OB_UserInfo) ob_userDao.findByID(userId,OB_UserInfo.class);
			System.out.println(info.getId() + info.getNClientId()
					+ info.getSName() + info.getSPassword());
			info.setSPassword(strPassword);	
			transConn.commit(); 
			
			if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
			{
				EncryptManager.getOBBeanFactory().changeOBUserPassword(userId, strPassword);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(transConn!=null)
				try { transConn.rollback(); } catch (SQLException e1) { }
			throw e;	
			
		} finally {
				this.finalizeBean(transConn);
		}

		return info;
	}
	
	
	
	
	
	
	/**
	 * �޸��û�
	 * 
	 * @param pi
	 * @return long
	 * @throws IException
	 * @throws Exception
	 */
	public OB_UserInfo updateClientSA(long userId, String strClientCode, String strCertNo,
			String[] strAccountNo)

	{
		Connection transConn = null;
		OB_UserInfo info = null;
		OB_UserInfo userCondition = new OB_UserInfo();
		long lReturn = -1;
		try {
			transConn = this.initBean();

			/*
			 * ��ʼ���������DAO ����ͬһ�����ݿ�����
			 */
			OB_UserDAO ob_userDao = new OB_UserDAO(true, transConn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO(
					"OB_Group_Of_User", transConn);
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", transConn);
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO(
					"ob_privilege", transConn);
			OB_Privilege_Of_GroupDAO ob_pribilege_of_groupDao = new OB_Privilege_Of_GroupDAO(
					"ob_privilege_of_group", transConn);

			// OBSystemDao obSystemDao = new OBSystemDao();
			ClientInfo ci = ob_userDao.findClientByCode(strClientCode);
			System.out.println("<><><><><><><><><><>"+ci.m_strCode+";%%%%%%%%%%%%%"+ci.m_lID);
			//modify by wjliu -- 2007/4/9 begin ----------------  ԭ�������û���Ϣʱ���Ĳ����ǵ�¼��,���ڸ�Ϊ��ID����ѯ
			
			//Collection cTmp = ob_userDao.findByLoginNo(ci.m_strCode, ci.m_lID);
			
			Collection cTmp = ob_userDao.findUserByClientID( ci.m_lID);
			if (cTmp != null) {
				Iterator iTmp = cTmp.iterator();
				info = (OB_UserInfo) iTmp.next();
			}
			System.out.println("$$$$$$$$$$$$$$$"+info!= null);
			// info = (OB_UserInfo)
			// ob_userDao.findByLoginNo(ci.m_strCode,ci.m_lID);
			if (info != null) {
				System.out.println(info.getId() + "\t" + info.getSName());
				userCondition.setId(info.getId());
				userCondition.setSCertNo(strCertNo);
			}
			Collection c_old = ob_userDao.findAccountBySA(ci.m_lID, -1, info.getId());
			 
			HashMap hm_old = new HashMap();
			
			if (c_old != null) {
				Iterator it = c_old.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					hm_old.put(ai.m_strAccountNo, ai.m_strAccountName);
				}
			}

			//
			ob_userDao.deleteAccountOwnedBySA(ci.m_lID);
			ob_userDao.deleteAccountOwnedByUser(info.getId());
			HashMap hm = new HashMap();
			if (strAccountNo != null) {
				Log.print("------------Account's lentth is + "+ strAccountNo.length + "------------");

				for (int i = 0; i < strAccountNo.length; i++) {
					Log.print("--------add a account------------");
					ob_userDao.setUseMaxID();
					ob_userDao.addAccountOwnedBySA(ci.m_lID, info.getId(),strAccountNo[i]);
					ob_userDao.addAccountOwnedByUser(ci.m_lID, info.getId(),strAccountNo[i]);
					if (hm_old.get(strAccountNo[i]) == null) {
						ob_userDao.addAccountPrvg(ob_userDao.getInitAccountPrvgByAccountNo(strAccountNo[i]));
					}
					hm.put(strAccountNo[i], strClientCode);
				}
			}
			Collection c = ob_userDao.findOBAccountByClient(ci.m_lID);
			if (c != null) {
				Iterator it = c.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					if (hm.get(ai.m_strAccountNo) == null) {
						ob_userDao.deleteAccountOwnedByClient(ci.m_lID,ai.m_strAccountNo);
						// obSystemDao.addAccountPrvg(
						// obSystemDao.getInitAccountPrvgByAccountNo(ai.m_strAccountNo)
						// );
					}
				}
			}
			//add by wjliu -- 2007-4-19 ���û���Ϣ�޸ĺ�,��״̬Ϊδ����״̬
			userCondition.setNInputUserID(userId);
			userCondition.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			ob_userDao.update(userCondition);
			transConn.commit();
			this.finalizeBean(transConn);

		} catch (Exception e) {
			Log.print("bizlogic-UserAdmin-modifyClientSA failed because "
					+ e.toString());
			try {
				transConn.rollback();
				this.finalizeBean(transConn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			try {
				this.finalizeBean(transConn);
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return info;

	}	
	/**
	 * �޸��û�(��������)
	 * 
	 * @param pi
	 * @return long
	 * @throws IException
	 * @throws Exception
	 */
	public OB_UserInfo updateClientSA(long userId,long clientId,String strEbankLoginNo, String strCertNo,
			String[] strAccountNo)

	{
		Connection transConn = null;
		OB_UserInfo info = null;
		OB_UserInfo userCondition = new OB_UserInfo();
		userCondition.setSLoginNo(strEbankLoginNo);
		long lReturn = -1;
		try {
			transConn = this.initBean();

			/*
			 * ��ʼ���������DAO ����ͬһ�����ݿ�����
			 */
			OB_UserDAO ob_userDao = new OB_UserDAO(true, transConn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO(
					"OB_Group_Of_User", transConn);
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", transConn);
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO(
					"ob_privilege", transConn);
			OB_Privilege_Of_GroupDAO ob_pribilege_of_groupDao = new OB_Privilege_Of_GroupDAO(
					"ob_privilege_of_group", transConn);

			// OBSystemDao obSystemDao = new OBSystemDao();
			ClientInfo ci = ob_userDao.findClientById(clientId);
			System.out.println("<><><><><><><><><><>"+ci.m_strCode+";%%%%%%%%%%%%%"+ci.m_lID);
			//modify by wjliu -- 2007/4/9 begin ----------------  ԭ�������û���Ϣʱ���Ĳ����ǵ�¼��,���ڸ�Ϊ��ID����ѯ
			
			//Collection cTmp = ob_userDao.findByLoginNo(ci.m_strCode, ci.m_lID);
			
			Collection cTmp = ob_userDao.findUserByClientID( ci.m_lID);
			if (cTmp != null) {
				Iterator iTmp = cTmp.iterator();
				info = (OB_UserInfo) iTmp.next();
			}
			System.out.println("$$$$$$$$$$$$$$$"+info!= null);
			// info = (OB_UserInfo)
			// ob_userDao.findByLoginNo(ci.m_strCode,ci.m_lID);
			if (info != null) {
				System.out.println(info.getId() + "\t" + info.getSName());
				userCondition.setId(info.getId());
				userCondition.setSCertNo(strCertNo);
			}
			Collection c_old = ob_userDao.findAccountBySA(ci.m_lID, -1, info.getId());
			 
			HashMap hm_old = new HashMap();
			
			if (c_old != null) {
				Iterator it = c_old.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					hm_old.put(ai.m_strAccountNo, ai.m_strAccountName);
				}
			}

			//
			ob_userDao.deleteAccountOwnedBySA(ci.m_lID);
			ob_userDao.deleteAccountOwnedByUser(info.getId());
			HashMap hm = new HashMap();
			if (strAccountNo != null) {
				Log.print("------------Account's lentth is + "+ strAccountNo.length + "------------");

				for (int i = 0; i < strAccountNo.length; i++) {
					Log.print("--------add a account------------");
					ob_userDao.setUseMaxID();
					ob_userDao.addAccountOwnedBySA(ci.m_lID, info.getId(),strAccountNo[i]);
					ob_userDao.addAccountOwnedByUser(ci.m_lID, info.getId(),strAccountNo[i]);
					if (hm_old.get(strAccountNo[i]) == null) {
						ob_userDao.addAccountPrvg(ob_userDao.getInitAccountPrvgByAccountNo(strAccountNo[i]));
					}
					hm.put(strAccountNo[i], strEbankLoginNo);
				}
			}
			Collection c = ob_userDao.findOBAccountByClient(ci.m_lID);
			if (c != null) {
				Iterator it = c.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					if (hm.get(ai.m_strAccountNo) == null) {
						ob_userDao.deleteAccountOwnedByClient(ci.m_lID,ai.m_strAccountNo);
						// obSystemDao.addAccountPrvg(
						// obSystemDao.getInitAccountPrvgByAccountNo(ai.m_strAccountNo)
						// );
					}
				}
			}
			//add by wjliu -- 2007-4-19 ���û���Ϣ�޸ĺ�,��״̬Ϊδ����״̬
			userCondition.setNInputUserID(userId);
			userCondition.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			ob_userDao.update(userCondition);
			transConn.commit();
			this.finalizeBean(transConn);

		} catch (Exception e) {
			Log.print("bizlogic-UserAdmin-modifyClientSA failed because "
					+ e.toString());
			try {
				transConn.rollback();
				this.finalizeBean(transConn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			try {
				this.finalizeBean(transConn);
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return info;

	}
	
	
	
	/**
	 * �޸��û�(��������)
	 * 
	 * @param pi
	 * @return long
	 * @throws IException
	 * @throws Exception
	 */
	public OB_UserInfo updateClientSA(long userId,long clientId,String strEbankLoginNo, String strCertNo,String strCertCn,
			String[] strAccountNo)

	{
		Connection transConn = null;
		OB_UserInfo info = null;
		OB_UserInfo userCondition = new OB_UserInfo();
		userCondition.setSLoginNo(strEbankLoginNo);
		long lReturn = -1;
		try {
			transConn = this.initBean();

			/*
			 * ��ʼ���������DAO ����ͬһ�����ݿ�����
			 */
			OB_UserDAO ob_userDao = new OB_UserDAO(true, transConn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO(
					"OB_Group_Of_User", transConn);
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", transConn);
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO(
					"ob_privilege", transConn);
			OB_Privilege_Of_GroupDAO ob_pribilege_of_groupDao = new OB_Privilege_Of_GroupDAO(
					"ob_privilege_of_group", transConn);

			// OBSystemDao obSystemDao = new OBSystemDao();
			ClientInfo ci = ob_userDao.findClientById(clientId);
			System.out.println("<><><><><><><><><><>"+ci.m_strCode+";%%%%%%%%%%%%%"+ci.m_lID);
			//modify by wjliu -- 2007/4/9 begin ----------------  ԭ�������û���Ϣʱ���Ĳ����ǵ�¼��,���ڸ�Ϊ��ID����ѯ
			
			//Collection cTmp = ob_userDao.findByLoginNo(ci.m_strCode, ci.m_lID);
			
			Collection cTmp = ob_userDao.findUserByClientID( ci.m_lID);
			if (cTmp != null) {
				Iterator iTmp = cTmp.iterator();
				info = (OB_UserInfo) iTmp.next();
			}
			System.out.println("$$$$$$$$$$$$$$$"+info!= null);
			// info = (OB_UserInfo)
			// ob_userDao.findByLoginNo(ci.m_strCode,ci.m_lID);
			if (info != null) {
				System.out.println(info.getId() + "\t" + info.getSName());
				userCondition.setId(info.getId());
				userCondition.setSCertNo(strCertNo);
				userCondition.setSCertCn(strCertCn);
			}
			Collection c_old = ob_userDao.findAccountBySA(ci.m_lID, -1, info.getId());
			 
			HashMap hm_old = new HashMap();
			
			if (c_old != null) {
				Iterator it = c_old.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					hm_old.put(ai.m_strAccountNo, ai.m_strAccountName);
				}
			}

			//
			ob_userDao.deleteAccountOwnedBySA(ci.m_lID);
			ob_userDao.deleteAccountOwnedByUser(info.getId());
			HashMap hm = new HashMap();
			if (strAccountNo != null) {
				Log.print("------------Account's lentth is + "+ strAccountNo.length + "------------");

				for (int i = 0; i < strAccountNo.length; i++) {
					Log.print("--------add a account------------");
					ob_userDao.setUseMaxID();
					ob_userDao.addAccountOwnedBySA(ci.m_lID, info.getId(),strAccountNo[i]);
					ob_userDao.addAccountOwnedByUser(ci.m_lID, info.getId(),strAccountNo[i]);
					if (hm_old.get(strAccountNo[i]) == null) {
						ob_userDao.addAccountPrvg(ob_userDao.getInitAccountPrvgByAccountNo(strAccountNo[i]));
					}
					hm.put(strAccountNo[i], strEbankLoginNo);
				}
			}
			Collection c = ob_userDao.findOBAccountByClient(ci.m_lID);
			if (c != null) {
				Iterator it = c.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					if (hm.get(ai.m_strAccountNo) == null) {
						ob_userDao.deleteAccountOwnedByClient(ci.m_lID,ai.m_strAccountNo);
						// obSystemDao.addAccountPrvg(
						// obSystemDao.getInitAccountPrvgByAccountNo(ai.m_strAccountNo)
						// );
					}
				}
			}
			//add by wjliu -- 2007-4-19 ���û���Ϣ�޸ĺ�,��״̬Ϊδ����״̬
			userCondition.setNInputUserID(userId);
			userCondition.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			ob_userDao.update(userCondition);
			transConn.commit();
			this.finalizeBean(transConn);

		} catch (Exception e) {
			Log.print("bizlogic-UserAdmin-modifyClientSA failed because "
					+ e.toString());
			try {
				transConn.rollback();
				this.finalizeBean(transConn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			try {
				this.finalizeBean(transConn);
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return info;

	}
	
	/**
	 * ��ѯ�ͻ�����Ա��Ϣ
	 * 
	 * @param QueryClientSAInfo
	 * @return Collection
	 * @throws Exception
	 */
	/*
	 * public Collection queryOBClientSA(QueryClientSAInfo qc) { UserAdmin oUser =
	 * new UserAdmin(); Collection c = null; try { c = (Collection)
	 * oUser.queryOBClientSA(qc); } catch (Exception e) {
	 * Log.print("bizlogic-EbankBiz-queryOBClientSA failed because " +
	 * e.toString()); } return c; }
	 */

	private String createRandomPassword() {
		String strPassword = "123456";
		try {
			Random random = new Random();
			long lRandom = random.nextLong();
			if (lRandom > 0)
				strPassword = String.valueOf(lRandom).substring(0, 6);
			else
				strPassword = String.valueOf(lRandom).substring(1, 7);
		} catch (Exception e) {
			Log.print("createRandomPassword failed because " + e.toString());
			strPassword = "123456";
		}
		return strPassword;
	}

	/**
	 * ��ѯ�ͻ�����Ա��Ϣ
	 * 
	 * @param QueryClientSAInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Collection queryOBClientSA(QueryClientSAInfo qc) throws IException {
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();

		try {
			c = (Collection) dao.queryOBClientSA(qc);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("queryOBClientSA ʱ�����ڲ������������Ա��ϵ");
		}

		return c;
	}

	/**
	 * ��ѯ�������Ͽͻ���Ϣ
	 * 
	 * @param QueryClientSAInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Collection queryOBClientAll(QueryClientSAInfo qc) throws IException {
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();

		try {
			c = (Collection) dao.queryOBClientAll(qc);
		} catch (IException e) {
			throw e;
		}

		return c;
	}
	/**
	 * ��ѯ���Ը��˵����Ͽͻ���Ϣ������ƹ���
	 * 
	 * @param QueryClientSAInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Collection queryCheckOBClientAll(QueryClientSAInfo qc) throws IException {
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();

		try {
			c = (Collection) dao.queryCheckOBClientAll(qc);
		} catch (IException e) {
			throw e;
		}

		return c;
	}
/**
 *  ��ѯ���������û�������ƽ̨��
 * @param qc
 * @return
 * @throws IException
 */
	public Collection queryCheckClientAll(QueryClientSAInfo qc) throws IException {
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();

		try {
			c = (Collection) dao.queryCheckClientAll(qc);
		} catch (IException e) {
			throw e;
		}

		return c;
	}
	/**
	 * ���ݿͻ�ID�������û�
	 * 
	 * @param lClientID
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findUserByClientID(long lClientID) throws IException {
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();

		try {
			c = (Collection) dao.findUserByClientID(lClientID);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("findUserByClientIDʱ�����ڲ������������Ա��ϵ");
		}

		return c;
	}

	/**
	 * ���ݿͻ�id �����û���
	 * 
	 * @param lClientID
	 * @return Collection
	 * @throws IException
	 * @throws Exception
	 */
	public Collection findGroupsByClient(long lClientID) throws IException {
		Collection c = null;
		OB_UserDAO dao = new OB_UserDAO();

		try {
			c = (Collection) dao.findGroupsByClient(lClientID);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("findGroupsByClientssʱ�����ڲ������������Ա��ϵ");
		}

		return c;
	}

	public Collection getAllPrivilege() throws IException {
		Collection c = null;
		OB_PrivilegeDAO dao = new OB_PrivilegeDAO();

		try {
			c = (Collection) dao.getAllPrivilege();
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("getAllPrivilegeʱ�����ڲ������������Ա��ϵ");
		}

		return c;
	}

	/**
	 * �޸��û�����
	 * 
	 * @param lUserID
	 * @param strPassword
	 * @return long
	 * @throws IException
	 * @throws Exception
	 */
	public long changePassword(long lUserID, String strPassword) throws IException {
		long lReturn = lUserID;
		try{
			//����У��
			if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
			{
				try {
					EncryptManager.getOBBeanFactory().changeOBUserPassword(lUserID, strPassword);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
			OB_UserInfo info = new OB_UserInfo();
			OB_UserDAO dao = new OB_UserDAO();
			info.setId(lUserID);
			info.setSPassword(strPassword);
			info.setDtChangePassword(Env.getSystemDateTime());
			dao.update(info);
			}
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			lReturn = -1;
			e.printStackTrace();

		}

		return lReturn;

	}
	/**
	 * �޸��û���������(����ƹ���)
	 * 
	 * @param lUserID
	 * @param strPassword
	 * @return long
	 * @throws IException
	 * @throws Exception
	 */
	public long changeExchangePassword(long lUserID, String strPassword) throws IException {
		long lReturn = lUserID;
		try{
			//����У��
			if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
			{
				try {
					EncryptManager.getOBBeanFactory().changeOBUserPassword(lUserID, strPassword);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
			OB_UserInfo info = new OB_UserInfo();
			OB_UserDAO dao = new OB_UserDAO();
			info.setId(lUserID);
			info.setExchangePassword(strPassword);
			info.setDtChangePassword(Env.getSystemDateTime());
			dao.update(info);
			}
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			lReturn = -1;
			e.printStackTrace();

		}

		return lReturn;

	}
	public long modifyClientCertNo(long lUserID, String strCertNo) {
		long lReturn = lUserID;
		OB_UserInfo info = new OB_UserInfo();
		OB_UserDAO dao = new OB_UserDAO();
		info.setId(lUserID);
		info.setSCertNo(strCertNo);
		try {
			dao.update(info);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			lReturn = -1;
			e.printStackTrace();

		}

		return lReturn;
	}
	
	public long modifyClientCertNo(OB_UserInfo info) {
		long lReturn = 1;
		OB_UserDAO dao = new OB_UserDAO();
		try {
			dao.update(info);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			lReturn = -1;
			e.printStackTrace();

		}
		return lReturn;
	}
	
	/**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userCondition
     * @param group_userCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int check(OB_UserInfo userCondition) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
            
        	OB_UserDAO userDao = new OB_UserDAO(); 
        	//��ѯ���û���Ϣ
        	userCondition = (OB_UserInfo)(userDao.findByID(userCondition.getId(),userCondition.getClass()));
            /*
             * �����û���Ϣ
             */        	
            userCondition.setNStatus(SYSConstant.SysCheckStatus.CHECK);
        	userCondition.setCheckUserID(userCondition.getCheckUserID());
        	userCondition.setCheckDate(Env.getSystemDateTime()); 
            userDao.update(userCondition);                
            nResult = 1;            
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    }
    

	//
	/**
	 * ��ø���ģ�飨����㣩�µ�����Ȩ��,���������û��顣
	 * 
	 * @param lModule
	 * @throws Exception
	 * @return
	 */
	public Collection getPrivilegesByTopMenu(Collection cAllPvg, int lTopMenuID) {
		Vector v = new Vector();
		try {
			if (cAllPvg != null) {
				Iterator it = cAllPvg.iterator();
				while (it.hasNext()) {
					OB_PrivilegeInfo pvg = (OB_PrivilegeInfo) it.next();
					//modify by wjliu -2007-6-16 ȡ��һ��'-'֮�����
					String strModule = "";
					String[] tempNo =  pvg.getPrivilegeNo().split("-");
					if(tempNo.length > 1)
					{
						strModule = tempNo[1];
					}
					//if (strModule.indexOf(String.valueOf(lTopMenuID)) >= 0) {
					if (strModule.equals(String.valueOf(lTopMenuID)) ) {
						v.addElement(pvg);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getPrivilegesByModule error because "
					+ e.toString());

		}
		return (v.size() > 0 ? v : null);
	}

	public long addGroup(OB_GroupInfo groupCondition,
			OB_Privilege_Of_GroupInfo pgCondition[]) throws IException

	{

		long groupId = -1;
		GroupBean bean = new GroupBean();

		groupId = bean.addGroup(groupCondition, pgCondition);

		return groupId;
	}

	public long updateGroup(OB_GroupInfo groupCondition,
			OB_Privilege_Of_GroupInfo pgCondition[]) throws IException {
		long groupId = -1;
		GroupBean bean = new GroupBean();

		groupId = bean.updateGroup(groupCondition, pgCondition);

		return groupId;
	}

	public long delGroup(long groupId) throws IException {
		long lReturn = -1;
		GroupBean bean = new GroupBean();

		lReturn = bean.delGroup(groupId);

		return lReturn;
	}

	public OB_GroupInfo findGroupInfoByID(long groupID) throws IException {
		OB_GroupInfo info = null;

		GroupBean bean = new GroupBean();

		info = bean.findGroupInfoByID(groupID);

		return info;
	}

	public long addUserAll(OB_UserInfo userCondition,
			OB_Group_Of_UserInfo group_userCondition[], String[] strAccountNo,String[] AccountNoQuery,String[] EbankAccountNoOperate,String[] EbankAccountNoQuery)
			throws IException {
		long lReturn = -1;
		UserBean bean = new UserBean();
		lReturn = bean.addUserAll(userCondition, group_userCondition, strAccountNo,AccountNoQuery,EbankAccountNoOperate,EbankAccountNoQuery);
		return lReturn;
	}
	
	public long addUser(OB_UserInfo userCondition)throws IException {
		long lReturn = -1;
		UserBean bean = new UserBean();
		lReturn = bean.addUser(userCondition);
		return lReturn;
	}	
	
	public long addUser(OB_UserInfo userCondition,
			OB_Group_Of_UserInfo group_userCondition[], String[] strAccountNo)
			throws IException {
		long lReturn = -1;
		UserBean bean = new UserBean();

		lReturn = bean
				.addUser(userCondition, group_userCondition, strAccountNo);

		return lReturn;
	}
	public void updateUserEbankAccountPublic(OB_UserInfo userCondition,String[] strAccountNoQuery,String strTableName) throws IException
	{
		UserBean bean = new UserBean();
		bean.updateUserEbankAccountPublic(userCondition, strAccountNoQuery, strTableName);
	}
	public void updateUserInfoPublic(OB_UserInfo userCondition,String[] strAccountNoQuery,String strTableName) throws IException
	{
		UserBean bean = new UserBean();
		bean.updateUserAccountPublic(userCondition, strAccountNoQuery, strTableName);
	}
	public long updateUserInfo(OB_UserInfo userCondition,
			OB_Group_Of_UserInfo group_userCondition[], String[] strAccountNo)
			throws IException {
		long lReturn = -1;
		UserBean bean = new UserBean();

		lReturn = bean.updateUserInfo(userCondition, group_userCondition,
				strAccountNo);

		return lReturn;
	}
	
	public long updateUserInfo(OB_UserInfo userCondition)
			throws IException {
		long lReturn = -1;
		UserBean bean = new UserBean();
		lReturn = bean.updateUserInfo(userCondition);
		return lReturn;
	}	
	/**
	 * �޸��ʻ�Ȩ��
	 * @param userCondition
	 * @param strAccountNo
	 * @return
	 * @throws IException
	 */
	public long updateUserAccount(OB_UserInfo userCondition,String[] strAccountNo)throws IException {
		long lReturn = -1;
		UserBean bean = new UserBean();
		lReturn = bean.updateUserAccount(userCondition,
				strAccountNo);
		return lReturn;
	}	

	public long delUser(long userId) throws IException {
		long lReturn = -1;
		UserBean bean = new UserBean();

		lReturn = bean.delUser(userId);

		return lReturn;
	}

	public void updateUserSAId(long userId) throws IException {
		OB_UserDAO dao = new OB_UserDAO();
		dao.updateUserSAId(userId);

	}

	public OB_UserInfo findUserInfoByID(long userID) throws IException {
		OB_UserInfo info = null;
		UserBean bean = new UserBean();

		info = bean.findUserInfoByID(userID);

		return info;
	}

	public Collection findPrivilegesByGroupId(long groupId) throws IException {
		Collection c = null;
		GroupBean bean = new GroupBean();

		c = bean.findPrivilegesByGroupId(groupId);

		return c;
	}

	public static void main(String[] args) {
		EBankbean test = new EBankbean();
		OB_UserInfo info = new OB_UserInfo();
		String[] s = { "01-01-0002-3" };
		try {
			// info = (OB_UserInfo) test.createClientSA("01-0002", null);
			// info = (OB_UserInfo) test.updateClientSA("01-0002", s);
			// System.out.println(info.getNClientId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Collection c = null;
		 * 
		 * QueryClientSAInfo info = new QueryClientSAInfo();
		 * info.setOrder("SLOGINNO"); EBankbean test = new EBankbean();
		 */
		/*
		 * try { c = test.queryOBClientSA(info); System.out.println(c.size()); }
		 * catch (IException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		// long l = test.changePassword(29,"123");
	}
	
	
	/**
	 * �޸Ĺ���Ա�û�
	 * �÷���ΪupdateClientSA�Ĳ��䣬��ΪupdateClientSA�ڲ�ѯ�û�ʱ�����ǰ����û�id������clientid�����¸��³���
	 * @param pi
	 * @return long
	 * @throws IException
	 * @throws Exception
	 */
	public OB_UserInfo updateClientSA(long saId,long userId,long clientId,String strEbankLoginNo, String strCertNo,String strCertCn,
			String[] strAccountNo)

	{
		Connection transConn = null;
		OB_UserInfo info = null;
		OB_UserInfo userCondition = new OB_UserInfo();
		userCondition.setSLoginNo(strEbankLoginNo);
		long lReturn = -1;
		try {
			transConn = this.initBean();

			/*
			 * ��ʼ���������DAO ����ͬһ�����ݿ�����
			 */
			OB_UserDAO ob_userDao = new OB_UserDAO(true, transConn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO(
					"OB_Group_Of_User", transConn);
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", transConn);
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO(
					"ob_privilege", transConn);
			OB_Privilege_Of_GroupDAO ob_pribilege_of_groupDao = new OB_Privilege_Of_GroupDAO(
					"ob_privilege_of_group", transConn);

			// OBSystemDao obSystemDao = new OBSystemDao();
			ClientInfo ci = ob_userDao.findClientById(clientId);
			System.out.println("<><><><><><><><><><>"+ci.m_strCode+";%%%%%%%%%%%%%"+ci.m_lID);
			//modify by wjliu -- 2007/4/9 begin ----------------  ԭ�������û���Ϣʱ���Ĳ����ǵ�¼��,���ڸ�Ϊ��ID����ѯ
			
			//Collection cTmp = ob_userDao.findByLoginNo(ci.m_strCode, ci.m_lID);
			
			Collection cTmp = ob_userDao.findUserByClientID( ci.m_lID);
			if (cTmp != null) 
			{
				Iterator iTmp = cTmp.iterator();
				while(iTmp.hasNext())
				{
					info = (OB_UserInfo) iTmp.next();
					if(info.getId()==saId)
					{
						break;
					}
				}
				
			}
			System.out.println("$$$$$$$$$$$$$$$"+info!= null);
			// info = (OB_UserInfo)
			// ob_userDao.findByLoginNo(ci.m_strCode,ci.m_lID);
			if (info != null) {
				System.out.println(info.getId() + "\t" + info.getSName());
				userCondition.setId(info.getId());
				userCondition.setSCertNo(strCertNo);
				userCondition.setSCertCn(strCertCn);
			}
			Collection c_old = ob_userDao.findAccountBySA(ci.m_lID, -1, info.getId());
			 
			HashMap hm_old = new HashMap();
			
			if (c_old != null) {
				Iterator it = c_old.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					hm_old.put(ai.m_strAccountNo, ai.m_strAccountName);
				}
			}

			//
			ob_userDao.deleteAccountOwnedBySA(ci.m_lID);
			ob_userDao.deleteAccountOwnedByUser(info.getId());
			HashMap hm = new HashMap();
			if (strAccountNo != null) {
				Log.print("------------Account's lentth is + "+ strAccountNo.length + "------------");

				for (int i = 0; i < strAccountNo.length; i++) {
					Log.print("--------add a account------------");
					ob_userDao.setUseMaxID();
					ob_userDao.addAccountOwnedBySA(ci.m_lID, info.getId(),strAccountNo[i]);
					ob_userDao.addAccountOwnedByUser(ci.m_lID, info.getId(),strAccountNo[i]);
					if (hm_old.get(strAccountNo[i]) == null) {
						ob_userDao.addAccountPrvg(ob_userDao.getInitAccountPrvgByAccountNo(strAccountNo[i]));
					}
					hm.put(strAccountNo[i], strEbankLoginNo);
				}
			}
			Collection c = ob_userDao.findOBAccountByClient(ci.m_lID);
			if (c != null) {
				Iterator it = c.iterator();
				while (it.hasNext()) {
					AccountInfo ai = (AccountInfo) it.next();
					if (hm.get(ai.m_strAccountNo) == null) {
						ob_userDao.deleteAccountOwnedByClient(ci.m_lID,ai.m_strAccountNo);
						// obSystemDao.addAccountPrvg(
						// obSystemDao.getInitAccountPrvgByAccountNo(ai.m_strAccountNo)
						// );
					}
				}
			}
			//add by wjliu -- 2007-4-19 ���û���Ϣ�޸ĺ�,��״̬Ϊδ����״̬
			userCondition.setNInputUserID(userId);
			userCondition.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			ob_userDao.update(userCondition);
			transConn.commit();
			this.finalizeBean(transConn);

		} catch (Exception e) {
			Log.print("bizlogic-UserAdmin-modifyClientSA failed because "
					+ e.toString());
			try {
				transConn.rollback();
				this.finalizeBean(transConn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			try {
				this.finalizeBean(transConn);
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return info;

	}
/**
 * ��ʼ����������Ա�û����ƹ��ã�
 * @param userList
 * @param collInitUser
 * @param ArrayClientID
 * @param OfficeID
 * @return
 * @throws Exception
 */	
	public long initializeEbankUser(ArrayList userList,Collection collInitUser,long[] ArrayClientID,long OfficeID)throws Exception
	{
		OB_UserInfo userInfo = null;
		Iterator it = null;
		Connection conn = null;
		long clientID = -1;
		TreeMap nameMap = new TreeMap();
		long flag = -1;
		long allClientID = -1;
		OB_GroupInfo queryGroupInfo = null;
		ArrayList groupList = new ArrayList();
		TreeMap userMap = new TreeMap();
		ArrayList accountList = new ArrayList();
		
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);

			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", conn);
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			
			//�����û�������λ��Ϣ
			it = userList.iterator();
			while(it.hasNext())
			{
				userInfo = (OB_UserInfo)it.next();
				clientID = userInfo.getNClientId();
				userMap = this.addEbankAdminUser(userInfo, conn,userMap,ArrayClientID);
			}
			
			nameMap = this.getNameMap(collInitUser, userMap);
			
			if(ArrayClientID!=null)
			{
				ArrayClientID[ArrayClientID.length-1] = clientID;
			}
			else
			{
				ArrayClientID = new long[]{clientID};
			}
			//�����û��鼰�û��û��������ϵ
			for(int i=0;i<ArrayClientID.length;i++)
			{

				allClientID = ArrayClientID[i];
				queryGroupInfo = new OB_GroupInfo();
				queryGroupInfo.setClientId(allClientID);
				groupList = ob_groupDao.findGroupInformationByCondition(queryGroupInfo);
				if(groupList==null)  
				{
					this.addGroupInformation(collInitUser, allClientID, conn, userMap,OfficeID);
				}
				else
				{
					this.addGroupOfUser(allClientID, conn, nameMap);
				}
				accountList = ob_userDao.findAccountByClientID(allClientID);
				if(accountList!=null)  //������ͻ����ʻ���
				{
					this.updateAccountOfUser(accountList, userMap, ArrayClientID[i], conn);
				}
			}
		
			conn.commit();
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			conn.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			this.finalizeBean(conn);
		}
		return flag;
		
	}
/**
 * ��ʼ�������û�(����ƽ̨��)
 * @param userinfo
 * @param ArrayClientID
 * @return
 * @throws Exception
 */
	public long initializeEbankUser(OB_UserInfo userInfo,long[] ArrayClientID)throws Exception
	{

		Connection conn = null;
		long flag = -1;
		long clientID = -1;
		ClientInfo clientInfo = null;
		long lUserID = -1;
		AdminOfUserInfo adminOfUserInfo = new AdminOfUserInfo();		
		try
		{
			
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			AdminOfUserDao adminOfUserDao = new AdminOfUserDao(conn);
			clientID = userInfo.getNClientId();
			//��������Ա�û���Ϣ
			clientInfo = ob_userDao.findClientById(clientID);
			userInfo.setNOfficeID(clientInfo.m_lOfficeID);
			String strPassword = "123456";
			userInfo.setSPassword("123456");
			userInfo.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			userInfo.setNSaid(-1);
			userInfo.setInput(Env.getSystemDateTime());
			userInfo.setDtChangePassword(Env.getSystemDateTime());
			lUserID = ob_userDao.add(userInfo);
			if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))  //���ܴ���
			{
				EncryptManager.getOBBeanFactory().changeOBUserPassword(lUserID, strPassword,conn);
			}
			//�����û���Ϣ���
			
			//��ʼ��¼�û������Ա��λ��Ϣ
			if(ArrayClientID!=null)
			{
				for(int i=0;i<ArrayClientID.length-1;i++)  //��¼������λ������Ϣ
				{
					adminOfUserInfo = new AdminOfUserInfo();
					adminOfUserInfo.setInitialUserID(lUserID);
					adminOfUserInfo.setClientID(ArrayClientID[i]);
					adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.NOTBELONG);
					adminOfUserDao.add(adminOfUserInfo);
				}
			}
			//��¼����λ������Ϣ
			adminOfUserInfo = new AdminOfUserInfo();
			adminOfUserInfo.setInitialUserID(lUserID);
			adminOfUserInfo.setClientID(clientID);
			adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.ISBELONG);
			adminOfUserDao.add(adminOfUserInfo);			
			conn.commit();
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			conn.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			this.finalizeBean(conn);
		}
		return flag;
		
	}
	
	public ArrayList findClientNotSelect(OB_UserInfo userInfo)throws Exception
	{
		ArrayList list = new ArrayList();
		AdminOfUserDao dao = new AdminOfUserDao();
		list = dao.findClientNotSelect(userInfo);
		return list;
		
	}
	
	public boolean whetherHasAdmin(OB_UserInfo userInfo)throws Exception
	{
		boolean hasAdmin = false;
		AdminOfUserDao dao = new AdminOfUserDao();
		hasAdmin = dao.whetherHasAdmin(userInfo);
		return hasAdmin;
	}
	
	public HashMap findAdminInformation(OB_UserInfo userInfo)throws Exception
	{
		HashMap map = new HashMap();
		AdminOfUserDao dao = new AdminOfUserDao();
		map = dao.findAdminInformation(userInfo);
		return map;
		
	}
	
	public ArrayList findAdminUserInformation(OB_UserInfo userInfo)throws Exception
	{
		ArrayList list = new ArrayList();
		AdminOfUserDao dao = new AdminOfUserDao();
		list = dao.findAdminUserInformation(userInfo);
		return list;
		
	}	
	
	public ArrayList findClientSelected(AdminOfUserInfo adminOfUserInfo)throws Exception
	{
		ArrayList list = new ArrayList();
		AdminOfUserDao dao = new AdminOfUserDao();
		list = dao.findClientSelected(adminOfUserInfo);
		return list;
	}
	
	/**
	 * ������������Ա��Ϣ���ƹ��ã�
	 * @param userList
	 * @param collInitUser
	 * @param ArrayClientID
	 * @param OfficeID
	 * @return
	 * @throws Exception
	 */
	public long updateEbankUser(ArrayList userList,Collection collInitUser,long[] ArrayClientID,long OfficeID)throws Exception
	{
		long flag = -1;
		Connection conn = null;
		Iterator it = null;
		OB_UserInfo userInfo = null;
		OB_GroupInfo queryGroupInfo = null;
		TreeMap userMap = new TreeMap();
		ArrayList groupList = new ArrayList();
		TreeMap nameMap = new TreeMap();
		long clientID = -1;
		long lUserID = -1;
		ArrayList accountList = new ArrayList();
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", conn);
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO("OB_Group_Of_User", conn);
			//�޸��û���Ϣ
			it = userList.iterator();
			while(it.hasNext())
			{
				userInfo = (OB_UserInfo)it.next();
				clientID = userInfo.getNClientId();
				userMap = this.updateAdminOfUser(userInfo, userMap, conn, ArrayClientID);
			}
			
			nameMap = this.getNameMap(collInitUser, userMap);
			
			
			if(ArrayClientID!=null)
			{
				ArrayClientID[ArrayClientID.length-1] = clientID;
			}
			else
			{
				ArrayClientID = new long[]{clientID};
			}			
			
			//ɾ������Ա�û�����ؿͻ�������������Ϣ
			it = userList.iterator();
			while(it.hasNext())
			{
				userInfo = (OB_UserInfo)it.next();
				lUserID = userInfo.getId();
				ob_group_of_userDao.del(lUserID);
				
			}
			//�޸��û�����Ϣ
			
			for(int i=0;i<ArrayClientID.length;i++)
			{
				queryGroupInfo = new OB_GroupInfo();
				queryGroupInfo.setClientId(ArrayClientID[i]);
				groupList = ob_groupDao.findGroupInformationByCondition(queryGroupInfo);
				if(groupList == null)
				{
					this.addGroupInformation(collInitUser, ArrayClientID[i], conn, userMap,OfficeID);
				}
				else
				{
					this.addGroupOfUser(ArrayClientID[i], conn, nameMap);
				}
				
				accountList = ob_userDao.findAccountByClientID(ArrayClientID[i]);
				if(accountList!=null)  //�޸ı�����ͻ��ʻ���
				{
					this.updateAccountOfUser(accountList, userMap, ArrayClientID[i], conn);
				}
				
			}		
			
			conn.commit();
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			conn.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally
		{
			this.finalizeBean(conn);
		}
		return flag;
	}
/**
 * �޸���������Ա��Ϣ������ƽ̨��
 * @param userInfo
 * @param ArrayClientID
 * @return
 * @throws Exception
 */
	public long updateEbankUser(OB_UserInfo userInfo,long[] ArrayClientID)throws Exception
	{
		long flag = -1;
		Connection conn = null;
		AdminOfUserInfo adminOfUserInfo = null;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			AdminOfUserDao adminOfUserDao = new AdminOfUserDao(conn);			
			//�޸Ĺ���Ա��Ϣ
			userInfo.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			ob_userDao.update(userInfo);
			
			//ɾ�����û������г�Ա��λ��Ϣ
			adminOfUserInfo = new AdminOfUserInfo();
			adminOfUserInfo.setInitialUserID(userInfo.getId());
			adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.NOTBELONG);
			adminOfUserDao.deleteAdminOfUser(adminOfUserInfo, conn);
			//�����û��³�Ա��λ��Ϣ
			if(ArrayClientID!=null)
			{
				for(int i=0;i<ArrayClientID.length-1;i++)
				{
					adminOfUserInfo = new AdminOfUserInfo();
					adminOfUserInfo.setInitialUserID(userInfo.getId());
					adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.NOTBELONG);
					adminOfUserInfo.setClientID(ArrayClientID[i]);
					adminOfUserDao.setUseMaxID();
					adminOfUserDao.add(adminOfUserInfo);
				}
			}			
			conn.commit();
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			conn.rollback();
			e.printStackTrace();
			throw new IException("�޸��û���Ϣ����",e);
		}finally
		{
			this.finalizeBean(conn);
		}
		return flag;
	}
/**
 * ɾ����������Ա��Ϣ���ƹ�ר�ã�
 * @param userList
 * @return
 * @throws Exception
 */
	public long deleteEbankUser(ArrayList userList)throws Exception
	{
		long flag = -1;
		Connection conn = null;
		Iterator it = null;
		OB_UserInfo userInfo = null;
		AdminOfUserInfo adminOfUserInfo = null;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			AdminOfUserDao adminOfUserDao = new AdminOfUserDao(conn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO("OB_Group_Of_User", conn);
			it = userList.iterator();
			while(it.hasNext())
			{
				//�����û���Ϣ
				userInfo = (OB_UserInfo)it.next();
				userInfo.setDtModify(Env.getSystemDateTime());
				userInfo.setNStatus(SYSConstant.SysCheckStatus.DELETED);
				ob_userDao.update(userInfo);
				
				//ɾ���û������Ա��λ����Ϣ
				adminOfUserInfo = new AdminOfUserInfo();
				adminOfUserInfo.setInitialUserID(userInfo.getId());
				adminOfUserDao.deleteAdminOfUser(adminOfUserInfo, conn);
				
				//ɾ������Ա�û�����ؿͻ�������������Ϣ
				ob_group_of_userDao.del(userInfo.getId());
			}
			conn.commit();
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			conn.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
			
		}finally
		{
			this.finalizeBean(conn);
		}
		return flag;
	}

	/**
	 * ɾ����������Ա��Ϣ������ƽ̨��
	 * @param userList
	 * @return
	 * @throws Exception
	 */
	public long deleteEbankUser(OB_UserInfo userInfo)throws Exception
	{
		long flag = -1;
		Connection conn = null;
		AdminOfUserInfo adminOfUserInfo = null;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			AdminOfUserDao adminOfUserDao = new AdminOfUserDao(conn);
			
			//�����û���Ϣ
			userInfo.setNStatus(SYSConstant.SysCheckStatus.DELETED);
			ob_userDao.update(userInfo);
				
			//ɾ���û������Ա��λ����Ϣ
			adminOfUserInfo = new AdminOfUserInfo();
			adminOfUserInfo.setInitialUserID(userInfo.getId());
			adminOfUserDao.deleteAdminOfUser(adminOfUserInfo, conn);

			conn.commit();
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			conn.rollback();
			e.printStackTrace();
			throw new IException("ɾ������Աʧ��",e);
			
		}finally
		{
			this.finalizeBean(conn);
		}
		return flag;
	}
	
	public String validateLoginNo (ArrayList loginNoList)throws Exception
	{
		String duplicateNo = "";
		String loginNo = "";
		OB_UserDAO ob_userDao = new OB_UserDAO();
		Iterator it = null;
		it = loginNoList.iterator();
		while(it.hasNext())
		{
			loginNo = (String)it.next();
			if (ob_userDao.isDuplicateOBLoginNo(loginNo)) 
			{
				duplicateNo = loginNo;
				break;
			}
			
		}
		
		return duplicateNo;
		
	}
	
	public String validateLoginNo (String strLoginName)throws Exception
	{
		String duplicateNo = "";
		OB_UserDAO ob_userDao = new OB_UserDAO();
		if (ob_userDao.isDuplicateOBLoginNo(strLoginName)) 
		{
			duplicateNo = strLoginName;
		}
		return duplicateNo;
		
	}	
	
	public String validateIdentityCard(ArrayList identityList)throws Exception
	{
		String duplicateIdentityCard = "";
		String identityCard = "";
		OB_UserDAO ob_userDao = new OB_UserDAO();
		Iterator it = null;
		it = identityList.iterator();
		while(it.hasNext())
		{
			identityCard = (String)it.next();
			if(ob_userDao.isDuplicateIdentityCard(identityCard))
			{
				duplicateIdentityCard = identityCard;
				break;
			}
			
			
		}
		return duplicateIdentityCard;
	}
	
	public boolean validateAccount(long lUserID)throws Exception
	{
		boolean bReturn = false;
		OB_UserDAO ob_userDao = new OB_UserDAO();
		bReturn = ob_userDao.validateAccount(lUserID);
		return bReturn;
	}
	/**
	 * ������������Ա(����ƹ�)
	 * @param userList
	 * @return
	 * @throws Exception
	 */
	public long checkAdminUser(ArrayList userList)throws Exception
	{
		long flag = -1;
		Connection conn = null;
		Iterator it = null;
		OB_UserInfo userInfo = null;
		try
		{
			conn = Database.getConnection();
			OB_UserDAO ob_userDao = new OB_UserDAO();
			it = userList.iterator();
			while(it.hasNext())
			{
				userInfo = (OB_UserInfo)it.next();
				userInfo.setCheckDate(Env.getSystemDateTime());
				userInfo.setNStatus(SYSConstant.SysCheckStatus.CHECK);
				ob_userDao.update(userInfo);
			}
			
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally
		{
			this.finalizeBean(conn);
		}
		return flag;
	}
	/**
	 * ������������Ա(����ƽ̨)
	 * @param userList
	 * @return
	 * @throws Exception
	 */
	public long checkAdminUser(OB_UserInfo userInfo)throws Exception
	{
		long flag = -1;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			OB_UserDAO ob_userDao = new OB_UserDAO();
			userInfo.setCheckDate(Env.getSystemDateTime());
			userInfo.setNStatus(SYSConstant.SysCheckStatus.CHECK);
			ob_userDao.update(userInfo);
			flag = 1;
		}catch(Exception e)
		{
			flag = -1;
			e.printStackTrace();
			throw new IException("�����û�ʧ��",e);
		}finally
		{
			this.finalizeBean(conn);
		}
		return flag;
	}
	/**
	 * ������������Ա�ʻ�
	 * @param accountList
	 * @param clientID
	 * @return
	 * @throws Exception
	 */
	public long updateAccountBySA(ArrayList accountList,long[] clientID)throws Exception
	{
		long flag = -1;
		Connection conn = null;
		Iterator it = null;
		AccountOwnedBySAInfo accountInfo = null;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			//��ɾ����Ա��λ�µ������ʻ�
			for(int i=0;i<clientID.length;i++)
			{
				ob_userDao.deleteAccountOwnedBySA(clientID[i]);
			}
			it = accountList.iterator();

			while(it.hasNext())
			{
				accountInfo = new AccountOwnedBySAInfo();
				accountInfo = (AccountOwnedBySAInfo)it.next();
				ob_userDao.addAccountOwnedBySA(accountInfo.getNClientID(),accountInfo.getNSAID(),accountInfo.getSAccountNo());
				ob_userDao.addAccountPrvg(ob_userDao.getInitAccountPrvgByAccountNo(accountInfo.getSAccountNo()));
			}
			
			conn.commit();
			flag = 1;
		}catch(Exception e)
		{
			conn.rollback();
			e.printStackTrace();
			flag = -1;
			throw new Exception(e.getMessage());
		}
		finally
		{
			this.finalizeBean(conn);
		}
		return flag;
	}

	public Collection getLastPrivilege() throws IException {
		Collection c = null;
		OB_PrivilegeDAO dao = new OB_PrivilegeDAO();

		try {
			c = (Collection) dao.getLastPrivilege();
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("getAllPrivilegeʱ�����ڲ������������Ա��ϵ");
		}

		return c;
	}	
	
	public Collection getLastPrivilegesByTopMenu(Collection cAllPvg, int lTopMenuID) {
		Vector v = new Vector();
		try {
			if (cAllPvg != null) {
				Iterator it = cAllPvg.iterator();
				while (it.hasNext()) {
					OB_PrivilegeInfo pvg = (OB_PrivilegeInfo) it.next();
					String strModule = pvg.getPrivilegeNo().substring(0, 4);
					if (strModule.indexOf(String.valueOf(lTopMenuID)) >= 0) {
						v.addElement(pvg);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getPrivilegesByModule error because "
					+ e.toString());

		}
		return (v.size() > 0 ? v : null);
	}	
	
	public TreeMap getInitialClientByXML()throws Exception
	{
		TreeMap map = new TreeMap();
		String strUserName = "";
		String strUserNumber = "";
		boolean status = false;
		InitUserXml initUserXml = new InitUserXml();
    	Collection collInitUser = initUserXml.getIniUserInfo();
    	Iterator itInitUser = collInitUser.iterator();
    	InitUserInfo initUserInfo = null;
    	
    	while (itInitUser.hasNext()) {
    		initUserInfo = (InitUserInfo) itInitUser.next();
    		strUserName = initUserInfo.getUserName();
    		strUserNumber =String.valueOf(initUserInfo.getUserNumber());
       		status = initUserInfo.isStatus();
    		if(status)
    		{
    			if(map.get(strUserNumber)==null)
    			{
    				if(initUserInfo.getUserNumber()>0)
    				{
    					map.put(strUserNumber,strUserName);
    				}
    			}
    		}
    	}    	
		return map;
	}
	
	public void addGroupInformation(Collection collInitUser,long clientID,Connection conn,TreeMap userMap,long officeID)throws Exception
	{
		InitUserInfo initInfo = null;
		Iterator groupIterator = null;
		long lUserNumber = -1;
		String strGroupName = "";
		long modelID = -1;
		boolean blStatus = false;
		Collection collPvg = null;
		OB_GroupInfo groupCondition = null;
		long lGroupID = -1;
		Iterator itPvg = null;
		String strPvgNo = "";
		OB_Privilege_Of_GroupInfo ob_Privilege_Of_GroupInfo = null;
		Long tempUserID = null;
		long lUserID = -1;
		OB_Group_Of_UserInfo group_userCondition = null;
		try
		{
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", conn);
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO("ob_privilege", conn);
			OB_Privilege_Of_GroupDAO ob_pribilege_of_groupDao = new OB_Privilege_Of_GroupDAO("ob_privilege_of_group", conn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO("OB_Group_Of_User", conn);
			groupIterator = collInitUser.iterator();
			while(groupIterator.hasNext())
			{
				initInfo = (InitUserInfo)groupIterator.next();
				lUserNumber = initInfo.getUserNumber();
				strGroupName = initInfo.getGroupName();
				modelID = initInfo.getModuleId();
				blStatus = initInfo.isStatus();
				collPvg = initInfo.getPrivileges();
				if(blStatus)
				{
					groupCondition = new OB_GroupInfo();
					if(strGroupName.indexOf("ϵͳ������")>-1||strGroupName.indexOf("ϵͳ��ȫ��")>-1)
					{
						groupCondition.setIsAdminGroup(1);
					}
					else
					{
						groupCondition.setIsAdminGroup(0);
					}
					groupCondition.setName(strGroupName);
					groupCondition.setModuleID(modelID);
					groupCondition.setClientId(clientID);
					groupCondition.setInputUserID(-1);
					groupCondition.setInputDate(Env.getSystemDateTime());	
					groupCondition.setOfficeID(officeID);
				//	ob_groupDao.setUseMaxID();
					lGroupID = ob_groupDao.add(groupCondition);  
					//�û�����Ϣ�������
					
					//������Ȩ����Ϣ
					itPvg = collPvg.iterator();
					while(itPvg.hasNext())
					{
						strPvgNo = (String)itPvg.next();
						ob_Privilege_Of_GroupInfo = new OB_Privilege_Of_GroupInfo();
						ob_Privilege_Of_GroupInfo.setGroupID(lGroupID);
						ob_Privilege_Of_GroupInfo.setPrivilegeID(ob_privilegeDao.findPrivilegeIdByNo(strPvgNo));
						//ob_pribilege_of_groupDao.setUseMaxID();
						ob_pribilege_of_groupDao.add(ob_Privilege_Of_GroupInfo);
					}
					
					if(lUserNumber>0)
					{
						//�����û����û����������
						tempUserID = (Long)userMap.get(new Long(lUserNumber));
						lUserID = tempUserID.longValue();
						group_userCondition = new OB_Group_Of_UserInfo();
						group_userCondition.setGroupID(lGroupID);
						group_userCondition.setUserID(lUserID);
						group_userCondition.setClientID(clientID);
						//ob_group_of_userDao.setUseMaxID();
						ob_group_of_userDao.add(group_userCondition);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	public TreeMap addEbankAdminUser(OB_UserInfo userInfo,Connection conn,TreeMap userMap,long[] ArrayClientID) throws Exception
	{
		long clientID = -1;
		String loginNo = "";
		ClientInfo clientInfo = null;
		long lUserID = -1;
		AdminOfUserInfo adminOfUserInfo = new AdminOfUserInfo();
		try
		{
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			AdminOfUserDao adminOfUserDao = new AdminOfUserDao(conn);
			clientID = userInfo.getNClientId();
			loginNo = userInfo.getSLoginNo();
			
			//У���û���Ϣ
			/* ��½�ſ����ظ�
			if (ob_userDao.isDuplicateOBLoginNo(loginNo)) 
			{
				throw new IException("�û���¼���ظ�");
			}	
			*/		
			//У���û���Ϣ���
			//�����û���Ϣ
			clientInfo = ob_userDao.findClientById(clientID);
			userInfo.setNOfficeID(clientInfo.m_lOfficeID);
			String strPassword = "123456";
			userInfo.setExchangePassword(strPassword);
			userInfo.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			userInfo.setNSaid(-1);
			userInfo.setInput(Env.getSystemDateTime());
			userInfo.setDtChangePassword(Env.getSystemDateTime());
			//ob_userDao.setUseMaxID();//������ͳһ��ȡSEQ_OB_USER
			lUserID = ob_userDao.add(userInfo);
			userMap.put(new Long(userInfo.getNUserNumber()), new Long(lUserID));
			if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))  //���ܴ���
			{
				EncryptManager.getOBBeanFactory().changeOBUserPassword(lUserID, strPassword,conn);
			}			
			//�����û����
			
			//��ʼ��¼�û������Ա��λ��Ϣ
			if(ArrayClientID!=null)
			{
				for(int i=0;i<ArrayClientID.length-1;i++)  //��¼������λ������Ϣ
				{
					adminOfUserInfo = new AdminOfUserInfo();
					adminOfUserInfo.setInitialUserID(lUserID);
					adminOfUserInfo.setClientID(ArrayClientID[i]);
					adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.NOTBELONG);
					adminOfUserDao.setUseMaxID();
					adminOfUserDao.add(adminOfUserInfo);
				}
			}
			//��¼����λ������Ϣ
			adminOfUserInfo = new AdminOfUserInfo();
			adminOfUserInfo.setInitialUserID(lUserID);
			adminOfUserInfo.setClientID(clientID);
			adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.ISBELONG);
			adminOfUserDao.setUseMaxID();
			adminOfUserDao.add(adminOfUserInfo);			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception();
		}
		return userMap;
	}
	
	public void addGroupOfUser(long clientID,Connection conn,TreeMap nameMap)throws Exception
	{
		ArrayList groupList = new ArrayList();
		Long tempUserID = null;
		long lUserID = -1;
		OB_Group_Of_UserInfo group_userCondition = null;
		try
		{
			
			OB_GroupDAO ob_groupDao = new OB_GroupDAO("OB_group", conn);
			OB_Group_Of_UserDAO ob_group_of_userDao = new OB_Group_Of_UserDAO("OB_Group_Of_User", conn);
			OB_GroupInfo queryGroupInfo = new OB_GroupInfo();
			queryGroupInfo.setClientId(clientID);
			queryGroupInfo.setIsAdminGroup(1);
			groupList = ob_groupDao.findGroupInformationByCondition(queryGroupInfo);
			if(groupList!=null)
			{
				Iterator groupIt = groupList.iterator();
				while(groupIt.hasNext())
				{
					OB_GroupInfo groupInfo = (OB_GroupInfo)groupIt.next();
					tempUserID = (Long)nameMap.get(groupInfo.getName());
					lUserID = tempUserID.longValue();	
					group_userCondition = new OB_Group_Of_UserInfo();
					group_userCondition.setUserID(lUserID);
					group_userCondition.setGroupID(groupInfo.getId());
					group_userCondition.setClientID(clientID);
					if(!ob_group_of_userDao.findSameGroupOfUser(group_userCondition))
					{
						//ob_group_of_userDao.setUseMaxID();
						ob_group_of_userDao.add(group_userCondition);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	public TreeMap getNameMap(Collection collInitUser,TreeMap userMap)
	{
		TreeMap nameMap = new TreeMap();
		InitUserInfo initInfo = new InitUserInfo();
		Iterator groupIterator = collInitUser.iterator();
		long lUserNumber = -1;
		String strGroupName = "";
		Long tempUserID = null;
		long lUserID = -1;
		while(groupIterator.hasNext())
		{
			initInfo = (InitUserInfo)groupIterator.next();
			lUserNumber = initInfo.getUserNumber();
			strGroupName = initInfo.getGroupName();
			if(lUserNumber>0)
			{
				tempUserID = (Long)userMap.get(new Long(lUserNumber));
				lUserID = tempUserID.longValue();
				nameMap.put(strGroupName, new Long(lUserID));
			}
		}
		return nameMap;
	}
	
	public TreeMap updateAdminOfUser(OB_UserInfo userInfo,TreeMap userMap,Connection conn,long[] ArrayClientID)throws Exception 
	{
		long lUserID = -1;
		long lUserNumber = -1;
		AdminOfUserInfo adminOfUserInfo = null;
		try
		{
			//�޸��û���Ϣ
			OB_UserDAO ob_userDao = new OB_UserDAO(conn);
			AdminOfUserDao adminOfUserDao = new AdminOfUserDao(conn);
			lUserID = userInfo.getId();
			lUserNumber = userInfo.getNUserNumber();
			userMap.put(new Long(lUserNumber), new Long(lUserID));
			userInfo.setDtModify(Env.getSystemDateTime());
			userInfo.setNStatus(SYSConstant.SysCheckStatus.UNCHECK);
			ob_userDao.update(userInfo);
			//�û���Ϣ�޸����
			
			//ɾ�����û������г�Ա��λ��Ϣ
			adminOfUserInfo = new AdminOfUserInfo();
			adminOfUserInfo.setInitialUserID(userInfo.getId());
			adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.NOTBELONG);
			adminOfUserDao.deleteAdminOfUser(adminOfUserInfo, conn);
			//�����û��³�Ա��λ��Ϣ
			if(ArrayClientID!=null)
			{
				for(int i=0;i<ArrayClientID.length-1;i++)
				{
					adminOfUserInfo = new AdminOfUserInfo();
					adminOfUserInfo.setInitialUserID(userInfo.getId());
					adminOfUserInfo.setIsBelongToClient(SYSConstant.belongToClient.NOTBELONG);
					adminOfUserInfo.setClientID(ArrayClientID[i]);
					adminOfUserDao.setUseMaxID();
					adminOfUserDao.add(adminOfUserInfo);
				}
			}			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return userMap;
	}
	
	public void updateAccountOfUser(ArrayList accountList,TreeMap userMap,long clientID,Connection conn)
	{
		Long tempUserID = null;
		long newUserID = -1;
		int lCount = 0;
		long oldUserID = -1;
		AccountOwnedBySAInfo info = null;
		try
		{
			OB_UserDAO userDao = new OB_UserDAO(conn);
			Set set= userMap.entrySet();
			Iterator it = set.iterator();
			while(it.hasNext())
			{
				
				Map.Entry entry = (Map.Entry)it.next();
				tempUserID = (Long)entry.getValue();
				newUserID = tempUserID.longValue();
				info = (AccountOwnedBySAInfo)accountList.get(lCount);
				oldUserID = info.getNSAID();
				userDao.updateAccountUser(clientID, oldUserID, newUserID);
				lCount++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList findAuthorizedUser(long clientID) throws Exception
	{
		OB_UserDAO userDao = new OB_UserDAO();
		ArrayList list = new ArrayList();
		list = userDao.findAuthorizedUser(clientID);
		return list;
	}
	

	
}