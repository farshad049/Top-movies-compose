package com.farshad.topmovies_compose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farshad.moviesAppCompose.data.model.network.PagingModel
import com.farshad.moviesAppCompose.data.remote.SimpleResponse
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.data.model.mapper.MovieMapper
import com.farshad.topmovies_compose.data.remote.ApiClient


class SearchDataSource (
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
    private val userSearch:String,
    private val localExceptionCallBack: (LocalException)->Unit
)  :PagingSource<Int, DomainMovieModel>() {

    sealed class LocalException(val title:String, val description:String=""):Exception(){
        object EmptySearch : LocalException(title = "Start Typing To Search")
        object NoResult: LocalException(title = "whoops", description = "looks like your search didn't return any result")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainMovieModel> {


        val currentPage = params.key ?: 1

        return try {
            val request= apiClient.getMoviesPagingByName(userSearch,currentPage)
            val endOfPaginationReached = request.bodyNullable?.data?.isEmpty()
            if (request.bodyNullable?.data?.isNotEmpty() == true) {
                LoadResult.Page(
                    data = request.bodyNullable?.data?.map { movieMapper.buildFrom(it) } ?: emptyList(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached == true) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DomainMovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }



    private fun calculateNextPage(request : SimpleResponse<PagingModel>):Int?{
        var nextPage : Int? = null

        if (request.bodyNullable != null){
            val totalPage = request.bodyNullable!!.metadata.page_count
            val currentPage = request.bodyNullable!!.metadata.current_page.toInt()
            if (currentPage < totalPage){
                nextPage = currentPage.plus(1)
            }
        }

        return nextPage
    }


}