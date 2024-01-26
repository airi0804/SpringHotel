package com.example.hotel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hotel.entity.Hotel;
import com.example.hotel.form.HotelForm;
import com.example.hotel.service.HotelService;
import com.example.hotel.validator.HotelValidator;

/** Hotelコントローラー */
@Controller
@RequestMapping("/hotel")
public class HotelController {
	/** DI対象 */
	@Autowired
	HotelService service;
	/** 「form-backing bean」の初期化 */
	@ModelAttribute
	public HotelForm setUpForm() {
		HotelForm form = new HotelForm();
		return form;
	}
	/** Hotelの予約一覧を表示します */
	@GetMapping
	public String showList(HotelForm hotelForm, Model model) {
		//新規登録設定
		hotelForm.setNewHotel(true);
		//一覧を取得する
		Iterable<Hotel> list = service.selectAll();
		//表示用「Model」への格納
		model.addAttribute("list", list);
		model.addAttribute("title", "新規予約");
		model.addAttribute("title2", "予約する");
		return "crud";
	}
	/** 予約データを1件挿入 */
	@PostMapping("/insert")
	public String insert(@Validated HotelForm hotelForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		// FormからEntityへの詰め替え
		Hotel hotel = new Hotel();
		hotel.setName(hotelForm.getName());
		hotel.setTel(hotelForm.getTel());
		hotel.setAddress(hotelForm.getAddress());
		hotel.setCheck_in(hotelForm.getCheck_in());
		hotel.setCheck_out(hotelForm.getCheck_out());
		hotel.setPeople(hotelForm.getPeople());
		hotel.setCourse(hotelForm.getCourse());
		// 入力チェック
		if(!bindingResult.hasErrors()) {
			service.insertHotel(hotel);
			redirectAttributes.addFlashAttribute("complete", "予約しました");
			return "redirect:/hotel";
		} else {
			// エラーがある場合は、一覧表示処理を呼びます
			return showList(hotelForm, model);
		}
	}
	/** Hotelデータを１件取得し、フォーム内に表示する */
	@GetMapping("/{id}")
	public String showUpdate(HotelForm hotelForm, @PathVariable Integer id, Model model) {
		//Hotelを取得(Optionalでラップ)
		Optional<Hotel> hotelOptional = service.selectOneById(id);
		//HotelFormへの詰め直し
		Optional<HotelForm> hotelFormOptional = hotelOptional.map(t -> makeHotelForm(t));
		//HotelFormがnullでなければ中身を取り出す
		if(hotelFormOptional.isPresent()) { 
			hotelForm = hotelFormOptional.get();
		}
		// 更新用のModelを作成する
		makeUpdateModel(hotelForm, model);
		return "crud";
	}
	/** 更新用のModelを作成する */
	private void makeUpdateModel(HotelForm hotelForm, Model model) {
		model.addAttribute("id", hotelForm.getId());
		hotelForm.setNewHotel(false);
		model.addAttribute("hotelForm", hotelForm);
		model.addAttribute("title", "予約変更");
		model.addAttribute("title2", "予約情報を変更する");
	}
	/** idをKeyにしてデータを更新する */
	@PostMapping("/update")
	public String update(
			@Validated HotelForm hotelForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		//HotelFormからHotelに詰め直す
		Hotel hotel = makeHotel(hotelForm);
		// 入力チェック
		if(!result.hasErrors()) {
			//更新処理、フラッシュスコープの使用、リダイレクト（個々の編集ページ）
			service.updateHotel(hotel);
			redirectAttributes.addFlashAttribute("complete", "更新しました");
			// 更新画面を表示する
			return "redirect:/hotel/" + hotel.getId();
		} else {
			// 更新用のModelを作成する
			makeUpdateModel(hotelForm, model);
			return "curd";
		}
	}
	// ---------- 【以下はFormとDomainObjectの詰めなおし】 ----------
	/** HotelFormからHotelに詰め直して戻り値とし返します */
	private Hotel makeHotel(HotelForm hotelForm) {
		Hotel hotel = new Hotel();
		hotel.setId(hotelForm.getId());
		hotel.setName(hotelForm.getName());
		hotel.setTel(hotelForm.getTel());
		hotel.setAddress(hotelForm.getAddress());
		hotel.setCheck_in(hotelForm.getCheck_in());
		hotel.setCheck_out(hotelForm.getCheck_out());
		hotel.setPeople(hotelForm.getPeople());
		hotel.setCourse(hotelForm.getCourse());
		return hotel;
	}
	/** HotelからHotelFormに詰め直して戻り値とし返します */
	private HotelForm makeHotelForm(Hotel hotel) {
		HotelForm form = new HotelForm();
		form.setId(hotel.getId());
		form.setName(hotel.getName());
		form.setTel(hotel.getTel());
		form.setAddress(hotel.getAddress());
		form.setCheck_in(hotel.getCheck_in());
		form.setCheck_out(hotel.getCheck_out());
		form.setPeople(hotel.getPeople());
		form.setCourse(hotel.getCourse());
		form.setNewHotel(false);
		return form;
	}
	/** idをKeyにしてデータを削除する */
	@PostMapping("/delete")
	public String delete(
			@RequestParam("id") String id,
			Model model,
			RedirectAttributes redirectAttributes) {
		//タスクを1件削除してリダイレクト
		service.deleteHotelById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "キャンセルしました");
		return "redirect:/hotel";
	}
	

//	@PostMapping("/in_out")
//	public String in_out(LocalDate check_in, LocalDate check_out, Hotel hotel,
//			HotelForm hotelForm, Model model, RedirectAttributes redirectAttributes,
//			Object target) {
//		System.out.println("wa");
//		HotelForm form = (HotelForm) target;
//		if (!(form.getCheck_out().compareTo(form.getCheck_in()) > 0)) {
//			service.errorInOut(hotel);
//			redirectAttributes.addFlashAttribute("check", "エラー チェックインよりチェックアウトの方が過去");
//			// エラーがある場合は、一覧表示処理を呼びます
//			return showList(hotelForm, model);
//		} else {
//			System.out.println("チェックインの方がチェックアウトより過去");
//			return "redirect:/hotel";
//		}
//	}

	/** インジェクション */
	@Autowired
	HotelValidator hotelValidator;
	/** 相関チェック登録 */
	@InitBinder("hotelForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(hotelValidator);
	}
}
