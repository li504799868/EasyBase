package com.lzp.easybase.demo.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lzp.easybase.demo.R

class ListFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_fragment)

        supportFragmentManager.beginTransaction().add(R.id.container, ListFragment()).commit()
    }
}
