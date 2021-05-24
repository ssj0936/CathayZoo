package com.timothy.zoo.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlantResponse(

	@field:SerializedName("result")
	val plantResult: PlantResult
)

@Entity
data class PlantResultsItem(
//	@PrimaryKey(autoGenerate = true)
//	val primaryId: Int,

	@PrimaryKey
	@field:SerializedName("﻿F_Name_Ch")
	val fNameCh: String,

	@field:SerializedName("F_pdf02_ALT")
	val fPdf02ALT: String? = null,

	@field:SerializedName("F_Name_En")
	val fNameEn: String? = null,

	@field:SerializedName("F_Voice01_URL")
	val fVoice01URL: String? = null,

	@field:SerializedName("F_Name_Latin")
	val fNameLatin: String? = null,

	@field:SerializedName("F_Pic04_URL")
	val fPic04URL: String? = null,

	@field:SerializedName("F_Summary")
	val fSummary: String? = null,

	@field:SerializedName("F_Brief")
	val fBrief: String? = null,

	@field:SerializedName("F_Location")
	val fLocation: String? = null,

	@field:SerializedName("F_pdf02_URL")
	val fPdf02URL: String? = null,

	@field:SerializedName("F_Voice01_ALT")
	val fVoice01ALT: String? = null,

	@field:SerializedName("F_Pic03_ALT")
	val fPic03ALT: String? = null,

	@field:SerializedName("F_Voice02_URL")
	val fVoice02URL: String? = null,

	@field:SerializedName("F_Voice02_ALT")
	val fVoice02ALT: String? = null,

	@field:SerializedName("F_Pic01_URL")
	val fPic01URL: String? = null,

	@field:SerializedName("F_Pic02_ALT")
	val fPic02ALT: String? = null,

	@field:SerializedName("F_Keywords")
	val fKeywords: String? = null,

	@field:SerializedName("F_Family")
	val fFamily: String? = null,

	@field:SerializedName("F_CID")
	val fCID: String? = null,

	@field:SerializedName("F_Pic01_ALT")
	val fPic01ALT: String? = null,

	@field:SerializedName("F_Pic02_URL")
	val fPic02URL: String? = null,

	@field:SerializedName("F_Update")
	val fUpdate: String? = null,

	@field:SerializedName("F_Voice03_URL")
	val fVoice03URL: String? = null,

	@field:SerializedName("F_Code")
	val fCode: String? = null,

	@field:SerializedName("F_Function＆Application")
	val fFunctionApplication: String? = null,

	@field:SerializedName("F_Voice03_ALT")
	val fVoice03ALT: String? = null,

	@field:SerializedName("F_Pic03_URL")
	val fPic03URL: String? = null,

	@field:SerializedName("F_Vedio_URL")
	val fVedioURL: String? = null,

	@field:SerializedName("F_pdf01_ALT")
	val fPdf01ALT: String? = null,

	@field:SerializedName("F_AlsoKnown")
	val fAlsoKnown: String? = null,

	@field:SerializedName("F_pdf01_URL")
	val fPdf01URL: String? = null,

//	@Ignore
//	@field:SerializedName("_id")
//	val id: Int? = null,

	@field:SerializedName("F_Feature")
	val fFeature: String? = null,

	@field:SerializedName("F_Pic04_ALT")
	val fPic04ALT: String? = null,

	@field:SerializedName("F_Genus")
	val fGenus: String? = null,

	@field:SerializedName("F_Geo")
	val fGeo: String? = null
):Serializable

data class PlantResult(

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("sort")
	val sort: String? = null,

	@field:SerializedName("results")
	val results: List<PlantResultsItem?>
)
