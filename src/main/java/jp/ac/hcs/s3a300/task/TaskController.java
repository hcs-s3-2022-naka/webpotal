package jp.ac.hcs.s3a300.task;


import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.hcs.s3a300.WebConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * タスク情報に関する機能・画面を制御する.
 */
@Slf4j
@Controller
public class TaskController {

	private static int HUNDRED_NUM = 60 * 60 * 24 * 365;

	@Autowired
	TaskService taskService;

	/**
	 * タスク一覧画面を表示する.
	 * @param principal ログイン情報
	 * @param model
	 * @return タスク一覧画面
	 */
	@PostMapping("/task/tasklist")
	public String getTaskList(Principal principal, Model model) {

		log.info("[" + principal.getName() + "]タスク検索:" + principal.getName());

		TaskEntity taskEntity = taskService.selectAll(principal.getName());
		model.addAttribute("taskEntity", taskEntity);

		return "task/task";
	}

	/**
	 * 1件分のタスク情報をデータベースに登録する.
	 * @param comment 登録するコメント
	 * @param limitday 登録する期限日
	 * @param principal ログイン情報
	 * @param model
	 * @return タスク一覧画面
	 */
	@PostMapping("/task/insert")
	public String postTaskInsert(@RequestParam("priority") String priority, @RequestParam("title") String title,
			@RequestParam("comment") String comment, @RequestParam("limitday") String limitday,
			Principal principal, Model model) {

		//タスク名の入力チェック
		if (title == null || title.equals("") || title.length() > 50) {
			log.warn("[" + principal.getName() + "]タスク登録データ:タスク名不適切");
			return getTaskList(principal, model);
		}
		//コメントの入力チェック
		if (comment == null || comment.equals("") || title.length() > 200) {
			log.warn("[" + principal.getName() + "]タスク登録データ:コメント不適切");
			return getTaskList(principal, model);
		}

		//日付の入力チェック
		Date dateLimitday = dateValid(limitday, principal, model);
		if (dateLimitday == null) {
			return getTaskList(principal, model);
		}

		//優先度の入力チェック
		int priorityNum = priorityValid(priority);
		if (priorityNum == 0) {
			log.warn("[" + principal.getName() + "]タスク登録データ:優先度がおかしい:" + priority);
			return getTaskList(principal, model);
		}

		log.info("[" + principal.getName() + "]タスク登録データ:" + title + "," + priority + "," + comment + "," + limitday);

		TaskData data = new TaskData();
		data.setUserId(principal.getName());
		data.setPriority(Priority.idOf(priorityNum));
		data.setTitle(title);
		data.setComment(comment);
		data.setLimitday(dateLimitday);

		boolean result = taskService.insertOne(data);

		if (result) {
			log.info("[" + principal.getName() + "]タスク登録成功");
		} else {
			log.warn("[" + principal.getName() + "]タスク登録失敗");
		}

		return getTaskList(principal, model);
	}

	private int priorityValid(String priority) {
		int priorityNum;
		try {
			priorityNum = Integer.parseInt(priority);
			if (priorityNum > 3 && priorityNum < 1) {
				throw new NumberFormatException("数値の範囲外です");
			}
		} catch (NumberFormatException e) {
			// 画面から数値に変換できない文字が入力された場合
			e.printStackTrace();
			return 0;
		}
		return priorityNum;
	}

	private Date dateValid(String limitday, Principal principal, Model model) {
		//期限日の入力チェック
		Date dateLimitday = null;
		try {
			// 画面から来る値は、区切り文字が / ではなく - になる為、注意が必要
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateLimitday = sdFormat.parse(limitday);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateLimitday);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MILLISECOND, -1);
			Date now = cal.getTime();
			if(now.before(new Date())) {
				throw new IllegalArgumentException("日付が過去日です");
			}
			if(dateLimitday.getTime() - (new Date().getTime()) > HUNDRED_NUM) {
				throw new IllegalArgumentException("日付が１００年後です");
			}
		} catch (ParseException e) {
			// 画面で入力をカレンダーにしているので、起こらない想定
			log.warn("[" + principal.getName() + "]タスク登録データ:日付形式がおかしい:" + limitday);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			log.warn("[" + principal.getName() + "]タスク登録データ:日付が不適用です:" + limitday);
			e.printStackTrace();
		}
		return dateLimitday;
	}

	/**
	 * 1件分のタスク情報をデータベースから削除する.
	 * @param id 削除するタスクID
	 * @param principal ログイン情報
	 * @param model
	 * @return タスク一覧画面
	 */
	@GetMapping("/task/delete/{id:.+}")
	public String getTaskDelete(@PathVariable("id") int id, Principal principal, Model model) {

		log.info("[" + principal.getName() + "]タスク削除:" + id);

		boolean result = taskService.deleteOne(id);

		if (result) {
			log.info("[" + principal.getName() + "]タスク削除成功");
			model.addAttribute("result", "タスク削除成功");
		} else {
			log.warn("[" + principal.getName() + "]タスク削除失敗");
			model.addAttribute("result", "タスク削除失敗");
		}

		return getTaskList(principal, model);
	}

	/**
	 * 自分の全てのタスク情報をCSVファイルとしてダウンロードさせる.
	 * @param principal ログイン情報
	 * @param model
	 * @return タスク情報のCSVファイル
	 */
	@PostMapping("/task/csv")
	public ResponseEntity<byte[]> getTaskCsv(Principal principal, Model model) {

		final String OUTPUT_FULLPATH = WebConfig.OUTPUT_PATH + WebConfig.FILENAME_TASK_CSV;

		log.info("[" + principal.getName() + "]CSVファイル作成:" + OUTPUT_FULLPATH);

		// CSVファイルをサーバ上に作成
		taskService.taskListCsvOut(principal.getName());

		// CSVファイルをサーバから読み込み
		byte[] bytes = null;
		try {
			bytes = taskService.getFile(OUTPUT_FULLPATH);
			log.info("[" + principal.getName() + "]CSVファイル読み込み成功:" + OUTPUT_FULLPATH);
		} catch (IOException e) {
			log.warn("[" + principal.getName() + "]CSVファイル読み込み失敗:" + OUTPUT_FULLPATH);
			e.printStackTrace();
		}

		// CSVファイルのダウンロード用ヘッダー情報設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", WebConfig.FILENAME_TASK_CSV);

		// CSVファイルを端末へ送信
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}

}
