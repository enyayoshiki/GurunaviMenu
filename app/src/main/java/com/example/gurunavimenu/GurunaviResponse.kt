package com.example.gurunavimenu

data class GurunaviResponse(
    val rest: List<Rest>
)

data class Rest(
    val category: String,
    val code: Code,
    val image_url: ImageUrl,
    val name: String
)

data class Code(
    val areacode: String,
    val areacode_s: String,
    val areaname: String,
    val areaname_s: String,
    val category_code_l: List<String>,
    val category_code_s: List<String>,
    val category_name_l: List<String>,
    val category_name_s: List<String>,
    val prefcode: String,
    val prefname: String
)

data class ImageUrl(
    val qrcode: String,
    val shop_image1: String,
    val shop_image2: String
)
