<%--
/*
 * 程序名称：v002.jsp
 * 功能说明： 修改、添加审批各级数的人员的显示页面
 */
--%>

<!--Header start-->
<%
/* 设置页面属性、session、引入的程序包 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
                java.sql.*,
                java.rmi.RemoteException,
                com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.approval.dataentity.*,
                com.iss.itreasury.ebank.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
    response.setHeader("Cache-Control","no-stored");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
%>

<%
    Log.print("---------进入 v002.jsp 修改、添加审批各级数的人员的显示页面---------");

    try
    {
		//请求检测
		/** 权限检查 **/
		String strTableTitle = "审批流设置";
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTableTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		/* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);

		String strOfficeName = sessionMng.m_strOfficeName;
		String strUserName = sessionMng.m_strUserName; 
        ApprovalSettingInfo ASItemInfo = null;  //获得数据(人员)
        ApprovalSettingInfo ASInfo = null;//获得数据(参数)
        String strTmp = "";
		long lIsOver = 1;       //判断是否全部都审批完1：是。2：否
		long lExist = 0;                 //记录当前页面选了多少人
        if (request.getAttribute("Error") != null && !request.getAttribute("Error").equals(""))
        {
            strTmp = (String)request.getAttribute("Error");
            Log.print(";;"+strTmp);
%>
            <script language="javascript">alert("处理出错:"+'<%=strTmp%>');</script>
<%
        }
        if (request.getAttribute("ASItemInfo") != null)
        {
            ASItemInfo = (ApprovalSettingInfo) request.getAttribute("ASItemInfo");
        }
        if (request.getAttribute("ASInfo") != null)
        {
            ASInfo = (ApprovalSettingInfo) request.getAttribute("ASInfo");
        }
		System.out.println("hrere!!");
		if (request.getAttribute("isOver") != null)
		{
			strTmp = (String)request.getAttribute("isOver");
			lIsOver = Long.parseLong(strTmp);
		}

        if(ASInfo == null )
        {
            String strErrorMessage = "数据取得错误";//ErrorMessage.getErrorMessage("E005","审批流设置");
%>
            <p class="char_err"><%= strErrorMessage %></p>
<%      } %>

<%
        Log.print("sessionMng.m_lOfficeID"+  sessionMng.m_lOfficeID);
		
%>

<script language="JavaScript" src="/websys/js/Control.js"></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>

<safety:resources />

<form name="frm" method=post>
<input type="hidden" name="lApprovalID" value="<%=ASInfo.getID()%>">
<input type="hidden" name="sType" >
<input type="hidden" name="lLevel">
<input type="hidden" name="strUserID">
<input type="hidden" name="isOver" value="<%=lIsOver%>">
<input type="hidden" name="isClick" value=0>
<input type="hidden" name="lLevelOrigin" value=<%=ASInfo.getLevel()%>>
<input name="strAction" type="hidden">
<input name="strSuccessPageURL" type="hidden">
<input name="strFailPageURL" type="hidden">
<input name="fromPage" type="hidden">
<table width="66%" class="top">
  <tr> 
    <td class="FormTitle" height="29" colspan="2"> 
      <p><b><font size="3">审批流设置（第二页）</font></b></p>
    </td>
  </tr>
  <tr> 
    <td height="102" colspan="2"> 
      <table width="90%">
        <tr bordercolor="#FFFFFF">
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
        </tr>        
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>审批流名称：</td>   
          <td height="29" align="left">
              <input type="text" class=box readonly
                     value='<%=ASInfo.getName()%>' size="10">
          </td>
          <td height="29" >审批流描述：</td>
          <td height="29" align="left">
              <input type="text" class=box readonly value='<%=ASInfo.getDesc()%>' size="10">
          </td>
        </tr>		
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>是否可以越级：</td>   
          <%
          if(ASInfo.getIsPass()==1)
          {
          %>
          <td height="29" align="left"><input type="text" class=box readonly value='是' size="10"></td>
          <%
          }else
          {
          %>
          <td height="29" align="left"><input type="text" class=box readonly value='否' size="10"></td>
          <%
          }
          %>
          <td height="29" >当前状态：</td>
          <td height="29" align="left">
              <input type="text" class=box readonly
                     value='<%=Constant.ApprovalStatus.getName(ASInfo.getStatusID())%>' size="10">
          </td> 
        </tr>		
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
		  <td height="29" >最大审批级别：</td>
          <td height="29" align="left">
              <input type="text" class=box readonly value='<%=ASInfo.getTotalLevel()%>' size="10">
          </td>
          <td borderColor=#999999 height=32 vAlign=middle>最小审批级别：</td>   
          <td height="29" align="left">
		  <input type="text" class=box readonly value='<%=ASInfo.getLowLevel()%>' size="10">
		  </td>          
        </tr>
      </table>
    </td>
  </tr>

  <tr> 
    <td height="102" colspan="2"> 
      <table width="90%">
        <TR><td colspan = <%=ASInfo.getTotalLevel()%>>
         <HR></td>
          </TR>     
        <tr bordercolor="#FFFFFF">
        <%Log.print("level="+ASInfo.getLevel());%>
        <%for(int i=0;i<ASInfo.getTotalLevel();i++)
        {
        %>
            <td height="29"><input type="radio" name="radioLevel" value='<%=i+1%>' <%if(ASInfo.getLevel()==(i+1)) out.println("checked");%> onclick="changelevel(frm)">第<%=i+1%>级</td>
        <%
        }
        %>
        </tr>
      </table>
    </td>
  </tr>
<TR>    
<TD width="12%">
      <table width="100">
        <tr bordercolor="#FFFFFF">
          <td height="29" width='20'>&nbsp;</td>
          <td height="29">审批人员:</td>
        </tr>
      </table>
</TD>
<TD colSpan=3 height=20 >
<script language="javascript">
function getSelectData(obj)//得到select框内的所有的值
{
  var cData = "";

  for(i=0;i<obj.options.length;i++)
  {
      if (i>0)
      {
          cData=cData+",";
      }
      cData=cData+obj.options[i].value;
  }
  return cData;
}

function setSelectData(obj,strText,strValue)
{
    if(strValue.length>0)
    {
        var strSingleText,strSingleValue,oOption;
        while(strValue.indexOf(",")>0)
        {
            var nIndex;
            nIndex=strText.indexOf(",");
            strSingleText=strText.substring(0,nIndex);
            strText=strText.substring(nIndex+1,strText.length);
            nIndex=strValue.indexOf(",");
            strSingleValue=strValue.substring(0,nIndex);
            strValue=strValue.substring(nIndex+1,strValue.length);
            oOption = document.createElement("OPTION");
            oOption.text=strSingleText;
            oOption.value=strSingleValue;
            obj.add(oOption);
        }
        oOption = document.createElement("OPTION");
        oOption.text=strText;
        oOption.value=strValue;
        obj.add(oOption);
    }
}

//将obj中已经有的value的项从objForDelete里删除
function deleteWasteData(objForDelete,obj)
{
    for(i=0;i<obj.options.length;i++)
    {
        for(j=0;j<objForDelete.options.length;j++)
        {
            if (obj.options[i].value==objForDelete.options[j].value)
            {
                objForDelete.remove(j);
                break;
            }
        }
    }
}

function deleteRows(form)
{
    var fFirst=true;
    while(true)
    {
        for(i=0;i<form.lUserID.options.length;i++)
        {
            if(form.lUserID.options[i].selected==true)
            {
                fFirst=false;
                var oOption = document.createElement("OPTION");
                oOption.text=form.lUserID.options[i].text;
                oOption.value=form.lUserID.options[i].value;
                form.forselectaaaa.add(oOption);
                form.lUserID.remove(i);
                break;
            }
        }
        if (fFirst )
        {
            break;
        }
        else
        {
            fFirst=true;
        }
    }
	form.isClick.value=1;           //表示已经更改
    form.bsdfsa.value=getSelectData(form.lUserID);
}
//test
function deleteRowsExceptExist(form,k)
{
    var fFirst=true;
    while(true)
    {
        for(i=0;i<form.lUserID.options.length;i++)
        {
		/* 05-05-30
			if(form.lUserID.options[i].selected==true&&i<k)
			{
				alert("因为存在未审批完成的业务，所以不能对已存人员进行删除！");
				return false;
			}
		*/
            if(form.lUserID.options[i].selected==true)
            {
                fFirst=false;
                var oOption = document.createElement("OPTION");
                oOption.text=form.lUserID.options[i].text;
                oOption.value=form.lUserID.options[i].value;
                form.forselectaaaa.add(oOption);
                form.lUserID.remove(i);
                break;
            }
        }
        if (fFirst )
        {
            break;
        }
        else
        {
            fFirst=true;
        }
    }
	form.isClick.value=1;           //表示已经更改
    form.bsdfsa.value=getSelectData(form.lUserID);
}

function insertRows(form)
{
    var fFirst=true;
    while(true)
    {
        for(i=0;i<form.forselectaaaa.options.length;i++)
        {
            if(form.forselectaaaa.options[i].selected==true)
            {
                fFirst=false;
                var oOption = document.createElement("OPTION");
                oOption.text=form.forselectaaaa.options[i].text;
                oOption.value=form.forselectaaaa.options[i].value;
                form.lUserID.add(oOption);
                form.forselectaaaa.remove(i);
                break;
            }
        }
        if (fFirst)
        {
            break;
        }
        else
        {
            fFirst=true;
        }
    }
	form.isClick.value=1;           //表示已经更改
    form.bsdfsa.value=getSelectData(form.lUserID);
}
</script>
<%
    //得到账户类型的名称和标识
    String strUserNameNotSelect = "",
           strUserIDNotSelect = "";
    String strUserNameSelect = "",
           strUserIDSelect = "";
    //可供选择的用户集合
    Vector vNotSelect = (Vector)(ASItemInfo.getNotSelectUser());
    //已经选择的用户集合
    Vector vSelect=(ASItemInfo.getSelectUser());
    if (vNotSelect != null)
    {
        for(int i=0;i<vNotSelect.size();i++)
        {
            ApprovalUserInfo info = (ApprovalUserInfo)(vNotSelect.elementAt(i));
            if(!strUserNameNotSelect.equals(""))
            {
                strUserNameNotSelect=strUserNameNotSelect + ",";
                strUserIDNotSelect=strUserIDNotSelect + ",";
            }
            strUserNameNotSelect=strUserNameNotSelect + info.getUserName();
            strUserIDNotSelect=strUserIDNotSelect + info.getUserID();
        }
    }
    if (vSelect != null)
    {
		lExist = vSelect.size();
        for(int i=0;i<vSelect.size();i++)
        {
            ApprovalUserInfo info = (ApprovalUserInfo)(vSelect.elementAt(i));
            if(!strUserNameSelect.equals(""))
            {
                strUserNameSelect=strUserNameSelect + ",";
                strUserIDSelect=strUserIDSelect + ",";
            }
            strUserNameSelect=strUserNameSelect + info.getUserName();
            strUserIDSelect=strUserIDSelect + info.getUserID();
        }
    }
    Log.print("not="+vNotSelect.size()+strUserNameNotSelect+strUserIDNotSelect);
    Log.print("yes="+vSelect.size()+strUserNameSelect+strUserIDSelect);
%>
         <input type="hidden" value="" name="bsdfsa">
  <table width="38%" border="0">
    <tr> 
      <td width="161" rowspan="2" align="center">选择人员</td>
      <td width="35" >&nbsp; </td>
      <td width="166" rowspan="2" align="center">已选择人员</td>
      <td width="10" rowspan="2" align="center">&nbsp;</td>
    </tr>
    <tr> </tr>
    <tr> 
      <td width="161" rowspan="2"> 
         <select class='box' name="forselectaaaa"  size="10"  style="FONT-SIZE: 12px; WIDTH: 150px" multiple></select>
      </td>
      <td width="35" height="87"> 
         <input type="button" value=">>" align="top" name="button" onClick="javascript:insertRows(frm)">
       </td>
       <td width="166" rowspan="2"> 
         <select name="lUserID"  size="10"  style="FONT-SIZE: 12px; WIDTH: 150px" multiple></select>
       </td>
       <td width="10" rowspan="2">&nbsp;</td>
     </tr>
     <tr> 
       <td width="35"> 
	   	<%if(lIsOver==2)
		{%>
         <input type="button" value="&lt;&lt;" align="top" name="button2" onClick="javascript:deleteRowsExceptExist(frm,<%=lExist%>)">
<%      }
		else
		{%>
		<input type="button" value="&lt;&lt;" align="top" name="button2" onClick="javascript:deleteRows(frm)">
<%      }%>		
       </td>
         <td height=30 align="right" >
           <input type="button" name="save" value=" 保存 " class = button onclick="frmSave(frm)">
         </td>
       </tr>
     </table>  
   </tr>
<script language="javascript">

    setSelectData(frm.forselectaaaa,"<%=strUserNameNotSelect%>","<%=strUserIDNotSelect%>");
    setSelectData(frm.lUserID,"<%=strUserNameSelect%>","<%=strUserIDSelect%>");
    deleteWasteData(frm.forselectaaaa,frm.lUserID);

</script>
  <tr>
    <td height="102" colspan="2"> 
      <table width="500">
        <tr bordercolor="#FFFFFF">      
          <td width="70%">&nbsp;</td>  
          <td height=30 align="right" >            
             <input type="button" name="active" value=" 激活 " class = button onclick="frmActive(frm)">
           </td>               
           <td height=30 align="right" >            
            <input type="button" name="back" value=" 返回 " class = button onclick="frmBack(frm)">
           </td>     
        </tr>
      </table>
      <input type=hidden name="type">
</td></tr></table>
</form>

<script language="javascript">
function frmActive(frm)
{
    //只选择了一个radioBox,则radioBox不成为数组,radioBox[i]不可用
	if( isNaN(frm.radioLevel.length) == true )
	{
		frm.lLevel.value = 1;
	}
	else
	{
		for(var i=0;i<<%=ASInfo.getTotalLevel()%>;i++)
		{
			if(frm.radioLevel[i].status == true)
			{
				frm.lLevel.value = i+1;
				break;
			}
		}
	}
    frm.action = "../control/c003.jsp";
    frm.submit();
}
function frmSave(frm)
{
    var strUserID="";
    
    for(i=0;i<frm.lUserID.options.length;i++)
    {
        if(strUserID!="")
        {
            strUserID=strUserID + ",";
        }
        strUserID=strUserID + frm.lUserID.options[i].value;
    }
    frm.strUserID.value=strUserID;
    frm.sType.value="save";
	//只选择了一个radioBox,则radioBox不成为数组,radioBox[i]不可用
	if( isNaN(frm.radioLevel.length) == true )
    {
		frm.lLevel.value = 1;
	}
	else
	{
		for(var i=0;i<<%=ASInfo.getTotalLevel()%>;i++)
		{
			if(frm.radioLevel[i].status == true)
			{
				frm.lLevel.value = i+1;
				break;
			}
		}
	}
    frm.action ="../control/c002.jsp";
    frm.submit();
}
function frmBack(frm)
{
	frm.action = "../view/v014.jsp";
	frm.lApprovalID.value= "";
<%
	if(request.getAttribute("fromPage")!=null && !request.getAttribute("fromPage").equals(""))
	{
%>
	frm.action = "../control/c011.jsp";
	frm.lApprovalID.value= "<%=ASInfo.getID()%>";
	frm.strAction.value = "toModify";
	frm.strSuccessPageURL.value = "../view/v013.jsp";
	frm.strFailPageURL.value = "../view/v013.jsp";	
<%	} %>
    
    frm.submit();
}
function changelevel(frm)
{
    //只选择了一个radioBox,则radioBox不成为数组,radioBox[i]不可用
	if( isNaN(frm.radioLevel.length) == true )
    {
		frm.lLevel.value = 1;
	}
	else
	{
		for(var i=0;i<<%=ASInfo.getTotalLevel()%>;i++)
		{
			if(frm.radioLevel[i].status == true)
			{
				frm.lLevel.value = i+1;
				break;
			}
		}
	}
	if(frm.isClick.value==1)
	{
		if(confirm("是否保存?"))
		{
		    var strUserID="";
		    for(i=0;i<frm.lUserID.options.length;i++)
		    {
		        if(strUserID!="")
		        {
		            strUserID=strUserID + ",";
		        }
		        strUserID=strUserID + frm.lUserID.options[i].value;
		    }
		    frm.strUserID.value=strUserID;
		    frm.sType.value="save";
		    frm.lLevel.value = frm.lLevelOrigin.value;   //如果保存，则传以前的那个levelradio值
		    frm.action ="../control/c002.jsp";
		    frm.submit();
		}
		else
		{
			frm.action = "../control/c002.jsp";
    		frm.submit();
		}
	}
	else
	{
    	frm.action = "../control/c002.jsp";
    	frm.submit();
	}
}
/*function alertError(err)
{
    alert("发生错误："+err);
}*/

</script>

<script language="javascript">
	//只选择了一个radioBox,则radioBox不成为数组,radioBox[i]不可用
	if( isNaN(frm.radioLevel.length) == true )
	{
		firstFocus(document.frm.radioLevel);
    }
	else
	{
		firstFocus(document.frm.radioLevel[1]);
	}
	//setSubmitFunction("frmSave(frm)");
    setFormName("frm");

<%
	if(request.getAttribute("fromPage")!=null && !request.getAttribute("fromPage").equals(""))
	{
%>
	frm.fromPage.value = "c011";
<%
	}
%>
</script>
<%
        OBHtml.showOBHomeEnd(out);
    }
    catch(Exception e)
    {
        Log.print(e.getMessage());
    }
%>
<%@ include file="/common/SignValidate.inc" %>