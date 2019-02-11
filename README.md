[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

# ClippableLayout


The layout of Android development to be used for clipping view by specific shape(eg. circle, rectangle, custom shape).


## Abstract


This library makes you help creating Tutorial
It's useful for implementing Tutorial view.
When a tutorial view is showed, you may want to draw overlapping view and clip the object which you want to explain.
In that cases, This library helps you implement it.


## Requirements


- Android API Level: 19 to use
- Kotlin

## Getting Started


- add dependencies into your app/build.gradle.

```
dependencies {
    ...
    
    implementation "youmeee.co.jp:clippablelayout:0.1.2"  // Add it here.
}
```


## Usage


1. Create the `ClipEntry` instance. `ClipEntry` includes the object to be clipped and its margin. I prepared shapes(eg. circle, rect, and custom) to clip.

```kotlin
val circleEntry = CircleClipEntry(textView, resources.getDimension(R.dimen.circle_clip_margin))
val rectEntry = RectClipEntry(textView2, resources.getDimension(R.dimen.circle_clip_margin))
```

2. Create and inflate `ViewGroup` to describe the object to be clipped.

```kotlin
val tutorialDescView = layoutInflater.inflate(R.layout.clippable_description, null)
```

3. Create the `ClippableItem`. To initialize it, you should use the `Context` and the list of `ClipEntry`, ViewGroup to describe with constructor.

```kotlin
val tutorialWindow1 = ClippableItem(this, listOf(circleEntry, rectEntry))
val tutorialWindow2 = ClippableItem(this, listOf(rectEntry), imageView)
```

4. Finally, Create the `ClipExecutor` instance with `ClipExecutorFactory`. Once you call `execute()` method, the clipping is immediately executed.

```kotlin
ClipExecutorFactory.create(listOf(tutorialWindow1, tutorialWindow2), window, container).execute()
```

## Licence

Copyright (c) 2019 Yu Mitsuhori (youmitsu).

Licensed under the MTI License.