<%
/**ҳ�湦��˵��
 * ҳ������ ��a122-v.jsp
 * ҳ�湦�� : ��������ѯ
 * ��    �� ��barneyliu
 * ��    �� ��2005-11-17
 * ��ʵ��˵����
 *				1����ѯ�����ʾ�б�
 * ����˵�� ��
 * �޸���ʷ ��
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>

<!--�ർ�벿�ֿ�ʼ-->
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

 <!--�ർ�벿�ֽ���-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	int p=0;
 
	String strRootPath = request.getContextPath();//http://xxxx/../cpob
    
    try
	{
			 //������
			//if(!FOBHtml.validateRequest(out, request,response)) return;

        //��¼���
		 /* 
		   if (sessionMng.isLogin() == false)
			{
			FOBHtml.showCommonMessage(out, sessionMng, "","", 1, "");
			out.flush();
			return;
        }
		*/	//�ж��Ƿ���Ȩ��
		//if (sessionMng.hasRight(request) == false)
		// {
			//FOBHtml.showCommonMessage(out, sessionMng, "NO WAY","", 1, "");
			//out.flush();
			//return;
		//}
		 
		 
		OBTodayBalanceResultInfo[] Results = (OBTodayBalanceResultInfo[])request.getAttribute("OBtodaybalanceresults");
		sessionMng.setSumResult("results",Results);
 
		/**ҵ���߼�����*/
		 
		/**ҳ����ʾ��ʼ*/

       OBHtml.showOBHomeHead(out, sessionMng, "�����˻��������", 1);

%>

<!--������Ϣ����ҳ��,��ҳ����Ե������ڵ���ʽ�����Ѿ���׽�����쳣-->
<jsp:include page="/ShowMessage.jsp"/>
<!--����js�ļ�-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>

<safety:resources />
<!--����js�ļ�-->

<!--ҳ�����ʼ-->
<form name="frmV002" method="post" action="">
<!--ҳ�����Ԫ�ؿ�ʼ-->
 	<input name="strAction" type="hidden" value="find">
	<input name="strSuccessPageURL" type="hidden" value="">
	<input name="strFailPageURL" type="hidden" value="">
  
<!--ҳ�����Ԫ�ؽ���-->
	
	<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="tableform">
	<tr class="tableheader">
		<td colspan="2" height=4 class=FormTitle>�����˻���������ѯ</td>
	</tr>
	<tr class="tableheader">
							<td colspan="2" height=4> �����˻�</td>
	</tr>
	<TR>
			<TD>
				<TABLE border=0 borderColor=#999999 class=ItemList cellspacing="1" width="100%">
					<TBODY>
						
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemTitle width="30%">�˻�����</TD>
				            <TD class=ItemTitle width="35%">�˺�</TD>
				            <TD class=ItemTitle width="30%">�ʽ����</TD>
					 
<%
//��ʼ���Ͳ����ڲ��˻���ʾ------------------------------------------------------------------------------------------
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
								{//�ǻ��ڵ��˻�
								if(Results[i].getAreaid() ==-1 && Results[i].getBankid() ==-9)
								{	//�����Ͳ�����ڲ��˻�
									if(bHead)//�ǵ�һ������˾�ļ�¼ ����ʾ----->�������ƣ�����˾
									{ bHead = false;
%>
									<tr class="alter1">
									<td colspan="3"  align="left" class="graytext" height="19" bgcolor="#DFDFDF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������ƣ�����˾ </td>
									</tr>
<%									}
								if(Results[i].getAccounttype() != Accounttype )
								{//��accounttype�ϼƣ�����ʾaccounttype�ϼ�
									if(accounttypeTotal.size() > 0)
									{
										Set set = accounttypeTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//�����ӶԱ������ķ���
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" С��":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
					
<%				
										}//end for
									}//end if.��ʾaccounttype�ϼƽ���
				
									//���accounttype�ϼ�
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

								//ͬһ��accountype�·ֱ���ͳ��
								Long key = new Long(currencyid);//�ñ�����key
								Double dTemp = (Double)accounttypeTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								accounttypeTotal.put(key,new Double(d));


								//����˾�ܼ�ֵ
								dTemp = (Double)cpfTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();			
								d = CurrenBalance + d;								
								cpfTotal.put(key,new Double(d));
								

								//�����ܼ�
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
				//��ʾ���һ��accounttype�ϼ�
					if(accounttypeTotal.size() > 0)
 					{
						Set set = accounttypeTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//�����ӶԱ������ķ���
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" С��":"&nbsp;"%>
											</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>		
<%				
			}//end for
		}//end if.��ʾ���һ���ͻ��ϼƽ���	
			
		//��ʾ�ܺϼ�ֵ
		if(cpfTotal.size() > 0)
		{
			Set set = cpfTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//�����ӶԱ������ķ���
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
					<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"����˾ �ܼ�":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)cpfTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.��ʾ�ܼƽ���

//��ɲ���˾�ڲ��˻���ʾ------------------------------------------------------------------------------------------ 
 
//�����˻�����ʾ---------------------------------------------------------------------------------------------------
					  
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
								{//�����е��˻�
								if(Results[n].getAreaid() != Areaid)
								{	//��ָ���������ĵ��˻�
									if(bankTotal.size() > 0)
									{
										Set set = bankTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//�����ӶԱ������ķ���
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
												
										for(int j = 0; j < keys.length; j++)
										{					
				%> 
															<TR align=center bgColor=#cccccc borderColor=#999999>
															<TD class=ItemBody>&nbsp;</TD>
															<TD class=ItemBody align=right><%=j == 0?bankName+" �ܼ�":"&nbsp;"%></TD>
															<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
															</TR>		
				<%				
										}//end for
									}//end if.��ʾ���һ���ͻ��ϼƽ���	
							
								//��ʾ�ܺϼ�ֵ
								if(areaTotal.size() > 0)
								{
									Set set = areaTotal.keySet();
									Long[] keys = (Long[])set.toArray(new Long[0]);
									//�����ӶԱ������ķ���
									keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
									
									for(int j = 0; j < keys.length; j++)
									{
				%>
									<TR align=center bgColor=#cccccc borderColor=#999999>
															
															<TD class=ItemBody align=right><%=j == 0?AreaName+" �ܼ�":"&nbsp;"%></TD>
															<TD class=ItemBody>&nbsp;</TD>
															<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)areaTotal.get(keys[j])).doubleValue(),2)%></TD>
															</TR>	
					<%
									}//end for.
								}//end if.��ʾ�ܼƽ���
									 
%>									
									<tr class="alter2">
							        <td colspan="3"  align="left" bgcolor="#FDF5DF" class="graytext" height="19">&nbsp;&nbsp;�������ģ�<%=Results[n].getAreaName()%></td>
									</tr>

									<tr class="alter1">
									<td colspan="3"  align="left" class="graytext" height="19" bgcolor="#DFDFDF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������ƣ�<%=Results[n].getBankName()%></td>
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
								{//��bank�ϼƣ�����ʾbank�ϼ�
									if(bankTotal.size() > 0)
									{
										Set set = bankTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//�����ӶԱ������ķ���
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?bankName+" �ܼ�":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   									
<%				
										}//end for
%>
	</TR>
											<tr class="alter1">
											<td colspan="3"  align="left" class="graytext" height="19" bgcolor="#DFDFDF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������ƣ�<%=Results[n].getBankName()%></td>
											</tr>
<%
									}//end if.��ʾbank�ϼƽ���
				
									//���bankTotal�ϼ�
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

								//ͬһ��bank�·ֱ���ͳ��
								Long key = new Long(currencyid);//�ñ�����key
								Double dTemp = (Double)bankTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								bankTotal.put(key,new Double(d));


								//���������ܼ�ֵ
								dTemp = (Double)areaTotal.get(key);
								d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();			
								d = CurrenBalance + d;								
								areaTotal.put(key,new Double(d));
								

								//�����ܼ�
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
				//��ʾ���һ��bank�ϼ�
					if(bankTotal.size() > 0)
					{
						Set set = bankTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//�����ӶԱ������ķ���
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
	
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?bankName+" �ܼ�":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)bankTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>		
<%				
						}//end for
					}//end if.��ʾ���һ���ͻ��ϼƽ���	
			
		//��ʾ�ܺϼ�ֵ
		if(areaTotal.size() > 0)
		{
			Set set = areaTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//�����ӶԱ������ķ���
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
					<TR align=center bgColor=#cccccc borderColor=#999999>
											
											<TD class=ItemBody align=right><%=j == 0?AreaName+" �ܼ�":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)areaTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.��ʾ�ܼƽ���
		 
		if(CURRENTTotal.size() > 0)
		{
			Set set = CURRENTTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//�����ӶԱ������ķ���
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
					<TR align=center bgColor=#cccccc borderColor=#999999>
											
											<TD class=ItemBody align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=j == 0?"�����˻� �ܼ�":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)CURRENTTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
			}//end for.
		}//end if.��ʾ�ܼƽ���
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
//��������˻�����ʾ-------------------------------------------------------------------------------------------
%>
	</TBODY>
				</TABLE>
  			</TD>
		</TR>
 		 </table>
<%
//��ʼί�д���ѷſ���ʾ-------------------------------------------------------------------------------------------
						
//���ί�д��δ�ſ���ʾ-------------------------------------------------------------------------------------------
%>
	 				
<%//��ʼ������ʾ-------------------------------------------------------------------------------------------	


 
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
								{//�Ƕ����˻���
								  if(bHead)//�ǵ�һ�� ��¼ ����ʾ-----> 
									{ bHead = false;
%>
								<br>	
								<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="tableform">	 
								<tr class="tableheader">
									<td colspan="2" height=4> �����˻� </td>
								</tr>
								<TR>
								<TD>
									<TABLE border=0 borderColor=#999999 class=ItemList cellspacing="1" width="100%">
										<TBODY>
										<TR align=center bgColor=#cccccc borderColor=#999999>
										<TD class=ItemTitle width="8%">�˻�����</TD>
										<TD class=ItemTitle width="10%">�˺�/���ݺ�</TD>	
										<TD class=ItemTitle width="10%"></TD>
										<TD class=ItemTitle width="12%"></TD>
										<TD class=ItemTitle width="15%">�����</TD>
										<TD class=ItemTitle width="15%">������</TD>
<%									}
							if(Results[i].getAccounttype() != Accounttype)
							{
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//�����ӶԱ������ķ���
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>				
											<TD class=ItemBody align=right><%=j == 0?"С��":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.��ʾaccount�ϼƽ���


								if(accounttypeTotal.size() > 0)
								{
									Accounttype = Results[i-1].getAccounttype();
									AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
									Set set = accounttypeTotal.keySet();
									Long[] keys = (Long[])set.toArray(new Long[0]);
									//�����ӶԱ������ķ���
									keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
									
									for(int j = 0; j < keys.length; j++)
									{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" �ܼ�":"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>	
	<%
									}//end for.
								}//end if.��ʾ�ܼƽ���
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
								{//��accounttype�ϼƣ�����ʾaccounttype�ϼ�
							 
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//�����ӶԱ������ķ���
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>				
											<TD class=ItemBody align=right><%=j == 0?"С��":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.��ʾaccount�ϼƽ���
%>
	
<%
				
									//���account�ϼ�
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

								//ͬһ��account�·ֱ���ͳ��
								Long key = new Long(currencyid);//�ñ�����key
								Double dTemp = (Double)accountTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								accountTotal.put(key,new Double(d));


								//��ͬ�˻����͵��ܼ�ֵ
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
				//��ʾ���һ��accounttype�ϼ�
					if(accountTotal.size() > 0)
					{
						Set set = accountTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//�����ӶԱ������ķ���
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
	
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>				
											<TD class=ItemBody align=right><%=j == 0?"С��":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
								 	   		</TR>			 
<%				
						}//end for
					}//end if.��ʾ���һ���ͻ��ϼƽ���	
			
		//��ʾ�ܺϼ�ֵ
		if(accounttypeTotal.size() > 0)
		{
			Set set = accounttypeTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//�����ӶԱ������ķ���
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" �ܼ�":"&nbsp;"%></TD>
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

		}//end if.��ʾ�ܼƽ���
		    	 
	}
 
//��ɶ�����ʾ----------------------------------------------------------------------------------------------------
%>
<%//��ʼ������ʾ--------------------------------------------------------------------------------------------------	


 
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
								{//�Ǵ����˻���
								  if(bHead)//�ǵ�һ�����Ͳ���ļ�¼ ����ʾ----->�������ƣ����Ͳ���
									{ bHead = false;
%>
								<br>	
								<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="tableform">	 
								<tr class="tableheader">
									<td colspan="2" height=4> �����˻�</td>
								</tr>
								<TR>
								<TD>
									<TABLE border=0 borderColor=#999999 class=ItemList cellspacing="1" width="100%">
										<TBODY>
										<TR align=center bgColor=#cccccc borderColor=#999999>
										<TD class=ItemTitle width="12%">�˻�����</TD>
										<TD class=ItemTitle width="12%">�˺�/��ͬ��</TD>
										<TD class=ItemTitle width="20%">��λ</TD>	
										<TD class=ItemTitle width="15%"></TD>
										<TD class=ItemTitle width="4%"></TD>
										<TD class=ItemTitle width="14%">������</TD>
										<TD class=ItemTitle width="14%">�������</TD>
<%									}
							if(Results[i].getAccounttype() != Accounttype)
							{
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//�����ӶԱ������ķ���
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>	
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"С��":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.��ʾaccount�ϼƽ���


								if(accounttypeTotal.size() > 0)
								{
									Accounttype = Results[i-1].getAccounttype();
									AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(Accounttype);
									Set set = accounttypeTotal.keySet();
									Long[] keys = (Long[])set.toArray(new Long[0]);
									//�����ӶԱ������ķ���
									keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
									
									for(int j = 0; j < keys.length; j++)
									{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" �ܼ�" :"&nbsp;"%></TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accounttypeTotal.get(keys[j])).doubleValue(),2)%></TD>
											
								 	   		</TR>	
	<%
									}//end for.
								}//end if.��ʾ�ܼƽ���
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
								{//��accounttype�ϼƣ�����ʾaccounttype�ϼ�
							 
									if(accountTotal.size() > 0)
									{
										Set set = accountTotal.keySet();
										Long[] keys = (Long[])set.toArray(new Long[0]);
										//�����ӶԱ������ķ���
										keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
										for(int j = 0; j < keys.length; j++)
										{					
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"С��":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
										 
								 	   		</TR>			 
										
<%				
										}//end for
									}//end if.��ʾaccount�ϼƽ���
%>	
<%
				
									//���account�ϼ�
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
						 
								//ͬһ��account�·ֱ���ͳ��
								Long key = new Long(currencyid);//�ñ�����key
								Double dTemp = (Double)accountTotal.get(key);
								double d = 0.0;
								if(dTemp != null) d = dTemp.doubleValue();
								d = CurrenBalance + d;
								accountTotal.put(key,new Double(d));


								//��ͬ�˻����͵��ܼ�ֵ
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
				//��ʾ���һ��accounttype�ϼ�
					if(accountTotal.size() > 0)
					{
						Set set = accountTotal.keySet();
						Long[] keys = (Long[])set.toArray(new Long[0]);
						//�����ӶԱ������ķ���
						keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
								
						for(int j = 0; j < keys.length; j++)
						{					
%>
	
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?"С��":"&nbsp;"%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
											<TD class=ItemBody align=right><%=NameRef.getCurrencySymbolbyId(keys[j].longValue())%><%=DataFormat.formatDisabledAmount(((Double)accountTotal.get(keys[j])).doubleValue(),2)%></TD>
										 
								 	   		</TR>			 
<%				
						}//end for
					}//end if.��ʾ���һ���ͻ��ϼƽ���	
			
		//��ʾ�ܺϼ�ֵ
		if(accounttypeTotal.size() > 0)
		{
			Set set = accounttypeTotal.keySet();
			Long[] keys = (Long[])set.toArray(new Long[0]);
			//�����ӶԱ������ķ���
			keys = (Long[])ObjectUtil.sortObjectByField(keys,"longValue",false);
			
			for(int j = 0; j < keys.length; j++)
			{
%>
											<TR align=center bgColor=#cccccc borderColor=#999999>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody>&nbsp;</TD>
											<TD class=ItemBody align=right><%=j == 0?AccounttypeName+" �ܼ�":"&nbsp;"%></TD>
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
		}//end if.��ʾ�ܼƽ���

		    	 
 
	}
 
//��ɴ�����ʾ-------------------------------------------------------------------------------------------
%>
</form>
				 
	 
			<table width="98%">
			<tr>
			<td width="450"><div align="left"><span class="graytext">��ѯ���ڣ�<%=DataFormat.getDateString()%></span> 
			</div></td>
			</tr>
			</table>
 

			 
		<hr>
		  	<table width="98%">
					<TR align="right">
						<TD align="right" colspan=>
							 <input type="button" class="button" name="button1" value="�� ��"  onclick="javascript:doBack();" onfocus="javascript:nextField='submitfunction'">
								&nbsp;	&nbsp;	&nbsp;
					 
							 <input type="button" class="button" name="button2" value="�� ��"  onclick="javascrip:doExport();" onfocus="javascript:nextField='submitfunction'">
								&nbsp;	&nbsp;	&nbsp;
					 
							 <input type="button" class="button" name="button3" value="�� ӡ"  onclick="javascript:doPrint();" onfocus="javascript:nextField='submitfunction'">					
						</TD>
						
					</TR>
				</TABLE>



<!--ҳ�������-->
<!--ҳ��ű���ʼ-->
<script language="JavaScript">
	////setSubmitFunction("doPrint()");
	setFormName("frmV002");
</script>

<script language="javascript">
function doPrint()
{
   if (confirm("�Ƿ��ӡ��"))
		{
			window.open('<%=strRootPath%>/accountinfo/a123-p.jsp');
         }
			
} 
function doExport()
{
   if (confirm("�Ƿ񵼳���"))
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
<!--ҳ��ű�Ԫ�ؽ���-->


<%	
	/**
	* ��ʾ�ļ�β
	*/
	OBHtml.showOBHomeEnd(out);
}
//�쳣����
catch(Exception exp)
{
	Log.print(exp.getMessage());
	OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
}
%>

<%@ include file="/common/SignValidate.inc" %>