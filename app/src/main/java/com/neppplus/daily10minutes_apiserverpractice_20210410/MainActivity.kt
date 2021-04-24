package com.neppplus.daily10minutes_apiserverpractice_20210410

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.neppplus.daily10minutes_apiserverpractice_20210410.adapters.ProjectAdapter
import com.neppplus.daily10minutes_apiserverpractice_20210410.datas.Project
import com.neppplus.daily10minutes_apiserverpractice_20210410.utils.ContextUtil
import com.neppplus.daily10minutes_apiserverpractice_20210410.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjects = ArrayList<Project>()
    lateinit var mProjectAdapter : ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        projectListView.setOnItemClickListener { parent, view, position, id ->
            val clickedProject = mProjects[position]

            val myIntent = Intent(mContext, ViewProjectDetailActivity::class.java)
            myIntent.putExtra("projectInfo", clickedProject)
            startActivity(myIntent)
        }

        logoutBtn.setOnClickListener {
               val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", {dialog, which ->
//                 로그인 : 아이디/비번 서버전달 => 회원이 맞는지 검사. => 성공시 토큰값 전달. => 앱에서 토큰을 저장.
//                로그아웃 : 기기에 저장된 토큰 값 삭제.

                ContextUtil.setLoginToken(mContext, "")

                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)

                finish()
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }
    }

    override fun setValues() {

        getProjectListFromServer()

        mProjectAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjects)
        projectListView.adapter = mProjectAdapter

//        BaseActivity가 물려주는 backImg를 메인화면에서만, 숨김처리.
        backImg.visibility = View.GONE

    }

    fun getProjectListFromServer() {
        ServerUtil.getRequestProjectList(mContext, object  : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val projectsArr = dataObj.getJSONArray("projects")

                for (i in 0 until projectsArr.length()) {
                    val projectObj = projectsArr.getJSONObject(i)



                    val project = Project.getProjectFromJson(projectObj)

                    mProjects.add(project)
                }
                runOnUiThread {
                    mProjectAdapter.notifyDataSetChanged()
                }
            }
        })
    }

}