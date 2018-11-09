<%@ page contentType="application/msexcel;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<!--类导入部分开始-->
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="java.sql.Timestamp"%>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.util.ObjectUtil"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.obquery.bizlogic.OBTodayBalanceBiz"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBTodayBalanceResultInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<!--类导入部分结束-->
 
<!--类导入部分结束-->


<%
	response.setHeader("Content-Disposition","; filename=\treport.xls");
	
%>
<%
  
  int p=0;
 
 
    try
	{
		// 用户登录检测 
		//请求检测
		//if(!FOBHtml.validateRequest(out, request,response)) return;

 
      /*  if (sessionMng.isLogin() == false)
        {
			OBHtml.showCommonMessage(out,Notes.CODE_COMMONMESSAGE_LOGIN, sessionMng, Notes.CODE_OB_MODULE_TYPE_ZJ, "中油财务有限责任公司", Notes.CODE_SHOWMENU_TYPE_SHOW,"");
			out.flush();
			return;
        }
*/
        // 判断用户是否有权限
		//if (sessionMng.hasRight(request) == false)
       //{
          //  out.println(sessionMng.hasRight(request));
        	//OBHtml.showCommonMessage(out,Notes.CODE_COMMONMESSAGE_PRIVILEGE, sessionMng, Notes.CODE_OB_MODULE_TYPE_ZJ, "NO WAY", Notes.CODE_SHOWMENU_TYPE_SHOW,"");
        	//out.flush();
        	//return;
        //}
		//强制转换
			 
		OBTodayBalanceResultInfo[] Results = (OBTodayBalanceResultInfo[])sessionMng.getSumResult("results");
		 
		/**打印方法*/
		IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);

%>
	<html>
<head>
<title></title>
<!--
<link rel="stylesheet" href="/webobf/template.css" type="text/css">
-->
<style type="text/css">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://www.meadroid.com/scriptx/ScriptX.cab#Version=5,60,0,360"></object>

</style>
</head>
	<body text="#000000" bgcolor="#FFFFFF">
		<table width="960" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="15%">&nbsp;</td>
				<td width="70%" align="center"><b><font style="font-size:22px"></font></b></td>
				<td width="15%">&nbsp;</td>
			</tr>
		</table>
			<br>
			
	 
	<tr align=middle>
		<td  class="td-rightbottom" colspan="2" height=4>当日余额查询</td>
	</tr>
	<TR>
			<TD>
				<TABLE border=0 cellspacing="1" width="100%" class="table1" >
					<TBODY>
						
						<TR align=middle >
				            <TD class=td-rightbottom width="30%">账户类型</TD>
				            <TD class=td-rightbottom width="35%">账号</TD>
				            <TD class=td-rightbottom width="30%">资金余额</TD>
					 
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
									if(bHead)//是第一个中油财务的记录 则显示----->银行名称：中油财务
									{ bHead = false;
%>
									<tr class="alter1">
									<td colspan="3"  align="left"  class=td-topright height="19" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行名称：财务公司 </td>
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?AccounttypeName+" 小计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
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


								//中油财务总计值
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
			 		 
						<TR align=middle >
				            <TD class=td-topright ><%=AccounttypeName%></TD>
				            <TD class=td-topright ><%=accountNo%></TD>
				            <TD class=td-topright align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							 
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?AccounttypeName+" 小计":"&nbsp;"%>
											</TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
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
					<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?"财务公司 总计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)cpfTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.显示总计结束

//完成中油财务内部账户显示------------------------------------------------------------------------------------------ 
 
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
															<TR align=middle >
															<TD class=td-topright>&nbsp;</TD>
															<TD class=td-topright align=right><%=j == 0?bankName+" 总计":"&nbsp;"%></TD>
															<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
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
									<TR align=middle >
															
															<TD class=td-topright align=right><%=j == 0?AreaName+" 总计":"&nbsp;"%></TD>
															<TD class=td-topright>&nbsp;</TD>
															<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)areaTotal.get(keys[j])).doubleValue(),2)%></TD>
															</TR>	
					<%
									}//end for.
								}//end if.显示总计结束
									 
%>									
									<tr class="alter2">
							        <td colspan="3"  align="left" bgcolor="#FDF5DF"  class=td-topright height="19">&nbsp;&nbsp;区域中心：<%=Results[n].getAreaName()%></td>
									</tr>

									<tr class="alter1">
									<td colspan="3"  align="left"  class=td-topright height="19">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行名称：<%=Results[n].getBankName()%></td>
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?bankName+" 总计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   									
<%				
										}//end for
%>
	</TR>
											<tr class="alter1">
											<td colspan="3"  align="left"  class=td-topright height="19" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行名称：<%=Results[n].getBankName()%></td>
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
			 		 
						<TR align=middle >
				            <TD class=td-topright ><%=AccounttypeName%></TD>
				            <TD class=td-topright ><%=accountNo%></TD>
				            <TD class=td-topright align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							 
							 
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
	
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?bankName+" 总计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
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
					<TR align=middle >
											
											<TD class=td-topright align=right><%=j == 0?AreaName+" 总计":"&nbsp;"%></TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)areaTotal.get(keys[j])).doubleValue(),2)%></TD>
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
					<TR align=middle >
											
											<TD class=td-topright align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=j == 0?"活期账户 总计":"&nbsp;"%></TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)CURRENTTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.显示总计结束
	}
	else
	{
%>					
										<TR align="middle" bordercolor="#999999" class="td-topright">
											<TD class="td-topright" height="20">&nbsp;
											</TD>
											<TD class="td-topright">&nbsp;
											</TD>
											<TD class="td-topright">&nbsp;
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
							
							boolean bHead = true;
							p=11;
							
							HashMap accounttypeTotal = new HashMap(32);
							HashMap accountTotal = new HashMap(32);
							 
							for(int i = 0;i<Results.length;i++)
							{
							
								if(Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.FIXED||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.NOTIFY)
								{//是定期账户组
								  if(bHead)//是第一个中油财务的记录 则显示----->银行名称：中油财务
									{ bHead = false;
%>
								<br>	
								  
								<tr align=middle>
									<td class=td-rightbottom  colspan="2" height=4> 定期账户</td>
								</tr>
								<TR>
								<TD>
									<TABLE border=0 cellspacing="1" width="100%" class="table1">
										<TBODY>
										<TR align=middle >
										<TD class=td-rightbottom width="10%">账户类型
										</TD>
										<TD class=td-rightbottom width="10%">账号/单据号
										</TD>
										<TD class=td-rightbottom width="10%">
										</TD>
										<TD class=td-rightbottom width="10%">
										</TD>
										<TD class=td-rightbottom width="15%">存款金额
										</TD>
										<TD class=td-rightbottom width="15%">存款余额
										</TD>
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>				
											<TD class=td-topright align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?AccounttypeName+" 总计":"&nbsp;"%></TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
									}//end for.
								}//end if.显示总计结束
								Accounttype = Results[i].getAccounttype();
 								AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
									
%>
								<TR align=middle >
											 <TD class=td-topright ><%=AccounttypeName%></TD>
											 <TD class=td-topright ><%=Results[i].getAccountNo()%></TD>
											 <TD class=td-topright>&nbsp;</TD>
											 <TD class=td-topright>&nbsp;</TD>
											 <TD class=td-topright>&nbsp;</TD>
											 <TD class=td-topright>&nbsp;</TD>
							    </TR>	
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>				
											<TD class=td-topright align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.显示account合计结束
%>
								<TR align=middle >
											 <TD class=td-topright ><%=AccounttypeName%></TD>
											 <TD class=td-topright ><%=Results[i].getAccountNo()%></TD>
											 <TD class=td-topright>&nbsp;</TD>
											 <TD class=td-topright>&nbsp;</TD>
											 <TD class=td-topright>&nbsp;</TD>
											 <TD class=td-topright>&nbsp;</TD>
							    </TR>	
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
								//StartDate = Results[i].getStartDate();
								//EndDate = Results[i].getEndDate();
								//Term =Results[i].getTerm();
						 
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
			 		 
						<TR align=middle >
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright ><%=billNO%></TD>
                            <TD class=td-topright ></TD>
							<TD class=td-topright ></TD>
							<TD class=td-topright align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							<TD class=td-topright align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							 
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
	
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>				
											<TD class=td-topright align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?AccounttypeName+" 总计":"&nbsp;"%></TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
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
						 
							
							HashMap accounttypeTotal = new HashMap(32);
							HashMap accountTotal = new HashMap(32);
							accounttypeTotal.clear();
							accountTotal.clear();
 
							for(int i = 0;i<Results.length;i++)
							{
							
								if(Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.OTHERLOAN||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.TRUST||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.DISCOUNT||Results[i].getAccountgroupid() ==SETTConstant.AccountGroupType.CONSIGN)
								{//是定期账户组
								  if(bHead)//是第一个中油财务的记录 则显示----->银行名称：中油财务
									{ bHead = false;
%>
								<br>	
								  
								<tr align=middle>
									<td  class=td-rightbottom colspan="2" height=4> 贷款账户</td>
								</tr>
								<TR>
								<TD>
									<TABLE border=0 cellspacing="1" width="100%" class="table1">
										<TBODY>
										<TR align=middle >
										<TD class=td-rightbottom width="8%">账户类型
										</TD>
										<TD class=td-rightbottom width="15%">账号/合同号
										</TD>
										<TD class=td-rightbottom width="20%">借款单位
										</TD>	
										<TD class=td-rightbottom width="11%">
										</TD>
										<TD class=td-rightbottom width="5%">
										</TD>
										<TD class=td-rightbottom width="14%">贷款金额
										</TD>
										<TD class=td-rightbottom width="14%">贷款余额
										</TD>
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>	
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?AccounttypeName+" 总计" :"&nbsp;"%></TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											
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
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
										 
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
			 		 
						<TR align=middle >
				            <TD class=td-topright ><%=AccounttypeName%></TD>
				            <TD class=td-topright ><%=Results[i].getAccountNo()%></TD>
				            <TD class=td-topright ><%=clientName%></TD>
							<TD class=td-topright ></TD>
							<TD class=td-topright ></TD>
							<TD class=td-topright align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
							<TD class=td-topright align=right ><%=NameRef.getCurrencySymbolbyId(currencyid)%><%=strCurrenBalance%></TD>
						 
							 
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
	
											<TR align=middle >
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?"小计":"&nbsp;"%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
										 
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
											<TR align=middle>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=j == 0?AccounttypeName+" 总计":"&nbsp;"%></TD>
											<TD class=td-topright>&nbsp;</TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=td-topright align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											
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
			<td width="450"><div align="left"><span  class=td-topright>查询日期：<%=DataFormat.getDateString()%></span> 
			</div></td>
			</tr>
			</table>			
	
		
		
	</table>
		<script language= "javascript">
	factory.printing.Print(true);
</script>

		<!--页面脚本元素结束-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
	 
	}
	//显示页面尾

	/**页面显示结束*/
%>
		