/*
 * Created on 2003-10-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.transdiscount.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.QueryConditionInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import java.util.ArrayList;
import com.iss.itreasury.loan.util.LOANConstant;;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_TransRepaymentDiscountDAO extends SettlementDAO
{
	public final static int ORDERBY_STRANSNO = 0;   //交易号
	public final static int ORDERBY_NTRANSACTIONTYPEID = 1;  //业务类型
	public final static int ORDERBY_NDISCOUNTACCOUNTID = 2;  //贴现账户号
	public final static int ORDERBY_NDISCOUNTCONTRACTID = 3;  //合同号 
	public final static int ORDERBY_NDISCOUNTNOTEID = 4;   //贴现凭证编号
	public final static int ORDERBY_DISCOUNTBILLID = 5;//银行承兑汇票号
	public final static int ORDERBY_BANKID = 6;  //银行存款开户行
	public final static int ORDERBY_MAOUNT = 7;  //金额	 
	public final static int ORDERBY_SABSTRACT = 8;//摘要 
	public final static int ORDERBY_NSTATUSID = 9;//状态 	

	private StringBuffer strSelectSQLBuffer = null;
	
	
	
//	通过ID更改loan_discountcontractbill中记录状态
	public long updateDiscountBillSave(long discountID) throws Exception
	{
		Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nDIscountNoteID=" + discountID + " \n\n");
		//Log.print("\n\n updateLoanDiscountStatusByID 传入参数 nNewStatusID=" + nNewStatusID + " \n\n");
		boolean flag = false;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = " update  loan_discountcontractbill " + " set  DTCANCELDATE=sysdate, NBILLSTATUSID="+SETTConstant.BillStatus.YHG+"    where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, discountID);
		//	ps.setLong(2, nDIscountNoteID);
			int nNum = -1;
			nNum = ps.executeUpdate();
			if (nNum > 0)
			{
				flag = true;
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return discountID;
	}
	
	/**
	 * constructor
	 */
	public Sett_TransRepaymentDiscountDAO()
	{
		super.strTableName = "Sett_TransRepaymentDiscount";
	}
	public static void main(String[] args)
	{

	}
	/**
	 * 方法说明：新增贴现收回交易记录
	 * @param info:TransRepaymentDiscountInfo
	 * @return : long - 返回新增贴现收回交易记录ID
	 * @throws IException
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 方法说明：新增贴现收回交易记录
	 * @param info:TransRepaymentDiscountInfo
	 * @return : long - 返回新增贴现收回交易记录ID
	 * @throws IException
	 */
	public long add(TransRepaymentDiscountInfo info) throws Exception 
	{
		long lReturn = -1;
		log.info("insert into  " + this.strTableName);
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			if(info.getID()==-1)
			{
				long id = this.getSett_TransRepaymentDiscountID();
				info.setID(id);
			}			
				
			conn = this.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into " + this.strTableName + " ");
			buffer.append(
				" (" + this.getItemNames(DAO_OPERATION_ADD) + ")values");//--/
			buffer.append("(");
			buffer.append("?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?,?,?,?,?,");
			buffer.append("sysdate,?,?,?,?,?,?,?,?,?,?");  //--/
			buffer.append(")");
			String sql = buffer.toString();

			ps = conn.prepareStatement(sql);
			Log.print("\n\n sql:  "+sql+"\n\n");

			int nRs = this.addDaoPrepareStatement(info, ps, DAO_OPERATION_ADD);  //--

			if (nRs > 0)
			{
				lReturn = info.getID();
			}
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 方法说明：物理修改贴现收回交易记录
	 * @param info :TransRepaymentDiscountInfo
	 * @return : long - 返回贴现收回交易记录ID
	 * @throws IException
	 */
	public long update(TransRepaymentDiscountInfo info) throws Exception   //end  wc --
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();

			StringBuffer buffer = new StringBuffer("");
			buffer.append("UPDATE \n");
			buffer.append(this.strTableName + "\n");
			buffer.append(" SET \n");
			buffer.append(this.getItemNames(DAO_OPERATION_UPDATE) + " \n");  //--
			buffer.append(" WHERE ID = ?");

			String sql = buffer.toString();

			log.info(sql);

			ps = conn.prepareStatement(sql);

			int nRs =
				this.addDaoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);  //--

			if (nRs > 0)
			{
				lReturn = info.getID();
			}

		}
		finally
		{
            cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 方法说明：删除贴现收回交易记录
	 * @param info :TransRepaymentDiscountInfo
	 * @return : long - 返回被删除贴现收回交易记录ID
	 * @throws IException
	 */
	public long delete(long id,long nUserID,String sAbstract,String sCheackAbstratc) 
		throws Exception  //end  wc
	{
		this.updateStatus(id,SETTConstant.TransactionStatus.DELETED,nUserID,sAbstract,sCheackAbstratc);
	
		return id;
	}
	/**
	 * 通过交易号查找交易ID
	 */
	public long getIDByTransNo(String strTransNo)throws Exception
	{
		long lID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn=this.getConnection();
			String sql=" select ID from sett_transrepaymentdiscount "+
						" where sTransNo=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				lID=rs.getLong("ID");
			}
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lID;
	}	

	
	/**
	 * 方法说明：根据交易ID，得到交易详细信息
	 * @param nID （数据库记录的主键）
	 * @return TransRepaymentDiscountInfo
	 * @throws IException
	 */
	public TransRepaymentDiscountInfo findByID(long nID) throws Exception  //end wc
	{
		TransRepaymentDiscountInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			String sql = "select * from " + this.strTableName + " where id=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, nID);
			rs = ps.executeQuery();
Log.print("\n\n sql: "+sql+" "+nID+"\n\n");
			info = this.transferResultsetIntoInfo(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return info;
	}
	/**
	 * 方法说明：修改状态
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return long  
	 * @throws IException
	 */
	public long updateStatus(long id, long StatusID,long lUserID,String strAbstract,   //end  wc  --
					String strCheckAbstract	) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = this.getConnection();
			String sql =
				" update " + this.strTableName + 
				" set nStatusID=?,nCheckUserID=?,sAbstract=?,sCheckAbstract=?,dtModify=sysdate where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, StatusID);
			ps.setLong(2, lUserID);
			ps.setString(3, strAbstract);	
			ps.setString(4, strCheckAbstract);	
			ps.setLong(5, id);

			int n = ps.executeUpdate();
			if (n > 0)
				lReturn = id;
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return lReturn;
	}

	/**
	 *方法说明：根据查询条件组合，查询出符合的记录
	 *  Method findByConditions.
	 * @param nOfficeID   //办事处ID
	 * @param nCurrencyID //币种ID
	 * @param nInputUserID     //用户ID
	 * @param nQueryTypeID     // 查询类型：0，（处理的查找）；1，（复核的查找） 2.
	 * @param nStatusID   //交易状态
	 * @param dtExecute        //查询日期 即执行日期
	 * @param nTransactionTypeID  //  业务类型
	 * @param orderByType//排序字段
	 * @param isDesc     //是否为降序 
	 * @return Collection
	 */
	public Collection findByCondition(QueryConditionInfo info) throws Exception  //end  wc --
	{
		long nOfficeID=info.getOfficeID();
		long nCurrencyID=info.getCurrencyID();
		long nUserID=info.getUserID();
		long nTypeID=info.getTypeID();
		long nTransactionTypeID=info.getTransactionTypeID();
		long nStatusID=info.getStatusID();
		int orderByType=(int)info.getOrderByType();
		boolean isDesc=info.getDesc();
		Timestamp date=info.getDate();
		String dt=DataFormat.getDateString(date); 
		
		Collection cln = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			StringBuffer buffer = new StringBuffer("");			
			buffer.append(" select * ");			
			buffer.append(" from " + this.strTableName + " \n");
			buffer.append(" where ID is not null ");

			//日期特别处理
			if(nStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
				buffer.append(" and dtExecute=to_date('"+DataFormat.getDateString(date)+"','yyyy-mm-dd')" );
			}		
			buffer.append(" and nOfficeID=" + nOfficeID);
			buffer.append(" and nCurrencyID=" + nCurrencyID);
				
			//根据查询类型处理用户ID
			if(nTypeID==0)
			{
				buffer.append(" and nInputUserID=" + nUserID);
				buffer.append(" and nCheckUserID!=" + nUserID);
			}
			else if(nTypeID==1)
			{	
				buffer.append(" and nCheckUserID=" + nUserID);
				buffer.append(" and nInputUserID!=" + nUserID);
			}			
			
			buffer.append(" and nTransactionTypeID="+SETTConstant.TransactionType.DISCOUNTRECEIVE+" " );			
            
			if(nTypeID==0)
			{
				if(nStatusID > 0)
					buffer.append(" and nStatusID=" + nStatusID);
				else
					buffer.append(" and nStatusID in("+SETTConstant.TransactionStatus.TEMPSAVE+","+SETTConstant.TransactionStatus.SAVE+") ");
			}
			else
			{
				buffer.append(" and nStatusID="+SETTConstant.TransactionStatus.CHECK);
			}

			//处理查询
			switch (orderByType)
			{
				case ORDERBY_STRANSNO :
					buffer.append(" order by sTransNo ");
					break;
				case ORDERBY_NTRANSACTIONTYPEID :
					buffer.append(" order by nTransaction" +
						"TypeID ");
					break;
				case ORDERBY_NDISCOUNTACCOUNTID :
					buffer.append(" order by nDiscountAccountID ");
					break;
				case ORDERBY_NDISCOUNTCONTRACTID :
					buffer.append(" order by nDiscountContractID ");
					break;
				case ORDERBY_NDISCOUNTNOTEID :
					buffer.append(" order by nDiscountNoteID ");
					break;
				case ORDERBY_BANKID :
					buffer.append(" order by nBankID ");
					break;
				case ORDERBY_MAOUNT :
					buffer.append(" order by mAmount ");
					break;					
				case ORDERBY_SABSTRACT :
					buffer.append(" order by sAbstract ");
					break;
				case ORDERBY_NSTATUSID :
					buffer.append(" order by nStatusID ");
					break;
				default :
					buffer.append(" order by sTransNo ");
					break;
			}
			if (isDesc)
				buffer.append(" desc ");
			else
				buffer.append(" asc ");

			String sql = buffer.toString();

			Log.print("\n\n" + sql + "\n\n");

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			cln = this.transferResultsetIntoCollection(rs);

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return cln;
	}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param TransRepaymentDiscountInfo info
	 * @param boolean bCheckOverdue   逾期贴现账户处理详细资料
	 * @return TransRepaymentDiscountInfo
	 */
	public TransRepaymentDiscountInfo match(TransRepaymentDiscountInfo info) throws Exception  //wc --
	{
		TransRepaymentDiscountInfo infoRtn = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			/* 需要匹配如下这些字段
			贴现账户号       nDiscountAccountID
			合同号           nDiscountContractID
			贴现凭证编号     nDiscountNoteID
			银行承兑汇票号   nDiscountBillID
			
			银行存款资料--活期存款账户   nCurrentAccountID
			银行存款资料--开户行   nBankID
			
			可选择条件
			活期账户号       nDepositAccountID
			逾期收回         mReturnedAmount
			罚息             mOverDueInterest
			
			金额             mAmount
			起息日           dtInterestStart			
			*/
			long nDiscountAccountID=info.getNDiscountAccountID();
			long nDiscountContractID=info.getNDiscountContractID();
			long nDiscountNoteID=info.getNDiscountNoteID();
			long nDiscountBillID=info.getNDiscountBillID();
			long nBankID=info.getNBankID();	
			long nCurrentAccountID=info.getNCurrentAccountID();
			
			
			long nDepositAccountID=info.getNDepositAccountID();
			double mReturnedAmount=info.getMReturnedAmount();
			double mOverDueInterest=info.getMOverDueInterest();
			double mAmount=info.getMAmount();
			Timestamp dtInterestStart=info.getDtInterestStart();	

			StringBuffer buffer = new StringBuffer("");
			buffer.append(" select * from "
					+ this.strTableName
					+ " where id is not null ");
			buffer.append(" and nDiscountAccountID=" + nDiscountAccountID);
			buffer.append(
					" and nDiscountContractID=" + nDiscountContractID);
			buffer.append(" and nDiscountNoteID=" + nDiscountNoteID);
			buffer.append(" and nDiscountBillID=" + nDiscountBillID);	
			buffer.append(" and nBankID=" + nBankID);
			buffer.append(" and nCurrentAccountID=" + nCurrentAccountID);
			
			if(info.getNIsReturned()==SETTConstant.BooleanValue.ISTRUE)
			{
				buffer.append(" and nDepositAccountID=" + nDepositAccountID);
				buffer.append(
						" and mReturnedAmount=" + DataFormat.formatNumber(mReturnedAmount,2).replaceAll(",",""));
				buffer.append(" and mOverDueInterest=" + DataFormat.formatNumber(mOverDueInterest,2).replaceAll(",",""));				
			}	
			buffer.append(" and mAmount=" + DataFormat.formatNumber(mAmount,2).replaceAll(",",""));
			if(dtInterestStart!=null)				
				buffer.append(" and dtInterestStart=to_date('" + dtInterestStart.toString().substring(0,10)+"','yyyy-mm-dd') ");	
				
			//必须交易状态必须为保存
			buffer.append(" and nStatusID=2 ");
			//交易号不能为空
			buffer.append(" and sTransNo is not null ");	
			//录入人不能是自己
			long nUserIDFromJsp=info.getNInputUserID();
			buffer.append(" and nInputUserID!="+nUserIDFromJsp+"  ");	

			String sql = buffer.toString();

			Log.print("\n\n" + sql + "\n\n");

			conn = this.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			infoRtn=this.transferResultsetIntoInfo(rs);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(ps);
		}

		return infoRtn;
	}

	/**
	 * 方法说明：根据查询条件匹配
	 * @param nOperation
	 * @return 已经拼好的字符串
	 */
	private String getItemNames(int nOperation)   //end  wc  --/
	{
		String rs = "";
		StringBuffer strBf = new StringBuffer("");

		if (nOperation == DAO_OPERATION_UPDATE
			|| nOperation == DAO_OPERATION_FIND)
		{
			strBf.append(" ID=?,");
			strBf.append(" nOfficeID=?,");
			strBf.append(" nCurrencyID=?,");
			strBf.append(" sTransNo=?,");
			strBf.append(" nTransactionTypeID=?,");
			strBf.append(" nClientID=?,");
			strBf.append(" nDiscountAccountID=?,");
			strBf.append(" nDiscountContractID=?,");
			strBf.append(" nDiscountNoteID=?,");
			strBf.append(" nDiscountBillID=?,");			
			strBf.append(" nBankID=?,");
			strBf.append(" nCashFlowID=?,");
			strBf.append(" nIsReturned=?,");
			strBf.append(" nDepositAccountID=?,");
			strBf.append(" mReturnedAmount=?,");
			strBf.append(" mOverDueInterest=?,");
			strBf.append(" mAmount=?,");
			strBf.append(" sAbstract=?,");
			strBf.append(" dtInterestStart=?,");
			strBf.append(" dtExecute=?,");
			strBf.append(" dtModify=sysdate,");
			strBf.append(" dtInput=?,");
			strBf.append(" nInputUserID=?,");
			strBf.append(" nCheckUserID=?,");
			strBf.append(" sCheckAbstract=?,");
			strBf.append(" nStatusID=?,");	
			strBf.append(" nAbstractID=?,");	
			strBf.append(" nSubAccountID=?,");
			strBf.append(" mCurrentBalance=?,");
			strBf.append(" mDiscountAmount=?,");	
			strBf.append(" nCurrentAccountID=?");	 //--/
		}
		else
		{
			strBf.append(" ID,");
			strBf.append(" nOfficeID,");
			strBf.append(" nCurrencyID,");
			strBf.append(" sTransNo,");
			strBf.append(" nTransactionTypeID,");
			strBf.append(" nClientID,");
			strBf.append(" nDiscountAccountID,");
			strBf.append(" nDiscountContractID,");
			strBf.append(" nDiscountNoteID,");
			strBf.append(" nDiscountBillID,");
			strBf.append(" nBankID,");
			strBf.append(" nCashFlowID,");
			strBf.append(" nIsReturned,");
			strBf.append(" nDepositAccountID,");
			strBf.append(" mReturnedAmount,");
			strBf.append(" mOverDueInterest,");
			strBf.append(" mAmount,");
			strBf.append(" sAbstract,");
			strBf.append(" dtInterestStart	,");
			strBf.append(" dtExecute,");
			strBf.append(" dtModify,");
			strBf.append(" dtInput,");
			strBf.append(" nInputUserID,");
			strBf.append(" nCheckUserID,");
			strBf.append(" sCheckAbstract,");
			strBf.append(" nStatusID,");	
			strBf.append(" nAbstractID,");
			strBf.append(" nSubAccountID,");	
			strBf.append(" mCurrentBalance,");	 
			strBf.append(" mDiscountAmount,"); 
			strBf.append(" nCurrentAccountID"); //-- /
				
		}

		rs = strBf.toString();
		return rs;
	}
	private int addDaoPrepareStatement(   //end  wc --/
		TransRepaymentDiscountInfo info,
		PreparedStatement psmt,
		int nOperation)
		throws Exception
	{
		int index = 1;
		PreparedStatement ps = psmt;
		ps.setLong(index++, info.getID());
		ps.setLong(index++, info.getNOfficeID());
		ps.setLong(index++, info.getNCurrencyID());
		ps.setString(index++, info.getSTransNo());
		ps.setLong(index++, info.getNTransactionTypeID());
		ps.setLong(index++, info.getNClientID());
		ps.setLong(index++, info.getNDiscountAccountID());
		ps.setLong(index++, info.getNDiscountContractID());
		ps.setLong(index++, info.getNDiscountNoteID());
		ps.setLong(index++, info.getNDiscountBillID());
		ps.setLong(index++, info.getNBankID());
		ps.setLong(index++, info.getNCashFlowID());
		ps.setLong(index++, info.getNIsReturned());
		ps.setLong(index++, info.getNDepositAccountID());
		ps.setDouble(index++, info.getMReturnedAmount	());
		ps.setDouble(index++, info.getMOverDueInterest());
		ps.setDouble(index++, info.getMAmount());
		ps.setString(index++, info.getSAbstract());
		ps.setTimestamp(index++, info.getDtInterestStart());
		ps.setTimestamp(index++, info.getDtExecute());
		//ps.setTimestamp(index++, info.getDtModify());
		ps.setTimestamp(index++, info.getDtInputDate());		
		ps.setLong(index++, info.getNInputUserID());
		ps.setLong(index++, info.getNCheckUserID());
		ps.setString(index++, info.getSCheckAbstract());
		ps.setLong(index++, info.getNStatusID());
		ps.setLong(index++, info.getNAbstractID());	
		ps.setLong(index++, info.getNSubAccountID());	
		ps.setDouble(index++, info.getMSumReceiveAmout());	
		ps.setDouble(index++, info.getMDiscountAmount());
		ps.setLong(index++, info.getNCurrentAccountID());//--/

		if (nOperation == DAO_OPERATION_UPDATE)
			ps.setLong(index++, info.getID());

		int nResult = ps.executeUpdate();
		return nResult;
	}

	private TransRepaymentDiscountInfo transferResultsetIntoInfo(ResultSet rs)  //end  wc
		throws Exception
	{
		TransRepaymentDiscountInfo info = null;
		while (rs.next())
		{
			info = new TransRepaymentDiscountInfo();			
			
			info.setID(rs.getLong("ID"));
			info.setNOfficeID(rs.getLong("nOfficeID"));
			info.setNCurrencyID(rs.getLong("nCurrencyID"));
			info.setSTransNo(rs.getString("sTransNo"));
			info.setNTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setNClientID(rs.getLong("nClientID"));
			info.setNDiscountAccountID(rs.getLong("nDiscountAccountID"));
			info.setNDiscountContractID(rs.getLong("nDiscountContractID"));
			info.setNDiscountNoteID(rs.getLong("nDiscountNoteID"));
			info.setNDiscountBillID(rs.getLong("nDiscountBillID"));
			info.setNBankID(rs.getLong("nBankID"));
			info.setNCashFlowID(rs.getLong("nCashFlowID"));
			info.setNIsReturned(rs.getLong("nIsReturned"));
			info.setNDepositAccountID(rs.getLong("nDepositAccountID"));
			info.setMReturnedAmount(rs.getDouble("mReturnedAmount"));
			info.setMOverDueInterest(rs.getDouble("mOverDueInterest"));
			info.setMAmount(rs.getDouble("mAmount"));
			info.setSAbstract(rs.getString("sAbstract"));
			info.setDtInterestStart(rs.getTimestamp("dtInterestStart"));
			info.setDtExecute(rs.getTimestamp("dtExecute"));
			info.setDtModify(rs.getTimestamp("dtModify"));
			info.setDtInputDate(rs.getTimestamp("dtInput"));
			info.setNInputUserID(rs.getLong("nInputUserID"));
			info.setNCheckUserID(rs.getLong("nCheckUserID"));
			info.setSCheckAbstract(rs.getString("sCheckAbstract"));
			info.setNStatusID(rs.getLong("nStatusID"));	
			info.setNAbstractID(rs.getLong("nAbstractID"));	
			info.setNSubAccountID(rs.getLong("nSubAccountID"));		
			info.setMSumReceiveAmout(rs.getDouble("mCurrentBalance"));   
			info.setMDiscountAmount(rs.getDouble("mDiscountAmount"));
			info.setNCurrentAccountID(rs.getLong("nCurrentAccountID"));//--	/
		}

		return info;
	}	
	
	private Collection transferResultsetIntoCollection(ResultSet rs)
		throws Exception
	{
		ArrayList list = new ArrayList();
		TransRepaymentDiscountInfo info = null;
		while (rs.next())
		{
			info = new TransRepaymentDiscountInfo();
			info.setID(rs.getLong("ID"));
			info.setNOfficeID(rs.getLong("nOfficeID"));
			info.setNCurrencyID(rs.getLong("nCurrencyID"));
			info.setSTransNo(rs.getString("sTransNo"));
			info.setNTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setNClientID(rs.getLong("nClientID"));
			info.setNDiscountAccountID(rs.getLong("nDiscountAccountID"));
			info.setNDiscountContractID(rs.getLong("nDiscountContractID"));
			info.setNDiscountNoteID(rs.getLong("nDiscountNoteID"));
			info.setNDiscountBillID(rs.getLong("nDiscountBillID"));
			info.setNBankID(rs.getLong("nBankID"));
			info.setNCashFlowID(rs.getLong("nCashFlowID"));
			info.setNIsReturned(rs.getLong("nIsReturned"));
			info.setNDepositAccountID(rs.getLong("nDepositAccountID"));
			info.setMReturnedAmount(rs.getDouble("mReturnedAmount"));
			info.setMOverDueInterest(rs.getDouble("mOverDueInterest"));
			info.setMAmount(rs.getDouble("mAmount"));
			info.setSAbstract(rs.getString("sAbstract"));
			info.setDtInterestStart(rs.getTimestamp("dtInterestStart"));
			info.setDtExecute(rs.getTimestamp("dtExecute"));
			info.setDtModify(rs.getTimestamp("dtModify"));
			info.setDtInputDate(rs.getTimestamp("dtInput"));
			info.setNInputUserID(rs.getLong("nInputUserID"));
			info.setNCheckUserID(rs.getLong("nCheckUserID"));
			info.setSCheckAbstract(rs.getString("sCheckAbstract"));
			info.setNStatusID(rs.getLong("nStatusID"));	
			info.setNAbstractID(rs.getLong("nAbstractID"));
			info.setNSubAccountID(rs.getLong("nSubAccountID"));
			info.setMSumReceiveAmout(rs.getDouble("mCurrentBalance")); 
			info.setMDiscountAmount(rs.getDouble("mDiscountAmount")); 
			info.setNCurrentAccountID(rs.getLong("nCurrentAccountID"));//--/

			list.add(info);
		}

		return list;
	}	
	
	//从账户子表中取得余额
	private double seekBalanceBySubaccountID(long nSubAccountID) throws Exception
	{
		double mValue=0.0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try
		{
			conn = this.getConnection();
			String sql=" select mBalance from sett_subaccount where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, nSubAccountID);
			
			rs = ps.executeQuery();
			while (rs.next())
			{
				mValue=rs.getDouble("mBalance");
			}
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return mValue;
	}	
	
	
	//通过ID更新mSumReceiveAmount
	public long updateSumReceiveAmountByID(long ID,double newSumReceiveAmount) throws Exception
	{
		long lRtn=-1;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = this.getConnection();
			String sql=" update "+this.strTableName+
                       " set mCurrentBalance="+newSumReceiveAmount+
                       " where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, ID);			

			int n = ps.executeUpdate();
			if (n > 0)
				lRtn=ID;
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}
		
		return lRtn;
	}
	
	//复核时,维护累计账户余额
	public long updateSumReceiveAmount(long ID,double mAmount,long nSubAccountID) throws Exception
	{
		long lRtn=-1;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			//通过账户ID从子账户表中取得余额
			double mBalance=this.seekBalanceBySubaccountID(nSubAccountID);
			//更改累计账户余额
			/*
            double dNewSumAmount=mAmount-mBalance;
			lRtn=this.updateSumReceiveAmountByID(ID,dNewSumAmount);		
			*/	
			lRtn=this.updateSumReceiveAmountByID(ID,mBalance);
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}
		
		return lRtn;
	}
	public long updateDiscountBillOfCancelCheck(long lID,Timestamp dtCancelDate,long lBillStatusID) throws Exception
	{
		Log.print("\n\n loan_discountcontractbill 传入参数 id=" + lID + " dtCancelDate= "+dtCancelDate+" NBillStatusID="+lBillStatusID+"\n\n");	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		long lReturn=-1;
		try
		{
			conn = this.getConnection();
			String sql = " update  loan_discountcontractbill " + " set  DTCANCELDATE=to_date('" + DataFormat.getDateString(dtCancelDate)+ "','yyyy-mm-dd') , NBILLSTATUSID=?  where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, lBillStatusID);		
			ps.setLong(2, lID);		
			int nNum = -1;
			nNum = ps.executeUpdate();
			if (nNum > 0)
			{
				lReturn = lID;
			}

		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return lReturn;
	}
	
	//查找在结算模块做过转贴现卖出买断发放业务的票据
	public String findDiscountContractBillIDs() throws Exception
	{
		StringBuffer sbu = new StringBuffer();
		String DiscountContractBillIDs="";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try
		{
			conn = this.getConnection();
			String sql=" select b.id from sett_transcraftbrother s,loan_discountcredence l,loan_discountcontractbill b,rtransdiscountcredencebill f" +
					" where s.nnoticeid = l.id and s.NTRANSACTIONTYPEID ="+SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT+" and s.NCRABUSINESSTYPEID = "+LOANConstant.LoanType.ZTX+" and s.NSUBTRANSACTIONTYPEID = "+SETTConstant.SubTransactionType.BREAK_NOTIFY+" and s.nstatusid = "+SETTConstant.TransactionStatus.CHECK+
					" and f.transdiscountcredenceid = l.id and b.id = f.discountcontractbillid ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				sbu.append(rs.getLong("id")+",");
			}
			DiscountContractBillIDs = sbu.toString();
			if(DiscountContractBillIDs.length()>0)
			{
				DiscountContractBillIDs=DiscountContractBillIDs.substring(0,DiscountContractBillIDs.length()-1);
			}
		}
		finally
		{
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return DiscountContractBillIDs;
	}	
}
