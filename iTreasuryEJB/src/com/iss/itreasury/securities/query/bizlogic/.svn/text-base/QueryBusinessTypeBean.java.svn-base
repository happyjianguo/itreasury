/*
 * Created on 2004-4-21
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.QueryBusinessTypeParam;
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
public class QueryBusinessTypeBean {

    protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);

    private StringBuffer sbSelect = null;

    private StringBuffer sbFrom = null;

    private StringBuffer sbWhere = null;

    private StringBuffer sbOrderBy = null;

    public final static int orderBy_BusinessTypeCode = 1;//业务类型编号

    public final static int orderBy_BusinessTypeName = 2;//业务类型名称

    public final static int orderBy_BusinessAttributeName = 3;//业务性质名称

    /**
     * 返回业务类型查询的SQLString语句
     * 
     * @param queryParam
     * @return
     */
    private void getSQL(QueryBusinessTypeParam queryParam) {
        sbSelect = new StringBuffer();
        //////////////select//////////////start//////////////////////-
        sbSelect.append("   *  \n");

        //////////////select//////////////end////////////////////////-
        sbFrom = new StringBuffer();
        //////////////from////////////////end////////////////////////-
        sbFrom.append("(     \n");
        sbFrom.append(" select \n");
        sbFrom.append("      SEC_BusinessType.code  code,            "); //1
        sbFrom.append("      SEC_BusinessType.name  tname,        "); //2
        sbFrom.append("      SEC_BusinessAttribute.name  aname        "); //2

        sbFrom.append("  from             "); //12

        sbFrom.append("  SEC_BusinessType,SEC_BusinessAttribute  "); //13
        sbFrom.append("  where             ");
        sbFrom
                .append("   SEC_BusinessAttribute.id =  SEC_BusinessType.BusinessAttributeID ");

        //条件一：业务类型编号
        String strIds="";
        
        String[] Codes = queryParam.getCodes();        
        if (Codes != null && Codes.length > 0) {           	        	
            for (int i = 0; i < Codes.length ; i++) {            	
                strIds+=Codes[i] + ",";
            }            
        }

        //条件二：业务类型名称

        String[] Names = queryParam.getNames();
        if (Names != null && Names.length > 0) {        	
            for (int i = 0; i < Names.length ; i++) {
            	strIds+=Names[i] + ",";
            }            
        }
        
        if(!strIds.equals("")){
        	sbFrom.append(" and SEC_BusinessType.code in (");
        	sbFrom.append(strIds.substring(0,strIds.length()-1));
        	
        	sbFrom.append(") \n");
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
        case orderBy_BusinessTypeCode:
            sbOrderBy.append(" \n order by code" + strDesc);
            break;
        case orderBy_BusinessTypeName:
            sbOrderBy.append(" \n order by tname" + strDesc);
            break;
        case orderBy_BusinessAttributeName:
            sbOrderBy.append(" \n order by aname" + strDesc);
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
    public PageLoader queryBusinessTypeInfo(
            QueryBusinessTypeParam queryParam) throws SecuritiesException {

        getSQL(queryParam);
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
                        "com.iss.itreasury.securities.query.dataentity.QueryBusinessTypeInfo",
                        null);

        pageLoader.setOrderBy(" ");
        return pageLoader;
    }
}
