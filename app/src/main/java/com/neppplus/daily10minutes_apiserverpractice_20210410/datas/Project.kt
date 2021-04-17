package com.neppplus.daily10minutes_apiserverpractice_20210410.datas

import org.json.JSONObject

class Project(
    var id : Int,
    var title : String,
    var imageUrl : String,
    var description : String) {

//    보조 생성자 추가. => Project() 만으로도 만들 수 있게.
    constructor() : this(0, "", "", "")

    companion object {
        fun getProjectFromJson(jsonObj : JSONObject) : Project {
            val project = Project()

            project.id = jsonObj.getInt("id")
            project.title = jsonObj.getString("title")
            project.imageUrl = jsonObj.getString("imageUrl")
            project.description = jsonObj.getString("description")

            return project
        }
    }
}