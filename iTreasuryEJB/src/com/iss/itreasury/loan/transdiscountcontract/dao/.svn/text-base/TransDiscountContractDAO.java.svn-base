/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountcontract.dao;


import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountContractContentDao;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractQueryInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.securities.util.SECConstant;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountContractDAO extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
    
    private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(CallableStatement cs) throws SQLException {
		try {
			if (cs != null) {
				cs.close();
				cs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}
    
	
    public TransDiscountContractDAO(String strTableName)
	{
		super(strTableName,true);
	}
    public static void main(String[] args)
    throws java.rmi.RemoteException, LoanException
{
    TransDiscountContractDAO dao = new TransDiscountContractDAO("Loan_ContractForm");
    TransDiscountContractInfo info = new TransDiscountContractInfo();
    TransDiscountContractBillInfo binfo = new TransDiscountContractBillInfo();
    TransDiscountContractQueryInfo qinfo = new TransDiscountContractQueryInfo();
    Collection c = null;
    //long l = dao.getNextCheckLevelByContractID(3000);
    Log4j log = new Log4j(Constant.ModuleType.LOAN, dao);
    
    //dao.findBillByTransDiscountContractID(500);
   // dao.doAfterCheckOver(3117);
    
    //log.print("nextchecklevel:"+l);
    //info.setId(500);
    /*
    try {
    	
		c = dao.getContractContent(370);
		log.print("contractcontent size:"+c.size());
	} catch (LoanDAOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//*/
    /*
    binfo.setContractID(30001);
    binfo.setId(11111);
    binfo.setAddDays(10);
    binfo.setCheckAmount(1000);
    binfo.setIsLocal(1);
    binfo.setRepurchaseTerm(200);
    Timestamp time = new Timestamp(0, 0, 0, 0, 0, 0, 0);
    binfo.setRepurchaseDate(time);
    dao.addBillRelation(binfo);
    //*/
    /*
    try
    {
        info = (TransDiscountContractInfo) dao.findByID(224, info.getClass());
        if (info != null)
        {
            System.out.println("id=" + info.getId());
        }
        else
        {
            System.out.println("info=null");
        }
            
    }
    catch (ITreasuryDAOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    */
    /////////////////////////////////////////////////
    
    try
    {
        qinfo.setQueryPurpose(2);
        qinfo.setCurrencyId(1);
        qinfo.setOfficeId(1);
        qinfo.setUserId(26);
        //qinfo.setStartTransDiscountContractId(50);
       // qinfo.setEndTransDiscountContractId(3500);
       // qinfo.setStartAmount(1000);
       // qinfo.setEndAmount(200000);
        //qinfo.setClientId(1);
            
        qinfo.setPageNo(1);
        qinfo.setPageLineCount(1000);
            
        c=dao.cpfFindByMultiOption(qinfo);
        if(c != null)
        {
            System.out.println("c.size = "+c.size());
        }
        else
        {
            System.out.println("c is null");
        }
    }
    catch (LoanException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (RemoteException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    //*/
    ///////////////////////////////////////////
    /*
   try
    {
         qinfo.setStartDiscountContractId(50);
            qinfo.setEndDiscountContractId(500);
            qinfo.setStartTransDiscountContractId(10);
            qinfo.setEndTransDiscountContractId(600);
            qinfo.setStartAmount(10);
            qinfo.setEndAmount(200000);
            //qinfo.setCode("010");
                
            qinfo.setPageNo(1);
            qinfo.setPageLineCount(1000);
                
            c=dao.findTransDiscountApplyBill(qinfo);
            if(c != null)
            {
                System.out.println("c.size = "+c.size());
            }
            else
            {
                System.out.println("c is null");
            }
    }
    catch (LoanException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (RemoteException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    /*
    ///////////////////////////////////////////
    try
    {
        //info.setId(info.);
        info.setCurrencyId(1);
        info.setOfficeId(1);
        info.setApplyCode(dao.createApplyCode(1,1,1));
        info.setBankAcceptPo(1);
        info.setBizAcceptPo(0);
        info.setLoanAmount(1000.00);
        //info.setStartDate(DataFormat.parseDate("2004-08-16",1));
        info.setLoanReason("1111");
        info.setLoanPurpose("123");
        info.setTypeId(LOANConstant.LoanType.ZTX);
        info.setBorrowClientId(1);
        info.setStatusId(LOANConstant.LoanStatus.SAVE);
        Log.print("applyCode:"+info.getApplyCode());
        //*/
        /*
        info.setId(135);
        info.setStatusId(LOANConstant.LoanStatus.CANCEL);
        dao.update(info);
        ///
        //*
        if(info.getId() < 0)
        {
            dao.setUseMaxID();
            dao.add(info);
        }
        else
        {
            dao.update(info);
        }//
    }
    catch (ITreasuryDAOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    //*/
    ///////////////////////////////////////////
}
/**
 * 中油
 * 根据贷款种类生成信贷(外汇)合同编号
 * 操作数据库表<LoanInfo>or<DiscountApply>
 * @param     long     lTypeID            贷款种类(1-12)见Notes.java,贴现=13需额外处理
 * @return    String   sApplyCode         信贷申请书编号
 **/
public String createContractCode(long lClientID, long lOfficeID,long lInOrOut)
    throws java.rmi.RemoteException, LoanException
{
    ResultSet rs = null;
    String strSQL = "";
    long lMaxCode = 0;
    String sApplyCode = "";
    String strOfficeName = "";
    String strClientCode = "0000";
    String strYear = "";
    String ApplyType = "";
    try
    {
        try
        {
            initDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        try
        {
            if(lInOrOut == LOANConstant.TransDiscountInOrOut.IN)
            {
                ApplyType = "ZTR";
            }
            else
            {
                ApplyType = "ZTC";
            }
            //取两位的年份
            strSQL = " select to_char(sysdate,'yy') from dual ";
            log4j.debug(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            if (rs != null && rs.next())
            {
                strYear = rs.getString(1);
                log4j.info("Two Bits Year is: " + strYear);
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */
			             
            //取得办事处名称
//            if (Env.getProjectName().equals(Constant.ProjectName.CPF))//中油
//            {
//                strSQL = " SELECT SNAME FROM OFFICE WHERE ID = " + lOfficeID;
//                prepareStatement(strSQL);
//                rs = executeQuery();
//                if (rs != null && rs.next())
//                {
//                    strOfficeName = rs.getString(1);
//                    log4j.info("*** 取得办事处名称: " + strOfficeName);
//                }
//                if (strOfficeName.indexOf("大庆") >= 0)
//                {
//                    strOfficeName = "-dq";
//                }
//                else if (strOfficeName.indexOf("沈阳") >= 0)
//                {
//                    strOfficeName = "-sy";
//                }
//                else if (strOfficeName.indexOf("吉林") >= 0)
//                {
//                    strOfficeName = "-jl";
//                }
//                else if (strOfficeName.indexOf("西安") >= 0)
//                {
//                    strOfficeName = "-xa";
//                }
//                else
//                {
//                    strOfficeName = "";
//                }
//                if (rs != null)
//                {
//                    rs.close();
//                    rs = null;
//                }
//            }

			/*  TOCONFIG—END  */
            
            //客户编号后4位
            strSQL =
                " select nvl(substr(sCode,4,4),'0000') from Client where ID= "
                    + lClientID;
            prepareStatement(strSQL);
            rs = executeQuery();
            if (rs != null && rs.next())
            {
                strClientCode = rs.getString(1);
                log4j.info("sClientCode is: " + strClientCode);
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            //计算最大序列号
            strSQL =
                " select nvl(max(substr(sContractCode,11,4)),0)+1  "
                    + " from loan_ContractForm where sContractCode like 'Y"
                    + strYear
                    + ApplyType
                    + "%' and sContractCode  not like '%(T)%' and nOfficeID = "
                    + lOfficeID;
            prepareStatement(strSQL);
            rs = executeQuery();
            if (rs != null && rs.next())
            {
                lMaxCode = rs.getLong(1);
                log4j.info("MaxCode is: " + lMaxCode);
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            sApplyCode =
                "Y"
                    + strYear
                    + ApplyType
                    + strClientCode
                    + DataFormat.formatInt(lMaxCode, 4, true)
                    + strOfficeName;
        }
        catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new LoanDAOException(e1);
        }
        catch (SQLException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
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
    
    return (sApplyCode);
}
/**
 *申请书的单笔查询操作
*/
public TransDiscountContractInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
{
    TransDiscountContractInfo info = new TransDiscountContractInfo();
    ResultSet rs = null;
    String strSQL = "";

    try
    {
        initDAO();
            
        strSQL = " select * from  " + this.strTableName
            + " where id=" + lID;
            System.out.println(strSQL);
        Log.print(strSQL);
        prepareStatement(strSQL);
        rs = executeQuery();
            
        if (rs != null && rs.next())
        {
            //贴现申请
            info.setId(lID); //贴现申请标识
            info.setLoanId(rs.getLong("nLoanId"));
            info.setApplyCode(rs.getString("sApplyCode")); //贴现申请编号
            info.setContractCode(rs.getString("sContractCode")); //贴现申请编号
            info.setTypeId(rs.getLong("nTypeId"));//贷款种类
            info.setNSubtypeid(rs.getLong("NSUBTYPEID"));
            info.setCurrencyId(rs.getLong("nCurrencyId"));//币种标识
            info.setOfficeId(rs.getLong("nOfficeId"));//办事处标识
            //申请单位
            info.setBorrowClientId(rs.getLong("nBorrowClientID")); //申请单位Id
            info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额(申请)
            info.setCheckAmount(rs.getDouble("mCheckAmount")); //核定金额（申请）
            info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率（申请）
            info.setDiscountDate(rs.getTimestamp("DTDISCOUNTDATE"));//转贴现日
            info.setDiscountTypeId(rs.getLong("NDISCOUNTTYPEID"));//转贴现种类
            info.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//回购种类
            info.setInOrOut(rs.getLong("NINOROUT"));            
            info.setStatusId(rs.getLong("nStatusID")); //贴现申请状态            
            info.setLoanAmount(rs.getDouble("mLoanAmount")); //贴现申请金额
            info.setInputUserID(rs.getLong("nInputUserID"));
            info.setInputDate(rs.getTimestamp("dtInputDate"));
            info.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //下一个审核人标示
            info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
            info.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
            info.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
            info.setLoanReason(rs.getString("sLoanReason"));
            info.setLoanPurpose(rs.getString("sLoanPurpose"));
            info.setStartDate(rs.getTimestamp("dtStartDate"));//start日
            info.setEndDate(rs.getTimestamp("dtEndDate"));//start日
            try {
            	TransDiscountContractContentDao ccDao = new TransDiscountContractContentDao();
            	System.out.println(info.getId());
				Collection contractContent = ccDao.getContractContentByContractId(info.getId());
				info.setContractContent(contractContent);
			} catch (LoanDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
            
        finalizeDAO();
            
    }
    catch (ITreasuryDAOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (SQLException e)
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
    return info;
}
/**
 *申请书的单笔查询操作1
*/
public TransDiscountContractInfo findByID1(long lID) throws java.rmi.RemoteException, LoanException
{
    TransDiscountContractInfo info = new TransDiscountContractInfo();
    ResultSet rs = null;
    String strSQL = "";

    try
    {
        initDAO();
            
        strSQL = " select * from  " + this.strTableName
            + " where id=" + lID;
            System.out.println(strSQL);
        Log.print(strSQL);
        prepareStatement(strSQL);
        rs = executeQuery();
            
        if (rs != null && rs.next())
        {
            //贴现申请
            info.setId(lID); //贴现申请标识
            info.setLoanId(rs.getLong("nLoanId"));
            info.setApplyCode(rs.getString("sApplyCode")); //贴现申请编号
            info.setContractCode(rs.getString("sContractCode")); //贴现申请编号
            info.setTypeId(rs.getLong("nTypeId"));//贷款种类
            info.setNSubtypeid(rs.getLong("NSUBTYPEID"));
            info.setCurrencyId(rs.getLong("nCurrencyId"));//币种标识
            info.setOfficeId(rs.getLong("nOfficeId"));//办事处标识
            //申请单位
            info.setBorrowClientId(rs.getLong("nBorrowClientID")); //申请单位Id
            info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额(申请)
            info.setCheckAmount(rs.getDouble("mCheckAmount")); //核定金额（申请）
            info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率（申请）
            info.setDiscountDate(rs.getTimestamp("DTDISCOUNTDATE"));//转贴现日
            info.setDiscountTypeId(rs.getLong("NDISCOUNTTYPEID"));//转贴现种类
            info.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//回购种类
            info.setInOrOut(rs.getLong("NINOROUT"));            
            info.setStatusId(rs.getLong("nStatusID")); //贴现申请状态            
            info.setLoanAmount(rs.getDouble("mLoanAmount")); //贴现申请金额
            info.setInputUserID(rs.getLong("nInputUserID"));
            info.setInputDate(rs.getTimestamp("dtInputDate"));
            info.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //下一个审核人标示
            info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
            info.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
            info.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
            info.setLoanReason(rs.getString("sLoanReason"));
            info.setLoanPurpose(rs.getString("sLoanPurpose"));
            info.setStartDate(rs.getTimestamp("dtStartDate"));//start日
            info.setEndDate(rs.getTimestamp("dtEndDate"));//start日
            info.setCounterpartAcctID(rs.getLong("ncounterpartacctid"));//交易对手开户行账户ID
            try {
            	TransDiscountContractContentDao ccDao = new TransDiscountContractContentDao();
            	System.out.println(info.getId());
				Collection contractContent = ccDao.getContractContentByContractId1(info.getId());
				info.setContractContent(contractContent);
			} catch (LoanDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
            
        finalizeDAO();
            
    }
    catch (ITreasuryDAOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (SQLException e)
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
    return info;
}
/**
 *合同的审核操作
*/
public long check(ApprovalTracingInfo ATInfo) throws LoanDAOException
{					
	long lApprovalContentID = ATInfo.getApprovalContentID();
	
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
    
    long lNextUserID = ATInfo.getNextUserID();
    long lApprovalID = ATInfo.getApprovalID();
    long lUserID = ATInfo.getInputUserID();
    long lLevel = -1;
    TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
    
    ApprovalSettingInfo appInfo = new ApprovalSettingInfo();
	ApprovalTracingInfo info = new ApprovalTracingInfo();
	ApprovalDelegation appbiz = new ApprovalDelegation();
	
    try
    {
        //获得ApprovalID
//        lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,ATInfo.getOfficeID(),ATInfo.getCurrencyID());    
		//下一级审核人级别
		lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
		Log.print("下一级审核人级别：" + lLevel);
		//审批设置
		appInfo = appbiz.findApprovalSetting(lApprovalID);
    } 
    catch (Exception e2)
    {
        // TODO Auto-generated catch block
        e2.printStackTrace();
    }
//////////////////////////////////////////////////////////////////////
    if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
    {
        cInfo.setId(lApprovalContentID);
        cInfo.setStatusId(LOANConstant.ContractStatus.REFUSE);
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    }
    if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
    {
        Log.print("====check====");
        cInfo.setId(lApprovalContentID);
        //:
        cInfo.setStatusId(LOANConstant.ContractStatus.APPROVALING);
//        cInfo.setStatusId(LOANConstant.ContractStatus.SUBMIT);
        //:~
        cInfo.setNextCheckUserId(lNextUserID);
        //aInfo.setNextCheckLevel(lNextLevel+1);
        if (appInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
		{
            cInfo.setNextCheckLevel(lLevel);
		    Log.print("更新下一个审核级别（可越级）：" + lLevel);
		}
		else
		{
		    cInfo.setNextCheckLevel(getNextCheckLevelByContractID(lApprovalContentID) + 1);
		    Log.print("更新下一个审核级别（不可越级）：" + getNextCheckLevelByContractID(lApprovalContentID) + 1);
		}
        
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    }
    if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER)
        //审批&&最后
    {
        cInfo.setId(lApprovalContentID);
        cInfo.setStatusId(LOANConstant.ContractStatus.CHECK);
        cInfo.setNextCheckUserId(lNextUserID);
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        //审批完成后需要做的操作
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        //审批完成后需要做的操作
        try
        {
            
            doAfterCheckOver(lApprovalContentID);
        }
        catch (LoanException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (RemoteException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
    {
        cInfo.setId(lApprovalContentID);
        //:
        cInfo.setStatusId(LOANConstant.ContractStatus.SUBMIT);
//        cInfo.setStatusId(LOANConstant.ContractStatus.SUBMIT);
        //:~
        cInfo.setNextCheckUserId(ATInfo.getInputUserID());
        //置下一级审核为1
        cInfo.setNextCheckLevel(1);
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    log4j.debug("check end");
    
	return lApprovalContentID;
}

private long getNextCheckLevelByContractID(long contractId) throws LoanDAOException
{
	long nextCheckLevel = -1;
    String strSQL = "";
    strSQL = " select nNextCheckLevel from loan_contractform where 1 = 1 ";
    strSQL += " and id = " + contractId;
    try
    {
        try
        {
            initDAO();
            prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            while (rs != null && rs.next())
            {
                nextCheckLevel = rs.getLong("nNextCheckLevel");
            }
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
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

/**
 *  //: new - 加入金额审批效验 //:~ 
 * @param qInfo
 * @return
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
public Collection findByMultiOption1(TransDiscountContractQueryInfo qInfo)
throws java.rmi.RemoteException, LoanException
{
Vector v = new Vector();
    
	StringBuffer strCount = new StringBuffer();
	StringBuffer strSelect = new StringBuffer();
	StringBuffer strOrder = new StringBuffer();
	String tempSQL="";
	String strSQL = "";
	String strSQL2="";
	String UNION=" union ";
	
	//取得查询条件
	long clientID = qInfo.getClientId();
	long queryPurpose = qInfo.getQueryPurpose();
    long officeID = qInfo.getOfficeId();
    long currencyID = qInfo.getCurrencyId();
    long startId = qInfo.getStartTransDiscountContractId();
    long endId = qInfo.getEndTransDiscountContractId();
    String startCode = qInfo.getStartTransDiscountContractCode();
    String endCode = qInfo.getEndTransDiscountContractCode();
    double startAmount = qInfo.getStartAmount();
    double endAmount = qInfo.getEndAmount();
    long statusID = qInfo.getQueryStatusId();
    long userID = qInfo.getUserId();
    Timestamp startLoanDate = qInfo.getStartLoanDate();
    Timestamp endLoanDate = qInfo.getEndLoanDate();
    Timestamp startInputDate = qInfo.getStartInputDate();
    Timestamp endInputDate = qInfo.getEndInputDate();
    
    ApprovalDelegation appBiz = new ApprovalDelegation();
    String strUser = qInfo.getUser();//审核的虚拟用户组
    
    long pageLineCount = qInfo.getPageLineCount();
    long pageNo = qInfo.getPageNo();
    long orderParam = qInfo.getOrderParam();
    long desc = qInfo.getDesc();
    String orderParamString = qInfo.getOrderParamString();
    //存放查询结果和查询控制的变量
    double totalAmount = 0.0;
    long lRecordCount = -1;
    long lPageCount = -1;
    long lRowNumStart = -1;
    long lRowNumEnd = -1;
    
    long lSubLoanTypeID=23;
    long lActionID=Constant.ApprovalAction.LOAN_CONTRACT;
    
    try{
        
        try{
               initDAO();
        }
        catch (ITreasuryDAOException e){
        	throw new LoanDAOException(e);
        }	        
    	
    	//: 审批SQL //:~
        strSQL="(SELECT aa.*,-1 moneysegment,-1 approvalid from "+this.strTableName+" aa";
        strSQL+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
        strSQL+=" where (a.NNEXTUSERID="+userID+" ";
        strSQL+=" and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
        strSQL+="  and a.NMODULEID="+Constant.ModuleType.CRAFTBROTHER+" and nstatusid="+Constant.RecordStatus.VALID+"";
        strSQL+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
        strSQL+=" where aa.id =d.NAPPROVALCONTENTID and aa.nstatusid="+LOANConstant.ContractStatus.APPROVALING;
        strSQL+=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+"";
        
        if(queryPurpose==2){
        	if(statusID>0)
        	strSQL+=" \n and aa.nstatusid ="+statusID;
        }        
        if(startId > 0 )
        {
        	strSQL+=" \n and aa.ID >= " + startId;
        }
        if(endId > 0 )
        {
        	strSQL+=" \n and aa.ID <= " + endId;
        }
        if (clientID != -1)
        {
        	strSQL+=" \n and aa.nBorrowClientID = " + clientID;
        }
        if (startAmount != 0)
        {
        	strSQL+=" \n and aa.mLoanAmount >= " + startAmount;
        }
        if (endAmount != 0)
        {
        	strSQL+=" \n and aa.mLoanAmount <= " + endAmount;
        }
        if (currencyID > 0)
        {
        	strSQL+=" \n and aa.nCurrencyID = " + currencyID;
        }
        if (officeID > 0)
        {
        	strSQL+=" \n and aa.nOfficeID = " + officeID;
        }
        if (startInputDate != null)
        {
        	strSQL+=
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startInputDate)
                    + "'";
        }
        if (endInputDate != null)
        {
        	strSQL+=
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endInputDate)
                    + "'";
        }
        if (startLoanDate != null)
        {
        	strSQL+=
                " \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startLoanDate)
                    + "'";
        }
        if (endLoanDate != null)
        {
        	strSQL+=
                " \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endLoanDate)
                    + "'";
        }
        
        //strSQL+=") union ( ";
         
        strSQL2 = " (select d.* from (";
        strSQL2 += " select aaa.* from (";
        strSQL2 += " select aa.*,rr.moneysegment,rr.approvalid from "+this.strTableName+" aa,loan_approvalrelation rr";
        strSQL2 += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyID;
        strSQL2 += " and rr.actionid = " +lActionID ;
        strSQL2 += " and rr.subloantypeid = "+lSubLoanTypeID ;    
        strSQL2 +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid ="+LOANConstant.ContractStatus.SUBMIT;
        
//        if(queryPurpose==1){
//            	strSQL2+=" \n and aa.nstatusid in(" + LOANConstant.ContractStatus.SAVE+","+LOANConstant.ContractStatus.SUBMIT+")";
//        }
//        
        if(queryPurpose==2){
        	if(statusID>0)
        	strSQL2+=" \n and aa.nstatusid ="+statusID;
        }
        
        if(startId > 0 )
        {
        	strSQL2+=" \n and aa.ID >= " + startId;
        }
        if(endId > 0 )
        {
        	strSQL2+=" \n and aa.ID <= " + endId;
        }
        if (clientID != -1)
        {
        	strSQL2+=" \n and aa.nBorrowClientID = " + clientID;
        }
        if (startAmount != 0)
        {
        	strSQL2+=" \n and aa.mLoanAmount >= " + startAmount;
        }
        if (endAmount != 0)
        {
        	strSQL2+=" \n and aa.mLoanAmount <= " + endAmount;
        }
        if (currencyID > 0)
        {
        	strSQL2+=" \n and aa.nCurrencyID = " + currencyID;
        }
        if (officeID > 0)
        {
        	strSQL2+=" \n and aa.nOfficeID = " + officeID;
        }

        if (startInputDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startInputDate)
                    + "'";
        }
        if (endInputDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endInputDate)
                    + "'";
        }
        if (startLoanDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startLoanDate)
                    + "'";
        }
        if (endLoanDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endLoanDate)
                    + "'";
        }           
        
        strSQL2 += " ) aaa,(";
        strSQL2 += " select aa.id,max(rr.moneysegment) maxamount from "+this.strTableName+" aa,loan_approvalrelation rr";
        strSQL2 += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyID;
        strSQL2 += " and rr.actionid = " +lActionID ;
        strSQL2 += " and rr.subloantypeid = "+lSubLoanTypeID ;            
        strSQL2 +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid ="+LOANConstant.ContractStatus.SUBMIT;
        
//        if(queryPurpose==1){
//        	strSQL2+=" \n and aa.nstatusid in(" + LOANConstant.ContractStatus.SAVE+","+LOANConstant.ContractStatus.SUBMIT+")";
//        }
//    
        if(queryPurpose==2){
        	if(statusID>0)
        	strSQL2+=" \n and aa.nstatusid ="+statusID;
        }
        
        if(startId > 0 )
        {
        	strSQL2+=" \n and aa.ID >= " + startId;
        }
        if(endId > 0 )
        {
        	strSQL2+=" \n and aa.ID <= " + endId;
        }
        if (clientID != -1)
        {
        	strSQL2+=" \n and aa.nBorrowClientID = " + clientID;
        }
        if (startAmount != 0)
        {
        	strSQL2+=" \n and aa.mLoanAmount >= " + startAmount;
        }
        if (endAmount != 0)
        {
        	strSQL2+=" \n and aa.mLoanAmount <= " + endAmount;
        }
        if (currencyID > 0)
        {
        	strSQL2+=" \n and aa.nCurrencyID = " + currencyID;
        }
        if (officeID > 0)
        {
        	strSQL2+=" \n and aa.nOfficeID = " + officeID;
        }
        if (startInputDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startInputDate)
                    + "'";
        }
        if (endInputDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endInputDate)
                    + "'";
        }
        if (startLoanDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startLoanDate)
                    + "'";
        }
        if (endLoanDate != null)
        {
        	strSQL2+=
                " \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endLoanDate)
                    + "'";
        }   
        
        strSQL2 += " group by aa.id ) bbb";
        strSQL2 += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
        strSQL2 += " loan_approvalsetting e,loan_approvalitem f";
        strSQL2 += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID+")";
        
        System.out.println("我的SQL^^^^^^^^^^^===="+strSQL+")"+UNION+strSQL2);
    	   
        // tempSQL
        tempSQL=strSQL+")"+UNION+strSQL2;
        
        System.out.println("我的SQL:"+tempSQL);
        
        try
        {
            long lID = -1;
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.strTableName);
            
            prepareStatement(tempSQL);
            ResultSet rs1 = executeQuery();
            while (rs1 != null && rs1.next())
            {
                lID = rs1.getLong("id");
                TransDiscountContractInfo info = new TransDiscountContractInfo();
                if(lID > 0)
                {
                    info=(TransDiscountContractInfo)dao.findByID(lID);//,info.getClass());

                    //当前查找总数
//                    info.setPageCount(lPageCount);
//                    info.setPageNo(pageNo);
//                    info.setRecordCount(lRecordCount);
//                    info.setTotalAmount(totalAmount);
                }
                //info.setDiscountDate(rs1.getTimestamp("dtDiscountDate"));//转贴现日
                //info.setInputDate(rs1.getTimestamp("dtInputDate"));
                
                v.add(info);
            }
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        catch (SQLException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    finally
    {
        try
        {
            finalizeDAO();
        } 
        catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }        
    return (v.size() > 0 ? v : null);    
}

/**
 * 合同的多笔查询操作
 * @param qInfo
 * @return
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
public Collection findByMultiOption(TransDiscountContractQueryInfo qInfo)
throws java.rmi.RemoteException, LoanException
{
	Vector v = new Vector();
    
    StringBuffer sbCount = new StringBuffer();
    StringBuffer sbSelect = new StringBuffer();
    StringBuffer sbSQL = new StringBuffer();
    StringBuffer sbOrder = new StringBuffer();
    String strSQL = "";
	//取得查询条件
	long clientID = qInfo.getClientId();
	long queryPurpose = qInfo.getQueryPurpose();
    long officeID = qInfo.getOfficeId();
    long currencyID = qInfo.getCurrencyId();
    long startId = qInfo.getStartTransDiscountContractId();
    long endId = qInfo.getEndTransDiscountContractId();
    String startCode = qInfo.getStartTransDiscountContractCode();
    String endCode = qInfo.getEndTransDiscountContractCode();
    double startAmount = qInfo.getStartAmount();
    double endAmount = qInfo.getEndAmount();
    long statusID = qInfo.getQueryStatusId();
    long userID = qInfo.getUserId();
    Timestamp startLoanDate = qInfo.getStartLoanDate();
    Timestamp endLoanDate = qInfo.getEndLoanDate();
    Timestamp startInputDate = qInfo.getStartInputDate();
    Timestamp endInputDate = qInfo.getEndInputDate();
    
    ApprovalDelegation appBiz = new ApprovalDelegation();
    String strUser = qInfo.getUser();//审核的虚拟用户组
    
    long pageLineCount = qInfo.getPageLineCount();
    long pageNo = qInfo.getPageNo();
    long orderParam = qInfo.getOrderParam();
    long desc = qInfo.getDesc();
    String orderParamString = qInfo.getOrderParamString();
    //存放查询结果和查询控制的变量
    double totalAmount = 0.0;
    long lRecordCount = -1;
    long lPageCount = -1;
    long lRowNumStart = -1;
    long lRowNumEnd = -1;
    
    try
    {
        try
        {
            initDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        //  计算记录总数   
        sbCount.setLength(0);
        
        
        sbSQL.setLength(0);
        sbSQL.append(" \n select * from LOAN_CONTRACTFORM aa  ");
        sbSQL.append(" \n where 1=1 ");
        sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
        sbSQL.append("  and  aa.nStatusID in ( " + LOANConstant.ContractStatus.SAVE +","+ LOANConstant.ContractStatus.SUBMIT +")");
        sbSQL.append(" \n and aa.nInputUserID = " + userID);
//        if (queryPurpose == 1) //for 修改
//        {
//            sbSQL.append(" \n and aa.nInputUserID = " + userID);
//            sbSQL.append(" \n and ");
//            sbSQL.append("  (  aa.nNextCheckLevel = 1 ");
//            sbSQL.append("  and  aa.nStatusID in ( " + LOANConstant.ContractStatus.SAVE +","+ LOANConstant.ContractStatus.SUBMIT +")");
//            sbSQL.append(" )");
//        }
//        if (queryPurpose == 2) //for 审核
//        {
//            sbSQL.append(" \n and aa.nStatusID = " + LOANConstant.ContractStatus.SUBMIT);
//            
//           
//            try
//            {
//                //获得真正来审批这个记录的人（真实或传给的虚拟的人！）
//                strUser = appBiz.findTheVeryUser(Constant.ModuleType.LOAN,Constant.ApprovalLoanType.ZTX,Constant.ApprovalAction.LOAN_CONTRACT,officeID,currencyID,userID);
//                
//            }
//            catch (Exception e1)
//            {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            if(strUser.length() <= 0)
//            {
//                strUser="("+userID+")";
//            }
//            sbSQL.append(" \n and aa.nNextCheckUserID in " + strUser);
//        }
        if(startId > 0 )
        {
            sbSQL.append(" \n and aa.ID >= " + startId);
        }
        if(endId > 0 )
        {
            sbSQL.append(" \n and aa.ID <= " + endId);
        }
        if (clientID != -1)
        {
            sbSQL.append(" \n and aa.nBorrowClientID = " + clientID);
        }
        if (startAmount != 0)
        {
            sbSQL.append(" \n and aa.mLoanAmount >= " + startAmount);
        }
        if (endAmount != 0)
        {
            sbSQL.append(" \n and aa.mLoanAmount <= " + endAmount);
        }
        if (currencyID > 0)
        {
            sbSQL.append(" \n and aa.nCurrencyID = " + currencyID);
        }
        if (officeID > 0)
        {
            sbSQL.append(" \n and aa.nOfficeID = " + officeID);
        }
        if (statusID > 0)
        {
            sbSQL.append(" \n and aa.nStatusID = " + statusID);
        }
        if (startInputDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startInputDate)
                    + "'");
        }
        if (endInputDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endInputDate)
                    + "'");
        }
        if (startLoanDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startLoanDate)
                    + "'");
        }
        if (endLoanDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endLoanDate)
                    + "'");
        }

        try
        {
            long lID = -1;
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.strTableName);
            System.out.println("----------:"+sbSQL.toString());
            prepareStatement(sbSQL.toString());
            ResultSet rs1 = executeQuery();
            while (rs1 != null && rs1.next())
            {
                lID = rs1.getLong("id");
                TransDiscountContractInfo info = new TransDiscountContractInfo();
                if(lID > 0)
                {
                    info=(TransDiscountContractInfo) dao.findByID(lID);//,info.getClass());
                    System.out.println("-====================================");
//                    //当前查找总数
//                    qInfo.setPageCount(lPageCount);
//                    qInfo.setPageNo(pageNo);
//                    qInfo.setRecordCount(lRecordCount);
//                    qInfo.setTotalAmount(totalAmount);
                }    
                v.add(info);
            }
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        catch (SQLException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
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
    
    return (v.size() > 0 ? v : null);

}

/**
 * 合同的多笔查询操作
 * @param qInfo
 * @return
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
public Collection findByMultiOption2(TransDiscountContractQueryInfo qInfo)
throws java.rmi.RemoteException, LoanException
{
	Vector v = new Vector();
    
    StringBuffer sbCount = new StringBuffer();
    StringBuffer sbSelect = new StringBuffer();
    StringBuffer sbSQL = new StringBuffer();
    StringBuffer sbOrder = new StringBuffer();
    String strSQL = "";
	//取得查询条件
	long clientID = qInfo.getClientId();
	long queryPurpose = qInfo.getQueryPurpose();
    long officeID = qInfo.getOfficeId();
    long currencyID = qInfo.getCurrencyId();
    long startId = qInfo.getStartTransDiscountContractId();
    long endId = qInfo.getEndTransDiscountContractId();
    String startCode = qInfo.getStartTransDiscountContractCode();
    String endCode = qInfo.getEndTransDiscountContractCode();
    double startAmount = qInfo.getStartAmount();
    double endAmount = qInfo.getEndAmount();
    long statusID = qInfo.getQueryStatusId();
    long userID = qInfo.getUserId();
    Timestamp startLoanDate = qInfo.getStartLoanDate();
    Timestamp endLoanDate = qInfo.getEndLoanDate();
    Timestamp startInputDate = qInfo.getStartInputDate();
    Timestamp endInputDate = qInfo.getEndInputDate();
    
    ApprovalDelegation appBiz = new ApprovalDelegation();
    String strUser = qInfo.getUser();//审核的虚拟用户组
    
    long pageLineCount = qInfo.getPageLineCount();
    long pageNo = qInfo.getPageNo();
    long orderParam = qInfo.getOrderParam();
    long desc = qInfo.getDesc();
    String orderParamString = qInfo.getOrderParamString();
    //存放查询结果和查询控制的变量
    double totalAmount = 0.0;
    long lRecordCount = -1;
    long lPageCount = -1;
    long lRowNumStart = -1;
    long lRowNumEnd = -1;
    
    try
    {
        try
        {
            initDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        //  计算记录总数   
        sbCount.setLength(0);
        
        
        sbSQL.setLength(0);
        sbSQL.append(" \n select * from LOAN_CONTRACTFORM aa  ");
        sbSQL.append(" \n where 1=1 ");
        sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
        sbSQL.append("  and  aa.nStatusID = " + LOANConstant.ContractStatus.SAVE );

        if(startId > 0 )
        {
            sbSQL.append(" \n and aa.ID >= " + startId);
        }
        if(endId > 0 )
        {
            sbSQL.append(" \n and aa.ID <= " + endId);
        }
        if (clientID != -1)
        {
            sbSQL.append(" \n and aa.nBorrowClientID = " + clientID);
        }
        if (startAmount != 0)
        {
            sbSQL.append(" \n and aa.mLoanAmount >= " + startAmount);
        }
        if (endAmount != 0)
        {
            sbSQL.append(" \n and aa.mLoanAmount <= " + endAmount);
        }
        if (currencyID > 0)
        {
            sbSQL.append(" \n and aa.nCurrencyID = " + currencyID);
        }
        if (officeID > 0)
        {
            sbSQL.append(" \n and aa.nOfficeID = " + officeID);
        }
        if (startInputDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startInputDate)
                    + "'");
        }
        if (endInputDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endInputDate)
                    + "'");
        }
        if (startLoanDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startLoanDate)
                    + "'");
        }
        if (endLoanDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endLoanDate)
                    + "'");
        }

        try
        {
            long lID = -1;
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.strTableName);
            
            prepareStatement(sbSQL.toString());
            ResultSet rs1 = executeQuery();
            while (rs1 != null && rs1.next())
            {
                lID = rs1.getLong("id");
                TransDiscountContractInfo info = new TransDiscountContractInfo();
                if(lID > 0)
                {
                    info=(TransDiscountContractInfo) dao.findByID(lID);//,info.getClass());

//                    //当前查找总数
//                    qInfo.setPageCount(lPageCount);
//                    qInfo.setPageNo(pageNo);
//                    qInfo.setRecordCount(lRecordCount);
//                    qInfo.setTotalAmount(totalAmount);
                }    
                v.add(info);
            }
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        catch (SQLException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
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
    
    return (v.size() > 0 ? v : null);

}

/**
 * 添加合同票据和合同的关系（即更新合同票据和合同关系表，插入一条记录）
 * @param info
 * @throws LoanDAOException
 */
public void addBillRelation(TransDiscountContractBillInfo info) throws LoanDAOException
{
	StringBuffer strSQL=new StringBuffer();
	long lTransDiscountContractId = info.getContractID();//取该票据所属的合同id
	long lDiscountContractBillId = info.getId(); 
	long cBillID=info.getBillSourceTypeID();
	long lIsLocal = info.getIsLocal();
	long lAddDays = info.getAddDays();
	Timestamp dtRepurchaseDate = info.getRepurchaseDate();
	long lRepurchaseTerm = info.getRepurchaseTerm();
	double lCheckAmount = info.getCheckAmount();
	
	try
    {
        try
        {
            initDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        
        if (lTransDiscountContractId > 0)
        {
        	strSQL.append("insert into RTRANSDISCOUNTCONTRACTBILL(TransDiscountContractId,DiscountContractBillId,IsLocal,AddDays,CheckAmount,RepurchaseTerm,RepurchaseDate,CbillID) values(");
        	strSQL.append(lTransDiscountContractId);strSQL.append(",");
        	strSQL.append(lDiscountContractBillId);strSQL.append(",");
        	strSQL.append(lIsLocal);strSQL.append(",");
        	strSQL.append(lAddDays);strSQL.append(",");
        	strSQL.append(lCheckAmount);strSQL.append(",");
        	strSQL.append(lRepurchaseTerm);strSQL.append(",");
        	strSQL.append("to_date('");
        	strSQL.append(DataFormat.getDateString(dtRepurchaseDate));strSQL.append("','yyyy-mm-dd')");strSQL.append(",");
        	strSQL.append(cBillID);strSQL.append(")");
        }
        try
        {
        	log4j.debug(strSQL.toString());
            prepareStatement(strSQL.toString());
            executeQuery();
            
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException("插入合同票据关系产生错误", e);
        }
        
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
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

/**
 * 添加合同票据和合同的关系（即更新合同票据和合同关系表，插入一条记录）
 * @param info
 * @throws LoanDAOException
 */
public void updateBillRelation(long BillID,long ContractID,double CheckAmount) throws LoanDAOException
{
	StringBuffer strSQL=new StringBuffer();
	
	
	try
    {
        try
        {
            initDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        
        if (ContractID > 0)
        {
        	strSQL.append("update RTRANSDISCOUNTCONTRACTBILL set CHECKAMOUNT="+CheckAmount);
        	strSQL.append("where TRANSDISCOUNTCONTRACTID="+ContractID);    			
        	strSQL.append("and DISCOUNTCONTRACTBILLID="+BillID);
        }
        try
        {
        	log4j.debug(strSQL.toString());
            prepareStatement(strSQL.toString());
            executeQuery();
            
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException("修改合同票据关系产生错误", e);
        }
        
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
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

public long findByBillID(long lApplyID) throws java.rmi.RemoteException, LoanException
{
	
	long id =-1;
	ResultSet rs = null;
    String strSQL = "";
    try
    {
        initDAO();        
        strSQL = " select * from  rtransdiscountcontractbill " 
            + " where cbillID=" + lApplyID;
            
        Log.print(strSQL);
        prepareStatement(strSQL);
        rs = executeQuery();
            
        if (rs != null && rs.next())
        {
            //贴现申请
      	id=  rs.getLong("DISCOUNTCONTRACTBILLID"); //贴现申请标识
       }           
        finalizeDAO();           
    }
    catch (ITreasuryDAOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (SQLException e)
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
	return id;
}
/**
 * 取得合同文本
 * @param lContractID
 * @return
 * @throws LoanDAOException
 * @throws Exception
 */
/* 把通过合同 id 取得合同文本集合的方法放到 TransDiscountContractContentDao 中
private Collection getContractContent(long lContractID) throws LoanDAOException,Exception 
{
	Vector vResult = new Vector();
	StringBuffer sbSQL = new StringBuffer(); 
	ContractContentDao dao = new ContractContentDao();
	ResultSet rs = null;
	PreparedStatement ps = null;
	try
    {
        initDAO();
    }
    catch (ITreasuryDAOException e)
    {
        throw new LoanDAOException(e);
    }
	try{
		sbSQL.append(" SELECT a.*,c.sName,'' as sAssureCode, -1 as nAssureTypeID ");
		sbSQL.append(" FROM loan_ContractContent a,loan_ContractForm b,Client c");
		sbSQL.append(" WHERE a.nContractID = b.ID");
		sbSQL.append(" AND b.nBorrowClientID = c.ID");
		sbSQL.append(" AND (a.nContractTypeID = " + LOANConstant.ContractType.LOAN);
		sbSQL.append(" OR a.nContractTypeID = " + LOANConstant.ContractType.ZTX + ")");
		sbSQL.append(" AND a.nContractID = ?");
		sbSQL.append(" UNION ");
		sbSQL.append(" SELECT a.*,c.sName,b.sAssureCode,b.nAssureTypeID ");
		sbSQL.append(" FROM loan_ContractContent a,loan_loanContractAssure b,Client c");
		sbSQL.append(" WHERE a.nContractID = b.nContractID");
		sbSQL.append(" AND a.nParentID = b.ID");
		sbSQL.append(" AND b.nClientID = c.ID");
		sbSQL.append(" AND b.nStatusID = " + Constant.RecordStatus.VALID);
		sbSQL.append(" AND a.nContractTypeID != " + LOANConstant.ContractType.LOAN);
		sbSQL.append(" AND a.nContractTypeID != " + LOANConstant.ContractType.ZTX);
		sbSQL.append(" AND a.nContractID = ?");
		
		log4j.info(sbSQL.toString());
		ps = prepareStatement(sbSQL.toString());
		ps.setLong(1, lContractID);
		ps.setLong(2, lContractID);
        rs = executeQuery();
        try {
			while (rs.next())
			{
				ContractContentInfo info = new ContractContentInfo();
				info.setID(rs.getLong("ID")); //ContentID
				//info.setSerialNo(rs.getLong("nSerialNo")); //序列号
				info.setContractTypeID(rs.getLong("nContractTypeID")); //合同类型ID
				info.setContractType(LOANConstant.ContractType.getName(info.getContractTypeID()));
				//合同类型
				info.setClientName(rs.getString("sName")); //单位名称
				info.setAssureTypeID(rs.getLong("nAssureTypeID")); //保证类型
				info.setCode(rs.getString("sAssureCode")); //保证合同编号
				String sPageName = dao.getContractJspName(info.getID(), info.getContractTypeID());
				info.setPageName(sPageName);

				vResult.add(info);
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	catch (ITreasuryDAOException e)
	{
	     throw new LoanDAOException("查找合同文本内容产生错误", e);
	}
	
    
    
	return vResult.size() > 0 ? vResult : null;
}

*/

/**
 * 审核完成以后，把该合同下的买入的票据置为可卖状态
 * @param lContractID
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
public void doAfterCheckOver(long lContractID) throws java.rmi.RemoteException, LoanException
{
	TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
	TransDiscountContractBillInfo cbInfo = new TransDiscountContractBillInfo();
	StringBuffer sbSQL = new StringBuffer();
	String strContractBillTable = "";
    ResultSet rs = null;
    
	/*  TOCONFIG—TODELETE  */
	/*
	 * 产品化不再区分项目 
	 * ninh 
	 * 2005-03-24
	 */
    
//	if(Env.getProjectName().equals("cpf"))
//	{
//		strContractBillTable = "Loan_DiscountContractBill";
//	}
//	else// if(Env.getProjectName().equals("hn"))
//	{
//		strContractBillTable = "Loan_DiscountContractBill";
//	}

	strContractBillTable = "Loan_DiscountContractBill";
	
	/*  TOCONFIG—END  */
	
    TransDiscountContractBillDAO billDAO = new TransDiscountContractBillDAO(strContractBillTable);
	
    //生成查找该合同下票据的 sql 语句
    sbSQL.append("select * from RTRANSDISCOUNTCONTRACTBILL ");
    sbSQL.append("where 1=1 ");
    sbSQL.append("and TransDiscountContractId=");sbSQL.append(lContractID);
    
    
    try {
	
		cInfo = findByID(lContractID);
		//Log.print(cInfo);
		initDAO();
		
		/*  TOCONFIG—TODELETE  */
		/*
		 * 产品化不再区分项目 
		 * ninh 
		 * 2005-03-24
		 */

		//如果是买入，更新票据为可卖状态
//		if (Env.getProjectName().equals("cpf"))
//		{
//			if (cInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
//			{
//				prepareStatement(sbSQL.toString());
//				Log.print(sbSQL.toString());
//				rs = executeQuery();
//				while (rs != null && rs.next())
//				{
//					cbInfo.setId(rs.getLong("DiscountContractBillId"));
//					cbInfo.setSellStatusID(Constant.YesOrNo.YES);
//					billDAO.update(cbInfo);
//				}
//			}
//		}
//		else
//		{
//			if (cInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN && cInfo.getDiscountTypeId()== LOANConstant.TransDiscountType.BUYBREAK)
//			{
//				prepareStatement(sbSQL.toString());
//				Log.print(sbSQL.toString());
//				rs = executeQuery();
//				while (rs != null && rs.next())
//				{
//					cbInfo.setId(rs.getLong("DiscountContractBillId"));
//					cbInfo.setSellStatusID(Constant.YesOrNo.YES);
//					billDAO.update(cbInfo);
//				}
//			}
//		
//			
//		}

		if (cInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN && cInfo.getDiscountTypeId()== LOANConstant.TransDiscountType.BUYBREAK)
		{
			prepareStatement(sbSQL.toString());
			Log.print(sbSQL.toString());
			rs = executeQuery();
			while (rs != null && rs.next())
			{
				cbInfo.setId(rs.getLong("DiscountContractBillId"));
				cbInfo.setSellStatusID(Constant.YesOrNo.YES);
				billDAO.update(cbInfo);
			}
		}
		
		/*  TOCONFIG—END  */
		
		finalizeDAO();
	} catch (ITreasuryDAOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e) {
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

/**
 * 中油项目的复核操作
 * @param ATInfo
 * @return
 * @throws LoanDAOException
 */
public long cpfCheck(ApprovalTracingInfo ATInfo) throws LoanDAOException
{
	long lApprovalContentID = ATInfo.getApprovalContentID();
	
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
    
    long lNextUserID = ATInfo.getNextUserID();
    long lApprovalID = ATInfo.getApprovalID();
    long lUserID = ATInfo.getInputUserID();
    TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
//////////////////////////////////////////////////////////////////////

    if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK)  //复核
    {
        cInfo.setId(lApprovalContentID);
        cInfo.setStatusId(LOANConstant.ContractStatus.CHECK);
        cInfo.setNextCheckUserId(lNextUserID);
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        //审批完成后需要做的操作
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        //审批完成后需要做的操作
        try
        {
            
            doAfterCheckOver(lApprovalContentID);
        }
        catch (LoanException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (RemoteException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    if (ATInfo.getCheckActionID() == LOANConstant.Actions.CANCELCHECK) //取消复核
    {
        Log.print("====cancel check====");
        cInfo.setId(lApprovalContentID);
        cInfo.setStatusId(LOANConstant.ContractStatus.SUBMIT);
        cInfo.setNextCheckUserId(lNextUserID);
        try
        {
            update(cInfo);
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
      //取消复核完成后需要做的操作
        try
        { 
            doAfterCancelCheck(lApprovalContentID);
        }
        catch (LoanException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (RemoteException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    log4j.debug("check end");
    
	return lApprovalContentID;
}

/**
 * 中油转贴现的条件查找操作
 * @param qInfo
 * @return
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
public Collection cpfFindByMultiOption(TransDiscountContractQueryInfo qInfo)
throws java.rmi.RemoteException, LoanException
{
	Vector v = new Vector();
    
    StringBuffer sbCount = new StringBuffer();
    StringBuffer sbSelect = new StringBuffer();
    StringBuffer sbSQL = new StringBuffer();
    StringBuffer sbOrder = new StringBuffer();
    String strSQL = "";
	//取得查询条件
	long clientID = qInfo.getClientId();
	long queryPurpose = qInfo.getQueryPurpose();
    long officeID = qInfo.getOfficeId();
    long currencyID = qInfo.getCurrencyId();
    long startId = qInfo.getStartTransDiscountContractId();
    long endId = qInfo.getEndTransDiscountContractId();
    String startCode = qInfo.getStartTransDiscountContractCode();
    String endCode = qInfo.getEndTransDiscountContractCode();
    double startAmount = qInfo.getStartAmount();
    double endAmount = qInfo.getEndAmount();
    long statusID = qInfo.getQueryStatusId();
    long userID = qInfo.getUserId();
    Timestamp startLoanDate = qInfo.getStartLoanDate();
    Timestamp endLoanDate = qInfo.getEndLoanDate();
    Timestamp startInputDate = qInfo.getStartInputDate();
    Timestamp endInputDate = qInfo.getEndInputDate();
      
    long pageLineCount = qInfo.getPageLineCount();
    long pageNo = qInfo.getPageNo();
    long orderParam = qInfo.getOrderParam();
    long desc = qInfo.getDesc();
    String orderParamString = qInfo.getOrderParamString();
    //存放查询结果和查询控制的变量
    double totalAmount = 0.0;
    long lRecordCount = -1;
    long lPageCount = -1;
    long lRowNumStart = -1;
    long lRowNumEnd = -1;
    
    try
    {
        try
        {
            initDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
        //  计算记录总数   
        sbCount.setLength(0);
        sbCount.append(" \n select count(*),sum(aa.mLoanAmount)  ");
        
        sbSQL.setLength(0);
        sbSQL.append(" \n from LOAN_CONTRACTFORM aa  ");
        sbSQL.append(" \n where 1=1 ");
        sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
        if (queryPurpose == 1) //for 修改
        {
            sbSQL.append(" \n and aa.nInputUserID = " + userID);
            sbSQL.append("  and   aa.nStatusID in ( " + LOANConstant.ContractStatus.SAVE +","+ LOANConstant.ContractStatus.SUBMIT +")");

        }
        if (queryPurpose == 2) //for 复核
        {	
        	String strPriv = getUserPriv( userID);
        	
        	if (strPriv.equals("h") || strPriv.equals("l"))
        	{
        		//" and CONTRACTINFO.NSTATUSID in ("+Notes.CODE_CONTRACT_STATUS_EXAMINE + "," + Notes.CODE_CONTRACT_STATUS_NOTACTIVE + ")"
        		sbSQL.append(" \n and ((aa.nStatusID in ( " + LOANConstant.ContractStatus.CHECK +","+LOANConstant.ContractStatus.NOTACTIVE+ " )))" );	
        	}
        	else
        	{
        		sbSQL.append(" \n and ((aa.nStatusID = " + LOANConstant.ContractStatus.SUBMIT +" and aa.nInputUserID <> "+userID +") or ");
        		sbSQL.append(" \n (aa.nStatusID in ( " + LOANConstant.ContractStatus.CHECK +","+LOANConstant.ContractStatus.NOTACTIVE+ " )))" );
        		//sbSQL.append("aa.nStatusID = " + LOANConstant.ContractStatus.CHECK +" and aa.nNextCheckUserID=" +userID+ "))");
        	}       
        }
        if(startId > 0 )
        {
            sbSQL.append(" \n and aa.ID >= " + startId);
        }
        if(endId > 0 )
        {
            sbSQL.append(" \n and aa.ID <= " + endId);
        }
        if (clientID != -1)
        {
            sbSQL.append(" \n and aa.nBorrowClientID = " + clientID);
        }
        if (startAmount != 0)
        {
            sbSQL.append(" \n and aa.mLoanAmount >= " + startAmount);
        }
        if (endAmount != 0)
        {
            sbSQL.append(" \n and aa.mLoanAmount <= " + endAmount);
        }
        if (currencyID > 0)
        {
            sbSQL.append(" \n and aa.nCurrencyID = " + currencyID);
        }
        if (officeID > 0)
        {
            sbSQL.append(" \n and aa.nOfficeID = " + officeID);
        }
        if (statusID > 0)
        {
            sbSQL.append(" \n and aa.nStatusID = " + statusID);
        }
        if (startInputDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startInputDate)
                    + "'");
        }
        if (endInputDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endInputDate)
                    + "'");
        }
        if (startLoanDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtStartDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startLoanDate)
                    + "'");
        }
        if (endLoanDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtEndDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endLoanDate)
                    + "'");
        }
////////////////////////////排序处理/////////////////
        sbOrder.setLength(0);
        int nIndex = 0;
        nIndex = orderParamString.indexOf(".");
        if (nIndex > 0)
        {
            if (orderParamString
                .substring(0, nIndex)
                .toLowerCase()
                .equals("loan_contractform"))
            {
                sbOrder.append(" \n order by aa." + orderParamString.substring(nIndex + 1));
            }
        }
        else
        {
            sbOrder.append(" \n order by aa.ID ");
        }
        if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
        {
            sbOrder.append(" desc ");
        }
        
        //生成没有带排序的sql语句，先查询获得符合要求的记录数
        strSQL = sbCount.toString() + sbSQL.toString();
        log4j.debug(strSQL);
        try
        {
            prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            if (rs != null && rs.next())
            {
                lRecordCount = rs.getLong(1);//结果集记录数
                totalAmount = rs.getDouble(2);//总贷款额
            }
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException("批量查询合同笔数产生错误", e);
        }
        catch (SQLException e)
        {
            throw new LoanDAOException("批量查询合同笔数产生错误", e);
        }
        log4j.info("lRecordCount:"+lRecordCount);
        log4j.info("totalAmount:"+DataFormat.formatAmount(totalAmount));
        //计算页数
        lPageCount = lRecordCount / pageLineCount;
        if ((lRecordCount % pageLineCount) != 0)
        {
            lPageCount++;
        }
        
        //返回需求的结果集
        lRowNumStart = (pageNo - 1) * pageLineCount + 1;//查询开始行
        lRowNumEnd = lRowNumStart + pageLineCount - 1;//查询结束行
        sbSelect.append(" \n select id from ( select aa.*,rownum r from ( select * ");
        sbOrder.append(" \n ) aa ) where r between " + lRowNumStart + " and " + lRowNumEnd);
        //生成查询所需记录集的sql语句
        strSQL = sbSelect.toString()+sbSQL.toString()+sbOrder.toString();
        log4j.debug(strSQL);
        try
        {
            long lID = -1;
            TransDiscountContractDAO dao = new TransDiscountContractDAO(this.strTableName);
            
            prepareStatement(strSQL);
            ResultSet rs1 = executeQuery();
            while (rs1 != null && rs1.next())
            {
                lID = rs1.getLong("id");
                TransDiscountContractInfo info = new TransDiscountContractInfo();
                if(lID > 0)
                {
                    info=(TransDiscountContractInfo) dao.findByID(lID);//,info.getClass());

                    //当前查找总数
                    info.setPageCount(lPageCount);
                    info.setPageNo(pageNo);
                    info.setRecordCount(lRecordCount);
                    info.setTotalAmount(totalAmount);
                }    
                v.add(info);
            }
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        catch (SQLException e)
        {
            throw new LoanDAOException("批量查询申请书产生错误", e);
        }
        try
        {
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new LoanDAOException(e);
        }
    } catch (Exception e)
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
    
    return (v.size() > 0 ? v : null);
	
}

/**
 *合同下的票据查询操作
*/
public Collection findBillByTransDiscountContractID(long lTransDiscountContractID) throws java.rmi.RemoteException, LoanException
{
    Vector v = new Vector();
    String strSQL = "";
    long lRecordCount = 0;
    long lBankCount = 0;
    long lBizCount = 0;
    double dTotalAmount = 0;
    double dTotalCheckAmount = 0;
    double dTotalInterest = 0;
    try
    {
        initDAO();
        try
        {
            //查找关系表
            strSQL =
                " select count(*),sum(b.mAmount),sum(b.mCheckAmount) "
                    + " from rTransDiscountContractBill r "
                    + "     ,Loan_DiscountContractBill b "
                    + " where 1 = 1 "
                    + " and r.TransDiscountContractId = "
                    + lTransDiscountContractID
                    + " and r.DiscountContractBillID = b.id "
                    + " and b.nStatusId = " + Constant.RecordStatus.VALID;
            log4j.debug(strSQL);
            prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            while (rs != null && rs.next())
            {
                lRecordCount = rs.getLong(1);
                dTotalAmount = rs.getDouble(2);
                dTotalCheckAmount = rs.getDouble(3);
                dTotalInterest = dTotalAmount - dTotalCheckAmount;
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            Log.print("lRecordCount:"+lRecordCount);
            Log.print("dTotalAmount:"+DataFormat.formatAmount(dTotalAmount));
            Log.print("dTotalCheckAmount:"+DataFormat.formatAmount(dTotalCheckAmount));
            Log.print("dTotalInterest:"+DataFormat.formatAmount(dTotalInterest));
            //detail
            strSQL =
                " select b.*,r.islocal,r.adddays,r.repurchaseterm rterm,r.repurchasedate rdate,r.checkAmount "
                    + " from RTransDiscountContractBill r "
                    + "     ,Loan_DiscountContractBill b "
                    + " where 1 = 1 "
                    + " and r.TransDiscountContractId = "
                    + lTransDiscountContractID
                    + " and r.DiscountContractBillID = b.id "
                    + " and b.nStatusId = " + Constant.RecordStatus.VALID
                    + " order by b.nContractId,b.nSerialNo ";
            log4j.debug(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            while (rs != null && rs.next())
            {
                TransDiscountApplyBillInfo info =
                    new TransDiscountApplyBillInfo();
                info.setId(rs.getLong("id"));
                info.setContractID(rs.getLong("nContractId"));
                info.setSerialNo(rs.getLong("nSerialNo"));
                info.setUserName(rs.getString("sUserName"));
                info.setBank(rs.getString("sBank"));
                //info.setIsLocal(rs.getLong("nIsLocal"));
                info.setCreate(rs.getTimestamp("dtCreate"));
                info.setEnd(rs.getTimestamp("dtEnd"));
                info.setCode(rs.getString("sCode"));
                info.setAmount(rs.getDouble("mAmount"));
                info.setStatusID(rs.getLong("nStatusID"));
                //info.setAddDays(rs.getLong("nAddDays"));
                info.setDiscountCredenceID(
                    rs.getLong("nDiscountCredenceID"));
                info.setOB_nDiscountCredenceID(
                    rs.getLong("OB_nDiscountCredenceID"));
                info.setAcceptPoTypeID(rs.getLong("nAcceptPoTypeID"));
                info.setFormerOwner(rs.getString("sFormerOwner"));
                //info.setCheckAmount(rs.getDouble("mCheckAmount"));
                info.setCheckAmount(rs.getDouble("checkAmount"));
                info.setAccrual(DataFormat.formatDouble(info.getAmount()-info.getCheckAmount()));
                
                info.setTotalBillAmount(dTotalAmount);
                info.setRecordCount(lRecordCount);
                info.setTotalRealAmount(dTotalCheckAmount);
                info.setTotalAccrual(dTotalInterest);
                
                /*
                if(info.getAcceptPoTypeID()==LOANConstant.DraftType.BANK)
                {
                    lBankCount += 1;
                }
                else if(info.getAcceptPoTypeID()==LOANConstant.DraftType.BIZ)
                {
                    lBizCount += 1;
                }
                //*/
                info.setIsLocal(rs.getLong("islocal"));
                info.setAddDays(rs.getLong("adddays"));
                info.setRepurchaseTerm(rs.getLong("rterm"));
                info.setRepurchaseDate(rs.getTimestamp("rdate"));
                
                //Log.print("===getRepurchaseTerm==="+info.getRepurchaseTerm());
                //Log.print("===getRepurchaseDate==="+info.getRepurchaseDate());
                
                v.add(info);
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
        }
        finalizeDAO();
    }
    catch (ITreasuryDAOException e)
    {
        throw new LoanDAOException(e);
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
    return v.size() > 0 ? v : null;	
}

/**
 * 中油－－－－取消复核时，应将买入的票据置为不可卖状态
 * @param lContractID
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
private void doAfterCancelCheck(long lContractID) throws java.rmi.RemoteException, LoanException
{
	TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
	TransDiscountContractBillInfo cbInfo = new TransDiscountContractBillInfo();
	StringBuffer sbSQL = new StringBuffer();
	String strContractBillTable = "";
    ResultSet rs = null;
            
	/*  TOCONFIG—TODELETE  */
	/*
	 * 产品化不再区分项目 
	 * ninh 
	 * 2005-03-24
	 */
	
//	if(Env.getProjectName().equals("cpf"))
//	{
//		strContractBillTable = "Loan_DiscountContractBill";
//	}
//	else// if(Env.getProjectName().equals("hn"))
//	{
//		strContractBillTable = "Loan_DiscountContractBill";
//	}

	strContractBillTable = "Loan_DiscountContractBill";
	
	/*  TOCONFIG—END  */
    
    TransDiscountContractBillDAO billDAO = new TransDiscountContractBillDAO(strContractBillTable);
	
    //生成查找该合同下票据的 sql 语句
    sbSQL.append("select * from RTRANSDISCOUNTCONTRACTBILL ");
    sbSQL.append("where 1=1 ");
    sbSQL.append("and TransDiscountContractId=");sbSQL.append(lContractID);
    
    
    try {
	
		cInfo = findByID(lContractID);
		//Log.print(cInfo);
		initDAO();
		//如果是买入，更新票据为可卖状态
		if (cInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
		{
			prepareStatement(sbSQL.toString());
			Log.print(sbSQL.toString());
			rs = executeQuery();
			while (rs != null && rs.next())
			{
				cbInfo.setId(rs.getLong("DiscountContractBillId"));
				cbInfo.setSellStatusID(Constant.YesOrNo.NO);
				billDAO.update(cbInfo);
			}
		}
		finalizeDAO();
	} catch (ITreasuryDAOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e) {
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

/**
 * 根据申请 id 查找对应的合同，且只查有效状态的合同
 * @param lApplyID 
 * @return
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
public TransDiscountContractInfo findContractByApplyID(long lApplyID) throws java.rmi.RemoteException, LoanException
{
	TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
	
	ResultSet rs = null;
    String strSQL = "";

    try
    {
        initDAO();
            
        strSQL = " select * from  " + this.strTableName
            + " where nLoanId=" + lApplyID
			+ "  and nStatusId >" + Constant.RecordStatus.INVALID;
            
        Log.print(strSQL);
        prepareStatement(strSQL);
        rs = executeQuery();
            
        if (rs != null && rs.next())
        {
            //贴现申请
            cInfo.setId(rs.getLong("id")); //贴现申请标识
            cInfo.setLoanId(rs.getLong("nLoanId"));
            cInfo.setApplyCode(rs.getString("sApplyCode")); //贴现申请编号
            cInfo.setContractCode(rs.getString("sContractCode")); //贴现申请编号
            cInfo.setTypeId(rs.getLong("nTypeId"));//贷款种类
            cInfo.setCurrencyId(rs.getLong("nCurrencyId"));//币种标识
            cInfo.setOfficeId(rs.getLong("nOfficeId"));//办事处标识
            //申请单位
            cInfo.setBorrowClientId(rs.getLong("nBorrowClientID")); //申请单位Id
            cInfo.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额(申请)
            cInfo.setCheckAmount(rs.getDouble("mCheckAmount")); //核定金额（申请）
            cInfo.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率（申请）
            cInfo.setDiscountDate(rs.getTimestamp("DTDISCOUNTDATE"));//转贴现日
            cInfo.setDiscountTypeId(rs.getLong("NDISCOUNTTYPEID"));//转贴现种类
            cInfo.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//回购种类
            cInfo.setInOrOut(rs.getLong("NINOROUT"));            
            cInfo.setStatusId(rs.getLong("nStatusID")); //贴现申请状态            
            cInfo.setLoanAmount(rs.getDouble("mLoanAmount")); //贴现申请金额
            cInfo.setInputUserID(rs.getLong("nInputUserID"));
            cInfo.setInputDate(rs.getTimestamp("dtInputDate"));
            cInfo.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //下一个审核人标示
            cInfo.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
            cInfo.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
            cInfo.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
            cInfo.setLoanReason(rs.getString("sLoanReason"));
            cInfo.setLoanPurpose(rs.getString("sLoanPurpose"));
            cInfo.setStartDate(rs.getTimestamp("dtStartDate"));//start日
            cInfo.setEndDate(rs.getTimestamp("dtEndDate"));//start日
            try {
            	TransDiscountContractContentDao ccDao = new TransDiscountContractContentDao();
				Collection contractContent = ccDao.getContractContentByContractId(cInfo.getId());
				cInfo.setContractContent(contractContent);
			} catch (LoanDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
            
        finalizeDAO();
            
    }
    catch (ITreasuryDAOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (SQLException e)
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
	return cInfo;
}

/**
 * 根据申请 id 查找对应的合同，且只查有效状态的合同
 * @param lApplyID 
 * @return
 * @throws java.rmi.RemoteException
 * @throws LoanException
 */
public TransDiscountContractInfo findContractByID(long lApplyID) throws java.rmi.RemoteException, LoanException
{
	TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
	
	ResultSet rs = null;
    String strSQL = "";

    try
    {
        initDAO();
            
        strSQL = " select * from  " + this.strTableName
            + " where nLoanId=" + lApplyID
			+ "  and nStatusId >" + Constant.RecordStatus.INVALID;
            
        Log.print(strSQL);
        prepareStatement(strSQL);
        rs = executeQuery();
            
        if (rs != null && rs.next())
        {
            //贴现申请
            cInfo.setId(rs.getLong("id")); //贴现申请标识                  			 
        }
            
        finalizeDAO();
            
    }
    catch (ITreasuryDAOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (SQLException e)
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
	return cInfo;
}
  //取用户级别 
  private String getUserPriv(long lUserID) throws LoanDAOException
  {
	  StringBuffer sb = new StringBuffer();
	  String strResult = "";

	  Connection con = null;
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  try
	  {
		  con = Database.getConnection();
		  // get the whole content
		  sb.append(" SELECT sPrivLevel FROM userinfo Where ID = ?");
		  ps = con.prepareStatement(sb.toString());
		  ps.setLong(1, lUserID);
		  rs = ps.executeQuery();
		  if( rs.next() )
			  strResult = rs.getString(1);
		  else
			  strResult = "";
		  if(rs!=null)
		  {
			  rs.close();
			  rs = null;
		  }
		  if(ps!=null)
		  {
			  ps.close();
			  ps = null;
		  }
		  sb.setLength(0);
	  }
	  catch(Exception e)
	  {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	  }
	  finally
	  {
	      try
	      {
	          if( rs != null )
              {
            	  rs.close();
            	  rs = null;
              }
              if( ps != null )
              {
            	  ps.close();
            	  ps = null;
              }
              if( con != null )
              {
            	  con.close();
            	  con = null;
              }
	      } catch (SQLException e1)
	      {
	          // TODO Auto-generated catch block
	          e1.printStackTrace();
	      }
	  }
	  return strResult;
  }		

  /**
   * 根据合同 id 更新合同下的MCHECKAMOUNT字段
   * Boxu add 审核之前操作
   * 2007-2-7
   * @param lTransDiscountContractID
   * @return
   * @throws java.rmi.RemoteException
   * @throws LoanException
   */
    public void updateCheckAmount(long lTransDiscountContractID,double checkAmount) throws LoanDAOException
	{
    	StringBuffer sb = new StringBuffer();
    	Connection con = null;
  	  	PreparedStatement ps = null;
  	  	ResultSet rs = null;
  	  	try
		{
  	  		con = Database.getConnection();
		    sb.append(" update loan_contractform set MCHECKAMOUNT = "+checkAmount+" Where ID = "+lTransDiscountContractID+" ");
		    ps = con.prepareStatement(sb.toString());
		    rs = ps.executeQuery();
		    
		    if(rs!=null)
			{
		       rs.close();
			   rs = null;
			}
			if(ps!=null)
			{
			   ps.close();
			   ps = null;
			}
			sb.setLength(0);
		}
  	  	catch (Exception e)
		{
  	  		e.printStackTrace();
		}
	  	finally
		{
		    try
		    {
		        if( rs != null )
	            {
	          	  rs.close();
	          	  rs = null;
	            }
	            if( ps != null )
	            {
	          	  ps.close();
	          	  ps = null;
	            }
	            if( con != null )
	            {
	          	  con.close();
	          	  con = null;
	            }
		    } catch (SQLException e1)
		    {
		        // TODO Auto-generated catch block
		        e1.printStackTrace();
		    }
		}
	}
    
    /**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long update(long loanID, long userID, long newStatusID)
	throws Exception {
       PreparedStatement ps = null;
       Connection conn = null;
       String strSQL = null;
       long lResult = -1;

      try {
	conn = Database.getConnection();
	strSQL = "update loan_ContractForm set nstatusid = ? where ID = ? ";

	ps = conn.prepareStatement(strSQL);
	ps.setLong(1, newStatusID);
	ps.setLong(2, loanID);
	
	lResult =  ps.executeUpdate();
	cleanup(ps);
	cleanup(conn);

	if (lResult < 0) {
		log.info("update loan_ContractForm property info error:" + lResult);
		return -1;
	} else {
		return loanID;
	}
} catch (Exception e) {

	cleanup(ps);
	cleanup(conn);
	throw e;
} finally {

	cleanup(ps);
	cleanup(conn);
}

}
	
	//added by qhzhou 2007-10-08
	public TransDiscountContractInfo findByApplyCode(String applyCode,long lOfficeID,long lCurrencyID)  throws java.rmi.RemoteException, LoanException{
		    TransDiscountContractInfo info = null;
		    ResultSet rs = null;
		    String strSQL = "";

		    try
		    {
		        initDAO();
		            
		        strSQL = " select * from  " + this.strTableName
		            + " where SAPPLYCODE= '" + applyCode + "'"
		            + " and nstatusid <> " + LOANConstant.ContractStatus.CANCEL
		        	+ " and nOfficeId = " + lOfficeID
		        	+ " and nCurrencyId = " + lCurrencyID;
		            System.out.println(strSQL);
		        Log.print(strSQL);
		        prepareStatement(strSQL);
		        rs = executeQuery();
		            
		        if (rs != null && rs.next())
		        {
		        	info = new TransDiscountContractInfo();
		            //贴现申请
		            info.setId(rs.getLong("ID")); //贴现申请标识
		            info.setLoanId(rs.getLong("nLoanId"));
		            info.setApplyCode(rs.getString("sApplyCode")); //贴现申请编号
		            info.setContractCode(rs.getString("sContractCode")); //贴现申请编号
		            info.setTypeId(rs.getLong("nTypeId"));//贷款种类
		            info.setNSubtypeid(rs.getLong("NSUBTYPEID"));
		            info.setCurrencyId(rs.getLong("nCurrencyId"));//币种标识
		            info.setOfficeId(rs.getLong("nOfficeId"));//办事处标识
		            //申请单位
		            info.setBorrowClientId(rs.getLong("nBorrowClientID")); //申请单位Id
		            info.setExamineAmount(rs.getDouble("mExamineAmount")); //批准金额(申请)
		            info.setCheckAmount(rs.getDouble("mCheckAmount")); //核定金额（申请）
		            info.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率（申请）
		            info.setDiscountDate(rs.getTimestamp("DTDISCOUNTDATE"));//转贴现日
		            info.setDiscountTypeId(rs.getLong("NDISCOUNTTYPEID"));//转贴现种类
		            info.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//回购种类
		            info.setInOrOut(rs.getLong("NINOROUT"));            
		            info.setStatusId(rs.getLong("nStatusID")); //贴现申请状态            
		            info.setLoanAmount(rs.getDouble("mLoanAmount")); //贴现申请金额
		            info.setInputUserID(rs.getLong("nInputUserID"));
		            info.setInputDate(rs.getTimestamp("dtInputDate"));
		            info.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //下一个审核人标示
		            info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
		            info.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
		            info.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
		            info.setLoanReason(rs.getString("sLoanReason"));
		            info.setLoanPurpose(rs.getString("sLoanPurpose"));
		            info.setStartDate(rs.getTimestamp("dtStartDate"));//start日
		            info.setEndDate(rs.getTimestamp("dtEndDate"));//start日
		            try {
		            	TransDiscountContractContentDao ccDao = new TransDiscountContractContentDao();
		            	System.out.println(info.getId());
						Collection contractContent = ccDao.getContractContentByContractId(info.getId());
						info.setContractContent(contractContent);
					} catch (LoanDAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		            
		        finalizeDAO();
		            
		    }
		    catch (ITreasuryDAOException e)
		    {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    catch (SQLException e)
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
		    return info;
	}
	
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查合同是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean cancelCredence(long applyId) throws LoanDAOException{
		boolean flag=false;
		long[] checkStatusId={
				LOANConstant.DiscountCredenceStatus.SAVE,
				LOANConstant.DiscountCredenceStatus.CHECK
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "update  loan_discountcredence s");
		sqlBuf.append(" set s.nstatusid = "+LOANConstant.DiscountCredenceStatus.CANCEL);
		sqlBuf.append(" where s.nstatusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.ncontractid = " + applyId);
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
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
			throw new LoanDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		return flag;		
	}
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查合同是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean checkStatuID(long applyId) throws LoanDAOException{
		boolean flag=false;
		long[] checkStatusId={
				//SECConstant.ContractStatus.SAVE,
				//LOANConstant.DiscountCredenceStatus.APPROVALING,
				LOANConstant.DiscountCredenceStatus.USED
				//LOANConstant.DiscountCredenceStatus.CHECK,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "select count(*) from LOAN_DISCOUNTCREDENCE s");
		sqlBuf.append(" where s.nStatusID in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.ncontractid = " + applyId);
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
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
			throw new LoanDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		return flag;		
	}
	
	//add by dwj 票据业务同步方正项目
	/**
	 * @param applyId
	 * @return
	 * @throws LoanDAOException
	 */
	public long getContracIDByApplyID(long applyId) throws LoanDAOException{
		long lID= -1;
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "select id from loan_contractform c");
		sqlBuf.append(" where c.nloanid = " + applyId);
		sqlBuf.append(" and c.nstatusid > 0  and c.nstatusid <>" + LOANConstant.ContractStatus.CANCEL);
		try
		{
			initDAO();
			prepareStatement(sqlBuf.toString());
			ResultSet rs = executeQuery();
			try
			{
				if(rs != null && rs.next())
				{
					lID=rs.getLong(1);
					
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new LoanDAOException(e);
		}finally{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		return lID;	
	}
	
	
	/**
	 * @param lContractId
	 * @return
	 * @throws IException
	 */
	public Collection generateTransDiscountCredenceByContract(TransDiscountContractInfo cInfo)throws LoanDAOException{
		Collection coll = null;
		
		 /*数据库对应属性*/
	    long id = -1;
	    Timestamp dtInputDate = Env.getSystemDate();//录入日期
	    long nStatusID = LOANConstant.DiscountCredenceStatus.SAVE;//状态
	    Timestamp dtCheckDate = Env.getSystemDate();//审核日期
	    long nNextCheckUserID = Constant.MachineUser.CheckUser;	//审核人
	    long nLastCheckUserID = Constant.MachineUser.CheckUser;	//下一级审核人
	    long nTypeID = LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE;//凭证种类，转贴现凭证
	    long nNextCheckLevel = -1;			//下一级审批级别
	    Timestamp dtFillDate = Env.getSystemDate();;		//填写日期
	    
	    double mInterest = 0.0;	//（转）贴现凭证利息
	    double mRate = 0.00;  //（转）贴现凭证利率
		try{
			initDAO();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" select  b.id           ID," + " \n ");//合同票据ID
			sqlBuffer.append("    c.id            	  nContractID," + " \n ");//贷款合同ID
			sqlBuffer.append("    a.ndiscounttypeid   nDiscountTypeId," + " \n ");//转贴现类型：买断\回购
			sqlBuffer.append("    decode(sign(nvl(a.nbankacceptpo,0)),1,1,2)    nDraftTypeID," + " \n ");//贴现汇票种类ID
			sqlBuffer.append("    b.scode             sDraftCode," + " \n ");//贴现汇票号码
			sqlBuffer.append("    b.dtcreate          dtPublicDate," + " \n ");//发票日
		    sqlBuffer.append("    b.dtend             dtAtTerm," + " \n ");//到期日
		    sqlBuffer.append("    b.nadddays          nAddDays," + " \n ");//节假日增加计息天数
		    //sqlBuffer.append("    b.nislocal          nIsLocal," + " \n ");//是否本地
		    sqlBuffer.append("    r.islocal          nIsLocal," + " \n ");//是否本地
		    sqlBuffer.append("    c.nborrowclientid   lApplyClientId," + " \n ");//借款单位
		    sqlBuffer.append("    e.name              sApplyClient," + " \n ");//借款单位
		    sqlBuffer.append("    d.bankaccountcode   sApplyAccount," + " \n ");//申请单位帐户号
		    sqlBuffer.append("    d.bankname          sApplyBank," + " \n ");//申请单位开户银行
		    sqlBuffer.append("    b.sbank             sAcceptClient," + " \n ");//承兑单位名称
		    sqlBuffer.append("    ''                  sAcceptAccount," + " \n ");//承兑单位帐户号
		    sqlBuffer.append("    ''                  sAcceptBank," + " \n ");//承兑单位银行
		    sqlBuffer.append("    b.mamount           mAmount," + " \n ");//贴现凭证金额
		    sqlBuffer.append("    a.mdiscountrate     mRate," + " \n ");//利率
		    sqlBuffer.append("    b.nbillsourcetypeid nBillSourceTypeID," + " \n ");//票据来源
		    sqlBuffer.append("    a.nofficeid         nOfficeID," + " \n ");//办事处
		    sqlBuffer.append("    ''                  PURCHASERINTEREST," + " \n ");//买方支付凭证利息
		    sqlBuffer.append("    a.ncurrencyid       nCurrencyID," + " \n ");//币种
		    sqlBuffer.append("    a.dtdiscountdate    dtDiscountDate," + " \n ");//转贴现日
		    sqlBuffer.append("    a.mdiscountrate     dDiscountRate," + " \n ");//转贴利率
		    sqlBuffer.append("    a.dtrepurchasedate  dtRepurchaseDate," + " \n ");//回购日期
		    sqlBuffer.append("    a.nrepurchaseterm   nRepurchaseTerm," + " \n ");//回购期限
		    sqlBuffer.append("    a.ninteresttypeid   ninteresttypeid," + " \n ");//计息方式
		    sqlBuffer.append("    a.ninputuserid      nInputUserID," + " \n ");//录入人ID
		    sqlBuffer.append("    h.adddays  outadddays" + " \n ");//卖出回购时的节假日天数
		    sqlBuffer.append(" from cra_loanform a," + " \n ");
		    sqlBuffer.append("    loan_discountcontractbill b," + " \n ");
		    sqlBuffer.append("    loan_contractform c," + " \n ");
		    sqlBuffer.append("    sec_counterpartbankaccount d," + " \n ");
		    sqlBuffer.append("    sec_counterpart e," + " \n ");
		    sqlBuffer.append("    rtransdiscountcontractbill r," + " \n ");
		    sqlBuffer.append("    RTransDiscountApplyBill h " + " \n ");
		    sqlBuffer.append(" where r.transdiscountcontractid = c.id" + " \n ");
		    sqlBuffer.append(" 		and r.discountcontractbillid = b.id" + " \n ");
		    sqlBuffer.append(" 		and c.nloanid = a.id" + " \n ");
		    sqlBuffer.append(" 		and h.discountcontractbillid(+)=b.id" + " \n ");
		    sqlBuffer.append(" 		and c.nborrowclientid = d.counterpartid(+)" + " \n ");
		    sqlBuffer.append(" 		and c.ncounterpartacctid = d.id" + " \n ");
		    sqlBuffer.append(" 		and d.counterpartid = e.id" + " \n ");
		    sqlBuffer.append(" 		and c.ntypeid = " + LOANConstant.LoanType.ZTX + " \n ");
		    sqlBuffer.append(" 		and b.nstatusid = " + LOANConstant.LoanStatus.SAVE + " \n ");
		    sqlBuffer.append(" 		and d.statusid = " + SECConstant.SettingStatus.CHECKED + " \n ");
		    sqlBuffer.append(" 		and c.id = " + cInfo.getId());
		    
		    prepareStatement(sqlBuffer.toString());
	        executeQuery();
	        
	        if(transRS != null){
	        	coll = new ArrayList();
	        	TransDiscountCredenceInfo  tdcInfo = null;
	        	
	        	String strInterestEnd = null;//
	        	Timestamp tsInterestEnd = null;//算息结束日
	        	Timestamp tsDiscountDate = null;//转贴现日
	        	Timestamp tsRepurchaseDate = null;//回购日期 
	        	long nInterestType = -1;//转贴现计息方式，1： 先舍入后累加 2： 先累加后舍入
	        	long nDiscountTypeId = -1;//转贴现类型：买断\回购
	        	int nDays = 0; //实际转贴现天数
	        	int nAddDays = 0;//节假日增加计息天数
	        	long nIsLocal = 0;//承兑方是否本地
	        	double dRate = 0;//转贴现利率
	            double dAmount = 0; //票据金额
	            double dAccrual = 0; //转贴现利息
	            double dRealAmount = 0; //实付贴现金额
	            double dTotalAmount = 0; //总票据金额
	            double dTotalAccrual = 0; //总票据利息
	            double dTotalRealAmount = 0; //总票据实付金额
	        	
	        	//买入买断式：一张票生成一张凭证
	        	if( cInfo.getInOrOut()==LOANConstant.TransDiscountInOrOut.IN && cInfo.getDiscountTypeId()==LOANConstant.TransDiscountType.BUYBREAK ){
		        	while(transRS.next()){
		        			                
		        		tdcInfo = new TransDiscountCredenceInfo();
		        		tdcInfo.setContractID(cInfo.getId());
		        		tdcInfo.setDraftTypeID(transRS.getLong("nDraftTypeID"));//贴现汇票种类ID
		        		tdcInfo.setDraftCode(transRS.getString("sDraftCode"));//贴现汇票号码
		        		tdcInfo.setPublicDate(transRS.getTimestamp("dtPublicDate"));//发票日
		        		tdcInfo.setAtTerm(transRS.getTimestamp("dtAtTerm"));//到期日
		        		
		        		tdcInfo.setApplyClientId(transRS.getLong("lApplyClientId"));//借款单位
		        		tdcInfo.setApplyClient(transRS.getString("sApplyClient"));//借款单位名称
		        		tdcInfo.setApplyAccount(transRS.getString("sApplyAccount"));//申请单位帐户号
		        		tdcInfo.setApplyBank(transRS.getString("sApplyBank"));//申请单位开户银行
		        	    tdcInfo.setAcceptClient(transRS.getString("sAcceptClient"));//承兑单位名称
		        	    tdcInfo.setAcceptAccount(transRS.getString("sAcceptAccount"));//承兑单位帐户号
		        	    tdcInfo.setAcceptBank(transRS.getString("sAcceptBank"));//承兑单位银行
		        	    tdcInfo.setAmount(transRS.getDouble("mAmount"));//贴现凭证金额
		        	    tdcInfo.setRate(transRS.getDouble("mRate"));//利率
		        	    tdcInfo.setBillSourceTypeID(transRS.getLong("nBillSourceTypeID"));//票据来源
		        	    tdcInfo.setOfficeID(transRS.getLong("nOfficeID"));//办事处
		        	    tdcInfo.setNCurrencyID(transRS.getLong("nCurrencyID"));//币种
		        	    tdcInfo.setDiscountDate(transRS.getTimestamp("dtDiscountDate"));//转贴现日
		        	    tdcInfo.setDiscountRate(transRS.getDouble("dDiscountRate"));//转贴利率
		        	    tdcInfo.setRepurchaseDate(transRS.getTimestamp("dtRepurchaseDate"));//回购日期
		        	    tdcInfo.setRepurchaseTerm(transRS.getLong("nRepurchaseTerm"));//回购期限
		        	    tdcInfo.setInputUserID(transRS.getLong("nInputUserID"));//录入人ID
		        	    
		        	    tdcInfo.setInputDate(dtInputDate);//录入日期
		        	    tdcInfo.setStatusID(nStatusID);//状态
		        	    tdcInfo.setCheckDate(dtCheckDate);//审核日期
		        	   
		        	    tdcInfo.setNextCheckUserID(nNextCheckUserID);//审核人
		        	    tdcInfo.setNextCheckUserID(nLastCheckUserID);//下一级审核人
		        	    tdcInfo.setTypeID(nTypeID);//凭证种类，转贴现凭证
		        	    tdcInfo.setNextCheckLevel(nNextCheckLevel);//下一级审批级别
		        	    tdcInfo.setFillDate(dtFillDate);//填写日期
		        	    
		        	    //计算凭证利息----------开始----------------------------------------------
		        	    dAmount = transRS.getDouble("mAmount");
		        	    nInterestType = transRS.getLong("ninteresttypeid");//计息方式
		        	    tsInterestEnd = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtAtTerm")));//格式化算息结束日,去掉时、分、秒信息
		            	tsDiscountDate = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtDiscountDate")));//格式化转贴现日,去掉时、分、秒信息
		            	
		            	dRate = transRS.getDouble("dDiscountRate");
		            	
		            	nAddDays = (int)transRS.getLong("nAddDays");
		            	
		            	nIsLocal = transRS.getLong("nIsLocal");
		            	
		            	nDays = DataFormat.getIntervalDays(tsDiscountDate, tsInterestEnd) - 1;//算头不算尾
		            	
		        	    nDays = nDays + nAddDays;//加节假日增加计息天数
		        	    
		        	    if (nIsLocal == Constant.YesOrNo.NO){
	                        nDays = nDays + 3;//加 承兑方非本地三天
		        	    }
		        	    dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
	                    
	                    dRealAmount = dAmount - dAccrual;
	                    
	                    //计算凭证利息----------结束--------------------------------------------------------
	                    tdcInfo.setInterest(dAccrual);//转贴现利息
	                    tdcInfo.setCheckAmount(dRealAmount);//实付转贴现金额
	                    
	                    //设置凭证票据关系
	                    long[] allBillIds = new long[1];
						allBillIds[0] = transRS.getLong("ID");
						tdcInfo.setAllBillID(allBillIds);
						
		        		coll.add(tdcInfo);
		        	}
	        	}else{//一个合同生成一个凭证
	        		tdcInfo = new TransDiscountCredenceInfo();
	        		tdcInfo.setContractID(cInfo.getId());
	        		ArrayList billIdList = new ArrayList();
	        		if(transRS.next()){//第一张票
	        			tdcInfo.setDraftTypeID(transRS.getLong("nDraftTypeID"));//贴现汇票种类ID
		        		tdcInfo.setDraftCode(transRS.getString("sDraftCode"));//贴现汇票号码
		        		tdcInfo.setPublicDate(transRS.getTimestamp("dtPublicDate"));//发票日
		        		tdcInfo.setAtTerm(transRS.getTimestamp("dtAtTerm"));//到期日
		        		
		        		tdcInfo.setApplyClientId(transRS.getLong("lApplyClientId"));//借款单位
		        		tdcInfo.setApplyClient(transRS.getString("sApplyClient"));//借款单位名称
		        		tdcInfo.setApplyAccount(transRS.getString("sApplyAccount"));//申请单位帐户号
		        		tdcInfo.setApplyBank(transRS.getString("sApplyBank"));//申请单位开户银行
		        	    tdcInfo.setAcceptClient(transRS.getString("sAcceptClient"));//承兑单位名称
		        	    tdcInfo.setAcceptAccount(transRS.getString("sAcceptAccount"));//承兑单位帐户号
		        	    tdcInfo.setAcceptBank(transRS.getString("sAcceptBank"));//承兑单位银行
		        	    
		        	    tdcInfo.setRate(transRS.getDouble("mRate"));//利率
		        	    tdcInfo.setBillSourceTypeID(transRS.getLong("nBillSourceTypeID"));//票据来源
		        	    tdcInfo.setOfficeID(transRS.getLong("nOfficeID"));//办事处
		        	    tdcInfo.setNCurrencyID(transRS.getLong("nCurrencyID"));//币种
		        	    tdcInfo.setDiscountDate(transRS.getTimestamp("dtDiscountDate"));//转贴现日
		        	    tdcInfo.setDiscountRate(transRS.getDouble("dDiscountRate"));//转贴利率
		        	    tdcInfo.setRepurchaseDate(transRS.getTimestamp("dtRepurchaseDate"));//回购日期
		        	    tdcInfo.setRepurchaseTerm(transRS.getLong("nRepurchaseTerm"));//回购期限
		        	    tdcInfo.setInputUserID(transRS.getLong("nInputUserID"));//录入人ID
		        	    
		        	    tdcInfo.setInputDate(dtInputDate);//录入日期
		        	    tdcInfo.setStatusID(nStatusID);//状态
		        	    tdcInfo.setCheckDate(dtCheckDate);//审核日期
		        	   
		        	    tdcInfo.setNextCheckUserID(nNextCheckUserID);//审核人
		        	    tdcInfo.setNextCheckUserID(nLastCheckUserID);//下一级审核人
		        	    tdcInfo.setTypeID(nTypeID);//凭证种类，转贴现凭证
		        	    tdcInfo.setNextCheckLevel(nNextCheckLevel);//下一级审批级别
		        	    tdcInfo.setFillDate(dtFillDate);//填写日期
		        	    
		        	    //计算凭证利息----------开始----------------------------------------------
		        	    dAmount = transRS.getDouble("mAmount");
		        	    nInterestType = transRS.getLong("ninteresttypeid");//计息方式
		        	    
		        	    nDiscountTypeId = transRS.getLong("nDiscountTypeId");//转贴现类型：买断\回购
		        	    
		        	    tsInterestEnd = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtAtTerm")));//格式化算息结束日,去掉时、分、秒信息
		        	    
		        	    if(nDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型,以购回日期为准
	                    {
		        	    	tsInterestEnd = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtRepurchaseDate")));//格式化算息结束日,去掉时、分、秒信息
	                    }
		        	    
		            	tsDiscountDate = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtDiscountDate")));//格式化转贴现日,去掉时、分、秒信息
		            	
		            	dRate = transRS.getDouble("dDiscountRate");
		            	
		            	 if(cInfo.getInOrOut()==LOANConstant.TransDiscountInOrOut.OUT &&nDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
		                    {
		            		    nAddDays = (int)transRS.getLong("outadddays");
		                    }else{
		                    	nAddDays = (int)transRS.getLong("nAddDays");
		                    }
		            	
		            	
		            	nIsLocal = transRS.getLong("nIsLocal");
		            	
		            	nDays = DataFormat.getIntervalDays(tsDiscountDate, tsInterestEnd) - 1;//算头不算尾
		            	 if(nDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
		                    {
		        	    nDays = nDays + nAddDays;//加节假日增加计息天数
		                    }
		        	    if(nDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
	                    {
		        	    	if (nIsLocal == Constant.YesOrNo.NO){
		                        nDays = nDays + 3;//加 承兑方非本地三天
			        	    }
	                    }
		        	    
		        	    if(nDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
	                    {
		        	    	nDays = nDays + nAddDays;//加节假日增加计息天数
	                    }
		        	    
		        	    dAmount = transRS.getDouble("mAmount");//票面金额
		        	    
		        	    dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
	                    
	                    dRealAmount = dAmount - dAccrual;
	                    
	                    if(nInterestType == LOANConstant.CountInterestType.CalAfterRounding)//先舍后累加计算
	                    {
	                        dAccrual = DataFormat.formatDouble(dAccrual, 2);
	                        dRealAmount = DataFormat.formatDouble(dRealAmount, 2);
	                    }
	                    dTotalAmount = dTotalAmount + dAmount;
	                    dTotalAccrual = dTotalAccrual + dAccrual;
	                    dTotalRealAmount = dTotalRealAmount + dRealAmount;
	                    
	                    billIdList.add(String.valueOf(transRS.getLong("ID")));
	        		}
	        		while(transRS.next()){
	        			//进入说明多张票据情况,第二张票开始
	        			
	        			tdcInfo.setDraftCode(null);//贴现汇票号码,详见票据明细表
		        		tdcInfo.setPublicDate(null);//发票日,详见票据明细表
		        		tdcInfo.setAtTerm(null);//到期日,详见票据明细表
		        		tdcInfo.setAcceptClient(null);//承兑单位名称,详见票据明细表
		        	    tdcInfo.setAcceptAccount(null);//承兑单位帐户号,详见票据明细表
		        	    tdcInfo.setAcceptBank(null);//承兑单位银行,详见票据明细表
		        	    tdcInfo.setBillSourceTypeID(-1);//票据来源,详见票据明细表
		        	    
	        			//计算凭证利息----------开始----------------------------------------------
		        	    dAmount = transRS.getDouble("mAmount");
		        	    nInterestType = transRS.getLong("ninteresttypeid");//计息方式
		        	    
		        	    nDiscountTypeId = transRS.getLong("nDiscountTypeId");//转贴现类型：买断\回购
		        	    
		        	    tsInterestEnd = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtAtTerm")));//格式化算息结束日,去掉时、分、秒信息
		        	    
		        	    if(nDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型,以购回日期为准
	                    {
		        	    	tsInterestEnd = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtRepurchaseDate")));//格式化算息结束日,去掉时、分、秒信息
	                    }
		        	    
		            	tsDiscountDate = DataFormat.getDateTime(DataFormat.formatDate(transRS.getTimestamp("dtDiscountDate")));//格式化转贴现日,去掉时、分、秒信息
		            	
		            	dRate = transRS.getDouble("dDiscountRate");
		            	
		            	 if(cInfo.getInOrOut()==LOANConstant.TransDiscountInOrOut.OUT &&nDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
		                    {
		            		    nAddDays = (int)transRS.getLong("outadddays");
		                    }else{
		                    	nAddDays = (int)transRS.getLong("nAddDays");
		                    }
		            	
		            	nIsLocal = transRS.getLong("nIsLocal");
		            	
		            	nDays = DataFormat.getIntervalDays(tsDiscountDate, tsInterestEnd) - 1;//算头不算尾
		            	
		            	 if(nDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
		                    {
		        	    nDays = nDays + nAddDays;//加节假日增加计息天数
		                    }
		        	    
		        	    if(nDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
	                    {
		        	    	if (nIsLocal == Constant.YesOrNo.NO){
		                        nDays = nDays + 3;//加 承兑方非本地三天
			        	    }
	                    }
		        	    
		        	    if(nDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
	                    {
		        	    	nDays = nDays + nAddDays;//加节假日增加计息天数
	                    }
		        	    
		        	    dAmount = transRS.getDouble("mAmount");//票面金额
		        	    
		        	    dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
	                    
	                    dRealAmount = dAmount - dAccrual;
	                    
	                    if(nInterestType == LOANConstant.CountInterestType.CalAfterRounding)//先舍后累加计算
	                    { 
	                        dAccrual = DataFormat.formatDouble(dAccrual, 2);
	                        dRealAmount = DataFormat.formatDouble(dRealAmount, 2);
	                    }
	                    dTotalAmount = dTotalAmount + dAmount;
	                    dTotalAccrual = dTotalAccrual + dAccrual;
	                    dTotalRealAmount = dTotalRealAmount + dRealAmount;
	                    
	                    billIdList.add(String.valueOf(transRS.getLong("ID")));
		        	}
	        		//保留2位精度
	        		dTotalAmount = DataFormat.formatDouble(dTotalAmount, 2);//总金额
	        		dTotalAccrual = DataFormat.formatDouble(dTotalAccrual, 2);//总利息
	                dTotalRealAmount = DataFormat.formatDouble(dTotalRealAmount, 2);//总核定金额
	                
	                tdcInfo.setAmount(dTotalAmount);//凭证总金额
	                tdcInfo.setInterest(dTotalAccrual);//凭证总转贴现利息
	                tdcInfo.setCheckAmount(dTotalRealAmount);//凭证总实付转贴现金额
	                
	                //设置凭证票据关系
	                long[] allBillIds = new long[billIdList.size()];
	                
	                for(int i = 0; i < billIdList.size(); i++){
	                	allBillIds[i] = Long.valueOf((String)billIdList.get(i)).longValue();
	                }
					
					tdcInfo.setAllBillID(allBillIds);
					
	        		coll.add(tdcInfo);
	        	}
	        }
			
		}catch(Exception e){
			e.printStackTrace();
			throw new LoanDAOException("生成转贴现凭证信息错误，请检查！",e);
		}finally{
			try{
			finalizeDAO();
			}catch(Exception e){
				throw new LoanDAOException("Gen_E001",e);
			}
		}
		return coll;
	}	

  
}
