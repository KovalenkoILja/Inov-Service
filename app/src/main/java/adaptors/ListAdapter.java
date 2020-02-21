package adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.inov_service.R;
import java.util.List;
import models.RecyclerViewData;

public class ListAdapter  extends BaseAdapter {

  private String TAG = getClass().getSimpleName();

  private List<RecyclerViewData> data;

  private int resourceLayout;
  private Context context;
  private OnItemClickListener listener;

  public ListAdapter(int resourceLayout, Context context, List<RecyclerViewData> data,
      OnItemClickListener listener) {
    this.resourceLayout = resourceLayout;
    this.context = context;
    this.data = data;
    this.listener = listener;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public RecyclerViewData getItem(int position) {
    return data.get(position);
  }

  @Override
  public long getItemId(int position) {
    return data.indexOf(getItem(position));
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    View holder = convertView;

    if (holder == null) {
      LayoutInflater vi;
      vi = LayoutInflater.from(context);
      holder = vi.inflate(resourceLayout, null);
    }
    RecyclerViewData item = getItem(position);

    if (item != null) {
      TextView textView = holder.findViewById(R.id.title);
      if (textView != null) {
        textView.setText(item.getTitle());
      }

      holder.setOnClickListener(
          v -> listener.onItemClick(v, position));
    }
    return holder;
  }
}
