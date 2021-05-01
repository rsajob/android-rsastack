# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/fantom/Android/Sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep public class * extends java.lang.Exception

-printmapping mapping.txt

# crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-dontwarn com.google.appengine.usersApi.urlfetch.**
-dontwarn rx.**
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep class okio.** { *; }
-keep class javax.annotation.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-keepattributes Signature
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

##############################################
## Retrofit2 (http://square.github.io/retrofit/)
##############################################

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# https://github.com/square/okio#proguard
-dontwarn okio.**

#
##############################################
## Gson
##############################################
#
## For using GSON @Expose annotation
#-keepattributes *Annotation*
#
## Gson specific classes
-dontwarn sun.misc.**
-dontwarn okio.**
-keep class com.google.gson.stream.** { *; }
#
## Application classes that will be serialized/deserialized over Gson
#-keep class com.powermobile.entity.** { *; }
#
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

#############################################
# Gson
#############################################
-keep class **$$PresentersBinder
-keep class **$$State
-keep class **$$ParamsHolder
-keep class **$$ViewStateClassNameProvider
-keepnames class * extends com.arellomobile.mvp.*


##############################################
## Glide https://github.com/bumptech/glide#proguard
##############################################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

##############################################
## okhttp https://github.com/square/okhttp
##############################################
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
## A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-keepnames class com.powermobile.entity.**

# Moxy
-dontwarn com.arellomobile.**

# Disable all Notes
#-dontnote **

-keep class com.arellomobile.mvp.**
-dontwarn com.powermobile.ui.**

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes EnclosingMethod

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.concurrent.GuardedBy
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

-dontwarn kotlin.reflect.jvm.internal.**
-keep class kotlin.** { *; }

####################################################3
# For Toothpick 3.x
-dontwarn javax.inject.**
-dontwarn javax.annotation.**
-keep class javax.inject.**
-keep class javax.annotation.**
-keepnames @javax.inject.Qualifier class *

-keep class **__Factory { *; }
-keep class **__MemberInjector { *; }
-keepclasseswithmembers class *{ @javax.inject.Inject <init>(...); }
-keepclasseswithmembers class *{ @javax.inject.Inject <init>(); }
-keepclasseswithmembers class *{ @javax.inject.Inject <fields>; }
-keepclasseswithmembers class *{ public <init>(...); }
-keepclassmembers class * {
	@javax.inject.Inject <init>(...);
	@javax.inject.Inject <init>();
	@javax.inject.Inject <fields>;
	public <init>(...);
}
-keepnames @toothpick.InjectConstructor class *

-keepclasseswithmembernames class * { toothpick.ktp.delegate.* *; }
-keepclassmembers class * {
    toothpick.ktp.delegate.* *;
}

####################################################3
# For staging
-dontobfuscate