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

   
   

    /**	���շ�¼������*/
	public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
	{
		ArrayList rtnList = new ArrayList();
		GLVoucherInfo voucher = new GLVoucherInfo();
		Connection conn = Database.getConnection();
		sett_GLEntryDAO glEntryDAO = new sett_GLEntryDAO(conn);
	
		Timestamp today = Env.getSystemDate(lOfficeID, lCurrencyID);
		System.out.println("��ʼ���շ�¼������,��������:"+ today.toString());		
		Collection c = null;

		try {
			c = glEntryDAO.findByExecuteDate(today, lOfficeID, lCurrencyID);
			System.out.println("������"+ c.size() + "����¼��Ҫ����");
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
				System.out.println("���ڴ����"+ i + "����¼");
				postGLEntry(lOfficeID, lCurrencyID, tmp.getSubjectCode(), tmp.getTransDirection(),tmp.getAmount(),today,conn);
				voucher.addEntryInfo(info);
			}
		}
		
		System.out.println("�������շ�¼������");	
		 voucher.setPostStatusID(Constant.GLPostStatus.SUCCESS);
		rtnList.add(voucher);
		return rtnList;
	}

	/**
	 * 	������¼������
	 *  �� һ����¼�뵽�����У�һ���ǹػ���ϵͳ�����ñ����������������з�¼���������С�
 	*/
	private void postGLEntry(long officeID, long currencyID, String subjectCode, long transDirection,double amount,Timestamp execDate,Connection conn) throws Exception{
		GLBalanceInfo glBalanceInfo = new GLBalanceInfo();
		glBalanceInfo.setOfficeID(officeID);
		glBalanceInfo.setCurrencyID(currencyID);
		glBalanceInfo.setGLSubjectCode(subjectCode);
		glBalanceInfo.setGLDate(execDate);
		Collection c = null;

		System.out.println("��ʼ������¼������");
		
		try {
			sett_GLBalanceDAO glBalanceDAO = new sett_GLBalanceDAO(conn);
			Sett_GLSubjectDefinitionDAO glSubjectDefDAO = new Sett_GLSubjectDefinitionDAO(conn);
			
			c = glBalanceDAO.findByCondition(glBalanceInfo);
			
			if(c==null || c.size() == 0){
			
				System.out.println("û�в�ѯ�����������Ϣ�������¼�¼");
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
							throw new IException(true,"���˷������󣬹���ʧ��",null);
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
					throw new IException(true,"�޷��ҵ���Ŀ��: "+subjectCode + " ��Ӧ�ķ�¼����, ����ʧ��",null);
				}
				
			
			
			}else{		
				System.out.println("��ѯ�����������Ϣ�����¼�¼");
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
					System.out.println("������ϢΪ:");
					System.out.println(UtilOperation.dataentityToString(tmp));
					glBalanceDAO.update(tmp);
				}
			}
		} catch (SQLException e) {
			throw new IException(true,e.getMessage(),e);
		}
		System.out.println("����������¼������");		
		
	}
   
    




   
  
}