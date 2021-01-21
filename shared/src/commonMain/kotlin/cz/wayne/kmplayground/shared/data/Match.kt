package cz.wayne.kmplayground.shared.data

@Serializable
data class Match(
    @SerialName("oppty_info_number")
    val optyInfoNumber: Int,
    @SerialName("oppty_end_date_format")
    val optyEndDateFormat: String? = null,
    @SerialName("oppty_end_time_format")
    val optyEndTimeFormat: String,
    @SerialName("team_name_1")
    val teamName1: String? = null,
    @SerialName("team_name_2")
    val teamName2: String? = null,
    @SerialName("oppty_name")
    val optyName:  String? = null,
    @SerialName("id_opportunity")
    val opportunityId: Int,
    @SerialName("oppty_end_date")
    val opptyEndDate:String? = null,
    @SerialName("oppty_end_time")
    val opptyEndTime:String? = null,
    @SerialName("oppty_note")
    val opptyNote:String? = null,
    @SerialName("oppty_type_name")
    val opptyTypeName:  String? = null,
    @SerialName("id_oppty_tree_type")
    val opptyTreeTypeId: Int,
    val odds: List<Odd>
)