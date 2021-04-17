package com.neppplus.daily10minutes_apiserverpractice_20210410

import android.os.Bundle
import com.neppplus.daily10minutes_apiserverpractice_20210410.adapters.ProjectAdapter
import com.neppplus.daily10minutes_apiserverpractice_20210410.datas.Project
import com.neppplus.daily10minutes_apiserverpractice_20210410.utils.ServerUtil
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

    }

    override fun setValues() {

        getProjectListFromServer()

        mProjectAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjects)
        projectListView.adapter = mProjectAdapter

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