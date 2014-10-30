package com.zht.train.entity;

import java.util.List;

public class Tickets {
	String validateMessagesShowId;
	String status;
	String httpstatus;
	List<TicketInfo> data;
	List<Messages> messages;
	ValidateMessages validateMessages;

	public String getValidateMessagesShowId() {
		return validateMessagesShowId;
	}

	public void setValidateMessagesShowId(String validateMessagesShowId) {
		this.validateMessagesShowId = validateMessagesShowId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHttpstatus() {
		return httpstatus;
	}

	public void setHttpstatus(String httpstatus) {
		this.httpstatus = httpstatus;
	}

	public List<TicketInfo> getData() {
		return data;
	}

	public void setData(List<TicketInfo> data) {
		this.data = data;
	}

	public List<Messages> getMessages() {
		return messages;
	}

	public void setMessages(List<Messages> messages) {
		this.messages = messages;
	}

	public ValidateMessages getValidateMessages() {
		return validateMessages;
	}

	public void setValidateMessages(ValidateMessages validateMessages) {
		this.validateMessages = validateMessages;
	}

}
