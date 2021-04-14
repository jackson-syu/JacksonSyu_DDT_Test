package com.hikari.jacksonsyu_ddt_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * Created by hikari on 2020/9/23
 */
class MainActivity : AppCompatActivity() {

    lateinit var photoview: PhotoView

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        initHandler();
    }

    fun init() {
        Log.d(TAG, "MainActivity~");

        photoview = findViewById(R.id.main_photoview)

    }

    private fun initHandler() {
        var attacher: PhotoViewAttacher = PhotoViewAttacher(photoview)
        attacher.setOnViewTapListener(object : PhotoViewAttacher.OnViewTapListener{
            override fun onViewTap(view: View?, x: Float, y: Float) {

            }
        })
    }
}