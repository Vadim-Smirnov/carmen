package net.ginteam.carmen.kotlin.view.adapter.city

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CityModel

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */
class CitiesAdapter(private val cities: List <CityModel>,
                    val onCityItemClick: (CityModel) -> Unit) : RecyclerView.Adapter <CitiesAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent?.context)
                .inflate(R.layout.list_item_city, parent, false).let {
            ViewHolder(it, onCityItemClick)
        }
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(cities[position])
    }

    class ViewHolder(itemView: View, val onClick: (CityModel) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private var mTextViewCityName: TextView
                = itemView.findViewById(R.id.text_view_city_name) as TextView

        fun bindData(city: CityModel) {
            with(city) {
                mTextViewCityName.text = name
                itemView.setOnClickListener {
                    onClick(this)
                }
            }
        }

    }

}