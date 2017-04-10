package net.ginteam.carmen.kotlin.view.adapter.cost

import android.graphics.Color
import android.graphics.drawable.Drawable
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
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by vadimsmirnov on 24.03.17.
 */

class CostHistoryAdapter(private val history: List <HistoryModel>,
                         val onCostHistoryItemClick: (HistoryModel) -> Unit) :
        RecyclerView.Adapter <RecyclerView.ViewHolder>() {

    enum class ITEM_TYPE {
        HISTORY, DATE
    }

    private var sortedHistory: MutableList <HistoryModel> = history.sortedBy { it.date }.toMutableList()

    init {
        (1..sortedHistory.size - 1 step 1)
                .filter { sortedHistory[it].date!!.month != sortedHistory[it - 1].date!!.month }
                .forEach { sortedHistory.add(it, sortedHistory[it]) }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == sortedHistory.size ||
                (position != 0 && sortedHistory[position].date!!.month != sortedHistory[position - 1].date!!.month)) {
            return ITEM_TYPE.DATE.ordinal
        }
        return ITEM_TYPE.HISTORY.ordinal
    }

    override fun getItemCount(): Int {
        return if (sortedHistory.isEmpty()) {
            sortedHistory.size
        } else {
            sortedHistory.size + 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE.HISTORY.ordinal) {
            LayoutInflater.from(parent?.context)
                    .inflate(R.layout.list_item_history, parent, false).let {
                ViewHolder(it, onCostHistoryItemClick)
            }
        } else {
            LayoutInflater.from(parent?.context)
                    .inflate(R.layout.list_item_stick, parent, false).let(::DateViewHolder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE.DATE.ordinal) {
            (holder as DateViewHolder).bindData(sortedHistory[position - 1].date!!)
            return
        }
        val currentCompany: HistoryModel = sortedHistory[position]
        (holder as? ViewHolder)?.bindData(currentCompany)
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

        fun bindData(historyItem: HistoryModel) {
            with(historyItem) {
                var icon: Drawable = ContextCompat.getDrawable(CarmenApplication.getContext(), R.drawable.ic_carwash_float_button)
                when (historyItem.costType!!.id) {
                    1L -> icon = ContextCompat.getDrawable(CarmenApplication.getContext(), R.drawable.ic_refuelling_float_button)
                    2L -> icon = ContextCompat.getDrawable(CarmenApplication.getContext(), R.drawable.ic_carwash_float_button)
                    3L -> icon = ContextCompat.getDrawable(CarmenApplication.getContext(), R.drawable.ic_car_service_float_button)
                    4L -> icon = ContextCompat.getDrawable(CarmenApplication.getContext(), R.drawable.ic_costs_float_button)
                }
                mImageButtonCostIcon.setImageDrawable(icon)

                shape.setColor(Color.parseColor(costType!!.color))

                mTextViewCostName.text = costType!!.name
                mTextViewCostDate.text = SimpleDateFormat("dd MMMMMM").format(date)
                mTextViewOdometer.text = odometer.toString()
                mTextViewCostPrice.text = price.toString()
                itemView.setOnClickListener {
                    onClick(this)
                }
            }
        }
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateView: TextView = itemView.findViewById(R.id.text_view_history_date) as TextView

        fun bindData(date: Date) {
            dateView.text = CarmenApplication.getContext()
                    .resources.getStringArray(R.array.month)[date.month]
        }

    }
}