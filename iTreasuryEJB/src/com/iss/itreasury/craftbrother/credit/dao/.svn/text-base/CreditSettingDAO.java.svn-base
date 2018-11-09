package com.iss.itreasury.craftbrother.credit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.craftbrother.credit.dataentity.CreditQueryInfo;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditSettingInfo;
import com.iss.itreasury.craftbrother.util.CRANameRef;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentContractDao;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.securities.util.NameRef;

public class CreditSettingDAO extends SettlementDAO{
	
	public CreditSettingDAO()
	{
		super("CRA_CREDITLIMIT");
	}
	public CreditSettingDAO(Connection conn)
	{
		super("CRA_CREDITLIMIT",conn);
	}
	
	private Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
/**zcq于2006-12-19日新增
 * 保存授信额度设置
 * @param creditInfo
 * @return
 * @throws Exception
 */
	public long saveCredit(CreditSettingInfo creditInfo) throws Exception
	{
		long lMaxID=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="";
		try
		{
			conn=Database.getConnection();
//			boolean validateOverlap=findCreditByDate(creditInfo);
//			if(validateOverlap==true)
//			{
//				throw new IException("Cra_E001");
//			}
			/*获取当前数据库的最大ID+1*/
			sql="select nvl(max(id)+1,1) lID from CRA_CREDITLIMIT";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				lMaxID=rs.getLong("lID");
			}
			cleanup(rs);
			cleanup(ps);
		    sql="insert into CRA_CREDITLIMIT(id,creditclientid,creditedclientid,creditdirection,creditamount,statusid,";
			sql+=" STARTDATE,ENDDATE,TRANSACTIONTYPE,INPUTUSERID,INPUTDATE,REMARK,CURRENCYID,OFFICEID,attachid) values(?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?)";
			System.out.println(sql);
		    ps=conn.prepareStatement(sql);
		    int index=1;
		    ps.setLong(index++,lMaxID);
		    ps.setLong(index++,creditInfo.getCreditClientID());
		    ps.setLong(index++,creditInfo.getCreditedClientID());
		    ps.setLong(index++,creditInfo.getCreditDirection());
		    ps.setDouble(index++,creditInfo.getAmount());
		    ps.setLong(index++,CRAconstant.TransactionStatus.SAVE);
		    ps.setTimestamp(index++,creditInfo.getStartDate());
		    ps.setTimestamp(index++,creditInfo.getEndDate());
		    ps.setLong(index++,creditInfo.getTransactionType());
		    ps.setLong(index++,creditInfo.getInputUserID());
		    ps.setString(index++,creditInfo.getRemark());
		    ps.setLong(index++,creditInfo.getCurrencyID());
		    ps.setLong(index++,creditInfo.getOfficeID());
		    ps.setLong(index++,creditInfo.getAttachId());
		    ps.executeUpdate();
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lMaxID;
	}
	public long updateCredit(CreditSettingInfo creditInfo) throws Exception
	{
		long lReturn=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="";
		try
		{
			conn=Database.getConnection();
//			boolean validateOverlap=findCreditByDate(creditInfo);
//			if(validateOverlap==true)
//			{
//				throw new IException("Cra_E001");
//			}
			int index=1;
			sql = "update cra_creditlimit set CREDITAMOUNT=?,STARTDATE=?,ENDDATE=?,TRANSACTIONTYPE=?,REMARK=?,attachid=? where id=?";
			ps=conn.prepareStatement(sql);
			ps.setDouble(index++,creditInfo.getAmount());
			ps.setTimestamp(index++,creditInfo.getStartDate());
			ps.setTimestamp(index++,creditInfo.getEndDate());
			ps.setLong(index++,creditInfo.getTransactionType());
			ps.setString(index++,creditInfo.getRemark());
			ps.setLong(index++,creditInfo.getAttachId());
		    ps.setLong(index++,creditInfo.getID());
		    int resultPointer = ps.executeUpdate();
			if(resultPointer>0)
			{
			   lReturn = creditInfo.getID();
			}
			cleanup(ps);
			cleanup(conn);
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
			throw e;
		}finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	public  boolean findCreditByDate(CreditSettingInfo creditInfo) throws ITreasuryDAOException, SQLException
	{
		boolean bReturn=false;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try 
		{
			conn = Database.getConnection();
			String sql="";
	        sql = "select * from cra_creditlimit where CREDITCLIENTID ="+creditInfo.getCreditClientID();
	        sql +=" and CREDITEDCLIENTID ="+creditInfo.getCreditedClientID();
	        sql +=" and TRANSACTIONTYPE ="+creditInfo.getTransactionType();
	        sql +=" and CREDITDIRECTION ="+creditInfo.getCreditDirection();
	        sql += " and statusID != "+CRAconstant.TransactionStatus.DELETED;
	        sql += " and statusID !=" + CRAconstant.TransactionStatus.REFUSED;
	        sql += " and ((STARTDATE<=To_Date('" + DataFormat.getDateString(creditInfo.getStartDate())+ "','yyyy-mm-dd')";
	        sql +=  " and ENDDATE>=To_Date('" + DataFormat.getDateString(creditInfo.getEndDate())+ "','yyyy-mm-dd'))";
	        
	        sql += " or (STARTDATE>=To_Date('" + DataFormat.getDateString(creditInfo.getStartDate())+ "','yyyy-mm-dd')";
	        sql +=  " and STARTDATE<=To_Date('" + DataFormat.getDateString(creditInfo.getEndDate())+ "','yyyy-mm-dd'))";
	        
	        sql += " or (ENDDATE>=To_Date('" + DataFormat.getDateString(creditInfo.getStartDate())+ "','yyyy-mm-dd')";
	        sql +=  " and ENDDATE<=To_Date('" + DataFormat.getDateString(creditInfo.getEndDate())+ "','yyyy-mm-dd')))";
	        if (creditInfo.getID() > 0) 
	        {
	          sql += " and id !=" + creditInfo.getID();
	        }  
	        System.out.println("检查是否已经存在某种交易某个时间段的时间设置的SQL:"+sql);
		    ps=conn.prepareStatement(sql);
		    rs=ps.executeQuery();
		    while( rs.next())
		    {
		    	bReturn = true;
		    }
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		} catch (Exception e) 
		{
	        throw new ITreasuryDAOException("根据交易类型和时间段查询授信设置时出错", e);
	    }finally
		{
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		}
		return bReturn;
	}
	
	
	/**
	 * 更新授信设置的状态，在取消设置或者审核的时候用到
	 * @param lID
	 * @param statusID
	 * @return
	 * @throws Exception
	 */
	public long updateStatus(long lID,long statusID) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="";
		long lReturn = -1;
		try
		{
			conn = Database.getConnection();
			sql =" update cra_creditlimit set STATUSID = ? where id=?";
			ps 	= conn.prepareStatement(sql);
			int index = 1;
			ps.setLong(index++,statusID);
			ps.setLong(index++,lID);
			int resultPointer = ps.executeUpdate();
			System.out.println(resultPointer);
			if(resultPointer>0)
			{
				lReturn = lID;
			}
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e){
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	public long updateAllStatus(long lID,long statusID,long checkStatus) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		//String sql="";
		long lReturn = -1;
		//long creditID= -1;
		try
		{
			CreditSettingInfo creditInfo =null;
			CreditSettingDAO creditSettingDao = new CreditSettingDAO();
			creditInfo = creditSettingDao.findCreditByID(lID);
			conn = Database.getConnection();
			String strSQL = "update cra_creditlimit";
				strSQL += " set STATUSID = ?";
				strSQL += " where CREDITCLIENTID= ? and CREDITEDCLIENTID!=?";
				strSQL += " and TRANSACTIONTYPE=? and STARTDATE=?";
				strSQL += " and ENDDATE=? and STATUSID=?";
			//String strSQL = "select id  from cra_creditlimit";
			//strSQL += " where CREDITCLIENTID= ? and CREDITEDCLIENTID!=? and TRANSACTIONTYPE=? and STARTDATE=? and ENDDATE=? and STATUSID=?";
			ps 	= conn.prepareStatement(strSQL);
			int index=1;
			ps.setLong(index++,statusID);
			ps.setLong(index++,creditInfo.getCreditClientID());
			ps.setLong(index++,creditInfo.getCreditedClientID());//限定被授信方 非财务公司
			ps.setLong(index++,creditInfo.getTransactionType());
			ps.setTimestamp(index++,creditInfo.getStartDate());
			ps.setTimestamp(index++,creditInfo.getEndDate());
			ps.setLong(index++,checkStatus);
			
			lReturn =ps.executeUpdate();
			
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e){
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	/**
	 * 根据ID查询授信额度设置情况
	 * @param lID
	 * @return
	 * @throws IException
	 * @throws SQLException
	 */
	public CreditSettingInfo findCreditByID(long lID) throws IException, SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="";
		CreditSettingInfo creditInfo=new CreditSettingInfo();
		try
		{
			conn=Database.getConnection();
			sql = "select * from CRA_CREDITLIMIT where id="+lID;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				creditInfo = new CreditSettingInfo();
				creditInfo.setID(rs.getLong("id"));
				creditInfo.setCreditClientID(rs.getLong("creditclientid"));
				creditInfo.setCreditedClientID(rs.getLong("creditedclientid"));
				creditInfo.setCreditDirection(rs.getLong("creditdirection"));
				creditInfo.setAmount(rs.getDouble("creditamount"));
				creditInfo.setStatusID(rs.getLong("statusid"));
				creditInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				creditInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				creditInfo.setTransactionType(rs.getLong("TRANSACTIONTYPE"));
				creditInfo.setInputUserID(rs.getLong("INPUTUSERID"));
				creditInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				creditInfo.setRemark(rs.getString("REMARK"));
				creditInfo.setCurrencyID(rs.getLong("CURRENCYID"));
				creditInfo.setOfficeID(rs.getLong("OFFICEID"));
				creditInfo.setAttachId(rs.getLong("attachid"));
			}
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		}catch(Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return creditInfo;
	}

	public Collection findCreditByCondition(CreditQueryInfo queryInfo) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Vector vec = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		CreditSettingInfo creditInfo = null;
		try
		{
			conn = Database.getConnection();
			sbSQL.append(" select * from CRA_CREDITLIMIT where CURRENCYID="+queryInfo.getCurrencyID()+" and OFFICEID="+queryInfo.getOfficeID());
			if(queryInfo.getTransactionType()>=0)
			{
				sbSQL.append(" and TRANSACTIONTYPE = "+queryInfo.getTransactionType());
			}
			if(queryInfo.getStartDate()!=null )
			{
				sbSQL.append(" and TRUNC(InputDate)>= To_Date('" + DataFormat.getDateString(queryInfo.getStartDate()) + "','yyyy-mm-dd')");
			}
			if(queryInfo.getEndDate()!=null)
			{
				sbSQL.append(" and TRUNC(InputDate) <=To_Date('" + DataFormat.getDateString(queryInfo.getEndDate()) + "','yyyy-mm-dd')");
			}
			if(queryInfo.getCounterParterID()>0)
			{
				sbSQL.append(" and( CREDITDIRECTION = '1' and CREDITEDCLIENTID ="+queryInfo.getCounterParterID());
				sbSQL.append(" or CREDITDIRECTION=2 and CREDITCLIENTID ="+queryInfo.getCounterParterID()+")");
			}
			if(queryInfo.getMinAmount()>0)
			{
				sbSQL.append(" and CREDITAMOUNT >= "+queryInfo.getMinAmount());
			}
			if( queryInfo.getMaxAmount()>0)
			{
				sbSQL.append(" and CREDITAMOUNT<="+queryInfo.getMaxAmount());
			}
			if( queryInfo.getStatusID()>=0)
			{
				sbSQL.append(" and STATUSID = "+queryInfo.getStatusID());
			}
			sbSQL.append(" order by id ");
			System.out.println(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while( rs.next())
			{
				creditInfo = new CreditSettingInfo();
				creditInfo.setID(rs.getLong("id"));
				creditInfo.setCreditClientID(rs.getLong("creditclientid"));
				creditInfo.setCreditedClientID(rs.getLong("creditedclientid"));
				creditInfo.setCreditDirection(rs.getLong("creditdirection"));
				creditInfo.setAmount(rs.getDouble("creditamount"));
				creditInfo.setStatusID(rs.getLong("statusid"));
				creditInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				creditInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				creditInfo.setTransactionType(rs.getLong("TRANSACTIONTYPE"));
				creditInfo.setInputUserID(rs.getLong("INPUTUSERID"));
				creditInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				creditInfo.setRemark(rs.getString("REMARK"));
				creditInfo.setCurrencyID(rs.getLong("CURRENCYID"));
				creditInfo.setOfficeID(rs.getLong("OFFICEID"));
				creditInfo.setAttachId(rs.getLong("attachid"));
				creditInfo.setTerm(DataFormat.getIntervalDays(creditInfo.getStartDate(),creditInfo.getEndDate()));
				long counterpartID=-1;
				long clientID=-1;
				if(creditInfo.getCreditDirection()==1)
				{
				   clientID = creditInfo.getCreditClientID();
				   counterpartID = creditInfo.getCreditedClientID();
				}else if(creditInfo.getCreditDirection()==2)
				{
				   clientID = creditInfo.getCreditedClientID();
				   counterpartID = creditInfo.getCreditClientID();
				}	
				String counterpartCode=com.iss.itreasury.securities.util.NameRef.getCounterpartCodeByID(counterpartID);
				String counterpartName =com.iss.itreasury.securities.util.NameRef.getCounterpartNameByID(counterpartID);
				String clientCode=com.iss.itreasury.settlement.util.NameRef.getClientCodeByID(clientID);
				String clientName=com.iss.itreasury.settlement.util.NameRef.getClientNameByID(clientID);
			    
				if(creditInfo.getCreditDirection()==1)
				{
				    creditInfo.setCreditClientCode(clientCode);
				    creditInfo.setCreditClientName(clientName);
				    creditInfo.setCreditedClientCode(counterpartCode);
				    creditInfo.setCreditedClientName(counterpartName);
				}
				else if(creditInfo.getCreditDirection()==2)
				{
				    creditInfo.setCreditClientCode(counterpartCode);
				    creditInfo.setCreditClientName(counterpartName);
				    creditInfo.setCreditedClientCode(clientCode);
				    creditInfo.setCreditedClientName(clientName);
				}
				vec.add(creditInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return vec.size()>0?vec:null;
	}
    /**
     * @验证某贷款合同转让申请是否满足交易对手对财务公司及成员单位的授信要求
     * @param info
     * @param conInfo
     * @return
     * @throws Exception 
     */
	public boolean checkUseableCreditAmount(AttornmentApplyInfo info,AttornmentContractInfo[] conInfo,long transactionType) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Vector vec = new Vector();
		String strSQL = "";
		boolean bReturn = false;
		
		long financeID = -1;//财务公司对应的客户ID
		long counterPartID = -1;//交易对手ID
		String counterPartName ="";//交易对手名称
		long clientID = -1; //客户ID
		String clientName = ""; //客户名称 
		double financeUseableCredit = 0.00;  //该同行对财务公司的可用授信额度
		double clientUseableCredit = 0.00; //该同行对某客户的可用授信额度
		double creditAmount = 0.00;//授信额度
		double usedCreditAmount = 0.00; //已使用授信额度
		Timestamp transactionStartDate = null;//交易起始时间
		Timestamp transactionEndDate = null;//交易截至时间
		Timestamp creditStartDate = null;//（交易时间区间落在某个的授信的时间区间中）授信开始时间
		Timestamp creditEndDate = null;//（交易时间区间落在某个授信的时间区间中）授信截至时间
		long lID = info.getRepurchaseApplyId();//资产转让申请ID
		CreditSettingInfo creditInfo =null;
		try
		{
			conn = Database.getConnection();
        	//根据资产转让申请ID查交易对手信息
        	strSQL = "select b.id ID,b.Name NAME,a.TRANSACTIONSTARTDATE,a.TRANSACTIONENDDATE  from sec_applyform a,SEC_Counterpart b  where a.COUNTERPARTID=b.id  and a.id ="+lID;
        	System.out.println("根据资产转让申请查交易对手信息的SQL:"+strSQL);
        	ps=conn.prepareStatement( strSQL );
        	rs = ps.executeQuery();
        	if (rs.next())
        	{
         		counterPartID = rs.getLong("ID");	
         		counterPartName = rs.getString("NAME");
         		transactionStartDate = rs.getTimestamp("TRANSACTIONSTARTDATE");
         		transactionEndDate = rs.getTimestamp("TRANSACTIONENDDATE");
        	}
           	cleanup(rs);
        	cleanup(ps);
        	//获取财务公司对应的客户ID
        	creditInfo = new CreditSettingInfo();
        	financeID = CRANameRef.getPartenerInfo(1).getClientID();
        	//查询改同行对财务公司的授信情况
        	creditInfo = findCreditAmountByDate(transactionStartDate,transactionEndDate,counterPartID,financeID,2,transactionType,counterPartName,"财务公司");
        	creditStartDate = creditInfo.getStartDate();
        	creditEndDate = creditInfo.getEndDate();
        	creditAmount = creditInfo.getAmount()*10000;//授信额度的单位是万

			//检测申请的贷款合同转让总金额是否在同行对财务公司的可用授信额度范围之内
        	System.out.println("-----------开始对财务公司进行授信检测---------------------！");
        	strSQL = "select nvl(sum(a.ATTORNMENTAMOUNT),0) usedCreditAmount"
        		+" from sec_attornmentapplyform a,sec_applyform b"
        		+" where b.TRANSACTIONTYPEID ="+transactionType
        		+" and a.REPURCHASEAPPLYID = b.id "
        		+" and( a.STATUSID ="+LOANConstant.AttornmentStatus.SUBMIT
        		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.CHECK
        		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.USED+")"
        		+" and b.COUNTERPARTID ="+counterPartID
          		+" and b.TRANSACTIONSTARTDATE>= To_Date('" + DataFormat.getDateString(creditStartDate) + "','yyyy-mm-dd')";
        	if(transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
        	{
        		strSQL += " and b.TRANSACTIONSTARTDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
        	}else
        	{
        		strSQL += " and b.TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
        	}
        	if(info.getId()>0)
        	{
        		//如果ID>0(即Update时)则累积已使用授信额度的时候，应该去掉改比贷款合同转让申请
        		strSQL+=" and b.id!="+info.getId();
        	}
        	System.out.println("根据资产转让申请id查贷款合同转让申请ID的SQL:"+strSQL);
        	ps=conn.prepareStatement( strSQL );
        	rs=ps.executeQuery() ;
        	if (rs.next())
        	{
        		usedCreditAmount = rs.getDouble("usedCreditAmount");
        	}
        	cleanup(rs);
        	cleanup(ps);
        	
        	financeUseableCredit = creditAmount-usedCreditAmount;
        	if(info.getAttornmentAmount()>financeUseableCredit)
        	{
        		String exceptionMess= " 该贷款合同转让申请的转让金额"+DataFormat.formatDisabledAmount(info.getAttornmentAmount())+"元，超过\""
        			   + counterPartName+"\"对财务公司关于"+CRAconstant.TransactionType.getName(transactionType)+"交易类型授信的可用额度，目前财务公司的可用授信额度为"+DataFormat.formatDisabledAmount(financeUseableCredit,2)+"元"
        		       +" 请修改后再提交";
        		throw new IException(exceptionMess);	
        	}
        	System.out.println("-----------对财务公司授信检测结束---------------------！");

        	
        	// 根据合同对相应的成员单位进行授信检查
        	System.out.println("------------------开始根据合同对相应的成员单位进行授信检查---------------------------");
        	for (int i=0;i<conInfo.length ;i++)
        	{	
        		AttornmentContractDao dao=new AttornmentContractDao();
    		    AttornmentApplyDao applyDao = new AttornmentApplyDao();
    		    ContractDao conDao = new ContractDao();
    		    String contractCode ="";//合同编号，用于提醒信息中
    		    long attContractID =-1;//贷款合同转让申请－合同的ID
    		    attContractID = conInfo[i].getId();//贷款合同转让申请－合同的ID
        		if (attContractID>0)
				{
					AttornmentContractInfo attContractInfo=(AttornmentContractInfo)dao.findByID( attContractID,AttornmentContractInfo.class );
					clientID = attContractInfo.getClientId();
					contractCode = attContractInfo.getContractCode();
				}	
        		else
        		{
        			//并根据合同ID找出对应的客户ID
					ContractInfo cInfo = conDao.findByID(conInfo[i].getContractId());
					clientID = cInfo.getBorrowClientID();
					contractCode = cInfo.getContractCode();
        		}
        		System.out.println("------------------------------clientID:"+clientID);
				clientName =NameRef.getClientNameByID(clientID);//获取客户名称，在异常信息中使用
        		//查询该交易对手对合同对应的成员单位的授信情况
            	creditInfo = findCreditAmountByDate(transactionStartDate,transactionEndDate,counterPartID,clientID,2,transactionType,counterPartName,clientName);
            	creditStartDate = creditInfo.getStartDate();
            	creditEndDate = creditInfo.getEndDate();
            	creditAmount = creditInfo.getAmount()*10000;//授信额度的单位是万
				
            	strSQL = "select nvl(sum(a.ATTORNMENTAMOUNT),0) usedCreditAmount"
            		+" from sec_attornmentapplyform a,sec_applyform b,SEC_AttornmentContract c"
            		+" where b.TRANSACTIONTYPEID ="+transactionType
            		+" and a.REPURCHASEAPPLYID = b.id and c.ATTORNMENTAPPLYID=a.id"
            		+" and( a.STATUSID ="+LOANConstant.AttornmentStatus.SUBMIT
            		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.CHECK
            		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.USED+")"
            		+" and b.COUNTERPARTID ="+counterPartID
            	    +" and c.CLIENTID="+clientID
            		+" and b.TRANSACTIONSTARTDATE>= To_Date('" + DataFormat.getDateString(creditStartDate) + "','yyyy-mm-dd')";
            	if(transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
            	{
            		strSQL += " and b.TRANSACTIONSTARTDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
            	}else
            	{
            		strSQL += " and b.TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
            	}
            	if(attContractID>0)
            	{
            		strSQL+=" and c.id!="+attContractID;
            	}
            	System.out.println("检验成员单位"+clientName+"授信额度的SQL:"+strSQL);
             	ps=conn.prepareStatement( strSQL );
            	rs=ps.executeQuery() ;
            	if (rs.next())
            	{
            		usedCreditAmount = rs.getDouble("usedCreditAmount");
            	}
            	cleanup(rs);
            	cleanup(ps);
            	
            	clientUseableCredit = creditAmount-usedCreditAmount;
            	if(conInfo[i].getAttornmentAmount()>clientUseableCredit)
            	{
            		String exceptionMess= " 该贷款合同转让申请中的编号为"+contractCode+"的合同的转让金额"+DataFormat.formatDisabledAmount(info.getAttornmentAmount())+"元，超过\""
            			   + counterPartName+"\"对该合同的借款客户\""+clientName+"\"对于"+CRAconstant.TransactionType.getName(transactionType)+"交易类型授信的可用额度，目前客户\""+clientName+"\"的可用授信额度为"+DataFormat.formatDisabledAmount(clientUseableCredit,2)+"元"
            		       +" 请修改后再提交";
            		throw new IException(exceptionMess);	
            	}
        	}
        	System.out.println("-----------对成员单位授信检查结束---------------------！");
        	bReturn = true;
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
			throw e;
		}finally
		{
		  	cleanup(rs);
        	cleanup(ps);
			cleanup(conn);
		}
		return bReturn;
	}
	/** 
	 *  根据时间，交易对手ID，客户ID查询改交易对手对改客户的授信额度
	 * @param startDate  交易开始时间
	 * @param endDate    交易截至时间
	 * @param clientID   客户ID（内部单位）
	 * @param counterpartID  交易对手编号
	 * @return amountReturn 返回授信额度
	 * @throws Exception 
	 */
	public CreditSettingInfo findCreditAmountByDate(Timestamp startDate,Timestamp endDate,long counterpartID,long clientID,long creditDirection,long transactionType,String counterPartName,String clientName)
	     throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CreditSettingInfo returnInfo =null;
		try
		{ 
			conn = Database.getConnection();
			String strSQL = "select ID,CREDITAMOUNT creditAmount,STARTDATE,ENDDATE from cra_creditlimit ";
			if(creditDirection==1)
			{
				strSQL += " where CREDITEDCLIENTID ="+counterpartID;
			}
			else if(creditDirection==2)
			{
				strSQL +="where CREDITCLIENTID ="+counterpartID+" and CREDITEDCLIENTID ="+clientID;
			}
			strSQL +=" and STARTDATE<=To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')";
			//因为合同卖出（买断）不存在结束日期一说，所以只能通过开始日期来确定授信的时间区间
//			if(transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY ||transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY)
//			{
//				strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')";
//			}else
//			{
//			    strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(endDate) + "','yyyy-mm-dd')";
//			}
			
			//modified by qhzhou 2009-04-16 只通过开始日期来确定授信的时间区间
			strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')";
			
			strSQL += " and TRANSACTIONTYPE ="+transactionType;
			strSQL += " and STATUSID in("+CRAconstant.TransactionStatus.APPROVALED+","+CRAconstant.TransactionStatus.USED+")";
			//added by qhzhou 2009-04-03取结束日期最远的授信区间
			strSQL += " order by ENDDATE desc";
			
			System.out.println("根据时间区间和交易对手查询授信情况的SQL:"+strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if( rs.next())
			{ 
				returnInfo = new CreditSettingInfo();
				returnInfo.setAmount( rs.getDouble("creditAmount"));
				returnInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				returnInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				returnInfo.setID(rs.getLong("ID"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			if(returnInfo==null)
			{
				String exceptionMess ="";
				if(creditDirection==1)
				{
					 exceptionMess= "对不起！没有找到财务公司对\""+counterPartName+"\"关于"+CRAconstant.TransactionType.getName(transactionType)+
			         "交易类型的并且与交易时间段相符合的授信额度设置记录，请到同业往来模块添加授信信息";
				}
			    else if(creditDirection==2)
			    {	
				 exceptionMess= "对不起！没有找到"+counterPartName+"对"+clientName+"关于"+CRAconstant.TransactionType.getName(transactionType)+
				         "交易类型的并且与交易时间段相符合的授信额度设置记录，请到同业往来模块添加授信信息";
			    }		
				throw new IException(exceptionMess);	
			}
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
		}finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return returnInfo;
	}

	public String checkRepurchaseCredit(long creditID) throws SQLException, IException
	{
		String sReturn="";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CreditApprovalDAO creditApprovalDao = null;
		CreditSettingDAO creditSettingDao = null;
		CreditSettingInfo creditInfo =null;
		double clientCreditTotal = 0.00;//该交易对手对所有成员单位关于此种交易类型的授信额度总和
		try
		{
			conn = Database.getConnection();
			creditSettingDao = new CreditSettingDAO();
			creditInfo = creditSettingDao.findCreditByID(creditID);
			String strSQL = "select  nvl(sum(CREDITAMOUNT),0) clientCreditTotal from cra_creditlimit";
			strSQL += " where CREDITCLIENTID= ? and CREDITEDCLIENTID!=? and TRANSACTIONTYPE=? and STARTDATE=? and ENDDATE=? and STATUSID=1";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			int index=1;
			ps.setLong(index++,creditInfo.getCreditClientID());
			ps.setLong(index++,creditInfo.getCreditedClientID());//限定被授信方 非财务公司
			ps.setLong(index++,creditInfo.getTransactionType());
			ps.setTimestamp(index++,creditInfo.getStartDate());
			ps.setTimestamp(index++,creditInfo.getEndDate());
			rs = ps.executeQuery();
			if(rs.next())
			{
				clientCreditTotal = rs.getDouble("clientCreditTotal");
			}
			//此处严格上来说会出现问题，几个double类型的值求和由于二进制存储及运算的的关系有可能得出的结果有细微的偏差，
			//导致本该相等的，结果不相等，所以如果需要的话，应该进行一些特殊处理
			if(creditInfo.getAmount()!=clientCreditTotal)
			{
				sReturn = "\\\""+NameRef.getCounterpartNameByID(creditInfo.getCreditClientID())
				+"\"关于"+CRAconstant.TransactionType.getName(creditInfo.getTransactionType())
				+"交易类型，对财务公司授信的授信额度"+"应该等于其在相同授信区间内对所有成员单位的授信额度的总和";
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return sReturn;
	}
}
