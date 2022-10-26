package com.example.teamapp.di.repository.retrofit

import com.example.teamapp.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody


class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val responseString: String
            val uri = chain.request().url.toUri().toString()
            responseString = if (uri.contains("invites")) {
                INVITATION_RESPONSE
            } else if (uri.contains("4")) {
                SUPPORTERS_ARE_AVAILABLE_BUT_NO_OPEN_SPOT_TEAM_RESPONSE
            } else if (uri.contains("3")) {
                ZERO_SUPPORTER_LIMIT_RESPONSE
            } else if (uri.contains("2")) {
                TEAM_IS_FULL_RESPONSE
            } else {
                NORMAL_TEAM_RESPONSE
            }
            //val responseString = NORMAL_TEAM_RESPONSE
            //val responseString = TEAM_IS_FULL_RESPONSE
            // val responseString = ZERO_SUPPORTER_LIMIT_RESPONSE
            //val responseString = SUPPORTERS_ARE_AVAILABLE_BUT_NO_OPEN_SPOT_TEAM_RESPONSE

            return Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(responseString.toResponseBody("application/json".toMediaTypeOrNull()))
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


private const val NORMAL_TEAM_RESPONSE =
    "{\"id\":\"57994f271ca5dd20847b910c\",\"members\":{\"total\":89,\"administrators\":1,\"managers\":18,\"editors\":6,\"members\":58,\"supporters\":6},\"plan\":{\"memberLimit\":100,\"supporterLimit\":20}}"

private const val TEAM_IS_FULL_RESPONSE =
    "{\"id\":\"57994f271ca5dd20847b910c\",\"members\":{\"total\":100,\"administrators\":2,\"managers\":18,\"editors\":6,\"members\":68,\"supporters\":6},\"plan\":{\"memberLimit\":100,\"supporterLimit\":20}}"

private const val ZERO_SUPPORTER_LIMIT_RESPONSE =
    "{\"id\":\"57994f271ca5dd20847b910c\",\"members\":{\"total\":83,\"administrators\":1,\"managers\":18,\"editors\":6,\"members\":58,\"supporters\":0},\"plan\":{\"memberLimit\":100,\"supporterLimit\":0}}"

private const val SUPPORTERS_ARE_AVAILABLE_BUT_NO_OPEN_SPOT_TEAM_RESPONSE =
    "{\"id\":\"57994f271ca5dd20847b910c\",\"members\":{\"total\":89,\"administrators\":1,\"managers\":18,\"editors\":6,\"members\":58,\"supporters\":6},\"plan\":{\"memberLimit\":100,\"supporterLimit\":6}}"

private const val INVITATION_RESPONSE ="{ \"url\": \"https://example.com/ti/eyJpbnZpdGVJZ\" }"



