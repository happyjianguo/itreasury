package com.iss.itreasury.securities.query.bizlogic;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import java.sql.Timestamp;
import com.iss.itreasury.util.AppContext;
import com.iss.system.BaseObjectFactory;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.securities.query.dataentity.QuerySecuritiesParam;
import com.iss.itreasury.securities.exception.SecuritiesException;

/**
 * @author gqfang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QuerySecuritiesBean
{
	protected Log4j log    =  new Log4j(Constant.ModuleType.SECURITIES,this);
	
	private StringBuffer sbSelect   = new StringBuffer();
	private StringBuffer sbFrom     = new StringBuffer();
	private StringBuffer sbWhere    = new StringBuffer();
	private StringBuffer sbOrderBy  = new StringBuffer();
	
	public final static int orderBy_SecuritiesCode1   = 1;
	public final static int orderBy_SecuritiesCode2   = 2;
	public final static int orderBy_ShorName          = 3;
	public final static int orderBy_TypeId            = 4;
	public final static int orderBy_ExchageCenterId   = 5;
	public final static int orderBy_Promotor          = 6;
	public final static int orderBy_IsSueDate         = 7;
	public final static int orderBy_ListingDate       = 8;
	public final static int orderBy_ValueDate         = 9;
	public final static int orderBy_InterestRate      = 10;
	public final static int orderBy_InterestCycle     = 11;
	public final static int orderBy_Term              = 12;
	
	/**
	 * 
	 * 返回证券查询的SQL语句
	 * @param queryParam
	 */
	private void getSQL(QuerySecuritiesParam queryParam)
	{
		System.out.println("###  证券查询的SQL语句  ###");
		long securitiesTypeId   = queryParam.getTypeId();
		
		sbSelect.append(" * \n");
		sbFrom.append("   \n");
		
		sbFrom.append("( Select \n");
		sbFrom.append(" SEC_Securities.*  \n");
		
		sbFrom.append(" From \n");
		sbFrom.append(" SEC_Securities , SEC_SecuritiesType \n");
		
		sbFrom.append(" Where \n");
		sbFrom.append(" SEC_Securities.typeId = SEC_SecuritiesType.id \n");
		sbFrom.append(" And SEC_SecuritiesType.id = "+ securitiesTypeId);
		sbFrom.append(" ) ");
		
		sbWhere.append(" ");
		String strDesc  = queryParam.getDesc() == 1 ? " DESC " : " ASC ";
		switch ( (int)queryParam.getOrderField())
		{
			case orderBy_SecuritiesCode1 :
				sbOrderBy.append(" \n Order By SecuritiesCode1 " + strDesc);
				break;
			case orderBy_SecuritiesCode2 :
				sbOrderBy.append(" \n Order By SecuritiesCode2 " + strDesc);
				break;
			case orderBy_ShorName :
				sbOrderBy.append(" \n Order By ShortName " + strDesc);
				break;
			case orderBy_TypeId :
				sbOrderBy.append(" \n Order By TypeId " + strDesc);
				break;
			case orderBy_ExchageCenterId :
				sbOrderBy.append(" \n Order By ExchangeCenterId " + strDesc);
				break;
			case orderBy_Promotor :
				sbOrderBy.append(" \n Order By Promotor " + strDesc);
				break;
			case orderBy_IsSueDate :
				sbOrderBy.append(" \n Order By IsSueStartDate " + strDesc);
				break;
			case orderBy_ListingDate :
				sbOrderBy.append(" \n Order By ListingDate " + strDesc);
				break;
			case orderBy_ValueDate :
				sbOrderBy.append(" \n Order By ValueDate " + strDesc);
				break;
			case orderBy_InterestRate :
				sbOrderBy.append(" \n Order By InterestRate " + strDesc);
				break;
			case orderBy_InterestCycle :
				sbOrderBy.append(" \n Order By InterestCycle " + strDesc);
				break;
			case orderBy_Term : 
				sbOrderBy.append(" \n Order By Term " + strDesc);
				break;
		} 
	}
	public PageLoader querySecuritiesInfo(QuerySecuritiesParam queryParam) throws SecuritiesException
	{
		System.out.println("查询证券类别下所有证券的详细信息。");
		getSQL(queryParam);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
								getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(),
		                          sbFrom.toString(),
		                          sbSelect.toString(),
		                          sbWhere.toString(),
		                          (int)Constant.PageControl.CODE_PAGELINECOUNT,
		                          "com.iss.itreasury.securities.query.dataentity.QuerySecuritiesInfo",
		                          null);
		pageLoader.setOrderBy(sbOrderBy.toString());
		return pageLoader;
	}
}