# AdvancedRecyclerView
[![Release](https://jitpack.io/v/StephenVinouze/AdvancedRecyclerView.svg)](https://jitpack.io/#StephenVinouze/AdvancedRecyclerView)
[![Build Status](https://travis-ci.org/StephenVinouze/AdvancedRecyclerView.svg)](https://travis-ci.org/StephenVinouze/AdvancedRecyclerView)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AdvancedRecyclerView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/3553)
[![GitHub license](http://img.shields.io/badge/license-APACHE2-blue.svg)](https://github.com/StephenVinouze/AdvancedRecyclerView/blob/master/LICENSE)

Before the appearance of `RecyclerView`, `ListView` and `GridView` were the common layout used to display lists of items.
Even though `RecyclerView` has optimized how to render list of items without having to worry how you want to display them (either list or grid) it comes with a price when implementing such behaviors.

This library comes with two focuses:

* Make your `RecyclerView` basic implementation dead simple.
* Intagrating useful features that could be found in either `ListView` and `GridView` (such as choice mode) and even more (sections, pagination, gestures).

Single choice | Multiple choice | Sections
---- | ---- | ----
![Single choice](art/single_choice_framed.png) | ![Multiple choice](art/multiple_choice_framed.png) | ![Sections](art/sections_framed.png)
  
## Migrate to v2

If you are already using the v1 of this library and considering migrating to the v2, here are a few things worth mentioning

* Several methods were renamed for clarification
* Packages were renamed to apply latest Android Studio guidelines. Some classes were also rearranged in the process.
* A Kotlin sample was added and callback systems were changed in favor of lambdas to match with Kotlin language.
* In order to not disadvantage Java users I kept the previous methods. Both Java and kotlin samples are available in this repository.
* For Java users, Java 8 is now required for this library because of lambdas. 

## Basic usage

The *core* module contains the basic logic to easily manipulate a `RecyclerView`. 
It allows you to define your adapter in a blink of the eye, with a already built-in `ViewHolder` pattern so that you just need to define how your items will be laid out in your list. 

Create your model :

```kotlin
data class Sample(val id: Int, val rate: Int, val name: String)
```

Create your view :

```kotlin
class SampleItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_sample_item, this, true)
    }

    fun bind(sample: Sample) {
        // Update your subviews with the Sample data 
    }

}
```

Then define your own adapter that extends from `RecyclerAdapter` and template it with your model that will be used to populate your list :

```kotlin
class SampleAdapter(context: Context) : RecyclerAdapter<Sample>(context) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View = SampleItemView(context)

    override fun onBindItemView(view: View, position: Int) {
        when (view) {
            is SampleItemView -> view.bind(items[position])
        }
    }

}
```

Finally in your `Activity`/`Fragment`, add your `Sample` items to your `SampleAdapter` and bind your adapter to your `RecyclerView`:

```kotlin
val adapter = SampleAdapter(this)
adapter.items = mutableListOf()
recyclerView.adapter = adapter
```

Your `RecyclerView` is ready to be displayed. You just have to implement your `bind()` method within your view to configure it (or use the databinding pattern if you prefer).

### Click events

Listening to click events are often required and usually requires to transit such an event from your adapter to your `Activity`/`Fragment` to maintain a clean architecture.
This library provides to optional lambdas for click and long click events that can be called directly from your adapter :

```kotlin
adapter.onClick = { view, position ->
    val sample = items[position]
    // Do whatever you want
}
adapter.onLongClick = { view, position ->
    val sample = items[position]
    // Do whatever you want
}
```

### Choice mode

A useful feature that can be found in either `ListView` and `GridView` is **ChoiceMode**. 
Although this does not come natively with `RecyclerView`, this library provides such a mechanism.  

Choice mode can be either *NONE* (default), *SINGLE* or *MULTIPLE*. 
If a choice mode other than *NONE* is declared for your adapter, your selected items will be internally stored at each view click.
You can also manually select an item :

```kotlin
adapter.toggleItemView(position)
```

You can obviously retrieve which items are selected :
 
```kotlin
adapter.selectedItemViewCount // Returns the selected item view count
adapter.getSelectedItemViews() // Returns a list of selected item views
adapter.isItemViewToggled(position) // Returns true if item is selected
```

If you need to remove all selected items :

```kotlin
adapter.clearSelectedItemViews()
```

## Advanced usage

In most cases the *core* module should handle most of the heavy work. 
This section presents more advanced concepts that can help you while using `RecyclerView` in your applications.

### Section

You may encounter lists that need to be regrouped within sections to present a clear sorting or your items. 
This can be easily done natively by overriding `getItemViewType()` and manipulate yourself each viewType in your adapter callbacks.
The *section* module intends to lift this work for you as well as to provide a clear implementation by separating section building callbacks from main items in your list.

Create a section item view to render your sections. Let's call it `SampleSectionItemView`.

Extends your adapter class from `RecyclerSectionAdapter` (which itself extends from `RecyclerAdapter`) and provides two more abstract methods to shape your views that will be displayed as sections :

```kotlin
class SampleSectionAdapter(context: Context) : RecyclerSectionAdapter<Int, Sample>(context, { it.rate }) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View = SampleItemView(context)

    override fun onBindItemView(view: View, position: Int) {
        when (view) {
            is SampleItemView -> view.bind(items[position])
        }
    }

    // Override these two new methods to render your sections
       
    override fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View =
            SampleSectionItemView(context)

    override fun onBindSectionItemView(sectionView: View, sectionPosition: Int) {
        sectionAt(sectionPosition)?.let {
            when (sectionView) {
                is SampleSectionItemView -> sectionView.bind(it)
            }
        }
    }

}
```

Note the **Int** generic type in the class declaration that indicates the type that will contains the section. In our case, we want to sort them by rate.
The building of the sections will be automatically taken care of by a lambda that you must provide in your constructor ()`{ sample -> sample.rate }`).

Wraps things up the way you were doing with your `RecyclerAdapter` :

```kotlin
val sectionAdapter = SampleSectionAdapter(this)
sectionAdapter.items = mutableListOf()
recyclerView.adapter = sectionAdapter
```

Setting items to your adapter will take care of the section building and relayout your list.
You may want to sort your items before setting them in your adapter to obtain consistent sections.

### Pagination

Pagination is a common pattern especially when interacting with an API to lazy load your items into your list.
The *pagination* module provides an extension to the `RecyclerView` class to easily activate pagination and notify what to do when pagination is triggered.
You should also notify your users that your content is being loaded into your list by providing a loader at the bottom of your list.

Extend your adapter from `RecyclerPaginationAdapter` and implement the loader creator callback :

```kotlin
class SamplePaginationAdapter(context: Context) : RecyclerPaginationAdapter<Sample>(context) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View = SampleItemView(context)

    override fun onBindItemView(view: View, position: Int) {
        when (view) {
            is SampleItemView -> view.bind(items[position], isItemViewToggled(position))
        }
    }

    override fun onCreateLoaderView(parent: ViewGroup, viewType: Int): View =
            LayoutInflater.from(context).inflate(R.layout.view_progress, parent, false)

}
```

Activate pagination on your `RecyclerView` and populate your list with the `appendItems()` extension method :

```kotlin
val paginationAdapter = SamplePaginationAdapter(this)
recyclerView.adapter = paginationAdapter
recyclerView.enablePagination(
    isLoading = {
        paginationAdapter.isLoading
    },
    hasAllItems = {
        // return true if you have loaded all your items to stop the pagination to try loading more
    },
    onLoad = {
        // Fetch and/or load your items within your list
        paginationAdapter.isLoading = true
        // Fetch items from API. Indicate loading done once fetch is finished
        paginationAdapter.isLoading = false
        // Append items into your list
        paginationAdapter.appendItems(items)
    }
)
```

:warning: Note that `RecyclerPaginationAdapter` extends from `RecyclerAdapter` as pagination for sectioned content is not (and won't be) supported. 

### Gesture

The `RecyclerView` component allows you to interact with your items by draging them with the `ItemTouchHelper` class. 
The *gesture* module abstracts this behavior to let you easily swipe-to-delete and/or move your items from your `RecyclerView`.
Items are manipulated for you for both move on swipe-to-delete gestures.

You can enable gestures by using the `RecyclerView` extension method :

```kotlin
recyclerView.enableGestures(
    dragDirections = ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    swipeDirections = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
    onMove = { fromPosition, toPosition ->
        // Do whatever you want
        true
    },
    onSwipe = { position, direction ->
        // Do whatever you want
    }
)
```

Note that all lambdas are optionals so that you may enable/configure your desired gestures.
Also, the *gesture* module includes the *section* module so that all your gestures also works while using your list with sections. 
The only limitation is that you cannot move an item from one section to another.

## Gradle Dependency

The gradle dependency is available via [JitPack](https://jitpack.io/#StephenVinouze/AdvancedRecyclerView). Add this in your root `build.gradle` file:

```groovy
allprojects {
    repositories {
	    maven { url "https://jitpack.io" }
    }
}
```

Then add the dependencies that you need in your project.

```groovy
def advancedrecyclerview_version = "{latest_version}"

dependencies {
    compile "com.github.StephenVinouze.AdvancedRecyclerView:core:${advancedrecyclerview_version}"
  
    // If you need to display lists with sections
    compile "com.github.StephenVinouze.AdvancedRecyclerView:section:${advancedrecyclerview_version}"
   
    // If you need to paginate your lists
    compile "com.github.StephenVinouze.AdvancedRecyclerView:pagination:${advancedrecyclerview_version}"
  
    // If you need to handle gestures within your lists (note that it will include the section module as well)
    compile "com.github.StephenVinouze.AdvancedRecyclerView:gesture:${advancedrecyclerview_version}"
}
```

## Pull requests

I welcome and encourage all pull requests. I might not be able to respond as fast as I would want to but I endeavor to be as responsive as possible.

All PR must:

1. Be written in Kotlin
2. Maintain code style
3. Indicate whether it is a enhancement, bug fix or anything else
4. Provide a clear description of what your PR brings
5. Enjoy coding in Kotlin :)
