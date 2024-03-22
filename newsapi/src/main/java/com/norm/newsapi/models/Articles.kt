package com.norm.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

//-"source": {
//    "id": "wired",
//    "name": "Wired"
//},
//"author": "Joel Khalili",
//"title": "What’s Behind the Bitcoin Price Surge? Vibes, Mostly",
//"description": "The price of bitcoin has climbed to a new all-time high. But assigning the cryptocurrency a value is anything but trivial.",
//"url": "https://www.wired.com/story/bitcoin-price-record-economics/",
//"urlToImage": "https://media.wired.com/photos/65ef46042ca08b0e59a9682f/191:100/w_1280,c_limit/031124-business-bitcoin-economics.jpg",
//"publishedAt": "2024-03-12T18:13:56Z",
//"content": "The latest surge in the price of bitcoin is increasing the clamor around it, says Dal Bianco, drawing in yet more speculators and creating a self-reinforcing cycle. Likewise, when collective confiden… [+2967 chars]"
//},

@Serializable
data class Articles(
    @SerialName("source") val source: Source,
    @SerialName("author") val author: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("url") val url: String,
    @SerialName("urlToImage") val urlToImage: String,
    @SerialName("publishedAt") val publishedAt: Date,
    @SerialName("content") val content: String,
)
