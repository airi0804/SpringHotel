package com.example.hotel.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/** Form */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelForm {
	/** 識別ID */
	private Integer id;
	/** 予約者の名前 */
//	@NotBlank(message="氏名が未入力です")
	@NotEmpty(message="氏名が未入力です")
	private String name;
	/** 予約者の電話番号 */
	@NotBlank(message="電話番号が未入力です")
	private String tel;
	/** 予約者の住所 */
	@NotBlank(message="住所が未入力です")
	private String address;
	/** チェックイン日 */
//	@NotBlank
	@NotNull(message="チェックイン日が未選択です")
	@Future
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate check_in;
	/** チェックアウト日 */
//	@NotBlank
	@NotNull(message="チェックアウト日が未選択です")
	@Future
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate check_out;
	/** 予約人数 */
	@NotBlank(message="予約人数が未選択です")
	private String people;
	/** コース */
	@NotBlank(message="コースが未選択です")
	private String course;
	/** 「登録」or「変更」判定用 */
	private Boolean newHotel;
}
