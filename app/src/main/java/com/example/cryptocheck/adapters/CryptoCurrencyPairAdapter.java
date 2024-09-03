package com.example.cryptocheck.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cryptocheck.R;
import com.example.cryptocheck.models.CryptoCurrencyPairModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CryptoCurrencyPairAdapter extends ArrayAdapter<CryptoCurrencyPairModel> {

    private ArrayList<CryptoCurrencyPairModel> cryptoPairs;

    public CryptoCurrencyPairAdapter(Context context, ArrayList<CryptoCurrencyPairModel> cryptoPairs) {
        super(context, R.layout.adapter_row_data);
        this.cryptoPairs = cryptoPairs;
    }

    private static class EmptyViewHolder {
        TextView emptyMessageTextView;
    }

    private static class CryptoPairHolder {
        TextView titleTextView;
        TextView priceChangePercentTextView;
        TextView bidAskPriceTextView;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (cryptoPairs.isEmpty()) {
            EmptyViewHolder holder;
            if (row == null || !(row.getTag() instanceof EmptyViewHolder)) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                row = inflater.inflate(R.layout.adapter_row_empty, parent, false);

                holder = new EmptyViewHolder();
                holder.emptyMessageTextView = row.findViewById(R.id.emptyMessageTextView);

                row.setTag(holder);
            }

            holder = (EmptyViewHolder) row.getTag();
            holder.emptyMessageTextView.setText(R.string.adapter_no_data);

            return row;
        } else {
            CryptoPairHolder holder;
            CryptoCurrencyPairModel pair = cryptoPairs.get(position);

            if (row == null || !(row.getTag() instanceof CryptoPairHolder)) {
                LayoutInflater inflater = LayoutInflater.from(getContext());

                row = inflater.inflate(R.layout.adapter_row_data, parent, false);

                holder = new CryptoPairHolder();

                holder.titleTextView = row.findViewById(R.id.title_textview);
                holder.priceChangePercentTextView = row.findViewById(R.id.price_change_percent_textview);
                holder.bidAskPriceTextView = row.findViewById(R.id.bid_ask_price_textview);
                row.setTag(holder);
            }

            holder = (CryptoPairHolder) row.getTag();

            holder.titleTextView.setText(pair.getSymbol());
            double priceChangePercent = Double.parseDouble(pair.getPriceChangePercent());
            holder.priceChangePercentTextView.setText(String.format("%s%s", new DecimalFormat("##.##").format(priceChangePercent), "%"));
            holder.bidAskPriceTextView.setText(String.format("%s/%s", pair.getBidPrice(), pair.getAskPrice()));

            return row;
        }
    }

    @Override
    public int getCount() {
        if (cryptoPairs.isEmpty()) {
            return 1;
        } else {
            return cryptoPairs.size();
        }
    }

    @Override
    public boolean isEnabled(int position) {
        if (cryptoPairs == null || cryptoPairs.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void updateItems(ArrayList<CryptoCurrencyPairModel> items) {
        this.cryptoPairs = items;
        notifyDataSetChanged();
    }

}
