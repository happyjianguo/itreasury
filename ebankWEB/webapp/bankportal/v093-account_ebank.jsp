<%
/**ҳ�湦��˵��
 * ҳ������ ��v093.jsp
 * ҳ�湦�� : ���˵���ӡ
 * ��    �� ��wenboshang
 * ��    �� ��2005-10-28
 * ��ʵ��˵����
 *	1�����˵���Ϣ��ӡ
 * ����˵�� ��
 * �޸���ʷ ��
 */
%>

<%@ page contentType = "text/html;charset=GBK" %>

<!--�ർ�벿�ֿ�ʼ-->
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AcctTransInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AccountInfo"/>
<jsp:directive.page import="com.iss.itreasury.util.Env"/>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz" %>
<%@ page import="java.util.*" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>
<jsp:directive.page import="com.iss.itreasury.util.DataFormat"/>
<%@ page import="java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<!--�ർ�벿�ֽ���-->

<%
        		
		//ǩ���������//////////////////////////////////////////////////// add by zhanglei 2010-05-20
     	String basePath = request.getScheme() +"://"+request.getServerName()+":"+request.getServerPort();//������������·��
		String nowDate = Env.getSystemDateTimeString();//��ǰ����
		String officeName = Env.getClientName();//���´�����
		//�鿴�Ƿ���ǩ��Ȩ��--start----
		long officeId = sessionMng.m_lOfficeID;
		long clientId = sessionMng.m_lClientID;
		long bzid = sessionMng.m_lCurrencyID;
		long userId = sessionMng.m_lUserID;
		//String nbilltypeId = EBankDocRiht.ebankDocType[5][0];
		String nbilltypeId = String.valueOf(EBankDocRiht.ZHJYMX);
		OBAccountSignatureBiz osb  =new OBAccountSignatureBiz();
		boolean hasRight = osb.checkHasRight(clientId,officeId,bzid,nbilltypeId,userId);
		//�鿴�Ƿ���ǩ��Ȩ��--end----
		double px=300;//������
		double py = 200;//������
		//////////////////////////////////////////////////////////////
    try
	{
		//emoduleid����6��������ģ��
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        //		out.flush();
		//		return;
		//}
		String strTitle = null;
		//�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		/**ҳ��У�鿪ʼ���û���¼У�顢�û�Ȩ��У�顢�ظ�����У�飩*/
		JSPLogger.info("*******����ҳ��--account\\view\\v093-account_ebank.jsp*******");
		/**ҳ��У�����*/
		//���PageLoaderKey
		//String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");		
		/**ҵ���߼���ʼ*/		
		String strContext = request.getContextPath();	
		/**���ؽ������֮һ���˻��ڳ����**/
		double beginBalance  = Double.NaN;
		beginBalance = ((Double)request.getAttribute("balanceCol")).doubleValue();		
		/**���ؽ������֮�����˻���ʷ������Ϣ**/
		Object[] queryResults = null;
		queryResults = (AcctTransInfo[])request.getAttribute("transInfos");
		JSPLogger.info("�˻���ʷ����    queryResults :  " + queryResults + " length:" + ((queryResults != null)?queryResults.length:-1));		
		/**���ؽ������֮������������˻�������Ϣ**/
		AccountInfo acctInfo = new AccountInfo();
		//acctInfo = (AccountInfo)request.getAttribute("acctInfo");	
		/**��ѯ����**/
		QueryBillPrintParam param = new QueryBillPrintParam();
		param = (QueryBillPrintParam)request.getAttribute("param");	
		if(param!=null)
		{
			long acctId = param.getAccountId();
		    String strAccountNo = NameRef.getAccountNOByAccountID(acctId);
			acctInfo.setAccountNo(strAccountNo);
			String strAccountName = NameRef.getAccountNameByAccountID(acctId);
			acctInfo.setAccountName(strAccountName);
			acctInfo.setClientId(param.getClientIdFrom());
			acctInfo.setCurrencyType(param.getCurrencyType());	
		}
		/**ҵ���߼�����*/		
		//����ͳ�Ʊ���
		double startBalance          = 0.00;  //�ڳ����
		double sumDebitAmount  		 = 0.00;  //�跽���ϼ�
		double sumCreditAmount 		 = 0.00;  //�������ϼ�
		double sumDebitAmountPerDay  = 0.00;  //ÿ�ս跽���ϼ�
		double sumCreditAmountPerDay = 0.00;  //ÿ�մ������ϼ�
		double beginBalancePerDay	=0.00;	//ÿ�յ��ڳ����		
		String strDebitAmount = null;
		String strCreditAmount = null;		
		Date   statDate       		 = null;  //����
		OBHtml.showLOANPrintHead(out,true, "A4", "", -1);		
		/**ҳ����ʾ��ʼ*/       
%>
<%
	//IPrintTemplate.showPrintHead(out,false,"A4","",1,sessionMng.m_lOfficeID);
	eBankPrint.showPrintReport(out,sessionMng,"A4",2,false);
%>
<TABLE class="" width="950" align="center">	
	  <TR>
    		<TD class=FormTitle align="center"><B>�˻�������ϸ</B></TD>
		</TR>
			<TD>
				<TABLE  width="950" align="center" border="0" cellspacing="0" cellpadding="0" align="center">
					<TBODY>
						<TR>
						<BR>
							<TD valign="top" width="100%">
							<TABLE   width="950"  border="0" cellspacing="0" cellpadding="0" class="table1"  align="center">
							<TBODY>
						<TR>							
							<TD class="td-right"> <font style="font-size: 12px">&nbsp;�ͻ����:</font></TD>
							<TD class="td-right"> <font style="font-size: 12px">&nbsp;<%=NameRef.getClientCodeByID( acctInfo.getClientId() )%></font></TD>
							<TD class="td-right"><font style="font-size: 12px">&nbsp;�ͻ�����:</font></TD>
							<TD class="td-right"><font style="font-size: 12px">&nbsp;<%=NameRef.getClientNameByID( acctInfo.getClientId() )%></font></TD>							
						</TR>						
						<TR>							
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;�˺�:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=acctInfo.getAccountNo()%></font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;�˻�����:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=acctInfo.getAccountName()%></font></TD>
						</TR>						
						<TR>							
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;����:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=NameRef.getCurrencyNameByID( acctInfo.getCurrencyType() )%></font></TD>
							<TD class=td-topright >&nbsp;</td>
							<TD class=td-topright >&nbsp;</td>
  						</TR>
						<TR>						
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;���� ��:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=  DataFormat.formatDate(param.getTransactionStartDate(),1)%></font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;��:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=  DataFormat.formatDate(param.getTransactionEndDate(),1)%></font></TD>
					</TABLE>
			</TD>
		</TR>			
	</TBODY>
</TABLE>
<BR>
<TABLE  width="950" border="0" cellspacing="0" cellpadding="0" align="center">
<TBODY> 		
		<TR>
			<TD>
				<TABLE  width="950" align="center" border="0" cellspacing="0" cellpadding="0"  class="table1" >
					<TBODY >						
						<TR >
				            <TD  class="td-right" width="8%" align="center">&nbsp;����</TD>
				            <TD  class="td-right" width="11%" align="center">&nbsp;ժҪ</TD>
				            <TD  class="td-right" width="10%" align="center">&nbsp;���ݺ�</TD>
						    <TD  class="td-right" width="16%" align="center">&nbsp;�Է��˺�</TD>
							<TD  class="td-right" width="16%" align="center">&nbsp;�Է��˻�����</TD>
							<TD  class="td-right" width="13%" align="center" nowrap>&nbsp;�跽���</TD>
							<TD  class="td-right" width="13%" align="center" nowrap>&nbsp;�������</TD>
							<TD  class="td-right" width="13%" align="center" nowrap>&nbsp;���</TD>
						</TR>	
						<TR >
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright align="center">&nbsp;<b>�ڳ����</b></TD>
				            <TD class=td-topright >&nbsp;</TD>
						   	<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright  align="right">&nbsp;<b>
													<%		
										startBalance = beginBalance;
										beginBalancePerDay = beginBalance;										
										%>
										<b>
										<%
										out.println( DataFormat.formatNumber(beginBalance,2) );
										%>
										</b>&nbsp;</TD>
						</TR>
						
						<%
						if( queryResults != null && queryResults.length >0 )
						{
							
							//��ʱ����
							statDate = ( (AcctTransInfo)queryResults[0]).getTransactionTime();
							
							for(int i = 0;i<queryResults.length;i++)
							{
								AcctTransInfo info = (AcctTransInfo)queryResults[i];
								
								if( info.getDirection() == AccountInfo.DEBIT )
								{
									sumDebitAmount   += info.getDebitAmount();
									strDebitAmount = DataFormat.formatNumber(info.getDebitAmount(),2);
									strCreditAmount = "";
								}
								else if( info.getDirection() == AccountInfo.CREDIT )
								{
									sumCreditAmount  += info.getCreditAmount();
									strCreditAmount = DataFormat.formatNumber(info.getCreditAmount(),2);
									strDebitAmount = "";																	
								}
								
								if( queryResults.length == 1 || info.getTransactionTime().equals(statDate) )
								{
									//�����˻�ͬһ��Ľ�������
									sumDebitAmountPerDay   += info.getDebitAmount();
									sumCreditAmountPerDay  += info.getCreditAmount();
								}
								
								if(  !info.getTransactionTime().equals(statDate) )
								{
									beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );								
									%>
									<TR >
							            <TD class=td-topright >&nbsp;<%=DataFormat.formatDate(statDate,1)%></TD>
							            <TD class=td-topright align="center"><b>&nbsp;���պϼ�</b></TD>
							            <TD class=td-topright >&nbsp;</TD>
									   	<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumDebitAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumCreditAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(beginBalancePerDay ,2  )%></b></TD>
									</TR>
									<%
									//�������ͬһ�������,�������³�˼��ͳ������
									statDate = info.getTransactionTime();
									sumDebitAmountPerDay   = info.getDebitAmount();
									sumCreditAmountPerDay  = info.getCreditAmount();
								}
								String strBalance ="";
								double balance = beginBalance+info.getCreditAmount()-info.getDebitAmount();
								beginBalance = balance;
								strBalance = DataFormat.formatNumber(balance , 2);
								if(i==queryResults.length-1){
									%>
						<TR  height="0" >
				            <TD height="0"  width="8%" align="center"></TD>
				            <TD height="0"  width="11%" align="center"></TD>
				            <TD height="0" width="10%" align="center"></TD>
						    <TD height="0"   width="16%" align="center"></TD>
							<TD height="0" width="16%" align="center"></TD>
							<TD height="0"  width="13%" align="center" ></TD>
							<TD height="0"   width="0" align="right"  ></TD>
							<TD height="0"  width="13%" align="center" id="signaturePosition_5" ></TD>
						</TR>
									<%
								}							
						%>
						<TR >
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatDate(info.getTransactionTime(),1)%></TD>
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getAbstractInfo())%></TD>
				            <%
				                  String infoCheckNo = "";
				                  if(info.getCheckNo() != null && info.getCheckNo().trim().length() > 0 && !info.getCheckNo().endsWith("0")){
				                         infoCheckNo = info.getCheckNo();
				                  }
				             %>
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(infoCheckNo) %></TD>
						   	<TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getOppAccountNo())%></TD>
							<TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getOppAccountName())%></TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap>&nbsp;<%=strDebitAmount%>&nbsp;</TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap>&nbsp;<%=strCreditAmount%>&nbsp;</TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap><%=strBalance%></TD>
						</TR>
						<%
								/** ���ֻ��һ����¼������������¼�������һ�б��պϼ�
								 * &����Ѿ������һ����¼�ˣ�����������¼�������һ�б��պϼ�
								 **/
								if( queryResults.length == 1 || i == queryResults.length -1 )
								{
									beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );																
									%>
									<TR >
							            <TD class=td-topright >&nbsp;<%=DataFormat.formatDate(statDate,1)%></TD>
							            <TD class=td-topright align="center"> &nbsp;<b>���պϼ�</b></TD>
							            <TD class=td-topright >&nbsp;</TD>
									   	<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumDebitAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumCreditAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(	beginBalancePerDay ,2  )%></b>&nbsp;</TD>
									</TR>
									<%
									//��˼��ͳ����������
									statDate = info.getTransactionTime();
									sumDebitAmountPerDay  = 0.00;
									sumCreditAmountPerDay = 0.00;
								}								
							}
						}
						else
						{
						%>
						<TR  height="0" >
				            <TD height="0"  width="8%" align="center"></TD>
				            <TD height="0"  width="11%" align="center"></TD>
				            <TD height="0" width="10%" align="center"></TD>
						    <TD height="0"   width="16%" align="center"></TD>
							<TD height="0" width="16%" align="center"></TD>
							<TD height="0"  width="13%" align="center" ></TD>
							<TD height="0"   width="0" align="right"  ></TD>
							<TD height="0"  width="13%" align="center" id="signaturePosition_5" ></TD>
						</TR>
						<TR >
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
						</TR>
						<%
						}
						%>

						<TR >
						    <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright align="center">&nbsp;<b>��ĩ���</b></TD>
				            <TD class=td-topright >&nbsp;</TD>
						   	<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumDebitAmount,2)%></b>&nbsp;</TD>
							<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumCreditAmount,2)%></b>&nbsp;</TD>
							<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(Arithmetic.add( startBalance,Arithmetic.sub(sumCreditAmount,sumDebitAmount) ),2)%></b>&nbsp;</TD>
						</TR>						
						</TBODY>
				</TABLE>
			</TD>
		</TR>		
</TBODY>
</TABLE>
</TD>
</TABLE>
<BR>
<table   width="950"  border="0" align="center">
	<tr>				
        <td width="70%" > &nbsp;</td>
		<td width="30%" align="right" id="printDate">��ӡʱ�䣺<%=DataFormat.formatDate(Env.getSystemDateTime(),DataFormat.FMT_DATE_YYYYMMDD)%></td>
	</tr>		
</table>
<!--ҳ�������-->
<!--ҳ��ű���ʼ-->
<!--ҳ��ű�Ԫ�ؽ���-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//��ʾҳ��β	
	/**ҳ����ʾ����*/
%>

<%

	if(hasRight){

 %>
<BODY language="javascript" onResize="ReSetSignaturePosition()" style="margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0">	
<OBJECT id="SignatureControl"  codebase="/websignature/cab/iSignatureHTML.cab#Version=7,1,0,196"  classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" width=0 height=0>
<param name="ServiceUrl" value="<%=basePath%>/NASApp/SignatureServlet">                       <!--��ȥ���ݿ������Ϣ-->
<param name="WebAutoSign" value="0">                     <!--�Ƿ��Զ�����ǩ��(0:�����ã�1:����)-->
<param name="PrintControlType" value=2>                  <!--��ӡ���Ʒ�ʽ��0:������  1��ǩ�·���������  2�������̿��ƣ�-->
<param name="MenuDocVerify" value=false>                 <!--�˵��ĵ���֤�ĵ�-->
<param name="MenuServerVerify" value=false>              <!--�˵�������֤,����汾ר��-->
<param name="MenuDigitalCert" value=false>               <!--�˵�����֤��-->
<param name="MenuDocLocked" value=false>                 <!--�˵��ĵ�����-->
<param name="MenuDeleteSign" value=false>                <!--�˵�����ǩ��-->
<param name="MenuMoveSetting" value=true>                <!--�˵���ֹ�ƶ�-->
<param name="PrintWater" value=false>                    <!--�Ƿ��ӡˮӡ-->
</OBJECT>
</BODY>
<script language="javascript">
	function setPirntPosition(){
		    oldScrollTop=document.body.scrollTop;
			document.body.scrollTop=0;
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = 899;
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}
	window.onbeforeprint=setPirntPosition;
	window.onafterprint=ReSetSignaturePosition;
		try{
			var   x,y;   
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-100);
		    document.all.SignatureControl.EnableMove = "false";          //����ǩ���Ƿ�����ƶ�
		    document.all.SignatureControl.PassWord = "123456";           //ǩ������,ϵͳĬ��Ϊ"",�����øĲ������ǩ�º󵯳���ѡ��ǩ�´����е����뽫Ĭ��Ϊ������      
		    document.all.SignatureControl.ShowSignatureWindow = "0";     //��ȡ������ѡ��ǩ�µĴ����Ƿ�ɼ�
		    document.all.SignatureControl.FieldsList = "strTransNos=���ҵ����";          //��ȡ������ǩ�±�����Ϣ�б�
		    document.all.SignatureControl.SaveHistory = "false";         //��ȡ�������Ƿ񱣴���ʷ��¼true-false
		    document.all.SignatureControl.UserName = "<%=Env.getClientName()%>"; //��ȡ������ǩ�µ��û�����
		    document.all.SignatureControl.PositionByTagType = 0;
		     	document.all.SignatureControl.Position(sx,sy);      //����ǩ��ʲôλ����Position��(0:���Ͻǡ�1:�м䡢2:���Ͻ�)
		    document.all.SignatureControl.ValidateCertTime = false;      //�����Ƿ���֤֤����ڻ�δ����
		    document.all.SignatureControl.ExtParam = "11111111|11";//transNo
		    document.all.SignatureControl.ExtParam1 = "<%=nowDate%>";          //����ǩ�¸�����Ϣ
		    //document.all.SignatureControl.SetWaterInfo("����ר��","����",0X0000FF,0);//����ǩ������ˮӡ��Ϣ
		    document.all.SignatureControl.WebSetFontOther(true,"","0","����",7,"$0000FF",false);//����ǩ��ͼ��������Ϣ(����ʱ�䡢ǩ����Ա�������)��ʾģʽ
		    document.all.SignatureControl.DefaultSignTimeFormat = 8;    //����ǩ�¸���ʱ��Ĭ�ϸ�ʽ
		    document.all.SignatureControl.SetSignTextOffset(0,30);      //���ø����ǵĸ�����Ϣ(����ʱ��)��ƫ����
		  }catch(e){
		    alert(e);
		  }
		    try{
		    	document.all.SignatureControl.RunSignature();               //ִ��ǩ��  
		    }catch(e){
		    	alert("���ǩ�´�������ϵ������Ա");
		    }
		  		 	//������ڴ�С�仯�ˣ�ǩ�µ�λ��ҲҪ�ı䡣  add by zhanglei  2010.06.11	    
	function ReSetSignaturePosition(){
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-120);
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}
</script>
<%
	}
%>