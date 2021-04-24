package com.neppplus.daily10minutes_apiserverpractice_20210410

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_view_proof_by_date.*
import java.util.*
import kotlin.math.log

class ViewProofByDateActivity : BaseActivity() {

    val mSeletedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_proof_by_date)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        selectDateBtn.setOnClickListener {

            val dateSetListener = object : DatePickerDialog.OnDateSetListener {

                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                    Log.d("선택된날짜", "${year}년 ${month}월 ${dayOfMonth}일")
                }

            }
//            실제 달력 띄우기 (AlertDialLog와 유사)

            val datePickerDialog = DatePickerDialog(mContext, dateSetListener,
                mSeletedDate.get(Calendar.YEAR),
                mSeletedDate.get(Calendar.MONTH),
                mSeletedDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

    }

    override fun setValues() {
    }


}