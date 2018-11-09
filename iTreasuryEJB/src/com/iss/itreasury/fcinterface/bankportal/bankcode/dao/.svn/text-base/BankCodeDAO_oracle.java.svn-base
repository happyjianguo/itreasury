package com.iss.itreasury.fcinterface.bankportal.bankcode.dao;

import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.BankCodeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

public class BankCodeDAO_oracle extends ITreasuryDAO
{

	/** 定义SQL语句变量* */
	private StringBuffer sbSelect = null;

	private StringBuffer sbFrom = null;

	private StringBuffer sbWhere = null;

	private StringBuffer sbOrderBy = null;

	/**
	 * 通过pageLoader 查询行名行号信息，通过地区码来确定查询的sql param String bankTypeCode
	 * 银行类别编码，AreaCodeInfo[] areaCodeInfo 地区码， String keyWord 银行关键字 return
	 * PageLoader throws SystemException
	 */
	public PageLoader findBankNOByCondition(BankCodeInfo condition,String[]areaCodes)
			throws SystemException 
	{
		getSQL(condition,areaCodes);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						sbFrom.toString(),
						sbSelect.toString(), 
						sbWhere.toString(), 
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.BankCodeInfo",
						null);
		pageLoader.setOrderBy(""+sbOrderBy.toString()+"");
		return pageLoader;
	}

	private void getSQL(BankCodeInfo condition,String[]areaCodes) 
	{
		sbSelect = new StringBuffer();
		sbFrom = new StringBuffer();
		sbWhere = new StringBuffer();
		sbOrderBy = new StringBuffer();
		sbSelect.append(" n_id as id, ");
		sbSelect.append(" s_bankcode as bankcode, ");
		sbSelect.append(" s_bankname as bankname ");
		sbFrom.append(" banknameandcode ");
		sbWhere.append(" 1=1 ");
		if (condition.getBankCode() != null && !"".equals(condition.getBankCode())) 
		{
			sbWhere.append(" and s_bankcode like '%" + condition.getBankCode() + "%' ");
		}
		if (condition.getBankName() != null && !"".equals(condition.getBankName())) 
		{
			sbWhere.append(" and s_bankname like '%" + condition.getBankName() + "%' ");
		}
		if(condition.getBankTypeCode() != null && !"".equals(condition.getBankTypeCode()))
		{
			sbWhere.append(" and s_banktypecode = '" + condition.getBankTypeCode() + "' ");
		}
		if (areaCodes != null && areaCodes.length > 0 ) 
		{
			sbWhere.append(" and ( s_areacode = '"+areaCodes[0]+"' ");
			for (int i = 1; i < areaCodes.length; i++) 
			{
				sbWhere.append(" or s_areacode ='" + areaCodes[i]+ "' ");
			}
			sbWhere.append(" ) ");
		}
		
		sbOrderBy.append(" Order by s_bankcode ");

		log.info("查询行号的SQL："+sbSelect.toString() + sbFrom.toString() + sbWhere.toString()
				+ sbOrderBy.toString());

	}
	
	public String getQueryBankCodeSQL(BankCodeInfo condition,String[]areaCodes){
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select n_id as id, ");
		sbSQL.append(" s_bankcode as bankcode, ");
		sbSQL.append(" s_bankname as bankname, s_areacode as areacode ");
		sbSQL.append("from banknameandcode ");
		sbSQL.append("where 1=1 ");
		if (condition.getBankCode() != null && !"".equals(condition.getBankCode())) 
		{
			sbSQL.append(" and s_bankcode like '%" + condition.getBankCode() + "%' ");
		}
		if (condition.getBankName() != null && !"".equals(condition.getBankName())) 
		{
			sbSQL.append(" and s_bankname like '%" + condition.getBankName() + "%' ");
		}
		if(condition.getBankTypeCode() != null && !"".equals(condition.getBankTypeCode()))
		{
			sbSQL.append(" and s_banktypecode = '" + condition.getBankTypeCode() + "' ");
		}
		if (areaCodes != null && areaCodes.length > 0 ) 
		{
			sbSQL.append(" and ( s_areacode = '"+areaCodes[0]+"' ");
			for (int i = 1; i < areaCodes.length; i++) 
			{
				sbSQL.append(" or s_areacode ='" + areaCodes[i]+ "' ");
			}
			sbSQL.append(" ) ");
		}
		
		sbSQL.append(" Order by s_bankcode ");

		return sbSQL.toString();
	}

}
