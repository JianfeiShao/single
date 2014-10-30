package com.zht.train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.zht.train.entity.Passenger;
import com.zht.train.entity.PassengerDTO;
import com.zht.train.entity.Person;
import com.zht.train.entity.TicketInfo;
import com.zht.train.entity.Tickets;

public class TestObectMapper {
	ObjectMapper mapper;

	@Before
	public void start() {
		mapper = new ObjectMapper();
	}

	@After
	public void end() {

	}

	@Ignore
	@Test
	public void objectMapper() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("111", "111");
		map.put("222", "222");
		TicketInfo ci = new TicketInfo();
		ci.setButtonTextInfo("xxx");
		ci.setSecretStr("aaaa");
		ci.setQueryLeftNewDTO(map);

		TicketInfo ci1 = new TicketInfo();
		ci1.setButtonTextInfo("xxx");
		ci1.setSecretStr("aaaa");
		ci1.setQueryLeftNewDTO(map);

		List<TicketInfo> list = new ArrayList<TicketInfo>();
		list.add(ci);
		list.add(ci1);
		Tickets c = new Tickets();
		c.setData(list);
		c.setHttpstatus("200");
		c.setStatus("200");
		c.setValidateMessagesShowId("xxxxxxxxx");
		System.out.println(mapper.writeValueAsString(c));
	}

	@Test
	public void objectMapperx() throws Exception{
		Passenger bi = new Passenger();
		bi.setExMsg("xxff");
		String x[] = new String[]{"1","2"};
		bi.setTwo_isOpenClick(x);
		bi.setOther_isOpenClick(x);
		Person ggg=new Person();
		ggg.setAddress("xxx");
		
		Person[] gg = new Person[]{ggg};
		
		bi.setNormal_passengers(gg);
		
		PassengerDTO shen = new PassengerDTO();
		shen.setData(bi);
		System.out.println(mapper.writeValueAsString(shen));
	}
}
