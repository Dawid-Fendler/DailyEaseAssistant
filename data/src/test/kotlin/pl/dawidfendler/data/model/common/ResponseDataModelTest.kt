package pl.dawidfendler.data.model.common

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

val responseBodyDataTest = "Bad Request".toResponseBody("text/plain".toMediaType())