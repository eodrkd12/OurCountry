package com.honestyandpassion.ourcountry.Item

import java.io.Serializable

data class ChatRoomItem (
    var roomId:Int,
    var maker:String,
    var partner:String,
    var roomDate:String,
    var roomTitle:String,
    var lastChat:String?,
    var lastChatFullTime:String?,
    var lastChatTime:String?
) : Serializable