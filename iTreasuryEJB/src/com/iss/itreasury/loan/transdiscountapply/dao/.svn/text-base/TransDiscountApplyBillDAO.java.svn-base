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
import java.sql.*;
import java.util.*;

//import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.bill.draft.dao.DiscountContractBillDao;
import com.iss.itreasury.bill.draft.dao.TransDraftInDAO;
import com.iss.itreasury.bill.draft.dataentity.DiscountContractBillInfo;
import com.iss.itreasury.bill.draft.dataentity.TransDraftInInfo;
import com.iss.itreasury.bill.draft.dataentity.assemble.DraftStorageAssembleInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.bill.util.UtilOperation;
import com.iss.itreasury.bill.venture.dao.BlackBillDao;
import com.iss.itreasury.bill.venture.dataentity.BlackBillInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.SelectBillInfo;
//中油
//import com.iss.cpf.util.*;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountApplyBillDAO extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
    public TransDiscountApplyBillDAO(String strTable)
    {
        //super("Loan_DiscountFormBill");
        //super("DiscountBill"); //中油
        super(strTable,true);
    }
    public static void main(String[] args)
        throws java.rmi.RemoteException, LoanException
    {
        TransDiscountApplyBillDAO dao = new TransDiscountApplyBillDAO("cra_transdiscountbill");
        TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();
        /*
        try
        {
            info = (TransDiscountApplyBillInfo) dao.findByID(3176, info.getClass());
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //*/
        /*
        try
        {
            Collection c = null;
            c = dao.findBillByTransDiscountID(137);
            if(c != null)
            {
                System.out.println("c.size = "+c.size());
            }
            else
            {
                System.out.println("c is null");
            }
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
        //*/

        try
        {
            info.setStatusID(Constant.RecordStatus.VALID);
            info.setAmount(100);
            info.setCode("001");
            info.setLoanID(136);
            info.setAddDays(2);
            info.setBank("工行");
            info.setIsLocal(2);
            info.setCheckAmount(100.00);
            info.setUserName("222222");
            info.setDiscountCredenceID(-1);
            info.setDiscountTypeID(1);
            
            if(info.getId() < 0)
            {
                dao.setUseMaxID();
                info.setSerialNo(dao.findMaxTransDiscountApplyBillSerialNo(info.getLoanID())+1);
                Log.print("getSerialNo="+info.getSerialNo());
                long id = dao.add(info);
                info.setId(id);
            }
            else
            {
                dao.update(info);
            }//*/
            Log.print("id:"+info.getId());
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     *申请书的单笔查询操作
     * @throws ITreasuryDAOException
    */
    public TransDiscountApplyBillInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
    {
        ResultSet rs = null;
        String strSQL = "";
        TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();

        try
        {
            initDAO();
            strSQL = " select * from  " + this.strTableName + " where id=" + lID;
            
            Log.print(strSQL);
            prepareStatement(strSQL);
            rs = executeQuery();
            
            if (rs != null && rs.next())
            {
                info.setId(rs.getLong("id"));
                info.setLoanID(rs.getLong("nLoanId"));
                //modified by qhzhou 2007-10-24
                long officeId=rs.getLong("nOfficeID");
                info.setOfficeId(officeId);
                info.setCurrencyId(rs.getLong("nCurrencyID"));         
           
                info.setSerialNo(rs.getLong("nSerialNo"));
                info.setUserName(rs.getString("sUserName"));
                info.setBank(rs.getString("sBank"));
                info.setIsLocal(rs.getLong("nIsLocal"));
                info.setCreate(rs.getTimestamp("dtCreate"));
                info.setEnd(rs.getTimestamp("dtEnd"));
                info.setCode(rs.getString("sCode"));
                info.setAmount(rs.getDouble("mAmount"));
                info.setStatusID(rs.getLong("nStatusID"));
                info.setAddDays(rs.getLong("nAddDays"));
                info.setDiscountCredenceID(
                    rs.getLong("nDiscountCredenceID"));
                info.setOB_nDiscountCredenceID(
                    rs.getLong("OB_nDiscountCredenceID"));
                info.setAcceptPoTypeID(rs.getLong("nAcceptPoTypeID"));
                info.setFormerOwner(rs.getString("sFormerOwner"));
                info.setCheckAmount(rs.getDouble("mCheckAmount"));
                info.setAccrual(info.getAmount()-info.getCheckAmount());
                info.setRepurchaseDate(rs.getTimestamp("dtRepurchaseDate"));
                info.setRepurchaseTerm(rs.getLong("nRepurchaseTerm"));
                
                //添加收款人信息 add by gdlian 2011-12-26 BUG #10872 
                info.setPayee(rs.getString("payee"));
                    
            }
            
            finalizeDAO();
            
        }        
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ITreasuryDAOException e)
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
     *申请书下的票据查询操作
     * @throws ITreasuryDAOException
    */
    public Collection findBillByTransDiscountApplyID(long lTransDiscountApplyID)
        throws java.rmi.RemoteException, LoanException
    {
        Vector v = new Vector();
        String strSQL = "";
        long lRecordCount = 0;
        double dTotalAmount = 0;
        double dTotalCheckAmount = 0;
        double totalAccrual = 0;
        try
        {
            initDAO();
            try
            {
                TransDiscountApplyBillDAO dao = new TransDiscountApplyBillDAO(this.strTableName);

                strSQL =
                    " select count(*),sum(mAmount),sum(mCheckAmount) from  "
                        + this.strTableName
                        + " where 1 = 1 "
                        + " and nStatusId = "
                        + Constant.RecordStatus.VALID
                        + " and nLoanId = "
                        + lTransDiscountApplyID;
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                while (rs != null && rs.next())
                {
                    lRecordCount = rs.getLong(1);
                    dTotalAmount = rs.getDouble(2);
                    dTotalCheckAmount = rs.getDouble(3);
                    totalAccrual = dTotalAmount - dTotalCheckAmount;
                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                
                strSQL =
                    " select id from  "
                        + this.strTableName
                        + " where 1 = 1 "
                        + " and nStatusId = "
                        + Constant.RecordStatus.VALID
                        + " and nLoanId = "
                        + lTransDiscountApplyID
                        + " order by nSerialNo ";
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                rs = executeQuery();
                while (rs != null && rs.next())
                {
                    TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();
                    
                    info = (TransDiscountApplyBillInfo)dao.findByID(rs.getLong("id"));//,info.getClass());
                    //info.setAccrual(info.getAmount()-info.getCheckAmount());

                    info.setTotalBillAmount(dTotalAmount);
                    info.setRecordCount(lRecordCount);
                    info.setTotalRealAmount(dTotalCheckAmount);
                    info.setTotalAccrual(totalAccrual);
                    
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
     *申请书下的票据删除操作
     * @throws ITreasuryDAOException
     * by gangwang
    */
    public void deleteAllBill(SelectBillInfo selectBillInfo)
    throws java.rmi.RemoteException, LoanException
{
    long[] lIDList = selectBillInfo.getAllBillID();
    long lTransDiscountApplyID = selectBillInfo.getTransDiscountApplyID();
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
                    lBillID = lIDList[i];
                    //get TransDiscountApplyID  TODO
                    TransDiscountApplyBillInfo tdbinfo = new TransDiscountApplyBillInfo();
                    tdbinfo =
                        (TransDiscountApplyBillInfo) findByID(lBillID,
                            tdbinfo.getClass());
                    lTransDiscountApplyID = tdbinfo.getLoanID();
                    log4j.info(
                        "lTransDiscountApplyID is: " + lTransDiscountApplyID);
                    //get SerialNo 
                    nSerialNo = tdbinfo.getSerialNo();
                    log4j.info("SerialNo is: " + nSerialNo);
                    
                    //更新状态为无效，逻辑删除
                    //delete(lIDList[i]);
                    tdbinfo.setId(lBillID);
                    tdbinfo.setStatusID(Constant.RecordStatus.INVALID);
                    update(tdbinfo);
                    
                    //更新序列号
                    updateBillSerialNo(lTransDiscountApplyID, nSerialNo);
//                    //取申请记录状态
//                    TransDiscountApplyDAO tdDAO = new TransDiscountApplyDAO("cra_LoanForm");
//                    TransDiscountApplyInfo tdinfo = new TransDiscountApplyInfo();
//                    tdinfo =
//                        (TransDiscountApplyInfo) tdDAO.findByID(
//                            lTransDiscountApplyID,
//                            tdinfo.getClass());
//                    nStatusID = tdinfo.getStatusId();
//                    // TODO 
//                    //if (nStatusID == Notes.CODE_DISCOUNT_STATUS_EXAMINE)
//                    //if (nStatusID == LOANConstant.LoanStatus.CHECK)
//                   // {
//                        //更新申请记录状态
//                    	tdinfo.setIsChoose(0);
//                        tdinfo.setId(lTransDiscountApplyID);
//                        tdinfo.setBillStatus(BILLConstant.TransctionStatus.OTHER);
//                       // tdinfo.setStatusId(LOANConstant.LoanStatus.SUBMIT);
//                        tdDAO.update(tdinfo);
//                        //删除以前的审核纪录
//                   // }
                }
            }
//          取申请记录状态
            TransDiscountApplyDAO tdDAO = new TransDiscountApplyDAO("cra_LoanForm");
            TransDiscountApplyInfo tdinfo = new TransDiscountApplyInfo();
            tdinfo =
                (TransDiscountApplyInfo) tdDAO.findByID(
                    lTransDiscountApplyID,
                    tdinfo.getClass());
            nStatusID = tdinfo.getStatusId();
            //更新申请记录状态
        	tdinfo.setIsChoose(0);
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
     *申请书下的票据删除操作
     * @throws ITreasuryDAOException
    */
    public void deleteBill(SelectBillInfo selectBillInfo)
        throws java.rmi.RemoteException, LoanException
    {
        long[] lIDList = selectBillInfo.getAllBillID();
        long lTransDiscountApplyID = selectBillInfo.getTransDiscountApplyID();
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
                        lBillID = lIDList[i];
                        //get TransDiscountApplyID  TODO
                        TransDiscountApplyBillInfo tdbinfo = new TransDiscountApplyBillInfo();
                        tdbinfo =
                            (TransDiscountApplyBillInfo) findByID(lBillID,
                                tdbinfo.getClass());
                        lTransDiscountApplyID = tdbinfo.getLoanID();
                        log4j.info(
                            "lTransDiscountApplyID is: " + lTransDiscountApplyID);
                        //get SerialNo 
                        nSerialNo = tdbinfo.getSerialNo();
                        log4j.info("SerialNo is: " + nSerialNo);
                        
                        //更新状态为无效，逻辑删除
                        //delete(lIDList[i]);
                        tdbinfo.setId(lBillID);
                        tdbinfo.setStatusID(Constant.RecordStatus.INVALID);
                        update(tdbinfo);
                        
                        //更新序列号
                        updateBillSerialNo(lTransDiscountApplyID, nSerialNo);
                        //取申请记录状态
                        TransDiscountApplyDAO tdDAO = new TransDiscountApplyDAO("Loan_LoanForm");
                        TransDiscountApplyInfo tdinfo = new TransDiscountApplyInfo();
                        tdinfo =
                            (TransDiscountApplyInfo) tdDAO.findByID(
                                lTransDiscountApplyID,
                                tdinfo.getClass());
                        nStatusID = tdinfo.getStatusId();
                        // TODO 
                        //if (nStatusID == Notes.CODE_DISCOUNT_STATUS_EXAMINE)
                        if (nStatusID == LOANConstant.LoanStatus.CHECK)
                        {
                            //更新申请记录状态
                            tdinfo.setId(lTransDiscountApplyID);
                            tdinfo.setStatusId(LOANConstant.LoanStatus.SUBMIT);
                            tdDAO.update(tdinfo);
                            //删除以前的审核纪录
                        }
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
     *更新票据序列号操作
     *批量
     * @throws ITreasuryDAOException
    */
    private void updateBillSerialNo(long lTransDiscountApplyID, long nSerialNo)
        throws java.rmi.RemoteException, LoanException
    {
        try
        {
            initDAO();
            String strSQL = "";
            if (lTransDiscountApplyID > 0 && nSerialNo > 0)
            {
                log4j.info("更新序列号");
                strSQL =
                    " update "
                        + this.strTableName
                        + " set nSerialNo=nSerialNo-1 "
                        + " where nSerialNo > "
                        + nSerialNo
                        + "   and nLoanID= "
                        + lTransDiscountApplyID;
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                executeUpdate();
            }
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
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
     * 生成票据编号
     * 操作数据库表
     * @param     long     lTypeID            贷款种类(1-12)见Notes.java,贴现=13需额外处理
     * @return    String   sCode         信贷申请书编号
     **/
    public long findMaxTransDiscountApplyBillSerialNo(long lTransDiscountApplyID)
        throws java.rmi.RemoteException, LoanException
    {
        long nSerialNo = -1;
        String strSQL = "";
        try
        {
            initDAO();
            try
            {
                strSQL =
                    " select nvl(max(nSerialNo),0) nSerialNo from  "
                        + this.strTableName
                        + " where 1 = 1 "
                        + " and nStatusId = "
                        + Constant.RecordStatus.VALID
                        + " and nLoanId = "
                        + lTransDiscountApplyID;
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                if (rs != null && rs.next())
                {
                    nSerialNo = rs.getLong(1);
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
    
        return nSerialNo;
    }
    /*
     * 中油  删除以前的审核纪录
     * 批量
     */
    private void deleteReviewOpinion(long nReviewTypeID, long nReviewContentID)
        throws java.rmi.RemoteException, LoanException
    {
        String strSQL = "";
        try
        {
            if (nReviewTypeID > 0 && nReviewContentID > 0)
            {
                log4j.info("删除以前的审核纪录");
                strSQL =
                    " delete from reviewOpinion "
                        + " where nReviewTypeID = "
                        + nReviewTypeID
                        + " and nReviewContentID = "
                        + nReviewContentID;
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                executeUpdate();
            }
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
	 * 得到转贴现入库信息
	 * @param id 票据id
	 * @param draftOperationType 操作类型（转贴现）
	 * @param draftInOrOut 入、出库类型
	 * @return 票据入库对应需要添加的数据集
	 * @throws LoanDAOException
	 */
    public Collection in_depot(long lApplyID,long lTransDiscountTypeID,long draftOperationType,long draftInOrOut) 
	throws java.rmi.RemoteException, LoanDAOException
	{
		Vector v=new Vector();
		String strSQL = "";
		
		try
		{
			initDAO();
			try
	        {
	            strSQL ="select b.ncurrencyid,b.nofficeid,b.ninputuserid,b.scontractcode,c.id,c.dtcreate,c.dtend,c.mamount,c.scode,a.dtdiscountdate,c.sformerowner sfformerowner, b.nborrowclientid borrowclientid,a.ndiscounttypeid discounttypeid,a.nbankacceptpo bankcount,a.nbizacceptpo bizcount,a.ninputuserid inputuserid";
	            strSQL += " from Cra_loanform a,loan_contractform b,loan_discountcontractbill c";
	            strSQL += " where a.id=b.nloanid and c.ncontractid=b.id";
	            strSQL += " and b.ntypeid=" + draftOperationType;
	            strSQL += " and a.id=" + lApplyID;
	            strSQL += " and b.nstatusid <> "+LOANConstant.ContractStatus.CANCEL;
	            strSQL += " and c.nstatusid = "+Constant.RecordStatus.VALID;
	            
	            log4j.debug(strSQL);
	            System.out.println("入出库信息查询sql："+strSQL);
	            prepareStatement(strSQL);
	            ResultSet rs = executeQuery();
	            while (rs != null && rs.next())
	            {
	            	// 设置入库信息transDraftInInfo
	            	TransDraftInInfo transDraftInInfo = new TransDraftInInfo();
	            	transDraftInInfo.setCurrencyID(rs.getLong("ncurrencyid"));
					transDraftInInfo.setOfficeID(rs.getLong("nofficeid"));
					transDraftInInfo.setInputUserID(rs.getLong("ninputuserid"));
					transDraftInInfo.setInDate(rs.getTimestamp("dtcreate"));
					UtilOperation utilOperation=new UtilOperation();
					if(draftOperationType==LOANConstant.LoanType.ZTX){
						//modify by xwhe 2007-12-6 暂时只能这样改
						transDraftInInfo.setTransCode(utilOperation.getNewDraftTransactionNo(transDraftInInfo.getOfficeID(),transDraftInInfo.getCurrencyID(),1));	
						long lBorrowClientId = rs.getLong("borrowclientid");
						String sBorrowClientName = NameRef.getCounterpartNameByID(lBorrowClientId);
						transDraftInInfo.setFormerOwner(sBorrowClientName);//直接前手，交易对手
						if((transDraftInInfo.getAbstract() == null || transDraftInInfo.getAbstract().equals("")) && draftInOrOut == LOANConstant.TransDiscountInOrOut.IN){
							transDraftInInfo.setAbstract("转贴现买入,自动入库");
						}
						transDraftInInfo.setCurrentHolder(Env.getClientName());//当前持票人“方正财务公司”
					}
					else
					{
					transDraftInInfo.setTransCode(utilOperation.getNewDraftTransactionNo(transDraftInInfo.getOfficeID(),transDraftInInfo.getCurrencyID(),draftOperationType));
					}
					transDraftInInfo.setStatusID(Constant.RecordStatus.VALID);
					transDraftInInfo.setTransTypeID(BILLConstant.DraftOperationType.DraftIn);
					transDraftInInfo.setInContractCode(rs.getString("scontractcode"));
					// 加入转贴现买入的票据来源类型
					if(lTransDiscountTypeID < 0){
						lTransDiscountTypeID = rs.getLong("discounttypeid");
					}
					// 买入买断：转贴现买入（入库）
					// 买入回购：逆回购买入（入库）
					if(lTransDiscountTypeID == LOANConstant.TransDiscountType.BUYBREAK)
					{
						transDraftInInfo.setBillSoureID(BILLConstant.BillSourceType.REDISCOUNTBUYIN);
					}
					if(lTransDiscountTypeID == LOANConstant.TransDiscountType.REPURCHASE)
					{
						transDraftInInfo.setBillSoureID(BILLConstant.BillSourceType.OBREPURCHASEBUYIN);
					}
					transDraftInInfo.setInDate(DataFormat.getDateTime(DataFormat.formatDate(rs.getTimestamp("dtdiscountdate"))));//入库日期，贴现日
				//	transDraftInInfo.setInputDate(DataFormat.getDateTime(DataFormat.formatDate(Env.getSystemDateTime())));//录入日期，贴现日
					//transDraftInInfo.setBillSoureID(BILLConstant.BillSourceType.REDISCOUNTBUYIN);
					transDraftInInfo.setFFormerOwner(rs.getString("sfformerowner"));
					transDraftInInfo.setInputUserID(rs.getLong("inputuserid"));
					//transDraftInInfo.setInputDate(DataFormat.getDateTime(DataFormat.formatDate(rs.getTimestamp("dtdiscountdate"))));//入库日期，贴现日
					
					// 设置票据信息discountContractBillInfo
					DiscountContractBillInfo discountContractBillInfo = new DiscountContractBillInfo();
					discountContractBillInfo.setId(rs.getLong("id"));
					discountContractBillInfo.setNStorageStatusID(draftInOrOut);
					discountContractBillInfo.setNInputUserID(transDraftInInfo.getInputUserID());
					discountContractBillInfo.setNOfficeID(transDraftInInfo.getOfficeID());
					discountContractBillInfo.setNCurrencyID(transDraftInInfo.getCurrencyID());
					discountContractBillInfo.setNDraftTypeID(transDraftInInfo.getTransTypeID());
					discountContractBillInfo.setNStatusID(Constant.RecordStatus.VALID);
					discountContractBillInfo.setNModuleSourceID(Constant.ModuleType.BILL);
					discountContractBillInfo.setNQueryStatusID(BILLConstant.BillQuery.NOTQUERY);	
					discountContractBillInfo.setDtCreate(rs.getTimestamp("dtcreate"));
					discountContractBillInfo.setDtEnd(rs.getTimestamp("dtend"));
					discountContractBillInfo.setMAmount(rs.getDouble("mamount"));
					discountContractBillInfo.setSCode(rs.getString("scode"));
					
					DraftStorageAssembleInfo draftStorageAssembleInfo=new DraftStorageAssembleInfo();
					draftStorageAssembleInfo.setDiscountContractBillInfo(discountContractBillInfo);
					draftStorageAssembleInfo.setTransDraftInInfo(transDraftInInfo);
					
					v.add(draftStorageAssembleInfo);
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
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LoanDAOException(" 入出库有错误! ",e);
		}
		
		return v;
	}
	
	/**
	 * 入库操作
	 * @param draftStorageAssembleInfo
	 * @return
	 * @throws RemoteException
	 * @throws LoanDAOException
	 */
	public long saveDraftIn(DraftStorageAssembleInfo draftStorageAssembleInfo) throws RemoteException,LoanDAOException{
    	DiscountContractBillDao billDao=new DiscountContractBillDao();
		TransDraftInDAO dao=new TransDraftInDAO();
		long ReturnId=-1;//-1表示更新操作
		System.out.println("---------------进入保存入库信息saveDraftIn--------------");
		//取得保存所需实体
		DiscountContractBillInfo discountContractBillInfo = new DiscountContractBillInfo();
		TransDraftInInfo transDraftInInfo=null;
		if(draftStorageAssembleInfo.getDiscountContractBillInfo()!=null&&draftStorageAssembleInfo.getTransDraftInInfo()!=null)
		{
			discountContractBillInfo=draftStorageAssembleInfo.getDiscountContractBillInfo();
			transDraftInInfo=draftStorageAssembleInfo.getTransDraftInInfo();
		}
		else
		{
			System.out.println("集合类DraftStorageAssembleInfo，内容不全！");
			//throw new BillException("Bill_E036","DraftStorageAssembleInfo",null);
		}
		
		try {
			// 调用票据风险管理模块方法判断当前票据是否在黑名单中
			BlackBillInfo blackBillInfo=new BlackBillInfo();
			BlackBillDao blackBillDao=new BlackBillDao();
			
			blackBillInfo.setBillTypeID(discountContractBillInfo.getNDraftTypeID());
			blackBillInfo.setBillCode(discountContractBillInfo.getSCode());
			blackBillInfo.setStatusID(Constant.RecordStatus.VALID);
			blackBillInfo.setOfficeID(discountContractBillInfo.getNOfficeID());
			blackBillInfo.setCurrencyID(discountContractBillInfo.getNCurrencyID());
			if(blackBillDao.isBillInBlackList(blackBillInfo))
			{
				//Bill_E028=该票据在黑名单中，不能?！
				throw new BillException("Bill_E028","入库",null);
			}
			
			//新增票据保存入库
        	//Timestamp tsNow=Env.getSystemDateTime();
			//修改票据自动入库，编号重复问题。将录入时间改为开机日 modify by wangzhen
        	Timestamp tsNow= Env.getSystemDate(transDraftInInfo.getOfficeID(),transDraftInInfo.getCurrencyID());
        	transDraftInInfo.setBillID(discountContractBillInfo.getId());
			transDraftInInfo.setInputDate(tsNow);
			ReturnId=dao.add(transDraftInInfo);
			//已存在票据做更新操作
			DiscountContractBillInfo updateBill = new DiscountContractBillInfo();
			updateBill.setId(discountContractBillInfo.getId());
			updateBill.setNStorageTransID(ReturnId);
			updateBill.setDtModifyDate(tsNow);
			billDao.update(updateBill);
			
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				//throw new BillException("Bill_E032",e);
			} catch (BillException ex){
				ex.printStackTrace();
				//throw new BillException(ex,ctx);
			}
			return ReturnId;
    }
	
	 /**
     *申请书下的票据金额查询
     * @throws ITreasuryDAOException
    */
    public double findBillAmountByID(long lTransDiscountApplyID)
        throws java.rmi.RemoteException, LoanException
    {

        String strSQL = "";
        double dTotalAmount = 0;
        try
        {
            initDAO();
            try
            {
                TransDiscountApplyBillDAO dao = new TransDiscountApplyBillDAO(this.strTableName);

                strSQL =
                    " select count(*),sum(mAmount),sum(mCheckAmount) from  "
                        + this.strTableName
                        + " where 1 = 1 "
                        + " and nStatusId = "
                        + Constant.RecordStatus.VALID
                        + " and nLoanId = "
                        + lTransDiscountApplyID;
                log4j.debug(strSQL);
                prepareStatement(strSQL);
                ResultSet rs = executeQuery();
                while (rs != null && rs.next())
                {
                    dTotalAmount = rs.getDouble(2);
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
        
        return dTotalAmount;
    }
    /**
     *卖出申请书下的已选票据 查询操作
    */
    public double findOutBillAmountByID(long lTransDiscountApplyID)
        throws java.rmi.RemoteException, LoanException
    {
        
        String strSQL = "";       
        double dTotalAmount = 0;
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
                    dTotalAmount = rs.getDouble(2);                  
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
        return dTotalAmount;
    }
}
