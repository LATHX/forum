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
$("#app-growl").append('<div class="alert alert-dark alert-dismissible fade show" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>Click the x on the upper right to dismiss this little thing. Or click growl again to show more growls</div>')})