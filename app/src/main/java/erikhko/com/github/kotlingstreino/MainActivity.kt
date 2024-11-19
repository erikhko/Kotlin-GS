package erikhko.com.github.kotlingstreino

// 3SIS
// Erik Hoon Ko RM93599

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import erikhko.com.github.kotlingstreino.adapter.DicaAdapter
import erikhko.com.github.kotlingstreino.database.DicaDatabaseHelper
import erikhko.com.github.kotlingstreino.model.Dica
import erikhko.com.github.kotlingstreino.R.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DicaAdapter
    private lateinit var searchView: SearchView
    private lateinit var databaseHelper: DicaDatabaseHelper

    private var dicas = mutableListOf<Dica>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        databaseHelper = DicaDatabaseHelper(this)

        recyclerView = findViewById(id.recyclerView)
        searchView = findViewById(id.searchView)

        dicas = databaseHelper.listarDicas().toMutableList()

        if (dicas.isEmpty()) {
            inicializarDicas()
            dicas = databaseHelper.listarDicas().toMutableList()
        }

        adapter = DicaAdapter(this, dicas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredDicas = dicas.filter { it.titulo.contains(newText ?: "", ignoreCase = true) }
                recyclerView.adapter = DicaAdapter(this@MainActivity, filteredDicas)
                return true
            }
        })
    }

    private fun inicializarDicas() {
        val dicasIniciais = listOf(

            Dica(
                "Reduza o consumo de água",
                "Feche a torneira enquanto escova os dentes.",
                "https://exemplo.com/economia-de-agua",
                "Essa simples atitude pode economizar até 20 litros de água por dia."
            ),
            Dica(
                "Desligue aparelhos",
                "Aparelhos eletrônicos consomem energia mesmo em modo de espera.",
                "https://exemplo.com/standby",
                "Dispositivos em standby podem consumir até 10% da sua conta de energia."
            ),
            Dica(
                "Plante uma árvore",
                "Contribua para o meio ambiente plantando árvores.",
                "https://exemplo.com/plantar-arvores",
                "Uma única árvore pode absorver até 22 kg de CO₂ por ano."
            ),
            Dica(
                "Compre produtos locais",
                "Dê preferência a produtores locais para reduzir a pegada de carbono.",
                "https://exemplo.com/produtos-locais",
                "Produtos locais chegam mais frescos e reduzem os impactos ambientais do transporte."
            ),
            Dica(
                "Use sacolas reutilizáveis",
                "Prefira sacolas reutilizáveis ao invés de plásticas.",
                "https://exemplo.com/sacolas-reutilizaveis",
                "O uso de sacolas plásticas demora centenas de anos para se decompor."
            ),
            Dica(
                "Evite o desperdício de alimentos",
                "Planeje suas refeições e aproveite os alimentos por completo.",
                "https://exemplo.com/evitar-desperdicio",
                "Cerca de 30% dos alimentos produzidos no mundo são desperdiçados todos os anos."
            )
        )

        for (dica in dicasIniciais) {
            databaseHelper.inserirDica(dica)
        }
    }
}
