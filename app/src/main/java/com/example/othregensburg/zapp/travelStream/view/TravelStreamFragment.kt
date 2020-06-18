package com.example.othregensburg.zapp.travelStream.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.othregensburg.zapp.R
import com.example.othregensburg.zapp.travelStream.controller.TravelStreamAdapter
import com.example.othregensburg.zapp.travelStream.model.DsTravelStream
import com.example.othregensburg.zapp.utils.Args
import java.util.ArrayList

class TravelStreamFragment : Fragment() {
    private var mRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_travel_stream, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupToolbar()
        setupUiComponents()
        setupRecyclerView()
    }

    /**
     * Set the passed title for the toolbar
     */
    private fun setupToolbar() {
        val title: String? = arguments?.getString(Args.TOOLBAR_TITLE)
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun setupUiComponents() {
        mRecyclerView = view?.findViewById(R.id.fragment_travel_stream_rv) as RecyclerView
    }

    private fun setupRecyclerView() {
        // Init the LayoutManager
        val layoutManager = LinearLayoutManager(activity)
        // Set the orientation for the LayoutManager
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        // Set LayoutManager for the RecyclerView
        mRecyclerView?.layoutManager = layoutManager
        // Init Adapter for RecyclerView
        val adapter = activity?.let { TravelStreamAdapter(it, generateDummyData()) }
        // Set the adapter
        mRecyclerView?.adapter = adapter
    }

    private fun generateDummyData(): List<DsTravelStream> {
        val data = ArrayList<DsTravelStream>()
        val object1 =
            DsTravelStream("Antelope Canyon", "USA, Arizona", R.drawable.travel_stream_1)
        val object2 =
            DsTravelStream("New York", "USA, New York", R.drawable.travel_stream_2)
        val object3 = DsTravelStream("Wyoming wilderness",
            getString(R.string.travel_stream_card_subtitle),
            R.drawable.travel_stream_3)
        data.add(object1)
        data.add(object2)
        data.add(object3)
        return data
    }

    companion object {
        fun newInstance(title: String?): TravelStreamFragment {
            val args = Bundle()
            args.putString(Args.TOOLBAR_TITLE, title)
            val fragment = TravelStreamFragment()
            fragment.arguments = args
            return fragment
        }
    }
}