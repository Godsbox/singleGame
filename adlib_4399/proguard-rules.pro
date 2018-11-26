# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#############################################
#
# 基本指令区域（没什么别的需求不需要动）
#
#############################################
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod

#############################################
#
# Android开发中一些需要保留的公共部分（没什么别的需求不需要动）
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}



# 4399广告联盟

# 不能混淆监听
-keep class com.mob4399.** {
    public protected *;
}

#广点通
-keep class com.qq.e.** {
    public protected *;
}

-keep class android.support.v4.**{
    public *;
}
-keep class android.support.v7.**{
    public *;
}


# ledou

-keep public class android.support.**                                           # 保持哪些类不被混淆
-keep public class * extends android.support.v4.content.FileProvider            # 保持哪些类不被混淆

-dontnote android.net.http.*
-dontnote org.apache.http.**

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-dontwarn com.mobgi.platform.**
-keep public class com.mobgi.MobgiAds {*;}
-keep public class com.mobgi.MobgiAds$FinishState {*;}
-keep public class com.mobgi.MobgiAdsError {*;}
-keep public class com.mobgi.MobgiAdsConfig {*;}
-keep public class com.mobgi.MobgiInterstitialAd {*;}
-keep public class com.mobgi.MobgiVideoAd {*;}
-keep public class com.mobgi.MobgiNativeAd {*;}
-keep public class com.mobgi.MobgiSplashAd {*;}
-keep public class com.mobgi.adutil.parser.NativeAdBeanPro {*;}
-keep public interface com.mobgi.** {*;}
-keep public class com.mobgi.platform.** {*;}

# apponboard
-keep class com.apponboard.** { *; }

# GDT
-keep class com.qq.e.** {
    public protected *;
}
-keep class android.support.v4.app.NotificationCompat**{
    public *;
}

#AdView
-dontwarn
-keep public class com.kyview.** {*;}
-keepclassmembers class * {public *;}
-keep public class com.kuaiyou.**.** {*;}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

# Inmobi
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

#
-keep public class com.kuaiyou.** {*;}

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
#-libraryjars libs/LamVideo_v4.1.jar
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