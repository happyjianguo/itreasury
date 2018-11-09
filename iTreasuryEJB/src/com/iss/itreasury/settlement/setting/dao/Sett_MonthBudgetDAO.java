/*
 * Created on 2004-04-23
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.query.queryobj.QDailyAmountVaryDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryDailyCapitalWhereInfo;
import com.iss.itreasury.settlement.setting.dataentity.MonthBudgetSetInfo;

/**
 * @author kewen hu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_MonthBudgetDAO extends SettlementDAO {
	/** DB连接 */
	private Connection conn = null;//DB连接

	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing(创建一个新的数据库连接)
	 * @exception nothing
	 */
    public Sett_MonthBudgetDAO() {
    	this.conn = this.getConnection();
        super.strTableName = "Sett_MonthBudget";
    }

	/**
	 * 构造函数
	 * @param  Connection conn
	 * @return nothing(保存参数中的数据库连接)
	 * @exception nothing
	 */
    public Sett_MonthBudgetDAO(Connection conn) {
    	this.conn = conn;
        super.strTableName = "Sett_MonthBudget";
    }

	/**
	 * 析构函数
	 * @param  Connection conn
	 * @return nothing(关闭参数中的数据库连接)
	 * @exception SQLException
	 */
	public void CloseSett_MonthBudgetDAO(Connection conn) throws SQLException {
		this.cleanup(conn);
	}

	/**
	 * 析构函数
	 * @param  nothing
	 * @return nothing(关闭新创建的数据库连接)
	 * @exception SQLException
	 */
	public void CloseSett_MonthBudgetDAO() throws SQLException {
		this.cleanup(this.conn);
	}

	/**
	 * 创建新的ID(数据库ID最大值+1)
	 * @param  Connection conn
	 * @return long
	 * @exception SQLException
	 */
    private long createMaxID(Connection conn) throws SQLException {
        long lResult = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuffer strSQL = new StringBuffer();
        strSQL.append("SELECT NVL(MAX(ID),0)+1 maxID FROM "+this.strTableName);

        log.info("SQL="+strSQL.toString());

        try {
            ps = conn.prepareStatement(strSQL.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
            	lResult = rs.getLong("maxID");
            }
        } catch (SQLException se) {
        	throw new SQLException("创建新的ID出错[createMaxID]"+se.toString());
        } finally {
            this.cleanup(rs);
            this.cleanup(ps);
        }

        return lResult;
    }

	/**
	 * 创建新的ID(数据库ID最大值+1)
	 * @param  nothing
	 * @return long
	 * @exception SQLException
	 */
    private long createMaxID() throws SQLException {
    	return this.createMaxID(this.conn);
    }

	/**
	 * 新增一条记录
	 * @param  MonthBudgetSetInfo info, Connection conn
	 * @return long
	 * @exception SQLException
	 */
    public long add(MonthBudgetSetInfo info, Connection conn) throws SQLException {
        long lResult = -1;
        PreparedStatement ps = null;

        StringBuffer strSQL = new StringBuffer();
        strSQL.append("INSERT INTO "+this.strTableName+" ( \n");
        strSQL.append("	ID, \n");
        strSQL.append("	nOfficeID, \n");
        strSQL.append("	nCurrencyID, \n");
        strSQL.append("	sYearMonth, \n");
        strSQL.append("	nSuperClientID1, \n");
        strSQL.append("	nSuperClientID2, \n");
        strSQL.append("	nClientID, \n");
        strSQL.append("	mDepositAmount, \n");
        strSQL.append("	mWithdrawAmount, \n");
        strSQL.append("	nStatusID \n");
        strSQL.append(") VALUES ( \n");
        strSQL.append("	"+(info.getID()==-1?this.createMaxID(conn):info.getID())+", \n");
        strSQL.append("	"+info.getOfficeID()+", \n");
        strSQL.append("	"+info.getCurrencyID()+", \n");
        strSQL.append("	"+info.getYearMonth()+", \n");
        strSQL.append("	"+info.getSuperClientID1()+", \n");
        strSQL.append("	"+info.getSuperClientID2()+", \n");
        strSQL.append("	"+info.getClientID()+", \n");
        strSQL.append("	"+info.getDepositAmount()+", \n");
        strSQL.append("	"+info.getWithdrawAmount()+", \n");
        strSQL.append("	"+Constant.RecordStatus.VALID+" \n");
        strSQL.append(") \n");

        log.info("SQL="+strSQL.toString());

        try {
            ps = conn.prepareStatement(strSQL.toString());
            lResult = (long) ps.executeUpdate();
        } catch (SQLException se) {
        	throw new SQLException("新增记录出错[add]"+se.toString());
        } finally {
            this.cleanup(ps);
        }

        return lResult;
    }

	/**
	 * 新增一条记录
	 * @param  MonthBudgetSetInfo info
	 * @return long
	 * @exception SQLException
	 */
    public long add(MonthBudgetSetInfo info) throws SQLException {
    	return this.add(info, this.conn);
    }

	/**
	 * 修改一条记录
	 * @param  MonthBudgetSetInfo info, Connection conn
	 * @return long
	 * @exception SQLException
	 */
    public long update(MonthBudgetSetInfo info, Connection conn) throws SQLException {
        long lResult = -1;
        PreparedStatement ps = null;

        StringBuffer strSQL = new StringBuffer();
        strSQL.append("UPDATE "+this.strTableName+" SET \n");
        strSQL.append("	ID = "+info.getID()+", \n");
        strSQL.append("	nOfficeID = "+info.getOfficeID()+", \n");
        strSQL.append("	nCurrencyID = "+info.getCurrencyID()+", \n");
        strSQL.append("	sYearMonth = "+info.getYearMonth()+", \n");
        strSQL.append("	nSuperClientID1 = "+info.getSuperClientID1()+", \n");
        strSQL.append("	nSuperClientID2 = "+info.getSuperClientID2()+", \n");
        strSQL.append("	nClientID = "+info.getClientID()+", \n");
        strSQL.append("	mDepositAmount = "+info.getDepositAmount()+", \n");
        strSQL.append("	mWithdrawAmount = "+info.getWithdrawAmount()+", \n");
        strSQL.append("	nStatusID = "+info.getStatusID()+" \n");
        strSQL.append("WHERE ID = "+info.getID()+" \n");

        log.info("SQL="+strSQL.toString());

        try {
            ps = conn.prepareStatement(strSQL.toString());
            lResult = (long) ps.executeUpdate();
        } catch (SQLException se) {
        	throw new SQLException("修改记录出错[update]"+se.toString());
        } finally {
            this.cleanup(ps);
        }

        return lResult;
    }

	/**
	 * 修改一条记录
	 * @param  MonthBudgetSetInfo info
	 * @return long
	 * @exception SQLException
	 */
    public long update(MonthBudgetSetInfo info) throws SQLException {
    	return this.update(info, this.conn);
    }

	/**
	 * 设置记录状态
	 * @param  long lId, long nNewStatusID, Connection conn
	 * @return long
	 * @exception SQLException
	 */
    private long updateStatusID(long lId, long nNewStatusID, Connection conn) throws SQLException {
        long lResult = -1;
        PreparedStatement ps = null;

        StringBuffer strSQL = new StringBuffer();
        strSQL.append("UPDATE "+this.strTableName+" \n");
        strSQL.append("SET nStatusID = "+nNewStatusID+" \n");
        strSQL.append("WHERE ID = "+lId+" \n");

        log.info("SQL="+strSQL.toString());

        try {
        	ps = conn.prepareStatement(strSQL.toString());
            lResult = (long) ps.executeUpdate();
        } catch (SQLException se) {
        	throw new SQLException("设置记录状态出错[updateStatusID]"+se.toString());
        } finally {
            this.cleanup(ps);
        }

        return lResult;
    }

	/**
	 * 删除一条记录
	 * @param  long lId, Connection conn
	 * @return long
	 * @exception SQLException
	 */
    public long delete(long lId, Connection conn) throws SQLException {
        return this.updateStatusID(lId, Constant.RecordStatus.INVALID, conn);
    }

	/**
	 * 删除一条记录
	 * @param  long lId
	 * @return long
	 * @exception SQLException
	 */
    public long delete(long lId) throws SQLException {
        return this.delete(lId, this.conn);
    }

	/**
	 * 通过nClientID查找记录
	 * @param  MonthBudgetSetInfo paramInfo, String[] string, Connection conn
	 * @return MonthBudgetSetInfo
	 * @exception SQLException
	 */
    public MonthBudgetSetInfo findByClientID(MonthBudgetSetInfo paramInfo, String[] string, Connection conn) throws SQLException {
    	MonthBudgetSetInfo info = new MonthBudgetSetInfo();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuffer strSQL = new StringBuffer();
        strSQL.append("SELECT * FROM "+this.strTableName+" \n");
        strSQL.append("WHERE 1 = 1 \n");
        strSQL.append("	AND nClientID = "+string[0]+" \n");
        strSQL.append("	AND nStatusID = "+SETTConstant.RecordStatus.VALID+" \n");
        strSQL.append("	AND sYearMonth = "+paramInfo.getYearMonth()+" \n");

        log.info("SQL="+strSQL.toString());

        try {
            ps = conn.prepareStatement(strSQL.toString());
            rs = ps.executeQuery();

            if (rs != null && rs.next()) {
                //ID				Y	PK
            	info.setID(rs.getLong("ID"));
                //nOfficeID	办事处ID	Number		
            	info.setOfficeID(rs.getLong("nOfficeID"));
            	//nCurrencyID	币种ID	Number
            	info.setCurrencyID(rs.getLong("nCurrencyID"));
            	//sYearMonth	预算年月	Varchar(20)			存入数据示例：“200404”
            	info.setYearMonth(paramInfo.getYearMonth());
            	//nSuperClientID1	上级单位1	Number
            	info.setSuperClientID1(rs.getLong("nSuperClientID1"));
            	//nSuperClientID2	上级单位2	Number
            	info.setSuperClientID2(rs.getLong("nSuperClientID2"));
            	//nClientID	下属单位	Number
            	info.setClientID(Long.parseLong(string[0]));
            	//mDepositAmount	预算存入金额	Moneyinfo.setClientID(rs.getLong("ClientID"));
            	info.setDepositAmount(rs.getDouble("mDepositAmount"));
            	//mWithdrawAmount	预算支取金额	Money
            	info.setWithdrawAmount(rs.getDouble("mWithdrawAmount"));
            	//nStatusID	状态	Number	Not nullDefault -1		1-正常0-无效
            	info.setStatusID(SETTConstant.RecordStatus.VALID);

            	/** 页面条件参数 */
            	info.setCode(string[2]);//客户编号
            	info.setName(string[1]);//客户名称

            	info.setTotalType(paramInfo.getTotalType());
            	info.setDepositAmountControlName(paramInfo.getDepositAmountControlName());
            	info.setWithdrawAmountControlName(paramInfo.getWithdrawAmountControlName());
            } else {
                //ID				Y	PK
            	info.setID(paramInfo.getID());
                //nOfficeID	办事处ID	Number		
            	info.setOfficeID(paramInfo.getOfficeID());
            	//nCurrencyID	币种ID	Number
            	info.setCurrencyID(paramInfo.getCurrencyID());
            	//sYearMonth	预算年月	Varchar(20)			存入数据示例：“200404”
            	info.setYearMonth(paramInfo.getYearMonth());
            	//nSuperClientID1	上级单位1	Number
            	info.setSuperClientID1(paramInfo.getSuperClientID1());
            	//nSuperClientID2	上级单位2	Number
            	info.setSuperClientID2(paramInfo.getSuperClientID2());
            	//nClientID	下属单位	Number
            	info.setClientID(Long.parseLong(string[0]));
            	//mDepositAmount	预算存入金额	Moneyinfo.setClientID(rs.getLong("ClientID"));
            	info.setDepositAmount(paramInfo.getDepositAmount());
            	//mWithdrawAmount	预算支取金额	Money
            	info.setWithdrawAmount(paramInfo.getWithdrawAmount());
            	//nStatusID	状态	Number	Not nullDefault -1		1-正常0-无效
            	info.setStatusID(paramInfo.getStatusID());

            	/** 页面条件参数 */
            	info.setCode(string[2]);//客户编号
            	info.setName(string[1]);//客户名称

            	info.setTotalType(paramInfo.getTotalType());
            	info.setDepositAmountControlName(paramInfo.getDepositAmountControlName());
            	info.setWithdrawAmountControlName(paramInfo.getWithdrawAmountControlName());
            }
        } catch (SQLException se) {
        	throw new SQLException("通过nClientID查找记录出错[findByClientID]"+se.toString());
        } finally {
            this.cleanup(rs);
            this.cleanup(ps);
        }

        return info;
    }

	/**
	 * 通过nClientID查找记录
	 * @param  MonthBudgetSetInfo paramInfo, String[] string
	 * @return MonthBudgetSetInfo
	 * @exception SQLException
	 */
    public MonthBudgetSetInfo findByClientID(MonthBudgetSetInfo paramInfo, String[] string) throws SQLException {
    	return this.findByClientID(paramInfo, string, this.conn);
    }

//===================================股份公司每日资金变动情况表==============================
	/**
	 * 查询股份公司结果记录
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryHoldingCompanyInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
    	paramInfo.setSuperClientID1(-1);
    	paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType001.TotalType001);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	String[] code0003 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003);
    	list.add(this.findByClientID(paramInfo, code0003));

    	return list;
    }

	/**
	 * 查询股份公司结果记录
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryHoldingCompanyInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryHoldingCompanyInfo(paramInfo, this.conn);
    }

	/**
	 * 查询全资电厂客户记录
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryCapitalElectricInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
        String[] code0003 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003);
    	paramInfo.setSuperClientID1(Long.parseLong(code0003[0]));
    	paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType001.TotalType001);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	Collection collection = qdavDao.getCapitalElectricClientID(qdcwi);
    	Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询全资电厂客户记录
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryCapitalElectricInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryCapitalElectricInfo(paramInfo, this.conn);
    }

	/**
	 * 查询控股电厂客户记录
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryHoldingElectricInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
        String[] code0003 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003);
    	paramInfo.setSuperClientID1(Long.parseLong(code0003[0]));
    	paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType001.TotalType002);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	Collection collection = qdavDao.getHoldingElectricClientID(qdcwi);
    	Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询控股电厂客户记录
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryHoldingElectricInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryHoldingElectricInfo(paramInfo, this.conn);
    }

//===================================股份公司代管电厂每日资金变动情况表==============================
	/**
	 * 查询集团电厂合计记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupElectricInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
    	String[] code0001 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001);
    	String[] code0003 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003);
		paramInfo.setSuperClientID1(Long.parseLong(code0001[0]));
		paramInfo.setSuperClientID2(Long.parseLong(code0003[0]));
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType002.TotalType001);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
		Collection collection = qdavDao.getGroupElectricClientID(qdcwi);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询集团电厂合计记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupElectricInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryGroupElectricInfo(paramInfo, this.conn);
    }

	/**
	 * 查询开发电厂合计记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderElectricInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
    	String[] code0002 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002);
    	String[] code0003 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003);
		paramInfo.setSuperClientID1(Long.parseLong(code0002[0]));
		paramInfo.setSuperClientID2(Long.parseLong(code0003[0]));
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType002.TotalType002);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	Collection collection = qdavDao.getEmpolderElectricClientID(qdcwi);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询开发电厂合计记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderElectricInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryEmpolderElectricInfo(paramInfo, this.conn);
    }

//===================================华能集团公司及控股电厂存款每日变动情况表==============================
	/**
	 * 查询华能集团公司的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupCompanyInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
		paramInfo.setSuperClientID1(-1);
		paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType003.TotalType001);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	list.add(this.findByClientID(paramInfo, qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001)));

    	return list;
    }

	/**
	 * 查询华能集团公司的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupCompanyInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryGroupCompanyInfo(paramInfo, this.conn);
    }

	/**
	 * 查询集团全资电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupCapitalInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
    	String[] code0001 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001);
		paramInfo.setSuperClientID1(Long.parseLong(code0001[0]));
		paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType003.TotalType001);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	Collection collection = qdavDao.getGroupCapitalClientID(qdcwi);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询集团全资电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupCapitalInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryGroupCapitalInfo(paramInfo, this.conn);
    }

	/**
	 * 查询华能国际电力开发公司的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderCompanyInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
		paramInfo.setSuperClientID1(-1);
		paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType003.TotalType002);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	list.add(this.findByClientID(paramInfo, qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002)));

    	return list;
    }

	/**
	 * 查询华能国际电力开发公司的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderCompanyInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryEmpolderCompanyInfo(paramInfo, this.conn);
    }

	/**
	 * 查询开发全资电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderCapitalInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
    	String[] code0002 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002);
		paramInfo.setSuperClientID1(Long.parseLong(code0002[0]));
		paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType003.TotalType002);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	Collection collection = qdavDao.getEmpolderCapitalClientID(qdcwi);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询开发全资电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderCapitalInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryEmpolderCapitalInfo(paramInfo, this.conn);
    }

	/**
	 * 查询集团控股电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupHoldingInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
    	String[] code0001 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001);
		paramInfo.setSuperClientID1(Long.parseLong(code0001[0]));
		paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType003.TotalType003);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	Collection collection = qdavDao.getGroupHoldingClientID(qdcwi);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询集团控股电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryGroupHoldingInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryGroupHoldingInfo(paramInfo, this.conn);
    }

	/**
	 * 查询开发控股电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderHoldingInfo(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList list = new ArrayList();

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
    	String[] code0002 = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002);
		paramInfo.setSuperClientID1(Long.parseLong(code0002[0]));
		paramInfo.setSuperClientID2(-1);
    	paramInfo.setTotalType(SETTConstant.ReportType.TotalType003.TotalType004);
    	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
    	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
    	Collection collection = qdavDao.getEmpolderHoldingClientID(qdcwi);
		Iterator iterator = null;
		if (collection != null) {
			iterator = collection.iterator();
		}
		while (iterator != null && iterator.hasNext()) {
			String[] string = (String[]) iterator.next();
			list.add(this.findByClientID(paramInfo, string));
		}

    	return list;
    }

	/**
	 * 查询开发控股电厂记录的结果集
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection
	 * @exception SQLException
	 */
    public Collection queryEmpolderHoldingInfo(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.queryEmpolderHoldingInfo(paramInfo, this.conn);
    }

	/**
	 * 查询有效记录(以nClientID自动排序)
	 * @param  MonthBudgetSetInfo paramInfo, Connection conn
	 * @return Collection[]
	 * @exception SQLException
	 */
    public Collection[] find(MonthBudgetSetInfo paramInfo, Connection conn) throws SQLException {
    	ArrayList[] list = null;
    	Collection collection = null;
    	Iterator iterator = null;

    	QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
    	qdcwi.setOfficeID(paramInfo.getOfficeID());
    	qdcwi.setCurrencyID(paramInfo.getCurrencyID());
        QDailyAmountVaryDao qdavDao = new QDailyAmountVaryDao(conn);
        //MonthBudgetSetInfo resultInfo = new MonthBudgetSetInfo();
        switch ((int) paramInfo.getReportType()) {
        case (int) SETTConstant.ReportType.ReportType001:
        	list = new ArrayList[3];
        	//股份公司
        	list[0] = new ArrayList();
        	paramInfo.setSuperClientID1(-1);
        	paramInfo.setSuperClientID2(-1);
        	paramInfo.setTotalType(SETTConstant.ReportType.TotalType001.TotalType001);
        	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
        	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
        	String[] code0003a = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003);
        	list[0].add(this.findByClientID(paramInfo, code0003a));
        	//全资电厂客户集
        	list[1] = new ArrayList();
        	paramInfo.setSuperClientID1(Long.parseLong(code0003a[0]));
        	paramInfo.setSuperClientID2(-1);
        	paramInfo.setTotalType(SETTConstant.ReportType.TotalType001.TotalType001);
        	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
        	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
        	collection = qdavDao.getCapitalElectricClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[1].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
        	//全资电厂小计
        	//控股电厂客户集
    		list[2] = new ArrayList();
        	paramInfo.setSuperClientID1(Long.parseLong(code0003a[0]));
        	paramInfo.setSuperClientID2(-1);
        	paramInfo.setTotalType(SETTConstant.ReportType.TotalType001.TotalType002);
        	paramInfo.setDepositAmountControlName("DepositAmount"+String.valueOf(paramInfo.getTotalType()));
        	paramInfo.setWithdrawAmountControlName("WithdrawAmount"+String.valueOf(paramInfo.getTotalType()));
        	collection = qdavDao.getHoldingElectricClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[2].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
        	//控股电厂小计
        	//总计
    		break;
        case (int) SETTConstant.ReportType.ReportType002:
        	list = new ArrayList[2];
        	//集团电厂合计记录的结果集
        	list[0] = new ArrayList();
        	String[] code0001b = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001);
        	String[] code0003b = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0003);
    		paramInfo.setSuperClientID1(Long.parseLong(code0001b[0]));
    		paramInfo.setSuperClientID2(Long.parseLong(code0003b[0]));
        	collection = qdavDao.getGroupElectricClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[0].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
        	//集团电厂合计
        	//开发电厂合计记录的结果集
    		list[1] = new ArrayList();
    		String[] code0002b = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002);
    		paramInfo.setSuperClientID1(Long.parseLong(code0002b[0]));
    		paramInfo.setSuperClientID2(Long.parseLong(code0003b[0]));
        	collection = qdavDao.getEmpolderElectricClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[1].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
        	//开发电厂合计
    		break;
        case (int) SETTConstant.ReportType.ReportType003:
        	list = new ArrayList[6];
        	//华能集团公司的结果集
        	list[0] = new ArrayList();
    		paramInfo.setSuperClientID1(-1);
    		paramInfo.setSuperClientID2(-1);
        	list[0].add(this.findByClientID(paramInfo, qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001)));
        	//集团全资电厂记录的结果集
        	list[1] = new ArrayList();
        	String[] code0001c = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0001);
    		paramInfo.setSuperClientID1(Long.parseLong(code0001c[0]));
    		paramInfo.setSuperClientID2(-1);
        	collection = qdavDao.getGroupCapitalClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[1].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
        	//华能国际电力开发公司的结果集
    		list[2] = new ArrayList();
    		paramInfo.setSuperClientID1(-1);
    		paramInfo.setSuperClientID2(-1);
    		list[2].add(this.findByClientID(paramInfo, qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002)));
        	//开发全资电厂记录的结果集
    		list[3] = new ArrayList();
    		String[] code0002c = qdavDao.CheckNormalForClientID(qdcwi, SETTConstant.PartClientCode.CODE0002);
    		paramInfo.setSuperClientID1(Long.parseLong(code0002c[0]));
    		paramInfo.setSuperClientID2(-1);
        	collection = qdavDao.getEmpolderCapitalClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[3].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
        	//集团控股电厂记录的结果集
    		list[4] = new ArrayList();
    		paramInfo.setSuperClientID1(Long.parseLong(code0001c[0]));
    		paramInfo.setSuperClientID2(-1);
        	collection = qdavDao.getGroupHoldingClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[4].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
        	//开发控股电厂记录的结果集
    		list[5] = new ArrayList();
    		paramInfo.setSuperClientID1(Long.parseLong(code0002c[0]));
    		paramInfo.setSuperClientID2(-1);
        	collection = qdavDao.getEmpolderHoldingClientID(qdcwi);
    		if (collection != null) {
    			iterator = collection.iterator();
    		}
    		while (iterator != null && iterator.hasNext()) {
    			String[] string = (String[]) iterator.next();
    			list[5].add(this.findByClientID(paramInfo, string));
    		}
    		if (iterator != null) {
    			iterator.remove();
    		}
    		if (collection != null) {
    			collection.clear();
    		}
    		break;
    	default:
    		break;
        }

        return list;
    }

	/**
	 * 查询有效记录(以nClientID自动排序)
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection[]
	 * @exception SQLException
	 */
    public Collection[] find(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.find(paramInfo, this.conn);
    }
}