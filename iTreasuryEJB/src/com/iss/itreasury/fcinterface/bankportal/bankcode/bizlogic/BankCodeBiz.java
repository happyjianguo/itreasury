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
	 * ͨ����ѯ������ѯ���б�ź���������
	 * 
	 * @param bankCode
	 *            //���б���
	 * @param bankName
	 *            //��������
	 * @param provinceCode
	 *            //ʡ����
	 * @param cityCode
	 *            //������
	 * @param keyWord
	 *            //���йؼ���
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
				{ // ����ʡ����û�в���������롣
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
				throw new BusinessException("ʡ������Ϊ�գ�������ѡ��!");
			}
		} catch (Exception e) 
		{
			log.info(e);
			e.printStackTrace();
			throw new BusinessException("��������ʱ����" + e.getMessage());
		}
		return pageLoader;
	}
	
	/**
	 * �����кŲ�ѯ
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
				{ // ����ʡ����û�в���������롣
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
				throw new BusinessException("ʡ������Ϊ�գ�������ѡ��!");
			}
			
			//�õ���ѯSQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(BankCodeBiz.class, "queryBankCodeResultSetHandle", paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
    
	/**
	 * �����кŲ�ѯ
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryBankCodeResultSetHandle(ResultSet rs, Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��

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
						//��ȡ����
						strBankCode = rs.getString("bankcode");
						strBankName = rs.getString("bankname");		
						areacode = rs.getString("areacode");		
						//�洢������
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,strBankCode+","+strBankCode+","+strBankName+","+areacode);		
						PagerTools.returnCellList(cellList,strBankName);
						//�洢������
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//��������
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
