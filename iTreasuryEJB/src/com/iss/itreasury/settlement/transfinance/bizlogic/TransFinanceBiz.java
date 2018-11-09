package com.iss.itreasury.settlement.transfinance.bizlogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.iss.itreasury.loan.bizdelegation.LeaseholdNoticeDelegation;
import com.iss.itreasury.loan.contract.bizlogic.ContractBiz;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.repayplan.dao.RepayPlanDao;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.settlement.transfinance.dao.Sett_TransReturnFinanceDao;
import com.iss.itreasury.settlement.transfinance.dataentity.TransQueryFinanceNewInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
/**
 * @author afzhu
 * �������޿��㡢���������ҵ������
 */
public class TransFinanceBiz {
	
	
	Sett_TransReturnFinanceDao dao=null;
	TransFinance tfejb = null;
	
	public TransFinanceBiz() throws Exception{
		
		try
		{
			TransFinanceHome home = (TransFinanceHome) EJBHomeFactory.getFactory().lookUpHome(TransFinanceHome.class);
			tfejb = (TransFinance) home.create();
		}
		catch (Exception e)
		{
			throw new Exception();
		}
	}
	public List getFinanceRentList(TransQueryFinanceNewInfo queryEntity) throws Exception
	{
		LeaseholdNoticeDelegation leaseholdNoticeDelegation = new LeaseholdNoticeDelegation();	
		ContractBiz contractBiz = new ContractBiz();
		//add by yunchang
		//date 2010-08-06
		//function ��������
		double surplusRecognizanceAmount = 0.0d;
		double recognizanceAmount = 0.0d;
		double allNoPayMount = 0.0d;
		String issueList = "";
		ContractInfo contractInfo = new ContractInfo();
		RepayPlanDao repayPlan = new RepayPlanDao();
		RepayPlanInfo repayPlanInfo = new RepayPlanInfo();
		List returnList = null;
		try{
			ContractDao cd = new ContractDao();
			dao=new Sett_TransReturnFinanceDao();
			List testList=dao.getFinanceRentList(queryEntity);
			returnList = new ArrayList();
			long contractIds[] = new long[testList.size()];
			String ts =queryEntity.getExecuteDate();
			//add by zwxiao ��ѯ����δ��������ʱ��ʹ�ÿ���ʱ������ѯ
			String systemDate = Env.getSystemDateString(queryEntity.getNofficeid(), queryEntity.getNcurrencyid());//����ʱ��
			TransReturnFinanceNewInfo trfn=null;
			//˫��ѭ���ϲ���¼
			for(int i=0;i<testList.size();i++)
			{
				
				trfn=(TransReturnFinanceNewInfo)testList.get(i);
				RateInfo ri = cd.getLatelyRateForRZZL(trfn.getContractID(),DataFormat.getDateTime(queryEntity.getExecuteDate()));//��õ����������
	//			RateInfo ri = cd.getLatelyRateForRZZLPlan(trfn.getContractID(),DataFormat.getDateTime(queryEntity.getExecuteDate()));//��õ����������
				trfn.setSissue(new Integer(trfn.getIssue()).toString());
				trfn.setRate(ri.getRate());//��������
				contractIds[i]=trfn.getContractID();//��ͬID����,����������δ���������ۼƳٸ�����
				for(int j=0;j<testList.size();j++)
				{
					if(i==j)continue;//�������ͬ��¼������
					if(trfn.getContractID()==((TransReturnFinanceNewInfo)testList.get(j)).getContractID())//ѭ��ƥ����ͬ��ͬ
					{
						trfn.setSissue(trfn.getSissue()+","+((TransReturnFinanceNewInfo)testList.get(j)).getIssue());// �������ϲ�
						trfn.setCurrentPaymentAmount(trfn.getCurrentPaymentAmount()+((TransReturnFinanceNewInfo)testList.get(j)).getCurrentPaymentAmount());//�ѱ��ڻ���������
						trfn.setRepaymentBalance(trfn.getCurrentPaymentAmount());//�ѻ��������Ĭ�ϸ�ֵΪ���ڻ�������ܺ�
						trfn.setPrincipal(trfn.getPrincipal()+((TransReturnFinanceNewInfo)testList.get(j)).getPrincipal());//�ѱ������
						trfn.setInterest(trfn.getInterest()+((TransReturnFinanceNewInfo)testList.get(j)).getInterest());//����Ϣ���
						testList.remove(j);//�Ƴ���ͬ��ͬ����ֹ�ظ��ϲ�
						j=j-1;//���ںϲ����ļ�¼��ɾ����list�м�¼��ǰ�滻������Ҫ��ѭ������һ�ؿ�ʼ
					}
				}
				returnList.add(trfn);//���պϲ���ķ��ؼ�
			}
			if(testList!=null && testList.size() > 0)
			{
				List ljcf = LOANNameRef.getContractLateSum(contractIds);//�ۼƳٸ�����
				List lxwf = LOANNameRef.getContractOutstandingSum(contractIds,systemDate);//����δ������
				List hkye = dao.getRepaymentBalance(contractIds);//�ѻ����ܶ���
				
				//�ۼƳٸ�����
				for(int n=0;n<ljcf.size();n++)
				{
					TransReturnFinanceNewInfo trfn_ljcf=(TransReturnFinanceNewInfo)ljcf.get(n);
					for(int f=0;f<returnList.size();f++)
					{
						trfn=(TransReturnFinanceNewInfo)returnList.get(f);
						if(trfn_ljcf.getContractID()==trfn.getContractID())
						{
							trfn.setLjcf(trfn_ljcf.getLjcf());
							//add by yunchang
							//date 2010-08-05
							//��ӱ�֤������ѯ
							surplusRecognizanceAmount = leaseholdNoticeDelegation.getSurplusRecognizanceAmount(trfn.getContractID());
							trfn.setSurplusRecognizanceAmount(surplusRecognizanceAmount);
							//add by yunchang
							//date 2010-08-05
							//function ���Ҫ��֤����
							contractInfo = contractBiz.findByID(trfn.getContractID());
							trfn.setRecognizanceAmount(contractInfo.getRecognizanceAmount());
							//add by yunchang
							//date 2010-08-05
							//function ���������������Ǳ��տ�֪ͨ����
							repayPlanInfo = repayPlan.getLastPayMount(trfn.getContractID(), ts); 
							trfn.setLastPayMount(repayPlanInfo.getDPlanPayAmount());//����Ӧ�����
							trfn.setLastPayMountAll(DataFormat.formatDouble(repayPlanInfo.getDPlanPayAmount(),2)+DataFormat.formatDouble(repayPlanInfo.getMINTERESTAMOUNT(),2));//����Ӧ���ܼ�
							trfn.setIssuebenqi(repayPlanInfo.getIssue());//������𳥸���Ӧ����
							//add by yunchang
							//date 2010-08-05
							//function ��������δ�����
							allNoPayMount = repayPlan.getAllNoPayMount(trfn.getContractID(), systemDate);
							trfn.setAllNoPayMount(allNoPayMount);
							//add by yunchang
							//date 2010-08-07
							//function ��������δ������
							issueList = repayPlan.getIssueList(trfn.getContractID(), systemDate);
							trfn.setIssueList(issueList);
							returnList.set(f, trfn);							
						}
					}
					
				}
				//����δ������
				for(int m=0;m<lxwf.size();m++)
				{
					TransReturnFinanceNewInfo trfn_lxwf=(TransReturnFinanceNewInfo)lxwf.get(m);
					for(int h=0;h<returnList.size();h++)
					{
						trfn=(TransReturnFinanceNewInfo)returnList.get(h);
						if(trfn_lxwf.getContractID()==trfn.getContractID())
						{
							trfn.setLxwf(trfn_lxwf.getLxwf());
							//trfn.setAllNoPayMount(trfn_lxwf.getAllNoPayMount());
							returnList.set(h, trfn);
						}
					}
					
				}
				//�ѻ����ܶ���
				if(hkye!=null && hkye.size()>0)
				{
					for(int m=0;m<hkye.size();m++)
					{
						TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)hkye.get(m);
						for(int h=0;h<returnList.size();h++)
						{
							trfn=(TransReturnFinanceNewInfo)returnList.get(h);
							if(trfn_hkye.getContractID()==trfn.getContractID())
							{
								trfn.setRepaymentBalance(trfn.getMexamineamount()-trfn_hkye.getAmountfrom());//��׼����ȥ�ѻ����ܶ�
								returnList.set(h, trfn);
							}
//							else
//							{
//								trfn.setRepaymentBalance(trfn.getMexamineamount());//��׼����ȥ�ѻ����ܶ�
//								returnList.set(h, trfn);
//							}
						}
						//hkye.remove(m);
						
					}
				}
				else
				{
					//double countMoney = 0.0;
					for(int m=0;m<returnList.size();m++)
					{
						TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)returnList.get(m);
						trfn_hkye.setRepaymentBalance(trfn_hkye.getMexamineamount());
						returnList.set(m, trfn_hkye);
						/*countMoney = trfn_hkye.getCurrentPaymentAmount();//���ڻ������
						for(int h=0;h<returnList.size();h++)
						{
							if(m==h)continue;
							trfn=(TransReturnFinanceNewInfo)returnList.get(h);
							if(trfn_hkye.getContractID()==trfn.getContractID())
							{
								countMoney  += trfn.getCurrentPaymentAmount();//��ͬһ�ʺ�ͬ�ı���Ӧ��������
							}
						}
						trfn_hkye.setRepaymentBalance(countMoney);
						returnList.set(m, trfn_hkye);*/
					}
						
					
				}
						
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
		
	}
	/**
	 * @author afzhu
	 * @param contractIds ��ͬID�ַ���
	 * @return
	 * ͨ����ͬID��ѯ�������޿���ʵ��
	 */
	public List getFinanceRentListByContractId(String contractIds,int flag,String exeDate) throws Exception
	{
		
		List returnList = new ArrayList();
		try{
		String sContractId[] = contractIds.split(",");
		int contractId[] = new int[sContractId.length];
		for(int i=0;i<sContractId.length;i++)
		{
			contractId[i]=Integer.parseInt(sContractId[i]);
		}
		dao=new Sett_TransReturnFinanceDao();
		List testList = dao.getFinanceRentListByContractId(contractId,exeDate);
		if(flag==1)return testList;
		TransReturnFinanceNewInfo trfn=null;
		//˫��ѭ���ϲ���¼
		for(int i=0;i<testList.size();i++)
		{
			trfn=(TransReturnFinanceNewInfo)testList.get(i);
			trfn.setSissue(new Integer(trfn.getIssue()).toString());
			for(int j=0;j<testList.size();j++)
			{
				if(i==j)continue;//�������ͬ��¼������
				if(trfn.getContractID()==((TransReturnFinanceNewInfo)testList.get(j)).getContractID())//ѭ��ƥ����ͬ��ͬ
				{
					trfn.setSissue(trfn.getSissue()+","+((TransReturnFinanceNewInfo)testList.get(j)).getIssue());// �������ϲ�
					trfn.setCurrentPaymentAmount(trfn.getCurrentPaymentAmount()+((TransReturnFinanceNewInfo)testList.get(j)).getCurrentPaymentAmount());//�ѱ��ڻ���������
					testList.remove(j);//�Ƴ���ͬ��ͬ����ֹ�ظ��ϲ�
					j=j-1;//���ںϲ����ļ�¼��ɾ����list�м�¼��ǰ�滻������Ҫ��ѭ������һ�ؿ�ʼ
				}
			}
			returnList.add(trfn);//���պϲ���ķ��ؼ�
			
			/************/
			//��������
			/*
			for(int j=0;j<10;j++)
			{
			TransReturnFinanceNewInfo trfn1=new TransReturnFinanceNewInfo();
			trfn1.setClientName(trfn.getClientName());
			trfn1.setContractID(trfn.getContractID());
			trfn1.setContractCode(trfn.getContractCode());
			trfn1.setStartDate(trfn.getStartDate());
			returnList.add(trfn1);
			}
			*/
			//��������
			/************/
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
		
	}
	/**
	 * @author afzhu
	 * @return
	 * ��ѯ��ͬ��ӡ�����ˮ��
	 */
	public TransReturnFinanceNewInfo.SubReturnFinance getContractNum() throws Exception
	{
		
		TransReturnFinanceNewInfo.SubReturnFinance subrf =null;
		try
		{
		dao=new Sett_TransReturnFinanceDao();
		subrf= dao.getContractNum();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return subrf;
	}
	
	/**
	 * @author afzhu
	 * @param sub ��ͬ�洢��ˮ��ʵ��(�ڲ���)
	 * @throws Exception
	 */
	public void saveFinance(TransReturnFinanceNewInfo.SubReturnFinance sub) throws Exception
	{
		try
		{
		dao=new Sett_TransReturnFinanceDao();
		dao.saveFinance(sub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
	/**
	 * @author afzhu
	 * @param queryEntity ��ѯ����ʵ��
	 * @return ������������List
	 * @throws Exception
	 */
	public List getQuantityRepaymentList(TransQueryFinanceNewInfo queryEntity) throws Exception
	{
			
		List returnList = null;
		try{
			
			dao=new Sett_TransReturnFinanceDao();
			System.out.println("========================��ѯ��������ʵ���б�==========================");
			returnList=dao.getFinanceRentList(queryEntity);
			System.out.println("========================��ѯ��������ʵ���б�==========================");
			long contractIds[] = new long[returnList.size()];
			String ts =queryEntity.getExecuteDate();
			//add by zwxiao ��ѯ����δ��������ʱ��ʹ�ÿ���ʱ������ѯ
			String systemDate = Env.getSystemDateString(queryEntity.getNofficeid(), queryEntity.getNcurrencyid());//����ʱ��
			TransReturnFinanceNewInfo trfn=null;
			for(int i=0;i<contractIds.length;i++)
			{
				trfn = (TransReturnFinanceNewInfo)returnList.get(i);
				contractIds[i]= trfn.getContractID();
			}
			if(returnList!=null && returnList.size()>0)
			{
					System.out.println("========================��ȡ�ۼƳٸ�����==========================");
					List ljcf = LOANNameRef.getContractLateSum(contractIds);//�ۼƳٸ�����
					System.out.println("========================��ȡ�ۼƳٸ�����==========================");
					System.out.println("========================��ȡ����δ������==========================");
					List lxwf = LOANNameRef.getContractOutstandingSum(contractIds,systemDate);//����δ������
					System.out.println("========================��ȡ����δ������==========================");
					System.out.println("========================��ȡ�ѻ����ܶ���==========================");
					List hkye = dao.getRepaymentBalance(contractIds);//�ѻ����ܶ���
					System.out.println("========================��ȡ�ѻ����ܶ���==========================");
					//�ۼƳٸ�����
					for(int n=0;n<ljcf.size();n++)
					{
						TransReturnFinanceNewInfo trfn_ljcf=(TransReturnFinanceNewInfo)ljcf.get(n);
						for(int f=0;f<returnList.size();f++)
						{
							trfn=(TransReturnFinanceNewInfo)returnList.get(f);
							if(trfn_ljcf.getContractID()==trfn.getContractID())
							{
								trfn.setLjcf(trfn_ljcf.getLjcf());
								returnList.set(f, trfn);
							}
						}
						
					}
					//����δ������
					for(int m=0;m<lxwf.size();m++)
					{
						TransReturnFinanceNewInfo trfn_lxwf=(TransReturnFinanceNewInfo)lxwf.get(m);
						for(int h=0;h<returnList.size();h++)
						{
							trfn=(TransReturnFinanceNewInfo)returnList.get(h);
							if(trfn_lxwf.getContractID()==trfn.getContractID())
							{
								trfn.setLxwf(trfn_lxwf.getLxwf());
								returnList.set(h, trfn);
							}
						}
						
					}
					//�ѻ����ܶ���
					if(hkye!=null && hkye.size()>0)
					{
						for(int m=0;m<hkye.size();m++)
						{
							TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)hkye.get(m);
							for(int h=0;h<returnList.size();h++)
							{
								trfn=(TransReturnFinanceNewInfo)returnList.get(h);
								if(trfn_hkye.getContractID()==trfn.getContractID())
								{
									trfn.setRepaymentBalance(trfn.getMexamineamount()-trfn_hkye.getAmountfrom());//��׼����ȥ�ѻ����ܶ�
									returnList.set(h, trfn);
								}
							}
							
						}
					}
					else
					{
						//double countMoney = 0.0;
						for(int m=0;m<returnList.size();m++)
						{
							TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)returnList.get(m);
							trfn_hkye.setRepaymentBalance(trfn_hkye.getMexamineamount());
							returnList.set(m, trfn_hkye);
							/*countMoney = trfn_hkye.getCurrentPaymentAmount();//���ڻ������
							for(int h=0;h<returnList.size();h++)
							{
								if(m==h)continue;
								trfn=(TransReturnFinanceNewInfo)returnList.get(h);
								if(trfn_hkye.getContractID()==trfn.getContractID())
								{
									countMoney  += trfn.getCurrentPaymentAmount();//��ͬһ�ʺ�ͬ�ı���Ӧ��������
								}
							}
							trfn_hkye.setRepaymentBalance(countMoney);
							returnList.set(m, trfn_hkye);*/
						}
				
					
					}
					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
		
	}
	

	/**
	 * ��������
	 * @author afzhu
	 * @param qt_quantityRepaymentList ǰ̨�����Ҫ�����ʵ���༯��
	 * @param balanceType ���㷽ʽ
	 */
	public List quantityRepaymentBalance(List qt_quantityRepaymentList,String balanceType,TransQueryFinanceNewInfo queryEntity) throws Exception
	{
		TransReturnFinanceNewInfo trfn = null;
		TransReturnFinanceNewInfo return_trfn = null;
		List failList = new ArrayList();//����ʧ�ܼ�¼�����
	//	List successList = new ArrayList();//����ɹ���¼�����
		List groupEach = null;//�洢ÿ������¼
		List finalList = null;
		dao=new Sett_TransReturnFinanceDao();
		try
		{
			//�ѽ���ɹ���ʧ�ܵļ�¼���뿪
			for(int i=0;i<qt_quantityRepaymentList.size();i++)
			{
				groupEach = (ArrayList)qt_quantityRepaymentList.get(i);
				System.out.println("========================�����������---��ʼ�������˻��ͱ�֤���˻�==========================");
				System.out.println("========================�����������---�����������˻��ͱ�֤���˻�==========================");
				System.out.println("========================�����������---��ʼ�������˻��ͱ�֤���˻�==========================");
				List test = dao.checkAccount(groupEach, balanceType);//�������ʻ�
				System.out.println("========================�����������---�����������˻��ͱ�֤���˻�==========================");
				List successGroup = new ArrayList();//����ɹ���¼��
				for(int j=0;j<test.size();j++)
				{
					return_trfn = (TransReturnFinanceNewInfo)test.get(j);
					if(return_trfn.getFlag()==0)//���������¼����ɹ�
					{
						successGroup.add(return_trfn);
					}
					else//���������¼����ʧ��
					{
						failList.add(return_trfn);
					}
				}
				List fail = quantityRepaymentBalance_createRecord(successGroup,balanceType);//����ÿһ��,���ص���ʧ�ܵļ�¼
				if(fail!=null && fail.size()!=0)//������ڽ���ʧ�ܽ����,�ǰ�ÿ���ʧ�ܽ����ӵ�ʧ�ܽ������
				{
					failList.addAll(fail);
				}
				//successList.add(successGroup);
				
			}
			
			//�Խ���ɹ��ļ�¼������Ӧ�����ݿ��¼
			/*for(int i = 0;i<successList.size();i++)//�ֱ���ÿ������¼,�������һ���׳��쳣,����Ӱ��������
			{
				List testGroup = (ArrayList)successList.get(i);
				List fail = quantityRepaymentBalance_createRecord(testGroup,balanceType);//����ÿһ��,���ص���ʧ�ܵļ�¼
				if(fail!=null && fail.size()!=0)//������ڽ���ʧ�ܽ����,�ǰ�ÿ���ʧ�ܽ����ӵ�ʧ�ܽ������
				{
					failList.addAll(fail);
				}
				
			}*/
			finalList =getQuantityRepaymentList(queryEntity);////����������,Ҫ����ִ��һ����㹦��,Ȼ���ʧ�ܽ���ͼ������ϲ�����
			for(int m =0;m<finalList.size();m++)
			{
				TransReturnFinanceNewInfo test1=(TransReturnFinanceNewInfo)finalList.get(m);
				for(int n=0;n<failList.size();n++)
				{
					TransReturnFinanceNewInfo test2=(TransReturnFinanceNewInfo)failList.get(n);
					if(test1.getContractID() == test2.getContractID() && test1.getIssue()==test2.getIssue())
					{
						test2.setRepaymentBalance(test1.getRepaymentBalance());//�������»������
						test2.setLxwf(test1.getLxwf());//�������µ�����δ������
						finalList.set(m, test2);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}
		return finalList;
	}
	
	/**
	 * �����������ɼ�¼
	 * @author afzhu
	 * @param qt_quantityRepaymentList
	 * @param balanceType
	 * @return
	 */
	public List quantityRepaymentBalance_createRecord(List successGroup,String balanceType)
	{
		List failList = new ArrayList();
		TransReturnFinanceNewInfo trfn = null;
		int i=0;
		try{
			for(i=0;i<successGroup.size();i++)
			{
				trfn = (TransReturnFinanceNewInfo)successGroup.get(i);
				int isFail = tfejb.quantityRepaymentBalance_createRecord(trfn, balanceType);
				if(isFail==-1)
				{
					new Exception();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//�����������һ����¼����ʧ��,��ô������¼֮������м�¼Ĭ��Ϊʧ��
			for(int j = i ;j<successGroup.size();j++)
			{
				trfn = (TransReturnFinanceNewInfo)successGroup.get(j);
				trfn.setRemark("ʧ��:���ɻ�Ʒ�¼���˻���ϸʱ��ۿ�ʱ����.ԭ��:"+e.getMessage());
				trfn.setFlag(-1);
				failList.add(trfn);
			}
		}
		return failList;
	}
	/**
	 * 
	 * ��������---���Ӳ���
	 * @author afzhu
	 * @param userid ��ǰ�û�ID
	 * @param exeDate ִ����(������)
	 * @return
	 * @throws Exception
	 */
	public List getHrefFindList(long userid,String exeDate) throws Exception
	{
		List returnList = new ArrayList();
		dao=new Sett_TransReturnFinanceDao();
		System.out.println("===============��ȡ���Ӳ����б�==============");
		returnList=dao.getHrefFindList(userid, exeDate);
		System.out.println("===============��ȡ���Ӳ����б�==============");
		return returnList;
	}
	
	/**
	 * ��������---���Ӳ���---ɾ������
	 * @author afzhu
	 * @param deleteParam
	 * @throws Exception
	 */
	public List hrefFindDelete(String deleteParam,long userid,String exeDate) throws Exception
	{
		System.out.println("===============���Ӳ���ɾ����¼��ʼ==============");
		tfejb.hrefFindDelete(deleteParam);
		System.out.println("===============���Ӳ���ɾ����¼����==============");
		List returnList = getHrefFindList(userid,exeDate);
		return returnList;
	}
	
	/**
	 * ��������----���Ӳ���---�����ۼƳٸ�
	 * @author afzhu
	 * @param transNo
	 * @throws Exception
	 */
	public List  updateLjcf(String transNo,long userid,String exeDate) throws Exception
	{
		int flag=0;//0Ϊ����,-1Ϊʧ��
		List returnList = null;
		try
		{
			JSONObject jo=new JSONObject(transNo);
			String transNoArr[] = jo.getString("transNo").split("&");
			returnList = getHrefFindList(userid,exeDate);
			dao=new Sett_TransReturnFinanceDao();
			dao.updateLjcf(transNoArr);
			returnList.add(new Integer(flag));
		}
		catch(Exception e)
		{
			flag=-1;
			if(returnList!=null)
			{
				returnList.add(new Integer(flag));
			}
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
	}
	
	/**
	 * @param contractids
	 * 
	 * �ж���������--���Ӳ���--ɾ���Ƿ������ǰ����
	 * 
	 */
	public String checkHrefFindDel(String contractids) throws Exception
	{
		dao=new Sett_TransReturnFinanceDao();
		String returnValue = dao.checkHrefFindDel(contractids);
		return returnValue;
	}
}
