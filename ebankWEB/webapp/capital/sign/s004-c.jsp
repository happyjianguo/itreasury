<%--
/*
 * 程序名称：s004-c.jsp
 * 功能说明：交易指令批量复核控制页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-07
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //标题变量
    String strTitle = "[业务签认]";
    /* 初始化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
    TransInfo transinfo = new TransInfo();
    try {
        long lShowMenu = OBConstant.ShowMenu.YES;
        String strMenu = (String)request.getParameter("menu");
        if (strMenu != null && strMenu.equals("hidden")) {
            lShowMenu = OBConstant.ShowMenu.NO;
        }
        Log.print("=================strMenu="+strMenu);
        //查询条件信息，用于s005-v.jsp,返回时，给form写值
        QueryCapForm qcf = new QueryCapForm();
        //临时量
        String sTemp = null;
        // 网上银行交易类型,资金划拨由专门的类型
        sTemp = (String) request.getParameter("SelectType");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setTransType(Long.parseLong(sTemp));
            Log.print("SelectType:"+sTemp);
        }

		Log.print("**********第次循环*******qcf.getTransType()**********"+qcf.getTransType());
        // 提交日期-从
        sTemp = (String) request.getParameter("txtConfirmA");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStartSubmit(sTemp);
            Log.print("txtConfirmA:"+sTemp);
        }
        // 提交日期-到
        sTemp = (String) request.getParameter("txtConfirmB");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setEndSubmit(sTemp);
            Log.print("txtConfirmB:"+sTemp);
        }
        // 交易指令状态
        sTemp = (String) request.getParameter("SelectStatus");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStatus(Long.parseLong(sTemp));
            Log.print("SelectStatus:"+sTemp);
        }
        // 交易金额-最小值
        if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase(""))) {
            qcf.setMinAmount(Double.parseDouble(DataFormat.reverseFormatAmount(
                request.getParameter("txtMinAmount").trim())));
            Log.print("s004-c.jsp:qcf.dMinAmount="+qcf.getMinAmount());
        }
        // 交易金额-最大值
        if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase(""))) {//是否中油账户
            qcf.setMaxAmount(Double.parseDouble(DataFormat.reverseFormatAmount(
                request.getParameter("txtMaxAmount").trim())));
            Log.print("s004-c.jsp:qcf.dMaxAmount="+qcf.getMaxAmount());
        }
        // 执行日期-从
        sTemp = (String) request.getParameter("txtExecuteA");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setStartExe(sTemp);
            Log.print("txtExecuteA:"+sTemp);
        }
        // 执行日期-到
        sTemp = (String) request.getParameter("txtExecuteB");
        if (sTemp != null && sTemp.trim().length() > 0) {
            qcf.setEndExe(sTemp);
            Log.print("txtExecuteB:"+sTemp);
        }

          //定义接收Form信息的变量
        long lIsCheck = -1;//是否是签认
        sTemp = (String) request.getParameter("txtisCheck");
        if (sTemp != null && sTemp.trim().length() > 0) {
            lIsCheck = Long.parseLong(sTemp);
            Log.print("txtisCheck:"+sTemp);
        }
        String tempArr[] = new String[]{};
        String strCheckbox[] = request.getParameterValues("txtCheckbox");
        String strID[] = null;
        Timestamp dtModify[] = new Timestamp[]{};
		//String strTypeID[] = null;
        if (request.getParameter("txtID") != null && request.getParameter("txtID").trim().length() > 0) {
        	tempArr = request.getParameterValues("txtID");
        	if(tempArr.length>0){
				strID = new String[tempArr.length];
				if(tempArr[0].split("####").length>1){
					strCheckbox = new String[tempArr.length];
				}
				dtModify = new Timestamp[tempArr.length];
			}
        	//strTypeID=request.getParameterValues("txtTransType");
			//if(strTypeID == null)
			//	strTypeID[0] = request.getParameter("txtTransType");
        	for(int i = 0 ; i<tempArr.length ; i++){
				String[] arr = tempArr[i].split("####");
				if(arr.length==1){//单笔
					strID[0] = arr[0];
				}else{//批量
					String iCount = arr[0];
					String id = arr[1];
					Timestamp date = DataFormat.getDateTime(arr[3]);
					strID[i] = id;
					strCheckbox[i] = iCount;
					dtModify[i] = date;
				}
			}
        }
        Log.print("strTypeID的值是："+request.getParameter("txtTransType"));
        Log.print("是否是签认取消："+(lIsCheck==0?"是":"否"));

        //初始化EJB
        OBFinanceInstrHome financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
        OBFinanceInstr financeInstr = financeInstrHome.create();

        //调用ejb方法
        int iCount = -1;//接收数据的个数
        if (strCheckbox != null) {
            iCount = strCheckbox.length;
        } else {
            iCount = 1;
        }
        Log.print("iCount:"+iCount);
        String strTemp = new String();//返回信息
        Vector vcResult = new Vector(1);//返回信息
        //初始状态是未签认，进行签认操作
        if (lIsCheck == 1) {
            //复核
            for (int i=0; i<iCount; i++) {
                //int iTag;//数组下标
                long lID;//指令序号
				long typeID;//指令类型
                long lRet;//调用sign()的反回值
                if (strCheckbox != null) {
                    //iTag = Integer.parseInt(strCheckbox[i]);
                    lID = Long.parseLong(strID[i]);
					///typeID=Long.parseLong(strTypeID[iTag-1]);
                } else {
                    lID = Long.parseLong(strID[0]);
					//typeID=Long.parseLong(strTypeID[0]);
                }
                //调用Ejb方法
                //modify by xwhe 2009-05-22 网银日志的添加
		        try
		             {
		             	  Timestamp dtmodify=null;
		                  financeInfo = financeInstr.findByID( lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		                  financeInfo.setSignUserID(sessionMng.m_lUserID);//设置签认人
		                  if(request.getParameter(lID+"_dtmodify")!=null){//批量
							 dtmodify = DataFormat.getDateTime((String) request.getParameter(lID+"_dtmodify"));// 上次修改时间  add by zhanglei  date 2010.05.31
						  }
						  if(request.getParameter("dtmodify")!=null&&dtmodify==null){//单条
						 	 dtmodify = DataFormat.getDateTime((String) request.getParameter("dtmodify"));// 上次修改时间  add by zhanglei  date 2010.05.31
						  }
						  if(dtModify[i]!=null){
						     dtmodify = dtModify[i];
						  }
						  financeInfo.setDtModify(dtmodify);
                          lRet = financeInstr.sign(financeInfo);//将条件改为实体对象，以免参数过多。  add  by  zhanglei  2010.06.02
                          transinfo.setStatus(Constant.SUCCESSFUL);
                     }
		       catch(Exception ex)
			         {
			     
			              transinfo.setStatus(Constant.FAIL);
				          ex.printStackTrace();
				          throw new IException(ex.getMessage());
				    
			         }
			   finally
		             {	
			              if(transinfo.getStatus()!=-1)
			            {
				          TranslogBiz translofbiz= new TranslogBiz();
				          transinfo.setHostip(request.getRemoteAddr());
				          transinfo.setHostname(request.getRemoteHost());
				          transinfo.setTransType(financeInfo.getTransType());
				          transinfo.setActionType(Constant.TransLogActionType.sign);				
				          translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			         	 }
		        	  }
                //标题	
				if(lRet<0){
					strTemp = "交易签认失败！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为："+lID+"</b><br>";
					vcResult.add(strTemp);
				}else{
					strTemp ="交易已提交至"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+
					"！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为："+lID+"</b><br>";
					vcResult.add(strTemp);
					/*叶洪润：说这里要注释掉*/
					//strTemp = NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+
						//"将尽快处理阁下的指令!<br>";
					//vcResult.add(strTemp);
				}
                
            }
        } else {//初始状态为已签认，取消签认
            for(int i=0; i<iCount; i++) {
                //int iTag;//数组下标
                long lID;//指令序号
				long typeID;//指令类型
                long lRet;//调用cancelsign()的反回值
                if (strCheckbox != null) {
                    //iTag = Integer.parseInt(strCheckbox[i]);
                    lID = Long.parseLong(strID[i]);
					//typeID=Long.parseLong(strTypeID[iTag-1]);
                } else {
                    lID = Long.parseLong(strID[0]);
					//typeID=Long.parseLong(strTypeID[0]);
                }
                //调用Ejb方法
               //modify by xwhe 2009-05-22 网银日志的添加
		        try
		             {
		                  financeInfo = financeInstr.findByID( lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
                          lRet = financeInstr.cancelSign(lID, sessionMng.m_lUserID);                     
                          transinfo.setStatus(Constant.SUCCESSFUL);
                          
                     }
		       catch(Exception ex)
			         {
			     
			              transinfo.setStatus(Constant.FAIL);
				          ex.printStackTrace();
				          throw new IException(ex.getMessage());
				    
			         }
			   finally
		             {	
			              if(transinfo.getStatus()!=-1)
			            {
				          TranslogBiz translofbiz= new TranslogBiz();
				          transinfo.setHostip(request.getRemoteAddr());
				          transinfo.setHostname(request.getRemoteHost());
				          transinfo.setTransType(financeInfo.getTransType());
				          transinfo.setActionType(Constant.TransLogActionType.cancelSign);				
				          translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			         	 }
					}
				if(lRet<0){
					strTemp = "交易取消签认失败！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为："+lID+"</b><br>";
					vcResult.add(strTemp);
				}else{
					 //返回信息处理
					strTemp = "这笔业务现为已复核状态，需要签认后才能提交到"+
					NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为："+lID+"</b><br>";
					vcResult.add(strTemp);
				}
               
            }
        }

        //在请求中保存结果对象
        request.setAttribute("menu", strMenu);
        request.setAttribute("return",vcResult);
        request.setAttribute("FormValue",qcf);
        request.setAttribute("isCheck",request.getParameter("txtisCheck"));
        //获取上下文环境
        //ServletContext sc = getServletContext();
        //设置返回地址
        RequestDispatcher rd = null;
        
        
        //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	//pageControllerInfo.setUrl(strNextURL);
	//分发
	// rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        if (strCheckbox != null) {
        
        	pageControllerInfo.setUrl("/capital/sign/s005-v.jsp");
	//分发
	 rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        
            
        } else {
          	pageControllerInfo.setUrl("/capital/sign/s006-v.jsp");
	//分发
	 rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
        }
        //forward到结果页面
        rd.forward(request, response);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>
