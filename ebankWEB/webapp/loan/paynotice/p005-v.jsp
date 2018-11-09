<%
/*
控制变量
d001-v
Control
*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
				com.iss.itreasury.settlement.util.*,			
				com.iss.itreasury.ebank.obpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obpaynotice.bizlogic.*"	
%>
<%@ include file="/common/common.jsp" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%


	String strOfficeName = sessionMng.m_strOfficeName;
	long lClientID = sessionMng.m_lClientID;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	String strTitle = "提款申请";
//////////////////////////////////////////////////////////////////////////////////////////////

	try
	{
		//判断是否登录//CODE_COMMONMESSAGE_LOGIN=1
	        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//定义变量
		
		//显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
                
                //定义变量
                Timestamp tsSystem = Env.getSystemDate(); 
				Timestamp tsInput = tsSystem; 
                long lID = -1;  
                long lCurrencyID = sessionMng.m_lCurrencyID; 
                long lOfficeID = sessionMng.m_lOfficeID; 
                long lInputUserID = sessionMng.m_lUserID; 
                String strTmp = ""; 
                long lContractID = -1; //合同号
                long lPayID = -1;      //放款通知单ID
                String strType = "";   //操作类型
                String strAction = "";
                String txtAction = "";
                //合同ID
                strTmp = (String)request.getAttribute("lContractID");
                if( strTmp != null && strTmp.length() > 0 )
                {
                     lContractID = Long.parseLong(strTmp.trim());
                }
                System.out.println("lContractID = "+lContractID);
                
				strTmp = (String)request.getAttribute("txtAction");
				if( strTmp != null && strTmp.length() > 0 )
				{
					txtAction = strTmp.trim();
				}
				
				strTmp = (String)request.getAttribute("strAction");
				if( strTmp != null && strTmp.length() > 0 )
				{
					strAction = strTmp.trim();
				}
                //放款通知单ID
                strTmp = (String)request.getAttribute("lPayID");
                if( strTmp != null && strTmp.length() > 0 )
                {
                     lPayID = Long.parseLong(strTmp.trim());
                }
                else
                {
                    lPayID = -1;
                }
                System.out.println("lPayID = "+lPayID);
                
//////////////////////////////////////////////////////////////////////////////////////
				PayNoticeInfo info = null;
				info = (PayNoticeInfo)request.getAttribute("info");
//////////////////////////////////////////////////////////////////////////////////////

%>
<form name="frm" method="post" action=""  >
<TABLE border=0 class=top height=60 width="54%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=35><B><%=strTitle%></B></TD></TR>
  <TR>
    <TD height=572 vAlign=top>
      <TABLE align=center border=0 height=60 width=730>
        <TBODY>
                
        <TR>
          <TD height=23 width=1>&nbsp;</TD>
          <TD colSpan=2 height=23><SPAN lang=ZH-CN 
            style="FONT-SIZE: 10pt"><FONT size=2>合同编号：<%=info.getContractCode()%></FONT></SPAN></TD>
          <TD width="673" height=23 colSpan=1></TD>
                
          </TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD colSpan=10 height=2>
            <HR>
            <FONT size=2><U>贷款合同基本资料</U></FONT></TD>
          <TD height=2 width=4>&nbsp;</TD></TR>
        <TR>
          <TD height=146 width=1>&nbsp;</TD>
          <TD colSpan=10 height=146>
              <TABLE cellPadding=0 cellSpacing=0 width="100%">
                <TBODY> 
                <TR> 
                  <TD height=31 ><FONT size=2>借款单位：</FONT></TD>
                  <TD height=31 colspan=5><FONT size=2> 
                    <INPUT class=box  size=80 disabled name=strBorrowClientName value=<%=info.getLoanClientName()%>>
                    </FONT></TD>
                  <TD height=31 >&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=32 ><FONT size=2>贷款金额： ￥</FONT></TD>
                  <TD height=32 ><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dLoanAmount size=18 value=<%=DataFormat.formatListAmount(info.getExamineAmount())%>>
                  <input type="hidden" name="dMoney" value="<%=info.getUnPayAmount()%>">
                    </FONT></TD>
                  <TD height=32 ><font size="2">已发放金额： ￥</font></TD>
                  <TD height=32 ><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dAmountDone size=18 value=<%=(info.getOpenAmount()>0?DataFormat.formatListAmount(info.getOpenAmount()):"0.00")%>>
                    </FONT></TD>
                  <TD height=32 ><font size="2">未发放金额： ￥</font></TD>
                  <TD height=32 ><font size=2> 
                    <input class=tar 
                  disabled name=dAmountLeft size=18 value=<%=DataFormat.formatListAmount(info.getUnPayAmount())%>>
                    </font></TD>
                  <TD height=32 >&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=32 ><FONT size=2>贷款期限：</FONT></TD>
                  <TD height=32><FONT size=2> 
                    <INPUT class=box 
                  disabled name=lInterValNum size=3 value=<%=info.getIntervalNum()%>>
                    月</FONT></TD>
                  <TD height=32 ><FONT size=2>贷款利率：</FONT></TD>
                  <TD height=32><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dInterestRate size=10 value=<%=DataFormat.formatRate(info.getInterestRate())%>>
                    %</FONT></TD>
                  <TD height=32 ><font size="2">已还款金额： ￥</font></TD>
                  <TD height=32 ><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dAmountDone size=18 value=<%=(info.getRepayAmount()>0?DataFormat.formatListAmount(info.getRepayAmount()):"0.00")%>>
                    </FONT></TD>
                  <TD height=32 >&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=29 ><FONT size=2>借款用途:</FONT></TD>
                  <TD height=29 colspan="5"><FONT size=2> 
                    <INPUT class=box 
                  disabled name=strLoanPurpose size=45 value=<%=DataFormat.formatString(info.getLoanPurpose())%>>
                    </FONT></TD>
                  <TD height=29 >&nbsp;</TD>
                </TR>
                </TBODY> 
              </TABLE>
            </TD>
          <TD height=146 width=4>&nbsp;</TD></TR>
        <TR>
          <TD height=24 width=1>&nbsp;</TD>
          <TD colSpan=10 height=24>
            <HR>
          </TD>
          <TD align=right height=24 width=4>&nbsp;</TD></TR>
        <TR>
          <TD height=30 width=1>&nbsp;</TD>
          <TD colSpan=10 height=30>
            <DIV align=center><B><FONT size=4>放款通知单</FONT></B></DIV></TD>
          <TD align=right height=30 width=4>&nbsp;</TD></TR>
        <TR>
          <TD height=13 width=1>&nbsp;</TD>
          <TD colSpan=10 height=13>
            <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 
            height=341 width="100%">
              
              <TR>
                <TD height=411>

                    <TABLE border=0 height=136 width="98%">

                      <TR> 
                        <TD colSpan=2 height=36 > <FONT size=2>放款日期：</FONT></TD>
                        <TD height=32 colSpan=2 > 
                <DIV align=left>
				<FONT size=2>
                                <INPUT class=box name="dtoutdate" onfocus="nextfield ='sloanaccount';" value="<%=DataFormat.formatDate(info.getOutDate())%>" disabled > 
						</font></div></TD>
                        <TD height=36>&nbsp;</TD>
                        <TD width="223" height=36 >&nbsp; 
                          
                        </TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>借款单位名称：</FONT></TD>
                        <TD height=36 colspan=4><FONT size=2> 
                          <INPUT class=box size=80 disabled
                        name="" value =<%=DataFormat.formatString(info.getLoanClientName())%>>
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD  height=36 colSpan=2><FONT size=2>借款单位账号：</FONT></TD>
                        <TD height=36 colspan=4 ><FONT size=2> 
                          <INPUT class=box onfocus="nextfield ='';" disabled
                        name="sloanaccount" size=25 value=<%=DataFormat.formatString(info.getLoanAccount())%>>
                          </FONT> </TD>
                 
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>借款单位邮编：</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled name=LoanZipCode size=25 value="<%=DataFormat.formatString(info.getLoanZipCode())%>">
                          </FONT></TD>
                        <TD height=36><FONT size=2>借款单位电话：</FONT></TD>
                        <TD height=36 ><FONT size=2  > 
                          <INPUT class=box  disabled name=LoanPhone size=25 value="<%=DataFormat.formatString(info.getLoanPhone())%>">
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>借款单位地址：</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled
                        name=strLoanAddress size=25 value="<%=DataFormat.formatString(info.getLoanAddress())%>">
                          </FONT></TD>
                        <TD height=36><font size="2">贷款种类：</font></TD>
                        <TD height=36 ><FONT size=2> 
                          <INPUT class=box  disabled
                        name=strLoanTypeName size=25 value="<%=DataFormat.formatString(LOANConstant.LoanType.getName(info.getLoanTypeID()))%>">
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD height=36 colspan="2"><font size="2"> 放款方式：</font></TD>
                        <TD height=36 colSpan=2>&nbsp;</TD>
                        <TD height=36>&nbsp;</TD>
                        <TD height=36 >&nbsp;</TD>
                      </TR>
                      <TR> 
                        <TD height=36 colspan="6"> 
                        <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 height="100%"  width="100%">
                        <tr><td>
                          <table align=left border=0 bordercolor=#999999 height="100%"  width="100%">
                            <tbody> 
                            <tr bordercolor=#D7DFE5> 
							    
								
                              <td colspan=4 height=20 valign=middle> 
                                <%if( info.getGrantTypeID() == LOANConstant.TransType.Bank )
                                {%>
                                <input type="checkbox" name="bankpay" value="checkbox" checked disabled>
                                <%}else{%>
                                <input type="checkbox" name="bankpay" value="checkbox" disabled>
                                <%}%><font size="2">银行付款</font> </td>
                            </tr>
							<%
							
							String paytype = request.getParameter("paytype");
							//if(paytype.equals("bankpay"))
							//	{
							%>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                                <td height=20 width="20%"><font size="2">开户银行：</font></td>
                                <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="BankCode" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(info.getAccountBankName())%>">
                                </p>
                              </td>

                              
                              <td height=20 width="20%"><font size="2">银行账号： 
                                </font></td>
                              <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="BankCode" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(info.getAccountBankCode())%>">
                                </p>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font size="2">收款方资料：</font></td>
                              <td height=20 width="30%">&nbsp;</td>
                              <td height=20 width="20%">&nbsp;</td>
                              <td height=20 width="30%">&nbsp;</td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                            <td height=20 width="20%"><font size="2">收款方账号：</font></td>
                                <td height=20 width="30%"> 
                                <p> 
                                  <INPUT class=box name="sremitinprovince" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(info.getReceiveAccount())%>">
                                </p>
                              </td>
                              
                              <td height=20 width="20%"><font size="2">收款方名称： 
                                </font></td>
                              <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="sreceiveclientname" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(info.getReceiveClientName())%>">
                                </p>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font size="2">汇入地（省）：</font></td>
                              <td height=20 width="30%"> <font size="2"> 
                                <INPUT class=box name="sremitinprovince" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(info.getRemitinProvince())%>">
                                </font></td>
                              <td height=20 width="20%"><font size="2">汇入地（市）：</font></td>
                              <td height=20 width="30%"> <font size="2"> 
                              <INPUT class=box name="sremitinprovince" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(info.getRemitinCity())%>">
                                </font></td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font size="2">汇入行名称：</font></td>
                              <td height=20 width="30%"><font size="2"> 
                              <INPUT class=box name="sremitinprovince" size=20 maxlength="100" disabled value="<%=DataFormat.formatString(info.getRemitBank())%>">
                                </font></td>
                              <td height=20 width="20%">&nbsp;</td>
                              <td height=20 width="30%">&nbsp;</td>
                            </tr>
                            </tbody> 
                          </table>
                          </td></tr></table>
                        </TD>
                      </TR>
					  <%
					  	//}else if(paytype.equals("account"))
						//	{
					  %>
                      <TR> 
                        <TD height=36 colspan="6"> 
                        <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 height="100%"  width="100%">
                        <tr><td>
                          <table align=left border=0 bordercolor=#999999 height="100%" 
            width="100%">
                            <tbody> 
                            <tr bordercolor=#D7DFE5> 
                              <td colspan=4 height=20 valign=middle>
							    <%if( info.getGrantTypeID() == LOANConstant.TransType.CurrentAccount )
                                {%>
                                <input type="checkbox" name="" value="checkbox" checked disabled>
                                <%
                                }else{%>
                                <input type="checkbox" name="" value="checkbox" disabled>
                                <%}%> 
                                <font size="2">活期账户</font></td>
                            </tr>

                            <tr bordercolor=#D7DFE5 valign=middle> 
							<TD width="14%">账号：</TD>
                              <td width="84%" height=20><input class=box name="sremitbank" value="<%=DataFormat.formatString(info.getGrantCurrentAccount())%>" size=20 maxlength="100"  onfocus="nextfield ='mamount';" disabled>
</td>
                              <td width="2%" height=20>&nbsp; </td>
                            </tr>
                            
                            </tbody> 
                          </table>
                          </td></tr></table>
                        </TD>
                      </TR>
					  <%
					  //}
					  %>
                      <TR> 
                        <TD height=36 width=73> <FONT size=2>批准金额：</FONT></TD>
                        <TD width="19" height=36> 
                          <DIV align=right><FONT size=2>￥</FONT></DIV>
                        </TD>
                        <TD height=36 colSpan=2><FONT size=2> 
               				 <input class=bar name="mamount" value="<%=DataFormat.formatDisabledAmount(info.getAmount())%>" size=20 maxlength="100"  onfocus="nextfield ='mamount';" disabled>
                          </FONT></TD>
                        <TD height=36><FONT size=2>&nbsp;</FONT> 
                          <DIV align=right><FONT size=2>(大写)</FONT></DIV>
                        </TD>
                        <TD height=36 ><FONT size=2>
						<%String Amount = DataFormat.formatAmount(info.getAmount());%>
                          <INPUT class=box disabled
                        name=mAmountFomat size=26 value="<%=ChineseCurrency.showChinese(Amount)%>">
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>委托方名称：</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled
                        name=strClientName size=25 value="<%=DataFormat.formatString(info.getConsignClientName())%>">

						  </FONT></TD>
                        <TD height=36><FONT size=2>委托方账号：</FONT></TD>
                        <TD height=36 ><FONT size=2> 
                          <INPUT class=box  disabled
                        name="sconsignaccount" size=25 value="<%=DataFormat.formatString(info.getConsignAccount())%>" onfocus="nextfield ='mRate';">
                          </FONT></TD>
                      </TR>
                      <TR> 
                           <td colspan="2"> <FONT size=2>基准利率</FONT></td>
							<TD width="84" height=33 ><FONT size=2> 
                          <input type="text" name="mRate" size="8" class="box" value="<%=DataFormat.formatRate(info.getWTInterestRate())%> " disabled>
                          %</FONT></TD>

                        <TD   width="159" ><select class='box' name="select1" disabled>
                              <option value=1 <%if(info.getAdjustRate()>=0) out.println("selected");%>>+</option>
                              <option value=2 <%if(info.getAdjustRate()<0) out.println("selected");%>>-</option>
                            </select> <font size="2">浮动</font> 
                            <input type="text" name="textfield3222" size="8" class="box" value="<%=info.getAdjustRate()>=0?DataFormat.formatRate(info.getAdjustRate()):DataFormat.formatRate(-info.getAdjustRate())%>" disabled>
              			% </TD>
                          <TD height=33 ><font size="2">执行利率</font>：</TD>
                        <TD height=33 ><FONT size=2> 
                          <input type="text" name="ExecuteValue" size="8" class="box" value="<%=DataFormat.formatRate(info.getInterestRate())%>"   disabled>
                          %</FONT></TD>
                      </TR>
                      <TR> 
                                          <TD colSpan=2 height=33><FONT size=2>手续费率：</FONT></TD>
                        <TD colSpan=2 height=33><FONT size=2> 
                          <INPUT class=tar name="mcommissionrate" size=16 onfocus="nextfield ='msuretyfeerate';setExecuteValue(<%=info.getAdjustRate()%>)"  value="<%=DataFormat.formatRate(info.getCommissionRate())%>" disabled>
                          % </FONT></TD>
                        <TD height=33 ><FONT size=2>担保费率：</FONT></TD>
                        <TD height=33 ><FONT size=2> 
                          <INPUT class=tar  onfocus="nextfield ='dtstart';" name="msuretyfeerate" size=16 value="<%=DataFormat.formatRate(info.getSuretyFeeRate())%>" disabled>
                          %</FONT></TD>
                      </TR>
                      <TR>
                        <TD height=33 width=73><FONT size=2>贷款日期：</FONT></TD>
                        <TD height=33 >
                          <DIV align=right><FONT size=2>由</FONT></DIV>
                        </TD>
                        <TD height=33 colspan=2>
                                        <DIV align=left><FONT size=2>
                                                        <INPUT class=box name="dtstart" onfocus="nextfield ='dtend';" value="<%=DataFormat.getDateString(info.getStart())%>" disabled> 
														</font></DIV></TD>
                        <TD width=90> <FONT size=2>至</FONT></TD>
                        <TD> 
                                        <DIV align=left><FONT size=2>
                                                        <INPUT class=box name="dtend" onfocus="nextfield ='scompanyleader';" value="<%=DataFormat.getDateString(info.getEnd())%>" disabled></FONT></DIV></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=24><FONT size=2>贷款期限：</FONT></TD>
                        <TD colSpan=4 height=24><FONT size=2> 
                          <INPUT class=box  disabled
                                name=textfield24222 size=8  value=<%=info.getIntervalNum()%>>
                          月</FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=24><FONT size=2>原因或用途：</FONT></TD>
                        <TD colSpan=4 height=24><FONT size=2> 
                          <TEXTAREA  disabled cols=70 name=LoanPurpose><%=DataFormat.formatString(info.getLoanPurpose())%></TEXTAREA>
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>贷款合同编号：</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled
                                name=strContractCode size=25 value=<%=DataFormat.formatString(info.getContractCode())%>>
                          </FONT></TD>
                        <TD height=36>&nbsp;</TD>
                        <TD height=36 >&nbsp;</TD>
                      </TR>
                      
                    </TABLE>

                  </TD></TR></TABLE>
            <HR>
          </TD>
          <TD align=right height=13 width=4><FONT size=2>&nbsp;</FONT></TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD width="101" height=2 align=left><FONT 
          size=2><U>执行计划详细</U></FONT></TD>
          <td align = right colSpan =9   height = 29  nowrap>
		<font size=2>录入人：<%=info.getInputUserName()%>&nbsp;&nbsp;&nbsp;&nbsp;录入时间：<%=DataFormat.getDateString(tsInput)%>&nbsp;&nbsp;&nbsp;&nbsp;状态：<%=OBConstant.LoanInstrStatus.getName(info.getStatusID())%></font>
		</td>
         </TR>
        <TR >
          <TD height=29 width=1>&nbsp;</TD>
          <TD align=left height=29 >
		  <FONT size=2>
          <INPUT class=button name=btnModifyExPlan onclick="Javascript: frmModifyExPlan('<%=lContractID%>');" type="button" value=" 执行计划 ">
            </FONT>
			</TD>
          <TD align=right colSpan=9 >
	
            <!--DIV align=left><FONT size=2></FONT></DIV><FONT size=2>&nbsp;</FONT-->
            <DIV align=right><FONT size=2>
			<%	
		  	if (info.getInputUserID()==sessionMng.m_lUserID
			&&
			(info.getStatusID()==OBConstant.LoanInstrStatus.SAVE
			||info.getStatusID()==OBConstant.LoanInstrStatus.SUBMIT)
			)
			{
		  %>
						<INPUT class=button name=Submit4223 onclick="printIt()" type=button value="打印">
                        <INPUT class=button name=cancel onclick="submitCancel(frm);" type=button value="取消申请单"> 
                        <INPUT class=button name=submitAct onclick="clickSubmit(frm);" type=button value="提交申请单" > 
						<INPUT class=button name=back onclick="clickAdd();" type=button value=" 新增申请单"> 
			<%}%>
						<INPUT class=button name=back onclick="clickBack();" type=button value=" 返 回 "> 
            </FONT></DIV>
			
			</TD>
       </TR>
  </TBODY></TABLE>
        <input type="hidden" name="strType">       
        <input type="hidden" name="strAction" value="view">
        <input type="hidden" name="lContractID" value=<%=lContractID%>> 
        <input type="hidden" name="ngranttypeid" value="">      
        <input type="hidden" name="lPayID" value="<%=lPayID%>">      
		<input type="hidden" name="code" value="<%=info.getCode()%>">
		<input type="hidden" name="txtAction" value="<%=txtAction%>">
		</TD>
       </TR>
  </TBODY></TABLE>
</form>
   
<script language="javascript">
 
 ////setSubmitFunction("clickSubmit(frm)");
 setFormName("frm");        
</script>    


<script language="JavaScript">
        function clickAdd()
        {
			frm.action="p001-v.jsp";
                frm.submit();   
        }
		
		function clickBack()
        {	
		var act = "<%=txtAction%>";	
		if(act=="modify")
                {
				frm.action="../query/q003-c.jsp";	
				}else
				frm.action="../paynotice/p001-v.jsp";
                
		frm.submit();
        }
        
        function clickSubmit(frm)
        {
          	if(confirm("确认提交？"))
			{	      

                frm.strType.value="submit";
				frm.strAction.value="submitAct";
                frm.action="p004-c.jsp";
                frm.submit();   
			}
        }
        
        function clickSubmitWT(frm)
        {
            if(confirm("确认提交？"))
			{	      

                frm.strType.value="submit";
				frm.strAction.value="submitAct";
                frm.action="p004-c.jsp";
                frm.submit();   
			}
        }
        function submitCancel(frm)
        {
          	if(confirm("确认取消？"))
			{	      
                frm.strType.value="submit";
				frm.strAction.value="cancel";
                frm.action="p004-c.jsp";
                frm.submit();   
			}
        }
        //检查页面输入是否合规范
        function checkPage(frm) 
        {
                if(frm.bankpay.checked == false && frm.currentaccount.checked == false)
                {
                    alert('请选择一种放款方式');
                    return false;
                }
                
                if(frm.bankpay.checked == true && frm.currentaccount.checked == true)
                {
                    alert('只能选择一种放款方式');
                    return false;
                }
                
                if(frm.bankpay.checked == true)
                {
                    frm.ngranttypeid.value = 1;

                    if (!checkMagnifier("frm","naccountbankid","txtBA","开户银行"))
                    {
                        return false;
                    }//*/
                        
                    if (!checkString(frm.txtAccount,"收款方账号"))
                    {
                        return false;
                    }
					    
                    if (!checkString(frm.sreceiveclientname,"收款方名称"))
                    {
                        return false;
                    }
                    
                    if (!checkString(frm.sremitinprovince,"汇入地（省）"))
                    {
                        return false;
                    } 
                     
                    if (!checkString(frm.sremitincity,"汇入地（市）"))
                    {
                        return false;
                    } 
                    
                    if (!checkString(frm.sremitbank,"汇入行名称"))
                    {
                        return false;
                    }  

                }
                
                if(frm.currentaccount.checked == true)
                {
                    frm.ngranttypeid.value = 2;

                        if (!checkMagnifier("frm","ngrantcurrentaccountid","txtAccountCodeCtrl1","账号"))
                        {
                            return false;
                        }     

                }
				
				if(eval("frm.mamount.value.length>frm.dAmountLeft.value.length"))
				{
					alert("批准金额不能大于未发放金额！");
					eval("frm.mamount.focus()");
					return(false);
				}
				else if(eval("frm.mamount.value.length==frm.dAmountLeft.value.length")&&eval("frm.mamount.value>frm.dAmountLeft.value"))
				{
					alert("批准金额不能大于未发放金额！");
					eval("frm.mamount.focus()");
					return(false);
				}
                
                //基准利率
                if (!checkMagnifier("frm","nbankinterestid","mRate","基准利率"))
                {
                    return false;
                }
                
                //放款日期
                if(!checkDate(frm.dtoutdate,1,"放款日期"))                      
                {
                    return false;
                }
                
                //批准金额
                if(!checkAmount(frm.mamount,1,"批准金额"))
                {
                    return false;
                }
                
                //手续费率 
                if(!checkRate(frm.mcommissionrate,0,"手续费率"))
                {
                    return false;
                }
                
                //担保费率
                if(!checkRate(frm.msuretyfeerate,0,"担保费率"))
                {
                    return false;
                }
                
                //贷款日期dtstart
                if(!checkDate(frm.dtstart,0,"贷款日期"))                        
                {
                    return false;
                }
                
                //贷款日期dtend
                if(!checkDate(frm.dtend,0,"贷款日期"))                  
                {
                    return false;
                }
                
                return true; 
        }
        
        //检查页面输入是否合规范
        function checkPageWT(frm) 
        {
                if(frm.bankpay.checked == false && frm.currentaccount.checked == false)
                {
                    alert('请选择一种放款方式');
                    return false;
                }
                
                if(frm.bankpay.checked == true && frm.currentaccount.checked == true)
                {
                    alert('只能选择一种放款方式');
                    return false;
                }

                if(frm.bankpay.checked == true)
                {
                    frm.ngranttypeid.value = 1;

                    if (!checkMagnifier("frm","naccountbankid","txtBA","开户银行"))
                    {
                        return false;
                    }//*/
                    
					if (!checkString(frm.txtAccount,"收款方账号"))
                    {
                        return false;
                    }
					    
                    if (!checkString(frm.sreceiveclientname,"收款方名称"))
                    {
                        return false;
                    }
                    
                    if (!checkString(frm.sremitinprovince,"汇入地（省）"))
                    {
                        return false;
                    } 
                    
                    if (!checkString(frm.sremitincity,"汇入地（市）"))
                    {
                        return false;
                    } 
                    
                    if (!checkString(frm.sremitbank,"汇入行名称"))
                    {
                        return false;
                    }  

                }
                
                if(frm.currentaccount.checked == true)
                {
                    frm.ngranttypeid.value = 2;

                        if (!checkMagnifier("frm","ngrantcurrentaccountid","txtAccountCodeCtrl1","账号"))
                        {
                            return false;
                        }     

                }              

                if (!checkMagnifier("frm","mRate","mRate","基准利率"))
                {
                    return false;
                }

                //放款日期
                if(!checkDate(frm.dtoutdate,1,"放款日期"))                      
                {
                    return false;
                }
                
                //批准金额
                if(!checkAmount(frm.mamount,1,"批准金额"))
                {
                    return false;
                }
                
                //手续费率 
                if(!checkRate(frm.mcommissionrate,0,"手续费率"))
                {
                    return false;
                }
                
                //担保费率
                if(!checkRate(frm.msuretyfeerate,0,"担保费率"))
                {
                    return false;
                }
                
                //贷款日期dtstart
                if(!checkDate(frm.dtstart,0,"贷款日期"))                        
                {
                    return false;
                }
                
                //贷款日期dtend
                if(!checkDate(frm.dtend,0,"贷款日期"))                  
                {
                    return false;
                }
                
                return true; 
        }
        
        function frmModifyExPlan(planID) //执行计划  action on the Form : frmPage
	{
		var url = "../query/q094-v.jsp?control=view&isYU=2&nTmpID=<%=info.getPlanVersionID()%>&isSM=<%=Constant.YesOrNo.NO%>";
		window.open(url, "执行计划", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
	}
 function setExecuteValue(floatRateValue)
{
	eval("document.frm.ExecuteValue.value = document.frm.mRate.value*(1 + floatRateValue*1/100)");
	eval("document.frm.ExecuteValue.value = Math.round(document.frm.ExecuteValue.value*1000000)/1000000");
}
</script>
<script language="javascript">
function printIt()
{
	window.open('p008-v.jsp?lContractID=<%=lContractID%>&lPayID=<%=lPayID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}
</script>

<%
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>