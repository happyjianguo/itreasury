package com.iss.itreasury.settlement.obinstruction.dataentity;

public class AmountCountInfo implements java.io.Serializable{

	public AmountCountInfo()
	{
	}
	private double balanceSum = 0.0;   //���С��
    private double amountSum = 0.0;   //���ϼ�
    private long count =0;            //���ױ���
	
    public double getAmountSum() {
		return amountSum;
	}
	public void setAmountSum(double amountSum) {
		this.amountSum = amountSum;
	}
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public double getBalanceSum() {
		return balanceSum;
	}
	public void setBalanceSum(double balanceSum) {
		this.balanceSum = balanceSum;
	}
    
    
	

}
