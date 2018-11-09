/*
 * Created on 2004-11-24
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalance.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.generalledger.dao.sett_GLEntryDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.offbalance.dataentity.OffBalanceInfo;
import com.iss.itreasury.settlement.offbalance.dataentity.OffBalanceParam;
import com.iss.itreasury.settlement.offbalance.exception.OffBalanceDAOException;
import com.iss.itreasury.settlement.offbalance.exception.OffBalanceException;
import com.iss.itreasury.settlement.offbalance.exception.OffBalanceModifiedException;
import com.iss.itreasury.settlement.offbalanceregister.bizlogic.OffBalanceRegister;
import com.iss.itreasury.settlement.offbalanceregister.bizlogic.OffBalanceRegisterHome;
import com.iss.itreasury.settlement.offbalanceregister.dao.OffBalanceRegisterDAO;
import com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo;
import com.iss.itreasury.settlement.offbalanceregister.exception.OffBalanceRegisterException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * Title: iTreasury Description: OffBalanceDAO后台操作的方法 Copyright (c) 2004
 * Company: iSoftStone
 * 
 * @author kewen hu
 * @version Date of Creation 2004-11-23
 */
public class OffBalanceDAO extends ITreasuryDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    /**
     * 交易号
     */
    public final static int ORDER_TRANS_NO = 1;

    private final static String ORDER_TRANS_NO_NAME = "transNo";

    /**
     * 业务类型
     */
    public final static int ORDER_TRANSACTION_TYPE = 2;

    private final static String ORDER_TRANSACTION_TYPE_NAME = "transactionType";

    /**
     * 科目号
     */
    public final static int ORDER_SUBJECT_CODE = 3;

    private final static String ORDER_SUBJECT_CODE_NAME = "subjectCode";

    /**
     * 科目名称
     */
    public final static int ORDER_SUBJECT_NAME = 4;

    private final static String ORDER_SUBJECT_NAME_NAME = "subjectName";

    /**
     * 合同ID
     */
    public final static int ORDER_CONTRACT_ID = 5;

    private final static String ORDER_CONTRACT_ID_NAME = "contractID";

    /**
     * 合同号
     */
    public final static int ORDER_CONTRACT_CODE = 6;

    private final static String ORDER_CONTRACT_CODE_NAME = "contractCode";

    /**
     * 放款通知单ID、贴现凭证ID
     */
    public final static int ORDER_LOAN_NOTE_ID = 7;

    private final static String ORDER_LOAN_NOTE_ID_NAME = "loanNoteID";

    /**
     * 放款通知单号、贴现凭证号
     */
    public final static int ORDER_LOAN_NOTE_CODE = 8;

    private final static String ORDER_LOAN_NOTE_CODE_NAME = "loanNoteCode";

    /**
     * 汇票ID
     */
    public final static int ORDER_BILL_ID = 9;

    private final static String ORDER_BILL_ID_NAME = "billID";

    /**
     * 汇票号
     */
    public final static int ORDER_BILL_CODE = 10;

    private final static String ORDER_BILL_CODE_NAME = "billCode";

    /**
     * 借款客户编号ID
     */
    public final static int ORDER_BORROW_CLIENT_ID = 11;

    private final static String ORDER_BORROW_CLIENT_ID_NAME = "borrowClientID";

    /**
     * 委托客户编号ID
     */
    public final static int ORDER_CONSIGN_CLIENT_ID = 12;

    private final static String ORDER_CONSIGN_CLIENT_ID_NAME = "consignClientID";

    /**
     * 借款人活期账户ID
     */
    public final static int ORDER_LOAN_ACCOUNT_ID = 13;

    private final static String ORDER_LOAN_ACCOUNT_ID_NAME = "loanAccountID";

    /**
     * 委托人活期账户ID
     */
    public final static int ORDER_CONSIGN_ACCOUNT_ID = 14;

    private final static String ORDER_CONSIGN_ACCOUNT_ID_NAME = "consignAccountID";

    /**
     * 交易方向 1—收入、2—付出
     */
    public final static int ORDER_DIRECTION = 15;

    private final static String ORDER_DIRECTION_NAME = "direction";

    /**
     * 执行日期
     */
    public final static int ORDER_EXECUTE_DATE = 16;

    private final static String ORDER_EXECUTE_DATE_NAME = "executeDate";

    /**
     * 托收日期
     */
    public final static int ORDER_CONSIGN_DATE = 17;

    private final static String ORDER_CONSIGN_DATE_NAME = "consignDate";

    /**
     * 保函开立日期
     */
    public final static int ORDER_START_DATE = 18;

    private final static String ORDER_START_DATE_NAME = "startDate";

    /**
     * 担保到期日期、票面到期日
     */
    public final static int ORDER_END_DATE = 19;

    private final static String ORDER_END_DATE_NAME = "endDate";

    /**
     * 汇票种类
     */
    public final static int ORDER_DRAFT_TYPE = 20;

    private final static String ORDER_DRAFT_TYPE_NAME = "draftType";

    /**
     * 担保类型
     */
    public final static int ORDER_ASSURE_TYPE = 21;

    private final static String ORDER_ASSURE_TYPE_NAME = "assureType";

    /**
     * 担保期限
     */
    public final static int ORDER_INTERVAL_NUM = 22;

    private final static String ORDER_INTERVAL_NUM_NAME = "intervalNum";

    /**
     * 是否本地
     */
    public final static int ORDER_IS_LOCAL = 23;

    private final static String ORDER_IS_LOCAL_NAME = "isLocal";

    /**
     * 担保合同金额
     */
    public final static int ORDER_DRAFT_AMOUNT = 24;

    private final static String ORDER_DRAFT_AMOUNT_NAME = "draftAmount";

    /**
     * 保证金金额
     */
    public final static int ORDER_DEPOSIT_AMOUNT = 25;

    private final static String ORDER_DEPOSIT_AMOUNT_NAME = "depositAmount";

    /**
     * 金额
     */
    public final static int ORDER_AMOUNT = 26;

    private final static String ORDER_AMOUNT_NAME = "amount";

    /**
     * 录入人ID
     */
    public final static int ORDER_INPUTUSER_ID = 27;

    private final static String ORDER_INPUTUSER_ID_NAME = "inputUserID";

    /**
     * 录入日期
     */
    public final static int ORDER_INPUT_DATE = 28;

    private final static String ORDER_INPUT_DATE_NAME = "inputDate";

    /**
     * 复核人ID
     */
    public final static int ORDER_CHECKUSER_ID = 29;

    private final static String ORDER_CHECKUSER_ID_NAME = "checkUserID";

    /**
     * 复核日期
     */
    public final static int ORDER_CHECK_DATE = 30;

    private final static String ORDER_CHECK_DATE_NAME = "checkDate";

    /**
     * 修改日期 精确到时分秒
     */
    public final static int ORDER_MODIFY_DATE = 31;

    private final static String ORDER_MODIFY_DATE_NAME = "modifyDate";

    /**
     * 状态 0—已删除、2—已保存、3—已复核
     */
    public final static int ORDER_STATUS_ID = 32;

    private final static String ORDER_STATUS_ID_NAME = "statusID";

    /**
     * 备注
     */
    public final static int ORDER_ABSTRACT = 33;

    private final static String ORDER_ABSTRACT_NAME = "abstract";

    /**
     * OffBalanceRegisterInfo中的客户编号
     */
    public final static int ORDER_CLIENT_ID = 34;

    private final static String ORDER_CLIENT_ID_NAME = "clientID";

    /**
     * OffBalanceRegisterInfo中的担保余额
     */
    public final static int ORDER_BALANCE = 35;

    private final static String ORDER_BALANCE_NAME = "balance";

    /**
     * OffBalanceRegisterInfo中的登记日期
     */
    public final static int ORDER_REGISTEDATE = 36;

    private final static String ORDER_REGISTEDATE_NAME = "registeDate";

    private OffBalanceRegister offBalanceRegister = null;

    private sett_GLEntryDAO sett_glEntryDAO = null;

    public static long sumItems = 0;//笔数(表外业务查询用)

    public static double sumAmount = 0.0;//金额合计(表外业务查询用)

    public static double sumBalance = 0.0;//余额合计(表外业务查询用)

    public static double debitCreditBalance = 0.0;//期初余额(表外业务明细账用)

    public OffBalanceDAO()
    {
        super("sett_offBalance");
        try
        {
            OffBalanceRegisterHome offBalanceRegisterHome = (OffBalanceRegisterHome) EJBHomeFactory.getFactory().lookUpHome(OffBalanceRegisterHome.class);
            offBalanceRegister = (OffBalanceRegister) offBalanceRegisterHome.create();
            sett_glEntryDAO = new sett_GLEntryDAO();
        }
        catch (Exception re)
        {
            re.printStackTrace();
        }
    }

    public OffBalanceDAO(Connection conn)
    {
        super("sett_offBalance", conn);
        try
        {
            OffBalanceRegisterHome offBalanceRegisterHome = (OffBalanceRegisterHome) EJBHomeFactory.getFactory().lookUpHome(OffBalanceRegisterHome.class);
            offBalanceRegister = (OffBalanceRegister) offBalanceRegisterHome.create();
            sett_glEntryDAO = new sett_GLEntryDAO();
        }
        catch (Exception re)
        {
            re.printStackTrace();
        }
    }

    /**
     * 通过ID查找结果集
     * 
     * @param long
     *            id
     * @return OffBalanceInfo
     * @exception throws
     *                OffBalanceException
     */
    public OffBalanceInfo findByID(long id) throws OffBalanceException
    {
        OffBalanceInfo offBalanceInfo = null;
        try
        {
            offBalanceInfo = (OffBalanceInfo) super.findByID(id, OffBalanceInfo.class);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }
        return offBalanceInfo;
    }

    /**
     * 通过条件查找结果集
     * 
     * @param OffBalanceParam
     *            parameter
     * @return Collection
     * @exception throws
     *                OffBalanceException
     */
    public Collection findByCondition(OffBalanceParam parameter) throws OffBalanceException
    {
        String orderByString = " order by \n";
        //拼排序字段
        if (parameter.getOrderByType() > 0)
        {
            orderByString = orderByString + getOrderColumnName((int) parameter.getOrderByType());
        }
        else
        {
            orderByString = orderByString + " transNo ";
        }
        if (parameter.getAscOrDesc() == Constant.PageControl.CODE_ASCORDESC_ASC)
        {
            orderByString = orderByString + " asc ";
        }
        else
        {
            orderByString = orderByString + " desc ";
        }

        Collection collection = null;
        try
        {
            collection = findByCondition(parameter, orderByString);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }
        return collection;

    }

    //for 排序
    public Collection findByCondition(OffBalanceInfo info, String orderByString) throws OffBalanceException
    {

        Collection collection = null;
        try
        {
            collection = super.findByCondition(info, orderByString);
        }
        catch (ITreasuryDAOException e)
        {
            throw new OffBalanceDAOException(e);
        }
        return collection;

    }

    /**
     * 回购型转贴现付出/收入
     * 
     * @param direction
     *            付出/收入
     * @return Collection 结果集
     * @throws ITreasuryDAOException
     */

    public PageLoader findByDirection(long direction,long lOfficeID,long lCurrencyID) throws Exception
    {

        StringBuffer m_sbFrom = new StringBuffer();
        StringBuffer m_sbSelect = new StringBuffer();
        StringBuffer m_sbWhere = new StringBuffer();
        StringBuffer m_sbOrderBy = new StringBuffer();

        m_sbSelect.append(" * \n");
        m_sbFrom.append(" sett_offbalance\n");

        if (direction == SETTConstant.TransactionDirection.INCOME)
        {//只作了收入，还没有作任何付出

            m_sbWhere.append(" transActionType = " + SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE + "\n");
            m_sbWhere.append(" and statusID = " + SETTConstant.TransactionStatus.CHECK + "\n");
            m_sbWhere.append(" and id not in (\n");
            m_sbWhere.append(" select a.id from\n");
            m_sbWhere.append(" (select * from sett_offbalance where transActionType = " + SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE + " and statusID = " + SETTConstant.TransactionStatus.CHECK + ") a,\n");
            m_sbWhere.append(" (select * from sett_offbalance where transActionType = " + SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE + " and statusID = " + SETTConstant.TransactionStatus.CHECK + ") b\n");
            m_sbWhere.append(" where 1 = 1\n");
            m_sbWhere.append(" and a.subjectCode = b.subjectCode\n");
            m_sbWhere.append(" and a.contractID = b.contractID\n");
            m_sbWhere.append(" and a.billID = b.billID\n");
            m_sbWhere.append(" and a.borrowClientID = b.borrowClientID\n");
            m_sbWhere.append(" and a.loanAccountID = b.loanAccountID)\n");
        }
        else
        {//转贴现付出
            m_sbWhere.append(" transActionType = " + SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE + "\n");
            m_sbWhere.append(" and direction = " + SETTConstant.TransactionDirection.PAYOUT + "\n");
            m_sbWhere.append(" and statusID = " + SETTConstant.TransactionStatus.CHECK + "\n");
            m_sbWhere.append(" and consignDate is not null\n");
        }
        m_sbWhere.append(" and OfficeID=" + lOfficeID);
        m_sbWhere.append(" and CurrencyID = " + lCurrencyID);
        m_sbOrderBy.append(" order by consignDate desc");

        //
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.offbalance.dataentity.OffBalanceInfo", null);
        pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
        return pageLoader;
    }

    /**
     * 商业汇票贴现情况查询
     * 
     * @param
     * @return @throws
     *         ITreasuryDAOException
     */
    public PageLoader findByDiscountCondition(OffBalanceRegisterInfo conditionInfo, String strOrderBy) throws Exception
    {
        StringBuffer m_sbFrom = new StringBuffer();
        StringBuffer m_sbSelect = new StringBuffer();
        StringBuffer m_sbWhere = new StringBuffer();
        StringBuffer m_sbOrderBy = new StringBuffer();

        m_sbSelect.append(" a.subjectCode, a.billID, a.clientID, b.loanAccountID, b.draftType, b.endDate, b.isLocal, a.consignDate, a.writeoffDate, a.transno, a.direction, a.statusID , a.amount");
        m_sbFrom.append(" SETT_OFFBALANCEREGISTER a,  SETT_OFFBALANCE b \n");
        m_sbWhere.append(" a.transactionType in (" + SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE + "," + SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE + ")");
        m_sbWhere.append(" and a.transNo = b.transNo ");
        m_sbWhere.append(" and a.OFFICEID = b.OFFICEID ");
        m_sbWhere.append(" and a.CurrencyID = b.CurrencyID ");
        m_sbWhere.append(" and a.OFFICEID = " + conditionInfo.getOfficeID());
        m_sbWhere.append(" and a.CurrencyID = " + conditionInfo.getCurrencyID());
        if (conditionInfo.getStatusID() == 0)//已核销
        {
            m_sbWhere.append(" and a.statusID = 0 and writeOffDate is not null ");
        }
        else if (conditionInfo.getStatusID() == 1)//未核销 待赎回
        {
            m_sbWhere.append(" and a.statusID = 1 and a.direction = " + conditionInfo.getDirection());
        }
        else
        {
            m_sbWhere.append(" and a.statusID in (0,1) ");
        }

        m_sbWhere.append(" and a.consignDate IS NOT NULL ");
        if (conditionInfo.getSubjectCode() != null && !conditionInfo.getSubjectCode().equals(""))
        {
            m_sbWhere.append(" and a.subjectCode = '" + conditionInfo.getSubjectCode() + "' ");
        }
        m_sbOrderBy.append(strOrderBy);

        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo", null);
        pageLoader.setOrderBy(m_sbOrderBy.toString());

        //查询笔数,金额合计 start
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try
        {
            String strSelect = " SELECT sum(a.amount) sumAmount, count(a.transno) sumItems \n";
            con = Database.getConnection();
            String sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
            ps = con.prepareStatement(sql);
            Log.print("@@@@@@@查询笔数,金额合计 start sql=\n" + sql);
            rs = ps.executeQuery();
            OffBalanceDAO.sumAmount = 0.0;
            OffBalanceDAO.sumItems = 0;
            if (rs.next())
            {
                OffBalanceDAO.sumAmount = rs.getDouble("sumAmount");
                OffBalanceDAO.sumItems = rs.getLong("sumItems");
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            con.close();
            con = null;
        }
        catch (Exception exp)
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
            throw exp;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        }
        //查询笔数,金额合计 end

        return pageLoader;
    }

    /**
     * 出保凭函信情况查询
     * 
     * @param
     * @return @throws
     *         ITreasuryDAOException
     */
    public PageLoader findByAssureCondition(OffBalanceRegisterInfo conditionInfo, String strOrderBy) throws Exception
    {
        StringBuffer m_sbFrom = new StringBuffer();
        StringBuffer m_sbSelect = new StringBuffer();
        StringBuffer m_sbWhere = new StringBuffer();
        StringBuffer m_sbOrderBy = new StringBuffer();

        m_sbSelect.append(" a.subjectcode subjectCode, ");//科目号
        m_sbSelect.append(" a.borrowclientid clientID, "); //被担保客户编号id
        m_sbSelect.append(" a.contractid contractID, ");//担保合同号id
        m_sbSelect.append(" a.loanAccountID loanAccountID, "); // 被担保人活期账户id
        m_sbSelect.append(" a.assuretype assureType, "); // 担保类型
        m_sbSelect.append(" a.startdate startDate, "); //担保开始期日
        m_sbSelect.append(" a.depositAmount depositAmount, "); // 保证金金额,
        m_sbSelect.append(" a.intervalNum intervalNum, ");// 担保期限
        m_sbSelect.append(" a.endDate endDate, "); //担保到期日期
        m_sbSelect.append(" sum(b.amount) amount, "); //担保金额
        m_sbSelect.append(" sum(b.balance) balance"); //担保余额
        m_sbFrom.append(" sett_offbalance a ,sett_offbalanceregister b \n");
        m_sbWhere.append(" a.OFFICEID = b.OFFICEID ");
        m_sbWhere.append(" and a.CurrencyID = b.CurrencyID ");
        m_sbWhere.append(" and a.OFFICEID = " + conditionInfo.getOfficeID());
        m_sbWhere.append(" and a.CurrencyID = " + conditionInfo.getCurrencyID());
        m_sbWhere.append(" and a.transno = b.transno(+) ");
        m_sbWhere.append(" and a.transactiontype in ( " + SETTConstant.TransactionType.ASSURE_INCOME_OFFBALANCE + ")");
        if (conditionInfo.getSubjectCode() != null && !conditionInfo.getSubjectCode().trim().equals(""))//已核销
        {
            m_sbWhere.append(" and a.subjectcode = " + conditionInfo.getSubjectCode().trim());
        }
        if (conditionInfo.getClientID() != -1)
        {
            m_sbWhere.append(" and a.borrowclientid = " + conditionInfo.getClientID());
        }
        if (conditionInfo.getContractID() != -1)
        {
            m_sbWhere.append(" and a.contractid = " + conditionInfo.getContractID());
        }
        if (conditionInfo.getAssureType() != -1)
        {
            m_sbWhere.append(" and a.assuretype = " + conditionInfo.getAssureType());
        }
        if (conditionInfo.getEndDate() != null)
        {
            m_sbWhere.append(" and a.enddate = to_date('" + DataFormat.getDateString(conditionInfo.getEndDate()) + "','yyyy-mm-dd')");
        }
        m_sbWhere.append(" group by a.contractid ,a.subjectcode ,a.borrowclientid,a.loanAccountID,a.assuretype,a.startdate,a.depositAmount,a.intervalNum,a.endDate ");
        m_sbWhere.append("having 1=1 ");
        if (conditionInfo.getAssureStatus() != -1)
        {
            if (conditionInfo.getAssureStatus() > 0)
                m_sbWhere.append(" and sum(b.balance) > 0 ");
            else if (conditionInfo.getAssureStatus() == 0)
                m_sbWhere.append(" and sum(b.balance) = 0 ");
        }
        if (conditionInfo.getAmount() > 0)
        {
            m_sbWhere.append(" and sum(b.amount) = " + conditionInfo.getAmount());
        }
        if (conditionInfo.getBalance() > 0)
        {
            m_sbWhere.append(" and sum(b.balance) = " + conditionInfo.getBalance());
        }

        m_sbOrderBy.append(strOrderBy);

        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo", null);
        pageLoader.setOrderBy(m_sbOrderBy.toString());
        //查询笔数,金额合计 start
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try
        {
            String strSelect = " SELECT sum(sum(b.amount)) sumAmount, sum(sum(b.balance)) sumBalance, count(a.contractid) sumItems \n";
            con = Database.getConnection();
            String sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
            ps = con.prepareStatement(sql);
            Log.print("@@@@@@@查询笔数,金额合计 start sql=\n" + sql);
            rs = ps.executeQuery();
            OffBalanceDAO.sumAmount = 0.0;
            OffBalanceDAO.sumBalance = 0.0;
            OffBalanceDAO.sumItems = 0;
            if (rs.next())
            {
                OffBalanceDAO.sumAmount = rs.getDouble("sumAmount");
                OffBalanceDAO.sumBalance = rs.getDouble("sumBalance");
                OffBalanceDAO.sumItems = rs.getLong("sumItems");
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            con.close();
            con = null;
        }
        catch (Exception exp)
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
            throw exp;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        }
        //查询笔数,金额合计 end
        return pageLoader;

    }

    /**
     * 出保凭函信明细情况查询
     * 
     * @param
     * @return @throws
     *         ITreasuryDAOException
     */
    public PageLoader findAssureDetailByContractId(String contractId) throws Exception
    {
        StringBuffer m_sbFrom = new StringBuffer();
        StringBuffer m_sbSelect = new StringBuffer();
        StringBuffer m_sbWhere = new StringBuffer();
        StringBuffer m_sbOrderBy = new StringBuffer();

        m_sbSelect.append(" subjectcode subjectCode, ");//科目号
        m_sbSelect.append(" borrowclientid clientID, "); //被担保客户编号id
        m_sbSelect.append(" contractid contractID, ");//担保合同号id
        m_sbSelect.append(" transno transNo, "); // 交易号
        m_sbSelect.append(" executeDate registeDate ,"); // 登记日期
        m_sbSelect.append(" assureType assureType ,"); // 担保类型
        m_sbSelect.append(" decode(direction,1,amount,2,0) amount ,"); // 担保金额
        m_sbSelect.append(" decode(direction,1,0,2,amount) balance "); // 担保余额
        m_sbFrom.append(" sett_offbalance \n");
        m_sbWhere.append(" 1=1 ");
        m_sbWhere.append(" and statusid = 3 ");
        m_sbWhere.append(" and transactiontype in ( " + SETTConstant.TransactionType.ASSURE_INCOME_OFFBALANCE + "," + SETTConstant.TransactionType.ASSURE_PAYOUT_OFFBALANCE + ")");
        if (contractId != null && !contractId.trim().equals(""))
        {
            m_sbWhere.append(" and contractid = " + contractId.trim());
        }

        m_sbOrderBy.append(" order by transNo desc ");

        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

        pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo", null);
        pageLoader.setOrderBy(m_sbOrderBy.toString());

        return pageLoader;

    }

    /**
     * Method match. 匹配一条记录
     * 
     * @param info
     * @return OffBalanceInfo
     * @throws SettlementDAOException
     */
    public OffBalanceInfo match(OffBalanceInfo info) throws SettlementDAOException
    {
        log.debug("=====开始匹配====");

        OffBalanceInfo rtInfo = null;

        try
        {
            initDAO();

            StringBuffer strSQLBuffer = new StringBuffer();
            strSQLBuffer.append("select * from \n");
            strSQLBuffer.append(strTableName + " \n");
            strSQLBuffer.append(" where \n");
            strSQLBuffer.append(" 1 = 1 \n");
            strSQLBuffer.append(" and officeID="+ info.getOfficeID() +" \n");
            strSQLBuffer.append(" and currencyID="+ info.getCurrencyID() +" \n");
            //交易号
            if (info.getTransNo() != null && info.getTransNo().length() > 0)
            {
                strSQLBuffer.append("\n AND transNo = '" + info.getTransNo() + "' ");
            }
            //业务类型
            if (info.getTransactionType() > -1)
            {
                strSQLBuffer.append("\n AND transactionType = " + info.getTransactionType());
            }
            //科目号
            if (info.getSubjectCode() != null && info.getSubjectCode().length() > 0)
            {
                strSQLBuffer.append("\n AND subjectCode = '" + info.getSubjectCode() + "' ");
            }
            //contractID Number 合同ID
            if (info.getContractID() > -1)
            {
                strSQLBuffer.append("\n AND contractID = " + info.getContractID());
            }
            //loanNoteID Number 放款通知单ID、贴现凭证ID
            if (info.getLoanNoteID() > -1)
            {
                strSQLBuffer.append("\n AND loanNoteID = " + info.getLoanNoteID());
            }
            //billID Number 汇票ID
            if (info.getBillID() > -1)
            {
                strSQLBuffer.append("\n AND billID = " + info.getBillID());
            }
            //borrowClientID Number 借款客户编号ID
            if (info.getBorrowClientID() > -1)
            {
                strSQLBuffer.append("\n AND borrowClientID = " + info.getBorrowClientID());
            }
            //consignClientID Number 委托客户编号ID
            if (info.getConsignClientID() > -1)
            {
                strSQLBuffer.append("\n AND consignClientID = " + info.getConsignClientID());
            }
            //loanAccountID Number 借款人活期账户ID
            if (info.getLoanAccountID() > -1)
            {
                strSQLBuffer.append("\n AND loanAccountID = " + info.getLoanAccountID());
            }
            //consignAccountID Number 委托人活期账户ID
            if (info.getConsignAccountID() > -1)
            {
                strSQLBuffer.append("\n AND consignAccountID = " + info.getConsignAccountID());
            }
            //direction Number 交易方向 1—收入、2—付出
            if (info.getDirection() > -1)
            {
                strSQLBuffer.append("\n AND direction = " + info.getDirection());
            }
            //executeDate DATE 执行日期
            if (info.getExecuteDate() != null)
            {
                strSQLBuffer.append("\n AND executeDate = to_date('" + DataFormat.getDateString(info.getExecuteDate()) + "','yyyy-mm-dd')");
            }
            //consignDate DATE 托收日期
            if (info.getConsignDate() != null)
            {
                strSQLBuffer.append("\n AND consignDate = to_date('" + DataFormat.getDateString(info.getConsignDate()) + "','yyyy-mm-dd')");
            }
            //startDate DATE 保函开立日期
            if (info.getStartDate() != null)
            {
                strSQLBuffer.append("\n AND startDate = to_date('" + DataFormat.getDateString(info.getStartDate()) + "','yyyy-mm-dd')");
            }
            //endDate DATE 担保到期日期、票面到期日
            if (info.getEndDate() != null)
            {
                strSQLBuffer.append("\n AND endDate = to_date('" + DataFormat.getDateString(info.getEndDate()) + "','yyyy-mm-dd')");
            }
            //draftType Number 汇票种类
            if (info.getDraftType() > -1)
            {
                strSQLBuffer.append("\n AND draftType = " + info.getDraftType());
            }
            //assureType Number 担保类型
            if (info.getAssureType() > -1)
            {
                strSQLBuffer.append("\n AND assureType = " + info.getAssureType());
            }
            //intervalNum Number 担保期限
            if (info.getIntervalNum() > -1)
            {
                strSQLBuffer.append("\n AND intervalNum = " + info.getIntervalNum());
            }
            //isLocal Number 是否本地
            if (info.getIsLocal() > -1)
            {
                strSQLBuffer.append("\n AND isLocal = " + info.getIsLocal());
            }
            //draftAmount NUMBER(21,6) 担保合同金额
            if (info.getDraftAmount() > 0.0)
            {
                strSQLBuffer.append("\n AND draftAmount = " + info.getDraftAmount());
            }
            //depositAmount NUMBER(21,6) 保证金金额
            if (info.getDepositAmount() > 0.0)
            {
                strSQLBuffer.append("\n AND depositAmount = " + info.getDepositAmount());
            }
            //amount NUMBER(21,6) 金额
            if (info.getAmount() > 0.0)
            {
                strSQLBuffer.append("\n AND amount = " + info.getAmount());
            }
            //inputUserID Number 录入人ID

            //inputDate DATE 录入日期
            if (info.getInputDate() != null)
            {
                strSQLBuffer.append("\n AND inputDate = to_date('" + DataFormat.getDateString(info.getInputDate()) + "','yyyy-mm-dd')");
            }
            //checkUserID Number 复核人ID
            if (info.getCheckUserID() > -1)//复核人不是录入人
            {
                strSQLBuffer.append("\n AND inputUserID != " + info.getCheckUserID());
            }
            //checkDate DATE 复核日期
            //modifyDate DATE 修改日期 精确到时分秒

            //statusID Number 状态 0—已删除、2—已保存、3—已复核
            if (info.getStatusID() > -1)
            {
                strSQLBuffer.append("\n AND statusID = " + info.getStatusID());
            }
            //abstract VARCHAR2(1000) 备注

            //////

            log.debug("=====匹配----sql:" + strSQLBuffer.toString());
            prepareStatement(strSQLBuffer.toString());
            executeQuery();
            Collection res = getDataEntitiesFromResultSet(info.getClass());
            if (res == null || res.size() == 0)
            {

            }
            else
            {
                Iterator it = res.iterator();
                rtInfo = (OffBalanceInfo) it.next();
            }
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            throw new SettlementDAOException("匹配记录产生错误", e);
        }
        finally
        {
            try
            {
                finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
                throw new SettlementDAOException(e);
            }
        }
        log.debug("=====结束匹配====");
        return rtInfo;
    }

    /**
     * Method match. 匹配链接查找
     * 
     * @param info
     * @return Collection
     * @throws OffBalanceException
     */
    public Collection findByMatch(OffBalanceInfo info, String orderByString) throws OffBalanceException, SettlementDAOException
    {
        log.debug("=====开始匹配链接查找====");
        Collection res = null;
        try
        {
            initDAO();
            StringBuffer strSQLBuffer = new StringBuffer();
            strSQLBuffer.append("select * from \n");
            strSQLBuffer.append(strTableName + " \n");
            strSQLBuffer.append(" where \n");
            strSQLBuffer.append(" 1 = 1 \n");
            strSQLBuffer.append(" and officeID = " + info.getOfficeID());
            strSQLBuffer.append(" and currencyID = " + info.getCurrencyID());
            if (info.getTransactionType() > -1)
            {
                strSQLBuffer.append("\n AND transactionType = " + info.getTransactionType());
            }
            if (info.getDirection() > -1)
            {
                strSQLBuffer.append("\n AND direction = " + info.getDirection());
            }
            if (info.getExecuteDate() != null)
            {
                strSQLBuffer.append("\n AND executeDate = to_date('" + DataFormat.getDateString(info.getExecuteDate()) + "','yyyy-mm-dd')");
            }
            if (info.getCheckUserID() > -1)//本执行日、本操作员复核的所有记录
            {
                strSQLBuffer.append("\n AND checkUserID = " + info.getCheckUserID());
            }
            if (info.getStatusID() > -1)
            {
                strSQLBuffer.append("\n AND statusID = " + info.getStatusID());
            }
            strSQLBuffer.append("\n " + orderByString);

            log.debug("=====开始匹配链接查找sql:" + strSQLBuffer.toString());
            System.out.println("=====开始匹配链接查找sql:"+ strSQLBuffer.toString());
            prepareStatement(strSQLBuffer.toString());
            executeQuery();
            res = getDataEntitiesFromResultSet(info.getClass());
            finalizeDAO();
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }
        finally
        {
            try
            {
                finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
                throw new SettlementDAOException(e);
            }
        }
        log.debug("=====开始匹配链接查找====");
        return res;
    }

    /**
     * 复核
     * 
     * @param OffBalanceParam
     *            parameter
     * @return void
     * @exception throws
     *                OffBalanceException
     */
    public void check(OffBalanceParam parameter) throws OffBalanceException
    {
        try
        {
            log4j.info("=====================通过ID(" + parameter.getId() + ")查询结果集");
            //取得结果集(通过ID)
            OffBalanceInfo OffBalanceInfo = this.findByID(parameter.getId());
            //生成会计分录Info集(GLEntryInfo)
            GLEntryInfo glEntryInfo = new GLEntryInfo();
            glEntryInfo.setOfficeID(parameter.getOfficeID());
            glEntryInfo.setCurrencyID(parameter.getCurrencyID());
            glEntryInfo.setSubjectCode(OffBalanceInfo.getSubjectCode());
            glEntryInfo.setTransNo(OffBalanceInfo.getTransNo());
            glEntryInfo.setTransactionTypeID(OffBalanceInfo.getTransactionType());
            glEntryInfo.setTransDirection(OffBalanceInfo.getDirection());
            glEntryInfo.setAmount(OffBalanceInfo.getAmount());
            glEntryInfo.setExecute(OffBalanceInfo.getExecuteDate());
            glEntryInfo.setAbstract(OffBalanceInfo.getAbstract());
            glEntryInfo.setInputUserID(OffBalanceInfo.getInputUserID());
            glEntryInfo.setCheckUserID(parameter.getCheckUserID());
            glEntryInfo.setStatusID(SETTConstant.EntryStatus.CHECKED);
            log4j.info("=====================业务方向是：" + SETTConstant.TransactionDirection.getName(OffBalanceInfo.getDirection()));
            OffBalanceRegisterInfo offBalanceRegisterInfo = new OffBalanceRegisterInfo();
            offBalanceRegisterInfo.setOfficeID(parameter.getOfficeID());
            offBalanceRegisterInfo.setCurrencyID(parameter.getCurrencyID());
            offBalanceRegisterInfo.setTransactionType(OffBalanceInfo.getTransactionType());
            offBalanceRegisterInfo.setSubjectCode(OffBalanceInfo.getSubjectCode());
            offBalanceRegisterInfo.setClientID(OffBalanceInfo.getBorrowClientID());
            offBalanceRegisterInfo.setContractID(OffBalanceInfo.getContractID());
            offBalanceRegisterInfo.setLoanNoteID(OffBalanceInfo.getLoanNoteID());
            offBalanceRegisterInfo.setBillID(OffBalanceInfo.getBillID());
            //该记录的业务类型方向为收入时
            if (OffBalanceInfo.getDirection() == SETTConstant.TransactionDirection.INCOME)
            {
                offBalanceRegisterInfo.setDirection(OffBalanceInfo.getDirection());
                //通过参数取得结果集
                log4j.info("=====================通过参数取得结果集:开始查询");
                Collection collection = this.offBalanceRegister.findByCondition(offBalanceRegisterInfo);
                log4j.info("=====================通过参数取得结果集:结束查询");
                Iterator iterator = null;
                if (collection != null)
                {
                    iterator = collection.iterator();
                }
                //如果存在结果集(金额、余额累加)
                if (iterator != null && iterator.hasNext())
                {
                    if ((OffBalanceInfo.getTransactionType() == SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE || OffBalanceInfo.getTransactionType() == SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE) && 
                    	OffBalanceInfo.getStatusID() == 1)
                    {
                        throw new OffBalanceException("Sett_E208", null);
                    }
                    log4j.info("=====================如果有结果集");
                    OffBalanceRegisterInfo offBalanceRegisterInfoIncome = (OffBalanceRegisterInfo) iterator.next();
                    OffBalanceRegisterInfo updateInfo = new OffBalanceRegisterInfo();
                    updateInfo.setId(offBalanceRegisterInfoIncome.getId());
                    updateInfo.setAmount(offBalanceRegisterInfoIncome.getAmount() + OffBalanceInfo.getAmount());
                    updateInfo.setBalance(offBalanceRegisterInfoIncome.getBalance() + OffBalanceInfo.getAmount());
                    updateInfo.setStatusID(1);
                    updateInfo.setRegisteDate(Env.getSystemDate(parameter.getOfficeID(), parameter.getCurrencyID()));
                    updateInfo.setWriteoffDate(null);
                    log4j.info("=====================更新登记簿：开始");
                    this.offBalanceRegister.update(updateInfo);
                    log4j.info("=====================更新登记簿：成功结束");

                    try
                    {
                        log4j.info("=====================生成会计分录：开始");
                        //增加会计分录
                        this.sett_glEntryDAO.add(glEntryInfo);
                        log4j.info("=====================生成会计分录：成功结束");
                    }
                    catch (Exception e1)
                    {
                        throw new OffBalanceException("Sett_E206", null);
                    }
                    //如果不存在结果集(新增记录)
                }
                else
                {
                    log4j.info("=====================如果没有结果集");
                    offBalanceRegisterInfo.setRegisteDate(Env.getSystemDate(parameter.getOfficeID(), parameter.getCurrencyID()));
                    offBalanceRegisterInfo.setConsignDate(OffBalanceInfo.getConsignDate());
                    offBalanceRegisterInfo.setAmount(OffBalanceInfo.getAmount());
                    offBalanceRegisterInfo.setBalance(OffBalanceInfo.getAmount());
                    offBalanceRegisterInfo.setTransNo(OffBalanceInfo.getTransNo());
                    offBalanceRegisterInfo.setStatusID(1);
                    log4j.info("=====================新增登记簿：开始");
                    this.offBalanceRegister.save(offBalanceRegisterInfo);
                    log4j.info("=====================新增登记簿：成功结束");

                    try
                    {
                        log4j.info("=====================生成会计分录：开始");
                        //增加会计分录
                        this.sett_glEntryDAO.add(glEntryInfo);
                        log4j.info("=====================生成会计分录：成功结束");
                    }
                    catch (Exception e1)
                    {
                        throw new OffBalanceException("Sett_E206", null);
                    }
                }
                //该记录的业务类型方向为付出时
            }
            else if (OffBalanceInfo.getDirection() == SETTConstant.TransactionDirection.PAYOUT)
            {
                //查出当前该笔付出所对应的收入记录
                offBalanceRegisterInfo.setStatusID(1);
                offBalanceRegisterInfo.setTransactionType(OffBalanceInfo.getTransactionType() - 1);
                offBalanceRegisterInfo.setDirection(SETTConstant.TransactionDirection.INCOME);
                log4j.info("=====================查出当前该笔付出所对应的登记簿收入记录:开始查询");
                Collection collection = this.offBalanceRegister.findByCondition(offBalanceRegisterInfo);
                log4j.info("=====================查出当前该笔付出所对应的登记簿收入记录:结束查询");
                Iterator iterator = null;
                if (collection != null)
                {
                    iterator = collection.iterator();
                }
                //如果找到匹配的收入记录
                if (iterator != null && iterator.hasNext())
                {
                    log4j.info("=====================如果有结果集");
                    OffBalanceRegisterInfo offBalanceRegisterInfoIncome = (OffBalanceRegisterInfo) iterator.next();
                    //校验当前笔付出的金额同所对应的收入记录的余额的大小
                    //如果校验结果为大
                    BigDecimal amount = new BigDecimal(OffBalanceInfo.getAmount());
                    BigDecimal balance = new BigDecimal(offBalanceRegisterInfoIncome.getBalance());
                    if (amount.compareTo(balance) < 0)
                    {
                        log4j.info("=====================当前该笔付出金额小于所对应的登记簿收入记录的余额");
                        //修改收入记录
                        OffBalanceRegisterInfo updateInfo = new OffBalanceRegisterInfo();
                        updateInfo.setId(offBalanceRegisterInfoIncome.getId());
                        updateInfo.setBalance(offBalanceRegisterInfoIncome.getBalance() - OffBalanceInfo.getAmount());
                        log4j.info("=====================修改登记簿收入记录：开始");
                        this.offBalanceRegister.update(updateInfo);
                        log4j.info("=====================修改登记簿收入记录：成功结束");
                        /**
                         * //增加付出记录 OffBalanceRegisterInfo saveInfo = new
                         * OffBalanceRegisterInfo();
                         * saveInfo.setOfficeID(parameter.getOfficeID());
                         * saveInfo.setCurrencyID(parameter.getCurrencyID());
                         * saveInfo
                         * .setClientID(OffBalanceInfo.getBorrowClientID());
                         * saveInfo.setContractID(OffBalanceInfo.getContractID());
                         * saveInfo.setLoanNoteID(OffBalanceInfo.getLoanNoteID());
                         * saveInfo.setBillID(OffBalanceInfo.getBillID());
                         * saveInfo
                         * .setSubjectCode(OffBalanceInfo.getSubjectCode());
                         * saveInfo.setDirection(OffBalanceInfo.getDirection());
                         * saveInfo.setRegisteDate(Env.getSystemDate(parameter
                         * .getOfficeID(), parameter.getCurrencyID())); saveInfo
                         * .setConsignDate(OffBalanceInfo.getConsignDate());
                         * saveInfo.setAmount(OffBalanceInfo.getAmount());
                         * saveInfo.setBalance(OffBalanceInfo.getAmount());
                         * saveInfo.setTransactionType(OffBalanceInfo
                         * .getTransactionType()); saveInfo.setStatusID(1);
                         * saveInfo.setTransNo(OffBalanceInfo.getTransNo());
                         * log4j.info("=====================新增登记簿付出记录：开始");
                         * this.offBalanceRegister.save(saveInfo);
                         * log4j.info("=====================新增登记簿付出记录：成功结束");
                         */
                        try
                        {
                            log4j.info("=====================生成会计分录：开始");
                            //增加会计分录
                            this.sett_glEntryDAO.add(glEntryInfo);
                            log4j.info("=====================生成会计分录：成功结束");
                        }
                        catch (Exception e1)
                        {
                            throw new OffBalanceException("Sett_E206", null);
                        }
                        //如果校验结果相等
                    }
                    else if (amount.compareTo(balance) == 0)
                    {
                        log4j.info("=====================当前该笔付出金额等于所对应的登记簿收入记录的余额");
                        //销账收入记录
                        OffBalanceRegisterInfo updateInfo = new OffBalanceRegisterInfo();
                        updateInfo.setId(offBalanceRegisterInfoIncome.getId());
                        updateInfo.setBalance(offBalanceRegisterInfoIncome.getBalance() - OffBalanceInfo.getAmount());
                        updateInfo.setStatusID(0);
                        updateInfo.setWriteoffDate(Env.getSystemDate(parameter.getOfficeID(), parameter.getCurrencyID()));
                        log4j.info("=====================销账登记簿收入记录：开始");
                        this.offBalanceRegister.update(updateInfo);
                        log4j.info("=====================销账登记簿收入记录：成功结束");
                        /**
                         * //增加付出记录 OffBalanceRegisterInfo saveInfo = new
                         * OffBalanceRegisterInfo();
                         * saveInfo.setOfficeID(parameter.getOfficeID());
                         * saveInfo.setCurrencyID(parameter.getCurrencyID());
                         * saveInfo
                         * .setClientID(OffBalanceInfo.getBorrowClientID());
                         * saveInfo.setContractID(OffBalanceInfo.getContractID());
                         * saveInfo.setLoanNoteID(OffBalanceInfo.getLoanNoteID());
                         * saveInfo.setBillID(OffBalanceInfo.getBillID());
                         * saveInfo
                         * .setSubjectCode(OffBalanceInfo.getSubjectCode());
                         * saveInfo.setDirection(OffBalanceInfo.getDirection());
                         * saveInfo.setRegisteDate(Env.getSystemDate(parameter
                         * .getOfficeID(), parameter.getCurrencyID())); saveInfo
                         * .setConsignDate(OffBalanceInfo.getConsignDate());
                         * saveInfo.setAmount(OffBalanceInfo.getAmount());
                         * saveInfo.setBalance(OffBalanceInfo.getAmount());
                         * saveInfo.setTransactionType(OffBalanceInfo
                         * .getTransactionType()); saveInfo.setStatusID(1);
                         * saveInfo.setTransNo(OffBalanceInfo.getTransNo());
                         * log4j.info("=====================新增登记簿付出记录：开始");
                         * this.offBalanceRegister.save(saveInfo);
                         * log4j.info("=====================新增登记簿付出记录：成功结束");
                         */
                        try
                        {
                            log4j.info("=====================生成会计分录：开始");
                            //增加会计分录
                            this.sett_glEntryDAO.add(glEntryInfo);
                            log4j.info("=====================生成会计分录：成功结束");
                        }
                        catch (Exception e1)
                        {
                            throw new OffBalanceException("Sett_E206", null);
                        }
                        //如果校验结果为小
                    }
                    else if (amount.compareTo(balance) > 0)
                    {
                        log4j.info("=====================当前该笔付出金额大于所对应的登记簿收入记录的余额");
                        throw new OffBalanceException("Sett_E202", null);
                    }
                    //如果没有匹配的收入记录
                }
                else
                {
                    log4j.info("=====================如果没有结果集");
                    throw new OffBalanceException("Sett_E203", null);
                }
            }

            log4j.info("=====================修改时间校验：开始");
            this.checkTimeStamp(parameter);
            log4j.info("=====================修改时间校验：成功结束");
            //复核该笔记录
            parameter.setStatusID(SETTConstant.TransactionStatus.CHECK);
            log4j.info("=====================复核开始：开始");
            updateStatus(parameter);
            log4j.info("=====================复核结束：成功结束");
        }
        catch (OffBalanceRegisterException oe)
        {
            throw new OffBalanceException();
        }
        catch (ITreasuryDAOException e)
        {
            throw new OffBalanceException();
        }
        catch (java.rmi.RemoteException re)
        {
            throw new OffBalanceException();
        }
    }

    /**
     * 取消复核
     * 
     * @param OffBalanceParam
     *            parameter
     * @return void
     * @exception throws
     *                OffBalanceException
     */
    public void cancleCheck(OffBalanceParam parameter) throws OffBalanceException
    {
        try
        {
            //取得结果集(通过ID)
            log4j.info("=====================通过ID(" + parameter.getId() + ")查询结果集");
            OffBalanceInfo OffBalanceInfo = this.findByID(parameter.getId());
            OffBalanceRegisterInfo offBalanceRegisterInfo = new OffBalanceRegisterInfo();
            offBalanceRegisterInfo.setOfficeID(parameter.getOfficeID());
            offBalanceRegisterInfo.setCurrencyID(parameter.getCurrencyID());
            offBalanceRegisterInfo.setTransactionType(OffBalanceInfo.getTransactionType());
            offBalanceRegisterInfo.setSubjectCode(OffBalanceInfo.getSubjectCode());
            offBalanceRegisterInfo.setClientID(OffBalanceInfo.getBorrowClientID());
            offBalanceRegisterInfo.setContractID(OffBalanceInfo.getContractID());
            offBalanceRegisterInfo.setLoanNoteID(OffBalanceInfo.getLoanNoteID());
            offBalanceRegisterInfo.setBillID(OffBalanceInfo.getBillID());
            log4j.info("=====================业务方向是：" + SETTConstant.TransactionDirection.getName(OffBalanceInfo.getDirection()));
            //该记录的业务类型方向为收入时
            if (OffBalanceInfo.getDirection() == SETTConstant.TransactionDirection.INCOME)
            {
                offBalanceRegisterInfo.setStatusID(1);
                //查出当前该笔收入所对应的登记簿收入记录
                offBalanceRegisterInfo.setDirection(SETTConstant.TransactionDirection.INCOME);
                log4j.info("=====================查出当前该笔收入所对应的登记簿收入记录:开始查询");
                Collection collectionIncome = this.offBalanceRegister.findByCondition(offBalanceRegisterInfo);
                log4j.info("=====================查出当前该笔收入所对应的登记簿收入记录:结束查询");
                Iterator iteratorIncome = null;
                if (collectionIncome != null)
                {
                    iteratorIncome = collectionIncome.iterator();
                }
                //如果找到匹配的收入记录
                if (iteratorIncome != null && iteratorIncome.hasNext())
                {
                    log4j.info("=====================如果有结果集");
                    OffBalanceRegisterInfo offBalanceRegisterInfoIncome = (OffBalanceRegisterInfo) iteratorIncome.next();
                    //校验当前笔收入的金额同所对应的登记簿收入记录的余额的大小
                    //如果校验结果为大
                    BigDecimal amount = new BigDecimal(OffBalanceInfo.getAmount());
                    BigDecimal balance = new BigDecimal(offBalanceRegisterInfoIncome.getBalance());
                    if (amount.compareTo(balance) < 0)
                    {
                        log4j.info("=====================当前该笔收入金额小于所对应的登记簿收入记录的余额");
                        //修改登记簿收入记录
                        OffBalanceRegisterInfo updateInfo = new OffBalanceRegisterInfo();
                        updateInfo.setId(offBalanceRegisterInfoIncome.getId());
                        updateInfo.setAmount(offBalanceRegisterInfoIncome.getAmount() - OffBalanceInfo.getAmount());
                        updateInfo.setBalance(offBalanceRegisterInfoIncome.getBalance() - OffBalanceInfo.getAmount());
                        log4j.info("=====================修改登记簿收入记录：开始");
                        this.offBalanceRegister.update(updateInfo);
                        log4j.info("=====================修改登记簿收入记录：成功结束");

                        try
                        {
                            log4j.info("=====================删除会计分录：开始");
                            //删除会计分录
                            this.sett_glEntryDAO.deleteByTransNo(OffBalanceInfo.getTransNo());
                            log4j.info("=====================删除会计分录：成功结束");
                        }
                        catch (Exception e1)
                        {
                            throw new OffBalanceException("Sett_E207", null);
                        }
                        //如果校验结果相等
                    }
                    else if (amount.compareTo(balance) == 0)
                    {
                        log4j.info("=====================当前该笔付出金额等于所对应的登记簿收入记录的余额");
                        //销账登记簿收入记录
                        OffBalanceRegisterInfo updateInfo = new OffBalanceRegisterInfo();
                        updateInfo.setId(offBalanceRegisterInfoIncome.getId());
                        updateInfo.setAmount(offBalanceRegisterInfoIncome.getAmount() - OffBalanceInfo.getAmount());
                        updateInfo.setBalance(offBalanceRegisterInfoIncome.getBalance() - OffBalanceInfo.getAmount());
                        updateInfo.setStatusID(0);
                        updateInfo.setWriteoffDate(Env.getSystemDate(parameter.getOfficeID(), parameter.getCurrencyID()));
                        log4j.info("=====================销账登记簿收入记录：开始");
                        this.offBalanceRegister.update(updateInfo);
                        log4j.info("=====================销账登记簿收入记录：成功结束");

                        try
                        {
                            log4j.info("=====================删除会计分录：开始");
                            //删除会计分录
                            this.sett_glEntryDAO.deleteByTransNo(OffBalanceInfo.getTransNo());
                            log4j.info("=====================删除会计分录：成功结束");
                        }
                        catch (Exception e1)
                        {
                            throw new OffBalanceException("Sett_E207", null);
                        }
                        //如果校验结果为小
                    }
                    else if (amount.compareTo(balance) > 0)
                    {
                        log4j.info("=====================当前该笔付出金额大于所对应的登记簿收入记录的余额");
                        throw new OffBalanceException("Sett_E205", null);
                    }
                }
                else
                {
                    log4j.info("=====================如果没有结果集");
                    throw new OffBalanceException("Sett_E204", null);
                }
                //该记录的业务类型方向为付出时
            }
            else if (OffBalanceInfo.getDirection() == SETTConstant.TransactionDirection.PAYOUT)
            {
                //查出当前该笔付出所对应的登记簿收入记录
                offBalanceRegisterInfo.setTransactionType(OffBalanceInfo.getTransactionType() - 1);
                offBalanceRegisterInfo.setDirection(SETTConstant.TransactionDirection.INCOME);
                log4j.info("=====================查出当前该笔付出所对应的登记簿收入记录:开始查询");
                Collection collectionIncome = offBalanceRegister.findByCondition(offBalanceRegisterInfo);
                log4j.info("=====================查出当前该笔付出所对应的登记簿收入记录:结束查询");
                Iterator iteratorIncome = null;
                if (collectionIncome != null)
                {
                    iteratorIncome = collectionIncome.iterator();
                }
                //如果存在结果集(金额、余额累加)
                if (iteratorIncome != null && iteratorIncome.hasNext())
                {
                    log4j.info("=====================如果有结果集");
                    OffBalanceRegisterInfo offBalanceRegisterInfoIncome = (OffBalanceRegisterInfo) iteratorIncome.next();
                    //正常状态
                    if (offBalanceRegisterInfoIncome.getStatusID() == 1)
                    {
                        log4j.info("=====================该笔付出所对应的登记簿收入记录的状态为正常");
                        OffBalanceRegisterInfo updateInfo = new OffBalanceRegisterInfo();
                        updateInfo.setId(offBalanceRegisterInfoIncome.getId());
                        updateInfo.setBalance(offBalanceRegisterInfoIncome.getBalance() + OffBalanceInfo.getAmount());
                        updateInfo.setRegisteDate(Env.getSystemDate(parameter.getOfficeID(), parameter.getCurrencyID()));
                        log4j.info("=====================更新登记簿收入记录：开始");
                        this.offBalanceRegister.update(updateInfo);
                        log4j.info("=====================更新登记簿收入记录：成功结束");
                        /**
                         * log4j.info("=====================更新登记簿付出记录：开始");
                         * this.offBalanceRegister.updateByTransNo(0,
                         * OffBalanceInfo.getTransNo());
                         * log4j.info("=====================更新登记簿付出记录：成功结束");
                         */
                        try
                        {
                            log4j.info("=====================删除会计分录：开始");
                            //删除会计分录
                            this.sett_glEntryDAO.deleteByTransNo(OffBalanceInfo.getTransNo());
                            log4j.info("=====================删除会计分录：成功结束");
                        }
                        catch (Exception e1)
                        {
                            throw new OffBalanceException("Sett_E207", null);
                        }
                        //销户状态
                    }
                    else if (offBalanceRegisterInfoIncome.getStatusID() == 0)
                    {
                        log4j.info("=====================该笔付出所对应的登记簿收入记录的状态为销户");
                        OffBalanceRegisterInfo updateInfo = new OffBalanceRegisterInfo();
                        updateInfo.setId(offBalanceRegisterInfoIncome.getId());
                        updateInfo.setBalance(offBalanceRegisterInfoIncome.getBalance() + OffBalanceInfo.getAmount());
                        updateInfo.setStatusID(1);
                        updateInfo.setWriteoffDate(null);
                        updateInfo.setRegisteDate(Env.getSystemDate(parameter.getOfficeID(), parameter.getCurrencyID()));
                        log4j.info("=====================更新登记簿收入记录：开始");
                        this.offBalanceRegister.update(updateInfo);
                        log4j.info("=====================更新登记簿收入记录：成功结束");
                        /**
                         * log4j.info("=====================更新登记簿付出记录：开始");
                         * this.offBalanceRegister.updateByTransNo(0,
                         * OffBalanceInfo.getTransNo());
                         * log4j.info("=====================更新登记簿付出记录：成功结束");
                         */
                        try
                        {
                            log4j.info("=====================删除会计分录：开始");
                            //删除会计分录
                            this.sett_glEntryDAO.deleteByTransNo(OffBalanceInfo.getTransNo());
                            log4j.info("=====================删除会计分录：成功结束");
                        }
                        catch (Exception e1)
                        {
                            throw new OffBalanceException("Sett_E207", null);
                        }
                    }
                    //如果不存在结果集
                }
                else
                {
                    log4j.info("=====================如果没有结果集");
                    throw new OffBalanceException("Sett_E200", null);
                }
            }

            log4j.info("=====================修改时间校验：开始");
            this.checkTimeStamp(parameter);
            log4j.info("=====================修改时间校验：成功结束");
            //取消复核该笔记录
            parameter.setStatusID(SETTConstant.TransactionStatus.SAVE);
            log4j.info("=====================取消复核：开始");
            updateStatus(parameter);
            log4j.info("=====================取消复核：成功结束");
        }
        catch (OffBalanceRegisterException oe)
        {
            throw new OffBalanceException();
        }
        catch (ITreasuryDAOException e)
        {
            throw new OffBalanceException();
        }
        catch (java.rmi.RemoteException re)
        {
            throw new OffBalanceException();
        }
    }

    /**
     * 保存
     * 
     * @param OffBalanceParam
     *            parameter
     * @return void
     * @exception throws
     *                OffBalanceException
     */
    public void add(OffBalanceParam offBalanceParam) throws OffBalanceException

    {
        try
        {
            super.add(offBalanceParam);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }
    }

    /**
     * 修改
     * 
     * @param OffBalanceParam
     *            parameter
     * @return void
     * @exception throws
     *                OffBalanceException
     */
    public void update(OffBalanceParam offBalanceParam) throws OffBalanceException
    {
        try
        {
            super.update(offBalanceParam);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }
    }

    /**
     * 交易方向更新操作
     * 
     * @param id
     * @return @throws
     *         ITreasuryDAOException
     */
    public void updateDirectionByID(long id) throws ITreasuryDAOException
    {
        try
        {
            OffBalanceRegisterDAO offBalanceRegisterDAO = new OffBalanceRegisterDAO();
            OffBalanceInfo offBalanceInfo = new OffBalanceInfo();
            offBalanceInfo = findByID(id);
            //获取方向direction和交易编号transNO
            long direction = offBalanceInfo.getDirection();
            long transactionType = offBalanceInfo.getTransactionType();
            if (direction == SETTConstant.TransactionDirection.INCOME)
            {
                direction = SETTConstant.TransactionDirection.PAYOUT;
            }
            else
            {
                direction = SETTConstant.TransactionDirection.INCOME;
            }

            if (transactionType == SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE)
            {
                transactionType = SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE;
            }
            else
            {
                transactionType = SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE;
            }

            String transNO = offBalanceInfo.getTransNo();
            //更新表外业务表
            StringBuffer strSQL = new StringBuffer();
            strSQL.append(" UPDATE sett_offbalance SET direction = " + direction + ", transactionType = " + transactionType + " WHERE id = " + id + " \n");
            log4j.debug(strSQL.toString());
            this.initDAO();
            this.prepareStatement(strSQL.toString());
            this.executeUpdate();
            this.finalizeDAO();
            //更新登记簿
            offBalanceRegisterDAO.updateByTransNo(transNO, direction, transactionType);

        }
        catch (ITreasuryException e)
        {
            throw new ITreasuryDAOException("交易方向更新异常", e);
        }
        finally
        {
            this.finalizeDAO();
        }
    }

    /**
     * 状态更新操作
     * 
     * @param offBalanceParam
     * @param statusID
     *            需要更新到的状态
     * @return @throws
     *         ITreasuryDAOException
     */
    public void updateStatus(OffBalanceParam offBalanceParam) throws ITreasuryDAOException
    {
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE \n");
            buffer.append(strTableName);
            buffer.append(" SET STATUSID = " + offBalanceParam.getStatusID());

            String time = Env.getSystemDateTime().toString();
            time = time.substring(0, 19);
            Log.print("######updateStatus ---- time=" + time);
            buffer.append(" , MODIFYDATE = to_date('" + time + "','YYYY-MM-DD-HH24:MI:SS')");//当前时间
            if (offBalanceParam.getCheckUserID() > -1)
            {
                buffer.append(", CHECKUSERID = " + offBalanceParam.getCheckUserID());
                //buffer.append(", CHECKDATE = to_date('" + time.substring(0,
                // 10) + "','yyyy-mm-dd')");
                buffer.append(", CHECKDATE = to_date('" + offBalanceParam.getCheckDate().toString().substring(0, 10) + "','yyyy-mm-dd')");
            }
            //TBD: maybe need add update execute date
            buffer.append("\n  WHERE ID = " + offBalanceParam.getId());
            String strSQL = buffer.toString();
            log.debug(strSQL);
            prepareStatement(strSQL);
            executeQuery();
            finalizeDAO();
        }
        catch (ITreasuryDAOException e)
        {
            finalizeDAO();
            throw new ITreasuryDAOException("状态更新异常", e);
        }
        finally
        {
            finalizeDAO();
        }

    }

    /**
     * 删除
     * 
     * @param long
     *            id
     * @return void
     * @exception throws
     *                OffBalanceException
     */
    public void delete(OffBalanceParam offBalanceParam) throws OffBalanceException
    {
        try
        {
            checkTimeStamp(offBalanceParam);
            offBalanceParam.setStatusID(SETTConstant.TransactionStatus.DELETED);
            //this.update(offBalanceParam);
            updateStatus(offBalanceParam);
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }
    }

    /**
     * 检查时间戳
     * 
     * 当录入页面的时间戳与数据库里的不相同时.检查记录状态，给前台打出时间戳不同的原因。
     */
    public OffBalanceInfo checkTimeStamp(OffBalanceParam offBalanceParam) throws OffBalanceException
    {
        //初始化数据库联接
        OffBalanceDAO offBalanceDAO = new OffBalanceDAO();

        //提取数据库里的记录
        OffBalanceInfo offBalanceInfoFromDB = new OffBalanceInfo();
        try
        {
            offBalanceInfoFromDB = (OffBalanceInfo) offBalanceDAO.findByID(offBalanceParam.getId(), offBalanceInfoFromDB.getClass());
        }
        catch (ITreasuryDAOException e)
        {
            throw new OffBalanceDAOException(e);
        }

        //比较两个记录里的时间戳是否相同
        // 记录的时间戳与数据库里的不相同时.说明该条记录已经被别人修改.
        if (!offBalanceParam.getModifyDate().equals(offBalanceInfoFromDB.getModifyDate()))
        {
            //检查记录前后变化的状态，查出时间戳改变的原因。
            String strReason = "";

            switch ((int) offBalanceInfoFromDB.getStatusID())
            {
                case (int) SETTConstant.TransactionStatus.DELETED:
                    strReason = "已删除";
                    break;
                case (int) SETTConstant.TransactionStatus.SAVE:
                    strReason = "已保存";
                    break;
                case (int) SETTConstant.TransactionStatus.CHECK:
                    strReason = "已复核";
                    break;
            }
            if (offBalanceParam.getStatusID() == SETTConstant.TransactionStatus.CHECK && offBalanceInfoFromDB.getStatusID() == SETTConstant.TransactionStatus.SAVE)
            {
                strReason = "已取消复核";
            }
            throw new OffBalanceModifiedException(strReason);
        }
        return offBalanceInfoFromDB;
    }

    /**
     * 表外业务明细账查询
     * 
     * @param OffBalanceParam
     *            parameter
     * @return Collection
     * @exception throws
     *                OffBalanceException
     */
    public Collection findTableOutsideDetail(OffBalanceParam parameter, String orderByString) throws OffBalanceException
    {
        Vector v = new Vector();
        StringBuffer strSQL = new StringBuffer();
        strSQL.append(" select * from SETT_OFFBALANCE where 1=1 ");
        strSQL.append("\n AND statusID = 3 "); //已复核过的
        if (parameter.getSubjectCode() != null && !parameter.getSubjectCode().equals(""))
            strSQL.append("\n AND subjectCode = '" + parameter.getSubjectCode() + "' ");
        if (parameter.getExecuteDate() != null)
            strSQL.append("\n AND executeDate >= to_date('" + DataFormat.getDateString(parameter.getExecuteDate()) + "','yyyy-mm-dd')");
        if (parameter.getExecuteDateEnd() != null)
            strSQL.append("\n AND executeDate <= to_date('" + DataFormat.getDateString(parameter.getExecuteDateEnd()) + "','yyyy-mm-dd')");
        if (parameter.getBorrowClientID() > -1)
            strSQL.append("\n AND borrowClientID = " + parameter.getBorrowClientID());
        if (parameter.getOfficeID() > -1)
            strSQL.append("\n AND officeID = " + parameter.getOfficeID());
        if (parameter.getCurrencyID() > -1)
            strSQL.append("\n AND currencyID = " + parameter.getCurrencyID());
        
        strSQL.append("\n" + orderByString);
        strSQL.append("\n");

        log4j.debug("findTableOutsideDetail=====\n" + strSQL.toString());
        try
        {
            this.initDAO();
            this.prepareStatement(strSQL.toString());
            this.executeQuery();
            OffBalanceInfo offBalanceInfo = new OffBalanceInfo();
            while (this.transRS != null && this.transRS.next())
            {
                //OffBalanceInfo info = (OffBalanceInfo)
                // this.getDataEntityFromResultSet(offBalanceInfo.getClass());
                OffBalanceInfo info = new OffBalanceInfo();
                info.setAbstract(transRS.getString("Abstract"));
                info.setAmount(transRS.getDouble("amount"));
                info.setAssureType(transRS.getLong("assureType"));
                info.setBillID(transRS.getLong("billID"));
                info.setBorrowClientID(transRS.getLong("borrowClientID"));
                info.setCheckDate(transRS.getTimestamp("checkDate"));
                info.setCheckUserID(transRS.getLong("checkUserID"));
                info.setConsignAccountID(transRS.getLong("consignAccountID"));
                info.setConsignClientID(transRS.getLong("consignClientID"));
                info.setConsignDate(transRS.getTimestamp("consignDate"));
                info.setContractID(transRS.getLong("contractID"));
                info.setCurrencyID(transRS.getLong("currencyID"));
                info.setDepositAmount(transRS.getDouble("depositAmount"));
                info.setDirection(transRS.getLong("direction"));
                info.setDraftAmount(transRS.getDouble("draftAmount"));
                info.setDraftType(transRS.getLong("draftType"));
                info.setEndDate(transRS.getTimestamp("endDate"));
                info.setExecuteDate(transRS.getTimestamp("executeDate"));
                info.setId(transRS.getLong("id"));
                info.setInputDate(transRS.getTimestamp("inputDate"));
                info.setInputUserID(transRS.getLong("inputUserID"));
                info.setIntervalNum(transRS.getLong("intervalNum"));
                info.setIsLocal(transRS.getLong("isLocal"));
                info.setLoanAccountID(transRS.getLong("loanAccountID"));
                info.setLoanNoteID(transRS.getLong("loanNoteID"));
                info.setModifyDate(transRS.getTimestamp("modifyDate"));
                info.setOfficeID(transRS.getLong("officeID"));
                info.setStartDate(transRS.getTimestamp("startDate"));
                info.setStatusID(transRS.getLong("statusID"));
                info.setSubjectCode(transRS.getString("subjectCode"));
                info.setTransactionType(transRS.getLong("transactionType"));
                info.setTransNo(transRS.getString("transNo"));

                v.add(info);
            }
            this.finalizeDAO();

            //期初余额
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            try
            {
                String sql = " SELECT mDebitBalance+mCreditBalance as debitCreditBalance from SETT_GLBALANCE \n";
                con = Database.getConnection();
                sql = sql + "\n where sGlSubjectCode = '" + parameter.getSubjectCode() + "' ";
                sql = sql + "\n and DTGLDate = to_date('" + DataFormat.getDateString(DataFormat.getPreviousDate(parameter.getExecuteDate())) + "','yyyy-mm-dd')";
                Log.print("findTableOutsideDetail----期初余额sql=\n" + sql);
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                debitCreditBalance = 0.0;
                if (rs.next())
                {
                    OffBalanceDAO.debitCreditBalance = rs.getDouble("debitCreditBalance");
                }
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception exp)
            {

            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }
        catch (SQLException e)
        {
            throw new OffBalanceDAOException("SQLException---", e);
        }
        finally
        {
            try
            {
                this.finalizeDAO();
            }
            catch (ITreasuryDAOException ies)
            {

            }
        }
        return (v.size() > 0 ? v : null);
    }

    /**
     * 表外业务汇总查询
     * 
     * @param OffBalanceParam
     *            parameter
     * @return Collection
     * @exception throws
     *                OffBalanceException
     */
    public Collection findTableOutsideCollect(OffBalanceParam parameter) throws OffBalanceException
    {
        Collection collection = null;
        StringBuffer strSQL = new StringBuffer();
        strSQL.append(" \n");

        log4j.debug(strSQL.toString());
        try
        {
            this.initDAO();

            this.prepareStatement(strSQL.toString());
            this.executeQuery();

            OffBalanceInfo offBalanceInfo = new OffBalanceInfo();
            collection = (Collection) this.getDataEntityFromResultSet(offBalanceInfo.getClass());

            this.finalizeDAO();
        }
        catch (ITreasuryDAOException ie)
        {
            throw new OffBalanceDAOException(ie);
        }

        return collection;
    }

    /**
     * 将次序类型转换为列名
     * 
     * @param orderType
     * @return
     */
    public static String getOrderColumnName(int orderType)
    {
        String returnValue = "";
        switch (orderType)
        {
            case ORDER_TRANS_NO:
                returnValue = ORDER_TRANS_NO_NAME;
                break;
            case ORDER_TRANSACTION_TYPE:
                returnValue = ORDER_TRANSACTION_TYPE_NAME;
                break;
            case ORDER_SUBJECT_CODE:
                returnValue = ORDER_SUBJECT_CODE_NAME;
                break;
            case ORDER_SUBJECT_NAME:
                returnValue = ORDER_SUBJECT_NAME_NAME;
                break;
            case ORDER_CONTRACT_ID:
                returnValue = ORDER_CONTRACT_ID_NAME;
                break;
            case ORDER_CONTRACT_CODE:
                returnValue = ORDER_CONTRACT_CODE_NAME;
                break;
            case ORDER_LOAN_NOTE_ID:
                returnValue = ORDER_LOAN_NOTE_ID_NAME;
                break;
            case ORDER_LOAN_NOTE_CODE:
                returnValue = ORDER_LOAN_NOTE_CODE_NAME;
                break;
            case ORDER_BILL_ID:
                returnValue = ORDER_BILL_ID_NAME;
                break;
            case ORDER_BILL_CODE:
                returnValue = ORDER_BILL_CODE_NAME;
                break;
            case ORDER_BORROW_CLIENT_ID:
                returnValue = ORDER_BORROW_CLIENT_ID_NAME;
                break;
            case ORDER_CONSIGN_CLIENT_ID:
                returnValue = ORDER_CONSIGN_CLIENT_ID_NAME;
                break;
            case ORDER_LOAN_ACCOUNT_ID:
                returnValue = ORDER_LOAN_ACCOUNT_ID_NAME;
                break;
            case ORDER_CONSIGN_ACCOUNT_ID:
                returnValue = ORDER_CONSIGN_ACCOUNT_ID_NAME;
                break;
            case ORDER_DIRECTION:
                returnValue = ORDER_DIRECTION_NAME;
                break;
            case ORDER_EXECUTE_DATE:
                returnValue = ORDER_EXECUTE_DATE_NAME;
                break;
            case ORDER_CONSIGN_DATE:
                returnValue = ORDER_CONSIGN_DATE_NAME;
                break;
            case ORDER_START_DATE:
                returnValue = ORDER_START_DATE_NAME;
                break;
            case ORDER_END_DATE:
                returnValue = ORDER_END_DATE_NAME;
                break;
            case ORDER_DRAFT_TYPE:
                returnValue = ORDER_DRAFT_TYPE_NAME;
                break;

            case ORDER_ASSURE_TYPE:
                returnValue = ORDER_ASSURE_TYPE_NAME;
                break;

            case ORDER_INTERVAL_NUM:
                returnValue = ORDER_INTERVAL_NUM_NAME;
                break;

            case ORDER_IS_LOCAL:
                returnValue = ORDER_IS_LOCAL_NAME;
                break;

            case ORDER_DRAFT_AMOUNT:
                returnValue = ORDER_DRAFT_AMOUNT_NAME;
                break;

            case ORDER_DEPOSIT_AMOUNT:
                returnValue = ORDER_DEPOSIT_AMOUNT_NAME;
                break;

            case ORDER_AMOUNT:
                returnValue = ORDER_AMOUNT_NAME;
                break;

            case ORDER_INPUTUSER_ID:
                returnValue = ORDER_INPUTUSER_ID_NAME;
                break;

            case ORDER_INPUT_DATE:
                returnValue = ORDER_INPUT_DATE_NAME;
                break;

            case ORDER_CHECKUSER_ID:
                returnValue = ORDER_CHECKUSER_ID_NAME;
                break;

            case ORDER_CHECK_DATE:
                returnValue = ORDER_CHECK_DATE_NAME;
                break;

            case ORDER_MODIFY_DATE:
                returnValue = ORDER_MODIFY_DATE_NAME;
                break;

            case ORDER_STATUS_ID:
                returnValue = ORDER_STATUS_ID_NAME;
                break;
            case ORDER_ABSTRACT:
                returnValue = ORDER_ABSTRACT_NAME;
                break;
            case ORDER_CLIENT_ID:
                returnValue = ORDER_CLIENT_ID_NAME;
                break;
            case ORDER_BALANCE:
                returnValue = ORDER_BALANCE_NAME;
                break;
            case ORDER_REGISTEDATE:
                returnValue = ORDER_REGISTEDATE_NAME;
                break;

        }
        return returnValue;
    }

    //test
    public static void main(String[] args)
    {
        OffBalanceDAO offBalanceDAO = new OffBalanceDAO();
        try
        {
            OffBalanceInfo info = new OffBalanceInfo();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}