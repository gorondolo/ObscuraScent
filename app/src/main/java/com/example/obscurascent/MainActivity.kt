package com.example.obscurascent

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.obscurascent.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val database = FirebaseDatabase.getInstance().reference
    private var allPerfumes = mutableListOf<Perfume>()
    private lateinit var perfumeAdapter: PerfumeAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = listOf("Todos", "Fresco", "Amaderado", "Oriental")

    private val bannerHandler = Handler(Looper.getMainLooper())
    private var currentBanner = 1
    private val totalBanners = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategories()
        loadBanner()
        loadPerfumes()
        setupSearch()

        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerHandler.removeCallbacksAndMessages(null)
    }

    private fun setupCategories() {
        categoryAdapter = CategoryAdapter(this, categories) { category ->
            filterByCategory(category)
        }
        binding.recyclerCategories.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        binding.recyclerCategories.adapter = categoryAdapter
    }

    private fun loadBanner() {
        loadBannerByIndex(1)
        startBannerRotation()
    }

    private fun startBannerRotation() {
        bannerHandler.postDelayed(object : Runnable {
            override fun run() {
                currentBanner++
                if (currentBanner > totalBanners) currentBanner = 1
                loadBannerByIndex(currentBanner)
                bannerHandler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    private fun loadBannerByIndex(index: Int) {
        database.child("banners").child("banner$index")
            .get().addOnSuccessListener { snapshot ->
                val imageUrl = snapshot.child("imageUrl").value.toString()
                Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .into(binding.imgBanner)
            }
    }

    private fun loadPerfumes() {
        database.child("perfumes")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    allPerfumes.clear()
                    for (item in snapshot.children) {
                        val perfume = item.getValue(Perfume::class.java)
                        perfume?.let { allPerfumes.add(it.copy(id = item.key ?: "")) }
                    }
                    setupPerfumeRecycler(allPerfumes)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun setupPerfumeRecycler(list: List<Perfume>) {
        perfumeAdapter = PerfumeAdapter(this, list)
        binding.recyclerPerfumes.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerPerfumes.adapter = perfumeAdapter
    }

    private fun filterByCategory(category: String) {
        val filtered = if (category == "Todos") allPerfumes.toList()
        else allPerfumes.filter { it.categoria.equals(category, ignoreCase = true) }
        setupPerfumeRecycler(filtered)
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                val filtered = if (query.isEmpty()) allPerfumes
                else allPerfumes.filter {
                    it.nombre.contains(query, ignoreCase = true) ||
                            it.marca.contains(query, ignoreCase = true)
                }
                setupPerfumeRecycler(filtered)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}