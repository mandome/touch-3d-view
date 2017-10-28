# Touch3DView
A 3D touch View like [Smartisan official website](http://www.smartisan.com/#/)'s banner.The View extends
RelativeLayout.

![View Sample](https://github.com/mandome/Touch3DView/blob/master/gif/ezgif-4-068f518748.gif)

# Usage
For a working implementation of this project see the `app/` folder.

1. Include the following dependency in your `build.gradle` file.

```groovy
compile 'com.mandome:touch3DView:1.0.0'
```

Or add the library as a project.

2. Include the `Touch3DView` widget in your layout.

```xml
<com.bruce.touch.Touch3DView
        android:id="@+id/touch_view"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        app:shadow_color="@color/mShadowColor"
        app:z_depth="depth2"
        app:touch_level="3">

    </com.bruce.touch.Touch3DView>
```

#### Other Options
In the xml definition,  you can set:

* **z_depth:** the shadow level, between depth0 to depth5, depth0 meas no shadow, depth5 meas the max shadow, default depth2.You can also set the shadow freely in java code.
* **z_depth_shape:** the shadow shape, obtain rect and oval.Default rect.
* **touch_level:** the int parameter, default value is 2.The bigger the number, the bigger the max Z depth.
* **do_anim:** if show shadow with anim.Default value is true.
* **anim_duration:** the anim duration.
* **shadow_color:** the shadow color.

#### In Java Code

* **setTouchLevel(int touchLevel)**
* **setShadowColor(int shadowColor)**
* **setZDepth(int depth):** like xml definition "z_depth", between 0 to 5.
* **setZDepth(ZDepth zDepth):** new ZDepth(float mOffsetYTopShadow, float mOffsetYBottomShadow, float mOffsetXLeftShadow, float mOffsetXRightShadow).All the parameters are positive number.

# extend
If you want to use this view in RecycleView or Viewpager,they may exist sliding conflicts.
#### Use In RecycleView
#### Use In ViewPager

# Developed By

 * Mandome - <abcd553847323@gmail.com>
 
 License
 -------
 
     Copyright 2017 Mandome
 
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
 
        http://www.apache.org/licenses/LICENSE-2.0
 
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
