package xyz.vmartinez.iniciodesesion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import xyz.vmartinez.iniciodesesion.activities.MenuActivity
import xyz.vmartinez.iniciodesesion.models.InicioDeSesionModel
import xyz.vmartinez.iniciodesesion.models.TokenModel
import xyz.vmartinez.iniciodesesion.services.ApiCallback
import xyz.vmartinez.iniciodesesion.services.InicioDeSesionService

class MainActivity : AppCompatActivity() {

    lateinit var servicio : InicioDeSesionService

    lateinit var editTextCorreo: EditText
    lateinit var editTextContrasenia: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //inicializar el servicio
        servicio = InicioDeSesionService()

        //Vinculamos los controles
        editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)
        editTextContrasenia = findViewById<EditText>(R.id.editTextContrasenia)
    }

    fun iniciarSesion(view: View){
        var correo = editTextCorreo.text.toString()
        var contrasenia = editTextContrasenia.text.toString()
        var inicioDeSesionModel = InicioDeSesionModel(correo, contrasenia)

        servicio.iniciarSesion(inicioDeSesionModel,  object : ApiCallback<TokenModel> {
            override fun onSuccess(token: TokenModel) {
                if(token.token == ""){
                    Toast.makeText(this@MainActivity, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                }else{
                    var intent: Intent = Intent(this@MainActivity, MenuActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onError(error: String) {
                Toast.makeText(this@MainActivity, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            }})
    }
}