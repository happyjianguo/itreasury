package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesContractParam;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @Name: QueryExtendContractBean.java @Description: @
 * 
 * @Author : gqfang 
 * @Create Date: 2005-4-26 To change the template for this
 * generated type comment go to Window - Preferences - Java - Code Generation -
 * Code and Comments
 */
public class QueryExtendContractBean
{
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;

	public final static int OrderBy_ContractCode = 1;       //ԭ��ͬ���
	public final static int OrderBy_ExtendContractCode = 2; //չ�ں�ͬ���
	public final static int OrderBy_Amount = 3;             //չ�ڽ��
	public final static int OrderBy_ExtendStartDate = 4;    //չ����ʼ��
	public final static int OrderBy_ExtendEndDate = 5;      //չ�ڵ�����
	public final static int OrderBy_Term = 6;               //չ������
	public final static int OrderBy_Rate = 7;               //չ������
	public final static int OrderBy_ContractStatusID = 8;   //չ�ں�ͬ״̬
	

	/**
	 * ���غ�ͬ��ѯ��SQLString���
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QuerySecuritiesContractParam queryParam)
	{
		System.out.println("Log========================================================AAA");
		sbSelect = new StringBuffer();
		sbSelect.append("   *  \n");
		sbFrom = new StringBuffer();
		
		//[��ͬ���]��[��Ӧ��������]��[ҵ������]��[���׶�������]��[��ͬ¼������]��[��ͬ״̬]��[���״̬]��[��������]��[¼����]
		sbFrom.append("(     \n");
		//����1��ҵ������id
		long businessTypeId = queryParam.getBusinessTypeId();
		//����2����ʼ��ͬ���
		long startContractId = queryParam.getStartContractId();
		//����3��������ͬ���
		long endContractId = queryParam.getEndContractId();
		//����4�����׶���
		long counterpartId = queryParam.getCounterpartId();
		//����5����ͬ¼�����ڿ�ʼ��
		Timestamp startDate = queryParam.getStartDate();
		String strStartDate = DataFormat.getDateString(startDate);
		//����6����ͬ¼�����ڽ�����
		Timestamp endDate = queryParam.getEndDate();
		String strEndDate = DataFormat.getDateString(endDate);
		//����7����ͬ״̬
		long statusID = queryParam.getStatusID();
		//����8��¼����
		long inputUserId = queryParam.getInputUserId();
		
		sbFrom.append("  select \n");
		sbFrom.append("   SEC_EXTENDFORM.id as id,--չ�ں�ͬid  \n");
		sbFrom.append("   SEC_EXTENDFORM.code as code,--չ�ں�ͬ���  \n");
		sbFrom.append("   SEC_EXTENDFORM.ApplyContractID as applyContractId ,--��Ӧԭ��ͬid  \n");
		sbFrom.append("   SEC_BUSINESSTYPE.id as businessTypeId ,--ҵ������id  \n");
		sbFrom.append("   SEC_EXTENDFORM.TRANSACTIONTYPEID as transactionTypeId,--��������id  \n");
		//sbFrom.append("   sec_applyContract.CounterpartID as counterpartID ,--���׶���id  \n");
		sbFrom.append("   SEC_EXTENDFORM.InputDate as inputDate ,--չ�ں�ͬ¼������  \n");
		sbFrom.append("   SEC_EXTENDFORM.ExtendStartDate as extendStartDate ,--չ�ں�ͬ��ʼ��  \n");
		sbFrom.append("   SEC_EXTENDFORM.ExtendEndDate as extendEndDate ,--չ�ں�ͬ������  \n");
		sbFrom.append("   SEC_EXTENDFORM.StatusID as statusId ,--չ�ں�ͬ״̬  \n");
		sbFrom.append("   SEC_EXTENDFORM.NextCheckUserID as nextCheckUserId ,--����ˣ�Ҳ��Ϊ����������״̬  \n");
		sbFrom.append("   SEC_EXTENDFORM.InputUserID as inputUserId ,--¼����  \n");
		sbFrom.append("   SEC_EXTENDFORM.Amount as amount ,--չ�ڽ��  \n");
		sbFrom.append("   SEC_EXTENDFORM.Rate as rate ,--չ������  \n");
	    //sbFrom.append("   sec_applyContract.TransactionDate as transactionDate ,--�ɽ�����(ί������)  \n");
		sbFrom.append("   SEC_EXTENDFORM.Term as term --����  \n");

		sbFrom.append("  from \n");
		sbFrom.append("   SEC_EXTENDFORM ,SEC_BUSINESSTYPE \n");

		sbFrom.append("   where 1=1 \n");
		sbFrom.append("   and substr(SEC_EXTENDFORM.TRANSACTIONTYPEID,0,2) = SEC_BUSINESSTYPE.id \n");
		//		===========�����Ǹ���ҳ�洫�ݵĲ�������������======start====
		//����1��ҵ������ID
		if (businessTypeId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.TRANSACTIONTYPEID = " + businessTypeId + " \n");
		}
		//����2����ʼ��ͬ(ԭ��ͬ)���
		if (startContractId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.applyContractId >= " + startContractId + " \n");
		}
		//����3��������ͬ(ԭ��ͬ)���
		if (endContractId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.applyContractId <= " + endContractId + " \n");
		}
		//����4�����׶���
		//if (counterpartId != -1)
		//{
		//	sbFrom.append(" and sec_applyContract.CounterpartID = "
		//			+ counterpartId + " \n");
		//}
		//����5����ͬ¼�����ڿ�ʼ��
		if (!"".equals(strStartDate))
		{
			sbFrom.append(" and to_char(SEC_EXTENDFORM.InputDate , 'yyyy-mm-dd') >= " + "'" + strStartDate + "'\n");
		}
		//����6����ͬ¼�����ڽ�����
		if (!"".equals(strEndDate))
		{
			sbFrom.append(" and to_char(SEC_EXTENDFORM.InputDate , 'yyyy-mm-dd') <= " + "'" + strEndDate + "'\n");
		}
		//����7����ͬ״̬
		if (statusID != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.StatusID = " + statusID + " \n");
		}
		//����8��¼����
		if (inputUserId != -1)
		{
			sbFrom.append(" and SEC_EXTENDFORM.InputUserID = " + inputUserId + " \n");
		}
		
		//sbFromƴд��������������������������
		sbFrom.append("   )  \n");
		
		sbWhere = new StringBuffer();
		
		sbWhere.append(" ");
		
		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		
		switch ((int) queryParam.getOrderField())
		{
			case OrderBy_ContractCode :
				sbOrderBy.append(" \n order by applyContractId " + strDesc);
				break;
			case OrderBy_ExtendContractCode :
				sbOrderBy.append(" \n order by id " + strDesc);
				break;
			case OrderBy_Amount :
				sbOrderBy.append(" \n order by Amount" + strDesc);
				break;
			case OrderBy_ExtendStartDate :
				sbOrderBy.append(" \n order by extendStartDate " + strDesc);
				break;
			case OrderBy_ExtendEndDate :
				sbOrderBy.append(" \n order by extendEndDate " + strDesc);
				break;
			case OrderBy_Term :
				sbOrderBy.append(" \n order by Term" + strDesc);
				break;
			case OrderBy_Rate :
				sbOrderBy.append(" \n order by Rate " + strDesc);
				break;
			case OrderBy_ContractStatusID :
				sbOrderBy.append(" \n order by StatusID " + strDesc);
				break;
		}
		
	}
	/**
	 * ����PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 * SecuritiesException
	 */
	public PageLoader queryContractInfo(QuerySecuritiesContractParam queryParam) throws SecuritiesException
	{

		getSQL(queryParam);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	
		log.debug("queryContractInfo ==sbOrderBy :" + sbOrderBy.toString());
		
		pageLoader .initPageLoader(
						new AppContext(),
						sbFrom.toString(),
						sbSelect.toString(),
						sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.securities.securitiescontractextend.dataentity.SecuritiesContractExtendInfo",
						null);
		
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		
		return pageLoader;
	}
}