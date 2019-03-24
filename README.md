[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)
![versionBadge](https://img.shields.io/badge/version-0.3.8-green.svg)

# Clipper


The AAR library to be used for clipping view by specific shape(eg. circle, rectangle, custom shape).


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
    
    implementation "jp.co.youmeee:clipper:0.3.8"  // Add it here.
}
```


## Usage

### Show a single tutorial view

1. Create the `ClipEntry` instance. `ClipEntry` includes the object to be clipped and its margin. I prepared shapes(eg. circle, rect, and custom) to clip.

```kotlin

val circleClipEntry = CircleClipEntry(textView)  // CircleClipEntry is the subclass of ClipEntry for clipping with a circle shape.

val rectClipEntry = RectClipEntry(textView2)  // RectClipEntry is the subclass of ClipEntry for clipping with a rectangle shape.

// You can set margin for clipping.
val circleClipEntry = CircleClipEntry(textView, resources.getDimensionPixelSize(R.dimen.fab_margin))  // Set the margin value into second args of ClipEntry constructor.

```

2. Create `ClipperLayout` via `ClipperLayoutInflater`

You should use `ClipperLayoutInflater.inflate()` when you create the ClipperLayout instance.
ClipperLayout belongs to ViewGroup class, which is FrameLayout. 
This layout includes overlapping view and describing view, and can execute a clipping behavior.


```kotlin

val circleClipEntry = CircleClipEntry(textView)
val cl = ClipperLayoutInflater.inflate(this, R.layout.tutorial_view_first)  // Create the ClipperLayout.
cl.addEntry(circleClipEntry)  // Set ClipEntry instance.
cl.addEntry(rectClipEntry)  // Add more ClipEntry instances.
cl.clip()  // Execute clipping.

// Write above codes simply.
ClipperLayoutInflater.inflate(this, R.layout.tutorial_view_first)
    .addEntry(circleClipEntry, rectClipEntry)
    .clip()

```


3. (Optional) Add an animation.

If you want to add animation, you can set ClipAnimator class when execution clipping.

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


## ClipAnimator

I prepared two ClipAnimator classes.

### 1. DefaultClipAnimator

An animation in which the alpha value changes gradually.


### 2. CircleRevealClipAnimator

Animation that changes to circular shape.


## Licence

Copyright (c) 2019 Yu Mitsuhori (youmitsu).

Licensed under the MTI License.
