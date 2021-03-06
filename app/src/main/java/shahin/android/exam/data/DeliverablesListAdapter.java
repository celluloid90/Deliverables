package shahin.android.exam.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import shahin.android.exam.R;

public class DeliverablesListAdapter extends ArrayAdapter<DeliverData> {
    public DeliverablesListAdapter(Context context) {
        super(context, R.layout.deliverables_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.deliverables_item, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DeliverData data = getItem(position);

        if(data != null) {
            Picasso.get().load(data.getThumbnil()).into(holder.mImageView);
            String description = data.getDescription() + " at " + data.getAddress();
            holder.mDescriptionTv.setText(description);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView mImageView;
        TextView mDescriptionTv;

        ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.image);
            mDescriptionTv = (TextView) view.findViewById(R.id.text_description);
        }
    }
}
