package com.example.othregensburg.zapp.music.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.othregensburg.zapp.R
import com.example.othregensburg.zapp.music.controller.AlbumAdapter
import com.example.othregensburg.zapp.music.model.DsAlbum
import com.example.othregensburg.zapp.utils.Args
import java.util.ArrayList

class MyMusicFragment : Fragment() {
    private var mContent: ArrayList<Any>? = null
    private var mAlbumAdapter: AlbumAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null

    /*
     * Lifecycle methods
     */
//    override fun onCreateView(
//        inflater: LayoutInflater,
//         container: ViewGroup?,
//         savedInstanceState: Bundle?): View {
//        return inflater.inflate(R.layout.fragment_my_music, container, false)
//
//    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /*
     * Private methods
     */
    private fun init() {
        mContent = ArrayList()
        setupUiComponents()
        setupToolbar()
        setupRecyclerView()
        loadContent()
    }

    private fun setupUiComponents() {
        mProgressBar = view?.findViewById(R.id.fragment_my_music_progress_bar)
        mRecyclerView =
            view?.findViewById(R.id.fragment_my_music_recycler_view) as RecyclerView
    }

    /**
     * Set the passed title for the toolbar
     */
    private fun setupToolbar() {
        val title: String? = arguments?.getString(Args.TOOLBAR_TITLE)
        // We have a Toolbar in place so we don't need to care about the NPE warning
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView?.setLayoutManager(layoutManager)
        mAlbumAdapter = getActivity()?.let { AlbumAdapter(it, mContent!!) }
        mRecyclerView?.setAdapter(mAlbumAdapter)
    }

    // Mockup method - usually you would fetch the data from the internet for example
    private fun loadContent() {
        val handler = Handler()
        handler.postDelayed({
            generateDummyData()
            generatePalette()
            showContentWithDelay(1000)
        }, 1000)
    }

    private fun generateDummyData() {
        // dummy object to show header - see AlbumAdapter#getViewType
        mContent!!.add(Any())

        // dummy album data
        val dsAlbumSupermodel =
            DsAlbum("Supermodel", "Foster the People", R.drawable.cover_foster_the_people)
        val dsAlbumHalcyonDays =
            DsAlbum("Halcyon Days", "Ellie Goulding", R.drawable.cover_ellie_goulding)
        val dsAlbumTheTruthAboutLove =
            DsAlbum("The Truth About Love", "P!nk", R.drawable.cover_truth_about_love)
        mContent!!.add(dsAlbumSupermodel)
        mContent!!.add(dsAlbumHalcyonDays)
        mContent!!.add(dsAlbumTheTruthAboutLove)
    }

    /**
     * Gets the dominant color for each Bitmap
     */
    private fun generatePalette() {
        val defaultColor = 0x000000
        for (o in mContent!!) {
            if (o is DsAlbum) {
                val bmp = BitmapFactory.decodeResource(resources, o.resIdImage)
                if (bmp != null && !bmp.isRecycled) {
                    Palette.from(bmp).generate(object : Palette.PaletteAsyncListener {
                        override fun onGenerated(palette: Palette?) {
                            if (palette != null) {
                                o.backgroundColor = palette.getDarkVibrantColor(defaultColor)
                            }
                        }
                    })
                }
            }
        }
    }

    /*
     * NOTE: Palette is created asynchronously so we can't be sure that that we have all colors
     * in place once the delayed action is called. For demo purpose this is OK - in production you
     * should go for a different solution.
     */
    private fun showContentWithDelay(delay: Long) {
        val handler = Handler()
        handler.postDelayed({ showContent() }, delay)
    }

    private fun showContent() {
        hideProgressBar()
        addContentToRecyclerView()
    }

    private fun hideProgressBar() {
        mProgressBar!!.visibility = View.GONE
    }

    private fun addContentToRecyclerView() {
        // Notify the RecyclerView that items have been added
        mAlbumAdapter!!.notifyItemRangeChanged(0, mContent!!.size)
    }

    companion object {
        fun newInstance(title: String?): MyMusicFragment {
            val args = Bundle()
            args.putString(Args.TOOLBAR_TITLE, title)
            val fragment = MyMusicFragment()
            fragment.arguments = args
            return fragment
        }
    }
}