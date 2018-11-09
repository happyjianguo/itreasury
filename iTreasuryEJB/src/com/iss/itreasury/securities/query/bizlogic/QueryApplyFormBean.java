/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;
import java.sql.Timestamp;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryApplyFormParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author ����
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryApplyFormBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	public final static int OrderBy_NoticeCode = 1; //ҵ��֪ͨ�����
	public final static int OrderBy_ClientName = 2; //ҵ��λ����
	public final static int OrderBy_BusinessTypeName = 3; //ҵ������
	public final static int OrderBy_TransactionTypeName = 4; //��������
	public final static int OrderBy_NoticeInputDate = 5; //ҵ��֪ͨ��¼������
	public final static int OrderBy_TransactionStartDate = 6; //����ɽ���ʼ��
	public final static int OrderBy_TransactionEndDate = 7; //����ɽ�������
	public final static int OrderBy_Accountcode = 8; //�ʽ��˻�
	public final static int OrderBy_NoticeStatusId = 9; //ҵ��֪ͨ��״̬
	public final static int OrderBy_PledgeSecuritiesAmount = 10;//�ÿ���
	public final static int OrderBy_MaturityDate = 11;//������
	/**
	 * ����֤ȯ��ѯ��SQLString���
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryApplyFormParam queryParam)
	{
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		//��Ҫ���������v002��ʾ����Ϣ
		//�������� ҵ��λ���� �ɶ��ʻ����� ҵ������ �������� ����¼������ ����ɽ���ʼ�� ����ɽ������� ���׶�������
		//����Ӫҵ������ �ʽ��˺� �������˾���� ���뵥״̬ ���״̬ ¼����
		sbFrom.append("(     \n");
		//����1��������¼�����ڿ�ʼ��
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//����2��������¼�����ڽ�����
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		
        //����ɽ����ڿ�ʼ�� 
		Timestamp transactionDateStart = queryParam.getTransactionDateStart();
		String strTransactionDateStart = DataFormat.getDateString(transactionDateStart);
		//����ɽ����ڽ�����
		Timestamp transactionDateEnd = queryParam.getTransactionDateEnd();
		String strTransactionDateEnd = DataFormat.getDateString(transactionDateEnd);
		
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
//		//����10���������˾����
//		String[] fundManagerCoIds = queryParam.getFundManagerCoIds();
		//����11��֤ȯ����
		String[] securitiesIds = queryParam.getSecuritiesIds();
		//����12��������״̬
		long statusId = queryParam.getStatusId();
		//����13��¼����
		long inputUserId = queryParam.getInputUserId();
		sbFrom.append("  select   \n");
		sbFrom.append("   SEC_APPLYFORM.ID as NoticeId,--������ID  \n");
		sbFrom.append("   SEC_APPLYFORM.code as NoticeCode,--��������  \n");
		sbFrom.append("   SEC_CLIENT.name as ClientName ,--ҵ��λ����  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--�ɶ��ʻ�����  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName ,--ҵ������  \n");
		sbFrom.append("   ST_Type.businessTypeId,  \n");
		sbFrom.append("   ST_Type.transactionTypeName, --�������� \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONTYPEID ,  \n");
		sbFrom.append("    ST_Type.businessAttributeID,  \n");
		sbFrom.append("   SEC_APPLYFORM.INPUTDATE as NoticeInputDate,--����¼������  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONSTARTDATE  ,--����ɽ���ʼ��  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONENDDATE  ,--����ɽ�������  \n");
		sbFrom.append("   decode(SEC_COUNTERPART.IsBankOfDeposit,1,-1,SEC_COUNTERPART.Id) as CounterPartId,--���׶��� \n");
		sbFrom.append("   decode(SEC_COUNTERPART.IsBankOfDeposit,1,SEC_COUNTERPART.Id,'') as BankOfDepositId,--����Ӫҵ������ \n");
		sbFrom.append("   SEC_ACCOUNT.AccountCode as Accountcode,  --�ʽ��ʺ�  \n");
		sbFrom.append("   SEC_SECURITIES.name as SecuritiesName,  --֤ȯ����  \n");
		sbFrom.append("   SEC_APPLYFORM.StatusID NoticeStatusId,  --������״̬ \n");
		sbFrom.append("   DECODE(SEC_APPLYFORM.StatusID,5,-1,SEC_APPLYFORM.NEXTCHECKUSERID) nextCheckUserId,  --����ˣ���Ϊ����������״̬ \n");
		sbFrom.append("   SEC_APPLYFORM.InputUserID userId  --¼���� \n");
		sbFrom.append("  from \n");
		//�ų� �������еĿ���
		sbFrom.append("   SEC_CLIENT,SEC_TRANSACTIONTYPE,(select * from SEC_COUNTERPART where ISBANK is null or ISBANK = -1)SEC_COUNTERPART, \n");
		sbFrom.append("   SEC_APPLYFORM ,SEC_BUSINESSTYPE, SEC_ACCOUNT , SEC_SECURITIES , SEC_STOCKHOLDERACCOUNT ,\n");
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_CLIENT.id(+) = SEC_APPLYFORM.ClientID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_TRANSACTIONTYPE.id = SEC_APPLYFORM.TransactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_COUNTERPART.id = SEC_APPLYFORM.CounterpartID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id = subStr(SEC_TRANSACTIONTYPE.Id,0,2) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.ACCOUNTID = SEC_ACCOUNT.ID (+)  \n");
		sbFrom.append("   and SEC_APPLYFORM.TransactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.SECURITIESID = SEC_SECURITIES.ID (+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   ST_Type.businessAttributeID in (1,2,3,4)  \n"); //��Ӧ�ŵ�һ��ҵ��
		//		===========�����Ǹ���ҳ�洫�ݵĲ�������������======start====
		//����1��������¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//����2��������¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		
        //�ɽ����ڿ�ʼ��
		if (!"".equals(strTransactionDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.transactionstartdate , 'yyyy-mm-dd') >= " + "'" + strTransactionDateStart + "'");
		}
		//�ɽ����ڽ�����
		if (!"".equals(strTransactionDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.transactionenddate , 'yyyy-mm-dd') <= " + "'" + strTransactionDateEnd + "'");
		}
		
		//����3��ҵ������
		if (businessTypeId > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" ST_Type.businessTypeId = " + businessTypeId + " \n");
		}
		//��������  �����ѯʱ����ѯ9��ҵ��֮���ҵ��
		sbFrom.append(" and ST_Type.businessTypeId not in ( 6,11,12,13,41,42,71,73,75,77,79,81,83,85,87,89,91,93 ) ");
		
		//����4����������
		if (transactionTypeIds != null && transactionTypeIds.length > 0)
		{
			sbFrom.append(" and SEC_APPLYFORM.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//����5��ҵ��λ
		if (clientId != -1)
		{
			sbFrom.append(" and \n SEC_CLIENT.id = " + clientId + " \n");
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
		//����7.1��ֻѡ���׶���
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1 )
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//����7.2��ֻѡ���׶���,����Ӫҵ������
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1 )
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����8.1��ֻѡ����Ӫҵ������
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " + bankOfDepositId + " \n");
		}
		//����9���ʽ��˺�
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and SEC_ACCOUNT.id in ( ");
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
		//����12��������״̬
		if (statusId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.statusId = " + statusId + " \n");
		}
		//����13��¼����
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.inputUserId = " + inputUserId + " \n");
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
			case OrderBy_TransactionStartDate :
				sbOrderBy.append(" \n order by TransactionStartDate" + strDesc);
				break;
			case OrderBy_TransactionEndDate :
				sbOrderBy.append(" \n order by TransactionEndDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
		}
		log.debug("////////////////////////////////////////-");
	}
	/**
	 * ����֤ȯ��ѯ��SQLString���
	 * 
	 * @param queryParam
	 * ˵�����������ڹ�����Ŀ�Ĳ�ѯ---�������ѯ
	 *  @return
	 */
	private void getCNMEFSQL(QueryApplyFormParam queryParam)
	{
		System.out.println("�������������ѯ---getCNMEFSQL()");
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		//��Ҫ���������v002��ʾ����Ϣ
		//�������� ҵ��λ���� �ɶ��ʻ����� ҵ������ �������� ����¼������ ����ɽ���ʼ�� ����ɽ������� ���׶�������
		//����Ӫҵ������ �ʽ��˺� �������˾���� ���뵥״̬ ���״̬ ¼����
		sbFrom.append("(     \n");
		//����1��������¼�����ڿ�ʼ��
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//����2��������¼�����ڽ�����
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
		sbFrom.append("  select   \n");
		sbFrom.append("   SEC_APPLYFORM.ID as NoticeId,--������ID  \n");
		sbFrom.append("   SEC_APPLYFORM.code as NoticeCode,--��������  \n");
		sbFrom.append("   SEC_CLIENT.name as ClientName ,--ҵ��λ����  \n");
		//sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName ,--�ɶ��ʻ�����  \n");
		sbFrom.append("   ST_Type.businessTypeName as BusinessTypeName ,--ҵ������  \n");
		sbFrom.append("   ST_Type.businessTypeId,  \n");
		sbFrom.append("   ST_Type.transactionTypeName, --�������� \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONTYPEID ,  \n");
		sbFrom.append("    ST_Type.businessAttributeID,  \n");
		sbFrom.append("   SEC_APPLYFORM.INPUTDATE as NoticeInputDate,--����¼������  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONSTARTDATE  ,--����ɽ���ʼ��  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONENDDATE  ,--����ɽ�������  \n");
		sbFrom.append("   SEC_APPLYFORM.PledgeSecuritiesAmount  ,--�ÿ���  \n");
		sbFrom.append("   SEC_APPLYFORM.MaturityDate  ,--������  \n");
		sbFrom.append("   SEC_APPLYFORM.OwnerTypeId  ,--���ѡ��  \n");
		sbFrom.append("   SEC_COUNTERPART.name as CounterPartName,--���׶�������,����Ӫҵ������,�������˾���� \n");
		sbFrom.append("   SEC_ACCOUNT.AccountCode as Accountcode,  --�ʽ��ʺ�  \n");
		sbFrom.append("   SEC_SECURITIES.name as SecuritiesName,  --֤ȯ����  \n");
		sbFrom.append("   SEC_APPLYFORM.StatusID NoticeStatusId,  --������״̬ \n");
		sbFrom.append("   SEC_APPLYFORM.NEXTCHECKUSERID nextCheckUserId,  --����ˣ���Ϊ����������״̬ \n");
		sbFrom.append("   SEC_APPLYFORM.InputUserID userId --¼����Id \n");
		sbFrom.append("  from \n");
		sbFrom.append("   SEC_CLIENT , SEC_TRANSACTIONTYPE , SEC_COUNTERPART , SEC_APPLYFORM ,\n");
		sbFrom.append("   SEC_BUSINESSTYPE, SEC_ACCOUNT , SEC_SECURITIES , SEC_STOCKHOLDERACCOUNT ,\n");
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_CLIENT.id(+) = SEC_APPLYFORM.ClientID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_TRANSACTIONTYPE.id = SEC_APPLYFORM.TransactionTypeID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_COUNTERPART.id = SEC_APPLYFORM.CounterpartID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id = subStr(SEC_TRANSACTIONTYPE.Id,0,2) \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.ACCOUNTID = SEC_ACCOUNT.ID (+)  \n");
		sbFrom.append("   and SEC_APPLYFORM.TransactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   SEC_APPLYFORM.SECURITIESID = SEC_SECURITIES.ID (+)  \n");
		sbFrom.append("   and  \n");
		sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n"); //��Ӧ�ŵ�һ��ҵ��
		//		===========�����Ǹ���ҳ�洫�ݵĲ�������������======start====
		//����1��������¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//����2��������¼�����ڿ�ʼ��
		if (!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(" to_char(SEC_APPLYFORM.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
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
			sbFrom.append(" and SEC_APPLYFORM.transactionTypeID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1]) + ") \n");
		}
		//����5��ҵ��λ
		if (clientId != -1)
		{
			sbFrom.append(" and \n SEC_CLIENT.id = " + clientId + " \n");
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
		//����7.1��ֻѡ���׶���
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(interBankCounterPartIds[interBankCounterPartIds.length - 1]) + ") \n");
		}
		//����7.2��ֻѡ���׶���,����Ӫҵ������
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < interBankCounterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����7.3��ֻѡ���׶���,�������˾����
		if (interBankCounterPartIds != null && interBankCounterPartIds.length > 0 && bankOfDepositId == -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
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
			sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " + bankOfDepositId + " \n");
		}
		//����8.2��ֻѡ����Ӫҵ������,�������˾����
		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			//sbFrom.append(" and \n SEC_APPLYFORM.counterPartId = " +
			// bankOfDepositId + " \n");
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����10.1��ֻѡ�������˾����
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1 && (interBankCounterPartIds == null || interBankCounterPartIds.length == 0))
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//����10.2����ѡ
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId != -1 && interBankCounterPartIds != null && interBankCounterPartIds.length > 0)
		{
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
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
			sbFrom.append(" and SEC_ACCOUNT.id in ( ");
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
		//����12��������״̬
		if (statusId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.statusId = " + statusId + " \n");
		}
		//����13��¼����
		if (inputUserId != -1)
		{
			sbFrom.append(" and \n SEC_APPLYFORM.inputUserId = " + inputUserId + " \n");
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
			case OrderBy_TransactionStartDate :
				sbOrderBy.append(" \n order by TransactionStartDate" + strDesc);
				break;
			case OrderBy_TransactionEndDate :
				sbOrderBy.append(" \n order by TransactionEndDate" + strDesc);
				break;
			case OrderBy_Accountcode :
				sbOrderBy.append(" \n order by Accountcode" + strDesc);
				break;
			case OrderBy_NoticeStatusId :
				sbOrderBy.append(" \n order by NoticeStatusId" + strDesc);
				break;
			case OrderBy_PledgeSecuritiesAmount :
				sbOrderBy.append(" \n order by PledgeSecuritiesAmount" + strDesc);
				break;
			case OrderBy_MaturityDate :
				sbOrderBy.append(" \n order by MaturityDate" + strDesc);
				break;
		}
		log.debug("////////////////////////////////////////-");
	}
	/**
	 * ����PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryApplyFormInfo(QueryApplyFormParam queryParam) throws SecuritiesException
	{
		/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
		{
			System.out.println("��Ŀ���ƣ�����;ģ�飺�������ѯ");
			getCNMEFSQL(queryParam);
		}
		else
		{*/
			System.out.println("��Ŀ���ƣ�--;ģ�飺�������ѯ");
			getSQL(queryParam);
		//}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QueryApplyFormInfo",
			null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}
}
