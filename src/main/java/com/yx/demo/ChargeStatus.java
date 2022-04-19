package com.yx.demo;

public enum ChargeStatus {
	
		SUCCESS ("充值成功","SUCCESS"),
		PROCESS ("处理中","PROCESSssssssssssss"),
		SENDSUCCESS ("发送成功","SENDSUCCESS"),
		FAILED ("充值失败","FAILED"),
		REFUND ("撤销并退款","REFUND"),
		OTHER ("其它","OTHER");

	private String name;
	private String key;

	ChargeStatus(String name, String key) {
		this.name = name;
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}
}

