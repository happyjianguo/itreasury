package com.iss.itreasury.fcinterface.bankportal.bankcode.bizlogic;

/**
 * fszhu
 * 2008-11-27
 * 
 */
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.fcinterface.bankportal.bankcode.BranchNOIdentify;
import com.iss.itreasury.fcinterface.bankportal.bankcode.dao.BankCodeDAO_oracle;
import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.AreaCodeInfo;
import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.BankCodeInfo;
import com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.BankCodeParamInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business.BusinessException;
import com.iss.itreasury.fcinterface.bankportal.util.Logger;
import com.iss.itreasury.system.bulletin.bizlogic.BulletinBiz;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import com.iss.system.dao.PageLoader;

public class BankCodeBiz 
{
	BankCodeDAO_oracle dao = new BankCodeDAO_oracle();
	private static Logger log = new Logger(BankCodeBiz.class);
	/**
	 * 
	 * 通过查询条件查询银行编号和银行名称
	 * 
	 * @param bankCode
	 *            //银行编码
	 * @param bankName
	 *            //银行名称
	 * @param provinceCode
	 *            //省名称
	 * @param cityCode
	 *            //市名称
	 * @param keyWord
	 *            //银行关键字
	 * @return PageLoader
	 * @throws SystemException
	 */
	public PageLoader findBankNOByCondition(BankCodeParamInfo condition)
			throws BusinessException  
	{
		log.info("Enter BankCodeBiz.findBankNOByCondition()...");
		PageLoader pageLoader = null;
		try 
		{
			String bankTypeCode = null;
			if (condition.getProvinceName() != null
					&& condition.getProvinceName().length() > 0) 
			{
				
					bankTypeCode = BranchNOIdentify
							.getBankTypeCodeByBankName(condition
									.getBankTypeName());
				condition.setBankTypeCode(bankTypeCode);
				String[] areaCodes = null;

				if (!(condition.getProvinceName() == null 
						&& condition.getCityName() == null)) 
				{
					AreaCodeBiz areaCodeBiz = new AreaCodeBiz();
					AreaCodeInfo query = new AreaCodeInfo();
					query.setAreaProvince(condition.getProvinceName());
					query.setAreaName(condition.getCityName());
					areaCodes = areaCodeBiz.findAreaCodeByCondition(query);
				}
				if ((areaCodes == null || areaCodes.length <= 0)
						&& (condition.getProvinceName() != null || condition
								.getCityName() != null)) 
				{ // 根据省市名没有查出来地区码。
					return null;
				}
				BankCodeDAO_oracle bankCodeDao = new BankCodeDAO_oracle();
				BankCodeInfo query = new BankCodeInfo();
				query.setBankCode(condition.getBankCode());
				query.setBankTypeCode(condition.getBankTypeCode());
				query.setBankName(condition.getBankName());
				pageLoader = bankCodeDao.findBankNOByCondition(query, areaCodes);
			}
			else
			{
				throw new BusinessException("省名不能为空，请重新选择!");
			}
		} catch (Exception e) 
		{
			log.info(e);
			e.printStackTrace();
			throw new BusinessException("查找银行时出错：" + e.getMessage());
		}
		return pageLoader;
	}
	
	/**
	 * 行名行号查询
	 * @param bulletinInfo
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryBankCode(BankCodeParamInfo condition) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("condition", condition);
			
			String bankTypeCode = null;
			if (condition.getProvinceName() != null && condition.getProvinceName().length() > 0) 
			{
				
				bankTypeCode = BranchNOIdentify.getBankTypeCodeByBankName(condition.getBankTypeName());
				condition.setBankTypeCode(bankTypeCode);
				String[] areaCodes = null;

				if (!(condition.getProvinceName() == null && condition.getCityName() == null)) 
				{
					AreaCodeBiz areaCodeBiz = new AreaCodeBiz();
					AreaCodeInfo query = new AreaCodeInfo();
					query.setAreaProvince(condition.getProvinceName());
					query.setAreaName(condition.getCityName());
					areaCodes = areaCodeBiz.findAreaCodeByCondition(query);
				}
				if ((areaCodes == null || areaCodes.length <= 0)
						&& (condition.getProvinceName() != null || condition
								.getCityName() != null)) 
				{ // 根据省市名没有查出来地区码。
					return null;
				}
				BankCodeDAO_oracle bankCodeDao = new BankCodeDAO_oracle();
				BankCodeInfo query = new BankCodeInfo();
				query.setBankCode(condition.getBankCode());
				query.setBankTypeCode(condition.getBankTypeCode());
				query.setBankName(condition.getBankName());
				sql = dao.getQueryBankCodeSQL(query,areaCodes);
			}
			else
			{
				throw new BusinessException("省名不能为空，请重新选择!");
			}
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(BankCodeBiz.class, "queryBankCodeResultSetHandle", paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
    
	/**
	 * 行名行号查询
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryBankCodeResultSetHandle(ResultSet rs, Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行

		BankCodeParamInfo condition = (BankCodeParamInfo)paramMap.get("condition");
		String bankName = condition.getLbankName();
		String strBankCode = "";
		String strBankName = "";
		String areacode = "";
		try{
			
				if(rs!=null)
				{
					while(rs.next())
					{
						//获取数据
						strBankCode = rs.getString("bankcode");
						strBankName = rs.getString("bankname");		
						areacode = rs.getString("areacode");		
						//存储列数据
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,strBankCode+","+strBankCode+","+strBankName+","+areacode);		
						PagerTools.returnCellList(cellList,strBankName);
						//存储行数据
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//返回数据
						resultList.add(rowInfo);
					
					}
				}
			    	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
}
