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

    public final static int orderBy_TransactionTypeID = 1;//交易类型编号

    public final static int orderBy_Trname = 2;//交易类型名称

    public final static int orderBy_Tyname = 3;//业务类型名称

    public final static int orderBy_Aname = 4;//业务性质名称

    public final static int orderBy_CapitalDirection = 5;//资金收付方向

    public final static int orderBy_IsUpdateStock = 6;//更新库存

    public final static int orderBy_IsUpdateCapital = 7;//更新资金余额

    public final static int orderBy_DeliveryOrderAttribute = 8;//交割单性质

    /**
     * 返回交易类型查询的SQLString语句
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
                .append("  decode(SEC_TransactionType.CapitalDirection,0,'不改变资金余额',  "); //2
        sbFrom.append("  1,'收（资金帐户）',  "); //2
        sbFrom.append("  2,'付（资金帐户）',  "); //2
        sbFrom.append("  3,'一付一收（资金帐户）',  "); //2
        sbFrom.append("  4,'银行收款',  "); //2
        sbFrom.append("  5,'银行付款',  "); //2
        sbFrom.append("  6,'资金帐户收，银行付',  "); //2
        sbFrom.append("  7,'资金帐户付，银行收')   capitalDirection,  "); //2
        sbFrom.append("  decode(SEC_TransactionType.StockDirection,0,'否',  "); //2
        sbFrom.append("  '是')  isUpdateStock, "); //2
        sbFrom.append("  decode(SEC_TransactionType.CapitalDirection,0,'否',  "); //2
        sbFrom.append("  '是')  isUpdateCapital, "); //2
        sbFrom
                .append("  decode(SEC_TransactionType.DeliveryOrderAttribute,1,'实际',  "); //2
        sbFrom.append("  2,'虚拟')  deliveryOrderAttribute "); //2

        sbFrom.append("  from             "); //12

        sbFrom
                .append("  SEC_TransactionType,SEC_BusinessType,SEC_BusinessAttribute  "); //13
        sbFrom
                .append("  where SEC_TransactionType.BusinessTypeID=SEC_BusinessType.ID            "); //12
        sbFrom
                .append("  and SEC_BusinessAttribute.ID =  SEC_BusinessType.BusinessAttributeID            "); //12

        //条件一：交易类型名称
        String[] TNames = queryParam.getTNames();
        if (TNames != null && TNames.length > 0) {
            sbFrom.append(" and SEC_TransactionType.Name in ('");
            for (int i = 0; i < TNames.length - 1; i++) {
                sbFrom.append(TNames[i] + "','");
            }
            sbFrom.append(TNames[TNames.length - 1] + "') \n");
        }

        //条件二：业务类型名称

        String[] BNames = queryParam.getBNames();
        if (BNames != null && BNames.length > 0) {
            sbFrom.append(" and SEC_BusinessType.Name in ('");
            for (int i = 0; i < BNames.length - 1; i++) {
                sbFrom.append(BNames[i] + "','");
            }
            sbFrom.append(BNames[BNames.length - 1] + "') \n");
        }

        //===========以下是根据页面传递的参数决定的条件======end====
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
	 * 交易类型查询
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
                .append("  decode(SEC_TransactionType.CapitalDirection,0,'不改变资金余额',  "); //2
        sbFrom.append("  1,'收（资金帐户）',  "); //2
        sbFrom.append("  2,'付（资金帐户）',  "); //2
        sbFrom.append("  3,'一付一收（资金帐户）',  "); //2
        sbFrom.append("  4,'银行收款',  "); //2
        sbFrom.append("  5,'银行付款',  "); //2
        sbFrom.append("  6,'资金帐户收，银行付',  "); //2
        sbFrom.append("  7,'资金帐户付，银行收')   capitalDirection,  "); //2
        sbFrom.append("  decode(SEC_TransactionType.StockDirection,0,'否',  "); //2
        sbFrom.append("  '是')  isUpdateStock, "); //2
        sbFrom.append("  decode(SEC_TransactionType.CapitalDirection,0,'否',  "); //2
        sbFrom.append("  '是')  isUpdateCapital, "); //2
        sbFrom
                .append("  decode(SEC_TransactionType.DeliveryOrderAttribute,1,'实际',  "); //2
        sbFrom.append("  2,'虚拟')  deliveryOrderAttribute "); //2

        sbFrom.append("  from             "); //12

        sbFrom
                .append("  SEC_TransactionType,SEC_BusinessType,SEC_BusinessAttribute  "); //13
        sbFrom
                .append("  where SEC_TransactionType.BusinessTypeID=SEC_BusinessType.ID            "); //12
        sbFrom
                .append("  and SEC_BusinessAttribute.ID =  SEC_BusinessType.BusinessAttributeID            "); //12

        //条件一：交易类型名称
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

        //条件二：业务类型名称

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

        //===========以下是根据页面传递的参数决定的条件======end====
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
     * 生成PageLoader
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
