package com.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetNews {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject postRequestFromUrl(String url, String body) throws IOException, JSONException {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(body);
		out.flush();

		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			instream.close();
		}
	}

	private static void saveDataToFile(String fileName, String data) {
		BufferedWriter writer = null;
		File file = new File("D:\\javaEE\\workspace\\MonkeyNews\\WebContent\\news" + fileName + ".json");
		// ����ļ������ڣ����½�һ��
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// д��
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("�ļ�д��ɹ���");
	}

	/**
	 * ���ݻ�ȡ��������ȡ����ʱ���ñ�����
	 */
	public static void Getnews(String baid, String url) throws IOException, JSONException {
		JSONObject json = getRequestFromUrl(url);
		ReloveJson(baid, json);
	}

	public static JSONObject getRequestFromUrl(String url) throws IOException, JSONException {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			instream.close();
		}
	}

	private static void ReloveJson(String baid, JSONObject json) {
		String Title = "";
		String Date = "";
		String Content = "";
		JSONArray data = json.getJSONArray("data");
		JSONObject news[] = new JSONObject[10];
		for (int i = 0; i < 10; i++) {
			news[i] = data.getJSONObject(i);
		}
		for (int i = 0; i < 10; i++) {
			Title = news[i].getString("title");
			Date = news[i].getString("publishDateStr");
			Content = news[i].getString("content");
			JSONObject okdata = new JSONObject();
			okdata.put("title", Title);
			okdata.put("date", Date);
			okdata.put("content", delHtmlTags(Content));
			Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
			Matcher matcher = pattern.matcher(Title);
			Title = matcher.replaceAll(""); // ��ƥ�䵽�ķǷ��ַ��Կ��滻
			File file = new File("D:\\javaEE\\workspace\\MonkeyNews\\WebContent\\news" + baid + "$" + Title + ".json");
			// ����ļ����ڣ���ɾ�����˳�һ��
			if (file.exists()) {
				file.delete();
			} else {
				saveDataToFile(baid + "$" + Title, okdata.toString()); // ���������䱣��
			}
		}
	}

	/**
	 * ȥ��html�����к��еı�ǩ
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String delHtmlTags(String htmlStr) {
		Document doc = Jsoup.parse(htmlStr);
		String text = doc.text();
		// remove extra white space
		StringBuilder builder = new StringBuilder(text);
		int index = 0;
		while (builder.length() > index) {
			char tmp = builder.charAt(index);
			if (Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)) {
				builder.setCharAt(index, ' ');
			}
			index++;
		}
		text = builder.toString().replaceAll(" +", " ").trim();
		return text;
	}
}