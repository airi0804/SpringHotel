package com.example.hotel.service;

import java.util.Optional;

import com.example.hotel.entity.Hotel;

/** Hotelサービス処理：Service */
public interface HotelService {
	/** 予約情報を全件取得します */
	Iterable<Hotel> selectAll();
	/** 予約情報を、idをキーに１件取得します */
	Optional<Hotel> selectOneById(Integer id);
	/** 予約情報を登録します */
	void insertHotel(Hotel hotel);
	/** 予約情報を更新します */
	void updateHotel(Hotel hotel);
	/** 予約情報を削除します */
	void deleteHotelById(Integer id);
	/** check_in, check_out 判定 */
	void errorInOut(Hotel hotel);
}
