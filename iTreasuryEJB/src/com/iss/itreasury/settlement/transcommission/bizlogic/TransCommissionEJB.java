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
 * @description:��������ȡ
 * @author: gqfang
 * @create: 2005-8-25
 *
 */
public class TransCommissionEJB implements SessionBean
{

    private javax.ejb.SessionContext mySessionCtx = null;
    
    final static long serialVersionUID = 3206093459760846163L;
    
    private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
    
    private static  Object lockObj = new Object();  //��̬
  

    /**
     * ejbActivate method comment
     * @exception java.rmi.RemoteException �쳣˵����
     */
    public void ejbActivate() throws java.rmi.RemoteException
    {
    }
    /**
     * ejbCreate method comment
     * @exception javax.ejb.CreateException �쳣˵����
     * @exception java.rmi.RemoteException �쳣˵����
     */
    public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
    {
    }
    /**
     * ejbPassivate method comment
     * @exception java.rmi.RemoteException �쳣˵����
     */
    public void ejbPassivate() throws java.rmi.RemoteException
    {
    }
    /**
     * ejbRemove method comment
     * @exception java.rmi.RemoteException �쳣˵����
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
     * @exception java.rmi.RemoteException �쳣˵����
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
    {
        mySessionCtx = ctx;
    }
    
   
    /**
     *@description: ���ܣ������ѽ��㴦�� 
     *              ��Ե�ҵ�������տ���и������ҵ����
     *              ��ϸʵ���������ڽ��������ѵ�ʱ��ϵͳִ���Զ����ˡ�
     *              ��ִ�й�����˵���»���Ϊ����͸���������:
     *              ���������
     *                     1.���ɽ��׺�
     *                     2.���������ѽ�����Ϣ����sett_TransCommission,���м�¼״̬Ϊ1(�����ݴ�)
     *                     3.�����˲���Ϣ��accountOperation.saveCommissionAccountDetails(),
     *                       (1)���Ӷ�Ӧ�����˻����ۼ�δ���˽��
     *                     4.����sett_TransCommission�еļ�¼��״̬Ϊ2(δ����)
     *              ���˲�����
     *                     1.����accountOperation.checkCommissionAccountDetails():
     *                       (1)�ڸ÷����е���withDrawCurrent()�۳������˻������˻��Ľ�
     *                       (2)�ڸ÷����е������˵� generateGLEntry()����,���ɻ���ˡ�
     *                     2.����sett_TransCommission�еļ�¼��״̬Ϊ3���Ѹ��ˣ���
     *                     3.�������ѵĽ��׺Ż����sett_TransCurrentDeposit �� sett_TransSpecailOperation��
     *                     
     *@return long
     *@throws RemoteException
     *@throws IRollbackException
     */
    public long commissionSettlement( Collection coll ) throws RemoteException,IRollbackException
    {
    	synchronized(lockObj){
        long returnValue = -1; //����ֵ
        
        Collection gatherColl = null;//���˻����ܺ�Ľ����
       
        //���߲����ӿ���
        UtilOperation utilOperation = new UtilOperation();
        
        //�˲������ӿ��� 
        AccountBookOperation accountBookOperation = new AccountBookOperation();
        
        //������DAO��
        Sett_TransCommissionDAO dao = new Sett_TransCommissionDAO();
        try
        {
            
            log.debug("part1:�������������ݵ� sett_TransCommission--��ʼ");
            partSave(coll);
            log.debug("part1:�������������ݵ� sett_TransCommission--����");
            
            
            log.debug("part2:���˻����ܽ����--��ʼ");
            gatherColl = generateGatherInfo(coll);
            log.debug("part2:���˻����ܽ����--��ʼ");
            
            
            
            Iterator gatherIterator = null;
            
            TransCommissionInfo info = null;
            
            if( gatherColl != null )
            {
                gatherIterator = gatherColl.iterator();
            }
            
            while( gatherIterator != null && gatherIterator.hasNext() )
            {
                info = (TransCommissionInfo)gatherIterator.next();
                
                
                log.debug("Part3:���ɽ��׺�--��ʼ");
                String transNo = utilOperation.getNewTransactionNo( info.getOfficeId() , info.getCurrencyId(),info.getTransactionTypeId());
                info.setTransNo( transNo );
                log.debug("Part3:--����------�½��׺���:" + transNo + "--------");
                
                
                
                log.debug("part4:�����˲���Ϣ--��ʼ");
                log.debug(UtilOperation.dataentityToString(info));
                accountBookOperation.saveCommissionAccountDetails(info);
                log.debug("part4:�����˲���Ϣ--����");
                
                log.debug("Part5:����accountOperation.checkCommissionAccountDetails()--��ʼ");
                info.setStatusId(SETTConstant.TransactionStatus.SAVE);
                accountBookOperation.checkCommissionAccountDetails(info);
                log.debug("Part5:����accountOperation.checkCommissionAccountDetails()--����");
                
                
                log.debug("Part6:�����׺ţ�״̬���sett_TransCommission--��ʼ");
                info.setTransNo(transNo);
                updateToTransCommission(info);
                log.debug("Part6:�����׺ţ�״̬���sett_TransCommission--����");
                
                log.debug("part7:�������ѵĽ��׺Ż����sett_TransCurrentDeposit �� sett_TransSpecailOperation--��ʼ");
                info.setTransNo(transNo);
                updateToMain( coll ,info);
                log.debug("part7:�������ѵĽ��׺Ż����sett_TransCurrentDeposit �� sett_TransSpecailOperation--����");
      
                
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
     *@description:�������ѵĽ��׺Ż����sett_TransCurrentDeposit
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
     *@description:�������ѵĽ��׺Ż����sett_TransSpecailOperation��
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
     *@description: ��Collection���������β��뵽��sett_TransCommission��
     *void
     *@param Collection
     */
    private void partSave( Collection coll ) throws RemoteException,IRollbackException
    {
    	synchronized(lockObj){
        //������DAO��
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
     *@description:���ɻ��ܵĽ����
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
     *@description:�����׺ţ�״̬���sett_TransCommission
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
     *@description: �������ѵĽ��׺Ż����sett_TransCurrentDeposit �� sett_TransSpecailOperation
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