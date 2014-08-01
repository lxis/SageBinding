package example.views;

import java.util.ArrayList;

import com.sage.binding.R;
import com.sage.binding.R.layout;
import com.sage.binding.R.menu;
import com.sage.binding.mvvm.facade.BindingAdapter;
import com.sage.binding.mvvm.facade.ViewModelBinder;
import com.sage.core.AsyncHelper;

import example.viewmodels.NewMainItemViewModel;
import example.viewmodels.NewMainViewModel;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class MainActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	NewMainViewModel viewModel = new NewMainViewModel();
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ViewModelBinder.Bind(viewModel, R.layout.new_main_activity,
				MainActivity.this);
		listView = (ListView) findViewById(R.id.newMainList);
		bindList();
	}

	private void bindList() {
		new AsyncHelper<ArrayList<NewMainItemViewModel>>() {
			@Override
			protected ArrayList<NewMainItemViewModel> await() {
				return viewModel.LoadData();
			}

			@Override
			protected void async(ArrayList<NewMainItemViewModel> result) {
				viewModel.AddData(result);
				bindAdapter();
			}
		};
	}

	BindingAdapter<NewMainItemViewModel> adapter;

	private void bindAdapter() {
		if (adapter == null) {
			adapter = new BindingAdapter<NewMainItemViewModel>(viewModel.Data,
					MainActivity.this, R.layout.new_main_list_item,
					NewMainItemViewModel.class);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	};
}
