package com.neppplus.daily10minutes_apiserverpractice_20210410.datas

import org.json.JSONObject
import java.io.Serializable

class Project(
    var id : Int,
    var title : String,
    var imageUrl : String,
    var description : String,
    var onGoingUserCount : Int,
    var proofMethod : String,
    var myLastStatus : String?) : Serializable {

//    태그 목록을 저장하기위한 ArrayList
    val tags = ArrayList<String>()


//    보조 생성자 추가. => Project() 만으로도 만들 수 있게.
    constructor() : this(0, "", "", "", 0, "", null)

    companion object {
        fun getProjectFromJson(jsonObj : JSONObject) : Project {
            val project = Project()

            project.id = jsonObj.getInt("id")
            project.title = jsonObj.getString("title")
            project.imageUrl = jsonObj.getString("img_url")
            project.description = jsonObj.getString("description")

            project.onGoingUserCount = jsonObj.getInt("ongoing_users_count")
            project.proofMethod = jsonObj.getString("proof_method")

//            내 최종 도전 상태. => null일 가능성도 있다.
//            null인 데이터를 파싱하려고 하면 에러처리, 파싱 중단.
//            null인지 아닌지 확인 -> null이 아닐때만 파싱
            if (!jsonObj.isNull("my_last_status")) {
                project.myLastStatus = jsonObj.getString("my_last_status")
            }


            val tagsArr = jsonObj.getJSONArray("tags")

            for (index in 0 until tagsArr.length()) {
                val tagObj = tagsArr.getJSONObject(index)
                val title = tagObj.getString("title")
                project.tags.add(title)
            }

            return project
        }
    }
}