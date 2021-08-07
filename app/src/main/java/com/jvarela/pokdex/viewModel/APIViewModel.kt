package com.jvarela.pokdex.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jvarela.pokdex.db.entity.UserPokemon
import com.jvarela.pokdex.manager.api.ProxyManager
import com.jvarela.pokdex.model.entity.FinalStat
import com.jvarela.pokdex.model.entity.Pokemon
import com.jvarela.pokdex.model.response.PokemonData
import com.jvarela.pokdex.viewModel.DBViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIViewModel(application: Application) : DBViewModel(application) {

    private val proxy = ProxyManager()
    private val pokemonLimit: Int = 30
    private var listOfPokemonData: MutableList<PokemonData> = ArrayList()
    private var listOfFavorites: List<UserPokemon> = ArrayList()
    private var listOfViewed: List<UserPokemon> = ArrayList()
    var selected: MutableLiveData<FilterList>? = null
    private val _originalList = MutableLiveData<List<Pokemon>>()
    val originalList: LiveData<List<Pokemon>> = _originalList
    private val _filteredList = MutableLiveData<List<Pokemon>>()
    val filteredList: LiveData<List<Pokemon>> = _filteredList

    fun getPokemon(id: Int = 1){
        if(listOfPokemonData.count() < pokemonLimit) {
            showLoader()
            proxy.getApiManager()
                .getPokemonDetail(id)
                .enqueue(object : Callback<PokemonData> {
                    override fun onResponse(
                        call: Call<PokemonData>,
                        response: Response<PokemonData>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { listOfPokemonData.add(it) }
                            if (id < pokemonLimit)
                                getPokemon(id = id + 1)
                            else
                                getDBData()
                        } else {
                            hideLoader()
                            showError()
                        }
                    }

                    override fun onFailure(call: Call<PokemonData>, t: Throwable) {
                        hideLoader()
                        showError()
                    }
                })
        }
    }

    fun getDBData(){
        getAllFavorites().observeForever{
            listOfFavorites = it
            getAllViewed().observeForever{
                listOfViewed = it
                convertPokemonObjects()
            }
        }
    }

    fun convertPokemonObjects(){
        val pokemons: MutableList<Pokemon> = ArrayList()
        var pokemon: Pokemon? = null
        var abilities: MutableList<String>? = null
        var stats: MutableList<FinalStat>? = null
        var finalStat: FinalStat? = null
        var isFavorite: Boolean = false
        var isViewed: Boolean = false
        for(pk in listOfPokemonData){
            abilities = ArrayList()
            stats = ArrayList()
            for(ab in pk.abilities){
                abilities.add(ab.ability.name)
            }
            for(st in pk.stats){
                finalStat = FinalStat(st.stat.name, if(st.base_stat.toInt() > 100) "100" else st.base_stat)
                stats.add(finalStat)
            }
            isFavorite = listOfFavorites.filter { it.idPokemon == pk.id }.count() > 0
            isViewed = listOfViewed.filter { it.idPokemon == pk.id }.count() > 0
            pokemon = Pokemon(pk.id, pk.sprites.front_default, pk.name, pk.height, pk.weight, abilities, stats, isFavorite, isViewed)
            pokemons.add(pokemon)
        }
        _originalList.postValue(pokemons)
        hideLoader()
    }

    fun select(filter: FilterList?) {
        if(selected == null){
            selected = MutableLiveData<FilterList>()
        }
        selected?.value = filter
        filterList()
    }

    fun filterList(){
        if(selected?.value == FilterList.ALL){
            _filteredList.postValue(originalList.value)
        }
        else if(selected?.value == FilterList.FAVORITES) {
            _filteredList.postValue(originalList.value?.filter { it.favorite })
        }
        else if(selected?.value == FilterList.VIEWED) {
            _filteredList.postValue(originalList.value?.filter { it.viewed })
        }
    }

    fun logout(){
        deleteUserSession()
        cleanData()
    }

    fun cleanData(){
        _originalList.postValue(ArrayList())
        listOfPokemonData = ArrayList()
        listOfFavorites = ArrayList()
        listOfViewed = ArrayList()
        selected = null
    }
}

enum class FilterList {
    FAVORITES, ALL, VIEWED
}