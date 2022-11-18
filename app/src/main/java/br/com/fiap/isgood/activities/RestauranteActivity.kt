package br.com.fiap.isgood.activities

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import br.com.fiap.isgood.R
import br.com.fiap.isgood.adapters.ListRestauranteSocialMidiaAdapter
import br.com.fiap.isgood.databinding.ActivityRestauranteBinding
import br.com.fiap.isgood.model.Restaurante
import br.com.fiap.isgood.viewmodel.RestauranteActivityViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class RestauranteActivity() : BaseDrawerActivity(), OnMapReadyCallback {
    lateinit var map: GoogleMap
    private lateinit var binding: ActivityRestauranteBinding
    lateinit var restauranteActivityViewModel: RestauranteActivityViewModel

    private lateinit var socialMidiaAdapter: ListRestauranteSocialMidiaAdapter

    val logId = ">>> RestauranteActivity <<<<"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestauranteBinding.inflate(layoutInflater)
        setOriginalContentView(binding.root)

        //Iniciando o Maps
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapRestaurante) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Inicia a activit model
        restauranteActivityViewModel =
            ViewModelProvider.NewInstanceFactory().create(RestauranteActivityViewModel::class.java)

        //Ativa os Listeners

        //Ativa os watchers
        restauranteActivityViewModel.restaurante.observe(this) { restaurante ->
            binding.tvNomeLoja.setText(restaurante.nome)
            binding.tvApresentacao.text = restaurante.apresentacao
            binding.ratingBarRestaurante.rating = restaurante.rating
            Glide.with(this).load(restaurante.strLogoRestaurante).into(binding.ivRestauranteTop)
            initRecyclerView()

        }

        //Informando o ID do Restaurante para o ModelView
        restauranteActivityViewModel.idRestaurante.value =
            intent.getStringExtra("idRestaurante") ?: "99"
    }

    private fun initRecyclerView() {
        socialMidiaAdapter = ListRestauranteSocialMidiaAdapter()
        binding.rvRestauranteSocialMidiaList.adapter = ConcatAdapter(socialMidiaAdapter)
        socialMidiaAdapter.submitList(restauranteActivityViewModel.restaurante.value?.socialLinksArrayList)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var restaurante: Restaurante

        if (restauranteActivityViewModel.restaurante.value != null)
            restaurante = restauranteActivityViewModel.restaurante.value!!
        else
            return

        map = googleMap
        val localRestaurante = LatLng(restaurante.latitude, restaurante.longitude)
        val marker = MarkerOptions()
            .position(localRestaurante)
            .title(restaurante.nome)
            .snippet(restaurante.endereco)
            .icon(BitmapDescriptorFactory.defaultMarker())

        map.addMarker(marker)?.showInfoWindow()
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(localRestaurante, 12.5F))

        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(p0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val title = TextView(applicationContext)
                title.setTextColor(Color.BLACK)
                title.setTypeface(null, Typeface.BOLD)
                title.text = restaurante.nome

                val snippet = TextView(applicationContext)
                snippet.setTextColor(Color.GRAY)
                snippet.text = restaurante.endereco

                val info = LinearLayout(applicationContext)
                info.orientation = LinearLayout.VERTICAL
                info.addView(title)
                info.addView(snippet)

                return info
            }
        })


    }
}