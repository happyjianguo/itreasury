//ѡ��ָ���˻����
function insertAppointAccount(form){
	var isAppoint = true; 
	var appointAccountNo = form.appointAccountIDStartCtrl.value;
	if(appointAccountNo == null || appointAccountNo.length < 12){
		alert("ָ���˻�����������飡");
	}else{
		for(i=0;i<form.appointAccountNo.options.length;i++){
    		if(form.appointAccountNo.options[i].value==appointAccountNo){
    		isAppoint=false;
        	alert("���˻���ָ����");
        	break;
      		}
    	}
    	if(isAppoint){
    		var oOption = document.createElement("OPTION");
			oOption.text=appointAccountNo;
	    	oOption.value=appointAccountNo;
	    	form.appointAccountNo.add(oOption);
	    	form.accountNos.value=getSelectAppointAccount(form.appointAccountNo);
    	}
	    form.appointAccountIDStartCtrl.value="";
	    //form.appointAccountIDStartCtrlCtrl2.value="";
	    //form.appointAccountIDStartCtrlCtrl3.value="";
	    //form.appointAccountIDStartCtrlCtrl4.value="";
	}
}
//�õ�select���ڵ����е�ֵ
function getSelectAppointAccount(obj){
	var cData;
  	cData=""
  	for(i=0;i<obj.options.length;i++){
    	if(i>0){
	  		cData=cData+"','";
    	}
    	cData=cData+obj.options[i].value;
	}
	return cData;
}
//ɾ��ָ���˻����
function deleteAppointAccount(form){
	var fFirst=true;
  	while(true){
    	for(i=0;i<form.appointAccountNo.options.length;i++){
    		if(form.appointAccountNo.options[i].selected==true){
        	fFirst=false;
        	form.appointAccountNo.remove(i);
        	break;
      		}
    	}
    	if(fFirst){
			break;
		}else{
			fFirst=true;
  		}
  	}
	form.accountNos.value=getSelectAppointAccount(form.appointAccountNo);
}