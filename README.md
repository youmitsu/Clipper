[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)
![versionBadge](https://img.shields.io/badge/version-0.3.2-green.svg)

# Clipper


A library of Android development to be used for clipping view by specific shape(eg. circle, rectangle, custom shape).


## Abstract


This library makes you help creating Tutorial
It's useful for implementing Tutorial view.
When a tutorial view is showed, you may want to draw overlapping view and clip the object which you want to explain.
In that cases, This library helps you implement it.


## Requirements


- Android API Level: 21 to use

## Getting Started


- Add this into your `build.gradle` file of project root.


```
repositories {
    maven {
        url "https://youmitsu.github.io/Clipper/repository"
    }
}
```


- Add dependencies into your `app/build.gradle` file.

```
dependencies {
    ...
    
    implementation "jp.co.youmeee:clipper:0.3.2"  // Add it here.
}
```


## Usage


1. Create the `ClipEntry` instance. `ClipEntry` includes the object to be clipped and its margin. I prepared shapes(eg. circle, rect, and custom) to clip.

```kotlin

val cl = ClipperLayout(this)  // initialize a ClipperLayout object.

val cl2 = ClipperLayout(this).also {  // initialize a ClipperLayout object and its propeties by also{}.
    it.add(CircleClipEntry(textView), CircleClipEntry(textView2))  //
    it.clipAnimator = DefaultClipAnimator()
    it.descView = DescriptionView(imageView)
}
```

2. Execute clipping.


```kotlin
cl2.clip()
```

3. If you want to execute multiple ClipperLayout on same time, you can use `BundleClipExecutor`


```
Clipper.createBundleExecutor(container, window, cl, cl2).clip()
```

## Licence

Copyright (c) 2019 Yu Mitsuhori (youmitsu).

Licensed under the MTI License.
