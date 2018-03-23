/**
 * Created by 4399-1126 on 2017/8/4.
 */
var flashvars = {};
var audioMgr = {resume: null, pause: null, audioCanPlayCallBack: null};
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
    info.token && (flashvars.token = info.token);
    info.u && (flashvars.u = info.u);
    info.uid && (flashvars.uid = info.uid);
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

function audioCanPlayCallBack(key) {
    audioMgr.audioCanPlayCallBack(key);
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