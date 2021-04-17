package com.neppplus.daily10minutes_apiserverpractice_20210410.datas

import org.json.JSONObject
import java.io.Serializable

class Project(
    var id : Int,
    var title : String,
    var imageUrl : String,
    var description : String,
    var onGoingUserCount : Int,
    var proofMethod : String) : Serializable {

//    보조 생성자 추가. => Project() 만으로도 만들 수 있게.
    constructor() : this(0, "", "", "", 0, "")

    companion object {
        fun getProjectFromJson(jsonObj : JSONObject) : Project {
            val project = Project()

            project.id = jsonObj.getInt("id")
            project.title = jsonObj.getString("title")
            project.imageUrl = jsonObj.getString("img_url")
            project.description = jsonObj.getString("description")

            project.onGoingUserCount = jsonObj.getInt("ongoing_users_count")
            project.proofMethod = jsonObj.getString("proof_method")

            return project
        }
    }
}