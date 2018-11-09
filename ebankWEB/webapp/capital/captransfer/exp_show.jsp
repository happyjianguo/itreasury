<%--
 ҳ������ �� exp_show.jsp
 ҳ�湦�� :  ����������ʾҳ��
 ��    �� �� niweinan
 ��    �� �� 2011-2-24
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>




<html>
	<head>
		<script type="text/javascript" src="/jQueryEasyUI/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="/jQueryEasyUI/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="/jQueryEasyUI/json2.js"></script>
		<script type="text/javascript" src="/itreasury/js/util.js"></script>
		<link rel="stylesheet" type="text/css" href="/jQueryEasyUI/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="/jQueryEasyUI/themes/default/easyui.css">
		<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
		
		<title>����������ʾҳ��</title>
		<%
			String strContext = request.getContextPath();
			String temp = "";
			String fileName = "";
			
			temp=request.getParameter("fileName");
			if(temp!=null&&!temp.trim().equals(""))
			{
				fileName = temp;
			}

		 %>
		<script type="text/javascript">
			$(function(){
				var lastIndex = -1;
				
				$('#tt').datagrid({
					title:'����������ϸҳ��',
					iconCls:'icon-edit',
					singleSelect:true,
					columns:[[
						{field:'sPayerAcctNo',title:'����ʻ����',editor:'text',width:110,align:'middle'},
						{field:'lRemitType',title:'��ʽ',formatter:remitTypesFormatter,
								 editor:{
								 	type:'combobox',
								 	options:{
								 		valueField:'lRemitType',
								 		textField:'name',
								 		data:remitTypes,
								 		editable:false,
								 		panelHeight:'auto',
								 		onChange:clickRemitType
								 		
								 		
								 	}
								 },align:'middle',width:70},
						{field:'sPayeeName',title:'�տ����',editor:'text',width:110,align:'middle'},
						{field:'sPayeeAcctNo',title:'�տ�ʻ����',editor:'text',width:110,align:'middle'},
						{field:'sPayeeProv',title:'����ʡ',editor:'text',width:50,align:'middle'},
						{field:'sPayeeCity',title:'������',editor:'text',width:50,align:'middle'},
						{field:'sPayeeBankName',title:'����������',editor:'text',width:100,align:'middle'},
						{field:'sPayeeBankCNAPSNO',title:'������CNAPS��',editor:'text',width:100,align:'middle'},
						{field:'sPayeeBankOrgNO',title:'�����л�����',editor:'text',width:100,align:'middle'},
						{field:'sPayeeBankExchangeNO',title:'���������к�',editor:'text',width:100,align:'middle'},
						{field:'dAmount',title:'���',
								editor:{
									type:'numberbox',
									options:{
											precision:2
											
								
										}
									},width:50,align:'middle'},
						{field:'isSameBank',title:'�Ƿ�ͬ��',formatter:isSameBankFormatter,
								editor:{
									type:'combobox',
									options:{
										valueField:'isSameBank',
										textField:'name',
										data:sameBank,
										editable:false,
								 		panelHeight:'auto'
										
									}
								},width:60,align:'middle'},
						{field:'lRemitArea',title:'�������',formatter:remitAreaFormatter,
								editor:{
									type:'combobox',
									options:{
										valueField:'lRemitArea',
										textField:'name',
										data:remitArea,
										editable:false,
								 		panelHeight:'auto'
									}
								},width:60,align:'middle'},
						{field:'lRemitSpeed',title:'����ٶ�',formatter:remitSpeedFormatter,
								editor:{
									type:'combobox',
									options:{
										valueField:'lRemitSpeed',
										textField:'name',
										data:remitSpeed,
										editable:false,
								 		panelHeight:'auto'
								 	
								 		
									}
								},width:60,align:'middle'},
						{field:'sNote',title:'�����;',editor:'text',width:100,align:'middle'}
					
						
					]],
					url:"getJSONString.jsp?fileName=<%=fileName%>",
					toolbar:[{
						text:'����',
						iconCls:'icon-add',
						handler:function(){
							$('#tt').datagrid('acceptChanges');	
							if(lastIndex==-1)
							{
								$('#tt').datagrid('appendRow',{
													sPayerAcctNo:'',
													lRemitType:'',
													sPayeeName:'',
													sPayeeAcctNo:'',
													sPayeeProv:'',
													sPayeeCity:'',
													sPayeeBankName:'',
													sPayeeBankCNAPSNO:'',
													sPayeeBankOrgNO:'',
													sPayeeBankExchangeNO:'',
													dAmount:'',
													isSameBank:'',
													lRemitArea:'',
													lRemitSpeed:'',
													sNote:''
												});
												lastIndex = $('#tt').datagrid('getRows').length-1;
												$('#tt').datagrid('beginEdit',lastIndex);
												$('#tt').datagrid('selectRow',lastIndex);
							}
							else 
							{
								if(validate(lastIndex))
								{
									var addRows = $('#tt').datagrid('getRows');
									var addRow = addRows[lastIndex];
									var sPayerAcctNo = addRow.sPayerAcctNo;
									var lRemitType = addRow.lRemitType;
									var sPayeeName = encodeURI(encodeURI(addRow.sPayeeName));
									var sPayeeAcctNo = addRow.sPayeeAcctNo;
									
									$.ajax(
										{
											type:'post',
											url:'<%=strContext%>/capital/captransfer/validate.jsp',
											data:'lRemitType='+lRemitType+'&sPayerAcctNo='+sPayerAcctNo+'&sPayeeName='+sPayeeName+'&sPayeeAcctNo='+sPayeeAcctNo,
											async:false,
											success:function(returnValue){
												
												var result = $(returnValue).filter("div#result").text();
												if(result=="PayerAccountHaveNoAuthority")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("�������в����ø���˻���Ȩ�ޣ�");
												}
												else if(result=="PayerAccountNotExist")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("����ʻ�������!");
												}
												else if(result=="PayeeAccountNotExist")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("�տ�ʻ�������!");
												}
												else if(result=="SameNo")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("����ʻ���ź��տ�ʻ������ͬ!");
												}
												else if(result=="Success")
												{
													$('#tt').datagrid('appendRow',{
														sPayerAcctNo:'',
														lRemitType:'',
														sPayeeName:'',
														sPayeeAcctNo:'',
														sPayeeProv:'',
														sPayeeCity:'',
														sPayeeBankName:'',
														sPayeeBankCNAPSNO:'',
														sPayeeBankOrgNO:'',
														sPayeeBankExchangeNO:'',
														dAmount:'',
														isSameBank:'',
														lRemitArea:'',
														lRemitSpeed:'',
														sNote:''
													});
													lastIndex = $('#tt').datagrid('getRows').length-1;
													$('#tt').datagrid('beginEdit',lastIndex);
													$('#tt').datagrid('selectRow',lastIndex);
												}
											}
											
										}
									);
									
								}
								else
								{
									$('#tt').datagrid('beginEdit',lastIndex);
								}
							}
						}
					},'-',{
						text:'ɾ��',
						iconCls:'icon-remove',
						handler:function(){
							var row;
							var index;
							row = $('#tt').datagrid('getSelected');
							if(row)
							{
								index = $('#tt').datagrid('getRowIndex',row);
								$('#tt').datagrid('deleteRow',index);
								
							}
							$('#tt').datagrid('acceptChanges');	
							lastIndex = -1;
						}
					},'-',{
						text:'����',
						iconCls:'icon-save',
						handler:function(){
							$('#tt').datagrid('acceptChanges');
							if(lastIndex==-1)
							{
								$('#tt').datagrid('beginEdit',lastIndex);
							}
							else
							{
								var saveRows = $('#tt').datagrid('getRows');
								var saveRow = saveRows[lastIndex];
								var sPayerAcctNo = saveRow.sPayerAcctNo;
								var lRemitType = saveRow.lRemitType;
								var sPayeeName = encodeURI(encodeURI(saveRow.sPayeeName));
								var sPayeeAcctNo = saveRow.sPayeeAcctNo;
								
								if(validate(lastIndex))
								{
									$.ajax(
										{
											type:'post',
											url:'<%=strContext%>/capital/captransfer/validate.jsp',
											data:'lRemitType='+lRemitType+'&sPayerAcctNo='+sPayerAcctNo+'&sPayeeName='+sPayeeName+'&sPayeeAcctNo='+sPayeeAcctNo,
											async:false,
											success:function(returnValue){
												var result = $(returnValue).filter("div#result").text();
												if(result=="PayerAccountHaveNoAuthority")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("�������в����ø���˻���Ȩ�ޣ�");
												}
												else if(result=="PayerAccountNotExist")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("����ʻ�������!");
												}
												else if(result=="PayeeAccountNotExist")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("�տ�ʻ�������!");
												}
												else if(result=="SameNo")
												{
													$('#tt').datagrid('beginEdit',lastIndex);
													alert("����ʻ���ź��տ�ʻ������ͬ!");
												}
												else if(result=="Success")
												{
													$('#tt').datagrid('clearSelections');
													lastIndex = -1;
												}
											}
										}
									);

								}
								else
								{
									$('#tt').datagrid('beginEdit',lastIndex);
									
								}
							}
							
						}
					},'-'],
					onBeforeLoad:function(){
						$('#tt').datagrid('rejectChanges');
					},
					onClickRow:function(rowIndex){   //�޸�
						if(lastIndex!=rowIndex)
						{
							$('#tt').datagrid('acceptChanges');	
							if(lastIndex==-1)
							{
								
								$('#tt').datagrid('beginEdit',rowIndex);
								lastIndex = rowIndex;
							}
							else
							{
								if(validate(lastIndex))
								{
									var modifyRows = $('#tt').datagrid('getRows');
									var modifyRow = modifyRows[lastIndex];
									var sPayerAcctNo = modifyRow.sPayerAcctNo;
									var lRemitType = modifyRow.lRemitType;
									var sPayeeName = encodeURI(encodeURI(modifyRow.sPayeeName));
									var sPayeeAcctNo = modifyRow.sPayeeAcctNo;
									$.ajax(
										{
											type:'post',
											url:'<%=strContext%>/capital/captransfer/validate.jsp',
											data:'lRemitType='+lRemitType+'&sPayerAcctNo='+sPayerAcctNo+'&sPayeeName='+sPayeeName+'&sPayeeAcctNo='+sPayeeAcctNo,
											async:false,
											success:function(returnValue){
												var result = $(returnValue).filter("div#result").text();
												if(result=="PayerAccountHaveNoAuthority")
												{
													$('#tt').datagrid('clearSelections');
													$('#tt').datagrid('beginEdit',lastIndex);
													$('#tt').datagrid('selectRow',lastIndex);
													alert("�������в����ø���˻���Ȩ�ޣ�");
												}
												else if(result=="PayerAccountNotExist")
												{
													$('#tt').datagrid('clearSelections');
													$('#tt').datagrid('beginEdit',lastIndex);
													$('#tt').datagrid('selectRow',lastIndex);
													alert("����ʻ�������!");
												}
												else if(result=="PayeeAccountNotExist")
												{
													$('#tt').datagrid('clearSelections');
													$('#tt').datagrid('beginEdit',lastIndex);
													$('#tt').datagrid('selectRow',lastIndex);
													alert("�տ�ʻ�������!");
												}
												else if(result=="SameNo")
												{
													$('#tt').datagrid('clearSelections');
													$('#tt').datagrid('beginEdit',lastIndex);
													$('#tt').datagrid('selectRow',lastIndex);
													alert("����ʻ���ź��տ�ʻ������ͬ!");
												}
												else if(result=="Success")
												{
													$('#tt').datagrid('beginEdit',rowIndex);
													lastIndex = rowIndex;
												}
											}
										}
									);

								}
								else
								{
									$('#tt').datagrid('clearSelections');
									$('#tt').datagrid('beginEdit',lastIndex);
									$('#tt').datagrid('selectRow',lastIndex);
								}
							}
							
							
						}

					}
				});
				
			});
			
			
						
			//�������
			var remitTypes = [                
				{lRemitType:102,name:'���л��'},
				{lRemitType:103,name:'�ڲ�ת��'}
			];
			
			//�Ƿ�ͬ��
			var sameBank = [
				
				{isSameBank:1,name:'��'},
				{isSameBank:2,name:'��'}
			];
			
			//�������
			var remitArea = [
				{lRemitArea:1,name:'����'},
				{lRemitArea:2,name:'���'}
			];
			
			//����ٶ�
			var remitSpeed = [
				{lRemitSpeed:1,name:'��ͨ'},
				{lRemitSpeed:2,name:'�Ӽ�'}
			];
			
			function remitTypesFormatter(value)
			{
				
				for(var i=0;i<remitTypes.length;i++)
				{
					if(remitTypes[i].lRemitType==value)
					{
						return remitTypes[i].name;
					}
					
				}
				return value;
			}
			
			function isSameBankFormatter(value)
			{
				for(var i=0;i<sameBank.length;i++)
				{
					if(sameBank[i].isSameBank==value)
					{
						return sameBank[i].name;
					}
				}
				return value;
			}
			
			function remitAreaFormatter(value)
			{
				for(var i=0;i<remitArea.length;i++)
				{
					if(remitArea[i].lRemitArea==value)
					{
						return remitArea[i].name;
					}
				}
				return value;
			}
			
			function remitSpeedFormatter(value)
			{
				for(var i=0;i<remitSpeed.length;i++)
				{
					if(remitSpeed[i].lRemitSpeed==value)
					{
						return remitSpeed[i].name;
					}
				}
				return value;
			}
	
			function clickRemitType()
			{
				var editLine = $('#tt').datagrid('getSelected');
				var editLineIndex = $('#tt').datagrid('getRowIndex',editLine);
				var remitTypeEdit = $('#tt').datagrid('getEditor',{index:editLineIndex,field:'lRemitType'});  //��ʽ
				var payeeProvEdit = $('#tt').datagrid('getEditor',{index:editLineIndex,field:'sPayeeProv'});  //����ʡ
				var payeeCityEdit = $('#tt').datagrid('getEditor',{index:editLineIndex,field:'sPayeeCity'});  //������
				var payeeBankNameEdit = $('#tt').datagrid('getEditor',{index:editLineIndex,field:'sPayeeBankName'});  //����������
				var payeeBankCNAPSNOEdit = $('#tt').datagrid('getEditor',{index:editLineIndex,field:'sPayeeBankCNAPSNO'});  //������CNAPS��
				var payeeBankOrgNOEdit = $('#tt').datagrid('getEditor',{index:editLineIndex,field:'sPayeeBankOrgNO'});  //�����л�����
				var payeeBankExchangeNOEdit = $('#tt').datagrid('getEditor',{index:editLineIndex,field:'sPayeeBankExchangeNO'}); //���������к�
				
				if(remitTypeEdit!=null&&payeeProvEdit!=null&&payeeCityEdit!=null&&payeeBankNameEdit!=null&&payeeBankCNAPSNOEdit!=null&&payeeBankOrgNOEdit!=null&&payeeBankExchangeNOEdit!=null)
				{
					var remitTypeTarget = remitTypeEdit.target;
					var remitType = remitTypeTarget.combobox('getValue');
					var payeeProv = payeeProvEdit.target;
					var payeeCity = payeeCityEdit.target;
					var payeeBankName = payeeBankNameEdit.target;
					var payeeBankCNAPSNO = payeeBankCNAPSNOEdit.target;
					var payeeBankOrgNO = payeeBankOrgNOEdit.target;
					var payeeBankExchangeNO = payeeBankExchangeNOEdit.target;
					if(remitType==<%=OBConstant.SettRemitType.BANKPAY%>)    //���и���
					{
						payeeProv.attr('readonly','');
						payeeCity.attr('readonly','');
						payeeBankName.attr('readonly','');
						payeeBankCNAPSNO.attr('readonly','');
						payeeBankOrgNO.attr('readonly','');
						payeeBankExchangeNO.attr('readonly','');
					}
					else if(remitType==<%=OBConstant.SettRemitType.INTERNALVIREMENT%>)  //�ڲ�ת��
					{
						payeeProv.val('');
						payeeProv.attr('readonly','readonly');
						payeeCity.val('');
						payeeCity.attr('readonly','readonly');
						payeeBankName.val('');
						payeeBankName.attr('readonly','readonly');
						payeeBankCNAPSNO.val('');
						payeeBankCNAPSNO.attr('readonly','readonly');
						payeeBankOrgNO.val('');
						payeeBankOrgNO.attr('readonly','readonly');
						payeeBankExchangeNO.val('');
						payeeBankExchangeNO.attr('readonly','readonly');
					}
				}
				
				
			
			}
			function validate(index)
			{
				
				if(index>-1)
				{
					var rows = $('#tt').datagrid('getRows');
					var row = rows[index];
					var sPayerAcctNo = row.sPayerAcctNo;
					var lRemitType = row.lRemitType;
					var sPayeeName = row.sPayeeName;
					var sPayeeAcctNo = row.sPayeeAcctNo;
					var sPayeeProv = row.sPayeeProv;
					var sPayeeCity = row.sPayeeCity;
					var sPayeeBankName = row.sPayeeBankName;
					var sPayeeBankCNAPSNO = row.sPayeeBankCNAPSNO;
					var sPayeeBankOrgNO = row.sPayeeBankOrgNO;
					var sPayeeBankExchangeNO = row.sPayeeBankExchangeNO;
					var dAmount = row.dAmount;
					var isSameBank = row.isSameBank;
					var lRemitArea = row.lRemitArea;
					var lRemitSpeed = row.lRemitSpeed;
					var sNote = row.sNote;
					var sPayeeNameLength = sPayeeName.replace(/[^\x00-xff]/g,'**').length;
					
					if(sPayerAcctNo=="")
					{
						$('#tt').datagrid('beginEdit',index);
						alert("����ʻ���Ų���Ϊ�գ�");
						return false;
					}
					if(lRemitType=="")
					{
						
						$('#tt').datagrid('beginEdit',index);
						alert("��ѡ���ʽ!");
						return false;
					}
					
					if(sPayeeName=="")
					{
						
						$('#tt').datagrid('beginEdit',index);
						alert("�տ���Ʋ���Ϊ��!");
						return false;
					}
					
					if(lRemitType==<%=OBConstant.SettRemitType.BANKPAY%>)
					{
						if(sPayeeNameLength>80)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("�տ���Ƴ��Ȳ��ܴ���40������(80���ֽ�)!");
							return false;
						}
					}
					
					if(sPayeeAcctNo=="")
					{
						
						$('#tt').datagrid('beginEdit',index);
						alert("�տ�ʻ���Ų���Ϊ��!");
						return false;
					}
					
					if(lRemitType==<%=OBConstant.SettRemitType.BANKPAY%>)
					{
						if(sPayeeAcctNo.length>50)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("�տ�ʻ���ų��Ȳ��ܴ���50!");
							return false;
						}
						if(sPayeeProv=="")
						{
							$('#tt').datagrid('beginEdit',index);
							alert("����ʡ����Ϊ��!");
							return false;
						}
						if(sPayeeProv.length>10)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("����ʡ���Ȳ��ܴ���10");
							return false;
						}
						if(sPayeeCity=="")
						{
							$('#tt').datagrid('beginEdit',index);
							alert("�����в���Ϊ��!");
							return false;
						}
						if(sPayeeCity.length>10)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("�����г��Ȳ��ܴ���10!");
							return false;
						}
						if(sPayeeBankName=="")
						{
							$('#tt').datagrid('beginEdit',index);
							alert("���������Ʋ���Ϊ��!");
							return false;
						}
						if(sPayeeBankName.length>40)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("���������Ƴ��Ȳ��ܴ���40!")
							return false;
						}
						if(sPayeeBankCNAPSNO.length>20)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("������CNAPS�ų��Ȳ��ܴ���20!")
							return false;
						}
						if(sPayeeBankOrgNO.length>20)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("�����л����ų��Ȳ��ܴ���20!")
							return false;
						}
						if(sPayeeBankExchangeNO.length>20)
						{
							$('#tt').datagrid('beginEdit',index);
							alert("���������кų��Ȳ��ܴ���20!")
							return false;
						}						
						
					}
					
					if(dAmount==0.00||dAmount=="")
					{
						$('#tt').datagrid('beginEdit',index);
						alert("����Ϊ0!");
						return false;
					}
					
					if(dAmount.indexOf("-")>-1)
					{
						$('#tt').datagrid('beginEdit',index);
						alert("����Ϊ����!");
						return false;
					}
					
					if(isSameBank=="")
					{
						$('#tt').datagrid('beginEdit',index);
						alert("�Ƿ�ͬ�в���Ϊ��!");
						return false;
					}
					
					if(lRemitArea=="")
					{
						$('#tt').datagrid('beginEdit',index);
						alert("���������Ϊ��!");
						return false;
					}
					
					if(lRemitSpeed=="")
					{
						$('#tt').datagrid('beginEdit',index);
						alert("����ٶȲ���Ϊ��!");
						return false;
					}
					
					if(sNote=="")
					{
						$('#tt').datagrid('beginEdit',index);
						alert("�����;����Ϊ��!");
						return false;
					}
					
					var sNoteLength = sNote.replace(/[^\x00-\xff]/g,"**").length;
					if(sNoteLength>12)
					{
						$('#tt').datagrid('beginEdit',index);
						alert("�����;���Ȳ��ܴ���6�����֣�12���ֽڣ�!");
						return false;
					}
					
					
				}
				return true;
			}
		</script>
	</head>
	<body>
		<form name="frm" action="" method="post" >
		<input type="hidden" name="jsonString" value="">
		<input type="hidden" name="strSuccessURL" value="/capital/captransfer/batchpay_v.jsp">
		<input type="hidden" name="strFailureURL" value="/capital/captransfer/batchpay_v.jsp">
		<table id="tt"></table>
		<br>
			<div align="right" >
				<input type="button" name=save value=" �� �� " style="font-size: 12px;line-height: 150%;color: #000000;text-decoration: none;background-image: url(/webob/graphics/button_bg.jpg);background-repeat: repeat-x;background-position: center center;height: 20px;border: 1px solid #989898;" onclick="javacript:doAdd()"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name=back value=" �� �� " style="font-size: 12px;line-height: 150%;color: #000000;text-decoration: none;background-image: url(/webob/graphics/button_bg.jpg);background-repeat: repeat-x;background-position: center center;height: 20px;border: 1px solid #989898;" onclick="javacript:doBack()"/>&nbsp;&nbsp;&nbsp;&nbsp;
				
			</div>
		</form>
	</body>
	<script type="text/javascript">
		function doAdd()
		{
			
			if(validateSubmit())
			{
			
				if(confirm("�Ƿ��ύ?"))
				{
					$('#tt').datagrid('acceptChanges');
					var jsonObject = $('#tt').datagrid('getData');
					var jsonString = JSON.stringify(jsonObject);
					frm.jsonString.value=jsonString;
					frm.action="<%=strContext%>/capital/captransfer/exp_save.jsp";
					showSending();
					frm.submit();
				
				}
			}
			
		}
		function doBack()
		{
			if(confirm("�Ƿ񷵻أ�"))
			{
				
				frm.action="<%=strContext%>/capital/captransfer/batchpay_v.jsp";
				showSending();
				frm.submit();
				
			}
		}
		
		function validateSubmit()
		{
			var row = $('#tt').datagrid('getSelected');
			if(row!=null)
			{
				alert("����������δ���棬���ȱ����������ύ��");
				return false;
			}
			var result = validateSumAmount();
			if(result!="success")
			{
				alert("�ʺ�"+result+"����!");
				return false;
			}
			return true;
		}
		
		function validateSumAmount()
		{
			var sumArray = new Array();
			var rows = $('#tt').datagrid('getRows');
			var row;
			var sPayerAcctNo;
			var dAmount;
			var flag = 0;
			var param;
			var result;
			for(var i=0;i<rows.length;i++)
			{
				row = rows[i];
				sPayerAcctNo = row.sPayerAcctNo;
				dAmount = row.dAmount;
				if(sumArray.length==0)
				{
					sumArray[flag] = new Array();
					sumArray[flag][0] = sPayerAcctNo;
					sumArray[flag][1] = parseFloat(dAmount,10);
					flag++;
					
				}
				else
				{	

					for(var j=0;j<sumArray.length;j++)
					{
						if(sumArray[j][0]==sPayerAcctNo)
						{
							sumArray[j][1]=sumArray[j][1]+parseFloat(dAmount,10);
							break;
						}
						if(j>=sumArray.length-1)
						{
							sumArray[flag] = new Array();
							sumArray[flag][0] = sPayerAcctNo;
							sumArray[flag][1] = parseFloat(dAmount,10);
							flag++;
							break;
						}
						
					}
				}
			}
			for(var k=0;k<sumArray.length;k++)
			{

				if(k==0)
				{
					param = "sPayerAcctNo="+sumArray[0][0]+"&dAmount="+sumArray[0][1];
				}else
				{
					param = param + "&sPayerAcctNo="+sumArray[k][0]+"&dAmount="+sumArray[k][1];
				}
			}
			
			
			$.ajax(
				{
					type:'post',
					url:'<%=strContext%>/capital/captransfer/totalAmountValidate.jsp',
					data:param,
					async:false,
					success:function(returnValue){
						var temp = $(returnValue).filter("div#result").text();
						result = temp;
					
					}
				}
			);
			return result;
			
			
		}


		
		
	</script>
</html>
