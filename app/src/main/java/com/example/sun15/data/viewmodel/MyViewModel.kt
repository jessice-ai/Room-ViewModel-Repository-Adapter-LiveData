package com.example.sun15.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sun15.data.repository.SunRepository
import com.example.sun15.data.room.SunUser
import com.example.sun15.data.room.SunUserDataBase


class MyViewModel(application: Application)  : AndroidViewModel(application) {
    var xun:Int = 1
    var mAllWords: LiveData<List<SunUser>>? = null
    var SunRepository:SunRepository? = null
    /**
     * 延迟初始化，一个 Repository 管理数据库操作
     * Repository 是 ViewModel 与 Room 之间的，中间层
     */
//    fun MyViewModel(application: Application){
//        SunRepository = SunRepository()
//    }
    /**
     * 添加数据
     */
    fun insert(SunUser: SunUser) {
        SunRepository?.insertData(SunUser)
        getAllWords()
    }
//
    /**
     * 更新数据
     */
    fun update_first_name(id:Int, value:String){
        SunRepository?.update_first_name(id,value)
    }

    /**
     * 侦听这个函数返回值，只要这个值发生变化，只需要调用 adapter 更新UI就可以
     * List<SunUser> 这个是没有添加 LiveData 的时候
     * LiveData<List<SunUser>> 这里的数据类型加上LiveData ，否则，Activity中你使用的 ViewModel 中的侦听器 observe 找不到对象
     * 这里的类型改了，一下地方类型，也要改成一样的
     * 1、ViewModel 中 getAllWords 函数类型
     * 2、ViewModel 中 公共变量 mAllWords 类型
     * 2、Repository 中 getAlldata 函数类型
     * 3、Dao 中 getAll 函数类型
     * 4、adapter 中的数据类型也要修改
     */
    fun getAllWords(): LiveData<List<SunUser>>? {
        mAllWords = SunRepository?.getAlldata()
        return mAllWords;
    }
    /**
     * 删除数据
     */
    fun delete(id:Int){
        SunRepository?.delete(id)
    }
    //var inputAge = MutableLiveData<Int>()
    /**
     * 初始化模块
     */
    init {
        /**
         * 构造函数（constructor） 初始化代码块（init） 伴生对象（companion object），执行顺序
         *  1、首先执行伴生对象（Companion object）
         *  2、其次执行初始化代码块 （init）
         *  3、再执行构造函数 （constructor）
         */
        val dao = SunUserDataBase.getInstance(application)?.getUserDao()
        SunRepository = dao?.let { SunRepository(it) }
    }
}