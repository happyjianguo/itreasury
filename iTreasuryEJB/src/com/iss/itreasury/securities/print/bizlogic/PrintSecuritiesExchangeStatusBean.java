/*
 * �������� 2004-5-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.iss.itreasury.securities.print.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesExchangeStatusParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author wangyi
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class PrintSecuritiesExchangeStatusBean {

	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;

	/**
	 * ����֤ȯ��ѯ��SQLString���
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(PrintSecuritiesExchangeStatusParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
  		
		
		//*** ֤ȯ�˵�id:����ƴ��һ��SQL ***
		long securitiesMenuId = queryParam.getSecuritiesMenuId();

		//����1�����ڿ�ʼ�� 
		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart = DataFormat.getDateString(noticeInputDateStart);
		//����2�����ڽ����� 
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd = DataFormat.getDateString(noticeInputDateEnd);
		
		//�������깺���¼������
		Timestamp deliveryOrderInputDateStart = queryParam.getDeliveryOrderInputDateStart();
		String strDeliveryOrderInputDateStart = DataFormat.getDateString(deliveryOrderInputDateStart);
		
		Timestamp deliveryOrderInputDateEnd = queryParam.getDeliveryOrderInputDateEnd();
		String strDeliveryOrderInputDateEnd = DataFormat.getDateString(deliveryOrderInputDateEnd);

		//����3��ҵ������ID
		long businessTypeId              = queryParam.getBusinessTypeId();		
		//����4����������ID
		String[] transactionTypeIds     = queryParam.getTransactionTypeIds();		
		//����5��ҵ��λ
		long clientId = queryParam.getClientId();
		//����6���ɶ��ʻ�
		String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();		
		//����7�����׶���id
		long counterPartId = queryParam.getCounterPartId();		
		//����8������Ӫҵ��
		long bankOfDepositId = queryParam.getBourseCounterPartId();
		//����9���ʽ��˺�
		String[] accountIds = queryParam.getAccountIds();
		//����10��֤ȯ����
		String[] securitiesIds = queryParam.getSecuritiesIds();
		
		if(securitiesMenuId == 1)//����1���ʽ���ҵ��
		{
				sbFrom.append("  select   \n");									
				sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
				sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--���׶���(ID)  \n");
				sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE VALUEDATE,--��Ϣ��  \n");
				sbFrom.append("   SEC_DELIVERYORDER.MATURITYDATE,--������ \n");
				sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--�����  \n");
				sbFrom.append("   SEC_DELIVERYORDER.RATE,--�������  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TERM,--�������  \n");
				
				sbFrom.append("   SEC_DELIVERYORDER.SUSPENSEINTEREST,--Ӧ����Ϣ  \n");
				sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--���ڻ�����  \n");
				sbFrom.append("   SEC_DELIVERYORDER.INTEREST,--ʵ����Ϣ  \n");
				sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
				sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--ʵ���ո�  \n");
				sbFrom.append("   SEC_DELIVERYORDER.STATUSID--״̬  \n");
				sbFrom.append("   from \n");
				sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART\n");
				sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
				sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
				sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
				sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
				
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (13)  \n");
				
				getRemainderStrOnetoEight(
					strNoticeInputDateStart,
					strNoticeInputDateEnd,
					strDeliveryOrderInputDateStart,
					strDeliveryOrderInputDateEnd,
					businessTypeId,
					transactionTypeIds,
					clientId,
					stockHolderAccountIds,
					counterPartId,
					bankOfDepositId,
					accountIds,
					securitiesIds); 
				sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		
		if(securitiesMenuId >= 2 && securitiesMenuId <= 6)//����2����Ʊ/���ʽ����/��ҵծ/��תծ/����ҵ��
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--�ɶ��ʻ�����  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--���׶���(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--����Ӫҵ������  \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--�ʽ��ʺ�(ID) \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--֤ȯ(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--�ɽ��۸�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--�ɽ�����  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--�ɽ����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.RATE,--Ԥ���������  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--Ԥ������  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--ʵ���ո�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--��λ�ɱ�  \n");
			//�����ʽ𻮲���ҵ��85����״̬Ӧ��ȡ��֪ͨ����״̬��Ϣ
			sbFrom.append("   decode(SEC_TRANSACTIONTYPE.BUSINESSTYPEID,85,SEC_NOTICEFORM.STATUSID,SEC_DELIVERYORDER.STATUSID) as STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART,SEC_NOTICEFORM \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.DELIVERYORDERID(+) =  SEC_DELIVERYORDER.id \n");
			//�ڱ���2����ȷ��ִ����һ��ҵ���SQL
			if(securitiesMenuId == 2)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (16,17,18)  \n");
			}
			if(securitiesMenuId == 3)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (56,57,58)  \n");
			}
			if(securitiesMenuId == 4)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (46,47)  \n");
			}
			if(securitiesMenuId == 5)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (51,52,53)  \n");
			}
			if(securitiesMenuId == 6)//���ѡ����� ����ҵ��
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (85,87,91)  \n");
			}
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 7)//����3��ծת��
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--�ɶ��ʻ�����  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--���׶���(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--����Ӫҵ������  \n");			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--�ʽ��ʺ�(ID) \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--��תծ����(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.OPPOSITESECURITIESID,--ת�ɹ�Ʊ����(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--ת�ɼ۸�  \n");				
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--ת�ɵ�ծȯ����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.OPPOSITEQUANTITY,--ת�ɵĹ�Ʊ����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--ת�ɽ��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--����һ�ɵ�ʣ������  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (54)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 8)//����4����������ծ
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--�ɶ��ʻ�����  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--���׶���(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--����Ӫҵ������  \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--�ʽ��ʺ�(ID) \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--֤ȯ(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICE,--����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--ȫ��  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.SUSPENSEINTEREST,--Ӧ����Ϣ  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--�ɽ����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--ʵ���ո�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--��λ�ɱ�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (33,34)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 9)//����5��������ծȯ�ع�
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
			sbFrom.append("   SEC_STOCKHOLDERACCOUNT.id   STOCKHOLDERACCOUNTID,--�ɶ��ʻ�����  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,'') COUNTERPARTID,--���׶���(ID)  \n");
			sbFrom.append("   decode(SEC_COUNTERPART.ISBANKOFDEPOSIT,1,SEC_COUNTERPART.ID)  BANKOFDEPOSITID,--����Ӫҵ������  \n");
			
			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--�ʽ��ʺ�(ID) \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--֤ȯ(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYDATE,--��������  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--�ɽ����  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESQUANTITY,--��Ѻ��׼ȯ  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--�ɽ��۸�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--���ؽ��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--ʵ���ո�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (27)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId >= 10 && securitiesMenuId <= 13)//����6�����м��ծ/����Ʊ��/����ծ/�����Խ���ծ
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--���׶���(ID)  \n");

			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--֤ȯ(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE,--������  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PRICE,--ȫ��  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.SUSPENSEINTEREST,--Ӧ����Ϣ  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICE,--����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESAMOUNT,--ȯ���ܶ�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--ȫ�۽��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICEAMOUNT,--���۽��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--ʵ���ո�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitNetCost,--���۵�λ�ɱ�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--ȫ�۵�λ�ɱ�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			if(securitiesMenuId == 10)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (31,32)  \n");
			}
			if(securitiesMenuId == 11)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (21,22)  \n");
			}
			if(securitiesMenuId == 12)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (36,37)  \n");
			}
			if(securitiesMenuId == 13)
			{
				sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (41,42)  \n");
			}					
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 14)//����7�����м�ծȯ�ع�
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--���׶���(ID)  \n");

			sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE,--�״ν�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYDATE,--���ڽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESID,--��Ѻծȯ����(ID)  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.RATE,--�ع�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TERM,--�ع�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESAMOUNT,--ȯ���ܶ�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGESECURITIESQUANTITY,--��Ѻծȯ����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.PLEDGERATE,--�������  \n");
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--�ɽ����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.MATURITYAMOUNT,--���ڻ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--ʵ���ո�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (26)  \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 15)//����8������ʽ����
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TRANSACTIONDATE,--�ɽ�����  \n");
			sbFrom.append("   SEC_DELIVERYORDER.CLIENTID,--ҵ��λ(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.COUNTERPARTID,--���׶���(ID)  \n");

			sbFrom.append("   SEC_DELIVERYORDER.ACCOUNTID,--�����ʺ�(ID)  \n");
			sbFrom.append("   SEC_DELIVERYORDER.DELIVERYDATE,--������  \n");
			sbFrom.append("   SEC_DELIVERYORDER.SECURITIESID,--֤ȯ����(ID)  \n");
				
			sbFrom.append("   SEC_DELIVERYORDER.AMOUNT,--���  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETPRICE,--��ֵ  \n");
			sbFrom.append("   SEC_DELIVERYORDER.QUANTITY,--�ݶ�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.TAX,--˰��  \n");
			sbFrom.append("   SEC_DELIVERYORDER.NETINCOME,--ʵ���ո�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.UnitCost,--��λ�ɱ�  \n");
			sbFrom.append("   SEC_DELIVERYORDER.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_DELIVERYORDER,SEC_STOCKHOLDERACCOUNT,SEC_COUNTERPART \n");
			sbFrom.append("   where SEC_DELIVERYORDER.TRANSACTIONTYPEID=SEC_TRANSACTIONTYPE.ID \n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+)=SEC_ACCOUNT.STOCKHOLDERACCOUNTID1 \n");
			sbFrom.append("   and SEC_DELIVERYORDER.ACCOUNTID=SEC_ACCOUNT.ID(+)\n");
			sbFrom.append("   and SEC_DELIVERYORDER.COUNTERPARTID = SEC_COUNTERPART.ID(+) \n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (61,62,63,64) \n");
				
			getRemainderStrOnetoEight(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				strDeliveryOrderInputDateStart,
				strDeliveryOrderInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
			sbFrom.append( " order by  SEC_DELIVERYORDER.SECURITIESID,SEC_DELIVERYORDER.TRANSACTIONDATE  \n");
		}
		if(securitiesMenuId == 16)//����9���ʲ��ع�
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--ҵ��֪ͨ��(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--���׶�������  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--�ع����  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionStartDate,--�ʲ��ع���ʼ��  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionEndDate,--�ʲ��ع�������  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.RATE,--����  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.SETTLEMENTTYPEID,--��Ϣ��ʽ(ID)  \n");				
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--����ִ����  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--������  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--ʵ����Ϣ  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (71)  \n");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}
		if(securitiesMenuId == 17)//����10��ί�����
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--ҵ��֪ͨ��(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--���׶�������  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--ί�н��  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionDate as TRANSACTIONSTARTDATE,--ί����ƿ�ʼ��  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionEndDate,--ί����ƽ�����  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.RATE,--������  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.SETTLEMENTTYPEID,--��Ϣ��ʽ(ID)  \n");				
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--����ִ����  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--������  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--ʵ��������  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (73,75)  \n");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}
		if(securitiesMenuId == 18)//����11���ṹ��Ͷ��
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--ҵ��֪ͨ��(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--���׶�������  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--�����  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.CURRENCYID,--����(ID)  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionStartDate,--��ͬ��Ч�տ�ʼ��  \n");
			sbFrom.append("   SEC_ApplyContract.TransactionEndDate,--��ͬ��Ч�ս�����  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.RATE,--������  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.SETTLEMENTTYPEID,--��Ϣ��ʽ(ID)  \n");				
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--����ִ����  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--������  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--ʵ��������  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (77) \n ");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}
		if(securitiesMenuId == 19)//����12��ծȯ����
		{
			sbFrom.append("  select   \n");									
			sbFrom.append("   SEC_TRANSACTIONTYPE.BUSINESSTYPEID, --ҵ������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.TRANSACTIONTYPEID,--��������(ID)  \n");
			sbFrom.append("   SEC_NOTICEFORM.CODE as NOTICEFORMCODE,--ҵ��֪ͨ��(CODE)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COUNTERPARTID,--���׶�������  \n");
			sbFrom.append("   SEC_ApplyForm.SECURITIESID,--ծȯ(ID)  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.AMOUNT,--�������  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.INTERESTTYPEID,--������ʽID  \n");
			sbFrom.append("   SEC_APPLYCONTRACT.COMMISSIONCHARGERATE,--�������� \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEAMOUNT,--������  \n");
			sbFrom.append("   SEC_NOTICEFORM.NOTICEINTEREST,--ʵ����Ϣ  \n");
			sbFrom.append("   SEC_NOTICEFORM.EXECUTEDATE,--����ִ����  \n");
			sbFrom.append("   SEC_NOTICEFORM.STATUSID--״̬  \n");
			sbFrom.append("   from \n");
			sbFrom.append("   SEC_NOTICEFORM,SEC_APPLYCONTRACT,SEC_APPLYFORM,SEC_TRANSACTIONTYPE,SEC_ACCOUNT,SEC_STOCKHOLDERACCOUNT \n");
			sbFrom.append("   where SEC_NOTICEFORM.CONTRACTID = SEC_APPLYCONTRACT.ID(+) \n");
			sbFrom.append("   and  SEC_APPLYCONTRACT.APPLYID = SEC_APPLYFORM.ID(+) \n");
			sbFrom.append("   and SEC_NOTICEFORM.TRANSACTIONTYPEID = SEC_TRANSACTIONTYPE.ID\n");
			sbFrom.append("   and  SEC_STOCKHOLDERACCOUNT.ID(+) = SEC_ACCOUNT.STOCKHOLDERACCOUNTID1\n");
			sbFrom.append("   and  SEC_ApplyContract.ACCOUNTID = SEC_ACCOUNT.ID(+)\n");
			sbFrom.append( "  and SEC_TRANSACTIONTYPE.BUSINESSTYPEID in (81)  \n");
				
			getRemainderStrNinetoTwelve(
				strNoticeInputDateStart,
				strNoticeInputDateEnd,
				businessTypeId,
				transactionTypeIds,
				clientId,
				stockHolderAccountIds,
				counterPartId,
				bankOfDepositId,
				accountIds,
				securitiesIds);
		}


		
		
		//sbFromƴд������������������������
		
		
		
		
		sbFrom.append("   )  \n");
		
		

	}




	//ǰ8������SQL�����str
	private void getRemainderStrOnetoEight(
		String strNoticeInputDateStart,
		String strNoticeInputDateEnd,
		String strDeliveryOrderInputDateStart,
		String strDeliveryOrderInputDateEnd,
		long businessTypeId,
		String[] transactionTypeIds,
		long clientId,
		String[] stockHolderAccountIds,
		long counterPartId,
		long bankOfDepositId,
		String[] accountIds,
		String[] securitiesIds) {

		//����1�����ڿ�ʼ�� 
		if(!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(
				" and to_char(SEC_DELIVERYORDER.TRANSACTIONDATE , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "' \n");
		}
		//����2�����ڽ����� 
		if(!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(
				"and to_char(SEC_DELIVERYORDER.TRANSACTIONDATE , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "' \n");
		}
//		����1�����ڿ�ʼ�� 
		if(!"".equals(strDeliveryOrderInputDateStart))
		{
			sbFrom.append(
				" and to_char(SEC_DELIVERYORDER.INPUTDATE , 'yyyy-mm-dd') >= " + "'" + strDeliveryOrderInputDateStart + "' \n");
		}
		//����2�����ڽ����� 
		if(!"".equals(strDeliveryOrderInputDateEnd))
		{
			sbFrom.append(
				"and to_char(SEC_DELIVERYORDER.INPUTDATE , 'yyyy-mm-dd') <= " + "'" + strDeliveryOrderInputDateEnd + "' \n");
		}
		//����3��ҵ������ID
		if(businessTypeId!=-1)
		{
			sbFrom.append( " and \n ");
			sbFrom.append( " SEC_TRANSACTIONTYPE.BUSINESSTYPEID = "+businessTypeId);
		}
		//����4����������ID
		if (transactionTypeIds != null && transactionTypeIds.length > 0) 
		{
			sbFrom.append(" and SEC_DELIVERYORDER.TRANSACTIONTYPEID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1])
					+ ") \n");
		}
		//����5��ҵ��λ
		if(clientId!=-1)
		{
			sbFrom.append( " and \n");
			sbFrom.append( " SEC_DELIVERYORDER.CLIENTID = "+clientId);
		}
		//����6���ɶ��ʻ�
		if(stockHolderAccountIds !=null&&stockHolderAccountIds.length >0)
		{
			sbFrom.append( " and SEC_STOCKHOLDERACCOUNT.ID in (");
			for(int i=0;i<stockHolderAccountIds.length -1;i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i])+" , ");
					
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length -1])+" ) \n ");
		}
		//����7�����׶���id,����8������Ӫҵ��: ��� ���׶���,����Ӫҵ�� ��ѡ����
		if (counterPartId != -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_DELIVERYORDER.COUNTERPARTID in (" + counterPartId + "," + bankOfDepositId + ") \n ");					
		}
		//����7�����׶���id,����8������Ӫҵ��: ���ֻѡ���� ���׶��֣�û��ѡ�� ����Ӫҵ��			
		if (counterPartId != -1 && bankOfDepositId == -1)
		{
			sbFrom.append( " and SEC_DELIVERYORDER.COUNTERPARTID = " + counterPartId + " \n ");	
		}
		//����7�����׶���id,����8������Ӫҵ��: ���ֻѡ���� ����Ӫҵ����û��ѡ�� ���׶���
		if (counterPartId == -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_DELIVERYORDER.COUNTERPARTID = " + bankOfDepositId + " \n ");	
		}
		//����9���ʽ��˺�
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and SEC_DELIVERYORDER.ACCOUNTID in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//����10��֤ȯ����
		if (securitiesIds != null && securitiesIds.length > 0) 
		{
			sbFrom.append(" and SEC_DELIVERYORDER.SECURITIESID in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(securitiesIds[securitiesIds.length - 1])
					+ ") \n");
		}
	}


	//��4������SQL�����str
	private void getRemainderStrNinetoTwelve(
		String strNoticeInputDateStart,
		String strNoticeInputDateEnd,
		long businessTypeId,
		String[] transactionTypeIds,
		long clientId,
		String[] stockHolderAccountIds,
		long counterPartId,
		long bankOfDepositId,
		String[] accountIds,
		String[] securitiesIds) {

		//����1�����ڿ�ʼ�� 
		if(!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(SEC_NOTICEFORM.ExecuteDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//����2�����ڽ����� 
		if(!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(SEC_NOTICEFORM.ExecuteDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		//����3��ҵ������ID
		if(businessTypeId!=-1)
		{
			sbFrom.append( " and \n ");
			sbFrom.append( " SEC_TRANSACTIONTYPE.BUSINESSTYPEID = "+businessTypeId);
		}
		//����4����������ID
		if (transactionTypeIds != null && transactionTypeIds.length > 0) 
		{
			sbFrom.append(" and SEC_NOTICEFORM.TRANSACTIONTYPEID in ( ");
			for (int i = 0; i < transactionTypeIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(transactionTypeIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(transactionTypeIds[transactionTypeIds.length - 1])
					+ ") \n");
		}
		//����5��ҵ��λ
		if(clientId!=-1)
		{
			sbFrom.append( " and \n");
			sbFrom.append( " SEC_ApplyContract.CLIENTID = "+clientId);
		}
		//����6���ɶ��ʻ�
		if(stockHolderAccountIds !=null&&stockHolderAccountIds.length >0)
		{
			sbFrom.append( " and SEC_STOCKHOLDERACCOUNT.ID in (");
			for(int i=0;i<stockHolderAccountIds.length -1;i++)
			{
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i])+" , ");
					
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length -1])+" ) \n ");
		}
		//����7�����׶���id,����8������Ӫҵ��: ��� ���׶���,����Ӫҵ�� ��ѡ����
		if (counterPartId != -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_ApplyContract.COUNTERPARTID in (" + counterPartId + "," + bankOfDepositId + ") \n ");					
		}
		//����7�����׶���id,����8������Ӫҵ��: ���ֻѡ���� ���׶��֣�û��ѡ�� ����Ӫҵ��			
		if (counterPartId != -1 && bankOfDepositId == -1)
		{
			sbFrom.append( " and SEC_ApplyContract.COUNTERPARTID = " + counterPartId + " \n ");	
		}
		//����7�����׶���id,����8������Ӫҵ��: ���ֻѡ���� ����Ӫҵ����û��ѡ�� ���׶���
		if (counterPartId == -1 && bankOfDepositId != -1)
		{
			sbFrom.append( " and SEC_ApplyContract.COUNTERPARTID = " + bankOfDepositId + " \n ");	
		}
		//����9���ʽ��˺�
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and SEC_ApplyContract.ACCOUNTID in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//����10��֤ȯ����
		if (securitiesIds != null && securitiesIds.length > 0) 
		{
			sbFrom.append(" and SEC_ApplyForm.SECURITIESID in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(securitiesIds[securitiesIds.length - 1])
					+ ") \n");
		}
	}


	/**
	 * ����PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader printSecuritiesExchangeStatus(PrintSecuritiesExchangeStatusParam printParam)
		throws SecuritiesException {

		getSQL(printParam);
		//
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		//log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.print.dataentity.PrintSecuritiesExchangeStatusInfo",
			null);
		pageLoader.setOrderBy("  ");
		return pageLoader;
	}

}