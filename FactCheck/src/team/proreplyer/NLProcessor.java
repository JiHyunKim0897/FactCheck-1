package team.proreplyer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class NLProcessor {
	
	static public void main(String[] args) {
		/*
		String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
		String accessKey = "12f62172-3e4b-4db6-bfae-edff06147564"; // 발급받은 Access Key
		String analysisCode = "srl"; // 언어 분석 코드
		String text = "류현진 선수는 23일 시범경기에서 투구수를 75개 늘렸다."; // 분석할 텍스트 데이터
		Gson gson = new Gson();

		Map<String, Object> request = new HashMap<>();
		Map<String, String> argument = new HashMap<>();

		argument.put("analysis_code", analysisCode);
		argument.put("text", text);

		request.put("access_key", accessKey);
		request.put("argument", argument);

		URL url;
		Integer responseCode = null;
		String responBody = null;
		try {
			url = new URL(openApiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(gson.toJson(request).getBytes("UTF-8"));
			wr.flush();
			wr.close();

			responseCode = con.getResponseCode();
			InputStream is = con.getInputStream();
			byte[] buffer = new byte[is.available()];
			int byteRead = is.read(buffer);
			responBody = new String(buffer);

			System.out.println("[responseCode] " + responseCode);
			System.out.println("[responBody]");
			System.out.println(responBody);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		NLProcessor nlprocessor = new NLProcessor();
		nlprocessor.test_NLP("영희는 영화만 본다.");
	}
	/*
	static public class Morpheme {
        final String text;
        final String type;
        Integer count;
        public Morpheme (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }
	*/
	
	public String test_NLP(String input) {
		String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
		String accessKey = "12f62172-3e4b-4db6-bfae-edff06147564"; // 발급받은 Access Key
		String analysisCode = "srl"; // 언어 분석 코드
		String text = input; // 분석할 텍스트 데이터
		Gson gson = new Gson();

		Map<String, Object> request = new HashMap<>();
		Map<String, String> argument = new HashMap<>();

		argument.put("analysis_code", analysisCode);
		argument.put("text", text);

		request.put("access_key", accessKey);
		request.put("argument", argument);

		URL url;
		Integer responseCode = null;
		//String responBody = null;
		String returnResult = "";
		try {
			url = new URL(openApiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(gson.toJson(request).getBytes("UTF-8"));
			wr.flush();
			wr.close();

			responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuffer sb = new StringBuffer();
 
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String responBodyJson = sb.toString();
            
            JSONParser jsonParser = new JSONParser();
            try {
				JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
				JSONObject returnObject = (JSONObject) jsonObject.get("return_object");
				JSONArray jsonArray = (JSONArray) returnObject.get("sentence");
				System.out.println(jsonArray);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
            // http 요청 오류 시 처리
            if ( responseCode != 200 ) {
                // 오류 내용 출력
                System.out.println("[error] " + responBodyJson);
                return null;
            }

            Map<String, Object> responeBody = gson.fromJson(responBodyJson, Map.class);
            Integer result = ((Double) responeBody.get("result")).intValue();
            Map<String, Object> returnObject;
            List<Map> sentences;
 
            // 분석 요청 오류 시 처리
            if ( result != 0 ) {
                // 오류 내용 출력
                System.out.println("[error] " + responeBody.get("result"));
                return null;
            }
 
            // 분석 결과 활용
            returnObject = (Map<String, Object>) responeBody.get("return_object");
            sentences = (List<Map>) returnObject.get("sentence");
 
            for(Map<String, Object> sentence: sentences) {
            	//System.out.println(sentences);
            	System.out.println("[입력한 문장(input)]\n " + sentence.get("text"));
            	
            	List<Map<String, Object>> analysisResult = (List<Map<String, Object>>) sentence.get("morp");
            	System.out.println("[형태소 분석 결과]");
            	for(int i=0; i<analysisResult.size(); i++) {
            		System.out.println(" " + analysisResult.get(i));
            		returnResult += analysisResult.get(i).get("lemma") + " ";
            	}
            	
            	analysisResult = (List<Map<String, Object>>) sentence.get("WSD");
            	System.out.println("[어휘의미 분석 결과(동음이의어)]");
            	for(int i=0; i<analysisResult.size(); i++) {
            		System.out.println(" " + analysisResult.get(i));
            	}
            	
            	analysisResult = (List<Map<String, Object>>) sentence.get("word");
            	System.out.println("[어절 정보 분석 결과]");
            	for(int i=0; i<analysisResult.size(); i++) {
            		System.out.println(" " + analysisResult.get(i));
            	}
            	
            	analysisResult = (List<Map<String, Object>>) sentence.get("NE");
            	System.out.println("[개체명 정보 인식 결과]");
            	for(int i=0; i<analysisResult.size(); i++) {
            		System.out.println(" " + analysisResult.get(i));
            	}
            	
            	analysisResult = (List<Map<String, Object>>) sentence.get("dependency");
            	System.out.println("[의존구문 분석 결과]");
            	for(int i=0; i<analysisResult.size(); i++) {
            		System.out.println(" " + analysisResult.get(i));
            	}
            	
            	analysisResult = (List<Map<String, Object>>) sentence.get("SRL");
            	System.out.println("[의미역 분석 결과]");
            	for(int i=0; i<analysisResult.size(); i++) {
            		System.out.println(" " + analysisResult.get(i));
            	}
            	System.out.println();
            }
            /*
            Map<String, Morpheme> morphemesMap = new HashMap<String, Morpheme>();
            Map<String, NameEntity> nameEntitiesMap = new HashMap<String, NameEntity>();
            List<Morpheme> morphemes = null;
            List<NameEntity> nameEntities = null;
 
            for( Map<String, Object> sentence : sentences ) {
                // 형태소 분석기 결과 수집 및 정렬
                List<Map<String, Object>> morphologicalAnalysisResult = (List<Map<String, Object>>) sentence.get("morp");
                for( Map<String, Object> morphemeInfo : morphologicalAnalysisResult ) {
                    String lemma = (String) morphemeInfo.get("lemma");
                    Morpheme morpheme = morphemesMap.get(lemma);
                    if ( morpheme == null ) {
                        morpheme = new Morpheme(lemma, (String) morphemeInfo.get("type"), 1);
                        morphemesMap.put(lemma, morpheme);
                    } else {
                        morpheme.count = morpheme.count + 1;
                    }
                }
 
                // 개체명 분석 결과 수집 및 정렬
                List<Map<String, Object>> nameEntityRecognitionResult = (List<Map<String, Object>>) sentence.get("NE");
                for( Map<String, Object> nameEntityInfo : nameEntityRecognitionResult ) {
                    String name = (String) nameEntityInfo.get("text");
                    NameEntity nameEntity = nameEntitiesMap.get(name);
                    if ( nameEntity == null ) {
                        nameEntity = new NameEntity(name, (String) nameEntityInfo.get("type"), 1);
                        nameEntitiesMap.put(name, nameEntity);
                    } else {
                        nameEntity.count = nameEntity.count + 1;
                    }
                }
            }
                
            if ( 0 < morphemesMap.size() ) {
                morphemes = new ArrayList<Morpheme>(morphemesMap.values());
                morphemes.sort( (morpheme1, morpheme2) -> {
                    return morpheme2.count - morpheme1.count;
                });
            }
 
            if ( 0 < nameEntitiesMap.size() ) {
                nameEntities = new ArrayList<NameEntity>(nameEntitiesMap.values());
                nameEntities.sort( (nameEntity1, nameEntity2) -> {
                    return nameEntity2.count - nameEntity1.count;
                });
            }
            
            // 형태소들 중 명사들에 대해서 많이 노출된 순으로 출력 ( 최대 5개 )
            morphemes
                .stream()
                .filter(morpheme -> {
                    return morpheme.type.equals("NNG") ||
                            morpheme.type.equals("NNP") ||
                            morpheme.type.equals("NNB");
                })
                .limit(5)
                .forEach(morpheme -> {
                    System.out.println("[명사] " + morpheme.text + " ("+morpheme.count+")" );
                });
 
            // 형태소들 중 동사들에 대해서 많이 노출된 순으로 출력 ( 최대 5개 )
            System.out.println("");
            morphemes
                .stream()
                .filter(morpheme -> {
                    return morpheme.type.equals("VV");
                })
                .limit(5)
                .forEach(morpheme -> {
                    System.out.println("[동사] " + morpheme.text + " ("+morpheme.count+")" );
                });
            
            // 인식된 개채명들 많이 노출된 순으로 출력 ( 최대 5개 )
            System.out.println("");
            nameEntities
                .stream()
                .limit(5)
                .forEach(nameEntity -> {
                    System.out.println("[개체명] " + nameEntity.text + " ("+nameEntity.count+")" );
                });
            */
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnResult;
	}
	
	public List<Map<String, Object>> NLP(String input) {
		String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
		String accessKey = "12f62172-3e4b-4db6-bfae-edff06147564"; // 발급받은 Access Key
		String analysisCode = "srl"; // 언어 분석 코드
		String text = input; // 분석할 텍스트 데이터
		Gson gson = new Gson();

		Map<String, Object> request = new HashMap<>();
		Map<String, String> argument = new HashMap<>();

		argument.put("analysis_code", analysisCode);
		argument.put("text", text);

		request.put("access_key", accessKey);
		request.put("argument", argument);

		URL url;
		Integer responseCode = null;
		String returnResult = "";
		try {
			url = new URL(openApiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(gson.toJson(request).getBytes("UTF-8"));
			wr.flush();
			wr.close();

			responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuffer sb = new StringBuffer();
 
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String responBodyJson = sb.toString();
 
            // http 요청 오류 시 처리
            if ( responseCode != 200 ) {
                // 오류 내용 출력
                System.out.println("[error] " + responBodyJson);
                return null;
            }

            Map<String, Object> responeBody = gson.fromJson(responBodyJson, Map.class);
            Integer result = ((Double) responeBody.get("result")).intValue();
            Map<String, Object> returnObject;
            List<Map<String, Object>> sentences;
 
            // 분석 요청 오류 시 처리
            if ( result != 0 ) {
                // 오류 내용 출력
                System.out.println("[error] " + responeBody.get("result"));
                return null;
            }
 
            // 분석 결과 활용
            returnObject = (Map<String, Object>) responeBody.get("return_object");
            sentences = (List<Map<String, Object>>) returnObject.get("sentence");
 
            return sentences;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONArray NLP2(String input) {
		String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
		String accessKey = "12f62172-3e4b-4db6-bfae-edff06147564"; // 발급받은 Access Key
		String analysisCode = "srl"; // 언어 분석 코드
		String text = input; // 분석할 텍스트 데이터
		Gson gson = new Gson();

		Map<String, Object> request = new HashMap<>();
		Map<String, String> argument = new HashMap<>();

		argument.put("analysis_code", analysisCode);
		argument.put("text", text);

		request.put("access_key", accessKey);
		request.put("argument", argument);

		URL url;
		Integer responseCode = null;
		String returnResult = "";
		try {
			url = new URL(openApiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(gson.toJson(request).getBytes("UTF-8"));
			wr.flush();
			wr.close();

			responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuffer sb = new StringBuffer();
            
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String responBodyJson = sb.toString();
            
            // http 요청 오류 시 처리
            if ( responseCode != 200 ) {
                // 오류 내용 출력
                System.out.println("[error] http 요청 오류");
                return null;
            }
 
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = null;
            try {
				JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
				JSONObject returnObject = (JSONObject) jsonObject.get("return_object");
				jsonArray = (JSONArray) returnObject.get("sentence");
			} catch (ParseException e) {
				e.printStackTrace();
			}
         
            return jsonArray;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
