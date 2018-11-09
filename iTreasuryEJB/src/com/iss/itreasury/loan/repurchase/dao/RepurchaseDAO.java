package com.iss.itreasury.loan.repurchase.dao;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.iss.itreasury.loan.exception.LoanDAOException;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.repurchase.dataentity.RepurchaseFormInfo;
import com.iss.itreasury.loan.repurchase.dataentity.RepurchaseItemInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 * @author shantao
 * 
 * @version 1.0 2005-12-27
 */
public class RepurchaseDAO extends LoanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

    public RepurchaseDAO()
    {
        super("loan_repurchaseform");
        super.setUseMaxID();
    }

    public void addRelContract(long id, String[] contractID) throws Exception
    {
        this.initDAO();
        String sql = "insert into loan_repurchaseitem values(?,?)";
        PreparedStatement ps = this.prepareStatement(sql);
        for (int i = 0; i < contractID.length; i++)
        {
            ps.setLong(1, id);
            ps.setLong(2, Long.parseLong(contractID[i]));
            ps.addBatch();
        }
        ps.executeBatch();

    }
    
    public void delete(String[] id)throws Exception
    {
        this.initDAO();
        StringBuffer sql = new StringBuffer();
        sql.append("update loan_repurchaseform set statusid=" + LOANConstant.RepurchaseStatus.CANCEL);
        if(id.length>0)
            sql.append(" where id="+id[0].trim());
        for(int i=1;i<id.length;i++)
        {
            sql.append(" or id=" + id[i].trim());
        }
        this.prepareStatement(sql.toString());
        this.executeUpdate();
    }
    
    public void repurchase(String[] id)throws Exception
    {
        this.initDAO();
        StringBuffer sql = new StringBuffer();
        sql.append("update loan_repurchaseform set statusid=" + LOANConstant.RepurchaseStatus.FINISH);
        sql.append(" , repurchasedate=?");
        sql.append(" where id="+id[0].trim());
        for(int i=1;i<id.length;i++)
        {
            sql.append(" or id=" + id[i].trim());
        }

        PreparedStatement ps = this.prepareStatement(sql.toString());
        Timestamp ts = Env.getSystemDateTime();
        ps.setTimestamp(1,ts);
        this.executeUpdate();
    }

    public List findRelByID(long id) throws Exception
    {
        List result = new ArrayList();
        RepurchaseItemInfo info = new RepurchaseItemInfo();
        ResultSet rs = null;
        try
        {
            RepurchaseFormInfo formInfo = (RepurchaseFormInfo) super.findByID(id,
                    RepurchaseFormInfo.class);
            result.add(formInfo);

            this.initDAO();
            //拼凑查询语句
            StringBuffer buffer = new StringBuffer("");
            buffer.append(" select a.id,a.scontractcode,b.sname as nBorrowClientName,");
            buffer.append(" a.ntypeid,a.dtstartdate,a.dtenddate,a.mexamineamount");
            buffer.append(" from loan_contractform a,client b,loan_repurchaseitem c");
            buffer.append(" where a.nborrowclientid=b.id ");
            buffer.append(" and a.id=c.contractid ");
            buffer.append(" and c.repurchaseid=" + id);

            String sql = buffer.toString();

            log4j.debug(sql);

            this.prepareStatement(sql);
            rs = this.executeQuery();

            ContractOperation tmp = new ContractOperation();
            while (rs.next())
            {
                info = new RepurchaseItemInfo();
                //                info.setId(rs.getLong("id"));
                info.setContractID(rs.getLong("id"));
                info.setSContractCode(rs.getString("scontractcode"));
                info.setNBorrowClientName(rs.getString("nBorrowClientName"));
                info.setTypeName(LOANConstant.LoanType.getName(rs.getLong("ntypeid")));
                info.setDtStartDate(rs.getTimestamp("dtstartdate"));
                info.setDtEndDate(rs.getTimestamp("dtenddate"));
                info.setMLoanAmount(rs.getDouble("mexamineamount"));
                info.setBalanceAmount(tmp.getLateAmount(info.getContractID())
                        .getBalanceAmount());
                result.add(info);
            }
        }
        catch (Exception e)
        {
            throw new LoanDAOException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                this.finalizeDAO();
            }
            catch (ITreasuryDAOException se)
            {
                throw new LoanDAOException(se.getMessage(), se);
            }
        }
        return result;
    }

    public RepurchaseFormInfo[] findRepByCon(Map condition) throws Exception
    {
        List result = new ArrayList();
        RepurchaseFormInfo info ;
        String operatorID = (String) condition.get("inputUserID"); //当前操作客户ID
        String currencyID = (String) condition.get("currencyID"); //当前系统使用币种ID
        String officeID = (String) condition.get("officeID"); //办事处ID
//        long statusID = Long.parseLong((String)condition.get("statusID"));
        long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
        long orderParam = 1;
        if(condition.get("desc")!=null)
            desc = Long.parseLong((String)condition.get("desc"));
        if(condition.get("orderParam")!=null)
            orderParam = Long.parseLong((String)condition.get("orderParam"));

        ResultSet rs = null;
        try
        {
            this.initDAO();
            //拼凑查询语句
            StringBuffer buffer = new StringBuffer("");

            buffer.append(" select id,code,bankid,amount,rate,startdate,");
            buffer.append(" enddate,statusid from loan_repurchaseform");
            buffer.append(" where officeid=" + officeID);
            buffer.append(" and currencyid=" + currencyID);
            buffer.append(" and inputuserid=" + operatorID);
            buffer.append(" and statusid=" + LOANConstant.RepurchaseStatus.SUBMIT);
            if(condition.get("amountStart")!=null)
                buffer.append(" and amount>=" + Double.parseDouble((String)condition.get("amountStart")));
            if(condition.get("amountEnd")!=null)
                buffer.append(" and amount<=" + Double.parseDouble((String)condition.get("amountEnd")));
            if(condition.get("bankID")!=null && Long.parseLong((String)condition.get("bankID"))!=-1)
                buffer.append(" and bankID=" + Long.parseLong((String)condition.get("bankID")));
            if(condition.get("code")!=null)
                buffer.append(" and code like '%" + (String)condition.get("code") + "%'");
            if(condition.get("startDate")!=null)
                buffer.append(" and enddate>=?");
            if(condition.get("endDate")!=null)
                buffer.append(" and enddate<=?");
            switch ((int) orderParam)
			{
				case 1 :
				    buffer.append(" order by code ");
					break;
				case 2 :
				    buffer.append(" order by bankid ");
					break;
				case 3 :
				    buffer.append(" order by amount ");
					break;
				case 4 :
				    buffer.append(" order by rate ");
					break;
				case 5 :
				    buffer.append(" order by startdate ");
					break;
				case 6 :
				    buffer.append(" order by enddate ");
					break;
				case 7 :
				    buffer.append(" order by statusid ");
					break;
				default :
				    buffer.append(" order by code ");
			}

			if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
			    buffer.append(" desc");
			}
            
            String sql = buffer.toString();
            log4j.debug(sql);
            
            int i=1;
            PreparedStatement ps = this.prepareStatement(sql);
            if(condition.get("startDate")!=null)
            {
                ps.setTimestamp(i, (Timestamp) condition.get("startDate"));
                i++;
            }
            if(condition.get("endDate")!=null)
                ps.setTimestamp(i, (Timestamp) condition.get("endDate"));
            rs = this.executeQuery();

            while (rs.next())
            {
                info = new RepurchaseFormInfo();
                info.setId(rs.getLong("id"));
                info.setCode(rs.getString("code"));
                info.setBankID(rs.getLong("bankid"));
                info.setAmount(rs.getDouble("amount"));
                info.setRate(rs.getDouble("rate"));
                info.setStartDate(rs.getTimestamp("startdate"));
                info.setEndDate(rs.getTimestamp("enddate"));
                info.setStatusID(rs.getLong("statusid"));
                result.add(info);
            }
        }
        catch (Exception e)
        {
            throw new LoanDAOException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                this.finalizeDAO();
            }
            catch (ITreasuryDAOException se)
            {
                throw new LoanDAOException(se.getMessage(), se);
            }
        }

        return (RepurchaseFormInfo[]) result.toArray(new RepurchaseFormInfo[0]);
    }

    public RepurchaseItemInfo[] searchContractByCondition(Map condition) throws Exception
    {
        RepurchaseItemInfo info;
        ArrayList result = new ArrayList();
        String operatorID = (String) condition.get("inputUserID"); //当前操作客户ID
        String currencyID = (String) condition.get("currencyID"); //当前系统使用币种ID
        String officeID = (String) condition.get("officeID"); //办事处ID
        Timestamp startDate = (Timestamp) condition.get("startDate");
        Timestamp endDate = (Timestamp) condition.get("endDate");
        long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
        long orderParam = 1;
        if(condition.get("desc")!=null)
            desc = Long.parseLong((String)condition.get("desc"));
        if(condition.get("orderParam")!=null)
            orderParam = Long.parseLong((String)condition.get("orderParam"));
        
        ResultSet rs = null;
        try
        {
            this.initDAO();
            StringBuffer buffer = new StringBuffer("");
            //拼凑查询语句
            buffer.append(" select a.id,a.scontractcode,b.sname as nBorrowClientName,");
            buffer.append(" a.ntypeid,a.dtstartdate,a.dtenddate,a.mexamineamount");
            buffer.append(" from loan_contractform a,client b");
            buffer.append(" where a.nborrowclientid=b.id");
            buffer.append(" and a.ncurrencyid=" + currencyID);
            buffer.append(" and a.nofficeid=" + officeID);
            buffer.append(" and (a.nstatusid=" + LOANConstant.ContractStatus.ACTIVE);
            buffer.append(" or a.nstatusid=" + LOANConstant.ContractStatus.EXTEND + ")");
            buffer.append(" and a.id not in(");
            buffer
                    .append(" select c.contractid from loan_repurchaseitem c,loan_repurchaseform d");
            buffer.append(" where c.repurchaseid=d.id ");
            buffer.append(" and d.statusid=" + LOANConstant.RepurchaseStatus.SUBMIT);
            buffer.append(" )");
            buffer.append(" and a.dtstartdate<?");
            buffer.append(" and a.dtenddate>?");
            switch ((int) orderParam)
			{
				case 1 :
				    buffer.append(" order by a.scontractcode ");
					break;
				case 2 :
				    buffer.append(" order by b.sname ");
					break;
				case 3 :
				    buffer.append(" order by a.ntypeid ");
					break;
				case 4 :
				    buffer.append(" order by a.dtstartdate ");
					break;
				case 5 :
				    buffer.append(" order by a.dtenddate ");
					break;
				case 6 :
				    buffer.append(" order by a.mexamineamount ");
					break;
				default :
				    buffer.append(" order by a.scontractcode ");
			}

			if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
			    buffer.append(" desc");
			}

            String sql = buffer.toString();
            log4j.debug(sql);

            PreparedStatement ps = this.prepareStatement(sql);
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            rs = this.executeQuery();

            ContractOperation tmp = new ContractOperation();
            while (rs.next())
            {
                info = new RepurchaseItemInfo();
                //                info.setId(rs.getLong("id"));
                info.setContractID(rs.getLong("id"));
                info.setSContractCode(rs.getString("scontractcode"));
                info.setNBorrowClientName(rs.getString("nBorrowClientName"));
                info.setTypeName(LOANConstant.LoanType.getName(rs.getLong("ntypeid")));
                info.setDtStartDate(rs.getTimestamp("dtstartdate"));
                info.setDtEndDate(rs.getTimestamp("dtenddate"));
                info.setMLoanAmount(rs.getDouble("mexamineamount"));
                info.setBalanceAmount(tmp.getLateAmount(info.getContractID())
                        .getBalanceAmount());
                result.add(info);
            }
        }
        catch (Exception e)
        {
            throw new LoanDAOException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                this.finalizeDAO();
            }
            catch (ITreasuryDAOException se)
            {
                throw new LoanDAOException(se.getMessage(), se);
            }
        }

        return (RepurchaseItemInfo[]) result.toArray(new RepurchaseItemInfo[0]);
    }
    public boolean isExist(String id) throws Exception
    {	
        boolean flag = false;
        ResultSet rs = null;
        try
        {
            this.initDAO();
            //拼凑查询语句
            StringBuffer buffer = new StringBuffer("");
            buffer.append(" select count(*) as num from loan_repurchaseform");
            buffer.append(" where code='" + id + "'");

            String sql = buffer.toString();

            log4j.debug(sql);

            this.prepareStatement(sql);
            rs = this.executeQuery();

            if(rs.next())
            {
                if(rs.getLong("num")>0)
                    flag = true;
            }
        }
        catch (Exception e)
        {
            throw new LoanDAOException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                this.finalizeDAO();
            }
            catch (ITreasuryDAOException se)
            {
                throw new LoanDAOException(se.getMessage(), se);
            }
        }
        return flag;
    }
}