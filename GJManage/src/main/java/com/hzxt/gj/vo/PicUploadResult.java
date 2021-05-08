package com.hzxt.gj.vo;

import java.io.Serializable;

/**
 * upload result
 * 
 * @author hehongtao
 *
 */
public class PicUploadResult implements Serializable {
	private static final long serialVersionUID = 2842959862824340370L;
	private Integer error = 0; // 图片上传错误不能抛出,所以设置这个标识 0表示无异常 1代表异常
	private String key;
	private String url;
	private String width;
	private String height;

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
