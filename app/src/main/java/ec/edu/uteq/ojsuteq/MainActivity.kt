package ec.edu.uteq.ojsuteq

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uteq.ojsuteq.adapter.JournalsAdapter
import ec.edu.uteq.ojsuteq.model.Journals
import ec.edu.uteq.ojsuteq.ws.Asynchtask
import ec.edu.uteq.ojsuteq.ws.WebService

class MainActivity : AppCompatActivity(), Asynchtask {

    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvVacio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView    = findViewById(R.id.listView)
        progressBar = findViewById(R.id.progressBar)
        tvVacio     = findViewById(R.id.tvVacio)

        cargarRevistas()

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            val j = listView.adapter.getItem(pos) as Journals
            val intent = Intent(this, IssuesActivity::class.java)
            intent.putExtra(EXTRA_JOURNAL_ID,   j.journalId)
            intent.putExtra(EXTRA_JOURNAL_NAME, j.name)
            startActivity(intent)
        }
    }

    private fun cargarRevistas() {
        progressBar.visibility = View.VISIBLE
        tvVacio.visibility     = View.GONE
        WebService(WebService.URL_JOURNALS, this, this).ejecutar()
    }

    override fun processFinish(result: String) {
        progressBar.visibility = View.GONE
        Log.d("RAW_JSON_JOURNALS", result.take(300))

        if (result.startsWith("ERROR:")) {
            tvVacio.text       = "Error de conexión.\n$result"
            tvVacio.visibility = View.VISIBLE
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            return
        }

        val journals = Journals.listFromJsonArray(result)

        if (journals.isEmpty()) {
            tvVacio.text       = getString(R.string.sin_datos)
            tvVacio.visibility = View.VISIBLE
        } else {
            listView.adapter = JournalsAdapter(this, journals)
        }
    }

    companion object {
        const val EXTRA_JOURNAL_ID   = "journal_id"
        const val EXTRA_JOURNAL_NAME = "journal_name"
    }
}
