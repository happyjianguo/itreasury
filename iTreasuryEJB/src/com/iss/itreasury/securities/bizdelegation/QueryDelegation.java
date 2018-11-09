/*
 * Created on 2004-4-7
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.bizlogic.QueryApplyFormBean;
import com.iss.itreasury.securities.query.bizlogic.QueryBusinessAttributeBean;
import com.iss.itreasury.securities.query.bizlogic.QueryBusinessTypeBean;
import com.iss.itreasury.securities.query.bizlogic.QueryCapitalLandingFormBean;
import com.iss.itreasury.securities.query.bizlogic.QueryContractExecutePlanBean;
import com.iss.itreasury.securities.query.bizlogic.QueryDeliveryorderBean;
import com.iss.itreasury.securities.query.bizlogic.QueryExecutePlanBean;
import com.iss.itreasury.securities.query.bizlogic.QueryExtendContractBean;
import com.iss.itreasury.securities.query.bizlogic.QueryNoticeFormBean;
import com.iss.itreasury.securities.query.bizlogic.QuerySecuritiesBean;
import com.iss.itreasury.securities.query.bizlogic.QuerySecuritiesContractBean;
import com.iss.itreasury.securities.query.bizlogic.QuerySecuritiesStockBean;
import com.iss.itreasury.securities.query.bizlogic.QuerySecuritiesTypeBean;
import com.iss.itreasury.securities.query.bizlogic.QueryTransactionTypeBean;
import com.iss.itreasury.securities.query.dataentity.QueryApplyFormParam;
import com.iss.itreasury.securities.query.dataentity.QueryBusinessAttributeParam;
import com.iss.itreasury.securities.query.dataentity.QueryBusinessTypeParam;
import com.iss.itreasury.securities.query.dataentity.QueryCapitalLandingFormParam;
import com.iss.itreasury.securities.query.dataentity.QueryContractExecutePlanParam;
import com.iss.itreasury.securities.query.dataentity.QueryDeliveryorderParam;
import com.iss.itreasury.securities.query.dataentity.QueryExecutePlanParam;
import com.iss.itreasury.securities.query.dataentity.QueryNoticeFormParam;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesContractParam;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesParam;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesStockParam;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesTypeParam;
import com.iss.itreasury.securities.query.dataentity.QueryTransactionTypeParam;
import com.iss.itreasury.securities.query.dataentity.StockQuerySumInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author hjliu
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryDelegation {
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	public QueryDelegation() throws RemoteException {

	}

	/**
	 * ��ѯ֤ȯ����ѯ
	 * 
	 * @param QuerySecuritiesStockParam
	 *            ֤ȯ��ѯҳ�����
	 * @throws SecuritiesException
	 */
	public PageLoader searchSecuritiesStock(QuerySecuritiesStockParam queryParam)
		throws SecuritiesException {
		PageLoader securitiesStockPageLoader = null;
		QuerySecuritiesStockBean querySecuritiesStockBean =
			new QuerySecuritiesStockBean();

		log4j.debug(
			"queryDelegation debug info ::::searchSecuritiesStock start");
		securitiesStockPageLoader =
			querySecuritiesStockBean.querySecuritiesStockInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::searchSecuritiesStock end");
		return securitiesStockPageLoader;
	}
	/**
	 * ���ؿ���ѯ�ϼ���Ϣ
	 * 
	 * @param QuerySecuritiesStockParam
	 *            ֤ȯ��ѯҳ�����
	 * @return @throws
	 *         SecuritiesException
	 */
	public StockQuerySumInfo queryStockSum(QuerySecuritiesStockParam queryParam)
		throws SecuritiesException {
		StockQuerySumInfo sumInfo = new StockQuerySumInfo();
		QuerySecuritiesStockBean querySecuritiesStockBean =
			new QuerySecuritiesStockBean();
		log4j.debug("queryDelegation debug info ::::queryStockSum start");
		sumInfo = querySecuritiesStockBean.queryStockSum(queryParam);
		log4j.debug("queryDelegation debug info ::::queryStockSum end");
		return sumInfo;
	}

	/**
	 * ҵ��֪ͨ����ѯ,����һ��PageLoader
	 * 
	 * @param QueryNoticeFormParam ҵ��֪ͨ����ѯҳ������
	 * @throws SecuritiesException
	 */
	public PageLoader queryNoticeForm(QueryNoticeFormParam queryParam)
		throws SecuritiesException {
		PageLoader queryNoticePageLoader = null;
		QueryNoticeFormBean queryNoticeFormBean = new QueryNoticeFormBean();

		log4j.debug("queryDelegation debug info ::::queryNoticeForm start");
		queryNoticePageLoader =
			queryNoticeFormBean.queryNoticeFormInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::queryNoticeForm end");
		return queryNoticePageLoader;
	}
	
	
	/**
	 * ���뵥��ѯ,����һ��PageLoader
	 * @author ����
	 * @param QueryApplyFormParam ҵ��֪ͨ����ѯҳ������
	 * @throws SecuritiesException
	 */
	public PageLoader queryApplyForm(QueryApplyFormParam queryParam)
		throws SecuritiesException {
		PageLoader queryApplyPageLoader = null;
		QueryApplyFormBean queryApplyFormBean = new QueryApplyFormBean();

		log4j.debug("queryDelegation debug info ::::queryApplyForm start");
		queryApplyPageLoader =
			queryApplyFormBean.queryApplyFormInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::queryApplyForm end");
		return queryApplyPageLoader;
	}

	/**
	 * ��ͬ��ѯ,����һ��PageLoader
	 * @author ����
	 * @param QuerySecuritiesContractParam ��ͬ��ѯҳ������
	 * @throws SecuritiesException
	 */
	public PageLoader queryContractForm(QuerySecuritiesContractParam queryParam)
		throws SecuritiesException {
		PageLoader queryApplyPageLoader = null;
		QuerySecuritiesContractBean queryContractFormBean = new QuerySecuritiesContractBean();

		log4j.debug("queryDelegation debug info ::::queryContractForm start");
		queryApplyPageLoader =
			queryContractFormBean.queryContractInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::queryContractForm end");
		return queryApplyPageLoader;
	}
	
	/**
	 * ��ͬ��ѯ,����һ��PageLoader
	 * @author gqfang
	 * @param QuerySecuritiesContractParam ��ͬ��ѯҳ������
	 * @throws SecuritiesException
	 */
	public PageLoader queryExtendContractForm(QuerySecuritiesContractParam queryParam)
		throws SecuritiesException 
	{
		PageLoader queryPageLoader = null;
		QueryExtendContractBean extendContractBean = new QueryExtendContractBean();

		log4j.debug("queryDelegation debug info ::::QueryExtendContractBean start");
		queryPageLoader =extendContractBean.queryContractInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::QueryExtendContractBean end");
		return queryPageLoader;
	}
	
	/** �����ѯ������һ��PageLoader
		 * @author�崫��
		 * @param queryParam
		 * @return
		 * @throws SecuritiesException
		 */
		public PageLoader queryDeliveryorder(QueryDeliveryorderParam queryParam)
		throws SecuritiesException {
		PageLoader queryNoticePageLoader = null;
		QueryDeliveryorderBean queryDeliveryorderBean = new QueryDeliveryorderBean();

		log4j.debug("queryDelegation debug info ::::queryDeliveryorder start");
		queryNoticePageLoader =
			queryDeliveryorderBean.queryDeliveryorderInfo (queryParam);
		log4j.debug("queryDelegation debug info ::::queryDeliveryorder end");
		return queryNoticePageLoader;
	}
	/**
		* ҵ�����ʲ�ѯ������һ��PageLoader @author����
		* 
		* @param queryParam
		* @return @throws
		*         SecuritiesException
		*/
	   public PageLoader queryBusinessAttribute(QueryBusinessAttributeParam queryParam) throws SecuritiesException {
		   PageLoader queryBusinessAttributePageLoader = null;
		   QueryBusinessAttributeBean queryBusinessAttributeBean = new QueryBusinessAttributeBean();

		   log4j.debug("queryDelegation debug info ::::queryBusinessAttribute start");
		   queryBusinessAttributePageLoader = queryBusinessAttributeBean
				   .queryBusinessAttributeInfo(queryParam);
		   log4j
				   .debug("queryDelegation debug info ::::queryBusinessAttribute end");
		   return queryBusinessAttributePageLoader;
	   }
	   
	   
	/**
	 * ���뵥��ѯ,����һ��PageLoader
	 * @author ����
	 * @param QueryCapitalLandingFormParam ҵ��֪ͨ����ѯҳ������
	 * @throws SecuritiesException
	 */
	public PageLoader queryCapitalLandingForm(QueryCapitalLandingFormParam queryParam)
		throws SecuritiesException {
		PageLoader queryPageLoader = null;
		QueryCapitalLandingFormBean queryBean = new QueryCapitalLandingFormBean();

		log4j.debug("queryDelegation debug info ::::queryCapitalLandingForm start");
		queryPageLoader =
		queryBean.queryCapitalLandingFormInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::queryCapitalLandingForm end");
		return queryPageLoader;
	}
	
	/**
		 * ֤ȯ����ѯ������һ��PageLoader @author����
		 * 
		 * @param queryParam
		 * @return @throws
		 *         SecuritiesException
		 */
		public PageLoader querySecuritiesType(QuerySecuritiesTypeParam queryParam)
				throws SecuritiesException {
			PageLoader querySecuritiesTypePageLoader = null;
			QuerySecuritiesTypeBean querySecuritiesTypeBean = new QuerySecuritiesTypeBean();

			log4j.debug("queryDelegation debug info ::::querySecuritiesType start");
			querySecuritiesTypePageLoader = querySecuritiesTypeBean
					.querySecuritiesTypeInfo(queryParam);
			log4j.debug("queryDelegation debug info ::::querySecuritiesType end");
			return querySecuritiesTypePageLoader;
		}

		/**
		 * ҵ�����Ͳ�ѯ������һ��PageLoader @author����
		 * 
		 * @param queryParam
		 * @return @throws
		 *         SecuritiesException
		 */
		public PageLoader queryBusinessType(QueryBusinessTypeParam queryParam)
				throws SecuritiesException {
			PageLoader queryBusinessTypePageLoader = null;
			QueryBusinessTypeBean queryBusinessTypeBean = new QueryBusinessTypeBean();

			log4j.debug("queryDelegation debug info ::::queryBusinessType start");
			queryBusinessTypePageLoader = queryBusinessTypeBean
					.queryBusinessTypeInfo(queryParam);
			log4j.debug("queryDelegation debug info ::::queryBusinessType end");
			return queryBusinessTypePageLoader;
		}

		/**
		 * �������Ͳ�ѯ������һ��PageLoader @author����
		 * 
		 * @param queryParam
		 * @return @throws
		 *         SecuritiesException
		 */
		public PageLoader queryTransactionType(QueryTransactionTypeParam queryParam)
				throws SecuritiesException {
			PageLoader queryTransactionTypePageLoader = null;
			QueryTransactionTypeBean queryTransactionTypeBean = new QueryTransactionTypeBean();

			log4j
					.debug("queryDelegation debug info ::::queryTransactionType start");
			queryTransactionTypePageLoader = queryTransactionTypeBean
					.queryTransactionTypeInfo(queryParam);
			log4j.debug("queryDelegation debug info ::::queryTransactionType end");
			return queryTransactionTypePageLoader;
		}		
	
	/**
	 * �����ں�ͬ��ѯ����ϸҳ���е����ִ�мƻ���Ϣ����ť�����ӵ��ض�¼�������ض�״̬�µ�ִ�мƻ�����,����һ��PageLoader
	 * @author ����
	 * @param QueryExecutePlanParam ִ�мƻ���Ϣ ��ѯҳ������
	 * @return PageLoader
	 * @throws SecuritiesException
	 */
	public PageLoader queryExecutePlan(QueryExecutePlanParam queryParam)
		throws SecuritiesException {
		PageLoader queryPageLoader = null;
		QueryExecutePlanBean queryBean = new QueryExecutePlanBean();

		log4j.debug("queryDelegation debug info ::::QueryExecutePlanBean start");
		queryPageLoader =
		queryBean.queryExecutePlanInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::QueryExecutePlanBean end");
		return queryPageLoader;
	}
	/**
	 * ���ڲ�ѯ֤ȯ����µ�����֤ȯ
	 * @author gqfang
	 * @param  QuerySecuritiesParam
	 * @return PageLoader
	 * @throws SecuritiesException
	 */
	public PageLoader querySecurities(QuerySecuritiesParam queryParam) throws SecuritiesException
	{
		PageLoader queryPageLoader = null;
		QuerySecuritiesBean queryBean = new QuerySecuritiesBean();
		log4j.debug("queryDelegation debug info ****QuerySecuritiesBean start");
		queryPageLoader = queryBean.querySecuritiesInfo(queryParam);
		log4j.debug("queryDelegation debug info ****QuerySecuritiesBean end");
		return queryPageLoader; 
	}
	
	/**
	 * �����ں�ͬ��ѯ����ϸҳ���е����ִ�мƻ���Ϣ����ť�����ӵ��ض�¼�������ض�״̬�µ�ִ�мƻ�����,����һ��PageLoader
	 * @author ����
	 * @param QueryContractExecutePlanParam ִ�мƻ���Ϣ ��ѯҳ������
	 * @return PageLoader
	 * @throws SecuritiesException
	 */
	public PageLoader QueryContractExecutePlan(QueryContractExecutePlanParam queryParam)
		throws SecuritiesException {
		PageLoader queryPageLoader = null;
		QueryContractExecutePlanBean queryBean = new QueryContractExecutePlanBean();

		log4j.debug("queryDelegation debug info ::::QueryContractExecutePlanBean start");
		queryPageLoader =
		queryBean.queryExecutePlanInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::QueryContractExecutePlanBean end");
		return queryPageLoader;
	}
	
	
	
}
