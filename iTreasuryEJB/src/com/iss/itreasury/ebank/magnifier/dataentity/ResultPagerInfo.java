package com.iss.itreasury.ebank.magnifier.dataentity;

import java.util.List;

public class ResultPagerInfo
{
	private long page = 0;// ��ǰҳ��
	private long total = 0;// ������
	private List rows = null;// ����
	//private String usertext = null;// �û��Զ�������
	//private String userNoHtmlText = null;//�û��Զ�������(no html��ʾ������excel)

	public long getPage()
	{
		return page;
	}

	public void setPage(long page)
	{
		this.page = page;
	}

	public long getTotal()
	{
		return total;
	}

	public void setTotal(long total)
	{
		this.total = total;
	}

	public List getRows()
	{
		return rows;
	}

	public void setRows(List rows)
	{
		this.rows = rows;
	}

/*	public String getUsertext() {
		return usertext;
	}

	public void setUsertext(String usertext) {
		this.usertext = usertext;
	}

	public String getUserNoHtmlText() {
		return userNoHtmlText;
	}

	public void setUserNoHtmlText(String userNoHtmlText) {
		this.userNoHtmlText = userNoHtmlText;
	}*/
}
