package example.models;

import java.util.ArrayList;


import android.os.AsyncTask;


public class MainListProvider
{
	ArrayList<Article> data;

	public MainListProvider()
	{
		data = new ArrayList<Article>();		
	}

	public ArrayList<Article> LoadData()
	{
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.add(new Article());//Add data here
		data = articles;
		return data;
	}

}
