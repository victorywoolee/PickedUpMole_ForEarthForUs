package app.woovictory.forearthforus.vm

import android.util.Log
import androidx.lifecycle.LiveData
import app.woovictory.forearthforus.base.BaseViewModel
import app.woovictory.forearthforus.data.repository.LoginRepository
import app.woovictory.forearthforus.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by VictoryWoo
 */
class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
    private val _clickToLogin = SingleLiveEvent<Any>()
    val clickToLogin: LiveData<Any>
        get() = _clickToLogin

    private val _clickToSignActivity = SingleLiveEvent<Any>()
    val clickToSignActivity: LiveData<Any>
        get() = _clickToSignActivity

    private val _clickToGoogleLogin = SingleLiveEvent<Any>()
    val clickToGoogleLogin: LiveData<Any>
        get() = _clickToGoogleLogin

    fun clickToLogin() {
        _clickToLogin.call()
    }

    fun clickToSignActivity() {
        _clickToSignActivity.call()
    }

    fun clickToGoogleLogin() {
        _clickToGoogleLogin.call()
    }

    fun postLogin(email: String, password: String) {
        addDisposable(
            loginRepository.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.v("success login 9871", it.token)
                }, {
                    Log.v("fail login 9871", it.message)
                })
        )
    }
}