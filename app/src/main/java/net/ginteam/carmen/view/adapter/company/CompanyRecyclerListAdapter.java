package net.ginteam.carmen.view.adapter.company;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyRecyclerListAdapter extends RecyclerView.Adapter <CompanyItemViewHolder> {

    private Context mContext;
    private CompanyListFragment.COMPANY_LIST_TYPE mType;
    private List <CompanyModel> mCompanies;

    private CompanyItemViewHolder.OnCompanyItemClickListener mCompanyItemClickListener;

    public CompanyRecyclerListAdapter(Context context, List <CompanyModel> companies, CompanyListFragment.COMPANY_LIST_TYPE type) {
        mContext = context;
        mType = type;
        mCompanies = companies;
    }

    public void setOnCompanyItemClickListener(CompanyItemViewHolder.OnCompanyItemClickListener listener) {
        mCompanyItemClickListener = listener;
    }

    @Override
    public CompanyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View companyItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(getListItemIdForType(mType), parent, false);
        return new CompanyItemViewHolder(companyItemView);
    }

    @Override
    public void onBindViewHolder(CompanyItemViewHolder holder, int position) {
        CompanyModel currentCompany = mCompanies.get(position);
        holder.getTextViewName().setText(currentCompany.getName());
        holder.getRatingViewRating().setRating(currentCompany.getRating());
        holder.getTextViewPrice().setText(String.valueOf(currentCompany.getPrice()));

        holder.getImageButtonAddToFavorite().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Like",Toast.LENGTH_SHORT).show();
            }
        });

        if (mCompanyItemClickListener == null) {
            Log.e("CompanyListAdapter", "mCompanyItemClickListener does not set!");
            return;
        }
        holder.setOnCompanyItemClickListener(currentCompany, mCompanyItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    private int getListItemIdForType(CompanyListFragment.COMPANY_LIST_TYPE type) {
        switch (type) {
            case RECENTLY_WATCHED: case POPULAR:
                return R.layout.list_item_company_vertical;
            default:
                return R.layout.list_item_company_horizontal;
        }
    }

}
