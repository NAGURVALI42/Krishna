package com.example.krishna

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("school/api/register.php")
    fun registerUser(@Body request: TeacherRegisterRequest): Call<RegisterResponse>

    @Headers("Accept: application/json", "Content-Type: text/plain")
    @POST("school/api/login.php")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @Multipart
    @POST("school/api/post_notice.php")
    fun postNotice(
        @Part("notice_date") noticeDate: RequestBody,
        @Part("due_date") dueDate: RequestBody,
        @Part("description") description: RequestBody,
        @Part("link_url") linkUrl: RequestBody,
        @Part coverImage: MultipartBody.Part
    ): Call<NoticeResponse>


    @GET("school/api/get_notices.php")
    fun getNotices(): Call<NoticeListResponse>

    @Multipart
    @POST("school/api/post_event.php")
    fun postEvent(
        @Part("event_date") eventDate: RequestBody,
        @Part("due_date") dueDate: RequestBody,
        @Part("description") description: RequestBody,
        @Part coverImage: MultipartBody.Part
    ): Call<PostEventResponse>


    @GET("school/api/get_events.php")
    fun getEvents(): Call<GetEventsResponse>


    @Multipart
    @POST("school/api/upload_work.php")
    fun uploadWork(
        @Part("date") date: RequestBody,
        @Part("due_date") dueDate: RequestBody,
        @Part("uploader_id") uploaderId: RequestBody,
        @Part("description") description: RequestBody,
        @Part document: MultipartBody.Part
    ): Call<UploadResponse>

    @GET("school/api/get_schedules.php")
    fun getSchedules(): Call<ScheduleResponse>

    @DELETE("delete_schedule.php")
    fun deleteSchedule(
        @Query("id") id: Int
    ): Call<GenericResponse>

    @POST("school/api/post_schedule.php")
    fun postSchedule(@Body request: PostScheduleRequest): Call<PostScheduleResponse>
    @GET("school/api/get_achievements.php")
    fun getAchievements(@Query("achiever_id") achieverId: Int): Call<AchievementResponse>


}
