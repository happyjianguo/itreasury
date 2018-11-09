<%
	/*
	*页面名称：p004-c.jsp
	*功能：提款申请
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
				com.iss.itreasury.settlement.util.*,			
				com.iss.itreasury.ebank.obpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obpaynotice.bizlogic.*"	
%>

<%@ include file="/common/common.jsp" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[提款申请]";
%>					
<%
  try
  {
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
	long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
	long lInputUserID = sessionMng.m_lUserID; 
	String paytype = "";
	
	//Log.print("*******进入页面--ebank/loan/discountapply/d011-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
//////////////////////////////////////////////////////////////////////////////////////

                //定义变量
                Timestamp tsSystem = Env.getSystemDate(); 
				Timestamp tsInput = tsSystem; 
                long lID = -1;  
                String strTmp  = ""; 
                long lContractID = -1; //合同号
                long lPayID = -1;      //放款通知单ID
                String strType = "";   //操作类型		
				String strAction = "";
				String code = "";
				String txtAction = "";
//////////////////////////////////////////////////////////////////////////////////////
               strTmp = (String)request.getAttribute("lContractID");
                if( strTmp != null && strTmp.length() > 0 )
                {
                     lContractID = Long.parseLong(strTmp.trim());
                }
                System.out.println("lContractID = "+lContractID);
                
				strTmp = (String)request.getAttribute("code");
                if( strTmp != null && strTmp.length() > 0 )
                {
                     code = strTmp.trim();
                }
                System.out.println("lContractID = "+lContractID);
                
                //放款通知单ID
                strTmp = (String)request.getAttribute("lPayID");
                if( strTmp != null && strTmp.length() > 0 )
                {
                     lPayID = Long.parseLong(strTmp.trim());
                }
				
				strTmp = (String)request.getAttribute("strAction");
				if( strTmp != null && strTmp.length() > 0 )
				{
					strAction = strTmp.trim();
				}
								
				strTmp = (String)request.getAttribute("txtAction");
				if( strTmp != null && strTmp.length() > 0 )
				{
					txtAction = strTmp.trim();
				}
              
///////////////////////////////////////////////////////////////////////////////////////
           OBPayNoticeHome PayNoticeHome = (OBPayNoticeHome)EJBObject.getEJBHome("OBPayNoticeHome");
		   OBPayNotice PayNotice = PayNoticeHome.create();
///////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////
						double dmon = 0.0;
                        
                        PayNoticeInfo lpninfo = new PayNoticeInfo();
                        
                        //合同ID
                        lpninfo.setContractID(lContractID);
                        
                        //放款通知单ID
                        lpninfo.setID(lPayID);                        
						
                        //批准金额                      
                        strTmp = (String)request.getAttribute("mamount");                        
                        if( strTmp != null && strTmp.length() > 0 )             
                        {
                                lpninfo.setAmount(DataFormat.parseNumber(strTmp.trim()));
                        }
                        
                        //贷款金额                      
                        strTmp = (String)request.getAttribute("dMoney");                        
                        if( strTmp != null && strTmp.length() > 0 )             
                        {
                                dmon = Double.parseDouble(strTmp.trim());
                        }
                        
                        if( lpninfo.getAmount() > dmon )
                        {
                        %>
                        <script language="javascript"> 
                             alert("批准金额不能大于未发放金额");
                        </script> 
                        <%
                        }
                        else
                        {
                        
                            //放款方式
                            strTmp = (String)request.getAttribute("ngranttypeid");                    
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setGrantTypeID(Long.parseLong(strTmp.trim()));
                            }
                            
                            //如果放款方式是 银行付款
                            if( lpninfo.getGrantTypeID() == LOANConstant.TransType.Bank )
                            {
                                //开户银行
                                strTmp = (String)request.getAttribute("naccountbankid");                    
                                if( strTmp != null && strTmp.length() > 0 )             
                                {
                                        lpninfo.setAccountBankID(Long.parseLong(strTmp.trim()));
                                }
                                
								
								
                                //收款方账号
                                strTmp = (String)request.getAttribute("txtAccount");                    
                                if( strTmp != null && strTmp.length() > 0 )             
                                {
                                        lpninfo.setReceiveAccount(strTmp.trim());
										System.out.println("sreceiveaccount="+strTmp.trim());
                                }
                                
                                //收款方名称
                                strTmp = (String)request.getAttribute("sreceiveclientname");                    
                                if( strTmp != null && strTmp.length() > 0 )             
                                {
                                        lpninfo.setReceiveClientName(strTmp.trim());
                                }
                                
                                //汇入地（省）
                                strTmp = (String)request.getAttribute("sremitinprovince");                    
                                if( strTmp != null && strTmp.length() > 0 )             
                                {
                                        lpninfo.setRemitinProvince(strTmp.trim());
                                }
                                
                                //汇入地（市）
                                strTmp = (String)request.getAttribute("sremitincity");                    
                                if( strTmp != null && strTmp.length() > 0 )             
                                {
                                        lpninfo.setRemitinCity(strTmp.trim());
                                }
                                
                                //汇入行名称
                                strTmp = (String)request.getAttribute("sremitbank");                    
                                if( strTmp != null && strTmp.length() > 0 )             
                                {
                                        lpninfo.setRemitBank(strTmp.trim());
                                }
                            }
                            
                            //如果放款方式是 活期账户
                            if( lpninfo.getGrantTypeID() == LOANConstant.TransType.CurrentAccount )
                            {
                                //账号
                                strTmp = (String)request.getAttribute("ngrantcurrentaccountid");                    
                                if( strTmp != null && strTmp.length() > 0 )             
                                {
                                        lpninfo.setGrantCurrentAccountID(Long.parseLong(strTmp.trim()));
                                }
                            }
                            
                            //放款日期
                            strTmp = (String)request.getAttribute("dtoutdate");                      
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setOutDate(DataFormat.getDateTime(strTmp.trim()));
                            }
                            
                            //借款单位账号
                            strTmp = (String)request.getAttribute("sloanaccount");                    
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setLoanAccount(strTmp.trim());
                            }
                            
                            //委托方账号
                            strTmp = (String)request.getAttribute("sconsignaccount");                      
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setConsignAccount(strTmp.trim());
                            }
                            
                            //基准利率ID
                            strTmp = (String)request.getAttribute("nbankinterestid");                         
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setBankInterestID(Long.parseLong(strTmp.trim()));
                                    System.out.println(" lpninfo.getBankInterestID()="+lpninfo.getBankInterestID());
                            }
                            
                            //基准利率值
                            strTmp = (String)request.getAttribute("mRate");                         
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setWTInterestRate(Double.parseDouble(strTmp.trim()));
                            }
                            
                            //手续费率
                            strTmp = (String)request.getAttribute("mcommissionrate");                        
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setCommissionRate(Double.parseDouble(strTmp.trim()));
                            }
                            
                            //担保费率
                            strTmp = (String)request.getAttribute("msuretyfeerate");                         
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setSuretyFeeRate(Double.parseDouble(strTmp.trim()));
                            }
                            
                            //贷款开始日期
                            strTmp = (String)request.getAttribute("dtstart");                    
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setStart(DataFormat.getDateTime(strTmp.trim()));
                            }
                            
                            //贷款结束日期
                            strTmp = (String)request.getAttribute("dtend");                      
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setEnd(DataFormat.getDateTime(strTmp.trim()));
                            }
                            
                            //公司领导
                            strTmp = (String)request.getAttribute("scompanyleader");                         
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setCompanyLeader(strTmp.trim());
                            }
                            
                            //部门领导
                            strTmp = (String)request.getAttribute("sdepartmentleader");                      
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setDepartmentLeader(strTmp.trim());
                            }
                            
                            //复核人
                            strTmp = (String)request.getAttribute("scheckperson");                   
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setCheckPerson(strTmp.trim());
                            }
                            
                            //经办人
                            strTmp = (String)request.getAttribute("shandlingperson");                        
                            if( strTmp != null && strTmp.length() > 0 )             
                            {
                                    lpninfo.setHandlingPerson(strTmp.trim());
                            }
		
                            
		strTmp = (String)request.getParameter("bankpay");
        if( strTmp != null && strTmp.length() > 0 )
        {	
             paytype = "bankpay";
        }
		strTmp = (String)request.getParameter("currentaccount");
        if( strTmp != null && strTmp.length() > 0 )
        {	
             paytype = "account";
        }
							
                            //录入人id
                            lpninfo.setInputUserID(sessionMng.m_lUserID);
                            
                            //下一审核人设为自己
                            //lpninfo.setNextCheckUserID(sessionMng.m_lUserID);
                            
                            //录入日期
                            lpninfo.setInputDate(Env.getSystemDate());
                            RequestDispatcher rd = null;
                            //提交操作
							System.out.println("@@@@@@@@@@@@@@@strAction = "+strAction);
							System.out.println("@@@@@@@@@@@@@@@txtAction = "+txtAction);

							if(strAction.equals("modify"))
							{
								lPayID = PayNotice.updatePayNotice(lpninfo);
								if(lPayID>0){
                           			System.out.println("update OBPayNoticeInfo Sucessed!!");
									request.setAttribute("lPayID",""+lPayID);
                        			request.setAttribute("strAction","view");
     
									ServletContext sc = getServletContext();
								     rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p002-c.jsp")));
								    rd.forward(request, response);
								}
                            }else if (strAction.equals("cancel"))
							{
								long lReturn = -1;
								lReturn = PayNotice.updateStatus(lPayID,OBConstant.LoanInstrStatus.CANCEL);
                        			ServletContext sc = getServletContext();
									if(txtAction.equals("modify"))
								     rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/query/q003-c.jsp")));
									else
									 rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p001-v.jsp")));
								    rd.forward(request, response);							
							}else if (strAction.equals("submitAct"))
							{
								long lReturn = -1;
								lReturn = PayNotice.updateStatus(lPayID,OBConstant.LoanInstrStatus.SUBMIT);
                        			ServletContext sc = getServletContext();
											request.setAttribute("code",""+code);
									if(txtAction.equals("modify"))
								     rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/query/q003-c.jsp")));
									else
								     rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p007-v.jsp")));
								    rd.forward(request, response);
							}
							else{
								lPayID = PayNotice.savePayNotice(lpninfo);
								if(lPayID>0){
                           			ServletContext sc = getServletContext();
									request.setAttribute("strAction","view");
									request.setAttribute("lPayID",""+lPayID);
									request.setAttribute("code",""+code);
									
								     rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p002-c.jsp")));
								    rd.forward(request, response);
								}
                        	}   
						}   
						%>
						
<%
}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>
