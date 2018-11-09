package com.iss.itreasury.credit.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.credit.check.CreditCheckBiz;
import com.iss.itreasury.credit.setting.dao.AmountFormDao;
import com.iss.itreasury.credit.setting.dao.AmountSetupDao;
import com.iss.itreasury.credit.setting.dao.SubAmountFormDao;
import com.iss.itreasury.credit.setting.dao.SubAmountSetupDao;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupViewInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

public class AmountFromQueryBiz {

	private AmountFormDao amountFormDao = null;
	
	private AmountSetupDao amountSetupDao = null;
	
	
	
	public AmountFromQueryBiz()
	{
		
	}
	/**
	 * 授信可用额度查询列表
	 * 
	 * @author 马现福
	 * @param amountFormViewInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public List getCreditRemainAmountQuery(AmountFormViewInfo amountFormViewInfo)throws ITreasuryDAOException
	{
		amountFormDao = new AmountFormDao();
		
		CreditCheckBiz creditCheckBiz = new CreditCheckBiz();
		
		List amountFormListNew = new ArrayList();
		
        //已使用的授信额度
		double dUsedAmount = 0.0;
		//剩余的授信额度
		double dRemainAmount = 0.0;
		
		try{
				List amountFormList = amountFormDao.getCreditRemainAmountQuery(amountFormViewInfo);

				if(amountFormList != null && amountFormList.size() > 0)
				{
					    for(int nCount = 0;nCount < amountFormList.size();nCount++)
					    {
					    	AmountFormViewInfo formViewInfo = (AmountFormViewInfo)amountFormList.get(nCount);
					        
                            //计算已使用的授信额度
							if(formViewInfo.getCreditModel() == LOANConstant.CreditModel.GROUP)
							{
								dUsedAmount = creditCheckBiz.getGroupUsedCreditAmount(formViewInfo);
							}
							else
							{
								dUsedAmount = creditCheckBiz.getUsedCreditAmount(formViewInfo);
							}
							
							//计算剩余的授信额度
							dRemainAmount = UtilOperation.Arith.sub(formViewInfo.getCreditAmount(), dUsedAmount);
							
							formViewInfo.setUsedAmount(dUsedAmount);
							
							formViewInfo.setRemainAmount(dRemainAmount);
		
							amountFormListNew.add(formViewInfo);
							
					    }
				}
				
		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("Gen_E001",e);
		}
		
		return amountFormListNew;
	}
	
	/**
	 * 授信历史记录查询列表
	 * 
	 * @param amountSetupViewInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public PageLoader getCreditRecordQuery(AmountSetupViewInfo amountSetupViewInfo)throws ITreasuryDAOException
	{
		amountSetupDao = new AmountSetupDao();
		
		return amountSetupDao.getCreditRecordQuery(amountSetupViewInfo);
	}
	
	/**
	 *　获取授信历史记录查询列表的order by，用于pageloader 
	 * 
	 * @param lDesc 升序或降序
	 * @param lOrderParam　排序的字段号　
	 * @return
	 */
	public  String getCreditRecordQueryOrderParam(long lDesc,long lOrderParam)
	{
		amountSetupDao = new AmountSetupDao();
		
		return amountSetupDao.getCreditRecordQueryOrderParam(lDesc, lOrderParam);
	}
	
	/**
	 * 查询某一有效授信的信息，包括已占用额度和可用额度
	 * 
	 * @param ID　授信id　
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountFormViewInfo getAmountFormViewDetail(long ID)throws ITreasuryDAOException
	{
		double dUsedAmount = 0.0;//已使用的授信额度
		
		double dRemainAmount = 0.0;//剩余的授信额度
		
		AmountFormViewInfo amountFormViewInfo = null;
		
		amountFormDao = new AmountFormDao();
		
		CreditCheckBiz creditCheckBiz = new CreditCheckBiz();
		
		SubAmountFormDao subAmountFormDAO = new SubAmountFormDao();
	    try{
	    	
			amountFormViewInfo = amountFormDao.getAmountFormViewDetail(ID);
			
			amountFormViewInfo.setSubAmountFormList(subAmountFormDAO.getSubAmountFormList(ID));
			
			if(amountFormViewInfo.getCreditModel() == LOANConstant.CreditModel.GROUP) //计算已使用的授信额度
			{
				dUsedAmount = creditCheckBiz.getGroupUsedCreditAmount(amountFormViewInfo);
			}
			else
			{
				dUsedAmount = creditCheckBiz.getUsedCreditAmount(amountFormViewInfo);
			}
			
			
			dRemainAmount = UtilOperation.Arith.sub(amountFormViewInfo.getCreditAmount(), dUsedAmount);//计算剩余的授信额度
			
	        amountFormViewInfo.setUsedAmount(dUsedAmount);
			
			amountFormViewInfo.setRemainAmount(dRemainAmount);
			
		
		
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    	throw new ITreasuryDAOException("Gen_E001",e);
	    }

		return amountFormViewInfo;
	}
	
	
	/**
	 * 查询某一授信记录的明细信息
	 * 
	 * @param amountSetupInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountSetupViewInfo getCreditAmountSetupView(AmountSetupInfo amountSetupInfo)throws ITreasuryDAOException
	{
		amountSetupDao = new AmountSetupDao();
		
		SubAmountSetupDao subAmountSetupDao = new SubAmountSetupDao();
		
		AmountSetupViewInfo amountSetupViewInfo = amountSetupDao.getCreditAmountSetupView(amountSetupInfo);
		
		amountSetupViewInfo.setSubAmountSetupCollection(subAmountSetupDao.getSubCreditAmountSetupColl(amountSetupViewInfo.getAmountSetupInfo()));
		
		return amountSetupViewInfo;
	}
	
	/**
	 * 查询某一授信的已占用详细情况，包括申请，合同和还款通知单（方法分支）
	 * 授信校验方式（自营贷款），1.放款通知单申请时校验,2.贷款申请时校验
	 * @author 马现福
	 * @param ID 授信ID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountFormViewInfo getUsedAmountDetail(long ID)throws ITreasuryDAOException
	{
		
		AmountFormViewInfo amountFormViewInfo = null;
		
	    try{
			if(Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1)
			{
				amountFormViewInfo = getUsedAmountDetail1(ID);
			}else{
				amountFormViewInfo = getUsedAmountDetail2(ID);
			}
	    }catch(Exception e){	    	
	    	e.printStackTrace();
	    	throw new ITreasuryDAOException("Gen_E001",e);
	    }finally{
	    	
	    }

		return amountFormViewInfo;
	}	
	
	/**
	 * 查询某一授信的已占用详细情况，包括申请，合同和还款通知单（1.放款通知单申请时校验）
	 * 已占用金额 = 放款通知单金额(审批中、已审批、已使用) - 还款单金额
	 * @author 马现福
	 * @param ID 授信ID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountFormViewInfo getUsedAmountDetail1(long ID)throws ITreasuryDAOException
	{
		List applyInfoList = new ArrayList();//申请单占用金额
		List contractInfoList = new ArrayList();//合同占用金额
		List repayInfoList = new ArrayList();//还款单释放金额
		List loanUsedAmountInfoList = new ArrayList();//自营贷款放款单金额
		
		AmountFormViewInfo amountFormViewInfo = null;
		
		Connection conn = null;
	    try{
	    	conn = Database.getConnection(); //自维护connection,减少获取连接的次数，提高效率
	    	amountFormDao = new AmountFormDao(conn);
			amountFormViewInfo = amountFormDao.getAmountFormViewDetail(ID);
			if(amountFormViewInfo.getCreditModel() == LOANConstant.CreditModel.GROUP) 
			{
				applyInfoList = amountFormDao.getGroupApplyUsedAmount(amountFormViewInfo);
				contractInfoList = amountFormDao.getGroupContractUsedAmount(amountFormViewInfo);
				repayInfoList = amountFormDao.getGroupRepayUsedAmount(amountFormViewInfo);
				loanUsedAmountInfoList = amountFormDao.getGroupLoanUsedAmount(amountFormViewInfo);
			}
			else
			{
				applyInfoList = amountFormDao.getApplyUsedAmount(amountFormViewInfo);
				contractInfoList = amountFormDao.getContractUsedAmount(amountFormViewInfo);
				repayInfoList = amountFormDao.getRepayUsedAmount(amountFormViewInfo);
				loanUsedAmountInfoList = amountFormDao.getLoanUsedAmount(amountFormViewInfo);
			}
			
			//查询出授信已占用的额度明细信息
			amountFormViewInfo.setApplyInfoList(applyInfoList);
			amountFormViewInfo.setContractInfoList(contractInfoList);
			amountFormViewInfo.setRepayInfoList(repayInfoList);
			amountFormViewInfo.setLoanUsedAmountInfoList(loanUsedAmountInfoList);
			
	    }catch(Exception e){	    	
	    	e.printStackTrace();
	    	throw new ITreasuryDAOException("Gen_E001",e);
	    }finally{
	    	if(conn!=null) try { conn.close(); }catch(Exception exp){ }
	    }

		return amountFormViewInfo;
	}
	
	/**
	 * 查询某一授信的已占用详细情况，包括申请，合同和还款通知单（2.贷款申请时校验）
	 * 已占用金额 = 申请金额 + 合同金额 - 非循环贷款时还款单金额
	 * @author 马现福
	 * @param ID 授信ID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountFormViewInfo getUsedAmountDetail2(long ID)throws ITreasuryDAOException
	{
		List applyInfoList = new ArrayList();//申请单占用金额
		List contractInfoList = new ArrayList();//合同占用金额
		List repayInfoList = new ArrayList();//还款单释放金额
		
		AmountFormViewInfo amountFormViewInfo = null;
		
		Connection conn = null;
	    try{
	    	conn = Database.getConnection(); //自维护connection,减少获取连接的次数，提高效率
	    	amountFormDao = new AmountFormDao(conn);
			amountFormViewInfo = amountFormDao.getAmountFormViewDetail(ID);
			if(amountFormViewInfo.getCreditModel() == LOANConstant.CreditModel.GROUP) 
			{
				applyInfoList = amountFormDao.getGroupApplyUsedAmount(amountFormViewInfo);
				contractInfoList = amountFormDao.getGroupContractUsedAmount(amountFormViewInfo);
				repayInfoList = amountFormDao.getGroupRepayUsedAmount(amountFormViewInfo);
			}
			else
			{
				applyInfoList = amountFormDao.getApplyUsedAmount(amountFormViewInfo);
				contractInfoList = amountFormDao.getContractUsedAmount(amountFormViewInfo);
				repayInfoList = amountFormDao.getRepayUsedAmount(amountFormViewInfo);
			}
			
			//查询出授信已占用的额度明细信息
			amountFormViewInfo.setApplyInfoList(applyInfoList);
			amountFormViewInfo.setContractInfoList(contractInfoList);
			amountFormViewInfo.setRepayInfoList(repayInfoList);
			
	    }catch(Exception e){	    	
	    	e.printStackTrace();
	    	throw new ITreasuryDAOException("Gen_E001",e);
	    }finally{
	    	if(conn!=null) try { conn.close(); }catch(Exception exp){ }
	    }

		return amountFormViewInfo;
	}
	
	/**
	 * 授信报表专用
	 * @param amountFormViewInfo
	 * @throws SQLException
	 */
	public void getCreditReport(AmountFormViewInfo amountFormViewInfo) throws SQLException{
		
		try {
			List list = new ArrayList();
			amountFormDao = new AmountFormDao();
			list = this.getCreditRemainAmountQuery(amountFormViewInfo);
			if(list != null && list.size() > 0)
			{
			    for(int nCount = 0;nCount < list.size();nCount++)
			    {
			    	AmountFormViewInfo info = (AmountFormViewInfo)list.get(nCount);
			    	amountFormViewInfo = new AmountFormViewInfo();
			    	amountFormViewInfo.setId(info.getId());
			    	amountFormViewInfo.setUsedAmount(info.getUsedAmount());
			    	amountFormDao.updateUsedAmount(amountFormViewInfo);
			    }	
			}
			
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
