  var vm = new Vue({
            el:'#app',
            data:{
                create:'创建',
				sex:'0',
				nickname:'',
				username:'',
				password:'',
                confirmPassword:'',
                city:'',
                agree:false,
                msginfo:'',
                isCodeActive:true,
                code:'',
                sendMail:'点击发送',
                totalTime:60,
                totalTimes:60,
                msgcode:'',
                msgemail:''
            },
            created(){
            },
            methods:{
              countdown:function(){
                    if(this.totalTime <= 0){
                        this.isCodeActive = false;
                        this.sendMail='点击验证';
                        window.clearInterval(intervalObj);
                    }else{
                        this.totalTime = this.totalTime-1;
                        this.sendMail=("再次获取("+ this.totalTime+")");
                    }
                },
            sendCodeMail:function(){
                var that = this;
                this.totalTime = this.totalTimes;
                this.isCodeActive=true;
                this.sendMail = "再次获取(60)";
                this.msgcode=''
                $.ajax({
                    type:"POST",
                    url:"/send-register-mail",
                    dataType:"json",
                    timeout : 50000,
                    data: {username: this.username},
                    success:function(ret){
                        if(ret.msg.msgCode == '202'){
                        }else{
                            that.msgcode='获取频繁，请稍后再试'
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        window.intervalObj=setInterval(()=>that.countdown(),1000);
                        if(textStatus == 'timeout') {
                            that.msgcode = "连接超时，请稍后再试";
                        }
                    },
                    error:function(jqXHR){
                        that.msgcode="服务器繁忙，请稍后再试"

                    }
                });

            },
            submit:function(){
                var that = this;
                var flag = that.validation();
                if(flag){
                    $.ajax({
                            type:"POST",
                            url:" /register",
                            dataType:"json",
                            timeout : 50000,
                            data: { nickname: this.nickname, username: this.username, password:strToMD5(strToMD5(this.password)),password:strToMD5(strToMD5(this.password)),confirmPassword:strToMD5(strToMD5(this.confirmPassword)),code:this.code,city:this.city,sex:this.sex },
                            success:function(ret){
                                if(ret.msg.msgCode == '202'){
                                	location.href="login.html"
                                }else{
									that.msginfo = ret.msg.msgInfo
                                }
                            },
                            complete: function (XMLHttpRequest, textStatus) {
                                if(textStatus == 'timeout') {
                                    that.msginfo = "连接超时，请稍后再试";
                                }
                            },
                            error:function(jqXHR){
                                that.msginfo="服务器繁忙，请稍后再试"

                            }
                        });
                }

            },
            validationEmail:function(){

                var reg = new RegExp("^[a-zA-Z0-9]+([._\\-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z0-9]+$");
                if(reg.test(this.username)){
                    $("#email").removeClass('board_red');
                    this.isCodeActive = false;
                    this.msgemail="";
                    return true;
                }else{
                    $("#email").addClass('board_red');
                    this.isCodeActive = true;
                    this.msgemail="非法邮箱";
                    return false;
                }

            },
            validation:function(){
            var that=this;
                var flag = true;
                $("#agreeGroup").removeClass('board_red');
                $("#password").removeClass('board_red');
                $("#confirmPassword").removeClass('board_red');
                $("#myAddrs").removeClass('board_red');
                $("#first_name").removeClass('board_red');
                $("#email").removeClass('board_red');
                $("#code").removeClass('board_red');
                this.msginfo='';
                this.city=$("#myAddrs").val().trim();
                if(this.agree == false){
                    $("#agreeGroup").addClass('board_red');
                    this.msginfo='未同意隐私政策'
                    flag = false;
                }

                if(this.password.trim()!=this.confirmPassword.trim()){
                    $("#password").addClass('board_red');
                    $("#confirmPassword").addClass('board_red');
                    this.msginfo='两次密码不一致'
                    flag = false;
                }
                 if(this.password.length<8){
                    $("#password").addClass('board_red');
                    $("#confirmPassword").addClass('board_red');
                    this.msginfo='密码长度不能少于8位'
                    flag = false;
                }
                if(this.password.trim()==''||this.confirmPassword.trim()==''){
                    $("#password").addClass('board_red');
                    $("#confirmPassword").addClass('board_red');
                    this.msginfo='两次密码不能为空'
                    flag = false;
                }
                if($("#myAddrs").val().trim()==''){
                    $("#myAddrs").addClass('board_red');
                    this.msginfo='请选择城市'
                    flag = false;
                }
                if($("#code").val().trim()==''){
                    $("#code").addClass('board_red');
                    this.msginfo='验证码不能为空';
                    flag=false;
                }

                if(this.nickname.trim()==''){
                    $("#first_name").addClass('board_red');
                    this.msginfo='请输入昵称'
                    flag = false;
                }
                  if(that.validationEmail() == false){
                    $("#email").addClass('board_red');
                    this.msginfo='邮箱无效'
                    flag = false;
                }
                if(this.username.trim()==''){
                    $("#email").addClass('board_red');
                    this.msginfo='请输入邮箱'
                    flag = false;
                }
                return flag;
            }
            }
            })