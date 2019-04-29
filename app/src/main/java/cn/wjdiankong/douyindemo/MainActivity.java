//package cn.wjdiankong.douyindemo;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import cn.wjdiankong.levideo.douyin.data.DouyinVideoListData;
//import cn.wjdiankong.levideo.douyin.utils.DouyinUtils;
//import cn.wjdiankong.levideo.hotsoon.data.HotsoonVideoListData;
//import cn.wjdiankong.levideo.hotsoon.utils.HotsoonUtils;
//import cn.wjdiankong.levideo.net.HttpClientUtils;
//import cn.wjdiankong.levideo.net.HttpClientUtils.ResponseCallback;
//
//public class MainActivity extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		getDouyinListData();
//
//		getHuoshanListData();
//	}
//
//	public void getDouyinListData(){
//		String url = DouyinUtils.getEncryptUrl(this, 0, 0);
//		HttpClientUtils httpClient = new HttpClientUtils();
//		httpClient.setUrl(url).setResponseCallback(new ResponseCallback() {
//			@Override
//			public void responseFinish(final String result) {
//				DouyinVideoListData listData = DouyinVideoListData.fromJSONData(result);
//				Log.i("jw", "list data:"+listData);
//			}
//
//			@Override
//			public void reponseFail() {
//			}
//
//			@Override
//			public void responseFinish(byte[] result) {
//
//			}
//		}).execGet();
//	}
//
//	public void getHuoshanListData(){
//		String url = HotsoonUtils.getEncryptUrl(this, 0, -1);
//		HttpClientUtils httpClient = new HttpClientUtils();
//		httpClient.setUrl(url).setResponseCallback(new ResponseCallback() {
//			@Override
//			public void responseFinish(final String result) {
//				HotsoonVideoListData listData = HotsoonVideoListData.fromJSONData(result);
//				Log.i("jw", "list data:"+listData);
//			}
//
//			@Override
//			public void reponseFail() {
//			}
//
//			@Override
//			public void responseFinish(byte[] result) {
//
//			}
//		}).execGet();
//	}
//
//}
