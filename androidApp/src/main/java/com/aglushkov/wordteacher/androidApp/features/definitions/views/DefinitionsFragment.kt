package com.aglushkov.wordteacher.androidApp.features.definitions.views

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aglushkov.wordteacher.androidApp.databinding.FragmentDefinitionsBinding
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.DefinitionsDisplayModeBlueprint
import com.aglushkov.wordteacher.androidApp.general.ItemViewBinder
import com.aglushkov.wordteacher.androidApp.general.SimpleAdapter
import com.aglushkov.wordteacher.androidApp.general.views.bind
import com.aglushkov.wordteacher.di.AppComponentOwner
import com.aglushkov.wordteacher.di.DaggerDefinitionsComponent
import com.aglushkov.wordteacher.shared.features.definitions.vm.DefinitionsDisplayMode
import com.aglushkov.wordteacher.shared.features.definitions.vm.DefinitionsVM
import com.aglushkov.wordteacher.shared.general.item.BaseViewItem
import com.aglushkov.wordteacher.shared.general.resource.Resource
import dev.icerock.moko.mvvm.utils.bind
import javax.inject.Inject

class DefinitionsVMWrapper(
    application: Application
): AndroidViewModel(application) {

    @Inject lateinit var vm: DefinitionsVM
}

class DefinitionsFragment: Fragment(), DefinitionsDisplayModeBlueprint.Listener {
    private lateinit var vm: DefinitionsVM
    private var binding: FragmentDefinitionsBinding? = null

    @Inject lateinit var binder: ItemViewBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val wmWrapper = ViewModelProviders.of(this)
                .get(DefinitionsVMWrapper::class.java)

        val vmState = savedInstanceState?.getParcelable(VM_STATE) ?: DefinitionsVM.State()
        val deps = (requireContext().applicationContext as AppComponentOwner).appComponent
        val component = DaggerDefinitionsComponent.builder()
            .setDeps(deps)
            .setVMState(vmState)
            .setDefinitionsDisplayListener(this)
            .build()
        component.injectViewModelWrapper(wmWrapper)
        component.injectDefinitionsFragment(this)

        vm = wmWrapper.vm
        observeViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(VM_STATE, vm.state)
    }

    private fun observeViewModel() {
        viewLifecycleOwnerLiveData.observe(this, Observer {
            if (it == null) return@Observer
            onViewLifecycleOwnerReady(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDefinitionsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        val binding = this.binding!!

        binding.list.apply {
            layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        }

        binding.loadingStatusView.setOnTryAgainListener {
            vm.onTryAgainClicked()
        }

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vm.onWordSubmitted(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onViewLifecycleOwnerReady(viewLifecycleOwner: LifecycleOwner) {
        vm.definitions.bind(viewLifecycleOwner) {
            showDefinitions(it!!)
        }
    }

    private fun showDefinitions(it: Resource<List<BaseViewItem<*>>>) {
        val binding = this.binding!!

        val errorText = vm.getErrorText(it)?.toString(requireContext())
        it.bind(binding.loadingStatusView, errorText)

        updateListAdapter(it)
    }

    private fun updateListAdapter(it: Resource<List<BaseViewItem<*>>>) {
        val binding = this.binding!!

        if (binding.list.adapter != null) {
            (binding.list.adapter as SimpleAdapter).submitList(it.data())
        } else {
            binding.list.adapter = SimpleAdapter(binder).apply {
                submitList(it.data())
            }
        }
    }

    // DefinitionsDisplayModeBlueprint.Listener
    override fun onDisplayModeChanged(mode: DefinitionsDisplayMode) {
        vm.onDisplayModeChanged(mode)
    }
}

private val VM_STATE = "vm_state"