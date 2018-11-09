/**
 * 针对业务系统。对flexigrid进行了二次封装 jiamiao 20090728 iss
 * 
 * 
 * colModel的配置如下
 * 
 * colModel : [ {display: '序号', name : 'id', width : 50, sortable : false,
 * align: 'left'}, {display: '题名', name : 'papTitle', width : 150, sortable :
 * true, align: 'left'}, {display: '作者', name : 'papAuthor', width : 100,
 * sortable : true, align: 'left'}, {display: '期刊', name : 'papJourney', width :
 * 100, sortable : true, align: 'left'}, {display: '发表日期', name : 'papPubtime',
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
				colModel : [],// 列参数
				params : [],//参数
				searchitems : false,//查询窗口
				buttons : false,// 按钮
				rpOptions : [10, 15, 20, 25, 50, 100],
				title : '',// 表头的名字
				classMethod : '',// 要调用的方法
				usepager : true,//是否启用分页
				rp : 10,// 预设每页行数
				page : 1,// 预设当前页数
				sortname : '',// 预设指定哪列进行排序
				sortorder : '',// 预设排序的方式
				procmsg : '数据读取中,请稍候...',// 读取时的信息
				pagestat : '显示从 {from} 到 {to} 条数据，共有 {total} 条数据',// 分页器的显示信息
				nomsg : '找不到符合条件的数据',// 没有数据时的信息
				width : $(this).parent().width() - 2 || $(this).width() - 2 || 'auto',
				height : 328, // 表格高度(注意不能使用100%之类的字符串)
				adaptiveHeight : true, //自适应高度
				isSuggest : false, //是否为放大镜
				tableId : 'flexlist', //默认的tableId名称
				userFunc : false,//提交前调用的方法
				query: '', //查询
				qtype: '', //查询对应的字段
				callback : '', //数据加载完成时自定义方法
				slider : false, //是否显示滑动
				printbutton : true,
				exportbutton : true,
				isEncode : false,
				resizable : false,
			 	colDrag : false //是否可以拖拽列头
				}, p);
				
				if(p.isSuggest == false)
				{
					var indent = 0;//缩进值 
					if($.browser.msie) {
						if($.browser.version == 6.0 || $.browser.version == 7.0) {
							indent = 32;
						}
						else if($.browser.version == 8.0) {
							indent = 2;
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
	 * 二次封装的flexigrid实现。可满足个性化需求
	 */
	$.flexme = function(t, p) {
		
		var _params = new Array();
		for (var param in p.params) {
			_params.push({
						name : param,
						value : encodeURIComponent(p.params[param])
					});
		}
		
		var contextUrl = '/NASApp/iTreasury-ebank/MagnifierQuery';
		var grid = $(t).flexigrid({
			colModel : p.colModel,
			searchitems : p.searchitems,//查询窗口
			buttons : p.buttons,
			url : contextUrl,
			method : 'POST',// 数据传送模式
			dataType : 'json',// 回传数据的类型,可以为xml和json
			rp : p.rp,// resultsperpage,预设的分页大小
			newp : p.page,// 预设的每页行数
			sortname : p.sortname,// 预设指定哪列进行排序
			sortorder : p.sortorder,// 预设排序的方式
			usepager : p.usepager,// 是否启用分页器
			title : p.title,// 标题
			useRp : true,// 使用分页大小选择器
			striped : true,// 奇偶行颜色相互交叉,不设置默认为true
			showTableToggleBtn : true,// 显示或关闭隐藏栏位的开启器
			width : p.width,
			height : p.height,// 表格高度(注意不能使用100%之类的字符串)
			adaptiveHeight : p.adaptiveHeight,//自适应高度
			rpOptions : p.rpOptions,
			resizable : p.resizable,// 是否可调整列表窗口大小,不设置默认为true
			onSubmit : p.userFunc,// 数据提交时调用的自定义函数
			nowrap : false,// 是否重叠,true为不启用重叠,flase为启用重叠
			procmsg : p.procmsg,// 读取时的信息
			pagestat : p.pagestat,// 分页器的显示信息
			nomsg : p.nomsg,// 没有数据时的信息
			isSuggest : p.isSuggest,//是否为放大镜
			params : _params, //参数
			query: p.query,//查询
			qtype: p.qtype,//查询对应的字段
			callback : p.callback,//数据加载完成时自定义方法
			slider : p.slider,
			isEncode : p.isEncode,
			colDrag : p.colDrag,
			panelname:p.panelname
		});
	};

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
				alert("无法通过" + formID + "找到form。请为其添加id和name");
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
	 * 为页面上的某元素赋值
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