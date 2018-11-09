package com.iss.itreasury.evoucher.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dao.ClientRightSetDao;
import com.iss.itreasury.evoucher.setting.dataentity.ClientRightEntity;
import com.iss.itreasury.evoucher.setting.dataentity.SignatureEntity;
import com.iss.itreasury.evoucher.util.VOUConstant.EleDocsSet;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.SessionMng;
import com.iss.system.dao.PageLoader;

public class ClientRigtSetBiz {

	/**
	 * 查找信息
	 * 
	 * @author zhanglei
	 * @param ce
	 * @return
	 * @throws SQLException
	 */
	public PageLoader findInfo(ClientRightEntity ce) throws SQLException {
		long nissignature = ce.getNissignature();
		long startClientNo = ce.getStartClientNo();
		long endClientNo = ce.getEndClientNo();
		String startClientCode = ce.getStartClientCode();
		String endClientCode = ce.getEndClientCode();
		long nofficeid = ce.getNofficeid();
		String condition = " 1=1 ";
		if (startClientCode != null&&!"".equals(startClientCode))
			condition += "  and  a.code>='" + startClientCode+"'";
		if (endClientCode != null&&!"".equals(endClientCode)) {
			condition += "  and  a.code<='" + endClientCode+"'";
		}
		if (startClientCode != null&&!"".equals(startClientCode)&&endClientCode != null&&!"".equals(endClientCode)) {
			if (startClientCode.compareTo(endClientCode)<0)
				condition = "  a.code between  '" + startClientCode + "' and '"
						+ endClientCode+"'";
			else
				condition = "  a.code between  '" + endClientCode + "' and '"
						+ startClientCode+"'";
		}

		String strNot = nissignature == 0 ? " not " : "";
		String strWhere = strNot
				+ " exists"
				+ "	(select b.nclientid from sett_signature b  where a.id=b.nclientid  and b.NISSIGNATURE="
				+ EleDocsSet.HASSETRIGHT + ")"
				+ " and  a.officeid=c.id and    a.statusid=1 and a.officeid="+nofficeid+" and    " + condition;
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						" client_clientinfo a ,office c",
						"a.id as nclientid,a.name as clientName,a.code as clientCode,a.officeid as nofficeid, "
								+ nissignature
								+ " as nissignature,c.sname as nofficename,'"+startClientCode+"' as startClientCode,'"+endClientCode+"' as endClientCode",
						strWhere,
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.evoucher.setting.dataentity.ClientRightEntity",
						null);
		return pageLoader;
	}

	/**
	 * 批量授权
	 * 
	 * @author zhanglei
	 * @param ids
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 * @throws VoucherException 
	 */
	public void setClientRight(String[] ids,long nputuserid) throws ITreasuryDAOException,
			SQLException, VoucherException {
		Connection con = null;
		try {
			con = Database.getConnection();
			ClientRightSetDao dao = new ClientRightSetDao(con);
			dao.setUseMaxID();
			for (int i = 0; i < ids.length; i++) {
				long officeId = Long.parseLong(ids[i].split("__")[1]);
				long id = Long.parseLong(ids[i].split("__")[0]);
				SignatureEntity se = new SignatureEntity();
				se.setNclientid(id);
				se.setNissignature(1);
				se.setNofficeid(officeId);
				se.setInputuserid(nputuserid + "");
				se.setDtinputdate(new Date());
				dao.add(se);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new VoucherException("Gen_E001", e);
		} finally {
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}

	/**
	 * 撤销授权
	 * 
	 * @author zhanglei
	 * @param ids
	 * @throws Exception
	 */
	public void removeRight(String[] ids) throws Exception {
		Connection con = null;
		try {
			con = Database.getConnection();
			ClientRightSetDao dao = new ClientRightSetDao(con);
			dao.batchRemove(ids);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			throw new VoucherException("Gen_E001", e);
		} finally {
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}
}
