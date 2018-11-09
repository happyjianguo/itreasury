package com.iss.itreasury.ebank.fundplan.model;

public class FundPlanInfo {
	private long configid = -1;
	private String name	= "";					//��������
	private long parentid = -1;					//��ID
	private long layerid = -1;					//���ò��
	private String code	= "";					//���ñ���
	private String expression	= "";			//��ʽ
	private long planitemid = -1;				//�ʽ�ƻ��ӱ�ID
	private long capitalplanconfigid = -1;		//�ʽ�ƻ�������ID
	private double total = 0.0;					//�ϼ�
	private double mondaycapital = 0.0;			//����һ
	private double tuesdaycapital = 0.0;		//���ڶ�
	private double wednesdaycapital = 0.0;		//������
	private double thursdaycapital = 0.0;		//������
	private double fridaycapital = 0.0;			//������
	private double nextweekcapital = 0.0;		//��һ��
	private String remark = "";					//��ע
	
	private long officeId = -1;					//added by ylguo
	private long currencyId = -1;					
	private long modelId = -1;					
	
	
	
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getModelId() {
		return modelId;
	}
	public void setModelId(long modelId) {
		this.modelId = modelId;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getConfigid() {
		return configid;
	}
	public void setConfigid(long configid) {
		this.configid = configid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getParentid() {
		return parentid;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public long getLayerid() {
		return layerid;
	}
	public void setLayerid(long layerid) {
		this.layerid = layerid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getPlanitemid() {
		return planitemid;
	}
	public void setPlanitemid(long planitemid) {
		this.planitemid = planitemid;
	}
	public long getCapitalplanconfigid() {
		return capitalplanconfigid;
	}
	public void setCapitalplanconfigid(long capitalplanconfigid) {
		this.capitalplanconfigid = capitalplanconfigid;
	}
	public double getMondaycapital() {
		return mondaycapital;
	}
	public void setMondaycapital(double mondaycapital) {
		this.mondaycapital = mondaycapital;
	}
	public double getTuesdaycapital() {
		return tuesdaycapital;
	}
	public void setTuesdaycapital(double tuesdaycapital) {
		this.tuesdaycapital = tuesdaycapital;
	}
	public double getWednesdaycapital() {
		return wednesdaycapital;
	}
	public void setWednesdaycapital(double wednesdaycapital) {
		this.wednesdaycapital = wednesdaycapital;
	}
	public double getThursdaycapital() {
		return thursdaycapital;
	}
	public void setThursdaycapital(double thursdaycapital) {
		this.thursdaycapital = thursdaycapital;
	}
	public double getFridaycapital() {
		return fridaycapital;
	}
	public void setFridaycapital(double fridaycapital) {
		this.fridaycapital = fridaycapital;
	}
	public double getNextweekcapital() {
		return nextweekcapital;
	}
	public void setNextweekcapital(double nextweekcapital) {
		this.nextweekcapital = nextweekcapital;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}