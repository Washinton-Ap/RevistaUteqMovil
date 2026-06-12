package ec.edu.uteq.ojsuteq

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uteq.ojsuteq.adapter.PubsAdapter
import ec.edu.uteq.ojsuteq.model.Pubs
import ec.edu.uteq.ojsuteq.ws.Asynchtask
import ec.edu.uteq.ojsuteq.ws.WebService

class PubsActivity : AppCompatActivity(), Asynchtask {

    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvVacio: TextView

    private var issueId: Int = 78

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pubs)

        listView    = findViewById(R.id.listView)
        progressBar = findViewById(R.id.progressBar)
        tvVacio     = findViewById(R.id.tvVacio)

        issueId = intent.getIntExtra(IssuesActivity.EXTRA_ISSUE_ID, 78)
        val issueLabel = intent.getStringExtra(IssuesActivity.EXTRA_ISSUE_LABEL) ?: "Artículos"

        supportActionBar?.title = issueLabel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cargarArticulos()
    }

    private fun cargarArticulos() {
        progressBar.visibility = View.VISIBLE
        tvVacio.visibility     = View.GONE
        WebService(WebService.URL_PUBS + issueId, this, this).ejecutar()
    }

    override fun processFinish(result: String) {
        progressBar.visibility = View.GONE
        Log.d("RAW_JSON_PUBS", result.take(300))

        if (result.startsWith("ERROR:")) {
            tvVacio.text       = "Error de conexión.\n$result"
            tvVacio.visibility = View.VISIBLE
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            return
        }

        val pubs = Pubs.listFromJsonArray(result)

        if (pubs.isEmpty()) {
            tvVacio.text       = getString(R.string.sin_datos)
            tvVacio.visibility = View.VISIBLE
        } else {
            listView.adapter = PubsAdapter(this, pubs)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
