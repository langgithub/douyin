package cn.wjdiankong.levideo.hotsoon.data;

import java.util.logging.Logger;

import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;
import cn.wjdiankong.levideo.data.LevideoData;
import cn.wjdiankong.levideo.net.Utils;

public class HotsoonVideoData extends LevideoData{
	
	public static HotsoonVideoData fromJSONData(String jsonStr){
		HotsoonVideoData data = new HotsoonVideoData();
		if(TextUtils.isEmpty(jsonStr)){
			return data;
		}
		try{
			JSONObject json = new JSONObject(jsonStr);
			//video标签中的信息
			/**
			 * data
			 * 	-video
			 * 		-url_list
			 *  	-download_url
			 *  		-url_list
			 *  	-cover
			 *  		-url_list
			 *  	-width
			 *  	-height
			 *  
			 * 	-author
			 * 		-avatar_jpg
			 * 			-url_list
			 * 		-nickname
			 * 		-city
			 * 		-birthday_description
			 * 
			 * 	-stats
			 * 		-play_count
			 * 
			 * 	-title
			 * 
			 * 	-create_time
			 */
			
			//视频内容信息
			JSONObject dataJson = json.getJSONObject("data");
			JSONObject videoJson = dataJson.getJSONObject("video");
			data.firstVideoPlayUrl = videoJson.getJSONArray("url_list").getString(0);
			data.secondVideoPlayUrl = videoJson.getJSONArray("url_list").getString(1);
			data.thirdVideoPlayUrl = data.secondVideoPlayUrl;
			data.firstVideoDownloadUrl = videoJson.getJSONArray("download_url").getString(0);
			data.secondVideoDownloadUrl = videoJson.getJSONArray("download_url").getString(1);
			data.thirdVideoDownloadUrl = data.secondVideoDownloadUrl;
			JSONObject coverJson = videoJson.getJSONObject("cover");
			data.coverImgUrl = coverJson.getJSONArray("url_list").getString(0);
			data.videoWidth = videoJson.optInt("width");
			data.videoHeight = videoJson.optInt("height");
			data.videoDuration = videoJson.optDouble("duration")+"";
			data.title = dataJson.optString("title");
			data.createTime = dataJson.optLong("create_time")*1000;
			data.playCount = dataJson.getJSONObject("stats").optInt("play_count");
			
			//视频作者信息
			JSONObject authorJson = dataJson.getJSONObject("author");
			JSONObject thumbJson = authorJson.getJSONObject("avatar_jpg");
			data.authorImgUrl = thumbJson.getJSONArray("url_list").getString(0);
			data.authorName = authorJson.optString("nickname");
			data.authorCity = authorJson.optString("city");
			data.authorAge = authorJson.optString("birthday_description");
			
			data.formatTimeStr = Utils.formatTimeStr(data.createTime);
			data.formatPlayCountStr = Utils.formatNumber(data.playCount);
			
			if(data.title.length() > 30){
				data.filterTitleStr = Utils.filterStrBlank(data.title.substring(0, 30)+"...");
			}else{
				data.filterTitleStr = Utils.filterStrBlank(data.title);
			}
			if(data.authorName.length() > 7){
				data.filterUserNameStr = Utils.filterStrBlank(data.authorName.substring(0, 7)+"...");
			}else{
				data.filterUserNameStr = Utils.filterStrBlank(data.authorName);
			}
			
		}catch(Exception e){
		}
		
		return data;
	}
	
	@Override
	public String toString(){
		return "videotitle="+title+",videoplayurl="+firstVideoPlayUrl+",videodownloadurl="+firstVideoDownloadUrl+",width="+videoWidth+",height="+videoHeight
				+",coverimgurl="+coverImgUrl+",authorname="+authorName+",authorimgurl="+authorImgUrl+",authorcity="+authorCity+",authorage="+authorAge
				+",videoplaycount="+playCount+",videoduration="+videoDuration;
	}
	
}
