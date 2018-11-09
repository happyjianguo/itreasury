<%@page contentType="text/html;charset=gbk"%>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.obcontent.bizlogic.*,
				com.iss.itreasury.loan.contractcontent.bizlogic.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
				com.iss.itreasury.ebank.obsystem.bizlogic.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	
	try
	{
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
		
////////////////////////////////////////////////////////////////////////////////////////////////
		
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem ejb = home.create();
		ClientInfo clientinfo=null;
////////////////////////////////////////////////////////////////////*/

		//取参数变量
		long lClientID = -1; //
		String strClientCode = "";
		String strClientName = "";
		String strLicence = "";
		//long lContentID = -1;//文本的ID
		//long[] lClientContentID = {-1,-1,-1}; //客户文本的ID
		long[] lContentID = {-1,-1,-1}; //客户文本的ID
		String[] strContentYear = {"","",""};
		String PageName = "";
		long PageNo = 1;
		long lIsReadOnly = Constant.YesOrNo.YES;

		String strTemp = "";
		/*
		strTemp = (String)request.getAttribute("lContentID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lContentID[0] = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lContentID[0] = -1;
			}
		}//*/
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lClientID = Long.parseLong(strTemp.trim());
			}
			catch (Exception e)
			{
				lClientID = -1;
			}
		}
		if(lClientID > 0)
		{
			clientinfo = ejb.findClientByID(lClientID);
			strClientCode = DataFormat.formatString(clientinfo.getCode());		//客户编号
			strClientName = DataFormat.formatString(clientinfo.getName());		//客户名称
			strLicence = DataFormat.formatString(clientinfo.getLicenceCode());	//营业执照

			lContentID = clientinfo.getFINANCETJBID();
			strContentYear =clientinfo.getFINANCETJBYear();
			/*/strClientPageName = clientinfo.getFINANCETJBName();
			strTemp = request.getAttribute("lContentID1");
			if (strTemp != null && !strTemp.equals(""))
			{
				try
				{
					lContentID[0] = Long.parseLong(strTemp);
				}
				catch (Exception e)
				{
					lContentID[0] = -1;
				}
			}
			strTemp = request.getAttribute("lContentID2");
			if (strTemp != null && !strTemp.equals(""))
			{
				try
				{
					lContentID[1] = Long.parseLong(strTemp);
				}
				catch (Exception e)
				{
					lContentID[1] = -1;
				}
			}
			strTemp = request.getAttribute("lContentID3");
			if (strTemp != null && !strTemp.equals(""))
			{
				try
				{
					lContentID[2] = Long.parseLong(strTemp);
				}
				catch (Exception e)
				{
					lContentID[2] = -1;
				}
			}//*/

		}
		strTemp = (String)request.getAttribute("PageName");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				PageName = strTemp;
			}
			catch (Exception e)
			{
				PageName = "";
			}
		}
		
		strTemp = (String)request.getAttribute("PageNo");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				PageNo = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				PageNo = 1;
			}
		}
		strTemp = (String)request.getAttribute("lIsReadOnly");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lIsReadOnly = Long.parseLong(strTemp.trim());
				if(lIsReadOnly != Constant.YesOrNo.YES)
				{
					lIsReadOnly = Constant.YesOrNo.NO;
				}
			}
			catch (Exception e)
			{
				lIsReadOnly = Constant.YesOrNo.YES;
			}
		}

		ContractContentOperation operation = new ContractContentOperation();
		String sContent = "";
		String[] sContents = {"","",""};
		PageNo = 1;
		if(lClientID < 0 )
		{//*
			sContents[0] = operation.getContractContent(lContentID[0], PageNo);
			request.setAttribute("sContent1", sContents[0]);
			request.setAttribute("lContentID1", String.valueOf(lContentID[0]));
			request.setAttribute("lIsReadOnly", String.valueOf(lIsReadOnly));
			//*/
		}
		else
		{
			for(int i=lContentID.length-1;i>=0;i--)
			{
				try
				{
					sContents[i] = operation.getContractContent(lContentID[i], PageNo);
				}
				catch(IException e)
				{
					OBHtml.showExceptionMessage(out,sessionMng,e,"资产负债表","", Constant.RecordStatus.VALID); 
					//e.printStackTrace();
					out.flush();
					return;
				}
			}
			request.setAttribute("lClientID", String.valueOf(lClientID));
			request.setAttribute("strClientCode", strClientCode);
			request.setAttribute("strClientName", strClientName);
			request.setAttribute("strLicence", strLicence);

			request.setAttribute("sContent1", sContents[0]);
			request.setAttribute("sContentYear1", strContentYear[0]);
			request.setAttribute("lContentID1", String.valueOf(lContentID[0]));
			request.setAttribute("sContent2", sContents[1]);
			request.setAttribute("sContentYear2", strContentYear[1]);
			request.setAttribute("lContentID2", String.valueOf(lContentID[1]));
			request.setAttribute("sContent3", sContents[2]);
			request.setAttribute("sContentYear3", strContentYear[2]);
			request.setAttribute("lContentID3", String.valueOf(lContentID[2]));
			request.setAttribute("lIsReadOnly", String.valueOf(lIsReadOnly));
		}

		/* 获取上下文环境 */
		//ServletContext sc = getServletContext();
	
		/* 设置返回地址 */
		
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(PageName);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward到结果页面 */
		rd.forward(request, response);
		//return;
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"资产负债表","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return;
	}
%>
