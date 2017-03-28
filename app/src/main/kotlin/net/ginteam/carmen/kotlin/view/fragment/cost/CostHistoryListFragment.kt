package net.ginteam.carmen.kotlin.view.fragment.cost

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CostHistoryListContract
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.cost.CostHistoryListPresenter
import net.ginteam.carmen.kotlin.view.adapter.cost.CostHistoryAdapter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment


/**
 * Created by vadimsmirnov on 24.03.17.
 */

class CostHistoryListFragment : BaseFragment<CostHistoryListContract.View,
        CostHistoryListContract.Presenter>(), CostHistoryListContract.View {

    override var mPresenter: CostHistoryListContract.Presenter = CostHistoryListPresenter()

    private var mHistoryItemSelectedListener: OnHistoryItemSelectedListener? = null

    private lateinit var mRecyclerViewHistory: RecyclerView
    private lateinit var mCostHistoryAdapter: CostHistoryAdapter

    companion object {
        fun newInstance(): CostHistoryListFragment = CostHistoryListFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mHistoryItemSelectedListener = context as OnHistoryItemSelectedListener?
    }

    override fun showCostHistoryList(history: List<HistoryModel>) {
        mCostHistoryAdapter = CostHistoryAdapter(history, {
            mHistoryItemSelectedListener?.onHistoryItemSelected(it)
        })
        mRecyclerViewHistory.adapter = mCostHistoryAdapter
    }

    override fun getLayoutResId(): Int = R.layout.fragment_cost_history_list

    override fun updateViewDependencies() {
        super.updateViewDependencies()
        mRecyclerViewHistory = mFragmentView.findViewById(R.id.recycler_view_history) as RecyclerView
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        layoutManager.stackFromEnd = true
        mRecyclerViewHistory.layoutManager = layoutManager
    }

    interface OnHistoryItemSelectedListener {

        fun onHistoryItemSelected(historyItem: HistoryModel)

    }
}