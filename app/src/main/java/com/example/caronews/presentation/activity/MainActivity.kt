package com.example.caronews.presentation.activity

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caronews.MyApplication
import com.example.caronews.databinding.ActivityMainBinding
import com.example.caronews.model.NewsListResponse
import com.example.caronews.presentation.adapter.NewsListAdapter
import com.example.caronews.repository.ResponseState
import com.example.caronews.viewmodel.MainViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModeFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var newsListAdapter: NewsListAdapter
    private  var returnedNewsList: List<NewsListResponse> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarColor()
        (applicationContext as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setActionBarProperties()
        mainViewModel = ViewModelProvider(this, viewModeFactory)[MainViewModel::class.java]
        dataLogic()
        setObserver()
        setAdapter()
    }

    private fun dataLogic() {
        if (isNetworkAvailable())
            getData()
        else
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun setStatusBarColor() {
        val window = this.window;
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.statusBarColor = getColor(android.R.color.holo_red_light)
    }

    private fun setAdapter() {
        newsListAdapter = NewsListAdapter(this)
        binding.newsRecycleList.layoutManager = LinearLayoutManager(this)
        binding.newsRecycleList.adapter = newsListAdapter
    }

    private fun setObserver() {
        mainViewModel.responseData.observe(this) { responseModel->
            when (responseModel) {
                is ResponseState.FailData -> showFailUi()
                is ResponseState.SuccessData -> showSuccessUi(responseModel.listOfNews)
            }
        }
    }

    private fun showSuccessUi(listOfNews: List<NewsListResponse>) {
        returnedNewsList = listOfNews
        sortRecentList()
        updateList()

    }

    private fun updateList() {
        newsListAdapter.updateList(returnedNewsList)
        newsListAdapter.notifyDataSetChanged()
    }

    private fun sortRecentList()
    {
        Collections.sort(returnedNewsList
        ) {
                o1:NewsListResponse, o2:NewsListResponse ->
            o2.timeCreated!!.compareTo(o1.timeCreated!!)

        }

    }


    private fun sortPopularList()
    {
            Collections.sort(
                returnedNewsList
            ) { o1: NewsListResponse, o2: NewsListResponse ->
               if( o1.rank!!.compareTo(o2.rank!!) == 0)
                   o2.timeCreated!!.compareTo(o1.timeCreated!!)
               else
                o1.rank!!.compareTo(o2.rank!!)
            }
    }

    private fun showFailUi() {
      Toast.makeText(this,"Unable To Get Response",Toast.LENGTH_SHORT).show()
    }

    private fun getData() {
        mainViewModel.getNewsDetail()
    }

    private fun setActionBarProperties() {
        binding.toolbar.title = "Carosell News"
        binding.toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == com.example.caronews.R.id.popular) {
            sortPopularList()
            updateList()
        }
       if (item.itemId == com.example.caronews.R.id.recent)
        {
            sortRecentList()
            updateList()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.caronews.R.menu.menu, menu)
        return true
    }

}