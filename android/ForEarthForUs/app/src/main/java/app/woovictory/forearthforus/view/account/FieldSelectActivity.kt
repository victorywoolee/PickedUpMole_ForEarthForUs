package app.woovictory.forearthforus.view.account

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import app.woovictory.forearthforus.MainActivity
import app.woovictory.forearthforus.R
import app.woovictory.forearthforus.base.BaseActivity
import app.woovictory.forearthforus.databinding.ActivityFieldSelectBinding
import app.woovictory.forearthforus.vm.account.FieldSelectViewModel
import kotlinx.android.synthetic.main.activity_field_select.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class FieldSelectActivity : BaseActivity<ActivityFieldSelectBinding, FieldSelectViewModel>(), View.OnClickListener {

    private fun checkSelected(imageView: ImageView, textView: TextView, number: Int) {
        imageView.isSelected = !imageView.isSelected

        if (imageView.isSelected) {
            preferList.add(number)
            textView.textColor = ContextCompat.getColor(applicationContext, R.color.fe_fu_main)
        } else {
            preferList.remove(number)
            textView.textColor = ContextCompat.getColor(this, R.color.fe_fu_body)
        }

        fieldSelectButton.isSelected = fieldAirPollution.isSelected || fieldWaterPollution.isSelected ||
                fieldGroundPollution.isSelected || fieldGlobalWarming.isSelected ||
                fieldSavingEnergy.isSelected || fieldDesert.isSelected || fieldAcidRain.isSelected
                || fieldWeatherChange.isSelected || fieldEndangeredSpecies.isSelected
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_field_select
    override val viewModel: FieldSelectViewModel by viewModel()
    private var preferList: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setButtonClickListener()
        initStartView()
        initDataBinding()
    }

    private fun setButtonClickListener() {
        viewDataBinding.apply {
            val it = this@FieldSelectActivity
            fieldAirPollution.setOnClickListener(it)
            fieldWaterPollution.setOnClickListener(it)
            fieldGroundPollution.setOnClickListener(it)
            fieldGlobalWarming.setOnClickListener(it)
            fieldSavingEnergy.setOnClickListener(it)
            fieldDesert.setOnClickListener(it)
            fieldAcidRain.setOnClickListener(it)
            fieldWeatherChange.setOnClickListener(it)
            fieldEndangeredSpecies.setOnClickListener(it)
            fieldSelectButton.setOnClickListener(it)
        }
    }

    override fun initStartView() {
        viewDataBinding.apply {
            vm = viewModel
            lifecycleOwner = this@FieldSelectActivity
        }
    }

    override fun initDataBinding() {
        viewModel.clickToSelectPreference.observe(this, Observer {
            if (fieldSelectButton.isSelected && preferList.size != 0) {
                viewModel.selectUserPreference(preferList)
            } else {
                toast("관심사를 선택해주세요.")
            }
        })

        viewModel.preferenceResponse.observe(this, Observer { flag ->
            if (flag) {
                startActivity<MainActivity>()
                finish()
            } else {
                toast("이미 선택하신 관심사입니다.")
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fieldAirPollution -> checkSelected(fieldAirPollution, fieldAirPollutionText, 1)
            R.id.fieldWaterPollution -> checkSelected(fieldWaterPollution, fieldWaterPollutionText, 2)
            R.id.fieldGroundPollution -> checkSelected(fieldGroundPollution, fieldGroundPollutionText, 3)
            R.id.fieldGlobalWarming -> checkSelected(fieldGlobalWarming, fieldGlobalWarmingText, 4)
            R.id.fieldSavingEnergy -> checkSelected(fieldSavingEnergy, fieldSavingEnergyText, 5)
            R.id.fieldDesert -> checkSelected(fieldDesert, fieldDesertText, 6)
            R.id.fieldAcidRain -> checkSelected(fieldAcidRain, fieldAcidRainText, 7)
            R.id.fieldWeatherChange -> checkSelected(fieldWeatherChange, fieldWeatherChangeText, 8)
            R.id.fieldEndangeredSpecies -> checkSelected(fieldEndangeredSpecies, fieldEndangeredSpeciesText, 9)
        }
    }
}
