package com.iss.itreasury.craftbrother.query.dao;

import com.iss.itreasury.craftbrother.query.dataentity.AssetInterestCalInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

public class QueryAssetInterestCalDAO {
		private StringBuffer m_sbSelect = null;
		private StringBuffer m_sbFrom = null;
		private StringBuffer m_sbWhere = null;
		private StringBuffer m_sbOrderBy = null;
		
		public QueryAssetInterestCalDAO()
		{
			
		}
		
		public PageLoader QueryAssetInterestCal(AssetInterestCalInfo info) throws Exception
		{
			getContractSQL(info);
			
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(
				new AppContext(),
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.craftbrother.query.dataentity.AssetInterestCalResultInfo",
				null);
			pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
			return pageLoader;
		}
		
		public void getContractSQL(AssetInterestCalInfo info)
		{
			//select
			m_sbSelect = new StringBuffer();
			//from 
			m_sbFrom = new StringBuffer();
			//where 
			m_sbWhere = new StringBuffer();
			//order 
			m_sbOrderBy = new StringBuffer();
			m_sbSelect.append(" distinct c.dtoutdate outdate, a.payid payid, e.transactiontypeid contractType, a.clientId borrowclientId,e.code loanContractNo, \n");
			m_sbSelect.append(" c.scode payFormNo,a.contractcode attornContractNo,e.Counterpartbankid bankId,to_date('"+DataFormat.getDateString(info.getStartInputDate())+"','yyyy-MM-dd')  interestStartDate, \n");
			m_sbSelect.append(" to_date('"+DataFormat.getDateString(info.getEndInputDate())+"','yyyy-MM-dd') interestEndDate,a.attornmentamount comReceiveInterestAmount,(ssub.mbalance-bb.lastattornmentamount) selfHaveAmount , \n");
			m_sbSelect.append(" e.commissionchargerate commissionchargerate,e.rate rate,c.minterestrate payformrate,c.mstaidadjustrate mstaidadjustrate,c.madjustrate madjustrate \n");
			m_sbFrom.append(" sec_attornmentcontract a,sec_attornmentapplyform b,loan_payform c,loan_contractform d,sec_applycontract e \n");
			
			m_sbFrom.append(" ,sett_subaccount ssub,sec_applyform sa ");
			
			m_sbFrom.append(" , (select sum(a.lastattornmentamount) lastattornmentamount,a.id id,a.payid payid \n");
			m_sbFrom.append(" from sec_attornmentcontract a \n");
			
			m_sbFrom.append(" where 1 = 1 \n");
			
			m_sbFrom.append(" and a.statusid in ( ");
			m_sbFrom.append(" "+ LOANConstant.AttornmentStatus.SAVE +", ");
			m_sbFrom.append(" "+ LOANConstant.AttornmentStatus.SUBMIT +", ");
			m_sbFrom.append(" "+ LOANConstant.AttornmentStatus.CHECK +", ");
			m_sbFrom.append(" "+ LOANConstant.AttornmentStatus.USED +", ");
			m_sbFrom.append(" "+ LOANConstant.AttornmentStatus.APPROVALING +" ");
			m_sbFrom.append(" ) ");
			
			m_sbFrom.append(" group by a.id,a.payid \n");
			m_sbFrom.append(" ) bb  \n");
			
			m_sbWhere.append(" a.attornmentapplyid = b.id \n");
			m_sbWhere.append(" and  a.payid = c.id  \n");
			m_sbWhere.append(" and c.ncontractid = d.id \n");
			m_sbWhere.append(" and e.applyid = b.repurchaseapplyid \n");
			
			m_sbWhere.append(" and ssub.al_nloannoteid = a.payid \n");
			
			m_sbWhere.append(" and bb.payid = c.id \n");
			m_sbWhere.append(" and bb.id = a.id \n");
			
			m_sbWhere.append(" and e.statusid in ( \n");
			m_sbWhere.append(" "+ SECConstant.ContractStatus.NOTACTIVE +", ");
			m_sbWhere.append(" "+ SECConstant.ContractStatus.ACTIVE +", ");
			m_sbWhere.append(" "+ SECConstant.ContractStatus.EXTEND +", ");
			m_sbWhere.append(" "+ SECConstant.ContractStatus.FINISH +" ");
			m_sbWhere.append(" ) \n");
			
			m_sbWhere.append(" and e.applyid = sa.id \n");
			m_sbWhere.append(" and sa.transactiontypeid = "+ SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY +" \n");
			
			//m_sbWhere.append(" and bb.amount>0 \n");//用于限制当在贷款做资产配置的时候，选择合同后没有对放款通知单进行配置，那么在查询的时候不要将该放款通知单查询出来
			
			if(info.getTransactionTypeId() > 0){//转让交易类型
				m_sbWhere.append(" and e.transactiontypeid = "+info.getTransactionTypeId());
			}
			if(info.getContractIDFrom() > 0){//贷款合同开始
				m_sbWhere.append(" and a.contractid >="+info.getContractIDFrom());
			}
			if(info.getContractIDTo() > 0){//贷款合同结束
				m_sbWhere.append(" and a.contractid <="+info.getContractIDTo());
			}
			if(info.getZContractCodeFrom() !=null && info.getZContractCodeFrom()!=""){//转让合同开始
				m_sbWhere.append(" and  e.code >='"+info.getZContractCodeFrom()+"'");
			}
			if(info.getZContractCodeTo() !=null && info.getZContractCodeTo()!=""){//转让合同结束
				m_sbWhere.append(" and  e.code <='"+info.getZContractCodeTo()+"'");
			}
			if(info.getClientID() > 0){//借款单位
				m_sbWhere.append(" and a.clientId = "+info.getClientID());
			}
			if(info.getSrBankID() > 0){//受让银行
				m_sbWhere.append(" and e.Counterpartbankid = "+info.getSrBankID());
			}
			if(info.getOfficeId() > 0){
				m_sbWhere.append(" and d.nofficeId = "+info.getOfficeId());
			}
			if(info.getCurrencyId() > 0){
				m_sbWhere.append(" and d.ncurrencyid = "+info.getCurrencyId());
			}
			
			System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"");
		}
	}

