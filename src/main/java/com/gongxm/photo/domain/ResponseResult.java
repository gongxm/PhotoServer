package com.gongxm.photo.domain;

public class ResponseResult {
	private int errcode;
	private String errmsg;
	private Object result;

	public ResponseResult() {
		this.errcode = 0;
		this.errmsg = "请求失败!";
	}

	public ResponseResult(int errcode, String errmsg) {
		this.errcode=errcode;
		this.errmsg=errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResponseResult [errcode=" + errcode + ", errmsg=" + errmsg + ", result=" + result + "]";
	}

	public void setSuccess() {
		this.errcode = 1;
		this.errmsg = "请求成功!";
	}

}
