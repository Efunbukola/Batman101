package app.saboorsalaam.cooldraggridtest;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ItemAdapter extends ArrayAdapter<Item> implements SpanVariableGridView.CalculateChildrenPosition {

    private int[] colors = {Color.BLUE,Color.GREEN, Color.YELLOW, Color.CYAN };
    int counter = 0;

	private final class ItemViewHolder {





        public TextView itemTitle;
		public TextView itemDescription;
		public ImageView itemIcon;
        public RelativeLayout back;

	}

	private Context mContext;
	private LayoutInflater mLayoutInflater = null;

	private View.OnClickListener onRemoveItemListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {

			Integer position = (Integer) view.getTag();
			removeItem(getItem(position));

		}
	};

	public void insertItem(Item item, int where) {

		if (where < 0 || where > (getCount() - 1)) {

			return;
		}

		insert(item, where);
	}

	public boolean removeItem(Item item) {

		remove(item);

		return true;
	}

	public ItemAdapter(Context context, List<Item> plugins) {

		super(context, R.layout.item, plugins);

		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ItemViewHolder itemViewHolder;

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item, parent, false);

			itemViewHolder = new ItemViewHolder();
			itemViewHolder.itemTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
			itemViewHolder.itemDescription = (TextView) convertView.findViewById(R.id.textViewDescription);
			itemViewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            itemViewHolder.back = (RelativeLayout) convertView.findViewById(R.id.back);
			convertView.setTag(itemViewHolder);

		} else {

			itemViewHolder = (ItemViewHolder) convertView.getTag();
		}

		final Item item = getItem(position);

		SpanVariableGridView.LayoutParams lp = new SpanVariableGridView.LayoutParams(convertView.getLayoutParams());
		lp.span = item.getSpans();
		convertView.setLayoutParams(lp);

		itemViewHolder.itemTitle.setText(item.getTitle());
		itemViewHolder.itemDescription.setText(item.getDescription());
		itemViewHolder.itemIcon.setImageResource(item.getIcon());



        switch (counter) {
            case 0:
                itemViewHolder.back.setBackgroundColor(Color.BLUE);
                counter++;
                break;
            case 1:
                itemViewHolder.back.setBackgroundColor(Color.GREEN);
                counter++;
                break;
            case 2:
                itemViewHolder.back.setBackgroundColor(Color.YELLOW);
                counter++;
                break;
            case 3:
                itemViewHolder.back.setBackgroundColor(Color.RED);
                counter = 0;
            default:
                itemViewHolder.back.setBackgroundColor(Color.GRAY);
                counter=1;
                break;
        }


		return convertView;
	}

	@Override
	public void onCalculatePosition(View view, int position, int row, int column) {

	}
}
