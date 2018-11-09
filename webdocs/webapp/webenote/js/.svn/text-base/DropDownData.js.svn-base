
function lib_bwcheck(){ 
	this.ver=navigator.appVersion
	this.agent=navigator.userAgent
	this.dom=document.getElementById?1:0
	this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom)?1:0;
	this.ie6=(this.ver.indexOf("MSIE 6")>-1 && this.dom)?1:0;
	this.ie4=(document.all && !this.dom)?1:0;
	this.ie=this.ie4||this.ie5||this.ie6
	this.mac=this.agent.indexOf("Mac")>-1
	this.opera5=this.agent.indexOf("Opera 5")>-1
	this.ns6=(this.dom && parseInt(this.ver) >= 5) ?1:0; 
	this.ns4=(document.layers && !this.dom)?1:0;
	this.bw=(this.ie6 || this.ie5 || this.ie4 || this.ns4 || this.ns6 || this.opera5 || this.dom)
	return this
}
var bw=new lib_bwcheck()

function makePageCoords(win,fr){
	if(!win) win=window
	this.x=0;this.x2=(bw.ns4 || bw.ns6)?win.innerWidth-1:win.document.body.offsetWidth;
	if(!fr&&bw.ie) this.x2-=20; else if(!fr&&bw.ns4) this.x2-=4; else if(bw.ns6) this.x2+=1
	if(bw.ns4&&!win.rows) this.x2+=5
	this.y=0;this.y2=(bw.ns4 || bw.ns6)?win.innerHeight:win.document.body.offsetHeight;
	if(!fr&&bw.ie) this.y2-=4; else if(bw.ns4&&fr) this.y2+=4
	this.y2orig=this.y2; this.x50=this.x2/2; this.y50=this.y2/2;
	this.x10=(this.x2*10)/100-5;this.y10=(this.y2*10)/100-5
	this.x15=(this.x2*15)/100-5;this.y15=(this.y2*15)/100-5; 
	return this;
}
/*******************************
Debugging function
********************************/
function debug(txt,ev){if(mDebugging==2) self.status=txt; else alert(txt); if(ev) eval(ev); return false}
/******************************
Scroll function
******************************/
var rrr=0
function cm_checkScrolled(obj){
	if(bw.ns4 || bw.ns6) obj.scrolledY=obj.win.pageYOffset
	else obj.scrolledY=obj.win.document.body.scrollTop
	if(obj.scrolledY!=obj.lastScrolled){
		if(!obj.useframes){
			for(i=0;i<obj.l[0].num;i++){var sobj=obj.l[0].o[i].oBorder; sobj.moveY(sobj.y+(obj.scrolledY-obj.lastScrolled))}
			if(obj.usebar) obj.oBar.moveY(obj.oBar.y+(obj.scrolledY-obj.lastScrolled))
		}
		obj.lastScrolled=obj.scrolledY; page.y=obj.scrolledY; page.y2=page.y2orig+obj.scrolledY
		if(!obj.useframes || bw.ie){ clearTimeout(obj.tim); obj.isover=0; obj.hideSubs(1,0)}
	}
	if((bw.ns4 || bw.ns6) && !obj.useframes) setTimeout("cm_checkScrolled("+obj.name+")",200)
}
/***********************
Checking if the values are % or not.
********************************/
function cm_checkp(num,w,check,istop,ds){
	if(num){
		var p=istop?toppage:page
		if(num.toString().indexOf("%")!=-1){
			if(w || (check && this.rows)) num=(p.x2*parseFloat(num)/100)
			else num=(p.y2*parseFloat(num)/100)
		}else num=eval(num)
	}else num=0; return num
}
/******************************
Making DIV objects + DIV objects code
******************************/
function cm_makeObj(obj,name,level,win,nest,o){
	if(o&&(bw.ns4||bw.ns6)) this.evnt=o
	else this.evnt=bw.dom?win.document.getElementById(obj):bw.ie4?win.document.all[obj]:bw.ns4?nest?win.document[nest].document[obj]:win.document[obj]:0;
	if(!this.evnt) return debug('There seems to be an error with this layer:\nFrame: '+win+'\nLayer: '+nest + "." + obj)
	this.css=bw.dom||bw.ie4?this.evnt.style:bw.ns4?this.evnt:0;			
	this.ref=bw.dom || bw.ie4?win.document:bw.ns4?this.css.document:0;
	this.hideIt=cm_hideIt; this.showIt=cm_showIt; this.writeIt=cm_writeIt; this.setactive=cm_setactive;
	this.moveIt=cm_moveIt; this.clipTo=cm_clipTo; if(name) this.parent=name; this.moveY=cm_moveY;
	this.l=level; this.addEvents=cm_addEvents; return this
}
function cm_writeIt(text){if(!this.img1){if(bw.ns4){this.ref.write(text);
this.ref.close()}else this.evnt.innerHTML=text}}
function cm_moveY(y){this.y=y; this.css.top=y}
function cm_moveIt(x,y){this.x=x; this.y=y; this.css.left=this.x;this.css.top=this.y}
function cm_showIt(){this.css.visibility="visible"; this.vis=1}
function cm_hideIt(){this.css.visibility="hidden"; this.vis=0}

function cm_setactive(on,name,frmmouse){
	if(!name) name=this.name
	var tobj=this.parent.m[name]
	if(tobj.img){ //Switch image
		if(tobj.img2){if(on) this.ref.images[tobj.img].src=tobj.img2; else this.ref.images[tobj.img].src=tobj.img1}
	}else{ //Switch text and backgroundcolor
		if(on){var color=tobj.c2; var fcolor=tobj.c4; var re=tobj.c3}else{var color=tobj.c1; var fcolor=tobj.c3; var re=tobj.c4}
		if(color){if(bw.dom || bw.ie4) this.css.backgroundColor=color
		else if(bw.ns4) if(color=="transparent") color=null; this.css.bgColor=color}
		if(fcolor && !bw.ns4){
			if(bw.ie4) this.evnt.style.color=fcolor 
			else if(this.evnt.childNodes[0]) this.evnt.style.color=fcolor
		}else if(fcolor&&frmmouse){
			t=this.parent.m[name].text
			t=t.replace(re,fcolor)
			this.writeIt(t)
		}
		//Stupid fix for netscape 6....
		if(tobj.l==0&&bw.ns6){this.parent.l[0].o[tobj.num].oBorder.hideIt(); this.parent.l[0].o[tobj.num].oBorder.showIt();}
	}
}
function cm_clipTo(t,r,b,l,w){if(bw.ns4){this.css.clip.top=t;this.css.clip.right=r; this.css.clip.bottom=b;this.css.clip.left=l
}else{this.css.clip="rect("+t+","+r+","+b+","+l+")"; if(w){this.css.width=r; this.css.height=b}}
this.width=r; this.height=b}
function cm_addEvents(n,name){
  /****
   * modified by hally. start:
   * 菜单的3个事件：onmousedown控制一级菜单，onmouseover控制二级及以下菜单，onmouseout控制菜单的关闭
   ****/
	this.evnt.onmousedown=new Function(name+".mdown('"+n+"')");	
	this.evnt.onmouseover=new Function(name+".mover('"+n+"')");
	 this.evnt.onmouseout=new Function(name+".mmout('"+n+"')");
	/****
	 * modified by hally, end
	 ****/
	if(bw.ns4){this.ref.captureEvents(Event.MOUSEUP); this.ref.onmouseup=new Function(name+".go('"+n+"')")}
	else this.evnt.onclick=new Function(name+".go('"+n+"')")
}
/******************************
Making menu object
******************************/
function cm_makeMenu(name,parent,text,link,target,width,height,img1,img2,bgcoloroff,bgcoloron,textcolor,hovercolor,onclick,onmousedown,onmouseover,onmouseout){
	this.m[name]=new Object(); var obj=this.m[name]; obj.name=name;	obj.subs=new Array(); obj.parent=parent;
	obj.lnk=(link==0||link=='')?"":link; obj.target="_self"
	if(parent!="" && parent){this.m[parent].subs[this.m[parent].subs.length]=name; l=this.m[parent].l+1} else l=0
	obj.l=l; prop1=l<this.level.length?this.level[l]:this.level[this.level.length-1]; prop2=this.level[0]
	if(this.l.length<=l){
		this.l[l]=new Object(); this.l[l].num=0; if(l==0) this.l[l].names=new Array()
		this.l[l].border=prop1["border"]||prop2["border"]; this.l[l].maxnum=0
		this.l[l].bordercolor=prop1["bordercolor"]||prop2["bordercolor"];
		s=prop1["align"]||prop2["align"]; if(s=="left") s=1; else if(s=="right") s=0; 
		else if(s=="top") s=3; else if(s=="bottom") s=2; this.l[l].align=s
		this.l[l].height=prop1["height"]||prop2["height"]; this.l[l].width=prop1["width"]||prop2["width"];
		this.l[l].style=prop1["style"]||prop2["style"];
		this.l[l].offsetX=String(prop1["offsetX"])!="undefined"?prop1["offsetX"]:prop2["offsetX"]
		this.l[l].offsetY=String(prop1["offsetY"])!="undefined"?prop1["offsetY"]:prop2["offsetY"]
		this.l[l].tc=textcolor||prop1.textcolor||prop2.textcolor;
	}
	if(l==0) this.l[l].names[this.l[l].names.length]=name
	if(parent!="" && parent){obj.num=this.m[parent].subs.length-1}else obj.num=this.l[l].num
	this.l[l].num++; this.aobj[l]=-1; prop=l<this.level.length?this.level[l]:this.level[this.level.length-1]
	obj.width=this.checkp(width?width:prop1.width?prop1.width:prop2.width,1,0,1);
	obj.height=this.checkp(height?height:prop1.height?prop1.height:prop2.height,0,0,1);
	if(parent!="" && parent){
	 if(this.m[parent].subs.length>this.l[l].maxnum) this.l[l].maxnum=this.m[parent].subs.length 
		if(this.m[parent].totheight==0) this.m[parent].totheight=this.l[l].border
		this.m[parent].totheight+=obj.height+ this.l[l].border
		if(this.m[parent].maxwidth<obj.width) this.m[parent].maxwidth=obj.width+this.l[l].border*2
	}else{
		this.l[l].maxnum=this.l[l].names.length; this.totwidth+=obj.width; this.totheight+=obj.height
		this.maxwidth=this.maxwidth>obj.width?this.maxwidth:obj.width; this.maxheight=this.maxheight>obj.height?this.maxwidth:obj.height
	}
	if(img1) text='<img src="'+img1+'" border="0" name="imgCMenu'+name+'">'

	else if(bw.ns4){text='<font size="'+(prop1.NS4fontSize||prop2.NS4fontSize)+'" 	face="'+(prop1.NS4font||prop2.NS4font)+'" color="'+(textcolor||this.l[l].tc)+'">'+text+'</font>'}



	
if(parent==""&&bw.ns4&&this.useNS4links) {text='<table cellpadding=0 border=0 height="'+height+'" width="'+width+'"><tr><td width="'+width+'" align="center"><a href="#" onclick="'+this.name+'.go(\''+name+'\'); return false" class="clNS4">'+text+'</a></td></tr></table>'}
else if (bw.ns4&&this.useNS4links) {text='<table cellpadding=1 border=0 height="'+height+'" width="'+width+'"><tr><td width="'+width+'"><a href="#" onclick="'+this.name+'.go(\''+name+'\'); return false" class="clNS4">'+text+'</a></td></tr></table>'}



	if(img1){obj.preimg1=new Image(); obj.preimg1.src=img1}; if(img2){obj.preimg2=new Image(); obj.preimg2.src=img2}
	if(img2) obj.img="imgCMenu"+name; else obj.img=0; obj.img1=img1||""; obj.img2=img2||""; obj.text=text;
	obj.c1=bgcoloroff||prop1.bgcoloroff||prop2.bgcoloroff; obj.c2=bgcoloron||prop1.bgcoloron||prop2.bgcoloron;
	obj.c3=textcolor||this.l[l].tc; obj.c4=hovercolor||prop1.hovercolor||prop2.hovercolor;
	obj.mclick=onclick||""; obj.mdown=onmousedown||""; obj.mover=onmouseover||""; obj.mout=onmouseout||"";
	obj.totheight=0; obj.maxwidth=0; obj.subx=-1; obj.suby=-1;
}
/******************************
Onmouseout
******************************/
function cm_mout(name){
  this.isOpen=0; //鼠标离开时
}
/******************************
onmousedown
******************************/
function cm_mdown(name){
  /****
   * modified by hally.
   * 控制一级菜单的显示
   ****/
  
  if (this.isOpen == 0)
  {
    //this.m[name].parent是菜单的父菜单的名字，m[name]可能类似属性组
    if (this.m[name].parent == "")
    {
      this.isOpen = 1;
    	clearTimeout(this.tim);
    	this.isover=1; 
    	if(this.m[name].mover!="") eval(this.m[name].mover)
    	var l=this.m[name].l; var num=this.m[name].num; var obj=this.l[l].o[num]
    	if(this.aobj[l]!=-1&&this.aobj[l]!=obj){if(this.aobj[l].l>=l) this.aobj[l].setactive(0,0,1)}
    	if(this.lastl==name&&l!=0) return; 
    	if(l==0&&this.loaded){
    		if(this.aobj[0].name==name && this.aobj[1] != null){
    			this.hideSubs(2,1); if(this.aobj[1]!=-1) this.aobj[1].setactive(0,0,1); this.aobj[1]=-1; this.lastl=-1; return
    		}
    	}
    	this.aobj[l]=obj; this.aobj[l].name=name; this.aobj[l].setactive(1,0,1); this.lastl=name; 
    	if(!this.loaded) return
    	this.showSubs(name,l,num)
    }
  }else
  {
    this.isOpen = 0;
  	clearTimeout(this.tim);
  	this.isover=0; if(this.m[name].mout!="") eval(this.m[name].mout); 
  	if(!(!bw.ie&&this.m[name].l==0&&this.useframes&&this.aobj[1]!=-1)) this.tim=setTimeout(this.name+".hideSubs(1,0,0,0,1)",this.wait)
  }
}
function cm_mover(name){
  /****
   * modified by hally.
   * 控制二级及以下菜单的显示
   * 二级以下菜单，鼠标进入时，isOpen变为1，所以，在mdown的控制下，单击就会关闭
   ****/
  
  if (this.isOpen == 0)
  {
    if (this.m[name].parent.length > 0)
    {
      this.isOpen = 1;
    	clearTimeout(this.tim);
    	this.isover=1; 
    	if(this.m[name].mover!="") eval(this.m[name].mover)
    	var l=this.m[name].l; var num=this.m[name].num; var obj=this.l[l].o[num]
    	if(this.aobj[l]!=-1&&this.aobj[l]!=obj){if(this.aobj[l].l>=l) this.aobj[l].setactive(0,0,1)}
    	if(this.lastl==name&&l!=0) return; 
    	if(l==0&&this.loaded){
    		if(this.aobj[0].name==name && this.aobj[1] != null){
    			this.hideSubs(2,1); if(this.aobj[1]!=-1) this.aobj[1].setactive(0,0,1); this.aobj[1]=-1; this.lastl=-1; return
    		}
    	}
    	this.aobj[l]=obj; this.aobj[l].name=name; this.aobj[l].setactive(1,0,1); this.lastl=name; 
    	if(!this.loaded) return
    	this.showSubs(name,l,num)
    }
  }else
  {
    this.isOpen = 0;
  	clearTimeout(this.tim);
  	this.isover=0; if(this.m[name].mout!="") eval(this.m[name].mout); 
  	if(!(!bw.ie&&this.m[name].l==0&&this.useframes&&this.aobj[1]!=-1)) this.tim=setTimeout(this.name+".hideSubs(1,0,0,0,1)",this.wait)
  }
}
/******************************
Hiding subelements
******************************/
function cm_hideSubs(l,system,cl,sys2,hc){
	if(this.isover && !system) return
	if(l==1 && this.aobj[0]!=-1&&!sys2){this.aobj[0].setactive(0,0,1);this.aobj[0]=-1}
	if(!this.loaded) return; if(cl==1) return
	for(i=l;i<this.l.length;i++){
		if(this.l[i].oBorder.vis==0) break;
		this.l[i].oBorder.hideIt(); this.aobj[i]=-1
	}
	if(hc&&this.hcode){ eval(this.hcode); this.hcode=""}
}
/******************************
Get x/y coords. Only the first time :)
******************************/
function cm_getCoords(name,l,num,topalign,align,ln,border,cn,lev1b){
	if(cn==5){ this.m[name].subx=0; this.m[name].suby=0; return }//Just in case infinitive loops
	if(l==1) var pobj=this.l[l-1].o[num].oBorder
	else var pobj=this.l[l-1].oBorder
	var x=pobj.x; var y=pobj.y;
	if(l!=1){y+=this.l[l-1].o[num].y}
	pborder=this.l[l-1].border; 
	lx=x+pobj.width; rx=x-this.m[name].maxwidth- (this.l[l-1].offsetX*2)
	if(align==0){if(l==1){y+=border} if(l==1&&this.useframes) x=0; else x=lx
	}else if(align==1){x=rx; if(l==1){if(this.useframes) x=page.x2 - this.m[name].maxwidth; y+=border}}
	if((align==2||topalign==2)&&lev1b!=3){
		if(l!=1 && (align!=1&&align!=0)){if(topalign==1) x=rx; else x=lx}
		if(l==1) if(this.useframes) y=0; else y+=this.m[name].height+border+pborder;
	}
	if((align==3||topalign==3)&&lev1b!=2){
		if(l!=1&&align!=1&&align!=0){if(topalign==1) x=rx; else x=lx}
		if(this.useframes&&l==1) y=page.y2 - this.m[name].totheight - this.l[l-1].offsetY*2
		else y-=this.m[name].totheight - this.l[l].offsetY*2; 		
		if(l!=1||lev1b==3) y+=this.m[name].height
	}
	this.m[name].scrollY=this.lastScrolled;
	this.m[name].subx=x+this.l[l-1].offsetX; this.m[name].suby=y+this.l[l-1].offsetY
	if(this.useframes&&l==1&&align!=3) this.m[name].suby+=this.lastScrolled
	if(this.pagecheck&&(l!=1||!this.useframes)) this.checkPage(name,l,num,topalign,align,ln,border,cn)
	
}
/******************************
Checking page coords
******************************/
function cm_checkPage(name,l,num,topalign,align,ln,border,cn){
	cn++; 
	if(this.m[name].subx+this.m[name].maxwidth>page.x2){
		if(align!=1){if(align==3&&topalign!=0) topalign=3; align=1; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}else if(this.m[name].subx<page.x){
		if(align!=0){if(align==3) topalign=3; align=0; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}else if((this.m[name].suby+this.m[name].totheight)>page.y2){
		if(l==1){topalign=3; this.getCoords(name,l,num,topalign,align,ln,border,cn,3)}
		else if(align!=3){ align=3; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}else if(this.m[name].suby<page.y){
		if(l==1){topalign=2; this.getCoords(name,l,num,topalign,align,ln,border,cn,2)}
		else if(align!=2){align=2; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}
}
/******************************
Showing subelements
******************************/
function cm_showSubs(name,l,num){
	l+=1; if(l>=this.l.length) return; ln=this.m[name].subs.length
	if(ln==0){this.hideSubs(l,1,0,1); return}
	else this.hideSubs(l+1,1); var border=this.l[l].border; this.aobj[l]=-1
	if(this.useframes&&(bw.ns4||bw.ns6)) cm_checkScrolled(this)
	if((this.m[name].subx==-1 || this.m[name].suby==-1) || this.m[name].scrollY!=this.lastScrolled || this.isresized){
		var topalign=this.l[0].align; var align=this.l[l-1].align; 
		this.getCoords(name,l,num,topalign,align,ln,border,0)
	}var x=this.m[name].subx; var y=this.m[name].suby;
	var bobj=this.l[l].oBorder; bobj.hideIt();
	bobj.clipTo(0,this.m[name].maxwidth,this.m[name].totheight,0,1)
	bobj.moveIt(x,y); var yy=border
	for(i=0;i<this.l[l].maxnum;i++){
		var obj=this.l[l].o[i]
		if(i<ln){
			var n=this.m[name].subs[i]; obj.aname=n; if(!bw.ns4||!this.NS4hover) obj.writeIt(this.m[n].text)
			obj.addEvents(n,this.name); var w=this.m[n].width; var h=this.m[n].height
			if(obj.y!=yy) obj.moveY(yy); yy+=h+border; if(!obj.img) obj.setactive(0,n,1); 
			if(obj.width!=w||obj.height!=h) obj.clipTo(0,w,h,0,1); obj.css.visibility="inherit"
		}else obj.hideIt()
	}bobj.showIt()
	if(!bw.ns4&&this.checkselect){ //CHECKING FOR SELECT BOXES
		for(i=0;i<this.sel.length;i++){
			selx=0; sely=0
			if(this.sel[i].offsetParent){
				selx+=this.sel[i].offsetParent.offsetLeft; sely+=this.sel[i].offsetParent.offsetTop
				if(this.sel[i].offsetParent.offsetParent){
					selx+=this.sel[i].offsetParent.offsetParent.offsetLeft; sely+=this.sel[i].offsetParent.offsetParent.offsetTop
					if(this.sel[i].offsetParent.offsetParent.offsetParent){
						selx+=this.sel[i].offsetParent.offsetParent.offsetParent.offsetLeft; sely+=this.sel[i].offsetParent.offsetParent.offsetParent.offsetTop
					}	
				}
			}
			selx+=this.sel[i].offsetLeft; sely+=this.sel[i].offsetTop
			selw=this.sel[i].offsetWidth; selh=this.sel[i].offsetHeight
			if(((selx+selw)>this.m[name].subx && selx<(this.m[name].subx+this.m[name].maxwidth))
			&&((sely+selh)>this.m[name].suby && sely<(this.m[name].suby+this.m[name].totheight))){
				if(this.sel[i].style.visibility!="hidden"){
					this.sel[i].level=l
					this.sel[i].style.visibility="hidden"; 
					this.hcode+=this.name+".sel["+i+"].style.visibility='visible';"
				}
			}else if(l<=this.sel[i].level) this.sel[i].style.visibility="visible"
		}
	}else if(bw.ns4&&this.hideForm){
		eval(this.hideForm+".visibility='hide'")
		this.hcode=this.hideForm+".visibility='show'"
	}
}
/******************************
Making all top elements
******************************/
function cm_makeTop(rr){
	var m,rows,border,x,y,mpa
	m=this.menuplacement; rows=this.rows; this.pxbetween=this.checkp(this.pxbetween,0,1,1)
	border=this.l[0].border;y=this.checkp(this.fromtop,0,0,1)+border;x=this.checkp(this.fromleft,0,0,1)+border
	if(m=="bottomcenter"||m=="bottom"){
		if(m=="bottomcenter") x=toppage.x2/2-(this.totwidth+border*this.l[0].num+this.pxbetween*(this.l[0].num-1))/2
		y=toppage.y2-this.maxheight-border
	}else if(m=="right") x=toppage.x2-this.maxwidth-border*2
	else if(m=="bottom") y=toppage.y2-this.maxheight-border*2
	else if(m=="center"){if(rows==0) x=toppage.x2/2 -  (this.maxwidth+border*2)/2; else x=toppage.x2/2 - (this.totwidth + border*this.l[0].num +this.pxbetween*(this.l[0].num-1))/2}
	else if(m.toString().indexOf(",")>-1) mpa=1
	if(this.usebar){ var bx,by,bww,bh,oBb
		oNS=bw.ns6?this.oNS[this.l[0].maxnum]:0
		this.oBar=new cm_makeObj('div'+this.name+'Bar',0,0,window,0,oNS)
		if(this.barx=="menu") bx=mpa&&rows?this.checkp(m[0],1,0,1)-border:x-border; else{ bx=this.checkp(this.barx,1,0,1) }
		if(this.bary=="menu") by=mpa&&!rows?this.checkp(m[0],0,0,1)-border:y-border; else by=this.checkp(this.bary,0,0,1);  this.oBar.moveIt(bx,by)
		if(this.barwidth=="menu"){bww=rows?mpa?(this.checkp(m[m.length-1],1,0,1)-bx)+this.m[this.l[0].names[this.l[0].num-1]].width+border:(this.totwidth +this.pxbetween*(this.l[0].num-1)):this.maxwidth; 
		bww+=!rows?border*2:0;}else bww=this.checkp(this.barwidth,1,0,1); 
		if(bw.ie&&rows&&this.barwidth=="100%"&&this.useframes) bww+=parseInt(self.document.body.leftMargin)*2
		if(this.barheight=="menu"){bh=!rows?mpa?(this.checkp(m[m.length-1],0,0,1)-by)+this.m[this.l[0].names[this.l[0].num-1]].height+border:(this.totheight + this.pxbetween*(this.l[0].num-1)):this.maxheight;
		bh+=rows?this.l[0].border*2:0;} else bh=this.checkp(this.barheight,0,0,1);
		this.oBar.clipTo(0,bww,bh,0,1);
		if(this.barinheritborder&&border){oBb=new cm_makeObj('div'+this.name+'Barb',0,0,window,'div'+this.name+'Bar'); 
		oBb.moveIt(border,border); oBb.clipTo(0,bww-border*2,bh-border*2,0,1); oBb=null;}
	}this.l[0].o=new Array()
	for(j=0;j<this.l[0].maxnum;j++){
		this.l[0].o[j]=new cm_makeObj('div'+this.name+'0_'+j,this,0,window,'div'+this.name+'0_'+j+'b'); if(bw.ns6) oNS=this.oNS[j]; else oNS=0
		this.l[0].o[j].oBorder=new cm_makeObj('div'+this.name+'0_'+j+'b',0,0,window,0,oNS)
		obj=this.l[0].o[j]; w=this.m[this.l[0].names[j]].width; h=this.m[this.l[0].names[j]].height
		obj.addEvents(this.l[0].names[j],this.name); obj.clipTo(0,w,h,0,1)
		if(mpa){if(rows==1) x=this.checkp(m[j],0,1,1); else y=this.checkp(m[j],0,0,1)}
		obj.moveIt(border,border); 
		obj.setactive(0,this.l[0].names[j])
		obj.oBorder.moveIt(x-border,y-border); obj.oBorder.clipTo(0,w+border*2,h+border*2,0,1); obj.oBorder.showIt()
		if(rows==0) y+=h+border+this.pxbetween
		else x+=w+border+this.pxbetween; obj.showIt()
	}
	if(!rr){
		if(!this.useframes&&!bw.ns4) this.refresh()
		else if(!this.useframes&&bw.ns4){l=""; if(onload){l=String(onload.toString()); l=l.replace("function onload(event)",""); l=l.slice(25,l.length-2)} l+=this.name+ ".refresh();"; onload=new Function(l)}
		if(this.resizecheck) setTimeout('window.onresize=new Function("'+this.name+'.resized()")',500)
		if(this.checkscroll){if(bw.ns4 || bw.ns6){if(this.checkscroll!=2&&this.useframes!=1){setTimeout("cm_checkScrolled("+this.name+")",200)}}}
		if(this.useframes&&!rr) this.checkFrame(0); else this.win=window
	}
	if(!bw.ns4&&this.checkselect) this.sel=bw.ie4?this.win.document.all.tags("SELECT"):this.win.document.getElementsByTagName("SELECT")
}
/******************************
Refreshing/making all sub elements
******************************/
function cm_refresh(ev){
	var border,obj,oNS,oNS2
	if(this.useframes) page=new makePageCoords(this.win,this.useframes); else page=toppage
	for(i=1;i<this.l.length;i++){
		this.l[i].o=new Array();
		border=this.l[i].border; defheight=this.checkp(this.l[i].height)
		if(bw.ns4){oNS=new Layer(this.l[i].width,this.win); oNS.zIndex=(500+i); oNS.bgColor=this.l[i].bordercolor;}
		else if(bw.ns6){oNS=document.createElement("DIV"); oNS.setAttribute("style",this.ns6styleb[i]); this.win.document.body.appendChild(oNS)}
		this.l[i].oBorder=new cm_makeObj('div'+this.name+i+"b",0,0,this.win,0,oNS)
		for(j=0;j<this.l[i].maxnum;j++){
			if(bw.ns4){oNS2=new Layer(this.l[i].width,oNS); oNS2.bgColor=this.l[i].bordercolor;  oNS2.paddingLeft="5"}
			else if(bw.ns6){oNS2=document.createElement("DIV");	oNS2.setAttribute("style",this.ns6style[i]); oNS.appendChild(oNS2)}
			this.l[i].o[j]=new cm_makeObj('div'+this.name+i+'_'+j,this,i,this.win,0,oNS2)
			obj=this.l[i].o[j]; if(!obj.addEvents) return; obj.moveIt(border,(border+defheight)*j + border)
		}	
	}this.loaded=1;
	if(this.checkscroll&&bw.ie){this.win.document.body.onscroll=new Function("cm_checkScrolled("+this.name+")"); cm_checkScrolled(this)}
}
function cm_NS6_createElement(st,inn){
	el=document.createElement("DIV")
	if(st) el.setAttribute("style",st)
	if(inn) el.innerHTML=inn
	document.body.appendChild(el)
	return el
}
/******************************
Making code
******************************/
function cm_construct(){
	this.level=null; var str=""; var str2=""; var frstr=""; var tempstr,
	num=bw.ie?this.l.length:1;
	for(i=0;i<num;i++){
		if(i!=0) frstr+='<div id="div'+this.name+i+'b" style="'+this.ns6styleb[i]+'" class="cl'+this.name+i+'b">\n'
		for(j=0;j<this.l[i].maxnum;j++){
			tempstr='<div id="div'+this.name+i+'_'+j+'" '; if(i!=0) tempstr+='style="'+this.ns6style[i]+'"'
			if(i==0){
				n=this.l[0].names[j]; txt=this.m[n].text
				if(!bw.ns6){
					str+='<div id="div'+this.name+'0_'+j+'b" class="cl'+this.name+'b'+i+'">'
					str+=tempstr+' class="cl'+this.name+i+'">'+txt+'</div>'; str+='</div>\n'
				}else this.oNS[j]=cm_NS6_createElement(this.ns6styleb[i],tempstr+' class="cl'+this.name+i+'">'+txt+'</div>')
			}else frstr+=tempstr+'"></div>\n'
		}if(i!=0){frstr+='</div>\n'}
	}
	if(this.usebar){
		if(this.barinheritborder) str2='<div id="div'+this.name+'Barb"></div>'; 
		if(bw.ns6) this.oNS[this.l[0].maxnum]=cm_NS6_createElement(this.ns6styleb[this.ns6styleb.length-1],str2)
		else{str+='<div id="div'+this.name+'Bar">';str+=str2+'</div>\n'}
	}
	if(!this.useframes&&bw.ie) str+=frstr; else this.frstr=frstr; if(!bw.ns6) document.write(str)
	this.makeTop();	if(this.useframes) window.onerror=cm_check_error;
}
//Trapping external pages in frame error!
coolFrameError=0
function cm_check_error(e){
	e=e.toLowerCase()
	if(e.indexOf("access")>-1||e.indexOf("permission")>-1){
		coolFrameError=1; return true
	}else return false
}
/******************************
Make styles
******************************/
function cm_makeStyle(){
	var str="<style>\n"; var zindex=150;  var w
	this.ns6style=new Array(); this.ns6styleb=new Array()
	for(i=0;i<this.l.length;i++){
		if(i==0){c=this.l[i].tc; if(bw.ns4){w="width:" +this.l[0].width+";"; this.l[i].style=""} else w=""; str+='.cl'+this.name+i+'{position:absolute; '+w+' background-color:transparent; color:'+c+';'+this.l[i].style+'; cursor:pointer; cursor:hand; visibility:inherit; z-index:'+zindex+'}\n'}
		else{
			st='position:absolute; '+this.l[i].style+';  cursor:pointer; cursor:hand; visibility:inherit; z-index:'+zindex; this.ns6style[i]=st
		}
		bc=this.l[i].border?'layer-background-color:'+this.l[i].bordercolor+'; visibility:hidden; background-color:'+this.l[i].bordercolor+';':'';
		if(!bw.ns4) w="overflow:hidden;"; else w=""
		tempstr='position:absolute; '+w+' clip:rect(0,0,0,0); '+bc+' z-index:'+(zindex-50)
		if(i==0&&!bw.ns6) str+='.cl'+this.name+'b'+i+'{'+tempstr+'}\n'; 
		else this.ns6styleb[i]=tempstr
		zindex+=100
	}	
	if(this.usebar){
		bg=this.barinheritborder?this.l[0].bordercolor:this.barcolor
		st='z-index:80; position:absolute; background-color:'+bg+'; layer-background-color:'+bg
		if(!bw.ns6) str+='#div'+this.name+'Bar{'+st+'}\n'; else this.ns6styleb[this.ns6styleb.length]=st
		if(this.barinheritborder){str+='#div'+this.name+'Barb{z-index:85; position:absolute; background-color:'+this.barcolor+'; layer-background-color:'+this.barcolor+'}\n'}
	}

	if(bw.ns4) str+="\nA.clNS4{font-size:"+this.myNS4FontSize+";text-decoration:none;padding:"+this.NS4padding+"}\n"
	document.write(str+"\n</style>\n")
	//if(bw.ns4) str+="\nA.clNS4{text-decoration:none;padding:"+this.NS4padding+"}\n"
	//document.write(str+"\n</style>\n")

}
/******************************
Refreshing page if it's resized
******************************/
function cm_resized(){
	page2=new makePageCoords(window,this.useframes); 
	if(page2.x2!=toppage.x2 || page2.y2!=toppage.y2){
		if(!bw.ns4){
			toppage=new makePageCoords(window,this.useframes); this.makeTop(1)
			if(!this.useframes) page=toppage; this.isresized=1
			eval(this.resizecode)
		}else{this.win.location.reload(); location.reload()}
	}
	if(!bw.ns4&&this.useframes){page=new makePageCoords(this.win,this.useframes)}
}
/******************************
Going to another page
******************************/
function cm_go(name){
	obj=this.m[name]; url=obj.lnk; target=obj.target; fc=obj.mclick
	if(url){
		if(this.useframes&&!coolFrameError) loc=this.win.location.href; else loc=location.href
		if(fc) eval(fc); url=this.checkFolder(loc.toString(),url); this.isover=0;
		if(String(target)=="undefined" || target=="" || target==0 || target=="_self"){
			this.hideSubs(1,0,1); this.win.location.href=url
		}else if(target=="_blank") window.open(url)
		else if(target=="_top" || target=="window") top.location.href=url  
		else if(top[target]) top[target].location.href=url
		else{fr=findFrame(target); if(fr) fr.location.href=url}
	}else if(fc) eval(fc)
}
/******************************
Getting folders - THANKS TO DCAGE FOR THIS FIX
******************************/
function cm_checkFolder(tmp,url){
	if(url.indexOf("mailto:")>-1 || url.indexOf("/")==0 || url.indexOf("http://")==0) return url
	else if(this.useframes && bw.ie) return url
	var addr=''; var lvl=''; var off_cnt=0; var cnt=0;
	if(tmp.indexOf('file:')>-1 || tmp.charAt(1)==':') addr=this.offlineUrl;
	else if(tmp.indexOf('http:')>-1) addr=this.onlineUrl;
	for(var i=0;i<addr.length;i++){if(addr.charAt(i)=='\/') off_cnt+=1}
	for(var i=0;i<tmp.length;i++){if(tmp.charAt(i)=='\/'){cnt+=1; if(cnt>off_cnt) lvl+='../'}}
	return lvl + url
}
/******************************
Checkloaded for the frames version
******************************/
function cm_checkLoaded(ev,ns){
	coolFName=eval(coolFName)
	var ok=0
	if(document.layers){ 
		if(ns){
			coolFName.refresh(); coolFName.nsload=1; ok=1; coolFrameError=0
		}else if(ev.target.name==coolFName.frame&&!coolFName.nsload){coolFName.refresh(); ok=1; coolFrameError=0; routeEvent(ev)}
	}else if(bw.ie){
		if (coolFName.win.document.readyState == "complete"){
			coolFName.win.document.body.insertAdjacentHTML("beforeEnd",coolFName.frstr)
			coolFName.win.document.body.onunload=cm_unloaded; coolFName.refresh(); ok=1; coolFrameError=0
		}else setTimeout("cm_checkLoaded()",200)
	}else if(bw.ns6){ 
		if(coolFName.win.document){
			if(coolFName.win.document.body){coolFName.win.addEventListener("unload", cm_unloaded, true); coolFName.refresh(); ok=1; coolFrameError=0}
			else setTimeout("cm_checkLoaded()",200)
		}else setTimeout("cm_checkLoaded()",200)
	}
}
function cm_unloaded(ev){coolFName=eval(coolFName); if(document.layers && ev.target.name!=coolFName.frame) return; else coolFName.nsload=0; coolFName.loaded=0; if(!document.layers) setTimeout("cm_checkLoaded()",200)}
/******************************
Make menu object
******************************/
var coolFName=""; var onload
function makeCoolMenu(name){
	coolFName=name
	this.name=name; this.lastScrolled=0;
	this.win=window; toppage=new makePageCoords(window,parent.frames.length);
	this.aobj=new Array(); this.m=new Array()
	this.o=new Array(); this.l=new Array()
	this.level=new Array(); this.lastl=-1; this.resized=cm_resized;
	this.makeMenu=cm_makeMenu; this.showSubs=cm_showSubs; this.makeTop=cm_makeTop;
	this.getCoords=cm_getCoords; this.checkPage=cm_checkPage;
	this.mmout=cm_mout; this.mover=cm_mover; this.mdown=cm_mdown; this.checkp=cm_checkp;
	/****
	 *  modified by hally :start
	 ****/
	this.isOpen = 0; //菜单是展开（1）还是收起（0）
	
	/****
	 *  modified by hally :end
	 ****/
	this.hideSubs=cm_hideSubs; this.construct=cm_construct;
	this.makeStyle=cm_makeStyle; this.refresh=cm_refresh;
	this.go=cm_go; this.frstr=""; this.loadcnt=0
	this.totwidth=0; this.totheight=0; this.maxwidth=0; this.maxheight=0
	this.tim=10; this.loaded=0; this.isover=false; this.checkFrame=cm_checkFrame;
	this.checkFolder=cm_checkFolder;  this.hcode=""
	this.oNS=new Array(); this.oNS2=new Array()
}

/******************************
Find frame
******************************/
function findFrame(frameName){
	obj=top; var frameObj=0;
	for(i=0;i<obj.frames.length;i++){
		if(obj.frames[i].name==frameName){frameObj=obj.frames[i]; break;}; ln=obj.frames[i].frames.length
		for(j=0;j<ln;j++){
			if(obj.frames[i].frames[j].name==frameName){frameObj=obj.frames[i].frames[j];  break}; ln2=obj.frames[i].frames[j].frames.length
			for(a=0;a<ln2;a++){
				if(obj.frames[i].frames[j].frames[a].name==frameName){frameObj=obj.frames[i].frames[j].frames[a]; break}
			}
		}
	}
	return frameObj
}
/******************************
Checking for frame
******************************/
function cm_checkFrame(num){
	var fr; if(num==10){debug('Frame: '+this.frame+' doesn\'t exist - Value: '+fr + " - Could not build menus."); return}
	if(!top.frames[this.frame]) fr=findFrame(this.frame); else fr=top.frames[this.frame]
	if(!fr){num++; setTimeout(this.name+".checkFrame("+num+")",500)
	}else{ this.win=fr
		if(bw.ns4){
			top.frames.captureEvents(Event.UNLOAD); top.frames.captureEvents(Event.LOAD); top.frames.onunload=cm_unloaded;
			top.frames.onload = cm_checkLoaded;	setTimeout("cm_checkLoaded(0,1)",1000)
		}else if((bw.ie||bw.ns6)) setTimeout("cm_checkLoaded()",200)
	}
}

//Start Data///////////////////////////////////


/*****************************************************************************
Default browsercheck - Leave this one
******************************************************************************/
function CenterNS(title,width){//Added for NS4 Centering TEJ
 if (bw.ns4) {
/*
  titleLengthpx = (title.length) * 5
  buffer = width - titleLengthpx;
  buffer = Math.round(buffer/8.1);
  //alert(buffer + " spaces added to " + title); 
for(i = 0;i<buffer;i++){
   //title = title + "&nbsp;";
	title =  "&nbsp;" + title;
	}
*/
 }
 if (bw.ns6) {
  buffer = 10;
  buffer = Math.round(buffer/3);
  for(i = 0;i<buffer;i++){
   title = title + "&nbsp;";
  }
 }
 
 return title;
}

function openWindow(url) {
	popupWin = window.open(url);
}


function lib_bwcheck(){ //Browsercheck (needed)
	this.ver=navigator.appVersion; this.agent=navigator.userAgent
	this.dom=document.getElementById?1:0
	this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom)?1:0;
	this.ie6=(this.ver.indexOf("MSIE 6")>-1 && this.dom)?1:0;
	this.ie4=(document.all && !this.dom)?1:0;
	this.ie=this.ie4||this.ie5||this.ie6
	this.mac=this.agent.indexOf("Mac")>-1
	this.opera5=this.agent.indexOf("Opera 5")>-1
	this.ns6=(this.dom && parseInt(this.ver) >= 5) ?1:0; 
	this.ns4=(document.layers && !this.dom)?1:0;
	this.bw=(this.ie6 || this.ie5 || this.ie4 || this.ns4 || this.ns6 || this.opera5 || this.dom)
	return this
}
var mDebugging=2 //General debugging variable. Set to 0 for no debugging, 1 for alerts or 2 for status debugging.

function showMenu(strPriv,strUrl)
{
var bw=new lib_bwcheck(); //Making browsercheck object

oCMenu=new makeCoolMenu("oCMenu"); //Making the menu object. Argument: menuname
oCMenu.useframes=0; //Do you want to use the menus as coolframemenu or not? (in frames or not) - Value: 0 || 1
oCMenu.frame="frmMain" ;//The name of your main frame (where the menus should appear). Leave empty if you're not using frames - Value: "main_frame_name"

/*If you set this to 1 you will get a "hand" cursor when moving over the links in NS4. The only downside to it is that 
if you're using frames and you don't have spesified that the links should have no "underline" you will get lines on the links.
In that case you have to add this STYLE setting to ALL the pages that will be loaded in the main frame:
<style>A.clNS4{text-decoration:none; padding:YOUR_PADDING}</style>  (if you don't want padding just remove the padding stuff)*/
oCMenu.useNS4links=0 ;

//After adding the "hover effect" for netscape as well, all styles are lost. But if you want padding add it here.
oCMenu.NS4padding=0 ;

//If you have select boxes close to your menu the menu will check for that and hide them if they are in the way of the menu.
//This feature does unfortunatly not work in NS4!
oCMenu.checkselect=1;

/*If you choose to have this code inside a linked js, or if your using frames it's important to set these variables. 
This will help you get your links to link to the right place even if your files are in different folders.
The offlineUrl variable is the actual path to the directory where you js file are locally. 
This is just so you can test it without uploading. Remember to start it with file:/// and only use slashes, no backward slashes!
Also remember to end with a slash                                                                                                 */
//oCMenu.offlineUrl="file:///C|/Inetpub/wwwroot/dhtmlcentral/" //Value: "path_to_menu_file_offline/"
//The onlineUrl variable is the online path to your script. Place in the full path to where your js file is. Remember to end with a slash.
//oCMenu.onlineUrl="" //Value: "path_to_menu_file_online/"

oCMenu.pagecheck=0 ;//Do you want the menu to check whether any of the subitems are out of the bouderies of the page and move them in again (this is not perfect but it hould work) - Value: 0 || 1
oCMenu.checkscroll=0 ;//Do you want the menu to check whether the page have scrolled or not? For frames you should always set this to 1. You can set this to 2 if you want this feature only on explorer since netscape doesn't support the window.onscroll this will make netscape slower (only if not using frames) - Value: 0 || 1 || 2
oCMenu.resizecheck=1 ;//Do you want the page to reload if it's resized (This should be on or the menu will crash in Netscape4) - Value: 0 || 1
oCMenu.wait=500 ;//How long to wait before hiding the menu on mouseout. Netscape 6 is a lot slower then Explorer, so to be sure that it works good enough there you should not have this lower then 500 - Value: milliseconds

//Background bar properties
oCMenu.usebar=0 ;//If you want to use a background-bar for the top items set this on - Value: 1 || 0
oCMenu.barcolor="Navy"; //The color of the background bar - Value: "color"
oCMenu.barwidth="100%" ;//The width of the background bar. Set this to "menu" if you want it to be the same width as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
oCMenu.barheight="menu" ;//The height of the background bar. Set this to "menu" if you want it to be the same height as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
oCMenu.barx=0 ;//The left position of the bar. Set this to "menu" if you want it be the same as the left position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
oCMenu.bary=0 ;//The top position of the bar Set this to "menu" if you want it be the same as the top position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
oCMenu.barinheritborder=0 ;//Set this to 1 if you want the bar to have the same border as the top menus - Value: 0 || 1

//Placement properties
oCMenu.rows=1; //This controls whether the top items is supposed to be laid out in rows or columns. Set to 0 for columns and 1 for row - Value 0 || 1

/*
oCMenu.fromleft=10 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
oCMenu.fromtop=90 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
*/

oCMenu.pxbetween=0; //How much space you want between each of the top items. - Value: px || "%"

/*You have several different ways to place the top items. 
You can have them right beside eachother (only adding the pxbetween variable)
oCMenu.menuplacement=0

You can have them aligned to one of the sides - This is mostly when not using frames, but can be used in both conditions
Values: (If you get strange results check the fromleft,fromtop and pxbetween variables above)
For menus that are placed in columns (align=left or align=right (se below)) you can align them to the "right" or "center"
For menus that are placed in rows (align=top or align=bottom (se below)) you can align them to the "bottom", "center" or "bottomcenter"
oCMenu.menuplacement="center"

You can also set them directly in pixels: (Remember to have as many array members as you have top items)
oCMenu.menuplacement=new Array(10,200,400,600)

Or you can place in percentage: (remember to use the ' ' around the numbers)


Choose one of those options to get the desired results.
*/
//oCMenu.menuplacement=new Array('2%','20%','36%','57%','70%','80%') 
oCMenu.menuplacement=0;


oCMenu.onlineUrl="/homeresource.nsf/AssetsByDescription/IntranetNavigation/$file/DropDownData.js/";
if (bw.ns4) {oCMenu.fromleft=8;}
else if (bw.ns6) {oCMenu.fromleft=8;}
else {oCMenu.fromleft=10;}
if (bw.ns4) {oCMenu.fromtop=58;}
else if (bw.ns6) {oCMenu.fromtop=58;}
else {oCMenu.fromtop=58;}
oCMenu.level[0]=new Array() ;
oCMenu.level[0].width=120;
oCMenu.level[0].height=20;
oCMenu.level[0].bgcoloroff='#475EA0';
oCMenu.level[0].bgcoloron='#2B77B5';
oCMenu.level[0].textcolor='#FFFFFF';
oCMenu.level[0].hovercolor='#FFFFFF';
oCMenu.level[0].style='padding:3px;font-family:Arial,Helvetica;font-size:11px; text-align:center';
oCMenu.level[0].border=1;
oCMenu.level[0].bordercolor='#475EA0';
oCMenu.level[0].offsetX=0;
oCMenu.level[0].offsetY=-1;
oCMenu.level[0].NS4font='Arial,Helvetica';
oCMenu.level[0].align='bottom';
oCMenu.level[1]=new Array() ;
oCMenu.level[1].width=250;
oCMenu.level[1].height=20;
oCMenu.level[1].bgcoloroff='#EFEFEF';
oCMenu.level[1].bgcoloron='#2B77B5';
oCMenu.level[1].textcolor='#000000';
oCMenu.level[1].hovercolor='#FFFFFF';
oCMenu.level[1].style='padding:4px;font-family:Arial,Helvetica;font-size:11px; ';
oCMenu.level[1].border=1;
oCMenu.level[1].bordercolor='#2B77B5';
oCMenu.level[1].offsetX=-5;
oCMenu.level[1].offsetY=3;
oCMenu.level[1].NS4font='Arial,Helvetica';
oCMenu.level[1].align='bottom';
oCMenu.myNS4FontSize='11px';

//alert(strPriv);
//   取得用户权限;
var arrDepart = new Array();
var arrLevel = new Array();
var arrName = new Array();
var arrNo = new Array();
var sParent = new Array();

arrDepart = strPriv.split("::");
if (arrDepart.length==4){
  
  arrName = arrDepart[0].split(";;");
  arrNo = arrDepart[1].split(";;");
  arrLevel = arrDepart[2].split(";;");
  arrUrl = arrDepart[3].split(";;");
  var iLength = arrName.length;

  var i = 0;
  var j;
  var sTmp;
  var sCompareNo;
  var sTmpUrl;
  var isShowArrow;
   
  for (j=0;j<iLength;j++)
  {
       sTmp = arrNo[j];
       
       //取得是否显示子菜单
       if (j != iLength -1 && arrLevel[j+1] != "999" )
       {
        
         sCompareNo = arrNo[j+1];
         isShowArrow = sCompareNo.indexOf(sTmp);
       }
       else
       {
         isShowArrow = -1;
       }
       
       //第一级菜单
       if((sTmp.indexOf("-",3) == -1)&&(sTmp.indexOf("-") > -1))
       {
         if( arrUrl[j] != "")
         {
           sTmpUrl = strUrl + arrUrl[j];
         }else
         {
           sTmpUrl = "";
         }
         
         i=0;
         sParent[i] = sTmp;
         if (sTmp == "2-400" ||sTmp == "2-500" ||sTmp == "2-600" ||sTmp == "2-800"||sTmp == "2-000")
           oCMenu.makeMenu(sTmp,'', CenterNS(arrName[j],'70'),sTmpUrl,'','70','20','','','','','#FFFFFF','','','status="";','status="";');
         else
           oCMenu.makeMenu(sTmp,'', CenterNS(arrName[j],'110'),sTmpUrl,'','110','20','','','','','#FFFFFF','','','status="";','status="";');
         continue;
         
       } 
       else if (sTmp.indexOf("-",3) > -1)
       {
     
         if( arrUrl[j] != "")
         {
           sTmpUrl = strUrl + arrUrl[j];
         }else
         {
           sTmpUrl = "";
         }
/*         
         if (sTmp == "2-100-100-1") 
         {
           alert("sTmp == 2-100-100-1");
           alert("sParent[i] == " + sParent[i]);
           alert("i = " + i);
           alert("sCompareNo == " + sCompareNo);
         }
*/         
         //与sParent[i]比较;
         if(sTmp.indexOf(sParent[i])>-1){
          
          
           //sTmp 是 s[i] 的下级菜单
           i++;
           sParent[i] = sTmp;
           //加一个菜单项,Parent是sParent[i-1],name是sTmp，url是相应的页面；
           if(arrLevel[j] != "999")
           {
           if (isShowArrow == -1)
           {
             oCMenu.makeMenu(sTmp,sParent[i-1],arrName[j],sTmpUrl,'','200','22','','','','','','','','status="http://";','status="";');
            }
           else
           {
             oCMenu.makeMenu(sTmp,sParent[i-1],'<img src="/webloan/graphics/m_arrow.gif" width="15" height="15" hspace="1" align="right">'+arrName[j],sTmpUrl,'','200','22','','','','','','','','status="http://";','status="";');
            }
           }
         
         }else {
       
           while((sTmp.indexOf(sParent[i])==-1) && (i>=0)){
             i--;
           }
         
         
           if(i>= 0){
         
             i++;
             sParent[i] = sTmp;
             //加一个菜单项,Parent是sParent[i-1],name是sTmp，url是相应的页面；
             if(arrLevel[j] != "999")
             {
/*
         if (sTmp == "1-100-101-100-101"){
           alert("show manu");
           alert("sParent[i-1] = " + sParent[i-1]);
           alert("arrName[j] = " + arrName[j]);
           alert("arrNo[j] = " + arrNo[j]);
         }
*/
             if (isShowArrow == -1)
             {
                oCMenu.makeMenu(sTmp,sParent[i-1],arrName[j],sTmpUrl,'','200','22','','','','','','','','status="http://";','status="";');
             }
             else
             {
               oCMenu.makeMenu(sTmp,sParent[i-1],'<img src="/webloan/graphics/m_arrow.gif" width="15" height="15" hspace="1" align="right">'+arrName[j],sTmpUrl,'','200','22','','','','','','','','status="http://";','status="";');
             }
             
             }
           }
         }//if(sTmp.indexOf(sParent[i])>-1)
       }  
       
  }//for (j=0;j<iLength;j++)       
  oCMenu.makeStyle();oCMenu.construct();
}//if (arrDepart.length==3)


var arrDepart = new Array(0);
var arrLevel = new Array(0);
var arrName = new Array(0);
var arrNo = new Array(0);
var sParent = new Array(0);


}