/*
 * Created on 2005-6-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.query.queryobj;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.budget.constitute.dao.Budget_PlanDAO;
import com.iss.itreasury.budget.dao.BudgetDAO;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo;
import com.iss.itreasury.budget.query.resultinfo.QBudgetResultInfo;
import com.iss.itreasury.budget.query.resultinfo.QBudgetVersionResultInfo;
import com.iss.itreasury.budget.templet.dataentity.DisplayTemplateDetailInfo;
import com.iss.itreasury.budget.templet.dataentity.DisplayTemplateInfo;
import com.iss.itreasury.budget.util.BUDGETConstant;
import com.iss.itreasury.budget.util.BUDGETNameRef;
import com.iss.itreasury.budget.util.UtilOperation;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class QBudget extends BudgetDAO {

	/**
	 * 
	 */
	public QBudget() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 预算版本查询 以及 预算调整查询
	 * 
	 * @param info
	 * @return QBudgetVersionResultInfo类型的集合
	 */
	public Collection queryBudgetVersion(QueryBudgetInfo info) {
		ArrayList list = new ArrayList();
		QBudgetVersionResultInfo planInfo = null;
		try {
			this.initDAO();
			String strSQL = "select a.ID,a.clientID,a.versionNo,a.budgetPeriodID,b.periodname,a.startdate,a.enddate,a.planDate,a.inputUserID,a.statusID,a.BudgetSystemID,c.budgetSystemName";
			strSQL += " from budget_plan a,BUDGET_PERIOD b ,budget_System c  where a.budgetPeriodID=b.id and a.BudgetSystemID = c.id  and a.StatusID != 0 "; 
			if (info.getClientID() > 0)
				strSQL += " and a.clientID = " + info.getClientID();
			if (info.getBudgetSystemID() > 0)
				strSQL += " and a.budgetSystemID=" + info.getBudgetSystemID();
			if (info.getBudgetPeriodID() > 0)
				strSQL += " and a.budgetPeriodID=" + info.getBudgetPeriodID();
			if (info.getStartDate() != null)
				strSQL += " and startDate >= to_date('" + info.getStartDate().toString().substring(0, 10) + " 00:00:00','yyyy-mm-dd HH24:MI:SS') ";
			if (info.getEndDate() != null)
				strSQL += " and endDate <= to_date('" + info.getEndDate().toString().substring(0, 10) + " 23:59:59','yyyy-mm-dd HH24:MI:SS') "; 
		    if(!info.getVersionNo().equals(""))
				strSQL += " and a.versionNo = '" + info.getVersionNo() + "'";
			strSQL += " order by a.versionNo asc";
			log.print(strSQL);
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			while (transRS.next()) { 

				planInfo = new QBudgetVersionResultInfo();
				planInfo.setPlanID(transRS.getLong("ID"));
				planInfo.setVersionNo(transRS.getString("versionNo"));				
				planInfo.setBudgetPeriodID(transRS.getLong("budgetPeriodID"));
				planInfo.setBudgetPeriodName(transRS.getString("periodName"));
				planInfo.setStartDate(transRS.getTimestamp("startDate"));
				planInfo.setEndDate(transRS.getTimestamp("endDate"));
				planInfo.setPlanDate(transRS.getTimestamp("planDate"));
				planInfo.setInputUserID(transRS.getLong("inputuserID"));
				planInfo.setStatusID(transRS.getLong("statusID"));
				planInfo.setBudgetSystemID(transRS.getLong("BudgetSystemID"));
				planInfo.setBudgetSystemName(transRS.getString("budgetSystemName"));
				planInfo.setClientID(transRS.getLong("clientID"));
                System.out.println("~~~~~~~~~预算ID~~~~~~~~~~ " + transRS.getLong("ID"));
                log.print("~~~~~~~~~预算ID~~~~~~~~~~ " + transRS.getLong("ID"));
				// planInfo.setBudgetFlag(transRS.getLong("budgetFlag"));
				list.add(planInfo);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 预算历史对比分析 对同一预算体系同一周期下的预算项目 对项目不同区间的预算金额与执行金额作比较 显示预算金额的差额与增长率以及执行金额的
	 * 差额与增长率
	 * 
	 * @QueryBudgetInfo info
	 * @return
	 */
	public Collection queryHistory(QueryBudgetInfo info) {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		StringBuffer sbSQL1 = null;
		ResultSet rs = null;
		String sResult = " ";
		ResultSet rs1 = null;
		String sResult1 = " ";
		Collection collA = new ArrayList();
		Collection collB = new ArrayList();
		Collection lastresultCol=new ArrayList();
		QBudget budget=new QBudget();
		Collection resultCol=new ArrayList();
		BigDecimal persent0=new BigDecimal(0);
		BigDecimal persent1=new BigDecimal(0);
		try {
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select a.itemid itemid,a.BudgetAmount BudgetAmount,a.ExEcuteAmount ExcuteAmount   from Budget_ItemSum a ");
			sbSQL.append(" where  a.statusid=1");
			sbSQL.append(" and a.BudgetSystemID="+info.getBudgetSystemID()+" and a.ClientID="+info.getClientID()+"");
			sbSQL.append(" and a.BudgetPeriodID="+info.getBudgetPeriodID()+"");
			sbSQL.append(" and decode(a.startdate,null,to_date('3000-01-01','yyyy-mm-dd'),a.startdate) \n");
			sbSQL.append(" = to_date('"+ DataFormat.formatDate(info.getStartDate())+ "','yyyy-mm-dd') \n");			
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				QBudgetResultInfo tmpInfo = new QBudgetResultInfo();
				tmpInfo.setItemID(rs.getLong("itemid"));
				tmpInfo.setBudgetAmount(rs.getDouble("BudgetAmount"));
				tmpInfo.setExecuteAmout(rs.getDouble("ExcuteAmount"));
				collA.add(tmpInfo);
			}
			
			sbSQL1 = new StringBuffer();
			sbSQL1.append(" select a.itemid itemid,a.BudgetAmount BudgetAmount,a.ExEcuteAmount ExcuteAmount   from Budget_ItemSum a ");
			sbSQL1.append(" where  a.statusid=1");
			sbSQL1.append(" and a.BudgetSystemID="+info.getBudgetSystemID()+" and a.ClientID="+info.getClientID()+"");
			sbSQL1.append(" and a.BudgetPeriodID="+info.getBudgetPeriodID()+"");
			sbSQL1.append(" and decode(a.startdate,null,to_date('3000-01-01','yyyy-mm-dd'),a.startdate) \n");
			sbSQL1.append(" = to_date('"+ DataFormat.formatDate(info.getRelativeStartDate())+ "','yyyy-mm-dd') \n");	
			Log.print(sbSQL1.toString());
			ps1 = conn.prepareStatement(sbSQL1.toString());
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				QBudgetResultInfo tmpInfo1 = new QBudgetResultInfo();
				tmpInfo1.setItemID(rs1.getLong("itemid"));
				tmpInfo1.setBBudgetAmount(rs1.getDouble("BudgetAmount"));
				tmpInfo1.setBExecuteAmout(rs1.getDouble("ExcuteAmount"));
				collB.add(tmpInfo1);
			}
		
			resultCol=	getLastResult(info, collA, collB);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally
        {
            try {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (conn != null)
                {
                   conn.close();
                   conn = null;
                }
                if (rs1 != null)
                {
                    rs1.close();
                    rs1 = null;
                }
                if (ps1 != null)
                {
                    ps1.close();
                    ps1 = null;
                }
            } catch (SQLException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
		return resultCol;
	}

	/**
	 * 根据体系id,单位ID查找体系内所有项目（要求按照项目编号排序）
	 */
	public Collection findItemByCondition(QueryBudgetInfo info)
			throws BudgetException {
		StringBuffer sbSQL = null;
		String sResult = " ";
		Collection coll = new ArrayList();
		try {
			this.initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct b.id id,b.itemno itemno,b.itemname itemname  from budget_itemprivilege a ,budget_templet b ");
			sbSQL.append(" where b.id=a.BudgetItemID");
			sbSQL.append(" and a.budgetClientID=" + info.getClientID() + "");
			sbSQL.append(" and a.budgetsystemid=" + info.getBudgetSystemID()+ "");
			sbSQL.append(" and b.statusid=1 and a.statusid=1 order by itemno");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) { 
				QBudgetResultInfo tmpInfo1 = new QBudgetResultInfo();
				tmpInfo1.setItemID(transRS.getLong("id"));
				tmpInfo1.setItemNo(transRS.getString("itemno"));
				tmpInfo1.setItemName(transRS.getString("itemname"));
				coll.add(tmpInfo1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return coll;
	}

	public Collection getSubClient(QueryBudgetInfo info) throws BudgetException {
		StringBuffer sbSQL = null;
		String sClientID = "";
		Collection coll = new ArrayList();

		try {
			this.initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" select a.ClientID clientid,b.scode code,b.sname name from clientRelation a,client b \n");
			sbSQL.append(" where a.ClientID =b.id and b.nstatusid=1 and  (a.clientid = " + info.getClientID() + " or a.ParentClientID="+ info.getClientID() + ") \n");
			sbSQL.append(" order by clientid");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) { 
				QBudgetResultInfo resuInf = new QBudgetResultInfo();
				resuInf.setClientID(transRS.getLong("clientid"));
				resuInf.setClientNo(transRS.getString("code"));
				resuInf.setClientName(transRS.getString("name"));
				coll.add(resuInf);
			}
			System.out.println(coll+"^^^^^^^^^size");
			if(coll.isEmpty()){
				QBudgetResultInfo resuInf = new QBudgetResultInfo();
				resuInf.setClientID(info.getClientID());
				resuInf.setClientNo(BUDGETNameRef.getClientCodeByID(info.getClientID()));
				resuInf.setClientName(BUDGETNameRef.getClientNameByID(info.getClientID()));
				coll.add(resuInf);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return coll;
	}
	
	/**
	 * 预算单位情况分析查询 查询公司下属单位在同 一预算体系同一周期下的项目的情况,包括预算 金额，执行金额，差额，执行比例。可以多选项目
	 * 
	 * @QueryBudgetInfo info
	 * @return
	 */
	public Collection queryClient(QueryBudgetInfo info) throws BudgetException {
		StringBuffer sbSQL = null;
		String sResult = " ";
		Collection coll = new ArrayList();

		try {
			this.initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" select a.ClientID clientid,a.ItemID itemid,b.itemName itemName,a.BudgetAmount budgetamount,a.ExecuteAmount executeamount \n");
			sbSQL.append("from Budget_ItemSum a,Budget_Templet b,CLIENTRELATION c \n");
			sbSQL.append(" where  a.ItemID=b.id and a.statusid="+ Constant.RecordStatus.VALID + " and b.statusid="+ Constant.RecordStatus.VALID + " \n");
			sbSQL.append("  and a.clientid = c.clientid \n");
			sbSQL.append(" and (a.clientid=" + info.getClientID()+ " or c.parentclientid=" + info.getClientID()+ ") \n");
			sbSQL.append(" and a.BudgetPeriodID=" + info.getBudgetPeriodID()+ " \n");
			sbSQL.append(" and a.BudgetSystemID=" + info.getBudgetSystemID()+ " \n");
			sbSQL.append(" and decode(a.StartDate,null,to_date('3000-01-01','yyyy-mm-dd'),a.StartDate) \n");
			sbSQL.append(" = to_date('"+ DataFormat.formatDate(info.getStartDate())+ "','yyyy-mm-dd') \n");
			//sbSQL.append(" and decode(a.StartDate,null,to_date('3000-01-01','yyyy-mm-dd'),a.StartDate) \n");
			//sbSQL.append(" <= to_date('"+ DataFormat.formatDate(info.getEndDate())+ "','yyyy-mm-dd') \n");
			sbSQL.append(" and a.ItemID in (" + info.getStrItemId() + ")\n");
			sbSQL.append(" order by ClientID,itemid \n");
			Log.print(sbSQL.toString());
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) { 
				QBudgetResultInfo resuInfo = new QBudgetResultInfo();
				resuInfo.setClientID(transRS.getLong("clientid"));// 客户ID
				resuInfo.setItemID(transRS.getLong("itemid"));// 项目ID
				resuInfo.setItemName(transRS.getString("itemName"));// 项目名称
				resuInfo.setBBudgetAmount(transRS.getDouble("budgetamount"));// 预算金额
				resuInfo.setBExecuteAmout(transRS.getDouble("executeamount"));// 执行金额
				resuInfo.setDiffExecuteAmount(transRS.getDouble("executeamount")- transRS.getDouble("budgetamount"));// 差额
				if (transRS.getDouble("budgetamount") != 0) {
					resuInfo.setExecutePercent((transRS.getDouble("executeamount") / transRS.getDouble("budgetamount")) * 100);// 执行比例
				} else {
					resuInfo.setExecutePercent(0);// 执行比例
				}
				coll.add(resuInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return coll;

	}

	
	 


	
	public long getAdjustNum(QueryBudgetInfo budgetInfo) throws BudgetException
	{

        log.print("enter getAdjustNum");
        long  adjustNum =-1;        
        try {
        	this.initDAO();
        	String strSQL = null;    	       
	            //取开始日期
	            Timestamp startDate = budgetInfo.getStartDate();	                	
	                	
	                strSQL = "select count(id) count  from budget_plan where 1=1";
	                strSQL += " and ClientID = ?";
	                strSQL += " and BudgetSystemID = ?";	                
	                strSQL += " and BudgetPeriodID = ?";
	                strSQL += " and StartDate = ?";
	                strSQL += " and BudgetFlag = "+BUDGETConstant.BudgetFlag.ADJUST;
	                strSQL += " and StatusID = " + BUDGETConstant.ConstituteStatus.LASTCHECK;	                
	                transPS = transConn.prepareStatement(strSQL);
	                transPS.setLong(1,budgetInfo.getClientID());
	                transPS.setLong(2,budgetInfo.getBudgetSystemID());	              
	                transPS.setLong(3,budgetInfo.getBudgetPeriodID());
	                transPS.setTimestamp(4,startDate);
	    			transRS = transPS.executeQuery();
	    			if (transRS.next()) { 	                	
	                	adjustNum = transRS.getLong("count");	
	                }	                
	            log.print(strSQL);
                log.print("clientID="+budgetInfo.getClientID());             
                log.print("budgetSystemID="+budgetInfo.getBudgetSystemID());             
                log.print("BudgetPeriodID="+budgetInfo.getBudgetPeriodID());
                log.print("startDate=" + startDate);
	           
	        
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        log.print("out getAmountByCondition方法");
        return adjustNum;
    
	}
	
	 /**
     * 根据单位,体系,周期,项目,开始日期取所需的金额类型(如当前预算数,上期执行数)
     * @param planInfo
     * @param itemID
     * @return
     * @throws BudgetException
     */
    private ArrayList getAdjustAmountByCondition(QueryBudgetInfo budgetInfo,long itemID) throws BudgetException
    {
        log.print("enter getAdjustAmountByCondition");
        ArrayList arrayAmount = new ArrayList();
        String versionNo = budgetInfo.getVersionNo();
        try {
        	this.initDAO();
        String strSQL = null;
    
	       
	            //取开始日期
	            Timestamp startDate = budgetInfo.getStartDate();
	            
	          
	               
	            	strSQL = "select a.ORIGINALAMOUNT ORIGINALAMOUNT ,a.BudgetAmount as BudgetAmount,";
	            	strSQL += " nvl(a.executeAmount,0) executeAmount,decode(a.BudgetAmount,0,0,a.executeAmount/a.BudgetAmount*100) as EXCUTESCALE,decode(a.BudgetAmount,0,0,(a.BUDGETAMOUNT-a.ORIGINALAMOUNT)/a.BudgetAmount*100) as ADJUSTSCALE,";
	            	strSQL += " (a.BudgetAmount-a.executeAmount) as SUB";
	            	strSQL += " from Budget_ItemSum a,Budget_Plan b,Budget_PlanDetail c where b.BudgetFlag=1";
	            	strSQL += " and  a.itemid=c.itemid and a.statusid=1 and b.statusid!=0 and c.statusid=1";
	                strSQL += " and a.BudgetSystemID = ?";
	                strSQL += " and a.itemID = ?";
	                strSQL += " and a.BudgetPeriodID = ?";
	                strSQL += " and a.ClientID=?";
	                strSQL += " and a.StartDate = ?";	
	                
	                log.info("**********"+strSQL);
	                log.print("clientID="+budgetInfo.getClientID());
	                log.print("versionNo="+versionNo);
	                log.print("budgetSystemID="+budgetInfo.getBudgetSystemID());
	                log.print("itemID="+itemID);
	                log.print("BudgetPeriodID="+budgetInfo.getBudgetPeriodID());
	                log.print("startDate=" + startDate);
	                transPS = transConn.prepareStatement(strSQL);
	                transPS.setLong(1,budgetInfo.getBudgetSystemID());
	                transPS.setLong(2,itemID);
	                transPS.setLong(3,budgetInfo.getBudgetPeriodID());
	                transPS.setLong(4,budgetInfo.getClientID());
	                transPS.setTimestamp(5,startDate);
	    			transRS = transPS.executeQuery();	    			      	 		            
	                if(transRS.next()){
	                	arrayAmount.add(DataFormat.formatAmountUseZero(transRS.getDouble("ORIGINALAMOUNT")));
	                  	arrayAmount.add(DataFormat.formatAmountUseZero(transRS.getDouble("BudgetAmount")));
	                	arrayAmount.add(DataFormat.formatAmountUseZero(transRS.getDouble("executeAmount")));
	                	arrayAmount.add(DataFormat.formatAmountUseZero(transRS.getDouble("EXCUTESCALE")));
	                	arrayAmount.add(DataFormat.formatAmountUseZero(transRS.getDouble("ADJUSTSCALE")));
	                   
	                }
	                if(transRS!=null)
	                {
	                	transRS.close();	                
	                }
	                if(transPS!=null)
	                {
	                	transPS.close();
	                
	                }	
	                	                		                	
	                strSQL = "select b.Amount from Budget_plan a ,Budget_planDetail b  where a.id=b.planid";
	                strSQL += " and a.ClientID = ?";
	                strSQL += " and a.BudgetSystemID = ?";
	                strSQL += " and b.itemID = ?";
	                strSQL += " and a.BudgetPeriodID = ?";
	                strSQL += " and a.StartDate = ?";
	                strSQL += " and a.BudgetFlag = "+BUDGETConstant.BudgetFlag.ADJUST;
	                strSQL += " and a.StatusID = " + BUDGETConstant.ConstituteStatus.LASTCHECK;
	                strSQL += " order by a.versionNo desc";
	                transPS = transConn.prepareStatement(strSQL);
	                transPS.setLong(1,budgetInfo.getClientID());
	                transPS.setLong(2,budgetInfo.getBudgetSystemID());
	                transPS.setLong(3,itemID);
	                transPS.setLong(4,budgetInfo.getBudgetPeriodID());
	                transPS.setTimestamp(5,startDate);
	                transRS = transPS.executeQuery();	    			      	 		            
	                while(transRS.next())
	                {
	                	arrayAmount.add(1,DataFormat.formatAmount(transRS.getDouble("Amount")));
	                   
	                }
	            
	            
	            log.print(strSQL);
                log.print("clientID="+budgetInfo.getClientID());
                log.print("versionNo="+versionNo);
                log.print("budgetSystemID="+budgetInfo.getBudgetSystemID());
                log.print("itemID="+itemID);
                log.print("BudgetPeriodID="+budgetInfo.getBudgetPeriodID());
                log.print("startDate=" + startDate);
	           
	        
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        log.print("out getAmountByCondition方法");
        return arrayAmount;
    }
    /**
     * 根据客户id查询账户当前活期余额
     * @param long clientid,long AccountType
     * @return
     * @throws BudgetException
     */
    public double getCurrentAccountBalance(long clientid) throws BudgetException
	{

        log.print("enter getAccountBalance");
        double accountBalance = 0.0;
        
        try {
        	this.initDAO();
            StringBuffer strSQL = new StringBuffer();    

	    	strSQL.append(" select distinct (a.mbalance-a.mUncheckPaymentAmount) balance \n");
	           	strSQL.append(" from SETT_SUBACCOUNT a ,sett_account b, SETT_ACCOUNTTYPE c \n");
	           	strSQL.append(" where a.nAccountID= b.id \n");
	           	strSQL.append(" and b.naccounttypeid=c.id");
	           	strSQL.append("  and c.naccountGroupid = " + SETTConstant.AccountGroupType.CURRENT +" \n" );
	          	           	
	           	strSQL.append(" and b.nclientid=" + clientid + "\n");
	           		
	               
	               log.info("**********"+strSQL.toString());
	               
	               transPS = transConn.prepareStatement(strSQL.toString());                 
	               transRS = transPS.executeQuery();	    			      	 		                	 		            
	               if(transRS.next())
	               {
	               	accountBalance  = transRS.getDouble("balance");

	                }
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        log.print("out getAccountBalance");
        return accountBalance;
    
    }
	/**
	 * 查询原始预算调整情况
	 * 此方法主要调用预算ID关联Budget_Plan,Budget_PlanDetail,Budget_ItemSum
	 * 查询原始额,实际预算金额,执行金额
	 * 
	 * @param planID
	 * @return QbudgetResultInfo类型集合
	 */
	public Collection queryBudgetAdjust(QueryBudgetInfo budgetInfo) throws BudgetException {
		
		   log.print("enter method :qBudget.queryBudgetInfo");
		      
		     Vector vector = new Vector();
		     DisplayTemplateInfo info = null;
		        try {
		            this.initDAO();
		            String strSQL = "select * from BUDGET_PLAN a,budget_plandetail b, BUDGET_TEMPLET c where a.id = b.planid and b.itemid= c.id  and a.id =? and b.statusid=? order by c.itemno";
		            log.print(strSQL);
		            log.print("planid="+budgetInfo.getPlanID());
		           
		            transPS = prepareStatement(strSQL);
		            transPS.setLong(1,budgetInfo.getPlanID());		            
		            transPS.setLong(2,Constant.RecordStatus.VALID);
		            transRS = executeQuery();
		            while (transRS.next())
		            {
		                info =  new DisplayTemplateInfo();
		                ArrayList detailList  = new ArrayList();
		                
		                info.setIsLeaf(transRS.getLong("isLeaf"));
		            	info.setItemID(transRS.getLong("itemId"));
		            	info.setItemLevel(transRS.getLong("itemLevel"));
		            	info.setItemName(transRS.getString("itemName"));								
		            	info.setItemNo(transRS.getString("itemNo"));
		            	if (transRS.getLong("parentItemID") > 0)
		            	    info.setParentItemID(transRS.getLong("parentItemID"));
		            	
		            	
		            
		            	//double[] arrayAmount = getAdjustAmountByCondition(budgetInfo,transRS.getLong("id"));
		            	ArrayList arrayAmount = new  ArrayList();
		            	arrayAmount=getAdjustAmountByCondition(budgetInfo,transRS.getLong("itemId"));
		            	for (int i=0;i<arrayAmount.size();i++)
		            	{
//		            	  添加显示的列,每一个列是个DisplayTemplateDetailInfo
		                	DisplayTemplateDetailInfo infoChild = new DisplayTemplateDetailInfo();
		            	   // infoChild.setAmount(Double.valueOf((String)arrayAmount.get(i)).doubleValue());
		            	    infoChild.setDisplayValue((String)arrayAmount.get(i));		            	 
		            	    infoChild.setDisplayType(1);		            	    
		            	    infoChild.setIsEdit(0);		            	  
		            	    	         	    	

		            	    System.out.println("setIsEdit="+infoChild.getIsEdit());
		            	    detailList.add(infoChild);
		                	info.setDetailInfos(detailList);
		            	}
		            	vector.add(info);
		            	
		            }
		        } catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();		           
		            throw new BudgetException();
					
		        }
		        finally
		        {
		            try {
		                this.finalizeDAO();
		            } catch (ITreasuryDAOException e1) {
		                // TODO Auto-generated catch block
		                e1.printStackTrace();
		            }
		        }
		        return vector;
		   
	}

	/**
	 * 查找小于此预算ID的此项目的每次的调整金额,组合成一个数组
	 * 
	 * @param info
	 * @return
	 */
	private double[] queryAdjustAmount(QueryBudgetInfo info) {
		return null;
	}

	/**
	 * 预算执行情况分析
	 * 
	 * @param info
	 * @return QbudgetResultInfo类型集合
	 */
	public Collection queryBudgetExecute(QueryBudgetInfo info) {	
		Collection lastresultCol=new ArrayList();
		try {	
			Budget_PlanDAO planDao=new Budget_PlanDAO();
			lastresultCol=planDao.getTemplateInfo(info);
		}catch(Exception ex){ex.printStackTrace();}
		
		return lastresultCol;
	}
	/**
	 * 某项目预算执行情况明细
	 * 
	 * @param info
	 * @return QExecuteDetailInfo类型集合
	 */
	public Collection queryExecuteDetail(QueryBudgetInfo info) {
		StringBuffer sbSQL = null;
		String sResult = " ";
		Collection coll = new ArrayList(); 
		try {
			 this.initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" select ItemNo,ItemName,ExecuteDate,TransNo,ExcuteAmount,ContractCode,remark from Budget_ItemDetail");
			sbSQL.append(" where statusid=1");
			sbSQL.append(" and ItemID=" + info.getItemID()+ " order by id desc");
			transPS = prepareStatement(sbSQL.toString());	           
            transRS = executeQuery();
            while (transRS.next())
            {
				QBudgetResultInfo resuInfo = new QBudgetResultInfo();
				resuInfo.setItemNo(transRS.getString("ItemNo"));
				resuInfo.setItemName(transRS.getString("ItemName"));
				resuInfo.setExecuteDate(transRS.getTimestamp("ExecuteDate"));
				resuInfo.setTransNo(transRS.getString("TransNo"));
				resuInfo.setExecuteAmout(transRS.getDouble("ExcuteAmount"));
				resuInfo.setContractCode(transRS.getString("ContractCode"));
				resuInfo.setRemark(transRS.getString("remark"));
				coll.add(resuInfo);
			}
		 } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();		                      			
        }
        finally
        {
            try {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
		return coll;
	}

	/**
	 * 预算结构分析 先查询出预算数及执行数,然后循环结果集调用Budget_ItemSumDAO查询上级项目的预算数以及执行数
	 * 
	 * @param info
	 * @return QbudgetResultInfo类型集合
	 */
	public Collection queryBudgetStructure(QueryBudgetInfo info) {
		return null;
	}

	/**
	 * 预算单位对比分析
	 * 
	 * @param info
	 * @return QBudgetResultInfo类型集合
	 */
	public Collection DiffClientBudget(QueryBudgetInfo info) {

		StringBuffer sbSQL = null;
		StringBuffer sbSQL1 = null;
		String sResult = " ";
		String sResult1 = " ";
		Collection collA = new ArrayList();
		Collection collB = new ArrayList();
		Collection lastresultCol=new ArrayList();
		QBudget budget=new QBudget();
		Collection resultCol=new ArrayList();
		BigDecimal persent0=new BigDecimal(0);
		BigDecimal persent1=new BigDecimal(0);
		try {
			 this.initDAO();
			sbSQL = new StringBuffer();
			sbSQL.append(" select a.itemid itemid,a.BudgetAmount BudgetAmount,a.ExEcuteAmount ExcuteAmount   from Budget_ItemSum a ");
			sbSQL.append(" where  a.statusid=1");
			sbSQL.append(" and a.BudgetSystemID="+info.getBudgetSystemID()+" and a.ClientID="+info.getClientID()+"");
			sbSQL.append(" and a.BudgetPeriodID="+info.getBudgetPeriodID()+"");
			sbSQL.append(" and decode(a.startdate,null,to_date('3000-01-01','yyyy-mm-dd'),a.startdate) \n");
			sbSQL.append(" = to_date('"+ DataFormat.formatDate(info.getStartDate())+ "','yyyy-mm-dd') \n");		
			Log.print(sbSQL.toString());
			transPS = prepareStatement(sbSQL.toString());	           
            transRS = executeQuery();
            while (transRS.next())
            {
				QBudgetResultInfo tmpInfo = new QBudgetResultInfo();
				tmpInfo.setItemID(transRS.getLong("itemid"));
				tmpInfo.setBudgetAmount(transRS.getDouble("BudgetAmount"));
				tmpInfo.setExecuteAmout(transRS.getDouble("ExcuteAmount"));
				collA.add(tmpInfo);
			}
			
            if(transRS!=null)
            {
            	transRS.close();	                
            }
            if(transPS!=null)
            {
            	transPS.close();
            
            }	
			sbSQL1 = new StringBuffer();
			sbSQL1.append(" select a.itemid itemid,a.BudgetAmount BudgetAmount,a.ExEcuteAmount ExcuteAmount   from Budget_ItemSum a ");
			sbSQL1.append(" where  a.statusid=1");
			sbSQL1.append(" and a.BudgetSystemID="+info.getBudgetSystemID()+" and a.ClientID="+info.getClientBID()+"");
			sbSQL1.append(" and a.BudgetPeriodID="+info.getBudgetPeriodID()+"");
			sbSQL1.append(" and decode(a.startdate,null,to_date('3000-01-01','yyyy-mm-dd'),a.startdate) \n");
			sbSQL1.append(" = to_date('"+ DataFormat.formatDate(info.getStartDate())+ "','yyyy-mm-dd') \n");							
			Log.print(sbSQL1.toString());
			transPS = prepareStatement(sbSQL1.toString());	           
            transRS = executeQuery();
            while (transRS.next()){
				QBudgetResultInfo tmpInfo1 = new QBudgetResultInfo();
				tmpInfo1.setItemID(transRS.getLong("itemid"));
				tmpInfo1.setBBudgetAmount(transRS.getDouble("BudgetAmount"));
				tmpInfo1.setBExecuteAmout(transRS.getDouble("ExcuteAmount"));
				collB.add(tmpInfo1);
			}
			resultCol=	getLastResult(info, collA, collB);
			
		 } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();		           
        }
        finally
        {
            try {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
		
		return resultCol;
	}
	
	

	/**
	 * 预算滞留比例分析 需要调用银企接口中的方法 
	 * 内转外金额=该单位内部账户转到外部银行账户的金额（即该单位外部银行账户该时间周期内收入合计）
	 * 对外支出金额=该单位外部银行账户对外支付的金额（即该单位外部银行该时间周期内支出合计）
	 * 银行滞留比例=外部银行账户余额-工行二级户余额/内部活期户余额+外部银行余额-工行二级户余额*100% 
	 * 预算滞留比例=外部银行账户余额-工行二级户余额/预算金额*100% 
	 * 预算支出比例= 对外支出金额/预算金额*100%
	 * 资金集中度比例= 内部活期户余额/内部活期户余额+外部银行余额-工行二级户余额*100%
	 * @param info
	 * @return QBudgetResultInfo类型集合
	 */
	public Collection queryBudgetResort(QueryBudgetInfo info)
			throws BudgetException {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		StringBuffer sbSQL1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sResult = " ";
		Collection coll = new ArrayList();

		try {
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select a.ClientID clientid,a.BudgetAmount budgetamount from Budget_ItemSum a");
			sbSQL.append(" where a.statusid=1");
			sbSQL.append(" and a.BudgetPeriodID=" + info.getBudgetPeriodID()+ "");
			sbSQL.append(" and a.BudgetSystemID=" + info.getBudgetSystemID()+ "");
			sbSQL.append(" and a.ItemID=" + info.getItemID() + "");
			sbSQL.append(" and decode(a.StartDate,null,to_date('3000-01-01','yyyy-mm-dd'),a.StartDate) \n");
			sbSQL.append(" = to_date('"+ DataFormat.formatDate(info.getStartDate())+ "','yyyy-mm-dd') \n");
			sbSQL.append(" and ((a.ClientID="+ info.getClientID() + ") or  (a.ClientID in (select ClientID from clientRelation where ParentClientID="+ info.getClientID() + ")))");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				QBudgetResultInfo resuInfo = new QBudgetResultInfo();
				// 根据单位查询收入、支出金额
				sbSQL1 = new StringBuffer();
				sbSQL1.append(" select a.MAMOUNT MAMOUNT,a.NDIRECTION NDIRECTION from SETT_TRANSINFOOFBANKACCOUNT a");
				sbSQL1.append(" where decode(a.DTTRANSACTION,null,to_date('3000-01-01','yyyy-mm-dd'),a.DTTRANSACTION) \n");
				sbSQL1.append(" >= to_date('"+ DataFormat.formatDate(info.getStartDate())+ "','yyyy-mm-dd') \n");
				sbSQL1.append(" and decode(a.DTTRANSACTION,null,to_date('3000-01-01','yyyy-mm-dd'),a.DTTRANSACTION) \n");
				sbSQL1.append(" <= to_date('"+ DataFormat.formatDate(info.getEndDate())+ "','yyyy-mm-dd') \n");
				sbSQL1.append(" and a.SACCOUNTNO in (select SBANKACCOUNTNO from Sett_bankAccountOfFiliale where NCLIENTID="+ rs.getLong("clientid") + ")");
				sbSQL1.append("union  (select b.MAMOUNT MAMOUNT,b.NDIRECTION NDIRECTION from SETT_HISTRANSINFOOFBANKACCOUNT b");
				sbSQL1.append(" where decode(b.DTTRANSACTION,null,to_date('3000-01-01','yyyy-mm-dd'),b.DTTRANSACTION) \n");
				sbSQL1.append(" >= to_date('"+ DataFormat.formatDate(info.getStartDate())+ "','yyyy-mm-dd') \n");
				sbSQL1.append(" and decode(b.DTTRANSACTION,null,to_date('3000-01-01','yyyy-mm-dd'),b.DTTRANSACTION) \n");
				sbSQL1.append(" <= to_date('"+ DataFormat.formatDate(info.getEndDate())+ "','yyyy-mm-dd') \n");
				sbSQL1.append(" and b.SACCOUNTNO in (select SBANKACCOUNTNO from Sett_bankAccountOfFiliale where NCLIENTID="+ rs.getLong("clientid") + "))");
				Log.print(sbSQL1.toString());
				ps1 = conn.prepareStatement(sbSQL1.toString());
				rs1 = ps1.executeQuery();
				// 贷是收入、借是支出
				double inAmount = 0.00;// //内转外金额
				double outAmount = 0.00; // 对外支付金额
				double bankResortPercent = 0.00; // 银行滞留比例
				double budgetResortPercent = 0.00; // 预算滞留比例
				while (rs1.next()) {
					if (rs1.getLong("NDIRECTION") == SETTConstant.DebitOrCredit.DEBIT) {
						outAmount += rs1.getDouble("MAMOUNT");
					} else if (rs1.getLong("NDIRECTION") == SETTConstant.DebitOrCredit.CREDIT) {
						inAmount += rs1.getDouble("MAMOUNT");
					}
				}
				resuInfo.setClientID(rs.getLong("clientid"));// 客户ID
				resuInfo.setBBudgetAmount(DataFormat.formatDouble(rs.getDouble("budgetamount")));// 预算金额
				resuInfo.setInAmount(DataFormat.formatDouble(inAmount));// 内转外金额
				resuInfo.setOutAmount(DataFormat.formatDouble(outAmount));// 对外支付金额
				double currentAccountBalance = this.getCurrentAccountBalance(resuInfo.getClientID());
				System.out.println("-----------活期余额----"+currentAccountBalance);
				double bankBalance = getBankAccountOfFilialeByClient((int)resuInfo.getClientID(),-1,Env.getSystemDate(),false);
				System.out.println("-----------银行余额----"+bankBalance);
				double subBankBalance = getBankAccountOfFilialeByClient((int)resuInfo.getClientID(),-1,Env.getSystemDate(),true);
				System.out.println("-----------二级户余额----"+subBankBalance);
				// 银行滞留比例=外部银行账户余额-工行二级户余额/内部活期户余额+外部银行余额-工行二级户余额*100%
				if ((bankBalance+currentAccountBalance-subBankBalance) != 0.00) {
					resuInfo.setBankResortPercent(DataFormat.formatDouble((bankBalance - subBankBalance)/ (bankBalance+currentAccountBalance-subBankBalance) * 100));
				} else {
					resuInfo.setBankResortPercent(0.00);
				}
				// 资金集中比例=内部活期户余额/内部活期户余额+外部银行余额-工行二级户余额*100%
				if ((bankBalance+currentAccountBalance-subBankBalance) != 0.00) {
					resuInfo.setCapitalPercent(DataFormat.formatDouble(currentAccountBalance/ (bankBalance+currentAccountBalance-subBankBalance) * 100));
				} else {
					resuInfo.setCapitalPercent(0.00);
				}
				// 预算滞留比例=（外部银行余额-工行二级户余额）/预算金额*100%
				if (rs.getDouble("budgetamount") > 0) {
					resuInfo.setBudgetResortPercent(DataFormat.formatDouble((bankBalance - subBankBalance)/ rs.getDouble("budgetamount") * 100));
				} else {
					resuInfo.setBudgetResortPercent(0.00);
				}
				coll.add(resuInfo);
				System.out.println(coll.size()+"^^^^^^^^^^^^");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}finally
        {
            try {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (conn != null)
                {
                   conn.close();
                   conn = null;
                }
                if (rs1 != null)
                {
                    rs1.close();
                    rs1 = null;
                }
                if (ps1 != null)
                {
                    ps1.close();
                    ps1 = null;
                }
            } catch (SQLException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
		return coll;
	}
	
	
	
	public Collection getLastResult(QueryBudgetInfo info,Collection collA,Collection collB){
		Collection lastresultCol=new ArrayList();
		QBudget budget=new QBudget();
		Collection resultCol=new ArrayList();
		BigDecimal persent0=new BigDecimal(0);
		BigDecimal persent1=new BigDecimal(0);
		//对两个集合进行处理得到需要的数据
		try{
			if(!collA.isEmpty()){
				for(Iterator iter=collA.iterator();iter.hasNext();){	
					boolean canadd=true;
					QBudgetResultInfo resu=(QBudgetResultInfo)iter.next();
					QBudgetResultInfo lastInfo=new QBudgetResultInfo();
					if(!collB.isEmpty()){
						for(Iterator iter1=collB.iterator();iter1.hasNext();){
							QBudgetResultInfo resu1=(QBudgetResultInfo)iter1.next();
							//如果项目一样，则计算要求显示的数据，并加到集合中
							if(resu.getItemID()==resu1.getItemID()){
								lastInfo.setItemID(resu.getItemID());//项目ID
								lastInfo.setBudgetAmount(resu.getBudgetAmount());//区间A预算金额
								lastInfo.setExecuteAmout(resu.getExecuteAmout());//区间A执行金额
								lastInfo.setBBudgetAmount(resu1.getBBudgetAmount());//区间B预算金额
								lastInfo.setBExecuteAmout(resu1.getBExecuteAmout());//区间B执行金额
								lastInfo.setDiffBudgetAmount(resu.getBudgetAmount()-resu1.getBBudgetAmount());//预算差额
								lastInfo.setDiffExecuteAmount(resu.getExecuteAmout()-resu1.getBExecuteAmout());//执行差额
								if(resu.getBudgetAmount()!=0){
									persent0= (new BigDecimal(lastInfo.getDiffBudgetAmount()).divide(new BigDecimal(resu.getBudgetAmount()),5,3)).multiply(new BigDecimal(100).divide(new BigDecimal(1),5,4));
									lastInfo.setDiffBudgetAmountPercent(persent0.doubleValue());	//预算增长率													
								}
								if(resu.getExecuteAmout()!=0){									
									persent1= (new BigDecimal(lastInfo.getDiffExecuteAmount()).divide(new BigDecimal(resu.getExecuteAmout()),5,3)).multiply(new BigDecimal(100).divide(new BigDecimal(1),5,4));
									lastInfo.setDiffExecuteAmountPercent(persent1.doubleValue());//执行增长率
								}
								lastresultCol.add(lastInfo);
								iter1.remove() ;
								canadd=false;
								break;								
							}							
						}						
					}
					//如果集合B中没有和A相同的项目，则直接把A集合的项目加进去
					if(canadd){						
						lastresultCol.add(resu);			
					}	
				}				
			}
			//如果集合B中有除A集合外的项目，则把项目加到集合中
			if(!collB.isEmpty()){
				for(Iterator iter1=collB.iterator();iter1.hasNext();){
					QBudgetResultInfo resu1=(QBudgetResultInfo)iter1.next();
					QBudgetResultInfo unit=new QBudgetResultInfo();
					unit.setItemID(resu1.getItemID());
					unit.setBBudgetAmount(resu1.getBBudgetAmount());
					unit.setBExecuteAmout(resu1.getBExecuteAmout());
					lastresultCol.add(unit);								
				}				
			}		
			//调用模板显示方式
			//if(!lastresultCol.isEmpty()){
				resultCol=budget.getTemplateInfo(info,lastresultCol);				
			//}
		}catch(Exception ex){ex.printStackTrace();}
		return resultCol;
	}
	/**
	 * 项目的模板处理，父项目和子项目的折叠
	 * @param budgetInfo
	 * @param coll
	 * @return
	 * @throws BudgetException
	 */
	public Collection getTemplateInfo(QueryBudgetInfo budgetInfo,Collection coll)throws BudgetException
    {
        log.print("enter method :Budget_PlanDAO.getTemplateInfo");
        log.print(UtilOperation.dataentityToString(budgetInfo));
        Vector vector = new Vector();
        DisplayTemplateInfo info = null;
        String str="";
        
        try {
            this.initDAO();
            String strSQL = "select b.* from Budget_ItemPrivilege a, Budget_Templet b where a.budgetClientID=? and a.budgetSystemID=? and a.statusID=? and a.budgetItemID = b.id order by b.itemno";
            log.print(strSQL);
            log.print("clientID="+budgetInfo.getClientID());
            log.print("budgetSystemID="+budgetInfo.getBudgetSystemID());
            transPS = prepareStatement(strSQL);
            transPS.setLong(1,budgetInfo.getClientID());
            transPS.setLong(2,budgetInfo.getBudgetSystemID());
            transPS.setLong(3,Constant.RecordStatus.VALID);
            transRS = executeQuery();
            while (transRS.next()){
                info =  new DisplayTemplateInfo();
                ArrayList detailList  = new ArrayList();  
                String panduan="0";
                info.setIsLeaf(transRS.getLong("isLeaf"));
            	info.setItemID(transRS.getLong("id"));
            	info.setItemLevel(transRS.getLong("itemLevel"));
            	info.setItemName(transRS.getString("itemName"));								
            	info.setItemNo(transRS.getString("itemNo"));
            	if (transRS.getLong("parentItemID") > 0)
            	    info.setParentItemID(transRS.getLong("parentItemID"));            	
            		for(Iterator iter=coll.iterator();iter.hasNext();){
    	            	QBudgetResultInfo result=(QBudgetResultInfo)iter.next(); 
    	            	if(result.getItemID()==transRS.getLong("id")){
		            		str="" +result.getBudgetAmount()+","+result.getExecuteAmout()+","+result.getBBudgetAmount()+","+result.getBExecuteAmout()+"";
		            		str+=","+result.getDiffBudgetAmount()+","+result.getDiffBudgetAmountPercent()+","+result.getDiffExecuteAmount()+","+result.getDiffExecuteAmountPercent()+"";
		            		String[] as = str.split(",");	 
		            		System.out.println(as.length);	
		            		for (int i = 0; i < as.length; i++) {
		            			DisplayTemplateDetailInfo infoChild = new DisplayTemplateDetailInfo(); 	            			   	            				   					
			            		infoChild.setAmount(Double.parseDouble(as[i]));
			            		infoChild.setDisplayValue(DataFormat.formatAmountUseZero(Double.parseDouble(as[i])));
			            		infoChild.setDisplayType(1);
			            		detailList.add(infoChild);	
	    	    				   	            			
		            		} 
		            		panduan="1";
    	            	}
    	            	info.setDetailInfos(detailList);          	      	
    	        }
            		if(panduan.equals("0")){
            			for (int m = 0; m < 8; m++) {
	            			DisplayTemplateDetailInfo infoChild0 = new DisplayTemplateDetailInfo(); 	            			   	            				   					
	            			infoChild0.setAmount(0.0);
	            			infoChild0.setDisplayValue("0.00");
	            			infoChild0.setDisplayType(1);
		            		detailList.add(infoChild0);	
    	    				   	            			
	            		}
            			info.setDetailInfos(detailList); 
            		}
            		
	            vector.add(info);	      
            }
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        return vector;
   }
	
	/**
	 * 根据客户id查询该客户的银行存款
	 * 
	 * @param clientID
	 *            客户id 默认为-1
	 * @param bankType
	 *            银行类型，如果是-1则是查询所有银行的
	 * @param date
	 *            查询日期 默认为当日日期
	 * @param isGroup
	 *            是否集团模式账户 true：是，false：否，如果是集团账户则查询设置隶属关系的账户的余额，如果不是则查询所有账户
	 * @return
	 */
	public  double getBankAccountOfFilialeByClient(int clientID, int bankType,
			Date date, boolean isGroup) {
		
		double temp = 0.0;
		try {
			 this.initDAO();
			StringBuffer strQuerySql = new StringBuffer();
			strQuerySql
					.append(" select sum(MBALANCE)  as sum from sett_balanceofbankaccount where 1=1");
			if (date != null)
				strQuerySql.append(" and to_char(DTCURRENT,'yyyy-MM-dd')='"
						+ DataFormat.formatDate(date,
								DataFormat.FMT_DATE_YYYYMMDD) + "' ");
			else
				strQuerySql.append(" and to_char(DTCURRENT,'yyyy-MM-dd')='"
						+ DataFormat.formatDate(Env.getSystemDate()) + "' ");
			strQuerySql
					.append(" and SBANKACCOUNTNO in ( select SBANKACCOUNTNO from sett_bankaccountoffiliale where 1=1 ");
			if(clientID>0)
			strQuerySql.append("and nclientid=" + clientID);
			if(bankType>0)
			strQuerySql.append(" and nbnktype=" + bankType);
			if (isGroup == true)
				strQuerySql.append(" and NUPCLIENTID >0");
			strQuerySql.append(" )");
			log.info(strQuerySql.toString());
			transPS = transConn.prepareStatement(strQuerySql.toString());
			transRS = transPS.executeQuery();
			while (transRS.next()) { 
				temp = transRS.getDouble("sum");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return temp;
	}


	/*public static void main(String[] args)
	{
	    QBudget qBudget = new QBudget();
	    QueryBudgetInfo info = new QueryBudgetInfo();*/
//	    info.setBudgetFlag(1);
//	    info.setInputUserID(1);
//	    info.setStatusID(1);
//	    info.setVersionNo("YS2005Y0703");
//	    info.setBudgetSystemID(1);
	    //info.setClientID(1);
//	    info.setVersionNo("YS2005Y0714");
//	    info.setStartDate(2005-07-01);
//	    info.setEndDate(2005-07-01);
//	    info = dao.findByVersionNo("YS2005Y071401");
	    //System.out.println(info.getId());
	    //System.out.println(info.getVersionNo());
	    //try {
            //Collection c = dao.findByCondition(info);
           // BudgetPlanInfo plan = (BudgetPlanInfo)c.iterator().next();
            //查询编制是否已经提交
            //System.out.println("plan.getStatusID()="+plan.getStatusID());
//	    info.setBudgetPeriodID(2);
//	    info.setStartDate(DataFormat.getDateTime(2005,1,1,1,1,1));
//	    info.setShowColumn(new long[]{BUDGETConstant.BudgetColumnList.CURRENTBUDGET});
            //Collection c = dao.getNewVersionNo(info);
            //System.out.println(dao.getNewVersionNo(info));
            //Collection c = dao.findConstituteByCondition(info);
//	    if (c!= null && c.size() > 0)
//	        System.out.println(c.size());
//        try {
//            dao.add(info);
//        } catch (ITreasuryDAOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        //} catch (ITreasuryDAOException e) {
            // TODO Auto-generated catch block
          //  e.printStackTrace();
       // }
	//}

}
