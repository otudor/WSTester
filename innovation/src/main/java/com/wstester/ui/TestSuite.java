package com.wstester.ui;

import java.util.List;

public class TestSuite {
	private Environment env;
	private List<TestCase> tc;

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public List<TestCase> getTc() {
		return tc;
	}

	public void setTc(List<TestCase> tc) {
		this.tc = tc;
	}
}
