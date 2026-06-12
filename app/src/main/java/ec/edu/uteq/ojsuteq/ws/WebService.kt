package ec.edu.uteq.ojsuteq.ws

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class WebService(
    private val url: String,
    private val context: Context,
    private val callback: Asynchtask
) {

    fun ejecutar() {
        Log.d(TAG, "GET $url")

        val queue: RequestQueue = Volley.newRequestQueue(context)

        val solicitud = StringRequest(
            Request.Method.GET,
            url,
            { respuesta ->
                Log.d(TAG, "Respuesta OK (${respuesta.length} chars)")
                callback.processFinish(respuesta)
            },
            { error ->
                val msg = error.networkResponse?.let {
                    "HTTP ${it.statusCode}: ${String(it.data ?: ByteArray(0))}"
                } ?: (error.message ?: "Error de red desconocido")
                Log.e(TAG, "Error Volley: $msg")
                callback.processFinish("ERROR: $msg")
            }
        )

        solicitud.setShouldCache(false)
        queue.add(solicitud)
    }

    companion object {
        private const val TAG = "WebService"

        const val URL_JOURNALS = "https://revistas.uteq.edu.ec/ws/journals.php"
        const val URL_ISSUES   = "https://revistas.uteq.edu.ec/ws/issues.php?j_id="
        const val URL_PUBS     = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id="
    }
}
