/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.dao;
import com.iss.itreasury.settlement.transspecial.dataentity.*;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.dao.*;
import java.sql.*;
import java.lang.reflect.*;
/**
 *
 * <p>Title:特殊交易的数据操作类 </p>
 * <p>Description:用于将特殊交易的数据插入特殊交易的数据表(Sett_TransSpecialOperation)中 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:isoftstone </p>
 * @author YuqiangLiao
 * @version 1.0
 */
public class Sett_TransSpecialOperationDAO extends SettlementDAO
{
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * 新增特殊交易的方法：
	 * 逻辑说明：将传入的TransSpecialOperationInfo值对象插入数据库
	 *
	 * @param info TransSpecialOperationInfo, 特殊交易实体类
	 * @return long 新生成记录的标识
	 * @throws Exception
	 */
	public long add(TransSpecialOperationInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement pstmquery = null;
		StringBuffer str = new StringBuffer();
		try
		{
			str.append("insert into sett_TransSpecialOperation values(");
			for (int i = 1; i <= 70; i++)
			{
				str.append("?,");
			}
			str.append("?)");
			con = this.getConnection();
			//获得sett_transspecialOperationInfo表结构
			String[] strs = this.getTableColumns("sett_TransSpecialOperation", con);
			//重新执行新的命令
			PreparedStatement pstminsert = null;
			pstminsert = con.prepareStatement(new String(str));
			System.out.println("========================SQL="+str.toString());
			//调用fullTableParam方法准备插入表中的数值
			int OperationTag = 1;
			this.fullTableParam(pstminsert, info, strs, OperationTag);
			//执行PrepareStatement
			final int RESULT = pstminsert.executeUpdate();
			if (RESULT != 0)
			{
				lReturn = info.getId();
			}
			//this.cleanup(rs);
			this.cleanup(pstminsert);
			this.cleanup(con);
		}
		catch(Exception e)
		{
		     throw e;
		}
		return lReturn;
	}
	/**
	 * 修改特殊交易的方法：
	 * 逻辑说明：提交要修改的特殊交易数据
	 *
	 * @param info, TransSpecialOperationInfo, 特殊交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransSpecialOperationInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		StringBuffer str = new StringBuffer();
		try
		{
			con = this.getConnection();
			//获得sett_transspecialOperationInfo表结构
			String[] cols = this.getTableColumns("sett_transSpecialOperation", con);
			str.append("update sett_TransSpecialOperation set \n");
			int j = 1;
			while (j < (cols.length - 1))
			{
				str.append(cols[j]);
				str.append("=?,\n");
				j++;
			}
			str.append(cols[j]);
			str.append("=? \n where ");
			str.append(cols[0]);
			str.append("=?");
			System.out.println("\n" + new String(str) + "\n");
			//重新执行新的命令
			pstm = con.prepareStatement(new String(str));
			//调用fullTableParam方法准备更新表中的数值
			int OperationTag = -1; //更新标志位
			this.fullTableParam(pstm, info, cols, -1);
			//执行PrepareStatement
			final int RESULT = pstm.executeUpdate();
			if (RESULT != 0)
			{
				lReturn = info.getId();
			}
			// this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally 
		{
		    this.cleanup(pstm);
			this.cleanup(con);
		}
		return lReturn;
	}
	/**
	 * 根据标识查询特殊交易交易明细的方法：
	 * 逻辑说明：
	 * 通过特殊交易ID编号查找特殊交易明细
	 *
	 * @param lID long , 交易的ID                       -----是指ntransno,还是记录的oid
	 * @return TransSpecialOperationInfo, 特殊交易交易实体类
	 * @throws Exception
	 */
	public TransSpecialOperationInfo findByID(long lID) throws Exception
	{
		System.out.println("DAO TransSpecialOperationInfo findByID:" + lID);
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TransSpecialOperationInfo tsoi = null;
		try
		{
			con = getConnection();
			pstm = con.prepareStatement("select * from sett_transspecialoperation where id=?");
			pstm.setLong(1, lID);
			rs = pstm.executeQuery();
			tsoi = new TransSpecialOperationInfo();
			ResultSetMetaData rmd = rs.getMetaData();
			if (rmd == null)
			{
				System.out.println("rs.getMetaData() is null");
			}
			while (rs.next())
			{
				getTableInfo(rs, tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		return tsoi;
	}
	
	/**
	 * 根据标识查询特殊交易交易明细的方法：
	 * 逻辑说明：
	 * 通过特殊交易ID编号查找特殊交易明细
	 *
	 * @param lID long , 交易的ID                       -----是指ntransno,还是记录的oid
	 * @return TransSpecialOperationInfo, 特殊交易交易实体类
	 * @throws Exception
	 */
	public TransSpecialOperationInfo findByNo(String no) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TransSpecialOperationInfo tsoi = null;
		try
		{
			con = getConnection();
			pstm = con.prepareStatement("select * from sett_transspecialoperation where stransno="+no);
			rs = pstm.executeQuery();
			tsoi = new TransSpecialOperationInfo();
			ResultSetMetaData rmd = rs.getMetaData();
			if (rmd == null)
			{
				System.out.println("rs.getMetaData() is null");
			}
			while (rs.next())
			{
				getTableInfo(rs, tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		return tsoi;
	}
	/**
	 * 根据交易号查找交易ID的方法：
	 */
	public long getIDByTransNo(String strTransNo) throws Exception
	{
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		long lID = -1;
		try
		{
			pstm = con.prepareStatement("select ID from sett_transspecialoperation where sTransNo=?");
			pstm.setString(1, strTransNo);
			rs = pstm.executeQuery();
			if (rs != null && rs.next())
			{
				lID = rs.getLong("ID");
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		catch (Exception ex)
		{
			Log.print(ex.toString());
			throw ex;
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		return lID;
	}
	/**
	 * 根据条件判断特殊交易是否重复的方法：
	 *
	 * @param TransSpecialOperationInfo searchInfo , 特殊交易实体类
	 * @return boolean , false 重复
	 * @throws Exception
	 */
	public boolean checkIsDuplicate(TransSpecialOperationInfo searchInfo) throws Exception
	{
		boolean rtnFlg = true;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from sett_TransSpecialOperation where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, searchInfo.getId());
			//ps.setLong(1,lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				rtnFlg = false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}
	/**
	 * 修改特殊交易状态的方法：
	 * 逻辑说明：
	 *
	 * @param lID, long, 特殊交易标识                     -----是指数据库中的sTransNo 交易编号
	 * @param dtModify, java.sql.Timestamp,修改标志时间	
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, java.sql.Timestamp dtModify, long lStatusID) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		try
		{
			con = this.getConnection();
			pstm = con.prepareStatement("update sett_transspecialOperation set nstatusid=?,dtmodify=? where id=?");
			pstm.setLong(1, lStatusID);
			pstm.setTimestamp(2, (java.sql.Timestamp) dtModify);
			pstm.setLong(3, lID);
			Log.print("lStatusID =" + lStatusID);
			Log.print("dtModify =" + dtModify);
			Log.print("lID =" + lID);
			int i = pstm.executeUpdate();
			this.cleanup(pstm);
			this.cleanup(con);
			if (i != 0)
			{
				return lID; //Long.parseLong(lID);   //符合其义?
			}
			else
			{
				return -1;
			}
		}
		finally
		{ 
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}
	//added by qhzhou 2007-08-13更新复核人
	public long updateCheckUser(long lID,java.sql.Timestamp dtModify,long lCheckUserID) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		try
		{
			con = this.getConnection();
			pstm = con.prepareStatement("update sett_transspecialOperation set ncheckuserid=? where id=?");
			pstm.setLong(1, lCheckUserID);
			pstm.setTimestamp(2, (java.sql.Timestamp) dtModify);
			pstm.setLong(2, lID);
			Log.print("lCheckUserID =" + lCheckUserID);
			Log.print("dtModify =" + dtModify);
			Log.print("lID =" + lID);
			int i = pstm.executeUpdate();
			this.cleanup(pstm);
			this.cleanup(con);
			if (i != 0)
			{
				return lID; //Long.parseLong(lID);   //符合其义?
			}
			else
			{
				return -1;
			}
		}
		finally
		{ 
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}
	public long updatePartValues(String[] cols, TransSpecialOperationInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		StringBuffer str = new StringBuffer();
		long lReturn = -1;
		str.append("update sett_TransSpecialOperation set \n");
		try
		{
			con = this.getConnection();
			int j = 1;
			while (j < (cols.length - 1))
			{
				str.append(cols[j]);
				str.append("=?,\n");
				j++;
			}
			str.append(cols[j]);
			str.append("=? \n where ");
			str.append(cols[0]);
			str.append("=?");
			System.out.println("\n" + new String(str) + "\n");
			//重新执行新的命令
			pstm = con.prepareStatement(new String(str));
			//调用fullTableParam方法准备更新表中的数值
			int OperationTag = -1; //更新标志位
			this.fullTableParam(pstm, info, cols, -1);
			//执行PrepareStatement
			final int RESULT = pstm.executeUpdate();
			if (RESULT != 0)
			{
				lReturn = info.getId();
			}
			// this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally
		{
		    this.cleanup(pstm);
			this.cleanup(con);   
		}
		
		return lReturn;
	}
	/**
	 * 根据状态查询的方法：
	 * 逻辑说明：
	 * 通过状态查询条件,将符合条件的特殊业务交易明细记录集查询出来
	 *
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @return Vector ,包含QueryByStatusConditionInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Vector findByStatus(QueryByStatusConditionInfo info, String strOrderByName, boolean isDesc) throws Exception
	{
		/*
		 System.out.println("OfficeID: "+info.getOfficeID());
		 System.out.println("CurrencyID:"+info.getCurrencyID());
		 System.out.println("TypeID:"+info.getTypeID());
		 System.out.println("UserID:"+info.getUserID());
		 System.out.println("Date:"+info.getDate());
		 System.out.println("strOrderByName:"+strOrderByName);
		 System.out.println("isDesc:"+isDesc);
		 
		 if(info.getStatusIDs().length>0){
		 System.out.println("statusids:"+(info.getStatusIDs())[0]);
		 }
		 */
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		long[] statusids = null;
		StringBuffer cmd = new StringBuffer();
		try
		{
		    con = this.getConnection();
			cmd.append("select * from sett_transspecialoperation ");
			cmd.append(" where nofficeid=" + info.getOfficeID());
			cmd.append(" and ncurrencyid=" + info.getCurrencyID());
			cmd.append(" and ntransactiontypeid=" + SETTConstant.TransactionType.SPECIALOPERATION);
			statusids = info.getStatusIDs();
			if (statusids != null && statusids.length == 1)
			{
				long statusid = statusids[0];
				cmd.append(" and nstatusid=");
				cmd.append(statusid);
			}
			else if (statusids != null && statusids.length == 2)
			{
				long statusid0 = statusids[0];
				long statusid1 = statusids[1];
				cmd.append(" and (nstatusid=" + statusid0 + "or nstatusid=" + statusid1 + ")");
			}
			if (info.getTypeID() == 0)
			{ //处理查找
				cmd.append(" and ninputuserid=");
				cmd.append(info.getUserID());
				/*
				 if (info.getDate() != null)
				 {
				 cmd.append(" and dtexecute=TO_DATE('"+DataFormat.getDateString(info.getDate())+"','yyyy-mm-dd')");
				 }
				 */
			}
			else if (info.getTypeID() == 1)
			{ //复核查找
				cmd.append(" and ncheckuserid=");
				cmd.append(info.getUserID());
				if (info.getDate() != null)
				{
					//复核日期
					cmd.append(" and dtexecute=TO_DATE('" + DataFormat.getDateString(info.getDate()) + "','yyyy-mm-dd')");
				}
			}
			if (strOrderByName != null && !isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
			}
			else if (strOrderByName != null && isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
				cmd.append(" desc");
			}
			Log.print(cmd);
			pstm = con.prepareStatement((new String(cmd)));
			rs = pstm.executeQuery();
			while (rs.next())
			{
				System.out.println("ResultSet values");
				TransSpecialOperationInfo tsoi = new TransSpecialOperationInfo();
				this.getTableInfo(rs, tsoi);
				v.add(tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
			if (!v.isEmpty())
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return v;
			}
			else
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return null;
			}
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}
	
	/**
	 * 按特殊业务子类型和状态查询
	 * 逻辑说明：
	 * 通过状态查询条件,将符合条件的特殊业务交易明细记录集查询出来
	 *
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @return Vector ,包含QueryByStatusConditionInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Vector findBySubtransSpecialIDandStatus(QueryBySubSpecialTypeAndStatusInfo info, String strOrderByName, boolean isDesc) throws Exception
	{
		/*
		 System.out.println("OfficeID: "+info.getOfficeID());
		 System.out.println("CurrencyID:"+info.getCurrencyID());
		 System.out.println("TypeID:"+info.getTypeID());
		 System.out.println("UserID:"+info.getUserID());
		 System.out.println("Date:"+info.getDate());
		 System.out.println("strOrderByName:"+strOrderByName);
		 System.out.println("isDesc:"+isDesc);
		 
		 if(info.getStatusIDs().length>0){
		 System.out.println("statusids:"+(info.getStatusIDs())[0]);
		 }
		 */
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		long[] statusids = null;
		StringBuffer cmd = new StringBuffer();
		try
		{
		    con = this.getConnection();
			cmd.append("select * from sett_transspecialoperation ");
			cmd.append(" where nofficeid=" + info.getOfficeID());
			cmd.append(" and ncurrencyid=" + info.getCurrencyID());
			statusids = info.getStatusIDs();
			if (statusids != null && statusids.length == 1)
			{
				long statusid = statusids[0];
				cmd.append(" and nstatusid=");
				cmd.append(statusid);
			}
			else if (statusids != null && statusids.length == 2)
			{
				long statusid0 = statusids[0];
				long statusid1 = statusids[1];
				cmd.append(" and (nstatusid=" + statusid0 + " or nstatusid=" + statusid1 + ")");
			}
			if (info.getTypeID() == 0)
			{ //处理查找
				cmd.append(" and ninputuserid=");
				cmd.append(info.getUserID());
				/*
				 if (info.getDate() != null)
				 {
				 cmd.append(" and dtexecute=TO_DATE('"+DataFormat.getDateString(info.getDate())+"','yyyy-mm-dd')");
				 }
				 */
			}
			else if (info.getTypeID() == 1)
			{ //复核查找
				cmd.append(" and ncheckuserid=");
				cmd.append(info.getUserID());
				if (info.getDate() != null)
				{
					//复核日期
					cmd.append(" and dtexecute=TO_DATE('" + DataFormat.getDateString(info.getDate()) + "','yyyy-mm-dd')");
				}
			}
			if(info.getTransactiontypeid()>0 )
			{
				cmd.append(" and NTRANSACTIONTYPEID=");
				cmd.append( info.getTransactiontypeid());
				
			}
			if(info.getSubTransactiontypeid()>0)
			{
				cmd.append(" and NOPERATIONTYPEID=");
				cmd.append( info.getSubTransactiontypeid());
				
			}
			
			if (strOrderByName != null && !isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
			}
			else if (strOrderByName != null && isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
				cmd.append(" desc");
			}
			Log.print(cmd);
			pstm = con.prepareStatement((new String(cmd)));
			rs = pstm.executeQuery();
			while (rs.next())
			{
				System.out.println("ResultSet values");
				TransSpecialOperationInfo tsoi = new TransSpecialOperationInfo();
				this.getTableInfo(rs, tsoi);
				v.add(tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
			if (!v.isEmpty())
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return v;
			}
			else
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return null;
			}
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}

	/**
	 * 复核匹配的方法：
	 * 逻辑说明：
	 *
	 * @param TransSpecialOperationInfo,特殊交易复核匹配查询条件实体类
	 * @return TransSpecialOperationInfo //包含TransSpecialOperationInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public TransSpecialOperationInfo match(TransSpecialOperationInfo info, SpecialOperationInfo soi) throws Exception
	{
		Vector v = new Vector(); 
		PreparedStatement pstm = null;
		ResultSet rs = null;
		StringBuffer buffer = null;
		ArrayList selcols = new ArrayList();
		ArrayList compareopers = new ArrayList();
		ArrayList logicopers = new ArrayList();
		ArrayList arr = new ArrayList();
		try
		{ 
			//查看有多少分割视图         
			String detailtypes = soi.getScontent();
			StringTokenizer token = new StringTokenizer(detailtypes, ",");
			String[] types = new String[token.countTokens()];
			int j = 0;
			while (token.hasMoreTokens())
			{
				types[j] = token.nextToken();
				//System.out.println(types[j]);
				j++;
			}
			int i = 0;
			while (i < types.length)
			{
				if (types[i].compareTo("付款方详细资料") == 0)
				{
					System.out.println(types[i]);
					fullPayerDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("收款方详细资料") == 0)
				{
					System.out.println(types[i]);
					fullReceiverDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("委托付款凭证资料") == 0)
				{
					System.out.println(types[i]);
					fullPayVoucherDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("票据资料") == 0)
				{
					System.out.println(types[i]);
					fullBillWarrantDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("银行资料") == 0)
				{
					System.out.println(types[i]);
					fullBankDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				i++;
			}
			//其他信息
			fullOtherDetailsParam(info, selcols, compareopers, logicopers, arr);
			String classname = info.getClass().getName();
			v = this.findByCondition(classname, "sett_transSpecialOperation", selcols, compareopers, logicopers, arr);
			if (!v.isEmpty())
			{
				return (TransSpecialOperationInfo) v.firstElement();
			}
			else
			{
				return null;
			} 
		}
		finally
		{
		    
		}
	}
	private void fullOtherDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//added by mzh_fu 2007/04/10 加上业务子类型校验
		selcols.add("NOPERATIONTYPEID");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNoperationtypeid()));
		
		//added by xwhe 2009/01/06
		selcols.add("NINPUTUSERID");
		logicopers.add("and");
		compareopers.add("<>");
		arr.add(new Long(info.getNcheckuserid()));
		
		//其他信息
		System.out.println("fullOtherDetailsParam ");
		//		办事处编号
		selcols.add("Nofficeid");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNofficeid()));
		//货币编号
		selcols.add("Ncurrencyid");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNcurrencyid()));
		//业务状态
		selcols.add("Nstatusid");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNstatusid()));
		//dtInterestStart	起息日
		selcols.add("dtInterestStart");
		logicopers.add("and");
		if (info.getDtintereststart() == null)
		{
			compareopers.add("is");
			arr.add(new String("Timestamp"));
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getDtintereststart());
		}
		//dtExecute	执行日
		selcols.add("dtExecute");
		//logicopers.add("and");
		if (info.getDtexecute() == null)
		{
			compareopers.add("is");
			arr.add(new String("Timestamp"));
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getDtexecute());
		}
	}
	private void fullBankDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//if(info.getSextaccountno()!=null){
		//非财务公司账户号
		System.out.println("fullBankDetailsParam ");
		//银行账户号 对应 非财务公司账户号 sExtAccountNo
		selcols.add("sExtAccountNo");
		logicopers.add("and");
		if (info.getSextaccountno() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getSextaccountno());
		}
//		//sBankChequeNo	银行支票号
//		selcols.add("sBankChequeNo");
//		logicopers.add("and");
//		//arr.add(new String(info.getSbankchequeno()));
//		if (info.getSbankchequeno() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSbankchequeno());
//		}
		//sExtClientName	非财务公司账户名称
		selcols.add("sExtClientName");
		logicopers.add("and");
		//arr.add(new String(info.getSextclientname()));
		if (info.getSextclientname() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getSextclientname());
		}
//		//sRemitInProvince	汇入省
//		selcols.add("sRemitInProvince");
//		logicopers.add("and");
//		if (info.getSremitinprovince() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSremitinprovince());
//		}
//		//sRemitInBank	汇入行名称
//		selcols.add("sRemitInBank");
//		logicopers.add("and");
//		if (info.getSremitinbank() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSremitinbank());
//		}
//		//sDeclarationNo	报单号
//		selcols.add("sDeclarationNo");
//		logicopers.add("and");
//		if (info.getSdeclarationno() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSdeclarationno());
//		}
//		//sRemitInCity	汇入市
//		selcols.add("sRemitInCity");
//		logicopers.add("and");
//		if (info.getSremitincity() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSremitincity());
//		}
		//}
	}
	private void fullBillWarrantDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{

	}
	private void fullPayVoucherDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{

	}
	private void fullReceiverDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//if(info.getNreceiveaccountid()!=-1){
		//获得收款方账户ID
		System.out.println("fullReceiverDetailsParam info.getNreceiveaccountid(): " + info.getNreceiveaccountid());
		//收款方账户ID
		selcols.add("nreceiveaccountid");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceiveaccountid()));
		//收款方客户编号
		selcols.add("nReceiveClientID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceiveclientid()));
		//sReceiveFixedDepositNo	收款方存单号
		selcols.add("sReceiveFixedDepositNo");
		logicopers.add("and");
		if (info.getSreceivefixeddepositno() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getSreceivefixeddepositno());
		}
		//nReceiveContractID	收款方合同ID
		selcols.add("nReceiveContractID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivecontractid()));
		//nReceiveLoanNoteID	收款方放款通知单ID
		selcols.add("nReceiveLoanNoteID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceiveloannoteid()));
		//}else if(info.getNreceivebankid()!=-1){
		//获得收款方开户行ID
		System.out.println("fullReceiverDetailsParam info.getNreceivebankid(): " + info.getNreceivebankid());
		//收款方开户行ID
		selcols.add("nReceiveBankID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivebankid()));


		
		//nReceiveSingleBankAccountTypeID	收款方银行单边账类型ID
		selcols.add("nreceivesinglebankaccounttypei");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivesinglebankaccounttypei()));
		//nReceiveCashFlowID	收款方现金流向ID
		selcols.add("nReceiveCashFlowID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivecashflowid()));
		//}else if(info.getNreceivegeneralledgertypeid()!=-1){
		//获得收款方总账类ID
		System.out.println("fullReceiverDetailsParam info.getNreceivegeneralledgertypeid(): " + info.getNreceivegeneralledgertypeid());
		//nReceiveGeneralLedgerTypeID	收款方总账类ID
		selcols.add("nReceiveGeneralLedgerTypeID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivegeneralledgertypeid()));
		//mReceiveAmount	收款金额
		selcols.add("mReceiveAmount");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getMreceiveamount()));
		//nReceiveDirection	收款方向
		selcols.add("nReceiveDirection");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getNreceivedirection()));
		//	}
	}
	/**
	 * 组织付款方详细资料的参数方法
	 * 
	 * @param info 类型TransSpecialOperationInfo,数据源
	 * @param selcols 类型ArrayList,组织要查询的列
	 * @param compareopers 类型ArrayList,组织比较逻辑符
	 * @param logicopers 类型ArrayList,组织查询参数间逻辑关系元算符
	 * @param arr 类型ArrayList,组织查询条件值
	 */
	private void fullPayerDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//if(info.getNpayaccountid()!=-1){
		//获得付款方账户ID
		System.out.println("fullPayerDetailsParam info.getNpayaccountid(): " + info.getNpayaccountid());
		//付款账户编号
		selcols.add("npayaccountid");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpayaccountid()));
		//付款方客户编号
		selcols.add("nPayClientID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpayclientid()));
		//付款方存单号
		selcols.add("sPayFixedDepositNo");
		if ((String) info.getSpayfixeddepositno() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(new String(info.getSpayfixeddepositno()));
		}
		logicopers.add("and");
		//付款方合同ID
		selcols.add("nPayContractID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaycontractid()));
		//付款方放款通知单ID
		selcols.add("nPayLoanNoteID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpayloannoteid()));
		//}else if(info.getNpaybankid()!=-1){
		//获得付款方开户行ID
		System.out.println("fullPayerDetailsParam info.getNpaybankid(): " + info.getNpaybankid());
		//付款方开户行ID
		selcols.add("nPayBankID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaybankid()));
		

		
		//付款方银行单边账类型ID
		selcols.add("nPaySingleBankAccountTypeID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaysinglebankaccounttypeid()));
		//付款方现金流向ID
		selcols.add("nPayCashFlowID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaycashflowid()));
		//}else if(info.getNpaygeneralledgertypeid()!=-1){
		//付款方总账类ID
		System.out.println("fullPayerDetailsParam info.getNpaygeneralledgertypeid(): " + info.getNpaygeneralledgertypeid());
		selcols.add("npaygeneralledgertypeid");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaygeneralledgertypeid()));
		//付款金额
		selcols.add("mPayAmount");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getMpayamount()));
		//付款方向	
		selcols.add("nPayDirection");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getNpaydirection()));
		//}
	}
	/**
	 * 该方法用于将值对象VO映射到数据表上
	 *说明：1.VO中属性成员名称要与数据库表字段名称对应，不能有差错；VO中属性属性成员一律要小写
	 * 2.插入操作规定---arr数组中要字段名顺序要与pstm执行的插入语句中操作的字段顺序一一对应
	 * 3.更新操作规定---(I) 该更新操作where条件中只能允许指定id字段
	 * (II)arr数组中第一个元素必须是要更新表的id字段，之后的字段顺序要与update语句中set字段顺序相同
	 * 3.该方法可以用于插入操作或更新操作
	 * 
	 * @param pstm 要执行的预编译语句
	 * @param o 传入的值对象(Value Object)
	 * @param arr 传入的要插入或更新表的字段名
	 * @param operationtag 操作标志位---1:插入;-1:更新
	 * @return PreparedStatment 即将要执行的预编译语句
	 * @throws java.lang.Exception 抛出任何在该函数中发生的异常
	 */
	public void fullTableParam(PreparedStatement pstm, Object o, String[] arr, int OperationTag) throws Exception
	{
		//System.out.println(o.getClass().toString());         
		int i = 0;
		if (OperationTag == 1)
		{ //插入操作
			i = 0; //从arr[0]开始
		}
		else if (OperationTag == -1)
		{ //更新操作
			//更新操作，开始点从arr[1]开始，将arr[0](字段名id)放到最后                  	
			i = 1; //更新操作从arr[1]开始                    
			Field f0 = o.getClass().getDeclaredField(arr[0].toLowerCase());
			f0.setAccessible(true);
			pstm.setString(arr.length, f0.get(o).toString()); //ID字段对应Update语句在最后一位
		}
		while (i < arr.length)
		{ //遍历要操作的字段所有值
			//使用反射机制中getDeclaredField()方法访问相应属性             
			Field f = o.getClass().getDeclaredField(arr[i].toLowerCase());
			//由于pstm语句中字段访问从1开始，比String[] arr 多1 ,所以i+1
			i += 1;
			//setAccessible()方法可以使能访问私有或受保护的属性                  
			f.setAccessible(true);
			//get(Object obj)方法能够自动把该字段值封装进适当的包装类
			Object val = f.get(o);
			//field字段是String类型
			if (f.getType() == String.class)
			{
				//为空时处理
				if (val == null)
				{
					System.out.println(f.getName() + ": null");
					//更新处理
					if (OperationTag < 0)
					{
						//由于i已从1开始，与update语句中字段顺序一致，所以要减回1才为update语句中对应字段的位置值,但i值不会变  
						pstm.setString(i - 1, null);
					}
					//插入处理
					else
					{
						pstm.setString(i, null);
					}
				}
				else
				{
					//非空时处理
					System.out.println(f.getName() + ": " + val.toString());
					if (OperationTag < 0)
					{
						//由于i已从1开始，与update语句中字段顺序一致，所以要减回1才为update语句中对应字段的位置值,但i值不会变
						pstm.setString(i - 1, val.toString());
						//插入处理	
					}
					else
					{
						pstm.setString(i, val.toString());
					}
				}
			}
			else if (f.getType() == Timestamp.class)
			{
				//field字段是Timestamp类型
				//非空处理
				if (val != null)
				{
					System.out.println(f.getName() + ": " + val.toString());
					//更新处理
					if (OperationTag < 0)
					{
						pstm.setTimestamp(i - 1, (java.sql.Timestamp) val);
					}
					else
					{
						//插入处理	
						pstm.setTimestamp(i, (java.sql.Timestamp) val);
					}
				}
				//空属性处理
				else
				{
					System.out.println(f.getName() + ": null ");
					//更新处理							
					if (OperationTag < 0)
					{
						pstm.setTimestamp(i - 1, null);
					}
					//插入处理
					else
					{
						pstm.setTimestamp(i, null);
					}
				}
			}
			else if (f.getType() == long.class)
			{
				//field字段是long类型
				//非空处理
				if (val != null)
				{
					Log.print(f.getName() + ": " + val.toString());
					if (OperationTag < 0)
					{
						pstm.setLong(i - 1, Long.valueOf(val.toString()).longValue());
						//Log.print("Long.parseLong(val.toString())="+Long.parseLong(val.toString()));
						//Log.print("Long.valueOf(val.toString())="+Long.valueOf(val.toString()).longValue());
					}
					else
					{
						pstm.setLong(i, Long.valueOf(val.toString()).longValue());
						//Log.print("Long.parseLong(val.toString())="+Long.parseLong(val.toString()));
						//Log.print("Long.valueOf(val.toString())="+Long.valueOf(val.toString()).longValue());
					}
				}
				//空属性处理
				else
				{
					if (OperationTag < 0)
					{
						pstm.setLong(i - 1, -1);
					}
					else
					{
						pstm.setLong(i, -1);
					}
					System.out.println(f.getName() + val.toString());
				}
			}
			else if (f.getType() == double.class)
			{
				//field字段是double类型
				if (val != null)
				{
					//pstm.setDouble(i,((Double)val).doubleValue());
					if (OperationTag < 0)
					{
						pstm.setDouble(i - 1, Double.parseDouble(val.toString()));
						System.out.println(f.getName() + ": " + val.toString());
					}
					else
					{
						pstm.setDouble(i, Double.parseDouble(val.toString()));
						System.out.println(f.getName() + ": " + val.toString());
					}
				}
				else
				{
					if (OperationTag < 0)
					{
						pstm.setDouble(i - 1, 0);
					}
					else
					{
						pstm.setDouble(i, 0);
					}
					System.out.println(f.getName() + ": 0");
				}
			}
		}
	}
	/** get the current sequence id of table sett_TransSpecialOperationInfo
	 * @return the current maximum id of table sett_TransSpecialOperationInfo
	 * @Exception
	 */
	public long getNextID() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			// 从sequence中取得当天交易的序列号。			
			sbSQL.append(" select seq_TransSpecialOperationID.nextval nextid from dual");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNextID = rs.getLong("nextid");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lNextID;
	}
	/**
	 * 从ResultSet结果集映射到相应的值对象上的方法
	 * 说明：返回结果集要与值对象属性成员一一对应，且属性成员必须一律小写，方能使用          
	 *
	 * @param rs  查询返回的结果集 
	 * @Object o  值对象        
	 * @throws java.lang.Exception
	 */
	public void getTableInfo(ResultSet rs, Object o) throws Exception
	{
		//获得ResultSet结构（Schema）        	
		ResultSetMetaData rsmd = null;
		try
		{
			rsmd = rs.getMetaData();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		//TransSpecialOperationInfo tsoi= new TransSpecialOperationInfo();
		int i = 1;
		//遍历表中每个字段为值对象属性成员赋值
		while (i <= rsmd.getColumnCount())
		{
			//得到字段名
			String colname = rsmd.getColumnName(i);
			//访问值对象中每个字段
			Field f = o.getClass().getDeclaredField(colname.toLowerCase());
			//System.out.println("getDeclaredField(colname.toLowerCase()");
			f.setAccessible(true);
			//System.out.println(f.getName());
			switch (rsmd.getColumnType(i))
			{
				//varchar字段的处理     	
				case java.sql.Types.VARCHAR :
					// System.out.println(rs.getString(i));
					f.set(o, rs.getString(i));
					break;
				//NUMERIC字段的处理
				case java.sql.Types.NUMERIC :
					//字段名前为m,表示货币金额
					if (f.getName().charAt(0) == 'm')
					{
						f.set(o, (new Double(rs.getDouble(i))));
						System.out.println(f.getName() + ":" + rs.getDouble(i));
						break;
					}
					//编号等long型的处理
					else
					{
						f.set(o, (new Long(rs.getLong(i))));
						System.out.println(f.getName() + ":" + rs.getLong(i));
						break;
					}
				//float类型的处理
				case java.sql.Types.FLOAT :
					f.set(o, (new Float(rs.getFloat(i))));
					break;
				// TIMESTAMP类型的处理 
				case java.sql.Types.DATE :
					f.set(o, rs.getTimestamp(i));
					System.out.println(f.getName() + ":" + rs.getTimestamp(i));
					break;
				default :
					f.set(o, rs.getTimestamp(i));
					break;
			}
			i++;
		}
	}
	/**
	 * 函数名:
	 * 	getTableColumns
	 * 功能：
	 * 	得到表的结构
	 * 使用规则：
	 * 	表中一定要有nstatusid字段
	 * 
	 * @param tablename 表名称 String
	 * @param con	数据库连接器 Connection ；该函数不断开连接器
	 * @return  String[] 表的所有列名  
	 * @throws Exception
	 */
	public String[] getTableColumns(String tablename, Connection con) throws Exception
	{
		//获得结果集为空的shema
	    PreparedStatement pstmquery = null;
	    ResultSet rs = null;
	    String[] strs = null;
	    try
	    {
			pstmquery = con.prepareStatement("select * from " + tablename + " where nstatusid=3737");
			rs = pstmquery.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			//得到该表的所有列名
			strs = new String[rsmd.getColumnCount()];
			System.out.println("columncount:" + rsmd.getColumnCount());
			int j = 1;
			while (j <= rsmd.getColumnCount())
			{
				strs[j - 1] = rsmd.getColumnLabel(j);
				//System.out.println(strs[j-1].toLowerCase());               
				j++;
			}
			//关闭pstmquery
			this.cleanup(rs);
			this.cleanup(pstmquery);
	    }
	    finally
	    {
			this.cleanup(rs);
			this.cleanup(pstmquery);
	        
	    }
		return strs;
	}
	/**
	 * 动态条件查询结果集方法
	 * 说明：1.compareoperation数组元素个数应与cols的元素个数相等，conditionoperation数组元素个数比cols个数少1
	 * 		 2.compareoperation中的比较符号可以是"="/">"/"<"/"<>"/">="/"<="/"LIKE"/"is"
	 * 		 3.conditionoperation中的逻辑符号可以是"and"/"or"
	 * 		 4.values 全是对象
	 * 		 5.当cols|compareoperation|conditionoperation为空，values非空时，为每字段匹配条件查询
	 * 
	 * @param classname 要查询的值对象(VO)的名字
	 * @param tablename 要查询的表
	 * @param cols	    要指定的查询列名数组
	 * @param compareoperation 查询列的比较符号数组
	 * @param conditionoperation 条件逻辑关系符号数组
	 * @param values 要查询的条件值
	 * @return vector 符合条件的值对象VO结果集
	 * @throws Exception
	 */
	public Vector findByCondition(String classname, String tablename, ArrayList cols, ArrayList compareoperation, ArrayList conditionoperation, ArrayList values) throws Exception
	{
		System.out.println("\n findByCondition begin \n");
		if (cols.size() == 2)
		{
			//用户什么都不添
			return null;
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			con = this.getConnection();
			//pstm=con.prepareStatement("select * from sett_transSpecialOperation where sExtAccountNo='aaa' and sBankChequeNo is NULL and sExtClientName is NULL and sRemitInProvince is NULL and sRemitInBank is NULL and sDeclarationNo is NULL and sRemitInCity='bj' ");
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from ");
			buffer.append(tablename);
			buffer.append(" where ");
			if (cols == null && compareoperation == null && conditionoperation == null && !values.isEmpty())
			{
				PreparedStatement psmeta = con.prepareStatement("select * from " + tablename + " where nstatusid=3737");
				//System.out.println("select * from "+tablename+ " where nstatusid=3737");
				ResultSet rsmeta = psmeta.executeQuery();
				ResultSetMetaData rsmd = rsmeta.getMetaData();
				int j = 1;
				String[] allcols = new String[rsmd.getColumnCount()];
				while (j <= rsmd.getColumnCount() - 1)
				{
					buffer.append(rsmd.getColumnLabel(j));
					//System.out.println(rsmd.getColumnLabel(j));
					allcols[j - 1] = rsmd.getColumnLabel(j);
					buffer.append("=? and \n");
					j++;
				}
				buffer.append(rsmd.getColumnLabel(rsmd.getColumnCount()));
				allcols[j - 1] = rsmd.getColumnLabel(rsmd.getColumnCount());
				//System.out.println(cols[j-1]);
				buffer.append("=?");
				this.cleanup(rsmeta);
				this.cleanup(psmeta);
			}
			else if ((conditionoperation != null) && (conditionoperation.size() >= 1))
			{
				int i = 0;
				for (i = 0; i < cols.size() - 1; i++)
				{
					String mycols = (String) cols.get(i);
					String mycompareoper = (String) compareoperation.get(i);
					String myconditionoper = (String) conditionoperation.get(i);
					buffer.append(mycols);
					if (mycompareoper.toLowerCase().compareTo("is") == 0)
					{
						buffer.append(" is NULL ");
					}
					else if (mycompareoper.toLowerCase().compareTo("like") == 0)
					{
						buffer.append(" like ");
						buffer.append("? ");
					}
					else
					{
						buffer.append(mycompareoper);
						buffer.append("? ");
					}
					buffer.append(myconditionoper);
					buffer.append(" \n");
				}
				buffer.append((String) cols.get(cols.size() - 1));
				buffer.append((String) compareoperation.get(compareoperation.size() - 1));
				buffer.append("? \n");
			}
			else if ((conditionoperation == null) || (conditionoperation.size() == 0))
			{
				buffer.append((String) cols.get(0));
				buffer.append((String) compareoperation.get(0));
				if (((String) compareoperation.get(0)).toLowerCase().compareTo("is") == 0)
				{
					buffer.append(" is NULL ");
				}
				else if (((String) compareoperation.get(0)).toLowerCase().compareTo("like") == 0)
				{
					buffer.append(" like ");
					buffer.append("? ");
				}
				else
				{
					buffer.append(((String) compareoperation.get(0)));
					buffer.append("? ");
				}
				buffer.append("?");
			}
			String cmd = new String(buffer);
			//String copycmd=null;
			if (cmd.endsWith("and"))
			{
				buffer.delete(buffer.length() - 3, buffer.length());
			}
			System.out.println("findbycondition sql: ");
			System.out.println(buffer);
			pstm = con.prepareStatement(new String(buffer));
			int p = 0;
			//遍历要操作的字段所有值
			System.out.println("values.size: " + values.size());
			//有值数列计数器
			int counter = 1;
			while (p < values.size())
			{
				Object val = values.get(p);
				//由于pstm语句中字段访问从1开始，比values.size() 多1 ,所以p+1
				p++;
				//val是String类型
				if (val.getClass() == String.class)
				{
					//为空时处理
					if (val.equals("String"))
					{
						System.out.println((String) cols.get(p - 1) + ": null");
						//pstm.setString(p,null);
					}
					else if (val.toString().compareTo("Timestamp") == 0)
					{
						System.out.println((String) cols.get(p - 1) + ": null ");
						//pstm.setTimestamp(p,null); 	
					}
					else if (val.toString().compareTo("Long") == 0)
					{
						System.out.println((String) cols.get(p - 1) + val.toString());
						//pstm.setLong(p,-1); 						
					}
					else if (val.toString().compareTo("Double") == 0)
					{
						System.out.println((String) cols.get(p - 1) + ": 0");
						//pstm.setDouble(p,0); 
					}
					//字符串非空时处理	
					else
					{
						System.out.println((String) cols.get(p - 1) + ": " + val.toString());
						pstm.setString(counter, val.toString());
						counter++;
					}
				}
				else 
				//val字段是Timestamp类型
				if (val instanceof Timestamp)
				{
					//非空处理
					if (val != null)
					{
						pstm.setTimestamp(counter, (java.sql.Timestamp) val);
						System.out.println((String) cols.get(p - 1) + ": " + val.toString());
						counter++;
						// java.sql.Date mydate=(java.sql.Date)val;
						//pstm.setDate(p,mydate);
					}
					/*
					 //空属性处理
					 else{
					 System.out.println(cols[p-1]+": null ");
					 pstm.setTimestamp(p,null); 
					 }  
					 */
				}
				//val字段是long类型
				else if (val instanceof Long)
				{
					//非空处理
					if (val != null)
					{
						pstm.setLong(counter, Long.parseLong(val.toString()));
						System.out.println((String) cols.get(p - 1) + ": " + ((Long) val).toString());
						counter++;
						//pstm.setLong(p,((Long)val).longValue());
					}
					/*					
					 //空属性处理
					 else{
					 pstm.setLong(p,-1);
					 System.out.println(cols[p-1]+val.toString());
					 } 
					 */
				}
				else 
				//val字段是double类型
				if (val instanceof Double)
				{
					if (val != null)
					{
						//pstm.setDouble(p,((Double)val).doubleValue());
						pstm.setDouble(counter, Double.parseDouble(val.toString()));
						System.out.println((String) cols.get(p - 1) + ": " + val.toString());
						counter++;
					}
					/*
					 else{
					 pstm.setDouble(p,0);
					 System.out.println(cols[p-1]+": 0");
					 }
					 */
				}
			}
			rs = pstm.executeQuery();
			while (rs.next())
			{
				Object obj = Class.forName(classname).newInstance();
				this.getTableInfo(rs, obj);
				if (obj != null)
				{
					v.add(obj);
					Field[] fs = obj.getClass().getDeclaredFields();
					int h = 0;
					System.out.println("\n obj shows begin \n");
					while (h < fs.length)
					{
						fs[h].setAccessible(true);
						if (fs[h].get(obj) != null)
						{
							System.out.println(fs[h].getName() + ": " + fs[h].get(obj).toString());
						}
						else
						{
							System.out.println(fs[h].getName() + ": " + "null");
						}
						h++;
					}
					System.out.println("\n obj shows okey \n");
				}
				else
				{
					System.out.println("obj doesn't build");
				}
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
			System.out.println("\n findByCondition end \n");
		}
		finally
		{
		    this.cleanup(rs);
		    this.cleanup(pstm);
		    this.cleanup(con);		    
		}
		return v;
	}
	/**
	 * 功能：通过提前还款通知单ID查询子账户ID
	 * @param lPayFormID
	 * @return
	 * @
	 */
	public long getSubAccountIDByPayFormID(long lPayFormID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			// 从sequence中取得当天交易的序列号。			
			sbSQL.append(" select ID from sett_subAccount where nStatusID>0 and al_nLoanNoteID=" + lPayFormID);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNextID = rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lNextID;
	}
	
	public static void main(String[] args){
	    System.out.println("ok");
	}
	
}