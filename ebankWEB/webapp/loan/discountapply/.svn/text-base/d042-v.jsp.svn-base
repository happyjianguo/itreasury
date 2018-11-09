<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.util.SessionMng"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.loan.util.LOANMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
                com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
    			com.iss.itreasury.loan.discount.bizlogic.*,
                com.iss.itreasury.loan.discount.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
                com.iss.itreasury.loan.contract.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
 //response.setHeader("Cache-Control","no-stored");
 //response.setHeader("Pragma","no-cache");
 //response.setDateHeader("Expires",0);
%>
<%!
public int IsInVector (Vector v,long lID)
{
    int i = 0;
    long lNum = -1;
	boolean flag = false;
	
	try{  	
		if (v != null && v.size() > 0)
		{
	 		while (i < v.size()) 
 	 		{
	      		Long lTmp = (Long) v.get(i);
				lNum = lTmp.longValue();
				if (lNum == lID)
				{					
					flag = true;
					break;
				}
				i++;
			}
		}
	}
	catch(Exception e) 
	{
    	System.out.println(e.toString());
    }
    
    return ((flag == true) ? i : -1);    
}

	/**
	 * 查询一个贴现申请下的贴现票据并计息，操作DiscountBill表
	 * @param lContractID 贴现合同标识
	 * @param lDiscountCredenceID 贴现凭证标识
	 * @return 返回贴现票据的列表
	 */
	public Collection findBillInterestByID(long lDiscountID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
		throws RemoteException,IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;

		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;

		double dDiscountRate = 0; //利率
		Timestamp tsDiscountDate = null; //计息日
		double dExamineAmount = 0; //票面金额
		double dDiscountAccrual = 0; //利息
		double dCheckAmount = 0; //实付金额

		Timestamp tsEnd = null; //贴现日期
		String strEnd = ""; //贴现日期
		int nDays = 0; //实际贴现天数
		double dAmount = 0; //票据金额
		double dAccrual = 0; //贴现利息
		double dRealAmount = 0; //实付贴现金额
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额

		try
		{
			con = Database.getConnection();

			Log.print("======进入贴现计息(findBillInterestByID)方法======");

			Log.print("申请标示：" + lDiscountID);

			if (lDiscountID > 0)
			{
				strSQL = " select a.* from Loan_LoanForm a where a.ID=? ";

				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lDiscountID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dExamineAmount = rs.getDouble("mExamineAmount"); 	//票面金额
					dRealAmount = rs.getDouble("mCheckAmount"); 		//实付贴现额
					dAccrual = dExamineAmount - dRealAmount; 			//贴现利息
					dDiscountRate = rs.getDouble("mDiscountRate"); 		//贴现利率
					tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				
				strSQL = " from Loan_DiscountFormBill where nStatusID=" + Constant.RecordStatus.VALID + " and nLoanID=" + lDiscountID;

			}
			
			Log.print("======开始查询票据总数和总金额======");

			//计算记录总数
			strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) ";
			//strSQL = " from DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountApplyID=" + lDiscountApplyID;

			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
				dTotalRealAmount = rs.getDouble(3);
				dTotalAccrual = dTotalAmount - dTotalRealAmount;
			}
			Log.print("==============");
			Log.print("票据总张数=" + lRecordCount);
			Log.print("票据总金额=" + dTotalAmount);
			Log.print("票据总利息=" + dTotalAccrual);
			Log.print("总实付金额=" + dTotalRealAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			Log.print("======结束查询票据总数和总金额======");

			lPageCount = lRecordCount / lPageLineCount;

			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) lOrderParam)
			{
				case 1 :
					strSQL += " order by nSerialNo ";
					break;
				case 2 :
					strSQL += " order by sUserName ";
					break;
				case 3 :
					strSQL += " order by sBank ";
					break;
				case 4 :
					strSQL += " order by nIsLocal ";
					break;
				case 5 :
					strSQL += " order by dtCreate ";
					break;
				case 6 :
					strSQL += " order by dtEnd ";
					break;
				case 7 :
					strSQL += " order by nAddDays ";
					break;
				case 8 :
					strSQL += " order by sCode ";
					break;
				case 9 :
					strSQL += " order by mAmount ";
					break;
				case 10 :
					strSQL += " order by nAcceptPOTypeID";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

			Log.print("翻页查询开始");
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();

				dbill.setDiscountApplyID(lDiscountID); //贴现合同标示
				dbill.setDiscountDate(tsDiscountDate); //计息日
				dbill.setDiscountRate(dDiscountRate); //计息利率

				//dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //凭证标示
				//dbill.OB_lDiscountCredenceID = rs.getLong("ob_nDiscountCredenceID");
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手				
				//////////////////////////////////////////////////////////

				//dAmount = rs.getDouble("mAmount"); //汇票金额

				tsEnd = rs.getTimestamp("dtEnd");
				nDays = 0;
				if (tsEnd != null && tsDiscountDate != null)
				{
					strEnd = tsEnd.toString();
					tsEnd =
						new java.sql.Timestamp(
							new Integer(strEnd.substring(0, 4)).intValue() - 1900,
							new Integer(strEnd.substring(5, 7)).intValue() - 1,
							new Integer(strEnd.substring(8, 10)).intValue(),
							0,
							0,
							0,
							0);
					nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
				}
				if (nDays >= 0)
				{
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO)
					{
						nDays = nDays + 3;
					}
					//dAccrual = dAmount * (dDiscountRate / 360) * 0.01 * nDays;
					//dAccrual = DataFormat.formatDouble(dAccrual, 2);
					//dRealAmount = dAmount - dAccrual;
				}
				/*
				Log.print("========================");
				Log.print("贴现天数=" + nDays);
				Log.print("汇票金额=" + dAmount);
				Log.print("汇票利息=" + dAccrual);
				Log.print("实付金额=" + dRealAmount);
				Log.print("========================");
				dTotalAccrual = DataFormat.formatDouble(dTotalAccrual, 2) + DataFormat.formatDouble(dAccrual, 2);
				dTotalRealAmount = DataFormat.formatDouble(dTotalRealAmount, 2) + DataFormat.formatDouble(dRealAmount, 2);
				*/
				dbill.setDays(nDays); //实际贴现天数
				dbill.setRealAmount(rs.getDouble("mCheckAmount"));//实付贴现金额
				dbill.setAccrual(rs.getDouble("mAmount")-rs.getDouble("mCheckAmount"));//贴现利息
				
				//////////////////////////////////////////////////////////

				dbill.setCount(lRecordCount);
				//dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
				//dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));
				dbill.setTotalAmount(dTotalAmount);
				dbill.setTotalAccrual(dTotalAccrual);
				dbill.setTotalRealAmount(dTotalRealAmount);
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalAmount, 2) - DataFormat.formatDouble(dTotalAccrual, 2));
				v.add(dbill);
			}
			Log.print("翻页查询结束");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;

		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
				throw new IException("Gen_E001");
			}
		}

		Log.print("======退出贴现计息(findBillInterestByID)方法======");

		return (v.size() > 0 ? v : null);

	}
%>
<%
//////////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
		//判断是否登录//CODE_COMMONMESSAGE_LOGIN=1
		if( !sessionMng.isLogin() )
		{	
			LOANHTML.showMessage(out,sessionMng,request,response,"登录",Constant.RecordStatus.INVALID,"Gen_E002");
			LOANHTML.showHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			LOANHTML.showMessage(out,sessionMng,request,response,"登录",Constant.RecordStatus.INVALID,"Gen_E003");
			LOANHTML.showHomeEnd(out);
			out.flush();
			return;
		}

		//定义变量，获取请求参数

		String strTmp = "";
		String strControl = "";
		String backurl = "";
		String backpage = "";

		int tmpInt = 0;
		int lastPageNum = 0;
		int delNum = 0;
		
		long lBillID = -1;
		long[] lBillIDArray = new long[1000]; 
		String strBillID = "";
		Vector v = null;

		long lContractID = -1;
		long lCredenceID = -1;
		long lDiscountID = -1;
		
		long txtContract = -1;			//贴现标示
		String txtContractCode = "";	//贴现申请编号

		double llv = 0;					//贴现利率
		Timestamp rq = null;			//贴现日期
		Timestamp tsEnd = null;			//贴现日期
		String strEnd = "";				//贴现日期
		int nDays = 0;					//实际贴现天数
		double dAccrual = 0;			//贴现利息
		double dRealAmount = 0;			//实付贴现金额
		long lCount = 0;				//票据总笔数
		double dTotalAccrual = 0;		//汇总贴现利息
		double dTotalRealAmount = 0;	//汇总实付贴现金额
		double dTotalAmount = 0;

		String strApplyClient = "";

		Collection temp = null;

		DiscountHome  DiscountHome = null;
		Discount      Discount = null;
		DiscountLoanInfo		dli = new DiscountLoanInfo ();
		DiscountCredenceInfo	dci = new DiscountCredenceInfo ();
		DiscountBillInfo		dbi = new DiscountBillInfo ();
			
		DiscountHome = (DiscountHome)EJBObject.getEJBHome("DiscountHome");
		Discount = DiscountHome.create();

		ContractHome		ContractHome = null;
        Contract			Contract = null;
		ContractInfo		cinfo = new ContractInfo ();
		
        ContractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
        Contract = ContractHome.create();

		LoanCommonSettingHome  loanCommonSettingHome = null;
        LoanCommonSetting      loanCommonSetting = null;
		ClientInfo clientinfo = new ClientInfo();

		loanCommonSettingHome = (LoanCommonSettingHome)EJBObject.getEJBHome("LoanCommonSettingHome");
        loanCommonSetting = loanCommonSettingHome.create();
		
		// 分页参数
		long lPageCount = 1;                   //几页
		long lPageNo = 1;                      //第几页
		long lOrderParam = 1;                  //根据什么排序
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //正序还是反序

		//判断正序和反序
		strTmp = (String)request.getAttribute("lDesc");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lDesc = Long.parseLong(strTmp.trim());
		}
		
		//判断排序条件
		strTmp = (String)request.getAttribute("lOrderParam");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lOrderParam = Long.parseLong(strTmp.trim());
		}

		//显示页数
		strTmp = (String)request.getAttribute("lPageNo");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lPageNo = Long.parseLong(strTmp.trim());
		}

///////control////////////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}
		
		strTmp = (String)request.getAttribute("backurl");
        if(strTmp != null && strTmp.length() > 0)
        {
             backurl = strTmp.trim();
        }

		strTmp = (String)request.getAttribute("backpage");
        if(strTmp != null && strTmp.length() > 0)
        {
             backpage = strTmp.trim();
        }

		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("lDiscountID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lDiscountID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("strBillID");
        if(strTmp != null && strTmp.length() > 0)
        {
             strBillID = strTmp.trim();
			 /**
			  * 将一个用","分开的串分解为一个Vector的数组
			  * @param strParam 需要拆分的参数
			  * @return 返回一个Vector，里面是Long型
			  */
			 
			 v = DataFormat.changeStringGroup(strBillID);
			 /*
			 if( (v != null) && (v.size() > 0) )
			 {
				Iterator it = v.iterator();
                while (it.hasNext() )
                {
					lBillID = ( long ) it.next();
				}
			 }
			 */
		}

////////view//////////////////////////////////////////////////////////////////////////
		if (strControl.equals("view"))
		{
			if (lCredenceID > 0)		
            {
				temp = Discount.findBillInterestByID(-1,lCredenceID,1000,1,lOrderParam,lDesc);
				dci = Discount.findDiscountCredenceByID (lCredenceID);
				strApplyClient = dci.getApplyClientName();
			}
			else if (lContractID > 0)		
            {
				temp = Discount.findBillInterestByID(lContractID,-1,1000,1,lOrderParam,lDesc);
				cinfo = Contract.findByID(lContractID);
				clientinfo = loanCommonSetting.findClientByID(cinfo.getBorrowClientID());
				strApplyClient = DataFormat.formatString(clientinfo.getName());
			}
			else if (lDiscountID > 0)		
            {
				temp = findBillInterestByID(lDiscountID,1000,1,lOrderParam,lDesc);
				dli = Discount.findDiscountByID(lDiscountID);
				clientinfo = loanCommonSetting.findClientByID(dli.getApplyClientID());
				strApplyClient = DataFormat.formatString(clientinfo.getName());
			}
		}

//////////////////////////////////////////////////////////////////////////////////////

		//如果是弹出窗口
		long lShowMenu = Constant.YesOrNo.YES;
  		strTmp = (String)request.getAttribute("isSM");
		if(strTmp != null && strTmp.length() > 0)
		{
			lShowMenu = Long.parseLong(strTmp);
		}

		LOANHTML.showHomeHead(out,sessionMng,"贴现票据计息明细表",lShowMenu);

%>

<script language="JavaScript" src="/webloan/js/Control.js"></script>
<script language="JavaScript" src="/webloan/js/Check.js"></script>
<safety:resources />
<form name="frm">

<TABLE border=0 class=top height=133 width="90%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贴现票据计息明细表</B></TD></TR>
  <TR>
    <TD height=29>借款单位：<%=strApplyClient%></TD></TR>
  <TR>
    <TD height=195>
      <TABLE cellPadding=0 height=112 width="99%">
        <TBODY>    
        <TR>          
          <TD colSpan=3 height=170 vAlign=top>
            <TABLE border=0 borderColor=#999999 class=ItemList height=73 
            width=900>
              <TBODY>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
				<TD class=ItemTitle height=20 width="5%"><A href="javascript:go('1');">序列号</A></TD>
                <TD class=ItemTitle height=20 width="8%"><A href="javascript:go('7');">汇票号码</A></TD>
                <TD class=ItemTitle height=20 width="15%"><A href="javascript:go('8');">汇票金额</A></TD>
                <TD class=ItemTitle height=20 width="10%">贴现日</TD>
                <TD class=ItemTitle height=20 width="10%"><A href="javascript:go('6');">到期日</A></TD>
				<TD class=ItemTitle height=20 width="8%"><A href="javascript:go('7');">节假日增加天数</A></TD>
                <TD class=ItemTitle height=20 width="8%">实际贴现天数</TD>
                <TD class=ItemTitle height=20 width="8%">贴现利率</TD>
			    <TD class=ItemTitle height=20 width="13%">贴现利息</TD>
                <TD class=ItemTitle height=20 width="15%">实付贴现金额</TD>
			  </TR>

<% /************************* Information Display********************************/ %>	
	<%	
                if( (temp != null) && (temp.size() > 0) )
                {
                    Iterator it = temp.iterator();
                    while (it.hasNext() )
					{
						DiscountBillInfo info = ( DiscountBillInfo ) it.next();                     
						lPageCount = info.getCount() / Constant.PageControl.CODE_PAGELINECOUNT;
						//lPageCount = info.lCount / 1;
            
						//if ((info.lCount % 1) != 0)
						if ((info.getCount() % Constant.PageControl.CODE_PAGELINECOUNT) != 0)
						{
							lPageCount ++;
						}
/*	
//单张票据的计息在EJB中处理
					   strEnd = info.tsEnd.toString();
					   tsEnd = new java.sql.Timestamp(new Integer(strEnd.substring(0,4)).intValue()-1900,new Integer(strEnd.substring(5,7)).intValue()-1,new Integer(strEnd.substring(8,10)).intValue(),0,0,0,0);

					   nDays = (int)java.lang.Math.ceil((tsEnd.getTime() - rq.getTime()) / 86400000) + info.nAddDays;
					   if (nDays >= 0)
					   {
							if (info.nIsBeijing == 0) nDays = nDays + 3;
							dAccrual = info.dAmount * (llv / Common.getDiscountDays(-1)) * 0.01 * nDays;
							dAccrual = DataFormat.formatDouble(dAccrual,2);
					        dRealAmount = info.dAmount - dAccrual;
					   } 
					   else
					   {
							nDays = 0;
							dAccrual = 0;
							dRealAmount = 0;
					   }
*/
						lCount = info.getCount();
						dTotalAccrual = info.getTotalAccrual();
						dTotalRealAmount = info.getTotalRealAmount();
						dTotalAmount = info.getTotalAmount();

						tmpInt++;
	%>
        <TR align="center"  borderColor=#999999>		  
		  <TD class=ItemBody>
		  <%=DataFormat.formatInt(tmpInt,3,true)%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.formatString(info.getCode())%>
		  </TD>
          <TD align="center" class=ItemBody>￥
		  <%if ((info!=null)&&(info.getAmount()>0)) {out.println(DataFormat.formatDisabledAmount(info.getAmount()));} else {out.println("0.00");}%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getDiscountDate())%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getEnd())%> 
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=info.getAddDays()%> 天
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=info.getDays()%> 天
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=DataFormat.formatRate(info.getDiscountRate())%> %
		  </TD>
		  <TD align="center" class=ItemBody>￥
		  <%if (info.getAccrual()>0) {out.println(DataFormat.formatDisabledAmount(info.getAccrual()));} else {out.println("0.00");}%>
		  </TD>
		  <TD align="center" class=ItemBody>￥
		  <%if (info.getRealAmount()>0) {out.println(DataFormat.formatDisabledAmount(info.getRealAmount()));} else {out.println("0.00");}%>	  
		  </TD>
		</TR>
<% /*************************The If and while 's End ********************************/ %>
        <%
		           }
	            }
			   else{
			   %>
         <TR align=center borderColor=#999999>
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD></TR>			   
			   <%			   
			   }
        %>
				  
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=10 height=25>
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar 
            height=50 width="100%">
              <TBODY>
              <TR>
                <td width="500" height="2">汇总贴现笔数：　　<%=lCount%><BR>
汇总贴现利息：　　￥ <%if (dTotalAccrual>0) {out.println(DataFormat.formatDisabledAmount(dTotalAccrual));} else {out.println("0.00");}%><BR>汇总实付贴现金额：￥ <%if (dTotalRealAmount>0) {out.println(DataFormat.formatDisabledAmount(dTotalRealAmount));} else {out.println("0.00");}%>
</td>
<TD height=2 width="428" valign=bottom></TD>
     </TR>
   </TBODY></TABLE></TD></TR></TBODY></TABLE>            
</TD></TR>
			
  <TR>
    <TD height=40 vAlign=bottom>
      <TABLE align=center border=0 height=40 width="99%">
        <TBODY>
        <TR borderColor=#ffffff>
          <TD colSpan=4 height=1>            
			<DIV align=right>
				<INPUT class=button name=Submit423 onclick="printPage();" type="button" value=" 打 印 ">
				<%if ((backurl != null) && (backurl.length() > 0)) {%>
				<INPUT class=button name="backstep" onClick="MM_goToURL('self','<%=backurl%>.jsp?lDiscountID=<%=lDiscountID%>&lCredenceID=<%=lCredenceID%>&lContractID=<%=lContractID%>&backpage=<%=backpage%>&control=view');" type="button" value=" 返 回 ">
				<%} else {%>
				<INPUT class=button name="close" onClick="window.close();" type="button" value=" 关 闭 ">
				<%}%>
            </DIV></TD></TR></TBODY></TABLE></TD></TR>

</TBODY></TABLE></TD></TR></TBODY></TABLE>

<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
<input type="hidden" name="lPageNo" value="<%=lPageNo%>">
<input type="hidden" name="lOrderParam" value="<%=lOrderParam%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="hidden" name="control" value="view">
<input type="hidden" name="backurl" value="<%=backurl%>">
<input type="hidden" name="backpage" value="<%=backpage%>">
<input type="hidden" name="lCredenceID" value="<%=lCredenceID%>">
<input type="hidden" name="lContractID" value="<%=lContractID%>">
<input type="hidden" name="lDiscountID" value="<%=lContractID%>">

<P><BR></P>
</form>

<script language="javascript">

function printPage()
{	
	window.open("S127-p.jsp?lDiscountID=<%=lDiscountID%>&lContractID=<%=lContractID%>&lCredenceID=<%=lCredenceID%>&control=view&isSM=<%=Constant.YesOrNo.NO%>","popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");	
}
/*
function key_down()
{
	if (window.event.keyCode == 13)
	{
		if (InputValid(frm.cz,1, "int", 1, 1, frm.lPageCount.value,"页数")) 
		{
			frm.lPageNo.value=frm.cz.value;
		}
		else	return (false);
		confirmSave(frm);
	}
}
*/
function go(para)
{  
	if (frm.lOrderParam.value == para)
	{
		if (frm.lDesc.value == "<%=Constant.PageControl.CODE_ASCORDESC_ASC%>")
		{
			frm.lDesc.value = "<%=Constant.PageControl.CODE_ASCORDESC_DESC%>";
		}
		else
		{
	   		frm.lDesc.value = "<%=Constant.PageControl.CODE_ASCORDESC_ASC%>"; 
		}
	}
	frm.lOrderParam.value = para;
	frm.control.value = "view";
	frm.action = "S127.jsp";
	showSending();
	frm.submit();
}
/*
function goto()
{
	var lMax=frm.lPageCount.value;
	if (InputValid(frm.cz,1, "int", 1, 1, lMax,"页数"))
	{
		frm.lPageNo.value=frm.cz.value;
	}
	else return (false);
	confirmSave(frm);
}

function gohere()
{
	confirmSave(frm);
}
*/

function selectAll(frm)
{
	var i;
	//只选择了一个checkBox,则checkBox不成为数组,checkBox[i]不可用
	if( isNaN(frm.checkbox) == true )
	{
		return false;
	}
	else if( isNaN(frm.checkbox.length) == true )	
	{		
		frm.checkbox.checked = true; 
	}
	else
	{
		for(i = 0; i < <%=lCount%>; i++)
		{
			if (frm.checkbox[i] != null)
			{
				frm.checkbox[i].checked = true;
			}
		}
	}
}

function disabledAll(frm)
{
	var i;
	//只选择了一个checkBox,则checkBox不成为数组,checkBox[i]不可用
	if( isNaN(frm.checkbox.length) == true )	
	{		
		frm.checkbox.disabled = true;
	}
	else
	{
		for(i = 0; i < <%=lCount%>; i++)
		{
			if (frm.checkbox[i] != null)
			{
				frm.checkbox[i].disabled = true;
			}
		}
	}
}

function countAll()
{
	frm.lPageNo.value="";
	confirmSave(frm);
}

function confirmSave(frm)
{		
	var bSelect;
	bSelect = false;
	//只选择了一个checkBox,则checkBox不成为数组,checkBox[i]不可用
	if( isNaN(frm.checkbox.length) == true )
	{
		if( frm.checkbox.checked == true )
		{
			bSelect = true;
		}			
	}
	else
	{
		var i;
		for(i=0; i<frm.checkbox.length; i++ )
		{
			if( frm.checkbox[i].checked == true )
			{
				bSelect = true;
				break;
			}
		}
	}
	if(!bSelect)
	{
		alert("请至少选择一张贴现票据！");
		return false;
	}

	if(confirm("是否提交？"))
	{
		frm.control.value="save";
		frm.action="S127.jsp";
		showSending();
		frm.submit();
		return true;
	}
}

<% if( (strBillID == null) || (strBillID.length() == 0) ) { %>
	//disabledAll(frm);
<% } %>
//firstFocus(frm.txtContractCtrl);
////setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 

</script>
<%
	LOANHTML.showHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"贴现申请", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>