<img src="https://user-images.githubusercontent.com/94654299/151062447-895f64ed-9379-42d5-92ad-c0a491e6b71f.png" width="290" height="78">

## Table of Contents
- [About eGain Messaging](#about-egain-messaging)
- [eGain Mobile SDK for Android](#egain-mobile-sdk-for-android)
- [Prerequisites/Requirements](#prerequisitesrequirements)
  * [System Requirements](#system-requirements)
  * [Transitive Dependencies](#transitive-dependencies)
  * [SDK Size](#sdk-size)
  * [Permissions / Credentials](#permissions--credentials)
  * [Sample app (Android)](#sample-app-android)
- [Installation](#installation)
    + [Option 1: SDK Gradle Installation](#option-1-sdk-gradle-installation)
    + [Option 2: SDK Manual Installation](#option-2-sdk-manual-installation)
    + [Next Steps: Continue the installation with the Configure SDK step](#nextsteps)

## About eGain Messaging

Messaging is increasingly becoming the most popular way customers are choosing to engage with businesses. With so many messaging platforms and channels, it is important for a business to be able to provide an experience to customers and a consistent experience for their agents. eGain Conversation Hub provides a consistent messaging experience for customers and agents across all messaging channels (synchronous and asynchronous). Its features support popular messaging platforms and custom messaging channels.

The eGain Chat SDK exposes methods which partner-developers can use to build mobile apps to interact with eGain Conversation Hub. With a few modifications, partners can integrate their existing mobile apps with eGain Conversation Hub.

By adding our SDK directly to your app, you get:

- A quick and easy way to converse with customers utilizing our eGain Conversation Hub's capabilities
- Multiple options to fully customize the SDK to the look and feel you want
- An always connected, ongoing conversation that increases customer satisfaction and engagement
- Network bandwidth, local storage, and battery usage optimizations


![default_android_flow](https://user-images.githubusercontent.com/94654299/157335751-ce857401-4ae3-423e-9990-3af816131773.png)


## eGain Mobile SDK for Android
The eGain Android SDK is bundled into an AAR (Android Archive) library. This library can be imported in any Android project to integrate the SDK. The library allows you to use the out-of-the-box UI for your application or you can create your own UI using the provided methods and parameters.

The SDK can be installed manually using the provided Android library. A basic "out of the box" installation takes around 20 minutes. To install the SDK for Android, please refer to the complete installation<inserlink> guide.

## Prerequisites/Requirements
### System Requirements
The SDK requires the following minimum versions:

- [Android API version 26](https://en.wikipedia.org/wiki/Android_Oreo) (Oreo 8.0)
- Java 1.8

The latest available versions are required of:

- [Android Studio](https://developer.android.com/studio), which automatically comes with the Gradle plugin.
- [Gradle](https://developer.android.com/studio/releases/gradle-plugin)
> ⚠️ Only native Android implementations are supported (Java / Kotlin). Please contact us if you would like to use other languages, core frameworks, or development environments.

### Transitive Dependencies
Android SDK depends on the latest versions of several libraries including:

- Okhttp
- Gson
- jsoup
- Picasso

If your application is using any of these libraries, it should be on the same major version the SDK is using.

> **_NOTE:_** Gradle automatically chooses the most recent version of a library when it finds more than one present.

### SDK Size
The size of the SDK varies depending upon the configuration.

- Min size: 312 KB (current AAR size)
- Max size: 0 MB

### Permissions / Credentials
Customers need to acquire credentials to gain access to the SDK. Please contact your eGain representative to receive your Conversation Hub client id and client secret.

### Sample app (Android)
You can find an example project with some basic implementations provided [here](https://github.com/eGain/egain-android-messaging/tree/dev/eG-sample-messaging)
  
## Installation
	
 > **_NOTE:_** Java and Kotlin projects can both be used with the SDK.
  
#### SDK Manual Installation 
Use these procedures to manually copy SDK files into your Android project.

1. Download the latest SDK library from the repository.
2. In the Android Studio project, go to **File → Project Structure → Dependencies**. 
3. Under the list of declared dependencies, click the "+" symbol and select **JAR/AAR Dependency**.
  ![add AAR image](https://user-images.githubusercontent.com/94654299/151071528-658a59bc-7c8a-4697-ab89-a5e26eb74873.png)

4. In the Add JAR/AAR Dependency dialog, do the following and click **OK**: 
    1. For Step 1. Type the path of the .AAR file you downloaded in step 1.
    2. For Step 2. Select the configuration for which you are trying to add the dependency. If you want all configurations to have access to the SDK, choose "implementation" configuration.
 
  ![add AAR 2 image](https://user-images.githubusercontent.com/94654299/151071554-43bc6f26-da74-467f-bbd1-027c7123b945.PNG)

5. Navigate to the application's build.gradle file and do the following:
    1. Verify added the correct dependency you just configured and confirm the path is the same one you entered in the prior step.
    2. Add the following requisite transitive dependencies for the SDK manually as the manual download does not download these dependencies.
  ```Java
  dependencies {
 
    //Transitive Dependencies
    implementation files('path/libs/SDK.aar')
    implementation group: 'com.squareup.okhttp3', name: 'okhttp', version: '4.9.1'
    implementation group: 'com.google.code.gson', name: 'gson', version: '2.8.7'
    implementation 'com.google.android.flexbox:flexbox:3.0.0'
    implementation 'pub.devrel:easypermissions:3.0.0'
    implementation 'org.jsoup:jsoup:1.14.3'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.github.MikeOrtiz:TouchImageView:3.1.1'
    implementation 'androidx.lifecycle:lifecycle-process:2.4.1'
    implementation 'androidx.lifecycle:lifecycle-common:2.4.1'
	
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'com.google.android.material:material:1.6.0-beta01'
 
    //Default
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.1'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
  ```
  > **_NOTE_:** Refer to the [Android Developer Guide](https://developer.android.com/studio/projects/android-library#psd-add-aar-jar-dependency) for adding JAR/AAR dependencies

### Next Steps: Continue the installation with the [Configure SDK step](https://github.com/eGain/egain-android-messaging/tree/dev/Examples)<a name="nextsteps"></a>
