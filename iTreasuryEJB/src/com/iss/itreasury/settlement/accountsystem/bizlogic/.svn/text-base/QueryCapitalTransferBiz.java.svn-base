/**
 * created  2008-03-05
 */
package com.iss.itreasury.settlement.accountsystem.bizlogic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.accountsystem.dao.AccountRelationDAO;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingInfo;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingViewInfo;
import com.iss.itreasury.settlement.accountsystem.dataentity.CapitalTransferStatParam;
import com.iss.itreasury.settlement.accountsystem.dataentity.CapitalTransferStatResult;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryAccountBalanceInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.IException;


public class QueryCapitalTransferBiz {
	/**
	 * 
	 * @param param
	 * @return
	 * @throws IException
	 */
	public Collection queryAccountCapitalTransferStat(CapitalTransferStatParam param) throws IException {
		AccountRelationDAO arDao = new AccountRelationDAO();
		Collection rc = new ArrayList();
		try {
			//
			Collection c = arDao.findCollByAccountSystemId(param.getAccountSystemId(), -1);//查询该体系所有关联帐户
			if(c != null){
				Iterator it = c.iterator();
				while(it.hasNext()){
					CapitalTransferStatResult info = new CapitalTransferStatResult();
				
					//该下级账户所有上收交易实体
					Collection upDipositInfos = new ArrayList();
					//该下级账户所有下拨交易实体
					Collection downDipositInfos = new ArrayList();
					
					AccountRelationSettingViewInfo arInfo = (AccountRelationSettingViewInfo)it.next();
					long lAccountID = arInfo.getNAccountId();//下级账户ID
					String strAccountCde = arInfo.getAccountCode();//下级账户编号
					
					info.setSubAccountId(lAccountID);
					info.setSubAccountCode(arInfo.getAccountCode());
					info.setSubAccountName(arInfo.getAccountName());
					//获得该下级账户所有上收交易实体
					upDipositInfos = arDao.queryAccountCapitalTransferStat(
							param.getAccountSystemId(), lAccountID, param
									.getBeginDate(), param.getEndDate(),
							SETTConstant.TransactionType.CAPITALUP, param
									.getLOfficeID(), param.getLCurrencyID());
					//统计上收信息
					if( upDipositInfos!=null && upDipositInfos.size() > 0){
						
						info.setCountCapitalUp(upDipositInfos.size());//上收笔数
						
						double upSumAmount = 0.00;
						Iterator upIt = upDipositInfos.iterator();
						while(upIt.hasNext()){
							TransCurrentDepositInfo transCurrentDepositInfo = (TransCurrentDepositInfo)upIt.next();
							upSumAmount += transCurrentDepositInfo.getAmount();
						} 
						info.setSumCapitalUpAmount(upSumAmount);//上收总金额
					}
					//获得该下级账户所有下拨交易实体
					downDipositInfos = arDao.queryAccountCapitalTransferStat(
							param.getAccountSystemId(), lAccountID, param
									.getBeginDate(), param.getEndDate(),
							SETTConstant.TransactionType.CAPITALDOWN,
							param.getLOfficeID(),
							param.getLCurrencyID());
					//统计下拨信息
					if( downDipositInfos!=null && downDipositInfos.size() > 0){
						
						info.setCountCapitalDown(downDipositInfos.size());//下拨笔数
						
						double dwSumAmount = 0.00;
						Iterator dwIt = downDipositInfos.iterator();
						while(dwIt.hasNext()){
							TransCurrentDepositInfo transCurrentDepositInfo = (TransCurrentDepositInfo)dwIt.next();
							dwSumAmount += transCurrentDepositInfo.getAmount();
						} 
						info.setSumCapitalDownAmount(dwSumAmount);//下拨总金额
					}
					//查询期末余额
					Sett_AccountDAO accDao = new Sett_AccountDAO();
					double endingBalance = accDao.queryAccountBalanceByDate(lAccountID,param.getEndDate(),param.getLOfficeID(),param.getLCurrencyID());
					info.setEndingBalance(endingBalance);
					
					rc.add(info);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return rc;
	}
	/**
	 * 划拨统计信息
	 * @param capitalTransColl
	 * @return
	 * @throws IException
	 */
	public CapitalTransferStatResult sumCapitalTransferStat(Collection capitalTransColl)throws IException {
		CapitalTransferStatResult result = new CapitalTransferStatResult();
		double sumUpAmount =0.00;
		double sumDownAmount = 0.00;
		long sumUpCount = 0;
		long sumDownCount =0;
		double sumEndingBalance = 0.00;
		if(capitalTransColl != null){
			Iterator it = capitalTransColl.iterator();
			while(it.hasNext()){
				CapitalTransferStatResult tmpResult = (CapitalTransferStatResult)it.next();
				sumUpAmount += tmpResult.getSumCapitalUpAmount();
				sumDownAmount += tmpResult.getSumCapitalDownAmount();
				sumUpCount += tmpResult.getCountCapitalUp();
				sumDownCount += tmpResult.getCountCapitalDown();
				sumEndingBalance += tmpResult.getEndingBalance();
			}
		}
		result.setCountCapitalDown(sumDownCount);
		result.setCountCapitalUp(sumUpCount);
		result.setSumCapitalDownAmount(sumDownAmount);
		result.setSumCapitalUpAmount(sumUpAmount);
		result.setEndingBalance(sumEndingBalance);
		return result;
	}

	public TransCurrentDepositInfo queryCapitalTransferTransInfos(QueryTransactionConditionInfo qInfo) throws IException {
		
		return null;
	}
	/**
	 * 获得体系下单个下级账户所有上收/下拨交易信息
	 * @param lAccountSystemId	体系ID
	 * @param lAccountID	下级账户ID 	
	 * @param beginDate		由 日期
	 * @param endDate		到 日期
	 * @param lTransTypeID	交易类型（上收 / 下拨）
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 * @throws IException
	 */
	public Collection queryCapitalTransferTransInfos(long lAccountSystemID,
			long lAccountID, Timestamp beginDate, Timestamp endDate,
			long lTransTypeID, long lOfficeID, long lCurrencyID)
			throws IException {
		AccountRelationDAO arDao = new AccountRelationDAO();
		Collection coll = null;
		try {
			coll = arDao.queryAccountCapitalTransferStat(lAccountSystemID, lAccountID, beginDate, endDate, lTransTypeID, lOfficeID, lCurrencyID);
		} catch (Exception e) {
            throw new IException("Gen_E001");
        }
		
		return coll;
	}
}
