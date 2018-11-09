<%
/**
 * 页面名称 ：q005-v.jsp
 * 页面功能 : 贷款保证资料
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.bizlogic.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.system.approval.bizlogic.*,
	com.iss.itreasury.system.user.bizlogic.*,
	com.iss.itreasury.system.user.dataentity.*,
	com.iss.itreasury.system.approval.dataentity.*,
	com.iss.itreasury.loan.loaninterestsetting.dataentity.*,
	com.iss.itreasury.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    try{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		LoanApplyInfo appInfo=(LoanApplyInfo)request.getAttribute("LoanApplyInfo");		//贷款申请信息
		Vector assVector=(Vector)appInfo.getAssureInfo();
		Vector planInfo=(Vector)request.getAttribute("Collection");						//贷款计划信息
		ClientInfo cInfo=(ClientInfo)request.getAttribute("ClientInfo");
		ClientInfo wtClientInfo=(ClientInfo)request.getAttribute("wtClientInfo");
	
	    long loanType=appInfo.getTypeID();
    	String loanTypeName=LOANConstant.LoanType.getName(loanType);
    	String action="";
		boolean iswt=false;
    	boolean isyt=false;
    	String backurl="";
    	boolean isLevel1=false;
     	boolean isQuery=true;
    
	    /* 客户的信息*/
    	long clientID=cInfo.getClientID();						//客户ID
 		long[] QTClientID={-1,-1,-1};
    	String[] QTClientName=new String[3];
    	float[] QTScale =new float[3];
    	String[] QTCardNo=new String[3];
    	String[] QTPwd=new String[3];
   
	    /*借款详情*/
 	   long loanID=appInfo.getID();
       long wtClientID=appInfo.getConsignClientID();
    	String applyCode=appInfo.getApplyCode();
    	double interestRate=appInfo.getInterestRate();						//贷款执行利率
    	long   bankInterestID=appInfo.getBankInterestID();
    	double bankInterestRate=0;
    	double chargeRate=appInfo.getChargeRate();
    	double adjustRate=appInfo.getAdjustRate();
    	// add by fxzhang 2007-1-8
    	double staidAdjustRate=appInfo.getStaidAdjustRate();
    	
    	long chargeRateTypeID=appInfo.getChargeRateTypeID();
    	Timestamp applyDate=appInfo.getInputDate();							//贷款申请时间
    	long intervalNum=appInfo.getIntervalNum();							//期限
    	Timestamp applyStartDate=appInfo.getStartDate();					//贷款申请开始时间
    	Timestamp applyEndDate=appInfo.getEndDate();
    	double loanAmount=appInfo.getLoanAmount();
    	double examineAmount=appInfo.getExamineAmount();
    	String loanReason=appInfo.getLoanReason();
    	String loanPurpose=appInfo.getLoanPurpose();
    	double selfAmount=appInfo.getSelfAmount();
    	double selfScale=appInfo.getSelfScale();
    	String other=appInfo.getOther();
    	String clientInfo=appInfo.getClientInfo();
    	long planVersion=appInfo.getPlanVersion();
    	double checkRate=0;
    	if ( bankInterestID>0 )
    	{
    		OBLoanQuery interestSet=new OBLoanQuery();
    		InterestRateInfo rateInfo=interestSet.findInterestRateByID(bankInterestID);
    		bankInterestRate=rateInfo.getInterestRate();
	    	System.out.println(""+bankInterestRate+"---"+adjustRate);
	    	//modified by fxzhang 2007-1-8
    		//checkRate=bankInterestRate * (1 + adjustRate/100);								//执行利率
    		checkRate=bankInterestRate * (1 + adjustRate/100)+staidAdjustRate;	//执行利率
			
    	}
	    
	    long isCredit=appInfo.getIsCredit();
	    long isAssure=appInfo.getIsAssure();
	    long isPledge=appInfo.getIsPledge();
	    long isImpawn=appInfo.getIsImpawn();
	    long isCircle=appInfo.getIsCircle();
	    long isTechnical=appInfo.getIsTechnical();
	    long isSaleBuy=appInfo.getIsSaleBuy();
    
    	if ( loanReason==null ) loanReason="";
    	if ( loanPurpose==null ) loanPurpose="";
    	if ( other==null ) other="";
	    
    	long pageNo=1;
    	long orderParam=99;
    	long lDesc=Constant.PageControl.CODE_ASCORDESC_DESC;
    	long userID=-1;
    	String userName="";
    	String tmp="";
         
	    double assSumAmount=0;
    	double creditAmount=0;     
    
		userID=sessionMng.m_lUserID;
		if ( userID==appInfo.getInputUserID() )
			isLevel1=true;
		userName=sessionMng.m_strUserName;
		tmp=(String)request.getAttribute("nOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
			orderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lDesc=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("nPageNo");
		if ( (tmp!=null)&&(tmp.length()>0) )
			pageNo=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("txtAction");
		if ( (tmp!=null)&&(tmp.length()>0) )
			action=tmp;
		
		tmp=(String)request.getAttribute("backurl");
		if ( (tmp!=null)&&(tmp.length()>0) )
			backurl=tmp;
						
		if ( cInfo!=null )
        {
    		QTClientName=cInfo.getQTClientName();
			QTScale=cInfo.getFQTScale();
			QTCardNo=cInfo.getQTCardNo();
			QTPwd=cInfo.getQTPwd();
		}	
	if ( loanType==LOANConstant.LoanType.WT)
    {
    	iswt=true;
	}
	else if( loanType==LOANConstant.LoanType.YT )
	{
		isyt=true;
	}
	 
	 /*获取审核历史*/
	 ApprovalBiz approvalBiz=new ApprovalBiz();
	 long lModuleID = Constant.ModuleType.LOAN;
	 long lActionID = Constant.ApprovalAction.LOAN_APPLY;
	 long checkLevel=-1;							//当前审核级别
	 System.out.println("---------------"+lModuleID+"----"+loanType+"-----"+lActionID+"-------"+loanID);
	 Vector approvalVector=(Vector)approvalBiz.findApprovalTracing(lModuleID,loanType,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanID,-1);
	 
	 //System.out.println("sfjsldjfl"+approvalVector.size());
     
 
        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"贷款申请查询",Constant.YesOrNo.NO);
        
        boolean isdq=false;
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top width=800>
  <TBODY>
  <TR class="tableHeader">
      <TD class=FormTitle height=35 colspan=11><B><%=loanTypeName%>申请——查看</B></TD>
  </TR>
  <TR>
    <TD height=879 vAlign=top colspan=11>
        <TABLE align=center border=0 height=960  width=800>
          <TBODY> 
          <TR> 
            <TD colSpan=4 height=22><FONT size=2>申请书编号：<%=applyCode%></FONT></TD>
            <TD height=22 >&nbsp;</TD>
          </tr>
          <tr> 
            <TD height=22 >&nbsp;</TD>
            <TD height=22 >&nbsp;</TD>
            <TD height=22 >&nbsp;</TD>
            <TD colSpan=8 height=22 align="right" > <FONT size=2> 
            	<INPUT class=button name="asdfasdafasd" onclick="Javascript: frmClose();" type="button" value=" 关 闭 " >
                <INPUT class=button name="asdfasdafasd" onclick="Javascript: frmPrint();" type="button" value=" 打 印 " >
              </FONT>
            </TD>
          </TR>
          <% if ( loanType==LOANConstant.LoanType.WT ){ %>
          <tr> 
            <td  height="171">&nbsp;</td>
            <td height="171" colspan="8"> 
              <hr>
              <table align=center border=0 width="100%">
                <tbody> 
                <tr> 
                  <td colspan=4 height=27> 
                    <p><font size=2><u>委托单位资料</u></font></p>
                  </td>
                </tr>
                <tr> 
                  <td height=31 ><font 
                              size=2>委托单位客户编号：</font></td>
                  <td height=31 ><font size=2> 
                    <input class=box disabled name=textfield24 value="<%=(wtClientInfo.getCode()==null)?"":wtClientInfo.getCode()%>" >
                    </font></td>
                  <td height=31 >&nbsp;</td>
                  <td height=31 >&nbsp;</td>
                </tr>
                <tr> 
                  <td height=27 ><font 
                            size=2>委托单位：</font></td>
                  <td height=27 colspan=3><font size=2> 
                    <input class=box size=80 disabled name=textfield232 value="<%=(wtClientInfo.getName()==null)?"":wtClientInfo.getName()%>">
                    </font></td>
                </tr>
                <tr> 
                  <td height=27 ><font 
                            size=2>联系人：</font></td>
                  <td height=27 ><font size=2> 
                    <input class=box disabled name=textfield2222 size=18 value="<%=(wtClientInfo.getContacter()==null)?"":wtClientInfo.getContacter()%>">
                    </font></td>
                  <td height=27 ></td>
                  <td height=27 ></td>
                </tr>
                </tbody> 
              </table>
            </TD>
          </TR>
          <% } %>
          <TR> 
            <TD height=29 ></TD>
            <TD colSpan=9 height=29> 
              <HR>
              <U> <A href="#" onclick="modifyClient()" >借款单位资料</A></U>
           </TD>
            <TD height=29></TD>
          </TR>
          <TR> 
            <TD height=2 ></TD>
            <TD colSpan=8 height=2 > 
              <TABLE  >
                <TBODY> 
                <tr> 
                  <td colspan=6 height="15"><%  
    	String CustomUrl = "/common/ShowClientInfo.jsp?ClientID="+String.valueOf(sessionMng.m_lClientID);
    	System.out.println("------@@@@@@@@@@@@###########-------------->"+CustomUrl);
    %><jsp:include page='<%=CustomUrl%>' /></td>
                </tr>
                <tr>
                  <td colspan=6 height="15"><hr /></td>
                </tr>
<%if ( Config.getBoolean(ConfigConstant.LOAN_CLIENT_REPORT,false) ){%>	
                <TR> 
                  <!--TD colSpan=2 ><FONT size=2>贷款调查表:</FONT></TD>
                  <TD >
                    <INPUT class=button name="dcb" type=button onfocus="nextfield='xyb'" value="贷款调查表" onclick="Javascript:window.open('../../content/c220a-c.jsp?ParentID=<%=loanID%>&control=view','','width=700,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">  </TD-->
                  <TD >&nbsp;</TD>  
                  <TD >
					<input class=button name="financialDCB" type=button value="资产负债表"  onClick="Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');" onKeydown="if(event.keyCode==13) Javascript:window.open('../../content/c201-c.jsp?control=view&lClientID=<%=cInfo.getClientID()%>&PageName=c211-v.jsp&isSM=<%=Constant.YesOrNo.NO%>&lIsReadOnly=<%=Constant.YesOrNo.YES%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');">                  </TD>
                  <TD>&nbsp;</TD>
                </TR>
 <%}%>               
                
                <TR> 
                  <TD colSpan=3 height=55><FONT size=2><BR>
                    <INPUT class=button name=Submit22 onclick="modifyClient()" type=button value=查看借款单位资料>
                    </FONT></TD>
                  <TD colSpan=2 height=55>&nbsp;</TD>
                  <TD height=55 >&nbsp;</TD>
                </TR>
                </TBODY> 
              </TABLE>
            </TD>
            <TD height=2 >&nbsp;</TD>
            <TD height=2 >&nbsp;</TD>
          </TR>
          <TR> 
            <TD height=14 >&nbsp;</TD>
            <TD colSpan=9 height=14> 
              <HR>
            </TD>
            <TD align=right height=14 >&nbsp;</TD>
          </TR>
          <TR> 
                   
            <TD height=29 >&nbsp;</TD>
            <TD colSpan=4 height=29><U><FONT size=2> <A href="#" onclick="modifyLoanBasic()"> 
              借款详情 </A></FONT></U></TD>
            <TD colSpan=3 height=29>&nbsp;</TD>
            <TD align=left height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
          </TR>
          
          
          <TR>
          
           <% if (iswt) { %>
            	<td colspan=12 >
              <TABLE align=center border=0 width=818>
                <TBODY> 
                <!--
                <TR> 
                  <TD height=26 width=1>&nbsp;</TD>
                  <TD colSpan=3 height=26><FONT size=2><U><FONT size=3><FONT 
                  size=2> <a href="S263-2.jsp?lLoanID=10344&control=view&lClientID=5">其他借款资料</a></FONT></FONT></U></FONT></TD>
                  <TD height=26 width=89>&nbsp;</TD>
                  <TD height=26 width=324>&nbsp;</TD>
                </TR>
                -->
                <TR> 
                  <TD height=44 >&nbsp;</TD>
                  <TD height=44 ><FONT size=2>贷款金额:</FONT></TD>
                  <TD height=44 > 
                    <DIV align=right> 
                      <table width="10%" border="0" height="30">
                        <tr> 
                          <td height="10"><font size=2>￥</font></td>
                        </tr>
                        <tr> 
                          <td height="11">&nbsp;</td>
                        </tr>
                      </table>
                    </DIV>
                  </TD>
                  <TD height=44 ><FONT size=2> 
                  
                    <INPUT class=tar disabled name=textfield2822 size=18 value="<%=DataFormat.formatNumber(loanAmount,2)%>">
                    （大写） 
                    <INPUT class=box disabled name=textfield242233 size=25 value="<%=ChineseCurrency.showChinese( DataFormat.formatAmount(loanAmount) ,1)%>">
                    </FONT>
                    </TD>
                  <TD height=44 ><FONT size=2>贷款利率:</FONT></TD>
                  <TD height=44 nowrap><FONT size=2 > 
                    <INPUT class=box disabled name=textfield2422332 size=10 value="<%=DataFormat.formatRate(interestRate)%>">
                    ％ 手续费 
                    <INPUT class=box disabled name=textfield2422333 size=10 value="<%=DataFormat.formatRate(chargeRate)%>">
                    ％</FONT><FONT size=2>&nbsp;</FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 >&nbsp;</TD>
                  <TD colSpan=2 height=33><FONT size=2>贷款期限:</FONT></TD>
                  <TD colSpan=3 height=33><FONT size=2> 
                    <INPUT class=box disabled name=textfield2422334 size=10 value="<%=DataFormat.getDateString(applyStartDate)%>">
                    到 
                    <INPUT class=box disabled name=textfield2422335 size=10 value="<%=DataFormat.getDateString(applyEndDate)%>">
                    <BR>
                    <INPUT class=box disabled name=textfield211422 size=2 value="<%=intervalNum%>">
                    月</FONT></TD>
                </TR>
                <TR> 
                  <TD height=24 >&nbsp;</TD>
                  <TD colSpan=2 height=33> 
                    <P><FONT size=2>贷款用途:</FONT></P>
                  </TD>
                  <TD height=33 ><FONT size=2> 
                    <TEXTAREA class=box cols=31 disabled name=textarea><%=loanPurpose%></TEXTAREA>
                    </FONT></TD>
                  <TD height=33 >&nbsp;</TD>
                  <TD height=33 >&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=24 >&nbsp;</TD>
                  <TD colSpan=2 height=33> 
                    <P><FONT size=2>项目批准机关及文号:</FONT></P>
                  </TD>
                  <TD colSpan=3 height=33><FONT size=2> 
                    <TEXTAREA class=box cols=31 disabled name=textarea><%=other%></TEXTAREA>
                    </FONT></TD>
                </TR>
                <TR> 
                  <TD colSpan=4><SPAN style="FONT-SIZE: 10pt"> 
                    <INPUT class=button name=Submit423222222222222 onclick="modifyLoanBasic()" type=button value="查看贷款基本资料">
                    </SPAN></TD>
                </TR>
                </TBODY>
              </TABLE>
              </td>
         <% }else{ %> 
            <TD height=29 >&nbsp;</TD>
            <TD height=29 ><FONT size=2> <% if ( isyt ) {%> 贷款额度: <% }else{ %>   借款金额:<% } %></FONT></TD>
            <TD align=right height=29 ><FONT size=2>￥</FONT></TD>
            <TD colSpan=2 height=29><FONT size=2> 
              <INPUT class=tar disabled name=textfield2822 size=16 value="<%=DataFormat.formatNumber(loanAmount,2)%>">
              </FONT></TD>
              
            <% if (isyt) { %>   
            <TD height=29 ><FONT size=2>财务公司承贷额度:</FONT></TD>
            <TD height=29 >￥ 
            <INPUT class=tar disabled name=saleamount size=16 value="<%=DataFormat.formatNumber(selfAmount,2)%>">
		    </td>    
		    <TD height=29 ><FONT size=2>借款期限:</FONT></TD>
            <TD height=29 ><FONT size=2> <INPUT class=box disabled name=textfield211422 size=2 value="<%=intervalNum%>">
              月</FONT></TD>   
              
			<% }else{ %>
            <TD height=29 ><FONT size=2>借款期限:</FONT></TD>
            <TD height=29 ><FONT size=2> <INPUT class=box disabled name=textfield211422 size=2 value="<%=intervalNum%>">
              月</FONT></TD>    
                 	
          	<TD colSpan=2 height=29></TD>
            <TD align=right height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
            <% } %>
          </TR>
          <TR> 
            <TD height=2 >&nbsp;</TD>
            <TD colSpan=2 height=2><FONT size=2>借款原因:</FONT></TD>
            <TD colSpan=7 height=2><FONT size=2> 
              <TEXTAREA class=box cols=65 disabled name=textfield><%=loanReason%></TEXTAREA>
              </FONT></TD>
            <TD align=right height=2 ></TD>
          </TR>
          <TR> 
            <TD height=30 >&nbsp;</TD>
            <TD colSpan=2 height=30><FONT size=2>借款用途:</FONT></TD>
            <TD colSpan=7 height=30><FONT size=2> 
              <TEXTAREA class=box cols=65 disabled name=textarea><%=loanPurpose%></TEXTAREA>
              </FONT></TD>
            <TD align=right height=30 ></TD>
          </TR>
          <TR> 
            <TD height=2 >&nbsp;</TD>
            <TD colSpan=2 height=2><FONT size=2>还款来源及措施:</FONT></TD>
            <TD colSpan=7 height=2><FONT size=2> 
              <TEXTAREA class=box cols=65 disabled name=textarea2><%=other%></TEXTAREA>
              </FONT></TD>
            <TD align=right height=2 >&nbsp;</TD>
           
          </TR>
          <TR> 
          	<td> </td>
            <td colspan=2 height=2><font size="2">借款单位情况简介:</font></td>
            <td colspan=7 height=2><font size=2> 
              <textarea class=box cols=65 disabled name=textarea2><%=(clientInfo==null)?"":clientInfo%></textarea>
              </font></td>
            <TD align=right height=2 >&nbsp;</TD>
          </TR>
          <TR> 
            <TD>&nbsp;</TD>
            <TD colspan="5"><FONT size=2> 
              <INPUT class=button name=Submit423222222222222 onclick="modifyLoanBasic()" type=button value="查看贷款基本资料">
              </FONT>
             
              </TD>
             <% } %>  
          </TR>
          <TR> 
            <TD height=244 >&nbsp;</TD>
            <TD colSpan=8 height=244> 
           
            
              <TABLE cellPadding=0 width=100%>
                <TBODY> 
                <TR > 
                  <TD colSpan=10 height=46 > 
                    <HR>
                  </TD>
                </TR>
                <TR> 
                  <TD colSpan=10 height=46 >
                    <TABLE width=100%>
                      <TBODY> 
                      <TR> 
                        <TD colSpan=4 height=24><U><FONT size=2> <A href="#" onclick="modifyLoanProperty()">贷款性质/担保方式</A></FONT></U></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4> 
                          <TABLE height=36 width=100%>
                            <TBODY> 
                            <TR> 
                              <TD height="44">循环贷款:</TD>
                              <TD height="44">
                                <select class=box disabled name=select2>
                                  <option value="-1"></option>
									<option value="1" <% if (isCircle==1) {%> selected <% } %>>是</option>
									<option value="2" <% if (isCircle==2) {%> selected <% } %>>否</option>
                                </select>
                              </TD>
                              <TD height="44">买卖方贷款:</TD>
                              <TD height="44">
                                <SELECT class=box disabled name=select>
                              	<option value="-1"></option>
								<option value="1" <% if (isSaleBuy==1) {%> selected <% } %> >买</option>
								<option value="2" <% if (isSaleBuy==2) {%> selected <% } %> >卖</option>
								<option value="3" <% if (isSaleBuy==3) {%> selected <% } %> >不适用</option>
                                </SELECT>
                                </TD>
                              <TD height="44">技改贷款:</TD>
                              <TD height="44">
                                <SELECT class=box disabled name=select>
                					<option value="-1"></option>
                					<option value="1" <% if (isTechnical==1) {%> selected <% } %> >是</option>
                					<option value="2" <% if (isTechnical==2) {%> selected <% } %> >否</option>
                                </SELECT>
                              </TD>
                              <TD height="44">&nbsp;</TD>
                            </TR>
                            <TR> 
                              <TD colSpan=7 height="50"><font size="2">贷款担保方式: 
                                <input type="checkbox" disabled name="checkbox" value="checkbox" <% if (isCredit>0) {%> checked <% } %> >
                                信用 
                                <input type="checkbox" disabled name="checkbox2" value="checkbox" <% if (isAssure>0) {%> checked <% } %> >
                                保证 
                                <input type="checkbox" disabled name="checkbox3" value="checkbox" <% if (isImpawn>0) {%> checked <% } %>>
                                质押 
                                <input type="checkbox" disabled name="checkbox4" value="checkbox" <% if (isPledge>0) {%> checked <% } %>>
                                抵押</font></TD>
                            </TR>
                            <TR> 
                              <TD colSpan=7><FONT size=2> 
                                <INPUT class=button name=Submit2as22 onclick="modifyLoanProperty()" type=button value=查看贷款性质/担保方式>
                                </FONT></TD>
                            </TR>
                            </TBODY> 
                          </TABLE>
                                         
                        </TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4> 
                          <HR align=center SIZE=2 >
                        </TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4 height=33><U><SPAN lang=ZH-CN 
                        style="FONT-SIZE: 10pt"><FONT size=2> <A href="#" onclick="modifyAssure()">贷款担保资料明细</A></FONT></SPAN></U></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=4> 
                          <TABLE align=center border=0 class=ItemList width="100%">
                            <TBODY> 
                            <TR align=center class="tableHeader"> 
                              <TD class=ItemTitle height=14 > <FONT size=2>客户编号</FONT></TD>
                              <TD class=ItemTitle height=14 > <FONT size=2>单位名称</FONT></TD>
                              <TD class=ItemTitle height=14 ><FONT size=2>担保方式</FONT></TD>
                              <TD class=ItemTitle height=14 > <FONT size=2>联系人</FONT></TD>
                              <TD class=ItemTitle height=14 > <FONT size=2>电话</FONT> </TD>
                              <TD class=ItemTitle height=14 > <FONT size=2>担保金额</FONT> </TD>
                              <TD class=ItemTitle height=14  align="center"><font size=2>担保比例(%)</font></TD>
                            </TR>
            <% 
          	if ( (assVector!=null)&&(assVector.size()>0) ){
          		int iCount=assVector.size();
          		AssureInfo assInfo=null;
          		long assaID=-1;
          		String asssCode="";
          		String asssName="";
          		long assTypeID=-1;
          		String asssContacter="";
          		String asssPhone="";
          		double assAmount=0;
          		double assRate=0;
          		for ( int i=0;i<iCount;i++ )
          		{
          			assInfo=(AssureInfo)assVector.get(i);
          			assaID=assInfo.getID();
          			asssCode=assInfo.getClientCode();
          			asssName=assInfo.getClientName();
          			assTypeID=assInfo.getAssureTypeID();
          			asssContacter=assInfo.getClientContacter();
          			asssPhone=assInfo.getClientPhone();
          			assAmount=assInfo.getAmount();
          			if ( loanAmount==0 )
          			{
          				assRate=0;
          			}
          			else
          			{
          				assRate=assAmount/loanAmount*100;
          			}	
          			if ( asssContacter==null ) asssContacter="";
          			if( asssPhone==null ) asssPhone="";
          			assSumAmount=assSumAmount+assAmount;
          			
          			
          %>			 
                            <TR align=center> 
                              <TD class=ItemBody height=35 ><FONT size=2><%=asssCode%></FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2><%=asssName%></FONT></TD>
                              <TD class=ItemBody height=23 ><FONT size=2> <%=LOANConstant.AssureType.getName(assTypeID)%> </FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2><%=asssContacter%></FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2><%=asssPhone%></FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2><%=DataFormat.formatNumber(assAmount,2)%></FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2><%=DataFormat.formatNumber(assRate,2)%></font></TD>
                            </TR>
          <% 	
          		}
          	} else {
          %>        
          		           <TR align=center> 
                              <TD class=ItemBody height=35 ><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=23 ><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 ><FONT size=2>&nbsp;</FONT></TD>
                              <TD class=ItemBody height=35 ></TD>
                            </TR>
          
          <% } %>             
                            
                            </TBODY> 
                          </TABLE>
                        </TD>
                      </TR>
                      
                      <TR> 
                        <TD ><FONT size=2>贷款金额:</FONT></TD>
                        <TD > 
                          <DIV align=right><FONT size=2>￥</FONT></DIV>
                        </TD>
                        <TD ><FONT size=2> <INPUT class=tar disabled name=textfield33 size=18 value="<%=DataFormat.formatNumber(loanAmount,2)%>"></FONT></TD>
                        <TD >&nbsp;</TD>
                      </TR>
                      <TR> 
                        <TD height=33 ><FONT size=2><U>其中</U></FONT></TD>
                        <TD height=33 >&nbsp;</TD>
                        <TD height=33 >&nbsp;</TD>
                        <TD height=33 >&nbsp;</TD>
                      </TR>
                      <TR> 
                        <TD ><FONT size=2>有担保贷款总额: </FONT></TD>
                        <TD > <FONT size=2>￥</FONT> </TD>
                        <% if ( assSumAmount==0 ) {%>
                        <TD ><FONT size=2> <INPUT class=tar disabled name=textfield224 size=18 value="0.00"></FONT></TD>
                        <% }else{ %>
                        <TD ><FONT size=2> <INPUT class=tar disabled name=textfield224 size=18 value="<%=DataFormat.formatNumber(assSumAmount,2)%>"></FONT></TD>
                        <% } %>
                        <% if (loanAmount==0) { %>
                        <TD ><font size="2">所占比例: <input class=tar disabled name=textfield2242 size=10 value="0.00">  %</font></TD>
                        <% }else{ %>
                        <TD ><font size="2">所占比例: <input class=tar disabled name=textfield2242 size=10 value="<%=DataFormat.formatNumber(assSumAmount/loanAmount*100,2)%>">  %</font></TD>
                        <% } %>
                      </TR>
                      <TR> 
                        <TD ><FONT size=2>信用贷款总额:</FONT></TD>
                        <TD > <FONT size=2>￥</FONT>  </TD>
                        <% creditAmount=loanAmount-assSumAmount;
                        	if (creditAmount<0) creditAmount=0;
                        	if ( creditAmount==0 ){
                        %>	
                        <TD ><FONT size=2> <INPUT class=tar disabled name=textfield4 size=18 value="0.00"></FONT></TD>
                        <% }else{ %>
                        <TD ><FONT size=2> <INPUT class=tar disabled name=textfield4 size=18 value="<%=DataFormat.formatNumber(creditAmount,2)%>"></FONT></TD>
                        <% } %>
                        <% if (loanAmount==0){%>
                        <TD ><font size="2">所占比例: <input class=tar disabled name=textfield22422 size=10 value="0.00"> %</font></TD>
                        <% }else{ %>
                        <TD ><font size="2">所占比例: <input class=tar disabled name=textfield22422 size=10 value="<%=DataFormat.formatNumber(creditAmount/loanAmount*100,2)%>"> %</font></TD>
                        <% } %>
                      </TR>
                      <TR> 
                        <TD colSpan=3>&nbsp;</TD>
                        <TD >&nbsp;</TD>
                      </TR>
                      
                      <TR> 
                        <TD colSpan=3> 
                          <DIV align=right></DIV>
                          <FONT size=2> 
                          <INPUT class=button name=Submit2222 onclick="modifyAssure()" type="button" value=查看担保资料明细>
                          </FONT></TD>
                        <TD >&nbsp;</TD>
                      </TR>
                      </TBODY> 
                    </TABLE>
                    
                    <hr>
                   <table width="100%" border="0" >
                      <tr> 
                        <td width="19%" nowrap>其他附件:</td>
                        <td width="81%"><iframe src="/NASApp/iTreasury-ebank/attach/AttachFramea.jsp?lID=<%=loanID%>&lTypeID=<%=LOANConstant.AttachParentType.LOAN%>&sCaption=<%=URLEncoder.encode("链接附件")%>&showOnly=true" width=600 height="100" scrolling="Auto" frameborder="0" name="iFrame" ></iframe>
                        </td>
                      </tr>
                    </table>
                    
                  </TD>
                </TR>
                </TBODY> 
              </TABLE>
            </TD>
            <TD height=244 >&nbsp;</TD>
            <TD align=right height=244 >&nbsp;</TD>
          </TR>
          <TR> 
            <TD height=2 >&nbsp;</TD>
            <TD colSpan=4 height=2><FONT size=2>&nbsp;</FONT><BR> </TD>
            <TD align=right colSpan=4 height=2><FONT size=2>&nbsp;</FONT></TD>
            <TD height=2 >&nbsp;</TD>
            <TD align=right height=2 >&nbsp;</TD>
          </TR>
          <TR> 
            <TD height=13 >&nbsp;</TD>
            <TD colSpan=9 height=13>  <HR> </TD>
            <TD align=right height=13 ><FONT size=2>&nbsp;</FONT></TD>
          </TR>
          <TR> 
            <TD height=29 >&nbsp;</TD>
            <TD align=left colSpan=3 height=29><FONT size=2><U>原始计划详细</U></FONT></TD>
           
            <TD align=right height=29 >&nbsp;</TD>
            <TD align=right colSpan=2 height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
          </TR>
          <TR> 
          <td colspan=12>
                    <table><tr>
            <TD height=29 >&nbsp;</TD>
            <TD align=left height=29><FONT size=2> 
              <INPUT class=button name=Submit4 onclick="modifyPlan()"  type=button value=查看原始计划>
              </FONT></TD>
            <TD align=right height=29 ><FONT size=2>&nbsp;</Font></TD>
            <TD  colSpan=3 align=center><FONT size=2>&nbsp;</font></TD>
            <TD  height=29 >&nbsp;</TD>
            <TD align=right height=29 > 
              <DIV align=center><FONT size=2></FONT></DIV>
            </TD>
            <TD align=right height=29 >&nbsp;</TD>
            <TD align=right height=29 >&nbsp;</TD>
          </TR>
          </table>
          </td>
          </tr>
          </TBODY> 
        </TABLE>
        	<br>      
     <table border="1" bordercolor="#999999" cellpadding="0" cellspacing="0">
            <tr> 
              <td>
               
                <table width="100%" border="0">
                  <tr> 
                    <td colspan="3"><font color="#CC0000">*</font><u>本公司审核意见</u></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <% if (isyt) { %>
                  <tr height=34> 
                    <td width=20%>批准贷款额度: </td>
                    <td width=20%> ￥
                    <% if (isLevel1) { %>
                      <script language="javascript">
       					createAmountCtrl("frm","checkAmount","<%=DataFormat.formatNumber(examineAmount,2)%>","checkIntervalNum","dAmountCN");
     				  </script>
                    <% }else{ %>
                    	<input type="text" name="checkAmount" class="box" size="18"  value="<%=DataFormat.formatNumber(examineAmount,2)%>" readonly > 
                    <% } %>
                    </td>
                    <% if ( isLevel1 ) {%>
                    <td width=39%>(大写)<input type="text" name="dAmountCN" class="box"  size="35"   value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(examineAmount),1)%>"   readonly> </td>
                    <% }else{ %>
                    <td width=39%>(大写)<input type="text" name="dAmountCN" class="box"  size="35"   value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(examineAmount),1)%>"   readonly> </td>
                    <% } %>
                    <td width=20%>期限: <input type="text" name="checkIntervalNum" class="box" size="2" value="<%=intervalNum%>" <% if ( !isLevel1 ){ %>readonly <%}%> onfocus="nextfield='checkSelfAmountScale'" onKeyup="checkExamineAmount(frm)"> 月</td>
                    <td ></td>
                  </tr>
                  <tr>
                    <td>批准财务公司承贷比例:</td>
                    <td>￥
                    <% if (isLevel1) { %>
							<input type="text" name="checkSelfAmountScale" class="tar" size="18"  value="<%=DataFormat.formatNumber(selfScale,2)%>" onfocus="nextfield='checkBaseRate'"> 
     				<% }else{ %>
                    	<input type="text" name="checkSelfAmountScale" class="tar" size="18"  value="<%=DataFormat.formatRate(selfScale)%>" readonly > 
					<% } %>                    
                    %</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr >
                    <td>批准财务公司承贷额度:</td>
                    <td>￥
                    	<input type="text" name="checkSelfAmount" class="tar" size="18"  value="<%=DataFormat.formatNumber(selfAmount,2)%>" readonly > 
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr> 
<%
	tmp="";
	if (bankInterestRate>0 )
		tmp=DataFormat.formatRate(bankInterestRate);
		
    String sMagnifierNameRate = URLEncoder.encode("基准利率");
    String sFormNameRate = "frm";
    String sMainPropertyRate = " ";
    if ( !isLevel1 )
    	sMainPropertyRate=sMainPropertyRate+" readonly ";
    String sPrefixRate = "";
    String   strReturnInitValues=tmp;						////放大镜回显栏位对应的初始值
    String[] sMainNamesRate = {"checkBaseRate"};
    String[] sMainFieldsRate = {"RateValue"};
    String[] sReturnNamesRate = {"lRateID"};
    String[] sReturnFieldsRate = {"RateID"};
    String[] sReturnValuesRate = {""+bankInterestID};
    String[] sDisplayNamesRate = {URLEncoder.encode("利率编号"),URLEncoder.encode("利率名称"),URLEncoder.encode("利率(%)")};
    String[] sDisplayFieldsRate = {"RateCode","RateName","RateValue"};
    int nIndexRate = 0;
    String strSQLRate = "getRateSQL('',-1)";//+SessionMng.m_lCurrencyID+")";
    String strNextControlsRate = "checkABS";
    String strMatchValue="RateCode";									////放大镜要模糊匹配的字段
	String strTitle=" 基准利率";
	String strFirstTD="";
	String strSecondTD=" align=right ";
	OBMagnifier.showZoomCtrl(out,sMagnifierNameRate,sFormNameRate,sPrefixRate,sMainNamesRate,sMainFieldsRate,sReturnNamesRate,sReturnFieldsRate,strReturnInitValues,sReturnValuesRate,sDisplayNamesRate,sDisplayFieldsRate,nIndexRate,sMainPropertyRate,strSQLRate,strMatchValue,strNextControlsRate,strTitle, strFirstTD, strSecondTD);
%>
                    <td align=left> %
                    <% if(!isLevel1){ %>
                      
                      <select name="checkABS" size="1"  onfocus="nextfield='txtlRate2'">
 						<% if (adjustRate>=0){ %> <option selected  value="1">+</option><% }%>
                        <% if (adjustRate<0){ %> <option selected  value="2">-</option> <% }%>
                      </select>
                    <%}else{%>
                      <select name="checkABS" size="1"  onfocus="nextfield='txtlRate2'">
 						<option <% if (adjustRate>=0){ %>selected <% }%> value="1">+</option>
                        <option <% if (adjustRate<0){ %>selected <% }%> value="2">-</option>
                      </select>
                    <%}%>  
                      浮动比例 
                      <input type="text" name="txtlRate2" class="tar" size="10" value="<%=DataFormat.formatRate(Math.abs(adjustRate))%>" <% if ( !isLevel1 ){ %>readonly <%}%> onfocus="nextfield='checkChargeRate'">%
                       <!-- 添加固定浮动利率 -->
                        <% if(!isLevel1)
	 				{
				 %>
						<select name="checkABS2" size="1" onBlur="writed()" onfocus="nextfield='txtStaidAdjustRate'">
 						<% if (staidAdjustRate>=0){ %> <option selected  value="1">+</option><% }%>
						<% if (staidAdjustRate<0){ %> <option selected  value="2">-</option> <% }%>
						</select>
				<%
					}
					else
					{
				%>
						<select name="checkABS2" size="1" onBlur="writed()" onfocus="nextfield='txtStaidAdjustRate'">
 						<option <% if (staidAdjustRate>=0){ %>selected <% }%> value="1">+</option>
						<option <% if (staidAdjustRate<0){ %>selected <% }%> value="2">-</option>
						</select>
				<%
					}
				%>
						固定浮动利率
						<input type="text" name="txtStaidAdjustRate" class="box" size="10" value="<%=DataFormat.formatRate(Math.abs(staidAdjustRate))%>" <% if ( !isLevel1 ){ %>readonly <%}%> onBlur="writed()" onfocus="nextfield='checkChargeRate'"> %

                      合同执行利率:
                    </td>
                    <td><input type="text" name="checkInterestRate" class="tar" size="10" value="<%=DataFormat.formatRate(checkRate)%>" readonly disabled> %</td>
                    <td> </td>
                  </tr>
                  <tr > 
                    <td>代理费率:</td>
                    <td> <input type="text" name="checkChargeRate" class="tar" size="10" value="<%=DataFormat.formatRate(chargeRate)%>" <% if ( !isLevel1 ){ %>readonly <%}%> onfocus="nextfield='checkNextUser'">‰</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
				<% } else if ( iswt ) { %>
				  <tr > 
                    <td>批准金额:￥</td>
                    <td> 
                    <% if ( isLevel1 ){ %>
                      <script language="javascript">
       					createAmountCtrl("frm","checkAmount","<%=DataFormat.formatNumber(examineAmount,2)%>","checkIntervalNum","dAmountCN");
     				  </script>
     				<% }else{ %>
     					<input type="text" name="checkAmount" class="box" size="18"  value="<%=DataFormat.formatNumber(examineAmount,2)%>" readonly > 
     				<% } %>	  
                    </td>
                    <% if ( isLevel1 ) {%>
                    <td>（大写）<input type="text" name="dAmountCN" class="box" size="40"   value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(examineAmount),1)%>"  readonly> </td>
                    <% }else{ %>
                    <td>（大写）<input type="text" name="dAmountCN" class="box" size="40"   value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(examineAmount),1)%>"  readonly> </td>
                    <% } %>
                    <td>期限: <input type="text" name="checkIntervalNum" onFocus="nextfield='checkBaseRate'" class="box" size="2" value="<%=intervalNum%>" <% if ( !isLevel1 ){ %>readonly <%}%> onKeyup="checkExamineAmount(frm)">月</td>
                    <td>&nbsp;</td>
                  </tr>
                  
                  <tr> 
                    <td width="120"> 合同执行利率:</td>
                    <td  width="149"> <input type="text" name="checkBaseRate" onFocus="nextfield='checkChargeRate'" class="box" size="10" value="<%=DataFormat.formatRate(interestRate)%>" <% if ( !isLevel1 ){ %>readonly <%}%> > ％</td>
                    <td> 手续费率: <input type="text" name="checkChargeRate" size="10" class="box" value="<%=DataFormat.formatRate(chargeRate)%>" <% if ( !isLevel1 ){ %>readonly <%}%> onfocus="nextfield='checkPayType'" > ％</td>
                    <td>收取方式:</td>
                    <td> 
                    <% if (isLevel1){ %>
                      <select name="checkPayType"  onfocus="nextfield='checkNextUser'" >
                        <option value=-1>　</option>
			 <%
              	long typeVal[]=LOANConstant.ChargeRatePayType.getAllCode();
              	String typeName="";
              	for ( int i=0;i<typeVal.length;i++ )
              	{
              		typeName=LOANConstant.ChargeRatePayType.getName(typeVal[i]);
              %> 
                <option value="<%=typeVal[i]%>" <% if (typeVal[i]==chargeRateTypeID ) {%> selected <% } %>><%=typeName%></option>
              <%	
              	}
              %> 
                      </select>
                   <%}else{%>
                  <select name="checkPayType"  onfocus="nextfield='checkNextUser'" >
			 <%
              	long typeVal[]=LOANConstant.ChargeRatePayType.getAllCode();
              	String typeName="";
              	for ( int i=0;i<typeVal.length;i++ )
              	{
              		typeName=LOANConstant.ChargeRatePayType.getName(typeVal[i]);
              		if (typeVal[i]==chargeRateTypeID)
              		{
              %> 
                <option value="<%=typeVal[i]%>" selected ><%=typeName%></option>
              <%	
              		}
              	}
              %> 
                      </select>
                   <%}%>   
                    </td>
                    
                  </tr>
                  <% } else { %>
                  <tr> 
                    <td>批准金额: ￥ </td>
                    <td> 
                    <% if ( isLevel1 ) { %>
                      <script language="javascript">
       					createAmountCtrl("frm","checkAmount","<%=DataFormat.formatNumber(examineAmount,2)%>","checkIntervalNum","dAmountCN");
     				  </script>
     				<% }else{ %>  
                    	<input type="text" name="checkAmount" class="box" size="18"  value="<%=DataFormat.formatNumber(examineAmount,2)%>" readonly > 
                    <% } %>	
                    </td>
                    <% if ( isLevel1 ) { %>
                    <td>（大写） <input type="text" name="dAmountCN" class="box" size="40"   value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(examineAmount),1)%>" readonly>  </td>
                    <% }else{ %>
                    <td>（大写） <input type="text" name="dAmountCN" class="box" size="40"   value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(examineAmount),1)%>" readonly>  </td>
                    <% } %>
                    <td>期限: <input type="text" name="checkIntervalNum" class="box" size="2" value="<%=intervalNum%>" <% if ( !isLevel1 ){ %>readonly <%}%> onfocus="nextfield='checkBaseRate'" onKeyup="checkExamineAmount(frm)"> 月</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr> 
<%
	tmp="";
	if (bankInterestRate>0 )
		tmp=DataFormat.formatRate(bankInterestRate);
		
    String sMagnifierNameRate = URLEncoder.encode("基准利率");
    String sFormNameRate = "frm";
    String sMainPropertyRate = " ";
    if ( !isLevel1 )
    	sMainPropertyRate=sMainPropertyRate+" readonly ";
    String sPrefixRate = "";
    String   strReturnInitValues=tmp;						////放大镜回显栏位对应的初始值
    String[] sMainNamesRate = {"checkBaseRate"};
    String[] sMainFieldsRate = {"RateValue"};
    String[] sReturnNamesRate = {"lRateID"};
    String[] sReturnFieldsRate = {"RateID"};
    String[] sReturnValuesRate = {""+bankInterestID};
    String[] sDisplayNamesRate = {URLEncoder.encode("利率编号"),URLEncoder.encode("利率名称"),URLEncoder.encode("利率(%)")};
    String[] sDisplayFieldsRate = {"RateCode","RateName","RateValue"};
    int nIndexRate = 0;
    String strSQLRate = "getRateSQL('',-1)";//+SessionMng.m_lCurrencyID+")";
    String strNextControlsRate = "checkABS";
    String strMatchValue="RateCode";									////放大镜要模糊匹配的字段
	String strTitle=" 基准利率";
	String strFirstTD="";
	String strSecondTD=" align=right ";
	OBMagnifier.showZoomCtrl(out,sMagnifierNameRate,sFormNameRate,sPrefixRate,sMainNamesRate,sMainFieldsRate,sReturnNamesRate,sReturnFieldsRate,strReturnInitValues,sReturnValuesRate,sDisplayNamesRate,sDisplayFieldsRate,nIndexRate,sMainPropertyRate,strSQLRate,strMatchValue,strNextControlsRate,strTitle, strFirstTD, strSecondTD);
%>
                    <td align=left>%
                    <% if(!isLevel1){ %>
                      <select name="checkABS" size="1"  onfocus="nextfield='txtlRate2'">
 						<% if (adjustRate>=0){ %> <option selected  value="1">+</option><% }%>
                        <% if (adjustRate<0){ %> <option selected  value="2">-</option> <% }%>
                      </select>
                    <%}else{%>
                      <select name="checkABS" size="1"  onfocus="nextfield='txtlRate2'">
 						<option <% if (adjustRate>=0){ %>selected <% }%> value="1">+</option>
                        <option <% if (adjustRate<0){ %>selected <% }%> value="2">-</option>
                      </select>
                    <%}%>  
                      浮动比例 
                      <input type="text" name="txtlRate2" class="box" size="10" value="<%=DataFormat.formatRate(Math.abs(adjustRate))%>" <% if ( !isLevel1 ){ %>readonly <%}%>  onfocus="nextfield='checkNextUser'"> %                 
                     <% if(!isLevel1)
					{
				%>
						<select name="checkABS2" size="1" onBlur="writed()" onfocus="nextfield='txtStaidAdjustRate'">
 						<% if (staidAdjustRate>=0){ %> <option selected  value="1">+</option><% }%>
						<% if (staidAdjustRate<0){ %> <option selected  value="2">-</option> <% }%>
						</select>
				<%
					}
					else
					{
				%>
						<select name="checkABS2" size="1" onBlur="writed()" onfocus="nextfield='txtStaidAdjustRate'">
 						<option <% if (staidAdjustRate>=0){ %>selected <% }%> value="1">+</option>
						<option <% if (staidAdjustRate<0){ %>selected <% }%> value="2">-</option>
						</select>
				<%
					}
				%>
						固定浮动利率
						<input type="text" name="txtStaidAdjustRate" class="box" size="10" value="<%=DataFormat.formatRate(Math.abs(staidAdjustRate))%>" <% if ( !isLevel1 ){ %>readonly <%}%> onBlur="writed()" onfocus="nextfield='checkNextUser'"> %

                    </td>
                    
                    
                    
                    
                    
                    
                    <td>合同执行利率:</td>
                    <td> <input type="text" name="checkInterestRate" class="box" size="10" value="<%=DataFormat.formatRate(checkRate)%>" disabled > %  </td>
                  </tr>
                  <% } %>
                  <tr> 
                    <td colspan="5" height="14"> <hr> </td>
                  </tr>
                  <tr> 
                    <td>历史审核意见:</td>
                    <td colspan="4"> <br>
                    
                      <table  width="550" border="0" align="left" height="50%" class="ItemList">
                        <tr class="tableHeader"> 
                          <td class="ItemTitle" width="12%" height="20"> <div align="center">序列号</div> </td>
                          <td class="ItemTitle" width="21%" height="20"> <div align="center">意见内容</div> </td>
                          <td class="ItemTitle" width="21%" height="20"> <div align="center">审核人</div> </td>
                          <td class="ItemTitle" width="20%" height="20"> <div align="center">审核决定</div> </td>
                          <td class="ItemTitle" width="26%" height="20"> <div align="center">日期和时间</div> </td>
                        </tr>
                        <% if ( (approvalVector!=null)&&(approvalVector.size()>0) ){ %>
                        <%  	ApprovalTracingInfo approvalInfo=null;
                        		long serialID=-1;
                        		String opinion="";
                        		String checkUserName="";
                        		long resultID=-1;
                        		String strResult="";
                        		Timestamp checkDate=null;
                        		String strCheckDate="";
                        		int approvalCount=approvalVector.size();
                        		for ( int i=0;i<approvalCount;i++ )
                        		{
                        			approvalInfo=(ApprovalTracingInfo)approvalVector.get(i);
                        			serialID=approvalInfo.getSerialID();
                        			opinion=approvalInfo.getOpinion();
                        			checkUserName=approvalInfo.getUserName();
                        			resultID=approvalInfo.getResultID();
                        			checkDate=approvalInfo.getApprovalDate();
                        			strResult=Constant.ApprovalDecision.getName(resultID);
                        			if ( opinion==null ) opinion="";
                        			if (userName==null) userName="";
                        			if ( checkDate==null) 
                        				strCheckDate="";
                        			else
                        				strCheckDate=DataFormat.getDateString(checkDate);
                        %>					
                        <tr> 
                          <td class="ItemBody" width="12%" height="24"><%=serialID%></td>
                          <td class="ItemBody" width="21%" height="24"><%=opinion%></td>
                          <td class="ItemBody" width="21%" height="24"><%=checkUserName%></td>
                          <td class="ItemBody" width="20%" height="24"><%=strResult%></td>
                          <td class="ItemBody" width="26%" height="24"><%=strCheckDate%></td>
                        </tr>
                        <%		} 	%>
                        <% }else{ 	%>
                        <tr> 
                          <td class="ItemBody" width="12%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="21%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="21%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="20%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="26%" height="24">&nbsp;</td>
                        </tr>
                        <% } %>
                      </table>
                    </td>
                  </tr>
                  <% if (!isQuery){%>
                  <tr> 
                    <td>审核意见:</td>
                    <td colspan="4"> <p><font size=2> <textarea class=box <%if (isQuery){%>disabled<%}%> cols=65 name=checkOpinion onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onfocus="nextfield='checkNextUser'"></textarea> </font></p> </td>
                  </tr>
                  <tr>
                    <td><% if (appInfo.getStatusID()==2){%>送交审核人:<%}%></td>
                    <td colspan="4">
                    <%
                    	if (appInfo.getStatusID()==2){
                    	checkLevel=approvalBiz.showApprovalUserList(out,"checkNextUser","checkpass",lModuleID,loanType,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,userID,1,false);
                    	System.out.println("获得当前的审核级别"+checkLevel);
                    	}
                    	//if ( checkLevel==-1 )
                    %> 
                    </td>
                  </tr>
                  <%}%>
                  <% if (isQuery) { %>
                  <tr>
                    <td><% if (appInfo.getStatusID()==2){%>下一级审核人:<%}%></td>
                    <td colspan="4">
                    <%
                    	if (appInfo.getStatusID()==2){                   	
                    		long nextCheckUserID=appInfo.getNextCheckUserID();                    		
                    		UserBean biz = new UserBean();
                    		
                    		// modified by fxzhang 2007-1-8  because of exception
                    		
                    		//UserInfo peopleInfo=biz.findUserByID(nextCheckUserID);  		
                    		//String nextCheckUserName=peopleInfo.getName();
                    		String nextCheckUserName = biz.getNameByID( nextCheckUserID );
                    %>
                    <input type="text" name="nextCheckUserName" class="box" size="10" value="<%=nextCheckUserName%>" disabled > 
                    <%		
                    	}
                    %> 
                    </td>
                  </tr>
                  <% } %>
                </table>
		<table><tr><td>
          <p>
        <input type="button" class=button name="asdfasdafasd22" onClick="Javascript: frmPrint();" value=" 打 印 " >
        <input type="button" class=button name="asdfasdafasd23" onClick="Javascript: window.close();" value=" 关 闭 " >
        </p>
        <p align=right>录入人:<%=appInfo.getInputUserName()%>  录入日期:<%=DataFormat.getDateString(appInfo.getInputDate())%> 状态:<%=LOANConstant.LoanStatus.getName(appInfo.getStatusID())%><br> </p>
      </TD></TR></TABLE>
<input type="hidden" name="lPageCount" value="1" >
<input type="hidden" name="lPageNo" value="1" >
<input type="hidden" name="lOrderParam" value="1" >
<input type="hidden" name="lDesc" value="1" >
<input type="hidden" name="control" value="view" >
<input type="hidden" name="lClientID" value="<%=cInfo.getClientID()%>" >
<input type="hidden" name="isdelete" value="0">
<input type="hidden" name="backurl" value="null" >
<input type="hidden" name="pages" value="S20.jsp">
<input type="hidden" name="lLoanID" value="<%=loanID%>">
<input type="hidden" name="lLoanType" value="<%=loanType%>">
<input type="hidden" name="type" value="<%=loanType%>">
<input type="hidden" name="txtAction" value="modify">
</td></tr></table></TD></TR></TBODY></TABLE>
</Form>

<script>
function frmClose()
{
	if (confirm("是否关闭本窗口"))
	{
		window.close();
	}
}
function check()
{
   if(confirm("是否审核"))
   {
   		frm.txtAction.value="check";
   		frm.backurl.value="l040-c.jsp";
		frm.action="l052-c.jsp";   		
   		showSending();
		frm.submit();
   }
}

function deleted()
{
   if(confirm("是否取消？"))
   {
 		frm.action="l045-c.jsp";   		
   		showSending();
		frm.submit();
   }
}
function addNew()
{
   if(confirm("是否新增？"))
   {
   		if ( frm.lLoanType.value=='<%=LOANConstant.LoanType.WT%>' )
   		{
			frm.action="l001-v.jsp";
		}else{
			frm.action="l002-v.jsp";
		}	
			   		
   		showSending();
		frm.submit();
   }
}
function modifyClient()
{
	window.open('<%=Env.EBANK_URL%>loan/query/q006-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&lClientID=<%=cInfo.getClientID()%>&txtAction=modify&statusID=<%=appInfo.getStatusID()%>&mType=client', '查看客户基本资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyLoanBasic()
{
	window.open('<%=Env.EBANK_URL%>loan/query/q006-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=modify&statusID=<%=appInfo.getStatusID()%>&mType=loanBasic', '查看贷款基本资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyLoanProperty()
{
	window.open('<%=Env.EBANK_URL%>loan/query/q006-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=modify&statusID=<%=appInfo.getStatusID()%>&mType=loanProperty', '查看贷款属性资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyAssure()
{
	window.open('<%=Env.EBANK_URL%>loan/query/q006-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=modify&statusID=<%=appInfo.getStatusID()%>&mType=assure', '查看贷款保证资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function modifyPlan()
{
	window.open('<%=Env.EBANK_URL%>loan/query/q006-c.jsp?lLoanType=<%=loanType%>&lLoanID=<%=appInfo.getID()%>&txtAction=modify&statusID=<%=appInfo.getStatusID()%>&mType=plan', '查看贷款计划资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function goBack()
{
   frm.action="l051-c.jsp";
   showSending();
   frm.submit();
}
function frmPrint()  //打印 action on the Form : theform
{
     if (confirm("是否打印？"))
     {
         window.open('<%=Env.EBANK_URL%>loan/query/q017-c.jsp?lLoanID=<%=loanID%>','',"width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
     }
}
</script>

<%
	LOANHTML.showHomeEnd(out);
	}
	catch( Exception ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>