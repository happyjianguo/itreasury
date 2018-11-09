/*
 * Created on 2004-2-11
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.isoftstone;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;

import com.iss.itreasury.settlement.generalledger.dao.Sett_GLSubjectDefinitionDAO;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLBalanceDAO;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;

import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;

;
/**
 * @author lixr
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLISoftStoneBean extends GLExtendBaseBean
{
    public static void main(String[] args)
    {
    }

   
   

    /**	日终分录过总账*/
	public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
	{
		ArrayList rtnList = new ArrayList();
		GLVoucherInfo voucher = new GLVoucherInfo();
		Connection conn = Database.getConnection();
		sett_GLEntryDAO glEntryDAO = new sett_GLEntryDAO(conn);
	
		Timestamp today = Env.getSystemDate(lOfficeID, lCurrencyID);
		System.out.println("开始日终分录过总账,　今天是:"+ today.toString());		
		Collection c = null;

		try {
			c = glEntryDAO.findByExecuteDate(today, lOfficeID, lCurrencyID);
			System.out.println("本日有"+ c.size() + "条分录需要过账");
		} catch (Exception e) {
			throw e;
			
		}
		int i = 0;
		if(c != null && !c.isEmpty()){
			Iterator it = c.iterator();
			while(it.hasNext()){
				com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo tmp = new com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo();
				tmp = (com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo) it.next();
				
				GLEntryInfo info = new GLEntryInfo();
				
				info.setAbstract(tmp.getAbstract());
                info.setAmount(tmp.getAmount());
                info.setCurrencyID(tmp.getCurrencyID());
                info.setOfficeID(tmp.getOfficeID());
                info.setDirectionID(tmp.getTransDirection());
                info.setExecute(tmp.getExecute());
                info.setInterestStart(tmp.getInterestStart());
                info.setSubject(tmp.getSubjectCode());
                info.setTransNo(tmp.getTransNo());
                
				
				i++; 
				System.out.println("正在处理第"+ i + "条分录");
				postGLEntry(lOfficeID, lCurrencyID, tmp.getSubjectCode(), tmp.getTransDirection(),tmp.getAmount(),today,conn);
				voucher.addEntryInfo(info);
			}
		}
		
		System.out.println("结束日终分录过总账");	
		 voucher.setPostStatusID(Constant.GLPostStatus.SUCCESS);
		rtnList.add(voucher);
		return rtnList;
	}

	/**
	 * 	单条分录过总账
	 *  将 一条分录入到总账中，一般是关机后，系统将调用本方法，逐条将所有分录过到总账中。
 	*/
	private void postGLEntry(long officeID, long currencyID, String subjectCode, long transDirection,double amount,Timestamp execDate,Connection conn) throws Exception{
		GLBalanceInfo glBalanceInfo = new GLBalanceInfo();
		glBalanceInfo.setOfficeID(officeID);
		glBalanceInfo.setCurrencyID(currencyID);
		glBalanceInfo.setGLSubjectCode(subjectCode);
		glBalanceInfo.setGLDate(execDate);
		Collection c = null;

		System.out.println("开始单条分录过总账");
		
		try {
			sett_GLBalanceDAO glBalanceDAO = new sett_GLBalanceDAO(conn);
			Sett_GLSubjectDefinitionDAO glSubjectDefDAO = new Sett_GLSubjectDefinitionDAO(conn);
			
			c = glBalanceDAO.findByCondition(glBalanceInfo);
			
			if(c==null || c.size() == 0){
			
				System.out.println("没有查询到总账余额信息，产生新记录");
				GLSubjectDefinitionInfo glSubjectDefInfo = glSubjectDefDAO.findByOldCode(subjectCode);
				
				
				if(glSubjectDefInfo != null && glSubjectDefInfo.getID() > 0){
					
					glSubjectDefInfo.setBalanceDirection(SETTConstant.SubjectAttribute.getDirection(glSubjectDefInfo.getSubjectType()));
					GLBalanceInfo tmp = new GLBalanceInfo(); 
					if(glSubjectDefInfo.getBalanceDirection() == 9)
						tmp.setBalanceDirection(1);
					else
						tmp.setBalanceDirection(glSubjectDefInfo.getBalanceDirection());
						
					if(transDirection == SETTConstant.DebitOrCredit.DEBIT){
						tmp.setDebitAmount(amount);
						tmp.setCreditAmount(0.0);
						tmp.setDebitNumber(1);
						tmp.setCreditNumber(0);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
							tmp.setDebitBalance(amount);
						}else if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.CREDIT){
							tmp.setCreditBalance((-1)*amount);
						}else{
							throw new IException(true,"过账发生错误，过账失败",null);
						}
					}else{
						tmp.setCreditAmount(amount);
						tmp.setDebitNumber(0);
						tmp.setCreditNumber(1);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
								tmp.setDebitBalance((-1)*amount);
							}else{
								tmp.setCreditBalance(amount);					
							}	
				    }
				
					tmp.setOfficeID(officeID);
					tmp.setCurrencyID(currencyID);
					tmp.setGLDate(execDate);
					tmp.setGLSubjectCode(subjectCode);
					glBalanceDAO.add(tmp);
				}else{
					throw new IException(true,"无法找到科目号: "+subjectCode + " 对应的分录设置, 过账失败",null);
				}
				
			
			
			}else{		
				System.out.println("查询到总账余额信息，更新记录");
				Iterator it = c.iterator();			    
				while(it.hasNext()){
					GLBalanceInfo tmp = (GLBalanceInfo) it.next();
					if(transDirection == SETTConstant.DebitOrCredit.DEBIT){
						tmp.setDebitAmount(tmp.getDebitAmount() + amount);
						tmp.setDebitNumber(tmp.getDebitNumber() + 1);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
							tmp.setDebitBalance(tmp.getDebitBalance() + amount);
						}else{
							tmp.setCreditBalance(tmp.getCreditBalance() - amount);
						}
					}else{
						tmp.setCreditAmount(tmp.getCreditAmount() + amount);
						tmp.setCreditNumber(tmp.getCreditNumber() + 1);
						if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
							tmp.setDebitBalance(tmp.getDebitBalance() - amount);
						}else{
							tmp.setCreditBalance(tmp.getCreditBalance() + amount);
						}
					}
					System.out.println("更新信息为:");
					System.out.println(UtilOperation.dataentityToString(tmp));
					glBalanceDAO.update(tmp);
				}
			}
		} catch (SQLException e) {
			throw new IException(true,e.getMessage(),e);
		}
		System.out.println("结束单条分录过总账");		
		
	}
   
    




   
  
}