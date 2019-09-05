package com.cc.skillapp.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Query<T> implements Serializable {
	private static final long serialVersionUID = 350954527188831321L;
	private Object bean;

	public String ErrorCode;
	public String Title;

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	private ArrayList<T> list;

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

}
