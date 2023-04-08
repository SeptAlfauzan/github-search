    package com.example.githubprofile.ui

    import android.os.Bundle
    import android.util.Log
    import android.view.Menu
    import android.view.MenuItem
    import android.view.View
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.bumptech.glide.Glide
    import com.example.githubprofile.R
    import com.example.githubprofile.adapter.DetailUserSectionPagerAdapter
    import com.example.githubprofile.config.helper.ViewModelHelper
    import com.example.githubprofile.databinding.ActivityDetailUserBinding
    import com.example.githubprofile.model.User
    import com.example.githubprofile.ui.viewModel.MainViewModel
    import com.google.android.material.tabs.TabLayout
    import com.google.android.material.tabs.TabLayoutMediator
    class DetailUserActivity : AppCompatActivity() {

        companion object{
            val TAG = "DetailUserActivity"
            val TAB_TITLES = listOf<String>(
                "Follower",
                "Following"
            )
            val USERNAME = "username"
        }
        private var _binding: ActivityDetailUserBinding? = null
        private val binding get() = _binding!!
        private lateinit var viewModel: MainViewModel

        private var isFavorite: Boolean = false
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            _binding = ActivityDetailUserBinding.inflate(layoutInflater)

            setContentView(binding.root)
            val bundle = intent.extras
            val username = bundle?.getString(USERNAME)

            val argsToSectionPager = Bundle().apply {
                putString(USERNAME, username)
            }

            val sectionPagerAdapter = DetailUserSectionPagerAdapter(this, argsToSectionPager)
            val viewPager = binding.viewPager

            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = binding.tabs

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = TAB_TITLES[position]
            }.attach()

            viewModel = ViewModelHelper.obtainViewModel(this as AppCompatActivity)

            viewModel.isLoading.observe(this){ isLoading(it) }
            viewModel.notification.observe(this){
                it.getContentIfNotHandled()?.let {text ->
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                }
            }

            Log.d(TAG, "onCreate: ${viewModel.currentUser.value}")

            viewModel.currentUser.observe(this){
                setDetailData(it)
                setTitle("Detail ${it.name} Account")
            }

            if(viewModel.currentUser.value == null){
                username?.let { user -> viewModel.getUser(user) }
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.favorite_button, menu)

            viewModel.currentUser.observe(this){
                viewModel.checkIsFavorite(it.id).observe(this){user ->
                    isFavorite = user != null
                    Log.d(TAG, "onCreateOptionsMenu: is favorite $isFavorite")
                    val favoriteIcon = resources.getDrawable(R.drawable.outline_favorite_24)
                    val unFavoriteIcon = resources.getDrawable(R.drawable.baseline_favorite_border_24)
                    menu?.findItem(R.id.toggle_favorite)?.icon = if(isFavorite) favoriteIcon else unFavoriteIcon
                }
            }
            return true;
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                android.R.id.home -> {
                    onBackPressed()
                    return true
                }
                R.id.toggle_favorite -> {
                    isFavorite = !isFavorite

                    val favoriteIcon = resources.getDrawable(R.drawable.outline_favorite_24)
                    val unFavoriteIcon = resources.getDrawable(R.drawable.baseline_favorite_border_24)
                    item.icon = if(isFavorite) favoriteIcon else unFavoriteIcon
                    viewModel.currentUser.value?.let { viewModel.toggleFavorite(isFavorite, it) }
                    return true
                }
                else -> return false
            }
        }

        private fun isLoading(status: Boolean){
            if(status){
                binding.detailLoading.visibility = View.VISIBLE
                binding.detailMainContainer.visibility = View.GONE
            }else{
                binding.detailLoading.visibility = View.GONE
                binding.detailMainContainer.visibility = View.VISIBLE
            }
        }

        private fun setDetailData(data: User){

            Glide.with(this).load(data.avatarUrl).into(
                binding.cardDetail.detailUserAvatar
            )

            binding.apply {
                cardDetail.detailName.text = data.name
                cardDetail.detailUsername.text = data.login
                followerInfo.textview.text = "${data.followers} Follower"
                followingInfo.textview.text = "${data.following} Following"
                detailDescription.text = data.bio ?: "-"
                emailInfo.emailUser.text = data.email ?: "-"
                twitterInfo.twitterUser.text = data.twitterUsername ?: "-"
            }
        }
    }