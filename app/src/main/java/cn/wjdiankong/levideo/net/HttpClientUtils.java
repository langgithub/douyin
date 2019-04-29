package cn.wjdiankong.levideo.net;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClientUtils {

	public interface ResponseCallback{
		void responseFinish(String result);
		void responseFinish(byte[] result);
		void reponseFail();
	}

	private final OkHttpClient client = new OkHttpClient();

	private String url;
	private ResponseCallback mCallback = null;
	private Map<String,String> paramsMap = null;
	private boolean isResponseByte = false;
	private Headers mHeader = null;

	public HttpClientUtils setUrl(String url){
		this.url = url;
		return this;
	}
	
	public HttpClientUtils setHeaderParams(Map<String, String> params){
		Builder builder = new Builder();
		for(String key : params.keySet()){
			builder.add(key, params.get(key));
		}
		mHeader = builder.build();
		return this;
	}

	public HttpClientUtils setResponseCallback(ResponseCallback callBack){
		this.mCallback = callBack;
		return this;
	}
	
	public HttpClientUtils setResponseByte(boolean isResponseByte){
		this.isResponseByte = isResponseByte;
		return this;
	}
	
	public HttpClientUtils setParams(Map<String, String> map){
		this.paramsMap = map;
		return this;
	}

	public void execGet(){
		Request.Builder reqBuilder = new Request.Builder();
		if(mHeader != null){
			reqBuilder.headers(mHeader);
		}
		Request request = reqBuilder.url(url).build();
		try {
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					if(mCallback != null){
						mCallback.reponseFail();
					}
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if(mCallback != null){
						if(isResponseByte){
							mCallback.responseFinish(response.body().bytes());
						}else{
							mCallback.responseFinish(response.body().string());
						}
					}
				}
			});
		} catch (Exception e) {
			if(mCallback != null){
				mCallback.reponseFail();
			}
		}
	}
	
	public void execPost(){
		if(paramsMap == null || paramsMap.size() == 0){
			if(mCallback != null){
				mCallback.reponseFail();
			}
		}else{
			FormBody.Builder builder = new FormBody.Builder();
			for(String key : paramsMap.keySet()){
				builder = builder.add(key, paramsMap.get(key));
			}
			RequestBody formBody = builder.build();
			Request.Builder reqBuilder = new Request.Builder();
			if(mHeader != null){
				reqBuilder.headers(mHeader);
			}
			Request request = reqBuilder.url(url).post(formBody).build();
			try {
				client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					if(mCallback != null){
						mCallback.reponseFail();
					}
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if(isResponseByte){
						mCallback.responseFinish(response.body().bytes());
					}else{
						mCallback.responseFinish(response.body().string());
					}
				}
			});
			} catch (Exception e) {
				if(mCallback != null){
					mCallback.reponseFail();
				}
			}
		}
	}

}
