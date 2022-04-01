package eld.peaksoft.pt30_demo

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import no.nordicsemi.android.ble.BleManager
import org.joda.time.DateTime
import timber.log.Timber
import java.io.IOException
import java.lang.StringBuilder
import java.util.*

class ATrackAx9BluetoothManager : Activity() {
    private var device: String? = null
    private var btDevice: BluetoothDevice? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)
        device = intent.getStringExtra("connector")
        btDevice = intent.getParcelableExtra("device")
        startBluetoothConnection(btDevice)
    }

    protected fun startBluetoothConnection(btDevice: BluetoothDevice?) {
        Log.d(TAG, "Starting Bluetooth connection..")
        try {
            // Instantiate a BluetoothSocket for the remote device and connect it.
            val btSock =
                btDevice!!.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
            btSock.connect()
            createDataReader(btSock)
        } catch (e: IOException) {
            Log.e(TAG, "Bluetooth connection failed.", e)
        }
    }

    fun createDataReader(btSock: BluetoothSocket): AtrackAx9SocketReader {


        return AtrackAx9SocketReader(device, btSock, this)
    }

    class AtrackAx9SocketReader internal constructor(
        private val device: String?,
        var btSock: BluetoothSocket,
        private val manager: ATrackAx9BluetoothManager
    ) {
        @Throws(IOException::class)
        protected fun populateVbus(vbusData: VbusData) {
            if (!btSock.isConnected) return

            // Default timestamp is now
            vbusData.setTimestamp(DateTime.now())
            val values = receiveData()
            if (values[VbusData.VBUS_ENG_RPM] != null) {
                val engineRPM = parseAndScaleEngineSpeed(values[VbusData.VBUS_ENG_RPM])
                vbusData.setEngRpm(engineRPM)
                vbusData.setEngineRpmTimestamp(DateTime.now())
                manager.writeDataToView("VBUS_ENG_RPM", engineRPM.toString())
            }
            if (values[VbusData.VBUS_ENG_TOTAL_HOURS] != null) {
                val engTotalHours = parseAndScaleEngineHours(values[VbusData.VBUS_ENG_TOTAL_HOURS])
                vbusData.setEngineTotalHours(engTotalHours)
                vbusData.setEngineTotalHoursTimestamp(DateTime.now())
                manager.writeDataToView("VBUS_ENG_TOTAL_HOURS", engTotalHours.toString())
            }
            if (values[VbusData.VBUS_LATITUDE] != null) {
                val latitude = parseAndScaleLatOrLon(values[VbusData.VBUS_LATITUDE])
                if (latitude != 0.0) {
                    vbusData.setLatitude(latitude)
                    vbusData.setLatlonTimestamp(DateTime.now())
                    manager.writeDataToView("VBUS_LATITUDE", latitude.toString())
                }
            }
            if (values[VbusData.VBUS_LONGITUDE] != null) {
                val longitude = parseAndScaleLatOrLon(values[VbusData.VBUS_LONGITUDE])
                if (longitude != 0.0) {
                    vbusData.setLongitude(longitude)
                    vbusData.setLatlonTimestamp(DateTime.now())
                    manager.writeDataToView("VBUS_LONGITUDE", longitude.toString())
                }
            }
            if (values[VbusData.VBUS_ODOMETER_KM] != null) {
                val odometerKm = values[VbusData.VBUS_ODOMETER_KM]!!.toDouble()
                vbusData.setOdometerKm(odometerKm)
                vbusData.setOdometerKmTimestamp(DateTime.now())
                manager.writeDataToView("VBUS_ODOMETER_KM", odometerKm.toString())
            }
            if (values[VbusData.VBUS_VEHICLE_SPEED_KPH] != null) {
                // J1939 Vehicle Speed in CPS
                val vehicleSpeed =
                    parseAndScaleVehicleSpeed(values[VbusData.VBUS_VEHICLE_SPEED_KPH])
                vbusData.setVehicleSpeedKph(vehicleSpeed)
                vbusData.setVehicleSpeedKphTimestamp(DateTime.now())
                manager.writeDataToView("VBUS_VEHICLE_SPEED_KPH", vehicleSpeed.toString())
            }
            if (values[VBUS_ATRACK_ODB2_SPEED_KPH] != null) {
                // ODB2 speed is in different units than J1939 Speed
                // For ODB2 the units are km/hour
                val vehicleSpeed = values[VBUS_ATRACK_ODB2_SPEED_KPH]!!
                    .toDouble()
                vbusData.setVehicleSpeedKph(vehicleSpeed)
                vbusData.setVehicleSpeedKphTimestamp(DateTime.now())
                manager.writeDataToView("VBUS_VEHICLE_SPEED_KPH", vehicleSpeed.toString())
            }
            if (values[VbusData.VBUS_TIMESTAMP] != null) {
                val l = values[VbusData.VBUS_TIMESTAMP]!!.toLong()
                // Multiply by 1000 to convert unix to joda time
                val d = DateTime(l * 1000)
                vbusData.setTimestamp(d)
                manager.writeDataToView("VBUS_TIMESTAMP", d.toString())
            }
            if (values[VbusData.VBUS_VIN] != null) {
                vbusData.setVin(values[VbusData.VBUS_VIN])
                manager.writeDataToView("VBUS_VIN", values[VbusData.VBUS_VIN].toString())
            }
            if (values[VbusData.VBUS_TRANS_GEAR] != null) {
                vbusData.setTransCurrentGear(Integer.valueOf(values[VbusData.VBUS_TRANS_GEAR]))
            }
            if (values[VbusData.VBUS_FUEL_USED] != null) {
                val fuelUsedG = parseAndScaleFuelUsed(values[VbusData.VBUS_FUEL_USED])
                vbusData.setFuelUsed(fuelUsedG)
            }
            Log.d(TAG, "Completed VbusData population")
        }

        @Throws(IOException::class)
        private fun receiveData(): Map<String, String?> {
            val retVals: MutableMap<String, String?> = HashMap()
            val `in` = btSock.inputStream
            val res = StringBuilder()
            while (true) {
                val b = `in`.read().toByte()
                if (b.toInt() == -1) { // -1 if the end of the stream is reached
                    break
                }
                val c = b.toChar()
                if (c == '\n') { // read until '\n' arrives
                    break
                }
                res.append(c)
            }
            val vbusRaw = res.toString().split(",[ ]*".toRegex()).toTypedArray()

            //if the data is valid in J1939 format, parse it
            when {
                vbusRaw.size == 28 -> {
                    retVals[VbusData.VBUS_TIMESTAMP] = vbusRaw[6]
                    retVals[VbusData.VBUS_LONGITUDE] = vbusRaw[8]
                    retVals[VbusData.VBUS_LATITUDE] = vbusRaw[9]
                    retVals[VbusData.VBUS_HEADING] = vbusRaw[10]
                    retVals[VbusData.VBUS_ENG_RPM] = vbusRaw[22]
                    retVals[VbusData.VBUS_VEHICLE_SPEED_KPH] = vbusRaw[23]
                    retVals[VbusData.VBUS_ODOMETER_KM] = vbusRaw[24]
                    retVals[VbusData.VBUS_ENG_TOTAL_HOURS] = vbusRaw[25]
                    retVals[VbusData.VBUS_VIN] = vbusRaw[26]
                    //trim off quirky characters attached to vin
                    if (vbusRaw[26].length > 17) {
                        val vin = vbusRaw[26].substring(0, 17)
                        retVals[VbusData.VBUS_VIN] = vin
                    }
                    retVals[VbusData.VBUS_FUEL_USED] = vbusRaw[27]
                }
                vbusRaw.size == 25 -> {
                    retVals[VbusData.VBUS_TIMESTAMP] = vbusRaw[6]
                    retVals[VbusData.VBUS_LONGITUDE] = vbusRaw[8]
                    retVals[VbusData.VBUS_LATITUDE] = vbusRaw[9]
                    retVals[VbusData.VBUS_HEADING] = vbusRaw[10]
                    retVals[VbusData.VBUS_ODOMETER_KM] = vbusRaw[12]

                    // ODB2 speed is in different units than J1939 Speed
                    retVals[VBUS_ATRACK_ODB2_SPEED_KPH] = vbusRaw[15]
                    retVals[VbusData.VBUS_ENG_RPM] = vbusRaw[22]
                    retVals[VbusData.VBUS_VIN] = vbusRaw[23]
                    if (vbusRaw[23].length > 17) {
                        val vin = vbusRaw[23].substring(0, 17)
                        retVals[VbusData.VBUS_VIN] = vin
                    }
                    retVals[VbusData.VBUS_ENG_TOTAL_HOURS] = vbusRaw[24]
                }
                vbusRaw[0].compareTo("AX9 Bluetooth Connected\r") == 0 -> {
                    configureDevice()
                }
            }
            return retVals
        }

        private fun configureDevice() {
            val configurationCommands: MutableList<String> = ArrayList()
            if (device == "J1939") {
                configurationCommands.add("AT\$REST=14,7\r\n")
                configurationCommands.add("AT\$OBDS=19\r\n")
                configurationCommands.add("AT\$FMSC=1,\"%JH2%JH1%JL3%JL2%JS1%JL4\"\r\n")
                configurationCommands.add("AT\$TRAC=,,,,,,18\r\n")
            } else if (device == "OBDII") {
                configurationCommands.add("AT\$REST=14,7\r\n")
                configurationCommands.add("AT\$OBDS=0\r\n")
                configurationCommands.add("AT\$FORM=,,,\"%RP%VN%EH\"\r\n")
                configurationCommands.add("AT\$TRAC=,,,,,,18\r\n")
            }
            for (command in configurationCommands) {
                val bytes = command.toByteArray()
                try {
                    btSock.outputStream.write(bytes)
                    btSock.outputStream.flush()
                } catch (e: IOException) {
                    Timber.e(e, "Error configuring Atrack")
                }
            }
        }

        private fun parseAndScaleFuelUsed(s: String?): Double {
            val fuelUsedMl = java.lang.Double.valueOf(s)
            return fuelUsedMl * 0.001
        }

        private fun parseAndScaleEngineCoolantTemp(s: String): Double {
            val engCoolantTempSixteenthC = java.lang.Double.valueOf(s)
            return engCoolantTempSixteenthC / 16.0
        }

        private fun parseAndScaleEngineHours(s: String?): Double {
            val engTwentiethHours = java.lang.Double.valueOf(s)
            return engTwentiethHours * 0.05
        }

        private fun parseAndScaleLatOrLon(s: String?): Double {
            val latOrLon = java.lang.Double.valueOf(s)
            return latOrLon * 0.000001
        }

        private fun parseAndScaleVehicleSpeed(s: String?): Double {
            val speedCPS = java.lang.Double.valueOf(s)
            return speedCPS / 256
        }

        private fun parseAndScaleEngineSpeed(s: String?): Double {
            var engSpeedRPM = java.lang.Double.valueOf(s)
            engSpeedRPM = engSpeedRPM * 0.125
            return engSpeedRPM
        }

        init {
            object : CountDownTimer(5000, 1000) {
                var vbusData: VbusData = VbusData()
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    try {
                        vbusData = VbusData()
                        populateVbus(vbusData)
                    } catch (e: IOException) {
                        Log.e(TAG, "populateVbusFailed", e)
                    }
                    start()
                }
            }.start()
        }
    }

    private fun writeDataToView(field: String, value: String) {
        val id = resources.getIdentifier(field, "id", applicationContext.packageName)
        val view = findViewById<View>(id) as TextView
        "$field: $value".also { view.text = it }
    }

    companion object {
        private val TAG = ATrackAx9BluetoothManager::class.java.simpleName
        private const val MANAGER_NAME = "ATrackAx9"
        private const val VBUS_ATRACK_ODB2_SPEED_KPH = "VBUS_ATRACK_ODB2_SPEED_KPH"
    }
}