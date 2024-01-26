package com.example.hotel.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** hotelテーブル用：Entity */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
	/** 識別ID */
	@Id
	private Integer id;
	/** 予約者の名前 */
	private String name;
	/** 予約者の電話番号 */
	private String tel;
	/** 予約者の住所 */
	private String address;
	/** チェックイン日 */
	private LocalDate check_in;
	/** チェックアウト日 */
	private LocalDate check_out;
	/** 予約人数 */
	private String people;
	/** コース */
	private String course;
}
