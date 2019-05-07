package com.lyj.fakepivix.app.data.source.remote

import android.util.ArrayMap
import com.lyj.fakepivix.app.constant.COMIC
import com.lyj.fakepivix.app.constant.ILLUST
import com.lyj.fakepivix.app.constant.IllustCategory
import com.lyj.fakepivix.app.data.model.response.Illust
import com.lyj.fakepivix.app.data.model.response.IllustListResp
import com.lyj.fakepivix.app.network.retrofit.RetrofitManager
import com.lyj.fakepivix.app.reactivex.schedulerTransform
import io.reactivex.Observable

/**
 * @author greensun
 *
 * @date 2019/4/15
 *
 * @desc 插画、漫画、小说
 */
class IllustRepository private constructor() {

    companion object {
        val instance by lazy { IllustRepository() }
    }

    private val illustList: ArrayMap<String, Illust> = ArrayMap()

    /**
     * 获取推荐
     */
    fun loadRecommendIllust(@IllustCategory category: String): Observable<IllustListResp> {
        val service = RetrofitManager.instance.apiService
        val ob = when(category) {
            ILLUST, COMIC -> service.getRecommendIllust(category)
            else -> service.getHomeNovelRecommendData()
                    .map { IllustListResp(it.contest_exists, it.novels, it.next_url, it.privacy_policy, it.ranking_novels) }
        }
        return ob.doOnNext {
//                    with(it) {
//                        nextUrl = next_url
//                        illusts.forEach {
//                            illust ->
//                            illustList[illust.id.toString()] = illust
//                        }
//                        ranking_illusts.forEach {
//                            illust ->
//                            illustList[illust.id.toString()] = illust
//                        }
//                    }
                }
                .schedulerTransform()
    }

    /**
     * 获取关注的
     */
    fun loadFollowedIllust(@IllustCategory category: String): Observable<IllustListResp> {
        val service = RetrofitManager.instance.apiService
        val ob = when(category) {
            ILLUST, COMIC -> service.getFollowIllustData()
            else -> service.getFollowNovelData()
                    .map { IllustListResp(it.contest_exists, it.novels, it.next_url, it.privacy_policy, it.ranking_novels) }
        }
        return ob.schedulerTransform()
    }

    fun loadMore(nextUrl: String, category: String = ILLUST): Observable<IllustListResp> {
        val service = RetrofitManager.instance.apiService
        val ob = when(category) {
            ILLUST, COMIC -> service.getMoreIllust(nextUrl)
            else -> service.getMoreNovel(nextUrl)
                    .map { IllustListResp(it.contest_exists, it.novels, it.next_url, it.privacy_policy, it.ranking_novels) }
        }
        return ob.doOnNext {
//                    with(it) {
//                        illusts.forEach {
//                            illust ->
//                            illustList[illust.id.toString()] = illust
//                        }
//                    }
                }
                .schedulerTransform()
    }

    fun clear() {
        illustList.clear()
    }
}