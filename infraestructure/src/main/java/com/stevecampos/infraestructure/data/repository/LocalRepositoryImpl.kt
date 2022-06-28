package com.stevecampos.infraestructure.data.repository

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Vehicle
import com.stevecampos.domain.repository.ParkingRepository
import com.stevecampos.infraestructure.data.db.ParkingDb
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.data.mapper.ParkingSpaceDomainToInfraMapper
import com.stevecampos.infraestructure.data.mapper.ParkingSpaceInfraToDomainMapper

class LocalRepositoryImpl(
    private val parkingSpaceDomainToInfraMapper: ParkingSpaceDomainToInfraMapper,
    private val parkingSpaceInfraToDomainMapper: ParkingSpaceInfraToDomainMapper,
    private val parkingDb: ParkingDb
    /*private val parkingSpaceDao: ParkingSpaceDao,
    private val carDao: CarEntityDao,
    private val motorcycleDao: MotorcycleEntityDao,*/
) : ParkingRepository {

    override suspend fun countCars(): Int {
        return parkingDb.carEntityDao.getCarItems().size
    }

    override suspend fun countMotorcycles(): Int {
        return parkingDb.motorcycleEntityDao.getMotorcycleItems().size
    }

    override suspend fun saveParkSpace(parkingSpace: ParkingSpace): Boolean {
        val parkingSpaceEntity = parkingSpaceDomainToInfraMapper.map(parkingSpace)
        val passed = parkingDb.parkingSpaceDao.insert(parkingSpaceEntity)
        return true
    }

    override suspend fun getParkSpaceForVehicle(vehicle: Vehicle): ParkingSpace? {
        val parkingSpaceEntity =
            parkingDb.parkingSpaceDao.findByPlate(vehiclePlate = vehicle.plate())
        val parkingSpaceDomain = parkingSpaceEntity?.let {
            parkingSpaceInfraToDomainMapper.map(parkingSpaceEntity)
        } ?: kotlin.run {
            null
        }
        return parkingSpaceDomain
    }

    override suspend fun resetParkSpace(vehicle: Vehicle): Boolean {
        return parkingDb.parkingSpaceDao.delete(vehicle.plate()) != -1
    }

    override suspend fun saveParkingSpaces(spaces: List<ParkingSpace>): Boolean {
        val itemsInfra: List<ParkingSpaceEntity> = parkingSpaceDomainToInfraMapper.map(spaces)
        val passed = parkingDb.parkingSpaceDao.insertAll(itemsInfra)
        return true
    }

    override suspend fun getParkingSpaces(): List<ParkingSpace> {
        val itemsInfra = parkingDb.parkingSpaceDao.getParkingSpaceItems()
        return parkingSpaceInfraToDomainMapper.map(itemsInfra)
    }
}