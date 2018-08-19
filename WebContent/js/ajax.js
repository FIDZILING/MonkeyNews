var xmlHttpRequest = createXHR();
function createXHR(){
	var request = false;
    //一般先判断非IE浏览器
    //window对象中有XMLHttpRequest存在就是非IE，包括（IE7，IE8）
    if(window.XMLHttpRequest){
        request=new XMLHttpRequest();//非IE以及IE7，IE8浏览器
        if(request.overrideMimeType){
            request.overrideMimeType("text/xml");//重置mime类型
        }
	}
	//window对象中有ActiveXObject属性存在就是IE浏览器的低版本
	else if(window.ActiveXObject){
    var versions = ['Microsoft.XMLHTTP',
                'MSXML.XMLHTTP',
                'Msxml2.XMLHTTP.7.0',
                'Msxml2.XMLHTTP.6.0',
                'Msxml2.XMLHTTP.5.0', 
                'Msxml2.XMLHTTP.4.0', 
                'MSXML2.XMLHTTP.3.0', 
                'MSXML2.XMLHTTP'];//各种IE浏览器创建Ajax对象时传递的参数
          for(var i=0; i<versions.length; i++){
                try{
                    request=new ActiveXObject(versions[i]);//各个IE浏览器版本的参数不同
                    if(request){
                        return request;
                    }
                }catch(e){
                    request=false;
                }
           }
    }
     return request;
}

function sendRequest(url,params,method,handler){
	if(!xmlHttpRequest)return false;
	xmlHttpRequest.onreadystatechange = handler;
	if(method == 'GET'){
		xmlHttpRequest.open(method,url+'?'+params,true);
		xmlHttpRequest.send(null);
	}
	if(method == 'POST'){
		xmlHttpRequest.open(method,url,true);
		xmlHttpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttpRequest.send(params);
	}
}