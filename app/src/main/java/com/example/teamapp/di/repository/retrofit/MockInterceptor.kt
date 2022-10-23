package com.example.teamapp.di.repository.retrofit

import com.example.teamapp.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull


class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = TEAM_RESPONSE

            return Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create("application/json".toMediaTypeOrNull(), responseString))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

}


private const val TEAM_RESPONSE =
    "{\"id\":\"57994f271ca5dd20847b910c\",\"members\":{\"total\":89,\"administrators\":1,\"managers\":18,\"editors\":6,\"members\":58,\"supporters\":6},\"plan\":{\"memberLimit\":100,\"supporterLimit\":20}}"
