/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.treasuryplan.reportapproval.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.dataentity.*;
import java.util.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.iss.itreasury.treasuryplan.exception.*;
import com.iss.itreasury.treasuryplan.util.*;
import com.iss.itreasury.treasuryplan.reportapproval.dataentity.*;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.system.approval.dao.ApprovalRelationDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalRelationInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizlogic.*;
import com.iss.itreasury.treasuryplan.report.dao.Trea_TPForecastDataDAO;
import com.iss.itreasury.treasuryplan.report.dataentity.*;
import com.iss.itreasury.treasuryplan.report.bizlogic.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Trea_TreasuryPlanDAO extends TreasuryPlanDAO
{
	
	/*
    3662	 	期末可用余额
	3661	     差额调节
	3660	 	资金头寸
	3659	 	必要备付金
	1830	 	资金运用
	2	 	    资金来源
	1	 	    期初可用余额
 */
private long beginBalanceRowID = TPConstant.RowID.getInstance().getBeginBalanceRowID();//1
private long beginBalanceRowID1 = TPConstant.RowID.getInstance().getBeginBalanceRowID1();//1
private long formRowID = TPConstant.RowID.getInstance().getFormRowID();//2
private long toRowID = TPConstant.RowID.getInstance().getToRowID();//1830
private long needMoneyRowID = TPConstant.RowID.getInstance().getNeedMoneyRowID();//3659
private long cashRowID = TPConstant.RowID.getInstance().getCashRowID();//3660
private long adjustCashRowID = TPConstant.RowID.getInstance().getAdjustCashRowID();//3660
private long balanceRegulateRowID = TPConstant.RowID.getInstance().getBalanceRegulateRowID();//3661
private long endBalanceRowID = TPConstant.RowID.getInstance().getEndBalanceRowID();//3662
private long endBalanceRowID1 = TPConstant.RowID.getInstance().getEndBalanceRowID1();//3662

	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	public Trea_TreasuryPlanDAO()
	{
		super("Trea_TreasuryPlan");
	}
	public Trea_TreasuryPlanDAO(Connection conn){
		super("Trea_TreasuryPlan",conn);
	}	
	public Collection findByMultiOption(TreasuryPlanQueryInfo qInfo) throws TreasuryPlanException
	{
		String strSelect = "";
		String strSQL = "";
		String strSQL1 = "";
		Vector v = new Vector();
				
		long queryPurpose = qInfo.getQueryPurpose();
		long currencyID = qInfo.getCurrencyId();
		long officeID = qInfo.getOfficeId();
		long statusID = qInfo.getStatusId();
		long userID = qInfo.getUserId();
		String strUser = qInfo.getStrUser();
		long departmentID = qInfo.getDepartId();
		
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		ApprovalBiz appBiz = new ApprovalBiz();
				
		try
        {
            initDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		System.out.println(queryPurpose);
		if (queryPurpose == 1) //for modify
		{
			strSelect="select count(*) ";
			strSQL = " from Trea_TreasuryPlan aa "
					+ " where 1 = 1 "
					+ " and StatusID >= " + TPConstant.PlanVersionStatus.SAVE
					+ " and StatusID <= " + TPConstant.PlanVersionStatus.CHECK					
					+ " and InputUserID = "	+ userID;
				
			//////////////////////查询条件////////////////////////////////////////////////////
			if (statusID == TPConstant.PlanVersionStatus.SAVE)
			{
				strSQL += " and nextCheckLevel = 1 ";
				strSQL += " and StatusID = " + TPConstant.PlanVersionStatus.SAVE;
			}
			else if (statusID == TPConstant.PlanVersionStatus.SUBMIT)
			{
				strSQL += " and nextCheckLevel = 1 ";
				strSQL += " and StatusID = " + TPConstant.PlanVersionStatus.SUBMIT;
			}
			else
			{
				strSQL += " and ( (nextCheckLevel=1 and StatusID = " + TPConstant.PlanVersionStatus.SAVE+")";
				strSQL += " or (nextCheckLevel=1 and statusID="+ TPConstant.PlanVersionStatus.SUBMIT+") )";
			}
		
		}
		else if (queryPurpose == 2) //for examine
		{
			strSelect = " select count(*) ";
			strSQL = " from Trea_TreasuryPlan aa " 
			    	+ " where 1 = 1 " ;
			    	
			//////////////////////查询条件////////////////////////////////////////////////////
			if (statusID == TPConstant.PlanVersionStatus.SUBMIT)
			{
				strSQL += " and StatusID = " + TPConstant.PlanVersionStatus.SUBMIT;
				strSQL += " and NextCheckUserID in " + strUser;
			}
			else if (statusID==TPConstant.PlanVersionStatus.CHECK)
			{
				strSQL += " and StatusID = " + TPConstant.PlanVersionStatus.CHECK;
			}
			else if (statusID==TPConstant.PlanVersionStatus.REFUSE)
			{
				strSQL += " and StatusID = " + TPConstant.PlanVersionStatus.REFUSE;
			}			
			else if (statusID==TPConstant.PlanVersionStatus.CANCEL)
			{
				strSQL += " and StatusID = " + TPConstant.PlanVersionStatus.CANCEL;
			}			
		}
				
		if (departmentID > 0)
		{
		    strSQL += " and departmentID = " + departmentID;
		}
		
		if (officeID>0)
		{
			strSQL +=" and officeID="+officeID;
		}
		
		if (currencyID>0)
		{
			strSQL+=" and currencyID="+currencyID;
		}
		////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
		int nIndex = 0;
		nIndex = orderParamString.indexOf(".");
		if (nIndex > 0)
		{
			if (orderParamString.substring(0, nIndex).toLowerCase().equals("Trea_TreasuryPlan"))
			{
				strSQL += " order by aa." + orderParamString.substring(nIndex + 1);
			}
		}
		else
		{
			strSQL += " order by aa.ID";
		}
		if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			strSQL += " desc";
		}
		log4j.debug(strSelect+strSQL);
		
		try
		{
			prepareStatement(strSelect+strSQL);
			ResultSet rs = executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace() ;
			throw new TreasuryPlanException();
		} 
		catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		lPageCount = lRecordCount / pageLineCount;
		if ((lRecordCount % pageLineCount) != 0)
		{
			lPageCount++;
		}		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//返回需求的结果集
		/*
		lRowNumStart = (pageNo - 1) * pageLineCount + 1;
		lRowNumEnd = lRowNumStart + pageLineCount - 1;
		strSQL = " select * from ( select aa.*,rownum r from ( select * " + strSQL;
		strSQL += " ) aa ) where r between " + lRowNumStart + " and " + lRowNumEnd;
		*/
		strSQL="select *" + strSQL;
		log4j.debug(strSQL);
		try
		{
			prepareStatement(strSQL);
			ResultSet rs1 = executeQuery();
			
			while (rs1 != null && rs1.next())
			{
			    TreasuryPlanInfo info = new TreasuryPlanInfo();
				//info = (TreasuryPlanInfo) getDataEntityFromResultSet(info.getClass());
				info.setId(rs1.getLong("Id"));
				info.setCode( rs1.getString("code") );
				info.setStartDate( rs1.getTimestamp("startDate"));
				info.setEndDate( rs1.getTimestamp("endDate"));
				info.setOfficeId(rs1.getLong("officeId")); //办事处
				info.setCurrencyId(rs1.getLong("currencyId")); //币种				
				info.setNextCheckUserId(rs1.getLong("nextCheckUserId")); //下一级审核人
				info.setNextCheckLevel(rs1.getLong("nextCheckLevel")); //下一级审核级别
				info.setInputUserId(rs1.getLong("inputUserId")); //录入人
				info.setInputDate(rs1.getTimestamp("inputDate")); //录入时间				
				info.setStatusId(rs1.getLong("statusId")); //状态
				info.setTimestamp(rs1.getTimestamp("tstamp")); //实践戳
				//Trea_TreasuryPlan表中没有的字段				
				info.setRecordCount(lRecordCount); //记录数
				info.setPageCount(lPageCount); //页数
				
				//此处需要修改，根据现有审批流进行修改
				info.setLastCheckUserId( appBiz.getLastCheckPerson( qInfo.getApprovalId(),info.getId(),1,1,1,1 ));
				v.add(info);
			}			
		}
		catch (ITreasuryDAOException e)
		{
			throw new TreasuryPlanException();
		}
		catch (SQLException e)
        {
            e.printStackTrace();
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
        finally
		{
			
			try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e)
	        {
	            e.printStackTrace();
	        }
		}   
		return (v.size() > 0 ? v : null);
		
	}
	
	public long createReportVersion(TreasuryPlanInfo qInfo) throws Exception
	{
		/*首先检查区间内是否有未完成的资金计划*/
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		long ret = -1;
		Vector aList=null;
		long firmDepID=-1;
		
		firmDepID = TPUtil.getCompanyID();
		conn=Database.getConnection() ;		
		strSQL ="SELECT * FROM Trea_TreasuryPlan  \n";
		strSQL +=" where 1=1   \n";
		//strSQL +=" and ( departmentID="+qInfo.getDepartmentId()+"  or departmentID="+firmDepID+" )";
		strSQL += " and (   \n";
		strSQL += "       (    \n";
		strSQL += "          departmentID = "+qInfo.getDepartmentId() +"";
		strSQL += "          and  statusID in( "+TPConstant.PlanVersionStatus.SAVE+","
		                                        + TPConstant.PlanVersionStatus.SUBMIT +")  \n";
		strSQL += "       ) or (    \n";
		strSQL +="           departmentID = "+firmDepID + " ";
		strSQL += " 		 and  statusID in( "+TPConstant.PlanVersionStatus.SAVE+","
		                                        +TPConstant.PlanVersionStatus.SUBMIT+","
											    + TPConstant.PlanVersionStatus.CHECK +")  \n";
		strSQL += "       )   \n";
		strSQL += "     )   \n";
		strSQL +=" and (   \n";
		strSQL +=" (to_char(StartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getStartDate())+"'   \n";
		strSQL +=" and to_char(endDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartDate()) + "')  \n";
		strSQL +=" or( to_char(startDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndDate()) + "'  \n";
		strSQL +=" and to_char(endDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getEndDate()) + "')   \n";
		strSQL +=" or( to_char(startDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartDate()) + "'  \n";
		strSQL +=" and to_char(endDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndDate()) + "')   \n";
		strSQL +=" or( to_char(startDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getStartDate()) + "'  \n";
		strSQL +=" and to_char(endDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getEndDate()) + "')  \n";
		strSQL +=" ) and gatherType = " +qInfo.getGatherType() + "\n";
		
		log4j.print( strSQL );
		ps=conn.prepareStatement( strSQL );
		rs=ps.executeQuery() ;
		
		if (rs.next())
		{
			log4j.print( "区间内有未完成的资金计划！");
			ret=0;			
		}
		if (rs!=null)
		{
			rs.close() ;
			rs=null;
		}
		if (ps!=null)
		{
			ps.close();
			ps=null;
		}

		long beginTime = 0;
		long endTime = 0;
		try
		{
			boolean isCompany =false;
			
			if (ret!=0)
			{
				//部门资金计划
				//System.out.println("----------||"+TPNameRef.getDepartmentLevelByID(qInfo.getDepartmentId()));
				if (TPNameRef.getDepartmentLevelByID(qInfo.getDepartmentId())<=0 )
				{
					/*拷贝编制区间内的粒度详细信息*/
					TPForecastDataConditionInfo conditionInfo = new TPForecastDataConditionInfo();
					conditionInfo.setAuthorizedDepartmentID(qInfo.getDepartmentId());
					conditionInfo.setOfficeID(qInfo.getOfficeId());
					conditionInfo.setCurrencyID(qInfo.getCurrencyId());
					conditionInfo.setTransactionDateStart(qInfo.getStartDate() );
					conditionInfo.setTransactionDateEnd(qInfo.getEndDate() );
					ReportBean bizbean=new ReportBean();
					if(qInfo.getGatherType()==TPConstant.GatherType.DAYSUM) //如果是按日汇总
					{
						log4j.print("按日汇总查询");
						aList = (Vector)bizbean.find(conditionInfo);
					}
					if(qInfo.getGatherType()==TPConstant.GatherType.WEEKSUM) //如果是按周汇总
					{
						log4j.print("按周汇总查询");
						aList = (Vector)bizbean.findByWeek(conditionInfo);
					}
					if(qInfo.getGatherType()==TPConstant.GatherType.MONTHSUM) //如果是按月汇总
					{
						log4j.print("按月汇总查询");
						aList = (Vector)bizbean.findByMonth(conditionInfo);
					}
					
				}
				else//公司资金计划
				{
					System.out.println("公司资金计划");
					isCompany = true;
					aList=getFirmPlanDetail(qInfo.getOfficeId(),qInfo.getCurrencyId(),qInfo.getDepartmentId(),qInfo.getStartDate(),qInfo.getEndDate());
				}
				if ( (aList==null)||(aList.size()==0) )
					ret=-1;
				else
				{
					/*首先创建新版本*/
					setUseMaxID();
					qInfo.setStatusId( TPConstant.PlanVersionStatus.SAVE );
					qInfo.setInputDate( Env.getSystemDate() );
					qInfo.setUpdateDate( Env.getSystemDate() );
					qInfo.setPlanDate(Env.getSystemDate() );
					qInfo.setUpdateUserId( qInfo.getInputUserId() );
					qInfo.setCode( getTreasuryPlanCode( qInfo.getDepartmentId(),Env.getSystemDateTime() ) );
					qInfo.setNextCheckLevel(1);
					//制单人必须处在审批流第一级
					qInfo.setNextCheckUserId(qInfo.getInputUserId());
					
					ret=add(qInfo);
					log4j.print( "获得新版本的版本ID:"+ret);
				}
							
				if ( (aList!=null)&&(aList.size()>0) )
				{
					TreasuryPlanDetailInfo detailInfo=null;
					TPForecastDataInfo forInfo=null;
					conn.setAutoCommit(false);
					beginTime = Env.getSystemDateTime().getTime();
					System.out.println("AddDetailInfo:beginTime:"+beginTime);
					Trea_TreasuryPlanDetailDAO detailDao = new Trea_TreasuryPlanDetailDAO(conn);
					
					detailDao.setUseMaxID() ;
					for (int i=0;i<aList.size();i++)
					{
						forInfo=(TPForecastDataInfo)aList.get(i);
						detailInfo=new TreasuryPlanDetailInfo();
						detailInfo.setTreasuryPlanID(ret);
						detailInfo.setAuthorizedDepartment( forInfo.getAuthorizedDepartment() );
						detailInfo.setAuthorizedUser( forInfo.getAuthorizedUser() );
						detailInfo.setForecastAmount( forInfo.getForecastAmount() );
						detailInfo.setIsLeaf( forInfo.getIsLeaf() );
						detailInfo.setLineID( forInfo.getLineID() );
						detailInfo.setLineLevel( forInfo.getLineLevel() );
						detailInfo.setLineName( forInfo.getLineName() );
						detailInfo.setLineNo( forInfo.getLineNo() );
						detailInfo.setParentLineID( forInfo.getParentLineID() );
						detailInfo.setPlanAmount( forInfo.getPlanAmount() );
						detailInfo.setTransactionDate( forInfo.getTransactionDate() );
						detailInfo.setIsNeedSum(forInfo.getIsNeedSum());
						
						detailDao.add(detailInfo);
					}	
					endTime = Env.getSystemDateTime().getTime();
					System.out.println("AddDetailInfo:beginTime:"+endTime);
					System.out.println("AddDetailInfo ="+(endTime-beginTime));
					
					conn.commit();
					
					if (isCompany)
					{
						reComputeFinal( conn,ret,qInfo.getStartDate(),qInfo.getEndDate(),qInfo.getOfficeId(),qInfo.getCurrencyId());
						conn.commit();
					}
		
				} 
				
				if (conn!=null)
				{
					conn.close();
					conn=null;
				}	
			}
		} 
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (conn!=null)
			{
				conn.close();
				conn=null;
			}				
		}
		return ret;
	}
	public long check(ApprovalTracingInfo ATInfo) throws TreasuryPlanException
	{
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
		long lNextLevel = ATInfo.getNextLevel();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		TreasuryPlanInfo info = new TreasuryPlanInfo();
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == TPConstant.Actions.REJECT) //拒绝
		{
			info.setId(lApprovalContentID);
			info.setStatusId(TPConstant.PlanVersionStatus.REFUSE);
			try
			{
				update(info);
			}
			catch (ITreasuryDAOException e)
			{
				throw new TreasuryPlanException();
			}
		}
		if (ATInfo.getCheckActionID() == TPConstant.Actions.CHECK) //审批
		{
			info.setId(lApprovalContentID);
			info.setStatusId(TPConstant.PlanVersionStatus.SUBMIT);
			info.setNextCheckUserId(lNextUserID);
			try
			{
				info.setNextCheckLevel(getNextCheckLevelByApplyID(lApprovalContentID) + 1);				
				update(info);
			}
			catch (ITreasuryDAOException e)
			{
				throw new TreasuryPlanException();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (ATInfo.getCheckActionID() == TPConstant.Actions.CHECKOVER) //审批&&最后
		{
			info.setId(lApprovalContentID);
			info.setStatusId(TPConstant.PlanVersionStatus.CHECK);
			info.setNextCheckUserId(lNextUserID);
			info.setCheckDate( Env.getSystemDate() );
			try
			{
				update(info);
			}
			catch (ITreasuryDAOException e)
			{
				throw new TreasuryPlanException();
			}
			//审批完成后需要做的操作
			//doAfterCheckOver(lApprovalContentID);
		}
		if (ATInfo.getCheckActionID() == TPConstant.Actions.RETURN) //修改
		{
			info.setId(lApprovalContentID);
			info.setStatusId(TPConstant.PlanVersionStatus.SUBMIT);
			info.setNextCheckUserId(ATInfo.getInputUserID());
			//置下一级审核为1
			info.setNextCheckLevel(1);
			try
			{
				update(info);
			}
			catch (ITreasuryDAOException e)
			{
				throw new TreasuryPlanException();
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		log4j.debug("check end");
		return lApprovalContentID;
	}
	private String getTreasuryPlanCode(long departmentID,Timestamp inputDate) throws Exception	
	{
		String planCode="";
		String departmentCode="";
		String dateCode="";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL="";
		
		departmentCode=TPNameRef.getDepartmentCodeByID( departmentID );
		if (departmentCode==null || departmentCode.length()==0 )
			departmentCode="OTH";
		log4j.print("获得部门代码："+departmentCode);
		
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.setTime ( inputDate ) ;
		String strMonth = String.valueOf ( calendar.get ( Calendar.MONTH ) + 1 ) ;
		if (strMonth.length ( ) == 1)
		{
			strMonth = "0" + strMonth ;
		}
		String strDay = String.valueOf ( calendar.get ( Calendar.DATE ) ) ;
		if (strDay.length ( ) == 1)
		{
			strDay = "0" + strDay ;
		}
		dateCode=calendar.get ( Calendar.YEAR ) + strMonth + strDay ;
		log4j.print( "获得日期代码:" +dateCode);
		 	
		strSQL="select nvl(max(substr(Code,length(code)-1,2)),0)+1 from Trea_TreasuryPlan where Code like '"+departmentCode+dateCode+"%'";
		
		try
		{
			conn=Database.getConnection() ;
			ps=conn.prepareStatement( strSQL );
			rs=ps.executeQuery() ;
			if ( rs.next() ) 
			{
				long sID=rs.getLong(1);
				if (sID<10)
					planCode=departmentCode+dateCode+"0"+sID;
				else
					planCode=departmentCode+dateCode+sID;	
			}
			if (rs!=null)
			{
				rs.close() ;
				rs=null;
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if(conn!=null)
			{
				conn.close() ;
				conn=null;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally{
			if (rs!=null)
			{
				rs.close() ;
				rs=null;
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if(conn!=null)
			{
				conn.close() ;
				conn=null;
			}
		}
		
		log4j.print( "编码："+planCode);
		
		return planCode;
	}
	//华能用的方法，先不使用
	/*private Vector getFirmPlanDetail(long officeID,long currencyID,long departmentID,Timestamp startDate,Timestamp endDate) throws Exception	
	{
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL="";
		Vector aList=new Vector();
		Timestamp curDate=startDate;
		long versionID = -1;
		String verGroup="";
		
		try
		{
			conn=Database.getConnection() ;
			while (!curDate.after(endDate))
			{
				strSQL="select departmentID,max(id) as maxID from trea_treasuryPlan "
					+" where 1=1"
					+" and statusID="+TPConstant.PlanVersionStatus.CHECK
					+" and departmentID<>"+departmentID
					+" and to_char(StartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(curDate) + "'"
					+" and to_char(EndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(curDate) + "'"
					+" group by departmentID";
					
				log4j.print( strSQL );
				ps=conn.prepareStatement( strSQL );
				rs=ps.executeQuery() ;
				
				verGroup="(0";
				while (rs.next()) 
				{
					versionID=rs.getLong("maxID");
					verGroup+=","+versionID;
				}
				verGroup+=")";
				
				if (rs!=null)
				{
					rs.close() ;
					rs=null;
				}
				if (ps!=null)
				{
					ps.close() ;
					ps=null;
				}
				
				if (verGroup.length()>3) //有可取的最大版本
				{
					strSQL="select * from ("
						+" select ForecastAmount,Isleaf,IsNeedSum,fs.LineID,LineLevel,LineName,LineNo,ParentLineID,TransactionDate,AuthorizedDepartment,AuthorizedUser "
						+" from trea_TreasuryPlanDetail fs, "
						+" (select max(id) as id,lineid from trea_TreasuryPlanDetail"
						+" where TreasuryPlanID in "+verGroup
						+" and transactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')"
						+" group by lineid"
						+" ) fd"
						+" where fs.id=fd.id"
						+" union all"
						+" select ForecastAmount,Isleaf,IsNeedSum,LineID,LineLevel,LineName,LineNo,ParentLineID,TransactionDate,AuthorizedDepartment,AuthorizedUser "
						+" from trea_tpForecastData"
						+" where lineID not in ("
						+" select distinct lineID from trea_treasuryplandetail"
						+" where 1=1 "
						+" and treasuryPlanID in"+verGroup
						+" and transactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')"						
						+" )"
						+" and transactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')"
						+") aa, (select lineID,sum(planAmount) as planAmount from ("
						+" select ForecastAmount,Isleaf,IsNeedSum,LineID,LineLevel,LineName,LineNo,ParentLineID,PlanAmount,TransactionDate,AuthorizedDepartment,AuthorizedUser "
						+" from trea_TreasuryPlanDetail "
						+" where TreasuryPlanID in "+verGroup
						+" and transactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')"
						+" union all"
						+" select ForecastAmount,Isleaf,IsNeedSum,LineID,LineLevel,LineName,LineNo,ParentLineID,PlanAmount,TransactionDate,AuthorizedDepartment,AuthorizedUser "
						+" from trea_tpForecastData"
						+" where lineID not in ("
						+" select distinct lineID from trea_treasuryplandetail"
						+" where 1=1 "
						+" and treasuryPlanID in"+verGroup
						+" and transactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')"						
						+" )"
						+" and transactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')"
						+") group by lineID ) bb"
						+" where bb.lineID=aa.lineID";	
				}
				else
				{
					strSQL="select * from trea_tpForecastData "
						+" where 1=1"
						+" and to_char(transactionDate,'yyyy-mm-dd')='"+DataFormat.getDateString(curDate)+"'";
				}

				log4j.print( strSQL );	
				ps=conn.prepareStatement( strSQL);
				rs=ps.executeQuery() ;
				while (rs.next())
				{
					TPForecastDataInfo info= new TPForecastDataInfo();

					info.setAuthorizedDepartment(rs.getString("AuthorizedDepartment"));
					info.setAuthorizedUser(rs.getString("AuthorizedUser"));			
					info.setIsLeaf(rs.getLong("IsLeaf"));	
					info.setLineID(rs.getLong("LineID"));	
					info.setLineLevel(rs.getLong("LineLevel"));
					info.setLineName(rs.getString("LineName"));
					info.setLineNo(rs.getString("LineNo"));
					info.setParentLineID(rs.getLong("ParentLineID"));
					info.setPlanAmount(rs.getDouble("PlanAmount"));
					info.setTransactionDate(rs.getTimestamp("TransactionDate"));
					info.setForecastAmount(rs.getDouble("FORECASTAMOUNT"));
					info.setIsNeedSum(rs.getLong("isNeedSum"));
					
					aList.add(info);					
				}
				if (rs!=null)
				{
					rs.close() ;
					rs=null;
				}
				if (ps!=null)
				{
					ps.close() ;
					ps=null;
				}					
							
				versionID=-1;
				curDate=DataFormat.getNextDate(curDate,1);
			}
			
			if (rs!=null)
			{
				rs.close() ;
				rs=null;
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if(conn!=null)
			{
				conn.close() ;
				conn=null;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally{
			if (rs!=null)
			{
				rs.close() ;
				rs=null;
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if(conn!=null)
			{
				conn.close() ;
				conn=null;
			}
		}
		
		return aList;
	}*/
	
	//中电子用的方法，
	private Vector getFirmPlanDetail(long officeID,long currencyID,long departmentID,Timestamp startDate,Timestamp endDate) throws Exception	
	{
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL="";
		Vector aList=new Vector();
		Timestamp curDate=startDate;
		long versionID = -1;
		String verGroup="";
		
		try
		{
			conn=Database.getConnection() ;
			while (!curDate.after(endDate))
			{
				strSQL="select departmentID,max(id) as maxID from trea_treasuryPlan "
					+" where 1=1"
					+" and statusID="+TPConstant.PlanVersionStatus.CHECK
					+" and departmentID<>"+departmentID
					+" and to_char(StartDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(curDate) + "'"
					+" and to_char(EndDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(curDate) + "'"
					+" group by departmentID";
					
				log4j.print( strSQL );
				ps=conn.prepareStatement( strSQL );
				rs=ps.executeQuery() ;
				
				verGroup="(0";
				while (rs.next()) 
				{
					versionID=rs.getLong("maxID");
					verGroup+=","+versionID;
				}
				verGroup+=")";
				
				if (rs!=null)
				{
					rs.close() ;
					rs=null;
				}
				if (ps!=null)
				{
					ps.close() ;
					ps=null;
				}
				
				if (verGroup.length()>3) //有可取的最大版本
				{
					strSQL=" SELECT ForecastAmount,Isleaf,IsNeedSum,LineID,LineNo, ";
					strSQL+=" LineLevel,LineName,ParentLineID,TransactionDate,";
					strSQL+=" AuthorizedDepartment,AuthorizedUser,PlanAmount";
					strSQL+=" FROM trea_TreasuryPlanDetail ";
					strSQL+=" WHERE TreasuryPlanID in "+verGroup; 
					strSQL+=" AND transactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')";
					strSQL+=" AND Isleaf = 1";
					strSQL+=" UNION";
					strSQL+=" SELECT tpf.ForecastAmount,Isleaf,IsNeedSum,";
					strSQL+=" id as LineID,LineNo,LineLevel,LineName,ParentLineID,";
					strSQL+=" to_date('"+DataFormat.getDateString(curDate)+"','yyyy-mm-dd') as TransactionDate,";
					strSQL+=" AuthorizedDepartment,AuthorizedUser,0 as PlanAmount";
					strSQL+=" FROM trea_tptemplate ";
					strSQL+=" ,(SELECT ForecastAmount,lineID as inLineid FROM Trea_TPForecastData";
					strSQL+=" WHERE OfficeID="+officeID;
					strSQL+=" AND CurrencyID="+currencyID;
					strSQL+=" AND TransactionDate=to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd')";
					strSQL+=" ) tpf";
					strSQL+=" WHERE Isleaf = 0"; 
					strSQL+=" AND id=tpf.inLineid"; 

					log4j.print( strSQL );	
					ps=conn.prepareStatement( strSQL); 
					rs=ps.executeQuery() ;
					while (rs.next())
					{
						TPForecastDataInfo info= new TPForecastDataInfo();

						info.setAuthorizedDepartment(rs.getString("AuthorizedDepartment"));
						info.setAuthorizedUser(rs.getString("AuthorizedUser"));			
						info.setIsLeaf(rs.getLong("IsLeaf"));	
						info.setLineID(rs.getLong("LineID"));	
						info.setLineLevel(rs.getLong("LineLevel")); 
						info.setLineName(rs.getString("LineName"));
						info.setLineNo(rs.getString("LineNo"));
						info.setParentLineID(rs.getLong("ParentLineID"));
						info.setPlanAmount(rs.getDouble("PlanAmount"));
						info.setTransactionDate(rs.getTimestamp("TransactionDate"));
						info.setForecastAmount(rs.getDouble("FORECASTAMOUNT"));
						info.setIsNeedSum(rs.getLong("isNeedSum"));
						
						aList.add(info);					
					}
					if (rs!=null)
					{
						rs.close() ;
						rs=null;
					}
					if (ps!=null)
					{
						ps.close() ;
						ps=null;
					}	
				}
				
				versionID=-1;
				curDate=DataFormat.getNextDate(curDate,1);
			}
			
			if (rs!=null)
			{
				rs.close() ;
				rs=null;
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if(conn!=null)
			{
				conn.close() ;
				conn=null;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally{
			if (rs!=null)
			{
				rs.close() ;
				rs=null;
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if(conn!=null)
			{
				conn.close() ;
				conn=null;
			}
		}
		
		return aList;
	}
	
	private String getAllDate(Timestamp startDate,Timestamp endDate){
		String rnt = " ( to_date('1900-01-01','yyyy-mm-dd')  ";
		Timestamp curDate=startDate;
		while (!curDate.after(endDate)){
		    rnt = rnt + " , to_date('"+DataFormat.getDateString(curDate) +"','yyyy-mm-dd') ";
			curDate=DataFormat.getNextDate(curDate,1);
		}
		rnt = rnt+") ";
		return "";
	}
	public Collection findTPForcastData(TreasuryPlanInfo vInfo) throws ITreasuryDAOException
	{
		Collection c = null;
		String strSQL = "";
		
		try {
			strSQL = "select * from Trea_Tpforecastdata \n"
				+" where 1=1 \n"
				+" and to_char(transactionDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(vInfo.getStartDate()) + "'"
				+" and to_char(transactionDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(vInfo.getEndDate()) + "'";
			if (vInfo.getIsCompany()==Constant.YesOrNo.NO )	
				strSQL+=" and AUTHORIZEDDEPARTMENTID = "+vInfo.getDepartmentId() ;
				
			prepareStatement(strSQL);
			executeQuery();
			c=getDataEntitiesFromResultSet( TPForecastDataInfo.class );
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}
		return c;
	}
	/**
	 * 获得该申请书的当前审批级别
	 * @param applyId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	private long getNextCheckLevelByApplyID(long applyId) throws Exception
	{
		long nextCheckLevel = -1;
		String strSQL = "";
		strSQL = " select nextCheckLevel from Trea_TreasuryPlan where 1 = 1 ";
		strSQL += " and id = " + applyId;
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
			throw e;
		}finally{
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				throw e;
			}
		}
		return nextCheckLevel;
	}	
	
	/**
	 * 获得该申请书的当前审批级别
	 * @param applyId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public  void deletePhysical(long applyId) throws Exception
	{
		String strSQL = "";
		strSQL = " delete from Trea_TreasuryPlan where id= "+applyId;

		try
		{
			initDAO();
			prepareStatement(strSQL);
			executeUpdate();
		}
		catch(ITreasuryDAOException e)
		{
			throw e;
		}finally{
			finalizeDAO();
		}	
	}		
	
	
	public void reComputeFinal(Connection conn,long versionID,Timestamp startDate,Timestamp endDate,long officeID,long currencyID) throws Exception
	{
		String strSQL="";
		//Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int interval=-1;
		Timestamp[] curDate=null;
		
		try
		{
			if (conn==null)
			{
				conn=Database.getConnection() ;
			}
			/*
			else
			{
				con=conn;
			}*/
			
			///////////////
			
			Timestamp sDate = startDate;
			Timestamp eDate = endDate;

			while(!sDate.after(eDate)){
				sumAmountDependOnLineLevel(officeID,currencyID,sDate,versionID,conn);
				sDate = DataFormat.getNextDate(sDate,1);
			}			
			
			strSQL="select TO_DATE('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd') - "
				+"TO_DATE('"+DataFormat.getDateString(startDate)+"','yyyy-mm-dd') from dual";
			ps=conn.prepareStatement( strSQL );
			log4j.print( strSQL );
			rs=ps.executeQuery() ;
			if (rs.next() )
			{
				interval=rs.getInt( 1 )+1;	
			}		
			
			System.out.println(""+interval);
			curDate=new Timestamp[interval];
			curDate[0]=startDate;
			for (int i=1;i<interval;i++)
			{
				curDate[i]=DataFormat.getNextDate( curDate[i-1]);
			}
			
			for (int i=0;i<interval;i++)
			{
				reComputeFinalDay(conn,versionID,curDate[i]);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	private void reComputeFinalDay(Connection conn,long versionID,Timestamp curDate) throws Exception
	{
		Connection con=null;
		PreparedStatement ps=null;
		double val3662=0;
		double val3661=0;
		double val1=0;
		double val2=0;
		double val1830=0;
		String strSQL="";
		
		try
		{
			con=conn;
			val1=getSingleLineAmount(conn,versionID,curDate,beginBalanceRowID);//期初可用余额
			val2=getSingleLineAmount(conn,versionID,curDate,formRowID); //资金来源
			val1830=getSingleLineAmount(conn,versionID,curDate,toRowID);//资金运用
			val3662=val1 + val2 - val1830;//期末可用余额
			
			//更新期末可用余额
			strSQL="update TREA_TREASURYPLANDETAIL set planAmount=?" 
				+" where treasuryplanID="+versionID
				+" and to_char(transactionDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(curDate) + "'"
				+" and lineID="+endBalanceRowID; //
			ps=con.prepareStatement( strSQL);
			log4j.print("更新期末可用余额:"+ strSQL +"and amount="+val3662);
			ps.setDouble( 1,val3662);
			ps.executeUpdate() ;
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			
			val1=getSingleLineAmount(conn,versionID,curDate,beginBalanceRowID1);//期初余额
			val3661=val1 + val2 - val1830;//期末余额
			
			//更新期末余额
			strSQL="update TREA_TREASURYPLANDETAIL set planAmount=?" 
				+" where treasuryplanID="+versionID
				+" and to_char(transactionDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(curDate) + "'"
				+" and lineID="+endBalanceRowID1; //
			ps=con.prepareStatement( strSQL);
			log4j.print("更新期末余额:"+ strSQL +"and amount="+val3661);
			ps.setDouble( 1,val3661);
			ps.executeUpdate() ;
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			
			double val_bybfj=getSingleLineAmount(conn,versionID,curDate,needMoneyRowID);//必要备付金
			double val_zjtc=val3662- val_bybfj;//资金头寸
			
			//更新资金头寸
			strSQL="update TREA_TREASURYPLANDETAIL set planAmount=?" 
				+" where treasuryplanID="+versionID
				+" and to_char(transactionDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(curDate) + "'"
				+" and lineID="+cashRowID; //
			ps=con.prepareStatement( strSQL);
			log4j.print("更新资金头寸:"+ strSQL +"and amount="+val_zjtc);
			ps.setDouble( 1,val_zjtc);
			ps.executeUpdate() ;
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			
			double val_cetj=getSingleLineAmount(conn,versionID,curDate,balanceRegulateRowID);//差额调节
			double val_tjhzjct=val_zjtc-val_cetj;//调节后资金头寸
			//更新调节后资金头寸
			strSQL="update TREA_TREASURYPLANDETAIL set planAmount=?" 
				+" where treasuryplanID="+versionID
				+" and to_char(transactionDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(curDate) + "'"
				+" and lineID="+adjustCashRowID; //
			ps=con.prepareStatement( strSQL);
			log4j.print("更新调节后资金头寸:"+ strSQL +"and amount="+val_tjhzjct);
			ps.setDouble( 1,val_tjhzjct);
			ps.executeUpdate() ;
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			
			/*Timestamp nextDay=DataFormat.getNextDate( curDate );
			strSQL="update TREA_TREASURYPLANDETAIL set planAmount=?" 
				+" where treasuryplanID="+versionID
				+" and to_char(transactionDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(nextDay) + "'"
				+" and lineID="+beginBalanceRowID;//1
			ps=con.prepareStatement( strSQL );
			log4j.print( strSQL +"and amount="+val3662);
			ps.setDouble( 1,val3662);
			ps.executeUpdate() ;
			
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}*/

		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
		}
	}
	
	private double getSingleLineAmount(Connection conn,long versionID,Timestamp curDate,long lineID) throws Exception
	{
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String strSQL="";
		double val=0;
		
		try
		{
			con=conn;
	
			strSQL="select planAmount from TREA_TREASURYPLANDETAIL where treasuryplanID="+versionID
				+" and to_char(transactionDate,'yyyy-mm-dd') = '" + DataFormat.getDateString(curDate) + "'"
				+" and lineID="+lineID;
			ps=con.prepareStatement( strSQL);
			log4j.print( strSQL );
			rs=ps.executeQuery() ;
			if 	(rs.next())
			{
				val=rs.getDouble("planAmount");	
				log4j.print("lineAmount:"+lineID+"==="+val);
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			if (ps!=null)
			{
				ps.close();
				ps=null;
			}
			
		}
		return val;		
	}
	public static void main(String args[])
	{
		Trea_TreasuryPlanDAO dao = new Trea_TreasuryPlanDAO();
		TreasuryPlanQueryInfo qInfo = new TreasuryPlanQueryInfo();
		TreasuryPlanInfo pInfo = new TreasuryPlanInfo();
		pInfo.setStartDate( DataFormat.getDateTime("2005-2-27"));
		pInfo.setEndDate(  DataFormat.getDateTime("2005-2-27"));
		pInfo.setDepartmentId( 9 );
		pInfo.setCurrencyId(1);
		pInfo.setOfficeId( 1 );
		qInfo.setQueryPurpose( 1 );
		qInfo.setInputUserId( 138 );
		Collection c=null;
		Connection conn=null;
		try
		{
			dao.findByMultiOption(qInfo);
			long a = dao.createReportVersion( pInfo );
			//System.out.println(a);
			//conn=Database.getConnection() ;
			//dao.reComputeFinal( conn,4,DataFormat.getDateTime("2004-08-31"),DataFormat.getDateTime("2004-09-01"));
		} catch (TreasuryPlanException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//System.out.println(c.size());
		
	}
	
	public void sumAmountDependOnLineLevel(long officeID, long currencyID, Timestamp forecastDate,long versionID,Connection conn) throws Exception {

		Trea_TreasuryPlanDetailDAO treasuryPlanDetailDAO = new Trea_TreasuryPlanDetailDAO(conn);
		long levelCount = treasuryPlanDetailDAO.getLevelCountByCondition(officeID,currencyID,/*currentDate,*/forecastDate,versionID);
		log4j.debug("--------一共:"+levelCount);
		for (long i = levelCount-1; i >= 1; i--) {
			log4j.debug("--------第:"+i+"行---------");
			Collection c2 = treasuryPlanDetailDAO.getSameLevelLeafDestinationData(officeID, currencyID, /*currentDate,*/ forecastDate, i,versionID);
			Iterator it2 = c2.iterator();
			while (it2.hasNext()) {
				Long lineID = (Long) it2
						.next();
				log4j.debug("------lineID.longValue():"+lineID.longValue());
				double sumAmount = treasuryPlanDetailDAO.sumAmountOfSubItems(
						officeID, currencyID, /*currentDate,*/ forecastDate, lineID.longValue(),versionID);
				
					if(sumAmount > 0)
						treasuryPlanDetailDAO.updateAmountByTransactionDateAndLineID(officeID, currencyID,forecastDate,lineID.longValue(),sumAmount,versionID);							

			    }		

			}
	}
	
}
