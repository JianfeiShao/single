package com.zht.train.entity;

import java.util.Map;

public class TicketInfo {
	Map<String,String> queryLeftNewDTO;
	String secretStr;
	String buttonTextInfo;
	public Map<String, String> getQueryLeftNewDTO() {
		return queryLeftNewDTO;
	}
	public void setQueryLeftNewDTO(Map<String, String> queryLeftNewDTO) {
		this.queryLeftNewDTO = queryLeftNewDTO;
	}
	public String getSecretStr() {
		return secretStr;
	}
	public void setSecretStr(String secretStr) {
		this.secretStr = secretStr;
	}
	public String getButtonTextInfo() {
		return buttonTextInfo;
	}
	public void setButtonTextInfo(String buttonTextInfo) {
		this.buttonTextInfo = buttonTextInfo;
	}
	
}
