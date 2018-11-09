/*
 * Created on 2005-12-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.query.dao;

import java.util.Map;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.loan.query.dataentity.RepurchaseFormWhereInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class QueryRepurchaseFormDao extends LoanDAO {

    private StringBuffer strSqlSelect = null;

    private StringBuffer strSqlFrom = null;

    private StringBuffer strSqlWhere = null;

    private StringBuffer strSqlOrderBy = null;

    public QueryRepurchaseFormDao() {
        super("");
        // TODO Auto-generated constructor stub
    }

    /**
     * 返回排序的sql语句，用作pageloader的参数
     * 
     * @param wInfo
     * @return
     */
    public String getOrderBySql(RepurchaseFormWhereInfo wInfo) {
        String sLDesc = "";
        if (wInfo.getLDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
            sLDesc = "desc \n";
        }
        String sOrderBy = null;
        switch ((int) wInfo.getOrderBySign()) {
        case 1: {
            sOrderBy = " \n order by code \n";
            break;
        }
        case 2: {
            sOrderBy = " \n order by bankId \n";
            break;
        }
        case 3: {
            sOrderBy = " \n order by amount \n";
            break;
        }
        case 4: {
            sOrderBy = " \n order by rate \n";
            break;
        }
        case 5: {
            sOrderBy = " \n order by repurchaseDate \n";
            break;
        }
        case 6: {
            sOrderBy = " \n order by startDate \n";
            break;
        }
        case 7: {
            sOrderBy = " \n order by endDate \n";
            break;
        }
        case 8: {
            sOrderBy = " \n order by statusId \n";
            break;
        }
        default: {
            sOrderBy = "";
        }
        }
        return sOrderBy + sLDesc;
    }

    private void getSqlToQueryRepurchaseForm(RepurchaseFormWhereInfo wInfo) {
        strSqlSelect = new StringBuffer();
        strSqlSelect.append(" \n id,code,");
        strSqlSelect.append("bankId,amount,rate, \n");
        strSqlSelect.append("startDate,endDate,statusId, \n");
        strSqlSelect.append("repurchaseDate \n");
        strSqlFrom = new StringBuffer();
        strSqlFrom.append(" \n loan_RepurchaseForm \n");
        strSqlWhere = new StringBuffer();
        strSqlWhere.append(" \n 1=1 \n");
        Map map = wInfo.gainAllUsedFieldsAndValue();
        if (map.containsKey("id") && wInfo.getId() != -1) {
            strSqlWhere.append("and id='" + wInfo.getId() + "' \n");
        }
        if (map.containsKey("bankId") && wInfo.getBankId() != -1) {
            strSqlWhere.append("and bankId=" + wInfo.getBankId() + " \n");
        }
        if (map.containsKey("amountFrom")) {
            strSqlWhere.append("and amount>=" + wInfo.getAmountFrom() + " \n");
        }
        if (map.containsKey("amountTo") && wInfo.getAmountTo() > 0) {
            strSqlWhere.append("and amount<=" + wInfo.getAmountTo() + " \n");
        }
        if (map.containsKey("repurchaseDateFrom")) {
            strSqlWhere.append("and to_char(endDate,'yyyy-mm-dd')>="
                    + DataFormat.getDateString(wInfo.getRepurchaseDateFrom())
                    + " \n");
        }
        if (map.containsKey("repurchaseDateTo")) {
            strSqlWhere.append("and to_char(endDate,'yyyy-mm-dd')<="
                    + DataFormat.getDateString(wInfo.getRepurchaseDateTo())
                    + " \n");
        }
        if (map.containsKey("statusId") && wInfo.getStatusId() != -1) {
            strSqlWhere.append("and statusId=" + wInfo.getStatusId() + " \n");
        }
        strSqlOrderBy = new StringBuffer();
        strSqlOrderBy.append(" \n order by id" + " \n");
    }

    /**
     * 
     * @param wInfo
     * @return
     */
    public PageLoader QueryRepurchaseForm(RepurchaseFormWhereInfo wInfo) {
        getSqlToQueryRepurchaseForm(wInfo);
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
                .getBaseObject("com.iss.system.dao.PageLoader");
        pageLoader.initPageLoader(new AppContext(), strSqlFrom.toString(),
                strSqlSelect.toString(), strSqlWhere.toString(),
                (int) Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.loan.query.dataentity.RepurchaseFormInfo",
                null);
        pageLoader.setOrderBy(strSqlOrderBy.toString());
        return pageLoader;
    }
    
    private void getSqlToQuerySumAmountRepurchaseForm(RepurchaseFormWhereInfo wInfo){
        getSqlToQueryRepurchaseForm(wInfo);
        strSqlSelect = new StringBuffer();
        strSqlSelect.append(" \n nvl(sum(amount),0) \n");
     }
    
    /**
     * 
     * @param wInfo
     * @return
     * @throws Exception
     */
    public double QuerySumAmountOfRepurchaseForm(RepurchaseFormWhereInfo wInfo) throws Exception{
        getSqlToQuerySumAmountRepurchaseForm(wInfo);
        double result=0.0;
        String strSql="select "+this.strSqlSelect.toString()+" from "+this.strSqlFrom.toString()+" where "+this.strSqlWhere.toString();
        try {
            this.initDAO();
            this.prepareStatement(strSql);
            this.executeQuery();
            this.transRS.next();
            result=this.transRS.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally{
            this.finalizeDAO();
        }
        return result;
    }
    
}