package com.kanaya.presentation.screens.map

import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.kanaya.common.base.activity.BaseActivity
import com.kanaya.common.utils.AppUtils
import com.kanaya.core.model.dto.TweetModel
import com.kanaya.mvvmexample.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.getViewModel


class MainActivity: BaseActivity(), OnMapReadyCallback {
    private lateinit var viewModel: ListTaskViewModel
    private val tweetsList = ArrayList<TweetModel>()
    private val appUtils = AppUtils()
    private lateinit var mMap: GoogleMap
    override fun init() {
        viewModel = getViewModel()
        viewModel.getTweets("ny")
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun setEvents() {
        // Input Text Search
        inputSearchTweets.setEndIconOnClickListener {
            txtSearch.setText("")
        }

        txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(txtSearch.text.toString())
                true
            }
            false
        }

    }

    private fun performSearch(query: String) {
        viewModel.getTweets(query)
    }

    override fun setObservers() {
        viewModel.tweetList.observe(this, Observer {
            it?.let {
                tweetsList.clear()

                tweetsList.addAll(
                    Gson().fromJson(
                        Gson().toJson(it?.data),
                        Array<TweetModel>::class.java
                    ).toList()
                )
                addMarkers(tweetsList)
                Log.i("Tweets : ", Gson().toJson(it))

            }
        })
        viewModel.isLoading.observe(this, Observer {
            it?.let {
                if (it) {
                    showLoadingDialog()
                } else {
                    hideLoadingDialog()
                }
            }
        })
        viewModel.showMessage.observe(this, Observer {
            it?.let {
                showMessage(it)
            }
        })

    }

    private fun addMarkers(tweetsList: ArrayList<TweetModel>) {
        val list:ArrayList<TweetModel> = ArrayList()
        list.clear()
        for (tweet in tweetsList){
            tweet.lat = appUtils.generateLat()
            tweet.long =appUtils.generateLong()
            list.add(tweet)
        }
        showPins(list)

    }
    private fun showPins(tweetsList: ArrayList<TweetModel>){
        try {
            mMap.clear()
            if (tweetsList.isNotEmpty()) {
                var location:LatLng? = null
                for (tweet in tweetsList) {
                     location = LatLng(tweet.lat!!, tweet.long!!)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title("${tweet.user?.name} ${tweet.user?.screenName}".replace("null","User"))
                            .snippet("${tweet.text}")
                            .icon(
                                appUtils.bitmapDescriptorFromVector(
                                    this,
                                    R.drawable.ic_pin
                                )
                            )
                    )
                }
                val INIT = CameraPosition.Builder().target(location!!).zoom(15.5f)
                    .bearing(300f) // orientation
                    .tilt(50f) // viewing angle
                    .build()
                // use map to move camera into position
                // use map to move camera into position
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(INIT))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.animateCamera(CameraUpdateFactory.zoomTo(9.0f))
    }
}