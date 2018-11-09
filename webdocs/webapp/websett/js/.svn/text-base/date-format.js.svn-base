

function getFormatFirstDateOfMonth(year,month){
	return datetoString(getFirstDateOfMonth(year,month));
}

function getFormatLastDateOfMonth(year,month){
	return datetoString(getLastDateOfMonth(year,month));
}
function getFormatFirstDateOfNextMonth(year,month){
	return datetoString(getFirstDateOfNextMonth(year,month));
}


function getFirstDateOfMonth(year,month)   
{   
 var new_year = year;    //取当前的年份   
 var new_month = month;  //取当前的月份 
 if(month>12)            //如果当前大于12月，则年份转到下一年   
 {   
  new_month -=12;        //月份减   
  new_year++;            //年份增   
 }    
 return (new Date(new_year,new_month-1,1));
}

function getLastDateOfMonth(year,month)   
{   
 var new_year = year;    //取当前的年份   
 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）   
 if(month>12)            //如果当前大于12月，则年份转到下一年   
 {   
  new_month -=12;        //月份减   
  new_year++;            //年份增   
 }   
 var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天 
 return (new Date(new_date.getTime()-1000*60*60*24));//获取当月最后一天日期    
}
/**
* 格式化date为yyyy-mm-dd
*/
function datetoString(date){
  if(date==null||date=='')
    return;
  var year = date.getFullYear();
  var month = date.getMonth()+1;
  var day = date.getDate();
  var hour = date.getHours();
  var minutes = date.getMinutes();
  var seconds = date.getSeconds();
  if(month<10){
  	month='0'+month;
  }
  if(day<10){
  	day='0'+day;
  }
  var rdate= "";
 // rdate =year+"-"+month+"-"+day+" "+hour+":"+minutes+":"+seconds;
  rdate =year+"-"+month+"-"+day;
  return rdate;
}
function getFirstDateOfNextMonth(year,month)   
{   
 var new_year = year;    //取当前的年份   
 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）   
 if(month>12)            //如果当前大于12月，则年份转到下一年   
 {   
  new_month -=12;        //月份减   
  new_year++;            //年份增   
 }   
 var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天 
 return (new Date(new_date.getTime()));//获取下个月的第一天日期    
}