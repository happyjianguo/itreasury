package com.iss.itreasury.settlement.upgathering.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransOnePayMultiReceiveDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.upgathering.dao.Sett_UpGatheringDetailDAO;
import com.iss.itreasury.settlement.upgathering.dao.Sett_UpGatheringPolicyDAO;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringDetailInfo;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringPolicyInfo;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;


/**
 * @author ygzhao
 * 2005-8-16
 */
public class UpGatheringBiz
{
	private Sett_UpGatheringPolicyDAO policyDao = null;
	private Sett_UpGatheringDetailDAO detailDao = null;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private long m_luserID = -1;
	
	public UpGatheringBiz()
	{
		policyDao = new Sett_UpGatheringPolicyDAO(); 
		detailDao = new Sett_UpGatheringDetailDAO();
	}
	
	/**
	 * ��ѯ���е����ղ���
	 * @param info
	 * @param lOrderBy
	 * @param lAscOrDesc
	 * @return
	 * @throws SettlementException
	 */
	public Collection findAllPolicies(UpGatheringPolicyInfo info,long lOrderBy,long lAscOrDesc) throws SettlementException
	{		
		Collection c = null;
		try
		{
			c = policyDao.findAllPolicies(info,lOrderBy,lAscOrDesc);
		} catch (SettlementDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return c;
	}
	
	/**
	 * ����ĳһ�����ղ���
	 * @param lID
	 * @return
	 * @throws SettlementException
	 */
	public UpGatheringPolicyInfo findPolicyByID(long lID) throws SettlementException
	{
		UpGatheringPolicyInfo info = null;
		try
		{
			info = (UpGatheringPolicyInfo)policyDao.findByID(lID, UpGatheringPolicyInfo.class);
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return info;
	}

	/**
	 * �õ���һ�����ղ��Եı��
	 * @return
	 * @throws SettlementException
	 */
	public String getNextPolicyCode() throws SettlementException
	{
		String strNextCode = "001";
		try
		{
			strNextCode = policyDao.getNextPolicyCode();
		} catch (SettlementDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return strNextCode;
	}
	
	/**
	 * �������޸����ղ���
	 * @param info
	 * @return
	 */
	public long savePolicy(UpGatheringPolicyInfo info) throws SettlementException
	{
		long lReturn = -1;
		try		
		{
			if(info.getId() > 0)
			{
				//�޸�
				policyDao.update(info);
				lReturn = 1;
			}
			else
			{
				//����
				info.setStatusId(Constant.RecordStatus.VALID);
				policyDao.setUseMaxID();
				lReturn = policyDao.add(info);
			}
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return lReturn;
	}
	
	/**
	 * ɾ�����ղ���,ͬʱɾ���ò��������еĸ���˻�����
	 * @param info
	 */
	public void deletePolicy(UpGatheringPolicyInfo info) throws SettlementException
	{
		try
		{
			//��ɾ�������ղ��������еĸ���˻�����
			detailDao.updateForDelete(info.getId());
			//Ȼ��ɾ�������ղ���
			info.setStatusId(Constant.RecordStatus.INVALID);
			policyDao.update(info);
			
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
	}
	
	/**
	 * ����ĳ�����µ����ղ�����ϸ
	 * @param lPolicyID ���ղ��Ա�ʶ
	 * @return
	 */
	public Collection findAllPayerAccountsByPolicy(long lPolicyID) throws SettlementException
	{		
		Collection c = null;
		UpGatheringDetailInfo info = new UpGatheringDetailInfo();
		info.setPolicyId(lPolicyID);
		info.setStatusId(Constant.RecordStatus.VALID);
		try
		{
			c = detailDao.findByCondition(info," order by SerialNo");
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return c;
	}
	
	/**
	 * ����ĳһ�������˻�����
	 * @param lID
	 * @return
	 * @throws SettlementException
	 */
	public UpGatheringDetailInfo findPayerAccountByID(long lID) throws SettlementException
	{
		UpGatheringDetailInfo info = null;
		try
		{
			info = (UpGatheringDetailInfo)detailDao.findByID(lID, UpGatheringDetailInfo.class);
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return info;
	}
	
	/**
	 * �������޸�ĳ�����µĸ���˻�����
	 * @param info
	 * @return
	 */
	public long savePayerAccount(UpGatheringDetailInfo info) throws SettlementException
	{
		long lReturn = -1;
		UpGatheringPolicyInfo policyInfo = null;
		try
		{
			policyInfo = findPolicyByID(info.getPolicyId());
			if(info.getPayerAccountId() == policyInfo.getPayeeAccountId())
			{
				//�����˻��͸����˻���ͬ
				lReturn = -3;
			}
			else 
			if(detailDao.isPayerAccountRole(info.getPayerAccountId(),info.getId()))
			{
				//�Ѿ��䵱����˻��Ľ�ɫ
				lReturn = -2;
			}
			else 
			if(info.getId() > 0)
			{
				//�޸�
				detailDao.update(info);
				lReturn = 1;
			}
			else
			{
				//����
				info.setStatusId(Constant.RecordStatus.VALID);
				detailDao.setUseMaxID();
				lReturn = detailDao.add(info);
			}
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return lReturn;
	}
	
	/**
	 * ɾ��ĳ�����µ�һ������˻�
	 * @param lID
	 */
	public void deletePayerAccount(UpGatheringDetailInfo info) throws SettlementException
	{
		try
		{
			info.setStatusId(Constant.RecordStatus.INVALID);
			detailDao.update(info);
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
	}
	
	/**
	 * ��ѯ���е����ղ���(�ʽ�������)
	 * @param lAscOrDesc
	 * @return
	 * @throws SettlementException
	 */
	public Collection findAllPoliciesForExcute(long lAscOrDesc) throws SettlementException
	{
		Collection c = null;
		try
		{
			c = policyDao.findAllPoliciesForExcute(lAscOrDesc);
		} catch (SettlementDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return c;
	}
	/**
	 * ����ĳ�����µ����ղ�����ϸ(�ʽ�������)
	 * @param lPolicyID
	 * @return
	 * @throws SettlementException
	 */
	public Collection findPayerAccountsByIDForExcute(long lPolicyID) throws SettlementException
	{
		Collection c = null;
		try
		{
			c = detailDao.findPayerAccountsByIDForExcute(lPolicyID);
		} catch (SettlementDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return c;
	}
	/**
	 * �����ѡ�Ĳ���,�����ʽ����� ���� +¼���ˡ������ˡ�ǩ֤��ID
	 * @param c Ҫ�����ʽ����յ����ղ��Ա�ʶID�ļ���
	 * @return
	 * @version 2.0
	 */
	public long autoExecuteUpGatheringOfPolicies(Collection c,long userId) throws SettlementException
	{
		m_luserID = userId;
		try{
			autoExecuteUpGatheringOfPolicies(c);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		}
		return -1;
	}
	
	/**
	 * �����ѡ�Ĳ���,�����ʽ�����
	 * @param c Ҫ�����ʽ����յ����ղ��Ա�ʶID�ļ���
	 * @return
	 */
	public long autoExecuteUpGatheringOfPolicies(Collection c) throws SettlementException
	{
		try
		{			
			if(c!=null && c.size()>0)
			{
				TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
				Sett_TransOnePayMultiReceiveDAO dao = new Sett_TransOnePayMultiReceiveDAO();
				Iterator it = c.iterator();
				while(it.hasNext())
				{
					long policyID = ((Long)it.next()).longValue();
					UpGatheringPolicyInfo policyInfo = findPolicyByID(policyID);
					Collection cDetailInfos = findPayerAccountsByIDForExcute(policyID);
					log.info("--------- policyID="+policyID+"  �ʽ������˻���Ŀ="+cDetailInfos.size());
					if(cDetailInfos != null && cDetailInfos.size()>0)//����ò���������Ҫ�����ʽ����յĿͻ�
					{
						Iterator itDetailInfos = cDetailInfos.iterator();
						double UpGatcheringAmountSum = 0.0;//�ò��Ա������չ������տ�����ս����ܺ�
						ArrayList list = new ArrayList();//���湴�������TransOnePayMultiReceiveInfoʵ��
						for(int i=0; itDetailInfos!=null && itDetailInfos.hasNext(); i++)	
						{
							//ѭ���жԸ����˻����д���
							log.info("--------forѭ���жԸ����˻����д���-----------");
							UpGatheringDetailInfo detailInfo = (UpGatheringDetailInfo)itDetailInfos.next();
							log.info("���ս��="+detailInfo.getUpGatcheringAmount());
							log.info("����������="+detailInfo.getLimit());
							//if(detailInfo.getUpGatcheringAmount() < detailInfo.getLimit())//���ս��С���˻�����������,�򲻽�������
							//	continue;
							if(detailInfo.getUpGatcheringAmount() == 0)//���ս��Ϊ0ʱ������������
								continue;
							log.info("--------���ս�С���˻�����������,��������---------id="+detailInfo.getId());
							Timestamp ts = Env.getSystemDate(detailInfo.getOfficeId(), detailInfo.getCurrencyId());//��ǰϵͳʱ��
							TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
							depositInfo.setAmount(detailInfo.getUpGatcheringAmount());//���ս�� ������
							UpGatcheringAmountSum += detailInfo.getUpGatcheringAmount();//�����ۼ�
							depositInfo.setAccountID(detailInfo.getPayerAccountId());//��/�����˻�ID
							depositInfo.setAbstract("�ʽ����ո����˻�");//ժҪ
							depositInfo.setCheckUserID(m_luserID);//������
							depositInfo.setCheckAbstract("�ʽ����ո���");
							
							depositInfo.setCurrencyID(detailInfo.getCurrencyId());
							depositInfo.setExecuteDate(ts);//ִ����
							depositInfo.setInputDate(ts);//¼������
							depositInfo.setInputUserID(m_luserID);//¼����
							depositInfo.setInterestStartDate(ts);//��Ϣ��
							depositInfo.setOfficeID(detailInfo.getOfficeId());
							depositInfo.setPayClientID(detailInfo.getPayerClientId());//����ͻ�ID
							depositInfo.setReceiveClientID(policyInfo.getUpClientId());//�տ�ͻ�ID
							depositInfo.setSignUserID(m_luserID);//ǩ����
							depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//��������
							depositInfo.setTypeID(SETTConstant.ReceiveOrPay.PAY);//����
							depositInfo.setSerialNo(list.size()+2);//���к� �����տ��˻��󸶿��˻���1��ʼ����
							log.info("autoExecuteUpGatheringOfPolicies()--------��ʼ���б���-----------");
							long depositId = depositDelegation.save(depositInfo);//����SETT_TRANSONEPAYMULTIRECEIVE���ID
							//֮�����ٲ�һ��ʵ��,����Ϊ�����֮��,�޸�ʱ���״̬�Ѿ������仯
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							//���и���
							log.info("autoExecuteUpGatheringOfPolicies()--------��ʼ���и���-----------");
							depositDelegation.check(depositInfo);
							//֮�����ٲ�һ��ʵ��,����Ϊ���˹�֮��,�޸�ʱ���״̬�Ѿ������仯
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							list.add(depositInfo);
						}
						if(list.size() > 0)
						{
							//˵������Ӧ���������ĸ��,����տ��˻����д���
							Timestamp ts = Env.getSystemDate(policyInfo.getOfficeId(), policyInfo.getCurrencyId());//��ǰϵͳʱ��
							TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
							depositInfo.setAmount(UpGatcheringAmountSum);//���ս�� ������
							depositInfo.setAccountID(policyInfo.getPayeeAccountId());//��/�����˻�ID
							depositInfo.setAbstract("�ʽ������տ��˻�");//ժҪ
							depositInfo.setCheckUserID(m_luserID);//������
							depositInfo.setCheckAbstract("�ʽ����ո���");
							depositInfo.setCurrencyID(policyInfo.getCurrencyId());
							depositInfo.setExecuteDate(ts);//ִ����
							depositInfo.setInputDate(ts);//¼������
							depositInfo.setInputUserID(m_luserID);//¼����
							depositInfo.setInterestStartDate(ts);//��Ϣ��
							depositInfo.setOfficeID(policyInfo.getOfficeId());
							depositInfo.setPayClientID(policyInfo.getUpClientId());//����ͻ�ID
							depositInfo.setReceiveClientID(policyInfo.getUpClientId());//�տ�ͻ�ID
							depositInfo.setSignUserID(m_luserID);//ǩ����
							depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//��������
							depositInfo.setTypeID(SETTConstant.ReceiveOrPay.RECEIVE);//����
							depositInfo.setSerialNo(1);//���к� �����տ��˻��󸶿��˻���1��ʼ����
							log.info("autoExecuteUpGatheringOfPolicies()--------��ʼ���б���-----------");
							long depositId = depositDelegation.save(depositInfo);//����SETT_TRANSONEPAYMULTIRECEIVE���ID
							//֮�����ٲ�һ��ʵ��,����Ϊ�����֮��,�޸�ʱ���״̬�Ѿ������仯
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							log.info("autoExecuteUpGatheringOfPolicies()--------��ʼ���и���-----------");
							depositDelegation.check(depositInfo);
							//֮�����ٲ�һ��ʵ��,����Ϊ���˹�֮��,�޸�ʱ���״̬�Ѿ������仯
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							list.add(depositInfo);
							//���й���
							TransOnePayMultiReceiveInfo[] infos = (TransOnePayMultiReceiveInfo[])list.toArray(new TransOnePayMultiReceiveInfo[0]);
							/*
							TransOnePayMultiReceiveInfo[] infos = new TransOnePayMultiReceiveInfo[list.size()];
							for(int i=0; i<list.size(); i++)
							{
								infos[i] = (TransOnePayMultiReceiveInfo)list.get(i);
							}
							*/
							log.info("autoExecuteUpGatheringOfPolicies()--------��ʼ���й���-----------");
							depositDelegation.squareUp(infos);
						}
						
						
					}
					
				}
			
			}
			
		}catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		} catch (IRollbackException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		}
		return -1;
	}
	/**
	 * ���ĳһ��������ѡ���˻�����,�����ʽ����� ���� +¼���ˡ������ˡ�ǩ֤��ID
	 * @param lPolicyID ���ղ��Ա�ʶ
	 * @param c �ò�������Ҫ�����ʽ����յ��˻�����ID�ļ���
	 * @return
	 * @throws SettlementException
	 * @version 2.0
	 */
	public long autoExecuteUpGatheringOfAccounts(long lPolicyID,Collection c,long lUserID) throws SettlementException
	{
		m_luserID = lUserID;
		try{
			autoExecuteUpGatheringOfAccounts(lPolicyID,c);
		}catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		}
		return -1;
	}
	/**
	 * ���ĳһ��������ѡ���˻�����,�����ʽ�����
	 * @param lPolicyID ���ղ��Ա�ʶ
	 * @param c �ò�������Ҫ�����ʽ����յ��˻�����ID�ļ���
	 * @return
	 * @throws SettlementException
	 */
	public long autoExecuteUpGatheringOfAccounts(long lPolicyID,Collection c) throws SettlementException
	{
		try
		{
			if(c!=null && c.size()>0)
			{
				TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
				Sett_TransOnePayMultiReceiveDAO dao = new Sett_TransOnePayMultiReceiveDAO();
				
				UpGatheringPolicyInfo policyInfo = findPolicyByID(lPolicyID);
				Collection cDetailInfos = findPayerAccountsByIDForExcute(lPolicyID);//�ò��������е��տ�˻�����
				double UpGatcheringAmountSum = 0.0;//�������չ������տ�����ս����ܺ�
				//TransOnePayMultiReceiveInfo[] infos = new TransOnePayMultiReceiveInfo[c.size()+1];//+1 ��Ϊ��Ҫ����һ���տ�˻�����
				if(cDetailInfos != null && cDetailInfos.size()>0)//����ò���������Ҫ�����ʽ����յĿͻ�
				{
					Iterator itDetailInfos = cDetailInfos.iterator();
					ArrayList list = new ArrayList();//���湴�������TransOnePayMultiReceiveInfoʵ��
					for(int i=0; itDetailInfos!=null && itDetailInfos.hasNext(); i++)	
					{
						//ѭ���жԸ����˻������ж�,�����ҳ����ѡ���,�������Ӧ�ı��渴�˲���
						UpGatheringDetailInfo detailInfo = (UpGatheringDetailInfo)itDetailInfos.next();
						Iterator it = c.iterator();//
						while(it!=null && it.hasNext())
						{
							long detailID = ((Long)it.next()).longValue();
							if(detailID == detailInfo.getId())//�ø����˻�������ҳ����ѡ���
							{
								log.info("-------------------�ø����˻�������ҳ����ѡ���----------------");
								Timestamp ts = Env.getSystemDate(detailInfo.getOfficeId(), detailInfo.getCurrencyId());//��ǰϵͳʱ��
								TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
								if(detailInfo.getUpGatcheringAmount() == 0)
									continue;
								depositInfo.setAmount(detailInfo.getUpGatcheringAmount());//���ս�� ������
								UpGatcheringAmountSum += detailInfo.getUpGatcheringAmount();//�����ۼ�
								depositInfo.setAccountID(detailInfo.getPayerAccountId());//��/�����˻�ID
								depositInfo.setAbstract("�ʽ����ո����˻�");//ժҪ
								depositInfo.setCheckUserID(m_luserID);//������
								depositInfo.setCheckAbstract("�ʽ����ո���");
								depositInfo.setCurrencyID(detailInfo.getCurrencyId());
								depositInfo.setExecuteDate(ts);//ִ����
								depositInfo.setInputDate(ts);//¼������
								depositInfo.setInputUserID(m_luserID);//¼����
								depositInfo.setInterestStartDate(ts);//��Ϣ��
								depositInfo.setOfficeID(detailInfo.getOfficeId());
								depositInfo.setPayClientID(detailInfo.getPayerClientId());//����ͻ�ID
								depositInfo.setReceiveClientID(policyInfo.getUpClientId());//�տ�ͻ�ID
								depositInfo.setSignUserID(m_luserID);//ǩ����
								depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//��������
								depositInfo.setTypeID(SETTConstant.ReceiveOrPay.PAY);//����
								depositInfo.setSerialNo(list.size()+2);//���к� �����տ��˻��󸶿��˻���1��ʼ����
								long depositId = depositDelegation.save(depositInfo);//����SETT_TRANSONEPAYMULTIRECEIVE���ID
								
								//֮�����ٲ�һ��ʵ��,����Ϊ�����֮��,�޸�ʱ���״̬�Ѿ������仯
								depositInfo = null;
								depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
								//���и���
								depositDelegation.check(depositInfo);
								//֮�����ٲ�һ��ʵ��,����Ϊ���˹�֮��,�޸�ʱ���״̬�Ѿ������仯
								depositInfo = null;
								depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
								list.add(depositInfo);
								break;
							}
								
						}
					}
					if(list.size() > 0)
					{
						//˵������Ӧ���������ĸ��,����տ��˻����д���
						log.info("-------------------����Ӧ���������ĸ��,����տ��˻����д���----------------");
						Timestamp ts = Env.getSystemDate(policyInfo.getOfficeId(), policyInfo.getCurrencyId());//��ǰϵͳʱ��
						TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
						depositInfo.setAmount(UpGatcheringAmountSum);//���ս�� ������
						depositInfo.setAccountID(policyInfo.getPayeeAccountId());//��/�����˻�ID
						depositInfo.setAbstract("�ʽ������տ��˻�");//ժҪ
						depositInfo.setCheckUserID(m_luserID);//������
						depositInfo.setCheckAbstract("�ʽ����ո���");
						depositInfo.setCurrencyID(policyInfo.getCurrencyId());
						depositInfo.setExecuteDate(ts);//ִ����
						depositInfo.setInputDate(ts);//¼������
						depositInfo.setInputUserID(m_luserID);//¼����
						depositInfo.setInterestStartDate(ts);//��Ϣ��
						depositInfo.setOfficeID(policyInfo.getOfficeId());
						depositInfo.setPayClientID(policyInfo.getUpClientId());//����ͻ�ID
						depositInfo.setReceiveClientID(policyInfo.getUpClientId());//�տ�ͻ�ID
						depositInfo.setSignUserID(m_luserID);//ǩ����
						depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//��������
						depositInfo.setTypeID(SETTConstant.ReceiveOrPay.RECEIVE);//����
						depositInfo.setSerialNo(1);//���к� �����տ��˻��󸶿��˻���1��ʼ����
						
						long depositId = depositDelegation.save(depositInfo);//����SETT_TRANSONEPAYMULTIRECEIVE���ID
						//֮�����ٲ�һ��ʵ��,����Ϊ�����֮��,�޸�ʱ���״̬�Ѿ������仯
						depositInfo = null;
						depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
						//���и���
						depositDelegation.check(depositInfo);
						//֮�����ٲ�һ��ʵ��,����Ϊ���˹�֮��,�޸�ʱ���״̬�Ѿ������仯
						depositInfo = null;
						depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
						list.add(depositInfo);
						//���й���(Ҫע��:�ո���ͻ����˻����ͱ�������Ӧ���Ѿ����ù�,�����޷����й���)
						log.info("-------------------listת��Ϊ����----------------");
						TransOnePayMultiReceiveInfo[] infos = (TransOnePayMultiReceiveInfo[])list.toArray(new TransOnePayMultiReceiveInfo[0]);
						/*
						TransOnePayMultiReceiveInfo[] infos = new TransOnePayMultiReceiveInfo[list.size()];
						for(int i=0; i<list.size(); i++)
						{
							infos[i] = (TransOnePayMultiReceiveInfo)list.get(i);
						}
						*/
						log.info("-------------------��ʼ�����Զ�����----------------");
						depositDelegation.squareUp(infos);
						log.info("-------------------�Զ����˳ɹ�----------------");
					}
				}
				
	            
			}			
			
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		} catch (IRollbackException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		}
		return -1;
	}
	
	/**
	 * ��ѯ���������ѽ����ʽ����յĽ���
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public Collection findForCancelUpGathering(UpGatheringQueryInfo info) throws SettlementException
	{
		Collection resultColl = null;
		TransOnePayMultiReceiveInfo searchInfo = new TransOnePayMultiReceiveInfo();
        try
		{
			TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
			searchInfo.setCurrencyID(info.getCurrencyID());
			searchInfo.setOfficeID(info.getOfficeID());
			searchInfo.setReceiveClientID(info.getReceiveClientID());
			searchInfo.setPayClientID(info.getPayClientID());
			searchInfo.setExecuteDate(DataFormat.getDateTime(Env.getSystemDateString(info.getOfficeID(),info.getCurrencyID())));
			searchInfo.setStatusID(SETTConstant.TransactionStatus.CIRCLE);
			searchInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);
			
			int nOrderByCode = Sett_TransOnePayMultiReceiveDAO.ORDERBY_TRANSACTIONNOID;
			resultColl = depositDelegation.findByConditionsForSquareUp(searchInfo, nOrderByCode, false);
			
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		} catch (IRollbackException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		}
		return resultColl;
	}
	/**
	 * �����ѡ�����ս���,����ȡ������
	 * @param c һ�����ս��׺ŵļ���
	 * @return
	 * @throws SettlementException
	 */
	public long cancelUpGatchering(Collection c) throws SettlementException
	{
		try
		{
			if(c!=null && c.size()>0)
			{
				Iterator it = c.iterator();
				TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
				while(it.hasNext())
				{
					String transNO = (String)it.next();
					//
					TransOnePayMultiReceiveInfo searchInfo = new TransOnePayMultiReceiveInfo();
					searchInfo.setTransNo(transNO);
					Collection resultColl = null;
					resultColl = depositDelegation.findByConditions(searchInfo, -1, false);
	
					if(resultColl != null && !resultColl.isEmpty())
		            {
		                TransOnePayMultiReceiveInfo[] infos = null;
	
						infos = (TransOnePayMultiReceiveInfo[])resultColl.toArray(new TransOnePayMultiReceiveInfo[0]);
						//�Ծ�����ͬ���׺ŵ�һ���¼����ȡ������
						if(depositDelegation.cancelSquareUp(infos))
						{
							//ȡ�����˺�,��ʱresultColl�е������Ѿ������ݿ��еĲ�һ��(״̬���޸�ʱ�䷢���仯),������Ҫ���·ֱ��һ��
							Iterator itCancelCheck = resultColl.iterator();
							while(itCancelCheck!=null && itCancelCheck.hasNext())
							{
								long lID = ((TransOnePayMultiReceiveInfo)itCancelCheck.next()).getId();
								TransOnePayMultiReceiveInfo info = depositDelegation.findBySett_TransOnePayMultiReceiveID(lID);
								//��ÿһ�ʽ��׽���ȡ������
								depositDelegation.cancelCheck(info);
								info = null;
								info = depositDelegation.findBySett_TransOnePayMultiReceiveID(lID);
								//ɾ�����׼�¼
								log.info("$$$$$$$$$$depositDelegation$$$$$$$$$ɾ�����׼�¼");
								depositDelegation.delete(info);
							}
						}			
		            }	
					else
					{
						throw new SettlementException("ָ�����׺�="+transNO+"��һ�����ս��׼�¼���ݿ��в����ڣ�",null);
					}
					//
				}
			}
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		} catch (IRollbackException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException("",e);
		}
		return -1;
	}
}
