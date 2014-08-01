package com.sage.core;

import android.os.AsyncTask;

public abstract class AsyncHelper<T> extends AsyncTask<String, Integer, T> {
	public AsyncHelper() {
		this.execute("");
	}

	protected abstract T await();

	@Override
	protected T doInBackground(String... params) {
		return await();
	}

	@Override
	protected void onPostExecute(T result) {
		async(result);
	}

	protected void async(T result) {

	}
}
