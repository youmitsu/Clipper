[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)
![versionBadge](https://img.shields.io/badge/version-0.3.7-green.svg)

# Clipper


A library of Android development to be used for clipping view by specific shape(eg. circle, rectangle, custom shape).


## Abstract


This library makes you help creating some tutorial views.
When a tutorial view is showed, you may want to draw overlapping view and clip the object which you want to explain.
In that cases, This library helps you implement it.


## Requirements


- Android API Level: 21 to use
- Use only from Kotlin class, not intended for use from Java


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
    
    implementation "jp.co.youmeee:clipper:0.3.7"  // Add it here.
}
```


## Usage

### Show a single tutorial view

1. Create the `ClipEntry` instance. `ClipEntry` includes the object to be clipped and its margin. I prepared shapes(eg. circle, rect, and custom) to clip.

```kotlin

val cl = ClipperLayout(this)  // Initialize a ClipperLayout instance.

val cl2 = ClipperLayout(this, DescriptionView(imageView))  // Initialize a ClipperLayout instance with DescriptionView.
        .addEntry(CircleClipEntry(textView))  // Initialize Entry class and add to the ClipperLayout instance.
        .clip(container, window)  // Execute clipping!! `container` is a parent ViewGroup
}
```

2. If you want to add animation, you can set ClipAnimator class when execution clipping.

```kotlin

val cl2 = ClipperLayout(this, DescriptionView(imageView))
        .addEntry(CircleClipEntry(textView))
        .clip(container, window, DefaultClipAnimator()) // <- Add DefaultClipAnimator().
}
```

### Show multiple tutorial views in order


1. If you want to execute multiple ClipperLayout on same time, you can use `ClipBundleExecutor`


```kotlin

val cl = ClipperLayout(this, DescriptionView(imageView1))
        .addEntry(CircleClipEntry(textView1))
val cl2 = ClipperLayout(this, DescriptionView(imageView2))
        .addEntry(CircleClipEntry(textView2))

// Create `ClipBundleExecutor` through a `Clipper.createBundleExecutor()` method. To execute clipping, call `execute()` method. 
Clipper.createBundleExecutor(container, window, cl, cl2).execute()  

```

2. If you want to add animation, you can set ClipAnimator class when execution clipping.


```kotlin

Clipper.createBundleExecutor(container, window, CircleRevealClipAnimator(), cl, cl2).execute() 

```


## List of ClipAnimators

I prepared two ClipAnimator classes.

### 1. DefaultClipAnimator

An animation in which the alpha value changes gradually.


### 2. CircleRevealClipAnimator

Animation that changes to circular shape.


## Licence

Copyright (c) 2019 Yu Mitsuhori (youmitsu).

Licensed under the MTI License.
