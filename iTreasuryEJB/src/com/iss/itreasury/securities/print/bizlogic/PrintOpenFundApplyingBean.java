package com.iss.itreasury.securities.print.bizlogic;
import java.sql.Timestamp;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.print.dataentity.PrintOpenFundApplyingParam;
/**
 * @author gqfang
 * ����ʽ����һ����
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintOpenFundApplyingBean
{


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
	private void getSQL(PrintOpenFundApplyingParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");
		sbWhere = new StringBuffer();
		sbWhere.append(" ");
		sbFrom = new StringBuffer();
		
		sbFrom.append("(     \n");
  		
		
		
		

		//����1�����ڿ�ʼ�� ��������
		Timestamp queryDateStart = queryParam.getQueryDateStart();
		String strQueryDateStart = DataFormat.getDateString(queryDateStart);
		//����2�����ڽ����� ��������
		Timestamp queryDateEnd = queryParam.getQueryDateEnd();
		String strQueryDateEnd = DataFormat.getDateString(queryDateEnd);
		//����3��ҵ��λ
		long clientId = queryParam.getClientId();
		//����4��֤ȯ����
		String[] securitiesIds = queryParam.getSecuritiesId();
		//����5���������˾����
		String[] fundManagerCoId = queryParam.getFundManagerCoId();
		//����6��//�ʽ��ʺ�
		String[] accountIds = queryParam.getAccountId();
		//������
		Timestamp closeDate = queryParam.getPreInputDate();
		String strCloseDate = DataFormat.getDateString(closeDate);
		
		
		sbFrom.append("  select   \n");
						
		sbFrom.append("   applyDelivery.clientId as clientId,--ҵ��λ  \n");
		sbFrom.append("   applyDelivery.counterPartId as counterPartId,  --�ʽ����˾  \n");
		sbFrom.append("   applyDelivery.accountId as accountId,--�ʽ��˺�  \n");
		sbFrom.append("   applyDelivery.securitiesId as securitiesId, --֤ȯid  \n");
		sbFrom.append("   applyDelivery.transactionTypeId as transactionTypeId, --��������id  \n");
		
		sbFrom.append("   applyDelivery.amountForApplying as amountForApplying,--�깺-���  \n");
		sbFrom.append("   applyDelivery.netPriceForApplying as netPriceForApplying,  --�깺-��ֵ  \n");
		sbFrom.append("   applyDelivery.quantityForApplying as quantityForApplying,--�깺-�ݶ�  \n");
		
		sbFrom.append("   confirmDelivery.amountForConfirm as amountForConfirm,--ȷ��-�Ϲ����  \n");
		sbFrom.append("   confirmDelivery.netPriceForConfirm as netPriceForConfirm,  --ȷ��-��ֵ  \n");
		sbFrom.append("   confirmDelivery.quantityForConfirm as quantityForConfirm,--ȷ��-ȷ�Ϸݶ�  \n");
		
		sbFrom.append("   cuttingDelivery.cashForCutting as cashForCutting,--�ֺ�-�ֽ����  \n");
		sbFrom.append("   cuttingDelivery.toQuantityForCutting as toQuantityForCutting,--�ֺ�-����ת�ݶ�  \n");
		sbFrom.append("   cuttingDelivery.finalQuantityForCutting as finalQuantityForCutting,  --�ֺ�-���շݶ�  \n");
		
		sbFrom.append("   redeemDelivery.netPriceForRedeem as netPriceForRedeem,--���-��ֵ  \n");
		sbFrom.append("   redeemDelivery.amountForRedeem as amountForRedeem,  --���-���  \n");
		sbFrom.append("   redeemDelivery.quantityForRedeem as quantityForRedeem, --���-�ݶ� \n");
		//sbFrom.append("   sum(totalDetails.finalQuantityForCutting) as finalQuantityForCutting,  --���-ʵ��ӯ��  \n");
		
		sbFrom.append("   presentQuantity.presentQuantity as presentQuantity, --�ַݶ� \n");
		sbFrom.append("   marketValue.marketValue as marketValue --��ֵ \n");
	
		sbFrom.append("  from \n");
		
				sbFrom.append("  ( \n");
				 
				sbFrom.append("  select  -- ����ʽ����-�깺 \n");
				sbFrom.append("    id as id , \n");
				sbFrom.append("    clientId as clientId,--ҵ��λ  \n");
		        sbFrom.append("    counterPartId as counterPartId,  --�ʽ����˾  \n");
		        sbFrom.append("    accountId as accountId,--�ʽ��˺�  \n");
		        sbFrom.append("    securitiesId as securitiesId, --֤ȯid  \n");
		        sbFrom.append("    transactionTypeId as transactionTypeId, --��������id  \n");
				sbFrom.append("    amount as amountForApplying ,  --���\n");
				sbFrom.append("    netPrice as netPriceForApplying ,  --��ֵ\n");
				sbFrom.append("    quantity as quantityForApplying, --�ݶ�\n");
				sbFrom.append("    TransactionDate as TransactionDate --��������\n");
				sbFrom.append("  from Sec_DeliveryOrder \n");
				sbFrom.append("  where transactionTypeId in (" + SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE + ","
								                               + SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID + ")" + "\n");
		        sbFrom.append("  and statusid >= 3 \n");
				sbFrom.append("  ) applyDelivery,\n");
				
				sbFrom.append("  ( \n");
				sbFrom.append("  select  --  ����ʽ����-ȷ�� \n");
				sbFrom.append("    id as id , \n");
				sbFrom.append("    clientId as clientId,--ҵ��λ  \n");
		        sbFrom.append("    counterPartId as counterPartId,  --�ʽ����˾  \n");
		        sbFrom.append("    accountId as accountId,--�ʽ��˺�  \n");
		        sbFrom.append("    securitiesId as securitiesId, --֤ȯid  \n");
				sbFrom.append("    amount as amountForConfirm,  --�Ϲ����\n");
				sbFrom.append("    netPrice as netPriceForConfirm,  --��ֵ\n");
				sbFrom.append("    quantity as quantityForConfirm  --ȷ�Ϸݶ�\n");
				sbFrom.append("  from Sec_DeliveryOrder\n");
				sbFrom.append("  where transactionTypeId in (" + SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM + ","
		                                                       + SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM + ")" + "\n");
				sbFrom.append("  )confirmDelivery, \n");
				
				sbFrom.append("  ( \n"); 
				sbFrom.append("  select  --����ʽ����-�ֺ�\n");
				//sbFrom.append("    Sec_DeliveryOrder.id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--ҵ��λ  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --�ʽ����˾  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--�ʽ��˺�  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --֤ȯid  \n");
				sbFrom.append("    sum(Sec_DeliveryOrder.amount) as cashForCutting,  --�ֽ����\n");
				sbFrom.append("    sum(Sec_DeliveryOrder.quantity) as toQuantityForCutting,  --����ת�ݶ�\n");
				sbFrom.append("    Sec_SecuritiesStock.quantity as finalQuantityForCutting  --���շݶ�\n");
				sbFrom.append("  from Sec_DeliveryOrder,Sec_SecuritiesStock \n");
				sbFrom.append("  where transactionTypeId in (" + SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING + ","
		                                                       + SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_SHARE_MELON_CUTTING + ")" + "\n");
				sbFrom.append("    And Sec_DeliveryOrder.SecuritiesId = Sec_SecuritiesStock.SecuritiesId  \n");
				sbFrom.append("    And Sec_DeliveryOrder.AccountID = Sec_SecuritiesStock.AccountID  \n");
				sbFrom.append("    And Sec_DeliveryOrder.ClientId = Sec_SecuritiesStock.ClientId   \n");
				sbFrom.append(" group by Sec_DeliveryOrder.clientId,Sec_DeliveryOrder.counterPartId, \n");
				sbFrom.append("    Sec_DeliveryOrder.accountId,Sec_DeliveryOrder.securitiesId  \n");
				sbFrom.append("    ,Sec_SecuritiesStock.quantity \n");
				sbFrom.append("  ) cuttingDelivery, \n");
				
				sbFrom.append("  ( \n");
				sbFrom.append("  select  --����ʽ����-���\n");
				//sbFrom.append("    id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--ҵ��λ  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --�ʽ����˾  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--�ʽ��˺�  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --֤ȯid  \n");
				sbFrom.append("    sum(amount)/ sum(quantity) as netPriceForRedeem,  --��ֵ\n");
				sbFrom.append("    sum(amount) as amountForRedeem,  --���\n");
				sbFrom.append("    sum(quantity) as quantityForRedeem  --�ݶ�\n");
				sbFrom.append("  from Sec_DeliveryOrder\n");
				sbFrom.append("  where transactionTypeId = " + SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM +  "\n");
				sbFrom.append(" group by Sec_DeliveryOrder.clientId,Sec_DeliveryOrder.counterPartId, \n");
				sbFrom.append("    Sec_DeliveryOrder.accountId,Sec_DeliveryOrder.securitiesId  \n");
				sbFrom.append("  ) redeemDelivery, \n");
				
				
				sbFrom.append("  ( \n");	
				sbFrom.append("  select  distinct --�ַݶ�\n");
				//sbFrom.append("    Sec_DeliveryOrder.id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--ҵ��λ  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --�ʽ����˾  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--�ʽ��˺�  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --֤ȯid  \n");
				sbFrom.append("    Sec_SecuritiesStock.quantity as presentQuantity  --�ַݶ�\n");
				sbFrom.append("  from Sec_DeliveryOrder,Sec_SecuritiesStock \n");
				sbFrom.append("  where Sec_DeliveryOrder.SecuritiesId = Sec_SecuritiesStock.SecuritiesId  \n");
				sbFrom.append("    And Sec_DeliveryOrder.AccountID = Sec_SecuritiesStock.AccountID  \n");
				sbFrom.append("    And Sec_DeliveryOrder.ClientId = Sec_SecuritiesStock.ClientId   \n");
				sbFrom.append("  ) presentQuantity,\n");
				
				sbFrom.append("  ( \n");
				sbFrom.append("  select  distinct--��ֵ\n");
				//sbFrom.append("    Sec_DeliveryOrder.id as id , \n");
				sbFrom.append("    Sec_DeliveryOrder.clientId as clientId,--ҵ��λ  \n");
		        sbFrom.append("    Sec_DeliveryOrder.counterPartId as counterPartId,  --�ʽ����˾  \n");
		        sbFrom.append("    Sec_DeliveryOrder.accountId as accountId,--�ʽ��˺�  \n");
		        sbFrom.append("    Sec_DeliveryOrder.securitiesId as securitiesId, --֤ȯid  \n");
				sbFrom.append("    Sec_SecuritiesMarket.netClosePrice as marketValue  --��ֵ\n");
				sbFrom.append("  from Sec_DeliveryOrder,Sec_SecuritiesMarket,Sec_Securities \n");
				sbFrom.append("  where Sec_DeliveryOrder.SecuritiesId = Sec_Securities.Id  \n");
				sbFrom.append("    And Sec_Securities.SecuritiesCode2 = Sec_SecuritiesMarket.securitiesCode  \n");
				
				sbFrom.append("    And to_char(Sec_SecuritiesMarket.CloseDate , 'yyyy-mm-dd') = " + "'" + DataFormat.getDateString(queryParam.getQueryDateEnd()) + "'");
				sbFrom.append("  ) marketValue\n");
				
			
			sbFrom.append(" WHERE  applyDelivery.clientId = confirmDelivery.clientId(+)  \n");
			sbFrom.append("    AND applyDelivery.clientId = cuttingDelivery.clientId(+)  AND applyDelivery.clientId = redeemDelivery.clientId(+)  \n");
			sbFrom.append("    AND applyDelivery.clientId = presentQuantity.clientId(+)  AND applyDelivery.clientId = marketValue.clientId(+)  \n");
			
			sbFrom.append("    AND applyDelivery.counterPartId = confirmDelivery.counterPartId(+)  \n");
			sbFrom.append("    AND applyDelivery.counterPartId = cuttingDelivery.counterPartId(+)  AND applyDelivery.counterPartId = redeemDelivery.counterPartId(+)  \n");
			sbFrom.append("    AND applyDelivery.counterPartId = presentQuantity.counterPartId(+)  AND applyDelivery.counterPartId = marketValue.counterPartId(+)  \n");
			
			sbFrom.append("    AND  applyDelivery.accountId = confirmDelivery.accountId(+)  \n");
			sbFrom.append("    AND applyDelivery.accountId = cuttingDelivery.accountId(+)  AND applyDelivery.accountId = redeemDelivery.accountId(+)  \n");
			sbFrom.append("    AND applyDelivery.accountId = presentQuantity.accountId(+)  AND applyDelivery.accountId = marketValue.accountId(+)  \n");
			
			sbFrom.append("    AND applyDelivery.securitiesId = confirmDelivery.securitiesId(+)  \n");
			sbFrom.append("    AND applyDelivery.securitiesId = cuttingDelivery.securitiesId(+)  AND applyDelivery.securitiesId = redeemDelivery.securitiesId(+)  \n");
			sbFrom.append("    AND applyDelivery.securitiesId = presentQuantity.securitiesId(+)  AND applyDelivery.securitiesId = marketValue.securitiesId(+)  \n");
			
			
        //===========�����Ǹ���ҳ�洫�ݵĲ�������������==========
		//����һ�����¼�����ڿ�ʼ��
		if(!"".equals(strQueryDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(applyDelivery.TransactionDate , 'yyyy-mm-dd') >= " + "'" + strQueryDateStart + "'");
		}
		
		//�����������¼���������
		if(!"".equals(strQueryDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(applyDelivery.TransactionDate , 'yyyy-mm-dd') <= " + "'" + strQueryDateEnd + "'");
		}
		
		//��������ҵ��λ
		if (clientId != -1) {
			sbFrom.append(" and \n applyDelivery.clientId = " + clientId + " \n");
		} 
		
        //�����ģ�֤ȯ����
		if (securitiesIds != null && securitiesIds.length > 0) {
			sbFrom.append(" and applyDelivery.SecuritiesID in ( ");
			for (int i = 0; i < securitiesIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(securitiesIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(securitiesIds[securitiesIds.length - 1])
					+ ") \n");
		}
		//�����壺�ʽ����˾����
		if (fundManagerCoId != null && fundManagerCoId.length > 0) {
			sbFrom.append(" and applyDelivery.counterpartID in ( ");
			for (int i = 0; i < fundManagerCoId.length - 1; i++) {
				sbFrom.append(Long.parseLong(fundManagerCoId[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(fundManagerCoId[fundManagerCoId.length - 1])
					+ ") \n");
		}
        //����6���ʽ��˺�
		if (accountIds != null && accountIds.length > 0) {
			sbFrom.append(" and applyDelivery.AccountID in ( ");
			for (int i = 0; i < accountIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(accountIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(accountIds[accountIds.length - 1]) + ") \n");
		}

		sbFrom.append("     order by applyDelivery.TransactionDate , clientId, counterPartId, accountId ,securitiesId ,transactionTypeId  \n");
		sbFrom.append("   )  \n");
		
		
		//sbFromƴд������������������������
	
		
		

	}

	

	/**
	 * ����PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader printOpenFund(PrintOpenFundApplyingParam printParam) throws SecuritiesException {

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
			"com.iss.itreasury.securities.print.dataentity.PrintOpenFundApplyingInfo",
			null);
		pageLoader.setOrderBy("  ");
		return pageLoader;
	}


}