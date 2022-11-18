package br.com.fiap.isgood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.isgood.R
import br.com.fiap.isgood.model.Restaurante
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList


class ListRestauranteAdapter(private val restaurantes: ArrayList<Restaurante>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<ListRestauranteAdapter.ViewHolder>(),Filterable {

    private  var restaurantesFullList:ArrayList<Restaurante> =  ArrayList();
    init {
        restaurantesFullList.addAll(restaurantes);
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView
        val endereco: TextView
        val ivRestauranteMin : ImageView
        init {
            nome = view.findViewById(R.id.textViewNomeRestaurante)
            endereco = view.findViewById(R.id.textViewEnderecoRestaurante)
            ivRestauranteMin = view.findViewById(R.id.ivRestauranteMin)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_restaurante_item, viewGroup, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nome.text = restaurantes[position].nome
        viewHolder.endereco.text = restaurantes[position].endereco
        Glide.with(viewHolder.ivRestauranteMin.context).load(restaurantes[position].strLogoRestaurante).into(viewHolder.ivRestauranteMin)

        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(restaurantes[position])
        }
    }



    override fun getItemCount() = restaurantes.size

    class OnClickListener(val funTocall: (restaurante: Restaurante) -> Unit) {
        fun onClick(restaurante: Restaurante) = funTocall(restaurante)

    }

    override fun getFilter(): Filter {
        return restauranteFilter;
    }


    private val restauranteFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Restaurante> = java.util.ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(restaurantesFullList)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                if (filterPattern.isEmpty() || filterPattern.isBlank()){
                    val results = FilterResults()
                    results.values = restaurantesFullList;
                    return results
                }
                for (item in restaurantesFullList) {
                    if (item.nome.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            restaurantes.clear()
            restaurantes.addAll(results.values as List<Restaurante>)
            notifyDataSetChanged()
        }
    }
}