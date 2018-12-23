var vm = new Vue({
            el:'#app',
            data:{
                userName:'',
                userPassword:'',
                isLoginCodeActive:false,
                isLoginActive:false,
                loginCodeButton: '点击验证',
                loginButton: '登录',
                totalTimes:60,
                msgInfo:'',
                token:'',
                loginCodeClass:'btn-primary',
                totalTime:0,
                publicKey:'',
                rememberMe:false,
                warming:'',
                username_board_red:'',
                password_board_red:'',
                isLoginActive:false
            },
            methods:{
                beforeCreate:function(){
                    window.intervalObj="";
                },
                countdown:function(){
                    if(this.totalTime <= 0){
                        this.isLoginCodeActive = false;
                        this.loginCodeButton='点击验证';
                        this.loginCodeClass='btn-primary'
                        window.clearInterval(intervalObj);
                        this.msgInfo=''
                    }else{
                        this.totalTime = this.totalTime-1;
                        this.loginCodeClass='btn btn-danger'
                        this.loginCodeButton=(this.msgInfo+this.totalTime+"s后重新发送");
                    }
                },
                login:function(){
                    this.isLoginActive=true
                    this.loginButton='登录中...'
                    this.password_board_red='';
                    this.username_board_red='';
                    this.warming="";
                    var isCommit = true
                    var that = this;
                    var encrypt = new JSEncrypt();
                    encrypt.setPublicKey(this.publicKey)
                    var encrypted = encrypt.encrypt(this.token+strToMD5(strToMD5(this.userPassword)));
                    if(this.token.trim() == ''||this.publicKey.trim()==''){
                        this.warming="请点击进行验证"
                        isCommit = false
                        this.isLoginActive=false
                        this.loginButton="登录"
                    }else if(this.userName.trim() == ''){
                        this.warming="请输入用户名"
                        this.username_board_red='board_red';
                        isCommit = false
                        this.isLoginActive=false
                        this.loginButton="登录"
                    }else if(this.userPassword.trim() == ''){
                        this.warming="请输入密码"
                        this.password_board_red='board_red';
                        isCommit = false
                        this.isLoginActive=false
                        this.loginButton="登录"
                    }
                    if(isCommit){
                        $.ajax({
                            type:"POST",
                            url:" /login",
                            dataType:"json",
                            timeout : 50000,
                            data: { token: this.token, username: this.userName, password:encrypted,rememberMe:this.rememberMe },
                            success:function(ret){
                                if(ret.msg.msgCode == '200'){
                                    location.href="index"
                                }else{
                                    that.warming=ret.msg.msgInfo
                                    that.isLoginActive=false
                                    that.loginButton="登录"
                                    that.isLoginCodeActive = false;
                                    that.loginCodeButton='点击验证'
                                    that.loginCodeClass='btn-primary'
                                }
                            },
                            complete: function (XMLHttpRequest, textStatus) {
                                if(textStatus == 'timeout') {
                                    that.warming = "连接超时，请稍后再试";
                                    that.isLoginActive = false
                                    that.loginButton = "登录"
                                }
                            },
                            error:function(jqXHR){
                                that.warming="服务器繁忙，请稍后再试"
                                that.isLoginActive=false
                                that.loginButton="登录"

                            }
                        });
                    }

                },
                loginCode:function () {
                    this.isLoginCodeActive = true;
                    var that = this;
                    this.totalTime = this.totalTimes;
                    this.loginCodeButton='正在验证'
                    //发送 post 请求
                    $.ajax({
                        type:"POST",
                        url:" /token",
                        dataType:"json",
                        timeout : 5000,
                        success:function(ret){
                            if(ret.msg.msgCode == '200'){
                                that.loginCodeButton=ret.msg.msgInfo
                                that.loginCodeClass='btn-success'
                                that.publicKey=ret.publicKey;
                                that.token=ret.token;
                            }else{
                                that.msgInfo=ret.msg.msgInfo+","
                                window.intervalObj=setInterval(()=>that.countdown(),1000);
                            }
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                            if(textStatus == 'timeout'){
                                that.loginCodeClass='btn btn-danger'
                                that.isLoginCodeActive = false;
                                that.loginCodeButton=("连接超时，请稍后再试");
                            }
                        },
                        error:function(jqXHR){
                            that.loginCodeClass='btn btn-danger'
                            that.isLoginCodeActive = false;
                            that.loginCodeButton=("服务器繁忙，请稍后再试");
                        }
                    });

                }
            }
        })