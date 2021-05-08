package com.hzxt.gj.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class StringUtil {

	public static String uuIdToLong() {
		UUID uuid = UUID.randomUUID();
		byte[] bt = uuid.toString().getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(bt, 1, 8);
		Long result = buffer.getLong();

		return result.toString();
	}

	public static String guid() {
		UUID uuid = UUID.randomUUID();
		String strUUID = uuid.toString();
		StringBuilder sb = new StringBuilder();
		char c = '-';
		for (int i = 0; i < strUUID.length(); i++) {
			char ch = strUUID.charAt(i);
			if (ch != c) {
				sb.append(ch);
			}
		}

		return sb.toString();
	}
}
