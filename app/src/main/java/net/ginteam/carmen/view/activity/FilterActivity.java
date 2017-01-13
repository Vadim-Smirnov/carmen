package net.ginteam.carmen.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.filter.FilterModel;
import net.ginteam.carmen.view.custom.FilterEditText;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements FilterEditText.OnFilterChangeListener {

    private final String GSON = "[\n" +
            "    {\n" +
            "        \"name\" : \"Рейтинг\",\n" +
            "        \"type\" : \"rating\",\n" +
            "        \"dialog\" : [\n" +
            "            {\n" +
            "                \"key\" : \"3\",\n" +
            "                \"value\" : \"Средний\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\" : \"4\",\n" +
            "                \"value\" : \"Хороший\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\" : \"5\",\n" +
            "                \"value\" : \"Отличный\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\" : \"Цена\",\n" +
            "        \"type\" : \"price_rel\",\n" +
            "        \"dialog\" : [\n" +
            "            {\n" +
            "                \"key\" : \"50\",\n" +
            "                \"value\" : \"Дешевый\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\" : \"150\",\n" +
            "                \"value\" : \"Средний\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\" : \"250\",\n" +
            "                \"value\" : \"Дорогой\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\" : \"Название\",\n" +
            "        \"type\" : \"name\",\n" +
            "        \"dialog\" : null\n" +
            "    }\n" +
            "]   ";

    private LinearLayout mLinearLayout;
    private List <FilterEditText> mFilterEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mLinearLayout = (LinearLayout) findViewById(R.id.main_content);

        FilterModel[] filterModels = new Gson().fromJson(GSON, FilterModel[].class);
        mFilterEditTexts = new ArrayList<>();

        for (FilterModel filterModel : filterModels) {
            FilterEditText filterEditText = new FilterEditText(this);
            filterEditText.setFilterModel(filterModel);
            filterEditText.setOnFilterChangeListener(this);

            mFilterEditTexts.add(filterEditText);
            mLinearLayout.addView(filterEditText);
        }
    }

    @Override
    public void onFilterChanged(FilterEditText filterEditText) {
        String result = "";
        for (FilterEditText filter: mFilterEditTexts) {
            result += filter.getStringFilter();
        }
        Log.d("FilterActivity", result);
    }

}