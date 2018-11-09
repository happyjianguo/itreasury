/*
 * Created on 2005-9-6
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transadjustinterest.dao;

import java.util.Collection;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfoQuery;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfo;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfo;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.DataFormat;

import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author feiye
 * 
 * 账户费用信息DAO
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class AccumulateFeeDao extends SettlementDAO{
	    
	 /**
     * 查找当前信息
     * @param lOfficeID 办事处标识
     * @param lCurrencyID 币种标识
     * @param strAccountNoStart 起始账户号
     * @param strAccountNoEnd 结束账户号
     * @param strContractNoStart 起始合同号
     * @param strContractNoEnd 结束合同号
     * @param strDueBillCodeStart 起始放款通知单号
     * @param strDueBillCodeEnd 结束放款通知单号
     * @param strFixedDepositNoStart 起始定期存单号
     * @param strFixedDepositNoEnd 结束定期存单号
     * @param tsDateStart 起始日期
     * @param tsDateEnd 结束日期
     * @param lPageLineCount,
     * @param lPageNo,
     * @param lOrderParam,
     * @param lDesc 
     */
   
	/**
     * 结算模块--查找(累计费用及利息调整)当日信息(查找表Sett_SubAccount ,不用判断日期)
     * @param AccumulateFeeInfoQuery 
     */
	//需要考一下，是否要加上存款类型ID的值
    public Collection findForm(AccumulateFeeInfoQuery queryCondition) throws SettlementDAOException,ITreasuryDAOException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        Vector v = new Vector();
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        int nIndex = 1;
        try
        {
        	//得到连接
            conn = this.getConnection();
            
            //不用取日期
            strSQL=" select b.id subAccountID,a.Id nAccountID,a.sAccountNO sAccountNo,c.id nDueBillNo, "
            	+"  c.sCode sLetoutRequisitionNo,e.id nContractID,e.sContractCode sContractNo "
            	+" ,d.sName sName ,b.MINTEREST mInterest,b.AL_mCommission fCommissionFee, "
            	+" b.AL_mSuretyFee fSuretyFee,b.AL_mInterestTax fInterestTaxFee , a.nAccountTypeID accountTypeID" 
            	+" from sett_Account a,sett_subAccount b,loan_payForm c,sett_interestRatePlan d, loan_contractform e , sett_accountType f"
            	+" where 1=1 "
            	+" and b.nAccountID=a.id "
            	+" and b.AF_nInterestPlanID=d.id(+) "
            	+" and b.AL_nLoanNoteID=c.id (+) "
            	+" and c.ncontractid = e.id(+) "
				+" and a.nAccountTypeID=f.id "		//账户类型相等
            	+" and f.nAccountGroupID  in ("+SETTConstant.AccountGroupType.CURRENT+","+SETTConstant.AccountGroupType.FIXED+") "
            	//账户组为活期和定期两种类型
            	+"  and a.nStatusID>0 "
            	+"  and b.nStatusID>0 "
            
            	+" and a.NOFFICEID = " + queryCondition.getLOfficeID()	//办事处ID
				+" and a.NCURRENCYID = "+ queryCondition.getLCurrencyID() ;	//币种ID
            
            	System.out.println("得到的办事处ID为:"+queryCondition.getLOfficeID());
            	System.out.println("得到的币种ID为:"+queryCondition.getLOfficeID());
            	
            //账户号
            if (queryCondition.getSAccountNoStartCtrl() != null && queryCondition.getSAccountNoStartCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo >= '" + queryCondition.getSAccountNoStartCtrl() + "'";
            }
            if (queryCondition.getSAccountNoEndCtrl() != null && queryCondition.getSAccountNoEndCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo <= '" + queryCondition.getSAccountNoEndCtrl() + "'";
            }
            //合同号(e)		(定期和活期无合同号限制)
            if (queryCondition.getSContractNoStartCtrl() != null && queryCondition.getSContractNoStartCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode >= '" + queryCondition.getSContractNoStartCtrl() + "'";
            }
            if (queryCondition.getSContractNoEndCtrl() != null && queryCondition.getSContractNoEndCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode <= '" + queryCondition.getSContractNoEndCtrl() + "'";
            }
            //放款通知单号(c)		(定期和活期无合同号限制)
            if (!queryCondition.getSDueBillCodeStartCtrl().equals(""))	
            {
                //strSQL = strSQL + " and c.sCode>='" + queryCondition.getSDueBillCodeStartCtrl() + "'";
            }
            if (!queryCondition.getSDueBillCodeEndCtrl().equals(""))
            {
                //strSQL = strSQL + " and c.sCode<='" + queryCondition.getSDueBillCodeEndCtrl() + "'";
            }
          
            ps = conn.prepareStatement(strSQL);
            
    		Log.print("findForm SQL : " + strSQL);
            rs = ps.executeQuery();
            Log.print("!!!!!!!!!!!!!!!!!! after execute query!!!!!!!!!!!!!!!!!!!!!");
            while (rs.next())		//将查询得到的结果封装到AccumulateFeeInfo信息表中，以借页面显示用
            {
                AccumulateFeeInfo accumulateFeeInfo = new AccumulateFeeInfo();

                accumulateFeeInfo.setLID(rs.getLong("subAccountID"));			//子账户ID
                Log.print("bbb");
                accumulateFeeInfo.setLAccountID(rs.getLong("nAccountID"));		//账户ID	
                accumulateFeeInfo.setLContractID(rs.getLong("nContractID"));	//合同ID
                accumulateFeeInfo.setLDueBillID(rs.getLong("nDueBillNo"));		//放款通知单ID
                Log.print("eee");
                //accumulateFeeInfo.m_lTypeID = rs.getLong("nTypeID");	//不知道需不需要要
                Log.print("fff");
                accumulateFeeInfo.setSAccountNo(rs.getString("sAccountNo"));		//账户编号
                accumulateFeeInfo.setSContractNo(rs.getString("sContractNo"));	//合同号
    		    accumulateFeeInfo.setSLetoutRequisitionNo(rs.getString("sLetoutRequisitionNo"));	//放款通知单号	
                //accumulateFeeInfo.m_tsOpen = rs.getTimestamp("dtInfo");
    		    			//得到日期的上一天
    			//accumulateFeeInfo.m_tsOpen = Function.getPreviousDate(tsDateStart);		//结束日期
    		    accumulateFeeInfo.setTsOpen(DataFormat.getPreviousDate(queryCondition.getTsDateStart()));  //结束日期
    		    //accumulateFeeInfo.setTsOpen(rs.getTimestamp("clearDate"));		//结束日期

    		    accumulateFeeInfo.setDInterest(rs.getDouble("mInterest"));				//累计利息
                accumulateFeeInfo.setSInterestRatePlanName(rs.getString("sName"));		//利息计划
                accumulateFeeInfo.setDCommission(rs.getDouble("fCommissionFee"));	//累计手续费
                accumulateFeeInfo.setDSuretyFee(rs.getDouble("fSuretyFee"));			//累计担保费
                accumulateFeeInfo.setDInterestTax(rs.getDouble("fInterestTaxFee"));	//累计利息税费
                
                //得到存款类型状态
                accumulateFeeInfo.setLDepositTypeID(rs.getLong("accountTypeID"));		//从sett_account表中取字段nAccountTypeID的值
                System.out.println("===得到账户类型为:"+accumulateFeeInfo.getLDepositTypeID());
                //添加到集合里
                v.add(accumulateFeeInfo);
            }
        }
        catch(SQLException sqle){
        	Log.print("出现了SQL异常");
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(rs);
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }
        return (v.size() > 0 ? v : null);
    }
    
    /**
     * 结算模块--查找(累计费用及利息调整)历史信息(查找表Sett_DailyAccount表，要判断时间)
     * @param AccumulateFeeInfoQuery 
     */
    public Collection findFormHistory(AccumulateFeeInfoQuery queryCondition) throws SettlementDAOException,ITreasuryDAOException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        Vector v = new Vector();
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        int nIndex = 1;
        try
        {
        	//得到连接
            conn = this.getConnection();
            //要取日期   （得到子账户ID以供日后只需要根据此项修改子账户及账户每日余额的调整值记录）
            strSQL=" select b.nSubAccountID subAccountID,a.Id nAccountID,a.sAccountNO sAccountNo"
            +",-1 nDueBillNo, '' sLetoutRequisitionNo,-1 nContractID,'' sContractNo"
            +",d.sName sName, a.nAccountTypeID accountTypeID ,b.dtDate dtDate"
            +", b.MINTEREST mInterest"
            +",0 fCommissionFee"
            +",0 fSuretyFee"
            +",0 fInterestTaxFee"
            +" from   sett_Account a,SETT_DAILYACCOUNTBALANCE b,sett_subAccount c,sett_interestRatePlan d ,sett_accountType f"
            +" where 1=1 "
            +" and b.nAccountID=a.id "
            +" and b.nSubAccountID=c.id "
            +" and c.AF_nInterestPlanID=d.id(+) "
            +" and a.nAccountTypeID=f.id " 		//账户类型相等						//定期和活期
            +" and f.nAccountGroupID  in ("+SETTConstant.AccountGroupType.CURRENT+","+SETTConstant.AccountGroupType.FIXED+") "
            +" and b.nAccountStatusID >0 "
            +" and b.nSubAccountStatusID >0 "
			
            +" and a.NOFFICEID = " + queryCondition.getLOfficeID()			//办事处ID
            +" and a.NCURRENCYID = "+ queryCondition.getLCurrencyID() ;		//币种ID
            
            System.out.println("得到的办事处ID为:"+queryCondition.getLOfficeID());
            System.out.println("得到的币种ID为:"+queryCondition.getLOfficeID());
            
            //账户号
            if (queryCondition.getSAccountNoStartCtrl() != null && queryCondition.getSAccountNoStartCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo >= '" + queryCondition.getSAccountNoStartCtrl() + "'";
            }
            if (queryCondition.getSAccountNoEndCtrl() != null && queryCondition.getSAccountNoEndCtrl().length() > 0)
            {
                strSQL = strSQL + " and a.sAccountNo <= '" + queryCondition.getSAccountNoEndCtrl() + "'";
            }
            
            //合同号(还没处理 e)	定期和活期不作处理
            if (queryCondition.getSContractNoStartCtrl() != null && queryCondition.getSContractNoStartCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode >= '" + queryCondition.getSContractNoStartCtrl() + "'";
            }
            if (queryCondition.getSContractNoEndCtrl() != null && queryCondition.getSContractNoEndCtrl().length() > 0)
            {
                //strSQL = strSQL + " and e.sContractCode <= '" + queryCondition.getSContractNoEndCtrl() + "'";
            }
            //放款通知单号(c)			定期和活期不作处理
            if (!queryCondition.getSDueBillCodeStartCtrl().equals(""))	
            {
                //strSQL = strSQL + " and c.sCode>='" + queryCondition.getSDueBillCodeStartCtrl() + "'";
            }
            if (!queryCondition.getSDueBillCodeEndCtrl().equals(""))
            {
                //strSQL = strSQL + " and c.sCode<='" + queryCondition.getSDueBillCodeEndCtrl() + "'";
            }
            
            //结息日期
            if (queryCondition.getTsDateStart()!=null)	
            {
                strSQL = strSQL + " and b.dtDate >=  to_date('" + DataFormat.getDateString( queryCondition.getTsDateStart() ) + "','yyyy-mm-dd')";
            }
            if (queryCondition.getTsDateEnd()!=null)	
            {
                strSQL = strSQL + " and b.dtDate <= to_date('" + DataFormat.getDateString( queryCondition.getTsDateEnd() ) + "','yyyy-mm-dd')";
            }
            
            //to_date('2005-09-08','yyyy-mm-dd');
          
            ps = conn.prepareStatement(strSQL);
            
    		Log.print("findForm SQL : " + strSQL);
            rs = ps.executeQuery();
            Log.print("!!!!!!!!!!!!!!!!!! after execute query!!!!!!!!!!!!!!!!!!!!!");
            while (rs.next())		//将查询得到的结果封装到AccumulateFeeInfo信息表中，以借页面显示用
            {
                AccumulateFeeInfo accumulateFeeInfo = new AccumulateFeeInfo();

                accumulateFeeInfo.setLID(rs.getLong("subAccountID"));			//子账户ID
                Log.print("bbb");
                accumulateFeeInfo.setLAccountID(rs.getLong("nAccountID"));		//账户ID	
                accumulateFeeInfo.setLContractID(rs.getLong("nContractID"));	//合同ID
                accumulateFeeInfo.setLDueBillID(rs.getLong("nDueBillNo"));		//放款通知单ID
                Log.print("eee");
                //accumulateFeeInfo.m_lTypeID = rs.getLong("nTypeID");	//不知道需不需要要
                Log.print("fff");
                accumulateFeeInfo.setSAccountNo(rs.getString("sAccountNo"));		//账户编号
                accumulateFeeInfo.setSContractNo(rs.getString("sContractNo"));	//合同号
    		    accumulateFeeInfo.setSLetoutRequisitionNo(rs.getString("sLetoutRequisitionNo"));	//放款通知单号	
                //accumulateFeeInfo.m_tsOpen = rs.getTimestamp("dtInfo");
    		    			//得到日期的下一天
    			//accumulateFeeInfo.m_tsOpen = Function.getPreviousDate(tsDateStart);		//结束日期
    		    //accumulateFeeInfo.setTsOpen(DataFormat.getNextDate(queryCondition.getTsDateStart()));		//结束日期
    		    accumulateFeeInfo.setTsOpen(rs.getTimestamp("dtDate"));		//结束日期

    		    accumulateFeeInfo.setDInterest(rs.getDouble("mInterest"));				//累计利息
                accumulateFeeInfo.setSInterestRatePlanName(rs.getString("sName"));		//利息计划
                accumulateFeeInfo.setDCommission(rs.getDouble("fCommissionFee"));	//累计手续费
                accumulateFeeInfo.setDSuretyFee(rs.getDouble("fSuretyFee"));			//累计担保费
                accumulateFeeInfo.setDInterestTax(rs.getDouble("fInterestTaxFee"));	//累计利息税费
                
                //得到存款类型状态
                accumulateFeeInfo.setLDepositTypeID(rs.getLong("accountTypeID"));		//从sett_account表中取字段nAccountTypeID的值
                System.out.println("===得到账户类型为:"+accumulateFeeInfo.getLDepositTypeID());
                //添加到集合里
                v.add(accumulateFeeInfo);
            }
        }
        catch(SQLException sqle){
        	Log.print("出现了SQL异常");
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(rs);
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }
        return (v.size() > 0 ? v : null);
    }
    
    //更新账户每日余额（复核用)
    public long updateDailyAccountBlance(AdjustInterestInfo adjustInfo) throws SettlementDAOException,ITreasuryDAOException{
    	long ret = 0;	//-1:失败  0:正常
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //查询串
		ResultSet rs = null;
		
		double interestValue=0;	//利息调整值
		if(adjustInfo.getLAddOrReduce()==1)	//正值
			interestValue=adjustInfo.getDInterest();
		else
			interestValue=adjustInfo.getDInterest()*(-1);
		
		try
		{
			System.out.println("*****************检查有没有要求更新的利息明细记录可供使用（复核用)******************");
			//生成SQL语句
			buffer.append(" select * from SETT_DAILYACCOUNTBALANCE ");
			
			//根据ID值
			buffer.append(" where  NSUBACCOUNTID = ? ");			//根据ID值
			buffer.append(" and  nAccountStatusID > 0 ");		//账户状态>0
			buffer.append(" and  nSubAccountStatusID > 0 ");	//子账户状态>0
			buffer.append(" and DTDATE >= to_date('" + DataFormat.getDateString( adjustInfo.getTsExecute() ) + "','yyyy-mm-dd') ");

	        //初始化数据库资源
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			//
			System.out.println("需要更新的子账户的ID号为:"+adjustInfo.getLSubAccountID());
			pstmt.setLong(1, adjustInfo.getLSubAccountID());	//根据ID值
			//打印SQL语句
			System.out.println("select ->strSQL:"+buffer.toString());
			rs=pstmt.executeQuery();
			if(!rs.next()){
				//如果没有查到记录，就退出此方法
				System.out.println("===========:没有查找需要的更新的记录!");
				ret = -100;
				return ret;
			}
			
			System.out.println("*****************开始更新账户每日余额（复核用)******************");
			//生成SQL语句
			buffer.setLength(0);
			buffer.append(" update SETT_DAILYACCOUNTBALANCE set \n");
			buffer.append(" MINTEREST = MINTEREST + ? "); 			//更新当日的累计余额信息
			
			//根据ID值
			buffer.append(" where  NSUBACCOUNTID = ? ");			//根据ID值
			buffer.append(" and  nAccountStatusID > 0 ");		//账户状态>0
			buffer.append(" and  nSubAccountStatusID > 0 ");	//子账户状态>0
			buffer.append(" and DTDATE >= to_date('" + DataFormat.getDateString( adjustInfo.getTsExecute() ) + "','yyyy-mm-dd') ");
			
	        //初始化数据库资源
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//开始设置值到SQL里面
			int nIndex = 1; 
			//
			pstmt.setDouble(nIndex++, interestValue);	//利息调整值
			//
			System.out.println("需要更新的子账户的ID号为:"+adjustInfo.getLSubAccountID());
			pstmt.setLong(nIndex++, adjustInfo.getLSubAccountID());	//根据ID值
			//打印SQL语句
			System.out.println("update->strSQL:"+buffer.toString());
			pstmt.executeUpdate();
			
			System.out.println("*****************结束更新账户每日余额（复核用)*****************");
		}
	    catch(SQLException sqle){
	    	ret = -1;
	    	sqle.printStackTrace();
	    }
	    finally
	    {
	    	try
			{
	    		System.out.println("*****************开始清除所用的JDBC资源!*****************");
	    		this.cleanup(rs);
	    		this.cleanup(pstmt);
	    		this.cleanup(conn);
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
	    }
		return ret;
	}
    
    //更新子账户(复核用)
    //这里面出现一个问题，当用户先择的时候，默认的是开机日期，但是如果开机日期和当天的日期不一样时，用户输入开机日期以后当天日期以前的日期就不
    //应该列新他的利息调整值
    public long updateSubAccount(AdjustInterestInfo adjustInfo) throws SettlementDAOException,ITreasuryDAOException{
    	long ret = 0;	//-1:失败  0:正常
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //查询串
		
		double interestValue=0;	//利息调整值
		if(adjustInfo.getLAddOrReduce()==1)	//正值
			interestValue=adjustInfo.getDInterest();
		else
			interestValue=adjustInfo.getDInterest()*(-1);
		
		try
		{
			System.out.println("*****************开始更新子账户利息余额（复核用)******************");
			//生成SQL语句
			buffer.append(" update sett_subAccount set \n");
			buffer.append(" MINTEREST = MINTEREST + ? "); 			//更新当日的累计余额信息
			
			//根据ID值
			buffer.append(" where  id = ? ");			//根据ID值
			buffer.append(" and  NSTATUSID != 0 ");		//子账户状态>0
			
			//buffer.append(" and DTDATE <= to_date('" + DataFormat.getDateString( adjustInfo.getTsExecute() ) + "','yyyy-mm-dd') ");
			
	        //初始化数据库资源
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//开始设置值到SQL里面
			int nIndex = 1; 
			//
			pstmt.setDouble(nIndex++, interestValue);	//利息调整值
			//
			System.out.println("需要更新的子账户的ID号为:"+adjustInfo.getLSubAccountID());
			pstmt.setLong(nIndex++, adjustInfo.getLSubAccountID());	//根据ID值
			//打印SQL语句
			System.out.println("update->strSQL:"+buffer.toString());
			pstmt.executeUpdate();
			
			System.out.println("*****************结束更新更新子账户利息余额（复核用)*****************");
		}
	    catch(SQLException sqle){
	    	ret = -1;
	    	sqle.printStackTrace();
	    }
	    finally
	    {
	    	try
			{
	    		this.cleanup(pstmt);
	    		this.cleanup(conn);
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
	    }
		return ret;
    }
   
}