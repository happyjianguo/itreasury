<!--Header start-->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.query.dataentity.*,
			com.iss.itreasury.ebank.util.*,			
			com.iss.itreasury.ebank.obdataentity.*,	
			com.iss.itreasury.ebank.obquery.bizlogic.*,
			com.iss.itreasury.ebank.obquery.dataentity.*,
			com.iss.itreasury.util.*"
%>
<!--Header end-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
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
			
		       	OBHtml.showOBHomeHead(out,sessionMng,"[指令查询]",Constant.YesOrNo.YES);
		       	long recordCount=-1;
		       	String action=GetParam(request,"txtAction","");
		       	if ( action.equals("First"))
		       	{
		       		termInfo.reset();
		       	}	
		%>	
		
		<script language="JavaScript" src="/webob/js/Check.js"></script>
		<script language="JavaScript" src="/webob/js/Control.js"></script>
		<safety:resources />
		<script type="text/JavaScript">
		<!--
		
		
		
		function MM_preloadImages() { //v3.0
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		//-->
		</script>
		<form name="frm1" method="post">
		<input type=hidden name=txtAction value=query>
		 <table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="">
		  <tr>
		    <td height="4"></td>
		  </tr>
		  <tr>
		    <td height="1" bgcolor="#47BBD2"></td>
		  </tr>
		  <tr>
		    <td height="24" valign="top"><table width="150" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="1" bgcolor="#47BBD2"></td>
		        <td width="124" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">申请指令查询</span></td>
		        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		    <td height="5"></td>
		  </tr>
		</table>
			 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
		      <tr>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td><table width="180" border="0" cellspacing="0" cellpadding="0">
		
		          <tr>
		            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
		            <td width="160" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 网上指令修改－指令查询</td>
		            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
		          </tr>
		        </table></td>
		      </tr>
		    </table>
		<table width="774" border="0" cellpadding="0" cellspacing="0" class=normal id="table1" align="center">
			<tr> 
				<td>
			<table width="100%"  align="center"  cellspace="1">
				<tr> 
					<td>&nbsp;</td>
					<td width="22%" nowrap>指令类型：</td>
					<td></td>
					<td width="35%"> 
						<select name="typeID" class="box" size="1" onfocus="nextfield='statusID'">
						<option value="-1"></option>
					<%
		              	long typeVal[]=OBConstant.LoanInstrType.getAllCode();
						//long typeVal[] = {OBConstant.LoanInstrType.LOAN_ZYDQ,OBConstant.LoanInstrType.EXTEND,OBConstant.LoanInstrType.DUEBILL,OBConstant.LoanInstrType.AHEADPAY };
		              	String typeName="";
		              	long selType=(termInfo==null)?-1:termInfo.getTypeID();
		              	for ( int i=0;i<typeVal.length;i++ )
		              	{
		              		typeName=OBConstant.LoanInstrType.getName(typeVal[i]);
		            %> 
		                <option value="<%=typeVal[i]%>" <% if(typeVal[i]==selType){%>selected<%}%> ><%=typeName%></option>
		            <%	
		              	}
		            %> 
		 				</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr> 
					<td height="28">&nbsp;</td>
					<td width="22%" nowrap>指令状态：</td>
					<td></td>
					<td width="35%"> 
						<select name="statusID" class="box" size="1" onfocus="nextfield='inputDateBegin'">
						<option value="99"></option>
					<%
		              	long statusVal[]=OBConstant.LoanInstrStatus.getAllCode();
		              	String statusName="";
		              	for ( int i=0;i<statusVal.length;i++ )
		              	{
							long selStatus=(termInfo==null)?99:termInfo.getStatusID();
			           		statusName=OBConstant.LoanInstrStatus.getName(statusVal[i]);
		            %> 
		                <option value="<%=statusVal[i]%>" <% if(statusVal[i]==selStatus){%>selected<%}%> ><%=statusName%></option>
		            <%	
		              	}
		            %> 
		 
						</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>   
					<td>&nbsp;</td>
					<td nowrap>指令录入时间：</td>
					<td>从：</td>
					<td nowrap>
						<fs_c:calendar 
			         	    name="inputDateBegin"
				          	value="" 
				          	properties="nextfield ='inputDateEnd'" 
				          	size="20"/>
				<script>
	          		$('#inputDateBegin').val('<%=(termInfo==null?"":DataFormat.getDateString(termInfo.getInputDateBegin()))%>');
	          	</script>
					<td>到：</td>
					<td align=left nowrap>
					<fs_c:calendar 
			         	    name="inputDateEnd"
				          	value="" 
				          	properties="nextfield ='scodeBegin'" 
				          	size="20"/>
				     <script>
	          		$('#inputDateEnd').val('<%=(termInfo==null?"":DataFormat.getDateString(termInfo.getInputDateEnd()))%>');
	          	</script>
					 </td>	
				</tr>
				<tr> 
		 
					<td width="0%" height="28" >&nbsp;</td>
					<td nowrap>合同编号：		</td>
				<%
				String codeBegin="";
				if (termInfo!=null)
				{
					codeBegin=termInfo.getCodeBegin();
				}
		 		String strMagnifierName1 = URLEncoder.encode("合同编号");							//放大镜的名称
				String strFormName1 = "frm1";										//主页面表单名称
				String strPrefix1 ="";												////控件名称前缀
				String[] strMainNames1 = {"scodeBegin"};				//放大镜回显栏位值列表
				String[] strMainFields1 = { "sContractCode"};				//放大镜回显栏位对应的表格字段
				String[] strReturnNames1 = {"codeBegin"};							//放大镜返回值列表(隐含值)
				String[] strReturnFields1 = {"sContractCode"};								//放大镜返回值(隐含值)对应的表格字段列表
				String   strReturnInitValues1=""+codeBegin;						////放大镜回显栏位对应的初始值
				String[] strReturnValues1 = {""+codeBegin};								//放大镜返回值(隐含值)对应的初始值
				String[] strDisplayNames1 = {URLEncoder.encode("合同编号")};				//放大镜小窗口显示的栏位名称
				String[] strDisplayFields1 = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
				int intIndex1 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
				String strMainProperty1 = " ";									//放大镜的对应控件栏位属性
				String strMatchValue1="sContractCode";									////放大镜要模糊匹配的字段
				String strNextControls1 = "scodeEnd";								//设置下一个焦点
				String strTitle1="由";
				String strFirstTD1="";
				String strSecondTD1=" ";
					
				//调用产生放大镜的方法
				OBMagnifier.showZoomCtrl(out,strMagnifierName1,strFormName1,strPrefix1,strMainNames1,strMainFields1,
					strReturnNames1,strReturnFields1, strReturnInitValues1, strReturnValues1,strDisplayNames1,strDisplayFields1,
					intIndex1,strMainProperty1,"getContractCode()", strMatchValue1,strNextControls1 ,strTitle1, strFirstTD1, strSecondTD1 );
				%> 
				<%
				String codeEnd="";
				if (termInfo!=null)
				{
					codeEnd=termInfo.getCodeEnd();
				}
				String strMagnifierName2 = URLEncoder.encode("合同编号");							//放大镜的名称
				String strFormName2 = "frm1";										//主页面表单名称
				String strPrefix2 ="";											////控件名称前缀
				String[] strMainNames2 = {"scodeEnd"};								//放大镜回显栏位值列表
				String[] strMainFields2 = { "sContractCode"};							//放大镜回显栏位对应的表格字段
				String[] strReturnNames2 = {"codeEnd"};							//放大镜返回值列表(隐含值)
				String[] strReturnFields2 = {"sContractCode"};								//放大镜返回值(隐含值)对应的表格字段列表
				String   strReturnInitValues2=""+codeEnd;						////放大镜回显栏位对应的初始值
				String[] strReturnValues2 = {""+codeEnd};								//放大镜返回值(隐含值)对应的初始值
				String[] strDisplayNames2 = {URLEncoder.encode("合同编号")};				//放大镜小窗口显示的栏位名称
				String[] strDisplayFields2 = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
				int intIndex2 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
				String strMainProperty2 = " ";									//放大镜的对应控件栏位属性
				String strMatchValue2="sContractCode";									////放大镜要模糊匹配的字段
				String strNextControls2 = "sinstrNoBegin ";								//设置下一个焦点
				String strTitle2="到";
				String strFirstTD2=" ";
				String strSecondTD2=" ";
			
				OBMagnifier.showZoomCtrl(out,strMagnifierName2,strFormName2,strPrefix2,strMainNames2,strMainFields2,
					strReturnNames2,strReturnFields2, strReturnInitValues2, strReturnValues2,strDisplayNames2,strDisplayFields2,
					intIndex2,strMainProperty2,"getContractCode()", strMatchValue2,strNextControls2,strTitle2, strFirstTD2, strSecondTD2);
				%>	
			
				</tr>
				<tr> 
					<td width="0%" height="28">&nbsp;</td>
					<td nowrap>指令号：</td>
				<%
				String instrNoBegin="";
				if (termInfo!=null)
				{
					instrNoBegin=termInfo.getInstrNoBegin();
				}
		 		String strMagnifierName11 = URLEncoder.encode("指令号");							//放大镜的名称
				String strFormName11 = "frm1";										//主页面表单名称
				String strPrefix11 ="";												////控件名称前缀
				String[] strMainNames11 = {"sinstrNoBegin"};				//放大镜回显栏位值列表
				String[] strMainFields11 = { "sInstructionNo"};				//放大镜回显栏位对应的表格字段
				String[] strReturnNames11 = {"instrNoBegin"};							//放大镜返回值列表(隐含值)
				String[] strReturnFields11 = {"sInstructionNo"};								//放大镜返回值(隐含值)对应的表格字段列表
				String   strReturnInitValues11=""+instrNoBegin;						////放大镜回显栏位对应的初始值
				String[] strReturnValues11 = {""+instrNoBegin};								//放大镜返回值(隐含值)对应的初始值
				String[] strDisplayNames11 = {URLEncoder.encode("指令号")};				//放大镜小窗口显示的栏位名称
				String[] strDisplayFields11 = {"sInstructionNo"};					//放大镜小窗口显示栏位对应的表格字段
				int intIndex11 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
				String strMainProperty11 = " ";									//放大镜的对应控件栏位属性
				String strMatchValue11="sInstructionNo";									////放大镜要模糊匹配的字段
				String strNextControls11 = "sinstrNoEnd";								//设置下一个焦点
				String strTitle11="由";
				String strFirstTD11="";
				String strSecondTD11=" ";
					
				//调用产生放大镜的方法
				OBMagnifier.showZoomCtrl(out,strMagnifierName11,strFormName11,strPrefix11,strMainNames11,strMainFields11,
					strReturnNames11,strReturnFields11, strReturnInitValues11, strReturnValues11,strDisplayNames11,strDisplayFields11,
					intIndex11,strMainProperty11,"getInstrCode()", strMatchValue11,strNextControls11 ,strTitle11, strFirstTD11, strSecondTD11 );
				%> 
				<%
				String instrNoEnd="";
				if (termInfo!=null)
				{
					instrNoEnd=termInfo.getInstrNoEnd();
				}
		
				String strMagnifierName12 = URLEncoder.encode("指令号");							//放大镜的名称
				String strFormName12 = "frm1";										//主页面表单名称
				String strPrefix12 ="";											////控件名称前缀
				String[] strMainNames12 = {"sinstrNoEnd"};								//放大镜回显栏位值列表
				String[] strMainFields12 = { "sInstructionNo"};							//放大镜回显栏位对应的表格字段
				String[] strReturnNames12 = {"instrNoEnd"};							//放大镜返回值列表(隐含值)
				String[] strReturnFields12 = {"sInstructionNo"};								//放大镜返回值(隐含值)对应的表格字段列表
				String   strReturnInitValues12=""+instrNoEnd;						////放大镜回显栏位对应的初始值
				String[] strReturnValues12 = {""+instrNoEnd};								//放大镜返回值(隐含值)对应的初始值
				String[] strDisplayNames12 = {URLEncoder.encode("指令号")};				//放大镜小窗口显示的栏位名称
				String[] strDisplayFields12 = {"sInstructionNo"};					//放大镜小窗口显示栏位对应的表格字段
				int intIndex12 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
				String strMainProperty12 = " ";									//放大镜的对应控件栏位属性
				String strMatchValue12="sInstructionNo";									////放大镜要模糊匹配的字段
				String strNextControls12 = "inputUserName";								//设置下一个焦点
				String strTitle12="到";
				String strFirstTD12=" ";
				String strSecondTD12=" ";
			
				OBMagnifier.showZoomCtrl(out,strMagnifierName12,strFormName12,strPrefix12,strMainNames12,strMainFields12,
					strReturnNames12,strReturnFields12, strReturnInitValues12, strReturnValues12,strDisplayNames12,strDisplayFields12,
					intIndex12,strMainProperty12,"getInstrCode()", strMatchValue12,strNextControls12,strTitle12, strFirstTD12, strSecondTD12);
				%>	
					
				</tr>
				<tr> 
				    <td width="0%" height="28">&nbsp;</td>
		         <%
		         long inputUserID=-1;
		         String userName="";
		         if (termInfo!=null)
		         {
		         	inputUserID=termInfo.getInputUserID();
		         	userName=termInfo.getInputUserName();
		         	if (inputUserID<0)
		         		userName="";
					System.out.println(userName+"^^^^^^^^^^^^^");
		         }
		       	String strMagnifierName9 = URLEncoder.encode("业务处理人");							//放大镜的名称
				String strFormName9 = "frm1";										//主页面表单名称
				String strPrefix9 ="";											////控件名称前缀
				String[] strMainNames9 = {"inputUserName"};	//放大镜回显栏位值列表
				String[] strMainFields9 = { "sName"};		//放大镜回显栏位对应的表格字段
				String[] strReturnNames9 = {"inputUserID"};							//放大镜返回值列表(隐含值)
				String[] strReturnFields9 = {"ID"};								//放大镜返回值(隐含值)对应的表格字段列表
				String   strReturnInitValues9=""+userName;						////放大镜回显栏位对应的初始值
				String[] strReturnValues9 = {""+inputUserID};								//放大镜返回值(隐含值)对应的初始值
				String[] strDisplayNames9 = {URLEncoder.encode("编号"),URLEncoder.encode("姓名")};				//放大镜小窗口显示的栏位名称
				String[] strDisplayFields9 = {"ID","sName"};					//放大镜小窗口显示栏位对应的表格字段
				int intIndex9 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
				String strMainProperty9 = " ";									//放大镜的对应控件栏位属性
				String strMatchValue9="ID";									////放大镜要模糊匹配的字段
				String strNextControls9 = "lClient";								//设置下一个焦点
				String strTitle9="业务处理人";
				String strFirstTD9=" colspan=2";
				String strSecondTD9=" ";
				boolean blnIsOptional9=false;
				OBMagnifier.showZoomCtrl(out,strMagnifierName9,strFormName9,strPrefix9,strMainNames9,strMainFields9,
					strReturnNames9,strReturnFields9, strReturnInitValues9, strReturnValues9,strDisplayNames9,strDisplayFields9,
					intIndex9,strMainProperty9,"getInputUser()", strMatchValue9,strNextControls9 ,strTitle9, strFirstTD9, strSecondTD9 );
				%>  
				<td colspan=2></td>
				</tr>
				<tr> 
					<td width="0%" height="47">&nbsp;</td>
					<td colspan="5" height="47"></td>
					<td align="right" width="0%" height="47"></td>
					<td colspan="5" align="right" width="45%" height="47">  
		            </td>
				</tr>
		 	</table>
		</td>
		</tr>
		</table>
		<br>
		      <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
		        <tr>
		          <td width="605">
		            <div align="right"></div>
		          </td>
		          <td width="63">
		            <div align="right">
					<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
		<%--			<input class=button1 id="submitt" name=add type=button value=" 提交 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> --%>
					</div>
		          </td>
		          <td width="62">
		            <div align="right">
					<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
					<input type="button" name="Submit1" value=" 查找 " class="button1" onclick="frmSubmit(frm1)">
					</div>
		          </td>
		        </tr>
		      </table>
		<br>
		 	<%
		 		//开始处理查询结果
		 		ArrayList aList=(ArrayList)request.getAttribute("Result");
		 		QuerySumInfo sumInfo=(QuerySumInfo)request.getAttribute("SumInfo");
		 		//System.out.println(""+aList.size());
		 	%>	 
			<table width="774" border="1" align="center" cellpadding="0" cellspacing="0" class=normal >
		          <thead>
		            <tr> 
		              <td  align="center" width="20%"><div align="center">指令类型</div></td>
		              <td  width="19%"><div align="center">指令号</div></td>
		              <td  width="19%"><div align="center">合同编号</div></td>
		              <td  width="13%"><div align="center">指令录入人</div></td>
		              <td  width="16%"><div align="center">指令录入时间</div></td>
		              <td width="13%"><div align="center">指令状态</div></td>
		            </tr>
		          </thead>
		          <%	
				if ( (aList!=null)&&(aList.size()>0) )
				{
					int count=aList.size();
					String instrTypeName="";
					String instrNo="";
					String contractCode="";
					String inputUserName="";
					String inputDateStr="";
					String statusStr="";
					recordCount=sumInfo.getAllRecord();
					for ( int i=0;i<count;i++ )
					{
						OBInstrInfo info=(OBInstrInfo)aList.get(i);
						instrTypeName=OBConstant.LoanInstrType.getName(info.getInstrTypeID());
						instrNo=info.getInstrNo();
						contractCode=info.getContractCode();
						inputUserName=info.getInputUserName();
						inputDateStr=DataFormat.getDateString(info.getInputDate());
						statusStr=OBConstant.LoanInstrStatus.getName(info.getStatusID());
						if (contractCode==null) contractCode="";
						//recordCount=info.getRecordCount();
						
			%>
		          <tr > 
		            <td  width="20%"><%=instrTypeName%> <div align="left"></div></td>
		            <td width="19%"><div align="center"><A href="javascript:confirmModify(<%=info.getInstrTypeID()%>,<%=info.getID()%>);" ><%=instrNo%></a></div></td>
		            <td width="19%"><%=contractCode%> <div align="center"></div></td>
		            <td  width="13%"><%=inputUserName!=null?inputUserName:""%> <div align="left"></div></td>
		            <td  width="16%"><%=inputDateStr%> <div align="center"></div></td>
		            <td  width="13%"><%=statusStr%> <div align="left"></div></td>
		          </tr>
		          <%		
					}
				}
				else
				{
			%>
		          <tr bordercolor="#999999" align="center"> 
		            <td width="20%"><div align="left"></div></td>
		            <td width="19%"><div align="left"></div></td>
		            <td width="19%"><div align="left"></div></td>
		            <td width="13%"><div align="left"></div></td>
		            <td width="16%"><div align="left"></div></td>
		            <td width="13%"><div align="left"></div></td>
		          </tr>
		          <% } %>
		        </table>
		
		<%
			//输出分页控件
			//OBHtml.showTurnPageControl(out,"frm1","../../loan/query/q003-c.jsp",recordCount,(termInfo==null?1:termInfo.getTxtPageNo()),Constant.PageControl.CODE_PAGELINECOUNT,(termInfo==null?99:termInfo.getLOrderParam()),(termInfo==null?1:termInfo.getLDesc()));
		%>
			</form>
		<script language="JavaScript">
		function confirmModify(typeid,id)
		{
			frm1.action="<%=Env.EBANK_URL%>loan/query/q001-c.jsp?instrTypeID="+typeid+"&parentID="+id;
			showSending();
			frm1.submit();
		}
		    function frmSubmit(frm)
			{
				if (!checkDate(frm.inputDateBegin,0,"指令录入时间开始"))
		  		{
			 		return false;
			  	}
		
				if (!checkDate(frm.inputDateEnd,0,"指令录入时间结束"))
		  		{
			 		return false;
		  		}  
			
				if (!CodeCompare(frm.inputDateBegin, frm.inputDateEnd, "起始日期和结束日期"))
					return(false);
		
				//frm.action = "q003-c.jsp";
				frm1.action="<%=Env.EBANK_URL%>loan/query/q003-c.jsp";
				frm.submit();
			}
		
		
		function CodeCompare(d_input1,d_input2,d_str)
		{
			if (d_input1.value.length>0 && d_input2.value.length>0)
			{
				if ( !CompareDateString(d_input1.value,d_input2.value))
				{
					alert(d_str+"不能由大至小。");
					form1.Submit12.focus();
					return (false);
				}
			}
			return true;
		}
			function getContractCode(code)
			{
				var sql="select distinct nContractID as id, sContractCode from OB_LoanInstrView  where sContractCode is not null and nClientID=<%=sessionMng.m_lClientID%> order by sContractCode";
				return sql;	
			}
			function getInstrCode(code)
			{
				var sql="select id, sInstructionNo from OB_LoanInstrView where nClientID=<%=sessionMng.m_lClientID%> or nConsignClientID=<%=sessionMng.m_lClientID%> order by id";
				return sql;	
			}
			function getInputUser(code)
			{
				var sql="select id, sName from OB_User where nClientID=<%=sessionMng.m_lClientID%> order by ID";
				return sql;	
			}
			
		
	 /* 页面焦点及回车控制 */
	 firstFocus(frm1.typeID);
	 //setSubmitFunction("frmSubmit(frm);");
	 setFormName("frm1");
	</script>
	<%
		OBHtml.showOBHomeEnd(out);
		}
	catch( IException ie )
	{
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>