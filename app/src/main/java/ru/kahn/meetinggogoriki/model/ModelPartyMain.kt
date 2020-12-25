package ru.kahn.meetinggogoriki.model

import java.net.URL

data class ModelPartyMain (
    val cod : String,
    val message : String,
    val icon_organizer : String,
    val image_party : String,
    val name_party : String,
    val name_organizer : String,
    val list_members : MutableList<ModelListMembers>
)

data class ModelListMembers (
    var image_member : String,
    var name_member : String
)

