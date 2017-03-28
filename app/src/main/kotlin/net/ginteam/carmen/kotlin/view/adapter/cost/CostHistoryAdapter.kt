package net.ginteam.carmen.kotlin.view.adapter.cost

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.realm.HistoryModel

/**
 * Created by vadimsmirnov on 24.03.17.
 */

class CostHistoryAdapter(private val history: List <HistoryModel>,
                         val onCostHistoryItemClick: (HistoryModel) -> Unit) :
        RecyclerView.Adapter <CostHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent?.context)
                .inflate(R.layout.list_item_history, parent, false).let {
            ViewHolder(it, onCostHistoryItemClick)
        }
    }

    override fun getItemCount(): Int = history.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(history[position])
    }

    class ViewHolder(itemView: View, val onClick: (HistoryModel) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private var mImageButtonCostIcon: AppCompatImageButton
                = itemView.findViewById(R.id.image_button_cost_icon) as AppCompatImageButton
        private var mTextViewCostName: TextView
                = itemView.findViewById(R.id.text_view_cost_name) as TextView
        private var mTextViewCostDate: TextView
                = itemView.findViewById(R.id.text_view_cost_date) as TextView
        private var mTextViewOdometer: TextView
                = itemView.findViewById(R.id.text_view_odometer) as TextView
        private var mTextViewCostPrice: TextView
                = itemView.findViewById(R.id.text_view_cost_price) as TextView

        val bgDrawable: LayerDrawable = mImageButtonCostIcon.background as LayerDrawable
        val shape: GradientDrawable = bgDrawable.findDrawableByLayerId(R.id.button_color) as GradientDrawable

        fun bindData(history: HistoryModel) {
            with(history) {
                mImageButtonCostIcon.setImageDrawable(ContextCompat.getDrawable(CarmenApplication.getContext(),
                        R.drawable.ic_refuelling_float_button))

                shape.setColor(ContextCompat.getColor(CarmenApplication.getContext(),
                        Color.parseColor(costType!!.color)))

                mTextViewCostName.text = costType!!.name
                mTextViewCostDate.text = date.toString()
                mTextViewOdometer.text = odometer.toString()
                mTextViewCostPrice.text = price.toString()
                itemView.setOnClickListener {
                    onClick(this)
                }
            }
        }
    }
}