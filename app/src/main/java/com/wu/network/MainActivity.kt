package com.wu.network

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.wu.net.api.BaseInfo
import com.wu.net.convert.ResultObservableTransformer
import com.wu.net.listener.DataListener
import com.wu.net.model.NetInitInfo
import com.wu.net.observable.SpecialCodeObservable
import com.wu.network.api.NetApi
import com.wu.network.api.TestApi
import com.wu.network.api.UserInfo
import java.util.*


class MainActivity : AppCompatActivity(),Observer {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpecialCodeObservable.getInstance().addObserver(this)
        setContentView(R.layout.activity_main)
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
            NetApi.getInstance().getService(TestApi::class.java).userInfo.compose(
                ResultObservableTransformer<BaseInfo<UserInfo>>(MainActivity@ this)
            ).subscribe(object : DataListener<BaseInfo<UserInfo>>() {
                override fun onSuccess(t: BaseInfo<UserInfo>?) {
                    Log.e("", "")
                }

                override fun onFail(code: Int, message: String?) {
                    Log.e("", "")
                }
            })
        }
    }

    override fun update(o: Observable?, arg: Any?) {
       if( o is SpecialCodeObservable){
           var code=arg as Int
           if (code == 601) {

           } else if (code == 605) {

           }

       }
    }
}