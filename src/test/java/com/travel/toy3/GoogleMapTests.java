package com.travel.toy3;

import com.travel.toy3.util.Geocoding;
import com.travel.toy3.util.GoogleMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class GoogleMapTests {

	@Test
	void mapTest() throws IOException {
		GoogleMap a = Geocoding.getGeoInfo("서울특별시 마포구 와우산로26길 9");
		System.out.println("========================================================================");
		System.out.println(a);
		System.out.println("========================================================================");

		String b = Geocoding.getAddress(37.5528662, 126.9262915);
		System.out.println("========================================================================");
		System.out.println(b);
		System.out.println("========================================================================");
	}

}
