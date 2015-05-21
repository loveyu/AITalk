package net.loveyu.aitalk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TalkAdapter extends BaseAdapter {
    private Context context;

    public TalkAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Talk.selfList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Talk getItem(int position) {
        return Talk.selfList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Talk talk = getItem(position);
        return talk.type;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Talk talk = Talk.selfList.get(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            if (getItemViewType(position) == Talk.ME) {
                convertView = View.inflate(context, R.layout.list_item_me, null);
            } else {
                convertView = View.inflate(context, R.layout.list_item_robots, null);
            }
            holder.msg = (TextView) convertView.findViewById(R.id.msg);
            convertView.setTag(holder);
        }
        holder.msg.setText(talk.msg);
        return convertView;
    }
}
