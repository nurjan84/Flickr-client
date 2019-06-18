package com.nurzhan.flickr.mvvm.views


import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.nurzhan.flickr.R
import com.nurzhan.flickr.adapters.PhotosAdapter
import com.nurzhan.flickr.callbacks.Callback
import com.nurzhan.flickr.databinding.FragmentMainBinding
import com.nurzhan.flickr.mvvm.viewmodels.MainFragmentViewModel
import com.nurzhan.flickr.room.entities.Photo
import com.nurzhan.flickr.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), View.OnClickListener {

    private val mainFragmentViewModel: MainFragmentViewModel by lazy { ViewModelProviders.of(this, providerFactory).get(MainFragmentViewModel::class.java) }
    private lateinit var  adapter : PhotosAdapter
    private lateinit var popupList: ListPopupWindow
    private var listState : Parcelable? = null
    private var layoutManager: GridLayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_main, container, false)
        binding.mainViewModel = mainFragmentViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popupList = ListPopupWindow(activity!!)
        searchSuggestionsListener()
        searchImeOptionListener()
        listStateListener()
        photosListener()
        searchButton.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        listState = layoutManager?.onSaveInstanceState()
        outState.putParcelable("listState",  listState)
        super.onSaveInstanceState(outState)
    }

    private fun setupAdapter(){
        layoutManager = GridLayoutManager(activity, 3)
        photosRecyclerView.layoutManager = layoutManager
        adapter = PhotosAdapter(activity!!,  Utils.getProductSquareSize(activity!!, 6,3))
        photosRecyclerView.adapter = adapter
        adapter.setOnPhotoClickListener(Callback {
            val photo = it as Photo
            Navigation.findNavController(photosRecyclerView).navigate(MainFragmentDirections.gotoPhotoViewer(photo))
        })
    }

    override fun onClick(v: View?) {
        when(v){
            searchButton -> {
                photosListener()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mainFragmentViewModel.setListState( layoutManager?.onSaveInstanceState())
    }

    private fun listStateListener(){
        mainFragmentViewModel.listState().observe(viewLifecycleOwner, Observer{ state ->
            listState = state
        })
    }

    private fun searchImeOptionListener(){
        searchField.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                photosListener()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun photosListener(){
        setupAdapter()
        mainFragmentViewModel.searchPhotos()
        mainFragmentViewModel.searchResults().removeObservers(viewLifecycleOwner)
        mainFragmentViewModel.searchResults().observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
            photosRecyclerView.layoutManager?.onRestoreInstanceState(listState)
        })
    }

    private fun createPopupListWindow(suggestions: Array<String>){
        if(suggestions.isNotEmpty()){

            popupList.setAdapter(ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, suggestions))
            popupList.anchorView = searchField

            popupList.setOnItemClickListener { _, view, _, _ ->
                val text = (view as TextView).text
                mainFragmentViewModel.suggestionSelected = true
                mainFragmentViewModel.searchText = text.toString()
                searchField.setText(text)
                photosListener()
                popupList.dismiss()
            }

            searchField.post {
                popupList.width = searchField.measuredWidth
                popupList.show()
            }

        }else{
            popupList.dismiss()
        }
    }

    private fun searchSuggestionsListener(){
        mainFragmentViewModel.searchSuggestions().observe(viewLifecycleOwner, Observer{ suggestions ->
            if(suggestions != null){
                createPopupListWindow(suggestions)
                mainFragmentViewModel.setSuggestions(null)
            }
        })
    }
}
