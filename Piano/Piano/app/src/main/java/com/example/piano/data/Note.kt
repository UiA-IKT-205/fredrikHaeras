package com.example.piano.data

data class Note(val value:String, val start:String, val end:Double){
    override fun toString(): String{
        return "Note $value. Started at $start. Total duration $end ms"
    }
}