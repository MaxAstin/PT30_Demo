package eld.peaksoft.pt30_demo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import no.nordicsemi.android.ble.BleManager

class MainActivity : AppCompatActivity() {
    val btAdapter = BluetoothAdapter.getDefaultAdapter()
    var connector: String? = null
    var btDevice: BluetoothDevice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun connectToPairedDevice(view: View?) {
        val intent = Intent(this, DeviceListActivity::class.java)
        this.startActivityForResult(intent, 1)
    }

    fun startBluetooth(view: View?) {
        startBluetoothConnectionActivity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val deviceAddress = data.getStringExtra("DeviceAddress")
            btDevice = btAdapter.getRemoteDevice(deviceAddress)
        } else {
            startBluetooth(null)
        }
    }

    private fun startBluetoothConnectionActivity() {

        when {
            connector == null -> {
                Toast.makeText(this, "Please select the connector type.", Toast.LENGTH_SHORT).show()
            }
            btDevice == null -> {
                Toast.makeText(
                    this,
                    "Please connect to a paired bluetooth device.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                val intent = Intent(this, ATrackAx9BluetoothManager::class.java)
                intent.putExtra("connector", connector)
                intent.putExtra("device", btDevice)
                this.startActivity(intent)
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        // Is the button now checked?
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.J1939 -> if (checked) connector = "J1939"
            R.id.OBDII -> if (checked) connector = "OBDII"
        }
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1
    }
}