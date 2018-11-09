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

package com.iss.itreasury.loan.transdiscountapply.dao;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.bill.bizdelegation.DraftDelegation;
import com.iss.itreasury.bill.draft.dataentity.TransDraftOutInfo;
import com.iss.itreasury.bill.draft.dataentity.assemble.DraftStorageAssembleInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.bill.util.UtilOperation;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.dataentity.SelectBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractBillDAO;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountApplyDAO extends LoanDAO
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
    
    
    
    
    
    public TransDiscountApplyDAO(String strTable)
    {
        //super("Loan_LoanForm");
        super(strTable,true);
    }
    public static void main(String[] args)
        throws java.rmi.RemoteException, LoanException
    {
        TransDiscountApplyDAO dao = new TransDiscountApplyDAO("Cra_LoanForm");
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        TransDiscountApplyBillInfo binfo = new TransDiscountApplyBillInfo();
        TransDiscountApplyQueryInfo qinfo = new TransDiscountApplyQueryInfo();
        ApprovalTracingInfo atinfo = new ApprovalTracingInfo();
        SelectBillInfo sInfo = new SelectBillInfo();
        Collection c = null;
       
        /*
        try
        {
            
            atinfo.setModuleID(2);
            atinfo.setLoanTypeID(11);
            atinfo.setActionID(1);
            atinfo.setApprovalContentID(411);
            atinfo.setApprovalID(162);
            atinfo.setNextUserID(76);
            atinfo.setUserID(91);
            atinfo.setInputUserID(91);
            atinfo.setCheckActionID(LOANConstant.Actions.CHECKOVER);
            
            dao.check(atinfo);
            //dao.cpfCheckApply(atinfo);
           
            
        }
        catch (LoanDAOException e)
        {
            
            e.printStackTrace();
        }
        //*/
        
        
        /*        
        try
        {
            long[] list = {1379};
            long[] list1 = {1};
            Timestamp[] list2 = {null};
            sInfo.setTransDiscountApplyID(306);
            sInfo.setAllBillID(list);
            sInfo.setAddDays(list1);
            sInfo.setRepurchaseDate(list2);
            sInfo.setRepurchaseTerm(list1);
            sInfo.setIsLocal(list1);
            dao.saveTransDiscountApplyBill(sInfo);
        }
        catch (LoanException e)
        {
            
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            
            e.printStackTrace();
        }
        
        //*/
        //*
//        try
//        {
//            c = dao.findOutBillByTransDiscountApplyID(322);//, info.getClass());
//            
//
//            if( (c != null) && (c.size() > 0) )
//            {
//                Iterator it = c.iterator();
//                while (it.hasNext() )
//                {
//                    TransDiscountApplyBillInfo bbinfo = ( TransDiscountApplyBillInfo ) it.next();
//                    Log.print("c===getRepurchaseTerm==="+bbinfo.getRepurchaseTerm());
//                    Log.print("c===getRepurchaseDate==="+bbinfo.getRepurchaseDate());
//           
//                }
//            }
//            if (c != null)
//            { }
//            else
//            {
//                System.out.println("info=null");
//            }
//            
//        }
//        catch (LoanDAOException e)
//        {
//            
//            e.printStackTrace();
//        }
        /*
        try
        {
            info = (TransDiscountApplyInfo) dao.findByID(411);//, info.getClass());
            if (info != null)
            {
                System.out.println("id=" + info.getId());
            }
            else
            {
                System.out.println("info=null");
            }
            Log.print("info:"+info);
            TransDiscountContractInfo cinfo = new TransDiscountContractInfo();

            //Timestamp Date = Env.getSystemDateTime();

            
            cinfo.setLoanId(info.getId());
            cinfo.setEndDate(info.getEndDate());
            cinfo.setStartDate(info.getStartDate());
            cinfo.setInOrOut(info.getInOrOut());
            cinfo.setDiscountTypeId(info.getDiscountTypeId());
            
            dao.getIntervalNum(cinfo);
            
            
        }
        catch (LoanDAOException e)
        {
            
            e.printStackTrace();
        }*/
        /*
        catch (ITreasuryDAOException e)
        {
            
            e.printStackTrace();
        }
        //*/
        /////////////////////////////////////////////////
        /*
        try
        {
            qinfo.setQueryPurpose(1);
            qinfo.setCurrencyId(1);
            qinfo.setOfficeId(1);
            qinfo.setUserId(76);
            //qinfo.setStartTransDiscountApplyId(50);
            //qinfo.setEndTransDiscountApplyId(500);
            qinfo.setStartAmount(0);
            qinfo.setEndAmount(200000000);
            //qinfo.setClientId(1);
            //* /
            qinfo.setPageNo(1);
            qinfo.setPageLineCount(1000);
            qinfo.setDiscountTypeId(LOANConstant.TransDiscountType.BUYBREAK);
            qinfo.setInOrOut(LOANConstant.TransDiscountInOrOut.OUT);
            qinfo.setTransDiscountApplyId(322);
            qinfo.setTransDiscountRate(6.0);
            Timestamp Date = Env.getSystemDateTime();
            qinfo.setCountDiscountDate(Date);
             
			//atinfo.setCheckActionID(LOANConstant.Actions.RETURN);
			//dao.cpfCheckApply(atinfo);
          	 //dao.cpfFindByMultiOption(qinfo);
            //dao.calInBillInterest(qinfo);
            
            c=dao.findByMultiOption(qinfo);
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
            
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            
            e.printStackTrace();
        }
        //*/
        ///////////////////////////////////////////
        //*
       /*try
        {
             	qinfo.setStartDiscountContractId(50);
                //qinfo.setEndDiscountContractId(500);
                //qinfo.setStartTransDiscountContractId(10);
                //qinfo.setEndTransDiscountContractId(600);
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
            
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            
            e.printStackTrace();
        }*/
        //*/
        /*
        ///////////////////////////////////////////
        try
        {
            //info.setId(info.);
            info.setCurrencyId(1);
            info.setOfficeId(1);
            info.setApplyCode(dao.createApplyCode(1,1,1));
            info.setBankAcceptPO(1);
            info.setBizAcceptPO(0);
            info.setLoanAmount(2000.00);
            //info.setStartDate(DataFormat.parseDate("2004-08-16",1));
            info.setLoanReason("1111");
            info.setLoanPurpose("123");
            info.setTypeId(LOANConstant.LoanType.ZTX);
            info.setBorrowClientId(1);
            info.setStatusId(LOANConstant.LoanStatus.SAVE);
            Log.print("applyCode:"+info.getApplyCode());
            //info.setId(138);
            //* /
            /*
            info.setId(135);
            info.setStatusId(LOANConstant.LoanStatus.CANCEL);
            dao.update(info);
            //* /
            //*
            long id = -1;
            if(info.getId() < 0)
            {
                //dao.setUseMaxID();
                id = dao.add(info);
                Log.print("id="+id);
            }
            else
            {
                dao.update(info);
            }//* /
        }
        catch (ITreasuryDAOException e)
        {
            
            e.printStackTrace();
        }
        //*/
        ///////////////////////////////////////////
        TransDiscountApplyDAO bb=new TransDiscountApplyDAO("Cra_loanform");
        TransDiscountApplyQueryInfo qInfo=new TransDiscountApplyQueryInfo();
        bb.findByMultiOption1(qInfo);
    }
    
    /**
     *申请书的单笔查询操作
    */
    public TransDiscountApplyInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
    {
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        ResultSet rs = null;
        String strSQL = "";

        try
        {
            initDAO();
            
            strSQL = " select * from Cra_loanForm " 
                + " where id=" + lID;
            System.out.println(strSQL);
            Log.print(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            
            if (rs != null && rs.next())
            {
                //贴现申请
                info.setId(lID); //贴现申请标识
                info.setApplyCode(rs.getString("sApplyCode")); //贴现申请编号
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
                info.setInputUserId(rs.getLong("nInputUserID"));
                info.setInputDate(rs.getTimestamp("dtInputDate"));
                info.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //下一个审核人标示
                info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
                info.setIsLowLevel(rs.getLong("isLowLevel"));
                info.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
                info.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
                info.setLoanReason(rs.getString("sLoanReason"));
                info.setLoanPurpose(rs.getString("sLoanPurpose"));
                info.setStartDate(rs.getTimestamp("dtStartDate"));//start日
                info.setEndDate(rs.getTimestamp("dtEndDate"));//start日
                info.setOfficeId(rs.getLong("nOfficeId"));
                info.setCurrencyId(rs.getLong("nCurrencyId"));
                info.setTypeId(rs.getLong("nTypeId"));
                info.setInterestTypeId(rs.getLong("nInterestTypeId"));
                info.setNSubtypeid(rs.getLong("NSUBTYPEID"));
                info.setIsLocal(rs.getLong("NISLOCAL"));
                info.setRepurchaseDate(rs.getTimestamp("DTREPURCHASEDATE"));
                info.setRepurchaseTerm(rs.getLong("NREPURCHASETERM"));
                info.setIsChoose(rs.getLong("NISCHOOSE"));
                info.setAddDays(rs.getLong("NADDDAYS"));
                info.setBillStatus(rs.getLong("NBILLSTATUS"));
                info.setTransActionTypeId(rs.getLong("NTRANSACTIONTYPEID"));
                info.setCreditId(rs.getLong("CREDITID"));
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            
            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e)
        {
            
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            
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
                
                e1.printStackTrace();
            }
        }
        return info;
    }
    /**
     *申请书的单笔查询操作
    */
    public TransDiscountApplyInfo findByCreditID(long lID) throws java.rmi.RemoteException, LoanException
    {
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        ResultSet rs = null;
        String strSQL = "";

        try
        {
            initDAO();
            
            strSQL = " select * from Cra_loanForm " 
                + " where creditID=" + lID+"and nstatusid!= "+LOANConstant.LoanStatus.CANCEL;
            System.out.println(strSQL);
            Log.print(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            
            if (rs != null && rs.next())
            {
                //贴现申请
                info.setId(lID); //贴现申请标识
                info.setApplyCode(rs.getString("sApplyCode")); //贴现申请编号
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
                info.setInputUserId(rs.getLong("nInputUserID"));
                info.setInputDate(rs.getTimestamp("dtInputDate"));
                info.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //下一个审核人标示
                info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
                info.setIsLowLevel(rs.getLong("isLowLevel"));
                info.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
                info.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
                info.setLoanReason(rs.getString("sLoanReason"));
                info.setLoanPurpose(rs.getString("sLoanPurpose"));
                info.setStartDate(rs.getTimestamp("dtStartDate"));//start日
                info.setEndDate(rs.getTimestamp("dtEndDate"));//start日
                info.setOfficeId(rs.getLong("nOfficeId"));
                info.setCurrencyId(rs.getLong("nCurrencyId"));
                info.setTypeId(rs.getLong("nTypeId"));
                info.setInterestTypeId(rs.getLong("nInterestTypeId"));
                info.setNSubtypeid(rs.getLong("NSUBTYPEID"));
                info.setIsLocal(rs.getLong("NISLOCAL"));
                info.setRepurchaseDate(rs.getTimestamp("DTREPURCHASEDATE"));
                info.setRepurchaseTerm(rs.getLong("NREPURCHASETERM"));
                info.setIsChoose(rs.getLong("NISCHOOSE"));
                info.setAddDays(rs.getLong("NADDDAYS"));
                info.setBillStatus(rs.getLong("NBILLSTATUS"));
                info.setTransActionTypeId(rs.getLong("NTRANSACTIONTYPEID"));
                info.setCreditId(rs.getLong("CREDITID"));
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            
            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e)
        {
            
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            
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
                
                e1.printStackTrace();
            }
        }
        return info;
    }
    /**
     *  
     *  //: new - 加入金额审批效验 //:~
    */
    
    public Collection findByMultiOption1(TransDiscountApplyQueryInfo qInfo)throws java.rmi.RemoteException, LoanException{
        Vector v = new Vector();
    
        StringBuffer strCount = new StringBuffer();
        StringBuffer strSelect = new StringBuffer();
        StringBuffer strOrder = new StringBuffer();
        String tempSQL="";
        String strSQL = "";
        String strSQL2="";
        String UNION=" union ";
        
        long queryPurpose = qInfo.getQueryPurpose();
        long officeID = qInfo.getOfficeId();
        long currencyID = qInfo.getCurrencyId();

        long startTransDiscountApplyId =
            qInfo.getStartTransDiscountApplyId();
        long endTransDiscountApplyId = 
            qInfo.getEndTransDiscountApplyId();
            
        long clientID = qInfo.getClientId();
        
        double startAmount = qInfo.getStartAmount();
        double endAmount = qInfo.getEndAmount();
        
        Timestamp startDate = qInfo.getQueryStartDate();
        Timestamp endDate = qInfo.getQueryEndDate();
        
        long statusID = qInfo.getQueryStatusId();
        
        long userID = qInfo.getUserId();
        
//        ApprovalDelegation appBiz = new ApprovalDelegation();
        String strUser = qInfo.getUser();//审核的虚拟用户组

        long pageLineCount = qInfo.getPageLineCount();
        long pageNo = qInfo.getPageNo();
        long orderParam = qInfo.getOrderParam();
        long desc = qInfo.getDesc();
        String orderParamString = qInfo.getOrderParamString();

        double totalAmount = 0.0;
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        
        long lActionID=Constant.ApprovalAction.LOAN_APPLY;
        long lSubLoanTypeID=23;
        
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
            strSQL+=" where aa.id =d.NAPPROVALCONTENTID and aa.nstatusid = " + LOANConstant.LoanStatus.APPROVALING;
            strSQL+=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+"";
            
//            if(queryPurpose==2){
//            	strSQL2+=" \n and aa.nstatusid = "+LOANConstant.LoanStatus.SUBMIT;
//            }
            
            if(startTransDiscountApplyId > 0 ){
            	strSQL+=" \n and aa.ID >= " + startTransDiscountApplyId;
            }
            if(endTransDiscountApplyId > 0 ){
            	strSQL+=" \n and aa.ID <= " + endTransDiscountApplyId;
            }
            if (clientID != -1){
            	strSQL+=" \n and aa.nBorrowClientID = " + clientID;
            }
            if (startAmount != 0){
            	strSQL+=" \n and aa.mLoanAmount >= " + startAmount;
            }
            if (endAmount != 0){
            	strSQL+=" \n and aa.mLoanAmount <= " + endAmount;
            }
            if (currencyID > 0){
            	strSQL+=" \n and aa.nCurrencyID = " + currencyID;
            }
            if (officeID > 0){
            	strSQL+=" \n and aa.nOfficeID = " + officeID;
            }
            if (startDate != null){
            	strSQL+=
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'";
            }
            if (endDate != null)
            {
            	strSQL+=
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                        + DataFormat.getDateString(endDate)
                        + "'";
            }
            
            //strSQL+=") union ( ";
             
            strSQL2 = " (select d.* from (";
            strSQL2 += " select aaa.* from (";
            strSQL2 += " select aa.*,rr.moneysegment,rr.approvalid from "+this.strTableName+" aa,loan_approvalrelation rr";
            strSQL2 += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyID;
            strSQL2 += " and rr.actionid = " + lActionID ;
            strSQL2 += " and rr.subloantypeid = " + lSubLoanTypeID ;    
            strSQL2 +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid = "+LOANConstant.LoanStatus.SUBMIT;

//            if(queryPurpose==1){
//	            	strSQL2+=" \n and aa.nstatusid = " + LOANConstant.LoanStatus.SAVE;
//            }
            
//            if(queryPurpose==2){
//	            	strSQL2+=" \n and aa.nstatusid = "+LOANConstant.LoanStatus.SUBMIT;
//            }
            
            if(startTransDiscountApplyId > 0 ){
            	strSQL2+=" \n and aa.ID >= " + startTransDiscountApplyId;
            }
            if(endTransDiscountApplyId > 0 ){
            	strSQL2+=" \n and aa.ID <= " + endTransDiscountApplyId;
            }
            if (clientID != -1){
            	strSQL2+=" \n and aa.nBorrowClientID = " + clientID;
            }
            if (startAmount != 0){
            	strSQL2+=" \n and aa.mLoanAmount >= " + startAmount;
            }
            if (endAmount != 0){
            	strSQL2+=" \n and aa.mLoanAmount <= " + endAmount;
            }
            if (currencyID > 0){
            	strSQL2+=" \n and aa.nCurrencyID = " + currencyID;
            }
            if (officeID > 0){
            	strSQL2+=" \n and aa.nOfficeID = " + officeID;
            }
            if (startDate != null){
            	strSQL2+=
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'";
            }
            if (endDate != null)
            {
            	strSQL2+=
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                        + DataFormat.getDateString(endDate)
                        + "'";
            }            
            
            strSQL2 += " ) aaa,(";
            strSQL2 += " select aa.id,max(rr.moneysegment) maxamount from "+this.strTableName+" aa,loan_approvalrelation rr";
            strSQL2 += " where rr.moduleid="+Constant.ModuleType.CRAFTBROTHER+" and aa.mLoanAmount>rr.moneysegment and rr.currencyid =" +currencyID;
            strSQL2 += " and rr.actionid = " + lActionID ;
            strSQL2 += " and rr.subloantypeid = " + lSubLoanTypeID ;            
            strSQL2 +=" and aa.ntypeid="+LOANConstant.LoanType.ZTX+" and aa.nstatusid = "+LOANConstant.LoanStatus.SUBMIT;
            
//            if(queryPurpose==1){
//            	strSQL2+=" \n and aa.nstatusid = " + LOANConstant.LoanStatus.SAVE;
//            }
//        
//            if(queryPurpose==2){
//            	strSQL2+=" \n and aa.nstatusid = "+LOANConstant.LoanStatus.SUBMIT;
//            }

            if(startTransDiscountApplyId > 0 ){
            	strSQL2+=" \n and aa.ID >= " + startTransDiscountApplyId;
            }
            if(endTransDiscountApplyId > 0 ){
            	strSQL2+=" \n and aa.ID <= " + endTransDiscountApplyId;
            }
            if (clientID != -1){
            	strSQL2+=" \n and aa.nBorrowClientID = " + clientID;
            }
            if (startAmount != 0){
            	strSQL2+=" \n and aa.mLoanAmount >= " + startAmount;
            }
            if (endAmount != 0){
            	strSQL2+=" \n and aa.mLoanAmount <= " + endAmount;
            }
            if (currencyID > 0){
            	strSQL2+=" \n and aa.nCurrencyID = " + currencyID;
            }
            if (officeID > 0){
            	strSQL2+=" \n and aa.nOfficeID = " + officeID;
            }
            if (startDate != null){
            	strSQL2+=
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'";
            }
            if (endDate != null)
            {
            	strSQL2+=
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                        + DataFormat.getDateString(endDate)
                        + "'";
            }     
            
            strSQL2 += " group by aa.id ) bbb";
            strSQL2 += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
            strSQL2 += " loan_approvalsetting e,loan_approvalitem f";
            strSQL2 += " where d.approvalid = e.id and f.nlevel=1 and f.napprovalid=e.id and f.nuserid="+userID+")";
            
            System.out.println("我的SQL^^^^^^^^^^^===="+strSQL+")"+UNION+strSQL2);
            tempSQL=strSQL+")"+UNION+strSQL2;

            try
            {
                long lID = -1;
                TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
                
                prepareStatement(tempSQL);
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    lID = rs1.getLong("id");
                    TransDiscountApplyInfo info = new TransDiscountApplyInfo();
                    if(lID > 0)
                    {
                        info=(TransDiscountApplyInfo)dao.findByID1(lID);//,info.getClass());

                        //当前查找总数
//                        info.setPageCount(lPageCount);
//                        info.setPageNo(pageNo);
//                        info.setRecordCount(lRecordCount);
//                        info.setTotalAmount(totalAmount);
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
                
                e1.printStackTrace();
            }
        }        
	    return (v.size() > 0 ? v : null);
    }
    

    /**
     *申请书的多笔查询操作
    */
    public Collection findByMultiOption(TransDiscountApplyQueryInfo qInfo)
        throws java.rmi.RemoteException, LoanException
    {
        Vector v = new Vector();
        
        StringBuffer sbCount = new StringBuffer();
        StringBuffer sbSelect = new StringBuffer();
        StringBuffer sbSQL = new StringBuffer();
        StringBuffer sbOrder = new StringBuffer();
        String strSQL = "";
        
        long queryPurpose = qInfo.getQueryPurpose();
        long officeID = qInfo.getOfficeId();
        long currencyID = qInfo.getCurrencyId();

        long startTransDiscountApplyId =
            qInfo.getStartTransDiscountApplyId();
        long endTransDiscountApplyId = 
            qInfo.getEndTransDiscountApplyId();
            
        long clientID = qInfo.getClientId();
        
        double startAmount = qInfo.getStartAmount();
        double endAmount = qInfo.getEndAmount();
        
        Timestamp startDate = qInfo.getQueryStartDate();
        Timestamp endDate = qInfo.getQueryEndDate();
        
        long statusID = qInfo.getQueryStatusId();
        
        long userID = qInfo.getUserId();
        
        ApprovalDelegation appBiz = new ApprovalDelegation();
        String strUser = qInfo.getUser();//审核的虚拟用户组

        long pageLineCount = qInfo.getPageLineCount();
        long pageNo = qInfo.getPageNo();
        long orderParam = qInfo.getOrderParam();
        long desc = qInfo.getDesc();
        String orderParamString = qInfo.getOrderParamString();

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
            //计算记录总数   
            sbCount.setLength(0);
            
            
            sbSQL.setLength(0);
            //sbSQL.append(" \n from Loan_LoanForm aa  ");
            sbSQL.append(" \n select * from "+this.strTableName+" aa  ");
            sbSQL.append(" \n where 1=1 ");
            sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
            sbSQL.append(" and aa.nStatusID in("+LOANConstant.LoanStatus.SUBMIT+","+LOANConstant.LoanStatus.SAVE+")");
            sbSQL.append(" \n and aa.nInputUserID = " + userID);
//            if (queryPurpose == 1) //for modify
//            {
//                sbSQL.append(" \n and aa.nInputUserID = " + userID);
//                sbSQL.append(" \n and (");
//                sbSQL.append("  (  aa.nNextCheckLevel = 1 ");
//                sbSQL.append("  and   aa.nStatusID = " + LOANConstant.LoanStatus.SUBMIT +"");
//                sbSQL.append("  ) or ( ");
//                sbSQL.append("aa.nStatusID ="+ LOANConstant.LoanStatus.SAVE + ")");
//                sbSQL.append(" )");
//                //sbSQL.append(" and aa.nNextCheckLevel = 1 ");
//            }
//            if (queryPurpose == 2) //for examine
//            {                               
//                try
//                {
//                    //获得真正来审批这个记录的人（真实或传给的虚拟的人！）
//                    strUser = appBiz.findTheVeryUser(Constant.ModuleType.CRAFTBROTHER,Constant.ApprovalLoanType.ZTX,Constant.ApprovalAction.LOAN_APPLY,officeID,currencyID,userID);
//                    
//                }
//                catch (Exception e1)
//                {
//                    
//                    e1.printStackTrace();
//                }
//                if(strUser.length() <= 0)
//                {
//                    strUser="("+userID+")";
//                }
//                
//            	sbSQL.append(" \n and ( ( aa.nStatusID = " + LOANConstant.LoanStatus.SUBMIT);
//                sbSQL.append(" \n and aa.nNextCheckUserID in " + strUser);
//            	sbSQL.append("  ) or (");
//            	sbSQL.append("  aa.nStatusID = " + LOANConstant.LoanStatus.CHECK + "))");
//            }
            if(startTransDiscountApplyId > 0 )
            {
                sbSQL.append(" \n and aa.ID >= " + startTransDiscountApplyId);
            }
            if(endTransDiscountApplyId > 0 )
            {
                sbSQL.append(" \n and aa.ID <= " + endTransDiscountApplyId);
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
            if (startDate != null)
            {
                sbSQL.append(
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'");
            }
            if (endDate != null)
            {
                sbSQL.append(
                    " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                        + DataFormat.getDateString(endDate)
                        + "'");
            }

            try
            {
                long lID = -1;
                TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
                System.out.println(sbSQL.toString());
                prepareStatement(sbSQL.toString());
                ResultSet rs1 = executeQuery();
                while (rs1 != null && rs1.next())
                {
                    lID = rs1.getLong("id");
                    TransDiscountApplyInfo info = new TransDiscountApplyInfo();
                    if(lID > 0)
                    {
                        info=(TransDiscountApplyInfo)dao.findByID(lID);//,info.getClass());

//                        //当前查找总数
//                        info.setPageCount(lPageCount);
//                        info.setPageNo(pageNo);
//                        info.setRecordCount(lRecordCount);
//                        info.setTotalAmount(totalAmount);
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
                
                e1.printStackTrace();
            }
        }
        return (v.size() > 0 ? v : null);
    }


	/**
	 *申请书的多笔查询操作(中油)
	*/
	public Collection cpfFindByMultiOption(TransDiscountApplyQueryInfo qInfo)
		throws java.rmi.RemoteException, LoanException
	{
		Vector v = new Vector();
        
		StringBuffer sbCount = new StringBuffer();
		StringBuffer sbSelect = new StringBuffer();
		StringBuffer sbSQL = new StringBuffer();
		StringBuffer sbOrder = new StringBuffer();
		String strSQL = "";
        
		long queryPurpose = qInfo.getQueryPurpose();
		long officeID = qInfo.getOfficeId();
		long currencyID = qInfo.getCurrencyId();

		long startTransDiscountApplyId =
			qInfo.getStartTransDiscountApplyId();
		long endTransDiscountApplyId = 
			qInfo.getEndTransDiscountApplyId();
            
		long clientID = qInfo.getClientId();
        
		double startAmount = qInfo.getStartAmount();
		double endAmount = qInfo.getEndAmount();
        
		Timestamp startDate = qInfo.getQueryStartDate();
		Timestamp endDate = qInfo.getQueryEndDate();
        
		long statusID = qInfo.getQueryStatusId();
        
		long userID = qInfo.getUserId();
        
		ApprovalDelegation appBiz = new ApprovalDelegation();
		String strUser = qInfo.getUser();//审核的虚拟用户组

		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();

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
            //计算记录总数   
            sbCount.setLength(0);
            sbCount.append(" \n select count(*),sum(aa.mLoanAmount)  ");
            
            sbSQL.setLength(0);
            //sbSQL.append(" \n from Loan_LoanForm aa  ");
            sbSQL.append(" \n from "+this.strTableName+" aa  ");
            sbSQL.append(" \n where 1=1 ");
            sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
            if (queryPurpose == 1) //for modify
            {
            	sbSQL.append(" \n and aa.nInputUserID = " + userID);
            	sbSQL.append(" \n and (");
            	sbSQL.append("  (  aa.nNextCheckUserID = " + userID);
            	sbSQL.append("  and   aa.nStatusID in ( " + LOANConstant.LoanStatus.SUBMIT +","+ LOANConstant.LoanStatus.SAVE +")");
            	sbSQL.append("  ) or (");
            	sbSQL.append("   aa.nStatusID in ( "+ LOANConstant.LoanStatus.CHECK +")");
            	sbSQL.append("  )");
            	sbSQL.append(" )");
            	//sbSQL.append(" and aa.nNextCheckLevel = 1 ");
            }
            if (queryPurpose == 2) //for examine
            {
            	sbSQL.append(" \n and (( aa.nStatusID = " + LOANConstant.LoanStatus.SUBMIT);      
            	sbSQL.append(" \n and aa.nNextCheckUserID = " + userID);
            	sbSQL.append("  ) or (");
            	sbSQL.append("  aa.nStatusID = "+ LOANConstant.LoanStatus.CHECK +" ) )");
            }
            if(startTransDiscountApplyId > 0 )
            {
            	sbSQL.append(" \n and aa.ID >= " + startTransDiscountApplyId);
            }
            if(endTransDiscountApplyId > 0 )
            {
            	sbSQL.append(" \n and aa.ID <= " + endTransDiscountApplyId);
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
            if (startDate != null)
            {
            	sbSQL.append(
            		" \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
            			+ DataFormat.getDateString(startDate)
            			+ "'");
            }
            if (endDate != null)
            {
            	sbSQL.append(
            		" \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
            			+ DataFormat.getDateString(endDate)
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
            		.equals("Cra_loanform"))
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
            strSQL = sbCount.toString() + sbSQL.toString();
            log4j.debug(strSQL);
            try
            {
            	prepareStatement(strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            		lRecordCount = rs.getLong(1);
            		totalAmount = rs.getDouble(2);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询申请书笔数产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询申请书笔数产生错误", e);
            }
            log4j.info("lRecordCount:"+lRecordCount);
            log4j.info("totalAmount:"+DataFormat.formatAmount(totalAmount));
            lPageCount = lRecordCount / pageLineCount;
            if ((lRecordCount % pageLineCount) != 0)
            {
            	lPageCount++;
            }
            //pageInfo[0] = lRecordCount;
            //pageInfo[1] = lPageCount;
            //v.add(pageInfo);
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集
            lRowNumStart = (pageNo - 1) * pageLineCount + 1;
            lRowNumEnd = lRowNumStart + pageLineCount - 1;
            sbSelect.append(" \n select id from ( select aa.*,rownum r from ( select * ");
            //sbSelect.append(sbSQL.toString()+sbOrder.toString());
            sbOrder.append(" \n ) aa ) where r between " + lRowNumStart + " and " + lRowNumEnd);
            strSQL = sbSelect.toString()+sbSQL.toString()+sbOrder.toString();
            log4j.debug(strSQL);
            try
            {
            	long lID = -1;
            	TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
                
            	prepareStatement(strSQL);
            	ResultSet rs1 = executeQuery();
            	while (rs1 != null && rs1.next())
            	{
            		lID = rs1.getLong("id");
            		TransDiscountApplyInfo info = new TransDiscountApplyInfo();
            		if(lID > 0)
            		{
            			info=(TransDiscountApplyInfo)dao.findByID(lID);//,info.getClass());

            			//当前查找总数
            			info.setPageCount(lPageCount);
            			info.setPageNo(pageNo);
            			info.setRecordCount(lRecordCount);
            			info.setTotalAmount(totalAmount);
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
                
                e1.printStackTrace();
            }
        }
		return (v.size() > 0 ? v : null);
	}
    
    /**
     *票据查询操作
    */
	public Collection findTransDiscountApplyBill(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
    {
        Vector v = new Vector();
        
        StringBuffer sbCount = new StringBuffer();
        StringBuffer sbSelect = new StringBuffer();
        StringBuffer sbSQL = new StringBuffer();
        StringBuffer sbSQL1 = new StringBuffer();
        StringBuffer sbOrder = new StringBuffer();
        String strSQL = "";
        
		long currencyId = qInfo.getCurrencyId();
		long officeId = qInfo.getOfficeId();

        long startDiscountContractId = qInfo.getStartDiscountContractId();
        long endDiscountContractId = qInfo.getEndDiscountContractId();
        
        long startTransDiscountContractId =
            qInfo.getStartTransDiscountContractId();
        long endTransDiscountContractId = 
            qInfo.getEndTransDiscountContractId();
            
        long billId = qInfo.getBillId();
        String code = qInfo.getCode();
        
        double startAmount = qInfo.getStartAmount();
        double endAmount = qInfo.getEndAmount();
        
        long pageLineCount = qInfo.getPageLineCount();
        long pageNo = qInfo.getPageNo();
        long orderParam = qInfo.getOrderParam();
        long desc = qInfo.getDesc();
        String orderParamString = qInfo.getOrderParamString();

        Timestamp startDate = qInfo.getQueryStartDate();//隐含条件
        Timestamp repurchaseDate = qInfo.getRepurchaseDate();//回购日期
        
        long discountTypeId = qInfo.getDiscountTypeId();//买断、回购
        
        long lRecordCount = 0;
        long lPageCount = 0;
        long lRowNumStart = 1;
        long lRowNumEnd = 1;
        double totalAmount = 0;
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
            //计算记录总数   
            sbCount.setLength(0);
            sbCount.append(" \n select count(*),sum(bb.mAmount) ");
            
            sbSQL.setLength(0);
            sbSQL.append(" \n from Loan_DiscountContractBill bb,loan_contractform cf,sett_transgrantdiscount gb ");
            sbSQL.append(" \n where 1=1 and bb.ncontractid=cf.id and gb.ndiscountnoteid=bb.ndiscountcredenceid");
            sbSQL.append(" \n and bb.nContractId > 0 and gb.nstatusid= "+SETTConstant.TransactionStatus.CHECK+" and cf.ntypeid= "+LOANConstant.LoanType.TX);
            // 可查询出的合同票据应为已入库的票据
            sbSQL.append(" \n and bb.nstoragestatusid = " + BILLConstant.DraftInOrOut.IN);
            //查询贴现合同状态为执行中的票据
            sbSQL.append(" \n and (cf.nstatusid = " +LOANConstant.ContractStatus.ACTIVE + " or cf.nstatusid = "+LOANConstant.ContractStatus.NOTACTIVE +")" );
			if (currencyId > 0)
			{
				sbSQL.append(" \n and bb.nCurrencyId = " + currencyId);
			}
			if (officeId > 0)
			{
				sbSQL.append(" \n and bb.nOfficeId = " + officeId);
			}
            if (startDate != null)//隐含条件
            {
                sbSQL.append(
                    " \n and to_char(bb.dtEnd,'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'");
            }
            if (discountTypeId == LOANConstant.TransDiscountType.REPURCHASE)
            {
                sbSQL.append(
                    " \n and to_char(nvl(bb.RepurchaseDate,sysdate+1000),'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'");
                
                sbSQL.append(
                        " \n and to_char(bb.dtEnd,'yyyy-mm-dd') >= '"
                            + DataFormat.getDateString(repurchaseDate)
                            + "'");
                
                sbSQL.append(
                        " \n and ((to_char(bb.RepurchaseDate,'yyyy-mm-dd') >= '"
                            + DataFormat.getDateString(repurchaseDate) +"' and bb.RepurchaseDate is not null) or bb.RepurchaseDate is null) ");

            }
            //sbSQL.append(" \n and aa.nStatusId in ("+LOANConstant.ContractStatus.CHECK+","+LOANConstant.ContractStatus.NOTACTIVE+","+LOANConstant.ContractStatus.ACTIVE+","+LOANConstant.ContractStatus.EXTEND+")");
            sbSQL.append(" \n and bb.nStatusId = " + Constant.RecordStatus.VALID);
            sbSQL.append(" and nvl(bb.nSellStatusId,1) = " + Constant.YesOrNo.YES );
            //*
            sbSQL.append(" \n and ( ");
            sbSQL.append(" \n ( bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.DISCOUN);

            if (startDiscountContractId != -1)
            {
                sbSQL.append(" \n and bb.nContractID >= " + startDiscountContractId);
            }
            if (endDiscountContractId != -1)
            {
                sbSQL.append(" \n and bb.nContractID <= " + endDiscountContractId);
            }
            sbSQL.append(" \n ) or (");
            sbSQL.append(" \n bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
            if (startTransDiscountContractId != -1)
            {
                sbSQL.append(" \n and bb.nContractID >= " + startTransDiscountContractId);
            }
            if (endTransDiscountContractId != -1)
            {
                sbSQL.append(" \n and bb.nContractID <= " + endTransDiscountContractId);
            }
            //*/
            sbSQL.append(" \n ) ");
            sbSQL.append(" \n ) ");
            
            
            //加上只查转贴现合同而来的票据或只查贴现而来的票据的情况
            if ((startDiscountContractId > 0 || endDiscountContractId >0) && (startTransDiscountContractId <= 0 && endTransDiscountContractId <= 0 ))
            {
            	sbSQL.append(" \n and bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.DISCOUN);
            	if (startDiscountContractId != -1)
            		sbSQL.append(" \n and bb.nContractID >= " + startDiscountContractId);
            	if (endDiscountContractId != -1)
                    sbSQL.append(" \n and bb.nContractID <= " + endDiscountContractId);
            }
            
            if ((startTransDiscountContractId > 0 || endTransDiscountContractId > 0)&&(startDiscountContractId <=0 && endDiscountContractId<= 0))
            {
            	sbSQL.append(" \n and  bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
                if (startTransDiscountContractId != -1)
                {
                    sbSQL.append(" \n and bb.nContractID >= " + startTransDiscountContractId);
                }
                if (endTransDiscountContractId != -1)
                {
                    sbSQL.append(" \n and bb.nContractID <= " + endTransDiscountContractId);
                }
            }
            
            
            if (startAmount != 0)
            {
                sbSQL.append(" \n and bb.mAmount >= " + startAmount);
            }
            if (endAmount != 0)
            {
                sbSQL.append(" \n and bb.mAmount <= " + endAmount);
            }
            if (billId != -1)
            {
                sbSQL.append(" \n and bb.ID = " + billId);
            }
            if (code != "" && code.length() > 0)
            {
                sbSQL.append(" \n and bb.sCode = '" + code + "'");
            }
            //为转贴现选票查询添加查询条件
            if(qInfo.getAcceptPOTypeID()>0)
            {
            	sbSQL.append(" and bb.NACCEPTPOTYPEID = "+qInfo.getAcceptPOTypeID());
            }
            //方正项目，转帖现如果是卖出买断式，则只能选择买入买断的转贴现票据
            if(qInfo.getDiscountTypeId() == LOANConstant.TransDiscountType.BUYBREAK){
            	sbSQL.append(" and (cf.ndiscounttypeid = " +  LOANConstant.TransDiscountType.BUYBREAK + " or cf.ndiscounttypeid is null)");
            }
            ////////////////////////////排序处理//////////////////////
            sbOrder.setLength(0);
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0)
            {
                if (orderParamString
                    .substring(0, nIndex)
                    .toLowerCase()
                    .equals("loan_discountcontractbill"))
                {
                    sbOrder.append(" \n order by " + orderParamString.substring(nIndex + 1));
                }
            }
            else
            {
                sbOrder.append(" \n order by nContractID,nSerialNo ");
            }
            if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
                sbOrder.append(" desc ");
            }
            
            
            sbSQL1.setLength(0);
            sbSQL1.append(" \n from Loan_DiscountContractBill bb,loan_contractform cf,sett_transcraftbrother bt,rtransdiscountcredencebill rb ");
            sbSQL1.append(" \n where 1=1 and rb.transdiscountcredenceid = bt.nnoticeid and rb.discountcontractbillid = bb.id and bb.ncontractid = cf.id");
            sbSQL1.append(" \n and bb.nContractId > 0 and cf.ntypeid = "+LOANConstant.LoanType.ZTX +" and bt.nstatusid = "+SETTConstant.TransactionStatus.CHECK );
            // 可查询出的合同票据应为已入库的票据
            sbSQL1.append(" \n and bb.nstoragestatusid = " + BILLConstant.DraftInOrOut.IN);
            //查询贴现合同状态为执行中的票据
            sbSQL1.append(" \n and (cf.nstatusid = " +LOANConstant.ContractStatus.ACTIVE + " or cf.nstatusid = "+LOANConstant.ContractStatus.NOTACTIVE +")" );
			if (currencyId > 0)
			{
				sbSQL1.append(" \n and bb.nCurrencyId = " + currencyId);
			}
			if (officeId > 0)
			{
				sbSQL1.append(" \n and bb.nOfficeId = " + officeId);
			}
            if (startDate != null)//隐含条件
            {
                sbSQL1.append(
                    " \n and to_char(bb.dtEnd,'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'");
            }
            if (discountTypeId == LOANConstant.TransDiscountType.REPURCHASE)
            {
                sbSQL1.append(
                    " \n and to_char(nvl(bb.RepurchaseDate,sysdate+1000),'yyyy-mm-dd') >= '"
                        + DataFormat.getDateString(startDate)
                        + "'");

                sbSQL1.append(
                        " \n and to_char(bb.dtEnd,'yyyy-mm-dd') >= '"
                            + DataFormat.getDateString(repurchaseDate)
                            + "'");
                
                sbSQL1.append(
                        " \n and ((to_char(bb.RepurchaseDate,'yyyy-mm-dd') >= '"
                            + DataFormat.getDateString(repurchaseDate) +"' and bb.RepurchaseDate is not null) or bb.RepurchaseDate is null) ");

            }
            //sbSQL.append(" \n and aa.nStatusId in ("+LOANConstant.ContractStatus.CHECK+","+LOANConstant.ContractStatus.NOTACTIVE+","+LOANConstant.ContractStatus.ACTIVE+","+LOANConstant.ContractStatus.EXTEND+")");
            sbSQL1.append(" \n and bb.nStatusId = " + Constant.RecordStatus.VALID);
            sbSQL1.append(" and nvl(bb.nSellStatusId,1) = " + Constant.YesOrNo.YES );
            //*
            sbSQL1.append(" \n and ( ");
            sbSQL1.append(" \n ( bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.DISCOUN);

            if (startDiscountContractId != -1)
            {
                sbSQL1.append(" \n and bb.nContractID >= " + startDiscountContractId);
            }
            if (endDiscountContractId != -1)
            {
                sbSQL1.append(" \n and bb.nContractID <= " + endDiscountContractId);
            }
            sbSQL1.append(" \n ) or (");
            sbSQL1.append(" \n bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
            if (startTransDiscountContractId != -1)
            {
                sbSQL1.append(" \n and bb.nContractID >= " + startTransDiscountContractId);
            }
            if (endTransDiscountContractId != -1)
            {
                sbSQL1.append(" \n and bb.nContractID <= " + endTransDiscountContractId);
            }
            //*/
            sbSQL1.append(" \n ) ");
            sbSQL1.append(" \n ) ");
            
            
            //加上只查转贴现合同而来的票据或只查贴现而来的票据的情况
            if ((startDiscountContractId > 0 || endDiscountContractId >0) && (startTransDiscountContractId <= 0 && endTransDiscountContractId <= 0 ))
            {
            	sbSQL1.append(" \n and bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.DISCOUN);
            	if (startDiscountContractId != -1)
            		sbSQL1.append(" \n and bb.nContractID >= " + startDiscountContractId);
            	if (endDiscountContractId != -1)
                    sbSQL1.append(" \n and bb.nContractID <= " + endDiscountContractId);
            }
            
            if ((startTransDiscountContractId > 0 || endTransDiscountContractId > 0)&&(startDiscountContractId <=0 && endDiscountContractId<= 0))
            {
            	sbSQL1.append(" \n and  bb.nBillSourceTypeId = " + LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
                if (startTransDiscountContractId != -1)
                {
                    sbSQL1.append(" \n and bb.nContractID >= " + startTransDiscountContractId);
                }
                if (endTransDiscountContractId != -1)
                {
                    sbSQL1.append(" \n and bb.nContractID <= " + endTransDiscountContractId);
                }
            }
            
            
            if (startAmount != 0)
            {
                sbSQL1.append(" \n and bb.mAmount >= " + startAmount);
            }
            if (endAmount != 0)
            {
                sbSQL1.append(" \n and bb.mAmount <= " + endAmount);
            }
            if (billId != -1)
            {
                sbSQL1.append(" \n and bb.ID = " + billId);
            }
            if (code != "" && code.length() > 0)
            {
                sbSQL1.append(" \n and bb.sCode = '" + code + "'");
            }
            //为转贴现选票查询添加查询条件
            if(qInfo.getAcceptPOTypeID()>0)
            {
            	sbSQL1.append(" and bb.NACCEPTPOTYPEID = "+qInfo.getAcceptPOTypeID());
            }
            //方正项目，转帖现如果是卖出买断式，则只能选择买入买断的转贴现票据
            if(qInfo.getDiscountTypeId() == LOANConstant.TransDiscountType.BUYBREAK){
            	sbSQL1.append(" and (cf.ndiscounttypeid = " +  LOANConstant.TransDiscountType.BUYBREAK + " or cf.ndiscounttypeid is null)");
            }
           
            
            
            strSQL = sbCount.toString() + sbSQL.toString() +" union " +sbCount.toString() + sbSQL1.toString();
            log4j.debug(strSQL);
            System.out.println("----------"+strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                /*
                if (rs != null && rs.next())
                {
                    lRecordCount = rs.getLong(1);
                    totalAmount = rs.getDouble(2);
                }
                */
                if(rs != null)
                {
                	while(rs.next())
                	{
                		 lRecordCount += rs.getLong(1);
                         totalAmount += rs.getDouble(2);
                	}
                }
                
            }
            catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("批量查询申请书笔数产生错误", e);
            }
            catch (SQLException e)
            {
                throw new LoanDAOException("批量查询申请书笔数产生错误", e);
            }
            log4j.info("lRecordCount==="+lRecordCount);
            lPageCount = lRecordCount / pageLineCount;
            if ((lRecordCount % pageLineCount) != 0)
            {
                lPageCount++;
            }
            //pageInfo[0] = lRecordCount;
            //pageInfo[1] = lPageCount;
            //v.add(pageInfo);
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集
            lRowNumStart = (pageNo - 1) * pageLineCount + 1;
            lRowNumEnd = lRowNumStart + pageLineCount - 1;
            //sbSelect.append(" \n select * from ( select aa.*,rownum r from ( select bb.*,cf.dtdiscountdate,cf.ninorout ");
            sbSelect.append(" \n select * from ( select aa.*,rownum r from (");
            //sbSelect.append(sbSQL.toString());
            sbOrder.append(" \n ) aa ) where r between " + lRowNumStart + " and " + lRowNumEnd);
            //sbSQL="select bb.*,cf.dtdiscountdate,cf.ninorout " + sbSQL.toString()+" union "+" select bb.*,cf.dtdiscountdate,cf.ninorout "+sbSQL1.toString();
            //strSQL = sbSelect.toString()+sbSQL.toString()+sbOrder.toString();
            strSQL = sbSelect.toString()+"select * from ( select bb.*,cf.dtdiscountdate,cf.ninorout " + sbSQL.toString()+" union "+" select bb.*,cf.dtdiscountdate,cf.ninorout "+sbSQL1.toString() +")"+sbOrder.toString();
           
            System.out.print(strSQL);
            
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs1 = executeQuery();
                long lID = -1;
                while (rs1 != null && rs1.next())
                {
                    lID = rs1.getLong("id");
                    //System.out.println("id=="+lID);
                    TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();
                    info.setId(lID);
                    info.setContractID(rs1.getLong("nContractId"));
                    info.setSerialNo(rs1.getLong("nSerialNo"));
                    info.setUserName(rs1.getString("sUserName"));
                    info.setBank(rs1.getString("sBank"));
                    info.setIsLocal(rs1.getLong("nIsLocal"));
                    info.setCreate(rs1.getTimestamp("dtCreate"));
                    info.setRepurchaseDate(rs1.getTimestamp("RepurchaseDate"));
                    info.setEnd(rs1.getTimestamp("dtEnd"));
                    info.setCode(rs1.getString("sCode"));
                    info.setAmount(rs1.getDouble("mAmount"));
                    info.setStatusID(rs1.getLong("nStatusID"));
                    info.setAddDays(rs1.getLong("nAddDays"));
                    info.setDiscountCredenceID(
                        rs1.getLong("nDiscountCredenceID"));
                    info.setOB_nDiscountCredenceID(
                        rs1.getLong("OB_nDiscountCredenceID"));
                    info.setAcceptPoTypeID(rs1.getLong("nAcceptPoTypeID"));
                    info.setFormerOwner(rs1.getString("sFormerOwner"));
                    info.setCheckAmount(rs1.getDouble("mCheckAmount"));
                    
                    info.setAccrual(info.getAmount()-info.getCheckAmount());
                    
                    info.setPageCount(lPageCount);
                    info.setRecordCount(lRecordCount);
                    info.setTotalBillAmount(totalAmount);
                    
                    // 贴现日，取自loan_contractform
                    info.setDiscountDate(rs1.getTimestamp("dtdiscountdate"));
                    info.setInOrOut(rs1.getLong("NINOROUT")) ;               
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
                
                e1.printStackTrace();
            }
        }
        return (v.size() > 0 ? v : null);
    }

    /**
     *申请书的审核操作
    */
    public long check(ApprovalTracingInfo ATInfo) throws LoanDAOException
    {
        long lMaxID = -1;
        long lSerialID = -1;
        long lStatusID = -1;
        long lResultID = -1;
        String strSQL = "";
        //定义相应操作常量
		long lCurrencyID = ATInfo.getCurrencyID();
		long lOfficeID = ATInfo.getOfficeID();
        //模块类型
        long lModuleID = ATInfo.getModuleID();
        //业务类型
        long lLoanTypeID = ATInfo.getLoanTypeID();
        //操作类型
        long lActionID = ATInfo.getActionID();
        long lApprovalContentID = ATInfo.getApprovalContentID();
        System.out.println("-------------------------------------:"+ATInfo.getApprovalContentID());
        long lNextUserID = ATInfo.getNextUserID();
        long lApprovalID = ATInfo.getApprovalID();
        long lUserID = ATInfo.getInputUserID();
        long lLevel = -1;
        TransDiscountApplyInfo aInfo = new TransDiscountApplyInfo();
        
        ApprovalSettingInfo appInfo = new ApprovalSettingInfo();
    	ApprovalTracingInfo info = new ApprovalTracingInfo();
    	ApprovalDelegation appbiz = new ApprovalDelegation();
    	
    	try
        {
            //获得ApprovalID
            //lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);    
    		//下一级审核人级别
    		System.out.println("下一级审核人级别："+lApprovalID+"       "+lNextUserID);
    		lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
    		Log.print("下一级审核人级别：" + lLevel);
    		//审批设置
    		appInfo = appbiz.findApprovalSetting(lApprovalID);
        } 
        catch (Exception e2)
        {
            
            e2.printStackTrace();
        }
        //////////////////////////////////////////////////////////////////////
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
        {
            aInfo.setId(lApprovalContentID);
            aInfo.setStatusId(LOANConstant.LoanStatus.REFUSE);
            try
            {
                update(aInfo);
            }
            catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
        {
            Log.print("====check====");
            aInfo.setId(lApprovalContentID);
            //:
//            aInfo.setStatusId(LOANConstant.LoanStatus.SUBMIT);
            aInfo.setStatusId(LOANConstant.LoanStatus.APPROVALING);
            if (lModuleID == Constant.ModuleType.BILL)
            {
            	aInfo.setBillStatus(BILLConstant.TransctionStatus.APPROVALING);
            }
            //:~
            aInfo.setNextCheckUserId(lNextUserID);
            //aInfo.setNextCheckLevel(lNextLevel+1);
            if (appInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
    		{
                aInfo.setNextCheckLevel(lLevel);
    		    Log.print("更新下一个审核级别（可越级）：" + lLevel);
    		}
    		else
    		{
    		    aInfo.setNextCheckLevel(getNextCheckLevelByApplyID(lApprovalContentID) + 1);
    		    Log.print("更新下一个审核级别（不可越级）：" + getNextCheckLevelByApplyID(lApprovalContentID) + 1);
    		}            
            try
            {
                update(aInfo);
            }
            catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER)
            //审批&&最后
        {
            aInfo.setId(lApprovalContentID);
            aInfo.setStatusId(LOANConstant.LoanStatus.CHECK);
            aInfo.setNextCheckUserId(lNextUserID);
            if (lModuleID == Constant.ModuleType.BILL)
            {
            	aInfo.setBillStatus(BILLConstant.TransctionStatus.APPROVALED);
            	aInfo.setIsChoose(1);
            }
            try
            {
                update(aInfo);
            }
            catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
            }
            //审批完成后需要做的操作
            try
            {
            	if (lModuleID != Constant.ModuleType.BILL)
                {
            		doAfterCheckOver(lApprovalContentID);
                }
            	else{
            		//票据出库
            		System.out.println("-----------票据出库开始---------");
            		//查询该笔申请下的已选票据信息
            		System.out.println("-----------取得票据信息---------");
            		Collection outBillColl = findOutBillByTransDiscountApplyID(lApprovalContentID);
            		Iterator iter = null;
            		if(outBillColl!=null && outBillColl.size()>0)
            		{
            			iter = outBillColl.iterator();
            			while(iter.hasNext())
            			{
            				TransDiscountApplyBillInfo tdabinfo = (TransDiscountApplyBillInfo)iter.next();
            				TransDiscountApplyInfo aInfo1 = new TransDiscountApplyInfo();
            				aInfo1 = (TransDiscountApplyInfo) findByID(lApprovalContentID);
            				System.out.println("-----------取得入库信息id---------");
            				long lCraftInID = getCraftInIDbyBillID(tdabinfo.getId());
                    		
                    		if(lCraftInID > 0)
                    		{
                    			DraftDelegation delegation = new DraftDelegation();
                    			DraftStorageAssembleInfo draftStorageAssembleInfo = new DraftStorageAssembleInfo();
                    			TransDraftOutInfo transDraftOutInfo=new TransDraftOutInfo();
                    			
                    			transDraftOutInfo.setCurrencyID(tdabinfo.getCurrencyId());
                    			transDraftOutInfo.setOfficeID(tdabinfo.getOfficeId());
                    			transDraftOutInfo.setInputDate(Env.getSystemDate(lOfficeID,lCurrencyID));
                    			transDraftOutInfo.setInputUserID(lUserID);
                    			transDraftOutInfo.setStatusID(Constant.RecordStatus.VALID);
                    			UtilOperation utilOperation=new UtilOperation(); 
                    			transDraftOutInfo.setTransCode(utilOperation.getNewDraftTransactionNo(tdabinfo.getOfficeId(),tdabinfo.getCurrencyId(),BILLConstant.DraftOperationType.DraftOut));
                    			transDraftOutInfo.setTransTypeID(BILLConstant.DraftOperationType.DraftIn);
                    			transDraftOutInfo.setOutDate(tdabinfo.getCreate());
                    			// 加入卖出转贴现出库类型
                    			// 卖出买断：转贴现卖出（出库）
                    			// 卖出回购：正回购卖出（出库）
                    			if(aInfo1.getDiscountTypeId()==LOANConstant.TransDiscountType.BUYBREAK)
                    			{
                    				transDraftOutInfo.setBillDestinationID(BILLConstant.BillWhither.REDISCOUNTSELLOUT);
                    			}
                    			if(aInfo1.getDiscountTypeId()==LOANConstant.TransDiscountType.REPURCHASE)
                    			{
                    				transDraftOutInfo.setBillDestinationID(BILLConstant.BillWhither.REPURCHASESELLOUT);
                    			}
                    			
                    			//transDraftOutInfo.setOutContractCode(tdabinfo.getContractID());//卖出时合同编号
                    			
                    			System.out.println("-----------取得入库信息---------");
                    			draftStorageAssembleInfo = delegation.findDraftInByID(lCraftInID);
                    			draftStorageAssembleInfo.setTransDraftOutInfo(transDraftOutInfo);
                    			
                    			//保存出库记录
                    			System.out.println("-----------保存出库信息---------");
                    			
                    			delegation.saveDraftOut(draftStorageAssembleInfo);
                    		}
                    		else
                    		{
                    			System.out.println("根据票据id取得相应入库信息出错！");
                    			throw new BillException();
                    		}
            			}
            		}
            		System.out.println("-----------票据出库结束---------");
            	}
            }
            catch (LoanException e1)
            {
                
                e1.printStackTrace();
            }
            catch (RemoteException e1)
            {
                
                e1.printStackTrace();
            } catch (BillException e) {
				
				e.printStackTrace();
				System.out.println("");
			}
        }
        if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
        {
            aInfo.setId(lApprovalContentID);
//            aInfo.setStatusId(LOANConstant.LoanStatus.SUBMIT);
            aInfo.setStatusId(LOANConstant.LoanStatus.SUBMIT);
            aInfo.setNextCheckUserId(ATInfo.getInputUserID());
            //置下一级审核为1
            aInfo.setNextCheckLevel(1);
            try
            {
                update(aInfo);
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
    		
		/**
		 * @author lazhang
		 * @param info
		 * 中油申请书审核操作
		 */
		public long cpfCheckApply(ApprovalTracingInfo info) throws LoanDAOException, RemoteException
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			TransDiscountApplyDAO dao = new TransDiscountApplyDAO(strTableName);
			//long lOBStatusID = 0;
			String strSQL = "";
			//String strSQLBill="";
			
			Log.print("=========================="+strTableName);
								
			try
			{
				initDAO();
				
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				strSQL = "";
				if (info.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
				{
					//删除以前的审核纪录
					strSQL = " update ReviewOpinion set nStatusID=? where nReviewTypeID=? and nReviewContentID=? ";
					ps = prepareStatement(strSQL);
					ps.setLong(1, Constant.RecordStatus.INVALID);
					ps.setLong(2, info.getActionID());
					ps.setLong(3, info.getApprovalContentID());
					ps.executeUpdate();
					ps.close();
					ps = null;
				
					strSQL = "update "+ strTableName + " set nStatusID=" + LOANConstant.DiscountCredenceStatus.REFUSE + " where ID=" + info.getApprovalContentID();
				
				}
				if (info.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
				{
					strSQL = "update "+ strTableName + " set nNextCheckUserID=" + info.getNextUserID() + " where ID=" + info.getApprovalContentID();
				}

				if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER)	//审批&&最后
				{
					strSQL = "update "+ strTableName +" set nNextCheckUserID=" + info.getNextUserID() + ", nStatusID=" + LOANConstant.DiscountCredenceStatus.CHECK + " where ID=" + info.getApprovalContentID();
					//审批完成之后的操作
					try	
					{
						dao.doAfterCheckOver(info.getApprovalContentID());						
					}
					catch (LoanException e1)
					{
						e1.printStackTrace();
					}
					catch (RemoteException e1)
					{
						e1.printStackTrace();
					}
				}
				if (info.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
				{
					strSQL = "update " + strTableName + " set nNextCheckUserID=nInputUserID, nStatusID=" + LOANConstant.DiscountCredenceStatus.SUBMIT + " where ID=" + info.getApprovalContentID();
				}

				System.out.println(strSQL);
				ps = prepareStatement(strSQL);
				ps.executeUpdate();
				ps.close();
				ps = null;
				
				//addReviewOpinion(info);
							
				finalizeDAO();
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				//throw new RemoteException(ex.getMessage());
				throw new LoanDAOException("审核失败",ex);
			}
			finally
			{
			    try
                {
                    finalizeDAO();
                } 
			    catch (ITreasuryDAOException e)
                {
                    
                    e.printStackTrace();
                }
			}
			return info.getApprovalContentID();
		}
		
		/**
		 * @author lazhang
		 * @param info
		 * 在审核完毕之后添加审核意见  insert into ReviewOpinion
		 */
		
		public void addReviewOpinion(ApprovalTracingInfo info) throws LoanDAOException, RemoteException
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			long lMaxID = -1;
			long lSerialID = -1;
			String strSQL = "";
			Log.print("=========================="+strTableName);
			try
			{
				initDAO();
				
				strSQL = "select nvl(max(ID)+1,1) ,nvl(max(nSerialID)+1,1) from reviewopinion";
				ps = prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lMaxID = rs.getLong(1);
					lSerialID = rs.getLong(2);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				strSQL = "";
				
				if (info.getCheckActionID() != LOANConstant.Actions.REJECT) //拒绝
				{
					strSQL = "insert into ReviewOpinion (ID, nReviewTypeID, nReviewContentID, nSerialID, nUserID, nNextUserID, sOpinion, dtDate, nResultID, nStatusID) values (?,?,?,?,?,?,?,sysdate,?,?)";
					//System.out.println(strSQL);
					ps = prepareStatement(strSQL);
					ps.setLong(1, lMaxID);
					ps.setLong(2, info.getActionID());
					ps.setLong(3, info.getApprovalContentID());
					ps.setLong(4, lSerialID);
					ps.setLong(5, info.getUserID());
					ps.setLong(6, info.getNextUserID());
					ps.setString(7, info.getOpinion());
					ps.setLong(8, info.getResultID());
					ps.setLong(9, info.getStatusID());
					ps.executeUpdate();
					ps.close();
					ps = null;
				}
				finalizeDAO();
				
			} catch (ITreasuryDAOException e)
			{
				
				e.printStackTrace();
			} catch (SQLException e)
			{
				
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
                    
                    e1.printStackTrace();
                }
			}
		}
    
    /**
     * 根据贷款种类生成信贷(外汇)申请书编号
     * 操作数据库表<LoanInfo>or<DiscountApply>
     * @return    String   sApplyCode         贴现申请书编号
     **/
    public String createApplyCode() throws java.rmi.RemoteException, LoanException
    {
        ResultSet rs = null;

        String strSelect = null;
        String strYear = null;
        long lMaxCode = 0;
        String strApplyCode = null;

        try
        {
            initDAO();

            //取两位的年份
            strSelect = " select to_char(sysdate,'yy') from dual ";
            prepareStatement(strSelect);
            rs = executeQuery();
            if (rs.next())
            {
                strYear = rs.getString(1);
                //Log.print("Two Bits Year is: " + strYear);
            }
            rs.close();
            rs = null;

            //计算最大序列号

            //贴现
            strSelect = " select nvl(max(substr(sApplyCode,6,3)),0) + 1 from Cra_LoanForm where 1 = 1 "
                      + " and sApplyCode like 'A" + strYear + "38%'";

            Log.print(strSelect);
            prepareStatement(strSelect);
            rs = executeQuery();
            if (rs.next())
            {
                lMaxCode = rs.getLong(1);
                Log.print("MaxCode is: " + lMaxCode);
            }
            rs.close();
            rs = null;

            finalizeDAO();
            //贴现
            strApplyCode = "A" + strYear + "38" + DataFormat.formatInt(lMaxCode, 3, true);
        }
        catch (ITreasuryDAOException e1)
        {
            
            e1.printStackTrace();
            throw new LoanDAOException(e1);
        }
        catch (SQLException e1)
        {
            
            e1.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        return (strApplyCode);
    }



    /**
     * 中油
     * 根据贷款种类生成信贷(外汇)申请书编号
     * 操作数据库表<LoanInfo>or<DiscountApply>
     * @param     long     lTypeID            贷款种类(1-12)见Notes.java,贴现=13需额外处理
     * @return    String   sApplyCode         信贷申请书编号
     **/
    public String createApplyCode(long lClientID, long lOfficeID,long lInOrOut)
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
                //取得办事处名称
                strSQL = " SELECT SNAME FROM OFFICE WHERE ID = " + lOfficeID;
                prepareStatement(strSQL);
                rs = executeQuery();
                if (rs != null && rs.next())
                {
                    strOfficeName = rs.getString(1);
                    log4j.info("*** 取得办事处名称: " + strOfficeName);
                }
                if (strOfficeName.indexOf("大庆") >= 0)
                {
                    strOfficeName = "-dq";
                }
                else if (strOfficeName.indexOf("沈阳") >= 0)
                {
                    strOfficeName = "-sy";
                }
                else if (strOfficeName.indexOf("吉林") >= 0)
                {
                    strOfficeName = "-jl";
                }
                else if (strOfficeName.indexOf("西安") >= 0)
                {
                    strOfficeName = "-xa";
                }
                else
                {
                    strOfficeName = "";
                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
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
                    " select nvl(max(substr(sApplyCode,11,4)),0)+1  "
                        + " from Cra_LoanForm where sApplyCode like 'Y"
                        + strYear
                        + ApplyType
                        + "%' and sApplyCode  not like '%(T)%' and nOfficeID = "
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
                
                e1.printStackTrace();
                throw new LoanDAOException(e1);
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
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        return (sApplyCode);
    }
    /*
     *  华能
     * 
     */
    private String getNextApplyCode(long lTransactionTypeID)
        throws LoanDAOException
    {
        String strSQL = "";
        String strCode = "000";
        long lCode = 0;
        Timestamp tsToday = Env.getSystemDateTime();
        String strYear = DataFormat.getDateString(tsToday).substring(2, 4);
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
            strSQL =
                " select max(nvl(sApplyCode,0)) Code "
                    //+ " from loan_LoanForm "
                    + " from " + this.strTableName + " "
                    + " where sApplyCode like 'Y"
                    + strYear
                    + "ZTC"
                    + lTransactionTypeID
                    + "%'";
            log4j.debug(strSQL);
            try
            {
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    strCode = rs.getString(1);
                    log4j.debug(strCode);
                    if (strCode != null && strCode.length() == 11)
                    {
                        lCode = Long.parseLong(strCode.substring(8)) + 1;
                    }
                    else
                    {
                        lCode = 1;
                    }
                    strCode =
                        "Y"
                            + strYear
                            + lTransactionTypeID
                            + DataFormat.formatInt(lCode, 3, true);
                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
            }
            catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException("生成申请书编号产生错误", e);
            }
            catch (SQLException e)
            {
                throw new LoanDAOException("生成申请书编号产生错误", e);
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
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        log4j.debug("  strcode: " + strCode);
        return strCode;
    }
    /*
     *  (non-Javadoc)
     * 
     */
    private void doAfterCheckOver(long lApplyID) throws java.rmi.RemoteException, LoanException
    {
        TransDiscountApplyInfo aInfo = new TransDiscountApplyInfo();
        long lContractId = -1;
        String strContractTable = "";
        String strFileName = "";        
        strContractTable = "Loan_ContractForm";        
        String strContractBillTable = "";
        strContractBillTable = "Loan_DiscountContractBill";
        
        try
        {
            Timestamp Date = Env.getSystemDateTime();
            //aInfo =(TransDiscountApplyInfo) findByID(lApplyID);//, aInfo.getClass());
            aInfo =(TransDiscountApplyInfo) findByID1(lApplyID);
            if (aInfo != null)
            {
                //生成转贴现合同
                //TransDiscountContractDelegation delegation = new TransDiscountContractDelegation();
                TransDiscountContractDAO cdao = new TransDiscountContractDAO(strContractTable);
                TransDiscountContractBillDAO cbdao = new TransDiscountContractBillDAO(strContractBillTable);
                
                TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
                //判断是否已经生成了贷款合同
                TransDiscountContractInfo cInfo1 =cdao.findByApplyCode(aInfo.getApplyCode(),aInfo.getOfficeId(),aInfo.getCurrencyId());
                
                
                cInfo.setLoanId(aInfo.getId()); 
                cInfo.setNSubtypeid(aInfo.getNSubtypeid());
                cInfo.setApplyCode(aInfo.getApplyCode());
                cInfo.setBankAcceptPO(aInfo.getBankAcceptPO());
                cInfo.setBizAcceptPO(aInfo.getBizAcceptPO());
                cInfo.setBorrowClientId(aInfo.getBorrowClientId());
                cInfo.setCheckAmount(aInfo.getCheckAmount());
                cInfo.setCurrencyId(aInfo.getCurrencyId());
                cInfo.setOfficeId(aInfo.getOfficeId());
                cInfo.setDiscountDate(aInfo.getDiscountDate());
                cInfo.setDiscountRate(aInfo.getDiscountRate());
                cInfo.setExamineAmount(aInfo.getExamineAmount());
                cInfo.setInputUserID(aInfo.getInputUserId());
                cInfo.setLoanAmount(aInfo.getLoanAmount());
                cInfo.setStartDate(aInfo.getStartDate());
                cInfo.setEndDate(aInfo.getEndDate());
                cInfo.setLoanReason(aInfo.getLoanReason());
                cInfo.setLoanPurpose(aInfo.getLoanPurpose());
                cInfo.setInOrOut(aInfo.getInOrOut());
                cInfo.setDiscountTypeId(aInfo.getDiscountTypeId());
                cInfo.setRepurchaseTypeId(aInfo.getRepurchaseTypeId());
                cInfo.setTypeId(aInfo.getTypeId());
                //cInfo.setEndDate(aInfo.getEndDate());
                cInfo.setDiscountDate(aInfo.getDiscountDate());
                cInfo.setNextCheckUserId(aInfo.getInputUserId());
                cInfo.setInterestTypeId(aInfo.getInterestTypeId());
                
                //lContractId=delegation.save(cInfo);
                
                //将下一个审批级别设为1
                cInfo.setNextCheckLevel(1);
                cInfo.setStatusId(LOANConstant.ContractStatus.SAVE);
                //info.setApplyCode(dao.createApplyCode());
                cInfo.setContractCode(cdao.createContractCode(
                                        cInfo.getBorrowClientId(),
                                        cInfo.getOfficeId(),
                                        cInfo.getInOrOut()));
                Log.print("ContractCode:"+cInfo.getContractCode());
                cInfo.setInputDate(Date);
                //info.setStartDate(info.getDiscountDate());
                
                //===========intervalnum==========/
                //将这里放到合同算息
                //getIntervalNum(cInfo);
                
                //Log.print("cInfo:"+cInfo);
                /*
                if (Env.getProjectName().equals(Constant.ProjectName.CPF))//中油
                {
                    //用默认取SEQ_loan_contractform
                }
                else//国机、华能
                {
                    cdao.setUseMaxID();//取MAX_ID
                }
                //*/
                cdao.setUseMaxID();//取MAX_ID
                //得到最大的ID
                //cInfo.setId(this.getMaxContractID());
                
                //因为贷款合同在处理完票据时生成,
                if(cInfo1 != null && cInfo1.getId() > 0){
                	lContractId = cInfo1.getId();
                	cInfo.setId(lContractId);
                	cdao.update(cInfo);
                }else{
                	lContractId=cdao.add(cInfo);
                }

                Log.print("========lContractId============"+lContractId);
                
                if(lContractId > 0)
                {
                	TransDiscountContractContentDao transConconDao = new TransDiscountContractContentDao();
				 	transConconDao.saveZTXContractContent(lContractId);
                
//                    if (Env.getProjectName().equals(Constant.ProjectName.CPF))//中油
//                    {
//                    	
//                    }
//                    else//国机、华能
//                    { 
//                        ContractContentDao conconDao = new ContractContentDao();
//                        strFileName = conconDao.addZTX(lContractId);
//                        
//                        ContractContentInfo CCInfo = new ContractContentInfo();
//                        CCInfo.setParentID(lContractId);
//                        CCInfo.setContractID(lContractId);
//                        CCInfo.setContractTypeID(LOANConstant.ContractType.ZTX);
//                        CCInfo.setDocName(strFileName);
//                        conconDao.saveContractContent(CCInfo);
//                    }
                //}
                
                //生成转贴现合同票据
                Collection temp = null;
                if(aInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
                {
                    Log.print("===买入==生成转贴现合同票据===start===");
                    TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
                    temp=bdao.findBillByTransDiscountApplyID(aInfo.getId());
                    
                    if( (temp != null) && (temp.size() > 0) )
                    {
                        Iterator it = temp.iterator();
                        while (it.hasNext() )
                        {
                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                            
                            TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
                            
                            if(abinfo != null)
                            {
								cbinfo.setCurrencyId(abinfo.getCurrencyId());
								cbinfo.setOfficeId(abinfo.getOfficeId());
                                cbinfo.setContractID(lContractId);
                                cbinfo.setAcceptPoTypeID(abinfo.getAcceptPoTypeID());
                                cbinfo.setAddDays(abinfo.getAddDays());
                                cbinfo.setAmount(abinfo.getAmount());
                                cbinfo.setBank(abinfo.getBank());
                                cbinfo.setCheckAmount(abinfo.getCheckAmount());
                                cbinfo.setCode(abinfo.getCode());
                                cbinfo.setCreate(abinfo.getCreate());
                                cbinfo.setEnd(abinfo.getEnd());
                                cbinfo.setDiscountCredenceID(abinfo.getDiscountCredenceID());
                                cbinfo.setFormerOwner(abinfo.getFormerOwner());
                                cbinfo.setIsLocal(abinfo.getIsLocal());
                                cbinfo.setOB_nDiscountCredenceID(abinfo.getOB_nDiscountCredenceID());
                                cbinfo.setSerialNo(abinfo.getSerialNo());
                                cbinfo.setStatusID(abinfo.getStatusID());
                                cbinfo.setUserName(abinfo.getUserName());
                                cbinfo.setSellStatusID(Constant.YesOrNo.NO);
                                cbinfo.setBillSourceTypeID(LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
                                cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
                                cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
                                /*
                                cbinfo.setIsLocal(abinfo.getIsLocal());
                                cbinfo.setAddDays(abinfo.getAddDays());
                                cbinfo.setCheckAmount(abinfo.getCheckAmount());
                                cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
                                cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
                                //*/
                                //cbinfo(abinfo());
                                
                                //delegation.saveBill(cbinfo);
                                    
                                //设置下一个序列号
                                cbinfo.setSerialNo(cbdao.findMaxTransDiscountContractBillSerialNo(cbinfo.getContractID())+1);
                
                                cbdao.setUseMaxID();//取MAX_ID

                                Log.print("===买入==复制票据===start===");
                                    
                                long id =cbdao.add(cbinfo);
                                    
                                cbinfo.setId(id);
                                    
                                Log.print("===买入==复制票据===end===");

                                Log.print("===买入==复制票据关系===start===");
                                cdao.addBillRelation(cbinfo);
                                Log.print("===买入==复制票据关系===end===");
                                
                            }
                        }
                        
                    }
                    Log.print("===买入==生成转贴现合同票据===end===");
                }
                else if(aInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
                {
                    Log.print("===买出==生成转贴现合同票据关系===start===");
                    temp=this.findOutBillByTransDiscountApplyID(aInfo.getId());
                    if( (temp != null) && (temp.size() > 0) )
                    {
                        Iterator it = temp.iterator();
                        while (it.hasNext() )
                        {
                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                            
                            TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
                            
                            if(abinfo != null)
                            {
                                /*
                                cbinfo.setId(abinfo.getId());
                                cbinfo.setBillSourceTypeID(LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
                                //cbinfo(abinfo());
                                
                                //delegation.saveBill(cbinfo);
                                cbdao.update(cbinfo);
                                //*/

                                cbinfo.setId(abinfo.getId());
                                cbinfo.setContractID(lContractId);
                                cbinfo.setIsLocal(abinfo.getIsLocal());
                                cbinfo.setAddDays(abinfo.getAddDays());
                                cbinfo.setCheckAmount(abinfo.getCheckAmount());
                                cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
                                cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
                                
                                Log.print("===买出==生成票据关系===start===");
                                cdao.addBillRelation(cbinfo);
                                Log.print("===买出==生成票据关系===end===");
                                
                            }
                        }
                    } 
                    Log.print("===买出==生成转贴现合同票据关系===end===");
                }
              }
            }
        }
        catch (ITreasuryDAOException e)
        {
            
            e.printStackTrace();
        }
        catch (LoanException e)
        {
            
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            
            e.printStackTrace();
        }
        catch (Exception e)
        {
            
            e.printStackTrace();
        }
    }
    /**
     * 获得该申请书的当前审批级别
     * @param applyId
     * @return
     * @throws SecuritiesDAOException
     */
    private long getNextCheckLevelByApplyID(long applyId)
        throws LoanDAOException
    {
        long nextCheckLevel = -1;
        String strSQL = "";
        //strSQL = " select nNextCheckLevel from loan_loanform where 1 = 1 ";
        strSQL = " select nNextCheckLevel from "+this.strTableName+" where 1 = 1 ";
        strSQL += " and id = " + applyId;
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
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        return nextCheckLevel;
    }
    /**
     *票据选择操作
    */
    public void saveTransDiscountApplyBill(SelectBillInfo selectBillInfo)
        throws java.rmi.RemoteException, LoanException
    {
        long lTransDiscountApplyID = selectBillInfo.getTransDiscountApplyID();
        long[] lBillIDList = selectBillInfo.getAllBillID();
        long[] isLocal = selectBillInfo.getIsLocal();//是否本地
        long[] addDays = selectBillInfo.getAddDays();//节假日增加天数
        Timestamp[] repurchaseDate = selectBillInfo.getRepurchaseDate();// 回购日期
        long[] repurchaseTerm = selectBillInfo.getRepurchaseTerm();// 回购期限
        ResultSet rs = null;
        long lIsSubmit = selectBillInfo.getIsSubmit();
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
                String strSQL = "";
                String strListID = "";
                
                //strListID = "(";
                for (int i = 0; i < lBillIDList.length; i++)
                {
                    if (lBillIDList[i] > 0)
                    {
                        if(i==0)
                        {
                            strListID += ""+lBillIDList[i];
                        }
                        else
                        {
                            strListID += ","+lBillIDList[i];
                        }
                    }
                }
                //strListID += ")";
                if (lTransDiscountApplyID > 0)
                {
                    //如果是提交申请，更新选择的合同票据
                    if(lIsSubmit == Constant.YesOrNo.YES)
                    {//* 
                        ///////////把申请下不可卖出合同票据改为可卖出////////////
                        log4j.info("更新申请下提交所有不可卖出合同票据改为可卖出");
                        strSQL =
                            " update loan_discountcontractbill "
                                + " set nSellStatusId = " + Constant.YesOrNo.YES
                                + " where 1=1  "
                                + " and id in ( "
                                + " select DiscountContractBillID "
                                + " from RTransDiscountApplyBill "
                                + " where TransDiscountApplyID = "
                                + lTransDiscountApplyID
                                + " and DiscountContractBillID not in ("+ strListID + " ) "
                                + " ) ";
                        log4j.debug(strSQL);
                        prepareStatement(strSQL);
                        executeUpdate();
                        /*
                        log4j.info("更新申请下提交选中合同票据改为不可卖出");
                        strSQL =
                            " update loan_discountcontractbill "
                                + " set nSellStatusId = " + Constant.YesOrNo.NO
                                + " where 1=1  "
                                + " and id in ( "
                                + strListID
                                + " ) ";
                        log4j.debug(strSQL);
                        prepareStatement(strSQL);
                        executeUpdate();
                        //*/
                        //删除贴现申请下所有票据关系
                        log4j.info("删除贴现申请下提交没有选中票据关系");
                        strSQL =
                            " delete RTransDiscountApplyBill "
                                + " where 1=1  "
                                + " and TransDiscountApplyID = "
                                + lTransDiscountApplyID
                                + " and DiscountContractBillID not in ("
                                + strListID
                                + " ) ";
                        log4j.debug(strSQL);
                        prepareStatement(strSQL);
                        executeUpdate();
                        //*/
                    }

                    if(lIsSubmit == Constant.YesOrNo.NO)
                    {
                        for (int i = 0; i < lBillIDList.length; i++)
                        {
                            if (lBillIDList[i] > 0)
                            {
                                log4j.info("更新转贴现申请卖出票据关系");
                                strSQL =
                                    " insert into RTransDiscountApplyBill "
                                        + " ( TransDiscountApplyID  "
                                        + " , DiscountContractBillID  ";
                                if(isLocal[i] > 0 ) 
                                {
                                    strSQL += " , isLocal ";
                                }
                                if(addDays[i] > 0 ) 
                                {
                                    strSQL += " , addDays ";
                                }
                                if(repurchaseTerm[i] > 0 ) 
                                {
                                    strSQL += " , repurchaseTerm ";
                                }
                                if(repurchaseDate[i] != null ) 
                                {
                                    strSQL += " , repurchaseDate ";
                                }
                                strSQL += " ) "
                                        + " values ( "
                                        + lTransDiscountApplyID
                                        + "," + lBillIDList[i];
                                if(isLocal[i] > 0 ) 
                                {
                                    strSQL += "," + isLocal[i];
                                }
                                if(addDays[i] > 0 ) 
                                {
                                    strSQL += "," + addDays[i];
                                }
                                if(repurchaseTerm[i] > 0 ) 
                                {
                                    strSQL += "," + repurchaseTerm[i];
                                }
                                if(repurchaseDate[i] != null ) 
                                {
                                    strSQL += ", To_Date('" + DataFormat.getDateString(repurchaseDate[i]) + "','yyyy-mm-dd')";
                                }
                                strSQL += " ) ";
                                log4j.debug(strSQL);
                                prepareStatement(strSQL);
                                executeUpdate();
                            
                                ///////////把合同票据改为不可卖出////////////
                                log4j.info("更新合同票据改为不可卖出");
                                strSQL =
                                    " update  loan_discountcontractbill  "
                                        + " set nSellStatusId = " + Constant.YesOrNo.NO
                                        + " where id = "
                                        + lBillIDList[i]
                                        + "  ";
                                log4j.debug(strSQL);
                                prepareStatement(strSQL);
                                executeUpdate();
                            }
                        }
                    }
                }
            }
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
                //throw e;
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
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
    }
    /**
     *卖出申请书下的已选票据 查询操作
    */
    public Collection findOutBillByTransDiscountApplyID(long lTransDiscountApplyID)
        throws java.rmi.RemoteException, LoanException
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
                        + " from RTransDiscountApplyBill r "
                        + "     ,Loan_DiscountContractBill b "
                        + " where 1 = 1 "
                        + " and r.TransDiscountApplyID = "
                        + lTransDiscountApplyID
                        + " and r.DiscountContractBillID = b.id "
                        + " and b.nStatusId = " + Constant.RecordStatus.VALID
                        + " and b.nSellStatusId = " + Constant.YesOrNo.NO
                        + "";
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
                        + " from RTransDiscountApplyBill r "
                        + "     ,Loan_DiscountContractBill b "
                        + " where 1 = 1 "
                        + " and TransDiscountApplyID = "
                        + lTransDiscountApplyID
                        + " and r.DiscountContractBillID = b.id"
                        + " and b.nStatusId = " + Constant.RecordStatus.VALID
                        + " and b.nSellStatusId = " + Constant.YesOrNo.NO
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
                    info.setOfficeId(rs.getLong("nofficeid"));
                    info.setCurrencyId(rs.getLong("ncurrencyid"));
                    
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
                    info.setPayee(rs.getString("payee"));
                    //转贴现类型(买断/回购)
                    //info.setRepurchaseTypeID(rs.getLong("ndiscounttypeid"));
                    
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
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        return v.size() > 0 ? v : null;
    }
    
    /**
     *申请书下的票据删除操作
    */
    public void deleteOutBill(SelectBillInfo selectBillInfo)
        throws java.rmi.RemoteException, LoanException
    {
    	System.out.println("进入测试方法");
        long[] lIDList = selectBillInfo.getAllBillID();
        long lTransDiscountApplyID = selectBillInfo.getTransDiscountApplyID();
        long lIsDelete = selectBillInfo.getIsSubmit();
        ResultSet rs = null;
        String strSQL = null;
        long lMaxID = -1;
        int i = 0;
        long lBillID = -1;
        long nStatusID = -1;
        long nSerialNo = -1;
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
                for (i = 0; i < lIDList.length; i++)
                {
                    if (lIDList[i] > 0)
                    {
                        if(lIsDelete==Constant.YesOrNo.YES)//删除
                        {
                            //删除贴现申请下所有合同票据关系
                            strSQL =
                                " delete RTransDiscountApplyBill "
                                    + " where 1=1  "
                                    + " and TransDiscountApplyID = "
                                    + lTransDiscountApplyID
                                    + " and DiscountContractBillID = "
                                    + lIDList[i]
                                    + "  ";
                            log4j.debug(strSQL);
                            prepareStatement(strSQL);
                            executeUpdate();
                        }
                        //*/
                        ///////////把申请下不可卖出合同票据改为可卖出////////////
                        log4j.info("更新合同票据改为可卖出");
                        strSQL =
                            " update loan_discountcontractbill "
                                + " set nSellStatusId = " + Constant.YesOrNo.YES
                                + " where 1=1  "
                                + " and id = "
                                + lIDList[i]
                                + " ";
                        log4j.debug(strSQL);
                        prepareStatement(strSQL);
                        executeUpdate();
                    }
                }
            }
            catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
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
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
    }
    /**
     *申请书下的票据删除操作
     *by gangwang
    */
    public void deleteAllOutBill(SelectBillInfo selectBillInfo)
        throws java.rmi.RemoteException, LoanException
    {
    	System.out.println("进入测试方法");
        long[] lIDList = selectBillInfo.getAllBillID();
        long lTransDiscountApplyID = selectBillInfo.getTransDiscountApplyID();
        long lIsDelete = selectBillInfo.getIsSubmit();
        ResultSet rs = null;
        String strSQL = null;
        long lMaxID = -1;
        int i = 0;
        long lBillID = -1;
        long nStatusID = -1;
        long nSerialNo = -1;
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
                for (i = 0; i < lIDList.length; i++)
                {
                    if (lIDList[i] > 0)
                    {
                        if(lIsDelete==Constant.YesOrNo.YES)//删除
                        {
                            //删除贴现申请下所有合同票据关系
                            strSQL =
                                " delete RTransDiscountApplyBill "
                                    + " where 1=1  "
                                    + " and TransDiscountApplyID = "
                                    + lTransDiscountApplyID
                                    + " and DiscountContractBillID = "
                                    + lIDList[i]
                                    + "  ";
                            log4j.debug(strSQL);
                            prepareStatement(strSQL);
                            executeUpdate();
                        }
                        //*/
                        ///////////把申请下不可卖出合同票据改为可卖出////////////
                        log4j.info("更新合同票据改为可卖出");
                        strSQL =
                            " update loan_discountcontractbill "
                                + " set nSellStatusId = " + Constant.YesOrNo.YES
                                + " where 1=1  "
                                + " and id = "
                                + lIDList[i]
                                + " ";
                        log4j.debug(strSQL);
                        prepareStatement(strSQL);
                        executeUpdate();
                        //Modified  By qhzhou 2008-01-24
//                        TransDiscountApplyDAO tdDAO = new TransDiscountApplyDAO("cra_LoanForm");
//                        TransDiscountApplyInfo tdinfo = new TransDiscountApplyInfo();
//                        tdinfo =
//                            (TransDiscountApplyInfo) tdDAO.findByID(
//                                lTransDiscountApplyID,
//                                tdinfo.getClass());
//                        nStatusID = tdinfo.getStatusId();
//                        // TODO 
//                        //if (nStatusID == Notes.CODE_DISCOUNT_STATUS_EXAMINE)
//                      //  if (nStatusID == LOANConstant.LoanStatus.CHECK)
//                       // {
//                            //更新申请记录状态
//                            tdinfo.setId(lTransDiscountApplyID);
//                            tdinfo.setBillStatus(BILLConstant.TransctionStatus.OTHER);
//                           // tdinfo.setStatusId(LOANConstant.LoanStatus.SUBMIT);
//                            tdDAO.update(tdinfo);
//                            //删除以前的审核纪录
//                       // }
                    }
                }
                TransDiscountApplyDAO tdDAO = new TransDiscountApplyDAO("cra_LoanForm");
                TransDiscountApplyInfo tdinfo = new TransDiscountApplyInfo();
                tdinfo =
                    (TransDiscountApplyInfo) tdDAO.findByID(
                        lTransDiscountApplyID,
                        tdinfo.getClass());
                nStatusID = tdinfo.getStatusId();
                tdinfo.setId(lTransDiscountApplyID);
                tdinfo.setBillStatus(BILLConstant.TransctionStatus.OTHER);
                tdDAO.update(tdinfo);
            }
            catch (ITreasuryDAOException e)
            {
                throw new LoanDAOException(e);
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
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
    }
    /**
    * 计算转贴现申请下的贴现票据利息，
    * 操作Loan_DiscountFormBill表
    * @param lDiscountApplyID 贴现标识
    * @param  dRate        贴现利率
    * @param  tsDate       贴现日
    * @return 返回贴现票据的列表
    */
    public Collection calOutBillInterest(TransDiscountApplyQueryInfo qInfo)
    throws java.rmi.RemoteException, LoanException//, IException
    {
        Timestamp tsDate = qInfo.getCountDiscountDate(); //转贴现日 
        long lDiscountApplyID = qInfo.getTransDiscountApplyId();//转贴现标识
        double dRate = qInfo.getTransDiscountRate(); //转贴现利率

        ResultSet rs = null;
        ResultSet rs1 = null;
        
        String strSelect = null;
        String strSQL = null;
        String strOrder = null;
        String strUpdate = null;

        Vector v = new Vector();
        
        long lPageLineCount=qInfo.getPageLineCount();
        long lPageNo=qInfo.getPageNo();
        long lDesc=qInfo.getDesc();
        String orderParamString = qInfo.getOrderParamString();
        long lCountType=qInfo.getTransDiscountCountType();
        long lInOrOut=qInfo.getInOrOut();
        long lDiscountTypeId=qInfo.getDiscountTypeId();
        long lReadOnly=qInfo.getReadOnly();
        
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;

        Timestamp tsEnd = null; //转贴现日期
        String strEnd = ""; //转贴现日期
        int nDays = 0; //实际转贴现天数
        double dAmount = 0; //票据金额
        double dAccrual = 0; //转贴现利息
        double dRealAmount = 0; //实付贴现金额
        double dTotalAmount = 0; //总票据金额
        double dTotalAccrual = 0; //总票据利息
        double dTotalRealAmount = 0; //总票据实付金额

        try
        {
            initDAO();

            Log.print("======进入转贴现计息(calculateTransDiscountBillInterest)方法======");

            Log.print("转贴现标示：" + lDiscountApplyID);
            Log.print("转贴现利率：" + dRate);
            Log.print("转贴现日  ：" + tsDate);
            
            if(lInOrOut==LOANConstant.TransDiscountInOrOut.OUT)
            {
                Log.print("=====转贴现卖出计息=====");

                if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
                {
                    Log.print("==卖出、回购型不校验贴现日==");
                }
                else
                {
                    Log.print("==卖出、买断型 校验贴现日==");
                    Log.print("==开始校验贴现日==");

                    //贴现日输入校验，应该小于所有票据到期日,大于贴现开始日期
                    strSQL = " select count(*) from loan_discountcontractbill "
                        + " where 1=1  "
                        + " and id in ( "
                        + " select DiscountContractBillID "
                        + " from RTransDiscountApplyBill "
                        + " where TransDiscountApplyID = "
                        + lDiscountApplyID
                        + " ) "
                        + " and nSellStatusId = " + Constant.YesOrNo.NO
                        + " and nStatusID=" + Constant.RecordStatus.VALID;
                    
                    strSQL += " and to_char(dtEnd,'yyyy-mm-dd') < '" + DataFormat.getDateString(tsDate) + "'";

                    Log.print(strSQL);
                    prepareStatement(strSQL);
                    rs = executeQuery();
                
                    if (rs != null && rs.next())
                    {
                        lRecordCount = rs.getLong(1);
                    }
                    if (lRecordCount > 0)
                    {
                        //抛出异常  贴现日应该小于所有票据到期日
                    	System.out.println("我的数据");
                        throw new LoanException("Loan_E006",null);
                        //throw new IException("Loan_E006");
                    }

                    if (rs != null)
                    {
                        rs.close();
                        rs = null;
                    }

                    Log.print("======贴现日校验结束======");

                }                
                Log.print("======开始查询所有票据信息======");

                strSelect = " select a.*,r.repurchaseTerm rterm,r.isLocal,r.adddays,r.repurchasedate dtrepurchasedate ";
                strSQL = " from loan_discountcontractbill a " 
                    + " ,RTransDiscountApplyBill r "
                    + " where a.nStatusID=" + Constant.RecordStatus.VALID
                    + " and r.TransDiscountApplyID = " + lDiscountApplyID
                    + " and a.nSellStatusId = " + Constant.YesOrNo.NO
                    + " and a.id = r.DiscountContractBillID ";

                Log.print(strSelect + strSQL);
                prepareStatement(strSelect + strSQL);
                rs = executeQuery();

                while (rs != null && rs.next())
                {
                    dAmount = rs.getDouble("mAmount"); //汇票金额

                    if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
                    {
                    	strEnd = rs.getTimestamp("dtrepurchasedate").toString();
                    }else{
                    	strEnd = rs.getTimestamp("dtEnd").toString();
                    }
                    tsEnd =
                        new java.sql.Timestamp(
                            new Integer(strEnd.substring(0, 4)).intValue() - 1900,
                            new Integer(strEnd.substring(5, 7)).intValue() - 1,
                            new Integer(strEnd.substring(8, 10)).intValue(),
                            0,
                            0,
                            0,
                            0);
                            
                    if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
                    {
                       // nDays = (int) rs.getLong("rterm");//+ rs.getInt("AddDays"); //回购期限
                      // modify by xwhe 2007-12-8                  
                    	nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                - tsDate.getTime()) / 86400000) 
                                + rs.getInt("AddDays"); //加上节假日增加计息天数
                    	//非本地加三天
                    	//if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
                        //    nDays = nDays + 3;
                    }
                    else
                    {
                        nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                - tsDate.getTime()) / 86400000) 
                                + rs.getInt("AddDays"); //加上节假日增加计息天数
                    }
                    
                    Log.print("1 nDays=" + nDays);

                    if (nDays >= 0)
                    {
                        if(lDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
                        {
                            if (rs.getLong("isLocal") == Constant.YesOrNo.NO)
                                nDays = nDays + 3;
                        }
                            
                        Log.print("2 nDays=" + nDays);
                        
                        dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
                        
                        dRealAmount = dAmount - dAccrual;
                    }
                    if(lCountType == LOANConstant.CountInterestType.CalAfterRounding)//先舍后累加计算
                    { 
                        Log.print("=========先舍后累加计算========");
                        dAccrual = DataFormat.formatDouble(dAccrual, 2);
                        dRealAmount = DataFormat.formatDouble(dRealAmount, 2);
                    }
                    Log.print("========================");
                    Log.print("汇票金额=" + dAmount);
                    Log.print("汇票利息=" + dAccrual);
                    Log.print("实付金额=" + dRealAmount);
                    Log.print("========================");
                    dTotalAccrual = dTotalAccrual + dAccrual;
                    dTotalRealAmount = dTotalRealAmount + dRealAmount;

                    dAmount = 0; //票据金额
                    dAccrual = 0; //转贴现利息
                    dRealAmount = 0; //实付转贴现金额

                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }

                Log.print("======结束查询所有票据信息======");

                Log.print("======开始查询票据总数和总金额======");

                //计算记录总数
                strSelect = " select count(*),sum(nvl(a.mAmount,0)) ";
                strSQL = " from loan_discountcontractbill a "
                        + " ,RTransDiscountApplyBill r "
                        + " where a.nStatusID=" + Constant.RecordStatus.VALID
                        //+ " and a.nSellStatusId = " + Constant.YesOrNo.NO
                        + " and r.TransDiscountApplyID = " + lDiscountApplyID
                        + " and a.id = r.DiscountContractBillID ";

                Log.print(strSelect + strSQL);
                prepareStatement(strSelect + strSQL);
                rs = executeQuery();

                if (rs != null && rs.next())
                {
                    lRecordCount = rs.getLong(1);
                    dTotalAmount = rs.getDouble(2);
                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }

                if(lCountType == LOANConstant.CountInterestType.CalBeforRounding)//先累加后舍计算
                { 
                    Log.print("=========先累加后舍计算========");
                    dTotalAccrual = DataFormat.formatDouble(dTotalAccrual, 2);
                    dTotalRealAmount = DataFormat.formatDouble(dTotalRealAmount, 2);
                }
                Log.print("==============");
                Log.print("票据总张数=" + lRecordCount);
                Log.print("票据总金额=" + dTotalAmount);
                Log.print("票据总利息=" + dTotalAccrual);
                Log.print("总实付金额=" + dTotalRealAmount);
                Log.print("==============");

                Log.print("======结束查询票据总数和总金额======");

                lPageCount = lRecordCount / lPageLineCount;

                if ((lRecordCount % lPageLineCount) != 0)
                {
                    lPageCount++;
                }
                //////////////////排序处理////////////
                int nIndex = 0;
                nIndex = orderParamString.indexOf(".");
                if (nIndex > 0)
                {
                    if (orderParamString
                        .substring(0, nIndex)
                        .toLowerCase()
                        .equals("loan_discountcontractbill"))
                    {
                        strOrder=" \n order by a." + orderParamString.substring(nIndex + 1);
                    }
                }
                else
                {
                    strOrder=" \n order by a.nSerialNo ";
                }

                if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
                {
                    strOrder += " desc ";
                }

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //返回需求的结果集
                lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
                lRowNumEnd = lRowNumStart + lPageLineCount - 1;

                strSQL = "select a.*,r.repurchaseTerm rterm,r.repurchaseDate rdate,r.IsLocal,r.AddDays,r.transdiscountapplyid loanId " + strSQL;
                strSQL = " select a.*, rownum r from " + " ( " + strSQL + strOrder + " ) a ";
                strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

                Log.print("翻页查询开始");
                Log.print(strSQL);
                prepareStatement(strSQL);
                rs = executeQuery();

                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("Loan_DiscountContractBill");
                TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
                
                
                while (rs != null && rs.next())
                {
                    TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();
                    
                    info.setId(rs.getLong("id"));
                    info.setSerialNo(rs.getLong("nSerialNo"));
                    info.setUserName(rs.getString("sUserName"));
                    info.setBank(rs.getString("sBank"));
                    info.setCreate(rs.getTimestamp("dtCreate"));
                    info.setEnd(rs.getTimestamp("dtEnd"));
                    info.setCode(rs.getString("sCode"));
                    info.setAmount(rs.getDouble("mAmount"));
                    info.setStatusID(rs.getLong("nStatusID"));

                    info.setLoanID(rs.getLong("loanId"));
                    info.setIsLocal(rs.getLong("IsLocal"));
                    info.setAddDays(rs.getLong("AddDays"));
                    info.setRepurchaseDate(rs.getTimestamp("rdate"));
                    info.setRepurchaseTerm(rs.getLong("rterm"));
                    
                    info.setDiscountCredenceID(
                        rs.getLong("nDiscountCredenceID"));
                    info.setOB_nDiscountCredenceID(
                        rs.getLong("OB_nDiscountCredenceID"));
                    info.setAcceptPoTypeID(rs.getLong("nAcceptPoTypeID"));
                    info.setFormerOwner(rs.getString("sFormerOwner"));
                    //info.setCheckAmount(rs.getDouble("mCheckAmount"));
                
                    //info= (TransDiscountApplyBillInfo) bdao.findByID(rs.getLong("Id"));//,info.getClass());
                
                    //计算票据利息
                    dAmount = info.getAmount(); //汇票金额
                    if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
                    {
                    	strEnd = DataFormat.getDateString(info.getRepurchaseDate());
                    }else{
                    	strEnd = DataFormat.getDateString(info.getEnd());
                    }
                    tsEnd =
                        new java.sql.Timestamp(
                            new Integer(strEnd.substring(0, 4)).intValue() - 1900,
                            new Integer(strEnd.substring(5, 7)).intValue() - 1,
                            new Integer(strEnd.substring(8, 10)).intValue(),
                            0,
                            0,
                            0,
                            0);
                            
                    if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
                    {
                     //   nDays = (int) info.getRepurchaseTerm();
                    	 nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                 - tsDate.getTime()) / 86400000) 
                                 + rs.getInt("AddDays"); //加上节假日增加计息天数
                  //  非本地加三天
                     //	if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
                    //         nDays = nDays + 3;
                            //+ rs.getInt("AddDays"); //回购期限
                    }
                    else
                    {
                        nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                - tsDate.getTime()) / 86400000) 
                                + rs.getInt("AddDays"); //加上节假日增加计息天数
                    }
                            
                    Log.print("nDays=" + nDays);

                    //nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDate.getTime()) / 86400000) + (int)info.getAddDays();// rs.getInt("nAddDays"); //加上节假日增加计息天数
                    if (nDays >= 0)
                    {
                        if(lDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
                        {
                            if (info.getIsLocal() == Constant.YesOrNo.NO)
                                nDays = nDays + 3;
                        }
                            
                        Log.print("nDays=" + nDays);
                        
                        dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
                       
                        dRealAmount = dAmount - dAccrual;
                    }
                    if(lCountType == LOANConstant.CountInterestType.CalAfterRounding)//先舍后累加计算
                    { 
                        Log.print("=======先舍后累加计算=========");
                        dAccrual = DataFormat.formatDouble(dAccrual, 2);
                        dRealAmount = DataFormat.formatDouble(dRealAmount, 2);
                    }
                    Log.print("========================");
                    Log.print("汇票金额=" + dAmount);
                    Log.print("汇票利息=" + dAccrual);
                    Log.print("实付金额=" + dRealAmount);
                    Log.print("========================");
                    
                    //info.setCountDays(nDays);
                    //info.setAccrual(DataFormat.formatDouble(dAccrual, 2));
                    //info.setRealAmount(DataFormat.formatDouble(dRealAmount, 2));

                    info.setCountDays(nDays);//计息天数
                    info.setAccrual(dAccrual);//利息
                    info.setRealAmount(dRealAmount);//实付金额
                    info.setCheckAmount(dRealAmount);
                    
                    info.setRecordCount(lRecordCount);//总记录数
                    info.setTotalBillAmount(dTotalAmount);//总票面金额
                    info.setTotalAccrual(dTotalAccrual);//总利息
                    info.setTotalRealAmount(dTotalAmount - dTotalAccrual);//总实付金额
                
                    dao.saveOutBill(info);
                
                    v.add(info);
                
                }
                Log.print("翻页查询结束");
            
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                //卖出计息结束
                if(lDiscountApplyID > 0 && lReadOnly != Constant.YesOrNo.YES)
                {
                    //更新转贴现申请的贴现日、贴现利率、贴现实付金额
                    TransDiscountApplyDAO adao = new TransDiscountApplyDAO(this.strTableName);
                
                    TransDiscountApplyInfo ainfo = new TransDiscountApplyInfo();
                    ainfo.setId(lDiscountApplyID);
                    ainfo.setDiscountRate(dRate);
                    ainfo.setDiscountDate(tsDate);
                    ainfo.setInterestTypeId(lCountType);
                    ainfo.setCheckAmount(dTotalRealAmount);
                    ainfo.setExamineAmount(dTotalAmount);
                    
                    this.update(ainfo);
                }
            }
            
            finalizeDAO();
            
        }
        /*
        catch (IException ie)
        {
            Log.print("异常退出贴现计息");
            log4j.error(ie.getMessage());
            throw ie;
            //throw new LoanException("Loan_E006",ie);
        }//*/
        catch (LoanException ie)
        {
            Log.print("异常退出贴现计息");
            log4j.error(ie.getMessage());
            throw new LoanException("Loan_E006",null);
            //throw ie;
        }
        catch (Exception e)
        {
            Log.print("异常退出贴现计息");
            log4j.error(e.toString());
            //throw new LoanException("Loan_E006",e);
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        Log.print("======退出贴现计息(calculateTransDiscountBillInterest)方法======");

        return (v.size() > 0 ? v : null);

    }
    /**
    * 计算转贴现申请下的贴现票据利息，
    * 操作Loan_DiscountFormBill表
    * @param lDiscountApplyID 贴现标识
    * @param  dRate        贴现利率
    * @param  tsDate       贴现日
    * @return 返回贴现票据的列表
    */
    public Collection calInBillInterest(TransDiscountApplyQueryInfo qInfo)
    throws java.rmi.RemoteException, LoanException//, IException
    {
        Timestamp tsDate = qInfo.getCountDiscountDate(); //转贴现日 
        long lDiscountApplyID = qInfo.getTransDiscountApplyId();//转贴现标识
        double dRate = qInfo.getTransDiscountRate(); //转贴现利率

        ResultSet rs = null;
        ResultSet rs1 = null;
        
        String strSelect = null;
        String strSQL = null;
        String strOrder = null;
        String strUpdate = null;

        Vector v = new Vector();
        
        long lPageLineCount=qInfo.getPageLineCount();
        long lPageNo=qInfo.getPageNo();
        long lDesc=qInfo.getDesc();
        String orderParamString = qInfo.getOrderParamString();
        long lCountType=qInfo.getTransDiscountCountType();
        long lInOrOut=qInfo.getInOrOut();
        long lDiscountTypeId=qInfo.getDiscountTypeId();
        //long lInterestTypeId=qInfo.getReadOnly();
        long lReadOnly=qInfo.getReadOnly();
        
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;

        Timestamp tsEnd = null; //转贴现日期
        String strEnd = ""; //转贴现日期
        int nDays = 0; //实际转贴现天数
        double dAmount = 0; //票据金额
        double dAccrual = 0; //转贴现利息
        double dRealAmount = 0; //实付贴现金额
        double dTotalAmount = 0; //总票据金额
        double dTotalAccrual = 0; //总票据利息
        double dTotalRealAmount = 0; //总票据实付金额

        try
        {
            initDAO();

            Log.print("======进入转贴现计息(calculateTransDiscountBillInterest)方法======");

            Log.print("转贴现标示：" + lDiscountApplyID);
            Log.print("转贴现利率：" + dRate);
            Log.print("转贴现日  ：" + tsDate);
            
            if(lInOrOut == LOANConstant.TransDiscountInOrOut.IN)
            {
                Log.print("=====转贴现买入计息=====");

                if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
                {
                    Log.print("==卖入、回购型不校验贴现日==");
                }
                else
                {
                    Log.print("==开始校验贴现日，贴现日应该小于所有票据到期日==");

                    //贴现日输入校验，应该小于所有票据到期日,大于贴现开始日期
                    strSQL = " select count(*) from cra_transdiscountbill " +
                        " where nStatusID=" + Constant.RecordStatus.VALID 
                        + " and nLoanID=" + lDiscountApplyID;
                    strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') < '" + DataFormat.getDateString(tsDate) + "'";

                    Log.print(strSQL);
                    prepareStatement(strSQL);
                    rs = executeQuery();

                    if (rs != null && rs.next())
                    {
                        lRecordCount = rs.getLong(1);
                    }
                    if (lRecordCount > 0)
                    {
                        //抛出异常  贴现日应该小于所有票据到期日
                    	System.out.println("我的数据");
                        throw new LoanException("Loan_E006",null);
                        //throw new IException("Loan_E006");
                    }

                    if (rs != null)
                    {
                        rs.close();
                        rs = null;
                    }

                    Log.print("======贴现日校验结束======");
                }
                
                Log.print("======开始查询所有票据信息======");

                strSelect = " select * ";
                strSQL = " from cra_transdiscountbill " +
                    " where nStatusID=" + Constant.RecordStatus.VALID + 
                    " and nLoanID=" + lDiscountApplyID;

                Log.print(strSelect + strSQL);
                prepareStatement(strSelect + strSQL);
                rs = executeQuery();

                while (rs != null && rs.next())
                {
                    dAmount = rs.getDouble("mAmount"); //汇票金额
                    if(lInOrOut == LOANConstant.TransDiscountInOrOut.IN
                    		&&lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)
                    {
                        //转贴现回购业务，算利息天数=回购日期-转贴现日期,而非到期日期-转贴现日期
                    	strEnd = rs.getTimestamp("dtrepurchasedate").toString();
                    }else{
                    	strEnd = rs.getTimestamp("dtEnd").toString();
                    }
                    tsEnd =
                        new java.sql.Timestamp(
                            new Integer(strEnd.substring(0, 4)).intValue() - 1900,
                            new Integer(strEnd.substring(5, 7)).intValue() - 1,
                            new Integer(strEnd.substring(8, 10)).intValue(),
                            0,
                            0,
                            0,
                            0);
                            
                    if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)
                    {
                        //nDays = (int) rs.getLong("nrepurchaseterm"); //回购期限
                        //modify by xwhe 2007-12-8                  
                    	nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                - tsDate.getTime()) / 86400000) 
                                + rs.getInt("nAddDays"); //加上节假日增加计息天数
                    	//回购的不加三天  modify by wwangzhen 2012-02-27 
                    	//非本地加三天
                    //	if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
                    //        nDays = nDays + 3;
                    }
                    else
                    {
                        nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                - tsDate.getTime()) / 86400000) 
                                + rs.getInt("nAddDays"); //加上节假日增加计息天数
                    }
                    
                    Log.print("1 day="+nDays);

                    if (nDays >= 0)
                    {
                        if(lDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
                        {
                            if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
                                nDays = nDays + 3;
                        }
                        //if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) nDays = nDays + 3;
                    
                        Log.print("2 day="+nDays);
                        
                        dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
                        
                        dRealAmount = dAmount - dAccrual;
                        
                    }
                    Log.print("========================");
                    Log.print("汇票金额=" + dAmount);
                    Log.print("汇票利息=" + dAccrual);
                    Log.print("实付金额=" + dRealAmount);
                    Log.print("========================");
                    
                    if(lCountType == LOANConstant.CountInterestType.CalAfterRounding)//先舍后累加计算
                    { 
                        dAccrual = DataFormat.formatDouble(dAccrual, 2);
                        dRealAmount = DataFormat.formatDouble(dRealAmount, 2);
                    }
                    
                    dTotalAccrual = dTotalAccrual + dAccrual;
                    dTotalRealAmount = dTotalRealAmount + dRealAmount;

                    dAmount = 0; //票据金额
                    dAccrual = 0; //转贴现利息
                    dRealAmount = 0; //实付转贴现金额

                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }

                Log.print("======结束查询所有票据信息======");

                Log.print("======开始查询票据总数和总金额======");

                //计算记录总数
                strSelect = " select count(*),sum(nvl(aa.mAmount,0)) ";
                strSQL = " from cra_transdiscountbill aa " +
                        " where aa.nStatusID=" + Constant.RecordStatus.VALID 
                        + " and aa.nLoanID=" + lDiscountApplyID;

                Log.print(strSelect + strSQL);
                prepareStatement(strSelect + strSQL);
                rs = executeQuery();

                if (rs != null && rs.next())
                {
                    lRecordCount = rs.getLong(1);
                    dTotalAmount = rs.getDouble(2);
                }

                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if(lCountType == LOANConstant.CountInterestType.CalBeforRounding)//先舍后累加计算
                { 
                    dTotalAccrual = DataFormat.formatDouble(dTotalAccrual, 2);
                    dTotalRealAmount = DataFormat.formatDouble(dTotalRealAmount, 2);
                }
                Log.print("==============");
                Log.print("票据总张数=" + lRecordCount);
                Log.print("票据总金额=" + dTotalAmount);
                Log.print("票据总利息=" + dTotalAccrual);
                Log.print("总实付金额=" + dTotalRealAmount);
                Log.print("==============");

                Log.print("======结束查询票据总数和总金额======");

                lPageCount = lRecordCount / lPageLineCount;

                if ((lRecordCount % lPageLineCount) != 0)
                {
                    lPageCount++;
                }
                //////////////////排序处理////////////
                int nIndex = 0;
                nIndex = orderParamString.indexOf(".");
                if (nIndex > 0)
                {
                    if (orderParamString
                        .substring(0, nIndex)
                        .toLowerCase()
                        .equals("cra_transdiscountbill"))
                    {
                        strOrder=" \n order by aa." + orderParamString.substring(nIndex + 1);
                    }
                }
                else
                {
                    strOrder=" \n order by aa.nSerialNo ";
                }

                if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
                {
                    strOrder += " desc ";
                }

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //返回需求的结果集
                lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
                lRowNumEnd = lRowNumStart + lPageLineCount - 1;

                strSQL = "select * " + strSQL;
                strSQL = " select a.*, rownum r from " + " ( " + strSQL + strOrder + " ) a ";
                strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

                Log.print("翻页查询开始");
                Log.print(strSQL);
                prepareStatement(strSQL);
                rs = executeQuery();

                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
                
                while (rs != null && rs.next())
                {
                    TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();
                    
                
                    info= (TransDiscountApplyBillInfo) bdao.findByID(rs.getLong("Id"));//,info.getClass());
                
                    //计算票据利息
                    dAmount = info.getAmount(); //汇票金额
                    if(lInOrOut == LOANConstant.TransDiscountInOrOut.IN
                    		&&lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)
                    {
                        //转贴现回购业务，算利息天数=回购日期-转贴现日期,而非到期日期-转贴现日期
                    	strEnd = DataFormat.getDateString(info.getRepurchaseDate()); 
                    }else{
                    	strEnd = DataFormat.getDateString(info.getEnd());
                    }
                    tsEnd =
                        new java.sql.Timestamp(
                            new Integer(strEnd.substring(0, 4)).intValue() - 1900,
                            new Integer(strEnd.substring(5, 7)).intValue() - 1,
                            new Integer(strEnd.substring(8, 10)).intValue(),
                            0,
                            0,
                            0,
                            0);
                            
                    if(lDiscountTypeId == LOANConstant.TransDiscountType.REPURCHASE)
                    {
                     //   nDays = (int) info.getRepurchaseTerm(); //回购期限
                        nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                - tsDate.getTime()) / 86400000) 
                                + rs.getInt("nAddDays"); //加上节假日增加计息天数
//                  //回购的不加三天  modify by wwangzhen 2012-02-27 
                    //   非本地加三天
                    //	if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
                    //        nDays = nDays + 3;
                    }
                    else
                    {
                        nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
                                - tsDate.getTime()) / 86400000) 
                                + rs.getInt("nAddDays"); //加上节假日增加计息天数
                    }
                    
                    Log.print("1 day="+nDays);

                    //nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDate.getTime()) / 86400000) + (int)info.getAddDays();// rs.getInt("nAddDays"); //加上节假日增加计息天数
                    if (nDays >= 0)
                    {
                        if(lDiscountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
                        {
                            if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
                                nDays = nDays + 3;
                        }
                        //if (info.getIsLocal() == Constant.YesOrNo.NO) nDays = nDays + 3;
                            
                        Log.print("2 day="+nDays);
                        
                        dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
                        
                        dRealAmount = dAmount - dAccrual;
                    }
                    
                    if(lCountType == LOANConstant.CountInterestType.CalAfterRounding)//先舍后累加计算
                    { 
                        dAccrual = DataFormat.formatDouble(dAccrual, 2);
                        dRealAmount = DataFormat.formatDouble(dRealAmount, 2);
                    }
                    
                    Log.print("========================");
                    Log.print("汇票金额=" + dAmount);
                    Log.print("汇票利息=" + dAccrual);
                    Log.print("实付金额=" + dRealAmount);
                    Log.print("========================");
                    
                    info.setCountDays(nDays);
                    info.setAccrual(dAccrual);
                    info.setRealAmount(dRealAmount);
                    info.setCheckAmount(dRealAmount);
                    
                    info.setRecordCount(lRecordCount);//总记录数
                    info.setTotalBillAmount(dTotalAmount);//总票面金额
                    info.setTotalAccrual(dTotalAccrual);//总利息
                    info.setTotalRealAmount(dTotalAmount - dTotalAccrual);//总实付金额
                    
                    if(lReadOnly != Constant.YesOrNo.YES)
                    {
                        TransDiscountApplyBillInfo billInfo = new TransDiscountApplyBillInfo();                 
                        billInfo.setId(info.getId());
                        billInfo.setCheckAmount(dRealAmount);                 
                        bdao.update(billInfo);                         
                        //modify by xwhe 思路:由BILLid 查找出合同ID ,再根据billID 和合同ID修改RTRANSDISCOUNTCONTRACTBILL表中的字段
                        if(info.getId()>0){
                        	long loanID=-1;
                        	String strContractTable = "Loan_ContractForm";
                        	TransDiscountContractDAO cdao = new TransDiscountContractDAO(strContractTable);
                        	TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
                        	TransDiscountApplyBillInfo info1 = new TransDiscountApplyBillInfo();
                        	long discountcontractBillID = new TransDiscountContractDAO("RTRANSDISCOUNTCONTRACTBILL").findByBillID(info.getId());
                        	info1 = bdao.findByID(info.getId());
                        	loanID =  info1.getLoanID();
                        	cInfo = cdao.findContractByID(loanID);
                        	new TransDiscountContractDAO("RTRANSDISCOUNTCONTRACTBILL").updateBillRelation(discountcontractBillID,cInfo.getId(),dRealAmount);	
                        }
                    }
                
                    v.add(info);
                
                }
                Log.print("翻页查询结束");
            
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                //买入计息结束
            
                if(lDiscountApplyID > 0 && lReadOnly != Constant.YesOrNo.YES)
                {
                    //更新转贴现申请的贴现日、贴现利率、贴现实付金额
                    TransDiscountApplyDAO adao = new TransDiscountApplyDAO(this.strTableName);
                
                    TransDiscountApplyInfo ainfo = new TransDiscountApplyInfo();
                    ainfo.setId(lDiscountApplyID);
                    ainfo.setDiscountRate(dRate);
                    ainfo.setDiscountDate(tsDate);
                    ainfo.setInterestTypeId(lCountType);
                    ainfo.setCheckAmount(dTotalRealAmount);
                    ainfo.setExamineAmount(dTotalAmount);
                    
                    this.update(ainfo);
                }

            }
                
            finalizeDAO();
            
        }
        /*
        catch (IException ie)
        {
            Log.print("异常退出贴现计息");
            log4j.error(ie.getMessage());
            throw ie;
        }//*/
        catch (LoanException ie)
        {
            Log.print("异常退出贴现计息");
            log4j.error(ie.getMessage());
            throw new LoanException("Loan_E006",null);
            //throw new ie;
        }
        catch (Exception e)
        {
            log4j.error(e.toString());
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        Log.print("======退出贴现计息(calculateTransDiscountBillInterest)方法======");

        return (v.size() > 0 ? v : null);

    }
    /**
     *申请书下的票据删除操作
    */
    public void saveOutBill(TransDiscountApplyBillInfo info)
        throws java.rmi.RemoteException, LoanException
    {
        String strSQL = null;

        try
        {
            initDAO();

            //取两位的年份
            strSQL = " update rtransdiscountapplybill  "
                    +" set islocal = " + info.getIsLocal()
                    +" ,adddays = " + info.getAddDays()
                    +" ,repurchaseterm = " + info.getRepurchaseTerm()
                    +" ,repurchasedate = To_Date('" + DataFormat.getDateString(info.getRepurchaseDate()) + "','yyyy-mm-dd')"
                    +" ,checkAmount = " + info.getCheckAmount()
                    +" where transdiscountapplyid = " + info.getLoanID()
                    +" and discountcontractbillid = " + info.getId()
                    +" ";
            prepareStatement(strSQL);
            executeUpdate();

            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e1)
        {
            
            e1.printStackTrace();
            throw new LoanDAOException(e1);
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得该申请书的 合同期限
     * @param applyId
     * @return
     * @throws SecuritiesDAOException
     */
    private void getIntervalNum(TransDiscountContractInfo cInfo)
        throws java.rmi.RemoteException, LoanException
    {
        long num = 0;

        long loanId = cInfo.getLoanId();
        Timestamp startDate = cInfo.getStartDate();
        Timestamp endDate = cInfo.getEndDate();
        long inOrOut = cInfo.getInOrOut();
        long discountTypeId = cInfo.getDiscountTypeId();

        //Timestamp discountDate = cInfo.getDiscountDate();
        
        Log.print("startDate="+startDate);
        System.out.println("startDate="+startDate);
        Log.print("endDate="+endDate);
        String strStart = startDate.toString();
        System.out.println("startDate="+startDate);
        Log.print("strStart="+strStart);
        startDate =
            new java.sql.Timestamp(
                new Integer(strStart.substring(0, 4)).intValue() - 1900,
                new Integer(strStart.substring(5, 7)).intValue() - 1,
                new Integer(strStart.substring(8, 10)).intValue(),
                0,
                0,
                0,
                0);
        System.out.println("startDate="+startDate);
        if(endDate != null)      
        {  
            String strEnd = endDate.toString();
            System.out.println("strEnd="+strEnd);
            Log.print("strEnd="+strEnd);
            endDate =
                new java.sql.Timestamp(
                    new Integer(strEnd.substring(0, 4)).intValue() - 1900,
                    new Integer(strEnd.substring(5, 7)).intValue() - 1,
                    new Integer(strEnd.substring(8, 10)).intValue(),
                    0,
                    0,
                    0,
                    0);
        }
        System.out.println("endDate="+endDate);
        /* 
         * 贷款部门是按照天数对转贴现进行核算，但是结算部门提出，
         * 对于转贴现业务需要按月进行核算。
         * 这样，必须在天数和月份之间确定转换的规则。
         * 合同确定方法：
         * 1 转贴现卖出（卖断）合同、转贴现买入（买断）合同天数的确定方法：
         * 转贴现开始日期至该合同中票据最晚到期日；
         * 2 转贴现卖出（回购）合同、转贴现买入（回购）合同天数的确定方法：
         * 最长回购期限
         */
         
        int numTmp = 0;
        
        Log.print("numTmp="+numTmp);
        System.out.println("numTmp="+numTmp);
        //TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);

        if(discountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
        {
            if(endDate == null) 
            {
                Collection temp = null;
                if(inOrOut == LOANConstant.TransDiscountInOrOut.IN)
                {
                    TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
                    temp=bdao.findBillByTransDiscountApplyID(loanId);
                    
                    if( (temp != null) && (temp.size() > 0) )
                    {
                        Iterator it = temp.iterator();
                        while (it.hasNext() )
                        {
                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                        
                            if(abinfo != null)
                            {
                                if(endDate == null)
                                {
                                    endDate = abinfo.getEnd();
                                }
                                else if(abinfo.getEnd().after(endDate))
                                {
                                    endDate = abinfo.getEnd();
                                }    
                            }
                        }
                        
                    }
                }
                else if(inOrOut == LOANConstant.TransDiscountInOrOut.OUT)
                {
                    temp=this.findOutBillByTransDiscountApplyID(loanId);
                    if( (temp != null) && (temp.size() > 0) )
                    {
                        Iterator it = temp.iterator();
                        while (it.hasNext() )
                        {
                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                            
                            TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
                            
                            if(abinfo != null)
                            {
                                if(endDate == null)
                                {
                                    endDate = abinfo.getEnd();
                                }
                                else if(abinfo.getEnd().after(endDate))
                                {
                                    endDate = abinfo.getEnd();
                                }           
                            }
                        }
                    } 
                }
            }
            System.out.println("endDate"+endDate);
            System.out.println("----------------------------"+(endDate.getTime() - startDate.getTime()));
            System.out.println("----------------------------"+(endDate.getTime() - startDate.getTime()) / 86400000);
            numTmp = (int)java.lang.Math.ceil((endDate.getTime() - startDate.getTime()) / 86400000);
            System.out.println("------------------------------------");
        }
        else if(discountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
        {
            Collection temp = null;
            if(inOrOut == LOANConstant.TransDiscountInOrOut.IN)
            {
                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
                temp=bdao.findBillByTransDiscountApplyID(loanId);
                    
                if( (temp != null) && (temp.size() > 0) )
                {
                    Iterator it = temp.iterator();
                    while (it.hasNext() )
                    {
                        TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                        
                        if(abinfo != null)
                        {

                                if(endDate == null)
                                {
                                    endDate = abinfo.getRepurchaseDate();
                                }
                                else if(abinfo.getRepurchaseDate().after(endDate))
                                {
                                    endDate = abinfo.getRepurchaseDate();
                                }        
   
                            
                            if((int)abinfo.getRepurchaseTerm() > numTmp)
                            {
                                numTmp=(int)abinfo.getRepurchaseTerm();
                            }
                        }
                    }
                        
                }
            }
            else if(inOrOut == LOANConstant.TransDiscountInOrOut.OUT)
            {
                temp=this.findOutBillByTransDiscountApplyID(loanId);
                if( (temp != null) && (temp.size() > 0) )
                {
                    Iterator it = temp.iterator();
                    while (it.hasNext() )
                    {
                        TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                            
                        TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
                            
                        if(abinfo != null)
                        {
                                if(endDate == null)
                                {
                                    endDate = abinfo.getRepurchaseDate();
                                }
                                else if(abinfo.getRepurchaseDate().after(endDate))
                                {
                                    endDate = abinfo.getRepurchaseDate();
                                }        
   
                            
                            if((int)abinfo.getRepurchaseTerm() > numTmp)
                            {
                                numTmp=(int)abinfo.getRepurchaseTerm();
                            }
                        }
                    }
                } 
            }
        }

        Log.print("endDate="+endDate);
        System.out.println("endDate="+endDate);
        cInfo.setEndDate(endDate);
        Log.print("numTmp="+numTmp);
        System.out.println("numTmp="+numTmp);
        /* 
         * 转换规则：
         *   a.  天数<=31天的归为1个月
         *   b.  31<天数<=62天的归为2个月
         *   c.  62<天数<=92天的归为3个月
         *   d.  92<天数<=123天的归为4个月
         *   e.  123<天数<=153天的归为5个月
         *   f.  天数〉153天的归为6个月
         */
        if(numTmp <= 31)
        {
            num = 1;
        }
        else if(numTmp > 31 && numTmp <= 62)
        {
            num = 2;
        }
        else if(numTmp > 62 && numTmp <= 92)
        {
            num = 3;
        }
        else if(numTmp > 92 && numTmp <= 123)
        {
            num = 4;
        }
        else if(numTmp > 123 && numTmp <= 153)
        {
            num = 5;
        }
        else if(numTmp > 153)
        {
            num = 6;
        }
        Log.print("num="+num);
        System.out.println("num="+num);
        cInfo.setIntervalNum(num);
        //Log.print("cInfo:"+cInfo);
        //return num;
    }
    
    /*
     * 取合同id
     */

    private long getMaxContractID() throws LoanDAOException
    {
        long id = -1;
        String strSQL = "";

        try
        {
            try
            {
                initDAO();
                
                //得到最大的ID
                strSQL = " select seq_Loan_ContractForm.Nextval from dual ";
                
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                while (rs != null && rs.next())
                {
                    id = rs.getLong(1);
                }
                if(rs != null)
                {
                    rs.close();
                    rs=null;
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
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
        return id;
    
    }

    //由于和国机共享 ejb ,为保证编译不出错而加的空方法。
   public void deleteReviewOpinion(ApprovalTracingInfo aInfo) 
   {
   	
   }
   
   /**
	 * 更新申请结束日期操作，保存票据和删除票据时调用
	 * @param lApplyId
	 * @throws LoanDAOException
	 * @throws RemoteException
	 */
	public void updateEndDate(long lApplyID) throws LoanDAOException, RemoteException
	{
		TransDiscountApplyInfo info = new TransDiscountApplyInfo();
		Timestamp startDate = null;
       Timestamp endDate = null;
       long inOrOut = -1;
       long discountTypeId = -1;
		
		
		try {
			initDAO();
			
			info = (TransDiscountApplyInfo) findByID(lApplyID);
			startDate = info.getStartDate();
			endDate = null;
			inOrOut = info.getInOrOut();
			discountTypeId = info.getDiscountTypeId();
			
			System.out.println("=====更新申请结束日期====");
			if(discountTypeId == LOANConstant.TransDiscountType.BUYBREAK)//买断型
	        {
				System.out.println("====买断型");
	                Collection temp = null;
	                if(inOrOut == LOANConstant.TransDiscountInOrOut.IN)
	                {
	                    TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
	                    temp=bdao.findBillByTransDiscountApplyID(lApplyID);
	                    
	                    if( (temp != null) && (temp.size() > 0) )
	                    {
	                        Iterator it = temp.iterator();
	                        while (it.hasNext() )
	                        {
	                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
	                        
	                            if(abinfo != null)
	                            {
	                                if(endDate == null)
	                                {
	                                    endDate = abinfo.getEnd();
	                                }
	                                else if(abinfo.getEnd().after(endDate))
	                                {
	                                    endDate = abinfo.getEnd();
	                                }    
	                            }
	                        }
	                        
	                    }
	                }
	                else if(inOrOut == LOANConstant.TransDiscountInOrOut.OUT)
	                {
	                    temp=this.findOutBillByTransDiscountApplyID(lApplyID);
	                    if( (temp != null) && (temp.size() > 0) )
	                    {
	                        Iterator it = temp.iterator();
	                        while (it.hasNext() )
	                        {
	                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
	                            
	                            TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
	                            
	                            if(abinfo != null)
	                            {
	                                if(endDate == null)
	                                {
	                                    endDate = abinfo.getEnd();
	                                }
	                                else if(abinfo.getEnd().after(endDate))
	                                {
	                                    endDate = abinfo.getEnd();
	                                }           
	                            }
	                        }
	                    } 
	                }
				
	        }
			else if(discountTypeId == LOANConstant.TransDiscountType.REPURCHASE)//回购型
	        {
				System.out.println("====回购型");
	            Collection temp = null;
	            if(inOrOut == LOANConstant.TransDiscountInOrOut.IN)
	            {
	                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
	                temp=bdao.findBillByTransDiscountApplyID(lApplyID);
	                    
	                if( (temp != null) && (temp.size() > 0) )
	                {
	                    Iterator it = temp.iterator();
	                    while (it.hasNext() )
	                    {
	                        TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
	                        
	                        if(abinfo != null)
	                        {
	                                if(endDate == null)
	                                {
	                                    endDate = abinfo.getRepurchaseDate();
	                                }
	                                else if(abinfo.getRepurchaseDate().after(endDate))
	                                {
	                                    endDate = abinfo.getRepurchaseDate();
	                                }          
	                        }
	                    }
	                        
	                }
	            }
	            else if(inOrOut == LOANConstant.TransDiscountInOrOut.OUT)
	            {
	                temp=this.findOutBillByTransDiscountApplyID(lApplyID);
	                if( (temp != null) && (temp.size() > 0) )
	                {
	                    Iterator it = temp.iterator();
	                    while (it.hasNext() )
	                    {
	                        TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
	                        System.out.println("date:"+abinfo.getRepurchaseDate());
	                            
	                        TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
	                            
	                        if(abinfo != null)
	                        {
	                                if(endDate == null)
	                                {
	                                    endDate = abinfo.getRepurchaseDate();
	                                }
	                                else if(abinfo.getRepurchaseDate().after(endDate))
	                                {
	                                    endDate = abinfo.getRepurchaseDate();
	                                }            
	                           
	                        }
	                    }
	                } 
	            }
	        
				
			}
			
			System.out.println("endDate:"+endDate);
			TransDiscountApplyInfo ainfo = new TransDiscountApplyInfo();
			ainfo.setId(lApplyID);
			ainfo.setEndDate(endDate);
			// 更新转贴现申请表中的贴现日期为转贴现申请开始日期
			ainfo.setDiscountDate(startDate);
			ainfo.setBillStatus(BILLConstant.TransctionStatus.SUBMIT);
			if(inOrOut == LOANConstant.TransDiscountInOrOut.IN){
				ainfo.setIsChoose(1);
			}
			if (endDate != null)
			update(ainfo);
			
			finalizeDAO();
			
		} catch (LoanException e) {
			
			e.printStackTrace();
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (ITreasuryDAOException e) {
			
			e.printStackTrace();
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                
                e.printStackTrace();
            }
        }
	}
	
	public TransDiscountApplyInfo findByID1(long lID) throws java.rmi.RemoteException, LoanException
    {
        TransDiscountApplyInfo info = new TransDiscountApplyInfo();
        ResultSet rs = null;
        String strSQL = "";

        try
        {
            initDAO();
            
            strSQL = " select * from Cra_loanForm " 
                + " where id=" + lID;
            System.out.println(strSQL);
            Log.print(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            
            if (rs != null && rs.next())
            {
                //贴现申请
                info.setId(lID); //贴现申请标识
                info.setApplyCode(rs.getString("sApplyCode")); //贴现申请编号
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
                info.setInputUserId(rs.getLong("nInputUserID"));
                info.setInputDate(rs.getTimestamp("dtInputDate"));
                info.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //下一个审核人标示
                info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
                info.setIsLowLevel(rs.getLong("isLowLevel"));
                info.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
                info.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
                info.setLoanReason(rs.getString("sLoanReason"));
                info.setLoanPurpose(rs.getString("sLoanPurpose"));
                info.setStartDate(rs.getTimestamp("dtStartDate"));//start日
                info.setEndDate(rs.getTimestamp("dtEndDate"));//start日
                info.setOfficeId(rs.getLong("nOfficeId"));
                info.setCurrencyId(rs.getLong("nCurrencyId"));
                info.setTypeId(rs.getLong("nTypeId"));
                info.setInterestTypeId(rs.getLong("nInterestTypeId"));
                info.setNSubtypeid(rs.getLong("NSUBTYPEID"));
                info.setIsLocal(rs.getLong("NISLOCAL"));
                info.setRepurchaseDate(rs.getTimestamp("DTREPURCHASEDATE"));
                info.setRepurchaseTerm(rs.getLong("NREPURCHASETERM"));
                info.setIsChoose(rs.getLong("NISCHOOSE"));
                info.setAddDays(rs.getLong("NADDDAYS"));
                info.setBillStatus(rs.getLong("NBILLSTATUS"));
                info.setTransActionTypeId(rs.getLong("NTRANSACTIONTYPEID"));
                //附件id
                info.setBillAttachId(rs.getLong("BILLATTACHID"));
                info.setAttachId(rs.getLong("ATTACHID"));
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            
            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e)
        {
            
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            
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
                
                e1.printStackTrace();
            }
        }
        return info;
    }
	
	public Collection findByMultiOption2(TransDiscountApplyQueryInfo qInfo)
    throws java.rmi.RemoteException, LoanException
{
    Vector v = new Vector();
    
    StringBuffer sbCount = new StringBuffer();
    StringBuffer sbSelect = new StringBuffer();
    StringBuffer sbSQL = new StringBuffer();
    StringBuffer sbOrder = new StringBuffer();
    String strSQL = "";
    
    long queryPurpose = qInfo.getQueryPurpose();
    long officeID = qInfo.getOfficeId();
    long currencyID = qInfo.getCurrencyId();

    long startTransDiscountApplyId =
        qInfo.getStartTransDiscountApplyId();
    long endTransDiscountApplyId = 
        qInfo.getEndTransDiscountApplyId();
        
    long clientID = qInfo.getClientId();
    
    double startAmount = qInfo.getStartAmount();
    double endAmount = qInfo.getEndAmount();
    
    Timestamp startDate = qInfo.getQueryStartDate();
    Timestamp endDate = qInfo.getQueryEndDate();
    
    long statusID = qInfo.getQueryStatusId();
    
    long userID = qInfo.getUserId();
    
    ApprovalDelegation appBiz = new ApprovalDelegation();
    String strUser = qInfo.getUser();//审核的虚拟用户组

    long pageLineCount = qInfo.getPageLineCount();
    long pageNo = qInfo.getPageNo();
    long orderParam = qInfo.getOrderParam();
    long desc = qInfo.getDesc();
    String orderParamString = qInfo.getOrderParamString();

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
        //计算记录总数   
        sbCount.setLength(0);
        
        
        sbSQL.setLength(0);
        //sbSQL.append(" \n from Loan_LoanForm aa  ");
        sbSQL.append(" \n select * from "+this.strTableName+" aa  ");
        sbSQL.append(" \n where 1=1 ");
        sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
        sbSQL.append(" and aa.nStatusID in ("+LOANConstant.LoanStatus.SUBMIT+", "+LOANConstant.LoanStatus.SAVE+", "+LOANConstant.LoanStatus.CHECK+") ");
        sbSQL.append(" \n and aa.nInputUserID = " + userID);
        if(startTransDiscountApplyId > 0 )
        {
            sbSQL.append(" \n and aa.ID >= " + startTransDiscountApplyId);
        }
        if(endTransDiscountApplyId > 0 )
        {
            sbSQL.append(" \n and aa.ID <= " + endTransDiscountApplyId);
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
        if (startDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startDate)
                    + "'");
        }
        if (endDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endDate)
                    + "'");
        }

        try
        {
            long lID = -1;
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
            System.out.println(sbSQL.toString());
            prepareStatement(sbSQL.toString());
            ResultSet rs1 = executeQuery();
            while (rs1 != null && rs1.next())
            {
                lID = rs1.getLong("id");
                TransDiscountApplyInfo info = new TransDiscountApplyInfo();
                if(lID > 0)
                {
                    info=(TransDiscountApplyInfo)dao.findByID1(lID);//,info.getClass());
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
            
            e1.printStackTrace();
        }
    }
    return (v.size() > 0 ? v : null);
}

	public Collection findByMultiOption3(TransDiscountApplyQueryInfo qInfo)
    throws java.rmi.RemoteException, LoanException
{
    Vector v = new Vector();
    
    StringBuffer sbCount = new StringBuffer();
    StringBuffer sbSelect = new StringBuffer();
    StringBuffer sbSQL = new StringBuffer();
    StringBuffer sbOrder = new StringBuffer();
    String strSQL = "";
    
    long queryPurpose = qInfo.getQueryPurpose();
    long officeID = qInfo.getOfficeId();
    long currencyID = qInfo.getCurrencyId();

    long startTransDiscountApplyId =
        qInfo.getStartTransDiscountApplyId();
    long endTransDiscountApplyId = 
        qInfo.getEndTransDiscountApplyId();
        
    long clientID = qInfo.getClientId();
    
    double startAmount = qInfo.getStartAmount();
    double endAmount = qInfo.getEndAmount();
    
    Timestamp startDate = qInfo.getQueryStartDate();
    Timestamp endDate = qInfo.getQueryEndDate();
    
    long statusID = qInfo.getQueryStatusId();
    
    long billStatus = qInfo.getBillStatus();
    
    long userID = qInfo.getUserId();
    
    ApprovalDelegation appBiz = new ApprovalDelegation();
    String strUser = qInfo.getUser();//审核的虚拟用户组

    long pageLineCount = qInfo.getPageLineCount();
    long pageNo = qInfo.getPageNo();
    long orderParam = qInfo.getOrderParam();
    long desc = qInfo.getDesc();
    String orderParamString = qInfo.getOrderParamString();

    double totalAmount = 0.0;
    long lRecordCount = -1;
    long lPageCount = -1;
    long lRowNumStart = -1;
    long lRowNumEnd = -1;
    long inOrOut = qInfo.getInOrOut();
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
        //计算记录总数   
        sbCount.setLength(0);
        
        
        sbSQL.setLength(0);
        //sbSQL.append(" \n from Loan_LoanForm aa  ");
        sbSQL.append(" \n select * from "+this.strTableName+" aa  ");
        sbSQL.append(" \n where 1=1 ");
        sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
        sbSQL.append(" and aa.nStatusID = "+LOANConstant.LoanStatus.CHECK );
        sbSQL.append(" and aa.NISCHOOSE = 0 ");
        if (billStatus < 0)
	      {
        	 sbSQL.append(" and aa.NBILLSTATUS not in( "+BILLConstant.TransctionStatus.APPROVALING+","+BILLConstant.TransctionStatus.APPROVALED+","+BILLConstant.TransctionStatus.SUBMIT+")");
	      }      
        //sbSQL.append(" \n and aa.nInputUserID = " + userID);
        if(startTransDiscountApplyId > 0 )
        {
            sbSQL.append(" \n and aa.ID >= " + startTransDiscountApplyId);
        }
        if(endTransDiscountApplyId > 0 )
        {
            sbSQL.append(" \n and aa.ID <= " + endTransDiscountApplyId);
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
        //added by qhzhou 2007-09-22
        if (inOrOut > 0)
        {
            sbSQL.append(" \n and aa.nInOrOut = " + inOrOut);
        }
//        if (statusID > 0)
//        {
//            sbSQL.append(" \n and aa.nStatusID = " + statusID);
//        }
	      if (billStatus > 0)
	      {
	          sbSQL.append(" \n and aa.nBillStatus = " + billStatus);
	      }
        
        if (startDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
                    + DataFormat.getDateString(startDate)
                    + "'");
        }
        if (endDate != null)
        {
            sbSQL.append(
                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
                    + DataFormat.getDateString(endDate)
                    + "'");
        }

        try
        {
            long lID = -1;
            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
            System.out.println(sbSQL.toString());
            prepareStatement(sbSQL.toString());
            ResultSet rs1 = executeQuery();
            while (rs1 != null && rs1.next())
            {
                lID = rs1.getLong("id");
                TransDiscountApplyInfo info = new TransDiscountApplyInfo();
                if(lID > 0)
                {
                    info=(TransDiscountApplyInfo)dao.findByID1(lID);//,info.getClass());
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
            
            e1.printStackTrace();
        }
    }
    return (v.size() > 0 ? v : null);
}

	/**
	 * 票据出库审批--转贴现
	 */
	public Collection findByMultiOption4(TransDiscountApplyQueryInfo qInfo)
    throws java.rmi.RemoteException, LoanException
	{
	    Vector v = new Vector();
	    
	    StringBuffer sbCount = new StringBuffer();
	    StringBuffer sbSelect = new StringBuffer();
	    StringBuffer sbSQL = new StringBuffer();
	    StringBuffer sbOrder = new StringBuffer();
	    String strSQL = "";
	    
	    long queryPurpose = qInfo.getQueryPurpose();
	    long officeID = qInfo.getOfficeId();
	    long currencyID = qInfo.getCurrencyId();
	
	    long startTransDiscountApplyId =
	        qInfo.getStartTransDiscountApplyId();
	    long endTransDiscountApplyId = 
	        qInfo.getEndTransDiscountApplyId();
	        
	    long clientID = qInfo.getClientId();
	    
	    double startAmount = qInfo.getStartAmount();
	    double endAmount = qInfo.getEndAmount();
	    
	    Timestamp startDate = qInfo.getQueryStartDate();
	    Timestamp endDate = qInfo.getQueryEndDate();
	    
	    long statusID = qInfo.getQueryStatusId();
	    
	    long userID = qInfo.getUserId();
	    
	    ApprovalDelegation appBiz = new ApprovalDelegation();
	    String strUser = qInfo.getUser();//审核的虚拟用户组
	
	    long pageLineCount = qInfo.getPageLineCount();
	    long pageNo = qInfo.getPageNo();
	    long orderParam = qInfo.getOrderParam();
	    long desc = qInfo.getDesc();
	    String orderParamString = qInfo.getOrderParamString();
	
	    double totalAmount = 0.0;
	    long lRecordCount = -1;
	    long lPageCount = -1;
	    long lRowNumStart = -1;
	    long lRowNumEnd = -1;
	    System.out.println("-------------------------------------------------------------");
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
	        //计算记录总数   
	        sbCount.setLength(0);
	        
	        
	        sbSQL.setLength(0);

	        sbSQL.append("(SELECT aa.* from "+this.strTableName+" aa");
	        sbSQL.append(" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b");
	        sbSQL.append(" where (a.NNEXTUSERID="+userID+" ");
	        sbSQL.append(" and a.nloantypeid = "+BILLConstant.BlankTransctionType.OUTBILL+" ");
	        sbSQL.append("  and a.NMODULEID="+Constant.ModuleType.BILL+" and nstatusid="+Constant.RecordStatus.VALID+"");
	        sbSQL.append(" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d");
	        sbSQL.append(" where aa.id =d.NAPPROVALCONTENTID and aa.nbillstatus = " + BILLConstant.TransctionStatus.APPROVALING);
	        sbSQL.append(" and aa.ntransactiontypeid="+BILLConstant.BlankTransctionType.OUTBILL+"");
            if(startTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and aa.ID >= " + startTransDiscountApplyId);
	        }
	        if(endTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and aa.ID <= " + endTransDiscountApplyId);
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
	        if (startDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
	                    + DataFormat.getDateString(startDate)
	                    + "'");
	        }
	        if (endDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
	                    + DataFormat.getDateString(endDate)
	                    + "'");
	        }
	        sbSQL.append(") union ( ");		    
	        sbSQL.append(" select a.* from "+this.strTableName+"  a , loan_approvalrelation b where a.ntransactiontypeid=b.loantypeid and b.moduleid="+Constant.ModuleType.BILL);
	        sbSQL.append(" and a.nbillstatus = "+BILLConstant.TransctionStatus.SUBMIT+" and "+userID+" in (");
	        sbSQL.append(" select nuserid from loan_approvalitem where napprovalid = ( select approvalid from loan_approvalrelation where moduleid = 10 and loantypeid = 206) and nlevel = 1)");
			//增加关于币种的判断-mhjin-东方电气
	        if(startTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and a.ID >= " + startTransDiscountApplyId);
	        }
	        if(endTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and a.ID <= " + endTransDiscountApplyId);
	        }
	        if (clientID != -1)
	        {
	            sbSQL.append(" \n and a.nBorrowClientID = " + clientID);
	        }
	        if (startAmount != 0)
	        {
	            sbSQL.append(" \n and a.mLoanAmount >= " + startAmount);
	        }
	        if (endAmount != 0)
	        {
	            sbSQL.append(" \n and a.mLoanAmount <= " + endAmount);
	        }
	        if (currencyID > 0)
	        {
	            sbSQL.append(" \n and a.nCurrencyID = " + currencyID);
	        }
	        if (officeID > 0)
	        {
	            sbSQL.append(" \n and a.nOfficeID = " + officeID);
	        }
	        if (startDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(a.dtInputDate,'yyyy-mm-dd') >= '"
	                    + DataFormat.getDateString(startDate)
	                    + "'");
	        }
	        if (endDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(a.dtInputDate,'yyyy-mm-dd') <= '"
	                    + DataFormat.getDateString(endDate)
	                    + "'");
	        }
	        sbSQL.append( " ) ");
	        
	        //sbSQL.append(" \n from Loan_LoanForm aa  ");
	/*        sbSQL.append(" \n select * from "+this.strTableName+" aa  ");
	        sbSQL.append(" \n where 1=1 ");
	        sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
	        sbSQL.append(" and aa.NBILLSTATUS in( "+BILLConstant.TransctionStatus.SUBMIT + ",");
	        sbSQL.append(" and aa.NISCHOOSE = 0 ");
	        sbSQL.append(" and aa.NBILLSTATUS not in( "+LOANConstant.LoanStatus.CHECK+","+LOANConstant.LoanStatus.APPROVALING+") ");
	        //sbSQL.append(" \n and aa.nInputUserID = " + userID);
	        if(startTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and aa.ID >= " + startTransDiscountApplyId);
	        }
	        if(endTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and aa.ID <= " + endTransDiscountApplyId);
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
	//        if (statusID > 0)
	//        {
	//            sbSQL.append(" \n and aa.nStatusID = " + statusID);
	//        }
	        if (startDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
	                    + DataFormat.getDateString(startDate)
	                    + "'");
	        }
	        if (endDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
	                    + DataFormat.getDateString(endDate)
	                    + "'");
	        }
	*/
	        try
	        {
	            long lID = -1;
	            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
	            System.out.println(sbSQL.toString());
	            prepareStatement(sbSQL.toString());
	            ResultSet rs1 = executeQuery();
	            while (rs1 != null && rs1.next())
	            {
	                lID = rs1.getLong("id");
	                TransDiscountApplyInfo info = new TransDiscountApplyInfo();
	                if(lID > 0)
	                {
	                    info=(TransDiscountApplyInfo)dao.findByID1(lID);//,info.getClass());
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
	            
	            e1.printStackTrace();
	        }
	    }
	    return (v.size() > 0 ? v : null);
	}
	//update by dwj 20111214 同步方正票据管理模块
	public Collection findByMultiOption5(TransDiscountApplyQueryInfo qInfo)
    throws java.rmi.RemoteException, LoanException
    {

	    Vector v = new Vector();
	    
	    StringBuffer sbCount = new StringBuffer();
	    StringBuffer sbSelect = new StringBuffer();
	    StringBuffer sbSQL = new StringBuffer();
	    StringBuffer sbOrder = new StringBuffer();
	    String strSQL = "";
	    
	    long queryPurpose = qInfo.getQueryPurpose();
	    long officeID = qInfo.getOfficeId();
	    long currencyID = qInfo.getCurrencyId();

	    long startTransDiscountApplyId =
	        qInfo.getStartTransDiscountApplyId();
	    long endTransDiscountApplyId = 
	        qInfo.getEndTransDiscountApplyId();
	        
	    long clientID = qInfo.getClientId();
	    
	    double startAmount = qInfo.getStartAmount();
	    double endAmount = qInfo.getEndAmount();
	    
	    Timestamp startDate = qInfo.getQueryStartDate();
	    Timestamp endDate = qInfo.getQueryEndDate();
	    
	    long statusID = qInfo.getQueryStatusId();
	    
	    long billStatus = qInfo.getBillStatus();
	    
	    long userID = qInfo.getUserId();
	    
	    ApprovalDelegation appBiz = new ApprovalDelegation();
	    String strUser = qInfo.getUser();//审核的虚拟用户组

	    long pageLineCount = qInfo.getPageLineCount();
	    long pageNo = qInfo.getPageNo();
	    long orderParam = qInfo.getOrderParam();
	    long desc = qInfo.getDesc();
	    String orderParamString = qInfo.getOrderParamString();

	    double totalAmount = 0.0;
	    long lRecordCount = -1;
	    long lPageCount = -1;
	    long lRowNumStart = -1;
	    long lRowNumEnd = -1;
	    long inOrOut = qInfo.getInOrOut();
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
	        //计算记录总数   
	        sbCount.setLength(0);
	        String strTable = "(select * from Cra_loanform a where  a.nTypeID = " + LOANConstant.LoanType.ZTX + " and a.nStatusID = "+LOANConstant.LoanStatus.CHECK;
	        if (billStatus < 0)	{
	        	strTable += " and a.NISCHOOSE = 0 and a.NBILLSTATUS not in ("+BILLConstant.TransctionStatus.APPROVALING+","+BILLConstant.TransctionStatus.APPROVALED+","+BILLConstant.TransctionStatus.SUBMIT+")";
	        }
	        strTable += ") union "
	        	+"(select * from Cra_loanform a  where a.nTypeID = " + LOANConstant.LoanType.ZTX + " and a.nStatusID = "+LOANConstant.LoanStatus.CHECK+" and a.nInOrOut = 1 and a.nBillStatus = 1 and not exists(select cc.nloanid from loan_contractform cc where cc.nloanid = a.id and cc.ntypeid = " + LOANConstant.LoanType.ZTX + " and cc.nstatusid > 0 and cc.nstatusid <> " + LOANConstant.ContractStatus.CANCEL +"))";
	        
	        sbSQL.setLength(0);
	        //sbSQL.append(" \n from Loan_LoanForm aa  ");
	        //sbSQL.append(" \n select * from "+this.strTableName+" aa  ");
	        sbSQL.append(" \n select * from ("+strTable+") aa  ");
	        sbSQL.append(" \n where 1=1 ");
	        sbSQL.append(" \n and aa.nTypeID = " + LOANConstant.LoanType.ZTX);
	        sbSQL.append(" and aa.nStatusID = "+LOANConstant.LoanStatus.CHECK );
	        //sbSQL.append(" and aa.NISCHOOSE = 0 ");
	       // if (billStatus < 0)
		   //   {
	       // 	 sbSQL.append(" and aa.NBILLSTATUS not in( "+BILLConstant.TransctionStatus.APPROVALING+","+BILLConstant.TransctionStatus.APPROVALED+","+BILLConstant.TransctionStatus.SUBMIT+")");
		   //   }      
	        //sbSQL.append(" \n and aa.nInputUserID = " + userID);
	        if(startTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and aa.ID >= " + startTransDiscountApplyId);
	        }
	        if(endTransDiscountApplyId > 0 )
	        {
	            sbSQL.append(" \n and aa.ID <= " + endTransDiscountApplyId);
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
	        //added by qhzhou 2007-09-22
	        if (inOrOut > 0)
	        {
	            sbSQL.append(" \n and aa.nInOrOut = " + inOrOut);
	        }
//	        if (statusID > 0)
//	        {
//	            sbSQL.append(" \n and aa.nStatusID = " + statusID);
//	        }
		      if (billStatus > 0)
		      {
		          sbSQL.append(" \n and aa.nBillStatus = " + billStatus);
		      }
	        
	        if (startDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') >= '"
	                    + DataFormat.getDateString(startDate)
	                    + "'");
	        }
	        if (endDate != null)
	        {
	            sbSQL.append(
	                " \n and to_char(aa.dtInputDate,'yyyy-mm-dd') <= '"
	                    + DataFormat.getDateString(endDate)
	                    + "'");
	        }

	        try
	        {
	            long lID = -1;
	            TransDiscountApplyDAO dao = new TransDiscountApplyDAO(this.strTableName);
	            System.out.println(sbSQL.toString());
	            prepareStatement(sbSQL.toString());
	            ResultSet rs1 = executeQuery();
	            while (rs1 != null && rs1.next())
	            {
	                lID = rs1.getLong("id");
	                TransDiscountApplyInfo info = new TransDiscountApplyInfo();
	                if(lID > 0)
	                {
	                    info=(TransDiscountApplyInfo)dao.findByID1(lID);//,info.getClass());
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
	            
	            e1.printStackTrace();
	        }
	    }
	    return (v.size() > 0 ? v : null);

    }
	
	public long getCraftInIDbyBillID (long lBillID) throws java.rmi.RemoteException, LoanException
	{
		long lCraftInID = -1;
		String strSQL = "";
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
	        
	        strSQL = "select id from bill_transdraftin where billid=" + lBillID;
	        System.out.println("查询入库ID SQL:"+strSQL);
	        
	        prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	lCraftInID = rs.getLong("id");
            }
            
            finalizeDAO();
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
	        } 
	        catch (ITreasuryDAOException e1)
	        {
	            e1.printStackTrace();
	        }
	    }
	    
		return lCraftInID;
	}
	
	public long saveContractBillInfo(long lApplyID) throws java.rmi.RemoteException, LoanException
	{
		String strSQL = "";
		long lContractId = -1;
		long lInOrOut = -1;
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
	        
	        strSQL = "select a.id,b.ninorout from loan_contractform a,cra_loanform b";
	        strSQL += " where a.ntypeid=" + LOANConstant.LoanType.ZTX;
	        strSQL += " and a.nloanid=b.id and b.id="+lApplyID;
	        System.out.println("查询入库ID SQL:"+strSQL);
	        
	        prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	lContractId = rs.getLong("id");
            	lInOrOut = rs.getLong("ninorout");
            }
            
            finalizeDAO();
            
            if(lContractId>0)
            {
            	if(lInOrOut == LOANConstant.TransDiscountInOrOut.IN)
            	{
	            	System.out.println("===买入==生成转贴现合同票据===start===");
	                Collection temp = null;
	                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
	                temp=bdao.findBillByTransDiscountApplyID(lApplyID);
	                
	                if( (temp != null) && (temp.size() > 0) )
	                {
	                    Iterator it = temp.iterator();
	                    while (it.hasNext() )
	                    {
	                        TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
	                        
	                        TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
	                        
	                        if(abinfo != null)
	                        {
	    						cbinfo.setCurrencyId(abinfo.getCurrencyId());
	    						cbinfo.setOfficeId(abinfo.getOfficeId());
	                            cbinfo.setContractID(lContractId);
	                            cbinfo.setAcceptPoTypeID(abinfo.getAcceptPoTypeID());
	                            cbinfo.setAddDays(abinfo.getAddDays());
	                            cbinfo.setAmount(abinfo.getAmount());
	                            cbinfo.setBank(abinfo.getBank());
	                            cbinfo.setCheckAmount(abinfo.getCheckAmount());
	                            cbinfo.setCode(abinfo.getCode());
	                            cbinfo.setCreate(abinfo.getCreate());
	                            cbinfo.setEnd(abinfo.getEnd());
	                            cbinfo.setDiscountCredenceID(abinfo.getDiscountCredenceID());
	                            cbinfo.setFormerOwner(abinfo.getFormerOwner());
	                            cbinfo.setIsLocal(abinfo.getIsLocal());
	                            cbinfo.setOB_nDiscountCredenceID(abinfo.getOB_nDiscountCredenceID());
	                            cbinfo.setSerialNo(abinfo.getSerialNo());
	                            cbinfo.setStatusID(abinfo.getStatusID());
	                            cbinfo.setUserName(abinfo.getUserName());
	                            cbinfo.setSellStatusID(Constant.YesOrNo.NO);
	                            cbinfo.setBillSourceTypeID(LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
	                            cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
	                            cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
	                            
	                            //cbinfo.setNstoragestatusid(LOANConstant.TransDiscountInOrOut.IN);
	                            /*
	                            cbinfo.setIsLocal(abinfo.getIsLocal());
	                            cbinfo.setAddDays(abinfo.getAddDays());
	                            cbinfo.setCheckAmount(abinfo.getCheckAmount());
	                            cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
	                            cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
	                            //*/
	                            //cbinfo(abinfo());
	                            
	                            //delegation.saveBill(cbinfo);
	                            
	                            TransDiscountContractBillDAO cbdao = new TransDiscountContractBillDAO("loan_discountcontractbill");
	                            TransDiscountContractDAO cdao = new TransDiscountContractDAO("loan_contractform");
	                                
	                            //设置下一个序列号
	                            cbinfo.setSerialNo(cbdao.findMaxTransDiscountContractBillSerialNo(cbinfo.getContractID())+1);
	            
	                            cbdao.setUseMaxID();//取MAX_ID
	
	                            System.out.println("===买入==复制票据===start===");
	                                
	                            long id =cbdao.add(cbinfo);
	                                
	                            cbinfo.setId(id);
	                                
	                            System.out.println("===买入==复制票据===end===");
	
	                            System.out.println("===买入==复制票据关系===start===");
	                            cdao.addBillRelation(cbinfo);
	                            System.out.println("===买入==复制票据关系===end===");
	                            
	                        }
	                    }
	                }
	                System.out.println("===买入==生成转贴现合同票据===end===");
	            }
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
	            finalizeDAO();
	        } 
	        catch (ITreasuryDAOException e1)
	        {
	            e1.printStackTrace();
	        }
	    }
	    
	    return lContractId;
	}
	
	public long add(ITreasuryBaseDataEntity dataEntity) throws ITreasuryDAOException{
		initDAO();
		
		//设置空值到DataEntity的已使用表,使得setPrepareStatementByDataEntity会ID进行付值
		dataEntity.setId(-1);
				
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT INTO "+ strTableName+" (\n");
		String[] buffers = getAllFieldNameBuffer(dataEntity,DAO_OPERATION_ADD);
		buffer.append(buffers[0]);
		buffer.append("\n) "+ "VALUES (\n");				
		buffer.append(buffers[1] +") \n");
		
		String strSQL = buffer.toString();
		System.out.println(strSQL);
		log.debug(strSQL);		
		prepareStatement(strSQL);

		long id = setPrepareStatementByDataEntity(dataEntity,DAO_OPERATION_ADD,buffers[0].toString());

		executeUpdate();
		
		finalizeDAO();
		return id;
	}
	
	public long setPrepareStatementByDataEntity(ITreasuryBaseDataEntity dataEntity, int operationType, String fieldNames) throws ITreasuryDAOException{

		String[] fieldNameArray = null;
		long id = -1;
		//maybe has blank in every string, you must trim it before using it
		
		//fieldNameArray = fieldNames.split(",");
		if(operationType == DAO_OPERATION_FIND){
			fieldNameArray = DataFormat.splitString(fieldNames, "AND");
//			String lastOne = fieldNameArray[fieldNameArray.length-1];
			//cut last one " = ?"
//			lastOne = lastOne.substring(0,lastOne.length()-4);
//			fieldNameArray[fieldNameArray.length-1] = lastOne;
		}
		else	
			fieldNameArray = DataFormat.splitString(fieldNames, ",");
		int j = 1;
		for (int i = 0; i < fieldNameArray.length; i++) {
			String fieldName = null;

			fieldName = (fieldNameArray[i]).trim();
							
			if(operationType == DAO_OPERATION_UPDATE || operationType == DAO_OPERATION_FIND){//cut " = ?"
				fieldName = fieldName.substring(0,fieldName.length()-4);
			}

			//log.debug(
			//	"Field Name: "+ fieldName);

			//更新操作不对ID进行处理	
			if(operationType == DAO_OPERATION_UPDATE && fieldName.compareToIgnoreCase("id") == 0)//更新操作不对id进行处理
					continue;				
			try {
				HashMap allFieldsAndValues = dataEntity.gainAllUsedFieldsAndValue();
				Object resValue = allFieldsAndValues.get(fieldName);
				
				if(resValue instanceof Long){
					long value = ((Long)(resValue)).longValue();
					if(operationType == DAO_OPERATION_ADD && fieldName.compareToIgnoreCase("id") == 0){
							value = getMaxID();							
						transPS.setLong(j, value);
						id = value;
					}
					else
						transPS.setLong(j, value);
				}else if(resValue instanceof Double){
					double value = ((Double)(resValue)).doubleValue();				
					transPS.setDouble(j,value);
				}else if(resValue instanceof String){
					transPS.setString(j, (String)resValue);
				}else if(resValue instanceof Timestamp){
					Timestamp time = (Timestamp)resValue;
					if(time.equals(ITreasuryBaseDataEntity.getNullTimeStamp()))
						time = null;
					transPS.setTimestamp(j, time);				
				}
				else{
					throw new ITreasuryDAOException("Debug:setPrepareStatementByDataEntity类型不匹配",null);
				}
				//log.debug("value is: "+resValue);

			} catch (SQLException e) {
				throw new ITreasuryDAOException("数据库异常发生",e);
			}
			j++;

		}		
		//log.debug("-----end of setPrepareStatementByDataEntity--------");
		return id;
	
	}	
	
	
	private long getMaxID()  throws ITreasuryDAOException{
		long id = -1;
		PreparedStatement localPS = null;
		ResultSet localRS = null;		
		StringBuffer sb = new StringBuffer();
		//sb.append("select case when max(l.id)>max(c.id) then nvl(max(l.id)+1,1) else nvl(max(c.id)+1,1) end oID from loan_loanform l , cra_loanform c");
        //上面的MAXID比较有问题,有时取不出值来
		sb.append("select nvl(max(maxl),0)+1 oID from (select max(id) maxl from cra_loanform union select max(id) maxl from loan_loanform)");
		try {	//内部维护RS和PS，否则将会产生冲突,但Connection使用同一个		
			localPS = transConn.prepareStatement(sb.toString());
			ResultSet rs = localPS.executeQuery();
			if (rs.next())
			{
				id = rs.getLong("oID");
			}
			if(localRS != null)
				localRS.close();
			if(localPS != null)
				localPS.close();
		} catch (SQLException e) {
			new ITreasuryDAOException("数据库获取ID产异常",e);
		}				
		return id;		
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
	strSQL = " update Cra_LoanForm set nStatusID=? where ID=?";

	ps = conn.prepareStatement(strSQL);
	ps.setLong(1, newStatusID);
	ps.setLong(2, loanID);

	lResult = ps.executeUpdate();
	cleanup(ps);
	cleanup(conn);

	if (lResult < 0) {
		log.info("update Cra_LoanForm property info error:" + lResult);
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
	 /**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long cancelBill(long loanID, long newStatusID)
	throws Exception {
       PreparedStatement ps = null;
       Connection conn = null;
       String strSQL = null;
       long lResult = -1;
      try {
	conn = Database.getConnection();
	strSQL = " update loan_discountcontractbill t set t.nstatusid="+newStatusID+" where t.ncontractid in " +
			"( select l.id from  cra_loanform c, loan_contractform l where  c.id = l.nloanid and c.id ="+loanID+")";	
	ps = conn.prepareStatement(strSQL);
	//ps.setLong(1, newStatusID);
	//ps.setLong(2, loanID);
	System.out.print("我的sql是:"+strSQL);
	lResult = ps.executeUpdate();
	cleanup(ps);
	cleanup(conn);
	if (lResult < 0) {
		log.info("update loan_discountcontractbill property info error:" + lResult);
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
	
	
	  /**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	
	/**
	 * 检查合同状态
	 */
	public long checkContractState(long loanId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = "select * from loan_contractform t where t.nloanid = ? and t.nstatusid not in(?,?,?)";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, loanId);
			ps.setLong(2, Constant.RecordStatus.INVALID);
			ps.setLong(3, SECConstant.ContractStatus.CANCEL);
			ps.setLong(4, SECConstant.ContractStatus.REFUSE);
			rs = ps.executeQuery();
			System.out.println(strSQL);
			
			while(rs.next()) {
				lResult = lResult + 1;
			}
			
			cleanup(ps);
		} catch (Exception e) {
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
		return lResult;
	}
	
	
    public  void doAfterApprovalOver(long lApplyID) throws java.rmi.RemoteException, LoanException,Exception
    {
        TransDiscountApplyInfo aInfo = new TransDiscountApplyInfo();
        long lContractId = -1;
        String strContractTable = "";
        String strFileName = "";        
        strContractTable = "Loan_ContractForm";        
        String strContractBillTable = "";
        strContractBillTable = "Loan_DiscountContractBill";
       // strContractBillTable = "cra_transdiscountbill";
        try
        {
            Timestamp Date = Env.getSystemDateTime();
            //aInfo =(TransDiscountApplyInfo) findByID(lApplyID);//, aInfo.getClass());
            aInfo =(TransDiscountApplyInfo) findByID1(lApplyID);
            if (aInfo != null)
            {
                //生成转贴现合同
                //TransDiscountContractDelegation delegation = new TransDiscountContractDelegation();
                TransDiscountContractDAO cdao = new TransDiscountContractDAO(strContractTable);
                TransDiscountContractBillDAO cbdao = new TransDiscountContractBillDAO(strContractBillTable);
                TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
                TransDiscountContractInfo cInfo = new TransDiscountContractInfo();
                //判断是否已经生成了贷款合同
                TransDiscountContractInfo cInfo1 =cdao.findByApplyCode(aInfo.getApplyCode(),aInfo.getOfficeId(),aInfo.getCurrencyId());
                double sumAmount=0.0;
                if(aInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
                {
              // 修改合同金额为票面金额，更新以前的是申请金额 modify by xwhe 2008-03-7
                sumAmount = bdao.findBillAmountByID(aInfo.getId());
                }
                else if(aInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
                {
               // 修改合同金额为票面金额，更新以前的是申请金额 modify by xwhe 2008-03-7
                 sumAmount = bdao.findOutBillAmountByID(aInfo.getId());	
                }
                cInfo.setLoanId(aInfo.getId()); 
                cInfo.setNSubtypeid(aInfo.getNSubtypeid());
                cInfo.setApplyCode(aInfo.getApplyCode());
                cInfo.setBankAcceptPO(aInfo.getBankAcceptPO());
                cInfo.setBizAcceptPO(aInfo.getBizAcceptPO());
                cInfo.setBorrowClientId(aInfo.getBorrowClientId());
                cInfo.setCheckAmount(aInfo.getCheckAmount());
                cInfo.setCurrencyId(aInfo.getCurrencyId());
                cInfo.setOfficeId(aInfo.getOfficeId());
                cInfo.setDiscountDate(aInfo.getDiscountDate());
                cInfo.setDiscountRate(aInfo.getDiscountRate());
                cInfo.setExamineAmount(aInfo.getExamineAmount());
                cInfo.setInputUserID(aInfo.getInputUserId());              
                cInfo.setLoanAmount(sumAmount);             
                cInfo.setStartDate(aInfo.getStartDate());
                cInfo.setEndDate(aInfo.getEndDate());
                cInfo.setLoanReason(aInfo.getLoanReason());
                cInfo.setLoanPurpose(aInfo.getLoanPurpose());
                cInfo.setInOrOut(aInfo.getInOrOut());
                cInfo.setDiscountTypeId(aInfo.getDiscountTypeId());
                cInfo.setRepurchaseTypeId(aInfo.getRepurchaseTypeId());
                cInfo.setTypeId(aInfo.getTypeId());
                //cInfo.setEndDate(aInfo.getEndDate());
                cInfo.setDiscountDate(aInfo.getDiscountDate());
                cInfo.setNextCheckUserId(aInfo.getInputUserId());
                cInfo.setInterestTypeId(aInfo.getInterestTypeId());
                
                //lContractId=delegation.save(cInfo);
                
                //将下一个审批级别设为1
                cInfo.setNextCheckLevel(1);
                cInfo.setStatusId(LOANConstant.ContractStatus.SAVE);
                //info.setApplyCode(dao.createApplyCode());
                
                //生成合同流水号
                HashMap map = new HashMap();
                map.put("officeID",String.valueOf(cInfo.getOfficeId()));
                map.put("currencyID",String.valueOf(cInfo.getCurrencyId()));
                map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
                map.put("transTypeID",String.valueOf(cInfo.getTypeId()));
                map.put("actionID",String.valueOf(CRAconstant.TransactionType.LOAN_CONTRACT));
                String applyCode=CreateCodeManager.createCode(map);
                
                cInfo.setContractCode(applyCode);
                Log.print("ContractCode:"+cInfo.getContractCode());
                cInfo.setInputDate(Date);
                //info.setStartDate(info.getDiscountDate());
                
                //===========intervalnum==========/
                //将这里放到合同算息
                //getIntervalNum(cInfo);
                
                //Log.print("cInfo:"+cInfo);
                /*
                if (Env.getProjectName().equals(Constant.ProjectName.CPF))//中油
                {
                    //用默认取SEQ_loan_contractform
                }
                else//国机、华能
                {
                    cdao.setUseMaxID();//取MAX_ID
                }
                //*/
                cdao.setUseMaxID();//取MAX_ID
                //得到最大的ID
                //cInfo.setId(this.getMaxContractID());
                
                //判断是否已经生成合同，生成则更新合同信息，避免多次通过审批生成多个合同的情况
                if(cInfo1 != null && cInfo1.getId() > 0){
                	lContractId = cInfo1.getId();
                	cInfo.setId(lContractId);
                	cdao.update(cInfo);
                }else{
                	cInfo.setAttachId(Env.getSystemDateTime().getTime());
                	lContractId=cdao.add(cInfo);
                }

                Log.print("========lContractId============"+lContractId);
                
                if(lContractId > 0)
                {
                	TransDiscountContractContentDao transConconDao = new TransDiscountContractContentDao();
				 	transConconDao.saveZTXContractContent(lContractId);
                
//                    if (Env.getProjectName().equals(Constant.ProjectName.CPF))//中油
//                    {
//                    	
//                    }
//                    else//国机、华能
//                    { 
//                        ContractContentDao conconDao = new ContractContentDao();
//                        strFileName = conconDao.addZTX(lContractId);
//                        
//                        ContractContentInfo CCInfo = new ContractContentInfo();
//                        CCInfo.setParentID(lContractId);
//                        CCInfo.setContractID(lContractId);
//                        CCInfo.setContractTypeID(LOANConstant.ContractType.ZTX);
//                        CCInfo.setDocName(strFileName);
//                        conconDao.saveContractContent(CCInfo);
//                    }
                //}
                
                //生成转贴现合同票据
                Collection temp = null;
                if(aInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
                {
                    Log.print("===买入==生成转贴现合同票据===start===");
                  //  TransDiscountApplyBillDAO bdao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
                    temp=bdao.findBillByTransDiscountApplyID(aInfo.getId());                      
                   
                    
                    
                    if( (temp != null) && (temp.size() > 0) )
                    {
                        Iterator it = temp.iterator();
                        while (it.hasNext() )
                        {
                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                            
                            TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
                            
                            if(abinfo != null)
                            {
								cbinfo.setCurrencyId(abinfo.getCurrencyId());
								cbinfo.setOfficeId(abinfo.getOfficeId());
                                cbinfo.setContractID(lContractId);
                                cbinfo.setAcceptPoTypeID(abinfo.getAcceptPoTypeID());
                                cbinfo.setAddDays(abinfo.getAddDays());
                                cbinfo.setAmount(abinfo.getAmount());
                                cbinfo.setBank(abinfo.getBank());
                                cbinfo.setCheckAmount(abinfo.getCheckAmount());
                                cbinfo.setCode(abinfo.getCode());
                                cbinfo.setCreate(abinfo.getCreate());
                                cbinfo.setEnd(abinfo.getEnd());
                                cbinfo.setDiscountCredenceID(abinfo.getDiscountCredenceID());
                                cbinfo.setFormerOwner(abinfo.getFormerOwner());
                                cbinfo.setIsLocal(abinfo.getIsLocal());
                                cbinfo.setOB_nDiscountCredenceID(abinfo.getOB_nDiscountCredenceID());
                                cbinfo.setSerialNo(abinfo.getSerialNo());
                                cbinfo.setStatusID(abinfo.getStatusID());
                                cbinfo.setUserName(abinfo.getUserName());
                                cbinfo.setSellStatusID(Constant.YesOrNo.NO);
                                cbinfo.setBillSourceTypeID(LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
                                cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
                                cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
                                cbinfo.setAcceptPoTypeID(abinfo.getAcceptPoTypeID());//票据类型
                                cbinfo.setDraftTypeId(abinfo.getAcceptPoTypeID());//汇票类型
                                cbinfo.setPayee(abinfo.getPayee());
                                /*
                                cbinfo.setIsLocal(abinfo.getIsLocal());
                                cbinfo.setAddDays(abinfo.getAddDays());
                                cbinfo.setCheckAmount(abinfo.getCheckAmount());
                                cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
                                cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
                                //*/
                                //cbinfo(abinfo());
                                
                                //delegation.saveBill(cbinfo);
                                    
                                //设置下一个序列号
                                cbinfo.setSerialNo(cbdao.findMaxTransDiscountContractBillSerialNo(cbinfo.getContractID())+1);
                
                                cbdao.setUseMaxID();//取MAX_ID

                                Log.print("===买入==复制票据===start===");
                                    
                                long id =cbdao.add(cbinfo);
                                                         
                                cbinfo.setId(id);
                                    
                                Log.print("===买入==复制票据===end===");
                                cbinfo.setBillSourceTypeID(abinfo.getId());
                                Log.print("===买入==复制票据关系===start===");
                                cdao.addBillRelation(cbinfo);
                                Log.print("===买入==复制票据关系===end===");
                                
                            }
                        }
                        
                    }
                    Log.print("===买入==生成转贴现合同票据===end===");
                }
                else if(aInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
                {
                    Log.print("===买出==生成转贴现合同票据关系===start===");
                    temp=this.findOutBillByTransDiscountApplyID(aInfo.getId());
                    if( (temp != null) && (temp.size() > 0) )
                    {
                        Iterator it = temp.iterator();
                        while (it.hasNext() )
                        {
                            TransDiscountApplyBillInfo abinfo = ( TransDiscountApplyBillInfo ) it.next();
                            
                            TransDiscountContractBillInfo cbinfo = new TransDiscountContractBillInfo();
                            
                            if(abinfo != null)
                            {
                                /*
                                cbinfo.setId(abinfo.getId());
                                cbinfo.setBillSourceTypeID(LOANConstant.BillSourceType.PACHASETRANSDISCOUNT);
                                //cbinfo(abinfo());
                                
                                //delegation.saveBill(cbinfo);
                                cbdao.update(cbinfo);
                                //*/

                                cbinfo.setId(abinfo.getId());
                                cbinfo.setContractID(lContractId);
                                cbinfo.setIsLocal(abinfo.getIsLocal());
                                cbinfo.setAddDays(abinfo.getAddDays());
                                cbinfo.setCheckAmount(abinfo.getCheckAmount());
                                cbinfo.setRepurchaseDate(abinfo.getRepurchaseDate());
                                cbinfo.setRepurchaseTerm(abinfo.getRepurchaseTerm());
                                
                                Log.print("===买出==生成票据关系===start===");
                                cdao.addBillRelation(cbinfo);
                                Log.print("===买出==生成票据关系===end===");
                                
                            }
                        }
                    } 
                    Log.print("===买出==生成转贴现合同票据关系===end===");
                }
              }
            }
        }
        catch (ITreasuryDAOException e)
        {
            
            e.printStackTrace();
        }
        catch (LoanException e)
        {
            
            e.printStackTrace();
        }
        catch(IException e)
        {
        	e.printStackTrace();
        	throw new IException("未关联编码设置",e);
        }
        catch (RemoteException e)
        {
            
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	  /**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long updateBillStatus(long loanID, long newStatusID)
	throws Exception {
       PreparedStatement ps = null;
       Connection conn = null;
       String strSQL = null;
       long lResult = -1;

      try {
		conn = Database.getConnection();
		strSQL = " update Cra_LoanForm set NBILLSTATUS = ? where ID = ?";
	
		ps = conn.prepareStatement(strSQL);
		ps.setLong(1, newStatusID);
		ps.setLong(2, loanID);
	
		lResult = ps.executeUpdate();
		cleanup(ps);
		cleanup(conn);
	
		if (lResult < 0) {
			log.info("update Cra_LoanForm property info error:" + lResult);
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
	
	  /**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long addAttachment(long loanID, long attachId) throws Exception {
       PreparedStatement ps = null;
       Connection conn = null;
       String strSQL = null;
       long lResult = -1;

      try {
		conn = Database.getConnection();
		strSQL = " update Cra_LoanForm set BILLATTACHID = ? where ID = ?";
	
		ps = conn.prepareStatement(strSQL);
		ps.setLong(1, attachId);
		ps.setLong(2, loanID);
	
		lResult = ps.executeUpdate();
		cleanup(ps);
		cleanup(conn);
	
		if (lResult < 0) {
			log.info("update Cra_LoanForm property info error:" + lResult);
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
	
	/**
	 * Added by xwhe, 2007/10/17
	 * 检查合同是否已被使用
	 * @param applyId 申请单id
	 * @return
	 */
	public boolean checkBillStatus(long applyId) throws LoanDAOException{
		boolean flag=false;
		long[] checkStatusId={
				BILLConstant.TransctionStatus.SUBMIT,
				BILLConstant.TransctionStatus.APPROVALING,
				BILLConstant.TransctionStatus.APPROVALED,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "select count(*) from cra_loanform c  ");
		sqlBuf.append(" where c.nbillstatus in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and c.id = " + applyId);
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
				SECConstant.ContractStatus.SAVE,
				LOANConstant.ContractStatus.CHECK,
				LOANConstant.ContractStatus.NOTACTIVE,
				LOANConstant.ContractStatus.ACTIVE,
				LOANConstant.ContractStatus.APPROVALING,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "select count(*) from loan_contractform s");
		sqlBuf.append(" where s.nstatusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.nloanid = " + applyId);
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
	public boolean cancelContract(long applyId) throws LoanDAOException{
		boolean flag=false;
		long[] checkStatusId={
				LOANConstant.ContractStatus.SAVE,
				};
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append( "update  loan_contractform s");
		sqlBuf.append(" set s.nstatusid = "+LOANConstant.ContractStatus.CANCEL);
		sqlBuf.append(" where s.nstatusid in (");
		for(int i=0;i<checkStatusId.length;i++){
			sqlBuf.append(checkStatusId[i]);
			if(i!=(checkStatusId.length-1)){
				sqlBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlBuf.append(" and s.nloanid = " + applyId);
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
}
