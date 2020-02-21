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
import models.NewsData;

public class NewsRecyclerViewAdaptor extends
    RecyclerView.Adapter<NewsRecyclerViewAdaptor.NewsViewHolder> {

  private String TAG = getClass().getSimpleName();

  private List<NewsData> data;

  private OnItemClickListener listener;

  public NewsRecyclerViewAdaptor(List<NewsData> data, OnItemClickListener listener) {
    this.data = data;
    this.listener = listener;
  }

  @NonNull
  @Override
  public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View layoutView = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.row_news_item, parent, false);

    final NewsViewHolder viewHolder = new NewsViewHolder(layoutView);

    viewHolder.itemView.setOnClickListener(
        v -> listener.onItemClick(v, viewHolder.getAdapterPosition()));

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
    NewsData curr_data = data.get(position);

    holder.titleTextView.setText(curr_data.getTitle());
    holder.descriptionTextView.setText(curr_data.getSticker());
    holder.dateTextView.setText(curr_data.getPeriod());

    if ( curr_data.getImgUrl()!= null && !curr_data.getImgUrl().isEmpty()) {
      LoadImageToView(URL + "/" + curr_data.getImgUrl(), holder.thumbImageView);
    }
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class NewsViewHolder extends RecyclerView.ViewHolder {

    ImageView thumbImageView;
    TextView titleTextView;
    TextView dateTextView;
    TextView descriptionTextView;
    View itemView;

    NewsViewHolder(View itemView) {
      super(itemView);

      thumbImageView = itemView.findViewById(R.id.thumbImageView);
      titleTextView = itemView.findViewById(R.id.titleTextView);
      descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
      dateTextView = itemView.findViewById(R.id.dateTextView);
      this.itemView = itemView;
    }
  }
}
