package com.iss.itreasury.clientmanage.client.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.clientmanage.client.dao.ClientDAO;
import com.iss.itreasury.clientmanage.client.dao.CorporationDAO;
import com.iss.itreasury.clientmanage.client.dao.NatureDAO;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.clientmanage.client.dataentity.NatureInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryClientInfo;
import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;
import com.iss.itreasury.command.Command;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.system.dao.PageLoader;

public class ClientCmd extends Command {

	public void update1(ClientInfo dataEntity, CorporationInfo dataEntity2)
			throws Exception {
		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);

		try {
			System.out.println("<><><>无重名<><><>");
			if(clientdao.isClientNoRepeat(dataEntity)){
				throw new IException("客户编号已存在！");
			}
			long levelId = clientdao.getlevelId(dataEntity2);
			String levelCode = clientdao.getlevelCode(dataEntity2, levelId);
			dataEntity.setLevelId(levelId);
			dataEntity.setLevelCode(levelCode);
			System.out
					.println("levelId:" + levelId + ";levelCode:" + levelCode);
			// 更新信息
			clientdao.update(dataEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("客户编号已存在！");
		
		} finally {
			clearConn(con);
		}

	}

	/**
	 * 新增客户信息
	 * 
	 * @throws Exception
	 */
	public long add(ClientInfo dataEntity) throws Exception {
		// 如果是法人客户，判断客户名称是否已经存在,如果存在抛出异常
		long myid = -1;
		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);

		if (dataEntity.clientBaseType.equals(CMConstant.ClientBaseType.CORPORATION))// 法人
		{
			if (!clientdao.isNameRepeat(dataEntity)) {
				// 法人客户名称不存在，产生客户编号，保存信息
				String cold = clientdao.generateClientNo(dataEntity);
				dataEntity.setCode(cold);
				clientdao.setUseMaxID();
				myid = clientdao.add(dataEntity);
			} else {
				throw new IException("此法人客户中文名称已存在!!");
			}
		} else {
			String cold = clientdao.generateClientNo(dataEntity);
			dataEntity.setCode(cold);
			clientdao.setUseMaxID();
			myid = clientdao.add(dataEntity);
		}

		if (con != null) {
			con.close();
			con = null;
		}
		return myid;

	}

	public long addClient(ClientInfo dataEntity) throws Exception {
		// 如果是法人客户，判断客户名称是否已经存在,如果存在抛出异常
		long myid = -1;
		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);

		if (dataEntity.clientBaseType.equals(CMConstant.ClientBaseType.CORPORATION))// 法人
		{
			if (!clientdao.isNameRepeat(dataEntity)) {
				// 法人客户名称不存在，产生客户编号，保存信息
				String cold = clientdao.genClientNo(dataEntity);
				dataEntity.setCode(cold);
				clientdao.setUseMaxID();
				myid = clientdao.add(dataEntity);
			} else {
				throw new IException("此法人客户中文名称已存在!!");
			}
		} else {
			String cold = clientdao.genClientNo(dataEntity);
			dataEntity.setCode(cold);
			clientdao.setUseMaxID();
			myid = clientdao.add(dataEntity);
		}

		if (con != null) {
			con.close();
			con = null;
		}
		return myid;
	}

	/**
	 * 更新一条客户信息，因为新增客户的时候，客户编号可以修改， 所以需要判断编号是否已经存在，是否符合编号规则
	 * 
	 * @author
	 * @param
	 * @throws ITreasuryException
	 */
	public void update(ClientInfo dataEntity) throws Exception {
		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);
		// 判断客户编号是否已经存在，存在则抛出异常
		if(clientdao.isClientNoRepeat(dataEntity)
				&& dataEntity.getCode().trim().length() != 0){
			throw new IException("客户编号已存在!!");
		}
		if (!clientdao.isClientNoRepeat(dataEntity)
				|| dataEntity.getCode().trim().length() == 0) {
			//法人
			if (dataEntity.clientBaseType.equals(CMConstant.ClientBaseType.CORPORATION)) {

				// 如果是企业客户，判断客户名称是否已经存在，存在抛出异常
				if (!clientdao.isNameRepeat(dataEntity)) {
					// 更新信息
					clientdao.update(dataEntity);
				} else {
					throw new IException("此法人名已存在!!");
				}

			} else {
				clientdao.update(dataEntity);
			}
		}

		if (con != null) {
			con.close();
			con = null;
		}

	}

	public void delete(long id) throws Exception {

		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);
		ClientInfo clientinfo = new ClientInfo();

		clientinfo = (ClientInfo) clientdao.findByID(id, clientinfo.getClass());

		// 首先判断是否引用信息,如果有引用信息，则抛出异常
		if (!clientdao.isInUse(clientinfo))// 如果没有被引用
		{
			// 删除客户信息
			clientdao.delete(id);
		} else// 如果有被引用
		{
		}
		if (con != null) {
			con.close();
			con = null;
		}

	}
	
	/*
	 * 查询办事中是否已经存在财务公司
	 * liuguang
	 */
	public long findCorpID(CorporationInfo corinfo,ClientInfo finfo) throws Exception {
		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);
		long id = -1;

		id = clientdao.findCorpID(corinfo, finfo);
		if (con != null) {
			con.close();
			con = null;
		}
		return id;
	}

	/*
	 * 根据id进行查询
	 */

	public ClientInfo findbyid(long id) throws Exception {

		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);
		ClientInfo clientinfo = new ClientInfo();

		clientinfo = (ClientInfo) clientdao.findByID(id, clientinfo.getClass());
		if (con != null) {
			con.close();
			con = null;
		}

		return clientinfo;
	}

	/**
	 * 根据客户编号，得到一条客户信息以及他的子信息
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	public CimsBaseDataEntity load(long id) throws Exception {

		Connection con = null;
		con = Database.getConnection();
		ClientDAO clientdao = new ClientDAO(con);
		ClientInfo clientinfo = new ClientInfo();

		// 通过findById获得ClientDataEntity
		clientinfo = (ClientInfo) clientdao.findByID(id, clientinfo.getClass());
		// 判断其为法人客户或者自然人客户
		if (clientinfo.clientBaseType.equals(CMConstant.ClientBaseType.CORPORATION))// 法人
		{
			// 如果是法人客户，获得CorporationDataEntity,将ClientDataEntity赋值
			// 给CorporationDataEntity后返回
			CorporationDAO corporationdao = new CorporationDAO(con);
			CorporationInfo corporationinfo = new CorporationInfo();
			corporationinfo = (CorporationInfo) corporationdao
					.findByclietID(id);
			corporationinfo.setClientInfo(clientinfo);
			if (con != null) {
				con.close();
				con = null;
			}
			return corporationinfo;
		} else if (clientinfo.clientBaseType.equals(CMConstant.ClientBaseType.NATURE))// 个人
		{
			// 如果是自然人客户，获得NatureDataEntity,将ClientDataEntity赋值
			// 给NatureDataEntity后返回

			NatureDAO naturedao = new NatureDAO(con);
			NatureInfo natureinfo = new NatureInfo();
			natureinfo = (NatureInfo) naturedao.findByclietID(id);
			natureinfo.setClientInfo(clientinfo);
			if (con != null) {
				con.close();
				con = null;
			}
			return natureinfo;
		} else {
			if (con != null) {
				con.close();
				con = null;
			}
			return null;
		}

	}

	/**
	 * 根据查询条件获取结果集
	 * 
	 * @throws Exception
	 */
	public Collection findByCondition(ClientInfo dataEntity) throws Exception {
		Collection c = null;
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			c = clientdao.findByCondition(dataEntity);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (c);
	}

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	public Collection findDetailById(long lId) throws Exception {
		Collection c = null;
		try {
			c = new ClientDAO().findDetailById(lId);
		} catch (Exception e) {
			throw e;
		}
		return (c);
	}

	/**
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws Exception
	 */
	public Vector findByCustomerManagerUserID(long CustomerManagerUserID)
			throws Exception {
		Vector v = new Vector();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			v = clientdao.find(CustomerManagerUserID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (v);
	}

	/**
	 * 客户转移更新客户经理id
	 * 
	 * @param args
	 * @throws Exception
	 * @throws Exception
	 */
	public long updateManager(ClientInfo dataEntity) throws Exception {
		long lReturn = -1;
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			lReturn = clientdao.updateManager(dataEntity);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return lReturn;
	}

	/**
	 * 根据Code查询 查存贷款
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findaccount(long ClientID, long[] type) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findaccount(ClientID, type);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 根据Code查询 查合同
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findloan(long ClientID, long[] type) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findloan(ClientID, type);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 根据Code查询 查保函
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findsaveletter(String Code) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findsaveletter(Code);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 根据Code查询 查财务报表
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findreport(long ClientID) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findreport(ClientID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 根据Code查询 查财务指标
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findtarget(long ClientID) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findtarget(ClientID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 根据Code查询 查信用金额
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findCredit(String Code) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findCredit(Code);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 根据Code查询 查预算
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findbudget(long ClientID, long budgetflag)
			throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);

			Info = clientdao.findbudget(ClientID, budgetflag);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 根据Code查询 查预算金额
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findbudgetmoney(long ClientID) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findbudgetmoney(ClientID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ClientInfo findClientBaseType(String Code) throws Exception {
		ClientInfo Info = new ClientInfo();
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			Info = clientdao.findClientBaseType(Code);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return (Info);
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public long findclientid(long ID) throws Exception {
		long ClientID = -1;
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			ClientID = clientdao.findclientid(ID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return ClientID;
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public long findNclient(long ID) throws Exception {
		long ClientID = -1;
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			ClientID = clientdao.findNclient(ID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return ClientID;
	}

	public String findmanager(long CustomerManagerUserID) throws Exception {
		String sname = null;
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			sname = clientdao.findmanager(CustomerManagerUserID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return sname;
	}

	public String findOffice(long OfficeID) throws Exception {
		String sname = null;
		Connection con = null;
		ClientDAO clientdao = null;

		try {
			con = Database.getConnection();
			clientdao = new ClientDAO(con);
			sname = clientdao.findOffice(OfficeID);
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
		return sname;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String parentName(long id) throws Exception {
		String str = "";
		Connection con = null;
		try {
			con = Database.getConnection();
			str = new ClientDAO(con).parentName(id);
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			clearConn(con);
		}
	}

	public Collection findByMultiOption(QueryClientInfo qInfo) {
		ArrayList v = new ArrayList();
		try {
			ClientDAO dao = new ClientDAO();
			v = (ArrayList) dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	public PageLoader findClientInfos(QueryClientInfo qInfo)
	{
		PageLoader pageLoader = null;
		String sql = "";
		try 
		{
			ClientDAO dao = new ClientDAO();
			sql = dao.getQueryClientInfo(qInfo);
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(new AppContext(), sql, "*", "", (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.clientmanage.client.dataentity.QueryClientInfo", null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageLoader;
	}
	
	public Collection findLogByClientID(QueryClientInfo qInfo, long officeId) {
		ArrayList v = new ArrayList();
		try {
			ClientDAO dao = new ClientDAO();
			v = (ArrayList) dao.findLogByClientID(qInfo, officeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public void modify(long id, long parentCorpID1) throws Exception {
		Vector v = new Vector();
		Connection con = null;
		try {
			con = Database.getConnection();
			ClientDAO clientdao = new ClientDAO(con);
			CorporationDAO cordao = new CorporationDAO(con);
			CorporationInfo minfo = new CorporationInfo();
			// 更新
			minfo.setParentCorpID1(parentCorpID1);
			long levelId = clientdao.getlevelId(minfo);
			String levelCode = clientdao.getlevelCode(minfo, levelId);
			ClientInfo cInfo = new ClientInfo();
			cInfo.setId(id);
			cInfo.setLevelId(levelId);
			cInfo.setLevelCode(levelCode);
			clientdao.update(cInfo);
			cordao.updateParent(id, parentCorpID1);
			v = clientdao.getList(id);
			if (v != null && v.size() > 0) {
				for (int i = 0; i < v.size(); i++) {
					ClientInfo info = (ClientInfo) v.get(i);
					this.modify(info.getId(), id);
				}
			}
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ClientCmd onecmd = new ClientCmd();
		CimsBaseDataEntity oneinfo = null;

		Collection aa = null;

		oneinfo = (CimsBaseDataEntity) onecmd.load(47);

		System.out.println(oneinfo.getClass());
		System.out.println(oneinfo.getId());

	}

}