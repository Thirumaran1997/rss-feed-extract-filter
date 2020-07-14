package com.extractor.springboot.web.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.extractor.springboot.web.model.FeedMessage;
import com.extractor.springboot.web.service.IndexService;
import com.google.gson.Gson;
import com.sun.syndication.io.FeedException;

@Controller
@SessionAttributes("name")
public class IndexController {
	
	@Autowired
	IndexService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) throws IllegalArgumentException, FeedException, IOException{
		return "login";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/postData", method = RequestMethod.POST)
	public ResponseEntity postData(@RequestBody String headers,HttpServletRequest request) throws MalformedURLException, IllegalArgumentException, IOException, FeedException{
		Map userDetailMap = new Gson().fromJson(headers, Map.class);
		Map<String,Object> headerMap = (Map<String, Object>) userDetailMap.get("headers");
		String city = (String) headerMap.get("cityname");
		boolean postMessage = service.postRssData(city);
		Map<String,Object> returnMap = new HashMap<>();
//		boolean isValidUser = service.validateUser(name, password);
		
//		if (!postMessage) {
//			model.put("errorMessage", "Data push to db Failed");
//			return "login";
//		}
//		
//		model.put("name", name);
//		model.put("password", password);
//		model.put("errorMessage", "Data push to db Suceeded");
		return new ResponseEntity(returnMap, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/filterData", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity filterData(HttpServletRequest request) throws ClassNotFoundException {
//        String city =(String) model.get("name");
//        String searchQuery=(String) model.get("password");
		Map recordMap = new HashMap<>();
		Map paramMap = new HashMap<>();
		Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            recordMap.put(key, value);
        }
        String city =(String) recordMap.get("cityname");
        String searchQuery=(String) recordMap.get("searchquery");
        paramMap.put("city", city);
        paramMap.put("searchQuery", searchQuery);
		List<FeedMessage> returnList = service.filterData(paramMap);
		Map<String,Object> detailsMap = new HashMap<>();
		detailsMap.put("result", returnList);
		return new ResponseEntity(detailsMap, HttpStatus.OK);
	}
	
	
}
