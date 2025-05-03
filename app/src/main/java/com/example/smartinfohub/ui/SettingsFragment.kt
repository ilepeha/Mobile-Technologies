package com.example.smartinfohub.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartinfohub.adapter.ItemAdapter
import com.example.smartinfohub.databinding.FragmentSettingsBinding
import com.example.smartinfohub.model.Item
import java.io.BufferedReader
import java.io.InputStreamReader

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = readItemsFromFile()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ItemAdapter(itemList)
    }

    private fun readItemsFromFile(): List<Item> {
        val items = mutableListOf<Item>()
        try {
            val inputStream = requireContext().assets.open("data.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.forEachLine { line ->
                val parts = line.split("|")
                if (parts.size == 2) {
                    items.add(Item(parts[0], parts[1]))
                }
            }
            reader.close()
        } catch (e: Exception) {
            Log.e("SettingsFragment", "Error reading data.txt", e)
        }
        return items
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
