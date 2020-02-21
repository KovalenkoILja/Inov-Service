package adaptors;

import static com.example.inov_service.MyAppGlideModule.LoadImageToView;
import static models.Urls.URL;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.inov_service.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.List;
import models.CardViewData;

public class SliderAdapter extends
    SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

  private String TAG = getClass().getSimpleName();

  private List<CardViewData> data;

  private OnItemClickListener listener;


  public SliderAdapter(List<CardViewData> data, OnItemClickListener listener) {
    this.data = data;
    this.listener = listener;
  }

  @Override
  public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
    View inflate = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.image_slider_layout_item, null);
    return new SliderAdapterViewHolder(inflate);
  }

  @Override
  public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

    viewHolder.itemView.setOnClickListener(
        v -> listener.onItemClick(v, position));

    CardViewData curr_data = data.get(position);
    viewHolder.textViewDescription.setText(curr_data.getTitle());
    viewHolder.textViewDescription.setTextSize(18);
    viewHolder.textViewDescription.setTextColor(Color.WHITE);
    viewHolder.imageGifContainer.setVisibility(View.VISIBLE);
    viewHolder.imageViewBackground.setVisibility(View.VISIBLE);

    if (curr_data.getItemImageUrl()!= null && !curr_data.getItemImageUrl().isEmpty()) {
      LoadImageToView(URL + curr_data.getItemImageUrl(), viewHolder.imageGifContainer);
    }
    if (curr_data.getBackgroundImageUrl()!= null && !curr_data.getBackgroundImageUrl().isEmpty()) {
      LoadImageToView(URL + curr_data.getBackgroundImageUrl(), viewHolder.imageViewBackground);
    }
  }

  @Override
  public int getCount() {
    //slider view count could be dynamic size
    return data.size();
  }

  class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

    View itemView;
    ImageView imageViewBackground;
    ImageView imageGifContainer;
    TextView textViewDescription;

    SliderAdapterViewHolder(View itemView) {
      super(itemView);
      imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
      imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
      textViewDescription = itemView.findViewById(R.id.titleTextView);
      this.itemView = itemView;
    }
  }


}