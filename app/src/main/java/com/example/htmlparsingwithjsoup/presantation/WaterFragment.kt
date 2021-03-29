package com.example.htmlparsingwithjsoup.presantation

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.htmlparsingwithjsoup.model.OnItemClickListener
import com.example.htmlparsingwithjsoup.R
import com.example.htmlparsingwithjsoup.adapters.ItemListAdapter
import com.example.htmlparsingwithjsoup.base.BaseFragment
import com.example.htmlparsingwithjsoup.enums.CutType
import com.example.htmlparsingwithjsoup.model.Cut
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_fragment.*

@AndroidEntryPoint
class WaterFragment : BaseFragment(), OnItemClickListener {

    lateinit var adapter: ItemListAdapter
    var cutList: ArrayList<Cut> = ArrayList()


    override fun getLayoutRes(): Int = R.layout.list_fragment

    override fun initView() {
        super.initView()
        initRecyclerView()
        val viewModel = provideViewModel()
        viewModel.getDocument(CutType.WATER)

        viewModel.isLoadingLiveData.observe(this, Observer {
            if (it)
                showBlockingPane()
            else
                hideBlockingPane()
        })

        viewModel.getDocumentLiveData.observe(this, Observer {
            if (it.hasText()) {
                updateRecyclerView(evaluateAllData(it))
            }
        })

        viewModel.getDocumentDetailLiveData.observe(this, Observer {
            if (it.hasText()) {
                evaluateDetailData(it)
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView?.let {
            it.layoutManager =
                GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false)
        }
        updateRecyclerView(cutList)
    }

    private fun updateRecyclerView(items: ArrayList<Cut>) {
        adapter = ItemListAdapter(items, this)
        recyclerView.adapter = adapter
    }


    override fun onItemClicked(cut: Cut) {
        val viewModel = provideViewModel()
        cut.linkText?.let {
            viewModel.getDocumentDetail(it)
        }
    }



}