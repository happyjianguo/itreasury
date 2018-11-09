/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dao;

import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obdao.*;
import com.iss.itreasury.ebank.obdataentity.*;
import com.iss.itreasury.ebank.obloanapply.dataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanApplyDao extends OBBaseDao
{
	private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);
    
	/**
	 * <ol><b>保存贷款初始信息</b>
	 * <ul>
	 * <li>操作数据库表LoanInfo
	 * </ul>
	 * </ol>
	 * @param cInfo LoanCreateInfo 
	 * @return long 成功返回1，失败返回0
	 */
	public long saveLoanCreate(OBLoanCreateInfo cInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long loanID = -1;
		long  lResult=-1;
		String instructionNo="";
		OBSecurityInfo sInfo=cInfo.getSecurityInfo();
        
		try 
		{
			conn = Database.getConnection ();
			if (cInfo.getLoanID() <= 0)                  //新建贷款 
			{
				//首先得到网银现有贷款的最大ID
				strSQL = "select nvl(max(ID)+1,1) oID from OB_Loan";
				ps = conn.prepareStatement (strSQL);
				rs = ps.executeQuery ();
				if (rs.next ()) 
				{
					loanID = rs.getLong ("oID");
				}
				cleanup(rs);
				cleanup(ps);
                
				//获得指令流水编号
				instructionNo=OBOperation.createInstrCode( OBConstant.SubModuleType.LOAN );
				log.info("Create loan:get applyCode:"+instructionNo);
                
				//插入贷款新建信息
				strSQL = "Insert into OB_loan(ID, NTYPEID, NCURRENCYID, NOFFICEID, "+
						"SINSTRUCTIONNO,NCONSIGNCLIENTID, NBORROWCLIENTID,"+
						"NINPUTUSERID,DTINPUTDATE,NSTATUSID)"+
						" values (?,?,?,?,?,?,?,?,sysdate,?)";
				ps = conn.prepareStatement (strSQL);
				int n=1;
				ps.setLong (n++,loanID);
				ps.setLong (n++,cInfo.getTypeID());
				ps.setLong (n++,sInfo.getCurrencyID());
				ps.setLong (n++,sInfo.getOfficeID());
				ps.setString (n++,instructionNo);
				ps.setLong (n++,cInfo.getConsignClientID());
				ps.setLong (n++,cInfo.getBorrowClientID());
				ps.setLong (n++,sInfo.getUserID());
				ps.setLong(n++,OBConstant.LoanInstrStatus.SAVE);
				lResult=ps.executeUpdate ();
		   }
		   else															//修改信息
		   {
		   		loanID=cInfo.getLoanID() ;
		   		long borrowClientID=cInfo.getBorrowClientID() ;
		   		strSQL="update OB_loan set nBorrowClientID=? where id=?";
		   		int n=1;
		   		ps=conn.prepareStatement( strSQL);
		   		ps.setLong(n++,borrowClientID);
		   		ps.setLong(n++,loanID);
		   		lResult=ps.executeUpdate();
		   }
		   cleanup(rs);
		   cleanup(ps);
		   cleanup(conn);
		}catch(IException ie){
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw ie;			       
		}catch(Exception e) {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace ();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lResult<0?lResult:loanID);
	}
	
	/**
	 * <ol><b>保存贷款基本信息</b>
	 * <ul>
	 * <li>操作数据库表LoanInfo
	 * </ul>
	 * </ol>
	 * @param bInfo LoanBasicInfo 
	 * @return long 成功返回1，失败返回0
	 */
	public long saveLoanBasic(OBLoanBasicInfo bInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long    lResult=-1;
        
		try 
		{
			conn = Database.getConnection();
			strSQL = " update OB_loan set mLoanAmount=?, " +
				"sLoanReason=?, sLoanPurpose=?, sOther=? ,nBankInterestID=? , "+
				"mChargeRate=?, nIntervalNum=?, dtStartDate=?, dtEndDate=?, " +
				"sClientInfo=?, mSelfAmount=?, mInterestRate=?,nChargeRateTypeId=?,NSUBTYPEID=?,ASSURECHARGERATE=?,ASSURECHARGETYPEID=?,BENEFICIARY=?,ASSURETYPEID1=?,ASSURETYPEID2=? where ID=?";
    
			ps=conn.prepareStatement(strSQL);
			int n=1;
			ps.setDouble(n++,bInfo.getLoanAmount());  
			ps.setString(n++,bInfo.getLoanReason() );
			ps.setString(n++,bInfo.getLoanPurpose() );
			ps.setString(n++,bInfo.getOther() );
			ps.setLong(n++,bInfo.getBankInterestID());
			ps.setDouble(n++,bInfo.getChargeRate());
			ps.setLong(n++,bInfo.getIntervalNum());
			ps.setTimestamp(n++,bInfo.getStartDate() );
			ps.setTimestamp(n++,bInfo.getEndDate());
			ps.setString(n++,bInfo.getClientInfo());
			ps.setDouble(n++,bInfo.getSaleAmount());
			ps.setDouble(n++,bInfo.getInterestRate());
			ps.setLong(n++,bInfo.getCheckPayType());
			ps.setLong(n++,bInfo.getSubTypeId());
			ps.setDouble(n++,bInfo.getAssureChargeRate());
			ps.setLong(n++,bInfo.getAssureChargeTypeID());
			ps.setString(n++,bInfo.getBeneficiary());
			ps.setLong(n++,bInfo.getAssureTypeID1());
			ps.setLong(n++,bInfo.getAssureTypeID2());		
			ps.setLong(n++,bInfo.getLoanID());
            
			lResult=ps.executeUpdate();
			
			cleanup(ps);
			cleanup(conn);
            
			return bInfo.getLoanID();

		}catch(Exception e){
			cleanup(ps);
			cleanup(conn);        	
			e.printStackTrace();
			throw e;
		}finally{
			cleanup(ps);
			cleanup(conn);
		}
	}
	/**
	 * <ol><b>保存贷款性质信息</b>
	 * <ul>
	 * <li>操作数据库表LoanInfo
	 * </ul>
	 * </ol>
	 * @param pInfo LoanPropertyInfo 
	 * @return long 成功返回1，失败返回0
	 */
	public long saveLoanProperty(OBLoanPropertyInfo pInfo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long    lResult=-1;
		try 
		{
			conn = Database.getConnection();
			strSQL = " update OB_loan set nIsCircle=?, nIsSaleBuy=?, " +
				"nIsTechnical=?, nIsCredit=?, nIsAssure=? ,nIsImpawn=?, nIsPledge=?,isRecognizance=? where ID=?";
                
			ps=conn.prepareStatement(strSQL);
            
			int n=1;
			ps.setLong(n++,pInfo.getIsCircle() );
			ps.setLong(n++,pInfo.getIsSaleBuy() );
			ps.setLong(n++,pInfo.getIsTechnical() );
			ps.setLong(n++,pInfo.getIsCredit() );
			ps.setLong(n++,pInfo.getIsAssure());
			ps.setLong(n++,pInfo.getIsImpawn());
			ps.setLong(n++,pInfo.getIsPledge());
			ps.setLong(n++,pInfo.getIsRecognizance());			
			ps.setLong(n++,pInfo.getLoanID());
            
			lResult=ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			
			return pInfo.getLoanID();

		}catch(Exception e){
			
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}finally{
			cleanup(ps);
			cleanup(conn);   
		}

	}
	/**
	 * 更新贷款申请状态，在提交，取消申请时使用
	 * @param loanID
	 * @param newStatusID
	 * @return long
	 * @throws Exception
	 */
	public long updateLoanStatus(long loanID, long newStatusID) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long    lResult=-1;
           
		try 
		{
			conn = Database.getConnection();
			strSQL = " update OB_loan set nStatusID=? where ID=?";
            //System.out.println("loanID:"+loanID+"---newStatusID:"+newStatusID);    
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,newStatusID);
			ps.setLong(2,loanID);
            
            lResult=ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			return loanID;

		}catch(Exception e){
			
			cleanup(ps);
			cleanup(conn);
			throw e;
		}finally{
			
			cleanup(ps);
			cleanup(conn);   
		}

	}
	public OBLoanApplyInfo findByID(long loanID) throws Exception
	{
		OBLoanApplyInfo applyInfo=new OBLoanApplyInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		String inputUserName="";
            
		try 
		{
			conn = Database.getConnection();
			strSQL = " select t.*,l.MADJUSTRATE,l.MSTAIDADJUSTRATE,l.NBANKINTERESTID as bankid from OB_loan t,loan_loanform l where t.ID=? and t.NINAPPLYID=l.id(+)";
               
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,loanID);
           System.out.println(loanID+"strssql========"+strSQL);
			rs=ps.executeQuery();
			if (rs.next())
			{
				applyInfo.setID(rs.getLong("ID"));
				applyInfo.setTypeID(rs.getLong("ntypeid"));
				applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
				applyInfo.setOfficeID(rs.getLong("nofficeid"));
				applyInfo.setApplyCode(rs.getString("sinapplycode"));
				applyInfo.setInstructionNo(rs.getString("sInstructionNo"));
				applyInfo.setInApplyID( rs.getLong("nInapplyID"));
				applyInfo.setConsignClientID(rs.getLong("nconsignclientid"));
				applyInfo.setBorrowClientID(rs.getLong("nborrowclientid"));
				applyInfo.setInputUserID(rs.getLong("ninputuserid"));
				applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
				applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
				applyInfo.setLoanReason(rs.getString("sloanreason"));
				applyInfo.setLoanPurpose(rs.getString("sloanpurpose"));
				applyInfo.setOther(rs.getString("sother"));
				applyInfo.setBankInterestID(rs.getLong("bankid"));
				applyInfo.setChargeRate(rs.getDouble("mchargerate"));
				applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
				applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
				applyInfo.setEndDate(rs.getTimestamp("dtenddate"));
				applyInfo.setClientInfo(rs.getString("sclientinfo"));
				applyInfo.setSelfAmount(rs.getDouble("mselfamount"));
				applyInfo.setInterestRate(rs.getDouble("mInterestRate"));
				applyInfo.setIsCircle(rs.getLong("niscircle"));
				applyInfo.setIsSaleBuy(rs.getLong("nissalebuy"));
				applyInfo.setIsTechnical(rs.getLong("nistechnical"));
				applyInfo.setIsCredit(rs.getLong("niscredit"));
				applyInfo.setIsAssure(rs.getLong("nisassure"));
				applyInfo.setIsImpawn(rs.getLong("nisimpawn"));
				applyInfo.setIsPledge(rs.getLong("nispledge"));
				applyInfo.setInterestTypeID(rs.getLong("nInterestTypeID"));
				applyInfo.setExamineAmount(rs.getDouble("mExamineAmount"));
				applyInfo.setStatusID(rs.getLong("nStatusID"));
				//applyInfo.setNextCheckUserID(rs.getLong("nNextCheckUserID"));
				applyInfo.setIsCanModify(rs.getLong("IsCanModify"));
				applyInfo.setChargeRateTypeID(rs.getLong("nChargeRateTypeID"));
				applyInfo.setRiskLevel(rs.getLong("nRiskLevel"));
				applyInfo.setTypeID1(rs.getLong("nTypeID1"));
				applyInfo.setTypeID2(rs.getLong("nTypeID2"));
				applyInfo.setTypeID3(rs.getLong("nTypeID3"));
				applyInfo.setNBankAcceptPO( rs.getLong("nBankAcceptPO"));
				applyInfo.setNBizAcceptPO(rs.getLong("nBizAcceptPO"));
				applyInfo.setCheckAmount(rs.getDouble("mCheckAmount"));
				applyInfo.setDiscountRate(rs.getDouble("mDiscountRate"));
				applyInfo.setDiscountDate(rs.getTimestamp("dtDiscountDate"));
				applyInfo.setAdjustRate( rs.getDouble("mAdjustRate"));
				applyInfo.setStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));  //固定浮动利率
				applyInfo.setSubTypeId(rs.getLong("nsubtypeid"));
				//担保业务
				applyInfo.setAssureChargeRate(rs.getDouble("AssureChargeRate")); 	//担保费率
				applyInfo.setAssureChargeTypeID(rs.getLong("AssureChargeTypeID"));	//担保费收取方式
				applyInfo.setBeneficiary(rs.getString("Beneficiary"));				//受益人
				applyInfo.setAssureTypeID1(rs.getLong("AssureTypeID1"));	 		//担保类型1
				applyInfo.setAssureTypeID2(rs.getLong("AssureTypeID2"));	 //担保类型2
				applyInfo.setIsRecognizance(rs.getLong("isRecognizance"));//是否保证金
				System.out.println(rs.getLong("isRecognizance")+"strssql===00000====="+rs.getLong("nsubtypeid"));
				 System.out.println(applyInfo.getSubTypeId()+"strssql========"+applyInfo.getIsRecognizance());   
				OBLoanAssureDao fadao=new OBLoanAssureDao();
				Collection c=fadao.findByLoanID(applyInfo.getID(),new OBPageInfo() );
				applyInfo.setAssureInfo(c);
                   
				OBLoanPlanDao planDao=new OBLoanPlanDao();
				long planCount=planDao.findCountByLoanID(loanID);
				applyInfo.setPlanDetailCount(planCount);
					
				long planVersion=planDao.findVersionByLoanID(loanID);
				applyInfo.setPlanVersion(planVersion);
				
				applyInfo.setPlanPayAmount(planDao.findSumPlanAmount(rs.getLong("ID"),LOANConstant.PlanType.PAY ));
				applyInfo.setPlanRepayAmount(planDao.findSumPlanAmount(rs.getLong("ID"),LOANConstant.PlanType.REPAY));
                          
			}
			cleanup(rs);
			cleanup(ps);
			strSQL = " select sName from OB_user where ID=? ";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,applyInfo.getInputUserID() );
			rs=ps.executeQuery();
			if ( rs.next() )
			{
				applyInfo.setInputUserName(rs.getString("sName"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch(Exception e){
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            	
		}
		return applyInfo;           
	}
	
	public OBLoanApplyInfo findByID(long loanID,OBPageInfo obPInfo) throws Exception
	{
		OBLoanApplyInfo applyInfo=new OBLoanApplyInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		String inputUserName="";
            
		try 
		{
			conn = Database.getConnection();
			strSQL = " select * from OB_loan where ID=? ";
               
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,loanID);
           
			rs=ps.executeQuery();
			if (rs.next())
			{
				applyInfo.setID(rs.getLong("ID"));
				applyInfo.setTypeID(rs.getLong("ntypeid"));
				applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
				applyInfo.setOfficeID(rs.getLong("nofficeid"));
				//applyInfo.setApplyCode(rs.getString("sapplycode"));
				applyInfo.setInstructionNo(rs.getString("sInstructionNo"));
				applyInfo.setInApplyID( rs.getLong("nInapplyID"));
				applyInfo.setConsignClientID(rs.getLong("nconsignclientid"));
				applyInfo.setBorrowClientID(rs.getLong("nborrowclientid"));
				applyInfo.setInputUserID(rs.getLong("ninputuserid"));
				applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
				applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
				applyInfo.setLoanReason(rs.getString("sloanreason"));
				applyInfo.setLoanPurpose(rs.getString("sloanpurpose"));
				applyInfo.setOther(rs.getString("sother"));
				applyInfo.setBankInterestID(rs.getLong("nbankinterestid"));
				applyInfo.setChargeRate(rs.getDouble("mchargerate"));
				applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
				applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
				applyInfo.setEndDate(rs.getTimestamp("dtenddate"));
				applyInfo.setClientInfo(rs.getString("sclientinfo"));
				applyInfo.setSelfAmount(rs.getDouble("mselfamount"));
				applyInfo.setInterestRate(rs.getDouble("mInterestRate"));
				applyInfo.setIsCircle(rs.getLong("niscircle"));
				applyInfo.setIsSaleBuy(rs.getLong("nissalebuy"));
				applyInfo.setIsTechnical(rs.getLong("nistechnical"));
				applyInfo.setIsCredit(rs.getLong("niscredit"));
				applyInfo.setIsAssure(rs.getLong("nisassure"));
				applyInfo.setIsImpawn(rs.getLong("nisimpawn"));
				applyInfo.setIsPledge(rs.getLong("nispledge"));
				applyInfo.setInterestTypeID(rs.getLong("nInterestTypeID"));
				applyInfo.setExamineAmount(rs.getDouble("mExamineAmount"));
				applyInfo.setStatusID(rs.getLong("nStatusID"));
				//applyInfo.setNextCheckUserID(rs.getLong("nNextCheckUserID"));
				applyInfo.setIsCanModify(rs.getLong("IsCanModify"));
				applyInfo.setChargeRateTypeID(rs.getLong("nChargeRateTypeID"));
				applyInfo.setRiskLevel(rs.getLong("nRiskLevel"));
				applyInfo.setTypeID1(rs.getLong("nTypeID1"));
				applyInfo.setTypeID2(rs.getLong("nTypeID2"));
				applyInfo.setTypeID3(rs.getLong("nTypeID3"));
				applyInfo.setNBankAcceptPO( rs.getLong("nBankAcceptPO"));
				applyInfo.setNBizAcceptPO(rs.getLong("nBizAcceptPO"));
				applyInfo.setCheckAmount(rs.getDouble("mCheckAmount"));
				applyInfo.setDiscountRate(rs.getDouble("mDiscountRate"));
				applyInfo.setDiscountDate(rs.getTimestamp("dtDiscountDate"));
				//applyInfo.setAdjustRate( rs.getDouble("mAdjustRate"));
				applyInfo.setSubTypeId(rs.getLong("nsubtypeid"));
				//担保业务
				applyInfo.setAssureChargeRate(rs.getDouble("AssureChargeRate")); 	//担保费率
				applyInfo.setAssureChargeTypeID(rs.getLong("AssureChargeTypeID"));	//担保费收取方式
				applyInfo.setBeneficiary(rs.getString("Beneficiary"));				//受益人
				applyInfo.setAssureTypeID1(rs.getLong("AssureTypeID1"));	 		//担保类型1
				applyInfo.setAssureTypeID2(rs.getLong("AssureTypeID2"));	 //担保类型2
				applyInfo.setIsRecognizance(rs.getLong("isRecognizance"));//是否保证金
                    
				OBLoanAssureDao fadao=new OBLoanAssureDao();
				Collection c=fadao.findByLoanID(applyInfo.getID(),obPInfo );
				applyInfo.setAssureInfo(c);
                   
				OBLoanPlanDao planDao=new OBLoanPlanDao();
				long planCount=planDao.findCountByLoanID(loanID);
				applyInfo.setPlanDetailCount(planCount);
					
				long planVersion=planDao.findVersionByLoanID(loanID);
				applyInfo.setPlanVersion(planVersion);
				
				applyInfo.setPlanPayAmount(planDao.findSumPlanAmount(rs.getLong("ID"),LOANConstant.PlanType.PAY ));
				applyInfo.setPlanRepayAmount(planDao.findSumPlanAmount(rs.getLong("ID"),LOANConstant.PlanType.REPAY));
                          
			}
			cleanup(rs);
			cleanup(ps);
			strSQL = " select sName from OB_user where ID=? ";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,applyInfo.getInputUserID() );
			rs=ps.executeQuery();
			if ( rs.next() )
			{
				applyInfo.setInputUserName(rs.getString("sName"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch(Exception e){
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            	
		}
		return applyInfo;           
	}
    
	private String getAddress(String strProvince, String strCity, String sAddress) throws Exception
	{
		String strAddress = "";
		try
		{
			if (strProvince != null && strProvince.trim().length() > 0)
			{
				strAddress = strProvince + "省";
			}
			if (strCity != null && strCity.trim().length() > 0)
			{
				strAddress = strAddress + strCity + "市";
			}
			strAddress = strAddress + sAddress;
		}
		catch (Exception e)
		{
			log.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strAddress;
	}
	/**
	 * 检查贷款申请权限，包括录入人，币种，办事处等信息
	 * @param loanID
	 * @param sInfo  安全信息
	 * @return long
	 * @throws Exception
	 */
	public long checkLoanSecurity(long loanID,OBSecurityInfo sInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		OBSecurityInfo applyInfo=new OBSecurityInfo();
		long loanStatusID=-1;
            
		try 
		{
			conn = Database.getConnection();
			strSQL = " select * from OB_loan where ID=? ";
			ps=conn.prepareStatement(strSQL);
			ps.setLong(1,loanID);
			rs=ps.executeQuery();
			if (rs.next())
			{
				applyInfo.setCurrencyID(rs.getLong("ncurrencyid"));
				applyInfo.setOfficeID(rs.getLong("nofficeid"));		
				applyInfo.setUserID(rs.getLong("ninputuserid"));
				loanStatusID=rs.getLong("nStatusID");
				if ((loanStatusID>OBConstant.LoanInstrStatus.SUBMIT) ||(loanStatusID<=0) )
				{
					log.print("该笔贷款申请的状态："+loanStatusID);
					throw new IException("OB_E006");
				}
				else
				{
					if (applyInfo.getOfficeID()!=sInfo.getOfficeID() )
					{
						log.print("officeID does not match!");
						throw new IException("OB_E005");
					}
					if (applyInfo.getCurrencyID()!=sInfo.getCurrencyID() )
					{
						log.print("currencyID does not match!");
						throw new IException("OB_E005");
					}
					if (applyInfo.getUserID()!=sInfo.getUserID() )
					{
						log.print("InputUserID does not match!");
						throw new IException("OB_E005");
					}
				}
			}
			else
			{
				log.print("没有找到所要求的贷款申请！");
				throw new IException("OB_E007");
			}
			
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch(Exception e){
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);            	
		}
		return 1;
	}

	public static void main(String[] args)
	{
	}
}
