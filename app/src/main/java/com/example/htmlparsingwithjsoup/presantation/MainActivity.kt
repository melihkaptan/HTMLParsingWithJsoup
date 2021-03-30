package com.example.htmlparsingwithjsoup.presantation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.htmlparsingwithjsoup.R
import com.example.htmlparsingwithjsoup.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val adapter = ViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ViewPager için adapter nesnesi oluşturuyoruz ve kullanacağımız fragment, title'ları ekliyoruz.

        adapter.addFragment(ElectricFragment(), getString(R.string.fragment_electric_name))
        adapter.addFragment(WaterFragment(), getString(R.string.fragment_water_name))

        //Adapter'ımızdaki verileri viewPager adapter'a veriyoruz
        viewPager.adapter = adapter
        //Tablar arasında yani viewPager'lar arasında geçisi sağlıyoruz
        tabs.setupWithViewPager(viewPager)

        fabSearch.setOnClickListener {
            println("search clicked")
        }
    }

}

