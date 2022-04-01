package eld.peaksoft.pt30_demo

import org.joda.time.DateTime
import java.util.HashMap

class VbusData {
    @Transient
    var ambient // Geometris only
            = 0.0
        private set
    private val assetId: Long? = null
    private val userId: Long = 0
    var batteryVoltage // battery voltage
            : Double? = null
        private set

    @Transient
    var deviceSerialNumber: String? = null
    private val driverHistoryId: Long? = null

    @Transient
    var dtc // DTC Code
            : String? = null
        private set

    @Transient
    private var dtcTimestamp: DateTime? = null

    @Transient
    private var engineTotalHoursTimestamp: DateTime? = null
    var engineRpm // EngineSpeed
            : Double? = null
        private set
    var engineCoolantTempCelsius // EngineCoolantTemp in Celsius
            : Double? = null
        private set
    var engineOilPressurekPa // EngineOilPressure
            : Double? = null
        private set
    var engineTotalHours // TotalEngineHours
            : Double? = null
        private set

    @Transient
    private var engineRpmTimestamp: DateTime? = null
    var fuelEconomy // FuelEconomy - liters/hour
            : Double? = null
        private set

    @Transient
    var fuelLevel // FuelLevel
            : Double? = null
        private set
    var fuelRate // FuelRate
            : Double? = null
        private set
    var fuelUsed // TotalFuelUsed
            : Double? = null
        private set

    @Transient
    var heading // GPS heading
            : Double? = null
        private set
    var highResOdometerKm // High Res Odometer in kilometer (km)
            : Double? = null
        private set

    @Transient
    var highResOdometerKmTimestamp: DateTime? = null
        private set

    @Transient
    var highResTripKm // HiResTotalDistance
            : Double? = null
        private set
    var isIgnition: Boolean? = null
        private set
    var latitude: Double? = null
        private set
    var longitude: Double? = null
        private set

    @Transient
    private var latlonTimestamp: DateTime? = null

    @Transient
    var managerName = ""
        private set

    @Transient
    var milStatus = 0.0
        private set
    var odometerKm // TotalDistance in kilometer (km)
            : Double? = null
        private set

    @Transient
    private var odometerKmTimestamp: DateTime? = null
    var parkingBrake: Int? = null
        private set
    var isPtoStatus // Power take off
            : Boolean? = null
        private set
    var isSeatBelt // SeatBelt
            : Boolean? = null
        private set
    var throttle // Throttle
            : Double? = null
        private set
    var timestamp: DateTime? = null
        private set

    @Transient
    var tripEngineHours: Double? = null
        private set

    @Transient
    var tripFuelUsed: Double? = null
        private set
    var tripOdometerKm // TripDistance in kilometer (km)
            : Double? = null
        private set

    @Transient
    var turnSignal: Int? = null
        private set

    @Transient
    private var vehicleSpeedKphTimestamp: DateTime? = null
    var transCurrentGear // TransCurrentGear
            : Int? = null
        private set
    var vehicleSpeedKph // VehicleSpeed in kilometer per hour (kph)
            : Double? = null
        private set
    var vin: String? = null
        private set

    @Transient
    private val fields: MutableMap<String, String> = HashMap()
    fun getFields(): Map<String, String> {
        return fields
    }

    fun putAllFields(fields: Map<String, String>?): VbusData {
        this.fields.putAll(fields!!)
        return this
    }

    fun putField(name: String, value: String): VbusData {
        fields[name] = value
        return this
    }

    fun getField(fieldName: String): String? {
        return fields[fieldName]
    }

    fun setAmbient(ambient: Double): VbusData {
        this.ambient = ambient
        return this
    }

    fun setBatteryVoltage(batteryVoltage: Double?): VbusData {
        this.batteryVoltage = batteryVoltage
        return this
    }

    fun setDtc(dtc: String?): VbusData {
        this.dtc = dtc
        return this
    }

    fun getDtcTimestamp(): DateTime? {
        return null
    }

    fun setDtcTimestamp(dtcTimestamp: DateTime?): VbusData {
        this.dtcTimestamp = dtcTimestamp
        return this
    }

    fun setEngCoolantTempCelsius(engineCoolantTemp: Double?): VbusData {
        engineCoolantTempCelsius = engineCoolantTemp
        return this
    }

    fun setEngOilPressurekPa(engineOilPressurekPa: Double?): VbusData {
        this.engineOilPressurekPa = engineOilPressurekPa
        return this
    }

    fun setEngRpm(engineRpm: Double?): VbusData {
        this.engineRpm = engineRpm
        return this
    }

    fun getEngineRpmTimestamp(): DateTime? {
        return null
    }

    fun setEngineRpmTimestamp(engineRpmTimestamp: DateTime?): VbusData {
        this.engineRpmTimestamp = engineRpmTimestamp
        return this
    }

    fun setEngineTotalHours(engineTotalHours: Double?): VbusData {
        this.engineTotalHours = engineTotalHours
        return this
    }

    fun getEngineTotalHoursTimestamp(): DateTime? {
        return null
    }

    fun setEngineTotalHoursTimestamp(engTotalHoursTimestamp: DateTime?): VbusData {
        engineTotalHoursTimestamp = engTotalHoursTimestamp
        return this
    }

    fun setFuelEconomy(fuelEconomy: Double?): VbusData {
        this.fuelEconomy = fuelEconomy
        return this
    }

    fun setFuelLevel(fuelLevel: Double?): VbusData {
        this.fuelLevel = fuelLevel
        return this
    }

    fun setFuelRate(fuelRate: Double?): VbusData {
        this.fuelRate = fuelRate
        return this
    }

    fun setFuelUsed(fuelUsed: Double?): VbusData {
        this.fuelUsed = fuelUsed
        return this
    }

    fun setHeading(heading: Double?): VbusData {
        this.heading = heading
        return this
    }

    fun setHighResOdometerKm(highResOdometerKm: Double?): VbusData {
        this.highResOdometerKm = highResOdometerKm
        return this
    }

    fun setHighResOdometerKmTimestamp(highResOdometerTimestamp: DateTime?): VbusData {
        highResOdometerKmTimestamp = highResOdometerTimestamp
        return this
    }

    fun setHighResTripKm(highResTripKm: Double?): VbusData {
        this.highResTripKm = highResTripKm
        return this
    }

    fun setIgnition(ignition: Boolean?): VbusData {
        isIgnition = ignition
        return this
    }

    fun setLatitude(latitude: Double?): VbusData {
        this.latitude = latitude
        return this
    }

    fun setLongitude(longitude: Double?): VbusData {
        this.longitude = longitude
        return this
    }

    fun getLatlonTimestamp(): DateTime? {
        return null
    }

    fun setLatlonTimestamp(latlonTimestamp: DateTime?): VbusData {
        this.latlonTimestamp = latlonTimestamp
        return this
    }

    fun setManagerName(managerName: String): VbusData {
        this.managerName = managerName
        return this
    }

    fun setMilStatus(milStatus: Double): VbusData {
        this.milStatus = milStatus
        return this
    }

    fun setOdometerKm(odometerKm: Double?): VbusData {
        this.odometerKm = odometerKm
        return this
    }

    fun getOdometerKmTimestamp(): DateTime? {
        return null
    }

    fun setOdometerKmTimestamp(odometerKm: DateTime?): VbusData {
        odometerKmTimestamp = odometerKm
        return this
    }

    fun setParkingBrake(parkingBrake: Int?): VbusData {
        this.parkingBrake = parkingBrake
        return this
    }

    fun setPtoStatus(ptoStatus: Boolean?): VbusData {
        isPtoStatus = ptoStatus
        return this
    }

    fun setSeatBelt(seatBelt: Boolean?): VbusData {
        isSeatBelt = seatBelt
        return this
    }

    fun setThrottle(throttle: Double?): VbusData {
        this.throttle = throttle
        return this
    }

    fun setTimestamp(timeStamp: DateTime?): VbusData {
        timestamp = timeStamp
        return this
    }

    fun setTransCurrentGear(transCurrentGear: Int?): VbusData {
        this.transCurrentGear = transCurrentGear
        return this
    }

    fun setTripEngineHours(tripEngineHours: Double?): VbusData {
        this.tripEngineHours = tripEngineHours
        return this
    }

    fun setTripFuelUsedLiter(tripFuelUsed: Double?): VbusData {
        this.tripFuelUsed = tripFuelUsed
        return this
    }

    fun setTripOdometerKm(tripOdometerKm: Double?): VbusData {
        this.tripOdometerKm = tripOdometerKm
        return this
    }

    fun setTurnSignal(turnSignal: Int?): VbusData {
        this.turnSignal = turnSignal
        return this
    }

    fun setVehicleSpeedKph(vehicleSpeedKph: Double?): VbusData {
        this.vehicleSpeedKph = vehicleSpeedKph
        return this
    }

    fun getVehicleSpeedKphTimestamp(): DateTime? {
        return null
    }

    fun setVehicleSpeedKphTimestamp(vehicleSpeedKphTimestamp: DateTime?): VbusData {
        this.vehicleSpeedKphTimestamp = vehicleSpeedKphTimestamp
        return this
    }

    fun setVin(vin: String?): VbusData {
        this.vin = vin
        return this
    }

    companion object {
        const val VBUS_BATT_VOLTAGE = "VBUS_BATT_VOLTAGE"
        const val VBUS_DTC = "VBUS_DTC"
        const val VBUS_DEVICE_SERIAL_NUMBER = "VBUS_DEVICE_SERIAL_NUMBER"
        const val VBUS_ENG_COOLANT_TEMP = "VBUS_ENG_COOLANT_TEMP"
        const val VBUS_ENG_OIL_PRESSURE = "VBUS_ENG_OIL_PRESSURE"
        const val VBUS_ENG_RPM = "VBUS_ENG_RPM"
        const val VBUS_ENG_RPM_TIMESTAMP = "VBUS_ENG_RPM_TIMESTAMP"
        const val VBUS_ENG_TOTAL_HOURS = "VBUS_ENG_TOTAL_HOURS"
        const val VBUS_ENG_TOTAL_HOURS_TIMESTAMP = "VBUS_ENG_TOTAL_HOURS_TIMESTAMP"
        const val VBUS_FUEL_ECONOMY = "VBUS_FUEL_ECONOMY"
        const val VBUS_FUEL_LEVEL = "VBUS_FUEL_LEVEL"
        const val VBUS_FUEL_RATE = "VBUS_FUEL_RATE"
        const val VBUS_FUEL_USED = "VBUS_FUEL_USED"
        const val VBUS_HEADING = "VBUS_HEADING"
        const val VBUS_HI_RES_ODOMETER = "VBUS_HI_RES_ODOMETER"
        const val VBUS_HI_RES_ODOMETER_TIMESTAMP = "VBUS_HI_RES_ODOMETER_TIMESTAMP"
        const val VBUS_HI_REZ_TRIP = "VBUS_HI_REZ_TRIP"
        const val VBUS_IGNITION = "VBUS_IGNITION"
        const val VBUS_LATITUDE = "VBUS_LATITUDE"
        const val VBUS_LONGITUDE = "VBUS_LONGITUDE"
        const val VBUS_MANAGER = "VBUS_MANAGER"
        const val VBUS_ODOMETER_KM = "VBUS_ODOMETER_KM"
        const val VBUS_ODOMETER_KM_TIMESTAMP = "VBUS_ODOMETER_KM_TIMESTAMP"
        const val VBUS_PARKING_BRAKE = "VBUS_PARKING_BRAKE"
        const val VBUS_PTO_STATUS = "VBUS_PTO_STATUS"
        const val VBUS_SEATBELT = "VBUS_SEATBELT"
        const val VBUS_THROTTLE_POSITION = "VBUS_THROTTLE_POSITION"
        const val VBUS_TIMESTAMP = "VBUS_TIMESTAMP"
        const val VBUS_TRANS_GEAR = "VBUS_TRANS_GEAR"
        const val VBUS_TRIP_ENGINE_HOURS = "VBUS_TRIP_ENGINE_HOURS"
        const val VBUS_TRIP_FUEL_USED = "VBUS_TRIP_FUEL_USED"
        const val VBUS_TRIP_ODOMETER = "VBUS_TRIP_ODOMETER"
        const val VBUS_TURN_SIGNAL = "VBUS_TURN_SIGNAL"
        const val VBUS_VEHICLE_SPEED_KPH = "VBUS_VEHICLE_SPEED_KPH"
        const val VBUS_VEHICLE_SPEED_KPH_TIMESTAMP = "VBUS_VEHICLE_SPEED_KPH_TIMESTAMP"
        const val VBUS_VIN = "VBUS_VIN"
    }
}