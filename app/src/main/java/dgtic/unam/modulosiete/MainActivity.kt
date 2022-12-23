package dgtic.unam.modulosiete

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar

enum class ProviderType {
    BASIC,
    CORREO,
    GOOGLE,
    FACEBOOK
}

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
        toolbar.setNavigationIcon(R.drawable.unam_32)
        iniciarNavegacionView()

    }
    private fun iniciarNavegacionView(){
        val navegacionView: NavigationView =findViewById(R.id.nav_view)
        navegacionView.setNavigationItemSelectedListener(this)
        val headerView: View = LayoutInflater.from(this).inflate(R.layout.header_main, navegacionView,false)
        navegacionView.addHeaderView(headerView)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.contraint_layout->{
                startActivity(Intent(this,ConstraintActivity::class.java))
            }
            R.id.nestedscrollview->{
                startActivity(Intent(this,NestedScrollViewActivity::class.java))
            }
            R.id.collapsing->{
                startActivity(Intent(this,CollapsingToolbarLayout::class.java))
            }
            R.id.video->{
                startActivity(Intent(this,MainActivityVideo::class.java))
            }
            R.id.sonido->{
                startActivity(Intent(this,MainActivitySonido::class.java))
            }
            R.id.recycler_view->{
                startActivity(Intent(this,RecyclerView::class.java))
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