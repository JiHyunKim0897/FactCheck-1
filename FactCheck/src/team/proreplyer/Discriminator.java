package team.proreplyer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Discriminator {
	List<String> relatedDatas = new ArrayList<>();

	public static void main(String[] args) {
		String hello = "[{\"a\":0, \"b\":9},{\"a\":8, \"b\":80}]";

		JSONParser jsonParser = new JSONParser();

		try {
			JSONArray jsonArray = (JSONArray) jsonParser.parse(hello);
			System.out.println(jsonArray);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int judgeTruth(String input, SentenceInfo sentenceInfo_input, ArrayList<SentenceInfo> relatedDatas) {
		int result = 0; // 0:판단유보, 1:사실, 2:거짓
		ArrayList<SentenceInfo> selectedDatas = new ArrayList<>();

		if (!sentenceInfo_input.obj.equals("null") || !sentenceInfo_input.cmp.equals("null")) { // 목적어 or 보어 있음
			for (int i = 0; i < relatedDatas.size(); i++) {
				if ((!relatedDatas.get(i).obj.equals("null") && !sentenceInfo_input.obj.equals("null")) || (!relatedDatas.get(i).cmp.equals("null") && !sentenceInfo_input.cmp.equals("null"))) { // 목적어 or 보어 체크
					if (relatedDatas.get(i).obj.equals(sentenceInfo_input.obj) || relatedDatas.get(i).cmp.equals(sentenceInfo_input.cmp)) { // 목적어 or 보어 같음
						boolean continue_flag = true;
						if (!relatedDatas.get(i).tmp.equals("null") && !sentenceInfo_input.tmp.equals("null")) { // 시간
																													// 비교
							if (!relatedDatas.get(i).tmp.equals(sentenceInfo_input.tmp)) { // 시간 다름
								continue_flag = false;
							}
						}
						if (continue_flag && !relatedDatas.get(i).location.equals("null")
								&& !sentenceInfo_input.location.equals("null")) { // 장소 비교
							if (!relatedDatas.get(i).location.equals(sentenceInfo_input.location)) { // 장소 다름
								continue_flag = false;
							}
						}
						if (continue_flag && !relatedDatas.get(i).adv.equals("null")
								&& !sentenceInfo_input.adv.equals("null")) { // 부사어 비교
							if (!relatedDatas.get(i).adv.equals(sentenceInfo_input.adv)) { // 부사어 다름
								continue_flag = false;
							}
						}
						if (continue_flag) { // 나머지 다 똑같!
							if (!(relatedDatas.get(i).neg.equals("no") ^ sentenceInfo_input.neg.equals("no"))) { // true~~~~~
								if (result != 1) {
									selectedDatas.clear();
								}
								result = 1;
								selectedDatas.add(relatedDatas.get(i));
							} else { // 부정(neg)에 걸림!!
								result = 2;
								selectedDatas.add(relatedDatas.get(i));
							}
						} else { // 하나라도 다름!
							if (result != 1) {
								result = 2;
								selectedDatas.add(relatedDatas.get(i));
							}
						}
					} else { // 목적어 or 보어 다름
						if (result != 1) {
							boolean continue_flag = true;
							if (!relatedDatas.get(i).tmp.equals("null") && !sentenceInfo_input.tmp.equals("null")) { // 시간
																														// 비교
								if (!relatedDatas.get(i).tmp.equals(sentenceInfo_input.tmp)) { // 시간 다름
									continue_flag = false;
								}
							}
							if (continue_flag && !relatedDatas.get(i).location.equals("null")
									&& !sentenceInfo_input.location.equals("null")) {
								if (!relatedDatas.get(i).location.equals(sentenceInfo_input.location)) { // 장소 다름
									continue_flag = false;
								}
							}
							if (continue_flag && !relatedDatas.get(i).adv.equals("null")
									&& !sentenceInfo_input.adv.equals("null")) {
								if (!relatedDatas.get(i).adv.equals(sentenceInfo_input.adv)) { // 부사어 다름
									continue_flag = false;
								}
							}
							if (continue_flag) { // 나머지 다 똑같!
								if (!(relatedDatas.get(i).neg.equals("no") ^ sentenceInfo_input.neg.equals("no"))) { // false
									result = 2;
									selectedDatas.add(relatedDatas.get(i));
								}
							}
						}
					}
				}
			}
		} else { // 목적어 or 보어 없음 -> 부사어 비교

		}

		// 결과 show(); 해줘야돼
		if (result == 0) {
			System.out.println("판단 유보!");
		} else {
			if (result == 1) {
				System.out.println("사실!");
			} else { // result == 2
				System.out.println("가짜뉴스입니다!");
			}
			System.out.println("***** 비교에 사용된 데이터 *****");
			for (int i = 0; i < selectedDatas.size(); i++) {
				System.out.println("비교문장" + (i + 1) + ") " + selectedDatas.get(i).sentence);
			}
		}
		return result;
	}

	public boolean test_judgeTruth(Map<String, Object> input, List<Map<String, Object>> related) {
		System.out.println("input : " + input.get("text"));
		for (int i = 0; i < related.size(); i++) { // 비교
			System.out.println("비교문장" + (i + 1) + " : " + related.get(i).get("text"));
			relatedDatas.add((String) related.get(i).get("text"));
			// VP:동사구, OBJ:목적격(거의 NP_OBJ인거같음-명사구목적격), SBJ:주격(거의 NP_OBJ)
		}

		// if(fact) input 디비에 넣어~~

		return false;
	}
}