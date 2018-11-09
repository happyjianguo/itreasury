package com.iss.itreasury.integratedCredit.bizlogic;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.integratedCredit.dao.CustomerfeedbackDao;
import com.iss.itreasury.integratedCredit.dao.LoanFinanceitemdetailDao;
import com.iss.itreasury.integratedCredit.dataentity.CreditFrameInfo;
import com.iss.itreasury.integratedCredit.dataentity.LoanAssureoracceptsynopsis;
import com.iss.itreasury.integratedCredit.dataentity.LoanBankliabilitiesdetail;
import com.iss.itreasury.integratedCredit.dataentity.LoanBankrecord;
import com.iss.itreasury.integratedCredit.dataentity.LoanCreditgrade;
import com.iss.itreasury.integratedCredit.dataentity.LoanCreditgradedetail;
import com.iss.itreasury.integratedCredit.dataentity.LoanExternalliabilities;
import com.iss.itreasury.integratedCredit.dataentity.LoanFinanceanalyse;
import com.iss.itreasury.integratedCredit.dataentity.LoanFinanceitemdetail;
import com.iss.itreasury.integratedCredit.dataentity.LoanGroupinsidecontact;
import com.iss.itreasury.integratedCredit.dataentity.LoanManageanalyse;
import com.iss.itreasury.integratedCredit.dataentity.LoanReceiveaccountage;
import com.iss.itreasury.integratedCredit.dataentity.LoanReceivefundsonaccount;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * Created 2008-6-16 14:50:59
 * 
 * @author wxsu
 */
public class CustomerfeedbackBean {

	
	CustomerfeedbackDao customerfeedbackDao=new CustomerfeedbackDao();
	LoanFinanceitemdetailDao fdao = new LoanFinanceitemdetailDao();
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	private long m_luserID = -1;
	/**
	 * 保存授信客户评分表属性清单
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long insertCreditgrade(LoanCreditgrade info)
	throws Exception {
		long resultid = -1;
		try {
			
			resultid = customerfeedbackDao.insertCreditgrade(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return resultid;
		
	}
	public long LoanFinanceanalysesave(LoanFinanceanalyse info)
	throws Exception {
		long resultid = -1;
		LoanFinanceitemdetailDao fdao=new LoanFinanceitemdetailDao();
		try {
			// if(info.getCreditid()<=0)	// modified by lcliu @ 08-11-27
			long ID = fdao.findFinanceByCreditID(info.getCreditid());
			if (ID <= 0)
			{
				resultid = fdao.LoanFinanceanalysesave(info);
			}
			else
			{
				info.setId(ID);
				resultid = fdao.updateFinanceanalysesave(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return resultid;
		
	}
     /**
		 * 应收帐龄分析表属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long insertReceiveaccountage(LoanReceiveaccountage info) throws Exception
	 {
		 long resultid = -1;
		 Collection cQT = info.getOthersStockHolder();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息");
						LoanReceiveaccountage QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanReceiveaccountage();//
							QTInfo = (LoanReceiveaccountage)it.next();
							if(QTInfo.getId()<=0)
							{
							resultid = customerfeedbackDao.insertReceiveaccountage(QTInfo);
							}else
							{
							resultid = customerfeedbackDao.updateReceiveaccountage(QTInfo);
							}
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		 
	 }
	 
	 //
	 public long insertFinanceitemdetail(LoanFinanceitemdetail info) throws Exception
	 {
		 long resultid = -1;
		 Collection cQT = info.getOthersStockHolder();
		 LoanFinanceitemdetailDao  fdao=new LoanFinanceitemdetailDao();
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息");
						LoanFinanceitemdetail QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanFinanceitemdetail();//
							QTInfo = (LoanFinanceitemdetail)it.next();
							if(QTInfo.getId()<=0)
							{
							resultid = fdao.LoanFinanceitemdetailsave(QTInfo);
							}else
							{
							resultid = fdao.updateFinanceitemdetail(QTInfo);
							}
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		 
	 }
	 /**
		 * 应收帐龄分析表属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long uinsertReceiveaccountage(LoanReceiveaccountage info) throws Exception
	 {
		 long resultid = -1;
		 Collection cQT = info.getOthersStockHolder();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息");
						LoanReceiveaccountage QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanReceiveaccountage();//
							QTInfo = (LoanReceiveaccountage)it.next();
							if(QTInfo.getId()<=0)
							{
							resultid = customerfeedbackDao.insertReceiveaccountage(QTInfo);
							}else{
								resultid = customerfeedbackDao.updateReceiveaccountage(QTInfo);
							}
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		 
	 }
	 /**
		 * 查询银行负债明细属性清单
		 * @param creditgradeid
		 * @return
		 * @throws Exception
		 */
		public LoanBankliabilitiesdetail findBankliabilitiesdetail(long creditgradeid,long itemid) throws Exception {
			LoanBankliabilitiesdetail loanBankliabilitiesdetail=new LoanBankliabilitiesdetail();
			try{
				
				loanBankliabilitiesdetail=customerfeedbackDao.findBankliabilitiesdetail(creditgradeid, itemid);
			}catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return loanBankliabilitiesdetail;
			
		}
	 /**
		 * 查询保存应收帐龄分析表
		 * @param creditgradeid
		 * @param accountage
		 * @return
		 * @throws Exception
		 */
		public LoanReceiveaccountage findReceiveaccountage(long creditgradeid,
				long accountage) throws Exception {
			LoanReceiveaccountage loanReceiveaccountage=new LoanReceiveaccountage();
			try{
				
				loanReceiveaccountage=customerfeedbackDao.findReceiveaccountage(creditgradeid, accountage);
			}catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return loanReceiveaccountage;
			
		}
	 /**
		 * 保存收帐款前五名单位信息属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long insertReceivefundsonaccount(LoanReceivefundsonaccount info) throws Exception
	 {
		 long resultid = -1;
		 Collection cQT = info.getReceivefundsonaccount();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息assa");
						LoanReceivefundsonaccount QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanReceivefundsonaccount();//
							QTInfo = (LoanReceivefundsonaccount)it.next();
							if(QTInfo.getId()<=0)
							{
							resultid = customerfeedbackDao.insertReceivefundsonaccount(QTInfo);
							}else
							{
							resultid = customerfeedbackDao.updateReceivefundsonaccount(QTInfo);
							}
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		 
	 }
	 /**
		 * 保存收帐款前五名单位信息属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long uinsertReceivefundsonaccount(LoanReceivefundsonaccount info) throws Exception
	 {
		 long resultid = -1;
		 Collection cQT = info.getReceivefundsonaccount();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息assa");
						LoanReceivefundsonaccount QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanReceivefundsonaccount();//
							QTInfo = (LoanReceivefundsonaccount)it.next();
							if(QTInfo.getId()<=0)
							{
							resultid = customerfeedbackDao.insertReceivefundsonaccount(QTInfo);
							}else{
								resultid = customerfeedbackDao.updateReceivefundsonaccount(QTInfo);
							}
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		 
	 }
	 /**
		 * 查询收帐款前五名单位信息属性清单
		 * @param creditgradeid
		 * @param accountage
		 * @return
		 * @throws Exception
		 */
		public LoanReceivefundsonaccount findReceivefundsonaccount(long creditgradeid,
				long order) throws Exception {
			
			LoanReceivefundsonaccount Receivefundsonaccount=new LoanReceivefundsonaccount();
			try{
				
				Receivefundsonaccount=customerfeedbackDao.findReceivefundsonaccount(creditgradeid, order);
			}catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return Receivefundsonaccount;
			
		}
		/**
		 * 查询经营分析属性清单
		 * @param creditgradeid
		 * @return
		 * @throws Exception
		 */
		public LoanManageanalyse findManageanalyse(long creditgradeid) throws Exception {
			LoanManageanalyse anageanalyse=new LoanManageanalyse();
			try{
				
				anageanalyse=customerfeedbackDao.findManageanalyse(creditgradeid);
			}catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return anageanalyse;
			
		}
	 /**
		 * 银行负债明细属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long insertBankliabilitiesdetail(LoanBankliabilitiesdetail info) throws Exception
	 {
		 long resultid = -1;
		 Collection cQT = info.getBankliabilitiesdetail();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息assa");
						LoanBankliabilitiesdetail QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanBankliabilitiesdetail();//
							QTInfo = (LoanBankliabilitiesdetail)it.next();
							if(QTInfo.getId()<=0 )
							{
								resultid = customerfeedbackDao.insertBankliabilitiesdetail(QTInfo);
								
								
							}else
							{
								resultid = customerfeedbackDao.updateBankliabilitiesdetail(QTInfo);
							}
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		 
	 }
//add by pengwang
	 public long insertBankliabilitiesdetail(LoanBankliabilitiesdetail info,String details) throws Exception
	 {
		 long resultid = -1;
		 Collection cQT = info.getBankliabilitiesdetail();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息assa");
						LoanBankliabilitiesdetail QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanBankliabilitiesdetail();//
							QTInfo = (LoanBankliabilitiesdetail)it.next();
							if(QTInfo.getId()<=0 )
							{
								resultid = customerfeedbackDao.insertBankliabilitiesdetail(QTInfo,details,it);
								
								
							}else
							{
								resultid = customerfeedbackDao.updateBankliabilitiesdetail(QTInfo,details,it);
							}
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		 
	 }
	 
	 
	 /** 
	  * 对外或有负债分析 查询
	  * @auter pengwang
	  * @param creditid
	  * @return
	  * @throws Exception
	  */
	 	 
	 	 
	 	 public CreditFrameInfo queryLoan_assureoracceptsynopsis(long creditid) throws Exception
	 	 {
	 		 CreditFrameInfo info=new CreditFrameInfo();
	 		
	 		try 
	 		{
	 			info = customerfeedbackDao.queryLoan_assureoracceptsynopsis(creditid);
	 		} 
	 		catch (Exception e) {
	 			e.printStackTrace();
	 			throw new SettlementException();
	 		}
	 		return info;	 
	 	 }
/** 
 * 银行负债情况-银行信贷登记系统记录 查询
 * @auter pengwang
 * @param creditid
 * @return
 * @throws Exception
 */
	 
	 
	 public CreditFrameInfo query(long creditid) throws Exception
	 {
		 CreditFrameInfo info=new CreditFrameInfo();
		
		try 
		{
			info = customerfeedbackDao.query(creditid);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return info;	 
	 }
	 
	 
	 
	 
/**
 * 信用等级明细属性清单
 * 
 * @param info
 * @return
 * @throws Exception
 */
public long insertCreditgradedetail(LoanCreditgradedetail info) throws Exception
   {

	 long resultid = -1;
	 Collection cQT = info.getCreditgradedetail();
	
		try {
			 if((cQT != null) && (cQT.size() > 0))
				{
					Log.print("重新添加其它股东单位信息assa");
					LoanCreditgradedetail QTInfo=null;
					Iterator it = cQT.iterator();
					while(it.hasNext())
					{
						QTInfo = new LoanCreditgradedetail();//
						QTInfo = (LoanCreditgradedetail)it.next();
						if(QTInfo.getId()<=0)
						{
						
						resultid = customerfeedbackDao.insertCreditgradedetail(QTInfo);
						}else
						{
						resultid = customerfeedbackDao.updateCreditgradedetail(QTInfo);
						}
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return resultid;
   }
public long updateCreditgradedetail(LoanCreditgradedetail info) throws Exception
{

	 long resultid = -1;
	 Collection cQT = info.getCreditgradedetail();
	
		try {
			 if((cQT != null) && (cQT.size() > 0))
				{
					Log.print("重新添加其它股东单位信息assa");
					LoanCreditgradedetail QTInfo=null;
					Iterator it = cQT.iterator();
					while(it.hasNext())
					{
						QTInfo = new LoanCreditgradedetail();//
						QTInfo = (LoanCreditgradedetail)it.next();
						
						// TODO:
						resultid = customerfeedbackDao.updateCreditgradedetailto(QTInfo);
						
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return resultid;
}
	 /**
		 * 对外或有负债分析属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long insertExternalliabilities(LoanExternalliabilities info) throws Exception
	 {
		 
		 
		 
		 long resultid = -1;
		 Collection cQT = info.getExternalliabilities();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息assa");
						LoanExternalliabilities QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanExternalliabilities();//
							QTInfo = (LoanExternalliabilities)it.next();
							if(QTInfo.getId()<=0 )
							{
								resultid = customerfeedbackDao.insertExternalliabilities(QTInfo);
								
								
							}else
							{
								resultid = customerfeedbackDao.updateExternalliabilities(QTInfo);
							}
						}
					}
		
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
	 }
	 /**
		 * 对外或有负债分析属性清单
		 * auter pengwang
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long insertExternalliabilities(LoanExternalliabilities info,String details) throws Exception
	 {
		 
		 
		 
		 long resultid = -1;
		 Collection cQT = info.getExternalliabilities();
		
			try {
				 if((cQT != null) && (cQT.size() > 0))
					{
						Log.print("重新添加其它股东单位信息assa");
						LoanExternalliabilities QTInfo=null;
						Iterator it = cQT.iterator();
						while(it.hasNext())
						{
							QTInfo = new LoanExternalliabilities();//
							QTInfo = (LoanExternalliabilities)it.next();
							if(QTInfo.getId()<=0 )
							{
								resultid = customerfeedbackDao.insertExternalliabilities(QTInfo,details,it);
								
								
							}else
							{
								resultid = customerfeedbackDao.updateExternalliabilities(QTInfo,details,it);
							}
						}
					}
		
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
	 }
	 /**
		 * 集团内部往来属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long insertGroupinsidecontact(LoanGroupinsidecontact info) throws Exception
	 {
		 long resultid = -1;
			try {
				if(info.getId()<=0)
				{
				resultid = customerfeedbackDao.insertGroupinsidecontact(info);
				}else
				{
					resultid = customerfeedbackDao.updateGroupinsidecontact(info);	
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
	 }
	 /**
		 * 经营分析属性清单
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 */
	 public long insertManageanalyse(LoanManageanalyse info) throws Exception
	    {
		  long resultid = -1;
			try {
				
				if(info.getId()<=0)
				{
				resultid = customerfeedbackDao.insertManageanalyse(info);
				}else
				{
					resultid = customerfeedbackDao.updateManageanalyse(info);	
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
	    }
	 /**
		 * 保存被担保/承兑企业简况属性清单
		 * @param info
		 * @return
		 * @throws Exception
		 */
		public long insertAssureoracceptsynopsis(LoanAssureoracceptsynopsis info) throws Exception {
			long resultid = -1;
			try {
				
				resultid = customerfeedbackDao.insertAssureoracceptsynopsis(info);
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		}
		/**
		 * 查询被担保/承兑企业简况属性清单
		 * @param creditgradeid
		 * @return
		 * @throws Exception
		 */
		public LoanAssureoracceptsynopsis findAssureoracceptsynopsis(long creditgradeid) throws Exception {
			LoanAssureoracceptsynopsis assureoracceptsynopsis=new LoanAssureoracceptsynopsis();
			try{
				
				assureoracceptsynopsis=customerfeedbackDao.findAssureoracceptsynopsis(creditgradeid);
			}catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return assureoracceptsynopsis;
		}
		/**
		 * 修改被担保/承兑企业简况属性清单
		 * @param pdInfo
		 * @return
		 * @throws SettlementException 
		 */
		public long updateAssureoracceptsynopsis(LoanAssureoracceptsynopsis pdInfo) throws SettlementException
	    {
			long resultid = -1;
			try {
				
				resultid = customerfeedbackDao.updateAssureoracceptsynopsis(pdInfo);
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
			
	    }
		/**
		 * 修改银行信贷登记系统记录表属性清单
		 * @param pdInfo
		 * @return
		 * @throws SettlementException 
		 */
		public long updateBankrecord(LoanBankrecord pdInfo) throws SettlementException
	    {
			long resultid = -1;
			try {
				
				resultid = customerfeedbackDao.updateBankrecord(pdInfo);
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
			
	    }
		/**
		 * 查询银行信贷登记系统记录表属性清单
		 * @param creditgradeid
		 * @return
		 * @throws Exception
		 */
		public LoanBankrecord findBankrecord(long creditgradeid) throws Exception {
			LoanBankrecord bankrecord=new LoanBankrecord();
			try{
				
				bankrecord=customerfeedbackDao.findBankrecord(creditgradeid);
			}catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return bankrecord;
		}
		/**
		 * 银行信贷登记系统记录表属性清单
		 * @param info
		 * @return
		 * @throws Exception
		 */
		public long insertBankrecord(LoanBankrecord info) throws Exception {
			long resultid = -1;
			try {
				
				resultid = customerfeedbackDao.insertBankrecord(info);
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
		}
		
	 /**
		 * wxsu 20080623 22:43
		 * 修改经营分析属性清单 
		 * 
		 * @param pdInfo
		 * @return
	 * @throws SettlementException 
		 */
		public long updateManageanalyse(LoanManageanalyse pdInfo) throws SettlementException
	    {
			long resultid = -1;
			try {
				
				resultid = customerfeedbackDao.updateManageanalyse(pdInfo);
			} catch (Exception e) {
				e.printStackTrace();
				throw new SettlementException();
			}
			return resultid;
	    }
		
		public long  insertByDB(long creditid,String details) throws Exception
		 {
			 long lResult=-1;
			 try
			 {
				 lResult = customerfeedbackDao.insertByDB(creditid, details);
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
			 return lResult;
		 }
	/**
	 * 查询财务报表分析info的List
	 * @author lcliu
	 * @ 08-11-18
	 */
	public List findFinanceAnalyseList(
			long clientIdFrom,
			long clientIdTo,
			long cycleYearFrom,
			long cycleMonthFrom,
			long cycleYearTo,
			long cycleMonthTo,
			String reportName,
			long isAudit
			)
	{
		return fdao.findFinanceAnalyseList(clientIdFrom,
				clientIdTo,
				cycleYearFrom,
				cycleMonthFrom,
				cycleYearTo,
				cycleMonthTo,
				reportName,
				isAudit);
	}
	
	/**
	 * 查询财务报表分析info
	 * @author lcliu
	 * @ 08-11-18
	 */
	public LoanFinanceanalyse findFinanceAnalyse(long id)
	{
		return fdao.findFinanceAnalyse(id);
	}
	
	/**
	 * 查询财务报表分析info
	 * @author lcliu
	 * @ 08-11-18
	 */
	public LoanFinanceanalyse findFinanceAnalyse(long clientID, String cycleYear, String cycleMonth)
	{
		return fdao.findFinanceAnalyse(clientID, cycleYear, cycleMonth);
	}
	
	/**
	 * 保存财务报表分析info
	 * 若不存在则插入
	 * 若存在则更新
	 * @author lcliu @ 08-11-28
	 * @param info
	 * @return
	 */
	public long saveFinanceAnalyse(LoanFinanceanalyse info)
	{
		long id = fdao.findFinanceAnalyse(info);
		if (id <= 0)
		{
			return fdao.insertFinanceAnalyse(info);
		}
		else
		{
			info.setId(id);
			return fdao.updateFinanceAnalyse(info);
		}
	}
	
	/**
	 * 插入财务报表分析数据
	 * @author lcliu
	 * @ 08-11-18
	 */
	public long insertFinanceAnalyse(LoanFinanceanalyse info)
	{
		return fdao.insertFinanceAnalyse(info);
	}

	/**
	 * 更新财务报表分析数据
	 * @author lcliu
	 * @version 08-11-18
	 * @param isAudit
	 * @param explain
	 * @param id
	 * @return boolean
	 */
	public boolean updateFinanceAnalyse(long isAudit,long reportNameId, String explain, long state, long id)
	{
		return fdao.updateFinanceAnalyse(isAudit, reportNameId, explain, state, id);
	}
	
	/**
	 * 删除财务报表分析数据
	 * @author lcliu
	 */
	public boolean deleteFinanceAnalyse(long id)
	{
		return fdao.deleteFinanceAnalyse(id);
	}
	
	/**
	 * 插入财务报表分析数据数据项
	 * @author lcliu
	 * @version 08-11-18
	 */
	public boolean insertFinanceDetail(List lst)
	{
		return fdao.insertFinanceDetail(lst);
	}
	
	/**
	 * 查询财务报表分析数据数据项
	 * @author lcliu
	 */
	public Map findFinanceDetailList(long financeId)
	{
		return fdao.findFinanceDetailList(financeId);
	}

	/**
	 * 根据客户ID查找最近的财务分析项
	 * @author lcliu
	 * @param clientID
	 * @return double数组：
	 * 				0--为资产规模
	 * 				1--净资产
	 * 				2--销售收入
	 * 				3--净利润
	 */
	public double[] findLatestFinancialItem(long clientID)
	{
		return fdao.findLatestFinancialItem(clientID);
	}

	/**
	 * 根据客户ID查找最近的财务分析项
	 * @author lcliu
	 * @param clientID
	 * @return double数组：
	 * 				0--为资产规模
	 * 				1--净资产
	 * 				2--销售收入
	 * 				3--净利润
	 */
	public double[] findLatestFinancialItem(long clientID, long creditGradeId)
	{
		return fdao.findLatestFinancialItem(clientID, creditGradeId);
	}
	
	/**
	 * 查询指定客户之前在指定年月之前24个月的主营业务收入之和（包括指定月份）
	 * @param clientID
	 * @param year
	 * @param month
	 * @return double[]，其中：
	 * 		double[0]为指定年月 之前第11月 到 当前月 的主营业务收入之和
	 * 		double[1]为指定年月 之前第23月 到 之前第12月 的主营业务收入之和
	 */
	public double[] getAmountOfLastYear(long clientID, long year, long month)
	{
		double[] amount = new double[2];
		amount[0] = fdao.getAmountOfLastYear(clientID, year, month);
		amount[1] = fdao.getAmountOfLastYear(clientID, year-1, month);
		return amount;
	}
	
	/**
	 * 根据客户ID查找有效的财务分析记录的ID
	 * DEBUG 11007 by lcliu at 09-2-12
	 * @param clientId
	 * @return
	 */
	public long findByClientId(long clientId)
	{
		return customerfeedbackDao.findByClientId(clientId);
	}
	
	/**
	 * 更新loan_creditgrade表中的cycleYear和cycleMonth字段。
	 * @author lcliu
	 * @param id
	 * @param cycleYear
	 * @param cycleMonth
	 * @return
	 */
	public long updateCreditCycle(long Id, String cycleYear, String cycleMonth)
	{
		return customerfeedbackDao.updateCreditCycle(Id, cycleYear, cycleMonth);
	}
	
	/**
	 * 从loan_creditgrade表中查询财务周期并以字符串数组返回
	 * @author lcliu
	 * @param Id
	 * @return
	 */
	public String[] queryCreditCycle(long Id)
	{
		return customerfeedbackDao.queryCreditCycle(Id);
	}
}
