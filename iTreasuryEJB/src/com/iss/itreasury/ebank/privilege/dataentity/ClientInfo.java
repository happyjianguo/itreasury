package com.iss.itreasury.ebank.privilege.dataentity;

/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 12:40:49)
 * @author：Administrator
 */

import java.sql.Timestamp;

public class ClientInfo implements java.io.Serializable
{
    /**
     * ClientInfo 构造子注解。
     */
    public ClientInfo()
    {
        super();
    }

    //ID标识
    public long m_lID;

    //办事处标识
    public long m_lOfficeID;

    //办事处名称
    public String m_strOfficeName;

    //客户编号
    public String m_strCode;

    //客户名称
    public String m_strName;

    //法人代码或营业执照
    public String m_strChiefRepCode;

    //企业性质 （上市、非上市、其他）
    public long m_lCorpNatureID;

    //企业类型 （总部、油田、炼化、销售、其他）
    public long m_lCorpIndustryID;

    //父公司标识
    public long m_lParentCorpID;

    //父公司名称
    public String m_strParentCorpName;

    //邮件
    public String m_strEmail;

    //地址
    public String m_strAddress;

    //邮编
    public String m_strZipCode;

    //电话
    public String m_strPhone;

    //传真
    public String m_strFax;

    //印鉴起始日期
    public Timestamp m_dtSignStart;

    //客户当前印鉴标识
    public long m_lCurrentSignID;

    //录入人ID
    public long m_lInputUserID;

    //录入人姓名
    public String m_strInputUserName;

    //录入时间
    public Timestamp m_dtInput;

    //更新用户ID
    public long m_lUpdateUserID;

    //更新用户
    public String m_strUpdateUserName;

    //更新时间
    public Timestamp m_dtUpdate;

    //是否有效
    public long m_lStatusID;

    //是否是股东
    public long m_lParentID;

    //办事处账户
    public String m_strOfficeAccount;

    //开户银行1
    public String m_strBank1;

    //开户银行2
    public String m_strBank2;

    //开户银行3
    public String m_strBank3;

    //省
    public String m_strProvince;

    //市
    public String m_strCity;

    //地址1
    public String m_strAddress1;

    //地址2
    public String m_strAddress2;

    //地址3
    public String m_strAddress3;

    //银行账户1
    public String m_strBankAccount1;

    //银行账户2
    public String m_strBankAccount2;

    //银行账户3
    public String m_strBankAccount3;

    //经济性质
    public String m_strEconomyType;

    //信用等级
    public long m_lCreditLevelID;

    //联系人
    public String m_strContacter;

    //主管部门
    public String m_strParentDepartment;
    public long m_lParentDepartmentID;

    //客户分类
    public long m_lClientTypeID;

    //风险评级
    public long m_lRiskLevelID;

    //法人代表
    public String m_strLegalPerson;

    //开户行账户
    public String m_strBankAccount;

    //开户行
    public String m_strBank;

    //注册资本
    public String m_strCaptial;


    //分页显示总页数记录
    public long m_lPageCount;

    //客户对应最大申请号
    public long m_lMaxLoanID = -1;

    //贷款调查表ID
    public long m_lDKDCBID;

    //贷款调查表名称
    public String m_strDKDCBName = "";

    //客户对应最大担保号
    public long m_lMaxAssureID = -1;

    //担保调查表ID
    public long m_lDBDCBID;

    //担保调查表名称
    public String m_strDBDCBName = "";


}
