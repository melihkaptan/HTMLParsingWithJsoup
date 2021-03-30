package com.example.htmlparsingwithjsoup.presantation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.htmlparsingwithjsoup.R
import com.example.htmlparsingwithjsoup.adapters.ItemListAdapter
import com.example.htmlparsingwithjsoup.model.Cut
import com.example.htmlparsingwithjsoup.model.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_fragment.*

@AndroidEntryPoint
class CutDetailActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var adapter: ItemListAdapter

    private val cutList: ArrayList<Cut>? by lazy {
        intent.getParcelableArrayListExtra(
            BUNDLE_DATA
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fragment)
        initRecyclerView()

        cutList?.let {
            updateRecyclerView(it)
        }
    }

    private fun initRecyclerView() {
        recyclerView?.let {
            it.layoutManager =
                GridLayoutManager(applicationContext, 1, GridLayoutManager.VERTICAL, false)
        }

    }

    private fun updateRecyclerView(items: ArrayList<Cut>) {
        adapter = ItemListAdapter(items, this)
        recyclerView.adapter = adapter
    }

    companion object {
        private const val BUNDLE_DATA = "bundle_data"
        fun newIntent(context: Context, cutList: ArrayList<Cut>): Intent {
            val intent = Intent(context, CutDetailActivity::class.java)

            intent.putParcelableArrayListExtra(BUNDLE_DATA, cutList)

            return intent
        }
    }

    override fun onItemClicked(cut: Cut) {
        TODO("Not yet implemented")
    }
}