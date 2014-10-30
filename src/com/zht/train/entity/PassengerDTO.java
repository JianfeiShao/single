package com.zht.train.entity;

public class PassengerDTO {
	String validateMessagesShowId;
	String status;
	String httpstatus;
	Passenger data;
	String[] messages;
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

	public Passenger getData() {
		return data;
	}

	public void setData(Passenger data) {
		this.data = data;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public ValidateMessages getValidateMessages() {
		return validateMessages;
	}

	public void setValidateMessages(ValidateMessages validateMessages) {
		this.validateMessages = validateMessages;
	}

}
