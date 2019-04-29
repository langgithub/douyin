package cn.wjdiankong.levideo.data;

public class LevideoData{

	public String title;//视频标题
	public String authorImgUrl;//作者头像地址
	public String authorName;//作者名称
	public String coverImgUrl;//视频封面图片地址
	public String firstVideoPlayUrl;//视频播放地址
	public String secondVideoPlayUrl;
	public String thirdVideoPlayUrl;
	public String firstVideoDownloadUrl;//视频下载地址
	public String secondVideoDownloadUrl;
	public String thirdVideoDownloadUrl;
	public int videoWidth;//视频宽度
	public int videoHeight;//视频高度
	public int playCount;//播放次数
	public long createTime;//创建时间

	//抖音专用
	public String musicImgUrl;//音乐图片
	public String musicName;//音乐名称
	public String musicAuthorName;//音乐作者

	//火山专用
	public String videoDuration;//视频时长(秒拍也用到)
	public String authorCity;//作者所在城市
	public String authorAge;//作者年龄

	//为了更好的效率问题提前格式化内容字段
	public String formatTimeStr = "";
	public String filterTitleStr = "";
	public String filterUserNameStr = "";
	public String formatPlayCountStr = "";
	public String filterMusicNameStr = "";

	//视频的Base64值
	public int type = 0;//视频类型：1 抖音 2 火山 3 快手 4 秒拍

}
