# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Programs\Android/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable, *Annotation*
-keepattributes Signature
-dontshrink

# 闪艺

-keep class com.lzj.shanyi.R$*
-keep public class * extends com.lzj.arch.core.Contract$Presenter

# Android SDK {
-keep class android.content.** { *; }
-keep class android.os.** { *; }
-keep class android.support.v4.** { *; }
-keep class !android.support.v7.view.menu.*MenuBuilder*, android.support.v7.** { *; }
-keep class android.support.design.** { *; }
-keep class android.support.annotation.** { *; }
-keep class org.android.** { *; }

-keep interface android.support.v4.** { *; }
-keep interface android.support.v7.** { *; }

-keep public class * extends android.view.View
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# Preserve all View implementations, their special context constructors, and
# their setters.
-keep public class * extends android.view.View
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve all classes that have special context constructors, and the
# constructors themselves.

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# Preserve all classes that have special context constructors, and the
# constructors themselves.

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve the special fields of all Parcelable implementations.

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.

-keepclassmembers class **.R$* {
  public static <fields>;
}

# Android SDK }

# 不混淆暴露出去的 JS 方法
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# EventBus
-keep class de.greenrobot.event.** { *; }
-keepclassmembers class ** {
    public void onEvent*(***);
}

# LeakCanary

-keep class com.squareup.leakcanary.** { *; }

# OkHttp

-dontwarn okio.**
-keep class okio.** { *; }

# RxJava/RxAndroid
-keep class io.reactivex.** { *; }
-dontwarn rx.internal.util.unsafe.**

# Glide
-keep class com.bumptech.glide.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule

# CircleImageView
-keep class de.hdodenhof.circleimageview.** { *; }

# PhotoView
-keep class uk.co.senab.photoview.** { *; }

# SwitchButton
-keep class com.kyleduo.switchbutton.** { *; }

# Timber
-keep class timber.** { *; }

# Gson
-keep class com.google.gson.** { *; }

# ImageCropView
-keep class it.sephiroth.android.library.easing.** { *; }
-keep class com.naver.android.helloyako.imagecrop.** { *; }

# ViewPagerIndicator
-keep class com.viewpagerindicator.** { *; }
-dontwarn com.viewpagerindicator.**

# Doorbell
-keep class com.wujilin.doorbell.** { *; }

# SwipeBackLayout
-keep class me.imid.swipebacklayout.lib.** { *; }

# 友盟社会化 SDK {

-dontusemixedcaseclassnames
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
 *;
}
-keep class com.tencent.mm.opensdk.** {
*;
}
-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.com.umeng.soexample.R$*{
public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
public static final int *;
    }
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}
-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keepattributes Signature

# 友盟社会化 SDK }

#意见反馈
-keep class com.alibaba.sdk.android.feedback.impl.FeedbackServiceImpl {*;}
-keep class com.alibaba.sdk.android.feedback.impl.FeedbackAPI {*;}
-keep class com.alibaba.sdk.android.feedback.util.IWxCallback {*;}
-keep class com.alibaba.sdk.android.feedback.util.IUnreadCountCallback{*;}
-keep class com.alibaba.sdk.android.feedback.FeedbackService{*;}
-keep public class com.alibaba.mtl.log.model.LogField {public *;}
-keep class com.taobao.securityjni.**{*;}
-keep class com.taobao.wireless.security.**{*;}
-keep class com.ut.secbody.**{*;}
-keep class com.taobao.dp.**{*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep class com.ta.utdid2.device.**{*;}

#友盟推送
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.ut.mini.**
-dontwarn com.squareup.wire.**

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class org.json.** { *; }

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}
-keep class okio.** {*;}
-keep class com.squareup.wire.** {*;}
-keep class org.android.spdy.**{*;}

-keep public class **.R$*{
   public static final int *;
}

-keep public class com.lzj.shanyi.R$*{
   public static final int *;
}

-dontwarn org.apache.http.**
-dontwarn android.webkit.**
-keep class org.apache.http.** { *; }
-keep class org.apache.commons.codec.** { *; }
-keep class org.apache.commons.logging.** { *; }
-keep class android.net.compatibility.** { *; }
-keep class android.net.http.** { *; }

-keep class org.android.agoo.impl.*{
        public <fields>;
        public <methods>;
}
-keep,allowshrinking class org.android.agoo.service.* {
    public <fields>;
    public <methods>;
}
-keep,allowshrinking class com.umeng.message.* {
    public <fields>;
    public <methods>;
}
-keep class com.umeng.message.protobuffer.* {
        public <fields>;
        public <methods>;
}
-keep class com.squareup.wire.* {
        public <fields>;
        public <methods>;
}
-keep class com.umeng.message.local.* {
        public <fields>;
        public <methods>;
}

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}

 -keepattributes EnclosingMethod
 -dontwarn InnerClasses

 #广告的混淆
 -keep class com.apponboard.** { *; }

 -keep class com.qq.e.** {
     public protected *;
 }
 -keep class android.support.v4.app.NotificationCompat**{
     public *;
 }

 -dontwarn
 -keep public class com.kyview.** {*;}
 -keepclassmembers class * {public *;}
 -keep public class com.kuaiyou.**.** {*;}
 -optimizationpasses 5
 -dontusemixedcaseclassnames
 -dontskipnonpubliclibraryclasses
 -dontpreverify
 -verbose

 -keepattributes SourceFile,LineNumberTable
 -keep class com.inmobi.** { *; }
 -dontwarn com.inmobi.**
 -keep public class com.google.android.gms.**
 -dontwarn com.google.android.gms.**
 -dontwarn com.squareup.picasso.**
 -keep class com.google.android.gms.ads.identifier.AdvertisingIdClient{
 public *;
 }
 -keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info{
 public *;
 }
 # skip the Picasso library classes
 -keep class com.squareup.picasso.** {*;}
 -dontwarn com.squareup.picasso.**
 -dontwarn com.squareup.okhttp.**
 # skip Moat classes
 -keep class com.moat.** {*;}
 -dontwarn com.moat.**
 # skip AVID classes
 -keep class com.integralads.avid.library.* {*;}

 #Centrixlink
 -dontwarn com.centrixlink.**
 -keep public class com.centrixlink.**  { *; }

 #Mobvista
 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class com.mintegral.** {*; }
 -keep interface com.mintegral.** {*; }
 -keep class android.support.v4.** { *; }
 -dontwarn com.mintegral.**
 -keep class **.R$* { public static final int mintegral*; }
 -keep class com.alphab.** {*; }
 -keep interface com.alphab.** {*; }

 #Kingsoft
 -keep class com.ksc.ad.sdk.**{ *;}
 -dontwarn com.ksc.ad.sdk.**

 -keep class com.bytedance.sdk.openadsdk.** { *; }
 -keep class com.androidquery.callback.** {*;}

 #Mobgi
 -keep public class com.mobgi.MobgiAds {*;}
 -keep public class com.mobgi.MobgiAds$FinishState {*;}
 -keep public class com.mobgi.MobgiAdsError {*;}
 -keep public class com.mobgi.MobgiAdsConfig {*;}
 -keep public class com.mobgi.MobgiInterstitialAd {*;}
 -keep public class com.mobgi.MobgiVideoAd {*;}
 -keep public class com.mobgi.MobgiNativeAd {*;}
 -keep public class com.mobgi.MobgiSplashAd {*;}
 -keep public interface com.mobgi.IMobgiAdsListener {*;}
 -keep public interface com.mobgi.IMobgiAdsLenovoListener {*;}
 -keep public interface com.mobgi.listener.SplashAdListener {*;}
 -keep public class com.mobgi.adutil.parser.NativeAdBeanPro {*;}

 #AdMod国内版
 -keep class * implements com.google.android.gms.ads.mediation.MediationAdapter {
   public *;
 }
 -keep class * implements com.google.ads.mediation.MediationAdapter {
   public *;
 }
 -keep class * implements com.google.android.gms.ads.mediation.customevent.CustomEvent {
   public *;
 }
 -keep class * implements com.google.ads.mediation.customevent.CustomEvent {
   public *;
 }
 -keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
     public static final *** NULL;
 }

 -keep class com.google.android.gms.common.internal.ReflectedParcelable
 -keepnames class * implements com.google.android.gms.common.internal.ReflectedParcelable
 -keepclassmembers class * implements android.os.Parcelable {
   public static final *** CREATOR;
 }
 -keep @interface android.support.annotation.Keep
 -keep @android.support.annotation.Keep class *
 -keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
 }
 -keepclasseswithmembers class * {
   @android.support.annotation.Keep <methods>;
 }
 -keep @interface com.google.android.gms.common.annotation.KeepName
 -keepnames @com.google.android.gms.common.annotation.KeepName class *
 -keepclassmembernames class * {
   @com.google.android.gms.common.annotation.KeepName *;
 }
 -keep @interface com.google.android.gms.common.util.DynamiteApi
 -keep @com.google.android.gms.common.util.DynamiteApi public class * {
   public <fields>;
   public <methods>;
 }
 -dontwarn android.security.NetworkSecurityPolicy
 -dontwarn android.app.Notification
 -dontwarn sun.misc.Unsafe
 -dontwarn libcore.io.Memory
 # AdMob国内版 -end

 #蓝莓
 #视频

 -dontwarn com.lam.**
 -keep class com.lam.** { *; }
 #插屏
 -keepattributes Exceptions,InnerClasses,Signature,*Annotation*
 -keepnames class * implements java.io.Serializable
 -keep public class com.androidquery.**{*;}
 -keep public class com.tencent.analytics.sdk.** {*;}

 #OneWay
 ############################################
 ##           OneWaySDK 混淆配置             ##
 ############################################

 -keepattributes *Annotation*
 -keep enum mobi.oneway.sdk.* {*;}
 -keep class mobi.oneway.sdk.** {*;}


 ############################################
 ##           OkDownload 混淆配置            ##
 ############################################


-dontwarn com.liulishuo.okdownload.**
-keep class com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnSQLite {
    public com.liulishuo.okdownload.core.breakpoint.DownloadStore createRemitSelf();
    public com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnSQLite(android.content.Context);
}
# okdownload:okhttp
-keepnames class com.liulishuo.okdownload.core.connection.DownloadOk


############################################
##             OkHttp 混淆配置              ##
############################################

# okhttp https://github.com/square/okhttp/#proguard
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

#穿山甲(今日头条)
###Toutiao v1.9.3.2
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.androidquery.callback.** {*;}
-keep class com.bytedance.sdk.openadsdk.service.TTDownloadProvider

#Unity
###Unity v2.1.0
# Keep filenames and line numbers for stack traces
-keepattributes SourceFile,LineNumberTable
# Keep JavascriptInterface for WebView bridge
-keepattributes JavascriptInterface
# Sometimes keepattributes is not enough to keep annotations
-keep class android.webkit.JavascriptInterface {
*;
}
# Keep all classes in Unity Ads package
-keep class com.unity3d.ads.** {
*;
}

#Vungle
###Vungle v6.3.18
# Vungle
-keep class com.vungle.warren.** { *; }
-dontwarn com.vungle.warren.error.VungleError$ErrorCode
# Moat SDK
-keep class com.moat.** { *; }
-dontwarn com.moat.**
# Okio
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Retrofit
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Google Android Advertising ID
-keep class com.google.android.gms.internal.** { *; }
-dontwarn com.google.android.gms.ads.identifier.**

#Uniplay
-dontwarn com.uniplay.**
-keep class com.uniplay.** { *; }

#联想
-keep class com.lestore.ad.sdk.**{
<fields>;
<methods>;
}
-keep class com.chance.**{
<fields>;
<methods>;
}
-keep class com.lenovo.**{
<fields>;
<methods>;
}
-keep class de.greenrobot.** {
<fields>;
<methods>;
}
-keep class com.qq.**{
<fields>;
<methods>;
}
-dontwarn com.qq.**
-dontwarn com.lenovo.**
-dontwarn com.lestore.ad.sdk.**
-dontwarn com.chance.**

#Mimo
-keep class com.xiaomi.ad.**{*;}
-keep class com.miui.zeus.**{*;}

#OPPO
#oppo sdk
-keep class com.oppo.** {
public protected *;
}
-keep class okio.**{ *; }
-keep class com.squareup.wire.**{ *; }
-keep public class * extends com.squareup.wire.**{ *; }
# Keep methods with Wire annotations (e.g. @ProtoField)
-keepclassmembers class ** {
@com.squareup.wire.ProtoField public *;
@com.squareup.wire.ProtoEnum public *;
}
-keep public class com.cdo.oaps.base.**{ *; }
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
#support-v4
-keep class android.support.v4.** { *; }

#GDT
-keep class com.qq.e.**{
public protected *;
}
-keep class android.support.v4.app.NotificationCompat**{
public *;
}


#Baidu
-keep class com.baidu.mobads.*.**{ *;}

#vivoSDK
-keep class com.vivo.*.**{ *; }


#九游
###九游
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes *Annotation*

## common
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service

-keep class android.app.**{*;}
-dontwarn  android.app.**

-keep class android.support.v7.media.*{public *;}
-keep class android.support.v4.** { *; }
-dontwarn android.support.**

## network libs
-keep class android.net.http.** { *; }
-dontwarn android.net.**
-dontnote android.net.http.*

-keep class org.apache.http.** { *; }
-dontwarn org.apache.**
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

# Keep native methods
-keepclasseswithmembers class * {
    native <methods>;
}

### utdid
-keep class com.ta.utdid2.**{*;}

-keep class com.ut.device.**{*;}
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**

# Keep ngad-sdk classes
-keep class cn.sirius.nga.** {*; }
-dontwarn cn.sirius.nga.**

-keep class cn.ninegame.library.** {*; }
-dontwarn cn.ninegame.library.**

-keep class com.qq.e.** {*; }
-dontwarn com.qq.e.**

-keep class com.taobao.** {*; }
-dontwarn com.taobao.**
-keep class android.taobao.** {*; }
-dontwarn android.taobao.**

-keep class com.UCMobile.Apollo.**{*;}

-dontwarn com.mobvista.**
-keep class com.mobvista.** {*; }
-keep interface com.mobvista.** {*; }
-keep class **.R$* { public static final int mobvista*; }
-keep class com.alphab.** {*; }
-keep interface com.alphab.** {*; }

-dontwarn com.lm.**
-keep class com.lm.** { *; }

-dontwarn com.uniplay.**
-keep class com.uniplay.** { *; }
-ignorewarnings