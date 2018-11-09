package com.iss.itreasury.loan.loancommonsetting.dataentity;

/**
 * �˴���������˵����
 * �������ڣ�(2002-1-15 12:40:49)
 * @author��Administrator
 */
import java.sql.Timestamp;
import java.util.*;
public class ClientInfo implements java.io.Serializable
{
    /**
     * ClientInfo ������ע�⡣
     */
    public ClientInfo()
    {
        super();
    }
    
    //ClientID��ʶ
    private long ClientID=-1;
    //�ͻ�����1
    private long ClientTypeID1 = -1;
    //  �ͻ�����2
    private long ClientTypeID2 = -1;
    //  �ͻ�����3
    private long ClientTypeID3 = -1;
    //  �ͻ�����4
    private long ClientTypeID4 = -1;
    //  �ͻ�����5
    private long ClientTypeID5 = -1;
    //  �ͻ�����6
    private long ClientTypeID6 = -1;
    //���´���ʶ Ҳ�ǲ���˾��ʶ
    private long OfficeID=-1;
    
    //���´����� Ҳ�ǲ���˾����
    private String OfficeName="";
    
    //�ͻ����
    private String Code="";
    
    //�ͻ�����
    private String Name="";
    
    //���˴����Ӫҵִ��
    private String LicenceCode="";
    
    //��ҵ����
    private long CorpNatureID=-1;
    
    //��ҵ��������
    private String corpNatureName="";
        
    //����˾��ʶ
    private long ParentCorpID=-1;
    
    //����˾����
    private String ParentCorpName="";
        
    //����˾2��ʶ
    private long ParentCorpID2=-1;
    
    //����˾2����
    private String ParentCorpName2="";
    
    //�����ʼ�
    private String Email="";
    
    //��ַ
    private String Address="";
    
    //�ʱ�
    private String ZipCode="";
    
    //�绰
    private String Phone="";
    
    //����
    private String Fax="";
    
    //¼����ID
    private long InputUserID=-1;
    
    //¼��������
    private String InputUserName="";
    
    //¼��ʱ��
    private Timestamp dtInput;
    
    //�����û�ID
    private long ModifyUserID=-1;
    
    //�����û�
    private String ModifyUserName="";
    
    //����ʱ��
    private Timestamp dtModify;
    
    //�Ƿ��ǹɶ�
    private long IsPartner=-1;
    
    //����˾�˻���
    private String Account="";
    
    //��������1
    private String Bank1="";
    
    //��������2
    private String Bank2="";
    
    //��������3
    private String Bank3="";
    
    //ʡ
    private String Province="";
    
    //��
    private String City="";
    
    //�����˻�1
    private String BankAccount1="";
    
    //�����˻�2
    private String BankAccount2="";
    
    //�����˻�3
    private String BankAccount3="";
    
    //��������
    private String EconomyType="";
    
    //���õȼ�
    private long CreditLevelID=-1;
    
    private String strCreditLevelID="";
    
    //��ϵ��
    private String Contacter="";
    
    //��Ӫ����ͻ�����
    private long LoanClientTypeID=-1;
    
    //��Ӫ����ͻ�����
    private String LoanClientType="";
    
    //����ͻ�����
    private long SettClientTypeID=-1;
    
    //����ͻ�����
    private String SettClientType="";
    
    //���ռ���
    private long RiskLevelID=-1;
    
    private String strRiskLevelID="";
    
    //�ֹ����� ��������
    private String RiskLevel=""; //ע������ר�� ***-����Ҳ��
    
    //״̬  �Ƿ���Ч
    private long StatusID=-1;
    
    //���˴���
    private String LegalPerson="";

    //ע���ʱ�
    //private double Captial=0;
    private String Captial="";
        
    //��������
    private String GeneratorCapacity="";
    
    //������λ
    private String JudGelevelClient="";
    
    //��Ӫ��Χ
    private String DealScope="";
    
    //�����
    private String LoanCardNo;
    
    //�������
    private String LoanCardPwd;
    
    //���˴���֤��
    private String LegalPersonCode;
    
    //���ʵȼ�
    private String IntelligenceLevel;
    
    //�عɵ�λ
    private long KGClientID=-1;
    private String KGClientName="";
    private float KGScale =0;
    private String KGCardNo="";
    private String KGPwd="";
    
    //�����ɶ�1-3
    private long[] QTClientID={-1,-1,-1};
    private String[] QTClientName=new String[3];
    private float[] QTScale =new float[3];
    private String[] QTCardNo=new String[3];
    private String[] QTPwd=new String[3];
    
    //��ҳ��ʾ��ҳ����¼
    private long PageCount=0;

    //�������ͳ�Ʊ�ID  �������
    private long[] FINANCETJBID={-1,-1,-1};
    
    //�������ͳ�Ʊ�����
    private String[] FINANCETJBName = {"","",""};
    
    //�������ͳ�Ʊ������
    private String[] FINANCETJBYear = {"","",""};
    
	//	��������
	private String FinanceManager="";

	private Collection OthersStockHolder = null;//�����ɶ�����
    
    
    //��������ID
    //private long lDKDCBID=-1;
    
    //������������
    //private String DKDCBName = "";
    
    //�ͻ���Ӧ��󵣱���
    //private long lMaxAssureID = -1;
    
    //���������ID
    //private long lDBDCBID=-1;
    
    //�������������
    //private String DBDCBName = "";
    
    //haier ע��ʱ��
    private Timestamp dtEnrol;

    
	//�Ϻ����� 4 ����ά���ֶ�
	private String SEFCString1;
	private String SEFCString2;
	private String SEFCString3;
	private String SEFCString4;
	
	//�Ϻ����� 4 ����չ���� added by zntan 2004-12-14
	private long extendAttributeID1 = -1;
	private long extendAttributeID2 = -1;
	private long extendAttributeID3 = -1;
	private long extendAttributeID4 = -1;
	
	//�Ϻ����� ��ʾ��ҵ���� added by zntan 2005-1-19
	private long industryTypeID = -1;
	private String Name1="";//�ͻ����1 added by weihuang
	private String Name2="";//�ͻ����2 added by weihuang
	private long nIsfinancingcollection= -1;//�Ƿ��ʽ�漯 added by weihuang
    /**
     * function �õ��ͻ���ʶ
     * return ClientID
     */
    public long getClientID()
    {
        return ClientID;
    }

    /**
     * @param  lID �ͻ���ʶ
     * function ���ÿͻ���ʶ
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
     * function �õ����´���ʶ
     * return OfficeID
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param lOfficeID
     * function ���ð��´���ʶ
     * return void
     */
    public void setOfficeID(long lOfficeID)
    {
        this.OfficeID = lOfficeID;
    }

    /**
     * function �õ����´�������˾������
     * return OfficeName
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

    /**
     * @param OfficeName
     * function ���ð��´�������˾������
     * return void
     */
    public void setOfficeName(String strOfficeName)
    {
        this.OfficeName = strOfficeName;
    }

    /**
     * function �õ��ͻ�����
     * return String
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * @param Code
     * function  ���ÿͻ�����
     * return void
     */
    public void setCode(String strCode)
    {
        this.Code = strCode;
    }

    /**
     * function �õ��ͻ�����
     * return Name
     */
    public String getName()
    {
        return Name;
    }

    /**
     * @param strName
     * function ���ÿͻ�����
     * return void
     */
    public void setName(String strName)
    {
        this.Name = strName;
    }

    /**
     * function �õ��ͻ���Ӫҵִ��
     * return LicenceCode
     */
    public String getLicenceCode()
    {
        return LicenceCode;
    }

    /**
     * @param strLicenceCode
     * function ���ÿͻ���Ӫҵִ��
     * return void
     */
    public void setLicenceCode(String strLicenceCode)
    {
        this.LicenceCode = strLicenceCode;
    }

    /**
     * function  �õ���ҵ����
     * return long
     */
    public long getCorpNatureID()
    {
        return CorpNatureID;
    }

    /**
     * @param lNatureID
     * function ������ҵ����
     * return void
     */
    public void setCorpNatureID(long lNatureID)
    {
        this.CorpNatureID = lNatureID;
    }

    /**
     * function �õ����ܲ���ID
     * return long
     */
    public long getParentCorpID()
    {
        return ParentCorpID;
    }

    /**
     * @param lParentCorpID
     * function �������ܲ���ID
     * return void
     */
    public void setParentCorpID(long lParentCorpID)
    {
        this.ParentCorpID = lParentCorpID;
    }
    
    /**
     * function �õ����ܲ������� 
     * return ParentCorpName
     */
    public String getParentCorpName()
    {
        return ParentCorpName;
    }

    /**
     * @param strName
     * function �������ܲ������� 
     * return void
     */
    public void setParentCorpName(String strName)
    {
        this.ParentCorpName = strName;
    }

    /**
     * function �õ����ܲ���2ID
     * return long
     */
    public long getParentCorpID2()
    {
        return ParentCorpID2;
    }

    /**
     * @param lParentCorpID2
     * function �������ܲ���2ID
     * return void
     */
    public void setParentCorpID2(long lParentCorpID2)
    {
        this.ParentCorpID2 = lParentCorpID2;
    }
    
    /**
     * function �õ����ܲ������� 
     * return ParentCorpName
     */
    public String getParentCorpName2()
    {
        return ParentCorpName2;
    }

    /**
     * @param strName
     * function �������ܲ������� 
     * return void
     */
    public void setParentCorpName2(String strName2)
    {
        this.ParentCorpName2 = strName2;
    }

    /**
     * function �õ��ʼ���ַ
     * return Email
     */
    public String getEmail()
    {
        return Email;
    }

    /**
     * @param strEmail
     * function �����ʼ���ַ
     * return void
     */
    public void setEmail(String strEmail)
    {
        this.Email = strEmail;
    }

    /**
     * function �õ���˾��ַ
     * return Address
     */
    public String getAddress()
    {
        return Address;
    }

    /**
     * @param strAddress
     * function ���ù�˾��ַ
     * return void
     */
    public void setAddress(String strAddress)
    {
        this.Address = strAddress;
    }

    /**
     * function �õ��ʱ�
     * return ZipCode
     */
    public String getZipCode()
    {
        return ZipCode;
    }

    /**
     * @param strZipCode
     * function �����ʱ�
     * return void
     */
    public void setZipCode(String strZipCode)
    {
        this.ZipCode = strZipCode;
    }

    /**
     * function �õ��绰����
     * return Phone
     */
    public String getPhone()
    {
        return Phone;
    }

    /**
     * @param strPhone
     * function ���õ绰����
     * return void
     */
    public void setPhone(String strPhone)
    {
        this.Phone = strPhone;
    }

    /**
     * function �õ��������
     * return Fax
     */
    public String getFax()
    {
        return Fax;
    }

    /**
     * @param strFax
     * function ���ô������
     * return void
     */
    public void setFax(String strFax)
    {
        this.Fax = strFax;
    }


    /**
     * function �õ�������ID
     * return ModifyUserID
     */
    public long getModifyUserID()
    {
        return ModifyUserID;
    }

    /**
     * @param lModifyUserID
     * function ���ø�����ID
     * return void
     */
    public void setModifyUserID(long lModifyUserID)
    {
        this.ModifyUserID = lModifyUserID;
    }

    /**
     * function �õ��Ƿ�ɶ�
     * return IsPartner
     */
    public long getIsPartner()
    {
        return IsPartner;
    }

    /**
     * @param lIsPartner
     * function �����Ƿ�ɶ�
     * return void
     */
    public void setIsPartner(long lIsPartner)
    {
        this.IsPartner = lIsPartner;
    }

    /**
     * function �õ�ʡ������
     * return Province
     */
    public String getProvince()
    {
        return Province;
    }

    /**
     * @param 
     * function ����ʡ������
     * return void
     */
    public void setProvince(String strProvince)
    {
        this.Province = strProvince;
    }
    
    /**
     * function �õ���������
     * return City
     */
    public String getCity()
    {
        return City;
    }

    /**
     * @param strCity
     * function ���ó�������
     * return void
     */
    public void setCity(String strCity)
    {
        this.City = strCity;
    }


    /**
     * function �õ��˻���
     * return Account
     */
    public String getAccount()
    {
        return Account;
    }

    /**
     * @param strAccount
     * function �����˻���
     * return void
     */
    public void setAccount(String strAccount)
    {
        this.Account = strAccount;
    }

    /**
     * function �õ�����1����
     * return Bank1
     */
    public String getBank1()
    {
        return Bank1;
    }

    /**
     * @param strBank1
     * function ��������1����
     * return void
     */
    public void setBank1(String strBank1)
    {
        this.Bank1 = strBank1;
    }

    /**
     * function �õ�����2����
     * return Bank2
     */
    public String getBank2()
    {
        return Bank2;
    }

    /**
     * @param strBank2
     * function ��������2����
     * return void
     */
    public void setBank2(String strBank2)
    {
        this.Bank2 = strBank2;
    }

    /**
     * function �õ�����2����
     * return Bank3
     */
    public String getBank3()
    {
        return Bank3;
    }

    /**
     * @param strBank3
     * function ��������3����
     * return void
     */
    public void setBank3(String strBank3)
    {
        this.Bank3 = strBank3;
    }

    /**
     * function �õ�����1�˻���
     * return BankAccount1
     */
    public String getBankAccount1()
    {
        return BankAccount1;
    }

    /**
     * @param strBankAccount1
     * function ��������1�˻���
     * return void
     */
    public void setBankAccount1(String strBankAccount1)
    {
        this.BankAccount1 = strBankAccount1;
    }

    /**
     * function �õ�����2�˻���
     * return BankAccount2
     */
    public String getBankAccount2()
    {
        return BankAccount2;
    }

    /**
     * @param strBankAccount2
     * function ��������2�˻���
     * return void
     */
    public void setBankAccount2(String strBankAccount2)
    {
        this.BankAccount2 = strBankAccount2;
    }

    /**
     * function �õ�����3�˻���
     * return BankAccount3
     */
    public String getBankAccount3()
    {
        return BankAccount3;
    }

    /**
     * @param strBankAccount3
     * function ��������3�˻���
     * return void
     */
    public void setBankAccount3(String strBankAccount3)
    {
        this.BankAccount3 = strBankAccount3;
    }

    /**
     * function �õ����õȼ�
     * return long
     */
    public long getCreditLevelID()
    {
        return CreditLevelID;
    }

    /**
     * @param 
     * function �������õȼ�
     * return void
     */
    public void setCreditLevelID(long lCreditLevelID)
    {
        this.CreditLevelID = lCreditLevelID;
    }

    /**
     * function �õ���ϵ��
     * return Contacter
     */
    public String getContacter()
    {
        return Contacter;
    }

    /**
     * @param strContacter
     * function ������ϵ��
     * return void
     */
    public void setContacter(String strContacter)
    {
        this.Contacter = strContacter;
    }

    /**
     * function �õ��ͻ�����
     * return lLoanClientTypeID
     */
    public long getLoanClientTypeID()
    {
        return LoanClientTypeID;
    }

    /**
     * @param lLoanClientTypeID
     * function ���ÿͻ�����
     * return void
     */
    public void setLoanClientTypeID(long lLoanClientTypeID)
    {
        this.LoanClientTypeID = lLoanClientTypeID;
    }

    /**
     * function �õ��ͻ�����
     * return SettClientTypeID
     */
    public long getSettClientTypeID()
    {
        return SettClientTypeID;
    }

    /**
     * @param lSettClientTypeID
     * function ���ÿͻ�����
     * return void
     */
    public void setSettClientTypeID(long lSettClientTypeID)
    {
        this.SettClientTypeID = lSettClientTypeID;
    }

    /**
     * function �õ����յȼ�
     * return lRiskLevelID
     */
    public long getRiskLevelID()
    {
        return RiskLevelID;
    }

    /**
     * @param lRiskLevelID
     * function ���÷��յȼ�
     * return void
     */
    public void setRiskLevelID(long lRiskLevelID)
    {
        this.RiskLevelID = lRiskLevelID;
    }

    /**
     * function �õ����յȼ�������-�ֹ����룩
     * return RiskLevel
     */
    public String getRiskLevel()
    {
        return RiskLevel;
    }

    /**
     * @param 
     * function ���÷��յȼ�������-�ֹ����룩
     * return void
     */
    public void setRiskLevel(String strRiskLevel)
    {
        this.RiskLevel = strRiskLevel;
    }

    /**
     * function �õ���ҵ����
     * return LegalPerson
     */
    public String getLegalPerson()
    {
        return LegalPerson;
    }

    /**
     * @param strLegalPerson
     * function ������ҵ����
     * return void
     */
    public void setLegalPerson(String strLegalPerson)
    {
        this.LegalPerson = strLegalPerson;
    }

    /**
     * function �õ��ʱ���
     * return dCaptial
     */
    public String getCaptial()
    {
        return Captial;
    }

    /**
     * @param s
     * function �����ʱ���
     * return void
     */
    public void setCaptial(String s)
    {
        this.Captial = s;
    }

    /**
     * function �õ���������
     * return GeneratorCapacity
     */
    public String getGeneratorCapacity()
    {
        return GeneratorCapacity;
    }

    /**
     * @param strGeneratorCapacity
     * function ���û�������
     * return void
     */
    public void setGeneratorCapacity(String strGeneratorCapacity)
    {
        this.GeneratorCapacity = strGeneratorCapacity;
    }

    /**
     * function �õ�������λ
     * return JudGelevelClient
     */
    public String getJudGelevelClient()
    {
        return JudGelevelClient;
    }

    /**
     * @param strJudGelevelClient
     * function ����������λ
     * return void
     */
    public void setJudGelevelClient(String strJudGelevelClient)
    {
        this.JudGelevelClient = strJudGelevelClient;
    }

    /**
     * function �õ���Ӫ��Χ
     * return DealScope
     */
    public String getDealScope()
    {
        return DealScope;
    }

    /**
     * @param strDealScope
     * function ���þ�Ӫ��Χ
     * return void
     */
    public void setDealScope(String strDealScope)
    {
        this.DealScope = strDealScope;
    }

    /**
     * function �õ��عɵ�λID
     * return lKGClientID
     */
    public long getKGClientID()
    {
        return KGClientID;
    }

    /**
     * @param lKGClientID
     * function ���ÿعɵ�λID
     * return void
     */
    public void setKGClientID(long lKGClientID)
    {
        this.KGClientID = lKGClientID;
    }

    /**
     * function �õ��عɵ�λ����
     * return KGClientName
     */
    public String getKGClientName()
    {
        return KGClientName;
    }

    /**
     * @param strKGClientName
     * function ���ÿعɵ�λ����
     * return void
     */
    public void setKGClientName(String strKGClientName)
    {
        this.KGClientName = strKGClientName;
    }

    /**
     * function �õ��عɵ�λ�ֹɱ���
     * return fKGScale
     */
    public float getFKGScale()
    {
        return KGScale;
    }

    /**
     * @param f
     * function ���ÿعɵ�λ�ֹɱ���
     * return void
     */
    public void setFKGScale(float f)
    {
        this.KGScale = f;
    }

    /**
     * function �õ��عɵ�λ�����
     * return KGCardNo
     */
    public String getKGCardNo()
    {
        return KGCardNo;
    }

    /**
     * @param strKGCardNo
     * function ���ÿعɵ�λ�����
     * return void
     */
    public void setKGCardNo(String strKGCardNo)
    {
        this.KGCardNo = strKGCardNo;
    }

    /**
     * function �õ��عɵ�λ�������
     * return KGPwd
     */
    public String getKGPwd()
    {
        return KGPwd;
    }

    /**
     * @param strKGPwd
     * function ���ÿعɵ�λ�������
     * return void
     */
    public void setKGPwd(String strKGPwd)
    {
        this.KGPwd = strKGPwd;
    }

    /**
     * function �õ������ɶ���λ����
     * return String[] strQTClientName
     */
    public String[] getQTClientName()
    {
        return QTClientName;
    }

    /**
     * @param strQTClientName
     * function ���������ɶ���λ����
     * return void
     */
    public void setQTClientName(String[] strQTClientName)
    {
        this.QTClientName = strQTClientName;
    }

    /**
     * function �õ������ɶ���λ�ֹɱ���
     * return float[] QTScale
     */
    public float[] getFQTScale()
    {
        return QTScale;
    }

    /**
     * @param fQTScale
     * function ���������ɶ���λ�ֹɱ���
     * return void
     */
    public void setFQTScale(float[] fQTScale)
    {
        this.QTScale = fQTScale;
    }

    /**
     * function �õ������ɶ���λ�����
     * return String[] QTCardNo
     */
    public String[] getQTCardNo()
    {
        return QTCardNo;
    }

    /**
     * @param strQTCardNo
     * function ���������ɶ���λ�����
     * return void
     */
    public void setQTCardNo(String[] strQTCardNo)
    {
        this.QTCardNo = strQTCardNo;
    }

    /**
     * function �õ������ɶ���λ�������
     * return String[] QTPwd
     */
    public String[] getQTPwd()
    {
        return QTPwd;
    }

    /**
     * @param strQTPwd
     * function ���������ɶ���λ�������
     * return void
     */
    public void setQTPwd(String[] strQTPwd)
    {
        this.QTPwd = strQTPwd;
    }

    /**
     * function �õ�����������
     * return InputUserName
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param strName
     * function ��������������
     * return void
     */
    public void setInputUserName(String strName)
    {
        this.InputUserName = strName;
    }

    /**
     * function �õ������
     * return LoanCardNo
     */
    public String getLoanCardNo()
    {
        return LoanCardNo;
    }

    /**
     * @param strLoanCardNo
     * function �õ�/����
     * return void
     */
    public void setLoanCardNo(String strLoanCardNo)
    {
        this.LoanCardNo = strLoanCardNo;
    }

    /**
     * function �õ��������
     * return LoanCardPwd
     */
    public String getLoanCardPwd()
    {
        return LoanCardPwd;
    }

    /**
     * @param strLoanCardPwd
     * function ���ô������
     * return void
     */
    public void setLoanCardPwd(String strLoanCardPwd)
    {
        this.LoanCardPwd = strLoanCardPwd;
    }

    /**
     * function �õ�/���÷��˴���֤��
     * return LegalPersonCode
     */
    public String getLegalPersonCode()
    {
        return LegalPersonCode;
    }

    /**
     * @param strLegalPersonCode
     * function �õ�/���÷��˴���֤��
     * return void
     */
    public void setLegalPersonCode(String strLegalPersonCode)
    {
        this.LegalPersonCode = strLegalPersonCode;
    }

    /**
     * function �õ�/�������ʵȼ�
     * return IntelligenceLevel
     */
    public String getIntelligenceLevel()
    {
        return IntelligenceLevel;
    }

    /**
     * @param strIntelligenceLevel
     * function �õ�/�������ʵȼ�
     * return void
     */
    public void setIntelligenceLevel(String strIntelligenceLevel)
    {
        this.IntelligenceLevel = strIntelligenceLevel;
    }

    /**
     * function �õ��������ͳ�Ʊ�ID  �������
     * return long[] FINANCETJBID
     */
    public long[] getFINANCETJBID()
    {
        return FINANCETJBID;
    }

    /**
     * @param ls
     * function ���ò������ͳ�Ʊ�ID  �������
     * return void
     */
    public void setFINANCETJBID(long[] ls)
    {
        this.FINANCETJBID = ls;
    }

    /**
     * function �õ��������ͳ�Ʊ�����
     * return String[] FINANCETJBName
     */
    public String[] getFINANCETJBName()
    {
        return FINANCETJBName;
    }

    /**
     * @param strings
     * function ���ò������ͳ�Ʊ�����
     * return void
     */
    public void setFINANCETJBName(String[] strings)
    {
        this.FINANCETJBName = strings;
    }

    /**
     * function �õ�/������Ӫ����ͻ�����
     * return LoanClientType
     */
    public String getLoanClientType()
    {
        return LoanClientType;
    }

    /**
     * @param string
     * function �õ�/������Ӫ����ͻ�����
     * return void
     */
    public void setLoanClientType(String string)
    {
        this.LoanClientType = string;
    }

    /**
     * function �õ�/���ý���ͻ�����
     * return SettClientType
     */
    public String getSettClientType()
    {
        return SettClientType;
    }

    /**
     * @param string
     * function �õ�/���ý���ͻ�����
     * return void
     */
    public void setSettClientType(String string)
    {
        this.SettClientType = string;
    }

    /**
     * function �õ�/���ò������ͳ�Ʊ������
     * return String[]
     */
    public String[] getFINANCETJBYear()
    {
        return FINANCETJBYear;
    }

    /**
     * @param strings
     * function �õ�/���ò������ͳ�Ʊ������
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
	 * Returns the financeManager.
	 * @return String
	 */
	public String getFinanceManager()
	{
		return FinanceManager;
	}

	/**
	 * Sets the financeManager.
	 * @param financeManager The financeManager to set
	 */
	public void setFinanceManager(String financeManager)
	{
		FinanceManager = financeManager;
	}

	/**
	 * Returns the othersStockHolder.
	 * @return Collection
	 */
	public Collection getOthersStockHolder()
	{
		return OthersStockHolder;
	}

	/**
	 * Sets the othersStockHolder.
	 * @param othersStockHolder The othersStockHolder to set
	 */
	public void setOthersStockHolder(Collection othersStockHolder)
	{
		OthersStockHolder = othersStockHolder;
	}

    /**
     * Returns the dtEnrol.
     * @return dtEnrol
     */
    public Timestamp getEnrol()
    {
        return dtEnrol;
    }
    /**
     * Sets the enrol.
     * @param enrol The enrol to set
     */
    public void setEnrol(Timestamp enrol)
    {
        dtEnrol = enrol;
    }
	/**
	 * @return
	 */
	public String getSEFCString1() {
		return SEFCString1;
	}

	/**
	 * @return
	 */
	public String getSEFCString2() {
		return SEFCString2;
	}

	/**
	 * @return
	 */
	public String getSEFCString3() {
		return SEFCString3;
	}

	/**
	 * @return
	 */
	public String getSEFCString4() {
		return SEFCString4;
	}

	/**
	 * @param string
	 */
	public void setSEFCString1(String string) {
		SEFCString1 = string;
	}

	/**
	 * @param string
	 */
	public void setSEFCString2(String string) {
		SEFCString2 = string;
	}

	/**
	 * @param string
	 */
	public void setSEFCString3(String string) {
		SEFCString3 = string;
	}

	/**
	 * @param string
	 */
	public void setSEFCString4(String string) {
		SEFCString4 = string;
	}


	/**
	 * @return Returns the extendAttributeID1.
	 */
	public long getExtendAttributeID1()
	{
		return extendAttributeID1;
	}
	/**
	 * @param extendAttributeID1 The extendAttributeID1 to set.
	 */
	public void setExtendAttributeID1(long extendAttributeID1)
	{
		this.extendAttributeID1 = extendAttributeID1;
	}
	/**
	 * @return Returns the extendAttributeID2.
	 */
	public long getExtendAttributeID2()
	{
		return extendAttributeID2;
	}
	/**
	 * @param extendAttributeID2 The extendAttributeID2 to set.
	 */
	public void setExtendAttributeID2(long extendAttributeID2)
	{
		this.extendAttributeID2 = extendAttributeID2;
	}
	/**
	 * @return Returns the extendAttributeID3.
	 */
	public long getExtendAttributeID3()
	{
		return extendAttributeID3;
	}
	/**
	 * @param extendAttributeID3 The extendAttributeID3 to set.
	 */
	public void setExtendAttributeID3(long extendAttributeID3)
	{
		this.extendAttributeID3 = extendAttributeID3;
	}
	/**
	 * @return Returns the extendAttributeID4.
	 */
	public long getExtendAttributeID4()
	{
		return extendAttributeID4;
	}
	/**
	 * @param extendAttributeID4 The extendAttributeID4 to set.
	 */
	public void setExtendAttributeID4(long extendAttributeID4)
	{
		this.extendAttributeID4 = extendAttributeID4;
	}
	/**
	 * @return Returns the industryTypeID1.
	 */
	public long getIndustryTypeID()
	{
		return industryTypeID;
	}
	/**
	 * @param industryTypeID1 The industryTypeID1 to set.
	 */
	public void setIndustryTypeID(long industryTypeID1)
	{
		this.industryTypeID = industryTypeID1;
	}
	
    /**
     * @return Returns the name1.
     */
    public String getName1() {
        return Name1;
    }
    /**
     * @param name1 The name1 to set.
     */
    public void setName1(String name1) {
        Name1 = name1;
    }
    /**
     * @return Returns the name2.
     */
    public String getName2() {
        return Name2;
    }
    /**
     * @param name2 The name2 to set.
     */
    public void setName2(String name2) {
        Name2 = name2;
    }
    /**
     * @return Returns the zjgj.
     */
    public long getNIsfinancingcollection() {
        return nIsfinancingcollection;
    }
    /**
     * @param zjgj The zjgj to set.
     */
    public void setNIsfinancingcollection(long zjgj) {
        this.nIsfinancingcollection = zjgj;
    }
    
	/**
	 * @return Returns the clientTypeID1.
	 */
	public long getClientTypeID1() {
		return ClientTypeID1;
	}
	/**
	 * @param clientTypeID1 The clientTypeID1 to set.
	 */
	public void setClientTypeID1(long clientTypeID1) {
		ClientTypeID1 = clientTypeID1;
	}
	/**
	 * @return Returns the clientTypeID2.
	 */
	public long getClientTypeID2() {
		return ClientTypeID2;
	}
	/**
	 * @param clientTypeID2 The clientTypeID2 to set.
	 */
	public void setClientTypeID2(long clientTypeID2) {
		ClientTypeID2 = clientTypeID2;
	}
	/**
	 * @return Returns the clientTypeID3.
	 */
	public long getClientTypeID3() {
		return ClientTypeID3;
	}
	/**
	 * @param clientTypeID3 The clientTypeID3 to set.
	 */
	public void setClientTypeID3(long clientTypeID3) {
		ClientTypeID3 = clientTypeID3;
	}
	/**
	 * @return Returns the clientTypeID4.
	 */
	public long getClientTypeID4() {
		return ClientTypeID4;
	}
	/**
	 * @param clientTypeID4 The clientTypeID4 to set.
	 */
	public void setClientTypeID4(long clientTypeID4) {
		ClientTypeID4 = clientTypeID4;
	}
	/**
	 * @return Returns the clientTypeID5.
	 */
	public long getClientTypeID5() {
		return ClientTypeID5;
	}
	/**
	 * @param clientTypeID5 The clientTypeID5 to set.
	 */
	public void setClientTypeID5(long clientTypeID5) {
		ClientTypeID5 = clientTypeID5;
	}
	/**
	 * @return Returns the clientTypeID6.
	 */
	public long getClientTypeID6() {
		return ClientTypeID6;
	}
	/**
	 * @param clientTypeID6 The clientTypeID6 to set.
	 */
	public void setClientTypeID6(long clientTypeID6) {
		ClientTypeID6 = clientTypeID6;
	}
	/**
	 * @return Returns the dtEnrol.
	 */
	public Timestamp getDtEnrol() {
		return dtEnrol;
	}
	/**
	 * @param dtEnrol The dtEnrol to set.
	 */
	public void setDtEnrol(Timestamp dtEnrol) {
		this.dtEnrol = dtEnrol;
	}
	/**
	 * @return Returns the dtInput.
	 */
	public Timestamp getDtInput() {
		return dtInput;
	}
	/**
	 * @param dtInput The dtInput to set.
	 */
	public void setDtInput(Timestamp dtInput) {
		this.dtInput = dtInput;
	}
	/**
	 * @return Returns the dtModify.
	 */
	public Timestamp getDtModify() {
		return dtModify;
	}
	/**
	 * @param dtModify The dtModify to set.
	 */
	public void setDtModify(Timestamp dtModify) {
		this.dtModify = dtModify;
	}

	public String getStrCreditLevelID()
	{
		return strCreditLevelID;
	}

	public void setStrCreditLevelID(String strCreditLevelID)
	{
		this.strCreditLevelID = strCreditLevelID;
	}

	public String getStrRiskLevelID()
	{
		return strRiskLevelID;
	}

	public void setStrRiskLevelID(String strRiskLevelID)
	{
		this.strRiskLevelID = strRiskLevelID;
	}
}
