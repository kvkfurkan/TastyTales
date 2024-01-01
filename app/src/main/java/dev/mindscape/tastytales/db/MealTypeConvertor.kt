package dev.mindscape.tastytales.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {
    @TypeConverter
    fun fromAnyToString(attribute:Any?) : String{
        if(attribute == null){
            return ""
        }else{
            return attribute as String
        }
    }
    @TypeConverter
    fun fromStringToAny(attribute: String?) : Any{
        if(attribute == null){
            return ""
        }else{
            return attribute
        }
    }
}