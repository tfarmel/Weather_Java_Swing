package com.tfarmel.weather;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -6595649089551656024L;

	public MainFrame(String title) {
		super(title);

		String apiKey = "a1f1987a2843d67b20c3e712ca6d394b";
		double latitude = 37.8267;
		double longitude = -122.4233;
		String forecastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(forecastUrl).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if(response.isSuccessful()){
					System.out.println(response.body().string());
				}
			}
			
			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println("Error : " + e.getMessage());				
			}
		});
	}

}
