package com.iss.itreasury.settlement.bankinterface.bizlogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.bankinterface.dao.Sett_BalanceOfBankAccountDAO;
import com.iss.itreasury.settlement.bankinterface.dao.Sett_BankAccountMappingDAO;
import com.iss.itreasury.settlement.bankinterface.dao.Sett_CorpAccountMappingDAO;
//import com.iss.itreasury.settlement.bankinterface.dao.Sett_HisTransInfoOfBankAccountDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountBalanceInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountMappingInfo;
//import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountTransInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.CorpAccountMappingInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryAccountMappingInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryBankAccountTransConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2005
 * Company:             iSoftStone
 * @author             	qijiang 
 * @version
 *  Date of Creation    2005-9-19
 */

public class BankAccountMappingBiz {
	
	private Connection conn = null;
	
	public BankAccountMappingBiz() 
	{
//		try
//		{
//			conn = Database.getConnection();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}
	
	public long saveBankAccountMapping(BankAccountMappingInfo info) throws IException
	{
		long lID = -1;
		try
		{
			Sett_BankAccountMappingDAO dao = new Sett_BankAccountMappingDAO();
			if( info.getID() < 0 )
			{
				lID = dao.add(info);
			}
			else
			{
				dao.update(info);
				lID = info.getID();
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		return lID;
	}
	
	public BankAccountMappingInfo findByID(long lID) throws IException
	{
		BankAccountMappingInfo bi = null;
		try
		{
			Sett_BankAccountMappingDAO dao = new Sett_BankAccountMappingDAO();
			bi = dao.findByID(lID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		return bi != null ? bi : null;
	}
	
	public Collection findByCondition(QueryAccountMappingInfo qfi) throws IException 
	{		
		Collection coll = null; 
		
		try
		{
			Sett_BankAccountMappingDAO dao = new Sett_BankAccountMappingDAO();
			coll = dao.findByCondition(qfi);			
		}
		catch (IException ie)
		{			
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		return coll != null ? coll : null;
	}
	/*
	public void importDataFromBankTrans(QueryAccountMappingInfo qfi) throws IException {
		try {
//			//��ѯ�����˻���ʷ��������
//			Sett_HisTransInfoOfBankAccountDAO hisTransDAO = new Sett_HisTransInfoOfBankAccountDAO(conn);
//			QueryBankAccountTransConditionInfo qInfo = new QueryBankAccountTransConditionInfo();
//			String[] accountNos = {qfi.getBankAccountNo()};
//			qInfo.setBankAccountNos(accountNos);
//			qInfo.setStartDate(qfi.getStartDate());
//			qInfo.setEndDate(qfi.getEndDate());
			
			Sett_BankAccountMappingDAO bankMappingDAO = new Sett_BankAccountMappingDAO(conn);
			
			Collection coll = null;
			coll = bankMappingDAO.getTransInfoForImport(qfi);
			
			//��ѯ���ж�������
			//Sett_BankAccountMappingDAO bankMappingDAO = new Sett_BankAccountMappingDAO(conn);
			Collection collMapping = null;
			collMapping = bankMappingDAO.getTransForCompare(qfi);
			
			
			Sett_CorpAccountMappingDAO corpMappingDAO = new Sett_CorpAccountMappingDAO(conn);
			CorpAccountMappingInfo corpMappingInfo = null;
			
			if(coll!=null && coll.size()>0) { 
				Iterator it = coll.iterator();
				//��������
				while(it.hasNext()) {
					BankAccountTransInfo bankTransInfo = new BankAccountTransInfo();
					bankTransInfo = (BankAccountTransInfo)it.next();
					boolean bIsExist = false;
					if(collMapping != null && collMapping.size() > 0) {
						Iterator itMapping = collMapping.iterator();
						while(itMapping.hasNext()) {
							BankAccountMappingInfo bankMappingInfo = new BankAccountMappingInfo();
							bankMappingInfo = (BankAccountMappingInfo)itMapping.next();
							
							if(bankTransInfo.getID()==bankMappingInfo.getID()) {
								//������˱������и����ݣ���ԭ���ױ��еļ�¼�ǡ���Ĩ�ˡ����򽫶��˱��е����ݱ�Ϊ������
								if(bankTransInfo.getIsDeletedByBank()==Constant.TRUE) {
									bankMappingInfo.setStatusID(SETTConstant.CheckAccountBookStatus.CLEARED);
									bankMappingDAO.update(bankMappingInfo);
									
									//����ö��������ѹ���ʱ�������Ӧ����ҵ���˹�����Ϣɾ�������������н��׶��˱���ƥ�����ݣ��������״̬��ʾΪ��δ�����
									if(bankMappingInfo.getStatusID() == SETTConstant.CheckAccountBookStatus.CHECKED) {
										corpMappingInfo = corpMappingDAO.findByMappingID(bankMappingInfo.getMappingID());
										corpMappingInfo.setStatusID(SETTConstant.CheckAccountBookStatus.UNCHECKED);
										corpMappingDAO.update(corpMappingInfo);
									}
								}
								bIsExist = true;
								break;
							}
						}
					}					
					
					//������������ݣ���ԭ���ױ��еļ�¼���ǡ���Ĩ�ˡ����򽫸ö��˼�¼�Ķ���״̬��ʾΪ��δ�����
					if(!bIsExist) {
						if(bankTransInfo.getIsDeletedByBank()!=Constant.TRUE) {
							BankAccountMappingInfo newBankMappingInfo = new BankAccountMappingInfo();
							newBankMappingInfo.setID(bankTransInfo.getID());
							newBankMappingInfo.setAccountNo(bankTransInfo.getAccountNo());
							newBankMappingInfo.setSubjectCode(qfi.getSubjectCode());
							newBankMappingInfo.setTransDirection(bankTransInfo.getDirection());
							newBankMappingInfo.setTransDate(new Timestamp(bankTransInfo.getTransAction().getTime()));
							newBankMappingInfo.setAmount(bankTransInfo.getAmount());
							newBankMappingInfo.setAbstract(bankTransInfo.getAbstract());
							newBankMappingInfo.setStatusID(SETTConstant.CheckAccountBookStatus.UNCHECKED);
							
							bankMappingDAO.add(newBankMappingInfo);
						}
					}
				}
			}
			
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
	}
	*/
	public BankAccountMappingInfo findByMappingID(long lMappingID) throws IException
	{
		BankAccountMappingInfo bi = null;
		try
		{
			Sett_BankAccountMappingDAO dao = new Sett_BankAccountMappingDAO();
			bi = dao.findByMappingID(lMappingID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		return bi != null ? bi : null;
	}
	
	public BankAccountBalanceInfo findByAccountAndDate(String accountNo, Timestamp date) throws IException {
		BankAccountBalanceInfo info = null;
		try
		{
			Sett_BalanceOfBankAccountDAO dao = new Sett_BalanceOfBankAccountDAO();
			info = dao.findByAccountAndDate(accountNo,new Date(date.getTime()));
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		
		return info != null ? info : null;
	}
	
}
