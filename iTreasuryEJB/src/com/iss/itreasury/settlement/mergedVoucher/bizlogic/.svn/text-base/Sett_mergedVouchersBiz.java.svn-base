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

	// 成员变量
	//private Connection conn = null; // 数据库连接

	// 构造函数
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

	// 插入一条记录
	public long add(MergedVoucherInfo form) throws RemoteException, IException {
		long result = -1;
		// 开始处理
		try {
			
			// 新建操作数据库的DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
			//插入一条记录
			result = dao.add(form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}

		// 返回函数值
		return result;
	}

	//通过批次号得到对象数组
	public Collection findBySbatchno(String sbatchno) throws RemoteException, IException {

		//定义变量
		Collection col = null;
		
		// 新建操作数据库的DAO
		Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
		try {
			col = dao.findBySbatchno(sbatchno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		
		//返回函数值
		return col;

	}

	// 根据批次号删除对应的记录（物理删除）
	public void delBySbatchno(String sbatchno) throws RemoteException, IException {
		// 开始处理
		try {

//			 新建操作数据库的DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
			dao.delBySbatchno(sbatchno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
	}

	
//	 查询已经合并的批次号通过查询条件来查找,返回PageLoader
	public PageLoader findByCondition(MergedVoucherInfo form) throws RemoteException, IException {

		// 定义变量
		PageLoader loader = null;

		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();

			// 获取说有记录
			loader = dao.findByAll(form);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}

		return loader;
	}
	
//	 查询已经合并的批次号通过查询条件来查找，返回集合 
	public Collection findAllByMergedInfo(MergedVoucherInfo form) throws RemoteException, IException{
		
		// 定义变量
		Collection findAllByInfoColl = null;
		
		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_mergedVouchersDAO dao = new Sett_mergedVouchersDAO();
			
			// 获取说有记录
			findAllByInfoColl = dao.findAllByMergedInfo(form);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		return findAllByInfoColl;
	}
	
/**	// 获取要修改的记录
	public AccountDeadLineInfo findById(long id)  throws RemoteException, IException {

		// 定义变量
		AccountDeadLineInfo adli = null;

		// 开始处理
		try {
			// 新建操作数据库的DAO
			AccountDeadLineDAO dao = new AccountDeadLineDAO();

			// 获取要编辑的数据
			adli = (AccountDeadLineInfo) dao.findByID(id, (new AccountDeadLineInfo()).getClass());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回函数值
		return adli;
	}

	// 修改指定的记录
	public boolean update(AccountDeadLineInfo form) {

		// 开始处理
		try {
			// 新建操作数据库的DAO
			AccountDeadLineDAO dao = new AccountDeadLineDAO();

			// 更新数据
			dao.update(form);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 返回函数值
		return true;
	}**/

}
