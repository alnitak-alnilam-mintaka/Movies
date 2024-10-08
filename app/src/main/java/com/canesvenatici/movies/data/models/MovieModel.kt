package com.canesvenatici.movies.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date


data class MovieModel (

  @SerializedName("rating"      ) var rating      : Double?           = null,
  @SerializedName("id"          ) var id          : Int?              = null,
  @SerializedName("revenue"     ) var revenue     : Int?              = null,
  @SerializedName("releaseDate" ) var releaseDate : Date?           = null,
  @SerializedName("posterUrl"   ) var posterUrl   : String?           = null,
  @SerializedName("runtime"     ) var runtime     : Int?              = null,
  @SerializedName("title"       ) var title       : String?           = null,
  @SerializedName("overview"    ) var overview    : String?           = null,
  @SerializedName("reviews"     ) var reviews     : Int?              = null,
  @SerializedName("budget"      ) var budget      : Int?              = null,
  @SerializedName("language"    ) var language    : String?           = null,
  @SerializedName("genres"      ) var genres      : List<String>      = listOf()

)