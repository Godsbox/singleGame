/**
 * Created by 4399-1126 on 2017/8/4.
 */
var flashvars = {};
var audioMgr = {resume: null, pause: null, audioCanPlayCallBack: null,audioErrorCallBack:null};
var gameCallback = {updateAccInfo: null};

function getQueryStringByName(name) {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}

function resumeAudio() {
    audioMgr.resume();
}

function pauseAudio() {
    audioMgr.pause();
}

function setLoginInfo(info) {
    if(info.client === 'wap'){
        info.uid && (flashvars.uid = info.uid);
    }else{
        info.token && (flashvars.token = info.token);
        info.u && (flashvars.u = info.u);
        info.uid && (flashvars.uid = info.uid);
    }

    gameCallback.updateAccInfo(false, false);
}

function logoutAccount() {
    gameCallback.updateAccInfo(true, false);
}

function unlockChapterFinished(result) {
    gameCallback.unlockChapterFinished(result, true);
}

function mallPay(result) {
    gameCallback.mallPay(result);
}
/*优惠券回调*/
function preferentialFinished(result) {
    gameCallback.preferentialFinished(result);
}

/**调用存档界面*/
function showSave() {
    gameCallback.showSave();
}

function audioCanPlayCallBack(key) {
    audioMgr.audioCanPlayCallBack(key);
}

function audioErrorCallBack(key) {
    audioMgr.audioErrorCallBack(key);
}
/**调用App接口 是否可以滑动退出  true不允许  false允许*/
function appCanBack(client,app_version,floor,offline) {
    if((client === 'ios' || client === 'android') && '1.8.1' <= app_version){
        //app下 去除滑动退出
        if(client === 'ios'){
            if(offline){
                javascript:setVolumeState(JSON.stringify({state:floor}));
            }else{
                window.webkit && window.webkit.messageHandlers.setVolumeState.postMessage(JSON.stringify({state:floor}));
            }
        }else if(client === 'android'){
            window.app && window.app.setVolumeState(floor ? 'true' : 'false');
        }
    }
}

function localStorage_getItem(key) {return ''}

function localStorage_setItem(key, value) {}

function removeLocalStorage(key) {}

function syMenuBack() {
    gameCallback.syMenuBack();
}

function syMenuExit(urlToGo) {
    gameCallback.syMenuExit(urlToGo);
}

//flashvars.ad = "0";
flashvars.gid = getQueryStringByName('gid');
flashvars.uuid = getQueryStringByName("uuid");
//flashvars.file = getQueryStringByName("file");
flashvars.type = getQueryStringByName("type");
flashvars.name = getQueryStringByName('name');
//flashvars.quality = getQueryStringByName("quality");
flashvars.u = getQueryStringByName('u');
flashvars.v = getQueryStringByName('v');
flashvars.token = getQueryStringByName('token');
flashvars.uid = getQueryStringByName('uid');
flashvars.client = getQueryStringByName('client');
flashvars.offline = getQueryStringByName('offline');
flashvars.app_name = getQueryStringByName('app_name');
flashvars.app_version = getQueryStringByName('app_version');
flashvars.device_name = getQueryStringByName('device_name');
flashvars.deviceOsVer = getQueryStringByName('device_system_version');
flashvars.wap = getQueryStringByName('wap');//来源
//群黑
flashvars.qh_username = getQueryStringByName("username");
flashvars.qh_serverid = getQueryStringByName("serverid");
flashvars.qh_time = getQueryStringByName("time");
flashvars.qh_isadult = getQueryStringByName("isadult");
flashvars.qh_flag = getQueryStringByName("flag");
flashvars.qh_uid = getQueryStringByName("unid");
flashvars.qh_qhchannel = getQueryStringByName("qhchannel");
flashvars.qh_qhchannelid = getQueryStringByName("qhchannelid");
flashvars.qh_qhgid = getQueryStringByName("qhgid");
flashvars.qh_key = getQueryStringByName("key");
flashvars.qh_nname = decodeURIComponent(getQueryStringByName("nname"));

flashvars.vertical = getQueryStringByName('vertical')==="" ? false : (getQueryStringByName('vertical') === "1" ? true : false);//是否竖屏 1竖屏、 0和无参数横屏（默认）
flashvars.extend = decodeURIComponent(getQueryStringByName("extend"));