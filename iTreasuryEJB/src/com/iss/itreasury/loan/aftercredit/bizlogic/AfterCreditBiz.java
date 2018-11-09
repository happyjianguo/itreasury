package com.iss.itreasury.loan.aftercredit.bizlogic;


import com.iss.itreasury.codingrule.dao.Sys_CodingRuleDao;
import com.iss.itreasury.codingrule.dao.Sys_CodingRuleDetailDao;
import com.iss.itreasury.codingrule.dataentity.CodingRuleInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.aftercredit.dao.AfterCreditDao;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditReportInfo;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditFiveSortInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.system.util.SYSConstant;


import com.iss.itreasury.util.Log;

public class AfterCreditBiz {
	
	
	private AfterCreditDao detailDao=null;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	private long m_luserID = -1;

	public AfterCreditBiz() {

		detailDao = new AfterCreditDao();
	}
	  public long insert(AfterCreditReportInfo info) throws Exception
	  {
		  long resultid = -1;
			try {
				 
				resultid = detailDao.insert(info);
				 
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception();
			}
			return resultid;
	  }
	  
	  
	    /**
	     * 
	     * @return
	     * @throws SettlementException
	     */
		public String buildcode()throws SettlementException
		{
			StringBuffer sb = new StringBuffer();
			String sDate = "";
			String assureCode = "J";
			try{
				sDate = Env.getSystemDateString();
			    //sDate = sDate.replaceAll("-", "");
				System.out.println("sDate_______________________sDate="+sDate);
				sDate = sDate.substring(2, 4);
            // ���ͱ��
			    sb.append(assureCode);
			    //����
			    sb.append(sDate);
			    sb.append("12");
			    long serial = detailDao.getMaxSerial(sb.toString()) + 1;
			    String serialCode = DataFormat.transCode(serial, 4);
			    sb.append(serialCode);
		      }
			catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}

		     return sb.toString();
		}
		
		 /* @return
		 * 
		 * �����鱨��
		 * 
		 * @throws RemoteException
		 * @throws IException
		 */
		public long examinePass(AfterCreditReportInfo lepInfo) throws RemoteException, IException
		{
			//����ʷ������޸ģ�ֻ����������¼��ͬʱ������״̬�ĳ����ύ��
			AfterCreditDao  ldao=new AfterCreditDao ();
			ApprovalTracingInfo info = new ApprovalTracingInfo();//��������Ϣ //������¼�� ��ÿһ�����������ļ�¼�����ApprovalTracingInfo���У�
			ApprovalSettingInfo asInfo = new ApprovalSettingInfo();//��ѯ����������Ϣ
			ApprovalDelegation appbiz = new ApprovalDelegation();
			AfterCreditReportInfo appInfo=null;
			
			/*��������lepInfo*/
			long lOfficeID=lepInfo.getOfficeid();//���´�
			long lCurrencyID=lepInfo.getCurrencyid();//����
			long lLoanID=lepInfo.getId();//��� ��ID
			long lUserID=lepInfo.getUserID();//sessionMng.m_lUserID���Ǹ��û���¼�ģ�
			
			long lNextUserID=lepInfo.getNextcheckuserid();//��һ������� ID
			String strOption=lepInfo.getHeckOpinion();//�������
			/*��������lepInfo���*/
			
			long lModuleID = Constant.ModuleType.LOAN;//����
			long lLoanTypeID = -1;
			long lActionID = Constant.ApprovalAction.DH_1;//�ۺ�����-���Ŷ�ȵ���
			long lApprovalID = -1;
			long lStatusID = Constant.RecordStatus.VALID;//��Ч(��)
			long lResultID = Constant.ApprovalDecision.PASS;//���ͨ��(��)
			long ret=-1;
			long checkLevelID=lepInfo.getNextchecklevel() ;//��һ����������nextchecklevel
			long lLevel = -1;						
			try
			{
				appInfo=ldao.selectReportInfoById(lLoanID);//�õ�����Ϣ
				lLoanTypeID=9;
				long status=appInfo.getStatus() ;//��õ�ǰ״̬
				long nNextCheckUserID=appInfo.getNextcheckuserid();//��һ�������
				if(!(status==LOANConstant.reportState.CHECKING||status==LOANConstant.reportState.SUBMIT))
					throw new IException("Gen_E001");
				ldao.examineUpdate(lepInfo);//�ڱ��� ������һ��������
				
				//���ApprovalID	��������������Ϣ ����������IDֵ��ʧ�ܣ�����ֵ=-1
				lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
				if ( lApprovalID<=0 )
					throw new IException("Gen_E105");
				//��һ������˼���  �����û���˼���
				lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
				lLevel = lLevel-1;
				//��������  ��������������Ϣ �����������̱���Ϣ
				asInfo = appbiz.findApprovalSetting(lApprovalID);
				
				
				
				if (lLevel==asInfo.getTotalLevel())	//���һ�����   asInfo.getTotalLevel();//������������	
				{
					//������״̬�ĳ����ύ  //�޸�����״̬
					ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECK);
					lResultID = Constant.ApprovalDecision.FINISH;		
				}
				else
				{
						//�ж��Ƿ��Խ������
				    if (asInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
					{
				    	//lLoanID ��ID   lLevel ��˼���
				        ldao.updateLoanNextCheckLevel(lLoanID,lLevel);
				        //����״̬ ������
				        ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
				        Log.print("������һ����˼��𣨿�Խ������" + lLevel);
					}
					else
					{
					    //��һ����˼����Զ���һ
						ldao.updateLoanNextCheckLevel(lLoanID,-1);
//						����״̬ ������
				        ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
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
				info.setResultID(lResultID);//��������״̬��  ���������ʾ
				info.setStatusID(lStatusID);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				ret=appbiz.saveApprovalTracing(info);
				System.out.println("SaveApprovalTracing:"+ret);
			}
			catch (IException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException(e.getMessage());
			}
			
			return ret;    	
		}
		/* @return
		 * 
		 * �����弶����
		 * 
		 * @throws RemoteException
		 * @throws IException
		 */
		public long fiveSortPass(AfterCreditFiveSortInfo lepInfo) throws RemoteException, IException
		{
			//����ʷ������޸ģ�ֻ����������¼��ͬʱ������״̬�ĳ����ύ��
			AfterCreditDao  ldao=new AfterCreditDao ();
			ApprovalTracingInfo info = new ApprovalTracingInfo();//��������Ϣ //������¼�� ��ÿһ�����������ļ�¼�����ApprovalTracingInfo���У�
			ApprovalSettingInfo asInfo = new ApprovalSettingInfo();//��ѯ����������Ϣ
			ApprovalDelegation appbiz = new ApprovalDelegation();
			AfterCreditFiveSortInfo appInfo=null;
			
			/*��������lepInfo*/
			long lOfficeID=lepInfo.getOfficeid();//���´�
			long lCurrencyID=lepInfo.getCurrencyid();//����
			long lLoanID=lepInfo.getId();//��� ��ID
			long lUserID=lepInfo.getUserID();//sessionMng.m_lUserID���Ǹ��û���¼�ģ�
			
			long lNextUserID=lepInfo.getNextcheckuserid();//��һ������� ID
			String strOption=lepInfo.getHeckOpinion();//�������
			/*��������lepInfo���*/
			
			long lModuleID = Constant.ModuleType.LOAN;//����
			long lLoanTypeID = -1;
			long lActionID = Constant.ApprovalAction.DH_1;
			long lApprovalID = -1;
			long lStatusID = Constant.RecordStatus.VALID;//��Ч(��)
			long lResultID = Constant.ApprovalDecision.PASS;//���ͨ��(��)
			long ret=-1;
			long checkLevelID=lepInfo.getNextchecklevel() ;//��һ����������nextchecklevel
			long lLevel = -1;
//			System.out.println("nextcheckuser:"+lNextUserID);
//			System.out.println("interestRate:"+lepInfo.getInterestRate() );
							
			try
			{
				appInfo=ldao.selectFiveSortInfoById(lLoanID);//�õ�����Ϣ
//				lLoanTypeID=appInfo.getTypeID();
				lLoanTypeID=14;//ԭʼֵ 13
				long status=appInfo.getStatus() ;//��õ�ǰ״̬
				long nNextCheckUserID=appInfo.getNextcheckuserid();//��һ�������
				
				System.out.println("  ״̬ status: " + status);
				/*
				if ( nNextCheckUserID!=lUserID )
					throw new IException("Gen_E001");
				*/
				//�жϵ�ǰ״̬ �Ƿ��� �ύ״̬ �� ������״̬ 
				//if ( status!=LOANConstant.CreditState.SUBMIT_ADJUST )
					//throw new IException("Gen_E001");
				if(!(status==LOANConstant.reportState.CHECKING||status==LOANConstant.reportState.SUBMIT))
					throw new IException("Gen_E001");
				
				System.out.println("�¼������ˣ�" + lepInfo.getNextcheckuserid());
				
				ldao.fiveSortUpdate(lepInfo);//�ڱ��� ������һ��������
				
				//���ApprovalID	��������������Ϣ ����������IDֵ��ʧ�ܣ�����ֵ=-1
				lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
				if ( lApprovalID<=0 )
					throw new IException("Gen_E105");
				//��һ������˼���  �����û���˼���
				lLevel = appbiz.findApprovalUserLevel(lApprovalID, lNextUserID);
				
				System.out.println(" ��һ�������˼���" + lLevel);
				lLevel = lLevel-1;
				Log.print("��һ������˼���" + lLevel);
				//��������  ��������������Ϣ �����������̱���Ϣ
				asInfo = appbiz.findApprovalSetting(lApprovalID);
				
				System.out.println("���һ����� : " + asInfo.getTotalLevel());
				//if (checkLevelID==asInfo.getTotalLevel())ԭ
				System.out.println(" ���һ������ lLevel : " + lLevel);
				System.out.println(" ���һ������         : " + asInfo.getTotalLevel());
				if (lLevel==asInfo.getTotalLevel())	//���һ�����   asInfo.getTotalLevel();//������������	
				{
					//������״̬�ĳ����ύ  //�޸�����״̬
					ldao.updateLoanStatuss(lLoanID,lUserID,LOANConstant.reportState.CHECK);
					
					//������������Ϣ���Ƶ���ͬ��
					//ContractDao conDao=new ContractDao();
					//conDao.insert(lLoanID,appInfo.getTypeID());
					lResultID = Constant.ApprovalDecision.FINISH;		
				}
				else
				{
						//�ж��Ƿ��Խ������
				    if (asInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
					{
				    	//lLoanID ��ID   lLevel ��˼���
				        ldao.updateLoanNextCheckLevels(lLoanID,lLevel);
				        //����״̬ ������
				        ldao.updateLoanStatuss(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
				        Log.print("������һ����˼��𣨿�Խ������" + lLevel);
					}
					else
					{
					    //��һ����˼����Զ���һ
						ldao.updateLoanNextCheckLevels(lLoanID,-1);
//						����״̬ ������
				        ldao.updateLoanStatus(lLoanID,lUserID,LOANConstant.reportState.CHECKING);
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
				info.setResultID(lResultID);//��������״̬��  ���������ʾ
				info.setStatusID(lStatusID);
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);
				ret=appbiz.saveApprovalTracing(info);
				System.out.println("SaveApprovalTracing:"+ret);
			}
			catch (IException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException(e.getMessage());
			}
			
			return ret;    	
		}
		//added by mk 2010-01-11 ɾ����������¼
	    public long deleteAfterCredit(long[] lDeleteID) 
	    {
	    	long id=-1;
	    	AfterCreditReportInfo info = new AfterCreditReportInfo();
	    	AfterCreditDao dao = new AfterCreditDao(info);
			
	        try {
		        	for(int i=0;i<lDeleteID.length;i++)
		        	{
		        		AfterCreditReportInfo afterCreditReportInfo =new AfterCreditReportInfo();
		        		afterCreditReportInfo.setId(lDeleteID[i]);
		        		afterCreditReportInfo.setStatus(SYSConstant.CodingRuleStatus.DELETED);
		        		dao.update(afterCreditReportInfo);
		        	}
		        	id=1;
				} catch (ITreasuryDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return id;
	    }
		//added by mk 2010-01-12 ɾ����������弶�����¼ 
	    public long deleteAfterCreditFiveSort(long[] lDeleteID) 
	    {
	    	long id=-1;
	    	AfterCreditFiveSortInfo info =new AfterCreditFiveSortInfo();
	    	AfterCreditDao dao = new AfterCreditDao(info);
			
	        try {
		        	for(int i=0;i<lDeleteID.length;i++)
		        	{
		        		AfterCreditFiveSortInfo afterCreditReportInfo =new AfterCreditFiveSortInfo();
		        		afterCreditReportInfo.setId(lDeleteID[i]);
		        		afterCreditReportInfo.setStatus(SYSConstant.CodingRuleStatus.DELETED);
		        		dao.update(afterCreditReportInfo);
		        	}
		        	id=1;
				} catch (ITreasuryDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return id;
	    }
		
}
