/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryNoticeFormParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Env;

/**
 * @author hjliu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryNoticeFormBean {

	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;

	public final static int OrderBy_NoticeCode = 1; //ҵ��֪ͨ�����
	public final static int OrderBy_DeliveryOrderCode = 2; //��Ӧ������
	public final static int OrderBy_ClientName = 3; //ҵ��λ����
	public final static int OrderBy_BusinessTypeName = 4; //ҵ������
	public final static int OrderBy_TransactionTypeName = 5; //��������
	public final static int OrderBy_NoticeInputDate = 6; //ҵ��֪ͨ��¼������
	public final static int OrderBy_Accountcode = 7; //�ʽ��˻�
	public final static int OrderBy_NetIncome = 8; //ʵ���ո�
	public final static int OrderBy_NoticeStatusId = 9; //ҵ��֪ͨ��״̬

	/**
	 * ����֤ȯ��ѯ��SQLString���
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryNoticeFormParam queryParam)
	{
		sbSelect = new StringBuffer();
		//////////////select//////////////start//////////////////////-
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("(     \n");
		//����1��ҵ��֪ͨ��¼�����ڿ�ʼ�� 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//����2��ҵ��֪ͨ��¼�����ڽ�����
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		//����3��ҵ������
		long businessTypeId = queryParam.getBusinessTypeId();
		//����4����������
		String[] transactionTypeIds = queryParam.getTransactionTypeIds();
		//����5��ҵ��λ
		long clientId = queryParam.getClientId();
		//����6���ɶ��ʻ�
		String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();
		//����7�����׶���
		String[] counterPartIds = queryParam.getCounterPartIds();
		//����8������Ӫҵ������
		long bankOfDepositId = queryParam.getBankOfDepositId();
		//����9���ʽ��˺�
		String[] accountIds = queryParam.getAccountIds();
		//����10���������˾����---��ѯ�����ȥ��������
		//String[] fundManagerCoIds = queryParam.getFundManagerCoIds();
		//����11��֤ȯ���� 
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//����12��������״̬
		long statusId = queryParam.getStatusId();
		//����13��¼����
		long inputUserId = queryParam.getInputUserId();
		//����14��ҵ��֪ͨ���ո�������
		Timestamp noticeExecuteDateStart = queryParam.getNoticeExecuteDateStart();
		String strNoticeExecuteDateStart = DataFormat.getDateString(noticeExecuteDateStart);
		
		Timestamp noticeExecuteDateEnd = queryParam.getNoticeExecuteDateEnd();
		String strNoticeExecuteDateEnd = DataFormat.getDateString(noticeExecuteDateEnd);
		//���뵥id
		//�����뵥����ϸҳ��������ʾ֪ͨ����Ϣ������֪ͨ����ѯר��
		long applyFormId = queryParam.getApplyFormId();
		//��ͬid
		//�ɺ�ͬ����ϸҳ������ҵ��֪ͨ����Ϣ������֪ͨ����ѯר��
		long contractID = queryParam.getContractID(); 	
		sbFrom.append("  select   \n");
		sbFrom.append("   notice.ID as NoticeId,  --ҵ��֪ͨ�� \n");
		sbFrom.append("   delivery.ID as DeliveryOrderID,  --��Ӧ��� \n");
		sbFrom.append("   delivery.ISRELATEDBYNOTICEFORM as isRelatedByNoticeForm ,--�Ƿ����⽻� \n");		
		sbFrom.append("   SEC_ApplyContract.ID as contractID,--��Ӧ��ͬid \n");
		sbFrom.append("   notice.code as NoticeCode,  --ҵ��֪ͨ����� \n");
		sbFrom.append("   delivery.Code as DeliveryOrderCode,  --��Ӧ������ \n");
		sbFrom.append("   client.name as ClientName,  --ҵ��λ���� \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--�ɶ��ʻ�����  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName,  --ҵ������ \n");
		sbFrom.append("   ST_Type.businessTypeId, --ҵ������ID \n");
		sbFrom.append("   ST_Type.transactionTypeName, --�������� \n");
		sbFrom.append("   delivery.transactionTypeID , --��������Id \n");
		sbFrom.append("   ST_Type.businessAttributeID, --ҵ������ID(3������ʽ����ҵ��2:������ҵ��1:���м�ҵ��) \n");
		sbFrom.append("   notice.inputDate as NoticeInputDate, --ҵ��֪ͨ��¼������ \n");
		sbFrom.append("   delivery.TransactionDate as TransactionDate, --ҵ��֪ͨ���ɽ����� \n");
		sbFrom.append("   decode(counterPart.IsBankOfDeposit,1,-1,counterPart.Id) as CounterPartId, -- ���׶��� \n");
		sbFrom.append("   decode(counterPart.IsBankOfDeposit,1,counterPart.Id,'') as BankOfDepositId, -- ����Ӫҵ�� \n");
	    sbFrom.append("   account.AccountCode as Accountcode, --�ʽ��˻� \n");
		sbFrom.append("   securities.name as SecuritiesName, --֤ȯ���� \n");
		sbFrom.append("   delivery.netIncome as NetIncome, --ʵ���ո� \n");
		sbFrom.append("   notice.StatusID as NoticeStatusId, --ҵ��֪ͨ��״̬ \n");
		sbFrom.append("   notice.NEXTCHECKUSERID nextCheckUserId,  --����ˣ���Ϊ����������״̬ \n");
		sbFrom.append("   notice.InputUserID as userId  \n");
		//sbFrom.append("   userInfo.sName as UserName --¼���� \n");
		//---------------------//ҵ��λID
		//sbFrom.append(" delivery.clientID as ClientId, \n");
		//---------------------//���׶���ID		
		//sbFrom.append(" delivery.counterPartId as CounterPartId, \n");
		//---------------------//�˻�ID
		//sbFrom.append(" delivery.accountID as AccountId, \n");
		//---------------------//֤ȯID
		//sbFrom.append(" delivery.securitiesID as SecuritiesID \n");
		sbFrom.append("  from \n");
		//---------------------//��ҵ��֪ͨ��
		sbFrom.append("   sec_noticeForm notice ,SEC_ApplyContract, \n");
		//---------------------//�����
		sbFrom.append("   sec_deliveryOrder delivery, \n");
		//---------------------//��ҵ��λ
		sbFrom.append("   sec_client client, SEC_STOCKHOLDERACCOUNT ,\n");
		//---------------------//�����һ��
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		//---------------------//���������ͣ�ҵ�����ͣ�ҵ������
		sbFrom.append("    from  sec_transactionType a ,sec_businessType b,sec_businessAttribute c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type,  \n");
		//---------------------//���ʽ��˻�
		sbFrom.append("    sec_Account account, \n");
		//---------------------//��֤ȯ��Ϣ
		sbFrom.append("    sec_Securities securities, \n");
		//---------------------//���û���Ϣ
		sbFrom.append("    USERINFO userInfo , \n");
		//---------------------//�����׶���
		sbFrom.append("    (SELECT * FROM Sec_CounterPArt WHERE IsBank is null or IsBank = -1)counterPart \n");

		sbFrom.append("   where 1=1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_ApplyContract.ID(+) = notice.ContractID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.deliveryOrderID = delivery.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.clientId = client.ID(+) \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.transactionTypeID = notice.transactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.accountID = account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   delivery.securitiesID = securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   notice.inputUserID = userInfo.id  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   --2004-09-06 ���������ӣ���Ϣ�޽��׶���ID  \n");
		sbFrom.append("   delivery.counterPartID = counterPart.id(+)  \n");
		//sbFrom.append("   and  \n");
		//sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n");
		//����1��ҵ��֪ͨ��¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//����2��ҵ��֪ͨ��¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		
		//����3��ҵ������
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" ST_Type.businessTypeId = " + businessTypeId + " \n");
		}
		//��������  ҵ��֪ͨ����ѯʱ��Ҫ��ѯ��ҵ��
		sbFrom.append(" and ST_Type.businessTypeId in ( 17,18,21,22,26,31,32,33,36,37,46,52,57,61,62,63,64,85 ) ");
		
		//����4����������
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and notice.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//����5��ҵ��λ
		if (clientId != -1)
		{
			sbFrom.append(" and \n client.id = " + clientId + " \n");
		}
		//����6���ɶ��ʻ�
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0)
		{
			sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.CLIENTID in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
		}
		//			����7.1��ֻѡ���׶���
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//����7.2��ֻѡ���׶���,����Ӫҵ������
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����8.1��ֻѡ����Ӫҵ������
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n delivery.counterPartId = " + bankOfDepositId + " \n");
		}
		
		//����9���ʽ��˺�
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and account.id in ( ");
			for (int i = 0; i < accountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//����11��֤ȯ����
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and SECURITIES.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//����12��ҵ��֪ͨ��״̬
		if (statusId != -1)
		{
			sbFrom.append(" and \n notice.statusId = " + statusId + " \n");
		}
		//����13��¼����
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n notice.inputUserId = " + inputUserId + " \n");
		}
		//����14��ҵ��֪ͨ���ո�������
		if (!"".equals(strNoticeExecuteDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') >= " + "'" + strNoticeExecuteDateStart + "'");
		}
		if (!"".equals(strNoticeExecuteDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') <= " + "'" + strNoticeExecuteDateEnd + "'");
		}
		//���뵥id
		//�����뵥����ϸҳ��������ʾ֪ͨ����Ϣ������֪ͨ����ѯר��
		if(applyFormId != -1)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" notice.DeliveryOrderID in (select SEC_DELIVERYORDER.id from SEC_DELIVERYORDER, ");
			sbFrom.append(" SEC_APPLYFORM where SEC_APPLYFORM.id = SEC_DELIVERYORDER.APPLYFORMID ");
			sbFrom.append(" and SEC_APPLYFORM.id = " + applyFormId + ")");
		}
		//��ͬid
		//�ɺ�ͬ����ϸҳ������ҵ��֪ͨ����Ϣ������֪ͨ����ѯר��
		if(contractID != -1)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" SEC_ApplyContract.ID = " + contractID );
		}
		//sbFromƴд��������������������������	
		sbFrom.append("   )  \n");
		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();
		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");
		//////////////where//////////////-end////////////////////////-
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		//log.debug( "////queryParam.getDesc() //////////-" +
		// queryParam.getDesc());
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_NoticeCode :
				sbOrderBy.append(" \n order by NoticeCode" + strDesc);
				break;
			case OrderBy_DeliveryOrderCode :
				sbOrderBy.append(" \n order by DeliveryOrderCode" + strDesc);
				break;
			case OrderBy_ClientName :
				sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BusinessTypeName :
				sbOrderBy.append(" \n order by BusinessTypeName" + strDesc);
				break;
			case OrderBy_TransactionTypeName :
				sbOrderBy.append(" \n order by TransactionTypeName" + strDesc);
				break;
			case OrderBy_NoticeInputDate :
				sbOrderBy.append(" \n order by NoticeInputDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NetIncome :
				sbOrderBy.append(" \n order by NetIncome" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
		}
		log.info("////////////////////////////////////////-");
	}
	/**
	 * ����֤ȯ��ѯ��SQLString���
	 * 
	 * @param queryParam
	 * @return
	 * ˵���������ڹ�����Ŀ�Ĳ�ѯ---ҵ��֪ͨ����ѯ
	 */
	private void getCNMEFSQL(QueryNoticeFormParam queryParam)
	{
		System.out.println("������ҵ��֪ͨ����ѯ---getCNMEFSQL()");
		sbSelect = new StringBuffer();
		//////////////select//////////////start//////////////////////-
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("(     \n");
		//����1��ҵ��֪ͨ��¼�����ڿ�ʼ�� 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//����2��ҵ��֪ͨ��¼�����ڽ�����
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		//����3��ҵ������
		long businessTypeId = queryParam.getBusinessTypeId();
		//����4����������
		String[] transactionTypeIds = queryParam.getTransactionTypeIds();
		//����5��ҵ��λ
		long clientId = queryParam.getClientId();
		//����6���ɶ��ʻ�
		//String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();
		//����7�����׶���
		String[] interBankCounterPartIds = queryParam.getInterBankCounterPartIds();
		//����8������Ӫҵ������
		long bankOfDepositId = queryParam.getBankOfDepositId();
		//����9���ʽ��˺�
		String[] accountIds = queryParam.getAccountIds();
		//����10���������˾����
		String[] fundManagerCoIds = queryParam.getFundManagerCoIds();
		//����11��֤ȯ���� 
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//����12��������״̬
		long statusId = queryParam.getStatusId();
		//����13��¼����
		long inputUserId = queryParam.getInputUserId();
		//����14��ҵ��֪ͨ���ո���������
		Timestamp noticeExecuteDateStart = queryParam.getNoticeExecuteDateStart();
		String strNoticeExecuteDateStart = DataFormat.getDateString(noticeExecuteDateStart);
        //����15��ҵ��֪ͨ���ո�������ʼ
		Timestamp noticeExecuteDateEnd = queryParam.getNoticeExecuteDateEnd();
		String strNoticeExecuteDateEnd = DataFormat.getDateString(noticeExecuteDateEnd);
		
		sbFrom.append("  select   \n");
		sbFrom.append("   notice.ID as NoticeId,  --ҵ��֪ͨ�� \n");
		sbFrom.append("   delivery.ID as DeliveryOrderID,  --��Ӧ��� \n");
		sbFrom.append("   notice.code as NoticeCode,  --ҵ��֪ͨ����� \n");
		sbFrom.append("   delivery.Code as DeliveryOrderCode,  --��Ӧ������ \n");
		sbFrom.append("   client.name as ClientName,  --ҵ��λ���� \n");
		//sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--�ɶ��ʻ�����  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName,  --ҵ������ \n");
		sbFrom.append("   ST_Type.businessTypeId, --ҵ������ID \n");
		sbFrom.append("   ST_Type.transactionTypeName, --�������� \n");
		sbFrom.append("   delivery.transactionTypeID , --��������Id \n");
		sbFrom.append("    ST_Type.businessAttributeID, --ҵ������ID(3������ʽ����ҵ��2:������ҵ��1:���м�ҵ��) \n");
		sbFrom.append("   notice.inputDate as NoticeInputDate, --ҵ��֪ͨ��¼������ \n");
		sbFrom.append("   delivery.TransactionDate as TransactionDate, --ҵ��֪ͨ���ɽ����� \n");
		sbFrom.append("   counterPart.name as CounterPartName,  --���׶������ƣ�����ҵ������Id�Ĳ�ͬ����ͬ�� \n");
		sbFrom.append("   account.AccountCode as Accountcode, --�ʽ��˻� \n");
		sbFrom.append("   securities.name as SecuritiesName, --֤ȯ���� \n");
		sbFrom.append("   delivery.netIncome as NetIncome, --ʵ���ո� \n");
		sbFrom.append("   notice.StatusID as NoticeStatusId, --ҵ��֪ͨ��״̬ \n");
		sbFrom.append("   notice.NEXTCHECKUSERID nextCheckUserId,  --����ˣ���Ϊ����������״̬ \n");
		sbFrom.append("   userInfo.Id UserId, --¼����Id \n");
		sbFrom.append("   userInfo.sName as UserName --¼���� \n");
		//---------------------//ҵ��λID
		//sbFrom.append(" delivery.clientID as ClientId, \n");
		//---------------------//���׶���ID		
		//sbFrom.append(" delivery.counterPartId as CounterPartId, \n");
		//---------------------//�˻�ID
		//sbFrom.append(" delivery.accountID as AccountId, \n");
		//---------------------//֤ȯID
		//sbFrom.append(" delivery.securitiesID as SecuritiesID \n");
		sbFrom.append("  from \n");
		//---------------------//��ҵ��֪ͨ��
		sbFrom.append("   sec_noticeForm notice , \n");
		//---------------------//�����
		sbFrom.append("   sec_deliveryOrder delivery, \n");
		//---------------------//��ҵ��λ
		sbFrom.append("   sec_client client, SEC_STOCKHOLDERACCOUNT ,\n");
		//---------------------//�����һ��
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		//---------------------//���������ͣ�ҵ�����ͣ�ҵ������
		sbFrom.append("    from  sec_transactionType a ,sec_businessType b,sec_businessAttribute c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type,  \n");
		//---------------------//���ʽ��˻�
		sbFrom.append("    sec_Account account, \n");
		//---------------------//��֤ȯ��Ϣ
		sbFrom.append("    sec_Securities securities, \n");
		//---------------------//���û���Ϣ
		sbFrom.append("    USERINFO userInfo , \n");
		//---------------------//�����׶���
		sbFrom.append("    Sec_CounterPArt counterPart \n");

		sbFrom.append("   where 1=1 and \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.deliveryOrderID = delivery.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.clientId = client.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   notice.transactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.transactionTypeID = notice.transactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   delivery.accountID = account.ID(+) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   delivery.securitiesID = securities.ID(+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   notice.inputUserID = userInfo.id  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   delivery.counterPartID = counterPart.id  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n");
		//����1��ҵ��֪ͨ��¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//����2��ҵ��֪ͨ��¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		//����3��ҵ������
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" ST_Type.businessTypeId = " + businessTypeId + " \n");
		}
		//����4����������
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and notice.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//����5��ҵ��λ
		if (clientId != -1)
		{
			sbFrom.append(" and \n client.id = " + clientId + " \n");
		}
		/**
		//����6���ɶ��ʻ�
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0)
		{
			sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.CLIENTID in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1]) + ") \n");
		}
		**/
		// ����7.1��ֻѡ���׶���
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(interBankCounterPartIds[interBankCounterPartIds.length - 1]) + ") \n");
		}
		//����7.2��ֻѡ���׶���,����Ӫҵ������
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����7.3��ֻѡ���׶���,�������˾����
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//����8.1��ֻѡ����Ӫҵ������
		if (bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0) && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			sbFrom.append(" and \n delivery.counterPartId = " + bankOfDepositId + " \n");
		}
		//����8.2��ֻѡ����Ӫҵ������,�������˾����
		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			//sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " +
			// bankOfDepositId + " \n");
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����10.1��ֻѡ�������˾����
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//����10.2����ѡ
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId != -1 && interBankCounterPartIds != null && interBankCounterPartIds.length > 0)
		{
			sbFrom.append(" and delivery.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����9���ʽ��˺�
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and account.id in ( ");
			for (int i = 0; i < accountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//����11��֤ȯ����
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and SEC_SECURITIES.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//����12��ҵ��֪ͨ��״̬
		if (statusId != -1)
		{
			sbFrom.append(" and \n notice.statusId = " + statusId + " \n");
		}
		//����13��¼����
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n notice.inputUserId = " + inputUserId + " \n");
		}
		//����14��ҵ��֪ͨ���ո�������
		if (!"".equals(strNoticeExecuteDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') >= " + "'" + strNoticeExecuteDateStart + "'");
		}
		if (!"".equals(strNoticeExecuteDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(notice.ExecuteDate , 'yyyy-mm-dd') <= " + "'" + strNoticeExecuteDateEnd + "'");
		}
		//sbFromƴд��������������������������	
		sbFrom.append("   )  \n");
		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();
		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");
		//////////////where//////////////-end////////////////////////-
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		//log.debug( "////queryParam.getDesc() //////////-" +
		// queryParam.getDesc());
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_NoticeCode :
				sbOrderBy.append(" \n order by NoticeCode" + strDesc);
				break;
			case OrderBy_DeliveryOrderCode :
				sbOrderBy.append(" \n order by DeliveryOrderCode" + strDesc);
				break;
			case OrderBy_ClientName :
				sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BusinessTypeName :
				sbOrderBy.append(" \n order by BusinessTypeName" + strDesc);
				break;
			case OrderBy_TransactionTypeName :
				sbOrderBy.append(" \n order by TransactionTypeName" + strDesc);
				break;
			case OrderBy_NoticeInputDate :
				sbOrderBy.append(" \n order by NoticeInputDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NetIncome :
				sbOrderBy.append(" \n order by NetIncome" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
		}
		log.info("////////////////////////////////////////-");
	}

	/**
	 * ����PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryNoticeFormInfo(QueryNoticeFormParam queryParam)
		throws SecuritiesException {

		//if( Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF) )
		//{
		//	System.out.println("��Ŀ���ƣ�����;ģ�飺ҵ��֪ͨ����ѯ");
		//	getCNMEFSQL(queryParam);
		//}
		//else
		//{
			System.out.println("��Ŀ���ƣ�--;ģ�飺ҵ��֪ͨ����ѯ");
			getSQL(queryParam);
		//}
		//
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QueryNoticeFormInfo",
			null);

		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}

}
