[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)
![versionBadge](https://img.shields.io/badge/version-0.3.8-green.svg)

# Clipper


The AAR library to be used for clipping view by specific shape(eg. circle, rectangle, custom shape).


## Abstract


This library makes you help creating some tutorial views.
When a tutorial view is showed, you may want to draw overlapping view and clip the object which you want to explain.
In that cases, This library helps you implement it.

## Sample project

Here: 
https://github.com/youmitsu/ClipperSample

## Requirements


- Android API Level: 21 to use
- Use only from Kotlin class, not intended for use from Java


## Getting Started


- Add this into your `build.gradle` file of project root. If you have already added, you don't need to add it.


```
repositories {
    jcenter()  // Add it here.
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

1. Create the ClipperLayout's layout resource file, e.g. `tutorial_view_first.xml`.

ClipperLayout's child views is used for description againt clipped view.
There are several attributes.

- dismissTriggerItemId

This is a resource id.
When this view is clicked, the overlapping view will be dismissed.
If you don't set this attribute, the overlapping view is dismissed when it's clicked at any points in its view.

- overlayColor

This color means overlaid view's color.
We can set any resource id of the colors.
By the way, the default color is `#a7000000`.

```tutorial_view_first.xml

<?xml version="1.0" encoding="utf-8"?>
<jp.co.youmeee.clipper.ClipperLayout
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        app:dismissTriggerItemId="@id/next_button"> <!--The overlapping view will be dismissed when the id of next_button is clicked.-->

    <!--Below view is for description.-->
    <com.google.android.material.card.MaterialCardView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:minHeight="200dp"
            android:layout_marginEnd="10dp"
            android:layout_marginStart="10dp"
            android:layout_gravity="center_vertical|center_horizontal">

        <TextView
                android:id="@+id/text"
                android:layout_height="wrap_content"
                android:layout_width="match_parent"
                android:layout_gravity="center_horizontal"
                android:text="You can record voice with this button."
                android:layout_margin="20dp"
                android:textSize="20sp"/>

        <com.google.android.material.button.MaterialButton
                android:id="@+id/next_button"
                android:layout_height="wrap_content"
                android:layout_width="wrap_content"
                android:layout_gravity="right|bottom"
                android:layout_margin="20dp"
                android:text="I GOT IT!"/>

    </com.google.android.material.card.MaterialCardView>
    <!--Above view is for description.-->

</jp.co.youmeee.clipper.ClipperLayout>

```


2. Create the `ClipEntry` instance. `ClipEntry` includes the object to be clipped and its margin. I prepared shapes(eg. circle, rect, and custom) to clip.

```kotlin

val circleClipEntry = CircleClipEntry(textView)  // CircleClipEntry is the subclass of ClipEntry for clipping with a circle shape.

val rectClipEntry = RectClipEntry(textView2)  // RectClipEntry is the subclass of ClipEntry for clipping with a rectangle shape.

// You can set margin for clipping.
val circleClipEntry = CircleClipEntry(textView, resources.getDimensionPixelSize(R.dimen.fab_margin))  // Set the margin value into second args of ClipEntry constructor.

```

3. Create `ClipperLayout` via `ClipperLayoutInflater`

You should use `ClipperLayoutInflater.inflate()` when you create the ClipperLayout instance.
ClipperLayout belongs to ViewGroup class, which is FrameLayout. 
This layout includes overlapping view and describing view, and can execute a clipping behavior.


```kotlin

val circleClipEntry = CircleClipEntry(textView)
val cl = ClipperLayoutInflater.inflate(this, R.layout.tutorial_view_first)  // Create the ClipperLayout.
cl.addEntry(circleClipEntry)  // Set ClipEntry instance.
cl.addEntry(rectClipEntry)  // Add more ClipEntry instances.
cl.clip(container, window)  // Execute clipping.
```

Write above codes simply.

```

ClipperLayoutInflater.inflate(this, R.layout.tutorial_view_first)
    .addEntry(circleClipEntry, rectClipEntry)
    .clip(container, window)

```


4. (Optional) Add an animation.

If you want to add animation, you can set ClipAnimator class when execution clipping.

```kotlin

ClipperLayoutInflater.inflate(this, R.layout.tutorial_view_first)
    .addEntry(circleClipEntry, rectClipEntry)
    .clip(container, window, DefaultClipAnimator())  // Add ClipAnimator instance to the 3rd arg.

```

### Show multiple tutorial views in order


1. If you want to execute multiple ClipperLayout on same time, you can use `ClipBundleExecutor`


```kotlin

val cl = ClipperLayoutInflater.inflate(this, R.layout.tutorial_view_first)
    .addEntry(circleClipEntry)
val cl2 = ClipperLayoutInflater.inflate(this, R.layout.tutorial_view_second)
    .addEntry(circleClipEntry2)

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

## Contribution

I have developped this library yet.
So, welcome any contributers.
Please contact for me. 
And I hope you to create issues or pull requests.

## Licence

Copyright (c) 2019 Yu Mitsuhori (youmitsu).

Licensed under the MIT License.
