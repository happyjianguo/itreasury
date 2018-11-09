/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settcontract.bizlogic;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.settcontract.dao.SettContractDAO;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettContractEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3212393459760846163L;
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
	 * No argument constructor required by container.
	 */
	public SettContractEJB()
	{
		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	private static Log4j log4j = null;
	
	private String TransDiscountApplyTableName = "Loan_ContractForm";
	
	/**
	 *合同的保存操作
	*/
	public long save(SettContractInfo info) throws java.rmi.RemoteException, SettlementException
	{
        long lID = -1;
        try
        {
            SettContractDAO dao = new SettContractDAO(this.TransDiscountApplyTableName);
            
            if(info.getId() <= 0)
            {
                //将下一个审批级别设为1
                info.setNextCheckLevel(1);
                info.setStatusID(SETTConstant.SettContractStatus.NOTACTIVE);
                String conCode = "";
				//conCode = LOANConstant.SubLoanType.getCode(info.getSubTypeID());
				 //info.setContractCode(conCode);
                if(info.getContractCode().equals(""))
				conCode = dao.createContractCode(info.getSubTypeID());
                else
                	conCode=info.getContractCode();
                info.setContractCode(conCode);
                Log.print("appCode : " + info.getApplyCode());
				Log.print("conCode : " + conCode);
                info.setLoanID(-1);
                
                dao.setUseMaxID();//取MAX_ID
                
                lID = dao.add(info);
                Log.print("ID : " + lID);
            }
            else
            {
                dao.update(info);
                lID = info.getId();
            }
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return lID;
	}

	/**
	 *合同的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SettlementException
	{
        try
        {
            SettContractDAO dao = new SettContractDAO(this.TransDiscountApplyTableName);
            
            if(lID > 0)
            {
                SettContractInfo info = new SettContractInfo();
                info.setId(lID);
                info.setStatusID(Constant.RecordStatus.INVALID);//状态置为无效
                
                dao.update(info);
            }
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	/**
	 *合同的审核操作
	*/
	/*public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, SettlementException
	{
	    ;
	}*/

	/**
	 *合同的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, SettlementException
	{
        try
        {
            SettContractDAO dao = new SettContractDAO(this.TransDiscountApplyTableName);
            
            if(lID > 0)
            {
                SettContractInfo info = new SettContractInfo();
                info.setId(lID);
                info.setStatusID(SETTConstant.SettContractStatus.CANCEL);//状态置为取消
                
                dao.update(info);
            }
        }
        catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	/**
	 *合同的单笔查询操作
	*/
	public SettContractInfo findByID(long lID) throws java.rmi.RemoteException, SettlementException
	{	    
	    SettContractInfo info = new SettContractInfo();
	    SettContractDAO dao = new SettContractDAO(this.TransDiscountApplyTableName);
        
		if(lID > 0)
		{
			info = (SettContractInfo)dao.findByID(lID);
			try
			{
			    info.setExecuteInterestRate(UtilOperation.getLoanInterestRate(null, -1, lID, null));
			    info.setSettContractAmountInfo(dao.getLateAmount(lID));
			} 
			catch (Exception e1)
			{
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
		}
        return info;
	}

	/**
	 *合同的多笔查询操作
	*/
	public Collection findByMultiOption(SettContractQueryInfo qInfo) throws java.rmi.RemoteException, SettlementException
	{
	    Collection c = null;
        try
        {
            SettContractDAO dao = new SettContractDAO(this.TransDiscountApplyTableName);
            
            c = dao.findByMultiOption(qInfo);
        }
        catch (SettlementDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c;
	}
	/**
	 *合同的分页
	*/
	public PageLoader getMultiOptin(SettContractQueryInfo conditionInfo)throws java.rmi.RemoteException, SettlementException{
		SettContractDAO dao = new SettContractDAO(this.TransDiscountApplyTableName);
		PageLoader pageLoader=dao.getMultiOptin(conditionInfo);	   
		return pageLoader;
	}

}
