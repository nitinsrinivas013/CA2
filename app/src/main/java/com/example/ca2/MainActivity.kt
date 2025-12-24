package com.example.ca2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ca2.ui.theme.CA2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA2Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun Screen1(
    nav : NavController
){
    var name by remember{ mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize().background(color = Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        TextField(name ,{name = it} , label = {Text("Enter Name")})
        Spacer(Modifier.height(15.dp))
        TextField(quantity , {quantity = it} , label = {Text("Enter Quantity")})
        Spacer(Modifier.height(25.dp))
        Button(
            onClick = {
                val qty = quantity.toIntOrNull() ?: 0
                if (qty > 0) {
                    nav.navigate("Screen2/$name/$quantity")
                }else {
                    Toast.makeText(
                        context, "Quantity must be greater than 0", Toast.LENGTH_LONG
                    ).show()
                }
            }
        ){
            Text("Done")
        }
    }
}

@Composable
fun Screen2(
    nav : NavController,
    name : String,
    quantity : String
) {
    Column(
        Modifier.fillMaxSize().background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Name: $name",
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(Modifier.height(15.dp))
        Text(
            "Quantity: $quantity",
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(Modifier.height(25.dp))
        Button(
            onClick = {nav.popBackStack()}
        ) {
            Text("Back")
        }
    }
}

@Composable
fun AppNavigation(){
    val nav = rememberNavController()
    NavHost(nav , "Screen1") {
        composable("Screen1"){
            Screen1(nav)
        }
        composable("Screen2/{name}/{quantity}"){
            val name = it.arguments?.getString("name")
            val quantity = it.arguments?.getString("quantity")
            Screen2(nav , name!! , quantity!!)
        }
    }
}