package com.xinxin.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import com.xinxin.passwordmanager.adapter.MainListAdapter
import com.xinxin.passwordmanager.repository.db.entity.DataEntityDao
import com.xinxin.passwordmanager.ui.ExportActivity
import com.xinxin.passwordmanager.ui.ImportActivity
import com.xinxin.passwordmanager.ui.add.AddAccountActivity
import com.xinxin.passwordmanager.ui.base.BaseActivity

/**
 * 主页
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView : RecyclerView
    private lateinit var mainListAdapter : MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd_main)
        fabAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity,AddAccountActivity::class.java))
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        recyclerView = findViewById(R.id.rv_main)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        mainListAdapter = MainListAdapter()
        recyclerView.adapter = mainListAdapter

        mainListAdapter.setOnItemClickListener(object : MainListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, AddAccountActivity::class.java)
                intent.putExtra("id",mainListAdapter.getItemId(position))
                startActivity(intent)
            }
        })

        mainListAdapter.setOnItemLongClickListener(object : MainListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                showPopMenu(view,position)
            }
        })

    }


    override fun onResume() {
        super.onResume()
        val list = MyApplication.instance.getDaoSession().dataEntityDao.queryBuilder().list()
        mainListAdapter.setData(list)
    }


    fun showPopMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.item_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            MyApplication.instance.getDaoSession().dataEntityDao.deleteByKey(mainListAdapter.getItemId(position))
            mainListAdapter.removeItem(position)
            false
        }
        popupMenu.setOnDismissListener {
//            Snackbar.make(currentFocus!!, "取消", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
        }
        popupMenu.show()
    }


    private fun showToast(name: String) {
        Snackbar.make(currentFocus!!, name, Snackbar.LENGTH_SHORT).show()
    }

    private var exitTime = System.currentTimeMillis()

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (System.currentTimeMillis() - exitTime < 2000) {
                System.exit(0)
                super.onBackPressed()
            } else {
                exitTime = System.currentTimeMillis()
                showToast(getString(R.string.click_two_quit))
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val menuItem = menu.findItem(R.id.action_search)
        val searchView= menuItem.actionView as android.support.v7.widget.SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                queryData(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                queryData(query)
                return true
            }

        })
        searchView.isSubmitButtonEnabled = true//设置是否显示开始搜索按钮

        return true
    }

    private fun queryData(query: String?) {
        if (query!!.isEmpty()) {
            val list = MyApplication.instance.getDaoSession().dataEntityDao.queryBuilder().build().list()
            mainListAdapter.setData(list)
        } else {
            val list = MyApplication.instance.getDaoSession().dataEntityDao.queryBuilder()
                    .where(DataEntityDao.Properties.SoftName.like("%$query%")).build().list()
            mainListAdapter.setData(list)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_camera -> startActivity(Intent(this@MainActivity,ImportActivity::class.java))
            R.id.nav_gallery -> startActivity(Intent(this@MainActivity,ExportActivity::class.java))
            R.id.nav_share -> {

            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
