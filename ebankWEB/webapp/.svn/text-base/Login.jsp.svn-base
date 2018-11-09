<%@ page contentType="text/html;charset=gbk"%> 
<%String strContext = request.getContextPath();%>

<html>

<head>

<title>iTreasuryPro</title>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">

<SCRIPT language=JavaScript>

	function checkForm()

	{

		var frm=document.form1;

		if (frm.UserName.value==""){

			alert("请输入用户名");

			frm.UserName.focus();

			return false;

		}

		if (frm.Password.value==""){

			alert("请输入密码");

			frm.Password.focus();

			return false;

		}

		frm.submit();

	}

</script>

</head>



<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" align="center">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">

  <tr>

  <td><table width="100%" height="223" border="0" cellpadding="0" cellspacing="0">

      <tr>

        <td class="eLoginBackLeft">&nbsp;</td>

        <td width="326" align="center" class="eLoginBackMiddle"><table width="100%" height="223" border="0" cellpadding="0" cellspacing="0">

          <tr>

            <td height="75" align="center" valign="top"><img src="/itreasury/graphics/loginb_logo.gif"></td>

          </tr>

          <tr>

            <td align="center" valign="top"><table width="247" border="0" cellpadding="3" cellspacing="4">
                    <form name="form1" method="post" action="<%=strContext%>/Authenticate.jsp">
                      <tr> 
                        <td width="68" nowrap><strong><font color="#000000">用户账号：</font></strong></td>
                        <td width="122"> <input name="UserName" type="text" id="UserName" style="width:150px" size="23"> 
                        </td>
                      </tr>
                      <tr> 
                        <td nowrap><strong><font color="#000000">密　　码：</font></strong></td>
                        <td><input name="Password" type="password" id="Password" style="width:150px" size="23"> 
                        </td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td nowrap>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td>&nbsp;</td>
                        <td nowrap> <input type="hidden" name="currency" value="1"> 
                          <input type="submit" name="Submit1" value="提交" onclick="javascript:return checkForm()"> 
                          <input type="reset" name="Submit2" value="取消"> </td>
                      </tr>
                    </form>
                  </table></td>

          </tr>

          <tr>

            <td height="20"><table width="100%" border="0" cellspacing="0" cellpadding="0">

              <tr>

                <td height="16" align="center">&copy; 2002 - 2004 软通动力 版权所有.保留所有权利. </td>

              </tr>

            </table></td>

          </tr>

        </table>

        </td>

        <td class="eLoginBackRight">&nbsp;</td>

      </tr>

    </table></td>

  </tr>

</table>

<script language="javascript">

	document.form1.UserName.focus();

</script>

<!--

<script language="javascript">

	window.open("notice.html",'notice',' width=460,height=320,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0');

</script>

-->

</body>

</html>

