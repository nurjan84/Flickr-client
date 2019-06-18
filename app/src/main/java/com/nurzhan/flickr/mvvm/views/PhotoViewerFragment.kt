package com.nurzhan.flickr.mvvm.views


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator
import com.nurzhan.flickr.R
import com.nurzhan.flickr.room.entities.Photo
import kotlinx.android.synthetic.main.fragment_photo_viewer.*

class PhotoViewerFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var photo:Photo? = null
        arguments?.let {
            photo = PhotoViewerFragmentArgs.fromBundle(it).photo
        }

        imageView.setProgressIndicator(ProgressPieIndicator())

        val imageUrl = if(photo?.urlO != null){
            photo?.urlO
        }else{
            "https://farm${photo?.farm}.staticflickr.com/${photo?.server}/${photo?.id}_${photo?.secret}.jpg"
        }

        imageView.showImage(Uri.parse(imageUrl))
    }


}
