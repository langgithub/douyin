package lang.com.douyin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ss.android.common.applog.GlobalContext;
import com.ss.android.common.applog.UserInfo;

import cn.wjdiankong.hookpms.ServiceManagerWraper;
import cn.wjdiankong.levideo.douyin.data.DouyinVideoListData;
import cn.wjdiankong.levideo.douyin.utils.DouyinUtils;
import cn.wjdiankong.levideo.hotsoon.data.HotsoonVideoListData;
import cn.wjdiankong.levideo.hotsoon.utils.HotsoonUtils;
import cn.wjdiankong.levideo.net.HttpClientUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //自爆签名
        ServiceManagerWraper.hookPMS(this.getApplicationContext());
        //抖音验证
        GlobalContext.setContext(getApplicationContext());

        try{
            System.loadLibrary("userinfo");//抖音&火山
        }catch(Exception e){
        }
        //抖音初始化操作
        UserInfo.setAppId(2);
        UserInfo.initUser("a3668f0afac72ca3f6c1697d29e0e1bb1fef4ab0285319b95ac39fa42c38d05f");
        String url="https://api.amemv.com/aweme/v1/nearby/feed/?max_cursor=0&count=20&feed_style=1&retry_type=no_retry&iid=70852738750&device_id=50245818019&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=165&version_name=1.6.5&device_platform=android&ssmix=a&device_type=MX4&device_brand=Meizu&language=zh&os_api=22&os_version=5.1&uuid=865479022819932&openudid=26332057d592614a&manifest_version_code=165&resolution=1152*1920&dpi=480&update_version_code=1652&_rticket=1556420746688&ts=1556420746";
        String[] param={"os_api", "22", "device_type", "MX4", "device_platform", "android", "ssmix","a", "iid", "70852738750", "manifest_version_code","165","dpi","480","uuid","865479022819932","version_code","165","app_name","aweme","version_name","1.6.5","openudid","26332057d592614a","device_id","50245818019","resolution","1152*1920","os_version","5.1","language","zh","device_brand", "Meizu", "ac", "wifi", "update_version_code", "1652", "aid", "1128", "channel", "wandoujia"};
        String as_cp=UserInfo.getUserInfo(1556420746,url,param);
        Log.d("douyin",as_cp);


    }

    public void craw(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDouyinListData();
            }
        }).start();

    }

    public void getDouyinListData(){
        String url = DouyinUtils.getEncryptUrl(this, 0, 0);
        HttpClientUtils httpClient = new HttpClientUtils();
        httpClient.setUrl(url).setResponseCallback(new HttpClientUtils.ResponseCallback() {
            @Override
            public void responseFinish(final String result) {
                DouyinVideoListData listData = DouyinVideoListData.fromJSONData(result);
                Log.i("jw", "list data:"+listData);
            }

            @Override
            public void reponseFail() {
            }

            @Override
            public void responseFinish(byte[] result) {

            }
        }).execGet();
    }
}
