/*
 * Created on 2004-4-22
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryTransactionTypeParam;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
//import com.iss.itreasury.util.DataFormat
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author huanwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryTransactionTypeBean {

    protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

    private StringBuffer sbSelect = null;

    private StringBuffer sbFrom = null;

    private StringBuffer sbWhere = null;

    private StringBuffer sbOrderBy = null;

    public final static int orderBy_TransactionTypeID = 1;//�������ͱ��

    public final static int orderBy_Trname = 2;//������������

    public final static int orderBy_Tyname = 3;//ҵ����������

    public final static int orderBy_Aname = 4;//ҵ����������

    public final static int orderBy_CapitalDirection = 5;//�ʽ��ո�����

    public final static int orderBy_IsUpdateStock = 6;//���¿��

    public final static int orderBy_IsUpdateCapital = 7;//�����ʽ����

    public final static int orderBy_DeliveryOrderAttribute = 8;//�������

    /**
     * ���ؽ������Ͳ�ѯ��SQLString���
     * 
     * @param queryParam
     * @return
     */
    private void getSQL(QueryTransactionTypeParam queryParam) {
        sbSelect = new StringBuffer();
        //////////////select//////////////start//////////////////////-
        sbSelect.append("   *  \n");

        //////////////select//////////////end////////////////////////-
        sbFrom = new StringBuffer();
        //////////////from////////////////end////////////////////////-
        sbFrom.append("(     \n");
        sbFrom.append(" select \n");
        sbFrom
                .append("  SEC_TransactionType.ID    transactionTypeID,            "); //1
        sbFrom.append("  SEC_TransactionType.Name    trname,          "); //2
        sbFrom.append("  SEC_BusinessType.Name    tyname,          "); //2
        sbFrom.append("  SEC_BusinessAttribute.Name    aname,          "); //2
        sbFrom
                .append("  decode(SEC_TransactionType.CapitalDirection,0,'���ı��ʽ����',  "); //2
        sbFrom.append("  1,'�գ��ʽ��ʻ���',  "); //2
        sbFrom.append("  2,'�����ʽ��ʻ���',  "); //2
        sbFrom.append("  3,'һ��һ�գ��ʽ��ʻ���',  "); //2
        sbFrom.append("  4,'�����տ�',  "); //2
        sbFrom.append("  5,'���и���',  "); //2
        sbFrom.append("  6,'�ʽ��ʻ��գ����и�',  "); //2
        sbFrom.append("  7,'�ʽ��ʻ�����������')   capitalDirection,  "); //2
        sbFrom.append("  decode(SEC_TransactionType.StockDirection,0,'��',  "); //2
        sbFrom.append("  '��')  isUpdateStock, "); //2
        sbFrom.append("  decode(SEC_TransactionType.CapitalDirection,0,'��',  "); //2
        sbFrom.append("  '��')  isUpdateCapital, "); //2
        sbFrom
                .append("  decode(SEC_TransactionType.DeliveryOrderAttribute,1,'ʵ��',  "); //2
        sbFrom.append("  2,'����')  deliveryOrderAttribute "); //2

        sbFrom.append("  from             "); //12

        sbFrom
                .append("  SEC_TransactionType,SEC_BusinessType,SEC_BusinessAttribute  "); //13
        sbFrom
                .append("  where SEC_TransactionType.BusinessTypeID=SEC_BusinessType.ID            "); //12
        sbFrom
                .append("  and SEC_BusinessAttribute.ID =  SEC_BusinessType.BusinessAttributeID            "); //12

        //����һ��������������
        String[] TNames = queryParam.getTNames();
        if (TNames != null && TNames.length > 0) {
            sbFrom.append(" and SEC_TransactionType.Name in ('");
            for (int i = 0; i < TNames.length - 1; i++) {
                sbFrom.append(TNames[i] + "','");
            }
            sbFrom.append(TNames[TNames.length - 1] + "') \n");
        }

        //��������ҵ����������

        String[] BNames = queryParam.getBNames();
        if (BNames != null && BNames.length > 0) {
            sbFrom.append(" and SEC_BusinessType.Name in ('");
            for (int i = 0; i < BNames.length - 1; i++) {
                sbFrom.append(BNames[i] + "','");
            }
            sbFrom.append(BNames[BNames.length - 1] + "') \n");
        }

        //===========�����Ǹ���ҳ�洫�ݵĲ�������������======end====
        sbFrom.append("   )  \n");

        //////////////from////////////////end////////////////////////-
        sbWhere = new StringBuffer();

        //////////////where//////////////-end////////////////////////-
        sbWhere.append(" ");

        //////////////where//////////////-end////////////////////////-

        sbOrderBy = new StringBuffer();
        String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
        log
                .debug("////queryParam.getDesc() //////////-"
                        + queryParam.getDesc());
        switch ((int) queryParam.getOrderField()) {
        case orderBy_TransactionTypeID:
            sbOrderBy.append(" \n order by transactionTypeID" + strDesc);
            break;
        case orderBy_Trname:
            sbOrderBy.append(" \n order by trname" + strDesc);
            break;
        case orderBy_Tyname:
            sbOrderBy.append(" \n order by tyname" + strDesc);
            break;
        case orderBy_Aname:
            sbOrderBy.append(" \n order by aname" + strDesc);
            break;
        case orderBy_CapitalDirection:
            sbOrderBy.append(" \n order by capitalDirection" + strDesc);
            break;
        case orderBy_IsUpdateStock:
            sbOrderBy.append(" \n order by isUpdateStock" + strDesc);
            break;
        case orderBy_IsUpdateCapital:
            sbOrderBy.append(" \n order by isUpdateCapital" + strDesc);
            break;
        case orderBy_DeliveryOrderAttribute:
            sbOrderBy.append(" \n order by deliveryOrderAttribute" + strDesc);
            break;

        }
        log.debug("////////////////////////////////////////-");
    }
	/**
	 * �������Ͳ�ѯ
	 * @param queryParam
	 */
    private void getSQL2(QueryTransactionTypeParam queryParam) {
        sbSelect = new StringBuffer();
        //////////////select//////////////start//////////////////////-
        sbSelect.append("   *  \n");

        //////////////select//////////////end////////////////////////-
        sbFrom = new StringBuffer();
        //////////////from////////////////end////////////////////////-
        sbFrom.append("(     \n");
        sbFrom.append(" select \n");
        sbFrom
                .append("  SEC_TransactionType.ID    transactionTypeID,            "); //1
        sbFrom.append("  SEC_TransactionType.Name    trname,          "); //2
        sbFrom.append("  SEC_BusinessType.Name    tyname,          "); //2
        sbFrom.append("  SEC_BusinessAttribute.Name    aname,          "); //2
        sbFrom
                .append("  decode(SEC_TransactionType.CapitalDirection,0,'���ı��ʽ����',  "); //2
        sbFrom.append("  1,'�գ��ʽ��ʻ���',  "); //2
        sbFrom.append("  2,'�����ʽ��ʻ���',  "); //2
        sbFrom.append("  3,'һ��һ�գ��ʽ��ʻ���',  "); //2
        sbFrom.append("  4,'�����տ�',  "); //2
        sbFrom.append("  5,'���и���',  "); //2
        sbFrom.append("  6,'�ʽ��ʻ��գ����и�',  "); //2
        sbFrom.append("  7,'�ʽ��ʻ�����������')   capitalDirection,  "); //2
        sbFrom.append("  decode(SEC_TransactionType.StockDirection,0,'��',  "); //2
        sbFrom.append("  '��')  isUpdateStock, "); //2
        sbFrom.append("  decode(SEC_TransactionType.CapitalDirection,0,'��',  "); //2
        sbFrom.append("  '��')  isUpdateCapital, "); //2
        sbFrom
                .append("  decode(SEC_TransactionType.DeliveryOrderAttribute,1,'ʵ��',  "); //2
        sbFrom.append("  2,'����')  deliveryOrderAttribute "); //2

        sbFrom.append("  from             "); //12

        sbFrom
                .append("  SEC_TransactionType,SEC_BusinessType,SEC_BusinessAttribute  "); //13
        sbFrom
                .append("  where SEC_TransactionType.BusinessTypeID=SEC_BusinessType.ID            "); //12
        sbFrom
                .append("  and SEC_BusinessAttribute.ID =  SEC_BusinessType.BusinessAttributeID            "); //12

        //����һ��������������
        StringBuffer condition1=new StringBuffer();
        StringBuffer condition2=new StringBuffer();
        
        String[] TNames = queryParam.getTNames();
        if (TNames != null && TNames.length > 0) {
            condition1.append(" SEC_TransactionType.id in ('");
            for (int i = 0; i < TNames.length - 1; i++) {
            	condition1.append(TNames[i] + "','");
            }
            condition1.append(TNames[TNames.length - 1] + "') \n");
        }

        //��������ҵ����������

        String[] BNames = queryParam.getBNames();
        if (BNames != null && BNames.length > 0) {
            condition2.append(" SEC_BusinessType.id in ('");
            for (int i = 0; i < BNames.length - 1; i++) {
            	condition2.append(BNames[i] + "','");
            }
            condition2.append(BNames[BNames.length - 1] + "') \n");
        }
        
        if (condition1.length()>0 && condition2.length()>0){
        	sbFrom.append(" and ("+condition1+" or "+condition2+" ) ");
        }
        else if(condition1.length()>0){
        	sbFrom.append(" and "+condition1);
        	
        }
        else if(condition2.length()>0){
        	sbFrom.append(" and "+condition2);
        }

        //===========�����Ǹ���ҳ�洫�ݵĲ�������������======end====
        sbFrom.append("   )  \n");

        //////////////from////////////////end////////////////////////-
        sbWhere = new StringBuffer();

        //////////////where//////////////-end////////////////////////-
        sbWhere.append(" ");

        //////////////where//////////////-end////////////////////////-

        sbOrderBy = new StringBuffer();
        String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
        log
                .debug("////queryParam.getDesc() //////////-"
                        + queryParam.getDesc());
        switch ((int) queryParam.getOrderField()) {
        case orderBy_TransactionTypeID:
            sbOrderBy.append(" \n order by transactionTypeID" + strDesc);
            break;
        case orderBy_Trname:
            sbOrderBy.append(" \n order by trname" + strDesc);
            break;
        case orderBy_Tyname:
            sbOrderBy.append(" \n order by tyname" + strDesc);
            break;
        case orderBy_Aname:
            sbOrderBy.append(" \n order by aname" + strDesc);
            break;
        case orderBy_CapitalDirection:
            sbOrderBy.append(" \n order by capitalDirection" + strDesc);
            break;
        case orderBy_IsUpdateStock:
            sbOrderBy.append(" \n order by isUpdateStock" + strDesc);
            break;
        case orderBy_IsUpdateCapital:
            sbOrderBy.append(" \n order by isUpdateCapital" + strDesc);
            break;
        case orderBy_DeliveryOrderAttribute:
            sbOrderBy.append(" \n order by deliveryOrderAttribute" + strDesc);
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
    public PageLoader queryTransactionTypeInfo(
            QueryTransactionTypeParam queryParam) throws SecuritiesException {

        getSQL2(queryParam);
        //
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
                .getBaseObject("com.iss.system.dao.PageLoader");

        //log.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
        pageLoader
                .initPageLoader(
                        new AppContext(),
                        sbFrom.toString(),
                        sbSelect.toString(),
                        sbWhere.toString(),
                        (int) Constant.PageControl.CODE_PAGELINECOUNT,
                        "com.iss.itreasury.securities.query.dataentity.QueryTransactionTypeInfo",
                        null);

        pageLoader.setOrderBy(" ");
        return pageLoader;
    }
}
