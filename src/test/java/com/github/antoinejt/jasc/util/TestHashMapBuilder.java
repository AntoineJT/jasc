package com.github.antoinejt.jasc.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class TestHashMapBuilder {

	@Test
	void test() {
		HashMapBuilder<String, String> hashMapBuilder = new HashMapBuilder<String, String>();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Test", "Unitaire");
		
		assertEquals(new HashMap<String, String>(), hashMapBuilder.build());
		assertEquals(hashMap, hashMapBuilder.put("Test", "Unitaire").build());
	}

}
