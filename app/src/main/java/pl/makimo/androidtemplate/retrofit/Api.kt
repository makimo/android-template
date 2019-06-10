package pl.makimo.androidtemplate.retrofit

//import retrofit2.Call
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.PATCH
//import retrofit2.http.Path

/**
 * Declarative specification of the API endpoints.
 */
interface Api {

    companion object {
        const val SOME_URL = "/some/thing"
        const val SOME_URL_WITH_ARG = "some/{thing}/else/"
    }

//  Some examples:
//
//    @GET(SOME_URL)
//    fun getSomething(): Call<Type>
//
//    @POST(SOME_URL)
//    fun postSomething(@Body arg: List<Type>): Call<Type2>
//
//    @PATCH("$TASK_URL{id}/")
//    @FormUrlEncoded
//    fun updateInsurance(
//        @Path("id") id: Int,
//        @Field("type") type: String,
//        @Field("custom_name") customName: String,
//        @Field("policy_number") policyNumber: String,
//        @Field("agency") agency: String
//    ): Call<Task>
}