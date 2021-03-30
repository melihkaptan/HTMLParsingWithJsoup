package com.example.htmlparsingwithjsoup.presantation

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.htmlparsingwithjsoup.model.OnItemClickListener
import com.example.htmlparsingwithjsoup.R
import com.example.htmlparsingwithjsoup.adapters.ItemListAdapter
import com.example.htmlparsingwithjsoup.base.BaseFragment
import com.example.htmlparsingwithjsoup.enums.CutType
import com.example.htmlparsingwithjsoup.model.Cut
import com.example.htmlparsingwithjsoup.presantation.viewmodel.CutViewModel
import com.example.htmlparsingwithjsoup.presantation.viewmodel.NetworkConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_fragment.*

@AndroidEntryPoint
class WaterFragment : BaseFragment(), OnItemClickListener {

    lateinit var adapter: ItemListAdapter
    var cutList: ArrayList<Cut> = ArrayList()
    private var emptyList = ArrayList<Cut>()

    override fun getLayoutRes(): Int = R.layout.list_fragment

    override fun initView() {
        super.initView()
        val viewModel = provideViewModel()

        initRecyclerView()
        initNetworkObserver(viewModel)
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

        adapter = ItemListAdapter(emptyList, this)

        recyclerView?.let {
            it.layoutManager =
                GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false)
        }
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

    private fun initNetworkObserver(viewModel: CutViewModel) {
        NetworkConnectionLiveData(context ?: return)
            .observe(viewLifecycleOwner, Observer { isConnected ->
                if (isConnected) {
                    // Internet Available
                    if (adapter.itemList.isEmpty())
                        viewModel.getDocument(CutType.WATER)
                } else {
                    // Internet Non Available
                    Toast.makeText(
                        context,
                        "  The application can not reach to server at the moment. Network connection is not available.",
                        Toast.LENGTH_LONG
                    ).show();
                    updateRecyclerView(emptyList)
                }
                return@Observer
            })

    }


}