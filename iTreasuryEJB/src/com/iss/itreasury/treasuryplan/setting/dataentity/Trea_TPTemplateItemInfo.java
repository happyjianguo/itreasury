package com.iss.itreasury.treasuryplan.setting.dataentity;

import java.io.Serializable;

import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * @name: Trea_TPTemplateItemInfo
 * @description:�ʽ�ƻ�ģ������Ŀ
 * @author: jason
 * @create: 2005-11-15
 */
public class Trea_TPTemplateItemInfo extends TreasuryPlanBaseDataEntity implements Serializable
{
    private long   id              = -1;   // ΨһԼ��

    private long   lineId          = -1;   // �ʽ�ƻ�����ĿId

    private long   moduleId        = -1;   // ������Դ

    private String clientCode      = null; // �ͻ�/ҵ��λ���

    private String accountCode     = null; // �˻���/�ʽ��˻�

    private String contractCode    = null; // ��ͬ��/�浥��

    private String payFormCode     = null; // �ſ�֪ͨ����

    private String counterPartName = null; // ���׶�������

    private String securitiesName  = null; // ֤ȯ/��Ʒ����

    private long   accountTypeId   = -1;   // �˻�����

    private String glSubjectCode   = null; // ��Ŀ��

    private long   amountDirection = -1;   // �����������

    private String parameter       = null; // �Զ������

    private long   amountFlag      = -1;   // ����־�� 1=���շ����� �� 2 =������ 3=�������

    private String calculateFlag   = null; // �����־

    public long getId()
    {
        // TODO Auto-generated method stub
        return id;
    }

    public void setId(long id)
    {
        // TODO Auto-generated method stub
        this.id = id;
        putUsedField("id", id);
    }

    /**
     * @return Returns the accountCode.
     */
    public String getAccountCode()
    {
        return accountCode;
    }

    /**
     * @param accountCode The accountCode to set.
     */
    public void setAccountCode(String accountCode)
    {
        this.accountCode = accountCode;
        putUsedField("accountCode", accountCode);
    }

    /**
     * @return Returns the accountTypeId.
     */
    public long getAccountTypeId()
    {
        return accountTypeId;
    }

    /**
     * @param accountTypeId The accountTypeId to set.
     */
    public void setAccountTypeId(long accountTypeId)
    {
        this.accountTypeId = accountTypeId;
        putUsedField("accountTypeId", accountTypeId);
    }

    /**
     * @return Returns the amountDirection.
     */
    public long getAmountDirection()
    {
        return amountDirection;
    }

    /**
     * @param amountDirection The amountDirection to set.
     */
    public void setAmountDirection(long amountDirection)
    {
        this.amountDirection = amountDirection;
        putUsedField("amountDirection", amountDirection);
    }

    /**
     * @return Returns the amountFlag.
     */
    public long getAmountFlag()
    {
        return amountFlag;
    }

    /**
     * @param amountFlag The amountFlag to set.
     */
    public void setAmountFlag(long amountFlag)
    {
        this.amountFlag = amountFlag;
        putUsedField("amountFlag", amountFlag);
    }

    /**
     * @return Returns the calculateFlag.
     */
    public String getCalculateFlag()
    {
        return calculateFlag;
    }

    /**
     * @param calculateFlag The calculateFlag to set.
     */
    public void setCalculateFlag(String calculateFlag)
    {
        this.calculateFlag = calculateFlag;
        putUsedField("calculateFlag", calculateFlag);
    }

    /**
     * @return Returns the clientCode.
     */
    public String getClientCode()
    {
        return clientCode;
    }

    /**
     * @param clientCode The clientCode to set.
     */
    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
        putUsedField("clientCode", clientCode);
    }

    /**
     * @return Returns the contractCode.
     */
    public String getContractCode()
    {
        return contractCode;
    }

    /**
     * @param contractCode The contractCode to set.
     */
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
        putUsedField("contractCode", contractCode);
    }

    /**
     * @return Returns the counterPartName.
     */
    public String getCounterPartName()
    {
        return counterPartName;
    }

    /**
     * @param counterPartName The counterPartName to set.
     */
    public void setCounterPartName(String counterPartName)
    {
        this.counterPartName = counterPartName;
        putUsedField("counterPartName", counterPartName);
    }

    /**
     * @return Returns the glSubjectCode.
     */
    public String getGlSubjectCode()
    {
        return glSubjectCode;
    }

    /**
     * @param glSubjectCode The glSubjectCode to set.
     */
    public void setGlSubjectCode(String glSubjectCode)
    {
        this.glSubjectCode = glSubjectCode;
        putUsedField("glSubjectCode", glSubjectCode);
    }

    /**
     * @return Returns the lineId.
     */
    public long getLineId()
    {
        return lineId;
    }

    /**
     * @param lineId The lineId to set.
     */
    public void setLineId(long lineId)
    {
        this.lineId = lineId;
        putUsedField("lineId", lineId);
    }

    /**
     * @return Returns the moduleId.
     */
    public long getModuleId()
    {
        return moduleId;
    }

    /**
     * @param moduleId The moduleId to set.
     */
    public void setModuleId(long moduleId)
    {
        this.moduleId = moduleId;
        putUsedField("moduleId", moduleId);
    }

    /**
     * @return Returns the parameter.
     */
    public String getParameter()
    {
        return parameter;
    }

    /**
     * @param parameter The parameter to set.
     */
    public void setParameter(String parameter)
    {
        this.parameter = parameter;
        putUsedField("parameter", parameter);
    }

    /**
     * @return Returns the payFormCode.
     */
    public String getPayFormCode()
    {
        return payFormCode;
    }

    /**
     * @param payFormCode The payFormCode to set.
     */
    public void setPayFormCode(String payFormCode)
    {
        this.payFormCode = payFormCode;
        putUsedField("payFormCode", payFormCode);
    }

    /**
     * @return Returns the securitiesName.
     */
    public String getSecuritiesName()
    {
        return securitiesName;
    }

    /**
     * @param securitiesName The securitiesName to set.
     */
    public void setSecuritiesName(String securitiesName)
    {
        this.securitiesName = securitiesName;
        putUsedField("securitiesName", securitiesName);
    }

}
