/* ����ִ�� */
function getFingerPrint(frm,nCmd)
{
	FpDemo = frm;
	if(typeof(FpDemo) =="undefined")
	{
		alert("����δ���壡");
		return;
	}
	
	var nPortNo = "0";			// �˿�SLCT
	var dwWaitTime = "18000";	// ��ʱEDIT
		
	var nRet = -1; 
	var sDvSn = "";
	var objVer = FpDemo.Ver;				// ����EDIT
	if (typeof(ObjFinger) == "undefined") {
		alert("�ؼ�δ�ҵ�������HTMl�������OBJECT");
		return -1;
	}

	// TESO
	if (typeof (ObjFinger.nSucStrFmtTyp) != "undefined") {
				// 0x30 ��ָ�ʽ
				ObjFinger.nSucStrFmtTyp = 1;
				// ����Э��
				ObjFinger.nComProtocol = 2;
				// USBЭ��
				ObjFinger.nUsbProtocol = 0;
				// �Ƿ���̧��
				ObjFinger.nRegChkToLv = 1;
				// ע��ָ��ģ��ʱ�ĶԻ���
				ObjFinger.nRegShowDlg = 1;
				// ��ָ֤������ʱ�ĶԻ���
				ObjFinger.nVerShowDlg = 1;
				// �Ƿ�����Զ�����
				ObjFinger.nNotSpeedUp = 0;
	}

	// ͨ�÷���
	switch(nCmd)
	{
	case 1: // ��֤������Base64		
		try{
			nRet = ObjFinger.FPIGetFeature(nPortNo, dwWaitTime);
		}
		catch(exception){
			alert("ָ�ƿؼ�û�а�װ��������");
		}
		
		if (nRet) {
			if("-15"==nRet){
				alert("�����ָ��ʶ������ʶ��ָ��");
			}else{
			//objVer.value = nRet;
			//alert("��ָ֤������ʧ�ܡ�������:[" + nRet + "]");
			}
		} else {
			objVer.value = ObjFinger.FPIGetFingerInfo();
		}
		break;
	case 10:// ��֤�ؼ��Ƿ���Ϲ淶
		var v1 = typeof (ObjFinger.FPIFpMatch);
		var v2 = typeof (ObjFinger.FPIGetFeature);
		var v3 = typeof (ObjFinger.FPIGetTemplate);
		var eMsg="";
		if ( v1== "undefined" ) eMsg += "û�� FPIFpMatch ����\n"; 
		if ( v2== "undefined" ) eMsg += "û�� FPIGetFeature ����\n"; 
		if ( v3== "undefined" ) eMsg += "û�� FPIGetTemplate ����\n"; 
		if (eMsg != "") {
		    alert( " ����\n\n"+eMsg);
		} else {
			alert("��׼���÷��������ڣ���ֱ���� ע�ᡢ��֤���ȶ�");
		}
		break;

	default:
		alert("���������");
	}

} 