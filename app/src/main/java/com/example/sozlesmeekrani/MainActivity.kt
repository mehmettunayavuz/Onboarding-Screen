package com.example.sozlesmeekrani

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("PermissionPrefs", Context.MODE_PRIVATE)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "transaction_screen"
                    ) {
                        composable("transaction_screen") { TransactionScreen(navController) }
                        composable("new_screen") { NewScreen(navController) }
                        composable("contact_screen") { ContactScreen(navController) }
                        composable("ready_screen"){ Ready(navController) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(navController: NavHostController) {
    val transaction by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.transaction))
    val progress by animateLottieCompositionAsState(
        composition = transaction,
        iterations = LottieConstants.IterateForever
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.morparatr_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .size(200.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LottieAnimation(
                composition = transaction,
                progress = { progress },
                modifier = Modifier.size(350.dp)
            )

            Text(
                text = "Morpara hesabından yapacağın tüm işlemleri güvenli şekilde gerçekleştir.",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(
            onClick = { /* Atla butonuna tıklama işlemi */ },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Atla",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }

        Button(
            onClick = { navController.navigate("new_screen") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(color = Color.Transparent)
        ) {
            Text(
                text = "İleri",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun NewScreen(navController: NavHostController) {
    val newAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.notification))
    val progress by animateLottieCompositionAsState(
        composition = newAnimation,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = newAnimation,
                progress = { progress },
                modifier = Modifier.size(350.dp)
            )

            Text(
                text = "Bildirimlerinizi açarak bütün gelişmelerden haberdar olabilirsiniz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )
        }

        Button(
            onClick = { /* Atla butonuna tıklama işlemi */ },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Atla",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }

        val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                navController.navigate("contact_screen")
            } else {
                // İzin verilmediğinde yapılacak işlemler
            }
        }

        Button(
            onClick = { requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "İleri",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun ContactScreen(navController: NavHostController) {
    val newAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.contacts))
    val progress by animateLottieCompositionAsState(
        composition = newAnimation,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = newAnimation,
                progress = { progress },
                modifier = Modifier.size(350.dp)
            )

            Text(
                text = "Rehberinize Erişim izni vererek daha iyi bir deneyim yaşayabilirsiniz.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )
        }

        Button(
            onClick = { /* Atla butonuna tıklama işlemi */ },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Atla",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }

        val requestContactPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                navController.navigate("ready_screen")

            } else {

            }
        }

        Button(
            onClick = { requestContactPermissionLauncher.launch(Manifest.permission.READ_CONTACTS) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "İleri",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }
    }
}
@Composable
fun Ready(navController: NavHostController) {
    val readyAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.allset))
    val progress by animateLottieCompositionAsState(
        composition = readyAnimation,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        )
        {
            LottieAnimation(
                composition = readyAnimation,
                progress = { progress },
                modifier = Modifier.size(350.dp)
            )
            Text(
                text = "Artık Hazırsınız! Uygulamayı kullanmaya başlayabilirsiniz.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(8.dp)
            )
        }
        Button(
            onClick = { /* Atla butonuna tıklama işlemi */ },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Atla",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )

        }
        Button(
            onClick = { /*   */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "İleri",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        TransactionScreen(navController)
    }
}

@Preview
@Composable
fun NewScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        NewScreen(navController)
    }
}

@Preview
@Composable
fun ContactScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        ContactScreen(navController)
    }
}
@Preview
@Composable
fun ReadyPreview(){
    MaterialTheme{
        val navController= rememberNavController()
        Ready(navController)
    }
}