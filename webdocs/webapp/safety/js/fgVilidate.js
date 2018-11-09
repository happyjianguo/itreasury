/* 命令执行 */
function getFingerPrint(frm,nCmd)
{
	FpDemo = frm;
	if(typeof(FpDemo) =="undefined")
	{
		alert("表单域未定义！");
		return;
	}
	
	var nPortNo = "0";			// 端口SLCT
	var dwWaitTime = "18000";	// 超时EDIT
		
	var nRet = -1; 
	var sDvSn = "";
	var objVer = FpDemo.Ver;				// 特征EDIT
	if (typeof(ObjFinger) == "undefined") {
		alert("控件未找到，请检查HTMl代码里的OBJECT");
		return -1;
	}

	// TESO
	if (typeof (ObjFinger.nSucStrFmtTyp) != "undefined") {
				// 0x30 拆分格式
				ObjFinger.nSucStrFmtTyp = 1;
				// 商行协议
				ObjFinger.nComProtocol = 2;
				// USB协议
				ObjFinger.nUsbProtocol = 0;
				// 是否检测抬起
				ObjFinger.nRegChkToLv = 1;
				// 注册指纹模板时的对话框
				ObjFinger.nRegShowDlg = 1;
				// 验证指纹特征时的对话框
				ObjFinger.nVerShowDlg = 1;
				// 是否禁用自动提速
				ObjFinger.nNotSpeedUp = 0;
	}

	// 通用方法
	switch(nCmd)
	{
	case 1: // 验证特征，Base64		
		try{
			nRet = ObjFinger.FPIGetFeature(nPortNo, dwWaitTime);
		}
		catch(exception){
			alert("指纹控件没有安装或有问题");
		}
		
		if (nRet) {
			if("-15"==nRet){
				alert("请插入指纹识别器，识别指纹");
			}else{
			//objVer.value = nRet;
			//alert("验证指纹特征失败。错误码:[" + nRet + "]");
			}
		} else {
			objVer.value = ObjFinger.FPIGetFingerInfo();
		}
		break;
	case 10:// 验证控件是否符合规范
		var v1 = typeof (ObjFinger.FPIFpMatch);
		var v2 = typeof (ObjFinger.FPIGetFeature);
		var v3 = typeof (ObjFinger.FPIGetTemplate);
		var eMsg="";
		if ( v1== "undefined" ) eMsg += "没有 FPIFpMatch 方法\n"; 
		if ( v2== "undefined" ) eMsg += "没有 FPIGetFeature 方法\n"; 
		if ( v3== "undefined" ) eMsg += "没有 FPIGetTemplate 方法\n"; 
		if (eMsg != "") {
		    alert( " 错误：\n\n"+eMsg);
		} else {
			alert("标准调用方法都存在！请分别测试 注册、验证、比对");
		}
		break;

	default:
		alert("错误的命令");
	}

} 