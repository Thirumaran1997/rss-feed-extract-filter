package com.extractor.springboot.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.extractor.springboot.web.model.FeedMessage;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

@Service
public class IndexService {

	private final String url = "jdbc:mysql://localhost:3306/rssdb";
	private final String user = "root";
	private final String password = "Thiru6497@";

	public boolean postRssData(String searchQuery) throws MalformedURLException, IOException, IllegalArgumentException, FeedException {
	    String url = "https://www.thehindu.com/news/cities/"+searchQuery+"/feeder/default.rss";
		URLConnection openConnection = new URL(url).openConnection();
		openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		InputStream is = openConnection.getInputStream();
		InputSource source = new InputSource(is);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        List<FeedMessage> feedList = new ArrayList<>();
        for (SyndEntry entry : (List<SyndEntry>)feed.getEntries()) {
        	FeedMessage message = new FeedMessage();
        	message.setAuthor(entry.getAuthor());
        	message.setDescription(entry.getDescription().getValue());
        	message.setLink(entry.getLink());
        	message.setTitle(entry.getTitle());
        	message.setPubDate(entry.getPublishedDate().toString());
        	feedList.add(message);
        }
        if(!feedList.isEmpty())
        	if(insertData(feedList,searchQuery)==1)
        		return true;
		return false;
	}

	private int insertData(List<FeedMessage> messageList, String searchQuery) {
		int status=0;
		Connection conn = null;
		List<FeedMessage> feedMessageList = new ArrayList<>();
	    try {
	        conn = DriverManager.getConnection(url, user, password);
	        System.out.println("Connected to the PostgreSQL server successfully.");

	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    
	    PreparedStatement st;
		try {
			if(searchQuery.equalsIgnoreCase("Chennai")||searchQuery.equalsIgnoreCase("Bangalore")
					||searchQuery.equalsIgnoreCase("Coimbatore")||searchQuery.equalsIgnoreCase("Delhi")||
					searchQuery.equalsIgnoreCase("Hyderabad")||searchQuery.equalsIgnoreCase("Kochi")||
					searchQuery.equalsIgnoreCase("Kolkata")||searchQuery.equalsIgnoreCase("Mumbai")||
					searchQuery.equalsIgnoreCase("Kozhikode")||searchQuery.equalsIgnoreCase("Madurai")||
					searchQuery.equalsIgnoreCase("Mangalore")||searchQuery.equalsIgnoreCase("PuduCherry")||
					searchQuery.equalsIgnoreCase("Thiruvananthapuram")||searchQuery.equalsIgnoreCase("Tiruchirapalli")||
					searchQuery.equalsIgnoreCase("Vijayawada")||searchQuery.equalsIgnoreCase("Vishakapatnam")){
			st = conn.prepareStatement("DELETE FROM FEED_DATA WHERE CITY_ID = ?");
			st.setString(1,searchQuery);
			int affectedrows = st.executeUpdate();
			for(int i=0;i<messageList.size();i++){
				st = conn.prepareStatement("INSERT INTO FEED_DATA (CITY_ID, AUTHOR, DESCRIPTION, LINK,TITLE,PUB_DATE) VALUES (?, ?, ?, ?,?,?)");
				st.setString(1, searchQuery);
			    st.setString(2, messageList.get(i).getAuthor());
			    st.setString(3, messageList.get(i).getDescription());
			    st.setString(4, messageList.get(i).getLink());
			    st.setString(5, messageList.get(i).getTitle());
			    st.setString(6, messageList.get(i).getPubDate());
			    st.executeUpdate();
			};
			st.close();
			status = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	public List<FeedMessage> filterData(Map<String,Object>recordMap){
		String city = (String) recordMap.get("city");
		String searchQuery = (String) recordMap.get("searchQuery");
		List<FeedMessage> feedMessageList = new ArrayList<>();
		Connection conn = null;
	    try {
	        conn = DriverManager.getConnection(url, user, password);
	        System.out.println("Connected to the PostgreSQL server successfully.");

	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    
	    PreparedStatement st;
		try {
			st = conn.prepareStatement("SELECT * FROM FEED_DATA WHERE LOWER(AUTHOR) LIKE ? OR LOWER(DESCRIPTION) LIKE ? OR LOWER(LINK) LIKE ? OR LOWER(TITLE) LIKE ? OR LOWER(PUB_DATE) LIKE ? AND CITY_ID = ? ");
			st.setString(1, "%"+searchQuery+"%");
			st.setString(2, "%"+searchQuery+"%");
			st.setString(3, "%"+searchQuery+"%");
			st.setString(4, "%"+searchQuery+"%");
			st.setString(5, "%"+searchQuery+"%");
			st.setString(6, city);	
			ResultSet rs = st.executeQuery();
		    while ( rs.next() )
		    {
		    	if(rs.getString("CITY_ID").equals(city)){
		      FeedMessage feedMessage = new FeedMessage();
		      feedMessage.setAuthor(rs.getString("AUTHOR"));
		      feedMessage.setDescription(rs.getString("DESCRIPTION"));
		      feedMessage.setLink(rs.getString("LINK"));
		      feedMessage.setTitle(rs.getString("TITLE"));
		      feedMessage.setPubDate(rs.getString("PUB_DATE"));
		      feedMessage.setCity(rs.getString("CITY_ID"));
		      feedMessageList.add(feedMessage);
		      System.out.println(feedMessage);
		    	}
		    }
		    System.out.println(feedMessageList);
		}catch(SQLException e){
			System.out.println(e);
		}
		return feedMessageList;
	}

}
