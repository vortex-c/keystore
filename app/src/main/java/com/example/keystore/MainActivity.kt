package com.example.keystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keystore.ui.theme.KeyStoreTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Home(Modifier.padding(12.dp))
                }
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    var text by remember {
        mutableStateOf("")
    }

    val encryptedData by viewModel.savedEncryptedString.observeAsState()

    Column(
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(200.dp),
            value = text,
            onValueChange = { text = it })
        encryptedData?.takeIf { it.isNotBlank() }?.let {
            Text(modifier = Modifier.padding(horizontal = 16.dp), text = it)
        }

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                if (text.isNotBlank()) {
                    viewModel.encryptAndSaveString(text)
                }
            }) {
            Text(text = "Encrypt / Decrypt")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    Home()
}