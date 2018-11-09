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
	// ����ͬ�������
	public static final long OrderBySecuritiesCode = 1;
	// ����ͬ��������
	public static final long OrderBySecuritiesName = 2;
	// ��֤ȯ���
	public static final long OrderByTypeID = 3;
	// ���������̼�����
	public static final long OrderByNetClosePrice = 4;
	// ��ȫ�����̼�����
	public static final long OrderByClosePrice = 5;
	// ��������
	public static final long OrderByCloseDate = 6;
	// ���������˾
	public static final long OrderByCounterpartID = 7;

	private static final long SECURITIESMARKET_STOCK = 1; //��Ʊ�������ݵ���
	private static final long SECURITIESMARKET_BOND = 2; //ծȯ�������ݵ���
	private static final long SECURITIESMARKET_OPENFUND = 3; //����ʽ�����������ݵ���
	public static final String SEC_UPLOAD_PATH = "securities/"; // ֤ȯ�ϴ�·��

	private Connection m_Conn = null;
    private StringBuffer m_sbOrderBy = null;
    private Log4j logger = null;

    //public static final String SEC_MARKET_REMOVE = "";

	// �������ݵ���ɾ��֤ȯ����--����֤ȯ 2004-11-05 ���ݾ�
    public static final String SEC_MARKET_REMOVE_HN =
        //����ɾ��?
        "7000001,7000002,7000012,7000013," +
        //ѡ��ɾ��
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

//  �������ݵ���ɾ��֤ȯ����--�������� 2004-11-05 ���ݾ�
    public static final String SEC_MARKET_REMOVE_CNMEF =
        //����ɾ��?
        "7000001,7000002,7000012,7000013," +
        //ѡ��ɾ��
        "7000003,7000004,7000005,7000006,7000007,7000008," +
        "7000010,7000011,8399001,8399002,8399003,8399004," +
        "8399106,8399107,8399108,8399110,8399120,8399130," +
        "8399131,8399132,8399133,8399134,8399135,8399136," +
        "8399137,8399138,8399139,8399140,8399150,8399160," +
        "8399170,8399180,8399190,8399200,8399210,8399220," +
        "8399230,8399305,8399481,8399005";    
	   

	/**
	 * ���캯��
	 *
	 * @param  conn
	 * @return nothing
	 * @throws Exception
	 */
    public MarketDAO(Connection conn) {
		this.m_Conn = conn;
	}

	/**
	 * ��������(��������)
	 *
	 * @param  nothing
	 * @return nothing
	 * @throws Exception
	 */
    public void CloseMarketDAO() throws Exception{
		this.cleanup(this.m_Conn);
	}

	/**
	 * ��������(������)
	 *
	 * @param  conn
	 * @return nothing
	 * @throws Exception
	 */
    public void CloseMarketDAO(Connection conn) throws Exception{
		this.cleanup(conn);
	}

    /**
     * �����������ʵ����������
     * @param conn
     * @param vtSQL Ҫ�������SQL��伯��
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
	 * ����txt�ļ���ȡ�����ݣ��������е��������ݳ������뵽֤ȯϵͳ�����ݿ���
	 * 
	 * @return int[] �������м�¼����ִ�еĽ������
	 * @throws MarketException
	 */
	public int[] add(Vector vctMarketInfo, long lUserID) throws MarketException {
		int[] intReturn = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		Timestamp dtDate = null;
		String strSecuritiesCode = ""; // ��Ʊ����
		String strSecuritiesName = ""; // ��Ʊ����
		double dblPrice = 0; // ���̼�
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
				//Log.print("���ȴ����һ�е���Ϣ �����ڣ�������");
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
					// ������յ����������Ѿ����룬����ɾ����һ�ε���Ĺ�Ʊ��������
					deleteMarketInfo(dtDate, SECURITIESMARKET_STOCK);
				} else if (i > 0 && strTemp.length() > 0) {
					index = strTemp.indexOf(",");
					strSecuritiesCode = strTemp.substring(0, index).trim();
                    //�ж���������֤ȯ�����Ƿ����������ݵ���ɾ�����У�������򲻵������ݿ�
					if (SEC_MARKET_REMOVE_HN.indexOf(strSecuritiesCode) != -1) {
						 Log.print("���ܵ����֤ȯ���룺"+strSecuritiesCode);
						 continue;
					}
					if (index != -1 && index <= strTemp.length()) {
						//ȥ����һλ�ı���7����8
						strSecuritiesCode = strTemp.substring(1, index).trim();
						strTemp = strTemp.substring(index + 1);
						index = strTemp.indexOf(",");
						if (index != -1 && index <= strTemp.length()) {
							strSecuritiesName =
								strTemp.substring(0, index).trim();
							strTemp = strTemp.substring(index + 1);
							//��Ʊ���̼�
							dblPrice = Double.parseDouble(strTemp.trim());
						}
					}
					
					/*
					 * ���ݾ� 2004-07-20 �޸ģ����������̼ۡ���0�Ĺ�Ʊ����
					 */
					if(dblPrice<=0){
					   continue;
					}
					if ((strSecuritiesName.indexOf("����")) != -1
								|| (strSecuritiesName.indexOf("����") != -1)
								|| (strSecuritiesName.indexOf("��ծ") != -1)
								|| (strSecuritiesName.indexOf("תծ") != -1)
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
						Log.print("�������ݵ�����죽" + strSQL);
						intTest = intTest + 1;
					}
					vctSQL.add(strSQL);
				}
			}
			// ��������������������
			intReturn = this.executeBatchByTransction(m_Conn, vctSQL);
			//����ɾ������������й�ծȯ����Ϣ
			//deleteBondMarketInfo(dtDate);
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		}

		return intReturn;
	}

	/**
	 * ����txt�ļ���ȡ�����ݣ��������е��������ݳ������뵽֤ȯϵͳ�����ݿ���
	 * 
	 * @return int[] �������м�¼����ִ�еĽ������
	 * @throws MarketException
	 */
	public int[] addForCnmef(Vector vctMarketInfo, long lUserID) throws MarketException {
		int[] intReturn = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		Timestamp dtDate = null;
		String strSecuritiesCode = ""; // ��Ʊ����
		String strSecuritiesName = ""; // ��Ʊ����
		double dblPrice = 0; // ���̼�
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
				//Log.print("���ȴ����һ�е���Ϣ �����ڣ�������");
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
					// ������յ����������Ѿ����룬����ɾ����һ�ε���Ĺ�Ʊ��������
					deleteMarketInfo(dtDate, SECURITIESMARKET_STOCK);
				} else if (i > 0 && strTemp.length() > 0) {
					index = strTemp.indexOf(",");
					strSecuritiesCode = strTemp.substring(0, index).trim();
                  //��־ǿ��2004-11-5���޸������е�֤ȯ���ܵ���,����ָ����ö����ܵ��룡�����������ݾ���

                    //�ж���������֤ȯ�����Ƿ����������ݵ���ɾ�����У�������򲻵������ݿ�
					if (SEC_MARKET_REMOVE_CNMEF.indexOf(strSecuritiesCode) != -1) {
						 Log.print("���ܵ����֤ȯ���룺"+strSecuritiesCode);
						 continue;
					}

					if (index != -1 && index <= strTemp.length()) {
						//ȥ����һλ�ı���7����8
						strSecuritiesCode = strTemp.substring(1, index).trim();
						strTemp = strTemp.substring(index + 1);
						index = strTemp.indexOf(",");
						if (index != -1 && index <= strTemp.length()) {
							strSecuritiesName =
								strTemp.substring(0, index).trim();
							strTemp = strTemp.substring(index + 1);
							//��Ʊ���̼�
							dblPrice = Double.parseDouble(strTemp.trim());
						}
					}
//��־ǿ��2004-11-3���޸������е�֤ȯ���ܵ���
/*
					//���ݾ� 2004-07-20 �޸ģ����������̼ۡ���0�Ĺ�Ʊ����
					if(dblPrice<=0){
					   continue;
					}
					if ((strSecuritiesName.indexOf("����")) != -1
								|| (strSecuritiesName.indexOf("����") != -1)
								|| (strSecuritiesName.indexOf("��ծ") != -1)
								|| (strSecuritiesName.indexOf("תծ") != -1)
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
						Log.print("�������ݵ�����죽" + strSQL);
						intTest = intTest + 1;
					}
					vctSQL.add(strSQL);
				}
			}
			// ��������������������
			intReturn = this.executeBatchByTransction(m_Conn, vctSQL);
			//����ɾ������������й�ծȯ����Ϣ
			//deleteBondMarketInfo(dtDate);
		} catch (SQLException e) {
			throw new MarketException(e.getMessage());
		}

		return intReturn;
	}

	/**
	 * У����Ҫ������������ݵ������Ƿ��Ѿ�����������
	 * 
	 * @return boolean ���������·��� true�����򷵻� false
	 * @throws MarketException
	 */
	private boolean checkDate(Timestamp dtDate) throws MarketException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		boolean blnExist = false;

		try {
			if (dtDate == null) {
				throw new MarketException("���������Ϊ�գ�");
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
	 * ɾ�������ϴε����������������
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
				throw new MarketException("���������Ϊ�գ�");
			}
			strSQL = "delete from SEC_SecuritiesMarket where TypeID = " + securitiesType + " and  to_char(CloseDate, 'yyyy-mm-dd') = '"+DataFormat.getDateString(dtDate)+"'";
			ps = m_Conn.prepareStatement(strSQL);
			//ps.setString(1, DataFormat.getDateString(dtDate));
			int deleteSum = ps.executeUpdate();
			Log.print("�����ǣ�"+securitiesType+" �������� "+dtDate+" ɾ����"+deleteSum+"��");
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
	 * ��ѯ��������
	 * 
	 * @return Collection ���������������������ݼ���
	 * @throws MarketException
	 */
	public PageLoader select(MarketConditionInfo marketConditionInfo) throws MarketException {
		//����SELECT
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append(" a.*, b.TypeID SecuritiesTypeID, b.ID SecuritiesID \n");
		//����FROM
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SEC_SecuritiesMarket a, SEC_Securities b \n");
		//����WHERE
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
		//����ORDER
		m_sbOrderBy = new StringBuffer();
		this.setOrderBy(marketConditionInfo);

        // ��ȡPageLoader����
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        // ��ʼ��PageLoader����ʵ�ֲ�ѯ�ͷ�ҳ
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
			case (int) OrderBySecuritiesCode: // ����ͬ�������
				m_sbOrderBy.append(" order by a.securitiesCode");
				break;
			case (int) OrderBySecuritiesName: // ����ͬ��������
				m_sbOrderBy.append(" order by a.securitiesName");
				break;
			case (int) OrderByTypeID: // ��֤ȯ���
				m_sbOrderBy.append(" order by b.TypeID");
				break;
			case (int) OrderByNetClosePrice: // ���������̼�����
				m_sbOrderBy.append(" order by a.NetClosePrice");
				break;
			case (int) OrderByClosePrice: // ��ȫ�����̼�����
				m_sbOrderBy.append(" order by a.CLOSEPRICE");
				break;
			case (int) OrderByCloseDate: // ��������
				m_sbOrderBy.append(" order by a.CloseDate");
				break;
			case (int) OrderByCounterpartID: // ���������˾
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
	 * ��ѯծȯ(��ծ��ȯ,��ҵծȯ��ȯ,����ծ)��������
	 * 
	 * @param strDate ��������
	 * @return ArrayList   ����ծȯ�������ݼ���
	 * @throws throws MarketException
	 */
	public ArrayList selectBond(String strDate) throws MarketException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList listResult = new ArrayList();
		StringBuffer strSQL = new StringBuffer();
		MarketInfo marketInfo = null;

		try {
			//���ò�ѯSQL���
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
			//��ѯծȯ��������
			ps = this.m_Conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			//�����ѯ���
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
	 * ��ѯĳһ�������ݵ���ϸ��Ϣ
	 * 
	 * @return MarketInfo ���ظ�����������ϸ��Ϣ
	 * @throws MarketException��MarketRuntimeException
	 */
	public MarketInfo selectDetail(MarketInfo marketInfo) throws MarketException, MarketRuntimeException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		MarketInfo resMarketInfo = null;

		try {
			//���ò�ѯ���
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
					//������Ϣ
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

					//������ʾ��Ϣ
					resMarketInfo.setInputUserName(DataFormat.formatString(rs.getString("InputUserName")));
					resMarketInfo.setUpdateUserName(DataFormat.formatString(rs.getString("UpdateUserName")));
				} else {
					throw new MarketRuntimeException("E007", new String[]{"��������"});
				}
			} else {
				throw new MarketException("û��������������������");
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
	 * �޸�ծȯ(��ծ��ȯ,��ҵծȯ��ȯ,����ծ)�������ݵľ��ۺ�ȫ��
	 * 
	 * @param  listMarketInfo ��������ʵ�����
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
			//ִ���޸Ĳ���
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
	 * �޸�ծȯ(��ծ��ȯ,��ҵծȯ��ȯ,����ծ,��תծ)�������ݵľ���
	 * 
	 * @param  listMarketInfo ��������ʵ�����
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
			//ִ���޸Ĳ���
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
	 * ������������
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
	 * ����ծȯ(��ծ��ȯ,��ҵծȯ��ȯ,����ծ)�������ݵľ��ۺ�ȫ��
	 * 
	 * @param  listMarketInfo ��������ʵ�����
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
					Log.print("ծȯ����strSQL= " + strSQL);
					index = index + 1;
				}
				vctSQL.add(strSQL);
			}

			/*
			 * ������SEC_Securities�е����е�δ���е�ծȯ�����ۣ�ȫ�۾�Ϊ��100 2004-05-31 �޸�
			 *
			 * select * from SEC_Securities where nexchangeCenter=(select Id from  SEC_exchangeCenter where sName = 'δ����');
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
			sbString.append("                           where Name = 'δ����')  \n");
			Log.print("δ����ծȯ���룺SQL= "+sbString.toString());
			vctSQL.add(sbString.toString());*/
            //��������������������
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
		 * ���뿪��ʽ������������ݵľ���
		 * @author hjliu
		 * @param  listMarketInfo ��������ʵ�����
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
						Log.print("����ʽ������strSQL= " + strSQL);
						index = index + 1;
					//}
					vctSQL.add(strSQL);
				}

				/*
				 * ������SEC_Securities�е����е�δ���е�ծȯ�����ۣ�ȫ�۾�Ϊ��100 2004-05-31 �޸�
				 *
				 * select * from SEC_Securities where nexchangeCenter=(select Id from  SEC_exchangeCenter where sName = 'δ����');
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
				sbString.append("                           where Name = 'δ����')  \n");
				Log.print("δ����ծȯ���룺SQL= "+sbString.toString());
				vctSQL.add(sbString.toString());*/
				//��������������������
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
	 * ���ӿ���ʽ������������
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