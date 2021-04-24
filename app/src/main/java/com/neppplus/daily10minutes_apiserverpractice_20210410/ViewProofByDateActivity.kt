package com.neppplus.daily10minutes_apiserverpractice_20210410

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import com.neppplus.daily10minutes_apiserverpractice_20210410.adapters.ProofAdapter
import com.neppplus.daily10minutes_apiserverpractice_20210410.datas.Project
import com.neppplus.daily10minutes_apiserverpractice_20210410.datas.Proof
import com.neppplus.daily10minutes_apiserverpractice_20210410.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_proof_by_date.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewProofByDateActivity : BaseActivity() {

    lateinit var mProject : Project

    val mSelectedDate = Calendar.getInstance()

    val mProofList = ArrayList<Proof>()

    lateinit var mProofAdapter : ProofAdapter

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

//                    선택일자를 멤버변수에 저장.

                    mSelectedDate.set(year, month, dayOfMonth)

//                    SimpleDateFormat 이용, 날짜 -> String 양식 가공.
                    val simpleDateFormat = SimpleDateFormat("yyyy년 M월 d일")
                    dateTxt.text = simpleDateFormat.format(mSelectedDate.time)

//                    서버에서, 선택된 날짜에 해당하는 글 불러오기.
                    getProofListByDate()

                }

            }
//            실제 달력 띄우기 (AlertDialLog와 유사)

            val datePickerDialog = DatePickerDialog(mContext, dateSetListener,
                mSelectedDate.get(Calendar.YEAR),
                mSelectedDate.get(Calendar.MONTH),
                mSelectedDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("project") as Project

        mProofAdapter = ProofAdapter(mContext, R.layout.proof_list_item, mProofList)
        proofListView.adapter = mProofAdapter
    }

//    서버에서, 선택된 날짜의 글을 받아와주는 함수.

    fun getProofListByDate() {

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateStr = sdf.format(mSelectedDate.time)

        ServerUtil.getRequestProjectProofListByDate(mContext, mProject.id, dateStr, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val projectObj = dataObj.getJSONObject("project")
                val proofsArr = projectObj.getJSONArray("proofs")

//                기존의 담겨있던 게시글은 전부 삭제 하고 나서 -> 새로 받아온 글들을 추가해주자.
                mProofList.clear()


                for (i in 0 until proofsArr.length()) {

//                    인증글 JSON -> Proof 객체로 변환 -> mProofList에 추가.

                    mProofList.add(Proof.getProofFromJson(proofsArr.getJSONObject(i)))
                }

//                나중에 게시글 목록을 추가로 불러옴 -> 리스트뷰 내용 변경 -> 어댑터 새로고침
                runOnUiThread {
                    mProofAdapter.notifyDataSetChanged()

                }

            }

        })

    }


}