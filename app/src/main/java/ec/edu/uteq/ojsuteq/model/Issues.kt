package ec.edu.uteq.ojsuteq.model

import org.json.JSONObject

data class Issues(
    val issueId: Int,
    val volume: String,
    val number: String,
    val year: String,
    val datePublished: String,
    val title: String,
    val doi: String,
    val cover: String
) {
    fun etiqueta(): String {
        val vol = if (volume.isNotBlank()) "Vol. $volume" else ""
        val num = if (number.isNotBlank()) "Nº $number" else ""
        val yr  = if (year.isNotBlank()) "($year)" else ""
        return listOf(vol, num, yr).filter { it.isNotBlank() }.joinToString(" ")
    }

    companion object {

        fun fromJson(o: JSONObject): Issues = Issues(
            issueId       = o.optString("issue_id", "0").toIntOrNull() ?: 0,
            volume        = o.optString("volume", ""),
            number        = o.optString("number", ""),
            year          = o.optString("year", ""),
            datePublished = o.optString("date_published", ""),
            title         = o.optString("title", "").trim(),
            doi           = o.optString("doi", ""),
            cover         = o.optString("cover", "")
        )

        fun listFromJsonArray(raw: String): List<Issues> {
            val lista = mutableListOf<Issues>()
            return try {
                val cleanRaw = raw.trimStart('\uFEFF').trim()
                val arr = org.json.JSONArray(cleanRaw)
                for (i in 0 until arr.length()) {
                    arr.optJSONObject(i)?.let { lista.add(fromJson(it)) }
                }
                lista
            } catch (e: Exception) {
                android.util.Log.e("Issues", "Error parseando JSON: ${e.message}")
                lista
            }
        }
    }
}
