package com.iss.itreasury.loan.integratedCredit.customerfeedback.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.dao.SettlementDAO;

import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.CreditFrameInfo;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanAssureoracceptsynopsis;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanBankliabilitiesdetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanBankrecord;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanCreditgrade;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanCreditgradedetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanExternalliabilities;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceanalyse;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanFinanceitemdetail;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanGroupinsidecontact;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanManageanalyse;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanReceiveaccountage;
import com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity.LoanReceivefundsonaccount;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;


/**
 * @author wxsu
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CustomerfeedbackDao extends SettlementDAO {

	private static Log4j log4j = null;

	public CustomerfeedbackDao() {
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * 保存授信客户评分表属性清单
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertCreditgrade(LoanCreditgrade info)
	throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		String applyCode = "";
		long ID=-1;

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();
			if(info.getId()<0)
			{
			ID = this.getMaxID(8);
//			获得申请书编号
			strSQL = " select to_char(sysdate,'yy') from dual ";
			String sYear = "";
			String curID = "";
			//long lCurID=cInfo.getCurrencyID();
			long lTypeID =12;

//			curID = LOANConstant.LoanType.getTypeCode(lTypeID);
//			curID = LOANNameRef.getSubLoanTypeCodeByID(lTypeID);
			curID = LOANConstant.SubLoanType.getCode(lTypeID);//lSubTypeID TODO

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sYear = rs.getString(1);
			}
			cleanup(rs);
			cleanup(ps);

			log.info("sYear:" + sYear + " curID:" + curID);
			System.out.println("sYear:" + sYear + " curID:" + curID);
			int nLen = curID.length() + sYear.length() + 1;

			strSQL = "select nvl(max(substr(creditcode," + (nLen+1) + ",4)),0)+1 from LOAN_CREDITGRADE where creditcode like 'A" + sYear + curID + "%'";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				long ll = rs.getLong(1);

				if (ll < 10)
				{
					applyCode = "A" + sYear + curID + "00" + ll;
				}
				else if (ll < 100)
				{
					applyCode = "A" + sYear + curID + "0" + ll;
				}
				else
				{
					applyCode = "A" + sYear + curID + ll;
				}

			}
			cleanup(rs);
			cleanup(ps);
			info.setCreditcode(applyCode);
			log.info("Create loan:get applyCode:" + applyCode);
			System.out.println("Create loan:get applyCode:" + applyCode);
			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_CREDITGRADE ("
					+ "id,clientid,creditcode,creditlevel,status, "
					+ "gradedate,inputuserid,creditlevelvalue,officeid,currencyid "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getClientid());
			ps.setString(n++, info.getCreditcode());
			ps.setString(n++, info.getCreditlevel());
			ps.setLong(n++, info.getStatus());
			ps.setTimestamp(n++, info.getGradedate());
			ps.setLong(n++, info.getInputuserid());
			ps.setDouble(n++, info.getCreditlevelvalue());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			

				if (lResult < 0) {
					log.info("insert a loan plan detail fail");
				
				} 
			}else
			{
				// 更新记录
				strSQL = "update LOAN_CREDITGRADE set ";
				if (info.getClientid() > 0)
					strSQL = strSQL + "  clientid=?";
				if (info.getCreditcode()!=null)
					strSQL = strSQL + " ,creditcode=?";
				if (info.getCreditlevel() !=null)
					strSQL = strSQL + " ,creditlevel=?";
				if (info.getStatus() > 0)
					strSQL = strSQL + " ,status=?";
				if (info.getGradedate()!=null)
					strSQL = strSQL + " ,gradedate=?";
				if (info.getInputuserid() > 0)
					strSQL = strSQL + " ,inputuserid=?";
				if (info.getCreditlevelvalue() > 0)
					strSQL = strSQL + " ,creditlevelvalue=?";
				if (info.getOfficeid() > 0)
					strSQL = strSQL + " ,officeid=?";
				
				if (info.getCurrencyid() > 0)
					strSQL = strSQL + " ,currencyid=?";
			

				strSQL = strSQL + " where ID=?";
				int nindex = 1;
				ps = conn.prepareStatement(strSQL);

				if (info.getClientid() > 0)
					ps.setLong(nindex++, info.getClientid());
				if (info.getCreditcode() !=null)
					ps.setString(nindex++, info.getCreditcode());
				if (info.getCreditlevel()!=null)
					ps.setString(nindex++, info.getCreditlevel());
				if (info.getStatus() > 0)
					ps.setLong(nindex++, info.getStatus());
				if (info.getGradedate() !=null)
					ps.setTimestamp(nindex++, info.getGradedate());
				if (info.getInputuserid() > 0)
					ps.setLong(nindex++, info.getInputuserid());
				if (info.getCreditlevelvalue() > 0)
					ps.setDouble(nindex++, info.getCreditlevelvalue());
				if (info.getOfficeid() > 0)
					ps.setLong(nindex++, info.getOfficeid());
				
				if (info.getCurrencyid() > 0)
					ps.setLong(nindex++, info.getCurrencyid());
				

				ps.setLong(nindex++, info.getId());

				
				lResult = ps.executeUpdate();
				if (lResult < 0) {
					log.info("update loan create info error:" + lResult);
				}
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lResult < 0 ? lResult : ID);
	}
	/**
	 * 查找授信客户评分表属性清单
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	 public LoanCreditgrade findByID(long lID)throws Exception
	 {
		 LoanCreditgrade info;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Connection con = null;
	    info = new LoanCreditgrade();
	    try
	    {
	        con = Database.getConnection();
	        StringBuffer sbSQL = new StringBuffer();
	        sbSQL.append(" select * from LOAN_CREDITGRADE");
	        sbSQL.append(" WHERE status !=  0");
	        sbSQL.append(" and id =  " + lID);
	        log.info(sbSQL.toString());
	        ps = con.prepareStatement(sbSQL.toString());
	        rs = ps.executeQuery();
	        if(rs.next())
	        {
	            info.setId(rs.getLong("id"));//ID
	            info.setClientid(rs.getLong("clientid"));//客户ID
	            info.setCreditcode(rs.getString("creditcode"));//信用等级评定编号
	            info.setCreditlevel(rs.getString("creditlevel"));//信用等级
	            info.setStatus(rs.getLong("status"));//状态
	            info.setGradedate(rs.getTimestamp("gradedate"));//评级日期
	            info.setInputuserid(rs.getLong("inputuserid"));//录入人
	            info.setCreditlevelvalue(rs.getDouble("creditlevelvalue"));//录入人
	            info.setOfficeid(rs.getLong("officeid"));//办事处
	            info.setCurrencyid(rs.getLong("currencyid"));//币种
	        }
	        if(rs != null)
	        {
	            rs.close();
	            rs = null;
	        }
	        if(ps != null)
	        {
	            ps.close();
	            ps = null;
	        }
	        if(con != null)
	        {
	            con.close();
	            con = null;
	        }
	    }
	    catch(Exception e)
	    {
	        log.error(e.toString());
	        throw new IException("Gen_E001");
	    }
	    finally
	    {
	        try
	        {
	            if(rs != null)
	            {
	                rs.close();
	                rs = null;
	            }
	            if(ps != null)
	            {
	                ps.close();
	                ps = null;
	            }
	            if(con != null)
	            {
	                con.close();
	                con = null;
	            }
	        }
	        catch(Exception e)
	        {
	            log.error(e.toString());
	            throw new IException("Gen_E001");
	        }
	    }
	    return info;
	}
	/**
	 * 填充客户信用评价休息 （信用级别，信用分数）
	 */
	public long updateCreditgrade(LoanCreditgrade pdInfo)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_CREDITGRADE "
                    +"set creditlevel=? , creditlevelvalue=? ,STATUS=? "
                    +"where ID=?";
        		
        	
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setString(n++,pdInfo.getCreditlevel());
                ps.setDouble(n++,pdInfo.getCreditlevelvalue());
                ps.setLong(n++,pdInfo.getStatus());
                ps.setLong(n++,pdInfo.getId());
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return pdInfo.getId();
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	/**
	 * 更改状态为3
	 * @param pdInfo
	 * @return
	 */
	public long updateCreditgradecStatus(long  clientID)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_CREDITGRADE "
                    +"set STATUS=3 "
                    +"where clientid=?";
        		
        	
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setLong(n++,clientID);
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return clientID;
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	/**
	 * 查询同一个客户有没有被保存的状态
	 * @param clientID
	 * @return
	 * @throws Exception
	 */
	public long selectCreditcode(long clientID) throws Exception {
		long  lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			
			strSQL = "select count(*)  from LOAN_CREDITGRADE  where  status=1 and clientid="+clientID;
			
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}
	/**
	 * 查询除去本次申请以为的状态
	 * @param clientID
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public long  selectCreditcodes(long clientID,long ID) throws Exception {
		long   lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			
			strSQL = "select count(*)  from LOAN_CREDITGRADE  where  ID!="+ID +"and status=1 and clientid="+clientID;
			
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}
	/**
	 * 修改授信客户信用评分状态该为已失效
	 * @param clientid
	 * @return
	 */
	public long updateCreditgradeStatus(long clientid)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_CREDITGRADE set STATUS=3 where CLIENTID="+ clientid+" and STATUS=2";
        		ps=conn.prepareStatement(strSQL);
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return clientid;
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	/**
	 * 取的授信客户评分表编号
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public String getCreditcode(long ID) throws Exception {
		String  lMaxID = "";
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			
			strSQL = "select creditcode from LOAN_CREDITGRADE  where  id="+ID;
			
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}

	/**
	 * 保存应收帐龄分析表
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertReceiveaccountage(LoanReceiveaccountage info)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(1);
		;

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_RECEIVEACCOUNTAGE ("
					+ "id,creditgradeid,accountage,accountcount,amount, "
					+ "scale,explain,inputdate,inputuserid,status,officeid,currencyid "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getCreditgradeid());
			ps.setLong(n++, info.getAccountage());
			ps.setLong(n++, info.getAccountcount());
			ps.setDouble(n++, info.getAmount());
			ps.setDouble(n++, info.getScale());
			ps.setString(n++, info.getExplain());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getInputuserid());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}

	/**
	 * 查询保存应收帐龄分析表
	 * @param creditgradeid
	 * @param accountage
	 * @return
	 * @throws Exception
	 */
	public LoanReceiveaccountage findReceiveaccountage(long creditgradeid,
			long accountage) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanReceiveaccountage info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_RECEIVEACCOUNTAGE " + " where status >0  "
					+ " and creditgradeid=" + creditgradeid
					+ "and accountage =" + accountage;

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanReceiveaccountage();
				info.setId(rs.getLong("ID"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				info.setAccountage(rs.getLong("accountage"));
				info.setAccountcount(rs.getLong("accountcount"));
				info.setAmount(rs.getDouble("amount"));
				info.setScale(rs.getDouble("scale"));
				info.setExplain(rs.getString("explain"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setStatus(rs.getLong("status"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * 修改应收帐龄分析表
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public long updateReceiveaccountage(LoanReceiveaccountage pInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			strSQL = " update LOAN_RECEIVEACCOUNTAGE set creditgradeid=?, accountage=?, ";
			strSQL+= "accountcount=?, amount=?, scale=? ,explain=?, inputdate=?, inputuserid=?,status=?,officeid=?,currencyid=?  where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getCreditgradeid());
			ps.setLong(n++, pInfo.getAccountage());
			ps.setLong(n++, pInfo.getAccountcount());
			ps.setDouble(n++, pInfo.getAmount());
			ps.setDouble(n++, pInfo.getScale());
			ps.setString(n++, pInfo.getExplain());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getId());

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			}
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 保存收帐款前五名单位信息属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertReceivefundsonaccount(LoanReceivefundsonaccount info)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(2);
		;

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_RECEIVEFUNDSONACCOUNT ("
					+ "id,creditgradeid,clientname,amount,scale, "
					+ "explain,inputdate,inputuserid,porder,status,officeid,currencyid "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getCreditgradeid());
			ps.setString(n++, info.getClientname());
			ps.setDouble(n++, info.getAmount());
			ps.setDouble(n++, info.getScale());
			ps.setString(n++, info.getExplain());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getInputuserid());
			ps.setLong(n++, info.getOrder());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}

	/**
	 * 查询收帐款前五名单位信息属性清单
	 * @param creditgradeid
	 * @param accountage
	 * @return
	 * @throws Exception
	 */
	public LoanReceivefundsonaccount findReceivefundsonaccount(long creditgradeid,
			long order) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanReceivefundsonaccount info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_RECEIVEFUNDSONACCOUNT " + " where status > 0 "
					+ " and creditgradeid=" + creditgradeid
					+ "and porder =" + order;

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanReceivefundsonaccount();
				info.setId(rs.getLong("ID"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				info.setClientname(rs.getString("clientname"));
				info.setAmount(rs.getDouble("amount"));
				info.setScale(rs.getDouble("scale"));
				info.setExplain(rs.getString("explain"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setOrder(rs.getLong("porder"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setStatus(rs.getLong("status"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	
/**
 * 修改收帐款前五名单位信息属性清单
 * @param pInfo
 * @return
 * @throws Exception
 */
	public long updateReceivefundsonaccount(LoanReceivefundsonaccount pInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			strSQL = " update LOAN_RECEIVEFUNDSONACCOUNT set creditgradeid=?, clientname=?, ";
			strSQL+= "amount=?, scale=?, explain=? ,inputdate=?, inputuserid=?, porder=?,status=?,officeid=?,currencyid=?  where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getCreditgradeid());
			ps.setString(n++, pInfo.getClientname());
			ps.setDouble(n++, pInfo.getAmount());
			ps.setDouble(n++, pInfo.getScale());
			ps.setString(n++, pInfo.getExplain());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setLong(n++, pInfo.getOrder());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getId());

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			}
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	//
	/**
	 * 银行负债明细属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertBankliabilitiesdetail(LoanBankliabilitiesdetail info)
			throws Exception {

		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(3);
		

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();
			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_BANKLIABILITIESDETAIL ("
					+ "id,itemid,loanbankname,transamount,comments, "
					+ "inputuserid,inputdate,status,officeid,currencyid,creditgradeid  "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getItemid());
			ps.setString(n++, info.getLoanbankname());
			ps.setDouble(n++, info.getTransamount());
			ps.setString(n++, info.getComments());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getCreditgradeid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

	}
	///add by pengwang
	/**
	 * 银行负债明细属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertBankliabilitiesdetail(LoanBankliabilitiesdetail info,String details,Iterator it)
			throws Exception {

		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		StringBuffer SQL=new StringBuffer();
		StringBuffer SELECTSQL=new StringBuffer();
		ResultSet rs = null;
		long lResult = -1;
		long cid=-1;
		long ID = this.getMaxID(3);
		

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();
            conn.setAutoCommit(false);
			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_BANKLIABILITIESDETAIL ("
					+ "id,itemid,loanbankname,transamount,comments, "
					+ "inputuserid,inputdate,status,officeid,currencyid,creditgradeid  "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getItemid());
			ps.setString(n++, info.getLoanbankname());
			ps.setDouble(n++, info.getTransamount());
			ps.setString(n++, info.getComments());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getCreditgradeid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			SELECTSQL.append(" select * from loan_bankrecord where creditgradeid ="+info.getCreditgradeid()+" ");
			ps=conn.prepareStatement(SELECTSQL.toString());
			rs=ps.executeQuery();
			while (rs.next())
			{
				cid=rs.getLong("creditgradeid");
			}
			if(cid<0)
			{
				if(!it.hasNext())
				{
				SQL.append(" insert into loan_bankrecord values((select nvl(max(id),0)+1 from loan_bankrecord ),?,?) ");
				ps=conn.prepareStatement(SQL.toString());
				int k=1;
				ps.setLong(k++, info.getCreditgradeid());
				ps.setString(k++, details);
				lResult = ps.executeUpdate();
				}
			}
			conn.commit();
			
			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 *修改银行负债明细属性清单
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public long updateBankliabilitiesdetail(LoanBankliabilitiesdetail pInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			strSQL = " update LOAN_BANKLIABILITIESDETAIL set itemid=?, loanbankname=?, ";
			strSQL+= "transamount=?, comments=?, inputuserid=? ,inputdate=?, status=?, officeid=?,currencyid=?,creditgradeid=?  where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getItemid());
			ps.setString(n++, pInfo.getLoanbankname());
			ps.setDouble(n++, pInfo.getTransamount());
			ps.setString(n++, pInfo.getComments());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getCreditgradeid());
			ps.setLong(n++, pInfo.getId());
			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			} 
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	
	/**
	 *修改银行负债明细属性清单
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public long updateBankliabilitiesdetail(LoanBankliabilitiesdetail pInfo,String details,Iterator it) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		StringBuffer SQL=new StringBuffer();
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			strSQL = " update LOAN_BANKLIABILITIESDETAIL set itemid=?, loanbankname=?, ";
			strSQL+= "transamount=?, comments=?, inputuserid=? ,inputdate=?, status=?, officeid=?,currencyid=?,creditgradeid=?  where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getItemid());
			ps.setString(n++, pInfo.getLoanbankname());
			ps.setDouble(n++, pInfo.getTransamount());
			ps.setString(n++, pInfo.getComments());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getCreditgradeid());
			ps.setLong(n++, pInfo.getId());
			lResult = ps.executeUpdate();
			if(!it.hasNext())
			{
				SQL.append(" update loan_bankrecord set record ='"+details+"' where creditgradeid="+pInfo.getCreditgradeid()+" ");
				ps=conn.prepareStatement(SQL.toString());
				lResult = ps.executeUpdate();
			}
			conn.commit();
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			} 
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	} 
	/**
	 * 查询银行负债明细属性清单
	 * @param creditgradeid
	 * @return
	 * @throws Exception
	 */
	public LoanBankliabilitiesdetail findBankliabilitiesdetail(long creditgradeid,long itemid) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanBankliabilitiesdetail info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_BANKLIABILITIESDETAIL " + " where status > 0 "
					+ " and creditgradeid=" + creditgradeid+" and itemid=" + itemid;
			log.info("update loan property info error:" + strSQL);
			
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanBankliabilitiesdetail();
				info.setId(rs.getLong("ID"));
				info.setItemid(rs.getLong("itemid"));
				info.setLoanbankname(rs.getString("loanbankname"));
				info.setTransamount(rs.getDouble("transamount"));
				info.setComments(rs.getString("comments"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setStatus(rs.getLong("status"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
				
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * 对外或有负债分析属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertExternalliabilities(LoanExternalliabilities info)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(4);
		;

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_EXTERNALLIABILITIES ("
					+ "id,officeid,currencyid,transtype,stockcount, "
					+ "object,balance,enddate,possibillity,status,inputuserid,inputdate,creditgradeid  "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getTranstype());
			ps.setLong(n++, info.getStockcount());
			ps.setString(n++, info.getPobject());
			ps.setDouble(n++, info.getBalance());
			ps.setTimestamp(n++, info.getEnddate());
			ps.setString(n++, info.getPossibillity());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getCreditgradeid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 对外或有负债分析属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertExternalliabilities(LoanExternalliabilities info,String details,Iterator it)
			throws Exception {
		long cid=-1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		StringBuffer strSQL1=new StringBuffer();
		StringBuffer SQL = new StringBuffer();
		long ID = this.getMaxID(4);

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_EXTERNALLIABILITIES ("
					+ "id,officeid,currencyid,transtype,stockcount, "
					+ "object,balance,enddate,possibillity,status,inputuserid,inputdate,creditgradeid  "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getTranstype());
			ps.setLong(n++, info.getStockcount());
			ps.setString(n++, info.getPobject());
			ps.setDouble(n++, info.getBalance());
			ps.setTimestamp(n++, info.getEnddate());
			ps.setString(n++, info.getPossibillity());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getCreditgradeid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			strSQL1.append(" select * from LOAN_ASSUREORACCEPTSYNOPSIS where creditgradeid ="+info.getCreditgradeid()+" ");
			ps=conn.prepareStatement(strSQL1.toString());
			rs=ps.executeQuery();
			while (rs.next())
			{
				cid=rs.getLong("creditgradeid");
			}
			if(cid<0)
			{
				if(!it.hasNext())
				{
				SQL.append(" insert into  LOAN_ASSUREORACCEPTSYNOPSIS  values((select nvl(max(id),0)+1 from LOAN_ASSUREORACCEPTSYNOPSIS ),?,?,?) ");
				ps=conn.prepareStatement(SQL.toString());
				int k=1;
				ps.setString(k++, details);
				ps.setLong(k++, 1);
				ps.setLong(k++, info.getCreditgradeid());
				lResult = ps.executeUpdate();
				}
			}
			conn.commit();
			System.out.println("*****####******添加成功******");
			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 修改对外或有负债分析属性清单
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public long updateExternalliabilities(LoanExternalliabilities pInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			strSQL = " update LOAN_EXTERNALLIABILITIES set officeid=?, currencyid=?, ";
			strSQL+= "transtype=?, stockcount=?, object=? ,balance=?, enddate=?, possibillity=?,status=?,inputuserid=?,inputdate=?,creditgradeid=?   where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getTranstype());
			ps.setLong(n++, pInfo.getStockcount());
			ps.setString(n++, pInfo.getPobject());
			ps.setDouble(n++, pInfo.getBalance());
			ps.setTimestamp(n++, pInfo.getEnddate());
			ps.setString(n++, pInfo.getPossibillity());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setLong(n++, pInfo.getCreditgradeid());
			ps.setLong(n++, pInfo.getId());
			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			}
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 修改对外或有负债分析属性清单
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public long updateExternalliabilities(LoanExternalliabilities pInfo,String details,Iterator it) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		StringBuffer SQL = new StringBuffer();
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			strSQL = " update LOAN_EXTERNALLIABILITIES set officeid=?, currencyid=?, ";
			strSQL+= "transtype=?, stockcount=?, object=? ,balance=?, enddate=?, possibillity=?,status=?,inputuserid=?,inputdate=?,creditgradeid=?   where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getTranstype());
			ps.setLong(n++, pInfo.getStockcount());
			ps.setString(n++, pInfo.getPobject());
			ps.setDouble(n++, pInfo.getBalance());
			ps.setTimestamp(n++, pInfo.getEnddate());
			ps.setString(n++, pInfo.getPossibillity());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setLong(n++, pInfo.getCreditgradeid());
			ps.setLong(n++, pInfo.getId());
			lResult = ps.executeUpdate();
	
				SQL.append(" update LOAN_ASSUREORACCEPTSYNOPSIS set synopsis ='"+details+"' where creditgradeid="+pInfo.getCreditgradeid()+" ");
				ps=conn.prepareStatement(SQL.toString());
				lResult = ps.executeUpdate();

			conn.commit();
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			}
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 通过ＩＤ删除对外或有负债分析属性清单
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public long deleteExternalliabilities(long ID) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			strSQL = " update LOAN_EXTERNALLIABILITIES set status=0  where ID="+ID ;
			ps = conn.prepareStatement(strSQL);

			int n = 1;
			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			}
			else
			{
				return ID;
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 通过授信客户评分ID返回集合
	 * @param creditgradeid
	 * @return
	 * @throws Exception
	 */
public Collection quetyExternalliabilities(long creditgradeid) throws Exception {
		
		Collection res =null;
		LoanExternalliabilities info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		res=new ArrayList();
		try
		{
			log4j.info("\n=======findByID start====");
			conn = Database.getConnection();
			//查询表ob_extend
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from LOAN_EXTERNALLIABILITIES ");
			sbSQL.append(" where status!=0 and creditgradeid="+creditgradeid+" ");
			log4j.info("\n=======findByID start===="+sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = new LoanExternalliabilities();
				info.setId(rs.getLong("ID"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
				info.setTranstype(rs.getLong("transtype"));
				info.setStockcount(rs.getLong("stockcount"));
				info.setPobject(rs.getString("object"));
				info.setBalance(rs.getDouble("balance"));
				info.setEnddate(rs.getTimestamp("enddate"));
				info.setPossibillity(rs.getString("possibillity"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				res.add(info);
				
			}
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
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
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
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return res;
	}
	
	/**
	 * 通过ID查找对外或有负债分析属性
	 * @param creditgradeid
	 * @return
	 * @throws Exception
	 */
	public LoanExternalliabilities findExternalliabilities(long id
			) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanExternalliabilities info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_EXTERNALLIABILITIES " + " where status > 0 "
					+ " and id=" + id;

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanExternalliabilities();
				info.setId(rs.getLong("ID"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
				info.setTranstype(rs.getLong("transtype"));
				info.setStockcount(rs.getLong("stockcount"));
				info.setPobject(rs.getString("pobject"));
				info.setBalance(rs.getDouble("balance"));
				info.setEnddate(rs.getTimestamp("enddate"));
				info.setPossibillity(rs.getString("possibillity"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * 集团内部往来属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertGroupinsidecontact(LoanGroupinsidecontact info)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(5);
		

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_GROUPINSIDECONTACT ("
					+ "id,grouprecord,orgrecord,balance,settmeasure, "
					+ "status,inputuserid,inputdate,creditgradeid,officeid,currencyid "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setString(n++, info.getGrouprecord());
			ps.setString(n++, info.getOrgrecord());
			ps.setDouble(n++, info.getBalance());
			ps.setDouble(n++, info.getSettmeasure());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getCreditgradeid());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());

			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 修改集团内部往来属性清单
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public long updateGroupinsidecontact(LoanGroupinsidecontact pInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			strSQL = " update LOAN_GROUPINSIDECONTACT set grouprecord=?, orgrecord=?, ";
			strSQL+= "balance=?, settmeasure=?, status=? ,inputuserid=?, inputdate=?, creditgradeid=?,officeid=?,currencyid=?  where ID=? ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
			ps.setString(n++, pInfo.getGrouprecord());
			ps.setString(n++, pInfo.getOrgrecord());
			ps.setDouble(n++, pInfo.getBalance());
			ps.setDouble(n++, pInfo.getSettmeasure());
			ps.setLong(n++, pInfo.getStatus());
			ps.setLong(n++, pInfo.getInputuserid());
			ps.setTimestamp(n++, pInfo.getInputdate());
			ps.setLong(n++, pInfo.getCreditgradeid());
			ps.setLong(n++, pInfo.getOfficeid());
			ps.setLong(n++, pInfo.getCurrencyid());
			ps.setLong(n++, pInfo.getId());

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0)
			{
				log.info("update loan property info error:" + lResult);
				return -1;
			}
			else
			{
				return pInfo.getId();
			}
		}
		catch (Exception e)
		{

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 通过客户信用评价ID取的集团内部往来属性清单
	 * @param creditgradeid
	 * @return
	 * @throws Exception
	 */
	public LoanGroupinsidecontact findGroupinsidecontact(long creditgradeid,long clientid,String strYear) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		String lastyear = (Long.parseLong(strYear)-1) + "";
		LoanGroupinsidecontact info = null;
		info = new LoanGroupinsidecontact();

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_GROUPINSIDECONTACT " + " where status > 0 "
			+ " and creditgradeid=" + creditgradeid;

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				info.setId(rs.getLong("ID"));
				info.setGrouprecord(rs.getString("grouprecord"));
				info.setOrgrecord(rs.getString("orgrecord"));
				info.setBalance(rs.getDouble("balance"));
				info.setSettmeasure(rs.getDouble("settmeasure"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
			}
			strSQL = 
				"SELECT SUM(NVL(A.MBALANCE, 0))" +
						"/(TO_DATE('" + lastyear + "-12-31', 'YYYY-MM-DD')" +
						"-TO_DATE('" + lastyear + "-01-01', 'YYYY-MM-DD') + 1) AS BALANCE " +
				"FROM SETT_DAILYACCOUNTBALANCE A,SETT_ACCOUNT B, CLIENT C, SETT_ACCOUNTTYPE D " +
				"WHERE 1 = 1 AND A.NACCOUNTID = B.ID AND B.NCLIENTID = C.ID " +
					"AND B.NACCOUNTTYPEID = D.ID AND D.NACCOUNTGROUPID IN (1, 2, 3) " +
					"AND D.ID <> 4 AND A.NSUBACCOUNTSTATUSID NOT IN (0, 2) " +
					"AND A.DTDATE >= TO_DATE('" + lastyear + "-01-01', 'YYYY-MM-DD') " +
					"AND A.DTDATE <= TO_DATE('" + lastyear + "-12-31', 'YYYY-MM-DD') " +
					"AND C.ID = " + clientid;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs.next())
			{
				info.setBalance(rs.getDouble("BALANCE"));
			}
			strSQL = 
				"SELECT SUM(NVL(A.MAMOUNT, 0))" +
					"/(TO_DATE('" + lastyear + "-12-31', 'YYYY-MM-DD')" +
					"-TO_DATE('" + lastyear + "-01-01', 'YYYY-MM-DD') + 1) AS SETTMEASURE " +
				"FROM SETT_TRANSACCOUNTDETAIL A,SETT_ACCOUNT B,CLIENT C,SETT_ACCOUNTTYPE D " +
				"WHERE 1 = 1 AND A.NTRANSACCOUNTID = B.ID AND B.NCLIENTID = C.ID " +
				"AND B.NACCOUNTTYPEID = D.ID AND D.NACCOUNTGROUPID IN (1, 2, 3) " +
				"AND A.NSTATUSID = 3 AND DTINTERESTSTART >= TO_DATE('" + lastyear + "-01-01', 'YYYY-MM-DD') " +
				"AND DTINTERESTSTART <= TO_DATE('" + lastyear + "-12-31', 'YYYY-MM-DD') " +
				"AND C.ID = " + clientid;
			log.info(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs.next())
			{
				info.setSettmeasure(rs.getDouble("SETTMEASURE"));
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * 经营分析属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertManageanalyse(LoanManageanalyse info) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(6);
		;

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_MANAGEANALYSE ("
					+ "id,creditgradeid,officeid,currencyid,incomeform, "
					+ "competecondition,marketlocation,elementarycondition,ambienceanalyse,advanced,startrate, "
					+ "managestandard,consist,completecondition,situation,designcondition,planning,status  "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getCreditgradeid());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setString(n++, info.getIncomeform());
			ps.setString(n++, info.getCompetecondition());
			ps.setString(n++, info.getMarketlocation());
			ps.setString(n++, info.getElementarycondition());
			ps.setString(n++, info.getAmbienceanalyse());
			ps.setString(n++, info.getAdvanced());
			ps.setString(n++, info.getStartrate());
			ps.setString(n++, info.getManagestandard());
			ps.setString(n++, info.getConsist());
			ps.setString(n++, info.getCompletecondition());
			ps.setString(n++, info.getSituation());
			ps.setString(n++, info.getDesigncondition());
			ps.setString(n++, info.getPlanning());
			ps.setLong(n++, info.getStatus());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	
	
	/**
	 * 保存被担保/承兑企业简况属性清单
	 * @param info
	 * @return
	 * @throws Exception
	 */ 
	public long insertAssureoracceptsynopsis(LoanAssureoracceptsynopsis info) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(9);
		

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into  LOAN_ASSUREORACCEPTSYNOPSIS ("
					+ "id,synopsis,status,creditgradeid "
					+ ") values (?, ?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setString(n++, info.getSynopsis());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getCreditgradeid());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 保存被担保/承兑企业简况属性清单
	 * @param info
	 * @return
	 * @throws Exception
	 */ 
	public long selectCreditidStatus(LoanAssureoracceptsynopsis info) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		long id=-1;
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "select * from   LOAN_ASSUREORACCEPTSYNOPSIS where CREDITGRADEID="+info.getCreditgradeid()+"";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			while(rs.next())
			{
				id = rs.getLong("id");
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return id;
	}
	/**
	 * 查询被担保/承兑企业简况属性清单
	 * @param creditgradeid
	 * @return
	 * @throws Exception
	 */
	public LoanAssureoracceptsynopsis findAssureoracceptsynopsis(long creditgradeid) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanAssureoracceptsynopsis info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_ASSUREORACCEPTSYNOPSIS " + " where 1 = 1 "
					+ " and creditgradeid=" + creditgradeid;

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanAssureoracceptsynopsis();
				info.setId(rs.getLong("ID"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				info.setSynopsis(rs.getString("synopsis"));
				info.setStatus(rs.getLong("status"));
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * 修改被担保/承兑企业简况属性清单
	 * @param pdInfo
	 * @return
	 */
	public long updateAssureoracceptsynopsis(LoanAssureoracceptsynopsis pdInfo)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_ASSUREORACCEPTSYNOPSIS "
                    +"set synopsis=?  "
                    +"where ID=?";
        		
        	
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setString(n++,pdInfo.getSynopsis());
                ps.setLong(n++,pdInfo.getId());
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return pdInfo.getId();
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	/**
	 * 银行信贷登记系统记录表属性清单
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertBankrecord(LoanBankrecord info) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(9);
		

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into  LOAN_BANKRECORD ("
					+ "id,creditgradeid,record  "
					+ ") values (?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getCreditgradeid());
			ps.setString(n++, info.getRecord());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	
	/**
	 * 查询银行信贷登记系统记录表属性清单
	 * @param creditgradeid
	 * @return
	 * @throws Exception
	 */
	public LoanBankrecord findBankrecord(long creditgradeid) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanBankrecord info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_BANKRECORD " + " where 1 = 1 "
					+ " and creditgradeid=" + creditgradeid;

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanBankrecord();
				info.setId(rs.getLong("ID"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				info.setRecord(rs.getString("record"));
				
				
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * 修改银行信贷登记系统记录表属性清单
	 * @param pdInfo
	 * @return
	 */
	public long updateBankrecord(LoanBankrecord pdInfo)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_BANKRECORD "
                    +"set record=?  "
                    +"where ID=?";
        		
        	
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setLong(n++,pdInfo.getCreditgradeid());
                ps.setString(n++,pdInfo.getRecord());
                ps.setLong(n++,pdInfo.getId());
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return pdInfo.getId();
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	/**
	 * 查询经营分析属性清单
	 * @param creditgradeid
	 * @return
	 * @throws Exception
	 */
	public LoanManageanalyse findManageanalyse(long creditgradeid) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanManageanalyse info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select * from LOAN_MANAGEANALYSE " + " where 1 = 1 "
					+ " and creditgradeid=" + creditgradeid;

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanManageanalyse();
				info.setId(rs.getLong("ID"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
				info.setIncomeform(rs.getString("incomeform"));
				info.setCompetecondition(rs.getString("competecondition"));
				info.setMarketlocation(rs.getString("marketlocation"));
				info.setElementarycondition(rs.getString("elementarycondition"));
				info.setAmbienceanalyse(rs.getString("ambienceanalyse"));
				info.setAdvanced(rs.getString("advanced"));
				info.setStartrate(rs.getString("startrate"));
				info.setManagestandard(rs.getString("managestandard"));
				info.setConsist(rs.getString("consist"));
				info.setCompletecondition(rs.getString("completecondition"));
				info.setSituation(rs.getString("situation"));
				info.setDesigncondition(rs.getString("designcondition"));
				info.setPlanning(rs.getString("planning"));
				
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * wxsu 20080623 22:43
	 * 修改经营分析属性清单 
	 * 
	 * @param pdInfo
	 * @return
	 */
	public long updateManageanalyse(LoanManageanalyse pdInfo)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_MANAGEANALYSE "
                    +"set creditgradeid=?, officeid=?, "
                    +"currencyid=?, incomeform=?, competecondition=?, marketlocation=?, "
                    +"elementarycondition=?, ambienceanalyse=? ,advanced=? ,startrate=? ,managestandard=? ,consist=? ,completecondition=? ,  "
                    +"situation=?, designcondition=?, planning=?, status=? "
                    +"where ID=?";
        		
        	
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setLong(n++,pdInfo.getCreditgradeid());
                ps.setLong(n++,pdInfo.getOfficeid());
                ps.setLong(n++,pdInfo.getCurrencyid());
                ps.setString(n++,pdInfo.getIncomeform());
                ps.setString(n++,pdInfo.getCompetecondition());
                ps.setString(n++,pdInfo.getMarketlocation());
                ps.setString(n++,pdInfo.getElementarycondition());
                ps.setString(n++,pdInfo.getAmbienceanalyse());
                ps.setString(n++,pdInfo.getAdvanced());
                ps.setString(n++,pdInfo.getStartrate());
                ps.setString(n++,pdInfo.getManagestandard());
                ps.setString(n++,pdInfo.getConsist());
                ps.setString(n++,pdInfo.getCompletecondition());
                ps.setString(n++,pdInfo.getSituation());
                ps.setString(n++,pdInfo.getDesigncondition());
                ps.setString(n++,pdInfo.getPlanning());
                ps.setLong(n++,pdInfo.getStatus());
                ps.setLong(n++,pdInfo.getId());
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return pdInfo.getId();
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	/**
	 * 信用等级明细属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertCreditgradedetail(LoanCreditgradedetail info)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		long ID = this.getMaxID(7);
		;

		System.out.println("********进入Dao层***");

		log.info("已经走到这里了1");
		try {
			conn = Database.getConnection();

			log.info("已经走到这里了2");
			/* 开始执行插入 */
			strSQL = "insert into LOAN_CREDITGRADEDETAIL ("
					+ "id,itemid,officeid,currencyid,targetid, "
					+ "targetname,weight,describe,score,diversity,diversityexplain, "
					+ "status,inputuserid,inputdate,creditgradeid,THATADJUSTMENT,InitialPoints,AdjustmentPoints,operationtype  "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ? )";
			ps = conn.prepareStatement(strSQL);
			int n = 1;
			ps.setLong(n++, ID);
			ps.setLong(n++, info.getItemid());
			ps.setLong(n++, info.getOfficeid());
			ps.setLong(n++, info.getCurrencyid());
			ps.setLong(n++, info.getTargetid());
			ps.setString(n++, info.getTargetname());
			ps.setLong(n++, info.getWeight());
			ps.setLong(n++, info.getDescribe());
			ps.setDouble(n++, info.getScore());
			ps.setDouble(n++, info.getDiversity());
			ps.setString(n++, info.getDiversityexplain());
			ps.setLong(n++, info.getStatus());
			ps.setLong(n++, info.getInputuserid());
			ps.setTimestamp(n++, info.getInputdate());
			ps.setLong(n++, info.getCreditgradeid());
			ps.setString(n++, info.getThatadjustment());
			ps.setDouble(n++, info.getInitialPoints());
			ps.setDouble(n++, info.getAdjustmentPoints());
			ps.setLong(n++, info.getOperationtype());
			log.info("已经走到这里了3");
			lResult = ps.executeUpdate();
			System.out.println("*****####******添加成功******");
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

			if (lResult < 0) {
				log.info("insert a loan plan detail fail");
				return -1;
			} else {
				return ID;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

	}
	/**
	 * 修改信用等级明细属性清单
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateCreditgradedetail(LoanCreditgradedetail pdInfo)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_CREDITGRADEDETAIL "
                    +"set itemid=?, officeid=?, "
                    +"currencyid=?, targetid=?, targetname=?, weight=?, "
                    +"describe=?, score=? ,diversity=? ,diversityexplain=? ,status=? ,inputuserid=? ,inputdate=? ,  "
                    +"creditgradeid=?,THATADJUSTMENT=?,InitialPoints=?,AdjustmentPoints=?,operationtype=?   where ID=?";
        		
        	
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setLong(n++,pdInfo.getItemid());
                ps.setLong(n++,pdInfo.getOfficeid());
                ps.setLong(n++,pdInfo.getCurrencyid());
                ps.setLong(n++,pdInfo.getTargetid());
                ps.setString(n++,pdInfo.getTargetname());
                ps.setLong(n++,pdInfo.getWeight());
                ps.setLong(n++,pdInfo.getDescribe());
                ps.setDouble(n++,pdInfo.getScore());
                ps.setDouble(n++,pdInfo.getDiversity());
                ps.setString(n++,pdInfo.getDiversityexplain());
                ps.setLong(n++,pdInfo.getStatus());
                ps.setLong(n++,pdInfo.getInputuserid());
                ps.setTimestamp(n++,pdInfo.getInputdate());
                ps.setLong(n++,pdInfo.getCreditgradeid());
                ps.setString(n++,pdInfo.getThatadjustment());
                ps.setDouble(n++,pdInfo.getInitialPoints());
                ps.setDouble(n++,pdInfo.getAdjustmentPoints());
                ps.setLong(n++,pdInfo.getOperationtype());
                ps.setLong(n++,pdInfo.getId());
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return pdInfo.getId();
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	
	
	public long updateCreditgradedetailto(LoanCreditgradedetail pdInfo)
    {
		PreparedStatement ps = null;
        //ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        long  lResult=-1;
        try
        {
        	log4j.info("\n=======调用保存方法　ＤＡＯ1====");
        	conn=Database.getConnection();
        	
        		System.out.println("***dwj***进入Dao***上传块");
    			strSQL="update LOAN_CREDITGRADEDETAIL "
                    +"set   weight=?, diversity=? ,diversityexplain=? ,status=?   where ID=?";
        		
        	
        		ps=conn.prepareStatement(strSQL);
                int n=1;
                ps.setLong(n++,pdInfo.getWeight());
                ps.setDouble(n++,pdInfo.getDiversity());
                ps.setString(n++,pdInfo.getDiversityexplain());
                ps.setLong(n++,pdInfo.getStatus());
                ps.setLong(n++,pdInfo.getId());
                lResult=ps.executeUpdate();
                log4j.info("\n=======调用保存方法　ＤＡＯ2====");
                cleanup(ps);
                cleanup(conn);
                
                if ( lResult<0 )
                {
                    log.info("update loan plan detail fail:"+lResult);
                    return -1;          
                }
                else
                {
                    return pdInfo.getId();
                }
    		
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
        	try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	/**
	 * 通过信用等级明细属性清单
	 * @param creditgradeid
	 * @param targetid
	 * @return
	 * @throws Exception
	 */
	public LoanCreditgradedetail findCreditgradedetail(long creditgradeid,long targetid) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanCreditgradedetail info = null;

		try {
			conn = Database.getConnection();
			strSQL = " select *   from LOAN_CREDITGRADEDETAIL  " + " where status > 0 "
					+ " and creditgradeid=" + creditgradeid+" and targetid="+targetid;
	
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanCreditgradedetail();
				info.setId(rs.getLong("ID"));
				info.setItemid(rs.getLong("itemid"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
				info.setTargetid(rs.getLong("targetid"));
				info.setTargetname(rs.getString("targetname"));
				info.setWeight(rs.getLong("weight"));
				info.setDescribe(rs.getLong("describe"));
				info.setScore(rs.getLong("score"));
				info.setDiversity(rs.getLong("diversity"));
				info.setDiversityexplain(rs.getString("diversityexplain"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setCreditgradeid(rs.getLong("creditgradeid"));
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	/**
	 * 得到ＩＤ最大值
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getMaxID(long type) throws Exception {
		long lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			if (type == 1) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_RECEIVEACCOUNTAGE ";
			} else if (type == 2) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_RECEIVEFUNDSONACCOUNT ";
			} else if (type == 3) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_BANKLIABILITIESDETAIL ";
			} else if (type == 4) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_EXTERNALLIABILITIES ";
			} else if (type == 5) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_GROUPINSIDECONTACT ";
			} else if (type == 6) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_MANAGEANALYSE ";
			} else if (type == 7) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_CREDITGRADEDETAIL ";
			}
			else if (type == 8) {
				strSQL = "select nvl(max(id)+1,1) from LOAN_CREDITGRADE ";
			}
			else if (type == 9) {
				strSQL = "select nvl(max(id)+1,1) from  LOAN_ASSUREORACCEPTSYNOPSIS ";
			}
			else if (type == 10) {
				strSQL = "select nvl(max(id)+1,1) from  LOAN_BANKRECORD ";
			}
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				lMaxID = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}
	/**
	 * 银行负债情况-银行信贷登记系统记录 查询
	 * @auter pengwang
	 * @param creditid
	 * @return
	 * @throws Exception
	 */ 
		 public CreditFrameInfo query(long creditid) throws Exception
		 {
			CreditFrameInfo info=new CreditFrameInfo();
			Connection conn=null; 
			ResultSet rs=null;
			PreparedStatement ps = null;
			StringBuffer strSQL = new StringBuffer();
			try 
			{
				conn = Database.getConnection();
				strSQL.append(" select * from loan_bankrecord where creditgradeid="+creditid+"");
				ps = conn.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();
				while(rs.next())
				{
					info.setRecord(rs.getString("record"));
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				rs.close();
				ps.close();
				conn.close();
			}
			return info;	 
		 }
		 public CreditFrameInfo queryLoan_assureoracceptsynopsis(long creditid) throws Exception
		 {
			CreditFrameInfo info=new CreditFrameInfo();
			Connection conn=null; 
			ResultSet rs=null;
			PreparedStatement ps = null;
			StringBuffer strSQL = new StringBuffer();
			try 
			{
				conn = Database.getConnection();
				strSQL.append(" select * from LOAN_ASSUREORACCEPTSYNOPSIS where creditgradeid="+creditid+"");
				ps = conn.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();
				while(rs.next())
				{
					info.setSynopsis(rs.getString("synopsis"));
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				rs.close();
				ps.close();
				conn.close();
			}
			return info;	 
		 }
		 public long  insertByDB(long creditid,String details) throws Exception
		 {
			Connection conn=null; 
			ResultSet rs=null;
			PreparedStatement ps = null;
			long lResult=-1;
			int k=1;
			StringBuffer strSQL = new StringBuffer();
			StringBuffer strSQL1 = new StringBuffer();
			long cid=-1;
			try 
			{
				
				conn = Database.getConnection();
				strSQL1.append(" select * from LOAN_ASSUREORACCEPTSYNOPSIS where creditgradeid ="+creditid+" ");
				ps=conn.prepareStatement(strSQL1.toString());
				rs=ps.executeQuery();
				while (rs.next())
				{
					cid=rs.getLong("creditgradeid");
				}
				if(cid<0)
				{
				
					strSQL.append(" insert into  LOAN_ASSUREORACCEPTSYNOPSIS  values((select nvl(max(id),0)+1 from LOAN_ASSUREORACCEPTSYNOPSIS ),?,?,?) ");
					ps = conn.prepareStatement(strSQL.toString());
					ps.setString(k++, details);
					ps.setLong(k++, 1);
					ps.setLong(k++, creditid);
					lResult = ps.executeUpdate();
				}
				else
				{
					strSQL.append(" update LOAN_ASSUREORACCEPTSYNOPSIS set synopsis ='"+details+"' where creditgradeid="+creditid+"	 ");
					ps = conn.prepareStatement(strSQL.toString());
					lResult = ps.executeUpdate();
				}
			
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				rs.close();
				ps.close();
				conn.close();
			}
			return lResult;	 
		 }

	/**
	 * 根据客户ID查找有效的财务分析记录的ID
	 * DEBUG 11007 by lcliu at 09-2-12
	 * @param clientId
	 * @return
	 */
	public long findByClientId(long clientId)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		long id = -1;
		
		try
		{
			String sql = "select id from loan_creditgrade where clientid = " + clientId + " and status in (1, 2, 3)";
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			
			if (rs.next())
			{
				id = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return id;
	}
	
	/**
	 * 更新loan_creditgrade表中的cycleYear和cycleMonth字段。
	 * @author lcliu
	 * @param id
	 * @param cycleYear
	 * @param cycleMonth
	 * @return
	 */
	public long updateCreditCycle(long Id, String cycleYear, String cycleMonth)
	{
		Connection conn = null;
		Statement statement = null;
		long result = -1;
		
		try
		{
			String sql = "update loan_creditgrade set cycleyear = '" + cycleYear
						+ "', cyclemonth = '" + cycleMonth + "' where id = " + Id;
			conn = Database.getConnection();
			statement = conn.createStatement();
			result = statement.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 从loan_creditgrade表中查询财务周期并以字符串数组返回
	 * @author lcliu
	 * @param Id
	 * @return
	 */
	public String[] queryCreditCycle(long Id)
	{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String[] cycle = new String[2];
		
		try
		{
			String sql = "select cycleYear, cycleMonth from loan_creditgrade where id = " + Id;
			conn = Database.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if (rs.next())
			{
				cycle[0] = rs.getString(1);
				cycle[1] = rs.getString(2);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return cycle;

	}
}
