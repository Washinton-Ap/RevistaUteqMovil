package ec.edu.uteq.ojsuteq.model

import org.json.JSONArray
import org.json.JSONObject

data class Pubs(
    val publicationId: Int,
    val title: String,
    val doi: String,
    val section: String,
    val datePublished: String,
    val resumen: String,
    val autores: String,
    val urlPdf: String,
    val urlHtml: String
) {
    companion object {

        fun fromJson(o: JSONObject): Pubs {
            val autoresStr = buildString {
                val arr: JSONArray? = o.optJSONArray("authors")
                if (arr != null) {
                    for (i in 0 until arr.length()) {
                        if (i > 0) append(", ")
                        append(arr.optJSONObject(i)?.optString("nombres", "") ?: "")
                    }
                }
            }
            var urlPdf  = ""
            var urlHtml = ""
            val galeys: JSONArray? = o.optJSONArray("galeys")
            if (galeys != null) {
                for (i in 0 until galeys.length()) {
                    val g = galeys.optJSONObject(i) ?: continue
                    val label = g.optString("label", "").uppercase()
                    val url   = g.optString("UrlViewGalley", "")
                    if (label == "PDF"  && urlPdf.isBlank())  urlPdf  = url
                    if (label == "HTML" && urlHtml.isBlank()) urlHtml = url
                }
            }
            val resumenRaw = o.optString("abstract", "")
            val resumenLimpio = resumenRaw.replace(Regex("<[^>]+>"), "")
                .replace(Regex("\\s+"), " ").trim()
            return Pubs(
                publicationId = o.optString("publication_id", "0").toIntOrNull() ?: 0,
                title         = o.optString("title", "Sin título").trim(),
                doi           = o.optString("doi", ""),
                section       = o.optString("section", ""),
                datePublished = o.optString("date_published", ""),
                resumen       = resumenLimpio,
                autores       = autoresStr,
                urlPdf        = urlPdf,
                urlHtml       = urlHtml
            )
        }
        fun listFromJsonArray(raw: String): List<Pubs> {
            val lista = mutableListOf<Pubs>()
            return try {
                val cleanRaw = raw.trimStart('\uFEFF').trim()
                val arr = org.json.JSONArray(cleanRaw)
                for (i in 0 until arr.length()) {
                    arr.optJSONObject(i)?.let { lista.add(fromJson(it)) }
                }
                lista
            } catch (e: Exception) {
                android.util.Log.e("Pubs", "Error parseando JSON: ${e.message}")
                lista
            }
        }
    }
}
