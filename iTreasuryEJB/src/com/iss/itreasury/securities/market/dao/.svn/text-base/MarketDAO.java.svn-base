/*
 * Created on 2004-06-23
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;
import java.util.Vector;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.market.dataentity.MarketInfo;
import com.iss.itreasury.securities.market.dataentity.MarketConditionInfo;
import com.iss.itreasury.securities.market.exception.MarketException;
import com.iss.itreasury.securities.market.exception.MarketRuntimeException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author kewen hu 2004-06-23 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MarketDAO extends SettlementDAO{
	// 按合同编号排序
	public static final long OrderBySecuritiesCode = 1;
	// 按合同名称排序
	public static final long OrderBySecuritiesName = 2;
	// 按证券类别
	public static final long OrderByTypeID = 3;
	// 按净价收盘价排序
	public static final long OrderByNetClosePrice = 4;
	// 按全价收盘价排序
	public static final long OrderByClosePrice = 5;
	// 按收盘日
	public static final long OrderByCloseDate = 6;
	// 按基金管理公司
	public static final long OrderByCounterpartID = 7;

	private static final long SECURITIESMARKET_STOCK = 1; //股票行情数据导入
	private static final long SECURITIESMARKET_BOND = 2; //债券行情数据导入
	private static final long SECURITIESMARKET_OPENFUND = 3; //开放式基金行情数据导入
	public static final String SEC_UPLOAD_PATH = "securities/"; // 证券上传路径

	private Connection m_Conn = null;
    private StringBuffer m_sbOrderBy = null;
    private Log4j logger = null;

    //public static final String SEC_MARKET_REMOVE = "";

	// 行情数据导入删除证券代码--华能证券 2004-11-05 刘惠军
    public static final String SEC_MARKET_REMOVE_HN =
        //必须删除?
        "7000001,7000002,7000012,7000013," +
        //选择删除
        "7000003,7000004,7000005,7000006,7000007,7000008," +
        "7000010,7000011,8399001,8399002,8399003,8399004," +
        "8399106,8399107,8399108,8399110,8399120,8399130," +
        "8399131,8399132,8399133,8399134,8399135,8399136," +
        "8399137,8399138,8399139,8399140,8399150,8399160," +
        "8399170,8399180,8399190,8399200,8399210,8399220," +
        "8399230,8399305,8399481,7129904,7129905,8399005," +    
	    "7000696,7009704,7009905,7009908,7010004,7010010," +	
		"7010103,7010107,7010110,7010112,7010115,7010203," +	
		"7010210,7010213,7010214,7010215,7010301,7010303," +	
		"7010307,8125302,8125630,8125729,8125898,8125930," +
		"8125936,8125959,8126301,7129805,7129901,7129902," +
		"8111016,8111017,8111018,8111019,7120001,7120101," +
		"7120102,7120201,7120202,7120203,7120204,7120205," +
		"7120206,7120207,7120288,7120301,7120302,7120303," +
		"8125069,8100303,8100307,8100308,7100311,9100401," +
		"9100403,7100567,7100726,7100795,8101903,8101904," +
		"8101905,8101912,8101917,8101966,8101973,8101995," +
		"8101998,8110001,9110418,7010308,7010311,7010401," +
		"7010403,7100009,7100016,7100087,7100096,8100115," +
		"7100117,7100177,8100196,8100203,8100210,8100213," +	
		"8100214,8100215,7100220,7100236,8100301,7129903,8111015,8125002,8125629";

//  行情数据导入删除证券代码--国机财务 2004-11-05 刘惠军
    public static final String SEC_MARKET_REMOVE_CNMEF =
        //必须删除?
        "7000001,7000002,7000012,7000013," +
        //选择删除
        "7000003,7000004,7000005,7000006,7000007,7000008," +
        "7000010,7000011,8399001,8399002,8399003,8399004," +
        "8399106,8399107,8399108,8399110,8399120,8399130," +
        "8399131,8399132,8399133,8399134,8399135,8399136," +
        "8399137,8399138,8399139,8399140,8399150,8399160," +
        "8399170,8399180,8399190,8399200,8399210,8399220," +
        "8399230,8399305,8399481,8399005";    
	   

	/**
	 * 构造函数
	 *
	 * @param  conn
	 * @return nothing
	 * @throws Exception
	 */
    public MarketDAO(Connection conn) {
		this.m_Conn = conn;
	}

	/**
	 * 析构函数(不带参数)
	 *
	 * @param  nothing
	 * @return nothing
	 * @throws Exception
	 */
    public void CloseMarketDAO() throws Exception{
		this.cleanup(this.m_Conn);
	}

	/**
	 * 析构函数(带参数)
	 *
	 * @param  conn
	 * @return nothing
	 * @throws Exception
	 */
    public void CloseMarketDAO(Connection conn) throws Exception{
		this.cleanup(conn);
	}

    /**
     * 批处理操作，实现了事务处理
     * @param conn
     * @param vtSQL 要批处理的SQL语句集合
     * @return
     * @throws SQLException
     */
    public static int[] executeBatchByTransction(Connection conn, Vector vtSQL) throws SQLException {
        Log.print("ExecuteBatch Begin!");
        int[] iCounts = null;
        Statement stmt = null;
        if (vtSQL == null || vtSQL.size() == 0) {
            return null;
        }

        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for (int i = 0; i < vtSQL.size(); i++) {
                stmt.addBatch((String) vtSQL.elementAt(i));
            }
            iCounts = stmt.executeBatch();
            conn.commit();
            Log.print("ExecuteBatch Success!");
        } catch (SQLException e) {
            conn.rollback();
            Log.print("ExecuteBatch Failed!");
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        }

        return iCounts;
    }

	/**
	 * 将从txt文件读取的数据，并将其中的行情数据成批导入到证券系统的数据库中
	 * 
	 * @return int[] 返回所有记录导入执行的结果数组
	 * @throws MarketException
	 */
	public int[] add(Vector vctMarketInfo, long lUserID) throws MarketException {
		int[] intReturn = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		Timestamp dtDate = null;
		String strSecuritiesCode = ""; // 股票代码
		String strSecuritiesName = ""; // 股票名称
		double dblPrice = 0; // 收盘价
		Vector vctSQL = new Vector();
		MarketInfo objMarketInfo = null;

		try {
			String strTemp = "";
			String strDate = "";
			int index = 0;
			int intTest = 1;
			// test data for printing the SQL of insert securitiesMarketInfo
			for (int i = 0; i < vctMarketInfo.size(); i++) {
				strTemp = (String) vctMarketInfo.elementAt(i);
				//Log.print("首先处理第一行的信息 ，日期！！！！");
				if (i == 0 && strTemp.length() > 0) {
					index = strTemp.indexOf(",");
					strDate = strTemp.substring(0, index);
					strTemp = strTemp.substring(index + 1);
					if (strDate != null && strDate.equals("date")) {
						index = strTemp.indexOf(",");
						Log.print("the first line &&& index = " + index);
						strDate = strTemp.substring(0, index);
						Log.print("the first line &&& strTemp = " + strTemp);
						Log.print("the first line &&& strDate = " + strDate);
						dtDate = DataFormat.getDateTime(strDate);
					}
					// 如果当日的行情数据已经导入，则先删除上一次导入的股票行情数据
					deleteMarketInfo(dtDate, SECURITIESMARKET_STOCK);
				} else if (i > 0 && strTemp.length() > 0) {
					index = strTemp.indexOf(",");
					strSecuritiesCode = strTemp.substring(0, index).trim();
                    //判断行情数据证券代码是否在行情数据导入删除串中，如果是则不导入数据库
					if (SEC_MARKET_REMOVE_HN.indexOf(strSecuritiesCode) != -1) {
						 Log.print("不能导入的证券号码："+strSecuritiesCode);
						 continue;
					}
					if (index != -1 && index <= strTemp.length()) {
						//去掉第一位的编码7或者8
						strSecuritiesCode = strTemp.substring(1, index).trim();
						strTemp = strTemp.substring(index + 1);
						index = strTemp.indexOf(",");
						if (index != -1 && index <= strTemp.length()) {
							strSecuritiesName =
								strTemp.substring(0, index).trim();
							strTemp = strTemp.substring(index + 1);
							//股票收盘价
							dblPrice = Double.parseDouble(strTemp.trim());
						}
					}
					
					/*
					 * 刘惠军 2004-07-20 修改，不导入收盘价《＝0的股票行情
					 */
					if(dblPrice<=0){
					   continue;
					}
					if ((strSecuritiesName.indexOf("配售")) != -1
								|| (strSecuritiesName.indexOf("放弃") != -1)
								|| (strSecuritiesName.indexOf("国债") != -1)
								|| (strSecuritiesName.indexOf("转债") != -1)
								|| ((strSecuritiesName.indexOf("N") != -1)
									&& (dblPrice == 0.0))) {
						continue;
					}	
					
					
					strSQL = "insert into SEC_SecuritiesMarket(securitiesCode, CloseDate,TypeID, securitiesName, CLOSEPRICE,";
					strSQL += " StatusID, InputUserID, InputDate, UpdateUserID, UpdateDate )";
					strSQL += " values ('"
						+ strSecuritiesCode
						+ "', to_date('"
						+ strDate
						+ "', 'yy-mm-dd'), "
						+ SECURITIESMARKET_STOCK
						+ ", '"
						+ strSecuritiesName
						+ "', "
						+ dblPrice
						+ ", 1,";
					strSQL += lUserID + ", " + " sysdate, null, null)";
					if (intTest < 2) {
						Log.print("行情数据导入ｓｑｌ＝" + strSQL);
						intTest = intTest + 1;
					}
					vctSQL.add(strSQL);
				}
			}
			// 批处理导入所有行情数据
			intReturn = this.executeBatchByTransction(m_Conn, vctSQL);
			//接着删除到入的数据有关债券的信息
			//deleteBondMarketInfo(dtDate);
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		}

		return intReturn;
	}

	/**
	 * 将从txt文件读取的数据，并将其中的行情数据成批导入到证券系统的数据库中
	 * 
	 * @return int[] 返回所有记录导入执行的结果数组
	 * @throws MarketException
	 */
	public int[] addForCnmef(Vector vctMarketInfo, long lUserID) throws MarketException {
		int[] intReturn = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		Timestamp dtDate = null;
		String strSecuritiesCode = ""; // 股票代码
		String strSecuritiesName = ""; // 股票名称
		double dblPrice = 0; // 收盘价
		Vector vctSQL = new Vector();
		MarketInfo objMarketInfo = null;

		try {
			String strTemp = "";
			String strDate = "";
			int index = 0;
			int intTest = 1;
			// test data for printing the SQL of insert securitiesMarketInfo
			for (int i = 0; i < vctMarketInfo.size(); i++) {
				strTemp = (String) vctMarketInfo.elementAt(i);
				//Log.print("首先处理第一行的信息 ，日期！！！！");
				if (i == 0 && strTemp.length() > 0) {
					index = strTemp.indexOf(",");
					strDate = strTemp.substring(0, index);
					strTemp = strTemp.substring(index + 1);
					if (strDate != null && strDate.equals("date")) {
						index = strTemp.indexOf(",");
						Log.print("the first line &&& index = " + index);
						strDate = strTemp.substring(0, index);
						Log.print("the first line &&& strTemp = " + strTemp);
						Log.print("the first line &&& strDate = " + strDate);
						dtDate = DataFormat.getDateTime(strDate);
					}
					// 如果当日的行情数据已经导入，则先删除上一次导入的股票行情数据
					deleteMarketInfo(dtDate, SECURITIESMARKET_STOCK);
				} else if (i > 0 && strTemp.length() > 0) {
					index = strTemp.indexOf(",");
					strSecuritiesCode = strTemp.substring(0, index).trim();
                  //胡志强（2004-11-5）修改让所有的证券都能导入,但是指数类得都不能导入！！！！（刘惠军）

                    //判断行情数据证券代码是否在行情数据导入删除串中，如果是则不导入数据库
					if (SEC_MARKET_REMOVE_CNMEF.indexOf(strSecuritiesCode) != -1) {
						 Log.print("不能导入的证券号码："+strSecuritiesCode);
						 continue;
					}

					if (index != -1 && index <= strTemp.length()) {
						//去掉第一位的编码7或者8
						strSecuritiesCode = strTemp.substring(1, index).trim();
						strTemp = strTemp.substring(index + 1);
						index = strTemp.indexOf(",");
						if (index != -1 && index <= strTemp.length()) {
							strSecuritiesName =
								strTemp.substring(0, index).trim();
							strTemp = strTemp.substring(index + 1);
							//股票收盘价
							dblPrice = Double.parseDouble(strTemp.trim());
						}
					}
//胡志强（2004-11-3）修改让所有的证券都能导入
/*
					//刘惠军 2004-07-20 修改，不导入收盘价《＝0的股票行情
					if(dblPrice<=0){
					   continue;
					}
					if ((strSecuritiesName.indexOf("配售")) != -1
								|| (strSecuritiesName.indexOf("放弃") != -1)
								|| (strSecuritiesName.indexOf("国债") != -1)
								|| (strSecuritiesName.indexOf("转债") != -1)
								|| ((strSecuritiesName.indexOf("N") != -1)
									&& (dblPrice == 0.0))) {
						continue;
					}	
*/
					strSQL = "insert into SEC_SecuritiesMarket(securitiesCode, CloseDate,TypeID, securitiesName, CLOSEPRICE,";
					strSQL += " StatusID, InputUserID, InputDate, UpdateUserID, UpdateDate )";
					strSQL += " values ('"
						+ strSecuritiesCode
						+ "', to_date('"
						+ strDate
						+ "', 'yy-mm-dd'), "
						+ SECURITIESMARKET_STOCK
						+ ", '"
						+ strSecuritiesName
						+ "', "
						+ dblPrice
						+ ", 1,";
					strSQL += lUserID + ", " + " sysdate, null, null)";
					if (intTest < 2) {
						Log.print("行情数据导入ｓｑｌ＝" + strSQL);
						intTest = intTest + 1;
					}
					vctSQL.add(strSQL);
				}
			}
			// 批处理导入所有行情数据
			intReturn = this.executeBatchByTransction(m_Conn, vctSQL);
			//接着删除到入的数据有关债券的信息
			//deleteBondMarketInfo(dtDate);
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		}

		return intReturn;
	}

	/**
	 * 校验所要导入的行情数据的日期是否已经做过库存更新
	 * 
	 * @return boolean 已做过更新返回 true；否则返回 false
	 * @throws MarketException
	 */
	private boolean checkDate(Timestamp dtDate) throws MarketException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		boolean blnExist = false;

		try {
			if (dtDate == null) {
				throw new MarketException("传入的日期为空！");
			}
			strSQL = "select count(*) from SEC_DailyStock where StockDate = ?";

			ps = m_Conn.prepareStatement(strSQL);
			ps.setTimestamp(1, dtDate);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				if (rs.getLong(1) > 0) {
					blnExist = true;
				}
			}
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				this.cleanup(rs);
				this.cleanup(ps);
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}

		return blnExist;
	}

	/**
	 * 删除当天上次导入的所有行情数据
	 * 
	 * @return void
	 * @throws MarketException
	 */
	public void deleteMarketInfo(Timestamp dtDate, long securitiesType)
		throws MarketException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;

		try {
			if (dtDate == null) {
				throw new MarketException("传入的日期为空！");
			}
			strSQL = "delete from SEC_SecuritiesMarket where TypeID = " + securitiesType + " and  to_char(CloseDate, 'yyyy-mm-dd') = '"+DataFormat.getDateString(dtDate)+"'";
			ps = m_Conn.prepareStatement(strSQL);
			//ps.setString(1, DataFormat.getDateString(dtDate));
			int deleteSum = ps.executeUpdate();
			Log.print("类型是："+securitiesType+" 的日期是 "+dtDate+" 删除了"+deleteSum+"条");
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				this.cleanup(ps);
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}
	}

	/**
	 * 查询行情数据
	 * 
	 * @return Collection 返回满足条件的行情数据集合
	 * @throws MarketException
	 */
	public PageLoader select(MarketConditionInfo marketConditionInfo) throws MarketException {
		//设置SELECT
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append(" a.*, b.TypeID SecuritiesTypeID, b.ID SecuritiesID \n");
		//设置FROM
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SEC_SecuritiesMarket a, SEC_Securities b \n");
		//设置WHERE
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" a.StatusID > 0 \n");
		m_sbWhere.append(" and (a.securitiesCode = b.securitiesCode1 \n");
		m_sbWhere.append(" or a.securitiesCode = b.securitiesCode2 \n");
		m_sbWhere.append(" or a.securitiesCode = b.securitiesCode3) \n");
		if (marketConditionInfo.getSecCloseStart() != null) {
			m_sbWhere.append(" and a.CloseDate >= TO_DATE('"+marketConditionInfo.getSecCloseStart()+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		if (marketConditionInfo.getSecCloseEnd() != null) {
			m_sbWhere.append(" and a.CloseDate <= TO_DATE('"+marketConditionInfo.getSecCloseEnd()+"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		if (marketConditionInfo.getSecSecuritiesCode() != null && !marketConditionInfo.getSecSecuritiesCode().equals("")) {
			m_sbWhere.append(" and a.securitiesCode = '"+marketConditionInfo.getSecSecuritiesCode()+"' \n");
		}
		if (marketConditionInfo.getSecSecuritiesTypeID() > -1) {
			m_sbWhere.append(" and b.TypeID = "+marketConditionInfo.getSecSecuritiesTypeID()+" \n");
		}
		//设置ORDER
		m_sbOrderBy = new StringBuffer();
		this.setOrderBy(marketConditionInfo);

        // 获取PageLoader对象
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        // 初始化PageLoader对象、实现查询和分页
        pageLoader.initPageLoader(
            new AppContext(),
            m_sbFrom.toString(),
            m_sbSelect.toString(),
            m_sbWhere.toString(),
            (int) Constant.PageControl.CODE_PAGELINECOUNT,
            "com.iss.itreasury.securities.market.dataentity.MarketInfo",
            null);
        pageLoader.setOrderBy(" " + this.getOrderBy().toString() + " ");

        return pageLoader;
	}

    /**
     * Returns the orderBy.
     * @return StringBuffer
     */
    public StringBuffer getOrderBy() {
        return m_sbOrderBy;
    }

    /**
     * Sets the orderBy.
     * @param orderBy The orderBy to set
     */
    public void setOrderBy(MarketConditionInfo marketConditionInfo) {
        // create orderby
        m_sbOrderBy = new StringBuffer();
		switch ((int) marketConditionInfo.getSecOrderParam()) {
			case (int) OrderBySecuritiesCode: // 按合同编号排序
				m_sbOrderBy.append(" order by a.securitiesCode");
				break;
			case (int) OrderBySecuritiesName: // 按合同名称排序
				m_sbOrderBy.append(" order by a.securitiesName");
				break;
			case (int) OrderByTypeID: // 按证券类别
				m_sbOrderBy.append(" order by b.TypeID");
				break;
			case (int) OrderByNetClosePrice: // 按净价收盘价排序
				m_sbOrderBy.append(" order by a.NetClosePrice");
				break;
			case (int) OrderByClosePrice: // 按全价收盘价排序
				m_sbOrderBy.append(" order by a.CLOSEPRICE");
				break;
			case (int) OrderByCloseDate: // 按收盘日
				m_sbOrderBy.append(" order by a.CloseDate");
				break;
			case (int) OrderByCounterpartID: // 按基金管理公司
				m_sbOrderBy.append(" order by a.CounterpartID");
				break;
			default :
				m_sbOrderBy.append(" order by a.securitiesCode");
		}
		if (marketConditionInfo.getSecAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
			m_sbOrderBy.append(" desc");
		} else if (marketConditionInfo.getSecAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_ASC) {
			m_sbOrderBy.append(" asc");
		}
    }

	/**
	 * 查询债券(国债现券,企业债券现券,金融债)行情数据
	 * 
	 * @param strDate 收盘日期
	 * @return ArrayList   返回债券行情数据集合
	 * @throws throws MarketException
	 */
	public ArrayList selectBond(String strDate) throws MarketException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList listResult = new ArrayList();
		StringBuffer strSQL = new StringBuffer();
		MarketInfo marketInfo = null;

		try {
			//设置查询SQL语句
			strSQL.append("SELECT a.* FROM SEC_SECURITIESMARKET a, \n");
			strSQL.append("( ");
			strSQL.append("  SELECT DISTINCT securitiesCode1 FROM SEC_Securities");
			strSQL.append("  WHERE TYPEID IN (5, 6, 7, 8) ");
			strSQL.append(") b ");
			strSQL.append("WHERE a.securitiesCode = b.securitiesCode1 AND a.StatusID = 1 ");
			if (strDate == null || strDate.length() == 0 || strDate.equalsIgnoreCase("null")) {
				strSQL.append(" AND a.CloseDate = (SELECT MAX(CloseDate) from SEC_SECURITIESMARKET)");
			} else {
				strSQL.append(" AND a.CloseDate = TO_DATE('"+strDate+"','YYYY/MM/DD HH24:MI:SS') \n");
			}
			//查询债券行情数据
			ps = this.m_Conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			//保存查询结果
			while (rs != null && rs.next()) {
				marketInfo = new MarketInfo();
				marketInfo.setSecuritiesCode(DataFormat.formatString(rs.getString("securitiesCode")));
				marketInfo.setSecuritiesName(DataFormat.formatString(rs.getString("securitiesName")));
				marketInfo.setCloseDate(rs.getTimestamp("CloseDate"));
				marketInfo.setNetClosePrice(rs.getDouble("NetClosePrice"));
				marketInfo.setClosePrice(rs.getDouble("CLOSEPRICE"));

				listResult.add(marketInfo);
			}
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				this.cleanup(rs);
				this.cleanup(ps);
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}

		return listResult;
	}

	/**
	 * 查询某一行情数据的详细信息
	 * 
	 * @return MarketInfo 返回该行情数据详细信息
	 * @throws MarketException，MarketRuntimeException
	 */
	public MarketInfo selectDetail(MarketInfo marketInfo) throws MarketException, MarketRuntimeException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		MarketInfo resMarketInfo = null;

		try {
			//设置查询语句
			strSQL.append("SELECT a.*, b.Name as securitiesName, b.ID as securitiesID, \n");
			strSQL.append("c.sName as InputUserName, d.sName as UpdateUserName \n");
			strSQL.append("FROM Sec_SecuritiesMarket a, SEC_Securities b, UserInfo c, UserInfo d \n");
			strSQL.append("WHERE a.InputUserID = c.ID(+) AND a.UpdateUserID = d.ID(+) \n");
			strSQL.append("	AND a.securitiesCode = b.securitiesCode1(+) AND a.StatusID > 0 \n");
			String strSecuritiesCode = marketInfo.getSecuritiesCode();
			if (strSecuritiesCode != null && !strSecuritiesCode.equals("")) {
				strSQL.append("	AND a.securitiesCode = '"+strSecuritiesCode+"' \n");
			}
			Timestamp CloseDate = marketInfo.getCloseDate();
			if (CloseDate != null) {
				strSQL.append("	AND a.CloseDate = TO_DATE('"+DataFormat.getDateString(CloseDate)+"','YYYY/MM/DD HH24:MI:SS') \n");
				Log.print("SQL="+strSQL);

				ps = this.m_Conn.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();

				if (rs.next()) {
					resMarketInfo = new MarketInfo();
					//基本信息
					resMarketInfo.setSecuritiesID(rs.getLong("securitiesID"));
					resMarketInfo.setCounterpartID(rs.getLong("counterpartID"));
					resMarketInfo.setSecuritiesTypeID(rs.getLong("typeID"));
					resMarketInfo.setSecuritiesCode(DataFormat.formatString(rs.getString("securitiesCode")));
					resMarketInfo.setSecuritiesName(DataFormat.formatString(rs.getString("securitiesName")));
					resMarketInfo.setClosePrice(rs.getDouble("CLOSEPRICE"));
					resMarketInfo.setNetClosePrice(rs.getDouble("NetClosePrice"));
					resMarketInfo.setCloseDate(rs.getTimestamp("CloseDate"));
					resMarketInfo.setStatusID(rs.getLong("StatusID"));
					resMarketInfo.setInputUserID(rs.getLong("InputUserID"));
					resMarketInfo.setInputDate(rs.getTimestamp("InputDate"));
					resMarketInfo.setUpdateUserID(rs.getLong("UpdateUserID"));
					resMarketInfo.setUpdateDate(rs.getTimestamp("UpdateDate"));

					//附加显示信息
					resMarketInfo.setInputUserName(DataFormat.formatString(rs.getString("InputUserName")));
					resMarketInfo.setUpdateUserName(DataFormat.formatString(rs.getString("UpdateUserName")));
				} else {
					throw new MarketRuntimeException("E007", new String[]{"行情数据"});
				}
			} else {
				throw new MarketException("没有日期条件或程序处理出错！");
			}
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				this.cleanup(rs);
				this.cleanup(ps);
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}

		return resMarketInfo;
	}

	/**
	 * 修改债券(国债现券,企业债券现券,金融债)行情数据的净价和全价
	 * 
	 * @param  listMarketInfo 行情数据实体对象
	 * @return nothing
	 * @exception throws MarketException, MarketRuntimeException
	 */
	public void updateBondPrice(List listMarketInfo) throws MarketException, MarketRuntimeException {
		PreparedStatement ps = null;
		StringBuffer strSQL = new StringBuffer();
		MarketInfo marketInfo = null;

		try {
			if (listMarketInfo == null) {
				return;
			}
			//执行修改操作
			for (int i = 0; i < listMarketInfo.size(); i++) {
				marketInfo = (MarketInfo) listMarketInfo.get(i);

				strSQL.append("UPDATE SEC_SECURITIESMARKET SET \n");
				strSQL.append("NetClosePrice = "+marketInfo.getNetClosePrice()+", \n");
				strSQL.append("ClosePrice = "+marketInfo.getClosePrice()+" \n");
				strSQL.append("WHERE 1 = 1 \n");
				strSQL.append("	AND SecuritiesCode = '"+marketInfo.getSecuritiesCode()+"' \n");
				strSQL.append("	AND CloseDate = TO_DATE('"+DataFormat.getDateString(marketInfo.getCloseDate())+"','YYYY/MM/DD HH24:MI:SS') \n");

				ps = this.m_Conn.prepareStatement(strSQL.toString());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				this.cleanup(ps);
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}
	}

	/**
	 * 修改债券(国债现券,企业债券现券,金融债,可转债)行情数据的净价
	 * 
	 * @param  listMarketInfo 行情数据实体对象
	 * @return nothing
	 * @exception throws MarketException, MarketRuntimeException
	 */
	public void updateNetPrice(List listMarketInfo) throws MarketException, MarketRuntimeException {
		PreparedStatement ps = null;
		StringBuffer strSQL = new StringBuffer();
		MarketInfo marketInfo = null;

		try {
			if (listMarketInfo == null) {
				return;
			}
			//执行修改操作
			for (int i = 0; i < listMarketInfo.size(); i++) {
				marketInfo = (MarketInfo) listMarketInfo.get(i);

				strSQL.append("UPDATE SEC_SECURITIESMARKET SET \n");
				strSQL.append("NetClosePrice = "+marketInfo.getNetClosePrice()+" \n");
				strSQL.append("WHERE 1 = 1 \n");
				strSQL.append("	AND SecuritiesCode = '"+marketInfo.getSecuritiesCode()+"' \n");
				strSQL.append("	AND CloseDate = TO_DATE('"+DataFormat.getDateString(marketInfo.getCloseDate())+"','YYYY/MM/DD HH24:MI:SS') \n");

				ps = this.m_Conn.prepareStatement(strSQL.toString());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				this.cleanup(ps);
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}
	}

	/**
	 * 更新行情数据
	 * 
	 * @param  MarketInfo marketInfo
	 * @return int
	 * @exception throws MarketException
	 */
	public int update(MarketInfo marketInfo) throws MarketException {
		int nReturn = 0;
		PreparedStatement ps = null;
		StringBuffer strSQL = new StringBuffer();

		try {
			strSQL.append("UPDATE SEC_SecuritiesMarket SET \n");
			strSQL.append("NetClosePrice = "+marketInfo.getNetClosePrice()+", \n");
			strSQL.append("CLOSEPRICE = "+marketInfo.getClosePrice()+", \n");
			strSQL.append("UpdateUserID = "+marketInfo.getUpdateUserID()+", \n");
			if (marketInfo.getSecuritiesTypeID() == SECConstant.SecuritiesType.MUTUAL_FUND) {
				strSQL.append("CounterpartID = "+marketInfo.getCounterpartID()+", \n");
			}
			strSQL.append("UpdateDate = sysdate \n");
			strSQL.append("WHERE 1 = 1 \n");
			strSQL.append("	AND securitiesCode = '"+marketInfo.getSecuritiesCode()+"' \n");
			strSQL.append("	AND CloseDate = TO_DATE('"+DataFormat.getDateString(marketInfo.getCloseDate())+"', 'YYYY/MM/DD HH24:MI:SS') \n");

			Log.print("SQL="+strSQL.toString());

			ps = m_Conn.prepareStatement(strSQL.toString());
			nReturn = ps.executeUpdate();
		} catch (Exception ex) {
		} finally {
			try {
				this.cleanup(ps);
			} catch (Exception ex) {
			}
		}

		return nReturn;
	}

	/**
	 * 增加债券(国债现券,企业债券现券,金融债)行情数据的净价和全价
	 * 
	 * @param  listMarketInfo 行情数据实体对象
	 * @return nothing
	 * @exception throws MarketException, MarketRuntimeException
	 */
	public void saveBondMarket(List listMarketInfo) throws MarketException, MarketRuntimeException {
		PreparedStatement ps = null;
		String strSQL = null;
		Vector vctSQL = new Vector();
		MarketInfo objMarketInfo = null;
		String closeDate = "";
		long lUserID = -1;

		try {
			if (listMarketInfo == null) {
				return;
			}
			int index = 1;
			for (int i = 0; i < listMarketInfo.size(); i++) {
				objMarketInfo = (MarketInfo) listMarketInfo.get(i);
				double netPrice = objMarketInfo.getNetClosePrice();
				double price = objMarketInfo.getClosePrice();
				closeDate =	DataFormat.getDateString(objMarketInfo.getCloseDate());
				String securitiesCode = objMarketInfo.getSecuritiesCode();
				String securitiesName = objMarketInfo.getSecuritiesName();
				lUserID = objMarketInfo.getInputUserID();
				strSQL = "insert into SEC_SecuritiesMarket(securitiesCode, CloseDate,TypeID, securitiesName, NetClosePrice,";
				strSQL += " CLOSEPRICE, StatusID, InputUserID, InputDate, UpdateUserID, UpdateDate )";
				strSQL += " values ('"
					+ securitiesCode
					+ "', to_date('"
					+ closeDate
					+ "', 'yy-mm-dd'), "
					+ SECURITIESMARKET_BOND
					+ ", '"
					+ securitiesName
					+ "', "
					+ netPrice
					+ ", "
					+ price
					+ ", 1,";
				strSQL += lUserID + ", " + " sysdate, null, null)";
				if (index < 2) {
					Log.print("债券导入strSQL= " + strSQL);
					index = index + 1;
				}
				vctSQL.add(strSQL);
			}

			/*
			 * 增加在SEC_Securities中的所有的未上市的债券，净价，全价均为：100 2004-05-31 修改
			 *
			 * select * from SEC_Securities where nexchangeCenter=(select Id from  SEC_exchangeCenter where sName = '未上市');
			 */
/**			StringBuffer sbString = new StringBuffer();
		    sbString.append(" INSERT INTO SEC_SecuritiesMarket \n");
			sbString.append("         SElect \n");
			sbString.append("             securitiesCode1 as securitiesCode , \n");
			sbString.append("             to_date('"+ closeDate+ "', 'yy-mm-dd') as CloseDate,  \n");
			sbString.append("             "+ SECURITIESMARKET_BOND + " as  TypeID, \n");
			sbString.append("             Name as securitiesName,\n");
			sbString.append("             100.00 as NetClosePrice , \n");
			sbString.append("             100.00 as CLOSEPRICE, \n");
			sbString.append("             1 as StatusID, \n");
			sbString.append("             "+lUserID +"  as InputUserID, \n");
			sbString.append("             sysdate as InputDate , \n");
			sbString.append("             null as UpdateUserID, \n");
			sbString.append("             null as UpdateDate \n");
			sbString.append("         FROM  SEC_Securities \n");
			sbString.append("         WHERE  exchangeCenterID= \n");
			sbString.append("                (select Id from  SEC_exchangeCenter  \n");
			sbString.append("                           where Name = '未上市')  \n");
			Log.print("未上市债券导入：SQL= "+sbString.toString());
			vctSQL.add(sbString.toString());*/
            //批处理导入所有行情数据
			this.executeBatchByTransction(m_Conn, vctSQL);
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				this.cleanup(ps);
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}
	}

	
	   /**
		 * 导入开放式基金的行情数据的净价
		 * @author hjliu
		 * @param  listMarketInfo 行情数据实体对象
		 * @return nothing
		 * @exception throws MarketException, MarketRuntimeException
		 */
		public void saveOpenFundMarket(List listMarketInfo) throws MarketException, MarketRuntimeException {
			PreparedStatement ps = null;
			String strSQL = null;
			Vector vctSQL = new Vector();
			MarketInfo objMarketInfo = null;
			String closeDate = "";
			long lUserID = -1;

			try {
				if (listMarketInfo == null) {
					return;
				}
				int index = 1;
				for (int i = 0; i < listMarketInfo.size(); i++) {
					objMarketInfo = (MarketInfo) listMarketInfo.get(i);
					long   countparterID = objMarketInfo.getCounterpartID();
					double netPrice = objMarketInfo.getNetClosePrice();
					double price = 0.0;
					closeDate =	DataFormat.getDateString(objMarketInfo.getCloseDate());
					String securitiesCode = objMarketInfo.getSecuritiesCode();
					String securitiesName = objMarketInfo.getSecuritiesName();
					lUserID = objMarketInfo.getInputUserID();
					strSQL = "insert into SEC_SecuritiesMarket(securitiesCode, CloseDate,TypeID, COUNTERPARTID,securitiesName, NetClosePrice,";
					strSQL += " CLOSEPRICE, StatusID, InputUserID, InputDate, UpdateUserID, UpdateDate )";
					strSQL += " values ('"
						+ securitiesCode
						+ "', to_date('"
						+ closeDate
						+ "', 'yy-mm-dd'), "
						+ SECConstant.SecuritiesType.MUTUAL_FUND
						+ ","
					    + countparterID 
					    + ", '"
						+ securitiesName
						+ "', "
						+ netPrice
						+ ", "
						+ price
						+ ", 1,";
					strSQL += lUserID + ", " + " sysdate, null, null)";
					//if (index < 2) {
						Log.print("开放式基金导入strSQL= " + strSQL);
						index = index + 1;
					//}
					vctSQL.add(strSQL);
				}

				/*
				 * 增加在SEC_Securities中的所有的未上市的债券，净价，全价均为：100 2004-05-31 修改
				 *
				 * select * from SEC_Securities where nexchangeCenter=(select Id from  SEC_exchangeCenter where sName = '未上市');
				 */
	/**			StringBuffer sbString = new StringBuffer();
				sbString.append(" INSERT INTO SEC_SecuritiesMarket \n");
				sbString.append("         SElect \n");
				sbString.append("             securitiesCode1 as securitiesCode , \n");
				sbString.append("             to_date('"+ closeDate+ "', 'yy-mm-dd') as CloseDate,  \n");
				sbString.append("             "+ SECURITIESMARKET_BOND + " as  TypeID, \n");
				sbString.append("             Name as securitiesName,\n");
				sbString.append("             100.00 as NetClosePrice , \n");
				sbString.append("             100.00 as CLOSEPRICE, \n");
				sbString.append("             1 as StatusID, \n");
				sbString.append("             "+lUserID +"  as InputUserID, \n");
				sbString.append("             sysdate as InputDate , \n");
				sbString.append("             null as UpdateUserID, \n");
				sbString.append("             null as UpdateDate \n");
				sbString.append("         FROM  SEC_Securities \n");
				sbString.append("         WHERE  exchangeCenterID= \n");
				sbString.append("                (select Id from  SEC_exchangeCenter  \n");
				sbString.append("                           where Name = '未上市')  \n");
				Log.print("未上市债券导入：SQL= "+sbString.toString());
				vctSQL.add(sbString.toString());*/
				//批处理导入所有行情数据
				this.executeBatchByTransction(m_Conn, vctSQL);
			} catch (SQLException e) {
				throw new MarketException(e.getMessage());
			} finally {
				try {
					this.cleanup(ps);
				} catch (Exception e) {
					throw new MarketException(e.getMessage());
				}
			}
		}

	/**
	 * 增加开放式基金行情数据
	 * 
	 * @param  MarketInfo info
	 * @return int
	 * @exception throws MarketException, MarketRuntimeException
	 */
	public int saveFundMarket(MarketInfo info) throws MarketException, MarketRuntimeException {
		int nReturn = 0;
		PreparedStatement ps = null;
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("INSERT INTO SEC_SecuritiesMarket( \n");
		strSQL.append("	SECURITIESCODE, \n");
		strSQL.append("	CLOSEDATE, \n");
		strSQL.append("	SECURITIESNAME, \n");
		strSQL.append("	NETCLOSEPRICE, \n");
		strSQL.append("	CLOSEPRICE, \n");
		strSQL.append("	STATUSID, \n");
		strSQL.append("	INPUTUSERID, \n");
		strSQL.append("	INPUTDATE, \n");
		strSQL.append("	UPDATEUSERID, \n");
		strSQL.append("	UPDATEDATE, \n");
		strSQL.append("	COUNTERPARTID, \n");
		strSQL.append("	TYPEID \n");
		strSQL.append(") VALUES ( \n");
		strSQL.append("	'"+info.getSecuritiesCode()+"', \n");
		strSQL.append("	TO_DATE('"+DataFormat.getDateString(info.getCloseDate())+"','YYYY/MM/DD HH24:MI:SS'), \n");
		if (info.getSecuritiesName()==null || info.getSecuritiesName()=="") {
			strSQL.append("	"+"NULL"+", \n");
		} else {
			strSQL.append("	'"+info.getSecuritiesName()+"', \n");
		}
		strSQL.append("	"+info.getNetClosePrice()+", \n");
		strSQL.append("	"+info.getClosePrice()+", \n");
		strSQL.append("	"+info.getStatusID()+", \n");
		strSQL.append("	"+info.getInputUserID()+", \n");
		strSQL.append("	sysdate, \n");
		strSQL.append("	"+info.getUpdateUserID()+", \n");
		strSQL.append("	NULL, \n");
		strSQL.append("	"+info.getCounterpartID()+", \n");
		strSQL.append("	"+info.getSecuritiesTypeID()+" \n");
		strSQL.append(") \n");

		Log.print("SQL="+strSQL.toString());

		try {
			ps = this.m_Conn.prepareStatement(strSQL.toString());
			nReturn = ps.executeUpdate();
		} catch (Exception e) {
		} finally {
			try {
				this.cleanup(ps);
			} catch (Exception e) {
			}
		}

		return nReturn;
	}
}