package com.wu.network

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.wu.net.api.BaseInfo
import com.wu.net.convert.ResultObservableTransformer
import com.wu.net.listener.DataListener
import com.wu.net.listener.OnFailListener
import com.wu.net.listener.OnSuccessListener
import com.wu.net.model.NetInitInfo
import com.wu.net.observable.SpecialCodeObservable
import com.wu.network.api.NetApi
import com.wu.network.api.UserInfo
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(), Observer {

    var dis: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpecialCodeObservable.getInstance().addObserver(this)
        setContentView(R.layout.activity_main)


        var hashMap = HashMap<String, String>()
        var info = object : NetInitInfo {
            override fun getAndroidVersionCode(): String {
                return "1.0.0"
            }

            override fun getAndroidVersionName(): String {
                return "1.0.0"
            }

            override fun getAndroidPageName(): String {
                return "1.0.0"
            }

            override fun getSpecialCode(): String {
                TODO("Not yet implemented")
            }

            override fun isDebug(): Boolean {
                return true
            }

        }

        findViewById<Button>(R.id.bt_get).setOnClickListener {
            //           getData()
            getData2()
        }
    }

    private fun getData2() {
        dis = NetApi
            .getInstance()
            .getMyService()
            .userInfo
            .compose(ResultObservableTransformer<BaseInfo<UserInfo>>(MainActivity@ this))
            .subscribe(object : OnSuccessListener<BaseInfo<UserInfo>>() {
            override fun onSuccess(t: BaseInfo<UserInfo>?) {
                Log.e("结果:", t!!.getMessage())
            }

        }, object : OnFailListener<Throwable>() {
            override fun onFail(code: Int, message: String?) {
                Log.e("结果:", message!!)
            }

        })


    }

    private fun getData() {

        NetApi.getInstance().getMyService().userInfo.compose(
            ResultObservableTransformer<BaseInfo<UserInfo>>(
                MainActivity@ this
            )
        ).subscribe(object : DataListener<BaseInfo<UserInfo>>() {
            override fun onSuccess(t: BaseInfo<UserInfo>?) {
                Log.e("", "")
            }

            override fun onFail(code: Int, message: String?) {
                Log.e("", "")
            }
        })
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is SpecialCodeObservable) {
            var code = arg as Int
            if (code == 601) {

            } else if (code == 605) {

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dis != null) dis!!.dispose()
    }
}