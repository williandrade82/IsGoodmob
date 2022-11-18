package br.com.fiap.isgood.activities

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.isgood.R
import br.com.fiap.isgood.model.AvaliacaoProduto
import br.com.fiap.isgood.model.Restaurante
import br.com.fiap.isgood.model.dao.RestauranteDAO
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_produtos_restaurante.*
import kotlinx.android.synthetic.main.activity_produtos_restaurante.tvNomeLoja

class ProdutosRestauranteActivity : BaseDrawerActivity() {

    lateinit var ratingBarProduto:RatingBar;
    lateinit var textViewIrLoja:TextView;
    lateinit  var recyclerView: RecyclerView;
    lateinit var restaurante : Restaurante


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOriginalContentView(R.layout.activity_produtos_restaurante);

        var idRestaurante = intent.getStringExtra("idRestaurante") ?: "99"
        restaurante = RestauranteDAO.getById(idRestaurante)
        tvNomeLoja.text = restaurante.nome
        Glide.with(this).load(restaurante.strLogoRestaurante).into(ivProdutoRestauranteTop)

        ratingBarProduto = findViewById(R.id.ratingBarProduto);
        ratingBarProduto.rating = restaurante.rating.toFloat();

        textViewIrLoja = findViewById<TextView>(R.id.textViewIrLoja);
        val underlineString = SpannableString(textViewIrLoja.text);
        underlineString.setSpan(UnderlineSpan(), 0, underlineString.length, 0);
        textViewIrLoja.text = underlineString;
        textViewIrLoja.setOnClickListener{
            val intent = Intent(applicationContext, RestauranteActivity::class.java)
            intent.putExtra("idRestaurante", restaurante.id)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerViewListAvaliacoesProdutos);
        configureRecyclerView();
    }

    private fun configureRecyclerView() {
        recyclerView.setLayoutManager(LinearLayoutManager(this));

        /*val avaliacao1 = AvaliacaoProduto (nomeAvaliador  ="rest1", produto = "end1", nota = 3F, comentario = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at tristique turpis. Donec id imperdiet lacus. Nullam turpis odio, suscipit eget egestas ac, faucibus sit amet diam. In lorem tortor.")
        val avaliacao2 = AvaliacaoProduto (nomeAvaliador="rest2", produto = "end2",nota = 4F, comentario = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at tristique turpis. Donec id imperdiet lacus. Nullam turpis odio, suscipit eget egestas ac, faucibus sit amet diam. In lorem tortor.")
        val avaliacao3 = AvaliacaoProduto (nomeAvaliador="rest3", produto = "end3",nota = 1F, comentario = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at tristique turpis. Donec id imperdiet lacus. Nullam turpis odio, suscipit eget egestas ac, faucibus sit amet diam. In lorem tortor.")

        val listAvaliacoes = ArrayList<AvaliacaoProduto>();
        listAvaliacoes.add(avaliacao1);
        listAvaliacoes.add(avaliacao2);
        listAvaliacoes.add(avaliacao3);*/
        //val listAvaliacoes = AvaliacaoProduto.getSampleArray()
        val listAvaliacoes = AvaliacaoProduto.getFromRestaurante(restaurante)

        val adapter = ListAvaliacaoProdutoAdapter(listAvaliacoes);
        recyclerView.adapter = adapter;

    }


    class ListAvaliacaoProdutoAdapter (private val dataSet: ArrayList<AvaliacaoProduto>) :
        RecyclerView.Adapter<ListAvaliacaoProdutoAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nome:TextView
            val nota:RatingBar
            val comentario:TextView
            val lancheNome:TextView
            val lancheImagem : ImageView
            init {
                nome = view.findViewById(R.id.textViewNomeComentario)
                nota = view.findViewById(R.id.ratingBarAvaliacaoProduto)
                comentario = view.findViewById(R.id.textViewComentario)
                lancheNome = view.findViewById(R.id.txtNomeLancheComentario)
                lancheImagem = view.findViewById(R.id.ivLancheComentario)
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_avaliacao_produto, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.nome.text = dataSet[position].usuarioAvaliador.name
            viewHolder.nota.rating = dataSet[position].nota
            viewHolder.comentario.text = dataSet[position].comentario
            viewHolder.lancheNome.text = dataSet[position].lanche.nome
            Glide.with(viewHolder.lancheImagem.context).load(dataSet[position].lanche.strFotoLanche).into(viewHolder.lancheImagem)
        }

        override fun getItemCount() = dataSet.size
    }
}