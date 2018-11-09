/*
 * Created on 2004-3-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.setting.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dataentity.CounterPartInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.*;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
/**
 * @author hjliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SEC_CounterPartDAO extends SecuritiesDAO
{

	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	/**
	 * @param tableName
	 */
	public SEC_CounterPartDAO()
	{
		super("SEC_CounterPart");
		// TODO Auto-generated constructor stub
	}

	/*
		 *  (non-Javadoc)
		 * 
		 */
	public String getCounterPartCode() throws SecuritiesDAOException
	{

		String strSQL = "";
		String strCode = "000";
		String lTransactionTypeID = "S050";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2, 4);

		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}

		//strSQL = " select max(nvl(Code,0)) Code from SEC_COUNTERPART where Code like 'CP" + strYear + "%'";
		//刘惠军 2004-07-20 修改，将ID前面补零形成code！！
		//strSQL = " select max(Code) from SEC_COUNTERPART";// where Code like 'CP" + strYear + "%'";
		strSQL = " select max(ID) from SEC_COUNTERPART";
		log4j.debug(strSQL);

		try
		{
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();

			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				log4j.debug(strCode);
				if (strCode != null)
				{
					lCode = Long.parseLong(strCode) + 1;
				}
				else
				{
					lCode = 1;
				}
				//strCode = "CP" + strYear + lTransactionTypeID + DataFormat.formatInt(lCode, 3, true);
				strCode = DataFormat.formatInt(lCode, 4, true);  //2004-07-20 lhj 修改为5位
				log4j.debug(strCode);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException("生成交易对手编号产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SecuritiesDAOException("生成交易对手编号产生错误", e);
		}

		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}

		log4j.debug(":::::::::: ::::strcode::::::" + strCode);

		return strCode;
	}
	/**
	 * 条件查找
	 * @return Collection
	 */
	public Collection findByLinkSearch(String strLinkSearch) throws SecuritiesException
	{
		try
		{
			initDAO();

			String strSQL = "select * from SEC_Counterpart where 1=1 " + strLinkSearch;
			CounterPartInfo info = new CounterPartInfo();

			prepareStatement(strSQL);
			executeQuery();

			Collection c = getDataEntitiesFromResultSet(info.getClass());

			finalizeDAO();
			return c;
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	/**
	 * 从表中取最大的备注编号
	 * @param  nothing
	 * @return String
	 * @exception throws ITreasuryDAOException
	 */
	public String getMaxCode() throws SecuritiesException	{
		String sReturn = "";
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();
			bufSQL.append("SELECT LPAD((NVL(MAX(id),0)+1),5,'0') maxCode FROM  sec_counterpart \n");
			log.info("SQL="+bufSQL.toString());

			localPS = transConn.prepareStatement(bufSQL.toString());
			localRS = localPS.executeQuery();
			while (localRS.next()) {
				sReturn = localRS.getString("maxCode");
			}
			if(localRS != null) {
				localRS.close();
				localRS = null;
			}
			if(localPS != null) {
				localPS.close();
				localPS = null;
			}
			this.finalizeDAO();
		} catch (SQLException e) {

		} catch (ITreasuryDAOException e) {
			new SecuritiesDAOException(e);
		}

		return sReturn==null || sReturn==""?"":sReturn;
	}
	/**
	 * 查找交易对手是否已经在下面三个表中的任意一个用到！！！
	 * 1:通知单:SEC_NoticeForm
	 * 2:交割单:SEC_DeliveryOrder
	 * 3:申请书:SEC_ApplyForm
	 * （！不必确定是在那个表中使用！）
	 * @return lReturn -1:没有用过，大于－1:已经用过
	 */
	public long findIsUseCounterPart(long counterPartID) throws SecuritiesDAOException
	{
		long lReturn = -1;
		try
		{
			initDAO();
			StringBuffer strSQL = new StringBuffer();
			/*
			 *
			   select counterpartID from
			 (
			    (select counterpartID  from SEC_ApplyForm )
			    union
			    (select counterpartID from SEC_DeliveryOrder  )
			    union
			    (select counterpartID  from (select counterpartID from SEC_NOTICEFORM a,sec_counterpartbankAccount b where a.counterpartBankID = b.counterpartID ) notice )
			)
			where  counterpartID=15;
			 * 
			 */
			strSQL.append(" SELECT counterpartID from \n");
			strSQL.append(" ( \n");
			strSQL.append("     (select counterpartID  from SEC_ApplyForm  f where f.statusid > 0) \n");
			strSQL.append("   UNION \n");
			strSQL.append("     (select counterpartID from sec_counterpartbankAccount  t where t.statusid > 0) \n");
			strSQL.append("   UNION \n");
			strSQL.append("     (select counterpartID from sec_account   a where a.statusid > 0) \n");			
			strSQL.append("   UNION \n");
			strSQL.append("     (select counterpartID from SEC_DeliveryOrder   d where d.statusid > 0) \n");
			strSQL.append("   UNION \n");
			strSQL.append("     (select counterpartID  from  \n");
			strSQL.append("        (select counterpartID from SEC_NOTICEFORM a,sec_counterpartbankAccount b \n");
			strSQL.append("        where a.counterpartBankID = b.counterpartID and a.statusid>0 and b.statusid > 0 ) notice ) \n");
			strSQL.append(" ) \n");
			strSQL.append(" WHERE  counterpartID=" + counterPartID);
			log.debug("lhj debug info SEC_CounterPartDAO = findIsUseCounterPart SQLString is " + strSQL.toString());
			prepareStatement(strSQL.toString());
			ResultSet rset = executeQuery();

			try
			{
				if (rset.next())
				{
					lReturn = rset.getLong("counterpartID");
				}
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
			}
			catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				throw new ITreasuryDAOException("查找交易对手是否已经使用出现异常！", e1);
			}
			finally
			{
				if (rset != null)
				{
					try
					{
						rset.close();
					}
					catch (SQLException e2)
					{
						// TODO Auto-generated catch block
						throw new ITreasuryDAOException("查找交易对手是否已经使用关闭rset出现异常！", e2);
					}
					rset = null;
				}

			}

			finalizeDAO();
			return lReturn;
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	public CounterPartInfo findCounterPartInfo(long counterPartID) throws SecuritiesDAOException
	{
		long lReturn = -1;
		try
		{
			CounterPartInfo info = new  CounterPartInfo();
			initDAO();
			StringBuffer strSQL = new StringBuffer();
			
			strSQL.append(" SELECT * from \n");		
			strSQL.append(" SEC_Counterpart    \n");		
			strSQL.append(" WHERE 1=1 and ID=" + counterPartID);
			prepareStatement(strSQL.toString());
			ResultSet rset = executeQuery();

			try
			{
				if (rset.next())
				{
					info.setCode(rset.getString("code"));
					info.setName(rset.getString("name"));
				}
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
			}
			catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				throw new ITreasuryDAOException("查找交易对手是否已经使用出现异常！", e1);
			}
			finally
			{
				if (rset != null)
				{
					try
					{
						rset.close();
					}
					catch (SQLException e2)
					{
						// TODO Auto-generated catch block
						throw new ITreasuryDAOException("查找交易对手是否已经使用关闭rset出现异常！", e2);
					}
					rset = null;
				}

			}

			finalizeDAO();
			return info;
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	
	public static void main(String[] args) {
		long lCode = Long.parseLong("000123456") + 1;
		String strCode = DataFormat.formatInt(lCode, 4, true);
		
		System.out.println(strCode);
           
	}

}
