/*
 * Created on 2004-5-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attornmentapply.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentQueryInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttornmentApplyDao extends SecuritiesDAO{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN , this);

	public AttornmentApplyDao()
	{
		super("SEC_AttornmentApplyForm");
	}
	
	/**
	 * 修改查找和审核查找
	 * @param qInfo
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public Collection findByMultiOption(AttornmentQueryInfo qInfo) throws SecuritiesDAOException 
	{
		Collection c=null;
		String strSQL="";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		String strUser = "";
		//模块类型
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.ATTORNMENT_APPLY;
		long lApprovalID = -1;
		long queryPurpose=qInfo.getQueryPurpose();
		
		try
        {
            try {
            	//获得ApprovalID
            	if (lApprovalID <= 0)
            	{
            		strUser = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,qInfo.getOfficeId(),qInfo.getCurrencyId(), qInfo.getUserId() );
            	}
            } catch (Exception e1) 
            {
            	log4j.error("getApprovalID fail");
            	e1.printStackTrace();
            }
            try
            {
            	//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
            	strUser = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,qInfo.getOfficeId(),qInfo.getCurrencyId(), qInfo.getUserId());
            } catch (Exception e2)
            {
            	log4j.error("findTheVeryUser fail");
            	e2.printStackTrace();
            }

            /*-----------------init DAO --------------------*/
            try 
            {
            	initDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of init------------------*/		
            		
            try 
            {
            	strSQL = "select * from SEC_AttornmentApplyForm \n" 
            		+" where 1=1 \n";
            		
            	if (queryPurpose==1)				//for modify
            	{
            		strSQL+= " and (( statusID = " + LOANConstant.AttornmentStatus.SUBMIT
            			+ " and nextCheckLevel = 1 ) "
            			+ " or " 
            			+ " StatusID = " + LOANConstant.AttornmentStatus.CHECK
            			+ " ) "
            			+ " and InputUserID = " + qInfo.getUserId();
            			//+ " and NextCheckUserID = " + qInfo.getUserId() +" ) )";
            						
            		if ( qInfo.getStatusId() == SECConstant.ContractStatus.SUBMIT )
            		{
            			//strSQL += " and NextCheckUserID = " + qInfo.getUserId();
            			strSQL += " and nextCheckLevel = 1" ;
            			strSQL += " and StatusID = " + SECConstant.ContractStatus.SUBMIT;				
            		}
            		else if ( qInfo.getStatusId() == SECConstant.ContractStatus.CHECK )
            		{
            			strSQL += " and StatusID = " + SECConstant.ContractStatus.CHECK;				
            		}
            	}
            	else if (queryPurpose==2)		//for check
            	{
            		strSQL+= " and NextCheckUserID in " + strUser   
            			+ " and StatusID = " + SECConstant.ContractStatus.SUBMIT;
            	}

            	if ( qInfo.getStartRepurchaseApplyId()>0 )
            	{
            		strSQL+=" and repurchaseApplyId >=" + qInfo.getStartRepurchaseApplyId();
            	}
            	
            	if ( qInfo.getEndRepurchaseApplyId()>0 )
            	{
            		strSQL+=" and repurchaseApplyId <=" + qInfo.getEndRepurchaseApplyId();
            	}
            	
            	if ( qInfo.getStartAttornmentApplyId() >0 )
            	{
            		strSQL+=" and ID>= " +qInfo.getStartAttornmentApplyId() ;
            	}

            	if ( qInfo.getEndAttornmentApplyId()  >0 )
            	{
            		strSQL+=" and ID<= " +qInfo.getEndAttornmentApplyId() ;
            	}
            	
            	if ( qInfo.getStartRepurchaseAmount() >0)
            	{
            		strSQL+=" and AttornmentAmount >=" +qInfo.getStartRepurchaseAmount() ;
            	}
            	
            	if (qInfo.getEndRepurchaseAmount() >0)
            	{
            		strSQL+=" and attornmentAmount<= "+qInfo.getEndRepurchaseAmount() ;
            	}

            	if (qInfo.getStartInputDate() != null) {
            		strSQL += " and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartInputDate()) + "'";
            	}
            	
            	if (qInfo.getEndInputDate() != null) {
            		strSQL += " and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndInputDate()) + "'";
            	}
            	
            	////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            	int nIndex = 0;
            	String orderParamString=qInfo.getOrderParamString() ;
            	nIndex = orderParamString.indexOf(".");
            	if (nIndex > 0)
            	{
            		if (orderParamString.substring(0,nIndex).toLowerCase().equals("SEC_contractAttornmentApplyForm"))
            		{
            			strSQL += " order by " + orderParamString.substring(nIndex+1);
            		}
            	}
            	else
            	{
            		strSQL += " order by ID";
            	}
            	
            	if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
            		strSQL += " desc";
            	}
            						
            	prepareStatement(strSQL);
            	executeQuery();
            	c=getDataEntitiesFromResultSet( AttornmentApplyInfo.class );

            }
            catch(Exception e)
            {
            	throw new SecuritiesDAOException("查找贷款转让时产生错误",e);
            }

            /*----------------finalize Dao-----------------*/
            try 
            {
            	finalizeDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of finalize---------------*/
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
           try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
        }
		return (c.size () > 0 ? c : null);
	}		
	
	public Collection findAttornmentContractByApplyId(long lID) throws SecuritiesDAOException
	{
		Collection c=null;
		String strSQL="";
		
		try
        {
            /*-----------------init DAO --------------------*/
            try 
            {
            	initDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of init------------------*/		
            		
            try 
            {
            	strSQL = " select * from SEC_AttornmentContract where attornmentApplyId = "+lID;
            		
            	prepareStatement(strSQL);
            	executeQuery();
            	c = getDataEntitiesFromResultSet( AttornmentContractInfo.class );
            	
            	if ( c!=null&&c.size()>0 )
            	{
            		ArrayList v=(ArrayList)c;
            		ContractDao conDao = new ContractDao();
            		for ( int i=0;i<v.size();i++ )
            		{
            			AttornmentContractInfo aInfo = (AttornmentContractInfo)v.get(i);
            			
            			//double balance = conDao.getBalanceForAttornment( aInfo.getContractId() );
            			//Boxu Update 2008年11月6日 资产转让按放款单进行转让
            			double balance = conDao.getPayformBalanceForAttornment(aInfo.getPayId());
            			
            			//这里不减去提前还款单金额,获取子账户实际金额
            			aInfo.setBalanceForAttornment(balance);
            			
            			//已经转让的金额
            			ContractDao cdao = new ContractDao();
        				double useBalance = cdao.sumLastAttornmentAmount(aInfo.getPayId());
        				
        				//已转让金额
        				aInfo.setLastAttornmentAmount(useBalance);
        				
        				//可转让金额
        				aInfo.setLeftoversattornmentamount(balance - useBalance);
            			
            			aInfo.setBorrowClientName( conDao.getClientName( aInfo.getClientId() ));
            		}
            	}

            }
            catch(Exception e)
            {
            	throw new SecuritiesDAOException("查找贷款转让申请得合同时产生错误",e);
            }

            /*----------------finalize Dao-----------------*/
            try 
            {
            	finalizeDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of finalize---------------*/
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
        {
        	try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
        }
		return (c.size () > 0 ? c : null);
	}		
	
	public long check(ApprovalTracingInfo ATInfo) throws SecuritiesDAOException {
		
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";

		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();
		
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();

		AttornmentApplyInfo aInfo = new AttornmentApplyInfo();
		long applyID=-1;

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
		{			

			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.REFUSE);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.SUBMIT);
			aInfo.setNextCheckUserId(lNextUserID);
//			aInfo.setNextCheckLevel(getNextCheckLevelByApplyID(lApprovalContentID) + 1);
			
			ApprovalDelegation appbiz = new ApprovalDelegation();
			try 
			{
				
				aInfo.setNextCheckLevel(appbiz.findApprovalUserLevel(lApprovalID,lNextUserID));
				
			} 
			catch (Exception e) {
			}
			try {
				update(aInfo);
				setNextCheckADD( lApprovalContentID );
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}

		if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.CHECK);
			aInfo.setNextCheckUserId(lNextUserID);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}

		}
		if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(SECConstant.ContractStatus.SUBMIT);
			aInfo.setNextCheckUserId(ATInfo.getInputUserID());
			aInfo.setNextCheckLevel(1);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		log4j.debug("check end");
		
		return lApprovalContentID;
	}	
	public String getApplyCode() throws SecuritiesDAOException 
	{
		String strSQL = "";
		ResultSet rs = null;
		String applyCode ="";
		long lID = -1;
		
		try
        {
            try {
            	initDAO();
            } catch (ITreasuryDAOException e) {
            	throw new SecuritiesDAOException(e);
            }
            
            try 
            {
            	//首先得到现有申请的最大ID
            	strSQL = "select nvl(max(ID)+1,1) oID from SEC_AttornmentApplyForm";
            	prepareStatement (strSQL);

            	rs = executeQuery ();

            	if (rs.next ()) 
            	{
            		lID = rs.getLong ("oID");
            	}
            	rs.close() ;
            	rs=null;
                    
            	//获得申请书编号
            	strSQL = " select to_char(sysdate,'yy') from dual ";
            	String sYear="";
            	String curID="40";
                    
            	prepareStatement(strSQL);
            	rs = executeQuery();
            	if (rs.next ())
            	{
            		sYear = rs.getString(1);
            	}
            	rs.close();
            	rs=null;
                    
            	strSQL="select nvl(max(substr(Code,6,3)),0)+1 from SEC_AttornmentApplyForm where Code like 'A"+sYear+curID+"%'";
            	prepareStatement(strSQL);
            	rs = executeQuery();
            	if (rs.next ())
            	{
            		long ll=rs.getLong(1);
                   	
            		if ( ll<10 )
            		{
            			applyCode = "A"+sYear+curID+"00"+ll;
            		}
            		else if (ll<100)
            		{
            			applyCode="A"+sYear+curID+"0"+ll;
            		}
            		else
            		{
            			applyCode = "A"+sYear+curID+ll;
            		}
                   	
            	}
            }catch (ITreasuryDAOException e) {
            	throw new SecuritiesDAOException(e);
            }catch (Exception ex){
            	ex.printStackTrace();
            }
            
            try {
            	finalizeDAO();
            } catch (ITreasuryDAOException e) {
            	throw new SecuritiesDAOException(e);
            }
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
        {
		    try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
		
		log4j.debug(":::::::::: ::::strcode::::::" + applyCode);

		return applyCode;
	}
	
	public void setNextCheckADD(long lID) throws SecuritiesDAOException 
	{
		String strSQL = "";
		ResultSet rs = null;
		
		try
        {
            try {
            	initDAO();
            } catch (ITreasuryDAOException e) {
            	throw new SecuritiesDAOException(e);
            }
            
            try 
            {
            	//首先得到现有申请的最大ID
            	strSQL = "update sec_attornmentApplyForm set nextCheckLevel=nextCheckLevel+1 where id="+lID;
            	prepareStatement (strSQL);
            	executeQuery ();

            }catch (ITreasuryDAOException e) {
            	throw new SecuritiesDAOException(e);
            }catch (Exception ex){
            	ex.printStackTrace();
            }
            
            try {
            	finalizeDAO();
            } catch (ITreasuryDAOException e) {
            	throw new SecuritiesDAOException(e);
            }
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
        {
		    try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
        }
		
	}	

	public Collection findContractByRepurchaseID(long lID) throws SecuritiesDAOException
	{
		Collection c=null;
		String strSQL="";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection conn2=null;
		long llID=-1;
		
		try
        {
            /*-----------------init DAO --------------------*/
            try 
            {
            	initDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of init------------------*/		
            		
            try 
            {
            	conn2=Database.getConnection() ;
            	strSQL = "select id from sec_attornmentapplyform where "
            		+" statusid = "+LOANConstant.AttornmentStatus.CHECK
            		+" and repurchaseapplyid="+lID;
            	System.out.println(strSQL);
            	ps2=conn2.prepareStatement( strSQL );
            	rs2=ps2.executeQuery() ;
            	if (rs2.next())
            	{
            		llID=rs2.getLong("id");	 
            	}
            	if (rs2!=null)
            	{
            		rs2.close();
            		rs2=null;
            	}
            	if (ps2!=null)
            	{
            		ps2.close() ;
            		ps2=null;
            	}
            	if (conn2!=null)
            	{
            		conn2.close();
            		conn2=null;
            	}
            	strSQL = "select * from SEC_AttornmentContract \n" 
            		+" where attornmentApplyId= "+llID;
            		
            	prepareStatement(strSQL);
            	executeQuery();
            	c=getDataEntitiesFromResultSet( AttornmentContractInfo.class );
            	
            	if ( c!=null&&c.size()>0 )
            	{
            		ArrayList v=(ArrayList)c;
            		ContractDao conDao = new ContractDao();
            		for ( int i=0;i<v.size();i++ )
            		{
            			AttornmentContractInfo aInfo = (AttornmentContractInfo)v.get(i);
            			
            			//double balance = conDao.getBalanceForAttornment( aInfo.getContractId() );
            			//Boxu Update 2008年11月6日 资产转让按放款单进行转让
            			double balance = conDao.getPayformBalanceForAttornment(aInfo.getPayId());
            			
            			//这里不减去提前还款单金额,获取子账户实际金额
            			aInfo.setBalanceForAttornment(balance);
            			
            			//已经转让的金额
            			ContractDao cdao = new ContractDao();
        				double useBalance = cdao.sumLastAttornmentAmount(aInfo.getPayId());
        				
        				//已转让金额
        				aInfo.setLastAttornmentAmount(useBalance);
        				
        				//可转让金额
        				aInfo.setLeftoversattornmentamount(balance - useBalance);
            			
            			aInfo.setBorrowClientName( conDao.getClientName( aInfo.getClientId() ));
            		}
            	}

            }
            catch(Exception e)
            {
            	throw new SecuritiesDAOException("查找贷款转让申请得合同时产生错误",e);
            }

            /*----------------finalize Dao-----------------*/
            try 
            {
            	finalizeDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of finalize---------------*/
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
        }
		return (c.size () > 0 ? c : null);
	}
	
	public AttornmentApplyInfo findAttornmentByRepurchaseID(long lID) throws SecuritiesDAOException
	{
		Collection c=null;
		String strSQL="";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection conn2=null;
		AttornmentApplyInfo applyInfo = null;
		long llID=-1;
		
		try
        {
            /*-----------------init DAO --------------------*/
            try 
            {
            	initDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of init------------------*/		
            		
            try 
            {
            	conn2=Database.getConnection() ;
            	strSQL = "select id from sec_attornmentapplyform where "
            		+" statusid = "+LOANConstant.AttornmentStatus.CHECK
            		+" and repurchaseapplyid="+lID;
            	System.out.println(strSQL);
            	ps2=conn2.prepareStatement( strSQL );
            	rs2=ps2.executeQuery() ;
            	if (rs2.next())
            	{
            		llID=rs2.getLong("id");	 
            	}
            	if (rs2!=null)
            	{
            		rs2.close();
            		rs2=null;
            	}
            	if (ps2!=null)
            	{
            		ps2.close() ;
            		ps2=null;
            	}
            	if (conn2!=null)
            	{
            		conn2.close();
            		conn2=null;
            	}
            	if (llID>0)			
            		applyInfo = (AttornmentApplyInfo)findByID( llID,AttornmentApplyInfo.class );

            }
            catch(Exception e)
            {
            	e.printStackTrace() ;
            	throw new SecuritiesDAOException("查找贷款转让申请时产生错误",e);
            }

            /*----------------finalize Dao-----------------*/
            try 
            {
            	finalizeDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of finalize---------------*/
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
        {
		    try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
        }
		
		return applyInfo;
	}
	
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查交割单是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean checkStatuID(long applyId) throws SecuritiesDAOException{
		boolean flag=false;
		long[] checkStatusId={
				//SECConstant.ContractStatus.SAVE,
				SECConstant.ContractStatus.APPROVALING,
				SECConstant.ContractStatus.CHECK,
				SECConstant.ContractStatus.ACTIVE,
				SECConstant.ContractStatus.NOTACTIVE,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "select count(*) from sec_applycontract c,sec_attornmentapplyform a");
		sqlBuf.append(" where c.statusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and a.id = " + applyId);
		sqlBuf.append(" and a.repurchaseapplyid =c.applyid");
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
			System.out.print(sqlBuf);
			ResultSet rs = executeQuery();
			try
			{
				if(rs != null && rs.next())
				{
					long temp=rs.getLong(1);
					if(temp>0){
						flag=true;
					}
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return flag;		
	}
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查交割单是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean cancelContract(long applyId) throws SecuritiesDAOException{
		boolean flag=false;
		PreparedStatement ps = null;
		Connection conn = null;
		
		long[] checkStatusId={
				SECConstant.ContractStatus.SAVE,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "update sec_applycontract s set s.statusid="+SECConstant.ContractStatus.CANCEL);
		sqlBuf.append(" where s.statusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.applyid = " + applyId);
		try		
		{   conn = Database.getConnection();
			ps = conn.prepareStatement(sqlBuf.toString());
			int rs=ps.executeUpdate();
		}
		catch (Exception e)
		{
			throw new SecuritiesDAOException("",e);
		}
		finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		return flag;		
	}
	
	
	
	
	private long getNextCheckLevelByApplyID(long applyId) throws SecuritiesDAOException
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nextCheckLevel from SEC_AttornmentApplyForm where 1 = 1 ";
		strSQL += " and id = " + applyId;
		try
        {
            try
            {
            	initDAO();
            	prepareStatement(strSQL);
            	ResultSet rs = executeQuery();
            	try
            	{
            		while (rs != null && rs.next()) 
            		{
            			nextCheckLevel = rs.getLong("nextCheckLevel");
            		}
            	}
            	catch (SQLException e1)
            	{
            		e1.printStackTrace();
            	}
            }
            catch(ITreasuryDAOException e)
            {
            	throw new SecuritiesDAOException(e);
            }
            try {
            	finalizeDAO();
            } catch (ITreasuryDAOException e) {
            	throw new SecuritiesDAOException(e);
            }
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
        {
        	try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
		
		return nextCheckLevel;
	}
	
	public void endForRepurchase(long repurchaseID) throws Exception
	{
		AttornmentContractDao dao=new AttornmentContractDao();
		ContractDao conDao = new ContractDao();
		ArrayList conList = new ArrayList();
		try
		{
			conList=(ArrayList)this.findContractByRepurchaseID(repurchaseID);
			conDao.setLastAttornment( conList,2);
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
			throw e;
		}
	}
	
	public AttornmentApplyInfo findAttornmentByRepurchaseID2(long lID) throws SecuritiesDAOException
	{
		Collection c=null;
		String strSQL="";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection conn2=null;
		AttornmentApplyInfo applyInfo = null;
		long llID=-1;
		
		try
        {
            /*-----------------init DAO --------------------*/
            try 
            {
            	initDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of init------------------*/		
            		
            try 
            {
            	conn2=Database.getConnection() ;
            	strSQL = "select id from sec_attornmentapplyform where "
            		+"repurchaseapplyid="+lID;
            	System.out.println(strSQL);
            	ps2=conn2.prepareStatement( strSQL );
            	rs2=ps2.executeQuery() ;
            	if (rs2.next())
            	{
            		llID=rs2.getLong("id");	 
            	}
            	if (rs2!=null)
            	{
            		rs2.close();
            		rs2=null;
            	}
            	if (ps2!=null)
            	{
            		ps2.close() ;
            		ps2=null;
            	}
            	if (conn2!=null)
            	{
            		conn2.close();
            		conn2=null;
            	}
            	if (llID>0)			
            		applyInfo = (AttornmentApplyInfo)findByID( llID,AttornmentApplyInfo.class );

            }
            catch(Exception e)
            {
            	e.printStackTrace() ;
            	throw new SecuritiesDAOException("查找贷款转让申请时产生错误",e);
            }

            /*----------------finalize Dao-----------------*/
            try 
            {
            	finalizeDAO();
            } 
            catch (ITreasuryDAOException e) 
            {
            	throw new SecuritiesDAOException(e);
            }
            /*----------------end of finalize---------------*/
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
        {
		    try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
        }
		
		return applyInfo;
	}
	
	public static void main(String argc[])
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		Collection c=null;
		try
		{
			dao.findAttornmentByRepurchaseID( 3186 );
		} catch (SecuritiesDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
