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
     * ͨ�����´����ȡ���´�id
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
     * ͨ���û���ȡ�û�id
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
     * ͨ���ͻ�id��ѯ�ͻ���
     * @param id 
     * @return �п��ܷ���null
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
     * ͨ���ͻ���ȡ�ͻ�id
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
     * ͨ���ͻ���ȡ�ͻ�id
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
     * ͨ���ͻ�Idȡ�ͻ�����
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
     * ͨ���˻�����ȡ�˻�����id
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
     * ͨ������ȡ��������id
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
     * ͨ�������ͬ�Ż�ȡ��ͬid
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
     * ͨ���˻��Ż�ȡ�˻�id
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
     * ͨ�������кŻ�ȡ������id
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
     * ͨ�������ͬ�Ż�ȡ��ͬid
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
     * ͨ�����������ͻ�ȡ����������ID
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
     * ͨ���ͻ�idȡ�ϼ���λid
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
     * ȡ������ӵ��ָ��������Ĺ�����ı���
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
     * ͨ�������˻�idȡ�����˻���
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