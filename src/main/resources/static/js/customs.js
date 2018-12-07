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
