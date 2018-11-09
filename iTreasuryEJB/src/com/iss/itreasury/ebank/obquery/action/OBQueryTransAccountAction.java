package com.iss.itreasury.ebank.obquery.action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.ebank.obquery.bizlogic.OBQueryTransAccountBiz;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class OBQueryTransAccountAction {
	
	OBQueryTransAccountBiz oBQueryTransAccountBiz = new OBQueryTransAccountBiz();
	
	public PagerInfo queryTransAccountDetail(Map map) throws Exception{
		PagerInfo pagerInfo = null;
		try
		{
			QueryTransAccountDetailWhereInfo qInfo = new QueryTransAccountDetailWhereInfo();
			//�������
			String strClientCode = "";//�ͻ���
			String strAccountNo = "";//�˺�
			long lAccountID = -1;//�˻�ID
			long lAccountTypeID = -1;//�˻�����ID
			long lAccountGroupID = -1;//�˻���ID
			String strContractCode = "";//��ͬ��
			long lContractID = -1;//��ͬID
			String strLoanNoteNo = "";//�ſ��
			long lLoanNoteID = -1;//�ſID
			String strFixedDepositNo = "";//���ڴ��ݺ�
			long lSubAccountID = -1;//���ڴ��ݶ�Ӧ�����˻�ID
			Timestamp tsStartDate = null;//��ʼ����
			String strStartDate = "";
			String strEndDate = "";
			Timestamp tsEndDate = null;//��������
			double dBalance = 0.0;//�ڳ����
			long lOfficeID = -1;
			long lCurrencyID = -1;
			String sClientCode = ""; 
			
			//��ȡ����
			if(map.get("laccountid").toString()!=null && map.get("laccountid").toString().trim().length()>0)
			{
				lAccountID = Long.parseLong(map.get("laccountid").toString().trim()); //�˻�ID
			}
			if(map.get("laccounttypeid").toString()!=null && map.get("laccounttypeid").toString().trim().length()>0)
			{
				lAccountTypeID = Long.parseLong(map.get("laccounttypeid").toString().trim()); //�˻�ID
			}
			if(map.get("laccountgroupid").toString()!=null && map.get("laccountgroupid").toString().trim().length()>0)
			{
				lAccountGroupID = Long.parseLong(map.get("laccountgroupid").toString().trim()); //�˻�ID
			}
			if(map.get("tsstart").toString()!=null && map.get("tsstart").toString().trim().length()>0)
			{
				strStartDate = map.get("tsstart").toString().trim();
				tsStartDate = DataFormat.getDateTime(strStartDate);  //��ʼ����
			}
			if(map.get("tsend").toString()!=null && map.get("tsend").toString().trim().length()>0)
			{
				strEndDate = map.get("tsend").toString().trim();
				tsEndDate = DataFormat.getDateTime(strEndDate);  //��ʼ����
			}
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
					||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
					||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
					||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) 
					&& map.get("strcontractcodectrl").toString()!=null 
					&& map.get("strcontractcodectrl").toString().trim().length()>0)
			{
				strContractCode = map.get("strcontractcodectrl").toString().trim(); //��ͬ��
			}
			
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
				&& map.get("strcontractcode").toString()!=null 
				&& map.get("strcontractcode").toString().trim().length()>0)
			{
				lContractID = Long.parseLong(map.get("strcontractcode").toString().trim()); //��ͬID
			}
			
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
				&& map.get("strloannotenoctrl").toString()!=null 
				&& map.get("strloannotenoctrl").toString().trim().length()>0)
			{
				strLoanNoteNo = map.get("strloannotenoctrl").toString().trim(); //�ſ�֪ͨ����
			}
			if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
				||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
				||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
				||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN)
				&& map.get("strloannoteno").toString()!=null 
				&& map.get("strloannoteno").toString().trim().length()>0 )
			{
				lLoanNoteID = Long.parseLong(map.get("strloannoteno").toString().trim());  //�ſ�֪ͨ��ID
			}
			
			if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED 
					|| lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) 
					&& map.get("strfixeddepositnoctrl")!=null 
					&& map.get("strfixeddepositnoctrl").toString().trim().length()>0)
			{
				if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
				{
					strFixedDepositNo = map.get("strfixeddepositnoctrl").toString().trim(); //���ڴ��ݺ�
				}
				else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
				{
					strFixedDepositNo = map.get("strnotifydepositnoctrl").toString().trim(); //֪ͨ���ݺ�
				}
			}

			if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED || 
				lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) 
				&& map.get("strfixeddepositnoctrl")!=null 
				&& map.get("strfixeddepositnoctrl").toString().trim().length()>0)
			{
				if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
				{		
					lSubAccountID = Long.parseLong(map.get("strfixeddepositno").toString().trim());//���˻�ID
				}
				else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
				{
					lSubAccountID = Long.parseLong(map.get("strnotifydepositno").toString().trim());  //���˻�ID
				}
			}
			if(map.get("lofficeid").toString()!=null && map.get("lofficeid").toString().trim().length()>0)
			{
				lOfficeID = Long.parseLong(map.get("lofficeid").toString().trim()); //�˻�ID
			}
			if(map.get("lcurrencyid").toString()!=null && map.get("lcurrencyid").toString().trim().length()>0)
			{
				lCurrencyID = Long.parseLong(map.get("lcurrencyid").toString().trim()); //�˻�ID
			}
			if(map.get("sclientcode").toString()!=null && map.get("sclientcode").toString().trim().length()>0)
			{
				sClientCode = map.get("sclientcode").toString().trim(); //�˻�ID
			}
			//qInfo.convertMapToDataEntity(map);//��Mapת��ΪINFO
			
			
			//��ʼ�������ò�ѯ������
	        QueryTransAccountDetailWhereInfo qtai = new QueryTransAccountDetailWhereInfo();
			QTransAccountDetail qobj = new QTransAccountDetail();
			
			
		    qInfo.setOfficeID(lOfficeID);
			qInfo.setCurrencyID(lCurrencyID);
			qInfo.setClientCode(sClientCode);//�ͻ���
			qInfo.setAccountNo(strAccountNo);//�˺�
			qInfo.setAccountID(lAccountID);//�˻�ID
			qInfo.setAccountTypeID(lAccountTypeID);//�˻�����ID
			qInfo.setContractCode(strContractCode);//��ͬ��
			qInfo.setContractID(lContractID);//��ͬID
			qInfo.setLoanNoteNo(strLoanNoteNo);//�ſ��
			qInfo.setLoanNoteID(lLoanNoteID);//�ſID
			qInfo.setFixedDepositNo(strFixedDepositNo);//���ڴ��ݺ�
			qInfo.setSubAccountID(lSubAccountID);//���ڴ��Ŷ�Ӧ�����˻�ID
			qInfo.setStartDate(tsStartDate);
			qInfo.setEndDate(tsEndDate);
			//qInfo.setDesc(1);
			qInfo.setOrderField(1);
			
			pagerInfo = oBQueryTransAccountBiz.queryTransAccountDetail(qInfo,lAccountGroupID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}
