package com.example.htmlparsingwithjsoup.base

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.htmlparsingwithjsoup.Constants
import com.example.htmlparsingwithjsoup.extensions.createProgressDialog
import com.example.htmlparsingwithjsoup.model.Cut
import com.example.htmlparsingwithjsoup.presantation.CutDetailActivity
import com.example.htmlparsingwithjsoup.presantation.viewmodel.CutViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    @MenuRes
    open val menuRes = Constants.NO_RES

    private var blockingPane: Dialog? = null

    private var blockingOperationCount = 0
    private var headerList: MutableList<String> = ArrayList()
    private var cutList: ArrayList<Cut> = ArrayList()
    var detailList: List<Pair<String, String>> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(menuRes != Constants.NO_RES)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (menuRes != Constants.NO_RES) {
            menu.clear()
            inflater.inflate(menuRes, menu)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    fun provideViewModel(): CutViewModel {
        return ViewModelProvider(this).get(CutViewModel::class.java)
    }

    open fun initView() {
        //can be overridden
    }

    fun getApplication() = activity?.application

    fun getApplicationContext() = getApplication()?.applicationContext

    fun showBlockingPane() {
        if (blockingOperationCount == 0) {
            if (blockingPane == null) {
                blockingPane = activity?.createProgressDialog()
            }

            blockingPane?.let {
                if (!it.isShowing) {
                    it.show()
                }
            }
        }
        blockingOperationCount += 1
    }

    fun hideBlockingPane() {
        blockingOperationCount -= 1
        if (blockingOperationCount == 0) {
            blockingPane?.dismiss()
            blockingPane = null
        }
    }

    fun provideFirebaseToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result
                println("Token : " + token)
            })

    }

    fun evaluateAllData(document: Document): ArrayList<Cut> {

        val headers: Elements =
            document.getElementsByClass("collapsible-header truncate")
        val body: Elements = document.getElementsByClass("collapsible-body")

        for ((index, value) in headers.withIndex()) {
            headerList.add(value.ownText())

            val cut = Cut().apply {
                header = value.ownText()
                description = body[index].text()
                linkText = body[index].getElementsByAttribute("href").attr("href")
            }
            cutList.add(cut)
        }

        return cutList
    }

    fun evaluateDetailData(document: Document) {
        val contentTitle: Elements = document.getElementsByClass("icerikMetin")
        val headers: Elements = document.getElementsByClass("icerikMetinRenkliKalin")

        var headerList = mutableListOf<String>()
        var contentList = mutableListOf<String>()

        if (contentTitle.hasText()) {
            for (content in contentTitle[0].textNodes())
                if (content.text().isNotBlank())
                    contentList.add(content.text())
        }


        headers?.let {
            headers?.forEach { header ->
                headerList.add(header.text())
            }

        }
        detailList = headerList.zip(contentList)
        var cutList = ArrayList<Cut>()

        for (x in detailList)
            cutList.add(
                Cut(
                    header = x.first,
                    description = x.second
                )
            )

        startActivity(CutDetailActivity.newIntent(requireContext(), cutList))
    }

}