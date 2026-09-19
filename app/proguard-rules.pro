# Add project specific ProGuard rules here.

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keep class retrofit2.** { *; }
-keepattributes Exceptions

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keep,allowdictionarywarnings class kotlinx.serialization.** { *; }
-keepclassmembers class kotlinx.serialization.** {
    static <fields>;
}
-keep interface kotlinx.serialization.** { *; }

# Keep all data models and API interfaces from being obfuscated
-keep class com.pitchpulse.data.model.** { *; }
-keep class com.pitchpulse.data.remote.** { *; }
-keep class com.pitchpulse.data.local.entity.** { *; }

# OkHttp
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Firebase
-keep class com.google.firebase.messaging.** { *; }

# Coil
-dontwarn coil3.**

# Keep BuildConfig
-keep class com.pitchpulse.BuildConfig { *; }