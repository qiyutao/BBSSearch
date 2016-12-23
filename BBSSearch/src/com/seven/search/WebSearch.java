package com.seven.search;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WebSearch {
	private String urlName;
	private String pn;
	private String last;
	private String keyWord;
	private ArrayList<Integer> list;
	
	public WebSearch(String u,String p,String l,String k) {
		urlName = u;
		pn = p;
		last = l;
		keyWord = k;
		list = new ArrayList<Integer>();
	}
	
	public void start(int i) {
			try {
				URL url = new URL(urlName+pn+i+last);
				URLConnection conn =   url.openConnection();
				InputStream in = conn.getInputStream();
				InputStreamReader data = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(data);
				String line = "";
				String key = new String(keyWord.getBytes("UTF-8"));
			
				while((line = reader.readLine())!=null) {
					//String line = new String(bs,"UTF-8");
					if(line.contains((key))) {
						//System.out.println(new String(line.getBytes(),"UTF-8"));
						list.add(i);
						break;
					}
				}
				reader.close();
				data.close();
				in.close();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
	public List<Integer> getList() {
		return list;
	}
}
