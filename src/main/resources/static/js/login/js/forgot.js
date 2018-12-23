

    function validationUsername(){
      var username = $("#username").val();
      if(!validationEmail(username)){
        $("#username").addClass('board_red');
        $("#usernameInfo").text('邮箱无效');
        return false;
      }else{
        $("#username").removeClass('board_red');
        $("#usernameInfo").text('');
        return true;
      }
    }


 function rest(){
     var code = getUrlParms("token");
     var flag = true;
     var password = $("#password").val();
     var confirmPassword = $("#confirmPassword").val();

                    $("#password").removeClass('board_red');
                    $("#confirmPassword").removeClass('board_red');
    $("#passwordMsgInfo").text('');
       if(password.length<8){
                    $("#password").addClass('board_red');
                    $("#confirmPassword").addClass('board_red');
                    $("#passwordMsgInfo").text('密码长度不能少于8位');
                    flag = false;
                }
                if(password.trim()==''||confirmPassword.trim()==''){
                    $("#password").addClass('board_red');
                    $("#confirmPassword").addClass('board_red');
                    $("#passwordMsgInfo").text('两次密码不能为空');
                    flag = false;
                }
                if(password.trim()!=confirmPassword.trim()){
                    $("#password").addClass('board_red');
                    $("#confirmPassword").addClass('board_red');
                    $("#passwordMsgInfo").text('两次密码不一致');
                    flag = false;
                }

                if(flag){
                    $.ajax({
                            type:"POST",
                            url:" /rest-password",
                            dataType:"json",
                            timeout : 50000,
                            data: {code: code, password:strToMD5(strToMD5(password)),confirmPassword:strToMD5(strToMD5(confirmPassword))},
                            success:function(ret){
                                if(ret.msg.msgCode == '200'){forgotPassword
                                	 $("#successMail").removeClass("hidden");
                                	 $("#resetPassword").addClass("hidden");
                                	 $("#msg").text('重置密码完成');
                                }else{
                            $("#passwordMsgInfo").text(ret.msg.msgInfo);
                                }
                            },
                            complete: function (XMLHttpRequest, textStatus) {
                                if(textStatus == 'timeout') {
                                    $("#passwordMsgInfo").text("连接超时，请稍后再试");
                                }
                            },
                            error:function(jqXHR){
                                $("#passwordMsgInfo").text("服务器繁忙，请稍后再试");

                            }
                        });
    }
}
    function sendMail(){
    var flag = validationEmail($("#username").val());
    $("#mailMsgInfo").text('');
                if(flag){
                    $.ajax({
                            type:"POST",
                            url:" /forgot-mail",
                            dataType:"json",
                            timeout : 50000,
                            data: {username: $("#username").val().trim() },
                            success:function(ret){
                                if(ret.msg.msgCode == '200'){forgotPassword
                                	 $("#successMail").removeClass("hidden");
                                	 $("#forgotPassword").addClass("hidden");
                                	 $("#msg").text('重置密码邮件已发送到邮箱,请注意查收');
                                }else{
                            $("#mailMsgInfo").text(ret.msg.msgInfo);
                                }
                            },
                            complete: function (XMLHttpRequest, textStatus) {
                                if(textStatus == 'timeout') {
                                    $("#mailMsgInfo").text("连接超时，请稍后再试");
                                }
                            },
                            error:function(jqXHR){
                                $("#mailMsgInfo").text("服务器繁忙，请稍后再试");

                            }
                        });
    }
}