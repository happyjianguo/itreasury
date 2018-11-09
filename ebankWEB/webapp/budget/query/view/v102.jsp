<%--
 ҳ������ ��v102.jsp
 ҳ�湦�� : �������� - Ԥ����Ŀ��λ��� ѡ����Ŀҳ��
 ��    �� ��liuyang
 ��    �� ��
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>

<!-- ������Ҫ����,��������.* -->
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
<%@ page import="com.iss.itreasury.budget.query.resultinfo.QBudgetResultInfo"%>
<%@ page import="com.iss.itreasury.budget.templet.dao.Budget_TempletDAO"%>





<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--������Ϣ����ҳ��,��ҳ����Ե������ڵ���ʽ�����Ѿ���׽�����쳣-->
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--����js�ļ�-->
<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />

<form name="frm001" method="post" action ="../control/c002.jsp">
<!-- ����ҳ����Ʋ��� -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">
<!-- ����ҵ���߼����� -->

<%
try
{
	//������
	/** Ȩ�޼�� **/
	String strTableTitle = "Ԥ����ϵ���� - Ԥ����Ŀ��λ�����ѯ";
	if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	
	/** ��ʾ�ļ�ͷ **/
	OBHtml.showOBHomeHead(out,sessionMng,"[Ԥ����Ŀ��λ�����ѯ]",Constant.YesOrNo.YES);



	/**
	 * ��������
	 */
	long lClientID				= sessionMng.m_lUserID;				//��ǰ�����û�ID
	long lCurrencyID				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long lOfficeID				= sessionMng.m_lOfficeID;			//���´�ID	
	/**
	* ����ҵ�����
	*/
%>
	<br>
<TABLE width="80%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>		�������� - Ԥ����Ŀ��λ�����ѯ</B></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE width="100%" border=0 align=center cellspacing="1" class=ItemList>
				<tr><td width="45%">&nbsp;Ԥ����Ŀ��</td>
                  <td width="10%" >&nbsp;</td>
                  <td width="45%" >&nbsp;&nbsp;<font color=#CC0000>*</font>��ѡ��</td>
                </tr>
                  <tr>

<td width="45%" height="15" valign="top">
<%QueryBudgetInfo objInfo=(QueryBudgetInfo)request.getAttribute("QueryBudgetInfo");%>
<input type=hidden name=budgetSystemID value="<%=objInfo.getBudgetSystemID()%>">
<input type=hidden name=clientID value="<%=objInfo.getClientID()%>">
<input type=hidden name=budgetPeriodID value="<%=objInfo.getBudgetPeriodID()%>">
<input type=hidden name=startDate value="<%=objInfo.getStartDate()%>">
<input type=hidden name=endDate value="<%=objInfo.getEndDate()%>">


					<select class='box' size="14" name="lineNameAssign" style="width:230"  multiple>
<%
	Collection coll=null;
	boolean checked=false;
	coll=(Collection)request.getAttribute("ItemColl");
	if(coll!=null){
		int i=0;
		for(Iterator iter =coll.iterator();iter.hasNext();){
			QBudgetResultInfo info=(QBudgetResultInfo)iter.next();
			i = i+1;
				long id = info.getItemID();
				String itemNo = info.getItemNo();
				long itemlevel =-1;
				String tmpValue = itemlevel + "$" + itemNo + "$" + id + "$" + i;
				out.println("<option value="+tmpValue+">"+info.getItemName()+"</option>");
		}
		out.println("</select>");
	}
	request.removeAttribute("ItemColl");
%>
</select></td>
                   <TD align="center" width="10%" class=""><input type=hidden name=SelPrivilege>
				<div align="center">
				<input name="buttonToRight" type="button" class="button" onclick="stot(frm001.lineNameAssign, frm001.lineNameNoAssign)" value="&nbsp;��>>&nbsp;">
				<br>
				<br>
				<input name="buttonToLeft" type="button" class="button" onclick="stot0(frm001.lineNameNoAssign, frm001.lineNameAssign)" value="&nbsp;<<��&nbsp;">
                </div>
		</TD>
		<TD align="center" width="45%" class="" colspan="2">
				<select name="lineNameNoAssign" size="14" style="width:230" multiple>
		         </select>&nbsp;
				</TD>
             </TABLE>
			 <TR>
         <TD width="100%" vAlign=bottom><TABLE align=center border=0 width="100%">
             <TBODY>
               <TR>
                   <td><DIV align=right><input name="Submit32" type="button" class="button" onClick="doSet()" value=" �� �� ">
				   <input name="Submit32" type="button" class="button" onClick="location.href='../view/v101.jsp';" value=" �� ��"></DIV></td>
               </TR>
             </TBODY>
           </TABLE>
         </TD></TR></TBODY></TABLE></form>
<iframe name="myiframe" src="../view/v002.jsp" style="display:none"></iframe>
<!-- Javascript���� -->
<script language="JavaScript">
//�趨form����
setFormName("form_m"); 
//�趨�س��Զ�ִ�ж���
//setSubmitFunction("doSet()");
//ҵ�����
function doSet()
{
	//�������������
	if(document.all.lineNameNoAssign.options.length==0){
		alert("Ԥ����Ŀ����Ϊ�գ���ѡ��");
		return false;
	}
	
	showSending();//��ʾ����ִ��
	setHiddenValue();	
	document.frm001.strSuccessPageURL.value="../view/v103.jsp";	//��������ɹ���������ҳ��
	document.frm001.strFailPageURL.value="../view/v103.jsp";		//����ʧ�ܺ�������ҳ��
	document.frm001.strAction.value="<%=Constant.Actions.MATCHSEARCH%>";	//�����������
	frm001.submit();
		
}
function doClose(){
	window.close(); 
}
function stot0(source, target)
{
	var len = source.options.length;
	for (var i = (len-1); i >=0; i--) {
		if (source.options[i].selected) {
			var idValue = source.options[i].value;
			var tmpLevel = getTmpLevel(idValue);			//����
			var privilegeNo = getPrivilegeNo(idValue);		//Ȩ�ޱ��
			var idValue2 ;
			var tmpLevel2;
			var privilegeNo2;
			var selprivilege=privilegeNo;
			var tmpInt=0;
			//��ѡ�е�Ϊ��Ȩ��,Ҫ���������е���Ȩ��ѡ���ٲ���
			//if (tmpLevel == "1"){
		  	for (var m = i;m < len;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//Ȩ�ޱ��
					if (privilegeNo2.indexOf(privilegeNo)==0){
						source.options[m].selected=true;
						tmpInt +=1;
					}
				}
			//��ѡ�е�Ϊ��Ȩ��,Ҫ�����еĸ�Ȩ��ѡ���ٲ���
				var totalUpPrivilegeNo="";
					if(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
						 var x=0;
						while(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
							privilegeNo = getUpPrivilegeNo(privilegeNo);
							 if(x<1){
							totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";
							 x++;}
							 else
							{
								 break;
							}
						}
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo;
					 
					}else{
						privilegeNo = getUpPrivilegeNo(privilegeNo);
						totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo;
					}
				if(!existRreast(selprivilege,source)){
				for (var m = 0;m <i;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//Ȩ�ޱ��
					 
					if (totalUpPrivilegeNo.indexOf("$"+privilegeNo2+"$")>-1  ){
						source.options[m].selected=true;
					}
				}
				}
				
			}
		}
	 sourceToTarget(source, target);
}
function existRreast(str,obj)
{
	 
	var len=obj.options.length;
	for(i=0;i<len;i++)
	{
		var idValue2 = obj.options[i].value;
	    var privilegeNo2 = getPrivilegeNo(idValue2);	
		if(obj.options[i].selected==false && privilegeNo2.substr(0,privilegeNo2.length-1)==str.substr(0,str.length-1))
			return true;
	}
	return false;
}

function stot(source, target)
{
	var len = source.options.length;
	for (var i = 0; i < len; i++) {
		if (source.options[i].selected) {
			var idValue = source.options[i].value;
			var tmpLevel = getTmpLevel(idValue);			//����
			var privilegeNo = getPrivilegeNo(idValue);		//Ȩ�ޱ��
			
			var idValue2 ;
			var tmpLevel2;
			var privilegeNo2;
			var tmpInt=0;
			//��ѡ�е�Ϊ��Ȩ��,Ҫ���������е���Ȩ��ѡ���ٲ���
			//if (tmpLevel == "1"){
				for (var m = i;m < len;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//Ȩ�ޱ��
					if (privilegeNo2.indexOf(privilegeNo)==0){
						source.options[m].selected=true;
						tmpInt +=1;
					}
				}
				
			//}else{//��ѡ�е�Ϊ��Ȩ��,Ҫ�����еĸ�Ȩ��ѡ���ٲ���*/
				var totalUpPrivilegeNo="";
				//if(panduan=="0"){
					if(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
						while(privilegeNo.lastIndexOf("_")!=privilegeNo.indexOf("_")){
							privilegeNo = getUpPrivilegeNo(privilegeNo);
							totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";

						}
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo+totalUpPrivilegeNo.substring(0,3) + "$";
					//alert(totalUpPrivilegeNo+"^^");
					}else{
						privilegeNo = getUpPrivilegeNo(privilegeNo);
						totalUpPrivilegeNo = totalUpPrivilegeNo + privilegeNo + "$";
						totalUpPrivilegeNo = "$" + totalUpPrivilegeNo;
						
							//alert(totalUpPrivilegeNo+"^^");
					}
					 
				//}else if(panduan=="1"){
				
				
				//}


				
				
				for (var m = 0;m < i;m++){
					idValue2 = source.options[m].value;
					privilegeNo2 = getPrivilegeNo(idValue2);		//Ȩ�ޱ��
					if (totalUpPrivilegeNo.indexOf("$"+privilegeNo2+"$")>-1){
						source.options[m].selected=true;
					}
				}
			}
		}
	//}
	sourceToTarget(source, target);
}

//ȡ���Ƿ�����Ȩ�ޣ�1�ǣ�0����
function getTmpLevel(str)
{
	var tmpArray = str.split("$");
	return tmpArray[0];
}
//ȡ��Ȩ�ޱ��
function getPrivilegeNo(str)
{
	var tmpArray = str.split("$");
	return tmpArray[1];
}
//ȡ��Ȩ��ID
function getId(str)
{
	var tmpArray = str.split("$");
	return tmpArray[2];
}
//ȡ������
function getOrder(str)
{
	var tmpArray = str.split("$");
	return tmpArray[3];
}
//ȡ���ϼ�Ȩ�ޱ��
function getUpPrivilegeNo(str)
{
	if (str.lastIndexOf("_")==1){
		return str;
	}else{
		return str.substring(0,str.lastIndexOf("_"));
	}
}

//��������hidden��ֵ
function setHiddenValue()
{
	frm001.SelPrivilege.value="";
	for(var i=0;i<frm001.lineNameNoAssign.options.length;i++)
	{
		var tmpValue = frm001.lineNameNoAssign.options[i].value;
		var tmpLevel = getTmpLevel(tmpValue);
		frm001.SelPrivilege.value = frm001.SelPrivilege.value + getId(tmpValue) + ",";
		
	}
	//alert(frm001.SelPrivilege.value+"id==")
}

//������õĸ��˵�
function clear(source)
{
	for (var i = 0; i < source.options.length; i++) 
	{
		if (source.options.length > 0)
		{
			var idValue = source.options[i].value;		//����
			var c=source.options.length-1;
				if (c==-1)
				{
					source.options[i] = null;
					if (i>0)
					i--;
					clear(source)
				}
			}
		}
}
//����Select����ת�Ƶ�������
function sourceToTarget(source, target) 
{
	var checkValue = false;
	for (var i = 0; i < source.options.length; i++) 
	{
		if (source.options[i].selected) 
		{
			var len = target.options.length;
			var idValue = source.options[i].value;			
			//�ж�Ȩ���Ƿ����
			for (var m = 0; m < len; m++) 
			{
				if (target.options[m].value == idValue) 
				{
					source.options[i] = null;
					break;
				}
			}
			if (m==len)
			{

				var tmpNum = new Number(getOrder(idValue));
				for (l = 0; l < target.options.length; l++) 
				{
					var tmpStr = target.options[l].value;
					var tmpNum2 = new Number(getOrder(tmpStr));
					if (tmpNum2 - tmpNum > 0)
					{
						break;
					}
				}
				l -= 1;
				
				//��Ҫ���Ȩ�޵ĵط�������������ú�
				for (var n = len; n > l+1; n--) {
					target.options[n] = new Option("", "");
					target.options[n].text = target.options[n-1].text;
					target.options[n].value = target.options[n-1].value;
				}

				//���ѡ�е�Ȩ��
				target.options[l+1] = new Option("", "");
				target.options[l+1].text = source.options[i].text;
				target.options[l+1].value = source.options[i].value;
				//ɾ��ԭȨ��
				//ÿɾ��һȨ�޶�Ҫ��i-1����Ϊ�����¼��㳤��
				source.options[i] = null;
					checkValue = true;
					i -= 1;
			}
		}
	}

	clear(source);
	for(var q=0;q < source.length;q++)
	{
		source.options[q].selected=false;
	}
	if (!checkValue) 
	{
	   alert("��ѡ��!");
	   return;
	}
}
</script>
<%	
	/**
	* ��ʵ�ļ�β
	*/
		OBHtml.showOBHomeEnd(out);
}
//�쳣����
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>