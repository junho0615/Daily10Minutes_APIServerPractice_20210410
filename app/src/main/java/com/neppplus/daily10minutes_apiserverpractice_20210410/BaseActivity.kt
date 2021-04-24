package com.neppplus.daily10minutes_apiserverpractice_20210410

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()

//    액션바에 등록한 UI요소들은 -> BaseActivity를 통해 각 화면에 분배됨.
//    BaseActivity의 멤버변수로 만들어두어야 => 각각의 화면에서 물려받아서 사용 가능.

    lateinit var backImg : ImageView

//    onCreate -> 일부 내용 변경해보자. -> 상속받은 함수 내용 변경 : override

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        BaseActivity를 상속받는 클래스들은 => 화면이 onCreate 될 때 => 액션바 세팅도 되도록.
//        무조건 세팅하는게 아니라, supportActionBar가 null 아닐때 (실체가 있을 때)만 실행되도록.

        supportActionBar?.let {
//            기본 액션바가 존재할때만 let { } 내용이 실행됨. (코틀린 문법)
            setCustomActionBar()
        }

    }


//    커스텀 액션바를 적용해주는 기능 (함수)

    fun setCustomActionBar() {
//        1. 액션바가 어떤 모양으로 보이게 하고싶은지?
//          => 모양 : Layout - xml 작성

//        2. 커스텀 액션바 적용

        val defaultActionBar = supportActionBar!!
//        기본액션바의 모드를 => 커스텀 지원 모드로 변경

        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

//        실제로 보여줄 커스텀 화면 지정. (my_custom_action_bar 지정)
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

//        좌우 여백이 남아서 제거해야함.
//        액션바를 잡고있는 (부모역할인) => Toolbar의 여백 제거. (내부 공간을 0으로 설정)


//        Toolbar 선택 : androidx가 주는걸로 선택.
        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)

//        코틀린에서도 UI요소를 사용할 수 있도록 추가 세팅.
        backImg = defaultActionBar.customView.findViewById(R.id.backImg)

        backImg.setOnClickListener {
            finish()
        }
    }

}