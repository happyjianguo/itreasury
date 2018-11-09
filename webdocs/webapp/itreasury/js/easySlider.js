/*
 * 	Easy Slider - jQuery plugin
 *	written by Alen Grakalic	
 *	http://cssglobe.com/post/3783/jquery-plugin-easy-image-or-content-slider
 *
 *	Copyright (c) 2009 Alen Grakalic (http://cssglobe.com)
 *	Dual licensed under the MIT (MIT-LICENSE.txt)
 *	and GPL (GPL-LICENSE.txt) licenses.
 *
 *	Built for jQuery library
 *	http://jquery.com
 *
 */
 
/*
 *	markup example for $("#images").easySlider();
 *	
 * 	<div id="images">
 *		<ul>
 *			<li><img src="images/01.jpg" alt="" /></li>
 *			<li><img src="images/02.jpg" alt="" /></li>
 *			<li><img src="images/03.jpg" alt="" /></li>
 *			<li><img src="images/04.jpg" alt="" /></li>
 *			<li><img src="images/05.jpg" alt="" /></li>
 *		</ul>
 *	</div>
 *
 */

(function($) {

	$.fn.easySlider = function(options){
	  
		// default configuration properties
		var defaults = {
			sliderText: 	'',
			sliderConext:	'',
			eventModel:     0,
			currencyID:     0,
			speed:          400			
		}; 
		
		var options = $.extend(defaults, options);
		
		return this.each(function() {
			obj = $(this);
			//var eventNum = 0;
			
			var menuTest;
			var menuModel;
			var menuUrl;
			if(options.sliderText != ""){
				var sliderTexts = options.sliderText.split("::");
				menuTest = sliderTexts[0].split(";;");
				menuModel = sliderTexts[1].split(";;");
				menuUrl = sliderTexts[2].split(";;");
				
				$(obj).append("<ul></ul>");
				for(var i=0; i<menuTest.length; i++){
					if(options.eventModel == menuModel[i]){
						$("ul", obj).append("<li><div id='slidertover' class='menucol_event'>"+ menuTest[i] +"</div></li>");
						//eventNum = i;
					}
					else{
						$("ul", obj).append("<li><div class='menucol'>"+ menuTest[i] +"</div></li>");
					}
				}
			}
			
			var s = $("li", obj).length;
			var w_li = obj.width();
			var w_ul = s*w_li;
			
			obj.css('width', obj.parents().width());
			var w = obj.width(); 
			var h = obj.height();
			var disnum = Math.floor(w/w_li);
			var ts = s-disnum;
			var t = 0;

			$("ul", obj).css('width',w_ul);			
			$("li", obj).css('float','left');

			
			$("div", obj).mouseover(function(){
				if($(this).attr("id") != "slidertover"){
					$(this).removeClass("menucol");
					$(this).addClass("menucol_tover");
				}
			});
			
			$("div", obj).mouseout(function(){
				if($(this).attr("id") != "slidertover"){
					$(this).removeClass("menucol_tover");
					$(this).addClass("menucol");
				}
			});
			
			$("div", obj).click(function(){		
				//alert($(event.srcElement));
				//alert(options.sliderConext + menuUrl[$("div", obj).index($(event.srcElement))]);
				window.location.href = options.sliderConext + menuUrl[$("div", obj).index($(event.srcElement))];
				
			});
		
			$("#slidernext").mouseover(function(){
				$(this).removeClass("menunext");
				$(this).addClass("menunext_tover");
			});
			
			$("#sliderprev").mouseover(function(){
				$(this).removeClass("menuprev");
				$(this).addClass("menuprev_tover");
			});
			
			$("#slidernext").mouseout(function(){		
				$(this).removeClass("menunext_tover");
				$(this).addClass("menunext");
			});
			
			$("#sliderprev").mouseout(function(){		
				$(this).removeClass("menuprev_tover");
				$(this).addClass("menuprev");
			});
			
			$("#slidernext").click(function(){
				$(this).fadeOut();
				animate("next");
				$(this).fadeIn();
			});
			
			$("#sliderprev").click(function(){
				$(this).fadeOut();
				animate("prev");
				$(this).fadeIn();
			});
			
			function animate(dir){
				if(w_ul > w){
					if(dir == "next"){
						t = (t>=ts) ? ts : t+1;	
					} else {
						t = (t<=0) ? 0 : t-1;
					};
					
					p = (t*w_li*-1);
					if(w-p < w_ul){
						$("ul",obj).animate(
							{ marginLeft: p }, 
							options.speed
						);
					}
					else{
						$("ul",obj).animate(
							{ marginLeft: w-w_ul }, 
							options.speed
						);
					}
				}
			};

			if($("#slidertover").size() == 1 && options.currencyID > 0){
				for(var i=0; i<s; i++){
					if($("div", obj)[i].id == "slidertover"){
						break;
					}
					else{
						animate("next");
					}
				}
			}
		});
	  
	};

})(jQuery);
