package xyz.teamgravity.observeconnectivityvalidated

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ConnectivityObserver(
    context: Context
) {

    private val manager: ConnectivityManager = context.getSystemService()!!

    val connected: Flow<Boolean> = callbackFlow {
        val callback = object : NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                trySend(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
            }

            override fun onUnavailable() {
                super.onUnavailable()

                trySend(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)

                trySend(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                trySend(true)
            }
        }

        manager.registerDefaultNetworkCallback(callback)

        awaitClose { manager.unregisterNetworkCallback(callback) }
    }
}