//iOS Web APP中点击链接跳转到Safari 浏览器新标签页的问题 devework.com
//stanislav.it/how-to-prevent-ios-standalone-mode-web-apps-from-opening-links-in-safari
if (("standalone" in window.navigator) && window.navigator.standalone) {
    var noddy, remotes = false;
    document.addEventListener('click', function (event) {
        noddy = event.target;
        while (noddy.nodeName !== "A" && noddy.nodeName !== "HTML") {
            noddy = noddy.parentNode;
        }
        if ('href' in noddy && noddy.href.indexOf('http') !== -1 && (noddy.href.indexOf(document.location.host) !== -1 || remotes)) {
            event.preventDefault();
            document.location.href = noddy.href;
        }
    }, false);
}

function strToMD5(str) {
    var md5Code = hex_md5(str);
    return md5Code;
}

function strToBase64(str) {
    var base64 = window.btoa(str);
    return base64;
}

function Base64ToStr(str) {
    str = window.atob(str);
    return str;
}

function getUrlParms(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function validationEmail(username) {
    var reg = new RegExp("^[a-zA-Z0-9]+([._\\-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z0-9]+$");
    if (reg.test(username)) {
        return true;
    } else {
        return false;
    }
}

function GetUrlRelativePath() {
    var url = document.location.toString();
    var arrUrl = url.split("//");

    var start = arrUrl[1].indexOf("/");
    var relUrl = arrUrl[1].substring(start);

    if (relUrl.indexOf("?") != -1) {
        relUrl = relUrl.split("?")[0];
    }
    return relUrl;
}

function isPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
        "SymbianOS", "Windows Phone",
        "iPad", "iPod", "MicroMessenger"];
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
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}

function guid() {
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}

function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}

function guid() {
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}

function isEmpty(str) {
    if (str == null || str.trim().length == 0) {
        return true;
    }
    return false;
}

function userPageAlert(strogerText, Text) {
$("#myAlert2").remove();
    $('nav').after("<div id='myAlert2' class='alert alert-danger fade-in-animation opacity95' style='position: fixed;left:0;right:0;top:auto;z-index:10000;'><a href='#' class='close' data-dismiss='alert'>&times;</a><strong>" + strogerText + "</strong>" + Text + "</div>");
}

function userPageSuccessAlert(strogerText, Text) {
$("#myAlert2").remove();
    $('nav').after("<div id='myAlert2' class='alert alert-success fade-in-animation opacity95' style='position: fixed;left:0;right:0;top:auto;z-index:10000;'><a href='#' class='close' data-dismiss='alert'>&times;</a><strong>" + strogerText + "</strong>" + Text + "</div>");
}

function getDateDiff(dateTimeStamp) {
    var minute = 1000 * 60;
    var hour = minute * 60;
    var day = hour * 24;
    var halfamonth = day * 15;
    var month = day * 30;
    var now = new Date().getTime();
    var datetime = dateTimeStamp;
    dateTimeStamp = getDateTimeStamp(dateTimeStamp);
    var diffValue = now - dateTimeStamp;
    if (diffValue < 0) {
        //若日期不符则弹出窗口告之
        //alert("结束日期不能小于开始日期！");
    }
    var monthC = diffValue / month;
    var weekC = diffValue / (7 * day);
    var dayC = diffValue / day;
    var hourC = diffValue / hour;
    var minC = diffValue / minute;
    if (monthC >= 1) {
        // result="" + parseInt(monthC) + "个月前";
        result = datetime;
    }
    else if (weekC >= 1) {
        // result="" + parseInt(weekC) + "周前";
        result = datetime;
    }
    else if (dayC >= 1) {
        result = "" + parseInt(dayC) + "天前";
    }
    else if (hourC >= 1) {
        result = "" + parseInt(hourC) + "小时前";
    }
    else if (minC >= 1) {
        result = "" + parseInt(minC) + "分钟前";
    } else
        result = "刚刚";
    return result;
}

//js函数代码：字符串转换为时间戳
function getDateTimeStamp(dateStr) {
    return Date.parse(dateStr.replace(/-/gi, "/"));
}

function jsonMsgValidation(data) {
    if (data.hasOwnProperty("msg")) {
        if (data.msg.msgCode == '301') {
            $("#refreshing").addClass("hidden");
            $("#loadComplete").removeClass("hidden");
            return false;
        }
        userPageAlert('', data.msg.msgInfo)
        return false;
    }
    return true;
}

function forumTimeout() {
    $("#refreshing").addClass("hidden");
    $("#loadComplete").removeClass("hidden");
}

function forumError() {
    $("#refreshing").addClass("hidden");
    $("#loadComplete").removeClass("hidden");
    userPageAlert('', '服务器繁忙，请稍后再试')
}

function media(data) {
    var s = "";
    if (data.type == '1') {
        s += "<ul class='figure-list boy' data-grid='images'>";
        if (!isEmpty(data.img0)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img0 + ")' src='" + data.img0 + "' url='" + data.img0 + "'/></li>";
        if (!isEmpty(data.img1)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img1 + ")' src='" + data.img1 + "' url='" + data.img1 + "'/></li>";
        if (!isEmpty(data.img2)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img2 + ")' src='" + data.img2 + "' url='" + data.img2 + "'/></li>";
        if (!isEmpty(data.img3)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img3 + ")' src='" + data.img3 + "' url='" + data.img3 + "'/></li>";
        if (!isEmpty(data.img4)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img4 + ")' src='" + data.img4 + "' url='" + data.img4 + "'/></li>";
        if (!isEmpty(data.img5)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img5 + ")' src='" + data.img5 + "' url='" + data.img5 + "'/></li>";
        if (!isEmpty(data.img6)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img6 + ")' src='" + data.img6 + "' url='" + data.img6 + "'/></li>";
        if (!isEmpty(data.img7)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img7 + ")' src='" + data.img7 + "' url='" + data.img7 + "'/></li>";
        if (!isEmpty(data.img8)) s += "<li><img data-action='zoom'  style='background-image:url(" + data.img8 + ")' src='" + data.img8 + "' url='" + data.img8 + "'/></li>";
        s += "</ul>";
    } else if (data.type == '2') {
        videoid = S4();
        s += " <ul class='figure-list boy' data-grid='' ><li><video controls>";
        s += "<source src='" + data.video + "'  id='" + videoid + "'></source>";
        s += "您的浏览器不支持HTML5视频</video></li></ul>";
    }
    return s;
}

function forumMain(data) {
    var s = "";
    var img = isEmpty(data.userImg) == true ? 'images/no_user_image.png' : data.userImg;
    s += "<li class='rv b agz fade-in-animation' id='post"+data.postid+"'><img class='bos vb yb aff' width='62px' height='62px'  src='" + img + "' data-sid='" + data.sid + "'  data-oper='1' data-toggle=\"modal\" data-target=\"#friend_modal\"><div class='rw'><div class='bpb'><small class='acx axc'><i class='fa fa-times width-auto height-auto fa-1x hidden' name='deletePost' onclick=\"deletePost('"+data.postid+"')\"></i><i class='fa fa-share-alt width-auto height-auto fa-1x' onclick=\"share('"+data.postid+"')\"></i>";
    if (isPC() && !isEmpty(data.lastupdatetime)) {
        s += getDateDiff(data.lastupdatetime.substring(0, data.lastupdatetime.lastIndexOf('.'))) + "</small><h6><a href=\"javascript:void(0);\" onclick=\"jump('/single_post?postid=" + data.postid + "&fid=" + $("#fid").val() + "')\">" + data.title + "</a></h6></div><p>" + data.text + "</p>";
    } else if(!isEmpty(data.lastupdatetime)){
        s += getDateDiff(data.lastupdatetime.substring(0, data.lastupdatetime.lastIndexOf('.'))) + "</small><h6><a href=\"single_post?postid=" + data.postid + "&fid=" + $("#fid").val() + "\">" + data.title + "</a></h6></div><p>" + data.text + "</p>";
    }
    s += media(data);
    if (data.hasOwnProperty("userPostReplyVOEntity")) {
        s += reply(data.userPostReplyVOEntity);
    }
    s += "</div></li>";
    return s;
}

function postMain(data) {
    var s = "";
    var img = isEmpty(data.userImg) == true ? 'images/no_user_image.png' : data.userImg;
    s += "<li class='rv b agz fade-in-animation'><img class='bos vb yb aff' width='62px' height='62px'  src='" + img + "' data-sid='" + data.creator + "'  data-oper='1' data-toggle=\"modal\" data-target=\"#friend_modal\"><div class='rw'><div class='bpb'><small class='acx axc'><i class='fa fa-share-alt width-auto height-auto fa-1x' onclick=\"share('"+data.postid+"')\"></i>";
    s += getDateDiff(data.lastupdatetime.substring(0, data.lastupdatetime.lastIndexOf('.'))) + "</small><h6>" + data.nickname + "</h6></div><p>" + data.text + "</p>";
    s += media(data);
    if (data.hasOwnProperty("userPostReplyVOEntity")) {
        s += reply(data.userPostReplyVOEntity);
    }
    s += "</div></li>";
    // $("#title").html("<i class='fa fa-chevron-left width-auto color-blue fa-1x' onclick=\"jumpForum()\"></i>"+data.title);
    $("#title").text(data.title);
    return s;
}

function postReply(data) {
    var s = "";
    var img = isEmpty(true && data.userImg) == true ? 'images/no_user_image.png' : data.userImg;
    s += "<li class='rv b agz fade-in-animation'><img class='bos vb yb aff' width='62px' height='62px' src='" + img + "' data-sid='" + data.creator + "' data-oper='1' data-toggle=\"modal\" data-target=\"#friend_modal\"><div class='rw'><div class='bpb'><small class='acx axc'><small onclick=replyUser('"+data.nickname+"')>回复</small>&nbsp;";
    if (data.hasOwnProperty("replyFavouriteEntity") && data.replyFavouriteEntity.favourite == '1') {
        s += "<i class='fa fa-arrow-up width-auto hegiht-auto color-blue' id='up" + data.replyid + "' onclick=\"favourite('up','" + data.replyid + "')\"></i>";
    } else {
        s += "<i class='fa fa-arrow-up width-auto hegiht-auto' id='up" + data.replyid + "' onclick=\"favourite('up','" + data.replyid + "')\"></i>";
    }
    s += "<i id='favouriteCount" + data.replyid + "'>" + data.favourite + "</i>";
    if (data.hasOwnProperty("replyFavouriteEntity") && data.replyFavouriteEntity.favourite == '-1') {
        s += "<i class='fa fa-arrow-down width-1px hegiht-auto color-blue' id='down" + data.replyid + "' onclick=\"favourite('down','" + data.replyid + "')\"></i>";
    } else {
        s += "<i class='fa fa-arrow-down width-1px hegiht-auto' id='down" + data.replyid + "' onclick=\"favourite('down','" + data.replyid + "')\"></i>";
    }
    s += "&nbsp;&nbsp;" + getDateDiff(data.lastupdatetime.substring(0, data.lastupdatetime.lastIndexOf('.'))) + "</small><h6>" + data.nickname + "</h6></div><p>" + data.text + "</p>";
    s += media(data);
    if (data.hasOwnProperty("userPostReplyVOEntity")) {
        s += reply(data.userPostReplyVOEntity);
    }
    s += "</div></li>";
    return s;
}

function reply(data) {
    var s = "<ul class='bow afa'><li class='rv afh'> <div class='rw well'><p><strong style='color:red;'>最佳 </strong><span>";
    if (data.hasOwnProperty("replyFavouriteEntity") && data.replyFavouriteEntity.favourite == '1') {
        s += "<i class='fa fa-arrow-up width-auto hegiht-auto color-blue' id='up" + data.replyid + "' onclick=\"favourite('up','" + data.replyid + "')\"></i>";
    } else {
        s += "<i class='fa fa-arrow-up width-auto hegiht-auto' id='up" + data.replyid + "' onclick=\"favourite('up','" + data.replyid + "')\"></i>";
    }
    s += "<i id='favouriteCount" + data.replyid + "'>" + data.favourite + "</i>";
    if (data.hasOwnProperty("replyFavouriteEntity") && data.replyFavouriteEntity.favourite == '-1') {
        s += "<i class='fa fa-arrow-down width-1px hegiht-auto color-blue' id='down" + data.replyid + "' onclick=\"favourite('down','" + data.replyid + "')\"></i>";
    } else {
        s += "<i class='fa fa-arrow-down width-1px hegiht-auto' id='down" + data.replyid + "' onclick=\"favourite('down','" + data.replyid + "')\"></i>";
    }
    s += "</span></p>" + data.text;
    s += media(data);
    s += "</div></li></ul>";
    return s;
}

function shock() {
    if (navigator.vibrate) {
        navigator.vibrate(300);//震动秒数
    } else if (navigator.webkitVibrate) {
        navigator.webkitVibrate(300);
    }
}

function favourite(oper, replyid) {

    shock();

    if ($("#authority").length <= 0) {
        userPageAlert('', '请先登录')
        return;
    }

    var hasColor = $("#" + oper + replyid).hasClass('color-blue');
    var hasColorUp = $("#up" + replyid).hasClass('color-blue');
    var hasColorDown = $("#down" + replyid).hasClass('color-blue');
    var operation = "";

    if (hasColor == true) {
        operation = "0";
        $.ajax({
            type: "POST",
            url: "/user-forum/favourite",
            dataType: "json",
            timeout: 50000,
            data: {replyId: replyid, operate: operation},
            success: function (data) {
                if (data.msg.msgCode == '200') {
                    var opposite;
                    if (oper == 'up') {
                        $("#favouriteCount" + replyid).text(parseInt($("#favouriteCount" + replyid).text()) - 1)
                        opposite = 'down';
                    }
                    if (oper == 'down') {
                        $("#favouriteCount" + replyid).text(parseInt($("#favouriteCount" + replyid).text()) + 1)
                        opposite = 'up';
                    }
                    $("#" + oper + replyid).removeClass('color-blue');
                    $("#" + opposite + replyid).removeClass('color-blue');
                } else {
                    userPageAlert('', data.msg.msgInfo)
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                if (textStatus == 'timeout') {
                    userPageAlert('', '连接超时，请稍后再试')
                    isSingleForumloading = true;
                }
            },
            error: function (jqXHR) {
                userPageAlert('', '服务器繁忙，请稍后再试')
                isSingleForumloading = true;
            }
        });
        return;
    }

    if (hasColorUp || hasColorDown) {
        if (hasColorUp)
            userPageAlert('', '请先取消之前的赞')
        if (hasColorDown)
            userPageAlert('', '请先取消之前的踩')
        return;
    }

    if (hasColor == false) {
        if (oper == 'up') {
            operation = "1";
        }
        if (oper == 'down') {
            operation = "-1";
        }
        $.ajax({
            type: "POST",
            url: "/user-forum/favourite",
            dataType: "json",
            timeout: 50000,
            data: {replyId: replyid, operate: operation},
            success: function (data) {

                if (data.msg.msgCode == '200') {
                    var opposite;
                    if (oper == 'up') {
                        $("#favouriteCount" + replyid).text(parseInt($("#favouriteCount" + replyid).text()) + 1)
                        opposite = 'down';
                    }
                    if (oper == 'down') {
                        $("#favouriteCount" + replyid).text(parseInt($("#favouriteCount" + replyid).text()) - 1)
                        opposite = 'up';
                    }
                    $("#" + oper + replyid).addClass('color-blue');
                    $("#" + opposite + replyid).removeClass('color-blue');
                } else {
                    userPageAlert('', data.msg.msgInfo)
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                if (textStatus == 'timeout') {
                    userPageAlert('', '连接超时，请稍后再试')
                    isSingleForumloading = true;
                }
            },
            error: function (jqXHR) {
                userPageAlert('', '服务器繁忙，请稍后再试')
                isSingleForumloading = true;
            }
        });
    }
}

function closeHidePanel() {
    $("#hidePanel").addClass('hidden');
}

function getSingleForum() {
    var fid = $("#fid").val();
    if (isSingleForumloading) {
        $("#refreshing").removeClass("hidden");
        $("#loadComplete").addClass("hidden");
        isSingleForumloading = false;
        $.ajax({
            type: "POST",
            url: "/user-forum/single-forum-postlist",
            dataType: "json",
            timeout: 50000,
            data: {fid: fid, page: page},
            success: function (data) {
                if (jsonMsgValidation(data)) {
                    for (var o in data) {
                        var videoid = '';
                        var s = "";
                        $("#title").text(data[o].fname);
                        if(!isEmpty(data[o].sid)){
                            s += forumMain(data[o]);
                            $("#single_forum_list").before(s);
                        }

                    }
                    page = page + 1;
                    $("#refreshing").addClass("hidden");
                    $("#loadComplete").addClass("hidden");
                    isSingleForumloading = true;
                } else {
                    isSingleForumloading = false;
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                forumTimeout();
                if (textStatus == 'timeout') {
                    userPageAlert('', '连接超时，请稍后再试')
                    isSingleForumloading = false;
                }
            },
            error: function (jqXHR) {
                forumError();
                isSingleForumloading = false;
            }
        });
    }
}

function singleForumFresh() {
    isSingleForumloading = true;
    $("#refreshing").removeClass("hidden");
    $("#loadComplete").addClass("hidden");
    getSingleForum();
}

function followForum(fid, oper,type) {
    $.ajax({
        type: "POST",
        url: "/user/follow-forum",
        dataType: "json",
        timeout: 50000,
        data: {fid: fid, oper: oper},
        success: function (data) {
            if (data.msg.msgCode == '200') {
                if(type == 'forum'){
                    $('#followForum').text('已关注');
                    $('#followForum').attr('onclick', '');
                    $('#followForum').fadeOut(3500, null);
                }else if(type.indexOf('ftd') != -1){
                    $('#'+type).text('已删除');
                    $('#TD'+type).fadeOut(3000, null);
                }

            } else {
                userPageAlert('', '关注失败，请稍后再试')
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
            if (textStatus == 'timeout') {
                userPageAlert('', '连接超时，请稍后再试')
            }
        },
        error: function (jqXHR) {
            userPageAlert('', '服务器繁忙，请稍后再试')
        }
    });
}

function isFollowForum() {
    if ($("#followForum").length > 0) {
        var fid = $("#fid").val()
        if (isEmpty(fid)) {
            fid = getUrlParms('fid');
        }
        $.post("/user-forum/isfollowforum", {fid: fid}, function (data) {
            if (data.msg.msgCode == '200') {
                $('#followForum').remove();
            }
        });
    }
}

function isInIframe() {
    if (window.frames.length != parent.frames.length) {
        return true;
    }
    return false;
}

function isAuthority() {
    if ($("#authority").length > 0) {
        return true;
    } else {
        return false;
    }
}

function followFriend(sid, oper, type) {

    $.ajax({
        type: "POST",
        url: "/user/follow-friend",
        dataType: "json",
        timeout: 50000,
        data: {friendSid: sid, oper: oper},
        success: function (data) {
            if (data.msg.msgCode == '200') {
                if (type == 'button' && $("#follow_friend").length > 0) {
                    var button = $("#follow_friend");
                    $("#follow_friend_ref").removeClass("fa-circle-o");
                    $("#follow_friend_ref").addClass("fa-check");
                    button.attr("disabled", "disabled");
                    button.removeClass("btn-primary");
                    button.addClass("btn-success");
                }else if(type.indexOf('ftd') != -1){
                    $('#'+type).text('已删除');
                    $('#TD'+type).fadeOut(3000, null);
                }
            } else {
                userPageAlert('', data.msg.msgInfo);
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
            if(type.indexOf('ftd') != -1){
                $('#'+type).prop('disabled','true');
            }
            if (textStatus == 'timeout') {
                userPageAlert('', '连接超时，请稍后再试')
            }
        },
        error: function (jqXHR) {
            userPageAlert('', '服务器繁忙，请稍后再试')
        }
    });
}

function removeDeletePostClass(){
$("i[name='deletePost']").removeClass('hidden');
}

function deletePost(postid){
 $.ajax({
        type: "POST",
        url: "/user/delete-post",
        dataType: "json",
        timeout: 50000,
        data: {postId:postid},
        success: function (data) {
            if (data.msg.msgCode == '200') {
                $("#post"+postid).fadeOut(1500, null);
            } else {
                userPageAlert('', data.msg.msgInfo);
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
            if (textStatus == 'timeout') {
                userPageAlert('', '连接超时，请稍后再试')
            }
        },
        error: function (jqXHR) {
            userPageAlert('', '服务器繁忙，请稍后再试')
        }
    });
}

function replyUser(nickname){
if(isAuthority()){
$("#deblock_udid").val("@"+nickname+":");
$("#reply_model").modal('show');
}else{
userPageAlert('', '登录后才能发帖')
}

}

function share(postid){
var domain = window.location.href;
domain = domain.substring(domain.indexOf("//")+2);
    domain = domain.substring(0,domain.indexOf('/'));
var url = 'http://'+domain + "/single_post?postid="+postid
var s = "<div class='modal show' tabindex='-1' role='dialog' id='messageShare'><div class='modal-dialog' role='document'><div class='modal-content'><div class='modal-header'><h5 class='modal-title'>分享</h5>";
s += "<button type='button' class='close' data-dismiss='modal' aria-label='Close'  onclick=\"javascript:$('#messageShare').remove();\"><span aria-hidden='true'>&times;</span></button></div><div class='modal-body'><p>";
s += "复制此链接分享该贴：<br>" + url + "</p></div><div class='modal-footer'><button type='button' class='btn btn-secondary' data-dismiss='modal' onclick=\"javascript:$('#messageShare').remove();\">关闭</button></div></div></div></div>"
$("nav").append(s)
}