/*
 * Created on 2003-10-7
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.loan.loanapply.dao;

/**
 * @author 赵国栋
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.loanapply.dataentity.*;
import java.util.*;
import java.sql.Timestamp;
public class LoanRepayPlanDao
{
    private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
    
    private void cleanup(ResultSet rs) throws SQLException
    {
        try
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
        }catch (SQLException sqle){ }
    }
    private void cleanup(CallableStatement cs) throws SQLException
    {
        try
        {
            if (cs != null)
            {
                cs.close();
                cs = null;
            }
        }catch (SQLException sqle){ }
     }
     private void cleanup(PreparedStatement ps) throws SQLException
     {
        try
        {
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
         }catch (SQLException sqle){}
     }
     private void cleanup(Connection con) throws SQLException
     {
        try
        {
            if (con != null)
            {
                con.close();
                con = null;
            }
         }catch (SQLException sqle){ }
    }
    
    public long insert(LoanPlanInfo pInfo) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        long    planID=-1;
        long    loanID=pInfo.getLoanID();
        long    planVersion=pInfo.getPlanVersion();
        long    inputUserID=pInfo.getInputUserID();
        long    isUsed=pInfo.getIsUsed();
        long    userType=pInfo.getUserType();
        
        if ( pInfo==null )
        {
            log.info("LoanPlanInfo to add is null");
            return -1;
        }

        try
        {
            conn=Database.getConnection();
            /* 首先获得该版本的ID */
            strSQL="select nvl(max(ID)+1,1) nID from loan_LoanFormPlan";
            ps=conn.prepareStatement(strSQL);
            rs=ps.executeQuery();
            if ( rs.next() )
            {
                planID=rs.getLong("nID");
            }
            cleanup(rs);
            cleanup(ps);
            if ( planID<0 )
            {
                log.info("Get Next ID Error when Insert a loanPlan Version");
                return -1;
            }
            /* 执行插入操作*/
            strSQL="Insert into loan_LoanFormPlan("
                +"ID, nLoanID, nPlanVersion, nInputUserID, dtInput,nNextCheckUserID, "
                +"nStatusID, sReason, nIsUsed, nUserType "
                +") values(?, ?, 1, ?, SYSDATE, -1, ?, '', ?, ? )";
            ps=conn.prepareStatement(strSQL);
            int n=1;
            ps.setLong(n++,planID);
            ps.setLong(n++,loanID);
            ps.setLong(n++,inputUserID);
            ps.setLong(n++,Constant.RecordStatus.VALID);
            ps.setLong(n++,isUsed);
            ps.setLong(n++,userType);
            lResult=ps.executeUpdate();
            cleanup(ps);
            cleanup(conn);
            if ( lResult<0 )
            {
                log.info("Insert a loan plan version error:"+lResult);
                return -1;
            }
            else
            {
                return planID;
            }
                            
        }
        catch (Exception e)
        {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            e.printStackTrace();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
        }
    }
    /**
     * 删除该计划版本的详细计划信息
     * @param vID   版本的ID
     * @return  成功返回1，错误返回-1
     */
    public long delete(long lID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        long  vID=-1;
        
        try 
        {
        	if ( lID<=0 )
        	{
        		log.info("The plan ID is"+lID);
        	}
			conn = Database.getConnection ();
			strSQL="select ID from loan_LoanFormPlan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lID);
			rs=ps.executeQuery();
			if ( rs.next() )
			{
				vID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			
            strSQL="delete from loan_LoanFormPlanDetail where nPlanID=?";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,vID);
            lResult=ps.executeUpdate();
            cleanup(ps);
            cleanup(conn);

        }catch(Exception e){
            //e.printStackTrace ();
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
            throw e;
        }finally{
        	cleanup(rs);
        	cleanup(ps);
        	cleanup(conn);            
        }
        return lResult;
    }
    public long findVersionByLoanID(long lLoanID) throws Exception
    {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;

		long planID=-1;

		if ( lLoanID==-1 )
		{
			log.info("lLoanID:-1,Can't find planversion!");
			return 0;
		}

		try
		{
			conn=Database.getConnection();
			/*首先获得该loanID对应的计划版本*/
			strSQL="select ID from loan_LoanFormPlan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lLoanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+lLoanID);
				return 0; 
			}
		
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return planID;
    }
	public long findLoanIDByPlanVersion(long planID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;

		long loanID=-1;

		try
		{
			conn=Database.getConnection();
			/*首先获得该loanID对应的计划版本*/
			strSQL="select nLoanID from loan_LoanFormPlan where ID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,planID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				loanID=rs.getLong("nLoanID");
			}
			cleanup(rs);
			cleanup(ps);
			if( loanID<0 )
			{
				cleanup(conn);
				log.info("can not find loanID for planVersion:"+planID);
				return 0; 
			}
		
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return loanID;
	}

    public double findSumPlanAmount(long loanID,long payRepay) throws Exception 
    {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
		long planID=-1;
		double payAmount=0;
			
		try
		{
			conn=Database.getConnection();
			/*首先获得该loanID对应的计划版本*/
			strSQL="select ID from loan_LoanFormPlan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,loanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+loanID);
				return 0; 
			}
			strSQL="select sum(mAmount) from loan_LoanFormPlanDetail where nPlanID=? and nPayTypeID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,planID);
			ps.setLong(2,payRepay);
			
			rs=ps.executeQuery();
			if ( rs.next())
			{
				payAmount=rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		}
		return payAmount;
		
    }
    public long findCountByLoanID(long lLoanID) throws Exception
    {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
		long planID=-1;
		if ( lLoanID==-1 )
		{
			log.info("lLoanID:-1,Can't find planDetailCount!");
			return 0;
		}

		try
		{
			conn=Database.getConnection();
			/*首先获得该loanID对应的计划版本*/
			strSQL="select ID from loan_LoanFormPlan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lLoanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+lLoanID);
				return 0; 
			}
			/*获得详细计划的个数*/
			strSQL="select count(*) from loan_LoanFormPlanDetail where nPlanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,planID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				lResult=rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return lResult;
    }
    public Timestamp findDefaultStartDate(long lLoanID) throws Exception
    {
    	Timestamp startDate=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long  lResult=-1;
		long    planID=-1;
		
		try
		{
			conn=Database.getConnection();
			/*首先获得该loanID对应的计划版本*/
			strSQL="select ID from loan_LoanFormPlan where nLoanID=?";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,lLoanID);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				planID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			if( planID<0 )
			{
				cleanup(conn);
				log.info("can not find plan version for loanID:"+lLoanID);
				return null; 
			}
			
			strSQL="select * from loan_LoanFormPlanDetail where nPlanID= "+planID
				+" and nPayTypeID="+LOANConstant.PlanType.PAY
				+" order by DTPlanDate asc";
			ps=conn.prepareStatement(strSQL);
			rs=ps.executeQuery();
			if ( rs.next())
			{
				startDate=rs.getTimestamp("dtPlanDate");
			}				
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			//e.printStackTrace();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}			    	
    	return startDate;
    }
    public Collection findByLoanID(long lLoanID,long lPageNo, long lPageLine,long lOrderParam,long lDesc) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        Vector v=null;
        long    planID=-1;
        if ( lLoanID<=0 )
        {
            log.info("find repay Plan detail fail:loanID<0");
            return null;
        }
        
        
        try
        {
            conn=Database.getConnection();
            /*首先获得该loanID对应的计划版本*/
            strSQL="select ID from loan_LoanFormPlan where nLoanID=?";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,lLoanID);
            rs=ps.executeQuery();
            if ( rs.next())
            {
                planID=rs.getLong("ID");
            }
            cleanup(rs);
            cleanup(ps);
            if( planID<0 )
            {
                cleanup(conn);
                log.info("can not find plan version for loanID:"+lLoanID);
                return null; 
            }
            
            /*组织查询条件*/
            strSQL="select * from loan_LoanFormPlanDetail where nPlanID="+planID;
            switch ((int) lOrderParam)
            {
                case 1 : //按计划日期排序
                    strSQL += " order by dtPlanDate ";
                    break;
				case 2 : //按方还款
					strSQL += " order by nPayTypeID ";
					break;    
				case 3 : //按金额
					strSQL += " order by mAmount ";
					break;    
				case 6 : //按修改日期排序
					strSQL += " order by dtModifyDate ";
					break;    
                default :
                    strSQL += " order by nPayTypeID,dtPlanDate";
            }
            System.out.println((int)lOrderParam);
            //判断是升序还是降序，升序是系统默认的，降序是desc
            if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
                strSQL += " desc";
            }
            
            String strSQLRecordCount = "";
            strSQLRecordCount = "select count(*) nCount from (  " + strSQL + ") aa";
            System.out.println(strSQLRecordCount);
            ps = conn.prepareStatement(strSQLRecordCount);
            rs = ps.executeQuery();
            long lRecordCount = 0; //记录数目
            if (rs.next())
            {
                lRecordCount = rs.getLong(1);
            }
            cleanup(rs);
            cleanup(ps);
            
            //计算总页数
            long lPageCount = lRecordCount / lPageLine;
            if ((lRecordCount % lPageLine) != 0)
            {
                lPageCount++;
            }
            //查询记录
            String strSQLRecord = "select * from  ( select aa.*,rownum r from ( " + strSQL;
            strSQLRecord = strSQLRecord + " ) aa ) where r between ? and ? ";
            long lRowNumStart = (lPageNo - 1) * lPageLine + 1;
            long lRowNumEnd = lRowNumStart + lPageLine - 1;
            Log.print(strSQLRecord);
            ps = conn.prepareStatement(strSQLRecord);
            ps.setLong(1, lRowNumStart);
            ps.setLong(2, lRowNumEnd);
            rs = ps.executeQuery();
            v=new Vector();
            
            while (rs.next())
            {
                LoanPlanDetailInfo dInfo=new LoanPlanDetailInfo();
                dInfo.setID(rs.getLong("ID"));
                dInfo.setPlanID(rs.getLong("nPlanID"));
                dInfo.setPlanDate(rs.getTimestamp("dtPlanDate"));
                dInfo.setPayTypeID(rs.getLong("nPayTypeID"));
                dInfo.setAmount(rs.getDouble("mAmount"));
                dInfo.setType(rs.getString("sType"));
                dInfo.setModifyDate(rs.getTimestamp("dtModifyDate"));
                dInfo.setLastExtendID(rs.getLong("nLastExtendID"));
                dInfo.setLastOverdueID(rs.getLong("nLastOverdueID"));
                dInfo.setLastVersionPlanID(rs.getLong("nLastVersionPlanID"));
                dInfo.setInterestAmount(rs.getDouble("MINTERESTAMOUNT"));
                dInfo.setRecognizanceAmount(rs.getDouble("MRECOGNIZANCEAMOUNT"));
                v.add(dInfo);
            }
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
       
        }
        catch (Exception e)
        {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            //e.printStackTrace();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
        }
		return v;
    }
    
    public long  autoSavePlanDetail(AutoPlanInfo apInfo) throws Exception
     {
           //取得贷款的起始时间
           int nNum = 0;  
           double dTotal = 0;
           double dLastTime = 0;
           long dEach = 0;
           long lResult = 0;
           long lVersionID = 0;

           long lLoanID=apInfo.getLLoanID();
           long nPayType=apInfo.getNPayType();
           Timestamp tsPayStart=apInfo.getTsPayStart() ;
           Timestamp tsPayEnd=apInfo.getTsPayEnd() ;
           long nRepayType=apInfo.getNRepayType() ;
           Timestamp tsRepayStart=apInfo.getTsRepayStart(); 
           Timestamp tsRepayEnd=apInfo.getTsRepayEnd();
           long nSourceType=apInfo.getNSourceType() ;

           Connection con = null;
           PreparedStatement ps = null;
           ResultSet rs = null;
           
           LoanRepayPlanDetailDao pdDao=null;
           LoanPlanDetailInfo pdInfo=null;
           try
           {
               con = Database.getConnection();
               StringBuffer sb = new StringBuffer();
               
               //取得申请金额
               sb.append("SELECT mLoanAmount FROM loan_LoanForm WHERE ID = ?");
               ps = con.prepareStatement(sb.toString());
               ps.setLong(1,lLoanID);
               rs = ps.executeQuery();
               if (rs.next())
               {
                   dTotal = rs.getDouble("mLoanAmount");
                   System.out.println("dTotal is " + dTotal);
               }
               cleanup(rs);
               cleanup(ps);
               sb.setLength(0);

               sb.append("select ID from loan_LoanFormPlan where nLoanID=?"); 
               ps = con.prepareStatement(sb.toString());
               ps.setLong(1,lLoanID);
               rs = ps.executeQuery();
               if (rs.next())
               {
                   lVersionID = rs.getLong("ID");
                   System.out.println("lVersionID is " + lVersionID);
               }
               System.out.println("Get version ID"+lVersionID);
               cleanup(rs);
               cleanup(ps);
               cleanup(con);
               sb.setLength(0);
               if ( lVersionID<=0 )
               {
               		log.error("Can not find plan version for apply"+lLoanID);
               		return -1;
               }
 
               int nAdd = 0;
               pdDao=new LoanRepayPlanDetailDao();
               if (nPayType != LOANConstant.PayType.NOTUSE ) 
               {
                   if ( nPayType == LOANConstant.PayType.ONETIME) //一次性放款
                   {
                        pdInfo=new LoanPlanDetailInfo();
                        pdInfo.setID(-1);
                        pdInfo.setPlanID(lVersionID);
                        pdInfo.setPayTypeID(LOANConstant.PlanType.PAY);
                        pdInfo.setPlanDate(tsPayStart);
                        pdInfo.setType("本金");
                        pdInfo.setAmount(dTotal);
						pdInfo.setLastExtendID(-1);
						pdInfo.setLastOverdueID(-1);
						pdInfo.setLastVersionPlanID(-1);
						
                        lResult=pdDao.insert(pdInfo);
                        pdInfo=null;
					 								
                   }
                   else
                   {
                        ArrayList payDateList=new ArrayList();
                        while (!tsPayStart.after(tsPayEnd)|| tsPayStart.toString().substring(1,10).equals(tsPayEnd.toString().substring(1,10)))
                        {
                            payDateList.add(tsPayStart); 
                            nNum++;
                            switch ( (int)nPayType )
                            {
                                case  (int)LOANConstant.PayType.YEAR  :     //年
                                    tsPayStart = DataFormat.getNextMonth(tsPayStart,12);
                                    break;
                                case  (int)LOANConstant.PayType.HALFYEAR  :     //半年
                                    tsPayStart = DataFormat.getNextMonth(tsPayStart,6);
                                    break;
                                case (int)LOANConstant.PayType.QUARTOR  :     //季
                                    tsPayStart = DataFormat.getNextMonth(tsPayStart,3);
                                    break;
                                case  (int)LOANConstant.PayType.MONTH :    //月
                                    tsPayStart = DataFormat.getNextMonth(tsPayStart,1);
                                    break;
                                case  (int)LOANConstant.PayType.WEEK :    //周
                                    tsPayStart = DataFormat.getNextDate(tsPayStart,7);
                                    break;
                                case  (int)LOANConstant.PayType.DAY  :   //日
                                    tsPayStart = DataFormat.getNextDate(tsPayStart,1);
                                    break;
                            }
                        }
                        dEach = (long)dTotal/nNum;
                        dLastTime = dTotal - dEach*(nNum-1);
                        int n=payDateList.size();
                        for ( int i=0;i<n;i++ )
                        {
                            pdInfo=new LoanPlanDetailInfo();
                            pdInfo.setPlanID(lVersionID);
                            pdInfo.setPayTypeID(LOANConstant.PlanType.PAY );
                            pdInfo.setPlanDate((Timestamp)payDateList.get(i));
                            pdInfo.setType("本金");
							pdInfo.setLastExtendID(-1);
							pdInfo.setLastOverdueID(-1);
							pdInfo.setLastVersionPlanID(-1);

                            if ( i==( n-1 ) )
                            {
                                pdInfo.setAmount(dLastTime);
                            }
                            else
                            {
                                pdInfo.setAmount(dEach);
                            }
                            lResult=pdDao.insert(pdInfo);
                            pdInfo=null;
                        }
                   }
               }
               // 放款开始 
               nNum = 0;
               dEach = 0;
               dLastTime = 0;
               if (nRepayType != LOANConstant.RepayType.NOTUSE) 
               {
                   if (nRepayType == LOANConstant.RepayType.ONETIME) //一次性放款
                   {
                       pdInfo=new LoanPlanDetailInfo();
                       pdInfo.setPlanID(lVersionID);
                       pdInfo.setPayTypeID(LOANConstant.PlanType.REPAY );
                       pdInfo.setPlanDate(tsRepayStart);
                       pdInfo.setType("本金");
                       pdInfo.setAmount(dTotal);
						pdInfo.setLastExtendID(-1);
						pdInfo.setLastOverdueID(-1);
						pdInfo.setLastVersionPlanID(-1);

                       lResult=pdDao.insert(pdInfo);
                       pdInfo=null;
                   }
                   else
                   {
                        ArrayList repayDateList=new ArrayList();
                        while (!tsRepayStart.after(tsRepayEnd) || tsRepayStart.toString().substring(1,10).equals(tsRepayEnd.toString().substring(1,10)))
                        {
                            repayDateList.add(tsRepayStart);
                            nNum++;
                            switch ((int)nRepayType)
                            {
                                case (int)LOANConstant.RepayType.YEAR  :     //年
                                    tsRepayStart = DataFormat.getNextMonth(tsRepayStart,12);
                                    break;
                                case (int)LOANConstant.RepayType.HALFYEAR  :     //半年
                                    tsRepayStart = DataFormat.getNextMonth(tsRepayStart,6);
                                    break;
                                case (int)LOANConstant.RepayType.QUARTOR  :     //季
                                    tsRepayStart = DataFormat.getNextMonth(tsRepayStart,3);
                                    break;
                                case  (int)LOANConstant.RepayType.MONTH:    //月
                                    tsRepayStart = DataFormat.getNextMonth(tsRepayStart,1);
                                    break;
                                case  (int)LOANConstant.RepayType.WEEK :    //周
                                    tsRepayStart = DataFormat.getNextDate(tsRepayStart,7);
                                    break;
                                case  (int)LOANConstant.RepayType.DAY  :   //日
                                    tsRepayStart = DataFormat.getNextDate(tsRepayStart,1);
                                    break;
                            }
                        }
                       dEach = (long)dTotal/nNum;
                       dLastTime = dTotal - dEach*(nNum-1);
                       int n=repayDateList.size();
                       for ( int i=0;i<n;i++ )
                       {
                           pdInfo=new LoanPlanDetailInfo();
                           pdInfo.setPlanID(lVersionID);
                           pdInfo.setPayTypeID(LOANConstant.PlanType.REPAY  );
                           pdInfo.setPlanDate((Timestamp)repayDateList.get(i));
                           pdInfo.setType("本金");
						pdInfo.setLastExtendID(-1);
						pdInfo.setLastOverdueID(-1);
						pdInfo.setLastVersionPlanID(-1);

                           if ( i==( n-1 ) )
                           {
                               pdInfo.setAmount(dLastTime);
                           }
                           else
                           {
                               pdInfo.setAmount(dEach);
                           }
                           lResult=pdDao.insert(pdInfo);
                           pdInfo=null;
                       }
                   }
               }
           }
           catch(Exception e)
           {
                e.printStackTrace();
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
                 throw e;
           }finally{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
           }
           return lResult;
        
    }
    
    //自动安排融资租赁租金偿付表
    public long  autoSaveLeaseholdPlanDetail(AutoPlanInfo apInfo) throws Exception
    {
          //取得贷款的起始时间
          int nNum = 0;  
          double dTotal = 0;//本金
          double dLastTime = 0;
          double dRecognizanceAmount = 0;//保证金
          double dRate = 0;//利率
          double dInterestAmount = 0;//利息
          long lInterestCountType = -1;//利息计算方式
          long nPayType = -1;//租金偿还方式
          long lRepayNum = -1;//偿还频率
          long lIntervalNum = -1;//期限
          long dEach = 0;
          long lResult = 0;
          long lVersionID = 0;
          //added by xiong fei 2010/05/19
          long ntypeID = -1;//取得贷款类型
          /*
           * add by yunchang
           * date 2010-08-27 11点26分
           * 增加   租赁物到期残值(融资租赁)
           * 增加   租赁物到期残值(融资租赁)所产生的利息
           */
          double mMatureNominalAmount = 0.0d; 
          double mMatureNominalRateAmount = 0.0d;

          long lLoanID=apInfo.getLLoanID();
          //long nPayType=apInfo.getNPayType();
          Timestamp tsPayStart=apInfo.getTsPayStart() ;
          Timestamp tsPayEnd=apInfo.getTsPayEnd() ;
          //long nRepayType=apInfo.getNRepayType() ;
          //Timestamp tsRepayStart=apInfo.getTsRepayStart(); 
          //Timestamp tsRepayEnd=apInfo.getTsRepayEnd();
          long nSourceType=apInfo.getNSourceType() ;

          Connection con = null;
          PreparedStatement ps = null;
          ResultSet rs = null;
          
          LoanRepayPlanDetailDao pdDao=null;
          LoanPlanDetailInfo pdInfo=null;
          try
          {
              con = Database.getConnection();
              StringBuffer sb = new StringBuffer();
              
              //取得本金、利率、利息计算方式、期限
              sb.append("SELECT * FROM loan_LoanForm where id = "+lLoanID);             
              ps = con.prepareStatement(sb.toString());
              rs = ps.executeQuery();
              if (rs.next())
              {
                  dTotal = rs.getDouble("MLOANAMOUNT");
                  dRate = rs.getDouble("MINTERESTRATE");
                  lInterestCountType = rs.getLong("NINTERESTCOUNTTYPEID");
                  nPayType = rs.getLong("ASSURECHARGETYPEID");
                  lIntervalNum = rs.getLong("NINTERVALNUM");
                  //added by xiong fei 2010/05/18 融资租赁自动生成偿付表处加了日期选择
                  ntypeID = rs.getLong("NTYPEID");
                  /*
                   * add by yunchang
                   * date 2010-08-27 11点26分
                   * 增加   租赁物到期残值(融资租赁)
                   */                  
                  mMatureNominalAmount = rs.getDouble("MMATURENOMINALAMOUNT");
                  
                  if(ntypeID!= LOANConstant.LoanType.RZZL)
                  {
                	  tsPayStart = rs.getTimestamp("DTSTARTDATE");
                  }
                  tsPayEnd = rs.getTimestamp("DTENDDATE");
                  nPayType = rs.getLong("ASSURECHARGETYPEID");
              }
              if (dTotal==0 || tsPayStart==null ||tsPayEnd==null ||nPayType==0 )
            	  return lResult;
              cleanup(rs);
              cleanup(ps);
              sb.setLength(0);
              
              //取得保证金总额
              sb.append(" SELECT sum(b.MAMOUNT) as RecognizanceAmount  FROM loan_LoanForm a, LOAN_LOANFORMASSURE b ");
              sb.append(" where a.id = b.NLOANID and b.NASSURETYPEID = "+ LOANConstant.AssureType.RECOGNIZANCE);  
              sb.append(" and b.NSTATUSID ="+LOANConstant.RecordStatus.VALID+" and a.id = "+ lLoanID);
              ps = con.prepareStatement(sb.toString());
              rs = ps.executeQuery();
              if (rs.next())
              {
            	  //added by xiong fei 2010/05/19 融资租赁里面计划里不扣保证金
                  if(ntypeID!= LOANConstant.LoanType.RZZL)
                  {
                	  dRecognizanceAmount = rs.getDouble("RecognizanceAmount");
                  }
              }
              cleanup(rs);
              cleanup(ps);
              sb.setLength(0);

              sb.append("select ID from loan_LoanFormPlan where nLoanID=?"); 
              ps = con.prepareStatement(sb.toString());
              ps.setLong(1,lLoanID);
              rs = ps.executeQuery();
              if (rs.next())
              {
                  lVersionID = rs.getLong("ID");
                  System.out.println("lVersionID is " + lVersionID);
              }
              System.out.println("Get version ID"+lVersionID);
              cleanup(rs);
              cleanup(ps);
              cleanup(con);
              sb.setLength(0);
              if ( lVersionID<=0 )
              {
              		log.error("Can not find plan version for apply"+lLoanID);
              		return -1;
              }

              int nAdd = 0;
              pdDao=new LoanRepayPlanDetailDao();

                  if ( nPayType == LOANConstant.ChargeRatePayType.ONETIME) //一次性放款
                  {
                       pdInfo=new LoanPlanDetailInfo();
                       pdInfo.setID(-1);
                       pdInfo.setPlanID(lVersionID);
                       pdInfo.setPayTypeID(LOANConstant.PlanType.PAY);
                       pdInfo.setPlanDate(tsPayStart);
                       pdInfo.setType("本金");
                       pdInfo.setAmount(dTotal);//本金
                       pdInfo.setLastExtendID(-1);
                       pdInfo.setLastOverdueID(-1);
                       pdInfo.setLastVersionPlanID(-1);
                       
					   pdInfo.setRecognizanceAmount(dRecognizanceAmount); //保证金
                       pdInfo.setInterestAmount((dTotal * dRate / 12 / 100) * lIntervalNum);//利率
				
                       lResult=pdDao.insert(pdInfo);
                       pdInfo=null;
					 								
                  }
                  else
                  { 
                       ArrayList payDateList=new ArrayList();
                    
                     //added by xiong fei 2010/05/28 原来的逻辑是合同结束日期可以做最后一期，新的逻辑里最后一期不能是合同结束日期
                       //modify by zwxiao 2010-06-28 
                       Timestamp tsStart = tsPayStart;
                       int count = 0;//循环次数
                       while (!tsPayStart.after(tsPayEnd)&&! tsPayStart.toString().substring(1,10).equals(tsPayEnd.toString().substring(1,10)))
                       {                          
                           nNum++;
                           switch ( (int)nPayType )
                           {
                           //added by xiong fei 2010/05/24 下面增加了控制，让安排的租金偿付表能把当月作为第一期
                               case  (int)LOANConstant.ChargeRatePayType.YEAR  :     //年
                                   if(nNum!=1){
                            	   tsPayStart = DataFormat.getNewNextMonth(tsStart,12*count);
                                   }
                            	   lRepayNum = 12;
                               	   break;
                               case  (int)LOANConstant.ChargeRatePayType.HALFYEAR  :     //半年
                                   if(nNum!=1){
                            	   tsPayStart = DataFormat.getNewNextMonth(tsStart,6*count);
                                   }
                                   lRepayNum = 6;
                                   break;
                               case (int)LOANConstant.ChargeRatePayType.QUARTER  :     //季
                            	   if(nNum!=1){
                                   tsPayStart = DataFormat.getNewNextMonth(tsStart,3*count);
                            	   }
                                   lRepayNum = 3;    
                                   break;
                               case  (int)LOANConstant.ChargeRatePayType.MONTH :    //月
                            	   if(nNum!=1){
                                   tsPayStart = DataFormat.getNewNextMonth(tsStart,1*count);
                            	   }
                               	   lRepayNum = 1;    
                               	   break;
                           }
                           //added by xiong fei 2010/05/28 原来的逻辑是合同结束日期可以做最后一期，新的逻辑里最后一期不能是合同结束日期
                           if(!tsPayStart.after(tsPayEnd)&& !tsPayStart.toString().substring(1,10).equals(tsPayEnd.toString().substring(1,10)))
                           {	   
                        	   payDateList.add(tsPayStart); 
                           }
                           count++;
                       }
                       dEach = (long)dTotal/payDateList.size();
                       dLastTime = dTotal - dEach*(payDateList.size()-1);
                       int n=payDateList.size();
                       
                       //add by zwxiao 2010-06-20 之前计算的等额本息的金额是错误
						double payment_interest_rate = dRate*lRepayNum/12/100; 
						/*
		                 * add by yunchang
		                 * date 2010-08-27 11点26分
		                 * 增加   租赁物到期残值(融资租赁) 每期所产生的利息
		                 */
						mMatureNominalRateAmount = mMatureNominalAmount * payment_interest_rate;
						//等本每次还款的金额，包含有本金和利息
					    //double principalTemp = ((1 - payment_interest_rate) * dTotal) / (payment_interest_rate * (1 - Math.pow(payment_interest_rate,lIntervalNum/lRepayNum))); 
					    /*
					     * add by yunchang
					     * date 2010-09-01
					     * function 残值的利息是单独计算的，所以应该先减去
					     */
						double principalTemp = ( dTotal - mMatureNominalAmount )*payment_interest_rate*Math.pow((1+payment_interest_rate),lIntervalNum/lRepayNum)/(Math.pow((1+payment_interest_rate),lIntervalNum/lRepayNum)-1);
					    /*
					     * add by yunchang
					     * date 2010-08-27 13:27
					     * function 等本每次还款的金额   添加上到期残值所产生的利息
					     */
					    principalTemp = principalTemp + mMatureNominalRateAmount ;
					    //四舍五入
					    principalTemp = DataFormat.formatDouble(principalTemp);
					    double principalsum =0.00;//等额本息使用，累计计算已经计划过的金额金额
					    for ( int i=0;i<payDateList.size();i++ )
                       {
                           	pdInfo=new LoanPlanDetailInfo();
                           	pdInfo.setPlanID(lVersionID);
                           	pdInfo.setPayTypeID(LOANConstant.PlanType.REPAY );
                           	pdInfo.setPlanDate((Timestamp)payDateList.get(i));
                           	pdInfo.setType("本金");							
                           	/*
                           	 * add by yunchang
                           	 * date 2010-09-01
                           	 * function 计算本金的时候应该先减去到期残值
                           	 */
							pdInfo.setAmount(DataFormat.formatDouble((dTotal - mMatureNominalAmount )/lIntervalNum*lRepayNum));//本金
							pdInfo.setRecognizanceAmount(dRecognizanceAmount/lIntervalNum*lRepayNum);//保证金
							
							if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEAMOUNT)//等额
							{
								if(i == payDateList.size() - 1){//如果是最后一期，就特殊处理一下，处理逻辑：1.每期的金额相加为总和为合同金额 2.每期的还款的金额相同 3.如果存在最后剩下几分钱的本期的情况就调整本金在调整利息
									pdInfo.setAmount(dTotal-mMatureNominalAmount-principalsum);
									pdInfo.setInterestAmount(principalTemp - pdInfo.getAmount());
									principalsum += pdInfo.getAmount();
								}else{
									/*
									 * add by yunchang
									 * date 2010-09-01
									 * function 等额本息计算时，添加上每期到期残值所产生的利息
									 */
									double interestAmountEvery = ((dTotal-mMatureNominalAmount-principalsum)*dRate/12/100) * lRepayNum ;
									interestAmountEvery = interestAmountEvery + mMatureNominalRateAmount ;
									pdInfo.setInterestAmount(DataFormat.formatDouble(interestAmountEvery));
									pdInfo.setAmount(principalTemp-pdInfo.getInterestAmount());
									//等额计算公式：（本金*利率/12/100）*（租赁期限（月）/期数）
									principalsum += pdInfo.getAmount();
								}
							}
							else if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEPRINCIPAL)//等本
							{
								double d = n;//new BigDecimal(n-i).divide(new BigDecimal(n),8,BigDecimal.ROUND_HALF_UP).doubleValue()*dTotal * (dRate/12/100) * lRepayNum;
								/*
								 * modify by yunchang
								 * date 2010-08-27 24:03
								 * function 计算利息的时候添加上 融资租赁 到期残值 所产生的利息
								 * pdInfo.setInterestAmount(DataFormat.formatDouble((d-i)/d*dTotal * (dRate/12/100) * lRepayNum));
								 */
								double interestAmountEvery = (d-i)/d*(dTotal-mMatureNominalAmount) * (dRate/12/100) * lRepayNum;
								interestAmountEvery =interestAmountEvery + mMatureNominalRateAmount ;
								pdInfo.setInterestAmount(DataFormat.formatDouble(interestAmountEvery));
								
								System.out.print(" lixi="+pdInfo.getInterestAmount());
								//等本计算方式：（本金－已还本金）*（利率/12/100）* （租赁期限（月）/期数）
							}
							pdInfo.setLastExtendID(-1);
							pdInfo.setLastOverdueID(-1);
							pdInfo.setLastVersionPlanID(-1);

							lResult=pdDao.insert(pdInfo);
							pdInfo=null;
                       }
                  }              
          }
          catch(Exception e)
          {
               e.printStackTrace();
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
                throw e;
          }finally{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
          }
          return lResult;
       
   }
    
    /**added by xiong fei 2010-07-29
     * 获取最新的合同计划
     * @param lLoanID
     * @param lPageNo
     * @param lPageLine
     * @param lOrderParam
     * @param lDesc
     * @return
     * @throws Exception
     */
    public Collection findNewPlanByLoanID(long lLoanID,long lPageNo, long lPageLine,long lOrderParam,long lDesc) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        
        Vector v=null;
        long    planID=-1;
        if ( lLoanID<=0 )
        {
            log.info("find repay Plan detail fail:loanID<0");
            return null;
        }
        
        
        try
        {
            conn=Database.getConnection();
            /*首先获得该loanID对应的计划版本*/
            strSQL="select max(ID) as ID  from loan_loancontractplan where nLoanID=? and  NSTATUSID = 1 and NISUSED = 2";
            ps=conn.prepareStatement(strSQL);
            ps.setLong(1,lLoanID);
            rs=ps.executeQuery();
            if ( rs.next())
            {
                planID=rs.getLong("ID");
            }
            cleanup(rs);
            cleanup(ps);
            if( planID<0 )
            {
                cleanup(conn);
                log.info("can not find plan version for loanID:"+lLoanID);
                return null; 
            }
            
            /*组织查询条件*/
            strSQL="select * from loan_loancontractplandetail where ncontractplanid ="+planID;
            switch ((int) lOrderParam)
            {
                case 1 : //按计划日期排序
                    strSQL += " order by dtPlanDate ";
                    break;
				case 2 : //按方还款
					strSQL += " order by nPayTypeID ";
					break;    
				case 3 : //按金额
					strSQL += " order by mAmount ";
					break;    
				case 6 : //按修改日期排序
					strSQL += " order by dtModifyDate ";
					break;    
                default :
                    strSQL += " order by nPayTypeID,dtPlanDate";
            }
            System.out.println((int)lOrderParam);
            //判断是升序还是降序，升序是系统默认的，降序是desc
            if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
                strSQL += " desc";
            }
            
            String strSQLRecordCount = "";
            strSQLRecordCount = "select count(*) nCount from (  " + strSQL + ") aa";
            System.out.println(strSQLRecordCount);
            ps = conn.prepareStatement(strSQLRecordCount);
            rs = ps.executeQuery();
            long lRecordCount = 0; //记录数目
            if (rs.next())
            {
                lRecordCount = rs.getLong(1);
            }
            cleanup(rs);
            cleanup(ps);
            
            //计算总页数
            long lPageCount = lRecordCount / lPageLine;
            if ((lRecordCount % lPageLine) != 0)
            {
                lPageCount++;
            }
            //查询记录
            String strSQLRecord = "select * from  ( select aa.*,rownum r from ( " + strSQL;
            strSQLRecord = strSQLRecord + " ) aa ) where r between ? and ? ";
            long lRowNumStart = (lPageNo - 1) * lPageLine + 1;
            long lRowNumEnd = lRowNumStart + lPageLine - 1;
            Log.print(strSQLRecord);
            ps = conn.prepareStatement(strSQLRecord);
            ps.setLong(1, lRowNumStart);
            ps.setLong(2, lRowNumEnd);
            rs = ps.executeQuery();
            v=new Vector();
            
            while (rs.next())
            {
                LoanPlanDetailInfo dInfo=new LoanPlanDetailInfo();
                dInfo.setID(rs.getLong("ID"));
                dInfo.setPlanID(rs.getLong("ncontractplanid"));
                dInfo.setPlanDate(rs.getTimestamp("dtPlanDate"));
                dInfo.setPayTypeID(rs.getLong("nPayTypeID"));
                dInfo.setAmount(rs.getDouble("mAmount"));
                dInfo.setType(rs.getString("sType"));
                dInfo.setModifyDate(rs.getTimestamp("dtModifyDate"));
                dInfo.setLastExtendID(rs.getLong("nLastExtendID"));
                dInfo.setLastOverdueID(rs.getLong("nLastOverdueID"));
                dInfo.setLastVersionPlanID(rs.getLong("nLastVersionPlanID"));
                dInfo.setInterestAmount(rs.getDouble("MINTERESTAMOUNT"));
                dInfo.setRecognizanceAmount(rs.getDouble("MRECOGNIZANCEAMOUNT"));
                v.add(dInfo);
            }
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
       
        }
        catch (Exception e)
        {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
            //e.printStackTrace();
            throw e;
        }finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
        }
		return v;
    }

    public static void main(String[] args)
    {
        LoanRepayPlanDao ldao=new LoanRepayPlanDao();
        long ret=-1;
       
        try
        {
            LoanPlanInfo pInfo=new LoanPlanInfo();
            pInfo.setLoanID(1);
            pInfo.setPlanVersion (1);
            pInfo.setInputUserID(1);
            pInfo.setReason("haha");
            pInfo.setNextCheckUserID(1);
            pInfo.setIsUsed(1);
            pInfo.setUserType(1);
            
            AutoPlanInfo apInfo=new AutoPlanInfo();
            apInfo.setLLoanID(89);
            //apInfo.setNPayType(7);
           // apInfo.setNRepayType(7);
            //apInfo.setNSourceType(LOANConstant.WhoChangePlan.LOANAPPLY);
            
            apInfo.setTsPayStart( DataFormat.getDateTime("2006-04-04"));
            apInfo.setTsPayEnd( DataFormat.getDateTime("2008-04-04"));
            apInfo.setTsRepayStart( DataFormat.getDateTime("2003-11-1"));
            apInfo.setTsRepayEnd( DataFormat.getDateTime("2003-12-1"));
            
            
            ldao.autoSaveLeaseholdPlanDetail(apInfo);
            //ret=ldao.insert(pInfo);
            //ret=ldao.findCountByLoanID(125);
            //Vector v=(Vector)ldao.findByLoanID(1,1,10,-1,1);
            //ret=ldao.delete(1);
            //System.out.println(""+v.size());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
