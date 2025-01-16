package xyz.teamgravity.observeconnectivityvalidated

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.teamgravity.observeconnectivityvalidated.ui.theme.ObserveConnectivityValidatedTheme

class MainActivity : ComponentActivity() {

    private val observer: ConnectivityObserver by lazy { ConnectivityObserver(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ObserveConnectivityValidatedTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    val connected by observer.connected.collectAsStateWithLifecycle(false)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        Text(
                            text = stringResource(R.string.your_connected, connected.toString())
                        )
                    }
                }
            }
        }
    }
}