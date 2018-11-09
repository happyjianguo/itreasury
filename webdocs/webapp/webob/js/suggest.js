
(function($) {
	$.suggest = {};
	$.fn.extend({
		suggest : function(p) {
			p = $.suggest.getProperty(p);
			return this.each(function(i, n) {
				// ����Ԫ��
				if(p.type=="textarea")
				{
					$(this).wrap('<tr><td></td></tr>');
					$(this).parent().parent().wrap('<table cellpadding="0" cellspacing="0" class="tab_search"></table>');
					$(this).parent().after('<td><a class="textareaaction" name="magnifier"/></td>');
					
				}else
				{
					$(this).wrap('<td class="control"></td>');
					$(this).parent().wrap('<tr class="show"></tr>');
					$(this).parent().parent().wrap('<table cellpadding="0" cellspacing="0" class="tab_search"></table>');
					$(this).parent().after('<td class="control"><a class="searchaction" name="magnifier"/></td>');
				}
				
				// Ԫ��
				if (p.elements && p.elements.length > 0) {
					$.each(p.elements, function(i, element) {
								element = $.extend({
											name : "",
											type : $.suggest.Type.STRING,
											elName : "",
											elType : "",
											value : ""
										}, element);
	
								if(element.elType == "hidden")
								{
									if ($("input[name='"+element.elName+"']").length == 0) {
										$(n).closest('table').find('tr > td:last')
											.append('<input type="hidden" name="' + element.elName + '" value="'+element.value+'"/>');
									}
									else
									{
										if(element.value!="")
										{
											$("input[name='"+element.elName+"']").val(element.value);
										}
	
									}
								}
							});

				}
				
				if ($(this).attr("disabled") != true
						&& $(this).attr("readonly") != true) {
					// ΪԪ������¼� keyup
					
					$(this).keydown(function(e) {
						if (e.keyCode == 13) {// Enter
							$(this).createPanel(p);
						}
						if (e.keyCode == 27) {// Esc
							$("#" + $(this).attr("name") + "_panel")
									.dialog("close");
						}
						
					}).closest("table")
					.find('tr > td:last > a[class="searchaction"]')
					.click(function(e) {// ����Ŵ�ͼ��
								$(this).parent().prev().children().createPanel(p);
							});
						
					// ΪԪ������¼� change
					$(this).change(function(e) {
						var nextTd = $(n).closest('table').find('tr > td:last');
						$.each(p.elements, function(i, element) {
							$("input[type='hidden'][name='"+ element.elName + "']",nextTd).val("");
						});
					}).closest("table")
					.find('tr > td:last > a[class="searchaction"]')
					.click(function(e) {// ����Ŵ�ͼ��
								$(this).parent().prev().children().createPanel(p);
							});
							
					$(this).closest("table").find('tr > td:last > a[class="textareaaction"]').click(function(e){
								$(this).parent().prev().children().createPanel(p);
							});
				}
			});
		},
		createPanel : function(p) {
			return this.each(function(i, n) {
				if ($("#" + $(this).attr("name") + "_panel").length == 0) {
					$(this).after("<div  style='overflow:hidden;' id='" + $(this).attr("name") + "_panel' title='" + p.title + "�Ŵ�'><table width='100%'></table></div>");
				}
				
				// ����λ��
				var dwidth = $(document).width(); // document�Ŀ�
				var dheight = $(document).height(); // document�ĸ�

				var browserWidth = document.documentElement.clientWidth;
			    var browserHeight = document.documentElement.clientHeight;
			    
				var wwidth = browserWidth || $(window).width(); // ���ڵĿ�
				var wheight = browserHeight || $(window).height(); // ���ڵĸ�
				
				var bwidth = $("body").width(); // body�Ŀ�
				var bheight = $("body").height(); // body�ĸ�
				
				var scrollX = document.documentElement.scrollLeft; //�����������ƫ����
				var scrollY = document.documentElement.scrollTop; //�����������ƫ����

				//var suggestTable = $(this).parent().parent().parent().parent();
				var suggestTable = $(this).closest("table[class=tab_search]")
				
				var _x=_y=0;
				if (p.offset && p.offset == 'bottom') {
					//x
					_x = $(suggestTable).offset().left;
					//y
					_y =  $(suggestTable).offset().top + $(suggestTable).height() + 4 - scrollY;
				}
				else if(p.offset && p.offset == 'top')
				{
					//x
					_x = $(suggestTable).offset().left;
					//y
					_y = $(suggestTable).offset().top - scrollY - p.height - 8;
				}
				var elName = $(this).attr("name");
				// ����panel
				$.ui.dialog.defaults.bgiframe = true;
				var panel = $("#" + elName + "_panel").dialog({
							width : p.width,
							minWidth : p.width,
							height : p.height,
							minHeight : p.height,
							position : [_x,_y],
							bgiframe : true,
							modal : true,
							resizable : false,
							close : function(event, ui) {
								$("#" + elName + "_panel").remove();
							}
						});
				

				// ���ڲ���
				var _searchitems = new Array();
				if (p.colModel && p.colModel.length > 0) {
					$.each(p.colModel, function(i, n) {
								n = $.extend({
											display : "",
											name : "",
											type : $.suggest.Type.STRING,
											width : 100,
											sortable : true,
											align : 'center',
											link : false
										}, n);
								_searchitems.push({
											display : n.display,
											name : n.name
										});
							});
				}

				var _sql = eval(p.sql);// �õ�SQL���
				//$.suggest.getSQLString(p.sql);

				// ת������Ĳ���
				var _p = {
					form : p.form,
					name : elName,
					colModel : (function(colModel) {
						var names = new Array();
						$.each(colModel, function() {
									names.push(this.name);
								});
						return names;
					})(p.colModel),// �ֶ���
					colModelType : (function(colModel) {
						var types = new Array();
						$.each(colModel, function() {
									types.push(this.type);
								});
						return types;
					})(p.colModel),// �ֶ�����
					colModelLink : (function(colModel) {
						var links = new Array();
						$.each(colModel, function() {
									if (this.link) {
										links.push(this.name);
									}
								});
						return links;
					})(p.colModel),// �ֶγ�����
					elementNames : (function(elements) {
						var elNames = new Array();
						$.each(elements, function() {
									elNames.push(this.elName);
								});
						return elNames;
					})(p.elements),// ҳ��Ԫ����
					elements : (function(elements) {
						var names = new Array();
						$.each(elements, function() {
									names.push(this.name);
								});
						return names;
					})(p.elements),// ҳ��Ԫ���ֶ���
					elementsType : (function(elements) {
						var types = new Array();
						$.each(elements, function() {
									types.push(this.type);
								});
						return types;
					})(p.elements),// ҳ��Ԫ���ֶ�����
					sql : _sql,// sql���
					nextFocus : p.nextFocus,// ��һ����
					callback : p.callback
				};

				// ����grid
				var grid = $("#" + $(this).attr("name") + "_panel > table")
						.flexigridenc({
							isSuggest : true,
							colModel : p.colModel,// �в���
							searchitems : _searchitems,
							classMethod : '',// Ҫ���õķ���
							page : p.page,
							rpOptions : p.rpOptions,
							rp : p.rp,
							sortname : p.sortname,// Ԥ��ָ�����н�������
							sortorder : p.sortorder,// Ԥ������ķ�ʽ
							params : _p,
							height : 258,
							isEncode : true,
							printbutton : false,
							exportbutton : false,
							pagestat : '��ǰ&nbsp;{from}~{to}&nbsp;�У���&nbsp;{total}&nbsp;��',// ��ҳ������ʾ��Ϣ
							nomsg : 'û������',// û������ʱ����Ϣ,
							panelname:$(this).attr("name")
						});
				// Ϊ���ڰ��¼�
				$(panel).bind('resize', function(event, ui) {
					$(grid).parent().parent().css("width",
							$(this).dialog('option', 'width') - 25);
				});
			});
		}
	});

	$.suggest.getProperty = function(p) {
		return $.extend({
					form : '',
					title : '�Ŵ�',// ��������
					colModel : [],// ��������
					elements : [],// ҳ��Ԫ������
					offset : 'bottom',// ������ʾλ�á�������bottom���͡�left������
					width : 600,// ���ڸ߶�
					height : 400,// ���ڿ��
					sql : '',// sql���
					page : 1,// ��ʼҳ
					rpOptions : [10, 25, 50, 100, 200],// ÿҳ��������
					rp : 10,// Ĭ��ÿҳ����
					sortname : '',// Ԥ��ָ�����н�������
					sortorder : '',// Ԥ������ķ�ʽ
					nextFocus : '',// ��һ����
					callback : false,
					type:''  //���������
				}, p);
	};
	
	$.suggest.fillForm = function(p) {
		p = $.extend({
					form : "",
					name : "",
					el : [],
					nextFocus : "",
					callback : false
				}, p);
		var form = $("form[name='" + p.form + "']");
		$.each(p.el, function(i,n) {
			//�����ID
			if ($(form).find("#" + this.name).length > 0) {
				$.each($(form).find("#" + this.name), function() {
					
					var _tagName = this.tagName;
					if (_tagName == "INPUT" || _tagName == "SELECT") {//�����input��select
						$(this).val(n.value);
					} else {
						$(this).text(n.value);
					}
				});
			} else {//û��ID��Ѱ������
				$.each($(form).find("*[name='" + this.name + "']"), function() {
					var _tagName = this.tagName;
					if (_tagName == "INPUT" || _tagName == "SELECT") {//�����input��select
						$(this).val(n.value);
					} else {
						$(this).text(n.value);
					}
				});
			}
		});

		$("#" + p.name + "_panel").remove();
		// $("#" + p.name + "_panel").dialog("close");
		
		if(p.callback && p.callback!="false") {
			//eval(p.callback)(p.el);
			eval(p.callback);
		}

		if (p.nextFocus) {
			if ($(form).find("*[name='" + p.nextFocus + "']").length > 0)
//				$(form).find("*[name='" + p.nextFocus + "']").focus();
				setTimeout(function(){$(form).find("*[name='" + p.nextFocus + "']").focus();},100);
		}
	};
	
	$.suggest.getSQLString = function(f) {
		var leftBk = f.indexOf('(');
		var rightBk = f.indexOf(')');
		var s = f.substring(leftBk + 1 , rightBk);
		var s1 = '';
		if(s.length > 0 && $.trim(s) != '')
		{
			var ss = s.split(',');
			for(var i=0; i<ss.length; i++)
			{
				if(ss[i].toLowerCase().indexOf('.value') >= 0)
				{
					ss[i] = '""';
				}
				s1 = s1 + ss[i] + ',';
			}
			s1 = s1.substring(0, s1.length - 1);
			
			f = f.substring(0, leftBk + 1) + s1 + ')';
		}
		return f;
	};

	$.suggest.Type = {
		STRING : 1,
		DATE : 2,
		DATETIME : 3,
		AMOUNT_2 : 4,
		AMOUNT_6 : 5,
		LONG : 6,
		DOUBLE : 7,
		ARRAY : 8,
		LIST : 9,
		MAP : 10,
		FUNCTION : 11
	};

})(jQuery);