package com.iss.itreasury.loan.loanapply.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.loanapply.dao.FormAssureDao;
import com.iss.itreasury.loan.loanapply.dao.LoanApplyDao;
import com.iss.itreasury.loan.loanapply.dao.LoanRepayPlanDao;
import com.iss.itreasury.loan.loanapply.dao.LoanRepayPlanDetailDao;
import com.iss.itreasury.loan.loanapply.dataentity.AssureInfo;
import com.iss.itreasury.loan.loanapply.dataentity.AutoPlanInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanBasicInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanCreateInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanExaminePassInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPlanDetailInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPlanInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPropertyInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanQueryInfo;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanCommonSetting;
import com.iss.itreasury.loan.loancommonsetting.bizlogic.LoanCommonSettingHome;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.approval.dao.ApprovalDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.system.logger.dataentity.LoggerBtnLevelInfo;
import com.iss.itreasury.system.logger.dataentity.LoggerResults;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class LoanApplyEJB implements SessionBean
{
    /* Methods required by SessionBean Interface. EJB 1.1 section 6.5.1. */
    
    /**
     * @see javax.ejb.SessionBean#setContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(SessionContext context){
        this.context = context;
    }
    private SessionContext context;
    /**
     * No argument constructor required by container.
     */
    public LoanApplyEJB()
    {
    }
    /**
     * Create method specified in EJB 1.1 section 6.10.3
     */
    public void ejbCreate()
    {
    }
    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate()
    {
    }
    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate()
    {
    }
    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove()
    {
    }
    
    /**
     * @param o LoanCreateInfo �����½���Ϣ
     * @return 1:�ɹ� -1 ʧ��
     * @throws RemoteException
     * @throws IException
     */
    public long add(LoanCreateInfo o) throws RemoteException, IException
    {
        LoanApplyDao dao=new LoanApplyDao();
        LoanRepayPlanDao pDao=new LoanRepayPlanDao();
        LoanPlanInfo plInfo=new LoanPlanInfo();
        long ret=-1;
//      add by fxzhang 2012-5-29 start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.CREATESAVE);
        try {
			logInfo.setBusinessType(LOANConstant.LoanType.getName(o.getTypeID()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "���������Ϣ");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//      add by fxzhang 2012-5-29 end

        try{
        	/*��������������Ϣ*/
            ret=dao.saveLoanCreate(o);
            
            if ( ret<0 ){
            	//throw new RemoteException("save loan create error");
            	//modified by mzh_fu 2007/08/07
            	throw new IRollbackException(context, "save loan create error");
            }
            	
			/*��������ƻ��汾��Ϣ*/	
            plInfo.setID(-1);
            plInfo.setLoanID(ret);
            plInfo.setInputUserID(o.getInputUserID() );
            plInfo.setIsUsed( 2 );
            plInfo.setUserType(1);
	        
    		pDao.insert(plInfo);
    		        
//          add by fxzhang 2012-5-29 start
            // ���ϲ�����־        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);//�������-�ɹ�
            // add by fxzhang 2012-5-29 end
            
        }catch(Exception e){
        	
//          add by fxzhang 2012-5-29 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by fxzhang 2012-5-29 end
            
        	//modified by mzh_fu 2007/08/07
            //throw new IException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
        //add by fxzhang 2012-5-29 start
        finally
        {
        	logInfo.setTransCode(o.getApplyCode()); //ҵ������-���׺�   
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
//		 add by fxzhang 2012-5-29 end
		
        return ret;
    }
    
    /**
     * @param o LoanBasicInfo ���������Ϣ
     * @return 1:�ɹ� -1 ʧ��
     * @throws RemoteException
     * @throws IException
     */
    public long updateBasic(LoanBasicInfo o)  throws RemoteException, IException
    {
        LoanApplyDao dao=new LoanApplyDao();
        long loanID=o.getLoanID();
        // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        try {
        	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanID));  //������־-ҵ������-���׺�  
			logInfo.setBusinessType(LOANConstant.LoanType.getName(o.getLoanType()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "�������");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
        long ret=-1;
        try{
			/*�����ͬ����*/
			ContractOperation co=new ContractOperation();
			co.beforeUpdateLoan(o.getLoanID());

			/*������Ϣ*/			
            ret=dao.saveLoanBasic(o);
            //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());
        	// add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;

    }
    
    /**
     * @param o LoanPropertyInfo ����������Ϣ
     * @return 1:�ɹ� -1 ʧ��
     * @throws RemoteException
     * @throws IException
     */
    public long updateProperty(LoanPropertyInfo o) throws RemoteException, IException
    {
        LoanApplyDao dao=new LoanApplyDao();
        long loanID=o.getLoanID() ;
        long ret=-1;
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        logInfo.setBusinessType(LOANConstant.LoanType.getName(o.getLoanType())+"-��������-�������ʺ͵�����ʽ");
        logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanID));  //������־-ҵ������-���׺�  
        try{
			/*�����ͬ����*/
			ContractOperation co=new ContractOperation();
			ret=co.beforeUpdateLoan(o.getLoanID());

			/*����������������Ϣ*/
            ret=dao.saveLoanProperty(o);
            //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//modified by mzh_fu 2007/08/07
        	// add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
            
           // throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
        //add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
    }
    /**
     * �ύ��������
     * @param lLoanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public long commit(long lLoanID,long lUserID) throws RemoteException, IException
	{
        LoanApplyDao dao=new LoanApplyDao();
        long ret=-1;
     // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.INITAPPROVAL);
        try {
			logInfo.setBusinessType("��������");
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lLoanID));  //������־-ҵ������-���׺�  
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        try{
            dao.updateLoanStatus(lLoanID,lUserID,LOANConstant.LoanStatus.SUBMIT);
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//modified by mzh_fu 2007/08/07
           // throw new RemoteException(e.getMessage());
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
	}
    /**
     * �ύ��������
     * @param lLoanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public long commitdb(LoanApplyInfo lainfo) throws RemoteException, IException
	{
        LoanApplyDao dao=new LoanApplyDao();
        long ret=-1;
        // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);//��������-�ύ����
        try {
			logInfo.setBusinessType(LOANConstant.LoanType.getName(lainfo.getTypeID()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
        try{
            dao.updateLoanStatusdb(lainfo);
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());
        	// add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	logInfo.setTransCode(lainfo.getApplyCode());  //������־-ҵ������-���׺�  
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end

        return ret;
	}
    /**
     * ɾ��һ�ʴ�������
     * @param lLoanID
     * @param lUserID
     * @return
     * @throws RemoteException
     * @throws IException
     */
    public long delete(long lLoanID,long lUserID) throws RemoteException, IException
	{
        LoanApplyDao dao=new LoanApplyDao();
        long ret=-1;
       // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.DELETE);
        try {
//			logInfo.setBusinessType("��������-����");
			logInfo.setBusinessType(Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY));
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lLoanID));  //������־-ҵ������-���׺�  
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
        try{
            dao.updateLoanStatus(lLoanID,lUserID,Constant.RecordStatus.INVALID);
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//modified by mzh_fu 2007/08/07
           // throw new RemoteException(e.getMessage());
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
	}
    
    /**
     * ȡ��һ�ʴ�������
     * @param lLoanID
     * @param lUserID
     * @return
     * @throws RemoteException
     * @throws IException
     */
    public long cancel(long lLoanID,long lUserID) throws RemoteException, IException
	{
        LoanApplyDao dao=new LoanApplyDao();
        long ret=-1;
     // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.CANCELAPPLY);
        try {
//			logInfo.setBusinessType("��������-����");
			logInfo.setBusinessType(Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY));
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lLoanID));  //������־-ҵ������-���׺� 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
        try{
			/*�����ͬ����*/
			ContractOperation co=new ContractOperation();
			ret=co.beforeUpdateLoan(lLoanID);

            dao.updateLoanStatus(lLoanID,lUserID,LOANConstant.LoanStatus.CANCEL);
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//modified by mzh_fu 2007/08/07
           // throw new RemoteException(e.getMessage());
        	//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	 
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
	}
    
    /**
     * ���ݴ����ID����ô����������Ϣ
     * @param lLoanID
     * @return
     * @throws RemoteException
     * @throws IException
     */
    public LoanApplyInfo findByID(long lLoanID) throws RemoteException, IException
    {
        LoanApplyDao dao=new LoanApplyDao();
        LoanApplyInfo applyInfo=null;
        try{
            applyInfo=dao.findByID(lLoanID);
        }catch(Exception e){
            throw new RemoteException(e.getMessage());
        }
        return applyInfo;
    }
    /**
     * ���ݴ���Ĳ�ѯ����������ȡ����������б�
     * @param lLoanID
     * @return
     * @throws RemoteException
     * @throws IException
     */
    public Collection query(LoanQueryInfo o) throws RemoteException, IException
	{
        LoanApplyDao dao=new LoanApplyDao();
        Collection c=null;
        try{
            c=dao.query(o);
        }catch(Exception e){
            throw new RemoteException(e.getMessage());
        }
        return c;
	}
   
    /**
     * added by mzh_fu 2007/05/30
     * @param lepInfo
     * @return
     * @throws RemoteException,IRollbackException 
     */
    public long saveExamineInfo(LoanExaminePassInfo lepInfo) throws RemoteException,IRollbackException{
		LoanApplyDao ldao = new LoanApplyDao();
		long lReturnId = -1;
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        try {
        	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lepInfo.getLoanID()));  //������־-ҵ������-���׺�  
			logInfo.setBusinessType(LOANConstant.LoanType.getName(lepInfo.getLoanType()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "�޸������Ϣ");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try {
			lReturnId = ldao.examineUpdate(lepInfo);	
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		} catch (Exception e) {
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return lReturnId;
    	
    }
    
    /**
     * added by mzh_fu 2007/05/25
     * @param lepInfo
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(LoanExaminePassInfo lepInfo)
			throws RemoteException, IRollbackException {
		LoanApplyDao ldao = new LoanApplyDao();
		ContractDao conDao = new ContractDao();
		String strContractCode = "";
		long lReturnId = -1;
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.SAVEANDINITAPPROVAL);
        try {
			logInfo.setBusinessType(LOANConstant.LoanType.getName(lepInfo.getLoanType()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY));
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lepInfo.getLoanID()));  //������־-ҵ������-���׺�  
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try {
		

			lReturnId = ldao.examineUpdate(lepInfo);

			LoanApplyDao loanApplyDao = new LoanApplyDao();
			InutParameterInfo inutParameterInfo = lepInfo
					.getInutParameterInfo();
			
			//���������������
			if(lepInfo.getLoanID() >0){
				LoanApplyInfo appInfo=ldao.findByID(lepInfo.getLoanID());
				inutParameterInfo.setDataEntity(appInfo);
			}

			// �ύ����
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// ����״̬��"������"
			loanApplyDao.updateLoanStatus(lepInfo.getLoanID(), lepInfo
					.getInputUserID(), LOANConstant.LoanStatus.APPROVALING);
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		} catch (Exception e) {
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return lReturnId;
	}
    
    
    public long submitApply(LoanExaminePassInfo lepInfo)
			throws RemoteException, IRollbackException {
		LoanApplyDao ldao = new LoanApplyDao();
		ContractDao conDao = new ContractDao();
		String strContractCode = "";
		long lReturnId = -1;
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        try {
        	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lepInfo.getLoanID()));  //������־-ҵ������-���׺�  
			logInfo.setBusinessType(LOANConstant.LoanType.getName(lepInfo.getLoanType()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) +"-���" );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try {
		
			lReturnId = ldao.examineUpdate(lepInfo);

			// ����״̬��"�ѱ���"
			new LoanApplyDao().updateLoanStatus(lepInfo.getLoanID(), lepInfo
					.getInputUserID(), LOANConstant.LoanStatus.SAVE);
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		} catch (Exception e) {
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return lReturnId;
	}

    
    
    public long examinePass(LoanExaminePassInfo lepInfo) throws RemoteException, IRollbackException
    {
		//����ʷ������޸ģ�ֻ����������¼��ͬʱ������״̬�ĳ����ύ��
		LoanApplyDao ldao=new LoanApplyDao();
		ContractDao conDao=new ContractDao();
		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalSettingInfo asInfo = new ApprovalSettingInfo();
		ApprovalDelegation appbiz = new ApprovalDelegation();
		LoanApplyInfo appInfo=null;
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.DOAPPRVOAL);
        try {
        	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lepInfo.getLoanID()));  //������־-ҵ������-���׺�  
			logInfo.setBusinessType(LOANConstant.LoanType.getName(lepInfo.getLoanType()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		long lOfficeID=lepInfo.getOfficeID();
		long lCurrencyID=lepInfo.getCurrencyID();
		long lLoanID=lepInfo.getLoanID();
		long lUserID=lepInfo.getUserID() ;
		long lInputUserID=lepInfo.getInputUserID();
		long lNextUserID=lepInfo.getNextUserID() ;
		String strOption=lepInfo.getOption();
		long lModuleID = Constant.ModuleType.LOAN;
		long lLoanTypeID = -1;
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		long lApprovalID = -1;
		long lStatusID = Constant.RecordStatus.VALID;
		long lResultID = Constant.ApprovalDecision.PASS;
		long ret=-1;
		long checkLevelID=lepInfo.getCheckLevelID() ;
		long lLevel = -1;
		String strContractCode = "";
	//	System.out.println("nextcheckuser:"+lNextUserID);
	//	System.out.println("interestRate:"+lepInfo.getInterestRate() );
						
		try
		{
			appInfo=ldao.findByID(lLoanID);
//			lLoanTypeID=appInfo.getTypeID();
			lLoanTypeID=appInfo.getSubTypeId();
			long status=appInfo.getStatusID() ;
//			long nNextCheckUserID=appInfo.getNextCheckUserID();
			
			/* modified by mzh_fu 2007/05/24
			 * 
				if ( nNextCheckUserID!=lUserID )
					throw new IException("Gen_E001");			 
			
			if ( status!=LOANConstant.LoanStatus.SUBMIT )
				throw new IException("Gen_E001");
			
			*/	
			
			
				
			//ldao.examineUpdate(lepInfo);

			//---- added by mzh_fu 2007/05/24 ������ begin
			
			InutParameterInfo inutParameterInfo = lepInfo.getInutParameterInfo();
			InutParameterInfo returnInfo = new InutParameterInfo();
			
			//��ҵ���¼����pinfo,ת���ɱ�׼map���ݵ�����������
			inutParameterInfo.setDataEntity(appInfo);
			
			//�ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			
			//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if(returnInfo.isLastLevel())
			{	
				ldao.updateLoanStatus(
						lLoanID,
						lUserID,
						LOANConstant.LoanStatus.CHECK);
				
				//������������Ϣ���Ƶ���ͬ��				
				conDao.insert(lLoanID,appInfo.getTypeID());
				lResultID = Constant.ApprovalDecision.FINISH;	
	
			}
			//��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if(returnInfo.isRefuse())
			{
				ldao.updateLoanStatus(
						lLoanID,
						lUserID,
						LOANConstant.LoanStatus.SAVE);
				// add by jbpan 20120614 ������־-��������-�ܾ�
				logInfo.setActionTypeID(Constant.LoggerOfOperationType.REFLUSE);
			}	
			
			//---- added by mzh_fu 2007/05/24 ������ end
			
			
			
		/* modified by mzh_fu 2007/05/24
		 * 			
  			//���ApprovalID
			lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
			if ( lApprovalID<=0 )
				throw new IException("Gen_E105");
			//��һ������˼���
			lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
			Log.print("��һ������˼���" + lLevel);
			//��������
			asInfo = appbiz.findApprovalSetting(lApprovalID);
			
			if ( checkLevelID==1 )	//���һ�����
			{
				//������״̬�ĳ����ύ
				ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.LoanStatus.CHECK);		
				
				//������������Ϣ���Ƶ���ͬ��				
				conDao.insert(lLoanID,appInfo.getTypeID());
				lResultID = Constant.ApprovalDecision.FINISH;		
			}
 			else
			{
			    if (asInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
				{
			        ldao.updateLoanNextCheckLevel(lLoanID,lLevel);
			        Log.print("������һ����˼��𣨿�Խ������" + lLevel);
				}
				else
				{
				    //��һ����˼����Զ���һ
					ldao.updateLoanNextCheckLevel(lLoanID,-1);
					Log.print("������һ����˼��𣨲���Խ������" + lLevel);
				}			    
			}
									
			//�����������
			info.setModuleID(lModuleID);
			info.setLoanTypeID(lLoanTypeID);
			info.setActionID(lActionID);
			info.setApprovalContentID(lLoanID);
			info.setUserID(lUserID);
			info.setNextUserID(lNextUserID);
			info.setOpinion(strOption);
			info.setResultID(lResultID);
			info.setStatusID(lStatusID);
			info.setOfficeID(lOfficeID);
			info.setCurrencyID(lCurrencyID);
			ret=appbiz.saveApprovalTracing(info);
			System.out.println("SaveApprovalTracing:"+ret);
			
			*/
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		catch (Exception e)
		{
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return ret;    	
    }
  
    public long examineRefuse(long lLoanID,String strOption,long lUserID,long lCurrencyID,long lOfficeID) throws RemoteException, IException
    {
		//��������¼�����޸ģ�ͬʱ������״̬�ĳ����ύ��
		LoanApplyDao ldao=new LoanApplyDao();
		ApprovalTracingInfo info = null;
		ApprovalDelegation appbiz = null;
		LoanApplyInfo appInfo=null;
		
		long lModuleID = Constant.ModuleType.LOAN;
		long lLoanTypeID = -1;
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		long lApprovalID = -1;
		long lStatusID = Constant.RecordStatus.INVALID;
		long lResultID = Constant.ApprovalDecision.REFUSE;
		long ret=-1;
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.REFLUSE);
        // add by jbpan 20120605 end				
		try
		{
			appInfo=ldao.findByID(lLoanID);
//			lLoanTypeID=appInfo.getTypeID();
			lLoanTypeID=appInfo.getSubTypeId();
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lLoanID));  //������־-ҵ������-���׺�  
			logInfo.setBusinessType(LOANConstant.LoanType.getName(appInfo.getTypeID()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) );
			//������״̬�ĳ��Ѿܾ�
			ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.LoanStatus.REFUSE);
			
			info = new ApprovalTracingInfo();
			appbiz = new ApprovalDelegation();

			info.setModuleID(lModuleID);
			info.setLoanTypeID(lLoanTypeID);
			info.setActionID(lActionID);
			info.setApprovalContentID(lLoanID);
			info.setUserID(lUserID);
			info.setOpinion(strOption);
			info.setResultID(lResultID);
			info.setStatusID(lStatusID);
			info.setOfficeID(lOfficeID);
			info.setCurrencyID(lCurrencyID);
			ret = appbiz.saveApprovalTracing(info); //����������Ϣ
			
			//���ApprovalID
			lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
			if ( lApprovalID<=0 ){
				//modified by mzh_fu 2007/08/07
				//throw new RemoteException("Cant find approval ID for loan apply");
				throw new IRollbackException(context,"Cant find approval ID for loan apply");
			}
						
			//�����������
			//2007-3-14 mzh_fu modified			
			//ret=appbiz.deleteApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lLoanID,2);
			ret=new ApprovalDao().deleteApprovalTracing(lModuleID, lLoanTypeID, lActionID, lOfficeID, lCurrencyID, lLoanID, 2);
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		catch (Exception e)
		{
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			e.printStackTrace();
			//modified by mzh_fu 2007/08/07
			//throw new RemoteException(e.getMessage());
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return ret;
    }
    
    public long examineBack(long lLoanID,String strOption,long lUserID,long lCurrencyID,long lOfficeID) throws RemoteException, IException
    {
    	//����ʷ������޸ģ�ֻ����������¼��ͬʱ������״̬�ĳ����ύ��
		LoanApplyDao ldao=new LoanApplyDao();
		ApprovalTracingInfo info = null;
		ApprovalDelegation appbiz = null;
		LoanApplyInfo appInfo=null;
		
		long lModuleID = Constant.ModuleType.LOAN;
		long lLoanTypeID = -1;
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		long lApprovalID = -1;
		long lStatusID = Constant.RecordStatus.VALID;
		long lResultID = Constant.ApprovalDecision.RETURN;
		long ret=-1;
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.RETURN);
        // add by jbpan 20120605 end				
    	try
		{
			appInfo=ldao.findByID(lLoanID);
//			lLoanTypeID=appInfo.getTypeID();
			lLoanTypeID=appInfo.getSubTypeId();
			//add by jbpan 20120605 ������־-ҵ������
			logInfo.setBusinessType(LOANConstant.LoanType.getName(appInfo.getTypeID()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "�����˻�");
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lLoanID));  //������־-ҵ������-���׺�  
			//���¸����������Ϊ¼����
			ldao.setCheckUserBack(lLoanID);
			
			//������״̬�ĳ��ύ
			ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.LoanStatus.SUBMIT);
			System.out.println("========="+lLoanID);
			
			//��һ����˼���ĳɵ�һ��
			ldao.updateLoanNextCheckLevel(lLoanID,1);
			
			info = new ApprovalTracingInfo();
			appbiz = new ApprovalDelegation();
			
		    //���ApprovalID
			lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
			if ( lApprovalID<=0 ){
				//modified by mzh_fu 2007/08/07
				//throw new RemoteException("Cant find approval ID for loan apply");
				throw new IRollbackException(context, "Cant find approval ID for loan apply");
			}
						
			//�����������
			info.setModuleID(lModuleID);
			info.setLoanTypeID(lLoanTypeID);
			info.setActionID(lActionID);
			info.setApprovalContentID(lLoanID);
			info.setUserID(lUserID);
			info.setOpinion(strOption);
			info.setResultID(lResultID);
			info.setStatusID(lStatusID);
			info.setOfficeID(lOfficeID);
			info.setCurrencyID(lCurrencyID);
			ret=appbiz.saveApprovalTracing(info);
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		catch (Exception e)
		{
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			e.printStackTrace();
			//modified by mzh_fu 2007/08/07
			//throw new RemoteException(e.getMessage());
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
    }
    /**
     * ����һ����֤��Ϣ
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */        
    public long addAssure(AssureInfo o) throws RemoteException, IException
    {
        FormAssureDao fDao=new FormAssureDao();
        long ret=-1;
        long loanID=o.getLoanID() ;
      // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.CREATESAVE);
        try {
			logInfo.setBusinessType(Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "����������ϸ");
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(o.getLoanID()));  //������־-ҵ������-���׺�  
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
        try
        {
			/*�����ͬ����*/
			ContractOperation co=new ContractOperation();
			ret=co.beforeUpdateLoan(o.getLoanID());

			/*���汣֤��Ϣ*/        	
            ret=fDao.insert(o);
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }
        catch (Exception e)
        {
        	//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	// modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());\
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;        
    }
	public long saveClientInfo(long lLoanID,ClientInfo o) throws RemoteException, IException
	{
		long ret=-1;
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        try {
			logInfo.setBusinessType(Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "����ͻ���Ϣ");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try
		{
			LoanCommonSettingHome home = (LoanCommonSettingHome)EJBObject.getEJBHome("LoanCommonSettingHome");
			LoanCommonSetting lcs = home.create();
			
			ret=lcs.saveClientInfo( o );
			
			if (lLoanID>0)
			 {
				 /*�����ͬ����*/
				 ContractOperation co=new ContractOperation();
				 ret=co.beforeUpdateLoan(lLoanID);            	
			 }
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		//modified by mzh_fu 2007/08/07
/*		catch (IException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
			throw e;
		}*/
		catch (Exception e)
		{
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			e.printStackTrace();
			//modified by mzh_fu 2007/08/07
			//throw new RemoteException(e.getMessage() );
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
        	logInfo.setTransCode(o.getCode());  //������־-ҵ������-�ͻ�����  
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return -1;
	}
    /**
     * ���±�֤��Ϣ
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public long updateAssure(AssureInfo o) throws RemoteException, IException
    {
        FormAssureDao fDao=new FormAssureDao();
        long ret=-1;
        long loanID=o.getLoanID() ;
     // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        try {
			logInfo.setBusinessType( Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "����������ϸ");
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(o.getLoanID()));  //������־-ҵ������-���׺�  
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
        try
        {
			/*�����ͬ����*/
			ContractOperation co=new ContractOperation();
			ret=co.beforeUpdateLoan(o.getLoanID());

            ret=fDao.update(o);
            //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }
        catch (Exception e)
        {
        	//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
       //add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
    }
    /**
     * ɾ��ָ���ı�֤��Ϣ
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
   
    public long deleteAssure(long aID[]) throws RemoteException, IException
    {
        FormAssureDao fDao=new FormAssureDao();
        AssureInfo assInfo=null;
        long loanID=1;
        long ret=-1;
     // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.DELETE);
        try {
			logInfo.setBusinessType( Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "����������ϸ");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
        try
        {
            for ( int i=0;i<aID.length;i++ )
            {
            	if (aID[i]<=0) continue;
            	assInfo=fDao.findByID(aID[i]);
            	loanID=assInfo.getLoanID();
                fDao.delete(aID[i]);
            }
            if (loanID>0)
            {
            	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanID));  //������־-ҵ������-���׺� 
				/*�����ͬ����*/
				ContractOperation co=new ContractOperation();
				ret=co.beforeUpdateLoan(loanID);            	
            }
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }
        catch (Exception e)
        {
        	//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	//modified by mzh_fu 2007/08/07
           // throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return 1;
    }
    
    /**
     * ���һ�����֤��Ϣ
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public AssureInfo findAssureByID(long aID) throws RemoteException, IException
    {
        FormAssureDao fDao=new FormAssureDao();
        AssureInfo aInfo=null;
        
        try
        {
            aInfo=fDao.findByID(aID);
        }
        catch (Exception e)
        {
           throw new RemoteException(e.getMessage());
        }
        
        return aInfo;
    }
    
    /**
     * added by xiong fei 2010/05/19
     * ͨ������ID����ñ�֤��
     * @param loanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public AssureInfo findAssureByLoanID(long aID) throws RemoteException, IException
    {
        FormAssureDao fDao=new FormAssureDao();
        AssureInfo aInfo=null;
        
        try
        {
            aInfo=fDao.findByLoanID(aID);
        }
        catch (Exception e)
        {
           throw new RemoteException(e.getMessage());
        }
        
        return aInfo;
    }
    
    
    
    /**
     * �����������Զ����ŷŻ���ƻ�
     * ���޸�֮ǰ�����ȵ���ContractOperation.beforeUpdateLoan()
     * ����LoanRepayPlanDao.delete()��ɾ������������мƻ���ϸ
     * Ȼ�󣬸�������������LoanRepayPlanDetailDao�е�insert()����������
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public long autoAddPlan(AutoPlanInfo apInfo) throws RemoteException, IException
    {
        LoanRepayPlanDao pDao=new LoanRepayPlanDao();
        long ret=-1;
        long lLoanID=apInfo.getLLoanID();
        // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        logInfo.setBusinessType("��������-ԭʼ�ƻ�");
        logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lLoanID));  //������־-ҵ������-���׺�  
        // add by jbpan 20120605 end
        try
        {
			/*�����ͬ����*/
			ContractOperation co=new ContractOperation();
			ret=co.beforeUpdateLoan(lLoanID);
            
            /* ɾ������������мƻ���ϸ */
            pDao.delete(lLoanID);
            
            /* �Զ����ɷŻ�����Ϣ*/
            ret=pDao.autoSavePlanDetail( apInfo );
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }
        catch (Exception e)
        {
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
    }
    /**
     * �����������Զ���������������𳥸���
     * ���޸�֮ǰ�����ȵ���ContractOperation.beforeUpdateLoan()
     * ����LoanRepayPlanDao.delete()��ɾ������������мƻ���ϸ
     * Ȼ�󣬸�������������LoanRepayPlanDetailDao�е�insert()����������
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public long autoAddLeaseholdPlan(AutoPlanInfo apInfo) throws RemoteException, IException
    {
        LoanRepayPlanDao pDao=new LoanRepayPlanDao();
        long ret=-1;
        long lLoanID=apInfo.getLLoanID();
        // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.CREATESAVE);
        logInfo.setBusinessType("��������-����������𳥸���");
    	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lLoanID));  //������־-ҵ������-���׺�  
        // add by jbpan 20120605 end
        try
        {
			/*�����ͬ����*/
			ContractOperation co=new ContractOperation();
			ret=co.beforeUpdateLoan(lLoanID);
            
            /* ɾ������������мƻ���ϸ */
            pDao.delete(lLoanID);
            
            /* �Զ�����𳥸���*/
            ret=pDao.autoSaveLeaseholdPlanDetail( apInfo );
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }
        catch (Exception e)
        {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
        return ret;
    }
    /**
     * ��ѯ������������мƻ���ϸ
     * ����LoanRepayPlanDao.java��findByLoanID()
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public Collection findPlanByLoanID(long lLoanID,long lPageNo,long lPageLine,long lOrderParam,long lDesc) throws RemoteException, IException
    {
        LoanRepayPlanDao pDao=new LoanRepayPlanDao();
        Collection c=null;
        
        try
        {
            c=pDao.findByLoanID(lLoanID,lPageNo,lPageLine,lOrderParam,lDesc);
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
                
        return c;
    }
    
    /**
     * ����������޸Ļ򱣴棩�Ż���ƻ�
     * ���޸�֮ǰ�����ȵ���ContractOperation.beforeUpdateLoan()
     * ����LoanRepayPlanDetailDao.java�е�insert()������update()����
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public long addPlan(Collection cList) throws RemoteException, IException
    {
        LoanRepayPlanDetailDao pdDao=new LoanRepayPlanDetailDao();
        LoanRepayPlanDao pDao=new LoanRepayPlanDao();
        LoanPlanDetailInfo pdInfo=null;
        long planID=-1;
        long loanID=-1;
        long ret=-1;
     // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        logInfo.setBusinessType("��������-ԭʼ�ƻ�");
        // add by jbpan 20120605 end
        Vector v=(Vector)cList;
        try
        {
            /*���Ӵ�����ϸ��Ϣ*/
            int vSize=v.size();
            for ( int i=0;i<vSize;i++ )
            {
                pdInfo=(LoanPlanDetailInfo)v.get(i);
                planID=pdInfo.getPlanID() ;
                if ( pdInfo.getID()<=0 ){
                	pdDao.insert(pdInfo);
                }else{
                	//add by jbpan 20120611 ������־-��������-�޸ı��� 
                	logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
                	pdDao.update(pdInfo);
                }
            }
            if (planID>0)
            {
            	loanID=pDao.findLoanIDByPlanVersion( planID );
            	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanID));  //������־-ҵ������-���׺�  
				/*�����ͬ����*/
				ContractOperation co=new ContractOperation();
				ret=co.beforeUpdateLoan(loanID);
            }
           //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
            return 1;
        }
        catch (Exception e)
        {
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	//modified by mzh_fu 2007/08/07
           // throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
    }
    /**
     * ɾ������ƻ�����ϸ��Ϣ
     * ���޸�֮ǰ�����ȵ���ContractOperation.beforeUpdateLoan()
     * ˳�����LoanRepayPlanDetailDao�е�delete()
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */
    public long deletePlan(long aPlanID[]) throws RemoteException, IException
    {
        LoanRepayPlanDetailDao pdDao=new LoanRepayPlanDetailDao();
        LoanRepayPlanDao pDao=new LoanRepayPlanDao();
        LoanApplyDao dao=new LoanApplyDao();
        LoanPlanDetailInfo detailInfo=null;
        
        long planVersion=-1;
        long loanID=-1;
        long ret=-1;
     // add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.DELETE);
		logInfo.setBusinessType("��������-ԭʼ�ƻ�");
        // add by jbpan 20120605 end
        try
        {
            /*˳��ɾ��loan plan detail ���еļ�¼*/
            for ( int i=0;i<aPlanID.length;i++)
            {
                if (aPlanID[i]<=0) continue;
                detailInfo=pdDao.findByID( aPlanID[i] );
                planVersion=detailInfo.getPlanID();
                pdDao.delete( aPlanID[i] );
            }
            if (planVersion>0)
            {
				loanID=pDao.findLoanIDByPlanVersion( planVersion );
				logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanID));  //������־-ҵ������-���׺� 
				/*�����ͬ����*/
				ContractOperation co=new ContractOperation();
				ret=co.beforeUpdateLoan(loanID);
            }
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
            return 1;
        }
        catch (Exception e)
        {
        	//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	//modified by mzh_fu 2007/08/07
           //throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
        finally
        {
        	 
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end

    }
    
    /**
     * ��ѯĳ������ƻ�����ϸ��Ϣ
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */
    public LoanPlanDetailInfo findPlanByID(long aPlanID) throws RemoteException, IException
    {
        LoanRepayPlanDetailDao pdDao=new LoanRepayPlanDetailDao();
        LoanPlanDetailInfo pdInfo=null;
        
        try
        {
            pdInfo=pdDao.findByID(aPlanID);
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        
        return pdInfo;
    }

	/** ��־ǿ(kewen hu)���(2004-03-10) */
	/**
	 * ͨ������IDȡ��ͬ���
	 * ����LoanApplyDao.java��findContratCodeByLoanID()
	 * @param  long lLoanID
	 * @return String ContractCode
	 * @exception RemoteException,Exception
	 */
	public String findContratCodeByLoanID(long lLoanID) throws RemoteException, IException {
		LoanApplyDao pDao=new LoanApplyDao();
		String sReturn = "";
		try {
			sReturn = pDao.findContratCodeByLoanID(lLoanID);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}

		return sReturn == null || sReturn == ""?"":sReturn;
	}
	
    /**
     * ���´��������Ƿ�����С��˼����ֶ�
     * @param lLoanID
     * @param isLowLevel
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public long updateLoanCheckLevel(long loanID, long isLowLevel) throws RemoteException, IException
	{
        LoanApplyDao dao = new LoanApplyDao();
        long ret = -1;
//        //add by jbpan 20120605 ������־ start
//        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
//        logInfo.setActionTypeID(Constant.LoggerOfOperationType.UPDATE);
//		logInfo.setBusinessType("���´��������Ƿ�����С��˼����ֶ�");
//        // add by jbpan 20120605 end
        try{
            ret = dao.updateLoanCheckLevel(loanID, isLowLevel);
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
//            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//add by jbapn 20120605 start
            // ���ϲ�����־       
//            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
//            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
//        finally
//        {
//        	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanID));  //������־-ҵ������-���׺�  
//            LoggerResults vResult = LoggerResults.getInstance(); 
//    		// ����־��¼���浽�ڴ���
//    		vResult.getResult().add(logInfo);
//        }     
        //add by jbpan 20120605 end
        return ret;
	}
    public long updateLoanCheckLevel(long loanID, long isLowLevel,long reportid) throws RemoteException, IException
	{
        LoanApplyDao dao = new LoanApplyDao();
        long ret = -1;
      //add by jbpan 20120605 ������־ start
//        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
//        logInfo.setActionTypeID(Constant.LoggerOfOperationType.UPDATE);
//		logInfo.setBusinessType("���´��������Ƿ�����С��˼����ֶ�");
        // add by jbpan 20120605 end
        try{
            ret = dao.updateLoanCheckLevel(loanID, isLowLevel,reportid);
          //add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
//            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
        }catch(Exception e){
        	//add by jbapn 20120605 start
            // ���ϲ�����־       
//            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
//            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(e.getMessage());
        	throw new IRollbackException(context, e.getMessage(), e);
        }
      //add by jbpan 20120605 start
//        finally
//        {
//        	logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanID));  //������־-ҵ������-���׺�  
//            LoggerResults vResult = LoggerResults.getInstance(); 
//    		// ����־��¼���浽�ڴ���
//    		vResult.getResult().add(logInfo);
//        }     
        //add by jbpan 20120605 end
        return ret;
	}
    
	/**
	 * �ύ����������
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long initApproval(LoanApplyInfo loanApplyInfo)throws RemoteException, IRollbackException
	{
		long depositId = -1;
		LoanApplyDao loanApplyDao = new LoanApplyDao();
		InutParameterInfo inutParameterInfo = loanApplyInfo.getInutParameterInfo();
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.INITAPPROVAL);
        try {
			logInfo.setBusinessType(LOANConstant.LoanType.getName(loanApplyInfo.getTypeID()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY));
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanApplyInfo.getID()));  //������־-ҵ������-���׺�  
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try
		{
			commitdb(loanApplyInfo);
			inutParameterInfo.setDataEntity(loanApplyInfo);
			//�ύ����
			FSWorkflowManager.initApproval(inutParameterInfo);
			//����״̬
			loanApplyDao.updateLoanStatus(
					loanApplyInfo.getID(),
					loanApplyInfo.getInputUserID(),
					LOANConstant.LoanStatus.APPROVALING);
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		catch (Exception e)
		{
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return depositId;
	}
	
	/**
	 * �ύ������
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	
	public long updatezl(LoanApplyInfo lainfo) throws RemoteException, IRollbackException
	{
		long depositId = -1;
		LoanApplyDao loanApplyDao = new LoanApplyDao();
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
        try {
			logInfo.setBusinessType(LOANConstant.LoanType.getName(lainfo.getTypeID()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "��ϸ��Ϣ");
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(lainfo.getID()));  //������־-ҵ������-���׺�  
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try{
			
			loanApplyDao.savezl(lainfo);
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		catch(Exception e){
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return depositId;
	}
	
	/**
	 * �ύ����������
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long initApprovalzl(LoanApplyInfo loanApplyInfo)throws RemoteException, IRollbackException
	{
		long depositId = -1;
		LoanApplyDao loanApplyDao = new LoanApplyDao();
		InutParameterInfo inutParameterInfo = loanApplyInfo.getInutParameterInfo();
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.INITAPPROVAL);
        try {
			logInfo.setBusinessType(LOANConstant.LoanType.getName(loanApplyInfo.getTypeID()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY) + "-" + "��ϸ��Ϣ");
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanApplyInfo.getID()));  //������־-ҵ������-���׺�  
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try
		{
			updatezl(loanApplyInfo);
			inutParameterInfo.setDataEntity(loanApplyInfo);
			//�ύ����
			FSWorkflowManager.initApproval(inutParameterInfo);
			//����״̬
			loanApplyDao.updateLoanStatus(
					loanApplyInfo.getID(),
					loanApplyInfo.getInputUserID(),
					LOANConstant.LoanStatus.APPROVALING);
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		catch (Exception e)
		{
			//add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return depositId;
	}
	
	/**
	 * Modify by leiyang date 2007/07/10
	 * ��������У���ͬ״̬
	 * @param loanInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long checkContract(LoanExaminePassInfo loanInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		LoanApplyDao loanDao = new LoanApplyDao();
		try
		{
			//У���ͬ״̬
			lReturn = loanDao.checkContractState(loanInfo.getLoanID());					
		}
		catch (Exception e)
		{
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}
	
	
	/**
	 * Modify by leiyang date 2007/07/10
	 * ��������ȡ����������������.�������룩
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(LoanExaminePassInfo loanInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();
		LoanApplyDao loanDao = new LoanApplyDao();
		// add by jbpan 20120605 ������־ start
        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
        logInfo.setActionTypeID(Constant.LoggerOfOperationType.CANCELAPPROVAL);
        try {
			logInfo.setBusinessType(LOANConstant.LoanType.getName(loanInfo.getLoanType()) + "-" + Constant.CodingruleAction.getName(Constant.CodingruleAction.LOAN_APPLY));
			logInfo.setTransCode(NameRef.getApplyCodeByLoanID(loanInfo.getLoanID()));  //������־-ҵ������-���׺�  
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        // add by jbpan 20120605 end
		try
		{
			//ȡ������
			lReturn = loanDao.updateStatusAndCheckStatus(loanInfo.getLoanID(), loanInfo.getUserID(), LOANConstant.LoanStatus.SAVE);
			
			if(lReturn > 0){
				//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
				if(inutParameterInfo.getApprovalEntryID()>0)
				{
					FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
				}
			}
			//add by jbpan 20120605 ���ϲ�����־-�������-�ɹ�        
            logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
		}
		catch (Exception e)
		{
//          add by jbapn 20120605 start
            // ���ϲ�����־       
            logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
            logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
            // add by jbpan 20120605 end
			throw new IRollbackException(context, e.getMessage(), e);
		}
		//add by jbpan 20120605 start
        finally
        {
            LoggerResults vResult = LoggerResults.getInstance(); 
    		// ����־��¼���浽�ڴ���
    		vResult.getResult().add(logInfo);
        }     
        //add by jbpan 20120605 end
		return lReturn;
	}
	
	 /**
	  * added by xiong fei 2010-07-29
     * ��ѯ�����ͬ�����мƻ���ϸ
     * ����LoanRepayPlanDao.java��findNewPlanByLoanID()
     * @param aPlanID
     * @return
     * @throws RemoteException
     * @throws IException
     */    
    public Collection findNewPlanByLoanID(long lLoanID,long lPageNo,long lPageLine,long lOrderParam,long lDesc) throws RemoteException, IException
    {
        LoanRepayPlanDao pDao=new LoanRepayPlanDao();
        Collection c=null;
        
        try
        {
            c=pDao.findNewPlanByLoanID(lLoanID,lPageNo,lPageLine,lOrderParam,lDesc);
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
                
        return c;
    }
}
