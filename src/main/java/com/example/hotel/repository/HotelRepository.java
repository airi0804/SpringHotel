package com.example.hotel.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.hotel.entity.Hotel;

/** Hotelテーブル：RepositoryImpl */
public interface HotelRepository extends CrudRepository<Hotel, Integer> {

}
