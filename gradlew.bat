<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/mobile_navigation"
    app:startDestination="@+id/nav_home">

    <fragment
        android:id="@+id/nav_home"
        android:name="com.example.myapplication.ui.home.HomeFragment"
        android:label="@string/menu_home"
        tools:layout="@layout/fragment_home" >

        <action
            android:id="@+id/action_HomeFragment_to_HomeSecondFragment"
            app:destination="@id/nav_home_second" />
    </fragment>
    <fragment
        android:id="@+id/nav_home_second"
        android:name="com.example.myapplication.ui.home.HomeSecondFragment"
        android:label="@string/home_second"
        tools:layout="@layout/fragment_home_second">
        <action
            android:id="@+id/action_HomeSecondFragment_to_HomeFragment"
            app:destination="@id/nav_home" />

        <argument
            android:name="myArg"
            app:argType="string" />
    </fragment>

    <fragment
        android:id="@+id/nav_gallery"
        android:name="com.example.myapplication.ui.gallery.GalleryFragment"
        android:label="@string/menu_gallery"
        tools:layout="@layout/fragment_gallery" />

    <fragment
        android:id="@+id/nav_slideshow"
        android:name="com.example.myapplication.ui.slideshow.SlideshowFragment"
        android:label="@string/menu_slideshow"
        tools:layout="@layout/fragment_slideshow" />
</navigation>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    