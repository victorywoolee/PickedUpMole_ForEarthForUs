package app.woovictory.forearthforus.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.woovictory.forearthforus.base.BaseViewModel
import app.woovictory.forearthforus.data.repository.EarthRepository
import app.woovictory.forearthforus.data.repository.feed.MissionFeedRepository
import app.woovictory.forearthforus.model.earth.EarthResponse
import app.woovictory.forearthforus.model.mission.MissionFeedResponse
import app.woovictory.forearthforus.util.SharedPreferenceManager
import app.woovictory.forearthforus.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by VictoryWoo
 */
class MainViewModel(
    private val earthRepository: EarthRepository, private val missionFeedRepository
    : MissionFeedRepository
) : BaseViewModel() {

    private val _earthResponse = MutableLiveData<EarthResponse>()
    val earthResponse: LiveData<EarthResponse>
        get() = _earthResponse

    // mutable 하게 만들고, immutable 하게 노출시킨다.

    private val _clickToEarthDetail = SingleLiveEvent<Any>()
    val clickToEarthDetail: LiveData<Any>
        get() = _clickToEarthDetail

    private val _missionFeedResponse = MutableLiveData<ArrayList<MissionFeedResponse>>()
    val missionFeedResponse: LiveData<ArrayList<MissionFeedResponse>>
        get() = _missionFeedResponse

    fun clickToDetail() {
        _clickToEarthDetail.call()
    }

    // 상단 지구 정보.
    fun getEarthInformation() {
        addDisposable(
            earthRepository
                .getEarthInformation(SharedPreferenceManager.token, SharedPreferenceManager.earthLevel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _earthResponse.value = it

                    Log.v("11882 ${it.earthLevel}", it.earthLevel.toString())
                    Log.v("11882", it.content)
                    Log.v("11882", it.image)
                }, {
                    Log.v("11882", it.message)
                })
        )
    }

    // 아래에 진행 중인 미션.
    fun getMissionFeed() {
        addDisposable(
            missionFeedRepository
                .getUserMissionFeed(SharedPreferenceManager.token, "progress")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _missionFeedResponse.value = it

                            Log.v("199427 success", it.size.toString())
                        }
                    }

                }, { e ->
                    Log.v("199427 fail", e.message)
                })
        )
    }
}