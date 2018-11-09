package com.iss.itreasury.craftbrother.credit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.craftbrother.credit.dataentity.CreditQueryInfo;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditSettingInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.system.approval.dao.ApprovalDao;
import com.iss.itreasury.system.approval.dao.ApprovalSettingDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalUserInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

public class CreditApprovalDAO extends OBBaseDao{
	private Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	
	public Collection  findApprovalCreditByCondition(CreditQueryInfo info,long userID) throws IException, SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Vector vec = new Vector();
		String sql="";
		CreditSettingInfo creditInfo = null;
		try{
			conn = Database.getConnection();
			sql="(SELECT c.*,-1 moneysegment,-1 approvalid from CRA_CREDITLIMIT c";
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a";
			sql+=" , (select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing where NMODULEID="+Constant.ModuleType.CRAFTBROTHER;
			sql+=" and NLOANTYPEID="+info.getSubLoanTypeID()+" and NACTIONID = "+info.getTransactionType()+ " group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" and a.NLOANTYPEID="+info.getSubLoanTypeID();
			sql+="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+CRAconstant.TransactionStatus.APPROVALING+"";
			if(info.getTransactionType()>=0)
			{
				sql+=" and c.TRANSACTIONTYPE = "+info.getTransactionType();
			}
			if(info.getStartDate()!=null )
			{
				sql+=" and c.TRUNC(InputDate)>= To_Date('" + DataFormat.getDateString(info.getStartDate()) + "','yyyy-mm-dd')";
			}
			if(info.getEndDate()!=null)
			{
				sql+=" and c.TRUNC(InputDate) <=To_Date('" + DataFormat.getDateString(info.getEndDate()) + "','yyyy-mm-dd')";
			}
			if(info.getCounterParterID()>0)
			{
				sql+=" and( c.CREDITDIRECTION = '1' and c.CREDITEDCLIENTID ="+info.getCounterParterID();
				sql+=" or c.CREDITDIRECTION=2 and c.CREDITCLIENTID ="+info.getCounterParterID()+")";
			}
			if(info.getMinAmount()>0)
			{
				sql+=" and c.CREDITAMOUNT >= "+info.getMinAmount();
			}
			if( info.getMaxAmount()>0)
			{
				sql+=" and c.CREDITAMOUNT<="+info.getMaxAmount();
			}
		    sql+=") union ( ";
			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid from CRA_CREDITLIMIT aa,loan_approvalrelation rr";
			//增加关于币种的判断-mhjin-东方电气
			sql += " where aa.transactiontype=rr.ACTIONID and rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.creditamount>rr.moneysegment and rr.currencyid =" +info.getCurrencyID()+ " and aa.statusid="+CRAconstant.TransactionStatus.SAVE;
			sql += " and rr.SUBLOANTYPEID ="+info.getSubLoanTypeID(); 
			if(info.getTransactionType()>=0)
			{
				sql+=" and aa.TRANSACTIONTYPE = "+info.getTransactionType();
			}
			if(info.getStartDate()!=null )
			{
				sql+=" and TRUNC(aa.InputDate)>= To_Date('" + DataFormat.getDateString(info.getStartDate()) + "','yyyy-mm-dd')";
			}
			if(info.getEndDate()!=null)
			{
				sql+=" and TRUNC(aa.InputDate) <=To_Date('" + DataFormat.getDateString(info.getEndDate()) + "','yyyy-mm-dd')";
			}
			if(info.getCounterParterID()>0)
			{
				sql+=" and( aa.CREDITDIRECTION = '1' and aa.CREDITEDCLIENTID ="+info.getCounterParterID();
				sql+=" or aa.CREDITDIRECTION=2 and aa.CREDITCLIENTID ="+info.getCounterParterID()+")";
			}
			if(info.getMinAmount()>0)
			{
				sql+=" and aa.CREDITAMOUNT >= "+info.getMinAmount();
			}
			if( info.getMaxAmount()>0)
			{
				sql+=" and aa.CREDITAMOUNT<="+info.getMaxAmount();
			}
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from CRA_CREDITLIMIT aa,loan_approvalrelation rr";
			//增加关于币种的判断-mhjin-东方电气
			sql += " where aa.transactiontype=rr.ACTIONID and rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.creditamount>rr.moneysegment and rr.currencyid =" +info.getCurrencyID()+ " and aa.statusid="+CRAconstant.TransactionStatus.SAVE;
			sql += " and rr.SUBLOANTYPEID ="+info.getSubLoanTypeID(); 
			if(info.getTransactionType()>=0)
			{
				sql+=" and aa.TRANSACTIONTYPE = "+info.getTransactionType();
			}
			if(info.getStartDate()!=null )
			{
				sql+=" and TRUNC(aa.InputDate)>= To_Date('" + DataFormat.getDateString(info.getStartDate()) + "','yyyy-mm-dd')";
			}
			if(info.getEndDate()!=null)
			{
				sql+=" and TRUNC(aa.InputDate) <=To_Date('" + DataFormat.getDateString(info.getEndDate()) + "','yyyy-mm-dd')";
			}
			if(info.getCounterParterID()>0)
			{
				sql+=" and( aa.CREDITDIRECTION = '1' and aa.CREDITEDCLIENTID ="+info.getCounterParterID();
				sql+=" or aa.CREDITDIRECTION=2 and aa.CREDITCLIENTID ="+info.getCounterParterID()+")";
			}
			if(info.getMinAmount()>0)
			{
				sql+=" and aa.CREDITAMOUNT >= "+info.getMinAmount();
			}
			if( info.getMaxAmount()>0)
			{
				sql+=" and aa.CREDITAMOUNT<="+info.getMaxAmount();
			}
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
			sql +=")";
			System.out.println("--授信额度设置的审批查找SQL:--"+sql);
			ps = conn.prepareStatement(sql);
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
	 * 根据审批结果来更新证卷业务记录的状态,同时处理审批记录表记录
	 * 申请业务操作
	 * @param lResultID 审批结果ID
	 * @param tranTypeId 业务类型ID
	 * @param settID 业务记录ID
	 * @return
     * @throws Exception 
	 */
	public long updateDataStatusID(ApprovalTracingInfo info) throws Exception{
		long lReturn=-1;
		String strError = "";
		try{
			long lResultID = -1;//审批结果
			long tranTypeId = -1;//交易类型
			long settID = -1; //交易ID
			long lActionID = -1;
			lResultID = info.getCheckActionID();
			System.out.println("-----------------------------------lResultID"+lResultID);
			tranTypeId = info.getLoanTypeID();
			settID = info.getApprovalContentID();
			lActionID = info.getActionID();
			long settStatusID=-1;//结算表记录状态
			long checkStatus = -1;
			System.out.println("-----------------------------------ActionType:"+info.getActionID());
			if(lActionID==CRAconstant.TransactionType.REPURCHASE_NOTIFY||lActionID==CRAconstant.TransactionType.BREAK_NOTIFY)
			{
	            //检查改交易对手在相同的时间段和授信区间内对财务公司的授信是否等于其对于所有成员单位授信额度的总和
				CreditApprovalDAO creditdao=new CreditApprovalDAO();
				strError = creditdao.checkRepurchaseCredit(settID);
				if (strError != null && strError.length() > 0)
				{
					throw new IException(strError);	
				}
			}	
			//先处理审批记录表内容
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//无论是拒绝\返回修改\审批通过\审核完成都新增一条记录
			lReturn = appbiz.saveApprovalTracing(info); //保存审批信息
			
			//审核拒绝,逻辑删除本条记录的所有的审核记录
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN)
			{				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),info.getLoanTypeID(),info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			
			//根据审批结果来判断结算表记录状态
			
			if(lResultID==Constant.ApprovalDecision.PASS){//审核通过,状态为审批中
				settStatusID=CRAconstant.TransactionStatus.APPROVALING;
				checkStatus=CRAconstant.TransactionStatus.SAVE;
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//审批拒绝，状态为已拒绝
				settStatusID=CRAconstant.TransactionStatus.REFUSED;		
				checkStatus=CRAconstant.TransactionStatus.SAVE;
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//审批返回，状态为已提交
				settStatusID=CRAconstant.TransactionStatus.SAVE;
				checkStatus=CRAconstant.TransactionStatus.APPROVALING;
			}else if(lResultID==Constant.ApprovalDecision.FINISH){//审批完成，状态为审批完成
				settStatusID=CRAconstant.TransactionStatus.APPROVALED;
				checkStatus=CRAconstant.TransactionStatus.SAVE;
			}
			//根据业务类型和审批结果来更新结算表记录状态信息
			System.out.println("^^^^^^^^^^^^^^^^"+tranTypeId);
			if( tranTypeId > 0 )
			{
				//Sett_TransCurrentDepositDAO bankdao=new Sett_TransCurrentDepositDAO();		
				CreditSettingDAO dao = new CreditSettingDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE)
				{
					if(lActionID==CRAconstant.TransactionType.REPURCHASE_NOTIFY||lActionID==CRAconstant.TransactionType.BREAK_NOTIFY)
					{
						// 先更新对成员单位授信的授信状态
						System.out.println("--------------------------test for debug:updateDataStatusID-------------------------");
						dao.updateAllStatus(settID,settStatusID,checkStatus);
					}	
                    // 修改原记录的状态
					lReturn=dao.updateStatus(settID,settStatusID);
					System.out.println(settID+"^^^^^^^删除^^^^^^^"+settStatusID);
				}
				else
				{
					if(lActionID==CRAconstant.TransactionType.REPURCHASE_NOTIFY||lActionID==CRAconstant.TransactionType.BREAK_NOTIFY)
					{
						// 先更新对成员单位授信的授信状态
						System.out.println("--------------------------test for debug:updateDataStatusID-------------------------");
						dao.updateAllStatus(settID,settStatusID,checkStatus);
					}	
					//修改原记录的状态
					lReturn=dao.updateStatus(settID,settStatusID);
					System.out.println(settID+"^^^^^^^^^^^^^^^^"+settStatusID);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;   
		}		
		return lReturn;				
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
			strSQL += " where CREDITCLIENTID= ? and CREDITEDCLIENTID!=? and TRANSACTIONTYPE=? and STARTDATE=? and ENDDATE=? and STATUSID=?";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			int index=1;
			ps.setLong(index++,creditInfo.getCreditClientID());
			ps.setLong(index++,creditInfo.getCreditedClientID());//限定被授信方 非财务公司
			ps.setLong(index++,creditInfo.getTransactionType());
			ps.setTimestamp(index++,creditInfo.getStartDate());
			ps.setTimestamp(index++,creditInfo.getEndDate());
			ps.setLong(index++,creditInfo.getStatusID());
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
	
	public long showApprovalUserList(
	        JspWriter out, 
	        String strFieldName,
	        String strNextFieldName, 
	        long lModuleID,
			long lSubLoanTypeID, 
			double amount,
			long lOfficeID, 
			long lCurrencyID,
			long lUserID,
			long dataStatusID,
			long ActionID
		) throws Exception
	{

		Connection con = null;
		ApprovalDao approvaldao = null;
		CreditApprovalDAO creditApprovalDao = null;
		ApprovalSettingDao approvalsettingdao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		ApprovalTracingInfo ATInfo = null;
		ApprovalSettingInfo ASInfo = null;
		long lApprovalID = -1;
		long lTotalLevel = -1;
		long lLevel = -1;
		long lIsPass = -1;		
		long lStatusID = -1;
		Vector vNextUser = new Vector(); //下一级审核人集合

		try 
		{
			//connection 不能嵌套
			con = Database.getConnection();
			creditApprovalDao = new CreditApprovalDAO();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);
			//查询审核设置信息
			lApprovalID = creditApprovalDao.getApprovalId(lModuleID,lSubLoanTypeID,lOfficeID,lCurrencyID,amount,ActionID);
			System.out.println("11111111111111111"+lApprovalID);
			lLevel = creditApprovalDao.getLevelId(lApprovalID,lUserID);
			System.out.println("222222222222222222"+lLevel);
			ASInfo = approvalsettingdao.findApprovalSetting(lApprovalID);
			System.out.println("33333333333333333"+ASInfo);
		
			if (ASInfo != null)
			{
				lApprovalID = ASInfo.getID();
				lTotalLevel = ASInfo.getTotalLevel();
				lIsPass = ASInfo.getIsPass();
				lStatusID = ASInfo.getStatusID();
			}
			//如果该设置的状态不是已激活，则返回-1
			if(lStatusID == Constant.ApprovalStatus.SUBMIT)
			{
				if(dataStatusID==CRAconstant.TransactionStatus.SAVE && lLevel!=1)
				{
					 //当前审核人不在审批设置中，返回-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,5);
                    lReturn = -2;
				}else if (lLevel <= 0)
                {
                    //当前审核人不在审批设置中，返回-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,2);
                    lReturn = -2;
                }
				else if (lLevel == lTotalLevel)
				{
					//最后一级审核
					showUserList(out, strFieldName, strNextFieldName, null, 3);
					lReturn = 1;
				}
				else if (lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel))
				{
				    //当前审核人不在审批设置第一级中，返回-2                    
	                showWrongUserList(out,strFieldName,strNextFieldName,3);
	                lReturn = -2;
				}
				else
				{
					//查询审核人员信息
					ASInfo = null;
					if (lIsPass == 1) //允许越级审核
					{					    
					        ASInfo = approvaldao.findApprovalItemAboveLevel(
									lApprovalID,
									lLevel,
									lUserID,									
									lTotalLevel,lCurrencyID,lOfficeID);					    
					}
					else
					{					     
					        ASInfo = approvaldao.findApprovalItem(lApprovalID,
									lLevel+1,
									lUserID,
									lCurrencyID,lOfficeID);					    
					}
					if (ASInfo != null)
					{ 
						vNextUser = ASInfo.getNextUser();
					}
					if (vNextUser == null || vNextUser.size() == 0)
					{
						showWrongUserList(out,strFieldName,strNextFieldName,4);
						//没有设置下一级审核人员
						lReturn = -1;
					}
					else
					{
						//审核人员下拉列表显示控件
						showUserList(out, strFieldName, strNextFieldName,
									 vNextUser,
									 lIsPass);
						lReturn = 0;
					}
				}
			}
			else
			{
				showWrongUserList(out,strFieldName,strNextFieldName,1);
				lReturn = -1;
			}
			con.close();
			con = null;
		}
        catch (IException ie)
        {
            if (con != null)
			{
				con.close();
				con = null;
			}
            throw ie;
        }
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.showApprovalUserList() failed.  Exception is " +
				e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (lReturn);
	
	}
	
	/**
	 * 根据审批流ID，用户ID查出此用户的审批级别
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
	public long getLevelId(long approvalID,long userID)
	{
		long lReturn=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=Database.getConnection();
			String sql="select nlevel from loan_approvalitem";
			sql+="  where napprovalid="+approvalID+" and nuserid="+userID+"";			
			System.out.println("查询审批级别IDSQL====="+sql);
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				lReturn=rs.getLong("nlevel");	
			}		
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}

	public long getApprovalId(long moduleid,long lSubLoanTypeID,long officeID,long currencyID,double amount,long ActionID)
	{
		long lReturn=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = Database.getConnection();
			String sql="select approvalid from loan_approvalrelation ";
			sql+=" where moneysegment = (select max(Moneysegment) from loan_approvalrelation";
			sql+=" where moduleid="+moduleid+" and moneysegment<="+amount+" ";
			
			//修改
			sql+=" and actionid="+ActionID+" and subloantypeid = "+lSubLoanTypeID+" ";

			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +")";
			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +" ";
			//ZCQ 于2007-1-30日为显示下一级审批人的查询方法添加模块限制条件
			sql+=" and moduleid="+moduleid;
			//修改
			sql+=" and actionid="+ActionID+" and subloantypeid = "+lSubLoanTypeID+" ";
			
			sql+=" and approvalid in (select id from loan_approvalsetting where nstatusid=2 and nofficeid="+officeID+" and ncurrencyid="+ currencyID +" )";
			System.out.println("查询审批流IDSQL======="+sql);
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				lReturn=rs.getLong("approvalid");	
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);		
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}
    
	
	/**
	 * 审核人员下拉列表显示控件(审批设置未完成～）
	 * @param out                输出
	 * @param strFieldName       域的名称
	 * @param strFieldName       下一个域的名称
	 * @param nErrorNo       	 错误信息编号
	 *
	 */
	private static void showWrongUserList(JspWriter out, String strFieldName,String strNextFieldName,int nErrorNo) throws Exception
	{
		int i = 0;
		ApprovalUserInfo AUInfo = new ApprovalUserInfo();
		String strError = "";

		try
		{
			switch (nErrorNo)
			{
				case 1:
				    strError = "审批设置未完成或没有激活！请检查！";
				    break;
				case 2:
				    strError = "当前审核人不在该审批设置中！请检查！";
				    break;
				case 3:
				    strError = "当前审核人不在该审批设置第一级中！请检查！";
				    break;
				case 4:
				    strError = "下一级审核人为空！请检查！";
				    break;
				case 5:
				    strError = "您不为第一级审批人！请检查！";
				    break;
				default :
				    strError = "审批设置不正确！请检查！";
			}
		    out.println("<script language='javascript'>");
			out.println("alert('" + strError + "');");
			out.println("</script>");
			out.println("<select class=\"box\" name=\"" + strFieldName +
						"\" onfocus=\"nextfield='" + strNextFieldName + "';\">");
			out.println("<option value=\"-1\"></option>");
			out.println("</select>");
		}
		catch (Exception ex)
		{
		}
	}
    
	/**
	 * 审核人员下拉列表显示控件
	 * @param out                输出
	 * @param strFieldName       域的名称
	 * @param strFieldName       下一个域的名称
	 * @param NextUser           用户列表
	 * @param lDisplayFinish     是否显示“审核完成”：1 是；2 否；3 只显示“审核完成”
	 *
	 */
	private static void showUserList(JspWriter out, String strFieldName,
									 String strNextFieldName, Vector vNextUser,
									 long lDisplayFinish) throws Exception
	{
		int i = 0;
		ApprovalUserInfo AUInfo = new ApprovalUserInfo();

		try
		{
			out.println("<select class=\"box\" name=\"" + strFieldName +
						"\" onfocus=\"nextfield='" + strNextFieldName + "';\">");
			out.println("<option value=\"-1\"></option>");
			if (lDisplayFinish != 3)
			{
				if (vNextUser != null && vNextUser.size() > 0)
				{
					while (i < vNextUser.size())
					{
						AUInfo = (ApprovalUserInfo) vNextUser.get(i);
						out.println("<option value=\"" + AUInfo.getUserID() +
									"\">" + AUInfo.getUserName() + "</option>");
						i++;
					}
					if (lDisplayFinish == 1)
					{
						out.println("<option value=\"-2\">审核完成</option>");
					}
				}
			}
			else
			{
				out.println("<option value=\"-2\">审核完成</option>");
			}
			out.println("</select>");
		}
		catch (Exception ex)
		{
		}
	}
	
}
