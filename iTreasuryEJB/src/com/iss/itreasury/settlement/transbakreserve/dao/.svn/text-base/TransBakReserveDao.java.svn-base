package com.iss.itreasury.settlement.transbakreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.dataconvert.util.Log;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class TransBakReserveDao extends SettlementDAO{
	
	public final static int ORDERBY_TRANSACTIONNOID = 0;
	public final static int ORDERBY_BAKACCOUNTNO = 1;
	public final static int ORDERBY_BAKOFFICENAME = 2;
	public final static int ORDERBY_AMOUNT = 4;
	public final static int ORDERBY_INTERESTSTARTDATE = 5;
	public final static int ORDERBY_EXECUTEDATEDATE = 6;
	public final static int ORDERBY_ABSTRACT = 7;
	public final static int ORDERBY_STATUSID = 8;
//	public final static int ORDERBY_BILLNO = 9;
//	public final static int ORDERBY_BANKCHEQUENO = 10;
//	public final static int ORDERBY_RECEIVEACCOUNTNO = 11;
//	public final static int ORDERBY_RECEIVECLIENTNAME = 12;
//	public final static int ORDERBY_SDECLARATIONNO = 13;
	
	
	private StringBuffer strSelectSQLBuffer = null;
	
	public TransBakReserveDao()
	{
		super.strTableName = "Sett_transReserves";
		strSelectSQLBuffer = new StringBuffer();
		strSelectSQLBuffer.append("select id,nofficeid,ncurrencyid,stransno,ntransactiontypeid,branchid,reserveaccountid,bakaccountid,\n");
		strSelectSQLBuffer.append("payorreceivetype,bankid,sremitinbank,sextaccountno,sextclientname,sremitinprovince,sremitincity,spayeebankexchangeno, \n");
		strSelectSQLBuffer.append("spayeebankcnapsno,spayeebankorgno,amount,dtintereststart,dtexecute,dtmodify,ninputuserid, \n");
		strSelectSQLBuffer.append("dtinput,ncheckuserid,nabstractid,sabstract,scheckabstract,nstatusid FROM \n");

	}
	
	/**
	 * 方法说明：修改状态
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return long  
	 * @throws IException
	 */
	public long updateStatus(long id, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("nStatusID = ?, dtModify = sysdate \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
	
	/**
	 * 方法说明：新增活期交易记录
	 * @param info:Sett_TransCurrentDepositInfo
	 * @return : long - 返回新增活期交易记录ID
	 * @throws IException
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public long add(TransBakReserveInfo info) throws Exception
	{
		long lReturn = -1;
		long mID=-1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//long id = getSett_TransCurrentDepositID();
			//info.setId(id);
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select max(id) mID from "+strTableName+ " \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				mID=rs.getLong("mID")+1;
			}
			else{
				
				
				mID=1;
			}
			cleanup(rs);
			cleanup(ps);

			info.setId(mID);
			strSQLBuffer=new StringBuffer();
			strSQL="";
			
			strSQLBuffer.append("INSERT INTO  \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("(" + getFieldsSQLString(-1, DAO_OPERATION_ADD, null) + " \n");
			strSQLBuffer.append(") VALUES \n");
			strSQLBuffer.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?) \n");
			strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			addDatatoPrepareStatement(info, ps, DAO_OPERATION_ADD);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(rs);
			cleanup(con);
		}
		return info.getId();
	}
	
	public long updateBakReserveInfo(TransBakReserveInfo info) throws Exception
	{
		long lReturn = -1;
		long mID=-1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("update   \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" set " + getFieldsSQLString(info.getNtransactiontypeid(), DAO_OPERATION_UPDATE, info) + " \n");
			strSQLBuffer.append(" where id="+info.getId()+" \n");
			String strSQL = strSQLBuffer.toString();
			System.out.print(strSQL);
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			addDatatoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);
			lReturn=ps.executeUpdate();
			
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(rs);
			cleanup(con);
		}
		return lReturn;
	}
	
	/**
	 *@param1 transactionType transaction type, sucha as check payment, -1 mean
	 *that this function do not need know transaction type
	*/
	private String getFieldsSQLString(long transactionType, int operation, TransBakReserveInfo info)
	{
		StringBuffer strSQLBuffer = new StringBuffer();
		String strRes = "";
		if ((operation == DAO_OPERATION_ADD ) && info == null)
		{

			strSQLBuffer.append(" ID, \n");
			strSQLBuffer.append("nOfficeID, \n");
			strSQLBuffer.append("nCurrencyID, \n");
			strSQLBuffer.append("sTransNo, \n");
			strSQLBuffer.append("ntransactiontypeid, \n");
			strSQLBuffer.append("branchid, \n");
			strSQLBuffer.append("reserveaccountid, \n");
			strSQLBuffer.append("bakaccountid, \n");
			strSQLBuffer.append("payorreceivetype, \n");
			strSQLBuffer.append("bankid, \n");
			strSQLBuffer.append("sremitinbank, \n");
			strSQLBuffer.append("sextaccountno, \n");
			strSQLBuffer.append("sextclientname, \n");
			strSQLBuffer.append("sremitinprovince, \n");
			strSQLBuffer.append("sremitincity, \n");
			strSQLBuffer.append("spayeebankexchangeno, \n");
			strSQLBuffer.append("spayeebankcnapsno, \n");
			strSQLBuffer.append("spayeebankorgno, \n");
			strSQLBuffer.append("amount, \n");
			strSQLBuffer.append("dtintereststart, \n");
			strSQLBuffer.append("dtexecute, \n");
			strSQLBuffer.append("dtmodify, \n");
			strSQLBuffer.append("ninputuserid, \n");
			strSQLBuffer.append("dtinput, \n");
			strSQLBuffer.append("ncheckuserid, \n");
			strSQLBuffer.append("nabstractid, \n");
			strSQLBuffer.append("sabstract,\n");
			strSQLBuffer.append("scheckabstract,\n");
			strSQLBuffer.append("nstatusid \n");
			
			strRes = strSQLBuffer.toString();
		}
		else if (operation == DAO_OPERATION_FIND && info != null)
		{

			
			strRes = strSQLBuffer.toString();
			
			boolean isNeedWhere = false;
			if (info.getId()>0)
			{
				strSQLBuffer.append(" AND ID = " + info.getId() + " \n");
				isNeedWhere = true;
			}
			if (info.getNofficeid()>0)
			{
				strSQLBuffer.append(" AND nOfficeID =" + info.getNofficeid() + " \n");
				isNeedWhere = true;
			}
			if (info.getNcurrencyid() != -1)
			{
				strSQLBuffer.append(" AND nCurrencyID = " + info.getNcurrencyid() + " \n");
				isNeedWhere = true;
			}
			if (info.getStransno() != null && info.getStransno().compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sTransNo = '" + info.getStransno() + "' \n");
				isNeedWhere = true;
			}
			if (info.getNtransactiontypeid() != -1)
			{
				strSQLBuffer.append(" AND ntransactiontypeid = " + info.getNtransactiontypeid() + " \n");
				isNeedWhere = true;
			}
			if (info.getBakaccountid() != -1)
			{
				strSQLBuffer.append(" AND bakaccountid =" + info.getBakaccountid() + " \n");
				isNeedWhere = true;
			}
			if (info.getBankid() != -1)
			{
				strSQLBuffer.append(" AND bankid =" + info.getBankid() + " \n");
				isNeedWhere = true;
			}
			if (info.getReserveaccountid() != -1)
			{
				strSQLBuffer.append(" AND reserveaccountid =" + info.getReserveaccountid() + " \n");
				isNeedWhere = true;
			}
			if (info.getPayorreceivetype() != -1)
			{
				strSQLBuffer.append(" AND payorreceivetype =" + info.getPayorreceivetype() + " \n");
				isNeedWhere = true;
			}
			if (info.getBranchid() != -1)
			{
				strSQLBuffer.append(" AND branchid =" + info.getBranchid() + " \n");
				isNeedWhere = true;
			}
			if (info.getAmount() != 0.0)
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(info.getAmount());
				strSQLBuffer.append(" AND amount =" + strAmountTemp + " \n");
				isNeedWhere = true;
			}
			if (info.getDtintereststart() != null)
			{
				String time = info.getDtintereststart().toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND dtintereststart = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			if (info.getDtexecute() != null)
			{
				String time = info.getDtexecute().toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND dtexecute = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			if (info.getSabstract() != null && info.getSabstract().compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sabstract = '" + info.getSabstract() + "' \n");
				isNeedWhere = true;
			}
			if (info.getNstatusid() >0)
			{
				strSQLBuffer.append(" AND nStatusID =" + info.getNstatusid() + " \n");
				isNeedWhere = true;
			}
			if (info.getNinputuserid()> 0)
			{
				strSQLBuffer.append(" AND ninputuserid = '" + info.getNinputuserid() + "' \n");
				isNeedWhere = true;
			}
			if (info.getNcheckuserid()>0)
			{
				strSQLBuffer.append(" AND ncheckuserid =" + info.getNcheckuserid() + " \n");
				isNeedWhere = true;
			}
			long[] statusIDs = info.getTransStatusIDs();
			if (statusIDs != null)
			{
				strSQLBuffer.append(" AND (nStatusID =" + statusIDs[0] + " \n");
				for (int i = 1; i < statusIDs.length; i++)
				{
					strSQLBuffer.append(" OR nStatusID =" + statusIDs[i] + " \n");
				}
				strSQLBuffer.append(")");
				isNeedWhere = true;
			}
			strRes = strSQLBuffer.toString();
			//Cut first "AND" if there is
			if (strRes.startsWith(" AND"))
				strRes = strRes.substring(4);
			if (isNeedWhere)
				strRes = " WHERE " + strRes;
		
		}
		else if(operation==DAO_OPERATION_UPDATE && info != null)
		{
			
			strSQLBuffer.append("sTransNo=?, \n");		
			strSQLBuffer.append("branchid=?, \n");			
			strSQLBuffer.append("reserveaccountid=?, \n");
			strSQLBuffer.append("bakaccountid=?, \n");
			strSQLBuffer.append("payorreceivetype=?, \n");
			strSQLBuffer.append("bankid=?, \n");
			
			if(info.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN){
				
				strSQLBuffer.append("sremitinbank=?, \n");
				strSQLBuffer.append("sextaccountno=?, \n");
				strSQLBuffer.append("sextclientname=?, \n");
				strSQLBuffer.append("sremitinprovince=?, \n");
				strSQLBuffer.append("sremitincity=?, \n");
				strSQLBuffer.append("spayeebankexchangeno=?, \n");
				strSQLBuffer.append("spayeebankcnapsno=?, \n");
				strSQLBuffer.append("spayeebankorgno=?, \n");
			}

			strSQLBuffer.append("amount=?, \n");
			strSQLBuffer.append("dtintereststart=?, \n");
			strSQLBuffer.append("dtexecute=?, \n");
			strSQLBuffer.append("dtmodify=sysdate, \n");
			strSQLBuffer.append("ninputuserid=?, \n");
			strSQLBuffer.append("nabstractid=?, \n");
			strSQLBuffer.append("sabstract=?\n");
			
			strRes = strSQLBuffer.toString();
		}
		else if (operation == DAO_OPERATION_MATCH && info != null)
		{
			
				strSQLBuffer.append(" AND nOfficeID = " + info.getNofficeid() + " \n");
				strSQLBuffer.append(" AND nCurrencyID = " + info.getNcurrencyid() + " \n");
				strSQLBuffer.append(" AND payorreceivetype = " + info.getPayorreceivetype() + " \n");
				strSQLBuffer.append(" AND ntransactiontypeid =" + info.getNtransactiontypeid() + " \n");
				strSQLBuffer.append(" AND bakaccountid =" + info.getBakaccountid() + " \n");

				if(info.getNtransactiontypeid()==1)
				{
					strSQLBuffer.append(" AND reserveaccountid =" + info.getReserveaccountid() + " \n");
					
				}
				else if(info.getNtransactiontypeid()==2)
				{
					
					strSQLBuffer.append(" AND bankid = " + info.getBankid() + " \n");
					
					
					if(info.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
					{
						if (info.getSextaccountno() == null || info.getSextaccountno().trim().length()==0 || info.getSextaccountno().equals("null"))
						{
							strSQLBuffer.append(" AND sextaccountno IS NULL ");
						}
						else
						{

							strSQLBuffer.append(" AND sextaccountno = '" + info.getSextaccountno() + "' \n");
						}
						
						if (info.getSextclientname() == null || info.getSextclientname().trim().length()==0 || info.getSextclientname().equals("null"))
						{
							strSQLBuffer.append(" AND sextclientname IS NULL ");
						}
						else
						{

							strSQLBuffer.append(" AND sextclientname = '" + info.getSextclientname() + "' \n");
						}
						
					}
					
				}

				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(info.getAmount());
				strSQLBuffer.append(" AND amount = " +strAmountTemp  + " \n");
				Timestamp interestStartDate = info.getDtintereststart();
				if (interestStartDate == null)
				{
					strSQLBuffer.append(" AND dtintereststart IS NULL ");
				}
				else
				{
					String time = interestStartDate.toString();
					time = time.substring(0, 10);
					strSQLBuffer.append(" AND dtintereststart = to_date('" + time + "','yyyy-mm-dd') \n");
				}
//				Timestamp executeDate = info.getDtexecute();
//				if (executeDate == null)
//				{
//					strSQLBuffer.append(" AND dtexecute IS NULL ");
//				}
//				else
//				{
//					String time = executeDate.toString();
//					time = time.substring(0, 10);
//					strSQLBuffer.append(" AND dtexecute = to_date('" + executeDate + "','yyyy-mm-dd') \n");
//				}
		
				strSQLBuffer.append(" AND ninputuserid <> " + info.getNcheckuserid());
				strSQLBuffer.append(" AND nstatusid = " + info.getNstatusid());
			strRes = strSQLBuffer.toString();
			//Cut first "AND" if there is
			if (strRes.startsWith(" AND"))
				strRes = strRes.substring(4);
		}
		return strRes;
	}
	
	/**
	 * @return last index of PerpareStatement
	 * @param stmt this is a reference and param will be modified 
	 * 
	 */
	private int addDatatoPrepareStatement(TransBakReserveInfo info, PreparedStatement stmt, int operation) throws Exception
	{
		PreparedStatement ps = stmt;  
		int index = 1;
		if(operation==DAO_OPERATION_UPDATE)
		{
			index = 1;
			ps.setString(index++, info.getStransno());
			ps.setLong(index++, info.getBranchid());
			ps.setLong(index++, info.getReserveaccountid());
			ps.setLong(index++, info.getBakaccountid());
			ps.setLong(index++, info.getPayorreceivetype());
			ps.setLong(index++, info.getBankid());
			
			if(info.getNtransactiontypeid()==SETTConstant.TransactionType.BAKRETURN)
			{
				
				ps.setString(index++, info.getSremitinbank());
				ps.setString(index++, info.getSextaccountno());
				ps.setString(index++, info.getSextclientname());
				ps.setString(index++, info.getSremitinprovince());
				ps.setString(index++, info.getSremitincity());
				ps.setString(index++, info.getSpayeebankexchangeno());
				ps.setString(index++, info.getSpayeebankcnapsno());
				ps.setString(index++, info.getSpayeebankorgno());
			}

			ps.setDouble(index++, info.getAmount());
			ps.setTimestamp(index++, info.getDtintereststart());
			ps.setTimestamp(index++, info.getDtexecute());
			ps.setLong(index++, info.getNinputuserid());
			ps.setLong(index++, info.getNabstractid());
			ps.setString(index++, info.getSabstract());
			System.out.println("**********************===="+index);
			return index;
		}
		else if(operation==DAO_OPERATION_ADD){
			index = 1;
			ps.setLong(index++, info.getId());
			ps.setLong(index++, info.getNofficeid());
			ps.setLong(index++, info.getNcurrencyid());
			ps.setString(index++, info.getStransno());
			ps.setLong(index++, info.getNtransactiontypeid());
			ps.setLong(index++, info.getBranchid());
			ps.setLong(index++, info.getReserveaccountid());
			ps.setLong(index++, info.getBakaccountid());
			ps.setLong(index++, info.getPayorreceivetype());
			ps.setLong(index++, info.getBankid());
			ps.setString(index++, info.getSremitinbank());
			ps.setString(index++, info.getSextaccountno());
			ps.setString(index++, info.getSextclientname());
			ps.setString(index++, info.getSremitinprovince());
			ps.setString(index++, info.getSremitincity());
			ps.setString(index++, info.getSpayeebankexchangeno());
			ps.setString(index++, info.getSpayeebankcnapsno());
			ps.setString(index++, info.getSpayeebankorgno());
			ps.setDouble(index++, info.getAmount());
			ps.setTimestamp(index++, info.getDtintereststart());
			ps.setTimestamp(index++, info.getDtexecute());
			//ps.setTimestamp(index++, info.getDtmodify());
			ps.setLong(index++, info.getNinputuserid());
			ps.setTimestamp(index++, info.getDtinput());
			ps.setLong(index++, info.getNcheckuserid());
			ps.setLong(index++, info.getNabstractid());
			ps.setString(index++, info.getSabstract());
			ps.setString(index++, info.getScheckabstract());
			ps.setLong(index++, info.getNstatusid());

			System.out.println("**********************===="+index);
			return index;
		}
		return index;
		
		
	}
	
	
	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransBakReserveInfo findByID(long transCurrentDepositID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransBakReserveInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(strSelectSQLBuffer);
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, transCurrentDepositID);
			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				res = (TransBakReserveInfo) ((ArrayList) c).get(0);
			}
			System.out.print(strSQL);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	
	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{
		ArrayList list = new ArrayList();
		while (rs.next())
		{
			TransBakReserveInfo info = new TransBakReserveInfo();
			
			info.setId(rs.getLong("id"));
			info.setNofficeid(rs.getLong("nofficeid"));
			info.setNcurrencyid(rs.getLong("ncurrencyid"));
			info.setStransno(rs.getString("stransno"));
			info.setNtransactiontypeid(rs.getLong("ntransactiontypeid"));
			info.setBranchid(rs.getLong("branchid"));
			info.setReserveaccountid(rs.getLong("reserveaccountid"));
			info.setBakaccountid(rs.getLong("bakaccountid"));
			info.setPayorreceivetype(rs.getLong("payorreceivetype"));
			info.setBankid(rs.getLong("bankid"));
			info.setSremitinbank(rs.getString("sremitinbank"));
			info.setSextaccountno(rs.getString("sextaccountno"));
			info.setSextclientname(rs.getString("sextclientname"));
			info.setSremitinprovince(rs.getString("sremitinprovince"));
			info.setSremitincity(rs.getString("sremitincity"));
			info.setSpayeebankexchangeno(rs.getString("spayeebankexchangeno"));
			info.setSpayeebankcnapsno(rs.getString("spayeebankcnapsno"));
			info.setSpayeebankorgno(rs.getString("spayeebankorgno"));
			info.setAmount(rs.getDouble("amount"));
			info.setDtintereststart(rs.getTimestamp("dtintereststart"));
			info.setDtexecute(rs.getTimestamp("dtexecute"));
			info.setDtmodify(rs.getTimestamp("dtmodify"));
			info.setNinputuserid(rs.getLong("ninputuserid"));
			info.setDtinput(rs.getTimestamp("dtinput"));
			info.setNcheckuserid(rs.getLong("ncheckuserid"));
			info.setNabstractid(rs.getLong("nabstractid"));
			info.setSabstract(rs.getString("sabstract"));
			info.setScheckabstract(rs.getString("scheckabstract"));
			info.setNstatusid(rs.getLong("nstatusid"));
			
			list.add(info);
		}
		return list;
	}
	
	/**
	 * 方法说明：根据查询条件组合，查询出符合的记录
	 *  Method findByConditions.
	 * @param sett_TransCurrentDepositInfo
	 * @param orderBySequence 
	 * @return Collection
	 */
	public Collection findByConditions(TransBakReserveInfo info, int orderByType, boolean isDesc) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT ");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_ADD, null));
			strSQLBuffer.append(" FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, info) + " \n");

			boolean isNeedOrderBy = true;
			switch (orderByType)
			{
				case ORDERBY_TRANSACTIONNOID :
					{
						strSQLBuffer.append(" ORDER BY sTransNo \n");
					}
					break;
				case ORDERBY_BAKACCOUNTNO :
				{
					strSQLBuffer.append(" ORDER BY bakaccountid \n");
				}
				break;
				case ORDERBY_BAKOFFICENAME :
				{
					strSQLBuffer.append(" ORDER BY branchid \n");
				}
				break;
				case ORDERBY_AMOUNT :
				{
					strSQLBuffer.append(" ORDER BY amount \n");
				}
				break;
				case ORDERBY_INTERESTSTARTDATE :
				{
					strSQLBuffer.append(" ORDER BY dtintereststart \n");
				}
				break;
				case ORDERBY_EXECUTEDATEDATE :
				{
					strSQLBuffer.append(" ORDER BY dtexecute \n");
				}
				break;
				case ORDERBY_ABSTRACT :
				{
					strSQLBuffer.append(" ORDER BY sabstract \n");
				}
				break;
				case ORDERBY_STATUSID :
				{
					strSQLBuffer.append(" ORDER BY nstatusid \n");
				}
				break;
				default :
					{
						isNeedOrderBy = false;
					}
					break;
			}
			if (isNeedOrderBy)
			{
				if (isDesc)
					strSQLBuffer.append(" DESC \n");
				else
					strSQLBuffer.append(" ASC \n");
			}

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			res = getInfoFromResultSet(rs);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	
	/**
	 * 方法说明：删除活期交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回被删除活期交易记录ID
	 * @throws IException
	 */
	public long delete(long id) throws Exception
	{
		this.updateStatus(id, SETTConstant.TransactionStatus.DELETED);
		return id;
	}
	
	/**
	 * 方法说明：根据ID查找修改时间
	 * @param transCurrentDepositID  
	 * @return Timestamp 
	 * @throws IException
	 */
	public Timestamp findTouchDate(long transCurrentDepositID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT dtModify FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, transCurrentDepositID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	
	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransBakReserveInfo match(TransBakReserveInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransBakReserveInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(strSelectSQLBuffer);
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE \n");
			String str = getFieldsSQLString(info.getNtransactiontypeid(), DAO_OPERATION_MATCH, info);
			
			if (str == null) //no need match condition
				return null;
			strSQLBuffer.append(str);
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
		
			ps = con.prepareStatement(strSQL);
			
			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				res = (TransBakReserveInfo) ((ArrayList) c).get(0);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		System.out.println("qlan***********************======restest======"+res);
		return res;
		
	}

/**
	 * 方法说明：修改状态
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return long  
	 * @throws IException
	 */
	public long updateStatus(long id, long StatusID,long checkuserid,String checkabstract) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("nStatusID = ?, dtModify = sysdate,NCHECKUSERID = ?,SCHECKABSTRACT = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, checkuserid);
			ps.setString(3, checkabstract);
			ps.setLong(4, id);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}

}
