/* Generated by Together */
package com.iss.itreasury.bill.venture.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.iss.itreasury.bill.util.BillDAO;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.bill.venture.dataentity.BlackBillInfo;
import com.iss.itreasury.bill.venture.dataentity.BlackQueryCondition;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
public class BlackBillDao extends BillDAO
{
	public BlackBillDao()
	{
		super("Bill_BlackList");
		setUseMaxID();
	}
	protected Log4j log = new Log4j(Constant.ModuleType.BILL, this);
	public final static long OrderByBillType = 0; //按票据类型排序
	public final static long OrderByBillCode = 1; //按票据编号排序
	public final static long OrderByCreateDate = 2; //出票日期
	public final static long OrderByEndDate = 3; //到期日
	public final static long OrderByBillAmount = 4; //票面金额
	public final static long OrderByGuardStatusID = 5; //协防状态
	/**
	 * 根据查询条件，获取查询结果 
	 */
	public Vector findByQueryCondition(BlackQueryCondition blackQueryConditionInfo) throws BillException
	{
		log.print("=======Enter method BlackBillDao.findByQueryCondition(BlackQueryCondition blackQueryConditionInfo)=======");
		Vector vctResult = new Vector();
		BlackBillInfo blackBillInfo = null;
		String strSQL = "";
		String strWhereSQL = "";
		long lRecordCount = 0; //总记录数
		long lPageCount = 0; //总页数
		long lStartNum = 0;
		long lEndNum = 0;
		try
		{
			//获取where语句
			strWhereSQL = getQueryWhereSQL(blackQueryConditionInfo);
			//计算总页数
			initDAO();
			strSQL = " select count(*) from " + this.strTableName + strWhereSQL;
			prepareStatement(strSQL);
			executeQuery();
			if (transRS.next())
			{
				lRecordCount = transRS.getLong(1);
			}
			lPageCount = lRecordCount / blackQueryConditionInfo.getQueryPageLineCount();
			if (lRecordCount % blackQueryConditionInfo.getQueryPageLineCount() != 0)
			{
				lPageCount = lPageCount + 1;
			}
			if (transRS != null)
			{
				transRS.close();//关闭连结
			}
			//blackQueryConditionInfo.setQueryPageCount(lPageCount);
			//计算开始行和结束行
			lStartNum = (blackQueryConditionInfo.getQueryPageNo() - 1) * blackQueryConditionInfo.getQueryPageLineCount() + 1;
			lEndNum = blackQueryConditionInfo.getQueryPageNo() * blackQueryConditionInfo.getQueryPageLineCount();
			if (lEndNum > lRecordCount)
			{
				lEndNum = lRecordCount;
			}
			//计算开始行和结束行
			strSQL = " select * from ( select t.*,rownum rnum " + " from " + this.strTableName + " t "  +strWhereSQL + " ) where rnum between " + lStartNum + " and " + lEndNum;
			prepareStatement(strSQL);
			executeQuery();
			while (transRS.next())
			{
				blackBillInfo = getBlackInfoFromResultSet(transRS);
				blackBillInfo.setQueryPageCount(lPageCount);
				vctResult.add(blackBillInfo);
			}
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new BillException(e.getMessage(), e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new BillException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new BillException(e.getMessage(), e);
			}
		}
		log.print("=======End method BlackBillDao.findByQueryCondition(BlackQueryCondition blackQueryConditionInfo)=======");
		return vctResult;
	}
	private String getQueryWhereSQL(BlackQueryCondition blackQueryConditionInfo) throws BillException
	{
		String strWhereSQL = " where 1 = 1 \n";
		strWhereSQL += " and OFFICEID=" + blackQueryConditionInfo.getQueryOfficeID(); //办事处
		strWhereSQL += " and CURRENCYID=" + blackQueryConditionInfo.getQueryCurrencyID(); //币种		
		//汇票类型	
		if (blackQueryConditionInfo.getQueryBillTypeID() > 0)
		{
			strWhereSQL += " and BILLTYPEID =" + blackQueryConditionInfo.getQueryBillTypeID() + "\n";
		}
		if (blackQueryConditionInfo.getQueryCodeStart() != null && blackQueryConditionInfo.getQueryCodeStart().trim().length() > 0) //起始票据编号
		{
			strWhereSQL += " and BILLCODE >='" + blackQueryConditionInfo.getQueryCodeStart() + "'\n";
		}
		if (blackQueryConditionInfo.getQueryCodeEnd() != null && blackQueryConditionInfo.getQueryCodeEnd().trim().length() > 0) //结束票据编号
		{
			strWhereSQL += " and BILLCODE <='" + blackQueryConditionInfo.getQueryCodeEnd() + "'\n";
		}
		if (blackQueryConditionInfo.getQueryInputUserID() > 0) //录入人
		{
			strWhereSQL += " and INPUTUSERID=" + blackQueryConditionInfo.getQueryInputUserID() + "\n";
		}
		if (blackQueryConditionInfo.getQueryInputDateStart() != null) //起始录入日期
		{
			strWhereSQL += " and INPUTDATE >=to_date('" + DataFormat.getDateString(blackQueryConditionInfo.getQueryInputDateStart()) + "','YYYY/MM/DD HH24:MI:SS')\n";
		}
		if (blackQueryConditionInfo.getQueryInputDateEnd() != null) //结束录入日期
		{
			strWhereSQL += " and INPUTDATE <=to_date('" + DataFormat.getDateString(blackQueryConditionInfo.getQueryInputDateEnd()) + "','YYYY/MM/DD HH24:MI:SS')\n";
		}
		if (blackQueryConditionInfo.getQueryCreateDateStart() != null) //起始出票日
		{
			strWhereSQL += " and CREATEDATE >=to_date('" + DataFormat.getDateString(blackQueryConditionInfo.getQueryCreateDateStart()) + "','YYYY/MM/DD HH24:MI:SS')\n";
		}
		if (blackQueryConditionInfo.getQueryCreateDateEnd() != null) //结束出票日
		{
			strWhereSQL += " and CREATEDATE <=to_date('" + DataFormat.getDateString(blackQueryConditionInfo.getQueryCreateDateEnd()) + "','YYYY/MM/DD HH24:MI:SS')\n";
		}
		if (blackQueryConditionInfo.getQueryEndDateStart() != null) //起始到期日
		{
			strWhereSQL += " and MATURITYDATE >=to_date('" + DataFormat.getDateString(blackQueryConditionInfo.getQueryEndDateStart()) + "','YYYY/MM/DD HH24:MI:SS')\n";
		}
		if (blackQueryConditionInfo.getQueryEndDateEnd() != null) //结束到期日
		{
			strWhereSQL += " and MATURITYDATE <=to_date('" + DataFormat.getDateString(blackQueryConditionInfo.getQueryEndDateEnd()) + "','YYYY/MM/DD HH24:MI:SS')\n";
		}
		if (blackQueryConditionInfo.getQueryAmounStart() > 0) //起始票面金额
		{
			strWhereSQL += " and BILLAMOUNT >=" + blackQueryConditionInfo.getQueryAmounStart() + "\n";
		}
		if (blackQueryConditionInfo.getQueryAmounEnd() > 0) //结束票面金额
		{
			strWhereSQL += " and BILLAMOUNT <=" + blackQueryConditionInfo.getQueryAmounEnd() + "\n";
		}
		if (blackQueryConditionInfo.getQueryAcceptorName() != null && blackQueryConditionInfo.getQueryAcceptorName().trim().length() > 0) //承兑人名称
		{
			strWhereSQL += " and ACCEPTOR like '%" + blackQueryConditionInfo.getQueryAcceptorName() + "%'" + "\n";
		}
		if (blackQueryConditionInfo.getQueryStatusIDs() != null) //协防状态
		{
			strWhereSQL += " and STATUSID in(";
			int nLength = blackQueryConditionInfo.getQueryStatusIDs().length;
			for (int i = 0; i < nLength; i++)
			{
				strWhereSQL += blackQueryConditionInfo.getQueryStatusIDs()[i];
				if (i < nLength - 1)
				{
					strWhereSQL += ",";
				}
			}
			strWhereSQL += " )" + "\n";
		}
		if (blackQueryConditionInfo.getQueryOrderByParam() > 0)
		{
			switch ((int) blackQueryConditionInfo.getQueryOrderByParam())
			{
				case (int) OrderByBillType : //按票据类型排序
					strWhereSQL += " order by BillTypeID \n";
					break;
				case (int) OrderByBillCode : //按票据编号排序
					strWhereSQL += " order by BillCode \n";
					break;
				case (int) OrderByCreateDate : //出票日期
					strWhereSQL += " order by CreateDate \n";
					break;
				case (int) OrderByEndDate : //到期日
					strWhereSQL += " order by MATURITYDATE \n";
					break;
				case (int) OrderByBillAmount : //票面金额
					strWhereSQL += " order by BILLAMOUNT \n";
					break;
				case (int) OrderByGuardStatusID : //协防状态
					strWhereSQL += " order by STATUSID \n";
					break;
				default :
					strWhereSQL += " order by BillCode \n";
			}
			if (blackQueryConditionInfo.getQueryDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strWhereSQL += " desc \n";
			}
			else
			{
				strWhereSQL += " asc \n";
			}
		}
		return strWhereSQL;
	}
	/**
	 * 检查指定的bill是否在黑名单中 
	 */
	public boolean isBillInBlackList(BlackBillInfo billInfo) throws BillException
	{
		boolean bResult = false;
		long lRecordAccount = -1;
		try
		{
			log.print("=======Enter method BlackBillDao.isBillInBlackList(BlackBillInfo billInfo)=======");
			initDAO();
			String strSQL = "select count(*) from " + this.strTableName + " where 1=1 \n";
			strSQL += " and BILLTYPEID=" + billInfo.getBillTypeID() + "\n";
			strSQL += " and BILLCODE='" + billInfo.getBillCode() + "'\n";
			strSQL += " and STATUSID=" + billInfo.getStatusID() + "\n";
			strSQL += " and OFFICEID=" + billInfo.getOfficeID() + "\n";
			strSQL += " and CURRENCYID=" + billInfo.getCurrencyID() + "\n";
			log.print("========strSQL start======");
			log.print(strSQL);
			log.print("========strSQL end======");
			prepareStatement(strSQL);
			transRS = executeQuery();
			if (transRS.next())
			{
				lRecordAccount = transRS.getLong(1);
			}
			if (lRecordAccount > 0)
			{
				bResult = true;
			}
			finalizeDAO();
			log.print("=======End method BlackBillDao.isBillInBlackList(BlackBillInfo billInfo)=======");
			return bResult;
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new BillException(e.getMessage(), e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new BillException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new BillException(e.getMessage(), e);
			}
		}
	}
	/**
	 * Method updateStatusByID.
	 * 修改黑名单票据状态
	 * @param lID
	 * @param lStatusID
	 * @return long
	 * @throws BillException
	 */
	public long updateStatusByID(long lID, long lStatusID) throws BillException
	{
		log.print("=======Enter method BlackBillDao.updateStatusByID(long lID)=======");
		long lReturn = -1;
		try
		{
			initDAO();
			String strSQL = " update " + this.strTableName + " set STATUSID=" + lStatusID + " where id=" + lID;
			log.print("========strSQL start======");
			log.print(strSQL);
			log.print("========strSQL end======");
			prepareStatement(strSQL);
			lReturn = executeUpdate();
			finalizeDAO();
			log.print("=======End method BlackBillDao.updateStatusByID(long lID)=======");
			return lReturn;
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new BillException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new BillException(e.getMessage(), e);
			}
		}
	}
	private BlackBillInfo getBlackInfoFromResultSet(ResultSet rs) throws SQLException
	{
		BlackBillInfo resultInfo = new BlackBillInfo();
		resultInfo.setId(rs.getLong("ID"));
		resultInfo.setBillTypeID(rs.getLong("BillTypeID"));
		resultInfo.setOfficeID(rs.getLong("OfficeID"));
		resultInfo.setCurrencyID(rs.getLong("CurrencyID"));
		resultInfo.setBillCode(rs.getString("BillCode"));
		resultInfo.setStatusID(rs.getLong("StatusID"));
		resultInfo.setInputUserID(rs.getLong("InputUserID"));
		resultInfo.setInputDate(rs.getTimestamp("InputDate"));
		resultInfo.setCreateDate(rs.getTimestamp("CreateDate"));
		resultInfo.setMaturityDate(rs.getTimestamp("MaturityDate"));
		resultInfo.setAcceptor(rs.getString("Acceptor"));
		resultInfo.setAcceptorAccount(rs.getString("AcceptorAccount"));
		resultInfo.setAcceptorBank(rs.getString("AcceptorBank"));
		resultInfo.setBillAmount(rs.getDouble("BillAmount"));
		resultInfo.setRemark(rs.getString("Remark"));
		return resultInfo;
	}
}
