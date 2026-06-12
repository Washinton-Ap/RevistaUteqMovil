package ec.edu.uteq.ojsuteq.model

import org.json.JSONObject

data class Journals(
    val journalId: Int,
    val name: String,
    val abbreviation: String,
    val portada: String,
    val description: String
) {
    companion object {

        fun fromJson(o: JSONObject): Journals {
            val descRaw = o.optString("description", "")
            val descLimpia = descRaw.replace(Regex("<[^>]+>"), "").trim()

            return Journals(
                journalId    = o.optString("journal_id", "0").toIntOrNull() ?: 0,
                name         = o.optString("name", "Sin nombre"),
                abbreviation = o.optString("abbreviation", ""),
                portada      = o.optString("portada", ""),
                description  = descLimpia
            )
        }

        fun listFromJsonArray(raw: String): List<Journals> {
            val lista = mutableListOf<Journals>()
            return try {
                val cleanRaw = raw.trimStart('\uFEFF').trim()
                val arr = org.json.JSONArray(cleanRaw)
                for (i in 0 until arr.length()) {
                    arr.optJSONObject(i)?.let { lista.add(fromJson(it)) }
                }
                lista
            } catch (e: Exception) {
                android.util.Log.e("Journals", "Error parseando JSON: ${e.message}")
                lista
            }
        }
    }
}
