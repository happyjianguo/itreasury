
(function($) {
	$.suggest = {};
	$.fn.extend({
		suggest : function(p) {
			p = $.suggest.getProperty(p);
			return this.each(function(i, n) {
				// 生成元素
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
				
				// 元素
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
					// 为元素添加事件 keyup
					
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
					.click(function(e) {// 点击放大镜图标
								$(this).parent().prev().children().createPanel(p);
							});
						
					// 为元素添加事件 change
					$(this).change(function(e) {
						var nextTd = $(n).closest('table').find('tr > td:last');
						$.each(p.elements, function(i, element) {
							$("input[type='hidden'][name='"+ element.elName + "']",nextTd).val("");
						});
					}).closest("table")
					.find('tr > td:last > a[class="searchaction"]')
					.click(function(e) {// 点击放大镜图标
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
					$(this).after("<div  style='overflow:hidden;' id='" + $(this).attr("name") + "_panel' title='" + p.title + "放大镜'><table width='100%'></table></div>");
				}
				
				// 计算位置
				var dwidth = $(document).width(); // document的宽
				var dheight = $(document).height(); // document的高

				var browserWidth = document.documentElement.clientWidth;
			    var browserHeight = document.documentElement.clientHeight;
			    
				var wwidth = browserWidth || $(window).width(); // 窗口的宽
				var wheight = browserHeight || $(window).height(); // 窗口的高
				
				var bwidth = $("body").width(); // body的宽
				var bheight = $("body").height(); // body的高
				
				var scrollX = document.documentElement.scrollLeft; //横向滚动条的偏移量
				var scrollY = document.documentElement.scrollTop; //纵向滚动条的偏移量

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
				// 生成panel
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
				

				// 窗口参数
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

				var _sql = eval(p.sql);// 得到SQL语句
				//$.suggest.getSQLString(p.sql);

				// 转换传入的参数
				var _p = {
					form : p.form,
					name : elName,
					colModel : (function(colModel) {
						var names = new Array();
						$.each(colModel, function() {
									names.push(this.name);
								});
						return names;
					})(p.colModel),// 字段名
					colModelType : (function(colModel) {
						var types = new Array();
						$.each(colModel, function() {
									types.push(this.type);
								});
						return types;
					})(p.colModel),// 字段类型
					colModelLink : (function(colModel) {
						var links = new Array();
						$.each(colModel, function() {
									if (this.link) {
										links.push(this.name);
									}
								});
						return links;
					})(p.colModel),// 字段超链接
					elementNames : (function(elements) {
						var elNames = new Array();
						$.each(elements, function() {
									elNames.push(this.elName);
								});
						return elNames;
					})(p.elements),// 页面元素名
					elements : (function(elements) {
						var names = new Array();
						$.each(elements, function() {
									names.push(this.name);
								});
						return names;
					})(p.elements),// 页面元素字段名
					elementsType : (function(elements) {
						var types = new Array();
						$.each(elements, function() {
									types.push(this.type);
								});
						return types;
					})(p.elements),// 页面元素字段类型
					sql : _sql,// sql语句
					nextFocus : p.nextFocus,// 下一焦点
					callback : p.callback
				};

				// 生成grid
				var grid = $("#" + $(this).attr("name") + "_panel > table")
						.flexigridenc({
							isSuggest : true,
							colModel : p.colModel,// 列参数
							searchitems : _searchitems,
							classMethod : '',// 要调用的方法
							page : p.page,
							rpOptions : p.rpOptions,
							rp : p.rp,
							sortname : p.sortname,// 预设指定哪列进行排序
							sortorder : p.sortorder,// 预设排序的方式
							params : _p,
							height : 258,
							isEncode : true,
							printbutton : false,
							exportbutton : false,
							pagestat : '当前&nbsp;{from}~{to}&nbsp;行，共&nbsp;{total}&nbsp;行',// 分页器的显示信息
							nomsg : '没有数据',// 没有数据时的信息,
							panelname:$(this).attr("name")
						});
				// 为窗口绑定事件
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
					title : '放大镜',// 窗口名称
					colModel : [],// 窗口属性
					elements : [],// 页面元素属性
					offset : 'bottom',// 窗口显示位置。包括“bottom”和“left”两种
					width : 600,// 窗口高度
					height : 400,// 窗口宽度
					sql : '',// sql语句
					page : 1,// 起始页
					rpOptions : [10, 25, 50, 100, 200],// 每页行数配置
					rp : 10,// 默认每页行数
					sortname : '',// 预设指定哪列进行排序
					sortorder : '',// 预设排序的方式
					nextFocus : '',// 下一焦点
					callback : false,
					type:''  //输入框类型
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
			//如果有ID
			if ($(form).find("#" + this.name).length > 0) {
				$.each($(form).find("#" + this.name), function() {
					
					var _tagName = this.tagName;
					if (_tagName == "INPUT" || _tagName == "SELECT") {//如果是input和select
						$(this).val(n.value);
					} else {
						$(this).text(n.value);
					}
				});
			} else {//没有ID则寻找名字
				$.each($(form).find("*[name='" + this.name + "']"), function() {
					var _tagName = this.tagName;
					if (_tagName == "INPUT" || _tagName == "SELECT") {//如果是input和select
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