package team.proreplyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataProcessor {
	boolean collectFlag = false;

	public static void main(String[] args) {
		DataProcessor dataProcessor = new DataProcessor();
		dataProcessor.processInputData("김정은이 이산가족에게 송이버섯 2톤을 선물하지 않았다.");
	}

	public void processInputData(String input) {
		/* 사용자가 입력한 문장 자연어처리 */
		// Map<String, Object> NLP_input = NLProcessor.NLP(input).get(0);
		JSONArray NLP_input = new NLProcessor().NLP2(input);
		SentenceInfo sentenceInfo_object = new SentenceInfo().NLPtoSentenceInfo(input, NLP_input);
		System.out.println(sentenceInfo_object.sbj + " / " + sentenceInfo_object.obj + " / " + sentenceInfo_object.verb);

		ArrayList<SentenceInfo> relatedDatas = getCompareData(sentenceInfo_object.sbj, sentenceInfo_object.verb);
		
		System.out.println();
		System.out.println("@@@ [" + input + "]에 대한 사실 여부 판단 중....");
		if (relatedDatas.size() == 0) { // 판단유보
			System.out.println("관련 데이터가 없어요.ㅠㅠ 판단 유보!");
		} else {
			new Discriminator().judgeTruth(input, sentenceInfo_object,
					relatedDatas);
		}
	}

	public ArrayList<SentenceInfo> getCompareData(String sbj, String vp) {
		// 디비 갔다와
		ArrayList<SentenceInfo> compareDatas = new ArrayList<>();
		try {
			new DBManager();
			compareDatas = new DBManager().readRelatedData(sbj, vp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compareDatas;
	}

}
