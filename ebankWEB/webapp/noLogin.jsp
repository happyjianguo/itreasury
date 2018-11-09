<%@ page language="java" pageEncoding="gbk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>noLogin</title>

		<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

		<TABLE align=center border=0 class=top height=217 width="30%">
			<TBODY>
				<TR>
					<TD class=FormTitle height=2 width="100%">
						<B></B>
					</TD>
				</TR>
				<TR>
					<TD height="190" vAlign=bottom width="100%">
						<TABLE align=center height="187" width="97%">
							<TBODY>
								<TR>
									<TD height=40 vAlign=top width="96%">
										<TABLE align=center border=1 borderColor=#999999 height="177" cellpadding="0"
											cellspacing="0" width="99%">
											<TBODY>
												<TR borderColor=#D7DFE5 vAlign="center">
													<TD height="162" colspan="3" align="center">
														对不起，您没有登录。
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<SCRIPT language=JavaScript>
	    	window.parent.logout_when_session_timeout();
	    </SCRIPT>
	</body>
</html>