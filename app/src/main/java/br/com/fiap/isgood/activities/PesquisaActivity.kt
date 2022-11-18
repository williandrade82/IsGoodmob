package br.com.fiap.isgood.activities

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import br.com.fiap.isgood.databinding.ActivityPesquisaBinding
import br.com.fiap.isgood.fragments.tab.LancheFragment
import br.com.fiap.isgood.fragments.tab.RestauranteFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class PesquisaActivity : BaseDrawerActivity() {

    lateinit  var tabLayout:TabLayout;
    lateinit  var viewPager2: ViewPager2;
    lateinit  var searchView: SearchView;
    val tabs = arrayListOf<String>("Restaurantes","Lanches");

    lateinit var binding: ActivityPesquisaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityPesquisaBinding.inflate(layoutInflater)
        setOriginalContentView(binding.root)
        //setOriginalContentView(br.com.fiap.isgood.R.layout.activity_pesquisa);
        searchView = binding.searchBar // findViewById(br.com.fiap.isgood.R.id.searchBar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterRestaurantes(newText);
                return true;
            }
        })
        configureTabs();
    }

    private fun filterRestaurantes(text: String?) {

       var frag: Fragment? = supportFragmentManager.findFragmentByTag("f"
                + 0);

        if (frag != null) {
            var restauranteFragment:RestauranteFragment = frag as RestauranteFragment
            text?.let { restauranteFragment.filterRestaurantes(it) };
        }

        frag = supportFragmentManager.findFragmentByTag("f"
        + 1);

        if (frag != null) {
            var lancheFragment:LancheFragment = frag as LancheFragment
            text?.let { lancheFragment.filterLanches(it) };
        }
    }

    private fun configureTabs() {
        tabLayout = findViewById(br.com.fiap.isgood.R.id.tabLayoutPesquisa);
        viewPager2 = findViewById(br.com.fiap.isgood.R.id.fragViewPagerPesquisa);
        viewPager2.adapter = FragmentViewPageAdapter(this,tabs.size);

        TabLayoutMediator (tabLayout,viewPager2,TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabs[position];
        }).attach();
    }

    open class FragmentViewPageAdapter (activity: BaseDrawerActivity, private val tabSize: Int) : FragmentStateAdapter (activity) {
        var restFrag = RestauranteFragment();
        var lancheFrag = LancheFragment();

        override fun getItemCount(): Int {
            return tabSize;
        }

        override fun createFragment(position: Int): Fragment {

            when(position) {
                0 -> return restFrag;
                1 -> return lancheFrag;
            }

            return Fragment();
        }
    }
}