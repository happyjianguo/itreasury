(function(){

	
	String.prototype.trim = function() {
	  return this.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	//定义初始化方法
	var createClass = {
		create:function(){
			return function(){
				this.initialize.apply(this,arguments);
			}
		}
	}	
	
	//拼装客户端数据公共方法
	var operator = {
		getValueArray:function()
		{
			this.valueArray = new Array();
			for(var i=0;i<this.nameArray.length;i++)
			{
				if(document.getElementById(this.nameArray[i]))
				{
					this.valueArray[i] = document.getElementById(this.nameArray[i]).value.replace(/,/g,"");
				}else if(document.getElementsByName(this.nameArray[i])&&document.getElementsByName(this.nameArray[i]).length>0)
				{
					this.valueArray[i] = document.getElementsByName(this.nameArray[i])[0].value.replace(/,/g,"");
				}else
				{
					this.valueArray[i] = "";
				}
			}
		},
		formatData:function(){
			this.getValueArray();
			if(this.nameArray.length==0||this.valueArray.length==0)
			{
				alert("待签名数据异常!");
				return "";
			}
			var returnValue = "<ORIGINALDATA>";
			returnValue += "<ITEMS>";
			for (var i = 0; i < this.nameArray.length; i++) {
				var strTempName = this.nameArray[i].trim().toUpperCase();
				if(this.valueArray[i])
				{
					var strTempValue = this.valueArray[i].trim();
				}else
				{
					var strTempValue = "";
				}
				returnValue += "<" + strTempName + ">";
				returnValue += strTempValue;
				returnValue += "</" + strTempName + ">";				
			}
			returnValue += "</ITEMS>";
			returnValue += "</ORIGINALDATA>";	
			return returnValue;	
		}
	};
	
	//资金划拨
	window.CaptransferOperator = createClass.create();
	CaptransferOperator.prototype = {
		initialize:function(){
			this.nameArray = ["payerAcctID","amount","confirmUserID","timestamp"];
		}
	}
	$.extend(CaptransferOperator.prototype,operator);
	
	//到期续存
	window.DriveFixDepositOperator = createClass.create();
	DriveFixDepositOperator.prototype = {
		initialize:function(){
			this.nameArray = ["payeeAcctID","sDepositBillNo","amount","sDepositBillPeriod","sDepositBillStartDate","sDepositInterestToAccountID","confirmUserID","timestamp"];
		}		
	}
	$.extend(DriveFixDepositOperator.prototype,operator);
	
	//定期支取
	window.FixedToCurrentTransferOperator = createClass.create();
	FixedToCurrentTransferOperator.prototype = {
		initialize:function(){
			this.nameArray = ["depositNo","payeeAcctID","interestPayeeAcctID","amount","confirmUserID","timestamp"];
		}		
	}
	$.extend(FixedToCurrentTransferOperator.prototype,operator);
	
	//通知支取
	window.NotifyDepositDrawOperator = createClass.create();
	NotifyDepositDrawOperator.prototype = {
		initialize:function(){
			this.nameArray = ["depositNo","payeeAcctID","interestPayeeAcctID","amount","confirmUserID","timestamp"];
		}	
	}
	$.extend(NotifyDepositDrawOperator.prototype,operator);
	
	//定期开立
	window.OpenFixDepositOperator = createClass.create();
	OpenFixDepositOperator.prototype = {
		initialize:function(){
			this.nameArray = ["payeeAcctID","payerAcctID","fixedDepositTime","amount","confirmUserID","timestamp"];
		}		
	}
	$.extend(OpenFixDepositOperator.prototype,operator);
	
	//通知开立
	window.OpenNotifyAccountOperator = createClass.create();
	OpenNotifyAccountOperator.prototype = {
		initialize:function(){
			this.nameArray = ["payeeAcctID","payerAcctID","noticeDay","amount","confirmUserID","timestamp"];
		}		
	}
	$.extend(OpenNotifyAccountOperator.prototype,operator);
	
	
})();