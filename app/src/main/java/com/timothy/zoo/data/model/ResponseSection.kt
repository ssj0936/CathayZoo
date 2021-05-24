package com.timothy.zoo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ZooSectionResponse(

	@field:SerializedName("result")
	val result: ZooSectionResult
)

data class ZooSectionResult(

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("sort")
	val sort: String? = null,

	@field:SerializedName("results")
	val results: List<ZooSectionResultsItem?>
)

@Entity
data class ZooSectionResultsItem(

	@field:SerializedName("E_Pic_URL")
	val ePicURL: String? = null,

	@field:SerializedName("E_Info")
	val eInfo: String? = null,

	@field:SerializedName("E_Category")
	val eCategory: String? = null,

	@field:SerializedName("E_Memo")
	val eMemo: String? = null,

	@field:SerializedName("E_no")
	val eNo: String? = null,

	@field:SerializedName("E_Name")
	val eName: String? = null,

	@PrimaryKey
	@field:SerializedName("_id")
	val id: Int? = null,

	@field:SerializedName("E_URL")
	val eURL: String? = null,

	@field:SerializedName("E_Geo")
	val eGeo: String? = null
):Serializable
