package dgtic.unam.modulosiete

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dgtic.unam.modulosiete.Fragments.*

enum class ProviderType {
    BASIC,
    CORREO,
    GOOGLE,
    FACEBOOK
}

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener { private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicioToolsBar()
    }

    private fun inicioToolsBar(){
        val toolbar:Toolbar=findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer=findViewById(R.id.drawer_layout)
        val toggle= ActionBarDrawerToggle(this, drawer,toolbar,R.string.abrir,R.string.cerrar)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze_24)
        iniciarNavegacionView()

    }
    private fun iniciarNavegacionView(){

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, MisCaminatasFragment()).commit()

        val navegacionView: NavigationView =findViewById(R.id.nav_view)
        val menu = navegacionView.menu
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            val spanString = SpannableString(menuItem.title.toString())
            spanString.setSpan(ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, spanString.length, 0)
            menuItem.title = spanString
            val icon = menuItem.icon
            icon?.mutate()
            icon?.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
        }
        navegacionView.setNavigationItemSelectedListener(this)
        val headerView: View = LayoutInflater.from(this).inflate(R.layout.header_main, navegacionView,false)
        navegacionView.addHeaderView(headerView)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setSelectedItemId(R.id.mis_caminatas)
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked), // unchecked
                intArrayOf(android.R.attr.state_checked)  // checked
            ),
            intArrayOf(
                Color.parseColor("#FFFFFF"),
                Color.parseColor("#74F270")
            )
        )
        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            when (menuItem.itemId) {
                R.id.nueva_caminata -> {
                    val homeFragment = AgregarCaminataFragment()
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment)
                }
                R.id.mis_caminatas -> {
                    val searchFragment = MisCaminatasFragment()
                    fragmentTransaction.replace(R.id.fragment_container, searchFragment)
                }
                R.id.nuevo_formulario -> {
                    val settingsFragment = FormulariosFragment()
                    fragmentTransaction.replace(R.id.fragment_container, settingsFragment)
                }
            }
            fragmentTransaction.commit()
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.myProfile->{
                true
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    // Comprobacion de correo y metodo de ingreso
    private fun setup(email: String, providerType: String) {
        //binding.emailTextView.text = email
        //binding.providerTextView.text = providerType

        // Se almacenan los datos de inicio de sesion
        val preferencias = getSharedPreferences(getString(R.string.file_preferencia), Context.MODE_PRIVATE).edit()
        preferencias.putString("email", email)
        preferencias.putString("proovedor", providerType)
        preferencias.apply()
    }
}