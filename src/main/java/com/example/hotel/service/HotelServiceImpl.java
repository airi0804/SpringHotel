package com.example.hotel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotel.entity.Hotel;
import com.example.hotel.repository.HotelRepository;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {
	/** Repository：注入 */
	@Autowired
	HotelRepository repository;
//	一覧表示
	@Override
	public Iterable<Hotel> selectAll() {
		return repository.findAll();
	}

//	idを指定して表示
	@Override
	public Optional<Hotel> selectOneById(Integer id) {
		return repository.findById(id);
	}

//	追加
	@Override
	public void insertHotel(Hotel hotel) {
		repository.save(hotel);

	}

//	編集
	@Override
	public void updateHotel(Hotel hotel) {
		repository.save(hotel);

	}

//	削除
	@Override
	public void deleteHotelById(Integer id) {
		repository.deleteById(id);

	}

//	check_in, check_out 判定
	@Override
	public void errorInOut(Hotel hotel) {
		repository.save(hotel);
	}
}
