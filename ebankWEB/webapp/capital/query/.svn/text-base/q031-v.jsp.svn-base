<%--
 页面名称 ：v001.jsp
 页面功能 : 挂失业务处理资料填写浏览页面
 作    者 ：jinchen
 日    期 ：2004-11-23
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.Timestamp,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
			    com.iss.itreasury.util.Env,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.ebank.util.OBMagnifier,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.settlement.util.NameRef,
				com.iss.itreasury.settlement.util.SETTConstant,
				com.iss.itreasury.loan.util.LOANConstant,
				com.iss.itreasury.settlement.reportlossorfreeze.dataentity.*,
				com.iss.itreasury.settlement.bizdelegation.TransAbatementDelegation,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo
				"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	try{

	Log.print("=================进入页面reportlossorfreeze/view/v001.jsp=========");
	/** 权限检查 **/
	String strTableTitle = "业务处理 ?? 挂失";
	   
	sessionMng.clearPageLoader();
	//显示文件头

	OBHtml.showOBHomeHead(out, sessionMng, "挂失业务查询", OBConstant.ShowMenu.YES);

	/**
	 * 公共参数
	 */
	long lCurrencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long lOfficeId				    = sessionMng.m_lOfficeID;			//办事处ID
	//定期客户回显信息
	long lClientID = -1;
	String strClientNo = "";
	String strClientName = "";
	String startdate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	String enddate =  Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	//定期账户
	String strAccountNo = null;
	//活期账户回显信息		
	String strCurrentAccountClientName = null;
	long lCurrentAccountID = -1;
	String strCurrentAccountNo = null;


	String strTemp = "";
	ReportLossOrFreezeInfo reportLossOrFreezeInfo=new ReportLossOrFreezeInfo();
	String isBack="false";
	/**
	 * 页面控制类
	 */
	PageCtrlInfo pageInfo		= new PageCtrlInfo();
	pageInfo.convertRequestToDataEntity(request);
	
	/**
	 * 从request得到页面控制参数
	 */
	 if (!pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
	 {
		 if(request.getAttribute("isBack")!=null)
		 {
			isBack=String.valueOf(request.getAttribute("isBack"));
			if(isBack.equalsIgnoreCase("true"))
			 {
				reportLossOrFreezeInfo.convertRequestToDataEntity(request);
			 }
				
		 }
	 }
	
	/**
	 * 如果操作结果不是成功,从request中获得所有和dataentity字段绑定的数据,完成页面回显
	 */
	 if (pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
		reportLossOrFreezeInfo.convertRequestToDataEntity(request);

//显示前页传过的提示
String SucInfo=(String) request.getAttribute("SucInfo");
if(SucInfo!=null&&SucInfo.length()>0)
{
%>
<script language="JavaScript">
alert("<%=SucInfo%>");
</script>
<%}%>

<!--jsp:include page="/ShowMessage.jsp"/-->
<!--引入js文件-->
<script language="JavaScript" src="/websett/js/Check.js" ></script>
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<!--引入js文件-->
<form method="post" name="frm">
	<!--页面控制变量-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="strCtrlPageURL" value="">
	
	<!--重复请求控制-->
	<!--页面控制变量-->
	
	<!--隐含业务数据-->
	<input type="hidden" name="isChange" value="">
	<input type="hidden" name="hdnTransActionType" value="<%=SETTConstant.TransactionType.REPORTLOSS%>">
	<!--隐含业务数据-->

  <TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
        
		   <TR>
           <TD class=FormTitle height=2 width="100%"><B>	挂失业务查询</B></TD>
         </TR>
         <TR>
           <TD width="100%" height=40 vAlign=bottom>
             <fieldset><TABLE align=center border=0 width="100%">
               <TBODY>
                 <TR borderColor="#E8E8E8">
									<%	
			
				long lOfficeIDC = sessionMng.m_lOfficeID;
				long lCurrencyIDC = sessionMng.m_lCurrencyID;
				String strFormNameC = "frm";
				String strCtrlNameC = "lClientID";
				String strTitleC = "<font color=#FF0000>*</font> 客户编号";
				long lClientIDC = lClientID;
				String strClientNoC = strClientNo;
				String strFirstTDC = "";
				String SecondTDC = "";
				String[] sNextControlsClientC = {"startdate"};
				String strRtnClientNameCtrlC = "strClientName";
		
				OBMagnifier.createChildClientCtrl(
				out,
				lOfficeIDC,
				lCurrencyIDC,
				strFormNameC,
				strCtrlNameC,
				strTitleC,
				lClientIDC,
				strClientNoC,
				sessionMng.m_lClientID,
				strFirstTDC,
				SecondTDC,
				sNextControlsClientC,
				strRtnClientNameCtrlC);				
			 %>
									
                <TD height="20" width="16%"> 客户名称 
                  ： </TD>
									<TD height="20" width="39%">
										<textarea name="strClientName"  readonly rows="2" cols="30"><%=strClientName%></textarea>
									</TD>
								</TR>
                 <TR>
                   <TD >起始日期：</TD>
        	<TD>
          		<fs_c:calendar 
	          	    name="startdate"
		          	value="" 
		          	properties="nextfield ='enddate'" 
		          	size="20"/>
		        	  <script>
	          		$('#startdate').val('<%=startdate%>');
	          	</script>
            </TD>
			 <TD >终止日期：</TD>
          <TD>
          		<fs_c:calendar 
	          	    name="enddate"
		          	value="" 
		          	properties="nextfield ='submitfunction'" 
		          	size="20"/>
		         <script>
	          		$('#enddate').val('<%=enddate%>');
	          	</script>
            </TD>
               </TR>
               </TBODY>
             </TABLE>
             </fieldset>
         </TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
                       <input name="Submit32" type="button" class="button" onClick="doGoNext();" value=" 继续 ">
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
           </TD>
         </TR>


       </TBODY>
    </TABLE>
</form>	

<script language="JavaScript">
firstFocus(document.frm.lClientIDCtrl);
//setSubmitFunction("doGoNext()");
setFormName("frm"); 
</script>

<script language="javascript">
var isSubmited=false;
//js 函数定义
function doGoNext()
{
		if(isSubmited)	
		{
			alert("请求已提交，请稍候！");
			return;
		}
	   if(!validateFields(frm)) 
		{
			return false;
		}
		if (!validate(frm) )
		{
			return false;
		}
			   
		   frm.action = "q032-c.jsp";
		   frm.strSuccessPageURL.value="/capital/query/q033-v.jsp";
		   frm.strFailPageURL.value="/capital/query/q033-v.jsp";
		   //frm.strCtrlPageURL.value="/settlement/tran/reportlossorfreeze/control/c003.jsp";
		   frm.strAction.value = "<%=SETTConstant.Actions.MATCHSEARCH%>";//操作--匹配查找
		   showSending();
		   isSubmited=true;
		   frm.submit();
		
}
function doGoSearch()
{
	if(isSubmited)	
   {
   		alert("请求已提交，请稍候！");
		return;
   }
	frm.action="../control/c003.jsp";
	frm.strSuccessPageURL.value="../view/v002.jsp";
	frm.strFailPageURL.value="../view/v002.jsp";
	//frm.strCtrlPageURL.value="../control/c003.jsp";
	frm.strAction.value = "<%=SETTConstant.Actions.LINKSEARCH%>";//操作--链接查询
	showSending();
	isSubmited=true;
	frm.submit();
}
function allFields()
{
	
	this.aa=new Array("startdate","起始日期","date",0);
	this.ab=new Array("enddate","终止日期","date",0);
	
}

//日期的格式
function validateDate(sDate)
{ 
  var iaDate = new Array(3);
	iaDate = sDate.toString().split("-");
	if (iaDate[0].length != 4 || iaDate[1].length != 2 || iaDate[2].length != 2) 
	 {
	 alert("请输入正确的日期格式!");	
	 return false; 
	 }
	 return true;
} 

function validate(frm)
{
      
       var b = true;
	   if (!CompareDate(frm.startdate,frm.enddate,"执行日期不能由大到小！ "))

			return(false);
		if (frm.lClientID.value <=0)
		{
			alert("客户编号不能为空,请从放大镜中选择");
			return(false);
     	}

       return b;
}  
//编号比较
function CodeCompare(d_input1,d_input2,d_str)
{
	if (d_input1.value.length>0 && d_input2.value.length>0)
	{
		if (d_input1.value>d_input2.value)
		{
			alert(d_str+"不能由大至小。");
			d_input1.focus();
			return (false);
		}
	}
	return true;
}
























































































































































</script>
<%

	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(Exception e)
    {
		
		e.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>