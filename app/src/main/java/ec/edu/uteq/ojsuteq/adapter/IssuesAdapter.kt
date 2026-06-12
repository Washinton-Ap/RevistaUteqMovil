package ec.edu.uteq.ojsuteq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ec.edu.uteq.ojsuteq.R
import ec.edu.uteq.ojsuteq.model.Issues

class IssuesAdapter(
    private val context: Context,
    private val datos: List<Issues>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = datos.size
    override fun getItem(pos: Int): Any = datos[pos]
    override fun getItemId(pos: Int): Long = datos[pos].issueId.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vista = convertView ?: inflater.inflate(R.layout.item_issue, parent, false)

        val imgCover  = vista.findViewById<ImageView>(R.id.imgCover)
        val tvEtiqueta = vista.findViewById<TextView>(R.id.tvEtiqueta)
        val tvTitulo  = vista.findViewById<TextView>(R.id.tvTitulo)
        val tvFecha   = vista.findViewById<TextView>(R.id.tvFecha)

        val issue = datos[position]

        tvEtiqueta.text = issue.etiqueta()
        tvTitulo.text   = issue.title.ifBlank { "Edición ${issue.year}" }
        tvFecha.text    = "Publicado: ${issue.datePublished.take(10)}"

        Glide.with(context)
            .load(issue.cover)
            .placeholder(R.drawable.logo_uteq)
            .error(R.drawable.logo_uteq)
            .centerCrop()
            .into(imgCover)

        return vista
    }
}
