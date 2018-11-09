/**
 * ���ҵ��ϵͳ����flexigrid�����˶��η�װ jiamiao 20090728 iss
 * 
 * 
 * colModel����������
 * 
 * colModel : [ {display: '���', name : 'id', width : 50, sortable : false,
 * align: 'left'}, {display: '����', name : 'papTitle', width : 150, sortable :
 * true, align: 'left'}, {display: '����', name : 'papAuthor', width : 100,
 * sortable : true, align: 'left'}, {display: '�ڿ�', name : 'papJourney', width :
 * 100, sortable : true, align: 'left'}, {display: '��������', name : 'papPubtime',
 * width : $(window).width()-462, sortable : true, align: 'left'} ],
 * 
 */
(function($) {
	var docloaded = false;

	$(document).ready(function() {
		docloaded = true;
	});

	$.fn.extend({
		flexigridenc : function(p) {
			p = $.extend({
				colModel : [],// �в���
				params : [],//����
				searchitems : false,//��ѯ����
				buttons : false,// ��ť
				rpOptions : [10, 15, 20, 25, 50, 100],
				title : '',// ��ͷ������
				classMethod : '',// Ҫ���õķ���
				usepager : true,//�Ƿ����÷�ҳ
				rp : 10,// Ԥ��ÿҳ����
				page : 1,// Ԥ�赱ǰҳ��
				sortname : '',// Ԥ��ָ�����н�������
				sortorder : '',// Ԥ������ķ�ʽ
				procmsg : '���ݶ�ȡ��,���Ժ�...',// ��ȡʱ����Ϣ
				pagestat : '��ʾ�� {from} �� {to} �����ݣ����� {total} ������',// ��ҳ������ʾ��Ϣ
				nomsg : '�Ҳ�����������������',// û������ʱ����Ϣ
				width : $(this).parent().width() - 2 || $(this).width() - 2 || 'auto',
				height : 328, // ���߶�(ע�ⲻ��ʹ��100%֮����ַ���)
				adaptiveHeight : true, //����Ӧ�߶�
				isSuggest : false, //�Ƿ�Ϊ�Ŵ�
				tableId : 'flexlist', //Ĭ�ϵ�tableId����
				userFunc : false,//�ύǰ���õķ���
				query: '', //��ѯ
				qtype: '', //��ѯ��Ӧ���ֶ�
				callback : '', //���ݼ������ʱ�Զ��巽��
				slider : false, //�Ƿ���ʾ����
				printbutton : true,
				exportbutton : true,
				isEncode : false,
				resizable : false,
			 	paramCache:true,
			 	colDrag : true, //�Ƿ������ק��ͷ
			 	submitSuccessFunc:'',
			 	submitErrorFunc:''
				}, p);
				if(p.isSuggest == false)
				{
					var indent = 0;//����ֵ 
					if($.browser.msie) {
						if($.browser.version == 6.0 || $.browser.version == 7.0) {
							indent = 50;
						}
						else if($.browser.version == 8.0) {
							indent = 100;
						}
					}
					
					var _tableId = $(this).attr("id") || p.tableId;
					if(_tableId)
					{
						if($("#" + _tableId).size() > 0) {
							$("#" + _tableId).parent().resize(function(){
								$("#" + _tableId).parent().parent().width(document.documentElement.clientWidth - indent);
							});
						}
						else {
							$(this).parent().resize(function(){
								$(this).parent().parent().width(document.documentElement.clientWidth - indent);
							});
						}
					}
					else {
						$(this).parent().resize(function(){
							$(this).parent().parent().width(document.documentElement.clientWidth - indent);
						});
					}
				}
			
				if (!docloaded) {
					$(document).ready(function() {
						$.flexme(this, p);
					});
				} else {
					$.flexme(this, p);
				}
				
				return this;
		},
		serializeArray4Flex : function(p) {
			p = $.extend({
						isEncode : false
					}, p);
			return this.map(function() {
						return this.elements
								? $.makeArray(this.elements)
								: this;
					}).filter(function() {
				return this.name
						&& !this.disabled
						&& (this.checked
								|| /select|textarea/i.test(this.nodeName) || /text|hidden|password|search/i
								.test(this.type));
			}).map(function(i, elem) {
				var val = $(this).val();
				if (p.isEncode) {
					val = encodeURIComponent(val);
				}
				
				return val == null ? null : $.isArray(val) ? $.map(val,
						function(val, i) {
							if (!(elem.name == "rp" || elem.name == "strClassMethod")) {
								return {
									name : elem.name,
									value : val
								};
							}
						}) : (!(elem.name == "rp" || elem.name == "strClassMethod")) ? {
					name : elem.name,
					value : val
				} : null;
			}).get();
		}
	});
	
	/**
	 * ���η�װ��flexigridʵ�֡���������Ի�����
	 */
	$.flexme = function(t, p) {
		
		var _params = new Array();
		for (var param in p.params) {
			_params.push({
						name : param,
						value : encodeURIComponent(p.params[param])
					});
		}		

		//alert(_params_jasper);
		
		//���ɴ�ӡ�͵�����ť
		if(p.printbutton || p.exportbutton) {
			p.buttons = new Array();
			if(p.printbutton){
				p.buttons.push({
					name : '��ӡ',
					bclass : 'print',
					onpress : function(com, grid) {
						if ($("#printpanel").length == 0)
						{
							var dialogHtml = '';
							dialogHtml += '<div id="printpanel" title="��ӡ">';
							dialogHtml += '<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"><tbody>';
							dialogHtml += '<tr height="30">';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="20%" align="right">ҳ��߾ࣺ</td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="65%" align="left">����&nbsp;<select name="pageTop"><option value="20" selected>20px</option><option value="30">30px</option><option value="40">40px</option><option value="50">50px</option><option value="60">60px</option></select>&nbsp;&nbsp;&nbsp;&nbsp;�����&nbsp;<select name="pageLeft"><option value="5" >5px</option><option value="10">10px</option><option value="15">15px</option><option value="20" selected>20px</option><option value="25">25px</option></select></td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '</tr>';
							dialogHtml += '<tr height="30">';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="20%" align="right"></td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="65%" align="left">�ײ�&nbsp;<select name="pageButtom"><option value="20" selected>20px</option><option value="30">30px</option><option value="40">40px</option><option value="50">50px</option><option value="60">60px</option></select>&nbsp;&nbsp;&nbsp;&nbsp;�Ҷ���&nbsp;<select name="pageRight"><option value="5" >5px</option><option value="10">10px</option><option value="15">15px</option><option value="20" selected>20px</option><option value="25">25px</option></select></td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '</tr>';
							dialogHtml += '<tr height="30">';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="20%" align="right">��ӡ���ݣ�</td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="65%" align="left"><input type="radio" name="pageChoose" value="this" checked="true"/>��ǰҳ&nbsp;&nbsp;<input type="radio" name="pageChoose" value="all"/>����ҳ</td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '</tr>';
							dialogHtml += '<tr height="30">';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="20%" align="right">��ӡ����</td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="65%" align="left"><input type="radio" name="pageType" value="1" checked="true"/>����&nbsp;&nbsp;<input type="radio" name="pageType" value="2"/>����</td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '</tr>';
							dialogHtml += '</tbody></table></div>';
							$('body').prepend(dialogHtml);
						}

						$("#printpanel").dialog({
							bgiframe : true,
							resizable: false,
							width : 400,
							modal : true,
							buttons : {
								"�ر�" : function() {
									$(this).dialog('close');
								},
								"ȷ��" : function() {
									var pageType = $('input[type="radio"][name="pageType"][checked="true"]', this).val();
									var pageSize = $('select[name="pageSize"]', this).val();
									var pageTop = $('select[name="pageTop"]', this).val();
									var pageLeft = $('select[name="pageLeft"]', this).val();
									var pageButtom = $('select[name="pageButtom"]', this).val();
									var pageRight = $('select[name="pageRight"]', this).val();
									var pageChoose = $('input[type="radio"][name="pageChoose"][checked="true"]', this).val();
									
									var idsArray = new Array();
									var titleArray = new Array();
									var tdWidthArray = new Array();
									var elTypeArray = new Array();
									
									$.each($('.hDiv > .hDivBox > table > thead > tr > th', grid), function(i, n) {
										if($('div:visible',this).size() > 0){
											
											if($('div:visible', this).parent().data('cm').elType == null){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												elTypeArray.push('String');
											}
											else if($('div:visible', this).parent().data('cm').elType == 'link'){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												elTypeArray.push('link');
											}
											else if($('div:visible', this).parent().data('cm').elType == 'rownum'){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												elTypeArray.push('rownum');
											}
											else if($('div:visible', this).parent().data('cm').elType == 'checkbox'){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												elTypeArray.push('checkbox');												
											}
										}
									});

									var _p = $(".hDiv > .hDivBox > table > thead > tr > th > div:visible",grid).parent().data("p");
									
									//alert($.param(_p.params));
									
									//------------------------------------begin
									$(this).dialog('close');
									//showSending();
									
									var tempForm = document.createElement("form");
									tempForm.action = '/NASApp/JasperPrintServlet';
									tempForm.method = "post";
									tempForm.target = "_blank";
									$(tempForm).attr("id","tempPrintForm");
									
									$(tempForm).append("<input type='hidden' name='strClassMethod' value='"+ p.classMethod +"'/>");
									$(tempForm).append("<input type='hidden' name='usepager' value='"+ p.usepager +"'/>");
									$(tempForm).append("<input type='hidden' name='pageType' value='"+ pageType +"'/>");
									$(tempForm).append("<input type='hidden' name='pageTop' value='"+ pageTop +"'/>");
									$(tempForm).append("<input type='hidden' name='pageButtom' value='"+ pageButtom+"'/>");
									$(tempForm).append("<input type='hidden' name='pageLeft' value='"+ pageLeft +"'/>");
									$(tempForm).append("<input type='hidden' name='pageRight' value='"+ pageRight +"'/>");
									$(tempForm).append("<input type='hidden' name='pageChoose' value='"+ pageChoose +"'/>");
									$(tempForm).append("<input type='hidden' name='ids' value='"+ idsArray.join(',') +"'/>");
									$(tempForm).append("<input type='hidden' name='tdWidths' value='"+ tdWidthArray.join(',') +"'/>");
									$(tempForm).append("<input type='hidden' name='titles' value='"+ titleArray.join(',') +"'/>");
									$(tempForm).append("<input type='hidden' name='elTypes' value='"+ elTypeArray.join(',')+"'/>");
									$(tempForm).append("<input type='hidden' name='page' value='"+ _p.page +"'/>");
									$(tempForm).append("<input type='hidden' name='sortname' value='"+ _p.sortname+"'/>");
									$(tempForm).append("<input type='hidden' name='sortorder' value='"+ _p.sortorder+"'/>");
									$(tempForm).append("<input type='hidden' name='rp' value='"+ _p.rp+"'/>");
									$(tempForm).append("<input type='hidden' name='tableTitle' value='"+ p.title+"'/>");
									var parameters=_p.params;
								 	for(var x=0;x<parameters.length;x++){		
								 		$(tempForm).append("<input type='hidden' name='"+parameters[x].name+"' value='"+decodeURIComponent(parameters[x].value)+"'/>");
								 	}
								 	
									$('body').append($(tempForm));
									
									$(tempForm).submit();
								}
							},
							close : function() {
								$("#printpanel").remove();
							}
						});
					}
				});
				p.buttons.push({separator: true});
			}
			if(p.exportbutton){
				p.buttons.push({
					name : '����',
					bclass : 'export',
					onpress : function(com, grid) {
					
						if ($("#exportpanel").length == 0)
						{
							var dialogHtml = '';
							dialogHtml += '<div id="exportpanel" title="����">';
							dialogHtml += '<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"><tbody>';
							dialogHtml += '<tr height="30">';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="20%" align="right">�������ݣ�</td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '<td width="65%" align="left"><input type="radio" name="pageChoose" value="this" checked="true"/>��ǰ����&nbsp;&nbsp;<input type="radio" name="pageChoose" value="all"/>��������</td>';
							dialogHtml += '<td width="5%"></td>';
							dialogHtml += '</tr>';
							dialogHtml += '</tbody></table></div>';
							$('body').prepend(dialogHtml);
						}
					
						$("#exportpanel").dialog({
							bgiframe : true,
							resizable: false,
							width : 400,
							modal : true,
							buttons : {
								"�ر�" : function() {
									$(this).dialog('close');
								},
								"ȷ��" : function() {
									var pageChoose = $('input[type="radio"][name="pageChoose"][checked="true"]', this).val();
									
									var idsArray = new Array();
									var titleArray = new Array();
									var tdWidthArray = new Array();
									var elTypeArray = new Array();
									
									$.each($('.hDiv > .hDivBox > table > thead > tr > th', grid), function(i, n) {
										if($('div:visible',this).size() > 0){											
											if($('div:visible', this).parent().data('cm').elType == null){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												//tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												tdWidthArray.push($('div:visible', this).css("width").replace("px",""));
												elTypeArray.push('String');												
											}
											else if($('div:visible', this).parent().data('cm').elType == 'link'){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												//tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												tdWidthArray.push($('div:visible', this).css("width").replace("px",""));
												elTypeArray.push('link');												
											}
											else if($('div:visible', this).parent().data('cm').elType == 'rownum'){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												//tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												tdWidthArray.push($('div:visible', this).css("width").replace("px",""));
												elTypeArray.push('rownum');												
											}
											else if($('div:visible', this).parent().data('cm').elType == 'checkbox'){
												idsArray.push($(this).attr('idx'));
												titleArray.push($('div:visible', this).text());
												//tdWidthArray.push($('div:visible', this).parent().data('cm').width);
												tdWidthArray.push($('div:visible', this).css("width").replace("px",""));
												elTypeArray.push('checkbox');												
											}
										}
									});

									var _p = $(".hDiv > .hDivBox > table > thead > tr > th > div:visible",grid).parent().data("p");
									
									$(this).dialog('close');
									
									$.post('/NASApp/AjaxServlet?strIsEncode=true&strIsExcel=true&' + $.param(_p.params)+"&export=true",
									{
										strUsePager:p.usepager,
										strClassMethod : p.classMethod, //ִ�з���
										pageChoose : pageChoose, //��ӡ����
										
										//params : $.param(_p.params), //����
										ids : idsArray.join(','), //IDs
										tdWidths : tdWidthArray.join(','), //�п�
										titles : encodeURIComponent(titleArray.join(',')), // ����
										elTypes:elTypeArray.join(','),
										
										page : _p.page, // Ԥ���ÿҳ����
										sortname : _p.sortname, // Ԥ��ָ�����н�������
										sortorder : _p.sortorder, // Ԥ������ķ�ʽ
										rp : _p.rp // resultsperpage,Ԥ��ķ�ҳ��С

										//colModel : p.colModel,
										//dataType : 'json' // �ش����ݵ�����,����Ϊxml��json
									},function(data)
									{	
										window.location.href('/NASApp/DownLoadFileServlet?file=' + data);
									});
								}
							},
							close : function() {
								$("#exportpanel").remove();
							}
						});
					}
				});
				p.buttons.push({separator: true});
			}
		}
		var contextUrl = '/NASApp/AjaxServlet?strUsePager='+p.usepager+'&strIsEncode=true&strClassMethod=' + p.classMethod;
		var grid = $(t).flexigrid({
			colModel : p.colModel,
			searchitems : p.searchitems,//��ѯ����
			buttons : p.buttons,
			url : contextUrl,
			method : 'POST',// ���ݴ���ģʽ
			dataType : 'json',// �ش����ݵ�����,����Ϊxml��json
			rp : p.rp,// resultsperpage,Ԥ��ķ�ҳ��С
			newp : p.page,// Ԥ���ÿҳ����
			sortname : p.sortname,// Ԥ��ָ�����н�������
			sortorder : p.sortorder,// Ԥ������ķ�ʽ
			usepager : p.usepager,// �Ƿ����÷�ҳ��
			title : p.title,// ����
			useRp : true,// ʹ�÷�ҳ��Сѡ����
			striped : true,// ��ż����ɫ�໥����,������Ĭ��Ϊtrue
			showTableToggleBtn : true,// ��ʾ��ر�������λ�Ŀ�����
			width : p.width,
			height : p.height,// ���߶�(ע�ⲻ��ʹ��100%֮����ַ���)
			adaptiveHeight : p.adaptiveHeight,//����Ӧ�߶�
			rpOptions : p.rpOptions,
			resizable : p.resizable,// �Ƿ�ɵ����б��ڴ�С,������Ĭ��Ϊtrue
			onSubmit : p.userFunc,// �����ύʱ���õ��Զ��庯��
			nowrap : true,// �Ƿ��ص�,trueΪ�������ص�,flaseΪ�����ص�
			procmsg : p.procmsg,// ��ȡʱ����Ϣ
			pagestat : p.pagestat,// ��ҳ������ʾ��Ϣ
			nomsg : p.nomsg,// û������ʱ����Ϣ
			isSuggest : p.isSuggest,//�Ƿ�Ϊ�Ŵ�
			params : _params, //����
			query: p.query,//��ѯ
			qtype: p.qtype,//��ѯ��Ӧ���ֶ�
			callback : p.callback,//���ݼ������ʱ�Զ��巽��
			slider : p.slider,
			isEncode : p.isEncode,
			paramCache:p.paramCache,
			colDrag : p.colDrag,
			submitSuccessFunc:p.submitSuccessFunc,
			submitErrorFunc:p.submitErrorFunc
		});
	};

	$.ajaxSubmit= function(tableID) {
		$("#" + tableID).flexOptions({
					newp : 1
				}).ajaxSubmit();
		return false;
	}
	$.gridReload = function(tableID) {
		$("#" + tableID).flexOptions({
					newp : 1
				}).flexReload();
		return false; 
	};
	
	$.gridReloadMe = function(tableID,page) {
		$("#" + tableID).flexOptions({newp : page}).flexReload();
		return false;
	};

	$.addFormData = function(formID, tableID) {
		if($("#" + formID).size() == 0) {
			if($("form[name='"+formID+"']").size() > 0) {
				$("form[name='"+formID+"']").attr("id",formID);
			}
			else {
				alert("�޷�ͨ��" + formID + "�ҵ�form����Ϊ�����id��name");
			}
		}
		var dt = $("#" + formID).serializeArray4Flex({
					isEncode : true
				});
		$("#" + tableID).flexOptions({
					params : dt
				});
		return true;
	}
	
	/**
	 * Ϊҳ���ϵ�ĳԪ�ظ�ֵ
	 */
	$.fn.setValue = function(value)
	{
		if (!docloaded) {
			var t = this;
			$(document).ready(function() {
				if(value && value!="" && value!="-1") {
					$(t).val(value);
				}
			});
		} else {
			if(value && value!="" && value!="-1") {
				$(this).val(value);
			}
		}
	};
	
	$.flexPost = function(url, data, callback){
		$.post(
			url, data, function(data){callback(eval(data));}, "script"
		);
	};

	$.Message = {
		setData : function(data, message) {
			return {
				strResult: data,
				strMessage: message
			};
		}
	};
	
	$(function(){
		$(".hiflex").flexigrid({
			showTableToggleBtn: true,
			useRp: true
		});
	});
	
})(jQuery);