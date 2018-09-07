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

# ---------- 阿里实名认证 -----------
-keep class com.taobao.securityjni.**{*;}
 -keep class com.taobao.wireless.security.**{*;}
 -keep class com.ut.secbody.**{*;}
 -keep class com.taobao.dp.**{*;}
 -keep class com.alibaba.wireless.security.**{*;}
 -keep class com.alibaba.security.rp.**{*;}
 -keep class com.alibaba.sdk.android.**{*;}
 -keep class com.alibaba.security.biometrics.**{*;}
 -keep class android.taobao.windvane.**{*;}

# ----------- Glide 混淆 ---------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# ----------- album 混淆 ------------------
-dontwarn com.yanzhenjie.album.**
-dontwarn com.yanzhenjie.mediascanner.**

# ------------- 腾讯互动直播 混淆 -----------
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**

-keep class tencent.**{*;}
-dontwarn tencent.**

-keep class qalsdk.**{*;}
-dontwarn qalsdk.**

#---------------- ButterKnife ---------------------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
