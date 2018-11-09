package com.iss.itreasury.settlement.transcommission.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.transcommission.dao.Sett_TransCommissionDAO;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 *
 * @name: TransCommissionEJB
 * @description:手续费收取
 * @author: gqfang
 * @create: 2005-8-25
 *
 */
public class TransCommissionEJB implements SessionBean
{

    private javax.ejb.SessionContext mySessionCtx = null;
    
    final static long serialVersionUID = 3206093459760846163L;
    
    private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
    
    private static  Object lockObj = new Object();  //静态
  

    /**
     * ejbActivate method comment
     * @exception java.rmi.RemoteException 异常说明。
     */
    public void ejbActivate() throws java.rmi.RemoteException
    {
    }
    /**
     * ejbCreate method comment
     * @exception javax.ejb.CreateException 异常说明。
     * @exception java.rmi.RemoteException 异常说明。
     */
    public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
    {
    }
    /**
     * ejbPassivate method comment
     * @exception java.rmi.RemoteException 异常说明。
     */
    public void ejbPassivate() throws java.rmi.RemoteException
    {
    }
    /**
     * ejbRemove method comment
     * @exception java.rmi.RemoteException 异常说明。
     */
    public void ejbRemove() throws java.rmi.RemoteException
    {
    }
    /**
     * getSessionContext method comment
     * @return javax.ejb.SessionContext
     */
    public javax.ejb.SessionContext getSessionContext()
    {
        return mySessionCtx;
    }
    /**
     * setSessionContext method comment
     * @param ctx javax.ejb.SessionContext
     * @exception java.rmi.RemoteException 异常说明。
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
    {
        mySessionCtx = ctx;
    }
    
   
    /**
     *@description: 功能：手续费结算处理 
     *              针对的业务：银行收款，银行付款，特殊业务处理
     *              详细实现描述：在结算手续费的时候，系统执行自动复核。
     *              就执行过程来说大致划分为保存和复核两部分:
     *              保存操作：
     *                     1.生成交易号
     *                     2.保存手续费交易信息到表sett_TransCommission,表中记录状态为1(等于暂存)
     *                     3.保存账簿信息：accountOperation.saveCommissionAccountDetails(),
     *                       (1)增加对应活期账户的累计未复核金额
     *                     4.更新sett_TransCommission中的记录的状态为2(未复核)
     *              复核操作：
     *                     1.调用accountOperation.checkCommissionAccountDetails():
     *                       (1)在该方法中调用withDrawCurrent()扣除活期账户的子账户的金额；
     *                       (2)在该方法中调用总账的 generateGLEntry()方法,生成会计账。
     *                     2.更新sett_TransCommission中的记录的状态为3（已复核）。
     *                     3.将手续费的交易号回填到表sett_TransCurrentDeposit 或 sett_TransSpecailOperation。
     *                     
     *@return long
     *@throws RemoteException
     *@throws IRollbackException
     */
    public long commissionSettlement( Collection coll ) throws RemoteException,IRollbackException
    {
    	synchronized(lockObj){
        long returnValue = -1; //返回值
        
        Collection gatherColl = null;//按账户汇总后的结果集
       
        //工具操作接口类
        UtilOperation utilOperation = new UtilOperation();
        
        //账簿操作接口类 
        AccountBookOperation accountBookOperation = new AccountBookOperation();
        
        //手续费DAO类
        Sett_TransCommissionDAO dao = new Sett_TransCommissionDAO();
        try
        {
            
            log.debug("part1:保存手续费数据到 sett_TransCommission--开始");
            partSave(coll);
            log.debug("part1:保存手续费数据到 sett_TransCommission--结束");
            
            
            log.debug("part2:按账户汇总结果集--开始");
            gatherColl = generateGatherInfo(coll);
            log.debug("part2:按账户汇总结果集--开始");
            
            
            
            Iterator gatherIterator = null;
            
            TransCommissionInfo info = null;
            
            if( gatherColl != null )
            {
                gatherIterator = gatherColl.iterator();
            }
            
            while( gatherIterator != null && gatherIterator.hasNext() )
            {
                info = (TransCommissionInfo)gatherIterator.next();
                
                
                log.debug("Part3:生成交易号--开始");
                String transNo = utilOperation.getNewTransactionNo( info.getOfficeId() , info.getCurrencyId(),info.getTransactionTypeId());
                info.setTransNo( transNo );
                log.debug("Part3:--结束------新交易号是:" + transNo + "--------");
                
                
                
                log.debug("part4:保存账簿信息--开始");
                log.debug(UtilOperation.dataentityToString(info));
                accountBookOperation.saveCommissionAccountDetails(info);
                log.debug("part4:保存账簿信息--结束");
                
                log.debug("Part5:调用accountOperation.checkCommissionAccountDetails()--开始");
                info.setStatusId(SETTConstant.TransactionStatus.SAVE);
                accountBookOperation.checkCommissionAccountDetails(info);
                log.debug("Part5:调用accountOperation.checkCommissionAccountDetails()--结束");
                
                
                log.debug("Part6:将交易号，状态回填到sett_TransCommission--开始");
                info.setTransNo(transNo);
                updateToTransCommission(info);
                log.debug("Part6:将交易号，状态回填到sett_TransCommission--结束");
                
                log.debug("part7:将手续费的交易号回填到表sett_TransCurrentDeposit 或 sett_TransSpecailOperation--开始");
                info.setTransNo(transNo);
                updateToMain( coll ,info);
                log.debug("part7:将手续费的交易号回填到表sett_TransCurrentDeposit 或 sett_TransSpecailOperation--结束");
      
                
                returnValue = 1;
                
            }
            
           
        }
		//modified by mzh_fu 2007/05/011
//        catch (RemoteException e)
//        {
//            throw e;
//        }
//        catch (IRollbackException e)
//        {
//            throw e;
//        }
        catch (Exception e)
        {
            throw new IRollbackException(mySessionCtx, e.getMessage(), e);
        }
        
        return returnValue;
    	}
    }

    
    /**
     *@description:将手续费的交易号回填到表sett_TransCurrentDeposit
     *void
     */
    private long updateToCurrent( TransCommissionInfo info ) throws Exception
    {
    	synchronized(lockObj){
        long lReturn = -1;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
           
                conn = Database.getConnection();
                
                String sql = " update sett_transcurrentdeposit  set sCommissionTransNo = '"+ info.getTransNo() +"' WHERE ID = " + info.getMainTransactionId();
                
                System.out.println(" ======= updateToCurrent \n"+sql);
                
                ps = conn.prepareStatement(sql);

                int n = ps.executeUpdate();
                if (n > 0)
                {
                    lReturn = info.getMainTransactionId();
                }
        }
        finally
        {
            if( ps != null )
            {
                ps.close();
                ps = null;
            }
            if( conn != null )
            {
                conn.close();
                conn = null;
            }
        }

        return lReturn;
    	}
    
    }
    
    /**
     *@description:将手续费的交易号回填到表sett_TransSpecailOperation。
     *void
     */
    private long updateToSpecial( TransCommissionInfo info ) throws Exception
    {
    	synchronized(lockObj){
        long lReturn = -1;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
           
                conn = Database.getConnection();
                
                String sql = " update sett_transspecialoperation  set sCommissionTransNo = '"+ info.getTransNo() +"' WHERE ID = " + info.getMainTransactionId();
                
                System.out.println(" ======= updateToSpecial \n"+sql);
                
                ps = conn.prepareStatement(sql);

                int n = ps.executeUpdate();
                if (n > 0)
                {
                    lReturn = info.getMainTransactionId();
                }
        }
        finally
        {
            if( ps != null )
            {
                ps.close();
                ps = null;
            }
            if( conn != null )
            {
                conn.close();
                conn = null;
            }
        }

        return lReturn;
    	}
    
    }
    
    /**
     *@description: 将Collection的数据依次插入到表sett_TransCommission中
     *void
     *@param Collection
     */
    private void partSave( Collection coll ) throws RemoteException,IRollbackException
    {
    	synchronized(lockObj){
        //手续费DAO类
        Sett_TransCommissionDAO dao = new Sett_TransCommissionDAO();
        
        Iterator iterator = null;
        
        TransCommissionInfo info = null;
        
        if( coll != null )
        {
            iterator = coll.iterator();
        }
        try
        {
        
            while( iterator != null && iterator.hasNext() )
            {
                info = (TransCommissionInfo)iterator.next();
               
                info.setStatusId(SETTConstant.TransactionStatus.TEMPSAVE);
                info.setTransNo(null);
                info.setId( dao.add(info) );
                
            }
        }
        catch (Exception e)
        {
            throw new IRollbackException(mySessionCtx, e.getMessage(), e);
        }
    	}
    }
    
    
    /**
     *@description:生成汇总的结果集
     *Collection
     *@param coll
     *@return
     */
    private Collection generateGatherInfo( Collection coll )
    {
        Vector vector = new Vector();
        Iterator iterator = null;
        long size = -1;
        
        if( coll != null )
        {
            iterator = coll.iterator();
            size = coll.size();
        }
        
        long gatherAccountId = -1;
        TransCommissionInfo gatherInfo = null;
        TransCommissionInfo info = null;
        double sumCommissionAmount = 0.00;
        int i = 0;
        
        while( iterator != null && iterator.hasNext() )
        {
            i++;
            
            info = (TransCommissionInfo)iterator.next();
            
            if( info.getAccountId() == gatherAccountId || gatherAccountId == -1 )
            {
                //sumCommissionAmount += info.getCommissionAmount();
            	sumCommissionAmount += info.getRebatecommissionAmount();
            	
                gatherAccountId = info.getAccountId();
            }
            else
            {
                gatherInfo = new TransCommissionInfo();
                
                gatherInfo.setCommissionAmount(sumCommissionAmount);
                gatherInfo.setAccountId(gatherAccountId);
                gatherInfo.setCurrencyId(info.getCurrencyId());
                gatherInfo.setOfficeId(info.getOfficeId());
                gatherInfo.setExecuteDate(info.getExecuteDate());
                gatherInfo.setInputUserId(info.getInputUserId());
                gatherInfo.setInterestStartDate(info.getInterestStartDate());
                gatherInfo.setTransactionTypeId(SETTConstant.TransactionType.COMMISSION);
                
                vector.add(gatherInfo);
                
                //sumCommissionAmount = info.getCommissionAmount();
                sumCommissionAmount = info.getRebatecommissionAmount();
                
                gatherAccountId     = info.getAccountId();
            }
            
            if( size == 1 || i == size )
            {
                gatherInfo = new TransCommissionInfo();
                
                gatherInfo.setCommissionAmount(sumCommissionAmount);
                gatherInfo.setAccountId(gatherAccountId);
                gatherInfo.setCurrencyId(info.getCurrencyId());
                gatherInfo.setOfficeId(info.getOfficeId());
                gatherInfo.setExecuteDate(info.getExecuteDate());
                gatherInfo.setInputUserId(info.getInputUserId());
                gatherInfo.setInterestStartDate(info.getInterestStartDate());
                gatherInfo.setTransactionTypeId(SETTConstant.TransactionType.COMMISSION);
                
                vector.add(gatherInfo);
            }
        }
        
        return vector;
    }
    
    /**
     *@description:将交易号，状态回填到sett_TransCommission
     *void
     *@param info
     */
    private long updateToTransCommission( TransCommissionInfo info )  throws Exception
    {
    	synchronized(lockObj){
        long lReturn = -1;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
           
                conn = Database.getConnection();
                
                String sql = " update sett_transcommission  set transNo = '"+ info.getTransNo() +"'" + 
                             " , statusId = " +SETTConstant.TransactionStatus.CHECK + 
                             " WHERE accountId = " + info.getAccountId() + 
                             " and  transNo is null ";
                
                
                System.out.println(" ======= update To sett_transcommission \n"+sql);
                
                ps = conn.prepareStatement(sql);

                int n = ps.executeUpdate();
                if (n > 0)
                {
                    lReturn = info.getAccountId();
                }
        }
        finally
        {
            if( ps != null )
            {
                ps.close();
                ps = null;
            }
            if( conn != null )
            {
                conn.close();
                conn = null;
            }
        }

        return lReturn;
    	}
    
    }
    
    /**
     *@description: 将手续费的交易号回填到表sett_TransCurrentDeposit 或 sett_TransSpecailOperation
     *void
     *@param coll
     *@param info
     *@throws Exception
     */
    private void updateToMain(Collection coll,TransCommissionInfo outInfo) throws Exception
    {
    synchronized(lockObj){
        Iterator it = null;
        TransCommissionInfo innerInfo = null;
        if( coll != null )
        {
            it = coll.iterator();
        }
        
        while( it != null && it.hasNext() )
        {
            innerInfo = (TransCommissionInfo)it.next();
            
            if( innerInfo.getAccountId() == outInfo.getAccountId() &&
                (  innerInfo.getMainTransactionType() == SETTConstant.TransactionType.BANKPAY               || 
                   innerInfo.getMainTransactionType() == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY ||
                   innerInfo.getMainTransactionType() == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT ||
                   innerInfo.getMainTransactionType() == SETTConstant.TransactionType.BANKRECEIVE           ||
                   innerInfo.getMainTransactionType() == SETTConstant.TransactionType.BANKRECEIVE_GATHERING ||
                   innerInfo.getMainTransactionType() == SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT ||
                   innerInfo.getMainTransactionType() == SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT
                )
              )
            {
                innerInfo.setTransNo(outInfo.getTransNo());
                updateToCurrent(innerInfo);
            }
            else if ( innerInfo.getAccountId() == outInfo.getAccountId() && innerInfo.getMainTransactionType() == SETTConstant.TransactionType.SPECIALOPERATION )
            {
                innerInfo.setTransNo(outInfo.getTransNo());
                updateToSpecial(innerInfo);
            }
        }
    	}
    }
    
}