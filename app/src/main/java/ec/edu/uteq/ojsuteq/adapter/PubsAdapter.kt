package ec.edu.uteq.ojsuteq.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import ec.edu.uteq.ojsuteq.R
import ec.edu.uteq.ojsuteq.model.Pubs

class PubsAdapter(
    private val context: Context,
    private val datos: List<Pubs>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = datos.size
    override fun getItem(pos: Int): Any = datos[pos]
    override fun getItemId(pos: Int): Long = datos[pos].publicationId.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vista = convertView ?: inflater.inflate(R.layout.item_pub, parent, false)

        val tvSeccion  = vista.findViewById<TextView>(R.id.tvSeccion)
        val tvTitulo   = vista.findViewById<TextView>(R.id.tvTitulo)
        val tvAutores  = vista.findViewById<TextView>(R.id.tvAutores)
        val tvFecha    = vista.findViewById<TextView>(R.id.tvFecha)
        val btnDoi     = vista.findViewById<Button>(R.id.btnDoi)
        val btnPdf     = vista.findViewById<Button>(R.id.btnPdf)

        val pub = datos[position]

        tvSeccion.text = pub.section.ifBlank { "Artículo" }
        tvTitulo.text  = pub.title
        tvAutores.text = pub.autores.ifBlank { "Autores no disponibles" }
        tvFecha.text   = pub.datePublished.take(10)

        if (pub.doi.isNotBlank()) {
            btnDoi.visibility = View.VISIBLE
            btnDoi.setOnClickListener { abrirUrl(pub.doi) }
        } else {
            btnDoi.visibility = View.GONE
        }

        if (pub.urlPdf.isNotBlank()) {
            btnPdf.visibility = View.VISIBLE
            btnPdf.setOnClickListener { abrirUrl(pub.urlPdf) }
        } else {
            btnPdf.visibility = View.GONE
        }

        return vista
    }

    private fun abrirUrl(url: String) {
        runCatching {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}
