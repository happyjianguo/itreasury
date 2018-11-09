function showMenu(strPriv)
{
var bw=new lib_bwcheck() //Making browsercheck object


oCMenu=new makeCoolMenu("oCMenu") //Making the menu object. Argument: menuname
oCMenu.useframes=0 //Do you want to use the menus as coolframemenu or not? (in frames or not) - Value: 0 || 1
oCMenu.frame="frmMain" //The name of your main frame (where the menus should appear). Leave empty if you're not using frames - Value: "main_frame_name"

/*If you set this to 1 you will get a "hand" cursor when moving over the links in NS4. The only downside to it is that 
if you're using frames and you don't have spesified that the links should have no "underline" you will get lines on the links.
In that case you have to add this STYLE setting to ALL the pages that will be loaded in the main frame:
<style>A.clNS4{text-decoration:none; padding:YOUR_PADDING}</style>  (if you don't want padding just remove the padding stuff)*/
oCMenu.useNS4links=0 

//After adding the "hover effect" for netscape as well, all styles are lost. But if you want padding add it here.
oCMenu.NS4padding=0 

//If you have select boxes close to your menu the menu will check for that and hide them if they are in the way of the menu.
//This feature does unfortunatly not work in NS4!
oCMenu.checkselect=1

/*If you choose to have this code inside a linked js, or if your using frames it's important to set these variables. 
This will help you get your links to link to the right place even if your files are in different folders.
The offlineUrl variable is the actual path to the directory where you js file are locally. 
This is just so you can test it without uploading. Remember to start it with file:/// and only use slashes, no backward slashes!
Also remember to end with a slash                                                                                                 */
//oCMenu.offlineUrl="file:///C|/Inetpub/wwwroot/dhtmlcentral/" //Value: "path_to_menu_file_offline/"
//The onlineUrl variable is the online path to your script. Place in the full path to where your js file is. Remember to end with a slash.
//oCMenu.onlineUrl="" //Value: "path_to_menu_file_online/"

oCMenu.pagecheck=0 //Do you want the menu to check whether any of the subitems are out of the bouderies of the page and move them in again (this is not perfect but it hould work) - Value: 0 || 1
oCMenu.checkscroll=0 //Do you want the menu to check whether the page have scrolled or not? For frames you should always set this to 1. You can set this to 2 if you want this feature only on explorer since netscape doesn't support the window.onscroll this will make netscape slower (only if not using frames) - Value: 0 || 1 || 2
oCMenu.resizecheck=1 //Do you want the page to reload if it's resized (This should be on or the menu will crash in Netscape4) - Value: 0 || 1
oCMenu.wait=500 //How long to wait before hiding the menu on mouseout. Netscape 6 is a lot slower then Explorer, so to be sure that it works good enough there you should not have this lower then 500 - Value: milliseconds

//Background bar properties
oCMenu.usebar=0 //If you want to use a background-bar for the top items set this on - Value: 1 || 0
oCMenu.barcolor="Navy" //The color of the background bar - Value: "color"
oCMenu.barwidth="100%" //The width of the background bar. Set this to "menu" if you want it to be the same width as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
oCMenu.barheight="menu" //The height of the background bar. Set this to "menu" if you want it to be the same height as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
oCMenu.barx=0 //The left position of the bar. Set this to "menu" if you want it be the same as the left position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
oCMenu.bary=0 //The top position of the bar Set this to "menu" if you want it be the same as the top position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
oCMenu.barinheritborder=0 //Set this to 1 if you want the bar to have the same border as the top menus - Value: 0 || 1

//Placement properties
oCMenu.rows=1 //This controls whether the top items is supposed to be laid out in rows or columns. Set to 0 for columns and 1 for row - Value 0 || 1

/*
oCMenu.fromleft=10 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
oCMenu.fromtop=70 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
*/

oCMenu.pxbetween=0 //How much space you want between each of the top items. - Value: px || "%"

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
oCMenu.menuplacement=0


oCMenu.onlineUrl="/homeresource.nsf/AssetsByDescription/IntranetNavigation/$file/DropDownData.js/";
if (bw.ns4) {oCMenu.fromleft=8}
else if (bw.ns6) {oCMenu.fromleft=8}
else {oCMenu.fromleft=10}
if (bw.ns4) {oCMenu.fromtop=62}
else if (bw.ns6) {oCMenu.fromtop=67}
else {oCMenu.fromtop=70}
oCMenu.level[0]=new Array() 
oCMenu.level[0].width=120
oCMenu.level[0].height=20
oCMenu.level[0].bgcoloroff='White'
oCMenu.level[0].bgcoloron='White'
oCMenu.level[0].textcolor='#666666'
oCMenu.level[0].hovercolor='#666666'
oCMenu.level[0].style='padding:3px;font-family:Arial,Helvetica;font-size:11px; text-align:center'
oCMenu.level[0].border=1
oCMenu.level[0].bordercolor='#CCCCCC'
oCMenu.level[0].offsetX=0
oCMenu.level[0].offsetY=-1
oCMenu.level[0].NS4font='Arial,Helvetica'
oCMenu.level[0].align='bottom'
oCMenu.level[1]=new Array() 
oCMenu.level[1].width=250
oCMenu.level[1].height=20
oCMenu.level[1].bgcoloroff='White'
oCMenu.level[1].bgcoloron='White'
oCMenu.level[1].textcolor='#666666'
oCMenu.level[1].hovercolor='#FF6600'
oCMenu.level[1].style='padding:4px;font-family:Arial,Helvetica;font-size:11px; '
oCMenu.level[1].border=1
oCMenu.level[1].bordercolor='#CCCCCC'
oCMenu.level[1].offsetX=0
oCMenu.level[1].offsetY=-1
oCMenu.level[1].NS4font='Arial,Helvetica'
oCMenu.level[1].align='bottom'
oCMenu.myNS4FontSize='11px'

alert(strMenu);

oCMenu.makeMenu('Top2','', CenterNS('柜台业务','90'),'','','90','20','','','','','#666666','','','status="";','status="";')

oCMenu.makeMenu('Sub2-1','Top2',"活期存款帐户","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-1-1','Sub2-1',"银行收款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-1-1','Grand2-1-1',"业务处理","http://10.10.10.45:81/cnpc_demo/Business/Inputyhsk.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-1-2','Grand2-1-1',"业务复核","http://10.10.10.45:81/cnpc_demo/Business/Approveyhsk.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-8','Sub2-1',"银行付款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-8-1','Grand2-1-8',"业务处理","http://10.10.10.45:81/cnpc_demo/business/InputCheque.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-8-2','Grand2-1-8',"业务复核","http://10.10.10.45:81/cnpc_demo/business/ApproveCheque.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-2','Sub2-1',"支票付款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-2-1','Grand2-1-2',"业务处理","http://10.10.10.45:81/cnpc_demo/Cheque/InputCheque.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-2-2','Grand2-1-2',"业务复核","http://10.10.10.45:81/cnpc_demo/Cheque/ApproveCheque.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-3','Sub2-1',"现金付款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-3-1','Grand2-1-3',"业务处理","http://10.10.10.45:81/cnpc_demo/Cash/InputCash.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-3-2','Grand2-1-3',"业务复核","http://10.10.10.45:81/cnpc_demo/Cash/ApproveCash.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-4','Sub2-1',"汇票付款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-4-1','Grand2-1-4',"业务处理","http://10.10.10.45:81/cnpc_demo/Huipaio/InputHP.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-4-2','Grand2-1-4',"业务复核","http://10.10.10.45:81/cnpc_demo/Huipaio/ApproveHP.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-5','Sub2-1',"内部转帐","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-5-1','Grand2-1-5',"业务处理","http://10.10.10.45:81/cnpc_demo/Business/Inputnbzz.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-5-2','Grand2-1-5',"业务复核","http://10.10.10.45:81/cnpc_demo/Business/Approvenbzz.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-5-3','Grand2-1-5',"业务确认","http://10.10.10.45:81/cnpc_demo/Confirm/Confirmsingle-nbzz/Confirm.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-6','Sub2-1',"委托收款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-6-1','Grand2-1-6',"业务处理","http://10.10.10.45:81/cnpc_demo/Wtsk/InputWtsk.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-6-2','Grand2-1-6',"业务复核","http://10.10.10.45:81/cnpc_demo/Wtsk/ApproveWtsk.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-6-3','Grand2-1-6',"业务确认","http://10.10.10.45:81/cnpc_demo/Confirm/Confirmsingle-wtsk/Confirm.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-7','Sub2-1',"委托存款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-7-1','Grand2-1-7',"业务处理","http://10.10.10.45:81/cnpc_demo/Wtck/InputWtck.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-7-2','Grand2-1-7',"业务复核","http://10.10.10.45:81/cnpc_demo/Wtck/ApproveWtck.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-11','Sub2-1',"保证金存款","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-11-1','Grand2-1-11',"业务处理","http://10.10.10.45:81/cnpc_demo/xyck/InputWtck.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-1-11-2','Grand2-1-11',"业务复核","http://10.10.10.45:81/cnpc_demo/xyck/ApproveWtck.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-9','Sub2-1',"国债买入","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand2-1-9-1','Grand2-1-9',"业务处理","http://10.10.10.45:81/cnpc_demo/GuoZhai/Buy.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand2-1-9-2','Grand2-1-9',"业务复核","http://10.10.10.45:81/cnpc_demo/GuoZhai/ApproveBuy.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-1-10','Sub2-1',"国债卖出","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand2-1-10-1','Grand2-1-10',"业务处理","http://10.10.10.45:81/cnpc_demo/GuoZhai/Sell.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand2-1-10-2','Grand2-1-10',"业务复核","http://10.10.10.45:81/cnpc_demo/GuoZhai/ApproveSell.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-1-12','Sub2-1',"一付多收业务","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-1-12-1','Grand2-1-12',"业务处理","http://10.10.10.45:81/cnpc_demo/Yewu/yewuchuli/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-12-2','Grand2-1-12',"业务复核","http://10.10.10.45:81/cnpc_demo/Yewu/yewufuhe/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-1-12-3','Grand2-1-12',"业务勾帐","http://10.10.10.45:81/cnpc_demo/Yewu/yewugouzhang/first.html",'','100','22','','','','','','','','status="http://";','status="";')



oCMenu.makeMenu('Sub2-2','Top2',"定期存款帐户","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-2-1','Sub2-2',"定期开立","",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-2-1-1','Grand2-2-1',"业务处理","http://10.10.10.45:81/cnpc_demo/Fixed/InputFixed.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-2-1-2','Grand2-2-1',"业务复核","http://10.10.10.45:81/cnpc_demo/Fixed/ApproveFixed.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-2-2','Sub2-2',"定期到期转活期","",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-2-2-1','Grand2-2-2',"业务处理","http://10.10.10.45:81/cnpc_demo/Saving/InputSaving.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-2-2-2','Grand2-2-2',"业务复核","http://10.10.10.45:81/cnpc_demo/Saving/ApproveSaving.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-2-3','Sub2-2',"定期续期转存","",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-2-3-1','Grand2-2-3',"业务处理","http://10.10.10.45:81/cnpc_demo/fixfix/Inputfixfix.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-2-3-2','Grand2-2-3',"业务复核","http://10.10.10.45:81/cnpc_demo/fixfix/Approvefixfix.htm",'','100','22','','','','','','','','status="http://";','status="";')


oCMenu.makeMenu('Sub22-2','Top2',"通知存款帐户","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand22-2-1','Sub22-2',"通知存款开立","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand22-2-1-1','Grand22-2-1',"业务处理","http://10.10.10.45:81/cnpc_demo/TongZhi/InputFixed.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand22-2-1-2','Grand22-2-1',"业务复核","http://10.10.10.45:81/cnpc_demo/Tongzhi/ApproveFixed.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand22-2-2','Sub22-2',"通知存款支取","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand22-2-2-1','Grand22-2-2',"业务处理","http://10.10.10.45:81/cnpc_demo/Tongzhi/InputSaving.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand22-2-2-2','Grand22-2-2',"业务复核","http://10.10.10.45:81/cnpc_demo/Tongzhi/ApproveSaving.htm",'','100','22','','','','','','','','status="http://";','status="";')


oCMenu.makeMenu('Sub2-3','Top2',"贷款帐户","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-3-1','Sub2-3',"信托贷款发放","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-1-1','Grand2-3-1',"业务处理","http://10.10.10.45:81/cnpc_demo/CreditLoan/Inputxtff.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-1-2','Grand2-3-1',"业务复核","http://10.10.10.45:81/cnpc_demo/CreditLoan/Approvextff.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-1-3','Grand2-3-1',"业务确认","http://10.10.10.45:81/cnpc_demo/CreditLoan/Confirmxtff.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-3-2','Sub2-3',"信托贷款收回","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-2-1','Grand2-3-2',"业务处理","http://10.10.10.45:81/cnpc_demo/CreditLoan/Inputxtsh.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-2-2','Grand2-3-2',"业务复核","http://10.10.10.45:81/cnpc_demo/CreditLoan/Approvextsh.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-2-3','Grand2-3-2',"业务确认","http://10.10.10.45:81/cnpc_demo/CreditLoan/Confirmxtsh.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-3-3','Sub2-3',"委托贷款发放","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-3-1','Grand2-3-3',"业务处理","http://10.10.10.45:81/cnpc_demo/Wtloan/Inputwtff.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-3-2','Grand2-3-3',"业务复核","http://10.10.10.45:81/cnpc_demo/Wtloan/Approvewtff.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-3-3','Grand2-3-3',"业务确认","http://10.10.10.45:81/cnpc_demo/Wtloan/Confirmwtff.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-3-4','Sub2-3',"委托贷款收回","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-4-1','Grand2-3-4',"业务处理","http://10.10.10.45:81/cnpc_demo/Wtloan/Inputwtsh.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-4-2','Grand2-3-4',"业务复核","http://10.10.10.45:81/cnpc_demo/Wtloan/Approvewtsh.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-4-3','Grand2-3-4',"业务确认","http://10.10.10.45:81/cnpc_demo/Wtloan/Confirmwtsh.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-3-5','Sub2-3',"贴现发放","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-5-1','Grand2-3-5',"业务处理","http://10.10.10.45:81/cnpc_demo/Tx/InputTxff.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-5-2','Grand2-3-5',"业务复核","http://10.10.10.45:81/cnpc_demo/Tx/ApproveTxff.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-5-3','Grand2-3-5',"业务确认","http://10.10.10.45:81/cnpc_demo/Tx/ConfirmTxff.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-3-6','Sub2-3',"贴现收回","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-6-1','Grand2-3-6',"业务处理","http://10.10.10.45:81/cnpc_demo/Tx/InputTxsh.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-6-2','Grand2-3-6',"业务复核","http://10.10.10.45:81/cnpc_demo/Tx/ApproveTxsh.htm",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-6-3','Grand2-3-6',"业务确认","http://10.10.10.45:81/cnpc_demo/Tx/ConfirmTxsh.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-3-7','Sub2-3',"多笔贷款收回","",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-7-1','Grand2-3-7',"业务处理","http://10.10.10.45:81/cnpc_demo/MultLoan/yewuchuli/First.html",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-7-2','Grand2-3-7',"业务复核","http://10.10.10.45:81/cnpc_demo/MultLoan/yewufuhe/First.html",'','100','22','','','','','','','','status="http://";','status="";')
oCMenu.makeMenu('SubGrand2-3-7-3','Grand2-3-7',"业务勾帐","http://10.10.10.45:81/cnpc_demo/MultLoan/yewugouzhang/first.html",'','100','22','','','','','','','','status="http://";','status="";')


oCMenu.makeMenu('Sub2-4','Top2',"委托业务","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-4-3','Sub2-4',"委托上收资金帐户设置","http://10.10.10.45:81/cnpc_demo/bailment/wtsszjzhsz/wtsszjzhsz.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-4','Sub2-4',"委托上收资金","http://10.10.10.45:81/cnpc_demo/bailment/wtsszj/wtsszj.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-5','Sub2-4',"上存资金帐户设置","http://10.10.10.45:81/cnpc_demo/bailment/sczjzhsz/sczjzhsz.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-6','Sub2-4',"上存资金调回及发放<br>负息资金","http://10.10.10.45:81/cnpc_demo/bailment/sczjdh/sczjdh0.htm",'','130','36','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-1','Sub2-4',"上存资金调回","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-4-1-1','Grand2-4-1',"业务处理","http://10.10.10.45:81/cnpc_demo/up/Inputnbzz.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-4-1-2','Grand2-4-1',"业务复核","http://10.10.10.45:81/cnpc_demo/up/Approvenbzz.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-4-1-3','Grand2-4-1',"业务确认","http://10.10.10.45:81/cnpc_demo/up/Confirm.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-2','Sub2-4',"还短负","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-4-2-1','Grand2-4-2',"业务处理","http://10.10.10.45:81/cnpc_demo/return/Inputnbzz.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-4-2-2','Grand2-4-2',"业务复核","http://10.10.10.45:81/cnpc_demo/return/Approvenbzz.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('SubGrand2-4-2-3','Grand2-4-2',"业务确认","http://10.10.10.45:81/cnpc_demo/return/Confirm.htm",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-7','Sub2-4',"客户委托资金调拨<br>帐户设置","http://10.10.10.45:81/cnpc_demo/bailment/khwtzjdbzhsz/khwtzjdbzhsz.htm",'','130','36','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-8','Sub2-4',"客户委托资金调拨","http://10.10.10.45:81/cnpc_demo/bailment/khwtzjdb/khwtzjdb.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-10','Sub2-4',"发放短期贷款设置","http://10.10.10.45:81/cnpc_demo/bailment/Shortloan/khwtzjdbzhsz.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-11','Sub2-4',"发放短期贷款","http://10.10.10.45:81/cnpc_demo/bailment/ShortloanActive/khwtzjdb.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-4-9','Sub2-4',"循环贷款帐户设置","http://10.10.10.45:81/cnpc_demo/bailment/fbjszhsz/fbjsxhdkzhsz.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub2-7','Top2',"通存通兑业务","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub2-7-1','Sub2-7',"通存通兑签认","http://10.10.10.45:81/cnpc_demo/sign/sign.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub2-7-2','Sub2-7',"通存通兑确认","http://10.10.10.45:81/cnpc_demo/Confirmsign/sign.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub2-5','Top2',"总帐类","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-5-1','Sub2-5',"业务处理","http://10.10.10.45:81/cnpc_demo/GLTrans/chuli/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-5-2','Sub2-5',"业务复核","http://10.10.10.45:81/cnpc_demo/GLTrans/fuhe/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub2-10','Top2',"交易费用处理","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-10-1','Sub2-10',"业务处理","http://10.10.10.45:81/cnpc_demo/FeeTrans/chuli/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-10-2','Sub2-10',"业务复核","http://10.10.10.45:81/cnpc_demo/FeeTrans/fuhe/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub2-8','Top2',"特殊业务处理","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand2-8-1','Sub2-8',"业务处理","http://10.10.10.45:81/cnpc_demo/SpecialTrans/chuli/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-8-2','Sub2-8',"业务复核","http://10.10.10.45:81/cnpc_demo/SpecialTrans/fuhe/First.html",'','100','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand2-8-3','Sub2-8',"业务确认","http://10.10.10.45:81/cnpc_demo/SpecialTrans/confirm/Confirm.htm",'','100','22','','','','','','','','status="http://";','status="";')




oCMenu.makeMenu('Top3','', CenterNS('查询','90'),'','','90','20','','','','','#666666','','','status="";','status="";')

oCMenu.makeMenu('Sub3-1','Top3',"帐户信息查询","http://10.10.10.45:81/cnpc_demo/Search/Account/accountsearch.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub3-16','Top3',"贷款查询","http://10.10.10.45:81/cnpc_demo/Search/Loan/Loan.htm",'','130','22','','','','','','','','status="https://";','status="";')

oCMenu.makeMenu('Sub3-12','Top3',"定期存款查询","http://10.10.10.45:81/cnpc_demo/Search/Fix/Fix.htm",'','130','22','','','','','','','','status="https://";','status="";')

oCMenu.makeMenu('Sub3-99','Top3',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-4','Top3',"帐户余额查询","http://10.10.10.45:81/cnpc_demo/Search/yue/yue.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub3-6','Top3',"帐户金额查询","http://10.10.10.45:81/cnpc_demo/Search/CustomerBalance/accountsearch.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-11','Top3',"平均余额分析","http://10.10.10.45:81/cnpc_demo/Search/analyze/search.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-99','Top3',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-17','Top3',"存款贷款汇总查询","http://10.10.10.45:81/cnpc_demo/Search/Balance/accountbalance.htm",'','130','22','','','','','','','','status="https://";','status="";')

oCMenu.makeMenu('Sub3-3','Top3',"日结汇总查询","http://10.10.10.45:81/cnpc_demo/Search/DayEnd/accountsearch.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub3-7','Top3',"客户汇总查询","http://10.10.10.45:81/cnpc_demo/Search/CustomerTotalBalance/accountsearch.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-5','Top3',"存款明细查询","http://10.10.10.45:81/cnpc_demo/Search/AccountBalance/accountbalance.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-99','Top3',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-2','Top3',"交易纪录查询","http://10.10.10.45:81/cnpc_demo/Search/TransRecord/Record.htm",'','130','22','','','','','','','','status="https://";','status="";')

oCMenu.makeMenu('Sub3-99','Top3',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-8','Top3',"结息记录查询","http://10.10.10.45:81/cnpc_demo/Search/Interest/accountsearch.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-9','Top3',"委托手续费结算查询","http://10.10.10.45:81/cnpc_demo/Search/handle/accountsearch.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-10','Top3',"委托担保费结算查询","http://10.10.10.45:81/cnpc_demo/Search/gurantee/accountsearch.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-99','Top3',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-13','Top3',"客户委托资金调拨查询","http://10.10.10.45:81/cnpc_demo/Search/khwtzjdb_search/khwtzjdb1.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-14','Top3',"上存资金调回及发放<br>负息资金查询","http://10.10.10.45:81/cnpc_demo/Search/sczjdh_search/sczjdh1.htm",'','130','36','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub3-15','Top3',"委托上收资金查询","http://10.10.10.45:81/cnpc_demo/Search/wtsszj_search/wtsszj1.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Top4','', CenterNS('帐户','90'),'','','90','20','','','','','#666666','','','status="";','status="";')

oCMenu.makeMenu('Sub4-1','Top4',"客户信息处理","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand4-1-1','Sub4-1',"新增客户信息","http://10.10.10.45:81/cnpc_demo/Account/Client/ClientAdd/First.html",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand4-1-2','Sub4-1',"修改客户信息","http://10.10.10.45:81/cnpc_demo/Account/Client/ClientEdit/First.html",'','130','22','','','','','','','','status="";','status="";')


oCMenu.makeMenu('Sub4-2','Top4',"帐户信息处理","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand4-2-1','Sub4-2',"新增帐户处理","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand4-2-1-1','Grand4-2-1',"开户处理","http://10.10.10.45:81/cnpc_demo/Account/account_create/openaccount.html",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand4-2-1-2','Grand4-2-1',"复核新增帐户信息","http://10.10.10.45:81/cnpc_demo/Account/accountCheck/first.html",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand4-2-2','Sub4-2',"已有帐户处理","",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand4-2-2-1','Grand4-2-2',"修改帐户信息","http://10.10.10.45:81/cnpc_demo/Account/AccountEdit/first.html",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand4-2-2-2','Grand4-2-2',"单一复核帐户修改信息","http://10.10.10.45:81/cnpc_demo/Account/accountCheckEdit/first.html",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('SubGrand4-2-2-3','Grand4-2-2',"批量复核帐户修改信息","http://10.10.10.45:81/cnpc_demo/Account/accountMassCheckEdit/first.html",'','130','22','','','','','','','','status="";','status="";')


oCMenu.makeMenu('Sub4-5','Top4',"帐户余额表","http://10.10.10.45:81/cnpc_demo/Account/Balance/accountsearch.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub4-6','Top4',"帐户对帐单","http://10.10.10.45:81/cnpc_demo/Account/Customerstatement/accountsearch.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Top5','', CenterNS('系统','90'),'','','90','20','','','','','#666666','','','status="";','status="";')

oCMenu.makeMenu('Sub1-1','Top5',"开机处理","http://10.10.10.45:81/cnpc_demo/System/OpenSystem.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub1-10','Top5',"关机处理","http://10.10.10.45:81/cnpc_demo/System/CloseSystem.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub1-8','Top5',"日结科目汇总查询","http://10.10.10.45:81/cnpc_demo/Search/Gl_dayend/accountsearch.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub1-11','Top5',"开户行余额汇总查询","http://10.10.10.45:81/cnpc_demo/Search/BankBalance/accountsearch.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub6-6','Top5',"更改密码","http://10.10.10.45:81/cnpc_demo/System/ChangePassword.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub6-7','Top5',"票据打印设置","http://10.10.10.45:81/cnpc_demo/System/PrintSetup.htm",'','130','22','','','','','','','','status="";','status="";')


oCMenu.makeMenu('Top7','', CenterNS('设置','90'),'','','90','20','','','','','#666666','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-1','Top7',"办事处编号设置","http://10.10.10.45:81/cnpc_demo/Config/Location/location4.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-2','Top7',"开户行编号设置","http://10.10.10.45:81/cnpc_demo/Config/Bank/BkConfig.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-7','Top7',"标准摘要定义设置","http://10.10.10.45:81/cnpc_demo/Config/Abstract/Abstract.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-4','Top7',"现金流向设置","http://10.10.10.45:81/cnpc_demo/Config/RemarkType/Remark.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub7-99','Top7',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-14','Top7',"业务处理默认值设置","http://10.10.10.45:81/cnpc_demo/Config/defaultvalue/business.html",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand6-5-3','Top7',"科目设置","http://10.10.10.45:81/cnpc_demo/Config/BusinessType/business.html",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-13','Top7',"帐户类型编码设置","http://10.10.10.45:81/cnpc_demo/Config/AccountDefault/openaccount.html",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub7-99','Top7',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-12','Top7',"银行利率设置","http://10.10.10.45:81/cnpc_demo/BankInterest/first.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand6-5-10','Top7',"利率计划设置","http://10.10.10.45:81/cnpc_demo/interestplan/first.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-6','Top7',"利息费用计算设置","http://10.10.10.45:81/cnpc_demo/expense/First.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-17','Top7',"交易费用设置","http://10.10.10.45:81/cnpc_demo/Config/Fee/business.html",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub7-99','Top7',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-8','Top7',"业务提醒设置","http://10.10.10.45:81/cnpc_demo/System/BizAlert.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub7-99','Top7',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-15','Top7',"总帐类业务设置","http://10.10.10.45:81/cnpc_demo/Config/Glset/business.html",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand6-5-16','Top7',"特殊业务类型设置","http://10.10.10.45:81/cnpc_demo/Config/SpecialType/SpecialType.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub7-99','Top7',"","",'','0','2','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-5-5','Top7',"系统参数设置","",'','130','22','','','','','','','','status="";','status="";')



oCMenu.makeMenu('Top6','', CenterNS('其他','90'),'','','90','20','','','','','#666666','','','status="";','status="";')

oCMenu.makeMenu('Sub6-1','Top6',"计算费用","http://10.10.10.45:81/cnpc_demo/Interest/first.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub6-2','Top6',"累计费用调整","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand6-2-1','Sub6-2',"调整","http://10.10.10.45:81/cnpc_demo/jishu/JishuBeginNew.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand6-2-2','Sub6-2',"复核","http://10.10.10.45:81/cnpc_demo/jishu/JishuConfirm.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub6-3','Top6',"应付利息及费用匡算","http://10.10.10.45:81/cnpc_demo/calculate/KuangsuanBeginNew.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub6-11','Top6',"资金限制处理","http://10.10.10.45:81/cnpc_demo/Freeze/ConfirmFreeze.htm",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Sub6-4','Top6',"委托付款凭证","",'','130','22','','','','','','','','status="http://";','status="";')

oCMenu.makeMenu('Grand6-4-1','Sub6-4',"委托付款凭证加密","http://10.10.10.45:81/cnpc_demo/password/first.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Grand6-4-2','Sub6-4',"委托付款凭证设置","http://10.10.10.45:81/cnpc_demo/checksetup/first.htm",'','130','22','','','','','','','','status="";','status="";')

oCMenu.makeMenu('Sub6-10','Top6',"网上业务处理","http://10.10.10.45:81/cnpc_demo/Internet/ConfirmInternet.htm",'','130','22','','','','','','','','status="http://";','status="";')


oCMenu.makeMenu('Top8','', CenterNS('注销','90'),'http://10.10.10.45:81/cnpc_demo/library/blank.htm','','90','20','','','','','#666666','','','status="";','status="";')

oCMenu.makeStyle();oCMenu.construct()
}