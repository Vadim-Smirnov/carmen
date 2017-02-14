package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.CompanyModel

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

object CompanyDetailsContract {

    interface View : BaseContract.View {

        fun showCompanyDetails(company: CompanyModel)

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun fetchCompanyDetail(companyId: Int)
        fun addCompanyToFavorites(company: CompanyModel)
        fun removeCompanyFromFavorites(company: CompanyModel)

    }

}