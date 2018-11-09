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

	// 成员变量
	private Connection conn = null; // 数据库连接

	// 构造函数
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

	// 插入一条记录
	public boolean add(TransAccountGuardInfo form) {

		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();
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
	public Collection findByCondition() {

		// 定义变量
		Collection colList = null;

		// 开始处理
		try {

			// 新建查询条件的表单
			TransAccountGuardInfo form = new TransAccountGuardInfo();
			form.setStatusId((int) Constant.RecordStatus.VALID);

			// 新建操作数据库的DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();

			// 获取说有记录
			colList = dao.findByCondition(form, " order by id ");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return colList;
	}

	// 获取要修改的记录
	public TransAccountGuardInfo findById(long id) {

		// 定义变量
		TransAccountGuardInfo adli = null;

		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();

			// 获取要编辑的数据
			adli = (TransAccountGuardInfo) dao.findByID(id, (new TransAccountGuardInfo())
					.getClass());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回函数值
		return adli;
	}

	// 修改指定的记录
	public boolean update(TransAccountGuardInfo form) {

		// 开始处理
		try {
			// 新建操作数据库的DAO
			Sett_TransAccountGuardDAO dao = new Sett_TransAccountGuardDAO();

			// 更新数据
			dao.update(form);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 返回函数值
		return true;
	}

	/*
	 * 校验付款单据
	 * 
	 * 返回值： 1 校验通过；0 程序运行错误 ；-1 非监管账户； -2 监管账户，超过单笔支付发生额； -3 监管账户，超过周期内付款总额；
	 * 
	 */
	public long checkTransAccountGuardInfo(TransAccountGuardInfo form) {

		// 定义变量
		long bResult = 0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		try {
			// 查询该账户的监管设置信息
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // 新建查询DAO
			Collection col = AccountGuardDAO.findByAccountId(form.getPayAccountId()); // 查询数据表

			// 是否是监管账户
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// 如果该账户存在监管设置中，读取监管设置信息
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();

				// 获取该账户本次付款额，周期内付款总额
				double lPayCurrent = form.getAmount(); // 本次付款额
				double dbTransPayLimited = accountGuardSettingInfo.getTransPayLimited();	//单笔交易限额

				// 校验本次付款额
				if (dbTransPayLimited > 0 && lPayCurrent >= dbTransPayLimited) {
					bResult = -2; // 本次付款额超过限额
				}
				Log.print("账户(" + form.getPayAccountId() + ")付款额是:" + lPayCurrent);
				Log.print("账户(" + form.getPayAccountId() + ")单笔交易限额是:"
						+ accountGuardSettingInfo.getTransPayLimited());

				// 校验周期内付款总额
				double dbSumLimited = accountGuardSettingInfo.getSumLimited();	//累计付款限额
				if (bResult != -2 && dbSumLimited > 0) {

					// 统计该账户周期内的付款总额
					double lSumPay = getSumPayInPeriods(form, accountGuardSettingInfo
							.getValidDate(), accountGuardSettingInfo.getStatDays());
					Log.print("账户(" + form.getPayAccountId() + ")周期内付款额是(包括本次付款额):" + lSumPay);
					Log.print("账户(" + form.getPayAccountId() + ")周期内付款限额是:"
							+ accountGuardSettingInfo.getSumLimited());
					if (lSumPay >= dbSumLimited) {
						bResult = -3; // 周期内付款额超过限额
					} else {
						bResult = 1; // 校验通过
					}

				}else if(bResult != -2){
					bResult = 1; // 校验通过
				}

			} else {
				bResult = -1; // 非监管账户
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return bResult;
		}

		// 返回函数值
		return bResult;

	}

	// 统计账户周期内付款总额（包括本次：新增单据，编辑单据）
	private double getSumPayInPeriods(TransAccountGuardInfo form, Timestamp tmValidDate,
			long lStatDays) throws SQLException {

		// 定义变量
		double dbResult = -1; // 交易总额
		Timestamp dtExecuteDate = form.getExecuteDate();
		Timestamp dtStart = null; // 起始日期
		Timestamp dtEnd = null; // 结束日期

		// 如果统计有效日期在业务日期之后，则不需要考虑这条记录
		if (tmValidDate.after(dtExecuteDate)) {
			dbResult = 0.0;
		} else {

			// 计算业务所在时间段内所有的付款金额总和(包括当前金额)

			// 计算业务日期属于哪个时间段
			Hashtable hsbDateSection = DataFormat.getDateSection(tmValidDate, lStatDays,
					dtExecuteDate);
			dtStart = (Timestamp) hsbDateSection.get("Start");
			dtEnd = (Timestamp) hsbDateSection.get("End");

			// 创建查询数据库的DAO对象
			Sett_TransAccountGuardDAO transAccountGuardDAO = new Sett_TransAccountGuardDAO();

			// 统计符合条件的账号付款金额之和
			dbResult = transAccountGuardDAO.sumPayInPeriods(form.getId(),form.getPayAccountId(), dtStart, dtEnd);

			// 加上本次支付金额
			dbResult += form.getAmount();

		}
		Log.print("账户(" + form.getPayAccountId() + ")周期内付款限额的有效起始日期是:"
				+ DataFormat.getDateString(tmValidDate));
		Log.print("账户(" + form.getPayAccountId() + ")付款额日期是:"
				+ DataFormat.getDateString(dtExecuteDate));
		Log.print("账户(" + form.getPayAccountId() + ")付款额日期所在的周期区间是:("
				+ DataFormat.getDateString(dtStart) + "到" + DataFormat.getDateString(dtEnd) + ")");

		// 返回函数值
		return dbResult;
	}

	// 统计账户周期内付款总额(不包括本次)
	public double getSumPayInPeriods(TransAccountGuardInfo form) {

		// 定义变量
		double dbResult = 0.0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		try {

			// 通过账户ID查询账户监管信息
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // 新建查询DAO
			Collection col = null;
			col = AccountGuardDAO.findByAccountId(form.getPayAccountId());// 查询数据表

			// 是否是监管账户
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// 如果该账户存在监管设置中，读取监管设置信息
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();
				Timestamp tmValidDate = accountGuardSettingInfo.getValidDate();
				long lStatDays = accountGuardSettingInfo.getStatDays();

				// 确定周期时间段
				Timestamp dtExecuteDate = form.getExecuteDate();
				Hashtable hsbDateSection = DataFormat.getDateSection(tmValidDate, lStatDays,
						dtExecuteDate);
				Timestamp dtStart = (Timestamp) hsbDateSection.get("Start");// 起始日期
				Timestamp dtEnd = (Timestamp) hsbDateSection.get("End");// 结束日期

				// 创建查询数据库的DAO对象
				Sett_TransAccountGuardDAO transAccountGuardDAO = new Sett_TransAccountGuardDAO();

				// 统计符合条件的账号付款金额之和
				dbResult = transAccountGuardDAO.sumPayInPeriods(form.getId(),form.getPayAccountId(), dtStart,
						dtEnd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 返回函数值
		return dbResult;

	}

	// 通过账户ID查询账户单笔交易金额上限
	public static double getTransPayLimitedByAccountId(long lAccountId) {

		// 定义变量
		double dbResult = 0.0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		// 开始处理
		try {

			// 查询该账户的监管设置信息
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // 新建查询DAO
			Collection col = AccountGuardDAO.findByAccountId(lAccountId); // 查询数据表

			// 是否是监管账户
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// 如果该账户存在监管设置中，读取监管设置信息
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();
				dbResult = accountGuardSettingInfo.getTransPayLimited();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 返回函数值
		return dbResult;

	}

	// 通过账户ID查询账户周期内付款总额上限
	public static double getSumLimitedByAccountId(long lAccountId) {

		// 定义变量
		double dbResult = 0.0;
		AccountGuardSettingInfo accountGuardSettingInfo = null;

		// 开始处理
		try {

			// 查询该账户的监管设置信息
			Sett_AccountGuardSettingDAO AccountGuardDAO = new Sett_AccountGuardSettingDAO(); // 新建查询DAO
			Collection col = AccountGuardDAO.findByAccountId(lAccountId); // 查询数据表

			// 是否是监管账户
			Iterator it = col.iterator();
			if (it.hasNext()) {

				// 如果该账户存在监管设置中，读取监管设置信息
				accountGuardSettingInfo = (AccountGuardSettingInfo) it.next();
				dbResult = accountGuardSettingInfo.getSumLimited();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 返回函数值
		return dbResult;
	}

	// 测试函数
	public static void main(String[] args) {

		System.out.println("您测试的值是:");
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
