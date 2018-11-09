/*
 * Created on 2004-4-5
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dao.QuerySecuritiesStockDAO;

import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesStockParam;
import com.iss.itreasury.securities.query.dataentity.StockQuerySumInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author hjliu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QuerySecuritiesStockBean {

    protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

    private StringBuffer sbSelect = null;

    private StringBuffer sbFrom = null;

    private StringBuffer sbWhere = null;

    private StringBuffer sbOrderBy = null;

    public final static int OrderBy_ClientName = 1;

    public final static int OrderBy_BankOfDepositName = 2;

    public final static int OrderBy_AccountName = 3;

    public final static int OrderBy_FundManagerCoName = 4;//�����ý��׶������򣬹����û������˾����

    public final static int OrderBy_SecuritiesType = 5;

    public final static int OrderBy_SecuritiesName = 6;

    public final static int OrderBy_Quantity = 7;

    public final static int OrderBy_Cost = 8;

    public final static int OrderBy_NetCost = 9;

    /**
     * ����֤ȯ��ѯ��SQLString���
     * 
     * @param queryParam
     * @return
     */
    private void getSQL(QuerySecuritiesStockParam queryParam) {
        sbSelect = new StringBuffer();

        sbSelect.append("   *  \n");

        sbFrom = new StringBuffer();

        sbFrom.append("   (  \n");

        long exchangeCenterId = queryParam.getExchangeCenterId(); //֤ȯ�����г�Id

        long securitiesType = queryParam.getSecuritiesType(); //֤ȯ���

        String[] securitiesIds = queryParam.getSecuritiesIds(); //֤ȯ����Id������

        long clientId = queryParam.getClientId();//ҵ��λID

        String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds();// �ɶ��ʻ�����Id������

        long bankOfDepositId = queryParam.getBankOfDepositId();//����Ӫҵ��ID

        String[] accountIds = queryParam.getAccountIds();//�ʽ��˺�Id������

		String[] counterPartIds = queryParam.getCounterPartIds();//���׶���

        sbFrom.append("   select  DISTINCT  \n");
        sbFrom.append("   daily.stockDate as StockDate, --������� \n");
        sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName, --�ɶ��ʻ�����\n");
        sbFrom.append("   client.name as ClientName, --ҵ��λ���� \n");
        sbFrom.append("   0 as businessAttributeID, --ҵ������ \n");
        sbFrom.append("   decode(counterpart.isbankOfDeposit ,1,counterpart.name,'') as BankOfDepositName, --����Ӫҵ������ \n");
        sbFrom.append("   account.accountCode as AccountCode, --�ʽ��˺� \n");
        sbFrom.append("   account.accountName as AccountName, --�ʽ��˻����� \n");
        sbFrom.append("   --account.type as AccountType, --�ʽ��˻����ͣ�1:֤ȯ�˻���2:����ʽ�����˻���3:�����˻� \n");
		sbFrom.append("   decode(counterpart.IsBankOfDeposit,1,-1,counterpart.Id) as CounterPartId,--���׶��� \n");
        sbFrom.append("   secType.name as SecuritiesType, --֤ȯ���� \n");
        sbFrom.append("   securities.shortname as securitiesName , --֤ȯ���� \n");
        sbFrom.append("   --securities.pledgeRate as SecuritiesRate, --�ع���Ѻ���ʣ������ڼ����۳ɱ�׼ȯ \n");
        sbFrom.append("   daily.Quantity as Quantity, --������� \n");
        sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) \n" +
        	"		as StandardQuantity, --�۳ɱ�׼ȯ�����û�лع���Ѻ���ʣ��򷵻��㣩,ȥβ�� \n");
        sbFrom.append("   daily.frozenQuantity as FrozenQuantity, --�Ѷ���֤ȯ���� \n");
        sbFrom.append("   decode((daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) ,null, 0 , daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) as UnitCost, --��λ�ɱ� \n");
        sbFrom.append("   decode((daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)),null , 0 , daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)) as UnitNetCost, --��λ�ɱ������ۣ� \n");
        sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost --ʵ�ʳɱ���ʵ�ʳɱ������ۣ� \n");
        
        sbFrom.append("   from \n");

        //---------------------//֤ȯ����ս������ҵ��λ�� �ɶ��ʻ�
        sbFrom.append("   sec_dailyStock daily,sec_client  client, SEC_STOCKHOLDERACCOUNT ,\n");
        //---------------------//�ʽ��˻���֤ȯ���ϱ�
        sbFrom.append("   sec_account account , sec_securities securities, \n");
        //---------------------//���׶��ֱ�֤ȯ���ͱ�
        sbFrom.append("   (select * from SEC_COUNTERPART where ISBANK is null or ISBANK = -1) counterpart ,sec_SecuritiesType secType \n");

//        sbFrom.append("   (select a.id as transactionTypeId, \n");
//        sbFrom.append("           a.name as transactionTypeName,  \n");
//        sbFrom.append("           b.Id as businessTypeId,  \n");
//        sbFrom.append("           b.name as businessTypeName, \n");
//        sbFrom.append("            c.ID as businessAttributeID \n");
//        sbFrom
//                .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
//        sbFrom
//                .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
//        sbFrom.append("     ) ST_Type  \n");

        sbFrom.append("   where  \n");
        
        sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.ClientID = client.id \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.accountid = account.id  \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.securitiesID= securities.id \n");
        sbFrom.append("   and \n");
        sbFrom.append("   account.counterpartID = counterpart.ID \n");
        sbFrom.append("   and \n");
        sbFrom.append("   securities.typeId = secType.ID \n");
//        sbFrom.append("   and  \n");
//        sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n"); //��Ӧ�ŵ�1��ҵ��

        //		===========�����Ǹ���ҳ�洫�ݵĲ�������������======start====
        //����1����ѯ����
        Timestamp queryDate = queryParam.getQueryDate();
        if (queryDate != null) {
            String strQueryDate = DataFormat.getDateString(queryDate);
            sbFrom.append(" and \n");
            sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate
                    + "','yyyy-mm-dd')");
        }
        //����2��֤ȯ�����г�
        if (exchangeCenterId != -1) {
            sbFrom.append(" and securities.EXCHANGECENTERID = "
                    + exchangeCenterId + " \n");
        }

        //����3��֤ȯ���
        if (securitiesType > 0) {
            sbFrom.append(" and \n");
            sbFrom.append(" securities.typeId = " + securitiesType);
        }
        //����4��֤ȯ����
        if (securitiesIds != null && securitiesIds.length > 0) {
            sbFrom.append(" and securities.id in ( ");
            for (int i = 0; i < securitiesIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
            }
            sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1])
                    + ") \n");
        }

        //����5��ҵ��λ
        if (clientId != -1) {
            sbFrom.append(" and client.id = " + clientId + " \n");
        }

        //����6���ɶ��ʻ�
        if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
            sbFrom.append(" and SEC_STOCKHOLDERACCOUNT.CLIENTID in ( ");
            for (int i = 0; i < stockHolderAccountIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
            }
            sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1])
                            + ") \n");
        }

        //����7���ʽ��˺�
        if (accountIds != null && accountIds.length > 0) {
            sbFrom.append(" and account.id in ( ");
            for (int i = 0; i < accountIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(accountIds[i]) + ",");
            }
            sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1])
                    + ") \n");
        }

		//����7.1��ֻѡ���׶���
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1 )
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//����7.2��ֻѡ���׶���,����Ӫҵ������
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1 )
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����8.1��ֻѡ����Ӫҵ������
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n counterpart.ID = " + bankOfDepositId + " \n");
		}

        //		===========�����Ǹ���ҳ�洫�ݵĲ�������������======end====

        //�����Ǵ���accout = -1�������

        sbFrom.append(" 	UNION   \n");
        sbFrom.append("   select  DISTINCT  \n");
        sbFrom.append("   daily.stockDate as StockDate, --������� \n");
        sbFrom.append("   '' as stockHolderAccountName, --�ɶ��ʻ����� \n");
        sbFrom.append("   client.name as ClientName, --ҵ��λ���� \n");
        sbFrom.append("   0 as businessAttributeID, --ҵ������ \n");
        sbFrom.append("   '' as BankOfDepositName, --����Ӫҵ������ \n");
        sbFrom.append("   '' as AccountCode, --�ʽ��˺� \n");
        sbFrom.append("   '' as AccountName, --�ʽ��˻����� \n");
        sbFrom.append("   --account.type as AccountType,--�ʽ��˻����ͣ�1:֤ȯ�˻���2:����ʽ�����˻���3:�����˻�  \n");
        sbFrom.append("   -1 as CounterPartId , --���׶��� \n");
        sbFrom.append("   secType.name as SecuritiesType, --֤ȯ���� \n");
        sbFrom.append("   securities.shortname as securitiesName , --֤ȯ���� \n");
        sbFrom.append("   --securities.pledgeRate as SecuritiesRate, --�ع���Ѻ���ʣ������ڼ����۳ɱ�׼ȯ \n");
        sbFrom.append("   daily.Quantity as Quantity, --������� \n");
        sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) " +
        	"		as StandardQuantity, --�۳ɱ�׼ȯ�����û�лع���Ѻ���ʣ��򷵻��㣩,ȥβ�� \n");
        sbFrom.append("   daily.frozenQuantity as FrozenQuantity, --�Ѷ���֤ȯ���� \n");
        sbFrom.append("   decode((daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) ,null, 0 ) as UnitCost,--��λ�ɱ� \n");
        sbFrom.append("   decode((daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)),null , 0 ) as UnitNetCost,--��λ�ɱ������ۣ�\n");
        sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost --ʵ�ʳɱ���ʵ�ʳɱ������ۣ�\n");

        sbFrom.append("   from \n");
        //---------------------//֤ȯ����ս������ҵ��λ�� �ɶ��ʻ�
        sbFrom.append("   sec_dailyStock daily,sec_client  client,SEC_STOCKHOLDERACCOUNT, \n");
        //---------------------//�ʽ��˻���֤ȯ���ϱ�
        sbFrom.append("   sec_securities securities,sec_account account, \n");
        //---------------------//���׶��ֱ�֤ȯ���ͱ�
        sbFrom.append("   sec_SecuritiesType secType \n");

//        sbFrom.append("   (select a.id as transactionTypeId, \n");
//        sbFrom.append("           a.name as transactionTypeName,  \n");
//        sbFrom.append("           b.Id as businessTypeId,  \n");
//        sbFrom.append("           b.name as businessTypeName, \n");
//        sbFrom.append("            c.ID as businessAttributeID \n");
//        sbFrom
//                .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
//        sbFrom
//                .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
//        sbFrom.append("     ) ST_Type  \n");

        sbFrom.append("   where 1=1 \n");
        sbFrom.append("   and SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.ClientID = client.id(+) \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.accountid = -1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.accountid = account.id(+)  \n");
        sbFrom.append("   and \n");
        sbFrom.append("   daily.securitiesID= securities.id \n");
//        sbFrom.append("   and \n");
//        sbFrom.append("   account.counterpartID = counterpart.ID \n");
        sbFrom.append("   and \n");
        sbFrom.append("   securities.typeId = secType.ID \n");
//        sbFrom.append("   and  \n");
//        sbFrom.append("   ST_Type.businessAttributeID = 1 \n"); //��Ӧ�ŵ�1��ҵ��

        //		===========�����Ǹ���ҳ�洫�ݵĲ�������������======start====
        //����1����ѯ����
         queryDate = queryParam.getQueryDate();
        if (queryDate != null) {
            String strQueryDate = DataFormat.getDateString(queryDate);
            sbFrom.append(" and \n");
            sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate
                    + "','yyyy-mm-dd')");
        }
        //����2��֤ȯ�����г�
        if (exchangeCenterId != -1) {
            sbFrom.append(" and securities.EXCHANGECENTERID = "
                    + exchangeCenterId + " \n");
        }

        //����3��֤ȯ���
        if (securitiesType > 0) {
            sbFrom.append(" and \n");
            sbFrom.append(" securities.typeId = " + securitiesType);
        }
        //����4��֤ȯ����
        if (securitiesIds != null && securitiesIds.length > 0) {
            sbFrom.append(" and securities.id in ( ");
            for (int i = 0; i < securitiesIds.length - 1; i++) {
                sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
            }
            sbFrom.append(Long
                    .parseLong(securitiesIds[securitiesIds.length - 1])
                    + ") \n");
        }

        //����5��ҵ��λ
        if (clientId != -1) {
            sbFrom.append(" and client.id = " + clientId + " \n");
        }

		//����6���ɶ��ʻ�
		if (stockHolderAccountIds != null && stockHolderAccountIds.length > 0) {
			sbFrom.append(" and account.STOCKHOLDERACCOUNTID1 in ( ");
			for (int i = 0; i < stockHolderAccountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(stockHolderAccountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(stockHolderAccountIds[stockHolderAccountIds.length - 1])
							+ ") \n");
		}

		//����7���ʽ��˺�
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and daily.accountid in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1])
					+ ") \n");
		}

		//����7.1��ֻѡ���׶���
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId == -1 )
		{
			sbFrom.append(" and account.counterpartID in ( ");
			for (int i = 0; i < counterPartIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(counterPartIds[counterPartIds.length - 1]) + ") \n");
		}
		//����7.2��ֻѡ���׶���,����Ӫҵ������
		if (counterPartIds != null && counterPartIds.length > 0 && bankOfDepositId != -1 )
		{
			sbFrom.append(" and account.counterpartID in ( ");
			for (int i = 0; i < counterPartIds.length; i++)
			{
				sbFrom.append(Long.parseLong(counterPartIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//����8.1��ֻѡ����Ӫҵ������
		if (bankOfDepositId != -1 && (counterPartIds == null || counterPartIds.length == 0))
		{
			sbFrom.append(" and \n account.counterpartID = " + bankOfDepositId + " \n");
		}

        //sbFromƴд��������������������������

        sbFrom.append("   )  \n");

        //------------from----------------end-------------------------
        sbWhere = new StringBuffer();

        //------------where---------------end-------------------------
        sbWhere.append(" ");

        //------------where---------------end-------------------------

        sbOrderBy = new StringBuffer();
        String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
        log
                .debug("----queryParam.getDesc() -----------"
                        + queryParam.getDesc());
        switch ((int) queryParam.getOrderField()) {
        case OrderBy_ClientName:
            sbOrderBy.append(" \n order by ClientName" + strDesc);
            break;
        case OrderBy_BankOfDepositName:
            sbOrderBy.append(" \n order by BankOfDepositName" + strDesc);
            break;
        case OrderBy_AccountName:
            sbOrderBy.append(" \n order by AccountName" + strDesc);
            break;
        case OrderBy_FundManagerCoName:
            sbOrderBy.append(" \n order by CounterPartId" + strDesc);
            break;
        case OrderBy_SecuritiesType:
            sbOrderBy.append(" \n order by SecuritiesType" + strDesc);
            break;
        case OrderBy_SecuritiesName:
            sbOrderBy.append(" \n order by securitiesName" + strDesc);
            break;
        case OrderBy_Quantity:
            sbOrderBy.append(" \n order by Quantity" + strDesc);
            break;
        case OrderBy_Cost:
            sbOrderBy.append(" \n order by cost" + strDesc);
            break;
        case OrderBy_NetCost:
            sbOrderBy.append(" \n order by netcost" + strDesc);
            break;

        }
        log.debug("-----------------------------------------");

    }
	/**
	 * ����֤ȯ��ѯ��SQLString���
	 * 
	 * @param queryParam
	 * ˵�����������ڹ�����Ŀ�Ĳ�ѯ---֤ȯ����ѯ 
	 * @return
	 */
	private void getCNMEFSQL(QuerySecuritiesStockParam queryParam)
	{
		System.out.println("^_^������֤ȯ����ѯ---getCNMEFSQL()");
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		sbFrom.append("   (  \n");
		//long exchangeCenterId = queryParam.getExchangeCenterId(); //֤ȯ�����г�Id
		long securitiesType = queryParam.getSecuritiesType(); //֤ȯ���
		String[] securitiesIds = queryParam.getSecuritiesIds(); //֤ȯ����Id������
		long clientId = queryParam.getClientId(); //ҵ��λID
		//String[] stockHolderAccountIds = queryParam.getStockHolderAccountIds(); // �ɶ��ʻ�����Id������
		long bankOfDepositId = queryParam.getBankOfDepositId(); //����Ӫҵ��ID
		String[] accountIds = queryParam.getAccountIds(); //�ʽ��˺�Id������
		String[] fundManagerCoIds = queryParam.getFundManagerCoIds(); //�������˾Id������
		sbFrom.append("   select  DISTINCT  \n");
		//---------------------//�������
		sbFrom.append("  daily.stockDate as StockDate,  \n");
		//---------------------//�ɶ��ʻ�����
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.name as stockHolderAccountName, \n");
		//---------------------//ҵ��λ����
		sbFrom.append("   client.name as ClientName,  \n");
		//---------------------//ҵ������
		sbFrom.append("   0 as businessAttributeID,  \n");
		//---------------------//����Ӫҵ������
		sbFrom.append("   decode(counterpart.isbankOfDeposit ,1,counterpart.name,'') as BankOfDepositName,  \n");
		//---------------------//�ʽ��˺�
		sbFrom.append("   account.accountCode as AccountCode,  \n");
		//---------------------//�ʽ��˻�����
		sbFrom.append("   account.accountName as AccountName,  \n");
		//---------------------//�ʽ��˻����ͣ�1:֤ȯ�˻���2:����ʽ�����˻���3:�����˻�
		sbFrom.append("   --account.type as AccountType,  \n");
		//---------------------//�������˾����
		sbFrom.append("   decode(counterpart.isFundManagementCo ,1,counterpart.name,'') as FundManagerCoName ,  \n");
		//---------------------//֤ȯ����
		sbFrom.append("   secType.name as SecuritiesType,  \n");
		//---------------------//֤ȯ����
		sbFrom.append("   securities.shortname as securitiesName ,  \n");
		//---------------------//�ع���Ѻ���ʣ������ڼ����۳ɱ�׼ȯ
		sbFrom.append("   --securities.pledgeRate as SecuritiesRate,  \n");
		//---------------------//�������
		sbFrom.append("   daily.Quantity as Quantity,  \n");
		//---------------------//�۳ɱ�׼ȯ�����û�лع���Ѻ���ʣ��򷵻��㣩,ȥβ��
		sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) as StandardQuantity, \n");
		//---------------------//�Ѷ���֤ȯ����
		sbFrom.append("   daily.frozenQuantity as FrozenQuantity,  \n");
		//---------------------//��λ�ɱ�
		sbFrom.append("   decode(sign(daily.Quantity),0,0,round(daily.cost/daily.Quantity,2)) as UnitCost, \n");
		//---------------------//��λ�ɱ������ۣ�
		sbFrom.append("   decode(sign(daily.Quantity),0,0,round(daily.Netcost/daily.Quantity,2)) as UnitNetCost, \n");
		//---------------------//ʵ�ʳɱ���ʵ�ʳɱ������ۣ�
		sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost\n");
		sbFrom.append("   from \n");
		//---------------------//֤ȯ����ս������ҵ��λ�� �ɶ��ʻ�
		sbFrom.append("   sec_dailyStock daily,sec_client  client, SEC_STOCKHOLDERACCOUNT ,\n");
		//---------------------//�ʽ��˻���֤ȯ���ϱ�
		sbFrom.append("   sec_account account , sec_securities securities, \n");
		//---------------------//���׶��ֱ�֤ȯ���ͱ�
		sbFrom.append("   sec_counterPArt counterpart ,sec_SecuritiesType secType \n");
		//		  sbFrom.append("   (select a.id as transactionTypeId, \n");
		//		  sbFrom.append("           a.name as transactionTypeName,  \n");
		//		  sbFrom.append("           b.Id as businessTypeId,  \n");
		//		  sbFrom.append("           b.name as businessTypeName, \n");
		//		  sbFrom.append("            c.ID as businessAttributeID \n");
		//		  sbFrom
		//				  .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		//		  sbFrom
		//				  .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		//		  sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where  \n");
		sbFrom.append("   SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.ClientID = client.id \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.accountid = account.id  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.securitiesID= securities.id \n");
		sbFrom.append("   and \n");
		sbFrom.append("   account.counterpartID = counterpart.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   securities.typeId = secType.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.Quantity > 0             \n");
		
		//		  sbFrom.append("   and  \n");
		//		  sbFrom.append("   ST_Type.businessAttributeID in (1,2,3)  \n"); //��Ӧ�ŵ�1��ҵ��
		//		===========�����Ǹ���ҳ�洫�ݵĲ�������������======start====
		//����1����ѯ����
		Timestamp queryDate = queryParam.getQueryDate();
		if (queryDate != null)
		{
			String strQueryDate = DataFormat.getDateString(queryDate);
			sbFrom.append(" and \n");
			sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate + "','yyyy-mm-dd')");
		}
		/**
		//����2��֤ȯ�����г�
		if (exchangeCenterId != -1)
		{
			sbFrom.append(" and securities.EXCHANGECENTERID = " + exchangeCenterId + " \n");
		}
		**/
		//����3��֤ȯ���
		if (securitiesType > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" securities.typeId = " + securitiesType);
		}
		//����4��֤ȯ����
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and securities.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//����5��ҵ��λ
		if (clientId != -1)
		{
			sbFrom.append(" and client.id = " + clientId + " \n");
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
		//����7���ʽ��˺�
		if (accountIds != null && accountIds.length > 0)
		{
			sbFrom.append(" and account.id in ( ");
			for (int i = 0; i < accountIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}
		//����8��ֻѡ����Ӫҵ������
		if (bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
		{
			sbFrom.append(" and counterpart.ID = " + bankOfDepositId + " \n");
		}
		//����9��ֻѡ�������˾����
		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1)
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
		}
		//����10��ֻѡ����Ӫҵ������,�������˾����
		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
		{
			sbFrom.append(" and counterpart.ID in ( ");
			for (int i = 0; i < fundManagerCoIds.length; i++)
			{
				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
			}
			sbFrom.append(bankOfDepositId + ") \n");
		}
		//		===========�����Ǹ���ҳ�洫�ݵĲ�������������======end====
		//�����Ǵ���accout = -1�������
		sbFrom.append(" UNION   \n");
		sbFrom.append("   select  DISTINCT  \n");
		//---------------------//�������
		sbFrom.append("  daily.stockDate as StockDate,  \n");
		//---------------------//�ɶ��ʻ�����
		sbFrom.append("   '' as stockHolderAccountName, \n");
		//---------------------//ҵ��λ����
		sbFrom.append("   client.name as ClientName,  \n");
		//---------------------//ҵ������
		sbFrom.append("   0 as businessAttributeID,  \n");
		//---------------------//����Ӫҵ������
		sbFrom.append("   '' as BankOfDepositName,  \n");
		//---------------------//�ʽ��˺�
		sbFrom.append("   '' as AccountCode,  \n");
		//---------------------//�ʽ��˻�����
		sbFrom.append("   '' as AccountName,  \n");
		//---------------------//�ʽ��˻����ͣ�1:֤ȯ�˻���2:����ʽ�����˻���3:�����˻�
		sbFrom.append("   --account.type as AccountType,  \n");
		//---------------------//�������˾����
		sbFrom.append("   '' as FundManagerCoName ,  \n");
		//---------------------//֤ȯ����
		sbFrom.append("   secType.name as SecuritiesType,  \n");
		//---------------------//֤ȯ����
		sbFrom.append("   securities.shortname as securitiesName ,  \n");
		//---------------------//�ع���Ѻ���ʣ������ڼ����۳ɱ�׼ȯ
		sbFrom.append("   --securities.pledgeRate as SecuritiesRate,  \n");
		//---------------------//�������
		sbFrom.append("   daily.Quantity as Quantity,  \n");
		//---------------------//�۳ɱ�׼ȯ�����û�лع���Ѻ���ʣ��򷵻��㣩,ȥβ��
		sbFrom.append("  (daily.Quantity * decode(securities.pledgeRate,null,0,securities.pledgeRate) /100) as StandardQuantity, \n");
		//---------------------//�Ѷ���֤ȯ����
		sbFrom.append("   daily.frozenQuantity as FrozenQuantity,  \n");
		//---------------------//��λ�ɱ�
		sbFrom.append("   decode((daily.cost/decode(daily.Quantity,0,1,daily.Quantity)) ,null, 0 ) as UnitCost, \n");
		//---------------------//��λ�ɱ������ۣ�
		sbFrom.append("   decode((daily.Netcost/decode(daily.Quantity,0,1,daily.Quantity)),null , 0 ) as UnitNetCost, \n");
		//---------------------//ʵ�ʳɱ���ʵ�ʳɱ������ۣ�
		sbFrom.append("   daily.cost as Cost,daily.NetCost as NetCost\n");
		sbFrom.append("   from \n");
		//---------------------//֤ȯ����ս������ҵ��λ�� �ɶ��ʻ�
		sbFrom.append("   sec_dailyStock daily,sec_client  client, \n");
		//---------------------//�ʽ��˻���֤ȯ���ϱ�
		sbFrom.append("   sec_securities securities, \n");
		//---------------------//���׶��ֱ�֤ȯ���ͱ�
		sbFrom.append("   sec_SecuritiesType secType \n");
		//		  sbFrom.append("   (select a.id as transactionTypeId, \n");
		//		  sbFrom.append("           a.name as transactionTypeName,  \n");
		//		  sbFrom.append("           b.Id as businessTypeId,  \n");
		//		  sbFrom.append("           b.name as businessTypeName, \n");
		//		  sbFrom.append("            c.ID as businessAttributeID \n");
		//		  sbFrom
		//				  .append("    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		//		  sbFrom
		//				  .append("    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		//		  sbFrom.append("     ) ST_Type  \n");
		sbFrom.append("   where 1=1 \n");
//		sbFrom.append("   and SEC_STOCKHOLDERACCOUNT.ID(+) = account.STOCKHOLDERACCOUNTID1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.ClientID = client.id \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.accountid = -1 \n");
		sbFrom.append("   and \n");
		sbFrom.append("   daily.securitiesID= securities.id \n");
//		sbFrom.append("   and \n");
//		sbFrom.append("   account.counterpartID = counterpart.ID \n");
		sbFrom.append("   and \n");
		sbFrom.append("   securities.typeId = secType.ID \n");
		//		  sbFrom.append("   and  \n");
		//		  sbFrom.append("   ST_Type.businessAttributeID in (1,2,3) \n"); //��Ӧ�ŵ�1��ҵ��
		//		===========�����Ǹ���ҳ�洫�ݵĲ�������������======start====
		//����1����ѯ����
		queryDate = queryParam.getQueryDate();
		if (queryDate != null)
		{
			String strQueryDate = DataFormat.getDateString(queryDate);
			sbFrom.append(" and \n");
			sbFrom.append(" daily.stockDate = to_Date('" + strQueryDate + "','yyyy-mm-dd')");
		}
		/**
		//����2��֤ȯ�����г�
		if (exchangeCenterId != -1)
		{
			sbFrom.append(" and securities.EXCHANGECENTERID = " + exchangeCenterId + " \n");
		}
		**/
		//����3��֤ȯ���
		if (securitiesType > 0)
		{
			sbFrom.append(" and \n");
			sbFrom.append(" securities.typeId = " + securitiesType);
		}
		//����4��֤ȯ����
		if (securitiesIds != null && securitiesIds.length > 0)
		{
			sbFrom.append(" and securities.id in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++)
			{
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(Long.parseLong(securitiesIds[securitiesIds.length - 1]) + ") \n");
		}
		//����5��ҵ��λ
		if (clientId != -1)
		{
			sbFrom.append(" and client.id = " + clientId + " \n");
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
//		//����7���ʽ��˺�
//		if (accountIds != null && accountIds.length > 0)
//		{
//			sbFrom.append(" and account.id in ( ");
//			for (int i = 0; i < accountIds.length - 1; i++)
//			{
//				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
//			}
//			sbFrom.append(Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
//		}
//		//����8��ֻѡ����Ӫҵ������
//		if (bankOfDepositId != -1 && (fundManagerCoIds == null || fundManagerCoIds.length == 0))
//		{
//			sbFrom.append(" and counterpart.ID = " + bankOfDepositId + " \n");
//		}
//		//����9��ֻѡ�������˾����
//		if (fundManagerCoIds != null && fundManagerCoIds.length > 0 && bankOfDepositId == -1)
//		{
//			sbFrom.append(" and counterpart.ID in ( ");
//			for (int i = 0; i < fundManagerCoIds.length - 1; i++)
//			{
//				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
//			}
//			sbFrom.append(Long.parseLong(fundManagerCoIds[fundManagerCoIds.length - 1]) + ") \n");
//		}
//		//����10��ֻѡ����Ӫҵ������,�������˾����
//		if (bankOfDepositId != -1 && fundManagerCoIds != null && fundManagerCoIds.length > 0)
//		{
//			sbFrom.append(" and counterpart.ID in ( ");
//			for (int i = 0; i < fundManagerCoIds.length; i++)
//			{
//				sbFrom.append(Long.parseLong(fundManagerCoIds[i]) + ",");
//			}
//			sbFrom.append(bankOfDepositId + ") \n");
//		}
		//sbFromƴд��������������������������
		sbFrom.append("   )  \n");
		//------------from----------------end-------------------------
		sbWhere = new StringBuffer();
		//------------where---------------end-------------------------
		sbWhere.append(" ");
		//------------where---------------end-------------------------
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		log.debug("----queryParam.getDesc() -----------" + queryParam.getDesc());
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_ClientName :
				sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_BankOfDepositName :
				sbOrderBy.append(" \n order by BankOfDepositName" + strDesc);
				break;
			case OrderBy_AccountName :
				sbOrderBy.append(" \n order by AccountName" + strDesc);
				break;
			case OrderBy_FundManagerCoName :
				sbOrderBy.append(" \n order by FundManagerCoName" + strDesc);
				break;
			case OrderBy_SecuritiesType :
				sbOrderBy.append(" \n order by SecuritiesType" + strDesc);
				break;
			case OrderBy_SecuritiesName :
				sbOrderBy.append(" \n order by securitiesName" + strDesc);
				break;
			case OrderBy_Quantity :
				sbOrderBy.append(" \n order by Quantity" + strDesc);
				break;
			case OrderBy_Cost :
				sbOrderBy.append(" \n order by cost" + strDesc);
				break;
			case OrderBy_NetCost :
				sbOrderBy.append(" \n order by netcost" + strDesc);
				break;
		}
		log.debug("-----------------------------------------");
	}

    /*
     * 
     * �����ﻹ������һ������sum�ķ��� ���ݾ� 2004-04-08 public QueryFixedDepositSumInfo SELECT
     * sum(quantity) as QuantitySum,sum(cost) as CostSum,sum(netcost) as
     * NetCostSum!!!!
     *  
     */
    public StockQuerySumInfo queryStockSum(QuerySecuritiesStockParam queryParam)
            throws SecuritiesException {
        StockQuerySumInfo sumInfo = new StockQuerySumInfo();
        String sqlString = "";
        //����sql���
        getSQL(queryParam);
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT \n");
        sqlBuffer.append(" sum(quantity) as stockQuantitySum, \n");
        sqlBuffer.append(" sum(cost) as stockCostSum,  \n");
        sqlBuffer.append(" sum(netcost) as stockNetCostSum \n");
        sqlBuffer.append(" FROM  \n");
        //String sqlString = "SELECT sum(quantity) as
        // stockQuantitySum,sum(cost) as stockCostSum,sum(netcost) as
        // stockNetCostSum FROM \n";

        sqlString = sqlBuffer.toString() + sbFrom.toString();
        log.debug("����֤ȯ����sql��䣺" + sqlString);
        QuerySecuritiesStockDAO dao = new QuerySecuritiesStockDAO();
        try {
            Collection col = dao.findByCondiction(sqlString);
            if (col != null && col.size() > 0) {
                Iterator colIterator = col.iterator();
                sumInfo = (StockQuerySumInfo) colIterator.next();
            }

        } catch (SecuritiesDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new SecuritiesException("Gen_E001", null);
        }
        log.debug("sumInfo=���ϼ�" + sumInfo.getStockQuantitySum());
        log.debug("sumInfo=�ɱ��ϼ�" + sumInfo.getStockCostSum());
        log.debug("sumInfo=�ɱ������ۣ��ϼ�" + sumInfo.getStockNetCostSum());
        return sumInfo;
    }

	/**
	 * ����PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader querySecuritiesStockInfo(QuerySecuritiesStockParam queryParam) throws SecuritiesException
	{
		/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
		{
			System.out.println("��Ŀ���ƣ�����;ģ�飺֤ȯ����ѯ");
			getCNMEFSQL(queryParam);
		}
		else
		{*/
			System.out.println("��Ŀ���ƣ�--;ģ�飺֤ȯ����ѯ");
			getSQL(queryParam);
		//}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		//		log.debug("selectString :" + sbSelect.toString());
		//		log.debug("fromString :" + sbFrom.toString());
		//		log.debug("whereString :" + sbWhere.toString());
		log.debug("sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QuerySecuritiesStockInfo",
			null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}

}
