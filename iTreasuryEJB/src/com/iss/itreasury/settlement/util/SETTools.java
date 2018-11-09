package com.iss.itreasury.settlement.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.iss.inut.workflow.constants.StateEnum;
import com.iss.inut.workflow.ws.WorkflowManager;

public class SETTools {

	public static HashMap getYHFKHashmap(HttpServletRequest request){
		WorkflowManager wf=null;
		StateEnum  se=null;
	String[] strs=new String[]{"dAmount","strBankChequeNo","lBankID",
"lBillBankID"
,

"lBillBankIDCtrl"
,



"lBillTypeID"
,



"lCashFlowID"
,



"strCheckAbstractStr"
,


"strConfirmAbstractStr"
,



"lConfirmOfficeID"
,



"lConfirmUserID"
,


"strConsignPassword"
,



"lConsignReceiveTypeID"
,



"strConsignVoucherNo"
,



"strDeclarationNo"
,



"strExtBankNo"
,



"strInstructionNo"
,



"strModifyTime"
,


"lPayAccountID"
,



"lReceiveAccountID"
,



"lSignUserID"
,



"lSingleBankAccountTypeID"
,



"lStatusID"
,



"lReceiveClientID"
,



"lPayClientID"
,




"lSubClientID"
,


"lReckoningTypeID"
,


"lReckoningBillTypeIDCtrl"
,


"lAbstractID"
,


"lExtClientIDCtrl"
,


"strExtClientName"
,


"strRemitInBank"
,



"strRemitInCity"
,



"strRemitInProvince"
,


"lAbstractIDCtrl"
,



"lCheckUserID"
,


"lCurrencyID"
,



"strExecuteDate"
,



"lId"
,



"lInputUserID"
,


"strInterestStartDate"
,



"lOfficeID"
,



"lTransactionTypeID"
,


"strTransNo"
,


"lBudgetItemID"
,




"commissionAmount"
,



"commissionType"};
	int len=strs.length;
	HashMap map=new HashMap();
	for(int i=0;i<len;i++){
		map.put(strs[i],request.getAttribute(strs[i]));
	}
	return map;
}

	
}
