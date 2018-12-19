function strToMD5(str){
    var md5Code = hex_md5(str);
    return md5Code;
}

function strToBase64(str){
    var base64 = window.btoa(str);
    return base64;
}

function Base64ToStr(str){
    str = window.atob(str);
    return str;
}
function getUrlParms(name){
   var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
   var r = window.location.search.substr(1).match(reg);
   if(r!=null)
   return unescape(r[2]);
   return null;
}
function validationEmail(username){
var reg = new RegExp("^[a-zA-Z0-9]+([._\\-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z0-9]+$");
if(reg.test(username)){
  return true;
}else{
  return false;
 }
}
function GetUrlRelativePath()
　　{
　　　　var url = document.location.toString();
　　　　var arrUrl = url.split("//");

　　　　var start = arrUrl[1].indexOf("/");
　　　　var relUrl = arrUrl[1].substring(start);

　　　　if(relUrl.indexOf("?") != -1){
　　　　　　relUrl = relUrl.split("?")[0];
　　　　}
　　　　return relUrl;
}
function isPC() {
   var userAgentInfo = navigator.userAgent;
   var Agents = ["Android", "iPhone",
      "SymbianOS", "Windows Phone",
      "iPad", "iPod"];
   var flag = true;
   for (var v = 0; v < Agents.length; v++) {
      if (userAgentInfo.indexOf(Agents[v]) > 0) {
         flag = false;
         break;
      }
   }
   return flag;
}
function S4() {
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
        return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}
function setCookie(name,value){
var Days = 30;
var exp = new Date();
exp.setTime(exp.getTime() + Days*24*60*60*1000);
document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name){
var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
if(arr=document.cookie.match(reg))
return unescape(arr[2]);
else
return null;
}
function S4() {
     return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
      return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}
function isEmpty(str){
    if(str == null || str.trim().length() == 0){
        return true;
    }
    return false;
}
function userPageAlert(strogerText, Text){
$('nav').after("<div id='myAlert2' class='alert alert-danger fade-in-animation opacity95' style='position: fixed;left:0;right:0;top:auto;z-index:1031;'><a href='#' class='close' data-dismiss='alert'>&times;</a><strong>"+strogerText+"</strong>"+Text+"</div>");
}