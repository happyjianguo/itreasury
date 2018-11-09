<%
/**
 * 页面名称 ：e001-v.jsp
 * 页面功能 : 网上银行展期申请处理-新增
 * 作    者 ：杨帆
 * 日    期 ：2004-01-11
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : e003-c.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.obextendapply.bizlogic.*,
	com.iss.itreasury.ebank.obextendapply.dataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.bizdelegation.*,
	com.iss.itreasury.ebank.obrepayplan.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[展期申请]";
%>			
<%
	try
	{
		
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

    	// 定义变量
		long lPageCount = 1;
		long lPageNo = 1;
		long lOrderParam = 1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
		long lDescTmp = Constant.PageControl.CODE_ASCORDESC_DESC;//临时传输变量
		String strcontrol = "";//控制动作
		String strFirst = "yes";
			
		LoanTypeRelationDelegation loande=new LoanTypeRelationDelegation();
		long[] temp =loande.getAllSetLoanTypeID(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
       // long[] lArrayID={LOANConstant.LoanType.ZY,LOANConstant.LoanType.WT,LOANConstant.LoanType.ZGXE,LOANConstant.LoanType.YT,LOANConstant.LoanType.MFXD};
		LinkedList ll=new LinkedList();
		for(int i=0;i<temp.length;i++)
		{
		  ll.add( new Long(temp[i]));
		}
			
		for( int i=0;i<ll.size();i++)
		{  long t=((Long)ll.get(i)).longValue();
		  //System.out.println("######t="+t);
		 if(t==LOANConstant.LoanType.TX||t==LOANConstant.LoanType.ZTX||t==LOANConstant.LoanType.DB||t==LOANConstant.LoanType.OTHER)
		 { ll.remove(i);
		   i=-1;
		  }//System.out.println("@@@@@@@%%%%%%%%%%%%%%s"+(n-1));
		}
		 
		int m=0;
		Iterator it=ll.iterator();
		long[] lArrayID=new long[ll.size()];
		while(it.hasNext())
		{
		  lArrayID[m++]=((Long)it.next()).longValue();
				  
		}


		String sApplyCode1 = "";
		String sApplyCode2 = "";
		String sBorrowClientName = "";
		long lBorrowClientID = -1;
		long lLoanApplyID1 = -1;
		long lLoanApplyID2 = -1;
		long lLoanIntervalNum = -1;
		long lLoanTypeID = -1;
		String strTmp = "";
		String sTitle = "贷款";
		
		//获得查询结果集
		Collection c = null;
       	if (request.getAttribute("info") != null)
       	{
           	c = (Collection)request.getAttribute("info");
       	}
		//查询条件
		OBQueryContractInfo qinfo = new OBQueryContractInfo();
       	if (request.getAttribute("qinfo") != null)
       	{
           	qinfo = (OBQueryContractInfo)request.getAttribute("qinfo");
       	}
		if ( (String)request.getAttribute("txtlLoanTypeID")!=null ) {
    	   	lLoanTypeID = Long.valueOf((String)request.getAttribute("txtlLoanTypeID")).longValue();
        }
		// ---------------------------------------------------------------------------------end

		//获取strcontrol
		/*if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");
		//判断正序和反序
		if ( (String)request.getAttribute("nDesc")!=null )
        {
    	   	lDesc = Long.valueOf((String)request.getAttribute("nDesc")).longValue();
			if(lDesc == Constant.PageControl.CODE_ASCORDESC_ASC)
			{
				lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
				lDescTmp = Constant.PageControl.CODE_ASCORDESC_ASC;
			}
			else
			{
				lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				lDescTmp = Constant.PageControl.CODE_ASCORDESC_DESC;
			}
			
        }
		//判断排序条件
		if ( (String)request.getAttribute("nOrderParam")!=null )
        {
    	 	lOrderParam = Long.valueOf((String)request.getAttribute("nOrderParam")).longValue();
        }
		
		//显示页数
		if ( (String)request.getAttribute("nPageNo")!=null )
        {
    	   	lPageNo = Long.valueOf((String)request.getAttribute("nPageNo")).longValue();
        }

		// 获取查询参数
		if ( (String)request.getAttribute("txtIDStartCtrl")!=null ) {
    	   	sApplyCode1 = DataFormat.toChinese(String.valueOf((String)request.getAttribute("txtIDStartCtrl")));
        }
		if ( (String)request.getAttribute("txtIDEndCtrl")!=null ) {
    	   	sApplyCode2 = DataFormat.toChinese(String.valueOf((String)request.getAttribute("txtIDEndCtrl")));
        }
		if ( (String)request.getAttribute("txtIDStart")!=null ) {
    	   	lLoanApplyID1 = Long.valueOf((String)request.getAttribute("txtIDStart")).longValue();
        }
		if ( (String)request.getAttribute("txtIDEnd")!=null ) {
    	   	lLoanApplyID2 = Long.valueOf((String)request.getAttribute("txtIDEnd")).longValue();
        }
		if ( (String)request.getAttribute("txtsBorrowClientNameCtrl")!=null ) {
    	   	sBorrowClientName = DataFormat.toChinese(String.valueOf((String)request.getAttribute("txtsBorrowClientNameCtrl")));
        }
		if ( (String)request.getAttribute("txtsBorrowClientName")!=null ) {
    	   	lBorrowClientID = Long.valueOf((String)request.getAttribute("txtsBorrowClientName")).longValue();
        }

		if ( (String)request.getAttribute("txtlLoanTypeID")!=null ) {
    	   	lLoanTypeID = Long.valueOf((String)request.getAttribute("txtlLoanTypeID")).longValue();
        }*/

// ------------------------------------------------- lLoanTypeID

		//if ( strcontrol.equals("view") )
		//{
     	

%>

<%  OBHtml.showOBHomeHead(out,sessionMng,"展期新增查找",Constant.ShowMenu.YES); %>


<!--*************************************************************************************-->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<table width=740 border="0" class="top" height="265">
  <tr  class="tableHeader"> 
    <td class="FormTitle" height="29"><b><%= sTitle %>展期新增查找(合同)</b></td>
  </tr>
  <tr> 
    <td height="185">

 		<form name="form1" action="../extendapply/e003-c.jsp?control=view" method="post">
        <input type="hidden" name="first" value="no">

		<table width="740" border="0" align="left">
        <tr>   
          <td height="33" colspan="6">&nbsp;</td>
        </tr>
  
<!-- --------------------------------------------------------------------------------------------------------- -->
        <tr> 

          <td>贷款类型：</td>
          <td> 
		  <%LOANConstant.LoanType.showList(out,"txtlLoanTypeID",lLoanTypeID,false,true,"",lArrayID);%>
		</td>
 		  <td width="60%" colspan="3" >&nbsp;</td>  
        </tr>

<!-- --------------------------------------------------------------------------------------------------------- -->
        <tr> 
 
 <%
		

		String strMagnifierName = URLEncoder.encode("合同编号");						//放大镜的名称
		String strFormName = "form1";									//主页面表单名称
		String strMainNames1[] = {"txtIDStartCtrl"};					//放大镜回显栏位值列表
		String strMainFields1[] = { "sContractCode"};					//放大镜回显栏位对应的表格字段
		String strReturnNames1[] = {"txtIDStart"};						//放大镜返回值列表(隐含值)
		String strReturnFields1[] = {"ID"};								//放大镜返回值(隐含值)对应的表格字段列表
		String strReturnValues1[] = {(""+qinfo.getContractIDFrom())};	//放大镜返回值(隐含值)对应的初始值
		String strReturnInitValues = qinfo.getContractNameFrom();		//放大镜回显栏位对应的初始值
		String strDisplayNames1[]= {URLEncoder.encode("合同编号")};						//放大镜小窗口显示的栏位名称
		String strDisplayFields1[] = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty = "";									//放大镜的对应控件栏位属性
		String strMatchValue = "";										//（放大镜要模糊匹配的字段，如传入null或""则匹配第一个显栏对应的数据库字段）
    	String strNextControls = "txtIDEndCtrl";						//设置下一个焦点
    	String strTitle1 = "合同编号  由";								//栏位标题
    	String strFirstTD = "";											//第一个TD的属性
    	String strSecondTD = "";										//第2个TD的属性
		
		//调用产生放大镜的方法
		Magnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames1,strMainFields1,strReturnNames1,strReturnFields1,
		strReturnInitValues,strReturnValues1,strDisplayNames1,strDisplayFields1,
		intIndex,strMainProperty,"getContractCode()", strMatchValue,strNextControls,
    	strTitle1,strFirstTD,strSecondTD);

		strMagnifierName = URLEncoder.encode("合同编号");						//放大镜的名称
		strFormName = "form1";									//主页面表单名称
		String strMainNames2[] = {"txtIDEndCtrl"};					//放大镜回显栏位值列表
		String strMainFields2[] = { "sContractCode"};							//放大镜回显栏位对应的表格字段
		String strReturnNames2[] = {"txtIDEnd"};						//放大镜返回值列表(隐含值)
		String strReturnFields2[] = {"ID"};								//放大镜返回值(隐含值)对应的表格字段列表
		String strReturnInitValues2 = qinfo.getContractNameTo();						//放大镜回显栏位对应的初始值
		String strReturnValues2[] = {(""+qinfo.getContractIDTo())};				//放大镜返回值(隐含值)对应的初始值
		String strDisplayNames2[]= {URLEncoder.encode("合同编号")};				//放大镜小窗口显示的栏位名称
		String strDisplayFields2[] = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
		intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		strMainProperty = "";													//放大镜的对应控件栏位属性
		String strMatchValue2 = "";										//（放大镜要模糊匹配的字段，如传入null或""则匹配第一个显栏对应的数据库字段）
    	String strNextControls2 = "Submit1";						//设置下一个焦点
    	String strTitle2 = "&nbsp;&nbsp;&nbsp;到";									//栏位标题
    	String strFirstTD2 = "";											//第一个TD的属性
    	String strSecondTD2 = "";										//第2个TD的属性

		
		//调用产生放大镜的方法
		Magnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames2,strMainFields2,strReturnNames2,strReturnFields2,
		strReturnInitValues2,strReturnValues2,strDisplayNames2,strDisplayFields2,
		intIndex,strMainProperty,"getContractCode()",strMatchValue2,strNextControls2,
    	strTitle2,strFirstTD2,strSecondTD2);
%>	
 		  <td width="40%">&nbsp;</td>  
        </tr>
<!--TR>
 <td width="30" height="28">&nbsp;</td> 
<%
		

		/*strMagnifierName = URLEncoder.encode("借款单位");							//放大镜的名称
		strFormName = "form1";									//主页面表单名称
		String strMainNames3[] = {"txtsBorrowClientNameCtrl"};			//放大镜回显栏位值列表
		String strMainFields3[] = { "sName"};							//放大镜回显栏位对应的表格字段
		String strReturnNames3[] = {"txtsBorrowClientName"};			//放大镜返回值列表(隐含值)
		String strReturnFields3[] = {"ID"};								//放大镜返回值(隐含值)对应的表格字段列表
		String strReturnInitValues3 = qinfo.getClientName();						//放大镜回显栏位对应的初始值		String strReturnValues3[] = {(""+lBorrowClientID)};								//放大镜返回值(隐含值)对应的初始值
		String strReturnValues3[] = {(""+qinfo.getClientID())};								//放大镜返回值(隐含值)对应的初始值
		String strDisplayNames3[]= {URLEncoder.encode("客户编号"),URLEncoder.encode("客户名称")};				//放大镜小窗口显示的栏位名称
		String strDisplayFields3[] = {"sCode","sName"};					//放大镜小窗口显示栏位对应的表格字段
		intIndex = 1; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		strMainProperty = " size='52'";													//放大镜的对应控件栏位属性
		String strMatchValue3 = "sCode";										//（放大镜要模糊匹配的字段，如传入null或""则匹配第一个显栏对应的数据库字段）
    	String strNextControls3 = "Submit1";						//设置下一个焦点
    	String strTitle3 = URLEncoder.encode("借款单位");									//栏位标题
    	String strFirstTD3 = "";											//第一个TD的属性
    	String strSecondTD3 = " colspan='3' ";										//第2个TD的属性
		
		//调用产生放大镜的方法
		Magnifier.showZoomCtrl(out,strMagnifierName,strFormName,"",
		strMainNames3,strMainFields3,strReturnNames3,strReturnFields3,
		strReturnInitValues3,strReturnValues3,strDisplayNames3,strDisplayFields3,
		intIndex,strMainProperty,"getClient("+strMainNames3[0]+".value)",strMatchValue3,strNextControls3,
    	strTitle3,strFirstTD3,strSecondTD3);*/
%> 		  <td width="40%">&nbsp;</td>     
		</TR-->
        <tr> 
          <td colspan="6" align="right" height="47">  
            <input type="submit" name="Submit1" value=" 查 找 " class="button" onclick="frmSubmit(form1)">
          </td>
        </tr>
      </table></form>
    </td>
  </tr>
  <tr> 
    <td height="75" align="center"> 
      <hr>
      <table border="0" bordercolor="#999999" class="ItemList" width="740">

		<!--form name="frmOrder" action="e003-c.jsp?control=view" method="post" >
			  <input type="hidden" name="first" value="no">
			  <input type="hidden" name="nPageNo" value="<%=qinfo.getPageNo()%>">
			  <input type="hidden" name="nOrderParam" value="<%=qinfo.getOrderParam()%>">
			  <input type="hidden" name="nDesc" value="<%=qinfo.getDesc()%>">
			  <input type="hidden" name="txtIDStart" value="<%=qinfo.getContractIDFrom()%>">
			  <input type="hidden" name="txtIDEnd" value="<%=qinfo.getContractIDTo()%>">
			  <input type="hidden" name="txtIDStartCtrl" value="<%=qinfo.getContractNameFrom()%>">
			  <input type="hidden" name="txtIDEndCtrl" value="<%=qinfo.getContractNameTo()%>">
			  <input type="hidden" name="txtsBorrowClientName" value="<%=qinfo.getClientID()%>">
			  <input type="hidden" name="txtsBorrowClientNameCtrl" value="<%=qinfo.getClientName()%>">
			  <input type="hidden" name="txtlLoanTypeID" value="<%=qinfo.getTypeID()%>"-->

        <tr bordercolor="#999999" bgcolor="#CCCCCC" align="center" class="tableHeader"> 
          <td class="ItemTitle" width="15%"><a href="javascript:order(frmPage,1)">合同编号</a></td>
          <td class="ItemTitle" width="22%"><a href="javascript:order(frmPage,2)">借款单位</a></td>
          <td class="ItemTitle" width="18%"><a href="javascript:order(frmPage,3)">合同金额</a></td>
          <td class="ItemTitle" width="15%"><a href="javascript:order(frmPage,4)">贷款日期</a></td>
          <td class="ItemTitle" width="15%"><a href="javascript:order(frmPage,5)">期限</a></td>
          <td class="ItemTitle" width="15%"><a href="javascript:order(frmPage,6)">是否展期</a></td>
        </tr>
		<!--/form-->

<!-- ejb begin -->
<%  
	    OBExtendApplyInfo le_info = new OBExtendApplyInfo();
		//System.out.println(c);

	    if ( c != null ) {
			  Iterator iter = c.iterator();
			  while (iter.hasNext()) {
			  le_info = (OBExtendApplyInfo)iter.next();
			  lPageCount = le_info.getPageCount();

 String strHref = "../extendapply/e004-c.jsp?control=view&lContractID="+le_info.getContractID()+"&lStatusID="+le_info.getStatusID();
//String strHref ="";
%>
		  <tr bordercolor="#999999" align="center"> 
		   <td class="ItemBody" ><A href="<%=strHref%>"><%= le_info.getContractCode() %></a></td>
          <td class="ItemBody" ><%= (le_info.getClientName()==null?"":le_info.getClientName()) %></td>
          <td class="ItemBody" align="right"><%= DataFormat.formatListAmount(le_info.getLoanAmount()) %></td>
          <td class="ItemBody" ><%= DataFormat.formatDate(le_info.getLoanDate()) %></td>
          <td class="ItemBody"><%= le_info.getLoanIntervalNum() %>个月</td>
          <td class="ItemBody" ><%= (le_info.getIsExtend() == 1?"是":"否") %></td>
<%
		}  out.println("</tr>");
	  } else {
%>

		  <tr>
		  <td class="ItemBody" >&nbsp;</td>
		  <td class="ItemBody" >&nbsp;</td>
          <td class="ItemBody">&nbsp;</td>
          <td class="ItemBody" >&nbsp;</td>
          <td class="ItemBody" >&nbsp;</td>
          <td class="ItemBody" >&nbsp;</td>
		  </tr>
<% } %>
<!-- ejb end -->


        <tr bordercolor="#999999"> 
          <td height="2" class="SearchBar" colspan="9"> 

			<%
						if (qinfo.getPageNo() < 1)
						{
							qinfo.setPageNo(1) ;
						}
						//输出分页控件
						System.out.println("lPageCount========="+lPageCount);
						OBHtml.showTurnPageControl(out,"frmPage","../extendapply/e003-c.jsp",lPageCount,qinfo.getPageNo(),Constant.PageControl.CODE_PAGELINECOUNT,qinfo.getOrderParam(),qinfo.getDesc());
					%>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
</table>

<script language="javascript">
 firstFocus(form1.txtlLoanTypeID);
 //setSubmitFunction("frmSubmit(form1);");
 setFormName("form1");        
</script>
<script language="JavaScript">
    function frmSubmit(form)
	{

//		if (!CodeCompare(form.txtIDStartCtrl,form.txtIDEndCtrl,"申请编号"))
//			return(false);

		showSending();
		form.submit();
	}

function frmSubmit2(form)
{
    var lMax;
	lMax = parseInt(form.hdnPageCount.value);
	if (InputValid(form.nPageNo,1, "int", 1, 1, lMax,"页数")) 
		showSending();
	form.submit();
}

function go(i)
{
	frmPage.nPageNo.value=parseInt(i);
	frmSubmit2(frmPage);
}
function order(form,i)
{
	if(form.lDesc.value == <%=Constant.PageControl.CODE_ASCORDESC_DESC%>)
	{
		form.lDesc.value = <%=Constant.PageControl.CODE_ASCORDESC_ASC%>;
	}
	else
	{
		form.lDesc.value = <%=Constant.PageControl.CODE_ASCORDESC_DESC%>;
	}
    form.lOrderParam.value=parseInt(i);
	showSending();
	form.submit();
}
function NumValid(d_input,d_str)
{

   if ( d_input.value.length > 0 ) 
	{
	   if ( !isInt(d_input.value))
    {
		alert( d_str+ "只能是数字");
		d_input.focus();
		return (false);
    }

	if  (!(1<=d_input.value && d_input.value <= 99999))
	{
		alert(d_str+ "的值必须在 1 到 99999 之间。");
		d_input.focus();
		return (false);
	}
	}
		return true;
}
//编号比较
function CodeCompare(d_input1,d_input2,d_str)
{
	if (d_input1.value.length>0 && d_input2.value.length>0)
	{
		if (d_input1.value>d_input2.value)
		{
			alert(d_str+"不能由大至小。");
			form1.Submit1.focus();
			return (false);
		}
	}
	return true;
}
function getContractCode()
{
 	return " SELECT id,sContractCode FROM loan_contractForm "
		+ " where "
		+ "  NSTATUSID in ("
                  + <%=LOANConstant.ContractStatus.ACTIVE%> + ","
                  + <%=LOANConstant.ContractStatus.EXTEND%>
                  + ") and nTypeID != <%=LOANConstant.LoanType.TX%> "
				  + " and NBORROWCLIENTID=<%=sessionMng.m_lClientID%> "
				  + " and NCURRENCYID=<%=sessionMng.m_lCurrencyID%>"
				  + " order by sContractCode";
}
function getClient(client)
{
	var sql = "SELECT id,sCode,sName FROM client";
	
	if ( client != null && client != "")
	{
		sql += " WHERE sCode like  '%25"+client +"%25'";
	}
		return (sql+" order by sCode") ;
}
</script>

<%		
	OBHtml.showOBHomeEnd(out);
		//}
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"展期新增查找",1);
		ie.printStackTrace();
		out.flush();
		return; 
	}

%>
<%@ include file="/common/SignValidate.inc" %>