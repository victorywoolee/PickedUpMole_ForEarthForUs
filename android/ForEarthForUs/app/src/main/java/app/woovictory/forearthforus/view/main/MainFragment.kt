package app.woovictory.forearthforus.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.woovictory.forearthforus.MainActivity
import app.woovictory.forearthforus.R
import app.woovictory.forearthforus.databinding.FragmentMainBinding
import app.woovictory.forearthforus.util.SharedPreferenceManager
import app.woovictory.forearthforus.util.earthLevelList
import app.woovictory.forearthforus.util.loadDrawableImage
import app.woovictory.forearthforus.view.main.detail.EarthDetailActivity
import app.woovictory.forearthforus.view.mission.MissionDetailActivity
import app.woovictory.forearthforus.vm.main.MainViewModel
import org.jetbrains.anko.support.v4.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by VictoryWoo
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private var mainMissionAdapter: MainMissionAdapter? = null
    private val mainViewModel: MainViewModel by viewModel()

    //get() = ViewModelProviders.of(this@MainFragment).get(MainViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return fragmentMainBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        setUpViewModel()
        setUpDataBinding()
    }

    override fun onResume() {
        super.onResume()

        mainViewModel.getInformation()
        mainViewModel.getUserInformation()

        /*   if (SharedPreferenceManager.missionCompleteCount >= 0) {
               Log.v("99201", "reload 시점!!")
               mainViewModel.getInformation()
               mainViewModel.getUserInformation()
           } else {
               Log.v("99201", "reload 안함!!")
               Log.v("99201 count", SharedPreferenceManager.missionCompleteCount.toString())
               toast("변경 사항이 없음.")
           }*/
    }

    private fun init() {
        mainMissionAdapter = MainMissionAdapter {
            startToDetailActivity(it)
        }
        fragmentMainBinding.itemMainEarthUserName.text = SharedPreferenceManager.userName
    }

    private fun setUpViewModel() {
        fragmentMainBinding.apply {
            vm = mainViewModel
            lifecycleOwner = this@MainFragment
        }
    }

    private fun setUpDataBinding() {
        mainViewModel.getInformation()

        mainViewModel.clickToEarthDetail.observe(this, Observer {
            startActivity<EarthDetailActivity>()
        })

        mainViewModel.earthResponse.observe(this, Observer {
            Log.v("40032", it.earthLevel.toString())
            Log.v("40032 ee", it.toString())
            setEarthLevel(it.earthLevel)
            //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxMiwidXNlcm5hbWUiOiJsc3dAbHN3LmNvbSIsImV4cCI6MTU2MjIzODQ1OSwiZW1haWwiOiJsc3dAbHN3LmNvbSJ9.8XHuyEPqeuhlAJ-U-ZrmyfxwAHqTHzC4pIwFrLADTLg
        })

        mainViewModel.missionFeedResponse.observe(this, Observer {
            Log.v("22883", it.size.toString())
            if (it.size > 0) {
                fragmentMainBinding.apply {
                    mainRv.visibility = View.VISIBLE
                    mainNullIconImage.visibility = View.GONE
                    mainMissionAdapter?.addItems(it)
                    setUpRecyclerView()
                }
            } else {
                fragmentMainBinding.apply {

                    mainNullIconImage.visibility = View.VISIBLE
                    mainNullIconImage.let { imageView ->
                        imageView.visibility = View.VISIBLE
                        imageView.setOnClickListener {
                            val a = activity as MainActivity
                            a.replaceFragment()
                        }
                    }
                    mainRv.visibility = View.GONE
                }
            }
        })

        mainViewModel.isLoading.observe(this, Observer { loading ->
            if (loading) {
                fragmentMainBinding.loading.visibility = View.VISIBLE
            } else {
                fragmentMainBinding.loading.visibility = View.GONE
            }
        })

        mainViewModel.earthUserResponse.observe(this, Observer {
            if (SharedPreferenceManager.earthLevel != it.earthLevel) {
                SharedPreferenceManager.earthLevel = it.earthLevel
                mainViewModel.getInformation()
            }
        })
    }

    private fun setUpRecyclerView() {
        fragmentMainBinding.mainRv.apply {
            layoutManager = LinearLayoutManager(context.applicationContext)
            adapter = mainMissionAdapter
            setHasFixedSize(true)
        }
    }

    private fun setEarthLevel(earthLevel: Int) {
        loadDrawableImage(fragmentMainBinding.mainBarGraph, earthLevelList[earthLevel - 1])
    }

    private fun startToDetailActivity(id: Int) {
        val intent = Intent(context, MissionDetailActivity::class.java)
        intent.putExtra("categoryId", id)
        intent.putExtra("url", "main")
        startActivity(intent)
    }
}