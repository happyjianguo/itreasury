package com.iss.itreasury.securities.notice.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * 
 * @author fanyang
 * @version Date of Creation 2004-3-1
 */
public class NoticeWithSecuritiesInfo extends SECBaseDataEntity { 
	private long id = -1;
	private long securitiesId = -1; //证券id
	private long noticeId = -1; //交割单ID
	private long quantity = -1; //交易数量
	private double price = 0.0; //交易价格
	private long statusId = -1; //状态
	
	//为债券承销加
	private String rateName = "";//利率(字符串)
	private long settlementTypeId = -1;//付息频率
	private double sumAmount = 0.0;//金额小计 
	private double faceAmount = 100;//面值常量
	private double faceSumAmount = 0.0;//票面小计
	
	//债券承销收款
	private long relatedId = -1;//和已收取债券的记录的关联id
	
	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public long getNoticeId()
	{
		return noticeId;
	}

	/**
	 * @return
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @return
	 */
	public long getQuantity()
	{
		return quantity;
	}

	/**
	 * @return
	 */
	public long getSecuritiesId()
	{
		return securitiesId;
	}

	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", id);
	}

	/**
	 * @param l
	 */
	public void setNoticeId(long l)
	{
		noticeId = l;
		putUsedField("noticeId", noticeId);
	}

	/**
	 * @param d
	 */
	public void setPrice(double d)
	{
		price = d;
		putUsedField("price", price);
	}

	/**
	 * @param l
	 */
	public void setQuantity(long l)
	{
		quantity = l;
		putUsedField("quantity", quantity);
	}

	/**
	 * @param l
	 */
	public void setSecuritiesId(long l)
	{
		securitiesId = l;
		putUsedField("securitiesId", securitiesId);
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
		putUsedField("statusId", statusId);
	}

	/**
	 * @return
	 */
	public String getRateName()
	{
		return rateName;
	}

	/**
	 * @return
	 */
	public long getSettlementTypeId()
	{
		return settlementTypeId;
	}

	/**
	 * @return
	 */
	public double getSumAmount()
	{
		return sumAmount;
	}

	/**
	 * @param string
	 */
	public void setRateName(String string)
	{
		rateName = string;
		putUsedField("rateName", rateName);
	}

	/**
	 * @param l
	 */
	public void setSettlementTypeId(long l)
	{
		settlementTypeId = l;
		putUsedField("settlementTypeId", settlementTypeId);
	}

	/**
	 * @param d
	 */
	public void setSumAmount(double d)
	{
		sumAmount = d;
		putUsedField("sumAmount", sumAmount);
	}

	/**
	 * @return
	 */
	public double getFaceAmount()
	{
		return faceAmount;
	}

	/**
	 * @param d
	 */
	public void setFaceAmount(double d)
	{
		faceAmount = d;
	}

	/**
	 * @return
	 */
	public long getRelatedId()
	{
		return relatedId;
	}

	/**
	 * @param l
	 */
	public void setRelatedId(long l)
	{
		relatedId = l;
		putUsedField("relatedId", relatedId);
	}

	/**
	 * @return
	 */
	public double getFaceSumAmount()
	{
		return faceSumAmount;
	}

	/**
	 * @param d
	 */
	public void setFaceSumAmount(double d)
	{
		faceSumAmount = d;
		putUsedField("faceSumAmount", faceSumAmount);
	}

}