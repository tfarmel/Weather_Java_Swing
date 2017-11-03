package com.tfarmel.weather;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import okhttp3.Call;
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

		new ForecastWorker(forecastUrl).execute();
	}
	
	class ForecastWorker extends SwingWorker<String, Void>{
		
		private String forecastUrl;
		
		public ForecastWorker(String forecastUrl){
			this.forecastUrl = forecastUrl;
		}

		@Override
		protected String doInBackground() throws Exception {
			System.out.println(Thread.currentThread().getName());
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(forecastUrl).build();
			Call call = client.newCall(request);
			try {
				Response response = call.execute();
				if(response.isSuccessful()){
					return response.body().string();				
				}
			} catch (IOException e) {
				System.err.println("Error : " + e);
			}
			return null;
		}
		
		@Override
		protected void done(){
			try {
				System.out.println(get());
			} catch (InterruptedException | ExecutionException e) {
				System.err.println("Error : " + e);
			}
			super.done();
		}
		
	}
}
