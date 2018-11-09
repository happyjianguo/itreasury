<%
/**页面功能说明
 * 页面名称 ：a122-v.jsp
 * 页面功能 : 当日余额查询
 * 作    者 ：barneyliu
 * 日    期 ：2005-11-17
 * 简单实现说明：
 *				1、查询结果显示列表
 * 特殊说明 ：
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>

<!--类导入部分开始-->
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.ObjectUtil"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.obquery.bizlogic.OBTodayBalanceBiz"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBTodayBalanceResultInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

 <!--类导入部分结束-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	int p=0;
 
	String strRootPath = request.getContextPath();//http://xxxx/../cpob
    
    try
	{
			 //请求检测
			//if(!FOBHtml.validateRequest(out, request,response)) return;

        //登录检测
		 /* 
		   if (sessionMng.isLogin() == false)
			{
			FOBHtml.showCommonMessage(out, sessionMng, "","", 1, "");
			out.flush();
			return;
        }
		*/	//判断是否有权限
		//if (sessionMng.hasRight(request) == false)
		// {
			//FOBHtml.showCommonMessage(out, sessionMng, "NO WAY","", 1, "");
			//out.flush();
			//return;
		//}
		 
		 
		OBTodayBalanceResultInfo[] Results = (OBTodayBalanceResultInfo[])request.getAttribute("OBtodaybalanceresults");
		sessionMng.setSumResult("results",Results);
 
		/**业务逻辑结束*/
		 
		/**页面显示开始*/

       OBHtml.showOBHomeHead(out, sessionMng, "汇总账户当日余额", 1);

%>

<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<jsp:include page="/ShowMessage.jsp"/>
<!--引入js文件-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>

<safety:resources />
<!--引入js文件-->

<!--页面表单开始-->
<form name="frmV002" method="post" action="">
<!--页面控制元素开始-->
 	<input name="strAction" type="hidden" value="find">
	<input name="strSuccessPageURL" type="hidden" value="">
	<input name="strFailPageURL" type="hidden" value="">
  
<!--页面控制元素结束-->
	
	<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="tableform">
	<tr class="tableheader">
		<td colspan="2" height=4 class=FormTitle>汇总账户当日余额查询</td>
	</tr>
	<tr class="tableheader">
							<td colspan="2" height=4> 活期账户</td>
	</tr>
	<TR>
			<TD>
				<TABLE border=0 borderColor=#999999 class=ItemList cellspacing="1" width="100%">
					<TBODY>
						
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemTitle width="30%">账户类型</TD>
				            <TD class=ItemTitle width="35%">账号</TD>
				            <TD class=ItemTitle width="30%">资金余额</TD>
					 
<%
//开始中油财务内部账户显示------------------------------------------------------------------------------------------
						if( Results != null && Results.length >0 )
						{
							long    Accounttype =	 -1;
							String  AccounttypeName = null;
							long    currencyid =	 -1;
						 	long    accountID =		 -1;
							String  accountNo =    null;
							double CurrenBalance = Double.NaN;
							String strCurrenBalance = null;
							long	Areaid =		 -1;
							String	AreaName =	   null;
							long	Bankid	=		 -1;
							String	bankName =	   null;
							boolean bHead = true;
							
							HashMap accounttypeTotal = new HashMap(32);
							HashMap cpfTotal = new HashMap(32);
							HashMap bankTotal = new HashMap(32);
							HashMap areaTotal = new HashMap(32);
							HashMap CURRENTTotal = new HashMap(32);

							for(int i = 0;i<Results.length;i++)
							{
								if(Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.CURRENT)
								{//是活期的账户
								if(Results[i].getAreaid() ==-1 && Results[i].getBankid() ==-9)
								{	//是中油财务的内部账户
									if(bHead)//是第一个财务公司的记录 则显示----->银行名称：财务公司
									{ bHead = false;
%>
									<tr class="alter1">
									<td colspan="3"  align="left" class="graytext" height="19" bgcolor="#DFDFDF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行名称：财务公司 </td>
									</tr>
<%									}
								if(Results[i].getAccounttype() != Accounttype )
								{//有accounttype合计，则显示accounttype合计
									if(accounttypeTotal.size() > 0)
									{
										Set set = accounttypeTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//需增加对编号排序的方法
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" 小计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
					
<%				
										}//end for
									}//end if.显示accounttype合计结束
				
									//清空accounttype合计
									accounttypeTotal.clear();
									currencyid = Results[i].getCurrencyID();
									Accounttype = Results[i].getAccounttype();	
									AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
								}
								else if(Results[i].getCurrencyID() != currencyid)
								{
									currencyid = Results[i].getCurrencyID();
								}
								accountNo = Results[i].getAccountNo();
								CurrenBalance = Results[i].getCurrenBalance();
								strCurrenBalance = DataFormat.formatDisabledAmount(CurrenBalance,2);

								//同一个accountype下分币种统计
								Long key = new Long(currencyid);//用币种作key
								Double dTemp = (Double)accounttypeTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								accounttypeTotal.put(key,new Double(d));


								//财务公司总计值
								dTemp = (Double)cpfTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();			
								d = CurrenBalance + d;								
								cpfTotal.put(key,new Double(d));
								

								//活期总计
								dTemp = (Double)CURRENTTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();								
								d = CurrenBalance + d;								
								CURRENTTotal.put(key,new Double(d));
%>								
			 		 
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemBody ><%=AccounttypeName%></TD>
				            <TD class=ItemBody ><%=accountNo%></TD>
				            <TD class=ItemBody align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							 
						</TR>
<%					  }
					}
				}//end for
				//显示最后一个accounttype合计
					if(accounttypeTotal.size() > 0)
 					{
						Set set = accounttypeTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//需增加对编号排序的方法
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" 小计":"&nbsp;"%>
											</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>		
<%				
			}//end for
		}//end if.显示最后一个客户合计结束	
			
		//显示总合计值
		if(cpfTotal.size() > 0)
		{
			Set set = cpfTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//需增加对编号排序的方法
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
					<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"财务公司 总计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)cpfTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.显示总计结束

//完成财务公司内部账户显示------------------------------------------------------------------------------------------ 
 
//银行账户的显示---------------------------------------------------------------------------------------------------
					  
						    Accounttype =	 -1;
							AccounttypeName = null;
							currencyid =	 -1;
						    accountID =		 -1;
						    accountNo =    null;
						    CurrenBalance = Double.NaN;
						    strCurrenBalance = null;
						 	Areaid =		 -1;
						 	AreaName =	   null;
						 	Bankid	=		 -1;
							bankName =	   null;
							bHead = true;
							bankTotal.clear();
							areaTotal.clear();
						for(int n = 0;n<Results.length;n++)
							{
								if(Results[n].getAccountgroupid() ==SETTConstant.AccountGroupType.CURRENT && Results[n].getBankid()!=-9)
								{//是银行的账户
								if(Results[n].getAreaid() != Areaid)
								{	//是指定区域中心的账户
									if(bankTotal.size() > 0)
									{
										Set set = bankTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//需增加对编号排序的方法
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
												
										for(int j = 0; j < keys.length; j++)
										{					
				%> 
															<TR align=center bgColor=#cccccc borderColor=#999999>
															<TD class=ItemBody>&nbsp;</TD>
															<TD class=ItemBody align=right><%=j == 0?bankName+" 总计":"&nbsp;"%></TD>
															<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
															</TR>		
				<%				
										}//end for
									}//end if.显示最后一个客户合计结束	
							
								//显示总合计值
								if(areaTotal.size() > 0)
								{
									Set set = areaTotal.keySet();
									Long[] keys = (Long[])set.toArray(new Long[0]);
									//需增加对编号排序的方法
									keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
									
									for(int j = 0; j < keys.length; j++)
									{
				%>
									<TR align=center bgColor=#cccccc borderColor=#999999>
															
															<TD class=ItemBody align=right><%=j == 0?AreaName+" 总计":"&nbsp;"%></TD>
															<TD class=ItemBody>&nbsp;</TD>
															<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)areaTotal.get(keys[j])).doubleValue(),2)%></TD>
															</TR>	
					<%
									}//end for.
								}//end if.显示总计结束
									 
%>									
									<tr class="alter2">
							        <td colspan="3"  align="left" bgcolor="#FDF5DF" class="graytext" height="19">&nbsp;&nbsp;区域中心：<%=Results[n].getAreaName()%></td>
									</tr>

									<tr class="alter1">
									<td colspan="3"  align="left" class="graytext" height="19" bgcolor="#DFDFDF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行名称：<%=Results[n].getBankName()%></td>
									</tr>
<%								
								areaTotal.clear();	
								bankTotal.clear();
								currencyid = Results[n].getCurrencyID();
								accountID =	Results[n].getAccountID();
								accountNo = Results[n].getAccountNo();
								Areaid = Results[n].getAreaid();
								AreaName = Results[n].getAreaName();


							}
							else if(Results[n].getCurrencyID() != currencyid)
							{
								currencyid = Results[n].getCurrencyID();
							}
						
						 
								if(Results[n].getBankid() != Bankid )
								{//有bank合计，则显示bank合计
									if(bankTotal.size() > 0)
									{
										Set set = bankTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//需增加对编号排序的方法
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?bankName+" 总计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   									
<%				
										}//end for
%>
	</TR>
											<tr class="alter1">
											<td colspan="3"  align="left" class="graytext" height="19" bgcolor="#DFDFDF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行名称：<%=Results[n].getBankName()%></td>
											</tr>
<%
									}//end if.显示bank合计结束
				
									//清空bankTotal合计
									bankTotal.clear();
									currencyid = Results[n].getCurrencyID();
									bankName = Results[n].getBankName();
									Bankid = Results[n].getBankid();
									Areaid = Results[n].getAreaid();
									AreaName = Results[n].getAreaName();
									Accounttype = Results[n].getAccounttype();
									AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
								}
								else if(Results[n].getCurrencyID() != currencyid)
								{
									currencyid = Results[n].getCurrencyID();
								}
								accountNo = Results[n].getAccountNo();
								CurrenBalance = Results[n].getCurrenBalance();
								strCurrenBalance = DataFormat.formatDisabledAmount(CurrenBalance,2);

								//同一个bank下分币种统计
								Long key = new Long(currencyid);//用币种作key
								Double dTemp = (Double)bankTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								bankTotal.put(key,new Double(d));


								//区域中心总计值
								dTemp = (Double)areaTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();			
								d = CurrenBalance + d;								
								areaTotal.put(key,new Double(d));
								

								//活期总计
								dTemp = (Double)CURRENTTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();								
								d = CurrenBalance + d;								
								CURRENTTotal.put(key,new Double(d));
%>								
			 		 
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemBody ><%=AccounttypeName%></TD>
				            <TD class=ItemBody ><%=accountNo%></TD>
				            <TD class=ItemBody align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							 
							 
						</TR>
<%					   
					}
				}//end for
				//显示最后一个bank合计
					if(bankTotal.size() > 0)
					{
						Set set = bankTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//需增加对编号排序的方法
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
	
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?bankName+" 总计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>		
<%				
						}//end for
					}//end if.显示最后一个客户合计结束	
			
		//显示总合计值
		if(areaTotal.size() > 0)
		{
			Set set = areaTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//需增加对编号排序的方法
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
					<TR align=center bgColor=#cccccc borderColor=#999999>
											
											<TD class=ItemBody align=right><%=j == 0?AreaName+" 总计":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)areaTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.显示总计结束
		 
		if(CURRENTTotal.size() > 0)
		{
			Set set = CURRENTTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//需增加对编号排序的方法
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
					<TR align=center bgColor=#cccccc borderColor=#999999>
											
											<TD class=ItemBody align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=j == 0?"活期账户 总计":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)CURRENTTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.显示总计结束
	}
	else
	{
%>					
										<TR align="center" bordercolor="#999999" class="ItemBody">
											<TD class="ItemBody" height="20">&nbsp;
											</TD>
											<TD class="ItemBody">&nbsp;
											</TD>
											<TD class="ItemBody">&nbsp;
											</TD>
											 
										</TR>
<%	
	}
//完成银行账户的显示-------------------------------------------------------------------------------------------
%>
	</TBODY>
				</TABLE>
  			</TD>
		</TR>
 		 </table>
<%
//开始委托存款已放款显示-------------------------------------------------------------------------------------------
						
//完成委托存款未放款显示-------------------------------------------------------------------------------------------
%>
	 				
<%//开始定期显示-------------------------------------------------------------------------------------------	


 
					if( Results != null && Results.length >0 )
						{
							long    Accounttype =	 -1;
							String  AccounttypeName = null;
							long    currencyid =	 -1;
						 	long    accountID =		 -1;
							String  accountNo =    null;
							double CurrenBalance = Double.NaN;
							String strCurrenBalance = null;
							String  billNO   = null;
							Timestamp StartDate	= null;
							Timestamp EndDate = null;
							long Term = -1;
							long	mark    =        -1;
							String  remark	=		null;
							
							boolean bHead = true;
							p=11;
							
							HashMap accounttypeTotal = new HashMap(32);
							HashMap accountTotal = new HashMap(32);
							 
							for(int i = 0;i<Results.length;i++)
							{
							
								if(Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.FIXED||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.NOTIFY)
								{//是定期账户组
								  if(bHead)//是第一个 记录 则显示-----> 
									{ bHead = false;
%>
								<br>	
								<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="tableform">	 
								<tr class="tableheader">
									<td colspan="2" height=4> 定期账户 </td>
								</tr>
								<TR>
								<TD>
									<TABLE border=0 borderColor=#999999 class=ItemList cellspacing="1" width="100%">
										<TBODY>
										<TR align=center bgColor=#cccccc borderColor=#999999>
										<TD class=ItemTitle width="8%">账户类型</TD>
										<TD class=ItemTitle width="10%">账号/单据号</TD>	
										<TD class=ItemTitle width="10%"></TD>
										<TD class=ItemTitle width="12%"></TD>
										<TD class=ItemTitle width="15%">存款金额</TD>
										<TD class=ItemTitle width="15%">存款余额</TD>
<%									}
							if(Results[i].getAccounttype() != Accounttype)
							{
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//需增加对编号排序的方法
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>				
											<TD class=ItemBody align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.显示account合计结束


								if(accounttypeTotal.size() > 0)
								{
									Accounttype = Results[i-1].getAccounttype();
									AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
									Set set = accounttypeTotal.keySet();
									Long[] keys = (Long[])set.toArray(new Long[0]);
									//需增加对编号排序的方法
									keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
									
									for(int j = 0; j < keys.length; j++)
									{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" 总计":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
									}//end for.
								}//end if.显示总计结束
								Accounttype = Results[i].getAccounttype();
								AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
									
%>
<%					
								accounttypeTotal.clear();	
								accountTotal.clear();
								//StartDate = Results[i].getStartDate();
								//EndDate = Results[i].getEndDate();
								//Term =Results[i].getTerm();
								currencyid = Results[i].getCurrencyID();
								accountID =	Results[i].getAccountID();
								accountNo = Results[i].getAccountNo();
							}
							else if(Results[i].getCurrencyID() != currencyid)
							{
								currencyid = Results[i].getCurrencyID();
							}
					 
							  if(Results[i].getAccountID() != accountID )
								{//有accounttype合计，则显示accounttype合计
							 
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//需增加对编号排序的方法
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>				
											<TD class=ItemBody align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.显示account合计结束
%>
	
<%
				
									//清空account合计
									accountTotal.clear();
									currencyid = Results[i].getCurrencyID();
									accountID =	Results[i].getAccountID();
									accountNo = Results[i].getAccountNo();
								 
								}
								else if(Results[i].getCurrencyID() != currencyid)
								{
									currencyid = Results[i].getCurrencyID();
								}
								CurrenBalance = Results[i].getCurrenBalance();
								strCurrenBalance = DataFormat.formatDisabledAmount(CurrenBalance,2);

								//同一个account下分币种统计
								Long key = new Long(currencyid);//用币种作key
								Double dTemp = (Double)accountTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								accountTotal.put(key,new Double(d));


								//相同账户类型的总计值
								dTemp = (Double)accounttypeTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();			
								d = CurrenBalance + d;								
								accounttypeTotal.put(key,new Double(d));
 
%>								
			 		 
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemBody ><%=AccounttypeName%></TD>
				            <TD class=ItemBody ><%=Results[i].getAccountNo()%></TD>
                            <TD class=ItemBody ></TD>
							<TD class=ItemBody ></TD>
							<TD class=ItemBody align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							<TD class=ItemBody align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							 
						</TR>
<%					  }
	    			 
				}//end for
				//显示最后一个accounttype合计
					if(accountTotal.size() > 0)
					{
						Set set = accountTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//需增加对编号排序的方法
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
	
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>				
											<TD class=ItemBody align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
<%				
						}//end for
					}//end if.显示最后一个客户合计结束	
			
		//显示总合计值
		if(accounttypeTotal.size() > 0)
		{
			Set set = accounttypeTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//需增加对编号排序的方法
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" 总计":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
%>
	 
					</TBODY>
				</TABLE>
  			</TD>
		</TR>
							</table>

<%

		}//end if.显示总计结束
		    	 
	}
 
//完成定期显示----------------------------------------------------------------------------------------------------
%>
<%//开始贷款显示--------------------------------------------------------------------------------------------------	


 
					if( Results != null && Results.length >0 )
						{
							long    Accounttype =	 -1;
							String  AccounttypeName = null;
							long    currencyid =	 -1;
						 	long    accountID =		 -1;
							String  accountNo =    null;
							double CurrenBalance = Double.NaN;
							String strCurrenBalance = null;
							String  billNO   = null;
							String  clientName     = null;
							Timestamp StartDate	= null;
							Timestamp EndDate = null;
							long Term = -1;
							double Rate = 0.0;
							
							boolean bHead = true;
							p=19;
							
							HashMap accounttypeTotal = new HashMap(32);
							HashMap accountTotal = new HashMap(32);
							accounttypeTotal.clear();
							accountTotal.clear();
 
							for(int i = 0;i<Results.length;i++)
							{
							
								if(Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.OTHERLOAN||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.TRUST||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.DISCOUNT||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.CONSIGN)
								{//是贷款账户组
								  if(bHead)//是第一个中油财务的记录 则显示----->银行名称：中油财务
									{ bHead = false;
%>
								<br>	
								<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="tableform">	 
								<tr class="tableheader">
									<td colspan="2" height=4> 贷款账户</td>
								</tr>
								<TR>
								<TD>
									<TABLE border=0 borderColor=#999999 class=ItemList cellspacing="1" width="100%">
										<TBODY>
										<TR align=center bgColor=#cccccc borderColor=#999999>
										<TD class=ItemTitle width="12%">账户类型</TD>
										<TD class=ItemTitle width="12%">账号/合同号</TD>
										<TD class=ItemTitle width="20%">借款单位</TD>	
										<TD class=ItemTitle width="15%"></TD>
										<TD class=ItemTitle width="4%"></TD>
										<TD class=ItemTitle width="14%">贷款金额</TD>
										<TD class=ItemTitle width="14%">贷款余额</TD>
<%									}
							if(Results[i].getAccounttype() != Accounttype)
							{
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//需增加对编号排序的方法
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>	
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.显示account合计结束


								if(accounttypeTotal.size() > 0)
								{
									Accounttype = Results[i-1].getAccounttype();
									AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
									Set set = accounttypeTotal.keySet();
									Long[] keys = (Long[])set.toArray(new Long[0]);
									//需增加对编号排序的方法
									keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
									
									for(int j = 0; j < keys.length; j++)
									{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" 总计" :"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											
								 	   		</TR>	
	<%
									}//end for.
								}//end if.显示总计结束
								Accounttype = Results[i].getAccounttype();
								AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
								 
									%>
								
<%					
								accounttypeTotal.clear();	
								accountTotal.clear();
								currencyid = Results[i].getCurrencyID();
								accountID =	Results[i].getAccountID();
								accountNo = Results[i].getAccountNo();
								clientName = Results[i].getClientName();
							}
							else if(Results[i].getCurrencyID() != currencyid)
							{
								currencyid = Results[i].getCurrencyID();
							}
					 
							  if(Results[i].getAccountID() != accountID )
								{//有accounttype合计，则显示accounttype合计
							 
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//需增加对编号排序的方法
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
										 
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.显示account合计结束
%>	
<%
				
									//清空account合计
									accountTotal.clear();
									currencyid = Results[i].getCurrencyID();
									accountID =	Results[i].getAccountID();
									accountNo = Results[i].getAccountNo();
								 
								}
								else if(Results[i].getCurrencyID() != currencyid)
								{
									currencyid = Results[i].getCurrencyID();
								}
								billNO = Results[i].getBillNO();
								CurrenBalance = Results[i].getCurrenBalance();
								strCurrenBalance = DataFormat.formatDisabledAmount(CurrenBalance,2);
								StartDate = Results[i].getStartDate();
								EndDate = Results[i].getEndDate();
								Term = Results[i].getTerm();
								Rate = Results[i].getRate();
								clientName = Results[i].getClientName();
						 
								//同一个account下分币种统计
								Long key = new Long(currencyid);//用币种作key
								Double dTemp = (Double)accountTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								accountTotal.put(key,new Double(d));


								//相同账户类型的总计值
								dTemp = (Double)accounttypeTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();			
								d = CurrenBalance + d;								
								accounttypeTotal.put(key,new Double(d));
			 
%>								
			 		 
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemBody ><%=AccounttypeName%></TD>
				            <TD class=ItemBody ><%=Results[i].getAccountNo()%></TD>
				            <TD class=ItemBody ><%=clientName%></TD>
							<TD class=ItemBody ></TD>
							<TD class=ItemBody ></TD>
							<TD class=ItemBody align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							<TD class=ItemBody align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
						 
							 
						</TR>
<%					  }
	    			 
				}//end for
				//显示最后一个accounttype合计
					if(accountTotal.size() > 0)
					{
						Set set = accountTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//需增加对编号排序的方法
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
	
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
										 
								 	   		</TR>			 
<%				
						}//end for
					}//end if.显示最后一个客户合计结束	
			
		//显示总合计值
		if(accounttypeTotal.size() > 0)
		{
			Set set = accounttypeTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//需增加对编号排序的方法
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" 总计":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											
								 	   		</TR>	
	<%
			}//end for.
%>
	 
					</TBODY>
				</TABLE>
  			</TD>
		</TR>
							</table>

<%
		}//end if.显示总计结束

		    	 
 
	}
 
//完成贷款显示-------------------------------------------------------------------------------------------
%>
</form>
				 
	 
			<table width="98%">
			<tr>
			<td width="450"><div align="left"><span class="graytext">查询日期：<%=DataFormat.getDateString()%></span> 
			</div></td>
			</tr>
			</table>
 

			 
		<hr>
		  	<table width="98%">
					<TR align="right">
						<TD align="right" colspan=>
							 <input type="button" class="button" name="button1" value="返 回"  onclick="javascript:doBack();" onfocus="javascript:nextField='submitfunction'">
								&nbsp;	&nbsp;	&nbsp;
					 
							 <input type="button" class="button" name="button2" value="导 出"  onclick="javascrip:doExport();" onfocus="javascript:nextField='submitfunction'">
								&nbsp;	&nbsp;	&nbsp;
					 
							 <input type="button" class="button" name="button3" value="打 印"  onclick="javascript:doPrint();" onfocus="javascript:nextField='submitfunction'">					
						</TD>
						
					</TR>
				</TABLE>



<!--页面表单结束-->
<!--页面脚本开始-->
<script language="JavaScript">
	////setSubmitFunction("doPrint()");
	setFormName("frmV002");
</script>

<script language="javascript">
function doPrint()
{
   if (confirm("是否打印？"))
		{
			window.open('<%=strRootPath%>/accountinfo/a123-p.jsp');
         }
			
} 
function doExport()
{
   if (confirm("是否导出？"))
		{
			window.open('<%=strRootPath%>/accountinfo/a124-e.jsp');
         }
			
} 
  

function doBack()
{
	var strHref = "<%=strRootPath%>/accountinfo/a120-v.jsp";
	document.location.href= strHref;
}
</script>
<!--页面脚本元素结束-->


<%	
	/**
	* 显示文件尾
	*/
	OBHtml.showOBHomeEnd(out);
}
//异常处理
catch(Exception exp)
{
	Log.print(exp.getMessage());
	OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
}
%>

<%@ include file="/common/SignValidate.inc" %>