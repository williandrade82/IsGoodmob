package br.com.fiap.isgood.fragments.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.isgood.R
import br.com.fiap.isgood.activities.ProdutosRestauranteActivity
import br.com.fiap.isgood.adapters.ListRestauranteAdapter
import br.com.fiap.isgood.apis.IsGoodApis
import br.com.fiap.isgood.config.RetrofitInstaceFactory
import br.com.fiap.isgood.model.dao.RestauranteDAO
import br.com.fiap.isgood.model.dto.EmpresasDTO
import br.com.fiap.isgood.utils.RestauranteUtils
import retrofit2.Call
import retrofit2.Response

class RestauranteFragment: Fragment () {

    lateinit  var recyclerView: RecyclerView;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurante,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewRestaurantes);
        configureRecyclerView();
    }

    private fun configureRecyclerView() {
        recyclerView.setLayoutManager(LinearLayoutManager(getActivity()));
        var empresaDTOlist:List<EmpresasDTO> = ArrayList<EmpresasDTO>();
        val retrofitClient = RetrofitInstaceFactory.getRetrofitInstance(getString(R.string.base_url));
        val apis = retrofitClient.create(IsGoodApis::class.java);
        val response: Response<List<EmpresasDTO>>;
        apis.getEmpresas().enqueue(object : retrofit2.Callback<List<EmpresasDTO>> {
            override fun onResponse(call: Call<List<EmpresasDTO>>, response: Response<List<EmpresasDTO>>) {
                empresaDTOlist = response.body()!!;
                val listRestaurantes =
                    RestauranteUtils.convertEmpresasDtoTORestaurantes(empresaDTOlist);
                RestauranteDAO.restaurantes = listRestaurantes;
                val adapter = ListRestauranteAdapter(listRestaurantes, ListRestauranteAdapter.OnClickListener{
                    val intentRestaurante = Intent(activity, ProdutosRestauranteActivity::class.java)
                    intentRestaurante.putExtra("idRestaurante", it.id)
                    startActivity(intentRestaurante)
                });
                recyclerView.adapter = adapter;
            }
            override fun onFailure(call: Call<List<EmpresasDTO>>, t: Throwable) {
                println("Erro ao consultar empresas")
            }
        })
    }

    fun filterRestaurantes (text:String) {
        var adapter:ListRestauranteAdapter ;
        adapter= recyclerView.adapter as ListRestauranteAdapter
        adapter.filter.filter(text);
    }
}