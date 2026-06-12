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
import ec.edu.uteq.ojsuteq.model.Journals

class JournalsAdapter(
    private val context: Context,
    private val datos: List<Journals>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = datos.size
    override fun getItem(pos: Int): Any = datos[pos]
    override fun getItemId(pos: Int): Long = datos[pos].journalId.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vista = convertView ?: inflater.inflate(R.layout.item_journal, parent, false)

        val imgPortada   = vista.findViewById<ImageView>(R.id.imgPortada)
        val tvNombre     = vista.findViewById<TextView>(R.id.tvNombre)
        val tvAbreviatura = vista.findViewById<TextView>(R.id.tvAbreviatura)

        val j = datos[position]

        tvNombre.text      = j.name
        tvAbreviatura.text = j.abbreviation.uppercase()

        Glide.with(context)
            .load(j.portada)
            .placeholder(R.drawable.logo_uteq)
            .error(R.drawable.logo_uteq)
            .centerCrop()
            .into(imgPortada)

        return vista
    }
}
