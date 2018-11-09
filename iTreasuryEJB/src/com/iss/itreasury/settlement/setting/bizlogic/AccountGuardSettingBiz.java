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

	// 成员变量
	private Connection conn = null; // 数据库连接

	// 构造函数
	public AccountGuardSettingBiz() {
		try {
			conn = Database.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 通过账号Id查询记录
	public Collection findByAccountId(long lAccountId) throws SQLException {

		// 定义变量
		Collection col = null;

		// 新建操作数据库的DAO
		Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();
		col = dao.findByAccountId(lAccountId);

		// 返回函数值
		return col;

	}

	// 插入一条记录
	public boolean add(AccountGuardSettingInfo form) {

		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();
			// 插入一条记录
			dao.add(form);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 返回函数值
		return true;
	}

	// 获取账户限额设置记录列表
	public Collection findByCondition(AccountGuardSettingInfo form) {

		// 定义变量
		Collection colList = null;

		// 开始处理
		try {

			// 新建查询条件的表单
			
			form.setStatusId(Constant.RecordStatus.VALID);
			// 新建操作数据库的DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();

			// 获取说有记录
			colList = dao.findByAll(form);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return colList;
	}

	// 获取要修改的记录
	public AccountGuardSettingInfo findById(long id) {

		// 定义变量
		AccountGuardSettingInfo adli = null;

		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();

			// 获取要编辑的数据
			adli = (AccountGuardSettingInfo) dao.findByID(id, (new AccountGuardSettingInfo())
					.getClass());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回函数值
		return adli;
	}

	// 修改指定的记录
	public boolean update(AccountGuardSettingInfo form) {

		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_AccountGuardSettingDAO dao = new Sett_AccountGuardSettingDAO();

			// 更新数据
			dao.update(form);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 返回函数值
		return true;
	}

}
