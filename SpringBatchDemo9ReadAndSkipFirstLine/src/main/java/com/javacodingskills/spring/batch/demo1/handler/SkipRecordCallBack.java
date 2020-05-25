package com.javacodingskills.spring.batch.demo1.handler;

import org.springframework.batch.item.file.LineCallbackHandler;

public class SkipRecordCallBack implements LineCallbackHandler {

	@Override
	public void handleLine(String line) {
		// TODO Auto-generated method stub
		System.out.println("###### first record data "+line);

	}

}
