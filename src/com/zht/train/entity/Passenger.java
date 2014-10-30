package com.zht.train.entity;

public class Passenger {
	String isExist;
	String exMsg;
	String[] two_isOpenClick;
	String[] other_isOpenClick;
	Person[] normal_passengers;
	String[] dj_passengers;

	public String[] getDj_passengers() {
		return dj_passengers;
	}

	public void setDj_passengers(String[] dj_passengers) {
		this.dj_passengers = dj_passengers;
	}

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}

	public String[] getTwo_isOpenClick() {
		return two_isOpenClick;
	}

	public void setTwo_isOpenClick(String[] two_isOpenClick) {
		this.two_isOpenClick = two_isOpenClick;
	}

	public String[] getOther_isOpenClick() {
		return other_isOpenClick;
	}

	public void setOther_isOpenClick(String[] other_isOpenClick) {
		this.other_isOpenClick = other_isOpenClick;
	}

	public Person[] getNormal_passengers() {
		return normal_passengers;
	}

	public void setNormal_passengers(Person[] normal_passengers) {
		this.normal_passengers = normal_passengers;
	}

}
