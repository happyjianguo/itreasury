package com.iss.itreasury.settlement.transferinterest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.transferinterest.bizlogic.DateUtil;
import com.iss.itreasury.settlement.transferinterest.dataentity.CraInterestInfo;
import com.iss.itreasury.settlement.transferinterest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.transferinterest.dataentity.TransferInterestRecordInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetialResultInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanAmountDetailinfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

public class CalculateCraInterestDao extends ITreasuryDAO{
	
	public CalculateCraInterestDao() throws IException
	{
		super("");
	}
	/**如果数据库连接已经在构造函数中传入(即这个数据库连接由外部维护)，直接返回这个连接，否则
	 *建立数据库连接并且在使用完毕后立刻关闭，参见void cleanup(Connection con)
	*/
	protected Connection getConnection()
	{
		Connection con = null;
		try
		{
			if(transConn == null)
			{
				con = Database.getConnection();
			}
			else
			{
				con = transConn;
			}
		}
		catch (Exception sqle)
		{
			sqle.printStackTrace();
		}
		return con;		
	}
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{
			//Log.print("进入关闭连接方法");
			/**transConn　不为空表明这个数据库连接相关的事务不是容器维护的，因此不在此处关闭
			 * 即　Assert(con == transConn)
			 */
			//Log.print("con ="+con);
			//Log.print("transConn ="+transConn);
			if (con != null && con.isClosed()==false && transConn == null)
			{
				//Log.print("关闭连接--开始");
				con.close();
				con = null;
				//Log.print("关闭连接--结束");
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	/**
	 * 查询合同(结息使用，此方法关联结息记录表)
	 * @param 
	 * @return Collection
	 * @throws Exception 
	 * @throws IException
	 */
	public Collection getClearContract(Connection con,CraInterestInfo qInfo) throws Exception
	{
		Vector vec = new Vector();
		StringBuffer sql = null;
		Connection conInternal = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		try{
			
	        sql = new StringBuffer();
	        sql.append(" select distinct(a.id) id,a.contractcode scontractcode,a.transtypeid,c.id counterpartid,c.counterpartname,a.officeid,a.currencyid \n");      
	        sql.append(" from cra_transfercontractform  a ,sett_transferloanamount b ,cra_counterpart c  \n");
	        sql.append(" where a.id = b.transfercontractid \n");
	        sql.append(" and a.counterpartid = c.id and b.counterpartid = c.id \n");
	        sql.append(" and a.statusid = "+CRAconstant.TransactionStatus.USED );
	        sql.append(" and b.statusid = "+SETTConstant.TransactionStatus.CHECK);	 
	        sql.append(" and b.transactiontypeid = "+SETTConstant.TransactionType.TRANSFERREPAY);	 
			if(qInfo.getLCraCounterID() > 0)
			{
				sql.append(" and c.id = "+ qInfo.getLCraCounterID() +" ");
			}
			if (!qInfo.getStrCraContractNoFrom().trim().equals(""))
			{ //转让合同号开始
				sql.append(" and( ");
				sql.append(" a.contractcode >= '" + qInfo.getStrCraContractNoFrom() + "'");
				sql.append(" ) ");
			}
			if (!qInfo.getStrCraContractNoTo().trim().equals(""))
			{ //转让合同号结束
				sql.append(" and( ");
				sql.append(" a.contractcode <= '" + qInfo.getStrCraContractNoTo() + "'");
				sql.append(" ) ");
			}
			if(qInfo.getLCraContractTypeId()> 0)
			{
	        	sql.append(" and a.transtypeid = "+ qInfo.getLCraContractTypeId() +" ");
			}
			if (qInfo.getDtClearInterest() != null)
			{ //结息日
				sql.append(" and ");
				sql.append(
					" b.intereststart <= to_date('"
						+ DataFormat.formatDate(qInfo.getDtClearInterest())
						+ "','yyyy-mm-dd')");							
			}			
			sql.append(" order by a.contractcode \n");	        
	        System.out.println(sql.toString());
	        ps =conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();	        
	        while(rs.next())
	        {
	        	TransferContractInfo conInfo = new TransferContractInfo();
	        	
	        	conInfo.setId(rs.getLong("id"));
	        	conInfo.setContractCode(rs.getString("scontractcode"));
	        	conInfo.setTranstypeId(rs.getLong("transtypeid"));
	        	conInfo.setCounterPartId(rs.getLong("counterpartid"));
	        	conInfo.setCounterPartName(rs.getString("counterpartname"));
	        	conInfo.setOfficeId(rs.getLong("officeid"));
	        	conInfo.setCurrencyId(rs.getLong("currencyid"));
	        	
	        	vec.add(conInfo);
	        }
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	    finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}	    	
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return vec;
	}
	/**
	 * 查询卖出回购转让合同的上次结息日或放款开始日期
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public Timestamp findDtStratDate(Connection con,TransferContractInfo qInfo) throws Exception
	{
		Timestamp dtStartDate = null;
		Connection conInternal = null;
		StringBuffer sql = null;
		StringBuffer sqlBuffer = null;
		StringBuffer bufferSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement preps = null;
		ResultSet resrs = null;
		PreparedStatement prepStat = null;
		ResultSet reset = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();//针对取消计提时，我们取取消计提日前一个计提日
	        sql.append(" select max(sett.DTINTERESTSETTLEMENT) clearInterest from SETT_TRANSFERINTERSETRECORD sett \n");
	        sql.append(" where sett.statusid = " + Constant.RecordStatus.VALID);
	        sql.append(" and sett.cracontractid = "+ qInfo.getId());
	        sql.append(" and sett.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.PREDRAWINTEREST);
	        sql.append(" and sett.dtinterestsettlement < (");
	        sql.append(" select k.dtinterestsettlement from sett_transferintersetrecord k \n");
	        sql.append(" where k.statusid = " + Constant.RecordStatus.VALID);
	        sql.append(" and k.cracontractid = "+ qInfo.getId());
	        sql.append(" and k.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.PREDRAWINTEREST);
	        sql.append(" and k.dtinterestsettlement < (");
	        sql.append(" select min(m.dtinterestsettlement) from sett_transferintersetrecord m \n");
	        sql.append(" where m.statusid = " + Constant.RecordStatus.VALID);
	        sql.append(" and m.cracontractid = "+ qInfo.getId());
	        sql.append(" and m.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST);
	        sql.append(" )) \n");
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())
	        {
	        	dtStartDate = rs.getTimestamp("clearInterest");
	        }
	        if(dtStartDate == null)
	        {
	        	bufferSQL = new StringBuffer();//计提记录表里面有值
	        	bufferSQL.append(" select max(b.DTINTERESTSETTLEMENT) clearInterest from SETT_TRANSFERINTERSETRECORD b \n");
	        	bufferSQL.append(" where b.statusid = " + Constant.RecordStatus.VALID);
	        	bufferSQL.append(" and b.cracontractid = "+ qInfo.getId());
	        	bufferSQL.append(" and b.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.PREDRAWINTEREST);
	        	prepStat = conInternal.prepareStatement(bufferSQL.toString());
	        	reset = prepStat.executeQuery();
	        	if(reset!= null && reset.next())
		        {
		        	dtStartDate = reset.getTimestamp("clearInterest");
		        }
	        }
	        if( dtStartDate == null)
	        {
	        	sqlBuffer = new StringBuffer();//计提记录表没有值，取交易
	        	sqlBuffer.append(" select min(k.intereststart) clearInterest from sett_transferloanamount k \n");
	        	sqlBuffer.append(" where k.statusid = " + SETTConstant.TransactionStatus.CHECK );
	        	sqlBuffer.append(" and k.transfercontractid = " + qInfo.getId());
	        	sqlBuffer.append(" and k.TRANSFERTYPE = " + qInfo.getTranstypeId());	
	        	sqlBuffer.append(" and k.TRANSACTIONTYPEID = " + SETTConstant.TransactionType.TRANSFERREPAY);
	        	preps = conInternal.prepareStatement(sqlBuffer.toString());
	        	resrs = preps.executeQuery();
	        	if(resrs!=null && resrs.next())
	        	{
	        		dtStartDate = resrs.getTimestamp("clearInterest");
	        	}
	        }
			
	        System.out.println("查询转让合同的上次结息日或放款开始日期"+sql.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}
	    	if(prepStat!=null)
	    	{
	    		prepStat.close();
	    		prepStat=null;
	    	}
	    	if(reset!=null)
	    	{
	    		reset.close();
	    		reset=null;
	    	}
	    	if(preps!=null)
	    	{
	    		preps.close();
	    		preps=null;
	    	}
	    	if(resrs!=null)
	    	{
	    		resrs.close();
	    		resrs=null;
	    	}	    	
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return dtStartDate;
	}
	/**
	 * 查询卖出回购转让合同的上次结息日或放款开始日期
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public Timestamp findNewDtStratDate(Connection con,TransferContractInfo qInfo) throws Exception
	{
		Timestamp dtStartDate = null;
		Connection conInternal = null;
		StringBuffer bufferSQL = null;
		PreparedStatement prepStat = null;
		ResultSet reset = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
						
	        	bufferSQL = new StringBuffer();
	        	bufferSQL.append(" select decode(max(b.DTINTERESTSETTLEMENT),null,to_date('2001-1-1', 'yyyy-mm-dd'),max(b.DTINTERESTSETTLEMENT)) clearInterest from SETT_TRANSFERINTERSETRECORD b \n");
	        	bufferSQL.append(" where b.statusid = " + Constant.RecordStatus.VALID);
	        	bufferSQL.append(" and b.cracontractid = "+ qInfo.getId());
	        	bufferSQL.append(" and b.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.PREDRAWINTEREST);
	        	bufferSQL.append(" and b.officeid =" +qInfo.getOfficeId() );
	        	bufferSQL.append(" and b.currencyid = " + qInfo.getCurrencyId());
	        	bufferSQL.append(" union ");
	        	bufferSQL.append(" select decode(min(k.intereststart),null,to_date('2001-1-1', 'yyyy-mm-dd'),min(k.intereststart)) clearInterest from sett_transferloanamount k \n");
	        	bufferSQL.append(" where k.statusid = " + SETTConstant.TransactionStatus.CHECK );
	        	bufferSQL.append(" and k.transfercontractid = " + qInfo.getId());
	        	bufferSQL.append(" and k.TRANSFERTYPE = " + qInfo.getTranstypeId());	
	        	bufferSQL.append(" and k.TRANSACTIONTYPEID = " + SETTConstant.TransactionType.TRANSFERREPAY);
	        	bufferSQL.append(" and k.officeid = " + qInfo.getOfficeId());
	        	bufferSQL.append(" and k.currencyid = " + qInfo.getCurrencyId());
	        	bufferSQL.append(" union ");
	        	bufferSQL.append(" select decode(max(t.intereststart),null,to_date('2001-1-1', 'yyyy-mm-dd'), max(t.intereststart)) clearInterest from sett_transferloanamount t \n");
	        	bufferSQL.append(" where t.statusid = " + SETTConstant.TransactionStatus.CHECK );
	        	bufferSQL.append(" and t.transfercontractid = " + qInfo.getId());
	        	bufferSQL.append(" and t.TRANSFERTYPE = " + qInfo.getTranstypeId());	
	        	bufferSQL.append(" and t.INTEREST > 0 " );	
	        	bufferSQL.append(" and t.TRANSACTIONTYPEID = " + SETTConstant.TransactionType.TRANSFERPAY);
	        	bufferSQL.append(" and t.officeid = " + qInfo.getOfficeId());
	        	bufferSQL.append(" and t.currencyid = " + qInfo.getCurrencyId());
	        	bufferSQL.append(" order by clearInterest desc  ");
	        	prepStat = conInternal.prepareStatement(bufferSQL.toString());
	        	reset = prepStat.executeQuery();
	        	if(reset!= null && reset.next())
		        {
		        	dtStartDate = reset.getTimestamp("clearInterest");
		        }
	       			
	        System.out.println("查询转让合同的上次结息日或放款开始日期"+bufferSQL.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(prepStat!=null)
	    	{
	    		prepStat.close();
	    		prepStat=null;
	    	}
	    	if(reset!=null)
	    	{
	    		reset.close();
	    		reset=null;
	    	}	    	
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return dtStartDate;
	}
	
	/**
	 * 查询转让合同起始余额
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public double findStartBalance(Connection con,TransferContractInfo qInfo) throws Exception
	{
		double skamount = 0.0;
		double fkamount = 0.0;
		double startBalance = 0.0;
		Connection conInternal = null;
		StringBuffer sql = null;
		StringBuffer sqlBuffer = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement preps = null;
		ResultSet resrs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append("  select nvl(sum(a.amount),0) skamount from sett_transferloanamount a  \n");
	        sql.append(" where a.statusid = " + SETTConstant.TransactionStatus.CHECK);
	        sql.append(" and a.transfercontractid = "+ qInfo.getId());
	        sql.append(" and a.transactiontypeid = "+ SETTConstant.TransactionType.TRANSFERREPAY);
	        sql.append(" and a.intereststart < to_date('"
						+ DataFormat.formatDate(qInfo.getPreclearInterstDate())
						+ "','yyyy-mm-dd')");							
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())
	        {
	        	skamount = rs.getDouble("skamount");
	        }
	       
	        sqlBuffer = new StringBuffer();
	        sqlBuffer.append(" select nvl(sum(b.amount),0) fkamount from sett_transferloanamount b \n");
	        sqlBuffer.append(" where b.statusid = " + SETTConstant.TransactionStatus.CHECK );
	        sqlBuffer.append(" and b.transfercontractid = " + qInfo.getId());
	        sqlBuffer.append(" and b.transactiontypeid = " + SETTConstant.TransactionType.TRANSFERPAY);	
	        sqlBuffer.append(" and b.intereststart < to_date('"
					+ DataFormat.formatDate(qInfo.getPreclearInterstDate())
					+ "','yyyy-mm-dd')");	
	        preps = conInternal.prepareStatement(sqlBuffer.toString());
	        resrs = preps.executeQuery();
	        if(resrs!=null && resrs.next())
	        {
	        	fkamount = resrs.getDouble("fkamount");
	        }
	        startBalance = skamount - fkamount;
	        System.out.println("查询转让合同的收款初始余额"+sql.toString());
	        System.out.println("查询转让合同的付款初始余额"+sqlBuffer.toString());
	        System.out.println("查询转让合同的初始余额值"+startBalance);
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}
	    	if(preps!=null)
	    	{
	    		preps.close();
	    		preps=null;
	    	}
	    	if(resrs!=null)
	    	{
	    		resrs.close();
	    		resrs=null;
	    	}
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return startBalance;
	}
	/**
	 * 查询收付款交易
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public Collection queryTransInfo(Connection con,TransferContractInfo qInfo) throws Exception
	{
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rset = null;		
		Vector coll = new Vector();
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append("  select a.* from sett_transferloanamount a \n");
	        sql.append("  where a.statusid = " + SETTConstant.TransactionStatus.CHECK);
	        sql.append("  and a.transfercontractid = "+ qInfo.getId());
	        sql.append("  and a.transactiontypeid in("+SETTConstant.TransactionType.TRANSFERPAY+","+SETTConstant.TransactionType.TRANSFERREPAY+")" );
	        sql.append("  and a.intereststart >= to_date('"
						+ DataFormat.formatDate(qInfo.getPreclearInterstDate())
						+ "','yyyy-mm-dd')");
	        sql.append("  and a.intereststart <= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterstDate())
					+ "','yyyy-mm-dd')");
	        sql.append(" and a.amount >0 ");
	        sql.append(" order by a.intereststart,a.id  ");
	        ps = conInternal.prepareStatement(sql.toString());
	        rset = ps.executeQuery();
	        while(rset!= null && rset.next())
	        {
	        	TransferLoanContractInfo info = new TransferLoanContractInfo();
	        	info.setID(rset.getLong("id"));
	        	info.setOFFICEID(rset.getLong("officeid"));
	        	info.setCURRENCYID(rset.getLong("currencyid"));
	        	info.setSTRANSNO(rset.getString("stransno") == null ? "" : rset.getString("stransno"));
	        	info.setTRANSACTIONTYPEID(rset.getLong("transactiontypeid"));
	        	info.setNOTICEID(rset.getLong("noticeid"));
	        	info.setPAYBANKID(rset.getLong("paybankid"));
	        	info.setPAYGENRALLEDGERTYPEID(rset.getLong("PAYGENRALLEDGERTYPEID"));
	        	info.setAMOUNT(rset.getDouble("amount"));
	        	info.setINTEREST(rset.getDouble("interest"));
	        	info.setCOMMISSION(rset.getDouble("commission"));
	        	info.setRECEIVEBANKID(rset.getLong("RECEIVEBANKID"));
	        	info.setRECGENERALLEDGERTYPEID(rset.getLong("RECGENERALLEDGERTYPEID"));
	        	info.setINTERESTSTART(rset.getTimestamp("intereststart"));
	        	info.setEXECUTE(rset.getTimestamp("execute"));
	        	info.setINPUTUSERID(rset.getLong("inputuserid"));
	        	info.setSABSTRACT(rset.getString("sabstract"));
	        	info.setSCHECKABSTRACT(rset.getString("scheckabstract"));
	        	info.setABSTRACTID(rset.getLong("abstractid"));
	        	info.setSTATUSID(rset.getLong("statusid"));
	        	info.setCHECKUSERID(rset.getLong("checkuserid"));
	        	info.setNMODIFY(rset.getTimestamp("nmodify"));
	        	info.setINPUTDATE(rset.getTimestamp("inputdate"));
	        	coll.add(info);
	        }
	       
	        System.out.println("查询转让合同的结算交易记录"+sql.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rset!=null)
	    	{
	    		rset.close();
	    		rset=null;
	    	}	    	
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return coll;
	}
	/**
	 * 查询转让合同的利率
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public double queryInterestRate(Connection con,TransferContractInfo qInfo) throws Exception
	{
		double dtRate = 0.00;
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		try{
			
			sql = new StringBuffer();
	        sql.append(" select k.drate from cra_transfercontractform k  \n");
	        sql.append(" where k.statusid = " + CRAconstant.TransactionStatus.USED);
	        sql.append(" and k.id = "+ qInfo.getId());
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())
	        {
	        	dtRate = rs.getDouble("drate");
	        }
	       		
	        System.out.println("查询转让合同的利率"+sql.toString());
	        System.out.println("查询转让合同的利率"+dtRate);
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}	    	
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return dtRate;
	}
	/**
	 * 查询收付款利息交易
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public double querySumInterest(Connection con,TransferContractInfo qInfo) throws Exception
	{
		double sumInterest = 0.0;
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append("  select nvl(sum(a.interest),0) sumInterest from sett_transferloanamount a  \n");
	        sql.append(" where a.statusid = " + SETTConstant.TransactionStatus.CHECK);
	        sql.append(" and a.transfercontractid = "+ qInfo.getId());
	        sql.append(" and a.transactiontypeid = "+ SETTConstant.TransactionType.TRANSFERPAY);
	        sql.append("  and a.intereststart >= to_date('"
	 				+ DataFormat.formatDate(qInfo.getPreclearInterstDate())
	 				+ "','yyyy-mm-dd')");
            sql.append("  and a.intereststart <= to_date('"
				+ DataFormat.formatDate(DateUtil.getNextNDay(qInfo.getClearInterstDate(),1))
				+ "','yyyy-mm-dd')");							
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())
	        {
	        	sumInterest = rs.getDouble("sumInterest");
	        }

	        System.out.println("查询收付款利息交易"+sql.toString());
	        System.out.println("查询收付款利息值"+sumInterest);
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}	    	
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return sumInterest;
	}
	/**
	 * 查询结息记录
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public TransferInterestRecordInfo queryPreDateByContractID(Connection con,TransferInterestRecordInfo qInfo) throws Exception
	{
		TransferInterestRecordInfo info  = new TransferInterestRecordInfo();
		Timestamp dtPrawDate = null;
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append(" select max(sett.DTINTERESTSETTLEMENT) clearInterest from SETT_TRANSFERINTERSETRECORD sett \n");
	        sql.append(" where sett.statusid = " + Constant.RecordStatus.VALID);
	        sql.append(" and sett.cracontractid = "+ qInfo.getCracontractid());
	        sql.append(" and sett.DTINTERESTSETTLEMENT = to_date('"
					+ DataFormat.formatDate(qInfo.getDtinterestsettlement())
					+ "','yyyy-mm-dd')");
	        sql.append(" and sett.NINTERESTTYPE = "+ qInfo.getInterestType());
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())	        	
	        {
	        	dtPrawDate = rs.getTimestamp("clearInterest");
	        	info.setDtinterestsettlement(dtPrawDate);
	        }
	
	        System.out.println("查询转让合同的结息记录日期"+sql.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}	    	
			if(con==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return info;
	}
	/**
	 * 新增结息表数据的方法：
	 * 逻辑说明：
	 * 
	 * @param info, InterestSettmentInfo, 结息交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long add(Connection con, TransferInterestRecordInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		try
		{

			//利用数据库的取ID;			
			long id = getID(conInternal);
			info.setId(id);

			StringBuffer buffer = new StringBuffer();
			buffer.append(" INSERT INTO SETT_TRANSFERINTERSETRECORD \n ");
			buffer.append(" ( ID, OfficeID, CurrencyID, STRANSNO, DTSTART \n ");
			buffer.append(" , DTEND, DAYS, AMOUNT, MINTEREST, DRATE \n ");
			buffer.append(" , DTEXECUTE, SABSTRACT, STATUSID, INPUTUSERID, INPUTDATE \n ");
			buffer.append(" , DTINTERESTSETTLEMENT, CRACONTRACTID, NINTERESTTYPE,PREDRAWINTEREST,CRACONTRACTDETAILID,PAYINTERESTACCOUNTID )\n ");
			buffer.append(" VALUES \n ");
			buffer.append("( ?,?,?,?,?, \n ");
			buffer.append(" ?,?,?,?,?, \n ");
			buffer.append(" ?,?,?,?,sysdate,\n ");
			buffer.append(" ?,?,?,?,?,? ) \n ");

			ps = conInternal.prepareStatement(buffer.toString());
			log.info(buffer.toString());
			
			int index = 1;
			ps.setLong(index++, info.getId());
			ps.setLong(index++, info.getOfficeid());
			ps.setLong(index++, info.getCurrencyid());
			ps.setString(index++, info.getStransno());
			ps.setTimestamp(index++, info.getDtstart());
			ps.setTimestamp(index++, info.getDtend());
			ps.setLong(index++, info.getDays());
			ps.setDouble(index++, info.getAmount());
			ps.setDouble(index++, info.getMinterest());
			ps.setDouble(index++, info.getDrate());
			ps.setTimestamp(index++, info.getDtexecute());
			ps.setString(index++, info.getSabstract());
			ps.setLong(index++, info.getStatusid());
			ps.setLong(index++, info.getInputuserid());
			ps.setTimestamp(index++, info.getDtinterestsettlement());
			ps.setLong(index++, info.getCracontractid());
			ps.setLong(index++, info.getInterestType());
			ps.setDouble(index++, info.getMinterest());
			ps.setLong(index++, info.getCraContractDetailID());
			ps.setLong(index++, info.getPayInterestAccountID());

			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getId();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
			if(con==null)
			{
			   cleanup(conInternal);
			}
		}
		return lReturn;
	}
	private long getID(Connection conn) throws Exception
	{
		long id = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer sb = new StringBuffer();
			sb.append("select SEQ_TRANSFERINTERSETRECORD.nextval nextid from dual");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				id = rs.getLong("nextid");
			}
		}
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}	    	
	    	/*
			if(conn!=null)
			{
			   cleanup(conn);
			}
			*/
            //finalizeDAO();
        }
		return id;
	}
	/**
	 * 查询转让合同的结息日已计提利息
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public double queryPrawInterest(Connection con,TransferContractInfo qInfo) throws Exception
	{
		double prawInterest = 0.0;
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append(" select sum(sett.PREDRAWINTEREST) clearInterest from SETT_TRANSFERINTERSETRECORD sett \n");
	        sql.append(" where sett.statusid = " + Constant.RecordStatus.VALID);
	        sql.append(" and sett.cracontractid = "+ qInfo.getId());
		    sql.append(" and sett.DTINTERESTSETTLEMENT >= to_date('"
		 				+ DataFormat.formatDate(qInfo.getPreclearInterstDate())
		 				+ "','yyyy-mm-dd')");
	        sql.append(" and sett.DTINTERESTSETTLEMENT <= to_date('"
					+ DataFormat.formatDate(DateUtil.getNextNDay(qInfo.getClearInterstDate(), 1))
					+ "','yyyy-mm-dd')");
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())
	        {
	        	prawInterest = rs.getDouble("clearInterest");
	        }
	        
			
	        System.out.println("查询转让合同的结息日已计提利息"+sql.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}	    	
			if(con == null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return prawInterest;
	}
	/**
	 * 查询卖出卖断转让合同的上次结息日或放款开始日期
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public Timestamp queryDtStratDate(Connection con,TransferContractInfo qInfo) throws Exception
	{
		Timestamp dtStartDate = null;
		Connection conInternal = null;
		StringBuffer sqlBuffer = null;
		StringBuffer bufferSQL = null;
		PreparedStatement preps = null;
		ResultSet resrs = null;
		PreparedStatement prepStat = null;
		ResultSet reset = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
	        	bufferSQL = new StringBuffer();//结息记录表里面有值
	        	bufferSQL.append(" select max(b.DTINTERESTSETTLEMENT) clearInterest from SETT_TRANSFERINTERSETRECORD b \n");
	        	bufferSQL.append(" where b.statusid = " + Constant.RecordStatus.VALID);
	        	bufferSQL.append(" and b.cracontractid = "+ qInfo.getId());
	        	bufferSQL.append(" and b.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
	        	prepStat = conInternal.prepareStatement(bufferSQL.toString());
	        	reset = prepStat.executeQuery();
	        	if(reset!= null && reset.next())
		        {
		        	dtStartDate = reset.getTimestamp("clearInterest");
		        }
	            if( dtStartDate == null)
	            {
	        	sqlBuffer = new StringBuffer();//结息记录表没有值，取交易
	        	sqlBuffer.append(" select min(k.intereststart) clearInterest from sett_transferloanamount k \n");
	        	sqlBuffer.append(" where k.statusid = " + SETTConstant.TransactionStatus.CHECK );
	        	sqlBuffer.append(" and k.transfercontractid = " + qInfo.getId());
	        	sqlBuffer.append(" and k.TRANSFERTYPE = " + qInfo.getTranstypeId());	
	        	sqlBuffer.append(" and k.TRANSACTIONTYPEID = " + SETTConstant.TransactionType.TRANSFERREPAY);
	        	preps = conInternal.prepareStatement(sqlBuffer.toString());
	        	resrs = preps.executeQuery();
	        	if(resrs!=null && resrs.next())
	        	{
	        		dtStartDate = resrs.getTimestamp("clearInterest");
	        	}
	        }
			
	        System.out.println("查询卖出卖断转让合同的上次结息日或放款开始日期"+bufferSQL.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	    finally{
	    	if(preps!=null)
	    	{
	    		preps.close();
	    		preps=null;
	    	}
	    	if(resrs!=null)
	    	{
	    		resrs.close();
	    		resrs=null;
	    	}
	    	if(prepStat!=null)
	    	{
	    		prepStat.close();
	    		prepStat=null;
	    	}
	    	if(reset!=null)
	    	{
	    		reset.close();
	    		prepStat=null;
	    	}
	    	if(con==null)
			{
			   cleanup(conInternal);
			}
	    	// finalizeDAO();
        }
		return dtStartDate;
	}
	/**
	 * 查询卖出卖断明细收付款交易期初余额
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public Collection queryBreakTrans(Connection con,TransferContractInfo qInfo) throws Exception
	{
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rset = null;		
		Vector coll = new Vector();
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append("  select sum( h.amount-f.amount) balance,h.noticeformid ,h.contractid from sett_transferloandetail h , \n");	 
	        sql.append(" (select m.* ,n.transfercontractid from sett_transferagentamount m, sett_transferloanamount n  \n " );
            sql.append("  where m.transferloanamountid = n.id  \n");
            sql.append("  and n.transfercontractid = "+ qInfo.getId());
            sql.append("  and m.statusid = " + SETTConstant.TransactionStatus.CHECK);
            sql.append("  and n.statusid = " + SETTConstant.TransactionStatus.CHECK);
            sql.append("  and n.intereststart <= to_date(' \n"
					+ DataFormat.formatDate(qInfo.getPreclearInterstDate())
					+ "','yyyy-mm-dd') ) f \n");
	        sql.append("  where  h.contractid = f.loancontractid \n" );
	        sql.append("  and h.noticeformid = f.loanpaynoticeid \n" );
	        sql.append("  and h.statusid = " + SETTConstant.TransactionStatus.CHECK);
	        sql.append("  and f.transfercontractid = "+ qInfo.getId());       
	        sql.append("  and h.INTRESTSTART <= to_date(' "
					+ DataFormat.formatDate(qInfo.getPreclearInterstDate())
					+ "','yyyy-mm-dd') ");
	        sql.append(" group by h.noticeformid,h.contractid ");
	        ps = conInternal.prepareStatement(sql.toString());
	        rset = ps.executeQuery();
	        while(rset!= null && rset.next())
	        {
	        	NoticeAndAgentDetialResultInfo info = new NoticeAndAgentDetialResultInfo();
	        	info.setBalance(rset.getDouble("balance"));
	        	info.setLoanPayNoticeID(rset.getLong("noticeformid"));
	        	info.setLoanContractID(rset.getLong("contractid"));
	        	coll.add(info);
	        }
	       
	        System.out.println("查询卖出卖断明细收付款交易期初余额"+sql.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rset!=null)
	    	{
	    		rset.close();
	    		rset=null;
	    	}	    	
			if(con ==null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return coll;
	}
	public Collection queryBreakTransInfo(Connection con,NoticeAndAgentDetialResultInfo qInfo) throws Exception
	{
		Connection conInternal = null;

		StringBuffer strSQL = null;	
		StringBuffer skSQL = null;
		PreparedStatement skps = null;
		ResultSet skrs = null;		
		StringBuffer fkSQL = null;
		PreparedStatement fkps = null;
		ResultSet fkrs = null;		
		PreparedStatement prepStat = null;
		ResultSet rstat = null;
		Vector coll = new Vector();
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			strSQL = new StringBuffer();
			strSQL.append(" select c.*  from sett_transferloanamount c \n");
			strSQL.append(" where c.statusid = " + SETTConstant.TransactionStatus.CHECK);
			strSQL.append(" and c.transfercontractid = 561 ");
			strSQL.append(" order by c.intereststart \n");
        	prepStat = conInternal.prepareStatement(strSQL.toString());
        	rstat = prepStat.executeQuery();
        	while(rstat!= null && rstat.next())
	        {
	        	if(rstat.getLong("TRANSACTIONTYPEID")==SETTConstant.TransactionType.TRANSFERREPAY)
	        	{
	        		skSQL = new StringBuffer();
	        		NoticeAndAgentDetialResultInfo skInfo = new NoticeAndAgentDetialResultInfo();
	        		skSQL.append("  select a.* from sett_transferloandetail a \n");
	        		skSQL.append("  where a.statusid = " + SETTConstant.TransactionStatus.CHECK);
	        		skSQL.append("  and a.transferamountid = "+ rstat.getLong("ID"));
	        		//skSQL.append("  and a.noticeformid = "+ qInfo.getLoanPayNoticeID());
	        		skps = conInternal.prepareStatement(skSQL.toString());
	        		skrs = skps.executeQuery();
	        		if(skrs!=null && skrs.next())
	        		{
	        		   skInfo.setBalance(skrs.getDouble("amount"));
	        		   skInfo.setTransTypeID(SETTConstant.TransactionType.TRANSFERREPAY);
	        		   skInfo.setInterestStart(skrs.getTimestamp("INTRESTSTART"));
	        		   coll.add(skInfo);
	        		}
	        		else
	        		{}
	        	}
	        	else if (rstat.getLong("TRANSACTIONTYPEID")==SETTConstant.TransactionType.AGENTTRANSFERREPAY)
	        	{
	        		fkSQL = new StringBuffer();
	        		NoticeAndAgentDetialResultInfo fkInfo = new NoticeAndAgentDetialResultInfo();
	        		fkSQL.append("  select b.* from sett_transferagentamount b \n");
	        		fkSQL.append("  where b.statusid = " + SETTConstant.TransactionStatus.CHECK);
	        		fkSQL.append("  and b.transferloanamountid = "+ rstat.getLong("ID"));
	        		//fkSQL.append("  and b.loanpaynoticeid = "+ qInfo.getLoanPayNoticeID());
	        		fkps = conInternal.prepareStatement(fkSQL.toString());
	        		fkrs = fkps.executeQuery();
	        		if(fkrs!=null && fkrs.next())
	        		{
	        		   fkInfo.setBalance(fkrs.getDouble("amount"));
	        		   fkInfo.setTransTypeID(SETTConstant.TransactionType.AGENTTRANSFERREPAY);
	        		   fkInfo.setInterestStart(fkrs.getTimestamp("INTERESTSTART"));
	        		   coll.add(fkInfo);
	        		}	
	        	}

	        }
        	
	        System.out.println("查询转让合同的结算交易记录"+strSQL.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(skps!=null)
	    	{
	    		skps.close();
	    		skps=null;
	    	}
	    	if(skrs!=null)
	    	{
	    		skrs.close();
	    		skrs=null;
	    	}
	    	if(fkps!=null)
	    	{
	    		fkps.close();
	    		fkps=null;
	    	}
	    	if(fkrs!=null)
	    	{
	    		fkrs.close();
	    		fkrs=null;
	    	}
			if(con == null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return coll;
	}
	/**
	 * 查询卖出卖断涉及到的单笔通知单
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public Collection queryLoanNoteDetail(Connection con,TransferContractInfo qInfo) throws Exception
	{
		Connection conInternal = null;
		StringBuffer strSQL = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector coll = new Vector();
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			strSQL = new StringBuffer();
			strSQL.append(" select b.noticeformid from sett_transferloanamount a ,sett_transferloandetail b \n");
			strSQL.append(" where b.statusid = " + CRAconstant.TransactionStatus.SAVE);
			strSQL.append(" and a.statusid = " + SETTConstant.TransactionStatus.CHECK);
			strSQL.append(" and a.id = b.transferamountid ");
			strSQL.append(" and a.transfercontractid = "+qInfo.getId());
			strSQL.append(" and a.officeid = "+qInfo.getOfficeId());
			strSQL.append(" and a.currencyid = "+qInfo.getCurrencyId());
			strSQL.append(" group by b.noticeformid " );
        	ps = conInternal.prepareStatement(strSQL.toString());
        	rs = ps.executeQuery();
        	while(rs!= null && rs.next())
	        {
        		TransferLoanAmountDetailinfo noticeInfo = new 	TransferLoanAmountDetailinfo(); 		
                //noticeInfo.setAmount(rs.getDouble("amount"));
                //noticeInfo.setContractid(rs.getLong("contractid"));
                //noticeInfo.setLoanaccountid(rs.getLong("loanaccountid"));
                noticeInfo.setNoticeformid(rs.getLong("NOTICEFORMID"));
                //noticeInfo.setIntereststart(rs.getTimestamp("INTERESTSTART"));
	            coll.add(noticeInfo);	        
	        }
        	
	        System.out.println("查询转让合同对应的贷款合同放款通知单的结算交易记录"+strSQL.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	    finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}
	    	if(con ==null)
			{
			   cleanup(conInternal);
			}
	    	// finalizeDAO();
        }
		return coll;
	}
	/**
	 * 查询卖出卖断单笔通知单收付款交易期初余额
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public double queryBreakNoticeBalance1(Connection con,TransferLoanAmountDetailinfo qInfo) throws Exception
	{
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rset = null;		
		Vector coll = new Vector();
    	TransferLoanAmountDetailinfo info = new TransferLoanAmountDetailinfo();
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append("  select sum( h.amount-f.amount) balance from sett_transferloandetail h , \n");	 
	        sql.append(" (select m.* ,n.transfercontractid from sett_transferagentamount m, sett_transferloanamount n  \n " );
            sql.append("  where m.transferloanamountid = n.id  \n");
            sql.append("  and n.transfercontractid = "+ qInfo.getCracontractid());
            sql.append("  and m.loanpaynoticeid = "+qInfo.getNoticeformid());
            sql.append("  and m.statusid = " + SETTConstant.TransactionStatus.CHECK);
            sql.append("  and n.statusid = " + SETTConstant.TransactionStatus.CHECK);
            sql.append("  and n.intereststart <= to_date(' \n"
					+ DataFormat.formatDate(qInfo.getPerinterestsettlement())
					+ "','yyyy-mm-dd') ) f \n");
	        sql.append("  where  h.contractid = f.loancontractid \n" );
	        sql.append("  and h.noticeformid = f.loanpaynoticeid \n" );
	        sql.append("  and h.statusid = " + SETTConstant.TransactionStatus.CHECK);
	        sql.append("  and f.transfercontractid = "+ qInfo.getCracontractid());  
	        sql.append("  and h.noticeformid = "+ qInfo.getNoticeformid()); 
	        sql.append("  and h.intereststart <= to_date(' "
					+ DataFormat.formatDate(qInfo.getPerinterestsettlement())
					+ "','yyyy-mm-dd') ");
	        sql.append(" group by h.noticeformid  ");
	        ps = conInternal.prepareStatement(sql.toString());
	        rset = ps.executeQuery();
	        if(rset!= null && rset.next())
	        {
                info.setAmount(rset.getDouble("balance"));
	        }
	       
	        System.out.println("查询卖出卖断明细收付款交易期初余额"+sql.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		 finally{
		    	if(ps!=null)
		    	{
		    		ps.close();
		    		ps=null;
		    	}
		    	if(rset!=null)
		    	{
		    		rset.close();
		    		rset=null;
		    	}
		    	if(con ==null)
				{
				   cleanup(conInternal);
				}
		    	// finalizeDAO();
	        }
		return info.getAmount()>0.00?info.getAmount():0.00;
	}
	/**
	 * 查询卖出卖断单笔通知单收付款交易期初余额
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public double queryBreakNoticeBalance(Connection con,TransferLoanAmountDetailinfo qInfo) throws Exception
	{
		double skamount = 0.0;
		double fkamount = 0.0;
		double startBalance = 0.0;
		Connection conInternal = null;
		StringBuffer sql = null;
		StringBuffer sqlBuffer = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement preps = null;
		ResultSet resrs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append("  select nvl(sum(a.amount),0) skamount from sett_transferloandetail a, sett_transferloanamount b  \n");
	        sql.append(" where a.statusid =  1 ") ;
	        sql.append(" and b.statusid = "+ SETTConstant.TransactionStatus.CHECK);
	        sql.append(" and b.id = a.transferamountid");
	        sql.append(" and b.transfercontractid = "+qInfo.getCracontractid());
	        sql.append(" and a.noticeformid = "+qInfo.getNoticeformid());
	        sql.append(" and a.intereststart < to_date('"
						+ DataFormat.formatDate(qInfo.getPerinterestsettlement())
						+ "','yyyy-mm-dd')");							
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())
	        {
	        	skamount = rs.getDouble("skamount");
	        }
	       
	        sqlBuffer = new StringBuffer();
	        sqlBuffer.append(" select nvl(sum(b.amount),0) fkamount from sett_transferagentamount a,sett_transferloanamount b \n");
	        sqlBuffer.append(" where b.statusid = " + SETTConstant.TransactionStatus.CHECK );
	        sqlBuffer.append(" and a.statusid = 1 ");
	        sqlBuffer.append(" and b.id = a.LOANPAYNOTICEID ");
	        sqlBuffer.append(" and b.transfercontractid = " + qInfo.getCracontractid());
	        sqlBuffer.append(" and a.LOANPAYNOTICEID = " + qInfo.getNoticeformid() );	
	        sqlBuffer.append(" and b.intereststart < to_date('"
					+ DataFormat.formatDate(qInfo.getPerinterestsettlement())
					+ "','yyyy-mm-dd')");	
	        preps = conInternal.prepareStatement(sqlBuffer.toString());
	        resrs = preps.executeQuery();
	        if(resrs!=null && resrs.next())
	        {
	        	fkamount = resrs.getDouble("fkamount");
	        }
	        startBalance = skamount - fkamount;
	        System.out.println("查询转让合同的初始余额"+sql.toString());
	        System.out.println("查询转让合同的初始余额"+sqlBuffer.toString());
	        System.out.println("查询转让合同的初始余额值"+startBalance);
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	    finally{
	    	if(ps != null)
	    	{
	    		ps.close();
	    		ps = null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs = null;
	    	}
	    	if(preps!=null)
	    	{
	    		preps.close();
	    		preps = null;
	    	}
	    	if(resrs!=null)
	    	{
	    		resrs.close();
	    		resrs = null;
	    	}
	    	if(con ==null)
			{
			   cleanup(conInternal);
			}
	    	 finalizeDAO();
        }
		return startBalance;
	}
	/**
	 * 查询卖出卖断单笔通知单收付款交易
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public Collection queryBreakTransDetailInfo(Connection con,TransferLoanAmountDetailinfo qInfo) throws Exception
	{
		Connection conInternal = null;

		StringBuffer strSQL = null;	
		StringBuffer skSQL = null;
		PreparedStatement skps = null;
		ResultSet skrs = null;		
		StringBuffer fkSQL = null;
		PreparedStatement fkps = null;
		ResultSet fkrs = null;		
		PreparedStatement prepStat = null;
		ResultSet rstat = null;
		Vector coll = new Vector();
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			strSQL = new StringBuffer();
			strSQL.append(" select c.*  from sett_transferloanamount c \n");
			strSQL.append(" where c.statusid = " + SETTConstant.TransactionStatus.CHECK);
			strSQL.append(" and c.transfercontractid =  " +qInfo.getCracontractid());
			strSQL.append(" and c.intereststart >= to_date(' "
						+ DataFormat.formatDate(qInfo.getPerinterestsettlement())
						+ "','yyyy-mm-dd') ");
			strSQL.append(" and c.intereststart < to_date(' "
					+ DataFormat.formatDate(qInfo.getInterestsettlement())
					+ "','yyyy-mm-dd') ");
			strSQL.append(" and c.officeid =  " +qInfo.getOfficeid());
			strSQL.append(" and c.currencyid =  " +qInfo.getCurrencyid());
			strSQL.append(" order by c.intereststart \n");
        	prepStat = conInternal.prepareStatement(strSQL.toString());
        	rstat = prepStat.executeQuery();
        	while(rstat!= null && rstat.next())
	        {
	        	if(rstat.getLong("TRANSACTIONTYPEID")==SETTConstant.TransactionType.TRANSFERREPAY)
	        	{
	        		skSQL = new StringBuffer();
	        		TransferLoanAmountDetailinfo skInfo = new TransferLoanAmountDetailinfo();
	        		skSQL.append("  select a.*,m.scontractcode,n.scode,m.nborrowclientid from sett_transferloandetail a ,loan_contractform m ,loan_payform n \n");
	        		skSQL.append("  where a.statusid = " + CRAconstant.TransactionStatus.SAVE);
	        		skSQL.append("  and a.transferamountid = "+ rstat.getLong("ID"));
	        		skSQL.append("  and a.noticeformid = "+ qInfo.getNoticeformid());
	        		skSQL.append("  and m.id = a.CONTRACTID and a.noticeformid = n.id and m.id = n.ncontractid ");	        		
	        		skSQL.append("  and a.intereststart >= to_date(' "
							+ DataFormat.formatDate(qInfo.getPerinterestsettlement())
							+ "','yyyy-mm-dd') ");
	        		skSQL.append("  and a.intereststart < to_date(' "
							+ DataFormat.formatDate(qInfo.getInterestsettlement())
							+ "','yyyy-mm-dd') ");
	        		skps = conInternal.prepareStatement(skSQL.toString());
	        		skrs = skps.executeQuery();
	        		if(skrs!=null && skrs.next())
	        		{
	        		   skInfo.setAmount(skrs.getDouble("amount"));
	        		   skInfo.setNoticeformid(skrs.getLong("NOTICEFORMID"));
	        		   skInfo.setTranstypeid(SETTConstant.TransactionType.TRANSFERREPAY);
	        		   skInfo.setIntereststart(skrs.getTimestamp("intereststart"));
	        		   skInfo.setContractcode(skrs.getString("scontractcode"));
	        		   skInfo.setPaycode(skrs.getString("scode"));
	        		   skInfo.setBorrowClientID(skrs.getLong("nborrowclientid"));
	        		   coll.add(skInfo);
	        		}
	        		else
	        		{}
	        	}
	        	else if (rstat.getLong("TRANSACTIONTYPEID")==SETTConstant.TransactionType.AGENTTRANSFERREPAY)
	        	{
	        		fkSQL = new StringBuffer();
	        		TransferLoanAmountDetailinfo fkInfo = new TransferLoanAmountDetailinfo();
	        		fkSQL.append("  select b.*,m.scontractcode,n.scode , m.nborrowclientid  from sett_transferagentamount b ,loan_contractform m ,loan_payform n \n");
	        		fkSQL.append("  where b.statusid = " + CRAconstant.TransactionStatus.SAVE);
	        		fkSQL.append("  and b.transferloanamountid = "+ rstat.getLong("ID"));
	        		fkSQL.append("  and b.loanpaynoticeid = "+ qInfo.getNoticeformid());
	        		fkSQL.append("  and m.id = b.loancontractid and b.loanpaynoticeid = n.id and m.id = n.ncontractid ");
	        		fkSQL.append("  and b.intereststart >= to_date(' "
							+ DataFormat.formatDate(qInfo.getPerinterestsettlement())
							+ "','yyyy-mm-dd') ");
	        		fkSQL.append("  and b.intereststart < to_date(' "
							+ DataFormat.formatDate(qInfo.getInterestsettlement())
							+ "','yyyy-mm-dd') ");
	        		fkps = conInternal.prepareStatement(fkSQL.toString());
	        		fkrs = fkps.executeQuery();
	        		if(fkrs!=null && fkrs.next())
	        		{
	        		   fkInfo.setAmount(fkrs.getDouble("amount"));
	        		   fkInfo.setNoticeformid(fkrs.getLong("LOANPAYNOTICEID"));
	        		   fkInfo.setTranstypeid(SETTConstant.TransactionType.AGENTTRANSFERREPAY);
	        		   fkInfo.setIntereststart(fkrs.getTimestamp("intereststart"));
	        		   fkInfo.setContractcode(fkrs.getString("scontractcode"));
	        		   fkInfo.setPaycode(fkrs.getString("scode"));
	        		   fkInfo.setBorrowClientID(fkrs.getLong("nborrowclientid"));
	        		   coll.add(fkInfo);
	        		}	
	        	}

	        }
        	
	        //System.out.println("查询卖出卖断单笔通知单收收款交易"+skSQL.toString());
	        //System.out.println("查询卖出卖断单笔通知单收付款交易"+fkSQL.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	    finally{
	    	if(fkps!=null)
	    	{
	    		fkps.close();
	    	    fkps = null;
	    	}
	    	if(fkrs!=null)
	    	{
	    		fkrs.close();
			    fkrs = null;	
	    	}
	    	if(prepStat!=null)
	    	{
	    		prepStat.close();
			    prepStat = null;
	    	}
	    	if(rstat!=null)
	    	{
	    		rstat.close();
			    rstat = null;
	    	}
	    	if(con == null)
			{
			   cleanup(conInternal);
			}
	    	// finalizeDAO();
        }
		return coll;
	}
	/**
	 * 查询转让合同对应每笔贷款合同明细ID
	 * @author xwhe
	 * @param contractID
	 * @return Timestamp
	 * @throws IException
	 */
	public long queryContractDetailID(Connection con,TransferLoanAmountDetailinfo qInfo) throws Exception
	{
		long ID = -1;
		Connection conInternal = null;
		StringBuffer sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		try{
			
			sql = new StringBuffer();
	        sql.append(" select t.* from cra_contractdetail t \n");
	        sql.append(" where t.statusid = " + Constant.RecordStatus.VALID);
	        sql.append(" and t.transfercontractformid = "+ qInfo.getCracontractid());
	        sql.append(" and t.loannoteid = "+ qInfo.getNoticeformid());
	        sql.append(" and t.officeid = "+ qInfo.getOfficeid());
	        sql.append(" and t.currencyid = "+ qInfo.getCurrencyid());
	        ps = conInternal.prepareStatement(sql.toString());
	        rs = ps.executeQuery();
	        if(rs!= null && rs.next())
	        {
	        	ID = rs.getLong("ID");
	        }
	        
			
	        System.out.println("查询转让合同对应每笔贷款合同明细ID"+sql.toString());
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }
		finally{
	    	if(ps!=null)
	    	{
	    		ps.close();
	    		ps=null;
	    	}
	    	if(rs!=null)
	    	{
	    		rs.close();
	    		rs=null;
	    	}	    	
			if(con == null)
			{
			   cleanup(conInternal);
			}
            //finalizeDAO();
        }
		return ID;
	}
}
