package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

//import com.iss.itreasury.bs.AccountTransactionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BankAccountTransInfo implements Serializable 
{
	private long ID = -1;
	private String AccountNo = null; //<Account_num>�˺�</Account_num>
	private String AccountName = null;
	private String BranchName = null;
	private String OppositeAccountNo = null;
	private String OppositeAccountName = null;
	private String OppositeBranchName = null;
	private double Amount = 0.0; //<Amount>������</Amount>
	private long CurrencyID = -1;
	private long Direction = -1; //<sign>�����־</sign>
	private String CheckNo = null; //<Check_num>ƾ֤��</Check_num>	
	private String CheckType = null;
	private Date TransAction = null; //<Trans_date>��������</Trans_date>
	private String TransactionType = null;
	private String Abstract = null; //<Trans_abstr>ժҪ</Trans_abstr>
	private String Comment = null; //<Yt>��;</Yt>
	private long BankType = -1;

	//�����ֶ�
	private String transNoOfBank = null;//STRANSNOOFBANK	���׵�����Ψһ��ʶ	Code
	private long isToTurnIn = -1;//NISTOTURNIN	�Ƿ���Ҫ��������	Number
	private long turnInResult = -1;//NTURNINRESULT	���˽��	Number
	private Timestamp turnInTime = null;//DTTURNIN	��������	Date
	private long turnInTransType = -1;//NTURNINTRANSTYPE	���˽�������	Number
	private String transactionNO = null;//STRANSACTIONNO	��Ӧ����ҵ��Ľ��׺�	Code
	private long isPrintedVoucher = -1;//NISPRINTEDVOUCHER	�Ƿ��Ѵ�ӡƾ֤	Number
	private Timestamp turnInSend = null;//DTTURNINSEND  ���׷���ʱ�� Date
	
	private long isAdd = -1;//�Ƿ�����,����������,�����ʾԭ��
	private String   sTurnInRemind = null; //STURNINREMIND ,���˲���������Ϣ
	private long  isDeletedByBank = Constant.FALSE ; //NISDELETEDBYBANK ,�����Ƿ���ɾ��

	//Ϊ���������ֶ�
	private double sumAmount = 0.0;//�ܺ�
	private long number = -1;//����
	/**
	 * @return Returns the isAdd.
	 */
	public long getIsAdd()
	{
		return isAdd;
	}
	/**
	 * @param isAdd The isAdd to set.
	 */
	public void setIsAdd(long isAdd)
	{
		this.isAdd=isAdd;
	}
	/**
	 * @return Returns the isPrintedVoucher.
	 */
	public long getIsPrintedVoucher()
	{
		return isPrintedVoucher;
	}
	/**
	 * @param isPrintedVoucher The isPrintedVoucher to set.
	 */
	public void setIsPrintedVoucher(long isPrintedVoucher)
	{
		this.isPrintedVoucher = isPrintedVoucher;
	}
	/**
	 * @return Returns the isToTurnIn.
	 */
	public long getIsToTurnIn()
	{
		return isToTurnIn;
	}
	/**
	 * @param isToTurnIn The isToTurnIn to set.
	 */
	public void setIsToTurnIn(long isToTurnIn)
	{
		this.isToTurnIn = isToTurnIn;
	}
	/**
	 * @return Returns the transactionNO.
	 */
	public String getTransactionNO()
	{
		return transactionNO;
	}
	/**
	 * @param transactionNO The transactionNO to set.
	 */
	public void setTransactionNO(String transactionNO)
	{
		this.transactionNO = transactionNO;
	}
	/**
	 * @return Returns the transNoOfBank.
	 */
	public String getTransNoOfBank()
	{
		return transNoOfBank;
	}
	/**
	 * @param transNoOfBank The transNoOfBank to set.
	 */
	public void setTransNoOfBank(String transNoOfBank)
	{
		this.transNoOfBank = transNoOfBank;
	}
	/**
	 * @return Returns the turnInResult.
	 */
	public long getTurnInResult()
	{
		return turnInResult;
	}
	/**
	 * @param turnInResult The turnInResult to set.
	 */
	public void setTurnInResult(long turnInResult)
	{
		this.turnInResult = turnInResult;
	}
	/**
	 * @return Returns the turnInTime.
	 */
	public Timestamp getTurnInTime()
	{
		return turnInTime;
	}
	/**
	 * @param turnInTime The turnInTime to set.
	 */
	public void setTurnInTime(Timestamp turnInTime)
	{
		this.turnInTime = turnInTime;
	}
	/**
	 * @return Returns the turnInTransType.
	 */
	public long getTurnInTransType()
	{
		return turnInTransType;
	}
	/**
	 * @param turnInTransType The turnInTransType to set.
	 */
	public void setTurnInTransType(long turnInTransType)
	{
		this.turnInTransType = turnInTransType;
	}
	/**
	 * Constructor for BankAccontTransInfo.
	 */
	public BankAccountTransInfo()
	{
		super();
	}

	/**
	 * Constructor for BankAccontTransInfo.
	 */
	/*public BankAccountTransInfo(AccountTransactionInfo transInfo)
	{
		this.ID = -1;
		this.AccountNo = transInfo.getAccountNo();
		this.AccountName = transInfo.getAccountName();
		this.BranchName = transInfo.getBranchName();
		this.OppositeAccountNo = transInfo.getOppositeAccountNo();
		this.OppositeAccountName = transInfo.getOppositeAccountName();
		this.OppositeBranchName = transInfo.getOppositeBranchName();
		this.Amount = transInfo.getAmount();
		if(transInfo.getCurrencyType()==null)
		{
			this.CurrencyID = -1;
		}else
		{
			this.CurrencyID = Constant.CurrencyType.convertFromCurrencyObjectOfBS(transInfo.getCurrencyType());
		}
		this.Direction = SETTConstant.DebitOrCredit.convertFromConstantObjectOfBS(transInfo.getDirection());
		this.CheckNo = transInfo.getCheckNo();
		this.CheckType = transInfo.getCheckType();
		this.TransAction = new Date(transInfo.getTransactionTime().getTime());
		this.TransactionType = transInfo.getTransactionType();
		this.Abstract = transInfo.getAbstract();
		this.Comment = transInfo.getComment();
		this.BankType = -1;
		this.transNoOfBank = transInfo.getIdentity();
	}*/
	
	/**
	 * @return Returns the abstract.
	 */
	public String getAbstract()
	{
		return Abstract;
	}
	/**
	 * @param abstract1 The abstract to set.
	 */
	public void setAbstract(String abstract1)
	{
		Abstract = abstract1;
	}
	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName()
	{
		return AccountName;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName)
	{
		AccountName = accountName;
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		AccountNo = accountNo;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount()
	{
		return Amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount)
	{
		Amount = amount;
	}
	/**
	 * @return Returns the bankType.
	 */
	public long getBankType()
	{
		return BankType;
	}
	/**
	 * @param bankType The bankType to set.
	 */
	public void setBankType(long bankType)
	{
		BankType = bankType;
	}
	/**
	 * @return Returns the branchName.
	 */
	public String getBranchName()
	{
		return BranchName;
	}
	/**
	 * @param branchName The branchName to set.
	 */
	public void setBranchName(String branchName)
	{
		BranchName = branchName;
	}
	/**
	 * @return Returns the checkNo.
	 */
	public String getCheckNo()
	{
		return CheckNo;
	}
	/**
	 * @param checkNo The checkNo to set.
	 */
	public void setCheckNo(String checkNo)
	{
		CheckNo = checkNo;
	}
	/**
	 * @return Returns the checkType.
	 */
	public String getCheckType()
	{
		return CheckType;
	}
	/**
	 * @param checkType The checkType to set.
	 */
	public void setCheckType(String checkType)
	{
		CheckType = checkType;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment()
	{
		return Comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment)
	{
		Comment = comment;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the direction.
	 */
	public long getDirection()
	{
		return Direction;
	}
	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(long direction)
	{
		Direction = direction;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getID()
	{
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id)
	{
		ID = id;
	}
	/**
	 * @return Returns the oppositeAccountName.
	 */
	public String getOppositeAccountName()
	{
		return OppositeAccountName;
	}
	/**
	 * @param oppositeAccountName The oppositeAccountName to set.
	 */
	public void setOppositeAccountName(String oppositeAccountName)
	{
		OppositeAccountName = oppositeAccountName;
	}
	/**
	 * @return Returns the oppositeAccountNo.
	 */
	public String getOppositeAccountNo()
	{
		return OppositeAccountNo;
	}
	/**
	 * @param oppositeAccountNo The oppositeAccountNo to set.
	 */
	public void setOppositeAccountNo(String oppositeAccountNo)
	{
		OppositeAccountNo = oppositeAccountNo;
	}
	/**
	 * @return Returns the oppositeBranchName.
	 */
	public String getOppositeBranchName()
	{
		return OppositeBranchName;
	}
	/**
	 * @param oppositeBranchName The oppositeBranchName to set.
	 */
	public void setOppositeBranchName(String oppositeBranchName)
	{
		OppositeBranchName = oppositeBranchName;
	}
	/**
	 * @return Returns the transAction.
	 */
	public Date getTransAction()
	{
		return TransAction;
	}
	/**
	 * @param transAction The transAction to set.
	 */
	public void setTransAction(Date transAction)
	{
		TransAction = transAction;
	}
	/**
	 * @return Returns the transactionType.
	 */
	public String getTransactionType()
	{
		return TransactionType;
	}
	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType)
	{
		TransactionType = transactionType;
	}
	/**
	 * @return Returns the trunInSend.
	 */
	public Timestamp getTurnInSend()
	{
		return turnInSend;
	}
	/**
	 * @param trunInSend The trunInSend to set.
	 */
	public void setTurnInSend(Timestamp turnInSend)
	{
		this.turnInSend = turnInSend;
	}
	/**
	 * @return Returns the sTurnInRemind.
	 */
	public String getSTurnInRemind() {
		return sTurnInRemind;
	}
	/**
	 * @param turnInRemind The sTurnInRemind to set.
	 */
	public void setSTurnInRemind(String turnInRemind) {
		sTurnInRemind = turnInRemind;
	}
	/**
	 * @return Returns the isDeletedByBank.
	 */
	public long getIsDeletedByBank() {
		return isDeletedByBank;
	}
	/**
	 * @param isDeletedByBank The isDeletedByBank to set.
	 */
	public void setIsDeletedByBank(long isDeletedByBank) {
		this.isDeletedByBank = isDeletedByBank;
	}
	/**
	 * @return Returns the number.
	 */
	public long getNumber() {
		return number;
	}
	/**
	 * @param number The number to set.
	 */
	public void setNumber(long number) {
		this.number = number;
	}
	/**
	 * @return Returns the sumAmount.
	 */
	public double getSumAmount() {
		return sumAmount;
	}
	/**
	 * @param sumAmount The sumAmount to set.
	 */
	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}
}
