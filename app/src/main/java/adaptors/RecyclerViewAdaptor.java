package adaptors;

import static com.example.inov_service.MyAppGlideModule.LoadImageToView;
import static models.Urls.URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inov_service.R;
import java.util.List;
import models.RecyclerViewData;

public class RecyclerViewAdaptor
    extends RecyclerView.Adapter<RecyclerViewAdaptor.RecyclerViewAdapterViewHolder> {

  private String TAG = getClass().getSimpleName();

  private List<RecyclerViewData> data;

  private OnItemClickListener listener;

  public RecyclerViewAdaptor(List<RecyclerViewData> data,
      OnItemClickListener listener) {
    this.data = data;
    this.listener = listener;
  }


  @Override
  public RecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {

    View layoutView = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.recycler_view_item, parent, false);

    final RecyclerViewAdapterViewHolder viewHolder = new RecyclerViewAdapterViewHolder(layoutView);

    viewHolder.itemView.setOnClickListener(
        v -> listener.onItemClick(v, viewHolder.getAdapterPosition()));

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerViewAdapterViewHolder holder, int position) {

    RecyclerViewData curr_data = data.get(position);
    holder.titleTextView.setText(curr_data.getTitle());
    holder.descTextView.setText(curr_data.getText());

    if (curr_data.getImgUrl()!= null && !curr_data.getImgUrl().isEmpty()) {
      LoadImageToView(URL + "/" + curr_data.getImgUrl(), holder.imgView);
    }
  }

  @Override
  public int getItemCount() {
    return data.size();
  }


  class RecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

    ImageView imgView;
    TextView titleTextView;
    TextView descTextView;
    View itemView;

    RecyclerViewAdapterViewHolder(View itemView) {
      super(itemView);

      imgView = itemView.findViewById(R.id.imgView);
      titleTextView = itemView.findViewById(R.id.titleTextView);
      descTextView = itemView.findViewById(R.id.descTextView);
      this.itemView = itemView;
    }
  }
}

