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
	 * 查询所有的上收策略
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
	 * 查找某一条上收策略
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
	 * 得到下一个上收策略的编号
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
	 * 新增或修改上收策略
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
				//修改
				policyDao.update(info);
				lReturn = 1;
			}
			else
			{
				//保存
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
	 * 删除上收策略,同时删除该策略下所有的付款方账户设置
	 * @param info
	 */
	public void deletePolicy(UpGatheringPolicyInfo info) throws SettlementException
	{
		try
		{
			//先删除该上收策略下所有的付款方账户设置
			detailDao.updateForDelete(info.getId());
			//然后删除该上收策略
			info.setStatusId(Constant.RecordStatus.INVALID);
			policyDao.update(info);
			
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
	}
	
	/**
	 * 查找某策略下的上收策略明细
	 * @param lPolicyID 上收策略标识
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
	 * 查找某一条上收账户设置
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
	 * 新增或修改某策略下的付款方账户设置
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
				//上收账户和付款账户相同
				lReturn = -3;
			}
			else 
			if(detailDao.isPayerAccountRole(info.getPayerAccountId(),info.getId()))
			{
				//已经充当付款方账户的角色
				lReturn = -2;
			}
			else 
			if(info.getId() > 0)
			{
				//修改
				detailDao.update(info);
				lReturn = 1;
			}
			else
			{
				//保存
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
	 * 删除某策略下的一个付款方账户
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
	 * 查询所有的上收策略(资金上收用)
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
	 * 查找某策略下的上收策略明细(资金上收用)
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
	 * 针对所选的策略,进行资金上收 重载 +录入人、复核人、签证人ID
	 * @param c 要进行资金上收的上收策略标识ID的集合
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
	 * 针对所选的策略,进行资金上收
	 * @param c 要进行资金上收的上收策略标识ID的集合
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
					log.info("--------- policyID="+policyID+"  资金上收账户数目="+cDetailInfos.size());
					if(cDetailInfos != null && cDetailInfos.size()>0)//如果该策略下有需要进行资金上收的客户
					{
						Iterator itDetailInfos = cDetailInfos.iterator();
						double UpGatcheringAmountSum = 0.0;//该策略本次上收过程中收款方所上收金额的总和
						ArrayList list = new ArrayList();//保存勾账所需的TransOnePayMultiReceiveInfo实体
						for(int i=0; itDetailInfos!=null && itDetailInfos.hasNext(); i++)	
						{
							//循环中对付款账户进行处理
							log.info("--------for循环中对付款账户进行处理-----------");
							UpGatheringDetailInfo detailInfo = (UpGatheringDetailInfo)itDetailInfos.next();
							log.info("上收金额="+detailInfo.getUpGatcheringAmount());
							log.info("最低余额限制="+detailInfo.getLimit());
							//if(detailInfo.getUpGatcheringAmount() < detailInfo.getLimit())//上收金额小于账户最低余额限制,则不进行上收
							//	continue;
							if(detailInfo.getUpGatcheringAmount() == 0)//上收金额为0时，不进行上收
								continue;
							log.info("--------上收金额不小于账户最低余额限制,进行上收---------id="+detailInfo.getId());
							Timestamp ts = Env.getSystemDate(detailInfo.getOfficeId(), detailInfo.getCurrencyId());//当前系统时间
							TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
							depositInfo.setAmount(detailInfo.getUpGatcheringAmount());//上收金额 发生额
							UpGatcheringAmountSum += detailInfo.getUpGatcheringAmount();//进行累加
							depositInfo.setAccountID(detailInfo.getPayerAccountId());//收/付款账户ID
							depositInfo.setAbstract("资金上收付款账户");//摘要
							depositInfo.setCheckUserID(m_luserID);//复核人
							depositInfo.setCheckAbstract("资金上收复核");
							
							depositInfo.setCurrencyID(detailInfo.getCurrencyId());
							depositInfo.setExecuteDate(ts);//执行日
							depositInfo.setInputDate(ts);//录入日期
							depositInfo.setInputUserID(m_luserID);//录入人
							depositInfo.setInterestStartDate(ts);//起息日
							depositInfo.setOfficeID(detailInfo.getOfficeId());
							depositInfo.setPayClientID(detailInfo.getPayerClientId());//付款客户ID
							depositInfo.setReceiveClientID(policyInfo.getUpClientId());//收款客户ID
							depositInfo.setSignUserID(m_luserID);//签认人
							depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//交易类型
							depositInfo.setTypeID(SETTConstant.ReceiveOrPay.PAY);//类型
							depositInfo.setSerialNo(list.size()+2);//序列号 依先收款账户后付款账户从1开始递增
							log.info("autoExecuteUpGatheringOfPolicies()--------开始进行保存-----------");
							long depositId = depositDelegation.save(depositInfo);//返回SETT_TRANSONEPAYMULTIRECEIVE表的ID
							//之所以再查一次实体,是因为保存过之后,修改时间和状态已经发生变化
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							//进行复核
							log.info("autoExecuteUpGatheringOfPolicies()--------开始进行复核-----------");
							depositDelegation.check(depositInfo);
							//之所以再查一次实体,是因为复核过之后,修改时间和状态已经发生变化
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							list.add(depositInfo);
						}
						if(list.size() > 0)
						{
							//说明有相应符合条件的付款方,则对收款账户进行处理
							Timestamp ts = Env.getSystemDate(policyInfo.getOfficeId(), policyInfo.getCurrencyId());//当前系统时间
							TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
							depositInfo.setAmount(UpGatcheringAmountSum);//上收金额 发生额
							depositInfo.setAccountID(policyInfo.getPayeeAccountId());//收/付款账户ID
							depositInfo.setAbstract("资金上收收款账户");//摘要
							depositInfo.setCheckUserID(m_luserID);//复核人
							depositInfo.setCheckAbstract("资金上收复核");
							depositInfo.setCurrencyID(policyInfo.getCurrencyId());
							depositInfo.setExecuteDate(ts);//执行日
							depositInfo.setInputDate(ts);//录入日期
							depositInfo.setInputUserID(m_luserID);//录入人
							depositInfo.setInterestStartDate(ts);//起息日
							depositInfo.setOfficeID(policyInfo.getOfficeId());
							depositInfo.setPayClientID(policyInfo.getUpClientId());//付款客户ID
							depositInfo.setReceiveClientID(policyInfo.getUpClientId());//收款客户ID
							depositInfo.setSignUserID(m_luserID);//签认人
							depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//交易类型
							depositInfo.setTypeID(SETTConstant.ReceiveOrPay.RECEIVE);//类型
							depositInfo.setSerialNo(1);//序列号 依先收款账户后付款账户从1开始递增
							log.info("autoExecuteUpGatheringOfPolicies()--------开始进行保存-----------");
							long depositId = depositDelegation.save(depositInfo);//返回SETT_TRANSONEPAYMULTIRECEIVE表的ID
							//之所以再查一次实体,是因为保存过之后,修改时间和状态已经发生变化
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							log.info("autoExecuteUpGatheringOfPolicies()--------开始进行复核-----------");
							depositDelegation.check(depositInfo);
							//之所以再查一次实体,是因为复核过之后,修改时间和状态已经发生变化
							depositInfo = null;
							depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
							list.add(depositInfo);
							//进行勾账
							TransOnePayMultiReceiveInfo[] infos = (TransOnePayMultiReceiveInfo[])list.toArray(new TransOnePayMultiReceiveInfo[0]);
							/*
							TransOnePayMultiReceiveInfo[] infos = new TransOnePayMultiReceiveInfo[list.size()];
							for(int i=0; i<list.size(); i++)
							{
								infos[i] = (TransOnePayMultiReceiveInfo)list.get(i);
							}
							*/
							log.info("autoExecuteUpGatheringOfPolicies()--------开始进行勾账-----------");
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
	 * 针对某一策略下所选的账户设置,进行资金上收 重载 +录入人、复核人、签证人ID
	 * @param lPolicyID 上收策略标识
	 * @param c 该策略下所要进行资金上收的账户设置ID的集合
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
	 * 针对某一策略下所选的账户设置,进行资金上收
	 * @param lPolicyID 上收策略标识
	 * @param c 该策略下所要进行资金上收的账户设置ID的集合
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
				Collection cDetailInfos = findPayerAccountsByIDForExcute(lPolicyID);//该策略下所有的收款方账户设置
				double UpGatcheringAmountSum = 0.0;//本次上收过程中收款方所上收金额的总和
				//TransOnePayMultiReceiveInfo[] infos = new TransOnePayMultiReceiveInfo[c.size()+1];//+1 因为还要保存一个收款方账户设置
				if(cDetailInfos != null && cDetailInfos.size()>0)//如果该策略下有需要进行资金上收的客户
				{
					Iterator itDetailInfos = cDetailInfos.iterator();
					ArrayList list = new ArrayList();//保存勾账所需的TransOnePayMultiReceiveInfo实体
					for(int i=0; itDetailInfos!=null && itDetailInfos.hasNext(); i++)	
					{
						//循环中对付款账户进行判断,如果是页面所选择的,则进行相应的保存复核操作
						UpGatheringDetailInfo detailInfo = (UpGatheringDetailInfo)itDetailInfos.next();
						Iterator it = c.iterator();//
						while(it!=null && it.hasNext())
						{
							long detailID = ((Long)it.next()).longValue();
							if(detailID == detailInfo.getId())//该付款账户设置是页面所选择的
							{
								log.info("-------------------该付款账户设置是页面所选择的----------------");
								Timestamp ts = Env.getSystemDate(detailInfo.getOfficeId(), detailInfo.getCurrencyId());//当前系统时间
								TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
								if(detailInfo.getUpGatcheringAmount() == 0)
									continue;
								depositInfo.setAmount(detailInfo.getUpGatcheringAmount());//上收金额 发生额
								UpGatcheringAmountSum += detailInfo.getUpGatcheringAmount();//进行累加
								depositInfo.setAccountID(detailInfo.getPayerAccountId());//收/付款账户ID
								depositInfo.setAbstract("资金上收付款账户");//摘要
								depositInfo.setCheckUserID(m_luserID);//复核人
								depositInfo.setCheckAbstract("资金上收复核");
								depositInfo.setCurrencyID(detailInfo.getCurrencyId());
								depositInfo.setExecuteDate(ts);//执行日
								depositInfo.setInputDate(ts);//录入日期
								depositInfo.setInputUserID(m_luserID);//录入人
								depositInfo.setInterestStartDate(ts);//起息日
								depositInfo.setOfficeID(detailInfo.getOfficeId());
								depositInfo.setPayClientID(detailInfo.getPayerClientId());//付款客户ID
								depositInfo.setReceiveClientID(policyInfo.getUpClientId());//收款客户ID
								depositInfo.setSignUserID(m_luserID);//签认人
								depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//交易类型
								depositInfo.setTypeID(SETTConstant.ReceiveOrPay.PAY);//类型
								depositInfo.setSerialNo(list.size()+2);//序列号 依先收款账户后付款账户从1开始递增
								long depositId = depositDelegation.save(depositInfo);//返回SETT_TRANSONEPAYMULTIRECEIVE表的ID
								
								//之所以再查一次实体,是因为保存过之后,修改时间和状态已经发生变化
								depositInfo = null;
								depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
								//进行复核
								depositDelegation.check(depositInfo);
								//之所以再查一次实体,是因为复核过之后,修改时间和状态已经发生变化
								depositInfo = null;
								depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
								list.add(depositInfo);
								break;
							}
								
						}
					}
					if(list.size() > 0)
					{
						//说明有相应符合条件的付款方,则对收款账户进行处理
						log.info("-------------------有相应符合条件的付款方,则对收款账户进行处理----------------");
						Timestamp ts = Env.getSystemDate(policyInfo.getOfficeId(), policyInfo.getCurrencyId());//当前系统时间
						TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();
						depositInfo.setAmount(UpGatcheringAmountSum);//上收金额 发生额
						depositInfo.setAccountID(policyInfo.getPayeeAccountId());//收/付款账户ID
						depositInfo.setAbstract("资金上收收款账户");//摘要
						depositInfo.setCheckUserID(m_luserID);//复核人
						depositInfo.setCheckAbstract("资金上收复核");
						depositInfo.setCurrencyID(policyInfo.getCurrencyId());
						depositInfo.setExecuteDate(ts);//执行日
						depositInfo.setInputDate(ts);//录入日期
						depositInfo.setInputUserID(m_luserID);//录入人
						depositInfo.setInterestStartDate(ts);//起息日
						depositInfo.setOfficeID(policyInfo.getOfficeId());
						depositInfo.setPayClientID(policyInfo.getUpClientId());//付款客户ID
						depositInfo.setReceiveClientID(policyInfo.getUpClientId());//收款客户ID
						depositInfo.setSignUserID(m_luserID);//签认人
						depositInfo.setTransactionTypeID(SETTConstant.TransactionType.UPGATHERING);//交易类型
						depositInfo.setTypeID(SETTConstant.ReceiveOrPay.RECEIVE);//类型
						depositInfo.setSerialNo(1);//序列号 依先收款账户后付款账户从1开始递增
						
						long depositId = depositDelegation.save(depositInfo);//返回SETT_TRANSONEPAYMULTIRECEIVE表的ID
						//之所以再查一次实体,是因为保存过之后,修改时间和状态已经发生变化
						depositInfo = null;
						depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
						//进行复核
						depositDelegation.check(depositInfo);
						//之所以再查一次实体,是因为复核过之后,修改时间和状态已经发生变化
						depositInfo = null;
						depositInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(depositId);
						list.add(depositInfo);
						//进行勾账(要注意:收付款客户在账户类型编码设置应该已经设置过,否则无法进行勾账)
						log.info("-------------------list转换为数组----------------");
						TransOnePayMultiReceiveInfo[] infos = (TransOnePayMultiReceiveInfo[])list.toArray(new TransOnePayMultiReceiveInfo[0]);
						/*
						TransOnePayMultiReceiveInfo[] infos = new TransOnePayMultiReceiveInfo[list.size()];
						for(int i=0; i<list.size(); i++)
						{
							infos[i] = (TransOnePayMultiReceiveInfo)list.get(i);
						}
						*/
						log.info("-------------------开始进行自动勾账----------------");
						depositDelegation.squareUp(infos);
						log.info("-------------------自动勾账成功----------------");
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
	 * 查询当天所有已进行资金上收的交易
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
	 * 针对所选的上收交易,进行取消操作
	 * @param c 一付多收交易号的集合
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
						//对具有相同交易号的一组记录进行取消勾账
						if(depositDelegation.cancelSquareUp(infos))
						{
							//取消勾账后,此时resultColl中的内容已经与数据库中的不一致(状态及修改时间发生变化),所以需要重新分别查一遍
							Iterator itCancelCheck = resultColl.iterator();
							while(itCancelCheck!=null && itCancelCheck.hasNext())
							{
								long lID = ((TransOnePayMultiReceiveInfo)itCancelCheck.next()).getId();
								TransOnePayMultiReceiveInfo info = depositDelegation.findBySett_TransOnePayMultiReceiveID(lID);
								//对每一笔交易进行取消复核
								depositDelegation.cancelCheck(info);
								info = null;
								info = depositDelegation.findBySett_TransOnePayMultiReceiveID(lID);
								//删除交易记录
								log.info("$$$$$$$$$$depositDelegation$$$$$$$$$删除交易记录");
								depositDelegation.delete(info);
							}
						}			
		            }	
					else
					{
						throw new SettlementException("指定交易号="+transNO+"的一付多收交易记录数据库中不存在！",null);
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
