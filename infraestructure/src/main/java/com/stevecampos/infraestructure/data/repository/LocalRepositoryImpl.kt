package com.stevecampos.infraestructure.data.repository

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Vehicle
import com.stevecampos.domain.repository.ParkingRepository
import com.stevecampos.infraestructure.data.dao.CarEntityDao
import com.stevecampos.infraestructure.data.dao.MotorcycleEntityDao
import com.stevecampos.infraestructure.data.dao.ParkingSpaceDao
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.data.mapper.ParkingSpaceDomainToInfraMapper
import com.stevecampos.infraestructure.data.mapper.ParkingSpaceInfraToDomainMapper

class LocalRepositoryImpl(
    private val parkingSpaceDomainToInfraMapper: ParkingSpaceDomainToInfraMapper,
    private val parkingSpaceInfraToDomainMapper: ParkingSpaceInfraToDomainMapper,
    private val parkingSpaceDao: ParkingSpaceDao,
    private val carDao: CarEntityDao,
    private val motorcycleDao: MotorcycleEntityDao,
) : ParkingRepository {

    override suspend fun countCars(): Int {
        return carDao.getCarItems().size
    }

    override suspend fun countMotorcycles(): Int {
        return motorcycleDao.getMotorcycleItems().size
    }

    override suspend fun saveParkSpace(parkingSpace: ParkingSpace): Boolean {
        val parkingSpaceEntity = parkingSpaceDomainToInfraMapper.map(parkingSpace)
        return parkingSpaceDao.insert(parkingSpaceEntity)
    }

    override suspend fun getParkSpaceForVehicle(vehicle: Vehicle): ParkingSpace? {
        val parkingSpaceEntity = parkingSpaceDao.findByPlate(vehiclePlate = vehicle.plate())
        val parkingSpaceDomain = parkingSpaceEntity?.let {
            parkingSpaceInfraToDomainMapper.map(parkingSpaceEntity)
        } ?: kotlin.run {
            null
        }
        return parkingSpaceDomain
    }

    override suspend fun resetParkSpace(vehicle: Vehicle): Boolean {
        return parkingSpaceDao.delete(vehicle.plate())
    }

    override suspend fun saveParkingSpaces(spaces: List<ParkingSpace>): Boolean {
        val itemsInfra: List<ParkingSpaceEntity> = parkingSpaceDomainToInfraMapper.map(spaces)
        return parkingSpaceDao.insertAll(itemsInfra)
    }

    override suspend fun getParkingSpaces(): List<ParkingSpace> {
        val itemsInfra = parkingSpaceDao.getParkingSpaceItems()
        return parkingSpaceInfraToDomainMapper.map(itemsInfra)
    }
}