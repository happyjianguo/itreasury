
/**
 *
 */
var trHeight = 18;//td 默认高度
var tdWidth = 94;//td 摘要宽度
var defaultSize = '12px';//默认字体大小
var className = "abstract";//需要控制的td id

function getElementsByClassName(className, tagName) 
{
	var ele = [], all = document.getElementsByTagName(tagName || "*");
	for (var i = 0; i < all.length; i++) 
	{
		if (all[i].className.match(new RegExp("(\\s|^)" + className + "(\\s|$)"))) 
		{
			ele[ele.length] = all[i];
		}
	}
	//alert(ele);
	return ele;
}

function getMLenEle() 
{
	var elements = getElementsByClassName(className, "td");
	var tempCount = 0;
	var mLenEles = new Array();
	
	for (var i = 0; i < elements.length; i++)
	{
		var element = elements[i];
		if ((element) && isStrong(element))
		{
			mLenEles[mLenEles.length] = element;
		}
	}
	return mLenEles;
}

function calculateWidth(element) 
{
	return element.offsetHeight;
	//return element.height();
}

function isStrong(element) 
{
	if ( calculateWidth(element) > trHeight)
	{	
		return true;
	}
	return false
}

function narrowSize(element) 
{
	var fontSize = element.style.fontSize;
	if (!fontSize)
	{
		fontSize = defaultSize;
	} 
	//alert(fontSize);//px
	element.style.fontSize = subNo(fontSize) - 0.4;
	
	if (isStrong(element))
	{
		//alert('0')
		narrowSize(element);
		//alert('1')
	}
	
}

function subNo(fontSize)
{
	var size = fontSize.substring(0, fontSize.length - 2);
	//alert(size);
	return size;
}

function setSize() 
{	
	var elements = getMLenEle();
	for (var i = 0; i < elements.length; i++)
	{
		var element = elements[i];
		narrowSize(element);
	}
	return;
}