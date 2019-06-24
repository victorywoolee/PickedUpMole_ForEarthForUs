package app.woovictory.forearthforus.vm.mypage

import androidx.lifecycle.LiveData
import app.woovictory.forearthforus.base.BaseViewModel
import app.woovictory.forearthforus.util.SingleLiveEvent

/**
 * Created by VictoryWoo
 */
class AchieveListViewModel : BaseViewModel() {

    private val _clickToBack = SingleLiveEvent<Any>()
    val clickToBack: LiveData<Any>
        get() = _clickToBack

    fun clickToBack() {
        _clickToBack.call()
    }
}