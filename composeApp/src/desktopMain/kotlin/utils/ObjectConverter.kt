package utils

import com.google.gson.Gson

class ObjectConverter {

    inline fun <reified T> stringToObject(objectInString: String): T {
        val gson = Gson()
        val result: T = gson.fromJson(objectInString, T::class.java)

        return  result
    }

}