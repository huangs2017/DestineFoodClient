package food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;

public class CommonAdapter<T> extends BaseAdapter{

	private Context ctx;
	private List<T> list;
	private int layoutId;
	private int variableId;

	public CommonAdapter(Context ctx, List<T> list, int layoutId, int variableId) {
		this.ctx = ctx;
		this.list = list;
		this.layoutId = layoutId;
		this.variableId = variableId;
	}

	public int getCount() {
		return list != null ? list.size() : 0;
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewDataBinding binding = null;
		if (convertView == null) {
			binding = DataBindingUtil.inflate( LayoutInflater.from(ctx), layoutId, parent, false);
		} else {
			binding = DataBindingUtil.getBinding(convertView);
		}
		binding.setVariable(variableId, list.get(position));
		return binding.getRoot();
	}
	
	
	public void setData(List<T> list) {
		this.list = list;
		notifyDataSetChanged();
	}

}
