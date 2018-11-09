package com.iss.itreasury.clientcenter.client.dataentity;

/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 12:40:49)
 * @author：Administrator
 */
import java.sql.Timestamp;
import java.util.*;
public class ClientInfo implements java.io.Serializable
{
    /**
     * ClientInfo 构造子注解。
     */
    public ClientInfo()
    {
        super();
    }
    
    //ClientID标识
    private long ClientID=-1;
    
    //办事处标识 也是财务公司标识
    private long OfficeID=-1;
    
    //办事处名称 也是财务公司名称
    private String OfficeName="";
    
    //客户编号
    private String Code="";
    
    //客户名称
    private String Name="";
    
    //法人代码或营业执照
    private String LicenceCode="";
    
    //企业类型
    private long CorpNatureID=-1;
    
    //企业类型名称
    private String corpNatureName="";
        
    //父公司标识
    private long ParentCorpID=-1;
    
    //父公司名称
    private String ParentCorpName="";
        
    //父公司2标识
    private long ParentCorpID2=-1;
    
    //父公司2名称
    private String ParentCorpName2="";
    
    //电子邮件
    private String Email="";
    
    //地址
    private String Address="";
    
    //邮编
    private String ZipCode="";
    
    //电话
    private String Phone="";
    
    //传真
    private String Fax="";
    
    //录入人ID
    private long InputUserID=-1;
    
    //录入人姓名
    private String InputUserName="";
    
    //录入时间
    private Timestamp dtInput;
    
    //更新用户ID
    private long ModifyUserID=-1;
    
    //更新用户
    private String ModifyUserName="";
    
    //更新时间
    private Timestamp dtModify;
    
    //是否是股东
    private long IsPartner=-1;
    
    //财务公司账户号
    private String Account="";
    
    //开户银行1
    private String Bank1="";
    
    //开户银行2
    private String Bank2="";
    
    //开户银行3
    private String Bank3="";
    
    //省
    private String Province="";
    
    //市
    private String City="";
    
    //银行账户1
    private String BankAccount1="";
    
    //银行账户2
    private String BankAccount2="";
    
    //银行账户3
    private String BankAccount3="";
    
    //经济性质
    private String EconomyType="";
    
    //信用等级
    private long CreditLevelID=-1;
    
    //联系人
    private String Contacter="";
    
    //自营贷款客户分类
    private long LoanClientTypeID=-1;
    
    //自营贷款客户分类
    private String LoanClientType="";
    
    //结算客户分类
    private long SettClientTypeID=-1;
    
    //结算客户分类
    private String SettClientType="";
    
    //风险级别
    private long RiskLevelID=-1;
    
    //手工输入 风险评级
    private String RiskLevel=""; //注：华能专用 ***-大桥也是
    
    //状态  是否有效
    private long StatusID=-1;
    
    //法人代表
    private String LegalPerson="";
    
    //财务主管
    private String FinanceManager="";

    //注册资本
    //private double Captial=0;
    private String Captial="";
        
    //机组容量
    private String GeneratorCapacity="";
    
    //评级单位
    private String JudGelevelClient="";
    
    //经营范围
    private String DealScope="";
    
    //总投资额
    private String InvestAmount="";
    
    //总投资时间
    private String InvestTime="";
    
    //贷款卡号
    private String LoanCardNo;
    
    //贷款卡密码
    private String LoanCardPwd;
    
    //法人代码证号
    private String LegalPersonCode;
    
    //资质等级
    private String IntelligenceLevel;
    
    //控股单位
    private long KGClientID=-1;
    private String KGClientName="";
    private float KGScale =0;
    private String KGCardNo="";
    private String KGPwd="";
    
    //其他股东1-3
    private long[] QTClientID={-1,-1,-1};
    private String[] QTClientName=new String[3];
    private float[] QTScale =new float[3];
    private String[] QTCardNo=new String[3];
    private String[] QTPwd=new String[3];
    
    private Collection OthersStockHolder = null;//其他股东集合
    
    //分页显示总页数记录
    private long PageCount=0;

    //财务情况统计表ID  最近三年
    private long[] FINANCETJBID={-1,-1,-1};
    
    //财务情况统计表名称
    private String[] FINANCETJBName = {"","",""};
    
    //财务情况统计表——年份
    private String[] FINANCETJBYear = {"","",""};
    
    //贷款调查表ID
    //private long lDKDCBID=-1;
    
    //贷款调查表名称
    //private String DKDCBName = "";
    
    //客户对应最大担保号
    //private long lMaxAssureID = -1;
    
    //担保调查表ID
    //private long lDBDCBID=-1;
    
    //担保调查表名称
    //private String DBDCBName = "";
    
    /**
     * function 得到客户标识
     * return ClientID
     */
    public long getClientID()
    {
        return ClientID;
    }

    /**
     * @param  lID 客户标识
     * function 设置客户标识
     * return void
     */
    public void setClientID(long lID)
    {
        this.ClientID = lID;
    }

/* qqgd add this two methods */
    public void setInputUserID(long userID)
    {
        this.InputUserID=userID;
    }
    public long getInputUserID()
    {
        return this.InputUserID;
    }
/* end of qqgd's adding*/
    
    /**
     * function 得到办事处标识
     * return OfficeID
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param lOfficeID
     * function 设置办事处标识
     * return void
     */
    public void setOfficeID(long lOfficeID)
    {
        this.OfficeID = lOfficeID;
    }

    /**
     * function 得到办事处（财务公司）名称
     * return OfficeName
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

    /**
     * @param OfficeName
     * function 设置办事处（财务公司）名称
     * return void
     */
    public void setOfficeName(String strOfficeName)
    {
        this.OfficeName = strOfficeName;
    }

    /**
     * function 得到客户编码
     * return String
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * @param Code
     * function  设置客户编码
     * return void
     */
    public void setCode(String strCode)
    {
        this.Code = strCode;
    }

    /**
     * function 得到客户名称
     * return Name
     */
    public String getName()
    {
        return Name;
    }

    /**
     * @param strName
     * function 设置客户名称
     * return void
     */
    public void setName(String strName)
    {
        this.Name = strName;
    }

    /**
     * function 得到客户的营业执照
     * return LicenceCode
     */
    public String getLicenceCode()
    {
        return LicenceCode;
    }

    /**
     * @param strLicenceCode
     * function 设置客户的营业执照
     * return void
     */
    public void setLicenceCode(String strLicenceCode)
    {
        this.LicenceCode = strLicenceCode;
    }

    /**
     * function  得到企业类型
     * return long
     */
    public long getCorpNatureID()
    {
        return CorpNatureID;
    }

    /**
     * @param lNatureID
     * function 设置企业类型
     * return void
     */
    public void setCorpNatureID(long lNatureID)
    {
        this.CorpNatureID = lNatureID;
    }

    /**
     * function 得到主管部门ID
     * return long
     */
    public long getParentCorpID()
    {
        return ParentCorpID;
    }

    /**
     * @param lParentCorpID
     * function 设置主管部门ID
     * return void
     */
    public void setParentCorpID(long lParentCorpID)
    {
        this.ParentCorpID = lParentCorpID;
    }
    
    /**
     * function 得到主管部门名称 
     * return ParentCorpName
     */
    public String getParentCorpName()
    {
        return ParentCorpName;
    }

    /**
     * @param strName
     * function 设置主管部门名称 
     * return void
     */
    public void setParentCorpName(String strName)
    {
        this.ParentCorpName = strName;
    }

    /**
     * function 得到主管部门2ID
     * return long
     */
    public long getParentCorpID2()
    {
        return ParentCorpID2;
    }

    /**
     * @param lParentCorpID2
     * function 设置主管部门2ID
     * return void
     */
    public void setParentCorpID2(long lParentCorpID2)
    {
        this.ParentCorpID2 = lParentCorpID2;
    }
    
    /**
     * function 得到主管部门名称 
     * return ParentCorpName
     */
    public String getParentCorpName2()
    {
        return ParentCorpName2;
    }

    /**
     * @param strName
     * function 设置主管部门名称 
     * return void
     */
    public void setParentCorpName2(String strName2)
    {
        this.ParentCorpName2 = strName2;
    }

    /**
     * function 得到邮件地址
     * return Email
     */
    public String getEmail()
    {
        return Email;
    }

    /**
     * @param strEmail
     * function 设置邮件地址
     * return void
     */
    public void setEmail(String strEmail)
    {
        this.Email = strEmail;
    }

    /**
     * function 得到公司地址
     * return Address
     */
    public String getAddress()
    {
        return Address;
    }

    /**
     * @param strAddress
     * function 设置公司地址
     * return void
     */
    public void setAddress(String strAddress)
    {
        this.Address = strAddress;
    }

    /**
     * function 得到邮编
     * return ZipCode
     */
    public String getZipCode()
    {
        return ZipCode;
    }

    /**
     * @param strZipCode
     * function 设置邮编
     * return void
     */
    public void setZipCode(String strZipCode)
    {
        this.ZipCode = strZipCode;
    }

    /**
     * function 得到电话号码
     * return Phone
     */
    public String getPhone()
    {
        return Phone;
    }

    /**
     * @param strPhone
     * function 设置电话号码
     * return void
     */
    public void setPhone(String strPhone)
    {
        this.Phone = strPhone;
    }

    /**
     * function 得到传真号码
     * return Fax
     */
    public String getFax()
    {
        return Fax;
    }

    /**
     * @param strFax
     * function 设置传真号码
     * return void
     */
    public void setFax(String strFax)
    {
        this.Fax = strFax;
    }


    /**
     * function 得到更改人ID
     * return ModifyUserID
     */
    public long getModifyUserID()
    {
        return ModifyUserID;
    }

    /**
     * @param lModifyUserID
     * function 设置更改人ID
     * return void
     */
    public void setModifyUserID(long lModifyUserID)
    {
        this.ModifyUserID = lModifyUserID;
    }

    /**
     * function 得到是否股东
     * return IsPartner
     */
    public long getIsPartner()
    {
        return IsPartner;
    }

    /**
     * @param lIsPartner
     * function 设置是否股东
     * return void
     */
    public void setIsPartner(long lIsPartner)
    {
        this.IsPartner = lIsPartner;
    }

    /**
     * function 得到省份名称
     * return Province
     */
    public String getProvince()
    {
        return Province;
    }

    /**
     * @param 
     * function 设置省份名称
     * return void
     */
    public void setProvince(String strProvince)
    {
        this.Province = strProvince;
    }
    
    /**
     * function 得到城市名称
     * return City
     */
    public String getCity()
    {
        return City;
    }

    /**
     * @param strCity
     * function 设置城市名称
     * return void
     */
    public void setCity(String strCity)
    {
        this.City = strCity;
    }


    /**
     * function 得到账户号
     * return Account
     */
    public String getAccount()
    {
        return Account;
    }

    /**
     * @param strAccount
     * function 设置账户号
     * return void
     */
    public void setAccount(String strAccount)
    {
        this.Account = strAccount;
    }

    /**
     * function 得到银行1名称
     * return Bank1
     */
    public String getBank1()
    {
        return Bank1;
    }

    /**
     * @param strBank1
     * function 设置银行1名称
     * return void
     */
    public void setBank1(String strBank1)
    {
        this.Bank1 = strBank1;
    }

    /**
     * function 得到银行2名称
     * return Bank2
     */
    public String getBank2()
    {
        return Bank2;
    }

    /**
     * @param strBank2
     * function 设置银行2名称
     * return void
     */
    public void setBank2(String strBank2)
    {
        this.Bank2 = strBank2;
    }

    /**
     * function 得到银行2名称
     * return Bank3
     */
    public String getBank3()
    {
        return Bank3;
    }

    /**
     * @param strBank3
     * function 设置银行3名称
     * return void
     */
    public void setBank3(String strBank3)
    {
        this.Bank3 = strBank3;
    }

    /**
     * function 得到银行1账户号
     * return BankAccount1
     */
    public String getBankAccount1()
    {
        return BankAccount1;
    }

    /**
     * @param strBankAccount1
     * function 设置银行1账户号
     * return void
     */
    public void setBankAccount1(String strBankAccount1)
    {
        this.BankAccount1 = strBankAccount1;
    }

    /**
     * function 得到银行2账户号
     * return BankAccount2
     */
    public String getBankAccount2()
    {
        return BankAccount2;
    }

    /**
     * @param strBankAccount2
     * function 设置银行2账户号
     * return void
     */
    public void setBankAccount2(String strBankAccount2)
    {
        this.BankAccount2 = strBankAccount2;
    }

    /**
     * function 得到银行3账户号
     * return BankAccount3
     */
    public String getBankAccount3()
    {
        return BankAccount3;
    }

    /**
     * @param strBankAccount3
     * function 设置银行3账户号
     * return void
     */
    public void setBankAccount3(String strBankAccount3)
    {
        this.BankAccount3 = strBankAccount3;
    }

    /**
     * function 得到信用等级
     * return long
     */
    public long getCreditLevelID()
    {
        return CreditLevelID;
    }

    /**
     * @param 
     * function 设置信用等级
     * return void
     */
    public void setCreditLevelID(long lCreditLevelID)
    {
        this.CreditLevelID = lCreditLevelID;
    }

    /**
     * function 得到联系人
     * return Contacter
     */
    public String getContacter()
    {
        return Contacter;
    }

    /**
     * @param strContacter
     * function 设置联系人
     * return void
     */
    public void setContacter(String strContacter)
    {
        this.Contacter = strContacter;
    }

    /**
     * function 得到客户分类
     * return lLoanClientTypeID
     */
    public long getLoanClientTypeID()
    {
        return LoanClientTypeID;
    }

    /**
     * @param lLoanClientTypeID
     * function 设置客户分类
     * return void
     */
    public void setLoanClientTypeID(long lLoanClientTypeID)
    {
        this.LoanClientTypeID = lLoanClientTypeID;
    }

    /**
     * function 得到客户分类
     * return SettClientTypeID
     */
    public long getSettClientTypeID()
    {
        return SettClientTypeID;
    }

    /**
     * @param lSettClientTypeID
     * function 设置客户分类
     * return void
     */
    public void setSettClientTypeID(long lSettClientTypeID)
    {
        this.SettClientTypeID = lSettClientTypeID;
    }

    /**
     * function 得到风险等级
     * return lRiskLevelID
     */
    public long getRiskLevelID()
    {
        return RiskLevelID;
    }

    /**
     * @param lRiskLevelID
     * function 设置风险等级
     * return void
     */
    public void setRiskLevelID(long lRiskLevelID)
    {
        this.RiskLevelID = lRiskLevelID;
    }

    /**
     * function 得到风险等级（华能-手工输入）
     * return RiskLevel
     */
    public String getRiskLevel()
    {
        return RiskLevel;
    }

    /**
     * @param 
     * function 设置风险等级（华能-手工输入）
     * return void
     */
    public void setRiskLevel(String strRiskLevel)
    {
        this.RiskLevel = strRiskLevel;
    }

    /**
     * function 得到企业法人
     * return LegalPerson
     */
    public String getLegalPerson()
    {
        return LegalPerson;
    }

    /**
     * @param strLegalPerson
     * function 设置企业法人
     * return void
     */
    public void setLegalPerson(String strLegalPerson)
    {
        this.LegalPerson = strLegalPerson;
    }

    /**
     * function 得到资本金
     * return dCaptial
     */
    public String getCaptial()
    {
        return Captial;
    }

    /**
     * @param s
     * function 设置资本金
     * return void
     */
    public void setCaptial(String s)
    {
        this.Captial = s;
    }

    /**
     * function 得到机组容量
     * return GeneratorCapacity
     */
    public String getGeneratorCapacity()
    {
        return GeneratorCapacity;
    }

    /**
     * @param strGeneratorCapacity
     * function 设置机组容量
     * return void
     */
    public void setGeneratorCapacity(String strGeneratorCapacity)
    {
        this.GeneratorCapacity = strGeneratorCapacity;
    }

    /**
     * function 得到评级单位
     * return JudGelevelClient
     */
    public String getJudGelevelClient()
    {
        return JudGelevelClient;
    }

    /**
     * @param strJudGelevelClient
     * function 设置评级单位
     * return void
     */
    public void setJudGelevelClient(String strJudGelevelClient)
    {
        this.JudGelevelClient = strJudGelevelClient;
    }

    /**
     * function 得到经营范围
     * return DealScope
     */
    public String getDealScope()
    {
        return DealScope;
    }

    /**
     * @param strDealScope
     * function 设置经营范围
     * return void
     */
    public void setDealScope(String strDealScope)
    {
        this.DealScope = strDealScope;
    }

    /**
     * function 得到控股单位ID
     * return lKGClientID
     */
    public long getKGClientID()
    {
        return KGClientID;
    }

    /**
     * @param lKGClientID
     * function 设置控股单位ID
     * return void
     */
    public void setKGClientID(long lKGClientID)
    {
        this.KGClientID = lKGClientID;
    }

    /**
     * function 得到控股单位名称
     * return KGClientName
     */
    public String getKGClientName()
    {
        return KGClientName;
    }

    /**
     * @param strKGClientName
     * function 设置控股单位名称
     * return void
     */
    public void setKGClientName(String strKGClientName)
    {
        this.KGClientName = strKGClientName;
    }

    /**
     * function 得到控股单位持股比例
     * return fKGScale
     */
    public float getFKGScale()
    {
        return KGScale;
    }

    /**
     * @param f
     * function 设置控股单位持股比例
     * return void
     */
    public void setFKGScale(float f)
    {
        this.KGScale = f;
    }

    /**
     * function 得到控股单位贷款卡号
     * return KGCardNo
     */
    public String getKGCardNo()
    {
        return KGCardNo;
    }

    /**
     * @param strKGCardNo
     * function 设置控股单位贷款卡号
     * return void
     */
    public void setKGCardNo(String strKGCardNo)
    {
        this.KGCardNo = strKGCardNo;
    }

    /**
     * function 得到控股单位贷款卡密码
     * return KGPwd
     */
    public String getKGPwd()
    {
        return KGPwd;
    }

    /**
     * @param strKGPwd
     * function 设置控股单位贷款卡密码
     * return void
     */
    public void setKGPwd(String strKGPwd)
    {
        this.KGPwd = strKGPwd;
    }

    /**
     * function 得到其他股东单位名称
     * return String[] strQTClientName
     */
    public String[] getQTClientName()
    {
        return QTClientName;
    }

    /**
     * @param strQTClientName
     * function 设置其他股东单位名称
     * return void
     */
    public void setQTClientName(String[] strQTClientName)
    {
        this.QTClientName = strQTClientName;
    }

    /**
     * function 得到其他股东单位持股比例
     * return float[] QTScale
     */
    public float[] getFQTScale()
    {
        return QTScale;
    }

    /**
     * @param fQTScale
     * function 设置其他股东单位持股比例
     * return void
     */
    public void setFQTScale(float[] fQTScale)
    {
        this.QTScale = fQTScale;
    }

    /**
     * function 得到其他股东单位贷款卡号
     * return String[] QTCardNo
     */
    public String[] getQTCardNo()
    {
        return QTCardNo;
    }

    /**
     * @param strQTCardNo
     * function 设置其他股东单位贷款卡号
     * return void
     */
    public void setQTCardNo(String[] strQTCardNo)
    {
        this.QTCardNo = strQTCardNo;
    }

    /**
     * function 得到其他股东单位贷款卡密码
     * return String[] QTPwd
     */
    public String[] getQTPwd()
    {
        return QTPwd;
    }

    /**
     * @param strQTPwd
     * function 设置其他股东单位贷款卡密码
     * return void
     */
    public void setQTPwd(String[] strQTPwd)
    {
        this.QTPwd = strQTPwd;
    }

    /**
     * function 得到输入人名称
     * return InputUserName
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param strName
     * function 设置输入人名称
     * return void
     */
    public void setInputUserName(String strName)
    {
        this.InputUserName = strName;
    }

    /**
     * function 得到贷款卡号
     * return LoanCardNo
     */
    public String getLoanCardNo()
    {
        return LoanCardNo;
    }

    /**
     * @param strLoanCardNo
     * function 得到/设置
     * return void
     */
    public void setLoanCardNo(String strLoanCardNo)
    {
        this.LoanCardNo = strLoanCardNo;
    }

    /**
     * function 得到贷款卡密码
     * return LoanCardPwd
     */
    public String getLoanCardPwd()
    {
        return LoanCardPwd;
    }

    /**
     * @param strLoanCardPwd
     * function 设置贷款卡密码
     * return void
     */
    public void setLoanCardPwd(String strLoanCardPwd)
    {
        this.LoanCardPwd = strLoanCardPwd;
    }

    /**
     * function 得到/设置法人代码证号
     * return LegalPersonCode
     */
    public String getLegalPersonCode()
    {
        return LegalPersonCode;
    }

    /**
     * @param strLegalPersonCode
     * function 得到/设置法人代码证号
     * return void
     */
    public void setLegalPersonCode(String strLegalPersonCode)
    {
        this.LegalPersonCode = strLegalPersonCode;
    }

    /**
     * function 得到/设置资质等级
     * return IntelligenceLevel
     */
    public String getIntelligenceLevel()
    {
        return IntelligenceLevel;
    }

    /**
     * @param strIntelligenceLevel
     * function 得到/设置资质等级
     * return void
     */
    public void setIntelligenceLevel(String strIntelligenceLevel)
    {
        this.IntelligenceLevel = strIntelligenceLevel;
    }

    /**
     * function 得到财务情况统计表ID  最近三年
     * return long[] FINANCETJBID
     */
    public long[] getFINANCETJBID()
    {
        return FINANCETJBID;
    }

    /**
     * @param ls
     * function 设置财务情况统计表ID  最近三年
     * return void
     */
    public void setFINANCETJBID(long[] ls)
    {
        this.FINANCETJBID = ls;
    }

    /**
     * function 得到财务情况统计表名称
     * return String[] FINANCETJBName
     */
    public String[] getFINANCETJBName()
    {
        return FINANCETJBName;
    }

    /**
     * @param strings
     * function 设置财务情况统计表名称
     * return void
     */
    public void setFINANCETJBName(String[] strings)
    {
        this.FINANCETJBName = strings;
    }

    /**
     * function 得到/设置自营贷款客户分类
     * return LoanClientType
     */
    public String getLoanClientType()
    {
        return LoanClientType;
    }

    /**
     * @param string
     * function 得到/设置自营贷款客户分类
     * return void
     */
    public void setLoanClientType(String string)
    {
        this.LoanClientType = string;
    }

    /**
     * function 得到/设置结算客户分类
     * return SettClientType
     */
    public String getSettClientType()
    {
        return SettClientType;
    }

    /**
     * @param string
     * function 得到/设置结算客户分类
     * return void
     */
    public void setSettClientType(String string)
    {
        this.SettClientType = string;
    }

    /**
     * function 得到/设置财务情况统计表——年份
     * return String[]
     */
    public String[] getFINANCETJBYear()
    {
        return FINANCETJBYear;
    }

    /**
     * @param strings
     * function 得到/设置财务情况统计表——年份
     * return void
     */
    public void setFINANCETJBYear(String[] strings)
    {
        this.FINANCETJBYear = strings;
    }

	/**
	 * @return
	 */
	public String getCorpNatureName()
	{
		return corpNatureName;
	}

	/**
	 * @param string
	 */
	public void setCorpNatureName(String string)
	{
		corpNatureName = string;
	}

	/**
	 * Returns the input.
	 * @return Timestamp
	 */
	public Timestamp getInput()
	{
		return dtInput;
	}

	/**
	 * Returns the modify.
	 * @return Timestamp
	 */
	public Timestamp getModify()
	{
		return dtModify;
	}

	/**
	 * Returns the economyType.
	 * @return String
	 */
	public String getEconomyType()
	{
		return EconomyType;
	}

	/**
	 * Returns the kGScale.
	 * @return float
	 */
	public float getKGScale()
	{
		return KGScale;
	}

	/**
	 * Returns the modifyUserName.
	 * @return String
	 */
	public String getModifyUserName()
	{
		return ModifyUserName;
	}

	/**
	 * Returns the pageCount.
	 * @return long
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * Returns the qTClientID.
	 * @return long[]
	 */
	public long[] getQTClientID()
	{
		return QTClientID;
	}

	/**
	 * Returns the qTScale.
	 * @return float[]
	 */
	public float[] getQTScale()
	{
		return QTScale;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * Sets the input.
	 * @param input The input to set
	 */
	public void setInput(Timestamp input)
	{
		dtInput = input;
	}

	/**
	 * Sets the modify.
	 * @param modify The modify to set
	 */
	public void setModify(Timestamp modify)
	{
		dtModify = modify;
	}

	/**
	 * Sets the economyType.
	 * @param economyType The economyType to set
	 */
	public void setEconomyType(String economyType)
	{
		EconomyType = economyType;
	}

	/**
	 * Sets the kGScale.
	 * @param kGScale The kGScale to set
	 */
	public void setKGScale(float kGScale)
	{
		KGScale = kGScale;
	}

	/**
	 * Sets the modifyUserName.
	 * @param modifyUserName The modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName)
	{
		ModifyUserName = modifyUserName;
	}

	/**
	 * Sets the pageCount.
	 * @param pageCount The pageCount to set
	 */
	public void setPageCount(long pageCount)
	{
		PageCount = pageCount;
	}

	/**
	 * Sets the qTClientID.
	 * @param qTClientID The qTClientID to set
	 */
	public void setQTClientID(long[] qTClientID)
	{
		QTClientID = qTClientID;
	}

	/**
	 * Sets the qTScale.
	 * @param qTScale The qTScale to set
	 */
	public void setQTScale(float[] qTScale)
	{
		QTScale = qTScale;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

    /**
     * @param 
     * function 得到/设置
     * return Collection
     */
    public Collection getOthersStockHolder()
    {
        return OthersStockHolder;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOthersStockHolder(Collection collection)
    {
        OthersStockHolder = collection;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getInvestAmount()
    {
        return InvestAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getInvestTime()
    {
        return InvestTime;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInvestAmount(String string)
    {
        InvestAmount = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInvestTime(String string)
    {
        InvestTime = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getFinanceManager()
    {
        return FinanceManager;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setFinanceManager(String string)
    {
        FinanceManager = string;
    }

}
