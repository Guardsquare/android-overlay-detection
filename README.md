<h1 align="center">Protecting Against Android Overlay Attacks</h4>

A set of techniques to protect against overlays on Android.

## 📱What is an overlay attack?

<p>
The Android overlay is a feature used by an app to appear on top of another app. 
Overlays are commonly used to display floating views such as the chat bubbles in Facebook Messenger 
or to display a temporary message or alert. 
They are often used to provide a more convenient user experience by allowing 
users to access certain features or information without leaving the app they are currently using. 
However, the benefits of this feature come with a big risk as Android overlays can, 
unfortunately, be used for malicious purposes.
</p>

<center>
<img src="https://www.guardsquare.com/hs-fs/hubfs/Blog%20assets/Blog%20internal%20images/1_attack-scenarios.png?width=800&height=468&name=1_attack-scenarios.png" alt="Attack Scenarios"/>
</center>

## 🔒 Protecting against overlay attacks

The Android application project in this repo demonstrates some ways in which you can protect against overlay attacks:

<img align="right" src=screenshot.png width="309" />

* In Android 12+ (API 32+): using "setHideOverlayWindows(true)", Android's specific defense against overlay attacks
* In Android 2+ (API 9+): multiple ways to check whether a touch event has been obscured by an overlay.

For further details, refer to the [companion blog post](https://www.guardsquare.com/blog/protecting-against-android-overlay-attacks-guardsquare).

<br />

## ✨ Building the application

The application can be built from the command line or within Android Studio:

<br />

```shell
$ ./gradlew assembleRelease
```

## ❓ How to use the application?

The application demos techniques to protect against filter attacks that
you can apply to your own application. To get the most out of the example application, the code is best read
together with the [companion blog post](https://www.guardsquare.com/blog/protecting-against-android-overlay-attacks-guardsquare).

<br clear=right />

## 🤝 Contributing

Contributions, issues and feature requests are welcome.
Feel free to check the [issues](https://github.com/Guardsquare/android-overlay-detection/issues) page if you would like to contribute.

## 📝 License

Copyright (c) 2002-2023 [Guardsquare NV](https://www.guardsquare.com/).
This project is released under the [Apache 2 license](LICENSE).