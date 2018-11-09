/*
 * Created on 2006-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class NameRef {
    private static TinyJdbcTemplate jdbcTemplate = new TinyJdbcTemplate();

    private static class GetIdRowCallbackHandler implements RowCallbackHandler {
        public Object processRow(ResultSet rs) throws Exception {
            return new Long(rs.getLong(1));
        }
    }
    
    private static class GetSingleStringQueryResultRowCallbackHandler implements RowCallbackHandler{
        public Object processRow(ResultSet rs) throws Exception {
            return rs.getString(1);
        }
    }

    private static long getIdFromCollection(Collection c) {
        long result = -1;
        if (c != null && c.size() > 0) {
            result = ((Long) c.iterator().next()).longValue();
        }
        return result;
    }

    /**
     * 通过办事处编号取办事处id
     * 
     * @param officeCode
     * @return
     */
    public static long getOfficeIdByOfficeCode(String officeCode) {
        String strSql = "select id from office where sCode='" + officeCode
                + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过用户名取用户id
     * 
     * @param userName
     * @return
     */
    public static long getUserIdByUserName(String userName) {
        String strSql = "select id from userInfo where sName like '%"
                + userName + "%' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }
    
    /**
     * 通过客户id查询客户名
     * @param id 
     * @return 有可能返回null
     */
    public static String getUserNameByUserId(long id){
        String strSql="select sName from userInfo where id="+id+" \n";
        List list=jdbcTemplate.query(strSql,new GetSingleStringQueryResultRowCallbackHandler());
        if(list.size()>0){
            return (String)list.get(0);
        }
        return null;
    }

    /**
     * 通过客户名取客户id
     * 
     * @param clientName
     * @return
     */
    public static long getClientIdByClientName(String clientName) {
        String strSql = "select id from client where sName='" + clientName
                + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过客户号取客户id
     * 
     * @param clientName
     * @return
     */
    public static long getClientIdByClientCode(String clientCode) {
        String strSql = "select id from client where sCode='" + clientCode
                + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过客户Id取客户名称
     * 
     * @param clientId
     * @return
     */
    public static String getClientNameByClientId(long clientId) {
        String strSql = "select sname from client where id='" + clientId
                + "' \n";
        Collection c = jdbcTemplate.query(strSql, new GetSingleStringQueryResultRowCallbackHandler()
        );
        if (c != null && c.size() > 0) {
            return (String) c.iterator().next();
        }
        return "";
    }

    /**
     * 通过账户类型取账户类型id
     * 
     * @param clientName
     * @return
     */
    public static long getAccountTypeIdByAccountType(String accountType) {
        String strSql = "select id from sett_AccountType where sAccountType='"
                + accountType + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过利率取利率设置id
     * 
     * @param rate
     * @return
     */
    public static long getInterestRateIdByRate(double rate) {
        String strSql = "select id from loan_InterestRate where mRate=" + rate
                + " \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过贷款合同号获取合同id
     * 
     * @param clientName
     * @return
     */
    public static long getLoanContractIdByCode(String contractCode) {
        String strSql = "select id from loan_ContractForm where sContractCode='"
                + contractCode + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过账户号获取账户id
     * 
     * @param clientName
     * @return
     */
    public static long getAccountIdByAccountCode(String accountCode) {
        String strSql = "select id from sett_Account where sAccountNo='"
                + accountCode + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过开户行号获取开户行id
     * 
     * @param
     * @return
     */
    public static long getBranchIdByBranchCode(String branchCode) {
        String strSql = "select id from sett_Branch where sCode='" + branchCode
                + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过贷款合同号获取合同id
     * 
     * @param
     * @return
     */
    public static long getContractFormIdByContractCode(String contractCode) {
        String strSql = "select id from loan_ContractForm where sContractCode='"
                + contractCode + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过贷款子类型获取贷款子类型ID
     * 
     * @param
     * @return
     */
    public static long getSubTypeIdBySubLoanType(long loanType,
            String subLoanType) {
        String strSql = "select id from loan_loanTypeSetting where name='"
                + subLoanType + "' \n";
        Collection c = jdbcTemplate
                .query(strSql, new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }

    /**
     * 通过客户id取上级单位id
     * 
     * @param
     * @return
     */
    public static long getParentCorpIdByClientTypeId(long clientId) {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select Client_CorporationInfo.parentCorpId1 \n");
        strSql.append("from Client_CorporationInfo,Client_clientInfo \n");
        strSql
                .append("where Client_CorporationInfo.clientId=Client_clientInfo.id \n");
        strSql.append("and Client_clientInfo.id=" + clientId + " \n");
        Collection c = jdbcTemplate.query(strSql.toString(),
                new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }
    
    /**
     * 取得所有拥有指定工作组的工作表的别名
     * @param workingGroupId
     * @return
     */
    public static String[] getWorkingFormAliasNamesOfAWorkingGroup(long workingGroupId){
        StringBuffer strSql=new StringBuffer();
        strSql.append("select aliasNameInConfigFile from \n");
        strSql.append("sett_TemplateSheetSetting,sett_TemplateGroupSetting \n");
        strSql.append("where sett_TemplateSheetSetting.workingGroupId=sett_TemplateGroupSetting.id \n");
        List list=jdbcTemplate.query(strSql.toString(),new RowCallbackHandler(){
            public Object processRow(ResultSet rs) throws Exception {
                return rs.getString(1);
            }
        });
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 通过银行账户id取银行账户号
     * 
     * @param
     * @return
     */
    public static long getBankCodeByBankId(long bankId) {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select SCODE \n");
        strSql.append("from SETT_BRANCH \n");
        strSql.append(" where id=" + bankId + " \n");
        Collection c = jdbcTemplate.query(strSql.toString(),
                new GetIdRowCallbackHandler());
        return getIdFromCollection(c);
    }
    
}