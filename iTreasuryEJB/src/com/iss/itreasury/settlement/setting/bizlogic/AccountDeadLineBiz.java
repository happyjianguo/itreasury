/**
 * 
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.settlement.setting.dao.AccountDeadLineDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountDeadLineInfo;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/** 
 * @author zyzhu
 * 
 */
public class AccountDeadLineBiz {

	// 成员变量
	private Connection conn = null; // 数据库连接

	// 构造函数
	public AccountDeadLineBiz() {
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

	// 插入一条记录
	public boolean add(AccountDeadLineInfo form) {

		// 开始处理
		try {
			
			// 新建操作数据库的DAO
			AccountDeadLineDAO dao = new AccountDeadLineDAO();
			
			//插入一条记录
			dao.add(form);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 返回函数值
		return true;
	}

	//通过账号ID查询账户限额设置表
	public Collection findByAccountId(long lAccountId) throws SQLException {

		//定义变量
		Collection col = null;
		
		// 新建操作数据库的DAO
		AccountDeadLineDAO dao = new AccountDeadLineDAO();
		col = dao.findByAccountId(lAccountId);
		
		//返回函数值
		return col;

	}

	// 获取账户限额设置记录列表
	public Collection findByCondition() {

		// 定义变量
		Collection colList = null;

		// 开始处理
		try {

			// 新建查询条件的表单
			AccountDeadLineInfo form = new AccountDeadLineInfo();
			form.setStatusId((int) Constant.RecordStatus.VALID);
		    //form.setOfficeId();
          
			// 新建操作数据库的DAO
			AccountDeadLineDAO dao = new AccountDeadLineDAO();

			// 获取说有记录
			colList = dao.findByCondition(form, " order by id ");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return colList;
	}

	
//	 获取账户限额设置记录列表
	public Collection findByCondition(AccountDeadLineInfo form) {

		// 定义变量
		Collection colList = null;

		// 开始处理
		try {

			// 新建查询条件的表单
			form.setStatusId((int) Constant.RecordStatus.VALID);
			
		    //form.setOfficeId();
			Connection	transConn = Database.getConnection();
			// 新建操作数据库的DAO
			AccountDeadLineDAO dao = new AccountDeadLineDAO();

			// 获取说有记录
			colList = dao.findByAll(form);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return colList;
	}
	
	// 获取要修改的记录
	public AccountDeadLineInfo findById(long id) {

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
	}

}
