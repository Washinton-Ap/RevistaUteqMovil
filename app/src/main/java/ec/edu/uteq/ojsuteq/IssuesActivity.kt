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
import ec.edu.uteq.ojsuteq.adapter.IssuesAdapter
import ec.edu.uteq.ojsuteq.model.Issues
import ec.edu.uteq.ojsuteq.ws.Asynchtask
import ec.edu.uteq.ojsuteq.ws.WebService

class IssuesActivity : AppCompatActivity(), Asynchtask {

    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvVacio: TextView

    private var journalId: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues)

        listView    = findViewById(R.id.listView)
        progressBar = findViewById(R.id.progressBar)
        tvVacio     = findViewById(R.id.tvVacio)

        journalId = intent.getIntExtra(MainActivity.EXTRA_JOURNAL_ID, 2)
        val journalName = intent.getStringExtra(MainActivity.EXTRA_JOURNAL_NAME) ?: "Volúmenes"

        supportActionBar?.title = journalName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cargarVolumenes()

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            val issue = listView.adapter.getItem(pos) as Issues
            val intent = Intent(this, PubsActivity::class.java)
            intent.putExtra(EXTRA_ISSUE_ID,    issue.issueId)
            intent.putExtra(EXTRA_ISSUE_LABEL, issue.etiqueta())
            startActivity(intent)
        }
    }

    private fun cargarVolumenes() {
        progressBar.visibility = View.VISIBLE
        tvVacio.visibility     = View.GONE
        WebService(WebService.URL_ISSUES + journalId, this, this).ejecutar()
    }

    override fun processFinish(result: String) {
        progressBar.visibility = View.GONE
        Log.d("RAW_JSON_ISSUES", result.take(300))

        if (result.startsWith("ERROR:")) {
            tvVacio.text       = "Error de conexión.\n$result"
            tvVacio.visibility = View.VISIBLE
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            return
        }

        val issues = Issues.listFromJsonArray(result)

        if (issues.isEmpty()) {
            tvVacio.text       = getString(R.string.sin_datos)
            tvVacio.visibility = View.VISIBLE
        } else {
            listView.adapter = IssuesAdapter(this, issues)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_ISSUE_ID    = "issue_id"
        const val EXTRA_ISSUE_LABEL = "issue_label"
    }
}
